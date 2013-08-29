/*
 * @(#)MissionAppearConditions.java   13/08/28
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


package rnr.src.rnrscenario.missions.requirements;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class MissionAppearConditions {
    private static final int MAP_CAPACITY = 64;
    private final Map<String, Requirement> conditions;

    /**
     * Constructs ...
     *
     */
    public MissionAppearConditions() {
        this.conditions = new HashMap<String, Requirement>(64);
    }

    /**
     * Method description
     *
     *
     * @param missionName
     * @param condition
     */
    public void addRequirement(String missionName, Requirement condition) {
        assert(null != condition) : "condition must be non-null reference";
        assert(null != missionName) : "missionName must be non-null reference";
        this.conditions.put(missionName, condition);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public boolean missionAvalible(String name) {
        assert(null != name) : "name must be non-null reference";

        Requirement condition = this.conditions.get(name);

        return ((null == condition) || (condition.check()));
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public Requirement getRequirement(String name) {
        if (null != name) {
            return (this.conditions.get(name));
        }

        return null;
    }
}


//~ Formatted in DD Std on 13/08/28
