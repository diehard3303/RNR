/*
 * @(#)sc00060base.java   13/08/26
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

import rnr.src.players.Chase;
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.Helper;
import rnr.src.rnrscr.trackscripts;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 1,
    fieldWithDesiredStage = ""
)
public abstract class sc00060base extends stage {
    int millisekonds_fora = 6000;
    int millisekonds_fora_short = 10000;
    Object syncMonitor = null;
    actorveh ourcar = null;
    actorveh BanditCar = null;
    trackscripts trs = null;
    aiplayer Bandit1 = null;
    boolean wasPrepared = false;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     * @param sceneName
     */
    public sc00060base(Object monitor, String sceneName) {
        super(monitor, sceneName);
    }

    void prepare() {
        if (this.wasPrepared) {
            return;
        }

        this.ourcar = Crew.getIgrokCar();
        eng.writeLog("SCENARIO RescueDorothy_succeeded");
        this.Bandit1 = aiplayer.getSimpleAiplayer("SC_BANDIT3LOW");
        this.BanditCar = Crew.getMappedCar("ARGOSY BANDIT");
        this.trs = new trackscripts(getSyncMonitor());
        eng.doWide(true);
        this.wasPrepared = true;
    }

    void epilogue() {
        if (!(this.wasPrepared)) {
            return;
        }

        eng.lock();
        Helper.restoreCameraToIgrokCar();
        this.Bandit1.beDriverOfCar(this.BanditCar);
        this.BanditCar.leaveParking();

        Chase chase = new Chase();

        chase.setParameters("easyChasing");
        chase.makechase(this.BanditCar, this.ourcar);
        this.ourcar.stop_autopilot();
        eng.unlock();
        eng.console("switch Dword_Freight_Argosy_Passanger_Window 1");
        runVehicleScene();
    }

    private void runVehicleScene() {
        eng.lock();

        actorveh carBandit = this.BanditCar;

        carBandit.registerCar("banditcar");
        this.ourcar.registerCar("ourcar");

        vectorJ pos = eng.getControlPointPosition("Room_Repair_Oxnard_01");

        assert(pos != null);

        Data data = new Data(new matrixJ(), pos, Crew.getIgrokCar());

        data.M_180 = new matrixJ();
        data.M_180.v0 = new vectorJ(-1.0D, 0.0D, 0.0D);
        data.M_180.v1 = new vectorJ(0.0D, -1.0D, 0.0D);
        eng.unlock();
        this.trs.PlaySceneXMLThreaded("00061", false, data);
        eng.lock();
        eng.doWide(false);
        eng.enableControl();
        this.ourcar.sVeclocity(20.0D);
        this.BanditCar.sVeclocity(10.0D);
        Helper.restoreCameraToIgrokCar();
        eng.unlock();
    }

    void waitFor(int timemillesec) throws InterruptedException {
        Thread.sleep(timemillesec);
    }

    static class Data {
        matrixJ M;
        matrixJ M_180;
        vectorJ P;
        actorveh car;

        Data() {}

        Data(matrixJ M, vectorJ P, actorveh car) {
            this.M = M;
            this.P = P;
            this.car = car;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
