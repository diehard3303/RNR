/*
 * @(#)BillOfLadingMenu.java   13/08/26
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
import rnr.src.players.IdentiteNames;
import rnr.src.rnrcore.eng;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class BillOfLadingMenu implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_documents.xml";
    private static final String GROUP = "DOCUMENT - BILL OF LADING";

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        menues.InitXml(_menu, "..\\data\\config\\menu\\menu_documents.xml", "DOCUMENT - BILL OF LADING");
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        long control = menues.FindFieldInMenu(_menu, "DOCUMENT - Bill RIGHT - NAME VALUE");

        if (control != 0L) {
            MENUText_field field = menues.ConvertTextFields(control);

            if (field != null) {
                IdentiteNames info = new IdentiteNames("SC_FBI");

                if (!(eng.noNative)) {
                    JavaEvents.SendEvent(57, 1, info);
                }

                if ((info.firstName != null) && (info.lastName != null)) {
                    KeyPair[] key = { new KeyPair("STEVEN_CUNNING", info.firstName + " " + info.lastName) };

                    MacroKit.ApplyToTextfield(field, key);
                }
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
    public void exitMenu(long _menu) {}

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
    @Override
    public String getMenuId() {
        return "scenarioBillOfLadingMENU";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static long create() {
        return menues.createSimpleMenu(new BillOfLadingMenu(), 1);
    }

    /**
     * @return the xml
     */
    public static String getXml() {
        return XML;
    }

    /**
     * @return the group
     */
    public static String getGroup() {
        return GROUP;
    }
}


//~ Formatted in DD Std on 13/08/26
