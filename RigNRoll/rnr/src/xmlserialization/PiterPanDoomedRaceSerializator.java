/*
 * @(#)PiterPanDoomedRaceSerializator.java   13/08/28
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

import rnr.src.players.RaceTrajectory;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.PiterPandoomedrace;
import rnr.src.rnrscenario.controllers.ScenarioHost;
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
    scenarioStage = 5,
    fieldWithDesiredStage = ""
)
public class PiterPanDoomedRaceSerializator implements IXMLSerializable {
    private static final RaceSerializator customDataSaver;

    static {
        customDataSaver = new RaceSerializator(
            PiterPanDoomedRaceSerializator.class.getAnnotation(ScenarioClass.class).scenarioStage());
    }

    private PiterPandoomedrace serializationTarget = null;
    private final ScenarioHost host;

    /**
     * Constructs ...
     *
     *
     * @param scenarioHost
     */
    public PiterPanDoomedRaceSerializator(ScenarioHost scenarioHost) {
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
    public PiterPanDoomedRaceSerializator(PiterPandoomedrace value, ScenarioHost scenarioHost) {
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
        return "piterpandommedrace";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(PiterPandoomedrace value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("finished", value.isRaceFinished());

        Helper.addAttribute("racename", value.getRaceName(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);
        customDataSaver.serializeXML(value, stream);
        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param host
     */
    public static void deserializeXML(Node node, ScenarioHost host) {
        String finishedString = node.getAttribute("finished");
        String raceName = node.getAttribute("racename");

        if ((null == raceName) || (0 == "null".compareTo(raceName))) {
            Log.error("ERRORR. PiterPanDoomedRaceSerializator on deserializeXML has no attribute with name racename");
            raceName = "pp_race";
        }

        boolean finished = Helper.ConvertToBooleanAndWarn(finishedString, "finished",
                               "PiterPanDoomedRaceSerializator on deserializeXML");

        try {
            RaceTrajectory.createTrajectoryForRaceWithPP();

            PiterPandoomedrace result = new PiterPandoomedrace(raceName, finished, host);
            Node raceNode = node.getNamedChild(customDataSaver.getNodeName());

            if (null == raceNode) {
                Log.error("ERRORR. PiterPanDoomedRaceSerializator on deserializeXML has no child node with name  "
                          + customDataSaver.getNodeName());
            }

            customDataSaver.deserializeXML(result, raceNode);
        } catch (IllegalStateException e) {
            Log.error("ERRORR. Bad save detected: trying to recover game state. Error message: " + e.getMessage());
        }
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
