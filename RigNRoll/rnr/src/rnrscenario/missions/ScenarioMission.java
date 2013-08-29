/*
 * @(#)ScenarioMission.java   13/08/28
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

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrscenario.missions.infochannels.InformationChannelData;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class ScenarioMission {
    static InformationChannelData channel;

    private static String getChannelName(String mission_name) {
        return mission_name;
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param organizerName
     * @param finishPoint
     * @param timeForMissionEnd
     * @param move_time
     * @param needFinishIcon
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void activateMission(String mission_name, String organizerName, String finishPoint,
                                       CoreTime timeForMissionEnd, boolean move_time, boolean needFinishIcon) {
        MissionInfo mission = createMission(mission_name, organizerName, finishPoint, timeForMissionEnd, move_time,
                                  needFinishIcon);
        MissionPlacement mission_placement = new MissionPlacement(mission, new ArrayList());

        MissionSystemInitializer.getMissionsManager().placeMissionToWorld(mission_placement);
        MissionSystemInitializer.getMissionsManager().activateDependantMission(mission_name, mission_name);
        EventsController.getInstance().eventHappen(new ScriptEvent[] { new CreateMissionEvent(mission_name) });

        IStartMissionListener lst = StartMissionListeners.getInstance().getStartMissionListener(mission_name);
        String missionStartPlaceName = null;

        if (null != lst) {
            missionStartPlaceName = lst.missionStarted();
        }

        mission.setMissionStartPlaceName(missionStartPlaceName);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param organizerName
     * @param finishPoint
     * @param timeForMissionEnd
     * @param move_time
     * @param needFinishIcon
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void activateMissionLoad(String mission_name, String organizerName, String finishPoint,
            CoreTime timeForMissionEnd, boolean move_time, boolean needFinishIcon) {
        MissionInfo mission = createMission(mission_name, organizerName, finishPoint, timeForMissionEnd, move_time,
                                  needFinishIcon);
        MissionPlacement mission_placement = new MissionPlacement(mission, new ArrayList());

        MissionSystemInitializer.getMissionsManager().placeMissionToWorld(mission_placement);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static MissionInfo createMission(String mission_name, String organizerName, String finishPoint,
            CoreTime timeForMissionEnd, boolean move_time, boolean needFinishIcon) {
        makefakechannel(getChannelName(mission_name));

        QIVisit qi = new QIVisit();

        qi.model = "VISIT";

        List start_channels = new ArrayList();
        List start_actions = new ArrayList();
        List any_end_actions = new ArrayList();
        MissionInfo result = new MissionInfo(mission_name, finishPoint, organizerName, qi, start_channels,
                                 start_actions, any_end_actions, any_end_actions, any_end_actions, any_end_actions,
                                 any_end_actions);

        result.setScenarioMission(true);
        result.setStarter(new Params(timeForMissionEnd, move_time, needFinishIcon));

        return result;
    }

    @SuppressWarnings("rawtypes")
    private static void makefakechannel(String resource_ref) {
        List appear_actions = new ArrayList();
        List accept_actions = new ArrayList();
        List decline_actions = new ArrayList();
        MissionCreationContext context = new MissionCreationContext(resource_ref);

        channel = new InformationChannelData("CbvChannel", resource_ref, null, appear_actions, accept_actions,
                decline_actions, context);
    }

    static class Params implements IMissionStarter {
        CoreTime endTime;
        boolean move_time;
        boolean needFinishIcon;

        Params(CoreTime endTime, boolean move_time, boolean needFinishIcon) {
            this.endTime = endTime;
            this.move_time = move_time;
            this.needFinishIcon = needFinishIcon;
        }

        /**
         * Method description
         *
         *
         * @param mission_name
         * @param isChannelImmediate
         * @param info_channel_point
         * @param qi_point
         * @param finish_point
         *
         * @return
         */
        @Override
        public String startMission(String mission_name, boolean isChannelImmediate, String info_channel_point,
                                   String qi_point, String finish_point) {
            Place place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(1);

            if (place.getName().compareTo(finish_point) == 0) {
                place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(1, place);
            }

            MissionEventsMaker.startMission_ScenarioMission(mission_name, isChannelImmediate, place.getName(),
                    qi_point, finish_point, this.endTime, this.move_time, this.needFinishIcon);

            return place.getName();
        }

        /**
         * Method description
         *
         *
         * @param mission_name
         * @param isChannelImmediate
         * @param info_channel_point
         * @param qi_point
         * @param finish_point
         * @param nearestPlaceName
         */
        @Override
        public void startMission(String mission_name, boolean isChannelImmediate, String info_channel_point,
                                 String qi_point, String finish_point, String nearestPlaceName) {
            Place place = MissionSystemInitializer.getMissionsMap().getPlace(nearestPlaceName);

            assert(place != null);
            MissionEventsMaker.startMission_ScenarioMission(mission_name, isChannelImmediate, place.getName(),
                    qi_point, finish_point, this.endTime, this.move_time, this.needFinishIcon);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
