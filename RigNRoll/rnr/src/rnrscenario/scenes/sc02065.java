/*
 * @(#)sc02065.java   13/08/28
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
import rnr.src.players.CutSceneAuxPersonCreator;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.teleport.ITeleported;
import rnr.src.rnrcore.teleport.MakeTeleport;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.ChaseKoh;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.parkingplace;
import rnr.src.rnrscr.trackscripts;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 15,
    fieldWithDesiredStage = ""
)
public final class sc02065 extends stage implements ITeleported {
    private static final boolean DEBUG = false;
    boolean was_teleported = false;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc02065(Object monitor) {
        super(monitor, "sc02065");
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();

        if (null != ChaseKoh.getInstance()) {
            ChaseKoh.getInstance().finishShootingAnimation();
        }

        eng.disableControl();

        actorveh car = Crew.getIgrokCar();

        car.sVeclocity(0.0D);

        aiplayer dakota = aiplayer.getScenarioAiplayer("SC_ONTANIELO");

        dakota.bePassangerOfCar(car);

        aiplayer gun = new aiplayer("SC_DAKOTAGUN");

        gun.sPoolBased("dakota_cut_scene_gun");
        gun.setModelCreator(new CutSceneAuxPersonCreator(), "dakotagun");
        gun.bePassangerOfCar_simple(car);

        Vector pool = new Vector();

        pool.add(new SceneActorsPool("gun", gun.getModel()));

        Data _data = new Data(car);
        trackscripts trs = new trackscripts(getSyncMonitor());

        eng.unlock();
        trs.PlaySceneXMLThreaded("02065", false, pool, _data);
        eng.lock();
        gun.abondoneCar(car);
        dakota.abondoneCar(car);
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();

        if (null != ChaseKoh.getInstance()) {
            ChaseKoh.getInstance().endChaseButContinueRun();
        }

        car.teleport(scenarioscript.OXNARD_OFFICE);
        MakeTeleport.teleport(this);
        eng.unlock();

        while (true) {
            simplewaitFor(100);
            eng.lock();

            if (this.was_teleported) {
                eng.unlock();

                break;
            }

            eng.unlock();
        }

        eng.lock();

        parkingplace place = parkingplace.findParkingByName("OxnardParking04", scenarioscript.OXNARD_OFFICE);

        car.makeParking(place, 0);

        if (null != ChaseKoh.getInstance()) {
            ChaseKoh.getInstance().scheduleCarBlow();
        }

        eng.enableControl();
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.makeComeInAndLeaveParkingFast();
        eng.lock();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    /**
     * Method description
     *
     */
    @Override
    public void teleported() {
        this.was_teleported = true;
    }

    void simplewaitFor(int timemillesec) {
        try {
            waitFor(timemillesec);
        } catch (InterruptedException e) {
            eng.writeLog("Script Error. Stage sc02065 error.");
        }
    }

    void waitFor(int timemillesec) throws InterruptedException {
        Thread.sleep(timemillesec);
    }

    class Data {
        actorveh car;

        Data(actorveh paramactorveh) {
            this.car = paramactorveh;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
