/*
 * @(#)BarPeopleScene.java   13/08/28
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
import rnr.src.rnrcore.vectorJ;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BarPeopleScene {
    private static BarPeopleSceneAnimation[] manAnimations;
    private static BarPeopleSceneAnimation[] womanAnimations;
    private static BarPeopleSceneAnimation[] manBarStandAnimations;
    private static BarPeopleSceneAnimation[] womanBarStandAnimations;
    private static BarPeopleSceneAnimation[] barmanAnimations;
    private static Map<Long, List<SCRuniperson>> scenePersons;

    static {
        manAnimations = new BarPeopleSceneAnimation[] {
            new BarPeopleSceneAnimation(new String[] { "plate", "fork" }, new String[] { "Plate1",
                "Fork1" }, "table_m1_bar") };
        womanAnimations = new BarPeopleSceneAnimation[] {
            new BarPeopleSceneAnimation(new String[] { "coca" }, new String[] { "Coca1" }, "table_w1_bar"),
            new BarPeopleSceneAnimation(new String[] { "cup", "plate" }, new String[] { "Cup1", "Plate2" },
                                        "table_w2_bar"),
            new BarPeopleSceneAnimation(new String[] { "cup", "plate", "spoon" }, new String[] { "Cup1", "Plate2",
                "Spoonl1" }, "table_w3_bar"), new BarPeopleSceneAnimation(new String[] { "plate1", "plate2", "cup",
                "fork" }, new String[] { "Plate1", "Plate2", "Cup1", "Fork1" }, "table_w4_bar") };
        manBarStandAnimations = new BarPeopleSceneAnimation[] {
            new BarPeopleSceneAnimation(new String[] { "glass" }, new String[] { "GlassSmall" }, "barstand_m1_bar"),
            new BarPeopleSceneAnimation(new String[] { "glass" }, new String[] { "GlassSmall" }, "barstand_m2_bar") };
        womanBarStandAnimations = new BarPeopleSceneAnimation[] {
            new BarPeopleSceneAnimation(new String[] { "glass" }, new String[] { "Coca1" }, "barstand_w1_bar"),
            new BarPeopleSceneAnimation(new String[0], new String[0], "barstand_w2_bar") };
        barmanAnimations = new BarPeopleSceneAnimation[] {
            new BarPeopleSceneAnimation(new String[] { "wine", "cloth" }, new String[] { "Wine1",
                "BarmenCloth" }, "barman_bar") };
        scenePersons = new HashMap();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    static List<SCRuniperson> getScenePersons(long scene) {
        if (scenePersons.containsKey(Long.valueOf(scene))) {
            return (scenePersons.get(Long.valueOf(scene)));
        }

        return new ArrayList();
    }

    static void forgetScenePersons(long scene) {
        if (scenePersons.containsKey(Long.valueOf(scene))) {
            scenePersons.remove(Long.valueOf(scene));
        }
    }

    /**
     * Method description
     *
     *
     * @param poss
     * @param dirs
     * @param models
     *
     * @return
     */
    public static long[] createTableScenes(vectorJ[] poss, vectorJ[] dirs, List<ModelForBarScene> models) {
        assert((poss != null) && (dirs != null) && (poss.length == dirs.length));

        int size = (models.size() > poss.length)
                   ? poss.length
                   : models.size();
        int[] indexes = shiffleIndexes(poss.length);

        assert(indexes.length >= size);

        long[] scenes = new long[size];

        for (int i = 0; i < size; ++i) {
            ModelForBarScene model = models.get(i);

            scenes[i] = createRandomScene((model.isMan())
                                          ? manAnimations
                                          : womanAnimations, model.getModelName(), poss[indexes[i]], dirs[indexes[i]]);
        }

        return scenes;
    }

    /**
     * Method description
     *
     *
     * @param poss
     * @param dirs
     * @param models
     *
     * @return
     */
    public static long[] createBarStandScenes(vectorJ[] poss, vectorJ[] dirs, List<ModelForBarScene> models) {
        assert((poss != null) && (dirs != null) && (poss.length == dirs.length));

        int size = (models.size() > poss.length)
                   ? poss.length
                   : models.size();
        int[] indexes = shiffleIndexes(poss.length);

        assert(indexes.length >= size);

        long[] scenes = new long[size];

        for (int i = 0; i < size; ++i) {
            ModelForBarScene model = models.get(i);

            scenes[i] = createRandomScene((model.isMan())
                                          ? manBarStandAnimations
                                          : womanBarStandAnimations, model.getModelName(), poss[indexes[i]],
                                          dirs[indexes[i]]);
        }

        return scenes;
    }

    /**
     * Method description
     *
     *
     * @param poss
     * @param dirs
     * @param models
     *
     * @return
     */
    public static long[] createBarmanScenes(vectorJ[] poss, vectorJ[] dirs, List<ModelForBarScene> models) {
        assert((poss != null) && (dirs != null) && (poss.length == dirs.length));

        int size = (models.size() > poss.length)
                   ? poss.length
                   : models.size();
        int[] indexes = shiffleIndexes(poss.length);

        assert(indexes.length >= size);

        long[] scenes = new long[size];

        for (int i = 0; i < size; ++i) {
            ModelForBarScene model = models.get(i);

            scenes[i] = createRandomScene(barmanAnimations, model.getModelName(), poss[indexes[i]], dirs[indexes[i]]);
        }

        return scenes;
    }

    private static int[] shiffleIndexes(int size) {
        int[] indexes = new int[size];

        for (int i = 0; i < indexes.length; ++i) {
            indexes[i] = i;
        }

        for (int i = 0; i < indexes.length; ++i) {
            int index1 = AdvancedRandom.RandFromInreval(0, indexes.length - 1);
            int index2 = AdvancedRandom.RandFromInreval(0, indexes.length - 1);

            if (AdvancedRandom.probability(0.9D)) {
                int valueHold = indexes[index1];

                indexes[index1] = indexes[index2];
                indexes[index2] = valueHold;
            }
        }

        return indexes;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static long createRandomScene(BarPeopleSceneAnimation[] animation, String modelName, vectorJ position,
            vectorJ direction) {
        int indexAnimation = AdvancedRandom.RandFromInreval(0, animation.length - 1);
        ArrayList listPersonsPerScene = new ArrayList();
        long scene = animation[indexAnimation].createScene(position, direction, modelName, listPersonsPerScene);

        scenePersons.put(Long.valueOf(scene), listPersonsPerScene);

        return scene;
    }
}


//~ Formatted in DD Std on 13/08/28
