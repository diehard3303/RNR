/*
 * @(#)SCRcardriver.java   13/08/26
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
public class SCRcardriver {

    /** Field description */
    public long nativePointer;

    /**
     * Constructs ...
     *
     */
    public SCRcardriver() {
        this.nativePointer = 0L;
    }

    /**
     * Method description
     *
     *
     * @param paramSCRuniperson
     */
    public native void nCarDriver(SCRuniperson paramSCRuniperson);

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramInt1
     * @param paramInt2
     */
    public native void AddSteeringAnimation(String paramString, int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public native void AddSitPose(String paramString);

    /**
     * Method description
     *
     */
    public native void play();

    /**
     * Method description
     *
     */
    public native void stop();

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void SetShift(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramInt
     */
    public native void AddGearingAnimation(String paramString, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public native void AddPedalingAnimation(String paramString);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public native void AddMirrorAnimation(String paramString);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void SitShift(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public native void AddWalkingOutAnimation(String paramString);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public native void AddWalkingInAnimation(String paramString);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public native void AddGasStationAnimation(String paramString);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public native void attachToCar(int paramInt);

    /**
     * Method description
     *
     *
     * @param Pers
     *
     * @return
     */
    public static SCRcardriver CreateCarDriver(SCRuniperson Pers) {
        SCRcardriver drv = new SCRcardriver();

        drv.nCarDriver(Pers);

        return drv;
    }

    /**
     * Method description
     *
     */
    public void removeDriver() {
        stop();
    }
}


//~ Formatted in DD Std on 13/08/26
