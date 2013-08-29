/*
 * @(#)Chainanm.java   13/08/28
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

import rnr.src.rnrcore.SCRanimparts;
import rnr.src.rnrcore.SCRperson;
import rnr.src.rnrcore.vectorJ;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class Chainanm {
    long nativePointer;

    Chainanm() {
        this.nativePointer = 0L;
    }

    /**
     * Method description
     *
     *
     * @param paramSCRperson
     */
    public native void nchain(SCRperson paramSCRperson);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramvectorJ
     * @param paramInt2
     * @param paramInt3
     */
    public native void Add(int paramInt1, vectorJ paramvectorJ, int paramInt2, int paramInt3);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramDouble
     */
    public native void Add(int paramInt1, int paramInt2, int paramInt3, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramvectorJ
     * @param paramInt2
     * @param paramSCRanimparts
     */
    public native void Add(int paramInt1, vectorJ paramvectorJ, int paramInt2, SCRanimparts paramSCRanimparts);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramSCRanimparts
     * @param paramDouble
     */
    public native void Add(int paramInt1, int paramInt2, SCRanimparts paramSCRanimparts, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramDouble
     */
    public native void AddAsk(int paramInt, double paramDouble);

    /**
     * Method description
     *
     *
     * @param _task
     * @param _groupmovwment
     * @param _typemovwment
     */
    public void Add(int _task, int _groupmovwment, int _typemovwment) {
        Add(_task, _groupmovwment, _typemovwment, 0.0D);
    }

    /**
     * Method description
     *
     *
     * @param _task
     * @param _groupmovwment
     * @param inanim
     */
    public void Add(int _task, int _groupmovwment, SCRanimparts inanim) {
        Add(_task, _groupmovwment, inanim, 0.0D);
    }
}


//~ Formatted in DD Std on 13/08/28
