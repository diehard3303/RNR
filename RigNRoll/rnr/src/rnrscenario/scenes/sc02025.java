/*
 * @(#)sc02025.java   13/08/28
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
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.EnemyBase;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.parkingplace;
import rnr.src.rnrscr.trackscripts;

/**
 * The Class sc02025.
 */
@ScenarioClass(
    scenarioStage = 14,
    fieldWithDesiredStage = ""
)
public final class sc02025 extends stage {

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc02025(Object monitor) {
        super(monitor, "sc02025");
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
        vectorJ pos = EnemyBase.getPOINT_TRUCKSTOP();
        parkingplace place = parkingplace.findParkingByName("pk_BR_MD_01", pos);

        car.makeParking(place, 5);
        eng.SwitchDriver_outside_cabin(car.getCar());

        vectorJ shift = new vectorJ(car.gDir());

        shift.mult(10.0D);

        vectorJ newPos = shift.oPlusN(car.gPosition());
        data _data = new Object() {
            public vectorJ P;
            public matrixJ M;
        };

        eng.unlock();

        trackscripts trs = new trackscripts(getSyncMonitor());

        trs.PlaySceneXMLThreaded("02025", false, _data);
        rnr.src.rnrscenario.tech.Helper.makeComeInAndLeaveParkingFast();
        eng.lock();
        eng.enableControl();
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.waitSimpleState();

        if (null != EnemyBase.getInstance()) {
            EnemyBase.getInstance().met_assault_team();
        }

        eng.lock();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}


//~ Formatted in DD Std on 13/08/28
