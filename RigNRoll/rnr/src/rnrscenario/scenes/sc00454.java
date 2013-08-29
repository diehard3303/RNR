/*
 * @(#)sc00454.java   13/08/26
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
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;
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
    scenarioStage = 4,
    fieldWithDesiredStage = ""
)
public final class sc00454 extends stage {

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc00454(Object monitor) {
        super(monitor, "sc00454");
    }

    static void teleportToBarWithBandits() {
        Place place = MissionSystemInitializer.getMissionsMap().getPlace("MP_Bar_SB_01");

        if (null == place) {
            return;
        }

        vectorJ position = place.getCoords();

        position.x += 50.0D;
        position.y += 50.0D;
        Crew.getIgrokCar().teleport(position);
        rnr.src.rnrscenario.tech.Helper.waitTeleport();
    }

    @Override
    protected void debugSetupPrecondition() {
        teleportToBarWithBandits();
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.makeParkAndComeOut("SB_Parking01", 4);

        trackscripts trs = new trackscripts(getSyncMonitor());

        trs.PlaySceneXMLThreaded("00454", false);
        rnr.src.rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.lock();
        eng.enableControl();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}


//~ Formatted in DD Std on 13/08/26
