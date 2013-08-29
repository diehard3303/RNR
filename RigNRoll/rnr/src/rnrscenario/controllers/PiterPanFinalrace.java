/*
 * @(#)PiterPanFinalrace.java   13/08/28
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

import rnr.src.players.Chase;
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.vehicle;
import rnr.src.rnrcore.ObjectXmlSerializable;
import rnr.src.rnrcore.PiterPanFinalRaceObjectXmlSerializable;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.Ithreadprocedure;
import rnr.src.rnrscenario.ThreadTask;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
@ScenarioClass(scenarioStage = 7)
public class PiterPanFinalrace extends RACEspace implements ScenarioController {
    private static final String Piter_Pan_final_race_win = "Piter Pan final race win";
    private static final String Piter_Pan_final_race_loose = "Piter Pan final race loose";
    private static final String Piter_Pan_final_race_call = "Piter Pan final race call";
    private boolean resetPiterPenAiParameters = true;
    private boolean raceFinished = false;
    private final PiterPanRaceHelper myHelper =
        new PiterPanRaceHelper(PiterPanFinalrace.class.getAnnotation(ScenarioClass.class).scenarioStage());
    private String raceName;
    private final ScenarioHost host;
    private final ObjectXmlSerializable serializator;

    /**
     * Constructs ...
     *
     *
     * @param racename
     * @param finished
     * @param scenarioHost
     *
     * @throws IllegalStateException
     */
    public PiterPanFinalrace(String racename, boolean finished, ScenarioHost scenarioHost)
            throws IllegalStateException {
        super(racename, 3, PiterPanFinalrace.class.getAnnotation(ScenarioClass.class).scenarioStage());
        assert(null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.raceName = racename;
        this.host = scenarioHost;

        if (finished) {
            throw new IllegalStateException("race saved after finish");
        }

        finish_onvip(true);
        this.failDetector = new RaceFailCondition();
        placeallOnStart();
        start();
        this.serializator = new PiterPanFinalRaceObjectXmlSerializable(this, this.host);
        this.serializator.registerObjectXmlSerializable();
        this.host.registerController(this);
    }

    /**
     * Constructs ...
     *
     *
     * @param racename
     * @param tip
     * @param scenarioHost
     */
    public PiterPanFinalrace(String racename, int tip, ScenarioHost scenarioHost) {
        super(racename, tip, PiterPanFinalrace.class.getAnnotation(ScenarioClass.class).scenarioStage());
        assert(null != scenarioHost) : "'scenarioHost' must be non-null reference";
        ThreadTask.create(new MakeRaceInitialization());
        this.host = scenarioHost;
        this.host.registerController(this);
        this.raceName = racename;
        this.serializator = new PiterPanFinalRaceObjectXmlSerializable(this, this.host);
        this.serializator.registerObjectXmlSerializable();
    }

    private void unregisterFromHosts() {
        this.serializator.unRegisterObjectXmlSerializable();
        this.host.unregisterController(this);
        finishImmediately();
    }

    /**
     * Method description
     *
     */
    @Override
    public void gameDeinitLaunched() {
        unregisterFromHosts();
        clearResources();
    }

    /**
     * Method description
     *
     */
    @Override
    public void finish_race_end_scene() {
        super.finish_race_end_scene();

        Ithreadprocedure sceneThread = new ÑhageCarsBack(this.m_isBadEnd);

        ThreadTask.create(sceneThread);
    }

    @Override
    protected void finishRace() {
        if (this.raceFinished) {
            return;
        }

        eng.unblockSO(1024);

        if (0 == (this.states.getStatesucceded() & 0x1000)) {
            placeallOnFinish();
        }

        if ((1 == this.states.getState(Crew.getIgrokCar()).getPlace())
                && (0 == (this.states.getStatesucceded() & 0x1000))) {
            EventsControllerHelper.messageEventHappened("Piter Pan final race win");
        } else if ((this.states.getStatesucceded() & 0x1000) != 0) {
            finish_race_end_scene();
            clearResources();
            EventsControllerHelper.messageEventHappened("Piter Pan final race call");
        } else {
            placeallOnFinish();
            EventsControllerHelper.messageEventHappened("Piter Pan final race loose");
        }

        unregisterFromHosts();
        this.raceFinished = true;
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        super.run();

        if (this.raceFinished) {
            finish();
        } else {
            if ((!(this.resetPiterPenAiParameters)) || (!(makeBanditsChasingPlayer()))) {
                return;
            }

            this.resetPiterPenAiParameters = false;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isRaceFinished() {
        return this.raceFinished;
    }

    /**
     * Method description
     *
     *
     * @param raceFinished
     */
    public void setRaceFinished(boolean raceFinished) {
        this.raceFinished = raceFinished;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getRaceName() {
        return this.raceName;
    }

    /**
     * Method description
     *
     *
     * @param raceName
     */
    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    class MakeRaceInitialization implements Ithreadprocedure {

        /**
         * Method description
         *
         */
        @Override
        public void call() {
            eng.lock();

            actorveh banditsCar = PiterPanFinalrace.this.myHelper.createPiterPanCarAndPassangers(
                                      PiterPanFinalrace.this.conditions.getStartPosition());

            PiterPanFinalrace.this.addParticipant(banditsCar);

            actorveh player = Crew.getIgrokCar();

            player.deleteSemitrailerIfExists();

            matrixJ remM = player.gMatrix();
            vectorJ remV = player.gPosition();
            vehicle gepard = vehicle.create("GEPARD", 0);

            gepard.setLeased(true);

            vehicle playerLastCar = player.querryCurrentCar();

            vehicle.changeLiveVehicle(player, gepard, remM, remV);
            eng.unlock();
            rnrscenario.tech.Helper.waitVehicleChanged();
            eng.lock();
            rnrscenario.Helper.placeLiveCarInGarage(playerLastCar);
            PiterPanFinalrace.this.addParticipant(player);
            PiterPanFinalrace.this.finish_onvip(true);
            PiterPanFinalrace.this.failDetector = new RaceFailCondition();
            PiterPanFinalrace.this.placeallOnStart();
            PiterPanFinalrace.this.start();

            Chase ch = new Chase();

            ch.paramMadracing();
            ch.be_ahead(banditsCar, Crew.getIgrokCar());
            PiterPanFinalrace.access$102(PiterPanFinalrace.this, false);
            banditsCar.sVeclocity(10.0D);
            banditsCar.showOnMap(true);
            eng.unlock();
        }

        /**
         * Method description
         *
         *
         * @param safe
         */
        @Override
        public void take(ThreadTask safe) {}
    }
}


//~ Formatted in DD Std on 13/08/28
