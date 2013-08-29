/*
 * @(#)Crew.java   13/08/26
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


package rnr.src.players;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Crew {
    private static Crew instance = null;
    private CarAnimationController carCreationController = null;
    private ScenarioCarCreationController scenarioCarCreationController = null;
    private LiveCarCreationController liveCarCreationController = null;
    private MappingCars mappingCars = null;

    /**
     * Constructs ...
     *
     */
    public Crew() {
        this.carCreationController = new CarAnimationController();
        this.scenarioCarCreationController = new ScenarioCarCreationController();
        this.liveCarCreationController = new LiveCarCreationController();
        this.mappingCars = new MappingCars();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static Crew getInstance() {
        if (instance == null) {
            setInstance(new Crew());
        }

        return instance;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setInstance(Crew value) {
        instance = value;
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param playerId
     *
     * @return
     */
    public actorveh addcrewman(int type, int playerId) {
        switch (type) {
         default :
             return this.carCreationController.onCarCreate(type, playerId);

         case 10 :
             return this.scenarioCarCreationController.onCarCreate(type, playerId);

         case 1 :
        }

        return this.liveCarCreationController.onCarCreate(type, playerId);
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param playerId
     */
    public void removecrewman(int type, int playerId) {
        switch (type) {
         default :
             this.carCreationController.onCarDelete(type, playerId);

             break;

         case 10 :
             this.scenarioCarCreationController.onCarDelete(type, playerId);

             break;

         case 1 :
             this.liveCarCreationController.onCarDelete(type, playerId);
        }

        if (null == this.mappingCars) {
            return;
        }

        this.mappingCars.removeMappedCar(playerId);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static aiplayer getIgrok() {
        return CrewNamesManager.getMainCharacterPlayer();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static actorveh getIgrokCar() {
        if (null != getInstance().liveCarCreationController) {
            return getInstance().liveCarCreationController.getLiveCar();
        }

        return null;
    }

    /**
     * Method description
     *
     */
    public static void rotateNonLoadedModels() {
        if (null == getInstance().carCreationController) {
            return;
        }

        getInstance().carCreationController.renewState();
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public static actorveh getMappedCar(String name) {
        if (null != getInstance().mappingCars) {
            return getInstance().mappingCars.getMappedCar(name);
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public static void deactivateMappedCar(String name) {
        if ((null == getInstance()) || (null == getInstance().mappingCars)) {
            return;
        }

        actorveh car = getInstance().mappingCars.getMappedCar(name);

        if (null == car) {
            return;
        }

        car.deactivate();
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param car
     */
    public static void addMappedCar(String name, actorveh car) {
        if (null != getInstance().mappingCars) {
            getInstance().mappingCars.addMappedCar(name, car);
        }
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public static void removeMappedCar(String name) {
        if (null != getInstance().mappingCars) {
            getInstance().mappingCars.removeMappedCar(name);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<String> getPoolNames() {
        if (null != this.carCreationController) {
            return this.carCreationController.getPoolNames();
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public CarAnimationController getCarCreationController() {
        return this.carCreationController;
    }

    /**
     * Method description
     *
     *
     * @param carCreationController
     */
    public void setCarCreationController(CarAnimationController carCreationController) {
        this.carCreationController = carCreationController;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public LiveCarCreationController getLiveCarCreationController() {
        return this.liveCarCreationController;
    }

    /**
     * Method description
     *
     *
     * @param liveCarCreationController
     */
    public void setLiveCarCreationController(LiveCarCreationController liveCarCreationController) {
        this.liveCarCreationController = liveCarCreationController;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public MappingCars getMappingCars() {
        return this.mappingCars;
    }

    /**
     * Method description
     *
     *
     * @param mappingCars
     */
    public void setMappingCars(MappingCars mappingCars) {
        this.mappingCars = mappingCars;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ScenarioCarCreationController getScenarioCarCreationController() {
        return this.scenarioCarCreationController;
    }

    /**
     * Method description
     *
     *
     * @param scenarioCarCreationController
     */
    public void setScenarioCarCreationController(ScenarioCarCreationController scenarioCarCreationController) {
        this.scenarioCarCreationController = scenarioCarCreationController;
    }

    /**
     * Method description
     *
     */
    public void deinit() {
        if (null == this.carCreationController) {
            return;
        }

        this.carCreationController.deinit();
    }
}


//~ Formatted in DD Std on 13/08/26
