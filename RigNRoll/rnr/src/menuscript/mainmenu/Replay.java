/*
 * @(#)Replay.java   13/08/26
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

import rnr.src.menuscript.IPoPUpMenuListener;
import rnr.src.menuscript.PoPUpMenu;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Replay extends Panel {
    private static final String[] PANELS_GROUPS = new String[0];
    private static final String PANELNAME = "REPLAY";
    private static final String[] _BUTTONS = { "Button REPLAY PLAY", "Button REPLAY DELETE" };
    private static final String _TABLE = "TABLEGROUP - REPLAY - 13 40";
    private static final String _RANGER = "Tableranger - REPLAY";
    private static final String _XML_NAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String _LINES = "Tablegroup - ELEMENTS - Replay Lines";
    private static final String[] _ELEMENTS = {
        "REPLAY - ClipName", "REPLAY - ClipName Edit", "REPLAY - ClipDate", "REPLAY - ClipName - QuickRace",
        "REPLAY - ClipName Edit - QuickRace", "REPLAY - ClipDate - QuickRace"
    };
    private static final String CANNOTSHOW_GROUP = "Tablegroup - REPLAY - NO FILES FOUND";
    private static final String CANNOTSHOW_WINDOW = "SINGLE PLAYER - REPLAY - NoFilesFound";
    private PoPUpMenu info_cannotshow = null;
    LoadGameTable table = null;

    Replay(MainMenu menu, IPoPUpMenuListener warn_lst) {
        super(menu, "REPLAY", PANELS_GROUPS);
        this.table = new LoadGameTable(this, menu._menu, _BUTTONS, "TABLEGROUP - REPLAY - 13 40",
                                       "Tableranger - REPLAY", "..\\data\\config\\menu\\menu_MAIN.xml",
                                       "Tablegroup - ELEMENTS - Replay Lines", _ELEMENTS, 7, 0, true,
                                       "BUTTON - REPLAY - SAVEGAMENAME TITLE", "BUTTON - REPLAY - DATE TITLE",
                                       "REPLAY - SCREENSHOT - SAVEGAMENAME");
        this.info_cannotshow = new PoPUpMenu(menu._menu, "..\\data\\config\\menu\\menu_MAIN.xml",
                "Tablegroup - REPLAY - NO FILES FOUND", "SINGLE PLAYER - REPLAY - NoFilesFound");
        this.info_cannotshow.addListener(warn_lst);
    }

    /**
     * Method description
     *
     */
    @Override
    public void init() {
        super.init();
        this.info_cannotshow.afterInit();
        this.table.afterInit(this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void updateWindowContext() {
        super.updateWindowContext();
        this.table.update(this);
    }

    final boolean isClipsListEmpty() {
        return LoadGameTable.isLoadgameListEmpty(7, true);
    }

    final void warnAboutEmptyList() {
        this.info_cannotshow.show();
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
