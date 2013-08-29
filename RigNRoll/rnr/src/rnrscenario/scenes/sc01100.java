/*
 * @(#)sc01100.java   13/08/26
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
import rnr.src.players.semitrailer;
import rnr.src.players.vehicle;
import rnr.src.rnrcore.Collide;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.BigRaceScenario;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscenario.tech.SleepOnTime;
import rnr.src.rnrscr.parkingplace;
import rnr.src.rnrscr.trackscripts;

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
public final class sc01100 extends stage {
    private static boolean m_debugScene = false;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc01100(Object monitor) {
        super(monitor, "sc01100");
    }

    /**
     * Method description
     *
     */
    public static void setDebugScene() {
        m_debugScene = true;
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        eng.startMangedFadeAnimation();
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.waitLostSemitrailer(Crew.getIgrokCar());
        eng.lock();

        actorveh car = Crew.getIgrokCar();
        vectorJ P_car = car.gPosition();

        car.makeParking(parkingplace.findParkingByName("John_House_Parking", P_car), 1);
        eng.unlock();
        new SleepOnTime(1000);
        eng.lock();

        matrixJ M_car = car.gMatrix();

        P_car = car.gPosition();

        semitrailer trailer = semitrailer.create("model_Gepard_Trailer", M_car, P_car);

        car.attach(trailer);
        eng.unlock();
        eng.lock();

        matrixJ M = car.gMatrix();
        vectorJ carpos = car.gPosition();
        vectorJ shift = new vectorJ(M.v0);
        vectorJ shiftdir = new vectorJ(M.v1);

        shift.mult(-7.0D);
        shiftdir.mult(-7.0D);
        carpos.oPlus(shift);
        carpos.oPlus(shiftdir);

        vectorJ pos1 = carpos.oPlusN(new vectorJ(0.0D, 0.0D, 100.0D));
        vectorJ pos2 = carpos.oPlusN(new vectorJ(0.0D, 0.0D, -100.0D));
        vectorJ P = Collide.collidePoint(pos1, pos2);

        if (P == null) {
            P = car.gPositionSaddle();
        }

        eng.unlock();
        new trackscripts(getSyncMonitor()).PlaySceneXMLThreaded("01100", false, new Object() {
            public matrixJ M;
            public vectorJ P;
            public matrixJ getM() {
                return this.M;
            }
            public vectorJ getP() {
                return this.P;
            }
        });
        eng.lock();
        car.leaveParking();
        eng.enableControl();
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.waitSimpleState();

        if (!(m_debugScene)) {
            eng.lock();
            trailer.delete();

            vehicle carlast = car.querryCurrentCar();

            P_car.oPlus(new vectorJ(0.0D, -100.0D, 0.0D));

            vehicle gepard = vehicle.create("GEPARD", 0);

            gepard.setLeased(true);
            vehicle.changeLiveVehicle(car, gepard, M_car, P_car);
            eng.unlock();
            rnr.src.rnrscenario.tech.Helper.waitVehicleChanged();
            eng.lock();
            car.UpdateCar();
            eng.SwitchDriver_in_cabin(car.getCar());
            rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
            BigRaceScenario.teleport(carlast);
            eng.unlock();
        } else {
            m_debugScene = false;
            eng.lock();
            trailer.delete();
            eng.SwitchDriver_in_cabin(car.getCar());
            rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
            eng.unlock();
        }

        eng.lock();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}


//~ Formatted in DD Std on 13/08/26
