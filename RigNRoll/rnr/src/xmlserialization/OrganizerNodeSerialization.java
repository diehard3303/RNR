/*
 * @(#)OrganizerNodeSerialization.java   13/08/28
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

import rnr.src.rnrorg.CustomerWarehouseAssociation;
import rnr.src.rnrorg.EmptyCustomer;
import rnr.src.rnrorg.IDeclineOrgListener;
import rnr.src.rnrorg.INPC;
import rnr.src.rnrorg.IStoreorgelement;
import rnr.src.rnrorg.IStoreorgelement.Status;
import rnr.src.rnrorg.IStoreorgelement.Type;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrorg.MissionEventsMaker.DeclineMissionListener;
import rnr.src.rnrorg.MissionTime;
import rnr.src.rnrorg.QuestCargoParams;
import rnr.src.rnrorg.QuestCustomer;
import rnr.src.rnrorg.QuestNPC;
import rnr.src.rnrorg.RewardForfeit;
import rnr.src.rnrorg.Scorgelement;
import rnr.src.rnrorg.journable;
import rnr.src.scenarioUtils.Pair;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class OrganizerNodeSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "organisernode";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(Scorgelement value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("id", value.getName());

        Helper.addAttribute("type", value.getType().toString(), attributes);
        Helper.addAttribute("status", value.getStatus().toString(), attributes);
        Helper.addAttribute("hasfragility", value.hasFragility(), attributes);
        Helper.addAttribute("fragility", value.getFragility(), attributes);
        Helper.addAttribute("important", value.isImportant(), attributes);
        Helper.addAttribute("rewardflag", value.getRewardFlag(), attributes);
        Helper.addAttribute("forfeit_flag", value.getForfeitFlag(), attributes);
        Helper.addAttribute("description", value.getDescriptionOriginal(), attributes);
        Helper.addAttribute("coef_time_to_pickup", value.getCoefTimePickup(), attributes);
        Helper.addAttribute("coef_time_to_complete", value.getCoefTimeComplete(), attributes);
        Helper.addAttribute("startpoint", value.getStartPoint(), attributes);
        Helper.addAttribute("pickuppoint", value.getLoadPoint(), attributes);
        Helper.addAttribute("finishpoint", value.getCompletePoint(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);

        RewardForfeit reward = value.getReward();

        serializeRewardForfeit(stream, "reward", reward);

        RewardForfeit forfeit = value.getForfeit();

        serializeRewardForfeit(stream, "forfeit", forfeit);

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

        ArrayList<IDeclineOrgListener> declineListeners = value.getListeners();

        if ((null != declineListeners) && (!(declineListeners.isEmpty()))) {
            if (declineListeners.size() > 1) {
                Log.error("OrganizerNodeSerialization in serializeXML has decline listeners more that 1");
            }

            IDeclineOrgListener listener = declineListeners.get(0);

            if (listener instanceof MissionEventsMaker.DeclineMissionListener) {
                MissionEventsMaker.DeclineMissionListener declineMissionListener =
                    (MissionEventsMaker.DeclineMissionListener) listener;

                Helper.printClosedNodeWithAttributes(stream, "declinelistener",
                        Helper.createSingleAttribute("missionName", declineMissionListener.getMission_name()));
            } else {
                Log.error("OrganizerNodeSerialization in serializeXML has decline listeners with unrecognized type "
                          + listener.getClass().getName());
            }
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
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Scorgelement deserializeXML(Node node) {
        String errorMessage = "OrganizerNodeSerialization in deserializeXML ";
        String name = node.getAttribute("id");
        String typeString = node.getAttribute("type");
        String importantString = node.getAttribute("important");
        String rewardFlagString = node.getAttribute("rewardflag");
        String forfeitFlagString = node.getAttribute("forfeit_flag");
        String descriptionString = node.getAttribute("description");
        String coefTimePickupString = node.getAttribute("coef_time_to_pickup");
        String coeftimecompleteString = node.getAttribute("coef_time_to_complete");
        String startPoint = node.getAttribute("startpoint");
        String loadPoint = node.getAttribute("pickuppoint");
        String completePoint = node.getAttribute("finishpoint");
        String statusString = node.getAttribute("status");
        String hasFragilityString = node.getAttribute("hasfragility");
        String fragilityString = node.getAttribute("fragility");
        IStoreorgelement.Status status = IStoreorgelement.Status.valueOf(statusString);
        boolean hasFragility = Helper.ConvertToBooleanAndWarn(hasFragilityString, "hasfragility", errorMessage);
        double fragilityValue = Helper.ConvertToDoubleAndWarn(fragilityString, "fragility", errorMessage);
        IStoreorgelement.Type type = IStoreorgelement.Type.valueOf(typeString);
        boolean isImportant = Helper.ConvertToBooleanAndWarn(importantString, "important", errorMessage);
        int rewardFlag = Helper.ConvertToIntegerAndWarn(rewardFlagString, "rewardflag", errorMessage);
        int forfeitFlag = Helper.ConvertToIntegerAndWarn(forfeitFlagString, "forfeit_flag", errorMessage);
        double coefTimePickUp = Helper.ConvertToDoubleAndWarn(coefTimePickupString, "coef_time_to_pickup",
                                    errorMessage);
        double coefTimeComplete = Helper.ConvertToDoubleAndWarn(coeftimecompleteString, "coef_time_to_complete",
                                      errorMessage);
        MissionTime coefTimePickUpResult = new MissionTime(coefTimePickUp);
        MissionTime coefTimeCompleteResult = new MissionTime(coefTimeComplete);
        Node rewardNode = node.getNamedChild("reward");
        Node forfeitNode = node.getNamedChild("forfeit");
        Node customerWarehouseAssociationNode = node.getNamedChild("customer_wh");
        Node customerEmptyNode = node.getNamedChild("customer_empty");
        Node customerQuestNode = node.getNamedChild("customer_quest");
        Node customerNpcNode = node.getNamedChild("customer_npc");
        Node startNoteNode = node.getNamedChild("startNote");
        Node finishNoteNode = node.getNamedChild("finishNote");
        Node failNoteNode = node.getNamedChild("failNotes");
        Node declineListenerNode = node.getNamedChild("declinelistener");
        RewardForfeit reward = deserializeRewardForfeit(rewardNode);
        RewardForfeit forfeit = deserializeRewardForfeit(forfeitNode);
        INPC customer = null;
        journable startNote = null;
        journable finishNote = null;
        journable[] failNotes = null;
        IDeclineOrgListener declineListener = null;

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
            Node journalNote = startNoteNode.getNamedChild(JournalElementSerialization.getNodeName());

            if (null != journalNote) {
                startNote = JournalElementSerialization.deserializeXML(journalNote);
            }
        }

        if (null != finishNoteNode) {
            Node journalNote = finishNoteNode.getNamedChild(JournalElementSerialization.getNodeName());

            if (null != journalNote) {
                finishNote = JournalElementSerialization.deserializeXML(journalNote);
            }
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

        if (null != declineListenerNode) {
            String missionName = declineListenerNode.getAttribute("missionName");

            if (null == missionName) {
                Log.error(errorMessage + " node with name " + "declinelistener" + " has no attribute with name "
                          + "missionName");
            } else {
                declineListener = new MissionEventsMaker.DeclineMissionListener(missionName);
            }
        }

        Scorgelement orgElement = new Scorgelement(name, type, isImportant, rewardFlag, reward, forfeitFlag, forfeit,
                                      descriptionString, customer, coefTimePickUpResult, coefTimeCompleteResult,
                                      startNote, finishNote, failNotes);

        orgElement.setSerialPoints(startPoint, loadPoint, completePoint);
        orgElement.setStatus(status);

        QuestCargoParams cargoParams = new QuestCargoParams();

        cargoParams.setCargoParams(hasFragility, fragilityValue);
        orgElement.setCargoParams(cargoParams);

        if (null != declineListener) {
            ArrayList declineListeners = new ArrayList();

            declineListeners.add(declineListener);
            orgElement.setListeners(declineListeners);
        }

        return orgElement;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    static void serializeRewardForfeit(PrintStream stream, String nodeName, RewardForfeit value) {
        if (null != value) {
            List<Pair<String, String>> tempAttributes = Helper.createSingleAttribute("coef_money", value.gCoefMoney());

            Helper.addAttribute("coef_rate", value.gCoefRate(), tempAttributes);
            Helper.addAttribute("coef_rank", value.gCoefRank(), tempAttributes);
            Helper.addAttribute("estimate_money", value.gMoney(), tempAttributes);
            Helper.addAttribute("estimate_rate", value.gRate(), tempAttributes);
            Helper.addAttribute("estimate_rank", value.gRank(), tempAttributes);
            Helper.addAttribute("real_money", value.getRealMoney(), tempAttributes);
            Helper.printOpenNodeWithAttributes(stream, nodeName, tempAttributes);

            HashMap<String, Double> factionRatings = value.getFactionRatings();

            if ((factionRatings != null) && (!(factionRatings.isEmpty()))) {
                Set<Entry<String, Double>> ratingsSet = factionRatings.entrySet();

                for (Map.Entry singleFactionEntry : ratingsSet) {
                    List factionAttributes = Helper.createSingleAttribute("name", (String) singleFactionEntry.getKey());

                    Helper.addAttribute("value", ((Double) singleFactionEntry.getValue()).doubleValue(),
                                        factionAttributes);
                    Helper.printClosedNodeWithAttributes(stream, "faction", factionAttributes);
                }
            }

            Helper.closeNode(stream, nodeName);
        }
    }

    static RewardForfeit deserializeRewardForfeit(Node node) {
        if (node == null) {
            return null;
        }

        String moneyCoefString = node.getAttribute("coef_money");
        String rateCoefString = node.getAttribute("coef_rate");
        String rankCoefString = node.getAttribute("coef_rank");
        String moneyString = node.getAttribute("estimate_money");
        String rateString = node.getAttribute("estimate_rate");
        String rankString = node.getAttribute("estimate_rank");
        String realMoneyString = node.getAttribute("real_money");
        double coefMoneyValue = Helper.ConvertToDoubleAndWarn(moneyCoefString, "coef_money",
                                    "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double coefRateValue = Helper.ConvertToDoubleAndWarn(rateCoefString, "coef_rate",
                                   "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double coefRankValue = Helper.ConvertToDoubleAndWarn(rankCoefString, "coef_rank",
                                   "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double moneyValue = Helper.ConvertToDoubleAndWarn(moneyString, "estimate_money",
                                "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double rateValue = Helper.ConvertToDoubleAndWarn(rateString, "estimate_rate",
                               "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double rankValue = Helper.ConvertToDoubleAndWarn(rankString, "estimate_rank",
                               "OrganizerNodeSerialization on deserializeRewardForfeit ");
        double realMoneyValue = Helper.ConvertToDoubleAndWarn(realMoneyString, "real_money",
                                    "OrganizerNodeSerialization on deserializeRewardForfeit ");
        RewardForfeit result = new RewardForfeit(coefMoneyValue, coefRateValue, coefRankValue);

        result.setRealMoney((int) realMoneyValue);
        result.sMoney((int) moneyValue);
        result.sRank((int) rankValue);
        result.sRate((int) rateValue);

        NodeList listFactionRewards = node.getNamedChildren("faction");

        if ((listFactionRewards != null) && (!(listFactionRewards.isEmpty()))) {
            for (Node factionRewardNode : listFactionRewards) {
                String factionName = factionRewardNode.getAttribute("name");

                if (factionName == null) {
                    Log.error("deserializeRewardForfeit. Node faction has no attribute name");
                }

                String factionRewardValueString = factionRewardNode.getAttribute("value");

                if (factionRewardValueString == null) {
                    Log.error("deserializeRewardForfeit. Node faction has no attribute value");
                }

                double factionRewardValue = Helper.ConvertToDoubleAndWarn(factionRewardValueString, "value",
                                                "OrganizerNodeSerialization on deserializeRewardForfeit ");

                result.addFactionRating(factionName, factionRewardValue);
            }
        }

        return result;
    }
}


//~ Formatted in DD Std on 13/08/28
