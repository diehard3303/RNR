/*
 * @(#)AO_AnimatedModel.java   13/08/28
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


package rnr.src.rnrscr;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class AO_AnimatedModel {
    private static HashMap<Integer, Long> anmObjects = new HashMap<Integer, Long>();

    /**
     * Method description
     *
     *
     * @param id
     * @param person
     * @param sceneName
     */
    public static void Load(int id, SCRuniperson person, String sceneName) {
        Vector<SceneActorsPool> vec = new Vector<SceneActorsPool>();

        vec.add(new SceneActorsPool("model", person));

        long scene = scenetrack.CreateSceneXMLPool(sceneName, 517, vec, new preset(new matrixJ(), new vectorJ()));

        changeTrackParams(sceneName, scene);
        anmObjects.put(Integer.valueOf(id), Long.valueOf(scene));
    }

    /**
     * Method description
     *
     *
     * @param id
     */
    public static void Unload(int id) {
        scenetrack.DeleteScene(anmObjects.get(Integer.valueOf(id)).longValue());
        anmObjects.remove(Integer.valueOf(id));
    }

    private static void changeTrackParams(String model_name, long track) {
        move_time(track, 5.0D);
    }

    private static void move_time(long track, double max_time) {
        scenetrack.moveSceneTime(track, AdvancedRandom.getRandomDouble() * max_time);
    }

    static class preset {

        /** Field description */
        public matrixJ M;

        /** Field description */
        public vectorJ P;

        preset(matrixJ M, vectorJ P) {
            this.P = P;
            this.M = M;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
