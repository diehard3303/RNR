/*
 * @(#)MissionsLogSerialization.java   13/08/28
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
import rnr.src.rnrscenario.missions.requirements.MissionsLog;
import rnr.src.rnrscenario.missions.requirements.MissionsLog.Event;
import rnr.src.rnrscenario.missions.requirements.MissionsLog.MissionState;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.Collection;
import java.util.HashMap;
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
public class MissionsLogSerialization implements XmlSerializable {
    private static MissionsLogSerialization instance = new MissionsLogSerialization();

    /**
     * Method description
     *
     *
     * @return
     */
    public static MissionsLogSerialization getInstance() {
        return instance;
    }

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
     * @param stream
     */
    @Override
    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        serializeXML(MissionsLog.getInstance(), stream);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "missionslog";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings("rawtypes")
    public static void serializeXML(MissionsLog value, PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        Map<String, MissionState> states = value.getMissionsEvents();
        Set<Entry<String, MissionState>> set = states.entrySet();

        for (Map.Entry entry : set) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            SimpleTypeSerializator.serializeXMLString((String) entry.getKey(), stream);

            Collection events = ((MissionsLog.MissionState) entry.getValue()).getOccuredEvents();

            ListElementSerializator.serializeXMLListelementOpen(stream);

            for (MissionsLog.Event event : events) {
                SimpleTypeSerializator.serializeXMLString(event.toString(), stream);
            }

            ListElementSerializator.serializeXMLListelementClose(stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
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
    public static void deserializeXML(rnr.src.xmlutils.Node node) {
        MissionsLog manager = MissionsLog.getInstance();
        NodeList listStates = node.getNamedChildren(ListElementSerializator.getNodeName());
        Map states = new HashMap(listStates.size());

        for (rnr.src.xmlutils.Node stateNode : listStates) {
            rnr.src.xmlutils.Node missionNameNode = stateNode.getNamedChild(SimpleTypeSerializator.getNodeNameString());
            String missionName = null;

            if (null == missionNameNode) {
                Log.error("MissionsLogSerialization in deserializeXML has no named node "
                          + SimpleTypeSerializator.getNodeNameString() + " in node named "
                          + ListElementSerializator.getNodeName());
            } else {
                missionName = SimpleTypeSerializator.deserializeXMLString(missionNameNode);
            }

            MissionsLog.MissionState missionState = new MissionsLog.MissionState();
            rnr.src.xmlutils.Node eventNode = stateNode.getNamedChild(ListElementSerializator.getNodeName());

            if (null != eventNode) {
                NodeList listEvents = eventNode.getNamedChildren(SimpleTypeSerializator.getNodeNameString());

                for (rnr.src.xmlutils.Node event : listEvents) {
                    String eventString = SimpleTypeSerializator.deserializeXMLString(event);
                    MissionsLog.Event eventValue = MissionsLog.Event.valueOf(eventString);

                    missionState.eventHappen(eventValue);
                }
            }

            states.put(missionName, missionState);
        }

        manager.setMissionsEvents(states);
    }
}


//~ Formatted in DD Std on 13/08/28
