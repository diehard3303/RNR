/*
 * @(#)MissionsLog.java   13/08/28
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


package rnr.src.rnrscenario.missions.requirements;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrscenario.missions.Dumpable;

//~--- JDK imports ------------------------------------------------------------

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class MissionsLog implements Dumpable {
    private static MissionsLog ourInstance = new MissionsLog();
    private static final int DEFAULT_MAP_CAPACITY = 128;
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Map<String, MissionState> missionsEvents = new HashMap(128);

    /**
     * Enum description
     *
     */
    public static enum Event {
        PLAYER_INFORMED_ABOUT_MISSION, PLAYER_DECLINED_MISSION, MISSION_ACCEPTED, MISSION_DROPPED, MISSION_COMPLETE,
        FRIGHT_BROKEN, FREIGHT_DELIVERY_EXPIRED, FREIGHT_LOADING_EXPIRED;

        private final boolean playerReactedOnMission;
        private final boolean missionFailed;
        private final boolean missionFinished;

        /**
         * Method description
         *
         *
         * @return
         */
        public static final Event[] values() {
            return ((Event[]) $VALUES.clone());
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean wasMissionConsideredByPlayer() {
            return this.playerReactedOnMission;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean missionFailed() {
            return this.missionFailed;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean missionFinished() {
            return this.missionFinished;
        }
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        ourInstance = new MissionsLog();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static MissionsLog getInstance() {
        return ourInstance;
    }

    /**
     * Method description
     *
     *
     * @param missionName
     *
     * @return
     */
    public MissionState getMissionState(String missionName) {
        return (this.missionsEvents.get(missionName));
    }

    /**
     * Method description
     *
     *
     * @param missionName
     * @param what
     */
    public void eventHappen(String missionName, Event what) {
        MissionState state = getMissionState(missionName);

        if (null == state) {
            state = new MissionState();
            state.eventHappen(what);
            this.missionsEvents.put(missionName, state);
        } else {
            state.eventHappen(what);
        }
    }

    /**
     * Method description
     *
     *
     * @param target
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void makeDump(OutputStream target) {
        PrintWriter out = new PrintWriter(target);

        out.println("MISSION EVENTS:");

        for (Map.Entry state : this.missionsEvents.entrySet()) {
            out.println("\tMISSION " + ((String) state.getKey()) + ':');

            for (Event event : ((MissionState) state.getValue()).getOccuredEvents()) {
                out.print("\t\t");
                out.println(event.name());
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Map<String, MissionState> getMissionsEvents() {
        return this.missionsEvents;
    }

    /**
     * Method description
     *
     *
     * @param missionsEvents
     */
    public void setMissionsEvents(Map<String, MissionState> missionsEvents) {
        this.missionsEvents = missionsEvents;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public static class MissionState {
        private final List<MissionsLog.Event> events;
        private boolean missionFinished;

        /**
         * Constructs ...
         *
         */
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public MissionState() {
            this.events = new LinkedList();
            this.missionFinished = false;
        }

        /**
         * Method description
         *
         *
         * @param what
         */
        public void eventHappen(MissionsLog.Event what) {
            if (null != what) {
                this.events.add(what);
                this.missionFinished |= what.missionFinished();
            } else {
                MissionsLogger.getInstance().doLog("Invalid event came to mission stati in MissionsLog", Level.SEVERE);
            }
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public Collection<MissionsLog.Event> getOccuredEvents() {
            return Collections.unmodifiableList(this.events);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean missionFinished() {
            return this.missionFinished;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
