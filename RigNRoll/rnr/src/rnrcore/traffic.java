/*
 * @(#)traffic.java   13/08/26
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
public final class traffic {
    private static final int CUT_SCENE_RADIUS = 1000;

    /** Field description */
    public static final int REGULAR_MODE = 0;

    /** Field description */
    public static final int CHASE_MODE = 1;

    /** Field description */
    public static final int CUT_SCENE_MODE = 1;

    /**
     * Method description
     *
     */
    public static native void restoreTrafficImmediately();

    /**
     * Method description
     *
     */
    public static native void restoreTrafficImmediatelyTemporary();

    /**
     * Method description
     *
     */
    public static native void cleanTrafficSmooth();

    /**
     * Method description
     *
     */
    public static native void cleanTrafficImmediately();

    /**
     * Method description
     *
     *
     * @param paramDouble
     * @param paramvectorJ
     */
    public static native void cleanTrafficSmooth(double paramDouble, vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramDouble
     * @param paramvectorJ
     */
    public static native void cleanTrafficImmediatelyTemporary(double paramDouble, vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void setTrafficMode(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void setTrafficModeTemporary(int paramInt);

    /**
     * Method description
     *
     *
     * @param position
     */
    public static void enterCutSceneMode(vectorJ position) {
        setTrafficMode(1);
        cleanTrafficSmooth(1000.0D, position);
    }

    /**
     * Method description
     *
     */
    public static void enterCutSceneModeImmediately() {
        setTrafficMode(1);
        cleanTrafficImmediately();
    }

    /**
     * Method description
     *
     */
    public static void enterChaseModeSmooth() {
        setTrafficMode(1);
        cleanTrafficSmooth();
    }

    /**
     * Method description
     *
     */
    public static void enterChaseModeImmediately() {
        setTrafficMode(1);
        cleanTrafficImmediately();
    }
}


//~ Formatted in DD Std on 13/08/26
