/*
 * @(#)PastFewDaysMenu.java   13/08/25
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

import rnr.src.rnrcore.Helper;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.loc;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class PastFewDaysMenu extends TypicalAnm implements menucreation {
    String text_ref = "specmenu.xml\\BLACK SCREEN and TITRES\\BLACK SCREEN and TITRES\\text";
    double time_show = 4.0D;
    long last_menu;

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void restartMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        this.last_menu = _menu;
        menues.InitXml(_menu, "..\\data\\config\\menu\\specmenu.xml", "BLACK SCREEN and TITRES");

        long control = menues.FindFieldInMenu(_menu, "text");

        if (0L != control) {
            menues.SetFieldText(control, loc.getMENUString(this.text_ref));
        } else {
            eng.err("ERRORR. Cannot find control text in xml ..\\data\\config\\menu\\specmenu.xml");
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        MenuAfterInitNarrator.justShow(_menu);
        eng.CreateInfinitScriptAnimation(this);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "blackscreentitresMENU";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static long create() {
        long last_menu = menues.createSimpleMenu(new PastFewDaysMenu());

        return last_menu;
    }

    /**
     * Method description
     *
     *
     * @param dt
     *
     * @return
     */
    @Override
    public boolean animaterun(double dt) {
        if (dt >= this.time_show) {
            menues.CallMenuCallBack_ExitMenu(this.last_menu);
            Helper.peekNativeMessage("resumescript");

            return true;
        }

        return false;
    }
}


//~ Formatted in DD Std on 13/08/25
