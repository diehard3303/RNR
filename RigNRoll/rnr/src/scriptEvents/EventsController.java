/*
 * @(#)EventsController.java   13/08/27
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

import rnr.src.rnrscenario.consistency.ScenarioGarbageFinder;
import rnr.src.rnrscenario.consistency.ScenarioStage;
import rnr.src.rnrscenario.consistency.StageChangedListener;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public final class EventsController implements StageChangedListener {
    private static EventsController thisInstance = new EventsController();
    private final List<EventListener> eventListeners = new ArrayList<EventListener>();
    private final List<EventListener> listenersToRemove = new ArrayList<EventListener>();
    private final List<EventListener> listenersToAdd = new ArrayList<EventListener>();
    private final Object synchronizationMonitor = new Object();
    private volatile boolean processingEvents = false;
    private int eventsChainDeep = 0;
    private ScenarioStage checkPointQuery = null;

    /**
     * Method description
     *
     */
    public static void deinit() {
        thisInstance = new EventsController();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static EventsController getInstance() {
        return thisInstance;
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void addListener(EventListener listener) {
        synchronized (this.synchronizationMonitor) {
            if (null == listener) {
                System.err.println("EventsController.addListener - listener is null: ignored");
            } else if (!(this.processingEvents)) {
                this.eventListeners.add(listener);
            } else {
                this.listenersToAdd.add(listener);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void removeListener(EventListener listener) {
        synchronized (this.synchronizationMonitor) {
            if (!(this.processingEvents)) {
                this.eventListeners.remove(listener);
            } else {
                this.listenersToRemove.add(listener);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     */
    public void eventHappen(ScriptEvent[] eventTuple) {
        synchronized (this.synchronizationMonitor) {
            this.processingEvents = true;
            this.eventsChainDeep += 1;

            if (0 < eventTuple.length) {
                List<ScriptEvent> tuple = Arrays.asList(eventTuple);

                for (EventListener listener : this.eventListeners) {
                    listener.eventHappened(tuple);
                }

                if (1 == this.eventsChainDeep) {
                    for (EventListener listener : this.listenersToAdd) {
                        this.eventListeners.add(listener);
                    }

                    this.listenersToAdd.clear();

                    for (EventListener listener : this.listenersToRemove) {
                        this.eventListeners.remove(listener);
                    }

                    this.listenersToRemove.clear();
                }
            }

            this.eventsChainDeep -= 1;

            if (0 == this.eventsChainDeep) {
                this.processingEvents = false;

                if (null != this.checkPointQuery) {
                    ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(super.getClass().getName(),
                            this.eventListeners, this.checkPointQuery);
                    this.checkPointQuery = null;
                }
            }

            if (!(this.processingEvents)) {
                AfterEventsRun.getInstance().run();
            }
        }
    }

    boolean isBussy() {
        return this.processingEvents;
    }

    /**
     * Method description
     *
     *
     * @param scenarioStage
     */
    @Override
    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        synchronized (this.synchronizationMonitor) {
            if (this.processingEvents) {
                this.checkPointQuery = scenarioStage;
            } else {
                ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(super.getClass().getName(), this.eventListeners,
                        scenarioStage);
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/27
