/*
 * @(#)ManageDriversManager.java   13/08/26
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
import rnr.src.menuscript.office.ManageDriversManager.DriverId;
import rnr.src.menuscript.office.ManageDriversManager.ShotDriverInfo;
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
public class ManageDriversManager {

    /** Field description */
    public static final int sort_by_name = 1;

    /** Field description */
    public static final int sort_by_rank = 2;

    /** Field description */
    public static final int sort_by_wage = 3;

    /** Field description */
    public static final int sort_by_loyality = 4;

    /** Field description */
    public static final int sort_by_roi = 5;

    /** Field description */
    public static final int sort_by_wins_div_missions = 6;

    /** Field description */
    public static final int sort_by_forefit = 7;

    /** Field description */
    public static final int sort_by_maintenance = 8;

    /** Field description */
    public static final int sort_by_gas = 9;

    /** Field description */
    public static final int sort_by_vehicle = 10;
    private static ManageDriversManager instance;
    private final Vector<ShotDriverInfo> exist_short_driver_info = new Vector<ShotDriverInfo>();

    /** Field description */
    public Vector<DriverId> ids = new Vector<DriverId>();

    /** Field description */
    public DriverId id = new DriverId();

    /** Field description */
    public float in_value = 0.0F;
    private final float out_value = 0.0F;
    private final float out_value0 = 0.0F;
    private final float out_value1 = 0.0F;
    private final FullDriverInfo full_driver_info = new FullDriverInfo();
    private final Summary summary = new Summary();
    private final boolean bRet = false;

    /** Field description */
    public int sort_mode = 1;

    /** Field description */
    public boolean bSortUp = false;

    /** Field description */
    public boolean in_b_value = false;
    private final boolean DEBUG_MODE = eng.noNative;

    private ManageDriversManager() {
        if (this.DEBUG_MODE) {
            int NOM = 2;

            for (int i = 0; i < NOM; ++i) {
                ShotDriverInfo inf = new ShotDriverInfo();

                inf.id = new DriverId(i);
                inf.driver_name = "name " + i;
                inf.isGray = ((i & 0x1) != 0);
                inf.wage = (100.0F * i);
                inf.x = i / NOM;
                inf.y = i / NOM;
                inf.rank = i;
                this.exist_short_driver_info.add(inf);
            }

            this.full_driver_info.id = new DriverId(1);
            this.full_driver_info.driver_name = "some";
            this.full_driver_info.age = 75;
            this.full_driver_info.face_index = "Woman001";
            this.full_driver_info.loyalty = 0.1F;
            this.full_driver_info.roi = 0.1F;
            this.full_driver_info.wins_missions = 100;
            this.full_driver_info.forefit = 0.1F;
            this.full_driver_info.maintenance = 0.1F;
            this.full_driver_info.gas = 0.1F;
            this.full_driver_info.short_vehicle_name = "some vn";
            this.full_driver_info.make = "MAKE";
            this.full_driver_info.model = "MODEL";
            this.full_driver_info.license_plate = "LICENSE";
            this.full_driver_info.vehJustBought = true;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static ManageDriversManager getManageDriversManager() {
        if (null == instance) {
            instance = new ManageDriversManager();
        }

        return instance;
    }

    Vector<ShotDriverInfo> GetShortDriverInfoList(int sort_mode, boolean bUp) {
        if (!(this.DEBUG_MODE)) {
            this.sort_mode = sort_mode;
            this.bSortUp = bUp;
            JavaEvents.SendEvent(29, 0, this);

            Vector<ShotDriverInfo> res = (Vector<ShotDriverInfo>) this.exist_short_driver_info.clone();

            this.exist_short_driver_info.clear();

            return res;
        }

        Vector<ShotDriverInfo> res = (Vector<ShotDriverInfo>) this.exist_short_driver_info.clone();

        return res;
    }

    FullDriverInfo GetFullDriverInfo(DriverId id) {
        this.id = id;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 1, this);
        }

        return this.full_driver_info;
    }

    void SetOrderType(Vector<DriverId> ids, float value) {
        if ((ids == null) || (ids.size() == 0)) {
            return;
        }

        this.ids = ids;
        this.in_value = value;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 2, this);
        }
    }

    float GetOrderType(Vector<DriverId> ids) {
        if ((ids == null) || (ids.size() == 0)) {
            return 0.0F;
        }

        this.ids = ids;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 3, this);
        }

        return this.out_value;
    }

    void SetLoadFargylity(Vector<DriverId> ids, float value) {
        if ((ids == null) || (ids.size() == 0)) {
            return;
        }

        this.ids = ids;
        this.in_value = value;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 4, this);
        }
    }

    float GetLoadFragylity(Vector<DriverId> ids) {
        if ((ids == null) || (ids.size() == 0)) {
            return 0.0F;
        }

        this.ids = ids;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 5, this);
        }

        return this.out_value;
    }

    void SetDistances(Vector<DriverId> ids, float value) {
        if ((ids == null) || (ids.size() == 0)) {
            return;
        }

        this.ids = ids;
        this.in_value = value;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 6, this);
        }
    }

    float GetDistances(Vector<DriverId> ids) {
        if ((ids == null) || (ids.size() == 0)) {
            return 0.0F;
        }

        this.ids = ids;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 7, this);
        }

        return this.out_value;
    }

    void SetWear(Vector<DriverId> ids, float value) {
        if ((ids == null) || (ids.size() == 0)) {
            return;
        }

        this.ids = ids;
        this.in_value = value;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 8, this);
        }
    }

    float GetWear(Vector<DriverId> ids) {
        if ((ids == null) || (ids.size() == 0)) {
            return 0.0F;
        }

        this.ids = ids;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 9, this);
        }

        return this.out_value;
    }

    void SetSpeed(Vector<DriverId> ids, float value) {
        if ((ids == null) || (ids.size() == 0)) {
            return;
        }

        this.ids = ids;
        this.in_value = value;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 10, this);
        }
    }

    float GetSpeed(Vector<DriverId> ids) {
        if ((ids == null) || (ids.size() == 0)) {
            return 0.0F;
        }

        this.ids = ids;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 11, this);
        }

        return this.out_value;
    }

    void SetLoadSafety(Vector<DriverId> ids, float value) {
        if ((ids == null) || (ids.size() == 0)) {
            return;
        }

        this.ids = ids;
        this.in_value = value;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 12, this);
        }
    }

    float GetLoadSafety(Vector<DriverId> ids) {
        if ((ids == null) || (ids.size() == 0)) {
            return 0.0F;
        }

        this.ids = ids;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 13, this);
        }

        return this.out_value;
    }

    void SetWage(Vector<DriverId> ids, float value) {
        if ((ids == null) || (ids.size() == 0)) {
            return;
        }

        this.ids = ids;
        this.in_value = value;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 14, this);
        }
    }

    int[] GetWage(Vector<DriverId> ids) {
        if ((ids == null) || (ids.size() == 0)) {
            return new int[] { 0, 100, 0 };
        }

        this.ids = ids;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 26, this);

            return new int[] { Math.round(this.out_value), Math.round(this.out_value0), Math.round(this.out_value1) };
        }

        return new int[] { 0, 100, 0 };
    }

    boolean Apply() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 16, this);
        }

        return this.bRet;
    }

    void Discard() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 17, this);
        }
    }

    Summary GetSummary() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 18, this);
        }

        return this.summary;
    }

    void SetOrderSelectionCriteria(Vector<DriverId> ids, boolean bSetAuto) {
        if ((ids == null) || (ids.size() == 0)) {
            return;
        }

        this.ids = ids;
        this.in_b_value = bSetAuto;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 19, this);
        }
    }

    boolean GetOrderSelectionCriteria(Vector<DriverId> ids) {
        if ((ids == null) || (ids.size() == 0)) {
            return true;
        }

        this.ids = ids;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 20, this);
        }

        return this.bRet;
    }

    void SetTruckSelectionCriteria(Vector<DriverId> ids, boolean bSetAuto) {
        if ((ids == null) || (ids.size() == 0)) {
            return;
        }

        this.ids = ids;
        this.in_b_value = bSetAuto;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 21, this);
        }
    }

    boolean GetTruckSelectionCriteria(Vector<DriverId> ids) {
        if ((ids == null) || (ids.size() == 0)) {
            return true;
        }

        this.ids = ids;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 22, this);
        }

        return this.bRet;
    }

    void SetWageChangeCriteria(Vector<DriverId> ids, boolean bSetAuto) {
        if ((ids == null) || (ids.size() == 0)) {
            return;
        }

        this.ids = ids;
        this.in_b_value = bSetAuto;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 23, this);
        }
    }

    boolean GetWageChangeCriteria(Vector<DriverId> ids) {
        if ((ids == null) || (ids.size() == 0)) {
            return true;
        }

        this.ids = ids;

        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 24, this);
        }

        return this.bRet;
    }

    void OnEnterOffice() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(29, 25, this);
        }
    }

    void OnLeaveOffice() {}

    static class DriverId {
        int id;

        DriverId() {}

        DriverId(int id) {
            this.id = id;
        }
    }


    static class FullDriverInfo {
        ManageDriversManager.DriverId id;
        String driver_name;
        int age;
        String face_index;
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
        boolean vehJustBought;

        FullDriverInfo() {
            this.id = new ManageDriversManager.DriverId();
        }
    }


    static class ShotDriverInfo {
        ManageDriversManager.DriverId id;
        String driver_name;
        int rank;
        float wage;
        boolean isGray;
        float x;
        float y;

        ShotDriverInfo() {
            this.id = new ManageDriversManager.DriverId();
        }
    }


    static class Summary {
        int drivers_involved;
        int order_criteria_changed;
        int truck_criteria_changed;
        int wages_changed_drivers;
        float wages_changed_day;
    }
}


//~ Formatted in DD Std on 13/08/26
