/*
 * @(#)DebugScene.java   13/08/26
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
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.trackscripts;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class DebugScene extends stage {
    private static String sceneName = "00030";

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public DebugScene(Object monitor) {
        super(monitor, "DebugScene");
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setSceneName(String value) {
        sceneName = value;
    }

    private Data makeNearCarData() {
        actorveh car = Crew.getIgrokCar();
        vectorJ pos = car.gPosition();
        vectorJ dir = car.gDir();

        dir.mult(10.0D);
        pos.oPlus(dir);

        vectorJ posUp = new vectorJ(pos.x, pos.y, pos.z + 100.0D);
        vectorJ posDown = new vectorJ(pos.x, pos.y, pos.z - 100.0D);
        vectorJ resultPosition = Collide.collidePoint(posUp, posDown);

        if (resultPosition == null) {
            resultPosition = pos;
        }

        return new Data(new matrixJ(), resultPosition);
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        Data presets = makeNearCarData();
        trackscripts track = new trackscripts(getSyncMonitor());

        track.PlaySceneXMLThreaded(sceneName, false, presets);
    }

    static class Data {
        matrixJ M;
        vectorJ P;
        matrixJ mFinish;
        vectorJ pFinish;
        matrixJ M_180;

        Data(matrixJ M, vectorJ P) {
            this.M = M;
            this.P = P;
            this.mFinish = M;
            this.pFinish = P;
            this.M_180 = new matrixJ(M);
            this.M_180.v0.mult(-1.0D);
            this.M_180.v1.mult(-1.0D);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
