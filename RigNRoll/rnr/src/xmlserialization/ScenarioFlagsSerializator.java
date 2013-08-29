/*
 * @(#)ScenarioFlagsSerializator.java   13/08/28
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
import rnr.src.rnrcore.XmlSerializable;
import rnr.src.rnrscenario.ScenarioFlagsManager;
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
public class ScenarioFlagsSerializator implements XmlSerializable {
    private static ScenarioFlagsSerializator instance = null;

    /**
     * Method description
     *
     *
     * @return
     */
    public static ScenarioFlagsSerializator getInstance() {
        if (instance == null) {
            instance = new ScenarioFlagsSerializator();
        }

        return instance;
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        serializeXML(ScenarioFlagsManager.getInstance(), stream);
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void loadFromNode(org.w3c.dom.Node node) {
        deserializeXML(new rnr.src.xmlutils.Node(node));
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
     * @return
     */
    public static String getNodeName() {
        return "scenario_flags";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void serializeXML(ScenarioFlagsManager value, PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        HashMap<String, Boolean> staticFlags = value.getStaticFlags();

        if ((staticFlags != null) && (!(staticFlags.isEmpty()))) {
            Set<Entry<String, Boolean>> staticFlagsEntries = staticFlags.entrySet();

            for (Map.Entry staticFlagInfo : staticFlagsEntries) {
                List attributes = Helper.createSingleAttribute("name", (String) staticFlagInfo.getKey());

                Helper.addAttribute("value", ((Boolean) staticFlagInfo.getValue()).booleanValue(), attributes);
                Helper.printClosedNodeWithAttributes(stream, "static_flag", attributes);
            }
        }

        HashMap timedFlags = value.getTimedFlags();

        if ((timedFlags != null) && (!(timedFlags.isEmpty()))) {
            Set timedFlagsEntries = timedFlags.entrySet();

            for (Map.Entry timedFlagInfo : timedFlagsEntries) {
                Helper.printOpenNodeWithAttributes(stream, "timed_flag",
                                                   Helper.createSingleAttribute("name",
                                                       (String) timedFlagInfo.getKey()));
                CoreTimeSerialization.serializeXML((CoreTime) timedFlagInfo.getValue(), stream);
                Helper.closeNode(stream, "timed_flag");
            }
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    public static void deserializeXML(rnr.src.xmlutils.Node node) {
        String errorMessage = "ScenarioFlagsSerializator on deserializeXML ";
        NodeList listStaticFlags = node.getNamedChildren("static_flag");

        if ((listStaticFlags != null) && (!(listStaticFlags.isEmpty()))) {
            for (rnr.src.xmlutils.Node staticNode : listStaticFlags) {
                String nameAttribute = staticNode.getAttribute("name");
                String valueAttributeString = staticNode.getAttribute("value");
                boolean value = Helper.ConvertToBooleanAndWarn(valueAttributeString, "value", errorMessage);

                if (nameAttribute == null) {
                    Log.error(errorMessage + " node " + "static_flag" + " has no attribute " + "name");
                }

                ScenarioFlagsManager.getInstance().setFlagValue(nameAttribute, value);
            }
        }

        NodeList listTimedFlags = node.getNamedChildren("timed_flag");

        if ((listTimedFlags == null) || (listTimedFlags.isEmpty())) {
            return;
        }

        for (rnr.src.xmlutils.Node timedNode : listTimedFlags) {
            String nameAttribute = timedNode.getAttribute("name");

            if (nameAttribute == null) {
                Log.error(errorMessage + " node " + "timed_flag" + " has no attribute " + "name");
            }

            NodeList coreTimeNodeList = timedNode.getNamedChildren(CoreTimeSerialization.getNodeName());

            if ((coreTimeNodeList == null) || (coreTimeNodeList.isEmpty())) {
                Log.error(errorMessage + " node " + "timed_flag" + " has no child nodes "
                          + CoreTimeSerialization.getNodeName());
            }

            rnr.src.xmlutils.Node coreTimeNode = coreTimeNodeList.get(0);
            CoreTime finishTime = CoreTimeSerialization.deserializeXML(coreTimeNode);

            if (finishTime == null) {
                Log.error(errorMessage + " node " + "timed_flag" + " has bad child node "
                          + CoreTimeSerialization.getNodeName());
            }

            ScenarioFlagsManager.getInstance().setFlagValueTimed(nameAttribute, finishTime);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
