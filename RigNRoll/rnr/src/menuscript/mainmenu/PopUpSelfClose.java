/*
 * @(#)PopUpSelfClose.java   13/08/26
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

import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.SMenu;
import rnr.src.menu.menues;
import rnr.src.menuscript.PoPUpMenu;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class PopUpSelfClose extends PoPUpMenu {
    private static final double TIME = 15.0D;
    private static final String COUNTDOUN = "PopUpWarning - COUNTDOWN";
    private long countdownTextField = 0L;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param xmlFile
     * @param controlGroup
     * @param windowName
     */
    public PopUpSelfClose(long _menu, String xmlFile, String controlGroup, String windowName) {
        super(_menu, xmlFile, controlGroup, windowName);
        this.countdownTextField = menues.FindFieldInMenu(_menu, "PopUpWarning - COUNTDOWN");
    }

    private void updateCountdown(int time) {
        MENUText_field tf = menues.ConvertTextFields(this.countdownTextField);

        if (null != tf) {
            KeyPair[] keys = { new KeyPair("SECONDS", "" + time) };

            MacroKit.ApplyToTextfield(tf, keys);
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void show() {
        menues.SetScriptObjectAnimation(0L, 299L, this, "animateClose");
        super.show();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnCancel(long _menu, MENUsimplebutton_field field) {
        stopCloseAnimating();
        super.OnCancel(_menu, field);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnExit(long _menu, SMenu field) {
        stopCloseAnimating();
        super.OnExit(_menu, field);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnOk(long _menu, MENUsimplebutton_field field) {
        stopCloseAnimating();
        super.OnOk(_menu, field);
    }

    private void stopCloseAnimating() {
        menues.StopScriptAnimation(299L);
    }

    void animateClose(long _menu, double time) {
        if (time > 15.0D) {
            OnCancel(0L, null);

            return;
        }

        updateCountdown((int) (15.0D - time));
    }
}


//~ Formatted in DD Std on 13/08/26
