/*
 * @(#)ChaseKohSerializator.java   13/08/28
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
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.ChaseKoh;
import rnr.src.rnrscenario.controllers.ChaseKoh.TooLong;
import rnr.src.rnrscenario.controllers.ChaseKoh.WantBlowCar;
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
    scenarioStage = 15,
    fieldWithDesiredStage = ""
)
public class ChaseKohSerializator implements IXMLSerializable {
    private ChaseKoh slave = null;
    private final ScenarioHost host;

    /**
     * Constructs ...
     *
     *
     * @param value
     */
    public ChaseKohSerializator(ChaseKoh value) {
        this.slave = value;
        this.host = null;
    }

    /**
     * Constructs ...
     *
     *
     * @param host
     */
    public ChaseKohSerializator(ScenarioHost host) {
        this.host = host;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "chasekoh";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings("unchecked")
    public static void serializeXML(ChaseKoh value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("toolongtime",
                                                    value.getTooLong().getTime());

        Helper.addAttribute("_last_chance_failed", value.is_last_chance_failed(), attributes);
        Helper.addAttribute("chase_started", value.isChase_started(), attributes);
        Helper.addAttribute("haswantblowanimation", value.getWant_blow_car() != null, attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);

        actorveh carkoh = value.getCarkohForShootAnimationSerialization();

        if (null != carkoh) {
            Helper.openNode(stream, "kohcarforshootanimation");
            ActorVehSerializator.serializeXML(carkoh, stream);
            Helper.closeNode(stream, "kohcarforshootanimation");
        }

        aiplayer kohPlayer = value.getKoh_chase_player();

        if (null != kohPlayer) {
            Helper.openNode(stream, "koh_chase_player");
            AIPlayerSerializator.serializeXML(kohPlayer, stream);
            Helper.closeNode(stream, "koh_chase_player");
        }

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
        String errorMessage = "ChaseKohSerializator on deserializeXML ";
        String timeChaseString = node.getAttribute("toolongtime");
        String lastChanceFailedString = node.getAttribute("_last_chance_failed");
        String chaseStartedString = node.getAttribute("chase_started");
        String hasBlowAnimationString = node.getAttribute("haswantblowanimation");
        double timeChaseValue = Helper.ConvertToDoubleAndWarn(timeChaseString, "toolongtime", errorMessage);
        boolean lastChanceFailedValue = Helper.ConvertToBooleanAndWarn(lastChanceFailedString, "_last_chance_failed",
                                            errorMessage);
        boolean chaseStartedValue = Helper.ConvertToBooleanAndWarn(chaseStartedString, "chase_started", errorMessage);
        boolean hasBlowAnimationValue = Helper.ConvertToBooleanAndWarn(hasBlowAnimationString, "haswantblowanimation",
                                            errorMessage);
        aiplayer player = null;
        actorveh kohCar = null;
        Node kohPlayer = node.getNamedChild("koh_chase_player");
        Node kohCarNode = node.getNamedChild("kohcarforshootanimation");

        if (null != kohPlayer) {
            Node playerNode = kohPlayer.getNamedChild(AIPlayerSerializator.getNodeName());

            if (null == playerNode) {
                Log.error(errorMessage + "has no child node with name " + AIPlayerSerializator.getNodeName()
                          + " in node named " + "koh_chase_player");
            } else {
                player = AIPlayerSerializator.deserializeXML(playerNode);
            }
        }

        if (null != kohCarNode) {
            Node carNode = kohCarNode.getNamedChild(ActorVehSerializator.getNodeName());

            if (null == carNode) {
                Log.error(errorMessage + "has no child node with name " + ActorVehSerializator.getNodeName()
                          + " in node named " + "kohcarforshootanimation");
            } else {
                kohCar = ActorVehSerializator.deserializeXML(carNode);
            }
        }

        ChaseKoh result = new ChaseKoh(host, true);

        result.getTooLong().setTime(timeChaseValue);
        result.set_last_chance_failed(lastChanceFailedValue);
        result.setChase_started(chaseStartedValue);

        if (hasBlowAnimationValue) {
            result.setWant_blow_car(new ChaseKoh.WantBlowCar());
        }

        result.setKoh_chase_player(player);
        result.setCarkohForShootAnimationSerialization(kohCar);
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
        serializeXML(this.slave, stream);
    }
}


//~ Formatted in DD Std on 13/08/28
