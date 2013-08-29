/*
 * @(#)IRootNodeNames.java   13/08/28
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


package rnr.src.xmlserialization;

/**
 * Interface description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public abstract interface IRootNodeNames {

    /** Field description */
    public static final String VERSION = "version";

    /** Field description */
    public static final String PLAYER_NODE = "aiplayer";

    /** Field description */
    public static final String PACK_NODE = "aipack";

    /** Field description */
    public static final String IDENTITIE_ATTR = "identitie";

    /** Field description */
    public static final String MODELBASED_ATTR = "modelbasedmodel";

    /** Field description */
    public static final String POOLBASED_ATTR = "poolbasedmodel";

    /** Field description */
    public static final String POLLREFNAME_ATTR = "poolref_name";

    /** Field description */
    public static final String IDFORMODELCREATOR_ATTR = "idForModelCreator";

    /** Field description */
    public static final String MODEL_CREATOR_NODE = "modelcreator";

    /** Field description */
    public static final String CLASSNAME_ATTR = "classname";

    /** Field description */
    public static final String INTERFACE_NODE = "interface";

    /** Field description */
    public static final String ACTORVEH_NODE = "actorveh";

    /** Field description */
    public static final String UID_ATTR = "uid";

    /** Field description */
    public static final String INTEGER_NODE = "int";

    /** Field description */
    public static final String STRING_LIST_NAME = "string_list";

    /** Field description */
    public static final String STRING_NODE = "string";

    /** Field description */
    public static final String VALUE_ATTR = "value";

    /** Field description */
    public static final String LIST_ELEMENT_NODE = "element";

    /** Field description */
    public static final String DRIVER_MODELS_POOL_NODE = "driversmodelspool";

    /** Field description */
    public static final String CYCLELIST_NODE = "CycleListModelNames";

    /** Field description */
    public static final String EXPOSING_NODE = "exposing";

    /** Field description */
    public static final String FREENICKNAMES_NODE = "freenicknames";

    /** Field description */
    public static final String BUSSYNICKNAMES_NODE = "bussynicknames";

    /** Field description */
    public static final String POOL_NODE = "pool";

    /** Field description */
    public static final String NICKNAME_NODE = "nickname";

    /** Field description */
    public static final String NICKNAME_IDENTITIE_ATTR = "identitie";

    /** Field description */
    public static final String NICKNAME_NICK_ATTR = "nickname";

    /** Field description */
    public static final String UNIQUE_NICKNAME_NODE = "uniqnickname";

    /** Field description */
    public static final String COUNTER_ATTR = "counter";

    /** Field description */
    public static final String CAR_ANIMATIONS_NODE = "caranimationscontroller";

    /** Field description */
    public static final String ASSIGNED_ANIMATIONS_NODE = "assignedanimations";

    /** Field description */
    public static final String LOADED_CARS_NODE = "loadedcars";

    /** Field description */
    public static final String NON_LOADED_CARS_NODE = "nonloadedcars";

    /** Field description */
    public static final String SCENARIO_CARS_NODE = "scenariocarcreationcontroller";

    /** Field description */
    public static final String LIVE_CARS_NODE = "livecarcreationcontroller";

    /** Field description */
    public static final String MAPPED_CARS_NODE = "mappedcars";

    /** Field description */
    public static final String LIST_NODE = "list";

    /** Field description */
    public static final String CREW_NODE = "crew";

    /** Field description */
    public static final String SCRIPTREF_NODE = "scriptref";

    /** Field description */
    public static final String CORETIME_NODE = "coretime";

    /** Field description */
    public static final String YEAR_ATTR = "year";

    /** Field description */
    public static final String MONTH_ATTR = "month";

    /** Field description */
    public static final String DATE_ATTR = "date";

    /** Field description */
    public static final String HOUR_ATTR = "hour";

    /** Field description */
    public static final String MINUTS_ATTR = "minuten";

    /** Field description */
    public static final String BIGRACE_NODE = "bigrace";

    /** Field description */
    public static final String BIGRACE_FIRSTTIME_ATTR = "firsttime";

    /** Field description */
    public static final String BIGRACE_MONSTERCUPPASSED_ATTR = "monstercuppassed";

    /** Field description */
    public static final String BIGRACE_SERIES_LOW_ATTR = "serieslow";

    /** Field description */
    public static final String BIGRACE_SERIES_HIGH_ATTR = "seriehigh";

    /** Field description */
    public static final String BIGRACE_NEXTRACE_NODE = "nextrace";

    /** Field description */
    public static final String BIGRACE_LASTTIMESCHEDULED_NODE = "lasttimescheduled";

    /** Field description */
    public static final String SO_MODELPRESETS_NODE = "somodelpreset";

    /** Field description */
    public static final String SO_MODELPRESETS_NAME_ATTR = "name";

    /** Field description */
    public static final String SO_MODELPRESETS_PLACE_ATTR = "placeflag";

    /** Field description */
    public static final String SO_MODELPRESETS_TAG_ATTR = "tag";

    /** Field description */
    public static final String SO_MODELPRESETS_WEIHJT_ATTR = "weight";

    /** Field description */
    public static final String SO_MODELPRESETS_ISMAN_ATTR = "isman";

    /** Field description */
    public static final String SO_PRESETS_NODE = "presets";

    /** Field description */
    public static final String SO_PRESETS_MODELS_NODE = "presetsmodels";

    /** Field description */
    public static final String SO_STO_PRESETS_NODE = "stopresets";

    /** Field description */
    public static final String SO_STO_PRESETS_MONEY_ATTR = "money";

    /** Field description */
    public static final String STO_HISTORY_NODE = "stohistory";

    /** Field description */
    public static final String STO_HISTORY_COUNTMEETING_ATTR = "count_meetings";

    /** Field description */
    public static final String STO_HISTORY_LASTCARMODEL_ATTR = "lastcarmodel";

    /** Field description */
    public static final String STO_HISTORY_CAMEWITHNEWCAR_ATTR = "cameWithNewCar";

    /** Field description */
    public static final String STO_HISTORY_DAMAGELEVEL_ATTR = "damagelevel";

    /** Field description */
    public static final String SPECOBJECT_NODE = "specobject";

    /** Field description */
    public static final String SPECOBJECT_NAME_ATTR = "name";

    /** Field description */
    public static final String SPECOBJECT_TYPE_ATTR = "type";

    /** Field description */
    public static final String SO_NODE = "so";

    /** Field description */
    public static final String SO_CACH_NODE = "socach";

    /** Field description */
    public static final String TIMEACTION_NODE = "timeaction";

    /** Field description */
    public static final String TIMEACTION_DAYS_ATTR = "days";

    /** Field description */
    public static final String TIMEACTION_HOURS_ATTR = "hours";

    /** Field description */
    public static final String TIMEACTION_NAME_ATTR = "name";

    /** Field description */
    public static final String TIMEACTION_EVENTENTER_NODE = "enterevent";

    /** Field description */
    public static final String TIMEACTION_EVENTLEAVE_NODE = "exitevent";

    /** Field description */
    public static final String DAYCOUNTERACTION_NODE = "dayCounterAction";

    /** Field description */
    public static final String DAYCOUNTERACTION_DAYS_ATTR = "days";

    /** Field description */
    public static final String ENDSCENARIO_NODE = "endscenario";

    /** Field description */
    public static final String ENDSCENARIO_TIMEACTIONS_NODE = "endscenariotimeactions";

    /** Field description */
    public static final String ENDSCENARIO_DAYSCOUNTERACTIONS_NODE = "endscenariodaycounteractions";

    /** Field description */
    public static final String UIDSTORAGE_NODE = "uidstorage";

    /** Field description */
    public static final String UIDSTORAGE_MAXXREATED_ATTR = "maxcreated";

    /** Field description */
    public static final String OBJECTSERIALIZATOR_NODE = "objectserializator";

    /** Field description */
    public static final String PREPARESC00060_NODE = "Preparesc00060";

    /** Field description */
    public static final String PREPARESC00060_BANDITCAR_NODE = "banditsCar";

    /** Field description */
    public static final String PREPARESC00060_ALREADYCALLED_ATTR = "alreadyCalled";

    /** Field description */
    public static final String PREPARESC00060_CHECKCONDITION_ATTR = "checkecondition";

    /** Field description */
    public static final String PREPARESC00060_COUNTERTURNERON_ATTR = "counterTurnedOn";

    /** Field description */
    public static final String PREPARESC00060_COUNTERLIMITSUCCEDED_ATTR = "counterlimit2Succeded";

    /** Field description */
    public static final String PREPARESC00060_QUEST_FAILURE_TIME_ATTR = "quest_failure_time";

    /** Field description */
    public static final String PREPARESC00060_QUEST_START_TIME_ATTR = "quest_start_time";

    /** Field description */
    public static final String CHASE00090_NODE = "chase00090";

    /** Field description */
    public static final String CHASE90_Dorothy_NODE = "Dorothy";

    /** Field description */
    public static final String CHASE90_StartTime_NODE = "StartTime";

    /** Field description */
    public static final String CHASEKOH_NODE = "chasekoh";

    /** Field description */
    public static final String CHASEKOH_TIMECHASE_ATTR = "toolongtime";

    /** Field description */
    public static final String CHASEKOH_kohcarforshootanimation_NODE = "kohcarforshootanimation";

    /** Field description */
    public static final String CHASEKOH_haswantblowanimation_ATTR = "haswantblowanimation";

    /** Field description */
    public static final String CHASEKOH_chase_started_ATTR = "chase_started";

    /** Field description */
    public static final String CHASEKOH_koh_chase_player_NODE = "koh_chase_player";

    /** Field description */
    public static final String CHASEKOH__last_chance_failed_ATTR = "_last_chance_failed";

    /** Field description */
    public static final String CHASETOPO_NODE = "chasetopo";

    /** Field description */
    public static final String CHASETOPO_CURstate_ATTR = "CURstate";

    /** Field description */
    public static final String CHASETOPO_finishcallKohrein_ATTR = "finishcallKohrein";

    /** Field description */
    public static final String CHASETOPO_startcallKohrein_ATTR = "startcallKohrein";

    /** Field description */
    public static final String CHASETOPO_countdouwn_KohCall_ATTR = "countdouwn_KohCall";

    /** Field description */
    public static final String CHASETOPO_to_prepatreDakota_ATTR = "to_prepatreDakota";

    /** Field description */
    public static final String CHASETOPO_deinitDakota_done_ATTR = "deinitDakota_done";

    /** Field description */
    public static final String CHASETOPO_to_prepare_punktA_ATTR = "to_prepare_punktA";

    /** Field description */
    public static final String CHASETOPO_needCustoremCall1_ATTR = "need_customer_call1";

    /** Field description */
    public static final String CHASETOPO_to_prepatreDarkTruck_ATTR = "to_prepatreDarkTruck";

    /** Field description */
    public static final String CHASETOPO_was_in_chaseagain0_ATTR = "was_in_chaseagain0";

    /** Field description */
    public static final String CHASETOPO_rightorder_on_bridgescene_ATTR = "rightorder_on_bridgescene";

    /** Field description */
    public static final String CHASETOPO_needAfterBridge_ATTR = "need_after_bridge";

    /** Field description */
    public static final String CHASETOPO_tostop_ATTR = "tostop";

    /** Field description */
    public static final String CHASETOPO_to_stop_ATTR = "to_stop";

    /** Field description */
    public static final String CHASETOPO_alreadyblown_ATTR = "alreadyblown";

    /** Field description */
    public static final String CHASETOPO_move_to_badscenario_ATTR = "move_to_badscenario";

    /** Field description */
    public static final String CHASETOPO_move_to_chase_ATTR = "move_to_chase";

    /** Field description */
    public static final String CHASETOPO_count_timeSilentQuest_ATTR = "count_timeSilentQuest";

    /** Field description */
    public static final String CHASETOPO_countdown_firstchase_ATTR = "countdown_firstchase";

    /** Field description */
    public static final String CHASETOPO_wait_customer_call2_ATTR = "wait_customer_call2";

    /** Field description */
    public static final String CHASETOPO_is_animate_bridge_ATTR = "is_animate_bridge";

    /** Field description */
    public static final String CHASETOPO_animate_bridge_starttime_ATTR = "animate_bridge_starttime";

    /** Field description */
    public static final String CHASETOPO_contestProceed_ATTR = "procceed_contest";

    /** Field description */
    public static final String CHASETOPO_animate_bridge_time_ATTR = "animate_bridge_time";

    /** Field description */
    public static final String CHASETOPO_stalkers_NODE = "stalkers";

    /** Field description */
    public static final String CURSEDHIWAY_NODE = "cursedhiway";

    /** Field description */
    public static final String CURSEDHIWAY_positionInfluenceZone_NODE = "positionInfluenceZone";

    /** Field description */
    public static final String CURSEDHIWAY_on_animation_ATTR = "on_animation";

    /** Field description */
    public static final String CURSEDHIWAY_release_animation_ATTR = "release_animation";

    /** Field description */
    public static final String CURSEDHIWAY_stop_animation_ATTR = "stop_animation";

    /** Field description */
    public static final String CURSEDHIWAY_last_coef_ATTR = "last_coef";

    /** Field description */
    public static final String CURSEDHIWAY_dead_from_mist_ATTR = "dead_from_mist";

    /** Field description */
    public static final String ENEMYBASE_NODE = "enemybase";

    /** Field description */
    public static final String ENEMYBASE_dwords_NODE = "dwords";

    /** Field description */
    public static final String ENEMYBASE_cars_NODE = "cars";

    /** Field description */
    public static final String ENEMYBASE_cars_assault_NODE = "cars_assault";

    /** Field description */
    public static final String ENEMYBASE_monica_NODE = "monica";

    /** Field description */
    public static final String ENEMYBASE_dakota_NODE = "dakota";

    /** Field description */
    public static final String ENEMYBASE_to_make_discover_base_NODE = "to_make_discover_base";

    /** Field description */
    public static final String ENEMYBASE_to_make_dakota_call_NODE = "to_make_dakota_call";

    /** Field description */
    public static final String ENEMYBASE_to_make_threat_call_NODE = "to_make_threat_call";

    /** Field description */
    public static final String ENEMYBASE_to_make_2020_call_NODE = "to_make_2020_call";

    /** Field description */
    public static final String ENEMYBASE_isAssaultCycleSceneCreated_ATTR = "isAssaultCycleSceneCreated";

    /** Field description */
    public static final String ENEMYBASE_wasVehicleExchange_ATTR = "wasVehicleExchange";

    /** Field description */
    public static final String ENEMYBASE_bad_conditions_ATTR = "bad_conditions";

    /** Field description */
    public static final String ENEMYBASE_assault_started_ATTR = "assault_started";

    /** Field description */
    public static final String ENEMYBASE_assault_start_time_ATTR = "assault_start_time";

    /** Field description */
    public static final String ENEMYBASE_want_dakota_warning_ATTR = "want_dakota_warning";

    /** Field description */
    public static final String ENEMYBASE_dakota_warning_start_time_ATTR = "dakota_warning_start_time";

    /** Field description */
    public static final String ENEMYBASE_want_dakota_warning_started_ATTR = "want_dakota_warning_started";

    /** Field description */
    public static final String ENEMYBASE_to_slow_down_gepard_ATTR = "to_slow_down_gepard";

    /** Field description */
    public static final String ENEMYBASE_slowdown_start_ATTR = "slowdown_start";

    /** Field description */
    public static final String ENEMYBASE_initialVelocity_ATTR = "initialVelocity";

    /** Field description */
    public static final String ENEMYBASE_SLOW_DOWN_ACCEL_ATTR = "SLOW_DOWN_ACCEL";

    /** Field description */
    public static final String ENEMYBASE_to_track_tunnel_ATTR = "to_track_tunnel";

    /** Field description */
    public static final String ENEMYBASE_was_near_base_ATTR = "was_near_base";

    /** Field description */
    public static final String KOHHELP_NODE = "kohhelp";

    /** Field description */
    public static final String KOHHELP_current_scene_ATTR = "current_scene";

    /** Field description */
    public static final String KOHHELP_f_makeKoh_ATTR = "f_makeKoh";

    /** Field description */
    public static final String KOHHELP_f_wasMadeKoh_ATTR = "f_wasMadeKoh";

    /** Field description */
    public static final String KOHHELP_timeKohOrdered_NODE = "timeKohOrdered";

    /** Field description */
    public static final String REDROCKCANYON_NODE = "redRockCanyon";

    /** Field description */
    public static final String REDROCKCANYON_TIME_ATTR = "time";

    /** Field description */
    public static final String PITERPANDOOMEDRACE_NODE = "piterpandommedrace";

    /** Field description */
    public static final String PITERPANFINALRACE_NODE = "piterpanfinalrace";

    /** Field description */
    public static final String PITERPANRACE_FINISHED_ATTR = "finished";

    /** Field description */
    public static final String PITERPANRACE_RACENAME_ATTR = "racename";

    /** Field description */
    public static final String VECTOR3D_NODE = "vector3d";

    /** Field description */
    public static final String X_ATTR = "x";

    /** Field description */
    public static final String Y_ATTR = "y";

    /** Field description */
    public static final String Z_ATTR = "z";

    /** Field description */
    public static final String MATRIX3D_NODE = "matrix3d";

    /** Field description */
    public static final String MATRIX_0_NODE = "v0";

    /** Field description */
    public static final String MATRIX_1_NODE = "v1";

    /** Field description */
    public static final String MATRIX_2_NODE = "v2";

    /** Field description */
    public static final String RACESPACE_NODE = "racespace";

    /** Field description */
    public static final String RACESPACE_raceFSMstate_ATTR = "raceFSMstate";

    /** Field description */
    public static final String RACESPACE_race_condition__condition_ATTR = "race_condition__condition";

    /** Field description */
    public static final String RACESPACE_timeElapsed_ATTR = "timeElapsed";

    /** Field description */
    public static final String RACESPACE_anyonfinish_ATTR = "anyonfinish";

    /** Field description */
    public static final String RACESPACE_lastplace_ATTR = "lastplace";

    /** Field description */
    public static final String RACESPACE_statesucceded_ATTR = "statesucceded";

    /** Field description */
    public static final String RACESPACE_participants_NODE = "participants";

    /** Field description */
    public static final String RACESPACE_singledata_NODE = "participantssingledata";

    /** Field description */
    public static final String RACESPACE_singledata_finished_ATTR = "finished";

    /** Field description */
    public static final String RACESPACE_singledata_place_ATTR = "place";

    /** Field description */
    public static final String RACESPACE_singledata_distance_ATTR = "distance";

    /** Field description */
    public static final String RACESPACE_state_ATTR = "state";

    /** Field description */
    public static final String RACESPACE_currentwait_ATTR = "currentwait";

    /** Field description */
    public static final String RACESPACE_critical_succeded_ATTR = "critical_succeded";

    /** Field description */
    public static final String RACESPACE_igrokonwarehouse_ATTR = "igrokonwarehouse";

    /** Field description */
    public static final String STATICSCENARIOVARIABLES_NODE = "staticscenariovariables";

    /** Field description */
    public static final String STATICSCENARIOVARIABLES_readypreparesc00060_ATTR = "readypreparesc00060";

    /** Field description */
    public static final String STATICSCENARIOVARIABLES_readycursedhiway_ATTR = "readycursedhiway";

    /** Field description */
    public static final String ORGANIZER_NODE = "organiser";

    /** Field description */
    public static final String ORGANIZER_WAREHOUSE_ELEMENTS_NUMBER_ATTR = "numwarehouseorders";

    /** Field description */
    public static final String ORGANIZER_ORGELEMENTS_NODE = "orgelements";

    /** Field description */
    public static final String ORGANIZER_ITEMS_NODE = "items";

    /** Field description */
    public static final String ORGANIZER_ELEMENT_NODE = "element";

    /** Field description */
    public static final String ORGANIZER_ELEMENT_NAME_ATTR = "name";

    /** Field description */
    public static final String ORGANIZER_MISSION_ITEMS_NODE = "missionitems";

    /** Field description */
    public static final String ORGANIZER_MISSION_MISSIONNAME_ATTR = "missionname";

    /** Field description */
    public static final String ORGANIZER_MISSION_ORGNAME_ATTR = "orgname";

    /** Field description */
    public static final String ORGANIZER_NODE_NODE = "organisernode";

    /** Field description */
    public static final String ORGANIZER_NODE_id_ATTR = "id";

    /** Field description */
    public static final String ORGANIZER_NODE_type_ATTR = "type";

    /** Field description */
    public static final String ORGANIZER_NODE_status_ATTR = "status";

    /** Field description */
    public static final String ORGANIZER_NODE_hasfragility_ATTR = "hasfragility";

    /** Field description */
    public static final String ORGANIZER_NODE_fragility_ATTR = "fragility";

    /** Field description */
    public static final String ORGANIZER_NODE_important_ATTR = "important";

    /** Field description */
    public static final String ORGANIZER_NODE_rewardflag_ATTR = "rewardflag";

    /** Field description */
    public static final String ORGANIZER_NODE_forfeitflag_ATTR = "forfeit_flag";

    /** Field description */
    public static final String ORGANIZER_NODE_description_ATTR = "description";

    /** Field description */
    public static final String ORGANIZER_NODE_coeftimepickup_ATTR = "coef_time_to_pickup";

    /** Field description */
    public static final String ORGANIZER_NODE_coeftimecomplete_ATTR = "coef_time_to_complete";

    /** Field description */
    public static final String ORGANIZER_NODE_startpoint_ATTR = "startpoint";

    /** Field description */
    public static final String ORGANIZER_NODE_pickuppoint_ATTR = "pickuppoint";

    /** Field description */
    public static final String ORGANIZER_NODE_finishpoint_ATTR = "finishpoint";

    /** Field description */
    public static final String ORGANIZER_NODE_CURRENT_NODE = "current";

    /** Field description */
    public static final String ORGANIZER_NODE_CURRENT_WAREHOUSE_NODE = "current_warehouse_order";

    /** Field description */
    public static final String ORGANIZER_NODE_reward_NODE = "reward";

    /** Field description */
    public static final String ORGANIZER_NODE_forfeit_NODE = "forfeit";

    /** Field description */
    public static final String ORGANIZER_NODE_rewardforfeit_money_ATTR = "estimate_money";

    /** Field description */
    public static final String ORGANIZER_NODE_rewardforfeit_rate_ATTR = "estimate_rate";

    /** Field description */
    public static final String ORGANIZER_NODE_rewardforfeit_rank_ATTR = "estimate_rank";

    /** Field description */
    public static final String ORGANIZER_NODE_rewardforfeit_real_money_ATTR = "real_money";

    /** Field description */
    public static final String ORGANIZER_NODE_rewardforfeit_coef_money_ATTR = "coef_money";

    /** Field description */
    public static final String ORGANIZER_NODE_rewardforfeit_coef_rate_ATTR = "coef_rate";

    /** Field description */
    public static final String ORGANIZER_NODE_rewardforfeit_coef_rank_ATTR = "coef_rank";

    /** Field description */
    public static final String ORGANIZER_NODE_rewardforfeit_faction_NODE = "faction";

    /** Field description */
    public static final String ORGANIZER_NODE_rewardforfeit_faction_name_ATTR = "name";

    /** Field description */
    public static final String ORGANIZER_NODE_rewardforfeit_faction_value_ATTR = "value";

    /** Field description */
    public static final String ORGANIZER_NODE_customer_warehouseassosiation_NODE = "customer_wh";

    /** Field description */
    public static final String ORGANIZER_NODE_customer_empty_NODE = "customer_empty";

    /** Field description */
    public static final String ORGANIZER_NODE_customer_npc_NODE = "customer_npc";

    /** Field description */
    public static final String ORGANIZER_NODE_customer_quest_NODE = "customer_quest";

    /** Field description */
    public static final String ORGANIZER_NODE_customer_name_ATTR = "customer_name";

    /** Field description */
    public static final String ORGANIZER_NODE_startnote_NODE = "startNote";

    /** Field description */
    public static final String ORGANIZER_NODE_finishnote_NODE = "finishNote";

    /** Field description */
    public static final String ORGANIZER_NODE_failnote_NODE = "failNotes";

    /** Field description */
    public static final String ORGANIZER_NODE_failnote_EMPTY_NODE = "failNotes_empty";

    /** Field description */
    public static final String ORGANIZER_DECLINELISTENER_NODE = "declinelistener";

    /** Field description */
    public static final String ORGANIZER_DECLINELISTENER_MISSIONNAME_ATTR = "missionName";

    /** Field description */
    public static final String JOURNAL_NODE_STRING = "journal";

    /** Field description */
    public static final String JOURNAL_ELEMENT_NODE_STRING = "journalElement";

    /** Field description */
    public static final String NOM_ATTRIBUTE = "nom";

    /** Field description */
    public static final String JOURNAL_DATE_NODE_STRING = "date";

    /** Field description */
    public static final String JOURNAL_EXPIRATION_DATE_NODE_STRING = "expiration_date";

    /** Field description */
    public static final String JOURNAL_ANSWER_LISTENER_NODE = "answerlistener";

    /** Field description */
    public static final String JOURNAL_ANSWER_LISTENER_NAME_ATTR = "name";

    /** Field description */
    public static final String ALBUM_NODE_STRING = "album";

    /** Field description */
    public static final String ALBUM_ELEMENT_NODE_STRING = "albumElement";

    /** Field description */
    public static final String ALBUM_VALUE = "albumElementValue";

    /** Field description */
    public static final String ALBUM_DESCR_ATTR = "albumElementDescr";

    /** Field description */
    public static final String ALBUM_TEXT_ATTR = "albumElementText";

    /** Field description */
    public static final String ALBUM_MATERIAL_ATTR = "albumElementMaterial";

    /** Field description */
    public static final String ALBUM_BIGRACE_ATTR = "albumElement";

    /** Field description */
    public static final String MACROS_BODY_NODE = "macrosBody";

    /** Field description */
    public static final String MACROS_SIMPLE_ATTR = "macrosBodySimple";

    /** Field description */
    public static final String MACROS_LOCREF_ATTR = "macrosBodyLocref";

    /** Field description */
    public static final String MACROS_STRING_ATTR = "value";

    /** Field description */
    public static final String MACROS_NAMESPACE_ATTR = "namespace";

    /** Field description */
    public static final String MACROS_LOCREF_LOCREF_ATTR = "locref";

    /** Field description */
    public static final String MACROS_NODE = "macro";

    /** Field description */
    public static final String MACROS_KEY_ATTR = "key";

    /** Field description */
    public static final String WAREHOUSEORG_NODE = "warehouseorder";

    /** Field description */
    public static final String WAREHOUSEORG_is_read_ATTR = "is_read";

    /** Field description */
    public static final String WAREHOUSEORG_id_ATTR = "id";

    /** Field description */
    public static final String WAREHOUSEORG_type_ATTR = "type";

    /** Field description */
    public static final String WAREHOUSEORG_status_ATTR = "status";

    /** Field description */
    public static final String WAREHOUSEORG_important_ATTR = "important";

    /** Field description */
    public static final String WAREHOUSEORG_type_failed_ATTR = "type_failed";

    /** Field description */
    public static final String WAREHOUSEORG_rewardFlag_ATTR = "rewardFlag";

    /** Field description */
    public static final String WAREHOUSEORG_forfeitFlag_ATTR = "forfeitFlag";

    /** Field description */
    public static final String WAREHOUSEORG_minutes_to_complete_ATTR = "minutes_to_complete";

    /** Field description */
    public static final String WAREHOUSEORG_seconds_to_complete_ATTR = "seconds_to_complete";

    /** Field description */
    public static final String WAREHOUSEORG_mission_state_ATTR = "mission_state";

    /** Field description */
    public static final String WAREHOUSEORG_fragility_ATTR = "fragility";

    /** Field description */
    public static final String WAREHOUSEORG_loadPoint_ATTR = "loadPoint";

    /** Field description */
    public static final String WAREHOUSEORG_completePoint_ATTR = "completePoint";

    /** Field description */
    public static final String WAREHOUSEORG_orderdescription_ATTR = "orderdescription";

    /** Field description */
    public static final String WAREHOUSEORG_racename_ATTR = "racename";

    /** Field description */
    public static final String WAREHOUSEORG_logoname_ATTR = "logoname";

    /** Field description */
    public static final String WAREHOUSEORG_requestTime_NODE = "requestTime";

    /** Field description */
    public static final String WAREHOUSEORG_completeTime_NODE = "completeTime";

    /** Field description */
    public static final String MISSIONS_NODE = "missions";

    /** Field description */
    public static final String MISSIONS_STATEFULL_FINISH_CONDITIONS_NODE = "sf_finish_conditions";

    /** Field description */
    public static final String MISSIONS_FINISH_CONDITION_NODE = "sf_finish_condition";

    /** Field description */
    public static final String MISSIONS_FINISH_CONDITION_MISSION_ATTR = "mission";

    /** Field description */
    public static final String MISSIONS_FINISH_CONDITION_CONDITION_ATTR = "condition";

    /** Field description */
    public static final String MISSIONS_TIMELEFT_ATTR = "timeleft";

    /** Field description */
    public static final String MISSIONS_ACTIVE_NODE = "activemissions";

    /** Field description */
    public static final String MISSIONS_FINISHED_NODE = "finishedmissions";

    /** Field description */
    public static final String MISSIONS_PRIORITIES_NODE = "missionspriorities";

    /** Field description */
    public static final String MISSIONS_DELAYEDCHANNLES_NODE = "missiondelayedchannels";

    /** Field description */
    public static final String MISSIONS_DELAYEDCHANNLES_MISSION_NAME_ATTR = "mission_name";

    /** Field description */
    public static final String MISSIONS_DELAYEDCHANNLES_CHANNEL_UID_ATTR = "channel_uid";

    /** Field description */
    public static final String MISSIONS_DELAYEDCHANNLES_SECONDS_REMAINED_ATTR = "seconds";

    /** Field description */
    public static final String MISSIONSLOG_NODE = "missionslog";

    /** Field description */
    public static final String MISSIONSACTIVATED_NODE = "missionsactivated";

    /** Field description */
    public static final String NEWSPAPER_NODE = "newspaper";

    /** Field description */
    public static final String NEWSPAPER_TENDER_NODE = "newspaper_tender";

    /** Field description */
    public static final String NEWSPAPER_TENDER_DESTINATION_ATTR = "destination";

    /** Field description */
    public static final String NEWSPAPER_TENDER_MULTIPLIER_ATTR = "multiplier";

    /** Field description */
    public static final String NEWSPAPER_TENDER_YEAR_ATTR = "year";

    /** Field description */
    public static final String NEWSPAPER_TENDER_MONTH_ATTR = "month";

    /** Field description */
    public static final String NEWSPAPER_TENDER_DAY_ATTR = "day";

    /** Field description */
    public static final String NEWSPAPER_TENDER_HOUR_ATTR = "hour";

    /** Field description */
    public static final String NEWSPAPER_TENDER_BASES_NODE = "bases";

    /** Field description */
    public static final String BLOCKSO_NODE = "blockso";

    /** Field description */
    public static final String BLOCKSO_ELEMENT_NODE = "blocker";

    /** Field description */
    public static final String BLOCKSO_ELEMENT_NAME_ATTR = "name";

    /** Field description */
    public static final String BLOCKSO_ELEMENT_TYPE_ATTR = "type";

    /** Field description */
    public static final String BLOCKSO_CONDITION_NODE = "condition";

    /** Field description */
    public static final String BLOCKSO_CONDITION_NOCONDITION_ATTR = "nocondition";

    /** Field description */
    public static final String BLOCKSO_CONDITION_TIMECONDITYION_ATTR = "timecondition";

    /** Field description */
    public static final String BLOCKSO_CONDITION_TIMECONDITION_DELTATIME_NODE = "deltatime";

    /** Field description */
    public static final String BLOCKSO_CONDITION_TIMECONDITION_STARTTIME_NODE = "starttime";

    /** Field description */
    public static final String MISSION_PASSANGER = "mission_passanger";

    /** Field description */
    public static final String SCENARIO_FLAGS = "scenario_flags";

    /** Field description */
    public static final String STATIC_FLAG_NODE = "static_flag";

    /** Field description */
    public static final String TIMED_FLAG_NODE = "timed_flag";

    /** Field description */
    public static final String SCENARIO_FLAG_NAME_ATTR = "name";

    /** Field description */
    public static final String SCENARIO_FLAG_VALUE_ATTR = "value";

    /** Field description */
    public static final String FACTION_RATER = "faction_rater";

    /** Field description */
    public static final String FACTION_NODE = "faction";

    /** Field description */
    public static final String FACTION_NAME_ATTR = "name";

    /** Field description */
    public static final String FACTION_VALUE_ATTR = "value";

    /** Field description */
    public static final String RATING_SYSTEM_NODE = "rating_system";

    /** Field description */
    public static final String RATING_SYSTEM_LIVE_NODE = "live_rating_system";

    /** Field description */
    public static final String RATING_SYSTEM_NPC_NODE = "npc_rating_system";

    /** Field description */
    public static final String RATING_SYSTEM_NPC_NAME_ATTR = "name";

    /** Field description */
    public static final String RATING_SYSTEM_PLAYER_RATING_NODE = "player_rating";

    /** Field description */
    public static final String RATING_SYSTEM_PLAYER_RATING_ID_ATTR = "id";

    /** Field description */
    public static final String RATING_SYSTEM_PLAYER_RATING_VALUE_ATTR = "rating";

    /** Field description */
    public static final String RATING_SYSTEM_CURRENT_RATER_NODE = "current_rater";

    /** Field description */
    public static final String RATING_SYSTEM_CURRENT_RATER_MISSIONS_NODE = "current_rater_mission";

    /** Field description */
    public static final String RATING_SYSTEM_CURRENT_RATER_MISSIONS_NAME_ATTR = "name";

    /** Field description */
    public static final String RATING_SYSTEM_RATER_NODE = "rater";

    /** Field description */
    public static final String RATING_SYSTEM_RATER_CHECKPOINT_ATTR = "checkpoint";

    /** Field description */
    public static final String RATING_SYSTEM_RATER_MANEY_ATTR = "money";

    /** Field description */
    public static final String RATING_SYSTEM_RATER_VALUE_NODE = "rating_value";

    /** Field description */
    public static final String RATING_SYSTEM_RATER_VALUE__VALUE_ATTR = "value";

    /** Field description */
    public static final String CBVIDEO_NODE = "cbvideo";

    /** Field description */
    public static final String CBVIDEO_ELEMENT_NODE = "single_cbcall";

    /** Field description */
    public static final String CBVIDEO_NAME_ATTR = "name";

    /** Field description */
    public static final String CBVIDEO_IDENTITIE_ATTR = "identitie";

    /** Field description */
    public static final String DELAYED_CHAN_NODE_STRING = "delayed_channel";

    /** Field description */
    public static final String DELAYED_CHAN_NODE_MISSIONS = "mission";

    /** Field description */
    public static final String DC_MISSION_NAME = "name";

    /** Field description */
    public static final String DC_TIME = "time";
}


//~ Formatted in DD Std on 13/08/28
