/*
 * @(#)sc00009.java   13/08/26
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
import rnr.src.players.vehicle;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.traffic;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.parkingplace;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 0,
    fieldWithDesiredStage = ""
)
public final class sc00009 extends stage {
    private static final String SCENES_PROCEED = "Begining Scene phase1";
    private static final String SKIP_SCENE = "Skip intro";
    private static final double ROOM_SIZE = 500.0D;
    private static final String SCENE_POINT = "intro_scene";

    /** Field description */
    public static final vectorJ officePos = new vectorJ(4831.0D, -24135.0D, 0.0D);
    private final vectorJ START_POSITION = new vectorJ(4131.0D, -24059.0D, 0.0D);
    private actorveh playersCar = null;
    private vehicle carlast = null;
    private vehicle carnew = null;
    vectorJ positionLastCar = null;
    matrixJ matrixLastCar = null;
    private final matrixJ M = new matrixJ();

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc00009(Object monitor) {
        super(monitor, "sc00009");
    }

    private void makecar() {
        eng.lock();
        eng.disableControl();
        this.playersCar = Crew.getIgrokCar();

        boolean toMakeParking = true;

        if (500.0D > this.playersCar.gPosition().oMinusN(officePos).length()) {
            this.playersCar.makeParking(parkingplace.findParkingByName("OxnardParking04", officePos), 0);
        } else {
            toMakeParking = false;
        }

        eng.unlock();

        if (toMakeParking) {
            rnr.src.rnrscenario.tech.Helper.waitParked(this.playersCar);
        }

        eng.lock();
        traffic.enterCutSceneModeImmediately();
        this.positionLastCar = this.playersCar.gPosition();
        this.matrixLastCar = this.playersCar.gMatrix();
        Crew.getIgrok().abondoneCar(this.playersCar);

        actorveh car = eng.CreateCarForScenario(CarName.CAR_MATHEW, new matrixJ(), this.START_POSITION);

        this.carnew = car.takeoff_currentcar();
        this.carlast = this.playersCar.querryCurrentCar();
        this.M.v0 = new vectorJ(0.0D, -1.0D, 0.0D);
        this.M.v1 = new vectorJ(1.0D, 0.0D, 0.0D);
        vehicle.changeLiveVehicle(this.playersCar, this.carnew, this.M, this.START_POSITION);
        car.deactivate();
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.waitVehicleChanged();
        eng.lock();
        Crew.getIgrokCar().UpdateCar();
        Crew.getIgrokCar().registerCar("matcar");

        aiplayer mathew = aiplayer.getScenarioAiplayer("SC_MATTHEW");

        mathew.beDriverOfCar(this.playersCar);
        Crew.getIgrok().bePassangerOfCar(this.playersCar);
        this.playersCar.switchOffEngine();
        eng.unlock();
    }

    private void afterScene() {
        eng.lock();
        traffic.setTrafficMode(0);
        Crew.getIgrok().abondoneCar(this.playersCar);

        aiplayer mathew = aiplayer.getScenarioAiplayer("SC_MATTHEW");

        mathew.abondoneCar(this.playersCar);
        vehicle.changeLiveVehicle(this.playersCar, this.carlast, this.matrixLastCar, this.positionLastCar);
        Crew.getIgrok().beDriverOfCar(this.playersCar);
        EventsControllerHelper.messageEventHappened("Begining Scene phase1");
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.waitVehicleChanged();
        eng.lock();
        this.carnew.delete();
        eng.unlock();
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();

        if (eng.introPlayingFirstTime()) {
            eng.switchOffIntro();
        }

        eng.unlock();

        if (bad_conditions()) {
            eng.lock();
            this.playersCar = Crew.getIgrokCar();

            if (this.playersCar.gPosition().oMinusN(officePos).length() < 500.0D) {
                this.playersCar.makeParking(parkingplace.findParkingByName("OxnardParking04", officePos), 0);
            }

            EventsControllerHelper.messageEventHappened("Skip intro");
            this.playersCar.leaveParking();
            rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
            eng.unlock();

            return;
        }

        eng.startMangedFadeAnimation();
        eng.lock();
        traffic.enterCutSceneModeImmediately();
        eng.unlock();
        makecar();

        trackscripts trs = new trackscripts(getSyncMonitor());
        presets data = new presets();

        data.car = this.playersCar;
        data.P = eng.getControlPointPosition("intro_scene");
        trs.PlaySceneXMLThreaded("00009", false, data);
        eng.startMangedFadeAnimation();
        afterScene();
        eng.lock();
        traffic.setTrafficMode(0);
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
        eng.ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = false;
    }

    private boolean bad_conditions() {
        this.playersCar = Crew.getIgrokCar();

        return ((scenarioscript.isAuotestRun()) || (!(scenarioscript.isTutorialOn()))
                || (this.playersCar.gPosition().oMinusN(officePos).length() > 500.0D));
    }

    static class presets {
        actorveh car;
        vectorJ P;
        matrixJ M;

        presets() {
            this.M = new matrixJ();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
