/*
 * @(#)Dialog.java   13/08/28
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

import rnr.src.menuscript.AnswerMenu;
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.loc;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;
import rnr.src.rnrscr.Dialog.Phrase;
import rnr.src.rnrscr.Dialog.Phrase.request;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class Dialog {
    private static final int YES_ANSWER = 0;
    private static final int NO_ANSWER = 1;
    private static final int SCENE_MC_MAN = 0;
    private static final int SCENE_MC_WOMAN = 1;
    private static final int SCENE_MAN_MC = 2;
    private static final int SCENE_WOMAN_MC = 3;
    private static final String FADE_BAR = "justfadebar";
    private static final String FADE_CAR = "justfade";
    private static final String EVENT_START_CARDIALOG = "start car dialog scene in MC car";
    private static final String EVENT_END_CARDIALOG = "end car dialog scene in MC car";
    private static final String EVENT_START_DIALOG = "start dialog scene in MC car";
    private static final String EVENT_END_DIALOG = "end dialog scene in MC car";
    private static final double DISTANCE_TO_PARKING = 200.0D;
    private static final double DISTANCE_TO_MISSION_POINT = 50.0D;
    private static final int scenefalg = 17;
    private static final int scenecyclefalg = 3;
    private static final int fadefalg = 17;
    private static final String TOP_NODE = "dialogs";
    private static final String DIALOG_NODE = "dialog";
    private static final String PHRASE_NODE = "item";
    private static final String NAME_ATTR = "name";
    private static final String CALLER_ATTR = "caller";
    private static final String CALLEE_ATTR = "callee";
    private static final String WAV_ATTR = "wav";
    private static final String QUESTION_NODE = "question";
    private static final String QUESTION_ID_ATTR = "id";
    private static final String QUESTION_TEXT_ATTR = "text";
    private static final int LIVE = 0;
    private static final int MAN = 1;
    private static final int WOMAN = 2;
    private static final String[] BAR_ENVIRONMENT;
    private static final String[] CAR_ENVIRONMENT;
    private static final SceneClipRandomizer[] SIMPLE_POINT_ENVIRONMENT;
    private static final String[] methNames;
    private static HashMap<String, Dialog> storage;

    static {
        BAR_ENVIRONMENT = new String[] { "XXXNicABar", "XXXManBar", "XXXWomanBar" };
        CAR_ENVIRONMENT = new String[] { "XXXNicACar", "pass_man", "pass_woman" };
        SIMPLE_POINT_ENVIRONMENT = new SceneClipRandomizer[] {
            new SceneClipRandomizer("meet_client_MC_to_man",
                                    new SceneClipRandomizer.Clips[] {
                                        new SceneClipRandomizer.Clips("CamS_MeetClient",
                                            new String[] { "CamS_to_Ivan_near_Clip",
                "CamS_to_Ivan_far_Clip" }), new SceneClipRandomizer.Clips("IVAN_NEW",
                new String[] { "IVAN_MEW_MeetClient_listen_2_Clip",
                               "IVAN_MEW_MeetClient_listen_hand_Clip", "IVAN_MEW_MeetClient_talk_Clip" }) }),
            new SceneClipRandomizer("meet_client_MC_to_woman",
                                    new SceneClipRandomizer.Clips[] {
                                        new SceneClipRandomizer.Clips("CamS_MeetClient",
                                            new String[] { "CamS_to_Ivan_near_Clip",
                "CamS_to_Ivan_far_Clip" }), new SceneClipRandomizer.Clips("IVAN_NEW",
                new String[] { "IVAN_MEW_MeetClient_listen_2_Clip",
                               "IVAN_MEW_MeetClient_listen_hand_Clip",
                               "IVAN_MEW_MeetClient_talk_Clip" }), new SceneClipRandomizer.Clips("Woman",
                                   new String[] { "Woman_bsg_listen_idle_plot_Clip" }) }),
            new SceneClipRandomizer("meet_client_man_to_MC",
                                    new SceneClipRandomizer.Clips[] {
                                        new SceneClipRandomizer.Clips("CamS_MeetClient",
                                            new String[] { "CamS_to_NPC_far_Clip",
                "CamS_to_NPC_near_Clip" }), new SceneClipRandomizer.Clips("IVAN_NEW",
                new String[] { "IVAN_MEW_MeetClient_listen_2_Clip",
                               "IVAN_MEW_MeetClient_listen_hand_Clip" }), new SceneClipRandomizer.Clips("Man",
                               new String[] { "MAN_MeetClient_talk_Clip",
                "MAN_MeetClient_talk_with_shlders_Clip", "MAN_MeetClient_listen_Clip" }) }),
            new SceneClipRandomizer("meet_client_woman_to_MC",
                                    new SceneClipRandomizer.Clips[] {
                                        new SceneClipRandomizer.Clips("CamS_MeetClient",
                                            new String[] { "CamS_to_NPC_far_Clip",
                "CamS_to_NPC_near_Clip" }), new SceneClipRandomizer.Clips("IVAN_NEW",
                new String[] { "IVAN_MEW_MeetClient_listen_2_Clip",
                               "IVAN_MEW_MeetClient_listen_hand_Clip" }), new SceneClipRandomizer.Clips("Woman",
                               new String[] { "Woman_bsg_talk_plot_Clip",
                "Woman_bsg_listen_idle_plot_Clip" }) }) };
        methNames = new String[] { "nextPhrase", "dialogended", "makeAnswerMenu" };
        storage = new HashMap<String, Dialog>();
    }

    private String[] ENVIRONMENT = null;
    private String FADE = null;
    private matrixJ M = null;
    private vectorJ P = null;
    private long cycleScene = 0L;
    private boolean simplePointScene = false;
    private String name = "";
    private BunchPhrases phrases = new BunchPhrases();
    private BunchPhrases current_flame = null;
    private SCRuniperson person_caller = null;
    private SCRuniperson person_callee = null;
    private ILeaveDialog iLeave = null;
    private int index = 0;
    private String identitie;

    /** Field description */
    public String nowarnings0;

    /** Field description */
    public actorveh nowarnings1;

    /** Field description */
    public matrixJ nowarnings2;

    /** Field description */
    public vectorJ nowarnings3;
    private actorveh car;

    private Dialog(Dialog value) {
        this.name = value.name;
        this.phrases = new BunchPhrases(value.phrases);
    }

    private Dialog(String name) {
        this.name = name;
    }

    private void sayAppear() {
        MissionDialogs.sayAppear(this.name);
    }

    private void sayYes() {
        MissionDialogs.sayYes(this.name);
    }

    private void sayNo() {
        MissionDialogs.sayNo(this.name);
    }

    private void sayEndDialog() {
        MissionDialogs.sayEnd(this.name);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean hasFinish() {
        return MissionDialogs.hasFinish(this.name);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public static final Dialog getDialog(String name) {
        boolean hasDialog = storage.containsKey(name);

        if (!(hasDialog)) {
            return null;
        }

        Dialog dialog = storage.get(name);

        assert(dialog != null);

        return new Dialog(dialog);
    }

    /**
     * Method description
     *
     *
     * @param npc
     * @param callee
     * @param identitie
     */
    public final void start_car_leave_car_on_end(aiplayer npc, SCRuniperson callee, String identitie) {
        this.iLeave = new LeaveDialogAndLeaveCar(npc);
        this.car = Crew.getIgrokCar();
        npc.bePassangerOfCar(this.car);
        this.car.registerCar("IvanCar");
        sayAppear();
        this.person_callee = callee;
        this.person_caller = npc.getModel();
        this.ENVIRONMENT = CAR_ENVIRONMENT;
        this.FADE = "justfade";
        this.identitie = identitie;
        this.current_flame = this.phrases;
        this.index = 0;
        fade();
    }

    /**
     * Method description
     *
     *
     * @param caller
     * @param callee
     * @param identitie
     */
    public final void start_bar(SCRuniperson caller, SCRuniperson callee, String identitie) {
        this.iLeave = new SimpleLeave();
        sayAppear();
        this.person_callee = callee;
        this.person_caller = caller;
        this.ENVIRONMENT = new String[BAR_ENVIRONMENT.length];

        for (int i = 0; i < this.ENVIRONMENT.length; ++i) {
            this.ENVIRONMENT[i] = BAR_ENVIRONMENT[i] + Bar.barType;
        }

        this.FADE = "justfadebar" + Bar.barType;
        this.identitie = identitie;
        this.current_flame = this.phrases;
        this.index = 0;
        fade();
    }

    /**
     * Method description
     *
     *
     * @param callee
     * @param identitie
     * @param M
     * @param P
     */
    public final void start_simplePoint(SCRuniperson callee, String identitie, matrixJ M, vectorJ P) {
        this.iLeave = new SimplePointLeavedialog();
        sayAppear();
        this.simplePointScene = true;
        this.person_callee = callee;

        aiplayer callerAIPlayer = aiplayer.getCutSceneAmbientPerson(identitie, "meet_client");

        this.person_caller = callerAIPlayer.getModel();
        this.FADE = "justfade";
        this.identitie = identitie;
        this.M = M;
        this.P = P;
        this.current_flame = this.phrases;
        this.index = 0;
        fade();
    }

    private final void fade() {
        event.eventObject((int) scenetrack.CreateSceneXML(this.FADE, 17, null), this, methNames[0]);
    }

    private String[] constructMessage(Phrase mess) {
        if (mess.requests.isEmpty()) {
            return new String[0];
        }

        String[] res = new String[mess.requests.size()];
        int iter = 0;

        for (Phrase.request req : mess.requests) {
            res[(iter++)] = loc.getDialogName(Phrase.request.access$900(req));
        }

        return res;
    }

    /**
     * Method description
     *
     */
    public void makeAnswerMenu() {
        Phrase firstPhrase = this.current_flame.Phrases.get(this.index);

        if (firstPhrase.isRequest()) {
            AnswerMenu.createAnswerMenu(constructMessage(firstPhrase), new WaitQnswer());
        } else {
            eng.err(
                "ERRORR. Dialog has error in dialog process. makeAnswerMenu reached firstPhrase.isRequest()==false");
        }
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void nextPhrase() {
        if ((this.current_flame.Phrases.isEmpty()) || (this.current_flame.Phrases.size() < this.index)) {
            dialogended();

            return;
        }

        Phrase firstPhrase = this.current_flame.Phrases.get(this.index);
        int flag = 17;

        if (firstPhrase.isRequest()) {
            eng.err("ERRORR. Dialog has error in dialog process. nextPhrase reached firstPhrase.isRequest()==true");
        } else {
            this.index += 1;

            Vector v = new Vector(1);

            if (firstPhrase.is_caller) {
                v.add(new SceneActorsPool("man", (firstPhrase.is_caller)
                                                 ? this.person_caller
                                                 : this.person_callee));
            }

            int tip_track_name = 0;
            String npc_model_name = eng.GetManPrefix(this.person_caller.nativePointer);
            boolean talkWithMan = !(npc_model_name.contains("Woman"));

            if (firstPhrase.is_caller) {
                tip_track_name = (talkWithMan)
                                 ? 1
                                 : 2;
            } else {
                tip_track_name = 0;
            }

            if (this.current_flame.Phrases.size() > this.index) {
                Phrase nextPhrase = this.current_flame.Phrases.get(this.index);

                if (nextPhrase.isRequest()) {
                    flag = 3;
                }
            }

            long scene = 0L;

            if (this.simplePointScene) {
                Vector pool = new Vector(1);

                pool.add(new SceneActorsPool("man", this.person_caller));

                int indexSceneCreator = (firstPhrase.is_caller)
                                        ? 3
                                        : (talkWithMan)
                                          ? 0
                                          : (firstPhrase.is_caller)
                                            ? 2
                                            : 1;

                assert(indexSceneCreator < SIMPLE_POINT_ENVIRONMENT.length);
                assert(this.M != null);
                assert(this.P != null);
                scene = SIMPLE_POINT_ENVIRONMENT[indexSceneCreator].createScene(flag, pool, new Object() {
                    public String Phrase;
                    public String model;
                    public String identity;
                    public actorveh car;
                    public matrixJ M;
                    public vectorJ P;
                });
            } else {
                scene = scenetrack.CreateSceneXMLPool(this.ENVIRONMENT[tip_track_name], flag, v, new Object() {
                    public String Phrase;
                    public String model;
                    public String identity;
                    public actorveh car;
                    public matrixJ M;
                    public vectorJ P;
                });
            }

            if (flag == 3) {
                this.cycleScene = scene;
                event.eventObject((int) scene + 1, this, methNames[2]);
            } else {
                event.eventObject((int) scene, this, (this.current_flame.Phrases.size() == this.index)
                        ? methNames[1]
                        : methNames[0]);
            }
        }
    }

    /**
     * Method description
     *
     */
    public void dialogended() {
        if (this.iLeave != null) {
            this.iLeave.leaveDialog();
            this.iLeave = null;
        }

        this.index = 0;
        this.current_flame = null;
        this.person_callee.play();
        this.person_caller.stop();
        sayEndDialog();
        rnr.src.menuscript.BarMenu.dialog_ended = true;
    }

    /**
     * Method description
     *
     */
    public static void deInit() {
        storage.clear();
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings("rawtypes")
    public static void init() {
        Vector filenames = new Vector();

        eng.getFilesAllyed("dialogs", filenames);

        Vector _names = filenames;

        for (String name : _names) {
            Node top = XmlUtils.parse(name);

            if ((null == top) || (top.getName().compareToIgnoreCase("dialogs") != 0)) {
                return;
            }

            NodeList children = top.getNamedChildren("dialog");

            for (int i = 0; i < children.size(); ++i) {
                Node node = children.get(i);
                String dialog_name = node.getAttribute("name");
                Dialog dialog = readDialog(node, dialog_name);

                storage.put(dialog_name, dialog);
            }
        }
    }

    private static final Dialog readDialog(Node node, String name) {
        Dialog res = new Dialog(name);

        res.phrases = readPharases(node);

        return res;
    }

    private static final BunchPhrases readPharases(Node node) {
        NodeList children = node.getNamedChildren("item");
        BunchPhrases bunch = new BunchPhrases();

        for (int i = 0; i < children.size(); ++i) {
            Node phrase = children.get(i);

            bunch.Phrases.add(readPhrase(phrase));
        }

        return bunch;
    }

    private static final Phrase readPhrase(Node node) {
        Node phrase = node;
        boolean is_callee = false;

        if (phrase.hasAttribute("callee")) {
            is_callee = true;
        } else if (phrase.hasAttribute("caller")) {
            is_callee = false;
        }

        String message = "";

        if (phrase.hasAttribute("wav")) {
            message = phrase.getAttribute("wav");
        }

        Phrase single_pharse = new Phrase(!(is_callee), message);
        NodeList questions = node.getNamedChildren("question");

        for (Node single_question : questions) {
            String id = single_question.getAttribute("id");
            String text = single_question.getAttribute("text");
            Phrase.request q = new Phrase.request();

            Phrase.request.access$602(q, Integer.parseInt(id));
            Phrase.request.access$902(q, text);

            NodeList dialogs = single_question.getNamedChildren("dialog");

            if (1 == dialogs.size()) {
                Phrase.request.access$702(q, readPharases(dialogs.get(0)));
            }

            single_pharse.requests.add(q);
        }

        return single_pharse;
    }

    private static class BunchPhrases {
        private ArrayList<Dialog.Phrase> Phrases = new ArrayList<Phrase>();

        BunchPhrases() {}

        BunchPhrases(BunchPhrases value) {
            this.Phrases = new ArrayList<Phrase>();

            for (Dialog.Phrase singlePhrase : value.Phrases) {
                this.Phrases.add(new Dialog.Phrase(singlePhrase));
            }
        }
    }


    class LeaveDialogAndLeaveCar implements ILeaveDialog {
        aiplayer npc;

        LeaveDialogAndLeaveCar(aiplayer npc) {
            rnr.src.rnrcore.Helper.peekNativeMessage("start car dialog scene in MC car");
            this.npc = npc;
            npc.getModel().lockPerson();

            actorveh car = Crew.getIgrokCar();

            car.sVeclocity(0.0D);

            Place nearestPlace = MissionSystemInitializer.getMissionsMap().getNearestPlace();
            vectorJ carPos = car.gPosition();

            if (nearestPlace != null) {
                double distance_2 = carPos.len2(nearestPlace.getCoords());

                switch (nearestPlace.getTip()) {
                 case 0 :
                 case 2 :
                 case 3 :
                     if (distance_2 < 40000.0D) {
                         parkingplace parking = parkingplace.findNearestParking(nearestPlace.getCoords());

                         if (parking != null) {
                             car.makeParking(parking, 0);
                             car.leaveParking();
                         }
                     }
                 case 1 :
                }
            }

            car.setHandBreak(true);
            eng.disableControl();
        }

        /**
         * Method description
         *
         */
        @Override
        public void leaveDialog() {
            this.npc.getModel().unlockPerson();
            rnr.src.rnrcore.Helper.peekNativeMessage("end car dialog scene in MC car");
            this.npc.abondoneCar(Dialog.this.car);
            Helper.restoreCameraToIgrokCar();
            eng.enableControl();
            Dialog.this.car.setHandBreak(false);
        }
    }


    private static class Phrase {
        Vector<request> requests = new Vector<request>();
        private boolean is_caller;
        private String phrase;

        Phrase() {}

        Phrase(Phrase value) {
            this.is_caller = value.is_caller;
            this.phrase = new String(value.phrase);
            this.requests = new Vector<request>();

            for (request singleRequest : value.requests) {
                this.requests.add(new request(singleRequest));
            }
        }

        Phrase(boolean is_caller, String phrase) {
            this.is_caller = is_caller;
            this.phrase = phrase;
        }

        boolean isRequest() {
            return (!(this.requests.isEmpty()));
        }

        /**
         * Class description
         *
         *
         * @version        1.0, 13/08/28
         * @author         TJ    
         */
        public static class request {
            private Dialog.BunchPhrases dialog_continue = new Dialog.BunchPhrases();
            private final String requestmessage;
            private final int id;

            request() {
                this.requestmessage = "empty request";
                this.id = 0;
            }

            request(request value) {
                this.requestmessage = new String(value.requestmessage);
                this.id = value.id;
                this.dialog_continue = new Dialog.BunchPhrases(value.dialog_continue);
            }
        }
    }


    class SimpleLeave implements ILeaveDialog {
        SimpleLeave() {
            rnr.src.rnrcore.Helper.peekNativeMessage("start dialog scene in MC car");
        }

        /**
         * Method description
         *
         */
        @Override
        public void leaveDialog() {
            rnr.src.rnrcore.Helper.peekNativeMessage("end dialog scene in MC car");
        }
    }


    class SimplePointLeavedialog implements ILeaveDialog {
        SimplePointLeavedialog() {
            actorveh liveCar = Crew.getIgrokCar();

            liveCar.UpdateCar();
            eng.SwitchDriver_outside_cabin(liveCar.getCar());

            actorveh car = Crew.getIgrokCar();

            car.sVeclocity(0.0D);

            Place nearestPlace = MissionSystemInitializer.getMissionsMap().getNearestPlace();
            vectorJ carPos = car.gPosition();
            vectorJ goodPosition = carPos;

            if (nearestPlace != null) {
                switch (nearestPlace.getTip()) {
                 case 0 :
                 case 2 :
                 case 3 :
                     break;

                 case 1 :
                     goodPosition = nearestPlace.getCoords();
                }
            }

            eng.disableControl();
        }

        /**
         * Method description
         *
         */
        @Override
        public void leaveDialog() {
            Crew.getIgrokCar().UpdateCar();
            eng.SwitchDriver_in_cabin(Crew.getIgrokCar().getCar());
            Helper.restoreCameraToIgrokCar();
            eng.enableControl();
        }
    }


    class WaitQnswer extends TypicalAnm implements IDialogListener {
        private boolean callBackRecieved = false;
        private boolean yesRecieved = false;

        WaitQnswer() {
            eng.CreateInfinitScriptAnimation(this);
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
            if (this.callBackRecieved) {
                if (this.yesRecieved) {
                    Dialog.this.sayYes();
                    makeAnswer(0);
                } else {
                    Dialog.this.sayNo();
                    makeAnswer(1);
                }

                return true;
            }

            return false;
        }

        /**
         * Method description
         *
         *
         * @param dialog_name
         */
        @Override
        public void onAppear(String dialog_name) {}

        /**
         * Method description
         *
         *
         * @param dialog_name
         */
        @Override
        public void onNo(String dialog_name) {
            this.callBackRecieved = true;
            this.yesRecieved = false;
        }

        /**
         * Method description
         *
         *
         * @param dialog_name
         */
        @Override
        public void onYes(String dialog_name) {
            this.callBackRecieved = true;
            this.yesRecieved = true;
        }

        private void makeAnswer(int value) {
            if (0L != Dialog.this.cycleScene) {
                scenetrack.DeleteScene(Dialog.this.cycleScene);
                Dialog.access$202(Dialog.this, 0L);
            }

            Dialog.Phrase phr =
                (Dialog.Phrase) Dialog.BunchPhrases.access$500(Dialog.this.current_flame).get(Dialog.this.index);

            for (Dialog.Phrase.request req : phr.requests) {
                if (Dialog.Phrase.request.access$600(req) == value) {
                    Dialog.access$402(Dialog.this, Dialog.Phrase.request.access$700(req));
                    Dialog.access$302(Dialog.this, 0);
                    Dialog.this.nextPhrase();
                }
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/28
