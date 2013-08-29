/*
 * @(#)NullMissionMap.java   13/08/28
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class NullMissionMap implements AbstractMissionsMap {
    private final Place proxyPlace = new Place();

    NullMissionMap() {
        this.proxyPlace.init(-1, "some place", new vectorJ(0.0D, 0.0D, 0.0D));
    }

    void setPlayerPosition(vectorJ position) {
        assert(null != position) : "position must be non-null reference";
    }

    /**
     * Method description
     *
     *
     * @param target
     */
    @Override
    public void addPlace(Place target) {}

    /**
     * Method description
     *
     *
     * @return
     *
     * @throws NoPlacesException
     */
    public Place getNearestPlace() throws NoPlacesException {
        return this.proxyPlace;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Place getCurrentPlace() {
        return null;
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void addListener(MapEventsListener listener) {}

    /**
     * Method description
     *
     *
     * @param listener
     */
    @Override
    public void removeListener(MapEventsListener listener) {}

    /**
     * Method description
     *
     *
     * @param listener
     * @param distance
     */
    @Override
    public void addListener(MapEventsListener listener, double distance) {}

    /**
     * Method description
     *
     *
     * @param listener
     * @param distance
     *
     * @return
     */
    @Override
    public boolean onPlace(MapEventsListener listener, double distance) {
        return false;
    }

    /**
     * Method description
     *
     *
     * @param pointname
     * @param pos
     *
     * @return
     */
    @Override
    public boolean onLoadingDistance(String pointname, vectorJ pos) {
        return false;
    }

    /**
     * Method description
     *
     *
     * @param pointname
     * @param pos
     *
     * @return
     */
    @Override
    public boolean afterUnloadingDistance(String pointname, vectorJ pos) {
        return true;
    }
}


//~ Formatted in DD Std on 13/08/28
