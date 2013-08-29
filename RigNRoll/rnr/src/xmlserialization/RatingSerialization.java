/*
 * @(#)RatingSerialization.java   13/08/28
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
import rnr.src.rnrrating.LivePlayerRatingSystem;
import rnr.src.rnrrating.NPCRatingSystem;
import rnr.src.rnrrating.PlayerRatingStats;
import rnr.src.rnrrating.RateSystem;
import rnr.src.rnrrating.RatedItem;
import rnr.src.rnrrating.Rater;
import rnr.src.scenarioUtils.Pair;
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
public class RatingSerialization implements XmlSerializable {
    private static RatingSerialization instance = null;

    /**
     * Method description
     *
     *
     * @return
     */
    public static RatingSerialization getInstance() {
        if (null == instance) {
            instance = new RatingSerialization();
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
        return "rating_system";
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @SuppressWarnings("rawtypes")
    public static void serializeXML(PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        RateSystem value = RateSystem.gSystem();
        LivePlayerRatingSystem liveRatingSystem = value.getLive();

        Helper.openNode(stream, "live_rating_system");
        serializeXML(liveRatingSystem, stream);
        Helper.closeNode(stream, "live_rating_system");

        NPCRatingSystem npcRatingSystem = value.getNpc();
        HashMap<String, PlayerRatingStats> npcRatings = npcRatingSystem.getRating();
        Set<Entry<String, PlayerRatingStats>> npcRatingsEntries = npcRatings.entrySet();

        for (Map.Entry entry : npcRatingsEntries) {
            Helper.printOpenNodeWithAttributes(stream, "npc_rating_system",
                                               Helper.createSingleAttribute("name", (String) entry.getKey()));
            serializeXML((PlayerRatingStats) entry.getValue(), stream);
            Helper.closeNode(stream, "npc_rating_system");
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings("rawtypes")
    public static void serializeXML(PlayerRatingStats value, PrintStream stream) {
        RatedItem ratedItem = value.getRating();
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("id", ratedItem.getId());

        Helper.addAttribute("rating", ratedItem.getRating(), attributes);
        Helper.printOpenNodeWithAttributes(stream, "player_rating", attributes);

        Rater rater = value.getCurrent();

        if (rater != null) {
            Helper.openNode(stream, "current_rater");
            serializeXML(rater, stream);
            Helper.closeNode(stream, "current_rater");
        }

        HashMap<String, Rater> currentMissions = value.getCurrent_missions();
        Set<Entry<String, Rater>> entrySetMissions = currentMissions.entrySet();

        for (Map.Entry entry : entrySetMissions) {
            Helper.printOpenNodeWithAttributes(stream, "current_rater_mission",
                                               Helper.createSingleAttribute("name", (String) entry.getKey()));
            serializeXML((Rater) entry.getValue(), stream);
            Helper.closeNode(stream, "current_rater_mission");
        }

        Helper.closeNode(stream, "player_rating");
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(Rater value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("checkpoint", value.getCheckPointRating());

        Helper.addAttribute("money", value.getMoney(), attributes);
        Helper.printOpenNodeWithAttributes(stream, "rater", attributes);

        int[] ratingValues = value.getRatingRates();

        for (int i : ratingValues) {
            Helper.printClosedNodeWithAttributes(stream, "rating_value", Helper.createSingleAttribute("value", i));
        }

        Helper.closeNode(stream, "rater");
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void deserializeXML(rnr.src.xmlutils.Node node) {
        RateSystem value = RateSystem.gSystem();
        rnr.src.xmlutils.Node liveRatingNode = node.getNamedChild("live_rating_system");

        if (liveRatingNode != null) {
            rnr.src.xmlutils.Node playerRatingStatsNode = liveRatingNode.getNamedChild("player_rating");

            if (playerRatingStatsNode != null) {
                PlayerRatingStats playerStats = deserializePlayerRatingStatsXML(playerRatingStatsNode);

                value.setLive(new LivePlayerRatingSystem(playerStats));
            }
        }

        NodeList npcRatingNodes = node.getNamedChildren("npc_rating_system");

        if (npcRatingNodes == null) {
            return;
        }

        HashMap npsStatsCollection = new HashMap();

        for (rnr.src.xmlutils.Node npcNode : npcRatingNodes) {
            String name = npcNode.getAttribute("name");

            if (name == null) {
                Log.error("Node npc_rating_system has no attribute name");
            }

            rnr.src.xmlutils.Node playerRatingStatsNode = npcNode.getNamedChild("player_rating");

            if (playerRatingStatsNode == null) {
                Log.error("Node npc_rating_system has no node  player_rating");
            }

            PlayerRatingStats playerStats = deserializePlayerRatingStatsXML(playerRatingStatsNode);

            npsStatsCollection.put(name, playerStats);
        }

        NPCRatingSystem npcRatingSystem = new NPCRatingSystem();

        npcRatingSystem.setRating(npsStatsCollection);
        value.setNpc(npcRatingSystem);
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static PlayerRatingStats deserializePlayerRatingStatsXML(xmlutils.Node node) {
        String idString = node.getAttribute("id");
        String ratingValueString = node.getAttribute("rating");
        int ratingValue = Helper.ConvertToIntegerAndWarn(ratingValueString, "rating",
                              "Node player_rating has non integer attribute rating");
        PlayerRatingStats result = new PlayerRatingStats(idString);
        RatedItem ratedItem = new RatedItem(idString);

        ratedItem.setRating(ratingValue);
        result.setRating(ratedItem);

        rnr.src.xmlutils.Node currentRater = node.getNamedChild("current_rater");

        if (currentRater != null) {
            rnr.src.xmlutils.Node raterNode = currentRater.getNamedChild("rater");

            if (raterNode != null) {
                Rater rater = deserializeRaterXML(raterNode);

                result.setCurrent(rater);
            }
        }

        NodeList missionRatersNodes = node.getNamedChildren("current_rater_mission");

        if (missionRatersNodes != null) {
            HashMap missionRatersCollection = new HashMap();

            for (rnr.src.xmlutils.Node missionRaterNode : missionRatersNodes) {
                String missionName = missionRaterNode.getAttribute("name");

                if (missionName == null) {
                    Log.error("Node current_rater_mission has no attribute name");
                }

                rnr.src.xmlutils.Node missionRater = missionRaterNode.getNamedChild("rater");
                Rater rater = deserializeRaterXML(missionRater);

                missionRatersCollection.put(missionName, rater);
            }

            result.setCurrent_missions(missionRatersCollection);
        }

        return result;
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static Rater deserializeRaterXML(rnr.src.xmlutils.Node node) {
        Rater result = new Rater();
        String checkPointString = node.getAttribute("checkpoint");
        String moneyString = node.getAttribute("money");
        int checkPointValue = Helper.ConvertToIntegerAndWarn(checkPointString, "checkpoint",
                                  "ERRORR in Rater deserializeRaterXML");

        result.setCheckPointRating(checkPointValue);

        int moneyValue = Helper.ConvertToIntegerAndWarn(moneyString, "money", "ERRORR in Rater deserializeRaterXML");

        result.setMoney(moneyValue);

        NodeList ratingValuesNodes = node.getNamedChildren("rating_value");

        if (ratingValuesNodes != null) {
            int[] ratingValues = new int[ratingValuesNodes.size()];

            for (int i = 0; i < ratingValuesNodes.size(); ++i) {
                rnr.src.xmlutils.Node singleRatingValue = ratingValuesNodes.get(i);
                String ratingValueString = singleRatingValue.getAttribute("value");
                int value = Helper.ConvertToIntegerAndWarn(ratingValueString, "value",
                                "ERRORR in Rater deserializeRaterXML");

                ratingValues[i] = value;
            }

            result.setRatingRates(ratingValues);
        }

        return result;
    }
}


//~ Formatted in DD Std on 13/08/28
