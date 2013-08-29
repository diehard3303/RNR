/*
 * @(#)PopUpSearch.java   13/08/26
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

import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.SMenu;
import rnr.src.menu.menues;
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
public class PopUpSearch {
    private static String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static int MENU_POSITION = 0;
    private static String TEMPLATE_GROOUP = "Tablegroup - ELEMENTS - Filer Button";
    private static String BUTTON_TEMPLATE = "SelectSearch";
    private static String ACTION_METH = "onButton";
    private static int count = 0;
    private static final String BUTTONS_SUFFIX = " PopUpSearch";
    private long _menu = 0L;
    private int num_controls = 0;
    private int shift_controls = 0;
    private long menu_control = 0L;
    private int initial_y = 0;
    private long[] buttons = null;
    private int size = 0;
    private final ArrayList<IChoosedata> listeners = new ArrayList();
    private String menu_name;

    PopUpSearch(long _menu, String MENU_GROUP) {
        this._menu = _menu;

        long[] menu_controls = menues.InitXml(_menu, XML, MENU_GROUP);

        if ((null == menu_controls) || (menu_controls.length < MENU_POSITION)) {
            Log.menu("ERRORR. PopUpSearch cannot load xml " + XML + " group " + MENU_GROUP);

            return;
        }

        this.menu_control = menu_controls[MENU_POSITION];

        SMenu window_control = menues.ConvertWindow(this.menu_control);

        this.menu_name = window_control.nameID;

        String[] astr = this.menu_name.split(" ");

        if (astr.length < 2) {
            Log.menu(
                "ERRORR. PopUpSearch. Menu Table. Bad name for root element - does not include table sizes. Name:\t"
                + this.menu_name);

            return;
        }

        this.num_controls = Integer.decode(astr[(astr.length - 2)]).intValue();
        this.shift_controls = Integer.decode(astr[(astr.length - 1)]).intValue();

        PopUpSearch tmp230_229 = this;

        tmp230_229.menu_name = tmp230_229.menu_name + " PopUpSearch" + count;
        window_control.nameID = this.menu_name;
        menues.UpdateField(window_control);
        formMenu();
        formControls(_menu);
    }

    void addListener(IChoosedata listener) {
        this.listeners.add(listener);
    }

    void removeListener(IChoosedata listener) {
        this.listeners.remove(listener);
    }

    private void formMenu() {
        SMenu menu = menues.ConvertWindow(this.menu_control);

        menu.poy -= this.shift_controls * (this.num_controls - 1);
        menu.leny = (this.shift_controls * this.num_controls);
        this.initial_y = menu.poy;
        menues.UpdateField(menu);
    }

    private void formControls(long _menu) {
        this.buttons = new long[this.num_controls];

        for (int i = 0; i < this.num_controls; ++i) {
            count += 1;

            long button = menues.InitXmlControl(_menu, XML, TEMPLATE_GROOUP, BUTTON_TEMPLATE);

            this.buttons[i] = button;

            MENUsimplebutton_field butt = menues.ConvertSimpleButton(button);

            butt.nameID = butt.nameID + " PopUpSearch" + count;
            butt.userid = i;
            butt.parentName = this.menu_name;
            butt.poy += i * this.shift_controls;
            butt.text = "Boo" + i;
            menues.UpdateField(butt);
            menues.SetScriptOnControl(_menu, butt, this, ACTION_METH, 4L);
        }
    }

    void show(int shift) {
        menues.SetShowField(this.menu_control, true);

        SMenu menu = menues.ConvertWindow(this.menu_control);

        menu.poy = (this.initial_y + shift);
        menues.UpdateField(menu);
    }

    void hide() {
        menues.SetShowField(this.menu_control, false);
    }

    private int getButtonIndex(int dataindex) {
        return (this.num_controls - dataindex - 1);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getFocusedData() {
        int count = 0;

        for (long ser : this.buttons) {
            if (menues.isControlOnFocus(this.menu_control, ser)) {
                return getDataIndex(count);
            }

            ++count;
        }

        return getDataIndex(this.size - 1);
    }

    private int getDataIndex(int buttonindex) {
        return (this.num_controls - buttonindex - 1);
    }

    void setData(String[] data) {
        this.size = ((data.length > this.num_controls)
                     ? this.num_controls
                     : data.length);

        for (int i = 0; i < this.size; ++i) {
            menues.SetShowField(this.buttons[getButtonIndex(i)], true);
            menues.SetFieldText(this.buttons[getButtonIndex(i)], data[i]);
            menues.UpdateMenuField(menues.ConvertMenuFields(this.buttons[getButtonIndex(i)]));
        }

        menues.setfocuscontrolonmenu(this._menu, this.buttons[getButtonIndex(this.size - 1)]);

        for (int i = this.size; i < this.num_controls; ++i) {
            menues.SetShowField(this.buttons[getButtonIndex(i)], false);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void onButton(long _menu, MENUsimplebutton_field field) {
        for (IChoosedata data : this.listeners) {
            data.selectData(getDataIndex(field.userid));
        }
    }
}


//~ Formatted in DD Std on 13/08/26
