/*
 * @(#)UniversalStateProcessor.java   13/08/28
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
import rnr.src.scenarioMachine.EventStateProcessor;
import rnr.src.scenarioMachine.FiniteState;
import rnr.src.scriptActions.ScriptAction;

//~--- JDK imports ------------------------------------------------------------

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
public abstract class UniversalStateProcessor implements EventStateProcessor {
    final LinkedList<EventReaction> reactionsOnEvents = new LinkedList<EventReaction>();

    /**
     * Method description
     *
     *
     * @param reaction
     */
    public final void addReaction(EventReaction reaction) {
        if (null == reaction) {
            return;
        }

        this.reactionsOnEvents.add(reaction);
    }

    void logEvent(List<ScriptEvent> event) {
        for (ScriptEvent e : event) {
            ScenarioLogger.getInstance().machineLog(Level.INFO, "tuple element " + e.toString());
        }
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
    public List<ScriptAction> getAllAvalibleActions(List<?> actionsToCheckForDuplicates) {
        LinkedList<ScriptAction> out = new LinkedList<ScriptAction>();

        for (EventReaction reaction : this.reactionsOnEvents) {
            List<ScriptAction> reactions = reaction.getAllAvalibleReactions();

            for (ScriptAction act : reactions) {
                if (null != act) {
                    out.add(act);
                }
            }
        }

        return out;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public List<List<ScriptEvent>> getAllAvalibleEvents() {
        LinkedList<List<ScriptEvent>> out = new LinkedList<List<ScriptEvent>>();

        for (EventReaction reaction : this.reactionsOnEvents) {
            List<ScriptEvent> eventTuple = reaction.getAllAvalibleEvents();

            if ((null != eventTuple) && (!(eventTuple.isEmpty()))) {
                out.add(eventTuple);
            }
        }

        return out;
    }

    /**
     * Method description
     *
     *
     * @param paramList
     * @param paramLinkedList
     * @param paramFiniteState
     *
     * @return
     */
    @Override
    public abstract FiniteState processEvent(List<ScriptEvent> paramList, LinkedList<FiniteState> paramLinkedList,
            FiniteState paramFiniteState);
}


//~ Formatted in DD Std on 13/08/28
