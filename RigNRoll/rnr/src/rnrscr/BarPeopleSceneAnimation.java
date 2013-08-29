/*
 * @(#)BarPeopleSceneAnimation.java   13/08/28
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

import rnr.src.players.ImodelCreate;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class BarPeopleSceneAnimation {
    static int counter;

    static {
        counter = 0;
    }

    String[] ids = null;
    String[] modelNames = null;
    vectorJ shift = new vectorJ();
    String sceneName;

    BarPeopleSceneAnimation(String[] ids, String[] modelNames, String sceneName) {
        assert(ids.length == modelNames.length);
        this.ids = new String[ids.length + 1];
        this.modelNames = new String[modelNames.length + 1];

        for (int i = 0; i < ids.length; ++i) {
            this.ids[(i + 1)] = ids[i];
            this.modelNames[(i + 1)] = modelNames[i];
        }

        this.ids[0] = "model";
        this.sceneName = sceneName;
    }

    BarPeopleSceneAnimation(vectorJ shift, String[] ids, String[] modelNames, String sceneName) {
        assert(ids.length == modelNames.length);
        this.ids = new String[ids.length + 1];
        this.modelNames = new String[modelNames.length + 1];

        for (int i = 0; i < ids.length; ++i) {
            this.ids[(i + 1)] = ids[i];
            this.modelNames[(i + 1)] = modelNames[i];
        }

        this.ids[0] = "model";
        this.sceneName = sceneName;
        this.shift = shift;
    }

    private void addModelName(String modelName) {
        this.modelNames[0] = modelName;
    }

    private Vector<SceneActorsPool> createModelsPool(List<SCRuniperson> createdPersons) {
        Vector<SceneActorsPool> vec = new Vector<SceneActorsPool>();

        for (int i = 0; i < this.ids.length; ++i) {
            ImodelCreate creator = Bar.getModelCreator();
            SCRuniperson person = creator.create(this.modelNames[i], "" + (counter++), "bar_so_model");

            createdPersons.add(person);
            vec.add(new SceneActorsPool(this.ids[i], person));
        }

        return vec;
    }

    long createScene(vectorJ position, vectorJ direction, String modelName, List<SCRuniperson> createdPersons) {
        assert(createdPersons != null);
        addModelName(modelName);

        Vector<SceneActorsPool> vec = createModelsPool(createdPersons);
        matrixJ M = new matrixJ(direction, new vectorJ(0.0D, 0.0D, 1.0D));
        vectorJ realPos = position.oPlusN(matrixJ.mult(M, this.shift));
        long scene = scenetrack.CreateSceneXMLPool(this.sceneName + Bar.barType, 5, vec, new Data(M, realPos));

        scenetrack.moveSceneTime(scene, AdvancedRandom.getRandomDouble() * 15.0D);

        return scene;
    }

    static class Data {
        matrixJ M;
        vectorJ P;

        Data(matrixJ M, vectorJ P) {
            this.M = M;
            this.P = P;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
