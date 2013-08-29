/*
 * @(#)RaceHistory.java   13/08/28
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

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrscr.RaceHistory.singleRace;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class RaceHistory {
    static RaceHistory RH = new RaceHistory();
    static final int simpleRace = 1;
    static final int bigRace = 2;
    static final int looserPlace = 0;
    static final int firstPlace = 1;
    static final int secondPlace = 2;
    static final int thirdPlace = 3;
    private final Vector<singleRace> races;

    /**
     * Constructs ...
     *
     */
    public RaceHistory() {
        this.races = new Vector<singleRace>();
    }

    private boolean wonRace(CoreTime diff, int typerace) {
        if (this.races.size() == 0) {
            return false;
        }

        singleRace rc = this.races.lastElement();

        if ((rc.typeRace == typerace) && (rc.racePlace != 0)) {
            return (new CoreTime().moreThanOnTime(rc.timeFinish, diff) <= 0);
        }

        return false;
    }

    private boolean lostRace(int typerace) {
        if (this.races.size() == 0) {
            return false;
        }

        singleRace rc = this.races.lastElement();

        if (rc.typeRace == typerace) {
            return (rc.racePlace == 0);
        }

        return false;
    }

    private boolean lostNumRaces(int typerace, int count) {
        if (this.races.size() < count) {
            return false;
        }

        int cnt = 0;

        for (int i = this.races.size() - 1; i >= 0; --i) {
            singleRace rc = this.races.elementAt(i);

            if (rc.typeRace == typerace) {
                if (rc.racePlace != 0) {
                    break;
                }

                ++cnt;
            }
        }

        return (cnt >= count);
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param place
     */
    public void finishRace(int type, int place) {
        singleRace rc = new singleRace();

        rc.typeRace = type;
        rc.racePlace = place;
        rc.timeFinish = new CoreTime();
        this.races.add(rc);
    }

    /**
     * Method description
     *
     *
     * @param diff
     *
     * @return
     */
    public boolean wonBigRace(CoreTime diff) {
        return wonRace(diff, 2);
    }

    /**
     * Method description
     *
     *
     * @param diff
     *
     * @return
     */
    public boolean wonSimpleRace(CoreTime diff) {
        return wonRace(diff, 1);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean lostBigRace() {
        return lostRace(2);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean lostCountsOfBigRaces() {
        return lostNumRaces(2, 3);
    }

    class singleRace {
        CoreTime timeStart;
        CoreTime timeFinish;
        int typeRace;
        int racePlace;

        singleRace() {
            this.timeStart = null;
            this.timeFinish = null;
            this.racePlace = 0;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
