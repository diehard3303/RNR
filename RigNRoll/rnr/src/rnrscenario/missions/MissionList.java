/*
 * @(#)MissionList.java   13/08/28
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


package rnr.src.rnrscenario.missions;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.event;
import rnr.src.scriptEvents.EventListener;
import rnr.src.scriptEvents.ScriptEvent;
import rnr.src.xmlserialization.Log;

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
public class MissionList implements EventListener {
    @SuppressWarnings("unused")
    private static MissionList list;
    @SuppressWarnings("unchecked")
    private final List<SingleMission> activeMissions = new LinkedList();
    private MissionFactory factory = null;

    MissionList(MissionFactory factory) {
        assert(null != factory) : "factory must be valid non-null reference";
        this.factory = factory;
        list = this;
    }

    /**
     * Method description
     *
     */
    public static void dialogsFinished() {
        event.SetScriptevent(9850L);
    }

    private void createSingelMissionAddToList(String name) {
        SingleMission mission = this.factory.create(name);

        if (mission != null) {
            this.activeMissions.add(mission);
        }
    }

    private void startMissions(List<ScriptEvent> eventTuple) {
        assert(null != eventTuple) : "eventTuple must be valid non-null reference; check your EventsController's code";

        for (ScriptEvent event : eventTuple) {
            if (event instanceof CreateMissionEvent) {
                createSingelMissionAddToList(((CreateMissionEvent) event).getMissionName());
            }
        }
    }

    private void transferEventsToActiveMissions(List<ScriptEvent> eventTuple) {
        if (null == eventTuple) {
            return;
        }

        for (ListIterator<SingleMission> missionIterator = this.activeMissions.listIterator();
                missionIterator.hasNext(); ) {
            SingleMission mission = missionIterator.next();

            if (mission == null) {
                continue;
            }

            if (mission.checkEnd(eventTuple)) {
                missionIterator.remove();
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     */
    @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        assert(null != eventTuple) : "eventTuple must be valid non-null reference; check your EventsController's code";
        transferEventsToActiveMissions(eventTuple);
        startMissions(eventTuple);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<String> getStartedMissions() {
        List missionNames = new ArrayList();

        if (this.activeMissions == null) {
            Log.error("MissionList.serializeXML activeMissions=null");
        }

        for (SingleMission mission : this.activeMissions) {
            if (mission == null) {
                Log.error("MissionList.serializeXML missions=null");
            }

            missionNames.add(mission.getMission_name());
        }

        return missionNames;
    }

    /**
     * Method description
     *
     *
     * @param names
     */
    public void restoreStartedMissions(List<String> names) {
        for (String missionName : names) {
            createSingelMissionAddToList(missionName);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
