/*
 * @(#)ManageBranchesManager.java   13/08/26
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

import rnr.src.menu.JavaEvents;
import rnr.src.menuscript.office.ManageBranchesManager.OfficeId;
import rnr.src.menuscript.office.ManageBranchesManager.OfficeInfo;
import rnr.src.menuscript.office.ManageBranchesManager.WarehouseInfo;
import rnr.src.rnrcore.eng;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class ManageBranchesManager {

    /** Field description */
    public static final int sort_by_name = 1;

    /** Field description */
    public static final int sort_by_funds = 2;

    /** Field description */
    public static final int apply_ok = 0;

    /** Field description */
    public static final int apply_not_enough_money = 1;

    /** Field description */
    public static final int apply_not_enough_turnover = 2;
    private static ManageBranchesManager instance;
    private final boolean DEBUG_MODE = eng.noNative;
    private final ErrorInfo apply_error_code = new ErrorInfo();

    /** Field description */
    public int in_int = 0;

    /** Field description */
    public boolean in_bool = false;

    /** Field description */
    public Vector<OfficeId> in_ids = new Vector<OfficeId>();
    private final Vector<OfficeInfo> offices_info = new Vector<OfficeInfo>();
    private final Vector<WarehouseInfo> warehouses_info = new Vector<WarehouseInfo>();
    private final Summary summary = new Summary();

    /** Field description */
    public String out_string = "Oxnard";

    private ManageBranchesManager() {
        this.apply_error_code.error_code = 0;
        this.apply_error_code.misc_info = 0;

        if (this.DEBUG_MODE) {
            this.summary.established = 2;
            this.summary.closed_up = 1;
            this.summary.warehouses_on_contract = 24;
            this.summary.total_warehouses = 42;
            this.summary.invested = -780000.0F;
            this.summary.released = 360000.0F;
            this.summary.total = 420000.0F;

            OfficeInfo info0 = new OfficeInfo();

            info0.id = new OfficeId(0);
            info0.where = "Reno";
            info0.funds = 450000.0F;
            info0.isGray = false;
            info0.isHeadquarter = false;
            info0.x = 0.0F;
            info0.y = 0.0F;
            this.offices_info.add(info0);

            OfficeInfo info1 = new OfficeInfo();

            info1.id = new OfficeId(1);
            info1.where = "San Diego";
            info1.funds = 570000.0F;
            info1.isGray = true;
            info1.isHeadquarter = false;
            info1.x = 0.5F;
            info1.y = 0.5F;
            this.offices_info.add(info1);

            OfficeInfo info2 = new OfficeInfo();

            info2.id = new OfficeId(2);
            info2.where = "Oxnard";
            info2.funds = 570000.0F;
            info2.isGray = false;
            info2.isHeadquarter = true;
            info2.x = 0.0F;
            info2.y = 0.0F;
            this.offices_info.add(info2);

            WarehouseInfo w0 = new WarehouseInfo();

            w0.name = "Los Angeles";
            w0.isGray = false;
            w0.x = -0.5F;
            w0.y = -0.5F;
            w0.companyAffected = true;
            w0.saleAffected = false;
            this.warehouses_info.add(w0);

            WarehouseInfo w1 = new WarehouseInfo();

            w1.name = "Santa Barbara";
            w1.isGray = true;
            w1.x = -0.25F;
            w1.y = -0.25F;
            w1.companyAffected = true;
            w1.saleAffected = true;

            WarehouseInfo w2 = new WarehouseInfo();

            w2.name = "Santa Barbara";
            w2.isGray = true;
            w2.x = -0.25F;
            w2.y = -0.25F;
            w2.companyAffected = false;
            w2.saleAffected = true;
            this.warehouses_info.add(w2);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static ManageBranchesManager getManageBranchesManager() {
        if (null == instance) {
            instance = new ManageBranchesManager();
        }

        return instance;
    }

    Summary GetSummary() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(37, 0, this);
        }

        return this.summary;
    }

    Vector<OfficeInfo> GetCompanyBranches(int sort_mode, boolean bUp) {
        if (!(this.DEBUG_MODE)) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            JavaEvents.SendEvent(37, 1, this);
        }

        Vector<OfficeInfo> res = (Vector<OfficeInfo>) this.offices_info.clone();

        return res;
    }

    Vector<OfficeInfo> GetOfficesForSale(int sort_mode, boolean bUp) {
        if (!(this.DEBUG_MODE)) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            JavaEvents.SendEvent(37, 2, this);
        }

        Vector<OfficeInfo> res = (Vector<OfficeInfo>) this.offices_info.clone();

        return res;
    }

    Vector<WarehouseInfo> GetWarehousesInTheArea(Vector<OfficeId> list) {
        if (!(this.DEBUG_MODE)) {
            this.in_ids = list;
            JavaEvents.SendEvent(37, 3, this);
        }

        Vector<WarehouseInfo> res = (Vector<WarehouseInfo>) this.warehouses_info.clone();

        return res;
    }

    void CloseUpBranch(Vector<OfficeId> list) {
        if (!(this.DEBUG_MODE)) {
            this.in_ids = list;
            JavaEvents.SendEvent(37, 4, this);
        }
    }

    void EstablishNewBranch(Vector<OfficeId> list) {
        if (!(this.DEBUG_MODE)) {
            this.in_ids = list;
            JavaEvents.SendEvent(37, 5, this);
        }
    }

    ErrorInfo Apply() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(37, 6, this);
        }

        return this.apply_error_code;
    }

    void Discard() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(37, 7, this);
        }
    }

    String GetHeadQuaterName() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(37, 8, this);
        }

        return this.out_string;
    }

    static class ErrorInfo {
        int error_code;
        int misc_info;
    }


    static class OfficeId {
        int id;

        OfficeId() {}

        OfficeId(int id) {
            this.id = id;
        }
    }


    static class OfficeInfo {
        ManageBranchesManager.OfficeId id;
        String where;
        float funds;
        boolean isGray;
        boolean isHeadquarter;
        float x;
        float y;

        OfficeInfo() {
            this.id = new ManageBranchesManager.OfficeId();
        }
    }


    static class Summary {
        int established;
        int closed_up;
        int warehouses_on_contract;
        int total_warehouses;
        float invested;
        float released;
        float total;
    }


    static class WarehouseInfo {
        String name;
        boolean isGray;
        float x;
        float y;
        boolean companyAffected;
        boolean saleAffected;
    }
}


//~ Formatted in DD Std on 13/08/26
