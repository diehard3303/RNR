/*
 * @(#)camscripts.java   13/08/28
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

import rnr.src.players.actorveh;
import rnr.src.rnrconfig.cabincam;
import rnr.src.rnrcore.IScriptTask;
import rnr.src.rnrcore.SCRcamera;
import rnr.src.rnrcore.cameratrack;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;

//~--- JDK imports ------------------------------------------------------------

import java.lang.reflect.Array;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class camscripts {

    /** Field description */
    public static camscripts helper = new camscripts();

    /**
     * Method description
     *
     *
     * @param camera
     * @param M
     * @param P
     *
     * @return
     */
    public static IScriptTask playCamera_bindMatrix(SCRcamera camera, matrixJ M, vectorJ P) {
        return new PlayCamera_bindMatrix(camera, M, P);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static IScriptTask restore_Camera_to_igrok_car() {
        return new Restore_Camera_to_igrok_car();
    }

    /**
     * Method description
     *
     *
     * @param value
     *
     * @return
     */
    public static IScriptTask buffercamturn(boolean value) {
        return new Buffercamturn(value);
    }

    /**
     * Method description
     *
     *
     * @param cam
     * @param car
     *
     * @return
     */
    public static IScriptTask playCamera_bindSteerWheel(SCRcamera cam, actorveh car) {
        return new PlayCamera_bindSteerWheel(cam, car);
    }

    /**
     * Method description
     *
     *
     * @param cam
     *
     * @return
     */
    public static IScriptTask deleteCamera(SCRcamera cam) {
        return new DeleteCam(cam);
    }

    /**
     * Method description
     *
     *
     * @param cam
     *
     * @return
     */
    public static IScriptTask stopCamera(SCRcamera cam) {
        return new StopCam(cam);
    }

    /**
     * Method description
     *
     *
     * @param cam
     *
     * @return
     */
    public static IScriptTask playCamera(SCRcamera cam) {
        return new PlayCam(cam);
    }

    /**
     * Method description
     *
     *
     * @param track
     *
     * @return
     */
    public static IScriptTask runTrack(long track) {
        return new RunTrack(track);
    }

    /**
     * Method description
     *
     *
     * @param track
     *
     * @return
     */
    public static IScriptTask deleteTrack(long track) {
        return new DeleteTrack(track);
    }

    /**
     * Method description
     *
     *
     * @param param
     */
    public void RunTrack(Param_CamScripts_SingleLong param) {
        cameratrack.RunTrack(param.param);
    }

    /**
     * Method description
     *
     *
     * @param param
     */
    public void StopTrack(Param_CamScripts_SingleLong param) {
        cameratrack.StopTrack(param.param);
    }

    /**
     * Method description
     *
     *
     * @param param
     */
    public void DeleteTrack(Param_CamScripts_SingleLong param) {
        cameratrack.DeleteTrack(param.param);
    }

    /**
     * Method description
     *
     */
    public static void buffercamturnoff() {
        rnr.src.rnrcore.Helper.peekNativeMessage("turn off buffer camera");
    }

    /**
     * Method description
     *
     */
    public static void buffercamturnon() {
        rnr.src.rnrcore.Helper.peekNativeMessage("turn on buffer camera");
    }

    /**
     * Method description
     *
     *
     * @param param
     */
    public void PlayCam(Param_CamScripts_SingleLong param) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(param.param);

        cam.PlayCamera();
    }

    /**
     * Method description
     *
     *
     * @param param
     */
    public void DeleteCam(Param_CamScripts_SingleLong param) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(param.param);

        cam.DeleteCamera();
    }

    /**
     * Method description
     *
     *
     * @param camp
     * @param car
     */
    public void PlayCamera_bindVehicle(long camp, long car) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);

        cam.BindToVehicle(car);
        cam.PlayCamera();
    }

    /**
     * Method description
     *
     *
     * @param camp
     * @param nomgate
     */
    public void PlayCamera_bindSTO(long camp, long nomgate) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);

        cam.BindToRepair(eng.GetCurrentRepairShop(), (int) nomgate);
        cam.PlayCamera();
    }

    /**
     * Method description
     *
     *
     * @param camp
     * @param nomgate
     */
    public void PlayCamera_bindWarehouse(long camp, long nomgate) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);

        cam.BindToWarehouse(eng.GetCurrentWarehouse(), (int) nomgate);
        cam.PlayCamera();
    }

    /**
     * Method description
     *
     *
     * @param camp
     * @param matr
     * @param posr
     */
    public void PlayCamera_bindMatrix(long camp, matrixJ matr, vectorJ posr) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);

        cam.BindToMatrix(matr, posr);
        cam.PlayCamera();
    }

    /**
     * Method description
     *
     *
     * @param camp
     * @param veh
     */
    public void PlayCamera_bindSteerWheel(long camp, long veh) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);

        cam.BindToVehicleSteerWheel(veh);
        cam.PlayCamera();
    }

    /**
     * Method description
     *
     *
     * @param camp
     * @param veh
     * @param nom_wh
     */
    public void PlayCamera_bindWheel(long camp, long veh, int nom_wh) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);

        cam.BindToVehicleWheel(veh, nom_wh);
        cam.PlayCamera();
    }

    /**
     * Method description
     *
     *
     * @param camp
     * @param veh
     */
    public void PlayCamera_bindWheel0(long camp, long veh) {
        PlayCamera_bindWheel(camp, veh, 0);
    }

    /**
     * Method description
     *
     *
     * @param camp
     * @param veh
     */
    public void PlayCamera_bindWheel1(long camp, long veh) {
        PlayCamera_bindWheel(camp, veh, 1);
    }

    /**
     * Method description
     *
     *
     * @param camp
     * @param veh
     */
    public void PlayCamera_bindWheel2(long camp, long veh) {
        PlayCamera_bindWheel(camp, veh, 2);
    }

    /**
     * Method description
     *
     *
     * @param camp
     * @param veh
     */
    public void PlayCamera_bindWheel3(long camp, long veh) {
        PlayCamera_bindWheel(camp, veh, 3);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public SCRcamera CreateCamera_FrontOfVehicle() {
        SCRcamera cam_veh1 = SCRcamera.CreateCamera("anm");

        cam_veh1.nCamera("anm");

        String CAM_VEHname2 = "camera_RUL11";
        matrixJ mrotVEH = new matrixJ();

        mrotVEH.v0.Set(0.0D, 1.0D, 0.0D);
        mrotVEH.v1.Set(0.0D, 0.0D, 1.0D);
        mrotVEH.v2.Set(1.0D, 0.0D, 0.0D);

        matrixJ mrotcamVEH = new matrixJ();

        mrotcamVEH.v0.Set(0.0D, 0.0D, -1.0D);
        mrotcamVEH.v1.Set(0.0D, 1.0D, 0.0D);
        mrotcamVEH.v2.Set(1.0D, 0.0D, 0.0D);
        cam_veh1.AddAnimation(CAM_VEHname2, "space_MDL-" + CAM_VEHname2, mrotVEH, mrotcamVEH);

        return cam_veh1;
    }

    /**
     * Method description
     *
     *
     * @param cam_veh1
     */
    public void AddCamera_FrontOfVehicle(SCRcamera cam_veh1) {
        String CAM_VEHname2 = "camera_RUL11";
        matrixJ mrotVEH = new matrixJ();

        mrotVEH.v0.Set(0.0D, 1.0D, 0.0D);
        mrotVEH.v1.Set(0.0D, 0.0D, 1.0D);
        mrotVEH.v2.Set(1.0D, 0.0D, 0.0D);

        matrixJ mrotcamVEH = new matrixJ();

        mrotcamVEH.v0.Set(0.0D, 0.0D, -1.0D);
        mrotcamVEH.v1.Set(0.0D, 1.0D, 0.0D);
        mrotcamVEH.v2.Set(1.0D, 0.0D, 0.0D);
        cam_veh1.AddAnimation(CAM_VEHname2, "space_MDL-" + CAM_VEHname2, mrotVEH, mrotcamVEH);
    }

    /**
     * Method description
     *
     *
     * @param cam_veh1
     */
    public void AddCamera_BackOfVehicle(SCRcamera cam_veh1) {
        String CAM_VEHname2 = "camera_RUL10";
        matrixJ mrotVEH = new matrixJ();

        mrotVEH.v0.Set(0.0D, 1.0D, 0.0D);
        mrotVEH.v1.Set(0.0D, 0.0D, 1.0D);
        mrotVEH.v2.Set(1.0D, 0.0D, 0.0D);

        matrixJ mrotcamVEH = new matrixJ();

        mrotcamVEH.v0.Set(0.0D, 0.0D, -1.0D);
        mrotcamVEH.v1.Set(0.0D, 1.0D, 0.0D);
        mrotcamVEH.v2.Set(1.0D, 0.0D, 0.0D);
        cam_veh1.AddAnimation(CAM_VEHname2, "space_MDL-" + CAM_VEHname2, mrotVEH, mrotcamVEH);
    }

    /**
     * Method description
     *
     *
     * @param carprefix
     *
     * @return
     */
    public SCRcamera CreateCameraBackSit(String carprefix) {
        SCRcamera cam_back = SCRcamera.CreateCamera("car");

        cam_back.nCamera("car");
        cabincam.Set_0_cam_Angles(cam_back.nativePointer, 0.0D, 0.0D, 0.0D);

        int carnom = -1;

        if (0 == carprefix.compareTo("")) {
            carnom = 1;
        }

        if (0 == carprefix.compareTo("FREIGHTLINER_CLASSIC_XL_car_4")) {
            carnom = 2;
        }

        if (0 == carprefix.compareTo("Freight_Cor_")) {
            carnom = 3;
        }

        switch (carnom) {
         case 1 :
             cabincam.Add_camera_cabin_point(cam_back.nativePointer, 0.0D, -2.0D, 1.55D, 0.0D, 0.0D, 0.0D, 0.0D);

             break;

         case 2 :
             cabincam.Add_camera_cabin_point(cam_back.nativePointer, 0.0D, -1.5D, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D);

             break;

         case 3 :
             cabincam.Add_camera_cabin_point(cam_back.nativePointer, 0.0D, -0.1499999999999999D, 2.589D, -100.0D,
                                             100.0D, -89.0D, 89.0D);

             break;

         default :
             eng.writeLog("FromScript. Cannot find description of car " + carprefix + " in CreateCameraBackSit.");
             cabincam.Add_camera_cabin_point(cam_back.nativePointer, 0.0D, -1.5D, 1.55D, 0.0D, 0.0D, 0.0D, 0.0D);
        }

        return cam_back;
    }

    static class Buffercamturn implements IScriptTask {
        private final boolean value;

        Buffercamturn(boolean value) {
            this.value = value;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            if (this.value) {
                camscripts.buffercamturnon();
            } else {
                camscripts.buffercamturnoff();
            }
        }
    }


    static class DeleteCam implements IScriptTask {
        private final SCRcamera cam;

        DeleteCam(SCRcamera cam) {
            this.cam = cam;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            this.cam.DeleteCamera();
        }
    }


    static class DeleteTrack implements IScriptTask {
        private final long track;

        DeleteTrack(long track) {
            this.track = track;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            SCRcamera[] allcameras = cameratrack.GetTrackCameras(this.track);

            for (int i = 0; i < Array.getLength(allcameras); ++i) {
                allcameras[i].StopCamera();
            }

            cameratrack.DeleteTrack(this.track);
        }
    }


    static class PlayCam implements IScriptTask {
        private final SCRcamera cam;

        PlayCam(SCRcamera cam) {
            this.cam = cam;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            this.cam.PlayCamera();
        }
    }


    static class PlayCamera_bindMatrix implements IScriptTask {
        private final SCRcamera cam;
        private final matrixJ M;
        private final vectorJ P;

        PlayCamera_bindMatrix(SCRcamera cam, matrixJ M, vectorJ P) {
            this.cam = cam;
            this.M = M;
            this.P = P;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            this.cam.BindToMatrix(this.M, this.P);
            this.cam.PlayCamera();
        }
    }


    static class PlayCamera_bindSteerWheel implements IScriptTask {
        private final SCRcamera cam;
        private final actorveh car;

        PlayCamera_bindSteerWheel(SCRcamera cam, actorveh car) {
            this.cam = cam;
            this.car = car;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            this.cam.BindToVehicleSteerWheel(this.car.getCar());
            this.cam.PlayCamera();
        }
    }


    static class Restore_Camera_to_igrok_car implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            Helper.restoreCameraToIgrokCar();
        }
    }


    static class RunTrack implements IScriptTask {
        private final long track;

        RunTrack(long track) {
            this.track = track;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            cameratrack.RunTrack(this.track);
        }
    }


    static class StopCam implements IScriptTask {
        private final SCRcamera cam;

        StopCam(SCRcamera cam) {
            this.cam = cam;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            this.cam.StopCamera();
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public class trackclipparams {
        String anmName;
        String name;

        /** Field description */
        public boolean Active;
        double In;
        double Out;
        double StartOffset;
        double Scale;
        double antiScale;
        double BefType;
        double BefHold;
        double BefCycl;
        double BefBoun;
        double AftType;
        double AftHold;
        double AftCycl;
        double AftBoun;

        /** Field description */
        public double Weight;
        String track_name;
        boolean track_mono;

        /** Field description */
        public boolean track_mute;

        /**
         * Constructs ...
         *
         */
        public trackclipparams() {
            this.anmName = "";
            this.name = "";
            this.track_name = "";
        }
    }
}


//~ Formatted in DD Std on 13/08/28
