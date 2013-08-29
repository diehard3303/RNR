/*
 * @(#)PiterPanRaceHelper.java   13/08/28
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


package rnr.src.rnrscenario.controllers;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.CarName;
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.Helper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public final class PiterPanRaceHelper {
    @SuppressWarnings("unused")
    private final int myScenarioStage;

    PiterPanRaceHelper(int stage) {
        this.myScenarioStage = stage;
    }

    /**
     * Method description
     *
     *
     * @param raceStartPosition
     *
     * @return
     */
    public actorveh createPiterPanCarAndPassangers(vectorJ raceStartPosition) {
        aiplayer bandit = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");
        aiplayer PiterPan = aiplayer.getSimpleAiplayer("SC_PITERPANLOW");
        actorveh banditsCar = eng.CreateCarForScenario(CarName.CAR_PITER_PAN, new matrixJ(), raceStartPosition);

        Crew.addMappedCar("ARGOSY BANDIT", banditsCar);
        bandit.bePassangerOfCar(banditsCar);
        PiterPan.beDriverOfCar(banditsCar);
        Helper.makePowerEngine(banditsCar);

        return banditsCar;
    }
}


//~ Formatted in DD Std on 13/08/28
