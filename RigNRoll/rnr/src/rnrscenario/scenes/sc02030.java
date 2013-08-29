/*
 * @(#)sc02030.java   13/08/28
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
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.EnemyBase;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.trackscripts;

//~--- JDK imports ------------------------------------------------------------

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
public final class sc02030 extends stage {

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc02030(Object monitor) {
        super(monitor, "sc02030");
    }

    /**
     * Method description
     *
     *
     * @param automat
     */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.doWide(true);
        eng.disableControl();

        actorveh car = Crew.getIgrokCar();

        car.sVeclocity(0.0D);

        vectorJ pos_Igrokcar = new vectorJ(3856.2060299999998D, 13122.95883D, 317.61900000000003D);
        matrixJ matr_Igrokcar = matrixJ.Mz(0.7874925584998415D);

        car.sPosition(pos_Igrokcar, matr_Igrokcar);

        vectorJ pos = eng.getControlPointPosition("EnemyBaseAssaultRoot");
        Vector v = new Vector();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");

        v.add(new SceneActorsPool("baza", person));

        Data _data = new Data(new matrixJ(), pos);
        trackscripts trs = new trackscripts(getSyncMonitor());
        long fadescene = trs.createSceneXML("justfadecutscene", null, _data);
        long scene1 = trs.createSceneXML("02030", v, _data);
        long scene2 = trs.createSceneXML("02040a_part1", v, _data);

        sc02040.prepareScene(getSyncMonitor());
        eng.unlock();
        trs.PlaySceneXMLThreaded(fadescene, true);
        trs.PlaySceneXMLThreaded(scene1, true);
        trs.PlaySceneXMLThreaded(scene2, false);
        eng.lock();

        if (EnemyBase.getInstance() != null) {
            EnemyBase.getInstance().assault_begun();
        }

        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        eng.doWide(false);
        eng.unlock();
        trs.PlaySceneXMLThreaded("02031", false);

        if (stage.isInterrupted()) {
            return;
        }

        eng.lock();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
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
}


//~ Formatted in DD Std on 13/08/28
