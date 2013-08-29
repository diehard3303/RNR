/*
 * @(#)RewardForfeit.java   13/08/26
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

import rnr.src.rnrcore.Log;
import rnr.src.rnrrating.FactionRater;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
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
public class RewardForfeit {
    private double coef_money = 0.0D;
    private double coef_rate = 0.0D;
    private double coef_rank = 0.0D;
    private int money = 0;
    private int rate = 0;
    private int rank = 0;
    private int real_money = 0;
    private int real_rate = 0;
    private int real_rank = 0;
    private int flag = 0;
    @SuppressWarnings("unchecked")
    private final HashMap<String, Double> m_factionRating = new HashMap();

    /**
     * Constructs ...
     *
     *
     * @param coef_money
     * @param coef_rate
     * @param coef_rank
     */
    public RewardForfeit(double coef_money, double coef_rate, double coef_rank) {
        this.coef_money = coef_money;
        this.coef_rate = coef_rate;
        this.coef_rank = coef_rank;

        if (coef_money != 0.0D) {
            this.flag |= 1;
        }

        if (coef_rate != 0.0D) {
            this.flag |= 4;
        }
    }

    /**
     * Method description
     *
     *
     * @param money
     */
    public void sMoney(int money) {
        this.money = money;
    }

    /**
     * Method description
     *
     *
     * @param rate
     */
    public void sRate(int rate) {
        this.rate = rate;
    }

    /**
     * Method description
     *
     *
     * @param rank
     */
    public void sRank(int rank) {
        this.rank = rank;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int gMoney() {
        return this.money;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int gRate() {
        return this.rate;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int gRank() {
        return this.rank;
    }

    /**
     * Method description
     *
     *
     * @param money
     */
    public void setRealMoney(int money) {
        this.real_money = money;
    }

    /**
     * Method description
     *
     *
     * @param rate
     */
    public void setRealRate(int rate) {
        this.real_rate = rate;
    }

    /**
     * Method description
     *
     *
     * @param rank
     */
    public void setRealRank(int rank) {
        this.real_rank = rank;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getRealMoney() {
        return this.real_money;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getRealRate() {
        return this.real_rate;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getRealRank() {
        return this.real_rank;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double gCoefMoney() {
        return this.coef_money;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double gCoefRate() {
        return this.coef_rate;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double gCoefRank() {
        return this.coef_rank;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getFlag() {
        return this.flag;
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param value
     */
    public void addFactionRating(String name, double value) {
        if (!(this.m_factionRating.containsKey(name))) {
            this.m_factionRating.put(name, Double.valueOf(value));
        } else {
            Log.simpleMessage("RewardForfeit addFactionRating trying to add existing faction rating value");
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public HashMap<String, Double> getFactionRatings() {
        return this.m_factionRating;
    }

    /**
     * Method description
     *
     *
     * @param coef
     */
    @SuppressWarnings("rawtypes")
    public void applyFactionRatings(double coef) {
        Set setRatings = this.m_factionRating.entrySet();

        for (Map.Entry singleFactionRating : setRatings) {
            String factionName = (String) singleFactionRating.getKey();
            double ratingDiff = ((Double) singleFactionRating.getValue()).doubleValue();

            ratingDiff *= coef;
            FactionRater.addFactionRating(factionName, ratingDiff);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
