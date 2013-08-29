/*
 * @(#)Helper.java   13/08/28
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


package rnr.src.rnrscr;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.cameratrack;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.Place;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class Helper {

    /**
     * Method description
     *
     *
     * @param person
     * @param car
     */
    public static void placePersonToCar(SCRuniperson person, actorveh car) {
        car.UpdateCar();

        if (car.getCar() == 0) {
            eng.err("placePersonToCar has unloaded car!");
        }

        person.SetInGameWorld();

        vectorJ poswheel = car.gPositionSteerWheel();
        vectorJ dirwheel = car.gDir();

        person.SetPosition(poswheel);
        person.SetDirection(dirwheel);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static vectorJ getCurrentPosition() {
        if (null == Crew.getIgrokCar()) {
            return null;
        }

        return Crew.getIgrokCar().gPosition();
    }

    /**
     * Method description
     *
     *
     * @param pos
     */
    public static void getNearestGoodPoint(vectorJ pos) {
        if (null == Crew.getIgrokCar()) {
            return;
        }

        Place place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(1);

        if (place == null) {
            place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(0);
        } else {
            pos.Set(place.getCoords());

            return;
        }

        if (place == null) {
            place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(3);
        } else {
            pos.Set(place.getCoords());

            return;
        }

        if (place == null) {
            pos.Set(Crew.getIgrokCar().gPosition());

            vectorJ direction = Crew.getIgrokCar().gDir();

            direction.mult(-20.0D);
            pos.oPlus(direction);
        }
    }

    /**
     * Method description
     *
     */
    public static void restoreCameraToIgrokCar() {
        Crew.getIgrokCar().UpdateCar();
        cameratrack.AttachCameraToCar(Crew.getIgrokCar().getCar());
    }

    /**
     * Method description
     *
     *
     * @param car
     *
     * @return
     */
    public static boolean isCarLive(actorveh car) {
        return (car.getAi_player() == Crew.getIgrokCar().getAi_player());
    }
}


//~ Formatted in DD Std on 13/08/28
