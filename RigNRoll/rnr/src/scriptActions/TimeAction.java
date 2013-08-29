/*
 * @(#)TimeAction.java   13/08/28
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
import rnr.src.rnrscenario.EndScenario;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.scriptEvents.ScriptEvent;
import rnr.src.scriptEvents.VoidEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class TimeAction extends ScenarioAction {
    static final long serialVersionUID = 0L;

    /** Field description */
    public String name = null;

    /** Field description */
    public int days = -1;

    /** Field description */
    public int hours = -1;

    /** Field description */
    public ScriptAction action = null;
    ScriptEvent exactEvent_on_activate = null;
    ScriptEvent exactEvent_on_deactivate = null;

    /**
     * Constructs ...
     *
     *
     * @param scenario
     */
    public TimeAction(scenarioscript scenario) {
        super(scenario);
    }

    /**
     * Constructs ...
     *
     *
     * @param name
     * @param numdays
     * @param action
     * @param scenario
     */
    public TimeAction(String name, Integer numdays, ScriptAction action, scenarioscript scenario) {
        super(scenario);
        this.name = name;
        this.days = numdays.intValue();
        this.action = action;
    }

    /**
     * Method description
     *
     */
    @Override
    public void act() {
        if (!(validate())) {
            ScenarioLogger.getInstance().machineWarning(
                "TimeAction instance wasn't correctly initialized! scenario system could work uncorrect");

            return;
        }

        if (null != this.action) {
            ScenarioLogger.getInstance().machineLog(Level.INFO, "action performed " + super.getClass().getName());
            this.hours = Math.max(0, this.hours);
            EndScenario.getInstance().delayAction(this.name, this.days, this.hours, this.exactEvent_on_activate,
                    this.exactEvent_on_deactivate);
        } else {
            ScenarioLogger.getInstance().machineLog(Level.WARNING,
                    "action hasn't been performed " + super.getClass().getName());
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean validate() {
        return ((0 <= this.days) && (null != this.name));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean hasChildAction() {
        return true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public ScriptAction getChildAction() {
        return this.action;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean actActionAsScenarioNode() {
        return true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public ScriptEvent getExactEventForConditionOnActivate() {
        if (this.exactEvent_on_activate == null) {
            this.exactEvent_on_activate = new VoidEvent(this.name + " activation.");
        }

        return this.exactEvent_on_activate;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public ScriptEvent getExactEventForConditionOnDeactivate() {
        if (null == this.exactEvent_on_deactivate) {
            this.exactEvent_on_deactivate = new VoidEvent(this.name + " deactivation.");
        }

        return this.exactEvent_on_deactivate;
    }
}


//~ Formatted in DD Std on 13/08/28
