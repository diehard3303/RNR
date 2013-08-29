/*
 * @(#)sc02040.java   13/08/28
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

import rnr.src.players.CarName;
import rnr.src.players.Crew;
import rnr.src.players.CutSceneAuxPersonCreator;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.EnemyBase;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.rnrscr.specobjects;
import rnr.src.rnrscr.trackscripts;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

//TODO: Auto-generated Javadoc

/**
 * Class description.
 *
 * @version 1.0, 13/08/28
 * @author TJ
 */
@ScenarioClass(
    scenarioStage = 14,
    fieldWithDesiredStage = ""
)
public final class sc02040 extends stage {

    /** The scene. */
    private static long scene = 0L;

    /**
     *     Constructs ...
     *    
     *     @param monitor
     *                the monitor
     */
    public sc02040(Object monitor) {
        super(monitor, "sc02040");
    }

    /**
     *     Method description.
     *    
     *     @param monitor
     *                the monitor
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void prepareScene(Object monitor) {
        vectorJ pos = eng.getControlPointPosition("EnemyBaseAssaultRoot");
        Vector v = new Vector();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");

        v.add(new SceneActorsPool("baza", person));

        Data _data = new Data(new matrixJ(), pos);
        trackscripts trs = new trackscripts(monitor);

        scene = trs.createSceneXML("02040a_part3", v, _data);
    }

    /**
     *     Method description.
     */
    public static void removeScene() {
        if (0L == scene) {
            return;
        }

        scenetrack.DeleteScene(scene);
        scene = 0L;
    }

    /*
     *  (non-Javadoc)
     * @see rnr.src.rnrscenario.stage#playSceneBody(rnr.src.rnrscenario.cScriptFuncs)
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();

        Vector v = new Vector();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");

        v.add(new SceneActorsPool("baza", person));

        aiplayer dakota_for_scene = new aiplayer("SC_ONTANIELOLOW");

        dakota_for_scene.sPoolBased("dakota_for_2040");
        dakota_for_scene.setModelCreator(new CutSceneAuxPersonCreator(), "dakota_for_2040");

        SCRuniperson dakota_person = dakota_for_scene.getModel();

        dakota_person.lockPerson();
        v.add(new SceneActorsPool("dakota", dakota_person));

        cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject("KeyPoint_2040");
        Data _data1 = new Data(new matrixJ(), new vectorJ(3926.0D, 13128.0D, 317.0D));

        eng.unlock();

        trackscripts trs = new trackscripts(getSyncMonitor());

        trs.PlaySceneXMLThreaded(scene, true);
        eng.lock();

        actorveh police1 = eng.CreateCarForScenario(CarName.CAR_POLICE, new matrixJ(), _data1.P);

        police1.registerCar("police1");

        actorveh police2 = eng.CreateCarForScenario(CarName.CAR_POLICE, new matrixJ(), _data1.P);

        police2.registerCar("police2");
        eng.unlock();
        trs.PlaySceneXMLThreaded("02040", false, v, _data1);
        eng.lock();
        dakota_person.unlockPerson();
        police1.deactivate();
        police2.deactivate();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.SwitchDriver_in_cabin(Crew.getIgrokCar().getCar());
        eng.enableControl();

        if (null != EnemyBase.getInstance()) {
            EnemyBase.getInstance().finish_Enemy_base();
        }

        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    /**
     *     The Class Data.
     */
    static class Data {

        /** The m. */
        matrixJ M;

        /** The p. */
        vectorJ P;

        /**
         *         Instantiates a new data.
         */
        Data() {}

        /**
         *         Instantiates a new data.
         *        
         *         @param M
         *                    the m
         *         @param P
         *                    the p
         */
        Data(matrixJ M, vectorJ P) {
            this.M = M;
            this.P = P;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
