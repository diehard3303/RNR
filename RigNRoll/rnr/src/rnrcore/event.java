/*
 * @(#)event.java   13/08/26
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

import rnr.src.rnrorg.organaiser;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class event {

    /** Field description */
    public static final int eventMainMenuClosed = 25001;

    /** Field description */
    public static final int eventShowPoliceMessage = 9801;

    /** Field description */
    public static final int eventEndShowPoliceMessage = 9802;

    /** Field description */
    public static final int eventDialogFinished = 9850;

    /** Field description */
    public static final int eventParkingFinished = 25001;

    /** Field description */
    public static final int eventStartDevelopMotelMenu = 7002;

    /** Field description */
    public static final int eventEndDevelopMotelMenu = 7003;

    /** Field description */
    public static final int eventStartDevelopRepairMenu = 2002;

    /** Field description */
    public static final int eventEndDevelopRepairMenu = 2003;

    /** Field description */
    public static final int eventStartDevelopOfficeMenu = 6005;

    /** Field description */
    public static final int eventEndDevelopOfficeMenu = 6006;

    /** Field description */
    public static final int eventHideKeyboard = 6007;

    /** Field description */
    public static final int PAKING = 0;

    /** Field description */
    public static final int BAR_EXITMENU = 1;

    /** Field description */
    public static final int MOTEL_EXITMENU = 2;

    /** Field description */
    public static final int MOTEL_DEVELOPFINISHED = 3;

    /** Field description */
    public static final int NEWCAR_CREATED = 4;

    /** Field description */
    public static final int OFFICE_EXITMENU = 5;

    /** Field description */
    public static final int GASSTATION_MESSAGECLOSED = 6;

    /** Field description */
    public static final int GASSTATION_LEAVE = 7;

    /** Field description */
    public static final int GASSTATION_CAME = 8;

    /** Field description */
    public static final int WH_LEAVEGATES = 9;

    /** Field description */
    public static final int WH_CAME = 10;

    /** Field description */
    public static final int WH_LEAVE = 11;

    /** Field description */
    public static final int WH_LOAD = 12;

    /** Field description */
    public static final int WH_UNLOAD = 13;

    /** Field description */
    public static final int WH_GOFROMEMPTY = 14;

    /** Field description */
    public static final int WH_GOFROMLOADED = 15;

    /** Field description */
    public static final int WH_EXITMENU = 16;

    /** Field description */
    public static final int STO_CAME = 17;

    /** Field description */
    public static final int STO_LEAVEGATES = 18;

    /** Field description */
    public static final int STO_INSIDE = 19;
    long nativePointer;

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramString1
     * @param paramString2
     */
    public native void eventScript(int paramInt, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramObject
     * @param paramString
     *
     * @return
     */
    public static native int eventObject(int paramInt, Object paramObject, String paramString);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void removeEventObject(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void Setevent(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void SetScriptevent(long paramLong);

    /**
     * Method description
     *
     *
     * @param ID
     * @param script
     * @param method
     *
     * @return
     */
    public static event CreateEventScript(int ID, String script, String method) {
        event ev = new event();

        ev.eventScript(ID, script, method);

        return ev;
    }

    /**
     * Method description
     *
     *
     * @param ID
     */
    public void SetScriptevent_NS(long ID) {
        SetScriptevent(ID);
    }

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramIScriptTask
     *
     * @return
     */
    public static native int createScriptObject(int paramInt, IScriptTask paramIScriptTask);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void deleteScriptObject(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public static native void finishScenarioMission(String paramString);

    /**
     * Method description
     *
     *
     * @param name
     */
    public static void failScenarioMission(String name) {
        organaiser.declineMission(name);
    }
}


//~ Formatted in DD Std on 13/08/26
