/*
 * @(#)IsoQuestEmulationStateProcessor.java   13/08/28
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
import rnr.src.scriptActions.ScenarioAnalysisMarkAction;
import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptActions.SingleStepScenarioAdvanceAction;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public final class IsoQuestEmulationStateProcessor extends UniversalStateProcessor {
    private boolean transitionDone = false;
    private String implicitNodeName = null;
    @SuppressWarnings("unchecked")
    private final List<Integer> doneReactions = new LinkedList();

    /**
     * Constructs ...
     *
     *
     * @param implicitStepNextNodeName
     */
    public IsoQuestEmulationStateProcessor(String implicitStepNextNodeName) {
        this.implicitNodeName = implicitStepNextNodeName;
    }

    private static FiniteState findState(List<FiniteState> avalibleStates, String name)
            throws IsoQuestEmulationStateProcessor.MultipleChoseException,
                   IsoQuestEmulationStateProcessor.NodeNotFoundException {
        if (null == name) {
            throw new IllegalArgumentException("name must be non-null reference");
        }

        FiniteState found = null;

        for (FiniteState candidate : avalibleStates) {
            if (0 == name.compareTo(candidate.getName())) {
                if (null != found) {
                    throw new IsoQuestEmulationStateProcessor.MultipleChoseException(
                        "scenario logic error: multiple state choose " + name);
                }

                found = candidate;

                break;
            }
        }

        if (null != found) {
            return found;
        }

        throw new IsoQuestEmulationStateProcessor.NodeNotFoundException("can't find node with name " + name);
    }

    /**
     * Method description
     *
     *
     * @param implicitStepNextNodeName
     */
    public void setImplicitStepNextNodeName(String implicitStepNextNodeName) {
        this.implicitNodeName = implicitStepNextNodeName;
    }

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
    @Override
    public FiniteState processEvent(List<ScriptEvent> event, LinkedList<FiniteState> avalibleStates,
                                    FiniteState currentState) {
        if ((null == event) || (null == avalibleStates) || (null == currentState)) {
            ScenarioLogger.getInstance().machineLog(Level.SEVERE,
                    "IsoQuestEmulationStateProcessor.processEvent: invalid arguments");

            return currentState;
        }

        logEvent(event);

        FiniteState newState = currentState;
        boolean atLeastOneReacted = false;

        for (ListIterator<EventReaction> iter = this.reactionsOnEvents.listIterator(); iter.hasNext(); ) {
            EventReaction reactor = iter.next();

            if (reactor.react(event)) {
                if (0 != reactor.getUid()) {
                    this.doneReactions.add(Integer.valueOf(reactor.getUid()));
                }

                atLeastOneReacted = true;

                if (reactor.getLastReacted() instanceof ScenarioStateNeedMoveEvent) {
                    try {
                        String newNodeName =
                            ((ScenarioStateNeedMoveEvent) reactor.getLastReacted()).getNodeStringName();

                        newState = findState(avalibleStates, newNodeName);
                        this.transitionDone = true;
                    } catch (MultipleChoseException exception) {
                        ScenarioLogger.getInstance().machineLog(Level.SEVERE, exception.getMessage());
                    } catch (NodeNotFoundException exception) {
                        ScenarioLogger.getInstance().machineLog(Level.SEVERE, exception.getMessage());
                    }
                } else {
                    reactor.deactivateReactor();
                    iter.remove();
                }
            }
        }

        boolean effectiveReactorsEmpty = true;

        for (EventReaction reaction : this.reactionsOnEvents) {
            if ((null != reaction.getAllAvalibleReactions()) && (!(reaction.getAllAvalibleReactions().isEmpty()))) {
                effectiveReactorsEmpty = false;

                break;
            }
        }

        if ((!(this.transitionDone)) && (effectiveReactorsEmpty) && (atLeastOneReacted)
                && (null != this.implicitNodeName)) {
            try {
                newState = findState(avalibleStates, this.implicitNodeName);
            } catch (MultipleChoseException exception) {
                ScenarioLogger.getInstance().machineLog(Level.SEVERE, exception.getMessage());
            } catch (NodeNotFoundException exception) {
                ScenarioLogger.getInstance().machineLog(Level.SEVERE, exception.getMessage());
            }
        }

        return newState;
    }

    @SuppressWarnings("rawtypes")
    private boolean checkForDuplicateOfImplicitStep(List actionsToCheckForDuplicates) {
        for (Iterator i$ = actionsToCheckForDuplicates.iterator(); i$.hasNext(); ) {
            Object genericAction = i$.next();

            if (genericAction instanceof SingleStepScenarioAdvanceAction) {
                SingleStepScenarioAdvanceAction action = (SingleStepScenarioAdvanceAction) genericAction;
                String destination = action.getName();

                if (this.implicitNodeName.contains(destination)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param actionsToCheckForDuplicates
     *
     * @return
     */
    @Override
    public List<ScriptAction> getAllAvalibleActions(List actionsToCheckForDuplicates) {
        List<ScriptAction> actions = super.getAllAvalibleActions(actionsToCheckForDuplicates);

        if (null != this.implicitNodeName) {
            if ((checkForDuplicateOfImplicitStep(actions))
                    || (checkForDuplicateOfImplicitStep(actionsToCheckForDuplicates))) {
                return actions;
            }

            if (0 != this.implicitNodeName.length()) {
                actions.add(new ScenarioAnalysisMarkAction(this.implicitNodeName));
            }
        }

        return actions;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public List<Integer> getDataToSave() {
        return Collections.unmodifiableList(this.doneReactions);
    }

    /**
     * Method description
     *
     *
     * @param savedData
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unused" })
    public void restoreFromData(List<Integer> savedData) {
        if (null == savedData) {
            throw new IllegalArgumentException("savedData must be non-null");
        }

        for (Iterator<Integer> i$ = savedData.iterator(); i$.hasNext(); ) {
            Integer reactionUid = i$.next();

            for (ListIterator<EventReaction> iter = this.reactionsOnEvents.listIterator(); iter.hasNext(); ) {
                EventReaction reaction = iter.next();

                if (reactionUid.intValue() == reaction.getUid()) {
                    reaction.deactivateReactor();
                    iter.remove();
                }
            }
        }

        Integer reactionUid;
        ListIterator iter;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getImplicitNodeName() {
        return this.implicitNodeName;
    }

    private static final class MultipleChoseException extends Exception {
        static final long serialVersionUID = 1L;

        MultipleChoseException() {}

        MultipleChoseException(String message) {
            super(message);
        }
    }


    private static final class NodeNotFoundException extends Exception {
        static final long serialVersionUID = 1L;

        NodeNotFoundException() {}

        NodeNotFoundException(String message) {
            super(message);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
