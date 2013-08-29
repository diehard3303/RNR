/*
 * @(#)SimpleEventReaction.java   13/08/27
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
public final class SimpleEventReaction extends EventReaction {
    static final long serialVersionUID = 0L;
    private ScriptAction scriptAction = null;
    private EventChecker eventChecker = null;

    /**
     * Constructs ...
     *
     *
     * @param scriptAction
     * @param eventChecker
     * @param uid
     */
    public SimpleEventReaction(ScriptAction scriptAction, EventChecker eventChecker, int uid) {
        super(uid);

        if (null == eventChecker) {
            throw new IllegalArgumentException("eventChecker must be non-null reference");
        }

        this.scriptAction = scriptAction;
        this.eventChecker = eventChecker;
    }

    @Override
    void deactivateReactor() {
        this.eventChecker.deactivateChecker();
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     *
     * @return
     */
    @Override
    public boolean react(List<ScriptEvent> eventTuple) {
        if ((null != eventTuple) && (!(eventTuple.isEmpty())) && (this.eventChecker.checkEvent(eventTuple))) {
            if (null != this.scriptAction) {
                this.scriptAction.act();
            }

            return true;
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public ScriptEvent getLastReacted() {
        return this.eventChecker.lastPossetiveChecked();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public List<ScriptAction> getAllAvalibleReactions() {
        if (null != this.scriptAction) {
            LinkedList out = new LinkedList();

            out.add(this.scriptAction);

            if (this.scriptAction.hasChildAction()) {
                out.add(this.scriptAction.getChildAction());
            }

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
    @Override
    public List<ScriptEvent> getAllAvalibleEvents() {
        if (null != this.eventChecker) {
            return this.eventChecker.getExpectantEvent();
        }

        return Collections.emptyList();
    }
}


//~ Formatted in DD Std on 13/08/27
