/*
 * @(#)MissionSystemInitializer.java   13/08/28
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


package rnr.src.rnrscenario.missions;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscenario.missions.infochannels.ArticleChannel;
import rnr.src.rnrscenario.missions.infochannels.CarDialogChannel;
import rnr.src.rnrscenario.missions.infochannels.CbvChannel;
import rnr.src.rnrscenario.missions.infochannels.ExternalChannel;
import rnr.src.rnrscenario.missions.infochannels.InBarTalkChannel;
import rnr.src.rnrscenario.missions.infochannels.InfoChannelEventCallback.ChannelClose;
import rnr.src.rnrscenario.missions.infochannels.InformationChannelsFactory;
import rnr.src.rnrscenario.missions.infochannels.LiveDialogChannel;
import rnr.src.rnrscenario.missions.infochannels.MissionsInfoPoster;
import rnr.src.rnrscenario.missions.infochannels.NewspaperChannel;
import rnr.src.rnrscenario.missions.infochannels.RadioChannel;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.PointsController;
import rnr.src.rnrscenario.missions.requirements.MissionsLog;
import rnr.src.rnrscenario.missions.starters.ConditionChecker;
import rnr.src.rnrscenario.sctask;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.MissionEndchecker;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class MissionSystemInitializer {
    private static MissionsMap map = null;
    private static MissionsInfoPoster channelsPoster = null;
    private static MissionManager missionsManager = null;
    private static MissionList activatedMissions = null;
    private static MissionFactory missionFactory = null;
    private static MissionStartResourses missionsStartResources = null;
    private static sctask missionDumper = null;

    /**
     * Method description
     *
     */
    public static void firstInitializeSystem() {}

    /**
     * Method description
     *
     */
    public static void initializeSystem() {
        map = new MissionsMap();

        InformationChannelsFactory channelsFactory = InformationChannelsFactory.getInstance();

        channelsFactory.addChannelPrototype("ExternalChannel", new ExternalChannel(), true, true, false,
                InfoChannelEventCallback.ChannelClose.DELAYED_ANSWERS);
        channelsFactory.addChannelPrototype("CbvChannel", new CbvChannel(map), true, false, true,
                InfoChannelEventCallback.ChannelClose.DIALOG);
        channelsFactory.addChannelPrototype("CarDialogChannel", new CarDialogChannel(), true, false, true,
                InfoChannelEventCallback.ChannelClose.DIALOG);
        channelsFactory.addChannelPrototype("LiveDialogChannel", new LiveDialogChannel(), true, false, true,
                InfoChannelEventCallback.ChannelClose.DIALOG);
        channelsFactory.addChannelPrototype("RadioChannel", new RadioChannel(map), false, false, false,
                InfoChannelEventCallback.ChannelClose.DELAYED_ANSWERS);
        channelsFactory.addChannelPrototype("NewspaperChannel", new NewspaperChannel(), false, true, false,
                InfoChannelEventCallback.ChannelClose.DELAYED_ANSWERS);
        channelsFactory.addChannelPrototype("ArticleChannel", new ArticleChannel(), false, true, false,
                InfoChannelEventCallback.ChannelClose.DELAYED_ANSWERS);
        channelsFactory.addChannelPrototype("InBarTalkChannel", new InBarTalkChannel(), true, true, true,
                InfoChannelEventCallback.ChannelClose.DIALOG);
        MissionInfo.init();
        missionsStartResources = new MissionStartResourses();
        channelsPoster = new MissionsInfoPoster();
        missionFactory = new MissionFactory();
        activatedMissions = new MissionList(missionFactory);
        EventsController.getInstance().addListener(activatedMissions);
        EventsController.getInstance().addListener(channelsPoster);
        missionsManager = new MissionManager(map);
        missionsManager.addMissionController(missionsStartResources);
        missionsManager.addMissionController(channelsPoster);
        missionsManager.addMissionController(missionFactory);

        Dumper dumper = new Dumper();

        missionDumper = dumper;
        dumper.addTask(PointsController.getInstance());
        dumper.addTask(MissionsLog.getInstance());
        dumper.on();
    }

    /**
     * Method description
     *
     */
    public static void deinitializeSystem() {
        InformationChannelsFactory.deinit();
        MissionEndchecker.deinit();
        MissionInfo.deinit();
        ConditionChecker.clearAllCheckers();
        map.deinit();
        map = null;

        if (null != missionsManager) {
            missionsManager.finishImmediately();
            missionsManager.deinitialize();
            missionsManager = null;
        }

        missionsStartResources = null;
        channelsPoster = null;
        missionFactory = null;
        activatedMissions = null;

        if (null != missionDumper) {
            missionDumper.finishImmediately();
        }

        missionDumper = null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static MissionList getActivatedMissions() {
        return activatedMissions;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static MissionManager getMissionsManager() {
        return missionsManager;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static MissionsMap getMissionsMap() {
        return map;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static MissionsInfoPoster getChannelsPoster() {
        return channelsPoster;
    }
}


//~ Formatted in DD Std on 13/08/28
