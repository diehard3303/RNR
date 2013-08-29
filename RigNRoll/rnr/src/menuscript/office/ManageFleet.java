/*
 * @(#)ManageFleet.java   13/08/26
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

import rnr.src.menu.Cmenu_TTI;
import rnr.src.menu.Helper;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUEditBox;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUTruckview;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.SMenu;
import rnr.src.menu.menues;
import rnr.src.menuscript.Converts;
import rnr.src.menuscript.table.ICompareLines;
import rnr.src.menuscript.table.ISelectLineListener;
import rnr.src.menuscript.table.ISetupLine;
import rnr.src.menuscript.table.Table;
import rnr.src.rnrconfig.IconMappings;
import rnr.src.rnrcore.Log;
import rnr.src.rnrcore.eng;

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
public class ManageFleet extends ApplicationTab {
    private static final String TAB_NAME = "MANAGE FLEET";
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String TABLE_MYFLEET = "MF - My Fleet - TABLEGROUP - 10 38";
    private static final String RANGER_MYFLEET = "MF - My Fleet - Tableranger";
    private static final String TABLE_MYFLEET_LINE = "Tablegroup - ELEMENTS - My Fleet";
    private static final String[] TABLE_MYFLEET_ELEMENTS = { "MF - My Fleet - MY FLEET - Vehicle",
            "MF - My Fleet - MY FLEET - Price", "MF - My Fleet - DEALER OFFERS - Vehicle",
            "MF - My Fleet - DEALER OFFERS - Price" };
    private static final int MYFLEET_MYVEHICLE = 0;
    private static final int MYFLEET_MYPRICE = 1;
    private static final int MYFLEET_DEALERVEHICLE = 2;
    private static final int MYFLEET_DEALERPRICE = 3;
    private static final String[] MYFLEET_FULL_INFO = {
        "MF - My Fleet Specifications - License Plate - VALUE", "MF - My Fleet Specifications - Type - VALUE",
        "MF - My Fleet Specifications - Condition - VALUE", "MF - My Fleet Specifications - Wear - VALUE",
        "MF - My Fleet Specifications - Speed - VALUE", "MF - My Fleet Specifications - Load Safety - VALUE",
        "MF - My Fleet Specifications - Horse Power - VALUE", "MF - My Fleet Specifications - Color - VALUE",
        "MF - My Fleet Specifications - 3 ROWS"
    };
    private static final int MYFLEET_LICENSE = 0;
    private static final int MYFLEET_TYPE = 1;
    private static final int MYFLEET_CONDITION = 2;
    private static final int MYFLEET_WEAR = 3;
    private static final int MYFLEET_SPEED = 4;
    private static final int MYFLEET_LOADSAFETY = 5;
    private static final int MYFLEET_HORSEPOWER = 6;
    private static final int MYFLEET_COLOR = 7;
    private static final int MYFLEET_VEHICLE_NAME = 8;
    private static final String MYFLEET_PHOTO = "MF - My Fleet Specifications - Photo";
    private static final String MYFLEET_SHOW = "BUTTON - MF - My Fleet Specifications - SHOW";
    private static final String[] MYFLEET_SORT = {
        "BUTTON - MF - My Fleet - Vehicle", "BUTTON - MF - My Fleet - Price",
        "BUTTON - MF - My Fleet Specifications - License Plate", "BUTTON - MF - My Fleet Specifications - Type",
        "BUTTON - MF - My Fleet Specifications - Condition", "BUTTON - MF - My Fleet Specifications - Wear",
        "BUTTON - MF - My Fleet Specifications - Speed", "BUTTON - MF - My Fleet Specifications - Load Safety",
        "BUTTON - MF - My Fleet Specifications - Horse Power", "BUTTON - MF - My Fleet Specifications - Color"
    };
    private static final int SORT_MYFLEET_NAME = 0;
    private static final int SORT_MYFLEET_PRICE = 1;
    private static final int SORT_MYFLEET_LICENCE = 2;
    private static final int SORT_MYFLEET_TYPE = 3;
    private static final int SORT_MYFLEET_CONDITION = 4;
    private static final int SORT_MYFLEET_WEAR = 5;
    private static final int SORT_MYFLEET_SPEED = 6;
    private static final int SORT_MYFLEET_LOADSAFETY = 7;
    private static final int SORT_MYFLEET_HORSEPOWER = 8;
    private static final int SORT_MYFLEET_COLOR = 9;
    private static final String TABLE_DEALERFLEET = "MF - Dealer Offers - TABLEGROUP - 11 38";
    private static final String RANGER_DEALERFLEET = "MF - Dealer Offers - Tableranger";
    private static final String TABLE_DEALERFLEET_LINE = "Tablegroup - ELEMENTS - Dealer Offers";
    private static final String[] TABLE_DEALERFLEET_ELEMENTS = {
        "MF - Dealer Offers - MY FLEET - Vehicle", "MF - Dealer Offers - MY FLEET - Dealer",
        "MF - Dealer Offers - MY FLEET - Discount", "MF - Dealer Offers - MY FLEET - Price",
        "MF - Dealer Offers - DEALER OFFERS - Vehicle", "MF - Dealer Offers - DEALER OFFERS - Dealer",
        "MF - Dealer Offers - DEALER OFFERS - Discount", "MF - Dealer Offers - DEALER OFFERS - Price"
    };
    private static final String[] TABLE_DEALERFLEET_ELEMENTS_text = new String[TABLE_DEALERFLEET_ELEMENTS.length];
    private static final int DEALERFLEET_VEHICLE = 4;
    private static final int DEALERFLEET_DEALER = 5;
    private static final int DEALERFLEET_DISCOUNT = 6;
    private static final int DEALERFLEET_PRICE = 7;
    private static final int DEALERFLEET_MYVEHICLE = 0;
    private static final int DEALERFLEET_MYDEALER = 1;
    private static final int DEALERFLEET_MYDISCOUNT = 2;
    private static final int DEALERFLEET_MYPRICE = 3;
    private static final String[] DEALERS_FULL_INFO = {
        "MF - Dealer Offers Specifications - Price - VALUE", "MF - Dealer Offers Specifications - Type - VALUE",
        "MF - Dealer Offers Specifications - Make - VALUE", "MF - Dealer Offers Specifications - Model - VALUE",
        "MF - Dealer Offers Specifications - Mileage - VALUE", "MF - Dealer Offers Specifications - Suspension - VALUE",
        "MF - Dealer Offers Specifications - Horse Power - VALUE", "MF - Dealer Offers Specifications - Color - VALUE",
        "MF - Dealer Offers Specifications - 3 ROWS"
    };
    private static final int DEALERINF_PRICE = 0;
    private static final int DEALERINF_TYPE = 1;
    private static final int DEALERINF_MAKE = 2;
    private static final int DEALERINF_MODEL = 3;
    private static final int DEALERINF_MILIAGE = 4;
    private static final int DEALERINF_SUSPENSION = 5;
    private static final int DEALERINF_HORSEPOWER = 6;
    private static final int DEALERINF_COLOR = 7;
    private static final int DEALERINF_VEHICLE_NAME = 8;
    private static final String DEALERINF_PHOTO = "MF - Dealer Offers Specifications - Photo";
    private static final String DEALERINF_SHOW =
        "BUTTON - MF - Dealer Offers Specifications - Dealer Offers Specifications - SHOW";
    private static final String[] DEALER_SORT = {
        "BUTTON - MF - Dealer Offers - Vehicle", "BUTTON - MF - Dealer Offers - Dealer",
        "BUTTON - MF - Dealer Offers - Discount", "BUTTON - MF - Dealer Offers - Price",
        "BUTTON - MF - Dealer Offers Specifications - Price", "BUTTON - MF - Dealer Offers Specifications - Type",
        "BUTTON - MF - Dealer Offers Specifications - Make", "BUTTON - MF - Dealer Offers Specifications - Model",
        "BUTTON - MF - Dealer Offers Specifications - Mileage",
        "BUTTON - MF - Dealer Offers Specifications - Suspension",
        "BUTTON - MF - Dealer Offers Specifications - Horse Power", "BUTTON - MF - Dealer Offers Specifications - Color"
    };
    private static final int SORT_DEALER_NAME = 0;
    private static final int SORT_DEALER_DEALERNAME = 1;
    private static final int SORT_DEALER_DISCOUNT = 2;
    private static final int SORT_DEALER_PRICE = 3;
    private static final int SORT_DEALER_PRICE1 = 4;
    private static final int SORT_DEALER_TYPE = 5;
    private static final int SORT_DEALER_MAKE = 6;
    private static final int SORT_DEALER_MODEL = 7;
    private static final int SORT_DEALER_MILEAGE = 8;
    private static final int SORT_DEALER_SUSPENSION = 9;
    private static final int SORT_DEALER_HORSEPOWER = 10;
    private static final int SORT_DEALER_COLOR = 11;
    private static final String[] LINE_MYCAR = { "MF - My Fleet - MY TRUCK - Vehicle",
            "MF - My Fleet - MY TRUCK - Price" };
    private static final String[] ACTION_BUTTONS = { "BUTTON - MF - MY TRUCK", "BUTTON - MF - SELL",
            "BUTTON - MF - PURCHASE", "BUTTON - MF - MY TRUCK - GRAY" };
    private static final String[] ACTION_METHODS = { "onMyTruck", "onSell", "onPurchase" };
    private static final int SELECT_MYTRUCK = 0;
    private static final int SELECT_SELL = 1;
    private static final int SELECT_PURCHASE = 2;
    private static final int SELECT_MYTRUCK_GRAY = 3;
    private static String SEARCH_MENU_GROUP = "Tablegroup - ELEMENTS - MF Filer Menu";
    private static final String[] SEARCH_BUTTONS = {
        "BUTTON PopUP - MF - Dealer Offers Search - Price", "BUTTON PopUP - MF - Dealer Offers Search - Type",
        "BUTTON PopUP - MF - Dealer Offers Search - Make", "BUTTON PopUP - MF - Dealer Offers Search - Model",
        "BUTTON PopUP - MF - Dealer Offers Search - Mileage", "BUTTON PopUP - MF - Dealer Offers Search - Suspension",
        "BUTTON PopUP - MF - Dealer Offers Search - Horse Power", "BUTTON PopUP - MF - Dealer Offers Search - Color"
    };
    private static final String[] SEARCH_TEXTS = {
        "MF - Dealer Offers Search - Price - VALUE", "MF - Dealer Offers Search - Type - VALUE",
        "MF - Dealer Offers Search - Make - VALUE", "MF - Dealer Offers Search - Model - VALUE",
        "MF - Dealer Offers Search - Mileage - VALUE", "MF - Dealer Offers Search - Suspension - VALUE",
        "MF - Dealer Offers Search - Horse Power - VALUE", "MF - Dealer Offers Search - Color - VALUE"
    };
    private static final String SEARCH_MODEL_BUTTON = "MF - Dealer Offers Search - Model - VALUE-Select";
    private static final int SEARCH_PRICE = 0;
    private static final int SEARCH_TYPE = 1;
    private static final int SEARCH_MAKE = 2;
    private static final int SEARCH_MODEL = 3;
    private static final int SEARCH_MILEAGE = 4;
    private static final int SEARCH_SUSPENSION = 5;
    private static final int SEARCH_HORSES = 6;
    private static final int SEARCH_COLOR = 7;
    private static final String[] SUMMARY_TEXTS = {
        "Vehicles To Sell VALUE FLEET", "Vehicles To Purchase VALUE FLEET", "Proceeds VALUE FLEET",
        "Expenses VALUE FLEET", "Overhead VALUE FLEET", "Total VALUE FLEET"
    };
    private static final int SUMMARY_VEHICLESTOSALE = 0;
    private static final int SUMMARY_VEHICLESTOPURCHASE = 1;
    private static final int SUMMARY_PROCEEDS = 2;
    private static final int SUMMARY_EXPENSES = 3;
    private static final int SUMMARY_OVERHEAD = 4;
    private static final int SUMMARY_TOTAL = 5;
    private static final String MACRO_MONEY = "MONEY";
    private static final String MACRO_VALUE = "VALUE";
    private static final String MACRO_SIGN = "SIGN";
    private static final String[] SUMMARY_MACRO = {
        "VALUE", "VALUE", "MONEY", "MONEY", "MONEY", "MONEY"
    };
    private static final String[] SUMMARY_MACRO2 = {
        "", "", "SIGN", "SIGN", "SIGN", "SIGN"
    };
    private static final int[] SUMMARY_MACRO_NUM = {
        1, 1, 2, 2, 2, 2
    };
    private static final String[] TERMOMETRS_NAMES = { "MF - My Fleet Specifications - Condition - VALUE - INDICATOR",
            "MF - My Fleet Specifications - Wear - VALUE - INDICATOR",
            "MF - My Fleet Specifications - Speed - VALUE - INDICATOR",
            "MF - My Fleet Specifications - Load Safety - VALUE - INDICATOR" };
    private static final int TERMOMETR_Condition = 0;
    private static final int TERMOMETR_Wear = 1;
    private static final int TERMOMETR_Speed = 2;
    private static final int TERMOMETR_Load_Safety = 3;
    private final long select_mytruck_button = 0L;
    private final long select_mytruck_button_gray = 0L;
    private long _menu = 0L;
    private VehicleDetails vehicleDetailes = null;
    private MyFleet myFleetTable = null;
    private DealerFleet dealerFleetTable = null;
    private SearchFilter purchaseFilter = null;
    private final String[] initialSummaryTexts = new String[SUMMARY_TEXTS.length];
    private final long[] summaryTexts = new long[SUMMARY_TEXTS.length];
    TermometrClass[] termometrs = null;
    sort myfleet_table_sort = new sort(1, true);
    sort dealerfleet_table_sort = new sort(8, true);
    ManageFlitManager.Filter filter = null;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param parent
     */
    public ManageFleet(long _menu, OfficeMenu parent) {
        super(_menu, "MANAGE FLEET", parent);
        ManageFlitManager.getManageFlitManager().OnEnterMenu();
        init(_menu);
    }

    private void init(long _menu) {
        this._menu = _menu;
        this.myFleetTable = new MyFleet(_menu);
        this.dealerFleetTable = new DealerFleet(_menu);
        this.purchaseFilter = new SearchFilter(_menu);

        for (int i = 0; i < SUMMARY_TEXTS.length; ++i) {
            this.summaryTexts[i] = menues.FindFieldInMenu(_menu, SUMMARY_TEXTS[i]);
            this.initialSummaryTexts[i] = menues.GetFieldText(this.summaryTexts[i]);
        }

        this.vehicleDetailes = new VehicleDetails(_menu);
        this.vehicleDetailes.addListener(new closeVehicleDeatiled());

        long control = menues.FindFieldInMenu(_menu, "CALL OFFICE HELP - MANAGE FLEET");

        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        this.termometrs = new TermometrClass[TERMOMETRS_NAMES.length];

        for (int i = 0; i < TERMOMETRS_NAMES.length; ++i) {
            this.termometrs[i] = new TermometrClass(this._menu, TERMOMETRS_NAMES[i]);
        }

        this.myFleetTable.afterInit();
        this.dealerFleetTable.afterInit();
        this.purchaseFilter.afterInit();
        this.vehicleDetailes.afterInit();
        this.vehicleDetailes.hide();
        update();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean update() {
        if (!(super.update())) {
            return false;
        }

        this.myFleetTable.updateTable();
        this.dealerFleetTable.updateTable();
        refresh_summary();
        makeUpdate();

        return true;
    }

    /**
     * Method description
     *
     */
    @Override
    public void deinit() {
        this.purchaseFilter.deinit();
        this.myFleetTable.table.deinit();
        this.dealerFleetTable.table.deinit();
        this.vehicleDetailes.DeInit();
        ManageFlitManager.getManageFlitManager().OnLeaveMenu();
    }

    private void refresh_summary() {
        ManageFlitManager.Summary summary = ManageFlitManager.getManageFlitManager().GetSummary();

        for (int i = 0; i < this.summaryTexts.length; ++i) {
            KeyPair[] keys = new KeyPair[SUMMARY_MACRO_NUM[i]];

            switch (i) {
             case 0 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.vehiles_to_sell);

                 break;

             case 1 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.vehiles_to_purchase);

                 break;

             case 2 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int) summary.proceeds));

                 if (SUMMARY_MACRO_NUM[i] > 1) {
                     keys[1] = new KeyPair(SUMMARY_MACRO2[i], (Math.abs((int) summary.proceeds) >= 0)
                             ? ""
                             : "-");
                 }

                 break;

             case 3 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int) summary.expenses));

                 if (SUMMARY_MACRO_NUM[i] > 1) {
                     keys[1] = new KeyPair(SUMMARY_MACRO2[i], (Math.abs((int) summary.expenses) >= 0)
                             ? ""
                             : "-");
                 }

                 break;

             case 4 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int) summary.overhead));

                 if (SUMMARY_MACRO_NUM[i] > 1) {
                     keys[1] = new KeyPair(SUMMARY_MACRO2[i], (Math.abs((int) summary.overhead) >= 0)
                             ? ""
                             : "-");
                 }

                 break;

             case 5 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int) summary.total));

                 if (SUMMARY_MACRO_NUM[i] > 1) {
                     keys[1] = new KeyPair(SUMMARY_MACRO2[i], (Math.abs((int) summary.total) >= 0)
                             ? ""
                             : "-");
                 }
            }

            menues.SetFieldText(this.summaryTexts[i], MacroKit.Parse(this.initialSummaryTexts[i], keys));
            menues.UpdateMenuField(menues.ConvertMenuFields(this.summaryTexts[i]));
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void apply() {
        setDirty();

        int err = ManageFlitManager.getManageFlitManager().Apply();

        switch (err) {
         case 2 :
             makeNotEnoughMoney();

             break;

         case 1 :
             makeNotEnoughCars();
        }

        update();
    }

    /**
     * Method description
     *
     */
    @Override
    public void discard() {
        setDirty();
        ManageFlitManager.getManageFlitManager().Discard();
        update();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(0);
        }
    }

    class DealerFleet implements ISetupLine, ISelectLineListener {
        private final String SORT_METHOD = "onSort";
        private final String SHOW_METH = "onShow";
        private ManageFleet.Dealerfleetline selected = null;
        private final ManageFleet.table_data_dealerfleet TABLE_DATA = new ManageFleet.table_data_dealerfleet();
        private long[] fullinfotexts = null;
        private String[] fullinfotexts_string = null;
        private final ManageFleetVehicles vehicles = new ManageFleetVehicles();
        Table table;
        private final long vehicle_photo;

        DealerFleet(long _menu) {
            this.table = new Table(_menu, "MF - Dealer Offers - TABLEGROUP - 11 38",
                                   "MF - Dealer Offers - Tableranger");
            this.table.setSelectionMode(2);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_office.xml",
                                     "Tablegroup - ELEMENTS - Dealer Offers", ManageFleet.TABLE_DEALERFLEET_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            reciveTableData();
            build_tree_data();

            for (String name : ManageFleet.TABLE_DEALERFLEET_ELEMENTS) {
                this.table.initLinesSelection(name);
            }

            this.fullinfotexts = new long[ManageFleet.DEALERS_FULL_INFO.length];
            this.fullinfotexts_string = new String[ManageFleet.DEALERS_FULL_INFO.length];

            for (int i = 0; i < ManageFleet.DEALERS_FULL_INFO.length; ++i) {
                this.fullinfotexts[i] = menues.FindFieldInMenu(_menu, ManageFleet.access$1100()[i]);
                this.fullinfotexts_string[i] = menues.GetFieldText(this.fullinfotexts[i]);
            }

            for (int i = 0; i < ManageFleet.DEALER_SORT.length; ++i) {
                long field = menues.FindFieldInMenu(_menu, ManageFleet.DEALER_SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

                buts.userid = i;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }

            for (int i = 0; i < ManageFleet.TABLE_DEALERFLEET_ELEMENTS.length; ++i) {
                long[] stat = this.table.getLineStatistics_controls(ManageFleet.TABLE_DEALERFLEET_ELEMENTS[i]);

                if ((stat == null) || (stat.length == 0)) {
                    Log.menu("ERRORR. MyFleet table. Has no field named " + ManageFleet.TABLE_MYFLEET_ELEMENTS[i]);
                }

                ManageFleet.TABLE_DEALERFLEET_ELEMENTS_text[i] = menues.GetFieldText(stat[0]);
            }

            menues.SetScriptOnControl(_menu,
                                      menues.ConvertMenuFields(menues.FindFieldInMenu(_menu,
                                          ManageFleet.ACTION_BUTTONS[2])), this, ManageFleet.ACTION_METHODS[2], 4L);
            menues.SetScriptOnControl(
                _menu,
                menues.ConvertMenuFields(
                    menues.FindFieldInMenu(
                        _menu,
                        "BUTTON - MF - Dealer Offers Specifications - Dealer Offers Specifications - SHOW")), this,
                            "onShow", 4L);
            this.vehicle_photo = menues.FindFieldInMenu(_menu, "MF - Dealer Offers Specifications - Photo");
        }

        /**
         * Method description
         *
         */
        public void afterInit() {
            this.table.afterInit();
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onShow(long _menu, MENUsimplebutton_field button) {
            menues.SetShowField(menues.GetBackMenu(_menu), false);
            ManageFleet.this.vehicleDetailes.show(this.vehicles.get(this.selected.id), this.selected.id, false);
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onPurchase(long _menu, MENUsimplebutton_field button) {
            ManageFleet.this.setDirty();

            Vector res = new Vector();

            for (ManageFleet.Dealerfleetline line : this.TABLE_DATA.all_lines) {
                if ((line.selected) && (line.wheather_show)) {
                    res.add(line.id);
                }
            }

            if (!(res.isEmpty())) {
                ManageFlitManager.getManageFlitManager().I_Purchase(res);
            }

            ManageFleet.this.update();
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
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(1,
                         !(ManageFleet.this.dealerfleet_table_sort.up));

                 break;

             case 1 :
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(2,
                         !(ManageFleet.this.dealerfleet_table_sort.up));

                 break;

             case 2 :
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(3,
                         !(ManageFleet.this.dealerfleet_table_sort.up));

                 break;

             case 3 :
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(4,
                         !(ManageFleet.this.dealerfleet_table_sort.up));

                 break;

             case 4 :
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(4,
                         !(ManageFleet.this.dealerfleet_table_sort.up));

                 break;

             case 5 :
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(5,
                         !(ManageFleet.this.dealerfleet_table_sort.up));

                 break;

             case 6 :
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(6,
                         !(ManageFleet.this.dealerfleet_table_sort.up));

                 break;

             case 7 :
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(7,
                         !(ManageFleet.this.dealerfleet_table_sort.up));

                 break;

             case 8 :
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(8,
                         !(ManageFleet.this.dealerfleet_table_sort.up));

                 break;

             case 9 :
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(9,
                         !(ManageFleet.this.dealerfleet_table_sort.up));

                 break;

             case 10 :
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(10,
                         !(ManageFleet.this.dealerfleet_table_sort.up));

                 break;

             case 11 :
                 ManageFleet.this.dealerfleet_table_sort = new ManageFleet.sort(11,
                         !(ManageFleet.this.dealerfleet_table_sort.up));
            }

            updateTable();
            ManageFleet.this.refresh_summary();
        }

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

        private void buildvoidcells() {
            if (this.TABLE_DATA.all_lines.size() < this.table.getNumRows()) {
                int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();

                for (int i = 0; i < dif; ++i) {
                    ManageFleet.Dealerfleetline data = new ManageFleet.Dealerfleetline();

                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
            } else {
                int count_good_data = 0;
                Iterator iter = this.TABLE_DATA.all_lines.iterator();

                while ((iter.hasNext()) && (((ManageFleet.Dealerfleetline) iter.next()).wheather_show)) {
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

        private void reciveTableData() {
            if (eng.noNative) {
                return;
            }

            this.TABLE_DATA.all_lines.clear();
            this.vehicles.clear();

            Vector drids = ManageFlitManager.getManageFlitManager().GetDealerVehiclesList(ManageFleet.this.filter,
                               ManageFleet.this.dealerfleet_table_sort.type,
                               ManageFleet.this.dealerfleet_table_sort.up);

            for (ManageFlitManager.ShotDealerVehicleInfo inf : drids) {
                ManageFleet.Dealerfleetline data = new ManageFleet.Dealerfleetline();

                data.id = inf.id;
                data.isGray = inf.isGray;
                data.price = inf.price;
                data.vehicle_name = inf.vehicle_name;
                data.discount = inf.discount;
                data.dealer_name = inf.dealer_name;
                data.wheather_show = true;
                this.TABLE_DATA.all_lines.add(data);
                this.vehicles.add(inf.id);
            }

            buildvoidcells();
        }

        private void build_tree_data() {
            this.table.reciveTreeData(convertTableData());
        }

        private void build_tree_data(ICompareLines comparator) {
            this.table.reciveTreeData(convertTableData(), comparator);
        }

        /**
         * Method description
         *
         */
        public void updateTable() {
            reciveTableData();
            build_tree_data(new CompareDealerFleet());
            this.table.refresh_no_select();
        }

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            ManageFleet.Dealerfleetline line = (ManageFleet.Dealerfleetline) table_node.item;

            if (!(line.wheather_show)) {
                menues.SetShowField(obj.nativePointer, false);

                return;
            }

            int position = this.table.getMarkedPosition(obj.nativePointer);

            switch (position) {
             case 4 :
                 if (line.isGray) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.vehicle_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 5 :
                 if (line.isGray) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.dealer_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 6 :
                 if (line.isGray) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("VALUE", "" + (int) (100.0D * line.discount));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer,
                                     MacroKit.Parse(ManageFleet.TABLE_DEALERFLEET_ELEMENTS_text[6], macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 7 :
                 if (line.isGray) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.price));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer,
                                     MacroKit.Parse(ManageFleet.TABLE_DEALERFLEET_ELEMENTS_text[7], macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 0 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.vehicle_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 1 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.dealer_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 2 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("VALUE", "" + (int) (100.0D * line.discount));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer,
                                     MacroKit.Parse(ManageFleet.TABLE_DEALERFLEET_ELEMENTS_text[2], macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 3 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.price));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer,
                                     MacroKit.Parse(ManageFleet.TABLE_DEALERFLEET_ELEMENTS_text[3], macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
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

        /**
         * Method description
         *
         *
         * @param table
         * @param line
         */
        @Override
        public void selectLineEvent(Table table, int line) {
            if (this.TABLE_DATA.all_lines.isEmpty()) {
                return;
            }

            for (ManageFleet.Dealerfleetline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }

            ManageFleet.Dealerfleetline data = (ManageFleet.Dealerfleetline) table.getItemOnLine(line).item;

            data.selected = true;
            this.selected = data;
            updateSelectedInfo();
        }

        /**
         * Method description
         *
         *
         * @param table
         * @param lines
         */
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
            if (this.TABLE_DATA.all_lines.isEmpty()) {
                return;
            }

            for (ManageFleet.Dealerfleetline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }

            for (Cmenu_TTI item : lines) {
                if (item.item != null) {
                    ManageFleet.Dealerfleetline data = (ManageFleet.Dealerfleetline) item.item;

                    data.selected = true;
                }
            }

            this.selected = ((ManageFleet.Dealerfleetline) table.getSelectedData().item);
            updateSelectedInfo();
        }

        private void updateSelectedInfo() {
            if (null == this.selected.id) {
                for (int i = 0; i < this.fullinfotexts.length; ++i) {
                    menues.SetFieldText(this.fullinfotexts[i], "---");
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
                }

                menues.SetShowField(this.vehicle_photo, false);

                return;
            }

            ManageFlitManager.FullDealerVehicleInfo inf =
                ManageFlitManager.getManageFlitManager().GetDealerVehiclesInfo(this.selected.id);
            KeyPair[] macro = new KeyPair[1];

            for (int i = 0; i < this.fullinfotexts.length; ++i) {
                switch (i) {
                 case 0 :
                     macro[0] = new KeyPair("MONEY", Helper.convertMoney((int) inf.price));
                     menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_string[i], macro));

                     break;

                 case 1 :
                     menues.SetFieldText(this.fullinfotexts[i], inf.type);

                     break;

                 case 2 :
                     menues.SetFieldText(this.fullinfotexts[i], inf.make);

                     break;

                 case 3 :
                     menues.SetFieldText(this.fullinfotexts[i], inf.model);

                     break;

                 case 4 :
                     macro[0] = new KeyPair("VALUE", Helper.convertMoney((int) inf.mileage));
                     menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_string[i], macro));

                     break;

                 case 5 :
                     menues.SetFieldText(this.fullinfotexts[i], inf.suspension);

                     break;

                 case 6 :
                     macro[0] = new KeyPair("VALUE", Helper.convertMoney((int) inf.horsepower));
                     menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_string[i], macro));

                     break;

                 case 7 :
                     menues.SetFieldText(this.fullinfotexts[i], inf.color);

                     break;

                 case 8 :
                     menues.SetFieldText(this.fullinfotexts[i], inf.vehicle_name);
                }

                menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
            }

            menues.SetShowField(this.vehicle_photo, true);
            IconMappings.remapVehicleIcon("" + inf.vehicle_picture, this.vehicle_photo);
        }

        class CompareDealerFleet implements ICompareLines {

            /**
             * Method description
             *
             *
             * @param object1
             * @param object2
             *
             * @return
             */
            @Override
            public boolean equal(Object object1, Object object2) {
                if ((object1 == null) || (object2 == null)) {
                    return false;
                }

                ManageFleet.Dealerfleetline line1 = (ManageFleet.Dealerfleetline) object1;
                ManageFleet.Dealerfleetline line2 = (ManageFleet.Dealerfleetline) object2;

                return ((line1.id != null) && (line2.id != null) && (line1.id.id == line2.id.id));
            }
        }
    }


    static class Dealerfleetline {
        ManageFlitManager.VehicleId id;
        String vehicle_name;
        String dealer_name;
        float discount;
        float price;
        boolean isGray;
        boolean selected;
        boolean wheather_show;

        Dealerfleetline() {
            this.selected = false;
        }
    }


    class MyFleet implements ISetupLine, ISelectLineListener {
        private final String SORT_METHOD = "onSort";
        private final String MYCAR_METH = "onMycar";
        private final String SHOW_METH = "onShow";
        ManageFlitManager.VehicleId selected = null;
        ManageFlitManager.VehicleId focued_selected = null;
        private long[] fullinfotexts = null;
        private String[] fullinfotexts_native = null;
        private final ManageFleet.table_data_myfleet TABLE_DATA = new ManageFleet.table_data_myfleet();
        private final long[] mycar_lines = new long[ManageFleet.LINE_MYCAR.length];
        private ManageFleet.Myfleetline mycarinfo = null;
        private long vehicle_photo = 0L;
        private final ManageFleetVehicles vehicles = new ManageFleetVehicles();
        Table table;
        private final String myvehicles_price;
        private final String dealers_price;
        private String mycarpriceText;

        MyFleet(long _menu) {
            this.table = new Table(_menu, "MF - My Fleet - TABLEGROUP - 10 38", "MF - My Fleet - Tableranger");
            this.table.setSelectionMode(2);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_office.xml", "Tablegroup - ELEMENTS - My Fleet",
                                     ManageFleet.TABLE_MYFLEET_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            reciveTableData();
            build_tree_data();

            for (String name : ManageFleet.TABLE_MYFLEET_ELEMENTS) {
                this.table.initLinesSelection(name);
            }

            this.fullinfotexts = new long[ManageFleet.MYFLEET_FULL_INFO.length];
            this.fullinfotexts_native = new String[ManageFleet.MYFLEET_FULL_INFO.length];

            for (int i = 0; i < ManageFleet.MYFLEET_FULL_INFO.length; ++i) {
                this.fullinfotexts[i] = menues.FindFieldInMenu(_menu, ManageFleet.access$200()[i]);
            }

            for (int i = 0; i < ManageFleet.MYFLEET_SORT.length; ++i) {
                long field = menues.FindFieldInMenu(_menu, ManageFleet.MYFLEET_SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

                buts.userid = i;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }

            long[] stat_myvehicles_price = this.table.getLineStatistics_controls(ManageFleet.TABLE_MYFLEET_ELEMENTS[1]);
            long[] stat_dealers_price = this.table.getLineStatistics_controls(ManageFleet.TABLE_MYFLEET_ELEMENTS[3]);

            if ((stat_myvehicles_price == null) || (stat_myvehicles_price.length < 1)) {
                Log.menu("ERRORR. MyFleet table. Has no field named " + ManageFleet.TABLE_MYFLEET_ELEMENTS[1]);
            }

            if ((stat_dealers_price == null) || (stat_dealers_price.length < 1)) {
                Log.menu("ERRORR. MyFleet table. Has no field named " + ManageFleet.TABLE_MYFLEET_ELEMENTS[3]);
            }

            this.myvehicles_price = menues.GetFieldText(stat_myvehicles_price[0]);
            this.dealers_price = menues.GetFieldText(stat_dealers_price[0]);

            for (int i = 0; i < ManageFleet.LINE_MYCAR.length; ++i) {
                this.mycar_lines[i] = menues.FindFieldInMenu(_menu, ManageFleet.access$000()[i]);
                menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.mycar_lines[i]), this, "onMycar", 2L);

                if (i == 1) {
                    this.mycarpriceText = menues.GetFieldText(this.mycar_lines[i]);
                }
            }

            menues.SetScriptOnControl(_menu,
                                      menues.ConvertMenuFields(menues.FindFieldInMenu(_menu,
                                          ManageFleet.ACTION_BUTTONS[0])), this, ManageFleet.ACTION_METHODS[0], 4L);
            menues.SetScriptOnControl(_menu,
                                      menues.ConvertMenuFields(menues.FindFieldInMenu(_menu,
                                          ManageFleet.ACTION_BUTTONS[1])), this, ManageFleet.ACTION_METHODS[1], 4L);
            menues.SetScriptOnControl(_menu,
                                      menues.ConvertMenuFields(menues.FindFieldInMenu(_menu,
                                          "BUTTON - MF - My Fleet Specifications - SHOW")), this, "onShow", 4L);
            this.vehicle_photo = menues.FindFieldInMenu(_menu, "MF - My Fleet Specifications - Photo");
            ManageFleet.access$602(ManageFleet.this, menues.FindFieldInMenu(_menu, ManageFleet.ACTION_BUTTONS[0]));
            ManageFleet.access$702(ManageFleet.this, menues.FindFieldInMenu(_menu, ManageFleet.ACTION_BUTTONS[3]));
        }

        /**
         * Method description
         *
         */
        public void afterInit() {
            for (int i = 0; i < ManageFleet.MYFLEET_FULL_INFO.length; ++i) {
                if (this.fullinfotexts[i] != 0L) {
                    this.fullinfotexts_native[i] = menues.GetFieldText(this.fullinfotexts[i]);
                }
            }

            if (ManageFleet.this.select_mytruck_button_gray != 0L) {
                menues.SetShowField(ManageFleet.this.select_mytruck_button_gray, false);
                menues.SetBlindess(ManageFleet.this.select_mytruck_button_gray, true);
                menues.SetIgnoreEvents(ManageFleet.this.select_mytruck_button_gray, true);
            }

            this.table.afterInit();
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onShow(long _menu, MENUsimplebutton_field button) {
            if ((null != this.focued_selected) && (this.focued_selected.id != 0)) {
                menues.SetShowField(menues.GetBackMenu(_menu), false);
                ManageFleet.this.vehicleDetailes.show(this.vehicles.get(this.focued_selected), this.focued_selected,
                        true);
            }
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onSell(long _menu, MENUsimplebutton_field button) {
            ManageFleet.this.setDirty();

            Vector res = new Vector();

            for (ManageFleet.Myfleetline line : this.TABLE_DATA.all_lines) {
                if ((line.selected) && (line.wheather_show) && (line.id != null) && (line.id.id != 0)) {
                    res.add(line.id);
                }
            }

            if (!(res.isEmpty())) {
                ManageFlitManager.getManageFlitManager().I_Sell(res);
            }

            ManageFleet.this.update();
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onMyTruck(long _menu, MENUsimplebutton_field button) {
            ManageFleet.this.setDirty();

            if ((null == this.selected) || (this.selected.id == 0)
                    || (!(ManageFlitManager.getManageFlitManager().Can_I_Take()))) {
                return;
            }

            ManageFlitManager.getManageFlitManager().I_Take(this.selected);
            updateTable();
            ManageFleet.this.refresh_summary();
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onMycar(long _menu, MENUbutton_field button) {
            for (int i = 0; i < this.mycar_lines.length; ++i) {
                menues.SetFieldState(this.mycar_lines[i], 1);
            }

            updateSelectedInfo(this.mycarinfo.id);
        }

        private void updateMyCar() {
            if (eng.noNative) {
                return;
            }

            ManageFlitManager.ShotMyVehicleInfo inf = ManageFlitManager.getManageFlitManager().GetMyVehicleInfo();

            this.mycarinfo = new ManageFleet.Myfleetline();
            this.mycarinfo.id = inf.id;

            if ((inf.id != null) && (inf.id.id != 0)) {
                this.mycarinfo.price = inf.price;
                this.mycarinfo.vehicle_name = inf.vehicle_name;

                for (int i = 0; i < this.mycar_lines.length; ++i) {
                    switch (i) {
                     case 0 :
                         menues.SetFieldText(this.mycar_lines[i], this.mycarinfo.vehicle_name);
                         menues.UpdateMenuField(menues.ConvertMenuFields(this.mycar_lines[i]));

                         break;

                     case 1 :
                         KeyPair[] macro = new KeyPair[1];

                         macro[0] = new KeyPair("MONEY", Helper.convertMoney((int) this.mycarinfo.price));
                         menues.SetShowField(this.mycar_lines[i], true);
                         menues.SetFieldText(this.mycar_lines[i], MacroKit.Parse(this.mycarpriceText, macro));
                         menues.UpdateMenuField(menues.ConvertMenuFields(this.mycar_lines[i]));
                    }
                }
            } else {
                this.mycarinfo.price = 0.0F;
                this.mycarinfo.vehicle_name = "---";

                for (int i = 0; i < this.mycar_lines.length; ++i) {
                    switch (i) {
                     case 0 :
                         menues.SetFieldText(this.mycar_lines[i], "---");
                         menues.UpdateMenuField(menues.ConvertMenuFields(this.mycar_lines[i]));

                         break;

                     case 1 :
                         menues.SetShowField(this.mycar_lines[i], true);
                         menues.SetFieldText(this.mycar_lines[i], "---");
                         menues.UpdateMenuField(menues.ConvertMenuFields(this.mycar_lines[i]));
                    }
                }
            }

            this.vehicles.add(this.mycarinfo.id);
        }

        private void deselectMyCar() {
            for (int i = 0; i < this.mycar_lines.length; ++i) {
                menues.SetFieldState(this.mycar_lines[i], 0);
            }
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
                 ManageFleet.this.myfleet_table_sort = new ManageFleet.sort(1,
                         !(ManageFleet.this.myfleet_table_sort.up));

                 break;

             case 1 :
                 ManageFleet.this.myfleet_table_sort = new ManageFleet.sort(2,
                         !(ManageFleet.this.myfleet_table_sort.up));

                 break;

             case 2 :
                 ManageFleet.this.myfleet_table_sort = new ManageFleet.sort(3,
                         !(ManageFleet.this.myfleet_table_sort.up));

                 break;

             case 3 :
                 ManageFleet.this.myfleet_table_sort = new ManageFleet.sort(4,
                         !(ManageFleet.this.myfleet_table_sort.up));

                 break;

             case 4 :
                 ManageFleet.this.myfleet_table_sort = new ManageFleet.sort(5,
                         !(ManageFleet.this.myfleet_table_sort.up));

                 break;

             case 5 :
                 ManageFleet.this.myfleet_table_sort = new ManageFleet.sort(6,
                         !(ManageFleet.this.myfleet_table_sort.up));

                 break;

             case 6 :
                 ManageFleet.this.myfleet_table_sort = new ManageFleet.sort(7,
                         !(ManageFleet.this.myfleet_table_sort.up));

                 break;

             case 7 :
                 ManageFleet.this.myfleet_table_sort = new ManageFleet.sort(8,
                         !(ManageFleet.this.myfleet_table_sort.up));

                 break;

             case 8 :
                 ManageFleet.this.myfleet_table_sort = new ManageFleet.sort(9,
                         !(ManageFleet.this.myfleet_table_sort.up));

                 break;

             case 9 :
                 ManageFleet.this.myfleet_table_sort = new ManageFleet.sort(10,
                         !(ManageFleet.this.myfleet_table_sort.up));
            }

            updateTable();
            ManageFleet.this.refresh_summary();
        }

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

        private void buildvoidcells() {
            if (this.TABLE_DATA.all_lines.size() < this.table.getNumRows()) {
                int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();

                for (int i = 0; i < dif; ++i) {
                    ManageFleet.Myfleetline data = new ManageFleet.Myfleetline();

                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
            } else {
                int count_good_data = 0;
                Iterator iter = this.TABLE_DATA.all_lines.iterator();

                while ((iter.hasNext()) && (((ManageFleet.Myfleetline) iter.next()).wheather_show)) {
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

        private void build_tree_data() {
            this.table.reciveTreeData(convertTableData());
        }

        private void build_tree_data(ICompareLines comparator) {
            this.table.reciveTreeData(convertTableData(), comparator);
        }

        private void reciveTableData() {
            if (eng.noNative) {
                return;
            }

            this.TABLE_DATA.all_lines.clear();
            this.vehicles.clear();

            Vector drids =
                ManageFlitManager.getManageFlitManager().GetMyVehiclesList(ManageFleet.this.myfleet_table_sort.type,
                    ManageFleet.this.myfleet_table_sort.up);

            for (ManageFlitManager.ShotMyVehicleInfo inf : drids) {
                ManageFleet.Myfleetline data = new ManageFleet.Myfleetline();

                data.id = inf.id;
                data.isGray = inf.isGray;
                data.price = inf.price;
                data.vehicle_name = inf.vehicle_name;
                data.wheather_show = true;
                this.TABLE_DATA.all_lines.add(data);
                this.vehicles.add(inf.id);
            }

            if (null != this.mycarinfo) {
                this.vehicles.add(this.mycarinfo.id);
            }

            buildvoidcells();
        }

        /**
         * Method description
         *
         */
        public void updateTable() {
            if (ManageFlitManager.getManageFlitManager().Can_I_Take()) {
                if (ManageFleet.this.select_mytruck_button_gray != 0L) {
                    menues.SetShowField(ManageFleet.this.select_mytruck_button_gray, false);
                }

                if (ManageFleet.this.select_mytruck_button != 0L) {
                    menues.SetShowField(ManageFleet.this.select_mytruck_button, true);
                }
            } else {
                if (ManageFleet.this.select_mytruck_button_gray != 0L) {
                    menues.SetShowField(ManageFleet.this.select_mytruck_button_gray, true);
                }

                if (ManageFleet.this.select_mytruck_button != 0L) {
                    menues.SetShowField(ManageFleet.this.select_mytruck_button, false);
                }
            }

            updateMyCar();
            this.selected = null;
            reciveTableData();
            build_tree_data(new CompareMyFleet());
            this.table.refresh_no_select();
        }

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            ManageFleet.Myfleetline line = (ManageFleet.Myfleetline) table_node.item;

            if (!(line.wheather_show)) {
                menues.SetShowField(obj.nativePointer, false);

                return;
            }

            int position = this.table.getMarkedPosition(obj.nativePointer);

            switch (position) {
             case 0 :
                 if (line.isGray) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.vehicle_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 1 :
                 if (line.isGray) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.price));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.myvehicles_price, macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 2 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.vehicle_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 3 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.price));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.dealers_price, macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));
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

        /**
         * Method description
         *
         *
         * @param table
         * @param line
         */
        @Override
        public void selectLineEvent(Table table, int line) {
            if (this.TABLE_DATA.all_lines.isEmpty()) {
                return;
            }

            for (ManageFleet.Myfleetline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }

            ManageFleet.Myfleetline data = (ManageFleet.Myfleetline) table.getItemOnLine(line).item;

            data.selected = true;
            this.selected = data.id;
            deselectMyCar();
            updateSelectedInfo(this.selected);
        }

        /**
         * Method description
         *
         *
         * @param table
         * @param lines
         */
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
            if (this.TABLE_DATA.all_lines.isEmpty()) {
                return;
            }

            for (ManageFleet.Myfleetline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }

            for (Cmenu_TTI item : lines) {
                if (item.item != null) {
                    ManageFleet.Myfleetline data = (ManageFleet.Myfleetline) item.item;

                    data.selected = true;
                }
            }

            this.selected = ((ManageFleet.Myfleetline) table.getSelectedData().item).id;
            deselectMyCar();
            updateSelectedInfo(this.selected);
        }

        private void updateSelectedInfo(ManageFlitManager.VehicleId id) {
            this.focued_selected = id;

            if ((null == id) || (id.id == 0)) {
                for (int i = 0; i < this.fullinfotexts.length; ++i) {
                    menues.SetFieldText(this.fullinfotexts[i], "---");
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
                }

                for (int i = 0; i < ManageFleet.this.termometrs.length; ++i) {
                    ManageFleet.this.termometrs[i].Update(0);
                }

                menues.SetShowField(this.vehicle_photo, false);

                return;
            }

            ManageFlitManager.FullMyVehicleInfo inf = ManageFlitManager.getManageFlitManager().GetMyVehiclesInfo(id);

            for (int i = 0; i < this.fullinfotexts.length; ++i) {
                switch (i) {
                 case 0 :
                     menues.SetFieldText(this.fullinfotexts[i], inf.license_plate);

                     break;

                 case 1 :
                     menues.SetFieldText(this.fullinfotexts[i], inf.type);

                     break;

                 case 2 :
                     KeyPair[] macro0 = { new KeyPair("VALUE",
                                                      Converts.ConvertNumeric((int) (100.0D * inf.condition))) };

                     menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_native[i], macro0));

                     break;

                 case 3 :
                     KeyPair[] macro1 = { new KeyPair("VALUE", Converts.ConvertNumeric((int) (100.0D * inf.wear))) };

                     menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_native[i], macro1));

                     break;

                 case 4 :
                     KeyPair[] macro2 = { new KeyPair("VALUE", Converts.ConvertNumeric((int) (100.0D * inf.speed))) };

                     menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_native[i], macro2));

                     break;

                 case 5 :
                     KeyPair[] macro3 = { new KeyPair("VALUE",
                                                      Converts.ConvertNumeric((int) (100.0D * inf.load_safety))) };

                     menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_native[i], macro3));

                     break;

                 case 6 :
                     KeyPair[] macro4 = { new KeyPair("VALUE", Converts.ConvertNumeric((int) inf.horse_power)) };

                     menues.SetFieldText(this.fullinfotexts[i], MacroKit.Parse(this.fullinfotexts_native[i], macro4));

                     break;

                 case 7 :
                     menues.SetFieldText(this.fullinfotexts[i], inf.color);

                     break;

                 case 8 :
                     menues.SetFieldText(this.fullinfotexts[i], inf.vehicle_name);
                }

                menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
            }

            for (int i = 0; i < ManageFleet.this.termometrs.length; ++i) {
                switch (i) {
                 case 0 :
                     ManageFleet.this.termometrs[i].Update((int) (100.0D * inf.condition));

                     break;

                 case 1 :
                     ManageFleet.this.termometrs[i].Update((int) (100.0D * inf.wear));

                     break;

                 case 2 :
                     ManageFleet.this.termometrs[i].Update((int) (100.0D * inf.speed));

                     break;

                 case 3 :
                     ManageFleet.this.termometrs[i].Update((int) (100.0D * inf.load_safety));
                }
            }

            menues.SetShowField(this.vehicle_photo, true);
            IconMappings.remapVehicleIcon("" + inf.vehicle_picture, this.vehicle_photo);
        }

        class CompareMyFleet implements ICompareLines {

            /**
             * Method description
             *
             *
             * @param object1
             * @param object2
             *
             * @return
             */
            @Override
            public boolean equal(Object object1, Object object2) {
                if ((object1 == null) || (object2 == null)) {
                    return false;
                }

                ManageFleet.Myfleetline line1 = (ManageFleet.Myfleetline) object1;
                ManageFleet.Myfleetline line2 = (ManageFleet.Myfleetline) object2;

                return ((line1.id != null) && (line2.id != null) && (line1.id.id == line2.id.id));
            }
        }
    }


    static class Myfleetline {
        ManageFlitManager.VehicleId id;
        String vehicle_name;
        float price;
        boolean isGray;
        boolean selected;
        boolean wheather_show;

        Myfleetline() {
            this.selected = false;
        }
    }


    class SearchFilter implements IChoosedata {
        private static final int _IDanimateClose = 1;
        private final String SEARCH_METH = "onSearch";
        private final String SEARCHMODEL_METH = "onModelSearch";
        private final String SEARCHMODEL_ENTER_METH = "onModelEnter";
        private final String SEARCHMODEL_DISMISS_METH = "onModelDismiss";
        private final String ANIMATE_METH = "onAnimate";
        private PopUpSearch search = null;
        private long[] texts = null;
        private long edit_box = 0L;
        private int[] shifts = null;
        private boolean pend_close = false;
        private boolean pend_edit = false;
        private boolean editing = false;
        private boolean pend_exit_edit = false;
        private int filter_current = 0;
        private int filter_choose = 0;
        private String filter_text = "";
        private ManageFlitManager.Filter filter = null;
        private boolean canEdit = false;

        SearchFilter(long _menu) {
            this.search = new PopUpSearch(_menu, ManageFleet.SEARCH_MENU_GROUP);
            this.search.addListener(this);
            this.shifts = new int[ManageFleet.SEARCH_BUTTONS.length];

            for (int i = 0; i < ManageFleet.SEARCH_BUTTONS.length; ++i) {
                long sbutt = menues.FindFieldInMenu(_menu, ManageFleet.SEARCH_BUTTONS[i]);
                MENUsimplebutton_field button = menues.ConvertSimpleButton(sbutt);

                button.userid = i;
                this.shifts[i] = button.poy;
                menues.SetScriptOnControl(_menu, button, this, "onSearch", 4L);
                menues.UpdateField(button);
            }

            for (int i = this.shifts.length - 1; i >= 0; --i) {
                this.shifts[i] -= this.shifts[0];
            }

            menues.SetScriptObjectAnimation(0L, 1L, this, "onAnimate");
            this.texts = new long[ManageFleet.SEARCH_TEXTS.length];

            for (int i = 0; i < ManageFleet.SEARCH_TEXTS.length; ++i) {
                this.texts[i] = menues.FindFieldInMenu(_menu, ManageFleet.access$1600()[i]);

                if (i == 3) {
                    menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.texts[i]), this, "onModelDismiss",
                                              19L);
                    menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.texts[i]), this, "onModelEnter",
                                              16L);
                }

                String[] textdata = getFilterValuesShort(i);

                if (textdata.length > 0) {
                    menues.SetFieldText(this.texts[i], textdata[(textdata.length - 1)]);
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[i]));
                }
            }

            this.edit_box = menues.FindFieldInMenu(_menu, "MF - Dealer Offers Search - Model - VALUE-Select");
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.edit_box), this, "onModelSearch", 4L);
            this.filter = new ManageFlitManager.Filter();
            this.filter.fileds = new int[8];
            this.filter.model_user = "";
        }

        /**
         * Method description
         *
         */
        public void afterInit() {
            menues.SetShowField(this.edit_box, true);
            menues.SetShowField(this.texts[3], false);
        }

        /**
         * Method description
         *
         */
        public void deinit() {
            menues.StopScriptAnimation(1L);
            this.search.removeListener(this);
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param field
         */
        public void onSearch(long _menu, MENUsimplebutton_field field) {
            this.search.show(this.shifts[field.userid]);

            String[] data = getFilterValues(field.userid);

            this.search.setData(data);
            this.filter_current = field.userid;

            if ((3 == field.userid) && (this.canEdit)) {
                this.pend_edit = true;
            }
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param field
         */
        public void onModelSearch(long _menu, MENUsimplebutton_field field) {
            if (!(this.canEdit)) {
                return;
            }

            this.pend_edit = true;
            this.search.show(this.shifts[3]);
            this.search.setData(getFilterValues(3));
            this.filter_current = 3;
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param field
         */
        public void onModelEnter(long _menu, MENUEditBox field) {
            selectData(this.search.getFocusedData());
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param field
         */
        public void onModelDismiss(long _menu, MENUEditBox field) {
            selectData(this.search.getFocusedData());
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param field
         */
        public void onChange(long _menu, long field) {
            this.filter_choose = -1;

            String newtext = menues.GetFieldText(field);

            if (newtext.compareTo(this.filter_text) != 0) {
                this.filter_text = newtext;
                this.search.setData(getModelFilterValues());
            }
        }

        /**
         * Method description
         *
         *
         * @param cookie
         * @param time
         */
        public void onAnimate(long cookie, double time) {
            this.canEdit = true;

            if (this.pend_close) {
                this.editing = false;
                this.pend_exit_edit = true;
                this.search.hide();
                updateFilter();
            }

            this.pend_close = false;

            if ((this.canEdit) && (this.pend_edit)) {
                menues.SetShowField(this.edit_box, false);
                menues.SetShowField(this.texts[3], true);
                menues.setFocusWindow(menues.ConvertWindow(menues.GetBackMenu(ManageFleet.this._menu)).ID);
                menues.setfocuscontrolonmenu(ManageFleet.this._menu, this.texts[3]);
                this.editing = true;
            }

            this.pend_edit = false;

            if (this.pend_exit_edit) {
                menues.SetShowField(this.edit_box, true);
                menues.SetShowField(this.texts[3], false);
                this.editing = false;
            }

            this.pend_exit_edit = false;

            if (this.editing) {
                onChange(ManageFleet.this._menu, this.texts[3]);
                this.search.show(this.shifts[3]);
                menues.setFocusWindow(menues.ConvertWindow(menues.GetBackMenu(ManageFleet.this._menu)).ID);
                menues.setfocuscontrolonmenu(ManageFleet.this._menu, this.texts[3]);
                this.pend_edit = true;
            }
        }

        /**
         * Method description
         *
         *
         * @param data
         */
        @Override
        public void selectData(int data) {
            this.pend_close = true;
            this.filter_choose = data;

            if (this.filter_current == 3) {
                this.filter_text = getSelectedString();
                menues.SetFieldText(this.texts[3], this.filter_text);
                menues.SetFieldText(this.edit_box, this.filter_text);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[3]));
                menues.UpdateMenuField(menues.ConvertMenuFields(this.edit_box));
                this.filter_text = "";
            }
        }

        private String getSelectedString() {
            int ch = this.filter_choose;
            int flt = 0;

            switch (this.filter_current) {
             case 0 :
                 flt = 0;

                 break;

             case 1 :
                 flt = 1;

                 break;

             case 2 :
                 flt = 2;

                 break;

             case 3 :
                 flt = 3;

                 break;

             case 4 :
                 flt = 4;

                 break;

             case 5 :
                 flt = 6;

                 break;

             case 6 :
                 flt = 5;

                 break;

             case 7 :
                 flt = 7;
            }

            Vector fields = ManageFlitManager.getManageFlitManager().GetFilterFields(flt);
            int res_choose = 0;

            for (ManageFlitManager.FilterField f : fields) {
                ch -= ((f.show_me)
                       ? 1
                       : 0);

                if (ch < 0) {
                    break;
                }

                ++res_choose;
            }

            return getFilterValuesShort(this.filter_current)[res_choose];
        }

        void updateFilter() {
            int ch = this.filter_choose;
            int flt = 0;

            switch (this.filter_current) {
             case 0 :
                 flt = 0;

                 break;

             case 1 :
                 flt = 1;

                 break;

             case 2 :
                 flt = 2;

                 break;

             case 3 :
                 flt = 3;

                 break;

             case 4 :
                 flt = 4;

                 break;

             case 5 :
                 flt = 6;

                 break;

             case 6 :
                 flt = 5;

                 break;

             case 7 :
                 flt = 7;
            }

            Vector fields = ManageFlitManager.getManageFlitManager().GetShortFilterFields(flt);
            int res_choose = 0;

            for (ManageFlitManager.FilterField f : fields) {
                ch -= ((f.show_me)
                       ? 1
                       : 0);

                if (ch < 0) {
                    break;
                }

                ++res_choose;
            }

            this.filter.fileds[flt] = res_choose;
            this.filter.model_user = this.filter_text;

            String shortName = ((ManageFlitManager.FilterField) fields.get(res_choose)).name;

            ManageFlitManager.getManageFlitManager().SetFilter(this.filter);
            ManageFleet.this.dealerFleetTable.updateTable();

            if (this.filter_current != 3) {
                menues.SetFieldText(this.texts[this.filter_current], shortName);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[this.filter_current]));
            } else if (this.filter_choose == -1) {
                menues.SetFieldText(this.texts[this.filter_current], this.filter_text);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[this.filter_current]));
                menues.SetFieldText(this.edit_box, this.filter_text);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.edit_box));
            } else {
                menues.SetFieldText(this.texts[this.filter_current], shortName);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[this.filter_current]));
                menues.SetFieldText(this.edit_box, shortName);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.edit_box));
            }
        }

        String[] getFilterValues(int taken) {
            return getFilterValuesAllTypes(taken, false);
        }

        String[] getFilterValuesShort(int taken) {
            return getFilterValuesAllTypes(taken, true);
        }

        private String[] getFilterValuesAllTypes(int taken, boolean is_short) {
            int flt = 0;

            switch (taken) {
             case 0 :
                 flt = 0;

                 break;

             case 1 :
                 flt = 1;

                 break;

             case 2 :
                 flt = 2;

                 break;

             case 3 :
                 flt = 3;

                 break;

             case 4 :
                 flt = 4;

                 break;

             case 5 :
                 flt = 6;

                 break;

             case 6 :
                 flt = 5;

                 break;

             case 7 :
                 flt = 7;
            }

            Vector fields = null;

            if (is_short) {
                fields = ManageFlitManager.getManageFlitManager().GetShortFilterFields(flt);
            } else {
                fields = ManageFlitManager.getManageFlitManager().GetFilterFields(flt);
            }

            int count = 0;

            for (ManageFlitManager.FilterField f : fields) {
                count += ((f.show_me)
                          ? 1
                          : 0);
            }

            String[] data = new String[count];

            count = 0;

            for (int i = 0; i < fields.size(); ++i) {
                if (((ManageFlitManager.FilterField) fields.get(i)).show_me) {
                    data[(count++)] = ((ManageFlitManager.FilterField) fields.get(i)).name;
                }
            }

            return data;
        }

        String[] getModelFilterValues() {
            this.filter.model_user = this.filter_text;
            ManageFlitManager.getManageFlitManager().SetFilter(this.filter);

            Vector fields = ManageFlitManager.getManageFlitManager().GetShortFilterFields(3);
            int count = 0;

            for (ManageFlitManager.FilterField f : fields) {
                count += ((f.show_me)
                          ? 1
                          : 0);
            }

            String[] data = new String[count];

            count = 0;

            for (int i = 0; i < fields.size(); ++i) {
                if (((ManageFlitManager.FilterField) fields.get(i)).show_me) {
                    data[(count++)] = ((ManageFlitManager.FilterField) fields.get(i)).name;
                }
            }

            return data;
        }
    }


    class TermometrClass {
        int value = 0;
        boolean bShow = false;
        long control;
        int initial_len_x;

        TermometrClass(long _menu, String name) {
            this.control = menues.FindFieldInMenu(_menu, name);

            MENUText_field field;

            if (this.control != 0L) {
                field = (MENUText_field) menues.ConvertMenuFields(this.control);

                if (field != null) {
                    this.initial_len_x = field.lenx;
                }
            }
        }

        void Update(int _value) {
            this.value = ((_value < 0)
                          ? 0
                          : _value);
            this.value = ((_value > 100)
                          ? 100
                          : _value);
            this.bShow = (this.value != 0);

            if (this.control != 0L) {
                MENUText_field field = (MENUText_field) menues.ConvertMenuFields(this.control);

                if (field != null) {
                    field.lenx = (this.initial_len_x * this.value / 100);
                    menues.UpdateMenuField(field);
                }

                menues.SetShowField(this.control, this.bShow);
            }
        }
    }


    class closeVehicleDeatiled implements IVehicleDetailesListener {

        /**
         * Method description
         *
         */
        @Override
        public void onClose() {
            menues.SetShowField(menues.GetBackMenu(ManageFleet.this._menu), true);
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


    static class table_data_dealerfleet {
        Vector<ManageFleet.Dealerfleetline> all_lines;

        table_data_dealerfleet() {
            this.all_lines = new Vector();
        }
    }


    static class table_data_myfleet {
        Vector<ManageFleet.Myfleetline> all_lines;

        table_data_myfleet() {
            this.all_lines = new Vector();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
