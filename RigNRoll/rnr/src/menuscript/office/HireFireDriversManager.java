/*
 * @(#)HireFireDriversManager.java   13/08/26
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
public class HireFireDriversManager {

    /** Field description */
    public static final int my_sort_by_name = 1;

    /** Field description */
    public static final int my_sort_by_rank = 2;

    /** Field description */
    public static final int my_sort_by_wage = 3;

    /** Field description */
    public static final int my_sort_by_loyalty = 4;

    /** Field description */
    public static final int my_sort_by_roi = 5;

    /** Field description */
    public static final int my_sort_by_wins_div_miss = 6;

    /** Field description */
    public static final int my_sort_by_forefit = 7;

    /** Field description */
    public static final int my_sort_by_maintenance = 8;

    /** Field description */
    public static final int my_sort_by_gas = 9;

    /** Field description */
    public static final int my_sort_by_vehicle = 10;

    /** Field description */
    public static final int dealer_sort_by_name = 1;

    /** Field description */
    public static final int dealer_sort_by_recruiter = 2;

    /** Field description */
    public static final int dealer_sort_by_rank = 3;

    /** Field description */
    public static final int dealer_sort_by_wage = 4;

    /** Field description */
    public static final int dealer_sort_by_gender = 5;

    /** Field description */
    public static final int dealer_sort_by_age = 6;

    /** Field description */
    public static final int dealer_sort_by_tickets = 7;

    /** Field description */
    public static final int dealer_sort_by_accidents = 8;

    /** Field description */
    public static final int dealer_sort_by_has_felony = 9;

    /** Field description */
    public static final int dealer_sort_by_experience = 10;

    /** Field description */
    public static final int apply_ok = 0;

    /** Field description */
    public static final int apply_not_enough_vehicles_for_drivers = 1;

    /** Field description */
    public static final int apply_not_enough_money = 2;

    /** Field description */
    public static final int apply_your_rank_is_not_sufficient = 3;

    /** Field description */
    public static final int field_gender = 0;

    /** Field description */
    public static final int field_age = 1;

    /** Field description */
    public static final int field_tickets = 2;

    /** Field description */
    public static final int field_accidents = 3;

    /** Field description */
    public static final int field_has_felony = 4;

    /** Field description */
    public static final int field_experience = 5;

    /** Field description */
    public static final int field_max = 6;
    private static HireFireDriversManager instance;
    private final boolean DEBUG_MODE = eng.noNative;
    private final int apply_error_code = 0;

    /** Field description */
    public int in_int = 0;

    /** Field description */
    public Filter in_filter = new Filter();
    private final Vector<FilterField> fields = new Vector<FilterField>();
    private final Vector<FilterField> short_fields = new Vector<FilterField>();

    /** Field description */
    public DriverId in_id = new DriverId();
    private final FullMyDriverInfo full_my_info = new FullMyDriverInfo();
    private final FullDealerDriverInfo full_dealer_info = new FullDealerDriverInfo();
    private final Summary summary = new Summary();

    /** Field description */
    public boolean in_bool = false;

    /** Field description */
    public Vector<DriverId> in_ids = new Vector<DriverId>();
    private final Vector<ShotMyDriverInfo> short_my_info = new Vector<ShotMyDriverInfo>();
    private final Vector<ShotDealerDriverInfo> short_dealer_info = new Vector<ShotDealerDriverInfo>();

    private HireFireDriversManager() {
        if (this.DEBUG_MODE) {
            FilterField field1 = new FilterField();
            FilterField field2 = new FilterField();
            FilterField field3 = new FilterField();
            FilterField field4 = new FilterField();

            field1.name = "Any";
            field1.show_me = false;
            field2.name = "18-25";
            field2.show_me = false;
            field3.name = "25-35";
            field3.show_me = true;
            field4.name = "35-45";
            field4.show_me = false;
            this.fields.add(field1);
            this.fields.add(field2);
            this.fields.add(field3);
            this.fields.add(field4);
            this.short_fields.add(field1);
            this.short_fields.add(field2);
            this.short_fields.add(field3);
            this.short_fields.add(field4);
            this.full_my_info.id = new DriverId(1);
            this.full_my_info.driver_name = "Driver1";
            this.full_my_info.age = 15;
            this.full_my_info.face_index = "Woman001";
            this.full_my_info.loyalty = 18.0F;
            this.full_my_info.roi = 15000.0F;
            this.full_my_info.forefit = 10000.0F;
            this.full_my_info.wins_missions = 100;
            this.full_my_info.maintenance = 30000.0F;
            this.full_my_info.gas = 4000.0F;
            this.full_my_info.short_vehicle_name = "Century";
            this.full_my_info.make = "Century";
            this.full_my_info.model = "Frrr";
            this.full_my_info.license_plate = "01234567";
            this.full_my_info.vehJustBought = true;
            this.full_dealer_info.id = new DriverId(2);
            this.full_dealer_info.driver_name = "Player112";
            this.full_dealer_info.age = 18;
            this.full_dealer_info.face_index = "Man_020";
            this.full_dealer_info.gender = "Female";
            this.full_dealer_info.tickets = 999;
            this.full_dealer_info.accidents = 1850;
            this.full_dealer_info.bHasFelony = true;
            this.full_dealer_info.experience = 7.0F;
            this.summary.vacant = 999;
            this.summary.to_fire = 888;
            this.summary.to_hire = 100;
            this.summary.advance = 8000000.0F;
            this.summary.commission = -22500000.0F;
            this.summary.total = 1000000.0F;

            for (int i = 0; i < 10; ++i) {
                ShotMyDriverInfo my = new ShotMyDriverInfo();

                my.id = new DriverId(2 * (i + 1));
                my.driver_name = "Driver " + i;
                my.rank = (100 * i);
                my.wage = (10000.0F + 10000.0F * i);
                my.justHire = ((i & 0x1) != 0);
                this.short_my_info.add(my);

                ShotDealerDriverInfo dealer = new ShotDealerDriverInfo();

                dealer.id = new DriverId(2 * (i + 1) + 1);
                dealer.driver_name = "Ghhasbnm" + i + "sdafgahk";
                dealer.dealer_name = "California Drivers " + i;
                dealer.rank = (200 * i);
                dealer.wage = (10000.0F + 20000.0F * i);
                dealer.justFire = ((i & 0x1) == 0);
                dealer.bCanHire = ((i & 0x2) == 0);
                this.short_dealer_info.add(dealer);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static HireFireDriversManager getHireFireDriversManager() {
        if (null == instance) {
            instance = new HireFireDriversManager();
        }

        return instance;
    }

    Vector<FilterField> GetFilterFields(int filed) {
        if (!(this.DEBUG_MODE)) {
            this.in_int = filed;
            JavaEvents.SendEvent(35, 0, this);
        }

        Vector<FilterField> res = (Vector<FilterField>) this.fields.clone();

        return res;
    }

    Vector<FilterField> GetShortFilterFields(int filed) {
        if (!(this.DEBUG_MODE)) {
            this.in_int = filed;
            JavaEvents.SendEvent(35, 11, this);
        }

        Vector<FilterField> res = (Vector<FilterField>) this.short_fields.clone();

        return res;
    }

    void SetFilter(Filter filter) {
        if (!(this.DEBUG_MODE)) {
            this.in_filter = filter;
            JavaEvents.SendEvent(35, 1, this);
        }
    }

    FullMyDriverInfo GetMyDriverInfo(DriverId selected_id) {
        if (!(this.DEBUG_MODE)) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(35, 2, this);
        }

        return this.full_my_info;
    }

    FullDealerDriverInfo GetDealerVehiclesInfo(DriverId selected_id) {
        if (!(this.DEBUG_MODE)) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(35, 3, this);
        }

        return this.full_dealer_info;
    }

    Summary GetSummary() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(35, 4, this);
        }

        return this.summary;
    }

    Vector<ShotMyDriverInfo> GetMyDriverList(int sort_mode, boolean bUp) {
        if (!(this.DEBUG_MODE)) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            JavaEvents.SendEvent(35, 5, this);
        }

        Vector<ShotMyDriverInfo> res = (Vector<ShotMyDriverInfo>) this.short_my_info.clone();

        return res;
    }

    Vector<ShotDealerDriverInfo> GetDealerVehiclesList(Filter filter, int sort_mode, boolean bUp) {
        if (!(this.DEBUG_MODE)) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            SetFilter(filter);
            JavaEvents.SendEvent(35, 6, this);
        }

        Vector<ShotDealerDriverInfo> res = (Vector<ShotDealerDriverInfo>) this.short_dealer_info.clone();

        return res;
    }

    void I_Fire(Vector<DriverId> ids) {
        if (!(this.DEBUG_MODE)) {
            this.in_ids = ids;
            JavaEvents.SendEvent(35, 7, this);
        }
    }

    int I_Hire(Vector<DriverId> ids) {
        if (!(this.DEBUG_MODE)) {
            this.in_ids = ids;
            JavaEvents.SendEvent(35, 8, this);
        }

        return this.apply_error_code;
    }

    void Discard() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(35, 9, this);
        }
    }

    int Apply() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(35, 10, this);
        }

        return this.apply_error_code;
    }

    static class DriverId {
        int id;

        DriverId() {}

        DriverId(int id) {
            this.id = id;
        }
    }


    static class Filter {
        int[] fileds;

        Filter() {
            this.fileds = new int[6];
        }
    }


    static class FilterField {
        String name;
        boolean show_me;
    }


    static class FullDealerDriverInfo {
        HireFireDriversManager.DriverId id;
        String driver_name;
        int age;
        String face_index;
        String gender;
        int tickets;
        int accidents;
        boolean bHasFelony;
        float experience;

        FullDealerDriverInfo() {
            this.id = new HireFireDriversManager.DriverId();
        }
    }


    static class FullMyDriverInfo {
        HireFireDriversManager.DriverId id;
        String driver_name;
        int age;
        String face_index;
        float loyalty;
        float loyalty_temp;
        float roi;
        float roi_temp;
        float wins_temp;
        int wins_missions;
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

        FullMyDriverInfo() {
            this.id = new HireFireDriversManager.DriverId();
        }
    }


    static class ShotDealerDriverInfo {
        HireFireDriversManager.DriverId id;
        String driver_name;
        String dealer_name;
        int rank;
        float wage;
        boolean justFire;
        boolean bCanHire;

        ShotDealerDriverInfo() {
            this.id = new HireFireDriversManager.DriverId();
        }
    }


    static class ShotMyDriverInfo {
        HireFireDriversManager.DriverId id;
        String driver_name;
        int rank;
        float wage;
        boolean justHire;

        ShotMyDriverInfo() {
            this.id = new HireFireDriversManager.DriverId();
        }
    }


    static class Summary {
        int vacant;
        int to_fire;
        int to_hire;
        float advance;
        float commission;
        float total;
    }
}


//~ Formatted in DD Std on 13/08/26
