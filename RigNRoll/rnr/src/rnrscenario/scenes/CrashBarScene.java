/*
 * @(#)CrashBarScene.java   13/08/26
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
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.IScriptTask;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.ScenarioSync;
import rnr.src.rnrcore.eng;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 15,
    fieldWithDesiredStage = ""
)
public class CrashBarScene {

    /** Field description */
    public static boolean DEBUG = false;

    /**
     * Method description
     *
     */
    public void run() {
        aiplayer player = Crew.getIgrok();
        SCRuniperson person = player.getModel();
        long task = eng.CreateTASK(person);
        long bar_world = eng.AddChangeWorldTask(task, "bar_crash", "cutscene");
        long startinsideanimation = eng.AddScriptTask(task, new StartScene(task));
        long dummy = eng.AddEventTask(task);

        eng.OnEndTASK(bar_world, "play", dummy);
        eng.OnMidTASK(dummy, 1.0D, 1.0D, "play", startinsideanimation);
        eng.playTASK(bar_world);
    }

    static class StartScene implements IScriptTask {
        private final long task;

        StartScene(long task) {
            this.task = task;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            if (CrashBarScene.DEBUG) {
                ScenarioSync.setPlayScene("sc02050");
                CrashBarScene.DEBUG = false;
            } else {
                EventsControllerHelper.messageEventHappened("start 2050");
            }

            eng.DeleteTASK(this.task);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
