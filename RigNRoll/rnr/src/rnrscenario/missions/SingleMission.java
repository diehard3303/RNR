/*
 * @(#)SingleMission.java   13/08/28
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

import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrscenario.missions.infochannels.InformationChannel;
import rnr.src.rnrscenario.missions.infochannels.InformationChannelData;
import rnr.src.rnrscenario.missions.infochannels.NoSuchChannelException;
import rnr.src.rnrscenario.missions.map.PointsController;
import rnr.src.scriptEvents.ComplexEventReaction;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

final class SingleMission {
    private ArrayList<MissionPhase> waysToEnd = null;
    private String serializationUid = null;
    private String mission_name = null;

    /**
     * Constructs ...
     *
     *
     * @param mission_name
     * @param endConditions
     */
    @SuppressWarnings("unchecked")
    public SingleMission(String mission_name, List<MissionPhase> endConditions) {
        this.waysToEnd = new ArrayList<MissionPhase>(endConditions);
        this.mission_name = mission_name;
    }

    void setSerializationUid(String missionName) {
        this.serializationUid = missionName;
    }

    String getSerializationUid() {
        return this.serializationUid;
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean checkEnd(List<ScriptEvent> eventTuple) {
        boolean missionFinished = false;

        for (Iterator<MissionPhase> possibleEndIterator = this.waysToEnd.iterator(); possibleEndIterator.hasNext(); ) {
            MissionPhase possibleEnd = possibleEndIterator.next();

            if (possibleEnd.getPhaseReaction().react(eventTuple)) {
                for (InformationChannelData channelDescription : possibleEnd.getInfoChannels()) {
                    try {
                        InformationChannel channel = channelDescription.makeWare();
                        List pointsToClear = new ArrayList(channelDescription.getPlacesNames());
                        int TASK_DELAY = 3600;

                        if (channel.isNoMainFinishSucces()) {
                            Disposable resourceCleaner = new Disposable(channel, pointsToClear) {
                                @Override
                                public void dispose() {
                                    this.channel.dispose();
                                    PointsController.getInstance().freePoints(
                                        this.pointsToClear,
                                        MissionSystemInitializer.getMissionsManager().getMissionPlacement(
                                            SingleMission.this.mission_name));
                                }
                            };

                            DelayedResourceDisposer.getInstance().addResourceToDispose(resourceCleaner,
                                    this.mission_name, channel.getUid(), 3600);
                        }

                        possibleEnd.getUIController().placeRecourcesThroghChannel(channel, this.mission_name,
                                channelDescription.getResource());
                    } catch (NoSuchChannelException e) {
                        MissionsLogger.getInstance().doLog("wrong channel name data: " + e.getMessage(), Level.SEVERE);
                    }
                }

                if (possibleEnd.getUIController().reg()) {
                    if (!(possibleEnd.getUIController().postInfo())) {
                        possibleEnd.getUIController().endDialog();
                    }
                } else {
                    MissionEventsMaker.missionOnMoney(this.mission_name);
                }

                possibleEndIterator.remove();
                missionFinished = true;
            }
        }

        if (missionFinished) {
            for (MissionPhase possibleEnd : this.waysToEnd) {
                for (InformationChannelData channelData : possibleEnd.getInfoChannels()) {
                    if (!(channelData.is_finish_delay())) {
                        ;
                    }

                    MissionEventsMaker.channelCleanResources(channelData.getUid());
                    PointsController.getInstance().freePoints(channelData.getPlacesNames(),
                            MissionSystemInitializer.getMissionsManager().getMissionPlacement(this.mission_name));
                }
            }

            MissionPlacement mp = MissionSystemInitializer.getMissionsManager().getMissionPlacement(this.mission_name);
            String s = mp.getInfo().getQuestItemPlacement();

            if (null != s) {
                ArrayList pNames = new ArrayList();

                pNames.add(s);
                PointsController.getInstance().freePoints(pNames, mp);
            }

            return true;
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getMission_name() {
        return this.mission_name;
    }
}


//~ Formatted in DD Std on 13/08/28
