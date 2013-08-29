/*
 * @(#)stoscene.java   13/08/28
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

import rnr.src.menu.menues;
import rnr.src.menuscript.STOmenues;
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.IScriptTask;
import rnr.src.rnrcore.SCRcamera;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.Ithreadprocedure;
import rnr.src.rnrscenario.ThreadTask;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class stoscene extends animation {
    static STOreaction reaction = new STOreaction();
    static int NOMMENONREPAIR = 1;
    private SCRcamera camera_aroundsto_inside;
    private SCRcamera camera_gofromsto_inside;
    private SCRcamera camera_back_of_vehicle;
    private final SCRcamera menuCamera;
    matrixJ scene_matr_SHOP;
    vectorJ scene_posit;
    int scene_nomgate;
    SCRuniperson repman;

    /**
     * Constructs ...
     *
     */
    public stoscene() {
        this.camera_aroundsto_inside = null;
        this.camera_gofromsto_inside = null;
        this.camera_back_of_vehicle = null;
        this.menuCamera = null;
        this.scene_matr_SHOP = null;
        this.scene_posit = null;
        this.scene_nomgate = -1;
        this.repman = null;
    }

    /**
     * Method description
     *
     */
    public void animateCamera2() {
        this.camera_aroundsto_inside.DeleteCamera();
        this.camera_aroundsto_inside = null;

        matrixJ Mrot_repaircam = new matrixJ();

        Mrot_repaircam.Set0(1.0D, 0.0D, 0.0D);
        Mrot_repaircam.Set1(0.0D, 0.0D, 1.0D);
        Mrot_repaircam.Set2(0.0D, -1.0D, 0.0D);

        matrixJ mrotcam = new matrixJ();
        SCRcamera cam1 = SCRcamera.CreateCamera("anm");

        cam1.nCamera("anm");

        if (AdvancedRandom.probability(0.5D)) {
            cam1.AddAnimation("repair_camera_1pos1", "space_MDL-camera_1pos1", Mrot_repaircam, mrotcam);
        } else {
            cam1.AddAnimation("repair_camera_1pos3", "space_MDL-camera_1pos3", Mrot_repaircam, mrotcam);
        }

        new camscripts().PlayCamera_bindSTO(cam1.nativePointer, this.scene_nomgate);
        this.camera_gofromsto_inside = cam1;
    }

    /**
     * Method description
     *
     */
    public void deleteCamera2() {
        this.camera_gofromsto_inside.DeleteCamera();
        this.camera_gofromsto_inside = null;
        Helper.restoreCameraToIgrokCar();
    }

    @SuppressWarnings("rawtypes")
    void createStoSceneTasked(STOPreset preset, Vector pool) {
        SCRuniperson person = Crew.getIgrok().getModel();
        long task = eng.CreateTASK(person);
        long arrivedEvent = eng.AddEventTask(task);
        long startEvent = eng.AddEventTask(task);
        long endEvent = eng.AddEventTask(task);
        long proceedTask = eng.AddScriptTask(task, new ProceedFromSTO());
        long deleteTask = eng.AddScriptTask(task, new DeleteTask(task));
        long menuClosedEvent = eng.AddEventTask(task);
        long listenMenuClosed = eng.AddScriptTask(task, new ListenMenuClosed(menuClosedEvent));
        long sceneTask = eng.AddScriptTask(task, new createSTOSCene(task, listenMenuClosed, preset, pool));
        long menuCameraTask = eng.AddScriptTask(task, new MenuCamera(preset));
        long deleteMenuCameraTask = eng.AddScriptTask(task, new DeleteMenuCamera());
        SOscene SC = new SOscene();

        SC.task = task;
        SC.person = person;
        SC.actor = Crew.getIgrok();
        SC.vehicle = Crew.getIgrokCar();
        eng.OnBegTASK(arrivedEvent, "end", startEvent);
        eng.OnEndTASK(startEvent, "end", arrivedEvent);

        CarInOutTasks CAR_out = SC.makecaroutOnEnd(startEvent, false);
        CarInOutTasks CAR_in = SC.makecarinOnEnd(endEvent);

        SC.restorecameratoCarOnEnd(CAR_in.getMTaskToWait());
        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", sceneTask);
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", proceedTask);
        eng.OnBegTASK(proceedTask, "play", deleteTask);
        eng.OnBegTASK(menuClosedEvent, "end", endEvent);
        eng.OnBegTASK(menuClosedEvent, "play", deleteMenuCameraTask);
        eng.OnBegTASK(listenMenuClosed, "play", menuCameraTask);
        eng.playTASK(arrivedEvent);
    }

    /**
     * Method description
     *
     */
    public void proceedScene() {
        animateCamera2();
        eng.returnCameraToGameWorld();

        actorveh ourcar = Crew.getIgrokCar();

        Repair_RealeseCar(eng.GetVehicleDriver(ourcar.getCar()));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int GetMoney() {
        if ((null != this.currentOwner) && (null != this.currentOwner.Presets())) {
            return ((STOpresets) this.currentOwner.Presets()).getMoney();
        }

        return -1;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void SetMoney(int value) {
        if ((null == this.currentOwner) || (null == this.currentOwner.Presets())) {
            return;
        }

        ((STOpresets) this.currentOwner.Presets()).setMoney(value);
    }

    /**
     * Method description
     *
     *
     * @param s
     */
    @Override
    public void StartWorkWithSO(cSpecObjects s) {
        this.currentOwner = s;

        if (null == s.Presets()) {
            STOpresets ourPresets = new STOpresets();

            ourPresets.setMoney(0);
            s.SetPresets(ourPresets);
        }

        STOpresets stopr = (STOpresets) s.Presets();

        stopr.historycomming.visit();
    }

    /**
     * Method description
     *
     *
     * @param addi
     */
    public void Repair_RealeseCar(long addi) {
        event.Setevent(2002 + (int) addi);
        event.Setevent(25002);
    }

    /**
     * Method description
     *
     *
     * @param matr_SHOP
     * @param posit
     * @param nomgate
     *
     * @return
     */
    public long RepairOffice(matrixJ matr_SHOP, vectorJ posit, int nomgate) {
        this.scene_matr_SHOP = matr_SHOP;
        this.scene_posit = posit;
        this.scene_nomgate = nomgate;
        activateSTOScene();
        createOperatorForPool();

        return this.repman.nativePointer;
    }

    /**
     * Method description
     *
     */
    public void createOperatorForPool() {
        LoadModels(NOMMENONREPAIR, 0);
        this.repman = SCRuniperson.createCutSceneAmbientPerson(getModelName(0, true), null);
        this.repman.SetInGameWorld();
        this.repman.play();
    }

    /**
     * Method description
     *
     */
    public void activateSTOScene() {
        actorveh ourcar = Crew.getIgrokCar();

        event.createScriptObject(17, new AnimateCamera1());
        event.createScriptObject(19, new PauseAnimateCamera1());
        event.createScriptObject(19, new LaunchScene(this));
        event.createScriptObject(18, new ExitScene());

        SCRcamera cam_back = SCRcamera.CreateCamera("anm");

        cam_back.nCamera("anm");

        camscripts camSCR = new camscripts();

        camSCR.AddCamera_BackOfVehicle(cam_back);
        camSCR.PlayCamera_bindSteerWheel(cam_back.nativePointer, ourcar.getCar());
        this.camera_back_of_vehicle = cam_back;
        eng.disableControl();
    }

    void fillPresetSoundStrings(STOPreset preset) {
        STOpresets stopr = (STOpresets) this.currentOwner.Presets();

        reaction.createReaction(preset, stopr.historycomming);
    }

    class AnimateCamera1 implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            stoscene.this.camera_back_of_vehicle.DeleteCamera();
            stoscene(stoscene.this, null);

            matrixJ Mrot_repaircam = new matrixJ();

            Mrot_repaircam.Set0(1.0D, 0.0D, 0.0D);
            Mrot_repaircam.Set1(0.0D, 0.0D, 1.0D);
            Mrot_repaircam.Set2(0.0D, -1.0D, 0.0D);

            matrixJ mrotcam = new matrixJ();
            SCRcamera cam1 = SCRcamera.CreateCamera("anm");

            cam1.nCamera("anm");
            cam1.SetPlayConsecutively(true);
            cam1.AddAnimation("repair_camera_1pos2", "space_MDL-camera_1pos2", Mrot_repaircam, mrotcam);
            new camscripts().PlayCamera_bindSTO(cam1.nativePointer, stoscene.this.scene_nomgate);
            stoscene(stoscene.this, cam1);
        }
    }


    class CSTOSCene implements Ithreadprocedure {
        ThreadTask safe;
        stoscene.STOPreset preset;
        Vector pool;

        /** Field description */
        public volatile long scene;

        /** Field description */
        public volatile stoscene sc;
        SCRcamera cSHOP_menu;

        @SuppressWarnings("rawtypes")
        CSTOSCene() {
            this.safe = null;
            this.preset = null;
            this.pool = new Vector();
            this.cSHOP_menu = null;
        }

        /**
         * Method description
         *
         */
        @Override
        public void call() {
            actorveh ourcar = Crew.getIgrokCar();

            new drvscripts().playOutOffCarThreaded(Crew.getIgrok(), ourcar, this.safe);
            STOmenues.createMenu();
            this.scene = trackscripts.initSceneXML(this.preset.sceneName, this.pool, this.preset);
            scenetrack.ReplaceSceneFlags(this.scene, 9);
            event.eventObject((int) this.scene, this.safe, "_resum");
            ThreadTask.create(new stoscene.CStomenupanelevent());
            this.safe._susp();
            scenetrack.DeleteScene(this.scene);
            eng.lock();
            create_menucam();
            menues.showMenu(6000);
            eng.unlock();
            event.eventObject(2001, this.safe, "_resum");
            this.safe._susp();
            eng.lock();
            delete_menucam();
            eng.unlock();
            new drvscripts().playInsideCarThreaded(Crew.getIgrok(), ourcar, this.safe);
            stoscene.this.proceedScene();
        }

        /**
         * Method description
         *
         *
         * @param safe
         */
        @Override
        public void take(ThreadTask safe) {
            this.safe = safe;
        }

        /**
         * Method description
         *
         */
        public void create_menucam() {
            matrixJ Mrot = new matrixJ();

            Mrot.Set0(-1.0D, 0.0D, 0.0D);
            Mrot.Set1(0.0D, 0.0D, 1.0D);
            Mrot.Set2(0.0D, 1.0D, 0.0D);
            this.cSHOP_menu = SCRcamera.CreateCamera("anm_cam");
            this.cSHOP_menu.AddAnimation("Root_Camera_on_repair_5", "Camera_on_repair", Mrot, new matrixJ());
            new camscripts().PlayCamera_bindMatrix(this.cSHOP_menu.nativePointer, this.preset.M, this.preset.P);
        }

        /**
         * Method description
         *
         */
        public void delete_menucam() {
            camscripts.deleteCamera(this.cSHOP_menu).launch();
            this.sc.animateCamera2();
        }
    }


    class CStomenupanelevent implements Ithreadprocedure {
        ThreadTask safe;

        CStomenupanelevent() {
            this.safe = null;
        }

        /**
         * Method description
         *
         */
        @Override
        public void call() {
            simplewaitFor(13000);
            eng.lock();
            event.Setevent(2002);
            eng.unlock();
        }

        /**
         * Method description
         *
         *
         * @param safe
         */
        @Override
        public void take(ThreadTask safe) {
            this.safe = safe;
        }

        void simplewaitFor(int timemillesec) {
            try {
                waitFor(timemillesec);
            } catch (InterruptedException e) {
                eng.writeLog("Script Error. CStomenupanelevent.");
            }
        }

        void waitFor(int timemillesec) throws InterruptedException {
            Thread.sleep(timemillesec);
        }
    }


    class DeleteMenuCamera implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            camscripts.deleteCamera(stoscene.this.menuCamera).launch();
        }
    }


    class DeleteTask implements IScriptTask {
        private final long task;

        DeleteTask(long paramLong) {
            this.task = paramLong;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            eng.DeleteTASK(this.task);
        }
    }


    static class DevelopPanelAnimation extends TypicalAnm {
        private static final double TIME = 11.5D;

        DevelopPanelAnimation() {
            eng.CreateInfinitScriptAnimation(this);
        }

        /**
         * Method description
         *
         *
         * @param dt
         *
         * @return
         */
        @Override
        public boolean animaterun(double dt) {
            if (dt >= 11.5D) {
                event.Setevent(2002);

                return true;
            }

            return false;
        }
    }


    class ExitScene implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            stoscene.this.deleteCamera2();
            eng.enableControl();
        }
    }


    class LaunchScene implements IScriptTask {
        private final stoscene sc;

        LaunchScene(stoscene paramstoscene) {
            this.sc = paramstoscene;
        }

        /**
         * Method description
         *
         */
        @Override
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public void launch() {
            stoscene.STOPreset preset = new stoscene.STOPreset();

            preset.M = stoscene.this.scene_matr_SHOP;
            preset.P = stoscene.this.scene_posit;
            stoscene.this.fillPresetSoundStrings(preset);

            SceneActorsPool poolman = new SceneActorsPool("repman", stoscene.this.repman);
            Vector pool = new Vector();

            pool.add(poolman);
            stoscene.this.createStoSceneTasked(preset, pool);
        }
    }


    class ListenMenuClosed implements IScriptTask {
        private static final String MENU_CLOSED = "menuClosed";
        private final long menuClosedEvent;

        ListenMenuClosed(long paramLong) {
            this.menuClosedEvent = paramLong;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            event.eventObject(2001, this, "menuClosed");
            Helper.placePersonToCar(Crew.getIgrok().getModel(), Crew.getIgrokCar());
        }

        /**
         * Method description
         *
         */
        public void menuClosed() {
            eng.playTASK(this.menuClosedEvent);
        }
    }


    class MenuCamera implements IScriptTask {
        private stoscene.STOPreset preset = null;

        MenuCamera(stoscene.STOPreset paramSTOPreset) {
            this.preset = paramSTOPreset;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            matrixJ Mrot = new matrixJ();

            Mrot.Set0(-1.0D, 0.0D, 0.0D);
            Mrot.Set1(0.0D, 0.0D, 1.0D);
            Mrot.Set2(0.0D, 1.0D, 0.0D);
            stoscene(stoscene.this, SCRcamera.CreateCamera("anm_cam"));
            stoscene.this.menuCamera.AddAnimation("Root_Camera_on_repair_5", "Camera_on_repair", Mrot, new matrixJ());
            new camscripts().PlayCamera_bindMatrix(stoscene.this.menuCamera.nativePointer, this.preset.M,
                    this.preset.P);
        }
    }


    class PauseAnimateCamera1 implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            stoscene.this.camera_aroundsto_inside.StopCamera();
            camscripts.stopCamera(stoscene.this.camera_aroundsto_inside).launch();
        }
    }


    class ProceedFromSTO implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            stoscene.this.proceedScene();
        }
    }


    class STOPreset {
        vectorJ P;
        matrixJ M;
        String sceneName;
        String Nic1;
        String Nic2;
        String Rep1;
        String Rep2;

        STOPreset() {
            this.P = null;
            this.M = null;
        }
    }


    class createSTOSCene implements IScriptTask {
        private static final String METHOD_ENDSCENE = "endScene";
        private stoscene.STOPreset preset = null;
        @SuppressWarnings("rawtypes")
        private Vector pool = new Vector();
        private final long task;
        private final long taskToLaunch;

        createSTOSCene(long paramLong1, long paramLong2, stoscene.STOPreset paramSTOPreset, Vector paramVector) {
            this.task = paramLong1;
            this.taskToLaunch = paramLong2;
            this.pool = paramVector;
            this.preset = preset;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            STOmenues.createMenu();
            new stoscene.DevelopPanelAnimation();

            long scene = scenetrack.CreateSceneXMLPool(this.preset.sceneName, 17, this.pool, this.preset);

            event.eventObject((int) scene, this, "endScene");
            eng.AddSceneTrackToTask(this.task, scene);
        }

        /**
         * Method description
         *
         */
        public void endScene() {
            STOmenues.showMenu();
            eng.playTASK(this.taskToLaunch);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
