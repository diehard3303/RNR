/*
 * @(#)RaceFailCondition.java   13/08/28
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

import rnr.src.rnrcore.ConvertGameTime;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.xmlserialization.nxs.AnnotatedSerializable;
import rnr.src.xmlserialization.nxs.LoadFrom;
import rnr.src.xmlserialization.nxs.SaveTo;

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
public class RaceFailCondition implements AnnotatedSerializable {

    /** Field description */
    public static final String SERIALIZATION_NODE_NAME = "RaceFailCondition";
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_TO_FAIL = 11;
    private final int scenarioStage;
    @SaveTo(
        destinationNodeName = "race_start_time",
        constructorArgumentNumber = 0,
        saveVersion = 0
    )
    @LoadFrom(
        sourceNodeName = "race_start_time",
        fromVersion = 0,
        untilVersion = 0
    )
    private CoreTime raceStartTime;

    /**
     * Constructs ...
     *
     */
    public RaceFailCondition() {
        this.scenarioStage = ((null == scenarioscript.script.getStage())
                              ? 0
                              : scenarioscript.script.getStage().getScenarioStage());
        this.raceStartTime = new CoreTime();
    }

    /**
     * Method description
     *
     */
    @Override
    public void finilizeDeserialization() {
        if (null != this.raceStartTime) {
            return;
        }

        this.raceStartTime = new CoreTime();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getId() {
        return "RaceFailCondition";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean raceFailed() {
        CoreTime timeOfEnd = ConvertGameTime.convertFromGiven(660, this.raceStartTime);

        return (0 < new CoreTime().moreThan(timeOfEnd));
    }
}


//~ Formatted in DD Std on 13/08/28
