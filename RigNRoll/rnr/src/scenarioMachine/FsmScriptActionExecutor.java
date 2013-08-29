/*
 * @(#)FsmScriptActionExecutor.java   13/08/27
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

import rnr.src.scriptActions.ScriptAction;

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
public final class FsmScriptActionExecutor implements FsmActionListener {
    private final LinkedList<ScriptAction> onEnterActions;
    private final LinkedList<ScriptAction> onLeavedActions;
    private final LinkedList<ScriptAction> onProcessActions;

    /**
     * Constructs ...
     *
     */
    public FsmScriptActionExecutor() {
        this.onEnterActions = new LinkedList<ScriptAction>();
        this.onLeavedActions = new LinkedList<ScriptAction>();
        this.onProcessActions = new LinkedList<ScriptAction>();
    }

    /**
     * Method description
     *
     *
     * @param state
     */
    @Override
    public void stateEntered(FiniteState state) {
        for (ScriptAction action : this.onEnterActions) {
            action.act();
        }
    }

    /**
     * Method description
     *
     *
     * @param state
     */
    @Override
    public void stateLeaved(FiniteState state) {
        for (ScriptAction action : this.onLeavedActions) {
            action.act();
        }
    }

    /**
     * Method description
     *
     *
     * @param state
     */
    @Override
    public void stateProcessed(FiniteState state) {
        for (ScriptAction action : this.onProcessActions) {
            action.act();
        }
    }

    void addOnEnterAction(ScriptAction action) {
        if (null == action) {
            throw new IllegalArgumentException("action must be non-null reference");
        }

        this.onEnterActions.add(action);
    }

    void addOnLeaveAction(ScriptAction action) {
        if (null == action) {
            throw new IllegalArgumentException("action must be non-null reference");
        }

        this.onLeavedActions.add(action);
    }

    void addOnProcessAction(ScriptAction action) {
        if (null == action) {
            throw new IllegalArgumentException("action must be non-null reference");
        }

        this.onProcessActions.add(action);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public List<ScriptAction> getAllAvalibleActions() {
        return Collections.emptyList();
    }
}


//~ Formatted in DD Std on 13/08/27
