/*
 * @(#)SingleStepScenarioAdvanceAction.java   13/08/28
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
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.ScenarioStateNeedMoveEvent;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collection;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public class SingleStepScenarioAdvanceAction extends ScriptAction {
    static final long serialVersionUID = 0L;
    private static final int DEFAULT_PHASE_VALUE = -1;
    private String name = null;
    private int phase = -1;
    private FiniteStateMachine machine = null;

    /**
     * Constructs ...
     *
     *
     * @param machine
     */
    public SingleStepScenarioAdvanceAction(FiniteStateMachine machine) {
        if (null == machine) {
            throw new IllegalArgumentException("machine must be non-null reference");
        }

        this.name = "unknown";
        this.machine = machine;
    }

    /**
     * Constructs ...
     *
     *
     * @param nextScenario
     * @param machine
     */
    public SingleStepScenarioAdvanceAction(String nextScenario, FiniteStateMachine machine) {
        super(16);

        if (null == machine) {
            throw new IllegalArgumentException("machine must be non-null reference");
        }

        if (null == nextScenario) {
            throw new IllegalArgumentException("nextScenario must be non-null reference");
        }

        this.machine = machine;
        this.name = nextScenario;
    }

    /**
     * Constructs ...
     *
     *
     * @param name
     * @param state
     * @param machine
     */
    public SingleStepScenarioAdvanceAction(String name, int state, FiniteStateMachine machine) {
        if (null == machine) {
            throw new IllegalArgumentException("machine must be non-null reference");
        }

        this.machine = machine;
        this.name = name;
        this.phase = state;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getDestination() {
        if (-1 != this.phase) {
            return this.name + "_phase_" + this.phase;
        }

        return this.name;
    }

    /**
     * Method description
     *
     */
    @Override
    public void act() {
        if (!(validate())) {
            ScenarioLogger.getInstance().machineWarning(
                "SingleStepScenarioAdvanceAction instance wasn't correctly initialized");

            return;
        }

        if (-1 == this.phase) {
            if (!(this.name.contains("_phase_"))) {
                Collection<String> avalibleStatesToMoveFrom = this.machine.getCurrentActiveStates();
                boolean wasFound = false;

                for (String stateName : avalibleStatesToMoveFrom) {
                    if (stateName.contains(this.name)) {
                        this.name = stateName;
                        wasFound = true;

                        break;
                    }
                }

                if (!(wasFound)) {
                    ScenarioLogger.getInstance().machineWarning(
                        "action SingleStepScenarioAdvanceAction couldn't be performed: " + this.name
                        + " was not found");

                    return;
                }
            }
        } else {
            SingleStepScenarioAdvanceAction tmp148_147 = this;

            tmp148_147.name = tmp148_147.name + "_phase_" + this.phase;
        }

        ScenarioLogger.getInstance().machineLog(Level.INFO,
                "action performed " + super.getClass().getName() + " state name == " + this.name);
        EventsController.getInstance().eventHappen(new ScriptEvent[] { new ScenarioStateNeedMoveEvent(this.name, -1) });
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean validate() {
        return ((null != this.name) && (null != this.machine));
    }
}


//~ Formatted in DD Std on 13/08/28
