/*
 * @(#)menuBridgeToll.java   13/08/26
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

import rnr.src.menu.JavaEvents;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.MenuAfterInitNarrator;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.rnrscr.ILeaveMenuListener;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class menuBridgeToll implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_documents.xml";
    private static final String GROUP = "Message BRIDGE TOLL";

    /** Field description */
    public double toll_money = 12.0D;
    private ILeaveMenuListener m_leave_menu_listener = null;

    /**
     * Constructs ...
     *
     *
     * @param listener
     */
    public menuBridgeToll(ILeaveMenuListener listener) {
        this.m_leave_menu_listener = listener;
    }

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
        menues.InitXml(_menu, "..\\data\\config\\menu\\menu_documents.xml", "Message BRIDGE TOLL");
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        long control = menues.FindFieldInMenu(_menu, "Message Bridge Toll - MONEY");

        if (control != 0L) {
            MENUText_field field = menues.ConvertTextFields(control);

            if (field != null) {
                JavaEvents.SendEvent(71, 0, this);

                KeyPair[] key = { new KeyPair("MONEY", "" + Converts.ConvertNumeric((int) this.toll_money)) };

                MacroKit.ApplyToTextfield(field, key);
            }
        }

        MenuAfterInitNarrator.justShow(_menu);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {
        this.m_leave_menu_listener.menuLeaved();
        JavaEvents.SendEvent(71, 1, this);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "bridgeTallMENU";
    }

    /**
     * Method description
     *
     *
     * @param listener
     *
     * @return
     */
    public static long CreateBridgeTollMenu(ILeaveMenuListener listener) {
        return menues.createSimpleMenu(new menuBridgeToll(listener), 1, 2.0D, "", 1600, 1200, 1600, 1200, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }
}


//~ Formatted in DD Std on 13/08/26
