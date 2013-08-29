/*
 * @(#)Cabin.java   13/08/26
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


package rnr.src.rnrconfig;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Cabin {

    /** Field description */
    public static final int VOID_GAUGE = 0;

    /** Field description */
    public static final int SPEDOMETR_GAUGE = 1;

    /** Field description */
    public static final int TACHMETR_GAUGE = 2;

    /** Field description */
    public static final int FUEL_GAUGE = 3;

    /** Field description */
    public static final int OILTEMPER_GAUGE = 4;

    /** Field description */
    public static final int WATERTEMP_GAUGE = 5;

    /** Field description */
    public static final int OILPREASU_GAUGE = 6;

    /** Field description */
    public static final int AIRPREASU_GAUGE = 7;

    /** Field description */
    public static final int AIRAPPLIE_GAUGE = 8;

    /** Field description */
    public static final int WIPERS_GAUGE = 9;

    /** Field description */
    public static final int AIRPESASUSECONDARY_GAUGE = 10;

    /** Field description */
    public static final int OILBOXTEMP_GAUGE = 11;

    /** Field description */
    public static final int AXLEFRONTTEMP_GAUGE = 12;

    /** Field description */
    public static final int AXLEREARTEMP_GAUGE = 13;

    /** Field description */
    public static final int VOLT_GAUGE = 14;

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramInt
     *
     * @return
     */
    @Deprecated
    public static native long CreateGAUGE(long paramLong, String paramString, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramDouble1
     * @param paramDouble2
     * @param paramDouble3
     * @param paramDouble4
     * @param paramDouble5
     */
    public static native void TuneGAUGE(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3,
            double paramDouble4, double paramDouble5);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramDouble1
     * @param paramDouble2
     */
    public static native void AddControlLight(long paramLong, String paramString, double paramDouble1,
            double paramDouble2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramInt
     *
     * @return
     */
    @Deprecated
    public static native long CreateLight(long paramLong, String paramString, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramInt
     * @param paramBoolean
     *
     * @return
     */
    public static native long CreateGAUGE(long paramLong, String paramString, int paramInt, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     * @param paramInt
     * @param paramBoolean
     *
     * @return
     */
    public static native long CreateLight(long paramLong, String paramString, int paramInt, boolean paramBoolean);
}


//~ Formatted in DD Std on 13/08/26
