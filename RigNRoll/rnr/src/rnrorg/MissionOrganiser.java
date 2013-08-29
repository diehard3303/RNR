/*
 * @(#)MissionOrganiser.java   13/08/26
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


package rnr.src.rnrorg;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrloggers.MissionsLogger;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class MissionOrganiser {
    private static MissionOrganiser instance;

    static {
        instance = null;
    }

    @SuppressWarnings("unchecked")
    private HashMap<String, String> missions = new HashMap();

    /**
     * Method description
     *
     */
    public static void deinit() {
        instance = null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static MissionOrganiser getInstance() {
        if (null == instance) {
            instance = new MissionOrganiser();
        }

        return instance;
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param org_name
     */
    public void addMission(String mission_name, String org_name) {
        if (this.missions.containsKey(mission_name)) {
            MissionsLogger.getInstance().doLog("MissionOrganiser already contains mission named " + mission_name,
                                               Level.SEVERE);

            return;
        }

        if (this.missions.containsValue(org_name)) {
            MissionsLogger.getInstance().doLog("MissionOrganiser already organiser named " + org_name
                                               + ". Try was fro mission named " + mission_name, Level.SEVERE);

            return;
        }

        this.missions.put(mission_name, org_name);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     *
     * @return
     */
    public String getOrgForMission(String mission_name) {
        if (!(this.missions.containsKey(mission_name))) {
            return null;
        }

        return (this.missions.get(mission_name));
    }

    /**
     * Method description
     *
     *
     * @param org_name
     *
     * @return
     */
    public String getMissionForOrganiser(String org_name) {
        assert(org_name != null);

        Set keys = this.missions.keySet();

        for (String key : keys) {
            if (this.missions.get(key).compareToIgnoreCase(org_name) == 0) {
                return key;
            }
        }

        MissionsLogger.getInstance().doLog("Trying to fins org " + org_name + "in missions. Cannot find!",
                                           Level.SEVERE);

        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public HashMap<String, String> getMissions() {
        return this.missions;
    }

    /**
     * Method description
     *
     *
     * @param missions
     */
    public void setMissions(HashMap<String, String> missions) {
        this.missions = missions;
    }
}


//~ Formatted in DD Std on 13/08/26
