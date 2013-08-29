/*
 * @(#)KohHelpManage.java   13/08/28
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

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.KohHelpObjectXmlSerializable;
import rnr.src.rnrcore.ObjectXmlSerializable;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.sctask;
import rnr.src.scriptEvents.EventsControllerHelper;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 0,
    fieldWithDesiredStage = ""
)
public class KohHelpManage extends sctask implements ScenarioController {
    private static final String MESSAGE_MAKE_KOH = "makeKohDummy";
    private static final String METH_MAKE_KOH = "setMakeKohDummy";
    private static final String METH_KOH_NOT_ACCTUAL = "setKohWasNotMade";
    private static final double COCH_POINT_ACTIVATION_DISTANCE = 500.0D;
    private static final int COCH_POINT_INDEX = 0;
    private static final int NICK_POINT_INDEX = 1;
    private static final int SCENE_INVALID = -1;
    private static final int SCENE30 = 0;
    private static final int MY_STAGE_NUMBER;
    private static final String[][] MEETING_POINTS;
    private static final CoreTime DAYS_TO_FAILURE_MEETING_WITH_COCH;
    static KohHelpManage instance;

    static {
        MY_STAGE_NUMBER = KohHelpManage.class.getAnnotation(ScenarioClass.class).scenarioStage();
        MEETING_POINTS = new String[][] {
            { "CochKeyPoint_OV_SB", "NikKeyPoint_OV_SB" }, { "CochKeyPoint_OV_LA", "NikKeyPoint_OV_LA" }
        };
        DAYS_TO_FAILURE_MEETING_WITH_COCH = CoreTime.days(1);
        instance = null;
    }

    private List<MeetingPlace> cochMeetingPlaces = null;
    private Location nickLocationOnMeetingPlace = null;
    private KohHelp spawnedController = null;
    private int current_scene = 0;
    private boolean makeKoh = false;
    private boolean wasMadeKoh = false;
    private CoreTime timeKohOrdered = null;
    private ObjectXmlSerializable serializator = null;
    private boolean scheduleRestoreNickLocationOnMeetingPlace = false;
    private final actorveh player—ar;
    private final ScenarioHost host;

    private KohHelpManage(ScenarioHost scenarioHost) {
        super(3, true);
        assert(null != scenarioHost) : "'scenarioHost' must be non-null reference";
        this.host = scenarioHost;
        this.player—ar = Crew.getIgrokCar();
        this.serializator = new KohHelpObjectXmlSerializable(this, this.host);
        this.serializator.registerObjectXmlSerializable();
        this.host.registerController(this);
        EventsControllerHelper.getInstance().addMessageListener(this, "setMakeKohDummy", "makeKohDummy");
        EventsControllerHelper.getInstance().addMessageListener(this, "setKohWasNotMade", "Escape Help Koh");
        EventsControllerHelper.getInstance().addMessageListener(this, "setKohWasNotMade", "Accept Help Koh");
    }

    /**
     * Method description
     *
     *
     * @param host
     */
    public static void constructSingleton(ScenarioHost host) {
        instance = new KohHelpManage(host);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static KohHelpManage getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     */
    @Override
    public void finish() {
        if (this.f_finished) {
            return;
        }

        super.finish();

        if (null != EventsControllerHelper.getInstance()) {
            EventsControllerHelper.getInstance().removeMessageListener(this, "setMakeKohDummy", "makeKohDummy");
            EventsControllerHelper.getInstance().removeMessageListener(this, "setKohWasNotMade", "Escape Help Koh");
            EventsControllerHelper.getInstance().removeMessageListener(this, "setKohWasNotMade", "Accept Help Koh");
        }

        this.host.unregisterController(this);
        this.serializator.unRegisterObjectXmlSerializable();
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        if (null == instance) {
            return;
        }

        if (null != instance.spawnedController) {
            instance.spawnedController.switchOff();
            instance.spawnedController = null;
        }

        instance.finish();
        instance.finishImmediately();
        instance = null;
    }

    static void accept() {
        if (-1 != instance.current_scene) {
            return;
        }

        eng.err("SCENE_INVALID == getInstance().current_scene");
    }

    /**
     * Method description
     *
     */
    @Override
    public void gameDeinitLaunched() {
        deinit();
    }

    /**
     * Method description
     *
     */
    public static void questFinished() {
        if (null == instance) {
            return;
        }

        instance.nullScene();
        deinit();
    }

    /**
     * Method description
     *
     */
    public void setMakeKohDummy() {
        eng.pager("Koh is prepared");
        this.makeKoh = true;
        this.timeKohOrdered = new CoreTime();
    }

    /**
     * Method description
     *
     */
    public void setKohWasNotMade() {
        this.wasMadeKoh = false;
    }

    private void nullScene() {
        this.makeKoh = false;
    }

    /**
     * Method description
     *
     */
    public void debugSetNickLocation() {
        this.nickLocationOnMeetingPlace = new Location(MY_STAGE_NUMBER, Crew.getIgrokCar().gPosition(),
                Crew.getIgrokCar().gMatrix());
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Location getNickLocation() {
        return this.nickLocationOnMeetingPlace;
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        createMeetingLocationsIfNull();

        if (this.scheduleRestoreNickLocationOnMeetingPlace) {
            actorveh cochCar = Crew.getMappedCar("KOH");

            if (null != cochCar) {
                vectorJ cochCarPosition = cochCar.gPosition();

                if ((null != cochCarPosition) && (0.0D < cochCarPosition.length())
                        && (0 < this.cochMeetingPlaces.size())) {
                    this.nickLocationOnMeetingPlace = this.cochMeetingPlaces.get(0).getNickLocation();

                    double smallestSqrDistance = cochCarPosition.len2(this.nickLocationOnMeetingPlace.getPosition());

                    for (int i = 1; i < this.cochMeetingPlaces.size(); ++i) {
                        Location nickPosition = this.cochMeetingPlaces.get(i).getNickLocation();
                        double sqrDistance = cochCarPosition.len2(nickPosition.getPosition());

                        if (sqrDistance >= smallestSqrDistance) {
                            continue;
                        }

                        smallestSqrDistance = sqrDistance;
                        this.nickLocationOnMeetingPlace = nickPosition;
                    }

                    this.scheduleRestoreNickLocationOnMeetingPlace = false;
                }
            }
        }

        if (0 < new CoreTime().moreThanOnTime(this.timeKohOrdered, DAYS_TO_FAILURE_MEETING_WITH_COCH)) {
            this.makeKoh = false;
            EventsControllerHelper.messageEventHappened("Escape Help Koh");
            questFinished();

            return;
        }

        if ((!(this.makeKoh)) || (this.cochMeetingPlaces.isEmpty())) {
            return;
        }

        MeetingPlace nearestMeetingPosition = this.cochMeetingPlaces.get(0);
        vectorJ playerPosition = this.player—ar.gPosition();
        double smallestSqrDistance = playerPosition.len2(nearestMeetingPosition.getCochLocation().getPosition());

        for (int i = 1; i < this.cochMeetingPlaces.size(); ++i) {
            MeetingPlace placementPosition = this.cochMeetingPlaces.get(i);
            double sqrDistance = playerPosition.len2(placementPosition.getCochLocation().getPosition());

            if (sqrDistance >= smallestSqrDistance) {
                continue;
            }

            smallestSqrDistance = sqrDistance;
            nearestMeetingPosition = placementPosition;
        }

        if (smallestSqrDistance >= 250000.0D) {
            return;
        }

        this.spawnedController = KohHelp.prepare(nearestMeetingPosition.getCochLocation());
        this.nickLocationOnMeetingPlace = nearestMeetingPosition.getNickLocation();
        this.makeKoh = false;
        this.wasMadeKoh = true;
    }

    private void createMeetingLocationsIfNull() {
        if (null != this.cochMeetingPlaces) {
            return;
        }

        this.cochMeetingPlaces = new ArrayList(MEETING_POINTS.length);

        for (String[] cochMeetingPoint : MEETING_POINTS) {
            try {
                MeetingPlace place = new MeetingPlace(MY_STAGE_NUMBER, cochMeetingPoint[0], cochMeetingPoint[1]);

                this.cochMeetingPlaces.add(place);
            } catch (IllegalStateException e) {
                eng.writeLog(String.format("WARNING: meeting place with coch was not found! names: %s, %s.",
                                           new Object[] { cochMeetingPoint[0],
                        cochMeetingPoint[1] }));
            }
        }
    }

    /**
     * Method description
     *
     */
    public void prepareKohDeserealize() {
        if (!(this.wasMadeKoh)) {
            return;
        }

        this.spawnedController = KohHelp.prepareSerialize();
        this.scheduleRestoreNickLocationOnMeetingPlace = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getCurrent_scene() {
        return this.current_scene;
    }

    /**
     * Method description
     *
     *
     * @param current_scene
     */
    public void setCurrent_scene(int current_scene) {
        this.current_scene = current_scene;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isF_makeKoh() {
        return this.makeKoh;
    }

    /**
     * Method description
     *
     *
     * @param koh
     */
    public void setF_makeKoh(boolean koh) {
        this.makeKoh = koh;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isF_wasMadeKoh() {
        return this.wasMadeKoh;
    }

    /**
     * Method description
     *
     *
     * @param madeKoh
     */
    public void setF_wasMadeKoh(boolean madeKoh) {
        this.wasMadeKoh = madeKoh;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public CoreTime getTimeKohOrdered() {
        return this.timeKohOrdered;
    }

    /**
     * Method description
     *
     *
     * @param timeKohOrdered
     */
    public void setTimeKohOrdered(CoreTime timeKohOrdered) {
        this.timeKohOrdered = timeKohOrdered;
    }
}


//~ Formatted in DD Std on 13/08/28
