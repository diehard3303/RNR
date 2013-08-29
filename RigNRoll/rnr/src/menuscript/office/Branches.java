/*
 * @(#)Branches.java   13/08/26
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

import rnr.src.menu.FocusManager;
import rnr.src.menu.Helper;
import rnr.src.menu.IFocusHolder;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.SelectCb;
import rnr.src.menu.menues;
import rnr.src.menuscript.RNRMapWrapper;
import rnr.src.menuscript.table.Table;
import rnr.src.menuscript.tablewrapper.TableData;
import rnr.src.menuscript.tablewrapper.TableLine;
import rnr.src.menuscript.tablewrapper.TableWrapped;
import rnr.src.rnrconfig.WorldCoordinates;
import rnr.src.rnrcore.Log;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Branches extends ApplicationTab {
    private static final String TAB_NAME = "BRANCHES";
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String COMPANY_TABLE = "MB - Company Branches - TABLEGROUP - 10 38";
    private static final String COMPANY_TABLE_RANGER = "MB - Company Branches - Tableranger";
    private static final String COMPANY_LINE = "Tablegroup - ELEMENTS - CompanyBranches";
    private static final String[] COMPANY_LINE_ELEMENTS = { "BUTTON - MB - Company Branches - Where VALUE",
            "BUTTON - MB - Company Branches - Where VALUE MARKED", "BUTTON - MB - Company Branches - Funds VALUE",
            "BUTTON - MB - Company Branches - Funds VALUE MARKED" };
    private static final int COMPANY_WHERE = 0;
    private static final int COMPANY_WHERE_GRAY = 1;
    private static final int COMPANY_VALUE = 2;
    private static final int COMPANY_VALUE_GRAY = 3;
    private static final String[] COMPANY_SORT = { "BUTTON - MB - Company Branches - WHERE",
            "BUTTON - MB - Company Branches - FUNDS" };
    private static final int COMPANY_SORT_WHERE = 0;
    private static final int COMPANY_SORT_FUNDS = 1;
    private static final String[] HEADQUATER = { "MB - Company Branches - OXNARD",
            "MB - Company Branches - HEADQUARTER" };
    private static final String HEADQUATER_SELECT = "onHeadquater";
    private static final String SALE_TABLE = "MB - Offices For Sale - TABLEGROUP - 11 38";
    private static final String SALE_TABLE_RANGER = "MB - Offices For Sale - Tableranger";
    private static final String SALE_LINE = "Tablegroup - ELEMENTS - Offices For Sale";
    private static final String[] SALE_LINE_ELEMENTS = { "BUTTON - MB - Offices For Sale - Where VALUE",
            "BUTTON - MB - Offices For Sale - Where VALUE MARKED", "BUTTON - MB - Offices For Sale - Funds VALUE",
            "BUTTON - MB - Offices For Sale - Funds VALUE MARKED" };
    private static final int SALE_WHERE = 0;
    private static final int SALE_WHERE_GRAY = 1;
    private static final int SALE_VALUE = 2;
    private static final int SALE_VALUE_GRAY = 3;
    private static final String[] SALE_SORT = { "BUTTON - MB - Offices For Sale - WHERE",
            "BUTTON - MB - Offices For Sale - INVESTMENT" };
    private static final String COMPANY_DESCRIPTION_TABLE = "MB - Company Branches - AREA - TABLEGROUP - 5 38";
    private static final String COMPANY_DESCRIPTION_TABLE_RANGER = "MB - Company Branches - AREA - Tableranger";
    private static final String SALES_DESCRIPTION_TABLE = "MB - Offices For Sale - AREA - TABLEGROUP - 5 38";
    private static final String SALES_DESCRIPTION_TABLE_RANGER = "MB - Offices For Sale - AREA - Tableranger";
    private static final String DESCRIPTION_LINE = "Tablegroup - ELEMENTS - CompanyBranches - AREA";
    private static final String[] DESCRIPTION_LINE_ELEMENTS = { "MB - Company Branches - AREA - Warehouse VALUE",
            "MB - Company Branches - AREA - Warehouse VALUE MARKED" };
    private static final int DESCRIPTION_NAME = 0;
    private static final int DESCRIPTION_NAME_MARKED = 1;
    private static final String[] SUMMARY_TEXTS = {
        "Established VALUE", "Closed Up VALUE", "Warehouses VALUE", "Invested VALUE", "Released VALUE", "Total VALUE"
    };
    private static final int SUMMARY_ESTABLISGED = 0;
    private static final int SUMMARY_CLOSED = 1;
    private static final int SUMMARY_ONCONTRACT = 2;
    private static final int SUMMARY_INVESTED = 3;
    private static final int SUMMARY_RELEASED = 4;
    private static final int SUMMARY_TOTAL = 5;
    private static final String MAP_NAME = "MAP - zooming picture BRANCES";
    private static final String MAP_ZOOM = "MB - MAP";
    private static final String MAP_SHIFT = "MB - MAP";
    private static final String MACRO_MONEY = "MONEY";
    private static final String MACRO_VALUE = "VALUE";
    private static final String MACRO_VALUE2 = "VALUE2";
    private static final String MACRO_SIGN = "SIGN";
    private static final String[] SUMMARY_MACRO = {
        "VALUE", "VALUE", "VALUE", "MONEY", "MONEY", "MONEY"
    };
    private static final String[] SUMMARY_MACRO2 = {
        "", "", "VALUE2", "SIGN", "SIGN", "SIGN"
    };
    private static final int[] SUMMARY_MACRO_NUM = {
        1, 1, 2, 2, 2, 2
    };
    private static final int SELECT_TYPE_COMPANY = 0;
    private static final int SELECT_TYPE_SALES = 1;
    private static final int SELECT_TYPE_HEADQUATER = 2;
    private static final String[] BUTTONS_ACTIONS = { "BUTTON - MB - Company Branches - CLOSE UP",
            "BUTTON - MB - Company Branches - CLOSE UP GRAY", "BUTTON - MB - Offices For Sale - ESTABLISH" };
    private static final String[] METHODS_ACTIONS = { "onCloseBranch", "", "onEstablishBranch" };
    private static final int ACTION_CLOSE = 0;
    private static final int ACTION_CLOSE_GRAY = 1;
    private final boolean DEBUG = false;
    private final int LIVE_WAREHOUSE = 4;
    private final int OFFICE_WAREHOUSE = 5;
    private final int LIVE_OFFICE = 11;
    private final int SALES_OFFICE = 12;
    private CompanyBranches company = null;
    private SaleBranches sales = null;
    private CompanyWarehousesInArea company_warehouses = null;
    private SalesWarehousesInArea sales_warehouses = null;
    private final String[] initialSummaryTexts = new String[SUMMARY_TEXTS.length];
    private final long[] summaryTexts = new long[SUMMARY_TEXTS.length];
    WorldCoordinates worldRectangle = null;
    HeadquaterLine headquater = null;
    SortedWarehouseDrawQuere warehousesQueue = new SortedWarehouseDrawQuere();
    private final RNRMapWrapper mapa;
    private final long[] action_controls;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param parent
     */
    public Branches(long _menu, OfficeMenu parent) {
        super(_menu, "BRANCHES", parent);
        this.headquater = new HeadquaterLine(_menu);
        this.worldRectangle = WorldCoordinates.getCoordinates();
        this.company = new CompanyBranches(_menu);
        this.sales = new SaleBranches(_menu);
        this.company_warehouses = new CompanyWarehousesInArea(_menu);
        this.sales_warehouses = new SalesWarehousesInArea(_menu);

        for (int i = 0; i < SUMMARY_TEXTS.length; ++i) {
            this.summaryTexts[i] = menues.FindFieldInMenu(_menu, SUMMARY_TEXTS[i]);
            this.initialSummaryTexts[i] = menues.GetFieldText(this.summaryTexts[i]);
        }

        this.mapa = new RNRMapWrapper(_menu, "MAP - zooming picture BRANCES", "MB - MAP", "MB - MAP",
                                      new SelectMapControl());
        this.action_controls = new long[BUTTONS_ACTIONS.length];

        for (int i = 0; i < BUTTONS_ACTIONS.length; ++i) {
            this.action_controls[i] = menues.FindFieldInMenu(_menu, BUTTONS_ACTIONS[i]);

            if (i != 1) {
                menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.action_controls[i]), this,
                                          METHODS_ACTIONS[i], 4L);
            }
        }

        long control = menues.FindFieldInMenu(_menu, "CALL OFFICE HELP - MANAGE BRANCHES");

        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    private void refresh_summary() {
        ManageBranchesManager.Summary summary = ManageBranchesManager.getManageBranchesManager().GetSummary();

        for (int i = 0; i < this.summaryTexts.length; ++i) {
            KeyPair[] keys = new KeyPair[SUMMARY_MACRO_NUM[i]];

            switch (i) {
             case 0 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.established);

                 break;

             case 1 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.closed_up);

                 break;

             case 2 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.warehouses_on_contract);

                 if (SUMMARY_MACRO_NUM[i] > 1) {
                     keys[1] = new KeyPair(SUMMARY_MACRO2[i], "" + summary.total_warehouses);
                 }

                 break;

             case 3 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int) summary.invested));

                 if (SUMMARY_MACRO_NUM[i] > 1) {
                     keys[1] = new KeyPair(SUMMARY_MACRO2[i], (Math.abs((int) summary.invested) >= 0)
                             ? ""
                             : "-");
                 }

                 break;

             case 4 :
                 keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int) summary.released));

                 if (SUMMARY_MACRO_NUM[i] > 1) {
                     keys[1] = new KeyPair(SUMMARY_MACRO2[i], (Math.abs((int) summary.released) >= 0)
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

    private void refresh_mapa() {
        this.mapa.ClearData();
        this.warehousesQueue.clear();

        if (this.company.isInFocus()) {
            this.company.refresh_map_objects(true);
            this.headquater.refresh_map_objects(false);
            this.sales.refresh_map_objects(false);
        } else if (this.sales.isInFocus()) {
            this.company.refresh_map_objects(false);
            this.headquater.refresh_map_objects(false);
            this.sales.refresh_map_objects(true);
        } else if (this.headquater.isInFocus()) {
            this.company.refresh_map_objects(false);
            this.headquater.refresh_map_objects(true);
            this.sales.refresh_map_objects(false);
        }

        this.warehousesQueue.draw();
    }

    private void drawWarehousesOnMapa(CompanyLine dataline, boolean highlite) {
        ArrayList query = new ArrayList();

        query.add(dataline);

        ArrayList data = reciveCompanyWarehouses(query);

        for (DescriptionLine line : data) {
            if (line.companyAffected) {
                this.warehousesQueue.add(line, highlite, this.LIVE_WAREHOUSE, 0);
            }
        }
    }

    private void drawWarehousesOnMapa(SalesLine dataline, boolean highlite) {
        ArrayList query = new ArrayList();

        query.add(dataline);

        ArrayList data = reciveSalesWarehouses(query);

        for (DescriptionLine line : data) {
            if ((!(line.companyAffected)) && (line.saleAffected)) {
                this.warehousesQueue.add(line, highlite, this.OFFICE_WAREHOUSE, 1);
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
    public void onCloseBranch(long _menu, MENUsimplebutton_field button) {
        setDirty();
        closeBranch(this.company.getSelectedMultiple());
        update();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onEstablishBranch(long _menu, MENUsimplebutton_field button) {
        setDirty();
        establishBranch(this.sales.getSelectedMultiple());
        update();
    }

    private int getSortTypeCompany(int type) {
        switch (type) {
         case 0 :
             return 1;

         case 1 :
             return 2;
        }

        return -1;
    }

    private int getSortTypeSale(int type) {
        return getSortTypeCompany(type);
    }

    CompanyLine reciveCompanyHeadquater() {
        if (this.DEBUG) {
            CompanyLine item = new CompanyLine();

            item.id = new ManageBranchesManager.OfficeId();
            item.id.id = 4;
            item.name = "name44";
            item.funds = 1000000.0D;
            item.x = 15000.0F;
            item.y = 0.0F;
            item.isGray = false;
            item.wheather_show = true;

            return item;
        }

        Vector got = ManageBranchesManager.getManageBranchesManager().GetCompanyBranches(1, true);

        for (int i = 0; i < got.size(); ++i) {
            ManageBranchesManager.OfficeInfo info = (ManageBranchesManager.OfficeInfo) got.get(i);

            if (!(info.isHeadquarter)) {
                continue;
            }

            CompanyLine item = new CompanyLine();

            item.id = info.id;
            item.name = info.where;
            item.funds = info.funds;
            item.x = info.x;
            item.y = info.y;
            item.isGray = info.isGray;
            item.wheather_show = true;

            return item;
        }

        return null;
    }

    ArrayList<CompanyLine> reciveCompany() {
        if (this.DEBUG) {
            ArrayList res = new ArrayList();

            for (int i = 0; i < 3; ++i) {
                CompanyLine item = new CompanyLine();

                item.id = new ManageBranchesManager.OfficeId();
                item.id.id = i;
                item.name = "name" + i;
                item.funds = (12.4D * i);
                item.x = (-10000.0F * i);
                item.y = (-40000.0F + 10000.0F * i);
                item.isGray = ((i & 0x1) == 0);
                item.wheather_show = true;
                res.add(item);
            }

            return res;
        }

        Vector got = ManageBranchesManager.getManageBranchesManager().GetCompanyBranches((this.company != null)
                ? this.company.tablesort.type
                : 1, (this.company != null)
                     ? this.company.tablesort.up
                     : true);
        ArrayList res = new ArrayList();

        for (int i = 0; i < got.size(); ++i) {
            ManageBranchesManager.OfficeInfo info = (ManageBranchesManager.OfficeInfo) got.get(i);

            if (info.isHeadquarter) {
                continue;
            }

            CompanyLine item = new CompanyLine();

            item.id = info.id;
            item.name = info.where;
            item.funds = info.funds;
            item.x = info.x;
            item.y = info.y;
            item.isGray = info.isGray;
            item.wheather_show = true;
            res.add(item);
        }

        return res;
    }

    ArrayList<DescriptionLine> reciveCompanyWarehouses(ArrayList<CompanyLine> bunch) {
        if (this.DEBUG) {
            ArrayList res = new ArrayList();

            if (bunch == null) {
                return res;
            }

            for (CompanyLine data : bunch) {
                for (int i = 0; i < 3; ++i) {
                    DescriptionLine item = new DescriptionLine();

                    item.name = data.id.id + "name" + i;
                    item.x = (-10000.0F + 12000.0F * i);
                    item.y = (data.id.id * 10000.0F + -2000.0F * i);
                    item.isGray = ((i & 0x1) == 0);
                    item.companyAffected = ((i & 0x1) == 1);
                    item.saleAffected = ((i & 0x2) == 0);
                    item.wheather_show = true;
                    res.add(item);
                }
            }

            return res;
        }

        ArrayList res = new ArrayList();

        if (bunch == null) {
            return res;
        }

        Vector list = new Vector();

        for (CompanyLine line : bunch) {
            list.add(line.id);
        }

        Vector got = ManageBranchesManager.getManageBranchesManager().GetWarehousesInTheArea(list);

        for (int i = 0; i < got.size(); ++i) {
            ManageBranchesManager.WarehouseInfo info = (ManageBranchesManager.WarehouseInfo) got.get(i);
            DescriptionLine item = new DescriptionLine();

            item.name = info.name;
            item.x = info.x;
            item.y = info.y;
            item.wheather_show = true;
            item.isGray = info.isGray;
            item.companyAffected = info.companyAffected;
            item.saleAffected = info.saleAffected;
            res.add(item);
        }

        return res;
    }

    void closeBranch(ArrayList<TableLine> data) {
        Vector res = new Vector(data.size());

        for (TableLine line : data) {
            CompanyLine cl = (CompanyLine) line;

            res.add(cl.id);
        }

        ManageBranchesManager.getManageBranchesManager().CloseUpBranch(res);
    }

    ArrayList<SalesLine> reciveSales() {
        if (this.DEBUG) {
            ArrayList res = new ArrayList();

            for (int i = 0; i < 3; ++i) {
                SalesLine item = new SalesLine();

                item.id = new ManageBranchesManager.OfficeId();
                item.id.id = i;
                item.name = "name" + i;
                item.funds = (12.4D * i);
                item.x = (-10000.0F + 10000.0F * i);
                item.y = (20000.0F * i);
                item.isGray = ((i & 0x1) == 0);
                item.wheather_show = true;
                res.add(item);
            }

            return res;
        }

        Vector got = ManageBranchesManager.getManageBranchesManager().GetOfficesForSale((this.sales != null)
                ? this.sales.tablesort.type
                : 1, (this.sales != null)
                     ? this.sales.tablesort.up
                     : true);
        ArrayList res = new ArrayList();

        for (int i = 0; i < got.size(); ++i) {
            ManageBranchesManager.OfficeInfo info = (ManageBranchesManager.OfficeInfo) got.get(i);
            SalesLine item = new SalesLine();

            item.id = info.id;
            item.name = info.where;
            item.funds = info.funds;
            item.x = info.x;
            item.y = info.y;
            item.isGray = info.isGray;
            item.wheather_show = true;
            res.add(item);
        }

        return res;
    }

    ArrayList<DescriptionLine> reciveSalesWarehouses(ArrayList<SalesLine> bunch) {
        if (this.DEBUG) {
            ArrayList res = new ArrayList();

            if (bunch == null) {
                return res;
            }

            for (SalesLine data : bunch) {
                for (int i = 0; i < 3; ++i) {
                    DescriptionLine item = new DescriptionLine();

                    item.name = data.id.id + "name" + i;
                    item.y = (-10000.0F + 12000.0F * i);
                    item.x = (data.id.id * 10000.0F + -2000.0F * i);
                    item.isGray = ((i & 0x1) == 0);
                    item.companyAffected = ((i & 0x1) == 1);
                    item.saleAffected = ((i & 0x2) == 0);
                    item.wheather_show = true;
                    res.add(item);
                }
            }

            return res;
        }

        ArrayList res = new ArrayList();

        if (bunch == null) {
            return res;
        }

        Vector list = new Vector();

        for (SalesLine line : bunch) {
            list.add(line.id);
        }

        Vector got = ManageBranchesManager.getManageBranchesManager().GetWarehousesInTheArea(list);

        for (int i = 0; i < got.size(); ++i) {
            ManageBranchesManager.WarehouseInfo info = (ManageBranchesManager.WarehouseInfo) got.get(i);
            DescriptionLine item = new DescriptionLine();

            item.name = info.name;
            item.x = info.x;
            item.y = info.y;
            item.isGray = info.isGray;
            item.companyAffected = info.companyAffected;
            item.saleAffected = info.saleAffected;
            item.wheather_show = true;
            res.add(item);
        }

        return res;
    }

    void establishBranch(ArrayList<TableLine> data) {
        Vector res = new Vector(data.size());

        for (TableLine line : data) {
            SalesLine sl = (SalesLine) line;

            res.add(sl.id);
        }

        ManageBranchesManager.getManageBranchesManager().EstablishNewBranch(res);
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        this.mapa.afterInit();
        this.mapa.workWith(this.LIVE_OFFICE);
        this.mapa.workWith(this.SALES_OFFICE);
        this.company_warehouses.afterInit();
        this.sales_warehouses.afterInit();
        this.company.afterInit();
        this.sales.afterInit();
    }

    /**
     * Method description
     *
     */
    @Override
    public void apply() {
        setDirty();

        ManageBranchesManager.ErrorInfo err = ManageBranchesManager.getManageBranchesManager().Apply();

        switch (err.error_code) {
         case 1 :
             makeNotEnoughMoney();

             break;

         case 2 :
             makeNotEnoughTurns(err.misc_info);
        }

        update();
    }

    /**
     * Method description
     *
     */
    @Override
    public void deinit() {
        this.company.deinit();
        this.sales.deinit();
        this.company_warehouses.deinit();
        this.sales_warehouses.deinit();
        this.headquater.deinit();
    }

    /**
     * Method description
     *
     */
    @Override
    public void discard() {
        setDirty();
        ManageBranchesManager.getManageBranchesManager().Discard();
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

        this.sales.updateTable();
        this.company.updateTable();
        refresh_summary();
        refresh_mapa();
        makeUpdate();
        this.headquater.selectHeadQuater();

        return true;
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
            this.parent.ShowTabHelp(3);
        }
    }

    class CompanyBranches extends TableWrapped {
        private Branches.sort tablesort = new Branches.sort(Branches.this.getSortTypeCompany(0), true);
        private String originalFunds = "";

        CompanyBranches(long _menu) {
            super(_menu, 2, false, "..\\data\\config\\menu\\menu_office.xml",
                  "MB - Company Branches - TABLEGROUP - 10 38", "MB - Company Branches - Tableranger",
                  "Tablegroup - ELEMENTS - CompanyBranches", Branches.COMPANY_LINE_ELEMENTS, null,
                  Branches.COMPANY_SORT);

            long[] texts = this.table.getLineStatistics_controls(Branches.COMPANY_LINE_ELEMENTS[2]);

            if ((null == texts) || (texts.length == 0)) {
                Log.menu("ERRORR. CompanyBranches table does not includes " + Branches.COMPANY_LINE_ELEMENTS[2]
                         + " control in line.");
            }

            this.originalFunds = menues.GetFieldText(texts[0]);
            deselectOnLooseFocus(true);
        }

        /**
         * Method description
         *
         */
        @Override
        public void updateTable() {
            super.updateTable();

            if ((!(this.TABLE_DATA.all_lines.isEmpty())) && (!(this.TABLE_DATA.all_lines.get(0).wheather_show))) {
                Branches.HeadquaterLine.access$000(Branches.this.headquater);
            } else {
                Branches.HeadquaterLine.access$1300(Branches.this.headquater);
            }
        }

        @Override
        protected void reciveTableData() {
            this.TABLE_DATA.all_lines.addAll(Branches.this.reciveCompany());
        }

        /**
         * Method description
         *
         *
         * @param button
         * @param position
         * @param table_node
         */
        @Override
        public void SetupLineInTable(long button, int position, TableLine table_node) {
            Branches.CompanyLine line = (Branches.CompanyLine) table_node;

            switch (position) {
             case 0 :
                 if (line.isGray) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);
                     menues.SetFieldText(button, line.name);
                 }

                 break;

             case 1 :
                 if (!(line.isGray)) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);
                     menues.SetFieldText(button, line.name);
                 }

                 break;

             case 2 :
                 if (line.isGray) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);

                     KeyPair[] keys = new KeyPair[1];

                     keys[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.funds));
                     menues.SetFieldText(button, MacroKit.Parse(this.originalFunds, keys));
                 }

                 break;

             case 3 :
                 if (!(line.isGray)) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);

                     KeyPair[] keys = new KeyPair[1];

                     keys[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.funds));
                     menues.SetFieldText(button, MacroKit.Parse(this.originalFunds, keys));
                 }
            }

            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        /**
         * Method description
         *
         *
         * @param linedata
         */
        @Override
        public void updateSelectedInfo(TableLine linedata) {
            Branches.this.company_warehouses.updateTable();
            Branches.this.refresh_mapa();
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onSort(long _menu, MENUsimplebutton_field button) {
            this.tablesort = new Branches.sort(Branches.this.getSortTypeCompany(button.userid), !(this.tablesort.up));
            updateTable();
        }

        /**
         * Method description
         *
         *
         * @param selectmode
         */
        public void refresh_map_objects(boolean selectmode) {
            for (TableLine line : this.TABLE_DATA.all_lines) {
                if (line.wheather_show) {
                    Branches.CompanyLine item = (Branches.CompanyLine) line;
                    String displayName = "";

                    item.map_id = Branches.this.mapa.addObject(Branches.this.LIVE_OFFICE,
                            (float) Branches.this.worldRectangle.convertX(item.x),
                            (float) Branches.this.worldRectangle.convertY(item.y), displayName,
                            new Branches.SelectMapData(item, 0));
                    Branches.this.mapa.selectHighlight(item.map_id, (item.selected) && (selectmode));
                    Branches.this.drawWarehousesOnMapa(item, (item.selected) && (selectmode));
                }
            }
        }

        /**
         * Method description
         *
         *
         * @param table
         */
        public void enterFocus(Table table) {}

        /**
         * Method description
         *
         *
         * @param table
         */
        public void leaveFocus(Table table) {}

        protected void deinit() {
            this.table.deinit();
        }
    }


    static class CompanyLine extends TableLine {
        ManageBranchesManager.OfficeId id;
        int map_id;
        String name;
        double funds;
        float x;
        float y;
        boolean isGray;

        CompanyLine() {
            this.isGray = false;
        }
    }


    class CompanyWarehousesInArea extends Branches.WarehousesInArea {
        CompanyWarehousesInArea(long paramLong) {
            super(paramLong, "MB - Company Branches - AREA - TABLEGROUP - 5 38",
                  "MB - Company Branches - AREA - Tableranger");
        }

        @Override
        protected void reciveTableData() {
            if (Branches.this.headquater.isInFocus()) {
                ArrayList query = new ArrayList();

                query.add(Branches.HeadquaterLine.access$2100(Branches.this.headquater));
                this.TABLE_DATA.all_lines.addAll(Branches.this.reciveCompanyWarehouses(query));
            } else {
                ArrayList query = new ArrayList();
                ArrayList selected = Branches.this.company.getSelectedMultiple();

                for (TableLine line : selected) {
                    if (line.wheather_show) {
                        query.add(line);
                    }
                }

                this.TABLE_DATA.all_lines.addAll(Branches.this.reciveCompanyWarehouses(query));
            }
        }
    }


    static class DescriptionLine extends TableLine {
        String name;
        int map_id;
        float x;
        float y;
        boolean isGray;
        boolean companyAffected;
        boolean saleAffected;
    }


    class HeadquaterLine implements IFocusHolder {
        private long[] headquater = null;
        private Branches.CompanyLine headquaterInfo = null;

        HeadquaterLine(long _menu) {
            this.headquater = new long[Branches.HEADQUATER.length];

            for (int i = 0; i < Branches.HEADQUATER.length; ++i) {
                this.headquater[i] = menues.FindFieldInMenu(_menu, Branches.access$300()[i]);
                menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.headquater[i]), this, "onHeadquater",
                                          2L);
            }

            menues.SetFieldText(this.headquater[0],
                                ManageBranchesManager.getManageBranchesManager().GetHeadQuaterName());
            FocusManager.register(this);
            this.headquaterInfo = Branches.this.reciveCompanyHeadquater();
        }

        /**
         * Method description
         *
         */
        @Override
        public void cbEnterFocus() {}

        /**
         * Method description
         *
         */
        @Override
        public void cbLeaveFocus() {
            deselectHeadQuater();
        }

        /**
         * Method description
         *
         */
        @Override
        public void ControlsCtrlAPressed() {}

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isInFocus() {
            return FocusManager.isFocused(this);
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onHeadquater(long _menu, MENUbutton_field button) {
            selectHeadQuater();
        }

        private void selectHeadQuater() {
            FocusManager.enterFocus(this);

            for (int i = 0; i < this.headquater.length; ++i) {
                menues.SetFieldState(this.headquater[i], 1);
            }

            menues.SetShowField(Branches.this.action_controls[0], false);
            menues.SetShowField(Branches.this.action_controls[1], true);
            Branches.this.company_warehouses.updateTable();
            Branches.this.refresh_mapa();
        }

        private void deselectHeadQuater() {
            for (int i = 0; i < this.headquater.length; ++i) {
                menues.SetFieldState(this.headquater[i], 0);
            }

            menues.SetShowField(Branches.this.action_controls[0], true);
            menues.SetShowField(Branches.this.action_controls[1], false);
        }

        /**
         * Method description
         *
         *
         * @param selectmode
         */
        public void refresh_map_objects(boolean selectmode) {
            Branches.CompanyLine item = this.headquaterInfo;
            String display_name = "";

            item.map_id = Branches.this.mapa.addObject(Branches.this.LIVE_OFFICE,
                    (float) Branches.this.worldRectangle.convertX(item.x),
                    (float) Branches.this.worldRectangle.convertY(item.y), display_name,
                    new Branches.SelectMapData(item, 2));
            Branches.this.mapa.selectHighlight(item.map_id, selectmode);
            Branches.this.drawWarehousesOnMapa(item, selectmode);
        }

        /**
         * Method description
         *
         */
        public void deinit() {
            FocusManager.unRegister(this);
        }
    }


    class SaleBranches extends TableWrapped {
        Branches.sort tablesort = new Branches.sort(Branches.this.getSortTypeSale(0), true);
        private String originalFunds = "";

        SaleBranches(long _menu) {
            super(_menu, 2, false, "..\\data\\config\\menu\\menu_office.xml",
                  "MB - Offices For Sale - TABLEGROUP - 11 38", "MB - Offices For Sale - Tableranger",
                  "Tablegroup - ELEMENTS - Offices For Sale", Branches.SALE_LINE_ELEMENTS, null, Branches.SALE_SORT);

            long[] texts = this.table.getLineStatistics_controls(Branches.SALE_LINE_ELEMENTS[2]);

            if ((null == texts) || (texts.length == 0)) {
                Log.menu("ERRORR. SaleBranches table does not includes " + Branches.SALE_LINE_ELEMENTS[2]
                         + " control in line.");
            }

            this.originalFunds = menues.GetFieldText(texts[0]);
            deselectOnLooseFocus(true);
        }

        @Override
        protected void reciveTableData() {
            this.TABLE_DATA.all_lines.addAll(Branches.this.reciveSales());
        }

        /**
         * Method description
         *
         *
         * @param button
         * @param position
         * @param table_node
         */
        @Override
        public void SetupLineInTable(long button, int position, TableLine table_node) {
            Branches.SalesLine line = (Branches.SalesLine) table_node;

            switch (position) {
             case 0 :
                 if (line.isGray) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);
                     menues.SetFieldText(button, line.name);
                 }

                 break;

             case 1 :
                 if (!(line.isGray)) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);
                     menues.SetFieldText(button, line.name);
                 }

                 break;

             case 2 :
                 if (line.isGray) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);

                     KeyPair[] keys = new KeyPair[1];

                     keys[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.funds));
                     menues.SetFieldText(button, MacroKit.Parse(this.originalFunds, keys));
                 }

                 break;

             case 3 :
                 if (!(line.isGray)) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);

                     KeyPair[] keys = new KeyPair[1];

                     keys[0] = new KeyPair("MONEY", Helper.convertMoney((int) line.funds));
                     menues.SetFieldText(button, MacroKit.Parse(this.originalFunds, keys));
                 }
            }

            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        /**
         * Method description
         *
         *
         * @param linedata
         */
        @Override
        public void updateSelectedInfo(TableLine linedata) {
            Branches.this.sales_warehouses.updateTable();
            Branches.this.refresh_mapa();
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onSort(long _menu, MENUsimplebutton_field button) {
            this.tablesort = new Branches.sort(Branches.this.getSortTypeSale(button.userid), !(this.tablesort.up));
            updateTable();
        }

        /**
         * Method description
         *
         *
         * @param hightlite
         */
        public void refresh_map_objects(boolean hightlite) {
            for (TableLine line : this.TABLE_DATA.all_lines) {
                if (line.wheather_show) {
                    Branches.SalesLine item = (Branches.SalesLine) line;
                    String displayName = "";

                    item.map_id = Branches.this.mapa.addObject(Branches.this.SALES_OFFICE,
                            (float) Branches.this.worldRectangle.convertX(item.x),
                            (float) Branches.this.worldRectangle.convertY(item.y), displayName,
                            new Branches.SelectMapData(item, 1));
                    Branches.this.mapa.selectHighlight(item.map_id, (item.selected) && (hightlite));
                    Branches.this.drawWarehousesOnMapa(item, (item.selected) && (hightlite));
                }
            }
        }

        /**
         * Method description
         *
         *
         * @param table
         */
        public void enterFocus(Table table) {}

        /**
         * Method description
         *
         *
         * @param table
         */
        public void leaveFocus(Table table) {}

        protected void deinit() {
            this.table.deinit();
        }
    }


    static class SalesLine extends TableLine {
        ManageBranchesManager.OfficeId id;
        int map_id;
        String name;
        double funds;
        float x;
        float y;
        boolean isGray;

        SalesLine() {
            this.isGray = false;
        }
    }


    class SalesWarehousesInArea extends Branches.WarehousesInArea {
        SalesWarehousesInArea(long paramLong) {
            super(paramLong, "MB - Offices For Sale - AREA - TABLEGROUP - 5 38",
                  "MB - Offices For Sale - AREA - Tableranger");
        }

        @Override
        protected void reciveTableData() {
            ArrayList query = new ArrayList();
            ArrayList selected = Branches.this.sales.getSelectedMultiple();

            for (TableLine line : selected) {
                if (line.wheather_show) {
                    query.add(line);
                }
            }

            this.TABLE_DATA.all_lines.addAll(Branches.this.reciveSalesWarehouses(query));
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
            Branches.SelectMapData data = (Branches.SelectMapData) sender;

            switch (data.type) {
             case 2 :
                 Branches.this.headquater.selectHeadQuater();

                 break;

             case 0 :
                 Branches.this.company.EnterFocus();
                 Branches.this.company.selectLineByData(data.line);

                 break;

             case 1 :
                 Branches.this.sales.EnterFocus();
                 Branches.this.sales.selectLineByData(data.line);
            }
        }
    }


    static class SelectMapData {
        TableLine line;
        int type;

        SelectMapData(TableLine line, int type) {
            this.line = line;
            this.type = type;
        }
    }


    class SortedWarehouseDrawQuere {
        private final ArrayList<Branches.SortedWarehouseDrawQuereItem> queue;

        SortedWarehouseDrawQuere() {
            this.queue = new ArrayList();
        }

        void clear() {
            this.queue.clear();
        }

        void add(Branches.DescriptionLine item, boolean hightlite, int type, int type_select) {
            for (Branches.SortedWarehouseDrawQuereItem line : this.queue) {
                if (line.line.name.compareTo(item.name) == 0) {
                    if ((hightlite) && (!(line.hightlite))) {
                        line.hightlite = hightlite;
                        line.type = type;
                        line.type_select = type_select;
                    }

                    return;
                }
            }

            this.queue.add(new Branches.SortedWarehouseDrawQuereItem(item, hightlite, type, type_select));
        }

        void draw() {
            for (Branches.SortedWarehouseDrawQuereItem line : this.queue) {
                line.line.map_id = Branches.this.mapa.addObject(line.type,
                        (float) Branches.this.worldRectangle.convertX(line.line.x),
                        (float) Branches.this.worldRectangle.convertY(line.line.y), line.line.name,
                        new Branches.SelectMapData(line.line, line.type_select));
                Branches.this.mapa.selectHighlight(line.line.map_id, line.hightlite);
            }
        }
    }


    static class SortedWarehouseDrawQuereItem {
        Branches.DescriptionLine line;
        boolean hightlite;
        int type;
        int type_select;

        SortedWarehouseDrawQuereItem(Branches.DescriptionLine line, boolean hightlite, int type, int type_select) {
            this.line = line;
            this.hightlite = hightlite;
            this.type = type;
            this.type_select = type_select;
        }
    }


    abstract class WarehousesInArea extends TableWrapped {
        WarehousesInArea(long paramLong, String paramString1, String paramString2) {
            super(paramLong, 0, false, "..\\data\\config\\menu\\menu_office.xml", paramString1, paramString2,
                  "Tablegroup - ELEMENTS - CompanyBranches - AREA", Branches.DESCRIPTION_LINE_ELEMENTS, null, null);
        }

        protected void deinit() {
            this.table.deinit();
        }

        /**
         * Method description
         *
         *
         * @param table
         */
        public void enterFocus(Table table) {}

        /**
         * Method description
         *
         *
         * @param table
         */
        public void leaveFocus(Table table) {}

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onSort(long _menu, MENUsimplebutton_field button) {}

        /**
         * Method description
         *
         *
         * @param button
         * @param position
         * @param table_node
         */
        @Override
        public final void SetupLineInTable(long button, int position, TableLine table_node) {
            Branches.DescriptionLine line = (Branches.DescriptionLine) table_node;

            switch (position) {
             case 0 :
                 if (line.isGray) {
                     menues.SetShowField(button, false);

                     return;
                 }

                 menues.SetShowField(button, true);
                 menues.SetFieldText(button, line.name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(button));

                 break;

             case 1 :
                 if (!(line.isGray)) {
                     menues.SetShowField(button, false);

                     return;
                 }

                 menues.SetShowField(button, true);
                 menues.SetFieldText(button, line.name);
                 menues.UpdateMenuField(menues.ConvertMenuFields(button));
            }
        }

        /**
         * Method description
         *
         *
         * @param linedata
         */
        @Override
        public final void updateSelectedInfo(TableLine linedata) {}
    }


    static class sort {
        int type;
        boolean up;

        sort(int type, boolean up) {
            this.type = type;
            this.up = up;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
