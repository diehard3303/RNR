/*
 * @(#)PointsController.java   13/08/28
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


package rnr.src.rnrscenario.missions.map;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.missions.Dumpable;
import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscenario.missions.MissionPlacement;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.PointsController.ReferenceCounter;
import rnr.src.rnrscr.parkingplace;

//~--- JDK imports ------------------------------------------------------------

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class PointsController implements Dumpable {
    static final boolean OLD_PARKING = false;
    private static PointsController ourInstance;

    static {
        ourInstance = new PointsController();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final Map<String, ReferenceCounter> lockedPoints = new HashMap();

    /**
     * Method description
     *
     *
     * @return
     */
    public static PointsController getInstance() {
        return ourInstance;
    }

    /**
     * Method description
     *
     */
    public static void deinitialize() {
        ourInstance = new PointsController();
    }

    /**
     * Method description
     *
     *
     * @param pointsNames
     * @param missionPlacement
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void lockPoints(Collection<String> pointsNames, MissionPlacement missionPlacement) {
        assert(null != pointsNames) : "pointName must be non-null reference";

        for (String pointName : pointsNames) {
            Place place = MissionSystemInitializer.getMissionsMap().getPlace(pointName);

            if (place.getTip() != 0) {
                ReferenceCounter refCounter = this.lockedPoints.get(pointName);

                if (null == refCounter) {
                    this.lockedPoints.put(pointName, new ReferenceCounter());
                } else {
                    refCounter.increment();
                }
            } else {
                ArrayList<Integer> list = missionPlacement.getParkingPP(pointName);

                if (list == null) {
                    ;
                }

                int np = missionPlacement.getParkingN(pointName);
                parkingplace nearestParkingPlace = missionPlacement.getParking(pointName);
                ArrayList listnumber = new ArrayList();

                while (np > 0) {
                    int parkingnumber = Crew.getIgrokCar().lockParkingForMission(nearestParkingPlace);

                    if (parkingnumber <= 0) {
                        break;
                    }

                    listnumber.add(Integer.valueOf(parkingnumber));
                    --np;
                }

                missionPlacement.putParking(pointName, listnumber);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param pointName
     * @param missionPlacement
     */
    public void freePoint(String pointName, MissionPlacement missionPlacement) {
        assert(null != pointName) : "pointName must be non-null reference";

        Place place = MissionSystemInitializer.getMissionsMap().getPlace(pointName);
        parkingplace park;

        if (place.getTip() != 0) {
            ReferenceCounter refCounter = this.lockedPoints.get(pointName);

            if ((null != refCounter) && (!(refCounter.decrement()))) {
                this.lockedPoints.remove(pointName);
                eng.log("PointController. Point with name " + pointName + " is free now.");
            }
        } else {
            park = missionPlacement.getParking(pointName);

            ArrayList<Integer> listnumber = missionPlacement.getParkingPP(pointName);

            for (Integer i : listnumber) {
                Crew.getIgrokCar().freeParkingForMission(park, i.intValue());
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param points
     * @param missionPlacement
     */
    public void freePoints(Iterable<String> points, MissionPlacement missionPlacement) {
        assert(null != points) : "points must be non-null reference";

        for (String point : points) {
            freePoint(point, missionPlacement);
        }
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings("rawtypes")
    public void printLockedPoints() {
        eng.log("printLockedPoints: ");

        Set<Entry<String, ReferenceCounter>> points = this.lockedPoints.entrySet();

        for (Map.Entry entry : points) {
            String mess = "Point :\t" + ((String) entry.getKey()) + " . Refcount :\t"
                          + ((ReferenceCounter) entry.getValue()).referenceCount;

            eng.log("\t" + mess);
        }

        eng.log("END printLockedPoints: ");
    }

    /**
     * Method description
     *
     *
     * @param group
     * @param missionPlacement
     *
     * @return
     */
    public boolean hasGroupFreePoint(Collection<String> group, MissionPlacement missionPlacement) {
        if (group.isEmpty()) {
            return true;
        }

        boolean res = false;

        for (String pointName : group) {
            Place place = MissionSystemInitializer.getMissionsMap().getPlace(pointName);

            if (place.getTip() != 0) {
                if (!(this.lockedPoints.containsKey(pointName))) {
                    return true;
                }
            } else {
                parkingplace nearestParkingPlace = parkingplace.findNearestParking(place.getCoords());

                if (nearestParkingPlace == null) {
                    eng.log(place.getName());
                    eng.log(Double.toString(place.getCoords().x) + Double.toString(place.getCoords().y)
                            + Double.toString(place.getCoords().z));
                }

                int np = missionPlacement.getInfo().getNeedParking(pointName, true);
                int parkingnumber = Crew.getIgrokCar().hasParkingForMission(nearestParkingPlace);

                if (parkingnumber >= np) {
                    missionPlacement.putParking(pointName, nearestParkingPlace);
                    missionPlacement.putParking(pointName, np);
                    res = true;
                }
            }
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param target
     */
    @Override
    public void makeDump(OutputStream target) {
        PrintWriter out = new PrintWriter(target);

        out.println("LOCKED POINTS:");

        for (String lockedPoint : this.lockedPoints.keySet()) {
            out.println(lockedPoint);
        }
    }

    private static final class ReferenceCounter {
        private int referenceCount;

        private ReferenceCounter() {
            this.referenceCount = 1;
        }

        void increment() {
            this.referenceCount += 1;
        }

        boolean decrement() {
            this.referenceCount -= 1;

            return (0 < this.referenceCount);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
