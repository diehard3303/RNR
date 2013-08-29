/*
 * @(#)ManageDrivers.java   13/08/26
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
import rnr.src.menu.IRangerChanged;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUEditBox;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUTruckview;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.RNRMap.Priority;
import rnr.src.menu.Ranger;
import rnr.src.menu.SelectCb;
import rnr.src.menu.menues;
import rnr.src.menuscript.RNRMapWrapper;
import rnr.src.menuscript.TooltipButton;
import rnr.src.menuscript.table.ICompareLines;
import rnr.src.menuscript.table.ISelectLineListener;
import rnr.src.menuscript.table.ISetupLine;
import rnr.src.menuscript.table.Table;
import rnr.src.rnrconfig.IconMappings;
import rnr.src.rnrconfig.WorldCoordinates;
import rnr.src.rnrcore.Log;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class ManageDrivers extends ApplicationTab {
    private static final boolean bDebug = false;
    private static final String TAB_NAME = "MANAGE DRIVERS";
    private static final String[] SLIDERS = {
        "Tableranger - MD Order Type", "Tableranger - MD Load Fragility", "Tableranger - MD Distances",
        "Tableranger - MD Wear", "Tableranger - MD Speed", "Tableranger - MD Load Safety",
        "Tableranger - MD Wage change"
    };
    private static final int SLIDER_ORDERTYPE = 0;
    private static final int SLIDER_LOADFRAG = 1;
    private static final int SLIDER_DISTANCES = 2;
    private static final int SLIDER_ORDERCRITERIA = 3;
    private static final int SLIDER_WEAR = 3;
    private static final int SLIDER_SPEED = 4;
    private static final int SLIDER_LOADSAFETY = 5;
    private static final int SLIDER_TRUCKSELECTIONCRITERIA = 6;
    private static final int SLIDER_WAGECHANGES = 6;
    private static final int SLIDER_WAGECHANGECRITERIA = 7;
    private static final String[] TEXT_SLIDERS = {
        "MD Order Type TITLE", "MD Load Fragility TITLE", "MD Distances TITLE", "MD Wear TITLE", "MD Speed TITLE",
        "MD Load Safety TITLE", "MD Wage change VALUE"
    };
    private static final int NORMALSTATE = 0;
    private static final int GREYSTATE = 1;
    private static final String[] CHECKBOXES = { "Order selection criteria - AUTO", "Truck selection criteria - AUTO",
            "Wage change - AUTO" };
    private static final int CHECKBOX_AUTO_ORDERCRITERIA = 0;
    private static final int CHECKBOX_AUTO_TRUCKSELECTIONCRITERIA = 1;
    private static final int CHECKBOX_AUTO_WAGECHANGE = 2;
    private static final String METH_CHECKBOXES = "onCheckBox";
    private static final String[] SUMMARY_TEXTS = { "Drivers Involved VALUE", "Order Criteria Changed VALUE",
            "Truck Criteria Changed VALUE", "Drivers VALUE", "$/Day VALUE" };
    private static final int TEXT_SUMMARY_DRIVER = 0;
    private static final int TEXT_SUMMARY_ORDER = 1;
    private static final int TEXT_SUMMARY_TRUCK = 2;
    private static final int TEXT_SUMMARY_WAGEDRIVERS = 3;
    private static final int TEXT_SUMMARY_WAGEMONEY = 4;
    private static final String[] FILE_TEXTS = {
        "MD DriverFile NAME 3 Rows", "FileVALUE Loyalty", "FileVALUE Return", "FileVALUE Wins/Missions",
        "FileVALUE Forfeit", "FileVALUE Maintenance", "FileVALUE Gas", "FileVALUE Vehicle", "FileVALUE Vehicle MARKED"
    };
    private static final int TEXT_FILE_TITLE = 0;
    private static final int TEXT_FILE_LOYALITY = 1;
    private static final int TEXT_FILE_ROI = 2;
    private static final int TEXT_FILE_WINS = 3;
    private static final int TEXT_FILE_FORFEI = 4;
    private static final int TEXT_FILE_MAINTANANCE = 5;
    private static final int TEXT_FILE_GAS = 6;
    private static final int TEXT_FILE_VEHICLE = 7;
    private static final int TEXT_FILE_VEHICLE_MARKED = 8;
    private static final String CONTROL_PHOTO = "MD DriverFile PHOTO";
    private static final String[] SORT_FIELDS = {
        "BUTTON - Loyalty", "BUTTON - Return", "BUTTON - Wins/Missions", "BUTTON - Forfeit", "BUTTON - Maintenance",
        "BUTTON - Gas", "BUTTON - Vehicle", "BUTTON - Name", "BUTTON - Rank", "BUTTON - Wage"
    };
    private static final int SOTR_FILE_LOYALITY = 0;
    private static final int SORT_FILE_ROI = 1;
    private static final int SORT_FILE_WINS = 2;
    private static final int SORT_FILE_FORFEI = 3;
    private static final int SORT_FILE_MAINTANANCE = 4;
    private static final int SORT_FILE_GAS = 5;
    private static final int SORT_FILE_VEHICLE = 6;
    private static final int SORT_NAME = 7;
    private static final int SORT_RANK = 8;
    private static final int SORT_WAGE = 9;
    private static final String METH_SORT = "onSort";
    private static final String CONTROL_APPLY = "BUTTON - APPLY";
    private static final String CONTROL_DISCARD = "BUTTON - DISCARD";
    private static final String METH_APPLY = "onApply";
    private static final String METH_DISCARD = "onDiscard";
    private static final String MAP_NAME = "MAP - zooming picture";
    private static final String MAP_ZOOM = "MD - MAP";
    private static final String MAP_SHIFT = "MD - MAP";
    private static final String TOOLTIPBUTTON_XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String TOOLTIPBUTTON_BUTTON = "MD DriverFile - Details";
    private static final String TOOLTIPBUTTON_MENUGROUP = "TOOLTIP Deatiled VehicleName";
    private static final String[] TOOLTIPBUTTON_TEXT = { "MD DriverFile - Vehicle VALUE",
            "MD DriverFile - Vehicle VALUE MARKED" };
    private static final int min_sliders = 0;
    private static final int max_sliders = 100;
    private static final int default_sliders = 50;
    private static final String[] TERMOMETRS_NAMES = {
        "FileVALUE Loyalty - INDICATOR", "FileVALUE Return - INDICATOR", "FileVALUE Wins/Missions - INDICATOR",
        "FileVALUE Forfeit - INDICATOR", "FileVALUE Maintenance - INDICATOR", "FileVALUE Gas - INDICATOR"
    };
    private static final int TERMOMETR_Loyalty = 0;
    private static final int TERMOMETR_Return = 1;
    private static final int TERMOMETR_Wins_Missions = 2;
    private static final int TERMOMETR_Forfeit = 3;
    private static final int TERMOMETR_Maintenance = 4;
    private static final int TERMOMETR_Gas = 5;
    private final String[] FILE_TEXTS_INITIAL_VALUES = new String[FILE_TEXTS.length];
    private final String KEY_NAME = "NAME";
    private final String KEY_AGE = "AGE";
    private final String KEY_MONEY = "MONEY";
    private final String KEY_WINS = "WINS";
    private final String KEY_SIGN = "SIGN";
    private long current_selected_id = 0L;
    private final table_data TABLE_DATA = new table_data();
    private final DriversTable dr_table = new DriversTable();
    private final Ranger[] sliders = new Ranger[SLIDERS.length];
    private final long[] checkboxes = new long[CHECKBOXES.length];
    private final long[] summarytexts = new long[SUMMARY_TEXTS.length];
    private final long[] filetexts = new long[FILE_TEXTS.length];
    private long[] slider_text = null;
    private String[] slider_text_text = null;
    private TooltipButton tooltipbutton = null;
    sort table_sort = new sort(1, true);
    WorldCoordinates worldRectangle = null;
    TermometrClass[] termometrs = null;
    long _menu = 0L;
    boolean bSelectFromMap = false;
    String tool_tip_text = null;
    private long driver_photo;
    RNRMapWrapper mapa;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param parent
     */
    public ManageDrivers(long _menu, OfficeMenu parent) {
        super(_menu, "MANAGE DRIVERS", parent);
        this._menu = _menu;
        this.worldRectangle = WorldCoordinates.getCoordinates();
        ManageDriversManager.getManageDriversManager().OnEnterOffice();
        init(_menu);
    }

    private void init(long _menu) {
        for (int i = 0; i < CHECKBOXES.length; ++i) {
            long field = menues.FindFieldInMenu(_menu, CHECKBOXES[i]);

            this.checkboxes[i] = field;

            MENUbutton_field but = menues.ConvertButton(field);

            but.userid = i;
            menues.SetScriptOnControl(_menu, but, this, "onCheckBox", 2L);
            menues.UpdateField(but);
        }

        for (int i = 0; i < SUMMARY_TEXTS.length; ++i) {
            long field = menues.FindFieldInMenu(_menu, SUMMARY_TEXTS[i]);

            this.summarytexts[i] = field;
        }

        for (int i = 0; i < FILE_TEXTS.length; ++i) {
            long field = menues.FindFieldInMenu(_menu, FILE_TEXTS[i]);

            this.filetexts[i] = field;
            this.FILE_TEXTS_INITIAL_VALUES[i] = menues.GetFieldText(field);
        }

        for (int i = 0; i < SORT_FIELDS.length; ++i) {
            long field = menues.FindFieldInMenu(_menu, SORT_FIELDS[i]);
            MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

            buts.userid = i;
            menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
            menues.UpdateField(buts);
        }

        this.slider_text = new long[TEXT_SLIDERS.length];
        this.slider_text_text = new String[TEXT_SLIDERS.length];

        for (int i = 0; i < TEXT_SLIDERS.length; ++i) {
            this.slider_text[i] = menues.FindFieldInMenu(_menu, TEXT_SLIDERS[i]);

            if (this.slider_text[i] != 0L) {
                this.slider_text_text[i] = menues.GetFieldText(this.slider_text[i]);
            }
        }

        this.dr_table.init(_menu);
        initSliders(_menu);

        long control = menues.FindFieldInMenu(_menu, "BUTTON - APPLY");

        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "onApply", 4L);
        control = menues.FindFieldInMenu(_menu, "BUTTON - DISCARD");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "onDiscard", 4L);
        control = menues.FindFieldInMenu(_menu, "CALL OFFICE HELP - MANAGE DRIVERS");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);

        long mapfield = menues.FindFieldInMenu(_menu, "MAP - zooming picture");

        if (0L == mapfield) {
            Log.menu("ERRORR. ManageDrivers. No rnr map named MAP - zooming picture");
        }

        this.mapa = new RNRMapWrapper(_menu, "MAP - zooming picture", "MD - MAP", "MD - MAP", new SelectMapControl());
        this.driver_photo = menues.FindFieldInMenu(_menu, "MD DriverFile PHOTO");
        this.tooltipbutton = new TooltipButton(_menu, "MD DriverFile - Details",
                "..\\data\\config\\menu\\menu_office.xml", "TOOLTIP Deatiled VehicleName", TOOLTIPBUTTON_TEXT);
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

        this.dr_table.afterInit();
        this.mapa.afterInit();
        this.mapa.workWith(10);
        this.tooltipbutton.afterInit();
        this.tooltipbutton.Enable(false);
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

        this.dr_table.update();
        refresh_fileinfo();
        refresh_summary();
        refresh_sliders();
        refresh_checkboxes();
        refresh_mapa();
        makeUpdate();

        return true;
    }

    /**
     * Method description
     *
     */
    @Override
    public void deinit() {
        ManageDriversManager.getManageDriversManager().OnLeaveOffice();
        this.dr_table.table.deinit();
    }

    private void initSliders(long _menu) {
        for (int i = 0; i < SLIDERS.length; ++i) {
            this.sliders[i] = new Ranger(_menu, SLIDERS[i], 0, 100, 50);
            this.sliders[i].addListener(new SliderChange(i));
        }
    }

    private void selectDriver(ManageDriversManager.DriverId id) {
        this.current_selected_id = ((id != null)
                                    ? id.id
                                    : 0L);
        refresh_fileinfo();
        refresh_sliders();
        refresh_checkboxes();

        if (!(this.bSelectFromMap)) {
            for (line_data item : this.TABLE_DATA.all_lines) {
                if ((item.wheather_show) && (item.id.id == id.id)) {
                    this.mapa.selectSelect(item.map_id, true);
                }
            }
        }
    }

    private void slider_moved(int slider_nom) {
        if ((slider_nom >= 0) && (slider_nom < 3)) {
            update_turnoffRadio(0, false);
        } else if ((slider_nom >= 3) && (slider_nom < 6)) {
            update_turnoffRadio(1, false);
        } else if ((slider_nom >= 6) && (slider_nom < 7)) {
            update_turnoffRadio(2, false);
        }
    }

    private void drop_slider_group(int group) {
        switch (group) {
         case 0 :
             for (int i = 0; i < 3; ++i) {
                 this.sliders[i].setValue(50, true);
             }

             break;

         case 1 :
             for (int i = 3; i < 6; ++i) {
                 this.sliders[i].setValue(50, true);
             }

             break;

         case 2 :
             for (int i = 6; i < 7; ++i) {
                 Vector bunch = this.dr_table.getSelectedDrivers();
                 int[] wages = ManageDriversManager.getManageDriversManager().GetWage(bunch);

                 ManageDriversManager.getManageDriversManager().GetWage(bunch);
                 this.sliders[i].setValue(wages[0], wages[1], wages[2], (int) (0.1D * (wages[1] - wages[0])), true);

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("MONEY", "" + wages[2]);
                 menues.SetFieldText(this.slider_text[6], MacroKit.Parse(this.slider_text_text[6], macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(this.slider_text[6]));
             }
        }
    }

    private void manage_gray_sliders() {
        for (Ranger item : this.sliders) {
            item.setState(0);
        }

        for (int i = 0; i < this.TABLE_DATA.all_lines.size() - 1; ++i) {
            for (int j = i + 1; j < this.TABLE_DATA.all_lines.size(); ++j) {
                if ((this.TABLE_DATA.all_lines.get(i).selected) && (this.TABLE_DATA.all_lines.get(j).selected)) {
                    for (int k = 0; k < 7; ++k) {
                        if (!(ask_samevalues_sliders(k, this.TABLE_DATA.all_lines.get(i),
                                                     this.TABLE_DATA.all_lines.get(j)))) {
                            this.sliders[k].setState(1);
                        }
                    }
                }
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
    public void onCheckBox(long _menu, MENUbutton_field button) {
        setDirty();

        int state = menues.GetFieldState(button.nativePointer);

        update_turnoffRadio(button.userid, state != 0);

        switch (button.userid) {
         case 0 :
             for (int i = 0; i < 3; ++i) {
                 this.sliders[i].setState(0);
             }

             break;

         case 1 :
             for (int i = 3; i < 6; ++i) {
                 this.sliders[i].setState(0);
             }

             break;

         case 2 :
             for (int i = 6; i < 7; ++i) {
                 this.sliders[i].setState(0);
             }
        }

        if (state == 1) {
            drop_slider_group(button.userid);
        }

        refresh_summary();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onSort(long _menu, MENUsimplebutton_field button) {
        update_sort(button.userid);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void onApply(long _menu, MENUsimplebutton_field button) {
        update_apply();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void onDiscard(long _menu, MENUsimplebutton_field button) {
        update_discard();
    }

    private float c01(int value) {
        if (value >= 100) {
            return 1.0F;
        }

        if (value <= 0) {
            return 0.0F;
        }

        return (value / 100.0F);
    }

    private int c0100_01(float value) {
        if (value >= 1.0F) {
            return 100;
        }

        if (value <= 0.0F) {
            return 0;
        }

        return (0 + (int) (100.0F * value));
    }

    private float c_11(int value) {
        if (value >= 100) {
            return 1.0F;
        }

        if (value <= 0) {
            return -1.0F;
        }

        return (-1.0F + value / 50.0F);
    }

    private void update_turnoffRadio(int button, boolean value) {
        Vector bunch = this.dr_table.getSelectedDrivers();

        if (bunch.isEmpty()) {
            return;
        }

        switch (button) {
         case 0 :
             menues.SetFieldState(this.checkboxes[button], (value)
                     ? 1
                     : 0);
             ManageDriversManager.getManageDriversManager().SetOrderSelectionCriteria(bunch, value);

             break;

         case 1 :
             menues.SetFieldState(this.checkboxes[button], (value)
                     ? 1
                     : 0);
             ManageDriversManager.getManageDriversManager().SetTruckSelectionCriteria(bunch, value);

             break;

         case 2 :
             menues.SetFieldState(this.checkboxes[button], (value)
                     ? 1
                     : 0);
             ManageDriversManager.getManageDriversManager().SetWageChangeCriteria(bunch, value);
        }
    }

    private void update_slider(int button, int value) {
        Vector bunch = this.dr_table.getSelectedDrivers();

        switch (button) {
         case 0 :
             ManageDriversManager.getManageDriversManager().SetOrderType(bunch, c01(value));

             break;

         case 1 :
             ManageDriversManager.getManageDriversManager().SetLoadFargylity(bunch, c01(value));

             break;

         case 2 :
             ManageDriversManager.getManageDriversManager().SetDistances(bunch, c01(value));

             break;

         case 3 :
             ManageDriversManager.getManageDriversManager().SetWear(bunch, c01(value));

             break;

         case 4 :
             ManageDriversManager.getManageDriversManager().SetSpeed(bunch, c01(value));

             break;

         case 5 :
             ManageDriversManager.getManageDriversManager().SetLoadSafety(bunch, c01(value));

             break;

         case 6 :
             int converted = value;
             KeyPair[] macro = new KeyPair[1];

             macro[0] = new KeyPair("MONEY", "" + converted);
             menues.SetFieldText(this.slider_text[6], MacroKit.Parse(this.slider_text_text[6], macro));
             menues.UpdateMenuField(menues.ConvertMenuFields(this.slider_text[6]));
             ManageDriversManager.getManageDriversManager().SetWage(bunch, value);
        }
    }

    private void refresh_fileinfo() {
        if (this.tool_tip_text == null) {
            this.tool_tip_text = this.tooltipbutton.GetText();
        }

        line_data current = null;

        for (line_data item : this.TABLE_DATA.all_lines) {
            if ((item.id != null) && (item.id.id == this.current_selected_id)) {
                current = item;

                break;
            }
        }

        if ((null == current) || (0L == this.current_selected_id)) {
            menues.SetShowField(this.driver_photo, false);

            for (int i = 0; i < this.filetexts.length; ++i) {
                menues.SetFieldText(this.filetexts[i], "");
                menues.UpdateMenuField(menues.ConvertMenuFields(this.filetexts[i]));
            }

            this.tooltipbutton.setText("");
            this.tooltipbutton.Enable(false);

            for (int i = 0; i < this.termometrs.length; ++i) {
                this.termometrs[i].Update(0);
            }

            return;
        }

        for (int i = 0; i < this.filetexts.length; ++i) {
            switch (i) {
             case 0 :
                 KeyPair[] pairs6 = { new KeyPair("NAME", "" + current.driver_name),
                                      new KeyPair("AGE", "" + current.age) };

                 menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs6));

                 break;

             case 1 :
                 menues.SetFieldText(this.filetexts[i], "" + (int) current.loyalty + "%");

                 break;

             case 2 :
                 KeyPair[] pairs1 = { new KeyPair("MONEY", Helper.convertMoney(Math.abs((int) current.roi))),
                                      new KeyPair("SIGN", ((int) current.roi >= 0)
                         ? ""
                         : "-") };

                 menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs1));

                 break;

             case 3 :
                 KeyPair[] pairs5 = { new KeyPair("WINS", "" + current.wins_missions) };

                 menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs5));

                 break;

             case 4 :
                 KeyPair[] pairs2 = { new KeyPair("MONEY", Helper.convertMoney((int) current.forefit)) };

                 menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs2));

                 break;

             case 5 :
                 KeyPair[] pairs3 = { new KeyPair("MONEY", Helper.convertMoney((int) current.maintenance)) };

                 menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs3));

                 break;

             case 6 :
                 KeyPair[] pairs4 = { new KeyPair("MONEY", Helper.convertMoney((int) current.gas)) };

                 menues.SetFieldText(this.filetexts[i], MacroKit.Parse(this.FILE_TEXTS_INITIAL_VALUES[i], pairs4));

                 break;

             case 7 :
                 menues.SetShowField(this.filetexts[i], !(current.vehickeJustBought));
                 menues.SetFieldText(this.filetexts[i], current.short_vehicle_name);

                 break;

             case 8 :
                 menues.SetShowField(this.filetexts[i], current.vehickeJustBought);
                 menues.SetFieldText(this.filetexts[i], current.short_vehicle_name);
            }

            menues.UpdateMenuField(menues.ConvertMenuFields(this.filetexts[i]));
        }

        for (int i = 0; i < this.termometrs.length; ++i) {
            switch (i) {
             case 0 :
                 this.termometrs[i].Update((int) (100.0D * current.loyalty_temp));

                 break;

             case 1 :
                 this.termometrs[i].Update((int) (100.0D * current.roi_temp));

                 break;

             case 2 :
                 this.termometrs[i].Update((int) (100.0D * current.wins_temp));

                 break;

             case 3 :
                 this.termometrs[i].Update((int) (100.0D * current.forefit_temp));

                 break;

             case 4 :
                 this.termometrs[i].Update((int) (100.0D * current.maintenance_temp));

                 break;

             case 5 :
                 this.termometrs[i].Update((int) (100.0D * current.gas_temp));
            }
        }

        if (null == current.face_index) {
            menues.SetShowField(this.driver_photo, false);
        } else {
            menues.SetShowField(this.driver_photo, true);
            IconMappings.remapPersonIcon("" + current.face_index, this.driver_photo);
        }

        if ((current.make == null) || (current.make.length() == 0) || (current.model == null)
                || (current.model.length() == 0) || (current.license_plate == null)
                || (current.license_plate.length() == 0)) {
            this.tooltipbutton.Enable(false);
        } else {
            KeyPair[] pairs = { new KeyPair("MAKE", current.make), new KeyPair("MODEL", current.model),
                                new KeyPair("LICENSE_PLATE", current.license_plate) };

            this.tooltipbutton.setText(MacroKit.Parse(this.tool_tip_text, pairs));
            this.tooltipbutton.setState((current.vehickeJustBought)
                                        ? 1
                                        : 0);
            this.tooltipbutton.Enable(true);
        }
    }

    private void refresh_summary() {
        ManageDriversManager.Summary summ = ManageDriversManager.getManageDriversManager().GetSummary();

        for (int i = 0; i < this.summarytexts.length; ++i) {
            switch (i) {
             case 0 :
                 menues.SetFieldText(this.summarytexts[i], "" + summ.drivers_involved);

                 break;

             case 1 :
                 menues.SetFieldText(this.summarytexts[i], "" + summ.order_criteria_changed);

                 break;

             case 2 :
                 menues.SetFieldText(this.summarytexts[i], "" + summ.truck_criteria_changed);

                 break;

             case 3 :
                 menues.SetFieldText(this.summarytexts[i], "" + summ.wages_changed_drivers);

                 break;

             case 4 :
                 menues.SetFieldText(this.summarytexts[i], ((summ.wages_changed_day < 0.0F)
                         ? "-"
                         : " ") + Helper.convertMoney((int) summ.wages_changed_day));
            }

            menues.UpdateMenuField(menues.ConvertMenuFields(this.summarytexts[i]));
        }
    }

    private void refresh_mapa() {
        this.mapa.ClearData();

        for (line_data item : this.TABLE_DATA.all_lines) {
            if (item.wheather_show) {
                String name_to_display = "";

                item.map_id = this.mapa.addObject(10, (float) this.worldRectangle.convertX(item.x),
                                                  (float) this.worldRectangle.convertY(item.y), name_to_display, item);
                setPriority(item.map_id, -2147383649);
            }
        }

        boolean first = true;

        for (line_data item : this.TABLE_DATA.all_lines) {
            if ((item.wheather_show) && (first) && (item.selected) && (item.id != null)) {
                this.mapa.selectSelect(item.map_id, true);
                first = false;
            }
        }

        updateWarehousesOnMapa();
    }

    private void setPriority(int icon, int value) {
        int p1 = 1;
        int p2 = 2;
        int p3 = 3;
        int p4 = 4;
        int p5 = 5;
        RNRMap.Priority priority = this.mapa.createPriority();

        priority.SetPriority(0, true, true, value + p5);
        priority.SetPriority(0, true, false, value + p4);
        priority.SetPriority(0, false, false, value);
        priority.SetPriority(0, false, true, value + p1);
        priority.SetPriority(1, true, true, value + p5);
        priority.SetPriority(1, true, false, value + p4);
        priority.SetPriority(1, false, false, value + p4);
        priority.SetPriority(1, false, true, value + p5);
        priority.SetPriority(2, true, true, value + p3);
        priority.SetPriority(2, true, false, value + p2);
        priority.SetPriority(2, false, false, value + p2);
        priority.SetPriority(2, false, true, value + p3);
        this.mapa.setPriority(icon, priority);
    }

    private void refresh_checkboxes() {
        Vector bunch = this.dr_table.getSelectedDrivers();

        for (int i = 0; i < this.checkboxes.length; ++i) {
            boolean value = false;

            switch (i) {
             case 0 :
                 value = ManageDriversManager.getManageDriversManager().GetOrderSelectionCriteria(bunch);

                 break;

             case 1 :
                 value = ManageDriversManager.getManageDriversManager().GetTruckSelectionCriteria(bunch);

                 break;

             case 2 :
                 value = ManageDriversManager.getManageDriversManager().GetWageChangeCriteria(bunch);
            }

            menues.SetFieldState(this.checkboxes[i], (value)
                    ? 1
                    : 0);

            if (value) {
                drop_slider_group(i);
            }
        }
    }

    private void refresh_sliders() {
        Vector bunch = this.dr_table.getSelectedDrivers();

        for (int i = 0; i < this.sliders.length; ++i) {
            int value = 0;

            switch (i) {
             case 0 :
                 value = c0100_01(ManageDriversManager.getManageDriversManager().GetOrderType(bunch));
                 this.sliders[i].setValue(value, true);

                 break;

             case 1 :
                 value = c0100_01(ManageDriversManager.getManageDriversManager().GetLoadFragylity(bunch));
                 this.sliders[i].setValue(value, true);

                 break;

             case 2 :
                 value = c0100_01(ManageDriversManager.getManageDriversManager().GetDistances(bunch));
                 this.sliders[i].setValue(value, true);

                 break;

             case 3 :
                 value = c0100_01(ManageDriversManager.getManageDriversManager().GetWear(bunch));
                 this.sliders[i].setValue(value, true);

                 break;

             case 4 :
                 value = c0100_01(ManageDriversManager.getManageDriversManager().GetSpeed(bunch));
                 this.sliders[i].setValue(value, true);

                 break;

             case 5 :
                 value = c0100_01(ManageDriversManager.getManageDriversManager().GetLoadSafety(bunch));
                 this.sliders[i].setValue(value, true);

                 break;

             case 6 :
                 int[] wages = ManageDriversManager.getManageDriversManager().GetWage(bunch);

                 value = wages[2];

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("MONEY", "" + wages[2]);
                 menues.SetFieldText(this.slider_text[6], MacroKit.Parse(this.slider_text_text[6], macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(this.slider_text[6]));
                 this.sliders[i].setValue(wages[0], wages[1], wages[2], (int) (0.1D * (wages[1] - wages[0])), true);

                 break;

             default :
                 this.sliders[i].setValue(value, true);
            }
        }
    }

    private void update_sort(int type_sort) {
        switch (type_sort) {
         case 0 :
             this.table_sort = new sort(4, !(this.table_sort.up));

             break;

         case 1 :
             this.table_sort = new sort(5, !(this.table_sort.up));

             break;

         case 2 :
             this.table_sort = new sort(6, !(this.table_sort.up));

             break;

         case 3 :
             this.table_sort = new sort(7, !(this.table_sort.up));

             break;

         case 4 :
             this.table_sort = new sort(8, !(this.table_sort.up));

             break;

         case 5 :
             this.table_sort = new sort(9, !(this.table_sort.up));

             break;

         case 6 :
             this.table_sort = new sort(10, !(this.table_sort.up));

             break;

         case 7 :
             this.table_sort = new sort(1, !(this.table_sort.up));

             break;

         case 8 :
             this.table_sort = new sort(2, !(this.table_sort.up));

             break;

         case 9 :
             this.table_sort = new sort(3, !(this.table_sort.up));
        }

        setDirty();
        update();
    }

    private void updateWarehousesOnMapa() {
        HashMap warehouses = new HashMap();
        Vector our_offices = ManageBranchesManager.getManageBranchesManager().GetCompanyBranches(1, true);
        Vector sales_offices = ManageBranchesManager.getManageBranchesManager().GetOfficesForSale(1, true);
        Vector offices = new Vector();

        for (ManageBranchesManager.OfficeInfo info : our_offices) {
            offices.add(info.id);
        }

        for (ManageBranchesManager.OfficeInfo info : sales_offices) {
            offices.add(info.id);
        }

        Vector warehouse_collection = ManageBranchesManager.getManageBranchesManager().GetWarehousesInTheArea(offices);

        for (ManageBranchesManager.WarehouseInfo ware : warehouse_collection) {
            ManageBranchesManager.WarehouseInfo inf = (ManageBranchesManager.WarehouseInfo) warehouses.get(ware.name);

            if (null == inf) {
                warehouses.put(ware.name, ware);
            }
        }

        Collection warecoll = warehouses.values();

        for (ManageBranchesManager.WarehouseInfo info : warecoll) {
            int type = (info.companyAffected)
                       ? 4
                       : 3;

            this.mapa.addObject(type, (float) this.worldRectangle.convertX(info.x),
                                (float) this.worldRectangle.convertY(info.y), info.name, new Object());
        }
    }

    private void update_apply() {
        setDirty();

        if (!(ManageDriversManager.getManageDriversManager().Apply())) {
            makeNotEnoughMoney();
        }

        update();
    }

    private void update_discard() {
        setDirty();
        ManageDriversManager.getManageDriversManager().Discard();
        update();
    }

    private boolean ask_samevalues_sliders(int type, line_data line1, line_data line2) {
        return false;
    }

    /**
     * Method description
     *
     */
    @Override
    public void apply() {
        ManageDriversManager.getManageDriversManager().Apply();
        update();
    }

    /**
     * Method description
     *
     */
    @Override
    public void discard() {
        ManageDriversManager.getManageDriversManager().Discard();
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
            this.parent.ShowTabHelp(1);
        }
    }

    class DriversTable implements ISetupLine, ISelectLineListener {
        private final String TABLE = "MD Drivers - TABLEGROUP - 7 38";
        private final String TABLE_RANGER = "Tableranger - MD - NameRating";
        private final String XML_LINE = "..\\data\\config\\menu\\menu_office.xml";
        private final String CONTROLGROUP_LINE = "Tablegroup - ELEMENTS - Drivers";
        private final int MARK_NAME = 0;
        private final int MARK_NAME_GRAY = 1;
        private final int MARK_RANK = 2;
        private final int MARK_RANK_GRAY = 3;
        private final int MARK_WAGE = 4;
        private final int MARK_WAGE_GRAY = 5;
        private final String KEY = "MONEY";
        private final String[] MARKS;
        private String SUBSOURCE;
        private long _menu;
        Table table;

        DriversTable() {
            this.TABLE = "MD Drivers - TABLEGROUP - 7 38";
            this.TABLE_RANGER = "Tableranger - MD - NameRating";
            this.XML_LINE = "..\\data\\config\\menu\\menu_office.xml";
            this.CONTROLGROUP_LINE = "Tablegroup - ELEMENTS - Drivers";
            this.MARKS = new String[] {
                "BUTTON - Name VALUE", "BUTTON - Name VALUE GRAY", "BUTTON - Rank VALUE", "BUTTON - Rank VALUE GRAY",
                "BUTTON - Wage VALUE", "BUTTON - Wage VALUE GRAY"
            };
            this.MARK_NAME = 0;
            this.MARK_NAME_GRAY = 1;
            this.MARK_RANK = 2;
            this.MARK_RANK_GRAY = 3;
            this.MARK_WAGE = 4;
            this.MARK_WAGE_GRAY = 5;
            this.KEY = "MONEY";
            this.SUBSOURCE = "";
            this.table = null;
        }

        void init(long _menu) {
            this._menu = _menu;
            this.table = new Table(_menu, "MD Drivers - TABLEGROUP - 7 38", "Tableranger - MD - NameRating");
            this.table.setSelectionMode(1);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_office.xml", "Tablegroup - ELEMENTS - Drivers",
                                     this.MARKS);
            this.table.takeSetuperForAllLines(this);
            reciveTableData();
            build_tree_data();

            for (String name : this.MARKS) {
                this.table.initLinesSelection(name);
            }

            this.table.addListener(this);

            long[] name_controls = this.table.getLineStatistics_controls(this.MARKS[4]);

            if ((name_controls == null) || (name_controls.length == 0)) {
                Log.menu("ERRORR. DriversTable. Table has no fields named " + this.MARKS[4]);
            }

            this.SUBSOURCE = menues.GetFieldText(name_controls[1]);
        }

        void afterInit() {
            this.table.afterInit();
            make_sync_group();
        }

        private void update() {
            reciveTableData();
            build_tree_data();
            this.table.refresh();
        }

        private void build_tree_data() {
            this.table.reciveTreeData(convertTableData());
        }

        private void reciveTableData() {
            ManageDrivers.this.TABLE_DATA.all_lines.clear();

            Vector drids = ManageDriversManager.getManageDriversManager().GetShortDriverInfoList(
                               ManageDrivers.this.table_sort.type, ManageDrivers.this.table_sort.up);

            if ((ManageDrivers.this.current_selected_id == 0L) && (!(drids.isEmpty()))) {
                ManageDrivers.access$202(ManageDrivers.this,
                                         (((ManageDriversManager.ShotDriverInfo) drids.get(0)).id != null)
                                         ? ((ManageDriversManager.ShotDriverInfo) drids.get(0)).id.id
                                         : 0L);
            }

            for (ManageDriversManager.ShotDriverInfo inf : drids) {
                ManageDriversManager.FullDriverInfo local_info =
                    ManageDriversManager.getManageDriversManager().GetFullDriverInfo(inf.id);
                ManageDrivers.line_data data = new ManageDrivers.line_data();

                data.id = inf.id;
                data.money_hour = inf.wage;
                data.driver_name_table = inf.driver_name;
                data.driver_name = local_info.driver_name;
                data.age = local_info.age;
                data.face_index = local_info.face_index;
                data.rank = inf.rank;
                data.loyalty = local_info.loyalty;
                data.loyalty_temp = local_info.loyalty_temp;
                data.roi = local_info.roi;
                data.roi_temp = local_info.roi_temp;
                data.wins_temp = local_info.wins_temp;
                data.wins_missions = local_info.wins_missions;
                data.forefit = local_info.forefit;
                data.forefit_temp = local_info.forefit_temp;
                data.maintenance = local_info.maintenance;
                data.maintenance_temp = local_info.maintenance_temp;
                data.gas = local_info.gas;
                data.gas_temp = local_info.gas_temp;
                data.model = local_info.model;
                data.license_plate = local_info.license_plate;
                data.make = local_info.make;
                data.short_vehicle_name = local_info.short_vehicle_name;
                data.vehickeJustBought = local_info.vehJustBought;
                data.is_gray = inf.isGray;
                data.x = inf.x;
                data.y = inf.y;
                data.wheather_show = true;
                ManageDrivers.this.TABLE_DATA.all_lines.add(data);
            }

            buildvoidcells();
        }

        private Cmenu_TTI convertTableData() {
            Cmenu_TTI root = new Cmenu_TTI();

            for (int i = 0; i < ManageDrivers.this.TABLE_DATA.all_lines.size(); ++i) {
                Cmenu_TTI ch = new Cmenu_TTI();

                ch.toshow = true;
                ch.ontop = (i == 0);
                ch.item = ManageDrivers.this.TABLE_DATA.all_lines.get(i);
                root.children.add(ch);
            }

            return root;
        }

        private void buildvoidcells() {
            if (ManageDrivers.this.TABLE_DATA.all_lines.size() < this.table.getNumRows()) {
                int dif = this.table.getNumRows() - ManageDrivers.this.TABLE_DATA.all_lines.size();

                for (int i = 0; i < dif; ++i) {
                    ManageDrivers.line_data data = new ManageDrivers.line_data();

                    data.wheather_show = false;
                    ManageDrivers.this.TABLE_DATA.all_lines.add(data);
                }
            } else {
                int count_good_data = 0;
                Iterator iter = ManageDrivers.this.TABLE_DATA.all_lines.iterator();

                while ((iter.hasNext()) && (((ManageDrivers.line_data) iter.next()).wheather_show)) {
                    ++count_good_data;
                }

                if ((count_good_data >= this.table.getNumRows())
                        && (count_good_data < ManageDrivers.this.TABLE_DATA.all_lines.size())) {
                    for (int i = ManageDrivers.this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                        ManageDrivers.this.TABLE_DATA.all_lines.remove(i);
                    }
                }
            }
        }

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            ManageDrivers.line_data line = (ManageDrivers.line_data) table_node.item;

            if (!(line.wheather_show)) {
                menues.SetShowField(obj.nativePointer, false);

                return;
            }

            int control = this.table.getMarkedPosition(obj.nativePointer);

            switch (control) {
             case 0 :
             case 1 :
                 obj.text = line.driver_name_table;
                 menues.UpdateMenuField(obj);

                 break;

             case 2 :
             case 3 :
                 obj.text = "" + line.rank;
                 menues.UpdateMenuField(obj);

                 break;

             case 4 :
             case 5 :
                 String to_set = Helper.convertMoney((int) line.money_hour);
                 KeyPair[] pair = { new KeyPair("MONEY", to_set) };

                 obj.text = MacroKit.Parse(this.SUBSOURCE, pair);
                 menues.UpdateMenuField(obj);
            }

            switch (control) {
             case 0 :
             case 2 :
             case 4 :
                 menues.SetShowField(obj.nativePointer, !(line.is_gray));

                 break;

             case 1 :
             case 3 :
             case 5 :
                 menues.SetShowField(obj.nativePointer, line.is_gray);
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
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {}

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

        private void make_sync_group() {
            long[] contrls_name = null;

            if (0 < this.MARKS.length) {
                contrls_name = this.table.getLineStatistics_controls(this.MARKS[0]);
            }

            long[] contrls_rank = null;

            if (2 < this.MARKS.length) {
                contrls_rank = this.table.getLineStatistics_controls(this.MARKS[2]);
            }

            long[] contrls_wage = null;

            if (4 < this.MARKS.length) {
                contrls_wage = this.table.getLineStatistics_controls(this.MARKS[4]);
            }

            if ((null == contrls_name) || (null == contrls_rank) || (null == contrls_wage)) {
                return;
            }

            if ((contrls_name.length != contrls_rank.length) || (contrls_name.length != contrls_wage.length)) {
                Log.menu("ERRORR. make_sync_group has wrong behaivoir. contrls_name.length is " + contrls_name.length
                         + " contrls_rank.length is " + contrls_rank.length + " contrls_wage.length is "
                         + contrls_wage.length);

                return;
            }

            for (int i = 0; i < contrls_name.length; ++i) {
                menues.SetSyncControlActive(this._menu, i, contrls_name[i]);
                menues.SetSyncControlState(this._menu, i, contrls_name[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_rank[i]);
                menues.SetSyncControlState(this._menu, i, contrls_rank[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_wage[i]);
                menues.SetSyncControlState(this._menu, i, contrls_wage[i]);
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
            for (ManageDrivers.line_data item : ManageDrivers.this.TABLE_DATA.all_lines) {
                item.selected = false;
            }

            ManageDrivers.line_data data = (ManageDrivers.line_data) table.getItemOnLine(line).item;

            data.selected = true;
            ManageDrivers.this.selectDriver(data.id);
            ManageDrivers.this.manage_gray_sliders();
        }

        /**
         * Method description
         *
         *
         * @param table
         * @param lines
         */
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {}

        private Vector<ManageDriversManager.DriverId> getSelectedDrivers() {
            Vector res = new Vector();

            for (ManageDrivers.line_data item : ManageDrivers.this.TABLE_DATA.all_lines) {
                if ((item.wheather_show) && (item.selected)) {
                    res.add(item.id);
                }
            }

            return res;
        }

        class CompareDriversFleet implements ICompareLines {

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

                ManageDrivers.line_data line1 = (ManageDrivers.line_data) object1;
                ManageDrivers.line_data line2 = (ManageDrivers.line_data) object2;

                return ((line1.id != null) && (line2.id != null) && (line1.id.id == line2.id.id));
            }
        }
    }


    class SelectMapControl implements SelectCb {

        /**
         * Method description
         *
         *
         * @param state
         * @param sender
         */
        @Override
        public void OnSelect(int state, Object sender) {
            ManageDrivers.this.bSelectFromMap = true;
            ManageDrivers.this.dr_table.table.select_line_by_data(sender);
            ManageDrivers.this.bSelectFromMap = false;
        }
    }


    class SliderChange implements IRangerChanged {
        private final int id;

        SliderChange(int paramInt) {
            this.id = paramInt;
        }

        /**
         * Method description
         *
         *
         * @param to_value
         */
        @Override
        public void rangerChanged(int to_value) {
            if (0L == ManageDrivers.this.current_selected_id) {
                return;
            }

            ManageDrivers.this.setDirty();
            ManageDrivers.this.slider_moved(this.id);
            ManageDrivers.this.update_slider(this.id, to_value);
            ManageDrivers.this.refresh_summary();
            ManageDrivers.this.sliders[this.id].setState(0);
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


    static class line_data {
        ManageDriversManager.DriverId id;
        int map_id;
        String driver_name_table;
        String driver_name;
        int age;
        String face_index;
        int rank;
        float money_hour;
        float loyalty;
        float loyalty_temp;
        float roi;
        float roi_temp;
        int wins_missions;
        float wins_temp;
        float forefit;
        float forefit_temp;
        float maintenance;
        float maintenance_temp;
        float gas;
        float gas_temp;
        String short_vehicle_name;
        String make;
        String model;
        String license_plate;
        boolean vehickeJustBought;
        float x;
        float y;
        boolean is_gray;
        boolean selected;
        boolean wheather_show;
    }


    static class sort {
        int type;
        boolean up;

        sort(int type, boolean up) {
            this.type = type;
            this.up = up;
        }
    }


    static class table_data {
        Vector<ManageDrivers.line_data> all_lines;

        table_data() {
            this.all_lines = new Vector();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
