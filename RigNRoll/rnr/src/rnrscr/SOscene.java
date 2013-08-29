/*
 * @(#)SOscene.java   13/08/28
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


package rnr.src.rnrscr;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.IScriptTask;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneMacroPairs;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.scenetrack;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class SOscene {
    private static final int INSIDE_ANIMATION = 0;
    private static final int OUTSIDE_ANIMATION = 1;
    private static final int NUM_OUT_CAMERA_TRACKS = 9;
    private static final int NUM_IN_CAMERA_TRACKS = 14;
    private static final String CAMERA_OUT_SCENE_NAME = "driver_coming_out";
    private static final String CAMERA_IN_SCENE_NAME = "driver_coming_in";
    private static final String MACRO_NOM = "NOM";
    private static final String MACRO_CAR_MODEL_NAME = "CAR_PREFIX";
    private static final String MACRO_MODEL_NAME = "MODEL_NAME";
    private static boolean chooseOutSceneFromNumber = false;
    private static boolean chooseInSceneFromNumber = false;
    private static int outSceneNumber = 0;
    private static int inSceneNumber = 0;

    /** Field description */
    public long task;

    /** Field description */
    public SCRuniperson person;

    /** Field description */
    public aiplayer actor;

    /** Field description */
    public actorveh vehicle;
    animation AA;

    /**
     * Constructs ...
     *
     */
    public SOscene() {
        this.AA = new animation();
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setOutSceneNum(int value) {
        chooseOutSceneFromNumber = value > 0;
        outSceneNumber = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setInSceneNum(int value) {
        chooseInSceneFromNumber = value > 0;
        inSceneNumber = value;
    }

    static SceneInOutPresets getOutSceneName(String modelName, String carNodeName) {
        int nomCameraScene = (chooseOutSceneFromNumber)
                             ? outSceneNumber
                             : AdvancedRandom.RandFromInreval(1, 9);
        SceneInOutPresets presets = new SceneInOutPresets("driver_coming_out", modelName, carNodeName, nomCameraScene);

        return presets;
    }

    static SceneInOutPresets getInSceneName(String modelName, String carNodeName) {
        int nomCameraScene = (chooseInSceneFromNumber)
                             ? inSceneNumber
                             : AdvancedRandom.RandFromInreval(1, 14);
        SceneInOutPresets presets = new SceneInOutPresets("driver_coming_in", modelName, carNodeName, nomCameraScene);

        return presets;
    }

    @SuppressWarnings("unused")
    private static long createCameraInOutTrack(SceneInOutPresets scenePresets, actorveh car) {
        CarData data = new CarData();

        data.car = car;
        car.registerCar("carinout");

        SceneMacroPairs macros = SceneMacroPairs.create();

        macros.addPair("NOM", "" + scenePresets.nomCameraTrack);
        macros.addPair("CAR_PREFIX", scenePresets.carPrefix);
        macros.addPair("MODEL_NAME", scenePresets.modelName);

        return scenetrack.CreateSceneXML(scenePresets.sceneName, 2, data, macros.getMacroPairs());
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public long waitParkingEvent() {
        long CARARRIVED = eng.AddEventTask(this.task);

        eng.EventTask_onParking(CARARRIVED, this.vehicle, false);

        return CARARRIVED;
    }

    /**
     * Method description
     *
     *
     * @param CARARRIVED
     * @param usefadeonend
     *
     * @return
     */
    public CarInOutTasks makecaroutOnEnd(long CARARRIVED, boolean usefadeonend) {
        usefadeonend = false;

        CarInOutTasks res = makecarout(false);

        eng.OnEndTASK(CARARRIVED, "play", res.getMTaskToStart());

        if (usefadeonend) {
            long fadein = eng.AddAnimTASK(this.task, "fadein", 0.0D);

            eng.OnEndTASK(res.getMTaskToWait(), "play", fadein);
            res.setMTaskToWait(fadein);
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param CARARRIVED
     * @param usefadeonend
     *
     * @return
     */
    public CarInOutTasks makecaroutOnStart(long CARARRIVED, boolean usefadeonend) {
        usefadeonend = false;

        CarInOutTasks res = makecarout(false);

        eng.OnBegTASK(CARARRIVED, "play", res.getMTaskToStart());

        if (usefadeonend) {
            long fadein = eng.AddAnimTASK(this.task, "fadein", 0.0D);

            eng.OnEndTASK(res.getMTaskToWait(), "play", fadein);
            res.setMTaskToWait(fadein);
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param CARARRIVED
     *
     * @return
     */
    public CarInOutTasks makecarinOnEnd(long CARARRIVED) {
        CarInOutTasks res = makecarin();

        eng.OnEndTASK(CARARRIVED, "play", res.getMTaskToStart());

        return res;
    }

    /**
     * Method description
     *
     *
     * @param CARARRIVED
     *
     * @return
     */
    public CarInOutTasks makecarinOnStart(long CARARRIVED) {
        CarInOutTasks res = makecarin();

        eng.OnBegTASK(CARARRIVED, "play", res.getMTaskToStart());

        return res;
    }

    /**
     * Method description
     *
     *
     * @param EVENTTASK
     *
     * @return
     */
    public long restorecameratoCarOnEnd(long EVENTTASK) {
        long RestoreCameraToCar = eng.AddScriptTask(this.task, camscripts.restore_Camera_to_igrok_car());

        eng.OnEndTASK(EVENTTASK, "play", RestoreCameraToCar);

        return RestoreCameraToCar;
    }

    /**
     * Method description
     *
     *
     * @param EVENTTASK
     *
     * @return
     */
    public long restorecameratoCarOnBegin(long EVENTTASK) {
        long RestoreCameraToCar = eng.AddScriptTask(this.task, camscripts.restore_Camera_to_igrok_car());

        eng.OnBegTASK(EVENTTASK, "play", RestoreCameraToCar);

        return RestoreCameraToCar;
    }

    private CarInOutTasks makecarout(boolean usefadeonend) {
        long endTask = eng.AddEventTask(this.task);
        long person_ingame = eng.AddScriptTask(this.task, new PlacePersonInGame(this.person));
        String carPrefix = eng.GetVehiclePrefix(this.vehicle.getCar());
        long out_cabin = eng.AddScriptTask(this.task, drvscripts.outCabinState(this.vehicle));
        long fadein = 0L;
        long animEvent = eng.AddEventTask(this.task);

        eng.EventTask_AnimationEventOut(animEvent, false);

        CameraSceneManager sceneCameraOut = new CameraSceneManager(1, this.task, this.actor.gModelname(), carPrefix);
        long COUTCA = eng.AddScriptTask(this.task, sceneCameraOut.getCreateSceneScriptTask());
        long deleteCOUTCAR = eng.AddScriptTask(this.task, sceneCameraOut);

        eng.OnBegTASK(COUTCA, "play", person_ingame);
        eng.OnBegTASK(COUTCA, "play", out_cabin);

        if (usefadeonend) {
            fadein = eng.AddAnimTASK(this.task, "fadein", 0.0D);
            eng.OnEndTASK(animEvent, "play", fadein);
            eng.OnEndTASK(fadein, "play", deleteCOUTCAR);
        } else {
            eng.OnEndTASK(animEvent, "play", deleteCOUTCAR);
            eng.OnEndTASK(animEvent, "end", endTask);
        }

        return new CarInOutTasks(COUTCA, endTask);
    }

    private CarInOutTasks makecarin() {
        long endTask = eng.AddEventTask(this.task);
        long person_ingame = eng.AddScriptTask(this.task, new PlacePersonInGame(this.person));
        String carPrefix = eng.GetVehiclePrefix(this.vehicle.getCar());
        long in_cabin = eng.AddScriptTask(this.task, drvscripts.inCabinState(this.vehicle));
        long turnoffbuffercam = eng.AddScriptTask(this.task, camscripts.buffercamturn(false));
        long turnonbuffercam = eng.AddScriptTask(this.task, camscripts.buffercamturn(true));
        long animEvent = eng.AddEventTask(this.task);

        eng.EventTask_AnimationEventIn(animEvent, false);

        CameraSceneManager sceneCameraIn = new CameraSceneManager(0, this.task, this.actor.gModelname(), carPrefix);
        long CINCA = eng.AddScriptTask(this.task, sceneCameraIn.getCreateSceneScriptTask());
        long deleteCINCAR = eng.AddScriptTask(this.task, sceneCameraIn);

        eng.OnBegTASK(CINCA, "play", person_ingame);
        eng.OnEndTASK(CINCA, "play", turnoffbuffercam);
        eng.OnEndTASK(CINCA, "play", in_cabin);
        eng.OnEndTASK(animEvent, "play", deleteCINCAR);
        eng.OnEndTASK(animEvent, "play", turnonbuffercam);
        eng.OnEndTASK(animEvent, "end", endTask);

        return new CarInOutTasks(CINCA, endTask);
    }

    static class CameraSceneManager implements IScriptTask {
        private SOscene.CreateAndPlayCameraScene startScene;
        private SOscene.DeleteCameraScene endScene;

        CameraSceneManager(int typeanimation, String modelName, String carNodeName) {
            this.startScene = null;
            this.endScene = null;

            switch (typeanimation) {
             case 0 :
                 this.startScene = new SOscene.CreateAndPlayCameraScene(SOscene.getInSceneName(modelName, carNodeName));

                 break;

             case 1 :
                 this.startScene = new SOscene.CreateAndPlayCameraScene(SOscene.getOutSceneName(modelName,
                         carNodeName));
            }
        }

        CameraSceneManager(int typeanimation, long task, String modelName, String carNodeName) {
            this(typeanimation, modelName, carNodeName);
            this.startScene.makeTaskScene(task);
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            this.endScene = new SOscene.DeleteCameraScene(this.startScene.getScene());
            this.endScene.launch();
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public IScriptTask getCreateSceneScriptTask() {
            return this.startScene;
        }
    }


    static class CarData {
        actorveh car;
    }


    static class CreateAndPlayCameraScene implements IScriptTask {
        private boolean useTask = false;
        private long scene;
        SOscene.SceneInOutPresets scenePresets;
        private long task;

        CreateAndPlayCameraScene(SOscene.SceneInOutPresets scenePresets) {
            this.scenePresets = scenePresets;
        }

        /**
         * Method description
         *
         *
         * @param task
         */
        public void makeTaskScene(long task) {
            this.task = task;
            this.useTask = true;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            this.scene = SOscene(this.scenePresets, Crew.getIgrokCar());
            scenetrack.UpdateSceneFlags(this.scene, 3);

            if (this.useTask) {
                eng.AddSceneTrackToTask(this.task, this.scene);
            }
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public long getScene() {
            return this.scene;
        }
    }


    static class DeleteCameraScene implements IScriptTask {
        private final long scene;

        DeleteCameraScene(long scene) {
            this.scene = scene;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            scenetrack.StopScene(this.scene);
            scenetrack.DeleteScene(this.scene);
        }
    }


    static class PlacePersonInGame implements IScriptTask {
        private final SCRuniperson person;

        PlacePersonInGame(SCRuniperson person) {
            this.person = person;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            this.person.SetInGameWorld();
        }
    }


    static class SceneInOutPresets {
        int nomCameraTrack = 1;
        String sceneName;
        String carPrefix;
        String modelName;

        SceneInOutPresets(String sceneName, String modelName, String carPrefix, int nomCameraTrack) {
            this.sceneName = sceneName;
            this.carPrefix = carPrefix;
            this.modelName = modelName;
            this.nomCameraTrack = nomCameraTrack;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
