/*
 * @(#)InformationChannel.java   13/08/28
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


package rnr.src.rnrscenario.missions.infochannels;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrorg.journal;
import rnr.src.rnrscenario.missions.Disposable;
import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscenario.missions.MissionManager;
import rnr.src.rnrscr.IMissionInformation;
import rnr.src.rnrscr.MissionDialogs;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public abstract class InformationChannel implements Cloneable, Disposable, IDealyedRealInfo {
    private static final int DESIRED_POINTS_CAPACITY = 4;
    private String uid;
    private final Set<String> avaliblePointsToAppear;
    protected boolean isFinishChannel;
    protected boolean isMain;
    protected String identitie;
    protected String resourceHold;
    private boolean isImmediateChannel;
    protected IMissionInformation cachedInfo;
    protected DelayedInfoInformation delayedInfo;

    /**
     * Constructs ...
     *
     */
    public InformationChannel() {
        this.uid = null;
        this.avaliblePointsToAppear = new HashSet(4);
        this.isFinishChannel = false;
        this.isMain = false;
        this.identitie = null;
        this.resourceHold = null;
        this.isImmediateChannel = false;
        this.cachedInfo = null;
        this.delayedInfo = null;
    }

    protected void renewChachedObject(String resource) {
        this.resourceHold = resource;

        if (null == this.cachedInfo) {
            this.cachedInfo = MissionDialogs.getMissionInfo(this.resourceHold);
        }

        if (this.cachedInfo != null) {
            this.cachedInfo.setChannelImmediate(this.isImmediateChannel);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public IMissionInformation getMInfo() {
        return this.cachedInfo;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isMain() {
        return (this.isMain);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isMainFinishSucces() {
        return ((this.isMain) && (this.isFinishChannel));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isNoMainFinishSucces() {
        return ((!(this.isMain)) && (this.isFinishChannel));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isFinish() {
        return (this.isFinishChannel);
    }

    /**
     * Method description
     *
     *
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Method description
     *
     *
     * @param identitie
     */
    public void setIdentitie(String identitie) {
        this.identitie = identitie;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setMainChannel(boolean value) {
        this.isMain = value;
    }

    /**
     * Method description
     *
     *
     * @param points
     */
    public void addPlaces(List<String> points) {
        assert(null != points) : "points must be non-null reference";
        this.avaliblePointsToAppear.addAll(points);
    }

    Set<String> whereCanAppear() {
        return this.avaliblePointsToAppear;
    }

    /**
     * Method description
     *
     */
    @Override
    public void dispose() {
        MissionEventsMaker.channelCleanResources(this.uid);
        clearResources();
    }

    /**
     * Method description
     *
     */
    public final void makeNotActive() {
        if (null == this.cachedInfo) {
            return;
        }

        this.cachedInfo.makeInformationNotActive();
        journal.getInstance().updateActiveNotes();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getUid() {
        return this.uid;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setFinishChannel(boolean value) {
        this.isFinishChannel = value;
    }

    /**
     * Method description
     *
     *
     * @param info
     * @param resource
     */
    public final void makeStartChannelPost(MissionInfo info, String resource) {
        this.resourceHold = resource;
        renewChachedObject(this.resourceHold);
        postStartMissionInfo(info, this.resourceHold);
        MissionEventsMaker.channalIsActive(this.uid);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param resource
     * @param useMainInfo
     */
    public void postInfo(String mission_name, String resource, boolean useMainInfo) {
        this.resourceHold = resource;
        renewChachedObject(resource);
        realInfoPost(mission_name, this.resourceHold, useMainInfo);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param resource
     * @param useMainInfo
     */
    public final void realInfoPost(String mission_name, String resource, boolean useMainInfo) {
        if ((!(MissionManager.getMSEnable())) && (!(this.isFinishChannel))) {
            return;
        }

        this.delayedInfo = new DelayedInfoInformation(mission_name, resource, useMainInfo);
        DelayedRealInfoPoster.getInstance().addPoster(this);
        MissionEventsMaker.channalIsActive(this.uid);
    }

    /**
     * Method description
     *
     *
     * @param paramMissionInfo
     * @param paramString
     */
    public abstract void postStartMissionInfo(MissionInfo paramMissionInfo, String paramString);

    abstract void clearResources();

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public abstract InformationChannel clone();

    /**
     * Method description
     *
     *
     * @param isImmediateChannel
     */
    public void setImmediateChannel(boolean isImmediateChannel) {
        this.isImmediateChannel = isImmediateChannel;
    }
}


//~ Formatted in DD Std on 13/08/28
