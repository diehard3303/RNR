/*
 * @(#)HeadUpDisplay.java   13/08/26
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
   Released under the BSD 3 clause license
Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this 
    list of conditions and the following disclaimer. Redistributions in binary 
    form must reproduce the above copyright notice, this list of conditions and 
    the following disclaimer in the documentation and/or other materials 
    provided with the distribution. Neither the name of the DieHard Development 
    nor the names of its contributors may be used to endorse or promote products 
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 *
 *
 *
 */


package rnr.src.menuscript;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.MenuAfterInitNarrator;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.rnrcore.Log;
import rnr.src.rnrcore.loc;
import rnr.src.rnrorg.EventGetPointLocInfo;
import rnr.src.rnrorg.IStoreorgelement;
import rnr.src.rnrorg.IStoreorgelement.Status;
import rnr.src.rnrorg.IStoreorgelement.Type;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrorg.MissionOrganiser;
import rnr.src.rnrorg.Scorgelement;
import rnr.src.rnrorg.WarehouseOrder;
import rnr.src.rnrorg.journal;
import rnr.src.rnrorg.organaiser;
import rnr.src.rnrscenario.ScenarioFlagsManager;

//~--- JDK imports ------------------------------------------------------------

import java.text.NumberFormat;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class HeadUpDisplay implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_com.xml";
    private static final String GROUP = "HeadUp";
    private static final String[] VALUES = {
        "YOU ARE - VALUE", "TIME LEFT - VALUE", "DESTINATION", "SPEED", "GEAR", "RpM", "TIME LEFT - VALUE GRAY"
    };
    private static final int VALUE_UR = 0;
    private static final int VALUE_TIMELEFT = 1;
    private static final int VALUE_DESTINATION = 2;
    private static final int VALUE_SPEED = 3;
    private static final int VALUE_GEARTYPE = 4;
    private static final int VALUE_RPM = 5;
    private static final int VALUE_TIMELEFT_GRAY = 6;
    private static final String MACRO_TYPE = "TYPE";
    private static final String MACRO_VALUE = "VALUE";
    private static final String MACRO_VALUE1 = "VALUE1";
    private static final String[][] MACROSES = {
        { "VALUE", "VALUE1" }, null, { "VALUE" }, null, { "TYPE", "VALUE" }, { "VALUE" }, null
    };
    private static final String[] PICTURES_STATES = {
        "TRAILER ON", "TRAILER OFF", "TRAILER BLINK", "HANDBRAKE", "ENGINEBRAKE", "NO SIGNAL",
        "REAR-VIEW-MIRROR MONITOR"
    };
    private static final int TRAILER_ON = 0;
    private static final int TRAILER_OFF = 1;
    private static final int TRAILER_BLINK = 2;
    private static final int HANDBREAK = 3;
    private static final int ENGINEBRAKE = 4;
    private static final int MONITOR_OFF = 5;
    private static final int MONITOR_ON = 6;
    private static final String[] PICTURES_CONDITIONS = { "FUEL CONDITION", "TRUCK CONDITION" };
    private static final int FUEL_CONDITION = 0;
    private static final int TRUCK_CONDITION = 1;
    private static final int SAFETY_CONDITION = 0;
    private static final String[] GRAYS = { "YOU ARE - TITLE GRAY", "YOU ARE - VALUE GRAY", "TIME LEFT - TITLE GRAY",
            "TIME LEFT - VALUE GRAY" };
    private static final String[] NON_GRAYS = { "YOU ARE - TITLE", "YOU ARE - VALUE", "TIME LEFT - TITLE",
            "TIME LEFT - VALUE" };
    private static final int GRAY_UR = 0;
    private static final int GRAY_UR_VALUE = 1;
    private static final String[] ICONS = {
        "HEADUP ICON - Navigator BACK", "HEADUP ICON - Navigator LEFT", "HEADUP ICON - Navigator RIGHT",
        "HEADUP ICON - Navigator LEFT MERGE", "HEADUP ICON - Navigator RIGHT MERGE", "HEADUP ICON - Navigator STRAIGHT",
        "HEADUP ICON - IMPORTANT", "HEADUP ICON - Status URGENT", "HEADUP ICON - Status PENDING",
        "HEADUP ICON - Status EXECUTED", "HEADUP ICON - Status FAILED", "HEADUP ICON - Type DELIVERY",
        "HEADUP ICON - Type TENDER", "HEADUP ICON - Type CONTEST", "HEADUP ICON - Type DELIVERY to ADDRESS",
        "HEADUP ICON - Type DELIVERY to CLIENT", "HEADUP ICON - Type CLIENT to ADDRESS",
        "HEADUP ICON - Type BOX to CLIENT", "HEADUP ICON - Type VISIT", "HEADUP ICON - Type RACE01",
        "HEADUP ICON - Type RACE02", "HEADUP ICON - Type RACE03", "HEADUP ICON - Type RACE04",
        "HEADUP ICON - Organizer - Messages UNREAD", "HEADUP ICON - Journal - Messages UNREAD", "HeadUp - RECORD",
        "HEADUP ICON - Navigator - TEXT", "HEADUP ICON - Type RACE01 wthTrailer",
        "HEADUP ICON - Type RACE02 wthTrailer", "HEADUP ICON - Type RACE03 wthTrailer",
        "HEADUP ICON - Type RACE04 wthTrailer", "HEADUP ICON - Type TO RACE01", "HEADUP ICON - Type TO RACE02",
        "HEADUP ICON - Type TO RACE03", "HEADUP ICON - Type TO RACE04"
    };
    private static final int ICON_NAV_BACK = 0;
    private static final int ICON_NAV_LEFT = 1;
    private static final int ICON_NAV_RIGHT = 2;
    private static final int ICON_NAV_LEFTMERGE = 3;
    private static final int ICON_NAV_RIGHTMERGE = 4;
    private static final int ICON_NAV_STRAIT = 5;
    private static final int ICON_IMPORTANT = 6;
    private static final int ICON_URGENT = 7;
    private static final int ICON_PENDING = 8;
    private static final int ICON_EXECUTED = 9;
    private static final int ICON_FAILED = 10;
    private static final int ICON_TYPE_DELIVERY = 11;
    private static final int ICON_TYPE_TENDER = 12;
    private static final int ICON_TYPE_CONTEST = 13;
    private static final int ICON_TYPE_DELIVERY_to_ADDRESS = 14;
    private static final int ICON_TYPE_DELIVERY_to_CLIENT = 15;
    private static final int ICON_TYPE_CLIENT_to_ADDRESS = 16;
    private static final int ICON_TYPE_BOX_to_CLIENT = 17;
    private static final int ICON_TYPE_VISIT = 18;
    private static final int ICON_TYPE_RACE01 = 19;
    private static final int ICON_TYPE_RACE02 = 20;
    private static final int ICON_TYPE_RACE03 = 21;
    private static final int ICON_TYPE_RACE04 = 22;
    private static final int ICON_ORGUNREAD = 23;
    private static final int ICON_JOUUNREAD = 24;
    private static final int ICON_RECORDING = 25;
    private static final int ICON_NAV_TEXT = 26;
    private static final int ICON_TYPE_RACE01_SEMI = 27;
    private static final int ICON_TYPE_RACE02_SEMI = 28;
    private static final int ICON_TYPE_RACE03_SEMI = 29;
    private static final int ICON_TYPE_RACE04_SEMI = 30;
    private static final int ICON_TYPE_RACE01_ANNON = 31;
    private static final int ICON_TYPE_RACE02_ANNON = 32;
    private static final int ICON_TYPE_RACE03_ANNON = 33;
    private static final int ICON_TYPE_RACE04_ANNON = 34;
    private static final String[] PICTURES_SIZE_CONDITION = { "HEADUP ICON - Safety CONDITION" };
    private static final String[] PICTURES_SIZE_CONDITION_ADDITIONAL = { "HEADUP ICON - Safety Back" };
    private static final String[] ACC = { "ACC - PIC", "ACC - TEXT" };
    private static final int ACC_TEXT = 1;
    private static final String[] GEARS_TEXTS = { loc.getMENUString("AUTOGEAR"), loc.getMENUString("MANUALGEAR") };
    private static final String[] GEARS_VALUE = { loc.getMENUString("REVERSE"), loc.getMENUString("NEUTRAL") };
    private static final int REVERSE_GEAR = 0;
    private static final int NEUTRAL_GEAR = 1;
    private static final int REVERSE_VALUE = -1;
    private static final int NEUTRAL_VALUE = 0;
    private static final String DEBUG_GROUP = "HeadUp - DEBUG";
    private static final String[] DEBUG_ICONS = {
        "HeadUp - DEBUG - ICONS - RED", "HeadUp - DEBUG - ICONS - YELLOW", "HeadUp - DEBUG - ICONS - GREEN",
        "HeadUp - DEBUG - ICONS - BLUE", "HeadUp - DEBUG - ICONS - DARKBLUE", "HeadUp - DEBUG - ICONS - PURPLE",
        "HeadUp - DEBUG - ICONS - GRAY", "HeadUp - DEBUG - ICONS - WHITE"
    };
    private static final int DEBUG_ICON_RED = 0;
    private static final int DEBUG_ICON_YELLOW = 1;
    private static final int DEBUG_ICON_GREEN = 2;
    private static final int DEBUG_ICON_BLUE = 3;
    private static final int DEBUG_ICON_DARKBLUE = 4;
    private static final int DEBUG_ICON_PURPLE = 5;
    private static final int DEBUG_ICON_GRAY = 6;
    private static final int DEBUG_ICON_WHITE = 7;
    private static HeadUpDisplay instance = null;
    private long debug_group = 0L;
    private boolean inited = false;
    private long[] debug_icons;
    private long _menu;
    private long[] controls;
    private long[] values;
    private String[] str_values;
    private long[] pictures;
    private long[] grays;
    private long[] nongrays;
    private Condition[] conditions;
    private ConditionBySize[] condition_by_size;
    private long[] icons;
    private long[] acc;

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void restartMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @return
     */
    public static long create() {
        if (null != instance) {
            return 0L;
        }

        instance = new HeadUpDisplay();

        return menues.createSimpleMenu(instance);
    }

    static boolean show() {
        if (null == instance) {
            return false;
        }

        if ((null == instance.controls) || (instance.controls.length == 0)) {
            return false;
        }

        menues.SetShowField(menues.GetBackMenu(instance._menu), true);

        return true;
    }

    static boolean hide() {
        if (null == instance) {
            return false;
        }

        if ((null == instance.controls) || (instance.controls.length == 0)) {
            return false;
        }

        menues.SetShowField(menues.GetBackMenu(instance._menu), false);

        return true;
    }

    static void destroy() {
        if (null == instance) {
            return;
        }

        menues.CallMenuCallBack_ExitMenu(instance._menu);
        instance = null;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        this._menu = _menu;
        this.controls = menues.InitXml(_menu, "..\\data\\config\\menu\\menu_com.xml", "HeadUp");
        this.values = new long[VALUES.length];
        this.str_values = new String[VALUES.length];
        this.pictures = new long[PICTURES_STATES.length];
        this.grays = new long[GRAYS.length];
        this.nongrays = new long[NON_GRAYS.length];
        this.conditions = new Condition[PICTURES_CONDITIONS.length];
        this.condition_by_size = new ConditionBySize[PICTURES_SIZE_CONDITION.length];
        this.icons = new long[ICONS.length];
        this.acc = new long[ACC.length];
        this.debug_icons = new long[DEBUG_ICONS.length];
        this.debug_group = menues.FindFieldInMenu(_menu, "HeadUp - DEBUG");

        for (int i = 0; i < VALUES.length; ++i) {
            this.values[i] = menues.FindFieldInMenu(_menu, VALUES[i]);
            this.str_values[i] = menues.GetFieldText(this.values[i]);
        }

        for (int i = 0; i < PICTURES_STATES.length; ++i) {
            this.pictures[i] = menues.FindFieldInMenu(_menu, PICTURES_STATES[i]);
        }

        for (int i = 0; i < GRAYS.length; ++i) {
            this.grays[i] = menues.FindFieldInMenu(_menu, GRAYS[i]);
        }

        for (int i = 0; i < NON_GRAYS.length; ++i) {
            this.nongrays[i] = menues.FindFieldInMenu(_menu, NON_GRAYS[i]);
        }

        for (int i = 0; i < PICTURES_CONDITIONS.length; ++i) {
            this.conditions[i] = new Condition(menues.FindFieldInMenu(_menu, PICTURES_CONDITIONS[i]));
        }

        for (int i = 0; i < ICONS.length; ++i) {
            this.icons[i] = menues.FindFieldInMenu(_menu, ICONS[i]);
        }

        for (int i = 0; i < DEBUG_ICONS.length; ++i) {
            this.debug_icons[i] = menues.FindFieldInMenu(_menu, DEBUG_ICONS[i]);
        }

        for (int i = 0; i < ACC.length; ++i) {
            this.acc[i] = menues.FindFieldInMenu(_menu, ACC[i]);
        }

        for (int i = 0; i < PICTURES_SIZE_CONDITION.length; ++i) {
            this.condition_by_size[i] = new ConditionBySize(menues.FindFieldInMenu(_menu, PICTURES_SIZE_CONDITION[i]),
                    menues.FindFieldInMenu(_menu, PICTURES_SIZE_CONDITION_ADDITIONAL[i]));
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        this.inited = true;

        for (long item : this.controls) {
            menues.setFocusOnControl(item, false);
            menues.SetIgnoreEvents(item, true);
            menues.SetBlindess(item, true);
        }

        for (long item : this.icons) {
            menues.SetIgnoreEvents(item, true);
            menues.SetBlindess(item, true);
            menues.SetShowField(item, false);
        }

        menues.SetShowField(instance.pictures[0], false);
        menues.SetShowField(instance.pictures[2], false);
        menues.SetShowField(instance.pictures[1], true);
        menues.SetShowField(instance.pictures[3], false);
        menues.SetShowField(instance.pictures[4], false);
        menues.SetShowField(instance.pictures[5], true);
        menues.SetShowField(instance.pictures[6], false);

        if (this.debug_group != 0L) {
            menues.SetShowField(this.debug_group, false);
        }

        menues.SetIgnoreEvents(menues.GetBackMenu(instance._menu), true);
        MenuAfterInitNarrator.justShow(_menu);
        hide();
        updateUnread();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "headupMENU";
    }

    private static String getRPM(double value) {
        NumberFormat format = NumberFormat.getInstance();

        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);
        format.setMinimumIntegerDigits(1);

        return format.format(value);
    }

    private static String getGearType(int value) {
        if (value >= GEARS_TEXTS.length) {
            Log.menu("ERRORR. Wromg gear type on HUD. value = " + value);

            return "ERRORR";
        }

        return GEARS_TEXTS[value];
    }

    private static String getGear(int value) {
        switch (value) {
         case -1 :
             return GEARS_VALUE[0];

         case 0 :
             return GEARS_VALUE[1];
        }

        return "" + value;
    }

    /**
     * Method description
     *
     *
     * @param debug_values
     * @param bShow
     */
    public static void update_debug(int debug_values, boolean bShow) {
        if ((null == instance) || (!(instance.inited)) || (instance.debug_group == 0L)) {
            return;
        }

        if (bShow) {
            if (instance.debug_icons[0] != 0L) {
                if ((debug_values & 0x1) != 0) {
                    menues.SetShowField(instance.debug_icons[0], true);
                } else {
                    menues.SetShowField(instance.debug_icons[0], false);
                }
            }

            if (instance.debug_icons[1] != 0L) {
                if ((debug_values & 0x2) != 0) {
                    menues.SetShowField(instance.debug_icons[1], true);
                } else {
                    menues.SetShowField(instance.debug_icons[1], false);
                }
            }

            if (instance.debug_icons[2] != 0L) {
                if ((ScenarioFlagsManager.getInstance() != null)
                        && (ScenarioFlagsManager.getValue("Dorothy_is_available"))) {
                    menues.SetShowField(instance.debug_icons[2], true);
                } else {
                    menues.SetShowField(instance.debug_icons[2], false);
                }
            }

            if (instance.debug_icons[3] != 0L) {
                if ((ScenarioFlagsManager.getInstance() != null)
                        && (!(ScenarioFlagsManager.getValue("MissionsEnebledByScenario")))) {
                    menues.SetShowField(instance.debug_icons[3], true);
                } else {
                    menues.SetShowField(instance.debug_icons[3], false);
                }
            }

            if (instance.debug_icons[4] != 0L) {
                menues.SetShowField(instance.debug_icons[4], false);
            }

            if (instance.debug_icons[5] != 0L) {
                if ((debug_values & 0x20) != 0) {
                    menues.SetShowField(instance.debug_icons[5], true);
                } else {
                    menues.SetShowField(instance.debug_icons[5], false);
                }
            }

            if (instance.debug_icons[6] != 0L) {
                if ((debug_values & 0x40) != 0) {
                    menues.SetShowField(instance.debug_icons[6], true);
                } else {
                    menues.SetShowField(instance.debug_icons[6], false);
                }
            }

            if (instance.debug_icons[7] != 0L) {
                if ((debug_values & 0x80) != 0) {
                    menues.SetShowField(instance.debug_icons[7], true);
                } else {
                    menues.SetShowField(instance.debug_icons[7], false);
                }
            }

            menues.SetShowField(instance.debug_group, true);
        } else {
            menues.SetShowField(instance.debug_group, false);
        }
    }

    /**
     * Method description
     *
     *
     * @param u_r
     * @param u_r_from
     * @param month
     * @param day
     * @param year
     * @param speed
     * @param gear
     * @param geartype
     * @param rpm
     */
    public static void update(int u_r, int u_r_from, int month, int day, int year, int speed, int gear, int geartype,
                              double rpm) {
        if ((null == instance) || (!(instance.inited))) {
            return;
        }

        ++u_r;
        ++u_r_from;

        IStoreorgelement current_mission = organaiser.getCurrent();

        if (current_mission != null) {
            String mission_name = MissionOrganiser.getInstance().getMissionForOrganiser(current_mission.getName());

            if ((null != mission_name) && (current_mission instanceof Scorgelement)) {
                MissionEventsMaker.updateOrganiserSligtly(mission_name, (Scorgelement) current_mission);
            } else if (current_mission instanceof WarehouseOrder) {
                MissionEventsMaker.updateOrganiser(mission_name, (WarehouseOrder) current_mission);
            }
        }

        for (int i = 0; i < instance.values.length; ++i) {
            KeyPair[] keys = null;

            if (null != MACROSES[i]) {
                keys = new KeyPair[MACROSES[i].length];
            }

            String value = "";

            switch (i) {
             case 0 :
                 keys[0] = new KeyPair(MACROSES[i][0], "" + u_r);
                 keys[1] = new KeyPair(MACROSES[i][1], "" + u_r_from);
                 value = instance.str_values[i];

                 break;

             case 1 :
                 if ((null != current_mission) && (current_mission.get_minutes_toFail() >= 0)
                         && (current_mission.get_seconds_toFail() >= 0)) {
                     value = Converts.ConverTime3Plus2(instance.str_values[i], current_mission.get_minutes_toFail(),
                                                       current_mission.get_seconds_toFail());
                 } else {
                     value = Converts.ConverTime3Plus2(instance.str_values[i], 0, 0);
                 }

                 break;

             case 2 :
                 if ((null != current_mission)
                         && (((current_mission.getMissionState() == 0) || (current_mission.getMissionState() == 1)))) {
                     String destination;

                     if (current_mission.getMissionState() == 0) {
                         EventGetPointLocInfo info =
                             MissionEventsMaker.getLocalisationMissionPointInfo(current_mission.loadPoint());

                         destination = info.short_name;
                     } else {
                         EventGetPointLocInfo info =
                             MissionEventsMaker.getLocalisationMissionPointInfo(current_mission.endPoint());

                         destination = info.short_name;
                     }

                     keys[0] = new KeyPair(MACROSES[i][0], destination);
                 } else {
                     keys[0] = new KeyPair(MACROSES[i][0], "");
                 }

                 value = instance.str_values[i];

                 break;

             case 3 :
                 value = "" + Math.abs(speed);

                 break;

             case 4 :
                 keys[0] = new KeyPair(MACROSES[i][0], getGearType(geartype));
                 keys[1] = new KeyPair(MACROSES[i][1], getGear(gear));
                 value = instance.str_values[i];

                 break;

             case 5 :
                 keys[0] = new KeyPair(MACROSES[i][0], getRPM(rpm));
                 value = instance.str_values[i];

                 break;

             case 6 :
                 value = Converts.ConverTime3Plus2(instance.str_values[i], 0, 0);
            }

            if (null != keys) {
                value = MacroKit.Parse(value, keys);
            }

            menues.SetFieldText(instance.values[i], value);
            menues.UpdateMenuField(menues.ConvertMenuFields(instance.values[i]));
        }
    }

    /**
     * Method description
     *
     *
     * @param has_semi
     * @param onhandbreak
     * @param state_fuel
     * @param state_truck
     * @param lost_semi
     * @param onenginebreak
     * @param state_condition
     * @param show_condition
     * @param monitor_installed
     */
    public static void update(boolean has_semi, boolean onhandbreak, double state_fuel, double state_truck,
                              boolean lost_semi, boolean onenginebreak, double state_condition, boolean show_condition,
                              boolean monitor_installed) {
        if ((null == instance) || (!(instance.inited))) {
            return;
        }

        if (lost_semi) {
            menues.SetShowField(instance.pictures[0], false);
            menues.SetShowField(instance.pictures[1], false);
            menues.SetShowField(instance.pictures[2], true);
        } else {
            menues.SetShowField(instance.pictures[0], has_semi);
            menues.SetShowField(instance.pictures[1], !(has_semi));
            menues.SetShowField(instance.pictures[2], false);
        }

        menues.SetShowField(instance.pictures[3], onhandbreak);
        menues.SetShowField(instance.pictures[4], onenginebreak);
        menues.SetShowField(instance.pictures[5], !(monitor_installed));
        menues.SetShowField(instance.pictures[6], monitor_installed);
        instance.conditions[0].setCondition(state_fuel);
        instance.conditions[1].setCondition(state_truck);
        instance.condition_by_size[0].setCondition(state_condition);
        instance.condition_by_size[0].setShowCondition(show_condition);
    }

    /**
     * Method description
     *
     *
     * @param inRace
     */
    public static void update(boolean inRace) {
        if ((null == instance) || (!(instance.inited))) {
            return;
        }

        for (long item : instance.grays) {
            menues.SetShowField(item, !(inRace));
        }

        for (long item : instance.nongrays) {
            menues.SetShowField(item, inRace);
        }

        if (!(inRace)) {
            menues.SetFieldText(instance.values[2], "");
            menues.UpdateMenuField(menues.ConvertMenuFields(instance.values[2]));
        }

        IStoreorgelement elem = organaiser.getCurrent();

        if (elem != null) {
            if ((0 > instance.grays.length) || (0 > instance.nongrays.length)) {
                Log.menu(
                    "ERRORR. HeadUpDisplay. Update inRace. You are HACK. GRAY_UR=0 is more that any of given arrays. grays.length="
                    + instance.grays.length + " nongrays.length=" + instance.nongrays.length);
            }

            if ((1 > instance.grays.length) || (1 > instance.nongrays.length)) {
                Log.menu(
                    "ERRORR. HeadUpDisplay. Update inRace. You are HACK. GRAY_UR_VALUE=1 is more that any of given arrays. grays.length="
                    + instance.grays.length + " nongrays.length=" + instance.nongrays.length);
            }

            boolean toShowURField = elem instanceof WarehouseOrder;

            menues.SetShowField(instance.grays[0], !(toShowURField));
            menues.SetShowField(instance.nongrays[0], toShowURField);
            menues.SetShowField(instance.grays[1], !(toShowURField));
            menues.SetShowField(instance.nongrays[1], toShowURField);
        }
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void updateRecord(boolean value) {
        if ((null == instance) || (!(instance.inited))) {
            return;
        }

        menues.SetShowField(instance.icons[25], value);
    }

    /**
     * Method description
     *
     */
    public static void updateDelivery() {
        if ((null == instance) || (!(instance.inited))) {
            return;
        }

        for (int i = 0; i < instance.icons.length; ++i) {
            switch (i) {
             case 6 :
             case 7 :
             case 8 :
             case 9 :
             case 10 :
             case 11 :
             case 12 :
             case 13 :
             case 14 :
             case 15 :
             case 16 :
             case 17 :
             case 18 :
             case 19 :
             case 20 :
             case 21 :
             case 22 :
             case 27 :
             case 28 :
             case 29 :
             case 30 :
             case 31 :
             case 32 :
             case 33 :
             case 34 :
                 menues.SetShowField(instance.icons[i], false);
             case 23 :
             case 24 :
             case 25 :
             case 26 :
            }
        }

        IStoreorgelement elem = organaiser.getCurrent();

        if (elem == null) {
            return;
        }

        IStoreorgelement.Status status = elem.getStatus();
        IStoreorgelement.Type type = elem.getType();

        if (status == IStoreorgelement.Status.executedMission) {
            menues.SetShowField(instance.icons[9], true);
        } else if (status == IStoreorgelement.Status.failedMission) {
            menues.SetShowField(instance.icons[10], true);
        } else if (status == IStoreorgelement.Status.urgentMission) {
            menues.SetShowField(instance.icons[7], true);
        } else if (status == IStoreorgelement.Status.pendingMission) {
            menues.SetShowField(instance.icons[8], true);
        }

        if (type == IStoreorgelement.Type.baseDelivery) {
            menues.SetShowField(instance.icons[11], true);
        } else if (type == IStoreorgelement.Type.tender) {
            menues.SetShowField(instance.icons[12], true);
        } else if (type == IStoreorgelement.Type.competition) {
            menues.SetShowField(instance.icons[13], true);
        } else if (type == IStoreorgelement.Type.trailerObjectDelivery) {
            menues.SetShowField(instance.icons[14], true);
        } else if (type == IStoreorgelement.Type.trailerClientDelivery) {
            menues.SetShowField(instance.icons[15], true);
        } else if (type == IStoreorgelement.Type.passangerDelivery) {
            menues.SetShowField(instance.icons[16], true);
        } else if (type == IStoreorgelement.Type.pakageDelivery) {
            menues.SetShowField(instance.icons[17], true);
        } else if (type == IStoreorgelement.Type.visit) {
            menues.SetShowField(instance.icons[18], true);
        } else if (type == IStoreorgelement.Type.bigrace1) {
            menues.SetShowField(instance.icons[22], true);
        } else if (type == IStoreorgelement.Type.bigrace2) {
            menues.SetShowField(instance.icons[21], true);
        } else if (type == IStoreorgelement.Type.bigrace3) {
            menues.SetShowField(instance.icons[20], true);
        } else if (type == IStoreorgelement.Type.bigrace4) {
            menues.SetShowField(instance.icons[19], true);
        } else if (type == IStoreorgelement.Type.bigrace1_semi) {
            menues.SetShowField(instance.icons[30], true);
        } else if (type == IStoreorgelement.Type.bigrace2_semi) {
            menues.SetShowField(instance.icons[29], true);
        } else if (type == IStoreorgelement.Type.bigrace3_semi) {
            menues.SetShowField(instance.icons[28], true);
        } else if (type == IStoreorgelement.Type.bigrace4_semi) {
            menues.SetShowField(instance.icons[27], true);
        } else if (type == IStoreorgelement.Type.bigrace1_announce) {
            menues.SetShowField(instance.icons[34], true);
        } else if (type == IStoreorgelement.Type.bigrace2_announce) {
            menues.SetShowField(instance.icons[33], true);
        } else if (type == IStoreorgelement.Type.bigrace3_announce) {
            menues.SetShowField(instance.icons[32], true);
        } else if (type == IStoreorgelement.Type.bigrace4_announce) {
            menues.SetShowField(instance.icons[31], true);
        }
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param text
     */
    public static void updateNavigator(int type, String text) {
        if ((null == instance) || (!(instance.inited))) {
            return;
        }

        for (int i = 0; i < instance.icons.length; ++i) {
            switch (i) {
             case 0 :
             case 1 :
             case 2 :
             case 3 :
             case 4 :
             case 5 :
             case 26 :
                 menues.SetShowField(instance.icons[i], false);
            }
        }

        int to_turn_on = -1;

        switch (type) {
         case -1 :
             break;

         case 0 :
             to_turn_on = 0;

             break;

         case 1 :
             to_turn_on = 1;

             break;

         case 2 :
             to_turn_on = 2;

             break;

         case 3 :
             to_turn_on = 3;

             break;

         case 4 :
             to_turn_on = 4;

             break;

         case 5 :
             to_turn_on = 5;
        }

        if (-1 != to_turn_on) {
            menues.SetShowField(instance.icons[to_turn_on], true);
            menues.SetShowField(instance.icons[26], true);
            menues.SetFieldText(instance.icons[26], text);
            menues.UpdateMenuField(menues.ConvertMenuFields(instance.icons[26]));
        }
    }

    /**
     * Method description
     *
     *
     * @param is_ob
     * @param velocity
     */
    public static void updateACC(boolean is_ob, int velocity) {
        if ((null == instance) || (!(instance.inited))) {
            return;
        }

        if (!(is_ob)) {
            for (long field : instance.acc) {
                menues.SetShowField(field, false);
            }
        } else {
            for (long field : instance.acc) {
                menues.SetShowField(field, true);
            }

            menues.SetFieldText(instance.acc[1], "" + velocity);
            menues.UpdateMenuField(menues.ConvertMenuFields(instance.acc[1]));
        }
    }

    /**
     * Method description
     *
     */
    public static void updateUnread() {
        if ((null == instance) || (!(instance.inited))) {
            return;
        }

        menues.SetShowField(instance.icons[23], organaiser.getInstance().hasUnread());
        menues.SetShowField(instance.icons[24], journal.getInstance().hasUnread());
    }

    /**
     * @return the xml
     */
    public static String getXml() {
        return XML;
    }

    /**
     * @return the group
     */
    public static String getGroup() {
        return GROUP;
    }

    /**
     * @return the valueUr
     */
    public static int getValueUr() {
        return VALUE_UR;
    }

    /**
     * @return the valueTimeleft
     */
    public static int getValueTimeleft() {
        return VALUE_TIMELEFT;
    }

    /**
     * @return the valueDestination
     */
    public static int getValueDestination() {
        return VALUE_DESTINATION;
    }

    /**
     * @return the valueSpeed
     */
    public static int getValueSpeed() {
        return VALUE_SPEED;
    }

    /**
     * @return the valueGeartype
     */
    public static int getValueGeartype() {
        return VALUE_GEARTYPE;
    }

    /**
     * @return the valueRpm
     */
    public static int getValueRpm() {
        return VALUE_RPM;
    }

    /**
     * @return the valueTimeleftGray
     */
    public static int getValueTimeleftGray() {
        return VALUE_TIMELEFT_GRAY;
    }

    /**
     * @return the macroType
     */
    public static String getMacroType() {
        return MACRO_TYPE;
    }

    /**
     * @return the macroValue
     */
    public static String getMacroValue() {
        return MACRO_VALUE;
    }

    /**
     * @return the macroValue1
     */
    public static String getMacroValue1() {
        return MACRO_VALUE1;
    }

    static class Condition {
        private final int posy;
        private final int sizey;
        private final long control;

        Condition(long control) {
            this.control = control;

            MENUText_field field = menues.ConvertTextFields(control);

            this.posy = field.poy;
            this.sizey = field.leny;
        }

        void setCondition(double value) {
            int size_left = (int) (this.sizey * value);
            int pos_new = this.posy + this.sizey - size_left;
            MENUText_field field = menues.ConvertTextFields(this.control);

            field.poy = pos_new;
            field.leny = size_left;
            menues.UpdateField(field);
        }
    }


    static class ConditionBySize {
        private final int sizey;
        private final long control;
        private final long back;

        ConditionBySize(long control, long back) {
            this.control = control;
            this.back = back;

            MENUText_field field = menues.ConvertTextFields(control);

            this.sizey = field.leny;
        }

        void setCondition(double value) {
            int size = (int) (this.sizey * (1.0D - value));
            MENUText_field field = menues.ConvertTextFields(this.control);

            field.leny = size;
            menues.UpdateField(field);
        }

        void setShowCondition(boolean bShow) {
            menues.SetShowField(this.control, bShow);
            menues.SetShowField(this.back, bShow);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
