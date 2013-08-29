/*
 * @(#)RaceTrajectory.java   13/08/26
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


package rnr.src.players;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashSet;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public final class RaceTrajectory {

    /** Field description */
    public static final String NONAME_RACE = "no name";

    /** Field description */
    public static final String PP_RACE = "pp_race";
    private static final Set<String> createdTrajectories = new HashSet();

    /**
     * Method description
     *
     */
    public static void createTrajectoryForRaceWithPP() {
        create("pp_race", "Race_Start_SB", "Race_Finish_AT");
    }

    @Deprecated
    private static native void createTrajectory(String paramString1, String paramString2, String paramString3);

    @Deprecated
    private static native void removeTrajectory(String paramString);

    @Deprecated
    private static native vectorJ gStart(String paramString);

    @Deprecated
    private static native vectorJ gFinish(String paramString);

    /**
     * Method description
     *
     *
     * @param trajectoryName
     * @param startZone
     * @param finishZone
     */
    public static void create(String trajectoryName, String startZone, String finishZone) {
        if (createdTrajectories.contains(trajectoryName)) {
            return;
        }

        createTrajectory(trajectoryName, startZone, finishZone);
        createdTrajectories.add(trajectoryName);
    }

    /**
     * Method description
     *
     *
     * @param trajectoryName
     */
    public static void remove(String trajectoryName) {
        if (!(createdTrajectories.contains(trajectoryName))) {
            return;
        }

        removeTrajectory(trajectoryName);
        createdTrajectories.remove(trajectoryName);
    }

    /**
     * Method description
     *
     *
     * @param trajectoryName
     *
     * @return
     */
    public static vectorJ getStart(String trajectoryName) {
        if (createdTrajectories.contains(trajectoryName)) {
            return gStart(trajectoryName);
        }

        eng.err("race trajectory '" + trajectoryName + "' has not been created");

        return new vectorJ();
    }

    /**
     * Method description
     *
     *
     * @param trajectoryName
     *
     * @return
     */
    public static vectorJ getFinish(String trajectoryName) {
        if (createdTrajectories.contains(trajectoryName)) {
            return gFinish(trajectoryName);
        }

        eng.err("race trajectory '" + trajectoryName + "' has not been created");

        return new vectorJ();
    }
}


//~ Formatted in DD Std on 13/08/26
