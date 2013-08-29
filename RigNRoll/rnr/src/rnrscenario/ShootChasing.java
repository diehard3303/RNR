/*
 * @(#)ShootChasing.java   13/08/28
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

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.rnrcore.Helper;
import rnr.src.rnrcore.INativeMessageEvent;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrscr.camscripts;
import rnr.src.rnrscr.camscripts.trackclipparams;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public abstract class ShootChasing extends TypicalAnm {
    private static final double ANGLE_MAX = 1.570796326794897D;
    private static final double ANGLE_MIN = -0.453785605518526D;
    private static final double SHOOT_DISTANCE = 100.0D;
    private static final double SHOOT_MIN = 20.0D;
    private static final double SHOOT_INTERVAL = 2.0D;
    private static final String[] METHODS = { "setFrontCoef", "setSideCoef", "setBackCoef" };
    private static final int[] FLAGS = { 512, 512, 516 };
    private static final String[][] CLIPS = {
        { "shoot_begin_front", "shoot_begin_side", "shoot_begin_back" },
        { "shoot_fin_front", "shoot_fin_side", "shoot_fin_back" },
        { "shoot_cyc_front", "shoot_cyc_side", "shoot_cyc_back" }
    };
    private static final int BEGIN = 0;
    private static final int FINISH = 1;
    private static final int CYCLE = 2;
    private static final int ALLTRACKS = 3;
    private static final int ALL_SIDES = 3;
    private String[] ACTOR_NAME = null;
    private String[] TRACKS = null;
    private final long[] scenes = new long[3];
    private final Rotation rotation = new Rotation();
    private final SightActions actions = new SightActions();
    private final Shooting shooting = new Shooting();
    private actorveh car_running = null;
    private actorveh car_chaser = null;
    private IShootChasing chase = null;
    private boolean finished = false;
    private boolean scenesPrepared = false;
    private boolean isFromLoadFlagForPrepareScenes = false;

    /**
     * Constructs ...
     *
     *
     * @param chase
     * @param car_chaser
     * @param car_running
     * @param ACTOR_NAME
     * @param TRACKS
     */
    public ShootChasing(IShootChasing chase, actorveh car_chaser, actorveh car_running, String[] ACTOR_NAME,
                        String[] TRACKS) {
        this.car_running = car_running;
        this.car_chaser = car_chaser;
        this.chase = chase;
        this.ACTOR_NAME = ACTOR_NAME;
        this.TRACKS = TRACKS;
    }

    protected final void init(boolean isFromLoad) {
        this.isFromLoadFlagForPrepareScenes = isFromLoad;
        eng.CreateInfinitScriptAnimation(this);
        createscenes();
        createeventslisteners();
        start_shoot();
    }

    protected abstract void prepareForScenes(boolean paramBoolean);

    protected abstract void prepareForFinish();

    protected abstract SCRuniperson getShoterModel();

    protected abstract SCRuniperson getGunModel();

    private boolean canCreateScenes() {
        int chaserCar = (getCar_chaser() == null)
                        ? 0
                        : getCar_chaser().getCar();

        if (chaserCar == 0) {
            return false;
        }

        int runningCar = (getCar_running() == null)
                         ? 0
                         : getCar_running().getCar();

        return (0 != runningCar);
    }

    private void createscenes() {
        if ((this.scenesPrepared) || (!(canCreateScenes()))) {
            return;
        }

        this.scenesPrepared = true;
        prepareForScenes(this.isFromLoadFlagForPrepareScenes);

        Vector pool = new Vector();

        pool.add(new SceneActorsPool("shooter", getShoterModel()));
        pool.add(new SceneActorsPool("gun", getGunModel()));

        Data data = new Data(Crew.getIgrokCar());

        for (int i = 0; i < 3; ++i) {
            this.scenes[i] = scenetrack.CreateSceneXMLPool(this.TRACKS[i], FLAGS[i], pool, data);

            if (this.scenes[i] == 0L) {
                MissionsLogger.getInstance().doLog("ShootChasing createscenes " + i + this.TRACKS[i], Level.INFO);
            }
        }
    }

    private void createeventslisteners() {
        Helper.addNativeEventListener(new BeginFinished());
        Helper.addNativeEventListener(new FinishFinished());
    }

    private void start_shoot() {
        scenetrack.runFromStart(this.scenes[0]);
    }

    private void cycle_shoot() {
        this.shooting.start_shooting();
        scenetrack.runFromStart(this.scenes[2]);
        scenetrack.UpdateSceneFlags(this.scenes[2], 4);
    }

    private void finish_shoot() {
        this.shooting.end_shooting();
        scenetrack.UpdateSceneFlags(this.scenes[2], 256);
        scenetrack.runFromStart(this.scenes[1]);
    }

    private void turnoff_finish_shoot() {
        scenetrack.UpdateSceneFlags(this.scenes[1], 256);
    }

    /**
     * Method description
     *
     */
    public void finish() {
        prepareForFinish();
        this.finished = true;

        for (long scene : this.scenes) {
            scenetrack.DeleteScene(scene);
        }
    }

    /**
     * Method description
     *
     *
     * @param dt
     *
     * @return
     */
    @Override
    public boolean animaterun(double dt) {
        if (this.finished) {
            return true;
        }

        createscenes();

        if ((!(this.scenesPrepared)) || (eng.gameWorldStopped())) {
            return false;
        }

        updateRotation(dt);

        for (String aACTOR_NAME : this.ACTOR_NAME) {
            for (int i = 0; i < 3; ++i) {
                for (int side = 0; side < 3; ++side) {
                    scenetrack.ChangeClipParam(this.scenes[i], aACTOR_NAME, CLIPS[i][side], this, METHODS[side]);
                }
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param pars
     */
    public void setFrontCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.rotation.front_coef;
    }

    /**
     * Method description
     *
     *
     * @param pars
     */
    public void setSideCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.rotation.side_coef;
    }

    /**
     * Method description
     *
     *
     * @param pars
     */
    public void setBackCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.rotation.back_coef;
    }

    private void updateRotation(double t) {
        Coef coef;

        if ((null == this.car_running) || (null == this.chase)) {
            coef = new Coef(Math.sin(t), false);
            coef = new Coef(2.0D * coef.coef - 1.0D, false);
        } else {
            coef = animateStraightForward(this.car_running);
        }

        if ((null != this.chase) && (!(this.chase.proceedShooting()))) {
            coef = new Coef(-2.0D, false);
        }

        if (this.rotation.setCoef(coef.coef)) {
            this.actions.setOnSight(true);
            this.shooting.shoot(t, coef);
        } else {
            this.actions.setOnSight(false);
        }
    }

    @Deprecated
    private Coef animateFromCar(actorveh car) {
        vectorJ pos0 = this.car_chaser.gPosition();
        matrixJ m0 = this.car_chaser.gMatrix();
        vectorJ pos1 = car.gPosition();
        vectorJ n_direction = vectorJ.oMinus(pos1, pos0);
        double len_2 = pos1.len2(pos0);
        boolean useChaseOnShootDetection = (this.chase != null) && (this.chase.useShootDetection());

        if ((useChaseOnShootDetection) && (null != this.chase) && (!(this.chase.shootMade()))) {
            return new Coef(-2.0D, false);
        }

        if ((!(useChaseOnShootDetection)) && (len_2 > 10000.0D)) {
            return new Coef(-2.0D, false);
        }

        n_direction.norm();

        double angle_sign = vectorJ.dot(n_direction, m0.v1);
        double cos_alfa = vectorJ.dot(n_direction, m0.v0);
        double coef;

        if (angle_sign > 0.0D) {
            double angle = Math.acos(cos_alfa);

            coef = angle / 1.570796326794897D;
        } else {
            double angle = -Math.acos(cos_alfa);

            coef = -angle / -0.453785605518526D;
        }

        return new Coef(coef, len_2 <= 400.0D);
    }

    private Coef animateStraightForward(actorveh car) {
        vectorJ pos0 = this.car_chaser.gPosition();
        vectorJ pos1 = car.gPosition();
        double len_2 = pos1.len2(pos0);
        boolean useChaseOnShootDetection = (this.chase != null) && (this.chase.useShootDetection());

        if ((useChaseOnShootDetection) && (null != this.chase) && (!(this.chase.shootMade()))) {
            return new Coef(-2.0D, false);
        }

        if ((!(useChaseOnShootDetection)) && (len_2 > 10000.0D)) {
            return new Coef(-2.0D, false);
        }

        return new Coef(1.0D, len_2 <= 400.0D);
    }

    protected final actorveh getCar_chaser() {
        return this.car_chaser;
    }

    protected final actorveh getCar_running() {
        return this.car_running;
    }

    private final class BeginFinished implements INativeMessageEvent {

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String getMessage() {
            return "2070shootbeginfinish";
        }

        /**
         * Method description
         *
         *
         * @param message
         */
        @Override
        public void onEvent(String message) {
            if (ShootChasing.this.finished) {
                return;
            }

            ShootChasing.this.actions.start_shoot_finished();
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public boolean removeOnEvent() {
            return false;
        }
    }


    static class Coef {
        double coef;
        boolean critical;

        Coef(double coef, boolean critical) {
            this.coef = coef;
            this.critical = critical;
        }
    }


    static final class Data {
        actorveh car;

        Data() {}

        Data(actorveh car) {
            this.car = car;
        }
    }


    final class FinishFinished implements INativeMessageEvent {

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String getMessage() {
            return "2070shootfinishfinish";
        }

        /**
         * Method description
         *
         *
         * @param message
         */
        @Override
        public void onEvent(String message) {
            ShootChasing.this.actions.finish_shoot_finished();
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public boolean removeOnEvent() {
            return false;
        }
    }


    static final class Rotation {
        double front_coef;
        double side_coef;
        double back_coef;

        Rotation() {
            this.front_coef = 1.0D;
            this.side_coef = 0.0D;
            this.back_coef = 0.0D;
        }

        /**
         * Method description
         *
         *
         * @param value
         *
         * @return
         */
        public boolean setCoef(double value) {
            if ((value >= 0.0D) && (value <= 1.0D)) {
                this.front_coef = value;
                this.side_coef = (1.0D - value);
                this.back_coef = 0.0D;

                return true;
            }

            if ((value < 0.0D) && (value > -1.0D)) {
                this.front_coef = 0.0D;
                this.side_coef = (1.0D + value);
                this.back_coef = (-value);

                return true;
            }

            return false;
        }
    }


    class Shooting {
        private double t_last;
        private double t_start;
        private boolean f_shooting;

        Shooting() {
            this.t_last = 0.0D;
            this.t_start = 0.0D;
            this.f_shooting = false;
        }

        void start_shooting() {
            this.f_shooting = true;
            this.t_start = this.t_last;
        }

        void end_shooting() {
            this.f_shooting = false;
        }

        void shoot(double t, ShootChasing.Coef coef) {
            this.t_last = t;

            if (!(this.f_shooting)) {
                return;
            }

            if (t - this.t_start > 2.0D) {
                this.t_start = this.t_last;

                if (null != ShootChasing.this.chase) {
                    ShootChasing.this.chase.aimed();
                }
            }

            if ((null == ShootChasing.this.chase) || ((!(coef.critical)) && (coef.coef >= 0.2D))) {
                return;
            }

            ShootChasing.this.chase.aimed_hard();
        }
    }


    class SightActions {
        boolean on_sigth;
        boolean idle_state;

        SightActions() {
            this.on_sigth = false;
            this.idle_state = true;
        }

        void setOnSight(boolean value) {
            if (value) {
                if ((!(this.on_sigth)) && (this.idle_state)) {
                    this.idle_state = false;
                    ShootChasing.this.start_shoot();
                }
            } else if (this.on_sigth) {
                stopShooting();
            }

            this.on_sigth = value;
        }

        void stopShooting() {
            ShootChasing.this.finish_shoot();
        }

        void start_shoot_finished() {
            if (this.on_sigth) {
                ShootChasing.this.cycle_shoot();
            } else {
                ShootChasing.this.finish_shoot();
            }
        }

        void finish_shoot_finished() {
            if (this.on_sigth) {
                ShootChasing.this.start_shoot();
            } else {
                ShootChasing.this.turnoff_finish_shoot();
                this.idle_state = true;
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/28
