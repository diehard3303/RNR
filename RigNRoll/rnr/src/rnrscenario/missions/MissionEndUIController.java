/*
 * @(#)MissionEndUIController.java   13/08/28
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

import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrscenario.missions.infochannels.CarDialogChannel;
import rnr.src.rnrscenario.missions.infochannels.InformationChannel;
import rnr.src.scriptEvents.EventListener;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.ScriptEvent;
import rnr.src.scriptEvents.SuccessEventChannel;
import rnr.src.scriptEvents.SuccessEventPicture;

//~--- JDK imports ------------------------------------------------------------

import java.util.LinkedList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class MissionEndUIController implements EventListener {

    /** Field description */
    public static int maxMission = 0;

    /** Field description */
    public static int curMission = 0;
    private static boolean numcheck = false;

    /** Field description */
    public String missionName;

    /** Field description */
    public List<ResourceInfo> resources;
    private int numMission;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MissionEndUIController() {
        this.missionName = null;
        this.resources = new LinkedList();
        this.numMission = 0;
    }

    /**
     * Method description
     *
     *
     * @param i
     */
    public static void setMaxMission(int i) {
        maxMission = 0;
        curMission = 0;
        numcheck = true;
    }

    /**
     * Method description
     *
     */
    public void realEnd() {
        EventsController.getInstance().removeListener(this);
        MissionEventsMaker.missionOnMoney(this.missionName);
        MissionList.dialogsFinished();
    }

    /**
     * Method description
     *
     */
    public static void nextMissionToEvent() {
        if (numcheck) {
            curMission += 1;

            if (curMission != maxMission) {
                return;
            }

            numcheck = false;
        }
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     */
    @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        if ((numcheck) && (this.numMission != curMission)) {
            return;
        }

        for (ScriptEvent event : eventTuple) {
            if (event instanceof SuccessEventPicture) {
                realEnd();

                return;
            }

            if (event instanceof SuccessEventChannel) {
                endDialog();

                return;
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param channel
     * @param missionName
     * @param resourceName
     */
    public void placeRecourcesThroghChannel(InformationChannel channel, String missionName, String resourceName) {
        this.missionName = missionName;

        if (null == channel) {
            return;
        }

        this.resources.add(new ResourceInfo(channel, missionName, resourceName));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean postInfo() {
        boolean r = false;

        for (ResourceInfo resourceInfo : this.resources) {
            boolean miss_passanger = resourceInfo.channel instanceof CarDialogChannel;
            boolean passanger_present = MissionEventsMaker.isPassanerSlotBuzzy();

            if (passanger_present) {
                passanger_present = MissionEventsMaker.samePassanger(this.missionName);
            }

            if ((resourceInfo.channel.isFinish()) && (resourceInfo.channel.isMain())
                    && (((!(miss_passanger)) || (passanger_present)))) {
                resourceInfo.place();
                r = true;
            }
        }

        return r;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean reg() {
        if (this.missionName == null) {
            return false;
        }

        EventsController.getInstance().addListener(this);
        this.numMission = maxMission;

        if (numcheck) {
            maxMission += 1;
        }

        return true;
    }

    /**
     * Method description
     *
     */
    public void endDialog() {}

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public final class ResourceInfo {
        private InformationChannel channel = null;
        private String missionName = null;
        private String resourceName = null;
        private final boolean succes = true;

        /**
         * Constructs ...
         *
         *
         * @param paramInformationChannel
         * @param paramString1
         * @param paramString2
         */
        public ResourceInfo(InformationChannel paramInformationChannel, String paramString1, String paramString2) {
            this.channel = paramInformationChannel;
            this.missionName = paramString1;
            this.resourceName = paramString2;
        }

        void place() {
            this.channel.postInfo(this.missionName, this.resourceName, true);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
