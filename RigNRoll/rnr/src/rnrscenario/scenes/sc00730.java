/*
 * @(#)sc00730.java   13/08/26
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
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrloggers.ScriptsLogger;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.rnrscr.specobjects;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptEvents.EventsControllerHelper;

//~--- JDK imports ------------------------------------------------------------

import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@rnr.src.rnrscenario.consistency.ScenarioClass(
    scenarioStage = 9,
    fieldWithDesiredStage = ""
)
public final class sc00730 extends rnr.src.rnrscenario.stage {
    private static final String ANIMATION_FINISHED = "Topo chase animate Dakota ended";
    private static final String WHERE_TO_PLACE_PLAYER_CAR_WHILE_SCENE = "DakotaCrossRoadKeyPoint_OV_LB";
    private static rnr.src.rnrcore.vectorJ debugPosDakota = null;
    private static rnr.src.rnrcore.matrixJ debugMatrixDakota = null;
    private static boolean m_useDebugMatrices = false;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc00730(Object monitor) {
        super(monitor, "sc00730");
    }

    /**
     * Method description
     *
     *
     * @param pos
     * @param mat
     */
    public static void setDebugMatrices(rnr.src.rnrcore.vectorJ pos, rnr.src.rnrcore.matrixJ mat) {
        debugPosDakota = pos;
        debugMatrixDakota = mat;
        m_useDebugMatrices = true;
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        eng.console("switch Dword_Freight_Argosy_Passanger_Window 1");

        aiplayer bandDriver = aiplayer.getScenarioAiplayer("SC_BANDIT3");
        aiplayer bandShooter = aiplayer.getScenarioAiplayer("SC_BANDITJOE");
        actorveh banditCar = Crew.getMappedCar("ARGOSY BANDIT");

        bandDriver.beDriverOfCar(banditCar);
        bandShooter.bePassangerOfCar(banditCar);
        banditCar.setVisible();

        actorveh playerCar = Crew.getIgrokCar();
        cSpecObjects playerPlacement =
            specobjects.getInstance().GetLoadedNamedScenarioObject("DakotaCrossRoadKeyPoint_OV_LB");

        if (null != playerPlacement) {
            if (null != playerCar) {
                playerCar.sVeclocity(0.0D);
                playerCar.setHandBreak(true);
                playerCar.sPosition(playerPlacement.position, playerPlacement.matrix);
            }
        } else {
            ScriptsLogger.getInstance().log(Level.SEVERE, 4,
                                            "failed to find AO_ScriptObject: DakotaCrossRoadKeyPoint_OV_LB");
        }

        eng.unlock();

        Params p = new Params();

        if (m_useDebugMatrices) {
            p.M = debugMatrixDakota;
            p.P = debugPosDakota;
            m_useDebugMatrices = false;
        } else {
            p.M = new matrixJ(scenarioscript.script.getChaseTopoMatDakota());
            p.P = new vectorJ(scenarioscript.script.getChaseTopoPosDakota());
        }

        trackscripts trs = new trackscripts(getSyncMonitor());

        trs.PlaySceneXMLThreaded("00730", false, null, p);
        eng.lock();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();

        if (0 != banditCar.getAi_player()) {
            bandDriver.abondoneCar(banditCar);
            bandShooter.abondoneCar(banditCar);
        }

        eng.enableControl();
        eng.console("switch Dword_Freight_Argosy_Passanger_Window 0");

        if (null != playerCar) {
            playerCar.setHandBreak(false);
        }

        scenarioscript.script.chaseTopoexitAnimationDakota();
        EventsControllerHelper.messageEventHappened("Topo chase animate Dakota ended");
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    class Params {
        matrixJ M;
        vectorJ P;

        Params() {
            this.M = new matrixJ();
            this.P = new vectorJ();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
