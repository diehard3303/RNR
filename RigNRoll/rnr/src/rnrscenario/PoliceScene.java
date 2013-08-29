/*
 * @(#)PoliceScene.java   13/08/28
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

import rnr.src.menu.menues;
import rnr.src.players.actorveh;
import rnr.src.rnrcore.Collide;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.traffic;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscr.Helper;
import rnr.src.rnrscr.SyncMonitors;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptEvents.EventsControllerHelper;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public class PoliceScene {

    /** Field description */
    public static boolean reserved_for_scene = false;

    /** Field description */
    public static String MESSAGE_FOR_SCENE = "police_scene_end";
    static final String light = "PoliceSituation";
    static final String serious = "PoliceSituation2";
    static final String crime = "PoliceSituation2";
    static final String car_scene = "PoliceSituation2";
    private static CPlayPoliceScene scene = null;

    /**
     * Method description
     *
     *
     * @param onSpecObject
     * @param guild
     * @param police
     * @param matrix
     * @param position
     * @param Police_matrix
     * @param Police_position
     * @param Quilt_matrix
     * @param Quilt_position
     */
    public static void playLight(boolean onSpecObject, actorveh guild, actorveh police, matrixJ matrix,
                                 vectorJ position, matrixJ Police_matrix, vectorJ Police_position,
                                 matrixJ Quilt_matrix, vectorJ Quilt_position) {
        police.setLockPlayer(true);
        scene = play(false, guild, police, matrix, position, Police_matrix, Police_position, Quilt_matrix,
                     Quilt_position, false, onSpecObject);
    }

    /**
     * Method description
     *
     *
     * @param onSpecObject
     * @param guild
     * @param police
     * @param matrix
     * @param position
     * @param Police_matrix
     * @param Police_position
     * @param Quilt_matrix
     * @param Quilt_position
     */
    public static void playSerious(boolean onSpecObject, actorveh guild, actorveh police, matrixJ matrix,
                                   vectorJ position, matrixJ Police_matrix, vectorJ Police_position,
                                   matrixJ Quilt_matrix, vectorJ Quilt_position) {
        police.setLockPlayer(true);
        scene = play(true, guild, police, matrix, position, Police_matrix, Police_position, Quilt_matrix,
                     Quilt_position, false, onSpecObject);
    }

    /**
     * Method description
     *
     *
     * @param onSpecObject
     * @param guild
     * @param police
     * @param matrix
     * @param position
     * @param Police_matrix
     * @param Police_position
     * @param Quilt_matrix
     * @param Quilt_position
     */
    public static void playCrime(boolean onSpecObject, actorveh guild, actorveh police, matrixJ matrix,
                                 vectorJ position, matrixJ Police_matrix, vectorJ Police_position,
                                 matrixJ Quilt_matrix, vectorJ Quilt_position) {
        police.setLockPlayer(true);
        scene = play(true, guild, police, matrix, position, Police_matrix, Police_position, Quilt_matrix,
                     Quilt_position, true, onSpecObject);
    }

    /**
     * Method description
     *
     */
    public static void playCreatedLight() {
        playCreated("PoliceSituation");
    }

    /**
     * Method description
     *
     */
    public static void playCreatedSerious() {
        playCreated("PoliceSituation2");
    }

    /**
     * Method description
     *
     */
    public static void playCreatedCrime() {
        playCreated("PoliceSituation2");
    }

    /**
     * Method description
     *
     *
     * @param scenename
     */
    public static void playCreated(String scenename) {
        if (null == scene) {
            eng.err("ERROR. Police scene wrong order of playing. info " + scenename);

            return;
        }

        scene.preset.scenename = scenename;
        ThreadTask.create(scene);
        scene = null;
    }

    private static CPlayPoliceScene play(boolean car_scene, actorveh guild, actorveh police, matrixJ matrix,
            vectorJ position, matrixJ Police_matrix, vectorJ Police_position, matrixJ Quilt_matrix,
            vectorJ Quilt_position, boolean gameover, boolean onSpecObject) {
        CPlayPoliceScene scene = new CPlayPoliceScene(guild, police);

        scene.gameover = gameover;
        scene.onSpecObject = onSpecObject;

        if (car_scene) {
            vectorJ pos1 = police.gPosition();
            vectorJ pos2 = new vectorJ(pos1);

            pos1.z += 10.0D;
            pos2.z -= 10.0D;

            vectorJ scenepos = Collide.collidePoint(pos1, pos2);

            if (scenepos == null) {
                scenepos = pos1;
            }

            scene.preset = new SPoliceScenePreset(police.gMatrix(), scenepos);
            scene.preset.policecar = police;
            scene.preset.M_police = Police_matrix;
            scene.preset.P_police = Police_position;
            scene.preset.M_quilt = Quilt_matrix;
            scene.preset.P_quilt = Quilt_position;
        } else {
            vectorJ pos1 = new vectorJ(position);
            vectorJ pos2 = new vectorJ(pos1);

            pos1.z += 10.0D;
            pos2.z -= 10.0D;
            scene.preset = new SPoliceScenePreset(matrix, position);
            scene.preset.M_police = Police_matrix;
            scene.preset.P_police = Police_position;
            scene.preset.M_quilt = Quilt_matrix;
            scene.preset.P_quilt = Quilt_position;
        }

        return scene;
    }

    static class CPlayPoliceScene implements Ithreadprocedure {
        boolean gameover = false;
        boolean onSpecObject = false;
        ThreadTask safe = null;
        @SuppressWarnings("rawtypes")
        Vector pool = new Vector();
        PoliceScene.SPoliceScenePreset preset;

        /** Field description */
        public volatile actorveh quiltPlayer;

        /** Field description */
        public volatile actorveh policePlayer;

        CPlayPoliceScene(actorveh quiltPlayer, actorveh policePlayer) {
            this.quiltPlayer = quiltPlayer;
            this.policePlayer = policePlayer;
        }

        /**
         * Method description
         *
         *
         * @param safe
         */
        @Override
        public void take(ThreadTask safe) {
            this.safe = safe;
        }

        /**
         * Method description
         *
         */
        @Override
        public void call() {
            if (!(Helper.isCarLive(this.quiltPlayer))) {
                eng.err("WARNING. Trying to judge non live player.");

                return;
            }

            this.quiltPlayer.sVeclocity(0.0D);
            this.policePlayer.sVeclocity(0.0D);
            this.quiltPlayer.setHandBreak(true);
            this.policePlayer.setHandBreak(true);
            eng.disableControl();
            eng.SwitchDriver_outside_cabin(this.quiltPlayer.getCar());
            new trackscripts(SyncMonitors.getScenarioMonitor()).PlaySceneXMLThreaded("justfade", false, this.safe);
            eng.lock();
            this.policePlayer.setLockPlayer(true);

            if (!(this.onSpecObject)) {
                traffic.setTrafficModeTemporary(1);
                traffic.cleanTrafficImmediatelyTemporary(500.0D, this.preset.P);
                eng.pauseGameExceptPredefineAnimation(true);
                this.policePlayer.sPosition(this.preset.P_police, this.preset.M_police);
                this.quiltPlayer.sPosition(this.preset.P_quilt, this.preset.M_quilt);
            }

            eng.unlock();

            long scene = trackscripts.initSceneXML(this.preset.scenename, this.pool, this.preset);

            scenetrack.ReplaceSceneFlags(scene, 9);
            event.eventObject((int) scene, this.safe, "_resum");
            this.safe._susp();
            scenetrack.DeleteScene(scene);
            this.policePlayer.setLockPlayer(false);
            eng.lock();
            Helper.restoreCameraToIgrokCar();

            if (!(this.onSpecObject)) {
                eng.pauseGameExceptPredefineAnimation(false);
                traffic.setTrafficModeTemporary(0);
                traffic.restoreTrafficImmediatelyTemporary();
            }

            if (!(PoliceScene.reserved_for_scene)) {
                eventMenu();
            } else {
                scene();
            }

            eng.unlock();
            eng.SwitchDriver_in_cabin(this.quiltPlayer.getCar());
            eng.enableControl();
            this.policePlayer.releasePedalBrake();
            this.quiltPlayer.setHandBreak(false);
            this.policePlayer.setHandBreak(false);
        }

        /**
         * Method description
         *
         */
        public void eventMenu() {
            if (!(this.gameover)) {
                event.Setevent(9801);
            } else {
                menues.gameoverMenuJail();
            }
        }

        /**
         * Method description
         *
         */
        public void scene() {
            EventsControllerHelper.messageEventHappened(PoliceScene.MESSAGE_FOR_SCENE);
        }
    }


    static class SPoliceScenePreset {
        actorveh policecar = null;
        vectorJ P;
        matrixJ M;
        vectorJ P_police;
        matrixJ M_police;
        vectorJ P_quilt;
        matrixJ M_quilt;
        String scenename;

        SPoliceScenePreset(matrixJ matrix, vectorJ position) {
            this.P = position;
            this.M = matrix;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
