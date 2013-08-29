/*
 * @(#)RACEspace_conditions.java   13/08/28
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.RaceTrajectory;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.consistency.ScenarioClass;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = -1,
    fieldWithDesiredStage = "scenarioStage"
)
public class RACEspace_conditions {

    /** Field description */
    public static final int onlast = 1;

    /** Field description */
    public static final int onvip = 16;

    /** Field description */
    public static final int onfirst = 256;

    /** Field description */
    public static final int onforcemojor = 4096;
    private vectorJ cachedPointStartt = new vectorJ();
    private vectorJ cachedPointFinish = new vectorJ();
    private final double delta_to_finish = 150.0D;
    private int condition = 16;
    @SuppressWarnings("unused")
    private final int scenarioStage;
    private final String racename;

    RACEspace_conditions(String _racename, int desiredScenarioStage) {
        this.scenarioStage = desiredScenarioStage;
        this.racename = _racename;
        this.cachedPointStartt = RaceTrajectory.getStart(this.racename);
        this.cachedPointFinish = RaceTrajectory.getFinish(this.racename);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String gRaceName() {
        return this.racename;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public vectorJ getStartPosition() {
        return this.cachedPointStartt;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public vectorJ getFinishPosition() {
        return this.cachedPointFinish;
    }

    /**
     * Method description
     *
     *
     * @param pos
     *
     * @return
     */
    public double checkPosition(vectorJ pos) {
        return this.cachedPointFinish.len2(pos);
    }

    /**
     * Method description
     *
     *
     * @param pos
     *
     * @return
     */
    public boolean checkPositionOnFinish(double pos) {
        return (pos < this.delta_to_finish * this.delta_to_finish);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getDelta() {
        return this.delta_to_finish;
    }

    /**
     * Method description
     *
     *
     * @param cond
     */
    public void setcondition(int cond) {
        this.condition = cond;
    }

    /**
     * Method description
     *
     *
     * @param cond
     *
     * @return
     */
    public boolean compareConditions(int cond) {
        return ((cond & this.condition) != 0);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getCondition() {
        return this.condition;
    }

    /**
     * Method description
     *
     *
     * @param condition
     */
    public void setCondition(int condition) {
        this.condition = condition;
    }
}


//~ Formatted in DD Std on 13/08/28
