/*
 * @(#)SCRcamera.java   13/08/26
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
public class SCRcamera {

    /** Field description */
    public long nativePointer;

    /**
     * Constructs ...
     *
     */
    public SCRcamera() {
        this.nativePointer = 0L;
    }

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public native void nCamera(String paramString);

    /**
     * Method description
     *
     *
     * @param paramDouble
     */
    public native void ScaleAnimation(double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public native void SetPlayConsecutively(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public native void SetPlayCycleAndConsecutively(boolean paramBoolean);

    /**
     * Method description
     *
     */
    public native void PlayCamera();

    /**
     * Method description
     *
     */
    public native void StopCamera();

    /**
     * Method description
     *
     */
    public native void DeleteCamera();

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param parammatrixJ1
     * @param parammatrixJ2
     */
    public native void AddAnimation(String paramString1, String paramString2, matrixJ parammatrixJ1,
                                    matrixJ parammatrixJ2);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public native void BindToVehicle(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     */
    public native void BindToWarehouse(long paramLong, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public native void BindToGasStation(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public native void BindToBar(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     */
    public native void BindToRepair(long paramLong, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public native void BindToVehicleSteerWheel(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     */
    public native void BindToVehicleWheel(long paramLong, int paramInt);

    /**
     * Method description
     *
     *
     * @param parammatrixJ
     * @param paramvectorJ
     */
    public native void BindToMatrix(matrixJ parammatrixJ, vectorJ paramvectorJ);

    /**
     * Method description
     *
     */
    public native void SetInBarWorld();

    /**
     * Method description
     *
     */
    public native void SetInBar2World();

    /**
     * Method description
     *
     */
    public native void SetInBar3World();

    /**
     * Method description
     *
     */
    public native void SetInOfficeWorld();

    /**
     * Method description
     *
     */
    public native void SetInHotelWorld();

    /**
     * Method description
     *
     */
    public native void SetInGameWorld();

    /**
     * Method description
     *
     */
    public native void SetInPoliceWorld();

    /**
     * Method description
     *
     */
    public native void IncludeCameraInCabinRender();

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public static native void jumpInCabin(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void SetCameraPosition(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param typ
     *
     * @return
     */
    public static SCRcamera CreateCamera(String typ) {
        SCRcamera cam = new SCRcamera();

        cam.nCamera(typ);

        return cam;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static SCRcamera CreateCamera_fake(long str) {
        SCRcamera cam = new SCRcamera();

        cam.nativePointer = str;

        return cam;
    }
}


//~ Formatted in DD Std on 13/08/26
