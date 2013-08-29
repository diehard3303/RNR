/*
 * @(#)Rater.java   13/08/26
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Rater {
    private static final int numRates = 6;
    private int[] ratingRates = null;
    private int money = 0;
    private int checkPointRating;

    /**
     * Constructs ...
     *
     */
    public Rater() {
        this.ratingRates = new int[6];

        for (int i = 0; i < 6; ++i) {
            this.ratingRates[i] = 0;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int gRatingOnStart() {
        return this.ratingRates[0];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int gRatingOnFinish() {
        return this.ratingRates[1];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int gRatingOnFail() {
        return this.ratingRates[2];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int gRatingOnGold() {
        return this.ratingRates[3];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int gRatingOnSilver() {
        return this.ratingRates[4];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int gRatingOnBronze() {
        return this.ratingRates[5];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int gRatingOnCheckPoint() {
        return this.checkPointRating;
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
     * @param value
     */
    public void setCheckPointRating(int value) {
        this.checkPointRating = value;
    }

    /**
     * Method description
     *
     *
     * @param ongold
     * @param onsilver
     * @param onbronze
     * @param onfinish
     * @param onfail
     *
     * @return
     */
    public static Rater initWhDelievery(int ongold, int onsilver, int onbronze, int onfinish, int onfail) {
        Rater rt = new Rater();

        rt.ratingRates[0] = 0;
        rt.ratingRates[1] = onfinish;
        rt.ratingRates[2] = onfail;
        rt.ratingRates[3] = ongold;
        rt.ratingRates[4] = onsilver;
        rt.ratingRates[5] = onbronze;

        return rt;
    }

    /**
     * Method description
     *
     *
     * @param on_succes
     * @param on_fail
     * @param coef_succes
     * @param coef_fail
     *
     * @return
     */
    public static Rater initMission(int on_succes, int on_fail, double coef_succes, double coef_fail) {
        Rater rt = new Rater();

        rt.ratingRates[0] = 0;
        rt.ratingRates[1] = (int) (on_succes * coef_succes);
        rt.ratingRates[2] = (int) (on_fail * coef_fail);
        rt.ratingRates[3] = 0;
        rt.ratingRates[4] = 0;
        rt.ratingRates[5] = 0;

        return rt;
    }

    /**
     * Method description
     *
     *
     * @param ongold
     * @param onsilver
     * @param onbronze
     * @param onfinish
     * @param onfail
     *
     * @return
     */
    public static Rater initWhOtherOrder(int ongold, int onsilver, int onbronze, int onfinish, int onfail) {
        return initWhDelievery(ongold, onsilver, onbronze, onfinish, onfail);
    }

    /**
     * Method description
     *
     *
     * @param ongold
     * @param onsilver
     * @param onbronze
     * @param onfinish
     * @param onfail
     *
     * @return
     */
    public static Rater initWhDrivingContest(int ongold, int onsilver, int onbronze, int onfinish, int onfail) {
        return initWhDelievery(ongold, onsilver, onbronze, onfinish, onfail);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * Method description
     *
     *
     * @param money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int[] getRatingRates() {
        return this.ratingRates;
    }

    /**
     * Method description
     *
     *
     * @param ratingRates
     */
    public void setRatingRates(int[] ratingRates) {
        this.ratingRates = ratingRates;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getCheckPointRating() {
        return this.checkPointRating;
    }
}


//~ Formatted in DD Std on 13/08/26
