/*
 * @(#)SliderBox.java   13/08/26
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


package rnr.src.ui;

//~--- JDK imports ------------------------------------------------------------

import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class SliderBox extends JPanel implements ValueChanged {
    static final long serialVersionUID = 1L;
    private static final String str_descr_min = "MIN";
    private static final String str_descr_max = "MAX";
    private static final String str_descr_value = "value ";
    private ValueChanged callback = null;
    private final JTextField descr_min;
    private final JTextField descr_max;
    private final JTextField descr_value;
    private final JTextField txt_min;
    private final JTextField txt_max;
    private final JTextField txt_value;
    private final SliderVariableDouble slider;
    protected double min_value;
    protected double max_value;
    protected int steps;

    /**
     * Constructs ...
     *
     *
     * @param minvalue
     * @param maxvalue
     * @param steps
     * @param currentvalue
     * @param name
     */
    public SliderBox(double minvalue, double maxvalue, int steps, double currentvalue, String name) {
        this.min_value = minvalue;
        this.max_value = maxvalue;
        this.steps = steps;
        setBorder(BorderFactory.createBevelBorder(0));
        setLayout(new GridLayout(4, 1));

        JPanel minpanel = new JPanel();

        minpanel.setLayout(new GridLayout(1, 2));

        JPanel maxpanel = new JPanel();

        maxpanel.setLayout(new GridLayout(1, 2));

        JPanel valuepanel = new JPanel();

        valuepanel.setLayout(new GridLayout(1, 2));
        this.descr_min = new JTextField("MIN");
        this.descr_min.setEditable(false);
        this.txt_min = new JTextField(format(minvalue));
        this.txt_min.setEditable(true);
        this.descr_max = new JTextField("MAX");
        this.descr_max.setEditable(false);
        this.txt_max = new JTextField(format(maxvalue));
        this.txt_max.setEditable(true);
        this.descr_value = new JTextField("value " + name);
        this.descr_value.setEditable(false);
        this.txt_value = new JTextField(format(currentvalue));
        this.txt_value.setEditable(true);
        this.slider = new SliderVariableDouble(this.min_value, this.max_value, steps, currentvalue);
        this.slider.assignChangeListener(this);

        FocusListener lisner = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField field = (JTextField) e.getSource();

                SliderBox.this.min_value = Double.parseDouble(field.getText());

                if (SliderBox.this.min_value > SliderBox.this.max_value) {
                    SliderBox.this.min_value = SliderBox.this.max_value;
                    field.setText(SliderBox.this.format(SliderBox.this.min_value));
                }

                SliderBox.this.updateSliderLimits();
            }
        };

        this.txt_min.addFocusListener(lisner);

        FocusListener lisner1 = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField field = (JTextField) e.getSource();

                SliderBox.this.max_value = Double.parseDouble(field.getText());

                if (SliderBox.this.min_value > SliderBox.this.max_value) {
                    SliderBox.this.max_value = SliderBox.this.min_value;
                    field.setText(SliderBox.this.format(SliderBox.this.max_value));
                }

                SliderBox.this.updateSliderLimits();
            }
        };

        this.txt_max.addFocusListener(lisner1);

        FocusListener lisner11 = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField field = (JTextField) e.getSource();
                double currvalue = Double.parseDouble(field.getText());

                SliderBox.this.setSliderValue(currvalue);
            }
        };

        this.txt_value.addFocusListener(lisner11);
        minpanel.add(this.descr_min);
        minpanel.add(this.txt_min);
        maxpanel.add(this.descr_max);
        maxpanel.add(this.txt_max);
        valuepanel.add(this.descr_value);
        valuepanel.add(this.txt_value);
        add(minpanel);
        add(maxpanel);
        add(this.slider);
        add(valuepanel);
    }

    protected void updateSliderLimits() {
        this.slider.changeLimits(this.min_value, this.max_value, this.steps);
    }

    protected String format(double value) {
        NumberFormat n = NumberFormat.getInstance();

        n.setMaximumFractionDigits(3);

        return n.format(value);
    }

    /**
     * Method description
     *
     */
    @Override
    public void recieveChange() {
        this.txt_value.setText(format(this.slider.getVariableValue()));

        if (this.callback != null) {
            this.callback.recieveChange();
        }
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setSliderValue(double value) {
        this.slider.setVariableValue(value);
        recieveChange();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getSliderValue() {
        return this.slider.getVariableValue();
    }

    /**
     * Method description
     *
     *
     * @param callback
     */
    public void assignChangeListener(ValueChanged callback) {
        this.callback = callback;
    }
}


//~ Formatted in DD Std on 13/08/26
