/*
 * @(#)CreateAllMenues.java   13/08/28
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

import rnr.src.menu.DateData;
import rnr.src.menu.PastFewDaysMenu;
import rnr.src.menu.ProgressIndicatorMenu;
import rnr.src.menu.TimeData;
import rnr.src.menu.Titres;
import rnr.src.menu.menues;
import rnr.src.menu.pager;
import rnr.src.menuscript.AnswerMenu;
import rnr.src.menuscript.BannerMenu;
import rnr.src.menuscript.BarMenu;
import rnr.src.menuscript.BikMenu;
import rnr.src.menuscript.BillOfLadingMenu;
import rnr.src.menuscript.EscapeMenu;
import rnr.src.menuscript.HeadUpDisplay;
import rnr.src.menuscript.MessageDebtSale;
import rnr.src.menuscript.MessageWindow;
import rnr.src.menuscript.MissionSuccessPicture;
import rnr.src.menuscript.Motelmenues;
import rnr.src.menuscript.NotifyGameOver;
import rnr.src.menuscript.PanelMenu;
import rnr.src.menuscript.PanelMenu.Time;
import rnr.src.menuscript.PoliceMenu;
import rnr.src.menuscript.RacePanelMenu;
import rnr.src.menuscript.RacePanelMenu.ParticipantInfo;
import rnr.src.menuscript.RacePanelMenu.SummaryInfo;
import rnr.src.menuscript.RoadService;
import rnr.src.menuscript.STOmenues;
import rnr.src.menuscript.ScenarioBigRaceConfirmation;
import rnr.src.menuscript.TotalVictoryMenu;
import rnr.src.menuscript.VictoryMenu;
import rnr.src.menuscript.WHmenues;
import rnr.src.menuscript.cbvideo.Dialogitem;
import rnr.src.menuscript.cbvideo.MenuCall;
import rnr.src.menuscript.cbvideo.MenuCallFullDialog;
import rnr.src.menuscript.cbvideo.MenuNotify;
import rnr.src.menuscript.gasstationmenu;
import rnr.src.menuscript.mainmenu.StartMenu;
import rnr.src.menuscript.menuBridgeToll;
import rnr.src.menuscript.office.OfficeMenu;
import rnr.src.menuscript.org.OrganiserMenu;
import rnr.src.players.IcontaktCB;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscr.ILeaveMenuListener;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public class CreateAllMenues {

    /** Field description */
    public long lastMenuCreated = 0L;

    /**
     * Method description
     *
     */
    public void introMENU() {
        this.lastMenuCreated = BikMenu.createMenu();
    }

    /**
     * Method description
     *
     */
    public void mainMENU() {
        this.lastMenuCreated = StartMenu.create();
    }

    /**
     * Method description
     *
     */
    public void escMENU() {
        this.lastMenuCreated = EscapeMenu.CreateEscapeMenu();
    }

    /**
     * Method description
     *
     */
    public void tutorialTruckStopMENU() {
        this.lastMenuCreated = MessageWindow.CreateMessageWindow("TUTORIAL - WAREHOUSE", true, true, 30.0D, "ESC",
                true, false);
    }

    /**
     * Method description
     *
     */
    public void tutorialWarehouseMENU() {
        this.lastMenuCreated = MessageWindow.CreateMessageWindow("TUTORIAL - TRUCKSTOP", true, true, 30.0D, "ESC",
                true, false);
    }

    /**
     * Method description
     *
     */
    public void f2gasMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(2);
    }

    /**
     * Method description
     *
     */
    public void f2repairMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(3);
    }

    /**
     * Method description
     *
     */
    public void f2barMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(4);
    }

    /**
     * Method description
     *
     */
    public void f2motelMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(6);
    }

    /**
     * Method description
     *
     */
    public void f2officeMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(7);
    }

    /**
     * Method description
     *
     */
    public void f2policeMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(8);
    }

    /**
     * Method description
     *
     */
    public void f2defaultMENU() {
        this.lastMenuCreated = new menues().SpecObjectMessage(10);
    }

    /**
     * Method description
     *
     */
    public void headupMENU() {
        this.lastMenuCreated = HeadUpDisplay.create();
    }

    /**
     * Method description
     *
     */
    public void cbvnotifyMENU() {
        this.lastMenuCreated = MenuNotify.create(1000000.0D).getMenuDescriptor();
    }

    /**
     * Method description
     *
     */
    public void cbvcallMENU() {
        this.lastMenuCreated = MenuCall.create().getMenuDescriptor();
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void cbvfulldialogMENU() {
        ArrayList items = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            Dialogitem item = new Dialogitem(new ContacterDummy(), "blah blah");

            items.add(item);
        }

        this.lastMenuCreated = MenuCallFullDialog.create(items).getMenuDescriptor();
    }

    /**
     * Method description
     *
     */
    public void pagerMENU() {
        this.lastMenuCreated = menues.createSimpleMenu(new pager());
    }

    /**
     * Method description
     *
     */
    public void banner01MENU() {
        this.lastMenuCreated = BannerMenu.CreateBannerMenu(1, "source", "destination", "RaceName", "Logoname", 100,
                100, new TimeData(), new DateData());
    }

    /**
     * Method description
     *
     */
    public void banner02MENU() {
        this.lastMenuCreated = BannerMenu.CreateBannerMenu(2, "source", "destination", "RaceName", "Logoname", 100,
                100, new TimeData(), new DateData());
    }

    /**
     * Method description
     *
     */
    public void banner03MENU() {
        this.lastMenuCreated = BannerMenu.CreateBannerMenu(3, "source", "destination", "RaceName", "Logoname", 100,
                100, new TimeData(), new DateData());
    }

    /**
     * Method description
     *
     */
    public void banner04MENU() {
        this.lastMenuCreated = BannerMenu.CreateBannerMenu(4, "source", "destination", "RaceName", "Logoname", 100,
                100, new TimeData(), new DateData());
    }

    /**
     * Method description
     *
     */
    public void racecheckin01MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaCheckIn(1, new CoreTime());
    }

    /**
     * Method description
     *
     */
    public void racecheckin02MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaCheckIn(2, new CoreTime());
    }

    /**
     * Method description
     *
     */
    public void racecheckin03MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaCheckIn(3, new CoreTime());
    }

    /**
     * Method description
     *
     */
    public void racecheckin04MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaCheckIn(4, new CoreTime());
    }

    /**
     * Method description
     *
     */
    public void racestartin01MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaStartIn(1);
    }

    /**
     * Method description
     *
     */
    public void racestartin02MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaStartIn(2);
    }

    /**
     * Method description
     *
     */
    public void racestartin03MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaStartIn(3);
    }

    /**
     * Method description
     *
     */
    public void racestartin04MENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaStartIn(4);
    }

    /**
     * Method description
     *
     */
    public void stellaPreparingToOrdersMENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaPrepareToOrders();
    }

    /**
     * Method description
     *
     */
    public void stellaPreparingToRaceMENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaPrepareToRace();
    }

    /**
     * Method description
     *
     */
    public void stellaWilcomMENU() {
        this.lastMenuCreated = BannerMenu.CreateStellaWelcome("some city");
    }

    /**
     * Method description
     *
     */
    public void barMENU() {
        this.lastMenuCreated = BarMenu.CreateBarMenu("bar name", new vectorJ(), null);
    }

    /**
     * Method description
     *
     */
    public void officeMENU() {
        this.lastMenuCreated = OfficeMenu.create();
    }

    /**
     * Method description
     *
     */
    public void gasMENU() {
        this.lastMenuCreated = menues.createSimpleMenu(new gasstationmenu());
    }

    /**
     * Method description
     *
     */
    public void motelMENU() {
        this.lastMenuCreated = menues.createSimpleMenu(new Motelmenues());
    }

    /**
     * Method description
     *
     */
    public void stoMENU() {
        this.lastMenuCreated = menues.createSimpleMenu(new STOmenues());
    }

    /**
     * Method description
     *
     */
    public void warehouseMENU() {
        this.lastMenuCreated = menues.createSimpleMenu(new WHmenues());
    }

    /**
     * Method description
     *
     */
    public void preparetoRaceInMENU() {
        this.lastMenuCreated = RacePanelMenu.PanelPreparingToRaceIn();
    }

    /**
     * Method description
     *
     */
    public void preparetoRaceOutMENU() {
        this.lastMenuCreated = RacePanelMenu.PanelPreparingToRaceOut();
    }

    /**
     * Method description
     *
     */
    public void preparetoOrdersInMENU() {
        this.lastMenuCreated = RacePanelMenu.PanelPreparingToOrdersIn();
    }

    /**
     * Method description
     *
     */
    public void preparetoOrdersOutMENU() {
        this.lastMenuCreated = RacePanelMenu.PanelPreparingToOrdersOut();
    }

    /**
     * Method description
     *
     */
    public void participantsRaceIn01MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[4];

        info[0] = new RacePanelMenu.ParticipantInfo("VANO", 100, 1, 2);
        info[1] = new RacePanelMenu.ParticipantInfo("VANO", 100, 2, 2);
        info[2] = new RacePanelMenu.ParticipantInfo("VANO", 100, 3, 2);
        info[3] = new RacePanelMenu.ParticipantInfo("VANO", 100, 4, 2);
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsIn(1, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void participantsRaceOut01MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[4];

        info[0] = new RacePanelMenu.ParticipantInfo("VANO", 100, 1, 2);
        info[1] = new RacePanelMenu.ParticipantInfo("VANO", 100, 2, 2);
        info[2] = new RacePanelMenu.ParticipantInfo("VANO", 100, 3, 2);
        info[3] = new RacePanelMenu.ParticipantInfo("VANO", 100, 4, 2);
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsOut(1, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void participantsRaceIn02MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[4];

        info[0] = new RacePanelMenu.ParticipantInfo("VANO", 100, 1, 2);
        info[1] = new RacePanelMenu.ParticipantInfo("VANO", 100, 2, 2);
        info[2] = new RacePanelMenu.ParticipantInfo("VANO", 100, 3, 2);
        info[3] = new RacePanelMenu.ParticipantInfo("VANO", 100, 4, 2);
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsIn(2, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void participantsRaceOut02MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[4];

        info[0] = new RacePanelMenu.ParticipantInfo("VANO", 100, 1, 2);
        info[1] = new RacePanelMenu.ParticipantInfo("VANO", 100, 2, 2);
        info[2] = new RacePanelMenu.ParticipantInfo("VANO", 100, 3, 2);
        info[3] = new RacePanelMenu.ParticipantInfo("VANO", 100, 4, 2);
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsOut(2, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void participantsRaceIn03MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[4];

        info[0] = new RacePanelMenu.ParticipantInfo("VANO", 100, 1, 2);
        info[1] = new RacePanelMenu.ParticipantInfo("VANO", 100, 2, 2);
        info[2] = new RacePanelMenu.ParticipantInfo("VANO", 100, 3, 2);
        info[3] = new RacePanelMenu.ParticipantInfo("VANO", 100, 4, 2);
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsIn(3, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void participantsRaceOut03MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[4];

        info[0] = new RacePanelMenu.ParticipantInfo("VANO", 100, 1, 2);
        info[1] = new RacePanelMenu.ParticipantInfo("VANO", 100, 2, 2);
        info[2] = new RacePanelMenu.ParticipantInfo("VANO", 100, 3, 2);
        info[3] = new RacePanelMenu.ParticipantInfo("VANO", 100, 4, 2);
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsOut(3, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void participantsRaceIn04MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[4];

        info[0] = new RacePanelMenu.ParticipantInfo("VANO", 100, 1, 2);
        info[1] = new RacePanelMenu.ParticipantInfo("VANO", 100, 2, 2);
        info[2] = new RacePanelMenu.ParticipantInfo("VANO", 100, 3, 2);
        info[3] = new RacePanelMenu.ParticipantInfo("VANO", 100, 4, 2);
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsIn(4, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void participantsRaceOut04MENU() {
        RacePanelMenu.ParticipantInfo[] info = new RacePanelMenu.ParticipantInfo[4];

        info[0] = new RacePanelMenu.ParticipantInfo("VANO", 100, 1, 2);
        info[1] = new RacePanelMenu.ParticipantInfo("VANO", 100, 2, 2);
        info[2] = new RacePanelMenu.ParticipantInfo("VANO", 100, 3, 2);
        info[3] = new RacePanelMenu.ParticipantInfo("VANO", 100, 4, 2);
        this.lastMenuCreated = RacePanelMenu.PanelListOfParticipantsOut(4, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void notinareaMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonNotInArea("some");
    }

    /**
     * Method description
     *
     */
    public void ordernothereMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonOrderNotHere("some");
    }

    /**
     * Method description
     *
     */
    public void missionothereMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonMissionNotHere("some");
    }

    /**
     * Method description
     *
     */
    public void semilostMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonSemitrailerLost("some");
    }

    /**
     * Method description
     *
     */
    public void deliveryFirstMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryFirst("some", 0, 0, 0, 0);
    }

    /**
     * Method description
     *
     */
    public void deliveryExecutedMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryExecuted("some", 0, 0, 0, 0);
    }

    /**
     * Method description
     *
     */
    public void contestFirstMENU() {
        this.lastMenuCreated = PanelMenu.PanelContestFirst("some", new PanelMenu.Time(), 0.0D, 0.0D, 0, 0, 0);
    }

    /**
     * Method description
     *
     */
    public void contestExecutedMENU() {
        this.lastMenuCreated = PanelMenu.PanelContestExecuted("some", 1, new PanelMenu.Time(), 0.0D, 0.0D, 0, 0);
    }

    /**
     * Method description
     *
     */
    public void contestDefaultMENU() {
        this.lastMenuCreated = PanelMenu.PanelContestDefaulted("some", 0, 0);
    }

    /**
     * Method description
     *
     */
    public void tenderFirstMENU() {
        this.lastMenuCreated = PanelMenu.PanelTenderFirst("some", 1, new PanelMenu.Time(), 0.0D, 0.0D, 0, 0);
    }

    /**
     * Method description
     *
     */
    public void tenderLateMENU() {
        this.lastMenuCreated = PanelMenu.PanelTenderLate("some", 0, 0);
    }

    /**
     * Method description
     *
     */
    public void forfeitEvacuationMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonForfeitEvacuation("some", 0, 0, 0, 0);
    }

    /**
     * Method description
     *
     */
    public void missionCanceledMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonMissionCancelled("some");
    }

    /**
     * Method description
     *
     */
    public void orderCanceledMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryOrderCancelled("some", 0, 0, 0, true);
    }

    /**
     * Method description
     *
     */
    public void orderCanceledCommonMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryCommonCancelled("some", 0, false);
    }

    /**
     * Method description
     *
     */
    public void towerdMENU() {
        this.lastMenuCreated = PanelMenu.PanelCommonTowed("some", 0, 0);
    }

    /**
     * Method description
     *
     */
    public void deliveryDamagedMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryDamaged("some", 0, 0, 0, true);
    }

    /**
     * Method description
     *
     */
    public void deliveryExpiredMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryExpired("some", 0, 0, 0, true);
    }

    /**
     * Method description
     *
     */
    public void tenderDefaultMENU() {
        this.lastMenuCreated = PanelMenu.PanelTenderDefaulted("some", 0, 0);
    }

    /**
     * Method description
     *
     */
    public void deliveryTowedCanceledMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryTowedCancelled("some", 0, 0, 0, 0);
    }

    /**
     * Method description
     *
     */
    public void deliveryTowedMENU() {
        this.lastMenuCreated = PanelMenu.PanelDeliveryTowed("some", 0, 0);
    }

    /**
     * Method description
     *
     */
    public void raceQualified01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelQualified(1, "racename", "logoname", 1, 1, 1, "destination",
                "checkpoint", new TimeData(), "driver", 0, 2, 23);
    }

    /**
     * Method description
     *
     */
    public void raceNotQualified01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelNotqualified(1, "racename", "logoname", 1, 1, 1, "destination",
                "checkpoint", new TimeData(), "driver", 0);
    }

    /**
     * Method description
     *
     */
    public void raceCheckPointFirst01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointFirst(1, "source", "destination", "racename", "logoname",
                true, "finita", "nexta", new TimeData(), 1, new TimeData(), 0.0D, 0.0D);
    }

    /**
     * Method description
     *
     */
    public void raceDropOrContinue01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelDropOrContinue(1, "racename", "logoname");
    }

    /**
     * Method description
     *
     */
    public void raceCheckpointMissed01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointMissed(1, "racename", "logoname", "next", new TimeData(),
                "finish");
    }

    /**
     * Method description
     *
     */
    public void raceNotAParticipant01MENU() {
        this.lastMenuCreated = RacePanelMenu.RaceNotAParticipant(1, "racename", "logoname");
    }

    /**
     * Method description
     *
     */
    public void raceWinGold01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(1, "source", "destination", "racename", "logoname", 0, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceWinSilver01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(1, "source", "destination", "racename", "logoname", 1, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceWinBronze01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(1, "source", "destination", "racename", "logoname", 2, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceSummary01MENU() {
        RacePanelMenu.SummaryInfo[] info = new RacePanelMenu.SummaryInfo[2];

        info[0] = new RacePanelMenu.SummaryInfo("name", 0, 2, new TimeData(), 0.0D);
        info[1] = new RacePanelMenu.SummaryInfo("name", 0, 3, new TimeData(), 0.0D);
        this.lastMenuCreated = RacePanelMenu.PanelSummaryReport(1, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void raceFinish01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceFinish(1, "source", "destination", "racename", "logoname", 1, 1,
                new TimeData(), 0.0D, 0.0D, 0);
    }

    /**
     * Method description
     *
     */
    public void raceTowedDefaultedIn01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedDefaultedIn(1, "name", "logo", 1, 1, 1);
    }

    /**
     * Method description
     *
     */
    public void raceTowedDefaulted01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedAndDefaulted(1, "racename", "logoname", 1, 1, 1);
    }

    /**
     * Method description
     *
     */
    public void raceCanceled01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceCancelled(1, "racename", "logoname", 1, true);
    }

    /**
     * Method description
     *
     */
    public void raceDefaulted01MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceDefaulted(1, "racename", "logoname", 1, true);
    }

    /**
     * Method description
     *
     */
    public void raceQualified02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelQualified(2, "racename", "logoname", 1, 1, 1, "destination",
                "checkpoint", new TimeData(), "driver", 0, 2, 23);
    }

    /**
     * Method description
     *
     */
    public void raceNotQualified02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelNotqualified(2, "racename", "logoname", 1, 1, 1, "destination",
                "checkpoint", new TimeData(), "driver", 0);
    }

    /**
     * Method description
     *
     */
    public void raceCheckPointFirst02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointFirst(2, "source", "destination", "racename", "logoname",
                true, "finita", "nexta", new TimeData(), 1, new TimeData(), 0.0D, 0.0D);
    }

    /**
     * Method description
     *
     */
    public void raceDropOrContinue02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelDropOrContinue(2, "racename", "logoname");
    }

    /**
     * Method description
     *
     */
    public void raceCheckpointMissed02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointMissed(2, "racename", "logoname", "next", new TimeData(),
                "finish");
    }

    /**
     * Method description
     *
     */
    public void raceNotAParticipant02MENU() {
        this.lastMenuCreated = RacePanelMenu.RaceNotAParticipant(2, "racename", "logoname");
    }

    /**
     * Method description
     *
     */
    public void raceWinGold02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(2, "source", "destination", "racename", "logoname", 0, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceWinSilver02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(2, "source", "destination", "racename", "logoname", 1, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceWinBronze02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(2, "source", "destination", "racename", "logoname", 2, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceSummary02MENU() {
        RacePanelMenu.SummaryInfo[] info = new RacePanelMenu.SummaryInfo[2];

        info[0] = new RacePanelMenu.SummaryInfo("name", 0, 2, new TimeData(), 0.0D);
        info[1] = new RacePanelMenu.SummaryInfo("name", 0, 3, new TimeData(), 0.0D);
        this.lastMenuCreated = RacePanelMenu.PanelSummaryReport(2, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void raceFinish02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceFinish(2, "source", "destination", "racename", "logoname", 1, 1,
                new TimeData(), 0.0D, 0.0D, 0);
    }

    /**
     * Method description
     *
     */
    public void raceTowedDefaultedIn02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedDefaultedIn(2, "racename", "logoname", 1, 1, 1);
    }

    /**
     * Method description
     *
     */
    public void raceTowedDefaulted02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedAndDefaulted(2, "racename", "logoname", 1, 1, 1);
    }

    /**
     * Method description
     *
     */
    public void raceCanceled02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceCancelled(2, "racename", "logoname", 1, true);
    }

    /**
     * Method description
     *
     */
    public void raceDefaulted02MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceDefaulted(2, "racename", "logoname", 1, true);
    }

    /**
     * Method description
     *
     */
    public void raceQualified03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelQualified(3, "racename", "logoname", 1, 1, 1, "destination",
                "checkpoint", new TimeData(), "driver", 0, 2, 23);
    }

    /**
     * Method description
     *
     */
    public void raceNotQualified03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelNotqualified(3, "racename", "logoname", 1, 1, 1, "destination",
                "checkpoint", new TimeData(), "driver", 0);
    }

    /**
     * Method description
     *
     */
    public void raceCheckPointFirst03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointFirst(3, "source", "destination", "racename", "logoname",
                true, "finita", "nexta", new TimeData(), 1, new TimeData(), 0.0D, 0.0D);
    }

    /**
     * Method description
     *
     */
    public void raceDropOrContinue03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelDropOrContinue(3, "racename", "logoname");
    }

    /**
     * Method description
     *
     */
    public void raceCheckpointMissed03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointMissed(3, "racename", "logoname", "next", new TimeData(),
                "finish");
    }

    /**
     * Method description
     *
     */
    public void raceNotAParticipant03MENU() {
        this.lastMenuCreated = RacePanelMenu.RaceNotAParticipant(3, "racename", "logoname");
    }

    /**
     * Method description
     *
     */
    public void raceWinGold03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(3, "source", "destination", "racename", "logoname", 0, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceWinSilver03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(3, "source", "destination", "racename", "logoname", 1, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceWinBronze03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(3, "source", "destination", "racename", "logoname", 2, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceSummary03MENU() {
        RacePanelMenu.SummaryInfo[] info = new RacePanelMenu.SummaryInfo[2];

        info[0] = new RacePanelMenu.SummaryInfo("name", 0, 2, new TimeData(), 0.0D);
        info[1] = new RacePanelMenu.SummaryInfo("name", 0, 3, new TimeData(), 0.0D);
        this.lastMenuCreated = RacePanelMenu.PanelSummaryReport(3, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void raceFinish03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceFinish(3, "source", "destination", "racename", "logoname", 1, 1,
                new TimeData(), 0.0D, 0.0D, 0);
    }

    /**
     * Method description
     *
     */
    public void raceTowedDefaultedIn03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedDefaultedIn(3, "racenae", "logoname", 1, 1, 1);
    }

    /**
     * Method description
     *
     */
    public void raceTowedDefaulted03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedAndDefaulted(3, "racename", "logoname", 1, 1, 1);
    }

    /**
     * Method description
     *
     */
    public void raceCanceled03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceCancelled(3, "racename", "logoname", 1, true);
    }

    /**
     * Method description
     *
     */
    public void raceDefaulted03MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceDefaulted(3, "racename", "logoname", 1, true);
    }

    /**
     * Method description
     *
     */
    public void raceQualified04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelQualified(4, "racename", "logoname", 1, 1, 1, "destination",
                "checkpoint", new TimeData(), "driver", 0, 2, 23);
    }

    /**
     * Method description
     *
     */
    public void raceNotQualified04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelNotqualified(4, "racename", "logoname", 1, 1, 1, "destination",
                "checkpoint", new TimeData(), "driver", 0);
    }

    /**
     * Method description
     *
     */
    public void raceCheckPointFirst04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointFirst(4, "source", "destination", "racename", "logoname",
                true, "finita", "nexta", new TimeData(), 1, new TimeData(), 0.0D, 0.0D);
    }

    /**
     * Method description
     *
     */
    public void raceDropOrContinue04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelDropOrContinue(4, "racename", "logoname");
    }

    /**
     * Method description
     *
     */
    public void raceCheckpointMissed04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelCheckpointMissed(4, "racename", "logoname", "next", new TimeData(),
                "finish");
    }

    /**
     * Method description
     *
     */
    public void raceNotAParticipant04MENU() {
        this.lastMenuCreated = RacePanelMenu.RaceNotAParticipant(4, "racename", "logoname");
    }

    /**
     * Method description
     *
     */
    public void raceWinGold04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(4, "source", "destination", "racename", "logoname", 0, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceWinSilver04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(4, "source", "destination", "racename", "logoname", 1, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceWinBronze04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceWin(4, "source", "destination", "racename", "logoname", 2, 0, 0,
                new TimeData(), 0.0D, 0.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void raceSummary04MENU() {
        RacePanelMenu.SummaryInfo[] info = new RacePanelMenu.SummaryInfo[2];

        info[0] = new RacePanelMenu.SummaryInfo("name", 0, 2, new TimeData(), 0.0D);
        info[1] = new RacePanelMenu.SummaryInfo("name", 0, 3, new TimeData(), 0.0D);
        this.lastMenuCreated = RacePanelMenu.PanelSummaryReport(4, "racename", "logoname", info);
    }

    /**
     * Method description
     *
     */
    public void raceFinish04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceFinish(4, "source", "destination", "racename", "logoname", 1, 1,
                new TimeData(), 0.0D, 0.0D, 0);
    }

    /**
     * Method description
     *
     */
    public void raceTowedDefaultedIn04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedDefaultedIn(4, "racename", "logoname", 1, 1, 1);
    }

    /**
     * Method description
     *
     */
    public void raceTowedDefaulted04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelTowedAndDefaulted(4, "racename", "logoname", 1, 1, 1);
    }

    /**
     * Method description
     *
     */
    public void raceCanceled04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceCancelled(4, "racename", "logoname", 1, true);
    }

    /**
     * Method description
     *
     */
    public void raceDefaulted04MENU() {
        this.lastMenuCreated = RacePanelMenu.PanelRaceDefaulted(4, "racename", "logoname", 1, true);
    }

    /**
     * Method description
     *
     */
    public void roadserviceMENU() {
        this.lastMenuCreated = RoadService.create(1, 1, 1, 1, 1);
    }

    /**
     * Method description
     *
     */
    public void scenarioBigRaceConfirmationMENU() {
        this.lastMenuCreated = ScenarioBigRaceConfirmation.createScenarioBigRaceConfirmationMenu("Oxnard",
                new CoreTime(), "LA", new DateData(), 1005);
    }

    /**
     * Method description
     *
     */
    public void policeMENU() {
        this.lastMenuCreated = PoliceMenu.CreatePoliceMenu(new Vector(), 1, 1);
    }

    /**
     * Method description
     *
     */
    public void unsettledDebtMENU() {
        this.lastMenuCreated = MessageDebtSale.CreateMessageDeptMenu(10000);
    }

    /**
     * Method description
     *
     */
    public void gameoverEndMENU() {
        this.lastMenuCreated = NotifyGameOver.CreateNotifyGameOver("ESC", 10000.0D, 0);
    }

    /**
     * Method description
     *
     */
    public void gameoverEndBlackScreenMENU() {
        this.lastMenuCreated = NotifyGameOver.CreateNotifyGameOver("ESC", 10000.0D, 4);
    }

    /**
     * Method description
     *
     */
    public void gameoverJailMENU() {
        this.lastMenuCreated = NotifyGameOver.CreateNotifyGameOver("ESC", 10000.0D, 1);
    }

    /**
     * Method description
     *
     */
    public void gameoverMurderMENU() {
        this.lastMenuCreated = NotifyGameOver.CreateNotifyGameOver("ESC", 10000.0D, 2);
    }

    /**
     * Method description
     *
     */
    public void gameoverBankruptMENU() {
        this.lastMenuCreated = NotifyGameOver.CreateNotifyGameOver("ESC", 10000.0D, 3);
    }

    /**
     * Method description
     *
     */
    public void gameoverTOTALMENU() {
        this.lastMenuCreated = TotalVictoryMenu.createGameOverTotal(null);
    }

    /**
     * Method description
     *
     */
    public void createLooseEconomy() {
        this.lastMenuCreated = VictoryMenu.createLooseEconomy(null);
    }

    /**
     * Method description
     *
     */
    public void createLooseSocial() {
        this.lastMenuCreated = VictoryMenu.createLooseSocial(null);
    }

    /**
     * Method description
     *
     */
    public void createLooseSport() {
        this.lastMenuCreated = VictoryMenu.createLooseSport(null);
    }

    /**
     * Method description
     *
     */
    public void createWinEconomy() {
        this.lastMenuCreated = VictoryMenu.createWinEconomy(null);
    }

    /**
     * Method description
     *
     */
    public void createWinSocial() {
        this.lastMenuCreated = VictoryMenu.createWinSocial(null);
    }

    /**
     * Method description
     *
     */
    public void createWinSport() {
        this.lastMenuCreated = VictoryMenu.createWinSport(null);
    }

    /**
     * Method description
     *
     */
    public void organiserMENU() {
        this.lastMenuCreated = OrganiserMenu.create();
    }

    /**
     * Method description
     *
     */
    public void answerMENU() {
        String[] tt = { "yes", "no" };

        this.lastMenuCreated = AnswerMenu.createAnswerMenu(tt, null);
    }

    /**
     * Method description
     *
     */
    public void missionSuccessMENU() {
        this.lastMenuCreated = MissionSuccessPicture.create("mission_name", "text_message", new CoreTime(),
                "texture_name");
    }

    /**
     * Method description
     *
     */
    public void billOfLadingMENU() {
        this.lastMenuCreated = BillOfLadingMenu.create();
    }

    /**
     * Method description
     *
     */
    public void bridgeTollMENU() {
        this.lastMenuCreated = menuBridgeToll.CreateBridgeTollMenu(new ILeaveMenuListener() {
            @Override
            public void menuLeaved() {}
        });
    }

    /**
     * Method description
     *
     */
    public void progressIndicatorMENU() {
        this.lastMenuCreated = ProgressIndicatorMenu.CreateProgressIndicatorMenu();
    }

    /**
     * Method description
     *
     */
    public void titreMENU() {
        this.lastMenuCreated = Titres.create(100.0F, "some some");
    }

    /**
     * Method description
     *
     */
    public void blackscreentitresMENU() {
        this.lastMenuCreated = PastFewDaysMenu.create();
    }

    static class ContacterDummy implements IcontaktCB {

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean accessible() {
            return false;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String gFirstName() {
            return "gFirstName";
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String gLastName() {
            return "gLastName";
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String gModelname() {
            return "gModelname";
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String gNickName() {
            return "gNickName";
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public SCRuniperson load_n_getModel() {
            return null;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
