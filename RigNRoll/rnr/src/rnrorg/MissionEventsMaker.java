/*
 * @(#)MissionEventsMaker.java   13/08/26
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


package rnr.src.rnrorg;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.JavaEvents;
import rnr.src.menuscript.BarMenu.BarPack;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.loc;
import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrscenario.missions.IChannelEventListener;
import rnr.src.rnrscenario.missions.StartMissionListeners;
import rnr.src.rnrscenario.missions.infochannels.InfoChannelAcceptEvent;
import rnr.src.rnrscenario.missions.infochannels.InfoChannelAppearEvent;
import rnr.src.rnrscenario.missions.infochannels.InfoChannelDeclineEvent;
import rnr.src.rnrscenario.missions.infochannels.InfoChannelEndEvent;
import rnr.src.rnrscr.IMissionInformation;
import rnr.src.scriptEvents.EventsControllerHelper;
import rnr.src.scriptEvents.MissionEndDialogEnded;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class MissionEventsMaker {
    private static volatile boolean flag = false;

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param finish_point
     */
    public static void changeMissionDestination(String mission_name, String finish_point) {
        JavaEvents.SendEvent(73, 0, new GetTwoStrings(mission_name, finish_point));

        String org_name = MissionOrganiser.getInstance().getOrgForMission(mission_name);

        if ((null == org_name) || (org_name.length() == 0)) {
            eng.err("changeMissionDestination couldn't find organiser for mission " + mission_name);

            return;
        }

        IStoreorgelement elem = Organizers.getInstance().get(org_name);

        if ((null == elem) || (!(elem instanceof Scorgelement))) {
            eng.err("changeMissionDestination couldn't find organiser element named " + org_name + " for mission "
                    + mission_name + " or have another class realization.");
        } else {
            Scorgelement scorgelem = (Scorgelement) elem;

            scorgelem.changeDestination(finish_point);
            organaiser.getInstance().updateMissionsOrgElements();
        }
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param isChannelImmeiate
     * @param info_channel_point
     * @param qi_point
     * @param finish_point
     */
    public static void startMission(String mission_name, boolean isChannelImmeiate, String info_channel_point,
                                    String qi_point, String finish_point) {
        startMission_parammed(0, mission_name, isChannelImmeiate, info_channel_point, qi_point, finish_point, -1, null,
                              false, true);
    }

    /**
     * Method description
     *
     *
     * @param race_uid
     * @param mission_name
     * @param isChannelImmeiate
     * @param info_channel_point
     * @param qi_point
     * @param finish_point
     */
    public static void startMission_AnnounceBigRace(int race_uid, String mission_name, boolean isChannelImmeiate,
            String info_channel_point, String qi_point, String finish_point) {
        startMission_parammed(1, mission_name, isChannelImmeiate, info_channel_point, qi_point, finish_point, race_uid,
                              null, false, true);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param isChannelImmeiate
     * @param info_channel_point
     * @param qi_point
     * @param finish_point
     * @param time_on_mission
     * @param move_time
     * @param needFinishIcon
     */
    public static void startMission_ScenarioMission(String mission_name, boolean isChannelImmeiate,
            String info_channel_point, String qi_point, String finish_point, CoreTime time_on_mission,
            boolean move_time, boolean needFinishIcon) {
        startMission_parammed(2, mission_name, isChannelImmeiate, info_channel_point, qi_point, finish_point, -1,
                              time_on_mission, move_time, needFinishIcon);
    }

    private static void startMission_parammed(int event_value, String mission_name, boolean isChannelImmeiate,
            String info_channel_point, String qi_point, String finish_point, int race_uid, CoreTime time_on_mission,
            boolean move_time, boolean needFinishIcon) {
        String org_name = MissionOrganiser.getInstance().getOrgForMission(mission_name);

        if (null == org_name) {
            MissionsLogger.getInstance().doLog("startMission. Cannot find org name for mission named " + mission_name,
                                               Level.SEVERE);

            return;
        }

        IStoreorgelement elem = Organizers.getInstance().get(org_name);

        if (null == elem) {
            MissionsLogger.getInstance().doLog("startMission. Cannot find org instance for mission named "
                                               + mission_name + "and org named " + org_name, Level.SEVERE);

            return;
        }

        elem.addDeclineListener(new DeclineMissionListener(mission_name));

        if (elem instanceof Scorgelement) {
            if (qi_point == null) {
                qi_point = info_channel_point;
            }

            Scorgelement org_elem = (Scorgelement) elem;

            org_elem.setSerialPoints(info_channel_point, qi_point, finish_point);
            JavaEvents.SendEvent(48, event_value,
                                 new StartMissionData(mission_name, isChannelImmeiate, info_channel_point, qi_point,
                                     finish_point, org_elem, race_uid, time_on_mission, move_time, needFinishIcon));
        } else {
            MissionsLogger.getInstance().doLog("startMission. Undefined behovouir. Org instance for mission named "
                                               + mission_name + "and org named " + org_name + " has class named "
                                               + elem.getClass().getName(), Level.SEVERE);
        }
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     */
    public static void declineMission(String mission_name) {
        JavaEvents.SendEvent(49, 0, new Data(mission_name));
    }

    /**
     * Method description
     *
     */
    public static void declineWareHouseMission() {
        JavaEvents.SendEvent(49, 1, new Object());
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param merchandise_name
     * @param point_name
     */
    public static void createQuestItemSemitrailer(String mission_name, String merchandise_name, String point_name) {
        JavaEvents.SendEvent(51, 0, new QuestItemData(mission_name, merchandise_name, point_name, "no uid"));
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param merchandise_name
     * @param point_name
     */
    public static void createQuestItemPackage(String mission_name, String merchandise_name, String point_name) {
        JavaEvents.SendEvent(51, 1, new QuestItemData(mission_name, merchandise_name, point_name, "no uid"));
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param channel_uid
     * @param places
     * @param priority
     * @param important
     * @param fginish
     * @param succes
     * @param fmain
     */
    public static void createChannelResourcesPlaces(String mission_name, String channel_uid, ArrayList<String> places,
            int priority, boolean important, boolean fginish, boolean succes, boolean fmain) {
        GetMissionChannelResourcesInfo info = new GetMissionChannelResourcesInfo(mission_name, channel_uid, priority,
                                                  important, fginish, succes, fmain);

        info.data = new Vector(places);
        JavaEvents.SendEvent(51, 6, info);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param model_name
     * @param color
     * @param uid
     */
    public static void createQuestItemAddItem(String mission_name, String model_name, int color, String uid) {
        QuestItemData data = new QuestItemData(mission_name, model_name, "", uid);

        data.color = color;
        JavaEvents.SendEvent(51, 2, data);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param model_name
     * @param uid
     */
    public static void createQuestItemNPC(String mission_name, String model_name, String uid) {
        JavaEvents.SendEvent(51, 3, new QuestItemData(mission_name, model_name, "", uid));
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param model_name
     * @param point_name
     */
    public static void createQuestItemPassanger(String mission_name, String model_name, String point_name) {
        JavaEvents.SendEvent(51, 4, new QuestItemData(mission_name, model_name, point_name, "no uid"));
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param model_name
     * @param point_name
     */
    public static void createQuestItemPassangerNoAnimation(String mission_name, String model_name, String point_name) {
        JavaEvents.SendEvent(51, 4, new QuestItemData(mission_name, model_name, point_name, "no uid", false));
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param model_name
     * @param point_name
     */
    public static void createQuestItemVisit(String mission_name, String model_name, String point_name) {
        JavaEvents.SendEvent(51, 5, new QuestItemData(mission_name, model_name, point_name, "no uid"));
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param uid
     */
    public static void clearResource(String mission_name, String uid) {
        GetTwoStrings info = new GetTwoStrings(mission_name, uid);

        JavaEvents.SendEvent(60, 0, info);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     */
    public static void abonishMission(String mission_name) {
        DataName info = new DataName(mission_name);

        JavaEvents.SendEvent(61, 0, info);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     */
    public static void declineMissionNotFromOrganoser(String mission_name) {
        organaiser.declineMissionByName(mission_name);
    }

    /**
     * Method description
     *
     *
     * @param dialog
     *
     * @return
     */
    public static SCRuniperson queueNPCResourseForDialog(IMissionInformation dialog) {
        QueueDialogResource res = new QueueDialogResource(dialog.getMissionName(), dialog.getChannelId());

        JavaEvents.SendEvent(52, 1, res);

        return res.person;
    }

    /**
     * Method description
     *
     *
     * @param dialog
     * @param identitie
     *
     * @return
     */
    public static aiplayer queueNPCPlayer_FreeFromVoter(IMissionInformation dialog, String identitie) {
        QueueDialogResource res = new QueueDialogResource(dialog.getMissionName(), dialog.getChannelId());

        JavaEvents.SendEvent(52, 2, res);

        aiplayer npc = aiplayer.getNamedAiplayerHiPoly(identitie, dialog.getMissionName() + " hot person");

        return npc;
    }

    /**
     * Method description
     *
     *
     * @param dialog
     */
    public static void npcPlayer_ResumeVoter(IMissionInformation dialog) {
        QueueDialogResource res = new QueueDialogResource(dialog.getMissionName(), dialog.getChannelId());

        JavaEvents.SendEvent(52, 3, res);
    }

    /**
     * Method description
     *
     *
     * @param channel_id
     * @param mission_name
     * @param point_name
     * @param isChannelImmediate
     */
    public static void channelSayAccept(String channel_id, String mission_name, String point_name,
            boolean isChannelImmediate) {
        JavaEvents.SendEvent(47, 1, new DataName(mission_name));

        if (null == point_name) {
            point_name = "";
        }

        IChannelEventListener lst = StartMissionListeners.getInstance().getChannleEventListener(mission_name);

        if (lst != null) {
            lst.eventOnChannel(point_name, channel_id, isChannelImmediate);
        }

        InfoChannelAcceptEvent event = new InfoChannelAcceptEvent(channel_id, point_name);

        EventsControllerHelper.eventHappened(event);
        EventsControllerHelper.messageEventHappened(mission_name + " accepted");
    }

    /**
     * Method description
     *
     *
     * @param channel_id
     * @param mission_name
     * @param point_name
     * @param isChannelImmediate
     */
    public static void channelSayDeclein(String channel_id, String mission_name, String point_name,
            boolean isChannelImmediate) {
        JavaEvents.SendEvent(47, 2, new DataName(mission_name));

        if (null == point_name) {
            point_name = "";
        }

        IChannelEventListener lst = StartMissionListeners.getInstance().getChannleEventListener(mission_name);

        if (lst != null) {
            lst.eventOnChannel(point_name, channel_id, isChannelImmediate);
        }

        InfoChannelDeclineEvent event = new InfoChannelDeclineEvent(channel_id, point_name);

        EventsControllerHelper.eventHappened(event);
        EventsControllerHelper.messageEventHappened(mission_name + " declined");
    }

    /**
     * Method description
     *
     *
     * @param channel_id
     * @param mission_name
     * @param point_name
     * @param isChannelImmediate
     */
    public static void channelSayAppear(String channel_id, String mission_name, String point_name,
            boolean isChannelImmediate) {
        JavaEvents.SendEvent(47, 0, new GetBoolNamed(mission_name, isChannelImmediate));

        if (point_name == null) {
            point_name = "";
        }

        IChannelEventListener lst = StartMissionListeners.getInstance().getChannleEventListener(mission_name);

        if (lst != null) {
            lst.eventOnChannel(point_name, channel_id, isChannelImmediate);
        }

        InfoChannelAppearEvent event = new InfoChannelAppearEvent(channel_id, point_name);

        EventsControllerHelper.eventHappened(event);
    }

    /**
     * Method description
     *
     *
     * @param channel_id
     * @param mission_name
     * @param point_name
     * @param isChannleImmediate
     */
    public static void channelSayEnd(String channel_id, String mission_name, String point_name,
                                     boolean isChannleImmediate) {
        MissionEndDialogEnded event = new MissionEndDialogEnded(mission_name, channel_id);

        EventsControllerHelper.eventHappened(event);

        if (null == point_name) {
            point_name = "";
        }

        InfoChannelEndEvent endevent = new InfoChannelEndEvent(channel_id, point_name);

        EventsControllerHelper.eventHappened(endevent);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param channelID
     */
    public static void channelSayEndEventToNative(String mission_name, String channelID) {
        JavaEvents.SendEvent(47, 3, new GetTwoStrings(mission_name, channelID));
    }

    /**
     * Method description
     *
     *
     * @param channelID
     */
    public static void channelCleanResources(String channelID) {
        JavaEvents.SendEvent(47, 8, new DataName(channelID));
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     */
    public static void cleanResourcesFade(String mission_name) {
        JavaEvents.SendEvent(47, 9, new DataName(mission_name));
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param element
     */
    public static void updateOrganiser(String mission_name, Scorgelement element) {
        JavaEvents.SendEvent(50, 0, new UpdateData(mission_name, element));
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param element
     */
    public static void updateOrganiserSligtly(String mission_name, Scorgelement element) {
        JavaEvents.SendEvent(50, 2, new UpdateData(mission_name, element));
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param element
     */
    public static void updateOrganiser(String mission_name, WarehouseOrder element) {
        JavaEvents.SendEvent(50, 1, new UpdateDataWarehouse(mission_name, element));
    }

    /**
     * Method description
     *
     *
     * @param point_name
     *
     * @return
     */
    public static EventGetPointLocInfo getLocalisationMissionPointInfo(String point_name) {
        EventGetPointLocInfo info = new EventGetPointLocInfo(point_name);

        if (point_name == null) {
            info.long_name = "";
            info.short_name = "";

            return info;
        }

        JavaEvents.SendEvent(56, 0, info);
        info.long_name = loc.getMissionPointLongName(info.long_name);
        info.short_name = loc.getMissionPointShortName(info.short_name);

        return info;
    }

    /**
     * Method description
     *
     *
     * @param point_name
     *
     * @return
     */
    public static EventGetPointLocInfo getLocalisationMissionPointInfoLocRefs(String point_name) {
        EventGetPointLocInfo info = new EventGetPointLocInfo(point_name);

        if (point_name == null) {
            info.long_name = "";
            info.short_name = "";

            return info;
        }

        JavaEvents.SendEvent(56, 0, info);

        return info;
    }

    /**
     * Method description
     *
     *
     * @param person_name
     *
     * @return
     */
    public static String getLocalisationNPCInfo(String person_name) {
        GetNpcLoc info = new GetNpcLoc(person_name);

        JavaEvents.SendEvent(57, 0, info);

        return info.person_loc;
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param channel_uid
     */
    public static void RegisterActivationPoint(String mission_name, String channel_uid) {
        GetTwoStrings info = new GetTwoStrings(mission_name, channel_uid);

        JavaEvents.SendEvent(47, 4, info);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param uid
     */
    public static void RegisterSuccesMissionChannelAsBounding(String mission_name, String uid) {
        GetTwoStrings info = new GetTwoStrings(mission_name, uid);

        JavaEvents.SendEvent(47, 7, info);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param point_name
     *
     * @return
     */
    public static boolean isChannelBlockedByPackage(String mission_name, String point_name) {
        GetBooleanOnTwoStrings info = new GetBooleanOnTwoStrings(point_name, mission_name);

        JavaEvents.SendEvent(47, 10, info);

        return info.value;
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param point_name
     *
     * @return
     */
    public static boolean isChannelBlockedByPassanger(String mission_name, String point_name) {
        GetBooleanOnTwoStrings info = new GetBooleanOnTwoStrings(point_name, mission_name);

        JavaEvents.SendEvent(47, 11, info);

        return info.value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean isPassanerSlotBuzzy() {
        GetBool info = new GetBool();

        JavaEvents.SendEvent(58, 0, info);

        return info.value;
    }

    /**
     * Method description
     *
     *
     * @param mission
     *
     * @return
     */
    public static boolean freeSlotForMission(String mission) {
        GetBooleanOnTwoStrings info = new GetBooleanOnTwoStrings(mission, "");

        JavaEvents.SendEvent(87, 0, info);

        return info.value;
    }

    /**
     * Method description
     *
     *
     * @param mission
     *
     * @return
     */
    public static boolean samePassanger(String mission) {
        GetBoolNamed info = new GetBoolNamed(mission, true);

        JavaEvents.SendEvent(85, 0, info);

        return info.value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean isWide() {
        GetBool info = new GetBool();

        JavaEvents.SendEvent(88, 0, info);

        return info.value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean isPackageSlotBuzzy() {
        GetBool info = new GetBool();

        JavaEvents.SendEvent(58, 1, info);

        return info.value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean isTrailerSlotBuzzy() {
        GetBool info = new GetBool();

        JavaEvents.SendEvent(58, 2, info);

        return info.value;
    }

    /**
     * Method description
     *
     *
     * @param barname
     *
     * @return
     */
    public static BarMenu.BarPack[] querryBarSlots(String barname) {
        GetBarSlotsInformation info = new GetBarSlotsInformation(barname);

        JavaEvents.SendEvent(59, 0, info);

        BarMenu.BarPack[] res = new BarMenu.BarPack[info.data.size()];

        for (int i = 0; i < info.data.size(); ++i) {
            BarMenu.BarPack pack = new BarMenu.BarPack();
            SingleBarSlotsInformation single_info = (SingleBarSlotsInformation) info.data.get(i);

            pack.type = ((single_info.is_pack)
                         ? 1
                         : 3);
            pack.mission_name = single_info.mission_name;
            res[i] = pack;
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param bar_name
     * @param mission_name
     */
    public static void pickupQuestItem(String bar_name, String mission_name) {
        GetTwoStrings info = new GetTwoStrings(bar_name, mission_name);

        JavaEvents.SendEvent(59, 1, info);
    }

    /**
     * Method description
     *
     *
     * @param resource
     */
    public static void makeRadioBreakNews(String resource) {
        DataName data = new DataName(resource);

        JavaEvents.SendEvent(47, 6, data);
    }

    /**
     * Method description
     *
     *
     * @param ch_name
     */
    public static void channalIsActive(String ch_name) {
        DataName data = new DataName(ch_name);

        JavaEvents.SendEvent(83, 0, data);
    }

    /**
     * Method description
     *
     *
     * @param mission
     */
    public static void missionOnMoney(String mission) {
        DataName data = new DataName(mission);

        JavaEvents.SendEvent(84, 0, data);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean isFlag() {
        return flag;
    }

    /**
     * Method description
     *
     *
     * @param flag
     */
    public static void setFlag(boolean flag) {
        flag = flag;
    }

    /**
     * Method description
     *
     *
     * @param checker
     */
    public static void makeMissionFinishChecker(Object checker) {
        if (eng.noNative) {
            setFlag(true);
        } else {
            JavaEvents.SendEvent(86, 0, checker);
        }
    }

    static class Data {
        CoreTime time_on_mission = null;
        boolean move_time = false;
        boolean needFinishIcon = true;
        String mission_name;
        String channel_uid;
        String p1;
        String p2;
        String p3;
        int race_uid;
        Scorgelement element;

        Data() {}

        Data(String mission_name) {
            this.mission_name = mission_name;
        }

        Data(String mission_name, String channel_uid, String p1, String p2, String p3, Scorgelement element,
             int race_id) {
            this.mission_name = mission_name;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.element = element;
            this.channel_uid = channel_uid;
            this.race_uid = race_id;
        }

        Data(String mission_name, String channel_uid, String p1, String p2, String p3, Scorgelement element,
             int race_id, CoreTime time_on_mission, boolean move_time, boolean needFinishIcon) {
            this.mission_name = mission_name;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.element = element;
            this.channel_uid = channel_uid;
            this.race_uid = race_id;
            this.time_on_mission = time_on_mission;
            this.move_time = move_time;
            this.needFinishIcon = needFinishIcon;
        }
    }


    static class DataName {
        String name;

        DataName(String name) {
            this.name = name;
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static class DeclineMissionListener implements IDeclineOrgListener {
        private final String mission_name;

        /**
         * Constructs ...
         *
         *
         * @param mission_name
         */
        public DeclineMissionListener(String mission_name) {
            this.mission_name = mission_name;
        }

        /**
         * Method description
         *
         */
        @Override
        public void declined() {
            MissionEventsMaker.declineMission(this.mission_name);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public String getMission_name() {
            return this.mission_name;
        }
    }


    static class GetBarSlotsInformation {
        Vector data = new Vector();
        String name;

        GetBarSlotsInformation(String name) {
            this.name = name;
        }
    }


    static class GetBool {
        boolean value;
    }


    static class GetBoolNamed {
        boolean value;
        String name;

        GetBoolNamed(String name, boolean value) {
            this.name = name;
            this.value = value;
        }
    }


    static class GetBooleanOnTwoStrings {
        String name;
        String name1;
        boolean value;

        GetBooleanOnTwoStrings(String name, String name1) {
            this.name = name;
            this.name1 = name1;
        }
    }


    static class GetMissionChannelResourcesInfo {
        Vector data = new Vector();
        String affinity;
        String name;
        int priority;
        boolean important;
        boolean finish;
        boolean succes;
        boolean is_main;

        GetMissionChannelResourcesInfo(String affinity, String name, int priority, boolean important, boolean finish,
                                       boolean succes, boolean is_main) {
            this.name = name;
            this.important = important;
            this.finish = finish;
            this.priority = priority;
            this.succes = succes;
            this.is_main = is_main;
            this.affinity = affinity;
        }
    }


    static class GetNpcLoc {
        String person;
        String person_loc;

        GetNpcLoc(String person) {
            this.person = person;
        }
    }


    static class GetThreeStrings {
        String name;
        String name1;
        String name2;

        GetThreeStrings(String name, String name1, String name2) {
            this.name = name;
            this.name1 = name1;
            this.name2 = name2;
        }
    }


    static class GetTwoStrings {
        String name;
        String name1;

        GetTwoStrings(String name, String name1) {
            this.name = name;
            this.name1 = name1;
        }
    }


    static class QuestItemData {
        String mission_name;
        String merchandise_name;
        String point_name;
        String uid;
        int color;
        boolean make_place;

        QuestItemData() {}

        QuestItemData(String mission_name, String merchandise_name, String point_name, String uid) {
            this.mission_name = mission_name;
            this.merchandise_name = merchandise_name;
            this.point_name = point_name;
            this.uid = uid;
            this.make_place = true;
        }

        QuestItemData(String mission_name, String merchandise_name, String point_name, String uid, boolean make_place) {
            this.mission_name = mission_name;
            this.merchandise_name = merchandise_name;
            this.point_name = point_name;
            this.uid = uid;
            this.make_place = make_place;
        }
    }


    static class QueueDialogResource {
        SCRuniperson person;
        String mission_name;
        String channel_uid;

        QueueDialogResource() {}

        QueueDialogResource(String mission_name, String channeluid) {
            this.mission_name = mission_name;
            this.channel_uid = channeluid;
        }
    }


    static class SingleBarSlotsInformation {
        boolean is_pack;
        boolean is_pass;
        String mission_name;
    }


    static class StartMissionData {
        CoreTime time_on_mission = null;
        boolean move_time = false;
        boolean needFinishIcon = true;
        String mission_name;
        boolean channel_immediate;
        String p1;
        String p2;
        String p3;
        int race_uid;
        Scorgelement element;

        StartMissionData() {}

        StartMissionData(String mission_name, boolean isChannelImmediate, String p1, String p2, String p3,
                         Scorgelement element, int race_id) {
            this.mission_name = mission_name;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.element = element;
            this.channel_immediate = isChannelImmediate;
            this.race_uid = race_id;
        }

        StartMissionData(String mission_name, boolean isChannelImmediate, String p1, String p2, String p3,
                         Scorgelement element, int race_id, CoreTime time_on_mission, boolean move_time,
                         boolean needFinishIcon) {
            this.mission_name = mission_name;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.element = element;
            this.channel_immediate = isChannelImmediate;
            this.race_uid = race_id;
            this.time_on_mission = time_on_mission;
            this.move_time = move_time;
            this.needFinishIcon = needFinishIcon;
        }
    }


    static class UpdateData {
        String mission_name = null;
        Scorgelement element = null;

        UpdateData(String mission_name, Scorgelement element) {
            this.mission_name = mission_name;
            this.element = element;
        }
    }


    static class UpdateDataWarehouse {
        String mission_name = null;
        WarehouseOrder element = null;

        UpdateDataWarehouse(String mission_name, WarehouseOrder element) {
            this.mission_name = mission_name;
            this.element = element;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
