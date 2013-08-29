/*
 * @(#)IWheelEnabled.java   13/08/25
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

import rnr.src.rnrcore.EventsHolder;
import rnr.src.rnrcore.IEventListener;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public abstract class IWheelEnabled implements IFocusHolder {
    private static ControlsMouseWheel wouse_wheel = null;
    private static ControlsCtrlAPressed ctrla = null;
    private static IWheelEnabled wheel_enabled = null;

    static {
        wouse_wheel = new ControlsMouseWheel();
        EventsHolder.addEventListenet(76, wouse_wheel);
        ctrla = new ControlsCtrlAPressed();
        EventsHolder.addEventListenet(32, ctrla);
    }

    private boolean bHide;

    /**
     * Constructs ...
     *
     */
    public IWheelEnabled() {
        this.bHide = false;
    }

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public abstract void ControlsMouseWheel(int paramInt);

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnMouseInside(long _menu, MENUText_field button) {
        if (!(this.bHide)) {
            wheel_enabled = this;
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnMouseOutside(long _menu, MENUText_field button) {
        if (wheel_enabled == this) {
            wheel_enabled = null;
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param table_name
     */
    public void wheelInit(long _menu, String table_name) {
        long table = menues.FindFieldInMenu(_menu, table_name);

        if (table != 0L) {
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(table), this, "OnMouseInside", 6L);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(table), this, "OnMouseOutside", 5L);
        }

        FocusManager.register(this);
    }

    /**
     * Method description
     *
     */
    public void wheelDeinit() {
        FocusManager.unRegister(this);

        if (wheel_enabled == this) {
            wheel_enabled = null;
        }
    }

    /**
     * Method description
     *
     */
    public void wheelControlSelected() {
        FocusManager.enterFocus(this);
    }

    /**
     * Method description
     *
     *
     * @param v
     */
    public void wheelHide(boolean v) {
        this.bHide = v;
    }

    static class ControlsCtrlAPressed implements IEventListener {

        /**
         * Method description
         *
         *
         * @param value
         */
        @Override
        public void on_event(int value) {
            IFocusHolder focused = FocusManager.getFocused();

            if (focused == null) {
                return;
            }

            focused.ControlsCtrlAPressed();
        }
    }


    static class ControlsMouseWheel implements IEventListener {

        /**
         * Method description
         *
         *
         * @param value
         */
        @Override
        public void on_event(int value) {
            if (IWheelEnabled.wheel_enabled == null) {
                return;
            }

            IWheelEnabled.wheel_enabled.ControlsMouseWheel(value);
        }
    }
}


//~ Formatted in DD Std on 13/08/25
