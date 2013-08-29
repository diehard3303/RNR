/*
 * @(#)sc00830.java   13/08/26
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
Released under the FreeBSD  license 
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met: 

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies, 
either expressed or implied, of the FreeBSD Project.
 *
 *
 *
 */


package rnr.src.rnrscenario.scenes;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.semitrailer;
import rnr.src.rnrcore.IScriptTask;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscenario.tech.SleepOnTime;
import rnr.src.rnrscr.drvscripts;
import rnr.src.rnrscr.parkingplace;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 9,
    fieldWithDesiredStage = ""
)
public final class sc00830 extends stage {
    private static final String ANIMATION_FINISHED = "Topo chase animate punkt b ended";
    private static boolean m_debugScene = false;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc00830(Object monitor) {
        super(monitor, "sc00830");
    }

    /**
     * Method description
     *
     */
    public static void setDebugScene() {
        m_debugScene = true;
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        trackscripts trs = new trackscripts(getSyncMonitor());
        actorveh car = Crew.getIgrokCar();

        if (!(m_debugScene)) {
            trs.PlaySceneXMLThreaded("justfade", false);
            eng.lock();
            eng.disableControl();

            vectorJ posTS = eng.getControlPointPosition("CP_LB_TS");

            car.sVeclocity(car.gVelocity().length() + 10.0D);
            car.autopilotTo(posTS);
            scenarioscript.script.parkOnPunktB();
            eng.unlock();
        } else {
            eng.lock();

            parkingplace parking = parkingplace.findParkingByName("PK_LA_LB_01",
                                       eng.getControlPointPosition("CP_LB_TS"));

            car.makeParking(parking, 4);
            eng.unlock();
        }

        m_debugScene = false;
        new SleepOnTime(4000);
        rnr.src.rnrscenario.tech.Helper.waitParked(car);
        eng.lock();
        car.setHandBreak(true);
        eng.unlock();
        trs.PlaySceneXMLThreaded("justfade", false);
        drvscripts.outCabinState(car).launch();
        trs.PlaySceneXMLThreaded("00830", false, scenarioscript.script.preparePunctBMatrix());
        eng.lock();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();

        actorveh car1 = Crew.getIgrokCar();

        drvscripts.playInsideCarFast(Crew.getIgrok(), car1);
        car1.leaveParking();

        actorveh car1num = Crew.getMappedCar("DOROTHY");
        actorveh car2num = Crew.getMappedCar("KOH");
        semitrailer trailer1 = car1num.querryTrailer();

        if (trailer1 != null) {
            trailer1.delete();
        }

        semitrailer trailer2 = car2num.querryTrailer();

        if (trailer2 != null) {
            trailer2.delete();
        }

        car1num.deactivate();
        car2num.deactivate();
        car.setHandBreak(false);
        scenarioscript.script.exitAnimation_punktB();
        EventsControllerHelper.messageEventHappened("Topo chase animate punkt b ended");
        eng.enableControl();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}


//~ Formatted in DD Std on 13/08/26
