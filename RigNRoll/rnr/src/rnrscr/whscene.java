/*
 * @(#)whscene.java   13/08/28
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
import rnr.src.rnrcore.Helper;
import rnr.src.rnrcore.IScriptTask;
import rnr.src.rnrcore.SCRcamera;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.cameratrack;
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
public class whscene {
    private long task;
    private actorveh car;
    private SCRuniperson person;
    SCRcamera cam_anm2;

    /**
     * Constructs ...
     *
     */
    public whscene() {
        this.cam_anm2 = SCRcamera.CreateCamera("anm_cam");
    }

    SCRcamera CreateCamea_animation(String name, String spacename) {
        SCRcamera cam1 = SCRcamera.CreateCamera("anm");

        cam1.nCamera("anm");

        matrixJ Mrot = new matrixJ();

        Mrot.Set0(-0.1D, 0.0D, 0.0D);
        Mrot.Set1(0.0D, 0.0D, 0.1D);
        Mrot.Set2(0.0D, 0.1D, 0.0D);

        matrixJ mrotcam = new matrixJ();

        cam1.AddAnimation(name, spacename, Mrot, mrotcam);

        return cam1;
    }

    long CreateCamea_animationtask(SCRcamera cam_anm1, String name, String spacename, long task, int nomgate) {
        matrixJ Mrot = new matrixJ();

        Mrot.Set0(-1.0D, 0.0D, 0.0D);
        Mrot.Set1(0.0D, 0.0D, 1.0D);
        Mrot.Set2(0.0D, 1.0D, 0.0D);

        matrixJ mrotcam = new matrixJ();

        cam_anm1.BindToWarehouse(eng.GetCurrentWarehouse(), nomgate - 1);
        cam_anm1.SetPlayCycleAndConsecutively(true);
        cam_anm1.SetPlayConsecutively(true);
        cam_anm1.AddAnimation(name, spacename, Mrot, mrotcam);

        long Camera1 = eng.AddScriptTask(task, camscripts.playCamera(cam_anm1));

        return Camera1;
    }

    long camerastop(SCRcamera cam_anm1, long task) {
        long Camera1 = eng.AddScriptTask(task, camscripts.stopCamera(cam_anm1));

        return Camera1;
    }

    long cameradelete(SCRcamera cam_anm1, long task) {
        long Camera1 = eng.AddScriptTask(task, camscripts.deleteCamera(cam_anm1));

        return Camera1;
    }

    /**
     * Method description
     *
     *
     * @param matrix
     * @param pos
     *
     * @return
     */
    public long CreateTrackCamera_CoteWarehouse(matrixJ matrix, vectorJ pos) {
        long track = cameratrack.CreateCameraTrack("..\\Data\\Actors\\Warehouse\\Camera\\Entry_W_House25fps_fast.txt");

        cameratrack.BindToMatrixCameraTrack(track, matrix, pos);
        cameratrack.InitCameraTrack(track, "anm", "Camera_Whouse");

        return track;
    }

    long CreateTrackCamera_LoadWarehouse(int nomgate, matrixJ matrix, vectorJ pos) {
        String filename = "..\\Data\\Actors\\Warehouse\\Camera\\Load_W_House25fps.txt";

        if ((nomgate > 1) && (nomgate / 2 * 2 == nomgate)) {
            filename = "..\\Data\\Actors\\Warehouse\\Camera\\Loadm_W_House25fps.txt";
        }

        long track = cameratrack.CreateCameraTrack(filename);

        cameratrack.BindToMatrixCameraTrack(track, matrix, pos);
        cameratrack.InitCameraTrack(track, "anm", "Camera_Whouse");

        return track;
    }

    long CreateTrackCamera_UnLoadWarehouse(int nomgate, matrixJ matrix, vectorJ pos) {
        String filename = "..\\Data\\Actors\\Warehouse\\Camera\\UnLoad_W_House25fps_fast.txt";

        if ((nomgate > 1) && (nomgate / 2 * 2 == nomgate)) {
            filename = "..\\Data\\Actors\\Warehouse\\Camera\\UnLoadm_W_House25fps_fast.txt";
        }

        long track = cameratrack.CreateCameraTrack(filename);

        cameratrack.BindToMatrixCameraTrack(track, matrix, pos);
        cameratrack.InitCameraTrack(track, "anm", "Camera_Whouse");

        return track;
    }

    long CreateTrackCamera_OutWarehouse_loaded(matrixJ matrix, vectorJ pos) {
        long track = cameratrack.CreateCameraTrack("..\\Data\\Actors\\Warehouse\\Camera\\Out_W_House25fps.txt");

        cameratrack.BindToMatrixCameraTrack(track, matrix, pos);
        cameratrack.InitCameraTrack(track, "anm", "Camera_Whouse");

        return track;
    }

    long CreateTrackCamera_OutWarehouse_empty(matrixJ matrix, vectorJ pos) {
        long track = cameratrack.CreateCameraTrack("..\\Data\\Actors\\Warehouse\\Camera\\Out_W_House25fps_fast.txt");

        cameratrack.BindToMatrixCameraTrack(track, matrix, pos);
        cameratrack.InitCameraTrack(track, "anm", "Camera_Whouse");

        return track;
    }

    /**
     * Method description
     *
     *
     * @param nomgate
     * @param matr_WHGATE
     * @param pos_WHGATE
     */
    public void runWareHouseScript(int nomgate, matrixJ matr_WHGATE, vectorJ pos_WHGATE) {
        aiplayer actor = Crew.getIgrok();

        this.car = Crew.getIgrokCar();
        this.person = actor.getModel();
        this.task = eng.CreateTASK(this.person);

        long Timer_Menu = eng.AddEventTask(this.task);
        long RELASECAMERA = eng.AddEventTask(this.task);
        long RELASECAMERA_timeshift_loaded = eng.AddEventTask(this.task);
        long RELASECAMERA_timeshift_empty = eng.AddEventTask(this.task);
        long ENABLEMENUSHOW = eng.AddScriptTask(this.task, new ShowMenu());
        SCRcamera cam_wh_enter = SCRcamera.CreateCamera("whenter");
        SCRcamera cam_wh_exit = SCRcamera.CreateCamera("whexit");
        long playcamenter = eng.AddScriptTask(this.task, camscripts.playCamera(cam_wh_enter));
        long delecamenter = eng.AddScriptTask(this.task, camscripts.deleteCamera(cam_wh_enter));
        long playcamexit = eng.AddScriptTask(this.task, camscripts.playCamera(cam_wh_exit));
        long delecamexit = eng.AddScriptTask(this.task, camscripts.deleteCamera(cam_wh_exit));
        long DELETEALL_andVehCam = eng.AddScriptTask(this.task, new FinishTask());
        long RestoreCameraToCar = eng.AddScriptTask(this.task, camscripts.restore_Camera_to_igrok_car());
        long SetDriverInCabin = eng.AddScriptTask(this.task, drvscripts.inCabinState(this.car));
        long EXIT_GAME = eng.AddEventTask(this.task);
        long ENDSCENE = eng.AddEventTask(this.task);
        long JUSTCAMETOWH = eng.AddEventTask(this.task);
        long LoadTrailer = eng.AddEventTask(this.task);
        long UnLoadTrailer = eng.AddEventTask(this.task);
        long GoFromWH_loaded = eng.AddEventTask(this.task);
        long GoFromWH_empty = eng.AddEventTask(this.task);
        long CameraGoingFromWH_loaded = eng.AddEventTask(this.task);
        long CameraGoingFromWH_empty = eng.AddEventTask(this.task);
        long CameraUNLOADING = eng.AddEventTask(this.task);

        eng.EventTask_ExitGame(EXIT_GAME, false);
        eng.EventTask_onWhLeave(ENDSCENE, false);
        eng.EventTask_onWhLeaveGates(RELASECAMERA, false);
        eng.EventTask_onWhCame(JUSTCAMETOWH, false);
        eng.EventTask_onWhLoad(LoadTrailer, true);
        eng.EventTask_onWhUnLoad(UnLoadTrailer, true);
        eng.EventTask_onWhGoFromEmpty(GoFromWH_empty, true);
        eng.EventTask_onWhGoFromLoaded(GoFromWH_loaded, true);

        long track = CreateTrackCamera_CoteWarehouse(matr_WHGATE, pos_WHGATE);
        long RUN_COMEWH_TRACK = eng.AddScriptTask(this.task, camscripts.runTrack(track));
        long DELETE_COMEWH_TRACK = eng.AddScriptTask(this.task, camscripts.deleteTrack(track));
        long trackLoad = CreateTrackCamera_LoadWarehouse(nomgate, matr_WHGATE, pos_WHGATE);
        long DELETE_LOADWH_TRACK = eng.AddScriptTask(this.task, camscripts.deleteTrack(trackLoad));
        long trackUnLoad = CreateTrackCamera_UnLoadWarehouse(nomgate, matr_WHGATE, pos_WHGATE);
        long RUN_UNLOADWH_TRACK = eng.AddScriptTask(this.task, camscripts.runTrack(trackUnLoad));
        long DELETE_UNLOADWH_TRACK = eng.AddScriptTask(this.task, camscripts.deleteTrack(trackUnLoad));
        long trackOut_loaded = CreateTrackCamera_OutWarehouse_loaded(matr_WHGATE, pos_WHGATE);
        long RUN_OUTWH_TRACK_LOADED = eng.AddScriptTask(this.task, camscripts.runTrack(trackOut_loaded));
        long DELETE_OUTWH_TRACK_LOADED = eng.AddScriptTask(this.task, camscripts.deleteTrack(trackOut_loaded));
        long trackOut_empty = CreateTrackCamera_OutWarehouse_empty(matr_WHGATE, pos_WHGATE);
        long RUN_OUTWH_TRACK_EMPTY = eng.AddScriptTask(this.task, camscripts.runTrack(trackOut_empty));
        long DELETE_OUTWH_TRACK_EMPTY = eng.AddScriptTask(this.task, camscripts.deleteTrack(trackOut_empty));

        eng.OnMidTASK(Timer_Menu, 1.0D, 1.0D, "play", delecamenter);
        eng.OnMidTASK(Timer_Menu, 1.0D, 1.0D, "play", RUN_COMEWH_TRACK);
        eng.OnBegTASK(LoadTrailer, "play", DELETE_COMEWH_TRACK);
        eng.OnBegTASK(UnLoadTrailer, "play", CameraUNLOADING);
        eng.OnBegTASK(CameraUNLOADING, "play", RUN_UNLOADWH_TRACK);
        eng.OnBegTASK(CameraGoingFromWH_loaded, "play", RELASECAMERA_timeshift_loaded);
        eng.OnBegTASK(CameraGoingFromWH_loaded, "play", DELETE_UNLOADWH_TRACK);
        eng.OnBegTASK(CameraGoingFromWH_empty, "play", RELASECAMERA_timeshift_empty);
        eng.OnBegTASK(CameraGoingFromWH_empty, "play", DELETE_UNLOADWH_TRACK);
        eng.OnBegTASK(CameraGoingFromWH_empty, "play", DELETE_LOADWH_TRACK);
        eng.OnBegTASK(GoFromWH_loaded, "play", CameraGoingFromWH_loaded);
        eng.OnBegTASK(GoFromWH_empty, "play", CameraGoingFromWH_empty);
        eng.OnBegTASK(CameraGoingFromWH_loaded, "play", RUN_OUTWH_TRACK_LOADED);
        eng.OnBegTASK(CameraGoingFromWH_empty, "play", RUN_OUTWH_TRACK_EMPTY);
        eng.OnMidTASK(Timer_Menu, 9.5D, 9.5D, "play", ENABLEMENUSHOW);
        eng.OnEndTASK(RELASECAMERA, "play", DELETE_OUTWH_TRACK_LOADED);
        eng.OnEndTASK(RELASECAMERA, "play", DELETE_OUTWH_TRACK_EMPTY);
        eng.OnEndTASK(RELASECAMERA, "play", playcamexit);
        eng.OnEndTASK(RELASECAMERA, "play", ENDSCENE);
        eng.OnEndTASK(ENDSCENE, "play", SetDriverInCabin);
        eng.OnEndTASK(ENDSCENE, "play", DELETEALL_andVehCam);
        eng.OnEndTASK(ENDSCENE, "play", delecamexit);
        eng.OnEndTASK(ENDSCENE, "play", RestoreCameraToCar);
        eng.OnEndTASK(JUSTCAMETOWH, "play", Timer_Menu);
        eng.OnEndTASK(EXIT_GAME, "play", DELETEALL_andVehCam);
        eng.playTASK(JUSTCAMETOWH);
        eng.playTASK(playcamenter);
        eng.playTASK(EXIT_GAME);
    }

    class FinishTask implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            eng.DeleteTASK(whscene.this.task);
        }
    }


    static class ShowMenu implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            Helper.peekNativeMessage("show wh menu");
        }
    }
}


//~ Formatted in DD Std on 13/08/28
