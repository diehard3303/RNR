/*
 * @(#)BigRaceScenario.java   13/08/28
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
import rnr.src.rnrrating.BigRace;
import rnr.src.rnrrating.IBigRaceEventsListener;
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class BigRaceScenario implements IBigRaceEventsListener {

    /** Field description */
    public static final int DAYS = 5;

    /** Field description */
    public static final String MESSAGE_WIN_RACE = "BigRace Quest Win";

    /** Field description */
    public static final String MESSAGE_LOOSE_RACE = "BigRace Quest Loose";
    private static BigRaceScenario instance = null;
    private int race_uid = -1;

    /**
     * Constructs ...
     *
     */
    public BigRaceScenario() {
        instance = this;
        start();
    }

    private void start() {
        this.race_uid = BigRace.sceduleScenarioRace(5);

        if (this.race_uid == -1) {
            eng.err("ERRORR. Cannot schedule scenario race");

            return;
        }

        BigRace.setSemitailerForRaceForLivePlayer(this.race_uid, "model_Gepard_Trailer", 0);
        BigRace.addListener(this);
    }

    /**
     * Method description
     *
     *
     * @param last_car
     */
    public static void teleport(vehicle last_car) {
        Helper.placeLiveCarInGarage(last_car);

        if (null != instance) {
            BigRace.teleportOnStart(instance.race_uid);
        }
    }

    /**
     * Method description
     *
     */
    public static void finishRace() {
        if (null != instance) {
            actorveh ourcar = Crew.getIgrokCar();
            matrixJ remM = ourcar.gMatrix();
            vectorJ remV = ourcar.gPosition();
            vehicle lastCar = Helper.getLiveCarFromGarage();

            vehicle.changeLiveVehicle(ourcar, lastCar, remM, remV);
        }
    }

    /**
     * Method description
     *
     */
    public static void init() {
        deinit();
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        if (null != instance) {
            BigRace.removeListener(instance);
        }

        instance = null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getUid() {
        return this.race_uid;
    }

    /**
     * Method description
     *
     */
    @Override
    public void raceFailed() {
        EventsControllerHelper.messageEventHappened("BigRace Quest Win");
    }

    /**
     * Method description
     *
     *
     * @param place
     */
    @Override
    public void raceFinished(int place) {
        if (place == 1) {
            EventsControllerHelper.messageEventHappened("BigRace Quest Win");
        } else {
            EventsControllerHelper.messageEventHappened("BigRace Quest Loose");
        }

        BigRace.removeListener(this);
    }

    /**
     * Method description
     *
     *
     * @param is_live
     */
    @Override
    public void raceStarted(boolean is_live) {
        if (!(is_live)) {
            EventsControllerHelper.messageEventHappened("BigRace Quest Loose");
            BigRace.removeListener(this);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
