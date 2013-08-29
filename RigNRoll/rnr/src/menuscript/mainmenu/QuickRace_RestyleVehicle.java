/*
 * @(#)QuickRace_RestyleVehicle.java   13/08/26
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

import rnr.src.menu.ComboBox;
import rnr.src.menu.Common;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.ListenerManager;
import rnr.src.menu.MENUTruckview;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MenuControls;
import rnr.src.menu.SelectCb;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class QuickRace_RestyleVehicle implements SelectCb, menucreation {
    private static final String XML_NAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String CONTROL_GROUP = "QUICK RACE - NEW GAME - SELECT VEHICLE - RESTYLE VEHICLE";
    private static final String VEHICLE_NAME = "Model - VALUE";
    private static final String VEHICLE_MANUFACTURER = "Make - VALUE";
    private static final String VEHICLE_TYPE = "Vehicle type - VALUE";
    private static final String[] BUTTONS = { "QUICK RACE - NEW GAME - SELECT VEHICLE - RESTYLE VEHICLE OK",
            "QUICK RACE - NEW GAME - SELECT VEHICLE - RESTYLE VEHICLE DEFAULT",
            "QUICK RACE - NEW GAME - SELECT VEHICLE - RESTYLE VEHICLE Exit" };
    private static final String[] METHODS = { "OnOk", "OnDefault", "OnCancel" };
    private static final String METALLICBUTTON = "METALLIC - Check BOX";
    private static final String CHAMELIONBUTTON = "CHAMELEON - Check BOX";
    private static final String CHAMELION_METALLIC_METHOD = "onColorChange";
    static final int BODY_ID = 0;
    static final int LEATHER_ID = 1;
    static final int DASH_ID = 2;
    static final int CLOTH_ID = 3;
    static final int GAUGES_ID = 4;
    static final int GLASSES_ID = 5;
    static final int MAX_ID = 6;
    static final int METALLIC_ID = 10;
    static String[] s_xmltypenames = {
        "MainColor", "Leather", "Dash", "Cloth", "Gauges", "Glasses"
    };
    static String[] s_xmltypetitle = {
        "MAIN", "LEATHER", "DASH", "CLOTH", "GAUGES", "GLASSES"
    };
    static String[] s_mattypenames = {
        "color", "leather", "dash", "cloth", "gauges", "glass"
    };
    static int[] s_matnum = {
        6, 8, 6, 16, 8, 16
    };
    static int[] s_mattexturesize = {
        256, 256, 512, 512, 512, 512
    };
    static int[] s_numpos = {
        6, 8, 6, 16, 9, 16
    };
    MENUTruckview view_ext = null;
    MENUTruckview view_int = null;
    ComboBox[] m_combobox = new ComboBox[6];
    private long control_metallic = 0L;
    private long control_chamelion = 0L;
    VehicleState vehicle_state = new VehicleState();
    PanelDialog parent = null;

    /** Field description */
    public Common common;
    MenuControls controls;

    /**
     * Constructs ...
     *
     *
     * @param _num_tech
     * @param _parent
     */
    public QuickRace_RestyleVehicle(int _num_tech, PanelDialog _parent) {
        this.vehicle_state.iTechNumber = _num_tech;
        this.parent = _parent;
        this.parent.setShow(false);
    }

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
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        this.controls = new MenuControls(_menu, "..\\data\\config\\menu\\menu_MAIN.xml",
                                         "QUICK RACE - NEW GAME - SELECT VEHICLE - RESTYLE VEHICLE");
        _initMenu(_menu);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    public void _initMenu(long _menu) {
        this.common = new Common(_menu);
        JavaEvents.SendEvent(65, 14, this.vehicle_state);
        this.control_chamelion = menues.FindFieldInMenu(_menu, "CHAMELEON - Check BOX");
        this.control_metallic = menues.FindFieldInMenu(_menu, "METALLIC - Check BOX");

        for (int i = 0; i < this.m_combobox.length; ++i) {
            int numitems = s_matnum[i];
            String intext = ((i == 0) || (i == 5))
                            ? "Exterior"
                            : "Interior";

            this.m_combobox[i] = new ComboBox(this.common, "..\\data\\config\\menu\\menu_MAIN.xml",
                                              "MenuMain RestyleVEHICLE - " + intext + " - " + s_xmltypenames[i],
                                              s_xmltypenames[i] + "00",
                                              "Border - " + s_xmltypenames[i] + " - JustForTest 8 positions",
                                              this.common.FindRadioButton("Restyle - " + s_xmltypenames[i]),
                                              this.common.FindSimpleButton(s_xmltypetitle[i] + " - PopUP button"),
                                              numitems, 38, 2, 3);
            this.m_combobox[i].AddListener(this);
            this.m_combobox[i].SetMaterial("mat_" + this.vehicle_state.selction_name + "_" + s_mattypenames[i]
                                           + "_110");
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    public void InitRestyle(long _menu) {
        for (int type = 0; type < 6; ++type) {
            int state = 0;

            switch (type) {
             case 0 :
                 state = s_matnum[0] - 1 - this.vehicle_state.iMainColor;

                 break;

             case 1 :
                 state = this.vehicle_state.iLeather;

                 break;

             case 3 :
                 state = this.vehicle_state.iCloth;

                 break;

             case 2 :
                 state = this.vehicle_state.iDash;

                 break;

             case 4 :
                 state = this.vehicle_state.iDashGauges;

                 break;

             case 5 :
                 state = this.vehicle_state.iGlass;
            }

            menues.SetFieldState(this.common.FindRadioButton("Restyle - " + s_xmltypenames[type]).nativePointer, state);

            int num = s_matnum[type];
            int[] temp = new int[num];

            for (int i = 0; i < num; ++i) {
                temp[i] = i;
            }

            this.m_combobox[type].FillMappingData(1.0F / num, temp);
        }
    }

    private void reciveMetallicChamelionFlags() {
        menues.SetFieldState(this.control_metallic, (this.vehicle_state.isMetallic)
                ? 1
                : 0);
        menues.SetFieldState(this.control_chamelion, (this.vehicle_state.isHamelion)
                ? 1
                : 0);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        ListenerManager.TriggerEvent(104);
        InitRestyle(this.common.GetMenu());
        reciveMetallicChamelionFlags();
        this.view_ext = this.common.FindTruckView("Exterior");
        this.view_ext.BindVehicle(this.vehicle_state.handle, 1, 8);
        this.view_int = this.common.FindTruckView("Interior");
        this.view_int.BindVehicle(this.vehicle_state.handle, 0, 0);
        RedrawRestyle();
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(menues.FindFieldInMenu(_menu, BUTTONS[0])), this,
                                  METHODS[0], 4L);
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(menues.FindFieldInMenu(_menu, BUTTONS[1])), this,
                                  METHODS[1], 4L);
        menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(menues.FindFieldInMenu(_menu, BUTTONS[2])), this,
                                  METHODS[2], 4L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.control_chamelion), this, "onColorChange", 2L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.control_metallic), this, "onColorChange", 2L);
    }

    private int getValueColor(int truecolor, boolean is_hamelion) {
        return ((is_hamelion)
                ? truecolor * 2 + 1
                : truecolor * 2);
    }

    private void RedrawRestyle() {
        SelectColor(0, this.vehicle_state.iMainColor);
        SelectColor(1, this.vehicle_state.iLeather);
        SelectColor(3, this.vehicle_state.iCloth);
        SelectColor(2, this.vehicle_state.iDash);
        SelectColor(4, this.vehicle_state.iDashGauges);
        SelectColor(5, this.vehicle_state.iGlass);
        SelectColor(10, (this.vehicle_state.isMetallic)
                        ? 1
                        : 0);

        if (this.control_metallic != 0L) {
            menues.SetFieldState(this.control_metallic, (this.vehicle_state.isMetallic)
                    ? 1
                    : 0);
        }

        if (this.control_chamelion != 0L) {
            menues.SetFieldState(this.control_chamelion, (this.vehicle_state.isHamelion)
                    ? 1
                    : 0);
        }

        this.m_combobox[0].Select(s_matnum[0] - 1 - this.vehicle_state.iMainColor);
        this.m_combobox[1].Select(this.vehicle_state.iLeather);
        this.m_combobox[2].Select(this.vehicle_state.iDash);
        this.m_combobox[3].Select(this.vehicle_state.iCloth);
        this.m_combobox[4].Select(this.vehicle_state.iDashGauges);
        this.m_combobox[5].Select(this.vehicle_state.iGlass);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void onColorChange(long _menu, MENUbutton_field field) {
        boolean oldmetal = this.vehicle_state.isMetallic;
        boolean oldcham = this.vehicle_state.isHamelion;

        this.vehicle_state.isMetallic = (menues.GetFieldState(this.control_metallic) != 0);
        this.vehicle_state.isHamelion = (menues.GetFieldState(this.control_chamelion) != 0);

        if (oldcham != this.vehicle_state.isHamelion) {
            SelectColor(0, this.vehicle_state.iMainColor);
        }

        if (oldmetal != this.vehicle_state.isMetallic) {
            SelectColor(10, (this.vehicle_state.isMetallic)
                            ? 1
                            : 0);
        }
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param color
     */
    public void SelectColor(int type, int color) {
        switch (type) {
         case 0 :
             this.vehicle_state.iMainColor = color;

             break;

         case 1 :
             this.vehicle_state.iLeather = color;

             break;

         case 3 :
             this.vehicle_state.iCloth = color;

             break;

         case 2 :
             this.vehicle_state.iDash = color;

             break;

         case 4 :
             this.vehicle_state.iDashGauges = color;

             break;

         case 5 :
             this.vehicle_state.iGlass = color;
        }

        MENUTruckview truckview = null;

        if ((type == 0) || (type == 5) || (type == 10)) {
            truckview = this.common.FindTruckView("Exterior");
        } else {
            truckview = this.common.FindTruckView("Interior");
        }

        if (type == 0) {
            truckview.SetState(0, type, getValueColor(this.vehicle_state.iMainColor, this.vehicle_state.isHamelion));
        } else if (type == 10) {
            truckview.SetState(0, type, (this.vehicle_state.isMetallic)
                                        ? 1
                                        : 0);
        } else {
            truckview.SetState(0, type, color);
        }
    }

    int GetTypeByName(String name, String[] names) {
        for (int i = 0; i < names.length; ++i) {
            if (name.indexOf(names[i]) != -1) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Method description
     *
     *
     * @param state
     * @param sender
     */
    @Override
    public void OnSelect(int state, Object sender) {
        for (int i = 0; i < this.m_combobox.length; ++i) {
            if (sender == this.m_combobox[i]) {
                SelectColor(i, (i == 0)
                               ? s_matnum[i] - 1 - state
                               : state);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {
        ListenerManager.TriggerEvent(105);
        this.parent.setShow(true);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "mainRestyleMENU";
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnOk(long _menu, MENUsimplebutton_field field) {
        this.view_ext.UnBind3DModel();
        this.view_int.UnBind3DModel();
        JavaEvents.SendEvent(65, 15, this.vehicle_state);
        JavaEvents.SendEvent(65, 16, this.vehicle_state);
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnCancel(long _menu, MENUsimplebutton_field field) {
        this.view_ext.UnBind3DModel();
        this.view_int.UnBind3DModel();
        JavaEvents.SendEvent(65, 16, this.vehicle_state);
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnDefault(long _menu, MENUsimplebutton_field field) {
        JavaEvents.SendEvent(65, 17, this.vehicle_state);
        RedrawRestyle();
    }

    static class VehicleState {

        /** Field description */
        public long handle;

        /** Field description */
        public long internal_handle;

        /** Field description */
        public int iTechNumber;

        /** Field description */
        public int iMainColor;

        /** Field description */
        public int iLeather;

        /** Field description */
        public int iCloth;

        /** Field description */
        public int iDash;

        /** Field description */
        public int iDashGauges;

        /** Field description */
        public int iGlass;

        /** Field description */
        public String selction_name;

        /** Field description */
        public boolean isMetallic;

        /** Field description */
        public boolean isHamelion;

        VehicleState() {
            this.handle = 0L;
            this.internal_handle = 0L;
            this.iTechNumber = 0;
            this.iMainColor = 0;
            this.iLeather = 0;
            this.iCloth = 0;
            this.iDash = 0;
            this.iDashGauges = 0;
            this.iGlass = 0;
            this.selction_name = null;
            this.isMetallic = true;
            this.isHamelion = false;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
