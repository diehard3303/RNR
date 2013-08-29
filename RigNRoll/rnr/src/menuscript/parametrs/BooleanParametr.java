/*
 * @(#)BooleanParametr.java   13/08/26
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


package rnr.src.menuscript.parametrs;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class BooleanParametr implements IParametr {
    private IBooleanValueChanger changer = null;
    private boolean f_default_setted = false;
    private boolean confirmed_value;
    private boolean value;
    private boolean default_value;

    BooleanParametr(boolean value, boolean default_value, IBooleanValueChanger ch) {
        this.value = value;
        this.default_value = default_value;
        this.confirmed_value = value;
        this.changer = ch;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean getBoolean() {
        return this.confirmed_value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getInteger() {
        return -1;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isBoolean() {
        return true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isInteger() {
        return false;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void setBoolean(boolean value) {
        this.confirmed_value = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void setInteger(int value) {}

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void setBooleanDefault(boolean value) {
        this.default_value = value;
        this.f_default_setted = true;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void setIntegerDefault(int value) {}

    /**
     * Method description
     *
     */
    @Override
    public void update() {
        this.value = this.confirmed_value;
        this.changer.reciveValue(this.value);
    }

    /**
     * Method description
     *
     */
    @Override
    public void updateDefault() {
        if (!(this.f_default_setted)) {
            this.default_value = this.value;
        }

        this.f_default_setted = true;
        update();
    }

    /**
     * Method description
     *
     */
    @Override
    public void makeDefault() {
        this.value = this.default_value;
        this.changer.reciveValue(this.value);
    }

    /**
     * Method description
     *
     *
     * @param to_confirm
     */
    @Override
    public void readFromChanger(boolean to_confirm) {
        this.value = this.changer.changeValue();

        if (to_confirm) {
            this.confirmed_value = this.value;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean changed() {
        return ((this.confirmed_value != this.value) || (this.changer.changeValue() != this.value));
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void setBooleanChange(boolean value) {
        this.value = value;

        if (null != this.changer) {
            this.changer.reciveValue(value);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean getBooleanChange() {
        return this.value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void setIntegerChange(int value) {}

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getIntegerChange() {
        return -1;
    }
}


//~ Formatted in DD Std on 13/08/26
