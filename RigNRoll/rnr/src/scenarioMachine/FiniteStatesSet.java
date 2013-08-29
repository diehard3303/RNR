/*
 * @(#)FiniteStatesSet.java   13/08/27
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


package rnr.src.scenarioMachine;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrloggers.ScenarioLogger;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public final class FiniteStatesSet {
    private static final int DEFAULT_SET_CAPACITY = 128;
    private final HashMap<String, FiniteState> statesSet;

    /**
     * Constructs ...
     *
     */
    public FiniteStatesSet() {
        this.statesSet = new HashMap<String, FiniteState>(128);
    }

    /**
     * Method description
     *
     *
     * @param state
     */
    public void addState(FiniteState state) {
        if (null == state) {
            throw new IllegalArgumentException("state must be non-null reference");
        }

        String stateName = state.getName();

        if (!(this.statesSet.containsKey(stateName))) {
            this.statesSet.put(stateName, state);
        } else {
            ScenarioLogger.getInstance().machineLog(Level.WARNING, "failed to add state to set: state already exists");
        }
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public FiniteState findState(String name) {
        return (this.statesSet.get(name));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Map<String, FiniteState> getStates() {
        return Collections.unmodifiableMap(this.statesSet);
    }

    /**
     * @return the defaultSetCapacity
     */
    public static int getDefaultSetCapacity() {
        return DEFAULT_SET_CAPACITY;
    }
}


//~ Formatted in DD Std on 13/08/27
