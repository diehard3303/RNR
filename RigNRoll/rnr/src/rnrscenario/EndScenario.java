/*
 * @(#)EndScenario.java   13/08/28
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


package rnr.src.rnrscenario;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.CoreTime;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class EndScenario extends sctask {
    private static EndScenario instance = null;
    private final Object actionsLatch = new Object();
    private List<DelayedAction> delayedActions = new LinkedList();

    private EndScenario(int tip) {
        super(tip, false);
        start();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static EndScenario getInstance() {
        if (null == instance) {
            instance = new EndScenario(3);
        }

        return instance;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        if (null == instance) {
            return;
        }

        instance.finishImmediately();
        instance = null;
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        ListIterator iter;

        synchronized (this.actionsLatch) {
            for (iter = this.delayedActions.listIterator(); iter.hasNext(); ) {
                DelayedAction counter = (DelayedAction) iter.next();

                if (counter.to_run()) {
                    counter.execute();
                    iter.remove();
                }
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param daysCount
     * @param hoursCount
     * @param event_on_activate
     * @param event_on_remove
     */
    public void delayAction(String name, int daysCount, int hoursCount, ScriptEvent event_on_activate,
                            ScriptEvent event_on_remove) {
        synchronized (this.actionsLatch) {
            this.delayedActions.add(new DelayedAction(name, daysCount, hoursCount, event_on_activate, event_on_remove));
        }
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void removeTimeQuest(String name) {
        ListIterator iter;

        synchronized (this.actionsLatch) {
            for (iter = this.delayedActions.listIterator(); iter.hasNext(); ) {
                DelayedAction counter = (DelayedAction) iter.next();

                if (0 == counter.getName().compareToIgnoreCase(name)) {
                    counter.on_remove();
                    iter.remove();
                }
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<DelayedAction> getDelayedActions() {
        synchronized (this.actionsLatch) {
            return new ArrayList(this.delayedActions);
        }
    }

    /**
     * Method description
     *
     *
     * @param delayedActions
     */
    public void setDelayedActions(List<DelayedAction> delayedActions) {
        synchronized (this.actionsLatch) {
            this.delayedActions = delayedActions;
        }
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public static final class DelayedAction {
        private String name = "no name";
        private CoreTime timeStart = new CoreTime();
        private CoreTime deltatime = null;
        private ScriptEvent event_on_activate = null;
        private ScriptEvent event_on_remove = null;

        /**
         * Constructs ...
         *
         *
         * @param name
         * @param days
         * @param hours
         * @param event_on_activate
         * @param event_on_remove
         */
        public DelayedAction(String name, int days, int hours, ScriptEvent event_on_activate,
                             ScriptEvent event_on_remove) {
            this.event_on_activate = event_on_activate;
            this.event_on_remove = event_on_remove;
            this.name = name;
            this.deltatime = CoreTime.daysNhours(days, hours);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean to_run() {
            return (new CoreTime().moreThanOnTime(this.timeStart, this.deltatime) >= 0);
        }

        /**
         * Method description
         *
         */
        public void execute() {
            if (null == this.event_on_activate) {
                return;
            }

            EventsController.getInstance().eventHappen(new ScriptEvent[] { this.event_on_activate });
        }

        /**
         * Method description
         *
         */
        public void on_remove() {
            if (null == this.event_on_remove) {
                return;
            }

            EventsController.getInstance().eventHappen(new ScriptEvent[] { this.event_on_remove });
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public CoreTime getTimeDelta() {
            return this.deltatime;
        }

        /**
         * Method description
         *
         *
         * @param deltatime
         */
        public void setDeltatime(CoreTime deltatime) {
            this.deltatime = deltatime;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public ScriptEvent getEvent_on_activate() {
            return this.event_on_activate;
        }

        /**
         * Method description
         *
         *
         * @param event_on_activate
         */
        public void setEvent_on_activate(ScriptEvent event_on_activate) {
            this.event_on_activate = event_on_activate;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public ScriptEvent getEvent_on_remove() {
            return this.event_on_remove;
        }

        /**
         * Method description
         *
         *
         * @param event_on_remove
         */
        public void setEvent_on_remove(ScriptEvent event_on_remove) {
            this.event_on_remove = event_on_remove;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public String getName() {
            return this.name;
        }

        /**
         * Method description
         *
         *
         * @param name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public CoreTime getTimeStart() {
            return this.timeStart;
        }

        /**
         * Method description
         *
         *
         * @param timeStart
         */
        public void setTimeStart(CoreTime timeStart) {
            this.timeStart = timeStart;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
