/*
 * @(#)RateSystem.java   13/08/26
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

import rnr.src.rnrscr.AdvancedRandom;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class RateSystem {

    /** Field description */
    public static final int minimalRating = 20;
    private static RateSystem system = null;
    private LivePlayerRatingSystem live = new LivePlayerRatingSystem();
    private NPCRatingSystem npc = new NPCRatingSystem();

    /**
     * Method description
     *
     */
    public static final void deinit() {
        system = null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static RateSystem gSystem() {
        if (system == null) {
            system = new RateSystem();
        }

        return system;
    }

    private static PlayerRatingStats getStats(String name) {
        PlayerRatingStats stats = null;

        if (name.compareToIgnoreCase("LIVE") == 0) {
            stats = gSystem().live;
        } else {
            stats = gSystem().npc.getRating(name);
        }

        return stats;
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public static void rateDelievery(String name) {
        getStats(name).sRate(Rater.initWhDelievery(WHRating.gRating(0), WHRating.gRating(1), WHRating.gRating(2),
                WHRating.gRating(3), WHRating.leaveDelieveryPenalty()));
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public static void rateAnotherOrder(String name) {
        getStats(name).sRate(Rater.initWhOtherOrder(WHRating.gRating(0), WHRating.gRating(1), WHRating.gRating(2),
                WHRating.gRating(3), WHRating.leaveDelieveryPenalty()));
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public static void rateDrivingContest(String name) {
        getStats(name).sRate(Rater.initWhDrivingContest(WHRating.gRating(0), WHRating.gRating(1), WHRating.gRating(2),
                WHRating.gRating(3), WHRating.leaveDelieveryPenalty()));
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public static void rateRoadContest(String name) {
        getStats(name).sRate(Rater.initWhDrivingContest(2 * WHRating.gRating(0), 2 * WHRating.gRating(1),
                2 * WHRating.gRating(2), 2 * WHRating.gRating(3), 2 * WHRating.leaveDelieveryPenalty()));
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param series
     */
    public static void rateBigRace(String name, int series) {
        Rater rater = Rater.initWhDrivingContest(WHRating.gRating(0, series), WHRating.gRating(1, series),
                          WHRating.gRating(2, series), WHRating.gRating(3, series),
                          WHRating.leaveBigRacePenalty(series));

        rater.setCheckPointRating(WHRating.gRating(0, series - 1));
        getStats(name).sRate(rater);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param mission_name
     * @param coef_succes
     * @param coef_fail
     */
    public static void rateMission(String name, String mission_name, double coef_succes, double coef_fail) {
        getStats(name).sRate(mission_name,
                 Rater.initMission(WHRating.gRating(0), WHRating.leaveDelieveryPenalty(), coef_succes, coef_fail));
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param place
     *
     * @return
     */
    public static int finish(String name, int place) {
        switch (place) {
         case 0 :
             return getStats(name).gold();

         case 1 :
             return getStats(name).silver();

         case 2 :
             return getStats(name).bronze();
        }

        return getStats(name).finish();
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param place
     *
     * @return
     */
    public static int passCheckpoint(String name, int place) {
        switch (place) {
         case 0 :
             return getStats(name).checkpoint();
        }

        return 0;
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param mission_name
     *
     * @return
     */
    public static int finishMission(String name, String mission_name) {
        return getStats(name).finishMission(mission_name);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public static int fail(String name) {
        return getStats(name).fail();
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param mission_name
     *
     * @return
     */
    public static int failMission(String name, String mission_name) {
        return getStats(name).failMission(mission_name);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int gLiveRating() {
        return gSystem().live.getrating();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int gLiveRatingAroung() {
        int res = 0;
        double rnd = AdvancedRandom.getRandomDouble();
        int live_rating = gSystem().live.getrating();

        for (int iter = 1; iter <= BigRace.series_highest; ++iter) {
            if ((iter == BigRace.series_highest) || (live_rating < BigRace.limits_series[iter])
                    || (live_rating >= BigRace.limits_series[(iter + 1)])) {
                continue;
            }

            double br2 = Math.log(BigRace.limits_series[(iter + 1)]);
            double br1 = Math.log(BigRace.limits_series[iter]);
            double res_log = rnd * (br2 - br1) + br1;

            res = (int) Math.exp(res_log);

            return res;
        }

        double double_res = (rnd + 1.0D) * BigRace.limits_series[BigRace.series_highest];

        res = (int) double_res;

        return res;
    }

    /**
     * Method description
     *
     *
     * @param lvl
     *
     * @return
     */
    public static int gBracketRatingAroung(int lvl) {
        int res = 0;
        double rnd = AdvancedRandom.getRandomDouble();

        if (lvl != BigRace.series_highest) {
            double br2 = Math.log(BigRace.limits_series[(lvl + 1)]);
            double br1 = Math.log(BigRace.limits_series[lvl]);
            double res_log = rnd * (br2 - br1) + br1;

            res = (int) Math.exp(res_log);

            return res;
        }

        double double_res = (rnd + 1.0D) * BigRace.limits_series[BigRace.series_highest];

        res = (int) double_res;

        return res;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void DEBUGsetLiveRating(int value) {
        gSystem().live.DEBUGsetrating(value);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static double gNormalLiveRating() {
        double live_normal_rate = Math.log(gSystem().live.getrating())
                                  / Math.log(BigRace.limits_series[BigRace.series_highest]);

        if (live_normal_rate > 1.0D) {
            live_normal_rate = 1.0D;
        }

        return live_normal_rate;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int estimateDelivery() {
        return WHRating.gRating(0);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int estimateFailDelivery() {
        return WHRating.leaveDelieveryPenalty();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int estimateAnotherOrder() {
        return WHRating.gRating(0);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int estimateFailAnotherOrder() {
        return WHRating.leaveDelieveryPenalty();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int estimateContest() {
        return WHRating.gRating(0);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int estimateFailContest() {
        return WHRating.leaveDelieveryPenalty();
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     *
     * @return
     */
    public static int estimateFinishLiveMission(String mission_name) {
        return gSystem().live.estimateFinishMission(mission_name);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     *
     * @return
     */
    public static int estimateFailLiveMission(String mission_name) {
        return gSystem().live.estimateFailMission(mission_name);
    }

    /**
     * Method description
     *
     *
     * @param raceid
     *
     * @return
     */
    public static int estimateGoldRace(int raceid) {
        if ((raceid > WHRating.series_GOLD.length - 1) || (raceid < 0)) {
            return -1;
        }

        return WHRating.series_GOLD[raceid];
    }

    /**
     * Method description
     *
     *
     * @param raceid
     *
     * @return
     */
    public static int estimateFailRace(int raceid) {
        if ((raceid > WHRating.series_GOLD.length - 1) || (raceid < 0)) {
            return -1;
        }

        return WHRating.leaveBigRacePenalty(raceid);
    }

    /**
     * Method description
     *
     *
     * @param coefficient
     */
    public static void addLiveRating(double coefficient) {
        if (system == null) {
            return;
        }

        system.live.addRating((int) (coefficient * estimateDelivery()));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public LivePlayerRatingSystem getLive() {
        return this.live;
    }

    /**
     * Method description
     *
     *
     * @param live
     */
    public void setLive(LivePlayerRatingSystem live) {
        this.live = live;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public NPCRatingSystem getNpc() {
        return this.npc;
    }

    /**
     * Method description
     *
     *
     * @param npc
     */
    public void setNpc(NPCRatingSystem npc) {
        this.npc = npc;
    }
}


//~ Formatted in DD Std on 13/08/26
