/*
 * @(#)sc00860.java   13/08/28
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

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.players.semitrailer;
import rnr.src.rnrcore.SCRcamera;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.traffic;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.SimplePresets;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptActions.MakeCallAction;
import rnr.src.scriptEvents.EventsControllerHelper;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
@ScenarioClass(
    scenarioStage = 9,
    fieldWithDesiredStage = ""
)
public final class sc00860 extends stage {
    private static final int SEMITRAILER_SHIFT_RELATIVE_TO_HOST_CAR = 5;
    private static final String ANIMATION_CALL = "Topo chase customer call 3";
    private static final String FINISHED_CALL = "cb00860 finished";
    private static final String ANIMATION_FINISHED = "Topo chase animate dark truck ended";
    private static final String UNSUSPEND = "unsuspend";
    private static boolean m_debugScene = false;
    private static semitrailer semitrailerToDeliver;
    private final Object synchronizationMonitor = new Object();
    private boolean waitingForCbvFinish = false;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc00860(Object monitor) {
        super(monitor, "sc00860");
    }

    /**
     * Method description
     *
     *
     * @param semitrailerToDeliver
     */
    public static void setSemitrailerToDeliver(semitrailer semitrailerToDeliver) {
        sc00860.semitrailerToDeliver = semitrailerToDeliver;
    }

    /**
     * Method description
     *
     */
    public static void setDebug() {
        m_debugScene = true;
    }

    /**
     * Method description
     *
     */
    public void unsuspend() {
        synchronized (this.synchronizationMonitor) {
            this.synchronizationMonitor.notify();
            this.waitingForCbvFinish = false;
        }
    }

    /**
     * Method description
     *
     *
     * @throws InterruptedException
     */
    public void waitUntilCbvFinished() throws InterruptedException {
        synchronized (this.synchronizationMonitor) {
            while (this.waitingForCbvFinish) {
                this.synchronizationMonitor.wait();
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        traffic.enterChaseModeImmediately();

        actorveh playerCar = Crew.getIgrokCar();
        actorveh darkTruck = Crew.getMappedCar("DARK TRUCK");
        vectorJ pos = darkTruck.gPosition();
        vectorJ dir = darkTruck.gDir();

        dir.mult(-40.0D);
        pos.oPlus(dir);
        playerCar.sVeclocity(0.0D);
        playerCar.setHandBreak(true);

        Vector actors = new Vector();

        actors.add(playerCar);
        actorveh.aligncars(actors, pos, 0.0D, 0.0D, 1, 1);
        SCRcamera.jumpInCabin(true);
        this.waitingForCbvFinish = true;
        EventsControllerHelper.getInstance().addMessageListener(this, "unsuspend", "cb00860 finished");
        eng.predefinedAnimationLaunchedFromJava(false);

        if (!(m_debugScene)) {
            EventsControllerHelper.messageEventHappened("Topo chase customer call 3");
        } else {
            MakeCallAction actionCall = new MakeCallAction(scenarioscript.script);

            actionCall.immediate = true;
            actionCall.name = "cb00860";
            actionCall.act();
            m_debugScene = false;
        }

        eng.unlock();

        try {
            waitUntilCbvFinished();
            eng.waitUntilEngineCanPlayScene();
        } catch (InterruptedException e) {
            System.err.print(e.getMessage());
        }

        eng.lock();

        if (null != semitrailerToDeliver) {
            vectorJ semitarailerPosition = playerCar.gPosition();

            semitarailerPosition.oMinus(playerCar.gMatrix().v1.getMultiplied(5.0D));
            semitrailerToDeliver.setVelocityModule(0.0D);
            semitrailerToDeliver.setMatrix(playerCar.gMatrix());
            semitrailerToDeliver.setPosition(semitarailerPosition);
            playerCar.attachSemitrailer(semitrailerToDeliver);
        }

        eng.disableControl();
        EventsControllerHelper.getInstance().removeMessageListener(this, "unsuspend", "cb00860 finished");

        aiplayer bill = aiplayer.getScenarioAiplayer("SC_BILL_OF_LANDING");

        bill.bePassangerOfCar_simple(playerCar);

        Vector pool = new Vector();

        pool.add(new SceneActorsPool("bill", bill.getModel()));
        SCRcamera.jumpInCabin(false);

        if (null != semitrailerToDeliver) {
            playerCar.attachSemitrailer(semitrailerToDeliver);
        }

        eng.unlock();

        SimplePresets pres = scenarioscript.script.prepareDarkTruckMatrix();
        Presets data = new Presets();

        data.car = playerCar;
        data.M = pres.M;
        data.P = pres.P;

        trackscripts trs = new trackscripts(getSyncMonitor());

        trs.PlaySceneXMLThreaded("00860_part1", true, pool, data);
        trs.PlaySceneXMLThreaded("00860_part2", true, data);
        trs.PlaySceneXMLThreaded("00860_part3", false, pool, data);
        eng.lock();
        bill.abondoneCar(playerCar);
        traffic.setTrafficMode(0);
        semitrailerToDeliver = null;
        EventsControllerHelper.messageEventHappened("Topo chase animate dark truck ended");
        scenarioscript.script.finishChaseTopo();
        playerCar.teleport(eng.getNamedOfficePosition("office_OV_01"));
        playerCar.setHandBreak(false);
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.waitTeleport();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        trs.PlaySceneXMLThreaded("00861", false);
        eng.startMangedFadeAnimation();
        rnr.src.rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.lock();
        eng.enableControl();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Presets {
        matrixJ M;
        vectorJ P;
        actorveh car;
    }
}


//~ Formatted in DD Std on 13/08/28
