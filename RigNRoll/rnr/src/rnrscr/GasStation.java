/*
 * @(#)GasStation.java   13/08/28
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
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.IScriptTask;
import rnr.src.rnrcore.SCRcamera;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class GasStation {
    private static final String[] ANIMS = { "IvanOnGas001", "IvanOnGas002", "IvanOnGas003" };
    private static final int SCENE1 = 0;
    private static final int SCENE2 = 1;
    private static final int SCENE3 = 2;
    private static final String ANM_SPACE = "space_GlobalSRT";
    private static final String MODEL_SPACE = "space_IVAN_NEWGlobalSRT";
    private static final String[] ANIM_ITEMS = { "GasKard001", "GasKard001" };
    private static final String[] ANIM_ITEMS_ANIMS = { "GasKard001Ivan", "GasKard001Ivan2" };
    private static final int ITEM_SCENE1 = 0;
    private static final int ITEM_SCENE2 = 1;
    private actorveh car;
    private SCRuniperson person;
    private long task;
    private SCRcamera cam1;
    private SCRcamera cam_anims;

    /**
     * Method description
     *
     *
     * @param nom_pump
     * @param posit
     * @param dirit
     */
    public void runGasStationScript(int nom_pump, vectorJ posit, vectorJ dirit) {
        camscripts.buffercamturnon();

        aiplayer actor = Crew.getIgrok();

        this.car = Crew.getIgrokCar();
        this.person = actor.getModel();

        if (nom_pump >= 5) {
            nom_pump = 4;
        }

        String carprefix = eng.GetVehiclePrefix(this.car.getCar());
        String personprefix = eng.GetManPrefix(this.person.nativePointer);

        this.task = eng.CreateTASK(this.person);

        long mixtask = eng.AddMergeAnimPositioningTASK_CarWheel_start(this.task, this.car, this.person, posit, dirit,
                           personprefix + "_out" + carprefix, ANIMS[0], "space_GlobalSRT", "space_IVAN_NEWGlobalSRT",
                           0.5D);
        long mixtaskin = eng.AddMergeAnimPositioningTASK_CarWheel_finish(this.task, posit, dirit, this.car,
                             this.person, ANIMS[1], personprefix + "_in" + carprefix, "space_GlobalSRT",
                             "space_IVAN_NEWGlobalSRT", 0.5D);
        long second = eng.AddAnimTASK(this.task, ANIMS[0], 0.0D);
        long loopstay = eng.AddAnimTASKLoop(this.task, ANIMS[2], 0.0D);
        long third = eng.AddEventTask(this.task);
        long forth = eng.AddAnimTASK(this.task, ANIMS[1], 0.0D);
        long gasmenu = eng.AddScriptTask(this.task, new CreateMenu());
        long realesecar = eng.AddScriptTask(this.task, new ReleaseCar());
        long kard1 = eng.AddScriptTask(this.task, new CreateAnimatedItem(0));
        long kard2 = eng.AddScriptTask(this.task, new CreateAnimatedItem(1));
        long deletekard1 = eng.AddScriptTask(this.task, new DeleteAnimatedItem(0));
        long deletekard2 = eng.AddScriptTask(this.task, new DeleteAnimatedItem(1));
        long door1 = eng.AddScriptTask(this.task, drvscripts.animatedDoorOut(this.person, this.car));
        long deletedoor1 = eng.AddScriptTask(this.task, drvscripts.deleteAnimatedDoorOut(this.person, this.car));
        long door2 = eng.AddScriptTask(this.task, drvscripts.animatedDoorIn(this.person, this.car));
        long deletedoor2 = eng.AddScriptTask(this.task, drvscripts.deleteAnimatedDoorIn(this.person, this.car));

        this.cam1 = SCRcamera.CreateCamera("anm");

        matrixJ mrot = new matrixJ();

        mrot.v0.Set(0.1D, 0.0D, 0.0D);
        mrot.v1.Set(0.0D, 0.0D, 0.1D);
        mrot.v2.Set(0.0D, -0.1D, 0.0D);

        matrixJ mrotcam = new matrixJ();

        mrotcam.v0.Set(1.0D, 0.0D, 0.0D);
        mrotcam.v1.Set(0.0D, 1.0D, 0.0D);
        mrotcam.v2.Set(0.0D, 0.0D, 1.0D);

        String camname1 = "camera_" + nom_pump + "pos1";
        String camname2 = "camera_" + nom_pump + "pos2";
        String camname3 = "camera_gen_pass1";
        String camname4 = "camera_gen_pass2";
        String camname5 = "camera_gen1";
        String camname6 = "camera_gen2";
        String camname7 = "camera_gen3";

        this.cam1.AddAnimation(camname1, "space_MDL-" + camname1, mrot, mrotcam);
        this.cam1.AddAnimation(camname2, "space_MDL-" + camname2, mrot, mrotcam);
        this.cam1.AddAnimation(camname3, "space_MDL-" + camname3, mrot, mrotcam);
        this.cam1.AddAnimation(camname4, "space_MDL-" + camname4, mrot, mrotcam);
        this.cam1.AddAnimation(camname5, "space_MDL-" + camname5, mrot, mrotcam);
        this.cam1.AddAnimation(camname6, "space_MDL-" + camname6, mrot, mrotcam);
        this.cam1.AddAnimation(camname7, "space_MDL-" + camname7, mrot, mrotcam);
        this.cam1.BindToGasStation(eng.GetCurrentGasStation());

        long camera1 = eng.AddScriptTask(this.task, camscripts.playCamera(this.cam1));
        long deleteall_andvehcam = eng.AddScriptTask(this.task, new FinishTask());

        this.cam_anims = SCRcamera.CreateCamera("anm");
        this.cam_anims.SetPlayConsecutively(true);
        this.cam_anims.SetPlayCycleAndConsecutively(true);

        String cam_animname = "";
        int choose = animation.RandomFromNom(4);

        choose = 4;

        switch (choose) {
         case 1 :
             cam_animname = "camera_RUL2";

             break;

         case 2 :
             cam_animname = "camera_RUL3";

             break;

         case 3 :
             cam_animname = "camera_RUL4";

             break;

         case 4 :
             cam_animname = "camera_RUL5";
        }

        matrixJ mrot_CA = new matrixJ();

        mrot_CA.v0.Set(0.0D, 0.1D, 0.0D);
        mrot_CA.v1.Set(0.0D, 0.0D, 0.1D);
        mrot_CA.v2.Set(0.1D, 0.0D, 0.0D);

        matrixJ mrotcam_CA = new matrixJ();

        mrotcam_CA.v0.Set(0.0D, 0.0D, -1.0D);
        mrotcam_CA.v1.Set(0.0D, 1.0D, 0.0D);
        mrotcam_CA.v2.Set(1.0D, 0.0D, 0.0D);
        this.cam_anims.AddAnimation(cam_animname, "space_MDL-" + cam_animname, mrot_CA, mrotcam_CA);

        long camera_ANIMATION = eng.AddScriptTask(this.task,
                                    camscripts.playCamera_bindSteerWheel(this.cam_anims, this.car));
        long camera_ANIMATION_play = eng.AddScriptTask(this.task, camscripts.playCamera(this.cam_anims));
        long stopcamera_ANIMATION = eng.AddScriptTask(this.task, camscripts.stopCamera(this.cam_anims));
        long endscene = eng.AddEventTask(this.task);
        long justcametopump = eng.AddEventTask(this.task);

        eng.EventTask_onGasStationLeave(endscene, false);
        eng.EventTask_onGasStationCame(justcametopump, false);
        eng.EventTask_onGasStationMessageClosed(third, false);
        eng.OnBegTASK(justcametopump, "play", door1);
        eng.OnEndTASK(justcametopump, "play", deletedoor1);
        eng.OnEndTASK(justcametopump, "play", mixtask);
        eng.OnEndTASK(justcametopump, "play", second);

        SCRcamera cOutCar = SCRcamera.CreateCamera("anm");
        String _cam_animname = "camera_RUL2";
        matrixJ _mrot_CA = new matrixJ();

        _mrot_CA.Set0(0.0D, 0.1D, 0.0D);
        _mrot_CA.Set1(0.0D, 0.0D, 0.1D);
        _mrot_CA.Set2(0.1D, 0.0D, 0.0D);

        matrixJ _mrotcam_CA = new matrixJ();

        _mrotcam_CA.Set0(0.0D, 0.0D, -1.0D);
        _mrotcam_CA.Set1(0.0D, 1.0D, 0.0D);
        _mrotcam_CA.Set2(1.0D, 0.0D, 0.0D);
        cOutCar.AddAnimation(_cam_animname, "space_MDL-" + _cam_animname, _mrot_CA, _mrotcam_CA);

        SCRcamera cInCar = SCRcamera.CreateCamera("anm");

        _cam_animname = "camera_RUL2";
        _mrot_CA = new matrixJ();
        _mrot_CA.Set0(0.0D, 0.1D, 0.0D);
        _mrot_CA.Set1(0.0D, 0.0D, 0.1D);
        _mrot_CA.Set2(0.1D, 0.0D, 0.0D);
        _mrotcam_CA = new matrixJ();
        _mrotcam_CA.Set0(0.0D, 0.0D, -1.0D);
        _mrotcam_CA.Set1(0.0D, 1.0D, 0.0D);
        _mrotcam_CA.Set2(1.0D, 0.0D, 0.0D);
        cInCar.AddAnimation(_cam_animname, "space_MDL-" + _cam_animname, _mrot_CA, _mrotcam_CA);

        long COUTCA = eng.AddScriptTask(this.task, camscripts.playCamera_bindSteerWheel(cOutCar, this.car));
        long deleteCOUTCAR = eng.AddScriptTask(this.task, camscripts.deleteCamera(cOutCar));
        long CINCA = eng.AddScriptTask(this.task, camscripts.playCamera_bindSteerWheel(cInCar, this.car));
        long turnoffbuffercam = eng.AddScriptTask(this.task, camscripts.buffercamturn(false));
        long deleteCINCAR = eng.AddScriptTask(this.task, camscripts.deleteCamera(cInCar));

        eng.OnEndTASK(justcametopump, "play", stopcamera_ANIMATION);
        eng.OnEndTASK(justcametopump, "play", COUTCA);
        eng.OnEndTASK(mixtask, "play", kard1);
        eng.OnEndTASK(second, "play", deleteCOUTCAR);
        eng.OnEndTASK(second, "play", camera1);
        eng.OnEndTASK(second, "play", deletekard1);
        eng.OnEndTASK(second, "play", loopstay);
        eng.OnMidTASK(second, 2.0D, 2.0D, "play", gasmenu);
        eng.OnEndTASK(third, "end", second);
        eng.OnEndTASK(third, "play", camera_ANIMATION_play);
        eng.OnEndTASK(third, "play", forth);
        eng.OnEndTASK(third, "play", kard2);
        eng.OnBegTASK(forth, "end", loopstay);
        eng.OnEndTASK(forth, "play", CINCA);
        eng.OnEndTASK(forth, "play", deletekard2);
        eng.OnEndTASK(forth, "play", endscene);
        eng.OnEndTASK(forth, "play", mixtaskin);
        eng.OnEndTASK(forth, "play", realesecar);
        eng.OnBegTASK(forth, "play", door2);
        eng.OnEndTASK(forth, "play", deletedoor2);
        eng.OnEndTASK(forth, "play", turnoffbuffercam);
        eng.OnEndTASK(forth, "play", deleteCINCAR);
        eng.OnEndTASK(forth, "play", endscene);
        eng.OnEndTASK(endscene, "play", deleteall_andvehcam);
        eng.OnEndTASK(justcametopump, "play", second);
        eng.playTASK(camera_ANIMATION);
        eng.playTASK(justcametopump);
        eng.playTASK(third);
    }

    class CreateAnimatedItem implements IScriptTask {
        private final int scene;

        CreateAnimatedItem(int paramInt) {
            this.scene = paramInt;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            GasStation.this.person.CreateAnimatedItem(GasStation.ANIM_ITEMS[this.scene],
                    GasStation.ANIM_ITEMS_ANIMS[this.scene], 0.0D);
        }
    }


    static class CreateMenu implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            menues.CreateGASSTATIONMENU();
        }
    }


    class DeleteAnimatedItem implements IScriptTask {
        private final int scene;

        DeleteAnimatedItem(int paramInt) {
            this.scene = paramInt;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            GasStation.this.person.DeleteAnimatedItem(GasStation.ANIM_ITEMS[this.scene],
                    GasStation.ANIM_ITEMS_ANIMS[this.scene]);
        }
    }


    class FinishTask implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            camscripts.deleteCamera(GasStation.this.cam1).launch();
            camscripts.deleteCamera(GasStation.this.cam_anims).launch();
            eng.DeleteTASK(GasStation.this.task);
            camscripts.buffercamturnon();
        }
    }


    class ReleaseCar implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            GasStation.this.car.leaveParking();
        }
    }
}


//~ Formatted in DD Std on 13/08/28
