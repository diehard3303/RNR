/*
 * @(#)RadioSmartSwitch.java   13/08/25
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
public class RadioSmartSwitch extends SmartArrowedGroup implements IIntegerValueChanger {
    private static final String VALUE = "VALUE";
    private long button_value = 0L;
    private int state = 0;
    int max_states = 0;

    /**
     * Constructs ...
     *
     *
     * @param xml_file
     * @param xml_node_group
     * @param title
     * @param curstate
     * @param max_states
     * @param bBlind
     */
    public RadioSmartSwitch(String xml_file, String xml_node_group, String title, int curstate, int max_states,
                            boolean bBlind) {
        super(xml_file, xml_node_group, title, bBlind);
        this.state = curstate;
        this.max_states = max_states;
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
            String name = menues.GetFieldName(this.controls[i]);

            if (name.compareTo("VALUE") == 0) {
                this.button_value = this.controls[i];
            }
        }

        if (0L == this.button_value) {
            Log.menu("RadioSmartSwitch couldnt find radio button named VALUE");
        }

        updateValue();
    }

    /**
     * Method description
     *
     */
    @Override
    public void init() {
        super.init();
    }

    /**
     * Method description
     *
     */
    @Override
    public void show() {
        super.show();

        if (0L != this.button_value) {
            menues.SetBlindess(this.button_value, true);
            menues.SetIgnoreEvents(this.button_value, true);
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
    public void OnLeft(long _menu, MENUsimplebutton_field button) {
        if (this.state <= 0) {
            this.state = 0;

            return;
        }

        this.state -= 1;
        updateValue();
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
        if (this.state >= this.max_states) {
            this.state = this.max_states;

            return;
        }

        this.state += 1;
        updateValue();
    }

    private void updateValue() {
        callValueChanged();

        if (0L == this.button_value) {
            return;
        }

        menues.SetFieldState(this.button_value, this.state);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int changeValue() {
        return this.state;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void reciveValue(int value) {
        if (value == this.state) {
            return;
        }

        if (value > this.max_states) {
            this.state = this.max_states;
        } else if (value < 0) {
            this.state = 0;
        } else {
            this.state = value;
        }

        updateValue();
    }

    /**
     * @return the value
     */
    public static String getValue() {
        return VALUE;
    }
}


//~ Formatted in DD Std on 13/08/25
