/*
 * @(#)OfficeMenu.java   13/08/26
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


package rnr.src.menuscript.office;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.BalanceUpdater;
import rnr.src.menu.Common;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.KeyPair;
import rnr.src.menu.ListenerManager;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.MenuControls;
import rnr.src.menu.SMenu;
import rnr.src.menu.TextScroller;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.menu.xmlcontrols;
import rnr.src.menu.xmlcontrols.MENUCustomStuff;
import rnr.src.menuscript.Converts;
import rnr.src.menuscript.IPoPUpMenuListener;
import rnr.src.menuscript.IUpdateListener;
import rnr.src.menuscript.IYesNoCancelMenuListener;
import rnr.src.menuscript.PoPUpMenu;
import rnr.src.menuscript.YesNoCancelMenu;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.Log;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.loc;
import rnr.src.rnrcore.vectorJ;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class OfficeMenu implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String XML_WARNING_MONEY = "..\\data\\config\\menu\\menu_office.xml";
    private static final String XML_WARNING_TURN = "..\\data\\config\\menu\\menu_office.xml";
    private static final String XML_WARNING_DUPT = "..\\data\\config\\menu\\menu_unsettled_debt.xml";
    private static final String XML_WARNING_APPLY = "..\\data\\config\\menu\\menu_office.xml";
    private static final String XML_WARNING_CARS = "..\\data\\config\\menu\\menu_office.xml";
    private static final String XML_WARNING_HAS_DEPT = "..\\data\\config\\menu\\menu_unsettled_debt.xml";
    private static final String CONTROLS_MAIN = "OFFICE";
    private static final String CONTROLS_WARNING_MONEY = "MESSAGE - Not enough money";
    private static final String CONTROLS_WARNING_TURNS = "MESSAGE - Not enough turns";
    private static final String CONTROLS_WARNING_DUBT = "Menu Debt";
    private static final String CONTROLS_WARNING_APPLY = "MESSAGE - Apply changes";
    private static final String CONTROLS_WARNING_CARS = "MESSAGE - Not enough vehicles";
    private static final String CONTROLS_WARNING_HASDEPT = "Message Debt";

    /** Field description */
    public static final int ERROR_CODE_NON = 0;

    /** Field description */
    public static final int ERROR_CODE_NOTENOUGHCARS = 1;

    /** Field description */
    public static final int ERROR_CODE_NOTENOUGHMONEY = 2;
    private static final String[] TAB_NAMES = {
        "Tab2 - Manage Fleet", "Tab2 - Manage Drivers", "Tab2 - Hire/Fire Driver", "Tab1 - Manage Branches",
        "Tab1 - Global statistics", "Tab1 - Company statistics"
    };
    private static final String TAB_METHOD = "onTab";

    /** Field description */
    public static final int TAB_MANAGEFLEET = 0;

    /** Field description */
    public static final int TAB_MANAGEDRIVERS = 1;

    /** Field description */
    public static final int TAB_HIREDRIVER = 2;

    /** Field description */
    public static final int TAB_BRANCHES = 3;

    /** Field description */
    public static final int TAB_GLOBAL_STATICSTICS = 4;

    /** Field description */
    public static final int TAB_COMPNANY_STATICSTICS = 5;

    /** Field description */
    public static final int INVALIDE_TAB = -1;
    private static final String[] ACTION_BUTTONS = { "BUTTON - EXIT", "BUTTON - DISCARD ALL", "BUTTON - APPLY ALL" };
    private static final String[] ACTION_FUNKS = { "onExit", "onDiscard", "onApply" };
    private static final String[] TOTALS_FIELDS = { "MY BALANCE - VALUE", "MY RANK - VALUE" };
    private static final int BALLANCE_TOTAL = 0;
    private static final int RANK_TOTAL = 1;
    private static final String MACRO_VALUE = "VALUE";
    static final int ANIMATION_MANAGEFLEET = 1;
    static final int ANIMATION_HIREDRIVERS = 2;
    private static boolean isMenuCreated = false;
    private static boolean isMenuAskedToCreate = false;
    private static boolean isShowMenuImmediately = false;
    MenuControls allmenu = null;
    private PoPUpMenu warning_has_dept = null;
    private PoPUpMenu warning_not_enough_money = null;
    private PoPUpMenu warning_not_enough_turns = null;
    private PoPUpMenu warning_not_enough_cars = null;
    private PoPUpMenu warning_unsettled_dubt = null;
    private YesNoCancelMenu warning_apply_changes = null;
    ArrayList<ApplicationTab> tabs = new ArrayList();
    private int current_tab = 0;
    private long _menu = 0L;
    private final long[] totals = new long[TOTALS_FIELDS.length];
    private final String[] totals_str = new String[TOTALS_FIELDS.length];
    private ApplyResult APPLYRESULT = null;
    private DiscardResult DISCARDRESULT = null;
    private ExitResult EXITRESULT = null;
    private final CheckState CHECKSTATE = new CheckState();
    private String not_enought_turns_text = "";
    private String unsettled_dubt_text = "";
    private PoPUpMenu show_help = null;
    private long help_text_title = 0L;
    private long help_text = 0L;
    private long help_text_scroller = 0L;
    TextScroller scroller = null;
    String info_text = null;
    String tool_tip_text = null;
    Common common;

    /** Field description */
    public vectorJ out_position;

    /** Field description */
    public String inVersion;

    /** Field description */
    public String inOfficeName;

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
     */
    public static void showMenu() {
        if (isMenuCreated) {
            menues.showMenu(8000);
        } else if (isMenuAskedToCreate) {
            isShowMenuImmediately = true;
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        JavaEvents.SendEvent(71, 17, this);
        this.common = new Common(_menu);
        isMenuAskedToCreate = false;
        this._menu = _menu;
        this.allmenu = new MenuControls(_menu, "..\\data\\config\\menu\\menu_office.xml", "OFFICE");

        ShowWarning sw = new ShowWarning();
        UpdateTotals uTotals = new UpdateTotals();

        for (int i = 0; i < TAB_NAMES.length; ++i) {
            long tab = menues.FindFieldInMenu(_menu, TAB_NAMES[i]);
            xmlcontrols.MENUCustomStuff obj_tab = (xmlcontrols.MENUCustomStuff) menues.ConvertMenuFields(tab);

            obj_tab.userid = i;
            menues.SetScriptOnControl(_menu, obj_tab, this, "onTab", 10L);

            switch (i) {
             case 1 :
                 this.tabs.add(new ManageDrivers(_menu, this));

                 break;

             case 0 :
                 this.tabs.add(new ManageFleet(_menu, this));

                 break;

             case 2 :
                 this.tabs.add(new HireDrivers(_menu, this));

                 break;

             case 3 :
                 this.tabs.add(new Branches(_menu, this));

                 break;

             case 5 :
                 this.tabs.add(new CompanyStatistics(_menu, this));

                 break;

             case 4 :
                 this.tabs.add(new GlobalStatistics(_menu, this));
            }

            if (!(this.tabs.isEmpty())) {
                this.tabs.get(i).addListener(sw);
                this.tabs.get(i).addListenerUpdate(uTotals);
                this.tabs.get(i).addListenerDirty(new DirtyListener(i));
            }

            menues.UpdateMenuField(obj_tab);
        }

        for (int i = 0; i < ACTION_BUTTONS.length; ++i) {
            long button = menues.FindFieldInMenu(_menu, ACTION_BUTTONS[i]);

            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button), this, ACTION_FUNKS[i], 4L);
        }

        for (int i = 0; i < TOTALS_FIELDS.length; ++i) {
            this.totals[i] = menues.FindFieldInMenu(_menu, TOTALS_FIELDS[i]);
            this.totals_str[i] = menues.GetFieldText(this.totals[i]);
        }

        this.warning_has_dept = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_unsettled_debt.xml", "Message Debt");
        this.warning_not_enough_money = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_office.xml",
                "MESSAGE - Not enough money", "MESSAGE - Not enough money");
        this.warning_not_enough_turns = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_office.xml",
                "MESSAGE - Not enough turns", "MESSAGE - Not enough turns");
        this.warning_apply_changes = new YesNoCancelMenu(_menu, "..\\data\\config\\menu\\menu_office.xml",
                "MESSAGE - Apply changes", "MESSAGE - Apply changes");
        this.warning_not_enough_cars = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_office.xml",
                "MESSAGE - Not enough vehicles", "MESSAGE - Not enough vehicles");
        this.warning_unsettled_dubt = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_unsettled_debt.xml",
                "Menu Debt", "Menu Debt", false);
        this.show_help = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_office.xml", "TOOLTIP - FREIGHT MANAGER",
                                       "TOOLTIP - FREIGHT MANAGER");
        this.help_text_title = this.show_help.getField("CALL OFFICE HELP - TITLE");
        this.help_text = this.show_help.getField("CALL OFFICE HELP - TEXT");
        this.help_text_scroller = this.show_help.getField("CALL OFFICE HELP - Tableranger");
        this.warning_not_enough_turns.addListener(new NotEnough_Listener());
        this.warning_not_enough_money.addListener(new NotEnough_Listener());
        this.warning_not_enough_cars.addListener(new NotEnough_Listener());
        this.warning_unsettled_dubt.addListener(new MenuDeptListener());
        this.warning_apply_changes.addListener(new YesNoCancelListener());
        this.not_enought_turns_text = menues.GetFieldText(this.warning_not_enough_turns.getField("TEXT1"));
        this.unsettled_dubt_text = menues.GetFieldText(this.warning_has_dept.getField("Text 1"));
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        isMenuCreated = true;
        BalanceUpdater.AddBalanceControl(this.totals[0]);

        for (ApplicationTab tab : this.tabs) {
            tab.afterInit();
        }

        this.warning_has_dept.afterInit();
        this.warning_not_enough_turns.afterInit();
        this.warning_not_enough_money.afterInit();
        this.warning_not_enough_cars.afterInit();
        this.warning_unsettled_dubt.afterInit();
        this.warning_apply_changes.afterInit();
        this.show_help.afterInit();
        menues.WindowSet_ShowCursor(_menu, true);
        menues.SetStopWorld(_menu, true);
        menues.SetMenagPOLOSY(_menu, true);
        menues.SetFieldState(menues.FindFieldInMenu(_menu, TAB_NAMES[5]), 1);
        makeEnterOfficeMenu();

        if (eng.noNative) {
            menues.showMenu(8000);
        }

        if (isShowMenuImmediately) {
            isShowMenuImmediately = false;
            menues.showMenu(8000);
        }

        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.GetBackMenu(_menu)), this, "OnExit", 17L);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {
        isMenuAskedToCreate = false;
        isMenuCreated = false;
        BalanceUpdater.RemoveBalanceControl(this.totals[0]);

        if (this.scroller != null) {
            this.scroller.Deinit();
        }

        for (ApplicationTab tab : this.tabs) {
            tab.deinit();
        }
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
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab2 - Manage Fleet";
            help_loc =
                "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Manage Fleet";
        } else if (tab_id == 1) {
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab2 - Manage Drivers";
            help_loc =
                "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Manage Drivers";
        } else if (tab_id == 2) {
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab2 - Hire/Fire Driver";
            help_loc =
                "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Hire/Fire Driver";
        } else if (tab_id == 3) {
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab1 - Manage Branches";
            help_loc =
                "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Manage Branches";
        } else if (tab_id == 4) {
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab1 - Global statistics";
            help_loc =
                "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Global statistics";
        } else if (tab_id == 5) {
            tite_loc = "menu_office.xml\\OFFICE\\TAB\\Tab1 - Company statistics";
            help_loc =
                "menu_office.xml\\TOOLTIP - FREIGHT MANAGER\\TOOLTIP - FREIGHT MANAGER\\CALL OFFICE HELP - Company statistics";
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

        if (!(eng.noNative)) {
            long info = this.show_help.getField("THE CITY OFFICE TITLE - TEXT");

            if (info != 0L) {
                MENUText_field text = (MENUText_field) menues.ConvertMenuFields(info);

                if ((text != null) && (text.text != null)) {
                    if (this.info_text == null) {
                        this.info_text = text.text;
                    }

                    JavaEvents.SendEvent(71, 2, this);

                    KeyPair[] pairs = { new KeyPair("RNR_VERSION", this.inVersion),
                                        new KeyPair("CITY", this.inOfficeName) };
                    String semi = MacroKit.Parse(this.info_text, pairs);
                    CoreTime time = new CoreTime();

                    text.text = Converts.ConvertDateAbsolute(semi, time.gMonth(), time.gDate(), time.gYear(),
                            time.gHour(), time.gMinute());
                    menues.UpdateField(text);
                }
            }
        }

        this.show_help.show();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "officeMENU";
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onTab(long _menu, xmlcontrols.MENUCustomStuff button) {
        if ((-1 == button.userid) || (this.tabs.size() - 1 < button.userid)) {
            return;
        }

        this.current_tab = button.userid;
        this.tabs.get(button.userid).update();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onExit(long _menu, MENUsimplebutton_field button) {
        pressExit();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnExit(long _menu, SMenu button) {
        pressExit();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onDiscard(long _menu, MENUsimplebutton_field button) {
        pressDiscardAll();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onApply(long _menu, MENUsimplebutton_field button) {
        pressApplyAll();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static long create() {
        isMenuAskedToCreate = true;

        return menues.CreateOfficeMenu(new OfficeMenu(), 1600, 1200, 1600, 1200, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    private void updateAllTabs() {
        for (ApplicationTab t : this.tabs) {
            t.setDirty();
        }

        this.tabs.get(this.current_tab).update();
    }

    private void drawUnsettledDept() {
        long field = this.warning_has_dept.getField("Text 1");
        KeyPair[] pairs = new KeyPair[1];

        pairs[0] = new KeyPair("MONEY", "" + getDept());

        String res_text = MacroKit.Parse(this.unsettled_dubt_text, pairs);

        menues.SetFieldText(field, res_text);
        menues.UpdateMenuField(menues.ConvertMenuFields(field));
        this.warning_has_dept.show();
    }

    private void drawNotEnoughTurnOver(int warehouses) {
        long field = this.warning_not_enough_turns.getField("TEXT1");
        KeyPair[] pairs = new KeyPair[1];

        pairs[0] = new KeyPair("VALUE", "" + warehouses);

        String res_text = MacroKit.Parse(this.not_enought_turns_text, pairs);

        menues.SetFieldText(field, res_text);
        menues.UpdateMenuField(menues.ConvertMenuFields(field));
        this.warning_not_enough_turns.show();
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void ESCPressed(int value) {
        pressExit();
    }

    private void pressExit() {
        makeExitOfficeMenu();

        if (hasChanges()) {
            this.warning_apply_changes.show();

            return;
        }

        if (hasDept()) {
            this.warning_unsettled_dubt.show();

            return;
        }

        if (!(hasVehicle())) {
            this.warning_not_enough_cars.show();

            return;
        }

        makeFinalExit();
    }

    private void pressApplyAll() {
        makeApplyAll();

        if (notEnoughCars()) {
            this.warning_not_enough_cars.show();
        } else if (notEnoughMoney()) {
            this.warning_not_enough_money.show();
        } else if (notEnoughTurnOver()) {
            drawNotEnoughTurnOver(this.APPLYRESULT.not_enough_turnover_num_warehouses);
        }

        updateAllTabs();
    }

    private void pressDiscardAll() {
        makeDiscardAll();
        updateAllTabs();
    }

    private void errorr(String text) {
        Log.menu("Office menu have problem. " + text);
    }

    private boolean hasChanges() {
        if (this.EXITRESULT != null) {
            return this.EXITRESULT.have_changes;
        }

        errorr("hasChanges. EXITRESULT is null");

        return false;
    }

    private boolean hasDept() {
        if (this.EXITRESULT != null) {
            return this.EXITRESULT.have_dept;
        }

        return this.CHECKSTATE.have_dept;
    }

    private int getDept() {
        if (this.EXITRESULT != null) {
            return this.EXITRESULT.dept_value;
        }

        return this.CHECKSTATE.dept_value;
    }

    private boolean hasVehicle() {
        if (this.EXITRESULT != null) {
            return this.EXITRESULT.have_vehicle;
        }

        return this.CHECKSTATE.have_vehicle;
    }

    private boolean notEnoughCars() {
        if (this.APPLYRESULT != null) {
            return this.APPLYRESULT.not_enough_cars;
        }

        errorr("notEnoughCars. APPLYRESULT is null");

        return true;
    }

    private boolean notEnoughMoney() {
        if (this.APPLYRESULT != null) {
            return this.APPLYRESULT.not_enough_money;
        }

        errorr("notEnoughMoney. APPLYRESULT is null");

        return false;
    }

    private boolean notEnoughTurnOver() {
        if (this.APPLYRESULT != null) {
            return this.APPLYRESULT.not_enough_turnover;
        }

        errorr("notEnoughMoney. APPLYRESULT is null");

        return false;
    }

    private void makeApplyAll() {
        this.APPLYRESULT = null;
        this.DISCARDRESULT = null;
        this.EXITRESULT = null;
        this.APPLYRESULT = new ApplyResult();
        JavaEvents.SendEvent(44, 1, this.APPLYRESULT);
    }

    private void makeDiscardAll() {
        this.APPLYRESULT = null;
        this.DISCARDRESULT = null;
        this.EXITRESULT = null;
        this.DISCARDRESULT = new DiscardResult();
        JavaEvents.SendEvent(44, 2, this.DISCARDRESULT);
    }

    private void makeEnterOfficeMenu() {
        if (eng.noNative) {
            return;
        }

        JavaEvents.SendEvent(44, 0, this.CHECKSTATE);

        if (hasDept()) {
            drawUnsettledDept();
        }
    }

    private void makeExitOfficeMenu() {
        this.APPLYRESULT = null;
        this.DISCARDRESULT = null;
        this.EXITRESULT = null;
        this.EXITRESULT = new ExitResult();
        JavaEvents.SendEvent(44, 3, this.EXITRESULT);
    }

    private void makeFinalExit() {
        ListenerManager.TriggerEvent(105);
        JavaEvents.SendEvent(44, 4, this);
        menues.CallMenuCallBack_ExitMenu(this._menu);
    }

    private void makeAutoDebtBallance() {
        JavaEvents.SendEvent(45, 0, this);
    }

    static class ApplyResult {
        boolean not_enough_cars;
        boolean not_enough_money;
        boolean not_enough_turnover;
        int not_enough_turnover_num_warehouses;

        ApplyResult() {
            this.not_enough_cars = false;
            this.not_enough_money = false;
            this.not_enough_turnover = false;
            this.not_enough_turnover_num_warehouses = 0;
        }
    }


    static class CheckState {
        boolean have_dept;
        boolean have_vehicle;
        int dept_value;
    }


    class DirtyListener implements IDirtyListener {
        private final int type;

        DirtyListener(int paramInt) {
            this.type = paramInt;
        }

        /**
         * Method description
         *
         *
         * @param tab
         */
        @Override
        public void settedDirty(ApplicationTab tab) {
            int i = 0;

            for (ApplicationTab singletab : OfficeMenu.this.tabs) {
                if (this.type != i++) {
                    singletab.setDirty();
                }
            }
        }
    }


    static class DiscardResult {}


    static class ExitResult {
        boolean have_changes;
        boolean have_dept;
        boolean have_vehicle;
        int dept_value;
    }


    class MenuDeptListener implements IPoPUpMenuListener {

        /**
         * Method description
         *
         */
        @Override
        public void onAgreeclose() {
            OfficeMenu.this.makeAutoDebtBallance();
            OfficeMenu.this.makeFinalExit();
        }

        /**
         * Method description
         *
         */
        @Override
        public void onClose() {
            OfficeMenu.this.updateAllTabs();
        }

        /**
         * Method description
         *
         */
        @Override
        public void onOpen() {}

        /**
         * Method description
         *
         */
        @Override
        public void onCancel() {}
    }


    class NotEnough_Listener implements IPoPUpMenuListener {

        /**
         * Method description
         *
         */
        @Override
        public void onAgreeclose() {
            onClose();
        }

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
        public void onOpen() {}

        /**
         * Method description
         *
         */
        @Override
        public void onCancel() {}
    }


    class ShowWarning implements IWarningListener {

        /**
         * Method description
         *
         */
        @Override
        public void makeNotEnoughCars() {
            OfficeMenu.this.warning_not_enough_cars.show();
        }

        /**
         * Method description
         *
         */
        @Override
        public void makeNotEnoughMoney() {
            OfficeMenu.this.warning_not_enough_money.show();
        }

        /**
         * Method description
         *
         *
         * @param num_bases
         */
        @Override
        public void makeNotEnoughTurnOver(int num_bases) {
            OfficeMenu.this.drawNotEnoughTurnOver(num_bases);
        }
    }


    static class TotalsValues {
        int rank;
    }


    class UpdateTotals implements IUpdateListener {

        /**
         * Method description
         *
         */
        @Override
        public void onUpdate() {
            if (eng.noNative) {
                return;
            }

            OfficeMenu.TotalsValues values = new OfficeMenu.TotalsValues();

            JavaEvents.SendEvent(44, 5, values);

            int rank = values.rank;

            menues.SetFieldText(OfficeMenu.this.totals[1], "" + rank);

            for (long t : OfficeMenu.this.totals) {
                menues.UpdateMenuField(menues.ConvertMenuFields(t));
            }
        }
    }


    class YesNoCancelListener implements IYesNoCancelMenuListener {

        /**
         * Method description
         *
         */
        @Override
        public void onOpen() {}

        /**
         * Method description
         *
         */
        @Override
        public void onCancelClose() {}

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
        public void onNoClose() {
            OfficeMenu.this.makeDiscardAll();
            OfficeMenu.this.pressExit();
        }

        /**
         * Method description
         *
         */
        @Override
        public void onYesClose() {
            OfficeMenu.this.makeApplyAll();

            if (OfficeMenu.this.notEnoughCars() != false) {
                OfficeMenu.this.warning_not_enough_cars.show();
                OfficeMenu.this.updateAllTabs();
            } else if (OfficeMenu.this.notEnoughMoney() != false) {
                OfficeMenu.this.warning_not_enough_money.show();
                OfficeMenu.this.updateAllTabs();
            } else if (OfficeMenu.this.notEnoughTurnOver() != false) {
                OfficeMenu.this.drawNotEnoughTurnOver(OfficeMenu.this.APPLYRESULT.not_enough_turnover_num_warehouses);
                OfficeMenu.this.updateAllTabs();
            } else {
                OfficeMenu.this.updateAllTabs();
                OfficeMenu.this.pressExit();

                return;
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/26
