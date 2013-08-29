/*
 * @(#)sc00090.java   13/08/28
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


package rnr.src.rnrscenario.scenes;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrscenario.Ithreadprocedure;
import rnr.src.rnrscenario.ThreadTask;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.animation;
import rnr.src.rnrscr.camscripts;
import rnr.src.rnrscr.camscripts.trackclipparams;
import rnr.src.rnrscr.trackscripts;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

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
public final class sc00090 extends stage {

    /** Field description */
    public static int tipshoot;

    /** Field description */
    public static double coef1;

    /** Field description */
    public static double coef2;
    private ThreadTask proc = null;
    cScriptFuncs automat = null;
    int tochoose = 1;

    /** Field description */
    public volatile double coef1_true;

    /** Field description */
    public volatile double coef2_true;

    /**
     * Constructs ...
     *
     *
     * @param proc
     */
    public sc00090(Ithreadprocedure proc) {
        super(proc, "sc00090");
        this.proc = new ThreadTask(proc);
    }

    /**
     * Method description
     *
     *
     * @param c
     */
    public static void SetCoef(double c) {
        tipshoot = (c > 1.0D)
                   ? 2
                   : 1;
        c = (c > 1.0D)
            ? 1.0D
            : c;
        coef1 = c;
        coef2 = 1.0D - c;
    }

    void SetCoef1(rnr.src.rnrscr.camscripts.trackclipparams pars) {
        pars.Weight = this.coef1_true;
    }

    void SetCoef2(camscripts.trackclipparams pars) {
        pars.Weight = this.coef2_true;
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        this.automat = automat;
        ChaseShooting();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    void ChaseShooting() {
        eng.lock();

        trackscripts trs = new trackscripts(getSyncMonitor());
        int rbd = tipshoot;

        rbd = (rbd == 1)
              ? 1
              : animation.RandomFromNom(4) + 1;
        this.coef1_true = coef1;
        this.coef2_true = coef2;

        Presets data = new Presets();

        data.banditcar = Crew.getMappedCar("ARGOSY BANDIT");

        Vector pool = new Vector();
        aiplayer Bandit = aiplayer.getSimpleAiplayer("SC_BANDITJOELOW");
        aiplayer GUN = aiplayer.getSimpleAiplayer("SC_BANDITGUN");
        SCRuniperson person1 = Bandit.getModel();

        person1.lockPerson();

        SCRuniperson person2 = GUN.getModel();

        person2.lockPerson();
        pool.add(new SceneActorsPool("bandit", Bandit.getModel()));
        pool.add(new SceneActorsPool("gun", GUN.getModel()));
        eng.unlock();

        switch (rbd) {
         case 1 :
             eng.lock();

             long sc1 = trs.PlaySceneXML_nGet("00060shootingbegin_side", false);

             scenetrack.ChangeClipParam(sc1, "BANDIT_JOE", "BANDIT_JOE_pas01_side_shoot_bgn_Clip", this, "SetCoef1");
             scenetrack.ChangeClipParam(sc1, "BANDIT_JOE", "BANDIT_JOE_pas01_back_shoot_bgn_Clip", this, "SetCoef2");
             scenetrack.ChangeClipParam(sc1, "Model_gun", "Model_gun_BANDIT_JOE_side_shoot_bgn_Clip", this, "SetCoef1");
             scenetrack.ChangeClipParam(sc1, "Model_gun", "Model_gun_BANDIT_JOE_back_shoot_bgn_Clip", this, "SetCoef2");
             event.eventObject((int) sc1, this.proc, "_resum");
             eng.unlock();
             this.proc._susp();
             eng.lock();
             scenetrack.DeleteScene(sc1);
             eng.unlock();
             eng.lock();

             long sc2 = trs.PlaySceneXML_nGet("00060shooting_side", false);

             scenetrack.ChangeClipParam(sc2, "BANDIT_JOE", "BANDIT_JOE_pas01_side_shoot_cyc_Clip", this, "SetCoef1");
             scenetrack.ChangeClipParam(sc2, "BANDIT_JOE", "BANDIT_JOE_pas01_back_shoot_cyc_Clip", this, "SetCoef2");
             scenetrack.ChangeClipParam(sc2, "Model_gun", "Model_gun_BANDIT_JOE_side_shoot_cyc_Clip", this, "SetCoef1");
             scenetrack.ChangeClipParam(sc2, "Model_gun", "Model_gun_BANDIT_JOE_back_shoot_cyc_Clip", this, "SetCoef2");
             scenetrack.ChangeClipParam(sc2, "Model_shoot", "Model_shoot_BANDIT_JOE_side_shoot_cyc_Clip", this,
                                        "SetCoef1");
             scenetrack.ChangeClipParam(sc2, "Model_shoot", "Model_shoot_BANDIT_JOE_back_shoot_cyc_Clip", this,
                                        "SetCoef2");
             event.eventObject((int) sc2, this.proc, "_resum");
             eng.unlock();
             this.proc._susp();
             eng.lock();
             scenetrack.DeleteScene(sc2);
             eng.unlock();
             eng.lock();

             long sc3 = trs.PlaySceneXML_nGet("00060shootingend_side", false);

             scenetrack.ChangeClipParam(sc3, "BANDIT_JOE", "BANDIT_JOE_pas01_side_shoot_fin_Clip", this, "SetCoef1");
             scenetrack.ChangeClipParam(sc3, "BANDIT_JOE", "BANDIT_JOE_pas01_back_shoot_fin_Clip", this, "SetCoef2");
             scenetrack.ChangeClipParam(sc3, "Model_gun", "Model_gun_BANDIT_JOE_side_shoot_fin_Clip", this, "SetCoef1");
             scenetrack.ChangeClipParam(sc3, "Model_gun", "Model_gun_BANDIT_JOE_back_shoot_fin_Clip", this, "SetCoef2");
             event.eventObject((int) sc3, this.proc, "_resum");
             eng.unlock();
             this.proc._susp();
             eng.lock();
             scenetrack.DeleteScene(sc3);
             eng.unlock();

             break;

         case 2 :
             trs.PlaySceneXMLThreaded("00060shootingbegin_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shootingend_frnt", false, pool, data, this.proc);

             break;

         case 3 :
             trs.PlaySceneXMLThreaded("00060shootingbegin_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shootingend_frnt", false, pool, data, this.proc);

             break;

         case 4 :
             trs.PlaySceneXMLThreaded("00060shootingbegin_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shootingend_frnt", false, pool, data, this.proc);

             break;

         case 5 :
             trs.PlaySceneXMLThreaded("00060shootingbegin_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
             trs.PlaySceneXMLThreaded("00060shootingend_frnt", false, pool, data, this.proc);
        }

        eng.lock();
        person1.unlockPerson();
        person2.unlockPerson();
        eng.unlock();
    }

    static class Presets {
        actorveh banditcar;
    }
}


//~ Formatted in DD Std on 13/08/28
