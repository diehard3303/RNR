/*
 * @(#)MissionsInfoPoster.java   13/08/28
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

import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscenario.missions.MissionsController;
import rnr.src.rnrscenario.missions.NeedPostMissionInfoEvent;
import rnr.src.rnrscenario.missions.StartMissionListeners;
import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptEvents.EventListener;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class MissionsInfoPoster implements MissionsController, EventListener {
    static final long serialVersionUID = 0L;
    private final Map<String, PostedInfo> postedInfo;
    private final Map<String, List<ChannelWithResource>> channelsOfDependentMissions;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MissionsInfoPoster() {
        this.postedInfo = new LinkedHashMap();
        this.channelsOfDependentMissions = new HashMap();
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void eventHappened(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event : eventTuple) {
            if (event instanceof NeedPostMissionInfoEvent) {
                String missionName = ((NeedPostMissionInfoEvent) event).getMissionName();

                if (null != missionName) {
                    List channels = this.channelsOfDependentMissions.remove(missionName);

                    if (null != channels) {
                        for (ChannelWithResource channelWithResource : channels) {
                            channelWithResource.post();
                        }
                    } else {
                        MissionsLogger.getInstance().doLog(
                            "MissionsInfoPoster: trying to post info of unexisting mission " + missionName,
                            Level.SEVERE);
                    }
                }
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param target
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void uploadMission(MissionInfo target) {
        assert(null != target) : "target must be non-null reference";
        StartMissionListeners.getInstance().registerChannelEventListener(target.getName(), target);
        StartMissionListeners.getInstance().registerStartMissionListener(target.getName(), target);

        List channels = target.getInfoStartChannels();

        if (channels.isEmpty()) {
            MissionsLogger.getInstance().doLog("came mission info without avalible start channels", Level.SEVERE);

            return;
        }

        List resources = new LinkedList();
        List pointsToFree = new LinkedList();

        this.postedInfo.put(target.getName(), new PostedInfo(resources, pointsToFree, target.getName()));

        PostMisssionOrNotDecision postInfoOrNot;

        if (!(target.hasStartDependence())) {
            postInfoOrNot = new PostMisssionOrNotDecision() {
                @Override
                void makeDesision(InformationChannel channel, String resource) {
                    channel.makeStartChannelPost(this.target, resource);
                }
            };
        } else {
            if (target.isDependByActivation()) {
                List store = new LinkedList();

                this.channelsOfDependentMissions.put(target.getName(), store);
                postInfoOrNot = new PostMisssionOrNotDecision() {
                    @Override
                    void makeDesision(InformationChannel channel, String resource) {
                        this.store.add(new MissionsInfoPoster.ChannelWithResource(resource, channel, this.target));
                    }
                };
            } else {
                if (!(channels.isEmpty())) {
                    MissionsLogger.getInstance().doLog("found dependent mission with start channels", Level.WARNING);
                }

                for (InformationChannelData channelData : channels) {
                    pointsToFree.addAll(channelData.getPlacesNames());
                }

                this.postedInfo.put(target.getName(),
                                    new PostedInfo(Collections.emptyList(), pointsToFree, target.getName()));

                return;
            }
        }

        PostMisssionOrNotDecision postInfoOrNot;

        for (InformationChannelData channelData : channels) {
            try {
                InformationChannel channelToPost = channelData.makeWare(target.getName(), target.getFinishPoint(),
                                                       target.getQuestItemPlacement(), target);

                pointsToFree.addAll(channelData.getPlacesNames());

                InfoChannelEventsListener channelsListener = new InfoChannelEventsListener(channelToPost.getUid());
                List resourceDisposer = new LinkedList();

                resourceDisposer.add(new PostedResourceMakeNotActive(target.getName()));
                resourceDisposer.add(new PostedResourceDisposer(target.getName()));

                List emptyList = Collections.emptyList();

                channelsListener.useCallbacks(
                    emptyList, emptyList, emptyList, resourceDisposer,
                    InformationChannelsFactory.getInstance().getCloseChannelInfo(channelData.getChannelName()));
                EventsController.getInstance().addListener(channelsListener);
                resources.add(channelToPost);
                postInfoOrNot.makeDesision(channelToPost, channelData.getResource());
            } catch (NoSuchChannelException e) {
                String errorDescription = "Invalid channel name came: " + channelData.getChannelName();

                MissionsLogger.getInstance().doLog(errorDescription, Level.SEVERE);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param target
     */
    @Override
    public void unloadMission(String target) {
        if (null == target) {
            return;
        }

        StartMissionListeners.getInstance().unregisterChannelEventListener(target);
        StartMissionListeners.getInstance().unregisterStartMissionListener(target);
        new PostedResourceDisposer(target).act();
    }

    /**
     * Method description
     *
     *
     * @param missionName
     * @param channelUid
     *
     * @return
     */
    public InformationChannel getPostedInformationChannel(String missionName, String channelUid) {
        if (this.postedInfo == null) {
            return null;
        }

        if (!(this.postedInfo.containsKey(missionName))) {
            return null;
        }

        PostedInfo channelsInfo = this.postedInfo.get(missionName);

        assert(channelsInfo != null);
        assert(channelsInfo.infoChannels != null);

        for (InformationChannel channel : channelsInfo.infoChannels) {
            if (channel.getUid().compareTo(channelUid) == 0) {
                return channel;
            }
        }

        return null;
    }

    private static final class ChannelWithResource {
        private String resource = null;
        private MissionInfo mission = null;
        private InformationChannel channel = null;

        ChannelWithResource(String resource, InformationChannel channel, MissionInfo mission) {
            this.resource = resource;
            this.channel = channel;
            this.mission = mission;
        }

        void post() {
            this.channel.makeStartChannelPost(this.mission, this.resource);
        }
    }


    private abstract class PostMisssionOrNotDecision {
        abstract void makeDesision(InformationChannel paramInformationChannel, String paramString);
    }


    private static final class PostedInfo {
        private List<InformationChannel> infoChannels = null;
        private Iterable<String> pointsInWorld = null;
        private String mission_name = null;

        PostedInfo(List<InformationChannel> infoChannels, Iterable<String> pointsInWorld, String mn) {
            assert(null != infoChannels) : "infoChannels must be non-null reference";
            assert(null != pointsInWorld) : "pointsInWorld must be non-null reference";
            this.infoChannels = infoChannels;
            this.pointsInWorld = pointsInWorld;
            this.mission_name = mn;
        }

        void dispose() {
            for (InformationChannel informationChannel : this.infoChannels) {
                informationChannel.dispose();
            }
        }

        void makeNotActive() {
            for (InformationChannel informationChannel : this.infoChannels) {
                informationChannel.makeNotActive();
            }
        }
    }


    private class PostedResourceDisposer extends ScriptAction {
        static final long serialVersionUID = 0L;
        private String missionWhichResourcesNeedToBeDisposed = null;

        /**
         * Constructs ...
         *
         *
         * @param paramString
         */
        public PostedResourceDisposer(String paramString) {
            assert(null != paramString) : " must be non-null reference";
            this.missionWhichResourcesNeedToBeDisposed = paramString;
        }

        /**
         * Method description
         *
         */
        @Override
        public void act() {
            MissionsInfoPoster.PostedInfo resources =
                MissionsInfoPoster.this.postedInfo.remove(this.missionWhichResourcesNeedToBeDisposed);

            if (null == resources) {
                return;
            }

            resources.dispose();
        }
    }


    private class PostedResourceMakeNotActive extends ScriptAction {
        static final long serialVersionUID = 0L;
        private String mission_name = null;

        /**
         * Constructs ...
         *
         *
         * @param paramString
         */
        public PostedResourceMakeNotActive(String paramString) {
            this.mission_name = paramString;
        }

        /**
         * Method description
         *
         */
        @Override
        public void act() {
            MissionsInfoPoster.PostedInfo resources = MissionsInfoPoster.this.postedInfo.get(this.mission_name);

            if (null == resources) {
                return;
            }

            resources.makeNotActive();
        }
    }
}


//~ Formatted in DD Std on 13/08/28
