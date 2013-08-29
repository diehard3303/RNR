/*
 * @(#)LiveDialogChannel.java   13/08/28
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

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;
import rnr.src.rnrscr.Dialog;
import rnr.src.rnrscr.IMissionInformation;
import rnr.src.rnrscr.IPointActivated;
import rnr.src.rnrscr.MissionDialogs;

//~--- JDK imports ------------------------------------------------------------

import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class LiveDialogChannel extends InformationChannel {
    static final long serialVersionUID = 0L;
    static final float DIST_FOR_LIVEDIALOG = 10.0F;
    private String resource_hold;

    /**
     * Constructs ...
     *
     */
    public LiveDialogChannel() {
        this.resource_hold = null;
    }

    /**
     * Method description
     *
     *
     * @param info
     * @param resource
     */
    @Override
    public void postStartMissionInfo(MissionInfo info, String resource) {
        this.resource_hold = resource;

        IMissionInformation dialog = MissionDialogs.getMissionInfo(resource);
        IPointActivated listener = new LiveDialogOnPointActivate(dialog, this.identitie);

        MissionDialogs.addDialogLiveDialog(resource, this.identitie, listener);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public LiveDialogChannel clone() {
        return new LiveDialogChannel();
    }

    /**
     * Method description
     *
     */
    @Override
    public final void dispose() {
        MissionEventsMaker.channelCleanResources(getUid());
        clearResources();

        if (null != this.cachedInfo) {
            MissionEventsMaker.channelSayEndEventToNative(this.cachedInfo.getMissionName(), getUid());
        }
    }

    @Override
    void clearResources() {
        if (null != this.resource_hold) {
            MissionDialogs.RemoveDialog(this.resource_hold);
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void delayedRealInfoPost() {
        String resource = this.delayedInfo.resource;
        boolean useMainInfo = this.delayedInfo.useMainInfo;

        this.resource_hold = resource;

        IMissionInformation dialog = MissionDialogs.getMissionInfo(resource);
        IPointActivated listener = new LiveDialogOnPointActivate(dialog, this.identitie);

        if ((useMainInfo) && (this.isMain)) {
            listener.pointActivated();
        } else {
            MissionDialogs.addDialogLiveDialog(resource, this.identitie, listener);
        }
    }

    static class LiveDialogOnPointActivate implements IPointActivated {
        private final IMissionInformation info;
        private final String identitie;

        LiveDialogOnPointActivate(IMissionInformation info, String identitie) {
            this.info = info;
            this.identitie = identitie;
        }

        /**
         * Method description
         *
         */
        @Override
        public void pointActivated() {
            aiplayer npc = MissionEventsMaker.queueNPCPlayer_FreeFromVoter(this.info, this.identitie);

            this.info.freeVoter();

            Dialog dlg = Dialog.getDialog(this.info.getDialogName());
            Place place = MissionSystemInitializer.getMissionsMap().getPlace(this.info.getPointName());

            if (place == null) {
                MissionsLogger.getInstance().doLog("LiveDialogChannel.pointActivated" + this.info.getPointName(),
                                                   Level.INFO);
            } else {
                vectorJ carp = Crew.getIgrokCar().gPosition();
                vectorJ npcp = place.getCoords();
                double dist = Math.sqrt((carp.x - npcp.x) * (carp.x - npcp.x) + (carp.y - npcp.y) * (carp.y - npcp.y));

                if (dist < 10.0D) {
                    dlg.start_car_leave_car_on_end(npc, Crew.getIgrok().getModel(), this.info.getIdentitie());
                } else {
                    dlg.start_simplePoint(Crew.getIgrok().getModel(), this.info.getIdentitie(), new matrixJ(),
                                          place.getCoords());
                }
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/28
