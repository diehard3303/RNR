/*
 * @(#)Titres.java   13/08/25
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

import rnr.src.rnrcore.eng;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class Titres implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\specmenu.xml";
    private static final String GROUP = "titre";
    private static final String TEXTFIELD = "text";
    private static long last_menu;
    private final String text;

    /**
     * Constructs ...
     *
     *
     * @param text
     */
    public Titres(String text) {
        this.text = text;
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
        menues.InitXml(_menu, "..\\data\\config\\menu\\specmenu.xml", "titre");

        long control = menues.FindFieldInMenu(_menu, "text");

        if (0L != control) {
            menues.SetFieldText(control, this.text);
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
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {
        if (last_menu == _menu) {
            last_menu = 0L;
        } else {
            eng.writeLog("Titre exitMenu with bad code: " + _menu + " Last menu: " + last_menu);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "titreMENU";
    }

    /**
     * Method description
     *
     */
    public static void clearTitres() {
        if (last_menu != 0L) {
            menues.CallMenuCallBack_ExitMenu(last_menu);
            last_menu = 0L;
        }
    }

    /**
     * Method description
     *
     *
     * @param dt
     * @param text
     *
     * @return
     */
    public static long create(float dt, String text) {
        if (last_menu != 0L) {
            menues.CallMenuCallBack_ExitMenu(last_menu);
            last_menu = 0L;
        }

        last_menu = menues.createTitre(new Titres(text), dt);

        return last_menu;
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

    /**
     * @return the textfield
     */
    public static String getTextfield() {
        return TEXTFIELD;
    }
}


//~ Formatted in DD Std on 13/08/25
