/*
 * @(#)test.java   13/08/26
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

import rnr.src.adjusting.Variables;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.menues;
import rnr.src.menuscript.EscapeMenu;
import rnr.src.menuscript.NotifyGameOver;
import rnr.src.menuscript.PanelMenu;
import rnr.src.menuscript.PanelMenu.Time;
import rnr.src.menuscript.RacePanelMenu;
import rnr.src.menuscript.TotalVictoryMenu;
import rnr.src.menuscript.VictoryMenu;
import rnr.src.menuscript.cbvideo.Dialogitem;
import rnr.src.menuscript.cbvideo.MenuCall;
import rnr.src.menuscript.mainmenu.StartMenu;
import rnr.src.menuscript.office.OfficeMenu;
import rnr.src.menuscript.org.OrganiserMenu;
import rnr.src.menuscript.testmenu;
import rnr.src.players.CarAnimationController;
import rnr.src.players.CarName;
import rnr.src.players.Chase;
import rnr.src.players.Crew;
import rnr.src.players.CutSceneAuxPersonCreator;
import rnr.src.players.DriversModelsPool;
import rnr.src.players.IdentiteNames;
import rnr.src.players.RaceTrajectory;
import rnr.src.players.ScenarioPersonPassanger;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.players.semitrailer;
import rnr.src.players.vehicle;
import rnr.src.rnrcore.Collide;
import rnr.src.rnrcore.INativeMessageEvent;
import rnr.src.rnrcore.IScriptTask;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrcore.Log;
import rnr.src.rnrcore.NativeEventController;
import rnr.src.rnrcore.SCRcamera;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.ScenarioSync;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.ScriptRef;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.anm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.teleport.ITeleported;
import rnr.src.rnrcore.teleport.MakeTeleport;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrrating.BigRace;
import rnr.src.rnrrating.FactionRater;
import rnr.src.rnrrating.RateSystem;
import rnr.src.rnrscenario.consistency.ScenarioStage;
import rnr.src.rnrscenario.controllers.KohHelpManage;
import rnr.src.rnrscenario.controllers.PiterPanFinalrace;
import rnr.src.rnrscenario.controllers.ResqueDorothyShootAnimate;
import rnr.src.rnrscenario.controllers.chase00090;
import rnr.src.rnrscenario.missions.MissionManager;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.scenes.CrashBarScene;
import rnr.src.rnrscenario.scenes.sc00009;
import rnr.src.rnrscenario.scenes.sc00324;
import rnr.src.rnrscenario.scenes.sc00520;
import rnr.src.rnrscenario.scenes.sc00530;
import rnr.src.rnrscenario.scenes.sc00730;
import rnr.src.rnrscenario.scenes.sc00830;
import rnr.src.rnrscenario.scenes.sc00860;
import rnr.src.rnrscenario.scenes.sc01030;
import rnr.src.rnrscenario.scenes.sc01100;
import rnr.src.rnrscenario.scenes.sc02040;
import rnr.src.rnrscenario.scenes.sc02060;
import rnr.src.rnrscr.AdvancedRandom;
import rnr.src.rnrscr.Bar;
import rnr.src.rnrscr.CarInOutTasks;
import rnr.src.rnrscr.Dialog;
import rnr.src.rnrscr.MissionDialogs;
import rnr.src.rnrscr.Office;
import rnr.src.rnrscr.PedestrianManager;
import rnr.src.rnrscr.SOscene;
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.rnrscr.camscripts.trackclipparams;
import rnr.src.rnrscr.drvscripts;
import rnr.src.rnrscr.parkingplace;
import rnr.src.rnrscr.specobjects;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scenarioMachine.FiniteStateMachine;
import rnr.src.scenarioUtils.Pair;
import rnr.src.scriptActions.ExternalChannelSayAppear;
import rnr.src.scriptActions.StartScenarioMissionAction;
import rnr.src.scriptEvents.EventListener;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.EventsControllerHelper;
import rnr.src.scriptEvents.MessageEvent;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class test {
    private static actorveh carkoh;
    static BigRaceCrowd pers_group1;
    private static actorveh car_park;
    private static long scene_test_mimic;

    /** Field description */
    public static int count_man;

    /** Field description */
    public static int count_ivan;

    /** Field description */
    public static int count_cam;
    private static final String[] Phrases;

    static {
        pers_group1 = null;
        scene_test_mimic = 0L;
        count_man = 0;
        count_ivan = 0;
        count_cam = 0;
        Phrases = new String[] {
            "MatT_SC00009_04", "CocH_SC00011_01", "SteC_SC01200_04", "JohL_SC00480_03", "TomD_SC00430_03",
            "SteC_SC01030_07", "NicA_SC01200_06", "DorL_SC02050_04", "NicA_SCST_20_01", "SteC_SC01200_01",
            "MatT_SC00009_03", "NicA_SC01080_01"
        };
    }

    private ResqueDorothyShootAnimate m_debug_shootanimation;

    /**
     * Method description
     *
     */
    public void doNotKillTasks() {
        scenarioscript.script.doNotClearTasksOnGameDeinit();
    }

    /**
     * Method description
     *
     */
    public void stopCheckEngineWhilePlayingScenes() {
        eng.CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = false;
    }

    /**
     * Method description
     *
     */
    public void oneShotStopCheckEngineWhilePlayingScenes() {
        eng.ONE_SHOT_CHECK_ENGINE_STATE_BEFORE_PLAYING_SCENE = false;
    }

    /**
     * Method description
     *
     */
    public void sc_sd() {
        scenariovalidate.saveDorothy();
    }

    /**
     * Method description
     *
     */
    public void sc_sc() {
        scenariovalidate.strangerCall();
    }

    /**
     * Method description
     *
     */
    public void sc_pp() {
        scenariovalidate.meetPiterPan();
    }

    /**
     * Method description
     *
     */
    public void sc_pp_final() {
        scenariovalidate.meetPiterPanFinalRace();
    }

    /**
     * Method description
     *
     */
    public void sc_pc() {
        scenariovalidate.waitPoliceCall();
    }

    /**
     * Method description
     *
     */
    public void sc_mt() {
        scenariovalidate.meetToto();
    }

    /**
     * Method description
     *
     */
    public void sc_dakota() {
        scenariovalidate.meetDakota();
    }

    /**
     * Method description
     *
     */
    public void sc_mat() {
        scenariovalidate.meetMat();
    }

    /**
     * Method description
     *
     */
    public void sc_eb() {
        scenariovalidate.enemyBase();
    }

    /**
     * Method description
     *
     */
    public void sc_ck() {
        scenariovalidate.chaseKoh();
    }

    /**
     * Method description
     *
     */
    public void sc_ch() {
        scenariovalidate.cursedHiway();
    }

    /**
     * Method description
     *
     */
    public void sc_ch_noorder() {
        scenariovalidate.cursedHiwayWithoutorder();
    }

    /**
     * Method description
     *
     */
    public void sc_police() {
        scenariovalidate.policeEntrapped();
    }

    /**
     * Method description
     *
     */
    public void sc_police_topo() {
        scenariovalidate.policeAfterTopo();
    }

    /**
     * Method description
     *
     */
    public void sc_john_bigrace() {
        scenariovalidate.checkJohnTask();
    }

    /**
     * Method description
     *
     */
    public void sc_br() {
        scenariovalidate.bigRaceQuest();
    }

    /**
     * Method description
     *
     */
    public void sc_br_fake() {
        scenariovalidate.bigRaceQuestFake();
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public boolean hasLoadedScenarioPoint(String name) {
        cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();

        return ((null != crossroad) && (0 == crossroad.name.compareToIgnoreCase(name)));
    }

    /**
     * Method description
     *
     *
     * @param callname
     * @param identitie
     */
    public void call(String callname, String identitie) {
        CBVideoStroredCall call = CBCallsStorage.getInstance().getStoredCall(callname);

        call.makecall(identitie, "void_mission");
    }

    /**
     * Method description
     *
     *
     * @param callname
     */
    public void call(String callname) {
        scenarioscript.script.launchCall(callname);
    }

    /**
     * Method description
     *
     */
    public void ppcall() {
        scenarioscript.script.launchCall("cb01660");
    }

    /**
     * Method description
     *
     */
    public void preparetestbar() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(0.0D, 0.0D, 0.0D);
        actorveh car = eng.CreateCarForScenario(CarName.CAR_DAKOTA, M, pos);

        carkoh = car;
        carkoh.UpdateCar();
        carkoh = car;
    }

    /**
     * Method description
     *
     */
    public void testbar() {
        carkoh.UpdateCar();
        carkoh.registerCar("madcar");
        carkoh.makeUnloadable(false);
        new CrashBarScene().run();
    }

    /**
     * Method description
     *
     */
    public void test_radius() {
        anm anim = new TypicalAnm() {
            private final vectorJ[] m_positionHistory = { null, null, null };
            private vectorJ m_radiusHistory = null;
            private boolean m_canCheckRadiusChangeState = true;
            private boolean m_currentRadiusChangeState = true;
            private boolean m_radiusChanged = true;
            private double m_lastTime = 0.0D;
            private double m_frameLength = 0.0D;
            private double m_countShiftTime = 0.0D;
            private double m_countRotateRadius = 0.0D;
            private final double s_timeToShift = 0.1D;
            private final double s_timeToRotateRadius = 1.0D;
            private void shiftPositionHistiry(vectorJ pos) {
                this.m_positionHistory[2] = this.m_positionHistory[1];
                this.m_positionHistory[1] = this.m_positionHistory[0];
                this.m_positionHistory[0] = pos;
            }
            private void rememberPositions() {
                this.m_countShiftTime += this.m_frameLength;

                if (this.m_countShiftTime < this.s_timeToShift) {
                    return;
                }

                this.m_countShiftTime = 0.0D;

                vectorJ pos = Crew.getIgrokCar().gPosition();

                shiftPositionHistiry(pos);
            }
            private void rememberRadiusVector(vectorJ pos) {
                this.m_countRotateRadius += this.m_frameLength;

                double modulo = (this.m_radiusHistory != null)
                                ? this.m_radiusHistory.x * pos.x + this.m_radiusHistory.y * pos.y
                                : -1.0D;

                if (this.m_countRotateRadius < this.s_timeToRotateRadius) {
                    this.m_radiusChanged |= modulo < -1.E-005D;

                    return;
                }

                this.m_countRotateRadius = 0.0D;
                this.m_radiusHistory = pos;
                this.m_canCheckRadiusChangeState = true;
                this.m_currentRadiusChangeState = this.m_radiusChanged;
                this.m_radiusChanged = (modulo < -1.E-005D);
            }
            private vectorJ getRadius() {
                if ((this.m_positionHistory[0] == null) || (this.m_positionHistory[1] == null)
                        || (this.m_positionHistory[2] == null)) {
                    return new vectorJ(1.0D, 0.0D, 10.0D);
                }

                vectorJ r1 = new vectorJ(this.m_positionHistory[0]);

                r1.oMinus(this.m_positionHistory[1]);
                r1.norm();

                vectorJ r2 = new vectorJ(this.m_positionHistory[2]);

                r2.oMinus(this.m_positionHistory[1]);
                r2.norm();

                return r1.oPlusN(r2);
            }
            private boolean getRadiusCheck() {
                if (!(this.m_canCheckRadiusChangeState)) {
                    return true;
                }

                this.m_canCheckRadiusChangeState = false;

                return this.m_currentRadiusChangeState;
            }
            @Override
            public boolean animaterun(double dt) {
                this.m_frameLength = (dt - this.m_lastTime);
                this.m_lastTime = dt;
                rememberPositions();

                vectorJ radiusVector = getRadius();

                rememberRadiusVector(radiusVector);
                Log.simpleMessage("Radius changed " + getRadiusCheck());

                return false;
            }
        };

        eng.CreateInfinitScriptAnimation(anim);
    }

    void testchase() {
        new ChaseTopoTest();
    }

    void testridefar1() {
        Crew.getIgrokCar().autopilotTo(new vectorJ(3945.0D, 39447.0D, 136.0D));
    }

    void testridefar2() {
        Crew.getIgrokCar().autopilotTo(new vectorJ(23894.0D, -40765.0D, -2.0D));
    }

    /**
     * Method description
     *
     */
    public void group1() {
        if (pers_group1 == null) {
            pers_group1 = new BigRaceCrowd();
        }

        pers_group1.crowPlaceSomeWhere();
    }

    /**
     * Method description
     *
     */
    public void delgroup1() {
        pers_group1.delsome();
    }

    /**
     * Method description
     *
     */
    public void group1_play1() {
        pers_group1.play1();
    }

    /**
     * Method description
     *
     */
    public void group1_play2() {
        pers_group1.play2();
    }

    /**
     * Method description
     *
     */
    public void group1_play3() {
        pers_group1.play3();
    }

    /**
     * Method description
     *
     */
    public void cartrack() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(0.0D, 0.0D, 0.0D);
        actorveh car = eng.CreateCarForScenario(CarName.CAR_DAKOTA, M, pos);

        Crew.addMappedCar("DAKOTA", car);
        pos = new vectorJ(0.0D, -400.0D, 0.0D);
        car = eng.CreateCarForScenario(CarName.CAR_BANDITS, M, pos);
        Crew.addMappedCar("ARGOSY BANDIT", car);
    }

    /**
     * Method description
     *
     */
    public void cartrack_check() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(0.0D, 0.0D, 0.0D);
        actorveh car = eng.CreateCarForScenario(CarName.CAR_DAKOTA, M, pos);

        Crew.addMappedCar("DAKOTA", car);
        pos = new vectorJ(0.0D, -400.0D, 0.0D);
        car = eng.CreateCarForScenario(CarName.CAR_BANDITS, M, pos);
        Crew.addMappedCar("ARGOSY BANDIT", car);
    }

    /**
     * Method description
     *
     */
    public void cartrack1() {
        actorveh car = Crew.getMappedCar("DAKOTA");

        car.registerCar("dakotacar");
        car = Crew.getMappedCar("ARGOSY BANDIT");
        car.registerCar("banditcar");
    }

    /**
     * Method description
     *
     */
    public void createcar() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(0.0D, 0.0D, 0.0D);
        actorveh car = eng.CreateCarForScenario(CarName.CAR_DAKOTA, M, pos);

        Crew.addMappedCar("DAKOTA", car);

        aiplayer dakota = aiplayer.getScenarioAiplayer("SC_ONTANIELO");

        dakota.beDriverOfCar(car);
    }

    /**
     * Method description
     *
     */
    public void deletecar() {
        actorveh dakotaCarNumber = Crew.getMappedCar("DAKOTA");

        if (dakotaCarNumber == null) {
            return;
        }

        dakotaCarNumber.deactivate();
    }

    /**
     * Method description
     *
     */
    public void checkdeleted() {
        aiplayer dakota = aiplayer.getScenarioAiplayer("SC_ONTANIELO");

        dakota.getModel().play();
    }

    /**
     * Method description
     *
     */
    public void bar1cam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");

        freeCam.PlayCamera();
        freeCam.SetInBarWorld();

        vectorJ posit = new vectorJ(0.0D, 0.0D, 2.0D);

        freeCam.SetCameraPosition(posit);
    }

    /**
     * Method description
     *
     */
    public void policeImmunityOff() {
        eng.makePoliceImmunity(false);
    }

    /**
     * Method description
     *
     */
    public void bar2cam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");

        freeCam.PlayCamera();
        freeCam.SetInBar2World();

        vectorJ posit = new vectorJ(0.0D, 0.0D, 2.0D);

        freeCam.SetCameraPosition(posit);
    }

    /**
     * Method description
     *
     */
    public void bar3cam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");

        freeCam.PlayCamera();
        freeCam.SetInBar3World();

        vectorJ posit = new vectorJ(0.0D, 0.0D, 2.0D);

        freeCam.SetCameraPosition(posit);
    }

    /**
     * Method description
     *
     */
    public void policecam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");

        freeCam.PlayCamera();
        freeCam.SetInPoliceWorld();

        vectorJ posit = new vectorJ(0.0D, 0.0D, 2.0D);

        freeCam.SetCameraPosition(posit);
    }

    /**
     * Method description
     *
     */
    public void officecam() {
        SCRuniperson person = Crew.getIgrok().getModel();
        long task = eng.CreateTASK(person);
        long officeWorld = eng.AddChangeWorldTask(task, "office", "simple");
        long officeCamera = eng.AddScriptTask(task, new OfficeCam());

        eng.OnEndTASK(officeWorld, "play", officeCamera);
        eng.playTASK(officeWorld);
    }

    /**
     * Method description
     *
     */
    public void motelcam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");

        freeCam.PlayCamera();
        freeCam.SetInHotelWorld();

        vectorJ posit = new vectorJ(0.0D, 0.0D, 2.0D);

        freeCam.SetCameraPosition(posit);
    }

    /**
     * Method description
     *
     */
    public void PanelDeliveryDamaged() {
        PanelMenu.PanelDeliveryDamaged("from script", 101, 102, 103, false);
    }

    /**
     * Method description
     *
     */
    public void PanelDeliveryExpired() {
        PanelMenu.PanelDeliveryExpired("from script", 101, 102, 103, false);
    }

    /**
     * Method description
     *
     */
    public void PanelDeliveryTowed() {
        PanelMenu.PanelDeliveryTowed("from script", 101, 102);
    }

    /**
     * Method description
     *
     */
    public void PanelDeliveryCancelled() {
        PanelMenu.PanelDeliveryTowedCancelled("from script", 101, 102, 103, 104);
    }

    /**
     * Method description
     *
     */
    public void PanelDeliveryExecuted() {
        PanelMenu.PanelDeliveryExecuted("from script", 101, 102, 103, 104);
    }

    /**
     * Method description
     *
     */
    public void PanelDeliveryFirst() {
        PanelMenu.PanelDeliveryFirst("from script", 101, 102, 103, 104);
    }

    /**
     * Method description
     *
     */
    public void PanelAnotherOrderLate() {
        PanelMenu.PanelTenderLate("from script", 101, 102);
    }

    /**
     * Method description
     *
     */
    public void PanelAnotherOrderFirst() {
        PanelMenu.Time t = new PanelMenu.Time(1, 2, 3);

        PanelMenu.PanelTenderFirst("from script", 101, t, 103.0D, 104.0D, 105, 106);
    }

    /**
     * Method description
     *
     */
    public void PanelAnotherOrderDefault() {
        PanelMenu.PanelTenderDefaulted("from script", 101, 102);
    }

    /**
     * Method description
     *
     */
    public void PanelDrivingContestDefaulted() {
        PanelMenu.PanelContestDefaulted("from script", 101, 102);
    }

    /**
     * Method description
     *
     */
    public void PanelDrivingContestFirst() {
        PanelMenu.Time t = new PanelMenu.Time(1, 2, 3);

        PanelMenu.PanelContestFirst("from script", t, 101.0D, 102.0D, 103, 104, 105);
    }

    /**
     * Method description
     *
     */
    public void PanelDrivingContestFinished() {
        PanelMenu.Time t = new PanelMenu.Time(1, 2, 3);

        PanelMenu.PanelContestExecuted("from script", 101, t, 102.0D, 103.0D, 104, 105);
    }

    /**
     * Method description
     *
     */
    public void panelchange() {
        RacePanelMenu.PanelPreparingToRaceIn();
    }

    /**
     * Method description
     *
     */
    public void jail() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void gameover() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0D, 0);
    }

    /**
     * Method description
     *
     */
    public void murder() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0D, 2);
    }

    /**
     * Method description
     *
     *
     * @param posx
     * @param posy
     * @param posz
     */
    public void testdrive(int posx, int posy, int posz) {
        Crew.getIgrokCar().autopilotTo(new vectorJ(posx, posy, posz));
    }

    /**
     * Method description
     *
     */
    public void stoptestdrive() {
        Crew.getIgrokCar().stop_autopilot();
    }

    /**
     * Method description
     *
     *
     * @param message
     */
    public void msg(String message) {
        EventsControllerHelper.messageEventHappened(message);
    }

    /**
     * Method description
     *
     *
     * @param q
     */
    public void quest(String q) {
        scenarioscript.script.gMachine().activateState(true, new String[] { q + "_phase_" + 1 });
    }

    /**
     * Method description
     *
     */
    public void startmenu() {
        StartMenu.create();
    }

    /**
     * Method description
     *
     */
    public void testallmenu() {
        TestCreateAllMenues.runAll();
    }

    /**
     * Method description
     *
     */
    public void testoffice() {
        OfficeMenu.create();
        eng.CreateInfinitScriptAnimation(new ShowOfficeMenu());
    }

    /**
     * Method description
     *
     */
    public void autotest_startmenu() {
        scenarioscript.setAutotestRun(true);
        StartMenu.autotest_create();
    }

    /**
     * Method description
     *
     */
    public void showSTOmenu() {
        menues.showMenu(6000);
    }

    /**
     * Method description
     *
     */
    public void motelmenu() {
        menues.CreateMotelMENU();
    }

    /**
     * Method description
     *
     */
    public void showmotelenu() {
        menues.showMenu(7000);
    }

    /**
     * Method description
     *
     */
    public void adjust() {
        Variables.adjust();
    }

    /**
     * Method description
     *
     *
     * @param xmlname
     * @param constrolgroup
     *
     * @return
     */
    public long menu(String xmlname, String constrolgroup) {
        long menu = testmenu.CreateTestMenu("..\\data\\config\\menu\\" + xmlname, constrolgroup);

        menues.WindowSet_ShowCursor(menu, true);

        return menu;
    }

    /**
     * Method description
     *
     */
    public void smenu() {
        menu("menu_office.xml", "Tablegroup - ELEMENTS - CompanyBranches");
    }

    /**
     * Method description
     *
     *
     * @param xmlname
     * @param constrolgroup
     */
    public void smenu(String xmlname, String constrolgroup) {
        menu(xmlname, constrolgroup);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void crcar(String name) {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(0.0D, 0.0D, 0.0D);
        actorveh car = eng.CreateCarImmediatly(name, M, pos);

        Crew.addMappedCar("DAKOTA", car);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     */
    public void mission(String mission_name) {
        MissionSystemInitializer.getMissionsManager().load_mission_debug(mission_name);
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     *
     * @return
     */
    public boolean missionstart(String mission_name) {
        return MissionSystemInitializer.getMissionsManager().load_mission_debug(mission_name);
    }

    /**
     * Method description
     *
     */
    public void inoffice() {
        new Office().debug_scene();
    }

    /**
     * Method description
     *
     */
    public void create_pass() {}

    /**
     * Method description
     *
     */
    public void create_org() {
        OrganiserMenu.create();
    }

    /**
     * Method description
     *
     */
    public void makecar() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(6049.0D, -24268.0D, 15.0D);

        car_park = eng.CreateCarImmediatly("PETERBILT_379", M, pos);
    }

    /**
     * Method description
     *
     */
    public void makecar2() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(6049.0D, -24268.0D, 15.0D);

        car_park = eng.CreateCarImmediatly("PETERBILT_387", M, pos);
    }

    /**
     * Method description
     *
     */
    public void makecar3() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(6049.0D, -24268.0D, 15.0D);

        car_park = eng.CreateCarImmediatly("KENWORTH_T600W", M, pos);
    }

    /**
     * Method description
     *
     */
    public void makecar4() {
        matrixJ M = new matrixJ();
        vectorJ pos = new vectorJ(6049.0D, -24268.0D, 15.0D);

        car_park = eng.CreateCarImmediatly("KENWORTH_K100E", M, pos);
    }

    /**
     * Method description
     *
     */
    public void notifytest() {
        MenuCall menu = MenuCall.create();

        menu.setItem(new Dialogitem(Crew.getIgrok().createCBContacter(), "message"));
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void debug_set_rating(int value) {
        RateSystem.DEBUGsetLiveRating(value);
    }

    /**
     * Method description
     *
     */
    public void message() {
        PanelMenu.PanelTenderDefaulted("Some", 1, 1);
    }

    /**
     * Method description
     *
     *
     * @param model_name
     */
    public void ped1(String model_name) {
        PedestrianManager.getInstance().removeNamedModel(model_name);
    }

    /**
     * Method description
     *
     */
    public void ped2() {
        PedestrianManager.getInstance().setPopulation(10);
    }

    /**
     * Method description
     *
     */
    public void ped3() {
        PedestrianManager.getInstance().setPopulation(100);
    }

    /**
     * Method description
     *
     */
    public void ped4() {
        PedestrianManager.getInstance().setPopulation(1);
    }

    /**
     * Method description
     *
     */
    public void testcar1() {
        vectorJ pos = new vectorJ(4315.0D, 12620.0D, 286.0D);
        actorveh car = eng.CreateCar_onway("FREIGHTLINER_ARGOSY", pos);
        vectorJ postunnel = eng.getControlPointPosition("EnemyBaseTunnel");

        car.autopilotTo(postunnel);
    }

    /**
     * Method description
     *
     */
    public void testcar2() {
        actorveh car = Crew.getIgrokCar();
        actorveh dakotacar = eng.CreateCarImmediatly("PETERBILT_379", car.gMatrix(),
                                 car.gPosition().oPlusN(new vectorJ(0.0D, 30.0D, 0.0D)));

        Crew.addMappedCar("DAKOTA", dakotacar);

        actorveh johncar = eng.CreateCarImmediatly("GMC_TOPKICK4500_JOHN_TRAMPLIN", car.gMatrix(),
                               car.gPosition().oPlusN(new vectorJ(0.0D, -30.0D, 0.0D)));

        Crew.addMappedCar("JOHN", johncar);
    }

    /**
     * Method description
     *
     */
    public void dakotadriver() {
        actorveh dakotacar = Crew.getMappedCar("DAKOTA");
        aiplayer dakota = new aiplayer("SC_ONTANIELOLOW");

        dakota.setModelCreator(new ScenarioPersonPassanger(), null);
        dakota.beDriverOfCar(dakotacar);
    }

    /**
     * Method description
     *
     */
    public void testcar3() {
        actorveh dakotacar = Crew.getMappedCar("DAKOTA");

        dakotacar.registerCar("DAKOTA");

        actorveh johncar = Crew.getMappedCar("JOHN");

        johncar.registerCar("JOHN");
        test2030();
    }

    /**
     * Method description
     *
     */
    public void testcar4() {
        actorveh police1 = eng.CreateCarImmediatly("Ford_CV_police", new matrixJ(), new vectorJ());

        police1.UpdateCar();
        police1.registerCar("police1");

        actorveh police2 = eng.CreateCarImmediatly("Ford_CV_police", new matrixJ(), new vectorJ(100.0D, 0.0D, 0.0D));

        police2.UpdateCar();
        police2.registerCar("police2");
    }

    private matrixJ testassault_getMatrix() {
        return new matrixJ();
    }

    private vectorJ testassault_getPosition() {
        return eng.getControlPointPosition("EnemyBaseAssaultRoot");
    }

    /**
     * Method description
     *
     */
    public void testassault1() {
        Vector v = new Vector();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");

        v.add(new SceneActorsPool("baza", person));

        Data _data = new Data(testassault_getMatrix(), testassault_getPosition(), null);

        trackscripts.CreateSceneXML("02040a_part1", v, _data);
    }

    /**
     * Method description
     *
     */
    public void testassault2() {
        Vector v = new Vector();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");

        v.add(new SceneActorsPool("baza", person));

        Data _data = new Data(testassault_getMatrix(), testassault_getPosition(), null);

        trackscripts.CreateSceneXMLCycle("02040a_part2", v, _data);
    }

    /**
     * Method description
     *
     */
    public void testassault3() {
        Vector v = new Vector();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");

        v.add(new SceneActorsPool("baza", person));

        Data _data = new Data(testassault_getMatrix(), testassault_getPosition(), null);

        trackscripts.CreateSceneXML("02040a_part3", v, _data);
    }

    /**
     * Method description
     *
     */
    public void enemybase_assault() {
        EnemyBaseDebug.assault();
    }

    /**
     * Method description
     *
     */
    public void changecar() {
        vectorJ ipos = rnrscr.Helper.getCurrentPosition();

        ipos.x += 100.0D;
        ipos.y -= 100.0D;

        actorveh car = eng.CreateCarForScenario(CarName.CAR_GEPARD, new matrixJ(), ipos);
        vectorJ pos_1 = car.gPosition();
        matrixJ mat_1 = car.gMatrix();
        vehicle gepard = car.takeoff_currentcar();
        actorveh ourcar = Crew.getIgrokCar();

        vehicle.changeLiveVehicle(ourcar, gepard, mat_1, pos_1);
        car.deactivate();
    }

    /**
     * Method description
     *
     *
     * @param carname
     */
    public void changecar(String carname) {
        vectorJ ipos = rnrscr.Helper.getCurrentPosition();

        ipos.x += 100.0D;
        ipos.y -= 100.0D;

        actorveh car = eng.CreateCarImmediatly(carname, new matrixJ(), ipos);
        vectorJ pos_1 = car.gPosition();
        matrixJ mat_1 = car.gMatrix();
        vehicle gepard = car.takeoff_currentcar();
        actorveh ourcar = Crew.getIgrokCar();

        vehicle.changeLiveVehicle(ourcar, gepard, mat_1, pos_1);
        car.deactivate();
    }

    /**
     * Method description
     *
     */
    public void testbarcrash() {
        Vector v = new Vector();
        SCRuniperson person = SCRuniperson.createLoadedObject("BarIn4");

        v.add(new SceneActorsPool("bar", person));

        Data _data = new Data(new matrixJ(), new vectorJ(), null);

        trackscripts.CreateSceneXML("02050", v, _data);
    }

    /**
     * Method description
     *
     */
    public void test1620() {
        ScenarioSync.setPlayScene("sc01620");
    }

    /**
     * Method description
     *
     */
    public void test2030() {
        ScenarioSync.setPlayScene("sc02030");
    }

    /**
     * Method description
     *
     */
    public void test2045() {
        ScenarioSync.setPlayScene("sc02045");
    }

    /**
     * Method description
     *
     */
    public void test2050_part2() {
        double delitel = 1.0D;
        matrixJ M = new matrixJ();

        M.v0 = new vectorJ(-Math.cos(3.141592653589793D / delitel), Math.cos(3.141592653589793D / delitel), 0.0D);
        M.v1 = new vectorJ(-Math.cos(3.141592653589793D / delitel), -Math.cos(3.141592653589793D / delitel), 0.0D);
        M.v2 = new vectorJ(0.0D, 0.0D, 1.0D);

        vectorJ pos = new vectorJ();
        Data _data = new Data(M, pos, null);

        trackscripts.CreateSceneXML("02050_part2", null, _data);
    }

    /**
     * Method description
     *
     */
    public void test2065() {
        ScenarioSync.setPlayScene("sc02065");
    }

    /**
     * Method description
     *
     */
    public void test1370() {
        ScenarioSync.setPlayScene("sc01370");
    }

    /**
     * Method description
     *
     */
    public void test1370_1() {
        ScenarioSync.setPlayScene("sc01370_1");
    }

    /**
     * Method description
     *
     */
    public void test3000() {
        ScenarioSync.setPlayScene("sc03000");
    }

    /**
     * Method description
     *
     */
    public void dakotashoot() {
        new ResqueDorothyShootAnimate(null, Crew.getIgrokCar(), false);
    }

    /**
     * Method description
     *
     */
    public void totalvictory() {
        TotalVictoryMenu.createGameOverTotal(null);
    }

    /**
     * Method description
     *
     */
    public void test1380() {
        ScenarioSync.setPlayScene("sc01380");
    }

    /**
     * Method description
     *
     */
    public void test_1() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 40.0D, 0);
    }

    /**
     * Method description
     *
     */
    public void test_2() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 40.0D, 3);
    }

    /**
     * Method description
     *
     */
    public void test_3() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 40.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void test_4() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 40.0D, 2);
    }

    /**
     * Method description
     *
     */
    public void createLooseEconomy() {
        VictoryMenu.createLooseEconomy(null);
    }

    /**
     * Method description
     *
     */
    public void createLooseSocial() {
        VictoryMenu.createLooseSocial(null);
    }

    /**
     * Method description
     *
     */
    public void createLooseSport() {
        VictoryMenu.createLooseSport(null);
    }

    /**
     * Method description
     *
     */
    public void createWinEconomy() {
        VictoryMenu.createWinEconomy(null);
    }

    /**
     * Method description
     *
     */
    public void createWinSocial() {
        VictoryMenu.createWinSocial(null);
    }

    /**
     * Method description
     *
     */
    public void createWinSport() {
        VictoryMenu.createWinSport(null);
    }

    /**
     * Method description
     *
     */
    public void testgun() {
        aiplayer GUN = aiplayer.getSimpleAiplayer("SC_BANDITGUN");
        IdentiteNames info = new IdentiteNames("SC_BANDITJOE");

        if (!(eng.noNative)) {
            JavaEvents.SendEvent(57, 1, info);
        }

        GUN.bePassangerOfCar_simple_like(Crew.getIgrokCar(), info.modelName);
    }

    /**
     * Method description
     *
     */
    public void test100() {
        ScenarioSync.setPlayScene("sc00100");
    }

    /**
     * Method description
     *
     */
    public void test1640() {
        ScenarioSync.setPlayScene("sc01640");
    }

    /**
     * Method description
     *
     */
    public void testmission1() {
        StartScenarioMissionAction action = new StartScenarioMissionAction();

        action.name = "m00040";
        action.act();
    }

    /**
     * Method description
     *
     */
    public void testmission2() {
        MissionEventsMaker.changeMissionDestination("m00040", "MP_Bar_LA_LB_01");
    }

    /**
     * Method description
     *
     */
    public void testmission3() {
        MissionEventsMaker.changeMissionDestination("m00040", "MP_MT_SR_03");
    }

    /**
     * Method description
     *
     */
    public void test610() {
        ScenarioSync.setPlayScene("sc00610");
    }

    /**
     * Method description
     *
     */
    public void testmission_semitrailer() {
        MissionSystemInitializer.getMissionsManager().activateAsideMission("sc00610");
    }

    /**
     * Method description
     *
     */
    public void testmission_semitrailer_1() {
        MissionDialogs.sayAppear("sc00610_start_channel");
    }

    /**
     * Method description
     *
     */
    public void test_bankrupt() {
        EventsControllerHelper.messageEventHappened("bankrupt");
    }

    /**
     * Method description
     *
     */
    public void test_scenario_bankrupt() {
        EventsControllerHelper.messageEventHappened("scenario_bankrupt");
    }

    /**
     * Method description
     *
     */
    public void test_1080() {
        ScenarioSync.setPlayScene("sc01080");
    }

    /**
     * Method description
     *
     */
    public void testcam1() {
        SCRcamera cam_veh1 = SCRcamera.CreateCamera("anm");
        String CAM_VEHname2 = "camera_RUL10";
        matrixJ mrotVEH = new matrixJ();

        mrotVEH.v0.Set(0.0D, 1.0D, 0.0D);
        mrotVEH.v1.Set(0.0D, 0.0D, 1.0D);
        mrotVEH.v2.Set(1.0D, 0.0D, 0.0D);

        matrixJ mrotcamVEH = new matrixJ();

        mrotcamVEH.v0.Set(0.0D, 0.0D, -1.0D);
        mrotcamVEH.v1.Set(0.0D, 1.0D, 0.0D);
        mrotcamVEH.v2.Set(1.0D, 0.0D, 0.0D);
        cam_veh1.AddAnimation(CAM_VEHname2, "space_MDL-" + CAM_VEHname2, mrotVEH, mrotcamVEH);
        cam_veh1.BindToVehicleSteerWheel(Crew.getIgrokCar().getCar());
        cam_veh1.PlayCamera();
    }

    /**
     * Method description
     *
     */
    public void test_1100() {
        eng.console("stats rating 100");
        EventsControllerHelper.messageEventHappened("big_race_scenario");
        ScenarioSync.setPlayScene("sc01100");
    }

    /**
     * Method description
     *
     */
    public void bigraceannounceevent() {
        ExternalChannelSayAppear appear_action = new ExternalChannelSayAppear();

        appear_action.name = "BigRaceAnnounce1nom1 startchannel";
        appear_action.act();
    }

    /**
     * Method description
     *
     */
    public void test_430() {
        ScenarioSync.setPlayScene("sc00430");
    }

    /**
     * Method description
     *
     */
    public void test_1030() {
        ScenarioSync.setPlayScene("sc01030");
    }

    /**
     * Method description
     *
     */
    public void test_1420() {
        ScenarioSync.setPlayScene("sc01420");
    }

    /**
     * Method description
     *
     */
    public void test1540() {
        ScenarioSync.setPlayScene("sc01540");
    }

    /**
     * Method description
     *
     */
    public void test_events_topo() {
        eng.setdword("DWORD_TopoQuest_Events", 1);
    }

    /**
     * Method description
     *
     */
    public void test_topoquest() {
        ChaseTopoDebug.allTopo();
    }

    /**
     * Method description
     *
     */
    public void test_topochase() {
        ChaseTopoDebug.simplechase();
    }

    /**
     * Method description
     *
     */
    public void test_topotestchase() {
        ChaseTopoDebug.simpliestchase();
    }

    /**
     * Method description
     *
     */
    public void test_toporacebandits() {
        ChaseTopoDebug.testContestStalkers();
    }

    /**
     * Method description
     *
     */
    public void test_dark_truck() {
        ChaseTopoDebug.darkTruck();
    }

    /**
     * Method description
     *
     */
    public void test_topo_friends() {
        ChaseTopoDebug.createFriends();
    }

    /**
     * Method description
     *
     */
    public void test_topo_trailers() {
        ChaseTopoDebug.justFriends();
    }

    /**
     * Method description
     *
     */
    public void test_topo_friends_finish() {
        ChaseTopoDebug.friendsFinish();
    }

    /**
     * Method description
     *
     */
    public void test_topo_friends_ride() {
        ChaseTopoDebug.friendsRide();
    }

    /**
     * Method description
     *
     */
    public void repair() {
        JavaEvents.SendEvent(43, 0, Crew.getIgrokCar());
    }

    /**
     * Method description
     *
     */
    public void repairbody() {
        JavaEvents.SendEvent(43, 1, Crew.getIgrokCar());
    }

    /**
     * Method description
     *
     */
    public void repairglass() {
        JavaEvents.SendEvent(43, 2, Crew.getIgrokCar());
    }

    /**
     * Method description
     *
     */
    public void cycletrace1() {
        eng.console("tracetopoint MP_bar_MJ_BS_01 cycletrace2");
    }

    /**
     * Method description
     *
     */
    public void cycletrace2() {
        eng.console("tracetopoint MP_bar_BS_01 cycletrace3");
    }

    /**
     * Method description
     *
     */
    public void cycletrace3() {
        eng.console("tracetopoint MP_gs_MJ_03 cycletrace1");
    }

    /**
     * Method description
     *
     */
    public void create_office() {
        OfficeMenu.create();
    }

    /**
     * Method description
     *
     *
     * @param scenename
     */
    public void create_cam_scene(String scenename) {
        actorveh car = Crew.getIgrokCar();

        car.registerCar("ourcar");

        create_passdata data = new create_passdata();

        data.car = car;
        scenetrack.CreateSceneXML(scenename, 17, data);
    }

    /**
     * Method description
     *
     */
    public void barstand1() {
        Bar.getInstance().startDialog("dia0");
    }

    /**
     * Method description
     *
     */
    public void barstand2() {
        Bar.getInstance().startDialog("dia1");
    }

    /**
     * Method description
     *
     */
    public void barstand3() {
        Bar.getInstance().startDialog("dia2");
    }

    /**
     * Method description
     *
     */
    public void barstand4() {
        Bar.getInstance().endDialog("dia0");
    }

    /**
     * Method description
     *
     */
    public void barstand5() {
        Bar.getInstance().endDialog("dia1");
    }

    /**
     * Method description
     *
     */
    public void barstand6() {
        Bar.getInstance().endDialog("dia2");
    }

    /**
     * Method description
     *
     */
    public void camout1() {
        SOscene.setOutSceneNum(0);
    }

    /**
     * Method description
     *
     */
    public void camout2() {
        SOscene.setOutSceneNum(1);
    }

    /**
     * Method description
     *
     */
    public void camout3() {
        SOscene.setOutSceneNum(2);
    }

    /**
     * Method description
     *
     */
    public void camout4() {
        SOscene.setOutSceneNum(3);
    }

    /**
     * Method description
     *
     */
    public void camout5() {
        SOscene.setOutSceneNum(4);
    }

    /**
     * Method description
     *
     */
    public void camout6() {
        SOscene.setOutSceneNum(5);
    }

    /**
     * Method description
     *
     */
    public void camout7() {
        SOscene.setOutSceneNum(6);
    }

    /**
     * Method description
     *
     */
    public void camout8() {
        SOscene.setOutSceneNum(7);
    }

    /**
     * Method description
     *
     */
    public void camout9() {
        SOscene.setOutSceneNum(8);
    }

    /**
     * Method description
     *
     */
    public void camin1() {
        SOscene.setInSceneNum(0);
    }

    /**
     * Method description
     *
     */
    public void camin2() {
        SOscene.setInSceneNum(1);
    }

    /**
     * Method description
     *
     */
    public void camin3() {
        SOscene.setInSceneNum(2);
    }

    /**
     * Method description
     *
     */
    public void camin4() {
        SOscene.setInSceneNum(3);
    }

    /**
     * Method description
     *
     */
    public void camin5() {
        SOscene.setInSceneNum(4);
    }

    /**
     * Method description
     *
     */
    public void camin6() {
        SOscene.setInSceneNum(5);
    }

    /**
     * Method description
     *
     */
    public void camin7() {
        SOscene.setInSceneNum(6);
    }

    /**
     * Method description
     *
     */
    public void camin8() {
        SOscene.setInSceneNum(7);
    }

    /**
     * Method description
     *
     */
    public void camin9() {
        SOscene.setInSceneNum(8);
    }

    /**
     * Method description
     *
     */
    public void camin10() {
        SOscene.setInSceneNum(9);
    }

    /**
     * Method description
     *
     */
    public void camin11() {
        SOscene.setInSceneNum(10);
    }

    /**
     * Method description
     *
     */
    public void camin12() {
        SOscene.setInSceneNum(11);
    }

    /**
     * Method description
     *
     */
    public void camin13() {
        SOscene.setInSceneNum(12);
    }

    /**
     * Method description
     *
     */
    public void camin14() {
        SOscene.setInSceneNum(13);
    }

    /**
     * Method description
     *
     */
    public void blow_car() {
        Preset blowpreset = new Preset();

        blowpreset.car = Crew.getIgrokCar();
        scenetrack.CreateSceneXML("blowcar", 1, blowpreset);
    }

    /**
     * Method description
     *
     */
    public void createtrailer() {
        actorveh ourcar = Crew.getIgrokCar();
        vectorJ dir = ourcar.gDir();

        dir.mult(-20.0D);

        vectorJ pos = ourcar.gPosition();

        pos.oPlus(dir);

        semitrailer tr = semitrailer.create("model_Flat_bed_cargo3", ourcar.gMatrix(), pos);

        ourcar.attach(tr);
    }

    /**
     * Method description
     *
     */
    public void play_out_of_car() {
        SCRuniperson person = Crew.getIgrok().getModel();
        long task = eng.CreateTASK(person);
        long startEvent = eng.AddEventTask(task);
        long timeEvent = eng.AddEventTask(task);
        long deleteTask = eng.AddScriptTask(task, new DeleteTask(task));
        long barWorld = eng.AddChangeWorldTask(task, "bar", "simple");
        long gameWorld = eng.AddChangeWorldTask(task, "game", "simple");
        SOscene SC = new SOscene();

        SC.task = task;
        SC.person = person;
        SC.actor = Crew.getIgrok();
        SC.vehicle = Crew.getIgrokCar();

        CarInOutTasks CAR_out = SC.makecaroutOnStart(startEvent, false);
        CarInOutTasks CAR_in = SC.makecarinOnEnd(gameWorld);

        SC.restorecameratoCarOnEnd(CAR_in.getMTaskToWait());
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", deleteTask);
        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", barWorld);
        eng.OnEndTASK(barWorld, "play", timeEvent);
        eng.OnMidTASK(timeEvent, 2.0D, 2.0D, "play", gameWorld);
        eng.playTASK(startEvent);
    }

    /**
     * Method description
     *
     */
    public void play_out_of_car_simple() {
        SCRuniperson person = Crew.getIgrok().getModel();
        long task = eng.CreateTASK(person);
        long startEvent = eng.AddEventTask(task);
        long endEvent = eng.AddEventTask(task);
        long deleteTask = eng.AddScriptTask(task, new DeleteTask(task));
        long timeEvent = eng.AddEventTask(task);
        SOscene SC = new SOscene();

        SC.task = task;
        SC.person = person;
        SC.actor = Crew.getIgrok();
        SC.vehicle = Crew.getIgrokCar();

        CarInOutTasks CAR_out = SC.makecaroutOnStart(startEvent, false);

        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", timeEvent);
        eng.OnMidTASK(timeEvent, 5.0D, 5.0D, "play", endEvent);
        eng.OnBegTASK(endEvent, "play", CAR_out.getMTaskToDeleteAll());
        eng.OnBegTASK(endEvent, "play", deleteTask);
        eng.playTASK(startEvent);
    }

    void debug_stage(String sceneName) {
        stage.performDebugPrePostConditions();

        if (sc00520.class.getName().endsWith(sceneName)) {
            ScenarioSync.setPlayScene(sceneName, 0);
        } else {
            ScenarioSync.setPlayScene(sceneName);
        }
    }

    void test_finish_mission(String missionName) {
        event.finishScenarioMission(missionName);
    }

    void test_pool() {
        new cTestDriversPools();
    }

    void schedule_big_race() {
        BigRace.gReference().DEBUGscheduleRace();
    }

    void test_esc_menu_cycle() {
        CycleMenuCreate.makeTest();
    }

    void allocationsDump() {}

    void gcgcgc() {
        System.gc();
        System.gc();
        System.gc();
    }

    /**
     * Method description
     *
     */
    public void TravelWorldCallBack() {
        JavaEvents.SendEvent(71, 7, this);
    }

    void dead() {
        rnrcore.Helper.peekNativeMessage("dead black screen");
    }

    void engine() {
        Crew.getIgrokCar().changeEngine("BANDITS_ENGINE");
    }

    void create_register_61cars() {
        vectorJ pos = eng.getControlPointPosition("Room_Repair_Oxnard_01");

        assert(pos != null);

        actorveh car = eng.CreateCarForScenario(CarName.CAR_BANDITS, new matrixJ(), pos);

        Crew.addMappedCar("ARGOSY BANDIT", car);
        Crew.getIgrokCar().registerCar("ourcar");
    }

    void ruscene61() {
        actorveh carBandit = Crew.getMappedCar("ARGOSY BANDIT");

        carBandit.registerCar("banditcar");

        vectorJ pos = eng.getControlPointPosition("Room_Repair_Oxnard_01");

        assert(pos != null);

        Data data = new Data(new matrixJ(), pos, Crew.getIgrokCar());

        data.M_180 = new matrixJ();
        data.M_180.v0 = new vectorJ(-1.0D, 0.0D, 0.0D);
        data.M_180.v1 = new vectorJ(0.0D, -1.0D, 0.0D);
        scenetrack.CreateSceneXML("00061", 17, data);
    }

    void blowcar() {
        drvscripts.BlowScene(Crew.getIgrok(), Crew.getIgrokCar());
    }

    void check_stop_car() {
        actorveh car = Crew.getIgrokCar();

        car.sVeclocity(0.0D);

        Vector vec_participants = new Vector();

        vec_participants.add(car);
        actorveh.aligncars_inRaceFinish(vec_participants, "pp_race", 40.0D, 0.0D, 1, 0);
    }

    void check_pp_drivers() {
        actorveh car = Crew.getIgrokCar();
        vectorJ pos = car.gPosition();
        vectorJ dir = car.gDir();

        dir.mult(10.0D);
        pos.oPlus(dir);

        vectorJ posUp = new vectorJ(pos.x, pos.y, pos.z + 100.0D);
        vectorJ posDown = new vectorJ(pos.x, pos.y, pos.z - 100.0D);
        vectorJ resultPosition = Collide.collidePoint(posUp, posDown);
        aiplayer Bandit1 = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");
        aiplayer PiterPan = aiplayer.getSimpleAiplayer("SC_PITERPANLOW");
        actorveh banditsCar = eng.CreateCarForScenario(CarName.CAR_PITER_PAN, new matrixJ(), resultPosition);

        Crew.addMappedCar("ARGOSY BANDIT", banditsCar);
        Bandit1.bePassangerOfCar(banditsCar);
        PiterPan.beDriverOfCar(banditsCar);
    }

    void pp_final_race() {
        new PiterPanFinalrace("pp_race", 3, scenarioscript.script);
    }

    /**
     * Method description
     *
     */
    public void craete_winner_scene_test() {
        vectorJ pos = new vectorJ();

        rnrscr.Helper.getNearestGoodPoint(pos);
        new BigRaceCrowd().winnersAnimation(1, pos, new matrixJ());
    }

    /**
     * Method description
     *
     */
    public void test_change1() {
        Ithreadprocedure sceneThread = new MakeRaceInitialization();

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void test_change2() {
        Ithreadprocedure sceneThread = new ÑhageCarsBack();

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void test861mission() {
        StartScenarioMissionAction action = new StartScenarioMissionAction();

        action.name = "m00861";
        action.timer = "Start0861";
        action.act();
    }

    /**
     * Method description
     *
     */
    public void test_mimic() {
        SCRuniperson person1 = SCRuniperson.createNamedCutScenePerson("IVAN_NEW", "IVAN_NEWman1", "man1");
        SCRuniperson person2 = SCRuniperson.createNamedCutScenePerson("IVAN_NEW", "IVAN_NEWman2", "man2");
        Vector pool = new Vector();

        pool.add(new SceneActorsPool("man1", person1));
        pool.add(new SceneActorsPool("man2", person2));

        Data data = new Data(new matrixJ(), getNearCarPosition(), null);

        data.M_180 = new matrixJ();
        data.M_180.v0 = new vectorJ(0.0D, -1.0D, 0.0D);
        data.M_180.v1 = new vectorJ(1.0D, 0.0D, 0.0D);
        data.P2 = new vectorJ(data.P);
        data.P2.x += 0.5D;
        scene_test_mimic = scenetrack.CreateSceneXMLPool("testmimic", 517, pool, data);
    }

    /**
     * Method description
     *
     */
    public void test_mimic_stop() {
        if (scene_test_mimic != 0L) {
            scenetrack.DeleteScene(scene_test_mimic);
        }
    }

    /**
     * Method description
     *
     */
    public void test_bar_empty() {
        eng.setParallelWorldCondition("bar", "bar_empty");
    }

    /**
     * Method description
     *
     */
    public void test_bar_full() {
        eng.setParallelWorldCondition("bar", "bar_full");
    }

    /**
     * Method description
     *
     *
     * @param flag_name
     */
    public void set_sc_flag_true(String flag_name) {
        ScenarioFlagsManager.getInstance().setFlagValue(flag_name, true);
    }

    /**
     * Method description
     *
     *
     * @param flag_name
     */
    public void set_sc_flag_false(String flag_name) {
        ScenarioFlagsManager.getInstance().setFlagValue(flag_name, false);
    }

    private String getPhrase() {
        int index = AdvancedRandom.RandFromInreval(0, Phrases.length - 1);

        return Phrases[index];
    }

    /**
     * Method description
     *
     */
    public void meet_client() {
        SCRuniperson person = SCRuniperson.createNamedCutScenePerson("Man_001HiPoly", "Client", "man");
        Vector pool = new Vector();

        pool.add(new SceneActorsPool("man", person));

        Data data = new Data(new matrixJ(), getNearCarPosition(), null);

        data.Phrase = getPhrase();

        long scene = scenetrack.CreateSceneXMLPool("meet_client", 17, pool, data);
        int nomManAnim = AdvancedRandom.RandFromInreval(0, 2);

        scenetrack.ChangeClipParam(scene, "Man", "MAN_MeetClient_listen_Clip", this, (nomManAnim == 0)
                ? "setClipActive"
                : "setClipPassive");
        scenetrack.ChangeClipParam(scene, "Man", "MAN_MeetClient_talk_Clip", this, (nomManAnim == 1)
                ? "setClipActive"
                : "setClipPassive");
        scenetrack.ChangeClipParam(scene, "Man", "MAN_MeetClient_talk_with_shlders_Clip", this, (nomManAnim == 2)
                ? "setClipActive"
                : "setClipPassive");

        int nomIvanAnim = AdvancedRandom.RandFromInreval(0, 1);

        scenetrack.ChangeClipParam(scene, "IVAN_NEW", "IVAN_MEW_MeetClient_listen_hand_Clip", this, (nomIvanAnim == 0)
                ? "setClipActive"
                : "setClipPassive");
        scenetrack.ChangeClipParam(scene, "IVAN_NEW", "IVAN_MEW_MeetClient_listen_2_Clip", this, (nomIvanAnim == 1)
                ? "setClipActive"
                : "setClipPassive");

        int nomCamAnim = AdvancedRandom.RandFromInreval(0, 1);

        scenetrack.ChangeClipParam(scene, "CamS_MeetClient", "CamS_to_NPC_near_Clip", this, (nomCamAnim == 0)
                ? "setClipActive"
                : "setClipPassive");
        scenetrack.ChangeClipParam(scene, "CamS_to_NPC_far", "CamS_to_NPC_near_Clip", this, (nomCamAnim == 1)
                ? "setClipActive"
                : "setClipPassive");
    }

    /**
     * Method description
     *
     *
     * @param pars
     */
    public void setClipActive(camscripts.trackclipparams pars) {
        pars.track_mute = false;
    }

    /**
     * Method description
     *
     *
     * @param pars
     */
    public void setClipPassive(camscripts.trackclipparams pars) {
        pars.track_mute = true;
    }

    /**
     * Method description
     *
     *
     * @param dialog_name
     * @param identitie
     */
    public void meet_client(String dialog_name, String identitie) {
        Dialog.getDialog(dialog_name).start_simplePoint(Crew.getIgrok().getModel(), identitie, new matrixJ(),
                         getNearCarPosition());
    }

    /**
     * Method description
     *
     */
    public void debug_sc00009() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                actorveh car = Crew.getIgrokCar();

                car.teleport(sc00009.officePos);
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc00009");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00011() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                actorveh car = Crew.getIgrokCar();

                car.teleport(sc00009.officePos);
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc00011");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    private vectorJ getNearCarPosition() {
        actorveh car = Crew.getIgrokCar();
        vectorJ pos = car.gPosition();
        vectorJ dir = car.gDir();

        dir.mult(10.0D);
        pos.oPlus(dir);

        vectorJ posUp = new vectorJ(pos.x, pos.y, pos.z + 100.0D);
        vectorJ posDown = new vectorJ(pos.x, pos.y, pos.z - 100.0D);
        vectorJ resultPosition = Collide.collidePoint(posUp, posDown);

        if (resultPosition == null) {
            resultPosition = pos;
        }

        return resultPosition;
    }

    /**
     * Method description
     *
     */
    public void debug_sc00030() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                actorveh carcoh = eng.CreateCarForScenario(CarName.CAR_COCH, new matrixJ(),
                                      test.this.getNearCarPosition());

                Crew.addMappedCar("KOH", carcoh);

                if (null == KohHelpManage.getInstance()) {
                    KohHelpManage.constructSingleton(scenarioscript.script);
                }

                if (null == KohHelpManage.getInstance().getNickLocation()) {
                    KohHelpManage.getInstance().debugSetNickLocation();
                }

                eng.unlock();
                rnrscenario.tech.Helper.waitLoaded(carcoh);
                eng.lock();
                test.this.debug_stage("sc00030");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00031() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                actorveh carcoh = eng.CreateCarForScenario(CarName.CAR_COCH, new matrixJ(),
                                      test.this.getNearCarPosition());

                Crew.addMappedCar("KOH", carcoh);

                if (null == KohHelpManage.getInstance()) {
                    KohHelpManage.constructSingleton(scenarioscript.script);
                }

                if (null == KohHelpManage.getInstance().getNickLocation()) {
                    KohHelpManage.getInstance().debugSetNickLocation();
                }

                eng.unlock();
                rnrscenario.tech.Helper.waitLoaded(carcoh);
                eng.lock();
                test.this.debug_stage("sc00031");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00032() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                actorveh carcoh = eng.CreateCarForScenario(CarName.CAR_COCH, new matrixJ(),
                                      test.this.getNearCarPosition());

                Crew.addMappedCar("KOH", carcoh);

                if (null == KohHelpManage.getInstance()) {
                    KohHelpManage.constructSingleton(scenarioscript.script);
                }

                if (null == KohHelpManage.getInstance().getNickLocation()) {
                    KohHelpManage.getInstance().debugSetNickLocation();
                }

                eng.unlock();
                rnrscenario.tech.Helper.waitLoaded(carcoh);
                eng.lock();
                test.this.debug_stage("sc00032");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00060_1() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = eng.getControlPointPosition("Oxnard_Bar_01");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();

                specobjects so = specobjects.getInstance();
                cSpecObjects ob = so.GetNearestLoadedParkingPlace_nearBarNamed("Room_Oxnard_Bar1", 200.0D);

                if (null != ob) {
                    actorveh banditsCar = eng.CreateCarForScenario(CarName.CAR_BANDITS, ob.matrix, ob.position);

                    banditsCar.UpdateCar();

                    if (0 == banditsCar.getCar()) {
                        eng.err("0==banditsCar.car in " + super.getClass().getName());
                    }

                    banditsCar.registerCar("banditcar");
                    Crew.addMappedCar("ARGOSY BANDIT", banditsCar);

                    parkingplace parking = parkingplace.findParkingByName("Oxnard_Parking_01", ob.position);

                    banditsCar.makeParking(parking, 4);
                }

                actorveh chaser = Crew.getMappedCar("ARGOSY BANDIT");

                test.access$102(test.this, new ResqueDorothyShootAnimate(null, chaser, false));
                test.this.debug_stage("sc00060part1");
                eng.unlock();
                NativeEventController.addNativeEventListener(new INativeMessageEvent() {
                    @Override
                    public String getMessage() {
                        return "debug_scene_finished";
                    }
                    @Override
                    public boolean removeOnEvent() {
                        return true;
                    }
                    @Override
                    public void onEvent(String message) {
                        test.this.m_debug_shootanimation.finish();

                        actorveh banditcar = Crew.getMappedCar("ARGOSY BANDIT");

                        Crew.removeMappedCar("ARGOSY BANDIT");
                        banditcar.deactivate();
                        chase00090.stopDorothyPassanger();
                    }
                });
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00065() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = eng.getControlPointPosition("Oxnard_Bar_01");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc00065");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00324_yes() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                sc00324.setDebugUnswer(true);
                eng.lock();
                test.this.debug_stage("sc00324");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00324_no() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                sc00324.setDebugUnswer(false);
                eng.lock();
                test.this.debug_stage("sc00324");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00465() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = RaceTrajectory.getFinish("pp_race");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();

                Vector vec_participants = new Vector();
                actorveh pl = Crew.getIgrokCar();

                pl.sVeclocity(0.0D);
                vec_participants.add(pl);
                actorveh.aligncars_inRaceFinish(vec_participants, "pp_race", 40.0D, 0.0D, 1, 0);
                eng.lock();
                eng.unlock();
                test.this.debug_stage("sc00465");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00520() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = RaceTrajectory.getFinish("pp_race");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();

                Vector vec_participants = new Vector();
                actorveh pl = Crew.getIgrokCar();

                pl.sVeclocity(0.0D);
                vec_participants.add(pl);
                actorveh.aligncars_inRaceFinish(vec_participants, "pp_race", 40.0D, 0.0D, 1, 0);
                eng.lock();
                eng.unlock();
                test.this.debug_stage("sc00520");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00530() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = RaceTrajectory.getFinish("pp_race");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();

                Vector vec_participants = new Vector();
                actorveh pl = Crew.getIgrokCar();

                pl.sVeclocity(0.0D);
                vec_participants.add(pl);
                actorveh.aligncars_inRaceFinish(vec_participants, "pp_race", 40.0D, 0.0D, 1, 0);
                eng.unlock();
                eng.lock();
                sc00530.setDebug();
                test.this.debug_stage("sc00530");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00730() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = new vectorJ(6358.0D, -20223.0D, 106.0D);
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();

                actorveh dakotacar = null;
                cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject("KeyPoint_730");

                if (null != crossroad) {
                    sc00730.setDebugMatrices(crossroad.position, crossroad.matrix);
                    dakotacar = eng.CreateCarForScenario(CarName.CAR_DAKOTA, crossroad.matrix, crossroad.position);

                    aiplayer dakota = aiplayer.getScenarioAiplayer("SC_ONTANIELO");

                    dakota.beDriverOfCar(dakotacar);
                    Crew.addMappedCar("DAKOTA", dakotacar);
                }

                eng.unlock();
                rnrscenario.tech.Helper.waitLoaded(dakotacar);
                eng.lock();

                actorveh banditcar = eng.CreateCarForScenario(CarName.CAR_BANDITS, new matrixJ(),
                                         test.this.getNearCarPosition());

                Crew.addMappedCar("ARGOSY BANDIT", banditcar);

                aiplayer pl = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");

                pl.beDriverOfCar(banditcar);

                aiplayer Bandit2 = aiplayer.getSimpleAiplayer("SC_BANDITJOELOW");
                aiplayer GUN = aiplayer.getSimpleAiplayer("SC_BANDITGUN");

                Bandit2.bePassangerOfCar(banditcar);
                GUN.bePassangerOfCar_simple_like(banditcar, Bandit2.gModelname());
                eng.unlock();
                rnrscenario.tech.Helper.waitLoaded(banditcar);
                eng.lock();
                Crew.getMappedCar("DAKOTA").registerCar("dakotacar");
                Crew.getMappedCar("ARGOSY BANDIT").registerCar("banditcar");
                test.this.debug_stage("sc00730");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00750() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = eng.getControlPointPosition("CP_126_5");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();

                cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();

                if ((crossroad != null) && (crossroad.name.compareToIgnoreCase("KeyPoint_750") == 0)) {
                    actorveh kohcar = eng.CreateCarForScenario(CarName.CAR_COCH, crossroad.matrix, crossroad.position);
                    vectorJ shift = new vectorJ(crossroad.matrix.v0);

                    shift.mult(5.0D);

                    actorveh dorcar = eng.CreateCarForScenario(CarName.CAR_DOROTHY, crossroad.matrix,
                                          crossroad.position.oPlusN(shift));

                    Crew.addMappedCar("DOROTHY", dorcar);
                    Crew.addMappedCar("KOH", kohcar);
                }

                eng.unlock();
                eng.lock();
                test.this.debug_stage("sc00750");
                eng.unlock();
                NativeEventController.addNativeEventListener(new INativeMessageEvent() {
                    @Override
                    public String getMessage() {
                        return "debug_scene_finished";
                    }
                    @Override
                    public boolean removeOnEvent() {
                        return true;
                    }
                    @Override
                    public void onEvent(String message) {
                        actorveh dorcar = Crew.getMappedCar("DOROTHY");
                        actorveh kohcar = Crew.getMappedCar("KOH");

                        Crew.removeMappedCar("DOROTHY");
                        Crew.removeMappedCar("KOH");
                        dorcar.deactivate();
                        kohcar.deactivate();
                    }
                });
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00830() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();
                sc00830.setDebugScene();

                vectorJ pos = eng.getControlPointPosition("CP_LB_TS");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();

                parkingplace parking = parkingplace.findParkingByName("PK_LA_LB_01", pos);
                matrixJ m = new matrixJ();
                vectorJ p = eng.getControlPointPosition("CP_LB_TS");
                vectorJ shift = new vectorJ(m.v0);

                shift.mult(5.0D);

                actorveh kohcar = eng.CreateCarForScenario(CarName.CAR_COCH, m, p);
                actorveh dorcar = eng.CreateCarForScenario(CarName.CAR_DOROTHY, m, p.oPlusN(shift));

                Crew.addMappedCar("DOROTHY", dorcar);
                Crew.addMappedCar("KOH", kohcar);
                kohcar.makeParking(parking, 6);
                dorcar.makeParking(parking, 7);
                eng.unlock();
                test.this.debug_stage("sc00830");
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00860() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();
                sc00860.setDebug();

                vectorJ pos = eng.getControlPointPosition("CP_meet_on_14");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();

                cSpecObjects crossroad = specobjects.getInstance().GetNearestLoadedScenarioObject();
                actorveh darktruck = null;

                if ((crossroad != null) && (crossroad.name.compareToIgnoreCase("KeyPoint_860") == 0)) {
                    darktruck = eng.CreateCarForScenario(CarName.CAR_MATHEW_DEAD, crossroad.matrix, crossroad.position);

                    Vector actors = new Vector();

                    actors.add(darktruck);
                    actorveh.aligncars(actors, crossroad.position, 15.0D, 10.0D, 1, 0);
                    Crew.addMappedCar("DARK TRUCK", darktruck);
                }

                eng.unlock();
                rnrscenario.tech.Helper.waitLoaded(darktruck);
                eng.lock();
                test.this.debug_stage("sc00860");
                eng.unlock();
                NativeEventController.addNativeEventListener(new INativeMessageEvent() {
                    @Override
                    public String getMessage() {
                        return "debug_scene_finished";
                    }
                    @Override
                    public boolean removeOnEvent() {
                        return true;
                    }
                    @Override
                    public void onEvent(String message) {
                        actorveh darktruck = Crew.getMappedCar("DARK TRUCK");

                        Crew.removeMappedCar("DARK TRUCK");
                        darktruck.deactivate();
                    }
                });
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc00930() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = eng.getControlPointPosition("Oxnard_Police");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc00930");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc01030_yes() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                sc01030.setDebugSceneAnswer(true);
                eng.lock();

                actorveh car = Crew.getIgrokCar();

                car.teleport(sc00009.officePos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01030");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc01030_no() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                sc01030.setDebugSceneAnswer(false);
                eng.lock();

                actorveh car = Crew.getIgrokCar();

                car.teleport(sc00009.officePos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01030");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc01100() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();
                sc01100.setDebugScene();

                vectorJ pos = eng.getControlPointPosition("John_House");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01100");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc01370() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = eng.getControlPointPosition("Cursed Highway Scene");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01370");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc01380() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = eng.getControlPointPosition("Cursed Highway Scene");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01380");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc01420() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                actorveh car = Crew.getIgrokCar();

                car.teleport(sc00009.officePos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01420");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc01540() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = eng.getControlPointPosition("Red Rock Canyon Scene");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01540");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc01541() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = eng.getControlPointPosition("Red Rock Canyon Scene");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01541");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc01620() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                actorveh car = Crew.getIgrokCar();

                car.teleport(new vectorJ(6979.0D, 14062.0D, 190.0D));
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01620");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc01640() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                actorveh car = Crew.getIgrokCar();

                car.teleport(new vectorJ(3618.4951169999999D, 13370.149047999999D, 320.98033099999998D));
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc01640");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc02025() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vectorJ pos = eng.getControlPointPosition("EnemyBaseTruckstop");
                actorveh car = Crew.getIgrokCar();

                car.teleport(pos);
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc02025");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc02030() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();
                eng.setdword("DWORD_EnemyBaseAssaultTeam", 0);
                eng.setdword("DWORD_EnemyBaseAssault", 1);

                actorveh car = Crew.getIgrokCar();

                car.teleport(new vectorJ(3856.2060299999998D, 13122.95883D, 317.61900000000003D));
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();

                vectorJ posAssault = eng.getControlPointPosition("LineUp2030");
                actorveh car1 = eng.CreateCarForScenario(CarName.CAR_JOHN, new matrixJ(), posAssault);
                actorveh car2 = eng.CreateCarForScenario(CarName.CAR_DAKOTA, new matrixJ(), posAssault);

                eng.unlock();
                rnrscenario.tech.Helper.waitLoaded(car1);
                eng.lock();
                car1.UpdateCar();
                car1.registerCar("JOHN");
                car1.setCollideMode(false);
                eng.unlock();
                rnrscenario.tech.Helper.waitLoaded(car2);
                eng.lock();
                car2.UpdateCar();
                car2.registerCar("DAKOTA");

                aiplayer dakota = new aiplayer("SC_ONTANIELOLOW");

                dakota.setModelCreator(new ScenarioPersonPassanger(), null);
                dakota.beDriverOfCar(car2);
                eng.unlock();
                eng.lock();
                test.this.debug_stage("sc02030");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc02040() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                actorveh car = Crew.getIgrokCar();

                car.teleport(new vectorJ(3856.2060299999998D, 13122.95883D, 317.61900000000003D));
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                sc02040.prepareScene(new Object());
                test.this.debug_stage("sc02040");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc02045() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                actorveh car = Crew.getIgrokCar();

                car.teleport(new vectorJ(3856.2060299999998D, 13122.95883D, 317.61900000000003D));
                eng.unlock();
                rnrscenario.tech.Helper.waitTeleport();
                eng.lock();
                test.this.debug_stage("sc02045");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_sc02050() {
        CrashBarScene.DEBUG = true;

        actorveh playerCar = Crew.getIgrokCar();

        playerCar.teleport(eng.getControlPointPosition("ChaseKohStart"));
        MakeTeleport.teleport(new ITeleported(playerCar) {
            @Override
            public void teleported() {
                vectorJ position = eng.getControlPointPosition("ChaseKohStart");
                parkingplace place = parkingplace.findParkingByName("pk_MW_01", position);

                this.val$playerCar.makeParking(place, 0);

                actorveh cochCar = eng.CreateCarForScenario(CarName.CAR_DAKOTA, new matrixJ(), position);
                aiplayer person_player = new aiplayer("SC_KOH");

                person_player.sPoolBased("koh_cut_scene_driver");
                person_player.setModelCreator(new CutSceneAuxPersonCreator(), "koh");
                person_player.beDriverOfCar(cochCar);

                SCRuniperson koh_person = person_player.getModel();

                koh_person.SetInWorld("bar_crash");
                Crew.addMappedCar("KOH", cochCar);
                eng.setdword("Dword_BarIn4_Crash", 1);
                CrashBarScene.DEBUG = true;
                new CrashBarScene().run();
            }
        });
        EventsController.getInstance().addListener(new EventListener() {
            @Override
            public void eventHappened(List<ScriptEvent> eventTuple) {
                if ((null == eventTuple) || (0 >= eventTuple.size()) || (!(eventTuple.get(0) instanceof MessageEvent))
                        || (0 != "finished scene sc02050".compareTo(((MessageEvent) eventTuple.get(0)).getMessage()))) {
                    return;
                }

                Crew.deactivateMappedCar("KOH");
                EventsController.getInstance().removeListener(this);
            }
        });
    }

    /**
     * Method description
     *
     */
    public void debug_sc02060() {
        actorveh kohcar = eng.CreateCarForScenario(CarName.CAR_DAKOTA, new matrixJ(), getNearCarPosition());

        Crew.addMappedCar("KOH", kohcar);
        sc02060.setDebugMode(true);
        debug_stage("sc02060");
        NativeEventController.addNativeEventListener(new INativeMessageEvent() {
            @Override
            public String getMessage() {
                return "debug_scene_finished";
            }
            @Override
            public boolean removeOnEvent() {
                return true;
            }
            @Override
            public void onEvent(String message) {
                actorveh cohcar = Crew.getMappedCar("SC_KOH");

                Crew.removeMappedCar("SC_KOH");
                cohcar.deactivate();
                sc02060.setDebugMode(false);
            }
        });
    }

    /**
     * Method description
     *
     */
    public void failIfScenarioAlive() {
        if (null == scenarioscript.script) {
            return;
        }

        if ((null != scenarioscript.script.getStage()) && (scenarioscript.script.getStage().isScenarioFinished())) {
            int currentScenarioStage = scenarioscript.script.getStage().getScenarioStage();

            eng.fatal(String.format("Scenario State is not maximal (%d)",
                                    new Object[] { Integer.valueOf(currentScenarioStage) }));
        }

        if (null == scenarioscript.script.getScenarioMachine()) {
            return;
        }

        Collection fsmActiveStates = scenarioscript.script.getScenarioMachine().getCurrentActiveStates();

        if (fsmActiveStates.isEmpty()) {
            return;
        }

        StringBuilder errorMessageComposer = new StringBuilder(1024);

        errorMessageComposer.append("Scenario FFSM has active states:");

        for (String stateName : fsmActiveStates) {
            errorMessageComposer.append(' ').append(stateName);
        }

        eng.fatal(errorMessageComposer.toString());
    }

    /**
     * Method description
     *
     */
    public void debug_sc02065() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                vehicle carGepard = vehicle.create("GEPARD", 0);

                carGepard.setLeased(true);

                actorveh car = Crew.getIgrokCar();
                matrixJ M = car.gMatrix();
                vectorJ P = test.this.getNearCarPosition();

                vehicle.changeLiveVehicle(car, carGepard, M, P);
                eng.unlock();
                rnrscenario.tech.Helper.waitVehicleChanged();
                eng.lock();
                test.this.debug_stage("sc02065");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    /**
     * Method description
     *
     */
    public void debug_final_1() {
        Ithreadprocedure sceneThread = new Ithreadprocedure() {
            @Override
            public void call() {
                eng.lock();

                List data = new ArrayList();

                data.add(new Pair(FactionRater.FACTION_NAMES[1], "0"));
                data.add(new Pair(FactionRater.FACTION_NAMES[0], "0"));
                FactionRater.setSerializationData(data);
                test.this.debug_stage("sc02070");
                eng.unlock();
            }
            @Override
            public void take(ThreadTask safe) {}
        };

        ThreadTask.create(sceneThread);
    }

    static class ChaseTopoTest extends TypicalAnm {

        /** Field description */
        public int mode = 2;

        /** Field description */
        public double dist_ahead0 = 10.0D;

        /** Field description */
        public double dist_ahead2 = 11.0D;

        /** Field description */
        public double dist_behind0 = -10.0D;

        /** Field description */
        public double dist_behind2 = -11.0D;

        /** Field description */
        public double minvel = 40.0D;

        /** Field description */
        public double maxvel = 60.0D;

        /** Field description */
        public boolean toRenew = true;
        actorveh chaser = null;
        actorveh flee = Crew.getIgrokCar();

        ChaseTopoTest() {
            eng.CreateInfinitScriptAnimation(this);
            this.chaser = eng.CreateCarForScenario(CarName.CAR_BANDITS, new matrixJ(), getPosBehind());
            this.chaser.sVeclocity(20.0D);
            Helper.makePowerEngine(this.chaser);
        }

        vectorJ getPosBehind() {
            vectorJ pos = this.flee.gDir();

            pos.mult(-100.0D);

            return pos.oPlusN(this.flee.gPosition());
        }

        void makeChaseParametrs() {
            Chase chase = new Chase();

            chase.mode = this.mode;
            chase.dist_ahead0 = this.dist_ahead0;
            chase.dist_ahead2 = this.dist_ahead2;
            chase.dist_behind0 = this.dist_behind0;
            chase.dist_behind2 = this.dist_behind2;
            chase.minvel = this.minvel;
            chase.maxvel = this.maxvel;
            chase.makechase(this.chaser, this.flee);
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
            if (this.toRenew) {
                makeChaseParametrs();
                this.toRenew = false;
            }

            return false;
        }
    }


    static class CycleMenuCreate extends TypicalAnm {
        private static final int MAX_CREATIONS = 300;
        private static final int DUMP_PERIOD = 10;
        private static CycleMenuCreate m_instance;

        static {
            m_instance = null;
        }

        private int m_mainMenuCreated = 0;
        private final int m_journalMenuCreated = 0;
        private int m_counter = 0;
        private boolean m_menuCreated = false;
        private long m_menuHandle = 0L;

        CycleMenuCreate() {
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
            if (300 < this.m_mainMenuCreated) {
                if (this.m_menuCreated) {
                    this.m_menuCreated = false;
                    menues.CallMenuCallBack_ExitMenu(this.m_menuHandle);
                    this.m_menuHandle = 0L;
                }

                return true;
            }

            if (++this.m_counter % 3 == 0) {
                this.m_counter = 0;

                if (this.m_menuCreated) {
                    assert(0L != this.m_menuHandle);
                    this.m_menuCreated = false;
                    menues.CallMenuCallBack_ExitMenu(this.m_menuHandle);
                    this.m_menuHandle = 0L;
                } else {
                    assert(0L == this.m_menuHandle);
                    this.m_menuCreated = true;
                    this.m_menuHandle = EscapeMenu.CreateEscapeMenu();
                    this.m_mainMenuCreated += 1;

                    if (0 == this.m_mainMenuCreated % 10) {
                        System.out.println("mainMenuCreated: " + this.m_mainMenuCreated);
                        System.out.println("journalMenuCreated: " + this.m_journalMenuCreated);
                    }
                }
            }

            return false;
        }

        static void makeTest() {
            if (null != m_instance) {
                return;
            }

            m_instance = new CycleMenuCreate();
        }
    }


    static class Data {
        matrixJ M;
        matrixJ M_180;
        vectorJ P;
        vectorJ P2;
        actorveh car;
        String Phrase;

        Data() {}

        Data(matrixJ M, vectorJ P, actorveh car) {
            this.M = M;
            this.P = P;
            this.car = car;
        }
    }


    class DeleteTask implements IScriptTask {
        private final long task;

        DeleteTask(long paramLong) {
            this.task = paramLong;
        }

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            eng.DeleteTASK(this.task);
        }
    }


    static class MakeRaceInitialization implements Ithreadprocedure {

        /**
         * Method description
         *
         */
        @Override
        public void call() {
            eng.lock();

            actorveh ourcar = Crew.getIgrokCar();
            matrixJ remM = ourcar.gMatrix();
            vectorJ remV = ourcar.gPosition();

            remV.x += 100.0D;

            vehicle gepard = vehicle.create("GEPARD", 0);

            gepard.setLeased(true);

            vehicle our_last_car = ourcar.querryCurrentCar();

            vehicle.changeLiveVehicle(ourcar, gepard, remM, remV);
            eng.unlock();
            rnrscenario.tech.Helper.waitVehicleChanged();
            eng.lock();
            Helper.placeLiveCarInGarage(our_last_car);
            eng.unlock();
        }

        /**
         * Method description
         *
         *
         * @param safe
         */
        @Override
        public void take(ThreadTask safe) {}
    }


    static class OfficeCam implements IScriptTask {

        /**
         * Method description
         *
         */
        @Override
        public void launch() {
            SCRcamera freeCam = SCRcamera.CreateCamera("free");

            freeCam.PlayCamera();
            freeCam.SetInOfficeWorld();

            vectorJ posit = new vectorJ(0.0D, 0.0D, 2.0D);

            freeCam.SetCameraPosition(posit);
        }
    }


    static class Preset {
        actorveh car;
    }


    class ShowOfficeMenu implements anm {
        private final ScriptRef uid;

        ShowOfficeMenu() {
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
            if (dt > 1.0D) {
                menues.showMenu(8000);

                return true;
            }

            return false;
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
    }


    private static final class cTestDriversPools extends TypicalAnm {
        cTestDriversPools() {
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
            Crew.getInstance().getCarCreationController().getPool().makePoolCycle();

            return false;
        }
    }


    static class create_passdata {
        actorveh car;
    }
}


//~ Formatted in DD Std on 13/08/26
