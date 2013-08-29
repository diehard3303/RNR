/*
 * @(#)SCRuniperson.java   13/08/26
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class SCRuniperson {

    /** Field description */
    public long nativePointer;

    /**
     * Constructs ...
     *
     */
    public SCRuniperson() {
        this.nativePointer = 0L;
    }

    /**
     * Method description
     *
     *
     * @param paramString
     *
     * @return
     */
    public static native SCRuniperson createLoadedObject(String paramString);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native SCRuniperson createMC(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native SCRuniperson createAmbientPerson(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native SCRuniperson createMissionVotingPerson(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native SCRuniperson createMissionScenePerson(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native SCRuniperson createCutScenePerson(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native SCRuniperson createCutSceneAmbientPerson(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native SCRuniperson createCBVideoPerson(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native SCRuniperson createSOPerson(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native SCRuniperson createSOMainPerson(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramString3
     *
     * @return
     */
    public static native SCRuniperson createNamedAmbientPerson(String paramString1, String paramString2,
            String paramString3);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramString3
     *
     * @return
     */
    public static native SCRuniperson createNamedMissionVotingPerson(String paramString1, String paramString2,
            String paramString3);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramString3
     *
     * @return
     */
    public static native SCRuniperson createNamedMissionScenePerson(String paramString1, String paramString2,
            String paramString3);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramString3
     *
     * @return
     */
    public static native SCRuniperson createNamedCutScenePerson(String paramString1, String paramString2,
            String paramString3);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramString3
     *
     * @return
     */
    public static native SCRuniperson createNamedCutSceneAmbientPerson(String paramString1, String paramString2,
            String paramString3);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramString3
     *
     * @return
     */
    public static native SCRuniperson createNamedCBVideoPerson(String paramString1, String paramString2,
            String paramString3);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramString3
     *
     * @return
     */
    public static native SCRuniperson createNamedSOPerson(String paramString1, String paramString2,
            String paramString3);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramString3
     *
     * @return
     */
    public static native SCRuniperson createNamedSOMainPerson(String paramString1, String paramString2,
            String paramString3);

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
     * @param paramString
     */
    public native void AddAnimation(String paramString);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void SetPosition(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void SetDirection(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @return
     */
    public native vectorJ GetPosition();

    /**
     * Method description
     *
     *
     * @return
     */
    public native matrixJ GetMatrix();

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     */
    public native void DeleteAnimatedItem(String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramString3
     * @param paramString4
     * @param paramDouble
     * @param paramLong1
     * @param paramLong2
     * @param parammatrixJ
     * @param paramBoolean1
     * @param paramBoolean2
     */
    public native void CreateAnimatedSpace_timedependance(String paramString1, String paramString2,
            String paramString3, String paramString4, double paramDouble, long paramLong1, long paramLong2,
            matrixJ parammatrixJ, boolean paramBoolean1, boolean paramBoolean2);

    /**
     * Method description
     *
     *
     * @param paramString1
     * @param paramString2
     * @param paramLong
     */
    public native void DeleteAnimatedSpace(String paramString1, String paramString2, long paramLong);

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
     */
    public native void SetInWarehouseEnvironment();

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public native void SetInWorld(String paramString);

    /**
     * Method description
     *
     */
    public native void play();

    /**
     * Method description
     *
     */
    public native void stop();

    /**
     * Method description
     *
     *
     * @return
     */
    public native long gNode();

    /**
     * Method description
     *
     */
    public native void stopbeingDriverOrPassanger();

    /**
     * Method description
     *
     *
     * @param paramString
     *
     * @return
     */
    public static native SCRuniperson findModel(String paramString);

    private native void lock();

    private native void unlock();

    /**
     * Method description
     *
     */
    public native void setAnimationsLoadConsequantly();

    /**
     * Method description
     *
     *
     * @param model
     * @param name
     * @param id
     * @param pos
     * @param dir
     *
     * @return
     */
    public static SCRuniperson createCutSceneAmbientPerson(String model, String name, String id, vectorJ pos,
            vectorJ dir) {
        SCRuniperson ter = createNamedCutSceneAmbientPerson(model, name, id);

        ter.SetDirection(dir);
        ter.SetPosition(pos);

        return ter;
    }

    /**
     * Method description
     *
     */
    public void lockPerson() {
        lock();
    }

    /**
     * Method description
     *
     */
    public void unlockPerson() {
        unlock();
    }
}


//~ Formatted in DD Std on 13/08/26
