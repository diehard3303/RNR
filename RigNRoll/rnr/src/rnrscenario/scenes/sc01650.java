/*
 * @(#)sc01650.java   13/08/28
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
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.EnemyBase;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.trackscripts;

//TODO: Auto-generated Javadoc

/**
 * The Class sc01650.
 */
@ScenarioClass(
    scenarioStage = -2,
    fieldWithDesiredStage = ""
)
public final class sc01650 extends stage {

    /**
     *     Instantiates a new sc01650.
     *    
     *     @param monitor
     *                the monitor
     */
    public sc01650(Object monitor) {
        super(monitor, "sc01650");
    }

    /*
     *  (non-Javadoc)
     * @see rnr.src.rnrscenario.stage#playSceneBody(rnr.src.rnrscenario.cScriptFuncs)
     */
    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();

        actorveh car = Crew.getIgrokCar();
        aiplayer monica = aiplayer.getScenarioAiplayer("SC_MONICA");

        monica.bePassangerOfCar(car);
        car.sOnTheRoadLaneAndStop(1);

        Data data = new Data(car.gMatrix(), car.gPosition(), car);

        eng.unlock();

        trackscripts trs = new trackscripts(getSyncMonitor());

        trs.PlaySceneXMLThreaded("01650", false, data);
        eng.lock();
        monica.abondoneCar(car);

        if (EnemyBase.getInstance() != null) {
            EnemyBase.getInstance().finish_tunnel(true);
        }

        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        eng.unlock();
        eng.lock();
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

        /** The car. */
        actorveh car;

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
         *         @param car
         *                    the car
         */
        Data(matrixJ M, vectorJ P, actorveh car) {
            this.M = M;
            this.P = P;
            this.car = car;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
