/*
 * @(#)Common.java   13/08/25
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


package rnr.src.menu;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscr.gameinfo;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class Common {

    /** Field description */
    public static int s_ID = 0;

    /** Field description */
    public static Common s_lastcommon;

    /** Field description */
    public PatchModif s_modif = new PatchModif();

    /** Field description */
    public long s_menu;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     */
    public Common(long _menu) {
        this.s_menu = _menu;
        s_lastcommon = this;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public long GetMenu() {
        return this.s_menu;
    }

    /**
     * Method description
     *
     */
    public void InitBalance() {
        gameinfo.script.m_iTotalAuth = 0;
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public SMenu FindWindow(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);

        return menues.ConvertWindow(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param _group
     *
     * @return
     */
    public MENUText_field FindTextField(String name, long _group) {
        long control = menues.FindFieldInGroup(this.s_menu, _group, name);

        return menues.ConvertTextFields(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param _group
     *
     * @return
     */
    public MENUsimplebutton_field FindSimpleButton(String name, long _group) {
        long control = menues.FindFieldInGroup(this.s_menu, _group, name);

        return menues.ConvertSimpleButton(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param parent
     *
     * @return
     */
    public MENUsimplebutton_field FindSimpleButtonByParent(String name, String parent) {
        long control = menues.FindFieldInMenu_ByParent(this.s_menu, parent, name);

        return menues.ConvertSimpleButton(control);
    }

    /**
     * Method description
     *
     *
     * @param xmlfilename
     *
     * @return
     */
    public static String ConstructPath(String xmlfilename) {
        return "..\\data\\config\\menu\\" + xmlfilename;
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param _group
     *
     * @return
     */
    public MENUbutton_field FindRadioButton(String name, long _group) {
        long control = menues.FindFieldInGroup(this.s_menu, _group, name);

        return menues.ConvertButton(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param parent
     *
     * @return
     */
    public MENUbutton_field FindRadioButtonByParent(String name, String parent) {
        long control = menues.FindFieldInMenu_ByParent(this.s_menu, parent, name);

        return menues.ConvertButton(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public MENUText_field FindTextField(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);

        return menues.ConvertTextFields(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param parent
     *
     * @return
     */
    public MENUText_field FindTextFieldByParent(String name, String parent) {
        long control = menues.FindFieldInMenu_ByParent(this.s_menu, parent, name);

        return menues.ConvertTextFields(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public MENUsimplebutton_field FindSimpleButton(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);

        return menues.ConvertSimpleButton(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param obj
     * @param method
     */
    public void SetScriptOnButton(String name, Object obj, String method) {
        menues.SetScriptOnControl(this.s_menu, FindSimpleButton(name), obj, method, 4L);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param obj
     * @param method
     * @param userid
     */
    public void SetScriptOnButtonUserid(String name, Object obj, String method, int userid) {
        MENUsimplebutton_field b = FindSimpleButton(name);

        b.userid = userid;
        menues.UpdateField(b);
        menues.SetScriptOnControl(this.s_menu, b, obj, method, 4L);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public MENUTruckview FindTruckView(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);

        return ((MENUTruckview) menues.ConvertMenuFields(control));
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public xmlcontrols.MENUCustomStuff FindTab(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);

        return ((xmlcontrols.MENUCustomStuff) menues.ConvertMenuFields(control));
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param parent
     * @param shiftx
     * @param shifty
     * @param xmlfile
     * @param controlgroup
     *
     * @return
     */
    public MENUbutton_field CreateRadioButton(String name, String parent, int shiftx, int shifty, String xmlfile,
            String controlgroup) {
        MENUbutton_field radio = CreateRadioButton(name, parent, xmlfile, controlgroup);

        radio.pox += shiftx;
        radio.poy += shifty;
        menues.UpdateField(radio);

        return radio;
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param parent
     * @param xmlfile
     * @param controlgroup
     *
     * @return
     */
    public MENUbutton_field CreateRadioButton(String name, String parent, String xmlfile, String controlgroup) {
        long control = menues.InitXmlControl(this.s_menu, xmlfile, controlgroup, name);
        MENUbutton_field button = menues.ConvertButton(control);

        button.parentName = parent;
        menues.UpdateField(button);

        return button;
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public RNRMap FindRNRMap(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);

        return menues.ConvertRNRMAPFields(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param parent
     *
     * @return
     */
    public RNRMap FindRNRMapByParent(String name, String parent) {
        long control = menues.FindFieldInMenu_ByParent(this.s_menu, parent, name);

        return menues.ConvertRNRMAPFields(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public MENUbutton_field FindRadioButton(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);

        return menues.ConvertButton(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public MENU_ranger FindScroller(String name) {
        long control = menues.FindFieldInMenu(this.s_menu, name);

        return menues.ConvertRanger(control);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param parent
     *
     * @return
     */
    public MENU_ranger FindScrollerByParent(String name, String parent) {
        long control = menues.FindFieldInMenu_ByParent(this.s_menu, parent, name);

        return menues.ConvertRanger(control);
    }

    /**
     * Method description
     *
     *
     * @param control
     *
     * @return
     */
    public RatingData GetRatingData(long control) {
        RatingData data = new RatingData();

        this.s_modif.Setup(data);
        menues.CallMappingModifications(control, this.s_modif, "GetData");

        return data;
    }

    /**
     * Method description
     *
     *
     * @param button
     * @param text
     * @param percent
     * @param data
     */
    public void SetRadioPercent(MENUbutton_field button, String text, double percent, RatingData data) {
        button.text = text;
        button.lenx = Math.max((int) (percent * data.picsize), data.textsize);
        this.s_modif.Setup(data, (int) (percent * data.picsize));
        menues.CallMappingModifications(button.nativePointer, this.s_modif, "FillData");
        menues.UpdateField(button);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param text
     */
    public void SetTextValue(String name, String text) {
        MENUText_field field = FindTextField(name);

        field.text = text;
        menues.UpdateField(field);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param text
     */
    public void AddTextValue(String name, String text) {
        MENUText_field field = FindTextField(name);

        field.text += text;
        menues.UpdateField(field);
    }

    /**
     * Method description
     *
     *
     * @param s1
     * @param s2
     * @param isascending
     *
     * @return
     */
    public static int Compare(String s1, String s2, boolean isascending) {
        int order = (isascending)
                    ? 1
                    : -1;

        if (s1 == null) {
            return order;
        }

        return (s1.compareTo(s2) * order);
    }

    /**
     * Method description
     *
     *
     * @param i1
     * @param i2
     * @param isascending
     *
     * @return
     */
    public static int Compare(int i1, int i2, boolean isascending) {
        int order = (isascending)
                    ? 1
                    : -1;
        int diff = i1 - i2;

        return (diff * order);
    }

    /**
     * Method description
     *
     *
     * @param d1
     * @param d2
     * @param isascending
     *
     * @return
     */
    public static int Compare(double d1, double d2, boolean isascending) {
        int order = (isascending)
                    ? 1
                    : -1;
        double d = d1 - d2;
        int diff = 0;

        if (d > 0.0D) {
            diff = 1;
        }

        if (d < 0.0D) {
            diff = -1;
        }

        return (diff * order);
    }

    /**
     * Method description
     *
     *
     * @param a
     *
     * @return
     */
    public static int Sign(double a) {
        if (a == 0.0D) {
            return 0;
        }

        return ((a > 0.0D)
                ? 1
                : -1);
    }

    /**
     * Method description
     *
     *
     * @param b1
     * @param b2
     * @param isascending
     *
     * @return
     */
    public static int Compare(boolean b1, boolean b2, boolean isascending) {
        int order = (isascending)
                    ? 1
                    : -1;
        int a = (b1)
                ? 1
                : 0;
        int b = (b2)
                ? 1
                : 0;

        return ((a - b) * order);
    }

    /**
     * Method description
     *
     *
     * @param t1
     * @param t2
     * @param isascending
     *
     * @return
     */
    public static int Compare(TimeData t1, TimeData t2, boolean isascending) {
        return Compare(t1.hours * 60 + t1.minutes, t2.hours * 60 + t2.minutes, isascending);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int GetID() {
        return (s_ID++);
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ    
     */
    public class PatchModif {
        RatingData data;
        int len;

        void Setup(RatingData _data) {
            this.data = _data;
        }

        void Setup(RatingData _data, int _len) {
            this.data = _data;
            this.len = _len;
        }

        void GetData(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
            this.data.picsize = sizex;

            for (int i = 0; i < stuff.length; ++i) {
                if (!(stuff[i].usepatch)) {
                    continue;
                }

                this.data.textsize = stuff[i]._patch.sx;

                return;
            }
        }

        void FillData(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
            for (int i = 0; i < stuff.length; ++i) {
                if (!(stuff[i].usepatch)) {
                    continue;
                }

                stuff[i]._patch.sx = this.len;
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/25
