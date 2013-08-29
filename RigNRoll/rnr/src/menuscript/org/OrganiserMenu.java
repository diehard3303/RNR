/*
 * @(#)OrganiserMenu.java   13/08/26
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


package rnr.src.menuscript.org;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.Common;
import rnr.src.menu.Helper;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MacroKit;
import rnr.src.menu.MenuAfterInitNarrator;
import rnr.src.menu.MenuControls;
import rnr.src.menu.TextScroller;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.menu.xmlcontrols;
import rnr.src.menu.xmlcontrols.MENUCustomStuff;
import rnr.src.menuscript.Converts;
import rnr.src.menuscript.IUpdateListener;
import rnr.src.menuscript.PoPUpMenu;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.loc;
import rnr.src.rnrrating.RateSystem;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class OrganiserMenu implements menucreation, IUpdateListener {
    static final boolean DEBUG = eng.noNative;
    private static final String XML = "..\\data\\config\\menu\\menu_com.xml";
    private static final String CONTROLS_MAIN = "Communicator";
    private static final String[] TAB_NAMES = { "Tab0 - ORGANIZER", "Tab0 - JOURNAL", "Tab0 - WEATHER", "Tab0 - EXIT",
            "Tab0 - PHOTO ALBUM" };

    /** Field description */
    public static final int TAB_ORG = 0;

    /** Field description */
    public static final int TAB_JOU = 1;

    /** Field description */
    public static final int TAB_WEATHER = 2;
    private static final int TAB_EXIT = 3;

    /** Field description */
    public static final int TAB_PHOTO_ALBUM = 4;
    private static final String TAB_METHOD = "onTab";
    private static final String PARAMS = "INFORMATION STRING";
    private static final String[] MACROSES = { "SIGN", "MONEY", "RANK", "RATING" };
    private static final int MACRO_SIGN = 0;
    private static final int MACRO_MONEY = 1;
    private static final int MACRO_RANK = 2;
    private static final int MACRO_RATING = 3;
    private PoPUpMenu show_help = null;
    private long help_text_title = 0L;
    private long help_text = 0L;
    private long help_text_scroller = 0L;
    TextScroller scroller = null;
    String info_text = null;
    private MenuControls allmenu = null;
    private final ArrayList<IOrgTab> tabs = new ArrayList();
    Common common;
    private long params_text;
    private String params_text_value;

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
    public static long create() {
        return menues.CreateJournalMenu(new OrganiserMenu(), 1600, 1200, 1600, 1200, 0, 0,
                                        "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        if (DEBUG) {
            eng.noNative = true;
        }

        this.common = new Common(_menu);
        this.allmenu = new MenuControls(_menu, "..\\data\\config\\menu\\menu_com.xml", "Communicator");
        this.params_text = this.allmenu.findControl("INFORMATION STRING");
        this.params_text_value = menues.GetFieldText(this.params_text);

        for (int i = 0; i < TAB_NAMES.length; ++i) {
            if (i != 4) {
                long tab = menues.FindFieldInMenu(_menu, TAB_NAMES[i]);
                xmlcontrols.MENUCustomStuff obj_tab = (xmlcontrols.MENUCustomStuff) menues.ConvertMenuFields(tab);

                obj_tab.userid = i;
                menues.UpdateMenuField(obj_tab);
                menues.SetScriptOnControl(_menu, obj_tab, this, "onTab", 10L);

                switch (i) {
                 case 0 :
                     OrganiserPane org = new OrganiserPane(_menu, this);

                     this.tabs.add(org);
                     org.addUpdateListener(this);

                     break;

                 case 1 :
                     this.tabs.add(new JournalPane(_menu, this));

                     break;

                 case 2 :
                     this.tabs.add(new OrganizerWeather(_menu, this));

                     break;

                 default :
                     this.tabs.add(new VoidTab());
                }
            }
        }

        menues.InitXml(_menu, "..\\data\\config\\menu\\menu_com.xml", "TOOLTIP - common");
        this.show_help = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_com.xml", "TOOLTIP - BLACK SHARK");
        this.help_text_title = this.show_help.getField("CALL COMMUNICATOR HELP - TITLE");
        this.help_text = this.show_help.getField("CALL COMMUNICATOR HELP - TEXT");
        this.help_text_scroller = this.show_help.getField("CALL COMMUNICATOR HELP - Tableranger");
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        for (IOrgTab pane : this.tabs) {
            pane.afterInit();
        }

        this.show_help.afterInit();
        MenuAfterInitNarrator.justShowAndStop(_menu);
        menues.SetFieldState(menues.FindFieldInMenu(_menu, TAB_NAMES[0]), 1);

        long photo_album = menues.FindFieldInMenu(_menu, TAB_NAMES[4]);

        if (photo_album != 0L) {
            menues.SetShowField(photo_album, false);
        }

        updateStatusBar();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {
        for (IOrgTab pane : this.tabs) {
            pane.exitMenu();
        }

        if (this.scroller != null) {
            this.scroller.Deinit();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "organiserMENU";
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onTab(long _menu, xmlcontrols.MENUCustomStuff button) {
        if (button.userid == 3) {
            menues.CallMenuCallBack_ExitMenu(_menu);

            return;
        }

        for (int i = 0; i < this.tabs.size(); ++i) {
            if (i == button.userid) {
                this.tabs.get(i).enterFocus();
            } else {
                this.tabs.get(i).leaveFocus();
            }
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void onUpdate() {
        updateStatusBar();
    }

    private void updateStatusBar() {
        if (DEBUG) {
            return;
        }

        TotalsValues values = new TotalsValues();

        JavaEvents.SendEvent(44, 9, values);

        int rating = RateSystem.gLiveRating();
        CoreTime time = new CoreTime();
        KeyPair[] pairs = new KeyPair[MACROSES.length];

        pairs[0] = new KeyPair(MACROSES[0], (Math.abs(values.balance) >= 0)
                ? ""
                : "-");
        pairs[1] = new KeyPair(MACROSES[1], Helper.convertMoney(values.balance));
        pairs[2] = new KeyPair(MACROSES[2], "" + values.rank);
        pairs[3] = new KeyPair(MACROSES[3], "" + rating);
        menues.SetFieldText(this.params_text,
                            MacroKit.Parse(Converts.ConvertDateAbsolute(this.params_text_value, time.gMonth(),
                                time.gDate(), time.gYear(), time.gHour(), time.gMinute()), pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(this.params_text));
    }

    /**
     * Method description
     *
     *
     * @param tab_id
     */
    public void ShowTabHelp(int tab_id) {
        String tite_loc = null;
        String help_loc = null;

        if (tab_id == 0) {
            tite_loc = "menu_com.xml\\Communicator\\Com - BACK ALL\\TAB\\Tab0 - ORGANIZER";
            help_loc = "menu_com.xml\\TOOLTIP - BLACK SHARK\\TOOLTIP - BLACK SHARK\\CALL COMMUNICATOR HELP - ORGANIZER";
        } else if (tab_id == 1) {
            tite_loc = "menu_com.xml\\Communicator\\Com - BACK ALL\\TAB\\Tab0 - JOURNAL";
            help_loc = "menu_com.xml\\TOOLTIP - BLACK SHARK\\TOOLTIP - BLACK SHARK\\CALL COMMUNICATOR HELP - JOURNAL";
        } else if (tab_id == 4) {
            tite_loc = "menu_com.xml\\Communicator\\Com - BACK ALL\\TAB\\Tab0 - PHOTO ALBUM";
            help_loc = "menu_com.xml\\TOOLTIP BLACK SHARK\\TOOLTIP BLACK SHARK\\CALL COMMUNICATOR HELP PHOTO ALBUM";
        } else if (tab_id == 2) {
            tite_loc = "menu_com.xml\\Communicator\\Com - BACK ALL\\TAB\\Tab0 - WEATHER";
            help_loc = "menu_com.xml\\TOOLTIP - BLACK SHARK\\TOOLTIP - BLACK SHARK\\CALL COMMUNICATOR HELP - WEATHER";
        } else {
            return;
        }

        if (this.help_text_title != 0L) {
            menues.SetFieldText(this.help_text_title, loc.getMENUString(tite_loc));
            menues.UpdateMenuField(menues.ConvertMenuFields(this.help_text_title));
        }

        String help_text_text = loc.getMENUString(help_loc);

        if ((help_text_text != null) && (this.help_text != 0L) && (this.help_text_scroller != 0L)) {
            MENU_ranger ranger = (MENU_ranger) menues.ConvertMenuFields(this.help_text_scroller);
            MENUText_field text = (MENUText_field) menues.ConvertMenuFields(this.help_text);

            if ((ranger != null) && (text != null)) {
                text.text = help_text_text;
                menues.UpdateField(text);

                int texh = menues.GetTextLineHeight(text.nativePointer);
                int startbase = menues.GetBaseLine(text.nativePointer);
                int linescreen = Converts.HeightToLines(text.leny, startbase, texh);
                int linecounter = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, help_text_text),
                                      startbase, texh) + 3;

                if (this.scroller != null) {
                    this.scroller.Deinit();
                }

                this.scroller = new TextScroller(this.common, ranger, linecounter, linescreen, texh, startbase, true,
                                                 "CALL OFFICE HELP - TEXT");
                this.scroller.AddTextControl(text);
            }
        }

        this.show_help.show();
    }

    static class TotalsValues {
        int balance;
        int rank;
    }
}


//~ Formatted in DD Std on 13/08/26
