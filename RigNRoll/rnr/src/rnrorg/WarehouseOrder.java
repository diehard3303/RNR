/*
 * @(#)WarehouseOrder.java   13/08/26
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

import rnr.src.menu.KeyPair;
import rnr.src.menu.MacroKit;
import rnr.src.rnrconfig.MerchandizeInformation;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.listelement;
import rnr.src.rnrcore.loc;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class WarehouseOrder implements listelement, IStoreorgelement {
    private static final String[] description = { "Warehouse order description", "Warehouse tender description",
            "Warehouse contest description", "Warehouse bigrace description" };
    private static final int DESCR_ORDER = 0;
    private static final int DESCR_TENDER = 1;
    private static final int DESCR_CONTEST = 2;
    private static final int DESCR_BIGRACE = 3;
    private static final String MACRO_WHAT = "WHAT";
    private static final String MACRO_SOURCE = "SOURCE";
    private static final String MACRO_DESTINATION = "DESTINATION";
    private static int countNumOrder = 0;

    /** Field description */
    public static WarehouseOrder currentOrder = null;
    private boolean is_read = true;
    private String id = "Warehouse order ";
    private IStoreorgelement.Type type = IStoreorgelement.Type.notype;
    private IStoreorgelement.Status status = IStoreorgelement.Status.nostatus;
    private boolean important = false;
    private INPC customer = new CustomerWarehouseAssociation();
    private CoreTime completeTime = null;
    private journable startNote = null;
    private journable finishNote = null;
    private journable[] failNote = null;
    private int type_failed = 0;
    private int rewardFlag = 5;
    private int forfeitFlag = 5;
    private RewardForfeit reward = new RewardForfeit(1.0D, 1.0D, 1.0D);
    private RewardForfeit forfeit = new RewardForfeit(1.0D, 1.0D, 1.0D);
    private int minutes_to_complete = 0;
    private int seconds_to_complete = 0;
    private int mission_state = -1;
    private double fragility = 0.0D;
    private String loadPoint = null;
    private String completePoint = null;
    private String merchandise = "nothing";
    private String racename = "hack";
    private String logoname = "logo";
    private boolean descriptionGenerated = false;
    private String generatedDescription = merchandise;
    @SuppressWarnings("unchecked")
    private final ArrayList<IDeclineOrgListener> listeners = new ArrayList<IDeclineOrgListener>();
    private CoreTime requestTime;

    /**
     * Constructs ...
     *
     */
    public WarehouseOrder() {
        addDeclineListener(new DeclineWarehouseMissionListener());
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static WarehouseOrder createDelieveryOrder() {
        WarehouseOrder order = new WarehouseOrder();

        currentOrder = order;
        order.id += countNumOrder;
        order.type = IStoreorgelement.Type.baseDelivery;
        order.start();
        countNumOrder += 1;

        return order;
    }

    /**
     * Method description
     *
     *
     * @param orderdescription
     */
    public void setOrderDescription(String orderdescription) {
        this.merchandise = orderdescription;
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void setRaceName(String name) {
        this.racename = name;
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void setLogoName(String name) {
        this.logoname = name;
    }

    /**
     * Method description
     *
     */
    public void createStartNote() {
        JournalStartWarehouse.createStartJournalNote(null, this.type, this.merchandise, this.loadPoint,
                this.completePoint, this.racename).start();
    }

    /**
     * Method description
     *
     */
    public void setTypeOrderDelivery() {
        this.type = IStoreorgelement.Type.baseDelivery;
    }

    /**
     * Method description
     *
     */
    public void setTypeOrderTender() {
        this.type = IStoreorgelement.Type.tender;
    }

    /**
     * Method description
     *
     */
    public void setTypeOrderContest() {
        this.type = IStoreorgelement.Type.competition;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getRaceName() {
        return this.racename;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getLogoName() {
        return this.logoname;
    }

    /**
     * Method description
     *
     *
     * @param raceid
     * @param use_semi
     * @param _racename
     * @param _logoname
     */
    public void setTypeOrderBigRace(int raceid, boolean use_semi, String _racename, String _logoname) {
        switch (raceid) {
         case 1 :
             this.type = ((use_semi)
                          ? IStoreorgelement.Type.bigrace1_semi
                          : IStoreorgelement.Type.bigrace1);

             break;

         case 2 :
             this.type = ((use_semi)
                          ? IStoreorgelement.Type.bigrace2_semi
                          : IStoreorgelement.Type.bigrace2);

             break;

         case 3 :
             this.type = ((use_semi)
                          ? IStoreorgelement.Type.bigrace3_semi
                          : IStoreorgelement.Type.bigrace3);

             break;

         case 4 :
             this.type = ((use_semi)
                          ? IStoreorgelement.Type.bigrace4_semi
                          : IStoreorgelement.Type.bigrace4);
        }

        setRaceName(_racename);
        setLogoName(_logoname);
    }

    /**
     * Method description
     *
     *
     * @param min
     * @param sec
     * @param state
     * @param mission_name
     */
    @Override
    public void updateTimeToComplete(int min, int sec, int state, String mission_name) {
        this.minutes_to_complete = min;
        this.seconds_to_complete = sec;
        this.mission_state = state;
        this.status = UrgetAgent.changeStatus(this, this.minutes_to_complete * 60 + this.seconds_to_complete,
                mission_name);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getMissionState() {
        return this.mission_state;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public IStoreorgelement.Type getType() {
        return this.type;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public IStoreorgelement.Status getStatus() {
        return this.status;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isImportant() {
        return this.important;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setImportant(boolean value) {
        this.important = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getName() {
        return this.id;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setName(String value) {
        this.id = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getDescription() {
        if (this.descriptionGenerated) {
            return this.generatedDescription;
        }

        this.descriptionGenerated = true;

        EventGetPointLocInfo info_load = MissionEventsMaker.getLocalisationMissionPointInfo(this.loadPoint);
        EventGetPointLocInfo info_complete = MissionEventsMaker.getLocalisationMissionPointInfo(this.completePoint);
        String MACROFIND = description[0];
        KeyPair[] pairs = new KeyPair[0];
        MerchandizeInformation merchandizeInfo = new MerchandizeInformation(this.merchandise);

        switch (this.type.ordinal()) {
         case 1 :
             MACROFIND = description[0];
             pairs = new KeyPair[3];
             pairs[0] = new KeyPair("WHAT", merchandizeInfo.getRealName());
             pairs[1] = new KeyPair("SOURCE", info_load.short_name);
             pairs[2] = new KeyPair("DESTINATION", info_complete.short_name);

             break;

         case 2 :
             MACROFIND = description[1];
             pairs = new KeyPair[2];
             pairs[0] = new KeyPair("SOURCE", info_load.short_name);
             pairs[1] = new KeyPair("DESTINATION", info_complete.short_name);

             break;

         case 3 :
             MACROFIND = description[2];
             pairs = new KeyPair[2];
             pairs[0] = new KeyPair("SOURCE", info_load.short_name);
             pairs[1] = new KeyPair("DESTINATION", info_complete.short_name);

             break;

         case 4 :
         case 5 :
         case 6 :
         case 7 :
         case 8 :
         case 9 :
         case 10 :
         case 11 :
             MACROFIND = description[3];
             pairs = new KeyPair[3];
             pairs[0] = new KeyPair("SOURCE", info_load.short_name);
             pairs[1] = new KeyPair("DESTINATION", info_complete.short_name);
             pairs[2] = new KeyPair("WHAT", loc.getBigraceShortName(this.racename));
        }

        String macro = loc.getOrgString(MACROFIND);

        this.generatedDescription = MacroKit.Parse(macro, pairs);

        return this.generatedDescription;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getDescriptionRef() {
        return "";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String loadPoint() {
        return this.loadPoint;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String endPoint() {
        return this.completePoint;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public vectorJ pos_start() {
        return pos_load();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public vectorJ pos_load() {
        Place place = MissionSystemInitializer.getMissionsMap().getPlace(this.loadPoint);

        return place.getCoords();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public vectorJ pos_complete() {
        Place place = MissionSystemInitializer.getMissionsMap().getPlace(this.completePoint);

        return place.getCoords();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getRewardFlag() {
        return this.rewardFlag;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getForfeitFlag() {
        return this.forfeitFlag;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public RewardForfeit getReward() {
        return this.reward;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public RewardForfeit getForfeit() {
        return this.forfeit;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public INPC getCustomer() {
        return this.customer;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public CoreTime dateOfRequest() {
        return this.requestTime;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public CoreTime timeToPickUp() {
        return this.requestTime;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public CoreTime timeToComplete() {
        return this.completeTime;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getCargoFragility() {
        return (int) (this.fragility * 100.0D);
    }

    /**
     * Method description
     *
     */
    @Override
    public void start() {
        this.is_read = true;
        addToList();
        updateStatus();
    }

    /**
     * Method description
     *
     */
    @Override
    public void finish() {
        changeStatus(IStoreorgelement.Status.executedMission);
        organaiser.finishMission(this);
    }

    /**
     * Method description
     *
     *
     * @param fail_type
     */
    @Override
    public void fail(int fail_type) {
        changeStatus(IStoreorgelement.Status.failedMission);
        organaiser.finishMission(this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void updateStatus() {
        if ((this.status == IStoreorgelement.Status.executedMission)
                || (this.status == IStoreorgelement.Status.failedMission)) {
            return;
        }

        changeStatus(IStoreorgelement.Status.pendingMission);
    }

    private void changeStatus(IStoreorgelement.Status aimed_status) {
        if (aimed_status == this.status) {
            return;
        }

        this.status = aimed_status;

        journable find_journal = null;

        if ((this.status == IStoreorgelement.Status.pendingMission)
                || (this.status == IStoreorgelement.Status.urgentMission)) {
            find_journal = this.startNote;
            this.startNote = null;
        } else if (this.status == IStoreorgelement.Status.failedMission) {
            this.forfeit.applyFactionRatings(-1.0D);

            if (null != this.failNote) {
                find_journal = this.failNote[this.type_failed];
            }

            this.failNote = null;
        } else if (this.status == IStoreorgelement.Status.executedMission) {
            this.reward.applyFactionRatings(1.0D);
            find_journal = this.finishNote;
            this.finishNote = null;
        }

        if (null == find_journal) {
            return;
        }

        find_journal.start();
    }

    /**
     * Method description
     *
     */
    @Override
    public void makeRead() {
        this.is_read = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isRead() {
        return this.is_read;
    }

    /**
     * Method description
     *
     */
    @Override
    public void deleteFromList() {}

    /**
     * Method description
     *
     */
    @Override
    public void addToList() {
        this.requestTime = new CoreTime();
        organaiser.getInstance().add(this);
        organaiser.getInstance().setCurrentWarehouseOrder(this);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int get_minutes_toFail() {
        return this.minutes_to_complete;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int get_seconds_toFail() {
        return this.seconds_to_complete;
    }

    /**
     * Method description
     *
     */
    @Override
    public void decline() {
        fail(0);

        for (IDeclineOrgListener lst : this.listeners) {
            lst.declined();
        }
    }

    /**
     * Method description
     *
     *
     * @param lst
     */
    @Override
    public void addDeclineListener(IDeclineOrgListener lst) {
        this.listeners.add(lst);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getType_failed() {
        return this.type_failed;
    }

    /**
     * Method description
     *
     *
     * @param type_failed
     */
    public void setType_failed(int type_failed) {
        this.type_failed = type_failed;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getFragility() {
        return this.fragility;
    }

    /**
     * Method description
     *
     *
     * @param fragility
     */
    public void setFragility(double fragility) {
        this.fragility = fragility;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getOrderdescription() {
        return this.merchandise;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public journable[] getFailNote() {
        return this.failNote;
    }

    /**
     * Method description
     *
     *
     * @param failNote
     */
    public void setFailNote(journable[] failNote) {
        this.failNote = failNote;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public journable getFinishNote() {
        return this.finishNote;
    }

    /**
     * Method description
     *
     *
     * @param finishNote
     */
    public void setFinishNote(journable finishNote) {
        this.finishNote = finishNote;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public journable getStartNote() {
        return this.startNote;
    }

    /**
     * Method description
     *
     *
     * @param startNote
     */
    public void setStartNote(journable startNote) {
        this.startNote = startNote;
    }

    /**
     * Method description
     *
     *
     * @param loadPoint
     */
    public void setLoadPoint(String loadPoint) {
        this.loadPoint = loadPoint;
    }

    /**
     * Method description
     *
     *
     * @param completePoint
     */
    public void setCompletePoint(String completePoint) {
        this.completePoint = completePoint;
    }

    /**
     * Method description
     *
     *
     * @param is_read
     */
    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    /**
     * Method description
     *
     *
     * @param type
     */
    public void setType(IStoreorgelement.Type type) {
        this.type = type;
    }

    /**
     * Method description
     *
     *
     * @param status
     */
    public void setStatus(IStoreorgelement.Status status) {
        this.status = status;
    }

    /**
     * Method description
     *
     *
     * @param rewardFlag
     */
    public void setRewardFlag(int rewardFlag) {
        this.rewardFlag = rewardFlag;
    }

    /**
     * Method description
     *
     *
     * @param forfeitFlag
     */
    public void setForfeitFlag(int forfeitFlag) {
        this.forfeitFlag = forfeitFlag;
    }

    /**
     * Method description
     *
     *
     * @param reward
     */
    public void setReward(RewardForfeit reward) {
        this.reward = reward;
    }

    /**
     * Method description
     *
     *
     * @param forfeit
     */
    public void setForfeit(RewardForfeit forfeit) {
        this.forfeit = forfeit;
    }

    /**
     * Method description
     *
     *
     * @param customer
     */
    public void setCustomer(INPC customer) {
        this.customer = customer;
    }

    /**
     * Method description
     *
     *
     * @param requestTime
     */
    public void setRequestTime(CoreTime requestTime) {
        this.requestTime = requestTime;
    }

    /**
     * Method description
     *
     *
     * @param completeTime
     */
    public void setCompleteTime(CoreTime completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int getCountNumOrder() {
        return countNumOrder;
    }

    /**
     * Method description
     *
     *
     * @param countNumOrder
     */
    public static void setCountNumOrder(int countNumOrder) {
        countNumOrder = countNumOrder;
    }

    static class DeclineWarehouseMissionListener implements IDeclineOrgListener {

        /**
         * Method description
         *
         */
        @Override
        public void declined() {
            MissionEventsMaker.declineWareHouseMission();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
