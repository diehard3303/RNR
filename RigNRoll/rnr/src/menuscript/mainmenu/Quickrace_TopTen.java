/*
 * @(#)Quickrace_TopTen.java   13/08/26
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
import rnr.src.menu.IValueChanged;
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
import rnr.src.menu.menues;
import rnr.src.menuscript.Converts;
import rnr.src.menuscript.parametrs.ParametrsBlock;
import rnr.src.menuscript.table.ISetupLine;
import rnr.src.menuscript.table.Table;
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
public class Quickrace_TopTen extends PanelDialog implements IValueChanged {
    private static final String[] LISTOFALTERANTIVESGROUP = { "Tablegroup - ELEMENTS - TopTen List Special CATEGORY",
            "Tablegroup - ELEMENTS - TopTen List Special CLASS" };
    private static final String[] STITLES = { "menu_MAIN.xml\\Tablegroup - ELEMENTS - TopTen List Special CATEGORY\\QUICK RACE - TOP TEN - CATEGORY\\TITLE",
            "menu_MAIN.xml\\Tablegroup - ELEMENTS - TopTen List Special CLASS\\QUICK RACE - TOP TEN - CLASS\\TITLE" };
    private static final String[] CATEGORY_TEXT = { loc.getMENUString("BEST TRUCKERS"),
            loc.getMENUString("BEST RACERS"), loc.getMENUString("BEST SHIPPERS") };
    private static final String[] CLASS_TEXT = { loc.getMENUString("LOD_EASY"), loc.getMENUString("LOD_NORMAL"),
            loc.getMENUString("LOD_HARD") };
    private static final String XML = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String TABLE_BEST = "TABLEGROUP - QUICK RACE - TOP TEN - BEST RESULTS - 10 36";
    private static final String TABLE_BEST_LINE = "Tablegroup - ELEMENTS - TopTenBestResults Lines";
    private static final String[] TABLE_BEST_ELEMENTS = { "QUICK RACE - TOP TEN - BEST RESULTS - NAME - VALUE",
            "QUICK RACE - TOP TEN - BEST RESULTS - PROFIT - VALUE",
            "QUICK RACE - TOP TEN - BEST RESULTS - SPEED - VALUE",
            "QUICK RACE - TOP TEN - BEST RESULTS - CAREFULNESS - VALUE" };
    private static final String TABLE_MY = "TABLEGROUP - QUICK RACE - TOP TEN - MY RESULTS - 10 36";
    private static final String TABLE_MY_LINE = "Tablegroup - ELEMENTS - TopTenMyResults Lines";
    private static final String[] TABLE_MY_ELEMENTS = { "QUICK RACE - TOP TEN - MY RESULTS - DATE - VALUE",
            "QUICK RACE - TOP TEN - MY RESULTS - PROFIT - VALUE", "QUICK RACE - TOP TEN - MY RESULTS - SPEED - VALUE",
            "QUICK RACE - TOP TEN - MY RESULTS - CAREFULNESS - VALUE" };
    private static final String[] SUMMARY_ELEMENTS = {
        "QUICK RACE - TOP TEN - MY STATISTICS - MAX SPEED - VALUE",
        "QUICK RACE - TOP TEN - MY STATISTICS - MAX DISTANCE - VALUE",
        "QUICK RACE - TOP TEN - MY STATISTICS - TOTAL DISTANCE - VALUE",
        "QUICK RACE - TOP TEN - MY STATISTICS - EXECUTED ORDERS - VALUE",
        "QUICK RACE - TOP TEN - MY STATISTICS - TOTAL PROFIT - VALUE",
        "QUICK RACE - TOP TEN - MY STATISTICS - TOTAL REPAIR - VALUE",
        "QUICK RACE - TOP TEN - MY STATISTICS - TOTAL TICKETS - VALUE"
    };
    ListOfAlternatives _category = null;
    ListOfAlternatives _class = null;
    long best_text_filed0_id = 0L;
    long best_text_filed1_id = 0L;
    long best_picture_id = 0L;
    long my_text_filed0_id = 0L;
    long my_text_filed1_id = 0L;
    long my_picture_id = 0L;
    long blind_id = 0L;
    private String[] summary_text = null;

    /** Field description */
    public sort current_sort = new sort();

    /** Field description */
    public Vector<MyResultsInfo> in_my_lines = new Vector();

    /** Field description */
    public Vector<BestResultsInfo> in_best_lines = new Vector();
    BestResults best_results = null;
    MyResults my_results = null;
    private long _menu = 0L;
    Summary summary = new Summary();

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param controls
     * @param window
     * @param okButton
     * @param parent
     */
    public Quickrace_TopTen(long _menu, long[] controls, long window, long okButton, Panel parent) {
        super(_menu, window, controls, okButton, 0L, 0L, 0L, parent);
        this._menu = _menu;
        JavaEvents.SendEvent(65, 19, this.current_sort);
        this._category = new ListOfAlternatives(parent.menu.XML_FILE, LISTOFALTERANTIVESGROUP[0],
                loc.getMENUString(STITLES[0]), CATEGORY_TEXT, false);
        this._class = new ListOfAlternatives(parent.menu.XML_FILE, LISTOFALTERANTIVESGROUP[1],
                loc.getMENUString(STITLES[1]), CLASS_TEXT, false);
        this._category.load(_menu);
        this._class.load(_menu);
        this.param_values.addParametr("QUICK RACE CATEGORY", this.current_sort._category, 0, this._category);
        this.param_values.addParametr("QUICK RACE CLASS", this.current_sort._class, 0, this._class);
        this._category.insertInTable(_menu,
                                     menues.FindFieldInMenu(_menu, "TABLEGROUP - QUICK RACE - TOP TEN - CATEGORY"));
        this._class.insertInTable(_menu, menues.FindFieldInMenu(_menu, "TABLEGROUP - QUICK RACE - TOP TEN - CLASS"));
        this._category.addListener(this);
        this._class.addListener(this);
        this.best_picture_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - BEST RESULTS - PROFIT - TITLE");
        this.best_text_filed0_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - BEST RESULTS - SPEED - TITLE");
        this.best_text_filed1_id = menues.FindFieldInMenu(_menu,
                "QUICK RACE - TOP TEN - BEST RESULTS - CAREFULNESS - TITLE");
        this.my_picture_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - MY RESULTS - PROFIT - TITLE");
        this.my_text_filed0_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - MY RESULTS - SPEED - TITLE");
        this.my_text_filed1_id = menues.FindFieldInMenu(_menu,
                "QUICK RACE - TOP TEN - MY RESULTS - CAREFULNESS - TITLE");
        this.blind_id = menues.FindFieldInMenu(_menu, "QUICK RACE - TOP TEN - TABLE - ALL BORDERS");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.best_picture_id), this, "onProfitSelected", 2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.my_picture_id), this, "onProfitSelected", 2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.best_text_filed0_id), this, "onSpeedSelected",
                                  2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.my_text_filed0_id), this, "onSpeedSelected", 2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.best_text_filed1_id), this,
                                  "onCarefulnessSelected", 2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.my_text_filed1_id), this,
                                  "onCarefulnessSelected", 2L);
        this.best_results = new BestResults(_menu, this);
        this.my_results = new MyResults(_menu, this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
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
    public void onProfitSelected(long _menu, MENUbutton_field button) {
        this._category.setValue(0);
        hide_some_controls();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onSpeedSelected(long _menu, MENUbutton_field button) {
        this._category.setValue(1);
        hide_some_controls();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onCarefulnessSelected(long _menu, MENUbutton_field button) {
        this._category.setValue(2);
        hide_some_controls();
    }

    void hide_some_controls() {
        if (this.best_picture_id != 0L) {
            menues.SetFieldState(this.best_picture_id, (this.current_sort._category == 0)
                    ? 1
                    : 0);
        }

        if (this.best_text_filed0_id != 0L) {
            menues.SetFieldState(this.best_text_filed0_id, (this.current_sort._category == 1)
                    ? 1
                    : 0);
        }

        if (this.best_text_filed1_id != 0L) {
            menues.SetFieldState(this.best_text_filed1_id, (this.current_sort._category == 2)
                    ? 1
                    : 0);
        }

        if (this.my_picture_id != 0L) {
            menues.SetFieldState(this.my_picture_id, (this.current_sort._category == 0)
                    ? 1
                    : 0);
        }

        if (this.my_text_filed0_id != 0L) {
            menues.SetFieldState(this.my_text_filed0_id, (this.current_sort._category == 1)
                    ? 1
                    : 0);
        }

        if (this.my_text_filed1_id != 0L) {
            menues.SetFieldState(this.my_text_filed1_id, (this.current_sort._category == 2)
                    ? 1
                    : 0);
        }

        if (this.blind_id != 0L) {
            menues.SetBlindess(this.blind_id, true);
            menues.SetIgnoreEvents(this.blind_id, true);
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void valueChanged() {
        this.current_sort._category = this._category.getValue();
        this.current_sort._class = this._class.getValue();
        hide_some_controls();

        if (this.best_results != null) {
            this.best_results.updateTable();
        }

        if (this.my_results != null) {
            this.my_results.updateTable();
        }
    }

    /**
     * Method description
     *
     */
    public void updateSummary() {
        JavaEvents.SendEvent(65, 23, this.summary);

        if (this.summary_text == null) {
            this.summary_text = new String[SUMMARY_ELEMENTS.length];

            for (int i = 0; i < SUMMARY_ELEMENTS.length; ++i) {
                this.summary_text[i] = menues.GetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[i]));
            }
        }

        KeyPair[] pairs = new KeyPair[1];

        pairs[0] = new KeyPair("VALUE", "" + this.summary.MAX_SPEED);
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[0]),
                            MacroKit.Parse(this.summary_text[0], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[0])));
        pairs = new KeyPair[1];
        pairs[0] = new KeyPair("VALUE", "" + this.summary.MAX_DISTANCE);
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[1]),
                            MacroKit.Parse(this.summary_text[1], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[1])));
        pairs = new KeyPair[1];
        pairs[0] = new KeyPair("VALUE", "" + this.summary.TOTAL_DISTANCE);
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[2]),
                            MacroKit.Parse(this.summary_text[2], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[2])));
        pairs = new KeyPair[1];
        pairs[0] = new KeyPair("VALUE", "" + this.summary.EXECUTED_ORDERS);
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[3]),
                            MacroKit.Parse(this.summary_text[3], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[3])));
        pairs = new KeyPair[2];
        pairs[0] = new KeyPair("SIGN", "" + ((this.summary.TOTAL_PROFIT >= 0)
                ? ""
                : "-"));
        pairs[1] = new KeyPair("MONEY", "" + Math.abs(this.summary.TOTAL_PROFIT));
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[4]),
                            MacroKit.Parse(this.summary_text[4], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[4])));
        pairs = new KeyPair[1];
        pairs[0] = new KeyPair("MONEY", "" + this.summary.TOTAL_REPAIR);
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[5]),
                            MacroKit.Parse(this.summary_text[5], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[5])));
        pairs = new KeyPair[1];
        pairs[0] = new KeyPair("MONEY", "" + this.summary.TOTAL_TICKETS);
        menues.SetFieldText(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[6]),
                            MacroKit.Parse(this.summary_text[6], pairs));
        menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(this._menu, SUMMARY_ELEMENTS[6])));
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void OnExit(long _menu, MENUsimplebutton_field button) {
        JavaEvents.SendEvent(65, 22, this.current_sort);
        super.OnExit(_menu, button);
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        super.afterInit();
        this.best_results.afterInit();
        this.my_results.afterInit();
        hide_some_controls();
        updateSummary();
    }

    /**
     * Method description
     *
     */
    @Override
    public void update() {
        super.update();
        this.best_results.updateTable();
        this.my_results.updateTable();
        updateSummary();
    }

    /**
     * Method description
     *
     */
    public void deinit() {
        this.best_results.deinit();
        this.my_results.deinit();
    }

    @Override
    protected void onShow(boolean value) {
        hide_some_controls();
    }

    @Override
    protected void onFreeze(boolean value) {
        hide_some_controls();
    }

    /**
     * Method description
     *
     */
    @Override
    public void readParamValues() {
        super.update();
        this.best_results.updateTable();
        this.my_results.updateTable();
        updateSummary();
    }

    class BestResults implements ISetupLine {
        private final Quickrace_TopTen.best_table_data TABLE_DATA = new Quickrace_TopTen.best_table_data();
        private long _menu = 0L;
        Quickrace_TopTen _parent = null;
        private String RESULT_PROFIT_text = null;
        private String RESULT_SPEED_text = null;
        private String RESULT_CAREFULNESS_text = null;
        Table table;

        BestResults(long paramLong, Quickrace_TopTen paramQuickrace_TopTen) {
            this._parent = parent;
            this._menu = paramLong;
            this.table = new Table(this._menu, "TABLEGROUP - QUICK RACE - TOP TEN - BEST RESULTS - 10 36", null);
            this.table.setSelectionMode(0);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_MAIN.xml",
                                     "Tablegroup - ELEMENTS - TopTenBestResults Lines",
                                     Quickrace_TopTen.TABLE_BEST_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            reciveTableData();
            build_tree_data();
        }

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
            Quickrace_TopTen.BestResultsInfo line = (Quickrace_TopTen.BestResultsInfo) table_node.item;

            if (!(line.wheather_show)) {
                menues.SetShowField(obj.nativePointer, false);

                return;
            }

            int control = this.table.getMarkedPosition(obj.nativePointer);

            switch (control) {
             case 0 :
                 obj.text = line.name;
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);
            }

            menues.SetShowField(obj.nativePointer, true);
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
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            Quickrace_TopTen.BestResultsInfo line = (Quickrace_TopTen.BestResultsInfo) table_node.item;

            if (!(line.wheather_show)) {
                menues.SetShowField(obj.nativePointer, false);

                return;
            }

            int control = this.table.getMarkedPosition(obj.nativePointer);

            switch (control) {
             case 0 :
                 break;

             case 1 :
                 if (this.RESULT_PROFIT_text == null) {
                     this.RESULT_PROFIT_text = obj.text;
                 }

                 KeyPair[] keys = { new KeyPair("SIGN", "" + ((line.profit >= 0)
                         ? " "
                         : "-")), new KeyPair("MONEY", "" + Math.abs(line.profit)) };

                 obj.text = MacroKit.Parse(this.RESULT_PROFIT_text, keys);
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);
                 menues.SetFieldState(obj.nativePointer, (Quickrace_TopTen.this.current_sort._category == 0)
                         ? 1
                         : 0);

                 break;

             case 2 :
                 if (this.RESULT_SPEED_text == null) {
                     this.RESULT_SPEED_text = obj.text;
                 }

                 KeyPair[] keys1 = { new KeyPair("VALUE", "" + line.speed) };

                 obj.text = MacroKit.Parse(this.RESULT_SPEED_text, keys1);
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);
                 menues.SetFieldState(obj.nativePointer, (Quickrace_TopTen.this.current_sort._category == 1)
                         ? 1
                         : 0);

                 break;

             case 3 :
                 if (this.RESULT_CAREFULNESS_text == null) {
                     this.RESULT_CAREFULNESS_text = obj.text;
                 }

                 KeyPair[] keys2 = { new KeyPair("VALUE", "" + line.carefulness) };

                 obj.text = MacroKit.Parse(this.RESULT_CAREFULNESS_text, keys2);
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);
                 menues.SetFieldState(obj.nativePointer, (Quickrace_TopTen.this.current_sort._category == 2)
                         ? 2
                         : 0);
            }

            menues.SetShowField(obj.nativePointer, true);
        }

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {}

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

        private void buildvoidcells() {
            if (this.TABLE_DATA.all_lines.size() < this.table.getNumRows()) {
                int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();

                for (int i = 0; i < dif; ++i) {
                    Quickrace_TopTen.BestResultsInfo data = new Quickrace_TopTen.BestResultsInfo();

                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
            } else {
                int count_good_data = 0;
                Iterator iter = this.TABLE_DATA.all_lines.iterator();

                while ((iter.hasNext()) && (((Quickrace_TopTen.BestResultsInfo) iter.next()).wheather_show)) {
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
            long[] contrls_0 = null;

            if (0 < Quickrace_TopTen.TABLE_BEST_ELEMENTS.length) {
                contrls_0 = this.table.getLineStatistics_controls(Quickrace_TopTen.TABLE_BEST_ELEMENTS[0]);
            }

            long[] contrls_1 = null;

            if (1 < Quickrace_TopTen.TABLE_BEST_ELEMENTS.length) {
                contrls_1 = this.table.getLineStatistics_controls(Quickrace_TopTen.TABLE_BEST_ELEMENTS[1]);
            }

            long[] contrls_2 = null;

            if (2 < Quickrace_TopTen.TABLE_BEST_ELEMENTS.length) {
                contrls_2 = this.table.getLineStatistics_controls(Quickrace_TopTen.TABLE_BEST_ELEMENTS[2]);
            }

            long[] contrls_3 = null;

            if (1 < Quickrace_TopTen.TABLE_BEST_ELEMENTS.length) {
                contrls_3 = this.table.getLineStatistics_controls(Quickrace_TopTen.TABLE_BEST_ELEMENTS[3]);
            }

            if ((null == contrls_0) || (null == contrls_1) || (null == contrls_2) || (null == contrls_3)) {
                return;
            }

            for (int i = 0; i < contrls_0.length; ++i) {
                menues.SetSyncControlActive(this._menu, i, contrls_0[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_1[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_2[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_3[i]);
            }
        }

        /**
         * Method description
         *
         */
        public void afterInit() {
            this.table.afterInit();
            make_sync_group();
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
         *
         * @param table
         * @param line
         */
        public void selectLineEvent(Table table, int line) {}

        /**
         * Method description
         *
         *
         * @param table
         * @param lines
         */
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {}

        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();
            JavaEvents.SendEvent(65, 21, this._parent);

            for (int i = 0; i < Math.min(Quickrace_TopTen.this.in_best_lines.size(), 10); ++i) {
                this.TABLE_DATA.all_lines.add(Quickrace_TopTen.this.in_best_lines.elementAt(i));
            }

            buildvoidcells();
        }

        /**
         * Method description
         *
         */
        public void deinit() {
            this.table.deinit();
        }
    }


    static class BestResultsInfo {
        String name;
        int profit;
        int speed;
        int carefulness;
        boolean wheather_show;

        BestResultsInfo() {
            this.name = null;
            this.profit = 0;
            this.speed = 0;
            this.carefulness = 0;
            this.wheather_show = true;
        }
    }


    static class MediaTime {
        int year;
        int month;
        int day;
        int hour;
        int min;
        int sec;

        MediaTime() {
            this.year = 0;
            this.month = 0;
            this.day = 0;
            this.hour = 0;
            this.min = 0;
            this.sec = 0;
        }
    }


    class MyResults implements ISetupLine {
        private final Quickrace_TopTen.my_table_data TABLE_DATA = new Quickrace_TopTen.my_table_data();
        private long _menu = 0L;
        Quickrace_TopTen _parent = null;
        private String RESULT_TIME_text = null;
        private String RESULT_PROFIT_text = null;
        private String RESULT_SPEED_text = null;
        private String RESULT_CAREFULNESS_text = null;
        Table table;

        MyResults(long paramLong, Quickrace_TopTen paramQuickrace_TopTen) {
            this._parent = parent;
            this._menu = paramLong;
            this.table = new Table(this._menu, "TABLEGROUP - QUICK RACE - TOP TEN - MY RESULTS - 10 36", null);
            this.table.setSelectionMode(0);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_MAIN.xml",
                                     "Tablegroup - ELEMENTS - TopTenMyResults Lines",
                                     Quickrace_TopTen.TABLE_MY_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            reciveTableData();
            build_tree_data();
        }

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
            Quickrace_TopTen.MyResultsInfo line = (Quickrace_TopTen.MyResultsInfo) table_node.item;

            if (!(line.wheather_show)) {
                menues.SetShowField(obj.nativePointer, false);

                return;
            }

            int control = this.table.getMarkedPosition(obj.nativePointer);

            switch (control) {
             case 0 :
                 if (this.RESULT_TIME_text == null) {
                     this.RESULT_TIME_text = obj.text;
                 }

                 obj.text = Converts.ConvertDateAbsolute(this.RESULT_TIME_text, line.date.month, line.date.day,
                         line.date.year, line.date.hour, line.date.min, line.date.sec);
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);

                 break;

             case 1 :
                 if (this.RESULT_PROFIT_text == null) {
                     this.RESULT_PROFIT_text = obj.text;
                 }

                 KeyPair[] keys = { new KeyPair("SIGN", "" + ((line.profit >= 0)
                         ? " "
                         : "-")), new KeyPair("MONEY", "" + Math.abs(line.profit)) };

                 obj.text = MacroKit.Parse(this.RESULT_PROFIT_text, keys);
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);

                 break;

             case 2 :
                 if (this.RESULT_SPEED_text == null) {
                     this.RESULT_SPEED_text = obj.text;
                 }

                 KeyPair[] keys1 = { new KeyPair("VALUE", "" + line.speed) };

                 obj.text = MacroKit.Parse(this.RESULT_SPEED_text, keys1);
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);

                 break;

             case 3 :
                 if (this.RESULT_CAREFULNESS_text == null) {
                     this.RESULT_CAREFULNESS_text = obj.text;
                 }

                 KeyPair[] keys2 = { new KeyPair("VALUE", "" + line.carefulness) };

                 obj.text = MacroKit.Parse(this.RESULT_CAREFULNESS_text, keys2);
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);
            }

            menues.SetShowField(obj.nativePointer, true);
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
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {}

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

        private void buildvoidcells() {
            if (this.TABLE_DATA.all_lines.size() < this.table.getNumRows()) {
                int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();

                for (int i = 0; i < dif; ++i) {
                    Quickrace_TopTen.MyResultsInfo data = new Quickrace_TopTen.MyResultsInfo();

                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
            } else {
                int count_good_data = 0;
                Iterator iter = this.TABLE_DATA.all_lines.iterator();

                while ((iter.hasNext()) && (((Quickrace_TopTen.MyResultsInfo) iter.next()).wheather_show)) {
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
            long[] contrls_0 = null;

            if (0 < Quickrace_TopTen.TABLE_MY_ELEMENTS.length) {
                contrls_0 = this.table.getLineStatistics_controls(Quickrace_TopTen.TABLE_MY_ELEMENTS[0]);
            }

            long[] contrls_1 = null;

            if (1 < Quickrace_TopTen.TABLE_MY_ELEMENTS.length) {
                contrls_1 = this.table.getLineStatistics_controls(Quickrace_TopTen.TABLE_MY_ELEMENTS[1]);
            }

            long[] contrls_2 = null;

            if (2 < Quickrace_TopTen.TABLE_MY_ELEMENTS.length) {
                contrls_2 = this.table.getLineStatistics_controls(Quickrace_TopTen.TABLE_MY_ELEMENTS[2]);
            }

            long[] contrls_3 = null;

            if (1 < Quickrace_TopTen.TABLE_MY_ELEMENTS.length) {
                contrls_3 = this.table.getLineStatistics_controls(Quickrace_TopTen.TABLE_MY_ELEMENTS[3]);
            }

            if ((null == contrls_0) || (null == contrls_1) || (null == contrls_2) || (null == contrls_3)) {
                return;
            }

            for (int i = 0; i < contrls_0.length; ++i) {
                menues.SetSyncControlActive(this._menu, i, contrls_0[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_1[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_2[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_3[i]);
            }
        }

        /**
         * Method description
         *
         */
        public void afterInit() {
            this.table.afterInit();
            make_sync_group();
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
         *
         * @param table
         * @param line
         */
        public void selectLineEvent(Table table, int line) {}

        /**
         * Method description
         *
         *
         * @param table
         * @param lines
         */
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {}

        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();
            JavaEvents.SendEvent(65, 20, this._parent);

            for (int i = 0; i < Math.min(Quickrace_TopTen.this.in_my_lines.size(), 10); ++i) {
                this.TABLE_DATA.all_lines.add(Quickrace_TopTen.this.in_my_lines.elementAt(i));
            }

            buildvoidcells();
        }

        /**
         * Method description
         *
         */
        public void deinit() {
            this.table.deinit();
        }
    }


    static class MyResultsInfo {
        Quickrace_TopTen.MediaTime date;
        int profit;
        int speed;
        int carefulness;
        boolean wheather_show;

        MyResultsInfo() {
            this.date = new Quickrace_TopTen.MediaTime();
            this.profit = 0;
            this.speed = 0;
            this.carefulness = 0;
            this.wheather_show = true;
        }
    }


    static class Summary {
        int MAX_SPEED;
        int MAX_DISTANCE;
        int TOTAL_DISTANCE;
        int EXECUTED_ORDERS;
        int TOTAL_PROFIT;
        int TOTAL_REPAIR;
        int TOTAL_TICKETS;

        Summary() {
            this.MAX_SPEED = 0;
            this.MAX_DISTANCE = 0;
            this.TOTAL_DISTANCE = 0;
            this.EXECUTED_ORDERS = 0;
            this.TOTAL_PROFIT = 0;
            this.TOTAL_REPAIR = 0;
            this.TOTAL_TICKETS = 0;
        }
    }


    static class best_table_data {
        Vector<Quickrace_TopTen.BestResultsInfo> all_lines;

        best_table_data() {
            this.all_lines = new Vector();
        }
    }


    static class my_table_data {
        Vector<Quickrace_TopTen.MyResultsInfo> all_lines;

        my_table_data() {
            this.all_lines = new Vector();
        }
    }


    static class sort {
        int _class;
        int _category;

        sort() {
            this._class = 0;
            this._category = 0;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
