/*
 * @(#)sc00030base.java   13/08/26
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
import rnr.src.rnrcore.Collide;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.traffic;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.KohHelpManage;
import rnr.src.rnrscenario.controllers.Location;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.trackscripts;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 0,
    fieldWithDesiredStage = ""
)
public abstract class sc00030base extends stage {
    private static final double SHIFT_Y = 7.0D;
    private static final double SHIFT_X = -1.0D;
    private static final String sceneNamepart1 = "00030part1";
    static boolean NO_TRAFFIC_IN_SCENE;

    static {
        NO_TRAFFIC_IN_SCENE = false;
    }

    /**
     * Constructs ...
     *
     *
     * @param monitor
     * @param sceneName
     */
    public sc00030base(Object monitor, String sceneName) {
        super(monitor, sceneName);
    }

    protected abstract String getSceneName();

    private void rotateSceneAcordingApproachingDirection(vectorJ pos, matrixJ Mscene, actorveh meet_car) {
        vectorJ R = meet_car.gPosition().oMinusN(pos);

        if (0.0D >= Mscene.v1.dot(R)) {
            return;
        }

        Mscene.v0.mult(-1.0D);
        Mscene.v1.mult(-1.0D);
    }

    private presets makePresets(actorveh car) {
        presets pres = new presets();

        pres.M = car.gMatrix();
        pres.P = car.gPosition();

        vectorJ shiftY = new vectorJ(pres.M.v1);

        shiftY.mult(7.0D);

        vectorJ shiftX = new vectorJ(pres.M.v0);

        shiftX.mult(-1.0D);
        pres.P.oPlus(shiftY.oPlusN(shiftX));

        vectorJ pos1 = new vectorJ(pres.P);
        vectorJ pos2 = new vectorJ(pres.P);

        pos1.z -= 100.0D;
        pos2.z += 100.0D;

        vectorJ colPoint = Collide.collidePoint(pos1, pos2);

        if (colPoint != null) {
            pres.P = colPoint;
        }

        return pres;
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();

        actorveh cochCar = Crew.getMappedCar("KOH");
        presets pres = makePresets(cochCar);

        if (NO_TRAFFIC_IN_SCENE) {
            traffic.enterCutSceneMode(pres.P);
        }

        eng.disableControl();

        actorveh playerCar = Crew.getIgrokCar();

        rotateSceneAcordingApproachingDirection(pres.P, pres.M, playerCar);

        Location location = KohHelpManage.getInstance().getNickLocation();

        assert(null != location) : "location for nick's car is null";
        playerCar.sVeclocity(0.0D);
        playerCar.setHandBreak(true);
        playerCar.sPosition(location.getPosition(), location.getOrientation());

        trackscripts track = new trackscripts(getSyncMonitor());

        eng.unlock();
        track.PlaySceneXMLThreaded("00030part1", true, pres);
        eng.lock();
        playerCar.sVeclocity(0.0D);
        cochCar.sVeclocity(0.0D);
        pres = makePresets(cochCar);
        eng.unlock();
        track.PlaySceneXMLThreaded(getSceneName(), false, pres);
        eng.lock();
        eng.enableControl();
        cochCar.deactivate();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.SwitchDriver_in_cabin(playerCar.getCar());
        KohHelpManage.questFinished();

        if (NO_TRAFFIC_IN_SCENE) {
            traffic.setTrafficMode(0);
        }

        playerCar.setHandBreak(false);
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static final class presets {
        matrixJ M;
        vectorJ P;
    }
}


//~ Formatted in DD Std on 13/08/26
