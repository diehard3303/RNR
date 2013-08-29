/*
 * @(#)JavaEvents.java   13/08/25
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
   Released under the BSD 3 clause license
Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this 
    list of conditions and the following disclaimer. Redistributions in binary 
    form must reproduce the above copyright notice, this list of conditions and 
    the following disclaimer in the documentation and/or other materials 
    provided with the distribution. Neither the name of the DieHard Development 
    nor the names of its contributors may be used to endorse or promote products 
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 *
 *
 *
 */


package rnr.src.menu;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.eng;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class JavaEvents implements SelectCb {

    /** Field description */
    public static final int ESCAPE_REFRESH = 0;

    /** Field description */
    public static final int ESCAPE_UPDATE = 1;

    /** Field description */
    public static final int ESCAPE_INIT = 2;

    /** Field description */
    public static final int ESCAPE_DESTROY = 3;

    /** Field description */
    public static final int ESCAPE_CHECKKEY = 4;

    /** Field description */
    public static final int ESCAPE_QUITGAME = 5;

    /** Field description */
    public static final int ESCAPE_ROADSERVICE = 6;

    /** Field description */
    public static final int JOURNAL_WEATHER = 7;

    /** Field description */
    public static final int WH_REFRESH = 8;

    /** Field description */
    public static final int HELP_REFRESH = 9;

    /** Field description */
    public static final int STO_APPLY = 10;

    /** Field description */
    public static final int RACE_FADECOMPLETE = 11;

    /** Field description */
    public static final int SINGLEPLAYERGAMEOPTIONS_READY_RECIEVE = 12;

    /** Field description */
    public static final int SINGLEPLAYERGAMEOPTIONS_UPDATE = 13;

    /** Field description */
    public static final int SETTINGSCONTROLS_READY_RECIEVE = 14;

    /** Field description */
    public static final int SETTINGSCONTROLS_UPDATE = 15;

    /** Field description */
    public static final int ESCAPE_CHECKAXE = 16;

    /** Field description */
    public static final int SETTINGSCONTROLS_GETAXENAME = 17;

    /** Field description */
    public static final int SETTINGSAUDIO_PROVIDE = 18;

    /** Field description */
    public static final int SETTINGSAUDIO_UPDATE = 19;

    /** Field description */
    public static final int SETTINGSVIDEO_PROVIDE = 20;

    /** Field description */
    public static final int SETTINGSVIDEO_UPDATE = 21;

    /** Field description */
    public static final int SETTINGSVIDEO_CHANGECARD = 22;

    /** Field description */
    public static final int ESCAPE_QUITMAINMENU = 23;

    /** Field description */
    public static final int ESCAPE_QUITMAINMENU_MAINMENU = 1;

    /** Field description */
    public static final int ESCAPE_QUITMAINMENU_RESTART = 2;

    /** Field description */
    public static final int ESCAPE_QUITMAINMENU_RESTART_INSTANTORDER = 3;

    /** Field description */
    public static final int MAIN_MENU_PROFILE = 24;

    /** Field description */
    public static final int SAVE_LOAD_COMMON = 25;

    /** Field description */
    public static final int SET_INTERCEPT_PAUSE = 26;

    /** Field description */
    public static final int INPAUSE_ON_GAME = 27;

    /** Field description */
    public static final int OFFPAUSE_ON_GAME = 28;

    /** Field description */
    public static final int MANAGE_DRIVERS = 29;

    /** Field description */
    public static final int CONTROL_CHECK_SHIFT = 30;

    /** Field description */
    public static final int CONTROL_CHECK_CTRL = 31;

    /** Field description */
    public static final int CONTROL_CATCH_CTRL_A = 32;

    /** Field description */
    public static final int MANAGE_FLEET = 33;

    /** Field description */
    public static final int RECIEVE_TEXTURECOORDS = 34;

    /** Field description */
    public static final int FIRE_HIRE_DRIVERS = 35;

    /** Field description */
    public static final int RECIEVE_WORLDCOORDINATES = 36;

    /** Field description */
    public static final int MANAGE_BRANCHES = 37;

    /** Field description */
    public static final int RECIEVE_CARINFO = 38;

    /** Field description */
    public static final int TRACE_FREEWAY = 39;

    /** Field description */
    public static final int RECIEVE_GAMESTATE = 40;

    /** Field description */
    public static final int MEASSURE_RACE_TIME = 41;

    /** Field description */
    public static final int CHECK_GAME_WORLD_STATE = 42;

    /** Field description */
    public static final int CAR_REPAIR = 43;

    /** Field description */
    public static final int OFFICE_ACTIONS = 44;

    /** Field description */
    public static final int PERFORM_AUTO_UNSETTLED_DEPT = 45;

    /** Field description */
    public static final int PEEKMESSAGE = 46;

    /** Field description */
    public static final int MISSIONCHANNEL_MESSAGES = 47;

    /** Field description */
    public static final int MISSIONSTART = 48;

    /** Field description */
    public static final int MISSIONDECLINE = 49;

    /** Field description */
    public static final int UPDATE_MISSION_PARAMS = 50;

    /** Field description */
    public static final int MISSION_QUESTITEM = 51;

    /** Field description */
    public static final int MISSION_QUEUERESOURCE = 52;

    /** Field description */
    public static final int CONTROL_CATCH_YES_KEY = 53;

    /** Field description */
    public static final int CONTROL_CATCH_NO_KEY = 54;

    /** Field description */
    public static final int RECIEVE_PACKAGESHIFT = 55;

    /** Field description */
    public static final int GET_MAPPOINTS_INFO = 56;

    /** Field description */
    public static final int GET_NPC_INFO = 57;

    /** Field description */
    public static final int CHECK_MISSIONSLOT_BUZZY = 58;

    /** Field description */
    public static final int QUERRY_MISSION_SLOTS = 59;

    /** Field description */
    public static final int MISSION_CLEANRESOURCE = 60;

    /** Field description */
    public static final int ABONISH_MISSION = 61;

    /** Field description */
    public static final int MENU_PHOTO_ALBUM = 62;

    /** Field description */
    public static final int GET_TRUCKER_IDENTITIES = 63;

    /** Field description */
    public static final int LOADMODEL_CALLBACK = 64;

    /** Field description */
    public static final int QUICK_RACE_MENU_CALLBACK = 65;

    /** Field description */
    public static final int WAREHOUSE_MENU_CALLBACK = 66;

    /** Field description */
    public static final int STO_VEHICLE_UPGRADES = 67;

    /** Field description */
    public static final int ORGANAIZER_PHOTO_ALBUM = 68;

    /** Field description */
    public static final int IS_BASE_READY_FOR_CHECKIN = 69;

    /** Field description */
    public static final int CONVERT_GAME_TIME = 70;

    /** Field description */
    public static final int MENU_MISC = 71;

    /** Field description */
    public static final int NARKO_INFLUENCE = 72;

    /** Field description */
    public static final int CHANGEMISSION_DESTINATION = 73;

    /** Field description */
    public static final int REPLAYPLAYERGAMEOPTIONS_READY_RECIEVE = 74;

    /** Field description */
    public static final int REPLAYPLAYERGAMEOPTIONS_UPDATE = 75;

    /** Field description */
    public static final int CONTROL_MOUSE_WHEEL = 76;

    /** Field description */
    public static final int GET_MERCHANDIZE_REAL_NAME = 77;

    /** Field description */
    public static final int GET_WAREHOUSE_REAL_NAME = 78;

    /** Field description */
    public static final int CONTROL_CATCH_ESC = 80;

    /** Field description */
    public static final int BALANCE_CHANGED = 81;

    /** Field description */
    public static final int CONTROL_CATCH_ENTER_KEY = 82;

    /** Field description */
    public static final int CHANEL_IS_ACTIVE = 83;

    /** Field description */
    public static final int MISSION_ON_MONEY = 84;

    /** Field description */
    public static final int SAME_PASSANGER = 85;

    /** Field description */
    public static final int MISSION_FINISH_SCRIPT_CONDITION = 86;

    /** Field description */
    public static final int FREE_SLOT_FOR_MISSION = 87;

    /** Field description */
    public static final int IS_WIDE = 88;
    static JavaEvents s_instance = null;
    HashMap<Integer, JavaEventCb> m_hashmap = new HashMap<Integer, JavaEventCb>();

    private JavaEvents() {
        ListenerManager.AddListener(105, this);
    }

    /**
     * Method description
     *
     *
     * @param ID
     * @param cb
     */
    public static void RegisterEvent(int ID, JavaEventCb cb) {
        if (s_instance == null) {
            s_instance = new JavaEvents();
        }

        Object obj = s_instance.m_hashmap.put(new Integer(ID), cb);

        if (obj == null) {
            return;
        }

        eng.writeLog("Duplicate entry for ID " + ID);
    }

    /**
     * Method description
     *
     *
     * @param ID
     * @param value
     * @param obj
     */
    public static void DispatchEvent(int ID, int value, Object obj) {
        SendEvent(ID, value, obj);

        if (s_instance == null) {
            return;
        }

        JavaEventCb cb = s_instance.m_hashmap.get(new Integer(ID));

        if (cb != null) {
            cb.OnEvent(ID, value, obj);
        }
    }

    /**
     * Method description
     *
     *
     * @param ID
     */
    public static void UnregisterEvent(int ID) {
        if (s_instance == null) {
            return;
        }

        s_instance.m_hashmap.remove(new Integer(ID));
    }

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramObject
     */
    public static native void SendEvent(int paramInt1, int paramInt2, Object paramObject);

    /**
     * Method description
     *
     *
     * @param state
     * @param sender
     */
    @Override
    public void OnSelect(int state, Object sender) {}
}


//~ Formatted in DD Std on 13/08/25
