/*
 * @(#)EventsControllerHelper.java   13/08/28
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

import rnr.src.rnrcore.eng;
import rnr.src.rnrloggers.ScenarioLogger;
import rnr.src.rnrscenario.consistency.ScenarioGarbageFinder;
import rnr.src.rnrscenario.consistency.ScenarioStage;
import rnr.src.rnrscenario.consistency.StageChangedListener;
import rnr.src.scenarioXml.XmlDocument;
import rnr.src.scriptEvents.EventsControllerHelper.MessageEventListener;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
public final class EventsControllerHelper implements EventListener, StageChangedListener {
    private static final String ROOT_NODE_NAME = "messages";
    private static final String MESSAGE_NODE_TAG_NAME = "msg";
    private static final String IMPLICIT_FILE_TO_LOAD = "..\\Data\\config\\messageEvents.xml";
    private static EventsControllerHelper ourInstance = null;
    private final Object latch = new Object();
    private boolean processingEvent = false;
    private ScenarioStage stageChangedEventHost = null;
    @SuppressWarnings("unchecked")
    private final ArrayList<Object> allListenersBuffer = new ArrayList<Object>();
    @SuppressWarnings("unchecked")
    private final HashSet<String> registeredMessagesForEvents = new HashSet<String>();
    private final HashMap<String, ArrayList<MessageEventListener>> listnersTable;

    @SuppressWarnings("unchecked")
    private EventsControllerHelper() {
        this.listnersTable = new HashMap<String, ArrayList<MessageEventListener>>();
        EventsController.getInstance().addListener(this);

        if (eng.noNative) {
            return;
        }

        loadMessageEventsStrings("..\\Data\\config\\messageEvents.xml");
    }

    /**
     * Method description
     *
     *
     * @param scenarioStage
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        if (this.processingEvent) {
            this.stageChangedEventHost = scenarioStage;
        } else {
            synchronized (this.latch) {
                for (ArrayList messageEventListeners : this.listnersTable.values()) {
                    for (MessageEventListener listener : messageEventListeners) {
                        this.allListenersBuffer.add(listener.listener);
                    }
                }

                List<Object> garbage = ScenarioGarbageFinder.getOutOfDateScenarioObjects(super.getClass().getName(),
                                           this.allListenersBuffer, scenarioStage);

                for (ArrayList messageEventListeners : this.listnersTable.values()) {
                    for (Iterator listenerIterator = messageEventListeners.iterator(); listenerIterator.hasNext(); ) {
                        MessageEventListener messageEventListener = (MessageEventListener) listenerIterator.next();

                        if (garbage.contains(messageEventListener.listener)) {
                            listenerIterator.remove();
                        }
                    }
                }

                Iterator listenerIterator;

                this.allListenersBuffer.clear();
            }
        }
    }

    private void registerMessageEvent(String messageText) {
        if (null == messageText) {
            ScenarioLogger.getInstance().machineLog(Level.WARNING,
                    "EventsController.registerMessageEvent - messageText is null: ignored");

            return;
        }

        if (!(this.registeredMessagesForEvents.add(messageText))) {
            ScenarioLogger.getInstance().machineLog(Level.WARNING,
                    "message with text " + messageText + " already exists");
        } else {
            ScenarioLogger.getInstance().machineLog(Level.INFO, "registered messageEvent; text == " + messageText);
        }
    }

    boolean isRegisteredMessageEvent(String messageText) {
        if (null == messageText) {
            ScenarioLogger.getInstance().machineLog(Level.WARNING,
                    "EventsController.isRegisteredMessageEvent - messageText is null: ignored");

            return false;
        }

        return this.registeredMessagesForEvents.contains(messageText);
    }

    private void loadMessageEventsStrings(String path) {
        synchronized (this.latch) {
            try {
                XmlDocument xml = new XmlDocument(path);
                Collection<String> extracted = xml.extractTagsTextContent("messages", "msg");

                for (String incomingMsg : extracted) {
                    if ((null != incomingMsg) && (0 < incomingMsg.length())) {
                        registerMessageEvent(incomingMsg);
                    }
                }
            } catch (IOException e) {
                ScenarioLogger.getInstance().machineLog(Level.WARNING,
                        "failed to load message events from " + path + ": " + e.getMessage());
            }
        }
    }

    /**
     * Method description
     *
     */
    public static void init() {
        ourInstance = new EventsControllerHelper();
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        ourInstance = null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static EventsControllerHelper getInstance() {
        return ourInstance;
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        this.processingEvent = true;

        try {
            for (ScriptEvent event : eventTuple) {
                if (event instanceof MessageEvent) {
                    synchronized (this.latch) {
                        ArrayList<MessageEventListener> listToCopy =
                            this.listnersTable.get(((MessageEvent) event).getMessage());

                        if (null != listToCopy) {
                            LinkedList listenersOfArgumentMessage = new LinkedList(listToCopy);

                            for (MessageEventListener listener : listenersOfArgumentMessage) {
                                try {
                                    listener.react();
                                } catch (IllegalAccessException exception) {
                                    ScenarioLogger.getInstance().machineWarning(exception.getMessage());
                                } catch (InvocationTargetException exception) {
                                    ScenarioLogger.getInstance().machineWarning(exception.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        } finally {
            this.processingEvent = false;
        }

        if (null == this.stageChangedEventHost) {
            return;
        }

        ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(super.getClass().getName(), this.listnersTable.entrySet(),
                this.stageChangedEventHost);
        this.stageChangedEventHost = null;
    }

    /**
     * Method description
     *
     *
     * @param what
     */
    public static void messageEventHappened(String what) {
        EventsController.getInstance().eventHappen(new ScriptEvent[] { new MessageEvent(what) });
    }

    /**
     * Method description
     *
     *
     * @param event
     */
    public static void eventHappened(ScriptEvent event) {
        EventsController.getInstance().eventHappen(new ScriptEvent[] { event });
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public static void endScenarioBranch(String name) {
        EventsController.getInstance().eventHappen(new ScriptEvent[] {
            new ScenarioBranchEndEvent(name + "(" + "_phase_" + "\\d+)?") });
    }

    /**
     * Method description
     *
     *
     * @param listener
     * @param methodToInvoke
     * @param messageToWait
     */
    public void addMessageListener(Object listener, String methodToInvoke, String messageToWait) {
        synchronized (this.latch) {
            if (!(isRegisteredMessageEvent(messageToWait))) {
                ScenarioLogger.getInstance().machineLog(Level.WARNING,
                        "trying to add listener on unregistered message == " + messageToWait);
            }

            ArrayList<MessageEventListener> listenersOfArgumentMessage = this.listnersTable.get(messageToWait);

            if (null == listenersOfArgumentMessage) {
                listenersOfArgumentMessage = new ArrayList<MessageEventListener>();
                this.listnersTable.put(messageToWait, listenersOfArgumentMessage);
            }

            try {
                listenersOfArgumentMessage.add(new MessageEventListener(listener, methodToInvoke));
                ScenarioLogger.getInstance().machineLog(Level.INFO,
                        "added text messages listener: " + listener.getClass().toString() + "; method to invoke: "
                        + methodToInvoke + "; message to wait: " + messageToWait);
            } catch (NoSuchMethodException exception) {
                ScenarioLogger.getInstance().machineWarning(exception.getMessage());
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param listener
     * @param methodToInvoke
     * @param messageToWait
     */
    public void removeMessageListener(Object listener, String methodToInvoke, String messageToWait) {
        @SuppressWarnings("rawtypes") Iterator eventListenerIterator;

        synchronized (this.latch) {
            ArrayList<MessageEventListener> listenersOfArgumentMessage = this.listnersTable.get(messageToWait);

            if (null != listenersOfArgumentMessage) {
                for (eventListenerIterator = listenersOfArgumentMessage.iterator(); eventListenerIterator.hasNext(); ) {
                    MessageEventListener eventListener = (MessageEventListener) eventListenerIterator.next();

                    if (eventListener.isSameEvent(listener, methodToInvoke)) {
                        eventListenerIterator.remove();

                        return;
                    }
                }
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param filePath
     */
    public void uploadMessageEventsToRegister(String filePath) {
        if (null == filePath) {
            throw new IllegalArgumentException("filePath must be non-null reference");
        }

        loadMessageEventsStrings(filePath);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Set<String> getAvalibleMessageEvents() {
        return Collections.unmodifiableSet(this.registeredMessagesForEvents);
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/27
     * @author         TJ
     */
    public static final class MessageEventListener {
        private Object listener = null;
        private Method methodToCall = null;

        MessageEventListener(Object listener, String methodToCallName) throws NoSuchMethodException {
            if ((null == listener) || (null == methodToCallName) || (0 >= methodToCallName.length())) {
                return;
            }

            this.listener = listener;
            this.methodToCall = listener.getClass().getDeclaredMethod(methodToCallName, new Class[0]);
            this.methodToCall.setAccessible(true);
        }

        boolean isSameEvent(Object listener, String methodToCallName) {
            return ((this.listener.equals(listener)) && (this.methodToCall.getName().compareTo(methodToCallName) == 0));
        }

        void react() throws IllegalAccessException, InvocationTargetException {
            this.methodToCall.invoke(this.listener, new Object[0]);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
