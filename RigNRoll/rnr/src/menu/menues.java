/*
 * @(#)menues.java   13/08/25
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

import rnr.src.menuscript.EscapeMenu;
import rnr.src.menuscript.HelpMenu;
import rnr.src.menuscript.NotifyGameOver;
import rnr.src.menuscript.office.OfficeMenu;
import rnr.src.rnrcore.Helper;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrscr.ILeaveMenuListener;
import rnr.src.rnrscr.gameinfo;
import rnr.src.rnrscr.gameinfo.cCreateWindowDispatch;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class menues implements menucreation {

    /** Field description */
    public static final int CB_CHANGERAGER = 1;

    /** Field description */
    public static final int CB_RADIOPRESS = 2;

    /** Field description */
    public static final int CB_MOUSEOVER = 3;

    /** Field description */
    public static final int CB_BUTTONPRESS = 4;

    /** Field description */
    public static final int CB_MOUSEOUTSIDE = 5;

    /** Field description */
    public static final int CB_MOUSEINSIDE = 6;

    /** Field description */
    public static final int CB_CONTROLSELECT = 7;

    /** Field description */
    public static final int CB_ACTIONBUTTONPRESS = 8;

    /** Field description */
    public static final int CB_USERACTION = 9;

    /** Field description */
    public static final int CB_SWITCHON = 10;

    /** Field description */
    public static final int CB_SWITCHOFF = 11;

    /** Field description */
    public static final int CB_ACTION_KEY_OUT_LEFT = 12;

    /** Field description */
    public static final int CB_ACTION_KEY_OUT_RIGHT = 13;

    /** Field description */
    public static final int CB_ACTION_KEY_OUT_DOWN = 14;

    /** Field description */
    public static final int CB_ACTION_KEY_OUT_UP = 15;

    /** Field description */
    public static final int CB_TEXTEXECUTE = 16;

    /** Field description */
    public static final int CB_CLOSEWINDOW = 17;

    /** Field description */
    public static final int CB_BEFOREACTION = 18;

    /** Field description */
    public static final int CB_TEXTDISSMIS = 19;

    /** Field description */
    public static final int CB_MAPOBJECTPRESSED = 20;

    /** Field description */
    public static final int CB_MAPOBJECTACTIVE = 21;

    /** Field description */
    public static final int CB_RELEASE = 22;

    /** Field description */
    public static final int VWI_UI_CONTROL_ACTION_USER = 65535;

    /** Field description */
    public static final int VWI_UI_CONTROL_ACTION_USER1 = 131070;

    /** Field description */
    public static final int VWI_UI_CONTROL_ACTION_USER2 = 196605;

    /** Field description */
    public static final int VWI_UI_CONTROL_ACTION_USER3 = 262140;

    /** Field description */
    public static final int VWI_UI_CONTROL_ACTION_USER4 = 327675;

    /** Field description */
    public static final int LOGIC_MENU_F2 = 0;

    /** Field description */
    public static final int LOGIC_MENU_MESSAGE = 1;

    /** Field description */
    public static final int LOGIC_MENU_WH = 2;

    /** Field description */
    public static final int LOGIC_MENU_CB = 3;

    /** Field description */
    public static final int LOGIC_MENU_TITRE = 4;

    /** Field description */
    public static final int LOGIC_MENU_PAUSE = 5;

    /** Field description */
    public static final long eTITRESNIMATIONID = 57087L;
    static long HELP_MENU = 0L;
    private String menuid;

    /**
     * Constructs ...
     *
     */
    public menues() {
        this.menuid = null;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void restartMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        if (this.menuid == null) {
            gameinfo info = gameinfo.script;

            return info.CW_info.menuId;
        }

        return this.menuid;
    }

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramInt4
     * @param paramInt5
     * @param paramInt6
     * @param paramString
     * @param paramInt7
     * @param paramInt8
     */
    public static native void CreateGasMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5,
            int paramInt6, String paramString, int paramInt7, int paramInt8);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramInt4
     * @param paramInt5
     * @param paramInt6
     * @param paramString
     * @param paramInt7
     * @param paramInt8
     */
    public static native void CreateSTOMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5,
            int paramInt6, String paramString, int paramInt7, int paramInt8);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramInt4
     * @param paramInt5
     * @param paramInt6
     * @param paramString
     * @param paramInt7
     * @param paramInt8
     */
    public static native void CreateWareHouseMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4,
            int paramInt5, int paramInt6, String paramString, int paramInt7, int paramInt8);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramInt4
     * @param paramInt5
     * @param paramInt6
     * @param paramString
     * @param paramInt7
     * @param paramInt8
     */
    public static native void CreateMotelMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4,
            int paramInt5, int paramInt6, String paramString, int paramInt7, int paramInt8);

    /**
     * Method description
     *
     *
     * @param parammenucreation
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramInt4
     * @param paramInt5
     * @param paramInt6
     * @param paramString
     * @param paramInt7
     * @param paramInt8
     *
     * @return
     */
    public static native long CreateOfficeMenu(menucreation parammenucreation, int paramInt1, int paramInt2,
            int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString, int paramInt7,
            int paramInt8);

    /**
     * Method description
     *
     *
     * @param parammenucreation
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramInt4
     * @param paramInt5
     * @param paramInt6
     * @param paramString
     * @param paramInt7
     * @param paramInt8
     *
     * @return
     */
    public static native long CreateJournalMenu(menucreation parammenucreation, int paramInt1, int paramInt2,
            int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString, int paramInt7,
            int paramInt8);

    /**
     * Method description
     *
     *
     * @param parammenucreation
     * @param paramDouble
     *
     * @return
     */
    public static native long createTitre(menucreation parammenucreation, double paramDouble);

    /**
     * Method description
     *
     *
     * @param parammenucreation
     * @param paramDouble
     * @param paramString1
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramInt4
     * @param paramInt5
     * @param paramInt6
     * @param paramString2
     * @param paramInt7
     * @param paramInt8
     *
     * @return
     */
    public static native long createSimpleMenu(menucreation parammenucreation, double paramDouble, String paramString1,
            int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6,
            String paramString2, int paramInt7, int paramInt8);

    /**
     * Method description
     *
     *
     * @param parammenucreation
     * @param paramInt1
     * @param paramDouble
     * @param paramString1
     * @param paramInt2
     * @param paramInt3
     * @param paramInt4
     * @param paramInt5
     * @param paramInt6
     * @param paramInt7
     * @param paramString2
     * @param paramInt8
     * @param paramInt9
     *
     * @return
     */
    public static native long createSimpleMenu(menucreation parammenucreation, int paramInt1, double paramDouble,
            String paramString1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6,
            int paramInt7, String paramString2, int paramInt8, int paramInt9);

    /**
     * Method description
     *
     *
     * @param menuObj
     *
     * @return
     */
    public static long createSimpleMenu(menucreation menuObj) {
        return createSimpleMenu(menuObj, 10000000.0D, "", 1600, 1200, 1600, 1200, 0, 0,
                                "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param menuObj
     * @param logic_type
     *
     * @return
     */
    public static long createSimpleMenu(menucreation menuObj, int logic_type) {
        return createSimpleMenu(menuObj, logic_type, 10000000.0D, "", 1600, 1200, 1600, 1200, 0, 0,
                                "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param menuObj
     * @param logic_type
     * @param hot_key
     *
     * @return
     */
    public static long createSimpleMenu(menucreation menuObj, int logic_type, String hot_key) {
        return createSimpleMenu(menuObj, logic_type, 10000000.0D, hot_key, 1600, 1200, 1600, 1200, 0, 0,
                                "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param menuObj
     * @param hotkey_exit
     *
     * @return
     */
    public static long createSimpleMenu(menucreation menuObj, String hotkey_exit) {
        return createSimpleMenu(menuObj, 10000000.0D, hotkey_exit, 1600, 1200, 1600, 1200, 0, 0,
                                "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     */
    public void ShowMenu() {
        setShowMenu(Common.s_lastcommon.GetMenu(), true);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param value
     */
    public static void setShowMenu(long _menu, boolean value) {
        if (value) {
            showMenu(getMenuID(_menu));
        } else {
            hideMenu(getMenuID(_menu));
        }
    }

    /**
     * Method description
     *
     */
    public static void CreateGASSTATIONMENU() {
        CreateGasMenu(1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     */
    public void ExitGASSTATIONMENU() {
        event.Setevent(1001);
    }

    /**
     * Method description
     *
     */
    public void ExitREPAIRMENU() {
        event.Setevent(2001);
    }

    /**
     * Method description
     *
     */
    public void ExitSimpleMenu() {
        if (!(eng.noNative)) {
            event.Setevent(8001);
        }
    }

    /**
     * Method description
     *
     */
    public void CreateWHMENU() {
        CreateWareHouseMenu(1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     */
    public void ExitWHMENU() {
        event.Setevent(3001);
    }

    /**
     * Method description
     *
     */
    public static void CreateMotelMENU() {
        CreateMotelMenu(1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     */
    public void ExitMotelMENU() {
        event.Setevent(5001);
    }

    /**
     * Method description
     *
     */
    public static void CreateOfficeMENU() {
        OfficeMenu.create();
    }

    /**
     * Method description
     *
     */
    public void ExitOfficeMENU() {
        event.Setevent(6001);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean CreateJournalMENU() {
        if (!(cancreate_somenu())) {
            return false;
        }

        CreateJournalMenu(null, 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);

        return true;
    }

    /**
     * Method description
     *
     */
    public void CreateJournalMENU_void() {
        CreateJournalMenu(null, 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     */
    public void ExitJournalMENU() {
        event.Setevent(7001);
    }

    /**
     * Method description
     *
     */
    public static void ExitBarMENU() {
        Helper.peekNativeMessage("Bar menu exit");
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        SetStopWorld(_menu, false);

        gameinfo info = gameinfo.script;

        info.CW_info.menuId = null;

        if (info.CW_info.OnSpecObject == true) {
            for (int i = 0; i < info.CW_info.strings.size(); ++i) {
                InitXml(_menu, Common.ConstructPath("specmenu.xml"), (String) info.CW_info.strings.get(i));
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        setShowMenu(_menu, true);

        gameinfo info = gameinfo.script;

        if (info.CW_info.Titre != true) {
            return;
        }

        SetScriptAnimation(_menu, 57087L, "menu/menues", "TitresMovement");
    }

    /**
     * Method description
     *
     */
    public void SpecObjectMessage1() {
        SpecObjectMessage(2);
    }

    /**
     * Method description
     *
     */
    public void SpecObjectMessage2() {
        SpecObjectMessage(3);
    }

    /**
     * Method description
     *
     */
    public void SpecObjectMessage3() {
        SpecObjectMessage(4);
    }

    /**
     * Method description
     *
     */
    public void SpecObjectMessage4() {
        SpecObjectMessage(6);
    }

    /**
     * Method description
     *
     */
    public void SpecObjectMessage5() {
        SpecObjectMessage(7);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean check_escapemenu_existance() {
        return EscapeMenu.created;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public long escapemenu() {
        return EscapeMenu.CreateEscapeMenu();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public long HelpF1Message() {
        return HelpMenu.CreateMenu();
    }

    /**
     * Method description
     *
     */
    public static void gameoverMenuBlackScreen() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0D, 4);
    }

    /**
     * Method description
     *
     */
    public static void gameoverMenu() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0D, 0);
    }

    /**
     * Method description
     *
     */
    public static void gameoverMenuJail() {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0D, 1);
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public static void gameoverMenuMurder(ILeaveMenuListener listener) {
        NotifyGameOver.CreateNotifyGameOver("ESC", 1000.0D, 2, listener);
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public static void gameoverBankrupt(ILeaveMenuListener listener) {
        NotifyGameOver.CreateNotifyGameOver("", 1000.0D, 3, listener);
    }

    /**
     * Method description
     *
     *
     * @param type_SpecObject
     *
     * @return
     */
    public long SpecObjectMessage(int type_SpecObject) {
        if (type_SpecObject == 1) {
            return 0L;
        }

        menues menu = new menues();
        long wnd = createSimpleMenu(menu, 0);
        gameinfo info = gameinfo.script;

        switch (type_SpecObject) {
         case 2 :
             info.CW_info.ClearAll();
             info.CW_info.OnSpecObject = true;
             info.CW_info.AddString("cpecgasf2");
             info.CW_info.menuId = "f2gasMENU";

             break;

         case 3 :
             info.CW_info.ClearAll();
             info.CW_info.OnSpecObject = true;
             info.CW_info.AddString("cpecrepairf2");
             info.CW_info.menuId = "f2repairMENU";

             break;

         case 4 :
             info.CW_info.ClearAll();
             info.CW_info.OnSpecObject = true;
             info.CW_info.AddString("cpecbarf2");
             info.CW_info.menuId = "f2barMENU";

             break;

         case 6 :
             info.CW_info.ClearAll();
             info.CW_info.OnSpecObject = true;
             info.CW_info.AddString("cpecmotelf2");
             info.CW_info.menuId = "f2motelMENU";

             break;

         case 7 :
             info.CW_info.ClearAll();
             info.CW_info.OnSpecObject = true;
             info.CW_info.AddString("cpecofficef2");
             info.CW_info.menuId = "f2officeMENU";

             break;

         case 8 :
             info.CW_info.ClearAll();
             info.CW_info.OnSpecObject = true;
             info.CW_info.AddString("cpecpolice2");
             info.CW_info.menuId = "f2policeMENU";

             break;

         case 5 :
         default :
             info.CW_info.ClearAll();
             info.CW_info.OnSpecObject = true;
             info.CW_info.AddString("cpdefaultf2");
             info.CW_info.menuId = "f2defaultMENU";
        }

        menu.menuid = info.CW_info.menuId;

        return wnd;
    }

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void SetMenagPOLOSY(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void menu_set_polosy(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void SetStopWorld(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void WindowSet_ShowCursor(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native MENUText_field InitiateTextFields(long paramLong, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native MENUsimplebutton_field InitiateSimpleButton(long paramLong, String paramString1,
            String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native MENU_ranger InitiateRanger(long paramLong, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native MENUbutton_field InitiateButton(long paramLong, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native long CreateTable(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native long CreateGroup(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     * @param paramLong4
     */
    public static native void AddGroupInTable(long paramLong1, long paramLong2, long paramLong3, long paramLong4);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramObject
     */
    public static native void AddControlInGroup(long paramLong1, long paramLong2, Object paramObject);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native long FillMajorDataTable(long paramLong1, long paramLong2, String paramString1,
            String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramString2
     */
    public static native void ScriptSyncGroup(long paramLong1, long paramLong2, String paramString1,
            String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramObject
     */
    public static native void ChangableFieldOnGroup(long paramLong, Object paramObject);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramObject
     * @param paramString
     *
     * @return
     */
    public static native long FillMajorDataTable_ScriptObject(long paramLong1, long paramLong2, Object paramObject,
            String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramObject
     * @param paramString
     */
    public static native void ScriptObjSyncGroup(long paramLong1, long paramLong2, Object paramObject,
            String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramObject
     */
    public static native void LinkGroupAndControl(long paramLong, Object paramObject);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramObject
     */
    public static native void LinkTableAndControl(long paramLong, Object paramObject);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void LingGroupAndGroup(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void LingGroupAndTable(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void LingGroupAndTable(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void LingTableAndTable(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramObject
     */
    public static native void StoreControlState(long paramLong, Object paramObject);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramObject
     * @param paramString1
     * @param paramString2
     */
    public static native void SetRunScriptOnControl(long paramLong, Object paramObject, String paramString1,
            String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramObject
     * @param paramString1
     * @param paramString2
     * @param paramLong2
     */
    public static native void SetRunScriptOnControl(long paramLong1, Object paramObject, String paramString1,
            String paramString2, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramObject
     * @param paramString1
     * @param paramString2
     */
    public static native void SetRunScriptOnControlDataPass(long paramLong, Object paramObject, String paramString1,
            String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramObject
     * @param paramString1
     * @param paramString2
     * @param paramLong2
     */
    public static native void SetRunScriptOnControlDataPass(long paramLong1, Object paramObject, String paramString1,
            String paramString2, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramString2
     */
    public static native void SetRunScriptOnGroup(long paramLong1, long paramLong2, String paramString1,
            String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramString2
     * @param paramLong3
     */
    public static native void SetRunScriptOnGroup(long paramLong1, long paramLong2, String paramString1,
            String paramString2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramString2
     */
    public static native void SetRunScriptOnTable(long paramLong1, long paramLong2, String paramString1,
            String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramString2
     * @param paramLong3
     */
    public static native void SetRunScriptOnTable(long paramLong1, long paramLong2, String paramString1,
            String paramString2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramObject1
     * @param paramObject2
     * @param paramString
     */
    public static native void SetScriptOnControl(long paramLong, Object paramObject1, Object paramObject2,
            String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramObject1
     * @param paramObject2
     * @param paramString
     * @param paramLong2
     */
    public static native void SetScriptOnControl(long paramLong1, Object paramObject1, Object paramObject2,
            String paramString, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramObject1
     * @param paramObject2
     * @param paramString
     */
    public static native void SetScriptOnControlDataPass(long paramLong, Object paramObject1, Object paramObject2,
            String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramObject1
     * @param paramObject2
     * @param paramString
     * @param paramLong2
     */
    public static native void SetScriptOnControlDataPass(long paramLong1, Object paramObject1, Object paramObject2,
            String paramString, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramObject
     * @param paramString
     */
    public static native void SetScriptOnGroup(long paramLong1, long paramLong2, Object paramObject,
            String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramObject
     * @param paramString
     * @param paramLong3
     */
    public static native void SetScriptOnGroup(long paramLong1, long paramLong2, Object paramObject,
            String paramString, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramObject
     * @param paramString
     */
    public static native void SetScriptOnTable(long paramLong1, long paramLong2, Object paramObject,
            String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramObject
     * @param paramString
     * @param paramLong3
     */
    public static native void SetScriptOnTable(long paramLong1, long paramLong2, Object paramObject,
            String paramString, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void RedrawAll(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void RedrawTable(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void RedrawGroup(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramInt
     *
     * @return
     */
    public static native long GetGroupOnLine(long paramLong1, long paramLong2, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     *
     * @return
     */
    public static native Cmenu_TTI GetXMLDataOnGroup(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     *
     * @return
     */
    public static native Cmenu_TTI GetXMLDataOnTable(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramCmenu_TTI
     */
    public static native void SetXMLDataOnGroup(long paramLong1, long paramLong2, Cmenu_TTI paramCmenu_TTI);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramCmenu_TTI
     */
    public static native void SetXMLDataOnTable(long paramLong1, long paramLong2, Cmenu_TTI paramCmenu_TTI);

    /**
     * Method description
     *
     *
     * @param paramCmenu_TTI
     */
    public static native void UpdateData(Cmenu_TTI paramCmenu_TTI);

    /**
     * Method description
     *
     *
     * @param paramCmenu_TTI
     */
    public static native void UpdateDataWithChildren(Cmenu_TTI paramCmenu_TTI);

    /**
     * Method description
     *
     *
     * @param paramCmenu_TTI
     */
    public static native void WrapData(Cmenu_TTI paramCmenu_TTI);

    /**
     * Method description
     *
     *
     * @param paramCmenu_TTI
     */
    public static native void WrapDataWithChildren(Cmenu_TTI paramCmenu_TTI);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void ConnectTableAndData(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     *
     * @return
     */
    public static native long GetTable_byGroup(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     *
     * @return
     */
    public static native long GetLineInTable(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     *
     * @return
     */
    public static native int GetTextHeight(long paramLong, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native int GetTextLineHeight(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native int GetBaseLine(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     */
    public static native void SetBaseLine(long paramLong, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     * @param paramInt
     *
     * @return
     */
    public static native int GetBaseLine(long paramLong, boolean paramBoolean, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt1
     * @param paramBoolean
     * @param paramInt2
     */
    public static native void SetBaseLine(long paramLong, int paramInt1, boolean paramBoolean, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     *
     * @return
     */
    public static native int GetBaseLinePressed(long paramLong, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt1
     * @param paramInt2
     */
    public static native void SetBaseLinePressed(long paramLong, int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native int GetNumStates(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native long GetBackMenu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     *
     * @return
     */
    public static native long FindFieldInMenu(long paramLong, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native long FindFieldInMenu_ByParent(long paramLong, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString
     *
     * @return
     */
    public static native long FindChildInControl(long paramLong1, long paramLong2, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString
     *
     * @return
     */
    public static native long FindFieldInGroup(long paramLong1, long paramLong2, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString
     *
     * @return
     */
    public static native long FindFieldInTable(long paramLong1, long paramLong2, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     */
    public static native void SetFieldText(long paramLong, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     */
    public static native void SetFieldState(long paramLong, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void SetShowField(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetFieldText(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native int GetFieldState(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native boolean GetShowField(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     *
     * @return
     */
    public static native long GetStoredState(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native String GetFieldName(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     */
    public static native void SetFieldParentName(long paramLong, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString
     */
    public static native void SetFieldName(long paramLong1, long paramLong2, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native Object ConvertMenuFields(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramObject
     */
    public static native void UpdateMenuField(Object paramObject);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString
     * @param paramBoolean
     * @param paramInt
     * @param paramDouble1
     * @param paramDouble2
     */
    public static native void SetUVRotationOnGroup(long paramLong1, long paramLong2, String paramString,
            boolean paramBoolean, int paramInt, double paramDouble1, double paramDouble2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString
     * @param paramBoolean
     * @param paramInt
     * @param paramDouble1
     * @param paramDouble2
     */
    public static native void SetUVRotationOnGroupTree(long paramLong1, long paramLong2, String paramString,
            boolean paramBoolean, int paramInt, double paramDouble1, double paramDouble2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString
     * @param paramBoolean1
     * @param paramInt1
     * @param paramLong3
     * @param paramBoolean2
     * @param paramInt2
     */
    public static native void CopyUVRotationOnGroupTree(long paramLong1, long paramLong2, String paramString,
            boolean paramBoolean1, int paramInt1, long paramLong3, boolean paramBoolean2, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramBoolean
     * @param paramInt
     * @param paramString2
     */
    public static native void SetUVAnimationCallBackOnGroup(long paramLong1, long paramLong2, String paramString1,
            boolean paramBoolean, int paramInt, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramBoolean
     * @param paramInt
     * @param paramString2
     */
    public static native void SetUVAnimationCallBackOnGroupTree(long paramLong1, long paramLong2, String paramString1,
            boolean paramBoolean, int paramInt, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramDouble1
     * @param paramDouble2
     * @param paramString2
     */
    public static native void SetAlfaAnimationOnGroup(long paramLong1, long paramLong2, String paramString1,
            double paramDouble1, double paramDouble2, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramDouble1
     * @param paramDouble2
     * @param paramString2
     */
    public static native void SetAlfaAnimationOnGroupTree(long paramLong1, long paramLong2, String paramString1,
            double paramDouble1, double paramDouble2, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramBoolean
     * @param paramInt
     * @param paramString2
     */
    public static native void SetUVAnimationCallBackOnControl(long paramLong, String paramString1,
            boolean paramBoolean, int paramInt, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramDouble1
     * @param paramDouble2
     * @param paramString2
     */
    public static native void SetAlfaAnimationOnControl(long paramLong, String paramString1, double paramDouble1,
            double paramDouble2, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     *
     * @return
     */
    public static native long[] InitXml(long paramLong, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     * @param paramString3
     *
     * @return
     */
    public static native long InitXmlControl(long paramLong, String paramString1, String paramString2,
            String paramString3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_ExitMenu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_OKMenu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_Cancel1Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_Cancel2Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_Cancel3Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_Cancel4Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_Cancel5Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_Cancel6Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_Cancel7Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_Cancel8Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_Cancel9Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_OK1Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_OK2Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_OK3Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_OK4Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_OK5Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_OK6Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_OK7Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_OK8Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void SetMenuCallBack_OK9Menu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_ExitMenu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_OKMenu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_Cancel1Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_Cancel2Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_Cancel3Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_Cancel4Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_Cancel5Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_Cancel6Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_Cancel7Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_Cancel8Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_Cancel9Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_OK1Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_OK2Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_OK3Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_OK4Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_OK5Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_OK6Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_OK7Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_OK8Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramLong3
     */
    public static native void SetMenuCallBack_OK9Menu(long paramLong1, long paramLong2, long paramLong3);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_ExitMenu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_OKMenu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_Cancel1Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_Cancel2Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_Cancel3Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_Cancel4Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_Cancel5Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_Cancel6Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_Cancel7Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_Cancel8Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_Cancel9Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_OK1Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_OK2Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_OK3Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_OK4Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_OK5Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_OK6Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_OK7Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_OK8Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void CallMenuCallBack_OK9Menu(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramInt
     * @param paramLong2
     */
    public static native void SetSyncControlState(long paramLong1, int paramInt, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramInt
     * @param paramLong2
     */
    public static native void SetSyncControlActive(long paramLong1, int paramInt, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramInt
     * @param paramLong2
     */
    public static native void RemoveSyncControlState(long paramLong1, int paramInt, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramInt
     * @param paramLong2
     */
    public static native void RemoveSyncControlActive(long paramLong1, int paramInt, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramString2
     */
    public static native void SetScriptAnimation(long paramLong1, long paramLong2, String paramString1,
            String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString1
     * @param paramString2
     * @param paramDouble
     */
    public static native void SetScriptAnimation(long paramLong1, long paramLong2, String paramString1,
            String paramString2, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramObject
     * @param paramString
     */
    public static native void SetScriptObjectAnimation(long paramLong1, long paramLong2, Object paramObject,
            String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramObject
     * @param paramString
     * @param paramDouble
     */
    public static native void SetScriptObjectAnimation(long paramLong1, long paramLong2, Object paramObject,
            String paramString, double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void StopScriptAnimation(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     *
     * @return
     */
    public static native int GetControlGroup(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     *
     * @return
     */
    public static native int GetControlTable(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     * @param paramString
     */
    public static native void ChangeAllMaterialsOnControl(long paramLong1, long paramLong2, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString1
     * @param paramString2
     */
    public static native void ChangeAllMaterialsOnControl(long paramLong, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     */
    public static native void SetControlColorOnAllSatetesAndTextsAnimated(long paramLong, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     * @param paramInt1
     * @param paramInt2
     */
    public static native void SetControlColor(long paramLong, boolean paramBoolean, int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean1
     * @param paramBoolean2
     * @param paramInt1
     * @param paramInt2
     */
    public static native void SetControlColor(long paramLong, boolean paramBoolean1, boolean paramBoolean2,
            int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     * @param paramInt1
     * @param paramInt2
     */
    public static native void SetTextColor(long paramLong, boolean paramBoolean, int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     */
    public static native void SetTextColor(long paramLong, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     * @param paramInt
     *
     * @return
     */
    public static native int GetTextColor(long paramLong, boolean paramBoolean, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native int GetTextColor(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramObject
     * @param paramString
     */
    public static native void CallMappingModifications(long paramLong, Object paramObject, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void SetBlindess(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void SetIgnoreEvents(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @return
     */
    public static native boolean cancreate_messagewindow();

    /**
     * Method description
     *
     *
     * @return
     */
    public static native boolean cancreate_somenu();

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     */
    public static native void setfocuscontrolonmenu(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native int getSX(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native int getSY(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     *
     * @return
     */
    public static native int getMenuID(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     */
    public static native void setMenuID(long paramLong, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void renderToTarget(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void renderToTargetContinuously(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void menuAnimation_stop(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void menuAnimation_resume(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void menuAnimation_setonend(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void menuAnimation_suspendonend(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void menuAnimation_reset(long paramLong);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void setControlCanOperate(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void setFocusWindow(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramString
     */
    public static native void setSuffixText(long paramLong, String paramString);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramLong2
     *
     * @return
     */
    public static native boolean isControlOnFocus(long paramLong1, long paramLong2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void setFocusOnControl(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void setActiveControl(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramBoolean
     */
    public static native void setPressedControl(long paramLong, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void showMenu(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public static native void hideMenu(int paramInt);

    /**
     * Method description
     *
     *
     * @param field
     *
     * @return
     */
    public static RNRMap ConvertRNRMAPFields(long field) {
        return ((RNRMap) ConvertMenuFields(field));
    }

    /**
     * Method description
     *
     *
     * @param field
     *
     * @return
     */
    public static MENUText_field ConvertTextFields(long field) {
        return ((MENUText_field) ConvertMenuFields(field));
    }

    /**
     * Method description
     *
     *
     * @param field
     *
     * @return
     */
    public static MENUsimplebutton_field ConvertSimpleButton(long field) {
        return ((MENUsimplebutton_field) ConvertMenuFields(field));
    }

    /**
     * Method description
     *
     *
     * @param field
     *
     * @return
     */
    public static MENU_ranger ConvertRanger(long field) {
        return ((MENU_ranger) ConvertMenuFields(field));
    }

    /**
     * Method description
     *
     *
     * @param field
     *
     * @return
     */
    public static MENUbutton_field ConvertButton(long field) {
        return ((MENUbutton_field) ConvertMenuFields(field));
    }

    /**
     * Method description
     *
     *
     * @param field
     *
     * @return
     */
    public static SMenu ConvertWindow(long field) {
        return ((SMenu) ConvertMenuFields(field));
    }

    /**
     * Method description
     *
     *
     * @param fild
     */
    public static void UpdateField(MENUText_field fild) {
        UpdateMenuField(fild);
    }

    /**
     * Method description
     *
     *
     * @param fild
     */
    public static void UpdateField(MENUsimplebutton_field fild) {
        UpdateMenuField(fild);
    }

    /**
     * Method description
     *
     *
     * @param fild
     */
    public static void UpdateField(MENU_ranger fild) {
        UpdateMenuField(fild);
    }

    /**
     * Method description
     *
     *
     * @param fild
     */
    public static void UpdateField(MENUbutton_field fild) {
        UpdateMenuField(fild);
    }

    /**
     * Method description
     *
     *
     * @param fild
     */
    public static void UpdateField(SMenu fild) {
        UpdateMenuField(fild);
    }

    void SetOneTestTextItem(MENUText_field field, int resolution) {
        int sizefont24 = 24;
        double diff = resolution / 1600.0D;

        sizefont24 = (int) Math.floor(sizefont24 * diff);
        SetOneTextField(field, 1600, 1200, 1101, 163, 330, 36, "Arial", sizefont24, (int) (26.0D * diff), "LeftBase",
                        -2334, true, false);
    }

    void SetUpOneMenu(SMenu _menu, int xres, int yres, int px, int py, int lx, int ly, String texname,
                      boolean statettex, double tx1, double ty1, double tx2, double ty2) {
        _menu.Xres = xres;
        _menu.Yres = yres;
        _menu.pox = px;
        _menu.poy = py;
        _menu.lenx = lx;
        _menu.leny = ly;
        _menu.texture = new CTextur_whithmapping();
        _menu.material = new CMaterial_whithmapping();
        _menu.material.material = "";
        _menu.texture.state = statettex;
        _menu.texture.t1x = tx1;
        _menu.texture.t1y = ty1;
        _menu.texture.t2x = tx2;
        _menu.texture.t2y = ty2;
        _menu.texture.texture = texname;
    }

    void SetUpOneMenu(SMenu _menu, int xres, int yres, int px, int py, int lx, int ly) {
        _menu.Xres = xres;
        _menu.Yres = yres;
        _menu.pox = px;
        _menu.poy = py;
        _menu.lenx = lx;
        _menu.leny = ly;
        _menu.texture = new CTextur_whithmapping();
        _menu.material = new CMaterial_whithmapping();
    }

    void SetOneTextField(MENUText_field field, int xres, int yres, int px, int py, int lx, int ly, String fnt, int sz,
                         int bzln, String TextFlags, int textcolor, boolean _bold, boolean _itallic) {
        field.Xres = xres;
        field.Yres = yres;
        field.pox = px;
        field.poy = py;
        field.lenx = lx;
        field.leny = ly;
        field.font = fnt;
        field.fonssz = sz;
        field.baseline = bzln;
        field.bold = _bold;
        field.italic = _itallic;
        field.TextFlags = TextFlags;
        field.textColor = textcolor;
        field.callbacks = new Vector();
    }

    void SetOneSimpleButton(MENUsimplebutton_field button, int xres, int yres, int px, int py, int lx, int ly) {
        button.Xres = xres;
        button.Yres = yres;
        button.pox = px;
        button.poy = py;
        button.lenx = lx;
        button.leny = ly;
        button.callbacks = new Vector();
    }

    void SetOneButton(MENUbutton_field button, int xres, int yres, int px, int py, int lx, int ly, int nomstates) {
        button.Xres = xres;
        button.Yres = yres;
        button.pox = px;
        button.poy = py;
        button.lenx = lx;
        button.leny = ly;
        button.nomstates = nomstates;
        button.callbacks = new Vector();
    }

    void SetOneRanger(MENU_ranger button, int xres, int yres, int px, int py, int lx, int ly, String _tip,
                      String _orientation) {
        button.Xres = xres;
        button.Yres = yres;
        button.pox = px;
        button.poy = py;
        button.lenx = lx;
        button.leny = ly;
        button.type = _tip;
        button.orientation = _orientation;
        button.thumb_texstates = new Vector();
        button.buttonup_texstates = new Vector();
        button.buttondown_texstates = new Vector();
        button.thumb_material = new Vector();
        button.buttonup_material = new Vector();
        button.buttondown_material = new Vector();
        button.callbacks = new Vector();
    }

    CTextur_whithmapping PrepairTexture(boolean _state, double tex1, double tey1, double lengthx, double lengthy,
            double lnx, double lny, String _name) {
        double t1 = tex1 / lnx;
        double t2 = tey1 / lny;
        double t3 = (tex1 + lengthx) / lnx;
        double t4 = (tey1 + lengthy) / lny;
        CTextur_whithmapping inTEX = new CTextur_whithmapping();

        inTEX.state = _state;
        inTEX.t1x = t1;
        inTEX.t1y = t2;
        inTEX.t2x = t3;
        inTEX.t2y = t4;
        inTEX.texture = _name;

        return inTEX;
    }

    CTextur_whithmapping PrepairTexture(boolean _state, double t1, double t2, double t3, double t4, String _name) {
        CTextur_whithmapping inTEX = new CTextur_whithmapping();

        inTEX.state = _state;
        inTEX.t1x = t1;
        inTEX.t1y = t2;
        inTEX.t2x = t3;
        inTEX.t2y = t4;
        inTEX.texture = _name;

        return inTEX;
    }

    CMaterial_whithmapping PrepairTexture(boolean _active, String _name) {
        CMaterial_whithmapping inTEX = new CMaterial_whithmapping();

        inTEX.tex = new Vector();
        inTEX._active = _active;
        inTEX.material = _name;

        return inTEX;
    }

    void AddMaterial(CMaterial_whithmapping inTEX, int nomtex, double x0, double y0, double lx, double ly, double rx,
                     double ry) {
        double tx0f = x0 / rx;
        double ty0f = y0 / ry;
        double tx1f = (x0 + lx) / rx;
        double ty1f = y0 / ry;
        double tx2f = (x0 + lx) / rx;
        double ty2f = (y0 + ly) / ry;
        double tx3f = x0 / rx;
        double ty3f = (y0 + ly) / ry;

        inTEX.Add(nomtex, (float) tx0f, (float) ty0f, (float) tx1f, (float) ty1f, (float) tx2f, (float) ty2f,
                  (float) tx3f, (float) ty3f);
    }

    void Detail1(CMaterial_whithmapping mat1_tex, double xl, double yl) {
        double xres0 = 1024.0D;
        double yres0 = 512.0D;
        double xres0LEN = 1600.0D;
        double yres0LEN = 1200.0D;
        double xres = eng.GetXResolution();
        double yres = eng.GetYResolution();
        double xl0 = 1024.0D;
        double yl0 = 512.0D;
        double tilex = 1.0D;
        double tiley = 35.0D;
        double tilexres = xres0 * tilex * xres * xres * xl / xres0 * xl0 * xres0LEN;
        double tileyres = yres0 * tiley * yres * yres * yl / yres0 * yl0 * yres0LEN;

        mat1_tex.AddMaterial(1, 0.0D, 0.0D, tilexres, tileyres, xres0, yres0);
    }

    void Detail2(CMaterial_whithmapping mat1_tex, double xl, double yl) {
        double xres0 = 1024.0D;
        double yres0 = 512.0D;
        double xres0LEN = 1600.0D;
        double yres0LEN = 1200.0D;
        double xres = eng.GetXResolution();
        double yres = eng.GetYResolution();
        double xl0 = 1024.0D;
        double yl0 = 512.0D;
        double tilex = 1.0D;
        double tiley = 0.05D;
        double tilexres = xres0 * tilex * xres * xres * xl / xres0 * xl0 * xres0LEN;
        double tileyres = yres0 * tiley * yres * yres * yl / yres0 * yl0 * yres0LEN;

        mat1_tex.AddMaterial(2, 0.0D, 0.0D, tilexres, tileyres, xres0, yres0);
    }

    void Detail2(CMaterial_whithmapping mat1_tex, double x0, double y0, double _X0, double _Y0, double x, double y,
                 double xl, double yl) {
        double xres0 = 4.0D;
        double yres0 = 1024.0D;
        double tilex = 1.0D;
        double tiley = 0.05D;
        double tx = tilex * xres0;
        double ty = tiley * yres0;
        double u = (x - x0) * tx / _X0;
        double v = (y - y0) * ty / _Y0;
        double wu = xl * tx / _X0;
        double wv = yl * ty / _Y0;

        mat1_tex.AddMaterial(2, u, v, wu, wv, xres0, yres0);
    }

    void testestes(CMaterial_whithmapping matt) {
        matt._state = 3;

        Object[] a = matt.tex.toArray();
        ctexcoord_multylayer F = (ctexcoord_multylayer) a[0];

        F.index = 50;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ
     */
    public class CMaterial_whithmapping {

        /** Field description */
        public int _state;

        /** Field description */
        public boolean _active;

        /** Field description */
        public boolean usepatch;

        /** Field description */
        public boolean pressed;

        /** Field description */
        public menues.s_nat_patch _patch;

        /** Field description */
        @SuppressWarnings("rawtypes")
        public Vector tex;

        /** Field description */
        public String material;

        @SuppressWarnings("rawtypes")
        CMaterial_whithmapping() {
            this.tex = new Vector();
        }

        @SuppressWarnings("unchecked")
        void Add(int nomtexcoords, float tx0, float ty0, float tx1, float ty1, float tx2, float ty2, float tx3,
                 float ty3) {
            menues.ctexcoord_multylayer texcoord = new menues.ctexcoord_multylayer();

            texcoord.index = nomtexcoords;
            texcoord.t0x = tx0;
            texcoord.t0y = ty0;
            texcoord.t1x = tx1;
            texcoord.t1y = ty1;
            texcoord.t2x = tx2;
            texcoord.t2y = ty2;
            texcoord.t3x = tx3;
            texcoord.t3y = ty3;
            this.tex.add(texcoord);
        }

        void AddMaterial(int nomtex, double x0, double y0, double lx, double ly, double rx, double ry) {
            double tx0f = x0 / rx;
            double ty0f = y0 / ry;
            double tx1f = (x0 + lx) / rx;
            double ty1f = y0 / ry;
            double tx2f = (x0 + lx) / rx;
            double ty2f = (y0 + ly) / ry;
            double tx3f = x0 / rx;
            double ty3f = (y0 + ly) / ry;

            Add(nomtex, (float) tx0f, (float) ty0f, (float) tx1f, (float) ty1f, (float) tx2f, (float) ty2f,
                (float) tx3f, (float) ty3f);
        }

        void AddMaterial(int nomtex, double x0, double y0, double x1, double y1, double x2, double y2, double x3,
                         double y3, double rx, double ry) {
            double tx0f = x0 / rx;
            double ty0f = y0 / ry;
            double tx1f = x1 / rx;
            double ty1f = y1 / ry;
            double tx2f = x2 / rx;
            double ty2f = y2 / ry;
            double tx3f = x3 / rx;
            double ty3f = y3 / ry;

            Add(nomtex, (float) tx0f, (float) ty0f, (float) tx1f, (float) ty1f, (float) tx2f, (float) ty2f,
                (float) tx3f, (float) ty3f);
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ
     */
    public class CRepairItem {
        boolean openlist;
        String name;
        double price;
        int condition;
        boolean authorize;
        int[] nom_lines;
        String otstup;
    }


    class CTextur_whithmapping {
        boolean state;
        double t1x;
        double t1y;
        double t2x;
        double t2y;
        String texture;
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ
     */
    public class cMenuItemCallback {
        int id;
        String ScriptName;
        String MethodName;

        cMenuItemCallback(int paramInt, String paramString1, String paramString2) {
            this.id = paramInt;
            this.ScriptName = paramString1;
            this.MethodName = paramString2;
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ
     */
    public class ctexcoord_multylayer {

        /** Field description */
        public int index;

        /** Field description */
        public boolean video;

        /** Field description */
        public float t0x;

        /** Field description */
        public float t0y;

        /** Field description */
        public float t1x;

        /** Field description */
        public float t1y;

        /** Field description */
        public float t2x;

        /** Field description */
        public float t2y;

        /** Field description */
        public float t3x;

        /** Field description */
        public float t3y;
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ
     */
    public class s_nat_patch {

        /** Field description */
        public int x;

        /** Field description */
        public int y;

        /** Field description */
        public int sx;

        /** Field description */
        public int sy;

        /** Field description */
        public String tip;

        /**
         * Constructs ...
         *
         */
        public s_nat_patch() {
            this.tip = new String();
        }
    }
}


//~ Formatted in DD Std on 13/08/25
