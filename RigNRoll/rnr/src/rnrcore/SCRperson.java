/*
 * @(#)SCRperson.java   13/08/26
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


package rnr.src.rnrcore;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscr.Chainanm;
import rnr.src.rnrscr.Eventanm;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class SCRperson {

    /** Field description */
    public long nativePointer;

    /**
     * Constructs ...
     *
     */
    public SCRperson() {
        this.nativePointer = 0L;
    }

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public native void Type(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramEventanm
     */
    public native void AttachToEvent(Eventanm paramEventanm);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public native void AttachToEvent(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void SetPos(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void SetDir(vectorJ paramvectorJ);

    /**
     * Method description
     *
     */
    public native void SetRandomTimeOnFive();

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramSCRanimparts
     */
    public native void SetFive(int paramInt, SCRanimparts paramSCRanimparts);

    /**
     * Method description
     *
     *
     * @param paramSCRanimparts
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     */
    public native void AddAnimGroup(SCRanimparts paramSCRanimparts, int paramInt1, int paramInt2, int paramInt3);

    /**
     * Method description
     *
     *
     * @return
     */
    public native String GetName();

    /**
     * Method description
     *
     *
     * @param _five
     * @param type
     */
    public void AddAnimGroup(SCRanimparts _five, int type) {
        AddAnimGroup(_five, type, 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramvectorJ1
     * @param paramvectorJ2
     */
    public native void nPerson(String paramString, vectorJ paramvectorJ1, vectorJ paramvectorJ2);

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramvectorJ1
     * @param paramvectorJ2
     */
    public native void nPersonNoDetailes(String paramString, vectorJ paramvectorJ1, vectorJ paramvectorJ2);

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramvectorJ1
     * @param paramvectorJ2
     */
    public native void nPersonSO(String paramString, vectorJ paramvectorJ1, vectorJ paramvectorJ2);

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramvectorJ1
     * @param paramvectorJ2
     */
    public native void nPersonNoDetailesSO(String paramString, vectorJ paramvectorJ1, vectorJ paramvectorJ2);

    /**
     * Method description
     *
     */
    public native void setDependent();

    /**
     * Method description
     *
     *
     * @param paramSCRperson
     */
    public native void addDependent(SCRperson paramSCRperson);

    /**
     * Method description
     *
     */
    public native void delete();

    /**
     * Method description
     *
     *
     * @param paramChainanm
     */
    public native void StartChain(Chainanm paramChainanm);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramDouble
     */
    public native void CreateAnimatedItem(String paramString1, String paramString2, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void ShiftPerson(vectorJ paramvectorJ);

    /**
     * Method description
     *
     */
    public native void SetInBarWorld();

    /**
     * Method description
     *
     */
    public native void SetInOfficeWorld();

    /**
     * Method description
     *
     */
    public native void SetInHotelWorld();

    /**
     * Method description
     *
     */
    public native void SetInGameWorld();

    /**
     * Method description
     *
     *
     * @param strr
     * @param begin
     * @param direction
     * @param on_specobject
     *
     * @return
     */
    public static SCRperson CreateAnm(String strr, vectorJ begin, vectorJ direction, boolean on_specobject) {
        SCRperson ter = new SCRperson();

        if (on_specobject) {
            ter.nPersonSO(strr, begin, direction);
        } else {
            ter.nPerson(strr, begin, direction);
        }

        return ter;
    }

    /**
     * Method description
     *
     *
     * @param strr
     * @param begin
     * @param direction
     * @param on_specobject
     *
     * @return
     */
    public static SCRperson CreateAnmNoDetailes(String strr, vectorJ begin, vectorJ direction, boolean on_specobject) {
        SCRperson ter = new SCRperson();

        if (on_specobject) {
            ter.nPersonNoDetailesSO(strr, begin, direction);
        } else {
            ter.nPersonNoDetailes(strr, begin, direction);
        }

        return ter;
    }
}


//~ Formatted in DD Std on 13/08/26
