/*
 * @(#)WeatherData.java   13/08/26
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


package rnr.src.menuscript.org;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.gameobj.WeatherPoint;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class WeatherData {
    Vector<?> m_warehouses;
    Vector<?> m_dates;

    /** Field description */
    public Vector<?> moonphase;

    /** Field description */
    public Vector<?> moondates;
    private Vector<?> m_weatherpoints;

    /**
     * Method description
     *
     */
    public void Init() {
        this.m_warehouses = new Vector<Object>();
        this.m_weatherpoints = new Vector<Object>();
        this.m_dates = new Vector<Object>();
        this.moonphase = new Vector<Object>();
        this.moondates = new Vector<Object>();
    }

    WeatherPoint GetWPoint(int day, boolean isnight, int whindex) {
        Vector<?> whpoints = (Vector<?>) this.m_weatherpoints.get(day * 2 + ((isnight)
                ? 1
                : 0));

        if (whindex >= whpoints.size()) {
            return null;
        }

        return ((WeatherPoint) whpoints.get(whindex));
    }
}


//~ Formatted in DD Std on 13/08/26
