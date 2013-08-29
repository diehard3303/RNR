/*
 * @(#)StopScenarioBranchAction.java   13/08/28
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


package rnr.src.scriptActions;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrloggers.ScenarioLogger;
import rnr.src.scenarioMachine.FiniteStateMachine;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class StopScenarioBranchAction extends ScenarioBranchAction {
    static final long serialVersionUID = 0L;

    /**
     * Constructs ...
     *
     *
     * @param machine
     */
    public StopScenarioBranchAction(FiniteStateMachine machine) {
        super(machine);
    }

    /**
     * Constructs ...
     *
     *
     * @param scenarioNodeName
     * @param machine
     */
    public StopScenarioBranchAction(String scenarioNodeName, FiniteStateMachine machine) {
        super(scenarioNodeName, machine);
    }

    private String findNodeName(Pattern pattern) {
        Set<String> activeStates = this.machine.getStatesNames();

        for (String stateName : activeStates) {
            if (pattern.matcher(stateName).matches()) {
                return stateName;
            }
        }

        return null;
    }

    private String findActiveNodeName(Pattern pattern) {
        Collection<String> activeStates = this.machine.getCurrentActiveStates();

        for (String stateName : activeStates) {
            if (pattern.matcher(stateName).matches()) {
                return stateName;
            }
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getDestination() {
        Pattern pattern = Pattern.compile(this.name + "(" + "_phase_" + "\\d+)?");
        String startOfWay = findNodeName(pattern);

        if (null != startOfWay) {
            return this.machine.findEndNode(startOfWay);
        }

        return null;
    }

    /**
     * Method description
     *
     */
    @Override
    public void act() {
        if (!(validate())) {
            ScenarioLogger.getInstance().machineWarning(
                "StartScenarioBranchAction instance wasn't correctly initialized");

            return;
        }

        Pattern pattern = Pattern.compile(this.name + "(" + "_phase_" + "\\d+)?");
        String startOfWay = findActiveNodeName(pattern);

        if (null != startOfWay) {
            this.machine.addNodeToWalkFromToEnd(startOfWay);
            ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + super.getClass().getName());
        } else {
            ScenarioLogger.getInstance().machineWarning(this.name
                    + "node wasn't found while trying to stop scenario branch");
        }
    }
}


//~ Formatted in DD Std on 13/08/28
