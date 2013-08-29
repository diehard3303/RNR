/*
 * @(#)SCRanimparts.java   13/08/26
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
public class SCRanimparts {

    /** Field description */
    public long nativePointer;

    /** Field description */
    public vectorJ shift;

    /**
     * Constructs ...
     *
     */
    public SCRanimparts() {
        this.nativePointer = 0L;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public vectorJ PermanentShift() {
        return this.shift;
    }

    /**
     * Method description
     *
     *
     * @param x
     * @param y
     * @param z
     */
    public void PermanentShift(double x, double y, double z) {
        this.shift = new vectorJ(x, y, z);
    }

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public native void TuneLoop(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public native void TuneAsinch(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramDouble
     * @param paramBoolean1
     * @param paramBoolean2
     */
    public native void Tune(double paramDouble, boolean paramBoolean1, boolean paramBoolean2);

    /**
     * Method description
     *
     *
     * @param paramDouble
     * @param paramBoolean
     */
    public native void Tune(double paramDouble, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param scale
     */
    public void Tune(double scale) {
        Tune(scale, false);
    }

    /**
     * Method description
     *
     *
     * @param paramDouble1
     * @param paramDouble2
     */
    public native void setTimeNScalePreventRandom(double paramDouble1, double paramDouble2);

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
     * @param paramvectorJ
     */
    public native void SetDir(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramDouble
     */
    public native void SetVelocity(double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramSCRperson
     * @param paramString1
     * @param paramString2
     * @param paramString3
     * @param paramString4
     * @param paramString5
     * @param paramString6
     */
    public native void nAnimParts(SCRperson paramSCRperson, String paramString1, String paramString2,
                                  String paramString3, String paramString4, String paramString5, String paramString6);

    /**
     * Method description
     *
     *
     * @param paramSCRperson
     * @param paramString1
     * @param paramString2
     * @param paramString3
     * @param paramString4
     * @param paramString5
     * @param paramString6
     */
    public native void AnimPartsIgnorNull(SCRperson paramSCRperson, String paramString1, String paramString2,
            String paramString3, String paramString4, String paramString5, String paramString6);

    /**
     * Method description
     *
     *
     * @param paramDouble1
     * @param paramDouble2
     */
    public native void SetUpAsinchron(double paramDouble1, double paramDouble2);

    /**
     * Method description
     *
     */
    public native void MoveHip();
}


//~ Formatted in DD Std on 13/08/26
