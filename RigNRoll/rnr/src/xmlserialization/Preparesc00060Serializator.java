/*
 * @(#)Preparesc00060Serializator.java   13/08/28
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
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.ScenarioHost;
import rnr.src.rnrscenario.controllers.preparesc00060;
import rnr.src.scenarioUtils.Pair;
import rnr.src.xmlutils.Node;

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
@ScenarioClass(
    scenarioStage = 1,
    fieldWithDesiredStage = ""
)
public class Preparesc00060Serializator implements IXMLSerializable {
    private final preparesc00060 serializationTarget;
    private final ScenarioHost host;

    /**
     * Constructs ...
     *
     *
     * @param scenarioHost
     */
    public Preparesc00060Serializator(ScenarioHost scenarioHost) {
        assert(null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.serializationTarget = null;
        this.host = scenarioHost;
    }

    /**
     * Constructs ...
     *
     *
     * @param value
     * @param scenarioHost
     */
    public Preparesc00060Serializator(preparesc00060 value, ScenarioHost scenarioHost) {
        assert(null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.serializationTarget = value;
        this.host = scenarioHost;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "Preparesc00060";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(preparesc00060 value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("alreadyCalled", value.isAlreadyCalled());

        Helper.addAttribute("checkecondition", value.isCheckecondition(), attributes);
        Helper.addAttribute("counterlimit2Succeded", value.isCounterTriggered(), attributes);
        Helper.addAttribute("counterTurnedOn", value.isCounterTurnedOn(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);

        CoreTime time = value.getQuestFailureTime();

        if (null != time) {
            Helper.openNode(stream, "quest_failure_time");
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "quest_failure_time");
        }

        time = value.getQuestStartTime();

        if (null != time) {
            Helper.openNode(stream, "quest_start_time");
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "quest_start_time");
        }

        actorveh banditCar = value.getBanditsCar();

        if (null != banditCar) {
            Helper.openNode(stream, "banditsCar");
            ActorVehSerializator.serializeXML(banditCar, stream);
            Helper.closeNode(stream, "banditsCar");
        }

        Helper.closeNode(stream, getNodeName());
    }

    private static preparesc00060 deserializeXML(Node node, ScenarioHost host) {
        String alreadyCalledString = node.getAttribute("alreadyCalled");
        String checkConditionString = node.getAttribute("checkecondition");
        String counterLimitSucceededString = node.getAttribute("counterlimit2Succeded");
        String isCounterTurnedOnString = node.getAttribute("counterTurnedOn");
        boolean alreadyCalledValue = Helper.ConvertToBooleanAndWarn(alreadyCalledString, "alreadyCalled",
                                         "Preparesc00060Serializator on deserializeXML ");
        boolean checkConditionValue = Helper.ConvertToBooleanAndWarn(checkConditionString, "checkecondition",
                                          "Preparesc00060Serializator on deserializeXML ");
        boolean counterLimitSucceededValue = Helper.ConvertToBooleanAndWarn(counterLimitSucceededString,
                                                 "counterlimit2Succeded",
                                                 "Preparesc00060Serializator on deserializeXML ");
        boolean isCounterTurnedOnValue = Helper.ConvertToBooleanAndWarn(isCounterTurnedOnString, "counterTurnedOn",
                                             "Preparesc00060Serializator on deserializeXML ");
        actorveh banditCar = null;
        Node banditCarNode = node.getNamedChild("banditsCar");

        if (null != banditCarNode) {
            Node actorVehNode = banditCarNode.getNamedChild(ActorVehSerializator.getNodeName());

            if (null != actorVehNode) {
                banditCar = ActorVehSerializator.deserializeXML(actorVehNode);
            }
        }

        CoreTime questFailureTime = null;
        CoreTime questStartTime = null;
        Node failureTimeNode = node.getNamedChild("quest_failure_time");
        Node startTimeNode = node.getNamedChild("quest_start_time");

        if (null != failureTimeNode) {
            Node itemNode = failureTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());

            if (null != itemNode) {
                questFailureTime = CoreTimeSerialization.deserializeXML(itemNode);
            } else {
                Log.error("ERRORR. Preparesc00060Serializator in deserializeXML has no node "
                          + CoreTimeSerialization.getNodeName() + " in node " + "quest_failure_time");
            }
        }

        if (null != startTimeNode) {
            Node itemNode = startTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());

            questStartTime = (null == itemNode)
                             ? new CoreTime()
                             : CoreTimeSerialization.deserializeXML(itemNode);
        } else {
            questStartTime = new CoreTime();
        }

        if ((isCounterTurnedOnValue) && (null == questFailureTime)) {
            questFailureTime = new CoreTime();
        }

        preparesc00060 result = new preparesc00060(host, true);

        result.setAlreadyCalled(alreadyCalledValue);
        result.setBanditsCar(banditCar);
        result.setCheckecondition(checkConditionValue);
        result.setQuestFailureTime(questFailureTime);
        result.setQuestStartTime(questStartTime);
        result.setCounterTriggered(counterLimitSucceededValue);
        result.setCounterTurnedOn(isCounterTurnedOnValue);
        result.start();

        return result;
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void deSerialize(Node node) {
        deserializeXML(node, this.host);
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    public void serialize(PrintStream stream) {
        serializeXML(this.serializationTarget, stream);
    }
}


//~ Formatted in DD Std on 13/08/28
