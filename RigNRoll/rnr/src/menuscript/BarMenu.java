/*
 * @(#)BarMenu.java   13/08/26
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

import rnr.src.menu.BaseMenu;
import rnr.src.menu.Common;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.MenuControls;
import rnr.src.menu.NotifyInformation;
import rnr.src.menu.TextScroller;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.players.Crew;
import rnr.src.players.IcontaktCB;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.SCRtalkingperson;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;
import rnr.src.rnrscr.Bar;
import rnr.src.rnrscr.BarMenuCreator;
import rnr.src.rnrscr.Dialog;
import rnr.src.rnrscr.IBarMoveNewspaperAnimation;
import rnr.src.rnrscr.SODialogParams;
import rnr.src.rnrscr.smi.IArticle;
import rnr.src.rnrscr.smi.Newspapers;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class BarMenu extends BaseMenu implements menucreation {

    /** Field description */
    public static final boolean DEBUG = eng.noNative;
    private static final String XML = "..\\data\\config\\menu\\menu_bar.xml";

    /** Field description */
    public static final int NEWS = 0;

    /** Field description */
    public static final int RACE = 1;

    /** Field description */
    public static final int SUMMARY = 2;

    /** Field description */
    public static final int TYPENEWS_SIZE = 3;
    private static final int max_lines_in_text_body = 15;
    private static final int max_lines_in_head_body = 4;
    private static boolean was_dialog = false;

    /** Field description */
    public static boolean dialog_ended = true;

    /** Field description */
    public static boolean was_news = false;
    private static BarMenu gmenu;
    private IBarMoveNewspaperAnimation animation = null;
    @SuppressWarnings("rawtypes")
    Vector m_photopapers = new Vector();
    int lasttextlines = 1;
    int lasthlines = 1;

    /** Field description */
    public String texture_name = "none";
    private String currentdialog = "";
    private SelectionPaper currentselection = null;
    private MenuCamera currentmenucamera = null;
    private final String bar_name;
    private final vectorJ pos;
    private long _menu;
    boolean m_singlepaper;
    BarEntry simple_entry;
    MENUText_field m_mainmenu;
    MENUText_field m_papermenu;
    MENUText_field m_articlemenu;
    MENUText_field m_photopic;
    MENUsimplebutton_field[] m_newsbuttons;
    private BarMenuSlots slots;
    TextScroller scroller;

    private BarMenu(String bar_name, vectorJ position, IBarMoveNewspaperAnimation animation) {
        this.animation = animation;
        this.bar_name = bar_name;
        this.pos = position;
    }

    /**
     * Method description
     *
     *
     * @param bar_name
     * @param position
     * @param animation
     *
     * @return
     */
    public static long CreateBarMenu(String bar_name, vectorJ position, IBarMoveNewspaperAnimation animation) {
        BarMenu menu = new BarMenu(bar_name, position, animation);

        gmenu = menu;
        was_dialog = false;
        was_news = false;

        return menues.createSimpleMenu(menu, 10000000.0D, "", 1600, 1200, 1600, 1200, 0, 0,
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
        this._menu = _menu;

        MenuControls allmenu = new MenuControls(_menu, "..\\data\\config\\menu\\menu_bar.xml", "BAR ALL", false);
        long text_field = allmenu.findControl("BarMenu - ALL");

        this.m_mainmenu = menues.ConvertTextFields(text_field);
        this.slots = new BarMenuSlots(this, _menu, this.pos);
        this.common = new Common(_menu);

        if (DEBUG) {
            this.m_singlepaper = false;
        } else {
            this.m_singlepaper = BarMenuCreator.isSinglePaperInBar(this.pos);
        }

        this.m_singlepaper = false;
        InitPaper();

        if (this.m_singlepaper) {
            Place bar_place = MissionSystemInitializer.getMissionsMap().getNearestPlaceByType(0);
            BarEntry[] entries = null;

            if (null != bar_place) {
                entries = Newspapers.getTheOnlyNewsPaper_BarEntries(bar_place.getName());
            }

            this.simple_entry = entries[0];
        } else {
            manageMenuCamera_enter();
        }

        menues.InitXml(_menu, Common.ConstructPath("menu_bar.xml"), "NewsTEXT 1 row");
        this.m_articlemenu = this.common.FindTextField("News 1 row - 55 SymbPerRow - 31 pixel");

        MENUsimplebutton_field b = this.common.FindSimpleButton("BUTTON - OK");

        if (this.m_singlepaper) {
            ;
        }

        menues.SetScriptOnControl(this.common.GetMenu(), b, this, "OnPaperClose", 4L);
        menues.SetMenuCallBack_ExitMenu(_menu, this.common.FindSimpleButton("BUTTON - EXIT").nativePointer, 4L);
    }

    void FillPaper(BarEntry entry) {
        this.m_papermenu = this.common.FindTextField("News");
        menues.SetShowField(this.m_papermenu.nativePointer, true);
        menues.SetShowField(this.m_articlemenu.nativePointer, true);
        this.texture_name = ((entry.texture_name != null)
                             ? entry.texture_name
                             : "none");
        JavaEvents.SendEvent(62, 2, this);

        MENUText_field head = this.common.FindTextField("TEXT - HeadLine");

        head.text = entry.headline;

        MENUText_field text = this.common.FindTextField("TEXT - Body");

        text.text = MacroKit.Parse(entry.papertext, entry.keys);

        int htexth = menues.GetTextHeight(head.nativePointer, head.text);
        int hbl = menues.GetBaseLine(head.nativePointer);
        int htlh = menues.GetTextLineHeight(head.nativePointer);
        int headlines = Converts.HeightToLines(htexth, hbl, htlh);
        int textlines = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, text.text),
                            menues.GetBaseLine(text.nativePointer), menues.GetTextLineHeight(text.nativePointer));
        int headshift = (int) ((Math.min(headlines, 4) - this.lasthlines) * 26.5D);
        int textshift = (int) ((Math.min(textlines, 15) - this.lasttextlines) * 26.5D);

        this.lasthlines = Math.min(headlines, 4);
        this.lasttextlines = Math.min(textlines, 15);
        head.leny += headshift;
        head.poy -= headshift + textshift;
        text.leny += textshift;
        text.poy -= textshift;
        menues.UpdateField(head);
        menues.UpdateField(text);

        MENUText_field backshadow = this.common.FindTextField("BackPaperShadow");

        backshadow.poy -= headshift + textshift;
        backshadow.leny += headshift + textshift;
        menues.UpdateField(backshadow);

        MENUText_field back = this.common.FindTextField("BackPaper");

        back.poy -= headshift + textshift;
        back.leny += headshift + textshift;
        menues.UpdateField(back);

        MENUText_field dark1 = this.common.FindTextField("Border DARK 1");

        dark1.poy -= textshift;
        menues.UpdateField(dark1);

        MENUText_field dark2 = this.common.FindTextField("Border DARK 2");

        dark2.poy -= textshift;
        menues.UpdateField(dark2);

        if (textlines > 15) {
            if (this.scroller != null) {
                this.scroller.Deinit();
            }

            this.scroller = new TextScroller(this.common, this.common.FindScroller("Tableranger - Newspaper"),
                                             textlines, 15, menues.GetTextLineHeight(text.nativePointer),
                                             menues.GetBaseLine(text.nativePointer), false,
                                             "GROUP - Tableranger - Newspaper");
            this.scroller.AddTextControl(text);
            menues.SetShowField(menues.FindFieldInMenu(this.common.GetMenu(), "GROUP - Tableranger - Newspaper"), true);
        } else {
            if (this.scroller != null) {
                this.scroller.Deinit();
            }

            this.scroller = null;
            menues.SetShowField(menues.FindFieldInMenu(this.common.GetMenu(), "GROUP - Tableranger - Newspaper"),
                                false);
        }

        if (entry.type != 2) {
            return;
        }

        PaperIndex pindex = new PaperIndex(entry);

        for (int i = 0; i < this.m_photopapers.size(); ++i) {
            PaperPhoto p = (PaperPhoto) this.m_photopapers.get(i);

            if (!(p.index.equals(pindex))) {
                continue;
            }

            this.m_photopic = p.control;
            menues.SetShowField(this.m_photopic.nativePointer, true);
        }
    }

    void InitPaper() {
        menues.InitXml(this._menu, "..\\data\\config\\menu\\menu_bar.xml", "News");
    }

    void OnSingleExit(long _menu, MENUsimplebutton_field button) {
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    void actorDialogFinished() {
        manageMenuCamera_enter();
        menues.menu_set_polosy(this.common.s_menu, false);

        if (this.m_mainmenu != null) {
            menues.SetShowField(this.m_mainmenu.nativePointer, true);
        }

        Bar.getInstance().endDialog(this.currentdialog);
        this.slots.update();
    }

    void startDialog(BarActor dialog) {
        manageMenuCamera_exit();
        menues.menu_set_polosy(this._menu, true);

        if (this.m_mainmenu != null) {
            menues.SetShowField(this.m_mainmenu.nativePointer, false);
        }

        this.currentdialog = dialog.dialogname;
        dialog.params.play();
        Bar.getInstance().startDialog(this.currentdialog);
        Dialog.getDialog(this.currentdialog).start_bar(dialog.model, Crew.getIgrok().getModel(),
                         dialog.params.getIdentitie());
        event.eventObject(9850, this, "actorDialogFinished");
    }

    void pickUpPack(String mission_name) {
        MissionEventsMaker.pickupQuestItem(this.bar_name, mission_name);
        this.slots.update();
    }

    private void manageMenuCamera_exit() {
        if (DEBUG) {
            return;
        }

        if (null != this.currentmenucamera) {
            this.currentmenucamera.closeSelection();
            this.currentmenucamera = null;
        }
    }

    private void manageMenuCamera_enter() {
        if (DEBUG) {
            return;
        }

        this.currentmenucamera = new MenuCamera();
    }

    void OnPaperSelect(BarEntry entry) {
        manageMenuCamera_exit();
        this.currentselection = new SelectionPaper(this._menu, entry);
        entry.article.readArticle();
    }

    void OnPaperClose(long _menu, MENUsimplebutton_field button) {
        if (null != this.currentselection) {
            this.currentselection.closeSelection();
            this.currentselection = null;
        }

        manageMenuCamera_enter();
        menues.SetShowField(this.m_papermenu.nativePointer, false);
        menues.SetShowField(this.m_articlemenu.nativePointer, false);

        if (this.m_mainmenu != null) {
            menues.SetShowField(this.m_mainmenu.nativePointer, true);
        }

        if (this.m_photopic != null) {
            menues.SetShowField(this.m_photopic.nativePointer, false);
            this.m_photopic = null;
        }

        this.slots.update();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        this.slots.afterInit();
        menues.WindowSet_ShowCursor(_menu, NotifyInformation.notify_should_show_cursor);
        menues.SetStopWorld(_menu, NotifyInformation.notify_should_stop_world);
        menues.SetMenagPOLOSY(_menu, true);
        menues.setShowMenu(_menu, true);

        if (this.m_singlepaper) {
            FillPaper(this.simple_entry);
            this.simple_entry.article.readArticle();

            if (this.m_mainmenu != null) {
                menues.SetShowField(this.m_mainmenu.nativePointer, false);
            }

            return;
        }

        menues.SetShowField(this.m_articlemenu.nativePointer, false);

        MENUText_field t = this.common.FindTextField("News");

        menues.SetShowField(t.nativePointer, false);

        for (int i = 0; i < this.m_photopapers.size(); ++i) {
            PaperPhoto p = (PaperPhoto) this.m_photopapers.get(i);

            menues.SetShowField(p.control.nativePointer, false);
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
        if (Bar.getInstance() != null) {
            Bar.getInstance().leaveBar();
        }

        if (this.scroller != null) {
            this.scroller.Deinit();
        }

        menues.ExitBarMENU();
        manageMenuCamera_exit();
        gmenu = null;
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
        return "barMENU";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean AnyStartDialogOrExit() {
        if (gmenu == null) {
            return false;
        }

        if ((!(was_dialog)) && (gmenu.slots.anyDialogStart())) {
            was_dialog = true;
            dialog_ended = false;

            return true;
        }

        if (!(dialog_ended)) {
            return true;
        }

        menues.CallMenuCallBack_ExitMenu(gmenu._menu);

        return true;
    }

    /**
     * @return the xml
     */
    public static String getXml() {
        return XML;
    }

    /**
     * @return the maxLinesInTextBody
     */
    public static int getMaxLinesInTextBody() {
        return max_lines_in_text_body;
    }

    /**
     * @return the maxLinesInHeadBody
     */
    public static int getMaxLinesInHeadBody() {
        return max_lines_in_head_body;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static class BarActor {
        SODialogParams params;

        /** Field description */
        public String dialogname;

        /** Field description */
        public SCRuniperson model;

        /** Field description */
        public SCRtalkingperson person;

        /**
         * Constructs ...
         *
         */
        public BarActor() {
            this.dialogname = "";
            this.model = null;
            this.person = null;
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static class BarEntry {

        /** Field description */
        public String headline;

        /** Field description */
        public String papertext;

        /** Field description */
        public int paperindex;

        /** Field description */
        public int type;

        /** Field description */
        public KeyPair[] keys;

        /** Field description */
        public IcontaktCB winner;

        /** Field description */
        public IArticle article;

        /** Field description */
        public String texture_name;
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static class BarPack {

        /** Field description */
        public int type;

        /** Field description */
        public String mission_name;
    }


    class MenuCamera {
        long scene = 0L;

        MenuCamera() {
            this.scene = Bar.getInstance().playMenuCamera();
        }

        void closeSelection() {
            scenetrack.DeleteScene(this.scene);
        }
    }


    private class PaperIndex {

        /** Field description */
        public int index;

        /** Field description */
        public int type;

        /**
         * Constructs ...
         *
         *
         * @param paramBarEntry
         */
        public PaperIndex(BarMenu.BarEntry paramBarEntry) {
            this.index = paramBarEntry.paperindex;
            this.type = paramBarEntry.type;
        }

        /**
         * Constructs ...
         *
         *
         * @param paramInt1
         * @param paramInt2
         */
        public PaperIndex(int paramInt1, int paramInt2) {
            this.index = paramInt1;
            this.type = type;
        }

        /**
         * Method description
         *
         *
         * @param obj
         *
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            PaperIndex p = (PaperIndex) obj;

            return ((p.index == this.index) && (p.type == this.type));
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public int hashCode() {
            return (this.index << 4 | this.type);
        }
    }


    private static class PaperPhoto {
        BarMenu.PaperIndex index;
        MENUText_field control;

        PaperPhoto(BarMenu.PaperIndex index, MENUText_field control) {
            this.index = index;
            this.control = control;
        }
    }


    class SelectionPaper {
        long scene = 0L;
        long _menu;
        BarMenu.BarEntry entry;

        SelectionPaper(long paramLong, BarMenu.BarEntry paramBarEntry, BarEntry entry) {
            this._menu = paramLong;
            this.entry = entry;

            if (BarMenu.this.m_mainmenu != null) {
                menues.SetShowField(BarMenu.this.m_mainmenu.nativePointer, false);
            }

            if (null != BarMenu.this.animation) {
                this.scene = BarMenu.this.animation.playMoveNeswpaper();
                event.eventObject((int) this.scene, this, "act");
            } else {
                act();
            }
        }

        void act() {
            BarMenu.this.FillPaper(this.entry);
        }

        void closeSelection() {
            scenetrack.DeleteScene(this.scene);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
