/*
 * @(#)Scorgelement.java   13/08/26
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
import rnr.src.rnrcore.listelement;
import rnr.src.rnrcore.loc;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrloggers.ScriptsLogger;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Scorgelement implements listelement, IStoreorgelement {
    private boolean is_read = true;
    private String id = "unknown";
    private IStoreorgelement.Type type = IStoreorgelement.Type.notype;
    private IStoreorgelement.Status status = IStoreorgelement.Status.nostatus;
    private boolean important = false;
    private String description = "non initialized";
    private int minutes_to_complete = 0;
    private int seconds_to_complete = 0;
    private journable startNote = null;
    private journable finishNote = null;
    private journable[] failNote = null;
    private int choose_fail_note = 0;
    private String startPoint = null;
    private String loadPoint = null;
    private String completePoint = null;
    private int mission_state = -1;
    private ArrayList<IDeclineOrgListener> listeners = new ArrayList();
    private final int reward_flag;
    private final RewardForfeit reward;
    private final int forfeit_flag;
    private final RewardForfeit forfeit;
    private final INPC customer;
    private final MissionTime coef_time_to_pickup;
    private final MissionTime coef_time_to_complete;
    private CoreTime requestTime;
    private CoreTime pickUpTime;
    private CoreTime completeTime;
    private IQuestCargo cargo;

    /**
     * Constructs ...
     *
     *
     * @param id
     * @param type
     * @param important
     * @param reward_flag
     * @param reward
     * @param forfeit_flag
     * @param forfeit
     * @param description
     * @param customer
     * @param coef_time_to_pickup
     * @param coef_time_to_complete
     * @param startNote
     * @param finishNote
     * @param failNote
     */
    public Scorgelement(String id, IStoreorgelement.Type type, boolean important, int reward_flag,
                        RewardForfeit reward, int forfeit_flag, RewardForfeit forfeit, String description,
                        INPC customer, MissionTime coef_time_to_pickup, MissionTime coef_time_to_complete,
                        journable startNote, journable finishNote, journable[] failNote) {
        this.id = id;
        this.type = type;
        this.important = important;
        this.reward_flag = reward_flag;
        this.reward = reward;
        this.forfeit_flag = forfeit_flag;
        this.forfeit = forfeit;
        this.description = description;
        this.customer = customer;
        this.coef_time_to_pickup = coef_time_to_pickup;
        this.coef_time_to_complete = coef_time_to_complete;
        this.startNote = startNote;
        this.finishNote = finishNote;
        this.failNote = failNote;
    }

    /**
     * Method description
     *
     *
     * @param cargo
     */
    public void setCargoParams(QuestCargoParams cargo) {
        this.cargo = cargo;
    }

    /**
     * Method description
     *
     *
     * @param year
     * @param month
     * @param date
     * @param hour
     * @param minuten
     */
    public void setRequestTime(int year, int month, int date, int hour, int minuten) {
        this.requestTime = new CoreTime(year, month, date, hour, minuten);
    }

    /**
     * Method description
     *
     *
     * @param year
     * @param month
     * @param date
     * @param hour
     * @param minuten
     */
    public void setPickupTime(int year, int month, int date, int hour, int minuten) {
        this.pickUpTime = new CoreTime(year, month, date, hour, minuten);
    }

    /**
     * Method description
     *
     *
     * @param year
     * @param month
     * @param date
     * @param hour
     * @param minuten
     */
    public void setCompleteTime(int year, int month, int date, int hour, int minuten) {
        this.completeTime = new CoreTime(year, month, date, hour, minuten);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getCoefTimePickup() {
        return this.coef_time_to_pickup.getCoef();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getCoefTimeComplete() {
        return this.coef_time_to_complete.getCoef();
    }

    /**
     * Method description
     *
     *
     * @param min
     * @param sec
     * @param state
     * @param missionName
     */
    @Override
    public void updateTimeToComplete(int min, int sec, int state, String missionName) {
        this.minutes_to_complete = min;
        this.seconds_to_complete = sec;
        this.mission_state = state;
        this.status = UrgetAgent.changeStatus(this, this.minutes_to_complete * 60 + this.seconds_to_complete,
                missionName);
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
    public String getRaceName() {
        return this.description;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getLogoName() {
        return this.description;
    }

    /**
     * Method description
     *
     *
     * @param _start_point
     * @param _load_point
     * @param _complete_point
     */
    public void setSerialPoints(String _start_point, String _load_point, String _complete_point) {
        this.startPoint = _start_point;
        this.loadPoint = _load_point;
        this.completePoint = _complete_point;
    }

    /**
     * Method description
     *
     *
     * @param _complete_point
     */
    public void changeDestination(String _complete_point) {
        this.completePoint = _complete_point;
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
     * @return
     */
    @Override
    public String getDescription() {
        return loc.getOrgString(this.description);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getDescriptionRef() {
        return this.description;
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
        Place place = MissionSystemInitializer.getMissionsMap().getPlace(this.startPoint);

        if (null == place) {
            return new vectorJ();
        }

        return place.getCoords();
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

        if (null == place) {
            return new vectorJ();
        }

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

        if (null == place) {
            return new vectorJ();
        }

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
        return this.reward_flag;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getForfeitFlag() {
        return this.forfeit_flag;
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
        return this.pickUpTime;
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
        if ((null == this.cargo) || (!(this.cargo.hasFragility()))) {
            return 0;
        }

        int result = (int) (this.cargo.getFragility() * 100.0D);

        if (result > 100) {
            result = 100;
        } else if (result < 0) {
            result = 0;
        }

        return result;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getFragility() {
        if ((null == this.cargo) || (!(this.cargo.hasFragility()))) {
            return 0.0D;
        }

        return this.cargo.getFragility();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean hasFragility() {
        return ((null != this.cargo) && (this.cargo.hasFragility()));
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
            find_journal = this.failNote[this.choose_fail_note];
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

    private void addLogRecord(String message) {
        ScriptsLogger.getInstance().log(Level.INFO, 1,
                                        "org: name == " + this.id + " \"" + this.description + "\" " + message);
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
    }

    /**
     * Method description
     *
     */
    @Override
    public void start() {
        addToList();
        updateStatus();
        this.is_read = false;
        addLogRecord("started");
    }

    /**
     * Method description
     *
     */
    @Override
    public void finish() {
        changeStatus(IStoreorgelement.Status.executedMission);
        organaiser.finishMission(this);
        addLogRecord("finished");
    }

    /**
     * Method description
     *
     *
     * @param type_fail
     */
    @Override
    public void fail(int type_fail) {
        this.choose_fail_note = type_fail;
        changeStatus(IStoreorgelement.Status.failedMission);
        organaiser.finishMission(this);
        addLogRecord("failed");
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
     *
     * @return
     */
    public String gDescription() {
        return this.description;
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
    public String getDescriptionOriginal() {
        return this.description;
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
     * @return
     */
    public String getStartPoint() {
        return this.startPoint;
    }

    /**
     * Method description
     *
     *
     * @param startPoint
     */
    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getLoadPoint() {
        return this.loadPoint;
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
     * @return
     */
    public String getCompletePoint() {
        return this.completePoint;
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
     * @param status
     */
    public void setStatus(IStoreorgelement.Status status) {
        this.status = status;
    }

    /**
     * Method description
     *
     *
     * @param element
     */
    public void submitLoadedOrgNode(Scorgelement element) {
        this.is_read = element.is_read;
        this.status = element.status;
        this.requestTime = element.requestTime;
        this.pickUpTime = element.pickUpTime;
        this.completeTime = element.completeTime;
        this.minutes_to_complete = element.minutes_to_complete;
        this.seconds_to_complete = element.seconds_to_complete;
        this.choose_fail_note = element.choose_fail_note;
        this.startPoint = element.startPoint;
        this.loadPoint = element.loadPoint;
        this.completePoint = element.completePoint;
        this.mission_state = element.mission_state;
        this.listeners = element.listeners;
        this.reward.sMoney(element.reward.gMoney());
        this.reward.sRank(element.reward.gRank());
        this.reward.sRate(element.reward.gRate());
        this.forfeit.sMoney(element.forfeit.gMoney());
        this.forfeit.sRank(element.forfeit.gRank());
        this.forfeit.sRate(element.forfeit.gRate());
        this.cargo = element.cargo;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<IDeclineOrgListener> getListeners() {
        return this.listeners;
    }

    /**
     * Method description
     *
     *
     * @param listeners
     */
    public void setListeners(ArrayList<IDeclineOrgListener> listeners) {
        this.listeners = listeners;
    }
}


//~ Formatted in DD Std on 13/08/26
