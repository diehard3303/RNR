/*
 * @(#)ListOfAlternatives.java   13/08/25
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

import rnr.src.menuscript.parametrs.IIntegerValueChanger;
import rnr.src.rnrcore.Log;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class ListOfAlternatives extends SmartArrowedGroup implements IIntegerValueChanger {
    private static final String VALUE = "VALUE";
    private String[] alteranlives = null;
    private long valu_field = 0L;
    private int choose = 0;
    private boolean f_fieldfound = false;

    /**
     * Constructs ...
     *
     *
     * @param xml_file
     * @param xml_node_group
     * @param title
     * @param alteranlives
     * @param bBlind
     */
    public ListOfAlternatives(String xml_file, String xml_node_group, String title, String[] alteranlives,
                              boolean bBlind) {
        super(xml_file, xml_node_group, title, bBlind);
        this.alteranlives = alteranlives;

        if ((null == alteranlives) || (alteranlives.length == 0)) {
            Log.menu("List alternatives. Alternatives are empty");
        }
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

        for (int i = 0; i < this.controls.length; ++i) {
            if (readValueField(this.controls[i])) {
                continue;
            }
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void init() {
        super.init();
        updateValue();
    }

    private boolean readValueField(long field) {
        String name = menues.GetFieldName(field);

        if (name.compareTo("VALUE") == 0) {
            this.valu_field = field;
            this.f_fieldfound = true;

            return true;
        }

        return false;
    }

    private void updateValue() {
        if (!(this.f_fieldfound)) {
            Log.menu("List alternatives. Value field was not found and trying ti updates.");

            return;
        }

        if (this.choose >= this.alteranlives.length) {
            Log.menu(
                "List alternatives. wrong behaivoir on updateValue. choose>=alteranlives.length. Values:\tchoose:\t"
                + this.choose + "\talteranlives.length:\t" + this.alteranlives.length);

            return;
        }

        menues.SetFieldText(this.valu_field, this.alteranlives[this.choose]);
        menues.UpdateMenuField(menues.ConvertMenuFields(this.valu_field));
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void OnLeft(long _menu, MENUsimplebutton_field button) {
        boolean value_changed = false;

        if (this.choose != 0) {
            this.choose -= 1;
            value_changed = true;
        }

        updateValue();

        if (value_changed) {
            callValueChanged();
        }
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
        int prev_value = this.choose;

        if (++this.choose >= this.alteranlives.length) {
            this.choose = (this.alteranlives.length - 1);
        }

        updateValue();

        if (prev_value != this.choose) {
            callValueChanged();
        }
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setValue(int value) {
        int prev_value = this.choose;

        this.choose = value;
        updateValue();

        if (prev_value != this.choose) {
            callValueChanged();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getValue() {
        return this.choose;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getStringValue() {
        return this.alteranlives[this.choose];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int changeValue() {
        return getValue();
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void reciveValue(int value) {
        setValue(value);
    }
}


//~ Formatted in DD Std on 13/08/25
