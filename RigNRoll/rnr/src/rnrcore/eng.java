/*
 * @(#)eng.java   13/08/26
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

import rnr.src.players.CarName;
import rnr.src.players.actorveh;
import rnr.src.rnrloggers.ScriptsLogger;
import rnr.src.rnrscenario.consistency.ScenarioStage;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class eng {

    /** Field description */
    public static final String TASK_UNSUSPEND = "unsuspend";

    /** Field description */
    public static final String TASK_PLAY = "play";

    /** Field description */
    public static final String TASK_END = "end";

    /** Field description */
    public static final String TASK_FADEIN = "fadein";

    /** Field description */
    public static final int DIFFICULTY_EASY = 0;

    /** Field description */
    public static final int DIFFICULTY_MEDIUM = 1;

    /** Field description */
    public static final int DIFFICULTY_HARD = 2;

    /** Field description */
    public static boolean canShowPagerMessages = true;

    /** Field description */
    public static boolean noNative = false;

    /** Field description */
    public static boolean _12HourFormat = false;
    private static final int DELAY_BETWEEN_POLLS = 1000;
    private static final Object engineLockLatch;
    private static int engineLockCounter;

    /** Field description */
    public static boolean CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE;

    /** Field description */
    public static boolean ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE;

    static {
        try {
            System.loadLibrary("rnr");
        } catch (UnsatisfiedLinkError e) {
            Properties prop = System.getProperties();

            System.err.println("java.library.path: " + prop.getProperty("java.library.path"));
            System.err.println("Cannot link rnr");
        }

        engineLockLatch = new Object();
        engineLockCounter = 0;
        CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = true;
        ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = true;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setUse_12HourTimeFormat(boolean value) {
        _12HourFormat = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean useNative() {
        return (!(noNative));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean use_12HourTimeFormat() {
        return _12HourFormat;
    }

    /**
     * Method description
     *
     *
     * @param message
     */
    public static void pager(String message) {
        if (!(canShowPagerMessages)) {
            return;
        }

        CreatePagerMessage(message);
    }

    /**
     * Method description
     *
     *
     * @param errcode
     */
    public static void err(String errcode) {
        writeLog("eng.err " + errcode);
    }

    /**
     * Method description
     *
     *
     * @param errcode
     */
    public static void log(String errcode) {
        writeLog("eng.log " + errcode);
    }

    private static native void assertInLog(String paramString);

    private static native void CreatePagerMessage(String paramString);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public static native void fatal(String paramString);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     */
    public static native void executeScript(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramanm
     */
    public static native void CreateInfinitScriptAnimation(anm paramanm);

    /**
     * Method description
     *
     *
     * @param paramanm
     * @param paramDouble
     */
    public static native void CreateInfinitCycleScriptAnimation(anm paramanm, double paramDouble);

    /**
     * Method description
     *
     *
     * @param str
     */
    public static void writeLog(String str) {
        if (noNative) {
            System.err.println(str);
        } else {
            assertInLog(str);
        }
    }

    /**
     * Method description
     *
     *
     * @param paramSCRperson
     */
    public static native void play(SCRperson paramSCRperson);

    /**
     * Method description
     *
     *
     * @param paramSCRperson
     * @param paramBoolean
     */
    public static native void setShow(SCRperson paramSCRperson, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @return
     */
    public static native int RecieveEvent();

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void playTASK(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramSCRuniperson
     *
     * @return
     */
    public static native long CreateTASK(SCRuniperson paramSCRuniperson);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void DeleteTASK(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramDouble
     *
     * @return
     */
    public static native long AddAnimTASKLoop(long paramLong, String paramString, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramDouble
     *
     * @return
     */
    public static native long AddAnimTASK(long paramLong, String paramString, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramDouble
     *
     * @return
     */
    public static native long AddAnimInversTASK(long paramLong, String paramString, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramDouble
     * @param paramBoolean
     *
     * @return
     */
    public static native long AddAnimTASKLoop(long paramLong, String paramString, double paramDouble,
            boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramDouble
     * @param paramBoolean
     *
     * @return
     */
    public static native long AddAnimTASK(long paramLong, String paramString, double paramDouble, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramDouble
     * @param paramBoolean
     *
     * @return
     */
    public static native long AddAnimInversTASK(long paramLong, String paramString, double paramDouble,
            boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     * @param paramDouble1
     * @param paramDouble2
     * @param paramDouble3
     * @param paramDouble4
     *
     * @return
     */
    public static native long AddAnimMixTASK(long paramLong, String paramString1, String paramString2,
            double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramvectorJ1
     * @param paramvectorJ2
     * @param paramvectorJ3
     * @param paramvectorJ4
     * @param paramString1
     * @param paramString2
     * @param paramString3
     * @param paramString4
     * @param paramDouble
     *
     * @return
     */
    public static native long AddMergeAnimPositioningTASK(long paramLong, vectorJ paramvectorJ1, vectorJ paramvectorJ2,
            vectorJ paramvectorJ3, vectorJ paramvectorJ4, String paramString1, String paramString2,
            String paramString3, String paramString4, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramactorveh
     * @param paramSCRuniperson
     * @param paramvectorJ1
     * @param paramvectorJ2
     * @param paramString1
     * @param paramString2
     * @param paramString3
     * @param paramString4
     * @param paramDouble
     *
     * @return
     */
    public static native long AddMergeAnimPositioningTASK_CarWheel_start(long paramLong, actorveh paramactorveh,
            SCRuniperson paramSCRuniperson, vectorJ paramvectorJ1, vectorJ paramvectorJ2, String paramString1,
            String paramString2, String paramString3, String paramString4, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramvectorJ1
     * @param paramvectorJ2
     * @param paramactorveh
     * @param paramSCRuniperson
     * @param paramString1
     * @param paramString2
     * @param paramString3
     * @param paramString4
     * @param paramDouble
     *
     * @return
     */
    public static native long AddMergeAnimPositioningTASK_CarWheel_finish(long paramLong, vectorJ paramvectorJ1,
            vectorJ paramvectorJ2, actorveh paramactorveh, SCRuniperson paramSCRuniperson, String paramString1,
            String paramString2, String paramString3, String paramString4, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void Anim_SetCamera(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramString
     * @param paramLong2
     */
    public static native void OnEndTASK(long paramLong1, String paramString, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramString
     * @param paramLong2
     */
    public static native void OnBegTASK(long paramLong1, String paramString, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramDouble1
     * @param paramDouble2
     * @param paramString
     * @param paramLong2
     */
    public static native void OnMidTASK(long paramLong1, double paramDouble1, double paramDouble2, String paramString,
            long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native long AddEventTask(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native long AddHasteTask(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void AddSceneTrackToTask(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramactorveh
     * @param paramBoolean
     */
    public static native void EventTask_onParking(long paramLong, actorveh paramactorveh, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onBARMessageClosed(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onMOTELMessageClosed(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onMOTELDevelopFinish(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onNewCarCreated(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onOfficeMessageClosed(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onOfficeBankrupt(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onGasStationMessageClosed(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onGasStationLeave(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onGasStationCame(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onWhLeaveGates(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onWhCame(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onWhLeave(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onWhLoad(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onWhUnLoad(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onWhGoFromEmpty(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_onWhGoFromLoaded(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_ExitGame(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_AnimationEventIn(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void EventTask_AnimationEventOut(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramIScriptTask
     *
     * @return
     */
    public static native long AddScriptTask(long paramLong, IScriptTask paramIScriptTask);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native long AddChangeWorldTask(long paramLong, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramvectorJ
     */
    public static native void ConvertVector_Vehicle(long paramLong, vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native vectorJ GetVehicle_steeringwheel_pos(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native vectorJ GetVehicle_steeringwheel_dir(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native vectorJ GetVehicle_pos(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native vectorJ GetVehicle_dir(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native long GetVehicle(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native long GetVehicleDriver(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native long CarNode(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetVehiclePrefix(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetVehicleNodeName(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetManPrefix(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void SwitchDriver_in_cabin(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void SwitchDriver_outside_cabin(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void SwitchDriver_nevermind(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native double GetAnimationLength(long paramLong);

    /**
     * Method description
     *
     *
     * @return
     */
    public static native long GetCurrentWarehouse();

    /**
     * Method description
     *
     *
     * @return
     */
    public static native long GetCurrentBar();

    /**
     * Method description
     *
     *
     * @return
     */
    public static native long GetCurrentGasStation();

    /**
     * Method description
     *
     *
     * @return
     */
    public static native long GetCurrentRepairShop();

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetBarInnerName(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetBarOutterName(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetWarehouseInnerName(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetWarehouseOutterName(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetGasStationInnerName(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetGasStationOutterName(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetRepairShopInnerName(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetRepairShopOutterName(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     *
     * @return
     */
    public static native vectorJ setCurrentOfficeNear(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramString
     *
     * @return
     */
    public static native vectorJ getNamedOfficePosition(String paramString);

    /**
     * Method description
     *
     *
     * @return
     */
    public static native int GetXResolution();

    /**
     * Method description
     *
     *
     * @return
     */
    public static native int GetYResolution();

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     */
    public static native void SetCrashBind_DO(long paramLong, int paramInt1, int paramInt2, int paramInt3);

    private static native actorveh CreateCarForPredefinedAnimation(String paramString, int paramInt,
            matrixJ parammatrixJ, vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param carModelMame
     * @param matrix
     * @param position
     *
     * @return
     */
    public static actorveh CreateCarForScenario(CarName carModelMame, matrixJ matrix, vectorJ position) {
        Log.simpleMessage("loading car for scenario");

        actorveh ware = CreateCarForPredefinedAnimation(carModelMame.getName(), carModelMame.getColor(), matrix,
                            position);

        ware.UpdateCar();

        return ware;
    }

    /**
     * Method description
     *
     *
     * @param paramScenarioStage
     */
    public static native void scenarioCheckPointReached(ScenarioStage paramScenarioStage);

    /**
     * Method description
     *
     *
     * @param paramString
     * @param parammatrixJ
     * @param paramvectorJ
     *
     * @return
     */
    @Deprecated
    public static native actorveh CreateCarImmediatly(String paramString, matrixJ parammatrixJ, vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramvectorJ
     *
     * @return
     */
    @Deprecated
    public static native actorveh CreateCar_onway(String paramString, vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramString3
     *
     * @return
     */
    public static native actorveh CreateAnimatedCar(String paramString1, String paramString2, String paramString3);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void BlowCar(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public static native void console(String paramString);

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public static native void doWide(boolean paramBoolean);

    private static native void setEscKeyEnabled(boolean paramBoolean);

    /**
     * Method description
     *
     */
    public static void enableEscKeyScenesSkip() {
        setEscKeyEnabled(true);
    }

    /**
     * Method description
     *
     */
    public static void disableEscKeyScenesSkip() {
        setEscKeyEnabled(false);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static native int gYear();

    /**
     * Method description
     *
     *
     * @return
     */
    public static native int gMonth();

    /**
     * Method description
     *
     *
     * @return
     */
    public static native int gDate();

    /**
     * Method description
     *
     *
     * @return
     */
    public static native int gHour();

    /**
     * Method description
     *
     *
     * @return
     */
    public static native int gMinuten();

    /**
     * Method description
     *
     */
    public static void lock() {
        synchronized (engineLockLatch) {
            if (0 == engineLockCounter) {
                lockEngine();
            }

            engineLockCounter += 1;
        }
    }

    /**
     * Method description
     *
     */
    public static void unlock() {
        synchronized (engineLockLatch) {
            if (0 == engineLockCounter) {
                return;
            }

            engineLockCounter -= 1;

            if (0 == engineLockCounter) {
                unlockEngine();
            }
        }
    }

    /**
     * Method description
     *
     */
    public static native void forceInterruptLock();

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public static native void predefinedAnimationLaunchedFromJava(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public static native void explosionsWhilePredefinedAnimation(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @return
     */
    public static native boolean canRunScenarioAnimation();

    /**
     * Method description
     *
     *
     * @return
     */
    public static native boolean gameWorldStopped();

    /**
     * Method description
     *
     *
     * @throws InterruptedException
     */
    public static void waitUntilEngineCanPlayScene() throws InterruptedException {
        if ((CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE) && (ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE)) {
            lock();

            while (!(canRunScenarioAnimation())) {
                ScriptsLogger.getInstance().log(Level.INFO, 4, "waiting for engine to enable play animation");
                unlock();
                Thread.sleep(1000L);
                lock();
            }

            predefinedAnimationLaunchedFromJava(true);
            unlock();
        }

        ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = true;
    }

    private static native void lockEngine();

    private static native void unlockEngine();

    /**
     * Method description
     *
     *
     * @return
     */
    public static native boolean introPlayingFirstTime();

    /**
     * Method description
     *
     */
    public static native void switchOffIntro();

    /**
     * Method description
     *
     */
    public static native void blockEscapeMenu();

    /**
     * Method description
     *
     */
    public static native void exceptionDuringGameSerialization();

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native String getStringRef(String paramString1, String paramString2);

    /**
     * Method description
     *
     */
    public static native void reloadScriptObjectsUids();

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public static native void holdresourse(String paramString);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public static native void looseresourse(String paramString);

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramInt
     */
    public static native void setdword(String paramString, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramString
     *
     * @return
     */
    public static native vectorJ getControlPointPosition(String paramString);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    @Deprecated
    public static native void markPointOnMap(vectorJ paramvectorJ);

    /**
     * Method description
     *
     */
    @Deprecated
    public static native void removeMarksOnMap();

    /**
     * Method description
     *
     */
    public static native void enableControl();

    /**
     * Method description
     *
     */
    public static native void disableControl();

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public static native void pauseGameExceptPredefineAnimation(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public static native void enableCabinView(boolean paramBoolean);

    /**
     * Method description
     *
     */
    public static native void returnCameraToGameWorld();

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramVector
     */
    public static native void getFilesAllyed(String paramString, Vector paramVector);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void blockSO(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void unblockSO(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramString
     */
    public static native void blockNamedSO(int paramInt, String paramString);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramString
     */
    public static native void unblockNamedSO(int paramInt, String paramString);

    /**
     * Method description
     *
     */
    public static native void startMangedFadeAnimation();

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public static native void makePoliceImmunity(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @return
     */
    public static native int getDifficultyLevel();

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     */
    public static native void setParallelWorldCondition(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramDouble1
     * @param paramDouble2
     * @param paramDouble3
     * @param paramString
     */
    public static native void makePayOff(double paramDouble1, double paramDouble2, double paramDouble3,
            String paramString);

    /**
     * Method description
     *
     *
     * @param paramString
     *
     * @return
     */
    public static native boolean dontQuestItemLost(String paramString);
}


//~ Formatted in DD Std on 13/08/26
