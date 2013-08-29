/*
 * @(#)Place.java   13/08/28
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


package rnr.src.rnrscenario.missions.map;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class Place implements Comparable<Object>, Serializable {
    static final long serialVersionUID = 0L;

    /** Field description */
    public static final int NULL_POINT = -1;

    /** Field description */
    public static final int BAR_POINT = 0;

    /** Field description */
    public static final int MISSION_POINT = 1;

    /** Field description */
    public static final int OFFICE_POINT = 2;

    /** Field description */
    public static final int MOTEL_POINT = 3;

    /** Field description */
    public static final int WAREHOUSE_POINT = 4;

    /** Field description */
    public static final int REPAIR_POINT = 5;

    /** Field description */
    public static final int GAS_POINT = 6;

    /** Field description */
    public static final int SCENARIO_POINT = 7;

    /** Field description */
    public static final String NODE_NAME_TO_CONSTRUCT_FORM = "point";
    private int tip;
    private String name;
    private vectorJ position;

    /**
     * Constructs ...
     *
     */
    public Place() {
        this.tip = 0;
        this.name = null;
        this.position = null;
    }

    /**
     * Method description
     *
     *
     * @param tip
     * @param name
     * @param position
     */
    public static void createPoint(int tip, String name, vectorJ position) {
        Place place = new Place();

        place.init(tip, name, position);
        MissionSystemInitializer.getMissionsMap().addPlace(place);
    }

    /**
     * Method description
     *
     *
     * @param tip
     * @param name
     * @param position
     */
    public void init(int tip, String name, vectorJ position) {
        assert(null != position) : "position must be non-null reference";
        assert(null != name) : "externalName must be non-null reference";
        this.position = position;
        this.name = name;
        this.tip = tip;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getTip() {
        return this.tip;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public vectorJ getCoords() {
        return this.position;
    }

    /**
     * Method description
     *
     *
     * @param point
     *
     * @return
     */
    public double distance(vectorJ point) {
        return vectorJ.oMinus(point, this.position).length();
    }

    /**
     * Method description
     *
     *
     * @param point
     *
     * @return
     */
    public double distance2(vectorJ point) {
        return point.len2(this.position);
    }

    /**
     * Method description
     *
     *
     * @param o
     *
     * @return
     */
    @Override
    public int compareTo(Object o) {
        if (null == o) {
            return 1;
        }

        assert((o instanceof Place) || (o instanceof String)) : "supported types: String, Place";

        if (o instanceof Place) {
            return this.name.compareTo(((Place) o).name);
        }

        return this.name.compareTo((String) o);
    }
}


//~ Formatted in DD Std on 13/08/28
