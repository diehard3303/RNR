/*
 * @(#)MakeCallAction.java   13/08/28
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

import rnr.src.rnrcore.eng;
import rnr.src.rnrloggers.ScenarioLogger;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.scriptEvents.CbVideoEvent;
import rnr.src.scriptEvents.CbVideoEvent.EventType;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public class MakeCallAction extends ScenarioAction {

    /** Field description */
    public String name = "Undefined";

    /** Field description */
    public boolean immediate = false;

    /**
     * Constructs ...
     *
     *
     * @param scenario
     */
    public MakeCallAction(scenarioscript scenario) {
        super(scenario);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void setCallName(String name) {
        if (null == name) {
            throw new IllegalArgumentException("name must be non-null reference");
        }

        this.name = name;
    }

    /**
     * Method description
     *
     */
    @Override
    public void act() {
        ScenarioLogger.getInstance().machineLog(Level.INFO,
                "action performed " + super.getClass().getName() + " " + this.name);

        if (!(eng.noNative)) {
            if (!(this.immediate)) {
                getScript().launchCall(this.name);
            } else {
                getScript().immediateLaunchCall(this.name);
            }
        } else {
            EventsController.getInstance().eventHappen(new ScriptEvent[] {
                new CbVideoEvent(this.name, CbVideoEvent.EventType.START, 0) });
            EventsController.getInstance().eventHappen(new ScriptEvent[] {
                new CbVideoEvent(this.name, CbVideoEvent.EventType.FINISH, 0) });
        }
    }
}


//~ Formatted in DD Std on 13/08/28
