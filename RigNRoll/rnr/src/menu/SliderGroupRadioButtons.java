/*
 * @(#)SliderGroupRadioButtons.java   13/08/25
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
public class SliderGroupRadioButtons extends SmartArrowedGroup implements IIntegerValueChanger {
    private static final String SLIDERBUTTON = "LampGreen";
    private static final String LAMPS_INDICATOR = "LAMPS - ";
    private static final String SLIDER_ELEMENTS_SUFFICS = " Elements";
    private int indicator_num = 0;
    private int indicator_step = 0;
    private String indicatorsParent = "";
    private RadioLineGroup radioButtons = null;
    private MENUbutton_field[] indicators = null;
    private boolean params_were_read = false;
    private boolean deselected = false;
    private int value;
    private int min_value;
    private int max_value;

    /**
     * Constructs ...
     *
     *
     * @param xml_file
     * @param xml_node_group
     * @param title
     * @param minvalue
     * @param maxvalue
     * @param curvalue
     * @param _bBlind
     */
    public SliderGroupRadioButtons(String xml_file, String xml_node_group, String title, int minvalue, int maxvalue,
                                   int curvalue, boolean _bBlind) {
        super(xml_file, xml_node_group, title, _bBlind);
        this.min_value = minvalue;
        this.max_value = maxvalue;
        this.value = curvalue;

        if ((minvalue < 0) || (maxvalue < 0) || (curvalue < 0)) {
            Log.menu("SliderGroupRadioButtons constructor minvalue<0 || maxvalue<0 || curvalue <0. Values: " + minvalue
                     + "\t" + maxvalue + "\t" + curvalue);
        } else {
            if ((minvalue < maxvalue) && (curvalue <= maxvalue) && (curvalue >= minvalue)) {
                return;
            }

            Log.menu(
                "SliderGroupRadioButtons constructor minvalue>=maxvalue || curvalue>maxvalue || curvalue<minvalue. Values: "
                + minvalue + "\t" + maxvalue + "\t" + curvalue);
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
            if (readIndicatorParams(_menu, this.controls[i])) {
                continue;
            }
        }

        copyIndicators(_menu);
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

    private String getIndicatorsTopName() {
        if (null == this.controls) {
            Log.menu("Slider Group. Trying to access not created controls field.");

            return "";
        }

        if (0 == this.controls.length) {
            Log.menu("Slider Group. controls field has 0 length.");

            return "";
        }

        return this.indicatorsParent;
    }

    private boolean readIndicatorParams(long _menu, long field) {
        String name = menues.GetFieldName(field);

        if (name.startsWith("LAMPS - ")) {
            this.params_were_read = true;

            String suffix = name.substring("LAMPS - ".length());
            String[] values = suffix.split(" ");

            if (values.length != 2) {
                Log.menu("Slider Group. Slider " + this.xml_node_group + " has wrong named " + "LAMPS - ");
            } else {
                this.indicator_num = Integer.decode(values[0]).intValue();
                this.indicator_step = Integer.decode(values[1]).intValue();
                this.indicators = new MENUbutton_field[this.indicator_num];
            }

            this.indicatorsParent = name + "SmartGroupTop" + (NUMELEM++);
            menues.SetFieldName(_menu, field, this.indicatorsParent);

            return true;
        }

        return false;
    }

    private void copyIndicators(long _menu) {
        if (!(this.params_were_read)) {
            Log.menu("Slider Group. Slider initialisation has wrong order");

            return;
        }

        for (int i = 0; i < this.indicator_num; ++i) {
            long ctrl = menues.InitXmlControl(_menu, this.xml_file, this.xml_node_group + " Elements", "LampGreen");
            MENUbutton_field button = menues.ConvertButton(ctrl);

            button.pox += i * this.indicator_step;
            button.parentName = getIndicatorsTopName();
            button.userid = i;
            menues.UpdateField(button);
            this.indicators[i] = button;
            menues.SetScriptOnControl(_menu, button, this, "OnButton", 2L);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnButton(long _menu, MENUbutton_field button) {
        this.radioButtons.select(button.userid);
    }

    private void initRadioGroup(long _menu) {
        if (null == this.indicators) {
            Log.menu("Slider Group. Cannot initialize radio group.");

            return;
        }

        this.radioButtons = new RadioLineGroup(_menu, this.indicators);
        this.radioButtons.addListener(this);
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
        int selected = this.radioButtons.getSelected();

        if (selected == 0) {
            if (nullValueExists()) {
                this.deselected = true;
                this.radioButtons.deselectall();
                updateValue();
            }

            return;
        }

        this.radioButtons.select(selected - 1);
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
        if (this.deselected) {
            this.deselected = false;
            this.radioButtons.select(0);
            updateValue();

            return;
        }

        int selected = this.radioButtons.getSelected();

        if (selected >= this.indicator_num - 1) {
            return;
        }

        this.radioButtons.select(selected + 1);
        updateValue();
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
        this.deselected = false;
        updateValue();
    }

    private boolean nullValueExists() {
        return (this.min_value == 0);
    }

    private void updateValue() {
        if (this.deselected) {
            this.value = this.min_value;
            callValueChanged();
            debug_text();

            return;
        }

        int selected = this.radioButtons.getSelected();

        if (selected == this.indicator_num - 1) {
            this.value = this.max_value;
            debug_text();
            callValueChanged();

            return;
        }

        if (nullValueExists()) {
            ++selected;
        }

        int totalstates = (nullValueExists())
                          ? this.indicator_num
                          : this.indicator_num - 1;

        this.value = (this.min_value + (this.max_value - this.min_value) / totalstates * selected);
        callValueChanged();
        debug_text();
    }

    private void setupValue() {
        int totalstates = (nullValueExists())
                          ? this.indicator_num
                          : this.indicator_num - 1;
        int selected = (this.value - this.min_value) / (this.max_value - this.min_value) / totalstates;

        selected = (nullValueExists())
                   ? --selected
                   : selected;

        if (-1 != selected) {
            this.radioButtons.select(selected);
            updateValue();
        } else {
            this.radioButtons.deselectall();
            this.deselected = true;
        }
    }

    private void debug_text() {}

    /**
     * Method description
     *
     */
    @Override
    public void hide() {
        super.hide();

        for (int i = 0; i < this.indicators.length; ++i) {
            menues.SetBlindess(this.indicators[i].nativePointer, true);
            menues.SetIgnoreEvents(this.indicators[i].nativePointer, true);
            menues.SetShowField(this.indicators[i].nativePointer, false);
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void show() {
        super.show();

        for (int i = 0; i < this.indicators.length; ++i) {
            menues.SetBlindess(this.indicators[i].nativePointer, false);
            menues.SetIgnoreEvents(this.indicators[i].nativePointer, false);
            menues.SetShowField(this.indicators[i].nativePointer, true);
        }
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
        setupValue();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getValue() {
        return this.value;
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

    /**
     * @return the sliderbutton
     */
    public static String getSliderbutton() {
        return SLIDERBUTTON;
    }

    /**
     * @return the lampsIndicator
     */
    public static String getLampsIndicator() {
        return LAMPS_INDICATOR;
    }

    /**
     * @return the sliderElementsSuffics
     */
    public static String getSliderElementsSuffics() {
        return SLIDER_ELEMENTS_SUFFICS;
    }
}


//~ Formatted in DD Std on 13/08/25
