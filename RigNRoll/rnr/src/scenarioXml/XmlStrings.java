/*
 * @(#)XmlStrings.java   13/08/27
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


package rnr.src.scenarioXml;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public final class XmlStrings {
    private static final int INITIAL_CAPACITY = 24;
    private static final HashMap<String, String> nameResolveTable = new HashMap<String, String>(24);

    /** Field description */
    public static final String DEFAULT_NAME = "unkonwn";

    /** Field description */
    public static final String THIS_NAME = "this";

    static {
        nameResolveTable.put("startscenariomission", "StartScenarioMissionAction");
        nameResolveTable.put("failorg", "FailOrgAction");
        nameResolveTable.put("startorg", "StartOrgAction");
        nameResolveTable.put("finishorg", "FinishOrgAction");
        nameResolveTable.put("startquest", "StartScenarioBranchAction");
        nameResolveTable.put("finishquest", "StopScenarioBranchAction");
        nameResolveTable.put("jou", "JournalAction");
        nameResolveTable.put("journal", "JournalAction");
        nameResolveTable.put("journal_active", "JournalActiveAction");
        nameResolveTable.put("phasequest", "SingleStepScenarioAdvanceAction");
        nameResolveTable.put("MP", "MissionPointMarkAction");
        nameResolveTable.put("wait", "TimeAction");
        nameResolveTable.put("so_animation", "SetSODefaultScriptAction");
        nameResolveTable.put("sc_countdown", "ScenarioCountdown");
        nameResolveTable.put("stop_sc_countdown", "StopScenarioCountdown");
        nameResolveTable.put("race", "CreateRace");
        nameResolveTable.put("QIsemitrailer", "CreateQISemitailer");
        nameResolveTable.put("deletequestitem", "DeleteQuestItem");
        nameResolveTable.put("removequestitem", "RemoveQuestItem");
        nameResolveTable.put("startmission", "StartMissionAction");
        nameResolveTable.put("declinemission", "DeclineScenerioMission");
        nameResolveTable.put("finishmission", "FinishScenarioMission");
        nameResolveTable.put("changemd", "ChangeMissionDestination");
        nameResolveTable.put("set_scenario_flag", "SetScenarioFlag");
        nameResolveTable.put("make_payoff", "MakePayOff");
        nameResolveTable.put("postinfo", "PostMissionInfoAction");
        nameResolveTable.put("deactivatemission", "DeactivateMission");
        nameResolveTable.put("scenario_stage", "SetTrapScenarioStageAction");
        nameResolveTable.put("scenario_finished", "ScenarioFinishedAction");
        nameResolveTable.put("police_immunity", "PoliceImmunity");
        nameResolveTable.put("kill_taken_missions", "KillTakenMissionsAction");
        nameResolveTable.put("traffic", "TrafficAction");
        nameResolveTable.put("so", "SpecialObjectEventChecker");
        nameResolveTable.put("questitem", "QuestItemEventChecker");
        nameResolveTable.put("daytime", "TimeEventChecker");
        nameResolveTable.put("blockso", "BlockSpecialObject");
        nameResolveTable.put("createquest", "StartScenarioBranchAction");
        nameResolveTable.put("unblockso", "UnblockSpecialObject");
        nameResolveTable.put("makecall", "MakeCallAction");
        nameResolveTable.put("removeaction", "RemoveTimeAction");
        nameResolveTable.put("scene", "SetSceneToRunAction");
        nameResolveTable.put("message", "MessageEventChecker");
        nameResolveTable.put("nativemessage", "NativeMessageListener");
        nameResolveTable.put("missionend", "MissionEndchecker");
        nameResolveTable.put("event", "PostMessageEvent");
        nameResolveTable.put("loosegame", "GameLooseAction");
        nameResolveTable.put("policescene", "ReservePoliceSceneScriptAction");
        nameResolveTable.put("reachtimer", "ReachPointTimer");
        nameResolveTable.put("removeresources", "MissionActionRemoveResources");
        nameResolveTable.put("faderemoveresource", "MissionActionRemoveResourcesFade");
        nameResolveTable.put("start_aside_mission", "CreateAsideMission");
        nameResolveTable.put("extermal_channel_appear", "ExternalChannelSayAppear");
        nameResolveTable.put("postinfo", "PostMissionInfoAction");
        nameResolveTable.put("fromhour", "start");
        nameResolveTable.put("tohour", "end");
        nameResolveTable.put("soname", "name");
        nameResolveTable.put("startscenariomission", "StartScenarioMissionAction");
    }

    /**
     * Method description
     *
     *
     * @param nameToResolve
     *
     * @return
     */
    public static String resolveName(String nameToResolve) {
        if (null == nameToResolve) {
            rnr.src.rnrloggers.ScenarioLogger.getInstance().parserLog(Level.SEVERE, "nameToResolve is null");

            return null;
        }

        String resolved = nameResolveTable.get(nameToResolve);

        if (null == resolved) {
            rnr.src.rnrloggers.ScenarioLogger.getInstance().parserLog(Level.SEVERE,
                    "can't resolve name == \"" + nameToResolve + '"');
        }

        return resolved;
    }

    /**
     *     @return the initialCapacity
     */
    public static int getInitialCapacity() {
        return INITIAL_CAPACITY;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/27
     * @author         TJ
     */
    public static final class AttributesStrings {

        /** Field description */
        public static final String DESCIPTION_ATTR_STR = "description";

        /** Field description */
        public static final String NAME_ATTR_STR = "name";

        /** Field description */
        public static final String ORG_ATTR_STR = "org";

        /** Field description */
        public static final String FINISH_ON_LAST_PHASE_ATTR_STR = "finishonlastphase";

        /** Field description */
        public static final String PHASE_ATTR_STR = "phase";

        /** Field description */
        public static final String FINISH_ATTR_STR = "finish";

        /** Field description */
        public static final String SO_ATTR_STR = "so";

        /** Field description */
        public static final String QUESTITEM_ATTR_STR = "questitem";

        /** Field description */
        public static final String SO_NAME_ATTR_STR = "soname";

        /** Field description */
        public static final String FROM_HOUR_ATTR_STR = "fromhour";

        /** Field description */
        public static final String TO_HOUR_ATTR_STR = "tohour";

        /** Field description */
        public static final String CREATE_QUEST_ATTR_STR = "createquest";

        /** Field description */
        public static final String GAIN_ATTR_STR = "gain";

        /** Field description */
        public static final String STATUS_ATTR_STR = "status";

        /** Field description */
        public static final String NOM_ATTR_STR = "nom";

        /** Field description */
        public static final String DAYS_ATTR_STR = "days";

        /** Field description */
        public static final String HOURS_ATTR_STR = "hours";

        /** Field description */
        public static final String DAY_TIME_ATTR_STR = "daytime";

        /** Field description */
        public static final String BLOCK_SO_ATTR_STR = "blockso";

        /** Field description */
        public static final String UNBLOCK_SO_ATTR_STR = "unblockso";

        /** Field description */
        public static final String MAKE_CALL_ATTR_STR = "makecall";

        /** Field description */
        public static final String REMOVE_TIME_ACTION_ATTR_STR = "removeaction";

        /** Field description */
        public static final String SCENE_ATTR_STR = "scene";

        /** Field description */
        public static final String MESSAGE_ATTR_STR = "message";

        /** Field description */
        public static final String NATIVE_MESSAGE_ATTR_STR = "nativemessage";

        /** Field description */
        public static final String MISSIONEND_ATTR_STR = "missionend";

        /** Field description */
        public static final String EVENT_ATTR_STR = "event";

        /** Field description */
        public static final String LOOSE_GAME_ATTR_STR = "loosegame";

        /** Field description */
        public static final String POLICE_SCENE_RESERVE_ATTR_STR = "policescene";

        /** Field description */
        public static final String REACHPOINT_TIMER_ATTR_STR = "reachtimer";

        /** Field description */
        public static final String REMOVEMISSIONRESOURSES_ATTR_STR = "removeresources";

        /** Field description */
        public static final String REMOVEMISSIONRESOURSESFADE_ATTR_STR = "faderemoveresource";

        /** Field description */
        public static final String POST_MISSION_INFO_ATTR_STR = "postinfo";

        /** Field description */
        public static final String START_ASIDE_MISSION_ATTR_STR = "start_aside_mission";

        /** Field description */
        public static final String EXTERNAL_CHANNEL_APPEARED_ATTR_STR = "extermal_channel_appear";

        /** Field description */
        public static final String STAR_SCENARIO_MISSION_ATTR_STR = "startscenariomission";
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/27
     * @author         TJ
     */
    public static final class TagStrings {

        /** Field description */
        public static final String CB_CALL_NODE = "cbcall";

        /** Field description */
        public static final String ELEMENT_NODE = "element";

        /** Field description */
        public static final String ISO_QUESTS_NODE_STR = "isoquests";

        /** Field description */
        public static final String QUESTS_NODE_STR = "quests";

        /** Field description */
        public static final String ITEM_NODE_STR = "item";

        /** Field description */
        public static final String TASK_NODE_STR = "task";

        /** Field description */
        public static final String CONDITION_NODE_STR = "cond";

        /** Field description */
        public static final String ACTION_NODE_STR = "action";

        /** Field description */
        public static final String Q_NODE_STR = "q";

        /** Field description */
        public static final String PHASE_NODE_STR = "phase";

        /** Field description */
        public static final String START_NODE_STR = "start";

        /** Field description */
        public static final String FINISH_NODE_STR = "finish";

        /** Field description */
        public static final String EXPIRED_NODE_STR = "expired";

        /** Field description */
        public static final String FAIL_NODE_STR = "fail";

        /** Field description */
        public static final String JOU_NODE_STR = "jou";

        /** Field description */
        public static final String JOUFULL_NODE_STR = "journal";

        /** Field description */
        public static final String JOURNAL_ACTIVE_NODE_STR = "journal_active";

        /** Field description */
        public static final String START_QUEST_NODE_STR = "startquest";

        /** Field description */
        public static final String FINISH_QUEST_NODE_STR = "finishquest";

        /** Field description */
        public static final String START_ORG_NODE_STR = "startorg";

        /** Field description */
        public static final String FINISH_ORG_NODE_STR = "finishorg";

        /** Field description */
        public static final String FAIL_ORG_NODE_STR = "failorg";

        /** Field description */
        public static final String MP_NODE_STR = "MP";

        /** Field description */
        public static final String PHASE_QUEST_NODE_STR = "phasequest";

        /** Field description */
        public static final String WAIT_NODE_STR = "wait";

        /** Field description */
        public static final String SETSODEFAULT_STR = "so_animation";

        /** Field description */
        public static final String ENDSCENARIO_STR = "sc_countdown";

        /** Field description */
        public static final String STOPENDSCENARIO_STR = "stop_sc_countdown";

        /** Field description */
        public static final String CREATE_RACE_ATTR_STR = "race";

        /** Field description */
        public static final String CREATE_QUEST_ITEM_SEMITRAILER_ATTR_STR = "QIsemitrailer";

        /** Field description */
        public static final String CREATE_QUEST_ITEM_DELETE_ATTR_STR = "deletequestitem";

        /** Field description */
        public static final String CREATE_QUEST_ITEM_REMOVE_ATTR_STR = "removequestitem";

        /** Field description */
        public static final String START_MISSION_NODE_STR = "startmission";

        /** Field description */
        public static final String DECLINE_MISSION_NODE_STR = "declinemission";

        /** Field description */
        public static final String FINISH_MISSION_NODE_STR = "finishmission";

        /** Field description */
        public static final String POST_MISSIONINFO_NODE_STR = "postinfo";

        /** Field description */
        public static final String DEACTIVATE_MISSION_NODE_STR = "deactivatemission";

        /** Field description */
        public static final String STAR_SCENARIO_MISSION_NODE_STR = "startscenariomission";

        /** Field description */
        public static final String CHANGE_MISSION_DESTINATION_NODE_STR = "changemd";

        /** Field description */
        public static final String SCENARIO_FLAG_NODE_STR = "set_scenario_flag";

        /** Field description */
        public static final String MAKE_PAYOFF_NODE_STR = "make_payoff";

        /** Field description */
        public static final String STAGE_NODE_STR = "scenario_stage";

        /** Field description */
        public static final String SCENARIO_FINISHED_NODE_STR = "scenario_finished";

        /** Field description */
        public static final String POLICE_IMMUNITY_NODE_STR = "police_immunity";

        /** Field description */
        public static final String KILL_TAKEN_MISSIONS_NODE_STR = "kill_taken_missions";

        /** Field description */
        public static final String TRAFFIC_NODE_STR = "traffic";
    }
}


//~ Formatted in DD Std on 13/08/27
