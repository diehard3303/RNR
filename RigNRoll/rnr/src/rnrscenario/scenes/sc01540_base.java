/*
 * @(#)sc01540_base.java   13/08/26
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

import rnr.src.menu.JavaEvents;
import rnr.src.players.CarName;
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.rnrcore.INativeMessageEvent;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.traffic;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptEvents.EventsControllerHelper;

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
    scenarioStage = 13,
    fieldWithDesiredStage = ""
)
public class sc01540_base extends stage {
    private static final String MESSAGE = "1540 finished";
    private static final String POSITION = "Red Rock Canyon Scene";
    private static final String[] MESSAGE_EVENTS = { "Blow1", "Blow2" };
    private actorveh mathewCar = null;
    private final String sceneName;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     * @param scenename
     */
    public sc01540_base(Object monitor, String scenename) {
        super(monitor, "sc" + scenename);
        this.sceneName = scenename;
    }

    private void createCar() {
        matrixJ M = new matrixJ();
        vectorJ pos = rnr.src.rnrscr.Helper.getCurrentPosition();

        pos.oPlus(new vectorJ(0.0D, 300.0D, 0.0D));
        this.mathewCar = eng.CreateCarForScenario(CarName.CAR_MATHEW_DEAD, M, pos);
        Crew.addMappedCar("MAT", this.mathewCar);
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();

        trackscripts trs = new trackscripts(getSyncMonitor());

        createCar();
        eng.disableControl();
        traffic.enterCutSceneModeImmediately();

        actorveh playerCar = Crew.getIgrokCar();

        playerCar.registerCar("ourcar");
        this.mathewCar.registerCar("matcar");

        params data = new params();

        data.M = new matrixJ();
        data.M.v0.mult(-1.0D);
        data.M.v1.mult(-1.0D);
        data.P = eng.getControlPointPosition("Red Rock Canyon Scene");

        if (null == data.P) {
            data.P = new vectorJ();
        }

        for (String MESSAGE_EVENT : MESSAGE_EVENTS) {
            rnr.src.rnrcore.Helper.addNativeEventListener(new Blow(MESSAGE_EVENT, this.mathewCar.getCar()));
        }

        rnr.src.rnrcore.Helper.addNativeEventListener(new Repair("RepairMatCar"));
        eng.unlock();
        trs.PlaySceneXMLThreaded(this.sceneName, false, data);
        eng.lock();

        Vector alllignedcars = new Vector();

        alllignedcars.add(playerCar);
        actorveh.aligncars(alllignedcars, playerCar.gPosition(), 1.0D, 1.0D, 1, 1);
        this.mathewCar.deactivate();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        traffic.setTrafficMode(0);
        EventsControllerHelper.messageEventHappened("1540 finished");
        eng.unlock();
    }

    private static final class Blow implements INativeMessageEvent {
        private final String message;
        private final int car;

        Blow(String message, int car) {
            this.message = message;
            this.car = car;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String getMessage() {
            return this.message;
        }

        /**
         * Method description
         *
         *
         * @param message
         */
        @Override
        public void onEvent(String message) {
            eng.BlowCar(this.car);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public boolean removeOnEvent() {
            return true;
        }
    }


    private final class Repair implements INativeMessageEvent {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private final String message;

        Repair(String paramString) {
            this.message = paramString;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String getMessage() {
            return this.message;
        }

        /**
         * Method description
         *
         *
         * @param message
         */
        @Override
        public void onEvent(String message) {
            JavaEvents.SendEvent(43, 0, sc01540_base.this.mathewCar);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public boolean removeOnEvent() {
            return true;
        }
    }


    static class params {
        matrixJ M;
        vectorJ P;
    }
}


//~ Formatted in DD Std on 13/08/26
