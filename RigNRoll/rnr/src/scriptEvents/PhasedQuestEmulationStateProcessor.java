/*
 * @(#)PhasedQuestEmulationStateProcessor.java   13/08/27
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


package rnr.src.scriptEvents;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrloggers.ScenarioLogger;
import rnr.src.scenarioMachine.FiniteState;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public final class PhasedQuestEmulationStateProcessor extends UniversalStateProcessor {

    /**
     * Method description
     *
     *
     * @param event
     * @param avalibleStates
     * @param currentState
     *
     * @return
     */
    public FiniteState processEvent(List<ScriptEvent> event, LinkedList<FiniteState> avalibleStates,
                                    FiniteState currentState) {
        if ((null == event) || (null == avalibleStates) || (null == currentState)) {
            ScenarioLogger.getInstance().machineWarning(
                "IsoQuestEmulationStateProcessor.processEvent: invalid arguments");

            return currentState;
        }

        logEvent(event);

        FiniteState newState = currentState;

        for (EventReaction reactor : this.reactionsOnEvents) {
            if ((reactor.react(event)) && (reactor.getLastReacted() instanceof ScenarioStateNeedMoveEvent)) {
                ScenarioStateNeedMoveEvent advanceScenarioEvent = (ScenarioStateNeedMoveEvent) reactor.getLastReacted();
                String newStateName = advanceScenarioEvent.getNodeStringName();
                FiniteState transitionTarget = null;

                for (FiniteState avalibleState : avalibleStates) {
                    if (0 == newStateName.compareTo(avalibleState.getName())) {
                        if (null != transitionTarget) {
                            ScenarioLogger.getInstance().machineWarning(
                                "scenario logic error: multiple state choose; current state == "
                                + currentState.getName());
                        }

                        transitionTarget = avalibleState;
                        newState = avalibleState;
                    }
                }

                if (null == transitionTarget) {
                    ScenarioLogger.getInstance().machineWarning(
                        "scenario logic error: couldn't find next state with name " + newStateName
                        + "; current state == " + currentState);
                }
            }
        }

        return newState;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<Integer> getDataToSave() {
        return Collections.emptyList();
    }

    /**
     * Method description
     *
     *
     * @param savedData
     */
    public void restoreFromData(List<Integer> savedData) {}
}


//~ Formatted in DD Std on 13/08/27
