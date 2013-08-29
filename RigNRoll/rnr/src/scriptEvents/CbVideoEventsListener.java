/*
 * @(#)CbVideoEventsListener.java   13/08/27
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public class CbVideoEventsListener implements EventListener {
    private final HashMap<String, CbVideoCallback> eventsReactors;

    /**
     * Constructs ...
     *
     */
    public CbVideoEventsListener() {
        this.eventsReactors = new HashMap<String, CbVideoCallback>();
    }

    /**
     * Method description
     *
     *
     * @param callName
     * @param callBack
     */
    public void addEventReaction(String callName, CbVideoCallback callBack) {
        if ((null == callName) || (null == callBack)) {
            return;
        }

        this.eventsReactors.put(callName, callBack);
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     */
    @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        if (null != eventTuple) {
            for (ScriptEvent event : eventTuple) {
                if (event instanceof CbVideoEvent) {
                    CbVideoEvent cbVideoEvent = (CbVideoEvent) event;
                    CbVideoCallback callBack = this.eventsReactors.get(cbVideoEvent.getCbVideoCallName());

                    if (null != callBack) {
                        try {
                            Method call = cbVideoEvent.getMethodToCall();

                            if (null != call) {
                                call.setAccessible(true);
                                call.invoke(callBack, cbVideoEvent.getParameters());
                            } else {
                                ScenarioLogger.getInstance().machineWarning(
                                    "CbVideoEventsListener.eventHappened: can't find callback method for video call; CBV name == "
                                    + cbVideoEvent.getCbVideoCallName());
                            }
                        } catch (IllegalAccessException exception) {
                            ScenarioLogger.getInstance().machineLog(Level.SEVERE, exception.getMessage());
                        } catch (InvocationTargetException exception) {
                            ScenarioLogger.getInstance().machineLog(Level.SEVERE,
                                    exception.getTargetException().getMessage());
                        }
                    } else {
                        ScenarioLogger.getInstance().machineLog(
                            Level.INFO,
                            "CbVideoEventsListener.eventHappened: can't find callback for video call; CBV name ==  "
                            + cbVideoEvent.getCbVideoCallName());
                    }
                }
            }
        } else {
            ScenarioLogger.getInstance().machineLog(Level.SEVERE,
                    "CbVideoEventsListener.eventHappened: invalid argument(s)");
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<ScriptAction> getActionList() {
        List<ScriptAction> out = new LinkedList<ScriptAction>();

        for (CbVideoCallback callBack : this.eventsReactors.values()) {
            out.addAll(callBack.getActionList());
        }

        return out;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Set<String> getCallNamesList() {
        return Collections.unmodifiableSet(this.eventsReactors.keySet());
    }
}


//~ Formatted in DD Std on 13/08/27
