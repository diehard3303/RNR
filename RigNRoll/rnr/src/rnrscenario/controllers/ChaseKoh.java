/*
 * @(#)ChaseKoh.java   13/08/26
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
import rnr.src.players.CutSceneAuxPersonCreator;
import rnr.src.players.ScenarioPersonPassanger;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.ChaseKohObjectXmlSerializable;
import rnr.src.rnrcore.ObjectXmlSerializable;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.teleport.ITeleported;
import rnr.src.rnrcore.teleport.MakeTeleport;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.ChaseKohShootAnimate;
import rnr.src.rnrscenario.IShootChasing;
import rnr.src.rnrscenario.animation.ShootingSeriesAnimation;
import rnr.src.rnrscenario.config.Config;
import rnr.src.rnrscenario.config.ConfigManager;
import rnr.src.rnrscenario.configurators.ChaseCochConfig;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.scenes.CrashBarScene;
import rnr.src.rnrscr.parkingplace;
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@ScenarioClass(scenarioStage = 15)
public class ChaseKoh extends TypicalAnm implements IShootChasing, ScenarioController {

    /** Field description */
    public static final String POINT_START = "ChaseKohStart";

    /** Field description */
    public static final String POINT_FINISH = "ChaseKohFinish";

    /** Field description */
    public static final String PROC_START = "KeyPoint_2050";

    /** Field description */
    public static final String DWORD_BAR_INSIDE = "Dword_BarIn4_Crash";

    /** Field description */
    public static final String MESSAGE_CREATE_START_SCENE = "start 2050";

    /** Field description */
    public static final String MESSAGE_SUCCES = "Chase Koh succes";

    /** Field description */
    public static final String MESSAGE_FAIL = "Chase Koh fail";

    /** Field description */
    public static final String MESSAGE_FAR = "Chase Koh far";

    /** Field description */
    public static final String DWORD_ChaseKoh = "DWORD_ChaseKoh";

    /** Field description */
    public static final String DWORD_Bar = "DWORD_ChaseKoh_DemolishedBar";
    private static final double DISTANCE_CHASE = 500.0D;
    private static final double DISTANCE_KILL = 20.0D;
    private static final double VELOCITY_TO_BLOW_AFTER_TELEPORT = 1.0D;
    private static final int[] EVENTS;
    private static final String[] EVENT_METHODS;
    private static ChaseKoh instance;

    static {
        EVENTS = new int[] { 22050 };
        EVENT_METHODS = new String[] { "enter_lastchance" };
        instance = null;
    }

    private boolean needStopAnimation = false;
    private boolean scheduleAfterLoadActions = false;
    private TemporarySceneRoots inbar_sceneroots = null;
    private ShootCount shooting = null;
    private boolean needResetAiParameters = true;
    private ChaseKohShootAnimate shoot_animation = null;
    private final ShootingSeriesAnimation shootSeries = new ShootingSeriesAnimation();
    private actorveh carkohForShootAnimationSerialization = null;
    private WantBlowCar want_blow_car = null;
    private boolean chase_started = false;
    private aiplayer koh_chase_player = null;
    private boolean _last_chance_failed = false;
    private final int[] event_ids = new int[EVENTS.length];
    private boolean firstTime = true;
    private actorveh cochCar;
    private final actorveh playerCar;
    private final ScenarioHost host;
    private final ObjectXmlSerializable serializator;
    private FarAway faraway;
    private TooLong tooLong;

    /**
     * Constructs ...
     *
     *
     * @param host
     * @param isGameLoading
     */
    public ChaseKoh(ScenarioHost host, boolean isGameLoading) {
        Config config = ConfigManager.getGlobal().getConfig(1);

        assert(config instanceof ChaseCochConfig) : "illegal type of config";
        this.tooLong = new TooLong((ChaseCochConfig) config);
        this.playerCar = Crew.getIgrokCar();

        if (isGameLoading) {
            this.scheduleAfterLoadActions = true;
        } else {
            this.playerCar.teleport(eng.getControlPointPosition("ChaseKohStart"));
            MakeTeleport.teleport(new TeleportToMiWuk());
        }

        eng.CreateInfinitScriptAnimation(this);
        instance = this;

        for (int i = 0; i < EVENTS.length; ++i) {
            this.event_ids[i] = event.eventObject(EVENTS[i], this, EVENT_METHODS[i]);
        }

        this.host = host;
        this.host.registerController(this);
        this.serializator = new ChaseKohObjectXmlSerializable(this);
        this.serializator.registerObjectXmlSerializable();
    }

    /**
     * Method description
     *
     */
    @Override
    public void gameDeinitLaunched() {
        endChase();
    }

    private static double sqrDistanseFromPlayerToCar(actorveh car) {
        assert(null != car);

        vectorJ otherPosition = car.gPosition();
        vectorJ playerPosition = Crew.getIgrokCar().gPosition();

        return otherPosition.len2(playerPosition);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static ChaseKoh getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     *
     * @param externalCounterValue
     *
     * @return
     */
    @Override
    public boolean animaterun(double externalCounterValue) {
        if (!(eng.canRunScenarioAnimation())) {
            return false;
        }

        if (this.firstTime) {
            if (this.scheduleAfterLoadActions) {
                this.cochCar.setNeverUnloadFlag();
            }

            eng.console("switch Dword_Gepard_Passanger_Window 1");
            eng.setdword("DWORD_ChaseKoh", 1);
            this.firstTime = false;
        }

        if (this.chase_started) {
            this.shootSeries.animate(externalCounterValue);

            if ((this.shooting.done()) || (400.0D > sqrDistanseFromPlayerToCar(this.cochCar))) {
                EventsControllerHelper.messageEventHappened("Chase Koh succes");
                this.chase_started = false;
            } else if ((this.faraway.done()) || (this.tooLong.done(externalCounterValue))
                       || (this._last_chance_failed)) {
                deleteCoch();
                finishShootingAnimation();
                EventsControllerHelper.messageEventHappened("Chase Koh far");
                this.chase_started = false;
            } else if ((this.needResetAiParameters) && (0 != this.cochCar.getAi_player())
                       && (0 != this.playerCar.getAi_player())) {
                Chase chase = new Chase();

                chase.paramModerateChasing();
                chase.be_ahead(this.cochCar, this.playerCar);
                this.needResetAiParameters = false;
            }
        }

        if ((null != this.want_blow_car) && (this.want_blow_car.done())) {
            this.serializator.unRegisterObjectXmlSerializable();

            return true;
        }

        return this.needStopAnimation;
    }

    /**
     * Method description
     *
     */
    @Override
    public void aimed() {
        if (null == this.shooting) {
            return;
        }

        this.shooting.fast_shoot();
    }

    /**
     * Method description
     *
     */
    @Override
    public void aimed_hard() {
        if (null == this.shooting) {
            return;
        }

        this.shooting.good_shoot();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean proceedShooting() {
        return this.chase_started;
    }

    /**
     * Method description
     *
     */
    public static void demolishBar() {
        eng.setdword("DWORD_ChaseKoh", 1);
    }

    /**
     * Method description
     *
     */
    public void start_chase() {
        if (null != this.inbar_sceneroots) {
            this.inbar_sceneroots.leave();
            this.inbar_sceneroots = null;
        }

        this.chase_started = true;

        vectorJ pos_start = eng.getControlPointPosition("ChaseKohStart");

        this.faraway = new FarAway(this.cochCar);
        this.shooting = new ShootCount((ChaseCochConfig) ConfigManager.getGlobal().getConfig(1), this.cochCar);
        this.cochCar.sPosition(pos_start);

        aiplayer person_player = new aiplayer("SC_KOHLOW");

        person_player.setModelCreator(new ScenarioPersonPassanger(), "koh");
        person_player.beDriverOfCar(this.cochCar);
        this.koh_chase_player = person_player;

        vectorJ pos = eng.getControlPointPosition("ChaseKohFinish");

        this.cochCar.autopilotTo(pos);

        Chase chase = new Chase();

        chase.paramModerateChasing();
        chase.be_ahead(this.cochCar, this.playerCar);
        this.needResetAiParameters = false;
        this.shoot_animation = new ChaseKohShootAnimate(this, this.cochCar, false);
        this.carkohForShootAnimationSerialization = this.cochCar;
        eng.console("switch Dword_Gepard_Passanger_Window 1");
        eng.setdword("DWORD_ChaseKoh", 1);
    }

    /**
     * Method description
     *
     */
    public void finishShootingAnimation() {
        if (null != this.shoot_animation) {
            this.shoot_animation.finish();
        }

        this.shoot_animation = null;
        this.carkohForShootAnimationSerialization = null;
    }

    /**
     * Method description
     *
     */
    public void endChaseButContinueRun() {
        endChase();
        this.needStopAnimation = false;
        this.serializator.registerObjectXmlSerializable();
    }

    /**
     * Method description
     *
     */
    public void endChase() {
        this.chase_started = false;
        this.needStopAnimation = true;
        deleteCoch();

        for (int _eventid : this.event_ids) {
            event.removeEventObject(_eventid);
        }

        finishShootingAnimation();
        eng.console("switch Dword_Gepard_Passanger_Window 0");
        eng.setdword("DWORD_ChaseKoh", 0);
        this.serializator.unRegisterObjectXmlSerializable();
        this.host.unregisterController(this);
    }

    private void deleteCoch() {
        if (null == this.cochCar) {
            return;
        }

        if (null != this.koh_chase_player) {
            this.koh_chase_player.abondoneCar(this.cochCar);
            this.koh_chase_player = null;
        }

        this.cochCar.showOnMap(false);
        this.cochCar.leave_target();
        this.cochCar.stop_autopilot();
        this.cochCar.deactivate();
        this.cochCar = null;
    }

    /**
     * Method description
     *
     */
    public void scheduleCarBlow() {
        this.want_blow_car = new WantBlowCar();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public aiplayer getKohChased() {
        return this.koh_chase_player;
    }

    void enter_lastchance() {
        this._last_chance_failed = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean shootMade() {
        if (null != this.shooting) {
            return ((this.shootSeries.isShooting()) && (this.shooting.isDistanceAllowToShoot()));
        }

        return this.shootSeries.isShooting();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean useShootDetection() {
        return true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean is_last_chance_failed() {
        return this._last_chance_failed;
    }

    /**
     * Method description
     *
     *
     * @param _last_chance_failed
     */
    public void set_last_chance_failed(boolean _last_chance_failed) {
        this._last_chance_failed = _last_chance_failed;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public actorveh getCarkohForShootAnimationSerialization() {
        return this.carkohForShootAnimationSerialization;
    }

    /**
     * Method description
     *
     *
     * @param carkohForShootAnimationSerialization
     */
    public void setCarkohForShootAnimationSerialization(actorveh carkohForShootAnimationSerialization) {
        if (null == carkohForShootAnimationSerialization) {
            return;
        }

        this.carkohForShootAnimationSerialization = carkohForShootAnimationSerialization;
        this.shoot_animation = new ChaseKohShootAnimate(this, carkohForShootAnimationSerialization, true);
        this.cochCar = carkohForShootAnimationSerialization;
        this.cochCar.autopilotTo(eng.getControlPointPosition("ChaseKohFinish"));
        this.faraway = new FarAway(this.cochCar);
        this.shooting = new ShootCount((ChaseCochConfig) ConfigManager.getGlobal().getConfig(1), this.cochCar);

        Chase chase = new Chase();

        chase.paramModerateChasing();
        chase.be_ahead(this.cochCar, this.playerCar);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isChase_started() {
        return this.chase_started;
    }

    /**
     * Method description
     *
     *
     * @param chase_started
     */
    public void setChase_started(boolean chase_started) {
        this.chase_started = chase_started;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public aiplayer getKoh_chase_player() {
        return this.koh_chase_player;
    }

    /**
     * Method description
     *
     *
     * @param koh_chase_player
     */
    public void setKoh_chase_player(aiplayer koh_chase_player) {
        this.koh_chase_player = koh_chase_player;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public WantBlowCar getWant_blow_car() {
        return this.want_blow_car;
    }

    /**
     * Method description
     *
     *
     * @param want_blow_car
     */
    public void setWant_blow_car(WantBlowCar want_blow_car) {
        this.want_blow_car = want_blow_car;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public TooLong getTooLong() {
        return this.tooLong;
    }

    /**
     * Method description
     *
     *
     * @param tooLong
     */
    public void setTooLong(TooLong tooLong) {
        this.tooLong = tooLong;
    }

    static final class FarAway {
        private actorveh target = null;

        FarAway(actorveh target) {
            this.target = target;
        }

        boolean done() {
            return (ChaseKoh.access$000(this.target) > 250000.0D);
        }
    }


    static final class ShootCount {
        int count = -1;
        boolean was_good_shot = false;
        private final int shootsToKill;
        private final double distanceToHit;
        private final actorveh target;

        /**
         * Constructs ...
         *
         *
         * @param chaseCochConfig
         * @param targetToShoot
         */
        public ShootCount(ChaseCochConfig chaseCochConfig, actorveh targetToShoot) {
            this.target = targetToShoot;
            this.shootsToKill = chaseCochConfig.getShootsToKillCoch();
            this.distanceToHit = chaseCochConfig.getDistanceToHitCoch();
        }

        void fast_shoot() {
            if (!(isDistanceAllowToShoot())) {
                return;
            }

            this.count += 1;
        }

        void good_shoot() {
            this.was_good_shot = true;
        }

        boolean done() {
            return ((this.was_good_shot) || (this.count > this.shootsToKill));
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isDistanceAllowToShoot() {
            return (ChaseKoh.access$000(this.target) < this.distanceToHit * this.distanceToHit);
        }
    }


    final class TeleportToMiWuk implements ITeleported {

        /**
         * Method description
         *
         */
        @Override
        public void teleported() {
            vectorJ position = eng.getControlPointPosition("ChaseKohStart");
            parkingplace place = parkingplace.findParkingByName("pk_MW_01", position);

            ChaseKoh.this.playerCar.makeParking(place, 0);
            ChaseKoh.access$202(ChaseKoh.this, eng.CreateCarForScenario(CarName.CAR_DAKOTA, new matrixJ(), position));
            ChaseKoh.this.cochCar.showOnMap(true);

            aiplayer person_player = new aiplayer("SC_KOH");

            person_player.sPoolBased("koh_cut_scene_driver");
            person_player.setModelCreator(new CutSceneAuxPersonCreator(), "koh");
            person_player.beDriverOfCar(ChaseKoh.this.cochCar);

            SCRuniperson koh_person = person_player.getModel();

            koh_person.SetInWorld("bar_crash");
            Crew.addMappedCar("KOH", ChaseKoh.this.cochCar);
            ChaseKoh.access$302(ChaseKoh.this,
                                new ChaseKoh.TemporarySceneRoots(ChaseKoh.this.cochCar, koh_person, person_player));
            eng.setdword("Dword_BarIn4_Crash", 1);
            new CrashBarScene().run();
        }
    }


    static class TemporarySceneRoots {
        actorveh car;
        SCRuniperson person;
        aiplayer player;

        TemporarySceneRoots(actorveh car, SCRuniperson person, aiplayer player) {
            this.car = car;
            this.person = person;
            this.player = player;
            person.lockPerson();
        }

        void leave() {
            this.player.abondoneCar(this.car);
            this.person.unlockPerson();
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static final class TooLong {
        private boolean firstTime = true;
        private double previousCounterValue = 0.0D;
        private double time = 0.0D;
        private final ChaseCochConfig config;

        /**
         * Constructs ...
         *
         *
         * @param config
         */
        public TooLong(ChaseCochConfig config) {
            this.config = config;
        }

        boolean done(double externalTimeCounter) {
            if (this.firstTime) {
                this.firstTime = false;
                this.previousCounterValue = externalTimeCounter;

                return false;
            }

            this.time += externalTimeCounter - this.previousCounterValue;
            this.previousCounterValue = externalTimeCounter;

            return (this.config.getTimeToCatchCoch() < this.time);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public double getTime() {
            return this.time;
        }

        /**
         * Method description
         *
         *
         * @param time
         */
        public void setTime(double time) {
            this.time = time;
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static final class WantBlowCar {
        boolean done() {
            double playerCarVelocity = Crew.getIgrokCar().gVelocity().length();

            if (1.0D < playerCarVelocity) {
                EventsControllerHelper.messageEventHappened("player car exploded");
                EventsControllerHelper.messageEventHappened("blowcar");

                return true;
            }

            return false;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
