/*
 * @(#)QuickRaceNewGame.java   13/08/26
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

import rnr.src.menu.Cmenu_TTI;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.KeyPair;
import rnr.src.menu.ListOfAlternatives;
import rnr.src.menu.MENUEditBox;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUTruckview;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.RadioGroupSmartSwitch;
import rnr.src.menu.TableOfElements;
import rnr.src.menu.menues;
import rnr.src.menuscript.PoPUpMenu;
import rnr.src.menuscript.parametrs.ParametrsBlock;
import rnr.src.menuscript.table.ISelectLineListener;
import rnr.src.menuscript.table.ISetupLine;
import rnr.src.menuscript.table.Table;
import rnr.src.rnrcore.Log;
import rnr.src.rnrcore.loc;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class QuickRaceNewGame extends PanelDialog {
    protected static String TABLE_DEVICE = "Tab0 - START SETTINGS";
    protected static String TABLE_SETTINGS = "TABLEGROUP - INSTANT ORDER - NEW GAME - START SETTINGS - 5 60";
    protected static String START_PARAMS_DEFAULTS = "INSTANT ORDER - NEW GAME - START SETTINGS DEFAULT";
    protected static String OK_BUTTON = "QUICK RACE - NEW GAME OK";
    protected static String OK_BUTTON_GRAY = "QUICK RACE - NEW GAME OK - GRAY";
    private static final String[] SETTINGSTITLES = { "LOD TITLE", "INSTANT ORDER START TIME TITLE",
            "INSTANT ORDER TIME OF DAY FREEZE TITLE", "INSTANT ORDER WEATHER AT START TITLE",
            "INSTANT ORDER WEATHER CHANGES TITLE" };
    private static final String[] LODs = { loc.getMENUString("LOD_EASY"), loc.getMENUString("LOD_NORMAL"),
            loc.getMENUString("LOD_HARD") };
    private static final String[] START_TIMEs = { loc.getMENUString("START_TIME_MORNING"),
            loc.getMENUString("START_TIME_NOON"), loc.getMENUString("START_TIME_EVENING"),
            loc.getMENUString("START_TIME_NIGHT") };
    private static final String[] START_WEATHERs = { loc.getMENUString("START_WEATHER_FINE"),
            loc.getMENUString("START_WEATHER_CLOUDY"), loc.getMENUString("START_WEATHER_RAINY") };
    private static final String[] WEATHER_CHANGES = { loc.getMENUString("WEATHER_CHANGES_FAST"),
            loc.getMENUString("WEATHER_CHANGES_MODERATE"), loc.getMENUString("WEATHER_CHANGES_SLOW"),
            loc.getMENUString("WEATHER_CHANGES_NEVER") };
    private static final String LISTOFALTERANTIVESGROUP = "Tablegroup - ELEMENTS - List";
    private static final String RADIOGROUPSMARTSWITCHGROUP = "Tablegroup - ELEMENTS - Switch";
    private static final String XML = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String RANGER_TRUCKS = "Tableranger - INSTANT ORDER - NEW GAME - SELECT VEHICLE";
    private static final String TABLE_TRUCKS = "TABLEGROUP - INSTANT ORDER - NEW GAME - SELECT VEHICLE - 14 40";
    private static final String TABLE_TRUCK_LINE = "Tablegroup - QUICK RACE - SELECT VEHICLE";
    private static final String[] TABLE_TRUCK_ELEMENTS = { "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - MODEL VALUE",
            "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - HORSE POWER VALUE",
            "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - MODEL VALUE - GRAY",
            "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - HORSE POWER VALUE - GRAY" };
    private static final String RANGER_WAREHOUSE = "Tableranger - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE";
    private static final String TABLE_WAREHOUSE = "TABLEGROUP - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - 14 40";
    private static final String TABLE_WAREHOUSE_LINE = "Tablegroup - QUICK RACE - SELECT RACE";
    private static final String[] TABLE_WAREHOUSE_ELEMENTS = {
        "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - WAREHOUSE NAME",
        "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - WAREHOUSE NAME DETAILS",
        "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - ROC VALUE",
        "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - WAREHOUSE NAME - GRAY",
        "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - WAREHOUSE NAME DETAILS - GRAY",
        "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - ROC VALUE - GRAY"
    };
    private static final String RESTYLE_VEHICLE_BUTTON =
        "INSTANT ORDER - NEW GAME - SELECT VEHICLE - RestyleWindow - RESTYLE";
    private static final String RESTYLE_VEHICLE_BUTTON_GRAY =
        "INSTANT ORDER - NEW GAME - SELECT VEHICLE - RestyleWindow - RESTYLE - GRAY";
    private static final String RESTYLE_VEHICLE_METHOD = "OnRestyle";
    private long ok_button = 0L;
    private long ok_gray = 0L;
    private final boolean choose_gray_wh = false;
    private final boolean choose_gray_veh = false;
    protected ParametrsBlock param_values = new ParametrsBlock();
    private TableOfElements table_settings = null;
    ListOfAlternatives settings0 = null;
    ListOfAlternatives settings1 = null;
    RadioGroupSmartSwitch settings2 = null;
    ListOfAlternatives settings3 = null;
    ListOfAlternatives settings4 = null;
    private Trucks truckTable = null;

    /** Field description */
    public Vector<TruckInfo> in_truck_lines = new Vector();

    /** Field description */
    public sort out_truck_sort_mode = null;

    /** Field description */
    public QUICK_RACE_TRUCK_TABLE_DATA int_truck_table_data = new QUICK_RACE_TRUCK_TABLE_DATA();
    private Warehouse warehouseTable = null;
    Vector<MapWarehouseInfo> map_warehouses = new Vector();

    /** Field description */
    public Vector<WarehouseInfo> in_warehouse_lines = new Vector();

    /** Field description */
    public sort out_warehouse_sort_mode = null;

    /** Field description */
    public QUICK_RACE_WAREHOUSE_TABLE_DATA int_warehouse_table_data = new QUICK_RACE_WAREHOUSE_TABLE_DATA();
    private long restyle_button = 0L;
    private long restyle_button_gray = 0L;
    QuickRace_RestyleVehicle restyle_vehicle = null;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param controls
     * @param window
     * @param exitButton
     * @param defaultButton
     * @param okButton
     * @param parent
     */
    public QuickRaceNewGame(long _menu, long[] controls, long window, long exitButton, long defaultButton,
                            long okButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, 0L, parent);
        this.table_settings = new TableOfElements(_menu, TABLE_SETTINGS);
        this.settings0 = new ListOfAlternatives(parent.menu.XML_FILE, "Tablegroup - ELEMENTS - List",
                loc.getMENUString(SETTINGSTITLES[0]), LODs, false);
        this.settings1 = new ListOfAlternatives(parent.menu.XML_FILE, "Tablegroup - ELEMENTS - List",
                loc.getMENUString(SETTINGSTITLES[1]), START_TIMEs, false);
        this.settings2 = new RadioGroupSmartSwitch(parent.menu.XML_FILE, "Tablegroup - ELEMENTS - Switch",
                loc.getMENUString(SETTINGSTITLES[2]), true, false);
        this.settings3 = new ListOfAlternatives(parent.menu.XML_FILE, "Tablegroup - ELEMENTS - List",
                loc.getMENUString(SETTINGSTITLES[3]), START_WEATHERs, false);
        this.settings4 = new ListOfAlternatives(parent.menu.XML_FILE, "Tablegroup - ELEMENTS - List",
                loc.getMENUString(SETTINGSTITLES[4]), WEATHER_CHANGES, false);
        this.settings0.load(_menu);
        this.settings1.load(_menu);
        this.settings2.load(_menu);
        this.settings3.load(_menu);
        this.settings4.load(_menu);
        this.param_values.addParametr("QUICK RACE LOD", 0, 0, this.settings0);
        this.param_values.addParametr("QUICK RACE START_TIME", 1, 1, this.settings1);
        this.param_values.addParametr("QUICK RACE DAY_FREEZE", false, false, this.settings2);
        this.param_values.addParametr("QUICK RACE WEATHER_AT_START", 1, 1, this.settings3);
        this.param_values.addParametr("QUICK RACE WEATHER_CHANGES", 1, 1, this.settings4);
        this.table_settings.insert(this.settings0);
        this.table_settings.insert(this.settings1);
        this.table_settings.insert(this.settings2);
        this.table_settings.insert(this.settings3);
        this.table_settings.insert(this.settings4);

        long field = menues.FindFieldInMenu(_menu, START_PARAMS_DEFAULTS);
        MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

        menues.SetScriptOnControl(_menu, buts, this, "OnStartParamsDefault", 4L);
        this.restyle_button = menues.FindFieldInMenu(_menu,
                "INSTANT ORDER - NEW GAME - SELECT VEHICLE - RestyleWindow - RESTYLE");
        buts = menues.ConvertSimpleButton(this.restyle_button);
        menues.SetScriptOnControl(_menu, buts, this, "OnRestyle", 4L);
        this.restyle_button_gray = menues.FindFieldInMenu(_menu,
                "INSTANT ORDER - NEW GAME - SELECT VEHICLE - RestyleWindow - RESTYLE - GRAY");
        this.ok_button = menues.FindFieldInMenu(_menu, OK_BUTTON);
        this.ok_gray = menues.FindFieldInMenu(_menu, OK_BUTTON_GRAY);
        this.truckTable = new Trucks(_menu, this);
        this.warehouseTable = new Warehouse(_menu, this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
        this.table_settings.DeInit();
        deinit();
        super.exitMenu();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnRestyle(long _menu, MENUsimplebutton_field button) {
        menues.createSimpleMenu(new QuickRace_RestyleVehicle(this.int_truck_table_data.current_truck_id, this));
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnStartParamsDefault(long _menu, MENUsimplebutton_field button) {
        this.param_values.onDefault();
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        this.table_settings.initTable();
        this.truckTable.afterInit();
        this.warehouseTable.afterInit();
        super.afterInit();
        JavaEvents.SendEvent(65, 7, this.param_values);
        this.param_values.onUpdate();
    }

    /**
     * Method description
     *
     */
    @Override
    public void update() {
        super.update();

        int old_truck_selection = this.int_truck_table_data.current_truck_id;

        this.truckTable.updateTable();

        for (int i = 0; i < this.truckTable.TABLE_DATA.all_lines.size(); ++i) {
            if (this.truckTable.TABLE_DATA.all_lines.elementAt(i).truck_id == old_truck_selection) {
                this.truckTable.table.select_line_by_data(this.truckTable.TABLE_DATA.all_lines.elementAt(i));
            }
        }

        int old_warehouse_selection = this.int_warehouse_table_data.current_warehouse_id;

        this.warehouseTable.updateTable();

        for (int i = 0; i < this.warehouseTable.TABLE_DATA.all_lines.size(); ++i) {
            if (this.warehouseTable.TABLE_DATA.all_lines.elementAt(i).wh_id == old_warehouse_selection) {
                this.warehouseTable.table.select_line_by_data(this.warehouseTable.TABLE_DATA.all_lines.elementAt(i));
            }
        }
    }

    /**
     * Method description
     *
     */
    public void deinit() {
        this.truckTable.deinit();
        this.warehouseTable.deinit();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void OnOk(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
        JavaEvents.SendEvent(65, 8, this.param_values);
        this.truckTable.onOk();
        this.warehouseTable.onOk();
        SaveLoadCommonManagement.getSaveLoadCommonManager().SetStartNewGameFlag(4);
        TopWindow.quitTopMenu();
    }

    private void UpdateGrayButtons() {
        if (this.ok_button != 0L) {
            if ((this.choose_gray_wh) || (this.choose_gray_veh)) {
                menues.SetShowField(this.ok_button, false);
            } else {
                menues.SetShowField(this.ok_button, true);
            }
        }

        if (this.ok_gray != 0L) {
            menues.SetBlindess(this.ok_gray, true);
            menues.SetIgnoreEvents(this.ok_gray, true);
        }

        if (this.restyle_button_gray != 0L) {
            menues.SetBlindess(this.restyle_button_gray, true);
            menues.SetIgnoreEvents(this.restyle_button_gray, true);
        }

        if (this.ok_gray != 0L) {
            if ((this.choose_gray_wh) || (this.choose_gray_veh)) {
                menues.SetShowField(this.ok_gray, true);
            } else {
                menues.SetShowField(this.ok_gray, false);
            }
        }

        if (this.restyle_button != 0L) {
            if (this.choose_gray_veh) {
                menues.SetShowField(this.restyle_button, false);
            } else {
                menues.SetShowField(this.restyle_button, true);
            }
        }

        if (this.restyle_button_gray != 0L) {
            if (this.choose_gray_veh) {
                menues.SetShowField(this.restyle_button_gray, true);
            } else {
                menues.SetShowField(this.restyle_button_gray, false);
            }
        }
    }

    /**
     * Method description
     *
     */
    public void valueChanged() {}

    @Override
    protected void onShow(boolean value) {
        this.warehouseTable.make_map_sync_group_and_seå_blindness();
        this.warehouseTable.hide_left_warehouse();

        if (value) {
            UpdateGrayButtons();
        }
    }

    @Override
    protected void onFreeze(boolean value) {
        this.warehouseTable.make_map_sync_group_and_seå_blindness();
        this.warehouseTable.hide_left_warehouse();

        if (!(value)) {
            UpdateGrayButtons();
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void readParamValues() {
        JavaEvents.SendEvent(65, 1, this.int_truck_table_data);

        int old_truck_selection = this.int_truck_table_data.current_truck_id;

        this.truckTable.updateTable();

        for (int i = 0; i < this.truckTable.TABLE_DATA.all_lines.size(); ++i) {
            if (this.truckTable.TABLE_DATA.all_lines.elementAt(i).truck_id == old_truck_selection) {
                this.truckTable.table.select_line_by_data(this.truckTable.TABLE_DATA.all_lines.elementAt(i));
            }
        }

        JavaEvents.SendEvent(65, 4, this.int_warehouse_table_data);

        int old_warehouse_selection = this.int_warehouse_table_data.current_warehouse_id;

        this.warehouseTable.updateTable();

        for (int i = 0; i < this.warehouseTable.TABLE_DATA.all_lines.size(); ++i) {
            if (this.warehouseTable.TABLE_DATA.all_lines.elementAt(i).wh_id == old_warehouse_selection) {
                this.warehouseTable.table.select_line_by_data(this.warehouseTable.TABLE_DATA.all_lines.elementAt(i));
            }
        }

        JavaEvents.SendEvent(65, 7, this.param_values);
        this.param_values.onUpdate();
    }

    static class MapWarehouseInfo {
        MENUbutton_field button_text;
        MENUbutton_field button_icon;
        int sync_group;
        long button_text_id;
        long button_icon_id;
        int wh_id;

        MapWarehouseInfo() {
            this.button_text = null;
            this.button_icon = null;
            this.sync_group = 0;
            this.button_text_id = 0L;
            this.button_icon_id = 0L;
            this.wh_id = 0;
        }
    }


    class PopUpLongWarehouseInfo {
        private static final String MENU_GROUP = "TOOLTIP - QUICK RACE - SELECT RACE - WAREHOUSE NAME DETAILS";
        private static final String TOOLTIP_TEXT =
            "TOOLTIP - QUICK RACE - SELECT RACE - WAREHOUSE NAME DETAILS - DETAILS TOOLTIP - TEXT";
        private PoPUpMenu menu = null;
        private long text = 0L;

        PopUpLongWarehouseInfo(long paramLong) {
            this.menu = new PoPUpMenu(paramLong, "..\\data\\config\\menu\\menu_MAIN.xml",
                                      "TOOLTIP - QUICK RACE - SELECT RACE - WAREHOUSE NAME DETAILS",
                                      "TOOLTIP - QUICK RACE - SELECT RACE - WAREHOUSE NAME DETAILS");
            this.text = this.menu.getField(
                "TOOLTIP - QUICK RACE - SELECT RACE - WAREHOUSE NAME DETAILS - DETAILS TOOLTIP - TEXT");
        }

        /**
         * Method description
         *
         */
        public void afterInit() {
            this.menu.afterInit();
        }

        void show(int shift, String name) {
            menues.SetFieldText(this.text, name);
            menues.UpdateMenuField(menues.ConvertMenuFields(this.text));
            this.menu.MoveByFromOrigin(0, shift);
            this.menu.show();
        }
    }


    static class QUICK_RACE_TRUCK_TABLE_DATA {
        int default_truck_id;
        int current_truck_id;

        QUICK_RACE_TRUCK_TABLE_DATA() {
            this.default_truck_id = 0;
            this.current_truck_id = 0;
        }
    }


    static class QUICK_RACE_WAREHOUSE_TABLE_DATA {
        int default_warehouse_id;
        int current_warehouse_id;

        QUICK_RACE_WAREHOUSE_TABLE_DATA() {
            this.default_warehouse_id = 0;
            this.current_warehouse_id = 0;
        }
    }


    static class TruckInfo {
        String truck_name;
        String manufacturer_name;
        int horse_power;
        int truck_id;
        boolean wheather_show;
        boolean isGray;

        TruckInfo() {
            this.wheather_show = true;
            this.isGray = true;
        }
    }


    class Trucks implements ISetupLine, ISelectLineListener {
        private static final int TRUCK_NAME = 0;
        private static final int TRUCK_POWER = 1;
        private static final int TRUCK_NAME_GRAY = 2;
        private static final int TRUCK_POWER_GRAY = 3;
        private final String SORT_METHOD = "onSort";
        private final String DEFAULT_METHOD = "onDefault";
        private final String[] SORT = { "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - MODEL TITLE",
                                        "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - HORSE POWER TITLE" };
        private final String DEFAULT = "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - DEFAULT";
        private final QuickRaceNewGame.truck_table_data TABLE_DATA = new QuickRaceNewGame.truck_table_data();
        private long _menu = 0L;
        QuickRaceNewGame _parent = null;
        String truck_power_string = null;
        Table table;

        Trucks(long menu, QuickRaceNewGame parent) {
            QuickRaceNewGame.this.out_truck_sort_mode = new QuickRaceNewGame.sort(0, true);
            this._parent = parent;
            this._menu = menu;
            this.table = new Table(this._menu, "TABLEGROUP - INSTANT ORDER - NEW GAME - SELECT VEHICLE - 14 40",
                                   "Tableranger - INSTANT ORDER - NEW GAME - SELECT VEHICLE");
            this.table.setSelectionMode(1);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_MAIN.xml",
                                     "Tablegroup - QUICK RACE - SELECT VEHICLE", QuickRaceNewGame.TABLE_TRUCK_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            reciveTableData();
            build_tree_data();

            for (String name : QuickRaceNewGame.TABLE_TRUCK_ELEMENTS) {
                this.table.initLinesSelection(name);
            }

            if (null != this.SORT) {
                for (int i = 0; i < this.SORT.length; ++i) {
                    long field = menues.FindFieldInMenu(this._menu, this.SORT[i]);
                    MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

                    buts.userid = i;
                    menues.SetScriptOnControl(this._menu, buts, this, "onSort", 4L);
                    menues.UpdateField(buts);
                }
            }

            long field = menues.FindFieldInMenu(this._menu,
                             "BUTTON - INSTANT ORDER - NEW GAME - SELECT VEHICLE - DEFAULT");
            MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

            menues.SetScriptOnControl(this._menu, buts, this, "onDefault", 4L);
            menues.UpdateField(buts);
        }

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            QuickRaceNewGame.TruckInfo line = (QuickRaceNewGame.TruckInfo) table_node.item;

            if (!(line.wheather_show)) {
                menues.SetShowField(obj.nativePointer, false);

                return;
            }

            int control = this.table.getMarkedPosition(obj.nativePointer);
            KeyPair[] pairs = new KeyPair[1];

            switch (control) {
             case 0 :
                 obj.text = line.manufacturer_name + " " + line.truck_name;
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);

                 break;

             case 1 :
                 if (this.truck_power_string == null) {
                     this.truck_power_string = obj.text;
                 }

                 pairs[0] = new KeyPair("VALUE", "" + line.horse_power);
                 obj.text = MacroKit.Parse(this.truck_power_string, pairs);
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);

                 break;

             case 2 :
                 obj.text = line.manufacturer_name + " " + line.truck_name;
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);

                 break;

             case 3 :
                 if (this.truck_power_string == null) {
                     this.truck_power_string = obj.text;
                 }

                 pairs[0] = new KeyPair("VALUE", "" + line.horse_power);
                 obj.text = MacroKit.Parse(this.truck_power_string, pairs);
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);
            }

            if (line.isGray) {
                if ((control == 0) || (control == 1)) {
                    menues.SetShowField(obj.nativePointer, false);
                } else {
                    menues.SetShowField(obj.nativePointer, true);
                }
            } else if ((control == 0) || (control == 1)) {
                menues.SetShowField(obj.nativePointer, true);
            } else {
                menues.SetShowField(obj.nativePointer, false);
            }
        }

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {}

        private Cmenu_TTI convertTableData() {
            Cmenu_TTI root = new Cmenu_TTI();

            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                Cmenu_TTI ch = new Cmenu_TTI();

                ch.toshow = true;
                ch.ontop = (i == 0);
                ch.item = this.TABLE_DATA.all_lines.get(i);
                root.children.add(ch);
            }

            return root;
        }

        private void build_tree_data() {
            this.table.reciveTreeData(convertTableData());
        }

        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();
            JavaEvents.SendEvent(65, 0, this._parent);

            for (int i = 0; i < QuickRaceNewGame.this.in_truck_lines.size(); ++i) {
                this.TABLE_DATA.all_lines.add(QuickRaceNewGame.this.in_truck_lines.elementAt(i));
            }

            buildvoidcells();
        }

        private void buildvoidcells() {
            if (this.TABLE_DATA.all_lines.size() < this.table.getNumRows()) {
                int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();

                for (int i = 0; i < dif; ++i) {
                    QuickRaceNewGame.TruckInfo data = new QuickRaceNewGame.TruckInfo();

                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
            } else {
                int count_good_data = 0;
                Iterator iter = this.TABLE_DATA.all_lines.iterator();

                while ((iter.hasNext()) && (((QuickRaceNewGame.TruckInfo) iter.next()).wheather_show)) {
                    ++count_good_data;
                }

                if ((count_good_data >= this.table.getNumRows())
                        && (count_good_data < this.TABLE_DATA.all_lines.size())) {
                    for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                        this.TABLE_DATA.all_lines.remove(i);
                    }
                }
            }
        }

        private void make_sync_group() {
            long[] contrls_name = this.table.getLineStatistics_controls(QuickRaceNewGame.TABLE_TRUCK_ELEMENTS[0]);
            long[] contrls_power = this.table.getLineStatistics_controls(QuickRaceNewGame.TABLE_TRUCK_ELEMENTS[1]);
            long[] contrls_name_gray = this.table.getLineStatistics_controls(QuickRaceNewGame.TABLE_TRUCK_ELEMENTS[2]);
            long[] contrls_power_gray = this.table.getLineStatistics_controls(QuickRaceNewGame.TABLE_TRUCK_ELEMENTS[3]);

            if (contrls_name.length != contrls_power.length) {
                Log.menu("ERRORR. make_sync_group has wrong behaivoir. contrls_name.length is " + contrls_name.length
                         + " contrls_time.length is " + contrls_power.length);

                return;
            }

            for (int i = 0; i < contrls_name.length; ++i) {
                menues.SetSyncControlActive(this._menu, i, contrls_name[i]);
                menues.SetSyncControlState(this._menu, i, contrls_name[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_power[i]);
                menues.SetSyncControlState(this._menu, i, contrls_power[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_name_gray[i]);
                menues.SetSyncControlState(this._menu, i, contrls_name_gray[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_power_gray[i]);
                menues.SetSyncControlState(this._menu, i, contrls_power_gray[i]);
            }
        }

        /**
         * Method description
         *
         */
        public void updateTable() {
            reciveTableData();
            build_tree_data();
            this.table.refresh();
        }

        /**
         * Method description
         *
         */
        public void afterInit() {
            JavaEvents.SendEvent(65, 1, QuickRaceNewGame.this.int_truck_table_data);

            int old_truck_selection = QuickRaceNewGame.this.int_truck_table_data.current_truck_id;

            this.table.afterInit();
            make_sync_group();

            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt(i).truck_id == old_truck_selection) {
                    this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
                }
            }
        }

        /**
         * Method description
         *
         */
        public void deinit() {
            this.table.deinit();
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onSort(long _menu, MENUsimplebutton_field button) {
            switch (button.userid) {
             case 0 :
                 QuickRaceNewGame.this.out_truck_sort_mode = new QuickRaceNewGame.sort(0,
                         !(QuickRaceNewGame.this.out_truck_sort_mode.up));

                 break;

             case 1 :
                 QuickRaceNewGame.this.out_truck_sort_mode = new QuickRaceNewGame.sort(1,
                         !(QuickRaceNewGame.this.out_truck_sort_mode.up));
            }

            int old_truck_selection = QuickRaceNewGame.this.int_truck_table_data.current_truck_id;

            updateTable();

            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt(i).truck_id == old_truck_selection) {
                    this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
                }
            }
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onDefault(long _menu, MENUsimplebutton_field button) {
            updateTable();

            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt(i).truck_id
                        == QuickRaceNewGame.this.int_truck_table_data.default_truck_id) {
                    this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
                }
            }
        }

        /**
         * Method description
         *
         *
         * @param table
         * @param line
         */
        @Override
        public void selectLineEvent(Table table, int line) {
            QuickRaceNewGame.TruckInfo data = (QuickRaceNewGame.TruckInfo) table.getItemOnLine(line).item;

            QuickRaceNewGame.this.int_truck_table_data.current_truck_id = data.truck_id;
            QuickRaceNewGame.access$102(QuickRaceNewGame.this, data.isGray);
            QuickRaceNewGame.this.UpdateGrayButtons();
            JavaEvents.SendEvent(65, 2, QuickRaceNewGame.this.int_truck_table_data);
        }

        /**
         * Method description
         *
         *
         * @param table
         * @param lines
         */
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {}

        /**
         * Method description
         *
         */
        public void onOk() {
            JavaEvents.SendEvent(65, 10, QuickRaceNewGame.this.int_truck_table_data);
        }
    }


    class Warehouse implements ISetupLine, ISelectLineListener {
        private static final int WAREHOUSE_NAME = 0;
        private static final int WAREHOUSE_POWER = 1;
        private final String SORT_METHOD = "onSort";
        private final String DEFAULT_METHOD = "onDefault";
        private final String ON_MAP_METHOD_EXECUTE = "onMapExectute";
        private final String ON_FULL_INFO = "onFullInfo";
        private final String[] SORT = { "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - WAREHOUSE TITLE",
                                        "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - ROC TITLE" };
        private final String DEFAULT = "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - DEFAULT";
        private final QuickRaceNewGame.warehouse_table_data TABLE_DATA = new QuickRaceNewGame.warehouse_table_data();
        private long _menu = 0L;
        QuickRaceNewGame _parent = null;
        QuickRaceNewGame.PopUpLongWarehouseInfo pop_up = null;
        private int[] old_sync_group = null;
        String warehouse_commodities_string = null;
        Table table;

        Warehouse(long menu, QuickRaceNewGame parent) {
            QuickRaceNewGame.this.out_warehouse_sort_mode = new QuickRaceNewGame.sort(0, true);
            this._parent = parent;
            this._menu = menu;
            this.table = new Table(this._menu, "TABLEGROUP - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - 14 40",
                                   "Tableranger - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE");
            this.table.setSelectionMode(1);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_MAIN.xml", "Tablegroup - QUICK RACE - SELECT RACE",
                                     QuickRaceNewGame.TABLE_WAREHOUSE_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);

            for (String name : QuickRaceNewGame.TABLE_WAREHOUSE_ELEMENTS) {
                this.table.initLinesSelection(name);
            }

            if (null != this.SORT) {
                for (int i = 0; i < this.SORT.length; ++i) {
                    long field = menues.FindFieldInMenu(this._menu, this.SORT[i]);
                    MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

                    buts.userid = i;
                    menues.SetScriptOnControl(this._menu, buts, this, "onSort", 4L);
                    menues.UpdateField(buts);
                }
            }

            long field = menues.FindFieldInMenu(this._menu,
                             "BUTTON - INSTANT ORDER - NEW GAME - SELECT WAREHOUSE - DEFAULT");
            MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

            menues.SetScriptOnControl(this._menu, buts, this, "onDefault", 4L);
            menues.UpdateField(buts);
            this.pop_up = new QuickRaceNewGame.PopUpLongWarehouseInfo(QuickRaceNewGame.this, menu);
        }

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            QuickRaceNewGame.WarehouseInfo line = (QuickRaceNewGame.WarehouseInfo) table_node.item;

            if (!(line.wheather_show)) {
                menues.SetShowField(obj.nativePointer, false);

                return;
            }

            int control = this.table.getMarkedPosition(obj.nativePointer);

            switch (control) {
             case 0 :
             case 3 :
                 obj.text = line.warehouse_name;
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);

                 break;

             case 1 :
             case 4 :
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);

                 break;

             case 2 :
             case 5 :
                 if (this.warehouse_commodities_string == null) {
                     this.warehouse_commodities_string = obj.text;
                 }

                 KeyPair[] pairs = new KeyPair[1];

                 pairs[0] = new KeyPair("VALUE", "" + line.warehouse_commodities);
                 obj.text = MacroKit.Parse(this.warehouse_commodities_string, pairs);
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);
            }

            if (this.old_sync_group != null) {
                menues.RemoveSyncControlActive(this._menu, this.old_sync_group[obj.userid], obj.nativePointer);
                menues.RemoveSyncControlState(this._menu, this.old_sync_group[obj.userid], obj.nativePointer);
            }

            menues.SetSyncControlActive(this._menu, get_sync_group(line.wh_id), obj.nativePointer);
            menues.SetSyncControlState(this._menu, get_sync_group(line.wh_id), obj.nativePointer);

            if (control == 5) {
                if (this.old_sync_group == null) {
                    this.old_sync_group = new int[this.table.getNumRows()];
                }

                this.old_sync_group[obj.userid] = get_sync_group(line.wh_id);
            }

            if (line.isGray) {
                if (control <= 2) {
                    menues.SetShowField(obj.nativePointer, false);
                } else {
                    menues.SetShowField(obj.nativePointer, true);
                }
            } else if (control <= 2) {
                menues.SetShowField(obj.nativePointer, true);
            } else {
                menues.SetShowField(obj.nativePointer, false);
            }
        }

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {}

        private Cmenu_TTI convertTableData() {
            Cmenu_TTI root = new Cmenu_TTI();

            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                Cmenu_TTI ch = new Cmenu_TTI();

                ch.toshow = true;
                ch.ontop = (i == 0);
                ch.item = this.TABLE_DATA.all_lines.get(i);
                root.children.add(ch);
            }

            return root;
        }

        private void build_tree_data() {
            this.table.reciveTreeData(convertTableData());
        }

        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();
            JavaEvents.SendEvent(65, 3, this._parent);

            for (int i = 0; i < QuickRaceNewGame.this.in_warehouse_lines.size(); ++i) {
                QuickRaceNewGame.WarehouseInfo info = QuickRaceNewGame.this.in_warehouse_lines.elementAt(i);

                this.TABLE_DATA.all_lines.add(info);
            }

            buildvoidcells();
        }

        private void buildvoidcells() {
            if (this.TABLE_DATA.all_lines.size() < this.table.getNumRows()) {
                int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();

                for (int i = 0; i < dif; ++i) {
                    QuickRaceNewGame.WarehouseInfo data = new QuickRaceNewGame.WarehouseInfo();

                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
            } else {
                int count_good_data = 0;
                Iterator iter = this.TABLE_DATA.all_lines.iterator();

                while ((iter.hasNext()) && (((QuickRaceNewGame.WarehouseInfo) iter.next()).wheather_show)) {
                    ++count_good_data;
                }

                if ((count_good_data >= this.table.getNumRows())
                        && (count_good_data < this.TABLE_DATA.all_lines.size())) {
                    for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                        this.TABLE_DATA.all_lines.remove(i);
                    }
                }
            }
        }

        private int get_sync_group(int wh_id) {
            for (int i = 0; i < QuickRaceNewGame.this.map_warehouses.size(); ++i) {
                if (QuickRaceNewGame.this.map_warehouses.elementAt(i).wh_id == wh_id) {
                    return QuickRaceNewGame.this.map_warehouses.elementAt(i).sync_group;
                }
            }

            return 0;
        }

        private void make_map_sync_group_and_seå_blindness() {
            if (QuickRaceNewGame.this.map_warehouses.size() == 0) {
                JavaEvents.SendEvent(65, 3, this._parent);

                for (int i = 0; i < QuickRaceNewGame.this.in_warehouse_lines.size(); ++i) {
                    QuickRaceNewGame.MapWarehouseInfo map_info = new QuickRaceNewGame.MapWarehouseInfo();
                    QuickRaceNewGame.WarehouseInfo info = QuickRaceNewGame.this.in_warehouse_lines.elementAt(i);

                    map_info.button_icon_id = menues.FindFieldInMenu(this._menu,
                            "ButtonMap - " + info.warehouse_internal_name);
                    map_info.button_text_id = menues.FindFieldInMenu(this._menu,
                            "ButtonMapText - " + info.warehouse_internal_name);
                    map_info.button_icon = menues.ConvertButton(map_info.button_icon_id);
                    map_info.button_text = menues.ConvertButton(map_info.button_text_id);

                    if ((map_info.button_icon_id != 0L) && (map_info.button_text_id != 0L)
                            && (map_info.button_icon != null) && (map_info.button_text != null)) {
                        menues.SetScriptOnControl(this._menu, map_info.button_icon, this, "onMapExectute", 2L);
                        menues.UpdateField(map_info.button_icon);
                        map_info.button_text.text = info.warehouse_name;
                        menues.UpdateField(map_info.button_text);
                        map_info.wh_id = info.wh_id;
                        map_info.sync_group = (20 + i);
                        menues.SetSyncControlActive(this._menu, map_info.sync_group, map_info.button_icon_id);
                        menues.SetSyncControlState(this._menu, map_info.sync_group, map_info.button_icon_id);
                        menues.SetSyncControlActive(this._menu, map_info.sync_group, map_info.button_text_id);
                        menues.SetSyncControlState(this._menu, map_info.sync_group, map_info.button_text_id);
                        QuickRaceNewGame.this.map_warehouses.add(map_info);
                    }
                }
            }

            for (int i = 0; i < QuickRaceNewGame.this.map_warehouses.size(); ++i) {
                if ((QuickRaceNewGame.this.map_warehouses.elementAt(i).button_icon != null)
                        && (QuickRaceNewGame.this.map_warehouses.elementAt(i).button_text != null)) {
                    menues.SetBlindess(QuickRaceNewGame.this.map_warehouses.elementAt(i).button_text.nativePointer,
                                       true);
                    menues.SetIgnoreEvents(QuickRaceNewGame.this.map_warehouses.elementAt(i).button_text.nativePointer,
                                           true);
                }
            }
        }

        /**
         * Method description
         *
         */
        public void updateTable() {
            reciveTableData();
            build_tree_data();
            this.table.refresh();
        }

        private void bind_detailed_info() {
            long[] contrls_but = null;

            contrls_but = this.table.getLineStatistics_controls(QuickRaceNewGame.TABLE_WAREHOUSE_ELEMENTS[1]);

            for (int i = 0; i < contrls_but.length; ++i) {
                MENUbutton_field obj = menues.ConvertButton(contrls_but[i]);

                menues.SetScriptOnControl(this._menu, obj, this, "onFullInfo", 4L);
            }
        }

        private void hide_left_warehouse() {
            long text_id = menues.FindFieldInMenu(this._menu, "ButtonMapText - MW");
            long icon_id = menues.FindFieldInMenu(this._menu, "ButtonMap - MW");
            MENUbutton_field text = menues.ConvertButton(text_id);

            if ((text != null) && (text.nativePointer != 0L)) {
                menues.SetShowField(text.nativePointer, false);
            }

            MENUbutton_field icon = menues.ConvertButton(icon_id);

            if ((icon != null) && (icon.nativePointer != 0L)) {
                menues.SetShowField(icon.nativePointer, false);
            }
        }

        /**
         * Method description
         *
         */
        public void afterInit() {
            JavaEvents.SendEvent(65, 4, QuickRaceNewGame.this.int_warehouse_table_data);

            int old_warehouse_selection = QuickRaceNewGame.this.int_warehouse_table_data.current_warehouse_id;

            this.table.afterInit();
            make_map_sync_group_and_seå_blindness();
            bind_detailed_info();

            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt(i).wh_id == old_warehouse_selection) {
                    this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
                }
            }
        }

        /**
         * Method description
         *
         */
        public void deinit() {
            this.table.deinit();
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onFullInfo(long _menu, MENUbutton_field button) {
            QuickRaceNewGame.WarehouseInfo line =
                (QuickRaceNewGame.WarehouseInfo) this.table.getItemOnLine(button.userid).item;

            this.pop_up.show(button.userid * 40, line.warehouse_long_name);
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onSort(long _menu, MENUsimplebutton_field button) {
            switch (button.userid) {
             case 0 :
                 QuickRaceNewGame.this.out_warehouse_sort_mode = new QuickRaceNewGame.sort(0,
                         !(QuickRaceNewGame.this.out_warehouse_sort_mode.up));

                 break;

             case 1 :
                 QuickRaceNewGame.this.out_warehouse_sort_mode = new QuickRaceNewGame.sort(1,
                         !(QuickRaceNewGame.this.out_warehouse_sort_mode.up));
            }

            int old_warehouse_selection = QuickRaceNewGame.this.int_warehouse_table_data.current_warehouse_id;

            updateTable();

            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt(i).wh_id == old_warehouse_selection) {
                    this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
                }
            }
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onDefault(long _menu, MENUsimplebutton_field button) {
            updateTable();

            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt(i).wh_id
                        == QuickRaceNewGame.this.int_warehouse_table_data.default_warehouse_id) {
                    this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
                }
            }
        }

        /**
         * Method description
         *
         *
         * @param table
         * @param line
         */
        @Override
        public void selectLineEvent(Table table, int line) {
            if ((table != null) && (table.getItemOnLine(line) != null)) {
                QuickRaceNewGame.WarehouseInfo data = (QuickRaceNewGame.WarehouseInfo) table.getItemOnLine(line).item;

                QuickRaceNewGame.this.int_warehouse_table_data.current_warehouse_id = data.wh_id;
                JavaEvents.SendEvent(65, 11, QuickRaceNewGame.this.int_warehouse_table_data);
                ExternalSelectWarehouse(data.wh_id);
                QuickRaceNewGame.access$402(QuickRaceNewGame.this, data.isGray);
                QuickRaceNewGame.this.UpdateGrayButtons();
            }
        }

        /**
         * Method description
         *
         *
         * @param wh_id
         */
        public void ExternalSelectWarehouse(int wh_id) {
            for (int i = 0; i < QuickRaceNewGame.this.map_warehouses.size(); ++i) {
                menues.SetFieldState(QuickRaceNewGame.this.map_warehouses.elementAt(i).button_icon_id,
                                     (QuickRaceNewGame.this.map_warehouses.elementAt(i).wh_id == wh_id)
                                     ? 1
                                     : 0);
                menues.SetFieldState(QuickRaceNewGame.this.map_warehouses.elementAt(i).button_text_id,
                                     (QuickRaceNewGame.this.map_warehouses.elementAt(i).wh_id == wh_id)
                                     ? 1
                                     : 0);
            }
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onMapExectute(long _menu, MENUbutton_field button) {
            int wh_id = -1;

            for (int i = 0; i < QuickRaceNewGame.this.map_warehouses.size(); ++i) {
                if (QuickRaceNewGame.this.map_warehouses.elementAt(i).button_icon == button) {
                    wh_id = QuickRaceNewGame.this.map_warehouses.elementAt(i).wh_id;

                    break;
                }
            }

            if (wh_id >= 0) {
                for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                    if (this.TABLE_DATA.all_lines.elementAt(i).wh_id == wh_id) {
                        this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
                    }
                }
            }
        }

        /**
         * Method description
         *
         *
         * @param table
         * @param lines
         */
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {}

        /**
         * Method description
         *
         */
        public void onOk() {
            JavaEvents.SendEvent(65, 9, QuickRaceNewGame.this.int_warehouse_table_data);
        }
    }


    static class WarehouseInfo {
        String warehouse_name;
        String warehouse_long_name;
        String warehouse_internal_name;
        int warehouse_commodities;
        int wh_id;
        boolean wheather_show;
        boolean isGray;

        WarehouseInfo() {
            this.wh_id = 0;
            this.wheather_show = true;
            this.isGray = true;
        }
    }


    static class sort {
        int type;
        boolean up;

        sort(int type, boolean up) {
            this.type = type;
            this.up = up;
        }
    }


    static class truck_table_data {
        Vector<QuickRaceNewGame.TruckInfo> all_lines;

        truck_table_data() {
            this.all_lines = new Vector();
        }
    }


    static class warehouse_table_data {
        Vector<QuickRaceNewGame.WarehouseInfo> all_lines;

        warehouse_table_data() {
            this.all_lines = new Vector();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
