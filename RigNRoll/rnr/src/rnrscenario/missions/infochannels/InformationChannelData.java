/*
 * @(#)InformationChannelData.java   13/08/28
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


package rnr.src.rnrscenario.missions.infochannels;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.NamedNodeMap;

import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrscenario.missions.AddItemVehicle;
import rnr.src.rnrscenario.missions.IAddItem;
import rnr.src.rnrscenario.missions.IStartMissionListener;
import rnr.src.rnrscenario.missions.MissionCreationContext;
import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.PriorityTable;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscr.IMissionInformation;
import rnr.src.rnrscr.MissionDialogs;
import rnr.src.rnrscr.MissionInfoNotDialog;
import rnr.src.rnrscr.SODialogInformation;
import rnr.src.scenarioMachine.FiniteStateMachine;
import rnr.src.scenarioXml.FiniteStatesSetBuilder;
import rnr.src.scenarioXml.ObjectProperties;
import rnr.src.scenarioXml.XmlFilter;
import rnr.src.scenarioXml.XmlNodeDataProcessor;
import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptEvents.EventsController;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public final class InformationChannelData implements IPlacableChannel {
    private static final String TAG_APPEAR = "appear";
    private static final String TAG_ACCEPT = "accept";
    private static final String TAG_DECLINE = "decline";
    private static final int INITIAL_MAP_CAPACITY = 3;
    private static XPathExpression expr;

    static {
        try {
            expr = XmlUtils.getXPath().compile("descendant::action[@startmission]");
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private String channelName = null;
    private String resource = null;
    private String npc = null;
    private final ArrayList<IAddItem> additem = new ArrayList<IAddItem>();
    private final ArrayList<String> pointsNames = new ArrayList<String>();
    private boolean is_channel_marked_as_main = false;
    private boolean is_channel_marked_as_success = false;
    private boolean is_channel_marked_as_finish = false;
    private String uid = null;
    private String missionname_for_resources = null;
    private ArrayList<String> missionsBegunByThisChannel = null;
    private Object dataForChannelConstruct = null;
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final Map<String, List<ScriptAction>> actionsOnEvents = new HashMap(3);

    /**
     * Constructs ...
     *
     *
     * @param source
     * @param context
     *
     * @throws InformationChannelDataCreationException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public InformationChannelData(org.w3c.dom.Node source, MissionCreationContext context)
            throws InformationChannelDataCreationException {
        assert(null != source) : "target must be non-null reference";
        this.missionsBegunByThisChannel = getMissionsBegunByChannel(source);
        this.actionsOnEvents.put("appear", new LinkedList());
        this.actionsOnEvents.put("accept", new LinkedList());
        this.actionsOnEvents.put("decline", new LinkedList());
        this.is_channel_marked_as_main = (null != source.getAttributes().getNamedItem("main"));
        this.is_channel_marked_as_success = (null != source.getAttributes().getNamedItem("success"));

        if (this.is_channel_marked_as_success) {
            this.is_channel_marked_as_finish = true;
        } else if (this.is_channel_marked_as_main) {
            this.is_channel_marked_as_finish = true;
        }

        org.w3c.dom.Node nameNode = source.getAttributes().getNamedItem("name");
        org.w3c.dom.Node resourceNode = source.getAttributes().getNamedItem("resource");
        boolean nameExist = XmlFilter.textContentExists(nameNode);
        boolean resourceExist = XmlFilter.textContentExists(resourceNode);

        if ((nameExist) && (resourceExist)) {
            this.channelName = nameNode.getTextContent();
            context.enterChannel(this.channelName);
            this.uid = context.getChannelUid(this.channelName);
            this.resource = resourceNode.getTextContent();
            loadActionsOnEvent(source, "appear");
            loadActionsOnEvent(source, "accept");
            loadActionsOnEvent(source, "decline");
            new XmlFilter(source.getChildNodes()).visitAllNodes("point", new XmlNodeDataProcessor() {
                @Override
                public void process(org.w3c.dom.Node target, Object param) {
                    org.w3c.dom.Node nameAttribute = target.getAttributes().getNamedItem("name");

                    if (!(XmlFilter.textContentExists(nameAttribute))) {
                        return;
                    }

                    String tPointName = nameAttribute.getTextContent();

                    if (!(InformationChannelData.this.pointsNames.contains(tPointName))) {
                        InformationChannelData.this.pointsNames.add(tPointName);
                    }
                }
            }, null);
        } else {
            throw new InformationChannelDataCreationException("found invalid channel node: invalid attributes");
        }

        org.w3c.dom.Node npcNode = source.getAttributes().getNamedItem("npc");

        if (XmlFilter.textContentExists(npcNode)) {
            this.npc = npcNode.getTextContent();
        }

        Node adds = new Node(source);
        NodeList list_ads = adds.getNamedChildren("add_item");

        for (Node item : list_ads) {
            if (item.hasAttribute("AddItemVehicle")) {
                String color = item.getAttribute("color");
                AddItemVehicle vehitem = new AddItemVehicle(item.getAttribute("model"), (color != null)
                        ? color
                        : "0");

                this.additem.add(vehitem);
            }
        }
    }

    /**
     * Constructs ...
     *
     *
     * @param channel_name
     * @param resource_ref
     * @param dataForChannelConstruct
     * @param on_appear
     * @param on_accept
     * @param on_decline
     * @param context
     */
    @SuppressWarnings("unchecked")
    public InformationChannelData(String channel_name, String resource_ref, Object dataForChannelConstruct,
                                  List<ScriptAction> on_appear, List<ScriptAction> on_accept,
                                  List<ScriptAction> on_decline, MissionCreationContext context) {
        context.enterChannel(channel_name);
        this.uid = context.getChannelUid(channel_name);
        this.actionsOnEvents.put("appear", on_appear);
        this.actionsOnEvents.put("accept", on_accept);
        this.actionsOnEvents.put("decline", on_decline);
        this.is_channel_marked_as_main = true;
        this.is_channel_marked_as_success = false;
        this.channelName = channel_name;
        this.resource = resource_ref;
        this.dataForChannelConstruct = dataForChannelConstruct;
        this.missionsBegunByThisChannel = new ArrayList();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getUid() {
        return this.uid;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean is_start() {
        return (!(this.is_channel_marked_as_finish));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean is_add_item() {
        return (!(this.additem.isEmpty()));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean is_finish_delay() {
        if (this.is_channel_marked_as_main) {
            return false;
        }

        return (this.is_channel_marked_as_finish);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void loadActionsOnEvent(org.w3c.dom.Node source, String nodeName) {
        assert(null != source) : "source must be non-null reference";
        assert(null != nodeName) : "nodeName must be non-null reference";

        List destination = this.actionsOnEvents.get(nodeName);

        assert(null != destination) : "invalid nodeName, must be one of TAG_APPEAR, TAG_ACCEPT or TAG_DECLINE";

        org.w3c.dom.Node rootOfActionList = new XmlFilter(source.getChildNodes()).nodeNameNext(nodeName);
        FiniteStateMachine scenarioMachine = scenarioscript.script.getScenarioMachine();
        XmlFilter actionFilter = new XmlFilter(rootOfActionList.getChildNodes());
        List actionsOnStartRawInfo = ObjectProperties.extractListEx(actionFilter);

        destination.addAll(FiniteStatesSetBuilder.actionsFromActionList(actionsOnStartRawInfo,
                MissionInfo.getLoadingMissionName(), scenarioMachine));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ArrayList<String> getMissionsBegunByChannel(org.w3c.dom.Node node) {
        ArrayList res = new ArrayList();
        org.w3c.dom.NodeList actonslist = null;

        try {
            actonslist = (org.w3c.dom.NodeList) expr.evaluate(node, XPathConstants.NODESET);
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        if (actonslist.getLength() != 0) {
            XmlFilter filteractions = new XmlFilter(actonslist);
            org.w3c.dom.Node nodeaction = filteractions.nextElement();

            while (null != nodeaction) {
                NamedNodeMap actionattr = nodeaction.getAttributes();
                org.w3c.dom.Node actionname = actionattr.getNamedItem("name");
                String mission_started = actionname.getNodeValue();

                if ("this".compareTo(mission_started) != 0) {
                    res.add(mission_started);
                }

                nodeaction = filteractions.nextElement();
            }
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param missionSourceName
     * @param finishPoint
     * @param questItemPlacement
     * @param listener
     *
     * @return
     *
     * @throws NoSuchChannelException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public InformationChannel makeWare(String missionSourceName, String finishPoint, String questItemPlacement,
                                       IStartMissionListener listener)
            throws NoSuchChannelException {
        InformationChannel ware = InformationChannelsFactory.getInstance().construct(this.channelName,
                                      this.dataForChannelConstruct);

        ware.setMainChannel(this.is_channel_marked_as_main);
        ware.setFinishChannel(this.is_channel_marked_as_finish);
        ware.setUid(this.uid);
        ware.setIdentitie(this.npc);
        ware.setImmediateChannel(isImmediate());
        ware.addPlaces(this.pointsNames);

        InfoChannelEventsListener channelsListener = new InfoChannelEventsListener(ware.getUid());
        ArrayList onend = new ArrayList();

        onend.add(new RemoveResources(this.missionname_for_resources, this.uid, ware));
        channelsListener.useCallbacks(getOnAccept(), getOnAppear(), getOnDecline(), onend,
                                      InformationChannelsFactory.getInstance().getCloseChannelInfo(this.channelName));
        EventsController.getInstance().addListener(channelsListener);

        return ware;
    }

    /**
     * Method description
     *
     *
     * @return
     *
     * @throws NoSuchChannelException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public InformationChannel makeWare() throws NoSuchChannelException {
        InformationChannel ware = InformationChannelsFactory.getInstance().construct(this.channelName,
                                      this.dataForChannelConstruct);

        ware.setMainChannel(this.is_channel_marked_as_main);
        ware.setFinishChannel(this.is_channel_marked_as_finish);
        ware.setUid(this.uid);
        ware.setIdentitie(this.npc);
        ware.setImmediateChannel(isImmediate());
        ware.addPlaces(this.pointsNames);

        if (ware instanceof DelayedChannel) {
            ((DelayedChannel) ware).immediatelyPost(this.is_channel_marked_as_main);
        }

        InfoChannelEventsListener channelsListener = new InfoChannelEventsListener(ware.getUid());
        ArrayList onEnd = new ArrayList();

        onEnd.add(new RemoveResources(this.missionname_for_resources, this.uid, ware));
        channelsListener.useCallbacks(getOnAccept(), getOnAppear(), getOnDecline(), onEnd,
                                      InformationChannelsFactory.getInstance().getCloseChannelInfo(this.channelName));
        EventsController.getInstance().addListener(channelsListener);

        return ware;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<String> getPlacesNames() {
        return Collections.unmodifiableList(this.pointsNames);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getChannelName() {
        return this.channelName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getResource() {
        return this.resource;
    }

    protected String getNpc() {
        return this.npc;
    }

    private List<ScriptAction> getOnAccept() {
        return (this.actionsOnEvents.get("accept"));
    }

    private List<ScriptAction> getOnAppear() {
        return (this.actionsOnEvents.get("appear"));
    }

    private List<ScriptAction> getOnDecline() {
        return (this.actionsOnEvents.get("decline"));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean hasAcceptAction() {
        return (!(getOnAccept().isEmpty()));
    }

    /**
     * Method description
     *
     *
     * @param action
     */
    public void addOnAcceptAction(ScriptAction action) {
        if (null == action) {
            return;
        }

        getOnAccept().add(action);
    }

    IMissionInformation constructMissionInfo(boolean is_dialog, String uid, String mission_name, String resource,
            String identitie, boolean is_FinishChannel, ArrayList<String> dependantMissions) {
        if (!(is_dialog)) {
            return new MissionInfoNotDialog(uid, mission_name, resource, false, is_FinishChannel, dependantMissions);
        }

        return new SODialogInformation(uid, mission_name, resource, identitie, false, is_FinishChannel,
                                       dependantMissions);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param missionImportant
     * @param channelOnFinish
     */
    public void placeResources(String mission_name, boolean missionImportant, boolean channelOnFinish) {
        if (!(MissionSystemInitializer.getMissionsManager().isLoading())) {
            PriorityTable prior = MissionSystemInitializer.getMissionsManager().getPriorityTable();

            this.is_channel_marked_as_finish = channelOnFinish;
            MissionEventsMaker.createChannelResourcesPlaces(mission_name, this.uid, this.pointsNames,
                    prior.getPriority(mission_name), missionImportant, channelOnFinish,
                    this.is_channel_marked_as_success, this.is_channel_marked_as_main);

            if ((channelOnFinish) && (this.is_channel_marked_as_success)
                    && (InformationChannelsFactory.getInstance().isBoundedChannel(this.channelName))) {
                MissionEventsMaker.RegisterSuccesMissionChannelAsBounding(mission_name, this.uid);
            }
        }

        boolean is_dialog = InformationChannelsFactory.getInstance().isDialogChannel(this.channelName);

        MissionDialogs.AddDialog(constructMissionInfo(is_dialog, this.uid, mission_name, this.resource, this.npc,
                channelOnFinish, this.missionsBegunByThisChannel));
        this.missionname_for_resources = mission_name;

        if (!(MissionSystemInitializer.getMissionsManager().isLoading())) {
            for (IAddItem item : this.additem) {
                item.place(mission_name, this.uid);
            }

            if ((null == this.npc) || ("CbvChannel".compareTo(getChannelName()) == 0)
                    || ("CarDialogChannel".compareTo(getChannelName()) == 0)) {
                return;
            }

            MissionEventsMaker.createQuestItemNPC(mission_name, this.npc, this.uid);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isMarkedAsMain() {
        return this.is_channel_marked_as_main;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isImmediate() {
        return InformationChannelsFactory.getInstance().isImmediateChannel(this.channelName);
    }

    private static class RemoveResources extends ScriptAction {
        private String resource = null;
        private String uid = null;
        InformationChannel informationChannel = null;

        RemoveResources(String resourceName, String resourceUid, InformationChannel finishChannel) {
            this.resource = resourceName;
            this.uid = resourceUid;
            this.informationChannel = finishChannel;
        }

        /**
         * Method description
         *
         */
        @Override
        public void act() {
            MissionEventsMaker.clearResource(this.resource, this.uid);

            if ((this.informationChannel != null) && (!(this.informationChannel instanceof RadioChannel))) {
                this.informationChannel.dispose();
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/28
