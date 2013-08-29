/*
 * @(#)EscapeMainMenu.java   13/08/26
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
import rnr.src.menu.JavaEvents;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.menues;
import rnr.src.menuscript.mainmenu.SaveLoadCommonManagement;
import rnr.src.rnrcore.Log;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class EscapeMainMenu extends WindowParentMenu implements ISubMenu {
    private static final String[] BUTTONS = { "BUTT - MAINMENU - YES", "BUTT - MAINMENU - CANCEL" };
    private static final String[] BUTTONS_METHODS = { "quitMainMenu", "mainmenuCancel" };
    private static final String FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String MENU = "MAIN MENU";
    long _menu;

    EscapeMainMenu(long _menu) {
        super(_menu, "..\\data\\config\\menu\\menu_esc.xml", "MAIN MENU");
        this._menu = _menu;

        if (BUTTONS.length != BUTTONS_METHODS.length) {
            Log.menu("EscapeMainMenu has bad initializers: BUTTONS.length!=BUTTONS_METHODS.length");
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        this.common = new Common(_menu);

        for (int i = 0; i < BUTTONS.length; ++i) {
            this.common.SetScriptOnButton(BUTTONS[i], this, BUTTONS_METHODS[i]);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void quitMainMenu(long _menu, MENUsimplebutton_field button) {
        menues.CallMenuCallBack_ExitMenu(_menu);
        JavaEvents.SendEvent(23, 1, this);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void mainmenuCancel(long _menu, MENUsimplebutton_field button) {}

    /**
     * Method description
     *
     */
    @Override
    public void Activate() {
        if (!(SaveLoadCommonManagement.getSaveLoadCommonManager().IsTheCurrentGameSaved())) {
            show();
        } else {
            menues.CallMenuCallBack_ExitMenu(this._menu);
            JavaEvents.SendEvent(23, 1, this);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
