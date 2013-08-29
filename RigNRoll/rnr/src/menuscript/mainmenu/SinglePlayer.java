/*
 * @(#)SinglePlayer.java   13/08/26
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

import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menuscript.IPoPUpMenuListener;
import rnr.src.menuscript.PoPUpMenu;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class SinglePlayer extends Panel {
    private static final String[] PANELS_GROUPS = { "SINGLE PLAYER - NEW GAME", "SINGLE PLAYER - LOAD GAME",
            "SINGLE PLAYER - GAME OPTIONS" };
    private static final String PANELNAME = "SINGLE PLAYER";
    private static final int LOAD_GAME_PANEL = 1;
    private static final String XML_NAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String CANNOTSHOW_GROUP = "Tablegroup - SINGLE PLAYER - LOAD GAME - NO FILES FOUND";
    private static final String CANNOTSHOW_WINDOW = "SINGLE PLAYER - LOAD GAME - NoFilesFound";
    private PoPUpMenu info_cannotshow = null;

    SinglePlayer(MainMenu menu) {
        super(menu, "SINGLE PLAYER", PANELS_GROUPS);
        add(PANELS_GROUPS[0],
            new SinglePlayerNewGame(menu._menu, menu.loadGroup(PANELS_GROUPS[0]), menu.findField(PANELS_GROUPS[0]),
                                    menu.findField(PANELS_GROUPS[0] + " Exit"),
                                    menu.findField(PANELS_GROUPS[0] + " DEFAULT"),
                                    menu.findField(PANELS_GROUPS[0] + " OK"), 0L, this));
        add(PANELS_GROUPS[1],
            new SinglePlayerLoadGame(menu._menu, menu.loadGroup(PANELS_GROUPS[1]), menu.findField(PANELS_GROUPS[1]),
                                     menu.findField(PANELS_GROUPS[1] + " Exit"),
                                     menu.findField(PANELS_GROUPS[1] + " DEFAULT"),
                                     menu.findField(PANELS_GROUPS[1] + " OK"), 0L, this));
        add(PANELS_GROUPS[2],
            new SinglePlayerGameOptions(menu._menu, menu.loadGroup(PANELS_GROUPS[2]), menu.findField(PANELS_GROUPS[2]),
                                        menu.findField(PANELS_GROUPS[2] + " Exit"),
                                        menu.findField(PANELS_GROUPS[2] + " DEFAULT"),
                                        menu.findField(PANELS_GROUPS[2] + " OK"), 0L, this, 0, false));
        this.info_cannotshow = new PoPUpMenu(menu._menu, "..\\data\\config\\menu\\menu_MAIN.xml",
                "Tablegroup - SINGLE PLAYER - LOAD GAME - NO FILES FOUND", "SINGLE PLAYER - LOAD GAME - NoFilesFound");
        this.info_cannotshow.addListener(new InWarning());
    }

    @Override
    void init() {
        super.init();
        this.info_cannotshow.afterInit();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void OnAction(long _menu, MENUsimplebutton_field button) {
        String name_dialog = this.dials_on_buttons.get(new Integer(button.userid));

        if (name_dialog.compareTo(PANELS_GROUPS[1]) == 0) {
            if (!(LoadGameTable.isLoadgameListEmpty(1, false))) {
                super.OnAction(_menu, button);
            } else {
                this.info_cannotshow.show();
            }
        } else {
            super.OnAction(_menu, button);
        }
    }

    class InWarning implements IPoPUpMenuListener {

        /**
         * Method description
         *
         */
        @Override
        public void onAgreeclose() {}

        /**
         * Method description
         *
         */
        @Override
        public void onClose() {}

        /**
         * Method description
         *
         */
        @Override
        public void onOpen() {}

        /**
         * Method description
         *
         */
        @Override
        public void onCancel() {}
    }
}


//~ Formatted in DD Std on 13/08/26
