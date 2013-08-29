/*
 * @(#)ShootingSeriesAnimation.java   13/08/26
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


package rnr.src.rnrscenario.animation;

//~--- JDK imports ------------------------------------------------------------

import java.util.Random;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class ShootingSeriesAnimation {
    static final int RELAXATION_MAX_DELTA = 2;
    static final int RELAXATION_MIN_TIME = 2;
    static final int MAX_TIME_TO_SHOOT_DELTA = 4;
    static final int MIN_TIME_TO_SHOOT = 2;
    private final Random random;
    private double lastSeriesTimeEnd;
    private double currentTime;
    private double previousCounterValue;
    private double relaxationTime;
    private boolean shooting;

    /**
     * Constructs ...
     *
     */
    public ShootingSeriesAnimation() {
        this.random = new Random(System.nanoTime());
        this.lastSeriesTimeEnd = 0.0D;
        this.currentTime = 0.0D;
        this.previousCounterValue = 0.0D;
        this.relaxationTime = 0.0D;
        this.shooting = false;
    }

    /**
     * Method description
     *
     *
     * @param externalCounterValue
     */
    public void animate(double externalCounterValue) {
        if (0.0D == this.previousCounterValue) {
            startShooting();
            this.previousCounterValue = externalCounterValue;

            return;
        }

        this.currentTime += externalCounterValue - this.previousCounterValue;
        this.previousCounterValue = externalCounterValue;

        if (this.shooting) {
            if (this.currentTime <= this.lastSeriesTimeEnd) {
                return;
            }

            this.shooting = false;
            this.relaxationTime = (this.random.nextDouble() * 2.0D + 2.0D);
        } else {
            if (this.currentTime <= this.lastSeriesTimeEnd + this.relaxationTime) {
                return;
            }

            startShooting();
        }
    }

    private void startShooting() {
        this.lastSeriesTimeEnd = (this.currentTime + 4.0D * this.random.nextDouble() + 2.0D);
        this.shooting = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isShooting() {
        return this.shooting;
    }
}


//~ Formatted in DD Std on 13/08/26
