/*
 * @(#)sc02050.java   13/08/28
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
import rnr.src.rnrscenario.controllers.ChaseKoh;
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
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 15,
    fieldWithDesiredStage = ""
)
public final class sc02050 extends stage {
    private static final boolean DEBUG = 0;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc02050(Object monitor) {
        super(monitor, "sc02050");
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();

        actorveh playerCar = Crew.getIgrokCar();

        playerCar.setHandBreak(true);

        actorveh cochCar = Crew.getMappedCar("KOH");

        cochCar.registerCar("madcar");

        Vector v = new Vector();
        SCRuniperson person = SCRuniperson.createLoadedObject("BarIn4");

        v.add(new SceneActorsPool("bar", person));

        Data _data = new Data(new matrixJ(), new vectorJ(), null);
        cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();
        Data _data1 = new Data(new matrixJ(), new vectorJ(), null);

        if ((null != crossroad) && (0 == "KeyPoint_2050".compareToIgnoreCase(crossroad.name))) {
            _data1 = new Data(crossroad.matrix, crossroad.position, null);
        }

        eng.unlock();

        trackscripts trs = new trackscripts(getSyncMonitor());

        trs.PlaySceneXMLThreaded("02050", true, v, _data);
        eng.lock();
        ChaseKoh.demolishBar();
        eng.unlock();
        trs.PlaySceneXMLThreaded("02050_part2", false, null, _data1);
        eng.lock();

        if (ChaseKoh.getInstance() != null) {
            ChaseKoh.getInstance().start_chase();
        }

        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.SwitchDriver_in_cabin(playerCar.getCar());
        playerCar.setHandBreak(false);
        eng.enableControl();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Data {
        matrixJ M;
        vectorJ P;
        actorveh car;

        Data() {}

        Data(matrixJ M, vectorJ P, actorveh car) {
            this.M = M;
            this.P = P;
            this.car = car;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
