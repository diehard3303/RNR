/*
 * @(#)scenarioscript.java   13/08/28
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

import rnr.src.menu.JavaEvents;
import rnr.src.menu.menues;
import rnr.src.menu.resource.MenuResourcesManager;
import rnr.src.menu.restartgame;
import rnr.src.menuscript.TotalVictoryMenu;
import rnr.src.menuscript.VictoryMenu;
import rnr.src.menuscript.VictoryMenuExitListener;
import rnr.src.players.Crew;
import rnr.src.players.CrewNamesManager;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.GameXmlSerializator;
import rnr.src.rnrcore.Helper;
import rnr.src.rnrcore.INativeMessageEvent;
import rnr.src.rnrcore.NativeEventController;
import rnr.src.rnrcore.NativeSerializationInterface;
import rnr.src.rnrcore.ScenarioSync;
import rnr.src.rnrcore.ScriptRefStorage;
import rnr.src.rnrcore.UidStorage;
import rnr.src.rnrcore.XmlSerializable;
import rnr.src.rnrcore.XmlSerializationFabric;
import rnr.src.rnrcore.anm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrloggers.ScenarioLogger;
import rnr.src.rnrorg.Organizers;
import rnr.src.rnrorg.PickUpEventManager;
import rnr.src.rnrorg.RenewJourmalAfterLoad;
import rnr.src.rnrorg.XmlInit;
import rnr.src.rnrorg.journal;
import rnr.src.rnrorg.organaiser;
import rnr.src.rnrrating.BigRace;
import rnr.src.rnrrating.FactionRater;
import rnr.src.rnrrating.RateSystem;
import rnr.src.rnrscenario.config.Config;
import rnr.src.rnrscenario.config.ConfigManager;
import rnr.src.rnrscenario.configurators.AiChaseConfig;
import rnr.src.rnrscenario.configurators.ChaseCochConfig;
import rnr.src.rnrscenario.configurators.ChaseToRescueDorothyConfig;
import rnr.src.rnrscenario.configurators.EnemyBaseConfig;
import rnr.src.rnrscenario.configurators.SpecialCargoConfig;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.consistency.ScenarioGarbageFinder;
import rnr.src.rnrscenario.consistency.ScenarioStage;
import rnr.src.rnrscenario.consistency.StageChangedListener;
import rnr.src.rnrscenario.controllers.ChaseKoh;
import rnr.src.rnrscenario.controllers.EnemyBase;
import rnr.src.rnrscenario.controllers.KohHelpManage;
import rnr.src.rnrscenario.controllers.RaceFailCondition;
import rnr.src.rnrscenario.controllers.RedRockCanyon;
import rnr.src.rnrscenario.controllers.ScenarioController;
import rnr.src.rnrscenario.controllers.ScenarioHost;
import rnr.src.rnrscenario.controllers.chase00090;
import rnr.src.rnrscenario.controllers.chaseTopo;
import rnr.src.rnrscenario.controllers.preparesc00060;
import rnr.src.rnrscenario.controllers.starters.Start0430.Checker;
import rnr.src.rnrscenario.missions.DelayedResourceDisposer;
import rnr.src.rnrscenario.missions.MissionManager;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.starters.ConditionChecker;
import rnr.src.rnrscenario.missions.starters.StarterBase;
import rnr.src.rnrscenario.scenes.bankrupt;
import rnr.src.rnrscr.Dialog;
import rnr.src.rnrscr.ILeaveMenuListener;
import rnr.src.rnrscr.MissionDialogs;
import rnr.src.rnrscr.MissionPassanger;
import rnr.src.rnrscr.Office;
import rnr.src.rnrscr.PedestrianManager;
import rnr.src.rnrscr.RaceHistory;
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.rnrscr.cbapparatus;
import rnr.src.rnrscr.drvscripts;
import rnr.src.rnrscr.smi.Newspapers;
import rnr.src.rnrscr.specobjects;
import rnr.src.scenarioMachine.FiniteStateMachine;
import rnr.src.scenarioXml.CbvEventListenerBuilder;
import rnr.src.scenarioXml.InternalScenarioRepresentation;
import rnr.src.scenarioXml.XmlScenarioMachineBuilder;
import rnr.src.scriptActions.BlockSpecialObject;
import rnr.src.scriptActions.PoliceImmunity;
import rnr.src.scriptActions.SetScenarioFlag;
import rnr.src.scriptActions.TrafficAction;
import rnr.src.scriptEvents.EventListener;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.EventsControllerHelper;
import rnr.src.scriptEvents.ScriptEvent;
import rnr.src.scriptEvents.SpecialObjectEvent;
import rnr.src.scriptEvents.SpecialObjectEvent.EventType;
import rnr.src.scriptEvents.TimeEvent;
import rnr.src.scriptEvents.VoidEvent;
import rnr.src.xmlserialization.AIPlayerSerializator;
import rnr.src.xmlserialization.ActorVehSerializator;
import rnr.src.xmlserialization.AlbumSerialization;
import rnr.src.xmlserialization.BigRaceSerialization;
import rnr.src.xmlserialization.BlockedSOSerializator;
import rnr.src.xmlserialization.CBVideoSerializator;
import rnr.src.xmlserialization.Chase00090Serializator;
import rnr.src.xmlserialization.ChaseKohSerializator;
import rnr.src.xmlserialization.ChaseTopoSerializator;
import rnr.src.xmlserialization.CrewXmlSerialization;
import rnr.src.xmlserialization.CursedHiWaySerializator;
import rnr.src.xmlserialization.DelayedChannelSerializator;
import rnr.src.xmlserialization.EndScenarioSerialization;
import rnr.src.xmlserialization.EnemyBaseSerializator;
import rnr.src.xmlserialization.FactionRaterSerializator;
import rnr.src.xmlserialization.JournalSerialization;
import rnr.src.xmlserialization.KohHelpSerializator;
import rnr.src.xmlserialization.MissionPassangerSerializator;
import rnr.src.xmlserialization.MissionsInitiatedSerialization;
import rnr.src.xmlserialization.MissionsLogSerialization;
import rnr.src.xmlserialization.MissionsSerialization;
import rnr.src.xmlserialization.NewspaperSerialization;
import rnr.src.xmlserialization.ObjectXmlSerializator;
import rnr.src.xmlserialization.OrganizerSerialization;
import rnr.src.xmlserialization.PiterPanDoomedRaceSerializator;
import rnr.src.xmlserialization.PiterPanFinalRaceSerializator;
import rnr.src.xmlserialization.Preparesc00060Serializator;
import rnr.src.xmlserialization.RatingSerialization;
import rnr.src.xmlserialization.RedRockCanyonSerializator;
import rnr.src.xmlserialization.ScenarioFlagsSerializator;
import rnr.src.xmlserialization.ScriptRefXmlSerialization;
import rnr.src.xmlserialization.SoSerialization;
import rnr.src.xmlserialization.StaticScenarioStuffSerializator;
import rnr.src.xmlserialization.UidStorageSerialization;
import rnr.src.xmlserialization.nxs.SerializatorOfAnnotated;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintStream;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public final class scenarioscript implements ScenarioHost {

    /** Field description */
    public static final String START_HELP_COCH = "start_help_coch";

    /** Field description */
    public static final String STARTDOROTHYSAVE = "startDorothySave";

    /** Field description */
    public static final String TELEPORTTOOFFICE = "teleporttooffice";

    /** Field description */
    public static final String CHASE_TOPO = "chase_topo";

    /** Field description */
    public static final String BIG_RACE_SCENARIO = "big_race_scenario";

    /** Field description */
    public static final String START_1300 = "start_1300";

    /** Field description */
    public static final String START_1500 = "start_1500";

    /** Field description */
    public static final String START_1600 = "start_1600";

    /** Field description */
    public static final String START_2050 = "start_2050";

    /** Field description */
    public static final String BANKRUPT = "bankrupt";

    /** Field description */
    public static final String SCENARIO_BANKRUPT = "scenario_bankrupt";

    /** Field description */
    public static final String BLOWCAR = "blowcar";

    /** Field description */
    public static final String TEAR_WIREROPE = "tear_wirerope";

    /** Field description */
    public static final String EVENT_RESUMESCRIPT = "resumescript";

    /** Field description */
    public static final String EVENT_BANKRUPT = "bankrupt";

    /** Field description */
    public static final String EVENT_SCENARIO_BANKRUPT = "scenario_bankrupt";

    /** Field description */
    public static final String EVENT_BLOW = "blowcar";

    /** Field description */
    public static final String EVENT_TEAR_WIREROPE = "tear_wirerope";

    /** Field description */
    public static final String EVENT_SOCIAL_LOOSE = "social loose";
    private static final int SOCIAL_LOOSE_MESSAGE_START_TIME = 8;
    private static final int SOCIAL_LOOSE_MESSAGE_END_TIME = 16;
    private static final int MAX_HOUR_VALUE = 23;
    private static final String DAFAULT_SAVING_FOLDER = "..\\saves\\";
    private static final String DAFAULT_SAVING_FILE = "gamesave.xml";

    /** Field description */
    public static final vectorJ OXNARD_OFFICE;
    private static final String[] FSM_NEWGANE;

    /** Field description */
    public static scenarioscript script;
    private static boolean isReleaseMode;
    private static boolean f_isTutorialOn;
    private static boolean isAutoTestRun;

    /** Field description */
    public static boolean INSTANT_ORDER_ONLY;
    private static int SCENARIO_ON_DEGUG_FLAG;

    static {
        OXNARD_OFFICE = new vectorJ(4831.0D, -24135.0D, 0.0D);
        FSM_NEWGANE = new String[] { "gamebegining", "gamebegining_no_scenario" };
        script = null;
        isReleaseMode = false;
        f_isTutorialOn = false;
        isAutoTestRun = false;
        INSTANT_ORDER_ONLY = false;
        SCENARIO_ON_DEGUG_FLAG = 0;
    }

    private boolean isInstantOrder = false;
    private boolean playerCarHasBlown = false;
    private final Object blownCarFlagLatch = new Object();
    private boolean f_messageEventsInited = false;
    private FiniteStateMachine scenarioMachine = null;
    private ScenarioStage scenarioStage = null;
    private final ConfigManager configManager = new ConfigManager();
    private final GameXmlSerializator xmlSerializator = new GameXmlSerializator("..\\saves\\", "gamesave.xml");
    private final StageChangedListener defaultGameStatesRestorer = new StageChangedListener() {
        @Override
        public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
            assert(null != scenarioStage);
            new PoliceImmunity().setValue(false).act();

            BlockSpecialObject blockPoliceAction = new BlockSpecialObject(scenarioscript.this);

            blockPoliceAction.setType("police");
            blockPoliceAction.act();

            BlockSpecialObject blockJohnHouseAction = new BlockSpecialObject(scenarioscript.this);

            blockJohnHouseAction.setType("johnhouse");
            blockJohnHouseAction.act();
            new TrafficAction().setOn(true).act();
            scenarioscript.access$000();
            eng.explosionsWhilePredefinedAnimation(false);

            if (2147483645 != scenarioStage.getScenarioStage()) {
                return;
            }

            CoreTime currentTime = new CoreTime();
            int daysDelta;
            int hoursDelta;

            if (8 > currentTime.gHour()) {
                hoursDelta = 8 - currentTime.gHour();
                daysDelta = 1;
            } else {
                if (16 < currentTime.gHour()) {
                    int hoursDelta = 23 - currentTime.gHour() + 8;

                    daysDelta = 0;
                } else {
                    hoursDelta = 0;
                    daysDelta = 1;
                }
            }

            EndScenario.getInstance().delayAction("SHOW SOCIAL LOOSE DIALOG", daysDelta, hoursDelta,
                    new VoidEvent("social loose"), null);
        }
    };
    private boolean f_start_help_coch = false;
    private boolean showScenarioLooseDialog = false;
    private boolean scheduleScenarioFinish = false;
    private boolean scheduledScenarioFinishSuccessful = false;
    private boolean clearTasks = true;
    private ScenarioController scenarioController = null;

    /**
     * Constructs ...
     *
     */
    public scenarioscript() {
        script = this;
        this.configManager.addConfig(new EnemyBaseConfig());
        this.configManager.addConfig(new ChaseCochConfig());
        this.configManager.addConfig(new ChaseToRescueDorothyConfig());
        this.configManager.addConfig(new AiChaseConfig());
        this.configManager.addConfig(new SpecialCargoConfig());
        this.configManager.load();
        ConfigManager.setGlobal(this.configManager);

        if (isReleaseMode) {
            eng.canShowPagerMessages = false;
            this.configManager.reloadConfigsOnQuery(false);
            ScenarioGarbageFinder.setFatalOnGarbage(false);
            Logger.getLogger("rnr").setLevel(Level.WARNING);
        } else {
            eng.canShowPagerMessages = true;
            this.configManager.reloadConfigsOnQuery(true);
        }

        if (eng.noNative) {
            this.configManager.setGameLevel(0);
        } else {
            this.configManager.setGameLevel(eng.getDifficultyLevel());
        }

        MissionSystemInitializer.firstInitializeSystem();
        SerializatorOfAnnotated.getInstance().register(Start0430.Checker.getUid(), Start0430.Checker.class);
        SerializatorOfAnnotated.getInstance().register(Start0454.Checker.getUid(), Start0454.Checker.class);
        SerializatorOfAnnotated.getInstance().register(ScenarioStage.getUid(), ScenarioStage.class);
        SerializatorOfAnnotated.getInstance().register("chase00090", chase00090.class);
        SerializatorOfAnnotated.getInstance().register("RaceFailCondition", RaceFailCondition.class);
        XmlSerializationFabric.addRegisterDeSerializationInterface(ActorVehSerializator.getNodeName(),
                new ActorVehSerializator());
        XmlSerializationFabric.addRegisterDeSerializationInterface(AIPlayerSerializator.getNodeName(),
                new AIPlayerSerializator());
        XmlSerializationFabric.addRegisterDeSerializationInterface(Preparesc00060Serializator.getNodeName(),
                new Preparesc00060Serializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(Chase00090Serializator.getNodeName(),
                new Chase00090Serializator());
        XmlSerializationFabric.addRegisterDeSerializationInterface(ChaseKohSerializator.getNodeName(),
                new ChaseKohSerializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(ChaseTopoSerializator.getNodeName(),
                new ChaseTopoSerializator());
        XmlSerializationFabric.addRegisterDeSerializationInterface(CursedHiWaySerializator.getNodeName(),
                new CursedHiWaySerializator());
        XmlSerializationFabric.addRegisterDeSerializationInterface(EnemyBaseSerializator.getNodeName(),
                new EnemyBaseSerializator((EnemyBaseConfig) this.configManager.getConfig(0), this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(KohHelpSerializator.getNodeName(),
                new KohHelpSerializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(PiterPanDoomedRaceSerializator.getNodeName(),
                new PiterPanDoomedRaceSerializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(PiterPanFinalRaceSerializator.getNodeName(),
                new PiterPanFinalRaceSerializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(RedRockCanyonSerializator.getNodeName(),
                new RedRockCanyonSerializator(this));
        XmlSerializationFabric.addRegisterDeSerializationInterface(BlockedSOSerializator.getNodeName(),
                new BlockedSOSerializator());
        Chase00090Serializator.setHost(this);
        this.xmlSerializator.addSerializationTarget(new XmlSerializable() {
            private static final String MY_NODE = "SCENARIO_TECH";
            @Override
            public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
                stream.println(String.format("<%s>%b</%s>", new Object[] { "SCENARIO_TECH",
                        Boolean.valueOf(scenarioscript.access$200(scenarioscript.this)), "SCENARIO_TECH" }));
            }
            @Override
            public void loadFromNode(org.w3c.dom.Node node) {
                if (null == node) {
                    return;
                }

                scenarioscript.access$202(scenarioscript.this, Boolean.parseBoolean(node.getTextContent()));
            }
            @Override
            public void yourNodeWasNotFound() {}
            @Override
            public String getRootNodeName() {
                return "SCENARIO_TECH";
            }
        });
        this.xmlSerializator.addSerializationTargetExclusively(new XmlSerializable() {
            @Override
            public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
                if (null == scenarioscript.this.scenarioStage) {
                    return;
                }

                SerializatorOfAnnotated.getInstance().saveState(stream, scenarioscript.this.scenarioStage);
            }
            @Override
            public void loadFromNode(org.w3c.dom.Node node) {
                if (null == node) {
                    return;
                }

                scenarioscript.access$302(
                    scenarioscript.this,
                    (ScenarioStage) SerializatorOfAnnotated.getInstance().loadStateOrNull(new xmlutils.Node(node)));

                if (null == scenarioscript.this.scenarioStage) {
                    scenarioscript.access$302(scenarioscript.this, new ScenarioStage());
                }

                scenarioscript.this.registerScenarioStageChangeListeners();
            }
            @Override
            public void yourNodeWasNotFound() {
                scenarioscript.access$302(scenarioscript.this, new ScenarioStage());
                scenarioscript.this.registerScenarioStageChangeListeners();
            }
            @Override
            public String getRootNodeName() {
                return ScenarioStage.getUid();
            }
        });
        this.xmlSerializator.addSerializationTargetExclusively(ScriptRefXmlSerialization.getInstance());
        this.xmlSerializator.addSerializationTargetExclusively(UidStorageSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(CrewXmlSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(BigRaceSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(SoSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(EndScenarioSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(ObjectXmlSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(StaticScenarioStuffSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(MissionsSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(MissionsLogSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(MissionsInitiatedSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(NewspaperSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(MissionPassangerSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(ScenarioFlagsSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(FactionRaterSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(RatingSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(CBVideoSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(DelayedChannelSerializator.getInstance());
        this.xmlSerializator.addSerializationTarget(OrganizerSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(JournalSerialization.getInstance());
        this.xmlSerializator.addSerializationTarget(AlbumSerialization.getInstance());
        NativeSerializationInterface.setGameSerializator(this.xmlSerializator);
        new FirstRun().start();
        new RaceHistory();
        eng.writeLog("Game started");
    }

    /**
     * Method description
     *
     */
    public static void setReleaseModeOn() {
        isReleaseMode = true;
    }

    /**
     * Method description
     *
     */
    public static void SetInstantOrderOnly() {
        INSTANT_ORDER_ONLY = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean setPlayerCarHasBlown() {
        synchronized (this.blownCarFlagLatch) {
            if (!(this.playerCarHasBlown)) {
                this.playerCarHasBlown = true;

                return true;
            }

            return false;
        }
    }

    FiniteStateMachine gMachine() {
        return this.scenarioMachine;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ScenarioStage getStage() {
        return this.scenarioStage;
    }

    /**
     * Method description
     *
     */
    public void debugInitStage() {
        this.scenarioStage = new ScenarioStage();
    }

    /**
     * Method description
     *
     */
    public static void switchOffScenarioConsistencyChecking() {
        if ((null == script) || (null == script.scenarioStage)) {
            return;
        }

        script.scenarioStage.scenarioStageUndefined();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isInstantOrder() {
        return this.isInstantOrder;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public SoBlock getSoBlocker() {
        return SoBlock.getInstance();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public FiniteStateMachine getScenarioMachine() {
        return this.scenarioMachine;
    }

    private void clearFSM() {
        if (null == this.scenarioMachine) {
            return;
        }

        this.scenarioMachine.clear();
        this.xmlSerializator.removeSerializationTarget(this.scenarioMachine);
        this.scenarioMachine = null;
    }

    private void buildFSM() {
        initMessageEvents();

        try {
            if (null == this.scenarioMachine) {
                InternalScenarioRepresentation scenario =
                    XmlScenarioMachineBuilder.getScenarioMachine("..\\Data\\config\\quests.xml", false);

                this.scenarioMachine = scenario.getStatesMachine();

                CbvEventListenerBuilder cbvBuilder = new CbvEventListenerBuilder("..\\Data\\config\\cbvideocalls.xml",
                                                         this.scenarioMachine);

                EventsController.getInstance().addListener(cbvBuilder.getWare());
                EventsController.getInstance().addListener(this.scenarioMachine);
                this.xmlSerializator.addSerializationTarget(gMachine());
            } else {
                XmlScenarioMachineBuilder.reloadMachine(this.scenarioMachine, "..\\Data\\config\\quests.xml");
            }
        } catch (IOException exception) {
            ScenarioLogger.getInstance().machineError(
                "Exception has been catched while loading scenario from XML; exception message: "
                + exception.getMessage());
            exception.printStackTrace(System.err);
            System.err.flush();
        }
    }

    private static void resetScenarioFlag(String flag) {
        assert(null != flag);

        SetScenarioFlag flags = new SetScenarioFlag();

        flags.value = true;
        flags.name = flag;
        flags.act();
    }

    private static void resetScenarioFlagsToDefault() {
        resetScenarioFlag("WarehousesEnabledByScenario");
        resetScenarioFlag("SavesEnabledByScenario");
        resetScenarioFlag("MissionsEnebledByScenario");
    }

    private void registerScenarioStageChangeListeners() {
        this.scenarioStage.addListener(EventsController.getInstance());
        this.scenarioStage.addListener(EventsControllerHelper.getInstance());
        this.scenarioStage.addListener(this.xmlSerializator);
        this.scenarioStage.addListener(NativeEventController.getInstance());
        this.scenarioStage.addListener(ScenarioSave.getInstance());
        this.scenarioStage.addListener(new StageChangedListener() {
            @Override
            public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
                ScenarioGarbageFinder.deleteOutOfDateScenarioObjects("ConditionChecker.allCheckers",
                        ConditionChecker.getAllCheckers(), scenarioStage);
            }
        });
        this.scenarioStage.addListener(new StageChangedListener() {
            @Override
            public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
                assert(null != scenarioStage);
                eng.scenarioCheckPointReached(scenarioStage);
            }
        });
        this.scenarioStage.addListener(this.defaultGameStatesRestorer);
    }

    private void beginFSM() {
        if (null == this.scenarioMachine) {
            System.err.print("ERRORR. beginFSM has null==scenarioMachine.");
        }

        this.scenarioMachine.activateState(true, new String[] { FSM_NEWGANE[SCENARIO_ON_DEGUG_FLAG] + "_phase_" + 1 });
    }

    void initMessageEvents() {
        if (this.f_messageEventsInited) {
            return;
        }

        this.f_messageEventsInited = true;
        EventsControllerHelper.getInstance().addMessageListener(script, "start_help_coch", "start_help_coch");
        EventsControllerHelper.getInstance().addMessageListener(script, "startDorothySave", "startDorothySave");
        EventsControllerHelper.getInstance().addMessageListener(script, "teleporttooffice", "teleporttooffice");
        EventsControllerHelper.getInstance().addMessageListener(script, "chase_topo", "chase_topo");
        EventsControllerHelper.getInstance().addMessageListener(script, "big_race_scenario", "big_race_scenario");
        EventsControllerHelper.getInstance().addMessageListener(script, "start_1300", "start_1300");
        EventsControllerHelper.getInstance().addMessageListener(script, "start_1500", "start_1500");
        EventsControllerHelper.getInstance().addMessageListener(script, "start_1600", "start_1600");
        EventsControllerHelper.getInstance().addMessageListener(script, "start_2050", "start_2050");
        EventsControllerHelper.getInstance().addMessageListener(script, "bankrupt", "bankrupt");
        EventsControllerHelper.getInstance().addMessageListener(script, "scenario_bankrupt", "scenario_bankrupt");
        EventsControllerHelper.getInstance().addMessageListener(script, "blowcar", "blowcar");
        EventsController.getInstance().addListener(new EventListener() {
            private boolean expectedTuple(List<ScriptEvent> eventTuple) {
                if ((1 != eventTuple.size()) || (!(eventTuple.get(0) instanceof VoidEvent))) {
                    return false;
                }

                return (0 == "social loose".compareTo(((VoidEvent) eventTuple.get(0)).getInfo()));
            }
            @Override
            public void eventHappened(List<ScriptEvent> eventTuple) {
                if (!(expectedTuple(eventTuple))) {
                    return;
                }

                scenarioscript.access$102(scenarioscript.this, true);
            }
        });
    }

    /**
     * Method description
     *
     */
    public void reloadConfig() {
        this.configManager.load();
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setGameDifficulty(int value) {
        this.configManager.setGameLevel(value);
    }

    /**
     * Method description
     *
     */
    public void playBankrupt() {
        bankrupt();
    }

    /**
     * Method description
     *
     */
    public void bankrupt() {
        if (this.isInstantOrder) {
            menu_bankrupt(null);
        } else {
            bankrupt.setSimpleBankrupt();
            ScenarioSync.setPlayScene("bankrupt");
        }
    }

    /**
     * Method description
     *
     */
    public void scenario_bankrupt() {
        bankrupt.setScenarioBankrupt();
        ScenarioSync.setPlayScene("bankrupt");
    }

    /**
     * Method description
     *
     *
     * @param quilt
     *
     * @return
     */
    public boolean arrested(int quilt) {
        System.out.println("arrested: " + quilt);
        EventsControllerHelper.messageEventHappened("Scenario Arrested");

        return PoliceScene.reserved_for_scene;
    }

    /**
     * Method description
     *
     *
     * @param lastzone
     */
    public void entereventSO(int lastzone) {
        specobjects specOb = specobjects.getInstance();

        switch (lastzone) {
         case 1 :
             specOb.last_SO_zone = 2;

             break;

         case 2 :
             specOb.last_SO_zone = 32;

             break;

         case 3 :
             specOb.last_SO_zone = 16;

             break;

         case 4 :
             specOb.last_SO_zone = 8;

             break;

         case 5 :
             specOb.last_SO_zone = 128;

             break;

         case 6 :
             specOb.last_SO_zone = 4;

             break;

         case 7 :
             specOb.last_SO_zone = 1;

             break;

         case 8 :
             specOb.last_SO_zone = 64;

             break;

         case 9 :
             specOb.last_SO_zone = 1024;
        }

        cSpecObjects spec = specOb.getCurrentObject();

        if (null == spec) {
            return;
        }

        EventsController.getInstance().eventHappen(new ScriptEvent[] {
            new SpecialObjectEvent(spec, SpecialObjectEvent.EventType.enter),
            new TimeEvent(new CoreTime()) });
    }

    void activatedF2() {
        specobjects specOb = specobjects.getInstance();
        cSpecObjects spec = specOb.getCurrentObject();

        if (null == spec) {
            return;
        }

        EventsController.getInstance().eventHappen(new ScriptEvent[] {
            new SpecialObjectEvent(spec, SpecialObjectEvent.EventType.f2),
            new TimeEvent(new CoreTime()) });
    }

    void exitSO() {
        specobjects specOb = specobjects.getInstance();
        cSpecObjects spec = specOb.getCurrentObject();

        if (null == spec) {
            return;
        }

        EventsController.getInstance().eventHappen(new ScriptEvent[] {
            new SpecialObjectEvent(spec, SpecialObjectEvent.EventType.exit),
            new TimeEvent(new CoreTime()) });
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public CBVideoStroredCall getStoredCall(String name) {
        return CBCallsStorage.getInstance().getStoredCall(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void launchCall(String name) {
        CBVideoStroredCall qu = getStoredCall(name);

        if (qu == null) {
            eng.writeLog("Cannot find call named " + name + " in launchCall.");
        } else {
            qu.makecall();
        }
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void immediateLaunchCall(String name) {
        CBVideoStroredCall qu = getStoredCall(name);

        if (qu == null) {
            eng.writeLog("Cannot find call named " + name + " in launchCall.");
        } else {
            qu.makeImmediateCall();
        }
    }

    void start_help_coch() {
        if (this.f_start_help_coch) {
            return;
        }

        this.f_start_help_coch = true;

        if (null == KohHelpManage.getInstance()) {
            KohHelpManage.constructSingleton(this);
        }

        KohHelpManage.getInstance().start();
    }

    void big_race_scenario() {
        new BigRaceScenario();
    }

    void start_1300() {
        new CursedHiWay();
    }

    void start_1500() {
        new RedRockCanyon(this, false);
    }

    void start_1600() {
        Config enemyBaseConfig = this.configManager.getConfig(0);

        assert(enemyBaseConfig instanceof EnemyBaseConfig) : "illegal config type";
        new EnemyBase((EnemyBaseConfig) enemyBaseConfig, this);
    }

    void start_2050() {
        new ChaseKoh(this, false);
    }

    /**
     * Method description
     *
     */
    public void enter_moat() {
        blowcar();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public chaseTopo constructChaseTopo() {
        return new chaseTopo(this, (SpecialCargoConfig) this.configManager.getConfig(4));
    }

    /**
     * Method description
     *
     */
    public void chase_topo() {
        ScenarioSave.getInstance().CHASETOPO = constructChaseTopo();
        ScenarioSave.getInstance().CHASETOPO.start();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public matrixJ getChaseTopoMatDakota() {
        if ((ScenarioSave.getInstance() != null) && (ScenarioSave.getInstance().CHASETOPO != null)) {
            return ScenarioSave.getInstance().CHASETOPO.getMatDakota();
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public vectorJ getChaseTopoPosDakota() {
        if ((ScenarioSave.getInstance() != null) && (ScenarioSave.getInstance().CHASETOPO != null)) {
            return ScenarioSave.getInstance().CHASETOPO.getPosDakota();
        }

        return null;
    }

    /**
     * Method description
     *
     */
    public void chaseTopoexitAnimationDakota() {
        if ((ScenarioSave.getInstance() == null) || (ScenarioSave.getInstance().CHASETOPO == null)) {
            return;
        }

        ScenarioSave.getInstance().CHASETOPO.exitAnimation_dakota();
    }

    /**
     * Method description
     *
     */
    public void chaseTopoexitAnimationPunktA() {
        if ((ScenarioSave.getInstance() == null) || (ScenarioSave.getInstance().CHASETOPO == null)) {
            return;
        }

        ScenarioSave.getInstance().CHASETOPO.exitAnimation_punktA();
    }

    /**
     * Method description
     *
     */
    public void parkOnPunktB() {
        if ((ScenarioSave.getInstance() == null) || (ScenarioSave.getInstance().CHASETOPO == null)) {
            return;
        }

        ScenarioSave.getInstance().CHASETOPO.parkOnPunktB();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public SimplePresets preparePunctBMatrix() {
        return chaseTopo.preparePunctBMatrix();
    }

    /**
     * Method description
     *
     */
    public void exitAnimation_punktB() {
        if ((ScenarioSave.getInstance() == null) || (ScenarioSave.getInstance().CHASETOPO == null)) {
            return;
        }

        ScenarioSave.getInstance().CHASETOPO.exitAnimation_punktB();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public SimplePresets prepareDarkTruckMatrix() {
        return chaseTopo.prepareDarkTruckMatrix();
    }

    /**
     * Method description
     *
     */
    public void finishChaseTopo() {
        if ((null == ScenarioSave.getInstance()) || (null == ScenarioSave.getInstance().CHASETOPO)) {
            return;
        }

        ScenarioSave.getInstance().CHASETOPO.finish();
        ScenarioSave.getInstance().CHASETOPO = null;
    }

    /**
     * Method description
     *
     */
    public void before_save() {
        Sc_serial.getInstance().recieve();
    }

    /**
     * Method description
     *
     */
    public void startDorothySave() {
        new preparesc00060(this, false).start();
    }

    /**
     * Method description
     *
     */
    public static void compactUids() {
        ScriptRefStorage.getInstance().compact();
    }

    /**
     * Method description
     *
     *
     * @param successfully
     */
    public void sheduleScenarioStopOnNextFrame(boolean successfully) {
        this.scheduleScenarioFinish = true;
        this.scheduledScenarioFinishSuccessful = successfully;
        eng.blockEscapeMenu();
    }

    void run_0_ceconds() {
        ScenarioSave.getInstance().run_0();

        if ((null != this.scenarioMachine) && (null != this.scenarioStage) && (this.scheduleScenarioFinish)
                && (0 == this.scenarioMachine.getCurrentActiveStatesCount())
                && (!(this.scenarioStage.isScenarioFinished()))) {
            this.scenarioStage.scenarioFinished(this.scheduledScenarioFinishSuccessful);
            this.scheduleScenarioFinish = false;
        }

        if ((!(menues.cancreate_somenu())) || (!(this.showScenarioLooseDialog))) {
            return;
        }

        looseScenario();
        VictoryMenu.createLooseSocial(new VictoryMenuExitListener() {
            @Override
            public void OnMenuExit(int result) {
                if (0 == result) {
                    if (Helper.hasContest()) {
                        return;
                    }

                    TotalVictoryMenu.createGameOverTotal(new VictoryMenuExitListener() {
                        @Override
                        public void OnMenuExit(int result) {
                            if (1 != result) {
                                return;
                            }

                            JavaEvents.SendEvent(23, 1, null);
                        }
                    });
                } else {
                    if ($assertionsDisabled) {
                        return;
                    }

                    throw new AssertionError();
                }
            }
        });
        this.showScenarioLooseDialog = false;
    }

    void run_3_ceconds() {
        Crew.rotateNonLoadedModels();

        if (!(menues.cancreate_messagewindow())) {
            return;
        }

        System.err.flush();
        ScenarioSave.getInstance().run_3();
        PedestrianManager.getInstance().process();
        SoBlock.getInstance().process();
        BigRace.process();
    }

    void run_60_ceconds() {
        if (!(menues.cancreate_messagewindow())) {
            return;
        }

        ScenarioSave.getInstance().run_60();
    }

    void run_600_ceconds() {
        if (!(menues.cancreate_messagewindow())) {
            return;
        }

        ScenarioSave.getInstance().run_600();
        cbapparatus.getInstance().clearFinishedCalls();
    }

    void tickMoveTime() {
        BigRace.process();
    }

    /**
     * Method description
     *
     *
     * @param dt
     * @param Obj
     *
     * @return
     */
    public boolean animaterun(double dt, anm Obj) {
        return Obj.animaterun(dt);
    }

    /**
     * Method description
     *
     */
    public void tutorial() {
        f_isTutorialOn = true;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setTutorial(boolean value) {
        f_isTutorialOn = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setAutotestRun(boolean value) {
        isAutoTestRun = value;
    }

    /**
     * Method description
     *
     */
    public static void turnScenarioOff() {
        resetScenarioFlagsToDefault();
        resetScenarioFlag("Dorothy_is_available");
        SCENARIO_ON_DEGUG_FLAG = 1;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean isScenarioOn() {
        return (SCENARIO_ON_DEGUG_FLAG != 1);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean isTutorialOn() {
        return f_isTutorialOn;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean isAuotestRun() {
        return isAutoTestRun;
    }

    /**
     * Method description
     *
     */
    public void teleporttooffice() {
        Office.teleportPlayer();
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void menu_bankrupt(ILeaveMenuListener listener) {
        menues.gameoverBankrupt(listener);
    }

    /**
     * Method description
     *
     */
    public void blowcar() {
        drvscripts.BlowScene(Crew.getIgrok(), Crew.getIgrokCar());
    }

    /**
     * Method description
     *
     */
    public void officeKeyboardHide() {
        JavaEvents.SendEvent(6007, 0, null);
    }

    /**
     * Method description
     *
     */
    public void winScenario() {
        JavaEvents.SendEvent(40, 1, null);
    }

    /**
     * Method description
     *
     */
    public void looseScenario() {
        JavaEvents.SendEvent(40, 2, null);
    }

    /**
     * Method description
     *
     */
    public void winRace() {
        JavaEvents.SendEvent(40, 3, null);
    }

    /**
     * Method description
     *
     */
    public void looseRace() {
        JavaEvents.SendEvent(40, 4, null);
    }

    private void initEventsListeners() {
        script.initMessageEvents();
        Helper.addNativeEventListener(new CResumeScript());
        Helper.addNativeEventListener(new CBlowCar());
        Helper.addNativeEventListener(new CTearWireRope());
    }

    /**
     * Method description
     *
     */
    public void startNewGame() {
        synchronized (this.blownCarFlagLatch) {
            this.playerCarHasBlown = false;
        }

        stage.resetInterruptionStatus();
        this.configManager.load();
        this.configManager.setGameLevel(eng.getDifficultyLevel());
        this.f_messageEventsInited = false;
        cCBVideoCall_Caller.initialize();
        ScenarioFlagsManager.init();
        Dialog.init();
        PickUpEventManager.init();
        EventsControllerHelper.init();
        MissionSystemInitializer.initializeSystem();
        BigRaceScenario.init();
        NativeEventController.init();
        initEventsListeners();
        PoliceScene.reserved_for_scene = false;
        restartgame.restart();
        buildFSM();
        this.scenarioStage = new ScenarioStage();
        registerScenarioStageChangeListeners();
        beginFSM();
        CBCallsStorage.getInstance().init();
        XmlInit.read();

        Vector filenames = new Vector();

        eng.getFilesAllyed("missions", filenames);
        MissionSystemInitializer.getMissionsManager().deinitialize();
        MissionSystemInitializer.getMissionsManager().initialize(filenames);
        eng.blockSO(64);
        eng.blockSO(1024);
        PedestrianManager.getInstance().setPopulation(0);

        try {
            ScenarioPassing.init();
        } catch (ScenarioPassing.InitializedException e) {
            eng.err(e.toString());
        }

        MenuResourcesManager.holdResources();
    }

    /**
     * Method description
     *
     */
    public void startQuickRace() {
        synchronized (this.blownCarFlagLatch) {
            this.playerCarHasBlown = false;
        }

        ScenarioFlagsManager.init();
        Dialog.init();
        PickUpEventManager.init();
        EventsControllerHelper.init();
        MissionSystemInitializer.initializeSystem();
        BigRaceScenario.init();
        NativeEventController.init();
        initEventsListeners();
        PoliceScene.reserved_for_scene = false;
        this.isInstantOrder = true;
        restartgame.restart();
        eng.blockSO(8);
        eng.blockSO(4);
        eng.blockSO(1);
        eng.blockSO(64);
        eng.blockSO(1024);
        PedestrianManager.getInstance().setPopulation(0);

        try {
            ScenarioPassing.init();
        } catch (ScenarioPassing.InitializedException e) {
            eng.err(e.toString());
        }

        MissionSystemInitializer.getMissionsManager().deinitialize();
        MenuResourcesManager.holdResources();
    }

    /**
     * Method description
     *
     */
    public void loadGame() {
        synchronized (this.blownCarFlagLatch) {
            this.playerCarHasBlown = false;
        }

        ScriptRefStorage.clearRefferaceTable();
        stage.resetInterruptionStatus();
        this.configManager.load();
        this.configManager.setGameLevel(eng.getDifficultyLevel());
        cCBVideoCall_Caller.initialize();
        ScenarioFlagsManager.init();
        Dialog.init();
        PickUpEventManager.init();
        EventsControllerHelper.init();
        MissionSystemInitializer.initializeSystem();
        BigRaceScenario.init();
        NativeEventController.init();
        initEventsListeners();
        PoliceScene.reserved_for_scene = false;
        restartgame.restart();
        buildFSM();
        XmlInit.read();
        CBCallsStorage.getInstance().init();

        Vector filenames = new Vector();

        eng.getFilesAllyed("missions", filenames);
        MissionSystemInitializer.getMissionsManager().deinitialize();
        MissionSystemInitializer.getMissionsManager().initialize(filenames);
        MenuResourcesManager.holdResources();
        new RenewJourmalAfterLoad();
    }

    /**
     * Method description
     *
     */
    public void deinitGame() {
        ScenarioSync.gameWentInMainMenu();
        ObjectXmlSerializator.getInstance().cleanUp();
        this.scheduleScenarioFinish = false;
        this.f_start_help_coch = false;
        this.f_messageEventsInited = false;
        cbapparatus.getInstance().clearAllCalls();
        CBCallsStorage.deinit();
        MissionPassanger.deinit();
        Newspapers.deinit();
        Dialog.deInit();
        EndScenario.deinit();
        PedestrianManager.deinit();
        XmlInit.deinit();
        Organizers.deinit();
        BigRaceScenario.deinit();
        NativeEventController.deinit();

        if (null != this.scenarioController) {
            this.scenarioController.gameDeinitLaunched();
        }

        PoliceScene.reserved_for_scene = false;
        StaticScenarioStuff.makeReadyCursedHiWay(false);
        StaticScenarioStuff.makeReadyPreparesc00060(false);
        this.isInstantOrder = false;
        clearFSM();
        cCBVideoCall_Caller.deinitialize();
        organaiser.deinit();
        journal.deinit();
        PedestrianManager.getInstance().setPopulation(0);

        try {
            ScenarioPassing.deinit();
        } catch (ScenarioPassing.NotInitializedException e) {
            eng.err(e.toString());
        }

        BigRace.deinit();
        MissionSystemInitializer.deinitializeSystem();
        StarterBase.deinitScenarioMissionsStarters();
        DelayedResourceDisposer.deinit();
        MenuResourcesManager.leaveResources();
        Crew.getInstance().deinit();
        SoBlock.deinit();
        PickUpEventManager.deinit();
        MissionDialogs.deinit();
        ScenarioFlagsManager.deinit();
        FactionRater.deinit();
        RateSystem.deinit();

        if (null != this.scenarioStage) {
            this.scenarioStage.scenarioUnloaded();
        }

        EventsControllerHelper.deinit();
        EventsController.deinit();
        ScriptRefStorage.getInstance().deinit();
        UidStorage.getInstance().reset();

        if (this.clearTasks) {
            ScenarioSave.getInstance().gameDeinitLaunched();
        }

        CrewNamesManager.deinit();

        if ((eng.noNative) || (ScriptRefStorage.getRefferaceTable().isEmpty())) {
            return;
        }

        eng.fatal("someone added ref to ScriptRefStorage while java were deinitting");
    }

    /**
     * Method description
     *
     */
    public void doNotClearTasksOnGameDeinit() {
        this.clearTasks = false;
    }

    /**
     * Method description
     *
     */
    public void backdoorDeinitLaunched() {
        if (null == this.scenarioStage) {
            return;
        }

        synchronized (this.scenarioStage.getLatch()) {
            boolean scenarioInEnemyBaseStage = EnemyBase.class.getAnnotation(ScenarioClass.class).scenarioStage()
                                               == this.scenarioStage.getScenarioStage();

            if ((scenarioInEnemyBaseStage) && (null != EnemyBase.getInstance())) {
                EnemyBase.getInstance().deleteScenesResources();
            }
        }
    }

    /**
     * Method description
     *
     */
    public void backdoorKillLaunched() {
        backdoorDeinitLaunched();
    }

    /**
     * Method description
     *
     *
     * @param controller
     */
    @Override
    public void registerController(ScenarioController controller) {
        if (null == this.scenarioController) {
            this.scenarioController = controller;
        } else {
            String errorMessage =
                "Attempt to register controller, when previous was not deleted. Incloming controller class: "
                + controller.getClass().getName();

            errorMessage = errorMessage + "Current controller class: " + this.scenarioController.getClass().getName();

            if (isReleaseMode) {
                eng.err(errorMessage);
            } else {
                eng.fatal(errorMessage);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param controller
     */
    @Override
    public void unregisterController(ScenarioController controller) {
        if (this.scenarioController == controller) {
            this.scenarioController = null;
        } else {
            String errorMessage = "Attempt to unregister controller, which has not been registered. Controller class: "
                                  + controller.getClass().getName();

            if (isReleaseMode) {
                eng.err(errorMessage);
            } else {
                eng.fatal(errorMessage);
            }
        }
    }

    static class CBlowCar implements INativeMessageEvent {

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String getMessage() {
            return "blowcar";
        }

        /**
         * Method description
         *
         *
         * @param message
         */
        @Override
        public void onEvent(String message) {
            if (!(scenarioscript.script.setPlayerCarHasBlown())) {
                return;
            }

            scenarioscript.script.blowcar();
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public boolean removeOnEvent() {
            return false;
        }
    }


    static class CResumeScript implements INativeMessageEvent {

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String getMessage() {
            return "resumescript";
        }

        /**
         * Method description
         *
         *
         * @param message
         */
        @Override
        public void onEvent(String message) {
            ScenarioSync.resumeScene();
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public boolean removeOnEvent() {
            return false;
        }
    }


    static class CTearWireRope implements INativeMessageEvent {

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String getMessage() {
            return "tear_wirerope";
        }

        /**
         * Method description
         *
         *
         * @param message
         */
        @Override
        public void onEvent(String message) {
            if (null == EnemyBase.getInstance()) {
                return;
            }

            EnemyBase.getInstance().assault_succeded();
            eng.setdword("DWORD_EnemyBaseWireRope", 0);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public boolean removeOnEvent() {
            return false;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
