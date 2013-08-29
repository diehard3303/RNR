/*
 * @(#)EnemyBase.java   13/08/28
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


package rnr.src.rnrscenario.controllers;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.CarName;
import rnr.src.players.Chase;
import rnr.src.players.Crew;
import rnr.src.players.ScenarioPersonPassanger;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.players.vehicle;
import rnr.src.rnrcore.ConvertGameTime;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.EnemyBaseObjectXmlSerializable;
import rnr.src.rnrcore.ObjectXmlSerializable;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.traffic;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.configurators.EnemyBaseConfig;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscenario.scenes.sc02040;
import rnr.src.rnrscr.AdvancedRandom;
import rnr.src.rnrscr.CBVideoInterruptCalls;
import rnr.src.rnrscr.IInterruptCall;
import rnr.src.rnrscr.parkingplace;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptEvents.EventsControllerHelper;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
@ScenarioClass(
    scenarioStage = 14,
    fieldWithDesiredStage = ""
)
public final class EnemyBase extends TypicalAnm implements ScenarioController {
    private static final boolean DEBUG = false;
    private static final double TRACK_MIN_DISTANCE_2 = 1600.0D;
    private static final double TRACK_MAX_DISTANCE_2 = 160000.0D;
    private static final double DAKOTA_WARNING_TIME = 60.0D;
    private static final double TIME_MONICA_FINDS_OUT = 900.0D;
    private static final double TIME_DAKOTA_CALL = 600.0D;
    private static final double TIME_2020_CALL = 1800.0D;

    /** Field description */
    public static final int MESSAGE_MET_MONICA = 0;

    /** Field description */
    public static final int MESSAGE_START = 1;

    /** Field description */
    public static final int MESSAGE_MONICA_CALL1 = 2;

    /** Field description */
    public static final int MESSAGE_MONICA_CALL1_FINISHED = 3;

    /** Field description */
    public static final int MESSAGE_MET_MONICA_FAILED = 4;

    /** Field description */
    public static final int MESSAGE_MONICA_DISCOVERED_ENEMYBASE = 5;

    /** Field description */
    public static final int MESSAGE_MONICA_CALL2_FINISHED = 6;

    /** Field description */
    public static final int MESSAGE_THREATS_CALL = 7;

    /** Field description */
    public static final int MESSAGE_THREATS_CALL_FINISHED = 8;

    /** Field description */
    public static final int MESSAGE_TRACK_FAILED = 9;

    /** Field description */
    public static final int MESSAGE_TRACK_SUCCEED = 10;

    /** Field description */
    public static final int MESSAGE_DAKOTA_CALL = 11;

    /** Field description */
    public static final int MESSAGE_DAKOTA_CALL_FINISHED = 12;

    /** Field description */
    public static final int MESSAGE_MET_ASSAULT_TEAM = 13;

    /** Field description */
    public static final int MESSAGE_REACH_ENEMYBASE_FAILURE = 14;

    /** Field description */
    public static final int MESSAGE_REACH_ENEMYBASE_SUCCEED = 15;

    /** Field description */
    public static final int MESSAGE_ASSAULT_SUCCEED = 16;

    /** Field description */
    public static final int MESSAGE_ASSAULT_COVARD = 17;

    /** Field description */
    public static final int MESSAGE_ASSAULT_FAILED = 18;

    /** Field description */
    public static final int MESSAGE_THREATS_CALL1_FINISHED = 19;

    /** Field description */
    public static final int MESSAGE_THREATS_CALL2_FINISHED = 20;

    /** Field description */
    public static final int MESSAGE_OVER_ENEMYBASE_MOAT = 21;

    /** Field description */
    public static final int MESSAGE_DAKOTA_WARNING = 22;

    /** Field description */
    public static final int MESSAGE_FINISH = 23;

    /** Field description */
    public static final int MESSAGE_1600_STARTED = 24;

    /** Field description */
    public static final int MESSAGE_2025_FAILED = 26;

    /** Field description */
    public static final int MESSAGE_2020_CALL = 27;

    /** Field description */
    public static final String METHOD_THREATCALL = "threat_call";

    /** Field description */
    public static final String METHOD_DACOTACALL = "dakota_call";

    /** Field description */
    public static final String METHOD_MONICA_DISCOVERED_BASE = "monica_discovered_base";

    /** Field description */
    public static final String METHOD_MONICA_CALL2_FINISHED = "monica_discovered_base_finish_call";

    /** Field description */
    public static final String METHOD_2025_FAILED = "failed_2025";

    /** Field description */
    public static final String METHOD_1600_STARTED = "started_1600";
    private static final int EVENTID_ENTER_MOAT = 0;

    /** Field description */
    public static final String DWORD_ENEMYBASE = "DWORD_EnemyBase";

    /** Field description */
    public static final String DWORD_MONICA = "DWORD_EnemyBaseMonica";

    /** Field description */
    public static final String DWORD_ASSAULTTEAM = "DWORD_EnemyBaseAssaultTeam";

    /** Field description */
    public static final String DWORD_ENEMYBASE_TUNNEL = "DWORD_EnemyBaseTunnel";

    /** Field description */
    public static final String DWORD_ENEMYBASE_ASSAULT = "DWORD_EnemyBaseAssault";

    /** Field description */
    public static final String DWORD_WIREROPE = "DWORD_EnemyBaseWireRope";
    private static final String POINT_TRUCKSTOP = "EnemyBaseTruckstop";
    private static final String POINT_TUNNEL = "EnemyBaseTunnel";

    /** Field description */
    public static final String POINT_ASSAULT = "EnemyBaseAssaultRoot";

    /** Field description */
    public static final String POINT_LINEUP = "LineUp2030";

    /** Field description */
    public static final String PROC_MOTEL = "KeyPoint_1620";

    /** Field description */
    public static final String PROC_TUNNEL = "KeyPoint_1640";

    /** Field description */
    public static final String PROC_SNIPER = "KeyPoint_2045";

    /** Field description */
    public static final String PROC_WIN = "KeyPoint_2040";

    /** Field description */
    public static final int BANDIT_CAR_TUNNEL = 0;

    /** Field description */
    public static final int BANDIT_CAR2 = 1;

    /** Field description */
    public static final int MONICA_CAR = 2;

    /** Field description */
    public static final int GEPARD_CAR_ASSAULT = 0;

    /** Field description */
    public static final int JOHN_CAR_ASSAULT = 1;

    /** Field description */
    public static final int DAKOTA_CAR_ASSAULT = 2;

    /** Field description */
    public static final String MISSION_MET_MONICA = "m01600";

    /** Field description */
    public static final String MISSION_MET_ASSAULTTEAM = "m02025";
    private static final double MAX_TIME_TO_SLOW_DOWN = 15.0D;

    /** Field description */
    public static final String[] MESSAGES;
    private static final int[] EVENTS;
    private static final String[] EVENT_METHODS;
    private static final Set<String> allDwords;

    /** Field description */
    public static final CarName[] CAR_NAMES;
    private static final int[] PARKINGPLACES;

    /** Field description */
    public static final CarName[] CAR_NAMES_ASSAULT;
    private static final int[] PARKINGPLACES_ASSAULT;
    private static int moatTriggerId;
    private static EnemyBase gENEMYBASE;

    static {
        MESSAGES = new String[] {
            "reach EnemyBaseTruckstop", "start_1600", "Dorothy_call", "cb01600 finished", "m01600 failed",
            "Monica discovered EnemyBase", "cb02010 finished", "threaten_call_1", "cb01660 finished",
            "track to EnemyBase failed", "track to EnemyBase succeded", "make_Dacota_call", "cb02015 finished",
            "met assault team", "Reach EnemyBase failure", "Reach EnemyBase succes", "EnemyBaseAssault succes",
            "EnemyBaseAssault covard", "EnemyBaseAssault failed", "cb02020 finished", "cb02021 finished",
            "enemybase moat", "Dakota warns about bomb", "EnemyBaseFinish", "m01600 activated", "m02025 activated",
            "m02025 failed", "make 2020 call"
        };
        EVENTS = new int[] {
            22046, 22047, 21620, 22025, 21640, 22030
        };
        EVENT_METHODS = new String[] {
            "enter_moat", "enter_nearbase", "enter_behindmotel", "enter_assaultteam", "enter_tracktunnel",
            "enter_assault"
        };
        allDwords = new TreeSet();
        allDwords.add("DWORD_EnemyBase");
        allDwords.add("DWORD_EnemyBase");
        allDwords.add("DWORD_EnemyBaseMonica");
        allDwords.add("DWORD_EnemyBaseAssaultTeam");
        allDwords.add("DWORD_EnemyBaseTunnel");
        allDwords.add("DWORD_EnemyBaseAssault");
        allDwords.add("DWORD_EnemyBaseWireRope");
        CAR_NAMES = new CarName[] { CarName.CAR_BANDITS, CarName.CAR_BANDITS, CarName.CAR_MONICA };
        PARKINGPLACES = new int[] { 5, 6, 7 };
        CAR_NAMES_ASSAULT = new CarName[] { CarName.CAR_GEPARD, CarName.CAR_JOHN, CarName.CAR_DAKOTA };
        PARKINGPLACES_ASSAULT = new int[] { 4, 5, 7 };
        moatTriggerId = 0;
        gENEMYBASE = null;
    }

    private final int[] event_ids = new int[EVENTS.length];
    private long assault_cycling_scene = 0L;
    private boolean firstTimeMeasure = true;
    private double lastTime = 0.0D;
    private boolean moatEventTriggerRegistered = false;
    private boolean stopPlay = false;
    private volatile boolean interruptPlay = false;
    private boolean isAssaultCycleSceneCreated = false;
    private boolean wasVehicleExchange = false;
    private boolean bad_conditions = false;
    private actorveh[] cars = new actorveh[CAR_NAMES.length];

    /** Field description */
    public actorveh[] cars_assault = new actorveh[CAR_NAMES_ASSAULT.length];
    private aiplayer monica = null;
    private aiplayer dakota = null;
    private final Set<String> currentActiveDwords = new TreeSet();
    private boolean assault_started = false;
    private double assault_start_time = 0.0D;
    private double dakota_warning_start_time = 0.0D;
    private boolean want_dakota_warning = false;
    private boolean want_dakota_warning_started = false;
    private boolean to_slow_down_gepard = false;
    private boolean slowdown_start = true;
    private double initialVelocity = 0.0D;
    private double slowDownAcceleration = -20.0D;
    private Timing_MonicaFindsOut to_make_discover_base = null;
    private Timing_DakotaCall to_make_dakota_call = null;
    private Timing_ThreatenCall to_make_threat_call = null;
    private Timing_BadCall to_make_2020_call = null;
    private boolean to_track_tunnel = false;
    private boolean was_near_base = false;
    private double slowDowningTime = 0.0D;
    private boolean assaultFailed = false;
    private ObjectXmlSerializable serializator;
    private final ScenarioHost host;
    private final EnemyBaseConfig config;

    /**
     * Constructs ...
     *
     *
     * @param config
     * @param host
     */
    public EnemyBase(EnemyBaseConfig config, ScenarioHost host) {
        assert((null != config) && (null != host));
        this.config = config;
        this.host = host;
        this.host.registerController(this);
        this.bad_conditions = (EVENTS.length != EVENT_METHODS.length);

        if (this.bad_conditions) {
            eng.log(super.getClass().getName() + " has bad conditions\n");

            return;
        }

        if (eng.useNative()) {
            for (int i = 0; i < EVENTS.length; ++i) {
                this.event_ids[i] = event.eventObject(EVENTS[i], this, EVENT_METHODS[i]);
            }

            eng.CreateInfinitScriptAnimation(this);
            EventsControllerHelper.getInstance().addMessageListener(this, "dakota_call", MESSAGES[12]);
            EventsControllerHelper.getInstance().addMessageListener(this, "threat_call", MESSAGES[8]);
            EventsControllerHelper.getInstance().addMessageListener(this, "monica_discovered_base", MESSAGES[4]);
            EventsControllerHelper.getInstance().addMessageListener(this, "monica_discovered_base", MESSAGES[9]);
            EventsControllerHelper.getInstance().addMessageListener(this, "monica_discovered_base_finish_call",
                    MESSAGES[6]);
            EventsControllerHelper.getInstance().addMessageListener(this, "started_1600", MESSAGES[24]);
            EventsControllerHelper.getInstance().addMessageListener(this, "failed_2025", MESSAGES[26]);
            EventsControllerHelper.getInstance().addMessageListener(this, "failedToReachEnemyBase", "m02030 failed");
            monica_call();
            this.serializator = new EnemyBaseObjectXmlSerializable(this);
            this.serializator.registerObjectXmlSerializable();
        }

        gENEMYBASE = this;
        setDword("DWORD_EnemyBase");
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static EnemyBase getInstance() {
        return gENEMYBASE;
    }

    /**
     * Method description
     *
     */
    @Override
    public void gameDeinitLaunched() {
        deinit();
    }

    /**
     * Method description
     *
     */
    public void failedToReachEnemyBase() {
        deinit();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static vectorJ getPOINT_TRUCKSTOP() {
        vectorJ pos = eng.getControlPointPosition("EnemyBaseTruckstop");

        if (pos.length() < 1.0D) {
            pos.x = 6977.0D;
            pos.y = 14065.0D;
            pos.z = 190.0D;
        }

        return pos;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static vectorJ getPOINT_LINEUP() {
        vectorJ pos = eng.getControlPointPosition("LineUp2030");

        if (pos.length() < 1.0D) {
            pos.x = 3837.0D;
            pos.y = 13149.0D;
            pos.z = 318.0D;
        }

        return pos;
    }

    private static void registerMoatEvent() {
        moatTriggerId = event.eventObject(EVENTS[0], scenarioscript.script, EVENT_METHODS[0]);
    }

    private static void unregisterMoatEvent() {
        event.removeEventObject(moatTriggerId);
    }

    private void setDword(String dword) {
        if (null == dword) {
            return;
        }

        this.currentActiveDwords.add(dword);

        if (!(eng.useNative())) {
            return;
        }

        eng.setdword(dword, 1);
    }

    private void offDword(String dword) {
        if (null == dword) {
            return;
        }

        this.currentActiveDwords.remove(dword);
        eng.setdword(dword, 0);
    }

    /**
     * Method description
     *
     */
    public void enableTruckstopTrigger() {
        setDword("DWORD_EnemyBaseAssaultTeam");
        EventsControllerHelper.getInstance().removeMessageListener(this, "enableTruckstopTrigger", "cb02015 finished");
    }

    /**
     * Method description
     *
     *
     * @param timeFromNative
     *
     * @return
     */
    @Override
    public boolean animaterun(double timeFromNative) {
        if (!(this.moatEventTriggerRegistered)) {
            if (!(eng.noNative)) {
                registerMoatEvent();
            }

            this.moatEventTriggerRegistered = true;
        }

        double deltaT = timeFromNative - this.lastTime;

        this.lastTime = timeFromNative;

        if (this.to_slow_down_gepard) {
            slowdownGaperd(deltaT);
        }

        if (this.interruptPlay) {
            return true;
        }

        if (this.firstTimeMeasure) {
            for (actorveh carForPredefinedScene : this.cars_assault) {
                if (null == carForPredefinedScene) {
                    continue;
                }

                carForPredefinedScene.setNeverUnloadFlag();
            }

            this.lastTime = timeFromNative;
            this.firstTimeMeasure = false;
        }

        if ((null != this.to_make_dakota_call) && (this.to_make_dakota_call.run())) {
            createCars_meet_assaultteam();
            EventsControllerHelper.getInstance().addMessageListener(this, "enableTruckstopTrigger", "cb02015 finished");
            this.to_make_dakota_call = null;
        }

        if ((null != this.to_make_threat_call) && (this.to_make_threat_call.run())) {
            this.to_make_threat_call = null;
        }

        if ((null != this.to_make_2020_call) && (this.to_make_2020_call.run())) {
            this.to_make_2020_call = null;
        }

        if ((null != this.to_make_discover_base) && (this.to_make_discover_base.run())) {
            this.to_make_discover_base = null;
        }

        if (this.to_track_tunnel) {
            track_tunnel();
        }

        if (this.assault_started) {
            this.assault_start_time += deltaT;

            if (this.assault_start_time > this.config.getTiming(0)) {
                assault_failed();
            }
        }

        if (this.want_dakota_warning) {
            if (!(this.want_dakota_warning_started)) {
                this.want_dakota_warning_started = true;
                this.dakota_warning_start_time = 0.0D;
            }

            this.dakota_warning_start_time += deltaT;

            if (this.dakota_warning_start_time > 60.0D) {
                this.want_dakota_warning = false;
                EventsControllerHelper.messageEventHappened(MESSAGES[22]);
            }
        }

        return this.stopPlay;
    }

    private void slowdownGaperd(double dt) {
        actorveh car = Crew.getIgrokCar();

        if (this.slowdown_start) {
            this.slowdown_start = false;

            double breakingDistance = this.config.getAfterRopeBreakingDistance();
            double initialVelocity = car.gVelocity().length();

            this.slowDownAcceleration = (-(initialVelocity * initialVelocity) / 2.0D * breakingDistance);
        }

        double newVelocity = car.gVelocity().length() + this.slowDownAcceleration * dt;

        this.slowDowningTime += dt;

        if ((0.0D >= newVelocity) || (this.slowDowningTime > 15.0D)) {
            this.to_slow_down_gepard = false;
            newVelocity = 0.0D;
            car.setHandBreak(true);
            interruptPerFrameExecution();
        }

        car.sVeclocity(newVelocity);
    }

    private void createCars_meet_monica() {
        vectorJ pos = getPOINT_TRUCKSTOP();
        parkingplace place = parkingplace.findParkingByName("pk_BR_MD_01", pos);

        for (int i = 0; i < CAR_NAMES.length; ++i) {
            actorveh car = eng.CreateCarForScenario(CAR_NAMES[i], new matrixJ(), pos);

            car.makeParking(place, PARKINGPLACES[i]);
            this.cars[i] = car;
        }
    }

    private void createCars_meet_assaultteam() {
        vectorJ pos = getPOINT_TRUCKSTOP();
        parkingplace place = parkingplace.findParkingByName("pk_BR_MD_01", pos);

        for (int i = 0; i < CAR_NAMES_ASSAULT.length; ++i) {
            actorveh car = eng.CreateCarForScenario(CAR_NAMES_ASSAULT[i], new matrixJ(), pos);

            car.makeParking(place, PARKINGPLACES_ASSAULT[i]);
            this.cars_assault[i] = car;
        }
    }

    private void track_tunnel() {
        vectorJ pos_enemy = this.cars[0].gPosition();
        vectorJ pos_we = Crew.getIgrokCar().gPosition();
        double len2 = pos_enemy.len2(pos_we);

        if (len2 < 1600.0D) {
            this.cars[0].stop_autopilot();
            EventsControllerHelper.messageEventHappened("blowcar");
            setTrackTunnel(false);
        } else {
            if (len2 <= 160000.0D) {
                return;
            }

            EventsControllerHelper.messageEventHappened(MESSAGES[9]);
            setTrackTunnel(false);
        }
    }

    /**
     * Method description
     *
     */
    public void met_monica() {
        actorveh car = Crew.getIgrokCar();

        this.monica = new aiplayer("ID_MONICA_2");
        this.monica.setModelCreator(new ScenarioPersonPassanger(), null);
        this.monica.bePassangerOfCar(car);

        vectorJ pos = eng.getControlPointPosition("EnemyBaseTunnel");

        this.cars[0].leaveParking();
        this.cars[0].autopilotTo(pos);

        Chase chase = new Chase();

        chase.paramModerateChasing();
        chase.be_ahead(this.cars[0], Crew.getIgrokCar());

        aiplayer driver = new aiplayer("SC_BANDIT");

        driver.setModelCreator(new ScenarioPersonPassanger(), null);
        driver.beDriverOfCar(this.cars[0]);
        offDword("DWORD_EnemyBaseMonica");
        setDword("DWORD_EnemyBaseTunnel");
        setTrackTunnel(true);
    }

    private void setTrackTunnel(boolean value) {
        if (value) {
            this.to_track_tunnel = true;
            traffic.enterChaseModeSmooth();
        } else {
            this.to_track_tunnel = false;
            traffic.setTrafficMode(0);
        }
    }

    /**
     * Method description
     *
     */
    public void met_assault_team() {
        eng.lock();

        actorveh player = Crew.getIgrokCar();
        vectorJ pos = this.cars_assault[0].gPosition();
        matrixJ mat = this.cars_assault[0].gMatrix();

        player.leaveParking();
        player.deleteSemitrailerIfExists();
        this.cars_assault[0].leaveParking();

        vehicle gepard = this.cars_assault[0].takeoff_currentcar();

        gepard.setLeased(true);

        vehicle lastPlayerVehicle = player.querryCurrentCar();

        this.wasVehicleExchange = true;
        vehicle.changeLiveVehicle(player, gepard, mat, pos);
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.waitVehicleChanged();
        eng.lock();
        rnr.src.rnrscenario.Helper.placeLiveCarInGarage(lastPlayerVehicle);

        for (int i = 0; i < this.cars_assault.length; ++i) {
            if (null == this.cars_assault[i]) {
                continue;
            }

            deactivateAssaultCar(i);
        }

        offDword("DWORD_EnemyBaseAssaultTeam");
        setDword("DWORD_EnemyBaseAssault");

        vectorJ pos_linup = getPOINT_LINEUP();

        for (int i = 0; i < CAR_NAMES_ASSAULT.length; ++i) {
            pos_linup.x -= 4.0D;

            if (i == 0) {
                continue;
            }

            this.cars_assault[i] = eng.CreateCarForScenario(CAR_NAMES_ASSAULT[i], new matrixJ(), pos_linup);
        }

        eng.unlock();
        this.want_dakota_warning = true;
    }

    /**
     * Method description
     *
     *
     * @param _fail
     */
    public void finish_tunnel(boolean _fail) {
        actorveh car = Crew.getIgrokCar();

        this.monica.abondoneCar(car);

        for (actorveh _car : this.cars) {
            _car.deactivate();
        }

        vectorJ pos = getPOINT_TRUCKSTOP();

        car.teleport(pos);

        if (_fail) {
            return;
        }

        this.to_make_threat_call = new Timing_ThreatenCall();
    }

    /**
     * Method description
     *
     */
    public void threat_call() {
        this.to_make_dakota_call = new Timing_DakotaCall();
    }

    /**
     * Method description
     *
     */
    public void dakota_call() {}

    /**
     * Method description
     *
     */
    public void monica_discovered_base() {
        this.to_make_discover_base = new Timing_MonicaFindsOut();
    }

    /**
     * Method description
     *
     */
    public void monica_call() {}

    /**
     * Method description
     *
     */
    public void monica_call_quest_started() {
        setDword("DWORD_EnemyBaseMonica");
        createCars_meet_monica();
    }

    /**
     * Method description
     *
     */
    public void monica_discovered_base_finish_call() {
        this.to_make_threat_call = new Timing_ThreatenCall();
    }

    private void createAssaultCycleScene() {
        if (eng.useNative()) {
            vectorJ pos = eng.getControlPointPosition("EnemyBaseAssaultRoot");
            Vector v = new Vector();
            SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");

            v.add(new SceneActorsPool("baza", person));

            Data _data = new Data(new matrixJ(), pos);

            this.assault_cycling_scene = trackscripts.CreateSceneXMLCycle("02040a_part2", v, _data);
        }

        this.isAssaultCycleSceneCreated = true;
    }

    /**
     * Method description
     *
     */
    public void assault_begun() {
        setDword("DWORD_EnemyBaseWireRope");
        createAssaultCycleScene();
        this.assault_started = true;
        this.assault_start_time = 0.0D;
    }

    /**
     * Method description
     *
     */
    public void assault_succeded() {
        this.to_slow_down_gepard = true;
        this.slowdown_start = true;
        this.assault_started = false;
        deleteAssaultCycleScene();
        EventsControllerHelper.messageEventHappened(MESSAGES[16]);
    }

    /**
     * Method description
     *
     */
    public void deleteScenesResources() {
        sc02040.removeScene();

        if (!(this.isAssaultCycleSceneCreated)) {
            return;
        }

        this.isAssaultCycleSceneCreated = false;
        scenetrack.DeleteScene(this.assault_cycling_scene);
    }

    private void deleteAssaultCycleScene() {
        if (!(this.isAssaultCycleSceneCreated)) {
            return;
        }

        this.isAssaultCycleSceneCreated = false;
        scenetrack.DeleteScene(this.assault_cycling_scene);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isAssaultFailed() {
        return this.assaultFailed;
    }

    /**
     * Method description
     *
     */
    public void assault_failed() {
        if (eng.useNative()) {
            sc02040.removeScene();
        }

        this.assault_started = false;
        this.assaultFailed = true;

        if (!(eng.useNative())) {
            return;
        }

        deleteAssaultCycleScene();
        sc02040.removeScene();
        unregisterMoatEvent();

        if (this.was_near_base) {
            EventsControllerHelper.messageEventHappened(MESSAGES[18]);
        } else {
            EventsControllerHelper.messageEventHappened(MESSAGES[17]);
        }
    }

    /**
     * Method description
     *
     */
    public void finish_Enemy_base() {
        deinit();
        EventsControllerHelper.messageEventHappened(MESSAGES[23]);
        this.stopPlay = true;
    }

    private void deactivateAssaultCarsButGepard() {
        for (int i = 0; i < this.cars_assault.length; ++i) {
            if (i == 0) {
                continue;
            }

            deactivateAssaultCar(i);
        }
    }

    /**
     * Method description
     *
     */
    public void interruptPerFrameExecution() {
        this.interruptPlay = true;
    }

    /**
     * Method description
     *
     */
    public void deinit() {
        gENEMYBASE = null;
        this.serializator.unRegisterObjectXmlSerializable();
        this.host.unregisterController(this);
        this.stopPlay = true;
        this.interruptPlay = true;

        for (int eventId : this.event_ids) {
            event.removeEventObject(eventId);
        }

        if (null != this.dakota) {
            this.dakota.abondoneCar(this.cars_assault[2]);
            this.dakota = null;
        }

        eng.explosionsWhilePredefinedAnimation(false);
        deleteAssaultCycleScene();
        unregisterMoatEvent();
        sc02040.removeScene();
        deactivateAssaultCarsButGepard();
        switchOffActiveDwords();
        EventsControllerHelper.getInstance().removeMessageListener(this, "dakota_call", MESSAGES[12]);
        EventsControllerHelper.getInstance().removeMessageListener(this, "threat_call", MESSAGES[8]);
        EventsControllerHelper.getInstance().removeMessageListener(this, "monica_discovered_base", MESSAGES[4]);
        EventsControllerHelper.getInstance().removeMessageListener(this, "monica_discovered_base", MESSAGES[9]);
        EventsControllerHelper.getInstance().removeMessageListener(this, "monica_discovered_base_finish_call",
                MESSAGES[6]);
        EventsControllerHelper.getInstance().removeMessageListener(this, "started_1600", MESSAGES[24]);
        EventsControllerHelper.getInstance().removeMessageListener(this, "failed_2025", MESSAGES[26]);
        EventsControllerHelper.getInstance().removeMessageListener(this, "failedToReachEnemyBase", "m02030 failed");
    }

    private void switchOffActiveDwords() {
        for (String currentActiveDword : this.currentActiveDwords) {
            if (null != currentActiveDword) {
                eng.setdword(currentActiveDword, 0);
            }
        }

        this.currentActiveDwords.clear();
    }

    private void deactivateAssaultCar(int i) {
        if (null == this.cars_assault[i]) {
            return;
        }

        this.cars_assault[i].deactivate();
        this.cars_assault[i] = null;
    }

    void enter_moat() {
        this.assault_started = false;
        deleteAssaultCycleScene();
        sc02040.removeScene();
        EventsControllerHelper.messageEventHappened("EnemyBaseAssault fell in moat");
    }

    void enter_nearbase() {
        this.was_near_base = true;
    }

    void enter_behindmotel() {
        event.finishScenarioMission("m01600");
        EventsControllerHelper.messageEventHappened(MESSAGES[0]);
    }

    void enter_assaultteam() {
        EventsControllerHelper.messageEventHappened(MESSAGES[13]);
    }

    void enter_tracktunnel() {
        setTrackTunnel(false);
        offDword("DWORD_EnemyBaseTunnel");
        EventsControllerHelper.messageEventHappened(MESSAGES[10]);
    }

    void enter_assault() {
        this.cars_assault[1].UpdateCar();
        this.cars_assault[1].registerCar("JOHN");
        this.cars_assault[1].setCollideMode(false);
        this.cars_assault[2].UpdateCar();
        this.cars_assault[2].registerCar("DAKOTA");
        this.dakota = new aiplayer("SC_ONTANIELOLOW");
        this.dakota.setModelCreator(new ScenarioPersonPassanger(), null);
        this.dakota.beDriverOfCar(this.cars_assault[2]);
        eng.explosionsWhilePredefinedAnimation(true);
        EventsControllerHelper.messageEventHappened(MESSAGES[15]);
    }

    /**
     * Method description
     *
     */
    public void failed_2025() {
        for (int i = 0; i < this.cars_assault.length; ++i) {
            deactivateAssaultCar(i);
        }

        this.to_make_2020_call = new Timing_BadCall();
    }

    /**
     * Method description
     *
     */
    public void started_1600() {
        monica_call_quest_started();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isWasVehicleExchange() {
        return this.wasVehicleExchange;
    }

    /**
     * Method description
     *
     *
     * @param wasVehicleExchange
     */
    public void setWasVehicleExchange(boolean wasVehicleExchange) {
        this.wasVehicleExchange = wasVehicleExchange;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isAssaultCycleSceneCreated() {
        return this.isAssaultCycleSceneCreated;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setAssaultCycleSceneCreated(boolean value) {
        this.isAssaultCycleSceneCreated = value;

        if (!(this.isAssaultCycleSceneCreated)) {
            return;
        }

        createAssaultCycleScene();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isAssault_started() {
        return this.assault_started;
    }

    /**
     * Method description
     *
     *
     * @param assault_started
     */
    public void setAssault_started(boolean assault_started) {
        this.assault_started = assault_started;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isBad_conditions() {
        return this.bad_conditions;
    }

    /**
     * Method description
     *
     *
     * @param bad_conditions
     */
    public void setBad_conditions(boolean bad_conditions) {
        this.bad_conditions = bad_conditions;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public actorveh[] getCars() {
        return this.cars;
    }

    /**
     * Method description
     *
     *
     * @param cars
     */
    public void setCars(actorveh[] cars) {
        this.cars = cars;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public actorveh[] getCars_assault() {
        return this.cars_assault;
    }

    /**
     * Method description
     *
     *
     * @param cars_assault
     */
    public void setCars_assault(actorveh[] cars_assault) {
        this.cars_assault = cars_assault;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public aiplayer getDakota() {
        return this.dakota;
    }

    /**
     * Method description
     *
     *
     * @param dakota
     */
    public void setDakota(aiplayer dakota) {
        this.dakota = dakota;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public aiplayer getMonica() {
        return this.monica;
    }

    /**
     * Method description
     *
     *
     * @param monica
     */
    public void setMonica(aiplayer monica) {
        this.monica = monica;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getAssault_start_time() {
        return this.assault_start_time;
    }

    /**
     * Method description
     *
     *
     * @param assault_start_time
     */
    public void setAssault_start_time(double assault_start_time) {
        this.assault_start_time = assault_start_time;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getDakota_warning_start_time() {
        return this.dakota_warning_start_time;
    }

    /**
     * Method description
     *
     *
     * @param dakota_warning_start_time
     */
    public void setDakota_warning_start_time(double dakota_warning_start_time) {
        this.dakota_warning_start_time = dakota_warning_start_time;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getInitialVelocity() {
        return this.initialVelocity;
    }

    /**
     * Method description
     *
     *
     * @param initialVelocity
     */
    public void setInitialVelocity(double initialVelocity) {
        this.initialVelocity = initialVelocity;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getSlowDownAcceleration() {
        return this.slowDownAcceleration;
    }

    /**
     * Method description
     *
     *
     * @param slow_down_accel
     */
    public void setSlowDownAcceleration(double slow_down_accel) {
        this.slowDownAcceleration = slow_down_accel;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isSlowdown_start() {
        return this.slowdown_start;
    }

    /**
     * Method description
     *
     *
     * @param slowdown_start
     */
    public void setSlowdown_start(boolean slowdown_start) {
        this.slowdown_start = slowdown_start;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Timing_BadCall getTo_make_2020_call() {
        return this.to_make_2020_call;
    }

    /**
     * Method description
     *
     *
     * @param to_make_2020_call
     */
    public void setTo_make_2020_call(Timing_BadCall to_make_2020_call) {
        this.to_make_2020_call = to_make_2020_call;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Timing_DakotaCall getTo_make_dakota_call() {
        return this.to_make_dakota_call;
    }

    /**
     * Method description
     *
     *
     * @param to_make_dakota_call
     */
    public void setTo_make_dakota_call(Timing_DakotaCall to_make_dakota_call) {
        this.to_make_dakota_call = to_make_dakota_call;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Timing_MonicaFindsOut getTo_make_discover_base() {
        return this.to_make_discover_base;
    }

    /**
     * Method description
     *
     *
     * @param to_make_discover_base
     */
    public void setTo_make_discover_base(Timing_MonicaFindsOut to_make_discover_base) {
        this.to_make_discover_base = to_make_discover_base;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Timing_ThreatenCall getTo_make_threat_call() {
        return this.to_make_threat_call;
    }

    /**
     * Method description
     *
     *
     * @param to_make_threat_call
     */
    public void setTo_make_threat_call(Timing_ThreatenCall to_make_threat_call) {
        this.to_make_threat_call = to_make_threat_call;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isTo_slow_down_gepard() {
        return this.to_slow_down_gepard;
    }

    /**
     * Method description
     *
     *
     * @param to_slow_down_gepard
     */
    public void setTo_slow_down_gepard(boolean to_slow_down_gepard) {
        this.to_slow_down_gepard = to_slow_down_gepard;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isTo_track_tunnel() {
        return this.to_track_tunnel;
    }

    /**
     * Method description
     *
     *
     * @param to_track_tunnel
     */
    public void setTo_track_tunnel(boolean to_track_tunnel) {
        this.to_track_tunnel = to_track_tunnel;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isWant_dakota_warning() {
        return this.want_dakota_warning;
    }

    /**
     * Method description
     *
     *
     * @param want_dakota_warning
     */
    public void setWant_dakota_warning(boolean want_dakota_warning) {
        this.want_dakota_warning = want_dakota_warning;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isWant_dakota_warning_started() {
        return this.want_dakota_warning_started;
    }

    /**
     * Method description
     *
     *
     * @param want_dakota_warning_started
     */
    public void setWant_dakota_warning_started(boolean want_dakota_warning_started) {
        this.want_dakota_warning_started = want_dakota_warning_started;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isWas_near_base() {
        return this.was_near_base;
    }

    /**
     * Method description
     *
     *
     * @param was_near_base
     */
    public void setWas_near_base(boolean was_near_base) {
        this.was_near_base = was_near_base;
    }

    /**
     * Method description
     *
     *
     * @param dword
     */
    public void activateDword(String dword) {
        if ((null == dword) || (!(allDwords.contains(dword))) || (this.currentActiveDwords.contains(dword))) {
            return;
        }

        setDword(dword);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Collection<String> getCurrentDwords() {
        return Collections.unmodifiableSet(this.currentActiveDwords);
    }

    static class Data {
        matrixJ M;
        vectorJ P;

        Data() {}

        Data(matrixJ M, vectorJ P) {
            this.M = M;
            this.P = P;
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ
     */
    public static class Timing_BadCall implements IInterruptCall {
        boolean finished = false;
        CoreTime finishTime;

        /**
         * Constructs ...
         *
         */
        public Timing_BadCall() {
            this.finishTime = ConvertGameTime.convertFromCurrent(1800);
            CBVideoInterruptCalls.add(this);
        }

        boolean run() {
            CoreTime current_time = new CoreTime();

            if ((!(this.finished)) && (current_time.moreThan(this.finishTime) >= 0)) {
                EventsControllerHelper.messageEventHappened(EnemyBase.MESSAGES[27]);
                CBVideoInterruptCalls.remove(this);
                this.finished = true;

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
        @Override
        public CoreTime getInterruptTime() {
            return this.finishTime;
        }

        /**
         * Method description
         *
         *
         * @param value
         */
        public void setInterruptTime(CoreTime value) {
            this.finishTime = value;
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ
     */
    public static class Timing_DakotaCall implements IInterruptCall {
        boolean finished = false;
        CoreTime finishTime;

        /**
         * Constructs ...
         *
         */
        public Timing_DakotaCall() {
            this.finishTime = ConvertGameTime.convertFromCurrent(600);
            CBVideoInterruptCalls.add(this);
        }

        boolean run() {
            CoreTime current_time = new CoreTime();

            if ((!(this.finished)) && (0 <= current_time.moreThan(this.finishTime))) {
                EventsControllerHelper.messageEventHappened(EnemyBase.MESSAGES[11]);
                CBVideoInterruptCalls.remove(this);
                this.finished = true;

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
        @Override
        public CoreTime getInterruptTime() {
            return this.finishTime;
        }

        /**
         * Method description
         *
         *
         * @param value
         */
        public void setInterruptTime(CoreTime value) {
            this.finishTime = value;
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ
     */
    public static class Timing_MonicaFindsOut implements IInterruptCall {
        boolean finished = false;
        CoreTime finishTime;

        /**
         * Constructs ...
         *
         */
        public Timing_MonicaFindsOut() {
            this.finishTime = ConvertGameTime.convertFromCurrent(900);
            CBVideoInterruptCalls.add(this);
        }

        boolean run() {
            CoreTime current_time = new CoreTime();

            if ((!(this.finished)) && (current_time.moreThan(this.finishTime) >= 0)) {
                EventsControllerHelper.messageEventHappened(EnemyBase.MESSAGES[5]);
                this.finished = true;
                CBVideoInterruptCalls.remove(this);

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
        @Override
        public CoreTime getInterruptTime() {
            return this.finishTime;
        }

        /**
         * Method description
         *
         *
         * @param value
         */
        public void setInterruptTime(CoreTime value) {
            this.finishTime = value;
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ
     */
    public static class Timing_ThreatenCall {
        private static final double MOURNING_TIME = 6.0D;
        boolean finished = false;
        CoreTime finishTime = null;

        /**
         * Constructs ...
         *
         */
        public Timing_ThreatenCall() {
            this.finishTime = new CoreTime();

            int year = this.finishTime.gYear();
            int month = this.finishTime.gMonth();
            int date = this.finishTime.gDate();
            int hour = this.finishTime.gHour();

            if (hour < 6.0D) {
                this.finishTime = new CoreTime(year, month, date, getRandomHour(), 0);
            } else {
                this.finishTime = new CoreTime(year, month, date, getRandomHour(), 0);
                this.finishTime.plus_days(1);
            }
        }

        private int getRandomHour() {
            return AdvancedRandom.RandFromInreval(12, 16);
        }

        boolean run() {
            CoreTime current_time = new CoreTime();

            if ((!(this.finished)) && (current_time.moreThan(this.finishTime) >= 0)) {
                EventsControllerHelper.messageEventHappened(EnemyBase.MESSAGES[7]);
                this.finished = true;

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
        public CoreTime getFinishTime() {
            return this.finishTime;
        }

        /**
         * Method description
         *
         *
         * @param finishTime
         */
        public void setFinishTime(CoreTime finishTime) {
            this.finishTime = finishTime;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
