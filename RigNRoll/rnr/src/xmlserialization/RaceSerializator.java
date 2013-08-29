/*
 * @(#)RaceSerializator.java   13/08/28
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

import rnr.src.players.actorveh;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.RACErace_state_single;
import rnr.src.rnrscenario.controllers.RACErace_states;
import rnr.src.rnrscenario.controllers.RACEspace;
import rnr.src.rnrscenario.controllers.RACEspace_conditions;
import rnr.src.rnrscenario.controllers.RaceFailCondition;
import rnr.src.scenarioUtils.Pair;
import rnr.src.xmlserialization.nxs.SerializatorOfAnnotated;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

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
@ScenarioClass(
    scenarioStage = -1,
    fieldWithDesiredStage = "scenarioStage"
)
public class RaceSerializator {
    private final int scenarioStage;

    /**
     * Constructs ...
     *
     *
     * @param desiredScenarioStage
     */
    public RaceSerializator(int desiredScenarioStage) {
        this.scenarioStage = desiredScenarioStage;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getNodeName() {
        return "racespace";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void serializeXML(RACEspace value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("raceFSMstate", value.getRaceFSMstate());

        Helper.addAttribute("race_condition__condition", value.getConditions().getCondition(), attributes);
        Helper.addAttribute("timeElapsed", value.getStates().getTimeElapsed(), attributes);
        Helper.addAttribute("anyonfinish", value.getStates().isAnyonfinish(), attributes);
        Helper.addAttribute("lastplace", value.getStates().getLastplace(), attributes);
        Helper.addAttribute("statesucceded", value.getStates().getStatesucceded(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);
        SerializatorOfAnnotated.getInstance().saveState(stream, value.getFailDetector());

        HashMap<rnr.src.rnrscenario.controllers.actorveh, RACErace_state_single> participants =
            value.getStates().getParticipantsAllData();

        if ((null == participants) || (participants.isEmpty())) {
            Log.error("ERRORR. RaceSerializator on serializeXML has null or empty participants.");
        } else {
            Helper.openNode(stream, "participants");

            Set set = participants.entrySet();

            for (Map.Entry entry : set) {
                ListElementSerializator.serializeXMLListelementOpen(stream);
                ActorVehSerializator.serializeXML((actorveh) entry.getKey(), stream);

                List singleDataAttributes = Helper.createSingleAttribute("finished",
                                                ((RACErace_state_single) entry.getValue()).isFinished());

                Helper.addAttribute("place", ((RACErace_state_single) entry.getValue()).getPlace(),
                                    singleDataAttributes);
                Helper.addAttribute("distance", ((RACErace_state_single) entry.getValue()).getDistance(),
                                    singleDataAttributes);
                Helper.printClosedNodeWithAttributes(stream, "participantssingledata", singleDataAttributes);
                ListElementSerializator.serializeXMLListelementClose(stream);
            }

            Helper.closeNode(stream, "participants");
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param race
     * @param node
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void deserializeXML(RACEspace race, Node node) {
        String errorMessage = "RaceSerializator on deserializeXML ";
        String raceFsmStateString = node.getAttribute("raceFSMstate");
        String raceConditionsConditionString = node.getAttribute("race_condition__condition");
        String timeElapsedString = node.getAttribute("timeElapsed");
        String anyOnFinishString = node.getAttribute("anyonfinish");
        String lastPlaceString = node.getAttribute("lastplace");
        String stateSuccededString = node.getAttribute("statesucceded");
        int raceFsmStateValue = Helper.ConvertToIntegerAndWarn(raceFsmStateString, "raceFSMstate", errorMessage);
        int raceConditionsConditionValue = Helper.ConvertToIntegerAndWarn(raceConditionsConditionString,
                                               "race_condition__condition", errorMessage);
        double timeElapsedValue = Helper.ConvertToDoubleAndWarn(timeElapsedString, "timeElapsed", errorMessage);
        boolean anyOnFinishValue = Helper.ConvertToBooleanAndWarn(anyOnFinishString, "anyonfinish", errorMessage);
        int lastPlaceValue = Helper.ConvertToIntegerAndWarn(lastPlaceString, "lastplace", errorMessage);
        int stateSuccededValue = Helper.ConvertToIntegerAndWarn(stateSuccededString, "statesucceded", errorMessage);

        race.setRaceFSMstate(raceFsmStateValue);
        race.getConditions().setCondition(raceConditionsConditionValue);
        race.getStates().setTimeElapsed(timeElapsedValue);
        race.getStates().setAnyonfinish(anyOnFinishValue);
        race.getStates().setLastplace(lastPlaceValue);
        race.getStates().setStatesucceded(stateSuccededValue);

        RaceFailCondition raceFailDetector = (RaceFailCondition) SerializatorOfAnnotated.getInstance().loadStateOrNull(
                                                 node.getNamedChild("RaceFailCondition"));

        if (null != raceFailDetector) {
            race.setFailDetector(raceFailDetector);
        }

        HashMap participants = new HashMap();
        Node nodeParticipants = node.getNamedChild("participants");

        if (null == nodeParticipants) {
            Log.error("ERRORR. RaceSerializator on deserializeXML has no child node with name participants");
        } else {
            NodeList listParticipants = nodeParticipants.getNamedChildren(ListElementSerializator.getNodeName());

            if ((null == listParticipants) || (listParticipants.isEmpty())) {
                Log.error("ERRORR. RaceSerializator on deserializeXML has no elementt nodes with name "
                          + ListElementSerializator.getNodeName() + " in node named " + "participants");
            } else {
                for (Node element : listParticipants) {
                    Node actorNode = element.getNamedChild(ActorVehSerializator.getNodeName());
                    Node singleDataNode = element.getNamedChild("participantssingledata");

                    if (null == actorNode) {
                        Log.error("ERRORR. RaceSerializator on deserializeXML has no child node with name "
                                  + ActorVehSerializator.getNodeName());
                    }

                    RACErace_state_single singleData = new RACErace_state_single(this.scenarioStage);

                    if (null == singleDataNode) {
                        Log.error(
                            "ERRORR. RaceSerializator on deserializeXML has no child node with name participantssingledata");
                    } else {
                        String sdFinisgedString = singleDataNode.getAttribute("finished");
                        boolean sdFinishedValue = Helper.ConvertToBooleanAndWarn(sdFinisgedString, "finished",
                                                      errorMessage);

                        singleData.setFinished(sdFinishedValue);

                        String sdPlaceString = singleDataNode.getAttribute("place");
                        int sdPlaceValue = Helper.ConvertToIntegerAndWarn(sdPlaceString, "place", errorMessage);

                        singleData.setPlace(sdPlaceValue);

                        String sdDistanceString = singleDataNode.getAttribute("distance");
                        int sdDistanceValue = Helper.ConvertToIntegerAndWarn(sdDistanceString, "distance",
                                                  errorMessage);

                        singleData.setDistance(sdDistanceValue);

                        actorveh car = ActorVehSerializator.deserializeXML(actorNode);

                        participants.put(car, singleData);
                    }
                }
            }
        }

        race.getStates().setParticipantsAllData(participants);
    }
}


//~ Formatted in DD Std on 13/08/28
