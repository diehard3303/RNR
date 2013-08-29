/*
 * @(#)SinglePlayerLoadGame.java   13/08/26
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class SinglePlayerLoadGame extends PanelDialog {
    private static final String[] _BUTTONS = { "SINGLE PLAYER - LOAD GAME LOAD", "SINGLE PLAYER - LOAD GAME DELETE" };
    private static final String _TABLE = "TABLEGROUP - SINGLE PLAYER - LOAD GAME - 15 40";
    private static final String _RANGER = "Tableranger - SINGLE PLAYER - LOAD GAME";
    private static final String _XML_NAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String _LINES = "Tablegroup - ELEMENTS - SaveGame Lines";
    private static final String[] _ELEMENTS = { "SINGLE PLAYER - LOAD GAME - SaveGameName",
            "SINGLE PLAYER - LOAD GAME - SaveGameName Edit", "SAVEGAME - DATE" };
    private static final int typeOfAGame = 1;
    LoadGameTable table = null;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param controls
     * @param window
     * @param exitButton
     * @param defaultButton
     * @param okButton
     * @param applyButton
     * @param parent
     */
    public SinglePlayerLoadGame(long _menu, long[] controls, long window, long exitButton, long defaultButton,
                                long okButton, long applyButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, applyButton, parent);
        this.table = new LoadGameTable(this, _menu, _BUTTONS, "TABLEGROUP - SINGLE PLAYER - LOAD GAME - 15 40",
                                       "Tableranger - SINGLE PLAYER - LOAD GAME",
                                       "..\\data\\config\\menu\\menu_MAIN.xml",
                                       "Tablegroup - ELEMENTS - SaveGame Lines", _ELEMENTS, 1, 0, false,
                                       "BUTTON - SINGLE PLAYER - LOAD GAME - SAVEGAMENAME TITLE",
                                       "BUTTON - SINGLE PLAYER - LOAD GAME - DATE TITLE",
                                       "SINGLE PLAYER - LOAD GAME - SCREENSHOT - SAVEGAMENAME");
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
    public void update() {
        super.update();
        this.table.update(this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
        this.table.deinit();
        super.exitMenu();
    }

    /**
     * Method description
     *
     */
    @Override
    public void readParamValues() {
        this.table.readParamValues();
    }
}


//~ Formatted in DD Std on 13/08/26
