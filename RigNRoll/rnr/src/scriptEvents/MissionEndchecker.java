/*
 * @(#)MissionEndchecker.java   13/08/28
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

import rnr.src.rnrcore.Helper;
import rnr.src.rnrcore.INativeMessageEvent;
import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscenario.missions.SuccesFailMissionEvent;
import rnr.src.rnrscenario.missions.requirements.MissionsLog;
import rnr.src.rnrscenario.missions.requirements.MissionsLog.Event;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public class MissionEndchecker implements EventChecker {
    static final long serialVersionUID = 0L;

    /** Field description */
    public static final String FAIL_TIMEOUT_PICKUP = "fail timeout pickup";

    /** Field description */
    public static final String FAIL_TIMEOUT_COMPLETE = "fail timeout complete";

    /** Field description */
    public static final String FAIL_DAMAGED = "fail damaged";

    /** Field description */
    public static final String FAIL_DECLINE = "decline";

    /** Field description */
    public static final String SUCCESS = "success";
    private static final String[] TYPE_END = { "fail timeout pickup", "fail timeout complete", "fail damaged",
            "decline", "success" };

    /** Field description */
    public static final String[] SUFFIX = { " mission failed timeout pickup", " mission failed timeout complete",
            " mission failed timeout damaged", " mission failed declined", " mission succeeded" };
    private static final Map<String, MissionsLog.Event> EVENTS_MAPPING = new HashMap<String, Event>();
    private static HashMap<String, storeINativeMessageEvent> registeredEventListeners;

    static {
        EVENTS_MAPPING.put("fail timeout pickup", MissionsLog.Event.FREIGHT_LOADING_EXPIRED);
        EVENTS_MAPPING.put("fail timeout complete", MissionsLog.Event.FREIGHT_DELIVERY_EXPIRED);
        EVENTS_MAPPING.put("fail damaged", MissionsLog.Event.FRIGHT_BROKEN);
        EVENTS_MAPPING.put("decline", MissionsLog.Event.MISSION_DROPPED);
        EVENTS_MAPPING.put("success", MissionsLog.Event.MISSION_COMPLETE);
        registeredEventListeners = new HashMap<String, storeINativeMessageEvent>();
    }

    private boolean constructed = false;
    private String type = "";
    private ScriptEvent event_to_emmit = null;
    private String mission_name = null;
    private INativeMessageEvent eventListener = null;

    /**
     * Constructs ...
     *
     */
    public MissionEndchecker() {
        this.mission_name = MissionInfo.getLoadingMissionName();
        EventCheckersBuilders.add_to_construct(this);
    }

    /**
     * Constructs ...
     *
     *
     * @param type
     */
    public MissionEndchecker(String type) {
        this.type = type;
        this.mission_name = MissionInfo.getLoadingMissionName();
        EventCheckersBuilders.add_to_construct(this);
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        if (null != registeredEventListeners) {
            registeredEventListeners.clear();
        }

        registeredEventListeners = new HashMap<String, storeINativeMessageEvent>();
    }

    private static void peekOnMissionName(INativeMessageEvent listener_not_to_exclude, String mission_name) {
        if (!(registeredEventListeners.containsKey(mission_name))) {
            MissionsLogger.getInstance().doLog("peekOnMissionName has no registered listeners for mission name "
                                               + mission_name, Level.SEVERE);
        }

        storeINativeMessageEvent storage = registeredEventListeners.get(mission_name);

        storage.remove_all_not_thatone(listener_not_to_exclude);
    }

    private static void registerListener(INativeMessageEvent listener, String mission_name) {
        storeINativeMessageEvent storage;

        if (!(registeredEventListeners.containsKey(mission_name))) {
            storage = new storeINativeMessageEvent();
            registeredEventListeners.put(mission_name, storage);
        } else {
            storage = registeredEventListeners.get(mission_name);
        }

        storage.add(listener);
    }

    /**
     * Method description
     *
     */
    public void construct() {
        if (this.constructed) {
            return;
        }

        this.constructed = true;
        this.event_to_emmit = new stubEvent();

        for (int i = 0; i < TYPE_END.length; ++i) {
            String str = TYPE_END[i];

            if (this.type.compareToIgnoreCase(str) == 0) {
                this.eventListener = new SuccesFailMissionEvent(this.mission_name, SUFFIX[i], this.event_to_emmit);
                Helper.addNativeEventListener(this.eventListener);
                registerListener(this.eventListener, this.mission_name);
            }
        }
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
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event : eventTuple) {
            if ((null != this.event_to_emmit) && (this.event_to_emmit.equals(event))) {
                peekOnMissionName(this.eventListener, this.mission_name);
                MissionsLog.getInstance().eventHappen(this.mission_name, EVENTS_MAPPING.get(this.type));

                return true;
            }
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
    public ScriptEvent lastPossetiveChecked() {
        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public List<ScriptEvent> getExpectantEvent() {
        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String isValid() {
        return null;
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void deactivateChecker() {
        Set<Entry<String, storeINativeMessageEvent>> entries = registeredEventListeners.entrySet();

        for (Map.Entry item : entries) {
            ((storeINativeMessageEvent) item.getValue()).remove_all_not_thatone(null);
        }
    }

    static class storeINativeMessageEvent {
        private final ArrayList<INativeMessageEvent> listeners;

        storeINativeMessageEvent() {
            this.listeners = new ArrayList<INativeMessageEvent>();
        }

        private void add(INativeMessageEvent listener) {
            this.listeners.add(listener);
        }

        private void remove_all_not_thatone(INativeMessageEvent listener) {
            for (INativeMessageEvent lst : this.listeners) {
                if (!(listener.equals(lst))) {
                    Helper.removeNativeEventListener(lst);
                }
            }
        }
    }


    static class stubEvent implements ScriptEvent {}
}


//~ Formatted in DD Std on 13/08/28
