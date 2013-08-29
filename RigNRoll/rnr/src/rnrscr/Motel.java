/*
 * @(#)Motel.java   13/08/28
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
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class Motel {
    private static final String INSIDE_ANIMATION = "ivan_in_motel";
    private actorveh car;
    private long task;
    private SCRuniperson person;
    private boolean _play_short;

    /**
     * Constructs ...
     *
     */
    public Motel() {
        this._play_short = false;
    }

    /**
     * Method description
     *
     */
    public void MotelInside() {
        aiplayer actor = Crew.getIgrok();

        this.car = Crew.getIgrokCar();
        this.person = actor.getModel();
        this.task = eng.CreateTASK(this.person);
        Helper.placePersonToCar(this.person, this.car);

        long movetocar = eng.AddScriptTask(this.task, drvscripts.placePersonToCar(this.person, this.car));
        long place_in_Object_inside = eng.AddScriptTask(this.task, new PlaceInModelPosition());
        long cominginside = eng.AddAnimTASK(this.task, "ivan_in_motel", 0.0D);
        long door2 = eng.AddScriptTask(this.task, new createanimatedmotel());
        long deletedoor2 = eng.AddScriptTask(this.task, new deleteanimateddoorinside_fromperson());
        matrixJ mrot = new matrixJ();

        mrot.v0.Set(-1.0D, 0.0D, 0.0D);
        mrot.v1.Set(0.0D, 0.0D, 1.0D);
        mrot.v2.Set(0.0D, 1.0D, 0.0D);

        matrixJ mrotcam = new matrixJ();
        SCRcamera cam_motel_comingin = SCRcamera.CreateCamera("anm_cam");

        cam_motel_comingin.SetInHotelWorld();
        cam_motel_comingin.SetPlayCycleAndConsecutively(false);
        cam_motel_comingin.SetPlayConsecutively(true);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel1", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel2", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel3", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel4", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel5", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel6", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel7", "Camera_in_motel", mrot, mrotcam);

        SCRcamera cam_newspaper = SCRcamera.CreateCamera("anm_cam");

        cam_newspaper.SetPlayConsecutively(false);
        cam_newspaper.SetPlayCycleAndConsecutively(false);
        cam_newspaper.SetInHotelWorld();
        cam_newspaper.AddAnimation("Root_Camera_in_motel8", "Camera_in_motel", mrot, mrotcam);

        matrixJ mroot = new matrixJ();

        mroot.Set0(-1.0D, 0.0D, 0.0D);
        mroot.Set1(0.0D, -1.0D, 0.0D);
        mroot.Set2(0.0D, 0.0D, 1.0D);

        vectorJ pos = new vectorJ(0.0D, 0.0D, 0.0D);
        long camera_newspaper = eng.AddScriptTask(this.task,
                                    camscripts.playCamera_bindMatrix(cam_newspaper, mroot, pos));
        long deletecamera_newspaper = eng.AddScriptTask(this.task, camscripts.deleteCamera(cam_newspaper));
        long NEWSPAPEREND = eng.AddEventTask(this.task);

        eng.EventTask_onMOTELMessageClosed(NEWSPAPEREND, false);

        long realesecar = eng.AddScriptTask(this.task, new ReleaseFromParking());
        long deletetask = eng.AddScriptTask(this.task, new FinishTask());
        long cr_newspaper = eng.AddScriptTask(this.task, new CreateMenu());
        long event_show_menu = eng.AddEventTask(this.task);
        long show_menu = eng.AddScriptTask(this.task, new ShowMenu());
        long develop_menu = eng.AddScriptTask(this.task, new DevelopMenu());

        eng.EventTask_onMOTELDevelopFinish(event_show_menu, false);

        long motel_world = eng.AddChangeWorldTask(this.task, "motel", "so");
        long game_world = eng.AddChangeWorldTask(this.task, "game", "simple");
        long camera_comimgin = eng.AddScriptTask(this.task,
                                   camscripts.playCamera_bindMatrix(cam_motel_comingin, mroot, pos));
        long stopcamera_comimgin = eng.AddScriptTask(this.task, camscripts.stopCamera(cam_motel_comingin));
        long person_inhotelworld = eng.AddScriptTask(this.task, new PlaceInModel());
        long person_ingame = eng.AddScriptTask(this.task, new PlaceInGame());
        long out_cabin = eng.AddScriptTask(this.task, drvscripts.outCabinState(this.car));
        long set_anm_camera_dependence_task = eng.AddScriptTask(this.task,
                                                  new Set_anm_camera_dependence(cominginside, cam_motel_comingin));

        eng.OnEndTASK(NEWSPAPEREND, "play", game_world);
        eng.OnEndTASK(NEWSPAPEREND, "play", deletecamera_newspaper);
        eng.OnEndTASK(game_world, "play", person_ingame);
        eng.OnEndTASK(game_world, "play", movetocar);

        SOscene SC = new SOscene();

        SC.task = this.task;
        SC.person = this.person;
        SC.actor = actor;
        SC.vehicle = this.car;

        long PARking = SC.waitParkingEvent();
        CarInOutTasks CAR_out = (this._play_short)
                                ? new CarInOutTasks(0L, PARking)
                                : SC.makecaroutOnEnd(PARking, true);
        CarInOutTasks CAR_in = SC.makecarinOnEnd(game_world);

        SC.restorecameratoCarOnEnd(CAR_in.getMTaskToWait());
        eng.playTASK(PARking);
        eng.disableControl();
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", realesecar);
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", deletetask);

        if (this._play_short) {
            eng.OnEndTASK(PARking, "play", out_cabin);
        }

        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", motel_world);
        eng.OnEndTASK(motel_world, "play", place_in_Object_inside);
        eng.OnEndTASK(motel_world, "play", cominginside);
        eng.OnEndTASK(motel_world, "play", person_inhotelworld);
        eng.OnBegTASK(cominginside, "play", cr_newspaper);
        eng.OnBegTASK(cominginside, "play", door2);
        eng.OnMidTASK(cominginside, 13.5D, 13.5D, "play", develop_menu);
        eng.OnEndTASK(cominginside, "play", deletedoor2);
        eng.OnEndTASK(event_show_menu, "play", show_menu);
        eng.OnBegTASK(cominginside, "play", set_anm_camera_dependence_task);
        eng.OnBegTASK(cominginside, "play", camera_comimgin);
        eng.OnEndTASK(cominginside, "play", deletedoor2);
        eng.OnEndTASK(cominginside, "play", stopcamera_comimgin);
        eng.OnEndTASK(cominginside, "play", camera_newspaper);
        eng.OnEndTASK(cominginside, "play", NEWSPAPEREND);
    }

    /**
     * Method description
     *
     */
    public void MotelInside_short() {
        this._play_short = true;
        MotelInside();
    }

    static class CreateMenu implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            menues.CreateMotelMENU();
        }
    }


    static class DevelopMenu implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            event.Setevent(7002);
        }
    }


    class FinishTask implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            eng.DeleteTASK(Motel.this.task);
            eng.enableControl();
        }
    }


    class PlaceInGame implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            Motel.this.person.SetInGameWorld();
            eng.returnCameraToGameWorld();
        }
    }


    class PlaceInModel implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            Motel.this.person.SetInHotelWorld();
        }
    }


    class PlaceInModelPosition implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            Motel.this.person.SetPosition(new vectorJ(0.0D, 0.0D, 0.0D));
            Motel.this.person.SetDirection(new vectorJ(0.0D, 1.0D, 0.0D));
            Motel.this.person.play();
        }
    }


    class ReleaseFromParking implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            Motel.this.car.leaveParking();
        }
    }


    static class Set_anm_camera_dependence implements IScriptTask {
        private final SCRcamera cam;
        private final long task;

        Set_anm_camera_dependence(long task, SCRcamera cam) {
            this.cam = cam;
            this.task = task;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            eng.Anim_SetCamera(this.task, this.cam.nativePointer);
        }
    }


    static class ShowMenu implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            menues.showMenu(7000);
        }
    }


    class createanimatedmotel implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            matrixJ mrot = new matrixJ();

            mrot.v0.Set(1.0D, 0.0D, 0.0D);
            mrot.v1.Set(0.0D, 0.0D, 1.0D);
            mrot.v2.Set(0.0D, -1.0D, 0.0D);

            matrixJ mrot_newspaper = new matrixJ();

            Motel.this.person.CreateAnimatedSpace_timedependance("SpaceDoor", "SpaceDoor_in_motel",
                    "space_MDL-SpaceDoor", "ivan_in_motel", 0.0D, 0L, 0L, mrot_newspaper, true, false);
            Motel.this.person.CreateAnimatedSpace_timedependance("Space_RC", "Space_RC_in_motel", "space_MDL-Space_RC",
                    "ivan_in_motel", 0.0D, 0L, 0L, mrot_newspaper, true, true);
            Motel.this.person.CreateAnimatedSpace_timedependance("Space_Doorhold01", "Space_Doorhold_in_motel",
                    "space_MDL-Space_Doorhold01", "ivan_in_motel", 0.0D, 0L, 0L, mrot_newspaper, true, true);
            Motel.this.person.CreateAnimatedSpace_timedependance("Space_Doorhold02", "Space_Doorhold_in_motel",
                    "space_MDL-Space_Doorhold02", "ivan_in_motel", 0.0D, 0L, 0L, mrot_newspaper, true, true);
        }
    }


    class deleteanimateddoorinside_fromperson implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            Motel.this.person.DeleteAnimatedSpace("SpaceDoor", "SpaceDoor_in_motel", 0L);
            Motel.this.person.DeleteAnimatedSpace("Space_RC", "Space_RC_in_motel", 0L);
            Motel.this.person.DeleteAnimatedSpace("Space_Doorhold01", "Space_Doorhold_in_motel", 0L);
            Motel.this.person.DeleteAnimatedSpace("Space_Doorhold02", "Space_Doorhold_in_motel", 0L);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
