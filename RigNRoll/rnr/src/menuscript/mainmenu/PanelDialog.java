/*
 * @(#)PanelDialog.java   13/08/26
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


package rnr.src.menuscript.mainmenu;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.FabricControlColor;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.SMenu;
import rnr.src.menu.menues;
import rnr.src.menuscript.parametrs.ParametrsBlock;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public abstract class PanelDialog implements IWindowContext {
    private static final String EXITMETHOD = "OnExit";
    private static final String OKMETHOD = "OnOk";
    private static final String DEFAULTMETHOD = "OnDefault";
    private static final String APPLYMETHOD = "OnApply";
    protected Panel parent = null;
    long[] controls = null;
    protected ParametrsBlock param_values = new ParametrsBlock();
    private boolean animating;

    PanelDialog(long _menu, long window, long[] controls, long exitButton, long defaultButton, long okButton,
                long applyButton, Panel parent) {
        this.parent = parent;
        this.controls = controls;

        if (0L != exitButton) {
            Object field = menues.ConvertMenuFields(exitButton);

            menues.SetScriptOnControl(_menu, field, this, "OnExit", 4L);
        }

        if (0L != defaultButton) {
            Object field = menues.ConvertMenuFields(defaultButton);

            menues.SetScriptOnControl(_menu, field, this, "OnDefault", 4L);
        }

        if (0L != okButton) {
            Object field = menues.ConvertMenuFields(okButton);

            menues.SetScriptOnControl(_menu, field, this, "OnOk", 4L);
        }

        if (0L != applyButton) {
            Object field = menues.ConvertMenuFields(applyButton);

            menues.SetScriptOnControl(_menu, field, this, "OnApply", 4L);
        }

        if (0L != window) {
            Object _window = menues.ConvertMenuFields(window);

            menues.SetScriptOnControl(_menu, _window, this, "OnExit", 17L);
        }
    }

    /**
     * Method description
     *
     */
    public void afterInit() {
        this.param_values.onUpdate();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnExit(long _menu, MENUsimplebutton_field button) {
        exitDialog();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnOk(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
        exitDialog();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnDefault(long _menu, MENUsimplebutton_field button) {
        this.param_values.onDefault();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnApply(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param window
     */
    public void OnExit(long _menu, SMenu window) {
        exitDialog();
    }

    protected void exitDialog() {
        this.parent.OnDialogExit(this);
    }

    boolean isAnimating() {
        return this.animating;
    }

    void setAnimating(boolean value) {
        this.animating = value;
    }

    void setFreeze(boolean value) {
        if (value) {
            for (int i = 0; i < this.controls.length; ++i) {
                CA.freezControl(this.controls[i]);
            }
        } else {
            for (int i = 0; i < this.controls.length; ++i) {
                CA.unfreezControl(this.controls[i]);
            }
        }

        onFreeze(value);
    }

    void setShow(boolean value) {
        if (value) {
            for (int i = 0; i < this.controls.length; ++i) {
                CA.showControl(this.controls[i]);
            }
        } else {
            for (int i = 0; i < this.controls.length; ++i) {
                CA.hideControl(this.controls[i]);
            }
        }

        onShow(value);
    }

    void setAlpha(int alpha) {
        for (int i = 0; i < this.controls.length; ++i) {
            FabricControlColor.setControlAlfa(this.controls[i], alpha);
        }
    }

    /**
     * Method description
     *
     */
    public void readParamValues() {}

    /**
     * Method description
     *
     */
    public void update() {
        this.param_values.onUpdate();
    }

    /**
     * Method description
     *
     */
    public void exitMenu() {}

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean areValuesChanged() {
        return this.param_values.areValuesChanged();
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitWindowContext() {
        exitDialog();
    }

    /**
     * Method description
     *
     */
    @Override
    public void updateWindowContext() {
        update();
    }

    protected void onShow(boolean value) {}

    protected void onFreeze(boolean value) {}
}


//~ Formatted in DD Std on 13/08/26
