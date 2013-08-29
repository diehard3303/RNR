/*
 * @(#)sc01080.java   13/08/26
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

import rnr.src.players.CutSceneAuxPersonCreator;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.eng;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.trackscripts;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = -2,
    fieldWithDesiredStage = ""
)
public final class sc01080 extends stage {

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc01080(Object monitor) {
        super(monitor, "sc01080");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        eng.disableControl();

        aiplayer cips1 = new aiplayer("SC_CHIPSY");

        cips1.sPoolBased("chips1");
        cips1.setModelCreator(new CutSceneAuxPersonCreator(), "chips1");

        aiplayer cips2 = new aiplayer("SC_CHIPSY");

        cips2.sPoolBased("chips2");
        cips2.setModelCreator(new CutSceneAuxPersonCreator(), "chips2");

        aiplayer coffe1 = new aiplayer("SC_COFFE");

        coffe1.sPoolBased("coffee1");
        coffe1.setModelCreator(new CutSceneAuxPersonCreator(), "coffee1");

        aiplayer coffe2 = new aiplayer("SC_COFFE");

        coffe2.sPoolBased("coffee2");
        coffe2.setModelCreator(new CutSceneAuxPersonCreator(), "coffee2");

        aiplayer coffe3 = new aiplayer("SC_COFFE");

        coffe3.sPoolBased("coffee3");
        coffe3.setModelCreator(new CutSceneAuxPersonCreator(), "coffee3");

        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
        SCRuniperson person_cips1 = cips1.getModel();
        SCRuniperson person_cips2 = cips2.getModel();
        SCRuniperson person_coffe1 = coffe1.getModel();
        SCRuniperson person_coffe2 = coffe2.getModel();
        SCRuniperson person_coffe3 = coffe3.getModel();

        person_cips1.lockPerson();
        person_cips2.lockPerson();
        person_coffe1.lockPerson();
        person_coffe2.lockPerson();
        person_coffe3.lockPerson();
        pool.add(new SceneActorsPool("chips1", person_cips1));
        pool.add(new SceneActorsPool("chips2", person_cips2));
        pool.add(new SceneActorsPool("coffee1", person_coffe1));
        pool.add(new SceneActorsPool("coffee2", person_coffe2));
        pool.add(new SceneActorsPool("coffee3", person_coffe3));
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.makeComeOut();

        trackscripts trs = new trackscripts(getSyncMonitor());

        trs.PlaySceneXMLThreaded("01080_big_bone", false, pool, new Object());
        eng.lock();
        person_cips1.unlockPerson();
        person_cips2.unlockPerson();
        person_coffe1.unlockPerson();
        person_coffe2.unlockPerson();
        person_coffe3.unlockPerson();
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.enableControl();
        eng.lock();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }
}


//~ Formatted in DD Std on 13/08/26
