/*
 * @(#)MissionInfoNotDialog.java   13/08/28
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


package rnr.src.rnrscr;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrloggers.MissionsLogger;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class MissionInfoNotDialog implements IMissionInformation {
    private boolean is_active = true;
    private boolean was_freed = false;
    private boolean f_hasPoint = false;
    private boolean is_info_posted = false;
    private boolean f_waitAnswer = true;
    private final ArrayList<IChannelPointChanges> changePointListeners = new ArrayList<IChannelPointChanges>();
    private ArrayList<String> dependantMissions = null;
    private boolean isChannelImmediate = false;
    private final String mission;
    private String pointName;
    private final String dialogname;
    private final String channelId;
    private boolean played;
    private final boolean isFinishInfo;

    /**
     * Constructs ...
     *
     *
     * @param channelId
     * @param mission
     * @param resource
     * @param played
     * @param isFinishInfo
     * @param dependantMissions
     */
    public MissionInfoNotDialog(String channelId, String mission, String resource, boolean played,
                                boolean isFinishInfo, ArrayList<String> dependantMissions) {
        this.dialogname = resource;
        this.mission = mission;
        this.channelId = channelId;
        this.played = played;
        this.isFinishInfo = isFinishInfo;
        this.dependantMissions = dependantMissions;
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    @Override
    public void addChannelPlaceChangedListener(IChannelPointChanges listener) {
        this.changePointListeners.add(listener);

        if (this.f_hasPoint) {
            listener.setOnPoint(this.pointName);
        } else {
            listener.freeFromPoint();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMissionName() {
        return this.mission;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getDialogName() {
        return this.dialogname;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getChannelId() {
        return this.channelId;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public ArrayList<String> getDependantMissions() {
        return this.dependantMissions;
    }

    /**
     * Method description
     *
     *
     * @param time
     *
     * @return
     */
    @Override
    public boolean checkTimePeriods(CoreTime time) {
        return true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isDialog() {
        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean wasVoterFreed() {
        return this.was_freed;
    }

    /**
     * Method description
     *
     */
    @Override
    public void freeVoter() {
        this.was_freed = true;
    }

    /**
     * Method description
     *
     */
    @Override
    public void playMissionInfo() {
        this.played = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean wasPlayed() {
        return this.played;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isFinishInformaton() {
        return this.isFinishInfo;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getIdentitie() {
        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isInformationActive() {
        return this.is_active;
    }

    /**
     * Method description
     *
     */
    @Override
    public void makeInformationNotActive() {
        this.is_active = false;
    }

    /**
     * Method description
     *
     */
    @Override
    public void freeFromPoint() {
        this.f_hasPoint = false;

        for (IChannelPointChanges item : this.changePointListeners) {
            item.freeFromPoint();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean hasPoint() {
        return this.f_hasPoint;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getPointName() {
        return this.pointName;
    }

    /**
     * Method description
     *
     *
     * @param point_name
     */
    @Override
    public void setPointName(String point_name) {
        this.f_hasPoint = true;
        this.pointName = point_name;

        if (point_name == null) {
            MissionsLogger.getInstance().doLog("MissionInfoNotDialog.setPointName", Level.INFO);
        }

        for (IChannelPointChanges item : this.changePointListeners) {
            item.setOnPoint(point_name);
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void postInfo() {
        this.is_info_posted = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isInfoPosted() {
        return this.is_info_posted;
    }

    /**
     * Method description
     *
     */
    @Override
    public void receiveAnswer() {
        this.f_waitAnswer = false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean hasQuestion() {
        return this.f_waitAnswer;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void setChannelImmediate(boolean value) {
        this.isChannelImmediate = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isChannelImmediate() {
        return this.isChannelImmediate;
    }
}


//~ Formatted in DD Std on 13/08/28
