/*
 * @(#)scenariovalidate.java   13/08/28
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


package rnr.src.rnrscenario;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.Helper;
import rnr.src.rnrcore.eng;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class scenariovalidate {
    private static final String SAVE_DOROTHY_START = "start Rescue Dorothy";
    private static final String SCTRANGER_CALL_START = "starngercall";
    private static final String MEET_PITER_PAN = "SC meet Piter Pan";
    private static final String WAIT_POLICE_CALL = "wait_policecall";
    private static final String MEET_TOPO = "SC meet Topo";
    private static final String POLICE_AFTER_TOPO = "SC police";
    private static final String BIGRACE_START = "SC BigRace";
    private static final String BIGRACE_FAKE = "bigracequest_debug";
    private static final String DAKOTAQUEST_START = "SC Dakota";
    private static final String MATQUEST_START = "SC Meet Mat";
    private static final String ENEMY_BASE_START = "SC Enemy Base";
    private static final String CHASE_KOH_START = "SC Chase Koh";
    private static final String CURSEDHIWAY_START = "SC Cursed Hiway";

    /**
     * Method description
     *
     */
    public static void saveDorothy() {
        startany("start Rescue Dorothy");
    }

    /**
     * Method description
     *
     */
    public static void strangerCall() {
        startany("starngercall");
    }

    /**
     * Method description
     *
     */
    public static void meetPiterPan() {
        startany("SC meet Piter Pan");
    }

    /**
     * Method description
     *
     */
    public static void meetPiterPanFinalRace() {
        scenarioscript.script.getScenarioMachine().activateState(new String[] { "SC meet Piter Pan_phase_4",
                "SC meet Piter Pan_phase_4" });
    }

    /**
     * Method description
     *
     */
    public static void waitPoliceCall() {
        startany("wait_policecall");
    }

    /**
     * Method description
     *
     */
    public static void meetToto() {
        startany("SC meet Topo");
    }

    /**
     * Method description
     *
     */
    public static void policeAfterTopo() {
        startany("SC police");
    }

    /**
     * Method description
     *
     */
    public static void bigRaceQuest() {
        startany("SC BigRace");
    }

    /**
     * Method description
     *
     */
    public static void bigRaceQuestFake() {
        eng.console("stats rating 100");
        startany("bigracequest_debug");
    }

    /**
     * Method description
     *
     */
    public static void meetDakota() {
        startany("SC Dakota");
    }

    /**
     * Method description
     *
     */
    public static void meetMat() {
        startany("SC Meet Mat");
    }

    /**
     * Method description
     *
     */
    public static void checkJohnTask() {
        MissionSystemInitializer.getMissionsManager().activateAsideMission("sc01080");
        startany("activate john help quest");
        eng.unblockNamedSO(1024, "SP_John_House");
    }

    /**
     * Method description
     *
     */
    public static void enemyBase() {
        scenarioscript.script.getScenarioMachine().activateState(new String[] { "SC Enemy Base",
                "SC Enemy Base_phase_1" });
        EventsControllerHelper.messageEventHappened("Dorothy_call");
    }

    /**
     * Method description
     *
     */
    public static void chaseKoh() {
        scenarioscript.script.getScenarioMachine().activateState(new String[] { "SC Chase Koh",
                "SC Chase Koh_phase_1" });
    }

    /**
     * Method description
     *
     */
    public static void cursedHiway() {
        scenarioscript.script.getScenarioMachine().activateState(new String[] { "SC Cursed Hiway",
                "SC Cursed Hiway_phase_1" });
    }

    /**
     * Method description
     *
     */
    public static void cursedHiwayWithoutorder() {
        scenarioscript.script.getScenarioMachine().activateState(new String[] { "SC Cursed Hiway",
                "SC Cursed Hiway_phase_1" });
        StaticScenarioStuff.makeReadyCursedHiWay(false);
        EventsControllerHelper.messageEventHappened("sc01300 accepted");
        Helper.peekNativeMessage("sc01300 loaded");
    }

    /**
     * Method description
     *
     */
    public static void policeEntrapped() {
        scenarioscript.script.getScenarioMachine().activateState(new String[] { "cursed_hiway",
                "cursed_hiway_phase_1" });
    }

    /**
     * Method description
     *
     *
     * @param scenariopart
     */
    public static void startany(String scenariopart) {
        scenarioscript.script.getScenarioMachine().activateState(new String[] { scenariopart,
                scenariopart + "_phase_" + 1 });
    }
}


//~ Formatted in DD Std on 13/08/28
