/*
 * @(#)WHRating.java   13/08/26
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


package rnr.src.rnrrating;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class WHRating {

    /** Field description */
    public static final int firstPlace = 0;

    /** Field description */
    public static final int secondPlace = 1;

    /** Field description */
    public static final int thirdPlace = 2;

    /** Field description */
    public static int[] series_GOLD = {
        6, 70, 1650, 14000, 3000000, 10000000
    };

    /** Field description */
    public static int[] series_SILVER = {
        (int) (series_GOLD[0] * 0.8D), (int) (series_GOLD[1] * 0.8D), (int) (series_GOLD[2] * 0.8D),
        (int) (series_GOLD[3] * 0.8D), (int) (series_GOLD[4] * 0.8D), (int) (series_GOLD[5] * 0.8D)
    };

    /** Field description */
    public static final int[] series_BRONZE = {
        (int) (series_GOLD[0] * 0.5D), (int) (series_GOLD[1] * 0.5D), (int) (series_GOLD[2] * 0.5D),
        (int) (series_GOLD[3] * 0.5D), (int) (series_GOLD[4] * 0.5D), (int) (series_GOLD[5] * 0.5D)
    };

    /** Field description */
    public static int[] series_FINISH = {
        0, 0, 0, 0, 0, 0
    };

    /** Field description */
    public static double penaltyCoeffecient = 0.3D;

    /** Field description */
    public static int[] series_PENALTY = {
        -(int) (series_GOLD[0] * penaltyCoeffecient), -(int) (series_GOLD[1] * penaltyCoeffecient),
        -(int) (series_GOLD[2] * penaltyCoeffecient), -(int) (series_GOLD[3] * penaltyCoeffecient),
        -(int) (series_GOLD[4] * penaltyCoeffecient), -(int) (series_GOLD[5] * penaltyCoeffecient)
    };

    /**
     * Method description
     *
     */
    public static final void renew() {
        for (int i = 0; i < 6; ++i) {
            series_SILVER[i] = (int) (series_GOLD[i] * 0.8D);
            series_BRONZE[i] = (int) (series_GOLD[i] * 0.5D);
            series_FINISH[i] = 0;
            series_PENALTY[i] = (-(int) (series_GOLD[i] * penaltyCoeffecient));
        }
    }

    protected static int gRating(int place) {
        switch (place) {
         case 0 :
             return series_GOLD[0];

         case 1 :
             return series_SILVER[0];

         case 2 :
             return series_BRONZE[0];
        }

        return series_FINISH[0];
    }

    protected static int gRating(int place, int series) {
        switch (place) {
         case 0 :
             return series_GOLD[series];

         case 1 :
             return series_SILVER[series];

         case 2 :
             return series_BRONZE[series];
        }

        return series_FINISH[series];
    }

    protected static int leaveDelieveryPenalty() {
        return series_PENALTY[0];
    }

    protected static int leaveBigRacePenalty(int series) {
        return series_PENALTY[series];
    }
}


//~ Formatted in DD Std on 13/08/26
