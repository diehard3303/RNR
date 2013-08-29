/*
 * @(#)chase00090.java   13/08/26
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

import rnr.src.players.Chase;
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrconfig.Config;
import rnr.src.rnrcore.ChaseObjectXmlSerializable;
import rnr.src.rnrcore.ConvertGameTime;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.ObjectXmlSerializable;
import rnr.src.rnrcore.SCRtalkingperson;
import rnr.src.rnrcore.Sphere;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.IShootChasing;
import rnr.src.rnrscenario.animation.ShootingSeriesAnimation;
import rnr.src.rnrscenario.config.ConfigManager;
import rnr.src.rnrscenario.configurators.ChaseToRescueDorothyConfig;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;
import rnr.src.rnrscenario.sctask;
import rnr.src.rnrscr.drvscripts;
import rnr.src.scriptEvents.EventsControllerHelper;
import rnr.src.xmlserialization.nxs.AnnotatedSerializable;
import rnr.src.xmlserialization.nxs.LoadFrom;
import rnr.src.xmlserialization.nxs.SaveTo;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
@ScenarioClass(
    scenarioStage = 1,
    fieldWithDesiredStage = ""
)
public final class chase00090 extends sctask implements IShootChasing, AnnotatedSerializable, ScenarioController {
    private static final String BAR_WHERE_BANDITS_GO_AFTER_CHASE = "MP_Bar_OV_SB_01";
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int ANIMATION_TIME_DELTA = 0;

    /** Field description */
    public static final String MY_SERIALIZATION_UID = "chase00090";
    private static final double MILLISECONDS_IN_SECOND = 1000.0D;
    private static final boolean NO_MESSAGES;
    private static aiplayer dorothy;
    private static chase00090 instance;

    static {
        NO_MESSAGES = !(Config.debugInformationEnabled);
        dorothy = null;
        instance = null;
    }

    private int minutesToEscape = 3;
    private double distanceToEscape = 500.0D;
    private double distanceToKill = 10.0D;
    private double distanceToShoot = 200.0D;
    @SaveTo(
        destinationNodeName = "chase_start_time",
        constructorArgumentNumber = 0,
        saveVersion = 0
    )
    @LoadFrom(
        sourceNodeName = "chase_start_time",
        fromVersion = 0,
        untilVersion = 0
    )
    private CoreTime chaseStartTime = null;
    private vectorJ barInSantaBarbaraPosition = new vectorJ(1800.0D, -23.5D, 21.0D);
    private ResqueDorothyShootAnimate shootAnimation = null;
    private final ShootingSeriesAnimation shootSeries = new ShootingSeriesAnimation();
    private SCRtalkingperson dorothyAnimation = null;
    private boolean isChaserCarRegistered = false;
    private boolean needShooting = false;
    private boolean predefinedAnimationFinished = false;
    private boolean debugMode = false;
    private boolean scheduleChasingStart = false;
    private final Object latch = new Object();
    private vectorJ previousBanditsPosition = null;
    private Sphere playerCarBoundingShpere = null;
    private final actorveh playerCar;
    private final actorveh banditsCar;
    private ChaseToRescueDorothyConfig config;
    private final ObjectXmlSerializable serializator;
    private ScenarioHost host;

    /**
     * Constructs ...
     *
     */
    public chase00090() {
        super(0, true);
        instance = this;

        if (null != ConfigManager.getGlobal()) {
            this.config = ((ChaseToRescueDorothyConfig) ConfigManager.getGlobal().getConfig(2));
        }

        if (null != MissionSystemInitializer.getMissionsMap()) {
            Place bar = MissionSystemInitializer.getMissionsMap().getPlace("MP_Bar_OV_SB_01");

            if (null != bar) {
                this.barInSantaBarbaraPosition = bar.getCoords();
            }
        }

        this.banditsCar = Crew.getMappedCar("ARGOSY BANDIT");
        this.playerCar = Crew.getIgrokCar();
        this.serializator = new ChaseObjectXmlSerializable(this);
        this.serializator.registerObjectXmlSerializable();
    }

    private boolean escapedFromChase(vectorJ playerPosition, vectorJ banditsPosition) {
        assert((null != playerPosition) && (null != banditsPosition)) : "all arguments must be non-null references";

        CoreTime chaseEndTime = ConvertGameTime.convertFromGiven(this.minutesToEscape * 60, this.chaseStartTime);

        return ((this.distanceToEscape * this.distanceToEscape < playerPosition.len2(banditsPosition))
                || (0 < new CoreTime().moreThan(chaseEndTime)));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static chase00090 getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     *
     * @param host
     */
    public static void constructStarted(ScenarioHost host) {
        chase00090 ware = new chase00090();

        ware.setHost(host);
        ware.shootAnimation = new ResqueDorothyShootAnimate(ware, ware.banditsCar, false);
        ware.start();
        ware.banditsCar.showOnMap(true);
    }

    /**
     * Method description
     *
     *
     * @param scenarioHost
     */
    public void setHost(ScenarioHost scenarioHost) {
        scenarioHost.registerController(this);
        this.host = scenarioHost;
    }

    private void deleteBandits() {
        eng.console("switch Dword_Freight_Argosy_Passanger_Window 0");
        this.banditsCar.leave_target();
        this.banditsCar.deactivate();
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        if (null == instance) {
            return;
        }

        chase00090 singleton = instance;

        instance = null;

        if (null != singleton.shootAnimation) {
            singleton.shootAnimation.finish();
        }

        singleton.stopAnimateDorothy(true);
        singleton.deleteBandits();
        singleton.finishImmediately();
        singleton.finish();
    }

    /**
     * Method description
     *
     */
    @Override
    public void finish() {
        if (this.f_finished) {
            return;
        }

        super.finish();
        this.serializator.unRegisterObjectXmlSerializable();
        this.host.unregisterController(this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void gameDeinitLaunched() {
        deinit();
    }

    private void debugMessage(String message) {
        if (NO_MESSAGES) {
            return;
        }

        eng.writeLog(message);
        eng.pager(message);
    }

    private void updateParamsFromConfig() {
        if (null == this.config) {
            return;
        }

        this.minutesToEscape = this.config.getTimeToRescue();
        this.distanceToEscape = this.config.getDistanceToRescue();
        this.distanceToKill = this.config.getDistanceToKill();
        this.distanceToShoot = this.config.getDistanceToShoot();
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        updateParamsFromConfig();

        if ((this.scheduleChasingStart) && (0 != this.banditsCar.getAi_player())
                && (0 != this.playerCar.getAi_player())) {
            startChasingPlayer();
            this.scheduleChasingStart = false;
        }

        if ((!(this.isChaserCarRegistered)) && (0 != this.banditsCar.getCar())) {
            this.banditsCar.registerCar("banditcar");
            this.isChaserCarRegistered = true;
        }

        animateDorothy();
        animateChasing();
    }

    private void animateChasing() {
        synchronized (this.latch) {
            if (!(this.predefinedAnimationFinished)) {
                this.needShooting = true;

                return;
            }
        }

        vectorJ playerPosition = this.playerCar.gPosition();
        vectorJ banditsPosition = this.banditsCar.gPosition();

        if (escapedFromChase(playerPosition, banditsPosition)) {
            debugMessage("escaped from bandits");
            this.needShooting = false;

            if (this.debugMode) {
                return;
            }

            stopChasingPlayer();
            successRescued();
            deinit();
        } else if (playerPosition.len2(banditsPosition) < this.distanceToShoot * this.distanceToShoot) {
            debugMessage("shooting distance");
            this.needShooting = true;
            this.playerCarBoundingShpere.setCenter(playerPosition.x, playerPosition.y, playerPosition.z);

            if ((this.playerCarBoundingShpere.intersecs(this.previousBanditsPosition, banditsPosition))
                    && (!(this.debugMode)) && (eng.canRunScenarioAnimation())) {
                debugMessage("death distance");
                finishImmediately();
                blowPlayersCar();
                deinit();
            }

            this.previousBanditsPosition = banditsPosition;
        } else {
            this.needShooting = false;
        }
    }

    /**
     * Method description
     *
     */
    public static void setDebugModeOff() {
        if (null == instance) {
            return;
        }

        instance.debugMode = false;
    }

    /**
     * Method description
     *
     */
    public static void setDebugMode() {
        if (null == instance) {
            return;
        }

        instance.debugMode = true;
    }

    private void animateDorothy() {
        if (null != this.dorothyAnimation) {
            return;
        }

        if (null == dorothy) {
            return;
        }

        this.dorothyAnimation = new SCRtalkingperson(dorothy.getModel());
        this.dorothyAnimation.playAnimation("DOROTY_NEW_pas_cycle", 1.0D);
    }

    private void stopChasingPlayer() {
        this.banditsCar.leave_target();
        this.banditsCar.autopilotTo(this.barInSantaBarbaraPosition);
        debugMessage("Bandit went to SB BAR");
    }

    /**
     * Method description
     *
     */
    public void startChasingPlayer() {
        synchronized (this.latch) {
            assert(!(this.predefinedAnimationFinished)) : "illegal state: chase already in progress";

            if (null == this.chaseStartTime) {
                this.chaseStartTime = new CoreTime();
            }

            this.predefinedAnimationFinished = true;
        }

        this.playerCarBoundingShpere = new Sphere(0.0D, 0.0D, 0.0D, this.distanceToKill);
        this.previousBanditsPosition = this.banditsCar.gPosition();

        Chase chase = new Chase();

        chase.setParameters("easyChasing");
        chase.makechase(this.banditsCar, this.playerCar);
        debugMessage("Bandit started chase player");
    }

    private void stopAnimateDorothy(boolean removeFromCar) {
        if (null != this.dorothyAnimation) {
            this.dorothyAnimation.stop();
            this.dorothyAnimation = null;
        }

        if ((null == dorothy) || (!(removeFromCar))) {
            return;
        }

        dorothy.abondoneCar(this.playerCar);
        dorothy = null;
    }

    private void blowPlayersCar() {
        drvscripts.BlowScene(Crew.getIgrok(), Crew.getIgrokCar());
        stopAnimateDorothy(false);
        stopChasingPlayer();

        if (null == this.shootAnimation) {
            return;
        }

        this.shootAnimation.finish();
        this.shootAnimation = null;
    }

    private void successRescued() {
        this.shootAnimation.finish();
        this.shootAnimation = null;
        stopAnimateDorothy(true);
        deleteBandits();
        EventsControllerHelper.messageEventHappened("Dorothy chase finished");
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static aiplayer getDorothy() {
        return dorothy;
    }

    /**
     * Method description
     *
     *
     * @param dorothy
     */
    public static void setDorothy(aiplayer dorothy) {
        dorothy = dorothy;
    }

    /**
     * Method description
     *
     */
    public static void createDorothyPassanger() {
        dorothy = aiplayer.getSimpleAiplayer("SC_DOROTHYLOW");
        dorothy.bePassangerOfCar(Crew.getIgrokCar());
    }

    /**
     * Method description
     *
     */
    public static void stopDorothyPassanger() {
        dorothy.abondoneCar(Crew.getIgrokCar());
        dorothy = null;
    }

    /**
     * Method description
     *
     */
    @Override
    public void finilizeDeserialization() {
        this.shootAnimation = new ResqueDorothyShootAnimate(this, this.banditsCar, true);
        this.scheduleChasingStart = true;
        start();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getId() {
        return "chase00090";
    }

    /**
     * Method description
     *
     */
    @Override
    public void aimed_hard() {}

    /**
     * Method description
     *
     */
    @Override
    public void aimed() {}

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean proceedShooting() {
        return true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean shootMade() {
        this.shootSeries.animate(System.currentTimeMillis() / 1000.0D);

        return ((this.needShooting) && (this.shootSeries.isShooting()));
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
}


//~ Formatted in DD Std on 13/08/26
