/*
 * @(#)sc00930.java   13/08/26
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
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.eng;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.rnrscr.drvscripts;
import rnr.src.rnrscr.parkingplace;
import rnr.src.rnrscr.specobjects;
import rnr.src.rnrscr.trackscripts;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 10,
    fieldWithDesiredStage = ""
)
public final class sc00930 extends stage {

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc00930(Object monitor) {
        super(monitor, "sc00930");
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        actorveh car = Crew.getIgrokCar();
        drvscripts drv = new drvscripts();

        drv.playOutOffCarThreaded(Crew.getIgrok(), car);

        trackscripts trs = new trackscripts(getSyncMonitor());

        eng.lock();
        eng.disableControl();

        parkingplace pPlace = parkingplace.findParkingByName("Oxnard_Police_Parking", car.gPosition());
        cSpecObjects pPlaceWorld = specobjects.getInstance().GetNearestLoadedParkingPlace();
        actorveh dakotacar = null;
        actorveh cohcar = null;

        if (null != pPlaceWorld) {
            dakotacar = eng.CreateCarForScenario(CarName.CAR_DAKOTA, pPlaceWorld.matrix, pPlaceWorld.position);
            dakotacar.makeParking(pPlace, 2);
            cohcar = eng.CreateCarForScenario(CarName.CAR_COCH, pPlaceWorld.matrix, pPlaceWorld.position);
            cohcar.makeParking(pPlace, 1);
        }

        eng.unlock();
        trs.PlaySceneXMLThreaded("00930", true);
        eng.lock();
        eng.returnCameraToGameWorld();

        if (null != dakotacar) {
            dakotacar.leaveParking();
            dakotacar.autopilotTo(eng.getControlPointPosition("Oxnard_Bar_01"));
        }

        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.waitGameWorldLoad();
        trs.PlaySceneXMLThreaded("00931", false, specobjects.getPolicePresets());
        eng.lock();

        if (null != dakotacar) {
            dakotacar.deactivate();
        }

        if (null != cohcar) {
            cohcar.deactivate();
        }

        aiplayer livePlayer = Crew.getIgrok();
        SCRuniperson person = livePlayer.getModel();

        person.play();
        eng.SwitchDriver_in_cabin(car.getCar());
        car.leaveParking();
        eng.doWide(false);
        eng.enableControl();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}


//~ Formatted in DD Std on 13/08/26
