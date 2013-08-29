/*
 * @(#)sc00530.java   13/08/26
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
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 7,
    fieldWithDesiredStage = ""
)
public final class sc00530 extends stage {
    private static boolean DEBUG = false;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc00530(Object monitor) {
        super(monitor, "sc00530");
    }

    /**
     * Method description
     *
     */
    public static void setDebug() {
        DEBUG = true;
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();

        actorveh car = Crew.getIgrokCar();

        eng.SwitchDriver_outside_cabin(car.getCar());
        car.setHandBreak(true);
        eng.unlock();

        trackscripts trs = new trackscripts(getSyncMonitor());
        Presests data = new Presests();

        data.M = car.gMatrix();

        vectorJ shift = new vectorJ(data.M.v1);

        shift.mult(10.0D);

        vectorJ pos1 = car.gPosition().oPlusN(shift);
        vectorJ pos2 = new vectorJ(pos1);

        pos1.z += 100.0D;
        pos2.z -= 100.0D;
        data.P = Collide.collidePoint(pos1, pos2);
        trs.PlaySceneXMLThreaded("00530", false, data);
        eng.lock();
        car.setHandBreak(false);
        event.Setevent(530);
        eng.SwitchDriver_in_cabin(car.getCar());
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();

        if (!(DEBUG)) {
            EventsControllerHelper.messageEventHappened("bankrupt");
        }

        DEBUG = false;
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Presests {
        matrixJ M;
        vectorJ P;
    }
}


//~ Formatted in DD Std on 13/08/26
