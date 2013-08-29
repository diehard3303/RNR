/*
 * @(#)BarScene.java   13/08/28
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
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.IScriptTask;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class BarScene {

    /** Field description */
    public static final String OUTSIDE_SCENE = "barin outside";

    /** Field description */
    public static final String INSIDE_SCENE = "barin inside ";

    /** Field description */
    public static final String NEWSPAPER_SCENE = "barin move newspaper ";

    /** Field description */
    public static final String OUTSIDE_DOOR_PREFIX = "Space_DoorToBar_";

    /** Field description */
    public static final String INSIDE_DOOR_PREFIX = "Space_DoorBar0";

    /** Field description */
    public static final String INSIDE_NEWSPAPER_PREFIX = "Space_NewspaperBar0";

    /** Field description */
    public static final boolean MENU_ON_SINGLE_PAPER = 1;
    private actorveh car;
    private long task;

    /**
     * Method description
     *
     */
    public void PersonageInBar() {
        aiplayer player = Crew.getIgrok();
        SCRuniperson person = player.getModel();

        this.car = Crew.getIgrokCar();
        this.task = eng.CreateTASK(person);

        long startinsideanimation = eng.AddScriptTask(this.task, new StartBarScene());
        long person_ingame = eng.AddScriptTask(this.task, new PlacePersonInGame(person));
        long game_world = eng.AddChangeWorldTask(this.task, "game", "simple");
        long movetocar = eng.AddScriptTask(this.task, drvscripts.placePersonToCar(person, this.car));
        long realesecar = eng.AddScriptTask(this.task, new ReleaseFromParking());
        long freeall = eng.AddScriptTask(this.task, new FinishTask());
        long NEWSPAPEREND = eng.AddEventTask(this.task);

        eng.EventTask_onBARMessageClosed(NEWSPAPEREND, false);
        eng.OnEndTASK(NEWSPAPEREND, "play", game_world);
        eng.OnEndTASK(game_world, "play", movetocar);
        eng.OnEndTASK(game_world, "play", person_ingame);

        SOscene SC = new SOscene();

        SC.task = this.task;
        SC.person = person;
        SC.actor = player;
        SC.vehicle = this.car;

        long PARking = SC.waitParkingEvent();
        CarInOutTasks CAR_out = SC.makecaroutOnEnd(PARking, false);
        CarInOutTasks CAR_in = SC.makecarinOnEnd(game_world);

        SC.restorecameratoCarOnEnd(CAR_in.getMTaskToWait());
        eng.playTASK(PARking);
        eng.disableControl();
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", realesecar);
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", freeall);
        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", startinsideanimation);
    }

    /**
     * Method description
     *
     */
    public void PersonageInBar_short() {
        aiplayer player = Crew.getIgrok();
        SCRuniperson person = player.getModel();

        this.car = Crew.getIgrokCar();
        this.task = eng.CreateTASK(person);

        long startinsideanimation = eng.AddScriptTask(this.task, new StartBarScene());
        long person_ingame = eng.AddScriptTask(this.task, new PlacePersonInGame(person));
        long game_world = eng.AddChangeWorldTask(this.task, "game", "simple");
        long movetocar = eng.AddScriptTask(this.task, drvscripts.placePersonToCar(person, this.car));
        long realesecar = eng.AddScriptTask(this.task, new ReleaseFromParking());
        long freeall = eng.AddScriptTask(this.task, new FinishTask());
        long NEWSPAPEREND = eng.AddEventTask(this.task);

        eng.EventTask_onBARMessageClosed(NEWSPAPEREND, false);
        eng.OnEndTASK(NEWSPAPEREND, "play", game_world);
        eng.OnEndTASK(game_world, "play", movetocar);
        eng.OnEndTASK(game_world, "play", person_ingame);

        SOscene SC = new SOscene();

        SC.task = this.task;
        SC.person = person;
        SC.actor = player;
        SC.vehicle = this.car;

        long PARking = SC.waitParkingEvent();
        long CAR_out = PARking;
        CarInOutTasks CAR_in = SC.makecarinOnEnd(game_world);

        SC.restorecameratoCarOnEnd(CAR_in.getMTaskToWait());
        eng.playTASK(PARking);
        eng.disableControl();
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", realesecar);
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", freeall);
        eng.OnEndTASK(CAR_out, "play", startinsideanimation);
    }

    class FinishTask implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            eng.DeleteTASK(BarScene.this.task);
            eng.enableControl();
        }
    }


    static class PlacePersonInGame implements IScriptTask {
        private final SCRuniperson person;

        PlacePersonInGame(SCRuniperson person) {
            this.person = person;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            this.person.SetInGameWorld();
            eng.returnCameraToGameWorld();
        }
    }


    class ReleaseFromParking implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            BarScene.this.car.leaveParking();
        }
    }


    class StartBarScene implements IScriptTask, IBarMoveNewspaperAnimation {
        private static final String METHOD_FINISHED = "barinFinished";
        private static final String METHOD_INSIDE = "barinInside";
        String bardoor;
        String newspaper;
        private long scene_outside;
        private String last_dialog;

        StartBarScene() {
            this.last_dialog = null;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            long scene = scenetrack.CreateSceneXML("barin outside", 3, specobjects.getBarPresets());

            this.scene_outside = scene;
            event.eventObject((int) scene + 1, this, "barinInside");
            eng.AddSceneTrackToTask(BarScene.this.task, scene);
        }

        /**
         * Method description
         *
         */
        public void barinInside() {
            this.bardoor = "Space_DoorBar0" + Bar.barType;
            this.newspaper = "Space_NewspaperBar0" + Bar.barType;

            long scene = scenetrack.CreateSceneXML("barin inside " + Bar.barType, 17, this);

            event.eventObject((int) scene, this, "barinFinished");
            eng.AddSceneTrackToTask(BarScene.this.task, scene);
        }

        private boolean playImmediateDialogs() {
            if (null != this.last_dialog) {
                Bar.getInstance().endDialog(this.last_dialog);
            }

            vectorJ pos = Bar.getCurrentBarPosition();
            DialogsSet set = MissionDialogs.queueDialogsForSO(8, pos, new CoreTime());
            int size = set.getQuestCount();

            if (size == 0) {
                return false;
            }

            for (int i = 0; i < size; ++i) {
                SODialogParams params = set.getQuest(i);

                if (params.wasPlayed()) {
                    continue;
                }

                if (!(params.isfinishDialog())) {
                    continue;
                }

                this.last_dialog = params.getDescription();
                Bar.getInstance().startDialog(params.getDescription());
                params.play();
                Dialog.getDialog(params.getDescription()).start_bar(params.getNpcModel(), Crew.getIgrok().getModel(),
                                 params.getIdentitie());
                event.eventObject(9850, this, "barinFinished");

                return true;
            }

            return false;
        }

        /**
         * Method description
         *
         */
        public void barinFinished() {
            scenetrack.DeleteScene(this.scene_outside);

            if (playImmediateDialogs()) {
                return;
            }

            Place bar_place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(0);
            String bar_name = null;

            if (null != bar_place) {
                bar_name = bar_place.getName();
            }

            BarMenuCreator.CreateBarMenu(Bar.getCurrentBarPosition(), this);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public long playMoveNeswpaper() {
            long scene = scenetrack.CreateSceneXML("barin move newspaper " + Bar.barType, 9, this);

            eng.AddSceneTrackToTask(BarScene.this.task, scene);

            return scene;
        }

        /**
         * Method description
         *
         */
        public void CreateBarMenu() {
            BarMenuCreator.CreateBarMenu(Bar.getCurrentBarPosition(), this);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
