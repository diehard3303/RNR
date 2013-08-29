/*
 * @(#)Start0600.java   13/08/28
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


package rnr.src.rnrscenario.controllers.starters;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.missions.starters.ConditionChecker;
import rnr.src.rnrscenario.missions.starters.StarterBase;
import rnr.src.xmlserialization.nxs.DeserializationConstructor;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 8,
    fieldWithDesiredStage = ""
)
public final class Start0600 extends StarterBase {

    /**
     * Constructs ...
     *
     */
    public Start0600() {
        CoreTime currentTime = new CoreTime();

        currentTime.plus_days(2);
        currentTime.sHour(23);
        currentTime.sMinute(59);
        this.time_mission_end = new CoreTime(currentTime);
    }

    /**
     * Method description
     *
     *
     * @param missionName
     *
     * @return
     */
    @Override
    public ConditionChecker getConditionChecker(String missionName) {
        return null;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    @ScenarioClass(
        scenarioStage = 8,
        fieldWithDesiredStage = ""
    )
    public static class Checker extends ConditionChecker {

        /**
         * Constructs ...
         *
         *
         * @param missionName
         */
        @DeserializationConstructor(
            fromVersion = 0,
            untilVersion = 0
        )
        public Checker(String missionName) {
            super(missionName, getUid());
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public static String getUid() {
            return "Start0600";
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public boolean check() {
            return true;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
