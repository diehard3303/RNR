/*
 * @(#)AdvancedRandom.java   13/08/28
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

//~--- JDK imports ------------------------------------------------------------

import java.util.Random;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class AdvancedRandom {
    private static Random rnd = null;

    /**
     * Method description
     *
     *
     * @param Min
     * @param Max
     *
     * @return
     */
    public static final int RandFromInreval(int Min, int Max) {
        if (null == rnd) {
            rnd = new Random();
        }

        return (Min + (int) Math.round(rnd.nextDouble() * (Max - Min)));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static final double getRandomDouble() {
        if (null == rnd) {
            rnd = new Random();
        }

        return rnd.nextDouble();
    }

    /**
     * Method description
     *
     *
     * @param value
     *
     * @return
     */
    public static final boolean probability(double value) {
        if (null == rnd) {
            rnd = new Random();
        }

        return (rnd.nextDouble() < value);
    }
}


//~ Formatted in DD Std on 13/08/28
