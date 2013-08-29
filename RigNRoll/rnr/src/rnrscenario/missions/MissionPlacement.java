/*
 * @(#)MissionPlacement.java   13/08/28
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


package rnr.src.rnrscenario.missions;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscr.parkingplace;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class MissionPlacement implements Serializable {
    static final long serialVersionUID = 0L;
    private MissionInfo missionInfo = null;
    private List<String> points = null;
    private final HashMap<String, parkingplace> parking = new HashMap<String, parkingplace>();
    private final HashMap<String, Integer> parking_num = new HashMap<String, Integer>();
    private final HashMap<String, ArrayList<Integer>> parking_list = new HashMap<String, ArrayList<Integer>>();
    private int p_cnt;

    /**
     * Constructs ...
     *
     *
     * @param missionInfo
     * @param points
     */
    public MissionPlacement(MissionInfo missionInfo, List<String> points) {
        assert(null != missionInfo) : "missionInfo must be non-null reference";
        assert(null != points) : "points must be non-null reference";
        this.missionInfo = missionInfo;
        this.points = points;
    }

    /**
     * Method description
     *
     *
     * @param point
     * @param N
     */
    public void putParking(String point, int N) {
        this.parking_num.put(point, new Integer(N));
    }

    /**
     * Method description
     *
     *
     * @param point
     * @param pk
     */
    public void putParking(String point, parkingplace pk) {
        this.parking.put(point, pk);
    }

    /**
     * Method description
     *
     *
     * @param point
     * @param list
     */
    public void putParking(String point, ArrayList<Integer> list) {
        this.parking_list.put(point, list);
    }

    /**
     * Method description
     *
     *
     * @param point
     *
     * @return
     */
    public int getParkingN(String point) {
        if (this.parking_num.isEmpty()) {
            return 0;
        }

        Integer nn = this.parking_num.get(point);

        if (nn == null) {
            return 0;
        }

        return nn.intValue();
    }

    /**
     * Method description
     *
     *
     * @param point
     *
     * @return
     */
    public parkingplace getParking(String point) {
        return (this.parking.get(point));
    }

    /**
     * Method description
     *
     *
     * @param point
     *
     * @return
     */
    public ArrayList<Integer> getParkingPP(String point) {
        return (this.parking_list.get(point));
    }

    /**
     * Method description
     *
     *
     * @param point
     *
     * @return
     */
    public int getParkingFromLock(String point) {
        ArrayList<?> list = getParkingPP(point);

        if (list.size() <= this.p_cnt) {
            return 1;
        }

        int pp = ((Integer) list.get(this.p_cnt)).intValue();

        this.p_cnt += 1;

        return pp;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public MissionInfo getInfo() {
        return this.missionInfo;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<String> getPoints() {
        return this.points;
    }
}


//~ Formatted in DD Std on 13/08/28
