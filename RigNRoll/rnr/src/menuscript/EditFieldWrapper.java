/*
 * @(#)EditFieldWrapper.java   13/08/26
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


package rnr.src.menuscript;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.Helper;
import rnr.src.menu.MENUEditBox;
import rnr.src.menu.menues;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class EditFieldWrapper {
    private final String METHOD_CHANGENAME = "changeName";
    private final String METHOD_DISSMIS = "dissmisName";
    private final ArrayList<IEditFieldListener> listeners = new ArrayList<IEditFieldListener>();
    private long control = 0L;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param controlname
     */
    public EditFieldWrapper(long _menu, String controlname) {
        this.control = menues.FindFieldInMenu(_menu, controlname);

        Object obj = menues.ConvertMenuFields(this.control);

        menues.SetScriptOnControl(_menu, obj, this, "dissmisName", 19L);
        menues.SetScriptOnControl(_menu, obj, this, "changeName", 16L);
    }

    /**
     * Method description
     *
     *
     * @param lst
     */
    public void addListener(IEditFieldListener lst) {
        this.listeners.add(lst);
    }

    /**
     * Method description
     *
     *
     * @param lst
     */
    public void removeListener(IEditFieldListener lst) {
        this.listeners.remove(lst);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param obj
     */
    public void dissmisName(long _menu, MENUEditBox obj) {
        menues.UpdateMenuField(menues.ConvertMenuFields(this.control));

        String text = getText();

        for (IEditFieldListener listener : this.listeners) {
            listener.textDismissed(text);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param obj
     */
    public void changeName(long _menu, MENUEditBox obj) {
        menues.UpdateMenuField(menues.ConvertMenuFields(this.control));

        String text = getText();

        for (IEditFieldListener listener : this.listeners) {
            listener.textEntered(text);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getText() {
        return menues.GetFieldText(this.control);
    }

    /**
     * Method description
     *
     *
     * @param text
     *
     * @return
     */
    public String setText(String text) {
        String res = menues.GetFieldText(this.control);

        menues.SetFieldText(this.control, text);
        menues.UpdateMenuField(menues.ConvertMenuFields(this.control));

        return res;
    }

    /**
     * Method description
     *
     *
     * @param text
     */
    public void setSuffix(String text) {
        menues.setSuffixText(this.control, text);
    }

    /**
     * Method description
     *
     */
    public void hide() {
        Helper.setControlShow(this.control, false);
    }

    /**
     * Method description
     *
     */
    public void show() {
        Helper.setControlShow(this.control, true);
    }

    /**
     * @return the mETHOD_CHANGENAME
     */
    public String getMETHOD_CHANGENAME() {
        return METHOD_CHANGENAME;
    }

    /**
     * @return the mETHOD_DISSMIS
     */
    public String getMETHOD_DISSMIS() {
        return METHOD_DISSMIS;
    }
}


//~ Formatted in DD Std on 13/08/26
