/*
 * @(#)drvscripts.java   13/08/28
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

import rnr.src.menu.menues;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.IScriptTask;
import rnr.src.rnrcore.SCRcamera;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.ScenarioSync;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrscenario.ThreadTask;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class drvscripts {
    private static Object synk_obj = new Object();

    /** Field description */
    public static drvscripts helper = new drvscripts();

    /**
     * Method description
     *
     *
     * @param person
     * @param car
     *
     * @return
     */
    public static IScriptTask placePersonToCar(SCRuniperson person, actorveh car) {
        return new PlacePersonToCar(person, car);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static IScriptTask resumeScript() {
        return new ResumeScript();
    }

    /**
     * Method description
     *
     *
     * @param safe
     *
     * @return
     */
    public static IScriptTask resumeScript(ThreadTask safe) {
        return new ResumeScriptSafe(safe);
    }

    /**
     * Method description
     *
     *
     * @param car
     *
     * @return
     */
    public static IScriptTask outCabinState(actorveh car) {
        return new OutCabinState(car);
    }

    /**
     * Method description
     *
     *
     * @param car
     *
     * @return
     */
    public static IScriptTask inCabinState(actorveh car) {
        return new InCabinState(car);
    }

    /**
     * Method description
     *
     *
     * @param person
     * @param car
     *
     * @return
     */
    public static IScriptTask animatedDoorOut(SCRuniperson person, actorveh car) {
        return new AnimatedDoor(person, car, "_out");
    }

    /**
     * Method description
     *
     *
     * @param person
     * @param car
     *
     * @return
     */
    public static IScriptTask animatedDoorIn(SCRuniperson person, actorveh car) {
        return new AnimatedDoor(person, car, "_in");
    }

    /**
     * Method description
     *
     *
     * @param person
     * @param car
     *
     * @return
     */
    public static IScriptTask deleteAnimatedDoorOut(SCRuniperson person, actorveh car) {
        return new DeleteAnimatedDoor(person, car, "_out");
    }

    /**
     * Method description
     *
     *
     * @param person
     * @param car
     *
     * @return
     */
    public static IScriptTask deleteAnimatedDoorIn(SCRuniperson person, actorveh car) {
        return new DeleteAnimatedDoor(person, car, "_in");
    }

    /**
     * Method description
     *
     *
     * @param tsk
     * @param camp
     */
    public void DeleteTaskNCam(long tsk, long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);

        cam.DeleteCamera();
        eng.DeleteTASK(tsk);
    }

    /**
     * Method description
     *
     *
     * @param pers
     * @param veh
     */
    public void playOutOffCarThreaded(aiplayer pers, actorveh veh) {
        ThreadTask safe = new ThreadTask(null);

        playOutOffCarThreaded(pers, veh, safe);
    }

    /**
     * Method description
     *
     *
     * @param pers
     * @param veh
     * @param safe
     */
    public void playOutOffCarThreaded(aiplayer pers, actorveh veh, ThreadTask safe) {
        SCRuniperson person = pers.getModel();

        if ((veh.getCar() == 0) || (person.nativePointer == 0L)) {
            return;
        }

        Helper.placePersonToCar(person, veh);

        long od = eng.CreateTASK(pers.getModel());
        long resumesct = eng.AddScriptTask(od, resumeScript(safe));
        SOscene SC = new SOscene();

        SC.task = od;
        SC.person = person;
        SC.actor = pers;
        SC.vehicle = veh;

        long PARking = eng.AddEventTask(od);
        CarInOutTasks CAR_out = SC.makecaroutOnStart(PARking, true);

        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", resumesct);
        eng.OnBegTASK(PARking, "end", PARking);
        eng.playTASK(PARking);
        safe._susp();
        eng.DeleteTASK(od);
    }

    /**
     * Method description
     *
     *
     * @param pers
     * @param veh
     */
    public void playInsideCarThreaded(aiplayer pers, actorveh veh) {
        ThreadTask safe = new ThreadTask(null);

        playInsideCarThreaded(pers, veh, safe);
    }

    /**
     * Method description
     *
     *
     * @param pers
     * @param veh
     * @param safe
     */
    public void playInsideCarThreaded(aiplayer pers, actorveh veh, ThreadTask safe) {
        SCRuniperson person = pers.getModel();

        if ((0 == veh.getCar()) || (0L == person.nativePointer)) {
            return;
        }

        Helper.placePersonToCar(person, veh);

        long od = eng.CreateTASK(pers.getModel());
        long resumesct = eng.AddScriptTask(od, resumeScript(safe));
        SOscene SC = new SOscene();

        SC.task = od;
        SC.person = person;
        SC.actor = pers;
        SC.vehicle = veh;

        long startScene = eng.AddEventTask(od);
        CarInOutTasks CAR_in = SC.makecarinOnStart(startScene);

        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", resumesct);
        eng.playTASK(startScene);
        safe._susp();
        eng.DeleteTASK(od);
    }

    /**
     * Method description
     *
     *
     * @param pers
     * @param car
     */
    public static void playInsideCarFast(aiplayer pers, actorveh car) {
        SCRuniperson person = pers.getModel();

        if ((0 == car.getCar()) || (0L == person.nativePointer)) {
            return;
        }

        Helper.placePersonToCar(person, car);
        eng.SwitchDriver_in_cabin(car.getCar());
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public void BlowCaritself(long car) {
        eng.BlowCar((int) car);
    }

    /**
     * Method description
     *
     *
     * @param player
     * @param car
     */
    public static void BlowScene(aiplayer player, actorveh car) {
        if ((null == car) || (null == player)) {
            return;
        }

        car.sVeclocity(0.0D);

        long od = eng.CreateTASK(player.getModel());
        long blowprocess = eng.AddEventTask(od);
        long blow = eng.AddScriptTask(od, new BlowCarItSelf(car));
        long mune = eng.AddScriptTask(od, new BlowCarMenu());
        BlowCarScene blowCarScene = new BlowCarScene(car);
        long track = eng.AddScriptTask(od, blowCarScene);
        long end = eng.AddScriptTask(od, new EndBlowCar(od, blowCarScene));

        eng.OnBegTASK(blowprocess, "play", track);
        eng.OnMidTASK(blowprocess, 0.1D, 0.1D, "play", blow);
        eng.OnMidTASK(blowprocess, 15.0D, 15.0D, "play", mune);
        eng.OnEndTASK(mune, "play", end);
        eng.playTASK(blowprocess);
    }

    /**
     * Method description
     *
     *
     * @param player
     * @param car
     */
    public static void BlowSceneOtherPlayer(aiplayer player, actorveh car) {
        if ((null == car) || (null == player)) {
            return;
        }

        car.sVeclocity(0.0D);

        long od = eng.CreateTASK(player.getModel());
        long blowprocess = eng.AddEventTask(od);
        long blow = eng.AddScriptTask(od, new BlowCarItSelf(car));
        long track = eng.AddScriptTask(od, new BlowCarSceneItSelf(car));
        long end = eng.AddScriptTask(od, new EndBlowCar(od, null));

        eng.OnBegTASK(blowprocess, "play", track);
        eng.OnMidTASK(blowprocess, 0.1D, 0.1D, "play", blow);
        eng.OnEndTASK(blow, "play", end);
        eng.playTASK(blowprocess);
    }

    static abstract interface IListenEndPredefAnimation {

        /**
         * Method description
         *
         */
        public abstract void animationFinished();
    }


    static class AnimatedDoor implements IScriptTask {
        private final SCRuniperson person;
        private final actorveh car;
        private final String anm_suffix;

        AnimatedDoor(SCRuniperson person, actorveh car, String anm_suffix) {
            this.person = person;
            this.car = car;
            this.anm_suffix = anm_suffix;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            String modelprefix = eng.GetVehiclePrefix(this.car.getCar());
            matrixJ mrot = new matrixJ();
            String prefix = eng.GetManPrefix(this.person.nativePointer);

            this.person.CreateAnimatedSpace_timedependance(modelprefix + "door1Space",
                    prefix + this.anm_suffix + modelprefix, "space_door", prefix + this.anm_suffix + modelprefix, 0.0D,
                    eng.CarNode(this.car.getCar()), this.car.getCar(), mrot, true, false);
        }
    }


    static class BlowCarItSelf implements IScriptTask {
        private final actorveh car;

        BlowCarItSelf(actorveh car) {
            this.car = car;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            eng.BlowCar(this.car.getCar());
        }
    }


    static class BlowCarMenu implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            menues.gameoverMenu();
        }
    }


    static class BlowCarScene implements IScriptTask, drvscripts.IListenEndPredefAnimation {
        private final actorveh car;
        private long scene;

        BlowCarScene(actorveh car) {
            this.car = car;
            this.scene = 0L;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            drvscripts.Preset blowpreset = new drvscripts.Preset();

            blowpreset.car = this.car;
            this.scene = scenetrack.CreateSceneXML("blowcar", 1, blowpreset);
        }

        /**
         * Method description
         *
         */
        @Override
        public void animationFinished() {
            if (this.scene != 0L) {
                scenetrack.DeleteScene(this.scene);
            }
        }
    }


    static class BlowCarSceneItSelf implements IScriptTask {
        private final actorveh car;

        BlowCarSceneItSelf(actorveh car) {
            this.car = car;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            drvscripts.Preset blowpreset = new drvscripts.Preset();

            blowpreset.car = this.car;
            scenetrack.CreateSceneXML("blowcar2070", 17, blowpreset);
        }
    }


    static class DeleteAnimatedDoor implements IScriptTask {
        private final SCRuniperson person;
        private final actorveh car;
        private final String anm_suffix;

        DeleteAnimatedDoor(SCRuniperson person, actorveh car, String anm_suffix) {
            this.person = person;
            this.car = car;
            this.anm_suffix = anm_suffix;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            String modelprefix = eng.GetVehiclePrefix(this.car.getCar());

            this.person.DeleteAnimatedSpace(modelprefix + "door1Space",
                                            eng.GetManPrefix(this.person.nativePointer) + this.anm_suffix
                                            + modelprefix, eng.CarNode(this.car.getCar()));
        }
    }


    static class EndBlowCar implements IScriptTask {
        long task;
        private final drvscripts.IListenEndPredefAnimation m_listenerLaunch;

        EndBlowCar(long task, drvscripts.IListenEndPredefAnimation listenerLaunch) {
            this.task = task;
            this.m_listenerLaunch = listenerLaunch;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            if (null != this.m_listenerLaunch) {
                this.m_listenerLaunch.animationFinished();
            }

            eng.DeleteTASK(this.task);
        }
    }


    static class InCabinState implements IScriptTask {
        private final actorveh car;

        InCabinState(actorveh car) {
            this.car = car;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            eng.SwitchDriver_in_cabin(this.car.getCar());
        }
    }


    static class OutCabinState implements IScriptTask {
        private final actorveh car;

        OutCabinState(actorveh car) {
            this.car = car;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            eng.SwitchDriver_outside_cabin(this.car.getCar());
        }
    }


    static class PlacePersonToCar implements IScriptTask {
        private final SCRuniperson person;
        private final actorveh car;

        PlacePersonToCar(SCRuniperson person, actorveh car) {
            this.person = person;
            this.car = car;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            Helper.placePersonToCar(this.person, this.car);
            this.person.play();
        }
    }


    static class Preset {
        actorveh car;
    }


    static class ResumeScript implements IScriptTask {
        ResumeScript() {
            eng.log("ResumeScript");
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            ScenarioSync.resumeScene();
        }
    }


    static class ResumeScriptSafe implements IScriptTask {
        private final ThreadTask safe;

        ResumeScriptSafe(ThreadTask safe) {
            this.safe = safe;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            this.safe._resum();
        }
    }
}


//~ Formatted in DD Std on 13/08/28
