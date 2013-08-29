/*
 * @(#)MissionsPrioritySerialization.java   13/08/28
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

import rnr.src.rnrscenario.missions.PriorityTable;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

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
public class MissionsPrioritySerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "missionspriorities";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings("rawtypes")
    public static void serializeXML(PriorityTable value, PrintStream stream) {
        if (null == value) {
            return;
        }

        Helper.openNode(stream, getNodeName());

        Map<String, Integer> table = value.getPriorities();

        if ((null != table) && (!(table.isEmpty()))) {
            Set<Entry<String, Integer>> set = table.entrySet();

            for (Map.Entry entry : set) {
                ListElementSerializator.serializeXMLListelementOpen(stream);
                SimpleTypeSerializator.serializeXMLString((String) entry.getKey(), stream);
                SimpleTypeSerializator.serializeXMLInteger(((Integer) entry.getValue()).intValue(), stream);
                ListElementSerializator.serializeXMLListelementClose(stream);
            }
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param table
     * @param node
     */
    public static void deserializeXML(PriorityTable table, Node node) {
        NodeList listElements = node.getNamedChildren(ListElementSerializator.getNodeName());

        if ((null != listElements) && (!(listElements.isEmpty()))) {
            for (Node element : listElements) {
                Node missionNameNode = element.getNamedChild(SimpleTypeSerializator.getNodeNameString());
                Node priorityNode = element.getNamedChild(SimpleTypeSerializator.getNodeNameInteger());
                String missionName = null;
                int priority = 0;

                if (null == missionNameNode) {
                    Log.error("MissionsPrioritySerialization in deserializeXML has no node named "
                              + SimpleTypeSerializator.getNodeNameString() + " in node named "
                              + ListElementSerializator.getNodeName());
                } else {
                    missionName = SimpleTypeSerializator.deserializeXMLString(missionNameNode);
                }

                if (null == priorityNode) {
                    Log.error("MissionsPrioritySerialization in deserializeXML has no node named "
                              + SimpleTypeSerializator.getNodeNameInteger() + " in node named "
                              + ListElementSerializator.getNodeName());
                } else {
                    priority = SimpleTypeSerializator.deserializeXMLInteger(priorityNode);
                }

                table.registerMissionPriority(missionName, priority);
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/28
