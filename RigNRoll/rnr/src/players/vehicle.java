/*
 * @(#)vehicle.java   13/08/26
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

import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class vehicle {
    int p_vehicle;

    /**
     * Constructs ...
     *
     */
    public vehicle() {
        this.p_vehicle = 0;
    }

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramInt
     *
     * @return
     */
    public static native vehicle create(String paramString, int paramInt);

    /**
     * Method description
     *
     */
    public native void delete();

    /**
     * Method description
     *
     */
    public native void placeInGarage();

    /**
     * Method description
     *
     */
    public native void placeToWorldFromGarage();

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public native void setLeased(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramactorveh
     * @param paramvehicle
     * @param parammatrixJ
     * @param paramvectorJ
     */
    public static native void changeLiveVehicle(actorveh paramactorveh, vehicle paramvehicle, matrixJ parammatrixJ,
            vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramvehicle
     * @param paramString
     */
    public static native void cacheVehicleWithName(vehicle paramvehicle, String paramString);

    /**
     * Method description
     *
     *
     * @param paramString
     *
     * @return
     */
    public static native vehicle getCacheVehicleByName(String paramString);
}


//~ Formatted in DD Std on 13/08/26
