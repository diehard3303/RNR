/*
 * @(#)sc00060part1.java   13/08/26
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
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.chase00090;
import rnr.src.rnrscr.drvscripts;
import rnr.src.rnrscr.parkingplace;
import rnr.src.rnrscr.specobjects;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptEvents.EventsControllerHelper;

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
public final class sc00060part1 extends sc00060base {

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc00060part1(Object monitor) {
        super(monitor, "sc00060part1");
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();
        super.prepare();

        drvscripts _drvscripts = new drvscripts();

        this.ourcar.makeParking(parkingplace.findParkingByName("Oxnard_Parking_01", this.ourcar.gPosition()), 3);
        eng.unlock();
        rnr.src.rnrscenario.tech.Helper.waitParked(this.ourcar);
        _drvscripts.playOutOffCarThreaded(Crew.getIgrok(), this.ourcar);
        eng.lock();

        long part1 = scenetrack.CreateSceneXML("00060part1", 0, specobjects.getBarPresets());

        eng.unlock();
        this.trs.PlaySceneXMLThreaded(part1, false);
        eng.lock();
        eng.disableControl();
        rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
        chase00090.createDorothyPassanger();
        drvscripts.playInsideCarFast(Crew.getIgrok(), this.ourcar);
        this.ourcar.leaveParking();

        Presets data = new Presets();

        data.car = this.ourcar;
        data.M = this.ourcar.gMatrix();
        data.P = this.ourcar.gPosition();
        data.P.z += 1.0D;

        long part2 = scenetrack.CreateSceneXML("00060part2", 0, specobjects.getBarPresets());

        eng.unlock();
        this.ourcar.SetHidden(2);
        this.trs.PlaySceneXMLThreaded(part2, false);
        this.ourcar.SetHidden(0);
        eng.lock();
        EventsControllerHelper.messageEventHappened("sceneendedwithDorothy");
        eng.unlock();
        super.epilogue();
        eng.lock();

        if (null != chase00090.getInstance()) {
            chase00090.getInstance().startChasingPlayer();
        }

        eng.unlock();
        eng.lock();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    static class Presets {
        actorveh car;
        actorveh banditcar;
        matrixJ M;
        vectorJ P;
    }
}


//~ Formatted in DD Std on 13/08/26
