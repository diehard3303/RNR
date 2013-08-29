/*
 * @(#)ScenarioMissions.java   13/08/26
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


package rnr.src.rnrorg;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rnr.src.rnrcore.eng;
import rnr.src.scenarioXml.XmlDocument;
import rnr.src.scenarioXml.XmlFilter;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

import java.util.HashMap;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class ScenarioMissions {

    /** Field description */
    public static final String TOP = "scenario_missions";

    /** Field description */
    public static final String TAG_ELEMENT = "element";

    /** Field description */
    public static final String MISSION_NAME = "missionname";

    /** Field description */
    public static final String ORG_NAME = "org";

    /** Field description */
    public static final String POINT_NAME = "point";

    /** Field description */
    public static final String NAME = "name";

    /** Field description */
    public static final String MOVE_TIME = "move_time";

    /** Field description */
    public static final String NEED_FINISH_ICON = "need_finish_icon";

    /** Field description */
    public static final String TIME_ON_MISSION = "time";
    private static ScenarioMissions instance = null;
    @SuppressWarnings("unchecked")
    private final HashMap<String, ScenarioMissionItem> scenariomissions = new HashMap<String, ScenarioMissionItem>();

    private ScenarioMissions() {
        read();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static ScenarioMissions getInstance() {
        if (null == instance) {
            instance = new ScenarioMissions();
        }

        return instance;
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public ScenarioMissionItem get(String name) {
        if (!(this.scenariomissions.containsKey(name))) {
            return null;
        }

        return (this.scenariomissions.get(name));
    }

    @SuppressWarnings("rawtypes")
    private void read() {
        Vector filenames = new Vector();

        if (!(eng.noNative)) {
            eng.getFilesAllyed("scenario_missions", filenames);
        }

        Vector _names = filenames;

        for (String name : _names) {
            try {
                read(name);
            } catch (Exception e) {
                eng.err(e.toString());
            }
        }
    }

    private void read(String filename) throws IOException {
        XmlDocument xml = new XmlDocument(filename);
        Document doc = xml.getContent();
        Node top = doc.getFirstChild();

        if (top.getNodeName().compareToIgnoreCase("scenario_missions") != 0) {
            return;
        }

        NodeList org_elements = doc.getElementsByTagName("element");
        XmlFilter filter = new XmlFilter(org_elements);
        Node node = filter.nextElement();

        while (null != node) {
            NamedNodeMap attributes = node.getAttributes();
            String mission_name = getAttribute("element", attributes, "missionname");
            String org_name = getAttribute("element", attributes, "org");
            String point_name = getAttribute("element", attributes, "point");
            String name = getAttribute("element", attributes, "name");
            double time_on_mission = getDoubleAttribute("element", attributes, "time", false);
            boolean move_time = getBooleanAttribute("element", attributes, "move_time", false);
            boolean need_finish_icon = getBooleanAttributeDefault("element", attributes, "need_finish_icon", true);

            this.scenariomissions.put(name,
                                      new ScenarioMissionItem(mission_name, org_name, point_name, time_on_mission,
                                          move_time, need_finish_icon));
            node = filter.nextElement();
        }
    }

    private String getAttribute(String nodename, NamedNodeMap attributes, String name) {
        Node val = attributes.getNamedItem(name);

        if (null == val) {
            eng.log("Parsing organaser errorr. Node " + nodename + " has no attribute " + name);

            return null;
        }

        return val.getTextContent();
    }

    private double getDoubleAttribute(String nodename, NamedNodeMap attributes, String name, boolean make_warning) {
        Node val = attributes.getNamedItem(name);

        if (null == val) {
            if (make_warning) {
                eng.log("Parsing organaser errorr. Node " + nodename + " has no attribute " + name);
            }

            return -1.0D;
        }

        String textval = val.getTextContent();
        double value = -1.0D;

        try {
            value = Double.parseDouble(textval);
        } catch (NumberFormatException e) {
            eng.log("Parsing organaser errorr. Node " + nodename + " has no bad attribute " + name + " with value "
                    + textval);
        }

        return value;
    }

    private boolean getBooleanAttribute(String nodename, NamedNodeMap attributes, String name, boolean make_warning) {
        Node val = attributes.getNamedItem(name);

        if (null == val) {
            if (make_warning) {
                eng.log("Parsing organaser errorr. Node " + nodename + " has no attribute " + name);
            }

            return false;
        }

        String textval = val.getTextContent();
        boolean value = false;

        try {
            value = Boolean.parseBoolean(textval);
        } catch (NumberFormatException e) {
            eng.log("Parsing organaser errorr. Node " + nodename + " has no bad attribute " + name + " with value "
                    + textval);
        }

        return value;
    }

    private boolean getBooleanAttributeDefault(String nodename, NamedNodeMap attributes, String name,
            boolean default_value) {
        Node val = attributes.getNamedItem(name);

        if (null == val) {
            return default_value;
        }

        String textval = val.getTextContent();
        boolean value = default_value;

        try {
            value = Boolean.parseBoolean(textval);
        } catch (NumberFormatException e) {
            eng.log("Parsing organaser errorr. Node " + nodename + " has no bad attribute " + name + " with value "
                    + textval);
        }

        return value;
    }
}


//~ Formatted in DD Std on 13/08/26
