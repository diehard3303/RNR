/*
 * @(#)MissionManager.java   13/08/28
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

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import rnr.src.menuscript.BarMenu;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrorg.ScenarioMissionItem;
import rnr.src.rnrorg.ScenarioMissions;
import rnr.src.rnrorg.journal;
import rnr.src.rnrorg.organaiser;
import rnr.src.rnrscenario.ScenarioFlagsManager;
import rnr.src.rnrscenario.missions.infochannels.InformationChannelData;
import rnr.src.rnrscenario.missions.map.AbstractMissionsMap;
import rnr.src.rnrscenario.missions.map.PointsController;
import rnr.src.rnrscenario.missions.requirements.MissionAppearConditions;
import rnr.src.rnrscenario.missions.requirements.MissionsLog;
import rnr.src.rnrscenario.missions.requirements.Requirement;
import rnr.src.rnrscenario.missions.requirements.RequirementsCreationException;
import rnr.src.rnrscenario.missions.requirements.RequirementsFactory;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscenario.sctask;
import rnr.src.rnrscr.parkingplace;
import rnr.src.scenarioUtils.Pair;
import rnr.src.scenarioXml.XmlDocument;
import rnr.src.scenarioXml.XmlFilter;
import rnr.src.scenarioXml.XmlNodeDataProcessor;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public final class MissionManager extends sctask {
    private static final int MISSIONS_CACHE_CAPACITY = 300;
    private static final int DEFAULT_ERROR_MESSAGE_CAPACITY = 128;
    private static final int RUN_FREQUENCY = 3;
    private static final String INFO_LOADING_ERROR = "failed to extract mission info from xml node: ";
    private static final int BASE_MISSION_PRIORITY = 10;
    private static boolean isMissionsActive;
    private static boolean debugModeOn;
    private static boolean agressiveModeOn;
    private static boolean result_readFile_loadMission;

    static {
        isMissionsActive = true;
        debugModeOn = true;
        agressiveModeOn = false;
        result_readFile_loadMission = false;
    }

    private final MissionAppearConditions requirementsForMissionActivation = new MissionAppearConditions();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final List<MissionsController> missionsWaiters = new LinkedList();
    private PointsController pointsController = PointsController.getInstance();
    private final PriorityTable missionsPriorities = new PriorityTable();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final List<MissionPlacement> activatedMissions = new LinkedList();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final List<MissionPlacement> finishedMissions = new LinkedList();
    @SuppressWarnings("unchecked")
    private final HashMap<String, HashMap<String, MissionPlacement>> activatedDependentMissions = new HashMap();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final Map<String, MissionPlacement> missionsPool = new LinkedHashMap(300);
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final Map<String, MissionPlacement> dependentMissionsPool = new LinkedHashMap(300);
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final Map<String, MissionPlacement> asideMissionsPool = new LinkedHashMap(300);
    private boolean initialized = false;
    private boolean loading = false;
    private boolean msEnable = true;
    private boolean mdebug = false;

    MissionManager(AbstractMissionsMap map) {
        super(3, false);
        assert(null != map) : "map must be non-null reference";
        super.start();
        RequirementsFactory.setPriorityTable(this.missionsPriorities);
    }

    /**
     * Method description
     *
     *
     * @param mname
     *
     * @return
     */
    public MissionPlacement getMissionPlacement(String mname) {
        MissionPlacement mp = this.missionsPool.get(mname);

        if (mp == null) {
            mp = this.dependentMissionsPool.get(mname);

            if (null == mp) {
                for (MissionPlacement mp1 : this.activatedMissions) {
                    if (mname.compareTo(mp1.getInfo().getName()) == 0) {
                        mp = mp1;

                        break;
                    }
                }

                if (null == mp) {
                    for (MissionPlacement mp1 : this.finishedMissions) {
                        if (mname.compareTo(mp1.getInfo().getName()) == 0) {
                            mp = mp1;

                            break;
                        }
                    }

                    if (null == mp) {
                        mp = this.asideMissionsPool.get(mname);

                        if (mp == null) {
                            return null;
                        }
                    }
                }
            }
        }

        return mp;
    }

    /**
     * Method description
     *
     *
     * @param mname
     *
     * @return
     */
    public MissionInfo getMissionInfo(String mname) {
        MissionPlacement mp = getMissionPlacement(mname);

        if (null == mp) {
            return null;
        }

        return mp.getInfo();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean getMissionSystemEnable() {
        return this.msEnable;
    }

    /**
     * Method description
     *
     *
     * @param v
     */
    public void setMissionSystemEnable(boolean v) {
        this.msEnable = v;
    }

    /**
     * Method description
     *
     *
     * @param mission
     * @param point
     *
     * @return
     */
    public static int getParkingPlace(String mission, String point) {
        return MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission).getParkingFromLock(point);
    }

    /**
     * Method description
     *
     *
     * @param mission
     * @param point
     *
     * @return
     */
    public static parkingplace getParking(String mission, String point) {
        return MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission).getParking(point);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean getMSEnable() {
        return MissionSystemInitializer.getMissionsManager().getMissionSystemEnable();
    }

    /**
     * Method description
     *
     *
     * @param main
     * @param dep
     *
     * @return
     */
    public static boolean isDepended(String main, String dep) {
        MissionInfo info = MissionSystemInitializer.getMissionsManager().getMissionInfo(main);

        return ((info != null) && (info.getDependent().contains(dep)));
    }

    /**
     * Method description
     *
     *
     * @param v
     */
    public static void setMSEnable(boolean v) {
        MissionSystemInitializer.getMissionsManager().setMissionSystemEnable(v);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean clickMenu() {
        if (MissionSystemInitializer.getMissionsManager().mdebug) {
            return BarMenu.AnyStartDialogOrExit();
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param v
     */
    public static void setMDebug(boolean v) {
        MissionSystemInitializer.getMissionsManager().mdebug = v;
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void declineMissions() {
        List list = new LinkedList();

        for (MissionPlacement m : MissionSystemInitializer.getMissionsManager().activatedMissions) {
            if (m.getInfo().isScenario()) {
                list.add(m.getInfo().getName());
            }
        }

        organaiser.declineActiveMissions(list);
        DelayedResourceDisposer.getInstance().setAllToDispose();
        journal.getInstance().declineAll();
    }

    /**
     * Method description
     *
     *
     * @param fileList
     */
    public void initialize(Collection<String> fileList) {
        if (this.initialized) {
            return;
        }

        this.initialized = true;
        uploadMissions(fileList);
    }

    /**
     * Method description
     *
     */
    public void deinitialize() {
        if (!(this.initialized)) {
            return;
        }

        this.initialized = false;
        StartMissionListeners.deinit();
        MissionsLog.deinit();
        PointsController.deinitialize();
        this.pointsController = PointsController.getInstance();
        RequirementsFactory.deinit();
        this.missionsWaiters.clear();
        this.activatedMissions.clear();
        this.finishedMissions.clear();
        this.activatedDependentMissions.clear();
        this.missionsPool.clear();
        this.dependentMissionsPool.clear();
        this.asideMissionsPool.clear();
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setActive(boolean value) {
        isMissionsActive = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setDebugMode(boolean value) {
        debugModeOn = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setAgressiveMode(boolean value) {
        agressiveModeOn = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public PriorityTable getPriorityTable() {
        return this.missionsPriorities;
    }

    void addMissionController(MissionsController controller) {
        assert(null != controller) : "controller must be valid non-null reference";
        this.missionsWaiters.add(controller);
    }

    private boolean needDeactivateMisssion(MissionsLog.MissionState state) {
        if ((null == state) || (state.missionFinished())) {
            return true;
        }

        return ((!(state.getOccuredEvents().contains(MissionsLog.Event.MISSION_ACCEPTED)))
                && (!(state.getOccuredEvents().contains(MissionsLog.Event.PLAYER_DECLINED_MISSION))));
    }

    private boolean isMissionActual(String missionName) {
        MissionsLog.MissionState state = MissionsLog.getInstance().getMissionState(missionName);

        if ((null != state) && (state.missionFinished())) {
            return DelayedResourceDisposer.getInstance().hasChannelsForMission(missionName);
        }

        if ((null != state) && (state.getOccuredEvents().contains(MissionsLog.Event.MISSION_ACCEPTED))) {
            return true;
        }

        return this.requirementsForMissionActivation.missionAvalible(missionName);
    }

    @SuppressWarnings("rawtypes")
    private void unloadMissionUtility(String missionName, MissionPlacement placement, boolean isActiveMission,
                                      boolean placeBackInPool, boolean unloaddep) {
        unloadMission(missionName);

        if (placeBackInPool) {
            if (isActiveMission) {
                this.missionsPool.put(missionName, placement);
            } else {
                this.dependentMissionsPool.put(missionName, placement);
            }
        } else {
            this.finishedMissions.add(placement);
        }

        if ((this.activatedDependentMissions.containsKey(missionName)) && (unloaddep)) {
            HashMap<String, MissionPlacement> dependantMissions = this.activatedDependentMissions.get(missionName);

            if (null == dependantMissions) {
                return;
            }

            Set<Entry<String, MissionPlacement>> keys = dependantMissions.entrySet();

            for (Map.Entry mission : keys) {
                unloadMissionUtility((String) mission.getKey(), (MissionPlacement) mission.getValue(), false,
                                     placeBackInPool, unloaddep);
            }

            this.activatedDependentMissions.remove(missionName);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void deactivateUnactualMissions() {
        for (Iterator loadedMissionNameIter = this.activatedMissions.iterator(); loadedMissionNameIter.hasNext(); ) {
            MissionPlacement missionAndPoints = (MissionPlacement) loadedMissionNameIter.next();
            MissionInfo mission = missionAndPoints.getInfo();
            String missionName = mission.getName();

            if (isMissionActual(missionName)) {
                continue;
            }

            MissionsLog.MissionState state = MissionsLog.getInstance().getMissionState(missionName);

            if ((null == state) && (agressiveModeOn)) {
                continue;
            }

            for (InformationChannelData channelData : missionAndPoints.getInfo().getAllChannels()) {
                this.pointsController.freePoints(channelData.getPlacesNames(), missionAndPoints);
            }

            String s = missionAndPoints.getInfo().getQuestItemPlacement();

            if (null != s) {
                ArrayList pNames = new ArrayList();

                pNames.add(s);
                this.pointsController.freePoints(pNames, missionAndPoints);
            }

            boolean check = null == state;

            check |= ((state != null) && (state.missionFinished()));
            check &= !(mission.isScenarioMission());
            check &= !(mission.isSelfPlacedMission());

            if ((check) && (needDeactivateMisssion(state))) {
                MissionsLogger.getInstance().doLog("Deactivated mission: " + missionName, Level.INFO);

                if (null != state) {
                    boolean placeBackInPool = (!(mission.isUnique())) && (!(state.missionFinished()));

                    unloadMissionUtility(missionName, missionAndPoints, true, placeBackInPool,
                                         !(state.missionFinished()));
                }

                loadedMissionNameIter.remove();
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean hasMissionFreePoints(MissionPlacement missionPlacement) {
        MissionInfo info = missionPlacement.getInfo();

        assert(null != info) : "info must be non-null reference";

        for (InformationChannelData channelData : info.getAllChannels()) {
            if (!(this.pointsController.hasGroupFreePoint(channelData.getPlacesNames(), missionPlacement))) {
                return false;
            }
        }

        String s = info.getQuestItemPlacement();

        if (null != s) {
            ArrayList pNames = new ArrayList();

            pNames.add(s);

            if (!(this.pointsController.hasGroupFreePoint(pNames, missionPlacement))) {
                return false;
            }
        }

        return true;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<MissionDepentantLoadElement> createDependableMissionsLoadInformation(String parentName,
            MissionPlacement mi) {
        List missionChainElements = new LinkedList();

        missionChainElements.add(new MissionDepentantLoadElement(parentName, mi));

        Queue breadthFirstSearchQueue = new LinkedList();

        breadthFirstSearchQueue.addAll(mi.getInfo().getDependent());

        while (!(breadthFirstSearchQueue.isEmpty())) {
            String dependentMissionToUpload = (String) breadthFirstSearchQueue.poll();
            MissionPlacement placement = this.dependentMissionsPool.get(dependentMissionToUpload);

            if (null != placement) {
                missionChainElements.addAll(createDependableMissionsLoadInformation(mi.getInfo().getName(), placement));
            }
        }

        return missionChainElements;
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void run() {
        if ((debugModeOn) || (!(isMissionsActive))) {
            return;
        }

        deactivateUnactualMissions();

        if (scenarioscript.isScenarioOn()) {
            this.msEnable = ScenarioFlagsManager.getInstance().getFlagValue("MissionsEnebledByScenario");

            if ((!(this.msEnable)) && (!(MissionEventsMaker.isWide()))) {
                declineMissions();

                return;
            }
        }

        Set<String> missionNames = this.missionsPool.keySet();
        String[] arrayMissionNames = missionNames.toArray(new String[missionNames.size()]);

        for (String iterMissionName : arrayMissionNames) {
            if (!(this.missionsPool.containsKey(iterMissionName))) {
                continue;
            }

            MissionPlacement missionPlacement = this.missionsPool.get(iterMissionName);
            MissionInfo missionData = missionPlacement.getInfo();
            String missionName = missionData.getName();

            if (!(this.requirementsForMissionActivation.missionAvalible(missionName))) {
                continue;
            }

            if (!(hasMissionFreePoints(missionPlacement))) {
                continue;
            }

            List<MissionDepentantLoadElement> missionChainElements = new LinkedList();
            Queue breadthFirstSearchQueue = new LinkedList();

            breadthFirstSearchQueue.addAll(missionData.getDependent());

            while (!(breadthFirstSearchQueue.isEmpty())) {
                String dependentMissionToUpload = (String) breadthFirstSearchQueue.poll();
                MissionPlacement placement = this.dependentMissionsPool.get(dependentMissionToUpload);

                if (null != placement) {
                    missionChainElements.addAll(createDependableMissionsLoadInformation(missionName, placement));
                }
            }

            boolean cant_place_dependants = false;

            for (MissionDepentantLoadElement uploadCandidate : missionChainElements) {
                cant_place_dependants |= !(hasMissionFreePoints(uploadCandidate.placement));
            }

            if (cant_place_dependants) {
                continue;
            }

            placeMissionToWorld(missionPlacement, this.missionsPool);
            registerMissionPriority(missionName);

            for (MissionDepentantLoadElement toPlaceInWorld : missionChainElements) {
                placeDependantMissionToWorld(toPlaceInWorld.parentName, toPlaceInWorld.placement,
                                             this.dependentMissionsPool);
            }
        }
    }

    private void registerMissionPriority(String missionName) {
        int missionPriority = 10;
        Requirement missionRequirement = this.requirementsForMissionActivation.getRequirement(missionName);

        if (null != missionRequirement) {
            missionPriority += missionRequirement.getPriorityIncrement();
        }

        this.missionsPriorities.registerMissionPriority(missionName, missionPriority);
    }

    private void riseMissionPriority_MovedFromDependantToActive(String parentName, String missionName) {
        this.missionsPriorities.movedFromDependantToActive(parentName, missionName);
    }

    /**
     * Method description
     *
     *
     * @param missionPlacement
     */
    public void placeMissionToWorld(MissionPlacement missionPlacement) {
        placeMissionToWorld(missionPlacement, null);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void placeMissionToWorld(MissionPlacement missionPlacement, Map<String, MissionPlacement> pool) {
        assert(null != missionPlacement) : "missionPlacement must be non-null reference";
        uploadMission(missionPlacement.getInfo());

        if (null != pool) {
            pool.remove(missionPlacement.getInfo().getName());
        }

        this.activatedMissions.add(missionPlacement);
        MissionsLogger.getInstance().doLog("Activated mission: " + missionPlacement.getInfo().getName(), Level.INFO);

        for (InformationChannelData channelData : missionPlacement.getInfo().getAllChannels()) {
            this.pointsController.lockPoints(channelData.getPlacesNames(), missionPlacement);
        }

        String s = missionPlacement.getInfo().getQuestItemPlacement();

        if (null == s) {
            return;
        }

        ArrayList pNames = new ArrayList();

        pNames.add(s);
        this.pointsController.lockPoints(pNames, missionPlacement);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void placeDependantMissionToWorld(String parentMissionName, MissionPlacement missionPlacement,
            Map<String, MissionPlacement> pool) {
        uploadMission(missionPlacement.getInfo());
        pool.remove(missionPlacement.getInfo().getName());

        HashMap missionDepednences;

        if (this.activatedDependentMissions.containsKey(parentMissionName)) {
            missionDepednences = this.activatedDependentMissions.get(parentMissionName);
        } else {
            missionDepednences = new HashMap();
            this.activatedDependentMissions.put(parentMissionName, missionDepednences);
        }

        missionDepednences.put(missionPlacement.getInfo().getName(), missionPlacement);
        MissionsLogger.getInstance().doLog("Activated dependent mission: " + missionPlacement.getInfo().getName(),
                                           Level.INFO);

        for (InformationChannelData channelData : missionPlacement.getInfo().getAllChannels()) {
            this.pointsController.lockPoints(channelData.getPlacesNames(), missionPlacement);
        }

        String s = missionPlacement.getInfo().getQuestItemPlacement();

        if (null == s) {
            return;
        }

        ArrayList pNames = new ArrayList();

        pNames.add(s);
        this.pointsController.lockPoints(pNames, missionPlacement);
    }

    private void uploadMissions(Collection<String> fileList) {
        if (null == fileList) {
            return;
        }

        for (String fileName : fileList) {
            loadMissions(fileName);
        }
    }

    /**
     * Method description
     *
     *
     * @param _mission_name
     *
     * @return
     */
    public boolean activateAsideMission(String _mission_name) {
        return debugActivateMission(_mission_name, this.asideMissionsPool);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private boolean debugActivateMission(String _mission_name, Map<String, MissionPlacement> pool) {
        assert(null != pool) : "pool must be non-null reference";
        assert(null != _mission_name) : "_mission_name must be non-null reference";

        MissionPlacement info = pool.get(_mission_name);

        if (null != info) {
            List<MissionPlacement> missionChainElements = new LinkedList();
            Queue breadthFirstSearchQueue = new LinkedList();

            breadthFirstSearchQueue.addAll(info.getInfo().getDependent());

            while (!(breadthFirstSearchQueue.isEmpty())) {
                String dependentMissionToUpload = (String) breadthFirstSearchQueue.poll();
                MissionPlacement placement = this.dependentMissionsPool.get(dependentMissionToUpload);

                if (null != placement) {
                    missionChainElements.add(placement);
                    breadthFirstSearchQueue.addAll(placement.getInfo().getDependent());
                }
            }

            hasMissionFreePoints(info);
            placeMissionToWorld(info, this.missionsPool);

            for (MissionPlacement toPlaceInWorld : missionChainElements) {
                hasMissionFreePoints(toPlaceInWorld);
                placeMissionToWorld(toPlaceInWorld, this.dependentMissionsPool);
            }

            return true;
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param _mission_name
     *
     * @return
     */
    public boolean load_mission_debug(String _mission_name) {
        if (null != _mission_name) {
            boolean activatedFromMissionsPool = debugActivateMission(_mission_name, this.missionsPool);

            if (activatedFromMissionsPool) {
                return true;
            }

            boolean activatedFromDependantPool = debugActivateMission(_mission_name, this.dependentMissionsPool);

            if (activatedFromDependantPool) {
                return true;
            }

            boolean activatedFromAsideMissionsPool = activateAsideMission(_mission_name);

            if (activatedFromAsideMissionsPool) {
                return true;
            }

            ScenarioMissionItem scenarioMissionInfo = ScenarioMissions.getInstance().get(_mission_name);

            if (scenarioMissionInfo != null) {
                ScenarioMission.activateMissionLoad(scenarioMissionInfo.getMission_name(),
                        scenarioMissionInfo.getOrg_name(), scenarioMissionInfo.getPoint_name(), new CoreTime(),
                        scenarioMissionInfo.getMoveTime(), scenarioMissionInfo.getNeedFinishIcon());

                return true;
            }
        }

        return false;
    }

    private boolean loadMissions(String xmlFile) {
        if ((null == xmlFile) || (0 == xmlFile.length())) {
            return false;
        }

        result_readFile_loadMission = false;

        try {
            XmlDocument document = new XmlDocument(xmlFile, null);
            Document dom = document.getContent();
            XmlFilter missionsFilter = new XmlFilter(dom.getElementsByTagName("missions"));
            Node missionData = missionsFilter.nextElement();

            while (null != missionData) {
                new XmlFilter(missionData.getChildNodes()).visitAllNodes("mission", new XmlNodeDataProcessor() {
                    @Override
                    @SuppressWarnings({ "rawtypes", "unchecked" })
                    public void process(Node target, Object param) {
                        assert(null != target) : "target must be non-null reference";

                        PointListExtractor pointsList = new PointListExtractor(target);
                        String finishPointName;

                        if (1 > pointsList.getFinishPoints().size()) {
                            MissionsLogger.getInstance().doLog("invalid count of finish mission points detected",
                                                               Level.WARNING);
                            finishPointName = "unkonwn";
                        } else {
                            finishPointName = pointsList.getFinishPoints().iterator().next();
                        }

                        MissionInfo missionConstructionData = new MissionInfo(target, finishPointName);
                        MissionPlacement valueToStore = new MissionPlacement(missionConstructionData,
                                                            new LinkedList(pointsList.getCommonPoints()));

                        if ((!(missionConstructionData.hasStartDependence()))
                                && (!(missionConstructionData.isScenarioMission()))) {
                            MissionManager.this.missionsPool.put(missionConstructionData.getName(), valueToStore);

                            try {
                                Node requirementsNode = new XmlFilter(target.getChildNodes()).nodeNameNext("req");

                                if ((null != requirementsNode) && (0 < requirementsNode.getChildNodes().getLength())) {
                                    Requirement condition = RequirementsFactory.makeOrRequirement(requirementsNode);

                                    MissionManager.this.requirementsForMissionActivation.addRequirement(
                                        missionConstructionData.getName(), condition);
                                }
                            } catch (RequirementsCreationException e) {
                                MissionsLogger.getInstance().doLog("failed to extract mission info from xml node: "
                                                                   + e.getLocalizedMessage(), Level.SEVERE);
                                MissionsLogger.getInstance().doLog("failed to parse requirement node, mission name: "
                                                                   + missionConstructionData.getName(), Level.SEVERE);
                            }
                        } else if (!(missionConstructionData.isScenarioMission())) {
                            MissionManager.this.dependentMissionsPool.put(missionConstructionData.getName(),
                                    valueToStore);
                        } else {
                            MissionManager.this.asideMissionsPool.put(missionConstructionData.getName(), valueToStore);
                        }

                        return;
                    }
                }, null);
                missionData = missionsFilter.nextElement();
            }
        } catch (IOException e) {
            StringBuilder errorMessage = new StringBuilder(128);

            errorMessage.append("failed load missions from ");
            errorMessage.append(xmlFile);
            errorMessage.append(": ");
            errorMessage.append(e.getLocalizedMessage());
            MissionsLogger.getInstance().doLog(errorMessage.toString(), Level.SEVERE);
        }

        return result_readFile_loadMission;
    }

    private void uploadMission(MissionInfo info) {
        assert(null != info) : "info must be non-null reference";

        for (MissionsController controller : this.missionsWaiters) {
            controller.uploadMission(info);
        }
    }

    private void unloadMission(String name) {
        assert(null != name) : "name must be non-null reference";

        for (MissionsController controller : this.missionsWaiters) {
            controller.unloadMission(name);
        }
    }

    /**
     * Method description
     *
     *
     * @param parentName
     * @param missionName
     */
    public final void activateDependantMission(String parentName, String missionName) {
        if (0 == parentName.compareTo(missionName)) {
            return;
        }

        if (!(this.activatedDependentMissions.containsKey(parentName))) {
            return;
        }

        HashMap dependantMissions = this.activatedDependentMissions.get(parentName);

        if (null == dependantMissions) {
            return;
        }

        riseMissionPriority_MovedFromDependantToActive(parentName, missionName);
        this.activatedMissions.add((MissionPlacement) dependantMissions.get(missionName));
        dependantMissions.remove(missionName);
    }

    /**
     * Method description
     *
     *
     * @param parentName
     * @param missionName
     */
    public final void deactivateDependantMission(String parentName, String missionName) {
        if (this.activatedDependentMissions.containsKey(parentName)) {
            HashMap dependantMissions = this.activatedDependentMissions.get(parentName);

            if (null == dependantMissions) {
                return;
            }

            dependantMissions.remove(missionName);
            MissionsLogger.getInstance().doLog("Deactivated dependant mission permanently: " + missionName, Level.INFO);
            unloadMission(missionName);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<Pair<String, String>> getActivatedMissions() {
        List activedMissionsNames = new ArrayList();

        for (MissionPlacement info : this.activatedMissions) {
            activedMissionsNames.add(new Pair(info.getInfo().getName(), info.getInfo().getMissionStartPlaceName()));
        }

        return activedMissionsNames;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<String> getFinisheddMissions() {
        List finishedMissionsNames = new ArrayList();

        for (MissionPlacement info : this.finishedMissions) {
            finishedMissionsNames.add(info.getInfo().getName());
        }

        return finishedMissionsNames;
    }

    /**
     * Method description
     *
     *
     * @param missionsNames
     */
    public void setActivatedMissions(List<Pair<String, String>> missionsNames) {
        this.loading = true;

        for (Pair pairMissionNamePlace : missionsNames) {
            String missionName = (String) pairMissionNamePlace.getFirst();

            if (!(load_mission_debug(missionName))) {
                MissionsLogger.getInstance().doLog("Failed to load mission for name, came from external source: "
                                                   + missionName, Level.SEVERE);
            }
        }

        this.loading = false;
    }

    /**
     * Method description
     *
     *
     * @param missionsNames
     */
    public void setFinishedMissions(List<String> missionsNames) {
        this.finishedMissions.clear();

        for (String name : missionsNames) {
            MissionPlacement placement = this.missionsPool.remove(name);

            if (null != placement) {
                this.finishedMissions.add(placement);
            } else {
                placement = this.dependentMissionsPool.remove(name);

                if (null != placement) {
                    this.finishedMissions.add(placement);
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
    public boolean isLoading() {
        return this.loading;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public PriorityTable getMissionsPriorities() {
        return this.missionsPriorities;
    }
}


//~ Formatted in DD Std on 13/08/28
