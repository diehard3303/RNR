/*
 * @(#)DelayedChannel.java   13/08/28
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

import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.AbstractMissionsMap;
import rnr.src.rnrscenario.missions.map.MapEventsListener;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;
import rnr.src.rnrscr.IChannelPointChanges;
import rnr.src.rnrscr.IMissionInformation;
import rnr.src.scriptEvents.EventListener;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public abstract class DelayedChannel extends InformationChannel
        implements MapEventsListener, EventListener, IChannelPointChanges {
    private static final double CHANNEL_DISNANCE = 450.0D;
    private AbstractMissionsMap map = null;
    private String missionName = null;
    private boolean immediatelyPost = false;
    private String channelPoint = null;
    private boolean channelListenerAdded = false;

    DelayedChannel(AbstractMissionsMap map) {
        assert(null != map) : "map must be non-null reference";
        this.map = map;
    }

    @Override
    protected void renewChachedObject(String resource) {
        super.renewChachedObject(resource);

        if ((!(this.channelListenerAdded)) && (null != this.cachedInfo)) {
            this.channelListenerAdded = true;
            this.cachedInfo.addChannelPlaceChangedListener(this);
        }
    }

    void immediatelyPost(boolean need) {
        this.immediatelyPost = need;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMissionName() {
        return this.missionName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getPointName() {
        return this.channelPoint;
    }

    /**
     * Method description
     *
     *
     * @param pointName
     */
    @Override
    public void setOnPoint(String pointName) {
        this.channelPoint = pointName;
        this.map.addListener(this, 450.0D);
    }

    /**
     * Method description
     *
     */
    @Override
    public void freeFromPoint() {
        if (null != this.channelPoint) {
            this.channelPoint = null;
            this.map.removeListener(this);
        }
    }

    AbstractMissionsMap getMap() {
        return this.map;
    }

    /**
     * Method description
     *
     *
     * @param info
     * @param resource
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public final void postStartMissionInfo(MissionInfo info, String resource) {
        this.missionName = info.getName();
        renewChachedObject(resource);
        postStartMissionInfo_Detailed(info, resource);

        if (whereCanAppear().isEmpty()) {
            Place place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(1);
            ArrayList places = new ArrayList();

            places.add(place.getName());
            addPlaces(places);
            this.cachedInfo.setPointName(place.getName());
            realInfoPost(this.missionName, resource, false);
        }
    }

    abstract void postStartMissionInfo_Detailed(MissionInfo paramMissionInfo, String paramString);

    /**
     * Method description
     *
     *
     * @param missionName
     * @param resource
     * @param useMainInfo
     */
    @Override
    public final void postInfo(String missionName, String resource, boolean useMainInfo) {
        this.missionName = missionName;
        renewChachedObject(resource);

        if ((this.immediatelyPost) || ((this.channelPoint != null) && (this.map.onPlace(this, 450.0D)))) {
            realInfoPost(missionName, this.resourceHold, false);
            this.map.removeListener(this);
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public final void placeAreaEntered() {
        realInfoPost(this.missionName, this.resourceHold, false);
        this.map.removeListener(this);
    }

    /**
     * Method description
     *
     *
     * @param where
     */
    public void placeAreaLeaved(Place where) {}

    /**
     * Method description
     *
     *
     * @param eventTuple
     */
    @Override
    public final void eventHappened(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event : eventTuple) {
            if (event instanceof ScriptEvent) {
                realInfoPost(this.missionName, this.resourceHold, false);
                EventsController.getInstance().removeListener(this);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public abstract DelayedChannel clone();
}


//~ Formatted in DD Std on 13/08/28
