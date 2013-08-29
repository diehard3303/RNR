/*
 * @(#)ManageFlitManager.java   13/08/26
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
public class ManageFlitManager {

    /** Field description */
    public static final int my_sort_by_name = 1;

    /** Field description */
    public static final int my_sort_by_price = 2;

    /** Field description */
    public static final int my_sort_by_license = 3;

    /** Field description */
    public static final int my_sort_by_type = 4;

    /** Field description */
    public static final int my_sort_by_condition = 5;

    /** Field description */
    public static final int my_sort_by_wear = 6;

    /** Field description */
    public static final int my_sort_by_speed = 7;

    /** Field description */
    public static final int my_sort_by_load_safety = 8;

    /** Field description */
    public static final int my_sort_by_horse_power = 9;

    /** Field description */
    public static final int my_sort_by_color = 10;

    /** Field description */
    public static final int dealer_sort_by_name = 1;

    /** Field description */
    public static final int dealer_sort_by_dealer = 2;

    /** Field description */
    public static final int dealer_sort_by_descount = 3;

    /** Field description */
    public static final int dealer_sort_by_price = 4;

    /** Field description */
    public static final int dealer_sort_by_type = 5;

    /** Field description */
    public static final int dealer_sort_by_make = 6;

    /** Field description */
    public static final int dealer_sort_by_model = 7;

    /** Field description */
    public static final int dealer_sort_by_milleage = 8;

    /** Field description */
    public static final int dealer_sort_by_suspension = 9;

    /** Field description */
    public static final int dealer_sort_by_horsepower = 10;

    /** Field description */
    public static final int dealer_sort_by_color = 11;

    /** Field description */
    public static final int apply_ok = 0;

    /** Field description */
    public static final int apply_not_enough_vehicles_for_drivers = 1;

    /** Field description */
    public static final int apply_not_enough_money = 2;

    /** Field description */
    public static final int field_price = 0;

    /** Field description */
    public static final int field_type = 1;

    /** Field description */
    public static final int field_make = 2;

    /** Field description */
    public static final int field_model = 3;

    /** Field description */
    public static final int field_milleage = 4;

    /** Field description */
    public static final int field_horse_power = 5;

    /** Field description */
    public static final int field_suspension = 6;

    /** Field description */
    public static final int field_color = 7;

    /** Field description */
    public static final int field_max = 8;
    private static ManageFlitManager instance;
    private final boolean DEBUG_MODE = eng.noNative;
    private final Vector fields = new Vector();
    private final Vector short_fields = new Vector();
    private final FullMyVehicleInfo full_my_info = new FullMyVehicleInfo();
    private final FullDealerVehicleInfo full_dealer_info = new FullDealerVehicleInfo();
    private final Summary summary = new Summary();
    private final Vector short_my_info = new Vector();
    private final Vector short_dealer_info = new Vector();
    private final ShotMyVehicleInfo short_my_vehicle_info = new ShotMyVehicleInfo();
    private final int apply_error_code = 0;

    /** Field description */
    public int in_int = 0;

    /** Field description */
    public Filter in_filter = new Filter();

    /** Field description */
    public VehicleId in_id = new VehicleId();

    /** Field description */
    public boolean in_bool = false;

    /** Field description */
    public Vector in_ids = new Vector();

    private ManageFlitManager() {
        if (this.DEBUG_MODE) {
            FilterField field1 = new FilterField();
            FilterField field2 = new FilterField();
            FilterField field3 = new FilterField();
            FilterField field4 = new FilterField();

            field1.name = "Any";
            field1.show_me = false;
            field2.name = "1";
            field2.show_me = false;
            field3.name = "2";
            field3.show_me = true;
            field4.name = "...";
            field4.show_me = false;
            this.fields.add(field1);
            this.fields.add(field2);
            this.fields.add(field3);
            this.fields.add(field4);
            this.short_fields.add(field1);
            this.short_fields.add(field2);
            this.short_fields.add(field3);
            this.short_fields.add(field4);
            this.full_my_info.id = new VehicleId(1);
            this.full_my_info.vehicle_name = "Kenworth t200";
            this.full_my_info.license_plate = "VODILA1";
            this.full_my_info.type = "tractor";
            this.full_my_info.condition = 0.5F;
            this.full_my_info.wear = 0.2F;
            this.full_my_info.speed = 0.1F;
            this.full_my_info.load_safety = 0.6F;
            this.full_my_info.horse_power = 300.0F;
            this.full_my_info.color = "Red";
            this.full_my_info.vehicle_picture = "FREIGHTLINER_CENTURY";
            this.full_my_info.color_picture = "Unknown";
            this.full_dealer_info.id = new VehicleId(2);
            this.full_dealer_info.vehicle_name = "Freightliner Century";
            this.full_dealer_info.price = 52000.0F;
            this.full_dealer_info.type = "tractor";
            this.full_dealer_info.make = "Freightliner";
            this.full_dealer_info.model = "Century";
            this.full_dealer_info.mileage = 560.0F;
            this.full_dealer_info.suspension = "Air Ride 100";
            this.full_dealer_info.horsepower = 420.0F;
            this.full_dealer_info.color = "Black";
            this.full_dealer_info.vehicle_picture = "FREIGHTLINER_ARGOSY";
            this.full_dealer_info.color_picture = "Unknown";
            this.summary.vehiles_to_sell = 8;
            this.summary.vehiles_to_purchase = 4;
            this.summary.proceeds = 80000.0F;
            this.summary.expenses = -225000.0F;
            this.summary.overhead = -20000.0F;
            this.summary.total = 165000.0F;

            for (int i = 0; i < 10; ++i) {
                ShotMyVehicleInfo my = new ShotMyVehicleInfo();

                my.id = new VehicleId(2 * (i + 1));
                my.vehicle_name = "Mack Vision " + i;
                my.price = (52000.0F + 5000.0F * i);
                my.isGray = ((i & 0x1) != 0);
                this.short_my_info.add(my);

                ShotDealerVehicleInfo dealer = new ShotDealerVehicleInfo();

                dealer.id = new VehicleId(2 * (i + 1) + 1);
                dealer.vehicle_name = "Freightliner Century " + i;
                dealer.dealer_name = "Reno Automarket " + i;
                dealer.discount = (0.05F * i);
                dealer.price = (44000.0F + 10000.0F * i);
                dealer.isGray = ((i & 0x1) == 0);
                this.short_dealer_info.add(dealer);
            }

            this.short_my_vehicle_info.id = new VehicleId(99);
            this.short_my_vehicle_info.vehicle_name = "Peterbilt 378";
            this.short_my_vehicle_info.price = 58000.0F;
            this.short_my_vehicle_info.isGray = false;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static ManageFlitManager getManageFlitManager() {
        if (null == instance) {
            instance = new ManageFlitManager();
        }

        return instance;
    }

    Vector<FilterField> GetFilterFields(int filed) {
        if (!(this.DEBUG_MODE)) {
            this.in_int = filed;
            JavaEvents.SendEvent(33, 0, this);
        }

        Vector res = (Vector) this.fields.clone();

        return res;
    }

    Vector<FilterField> GetShortFilterFields(int filed) {
        if (!(this.DEBUG_MODE)) {
            this.in_int = filed;
            JavaEvents.SendEvent(33, 13, this);
        }

        Vector res = (Vector) this.short_fields.clone();

        return res;
    }

    void SetFilter(Filter filter) {
        if (!(this.DEBUG_MODE)) {
            this.in_filter = filter;
            JavaEvents.SendEvent(33, 1, this);
        }
    }

    FullMyVehicleInfo GetMyVehiclesInfo(VehicleId selected_id) {
        if (!(this.DEBUG_MODE)) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(33, 2, this);
        }

        return this.full_my_info;
    }

    FullMyVehicleInfo GetMyVehiclesInfoWithUpgrades(VehicleId selected_id) {
        if (!(this.DEBUG_MODE)) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(33, 17, this);
        }

        return this.full_my_info;
    }

    FullDealerVehicleInfo GetDealerVehiclesInfo(VehicleId selected_id) {
        if (!(this.DEBUG_MODE)) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(33, 3, this);
        }

        return this.full_dealer_info;
    }

    FullDealerVehicleInfo GetDealerVehiclesInfoWithUpgrades(VehicleId selected_id) {
        if (!(this.DEBUG_MODE)) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(33, 16, this);
        }

        return this.full_dealer_info;
    }

    Summary GetSummary() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(33, 4, this);
        }

        return this.summary;
    }

    ShotMyVehicleInfo GetMyVehicleInfo() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(33, 5, this);
        }

        return this.short_my_vehicle_info;
    }

    Vector<ShotMyVehicleInfo> GetMyVehiclesList(int sort_mode, boolean bUp) {
        if (!(this.DEBUG_MODE)) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            JavaEvents.SendEvent(33, 6, this);
        }

        Vector res = (Vector) this.short_my_info.clone();

        return res;
    }

    Vector<ShotDealerVehicleInfo> GetDealerVehiclesList(Filter filter, int sort_mode, boolean bUp) {
        if (!(this.DEBUG_MODE)) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            this.in_filter = filter;
            JavaEvents.SendEvent(33, 7, this);
        }

        Vector res = (Vector) this.short_dealer_info.clone();

        return res;
    }

    boolean Can_I_Take() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(33, 18, this);

            return this.in_bool;
        }

        return false;
    }

    void I_Take(VehicleId id) {
        if (!(this.DEBUG_MODE)) {
            this.in_id = id;
            JavaEvents.SendEvent(33, 8, this);
        }
    }

    void I_Sell(Vector<VehicleId> ids) {
        if (!(this.DEBUG_MODE)) {
            this.in_ids = ids;
            JavaEvents.SendEvent(33, 9, this);
        }
    }

    void I_Purchase(Vector<VehicleId> ids) {
        if (!(this.DEBUG_MODE)) {
            this.in_ids = ids;
            JavaEvents.SendEvent(33, 10, this);
        }
    }

    void Discard() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(33, 11, this);
        }
    }

    int Apply() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(33, 12, this);
        }

        return this.apply_error_code;
    }

    void OnEnterMenu() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(33, 14, this);
        }
    }

    void OnLeaveMenu() {
        if (!(this.DEBUG_MODE)) {
            JavaEvents.SendEvent(33, 15, this);
        }
    }

    static class Filter {
        int[] fileds;
        String model_user;

        Filter() {
            this.fileds = new int[8];
        }
    }


    static class FilterField {
        String name;
        boolean show_me;
    }


    static class FullDealerVehicleInfo {
        ManageFlitManager.VehicleId id;
        String vehicle_name;
        float price;
        String type;
        String make;
        String model;
        float mileage;
        float condition;
        String suspension;
        float horsepower;
        String color;
        String vehicle_picture;
        String color_picture;
        String upgrades_info_string;

        FullDealerVehicleInfo() {
            this.id = new ManageFlitManager.VehicleId();
        }
    }


    static class FullMyVehicleInfo {
        ManageFlitManager.VehicleId id;
        String vehicle_name;
        String license_plate;
        String type;
        float condition;
        float wear;
        float speed;
        float load_safety;
        float horse_power;
        float mileage;
        String color;
        String vehicle_picture;
        String color_picture;
        String upgrades_info_string;
        String suspension;

        FullMyVehicleInfo() {
            this.id = new ManageFlitManager.VehicleId();
        }
    }


    static class ShotDealerVehicleInfo {
        ManageFlitManager.VehicleId id;
        String vehicle_name;
        String dealer_name;
        float discount;
        float price;
        boolean isGray;

        ShotDealerVehicleInfo() {
            this.id = new ManageFlitManager.VehicleId();
        }
    }


    static class ShotMyVehicleInfo {
        ManageFlitManager.VehicleId id;
        String vehicle_name;
        float price;
        boolean isGray;

        ShotMyVehicleInfo() {
            this.id = new ManageFlitManager.VehicleId();
        }
    }


    static class Summary {
        int vehiles_to_sell;
        int vehiles_to_purchase;
        float proceeds;
        float expenses;
        float overhead;
        float total;
    }


    static class VehicleId {
        int id;

        VehicleId() {}

        VehicleId(int id) {
            this.id = id;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
