/*
 * @(#)ScenarioSync.java   13/08/26
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


package rnr.src.rnrcore;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscr.SyncMonitors;
import rnr.src.scenarioUtils.Pair;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class ScenarioSync {
    private static final String SCENE_PACKAGE = "rnrscenario.scenes";
    private static ScenarioSync instance = null;
    private static final int NO_CURRENT_SCENARIO_STAGE_REQUIRED = -1;
    private final Object monitor = new Object();
    private boolean reserved = false;
    private Resumer resumer = null;
    private Integer currentScenarioStage = null;
    private String sceneToPlay = null;
    private boolean torunscript;
    private cScriptFuncs scenesPerformer;
    private Thread scriptrunningthreadrefference;

    private ScenarioSync() {
        this.resumer = new Resumer(SyncMonitors.getScenarioMonitor());
    }

    private static ScenarioSync getInstance() {
        if (null == instance) {
            instance = new ScenarioSync();

            synchronized (instance.monitor) {
                instance.torunscript = false;
                instance.scenesPerformer = new cScriptFuncs(SyncMonitors.getScenarioMonitor(), "rnrscenario.scenes");
                instance.scriptrunningthreadrefference = new Thread(instance.scenesPerformer);
                instance.scriptrunningthreadrefference.start();
            }
        }

        return instance;
    }

    /**
     * Method description
     *
     */
    public static void gameWentInMainMenu() {
        dropSceneToPlay();
        getInstance().scenesPerformer.interruptCurrentScene();
        resumeScene();
    }

    /**
     * Method description
     *
     */
    public static void resumeScene() {
        SyncMonitors.setNotificationFlag(true);
        getInstance().resumer.resume();
    }

    /**
     * Method description
     *
     */
    public static void runscript() {
        getInstance().torunscript = true;

        boolean was_reserved = getInstance().reserved;

        getInstance().reserved = true;

        if (was_reserved) {
            return;
        }

        resumeScene();
    }

    /**
     * Method description
     *
     */
    public void pausegame() {
        synchronized (this.monitor) {
            if (!(this.scenesPerformer.isWaiting())) {
                this.scenesPerformer.suspend();
            }
        }
    }

    /**
     * Method description
     *
     */
    public void resumegame() {
        synchronized (this.monitor) {
            if (this.scenesPerformer.isWaiting()) {
                SyncMonitors.setNotificationFlag(true);
                this.resumer.resume();
            }
        }
    }

    /**
     * Method description
     *
     */
    public static void interruptScriptRunningThread() {
        if (null == getInstance().scriptrunningthreadrefference) {
            return;
        }

        getInstance().scriptrunningthreadrefference.interrupt();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static Pair<String, Integer> extrudeSceneToPlay() {
        synchronized (getInstance().monitor) {
            String sceneNameToReturn = getInstance().sceneToPlay;
            Integer currentScenarioStage = getInstance().currentScenarioStage;

            getInstance().sceneToPlay = null;
            getInstance().currentScenarioStage = null;

            return new Pair(sceneNameToReturn, currentScenarioStage);
        }
    }

    /**
     * Method description
     *
     *
     * @param sceneName
     * @param currentScenarioStage
     */
    public static void setPlayScene(String sceneName, int currentScenarioStage) {
        synchronized (getInstance().monitor) {
            eng.log("setPlayScene is " + sceneName);

            if (null != sceneName) {
                getInstance().currentScenarioStage = ((0 <= currentScenarioStage)
                        ? Integer.valueOf(currentScenarioStage)
                        : null);
                getInstance().sceneToPlay = sceneName;
            }

            runscript();
        }
    }

    /**
     * Method description
     *
     */
    public static void dropSceneToPlay() {
        synchronized (getInstance().monitor) {
            getInstance().currentScenarioStage = null;
            getInstance().sceneToPlay = null;
        }
    }

    /**
     * Method description
     *
     *
     * @param sceneName
     */
    public static void setPlayScene(String sceneName) {
        setPlayScene(sceneName, -1);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean needRunScript() {
        synchronized (getInstance().monitor) {
            return getInstance().torunscript;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean isScriptReserved() {
        synchronized (getInstance().monitor) {
            return getInstance().reserved;
        }
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setReserved(boolean value) {
        synchronized (getInstance().monitor) {
            getInstance().reserved = value;
        }
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setNeedToRunScript(boolean value) {
        synchronized (getInstance().monitor) {
            getInstance().torunscript = value;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static cScriptFuncs getSceneObject() {
        return getInstance().scenesPerformer;
    }
}


//~ Formatted in DD Std on 13/08/26
