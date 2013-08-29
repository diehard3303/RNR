/*
 * @(#)sc01370.java   13/08/26
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

import rnr.src.menu.menues;
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.semitrailer;
import rnr.src.rnrcore.Helper;
import rnr.src.rnrcore.INativeMessageEvent;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.CursedHiWay;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.stage;
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
public final class sc01370 extends stage {

    /** Field description */
    public static String MESSAGE = "Blow";

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc01370(Object monitor) {
        super(monitor, "sc01370");
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();

        semitrailer semi = Crew.getIgrokCar().querryTrailer();

        if (semi != null) {
            Crew.getIgrokCar().deattach(semi);
        }

        Crew.getIgrokCar().registerCar("ourcar");

        vectorJ pos = eng.getControlPointPosition("Cursed Highway Scene");
        Data data = new Data();

        data.P = pos;

        trackscripts trs = new trackscripts(getSyncMonitor());

        Helper.addNativeEventListener(new Blow(MESSAGE, Crew.getIgrokCar().getCar()));
        eng.unlock();
        trs.PlaySceneXMLThreaded("01370", false, data);
        eng.lock();
        CursedHiWay.finishCursedHiWay();
        menues.gameoverMenu();
        eng.unlock();
        eng.lock();
        Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Blow implements INativeMessageEvent {
        static final long serialVersionUID = 0L;
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


    static class Data {
        vectorJ P;
        matrixJ M;

        Data() {
            this.M = new matrixJ();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
