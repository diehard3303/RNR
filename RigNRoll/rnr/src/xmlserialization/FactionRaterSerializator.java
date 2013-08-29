/*
 * @(#)FactionRaterSerializator.java   13/08/28
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
import rnr.src.rnrrating.FactionRater;
import rnr.src.scenarioUtils.Pair;
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
public class FactionRaterSerializator implements XmlSerializable {
    private static FactionRaterSerializator instance = null;

    /**
     * Method description
     *
     *
     * @return
     */
    public static FactionRaterSerializator getInstance() {
        if (instance == null) {
            instance = new FactionRaterSerializator();
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
        serializeXML(stream);
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void loadFromNode(org.w3c.dom.Node node) {
        deserialize(new rnr.src.xmlutils.Node(node));
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
        return "faction_rater";
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void serializeXML(PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        List<Pair<String, String>> factionsData = FactionRater.getSerializationData();

        if ((factionsData != null) && (!(factionsData.isEmpty()))) {
            for (Pair factionSingleData : factionsData) {
                List factionAttributes = Helper.createSingleAttribute("name", (String) factionSingleData.getFirst());

                Helper.addAttribute("value", (String) factionSingleData.getSecond(), factionAttributes);
                Helper.printClosedNodeWithAttributes(stream, "faction", factionAttributes);
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
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void deserialize(rnr.src.xmlutils.Node node) {
        NodeList factionDataNodes = node.getNamedChildren("faction");
        List factionsData = new ArrayList();

        if ((factionDataNodes != null) && (!(factionDataNodes.isEmpty()))) {
            for (rnr.src.xmlutils.Node singleFactionNode : factionDataNodes) {
                String nameAttributeString = singleFactionNode.getAttribute("name");

                if (nameAttributeString == null) {
                    Log.error("FactionRaterSerializator deserialize. Node faction has no attribute name");
                }

                String valueAttributeString = singleFactionNode.getAttribute("value");

                if (valueAttributeString == null) {
                    Log.error("FactionRaterSerializator deserialize. Node faction has no attribute value");
                }

                factionsData.add(new Pair(nameAttributeString, valueAttributeString));
            }
        }

        FactionRater.setSerializationData(factionsData);
    }
}


//~ Formatted in DD Std on 13/08/28
