/*
 * @(#)NativeEventController.java   13/08/26
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


package rnr.src.rnrcore;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscenario.consistency.ScenarioGarbageFinder;
import rnr.src.rnrscenario.consistency.ScenarioStage;
import rnr.src.rnrscenario.consistency.StageChangedListener;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class NativeEventController implements StageChangedListener {
    private static NativeEventController instance = null;
    private final Map<String, List<INativeMessageEvent>> registeredEvents = new HashMap();
    private final List<INativeMessageEvent> m_invalideListeners = new ArrayList();
    private final List<INativeMessageEvent> m_newListeners = new ArrayList();
    private ScenarioStage scenarioStageChange = null;
    private boolean processingEvent = false;

    private NativeEventController() {
        instance = this;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static NativeEventController getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     */
    public static void init() {
        instance = new NativeEventController();
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        instance = null;
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public static void addNativeEventListener(INativeMessageEvent listener) {
        if ((null == listener) || (listener.getMessage() == null) || (0 == listener.getMessage().length())) {
            return;
        }

        if (!(instance.processingEvent)) {
            Log.simpleMessage("NativeEventController addNativeEventListener " + listener.getMessage());

            if (instance.registeredEvents.containsKey(listener.getMessage())) {
                ((List) instance.registeredEvents.get(listener.getMessage())).add(listener);
            } else {
                ArrayList list = new ArrayList();

                list.add(listener);
                instance.registeredEvents.put(listener.getMessage(), list);
            }
        } else {
            Log.simpleMessage("NativeEventController delayed addNativeEventListener " + listener.getMessage());
            instance.m_newListeners.add(listener);
        }
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public static void removeNativeListener(INativeMessageEvent listener) {
        if ((null == listener) || (null == listener.getMessage()) || (0 == listener.getMessage().length())) {
            return;
        }

        if (!(instance.processingEvent)) {
            Log.simpleMessage("NativeEventController removeNativeListener " + listener.getMessage());

            if (!(instance.registeredEvents.containsKey(listener.getMessage()))) {
                return;
            }

            ((List) instance.registeredEvents.get(listener.getMessage())).remove(listener);
        } else {
            Log.simpleMessage("NativeEventController delayed removeNativeListener " + listener.getMessage());
            instance.m_invalideListeners.add(listener);
        }
    }

    /**
     * Method description
     *
     *
     * @param message
     */
    public static void messageEventHappend(String message) {
        Log.simpleMessage("NativeEventController messageEventHappend " + message);
        instance.processingEvent = true;

        if (instance.registeredEvents.containsKey(message)) {
            List listeners = instance.registeredEvents.get(message);
            Iterator iter = listeners.iterator();

            while (iter.hasNext()) {
                INativeMessageEvent listener = (INativeMessageEvent) iter.next();

                if (instance.m_invalideListeners.contains(listener)) {
                    continue;
                }

                listener.onEvent(message);

                if (listener.removeOnEvent()) {
                    iter.remove();
                }
            }
        }

        instance.processingEvent = false;

        for (INativeMessageEvent listener : instance.m_newListeners) {
            addNativeEventListener(listener);
        }

        instance.m_newListeners.clear();

        for (INativeMessageEvent listener : instance.m_invalideListeners) {
            removeNativeListener(listener);
        }

        instance.m_invalideListeners.clear();

        if (null == instance.scenarioStageChange) {
            return;
        }

        ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(NativeEventController.class.getName(),
                instance.registeredEvents.values(), instance.scenarioStageChange);
        instance.scenarioStageChange = null;
    }

    /**
     * Method description
     *
     *
     * @param scenarioStage
     */
    @Override
    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        if (this.processingEvent) {
            this.scenarioStageChange = scenarioStage;
        } else {
            ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(super.getClass().getName(),
                    this.registeredEvents.values(), scenarioStage);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
