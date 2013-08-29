/*
 * @(#)MainMenu.java   13/08/26
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

import rnr.src.menu.menues;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class MainMenu {
    String XML_FILE = "..\\data\\config\\menu\\";
    protected final long animid_FADEOUT = 1L;
    protected final long animid_FADEIN = 2L;
    protected final int animid_SLIDER = 3;
    protected final int animid_FOLD = 4;
    protected final int animid_UNFOLD = 5;
    protected final long animid_FADEOUTHEAD = 6L;
    protected final long animid_FADEINHEAD = 7L;
    final long animid_FADEOUTDIALOG = 8L;
    final long animid_FADEINDIALOG = 9L;

    /** Field description */
    public float PERIODFADEOUT = 0.2F;

    /** Field description */
    public float PERIODFADEIN = 0.4F;

    /** Field description */
    public float PERIODFOLD = 0.2F;

    /** Field description */
    public float PERIODUNFOLD = 0.4F;
    protected long _menu = 0L;
    protected HashMap<String, String> panelDialogStates = new HashMap();

    MainMenu(String file) {
        this.XML_FILE += file;
    }

    long[] loadGroup(String groupname) {
        return menues.InitXml(this._menu, this.XML_FILE, groupname);
    }

    long findField(String fieldName) {
        return menues.FindFieldInMenu(this._menu, fieldName);
    }

    /**
     * Method description
     *
     *
     * @param cb
     */
    public void OnDialogOpen(IFadeOutFadeIn cb) {}

    /**
     * Method description
     *
     */
    public void OnDialogOpenImmediate() {}

    /**
     * Method description
     *
     *
     * @param cb
     */
    public void OnDialogClose(IFadeOutFadeIn cb) {}

    void resetToDefaulScreen() {}

    /**
     * Method description
     *
     *
     * @param panelname
     * @param dialogname
     */
    public void rememberPanelState(String panelname, String dialogname) {
        this.panelDialogStates.put(panelname, dialogname);
    }

    /**
     * Method description
     *
     *
     * @param panelname
     */
    public void forgetPanelState(String panelname) {
        this.panelDialogStates.remove(panelname);
    }
}


//~ Formatted in DD Std on 13/08/26
