/*
 * @(#)sc01640.java   13/08/28
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
import rnr.src.rnrcore.matrixJ;
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
@ScenarioClass(
    scenarioStage = -2,
    fieldWithDesiredStage = ""
)
public final class sc01640 extends stage {
    private static final String ROOT_SCENE = "KeyPoint_2045";

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc01640(Object monitor) {
        super(monitor, "sc01640");
    }

    /*
     *  (non-Javadoc)
     * @see rnr.src.rnrscenario.stage#playSceneBody(rnr.src.rnrscenario.cScriptFuncs)
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();

        actorveh car = Crew.getIgrokCar();
        aiplayer monica = aiplayer.getScenarioAiplayer("SC_MONICA");

        monica.bePassangerOfCar(car);

        vectorJ pos = eng.getControlPointPosition("EnemyBaseAssaultRoot");
        Vector pool = new Vector();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");

        pool.add(new SceneActorsPool("baza", person));

        Data data = new Data(new matrixJ(), new vectorJ(3618.4951169999999D, 13370.149047999999D, 320.98033099999998D),
                             new matrixJ(), pos, car);
        cSpecObjects sceneroot = specobjects.getInstance().GetLoadedNamedScenarioObject("KeyPoint_2045");

        if (sceneroot != null) {
            data.P = sceneroot.position;
        }

        cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();

        if ((crossroad != null) && (crossroad.name.compareToIgnoreCase("KeyPoint_1640") == 0)) {
            vectorJ check = new vectorJ(3926.0D, 13128.0D, 317.0D);

            if (check.len2(crossroad.position) > 1000000.0D) {
                crossroad.position = check;
            }

            car.sPosition(crossroad.position, crossroad.matrix);
            car.sVeclocity(0.0D);
        }

        trackscripts trs = new trackscripts(getSyncMonitor());

        eng.unlock();
        trs.PlaySceneXMLThreaded("01640", false, pool, data);
        eng.lock();
        monica.abondoneCar(car);

        if (EnemyBase.getInstance() != null) {
            EnemyBase.getInstance().finish_tunnel(false);
        }

        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        eng.unlock();
        eng.lock();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Data {
        matrixJ M;
        vectorJ P;
        matrixJ M1;
        vectorJ P1;
        actorveh car;

        Data(matrixJ M, vectorJ P, matrixJ M1, vectorJ P1, actorveh car) {
            this.M = M;
            this.P = P;
            this.M1 = M1;
            this.P1 = P1;
            this.car = car;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
