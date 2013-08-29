/*
 * @(#)semitrailer.java   13/08/26
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
public class semitrailer {
    final int p_triler;

    /**
     * Constructs ...
     *
     */
    public semitrailer() {
        this.p_triler = 0;
    }

    /**
     * Method description
     *
     *
     * @param trailer
     *
     * @return
     */
    public final boolean equal(semitrailer trailer) {
        return (this.p_triler == trailer.p_triler);
    }

    /**
     * Method description
     *
     *
     * @param paramString
     * @param parammatrixJ
     * @param paramvectorJ
     *
     * @return
     */
    public static native semitrailer create(String paramString, matrixJ parammatrixJ, vectorJ paramvectorJ);

    /**
     * Method description
     *
     */
    public native void delete();

    /**
     * Method description
     *
     *
     * @return
     */
    public native matrixJ getMatrix();

    /**
     * Method description
     *
     *
     * @return
     */
    public native vectorJ getPosition();

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public native void registerCar(String paramString);

    /**
     * Method description
     *
     *
     * @return
     */
    public native String querryModelName();

    /**
     * Method description
     *
     *
     * @param parammatrixJ
     */
    public native void setMatrix(matrixJ parammatrixJ);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void setPosition(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramDouble
     */
    public native void setVelocityModule(double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramString
     *
     * @return
     */
    public static native semitrailer querryMissionSemitrailer(String paramString);
}


//~ Formatted in DD Std on 13/08/26
