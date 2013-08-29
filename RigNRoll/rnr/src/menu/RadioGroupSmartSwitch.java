/*
 * @(#)RadioGroupSmartSwitch.java   13/08/25
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

import rnr.src.menuscript.parametrs.IBooleanValueChanger;
import rnr.src.rnrcore.Log;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class RadioGroupSmartSwitch extends SmartArrowedGroup implements IBooleanValueChanger, IRadioAccess {
    private static final String[] SWITCHBUTTONS = { "OFF", "ON" };
    private final MENUbutton_field[] indicators = new MENUbutton_field[2];
    private int indicators_size = 0;
    private RadioGroup radioButtons = null;
    private boolean value = false;

    /**
     * Constructs ...
     *
     *
     * @param xml_file
     * @param xml_node_group
     * @param title
     * @param value
     * @param bBlind
     */
    public RadioGroupSmartSwitch(String xml_file, String xml_node_group, String title, boolean value, boolean bBlind) {
        super(xml_file, xml_node_group, title, bBlind);
        this.value = value;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void load(long _menu) {
        super.load(_menu);

        for (int nm = 0; nm < SWITCHBUTTONS.length; ++nm) {
            for (int i = 0; i < this.controls.length; ++i) {
                String name = menues.GetFieldName(this.controls[i]);

                if (name.compareTo(SWITCHBUTTONS[nm]) == 0) {
                    MENUbutton_field button = menues.ConvertButton(this.controls[i]);

                    button.userid = nm;
                    menues.UpdateField(button);
                    addIndicator(button);
                }
            }
        }

        initRadioGroup(_menu);
    }

    /**
     * Method description
     *
     */
    @Override
    public void init() {
        super.init();
        setupValue();
    }

    private void addIndicator(MENUbutton_field field) {
        if (this.indicators_size >= 2) {
            Log.menu("Switch group. AddIndicator detected more then 2 elements.");

            return;
        }

        this.indicators[(this.indicators_size++)] = field;
    }

    private void initRadioGroup(long _menu) {
        if (this.indicators_size != 2) {
            Log.menu("Switch Group. Cannot initialize radio group. indicators_size is " + this.indicators_size);

            return;
        }

        this.radioButtons = new RadioGroup(_menu, this.indicators);
        this.radioButtons.addListener(this);
        this.radioButtons.addAccessListener(this);
    }

    private void setupValue() {
        this.radioButtons.select((this.value)
                                 ? 1
                                 : 0);
    }

    private void debug_text() {}

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void OnLeft(long _menu, MENUsimplebutton_field button) {
        this.radioButtons.select(0);
        this.value = false;
        debug_text();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void OnRight(long _menu, MENUsimplebutton_field button) {
        this.radioButtons.select(1);
        this.value = true;
        debug_text();
    }

    /**
     * Method description
     *
     *
     * @param button
     * @param cs
     */
    @Override
    public void controlSelected(MENUbutton_field button, int cs) {
        this.value = (button.userid == 1);
        debug_text();
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setValue(boolean value) {
        this.value = value;
        setupValue();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean getValue() {
        return this.value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean changeValue() {
        return getValue();
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void reciveValue(boolean value) {
        setValue(value);
    }

    /**
     * Method description
     *
     *
     * @param button
     * @param state
     *
     * @return
     */
    @Override
    public boolean controlAccessed(MENUbutton_field button, int state) {
        if (state == 0) {
            if ((!(this.value)) && (this.radioButtons.getSelected() == 0)) {
                return false;
            }

            if ((this.value) && (this.radioButtons.getSelected() == 1)) {
                return false;
            }
        }

        return true;
    }
}


//~ Formatted in DD Std on 13/08/25
