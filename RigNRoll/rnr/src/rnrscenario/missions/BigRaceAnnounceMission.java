/*
 * @(#)BigRaceAnnounceMission.java   13/08/28
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

import rnr.src.menuscript.Converts;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.MacroBuilder;
import rnr.src.rnrcore.Macros;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.loc;
import rnr.src.rnrorg.EmptyCustomer;
import rnr.src.rnrorg.IStoreorgelement;
import rnr.src.rnrorg.IStoreorgelement.Type;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrorg.MissionTime;
import rnr.src.rnrorg.Organizers;
import rnr.src.rnrorg.RewardForfeit;
import rnr.src.rnrorg.Scorgelement;
import rnr.src.rnrorg.XmlInit;
import rnr.src.rnrorg.journable;
import rnr.src.rnrorg.journalelement;
import rnr.src.rnrscenario.missions.BigRaceAnnounceMission.Params;
import rnr.src.rnrscenario.missions.infochannels.InformationChannelData;
import rnr.src.rnrscr.smi.BigRaceAnnounceCreator;
import rnr.src.scriptActions.ExternalChannelSayAppear;
import rnr.src.scriptActions.JournalAction;
import rnr.src.scriptActions.JournalActiveAction;
import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptActions.StartMissionAction;
import rnr.src.scriptEvents.AfterEventsRun;
import rnr.src.scriptEvents.IAfterEventsRun;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class BigRaceAnnounceMission implements Serializable {
    static final long serialVersionUID = 0L;
    private static final String[] ANNOUNCED_JOURNAL = { "FAKE", "BIGRACE ANNOUNCED 1", "BIGRACE ANNOUNCED 2",
            "BIGRACE ANNOUNCED 3", "BIGRACE ANNOUNCED 4" };
    private static int count_announsments = 0;

    /** Field description */
    public static final String APPEAR_CHANNEL_JOURNAL_NOTE = "";

    /** Field description */
    public static final String DESCRIPTION_LOC = "";
    private static BigRaceAnnounceMission instance = null;
    private final HashMap<Integer, Params> missions;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings("unchecked")
    public BigRaceAnnounceMission() {
        this.missions = new HashMap<Integer, Params>();
    }

    private static BigRaceAnnounceMission getInstance() {
        if (null == instance) {
            instance = new BigRaceAnnounceMission();
        }

        return instance;
    }

    private static String getDescriptionString(String shortracename) {
        return loc.getBigraceShortName(shortracename);
    }

    private static String createResourceRefForMissionStartChannel(String mission_name) {
        return mission_name + " startchannel";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static JournalNoteData getAppearStartChannleJournalNote(int type, String shortracename, CoreTime startTime,
            String source, String destination, int limitrating) {
        List macroces = new ArrayList();

        macroces.add(new Macros("SOURCE", MacroBuilder.makeSimpleMacroBody("CITYNAME", source)));
        macroces.add(new Macros("DESTINATION", MacroBuilder.makeSimpleMacroBody("CITYNAME", destination)));
        macroces.add(new Macros("SHORTRACENAME", MacroBuilder.makeSimpleMacroBody("BIGRACE_SHORTNAME", shortracename)));
        macroces.add(new Macros("RATING", MacroBuilder.makeSimpleMacroBody("" + limitrating)));
        macroces.addAll(Converts.ConvertDateMacroAbsolute(startTime.gMonth(), startTime.gDate(), startTime.gYear(),
                startTime.gHour(), startTime.gMinute()));

        JournalNoteData result = new JournalNoteData(ANNOUNCED_JOURNAL[type], macroces);

        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void activateMission(Params params, boolean isImmediate) {
        MissionInfo mission = createMission(params, isImmediate);

        mission.setSelfPlacedMission(true);

        MissionPlacement mission_placement = new MissionPlacement(mission, new ArrayList());

        MissionSystemInitializer.getMissionsManager().placeMissionToWorld(mission_placement);
        params.created_mission = mission;
    }

    /**
     * Method description
     *
     *
     * @param immediate_mission
     * @param finish_point
     * @param race_uid
     * @param type
     * @param raceName
     * @param shortRaceName
     * @param startWarehouse
     * @param finishWarehouse
     * @param moneyPrize
     * @param ratingThreshold
     * @param yearstart
     * @param monthstart
     * @param daystart
     * @param hourstart
     * @param minstart
     * @param yearfinish
     * @param monthfinish
     * @param dayfinish
     * @param hourfinish
     * @param minfinish
     * @param yearArticleLife
     * @param monthArticleLife
     * @param dayArticleLife
     * @param hourArticleLife
     */
    public static void announceBigRace(boolean immediate_mission, String finish_point, int race_uid, int type,
                                       String raceName, String shortRaceName, String startWarehouse,
                                       String finishWarehouse, int moneyPrize, int ratingThreshold, int yearstart,
                                       int monthstart, int daystart, int hourstart, int minstart, int yearfinish,
                                       int monthfinish, int dayfinish, int hourfinish, int minfinish,
                                       int yearArticleLife, int monthArticleLife, int dayArticleLife,
                                       int hourArticleLife) {
        Params params = new Params(finish_point, race_uid, type, raceName, shortRaceName, startWarehouse,
                                   finishWarehouse, moneyPrize, ratingThreshold, yearstart, monthstart, daystart,
                                   hourstart, minstart, yearfinish, monthfinish, dayfinish, hourfinish, minfinish,
                                   yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);

        getInstance().missions.put(Integer.valueOf(race_uid), params);
        activateMission(params, immediate_mission);

        if (immediate_mission) {
            AfterEventsRun.getInstance().addListener(new ImmediateMissionAnnounce(params.created_mission.getName()));
        }
    }

    /**
     * Method description
     *
     *
     * @param race_uid
     */
    public static void clearAnnounced(int race_uid) {
        if (getInstance().missions.containsKey(Integer.valueOf(race_uid))) {
            Params params = getInstance().missions.remove(Integer.valueOf(race_uid));

            params.created_mission.setSelfPlacedMission(false);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static MissionInfo createMission(Params params, boolean isImmediate) {
        String mission_name = "BigRaceAnnounce" + params.race_uid + "nom" + (count_announsments++);
        String organizerName = mission_name + " org";
        String resource_ref = createResourceRefForMissionStartChannel(mission_name);
        String finishPointName = params.finish_point;
        MissionCreationContext context = new MissionCreationContext(mission_name);
        QIVisit qi = new QIVisit();

        qi.model = "VISIT";

        IStoreorgelement orgelement = null;
        IStoreorgelement.Type tip = IStoreorgelement.Type.notype;

        switch (params.type) {
         case 1 :
             tip = IStoreorgelement.Type.bigrace1_announce;

             break;

         case 2 :
             tip = IStoreorgelement.Type.bigrace2_announce;

             break;

         case 3 :
             tip = IStoreorgelement.Type.bigrace3_announce;

             break;

         case 4 :
             tip = IStoreorgelement.Type.bigrace4_announce;
        }

        Scorgelement orgElement = new Scorgelement(organizerName, tip, false, 8, new RewardForfeit(0.0D, 0.0D, 0.0D),
                                      0, new RewardForfeit(0.0D, 0.0D, 0.0D),
                                      getDescriptionString(params.shortRaceName), new EmptyCustomer(),
                                      new MissionTime(), new MissionTime(), null, null,
                                      new journable[XmlInit.TAG_FAIL.length]);

        orgelement = orgElement;
        Organizers.getInstance().addSpecial(organizerName, orgelement);

        List start_channels = new ArrayList();

        if (!(isImmediate)) {
            List appear_actions = new ArrayList();
            JournalNoteData journalData = getAppearStartChannleJournalNote(params.type, params.shortRaceName,
                                              new CoreTime(params.yearstart, params.monthstart, params.daystart,
                                                  params.hourstart, params.minstart), params.startWarehouse,
                                                      params.finishWarehouse, params.ratingThreshold);

            appear_actions.add(new JournalActiveAction(new journalelement(journalData.getDescriptionLocRef(),
                    journalData.getMacrosPairs())));

            List accept_actions = new ArrayList();
            StartMissionAction act = new StartMissionAction();

            act.name = mission_name;
            act.setActivatedBy(mission_name);
            accept_actions.add(act);

            List decline_actions = new ArrayList();
            Object dataForChannelConstruct = new BigRaceAnnounceCreator(params.race_uid, params.type, params.raceName,
                                                 params.shortRaceName, params.startWarehouse, params.finishWarehouse,
                                                 params.moneyPrize, params.ratingThreshold, params.yearstart,
                                                 params.monthstart, params.daystart, params.hourstart, params.minstart,
                                                 params.yearfinish, params.monthfinish, params.dayfinish,
                                                 params.hourfinish, params.minfinish, params.yearArticleLife,
                                                 params.monthArticleLife, params.dayArticleLife,
                                                 params.hourArticleLife);
            InformationChannelData channel = new InformationChannelData("ArticleChannel", resource_ref,
                                                 dataForChannelConstruct, appear_actions, accept_actions,
                                                 decline_actions, context);

            start_channels.add(channel);
        } else {
            List appear_actions = new ArrayList();
            JournalNoteData journalData = getAppearStartChannleJournalNote(params.type, params.shortRaceName,
                                              new CoreTime(params.yearstart, params.monthstart, params.daystart,
                                                  params.hourstart, params.minstart), params.startWarehouse,
                                                      params.finishWarehouse, params.ratingThreshold);

            appear_actions.add(new JournalAction(new journalelement(journalData.getDescriptionLocRef(),
                    journalData.getMacrosPairs())));

            StartMissionAction act = new StartMissionAction();

            act.name = mission_name;
            act.setActivatedBy(mission_name);
            appear_actions.add(act);

            List accept_actions = new ArrayList();
            List decline_actions = new ArrayList();
            Object dataForChannelConstruct = new BigRaceAnnounceCreator(params.race_uid, params.type, params.raceName,
                                                 params.shortRaceName, params.startWarehouse, params.finishWarehouse,
                                                 params.moneyPrize, params.ratingThreshold, params.yearstart,
                                                 params.monthstart, params.daystart, params.hourstart, params.minstart,
                                                 params.yearfinish, params.monthfinish, params.dayfinish,
                                                 params.hourfinish, params.minfinish, params.yearArticleLife,
                                                 params.monthArticleLife, params.dayArticleLife,
                                                 params.hourArticleLife);
            InformationChannelData channel = new InformationChannelData("ExternalChannel", resource_ref,
                                                 dataForChannelConstruct, appear_actions, accept_actions,
                                                 decline_actions, context);

            start_channels.add(channel);
        }

        List start_actions = new ArrayList();
        List any_end_actions = new ArrayList();

        any_end_actions.add(new CycleActivateMission(params.race_uid));

        MissionInfo res = new MissionInfo(mission_name, finishPointName, organizerName, qi, start_channels,
                                          start_actions, any_end_actions, any_end_actions, any_end_actions,
                                          any_end_actions, any_end_actions);

        res.setStarter(params);

        return res;
    }

    static class CycleActivateMission extends ScriptAction {
        static final long serialVersionUID = 0L;
        private final int uid;

        CycleActivateMission(int uid) {
            this.uid = uid;
        }

        void run() {
            BigRaceAnnounceMission.access$200(
                (BigRaceAnnounceMission.Params) BigRaceAnnounceMission.access$000().missions.get(
                    Integer.valueOf(this.uid)), false);
        }

        /**
         * Method description
         *
         */
        @Override
        public void act() {
            if (BigRaceAnnounceMission.access$000().missions.containsKey(Integer.valueOf(this.uid))) {
                eng.CreateInfinitScriptAnimation(new BigRaceAnnounceMission.CycleActivateMissionRune(this));
            }
        }
    }


    static class CycleActivateMissionRune extends TypicalAnm {
        private final BigRaceAnnounceMission.CycleActivateMission cycle;

        CycleActivateMissionRune(BigRaceAnnounceMission.CycleActivateMission cycle) {
            this.cycle = cycle;
        }

        /**
         * Method description
         *
         *
         * @param dt
         *
         * @return
         */
        @Override
        public boolean animaterun(double dt) {
            this.cycle.run();

            return true;
        }
    }


    static class ImmediateMissionAnnounce implements IAfterEventsRun {
        String mission_name;

        private ImmediateMissionAnnounce(String mission_name) {
            this.mission_name = mission_name;
        }

        /**
         * Method description
         *
         */
        @Override
        public void run() {
            ExternalChannelSayAppear appear_action = new ExternalChannelSayAppear();

            appear_action.name = BigRaceAnnounceMission.access$300(this.mission_name);
            appear_action.act();
        }
    }


    static class JournalNoteData {
        @SuppressWarnings("rawtypes")
        private final List<Macros> macrosPairs = new ArrayList();
        private final String descriptionLocRef;

        JournalNoteData(String descriptionLocRef, List<Macros> macrosPairs) {
            this.descriptionLocRef = descriptionLocRef;
            this.macrosPairs.addAll(macrosPairs);
        }

        String getDescriptionLocRef() {
            return this.descriptionLocRef;
        }

        List<Macros> getMacrosPairs() {
            return this.macrosPairs;
        }
    }


    static class Params implements IMissionStarter {
        MissionInfo created_mission = null;
        String finish_point;
        int race_uid;
        int type;
        String raceName;
        String shortRaceName;
        String startWarehouse;
        String finishWarehouse;
        int moneyPrize;
        int ratingThreshold;
        int yearstart;
        int monthstart;
        int daystart;
        int hourstart;
        int minstart;
        int yearfinish;
        int monthfinish;
        int dayfinish;
        int hourfinish;
        int minfinish;
        int yearArticleLife;
        int monthArticleLife;
        int dayArticleLife;
        int hourArticleLife;

        /**
         * Constructs ...
         *
         *
         * @param finish_point
         * @param race_uid
         * @param type
         * @param raceName
         * @param shortRaceName
         * @param startWarehouse
         * @param finishWarehouse
         * @param moneyPrize
         * @param ratingThreshold
         * @param yearstart
         * @param monthstart
         * @param daystart
         * @param hourstart
         * @param minstart
         * @param yearfinish
         * @param monthfinish
         * @param dayfinish
         * @param hourfinish
         * @param minfinish
         * @param yearArticleLife
         * @param monthArticleLife
         * @param dayArticleLife
         * @param hourArticleLife
         */
        public Params(String finish_point, int race_uid, int type, String raceName, String shortRaceName,
                      String startWarehouse, String finishWarehouse, int moneyPrize, int ratingThreshold,
                      int yearstart, int monthstart, int daystart, int hourstart, int minstart, int yearfinish,
                      int monthfinish, int dayfinish, int hourfinish, int minfinish, int yearArticleLife,
                      int monthArticleLife, int dayArticleLife, int hourArticleLife) {
            this.finish_point = finish_point;
            this.race_uid = race_uid;
            this.type = type;
            this.raceName = raceName;
            this.shortRaceName = shortRaceName;
            this.startWarehouse = startWarehouse;
            this.finishWarehouse = finishWarehouse;
            this.moneyPrize = moneyPrize;
            this.ratingThreshold = ratingThreshold;
            this.yearstart = yearstart;
            this.monthstart = monthstart;
            this.daystart = daystart;
            this.hourstart = hourstart;
            this.minstart = minstart;
            this.yearfinish = yearfinish;
            this.monthfinish = monthfinish;
            this.dayfinish = dayfinish;
            this.hourfinish = hourfinish;
            this.minfinish = minfinish;
            this.yearArticleLife = yearArticleLife;
            this.monthArticleLife = monthArticleLife;
            this.dayArticleLife = dayArticleLife;
            this.hourArticleLife = hourArticleLife;
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
         *
         * @return
         */
        @Override
        public String startMission(String mission_name, boolean isChannelImmeiate, String info_channel_point,
                                   String qi_point, String finish_point) {
            MissionEventsMaker.startMission_AnnounceBigRace(this.race_uid, mission_name, isChannelImmeiate,
                    info_channel_point, qi_point, finish_point);

            return null;
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
         * @param missionStartPlaceName
         */
        @Override
        public void startMission(String mission_name, boolean isChannelImmeiate, String info_channel_point,
                                 String qi_point, String finish_point, String missionStartPlaceName) {
            MissionEventsMaker.startMission_AnnounceBigRace(this.race_uid, mission_name, isChannelImmeiate,
                    info_channel_point, qi_point, finish_point);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
