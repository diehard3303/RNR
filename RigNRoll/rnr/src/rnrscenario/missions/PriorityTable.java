/*
 * @(#)PriorityTable.java   13/08/28
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


package rnr.src.rnrscenario.missions;

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
public final class PriorityTable {
    private final Map<String, Integer> priorities;

    /**
     * Constructs ...
     *
     */
    public PriorityTable() {
        this.priorities = new HashMap<String, Integer>();
    }

    /**
     * Method description
     *
     *
     * @param parentName
     * @param missionName
     */
    public void movedFromDependantToActive(String parentName, String missionName) {
        Integer priority = this.priorities.get(missionName);

        if (null != priority) {
            priority = Integer.valueOf(priority.intValue() + 100);
        } else {
            registerMissionPriority(missionName, 100 + getPriority(parentName));
        }
    }

    /**
     * Method description
     *
     *
     * @param missionName
     *
     * @return
     */
    public int getPriority(String missionName) {
        if (null != missionName) {
            Integer priority = this.priorities.get(missionName);

            if (null != priority) {
                return priority.intValue();
            }
        }

        return 0;
    }

    /**
     * Method description
     *
     *
     * @param missionName
     * @param priotiry
     */
    public void registerMissionPriority(String missionName, int priotiry) {
        if (null == missionName) {
            return;
        }

        this.priorities.put(missionName, Integer.valueOf(priotiry));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Map<String, Integer> getPriorities() {
        return this.priorities;
    }
}


//~ Formatted in DD Std on 13/08/28
