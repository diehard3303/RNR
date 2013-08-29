/*
 * @(#)CbVideoEvent.java   13/08/28
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
import rnr.src.scriptEvents.CbVideoEvent.EventType;

//~--- JDK imports ------------------------------------------------------------

import java.lang.reflect.Method;

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public class CbVideoEvent implements ScriptEvent {
    @SuppressWarnings("unchecked")
    private static HashMap<EventType, Method> methodResolveTable = new HashMap<EventType, Method>();

    static {
        try {
            methodResolveTable.put(EventType.START, CbVideoCallback.class.getDeclaredMethod("onStart", new Class[0]));
            methodResolveTable.put(EventType.FINISH, CbVideoCallback.class.getDeclaredMethod("onFinish", new Class[0]));
            methodResolveTable.put(EventType.ANSWER,
                                   CbVideoCallback.class.getDeclaredMethod("onAnswer", new Class[] { Integer.TYPE }));
        } catch (NoSuchMethodException exception) {
            ScenarioLogger.getInstance().machineWarning("failed to initialize CbVideoEvent callback lookup table");
            ScenarioLogger.getInstance().machineWarning(exception.getLocalizedMessage());
        }
    }

    private String cbVideoCallName = null;
    private Method cbVideoCallbackMethodToCall = null;
    private Object[] parametersToMethod = null;

    /**
     * Enum description
     *
     */
    public static enum EventType { START, FINISH, ANSWER; }

    /**
     * Constructs ...
     *
     *
     * @param cbVideoCallName
     * @param whatHappened
     * @param answerNomber
     */
    public CbVideoEvent(String cbVideoCallName, EventType whatHappened, int answerNomber) {
        this.cbVideoCallName = cbVideoCallName;
        this.cbVideoCallbackMethodToCall = (methodResolveTable.get(whatHappened));

        if (EventType.ANSWER == whatHappened) {
            this.parametersToMethod = new Object[1];
            this.parametersToMethod[0] = Integer.valueOf(answerNomber);
        } else {
            this.parametersToMethod = new Object[0];
        }
    }

    Object[] getParameters() {
        return this.parametersToMethod;
    }

    Method getMethodToCall() {
        return this.cbVideoCallbackMethodToCall;
    }

    String getCbVideoCallName() {
        return this.cbVideoCallName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String toString() {
        if (null != this.cbVideoCallName) {
            if (null != this.cbVideoCallbackMethodToCall) {
                return "CbVideo " + this.cbVideoCallName + ' ' + this.cbVideoCallbackMethodToCall.getName();
            }

            return "CbVideo " + this.cbVideoCallName;
        }

        return new String();
    }
}


//~ Formatted in DD Std on 13/08/28
