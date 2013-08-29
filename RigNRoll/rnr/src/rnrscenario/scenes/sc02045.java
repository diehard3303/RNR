/*
 * @(#)sc02045.java   13/08/28
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
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.rnrscr.specobjects;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptEvents.EventsControllerHelper;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * The Class sc02045.
 */
@ScenarioClass(
    scenarioStage = 14,
    fieldWithDesiredStage = ""
)
public final class sc02045 extends stage {

    /** Field description */
    public static final boolean DEBUG = false;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc02045(Object monitor) {
        super(monitor, "sc02045");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        if (!(scenarioscript.script.setPlayerCarHasBlown())) {
            return;
        }

        eng.lock();
        eng.doWide(true);
        eng.disableControl();

        actorveh car = Crew.getIgrokCar();

        car.sVeclocity(0.0D);

        Vector v = new Vector();
        SCRuniperson person = SCRuniperson.createLoadedObject("Baza_bandits");
        SCRuniperson shooter = SCRuniperson.createCutSceneAmbientPerson("Man_024", null);

        v.add(new SceneActorsPool("baza", person));
        v.add(new SceneActorsPool("shooter", shooter));

        cSpecObjects crossroad = specobjects.getInstance().GetLoadedNamedScenarioObject("KeyPoint_2045");
        Data _data;

        if (crossroad != null) {
            _data = new Data(new matrixJ(), crossroad.position);
        } else {
            matrixJ M = new matrixJ();
            vectorJ P = new vectorJ(3618.4899999999998D, 13370.0D, 320.0D);

            _data = new Data(M, P);
        }

        trackscripts trs = new trackscripts(getSyncMonitor());
        long fadescene = trs.createSceneXML("justfadecutscene", null, _data);
        long scene1 = trs.createSceneXML("02045", v, _data);

        eng.unlock();
        trs.PlaySceneXMLThreaded(fadescene, true);
        trs.PlaySceneXMLThreaded(scene1, false);
        eng.lock();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        eng.enableControl();
        eng.doWide(false);
        EventsControllerHelper.messageEventHappened("blowcar");
        eng.unlock();
        eng.lock();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static final class Data {
        matrixJ M;
        vectorJ P;

        Data() {}

        Data(matrixJ M, vectorJ P) {
            this.M = M;
            this.P = P;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
