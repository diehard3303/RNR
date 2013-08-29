/*
 * @(#)sc02060.java   13/08/28
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
import rnr.src.rnrcore.eng;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.ChaseKoh;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptEvents.EventsControllerHelper;

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
public final class sc02060 extends stage {
    private static boolean NEED_BLOW_CAR = true;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc02060(Object monitor) {
        super(monitor, "sc02060");
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setDebugMode(boolean value) {
        NEED_BLOW_CAR = !(value);
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.doWide(true);
        eng.disableControl();

        Data data = new Data();

        data.car = Crew.getMappedCar("KOH");

        trackscripts trs = new trackscripts(getSyncMonitor());

        eng.unlock();
        trs.PlaySceneXMLThreaded("02060", false, data);
        eng.lock();

        if (null != ChaseKoh.getInstance()) {
            ChaseKoh.getInstance().endChase();
        }

        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        eng.doWide(false);
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.waitSimpleState();
        eng.lock();

        if (NEED_BLOW_CAR) {
            EventsControllerHelper.messageEventHappened("blowcar");
        }

        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    private static final class Data {
        actorveh car;
    }
}


//~ Formatted in DD Std on 13/08/28
