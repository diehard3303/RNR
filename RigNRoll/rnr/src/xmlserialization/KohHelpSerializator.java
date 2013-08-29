/*
 * @(#)KohHelpSerializator.java   13/08/28
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
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.KohHelpManage;
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
    scenarioStage = 0,
    fieldWithDesiredStage = ""
)
public class KohHelpSerializator implements IXMLSerializable {
    private KohHelpManage slave = null;
    private final ScenarioHost scenarioHost;

    /**
     * Constructs ...
     *
     *
     * @param host
     */
    public KohHelpSerializator(ScenarioHost host) {
        assert(null != host) : "'host' must be non-null reference";
        this.slave = null;
        this.scenarioHost = host;
    }

    /**
     * Constructs ...
     *
     *
     * @param value
     * @param host
     */
    public KohHelpSerializator(KohHelpManage value, ScenarioHost host) {
        assert(null != host) : "'host' must be non-null reference";
        this.scenarioHost = host;
        this.slave = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "kohhelp";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(KohHelpManage value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("current_scene", value.getCurrent_scene());

        Helper.addAttribute("f_makeKoh", value.isF_makeKoh(), attributes);
        Helper.addAttribute("f_wasMadeKoh", value.isF_wasMadeKoh(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);

        CoreTime time = value.getTimeKohOrdered();

        if (null != time) {
            Helper.openNode(stream, "timeKohOrdered");
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "timeKohOrdered");
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
        String errorMessage = "KohHelpSerializator in deserializeXML ";
        String currentSceneString = node.getAttribute("current_scene");
        String toMakeKohString = node.getAttribute("f_makeKoh");
        String wasMakeKohString = node.getAttribute("f_wasMadeKoh");
        int currentSceneValue = Helper.ConvertToIntegerAndWarn(currentSceneString, "current_scene", errorMessage);
        boolean toMakeKohValue = Helper.ConvertToBooleanAndWarn(toMakeKohString, "f_makeKoh", errorMessage);
        boolean wasMakeKohValue = Helper.ConvertToBooleanAndWarn(wasMakeKohString, "f_wasMadeKoh", errorMessage);
        CoreTime timeKohOrdered = null;
        Node timeNode = node.getNamedChild("timeKohOrdered");

        if (null != timeNode) {
            Node itemNode = timeNode.getNamedChild(CoreTimeSerialization.getNodeName());

            if (null != itemNode) {
                timeKohOrdered = CoreTimeSerialization.deserializeXML(itemNode);
            } else {
                Log.error("ERRORR. KohHelpSerializator in deserializeXML has no node "
                          + CoreTimeSerialization.getNodeName() + " in node " + "timeKohOrdered");
            }
        }

        if (null == KohHelpManage.getInstance()) {
            KohHelpManage.constructSingleton(host);
        }

        KohHelpManage value = KohHelpManage.getInstance();

        value.setCurrent_scene(currentSceneValue);
        value.setF_makeKoh(toMakeKohValue);
        value.setF_wasMadeKoh(wasMakeKohValue);
        value.setTimeKohOrdered(timeKohOrdered);
        value.prepareKohDeserealize();
        value.start();
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void deSerialize(Node node) {
        deserializeXML(node, this.scenarioHost);
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
