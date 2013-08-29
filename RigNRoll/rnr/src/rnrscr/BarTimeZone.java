/*
 * @(#)BarTimeZone.java   13/08/28
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class BarTimeZone {
    private int manCount;
    private int womanCount;

    /**
     * Constructs ...
     *
     */
    public BarTimeZone() {
        this.manCount = 0;
        this.womanCount = 0;
    }

    /**
     * Method description
     *
     *
     * @param hour
     * @param Scale
     * @param isEgg
     */
    public void ProcessZone(int hour, double Scale, boolean isEgg) {
        int peopleCount = 5;

        if ((hour >= 0) && (hour < 8)) {
            peopleCount = (int) (Scale * AdvancedRandom.RandFromInreval(1, 2));
        }

        if ((hour >= 8) && (hour < 10)) {
            peopleCount = (int) (Scale * AdvancedRandom.RandFromInreval(10, 15));
        }

        if (((hour >= 10) && (hour < 13)) || ((hour >= 14) && (hour < 18))) {
            peopleCount = (int) (Scale * AdvancedRandom.RandFromInreval(5, 8));
        }

        if ((hour >= 13) && (hour < 14)) {
            peopleCount = (int) (Scale * AdvancedRandom.RandFromInreval(12, 17));
        }

        if ((hour >= 18) && (hour <= 23)) {
            peopleCount = (int) (Scale * AdvancedRandom.RandFromInreval(15, 19));
        }

        if (peopleCount == 1) {
            this.manCount = ((AdvancedRandom.RandFromInreval(1, 9) > 7)
                             ? 0
                             : 1);
        } else if (peopleCount == 2) {
            this.manCount = ((AdvancedRandom.RandFromInreval(1, 9) > 7)
                             ? 1
                             : 2);
        } else {
            this.manCount = AdvancedRandom.RandFromInreval(2 * peopleCount / 3, peopleCount);
        }

        this.womanCount = (peopleCount - this.manCount);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int GetManCount() {
        return this.manCount;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int GetWomanCount() {
        return this.womanCount;
    }
}


//~ Formatted in DD Std on 13/08/28
