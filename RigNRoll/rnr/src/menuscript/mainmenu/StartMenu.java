/*
 * @(#)StartMenu.java   13/08/26
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


package rnr.src.menuscript.mainmenu;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.FabricControlColor;
import rnr.src.menu.IRadioAccess;
import rnr.src.menu.IRadioChangeListener;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.KeyPair;
import rnr.src.menu.ListenerManager;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.RadioGroup;
import rnr.src.menu.SMenu;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.menuscript.IPoPUpMenuListener;
import rnr.src.menuscript.IQuitMenu;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrscenario.scenarioscript;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class StartMenu extends MainMenu implements menucreation, IQuitMenu {
    private static StartMenu currentmenu = null;
    private static final String TABS_PREFIX = "BUTTON TAB";
    private static final String BACKGROUND_GROUP_PERMANENT = "COMMON VIDEO";
    private static final String BACKGROUND_GROUP_FLOAT = "MAIN MENU - BACKGROUND";
    private static final String MAIN_GROUP = "MAIN BUTTONS";
    private static final String DEFAULTSCREEN_GROUP = "DEFAULT SCREEN";
    private static final int NOM_REPLAY = 4;
    private static final String BUTONPREFIX = "BUTTON ";
    private static final String TAB_BACK_NAME = "BACK";
    private static final String SLIDER_NAME = "SLIDEPOINTER";
    private static final String[] PROFILE_NAMES = { "Info - PROFILE - VALUE", "Info - PROFILE - PROFILE",
            "Info - SINGLE PLAYER - PROFILE", "Info - QUICK RACE - PROFILE", "Info - SETTINGS - PROFILE" };
    private static final String PROFILE_NAME_KEY = "PROFILE";
    private static final String EXITMETHOD = "OnExit";
    private static final int NUM_TABS = 6;
    private static LastMenuState lastMenuState = null;
    private static StartMenu pr_Menu = null;

    /** Field description */
    public double velocitylimit = 1000.0D;

    /** Field description */
    public double fastveldump = 4000.0D;

    /** Field description */
    public double accelerationlimit = 1000.0D;

    /** Field description */
    public double accelerationcoef = 100.0D;

    /** Field description */
    public int slowlimit = 3;

    /** Field description */
    public int timelimit = 100;

    /** Field description */
    public int timelimit0 = 50;

    /** Field description */
    public int timelimitlimit = 500;

    /** Field description */
    public double timesafecoef = 1.5D;

    /** Field description */
    public double timediffcoef = 0.5D;

    /** Field description */
    public int tab_backminsize = 80;
    private final boolean needed_default_tab = false;
    private final int EXIT_BUTTON_NOM = 5;
    private final long[][] tabs = new long[6][];
    private long[] background_float = null;
    private long[] screen_float = null;
    private long[] buttonsgroup = null;
    private final long[] buttons = new long[6];
    private final int[] buttonsY = new int[6];
    private long tab_back = 0L;
    MENUText_field back = null;
    private int tab_backmaxsize = 0;
    private long slider_back = 0L;
    private long[] profile_names = null;
    private String[] profile_names_sources = null;
    private RadioGroup rgroup = null;
    private SelectAnimation animation = null;
    private SliderAnimation slideranimation = null;
    private TabAnimations tabanimation = null;
    private final ArrayList<Panel> _Menus = new ArrayList();
    private final Panel replayPanel = null;
    private final int[] are_blind = { 2, 4 };
    private boolean b_selectingDeafult = false;
    private boolean b_fistSelection = false;
    private boolean b_switchingInsideSelection = false;
    private int lastState;

    /** Field description */
    public String inVersion;

    StartMenu() {
        super("menu_MAIN.xml");
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
    public static StartMenu getCurrentObject() {
        return currentmenu;
    }

    private void restoreBlindness() {
        if (this.buttons[4] != 0L) {
            menues.SetShowField(this.buttons[4], false);
        }

        if (!(scenarioscript.INSTANT_ORDER_ONLY)) {
            return;
        }

        for (int i = 0; i < this.are_blind.length; ++i) {
            CA.freezControl(this.buttons[this.are_blind[i]]);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static long create() {
        return menues.createSimpleMenu(new StartMenu(), 1000000000.0D, "", 1600, 1200, 1600, 1200, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     */
    public static void autotest_create() {
        menues.createSimpleMenu(new StartMenu(), 5.0D, "", 1600, 1200, 1600, 1200, 0, 0,
                                "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    private void resolve_controls() {
        for (int tabgroups = 0; tabgroups < 6; ++tabgroups) {
            this.buttons[tabgroups] = findField("BUTTON " + (tabgroups + 1));
        }

        this.tab_back = findField("BACK");
        this.slider_back = findField("SLIDEPOINTER");
    }

    private void initRadioGroups() {
        MENUbutton_field[] buttons_fields = new MENUbutton_field[this.buttons.length];

        for (int i = 0; i < this.buttons.length; ++i) {
            buttons_fields[i] = menues.ConvertButton(this.buttons[i]);
            buttons_fields[i].userid = i;
            this.buttonsY[i] = buttons_fields[i].poy;

            if (i != 0) {
                this.buttonsY[i] -= this.buttonsY[0];
            }

            menues.UpdateField(buttons_fields[i]);
        }

        if (this.buttonsY.length != 0) {
            this.buttonsY[0] = 0;
        }

        this.rgroup = new RadioGroup(this._menu, buttons_fields);
        this.rgroup.addAccessListener(new RadioButtonAccess());
        this.rgroup.addListener(this.animation);
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
        pr_Menu = this;

        if (null != lastMenuState) {
            lastMenuState.restore(this);
        }

        menues.SetMenagPOLOSY(_menu, false);
        currentmenu = this;
        this._menu = _menu;
        loadGroup("COMMON VIDEO");
        this.background_float = loadGroup("MAIN MENU - BACKGROUND");
        this.screen_float = loadGroup("DEFAULT SCREEN");
        this.buttonsgroup = loadGroup("MAIN BUTTONS");

        for (int tabgroups = 0; tabgroups < 6; ++tabgroups) {
            this.tabs[tabgroups] = loadGroup("BUTTON TAB" + (tabgroups + 1));
        }

        this._Menus.add(new SinglePlayer(this));
        this._Menus.add(new Settings(this));
        this._Menus.add(new QuitMenu(this));
        this._Menus.add(new Profile(this));
        this._Menus.add(new QuickRace(this));
    }

    private void initanimations() {
        this.back = menues.ConvertTextFields(this.tab_back);
        this.tab_backmaxsize = this.back.lenx;
        this.animation = new SelectAnimation();
        this.slideranimation = null;
        this.tabanimation = new TabAnimations();
        menues.SetScriptOnControl(this._menu, menues.ConvertWindow(menues.GetBackMenu(this._menu)), this, "OnExit",
                                  17L);
    }

    /**
     * Method description
     *
     */
    public void update_profile_name() {
        if (null != this.profile_names) {
            String nm = new String();

            nm = ProfileManagement.getProfileManager().GetCurrentProfileName();

            String upper_name = nm.toUpperCase();
            KeyPair[] pair = new KeyPair[1];

            pair[0] = new KeyPair("PROFILE", upper_name);

            for (int i = 0; i < this.profile_names.length; ++i) {
                if (this.profile_names_sources[i] != null) {
                    String res = MacroKit.Parse(this.profile_names_sources[i], pair);

                    menues.SetFieldText(this.profile_names[i], res);
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.profile_names[i]));
                }
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
        ListenerManager.TriggerEvent(104);
        this.profile_names = new long[PROFILE_NAMES.length];
        this.profile_names_sources = new String[PROFILE_NAMES.length];

        for (int i = 0; i < PROFILE_NAMES.length; ++i) {
            this.profile_names[i] = menues.FindFieldInMenu(_menu, PROFILE_NAMES[i]);
            this.profile_names_sources[i] = menues.GetFieldText(this.profile_names[i]);
        }

        update_profile_name();
        resolve_controls();
        initanimations();
        initRadioGroups();

        Iterator iter = this._Menus.iterator();

        while (iter.hasNext()) {
            ((Panel) iter.next()).init();
        }

        initialScreen();
        restoreLastState();
        accesorries();
        restoreBlindness();

        long version = menues.FindFieldInMenu(_menu, "The Version");

        if (version != 0L) {
            MENUText_field text = (MENUText_field) menues.ConvertMenuFields(version);

            if ((text != null) && (text.text != null)) {
                JavaEvents.SendEvent(71, 20, this);

                if (this.inVersion != null) {
                    KeyPair[] pairs = { new KeyPair("RNR_VERSION", this.inVersion) };

                    text.text = MacroKit.Parse(text.text, pairs);
                    menues.UpdateField(text);
                }
            }
        }

        eng.console("bik play -a -l -s ..\\Data\\video\\mainmenuvideo.bik 0");
    }

    @SuppressWarnings("rawtypes")
    private void restoreLastState() {
        if (this.needed_default_tab) {
            this.b_selectingDeafult = true;
            this.rgroup.select(this.lastState);
            this.b_selectingDeafult = false;

            for (int control = 0; control < this.tabs[this.lastState].length; ++control) {
                menues.SetShowField(this.tabs[this.lastState][control], true);
            }

            CA.showControl(this.slider_back);
            CA.showControl(this.tab_back);

            Set entry = this.panelDialogStates.entrySet();
            Iterator iter = entry.iterator();

            while (iter.hasNext()) {
                Map.Entry entr = (Map.Entry) iter.next();
                String name = (String) entr.getKey();
                Iterator menuiter = this._Menus.iterator();

                while (menuiter.hasNext()) {
                    Panel pan = (Panel) menuiter.next();

                    if (name.compareTo(pan.getName()) == 0) {
                        pan.makeLastState((String) entr.getValue());
                    }
                }
            }

            Iterator menuiter = this._Menus.iterator();

            while (menuiter.hasNext()) {
                ((Panel) menuiter.next()).restoreLastState();
            }
        }
    }

    private void accesorries() {
        menues.setShowMenu(this._menu, true);
        menues.WindowSet_ShowCursor(this._menu, true);
        menues.SetStopWorld(this._menu, true);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {
        ListenerManager.TriggerEvent(105);
        TopWindow.UnRegister(this);
        pr_Menu = null;
        currentmenu = null;

        Iterator iter = this._Menus.iterator();

        while (iter.hasNext()) {
            ((Panel) iter.next()).exitMenu();
        }

        skippAniations();

        if (!(eng.noNative)) {
            event.Setevent(8001);
        }

        eng.console("bik stop");
        JavaEvents.SendEvent(3, 0, this);
        skippAniations();
        menues.StopScriptAnimation(7L);
        menues.StopScriptAnimation(6L);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "mainMENU";
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param window
     */
    public void OnExit(long _menu, SMenu window) {
        this.rgroup.select(this.EXIT_BUTTON_NOM);
    }

    private void initialScreen() {
        for (int i = 0; i < 6; ++i) {
            for (int control = 0; control < this.tabs[i].length; ++control) {
                menues.SetShowField(this.tabs[i][control], false);
            }
        }

        CA.hideControl(this.slider_back);
        CA.hideControl(this.tab_back);
        this.b_fistSelection = true;
    }

    @Override
    void resetToDefaulScreen() {
        freezeOnDialogEnd();
        this.b_switchingInsideSelection = true;
        this.tabanimation.turnoff();
        this.b_fistSelection = true;
        this.b_switchingInsideSelection = false;
    }

    private SliderAnimation getSlider() {
        if (null == this.slideranimation) {
            this.slideranimation = new SliderAnimation();
        }

        return this.slideranimation;
    }

    private void moveSliderToButton(int button_nom) {
        if (button_nom >= 6) {
            eng.err("Bad moveSliderToButton");

            return;
        }

        getSlider().setReachPoint(this.buttonsY[button_nom]);
    }

    private void skippAniations() {
        menues.StopScriptAnimation(2L);
        menues.StopScriptAnimation(1L);
        menues.StopScriptAnimation(3L);
        menues.StopScriptAnimation(5L);
        menues.StopScriptAnimation(4L);
    }

    private void skippFadeAniations() {
        menues.StopScriptAnimation(2L);
        menues.StopScriptAnimation(1L);
        menues.StopScriptAnimation(5L);
        menues.StopScriptAnimation(4L);
    }

    void unfreezeOnDialogEnd() {
        this.tabanimation.restoreTabs();

        for (int i = 0; i < this.background_float.length; ++i) {
            CA.unfreezControl(this.background_float[i]);
        }

        for (int i = 0; i < this.buttons.length; ++i) {
            CA.unfreezControl(this.buttons[i]);
        }

        restoreBlindness();
    }

    void freezeOnDialogEnd() {
        this.tabanimation.hideTabs();

        for (int i = 0; i < this.background_float.length; ++i) {
            CA.freezControl(this.background_float[i]);
        }

        for (int i = 0; i < this.buttons.length; ++i) {
            CA.freezControl(this.buttons[i]);
        }

        restoreBlindness();
    }

    /**
     * Method description
     *
     *
     * @param cb
     */
    @Override
    public void OnDialogOpen(IFadeOutFadeIn cb) {
        freezeOnDialogEnd();
        new FadeOutHead(this.tabanimation.getCurrent(), new OnDialogActions(cb));
    }

    /**
     * Method description
     *
     */
    @Override
    public void OnDialogOpenImmediate() {
        unfreezeOnDialogEnd();
        CA.showControl(this.slider_back);
        CA.showControl(this.tab_back);

        int group = this.tabanimation.getCurrent();

        getSlider().setinPosition(this.buttonsY[group]);

        int res_aloha = 0;

        for (int i = 0; i < this.tabs[group].length; ++i) {
            FabricControlColor.setControlAlfa(this.tabs[group][i], res_aloha);
        }

        FabricControlColor.setControlAlfa(this.tab_back, res_aloha);
        FabricControlColor.setControlAlfa(this.slider_back, res_aloha);

        for (int i = 0; i < this.background_float.length; ++i) {
            FabricControlColor.setControlAlfa(this.background_float[i], res_aloha);
        }

        for (int i = 0; i < this.buttonsgroup.length; ++i) {
            FabricControlColor.setControlAlfa(this.buttonsgroup[i], res_aloha);
        }

        for (int i = 0; i < this.screen_float.length; ++i) {
            FabricControlColor.setControlAlfa(this.screen_float[i], res_aloha);
        }
    }

    /**
     * Method description
     *
     *
     * @param cb
     */
    @Override
    public void OnDialogClose(IFadeOutFadeIn cb) {
        new FadeInHead(this.tabanimation.getCurrent(), new OnDialogActions(cb));
    }

    /**
     * Method description
     *
     */
    public static void menuNeedRestoreLastState() {
        if (null != pr_Menu) {
            lastMenuState = new LastMenuState(pr_Menu.panelDialogStates, pr_Menu.lastState, true);
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void quitMenu() {
        menues.CallMenuCallBack_ExitMenu(getCurrentObject()._menu);
    }

    /**
     * Method description
     *
     */
    public static void DebugQuitMenu() {
        menues.CallMenuCallBack_ExitMenu(getCurrentObject()._menu);
    }

    /**
     * Method description
     *
     */
    public void restoreProfileValuesToMenu() {
        for (Panel panel : this._Menus) {
            panel.restoreProfileValuesToPanel();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    class DefaultScreenAnimation implements IFadeOutFadeIn {
        private IFadeOutFadeIn cb = null;

        DefaultScreenAnimation() {}

        DefaultScreenAnimation(boolean paramBoolean) {
            StartMenu.this.freezeOnDialogEnd();
            this.cb = new StartMenu.DefaultScreenFade();
            menues.StopScriptAnimation((paramBoolean)
                                       ? 7L
                                       : 6L);
            menues.SetScriptObjectAnimation(0L, (paramBoolean)
                    ? 7L
                    : 6L, this, (paramBoolean)
                                ? "fadein"
                                : "fadeout");
        }

        void fadein(long c, double time) {
            int res_alpha = Animation.alpha_fadein(time, StartMenu.this.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();

            for (int i = 0; i < StartMenu.this.screen_float.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.screen_float[i], res_alpha);
            }

            if (end_animation) {
                if (null != this.cb) {
                    this.cb.fadeinEnded();
                }

                menues.StopScriptAnimation(7L);
            }
        }

        void fadeout(long c, double time) {
            int res_alpha = Animation.alpha_fadeout(time, StartMenu.this.PERIODFADEOUT);
            boolean end_animation = Animation.animtionEnded();

            for (int i = 0; i < StartMenu.this.screen_float.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.screen_float[i], res_alpha);
            }

            if (end_animation) {
                if (null != this.cb) {
                    this.cb.fadeoutEnded();
                }

                menues.StopScriptAnimation(6L);
            }
        }

        /**
         * Method description
         *
         */
        @Override
        public void fadeinEnded() {}

        /**
         * Method description
         *
         */
        @Override
        public void fadeoutEnded() {
            new DefaultScreenAnimation(true);
        }
    }


    class DefaultScreenFade implements IFadeOutFadeIn {

        /**
         * Method description
         *
         */
        @Override
        public void fadeinEnded() {
            CA.hideControl(StartMenu.this.slider_back);
            CA.hideControl(StartMenu.this.tab_back);
            StartMenu.TabAnimations.access$2000(StartMenu.this.tabanimation, StartMenu.this.tabanimation.getCurrent());
            StartMenu.this.unfreezeOnDialogEnd();
        }

        /**
         * Method description
         *
         */
        @Override
        public void fadeoutEnded() {
            StartMenu.this.unfreezeOnDialogEnd();
            CA.showControl(StartMenu.this.slider_back);
            CA.showControl(StartMenu.this.tab_back);
            StartMenu.this.tabanimation.appear();
        }
    }


    class FadeInCurrentPanel {
        private IFadeOutFadeIn cb = null;

        FadeInCurrentPanel(IFadeOutFadeIn paramIFadeOutFadeIn) {
            this.cb = paramIFadeOutFadeIn;
            menues.StopScriptAnimation(2L);
            menues.SetScriptObjectAnimation(0L, 2L, this, "animate");
        }

        void animate(long c, double time) {
            int group = StartMenu.this.tabanimation.getCurrent();
            int res_alpha = Animation.alpha_fadein(time, StartMenu.this.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();

            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_alpha);
            }

            FabricControlColor.setControlAlfa(StartMenu.this.tab_back, res_alpha);
            FabricControlColor.setControlAlfa(StartMenu.this.slider_back, res_alpha);

            if (end_animation) {
                this.cb.fadeinEnded();
                menues.StopScriptAnimation(2L);
            }
        }
    }


    class FadeInHead {
        private IFadeOutFadeIn cb = null;

        FadeInHead(int paramInt, IFadeOutFadeIn paramIFadeOutFadeIn) {
            this.cb = cb;
            menues.StopScriptAnimation(7L);
            menues.SetScriptObjectAnimation(paramInt, 7L, this, "animate");
        }

        void animate(long c, double time) {
            int group = (int) c;
            int res_aloha = Animation.alpha_fadein(time, StartMenu.this.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();

            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_aloha);
            }

            for (int i = 0; i < StartMenu.this.background_float.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.background_float[i], res_aloha);
            }

            for (int i = 0; i < StartMenu.this.buttonsgroup.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.buttonsgroup[i], res_aloha);
            }

            if (end_animation) {
                this.cb.fadeinEnded();
                menues.StopScriptAnimation(7L);
            }
        }
    }


    class FadeInPanel {
        private IFadeOutFadeIn cb = null;

        FadeInPanel(long paramLong, IFadeOutFadeIn paramIFadeOutFadeIn) {
            this.cb = cb;
            menues.StopScriptAnimation(2L);
            menues.SetScriptObjectAnimation(paramLong, 2L, this, "animate");
        }

        void animate(long c, double time) {
            int group = (int) c;
            int res_aloha = Animation.alpha_fadein(time, StartMenu.this.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();

            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_aloha);
            }

            if (end_animation) {
                this.cb.fadeinEnded();
                menues.StopScriptAnimation(2L);
            }
        }
    }


    class FadeInPanelEnd {
        FadeInPanelEnd(int group) {
            int res_aloha = 255;

            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_aloha);
            }
        }
    }


    class FadeOutCurrentPanel {
        private IFadeOutFadeIn cb = null;

        FadeOutCurrentPanel(IFadeOutFadeIn paramIFadeOutFadeIn) {
            this.cb = paramIFadeOutFadeIn;
            menues.StopScriptAnimation(1L);
            menues.SetScriptObjectAnimation(0L, 1L, this, "animate");
        }

        void animate(long c, double time) {
            int group = StartMenu.this.tabanimation.getCurrent();
            int res_alpha = Animation.alpha_fadeout(time, StartMenu.this.PERIODFADEOUT);
            boolean end_animation = Animation.animtionEnded();

            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_alpha);
            }

            FabricControlColor.setControlAlfa(StartMenu.this.tab_back, res_alpha);
            FabricControlColor.setControlAlfa(StartMenu.this.slider_back, res_alpha);

            if (end_animation) {
                this.cb.fadeoutEnded();
                menues.StopScriptAnimation(1L);
            }
        }
    }


    class FadeOutHead {
        private IFadeOutFadeIn cb = null;

        FadeOutHead(int paramInt, IFadeOutFadeIn paramIFadeOutFadeIn) {
            this.cb = cb;
            menues.StopScriptAnimation(6L);
            menues.SetScriptObjectAnimation(paramInt, 6L, this, "animate");
        }

        void animate(long c, double time) {
            int group = (int) c;
            int res_aloha = Animation.alpha_fadeout(time, StartMenu.this.PERIODFADEOUT);
            boolean end_animation = Animation.animtionEnded();

            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_aloha);
            }

            for (int i = 0; i < StartMenu.this.background_float.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.background_float[i], res_aloha);
            }

            for (int i = 0; i < StartMenu.this.buttonsgroup.length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.buttonsgroup[i], res_aloha);
            }

            if (end_animation) {
                this.cb.fadeoutEnded();
                menues.StopScriptAnimation(6L);
            }
        }
    }


    class FadeOutPanel {
        private IFadeOutFadeIn cb = null;

        FadeOutPanel(long paramLong, IFadeOutFadeIn paramIFadeOutFadeIn) {
            this.cb = cb;
            menues.StopScriptAnimation(1L);
            menues.SetScriptObjectAnimation(paramLong, 1L, this, "animate");
        }

        void animate(long c, double time) {
            int group = (int) c;
            int res_aloha = Animation.alpha_fadeout(time, StartMenu.this.PERIODFADEOUT);
            boolean end_animation = Animation.animtionEnded();

            for (int i = 0; i < StartMenu.this.tabs[group].length; ++i) {
                FabricControlColor.setControlAlfa(StartMenu.this.tabs[group][i], res_aloha);
            }

            if (end_animation) {
                this.cb.fadeoutEnded();
                menues.StopScriptAnimation(1L);
            }
        }
    }


    class FoldPanel {
        FoldPanel() {
            menues.StopScriptAnimation(4L);
            menues.SetScriptObjectAnimation(0L, 4L, this, "animate");
        }

        void animate(long c, double time) {
            double period = StartMenu.this.PERIODFOLD;
            double size = StartMenu.this.tab_backminsize
                          + (StartMenu.this.tab_backmaxsize - StartMenu.this.tab_backminsize) * (period - time)
                            / period;
            boolean end_animation = false;

            if (size <= StartMenu.this.tab_backminsize) {
                size = StartMenu.this.tab_backminsize;
                end_animation = true;
            }

            StartMenu.this.back.lenx = (int) size;
            menues.UpdateField(StartMenu.this.back);

            if (end_animation) {
                StartMenu.this.tabanimation.foldingEnded();
                menues.StopScriptAnimation(4L);
            }
        }
    }


    class InWarningEmptyList implements IPoPUpMenuListener {

        /**
         * Method description
         *
         */
        @Override
        public void onAgreeclose() {}

        /**
         * Method description
         *
         */
        @Override
        public void onClose() {}

        /**
         * Method description
         *
         */
        @Override
        public void onCancel() {}

        /**
         * Method description
         *
         */
        @Override
        public void onOpen() {
            StartMenu.this.tabanimation.selectTabOnAnimation(
                StartMenu.TabAnimations.access$1600(StartMenu.this.tabanimation));
            StartMenu.this.resetToDefaulScreen();
        }
    }


    static class LastMenuState {
        private boolean last_needed_default_tab = false;
        private final HashMap<String, String> last_panelDialogStates;
        private final int last_lastTab;

        private LastMenuState(HashMap<String, String> last_panelDialogStates, int last_lastTab,
                              boolean last_needed_default_tab) {
            this.last_panelDialogStates = last_panelDialogStates;
            this.last_lastTab = last_lastTab;
            this.last_needed_default_tab = last_needed_default_tab;
        }

        void restore(StartMenu menu) {
            StartMenu.access$2102(menu, this.last_needed_default_tab);
            StartMenu.access$1002(menu, this.last_lastTab);
            menu.panelDialogStates = this.last_panelDialogStates;
        }
    }


    class OnDialogActions implements IFadeOutFadeIn {
        IFadeOutFadeIn cb;

        OnDialogActions(IFadeOutFadeIn paramIFadeOutFadeIn) {
            this.cb = paramIFadeOutFadeIn;
        }

        /**
         * Method description
         *
         */
        @Override
        public void fadeinEnded() {
            if (null != this.cb) {
                this.cb.fadeinEnded();
            }

            StartMenu.this.unfreezeOnDialogEnd();
        }

        /**
         * Method description
         *
         */
        @Override
        public void fadeoutEnded() {
            if (null != this.cb) {
                this.cb.fadeoutEnded();
            }
        }
    }


    class RadioButtonAccess implements IRadioAccess {

        /**
         * Method description
         *
         *
         * @param button
         * @param state
         *
         * @return
         */
        @Override
        public boolean controlAccessed(MENUbutton_field button, int state) {
            return (state == 1);
        }
    }


    class SelectAnimation implements IRadioChangeListener {

        /**
         * Method description
         *
         *
         * @param button
         * @param cs
         */
        @Override
        public final void controlSelected(MENUbutton_field button, int cs) {
            if ((StartMenu.this.b_selectingDeafult) || (StartMenu.this.b_switchingInsideSelection)) {
                StartMenu.access$202(StartMenu.this, false);
                StartMenu.this.tabanimation.setupTab(button.userid);

                return;
            }

            if (StartMenu.this.b_fistSelection) {
                if (4 == button.userid) {
                    return;
                }

                StartMenu.access$202(StartMenu.this, false);
                StartMenu.this.tabanimation.setupTab(button.userid);
                new StartMenu.DefaultScreenAnimation(false);

                return;
            }

            if (4 == button.userid) {
                if (((Replay) StartMenu.this.replayPanel).isClipsListEmpty()) {
                    ((Replay) StartMenu.this.replayPanel).warnAboutEmptyList();

                    return;
                }

                ((Replay) StartMenu.this.replayPanel).updateWindowContext();
            }

            StartMenu.this.tabanimation.selectTabOnAnimation(button.userid);
            StartMenu.this.moveSliderToButton(button.userid);
        }
    }


    class SliderAnimation {
        private boolean f_parking = false;
        private double time_parking_end = 0.0D;
        private double time_parking_start = 0.0D;
        private int pos_parking_start = 0;
        private double vel_parking_start = 0.0D;
        private double accelparking = 0.0D;
        private MENUText_field slider_button = null;
        private int initialposition = 0;
        private int reachpoint = 0;
        private int currentpoint = 0;
        private double previoustime = 0.0D;
        private double dt = 0.0D;
        private double velocity = 0.0D;

        SliderAnimation() {
            this.slider_button = menues.ConvertTextFields(StartMenu.this.slider_back);
            this.initialposition = this.slider_button.poy;
            menues.SetScriptObjectAnimation(StartMenu.this.slider_back, 3L, this, "animate");
            this.currentpoint = this.reachpoint;
        }

        private void deltaTime(double time) {
            this.dt = (time - this.previoustime);
            this.previoustime = time;

            if (this.dt < 0.0D) {
                eng.err("reverce move of time");

                return;
            }
        }

        private void updateSlider() {
            this.slider_button.poy = (this.initialposition + this.currentpoint);
            menues.UpdateField(this.slider_button);
        }

        void setReachPoint(int point) {
            int diff = Math.abs(this.currentpoint - point);

            if (diff < StartMenu.this.timesafecoef * StartMenu.this.timelimit0) {
                StartMenu.this.timelimit = StartMenu.this.timelimit0;
            } else {
                StartMenu.this.timelimit = (StartMenu.this.timelimit0
                                            + (int) ((diff - (StartMenu.this.timesafecoef * StartMenu.this.timelimit0))
                                                * StartMenu.this.timediffcoef));
            }

            if (StartMenu.this.timelimit > StartMenu.this.timelimitlimit) {
                StartMenu.this.timelimit = StartMenu.this.timelimitlimit;
            }

            this.reachpoint = point;
            this.f_parking = false;
        }

        protected void setinPosition(int reachpoint) {
            this.currentpoint = reachpoint;
            this.reachpoint = reachpoint;
            updateSlider();
        }

        void animate(long button, double time) {
            deltaTime(time);

            if (this.currentpoint == this.reachpoint) {
                return;
            }

            if (this.f_parking) {
                parking(time);
                updateSlider();

                return;
            }

            long diff = this.reachpoint - this.currentpoint;

            if ((diff > 0L) && (diff < StartMenu.this.slowlimit)) {
                this.currentpoint += 1;
                updateSlider();

                return;
            }

            if ((diff < 0L) && (diff > -StartMenu.this.slowlimit)) {
                this.currentpoint -= 1;
                updateSlider();

                return;
            }

            if ((diff < 0L) && (diff > -StartMenu.this.timelimit)) {
                this.f_parking = true;
                this.accelparking = (this.velocity * this.velocity / 2.0D * diff);

                if (this.velocity < 0.0D) {
                    this.accelparking *= -1.0D;
                } else {
                    this.accelparking *= -1.0D;
                    this.velocity *= -1.0D;
                }

                this.time_parking_start = (time - this.dt);
                this.time_parking_end = (time + Math.abs(this.velocity / this.accelparking));
                this.pos_parking_start = this.currentpoint;
                this.vel_parking_start = this.velocity;
                parking(time);
                updateSlider();

                return;
            }

            if ((diff > 0L) && (diff < StartMenu.this.timelimit)) {
                this.f_parking = true;
                this.accelparking = (this.velocity * this.velocity / 2.0D * diff);

                if (this.velocity > 0.0D) {
                    this.accelparking *= -1.0D;
                } else {
                    this.accelparking *= -1.0D;
                    this.velocity *= -1.0D;
                }

                this.time_parking_start = (time - this.dt);
                this.time_parking_end = (time + Math.abs(this.velocity / this.accelparking));
                this.pos_parking_start = this.currentpoint;
                this.vel_parking_start = this.velocity;
                parking(time);
                updateSlider();

                return;
            }

            double accel = StartMenu.this.accelerationcoef * diff;

            if ((accel > 0.0D) && (accel > StartMenu.this.accelerationlimit)) {
                accel = StartMenu.this.accelerationlimit;
            } else if ((accel < 0.0D) && (accel < -StartMenu.this.accelerationlimit)) {
                accel = -StartMenu.this.accelerationlimit;
            }

            if (accel * this.velocity < 0.0D) {
                if (this.velocity > 0.0D) {
                    this.velocity -= StartMenu.this.fastveldump * this.dt;
                } else {
                    this.velocity += StartMenu.this.fastveldump * this.dt;
                }
            }

            this.velocity += accel * this.dt;

            if ((this.velocity > 0.0D) && (this.velocity > StartMenu.this.velocitylimit)) {
                this.velocity = StartMenu.this.velocitylimit;
            } else if ((this.velocity < 0.0D) && (this.velocity < -StartMenu.this.velocitylimit)) {
                this.velocity = (-StartMenu.this.velocitylimit);
            }

            this.currentpoint += (int) (this.velocity * this.dt);
            updateSlider();
        }

        private void parking(double time) {
            if (time >= this.time_parking_end) {
                this.currentpoint = this.reachpoint;
                this.velocity = 0.0D;
                this.f_parking = false;

                return;
            }

            double T = time - this.time_parking_start;

            this.currentpoint = (int) (this.pos_parking_start
                                       + (this.vel_parking_start + this.accelparking * T * 0.5D) * T);
            this.velocity = (this.vel_parking_start + this.accelparking * T);

            if (this.currentpoint == this.reachpoint) {
                this.velocity = 0.0D;
                this.f_parking = false;
            }
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ
     */
    public class TabAnimations implements IFadeOutFadeIn {
        private int currenttab;
        private int pendedtab;
        private boolean animating;

        /**
         * Constructs ...
         *
         */
        public TabAnimations() {
            this.currenttab = StartMenu.this.lastState;
        }

        /**
         * Method description
         *
         */
        public void appear() {
            this.pendedtab = this.currenttab;
            new StartMenu.UnfoldPanelEnd();
            turnonTab(this.pendedtab);
            freezeTab(this.pendedtab);
            new StartMenu.FadeInCurrentPanel(this);
            StartMenu.this.getSlider().setinPosition(StartMenu.this.buttonsY[this.pendedtab]);
        }

        /**
         * Method description
         *
         */
        public void turnoff() {
            StartMenu.this.rgroup.deselectall();
            new StartMenu.FadeOutCurrentPanel(new StartMenu.DefaultScreenAnimation());
        }

        protected void setupTab(int tab) {
            this.currenttab = tab;
        }

        /**
         * Method description
         *
         *
         * @param tab
         */
        public void selectTabOnAnimation(int tab) {
            if (this.animating) {
                makeUnfoldOnly(tab);
            } else {
                if (tab == this.currenttab) {
                    return;
                }

                makeWholeAnimation(tab);
            }
        }

        /**
         * Method description
         *
         */
        public void foldingEnded() {
            new StartMenu.UnfoldPanel();
        }

        /**
         * Method description
         *
         */
        public void unfoldingEnded() {
            turnonTab(this.pendedtab);
            freezeTab(this.pendedtab);
            new StartMenu.FadeInPanel(this.pendedtab, this);
        }

        /**
         * Method description
         *
         */
        @Override
        public void fadeinEnded() {
            this.currenttab = this.pendedtab;
            unfreezeTab(this.currenttab);
            StartMenu.this.restoreBlindness();
            this.animating = false;
            StartMenu.access$1002(StartMenu.this, this.currenttab);
        }

        /**
         * Method description
         *
         */
        @Override
        public void fadeoutEnded() {
            turnoffTab(this.currenttab);
            new StartMenu.FoldPanel();
        }

        private void makeUnfoldOnly(int tab) {
            if (tab == this.pendedtab) {
                freezeTab(this.currenttab);
                turnoffTab(this.currenttab);
                this.currenttab = this.pendedtab;
                unfreezeTab(this.currenttab);
                turnonTab(this.currenttab);
                new StartMenu.FadeInPanelEnd(this.currenttab);
                StartMenu.this.restoreBlindness();
                new StartMenu.UnfoldPanelEnd();
                this.animating = false;
                StartMenu.this.skippFadeAniations();

                return;
            }

            StartMenu.this.skippFadeAniations();
            turnoffTab(this.pendedtab);

            if (this.currenttab != this.pendedtab) {
                turnoffTab(this.currenttab);
            }

            freezeTab(this.pendedtab);
            this.pendedtab = tab;
            this.animating = true;
            new StartMenu.UnfoldPanel();
        }

        private void makeWholeAnimation(int tab) {
            this.animating = true;
            this.pendedtab = tab;
            freezeTab(this.currenttab);
            freezeTab(this.pendedtab);
            new StartMenu.FadeOutPanel(this.currenttab, this);
        }

        private void turnoffTab(int tab) {
            if (tab >= 6) {
                eng.err("Bad tab selection. turnoffTab");

                return;
            }

            for (int control = 0; control < StartMenu.this.tabs[tab].length; ++control) {
                CA.hideControl(StartMenu.this.tabs[tab][control]);
            }
        }

        private void turnonTab(int tab) {
            if (tab >= 6) {
                eng.err("Bad tab selection. turnonTab");

                return;
            }

            for (int control = 0; control < StartMenu.this.tabs[tab].length; ++control) {
                CA.showControl(StartMenu.this.tabs[tab][control]);
            }
        }

        private void freezeTab(int tab) {
            if (tab >= 6) {
                eng.err("Bad tab selection. freezeTab");

                return;
            }

            for (int control = 0; control < StartMenu.this.tabs[tab].length; ++control) {
                CA.freezControl(StartMenu.this.tabs[tab][control]);
            }
        }

        private void unfreezeTab(int tab) {
            if (tab >= 6) {
                eng.err("Bad tab selection. unfreezeTab");

                return;
            }

            for (int control = 0; control < StartMenu.this.tabs[tab].length; ++control) {
                CA.unfreezControl(StartMenu.this.tabs[tab][control]);
            }
        }

        int getCurrent() {
            return this.currenttab;
        }

        void hideTabs() {
            freezeTab(this.currenttab);
        }

        void restoreTabs() {
            unfreezeTab(this.currenttab);
        }
    }


    class UnfoldPanel {
        UnfoldPanel() {
            menues.StopScriptAnimation(5L);
            menues.SetScriptObjectAnimation(0L, 5L, this, "animate");
        }

        void animate(long c, double time) {
            double period = StartMenu.this.PERIODUNFOLD;
            double size = StartMenu.this.tab_backminsize
                          + (StartMenu.this.tab_backmaxsize - StartMenu.this.tab_backminsize) * time / period;
            boolean end_animation = false;

            if (size >= StartMenu.this.tab_backmaxsize) {
                size = StartMenu.this.tab_backmaxsize;
                end_animation = true;
            }

            MENUText_field back = menues.ConvertTextFields(StartMenu.this.tab_back);

            back.lenx = (int) size;
            menues.UpdateField(back);

            if (end_animation) {
                StartMenu.this.tabanimation.unfoldingEnded();
                menues.StopScriptAnimation(5L);
            }
        }
    }


    class UnfoldPanelEnd {
        UnfoldPanelEnd() {
            MENUText_field back = menues.ConvertTextFields(StartMenu.this.tab_back);

            back.lenx = StartMenu.this.tab_backmaxsize;
            menues.UpdateField(back);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
