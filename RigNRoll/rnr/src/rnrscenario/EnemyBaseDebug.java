/*
 * @(#)EnemyBaseDebug.java   13/08/28
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

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.vehicle;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.controllers.EnemyBase;
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class EnemyBaseDebug {

    /**
     * Method description
     *
     */
    public static void assault() {
        EventsControllerHelper.messageEventHappened("start_1600");
        Helper.start_quest("enemybase_blowcar");
        Helper.start_quest("2015_enemybase");
        Helper.phase_quest("2015_enemybase", 6);

        vectorJ pos = EnemyBase.getPOINT_LINEUP();

        for (int i = 0; i < EnemyBase.CAR_NAMES_ASSAULT.length; ++i) {
            pos.x -= 4.0D;

            if (i == 0) {
                vectorJ ipos = rnr.src.rnrscr.Helper.getCurrentPosition();

                ipos.x += 100.0D;
                ipos.y += 100.0D;
                EnemyBase.getInstance().cars_assault[i] = eng.CreateCarForScenario(EnemyBase.CAR_NAMES_ASSAULT[i],
                        new matrixJ(), ipos);

                vectorJ pos_1 = EnemyBase.getInstance().cars_assault[i].gPosition();
                matrixJ mat_1 = EnemyBase.getInstance().cars_assault[i].gMatrix();
                vehicle gepard = EnemyBase.getInstance().cars_assault[i].takeoff_currentcar();

                gepard.setLeased(true);

                actorveh ourcar = Crew.getIgrokCar();
                vehicle last_vehicle = ourcar.querryCurrentCar();

                vehicle.changeLiveVehicle(ourcar, gepard, mat_1, pos_1);
                Helper.placeLiveCarInGarage(last_vehicle);
            } else {
                EnemyBase.getInstance().cars_assault[i] = eng.CreateCarForScenario(EnemyBase.CAR_NAMES_ASSAULT[i],
                        new matrixJ(), pos);

                if (i == 2) {
                    EnemyBase.getInstance().cars_assault[i].registerCar("DAKOTA");
                } else if (i == 1) {
                    EnemyBase.getInstance().cars_assault[i].registerCar("JOHN");
                }
            }
        }

        EnemyBase.getInstance().cars_assault[0].deactivate();
        eng.setdword("DWORD_EnemyBaseAssaultTeam", 0);
        eng.setdword("DWORD_EnemyBaseAssault", 1);
        eng.setdword("DWORD_EnemyBase", 1);
    }
}


//~ Formatted in DD Std on 13/08/28
