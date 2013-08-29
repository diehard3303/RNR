/*
 * @(#)MeetingPlace.java   13/08/28
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


package rnr.src.rnrscenario.controllers;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.rnrscr.specobjects;

@ScenarioClass(
    scenarioStage = -1,
    fieldWithDesiredStage = "scenarioStage"
)
final class MeetingPlace {
    private final int scenarioStage;
    private final Location cochLocation;
    private final Location nickLocation;

    MeetingPlace(int scenarioStage, String cochPointName, String nickPointName) throws IllegalStateException {
        cSpecObjects cochPlacementPoint = specobjects.getInstance().GetLoadedNamedScenarioObject(cochPointName);
        Location cochLocation;

        if (null != cochPlacementPoint) {
            cochLocation = new Location(scenarioStage, cochPlacementPoint.position, cochPlacementPoint.matrix);
        } else {
            throw new IllegalStateException("illegal game data state: point coch point was not found");
        }

        cSpecObjects nickPlacementPoint = specobjects.getInstance().GetLoadedNamedScenarioObject(nickPointName);
        Location nickLocation;

        if (null != nickPlacementPoint) {
            nickLocation = new Location(scenarioStage, nickPlacementPoint.position, nickPlacementPoint.matrix);
        } else {
            throw new IllegalStateException("illegal game data state: point nick point was not found");
        }

        this.cochLocation = cochLocation;
        this.nickLocation = nickLocation;
        this.scenarioStage = scenarioStage;
    }

    Location getCochLocation() {
        return this.cochLocation;
    }

    Location getNickLocation() {
        return this.nickLocation;
    }
}


//~ Formatted in DD Std on 13/08/28
