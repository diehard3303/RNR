/*
 * @(#)SetSceneToRunAction.java   13/08/28
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


package rnr.src.scriptActions;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.ScenarioSync;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrloggers.ScenarioLogger;
import rnr.src.rnrscenario.scenarioscript;

//~--- JDK imports ------------------------------------------------------------

import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class SetSceneToRunAction extends ScenarioAction {
    String name = null;
    boolean waitEvent = false;
    private final boolean afterparking = false;
    private final boolean otherevent = false;
    private final int scenarioStageNumberForScene = -1;

    /**
     * Constructs ...
     *
     *
     * @param scenario
     */
    public SetSceneToRunAction(scenarioscript scenario) {
        super(scenario);
    }

    /**
     * Method description
     *
     */
    @Override
    public void act() {
        if (!(validate())) {
            ScenarioLogger.getInstance().machineWarning(
                "SetSceneToRun wasn't correctly initialized, scene name is null");

            return;
        }

        if (!(this.waitEvent)) {
            if (this.afterparking) {
                this.waitEvent = true;

                try {
                    event.eventObject(25001, this, "act");
                } catch (UnsatisfiedLinkError error) {
                    return;
                }

                return;
            }

            if (this.otherevent) {
                this.waitEvent = true;

                String message = "code ERROR. Unaccesiible field is filled. SetSceneToRunAction. act";

                eng.err(message);
                ScenarioLogger.getInstance().machineLog(Level.WARNING, message);

                return;
            }
        }

        if (!(eng.noNative)) {
            if (this.afterparking) {
                eng.ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = false;
            }

            if (-1 == this.scenarioStageNumberForScene) {
                ScenarioSync.setPlayScene(this.name);
            } else {
                ScenarioSync.setPlayScene(this.name, this.scenarioStageNumberForScene);
            }
        }

        ScenarioLogger.getInstance().machineLog(Level.INFO,
                "action performed " + super.getClass().getName() + " scene to play ==" + this.name);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean validate() {
        return (null != this.name);
    }
}


//~ Formatted in DD Std on 13/08/28
