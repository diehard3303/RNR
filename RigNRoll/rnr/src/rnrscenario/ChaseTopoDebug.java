/*
 * @(#)ChaseTopoDebug.java   13/08/28
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

import rnr.src.players.Chase;
import rnr.src.players.Crew;
import rnr.src.players.Trajectory;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.players.semitrailer;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.controllers.chaseTopo;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscr.MissionDialogs;
import rnr.src.scenarioMachine.FiniteStateMachine;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class ChaseTopoDebug {

    /**
     * Method description
     *
     */
    public static void allTopo() {
        scenarioscript.script.getScenarioMachine().activateState(new String[] { "SC meet Topo",
                "SC meet Topo_phase_1" });
    }

    /**
     * Method description
     *
     */
    public static void simplechase() {
        chaseTopo.PareGoodPsition GP = new chaseTopo.PareGoodPsition();

        GP.V = eng.getControlPointPosition("CP_exit5_1");

        actorveh car = eng.CreateCar_onway("FREIGHTLINER_ARGOSY", GP.V);

        car.sVeclocity(25.0D);
        Crew.addMappedCar("ARGOSY BANDIT", car);

        aiplayer pl = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");

        pl.beDriverOfCar(car);

        Chase ch_ase = new Chase();

        ch_ase.paramModerateChasing();
        ch_ase.makechase(car, Crew.getIgrokCar());
    }

    /**
     * Method description
     *
     */
    public static void simpliestchase() {
        chaseTopo.PareGoodPsition GP = new chaseTopo.PareGoodPsition();
        vectorJ pos = Crew.getIgrokCar().gDir();

        pos.mult(-100.0D);
        GP.M = Crew.getIgrokCar().gMatrix();
        GP.V = pos.oPlusN(Crew.getIgrokCar().gPosition());

        actorveh car = eng.CreateCar_onway("FREIGHTLINER_ARGOSY", GP.V);

        car.sVeclocity(25.0D);
        Crew.addMappedCar("ARGOSY BANDIT", car);

        aiplayer pl = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");

        pl.beDriverOfCar(car);

        Chase ch_ase = new Chase();

        ch_ase.paramModerateChasing();
        ch_ase.makechase(car, Crew.getIgrokCar());
    }

    /**
     * Method description
     *
     */
    public static void testContestStalkers() {
        chaseTopo chase = scenarioscript.script.constructChaseTopo();

        chase.createRaceBanditsToBridge();
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings("deprecation")
    public static void createFriends() {
        matrixJ m = new matrixJ();
        vectorJ p = eng.getControlPointPosition("CP_LB_TS");
        vectorJ shift = new vectorJ(m.v0);

        shift.mult(5.0D);

        actorveh kohcar = eng.CreateCarImmediatly("PETERBILT_378", m, p);
        actorveh dorcar = eng.CreateCarImmediatly("FREIGHTLINER_ARGOSY", m, p.oPlusN(shift));

        Crew.addMappedCar("DOROTHY", dorcar);
        Crew.addMappedCar("KOH", kohcar);
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void justFriends() {
        actorveh car_dor = Crew.getMappedCar("DOROTHY");
        actorveh car_koh = Crew.getMappedCar("KOH");

        aiplayer.getScenarioAiplayer("SC_DOROTHY").abondoneCar(car_dor);
        aiplayer.getScenarioAiplayer("SC_KOH").abondoneCar(car_koh);
        Trajectory.createTrajectory("dorothytrajectory", "CP_ContBase_Start1", "CP_ContBase_Finish1");
        Trajectory.createTrajectory("kohtrajectory", "CP_ContBase_Start2", "CP_ContBase_Finish2");

        Vector players = new Vector();

        players.add(car_dor);
        actorveh.aligncars_inTrajectoryStart(players, "dorothytrajectory", 0.0D, 0.0D, 2, 1);
        players = new Vector();
        players.add(car_koh);
        actorveh.aligncars_inTrajectoryStart(players, "kohtrajectory", 0.0D, 0.0D, 2, 1);

        vectorJ shift1 = car_dor.gDir();

        shift1.mult(-10.0D);

        vectorJ shift2 = car_koh.gDir();

        shift2.mult(-10.0D);

        semitrailer trailerDor = semitrailer.create("model_Flat_bed_cargo3", car_dor.gMatrix(),
                                     car_dor.gPosition().oPlusN(shift1));
        semitrailer trailerKoh = semitrailer.create("model_Flat_bed_cargo3", car_koh.gMatrix(),
                                     car_koh.gPosition().oPlusN(shift2));

        car_dor.attach(trailerDor);
        car_koh.attach(trailerKoh);
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void friendsRide() {
        actorveh car1 = Crew.getMappedCar("DOROTHY");
        actorveh car2 = Crew.getMappedCar("KOH");

        car1.sVeclocity(30.0D);
        car2.sVeclocity(30.0D);

        Vector players = new Vector();

        players.add(car1);
        actorveh.autopilotOnTrajectory(players, "dorothytrajectory");
        players = new Vector();
        players.add(car2);
        actorveh.autopilotOnTrajectory(players, "kohtrajectory");
        eng.setdword("DWORD_TopoQuest_Bridge", 1);
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void friendsFinish() {
        actorveh car1 = Crew.getMappedCar("DOROTHY");
        actorveh car2 = Crew.getMappedCar("KOH");

        eng.setdword("DWORD_TopoQuest_Bridge", 1);
        eng.setdword("DWORD_TopoQuest_BridgeCol", 0);
        Trajectory.createTrajectory("nearbridge1", "CP_UnderBridge_Start1", "CP_UnderBridge_Finish1");
        Trajectory.createTrajectory("nearbridge2", "CP_UnderBridge_Start2", "CP_UnderBridge_Finish2");
        car1.stop_autopilot();
        car2.stop_autopilot();

        Vector players = new Vector();

        players.add(car1);
        actorveh.aligncars_inTrajectoryStart(players, "nearbridge1", 0.0D, 0.0D, 2, 1);
        players = new Vector();
        players.add(car2);
        actorveh.aligncars_inTrajectoryStart(players, "nearbridge2", 0.0D, 0.0D, 2, 1);
    }

    /**
     * Method description
     *
     */
    public static void darkTruck() {
        new test().createtrailer();
        MissionSystemInitializer.getMissionsManager().activateAsideMission("sc00610");
        MissionDialogs.sayAppear("sc00610_start_channel");
        eng.console("pickup sc00610");
        scenarioscript.script.getScenarioMachine().activateState(new String[] { "chasetopo", "chasetopo_phase_1" });
        scenarioscript.script.getScenarioMachine().activateState(new String[] { "chasetopo", "chasetopo_phase_15" });
        ScenarioSave.getInstance().CHASETOPO = scenarioscript.script.constructChaseTopo();
        ScenarioSave.getInstance().CHASETOPO.exitAnimation_punktB();
    }
}


//~ Formatted in DD Std on 13/08/28
