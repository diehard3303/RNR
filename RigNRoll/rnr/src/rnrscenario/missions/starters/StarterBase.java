/*
 * @(#)StarterBase.java   13/08/28
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


package rnr.src.rnrscenario.missions.starters;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrscenario.controllers.starters.Start0430;
import rnr.src.rnrscenario.controllers.starters.Start0430.Checker;
import rnr.src.rnrscenario.controllers.starters.Start0454;
import rnr.src.rnrscenario.controllers.starters.Start0600;
import rnr.src.rnrscenario.missions.ScenarioMission;
import rnr.src.rnrscenario.sctask;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public class StarterBase extends sctask implements IStarter {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final List<StarterBase> allStarters = new ArrayList();
    protected CoreTime time_mission_end = null;
    private String mission_name;
    private String organizerName;
    private String finishPoint;
    private boolean move_time;
    private boolean needFinishIcon;

    /**
     * Constructs ...
     *
     */
    public StarterBase() {
        super(0, true);
        start();
        allStarters.add(this);
    }

    /**
     * Method description
     *
     */
    public static void deinitScenarioMissionsStarters() {
        for (StarterBase starter : allStarters) {
            starter.finishImmediately();
        }

        allStarters.clear();
    }

    protected final String getFinishPoint() {
        return this.finishPoint;
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param organizerName
     * @param finishPoint
     * @param timeForMissionEnd
     * @param move_time
     * @param needFinishIcon
     */
    @Override
    public void start(String mission_name, String organizerName, String finishPoint, double timeForMissionEnd,
                      boolean move_time, boolean needFinishIcon) {
        this.mission_name = mission_name;
        this.organizerName = organizerName;
        this.finishPoint = finishPoint;
        this.move_time = move_time;
        this.needFinishIcon = needFinishIcon;
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        ScenarioMission.activateMission(this.mission_name, this.organizerName, this.finishPoint, this.time_mission_end,
                                        this.move_time, this.needFinishIcon);

        ConditionChecker conditionChecker = getConditionChecker(this.mission_name);

        if (conditionChecker != null) {
            MissionEventsMaker.makeMissionFinishChecker(conditionChecker);
        }

        finish();
    }

    /**
     * Method description
     *
     */
    @Override
    public void finish() {
        super.finish();
        allStarters.remove(this);
    }

    /**
     * Method description
     *
     *
     * @param missionName
     *
     * @return
     */
    public ConditionChecker getConditionChecker(String missionName) {
        return null;
    }

    /**
     * Method description
     *
     *
     * @param missionName
     */
    public static void init0454Condition(String missionName) {
        MissionEventsMaker.makeMissionFinishChecker(new Start0454.Checker(missionName));
    }

    /**
     * Method description
     *
     *
     * @param missionName
     */
    public static void init0430Condition(String missionName) {
        MissionEventsMaker.makeMissionFinishChecker(new Start0430.Checker(missionName));
    }

    /**
     * Method description
     *
     *
     * @param missionName
     */
    public static void init0600Condition(String missionName) {
        MissionEventsMaker.makeMissionFinishChecker(new Start0600.Checker(missionName));
    }
}


//~ Formatted in DD Std on 13/08/28
