/*
 * @(#)ScriptAction.java   13/08/28
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

import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.LinkedList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public abstract class ScriptAction implements Comparable<ScriptAction> {
    static final int HIGH_PRIORITY = 4;
    static final int MEDIUM_PRIORITY = 8;
    static final int LOW_PRIORITY = 16;

    /** Field description */
    public static final String DATA_NODE_STRING = "data";
    private final int priority;

    /**
     * Constructs ...
     *
     */
    public ScriptAction() {
        this.priority = 4;
    }

    /**
     * Constructs ...
     *
     *
     * @param priority
     */
    public ScriptAction(int priority) {
        this.priority = priority;
    }

    /**
     * Method description
     *
     *
     * @param action
     *
     * @return
     */
    public static List<ScriptAction> pack(ScriptAction action) {
        List<ScriptAction> result = new LinkedList<ScriptAction>();

        result.add(action);

        return result;
    }

    /**
     * Method description
     *
     *
     * @param o
     *
     * @return
     */
    @Override
    public int compareTo(ScriptAction o) {
        if (null == o) {
            return -1;
        }

        if (this.priority < o.priority) {
            return -1;
        }

        if (this.priority > o.priority) {
            return 1;
        }

        return 0;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean validate() {
        return true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean hasChildAction() {
        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ScriptAction getChildAction() {
        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean actActionAsScenarioNode() {
        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ScriptEvent getExactEventForConditionOnActivate() {
        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ScriptEvent getExactEventForConditionOnDeactivate() {
        return null;
    }

    /**
     * Method description
     *
     */
    public abstract void act();
}


//~ Formatted in DD Std on 13/08/28
