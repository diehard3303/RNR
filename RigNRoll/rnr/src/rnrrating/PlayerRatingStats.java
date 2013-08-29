/*
 * @(#)PlayerRatingStats.java   13/08/26
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

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class PlayerRatingStats {
    private Rater current = null;
    private HashMap<String, Rater> current_missions = new HashMap();
    private RatedItem rating;

    /**
     * Constructs ...
     *
     *
     * @param id
     */
    public PlayerRatingStats(String id) {
        this.rating = new RatedItem(id);
    }

    void sRate(Rater item) {
        if (this.current != null) {
            fail();
        }

        this.current = item;
    }

    void sRate(String mission_name, Rater item) {
        if (this.current_missions.containsKey(mission_name)) {
            System.err.print("ERRORR. MissionSistem passed to rate mission " + mission_name + "that already in list.");

            return;
        }

        this.current_missions.put(mission_name, item);
    }

    int getrating() {
        return this.rating.getRating();
    }

    void addRating(int value) {
        this.rating.addRating(value);
    }

    void DEBUGsetrating(int value) {
        this.rating.setRating(value);
    }

    int finish() {
        if (this.current == null) {
            Log.simpleMessage("Trying to finish player, while have no rated quests");

            return 0;
        }

        int diff_rating = this.current.gRatingOnFinish();

        this.rating.addRating(diff_rating);
        this.current = null;

        return diff_rating;
    }

    int gold() {
        if (this.current == null) {
            Log.simpleMessage("Trying to gold player, while have no rated quests");

            return 0;
        }

        int diff_rating = this.current.gRatingOnGold();

        this.rating.addRating(diff_rating);
        this.current = null;

        return diff_rating;
    }

    int silver() {
        if (this.current == null) {
            Log.simpleMessage("Trying to silver player, while have no rated quests");

            return 0;
        }

        int diff_rating = this.current.gRatingOnSilver();

        this.rating.addRating(diff_rating);
        this.current = null;

        return diff_rating;
    }

    int bronze() {
        if (this.current == null) {
            Log.simpleMessage("Trying to bronze player, while have no rated quests");

            return 0;
        }

        int diff_rating = this.current.gRatingOnBronze();

        this.rating.addRating(diff_rating);
        this.current = null;

        return diff_rating;
    }

    int checkpoint() {
        if (this.current == null) {
            Log.simpleMessage("Trying to checkpoint player, while have no rated quests");

            return 0;
        }

        int diff_rating = this.current.gRatingOnCheckPoint();

        this.rating.addRating(diff_rating);
        this.current = null;

        return diff_rating;
    }

    int fail() {
        if (this.current == null) {
            Log.simpleMessage("Trying to fail player, while have no rated quests");

            return 0;
        }

        int diff_rating = this.current.gRatingOnFail();

        this.rating.addRating(diff_rating);
        this.current = null;

        return diff_rating;
    }

    int finishMission(String mission_name) {
        if (!(this.current_missions.containsKey(mission_name))) {
            Log.simpleMessage("Trying to finishMission player, while have no rated mission with name " + mission_name);

            return 0;
        }

        Rater rat = this.current_missions.get(mission_name);
        int diff_rating = rat.gRatingOnFinish();

        this.rating.addRating(diff_rating);
        this.current_missions.remove(mission_name);

        return diff_rating;
    }

    int failMission(String mission_name) {
        if (!(this.current_missions.containsKey(mission_name))) {
            Log.simpleMessage("Trying to failMission player, while have no rated mission with name " + mission_name);

            return 0;
        }

        Rater rat = this.current_missions.get(mission_name);
        int diff_rating = rat.gRatingOnFail();

        this.rating.addRating(diff_rating);
        this.current_missions.remove(mission_name);

        return diff_rating;
    }

    int estimateFinishMission(String mission_name) {
        if (!(this.current_missions.containsKey(mission_name))) {
            Log.simpleMessage("Trying to estimateFinishMission player, while have no rated mission with name "
                              + mission_name);

            return 0;
        }

        return this.current_missions.get(mission_name).gRatingOnGold();
    }

    int estimateFailMission(String mission_name) {
        if (!(this.current_missions.containsKey(mission_name))) {
            Log.simpleMessage("Trying to estimateFailMission player, while have no rated mission with name "
                              + mission_name);

            return 0;
        }

        return this.current_missions.get(mission_name).gRatingOnFail();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Rater getCurrent() {
        return this.current;
    }

    /**
     * Method description
     *
     *
     * @param current
     */
    public void setCurrent(Rater current) {
        this.current = current;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public HashMap<String, Rater> getCurrent_missions() {
        return this.current_missions;
    }

    /**
     * Method description
     *
     *
     * @param current_missions
     */
    public void setCurrent_missions(HashMap<String, Rater> current_missions) {
        this.current_missions = current_missions;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public RatedItem getRating() {
        return this.rating;
    }

    /**
     * Method description
     *
     *
     * @param rating
     */
    public void setRating(RatedItem rating) {
        this.rating = rating;
    }
}


//~ Formatted in DD Std on 13/08/26
