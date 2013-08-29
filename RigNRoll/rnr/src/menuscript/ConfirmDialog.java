/*
 * @(#)ConfirmDialog.java   13/08/26
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

import rnr.src.menu.Common;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.SMenu;
import rnr.src.menu.menues;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class ConfirmDialog {
    ParentMenu m_curmenu;
    SMenu m_warnwindow;
    String m_warntext;
    MENUText_field m_warntextfield;

    /**
     * Constructs ...
     *
     *
     * @param common
     * @param menuname
     * @param textname
     * @param yesbutton
     * @param nobutton
     * @param cancelbutton
     */
    public ConfirmDialog(Common common, String menuname, String textname, String yesbutton, String nobutton,
                         String cancelbutton) {
        this.m_warnwindow = common.FindWindow(menuname);
        this.m_warntextfield = common.FindTextField(textname);

        if (this.m_warntextfield != null) {
            this.m_warntext = this.m_warntextfield.text;
        }

        if (yesbutton != null) {
            MENUsimplebutton_field button = common.FindSimpleButton(yesbutton);

            if (button != null) {
                menues.SetScriptOnControl(common.GetMenu(), button, this, "OnYes", 4L);
            }
        }

        if (nobutton != null) {
            MENUsimplebutton_field button = common.FindSimpleButton(nobutton);

            if (button != null) {
                menues.SetScriptOnControl(common.GetMenu(), button, this, "OnNo", 4L);
            }
        }

        if (cancelbutton != null) {
            MENUsimplebutton_field button = common.FindSimpleButton(cancelbutton);

            if (button != null) {
                menues.SetScriptOnControl(common.GetMenu(), button, this, "OnCancel", 4L);
            }
        }

        if (this.m_warnwindow != null) {
            menues.SetScriptOnControl(common.GetMenu(), this.m_warnwindow, this, "OnExit", 17L);
        }
    }

    /**
     * Method description
     *
     *
     * @param menu
     * @param text
     */
    public void AskUser(ParentMenu menu, String text) {
        if (this.m_warntextfield != null) {
            if (text == null) {
                text = this.m_warntext;
            }

            this.m_warntextfield.text = this.m_warntext;
            menues.UpdateField(this.m_warntextfield);
        }

        this.m_curmenu = menu;
        menues.SetShowField(this.m_warnwindow.nativePointer, true);
    }

    void OnYes(long _menu, MENUsimplebutton_field button) {
        menues.SetShowField(this.m_warnwindow.nativePointer, false);
        this.m_curmenu.PassUserDecision(0);
    }

    void OnNo(long _menu, MENUsimplebutton_field button) {
        menues.SetShowField(this.m_warnwindow.nativePointer, false);
        this.m_curmenu.PassUserDecision(1);
    }

    void OnCancel(long _menu, MENUsimplebutton_field button) {
        menues.SetShowField(this.m_warnwindow.nativePointer, false);
        this.m_curmenu.PassUserDecision(2);
    }

    void OnExit(long _menu, SMenu wnd) {
        menues.SetShowField(this.m_warnwindow.nativePointer, false);
        this.m_curmenu.PassUserDecision(2);
    }
}


//~ Formatted in DD Std on 13/08/26
