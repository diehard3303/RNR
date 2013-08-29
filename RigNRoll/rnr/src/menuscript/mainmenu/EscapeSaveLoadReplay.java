/*
 * @(#)EscapeSaveLoadReplay.java   13/08/26
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

import rnr.src.menu.Common;
import rnr.src.menuscript.ConfirmDialog;
import rnr.src.menuscript.WindowParentMenu;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class EscapeSaveLoadReplay extends WindowParentMenu implements IWindowContext {
    private static final String FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String MENU = "LOAD CLIP";
    private static final String[] _BUTTONS = { "BUTT - CLIPS - PLAY", "BUTT - CLIPS - DELETE" };
    private static final String _TABLE = "TABLEGROUP - CLIP - NAME - 9 36";
    private static final String _RANGER = "LOAD CLIP LIST - Tableranger";
    private static final String _XML_NAME = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String _LINES = "Tablegroup - ELEMENTS - CLIPS Lines";
    private static final String[] _ELEMENTS = {
        "CLIPS - CLIPNAME", "CLIPS - CLIPNAME Edit", "CLIPS - CLIPDATE", "CLIPS - CLIPNAME - QuickRace",
        "CLIPS - CLIPNAME Edit - QuickRace", "CLIPS - CLIPDATE - QuickRace"
    };
    private LoadGameTable table = null;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param common
     * @param dialog
     */
    public EscapeSaveLoadReplay(long _menu, Common common, ConfirmDialog dialog) {
        super(_menu, "..\\data\\config\\menu\\menu_esc.xml", "LOAD CLIP");
        this.common = common;
        this.table = new LoadGameTable(this, _menu, _BUTTONS, "TABLEGROUP - CLIP - NAME - 9 36",
                                       "LOAD CLIP LIST - Tableranger", "..\\data\\config\\menu\\menu_esc.xml",
                                       "Tablegroup - ELEMENTS - CLIPS Lines", _ELEMENTS, 7, 1, true,
                                       "LOAD CLIP LIST - CLIPNAME TITLE", "LOAD CLIP LIST - DATE TITLE",
                                       "CLIP - SCREENSHOT - TITLE");
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        super.afterInit();
        this.table.afterInit(this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitWindowContext() {
        hide();
    }

    /**
     * Method description
     *
     */
    public void closeMainWindowOfWindowContext() {}

    /**
     * Method description
     *
     */
    @Override
    public void Activate() {
        updateWindowContext();
        super.Activate();
    }

    /**
     * Method description
     *
     */
    @Override
    public void updateWindowContext() {
        this.table.update(this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
        super.exitMenu();
        this.table.deinit();
    }
}


//~ Formatted in DD Std on 13/08/26
