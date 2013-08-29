/*
 * @(#)IStoreorgelement.java   13/08/26
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

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.vectorJ;

/**
 * Interface description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public abstract interface IStoreorgelement extends Ireadelement {

    /** Field description */
    public static final int MONEY_REWARD = 1;

    /** Field description */
    public static final int RATING_REWARD = 4;

    /** Field description */
    public static final int INFORMATION_REWARD = 8;

    /**
     * Enum description
     *
     */
    public static enum Status {
        nostatus, failedMission, executedMission, urgentMission, pendingMission;

        /**
         * Method description
         *
         *
         * @return
         */
        public static final Status[] values() {
            return ((Status[]) $VALUES.clone());
        }
    }

    /**
     * Enum description
     *
     */
    public static enum Type {
        notype, baseDelivery, trailerObjectDelivery, competition, tender, trailerClientDelivery, pakageDelivery,
        passangerDelivery, visit, bigrace1_announce, bigrace2_announce, bigrace3_announce, bigrace4_announce,
        bigrace1_semi, bigrace2_semi, bigrace3_semi, bigrace4_semi, bigrace1, bigrace2, bigrace3, bigrace4;

        private final boolean mIsSpecialType = false;

        /**
         * Method description
         *
         *
         * @return
         */
        public static final Type[] values() {
            return ((Type[]) $VALUES.clone());
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isSpecialType() {
            return this.mIsSpecialType;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract Type getType();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract Status getStatus();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract boolean isImportant();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String getName();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String getDescription();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String getDescriptionRef();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String getRaceName();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String getLogoName();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String loadPoint();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String endPoint();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract vectorJ pos_start();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract vectorJ pos_load();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract vectorJ pos_complete();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract int getRewardFlag();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract int getForfeitFlag();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract RewardForfeit getReward();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract RewardForfeit getForfeit();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract INPC getCustomer();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract CoreTime dateOfRequest();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract CoreTime timeToPickUp();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract CoreTime timeToComplete();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract int getCargoFragility();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract int get_minutes_toFail();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract int get_seconds_toFail();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract int getMissionState();

    /**
     * Method description
     *
     */
    public abstract void start();

    /**
     * Method description
     *
     */
    public abstract void finish();

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public abstract void fail(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramString
     */
    public abstract void updateTimeToComplete(int paramInt1, int paramInt2, int paramInt3, String paramString);

    /**
     * Method description
     *
     */
    public abstract void updateStatus();

    /**
     * Method description
     *
     */
    public abstract void decline();

    /**
     * Method description
     *
     *
     * @param paramIDeclineOrgListener
     */
    public abstract void addDeclineListener(IDeclineOrgListener paramIDeclineOrgListener);
}


//~ Formatted in DD Std on 13/08/26
