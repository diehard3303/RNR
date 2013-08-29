/*
 * @(#)EscapeMenu.java   13/08/26
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


package rnr.src.menuscript;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.Common;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.ListenerManager;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.SMenu;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.menuscript.mainmenu.EscapeSaveLoad;
import rnr.src.menuscript.mainmenu.EscapeSaveLoadReplay;
import rnr.src.menuscript.mainmenu.TopWindow;
import rnr.src.rnrcore.Log;
import rnr.src.rnrscenario.ScenarioFlagsManager;
import rnr.src.rnrscr.gameinfo;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class EscapeMenu extends ParentMenu implements menucreation, IresolutionChanged, IQuitMenu {
    static final int TAB_MAIN = 0;
    static final int TAB_RESTART = 1;
    static final int TAB_SAVELOAD = 2;
    static final int TAB_OPTIONS = 3;
    static final int TAB_QUIT = 4;
    static final int TAB_ROADSERVICE = 5;
    static final int TAB_BACK = 6;
    static final int TAB_MAX = 7;

    /** Field description */
    public static final int GAME_SINGLE_PLAYER = 0;

    /** Field description */
    public static final int GAME_MULTI_PLAYER = 1;

    /** Field description */
    public static final int GAME_INSTANT_ORDER = 2;

    /** Field description */
    public static final int GAME_REPLAY = 3;
    private static int game_type = 0;
    private static String[] s_single_buttonnames = {
        "Tab0 - Main Menu", "Tab0 - Restart", "Tab0 - Save/Load", "Tab0 - Options", "Tab0 - Quit Game",
        "Tab0 - Road Service", "Tab0 - Back"
    };
    private static String[] s_single_graybuttonnames = {
        "Tab0 - Main Menu", "Tab0 - Restart", "Tab0 - Save/Load GRAY", "Tab0 - Options", "Tab0 - Quit Game",
        "Tab0 - Road Service GRAY", "Tab0 - Back"
    };
    private static String[] s_replay_buttonnames = {
        "Tab0 - Main Menu", "Tab0 - Restart", "Tab0 - Load", "Tab0 - Options", "Tab0 - Quit Game",
        "Tab0 - Road Service", "Tab0 - Back"
    };
    private static String[] s_replay_graybuttonnames = {
        "Tab0 - Main Menu", "Tab0 - Restart", "Tab0 - Load", "Tab0 - Options", "Tab0 - Quit Game",
        "Tab0 - Road Service GRAY", "Tab0 - Back"
    };
    private static boolean[] s_buttons_gray = {
        false, false, false, false, false, false, false
    };

    /** Field description */
    public static final int ID_SAVE = 0;

    /** Field description */
    public static boolean created = false;
    private static boolean f_changeVideoResolution = false;
    Vector m_Saves = new Vector();
    private boolean f_initCompleted = false;
    ConfirmDialog m_cdialog;

    /**
     * Method description
     *
     *
     * @param type
     */
    public static void SetGameType(long type) {
        if (type == 0L) {
            s_buttons_gray[2] = false;
            s_buttons_gray[5] = false;
            game_type = 0;
        } else if (type == 1L) {
            s_buttons_gray[2] = false;
            s_buttons_gray[5] = false;
            game_type = 1;
        } else if (type == 2L) {
            s_buttons_gray[2] = true;
            s_buttons_gray[5] = false;
            game_type = 2;
        } else if (type == 3L) {
            s_buttons_gray[2] = false;
            s_buttons_gray[5] = true;
            game_type = 3;
        } else if (type == 4L) {
            s_buttons_gray[2] = true;
            s_buttons_gray[5] = false;
            game_type = 0;
        }

        if ((game_type != 0) || (s_buttons_gray[2] != 0) || (ScenarioFlagsManager.getInstance() == null)
                || (ScenarioFlagsManager.getInstance().getFlagValue("SavesEnabledByScenario"))) {
            return;
        }

        s_buttons_gray[2] = true;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        TopWindow.Register(this);
        this.common = new Common(_menu);
        ListenerManager.TriggerEvent(103);
        menues.InitXml(_menu, Common.ConstructPath("menu_esc.xml"), "Menu Esc");
        menues.WindowSet_ShowCursor(_menu, true);
        menues.SetStopWorld(_menu, true);
        JavaEvents.SendEvent(2, 0, this);
        this.m_cdialog = new ConfirmDialog(this.common, "TabSwitchWarning - REMAP", "TabSwitchWarning - REMAP - TEXT",
                                           "BUTT - TabSwitchWarning - REMAP - YES",
                                           "BUTT - TabSwitchWarning - REMAP - NO",
                                           "BUTT - TabSwitchWarning - REMAP - CANCEL");

        int NUM_BUTTONS = (game_type != 3)
                          ? s_single_buttonnames.length
                          : s_replay_buttonnames.length;
        ISubMenu[] tab_menues = new ISubMenu[NUM_BUTTONS];

        for (int i = 0; i < NUM_BUTTONS; ++i) {
            if (i == 3) {
                EscapeOptions settings = new EscapeOptions(_menu, this.common, this.m_cdialog, game_type);

                settings.addListener(new MenuClose(i));
                tab_menues[i] = settings;
            } else if (i == 4) {
                EscapeQuit quit = new EscapeQuit(_menu);

                quit.addListener(new MenuClose(i));
                tab_menues[i] = quit;
            } else if (i == 0) {
                EscapeMainMenu mmenu = new EscapeMainMenu(_menu);

                mmenu.addListener(new MenuClose(i));
                tab_menues[i] = mmenu;
            } else if (i == 2) {
                if (game_type != 3) {
                    EscapeSaveLoad saveload = new EscapeSaveLoad(_menu, this.common, this.m_cdialog);

                    saveload.addListener(new MenuClose(i));
                    tab_menues[i] = saveload;
                } else {
                    EscapeSaveLoadReplay saveload = new EscapeSaveLoadReplay(_menu, this.common, this.m_cdialog);

                    saveload.addListener(new MenuClose(i));
                    tab_menues[i] = saveload;
                }
            } else if (i == 1) {
                tab_menues[i] = null;
            } else {
                tab_menues[i] = null;
            }
        }

        for (int i = 0; i < NUM_BUTTONS; ++i) {
            if (game_type != 3) {
                if (s_buttons_gray[i] != 0) {
                    addButton(this.common.FindRadioButton(s_single_graybuttonnames[i]), tab_menues[i]);
                } else {
                    addButton(this.common.FindRadioButton(s_single_buttonnames[i]), tab_menues[i]);
                }
            } else if (s_buttons_gray[i] != 0) {
                addButton(this.common.FindRadioButton(s_replay_graybuttonnames[i]), tab_menues[i]);
            } else {
                addButton(this.common.FindRadioButton(s_replay_buttonnames[i]), tab_menues[i]);
            }
        }

        initSubMenues();

        EscapeOptions esc_op = (EscapeOptions) tab_menues[3];

        esc_op.addVideoSettingsListener(this);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        this.f_initCompleted = true;
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.GetBackMenu(_menu)), this, "OnExit", 17L);
        afterInitSubMenues();
        ListenerManager.TriggerEvent(104);
        menues.setShowMenu(_menu, true);
        menues.SetStopWorld(_menu, true);

        if (game_type != 3) {
            for (int i = 0; i < s_single_buttonnames.length; ++i) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_buttonnames[i]), false);
            }

            for (int i = 0; i < s_single_graybuttonnames.length; ++i) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[i]), false);
            }

            for (int i = 0; i < s_buttons_gray.length; ++i) {
                if (s_buttons_gray[i] != 0) {
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[i]), true);
                    menues.SetIgnoreEvents(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[i]), true);
                    menues.SetBlindess(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[i]), true);
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_buttonnames[i]), false);
                } else {
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[i]), false);
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_buttonnames[i]), true);
                }
            }

            menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[1]), false);
            menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_buttonnames[1]), false);
        } else {
            for (int i = 0; i < s_single_buttonnames.length; ++i) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_buttonnames[i]), false);
            }

            for (int i = 0; i < s_single_graybuttonnames.length; ++i) {
                menues.SetShowField(menues.FindFieldInMenu(_menu, s_single_graybuttonnames[i]), false);
            }

            for (int i = 0; i < s_buttons_gray.length; ++i) {
                if (s_buttons_gray[i] != 0) {
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[i]), true);
                    menues.SetIgnoreEvents(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[i]), true);
                    menues.SetBlindess(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[i]), true);
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_buttonnames[i]), false);
                } else {
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[i]), false);
                    menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_buttonnames[i]), true);
                }
            }

            menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_graybuttonnames[1]), false);
            menues.SetShowField(menues.FindFieldInMenu(_menu, s_replay_buttonnames[1]), false);
        }

        menues.setFocusWindow(menues.getMenuID(_menu));

        if (f_changeVideoResolution) {
            selectButton(3);
            menues.SetFieldState(menues.FindFieldInMenu(_menu, "Tab0 - VIDEO"), 1);
            f_changeVideoResolution = false;
        } else {
            menues.setfocuscontrolonmenu(_menu, this.buttons.get(5).longValue());
        }
    }

    private void SimpleButtonCheck(int m_curtab) {
        if (m_curtab == 5) {
            JavaEvents.SendEvent(6, 0, this);
            menues.CallMenuCallBack_ExitMenu(this.common.GetMenu());
        } else if (m_curtab == 6) {
            menues.CallMenuCallBack_ExitMenu(this.common.GetMenu());
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param tab
     */
    public void OnButton(long _menu, MENUbutton_field tab) {
        if (!(this.f_initCompleted)) {
            return;
        }

        super.OnButton(_menu, tab);

        if (!(this.buttonPending)) {
            SimpleButtonCheck(tab.userid);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {
        TopWindow.UnRegister(this);
        created = false;
        super.exitMenu();
        JavaEvents.SendEvent(3, 0, this);
        ListenerManager.TriggerEvent(105);
        this.buttons = null;
    }

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
        return "escMENU";
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void On911(long _menu, MENUsimplebutton_field button) {
        long pplayer = gameinfo.script.m_pPlayer;

        RepairPlayer(pplayer);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static long CreateEscapeMenu() {
        if (!(created)) {
            created = true;

            return menues.createSimpleMenu(new EscapeMenu(), 10000000.0D, "", 1600, 1200, 1600, 1200, 0, 0,
                                           "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        }

        return 0L;
    }

    void RefreshGameData(int value) {
        JavaEvents.SendEvent(0, value, this);
    }

    void UpdateGameData(int value) {
        JavaEvents.SendEvent(1, value, this);
    }

    /**
     * Method description
     *
     *
     * @param paramLong
     */
    public static native void RepairPlayer(long paramLong);

    @Override
    void NeedToConfirm(String text) {
        this.m_cdialog.AskUser(this, text);
    }

    /**
     * Method description
     *
     */
    @Override
    public void changed() {
        f_changeVideoResolution = true;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param menu
     */
    public void OnExit(long _menu, SMenu menu) {
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    /**
     * Method description
     *
     */
    @Override
    public void quitMenu() {
        menues.CallMenuCallBack_ExitMenu(this.common.s_menu);
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

    class MenuClose implements IMenuListener {

        /** Field description */
        public int nom_tab;

        MenuClose(int paramInt) {
            this.nom_tab = paramInt;

            if (paramInt >= 7) {
                Log.menu("MenuClose asked to act with wrong tab. nomtab = " + paramInt);
            }
        }

        /**
         * Method description
         *
         */
        @Override
        public void onClose() {
            EscapeMenu.this.makeNonActiveState();
            menues.setfocuscontrolonmenu(EscapeMenu.this.common.GetMenu(), EscapeMenu.this.buttons.get(6).longValue());
        }

        /**
         * Method description
         *
         */
        @Override
        public void onOpen() {}
    }


    static class SaveItem {
        String name;

        SaveItem(String _name) {
            this.name = _name;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
