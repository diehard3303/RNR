/*
 * @(#)BigRace.java   13/08/26
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

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrscenario.ScenarioFlagsManager;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class BigRace {

    /** Field description */
    public static final int FLAG_SIMPLE = 1;

    /** Field description */
    public static final int FLAG_SCENARIO = 2;

    /** Field description */
    public static final int FLAG_EMMEDIATE = 4;

    /** Field description */
    public static final int series1 = 1;

    /** Field description */
    public static final int series2 = 2;

    /** Field description */
    public static final int series3 = 3;

    /** Field description */
    public static final int series4 = 4;

    /** Field description */
    public static final int series5 = 5;

    /** Field description */
    public static int series_highest = 4;

    /** Field description */
    public static int[] limits_series = {
        0, 100, 1000, 10000, 100000, 1000000
    };
    private static final double prob_disturbance = 0.9D;
    private static final double frequency = 0.3333333333333333D;
    private static BigRace race = null;
    private final ArrayList<IBigRaceEventsListener> listeners = new ArrayList();
    private final ArrayList<IBigRaceEventsListener> deleted_listeners = new ArrayList();
    private final ArrayList<IBigRaceEventsListener> new_listeners = new ArrayList();
    private boolean listerns_are_working = false;
    private CoreTime lasttimesceduled = null;
    private CoreTime next_race = null;
    private boolean first_time_scheduled_race = true;
    private int series_low = 1;
    private int series_high = 2;
    private boolean mostercup_processed = false;

    /**
     * Method description
     *
     *
     * @return
     */
    public static BigRace gReference() {
        if (race == null) {
            race = new BigRace();
        }

        return race;
    }

    /**
     * Method description
     *
     */
    public static final void process() {
        if (race == null) {
            return;
        }

        race.coreProcess();
    }

    /**
     * Method description
     *
     */
    public static final void initiate() {
        race = new BigRace();
        race.startBigRaceSystem();
    }

    /**
     * Method description
     *
     */
    public static final void deinit() {
        race = null;
    }

    void startBigRaceSystem() {
        this.series_low = 1;
        this.series_high = 2;
        this.mostercup_processed = false;
        this.next_race = null;
        this.lasttimesceduled = new CoreTime();
        this.lasttimesceduled.plus_days(2);
    }

    private double calculateProbability() {
        int rating = RateSystem.gLiveRating();
        double posibility = getCalculatedProbability(rating, this.series_low, this.series_high);

        if ((posibility > 1.0D) && (this.series_high < series_highest)) {
            this.series_low += 1;
            this.series_high += 1;
            posibility = calculateProbability();
        }

        return posibility;
    }

    private int ln(int value) {
        if (value <= 0) {
            value = 1;
        }

        return (int) Math.log(value);
    }

    double getCalculatedProbability(int rating, int serieslow, int serieshigh) {
        int i = serieslow;
        int i1 = serieshigh;
        int i2 = serieshigh + 1;
        double res_posibility;

        if (rating < limits_series[i]) {
            res_posibility = 0.0D;
        } else {
            if (serieshigh < series_highest) {
                res_posibility = (ln(rating) - ln(limits_series[i]))
                                 / (ln(limits_series[i1]) + (ln(limits_series[i2]) - ln(limits_series[i1])) * 0.9D
                                    - ln(limits_series[i]));
            } else {
                res_posibility = (rating - limits_series[i]) / (limits_series[i1] - limits_series[i]);
            }
        }

        return res_posibility;
    }

    private void sceduleBigRace(double probability) {
        if (this.mostercup_processed) {
            return;
        }

        int next_series = this.series_low;

        if (probability > Math.random()) {
            if ((this.series_high == series_highest) && (this.mostercup_processed)) {
                next_series = this.series_low;
            } else {
                next_series = this.series_high;
                this.mostercup_processed = (this.series_high == series_highest);

                if ((this.mostercup_processed) && (ScenarioFlagsManager.getInstance() != null)) {
                    ScenarioFlagsManager.getInstance().setFlagValueTimed("Start_M_CUP_news", 2);
                }
            }
        } else {
            next_series = this.series_low;
        }

        this.next_race = new CoreTime();
        this.lasttimesceduled = new CoreTime(this.next_race);
        this.next_race.plus(CoreTime.days(2));

        int hours = (int) (23.0D * Math.random());
        int minuts = (int) (59.0D * Math.random());

        prepareBigRace(1, next_series, limits_series[next_series], this.next_race.gYear(), this.next_race.gMonth(),
                       this.next_race.gDate(), hours, minuts);
    }

    /**
     * Method description
     *
     *
     * @param days
     *
     * @return
     */
    public static int sceduleScenarioRace(int days) {
        int next_series = race.series_low;
        int hours = (int) (23.0D * Math.random());
        int minuts = (int) (59.0D * Math.random());

        race.next_race = new CoreTime();
        race.next_race.plus(CoreTime.days(days));

        return prepareBigRace(6, next_series, limits_series[next_series], race.next_race.gYear(),
                              race.next_race.gMonth(), race.next_race.gDate(), hours, minuts);
    }

    private final void coreProcess() {
        CoreTime current = new CoreTime();

        if (this.lasttimesceduled != null) {
            boolean to_schedule = CoreTime.CompareByDays(current, this.lasttimesceduled) > 0;

            if ((this.first_time_scheduled_race) && (to_schedule)) {
                this.first_time_scheduled_race = false;
            }

            if (to_schedule) {
                double probability = calculateProbability();

                sceduleBigRace(probability);
            }
        }
    }

    /**
     * Method description
     *
     */
    public void DEBUGscheduleRace() {
        CoreTime raceTime = new CoreTime();
        CoreTime deltaTime = new CoreTime(0, 0, 0, 12, 0);

        raceTime.plus(deltaTime);
        prepareBigRace(1, 1, limits_series[1], raceTime.gYear(), raceTime.gMonth(), raceTime.gDate(), raceTime.gHour(),
                       raceTime.gMinute());
    }

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramInt4
     * @param paramInt5
     * @param paramInt6
     * @param paramInt7
     * @param paramInt8
     *
     * @return
     */
    public static native int prepareBigRace(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5,
            int paramInt6, int paramInt7, int paramInt8);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void teleportOnStart(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramString
     * @param paramInt2
     */
    public static native void setSemitailerForRaceForLivePlayer(int paramInt1, String paramString, int paramInt2);

    /**
     * Method description
     *
     *
     * @param lst
     */
    public static void addListener(IBigRaceEventsListener lst) {
        if (race.listerns_are_working) {
            race.new_listeners.add(lst);
        } else {
            race.listeners.add(lst);
        }
    }

    /**
     * Method description
     *
     *
     * @param lst
     */
    public static void removeListener(IBigRaceEventsListener lst) {
        if (race.listerns_are_working) {
            race.deleted_listeners.add(lst);
        } else {
            race.listeners.remove(lst);
        }
    }

    /**
     * Method description
     *
     *
     * @param race_uid
     * @param live_race
     */
    public static void bigRaceStarted(int race_uid, boolean live_race) {
        race.listerns_are_working = true;

        for (IBigRaceEventsListener lst : race.listeners) {
            if (lst.getUid() == race_uid) {
                lst.raceStarted(live_race);
            }
        }

        race.listeners.addAll(race.new_listeners);
        race.listeners.removeAll(race.deleted_listeners);
        race.new_listeners.clear();
        race.deleted_listeners.clear();
        race.listerns_are_working = false;
    }

    /**
     * Method description
     *
     *
     * @param race_uid
     */
    public static void bigRaceFailedByLive(int race_uid) {
        race.listerns_are_working = true;

        for (IBigRaceEventsListener lst : race.listeners) {
            if (lst.getUid() == race_uid) {
                lst.raceFailed();
            }
        }

        race.listeners.addAll(race.new_listeners);
        race.listeners.removeAll(race.deleted_listeners);
        race.new_listeners.clear();
        race.deleted_listeners.clear();
        race.listerns_are_working = false;
    }

    /**
     * Method description
     *
     *
     * @param race_uid
     * @param place
     */
    public static void bigRaceFinishByLive(int race_uid, int place) {
        race.listerns_are_working = true;

        for (IBigRaceEventsListener lst : race.listeners) {
            if (lst.getUid() == race_uid) {
                lst.raceFinished(place);
            }
        }

        race.listeners.addAll(race.new_listeners);
        race.listeners.removeAll(race.deleted_listeners);
        race.new_listeners.clear();
        race.deleted_listeners.clear();
        race.listerns_are_working = false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isFirst_time_scheduled_race() {
        return this.first_time_scheduled_race;
    }

    /**
     * Method description
     *
     *
     * @param first_time_scheduled_race
     */
    public void setFirst_time_scheduled_race(boolean first_time_scheduled_race) {
        this.first_time_scheduled_race = first_time_scheduled_race;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public CoreTime getLasttimesceduled() {
        return this.lasttimesceduled;
    }

    /**
     * Method description
     *
     *
     * @param lasttimesceduled
     */
    public void setLasttimesceduled(CoreTime lasttimesceduled) {
        this.lasttimesceduled = lasttimesceduled;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isMostercup_processed() {
        return this.mostercup_processed;
    }

    /**
     * Method description
     *
     *
     * @param mostercup_processed
     */
    public void setMostercup_processed(boolean mostercup_processed) {
        this.mostercup_processed = mostercup_processed;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public CoreTime getNext_race() {
        return this.next_race;
    }

    /**
     * Method description
     *
     *
     * @param next_race
     */
    public void setNext_race(CoreTime next_race) {
        this.next_race = next_race;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getSeries_high() {
        return this.series_high;
    }

    /**
     * Method description
     *
     *
     * @param series_high
     */
    public void setSeries_high(int series_high) {
        this.series_high = series_high;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getSeries_low() {
        return this.series_low;
    }

    /**
     * Method description
     *
     *
     * @param series_low
     */
    public void setSeries_low(int series_low) {
        this.series_low = series_low;
    }
}


//~ Formatted in DD Std on 13/08/26
