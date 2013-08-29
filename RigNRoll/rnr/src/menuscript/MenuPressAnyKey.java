/*
 * @(#)MenuPressAnyKey.java   13/08/26
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

import rnr.src.menu.MenuAfterInitNarrator;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class MenuPressAnyKey implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\SPECMENU.XML";
    private static final String GROUP = "PRESS ANY KEY";
    private static long this_menu = 0L;
    private long _menu = 0L;

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
     */
    public static void createPauseMenu() {
        if (0L != this_menu) {
            return;
        }

        this_menu = menues.createSimpleMenu(new MenuPressAnyKey(), 5);
    }

    /**
     * Method description
     *
     */
    public static void deletePauseMenu() {
        if (0L == this_menu) {
            return;
        }

        long menu = this_menu;

        this_menu = 0L;
        menues.CallMenuCallBack_ExitMenu(menu);
    }

    /**
     * Method description
     *
     *
     * @param menu
     */
    @Override
    public void InitMenu(long menu) {
        this._menu = menu;
        menues.InitXml(this._menu, "..\\data\\config\\menu\\SPECMENU.XML", "PRESS ANY KEY");
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
        return "pressanykeyMENU";
    }
}


//~ Formatted in DD Std on 13/08/26
