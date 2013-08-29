/*
 * @(#)MissionsSerialization.java   13/08/28
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.XmlSerializable;
import rnr.src.rnrscenario.missions.MissionManager;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.starters.ConditionChecker;
import rnr.src.scenarioUtils.Pair;
import rnr.src.scenarioXml.XmlFilter;
import rnr.src.xmlserialization.nxs.SerializatorOfAnnotated;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class MissionsSerialization implements XmlSerializable {
    private static MissionsSerialization instance = new MissionsSerialization();

    /**
     * Method description
     *
     *
     * @return
     */
    public static MissionsSerialization getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getRootNodeName() {
        return getNodeName();
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void loadFromNode(org.w3c.dom.Node node) {
        deserializeXML(new Node(node));
    }

    /**
     * Method description
     *
     */
    @Override
    public void yourNodeWasNotFound() {}

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        serializeXML(MissionSystemInitializer.getMissionsManager(), stream);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "missions";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings("rawtypes")
    public static void serializeXML(MissionManager value, PrintStream stream) {
        Helper.openNode(stream, getNodeName());
        Helper.openNode(stream, "sf_finish_conditions");

        List<ConditionChecker> allCheckers = ConditionChecker.getAllCheckers();

        for (ConditionChecker checker : allCheckers) {
            SerializatorOfAnnotated.getInstance().saveState(stream, checker);
        }

        Helper.closeNode(stream, "sf_finish_conditions");

        List<rnr.src.rnrscenario.missions.Pair<String, String>> activatedMissions = value.getActivatedMissions();

        if ((null != activatedMissions) && (!(activatedMissions.isEmpty()))) {
            Helper.openNode(stream, "activemissions");

            for (Pair name : activatedMissions) {
                ListElementSerializator.serializeXMLListelementOpen(stream);
                SimpleTypeSerializator.serializeXMLString((String) name.getFirst(), stream);

                if (name.getSecond() != null) {
                    SimpleTypeSerializator.serializeXMLString((String) name.getSecond(), stream);
                }

                ListElementSerializator.serializeXMLListelementClose(stream);
            }

            Helper.closeNode(stream, "activemissions");
        }

        List finishedMissions = value.getFinisheddMissions();

        if ((null != finishedMissions) && (!(finishedMissions.isEmpty()))) {
            Helper.openNode(stream, "finishedmissions");

            for (String name : finishedMissions) {
                ListElementSerializator.serializeXMLListelementOpen(stream);
                SimpleTypeSerializator.serializeXMLString(name, stream);
                ListElementSerializator.serializeXMLListelementClose(stream);
            }

            Helper.closeNode(stream, "finishedmissions");
        }

        MissionsPrioritySerialization.serializeXML(value.getMissionsPriorities(), stream);
        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void deserializeXML(rnr.src.xmlutils.Node node) {
        MissionManager manager = MissionSystemInitializer.getMissionsManager();
        Node activeMissions = node.getNamedChild("activemissions");

        if (null != activeMissions) {
            NodeList listActiveMissions = activeMissions.getNamedChildren(ListElementSerializator.getNodeName());

            if ((null != listActiveMissions) && (!(listActiveMissions.isEmpty()))) {
                List missionsNames = new ArrayList();

                for (Node activeMissionNode : listActiveMissions) {
                    NodeList nameNode = activeMissionNode.getNamedChildren(SimpleTypeSerializator.getNodeNameString());

                    if (null == nameNode) {
                        Log.error("MissionsSerialization in deserializeXML has no named nodes "
                                  + SimpleTypeSerializator.getNodeNameString() + " in node named "
                                  + ListElementSerializator.getNodeName() + " in node named " + "activemissions");
                    } else {
                        Pair pairMissionNamePointName = new Pair(null, null);
                        int counStrings = 0;

                        for (Node nodeString : nameNode) {
                            if (counStrings == 0) {
                                String missionName = SimpleTypeSerializator.deserializeXMLString(nodeString);

                                pairMissionNamePointName.setFirst(missionName);
                            } else if (counStrings == 1) {
                                String pointName = SimpleTypeSerializator.deserializeXMLString(nodeString);

                                pairMissionNamePointName.setSecond(pointName);
                            }

                            ++counStrings;
                        }

                        missionsNames.add(pairMissionNamePointName);
                    }
                }

                manager.setActivatedMissions(missionsNames);
            }
        }

        xmlutils.Node finishedMissions = node.getNamedChild("finishedmissions");

        if (null != finishedMissions) {
            NodeList listFinishedMissions = finishedMissions.getNamedChildren(ListElementSerializator.getNodeName());

            if ((null != listFinishedMissions) && (!(listFinishedMissions.isEmpty()))) {
                List miossionsNames = new ArrayList();

                for (xmlutils.Node activeMissionNode : listFinishedMissions) {
                    xmlutils.Node nameNode =
                        activeMissionNode.getNamedChild(SimpleTypeSerializator.getNodeNameString());

                    if (null == nameNode) {
                        Log.error("MissionsSerialization in deserializeXML has no named node "
                                  + SimpleTypeSerializator.getNodeNameString() + " in node named "
                                  + ListElementSerializator.getNodeName() + " in node named " + "finishedmissions");
                    } else {
                        String missionName = SimpleTypeSerializator.deserializeXMLString(nameNode);

                        miossionsNames.add(missionName);
                    }
                }

                manager.setFinishedMissions(miossionsNames);
            }
        }

        xmlutils.Node priorityNode = node.getNamedChild(MissionsPrioritySerialization.getNodeName());

        if (null != priorityNode) {
            MissionsPrioritySerialization.deserializeXML(manager.getMissionsPriorities(), priorityNode);
        }

        xmlutils.Node statefullFinishConditions = node.getNamedChild("sf_finish_conditions");

        if (null == statefullFinishConditions) {
            return;
        }

        XmlFilter nodesFilter = new XmlFilter(statefullFinishConditions.getNode().getChildNodes());
        org.w3c.dom.Node finishCheckerNode = nodesFilter.nextElement();

        while (null != finishCheckerNode) {
            if (null == SerializatorOfAnnotated.getInstance().loadStateOrNull(new xmlutils.Node(finishCheckerNode))) {
                Log.error("Finish condition was not loaded from mission node with name "
                          + finishCheckerNode.getNodeName());
            }

            finishCheckerNode = nodesFilter.nextElement();
        }
    }
}


//~ Formatted in DD Std on 13/08/28
