/*
 * @(#)KohHelp.java   13/08/28
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

import rnr.src.players.CarName;
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.rnrcore.Collide;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrcore.ScriptRef;
import rnr.src.rnrcore.anm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
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
@ScenarioClass(
    scenarioStage = 0,
    fieldWithDesiredStage = ""
)
public class KohHelp implements anm {

    /** Field description */
    public static final String ACCEPT_HELP = "Accept Help Koh";

    /** Field description */
    public static final String ESCAPE_HELP = "Escape Help Koh";
    private static final String SCENENAME = "00030cycle";
    private int LAG = 200;
    private double min_distance = 50.0D;
    private double max_distance = 1000.0D;
    private boolean switchedOffFromOutside;
    private final long scene;
    private boolean is_playing;
    private actorveh koh_car;
    private actorveh player_car;
    private final Object latch;
    private final ScriptRef uid;

    /**
     * Constructs ...
     *
     */
    public KohHelp() {
        this.LAG = 200;
        this.min_distance = 50.0D;
        this.max_distance = 1000.0D;
        this.switchedOffFromOutside = false;
        this.scene = 0L;
        this.is_playing = false;
        this.latch = new Object();
        this.uid = new ScriptRef();
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void setUid(int value) {
        this.uid.setUid(value);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getUid() {
        return this.uid.getUid(this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void removeRef() {
        this.uid.removeRef(this);
    }

    /**
     * Method description
     *
     *
     * @param p
     */
    @Override
    public void updateNative(int p) {}

    static KohHelp prepare(Location where) {
        actorveh car = eng.CreateCarForScenario(CarName.CAR_COCH, where.getOrientation(), where.getPosition());

        Crew.addMappedCar("KOH", car);

        KohHelp help = new KohHelp();

        help.koh_car = car;
        help.player_car = Crew.getIgrokCar();

        KohHelp tmp46_45 = help;

        tmp46_45.getClass();
        ThreadTask.create(new Koh());
        eng.CreateInfinitScriptAnimation(help);

        return help;
    }

    static KohHelp prepareSerialize() {
        actorveh car = Crew.getMappedCar("KOH");
        KohHelp help = new KohHelp();

        help.koh_car = car;
        help.player_car = Crew.getIgrokCar();

        KohHelp tmp31_30 = help;

        tmp31_30.getClass();
        ThreadTask.create(new Koh());
        eng.CreateInfinitScriptAnimation(help);

        return help;
    }

    private presets makePresets(actorveh car) {
        presets pres = new presets();

        pres.M = car.gMatrix();

        vectorJ point1 = new vectorJ(car.gPosition());

        point1.z += 10.0D;

        vectorJ point2 = new vectorJ(point1);

        point2.z -= 20.0D;
        pres.P = Collide.collidePoint(point1, point2);

        return pres;
    }

    private void finish_scene() {
        scenetrack.DeleteScene(this.scene);
    }

    void switchOff() {
        synchronized (this.latch) {
            this.switchedOffFromOutside = true;
            deactivateCochCar();
        }
    }

    /**
     * Method description
     *
     *
     * @param dt
     *
     * @return
     */
    @Override
    public boolean animaterun(double dt) {
        synchronized (this.latch) {
            if (!(this.is_playing)) {
                return false;
            }

            if (this.switchedOffFromOutside) {
                this.is_playing = false;

                return true;
            }

            double distance_2 = this.koh_car.gPosition().len2(this.player_car.gPosition());

            if (distance_2 < 2500.0D) {
                KohHelpManage.accept();
                finish_scene();
                EventsControllerHelper.messageEventHappened("Accept Help Koh");
                this.is_playing = false;

                return true;
            }

            if (distance_2 > 1000000.0D) {
                KohHelpManage.questFinished();
                finish_scene();
                EventsControllerHelper.messageEventHappened("Escape Help Koh");
                deactivateCochCar();
                this.is_playing = false;

                return true;
            }

            return false;
        }
    }

    private void deactivateCochCar() {
        if (null == this.koh_car) {
            return;
        }

        this.koh_car.deactivate();
        this.koh_car = null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public IXMLSerializable getXmlSerializator() {
        return null;
    }

    final class Koh implements Ithreadprocedure {
        ThreadTask safe;

        Koh() {
            this.safe = null;
        }

        /**
         * Method description
         *
         */
        @Override
        public void call() {
            while (KohHelp.this.koh_car.getCar() == 0) {
                this.safe.sleep(200L);
                KohHelp.this.koh_car.UpdateCar();
            }

            eng.lock();
            KohHelp.this.koh_car.sVeclocity(0.0D);

            KohHelp.presets pre = KohHelp.this.makePresets(KohHelp.this.koh_car);

            eng.unlock();
            KohHelp.access$202(KohHelp.this, scenetrack.CreateSceneXML("00030cycle", 517, pre));
            KohHelp.access$302(KohHelp.this, true);
        }

        /**
         * Method description
         *
         *
         * @param safe
         */
        @Override
        public void take(ThreadTask safe) {
            this.safe = safe;
        }
    }


    static final class presets {
        matrixJ M;
        vectorJ P;
    }
}


//~ Formatted in DD Std on 13/08/28
