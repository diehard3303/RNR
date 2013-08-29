/*
 * @(#)CbvChannel.java   13/08/28
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
import rnr.src.rnrscenario.CBCallsStorage;
import rnr.src.rnrscenario.CBVideoStroredCall;
import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscenario.missions.map.AbstractMissionsMap;
import rnr.src.rnrscr.IMissionInformation;
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
public class CbvChannel extends DelayedChannel {
    static final long serialVersionUID = 0L;
    private String resource_hold = null;
    private boolean posted = false;

    /**
     * Constructs ...
     *
     *
     * @param map
     */
    public CbvChannel(AbstractMissionsMap map) {
        super(map);
    }

    /**
     * Method description
     *
     *
     * @param info
     * @param resource
     */
    public void postStartMissionInfo_Detailed(MissionInfo info, String resource) {
        this.resource_hold = resource;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public CbvChannel clone() {
        return new CbvChannel(getMap());
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

        this.resource_hold = resource;

        if (this.posted) {
            return;
        }

        this.posted = true;

        IMissionInformation dialog = MissionDialogs.queueDialog(getUid());
        String dialog_name = dialog.getDialogName();
        CBVideoStroredCall call = CBCallsStorage.getInstance().getStoredCall(dialog_name);

        if (null == call) {
            MissionsLogger.getInstance().doLog("CbvChannel for mission " + mission_name + " can find dialog named "
                                               + dialog_name, Level.SEVERE);
        }

        call.makecall(this.identitie, mission_name);
    }
}


//~ Formatted in DD Std on 13/08/28
