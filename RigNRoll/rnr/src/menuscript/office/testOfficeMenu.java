/*
 * @(#)testOfficeMenu.java   13/08/26
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


package rnr.src.menuscript.office;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MenuControls;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.menuscript.PoPUpMenu;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class testOfficeMenu implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String CONTROLS_MAIN = "OFFICE";
    private static final String XML_WARNING_HAS_DEPT = "..\\data\\config\\menu\\menu_unsettled_debt.xml";
    private static final String CONTROLS_WARNING_HASDEPT = "Message Debt";
    private PoPUpMenu warning_has_dept = null;
    MenuControls allmenu = null;

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
     * @return
     */
    public static long create() {
        return menues.createSimpleMenu(new testOfficeMenu(), 1000000.0D, "ESC", 1600, 1200, 1600, 1200, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        this.allmenu = new MenuControls(_menu, "..\\data\\config\\menu\\menu_office.xml", "OFFICE");
        this.warning_has_dept = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_unsettled_debt.xml", "Message Debt",
                "Message Debt", false);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        this.warning_has_dept.afterInit();
        this.warning_has_dept.show();
        menues.setShowMenu(_menu, true);
        menues.SetStopWorld(_menu, true);
        menues.WindowSet_ShowCursor(_menu, true);

        long control = menues.FindFieldInMenu(_menu, "MF - My Fleet - Tableranger");
        MENU_ranger ranger = menues.ConvertRanger(control);

        ranger.max_value = 3000;
        ranger.min_value = 0;
        ranger.current_value = 1500;
        menues.UpdateField(ranger);
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
        return "officeMENU";
    }
}


//~ Formatted in DD Std on 13/08/26
