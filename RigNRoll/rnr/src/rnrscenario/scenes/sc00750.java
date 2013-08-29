/*
 * @(#)sc00750.java   13/08/26
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
import rnr.src.rnrcore.Collide;
import rnr.src.rnrcore.IScriptTask;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.SimplePresets;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.rnrscr.drvscripts;
import rnr.src.rnrscr.specobjects;
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
public final class sc00750 extends stage {
    private static final String ANIMATION_FINISHED = "Topo chase animate punkt a ended";

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc00750(Object monitor) {
        super(monitor, "sc00750");
    }

    SimplePresets makePresets() {
        matrixJ punktAM = null;
        vectorJ punktAV = null;
        cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();

        if ((crossroad != null) && (crossroad.name.compareToIgnoreCase("KeyPoint_750") == 0)) {
            punktAM = crossroad.matrix;

            vectorJ pos1 = crossroad.position.oPlusN(new vectorJ(0.0D, 0.0D, 100.0D));
            vectorJ pos2 = crossroad.position.oPlusN(new vectorJ(0.0D, 0.0D, -100.0D));

            punktAV = Collide.collidePoint(pos1, pos2);

            if (punktAV == null) {
                punktAV = crossroad.position;
            }

            vectorJ shift_scene = new vectorJ(crossroad.matrix.v0);

            shift_scene.mult(-20.0D);
            punktAV.oPlus(shift_scene);
        }

        return new SimplePresets(punktAV, punktAM);
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        trackscripts trs = new trackscripts(getSyncMonitor());

        trs.PlaySceneXMLThreaded("justfade", false);
        eng.lock();
        eng.disableControl();

        actorveh car = Crew.getIgrokCar();

        car.sVeclocity(0.0D);

        cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();

        if ((null != crossroad) && (0 == "KeyPoint_750".compareTo(crossroad.name))) {
            vectorJ shift = new vectorJ(crossroad.matrix.v0);

            shift.mult(-5.0D);

            vectorJ pos_to_place_our_car = crossroad.position.oPlusN(shift);

            car.sPosition(pos_to_place_our_car);
            car.sOnTheRoadLaneAndStop(1);
        }

        drvscripts.outCabinState(car).launch();
        drvscripts.outCabinState(Crew.getMappedCar("DOROTHY")).launch();
        drvscripts.outCabinState(Crew.getMappedCar("KOH")).launch();
        eng.unlock();
        trs.PlaySceneXMLThreaded("00750", false, makePresets());
        eng.lock();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();

        actorveh car1 = Crew.getIgrokCar();

        drvscripts.playInsideCarFast(Crew.getIgrok(), car1);
        scenarioscript.script.chaseTopoexitAnimationPunktA();
        EventsControllerHelper.messageEventHappened("Topo chase animate punkt a ended");
        eng.unlock();
        eng.lock();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}


//~ Formatted in DD Std on 13/08/26
