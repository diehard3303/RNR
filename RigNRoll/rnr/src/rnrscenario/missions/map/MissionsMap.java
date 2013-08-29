/*
 * @(#)MissionsMap.java   13/08/28
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

import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrscenario.missions.infochannels.CbvChannel;
import rnr.src.rnrscenario.missions.map.MissionsMap.cMapEventListener;
import rnr.src.rnrscenario.sctask;
import rnr.src.rnrscr.Helper;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class MissionsMap extends sctask implements AbstractMissionsMap {
    private static final int UPDATE_FREQUENCY = 3;
    private static final int PLACES_CAPACITY = 512;
    private static double LOADING_DISTANCE = 1000000.0D;
    private static double UNLOADING_DISTANCE = 4000000.0D;
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final Map<String, Place> places = new LinkedHashMap(512);
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final List<cMapEventListener> listeners = new LinkedList();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final List<MapEventsListener> listenersToDelete = new LinkedList();
    private boolean is_running = false;

    /**
     * Constructs ...
     *
     */
    public MissionsMap() {
        super(3, false);
        super.start();
    }

    /**
     * Method description
     *
     *
     * @param pointname
     * @param pos
     *
     * @return
     */
    @Override
    public boolean onLoadingDistance(String pointname, vectorJ pos) {
        Place place = getPlace(pointname);

        if (place == null) {
            MissionsLogger.getInstance().doLog("onLoadingDistance cannot find point named " + pointname, Level.SEVERE);

            return false;
        }

        return (place.distance2(pos) < LOADING_DISTANCE);
    }

    /**
     * Method description
     *
     *
     * @param pointname
     * @param pos
     *
     * @return
     */
    @Override
    public boolean afterUnloadingDistance(String pointname, vectorJ pos) {
        Place place = getPlace(pointname);

        if (place == null) {
            MissionsLogger.getInstance().doLog("afterUnloadingDistance cannot find point named " + pointname,
                                               Level.SEVERE);

            return false;
        }

        return (place.distance2(pos) > UNLOADING_DISTANCE);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public Place getPlace(String name) {
        if (null == name) {
            return null;
        }

        return (this.places.get(name));
    }

    /**
     * Method description
     *
     *
     * @param maplistener
     * @param distance
     */
    @Override
    public void addListener(MapEventsListener maplistener, double distance) {
        if (null == maplistener) {
            return;
        }

        String point_name = maplistener.getPointName();

        if (point_name != null) {
            this.listeners.add(new cMapEventListener(this.places.get(point_name), maplistener, distance));
        }
    }

    /**
     * Method description
     *
     *
     * @param maplistener
     */
    @Override
    public void removeListener(MapEventsListener maplistener) {
        if (null == maplistener) {
            return;
        }

        if (this.listenersToDelete.contains(maplistener)) {
            return;
        }

        this.listenersToDelete.add(maplistener);

        if (!(this.is_running)) {
            for (MapEventsListener listener : this.listenersToDelete) {
                Iterator<cMapEventListener> iter = this.listeners.iterator();

                while (iter.hasNext()) {
                    cMapEventListener lst = iter.next();

                    if (lst.listener.equals(listener)) {
                        lst.listener.placeAreaEntered();
                        iter.remove();
                    }
                }
            }

            this.listenersToDelete.clear();
        }
    }

    /**
     * Method description
     *
     *
     * @param target
     */
    @Override
    public void addPlace(Place target) {
        if (null == target) {
            return;
        }

        this.places.put(target.getName(), target);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Place getNearestPlace() {
        if (this.places.isEmpty()) {
            return null;
        }

        Place nearestFound = this.places.values().iterator().next();
        vectorJ pos = Helper.getCurrentPosition();
        double distance = nearestFound.distance(pos);

        for (Map.Entry place : this.places.entrySet()) {
            double newDistance = ((Place) place.getValue()).distance(pos);

            if (newDistance < distance) {
                nearestFound = (Place) place.getValue();
                distance = newDistance;
            }
        }

        return nearestFound;
    }

    /**
     * Method description
     *
     *
     * @param type
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Place getNearestPlaceByType(int type) {
        if (this.places.isEmpty()) {
            return null;
        }

        Place nearestFound = this.places.values().iterator().next();
        vectorJ pos = Helper.getCurrentPosition();
        double distance = nearestFound.distance(pos);

        for (Map.Entry place : this.places.entrySet()) {
            if (((Place) place.getValue()).getTip() == type) {
                double newDistance = ((Place) place.getValue()).distance(pos);

                if (newDistance < distance) {
                    nearestFound = (Place) place.getValue();
                    distance = newDistance;
                }
            }
        }

        return nearestFound;
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param not_place
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Place getNearestPlaceByType(int type, Place not_place) {
        if (this.places.isEmpty()) {
            return null;
        }

        Place nearestFound = this.places.values().iterator().next();
        vectorJ pos = Helper.getCurrentPosition();
        double distance = nearestFound.distance(pos);

        for (Map.Entry place : this.places.entrySet()) {
            if (((Place) place.getValue()).getTip() == type) {
                double newDistance = ((Place) place.getValue()).distance(pos);

                if ((newDistance < distance) && (!(not_place.equals(place.getValue())))) {
                    nearestFound = (Place) place.getValue();
                    distance = newDistance;
                }
            }
        }

        return nearestFound;
    }

    /**
     * Method description
     *
     *
     * @param listener
     * @param distance
     *
     * @return
     */
    @Override
    public boolean onPlace(MapEventsListener listener, double distance) {
        vectorJ position = Helper.getCurrentPosition();
        String point_name = listener.getPointName();

        if (point_name != null) {
            vectorJ pos = this.places.get(point_name).getCoords();

            return (Math.abs(pos.x - position.x) + Math.abs(pos.y - position.y) < distance);
        }

        return false;
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        vectorJ position = Helper.getCurrentPosition();

        if (null == position) {
            return;
        }

        this.is_running = true;

        for (cMapEventListener lst : this.listeners) {
            vectorJ pos = lst.place.getCoords();

            if (Math.abs(pos.x - position.x) + Math.abs(pos.y - position.y) < lst.distance) {
                if ((!(lst.listener instanceof CbvChannel))
                        || (MissionEventsMaker.freeSlotForMission(lst.listener.getMissionName()))) {
                    ;
                }

                lst.listener.placeAreaEntered();
            }
        }

        for (MapEventsListener listener : this.listenersToDelete) {
            Iterator<cMapEventListener> iter = this.listeners.iterator();

            while (iter.hasNext()) {
                cMapEventListener lst = iter.next();

                if (lst.listener.equals(listener)) {
                    iter.remove();
                }
            }
        }

        this.listenersToDelete.clear();
        this.is_running = false;
    }

    /**
     * Method description
     *
     */
    public void deinit() {
        this.places.clear();
        this.listeners.clear();
        this.listenersToDelete.clear();
        this.is_running = false;
        finishImmediately();
    }

    static class cMapEventListener {
        Place place;
        MapEventsListener listener;
        double distance;

        cMapEventListener(Place place, MapEventsListener listener, double distance) {
            this.place = place;
            this.listener = listener;
            this.distance = distance;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
