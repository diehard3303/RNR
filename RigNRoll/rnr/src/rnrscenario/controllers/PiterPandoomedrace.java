/*
 * @(#)PiterPandoomedrace.java   13/08/28
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
import rnr.src.rnrcore.PiterPanDoomedRaceObjectXmlSerializable;
import rnr.src.rnrcore.cameratrack;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscr.Office;
import rnr.src.scriptEvents.EventsControllerHelper;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class PiterPandoomedrace extends RACEspace implements ScenarioController {
    private static final String Piter_Pan_doomed_race_call = "Piter Pan doomed race call";
    private static final String Piter_Pan_doomed_race_finished = "Piter Pan doomed race finished";
    private static final String Piter_Pan_doomed_race_win = "Piter Pan doomed race win";

    /** Field description */
    public static final String PLAYER_LOOSE_CAR_EVENT = "Player loose car";

    /** Field description */
    public static final String PLAYER_LOOSE_CAR_METHOD = "playerLooseCar";
    private static final double banditCarInitialVelocity = 5.0D;
    private boolean resetPiterPenAiParameters = true;
    private boolean raceFinished = false;
    private final PiterPanRaceHelper myHelper = new PiterPanRaceHelper(
                                                    ((ScenarioClass) PiterPandoomedrace.class.getAnnotation(
                                                        ScenarioClass.class)).scenarioStage());
    private ObjectXmlSerializable serializator = null;
    private String comment;
    private String raceName;
    private final ScenarioHost host;

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
    public PiterPandoomedrace(String racename, boolean finished, ScenarioHost scenarioHost)
            throws IllegalStateException {
        super(racename, 3,
              ((ScenarioClass) PiterPandoomedrace.class.getAnnotation(ScenarioClass.class)).scenarioStage());
        assert(null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.host = scenarioHost;
        this.host.registerController(this);

        if (finished) {
            throw new IllegalStateException(
                "Illegal data was saved because of errors in ObjectXmlSerializator with map");
        }

        this.raceFinished = finished;
        this.raceName = racename;
        finish_onvip(true);
        this.failDetector = new RaceFailCondition();
        placeallOnStart();
        start();
        this.serializator = new PiterPanDoomedRaceObjectXmlSerializable(this, this.host);
        EventsControllerHelper.getInstance().addMessageListener(this, "playerLooseCar", "Player loose car");
        this.serializator.registerObjectXmlSerializable();
    }

    /**
     * Constructs ...
     *
     *
     * @param racename
     * @param type
     * @param scenarioHost
     */
    public PiterPandoomedrace(String racename, int type, ScenarioHost scenarioHost) {
        super(racename, type,
              ((ScenarioClass) PiterPandoomedrace.class.getAnnotation(ScenarioClass.class)).scenarioStage());
        assert(null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.raceName = racename;
        this.host = scenarioHost;
        this.host.registerController(this);

        actorveh banditsCar = this.myHelper.createPiterPanCarAndPassangers(this.conditions.getStartPosition());
        actorveh ourCar = Crew.getIgrokCar();

        addParticipant(banditsCar);
        addParticipant(ourCar);
        finish_onvip(true);
        this.failDetector = new RaceFailCondition();
        placeallOnStart();
        start();

        Chase chase = new Chase();

        chase.paramModerateChasing();
        chase.be_ahead(banditsCar, ourCar);
        this.resetPiterPenAiParameters = false;
        banditsCar.sVeclocity(5.0D);
        banditsCar.showOnMap(true);
        this.serializator = new PiterPanDoomedRaceObjectXmlSerializable(this, this.host);
        this.serializator.registerObjectXmlSerializable();
        EventsControllerHelper.getInstance().addMessageListener(this, "playerLooseCar", "Player loose car");
    }

    /**
     * Method description
     *
     */
    public void playerLooseCar() {
        EventsControllerHelper.getInstance().removeMessageListener(this, "playerLooseCar", "Player loose car");

        for (actorveh car : this.states.getParticipants()) {
            car.leave_target();
        }

        ThreadTask.create(new Ithreadprocedure() {
            public void call() {
                try {
                    eng.waitUntilEngineCanPlayScene();
                    eng.lock();
                    eng.startMangedFadeAnimation();

                    actorveh playerCar = Crew.getIgrokCar();

                    assert(playerCar != null);
                    playerCar.detachSemitrailer();

                    vectorJ pos_car = playerCar.gPosition();

                    assert(pos_car != null);
                    eng.SwitchDriver_in_cabin(playerCar.getCar());
                    Crew.getIgrokCar().UpdateCar();
                    cameratrack.AttachCameraToCar(Crew.getIgrokCar().getCar());

                    vectorJ officePosition = eng.getNamedOfficePosition("office_OV_01");

                    playerCar.teleport(officePosition);
                    eng.unlock();
                    Helper.waitTeleport();
                    eng.predefinedAnimationLaunchedFromJava(false);
                    eng.waitUntilEngineCanPlayScene();
                    eng.lock();
                    playerCar.takeoff_currentcar().delete();
                    Office.teleport();
                    eng.unlock();
                    eng.predefinedAnimationLaunchedFromJava(false);
                } catch (InterruptedException e) {
                    System.err.print(e.getMessage());
                    e.printStackTrace(System.err);
                } finally {
                    eng.predefinedAnimationLaunchedFromJava(false);
                }
            }
            public void take(ThreadTask safe) {}
        });
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
        EventsControllerHelper.getInstance().removeMessageListener(this, "playerLooseCar", "Player loose car");
        unregisterFromHosts();
        clearResources();
    }

    @Override
    protected void finishRace() {
        if (this.raceFinished) {
            return;
        }

        unregisterFromHosts();
        this.raceFinished = true;

        if (this.states.getState(Crew.getIgrokCar()).getPlace() == 1) {
            this.comment = "We won!";
            placeallOnFinish();
            EventsControllerHelper.getInstance().removeMessageListener(this, "playerLooseCar", "Player loose car");
            EventsControllerHelper.messageEventHappened("Piter Pan doomed race win");
        } else {
            this.comment = "We lost...";

            if (0 != (this.states.getStatesucceded() & 0x1000)) {
                clearResources();
                EventsControllerHelper.messageEventHappened("Piter Pan doomed race call");
            } else {
                placeAllOnFinishIfPlayerNotFinished();
                EventsControllerHelper.messageEventHappened("Piter Pan doomed race finished");
            }
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        super.run();

        if (this.raceFinished) {
            eng.pager("Race Finished\n" + this.comment);
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
}


//~ Formatted in DD Std on 13/08/28
