/*
 * @(#)SliderVariableDouble.java   13/08/26
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

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class SliderVariableDouble extends JSlider implements ChangeListener {
    static final long serialVersionUID = 1L;
    private double min_value = 0.0D;
    private double max_value = 0.0D;
    private double cur_value = 0.0D;
    private int max_scale_value = 0;
    private ValueChanged callback = null;

    /**
     * Constructs ...
     *
     *
     * @param min_value
     * @param max_value
     * @param steps
     */
    public SliderVariableDouble(double min_value, double max_value, int steps) {
        super(0, 0, 1, 0);
        this.cur_value = min_value;
        updateLimits(min_value, max_value, steps);
        setMaximum(this.max_scale_value);
        setValue(getValueInt(this.cur_value));
        addChangeListener(this);
    }

    /**
     * Constructs ...
     *
     *
     * @param min_value
     * @param max_value
     * @param steps
     * @param initialvalue
     */
    public SliderVariableDouble(double min_value, double max_value, int steps, double initialvalue) {
        super(0, 0, 1, 0);
        this.cur_value = initialvalue;
        updateLimits(min_value, max_value, steps);
        setMaximum(this.max_scale_value);
        setValue(getValueInt(this.cur_value));
        addChangeListener(this);
    }

    private void updateLimits(double min_value, double max_value, int steps) {
        this.min_value = min_value;
        this.max_value = max_value;
        this.max_scale_value = steps;
    }

    private int getValueInt(double value) {
        if (value < this.min_value) {
            return 0;
        }

        if (value > this.max_value) {
            return this.max_scale_value;
        }

        return (int) (this.max_scale_value * (value - this.min_value) / (this.max_value - this.min_value));
    }

    private double getValueDouble(int value) {
        if (value == 0) {
            return this.min_value;
        }

        if (value == this.max_scale_value) {
            return this.max_value;
        }

        return (this.min_value + (this.max_value - this.min_value) * value / this.max_scale_value);
    }

    /**
     * Method description
     *
     *
     * @param e
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        double last_cur_value = this.cur_value;

        this.cur_value = getValueDouble(getValue());

        if ((last_cur_value != this.cur_value) && (this.callback != null)) {
            this.callback.recieveChange();
        }
    }

    /**
     * Method description
     *
     *
     * @param cb
     */
    public void assignChangeListener(ValueChanged cb) {
        this.callback = cb;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getVariableValue() {
        return this.cur_value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setVariableValue(double value) {
        this.cur_value = value;
        setValue(getValueInt(this.cur_value));
    }

    /**
     * Method description
     *
     *
     * @param min_value
     * @param max_value
     * @param steps
     */
    public void changeLimits(double min_value, double max_value, int steps) {
        updateLimits(min_value, max_value, steps);
        setMaximum(this.max_scale_value);
        setValue(getValueInt(this.cur_value));
    }
}


//~ Formatted in DD Std on 13/08/26
