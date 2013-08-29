/*
 * @(#)MissionInfo.java   13/08/28
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

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrorg.IStoreorgelement;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrorg.MissionOrganiser;
import rnr.src.rnrorg.Organizers;
import rnr.src.rnrscenario.missions.infochannels.InformationChannelData;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;
import rnr.src.scenarioUtils.AdvancedClass;
import rnr.src.scenarioXml.ObjectProperties;
import rnr.src.scenarioXml.XmlFilter;
import rnr.src.scriptActions.FailOrgAction;
import rnr.src.scriptActions.FinishOrgAction;
import rnr.src.scriptActions.MissionActionRemoveResourcesFade;
import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptActions.StartOrgAction;
import rnr.src.scriptEvents.EventChecker;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.MissionEndchecker;
import rnr.src.scriptEvents.MissionStartedOnPoint;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public final class MissionInfo implements IStartMissionListener, IChannelEventListener {
    private static final int ERROR_MESSAGE_CAPACITY = 64;
    private static final int ARRAYS_CAPACITY = 8;
    private static UniqueNamesGenerator namesGenerator;
    private static String loading_name;

    static {
        namesGenerator = null;
    }

    private IMissionStarter starter = new CommonStarter();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final List<MissionPhase> endInfo = new ArrayList(8);
    private MissionPhase startInfo = null;
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final List<InformationChannelData> allChannels = new ArrayList(8);
    private List<QuestItem> questItem = null;
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final Set<String> dependentMissions = new HashSet();
    private String name = null;
    private boolean missionDependFromOther = false;
    private boolean missionDependFromOtherByActivation = false;
    private boolean scenarioMission = false;
    private boolean selfPlacedMission = false;
    private boolean missionIsUnique = false;
    private boolean missionIsBounded = false;
    private String description = null;
    private String externalId = null;
    private String finishPoint = null;
    private String lastChnnelEventPoint = null;
    private boolean isLastChannelImmediate = false;
    private String missionStartPlaceName = null;

    /**
     * Constructs ...
     *
     *
     * @param dataSource
     * @param finishPointName
     */
    public MissionInfo(Node dataSource, String finishPointName) {
        assert(null != dataSource) : "name must be non-null reference";
        assert(null != finishPointName) : "finishPointName must be non-null reference";
        this.finishPoint = finishPointName;
        this.missionDependFromOther = loadBooleanFlagFromAttribute(dataSource, "depend");
        this.missionDependFromOtherByActivation = loadBooleanFlagFromAttribute(dataSource, "dependByActivation");
        this.scenarioMission = loadBooleanFlagFromAttribute(dataSource, "scenarioMission");
        this.missionIsUnique = loadBooleanFlagFromAttribute(dataSource, "uniq");
        this.missionIsBounded = loadBooleanFlagFromAttribute(dataSource, "bounded");
        this.description = loadStringAttribute(dataSource, "description");
        this.externalId = loadStringAttribute(dataSource, "id");

        String organizerName = loadStringAttribute(dataSource, "org");

        if (null == organizerName) {
            organizerName = "unknown";
        }

        Node nameAttributeNode = dataSource.getAttributes().getNamedItem("name");

        if (XmlFilter.textContentExists(nameAttributeNode)) {
            this.name = nameAttributeNode.getTextContent();
        } else {
            this.name = namesGenerator.getName();
        }

        loading_name = this.name;
        MissionOrganiser.getInstance().addMission(this.name, organizerName);

        MissionCreationContext context = new MissionCreationContext(this.name);

        loadQuestItems(dataSource);
        context.enterPhase();
        this.startInfo = new MissionPhase(dataSource, organizerName, context);
        context.exitPhase();
        this.allChannels.addAll(this.startInfo.getInfoChannels());

        MissionStartActions START = new MissionStartActions(this.name, organizerName);

        START.extractActions(dataSource, "start");
        EventsController.getInstance().addListener(START);

        XmlFilter nodeMiner = new XmlFilter(dataSource.getChildNodes());
        Node endNode = nodeMiner.nodeNameNext("end");

        while (null != endNode) {
            context.enterPhase();

            MissionPhase phase = new MissionPhase(endNode, organizerName, context);

            context.exitPhase();
            this.endInfo.add(phase);
            this.allChannels.addAll(phase.getInfoChannels());
            endNode = nodeMiner.nodeNameNext("end");
        }

        loadDependences(nodeMiner);
        loading_name = null;
    }

    /**
     * Constructs ...
     *
     *
     * @param mission_name
     * @param finishPointName
     * @param organizerName
     * @param qi
     * @param start_channels
     * @param start_actions
     * @param success_actions
     * @param damaged_actions
     * @param decline_actions
     * @param timeoutcomplete_actions
     * @param timeoutpickup_actions
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MissionInfo(String mission_name, String finishPointName, String organizerName, QuestItem qi,
                       List<InformationChannelData> start_channels, List<ScriptAction> start_actions,
                       List<ScriptAction> success_actions, List<ScriptAction> damaged_actions,
                       List<ScriptAction> decline_actions, List<ScriptAction> timeoutcomplete_actions,
                       List<ScriptAction> timeoutpickup_actions) {
        this.finishPoint = finishPointName;
        this.missionDependFromOther = false;
        this.missionDependFromOtherByActivation = false;
        this.missionIsUnique = true;
        this.missionIsBounded = false;
        this.description = "null";
        this.externalId = "null";
        this.name = mission_name;
        loading_name = this.name;
        MissionOrganiser.getInstance().addMission(this.name, organizerName);

        if (this.questItem == null) {
            this.questItem = new ArrayList();
        }

        this.questItem.add(qi);
        this.startInfo = new MissionPhase(start_channels, start_actions, null);
        this.allChannels.addAll(this.startInfo.getInfoChannels());

        ArrayList load_actions = new ArrayList();

        load_actions.add(new StartOrgAction(organizerName));

        MissionStartActions START = new MissionStartActions(this.name, organizerName, load_actions);

        EventsController.getInstance().addListener(START);

        EventChecker checker = new MissionEndchecker("success");

        load_actions = new ArrayList();
        load_actions.add(new FinishOrgAction(organizerName));
        load_actions.add(new MissionActionRemoveResourcesFade());
        load_actions.addAll(success_actions);

        MissionPhase phase = new MissionPhase(new ArrayList(), load_actions, checker);

        this.endInfo.add(phase);

        String[] fail_names = { "fail damaged", "decline", "fail timeout complete", "fail timeout pickup" };
        int DAMAGED = 0;
        int DECLINE = 1;
        int TO_COMPLETE = 2;
        int TO_PICKUP = 3;
        int[] fail_types = { 2, 3, 1, 0 };

        for (int i = 0; i < fail_names.length; ++i) {
            checker = new MissionEndchecker(fail_names[i]);
            load_actions = new ArrayList();

            FailOrgAction act = new FailOrgAction(organizerName);

            act.type_fail = fail_types[i];
            load_actions.add(act);

            if (i == DAMAGED) {
                load_actions.addAll(damaged_actions);
            } else if (i == DECLINE) {
                load_actions.addAll(decline_actions);
            } else if (i == TO_COMPLETE) {
                load_actions.addAll(timeoutcomplete_actions);
            } else if (i == TO_PICKUP) {
                load_actions.addAll(timeoutpickup_actions);
            }

            phase = new MissionPhase(new ArrayList(), load_actions, checker);
            this.endInfo.add(phase);
        }

        loading_name = null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean hasAcceptAction() {
        for (InformationChannelData ch : this.allChannels) {
            if (ch.hasAcceptAction()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param point
     * @param withloaditem
     *
     * @return
     */
    public int getNeedParking(String point, boolean withloaditem) {
        int np = 0;

        for (InformationChannelData id : this.allChannels) {
            Place place = MissionSystemInitializer.getMissionsMap().getPlace(point);

            if (place.getTip() == 0) {
                ;
            }

            if (id.getPlacesNames().contains(point)) {
                if ((id.is_start()) && (withloaditem)) {
                    for (QuestItem qi : this.questItem) {
                        if (qi.isSemitrailer()) {
                            ++np;
                        }
                    }
                }

                if (id.is_add_item()) {
                    ++np;
                }
            }
        }

        return np;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isScenario() {
        return this.scenarioMission;
    }

    /**
     * Method description
     *
     */
    public static void init() {
        namesGenerator = new UniqueNamesGenerator("mission");
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        namesGenerator = null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getLoadingMissionName() {
        return loading_name;
    }

    /**
     * Method description
     *
     *
     * @param to_set
     */
    public void setStarter(IMissionStarter to_set) {
        this.starter = to_set;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<MissionPhase> getEndPhase() {
        return this.endInfo;
    }

    @SuppressWarnings("rawtypes")
    private QuestItem makeWare(ObjectProperties source)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
                   InstantiationException {
        assert(null != source) : "source must be valid non-null reference";

        String questItemClassName = source.getName();

        if ((null == questItemClassName) || (0 == questItemClassName.length())) {
            MissionsLogger.getInstance().doLog("found quest item node without name; ignored", Level.WARNING);

            return null;
        }

        AdvancedClass classSearcher = new AdvancedClass(questItemClassName,
                                          MissionXmlStrings.PACKAGES_WITH_QUEST_ITEMS);
        Class<?> questItemClass = classSearcher.getInternal();
        Constructor<?> creator = questItemClass.getDeclaredConstructor(new Class[0]);

        creator.setAccessible(true);

        QuestItem ware = (QuestItem) creator.newInstance(new Object[0]);
        Set<Entry<Object, Object>> properties = source.getParams().entrySet();

        for (Map.Entry property : properties) {
            try {
                Field fieldToSet = questItemClass.getDeclaredField((String) property.getKey());

                fieldToSet.setAccessible(true);
                fieldToSet.set(ware, property.getValue());
            } catch (NoSuchFieldException e) {
                StringBuilder errorMessage = new StringBuilder(64);

                errorMessage.append("Illegal data in XML, field ");
                errorMessage.append((String) property.getKey());
                errorMessage.append(" hasn't been found; exception: ");
                errorMessage.append(e.getLocalizedMessage());
                MissionsLogger.getInstance().doLog(errorMessage.toString(), Level.SEVERE);
            } catch (IllegalAccessException e) {
                MissionsLogger.getInstance().doLog("Access denided to field; exception: " + e.getLocalizedMessage(),
                                                   Level.SEVERE);
            }
        }

        return ware;
    }

    private void processInternalError(Exception e) {
        assert(null != e) : "e must be valid non-null reference";

        StringBuilder errorMessage = new StringBuilder(64);

        errorMessage.append("Internal error; exception message: ");
        errorMessage.append(e.getLocalizedMessage());
        MissionsLogger.getInstance().doLog(errorMessage.toString(), Level.SEVERE);
    }

    private void logErrorMessage(String prefex, ObjectProperties badSource, Exception e) {
        assert(null != prefex) : "prefex must be valid non-null reference";
        assert(null != badSource) : "badSource must be valid non-null reference";
        assert(null != e) : "e must be valid non-null reference";

        StringBuilder errorMessage = new StringBuilder(64);

        errorMessage.append(prefex);
        errorMessage.append(badSource.getName());
        errorMessage.append("'; exception message: ");
        errorMessage.append(e.getLocalizedMessage());
        MissionsLogger.getInstance().doLog(errorMessage.toString(), Level.SEVERE);
    }

    private QuestItem constructQuestItem(ObjectProperties source) {
        assert(null != source) : "source must be valid non-null reference";

        try {
            return makeWare(source);
        } catch (ClassNotFoundException e) {
            logErrorMessage("Illegal data in XML, can't find quest item with name ' ", source, e);
        } catch (NoSuchMethodException e) {
            logErrorMessage("Internal error, constructor with no arguments hasn't been realized in class ' ", source,
                            e);
        } catch (IllegalAccessException e) {
            processInternalError(e);
        } catch (InvocationTargetException e) {
            processInternalError(e);
        } catch (InstantiationException e) {
            processInternalError(e);
        }

        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void loadQuestItems(Node source) {
        if (null != source) {
            XmlFilter dataMiner = new XmlFilter(source.getChildNodes());
            ArrayList<ObjectProperties> questItemsProperties = ObjectProperties.extractListEx(dataMiner, "quest_item");

            this.questItem = new ArrayList(questItemsProperties.size());

            for (ObjectProperties properties : questItemsProperties) {
                QuestItem ware = constructQuestItem(properties);

                if (null != ware) {
                    this.questItem.add(ware);
                }
            }
        } else {
            this.questItem = new ArrayList();
        }
    }

    private static boolean loadBooleanFlagFromAttribute(Node attributesSource, String attributeName) {
        assert(null != attributesSource) : "attributesSource must be non-null reference";
        assert(null != attributeName) : "attributeName must be non-null reference";

        Node attributeNode = attributesSource.getAttributes().getNamedItem(attributeName);

        return ((null != attributeNode) && (Boolean.parseBoolean(attributeNode.getTextContent())));
    }

    private static String loadStringAttribute(Node attributesSource, String attributeName) {
        assert(null != attributesSource) : "attributesSource must be non-null reference";
        assert(null != attributeName) : "attributeName must be non-null reference";

        Node attributeNode = attributesSource.getAttributes().getNamedItem(attributeName);

        if (XmlFilter.textContentExists(attributeNode)) {
            return attributeNode.getTextContent();
        }

        return null;
    }

    private void loadDependences(XmlFilter nodeMiner) {
        assert(null != nodeMiner) : "nodeMiner must be non-null reference";

        Node dependencesRootNode = nodeMiner.goOnStart().nodeNameNext("dependences");

        if ((null == dependencesRootNode) || (0 >= dependencesRootNode.getChildNodes().getLength())) {
            return;
        }

        nodeMiner = new XmlFilter(dependencesRootNode.getChildNodes());

        Node dependentMissionNode = nodeMiner.nodeNameNext("parentFor");

        while (null != dependentMissionNode) {
            Node dependentMissionNameNode = dependentMissionNode.getAttributes().getNamedItem("mission");

            if (XmlFilter.textContentExists(dependentMissionNameNode)) {
                this.dependentMissions.add(dependentMissionNameNode.getTextContent());
            }

            dependentMissionNode = nodeMiner.nodeNameNext("parentFor");
        }
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

    void createItems() {
        for (QuestItem item : this.questItem) {
            item.doPlace(this.name);
        }
    }

    void executeStartActions() {
        this.startInfo.execute();
    }

    SingleMission constructMission() {
        SingleMission ware = new SingleMission(this.name, this.endInfo);

        ware.setSerializationUid(this.name);

        return ware;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isDepend() {
        return this.missionDependFromOther;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isDependByActivation() {
        return this.missionDependFromOtherByActivation;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean hasStartDependence() {
        return ((this.missionDependFromOtherByActivation) || (this.missionDependFromOther));
    }

    /**
     * Method description
     *
     *
     * @param scenarioMission
     */
    public void setScenarioMission(boolean scenarioMission) {
        this.scenarioMission = scenarioMission;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isScenarioMission() {
        return this.scenarioMission;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isSelfPlacedMission() {
        return this.selfPlacedMission;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setSelfPlacedMission(boolean value) {
        this.selfPlacedMission = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isUnique() {
        return this.missionIsUnique;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isBounded() {
        return this.missionIsBounded;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getExternalId() {
        return this.externalId;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getFinishPoint() {
        return this.finishPoint;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getQuestItemPlacement() {
        if (this.questItem.isEmpty()) {
            MissionsLogger.getInstance().doLog("no quest items loaded for mission " + this.name, Level.SEVERE);

            return null;
        }

        return this.questItem.get(0).getPlacement();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<InformationChannelData> getInfoStartChannels() {
        return this.startInfo.getInfoChannels();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<InformationChannelData> getInfoEndChannels() {
        List res = new ArrayList();

        for (MissionPhase phase : this.endInfo) {
            res.addAll(phase.getInfoChannels());
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<InformationChannelData> getAllChannels() {
        return Collections.unmodifiableList(this.allChannels);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isImportant() {
        boolean isMissionImportant = false;
        String orgName = MissionOrganiser.getInstance().getOrgForMission(this.name);

        if (null != orgName) {
            IStoreorgelement org = Organizers.getInstance().get(orgName);

            if (null != org) {
                isMissionImportant = org.isImportant();
            }
        }

        return isMissionImportant;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String missionStarted() {
        EventsController.getInstance().eventHappen(new ScriptEvent[] {
            new MissionStartedOnPoint(this.name, this.lastChnnelEventPoint) });

        return this.starter.startMission(this.name, this.isLastChannelImmediate, this.lastChnnelEventPoint,
                                         getQuestItemPlacement(), this.finishPoint);
    }

    /**
     * Method description
     *
     *
     * @param missionStartPlaceName
     */
    @Override
    public void missionStartedOnPoint(String missionStartPlaceName) {
        EventsController.getInstance().eventHappen(new ScriptEvent[] {
            new MissionStartedOnPoint(this.name, this.lastChnnelEventPoint) });
        this.starter.startMission(this.name, this.isLastChannelImmediate, this.lastChnnelEventPoint,
                                  getQuestItemPlacement(), this.finishPoint, missionStartPlaceName);
    }

    /**
     * Method description
     *
     *
     * @param pointName
     * @param channelUid
     * @param isChannelImmediate
     */
    @Override
    public void eventOnChannel(String pointName, String channelUid, boolean isChannelImmediate) {
        this.lastChnnelEventPoint = pointName;
        this.isLastChannelImmediate = isChannelImmediate;

        for (String mission_name : this.dependentMissions) {
            if (mission_name.compareTo(this.name) == 0) {
                continue;
            }

            IChannelEventListener lst = StartMissionListeners.getInstance().getChannleEventListener(mission_name);

            if (lst != null) {
                lst.eventOnChannel(pointName, channelUid, isChannelImmediate);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Collection<String> getDependent() {
        return this.dependentMissions;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getMissionStartPlaceName() {
        return this.missionStartPlaceName;
    }

    /**
     * Method description
     *
     *
     * @param missionStartPlaceName
     */
    public void setMissionStartPlaceName(String missionStartPlaceName) {
        this.missionStartPlaceName = missionStartPlaceName;
    }

    static class CommonStarter implements IMissionStarter {

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
            MissionEventsMaker.startMission(mission_name, isChannelImmeiate, info_channel_point, qi_point,
                                            finish_point);

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
         * @param missionPlaceStartName
         */
        @Override
        public void startMission(String mission_name, boolean isChannelImmeiate, String info_channel_point,
                                 String qi_point, String finish_point, String missionPlaceStartName) {
            MissionEventsMaker.startMission(mission_name, isChannelImmeiate, info_channel_point, qi_point,
                                            finish_point);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
