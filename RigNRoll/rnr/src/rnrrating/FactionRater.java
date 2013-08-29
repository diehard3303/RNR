/*
 * @(#)FactionRater.java   13/08/28
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


package rnr.src.rnrrating;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.Log;
import rnr.src.scenarioUtils.Pair;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class FactionRater {

    /** Field description */
    public static final int MONIKA = 0;

    /** Field description */
    public static final int DOROTHY = 1;

    /** Field description */
    public static double DEFAULT_FACTION_RATING;

    /** Field description */
    public static final String[] FACTION_NAMES;
    private static FactionRater instance;

    static {
        DEFAULT_FACTION_RATING = 0.0D;
        FACTION_NAMES = new String[] { "MonikaRank", "DorothyRank" };
        instance = null;
    }

    @SuppressWarnings("unchecked")
    private HashMap<String, Double> m_FactionsValues = new HashMap<String, Double>();

    private static FactionRater getInstance() {
        if (instance == null) {
            instance = new FactionRater();
        }

        return instance;
    }

    private void checkFactionIsPredefined(String factionName) {
        for (String name : FACTION_NAMES) {
            if (name.compareTo(factionName) == 0) {
                return;
            }
        }

        Log.simpleMessage("Faction rater errorr. Working with unknown faction " + factionName);
    }

    private void addFactionValue(String factionName, double value) {
        checkFactionIsPredefined(factionName);

        double newFactionValue = value;

        if (this.m_FactionsValues.containsKey(factionName)) {
            Double factionValue = this.m_FactionsValues.get(factionName);

            newFactionValue += factionValue.doubleValue();
        }

        Log.rating("Faction " + factionName + " has changed value to " + newFactionValue);
        this.m_FactionsValues.put(factionName, Double.valueOf(newFactionValue));
    }

    private void setFactionValues(List<Pair<String, String>> data) {
        assert(data != null);
        this.m_FactionsValues = new HashMap<String, Double>();

        for (Pair<?, ?> pairValues : data) {
            String factionName = (String) pairValues.getFirst();

            assert(factionName != null);

            String factionValueString = (String) pairValues.getSecond();

            assert(factionValueString != null);

            try {
                double factionValue = Double.parseDouble(factionValueString);

                this.m_FactionsValues.put(factionName, Double.valueOf(factionValue));
            } catch (NumberFormatException exception) {
                Log.rating("Cannot format faction " + factionName + " value " + factionValueString + " to Double.");
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private List<Pair<String, String>> getFactionValues() {
        if (this.m_FactionsValues != null) {
            List<Pair<String, String>> resultList = new ArrayList<Pair<String, String>>();
            Set<Entry<String, Double>> factionsEntrieSet = this.m_FactionsValues.entrySet();

            for (Map.Entry singleFactionEntry : factionsEntrieSet) {
                resultList.add(new Pair<String, String>((String) singleFactionEntry.getKey(),
                                        ((Double) singleFactionEntry.getValue()).toString()));
            }

            return resultList;
        }

        return null;
    }

    private double getFactionValue(String factionName) {
        checkFactionIsPredefined(factionName);

        if (this.m_FactionsValues.containsKey(factionName)) {
            return this.m_FactionsValues.get(factionName).doubleValue();
        }

        return DEFAULT_FACTION_RATING;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        instance = null;
    }

    /**
     * Method description
     *
     *
     * @param factionName
     * @param value
     */
    public static void addFactionRating(String factionName, double value) {
        getInstance().addFactionValue(factionName, value);
    }

    /**
     * Method description
     *
     *
     * @param factionName
     *
     * @return
     */
    public static double getFactionRating(String factionName) {
        return getInstance().getFactionValue(factionName);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static List<Pair<String, String>> getSerializationData() {
        return getInstance().getFactionValues();
    }

    /**
     * Method description
     *
     *
     * @param data
     */
    public static void setSerializationData(List<Pair<String, String>> data) {
        getInstance().setFactionValues(data);
    }
}


//~ Formatted in DD Std on 13/08/28
