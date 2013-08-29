/*
 * @(#)Helper.java   13/08/26
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


package rnr.src.rnrscenario.tech;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.players.semitrailer;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.WorldState;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.teleport.ITeleported;
import rnr.src.rnrcore.teleport.MakeTeleport;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscr.drvscripts;
import rnr.src.rnrscr.parkingplace;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class Helper {

    /**
     * Method description
     *
     */
    public static void waitGameWorldLoad() {
        while (true) {
            eng.lock();

            if (rnr.src.rnrcore.WorldState.checkStateWorld_GameWorld()) {
                eng.unlock();

                return;
            }

            eng.unlock();
            new SleepOnTime(100);
        }
    }

    /**
     * Method description
     *
     */
    public static void waitVehicleChanged() {
        while (true) {
            eng.lock();

            if (WorldState.checkStateWorld_VehicleChanged()) {
                eng.unlock();

                return;
            }

            eng.unlock();
            new SleepOnTime(100);
        }
    }

    /**
     * Method description
     *
     */
    public static void waitSimpleState() {
        while (true) {
            eng.lock();

            if (WorldState.checkStateState("simple")) {
                eng.unlock();

                return;
            }

            eng.unlock();
            new SleepOnTime(100);
        }
    }

    /**
     * Method description
     *
     */
    public static void waitTeleport() {
        Teleport tp = new Teleport();

        MakeTeleport.teleport(tp);

        while (true) {
            eng.lock();

            if (tp.was_teleported) {
                eng.unlock();

                return;
            }

            eng.unlock();
            new SleepOnTime(100);
        }
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public static void waitLostSemitrailer(actorveh car) {
        while (true) {
            eng.lock();

            semitrailer current_semi = car.querryTrailer();

            if (null == current_semi) {
                eng.unlock();

                return;
            }

            eng.unlock();
            new SleepOnTime(100);
        }
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public static void waitParked(actorveh car) {
        while (true) {
            eng.lock();

            boolean value = car.isparked();

            if (value) {
                eng.unlock();

                return;
            }

            eng.unlock();
            new SleepOnTime(100);
        }
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public static void waitLoaded(actorveh car) {
        while (true) {
            eng.lock();
            car.UpdateCar();

            boolean isLoaded = car.getCar() != 0;

            if (isLoaded) {
                eng.unlock();

                return;
            }

            eng.unlock();
            new SleepOnTime(100);
        }
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param num
     */
    public static void makeParkAndComeOut(String name, int num) {
        actorveh liveCar = Crew.getIgrokCar();
        vectorJ currentPlayerPosition = liveCar.gPosition();

        if (currentPlayerPosition != null) {
            parkingplace nearestParkingPlace = parkingplace.findParkingByName(name, currentPlayerPosition);

            if (nearestParkingPlace != null) {
                eng.lock();
                liveCar.makeParkingAnimated(nearestParkingPlace, 4.0D, num);
                eng.unlock();
            } else {
                return;
            }
        } else {
            return;
        }

        waitParked(liveCar);
        makeComeOut();
    }

    /**
     * Method description
     *
     */
    public static void makeComeOut() {
        actorveh liveCar = Crew.getIgrokCar();
        aiplayer livePlayer = Crew.getIgrok();

        drvscripts.helper.playOutOffCarThreaded(livePlayer, liveCar);
    }

    /**
     * Method description
     *
     */
    public static void makeComeInAndLeaveParking() {
        eng.lock();
        eng.returnCameraToGameWorld();
        eng.unlock();
        waitGameWorldLoad();

        aiplayer livePlayer = Crew.getIgrok();
        actorveh liveCar = Crew.getIgrokCar();

        liveCar.UpdateCar();

        if ((null == livePlayer) || (0 == liveCar.getCar())) {
            return;
        }

        SCRuniperson person = livePlayer.getModel();

        person.play();
        drvscripts.helper.playInsideCarThreaded(livePlayer, liveCar);
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.lock();
        liveCar.leaveParking();
        eng.unlock();
    }

    /**
     * Method description
     *
     */
    public static void makeComeInAndLeaveParkingFast() {
        eng.returnCameraToGameWorld();
        waitGameWorldLoad();

        aiplayer livePlayer = Crew.getIgrok();
        actorveh liveCar = Crew.getIgrokCar();

        liveCar.UpdateCar();

        if ((null == livePlayer) || (0 == liveCar.getCar())) {
            return;
        }

        SCRuniperson person = livePlayer.getModel();

        person.play();
        eng.SwitchDriver_in_cabin(liveCar.getCar());
        rnrscr.Helper.restoreCameraToIgrokCar();
        eng.lock();
        liveCar.leaveParking();
        eng.unlock();
    }

    /**
     * Method description
     *
     *
     * @param originalM
     *
     * @return
     */
    public static matrixJ makeMatrixAlignedToZ(matrixJ originalM) {
        matrixJ newM = new matrixJ();

        newM.Set2(0.0D, 0.0D, 1.0D);

        vectorJ newY = new vectorJ(originalM.v1);

        newY.z = 0.0D;
        newY.norm();

        vectorJ newX = newY.oCross(newM.v2);

        newM.Set0(newX.x, newX.y, newX.z);
        newM.Set1(newY.x, newY.y, newY.z);

        return newM;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static class Teleport implements ITeleported {
        boolean was_teleported;

        /**
         * Constructs ...
         *
         */
        public Teleport() {
            this.was_teleported = false;
        }

        /**
         * Method description
         *
         */
        @Override
        public void teleported() {
            this.was_teleported = true;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
