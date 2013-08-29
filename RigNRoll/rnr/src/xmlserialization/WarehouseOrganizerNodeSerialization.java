/*
 * @(#)WarehouseOrganizerNodeSerialization.java   13/08/28
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

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrorg.CustomerWarehouseAssociation;
import rnr.src.rnrorg.EmptyCustomer;
import rnr.src.rnrorg.INPC;
import rnr.src.rnrorg.IStoreorgelement;
import rnr.src.rnrorg.QuestCustomer;
import rnr.src.rnrorg.QuestNPC;
import rnr.src.rnrorg.RewardForfeit;
import rnr.src.rnrorg.WarehouseOrder;
import rnr.src.rnrorg.journable;
import rnr.src.scenarioUtils.Pair;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class WarehouseOrganizerNodeSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "warehouseorder";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(WarehouseOrder value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("is_read", value.isRead());

        Helper.addAttribute("id", value.getName(), attributes);
        Helper.addAttribute("type", value.getType().toString(), attributes);
        Helper.addAttribute("status", value.getStatus().toString(), attributes);
        Helper.addAttribute("important", value.isImportant(), attributes);
        Helper.addAttribute("type_failed", value.getType_failed(), attributes);
        Helper.addAttribute("rewardFlag", value.getRewardFlag(), attributes);
        Helper.addAttribute("forfeitFlag", value.getForfeitFlag(), attributes);
        Helper.addAttribute("minutes_to_complete", value.get_minutes_toFail(), attributes);
        Helper.addAttribute("seconds_to_complete", value.get_seconds_toFail(), attributes);
        Helper.addAttribute("mission_state", value.getMissionState(), attributes);
        Helper.addAttribute("fragility", value.getFragility(), attributes);
        Helper.addAttribute("loadPoint", value.loadPoint(), attributes);
        Helper.addAttribute("completePoint", value.endPoint(), attributes);
        Helper.addAttribute("orderdescription", value.getOrderdescription(), attributes);
        Helper.addAttribute("racename", value.getRaceName(), attributes);
        Helper.addAttribute("logoname", value.getLogoName(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);

        RewardForfeit reward = value.getReward();

        if (null != reward) {
            List<Pair<String, String>> tempAttributes = Helper.createSingleAttribute("coef_money", reward.gCoefMoney());

            Helper.addAttribute("coef_rate", reward.gCoefRate(), tempAttributes);
            Helper.addAttribute("coef_rank", reward.gCoefRank(), tempAttributes);
            Helper.addAttribute("estimate_money", reward.gMoney(), tempAttributes);
            Helper.addAttribute("estimate_rate", reward.gRate(), tempAttributes);
            Helper.addAttribute("estimate_rank", reward.gRank(), tempAttributes);
            Helper.addAttribute("real_money", reward.getRealMoney(), tempAttributes);
            Helper.printClosedNodeWithAttributes(stream, "reward", tempAttributes);
        }

        RewardForfeit forfeit = value.getForfeit();

        if (null != forfeit) {
            List<Pair<String, String>> tempAttributes = Helper.createSingleAttribute("coef_money",
                                                            forfeit.gCoefMoney());

            Helper.addAttribute("coef_rate", forfeit.gCoefRate(), tempAttributes);
            Helper.addAttribute("coef_rank", forfeit.gCoefRank(), tempAttributes);
            Helper.addAttribute("estimate_money", forfeit.gMoney(), tempAttributes);
            Helper.addAttribute("estimate_rate", forfeit.gRate(), tempAttributes);
            Helper.addAttribute("estimate_rank", forfeit.gRank(), tempAttributes);
            Helper.addAttribute("real_money", forfeit.getRealMoney(), tempAttributes);
            Helper.printClosedNodeWithAttributes(stream, "forfeit", tempAttributes);
        }

        INPC customer = value.getCustomer();

        if (null != customer) {
            if (customer instanceof CustomerWarehouseAssociation) {
                Helper.printClosedNodeWithAttributes(stream, "customer_wh", null);
            } else if (customer instanceof EmptyCustomer) {
                Helper.printClosedNodeWithAttributes(stream, "customer_empty", null);
            } else if (customer instanceof QuestCustomer) {
                Helper.printClosedNodeWithAttributes(stream, "customer_quest",
                        Helper.createSingleAttribute("customer_name", customer.getID()));
            } else if (customer instanceof QuestNPC) {
                Helper.printClosedNodeWithAttributes(stream, "customer_npc",
                        Helper.createSingleAttribute("customer_name", customer.getID()));
            }
        }

        CoreTime requestTime = value.dateOfRequest();

        if (null != requestTime) {
            Helper.openNode(stream, "requestTime");
            CoreTimeSerialization.serializeXML(requestTime, stream);
            Helper.closeNode(stream, "requestTime");
        }

        CoreTime completeTime = value.timeToComplete();

        if (null != completeTime) {
            Helper.openNode(stream, "completeTime");
            CoreTimeSerialization.serializeXML(completeTime, stream);
            Helper.closeNode(stream, "completeTime");
        }

        journable startNote = value.getStartNote();

        if (null != startNote) {
            Helper.openNode(stream, "startNote");
            JournalElementSerialization.serializeXML(startNote, stream);
            Helper.closeNode(stream, "startNote");
        }

        journable finishNote = value.getFinishNote();

        if (null != finishNote) {
            Helper.openNode(stream, "finishNote");
            JournalElementSerialization.serializeXML(finishNote, stream);
            Helper.closeNode(stream, "finishNote");
        }

        journable[] failNote = value.getFailNote();

        if (null != failNote) {
            Helper.openNode(stream, "failNotes");

            for (journable element : failNote) {
                ListElementSerializator.serializeXMLListelementOpen(stream);

                if (null == element) {
                    Helper.printClosedNodeWithAttributes(stream, "failNotes_empty", null);
                } else {
                    JournalElementSerialization.serializeXML(element, stream);
                }

                ListElementSerializator.serializeXMLListelementClose(stream);
            }

            Helper.closeNode(stream, "failNotes");
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static WarehouseOrder deserializeXML(Node node) {
        String errorrMessage = "WarehouseOrganizerNodeSerialization in deserializeXML ";
        String isReadString = node.getAttribute("is_read");
        String nameString = node.getAttribute("id");
        String typeString = node.getAttribute("type");
        String statusString = node.getAttribute("status");
        String isImportantString = node.getAttribute("important");
        String typeFailedString = node.getAttribute("type_failed");
        String rewardFlagString = node.getAttribute("rewardFlag");
        String forfeitFlagString = node.getAttribute("forfeitFlag");
        String minutesToCompleteString = node.getAttribute("minutes_to_complete");
        String secondsToCompleteString = node.getAttribute("seconds_to_complete");
        String missionStateString = node.getAttribute("mission_state");
        String fragilityString = node.getAttribute("fragility");
        String loadpointString = node.getAttribute("loadPoint");
        String completePointString = node.getAttribute("completePoint");
        String orderdescriptionString = node.getAttribute("orderdescription");
        String raceNameString = node.getAttribute("racename");
        String logoString = node.getAttribute("logoname");
        boolean isRead = Helper.ConvertToBooleanAndWarn(isReadString, "is_read", errorrMessage);
        IStoreorgelement.Type type = IStoreorgelement.Type.valueOf(typeString);
        IStoreorgelement.Status status = IStoreorgelement.Status.valueOf(statusString);
        boolean isImportant = Helper.ConvertToBooleanAndWarn(isImportantString, "important", errorrMessage);
        int typeFailed = Helper.ConvertToIntegerAndWarn(typeFailedString, "type_failed", errorrMessage);
        int rewardFlag = Helper.ConvertToIntegerAndWarn(rewardFlagString, "rewardFlag", errorrMessage);
        int forfeitFlag = Helper.ConvertToIntegerAndWarn(forfeitFlagString, "forfeitFlag", errorrMessage);
        int minutesToComplete = Helper.ConvertToIntegerAndWarn(minutesToCompleteString, "minutes_to_complete",
                                    errorrMessage);
        int secondsToComplete = Helper.ConvertToIntegerAndWarn(secondsToCompleteString, "seconds_to_complete",
                                    errorrMessage);
        int missionState = Helper.ConvertToIntegerAndWarn(missionStateString, "mission_state", errorrMessage);
        double fragility = Helper.ConvertToDoubleAndWarn(fragilityString, "fragility", errorrMessage);
        Node rewardNode = node.getNamedChild("reward");
        Node forfeitNode = node.getNamedChild("forfeit");
        Node customerWarehouseAssociationNode = node.getNamedChild("customer_wh");
        Node customerEmptyNode = node.getNamedChild("customer_empty");
        Node customerQuestNode = node.getNamedChild("customer_quest");
        Node customerNpcNode = node.getNamedChild("customer_npc");
        Node startNoteNode = node.getNamedChild("startNote");
        Node finishNoteNode = node.getNamedChild("finishNote");
        Node failNoteNode = node.getNamedChild("failNotes");
        Node requestTimeNode = node.getNamedChild("requestTime");
        Node completeTimeNode = node.getNamedChild("completeTime");
        RewardForfeit reward = OrganizerNodeSerialization.deserializeRewardForfeit(rewardNode);
        RewardForfeit forfeit = OrganizerNodeSerialization.deserializeRewardForfeit(forfeitNode);
        INPC customer = null;
        journable startNote = null;
        journable finishNote = null;
        journable[] failNotes = null;
        CoreTime requestTime = null;
        CoreTime completeTime = null;

        if (null != requestTimeNode) {
            Node nodeTime = requestTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());

            if (null == nodeTime) {
                Log.error(errorrMessage + " node named " + "requestTime" + " has no named node "
                          + CoreTimeSerialization.getNodeName());
            } else {
                requestTime = CoreTimeSerialization.deserializeXML(nodeTime);
            }
        }

        if (null != completeTimeNode) {
            Node nodeTime = completeTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());

            if (null == nodeTime) {
                Log.error(errorrMessage + " node named " + "completeTime" + " has no named node "
                          + CoreTimeSerialization.getNodeName());
            } else {
                completeTime = CoreTimeSerialization.deserializeXML(nodeTime);
            }
        }

        if (null != customerWarehouseAssociationNode) {
            customer = new CustomerWarehouseAssociation();
        } else if (null != customerEmptyNode) {
            customer = new EmptyCustomer();
        } else if (null != customerQuestNode) {
            String nameCustomer = customerQuestNode.getAttribute("customer_name");

            if (null == nameCustomer) {
                Log.error(
                    "OrganizerNodeSerialization on deserializeXML has no attribute with name customer_name on node named customer_quest");
            } else {
                customer = new QuestCustomer(nameCustomer);
            }
        } else if (null != customerNpcNode) {
            String nameCustomer = customerNpcNode.getAttribute("customer_name");

            if (null == nameCustomer) {
                Log.error(
                    "OrganizerNodeSerialization on deserializeXML has no attribute with name customer_name on node named customer_npc");
            } else {
                customer = new QuestNPC(nameCustomer);
            }
        }

        if (null != startNoteNode) {
            startNote = JournalElementSerialization.deserializeXML(startNoteNode);
        }

        if (null != finishNoteNode) {
            finishNote = JournalElementSerialization.deserializeXML(finishNoteNode);
        }

        int i;

        if (null != failNoteNode) {
            NodeList listFailNodes = failNoteNode.getNamedChildren(ListElementSerializator.getNodeName());

            failNotes = new journable[listFailNodes.size()];
            i = 0;

            for (Node element : listFailNodes) {
                Node emptyNode = element.getNamedChild("failNotes_empty");
                Node journalNode = element.getNamedChild(JournalElementSerialization.getNodeName());

                if (null == emptyNode) {
                    journable jou = JournalElementSerialization.deserializeXML(journalNode);

                    failNotes[i] = jou;
                }

                ++i;
            }
        }

        WarehouseOrder result = new WarehouseOrder();

        result.setName(nameString);
        result.setLoadPoint(loadpointString);
        result.setCompletePoint(completePointString);
        result.setOrderDescription(orderdescriptionString);
        result.setRaceName(raceNameString);
        result.setLogoName(logoString);
        result.setIs_read(isRead);
        result.setType(type);
        result.setStatus(status);
        result.setImportant(isImportant);
        result.setType_failed(typeFailed);
        result.setRewardFlag(rewardFlag);
        result.setForfeitFlag(forfeitFlag);
        result.updateTimeToComplete(minutesToComplete, secondsToComplete, missionState, "");
        result.setFragility(fragility);
        result.setReward(reward);
        result.setForfeit(forfeit);
        result.setCustomer(customer);
        result.setStartNote(startNote);
        result.setFinishNote(finishNote);
        result.setFailNote(failNotes);
        result.setRequestTime(requestTime);
        result.setCompleteTime(completeTime);

        return result;
    }
}


//~ Formatted in DD Std on 13/08/28
