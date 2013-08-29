/*
 * @(#)scenetrack.java   13/08/26
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

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class scenetrack {

    /** Field description */
    public static final int TRACK_RUN = 1;

    /** Field description */
    public static final int TRACK_SUSPENDONEND = 2;

    /** Field description */
    public static final int TRACK_CYCLE = 4;

    /** Field description */
    public static final int TRACK_ESCRECIEVE = 8;

    /** Field description */
    public static final int TRACK_SELFDELETE = 16;

    /** Field description */
    public static final int TRACK_STOPPED = 256;

    /** Field description */
    public static final int TRACK_AMBIENT = 512;

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native SCRuniperson[] GetSceneAll(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     *
     * @return
     */
    public static native SCRuniperson GetSceneActor(long paramLong, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void InitScene(long paramLong);

    /**
     * Method description
     *
     *
     * @return
     */
    public static native long CreateScene();

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     */
    public static native void CreateTrackInSecene(long paramLong, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetCamerasInSecene(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void RunScene(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void StopScene(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void DeleteScene(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramInt
     * @param paramObject
     * @param paramVector
     *
     * @return
     */
    public static native long CreateSceneXML(String paramString, int paramInt, Object paramObject, Vector paramVector);

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramInt
     * @param paramVector1
     * @param paramObject
     * @param paramVector2
     *
     * @return
     */
    public static native long CreateSceneXMLPool(String paramString, int paramInt, Vector paramVector1,
            Object paramObject, Vector paramVector2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     */
    public static native void UpdateSceneFlags(long paramLong, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     */
    public static native void ReplaceSceneFlags(long paramLong, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramDouble
     */
    public static native void moveSceneTime(long paramLong, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramDouble
     */
    public static native void setSceneTime(long paramLong, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramFloat
     */
    public static native void setSceneWeight(long paramLong, float paramFloat);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void runFromStart(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     *
     * @return
     */
    public static native long CreateItemTrackInScene(long paramLong, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void InitItems(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public static native void Set_waittrackcreation(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     * @param paramObject
     * @param paramString3
     */
    public static native void ChangeClipParam(long paramLong, String paramString1, String paramString2,
            Object paramObject, String paramString3);

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param flags
     * @param preset
     *
     * @return
     */
    public static long CreateSceneXML(String Scenename, int flags, Object preset) {
        return CreateSceneXML(Scenename, flags, preset, null);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param flags
     * @param pool
     * @param preset
     *
     * @return
     */
    public static long CreateSceneXMLPool(String Scenename, int flags, Vector pool, Object preset) {
        return CreateSceneXMLPool(Scenename, flags, pool, preset, null);
    }
}


//~ Formatted in DD Std on 13/08/26
