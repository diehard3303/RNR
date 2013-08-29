/*
 * @(#)CarDialogChannel.java   13/08/28
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

import rnr.src.menu.menues;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscr.IMissionInformation;
import rnr.src.rnrscr.IPointActivated;
import rnr.src.rnrscr.MissionDialogs;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class CarDialogChannel extends InformationChannel {
    static final long serialVersionUID = 0L;
    private String resource_hold;

    /**
     * Constructs ...
     *
     */
    public CarDialogChannel() {
        this.resource_hold = null;
    }

    /**
     * Method description
     *
     *
     * @param info
     * @param resource
     */
    public void postStartMissionInfo(MissionInfo info, String resource) {
        this.resource_hold = resource;

        IMissionInformation dialog = MissionDialogs.getMissionInfo(resource);
        IPointActivated listener = new CarDialogOnPointActivate(dialog, this.identitie);

        MissionDialogs.addDialogCarDialog(resource, this.identitie, listener);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public InformationChannel clone() {
        return new CarDialogChannel();
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
        String mission_name = this.delayedInfo.mission_name;
        String resource = this.delayedInfo.resource;
        boolean useMainInfo = this.delayedInfo.useMainInfo;

        this.resource_hold = resource;

        if (!(menues.cancreate_messagewindow())) {
            new WaitApproptiateMomentDialog(getUid(), mission_name, resource, this.identitie, useMainInfo);

            return;
        }

        IMissionInformation dialog = MissionDialogs.getMissionInfo(resource);
        IPointActivated listener = new CarDialogOnPointActivate(dialog, this.identitie);

        if ((useMainInfo) && (this.isMain)) {
            listener.pointActivated();
        } else {
            MissionDialogs.addDialogCarDialog(resource, this.identitie, listener);
        }
    }

    static class CarDialogOnPointActivate implements IPointActivated {
        private final IMissionInformation info;
        private final String identitie;

        CarDialogOnPointActivate(IMissionInformation info, String identitie) {
            this.info = info;
            this.identitie = identitie;
        }

        /**
         * Method description
         *
         */
        @Override
        public void pointActivated() {
            MissionDialogs.startDialogCarDialog(this.info.getDialogName(), this.identitie);
        }
    }


    class WaitApproptiateMomentDialog extends TypicalAnm {

        /** Field description */
        public String uid;

        /** Field description */
        public String mission_name;
        private final String resource;
        private final String id;
        private final boolean useMainInfo;

        WaitApproptiateMomentDialog(String paramString1, String paramString2, String paramString3, String paramString4,
                                    boolean paramBoolean) {
            this.uid = paramString1;
            this.mission_name = paramString2;
            this.resource = paramString3;
            this.id = paramString4;
            this.useMainInfo = paramBoolean;
            eng.CreateInfinitScriptAnimation(this);
        }

        /**
         * Method description
         *
         *
         * @param dt
         *
         * @return
         */
        @Override
        public boolean animaterun(double dt) {
            if (!(menues.cancreate_messagewindow())) {
                return false;
            }

            IMissionInformation dialog = MissionDialogs.getMissionInfo(this.resource);
            IPointActivated listener = new CarDialogChannel.CarDialogOnPointActivate(dialog,
                                           CarDialogChannel.this.identitie);

            if ((this.useMainInfo) && (CarDialogChannel.this.isMain)) {
                listener.pointActivated();
            } else {
                MissionDialogs.startDialogCarDialog(this.resource, CarDialogChannel.this.identitie);
            }

            return true;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
