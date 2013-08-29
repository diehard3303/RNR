/*
 * @(#)MissionFactory.java   13/08/28
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

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class MissionFactory implements MissionsController {
    static final long serialVersionUID = 0L;
    private final Map<String, MissionInfo> wares;

    /**
     * Constructs ...
     *
     */
    public MissionFactory() {
        this.wares = new HashMap<String, MissionInfo>();
    }

    SingleMission create(String name) {
        assert(null != name) : "name must be valid non-null pointer";

        MissionInfo infoToContructFrom = this.wares.remove(name);

        if (null != infoToContructFrom) {
            infoToContructFrom.executeStartActions();
            MissionsLogger.getInstance().doLog("mission with name '" + name + "' has been created", Level.FINE);

            return infoToContructFrom.constructMission();
        }

        MissionsLogger.getInstance().doLog("mission with name '" + name + "' wasn't loaded into factory",
                                           Level.WARNING);

        return null;
    }

    /**
     * Method description
     *
     *
     * @param constructFrom
     */
    @Override
    public void uploadMission(MissionInfo constructFrom) {
        assert(null != constructFrom) : "constructFrom must be valid non-null pointer";

        if (this.wares.keySet().contains(constructFrom.getName())) {
            MissionsLogger.getInstance().doLog("mission with name '" + constructFrom.getName()
                                               + "' already loaded in factory", Level.FINE);
        } else {
            this.wares.put(constructFrom.getName(), constructFrom);
            MissionsLogger.getInstance().doLog("mission with name '" + constructFrom.getName()
                                               + "' has been uploaded to factory", Level.FINE);
        }
    }

    /**
     * Method description
     *
     *
     * @param missionName
     */
    @Override
    public void unloadMission(String missionName) {
        assert(null != missionName) : "missionName must be valid non-null pointer";

        if (null == this.wares.remove(missionName)) {
            MissionsLogger.getInstance().doLog("mission with name '" + missionName + "' wasn't loaded into factory",
                                               Level.FINE);
        } else {
            MissionsLogger.getInstance().doLog("mission with name '" + missionName
                                               + "' has been unloaded from factory", Level.FINE);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
