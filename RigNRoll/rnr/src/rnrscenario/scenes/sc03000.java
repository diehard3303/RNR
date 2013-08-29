/*
 * @(#)sc03000.java   13/08/26
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

import rnr.src.menu.JavaEvents;
import rnr.src.menuscript.TotalVictoryMenu;
import rnr.src.menuscript.VictoryMenuExitListener;
import rnr.src.players.CarName;
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.players.vehicle;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.traffic;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscenario.tech.SleepOnTime;
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.rnrscr.drvscripts;
import rnr.src.rnrscr.specobjects;
import rnr.src.rnrscr.trackscripts;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 15,
    fieldWithDesiredStage = ""
)
public final class sc03000 extends stage {

    /** Field description */
    public static final String SCENE_OBZOR = "03000_obzor";

    /** Field description */
    public static final String[] SCENES_CHOOSE = { "03020", "03010", "03000" };

    /** Field description */
    public static final String[] SCENES_DAWN = { "03030_2", "03030_3", "03030_1" };

    /** Field description */
    public static final int MONICA_FINAL = 0;

    /** Field description */
    public static final int DOROTHY_FINAL = 1;

    /** Field description */
    public static final int SELF_FINAL = 2;

    /** Field description */
    public static final String PROC_SCENE = "KeyPoint_FinalScene";

    /** Field description */
    public static final String POINT_FINAL_SCENE = "FinalScene";

    /** Field description */
    public static final String POINT_DAWN_START = "DawnStart";

    /** Field description */
    public static final String POINT_DAWN_END = "DawnEnd";
    boolean has_menu = false;
    int result_menu_close = -1;
    int INDEX = 0;
    private boolean needTakeOffPlayerCar = true;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc03000(Object monitor) {
        super(monitor, "sc03000");
    }

    @Override
    protected void debugSetupPrecondition() {
        this.needTakeOffPlayerCar = false;
    }

    @Override
    protected void debugSetupPostcondition() {
        this.needTakeOffPlayerCar = true;
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        trackscripts trs = new trackscripts(getSyncMonitor());

        eng.lock();
        eng.doWide(true);
        this.INDEX = 2;
        traffic.enterChaseModeSmooth();
        eng.disableControl();
        eng.startMangedFadeAnimation();

        vectorJ pos = eng.getControlPointPosition("FinalScene");
        actorveh playerCarGepard = Crew.getIgrokCar();

        playerCarGepard.teleport(pos);
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.waitTeleport();
        eng.lock();
        playerCarGepard.setHandBreak(true);

        cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject("KeyPoint_FinalScene");
        Data _data;
        DataObzor _data_obzor;

        if (null != crossroad) {
            matrixJ obzorM = new matrixJ(crossroad.matrix);

            obzorM.v0.mult(-1.0D);
            obzorM.v1.mult(-1.0D);

            vectorJ pObzor = new vectorJ(crossroad.position);
            vectorJ shift = new vectorJ(crossroad.matrix.v0);

            shift.mult(2.0D);
            pObzor.oPlus(shift);
            _data_obzor = new DataObzor(obzorM, crossroad.position, pObzor);
            _data = new Data(crossroad.matrix, crossroad.position);
        } else {
            _data = new Data(new matrixJ(), new vectorJ());

            matrixJ obzorM = new matrixJ();

            obzorM.v0.mult(-1.0D);
            obzorM.v1.mult(-1.0D);
            _data_obzor = new DataObzor(obzorM, new vectorJ(), new vectorJ());
        }

        eng.SwitchDriver_outside_cabin(playerCarGepard.getCar());

        SCRuniperson personMC = Crew.getIgrok().getModel();

        personMC.SetPosition(_data_obzor.P.oPlusN(new vectorJ(0.0D, 0.0D, -100.0D)));
        personMC.stop();

        vectorJ p_forward = new vectorJ(_data.M.v1);
        vectorJ p_side = new vectorJ(_data.M.v0);

        p_forward.mult(100.0D);
        p_side.mult(100.0D);

        actorveh car_DOROTHY = eng.CreateCarForScenario(CarName.CAR_DOROTHY, _data.M,
                                   _data.P.oPlusN(p_forward).oPlusN(p_side));
        actorveh car_MONICA = eng.CreateCarForScenario(CarName.CAR_MONICA, _data.M, _data.P.oPlusN(p_forward));

        placeCars(car_DOROTHY, car_MONICA, playerCarGepard, _data.P, _data.M);
        eng.doWide(false);
        eng.unlock();
        trs.PlaySceneXMLThreaded("03000_obzor", false, _data_obzor);
        trs.PlaySceneXMLThreaded(SCENES_CHOOSE[this.INDEX], false, _data);
        eng.startMangedFadeAnimation();
        eng.enableControl();
        rnr.src.rnrscenario.tech.Helper.waitSimpleState();
        eng.disableControl();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();

        vectorJ pos_dawn = eng.getControlPointPosition("DawnStart");

        pos_dawn.x -= 100.0D;
        pos_dawn.y += 8.0D;
        playerCarGepard.teleport(pos_dawn);
        rnr.src.rnrscenario.tech.Helper.waitTeleport();
        eng.lock();
        eng.doWide(true);
        playerCarGepard.sPosition(pos_dawn);

        matrixJ Mscene = new matrixJ();

        Mscene.v0 = new vectorJ(0.0D, 1.0D, 0.0D);
        Mscene.v1 = new vectorJ(-1.0D, 0.0D, 0.0D);
        _data = new Data(Mscene, pos_dawn);
        playerCarGepard.UpdateCar();
        playerCarGepard.registerCar("ourcar");
        eng.unlock();
        trs.PlaySceneXMLThreaded(SCENES_DAWN[this.INDEX], false, _data);
        eng.lock();

        boolean hasContest = rnr.src.rnrcore.Helper.hasContest();

        if (!(hasContest)) {
            this.has_menu = true;
            TotalVictoryMenu.createGameOverTotal(new VictoryMenuExitListener() {
                @Override
                public void OnMenuExit(int result) {
                    sc03000.this.result_menu_close = result;
                }
            });
        }

        eng.unlock();
        car_DOROTHY.deactivate();
        car_MONICA.deactivate();

        if (this.has_menu) {
            while (true) {
                new SleepOnTime(100);
                eng.lock();

                if (-1 != this.result_menu_close) {
                    eng.unlock();

                    break;
                }

                eng.unlock();
            }

            switch (this.result_menu_close) {
             case 0 :
                 teleportToOfficeAndTakeOffGepard();

                 break;

             case 1 :
                 eng.lock();
                 JavaEvents.SendEvent(23, 1, this);
                 eng.unlock();
            }
        } else {
            teleportToOfficeAndTakeOffGepard();
        }

        eng.lock();
        eng.doWide(false);
        traffic.setTrafficMode(0);
        eng.enableControl();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    private void teleportToOfficeAndTakeOffGepard() {
        eng.startMangedFadeAnimation();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();

        actorveh ourcar = Crew.getIgrokCar();

        if (this.needTakeOffPlayerCar) {
            matrixJ M = ourcar.gMatrix();
            vectorJ P = ourcar.gPosition();
            vehicle currentCar = ourcar.querryCurrentCar();
            vehicle lastVehicleFromGarage = rnr.src.rnrscenario.Helper.getLiveCarFromGarage();

            vehicle.changeLiveVehicle(ourcar, lastVehicleFromGarage, M, P);
            rnr.src.rnrscenario.tech.Helper.waitVehicleChanged();
            currentCar.delete();
        }

        vectorJ ourOfficePosition = eng.getNamedOfficePosition("office_OV_01");

        ourcar.teleport(ourOfficePosition);
        rnr.src.rnrscenario.tech.Helper.waitTeleport();
        drvscripts.playInsideCarFast(Crew.getIgrok(), ourcar);
        eng.SwitchDriver_in_cabin(ourcar.getCar());
    }

    private void placeCars(actorveh car_kenworth, actorveh car_dodge, actorveh car_our, vectorJ P, matrixJ M) {
        vectorJ shiftY = new vectorJ(M.v1);

        shiftY.mult(1.0D);
        shiftY.norm();
        shiftY.mult(11.5D);

        vectorJ shiftYkenworth = new vectorJ(M.v1);

        shiftYkenworth.mult(1.0D);
        shiftYkenworth.norm();
        shiftYkenworth.mult(13.5D);

        vectorJ shiftY_our = new vectorJ(M.v1);

        shiftY_our.mult(1.0D);
        shiftY_our.norm();
        shiftY_our.mult(11.5D);

        vectorJ shiftX = new vectorJ(M.v0);

        shiftX.norm();

        vectorJ shiftX_kenworth = new vectorJ(shiftX);

        shiftX_kenworth.mult(4.0D);

        vectorJ shiftX_dodge = new vectorJ(shiftX);

        shiftX_dodge.mult(-1.5D);

        vectorJ shiftX_our = new vectorJ(shiftX);

        shiftX_our.mult(8.5D);

        vectorJ pos_kenworth = new vectorJ(P);
        vectorJ pos_dodge = new vectorJ(P);
        vectorJ pos_our = new vectorJ(P);

        pos_kenworth.oPlus(shiftYkenworth);
        pos_kenworth.oPlus(shiftX_kenworth);
        pos_dodge.oPlus(shiftY);
        pos_dodge.oPlus(shiftX_dodge);
        pos_our.oPlus(shiftY_our);
        pos_our.oPlus(shiftX_our);

        matrixJ M_180 = new matrixJ(M);

        M_180.v0.mult(-1.0D);
        M_180.v1.mult(-1.0D);
        car_kenworth.sPosition(pos_kenworth, M_180);
        car_dodge.sPosition(pos_dodge, M_180);
        car_our.sPosition(pos_our, M_180);
    }

    static class Data {
        vectorJ P;
        matrixJ M;
        matrixJ M_180;

        Data(matrixJ M, vectorJ P) {
            this.P = P;
            this.M = M;
            this.M_180 = new matrixJ(M);
            this.M_180.v0.mult(-1.0D);
            this.M_180.v1.mult(-1.0D);
        }
    }


    static class DataObzor {
        vectorJ P;
        vectorJ Pshift;
        matrixJ M;
        matrixJ M_180;

        DataObzor(matrixJ M, vectorJ P, vectorJ Pshift) {
            this.P = P;
            this.Pshift = Pshift;
            this.M = M;
            this.M_180 = new matrixJ(M);
            this.M_180.v0.mult(-1.0D);
            this.M_180.v1.mult(-1.0D);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
