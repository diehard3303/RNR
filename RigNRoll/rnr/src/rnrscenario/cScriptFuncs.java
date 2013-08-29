/*
 * @(#)cScriptFuncs.java   13/08/28
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


package rnr.src.rnrscenario;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.ScenarioSync;
import rnr.src.rnrcore.eng;
import rnr.src.scenarioUtils.Pair;
import rnr.src.scriptEvents.EventsControllerHelper;

//~--- JDK imports ------------------------------------------------------------

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public final class cScriptFuncs implements Runnable {
    private final Object criticalSectionMonitor = new Object();
    private boolean needToSuspend = false;
    private boolean waiting = false;
    private String packageWithSceneClasses = null;
    private final Object monitor;

    /**
     * Constructs ...
     *
     *
     * @param syncronizationMonitor
     * @param scenePackage
     */
    public cScriptFuncs(Object syncronizationMonitor, String scenePackage) {
        if (null == syncronizationMonitor) {
            throw new IllegalArgumentException("syncronizationMonitor mast be valid non-null reference");
        }

        this.monitor = syncronizationMonitor;
        this.packageWithSceneClasses = scenePackage;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean toRun() {
        return ((ScenarioSync.needRunScript()) || (ScenarioSync.isScriptReserved()));
    }

    /**
     * Method description
     *
     */
    public void reset() {
        ScenarioSync.setNeedToRunScript(false);
        ScenarioSync.setReserved(false);
    }

    /**
     * Method description
     *
     */
    public void interruptCurrentScene() {
        stage.interrupt();
        eng.forceInterruptLock();
    }

    private void tryToSuspendOnce() throws InterruptedException {
        synchronized (this.monitor) {
            if (this.needToSuspend) {
                this.monitor.wait();
                this.needToSuspend = false;
            }
        }
    }

    /**
     * Method description
     *
     */
    public void suspend() {
        synchronized (this.monitor) {
            this.needToSuspend = true;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isWaiting() {
        synchronized (this.criticalSectionMonitor) {
            return this.waiting;
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        try {
            eventhandler();
            tryToSuspendOnce();
        } catch (InterruptedException e) {
            if (eng.noNative) {
                return;
            }

            eng.writeLog("Script Error: eventhandler was interrupted");
        }
    }

    void eventhandler() throws InterruptedException {
        if (toRun()) {
            reset();

            try {
                eng.waitUntilEngineCanPlayScene();

                Pair sceneToPlayParams = ScenarioSync.extrudeSceneToPlay();
                String sceneToPlay = (String) sceneToPlayParams.getFirst();
                Integer currentScenarioStage = (Integer) sceneToPlayParams.getSecond();

                if (null != sceneToPlay) {
                    try {
                        stage launchedScene = playScene(sceneToPlay, currentScenarioStage);

                        if (null == launchedScene) {
                            System.err.println("Failed to create instance of scene " + sceneToPlay
                                               + ". Possible not enough parameters to constructor.");
                        }
                    } catch (Exception exception) {
                        System.err.println("exception has occured while trying to run scene whith name == "
                                           + sceneToPlay);
                        System.err.println(exception.getLocalizedMessage());
                        exception.printStackTrace(System.err);
                    }
                }
            } finally {
                eng.predefinedAnimationLaunchedFromJava(false);
                stage.resetInterruptionStatus();
                stage.resetDebugPrePostConditions();
            }
        }

        if (!(ScenarioSync.isScriptReserved())) {
            synchronized (this.criticalSectionMonitor) {
                this.waiting = true;
            }

            synchronized (this.monitor) {
                this.monitor.wait();
            }
        }

        synchronized (this.criticalSectionMonitor) {
            this.waiting = false;
        }
    }

    private stage playScene(String sceneToPlay, Integer currentScenarioStage)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class sceneClass = Class.forName(this.packageWithSceneClasses + '.' + sceneToPlay);
        Constructor[] creationStrategies = sceneClass.getConstructors();
        stage scene = null;

        for (Constructor creator : creationStrategies) {
            if (null != currentScenarioStage) {
                if ((2 == creator.getParameterTypes().length) && (Object.class == creator.getParameterTypes()[0])
                        && (Integer.TYPE == creator.getParameterTypes()[1])) {
                    creator.setAccessible(true);
                    scene = (stage) creator.newInstance(new Object[] { this.monitor, currentScenarioStage });
                }
            } else if ((1 == creator.getParameterTypes().length) && (Object.class == creator.getParameterTypes()[0])) {
                creator.setAccessible(true);
                scene = (stage) creator.newInstance(new Object[] { this.monitor });
            }

            if (null == scene) {
                continue;
            }

            scene.playScene(this);
            EventsControllerHelper.messageEventHappened(sceneToPlay + " finished");

            break;
        }

        return scene;
    }
}


//~ Formatted in DD Std on 13/08/28
