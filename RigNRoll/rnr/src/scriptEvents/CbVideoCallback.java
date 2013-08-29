/*
 * @(#)CbVideoCallback.java   13/08/27
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
import rnr.src.scriptActions.ScriptAction;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public class CbVideoCallback {
    private final LinkedList<ScriptAction> onStartActions = new LinkedList<ScriptAction>();
    private final LinkedList<ScriptAction> onFinishActions = new LinkedList<ScriptAction>();
    private HashMap<Integer, LinkedList<ScriptAction>> onAnswerActions = null;

    /**
     * Constructs ...
     *
     *
     * @param answersCount
     */
    public CbVideoCallback(int answersCount) {
        if (0 <= answersCount) {
            this.onAnswerActions = new HashMap<Integer, LinkedList<ScriptAction>>(answersCount);
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.<init>: came invalid answersCount param == "
                    + answersCount);
            this.onAnswerActions = new HashMap<Integer, LinkedList<ScriptAction>>();
        }
    }

    /**
     * Method description
     *
     *
     * @param action
     */
    public void addOnStartAction(ScriptAction action) {
        if (null != action) {
            this.onStartActions.add(action);
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.addOnStartAction: invalid argument(s)");
        }
    }

    /**
     * Method description
     *
     *
     * @param action
     */
    public void addOnFinishAction(ScriptAction action) {
        if (null != action) {
            this.onFinishActions.add(action);
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.addOnFinishAction: invalid argument(s)");
        }
    }

    /**
     * Method description
     *
     *
     * @param answerNom
     * @param action
     */
    public void addOnAnswerAction(int answerNom, ScriptAction action) {
        if ((null != action) && (0 <= answerNom)) {
            if (!(this.onAnswerActions.containsKey(Integer.valueOf(answerNom)))) {
                this.onAnswerActions.put(Integer.valueOf(answerNom), new LinkedList<ScriptAction>());
            }

            this.onAnswerActions.get(Integer.valueOf(answerNom)).add(action);
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.addOnAnswerAction: invalid argument(s)");
        }
    }

    /**
     * Method description
     *
     *
     * @param answerNom
     * @param toAdd
     */
    public void addOnAnswerActions(int answerNom, Collection<ScriptAction> toAdd) {
        if ((null != toAdd) && (0 <= answerNom)) {
            if (!(this.onAnswerActions.containsKey(Integer.valueOf(answerNom)))) {
                this.onAnswerActions.put(Integer.valueOf(answerNom), new LinkedList<ScriptAction>());
            }

            this.onAnswerActions.get(Integer.valueOf(answerNom)).addAll(toAdd);
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.addOnAnswerAction: invalid argument(s)");
        }
    }

    /**
     * Method description
     *
     *
     * @param toAdd
     */
    public void addOnStartActions(Collection<ScriptAction> toAdd) {
        if ((null == toAdd) || (0 >= toAdd.size())) {
            return;
        }

        this.onStartActions.addAll(toAdd);
    }

    /**
     * Method description
     *
     *
     * @param toAdd
     */
    public void addOnFinishActions(Collection<ScriptAction> toAdd) {
        if ((null == toAdd) || (0 >= toAdd.size())) {
            return;
        }

        this.onFinishActions.addAll(toAdd);
    }

    void onStart() {
        for (ScriptAction action : this.onStartActions) {
            action.act();
        }
    }

    void onFinish() {
        for (ScriptAction action : this.onFinishActions) {
            action.act();
        }
    }

    void onAnswer(int answerNomber) {
        if ((0 <= answerNomber) && (this.onAnswerActions.containsKey(Integer.valueOf(answerNomber)))) {
            LinkedList<ScriptAction> onNthAnswerActions = this.onAnswerActions.get(Integer.valueOf(answerNomber));

            for (ScriptAction action : onNthAnswerActions) {
                action.act();
            }
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.onAnswer: invalid argument(s)");
        }
    }

    @SuppressWarnings("unchecked")
    List<ScriptAction> getActionList() {
        LinkedList<ScriptAction> out = new LinkedList<ScriptAction>();

        out.addAll(this.onStartActions);
        out.addAll(this.onFinishActions);

        for (List<?> toAddInOut : this.onAnswerActions.values()) {
            out.addAll((Collection<? extends ScriptAction>) toAddInOut);
        }

        return out;
    }
}


//~ Formatted in DD Std on 13/08/27
