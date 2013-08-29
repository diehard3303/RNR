/*
 * @(#)GlobalStatistics.java   13/08/26
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
import rnr.src.menu.JavaEvents;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUEditBox;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUTruckview;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.menues;
import rnr.src.menuscript.table.ISetupLine;
import rnr.src.menuscript.table.Table;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class GlobalStatistics extends ApplicationTab {
    private static final String TAB_NAME = "NO";
    private static final String[] GOLDCARRIER_FIELDS = { "GS - Company GOLD CARRIER", "GS - Coverage GOLD CARRIER",
            "GS - Turnover GOLD CARRIER", "GS - Drivers GOLD CARRIER" };
    private static final int GOLDCARRIER_COMPANY = 0;
    private static final int GOLDCARRIER_COVERAGE = 1;
    private static final int GOLDCARRIER_TURNOVER = 2;
    private static final int GOLDCARRIER_DRIVERS = 3;
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String CONTROL_GROUP0 = "GS 01 - ALL";
    private static final String CONTROL_GROUP1 = "GS 02 - ALL";
    private static final String CONTROL_GROUP2 = "GS 03 - ALL";
    private static final String TABLE0 = "GS 01 - TABLEGROUP - 8 38";
    private static final String TABLE1 = "GS 02 - TABLEGROUP - 7 38";
    private static final String TABLE2 = "GS 03 - TABLEGROUP - 8 38";
    private static final String TABLE_LINE = "Tablegroup - ELEMENTS - Global Statistics - RANK";
    private static final String[] COMPANY_FIELDS = {
        "GS - Rank VALUE", "GS - Company VALUE", "GS - Coverage VALUE", "GS - Turnover VALUE", "GS - Drivers VALUE",
        "GS - GoldCarrierStatus GOLDLINE", "GS - GoldCarrierStatus MEDAL"
    };
    private static final int COMPANY_RANK = 0;
    private static final int COMPANY_COMPANY = 1;
    private static final int COMPANY_COVERAGE = 2;
    private static final int COMPANY_TURNOVER = 3;
    private static final int COMPANY_DRIVERS = 4;
    private static final int GOLDLINE = 5;
    private static final int MEDAL = 6;
    private static final String MACRO_VALUE = "VALUE";
    private Table table0 = null;
    private Table table1 = null;
    private Table table2 = null;
    private StatTable statistics0 = null;
    private StatTable statistics1 = null;
    private StatTable statistics2 = null;
    private final String[] COMPANY_FIELDS_TEXTS = new String[COMPANY_FIELDS.length];
    private final String[][] GOLD_TEXTS = new String[3][GOLDCARRIER_FIELDS.length];
    private final long[][] gold = new long[3][GOLDCARRIER_FIELDS.length];
    private long gs01 = 0L;
    private long gs02 = 0L;
    private long gs03 = 0L;
    private Info globalStats = null;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param parent
     */
    public GlobalStatistics(long _menu, OfficeMenu parent) {
        super(_menu, "NO", parent);
        this.gs01 = menues.FindFieldInMenu(_menu, "GS 01 - ALL");
        this.gs02 = menues.FindFieldInMenu(_menu, "GS 02 - ALL");
        this.gs03 = menues.FindFieldInMenu(_menu, "GS 03 - ALL");

        for (int panel = 0; panel < 3; ++panel) {
            for (int i = 0; i < GOLDCARRIER_FIELDS.length; ++i) {
                this.gold[panel][i] = menues.FindFieldInMenu(_menu, GOLDCARRIER_FIELDS[i] + " " + (panel + 1));

                if (this.gold[panel][i] != 0L) {
                    this.GOLD_TEXTS[panel][i] = menues.GetFieldText(this.gold[panel][i]);
                }
            }
        }

        this.table0 = new Table(_menu, "GS 01 - TABLEGROUP - 8 38", null);
        this.table0.fillWithLines("..\\data\\config\\menu\\menu_office.xml",
                                  "Tablegroup - ELEMENTS - Global Statistics - RANK", COMPANY_FIELDS);
        this.table1 = new Table(_menu, "GS 02 - TABLEGROUP - 7 38", null);
        this.table1.fillWithLines("..\\data\\config\\menu\\menu_office.xml",
                                  "Tablegroup - ELEMENTS - Global Statistics - RANK", COMPANY_FIELDS);
        this.table2 = new Table(_menu, "GS 03 - TABLEGROUP - 8 38", null);
        this.table2.fillWithLines("..\\data\\config\\menu\\menu_office.xml",
                                  "Tablegroup - ELEMENTS - Global Statistics - RANK", COMPANY_FIELDS);

        for (int i = 0; i < COMPANY_FIELDS.length; ++i) {
            long field = menues.FindFieldInMenu(_menu, COMPANY_FIELDS[i]);

            this.COMPANY_FIELDS_TEXTS[i] = menues.GetFieldText(field);
        }

        long control = menues.FindFieldInMenu(_menu, "CALL OFFICE HELP - GLOBAL STATISTICS");

        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
        this.statistics0 = new StatTable(this.table0);
        this.table0.takeSetuperForAllLines(this.statistics0);
        this.table0.setSelectionMode(0);
        this.statistics1 = new StatTable(this.table1);
        this.table1.takeSetuperForAllLines(this.statistics1);
        this.table1.setSelectionMode(0);
        this.statistics2 = new StatTable(this.table2);
        this.table2.takeSetuperForAllLines(this.statistics2);
        this.table2.setSelectionMode(0);
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        this.table0.afterInit();
        this.table1.afterInit();
        this.table2.afterInit();
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

        this.globalStats = new Info();
        JavaEvents.SendEvent(44, 7, this.globalStats);
        this.statistics0.updateTable();
        this.statistics1.updateTable();
        this.statistics2.updateTable();
        updateGoldCarrier();

        return true;
    }

    /**
     * Method description
     *
     */
    @Override
    public void deinit() {
        this.table0.deinit();
        this.table1.deinit();
        this.table2.deinit();
    }

    /**
     * Method description
     *
     */
    @Override
    public void apply() {}

    /**
     * Method description
     *
     */
    @Override
    public void discard() {}

    private void updateGoldCarrier() {
        if (!(this.globalStats.goldcarreir.economics_done)) {
            if (!(this.globalStats.goldcarreir.we_have_winner)) {
                menues.SetShowField(this.gs01, true);
                menues.SetShowField(this.gs02, false);
                menues.SetShowField(this.gs03, false);

                for (int pane = 0; pane < 3; ++pane) {
                    for (int i = 0; i < this.gold[pane].length; ++i) {
                        if (this.gold[pane][i] != 0L) {
                            menues.SetFieldText(this.gold[pane][i], "---");
                            menues.UpdateMenuField(menues.ConvertMenuFields(this.gold[pane][i]));
                        }
                    }
                }
            } else {
                menues.SetShowField(this.gs01, false);
                menues.SetShowField(this.gs02, true);
                menues.SetShowField(this.gs03, false);

                String company = this.globalStats.goldcarreir.company;
                int turnover = this.globalStats.goldcarreir.turnover;
                int coverage = this.globalStats.goldcarreir.coverage;
                int drivers = this.globalStats.goldcarreir.drivers;

                for (int pane = 0; pane < 3; ++pane) {
                    for (int i = 0; i < this.gold[pane].length; ++i) {
                        if (this.gold[pane][i] != 0L) {
                            switch (i) {
                             case 0 :
                                 menues.SetFieldText(this.gold[pane][i], company);

                                 break;

                             case 1 :
                                 KeyPair[] _macro = new KeyPair[1];

                                 _macro[0] = new KeyPair("VALYE", "" + coverage);

                                 if (this.GOLD_TEXTS[pane][i] != null) {
                                     menues.SetFieldText(this.gold[pane][i],
                                                         MacroKit.Parse(this.GOLD_TEXTS[pane][i], _macro));
                                 }

                                 break;

                             case 2 :
                                 KeyPair[] macro = new KeyPair[2];

                                 macro[0] = new KeyPair("SIGN", "" + ((turnover >= 0)
                                         ? ""
                                         : "-"));
                                 macro[1] = new KeyPair("MONEY", Helper.convertMoney(turnover));

                                 if (this.GOLD_TEXTS[pane][i] != null) {
                                     menues.SetFieldText(this.gold[pane][i],
                                                         MacroKit.Parse(this.GOLD_TEXTS[pane][i], macro));
                                 }

                                 break;

                             case 3 :
                                 menues.SetFieldText(this.gold[pane][i], "" + drivers);
                            }

                            menues.UpdateMenuField(menues.ConvertMenuFields(this.gold[pane][i]));
                        }
                    }
                }
            }
        } else {
            menues.SetShowField(this.gs01, false);
            menues.SetShowField(this.gs02, false);
            menues.SetShowField(this.gs03, true);

            for (int pane = 0; pane < 3; ++pane) {
                for (int i = 0; i < this.gold[pane].length; ++i) {
                    if (this.gold[pane][i] != 0L) {
                        menues.SetFieldText(this.gold[pane][i], "---");
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.gold[pane][i]));
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
    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(4);
        }
    }

    static class GoldCarreir {
        boolean economics_done;
        boolean we_have_winner;
        String company;
        int turnover;
        int drivers;
        int coverage;

        GoldCarreir() {
            this.economics_done = false;
            this.we_have_winner = false;
        }
    }


    static class Info {
        GlobalStatistics.GoldCarreir goldcarreir;
        Vector ccompanies;

        Info() {
            this.goldcarreir = new GlobalStatistics.GoldCarreir();
            this.ccompanies = new Vector();
        }
    }


    static class Line {
        int rank;
        String company;
        int coverage;
        int turnover;
        int drivers;
        boolean has_won;
        boolean selected;
        boolean wheather_show;

        Line() {
            this.selected = false;
        }
    }


    static class SingleCompany {
        int rank;
        String company;
        int coverage;
        int turnover;
        int drivers;
        boolean has_won;
    }


    class StatTable implements ISetupLine {
        private final GlobalStatistics.table_data TABLE_DATA = new GlobalStatistics.table_data();
        Table table;

        StatTable(Table paramTable) {
            this.table = paramTable;
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
                    GlobalStatistics.Line data = new GlobalStatistics.Line();

                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
            } else {
                int count_good_data = 0;
                Iterator iter = this.TABLE_DATA.all_lines.iterator();

                while ((iter.hasNext()) && (((GlobalStatistics.Line) iter.next()).wheather_show)) {
                    ++count_good_data;
                }

                if ((count_good_data < this.table.getNumRows())
                        || (count_good_data >= this.TABLE_DATA.all_lines.size())) {
                    return;
                }

                for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                    this.TABLE_DATA.all_lines.remove(i);
                }
            }
        }

        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();

            Iterator iter = GlobalStatistics.this.globalStats.ccompanies.iterator();

            while (iter.hasNext()) {
                GlobalStatistics.SingleCompany company = (GlobalStatistics.SingleCompany) iter.next();
                GlobalStatistics.Line data = new GlobalStatistics.Line();

                data.company = company.company;
                data.coverage = company.coverage;
                data.drivers = company.drivers;
                data.rank = company.rank;
                data.turnover = company.turnover;
                data.has_won = company.has_won;
                data.wheather_show = true;
                this.TABLE_DATA.all_lines.add(data);
            }

            buildvoidcells();
        }

        private void build_tree_data() {
            this.table.reciveTreeData(convertTableData());
        }

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
            GlobalStatistics.Line line = (GlobalStatistics.Line) table_node.item;

            if (!(line.wheather_show)) {
                menues.SetShowField(obj.nativePointer, false);

                return;
            }

            int position = this.table.getMarkedPosition(obj.nativePointer);

            switch (position) {
             case 0 :
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, "" + line.rank);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 4 :
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, "" + line.drivers);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 1 :
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer, "" + line.company);
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 2 :
                 KeyPair[] macro = new KeyPair[1];

                 macro[0] = new KeyPair("VALUE", "" + line.coverage);
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer,
                                     MacroKit.Parse(GlobalStatistics.this.COMPANY_FIELDS_TEXTS[2], macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 3 :
                 KeyPair[] macro = new KeyPair[2];

                 macro[0] = new KeyPair("SIGN", "" + ((line.turnover >= 0)
                         ? ""
                         : "-"));
                 macro[1] = new KeyPair("MONEY", Helper.convertMoney(line.turnover));
                 menues.SetShowField(obj.nativePointer, true);
                 menues.SetFieldText(obj.nativePointer,
                                     MacroKit.Parse(GlobalStatistics.this.COMPANY_FIELDS_TEXTS[3], macro));
                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 5 :
                 if ((GlobalStatistics.this.globalStats != null)
                         && (GlobalStatistics.this.globalStats.goldcarreir != null)
                         && (GlobalStatistics.this.globalStats.goldcarreir.economics_done == true)) {
                     if (line.has_won) {
                         menues.SetShowField(obj.nativePointer, true);
                     } else {
                         menues.SetShowField(obj.nativePointer, false);
                     }
                 } else {
                     menues.SetShowField(obj.nativePointer, false);
                 }

                 menues.UpdateMenuField(menues.ConvertMenuFields(obj.nativePointer));

                 break;

             case 6 :
                 if ((GlobalStatistics.this.globalStats != null)
                         && (GlobalStatistics.this.globalStats.goldcarreir != null)
                         && (GlobalStatistics.this.globalStats.goldcarreir.economics_done == true)) {
                     if (line.has_won) {
                         menues.SetShowField(obj.nativePointer, true);
                     } else {
                         menues.SetShowField(obj.nativePointer, false);
                     }
                 } else {
                     menues.SetShowField(obj.nativePointer, false);
                 }

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
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {}

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
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {}
    }


    static class table_data {
        Vector<GlobalStatistics.Line> all_lines;

        table_data() {
            this.all_lines = new Vector();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
