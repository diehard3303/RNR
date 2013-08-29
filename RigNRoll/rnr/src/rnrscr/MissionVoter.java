/*
 * @(#)MissionVoter.java   13/08/28
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


package rnr.src.rnrscr;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.ScriptRef;
import rnr.src.rnrcore.anm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrscenario.missions.MissionManager;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public class MissionVoter implements anm {
    private static final String[] SCENE_NAME = { "voter_man", "voter_woman" };
    private static final String[] SCENE__CALM_NAME = { "voter_man_calm", "voter_woman_calm" };
    private static final String[] ACTOR_NAME = { "XXXMan", "XXXWoman" };
    private static final String[][] CLIP_NAMES = {
        { "SC_voter_man_01_Clip", "SC_voter_man_02_Clip", "SC_voter_man_03_Clip" },
        { "SC_voter_woman_01_Clip", "SC_voter_woman_02_Clip", "SC_voter_woman_03_Clip" }
    };
    private static final int MAN = 0;
    private static final int WOMAN = 1;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int CALM = 0;
    private static final double left_angle = -59.0D;
    private static final double right_angle = 53.0D;
    private static final double time_mix = 1.0D;
    private static final double DISTANCE = 100.0D;
    private static final double DISTANCE_IMMOBILE = 2.0D;
    private static final double TIME_IMMOBILE = 3.0D;
    private static final double TIME_MIN_CALM = 5.0D;
    private static final double TIME_MIN_ACTIVE = 5.0D;
    private static final double TIME_MAX_ACTIVE = 30.0D;
    private static HashMap<String, MissionVoter> anmObjects = new HashMap<String, MissionVoter>();
    private final ScriptRef uid = new ScriptRef();
    private int modelIndex = 0;
    private double left_coef = 0.0D;
    private double right_coef = 1.0D;
    private double calm_coef = 0.0D;
    private double calm_state_time = 0.0D;
    private boolean calm_state = false;
    private boolean finished = false;
    private vectorJ scenePos = null;
    private matrixJ sceneMat = null;
    private vectorJ lastPos = null;
    private vectorJ lastPosImmobile = null;
    private vectorJ currentPos = null;
    private double countUnmoved = 0.0D;
    private boolean immobilised = false;
    private double count_calmstate_time = 0.0D;
    private double count_active_time = 0.0D;
    private boolean is_calm = true;
    private boolean need_voting = false;
    private boolean not_active = false;
    private boolean first_calm = true;
    private SCRuniperson person = null;
    private long scene;
    private long scene_calm;

    MissionVoter() {}

    MissionVoter(long scene, long scene_calm, int modelIndex, matrixJ M, vectorJ P, SCRuniperson person) {
        this.scene = scene;
        this.modelIndex = modelIndex;
        this.scene_calm = scene_calm;
        this.sceneMat = M;
        this.scenePos = P;
        this.person = person;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void setUid(int value) {
        this.uid.setUid(value);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getUid() {
        return this.uid.getUid(this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void removeRef() {
        this.uid.removeRef(this);
    }

    /**
     * Method description
     *
     *
     * @param p
     */
    @Override
    public void updateNative(int p) {}

    /**
     * Method description
     *
     *
     * @param id
     * @param person
     * @param M
     * @param P
     */
    public static void start(String id, SCRuniperson person, matrixJ M, vectorJ P) {
        int index = defineModelIndex(person);
        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();

        pool.add(new SceneActorsPool("man", person));

        long scene = scenetrack.CreateSceneXMLPool(SCENE_NAME[index], 517, pool, new preset(M, P));

        if (scene == 0L) {
            MissionsLogger.getInstance().doLog("MissionVoter scene == NULL" + SCENE_NAME[index], Level.INFO);
        }

        long scene_calm = scenetrack.CreateSceneXMLPool(SCENE__CALM_NAME[index], 517, pool, new preset(M, P));

        if (scene_calm == 0L) {
            MissionsLogger.getInstance().doLog("MissionVoter scene_calm == NULL" + SCENE__CALM_NAME[index], Level.INFO);
        }

        MissionVoter obj = new MissionVoter(scene, scene_calm, index, M, P, person);

        eng.CreateInfinitScriptAnimation(obj);
        anmObjects.put(id, obj);
    }

    /**
     * Method description
     *
     *
     * @param id
     */
    public static void stop(String id) {
        if (anmObjects.containsKey(id)) {
            anmObjects.get(id).finish();
            anmObjects.remove(id);
        }
    }

    /**
     * Method description
     *
     *
     * @param id
     */
    public static void freeVoting(String id) {
        if (anmObjects.containsKey(id)) {
            anmObjects.get(id).voting(false);
        }
    }

    /**
     * Method description
     *
     *
     * @param id
     */
    public static void resumeVoting(String id) {
        if (anmObjects.containsKey(id)) {
            anmObjects.get(id).voting(true);
        }
    }

    private static int defineModelIndex(SCRuniperson person) {
        return ((eng.GetManPrefix(person.nativePointer).contains("Woman"))
                ? 1
                : 0);
    }

    /**
     * Method description
     *
     */
    public void finish() {
        this.finished = true;
        scenetrack.DeleteScene(this.scene);
        scenetrack.DeleteScene(this.scene_calm);
    }

    /**
     * Method description
     *
     *
     * @param to_vote
     */
    public void voting(boolean to_vote) {
        if (!(to_vote)) {
            scenetrack.StopScene(this.scene);
            scenetrack.StopScene(this.scene_calm);
            this.person.stop();
        } else {
            scenetrack.RunScene(this.scene);
            scenetrack.RunScene(this.scene_calm);
            this.person.play();
            this.not_active = true;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean real_voting() {
        return ((inActivateRadius()) && (inActivateSemiSphere()) && (this.need_voting)
                && (MissionSystemInitializer.getMissionsManager().getMissionSystemEnable()));
    }

    /**
     * Method description
     *
     *
     * @param id
     * @param value
     */
    public static void setVoting(String id, boolean value) {
        if (anmObjects.containsKey(id)) {
            MissionVoter missionVoter = anmObjects.get(id);

            missionVoter.need_voting = value;
        }
    }

    /**
     * Method description
     *
     *
     * @param t
     *
     * @return
     */
    @Override
    public boolean animaterun(double t) {
        if (this.finished) {
            return true;
        }

        update();
        countImmobile(t);
        calCoefs();

        if (this.is_calm) {
            if ((t - this.count_calmstate_time > 5.0D) && (real_voting()) && (!(this.immobilised))) {
                this.is_calm = false;
                this.count_active_time = t;
            }
        } else if (t - this.count_active_time > 5.0D) {
            if (real_voting()) {
                if ((this.immobilised) || (t - this.count_active_time > 30.0D)) {
                    this.is_calm = true;
                    this.count_calmstate_time = t;
                }
            } else {
                this.is_calm = true;
                this.count_calmstate_time = t;
            }
        }

        if (this.not_active) {
            this.is_calm = true;
        }

        if (!(this.is_calm)) {
            if (this.calm_state) {
                this.calm_state_time = t;
            }

            this.calm_state = false;

            double coef_calm_mix = (t - this.calm_state_time > 1.0D)
                                   ? 0.0D
                                   : (1.0D - t + this.calm_state_time) / 1.0D;

            this.left_coef *= (1.0D - coef_calm_mix);
            this.right_coef *= (1.0D - coef_calm_mix);
            this.calm_coef = coef_calm_mix;
            this.first_calm = false;
        } else {
            if (!(this.calm_state)) {
                this.calm_state_time = t;
            }

            this.calm_state = true;

            double tmx = (this.first_calm)
                         ? 0.0D
                         : 1.0D;
            double coef_calm_mix = (t - this.calm_state_time >= tmx)
                                   ? 1.0D
                                   : (t - this.calm_state_time) / 1.0D;

            this.left_coef *= (1.0D - coef_calm_mix);
            this.right_coef *= (1.0D - coef_calm_mix);
            this.calm_coef = coef_calm_mix;
        }

        scenetrack.ChangeClipParam(this.scene, ACTOR_NAME[this.modelIndex], CLIP_NAMES[this.modelIndex][1], this,
                                   "setLeftCoef");
        scenetrack.ChangeClipParam(this.scene, ACTOR_NAME[this.modelIndex], CLIP_NAMES[this.modelIndex][2], this,
                                   "setRightCoef");
        scenetrack.ChangeClipParam(this.scene_calm, ACTOR_NAME[this.modelIndex], CLIP_NAMES[this.modelIndex][0], this,
                                   "setCalmCoef");

        return false;
    }

    /**
     * Method description
     *
     *
     * @param pars
     */
    public void setLeftCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.left_coef;
    }

    /**
     * Method description
     *
     *
     * @param pars
     */
    public void setRightCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.right_coef;
    }

    /**
     * Method description
     *
     *
     * @param pars
     */
    public void setCalmCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.calm_coef;
    }

    private void update() {
        this.lastPos = this.currentPos;
        this.currentPos = Helper.getCurrentPosition();
    }

    private void countImmobile(double t) {
        if ((null == this.lastPos) || (this.currentPos == null)) {
            return;
        }

        if (null == this.lastPosImmobile) {
            this.lastPosImmobile = this.lastPos;
        }

        if (t - this.countUnmoved > 3.0D) {
            if (this.currentPos.len2(this.lastPosImmobile) > 4.0D) {
                this.lastPosImmobile = this.currentPos;
                this.countUnmoved = t;
                this.immobilised = false;
            } else {
                this.immobilised = true;
            }
        }
    }

    private void calCoefs() {
        if ((null == this.currentPos) || (null == this.scenePos)) {
            return;
        }

        vectorJ R = vectorJ.oMinus(this.currentPos, this.scenePos);
        double cos_x = vectorJ.dot(R.normN(), this.sceneMat.v0.normN());
        double angle = Math.acos(cos_x);

        if ((angle > 0.0D) && (angle < 3.141592653589793D)) {
            if (angle > 2.600540585471551D) {
                this.left_coef = 1.0D;
                this.right_coef = (1.0D - this.left_coef);
            } else if (angle < 0.6457718232379019D) {
                this.right_coef = 1.0D;
                this.left_coef = (1.0D - this.right_coef);
            } else {
                this.left_coef = ((180.0D * angle / 3.141592653589793D - 90.0D + 53.0D) / 112.0D);
                this.right_coef = (1.0D - this.left_coef);
            }
        }
    }

    private boolean inActivateRadius() {
        return ((null != this.currentPos) && (this.currentPos.len2(this.scenePos) < 10000.0D));
    }

    private boolean inActivateSemiSphere() {
        return ((null != this.currentPos)
                && (this.sceneMat.v1.dot(vectorJ.oMinus(this.currentPos, this.scenePos)) > 0.0D));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public IXMLSerializable getXmlSerializator() {
        return null;
    }

    static class preset {

        /** Field description */
        public matrixJ M;

        /** Field description */
        public vectorJ P;

        preset(matrixJ M, vectorJ P) {
            this.P = P;
            this.M = M;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
