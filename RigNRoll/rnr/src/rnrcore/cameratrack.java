/*
 * @(#)cameratrack.java   13/08/26
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class cameratrack {

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     * @param paramString3
     * @param paramString4
     */
    public static native void ChangeClipParam(long paramLong, String paramString1, String paramString2,
            String paramString3, String paramString4);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     * @param paramString3
     */
    public static native void ChangeClipParam(long paramLong, String paramString1, String paramString2,
            String paramString3);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native SCRcamera[] GetTrackCameras(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     *
     * @return
     */
    public static native SCRcamera[] GetTrackActorCameras(long paramLong, String paramString);

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
    public static native SCRcamera GetTrackActorClipCameras(long paramLong, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     */
    public static native void InitCameraTrack(long paramLong, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     * @param paramString3
     */
    public static native void InitCameraTrackActor(long paramLong, String paramString1, String paramString2,
            String paramString3);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     * @param paramString3
     * @param paramString4
     */
    public static native void InitCameraTrackActorClip(long paramLong, String paramString1, String paramString2,
            String paramString3, String paramString4);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param parammatrixJ
     * @param paramvectorJ
     */
    public static native void BindToMatrixCameraTrack(long paramLong, matrixJ parammatrixJ, vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void BindToVehicleSteerWheelCameraTrack(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramString
     *
     * @return
     */
    public static native long CreateCameraTrack(String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramDouble
     */
    public static native void SetGlobalTime(long paramLong, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramDouble
     */
    public static native void SetGlobalTime(long paramLong, String paramString, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramDouble
     */
    public static native void SetMaximumTime(long paramLong, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramDouble
     */
    public static native void SetMaximumTime(long paramLong, String paramString, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramDouble
     */
    public static native void AddTime(long paramLong, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramDouble
     */
    public static native void AddTime(long paramLong, String paramString, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void RunTrack(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void StopTrack(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void DeleteTrack(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void AttachCameraToCar(int paramInt);
}


//~ Formatted in DD Std on 13/08/26
