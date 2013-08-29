/*
 * @(#)HireDrivers.java   13/08/26
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
import rnr.src.menu.menues;
import rnr.src.menuscript.PoPUpMenu;
import rnr.src.menuscript.TooltipButton;
import rnr.src.menuscript.table.ICompareLines;
import rnr.src.menuscript.table.ISelectLineListener;
import rnr.src.menuscript.table.ISetupLine;
import rnr.src.menuscript.table.Table;
import rnr.src.rnrconfig.IconMappings;
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
public class HireDrivers extends ApplicationTab {
    private static final String TAB_NAME = "HIRE DRIVERS";
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String TABLE_MYTEAM = "HFD My Drivers - TABLEGROUP - 9 38";
    private static final String RANGER_MYTEAM = "Tableranger - HFD My Drivers";
    private static final String TABLE_MYTEAM_LINE = "Tablegroup - ELEMENTS - MyTeam";
    private static final String[] TABLE_MYTEAM_ELEMENTS = {
        "BUTTON - HFD My Drivers - Name VALUE", "BUTTON - HFD My Drivers - Rank VALUE",
        "BUTTON - HFD My Drivers - Wage VALUE", "BUTTON - HFD My Drivers - Name VALUE GRAY",
        "BUTTON - HFD My Drivers - Rank VALUE GRAY", "BUTTON - HFD My Drivers - Wage VALUE GRAY"
    };
    private static final int MYTEAM_NAME_TABLE = 0;
    private static final int MYTEAM_RANK = 1;
    private static final int MYTEAM_WAGE = 2;
    private static final int MYTEAM_NAME_TABLE_GRAY = 3;
    private static final int MYTEAM_RANK_GRAY = 4;
    private static final int MYTEAM_WAGE_GRAY = 5;
    private static final String[] MYTEAM_FULL_INFO = {
        "HFD My Drivers - DriverFile NAME 3 Rows", "HFD My Drivers - FileVALUE Loyalty",
        "HFD My Drivers - FileVALUE Return", "HFD My Drivers - FileVALUE Wins/Missions",
        "HFD My Drivers - FileVALUE Forfeit", "HFD My Drivers - FileVALUE Maintenance",
        "HFD My Drivers - FileVALUE Gas", "HFD My Drivers - FileVALUE Vehicle",
        "HFD My Drivers - FileVALUE Vehicle MARKED"
    };
    private static final int MYTEAM_NAME_FULLINFO = 0;
    private static final int MYTEAM_LOYALITY = 1;
    private static final int MYTEAM_ROI = 2;
    private static final int MYTEAM_WINS = 3;
    private static final int MYTEAM_FORFEIT = 4;
    private static final int MYTEAM_MAINTENANCE = 5;
    private static final int MYTEAM_GAS = 6;
    private static final int MYTEAM_VEHICLE = 7;
    private static final int MYTEAM_VEHICLE_MARKED = 8;
    private static final String MYTEAM_PHOTO = "HFD My Drivers - DriverFile PHOTO";
    private static final String[] MYTEAM_SORT = {
        "BUTTON - HFD My Drivers - Name", "BUTTON - HFD My Drivers - Rank", "BUTTON - HFD My Drivers - Wage",
        "BUTTON - My Drivers - Loyalty", "BUTTON - My Drivers - Return", "BUTTON - My Drivers - Wins/Missions",
        "BUTTON - My Drivers - Forfeit", "BUTTON - My Drivers - Maintenance", "BUTTON - My Drivers - Gas",
        "BUTTON - My Drivers - Vehicle"
    };
    private static final int SORT_MYTEAM_NAME = 0;
    private static final int SORT_MYTEAM_RANK = 1;
    private static final int SORT_MYTEAM_WAGE = 2;
    private static final int SORT_MYTEAM_LOYALITY = 3;
    private static final int SORT_MYTEAM_ROI = 4;
    private static final int SORT_MYTEAM_WINS = 5;
    private static final int SORT_MYTEAM_FORFEIT = 6;
    private static final int SORT_MYTEAM_MAINTENANCE = 7;
    private static final int SORT_MYTEAM_GAS = 8;
    private static final int SORT_MYTEAM_VEHICLE = 9;
    private static final String TABLE_HIRELINGS = "HFD Recruits - TABLEGROUP - 10 38";
    private static final String RANGER_HIRELINGS = "Tableranger - HFD Recruits";
    private static final String TABLE_HIRELINGS_LINE = "Tablegroup - ELEMENTS - Hirelings";
    private static final String[] TABLE_HIRELINGS_ELEMENTS = {
        "BUTTON - HFD Recruits - Name VALUE", "BUTTON - HFD Recruits - Recruiter VALUE",
        "BUTTON - HFD Recruits - Rank VALUE", "BUTTON - HFD Recruits - Wage VALUE",
        "BUTTON - HFD Recruits - Name VALUE MARKED", "BUTTON - HFD Recruits - Recruiter VALUE MARKED",
        "BUTTON - HFD Recruits - Rank VALUE MARKED", "BUTTON - HFD Recruits - Wage VALUE MARKED",
        "BUTTON - HFD Recruits - Name VALUE GRAY", "BUTTON - HFD Recruits - Recruiter VALUE GRAY",
        "BUTTON - HFD Recruits - Rank VALUE GRAY", "BUTTON - HFD Recruits - Wage VALUE GRAY"
    };
    private static final int HIRELINGS_NAME = 0;
    private static final int HIRELINGS_RECRUITER = 1;
    private static final int HIRELINGS_RANK = 2;
    private static final int HIRELINGS_WAGE = 3;
    private static final int HIRELINGS_NAME_GRAY = 4;
    private static final int HIRELINGS_RECRUITER_GRAY = 5;
    private static final int HIRELINGS_RANK_GRAY = 6;
    private static final int HIRELINGS_WAGE_GRAY = 7;
    private static final int HIRELINGS_NAME_GRAY2 = 8;
    private static final int HIRELINGS_RECRUITER_GRAY2 = 9;
    private static final int HIRELINGS_RANK_GRAY2 = 10;
    private static final int HIRELINGS_WAGE_GRAY2 = 11;
    private static final String[] HIRELINGS_FULL_INFO = {
        "HFD Recruits - Resume NAME 3 Rows", "HFD Recruits - FileVALUE Age", "HFD Recruits - FileVALUE Gender",
        "HFD Recruits - FileVALUE Tickets", "HFD Recruits - FileVALUE Accidents", "HFD Recruits - FileVALUE Has Felony",
        "HFD Recruits - FileVALUE Experience"
    };
    private static final int HIRELINGS_FULLNAME = 0;
    private static final int HIRELINGS_AGE = 1;
    private static final int HIRELINGS_GENDER = 2;
    private static final int HIRELINGS_TICKETS = 3;
    private static final int HIRELINGS_ACCIDENTS = 4;
    private static final int HIRELINGS_FELONY = 5;
    private static final int HIRELINGS_EXPERIENCE = 6;
    private static final String HIRELINGS_PHOTO = "HFD Recruits - Resume PHOTO";
    private static final String[] HIRELINGS_FULL_INFO_HASFELONY = { loc.getMENUString("common\\Yes"),
            loc.getMENUString("common\\No") };
    private static final int HIRELINGS_FULL_INFO_HASFELONY_YES = 0;
    private static final int HIRELINGS_FULL_INFO_HASFELONY_NO = 1;
    private static final String[] HIRELINGS_SORT = {
        "BUTTON - HFD Recruits - Name", "BUTTON - HFD Recruits - Recruiter", "BUTTON - HFD Recruits - Rank",
        "BUTTON - HFD Recruits - Wage", "BUTTON - HFD Recruits - Gender", "BUTTON - HFD Recruits - Age",
        "BUTTON - HFD Recruits - Tickets", "BUTTON - HFD Recruits - Accidents", "BUTTON - HFD Recruits - Has Felony",
        "BUTTON - HFD Recruits - Experience"
    };
    private static final int SORT_HIRELINGS_NAME = 0;
    private static final int SORT_HIRELINGS_RECUITER = 1;
    private static final int SORT_HIRELINGS_RANK = 2;
    private static final int SORT_HIRELINGS_WAGE = 3;
    private static final int SORT_HIRELINGS_GENDER = 4;
    private static final int SORT_HIRELINGS_AGE = 5;
    private static final int SORT_HIRELINGS_TICKETS = 6;
    private static final int SORT_HIRELINGS_ACCIDENTS = 7;
    private static final int SORT_HIRELINGS_FELONY = 8;
    private static final int SORT_HIRELINGS_EXPERIENCE = 9;
    private static final String[] ACTION_BUTTONS = { "BUTTON - HFD - HIRE", "BUTTON - HFD - FIRE" };
    private static final String[] ACTION_METHODS = { "onHire", "onFire" };
    private static final int SELECT_HIRE = 0;
    private static final int SELECT_FIRE = 1;
    private static String SEARCH_MENU_GROUP = "Tablegroup - ELEMENTS - HFD Filer Menu";
    private static final String[] SEARCH_BUTTONS = {
        "BUTTON PopUP - HFD Resume Search - Gender", "BUTTON PopUP - HFD Resume Search - Age",
        "BUTTON PopUP - HFD Resume Search - Tickets", "BUTTON PopUP - HFD Resume Search - Accidents",
        "BUTTON PopUP - HFD Resume Search - Has Felony", "BUTTON PopUP - HFD Resume Search - Experience"
    };
    private static final String[] SEARCH_TEXTS = {
        "HFD Resume Search - Gender - VALUE", "HFD Resume Search - Age - VALUE", "HFD Resume Search - Tickets - VALUE",
        "HFD Resume Search - Accidents - VALUE", "HFD Resume Search - Has Felony - VALUE",
        "HFD Resume Search - Experience - VALUE"
    };
    private static final int SEARCH_GENDER = 0;
    private static final int SEARCH_AGE = 1;
    private static final int SEARCH_TICKETS = 2;
    private static final int SEARCH_ACCIDENTS = 3;
    private static final int SEARCH_FELONY = 4;
    private static final int SEARCH_EXPERIENCE = 5;
    private static final String[] SUMMARY_TEXTS = {
        "Vacant VALUE HIRE", "To Fire VALUE HIRE", "To Hire VALUE HIRE", "Advance VALUE HIRE", "Commission VALUE HIRE",
        "Total VALUE HIRE"
    };
    private static final int SUMMARY_VACANT = 0;
    private static final int SUMMARY_FIRE = 1;
    private static final int SUMMARY_HIRE = 2;
    private static final int SUMMARY_ADVENCED = 3;
    private static final int SUMMARY_COMMISIONS = 4;
    private static final int SUMMARY_TOTAL = 5;
    private static final String MACRO_MONEY = "MONEY";
    private static final String MACRO_VALUE = "VALUE";
    private static final String MACRO_NAME = "NAME";
    private static final String MACRO_AGE = "AGE";
    private static final String MACRO_WINS = "WINS";
    private static final String MACRO_SIGN = "SIGN";
    private static final String[] SUMMARY_MACRO = {
        "VALUE", "VALUE", "VALUE", "MONEY", "MONEY", "MONEY"
    };
    private static final String[] SUMMARY_MACRO2 = {
        "", "", "", "SIGN", "SIGN", "SIGN"
    };
    private static final int[] SUMMARY_MACRO_NUM = {
        1, 1, 1, 2, 2, 2
    };
    private static final String TOOLTIPBUTTON_XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String TOOLTIPBUTTON_BUTTON = "HFD My Drivers - Details";
    private static final String TOOLTIPBUTTON_MENUGROUP = "TOOLTIP - HFD - Detailed VehicleName";
    private static final String[] TOOLTIPBUTTON_TEXT = { "HFD DriverFile - Vehicle VALUE",
            "HFD DriverFile - Vehicle VALUE MARKED" };
    private static final String XML_WARNING_RANK = "..\\data\\config\\menu\\menu_office.xml";
    private static final String GROUP_WARNING_RANK = "MESSAGE - Not sufficient rank";
    private static final String[] TERMOMETRS_NAMES = {
        "HFD My Drivers - FileVALUE Loyalty - INDICATOR", "HFD My Drivers - FileVALUE Return - INDICATOR",
        "HFD My Drivers - FileVALUE Wins/Missions - INDICATOR", "HFD My Drivers - FileVALUE Forfeit - INDICATOR",
        "HFD My Drivers - FileVALUE Maintenance - INDICATOR", "HFD My Drivers - FileVALUE Gas - INDICATOR"
    };
    private static final int TERMOMETR_Loyalty = 0;
    private static final int TERMOMETR_Return = 1;
    private static final int TERMOMETR_Wins_Missions = 2;
    private static final int TERMOMETR_Forfeit = 3;
    private static final int TERMOMETR_Maintenance = 4;
    private static final int TERMOMETR_Gas = 5;
    private MyTeam myTeamTable = null;
    private Hirelings hirelingsTable = null;
    private SearchFilter hirelingFilter = null;
    private final String[] initialSummaryTexts = new String[SUMMARY_TEXTS.length];
    private final long[] summaryTexts = new long[SUMMARY_TEXTS.length];
    private TooltipButton tooltipbutton = null;
    private PoPUpMenu warning_rank = null;
    TermometrClass[] termometrs = null;
    long _menu = 0L;
    sort myteam_table_sort = new sort(1, true);
    sort hirelings_table_sort = new sort(3, true);
    String tool_tip_text = null;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param parent
     */
    public HireDrivers(long _menu, OfficeMenu parent) {
        super(_menu, "HIRE DRIVERS", parent);
        this._menu = _menu;
        init(_menu);
    }

    private void init(long _menu) {
        this.hirelingFilter = new SearchFilter(_menu);
        this.myTeamTable = new MyTeam(_menu);
        this.hirelingsTable = new Hirelings(_menu);

        for (int i = 0; i < SUMMARY_TEXTS.length; ++i) {
            this.summaryTexts[i] = menues.FindFieldInMenu(_menu, SUMMARY_TEXTS[i]);
            this.initialSummaryTexts[i] = menues.GetFieldText(this.summaryTexts[i]);
        }

        this.tooltipbutton = new TooltipButton(_menu, "HFD My Drivers - Details",
                "..\\data\\config\\menu\\menu_office.xml", "TOOLTIP - HFD - Detailed VehicleName", TOOLTIPBUTTON_TEXT);
        this.warning_rank = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_office.xml",
                                          "MESSAGE - Not sufficient rank", "MESSAGE - Not sufficient rank");

        long control = menues.FindFieldInMenu(_menu, "CALL OFFICE HELP - HIRE/FIRE DRIVER");

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

        this.tooltipbutton.afterInit();
        this.tooltipbutton.Enable(false);
        this.myTeamTable.afterInit();
        this.hirelingsTable.afterInit();
        this.warning_rank.afterInit();
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

        this.myTeamTable.updateTable();
        this.hirelingsTable.updateTable();
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
        this.hirelingFilter.deinit();
        this.myTeamTable.table.deinit();
        this.hirelingsTable.table.deinit();
    }

    private void refresh_summary() {
        HireFireDriversManager.Summary summary = HireFireDriversManager.getHireFireDriversManager().GetSummary();

        for (int i = 0; i < this.summaryTexts.length; ++i) {
            KeyPair[] keys = new KeyPair[SUMMARY_MACRO_NUM[i]];

            switch (i) {
             case 0 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.vacant);

                 break;

             case 2 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.to_hire);

                 break;

             case 1 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.to_fire);

                 break;

             case 3 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int) summary.advance));

                 if (SUMMARY_MACRO_NUM[i] > 1) {
                     keys[1] = new KeyPair(SUMMARY_MACRO2[i], (Math.abs((int) summary.advance) >= 0)
                             ? ""
                             : "-");
                 }

                 break;

             case 4 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int) summary.commission));

                 if (SUMMARY_MACRO_NUM[i] > 1) {
                     keys[1] = new KeyPair(SUMMARY_MACRO2[i], (Math.abs((int) summary.commission) >= 0)
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

        int err = HireFireDriversManager.getHireFireDriversManager().Apply();

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
        HireFireDriversManager.getHireFireDriversManager().Discard();
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
            this.parent.ShowTabHelp(2);
        }
    }

    class Hirelings implements ISetupLine, ISelectLineListener {
        private final String SORT_METHOD = "onSort";
        private HireDrivers.Hirelingsline selected = null;
        private final HireDrivers.table_data_hirelingfleet TABLE_DATA = new HireDrivers.table_data_hirelingfleet();
        private long[] fullinfotexts = null;
        private String[] fullinfotexts_initials = null;
        Table table;
        private final String hireling_wage;
        private final long man_photo;

        Hirelings(long _menu) {
            this.table = new Table(_menu, "HFD Recruits - TABLEGROUP - 10 38", "Tableranger - HFD Recruits");
            this.table.setSelectionMode(2);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_office.xml", "Tablegroup - ELEMENTS - Hirelings",
                                     HireDrivers.TABLE_HIRELINGS_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            reciveTableData();
            build_tree_data();

            for (String name : HireDrivers.TABLE_HIRELINGS_ELEMENTS) {
                this.table.initLinesSelection(name);
            }

            this.fullinfotexts = new long[HireDrivers.HIRELINGS_FULL_INFO.length];
            this.fullinfotexts_initials = new String[HireDrivers.HIRELINGS_FULL_INFO.length];

            for (int i = 0; i < HireDrivers.HIRELINGS_FULL_INFO.length; ++i) {
                this.fullinfotexts[i] = menues.FindFieldInMenu(_menu, HireDrivers.access$800()[i]);
                this.fullinfotexts_initials[i] = menues.GetFieldText(this.fullinfotexts[i]);
            }

            for (int i = 0; i < HireDrivers.HIRELINGS_SORT.length; ++i) {
                long field = menues.FindFieldInMenu(_menu, HireDrivers.HIRELINGS_SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

                buts.userid = i;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }

            long[] stat_myteam_wage = this.table.getLineStatistics_controls(HireDrivers.TABLE_HIRELINGS_ELEMENTS[7]);
            long[] stat_hireling_wage = this.table.getLineStatistics_controls(HireDrivers.TABLE_HIRELINGS_ELEMENTS[3]);

            if ((stat_myteam_wage == null) || (stat_myteam_wage.length < 1)) {
                Log.menu("ERRORR. MyFleet table. Has no field named " + HireDrivers.TABLE_HIRELINGS_ELEMENTS[7]);
            }

            if ((stat_hireling_wage == null) || (stat_hireling_wage.length < 1)) {
                Log.menu("ERRORR. MyFleet table. Has no field named " + HireDrivers.TABLE_HIRELINGS_ELEMENTS[3]);
            }

            this.hireling_wage = menues.GetFieldText(stat_hireling_wage[0]);
            menues.SetScriptOnControl(_menu,
                                      menues.ConvertMenuFields(menues.FindFieldInMenu(_menu,
                                          HireDrivers.ACTION_BUTTONS[0])), this, HireDrivers.ACTION_METHODS[0], 4L);
            this.man_photo = menues.FindFieldInMenu(_menu, "HFD Recruits - Resume PHOTO");
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
        public void onHire(long _menu, MENUsimplebutton_field button) {
            HireDrivers.this.setDirty();

            Vector res = new Vector();

            for (HireDrivers.Hirelingsline line : this.TABLE_DATA.all_lines) {
                if ((line.selected) && (line.wheather_show)) {
                    res.add(line.id);
                }
            }

            int err = 0;

            if (!(res.isEmpty())) {
                err = HireFireDriversManager.getHireFireDriversManager().I_Hire(res);
            }

            if (3 == err) {
                HireDrivers.this.warning_rank.show();
            }

            HireDrivers.this.update();
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
                 HireDrivers.this.hirelings_table_sort = new HireDrivers.sort(1,
                         !(HireDrivers.this.hirelings_table_sort.up));

                 break;

             case 2 :
                 HireDrivers.this.hirelings_table_sort = new HireDrivers.sort(3,
                         !(HireDrivers.this.hirelings_table_sort.up));

                 break;

             case 1 :
                 HireDrivers.this.hirelings_table_sort = new HireDrivers.sort(2,
                         !(HireDrivers.this.hirelings_table_sort.up));

                 break;

             case 3 :
                 HireDrivers.this.hirelings_table_sort = new HireDrivers.sort(4,
                         !(HireDrivers.this.hirelings_table_sort.up));

                 break;

             case 4 :
                 HireDrivers.this.hirelings_table_sort = new HireDrivers.sort(5,
                         !(HireDrivers.this.hirelings_table_sort.up));

                 break;

             case 5 :
                 HireDrivers.this.hirelings_table_sort = new HireDrivers.sort(6,
                         !(HireDrivers.this.hirelings_table_sort.up));

                 break;

             case 6 :
                 HireDrivers.this.hirelings_table_sort = new HireDrivers.sort(7,
                         !(HireDrivers.this.hirelings_table_sort.up));

                 break;

             case 7 :
                 HireDrivers.this.hirelings_table_sort = new HireDrivers.sort(8,
                         !(HireDrivers.this.hirelings_table_sort.up));

                 break;

             case 8 :
                 HireDrivers.this.hirelings_table_sort = new HireDrivers.sort(9,
                         !(HireDrivers.this.hirelings_table_sort.up));

                 break;

             case 9 :
                 HireDrivers.this.hirelings_table_sort = new HireDrivers.sort(10,
                         !(HireDrivers.this.hirelings_table_sort.up));
            }

            updateTable();
            HireDrivers.this.refresh_summary();
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
                    HireDrivers.Hirelingsline data = new HireDrivers.Hirelingsline();

                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
            } else {
                int count_good_data = 0;
                Iterator iter = this.TABLE_DATA.all_lines.iterator();

                while ((iter.hasNext()) && (((HireDrivers.Hirelingsline) iter.next()).wheather_show)) {
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
            this.TABLE_DATA.all_lines.clear();

            Vector drids = HireFireDriversManager.getHireFireDriversManager().GetDealerVehiclesList(
                               HireDrivers.access$1100(HireDrivers.this).filter,
                               HireDrivers.this.hirelings_table_sort.type, HireDrivers.this.hirelings_table_sort.up);

            for (HireFireDriversManager.ShotDealerDriverInfo inf : drids) {
                HireDrivers.Hirelingsline data = new HireDrivers.Hirelingsline();

                data.id = inf.id;
                data.driver_name = inf.driver_name;
                data.dealer_name = inf.dealer_name;
                data.rank = inf.rank;
                data.wage = inf.wage;
                data.isGray = inf.justFire;
                data.isGray2 = (!(inf.bCanHire));
                data.wheather_show = true;
                this.TABLE_DATA.all_lines.add(data);
            }

            buildvoidcells();
        }

        /**
         * Method description
         *
         */
        public void updateTable() {
            this.selected = null;
            reciveTableData();
            build_tree_data(new CompareDealerDrivers());
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
            HireDrivers.Hirelingsline line = (HireDrivers.Hirelingsline) table_node.item;

            if (!(line.wheather_show)) {
                menues.SetShowField(obj.nativePointer, false);

                return;
            }

            int position = this.table.getMarkedPosition(obj.nativePointer);

            switch (position) {
             case 0 :
                 if ((line.isGray) || (line.isGray2)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.driver_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 1 :
                 if ((line.isGray) || (line.isGray2)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.dealer_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 2 :
                 if ((line.isGray) || (line.isGray2)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, "" + line.rank);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 3 :
                 if ((line.isGray) || (line.isGray2)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.wage));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.hireling_wage, macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 4 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.driver_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 5 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.dealer_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 6 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, "" + line.rank);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 7 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.wage));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.hireling_wage, macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 8 :
                 if (!(line.isGray2)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.driver_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 9 :
                 if (!(line.isGray2)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.dealer_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 10 :
                 if (!(line.isGray2)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, "" + line.rank);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 11 :
                 if (!(line.isGray2)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.wage));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.hireling_wage, macro));
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
            for (HireDrivers.Hirelingsline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }

            HireDrivers.Hirelingsline data = (HireDrivers.Hirelingsline) table.getItemOnLine(line).item;

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
            for (HireDrivers.Hirelingsline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }

            for (Cmenu_TTI item : lines) {
                if (item.item != null) {
                    HireDrivers.Hirelingsline data = (HireDrivers.Hirelingsline) item.item;

                    data.selected = true;
                }
            }

            this.selected = ((HireDrivers.Hirelingsline) table.getSelectedData().item);
            updateSelectedInfo();
        }

        private void updateSelectedInfo() {
            if (null == this.selected.id) {
                for (int i = 0; i < this.fullinfotexts.length; ++i) {
                    menues.SetFieldText(this.fullinfotexts[i], "---");
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
                }

                return;
            }

            HireFireDriversManager.FullDealerDriverInfo inf =
                HireFireDriversManager.getHireFireDriversManager().GetDealerVehiclesInfo(this.selected.id);
            boolean bad = this.selected.id.id == 0;

            for (int i = 0; i < this.fullinfotexts.length; ++i) {
                switch (i) {
                 case 0 :
                     if (!(bad)) {
                         KeyPair[] keys = new KeyPair[2];

                         keys[0] = new KeyPair("NAME", inf.driver_name);
                         keys[1] = new KeyPair("AGE", "" + inf.age);
                         menues.SetFieldText(this.fullinfotexts[i],
                                             MacroKit.Parse(this.fullinfotexts_initials[i], keys));
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 1 :
                     if (!(bad)) {
                         KeyPair[] keys = new KeyPair[1];

                         keys[0] = new KeyPair("VALUE", "" + inf.age);
                         menues.SetFieldText(this.fullinfotexts[i],
                                             MacroKit.Parse(this.fullinfotexts_initials[i], keys));
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 2 :
                     if (!(bad)) {
                         menues.SetFieldText(this.fullinfotexts[i], inf.gender);
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 3 :
                     if (!(bad)) {
                         menues.SetFieldText(this.fullinfotexts[i], "" + inf.tickets);
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 4 :
                     if (!(bad)) {
                         menues.SetFieldText(this.fullinfotexts[i], "" + inf.accidents);
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 5 :
                     if (!(bad)) {
                         menues.SetFieldText(this.fullinfotexts[i], (inf.bHasFelony)
                                 ? HireDrivers.HIRELINGS_FULL_INFO_HASFELONY[0]
                                 : HireDrivers.HIRELINGS_FULL_INFO_HASFELONY[1]);
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 6 :
                     if (!(bad)) {
                         KeyPair[] exp = new KeyPair[1];

                         exp[0] = new KeyPair("VALUE", "" + inf.experience);
                         menues.SetFieldText(this.fullinfotexts[i],
                                             MacroKit.Parse(this.fullinfotexts_initials[i], exp));
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }
                }

                menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
            }

            IconMappings.remapPersonIcon("" + inf.face_index, this.man_photo);
        }

        class CompareDealerDrivers implements ICompareLines {

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

                HireDrivers.Hirelingsline line1 = (HireDrivers.Hirelingsline) object1;
                HireDrivers.Hirelingsline line2 = (HireDrivers.Hirelingsline) object2;

                return ((line1.id != null) && (line2.id != null) && (line1.id.id == line2.id.id));
            }
        }
    }


    static class Hirelingsline {
        HireFireDriversManager.DriverId id;
        String driver_name;
        String dealer_name;
        int rank;
        float wage;
        boolean isGray;
        boolean isGray2;
        boolean selected;
        boolean wheather_show;

        Hirelingsline() {
            this.id = new HireFireDriversManager.DriverId();
            this.selected = false;
        }
    }


    class MyTeam implements ISetupLine, ISelectLineListener {
        private final String SORT_METHOD = "onSort";
        HireDrivers.Myteamline selected = null;
        private long[] fullinfotexts = null;
        private String[] fullinfotexts_iitials = null;
        private final HireDrivers.table_data_myteam TABLE_DATA = new HireDrivers.table_data_myteam();
        private long man_photo = 0L;
        Table table;
        private final String myteam_wage;

        MyTeam(long _menu) {
            this.table = new Table(_menu, "HFD My Drivers - TABLEGROUP - 9 38", "Tableranger - HFD My Drivers");
            this.table.setSelectionMode(2);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_office.xml", "Tablegroup - ELEMENTS - MyTeam",
                                     HireDrivers.TABLE_MYTEAM_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            reciveTableData();
            build_tree_data();

            for (String name : HireDrivers.TABLE_MYTEAM_ELEMENTS) {
                this.table.initLinesSelection(name);
            }

            this.fullinfotexts = new long[HireDrivers.MYTEAM_FULL_INFO.length];
            this.fullinfotexts_iitials = new String[HireDrivers.MYTEAM_FULL_INFO.length];

            for (int i = 0; i < HireDrivers.MYTEAM_FULL_INFO.length; ++i) {
                this.fullinfotexts[i] = menues.FindFieldInMenu(_menu, HireDrivers.access$100()[i]);
                this.fullinfotexts_iitials[i] = menues.GetFieldText(this.fullinfotexts[i]);
            }

            for (int i = 0; i < HireDrivers.MYTEAM_SORT.length; ++i) {
                long field = menues.FindFieldInMenu(_menu, HireDrivers.MYTEAM_SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

                buts.userid = i;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }

            long[] stat_myteam_wage = this.table.getLineStatistics_controls(HireDrivers.TABLE_MYTEAM_ELEMENTS[2]);

            if ((stat_myteam_wage == null) || (stat_myteam_wage.length < 1)) {
                Log.menu("ERRORR. MyFleet table. Has no field named " + HireDrivers.TABLE_MYTEAM_ELEMENTS[2]);
            }

            this.myteam_wage = menues.GetFieldText(stat_myteam_wage[0]);
            menues.SetScriptOnControl(_menu,
                                      menues.ConvertMenuFields(menues.FindFieldInMenu(_menu,
                                          HireDrivers.ACTION_BUTTONS[1])), this, HireDrivers.ACTION_METHODS[1], 4L);
            this.man_photo = menues.FindFieldInMenu(_menu, "HFD My Drivers - DriverFile PHOTO");
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
        public void onFire(long _menu, MENUsimplebutton_field button) {
            HireDrivers.this.setDirty();

            Vector res = new Vector();

            for (HireDrivers.Myteamline line : this.TABLE_DATA.all_lines) {
                if ((line.selected) && (line.wheather_show)) {
                    res.add(line.id);
                }
            }

            if (!(res.isEmpty())) {
                HireFireDriversManager.getHireFireDriversManager().I_Fire(res);
            }

            HireDrivers.this.update();
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
                 HireDrivers.this.myteam_table_sort = new HireDrivers.sort(1, !(HireDrivers.this.myteam_table_sort.up));

                 break;

             case 1 :
                 HireDrivers.this.myteam_table_sort = new HireDrivers.sort(2, !(HireDrivers.this.myteam_table_sort.up));

                 break;

             case 4 :
                 HireDrivers.this.myteam_table_sort = new HireDrivers.sort(5, !(HireDrivers.this.myteam_table_sort.up));

                 break;

             case 2 :
                 HireDrivers.this.myteam_table_sort = new HireDrivers.sort(3, !(HireDrivers.this.myteam_table_sort.up));

                 break;

             case 6 :
                 HireDrivers.this.myteam_table_sort = new HireDrivers.sort(7, !(HireDrivers.this.myteam_table_sort.up));

                 break;

             case 3 :
                 HireDrivers.this.myteam_table_sort = new HireDrivers.sort(4, !(HireDrivers.this.myteam_table_sort.up));

                 break;

             case 8 :
                 HireDrivers.this.myteam_table_sort = new HireDrivers.sort(9, !(HireDrivers.this.myteam_table_sort.up));

                 break;

             case 7 :
                 HireDrivers.this.myteam_table_sort = new HireDrivers.sort(8, !(HireDrivers.this.myteam_table_sort.up));

                 break;

             case 5 :
                 HireDrivers.this.myteam_table_sort = new HireDrivers.sort(6, !(HireDrivers.this.myteam_table_sort.up));

                 break;

             case 9 :
                 HireDrivers.this.myteam_table_sort = new HireDrivers.sort(10,
                         !(HireDrivers.this.myteam_table_sort.up));
            }

            updateTable();
            HireDrivers.this.refresh_summary();
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
                    HireDrivers.Myteamline data = new HireDrivers.Myteamline();

                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
            } else {
                int count_good_data = 0;
                Iterator iter = this.TABLE_DATA.all_lines.iterator();

                while ((iter.hasNext()) && (((HireDrivers.Myteamline) iter.next()).wheather_show)) {
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
            this.TABLE_DATA.all_lines.clear();

            Vector drids = HireFireDriversManager.getHireFireDriversManager().GetMyDriverList(
                               HireDrivers.this.myteam_table_sort.type, HireDrivers.this.myteam_table_sort.up);

            for (HireFireDriversManager.ShotMyDriverInfo inf : drids) {
                HireDrivers.Myteamline data = new HireDrivers.Myteamline();

                data.id = inf.id;
                data.driver_name = inf.driver_name;
                data.rank = inf.rank;
                data.wage = inf.wage;
                data.isGray = inf.justHire;
                data.wheather_show = true;
                this.TABLE_DATA.all_lines.add(data);
            }

            buildvoidcells();
        }

        /**
         * Method description
         *
         */
        public void updateTable() {
            this.selected = null;
            reciveTableData();
            build_tree_data(new CompareMyDrivers());
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
            HireDrivers.Myteamline line = (HireDrivers.Myteamline) table_node.item;

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
                 menues.SetFieldText(obj.nativePointer, line.driver_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 1 :
                 if (line.isGray) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, "" + line.rank);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 2 :
                 if (line.isGray) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.wage));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.myteam_wage, macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 3 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, line.driver_name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 4 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, "" + line.rank);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 5 :
                 if (!(line.isGray)) {
                     menues.SetShowField(obj.nativePointer, false);

                     return;
                 }

                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.wage));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, MacroKit.Parse(this.myteam_wage, macro));
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
            for (HireDrivers.Myteamline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }

            HireDrivers.Myteamline data = (HireDrivers.Myteamline) table.getItemOnLine(line).item;

            data.selected = true;
            this.selected = data;
            updateSelectedInfo(this.selected.id);
        }

        /**
         * Method description
         *
         *
         * @param table
         * @param lines
         */
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
            for (HireDrivers.Myteamline item : this.TABLE_DATA.all_lines) {
                item.selected = false;
            }

            for (Cmenu_TTI item : lines) {
                if (item.item != null) {
                    HireDrivers.Myteamline data = (HireDrivers.Myteamline) item.item;

                    data.selected = true;
                }
            }

            this.selected = ((HireDrivers.Myteamline) table.getSelectedData().item);
            updateSelectedInfo(this.selected.id);
        }

        private void updateSelectedInfo(HireFireDriversManager.DriverId id) {
            if (HireDrivers.this.tool_tip_text == null) {
                HireDrivers.this.tool_tip_text = HireDrivers.this.tooltipbutton.GetText();
            }

            if (null == id) {
                for (int i = 0; i < this.fullinfotexts.length; ++i) {
                    switch (i) {
                     case 7 :
                         menues.SetShowField(this.fullinfotexts[i], true);

                         break;

                     case 8 :
                         menues.SetShowField(this.fullinfotexts[i], false);
                    }

                    menues.SetFieldText(this.fullinfotexts[i], "---");
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
                }

                for (int i = 0; i < HireDrivers.this.termometrs.length; ++i) {
                    HireDrivers.this.termometrs[i].Update(0);
                }

                HireDrivers.this.tooltipbutton.Enable(false);

                return;
            }

            HireFireDriversManager.FullMyDriverInfo inf =
                HireFireDriversManager.getHireFireDriversManager().GetMyDriverInfo(id);
            boolean bad = this.selected.id.id == 0;

            for (int i = 0; i < this.fullinfotexts.length; ++i) {
                switch (i) {
                 case 0 :
                     if (!(bad)) {
                         KeyPair[] keys = new KeyPair[2];

                         keys[0] = new KeyPair("NAME", inf.driver_name);
                         keys[1] = new KeyPair("AGE", "" + inf.age);
                         menues.SetFieldText(this.fullinfotexts[i],
                                             MacroKit.Parse(this.fullinfotexts_iitials[i], keys));
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 1 :
                     if (!(bad)) {
                         menues.SetFieldText(this.fullinfotexts[i], "" + (int) inf.loyalty + "%");
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 2 :
                     if (!(bad)) {
                         KeyPair[] keysroi = new KeyPair[2];

                         keysroi[0] = new KeyPair("MONEY", Helper.convertMoney(Math.abs((int) inf.roi)));
                         keysroi[1] = new KeyPair("SIGN", (inf.roi >= 0.0F)
                                                          ? ""
                                                          : "-");
                         menues.SetFieldText(this.fullinfotexts[i],
                                             MacroKit.Parse(this.fullinfotexts_iitials[i], keysroi));
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 3 :
                     if (!(bad)) {
                         KeyPair[] keys2 = new KeyPair[1];

                         keys2[0] = new KeyPair("WINS", "" + inf.wins_missions);
                         menues.SetFieldText(this.fullinfotexts[i],
                                             MacroKit.Parse(this.fullinfotexts_iitials[i], keys2));
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 4 :
                     if (!(bad)) {
                         KeyPair[] keys3 = new KeyPair[1];

                         keys3[0] = new KeyPair("MONEY", Helper.convertMoney((int) inf.forefit));
                         menues.SetFieldText(this.fullinfotexts[i],
                                             MacroKit.Parse(this.fullinfotexts_iitials[i], keys3));
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 5 :
                     if (!(bad)) {
                         KeyPair[] keys4 = new KeyPair[1];

                         keys4[0] = new KeyPair("MONEY", Helper.convertMoney((int) inf.maintenance));
                         menues.SetFieldText(this.fullinfotexts[i],
                                             MacroKit.Parse(this.fullinfotexts_iitials[i], keys4));
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 6 :
                     if (!(bad)) {
                         KeyPair[] keys5 = new KeyPair[1];

                         keys5[0] = new KeyPair("MONEY", Helper.convertMoney((int) inf.gas));
                         menues.SetFieldText(this.fullinfotexts[i],
                                             MacroKit.Parse(this.fullinfotexts_iitials[i], keys5));
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 7 :
                     if (!(bad)) {
                         menues.SetShowField(this.fullinfotexts[i], !(inf.vehJustBought));
                         menues.SetFieldText(this.fullinfotexts[i], inf.short_vehicle_name);
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }

                     break;

                 case 8 :
                     if (!(bad)) {
                         menues.SetShowField(this.fullinfotexts[i], inf.vehJustBought);
                         menues.SetFieldText(this.fullinfotexts[i], inf.short_vehicle_name);
                     } else {
                         menues.SetFieldText(this.fullinfotexts[i], "---");
                     }
                }

                menues.UpdateMenuField(menues.ConvertMenuFields(this.fullinfotexts[i]));
            }

            for (int i = 0; i < HireDrivers.this.termometrs.length; ++i) {
                switch (i) {
                 case 0 :
                     if (!(bad)) {
                         HireDrivers.this.termometrs[i].Update((int) (100.0D * inf.loyalty_temp));
                     } else {
                         HireDrivers.this.termometrs[i].Update(0);
                     }

                     break;

                 case 1 :
                     if (!(bad)) {
                         HireDrivers.this.termometrs[i].Update((int) (100.0D * inf.roi_temp));
                     } else {
                         HireDrivers.this.termometrs[i].Update(0);
                     }

                     break;

                 case 2 :
                     if (!(bad)) {
                         HireDrivers.this.termometrs[i].Update((int) (100.0D * inf.wins_temp));
                     } else {
                         HireDrivers.this.termometrs[i].Update(0);
                     }

                     break;

                 case 3 :
                     if (!(bad)) {
                         HireDrivers.this.termometrs[i].Update((int) (100.0D * inf.forefit_temp));
                     } else {
                         HireDrivers.this.termometrs[i].Update(0);
                     }

                     break;

                 case 4 :
                     if (!(bad)) {
                         HireDrivers.this.termometrs[i].Update((int) (100.0D * inf.maintenance_temp));
                     } else {
                         HireDrivers.this.termometrs[i].Update(0);
                     }

                     break;

                 case 5 :
                     if (!(bad)) {
                         HireDrivers.this.termometrs[i].Update((int) (100.0D * inf.gas_temp));
                     } else {
                         HireDrivers.this.termometrs[i].Update(0);
                     }
                }
            }

            IconMappings.remapPersonIcon("" + inf.face_index, this.man_photo);

            if ((inf.make == null) || (inf.make.length() == 0) || (inf.model == null) || (inf.model.length() == 0)
                    || (inf.license_plate == null) || (inf.license_plate.length() == 0)) {
                HireDrivers.this.tooltipbutton.Enable(false);
            } else {
                KeyPair[] pairs = { new KeyPair("MAKE", inf.make), new KeyPair("MODEL", inf.model),
                                    new KeyPair("LICENSE_PLATE", inf.license_plate) };

                HireDrivers.this.tooltipbutton.setText(MacroKit.Parse(HireDrivers.this.tool_tip_text, pairs));
                HireDrivers.this.tooltipbutton.Enable(true);
                HireDrivers.this.tooltipbutton.setState((inf.vehJustBought)
                        ? 1
                        : 0);
            }
        }

        class CompareMyDrivers implements ICompareLines {

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

                HireDrivers.Myteamline line1 = (HireDrivers.Myteamline) object1;
                HireDrivers.Myteamline line2 = (HireDrivers.Myteamline) object2;

                return ((line1.id != null) && (line2.id != null) && (line1.id.id == line2.id.id));
            }
        }
    }


    static class Myteamline {
        HireFireDriversManager.DriverId id;
        String driver_name;
        int rank;
        float wage;
        boolean isGray;
        boolean selected;
        boolean wheather_show;

        Myteamline() {
            this.selected = false;
        }
    }


    class SearchFilter implements IChoosedata {
        private static final int _IDanimateClose = 2;
        private final String SEARCH_METH = "onSearch";
        private final String ANIMATE_METH = "onAnimate";
        private PopUpSearch search = null;
        private long[] texts = null;
        private int[] shifts = null;
        private boolean pend_close = false;
        private int filter_current = 0;
        private int filter_choose = 0;
        private String[] lastFilter = null;
        private HireFireDriversManager.Filter filter = null;

        SearchFilter(long _menu) {
            this.search = new PopUpSearch(_menu, HireDrivers.SEARCH_MENU_GROUP);
            this.search.addListener(this);
            this.shifts = new int[HireDrivers.SEARCH_BUTTONS.length];

            for (int i = 0; i < HireDrivers.SEARCH_BUTTONS.length; ++i) {
                long sbutt = menues.FindFieldInMenu(_menu, HireDrivers.SEARCH_BUTTONS[i]);
                MENUsimplebutton_field button = menues.ConvertSimpleButton(sbutt);

                button.userid = i;
                this.shifts[i] = button.poy;
                menues.SetScriptOnControl(_menu, button, this, "onSearch", 4L);
                menues.UpdateField(button);
            }

            for (int i = this.shifts.length - 1; i >= 0; --i) {
                this.shifts[i] -= this.shifts[0];
            }

            menues.SetScriptObjectAnimation(0L, 2L, this, "onAnimate");
            this.texts = new long[HireDrivers.SEARCH_TEXTS.length];

            for (int i = 0; i < HireDrivers.SEARCH_TEXTS.length; ++i) {
                this.texts[i] = menues.FindFieldInMenu(_menu, HireDrivers.access$1600()[i]);

                String[] textdata = getFilterValues(i);

                if (textdata.length > 0) {
                    menues.SetFieldText(this.texts[i], textdata[(textdata.length - 1)]);
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[i]));
                }
            }

            this.filter = new HireFireDriversManager.Filter();
            this.filter.fileds = new int[6];
        }

        /**
         * Method description
         *
         */
        public void deinit() {
            menues.StopScriptAnimation(2L);
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
        }

        /**
         * Method description
         *
         *
         * @param cookie
         * @param time
         */
        public void onAnimate(long cookie, double time) {
            if (this.pend_close) {
                this.search.hide();
                updateFilter();
            }

            this.pend_close = false;
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
        }

        void updateFilter() {
            int ch = this.filter_choose;
            Vector fields =
                HireFireDriversManager.getHireFireDriversManager().GetShortFilterFields(this.filter_current);
            int res_choose = 0;

            for (HireFireDriversManager.FilterField f : fields) {
                ch -= ((f.show_me)
                       ? 1
                       : 0);

                if (ch < 0) {
                    break;
                }

                ++res_choose;
            }

            this.filter.fileds[this.filter_current] = res_choose;
            HireFireDriversManager.getHireFireDriversManager().SetFilter(this.filter);
            HireDrivers.this.hirelingsTable.updateTable();
            menues.SetFieldText(this.texts[this.filter_current], this.lastFilter[this.filter_choose]);
            menues.UpdateMenuField(menues.ConvertMenuFields(this.texts[this.filter_current]));
        }

        String[] getFilterValues(int taken) {
            int flt = 0;

            switch (taken) {
             case 1 :
                 flt = 1;

                 break;

             case 3 :
                 flt = 3;

                 break;

             case 5 :
                 flt = 5;

                 break;

             case 4 :
                 flt = 4;

                 break;

             case 0 :
                 flt = 0;

                 break;

             case 2 :
                 flt = 2;
            }

            Vector fields = HireFireDriversManager.getHireFireDriversManager().GetFilterFields(flt);
            int count = 0;

            for (HireFireDriversManager.FilterField f : fields) {
                count += ((f.show_me)
                          ? 1
                          : 0);
            }

            String[] data = new String[count];

            count = 0;

            for (int i = 0; i < fields.size(); ++i) {
                if (((HireFireDriversManager.FilterField) fields.get(i)).show_me) {
                    data[(count++)] = ((HireFireDriversManager.FilterField) fields.get(i)).name;
                }
            }

            this.lastFilter = data;

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


    static class sort {
        int type;
        boolean up;

        sort(int type, boolean up) {
            this.type = type;
            this.up = up;
        }
    }


    static class table_data_hirelingfleet {
        Vector<HireDrivers.Hirelingsline> all_lines;

        table_data_hirelingfleet() {
            this.all_lines = new Vector();
        }
    }


    static class table_data_myteam {
        Vector<HireDrivers.Myteamline> all_lines;

        table_data_myteam() {
            this.all_lines = new Vector();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
