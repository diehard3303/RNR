/*
 * @(#)FiniteState.java   13/08/27
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
import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
@SuppressWarnings("rawtypes")
public final class FiniteState implements Comparable {

    /** Field description */
    public static final int DEFAULT_STATE_NOM = -1;
    @SuppressWarnings("unchecked")
    private final LinkedList<FiniteState> nextStates = new LinkedList();
    @SuppressWarnings("unchecked")
    private final LinkedList<FsmActionListener> stateChangedListeners = new LinkedList();
    private EventStateProcessor eventProcessor = null;
    private String stateName = null;
    private boolean isLeaved = false;
    private boolean isEntered = false;

    /**
     * Constructs ...
     *
     *
     * @param processor
     * @param name
     */
    public FiniteState(EventStateProcessor processor, String name) {
        if (null == name) {
            throw new IllegalArgumentException("name must be non-null reference");
        }

        this.eventProcessor = processor;
        this.stateName = name;
    }

    /**
     * Method description
     *
     *
     * @param processor
     */
    public void setEventProcessor(EventStateProcessor processor) {
        if (null == processor) {
            throw new IllegalArgumentException("processor must be non-null reference");
        }

        this.eventProcessor = processor;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final String getName() {
        return this.stateName;
    }

    /**
     * Method description
     *
     *
     * @param state
     */
    public final void addNextState(FiniteState state) {
        if (null == state) {
            throw new IllegalArgumentException("state must be non-null reference");
        }

        this.nextStates.add(state);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final LinkedList<FiniteState> getNextStates() {
        return this.nextStates;
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     *
     * @return
     */
    public final FiniteState process(List<ScriptEvent> eventTuple) {
        ScenarioLogger.getInstance().machineLog(Level.FINEST, this.stateName + " state: processing event tuple");

        if (null == this.eventProcessor) {
            return this;
        }

        for (FsmActionListener listener : this.stateChangedListeners) {
            ScenarioLogger.getInstance().machineLog(Level.FINEST,
                    "listener \"" + listener.getClass().getName() + "\" called");
            listener.stateProcessed(this);
        }

        return this.eventProcessor.processEvent(eventTuple, this.nextStates, this);
    }

    /**
     * Method description
     *
     */
    public final void entered() {
        if (this.isEntered) {
            return;
        }

        this.isEntered = true;
        ScenarioLogger.getInstance().machineLog(Level.FINEST, this.stateName + " state entered");

        for (FsmActionListener listener : this.stateChangedListeners) {
            ScenarioLogger.getInstance().machineLog(Level.FINEST, listener.getClass().getName() + " listener");
            listener.stateEntered(this);
        }
    }

    /**
     * Method description
     *
     */
    public final void leaved() {
        if (this.isLeaved) {
            return;
        }

        this.isLeaved = true;
        ScenarioLogger.getInstance().machineLog(Level.FINEST, this.stateName + " state leaved");

        for (FsmActionListener listener : this.stateChangedListeners) {
            ScenarioLogger.getInstance().machineLog(Level.FINEST, listener.getClass().getName() + " listener");
            listener.stateLeaved(this);
        }
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void addStateChangedListener(FsmActionListener listener) {
        if (null == listener) {
            return;
        }

        this.stateChangedListeners.add(listener);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    public List<ScriptAction> getAllAvalibleActions() {
        LinkedList out = new LinkedList();

        for (FsmActionListener stateListener : this.stateChangedListeners) {
            out.addAll(stateListener.getAllAvalibleActions());
        }

        if (null != this.eventProcessor) {
            out.addAll(this.eventProcessor.getAllAvalibleActions(out));
        }

        if (!(out.isEmpty())) {
            return out;
        }

        return Collections.emptyList();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<List<ScriptEvent>> getExpectedEvents() {
        if (null != this.eventProcessor) {
            return this.eventProcessor.getAllAvalibleEvents();
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<Integer> getDataToSave() {
        if (null != this.eventProcessor) {
            return this.eventProcessor.getDataToSave();
        }

        return Collections.emptyList();
    }

    /**
     * Method description
     *
     *
     * @param savedData
     */
    public void restoreState(List<Integer> savedData) {
        this.eventProcessor.restoreFromData(savedData);
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
    public final int compareTo(Object o) {
        return this.stateName.compareTo((String) o);
    }
}


//~ Formatted in DD Std on 13/08/27
