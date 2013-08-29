/*
 * @(#)sc02070.java   13/08/28
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


package rnr.src.rnrscenario.scenes;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.PastFewDaysMenu;
import rnr.src.menuscript.VictoryMenu;
import rnr.src.menuscript.VictoryMenuExitListener;
import rnr.src.players.CarName;
import rnr.src.players.Crew;
import rnr.src.players.ScenarioPersonPassanger;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.PayOffManager;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.ChaseKoh;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.Helper;
import rnr.src.rnrscr.drvscripts;
import rnr.src.rnrscr.trackscripts;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 15,
    fieldWithDesiredStage = ""
)
public final class sc02070 extends stage {
    private static final int TIME_MENU_SCENARIO_VICTORY_OFFSET = 28000;
    private static final int LAG = 100;
    boolean menu_closed = false;
    private aiplayer cochCarDriver = null;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc02070(Object monitor) {
        super(monitor, "sc02070");
    }

    @Override
    protected void debugSetupPrecondition() {
        eng.lock();

        actorveh cochCar = eng.CreateCarForScenario(CarName.CAR_COCH, new matrixJ(),
                               Crew.getIgrokCar().gPosition().oPlusN(new vectorJ(20.0D, 20.0D, 0.0D)));

        Crew.addMappedCar("KOH", cochCar);
        this.cochCarDriver = new aiplayer("SC_KOHLOW");
        this.cochCarDriver.setModelCreator(new ScenarioPersonPassanger(), "koh");
        this.cochCarDriver.beDriverOfCar(cochCar);
        eng.unlock();
    }

    @Override
    protected void debugSetupPostcondition() {
        eng.lock();
        this.cochCarDriver.abondoneCar(Crew.getMappedCar("KOH"));
        Crew.deactivateMappedCar("KOH");
        eng.unlock();
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        trackscripts trs = new trackscripts(getSyncMonitor());

        eng.lock();
        eng.disableControl();

        actorveh car = Crew.getIgrokCar();

        car.sVeclocity(0.0D);

        actorveh carkoh = Crew.getMappedCar("KOH");

        carkoh.stop_autopilot();

        aiplayer player = null;

        if (ChaseKoh.getInstance() != null) {
            player = ChaseKoh.getInstance().getKohChased();
        } else if (null != this.cochCarDriver) {
            player = this.cochCarDriver;
        }

        if (player != null) {
            drvscripts.BlowSceneOtherPlayer(player, carkoh);
        }

        eng.unlock();
        trs.playSceneThreaded();
        eng.lock();

        if (ChaseKoh.getInstance() != null) {
            ChaseKoh.getInstance().endChase();
        }

        Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        PastFewDaysMenu.create();
        eng.unlock();
        trs.playSceneThreaded();

        long scene_prize = trackscripts.CreateSceneXMLCycle("02080", null, null);

        simplewaitFor(28000);
        scenarioscript.script.winScenario();
        VictoryMenu.createWinSocial(new VictoryMenuExitListener() {
            @Override
            public void OnMenuExit(int result) {
                sc02070.this.menu_closed = true;
            }
        });

        boolean to_break;

        do {
            simplewaitFor(100);
            eng.lock();
            to_break = this.menu_closed;
            eng.unlock();
        } while (!(to_break));

        eng.lock();
        scenetrack.StopScene(scene_prize);
        scenetrack.DeleteScene(scene_prize);
        Helper.restoreCameraToIgrokCar();
        PayOffManager.getInstance().makePayOff(PayOffManager.PAYOFF_NAMES[11]);
        eng.unlock();
        new sc03000(getSyncMonitor()).playSceneBody(automat);
    }

    void simplewaitFor(int timemillesec) {
        try {
            Thread.sleep(timemillesec);
        } catch (InterruptedException e) {
            eng.writeLog("Script Error. Stage sc00324 error.");
        }
    }
}


//~ Formatted in DD Std on 13/08/28
