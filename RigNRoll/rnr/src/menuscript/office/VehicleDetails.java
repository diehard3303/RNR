/*
 * @(#)VehicleDetails.java   13/08/26
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

import rnr.src.gameobj.CarInfo;
import rnr.src.menu.Common;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUTruckview;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.TextScroller;
import rnr.src.menu.TruckView;
import rnr.src.menu.menues;
import rnr.src.menuscript.Converts;
import rnr.src.menuscript.VehicleLoader;
import rnr.src.rnrcore.Log;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class VehicleDetails {
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String GROUP = "MF - Specifications - SHOW - Vehicle Details";
    private static final String TRUCK_VIEW = "Vehicle Details - Truckview";
    private static final String CONTROL_GROUP_INTERIOR = "Interior LEGENDS - Mouse";
    private static final String CONTROL_GROUP_EXTERIOR = "Exterior LEGENDS - Mouse";
    private static final String[] DATA_FIELDS = {
        "Vehicle Details VALUE - License Plate", "Vehicle Details VALUE - Type", "Vehicle Details VALUE - Condition",
        "Vehicle Details VALUE - Wear", "Vehicle Details VALUE - Mileage", "Vehicle Details VALUE - Speed",
        "Vehicle Details VALUE - Load Safety", "Vehicle Details VALUE - Suspension",
        "Vehicle Details VALUE - Horse Power", "Vehicle Details VALUE - Color"
    };
    private static final String UPGRADES_TEXTFIELD = "Vehicle Details VALUE - Upgrades";
    private static final String UPGRADES_RANGER = "Vehicle Details - Upgrades - Tableranger";
    private static final int LICENCE = 0;
    private static final int TYPE = 1;
    private static final int CONDITION = 2;
    private static final int WEAR = 3;
    private static final int MILIAGE = 4;
    private static final int SPEED = 5;
    private static final int LOADSAFTY = 6;
    private static final int SUSPESION = 7;
    private static final int HORSE = 8;
    private static final int COLOR = 9;
    private static final String[] HEAD = { "Vehicle Details - BOTTOM TITLE", "Vehicle Details - TOP TITLE" };
    private static final String MACRO_MODEL = "MODEL";
    private static final String BRIEF = "Vehicle Details - BRIEFLY - TITLE";
    private static final String[] ACTIONS = { "BUTTON - Vehicle Details - OK", "BUTTON - Vehicle Details - EXTERIOR",
            "BUTTON - Vehicle Details - INTERIOR" };
    private static final String[] METHODS = { "onOk", "onExterior", "onInterior" };
    TextScroller scroller = null;
    private long id_upgrade_text = 0L;
    private long id_upgrade_scroller = 0L;
    private final long[] data_fields = new long[DATA_FIELDS.length];
    private final String[] data_fields_strings = new String[DATA_FIELDS.length];
    private final ArrayList<IVehicleDetailesListener> listeners = new ArrayList();
    private TruckView view = null;
    private CarInfo current = null;
    private ManageFlitManager.VehicleId idcurrent = null;
    private boolean isVehicleOur = false;
    Common common;
    private long[] controls;
    private String[] valuesHEAD;
    private long[] controlsHEAD;
    private String valueBRIEF;
    private long controlBRIEF;
    private long interiorControls;
    private long exteriorControls;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     */
    public VehicleDetails(long _menu) {
        this.controls = menues.InitXml(_menu, "..\\data\\config\\menu\\menu_office.xml",
                                       "MF - Specifications - SHOW - Vehicle Details");

        if (this.controls == null) {
            Log.menu(
                "ERRORR. VehicleDetails. Xml ..\\data\\config\\menu\\menu_office.xml does not contain control group MF - Specifications - SHOW - Vehicle Details");

            return;
        }

        if (this.controls.length == 0) {
            Log.menu(
                "ERRORR. VehicleDetails. In xml ..\\data\\config\\menu\\menu_office.xml control group MF - Specifications - SHOW - Vehicle Details contains no elements.");

            return;
        }

        for (int i = 0; i < ACTIONS.length; ++i) {
            long cont = menues.FindFieldInMenu(_menu, ACTIONS[i]);

            if (0L == cont) {
                Log.menu(
                    "ERRORR. VehicleDetails. In xml ..\\data\\config\\menu\\menu_office.xml control group MF - Specifications - SHOW - Vehicle Details contains no button "
                    + ACTIONS[i]);

                return;
            }

            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(cont), this, METHODS[i], 4L);
        }

        for (int i = 0; i < DATA_FIELDS.length; ++i) {
            this.data_fields[i] = menues.FindFieldInMenu(_menu, DATA_FIELDS[i]);
        }

        long truckView = menues.FindFieldInMenu(_menu, "Vehicle Details - Truckview");

        if (0L == truckView) {
            Log.menu(
                "ERRORR. VehicleDetails. In xml ..\\data\\config\\menu\\menu_office.xml control group MF - Specifications - SHOW - Vehicle Details contains no truck view Vehicle Details - Truckview");

            return;
        }

        this.view = new TruckView((MENUTruckview) menues.ConvertMenuFields(truckView));
        this.valuesHEAD = new String[HEAD.length];
        this.controlsHEAD = new long[HEAD.length];

        for (int i = 0; i < HEAD.length; ++i) {
            this.controlsHEAD[i] = menues.FindFieldInMenu(_menu, HEAD[i]);

            if (0L == this.controlsHEAD[i]) {
                Log.menu(
                    "ERRORR. VehicleDetails. In xml ..\\data\\config\\menu\\menu_office.xml control group MF - Specifications - SHOW - Vehicle Details contains no HEAD control "
                    + HEAD[i]);
            } else {
                this.valuesHEAD[i] = menues.GetFieldText(this.controlsHEAD[i]);
            }
        }

        this.controlBRIEF = menues.FindFieldInMenu(_menu, "Vehicle Details - BRIEFLY - TITLE");

        if (0L == this.controlBRIEF) {
            Log.menu(
                "ERRORR. VehicleDetails. In xml ..\\data\\config\\menu\\menu_office.xml control group MF - Specifications - SHOW - Vehicle Details contains no BRIEF control Vehicle Details - BRIEFLY - TITLE");
        } else {
            this.valueBRIEF = menues.GetFieldText(this.controlBRIEF);
        }

        this.id_upgrade_text = menues.FindFieldInMenu(_menu, "Vehicle Details VALUE - Upgrades");
        this.id_upgrade_scroller = menues.FindFieldInMenu(_menu, "Vehicle Details - Upgrades - Tableranger");
        this.interiorControls = menues.FindFieldInMenu(_menu, "Interior LEGENDS - Mouse");
        this.exteriorControls = menues.FindFieldInMenu(_menu, "Exterior LEGENDS - Mouse");
        this.common = new Common(_menu);
    }

    /**
     * Method description
     *
     */
    public void afterInit() {
        for (int i = 0; i < DATA_FIELDS.length; ++i) {
            if (this.data_fields[i] != 0L) {
                this.data_fields_strings[i] = menues.GetFieldText(this.data_fields[i]);
            } else {
                this.data_fields_strings[i] = null;
            }
        }
    }

    /**
     * Method description
     *
     */
    public void DeInit() {
        if (this.scroller != null) {
            this.scroller.Deinit();
        }
    }

    /**
     * Method description
     *
     *
     * @param info
     * @param id
     * @param owrvehicle
     */
    public void show(CarInfo info, ManageFlitManager.VehicleId id, boolean owrvehicle) {
        this.current = info;
        this.idcurrent = id;
        this.isVehicleOur = owrvehicle;
        VehicleLoader.BindExterior(this.view, this.current);

        if (this.interiorControls != 0L) {
            menues.SetShowField(this.interiorControls, false);
        }

        if (this.exteriorControls != 0L) {
            menues.SetShowField(this.exteriorControls, true);
        }

        menues.SetShowField(this.controls[0], true);
        update();
    }

    /**
     * Method description
     *
     */
    public void hide() {
        menues.SetShowField(this.controls[0], false);

        for (IVehicleDetailesListener item : this.listeners) {
            item.onClose();
        }
    }

    private void update() {
        ManageFlitManager.FullMyVehicleInfo info1 = null;
        ManageFlitManager.FullDealerVehicleInfo info2 = null;

        if (this.isVehicleOur) {
            info1 = ManageFlitManager.getManageFlitManager().GetMyVehiclesInfoWithUpgrades(this.idcurrent);
        } else {
            info2 = ManageFlitManager.getManageFlitManager().GetDealerVehiclesInfoWithUpgrades(this.idcurrent);
        }

        KeyPair[] keys = new KeyPair[1];

        for (int i = 0; i < this.data_fields.length; ++i) {
            switch (i) {
             case 0 :
                 if (null != info1) {
                     menues.SetFieldText(this.data_fields[i], info1.license_plate);
                 } else if (null != info2) {
                     menues.SetFieldText(this.data_fields[i], "-");
                 }

                 break;

             case 1 :
                 if (null != info1) {
                     menues.SetFieldText(this.data_fields[i], info1.type);
                 } else if (null != info2) {
                     menues.SetFieldText(this.data_fields[i], info2.type);
                 }

                 break;

             case 2 :
                 if (null != info1) {
                     keys[0] = new KeyPair("VALUE", "" + (int) (100.0D * info1.condition));
                 } else if (null != info2) {
                     keys[0] = new KeyPair("VALUE", "" + (int) (100.0D * info2.condition));
                 }

                 menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));

                 break;

             case 3 :
                 if (null != info1) {
                     keys[0] = new KeyPair("VALUE", "" + (int) (100.0D * info1.wear));
                     menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));
                 } else if (null != info2) {
                     menues.SetFieldText(this.data_fields[i], "-");
                 }

                 break;

             case 4 :
                 if (null != info1) {
                     keys[0] = new KeyPair("VALUE", "" + (int) info1.mileage);
                 } else if (null != info2) {
                     keys[0] = new KeyPair("VALUE", "" + (int) info2.mileage);
                 }

                 menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));

                 break;

             case 5 :
                 if (null != info1) {
                     keys[0] = new KeyPair("VALUE", "" + (int) (100.0D * info1.speed));
                     menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));
                 } else if (null != info2) {
                     menues.SetFieldText(this.data_fields[i], "-");
                 }

                 break;

             case 6 :
                 if (null != info1) {
                     keys[0] = new KeyPair("VALUE", "" + (int) (100.0D * info1.load_safety));
                     menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));
                 } else if (null != info2) {
                     menues.SetFieldText(this.data_fields[i], "-");
                 }

                 break;

             case 7 :
                 if (null != info1) {
                     menues.SetFieldText(this.data_fields[i], info1.suspension);
                 } else if (null != info2) {
                     menues.SetFieldText(this.data_fields[i], info2.suspension);
                 }

                 break;

             case 8 :
                 if (null != info1) {
                     keys[0] = new KeyPair("VALUE", "" + (int) info1.horse_power);
                 } else if (null != info2) {
                     keys[0] = new KeyPair("VALUE", "" + (int) info2.horsepower);
                 }

                 menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_fields_strings[i], keys));

                 break;

             case 9 :
                 if (null != info1) {
                     menues.SetFieldText(this.data_fields[i], info1.color);
                 } else if (null != info2) {
                     menues.SetFieldText(this.data_fields[i], info2.color);
                 }
            }

            menues.UpdateMenuField(menues.ConvertMenuFields(this.data_fields[i]));
        }

        MENU_ranger ranger = (MENU_ranger) menues.ConvertMenuFields(this.id_upgrade_scroller);
        MENUText_field text = (MENUText_field) menues.ConvertMenuFields(this.id_upgrade_text);

        if ((ranger != null) && (text != null)) {
            if (this.id_upgrade_text != 0L) {
                menues.SetShowField(this.id_upgrade_text, true);
            }

            String upgrade_text = "NONE";

            if (null != info1) {
                upgrade_text = info1.upgrades_info_string;
            } else if (null != info2) {
                upgrade_text = info2.upgrades_info_string;
            }

            text.text = upgrade_text;
            menues.UpdateField(text);

            int texh = menues.GetTextLineHeight(text.nativePointer);
            int startbase = menues.GetBaseLine(text.nativePointer);
            int linescreen = Converts.HeightToLines(text.leny, startbase, texh);
            int linecounter = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, upgrade_text), startbase,
                                  texh);

            if (this.scroller != null) {
                this.scroller.Deinit();
            }

            this.scroller = new TextScroller(this.common, ranger, linecounter, linescreen, texh, startbase, false,
                                             "Vehicle Details VALUE - Upgrades");
            this.scroller.AddTextControl(text);
        }

        String modelname = (null != info2)
                           ? info2.vehicle_name
                           : (null != info1)
                             ? info1.vehicle_name
                             : "";

        for (int i = 0; i < this.controlsHEAD.length; ++i) {
            KeyPair[] keyss = { new KeyPair("MODEL", modelname) };
            String res = MacroKit.Parse(this.valuesHEAD[i], keyss);

            menues.SetFieldText(this.controlsHEAD[i], res.toUpperCase());
            menues.UpdateMenuField(menues.ConvertMenuFields(this.controlsHEAD[i]));
        }

        KeyPair[] keys1 = { new KeyPair("VEHICLE_MODEL", modelname) };

        menues.SetFieldText(this.controlBRIEF, MacroKit.Parse(this.valueBRIEF, keys1));
        menues.UpdateMenuField(menues.ConvertMenuFields(this.controlBRIEF));
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onOk(long _menu, MENUsimplebutton_field button) {
        VehicleLoader.Unload();
        hide();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onExterior(long _menu, MENUsimplebutton_field button) {
        VehicleLoader.BindExterior(this.view, this.current);

        if (this.interiorControls != 0L) {
            menues.SetShowField(this.interiorControls, false);
        }

        if (this.exteriorControls != 0L) {
            menues.SetShowField(this.exteriorControls, true);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onInterior(long _menu, MENUsimplebutton_field button) {
        VehicleLoader.BindInterior(this.view, this.current);

        if (this.interiorControls != 0L) {
            menues.SetShowField(this.interiorControls, true);
        }

        if (this.exteriorControls != 0L) {
            menues.SetShowField(this.exteriorControls, false);
        }
    }

    /**
     * Method description
     *
     *
     * @param lst
     */
    public void addListener(IVehicleDetailesListener lst) {
        this.listeners.add(lst);
    }
}


//~ Formatted in DD Std on 13/08/26
