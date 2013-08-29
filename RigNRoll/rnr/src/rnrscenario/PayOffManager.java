/*
 * @(#)PayOffManager.java   13/08/28
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


package rnr.src.rnrscenario;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Document;

import rnr.src.rnrcore.Log;
import rnr.src.rnrcore.eng;
import rnr.src.rnrrating.FactionRater;
import rnr.src.rnrrating.RateSystem;
import rnr.src.rnrscenario.PayOffManager.SinglePayOff;
import rnr.src.scenarioXml.XmlDocument;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

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
public class PayOffManager {

    /** Field description */
    public static final int DOROTHY_SAVE_LOOSE = 0;

    /** Field description */
    public static final int DOROTHY_SAVE_SUCCESS = 1;

    /** Field description */
    public static final int PP_RACE_LOOSE = 2;

    /** Field description */
    public static final int PP_RACE_SUCCESS = 3;

    /** Field description */
    public static final int PP_FINALRACE_SUCCESS = 4;

    /** Field description */
    public static final int CHASE_TOPO_SUCCESS = 5;

    /** Field description */
    public static final int TRACK_TO_ENEMY_BASE_SUCCESS = 6;

    /** Field description */
    public static final int TRACK_TO_ENEMY_BASE_LOOSE = 7;

    /** Field description */
    public static final int ENEMY_BASE_ASSAULT_SUCCESS = 8;

    /** Field description */
    public static final int KOH_CHASE_SUCCESS = 9;

    /** Field description */
    public static final int VICTORY_ECONOMY = 10;

    /** Field description */
    public static final int VICTORY_SOCIAL = 11;

    /** Field description */
    public static final int VICTORY_RACE = 12;
    private static final String XML_NAME = "..\\data\\config\\payoff.xml";
    private static final String TOP_NODE = "payoff";
    private static final String ELEMENT_NODE = "element";
    private static final String NAME_ATTR = "name";
    private static final String MONEY_COEFF_ATTR = "money";
    private static final String RATING_COEFF_ATTR = "rating";
    private static final String RANK_COEFF_ATTR = "rank";
    private static final String REAL_MONEY_ATTR = "realmoney";
    private static final String LOC_REF_ATTR = "pager_locref";
    private static final String FACTION_NODE = "faction";
    private static final String FACTION_NAME_ATTR = "name";
    private static final String FACTION_VALUE_ATTR = "rating";

    /** Field description */
    public static final String[] PAYOFF_NAMES;
    private static PayOffManager instance;

    static {
        PAYOFF_NAMES = new String[] {
            "dorothy_save_loose", "dorothy_save_success", "pp_race_loose", "pp_race_success", "pp_final_race_success",
            "chase_topo_success", "track_to_enemy_base_success", "track_to_enemy_base_loose",
            "enemy_base_assault_success", "koh_chase_success", "economy_victory", "social_victory", "race_victory"
        };
        instance = null;
    }

    private final HashMap<String, SinglePayOff> m_payOffs = new HashMap<String, SinglePayOff>();

    private PayOffManager() {
        read("..\\data\\config\\payoff.xml");
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static PayOffManager getInstance() {
        if (instance == null) {
            instance = new PayOffManager();
        }

        return instance;
    }

    private void read(String filename) {
        XmlDocument xml = null;

        try {
            xml = new XmlDocument(filename);
        } catch (IOException exception) {
            Log.simpleMessage("Errorr. Cannot find file name " + filename);

            return;
        }

        Document doc = xml.getContent();
        org.w3c.dom.Node top = doc.getFirstChild();

        if (top.getNodeName().compareToIgnoreCase("payoff") != 0) {
            return;
        }

        rnr.src.xmlutils.Node topNode = new rnr.src.xmlutils.Node(top);
        NodeList elementsList = topNode.getNamedChildren("element");

        if (elementsList != null) {
            for (rnr.src.xmlutils.Node elementNode : elementsList) {
                String attributeName = elementNode.getAttribute("name");
                String attributeMoney = elementNode.getAttribute("money");
                String attributeRealMoney = elementNode.getAttribute("realmoney");
                String attributeRating = elementNode.getAttribute("rating");
                String attributeRank = elementNode.getAttribute("rank");
                String attributeLocRef = elementNode.getAttribute("pager_locref");
                double money_coef = (attributeMoney != null)
                                    ? Double.parseDouble(attributeMoney)
                                    : 0.0D;
                double real_money = (attributeRealMoney != null)
                                    ? Double.parseDouble(attributeRealMoney)
                                    : 0.0D;
                double rating_coef = (attributeRating != null)
                                     ? Double.parseDouble(attributeRating)
                                     : 0.0D;
                double rank_coef = (attributeRank != null)
                                   ? Double.parseDouble(attributeRank)
                                   : 0.0D;
                SinglePayOff payOff = new SinglePayOff(money_coef, rank_coef, rating_coef, real_money);

                payOff.setAnnouncePaymentLocRef(attributeLocRef);

                NodeList factionsList = elementNode.getNamedChildren("faction");

                if (factionsList != null) {
                    for (rnr.src.xmlutils.Node factionNode : factionsList) {
                        String attributeFactionName = factionNode.getAttribute("name");
                        String attributeFactionValue = factionNode.getAttribute("rating");
                        double valueFactionPayOff = (attributeFactionValue != null)
                                                    ? Double.parseDouble(attributeFactionValue)
                                                    : 0.0D;

                        payOff.addFactionPayOff(attributeFactionName, valueFactionPayOff);
                    }
                }

                if (this.m_payOffs.containsKey(attributeName)) {
                    Log.simpleMessage("WARNING. Trying to configue pay off twice. Pay off name " + attributeName);
                }

                this.m_payOffs.put(attributeName, payOff);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param payOffName
     */
    public void makePayOff(String payOffName) {
        if (!(this.m_payOffs.containsKey(payOffName))) {
            Log.simpleMessage("ERRORR. Trying to pay off on unexisting pay off: " + payOffName);

            return;
        }

        Log.simpleMessage("Makin pay off: " + payOffName);

        SinglePayOff payOff = this.m_payOffs.get(payOffName);

        assert(payOff != null);
        payOff.makePayOff();
    }

    static class SinglePayOff {
        String announcePaymentLocRef = null;
        HashMap<String, Double> factionRewards = new HashMap<String, Double>();
        double money_coeff;
        double rank_coef;
        double rating_coef;
        double real_money;

        SinglePayOff(double money_coeff, double rank_coef, double rating_coef, double real_money) {
            this.money_coeff = money_coeff;
            this.rank_coef = rank_coef;
            this.rating_coef = rating_coef;
            this.real_money = real_money;
        }

        void setAnnouncePaymentLocRef(String value) {
            this.announcePaymentLocRef = value;
        }

        void addFactionPayOff(String factionName, double value) {
            if (this.factionRewards.containsKey(factionName)) {
                Log.simpleMessage("WARNING. Trying to create faction reward twice. Faction name " + factionName
                                  + " reward is " + value);

                return;
            }

            this.factionRewards.put(factionName, Double.valueOf(value));
        }

        @SuppressWarnings("rawtypes")
        void makePayOff() {
            Set<Entry<String, Double>> factionsRewardSet = this.factionRewards.entrySet();

            for (Map.Entry singleFactionEntry : factionsRewardSet) {
                FactionRater.addFactionRating((String) singleFactionEntry.getKey(),
                                              ((Double) singleFactionEntry.getValue()).doubleValue());
            }

            RateSystem.addLiveRating(this.rating_coef);
            eng.makePayOff(this.money_coeff, this.rank_coef, this.real_money, this.announcePaymentLocRef);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
