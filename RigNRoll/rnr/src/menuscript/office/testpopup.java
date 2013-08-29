/*
 * @(#)testpopup.java   13/08/26
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

import rnr.src.menu.MENUEditBox;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MenuAfterInitNarrator;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;

//~--- JDK imports ------------------------------------------------------------

import java.util.Random;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class testpopup implements menucreation, IChoosedata {
    private boolean pend_close = false;
    private boolean pend_edit = false;
    private boolean editing = false;
    private boolean pend_exit_edit = false;
    private String filter_text = "";
    private long _menu = 0L;
    private PopUpSearch s;
    private long edit_box;
    private long edit_field;

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
    public void AfterInitMenu(long _menu) {
        MenuAfterInitNarrator.justShowAndStop(_menu);
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
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        this._menu = _menu;
        menues.InitXml(_menu, "..\\data\\config\\menu\\menu_office.xml", "OFFICE");
        this.s = new PopUpSearch(_menu, "Tablegroup - ELEMENTS - HFD Filer Menu");

        long sbutt = menues.FindFieldInMenu(_menu, "BUTTON PopUP - MF - Dealer Offers Search - Model");
        MENUsimplebutton_field button = menues.ConvertSimpleButton(sbutt);

        menues.SetScriptOnControl(_menu, button, this, "onSearch", 4L);
        this.edit_box = menues.FindFieldInMenu(_menu, "MF - Dealer Offers Search - Model - VALUE-Select");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.edit_box), this, "onModelSearch", 4L);
        this.edit_field = menues.FindFieldInMenu(_menu, "MF - Dealer Offers Search - Model - VALUE");
        this.s.addListener(this);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.edit_field), this, "onModelDismiss", 19L);
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.edit_field), this, "onModelEnter", 16L);
        menues.SetScriptObjectAnimation(0L, 20L, this, "onAnimate");
    }

    /**
     * Method description
     *
     */
    public static void create() {
        menues.createSimpleMenu(new testpopup());
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void onSearch(long _menu, MENUsimplebutton_field field) {
        this.s.show(0);

        String[] data = { "stoke 1", "is it stroke - o what?", "smooth!" };

        this.s.setData(data);
        this.pend_edit = true;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void onModelSearch(long _menu, MENUsimplebutton_field field) {
        this.pend_edit = true;
        this.s.show(0);

        String[] data = { "stoke 1", "is it stroke - o what?", "smooth!" };

        this.s.setData(data);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void onModelEnter(long _menu, MENUEditBox field) {
        selectData(this.s.getFocusedData());
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void onModelDismiss(long _menu, MENUEditBox field) {
        selectData(this.s.getFocusedData());
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void onChange(long _menu, long field) {
        String newtext = menues.GetFieldText(field);

        if (newtext.compareTo(this.filter_text) != 0) {
            this.filter_text = newtext;

            String[] resHACK = new String[(int) (10.0D * new Random().nextDouble())];

            this.s.setData(resHACK);
        }

        this.filter_text = newtext;
    }

    /**
     * Method description
     *
     *
     * @param cookie
     * @param time
     */
    public void onAnimate(long cookie, double time) {
        if (this.pend_close) {
            this.editing = false;
            this.pend_exit_edit = true;
            this.s.hide();
        }

        this.pend_close = false;

        if (this.pend_edit) {
            menues.SetShowField(this.edit_box, false);
            menues.SetShowField(this.edit_field, true);
            menues.setFocusWindow(menues.ConvertWindow(menues.GetBackMenu(this._menu)).ID);
            menues.setfocuscontrolonmenu(this._menu, this.edit_field);
            this.editing = true;
        }

        this.pend_edit = false;

        if (this.pend_exit_edit) {
            menues.SetShowField(this.edit_box, true);
            menues.SetShowField(this.edit_field, false);
            this.editing = false;
        }

        this.pend_exit_edit = false;

        if (this.editing) {
            onChange(this._menu, this.edit_field);
            this.s.show(0);
            this.pend_edit = true;
            menues.setFocusWindow(menues.ConvertWindow(menues.GetBackMenu(this._menu)).ID);
            menues.setfocuscontrolonmenu(this._menu, this.edit_field);
        }
    }

    /**
     * Method description
     *
     *
     * @param data
     */
    @Override
    public void selectData(int data) {
        this.pend_close = true;

        String[] str = { "stoke 1", "is it stroke - o what?", "smooth!" };

        this.filter_text = str[data];
        menues.SetFieldText(this.edit_field, this.filter_text);
        menues.SetFieldText(this.edit_box, this.filter_text);
        menues.UpdateMenuField(menues.ConvertMenuFields(this.edit_field));
        menues.UpdateMenuField(menues.ConvertMenuFields(this.edit_box));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "officeMENU";
    }
}


//~ Formatted in DD Std on 13/08/26
