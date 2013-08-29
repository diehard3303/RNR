/*
 * @(#)RACErace_state_single.java   13/08/28
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


package rnr.src.rnrscenario.controllers;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class RACErace_state_single implements Comparable<Object> {
    private boolean finished = false;
    private int place = 0;
    private double distance = 0.0D;
    @SuppressWarnings("unused")
    private final int scenarioStage;

    /**
     * Constructs ...
     *
     *
     * @param scenarioStage
     */
    public RACErace_state_single(int scenarioStage) {
        this.scenarioStage = scenarioStage;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getDistance() {
        return this.distance;
    }

    /**
     * Method description
     *
     *
     * @param distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isFinished() {
        return this.finished;
    }

    /**
     * Method description
     *
     *
     * @param finished
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getPlace() {
        return this.place;
    }

    /**
     * Method description
     *
     *
     * @param place
     */
    public void setPlace(int place) {
        this.place = place;
    }

    /**
     * Method description
     *
     *
     * @param arg0
     *
     * @return
     */
    @Override
    public int compareTo(Object arg0) {
        RACErace_state_single data = (RACErace_state_single) arg0;

        if ((this.finished) && (data.finished)) {
            return 0;
        }

        if (this.finished) {
            return -1;
        }

        if (data.finished) {
            return 1;
        }

        return (int) (this.distance - data.distance);
    }
}


//~ Formatted in DD Std on 13/08/28
