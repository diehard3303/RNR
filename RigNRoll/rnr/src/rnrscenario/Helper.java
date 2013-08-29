/*
 * @(#)Helper.java   13/08/27
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

import rnr.src.players.actorveh;
import rnr.src.players.vehicle;
import rnr.src.scriptActions.SingleStepScenarioAdvanceAction;
import rnr.src.scriptActions.StartScenarioBranchAction;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public class Helper {
    private static final String MC_VEHICLE_CACHE_NAME = "lastcar";

    /**
     * Method description
     *
     *
     * @param quest_name
     */
    public static void start_quest(String quest_name) {
        StartScenarioBranchAction action = new StartScenarioBranchAction(quest_name,
                                               scenarioscript.script.getScenarioMachine());

        action.act();
    }

    /**
     * Method description
     *
     *
     * @param quest_name
     * @param state
     */
    public static void phase_quest(String quest_name, int state) {
        SingleStepScenarioAdvanceAction action = new SingleStepScenarioAdvanceAction(quest_name, state,
                                                     scenarioscript.script.getScenarioMachine());

        action.act();
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public static void makePowerEngine(actorveh car) {
        assert(car != null);
        car.changeEngine("BANDITS_ENGINE");
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public static void placeLiveCarInGarage(vehicle car) {
        vehicle.cacheVehicleWithName(car, "lastcar");
        car.placeInGarage();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static vehicle getLiveCarFromGarage() {
        vehicle lastCar = vehicle.getCacheVehicleByName("lastcar");

        assert(lastCar != null);
        lastCar.placeToWorldFromGarage();

        return lastCar;
    }

	/**
	 * @return the mcVehicleCacheName
	 */
	public static String getMcVehicleCacheName() {
		return MC_VEHICLE_CACHE_NAME;
	}
}


//~ Formatted in DD Std on 13/08/27
