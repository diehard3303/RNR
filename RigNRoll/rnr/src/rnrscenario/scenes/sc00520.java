/*
 * @(#)sc00520.java   13/08/26
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
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.trackscripts;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = -1,
    fieldWithDesiredStage = "scenarioStageNumber"
)
public final class sc00520 extends stage {
    private final int scenarioStageNumber;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     * @param scenarioStageNumber
     */
    public sc00520(Object monitor, int scenarioStageNumber) {
        super(monitor, "sc00520");
        this.scenarioStageNumber = scenarioStageNumber;
    }

    private Presets updatePositions(actorveh car) {
        Presets res = new Presets();

        res.M = rnr.src.rnrscenario.tech.Helper.makeMatrixAlignedToZ(car.gMatrix());
        res.P = car.gPosition();

        vectorJ shift = car.gDir();

        shift.mult(10.0D);
        res.P.oPlus(shift);

        vectorJ p1 = res.P.oPlusN(new vectorJ(0.0D, 0.0D, 10.0D));
        vectorJ p2 = res.P.oPlusN(new vectorJ(0.0D, 0.0D, -10.0D));

        res.P = Collide.collidePoint(p1, p2);

        return res;
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();

        actorveh car = Crew.getIgrokCar();

        car.sVeclocity(0.0D);
        car.setHandBreak(true);
        eng.SwitchDriver_outside_cabin(car.getCar());
        eng.unlock();

        trackscripts trs = new trackscripts(getSyncMonitor());

        trs.PlaySceneXMLThreaded("00520", false, updatePositions(car));
        eng.lock();
        car.setHandBreak(false);
        event.Setevent(465);
        eng.SwitchDriver_in_cabin(car.getCar());
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Presets {
        matrixJ M;
        vectorJ P;
    }
}


//~ Formatted in DD Std on 13/08/26
