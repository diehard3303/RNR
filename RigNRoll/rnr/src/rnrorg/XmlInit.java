/*
 * @(#)XmlInit.java   13/08/26
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

import rnr.src.rnrcore.Log;
import rnr.src.rnrcore.eng;
import rnr.src.scenarioXml.XmlDocument;
import rnr.src.scenarioXml.XmlFilter;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class XmlInit {
    private static final String ERROR_DATA = "ERRORR";
    private static final String DEFAULT_IMPORTANCE = "false";
    private static final String DEFAULT_REWARD_FORFEIT = "";
    private static final String DATA_TIME_URGENT = "urgent";

    /** Field description */
    public static final String[] TAG_FAIL = { "fail_timeout_pickup", "fail_timeout_complete", "fail_damaged",
            "decline" };
    private static boolean is_read = false;
    private final String TOP = "org";
    private final String TAG_ELEMENT = "element";
    private final String TAG_REWARD = "reward";
    private final String TAG_FORFEIT = "forfeit";
    private final String TAG_START = "start";
    private final String TAG_FINISH = "finish";
    private final String TAG_PERIODS = "periods";
    private final String ATTR_NAME = "name";
    private final String ATTR_DESCRIPTION = "description";
    private final String ATTR_TYPE = "type";
    private final String ATTR_CUSTOMERNPC = "customer_npc";
    private final String ATTR_CUSTOMER = "customer";
    private final String ATTR_IMPORTANT = "importance";
    private final String ATTR_MONEY = "money";
    private final String ATTR_RATING = "rating";
    private final String ATTR_RANK = "rank";
    private final String NODE_FACTION = "faction";
    private final String ATTR_FACTION_NAME = "name";
    private final String ATTR_FACTION_VALUE = "rating";
    private final String ATTR_OUTOFTIMEQUESTITEM = "outoftime_questitem";
    private final String ATTR_OUTOFTIMEMISSION = "outoftime_mission";
    private final String ATTR_TIMECOEF = "timecoef";

    /**
     * Method description
     *
     */
    @SuppressWarnings("rawtypes")
    public static void read() {
        if (is_read) {
            return;
        }

        is_read = true;

        XmlInit scaner = new XmlInit();
        Vector filenames = new Vector();

        scaner.getClass();
        eng.getFilesAllyed("org", filenames);

        Vector _names = filenames;

        for (String name : _names) {
            scaner.scan(name);
        }
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        is_read = false;
    }

    /**
     * Method description
     *
     *
     * @param filename
     */
    public void scan(String filename) {
        try {
            new XmlInit().read(filename);
        } catch (Exception e) {}
    }

    private RewardForfeit buildRewardForfeit(org.w3c.dom.Node node) {
        if (node == null) {
            return null;
        }

        NamedNodeMap reward_attributes = node.getAttributes();
        String money = getAttribute(reward_attributes, "money", "");
        String rating = getAttribute(reward_attributes, "rating", "");
        String rank = getAttribute(reward_attributes, "rank", "");
        RewardForfeit rewardParams = new RewardForfeit(convertDouble(money, 1.0D), convertDouble(rating, 1.0D),
                                         convertDouble(rank, 1.0D));
        Node utilNode = new Node(node);
        NodeList listFactionRewards = utilNode.getNamedChildren("faction");

        if ((listFactionRewards != null) && (!(listFactionRewards.isEmpty()))) {
            for (Node factionRewardNode : listFactionRewards) {
                String factionName = factionRewardNode.getAttribute("name");

                if (factionName == null) {
                    Log.simpleMessage("Organizer XMLInit. Node faction has no attribute name");
                }

                String factionRewardValueString = factionRewardNode.getAttribute("rating");

                if (factionRewardValueString == null) {
                    Log.simpleMessage("Organizer XMLInit. Node faction has no attribute rating");
                }

                double factionRewardValue = convertDouble(factionRewardValueString, 0.0D);

                rewardParams.addFactionRating(factionName, factionRewardValue);
            }
        }

        return rewardParams;
    }

    void read(String filename) throws IOException {
        XmlDocument xml = new XmlDocument(filename);
        Document doc = xml.getContent();
        org.w3c.dom.Node top = doc.getFirstChild();

        if (top.getNodeName().compareToIgnoreCase("org") != 0) {
            return;
        }

        org.w3c.dom.NodeList org_elements = doc.getElementsByTagName("element");
        XmlFilter filter = new XmlFilter(org_elements);
        org.w3c.dom.Node node = filter.nextElement();

        while (null != node) {
            NamedNodeMap attributes = node.getAttributes();
            String name = getAttribute("element", attributes, "name");
            String description = getAttribute("description", attributes, "description");
            String type = getAttribute("description", attributes, "type");
            String customer_npc = null;
            String customer = null;
            int reward_flag = 0;
            int forfeit_flag = 0;
            RewardForfeit forfeitParams = null;
            RewardForfeit rewardParams = null;
            INPC iCustomer = null;
            MissionTime quest_item_time = null;
            MissionTime complete_time = null;
            journable startNote = null;
            journable successNote = null;
            journable[] failNote = null;
            IStoreorgelement.Type typeQuest = IStoreorgelement.Type.notype;

            if (null != type) {
                try {
                    typeQuest = IStoreorgelement.Type.valueOf(type);
                } catch (Exception e) {}
            }

            if (!(hasAttribute(attributes, "customer_npc"))) {
                customer = getAttribute("element", attributes, "customer");
            } else {
                customer_npc = getAttribute("element", attributes, "customer_npc");
            }

            if (null != customer) {
                iCustomer = new QuestCustomer(customer);
            } else if (null != customer_npc) {
                iCustomer = new QuestNPC(customer_npc);
            }

            String importance = getAttribute(attributes, "importance", "false");
            XmlFilter filter_rewards_forfeits = new XmlFilter(node.getChildNodes());
            org.w3c.dom.Node reward = filter_rewards_forfeits.nodeNameNext("reward");

            rewardParams = buildRewardForfeit(reward);

            if (rewardParams != null) {
                reward_flag = rewardParams.getFlag();
            }

            filter_rewards_forfeits.goOnStart();

            org.w3c.dom.Node forfeit = filter_rewards_forfeits.nodeNameNext("forfeit");

            forfeitParams = buildRewardForfeit(forfeit);

            if (forfeitParams != null) {
                forfeit_flag = forfeitParams.getFlag();
            }

            filter_rewards_forfeits.goOnStart();

            org.w3c.dom.Node start = filter_rewards_forfeits.nodeNameNext("start");

            if (null != start) {
                NamedNodeMap start_attributes = start.getAttributes();
                String journaldescription = getAttribute("start", start_attributes, "description");

                startNote = new journalelement(journaldescription, null);
            }

            filter_rewards_forfeits.goOnStart();

            org.w3c.dom.Node finish = filter_rewards_forfeits.nodeNameNext("finish");

            if (null != finish) {
                NamedNodeMap finish_attributes = finish.getAttributes();
                String journaldescription = getAttribute("finish", finish_attributes, "description");

                successNote = new journalelement(journaldescription, null);
            }

            failNote = new journable[TAG_FAIL.length];

            for (int i = 0; i < TAG_FAIL.length; ++i) {
                filter_rewards_forfeits.goOnStart();

                org.w3c.dom.Node fail = filter_rewards_forfeits.nodeNameNext(TAG_FAIL[i]);

                if (null != fail) {
                    NamedNodeMap fail_attributes = fail.getAttributes();
                    String journaldescription = getAttribute(TAG_FAIL[i], fail_attributes, "description");

                    failNote[i] = new journalelement(journaldescription, null);
                }
            }

            filter_rewards_forfeits.goOnStart();

            org.w3c.dom.Node periods = filter_rewards_forfeits.nodeNameNext("periods");

            while (null != periods) {
                NamedNodeMap periods_attributes = periods.getAttributes();

                if (hasAttribute(periods_attributes, "outoftime_questitem")) {
                    quest_item_time = new MissionTime();

                    String mode = getAttribute(periods_attributes, "outoftime_questitem", "ERRORR");

                    if (mode.compareTo("urgent") == 0) {
                        quest_item_time.makeUrgent();
                    } else if (hasAttribute(periods_attributes, "timecoef")) {
                        String time_coef = getAttribute(periods_attributes, "timecoef", "ERRORR");
                        double value = convertDouble(time_coef, 1.0D);

                        quest_item_time.setCoef(value);
                    }
                } else {
                    complete_time = new MissionTime();

                    String mode = getAttribute("periods", periods_attributes, "outoftime_mission");

                    if (mode.compareTo("urgent") == 0) {
                        complete_time.makeUrgent();
                    } else if (hasAttribute(periods_attributes, "timecoef")) {
                        String time_coef = getAttribute(periods_attributes, "timecoef", "ERRORR");
                        double value = convertDouble(time_coef, 1.0D);

                        complete_time.setCoef(value);
                    }
                }

                periods = filter_rewards_forfeits.nodeNameNext("periods");
            }

            Scorgelement orgElement = new Scorgelement(name, typeQuest, convertToBoolean(importance), reward_flag,
                                          rewardParams, forfeit_flag, forfeitParams, description, iCustomer,
                                          quest_item_time, complete_time, startNote, successNote, failNote);

            Organizers.getInstance().add(name, orgElement);
            node = filter.nextElement();
        }
    }

    private boolean hasAttribute(NamedNodeMap attributes, String name) {
        return (null != attributes.getNamedItem(name));
    }

    private String getAttribute(NamedNodeMap attributes, String name, String DefaultValue) {
        org.w3c.dom.Node val = attributes.getNamedItem(name);

        if (null == val) {
            return DefaultValue;
        }

        return val.getTextContent();
    }

    private String getAttribute(String nodename, NamedNodeMap attributes, String name) {
        org.w3c.dom.Node val = attributes.getNamedItem(name);

        if (null == val) {
            eng.log("Parsing organaser errorr. Node " + nodename + " has no attribute " + name);

            return null;
        }

        return val.getTextContent();
    }

    private boolean convertToBoolean(String value) {
        if (null == value) {
            return false;
        }

        return ((value.compareToIgnoreCase("on") == 0) || (value.compareToIgnoreCase("true") == 0));
    }

    private double convertDouble(String value, double defaultValue) {
        double resvalue = defaultValue;

        try {
            resvalue = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            eng.log("ERRORR orgamazer xml init. Attribute timecoef is not double. Mission initialising failes.");
        }

        return resvalue;
    }

    static org.w3c.dom.NodeList makeXpath(Object input, String expression) {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        try {
            XPathExpression expr = xpath.compile(expression);
            Object result = expr.evaluate(input, XPathConstants.NODESET);

            return ((org.w3c.dom.NodeList) result);
        } catch (Exception e) {
            System.err.print(e.toString());
        }

        return null;
    }

    static ArrayList<String> getMissionsBegunByChannel(org.w3c.dom.Node node) {
        ArrayList res = new ArrayList();
        org.w3c.dom.NodeList actonslist = makeXpath(node, "descendant::action[@startmission]");

        if (actonslist.getLength() != 0) {
            XmlFilter filteractions = new XmlFilter(actonslist);
            org.w3c.dom.Node nodeaction = filteractions.nextElement();

            while (null != nodeaction) {
                NamedNodeMap actionattr = nodeaction.getAttributes();
                org.w3c.dom.Node actionname = actionattr.getNamedItem("name");
                String mission_started = actionname.getNodeValue();

                if (mission_started.compareTo("this") != 0) {
                    res.add(mission_started);
                }

                nodeaction = filteractions.nextElement();
            }
        }

        return res;
    }

    static void xpathtest(String filename) throws IOException {
        FileOutputStream fileout = new FileOutputStream(filename + ".log");
        ObjectOutputStream stream = new ObjectOutputStream(fileout);
        XmlDocument xml = new XmlDocument(filename);
        Document doc = xml.getContent();
        org.w3c.dom.NodeList infochannelslist = makeXpath(doc, "//infochannel");
        XmlFilter filter = new XmlFilter(infochannelslist);
        org.w3c.dom.Node node = filter.nextElement();

        while (null != node) {
            org.w3c.dom.NodeList actonslist = makeXpath(node, "descendant::action[@startmission]");

            if (actonslist.getLength() != 0) {
                NamedNodeMap attrs = node.getAttributes();
                org.w3c.dom.Node channelname = attrs.getNamedItem("name");
                org.w3c.dom.Node channelresource = attrs.getNamedItem("resource");
                String message = "Information channel with name " + channelname.getNodeValue() + " and resource "
                                 + channelresource.getNodeValue() + " has startmission actions.\n";

                stream.writeObject(message);

                XmlFilter filteractions = new XmlFilter(actonslist);
                org.w3c.dom.Node nodeaction = filteractions.nextElement();

                while (null != nodeaction) {
                    NamedNodeMap actionattr = nodeaction.getAttributes();
                    org.w3c.dom.Node actionname = actionattr.getNamedItem("name");

                    stream.writeObject("\tStartmission named " + actionname.getNodeValue() + "\n");
                    nodeaction = filteractions.nextElement();
                }
            }

            node = filter.nextElement();
        }

        stream.flush();
        stream.close();
    }

    /**
     * Method description
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        ArrayList testarr = new ArrayList();

        testarr.add(Integer.valueOf(5));
        testarr.add(Integer.valueOf(1));
        testarr.add(Integer.valueOf(6));
        testarr.add(Integer.valueOf(2));
        testarr.add(Integer.valueOf(7));
        testarr.add(Integer.valueOf(3));
        testarr.add(Integer.valueOf(8));
        testarr.add(Integer.valueOf(4));
        Collections.sort(testarr, new ctest());

        try {
            xpathtest("E:\\BAZA\\Data\\Missions\\dnm_missions.xml");
        } catch (Exception e) {
            System.err.print(e.toString());
        }
    }

    static class ctest implements Comparator {

        /**
         * Method description
         *
         *
         * @param arg0
         * @param arg1
         *
         * @return
         */
        @Override
        public int compare(Object arg0, Object arg1) {
            return (((Integer) arg0).intValue() - ((Integer) arg1).intValue());
        }
    }
}


//~ Formatted in DD Std on 13/08/26
