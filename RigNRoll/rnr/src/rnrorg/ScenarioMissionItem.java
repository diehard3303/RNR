/*
 * @(#)ScenarioMissionItem.java   13/08/26
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class ScenarioMissionItem {
    private final String mission_name;
    private final String org_name;
    private final String point_name;
    private final boolean move_time;
    private final boolean needFinishIcon;
    private final double time_on_mission;

    /**
     * Constructs ...
     *
     *
     * @param mission_name
     * @param org_name
     * @param point_name
     * @param time_on_mission
     * @param move_time
     * @param needFinishIcon
     */
    public ScenarioMissionItem(String mission_name, String org_name, String point_name, double time_on_mission,
                               boolean move_time, boolean needFinishIcon) {
        this.mission_name = mission_name;
        this.org_name = org_name;
        this.point_name = point_name;
        this.time_on_mission = time_on_mission;
        this.move_time = move_time;
        this.needFinishIcon = needFinishIcon;
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

    /**
     * Method description
     *
     *
     * @return
     */
    public String getOrg_name() {
        return this.org_name;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getPoint_name() {
        return this.point_name;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getTimeOnMission() {
        return this.time_on_mission;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean getMoveTime() {
        return this.move_time;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean getNeedFinishIcon() {
        return this.needFinishIcon;
    }
}


//~ Formatted in DD Std on 13/08/26
