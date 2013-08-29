/*
 * @(#)EscapeSaveLoad.java   13/08/26
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
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.menues;
import rnr.src.menuscript.ConfirmDialog;
import rnr.src.menuscript.IPoPUpMenuListener;
import rnr.src.menuscript.PoPUpMenu;
import rnr.src.menuscript.WindowParentMenu;
import rnr.src.rnrcore.Log;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class EscapeSaveLoad extends WindowParentMenu implements IWindowContext {
    private static final String FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String MENU = "SAVE LOAD GAME";
    private static final String[] _BUTTONS = { "BUTT - SAVE/LOAD - LOAD", "BUTT - SAVE/LOAD - DELETE" };
    private static final String _TABLE = "TABLEGROUP - GAME - NAME - 9 36";
    private static final String _RANGER = "SAVE/LOAD LIST - Tableranger";
    private static final String _XML_NAME = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String _LINES = "Tablegroup - ELEMENTS - SaveGame Lines";
    private static final String[] _ELEMENTS = { "SaveGameName", "SaveGameName Edit", "SaveGameTime" };
    private static final int typeOfAGame = 1;
    private static final int typeOfSave = 4;
    private static final String[] LOCAL_BUTTONS = { "BUTT - SAVE/LOAD - SAVE" };
    private static final String[] LOCAL_METHODS = { "onSave" };
    private static final String REPLACE_GROUP = "Tablegroup - CONFIRM REPLACE";
    private static final String REPLACE_WINDOW = "CONFIRM REPLACE";
    private static final String REPLACE_TEXT = "REPLACE";
    private static final String REPLACE_TEXT_KEY = "PROFILENAME";
    private static final String ENTERNAME_GROUP = "Tablegroup - SAVELOAD - ENTER GAME NAME";
    private static final String ENTERNAME_WINDOW = "ENTER GAME NAME";
    private static final int REPLACE_CONFIRM = 1;
    private static final int ENTERNAME_OK = 2;
    private PoPUpMenu warning_replace = null;
    private PoPUpMenu warning_entername = null;
    private long replace_text = 0L;
    private LoadGameTable table = null;
    private EscapeNewSaveGame savegame = null;
    private String pendedname = null;
    private String replace_text_store;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param common
     * @param dialog
     */
    public EscapeSaveLoad(long _menu, Common common, ConfirmDialog dialog) {
        super(_menu, "..\\data\\config\\menu\\menu_esc.xml", "SAVE LOAD GAME");
        this.common = common;

        for (int i = 0; i < LOCAL_BUTTONS.length; ++i) {
            Object field = menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, LOCAL_BUTTONS[i]));

            menues.SetScriptOnControl(_menu, field, this, LOCAL_METHODS[i], 4L);
        }

        this.table = new LoadGameTable(this, _menu, _BUTTONS, "TABLEGROUP - GAME - NAME - 9 36",
                                       "SAVE/LOAD LIST - Tableranger", "..\\data\\config\\menu\\menu_esc.xml",
                                       "Tablegroup - ELEMENTS - SaveGame Lines", _ELEMENTS, 1, 1, false,
                                       "SAVE/LOAD LIST - FILENAME TITLE", "SAVE/LOAD LIST - DATE TITLE",
                                       "GAME - SCREENSHOT - TITLE");
        this.savegame = new EscapeNewSaveGame(_menu, this.table, this);
        this.warning_replace = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_esc.xml",
                "Tablegroup - CONFIRM REPLACE", "CONFIRM REPLACE");
        this.warning_replace.addListener(new InWarning(1));
        this.replace_text = this.warning_replace.getField("REPLACE");
        this.warning_entername = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_esc.xml",
                "Tablegroup - SAVELOAD - ENTER GAME NAME", "ENTER GAME NAME");
        this.warning_entername.addListener(new InWarning(2));

        if (this.replace_text != 0L) {
            this.replace_text_store = menues.GetFieldText(this.replace_text);
        }

        SaveLoadCommonManagement.getSaveLoadCommonManager().OnEnterESCmenu();
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        super.afterInit();
        this.table.afterInit(this);
        this.warning_replace.afterInit();
        this.warning_entername.afterInit();
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
    public void SaveGame() {
        this.pendedname = this.table.GetSelectedMediaName();

        if (this.pendedname == null) {
            if (!(this.savegame.isGoodName())) {
                this.warning_entername.show();

                return;
            }

            this.pendedname = this.savegame.getSaveName();
        }

        if (!(SaveLoadCommonManagement.getSaveLoadCommonManager().SetSaveGameFlag(this.pendedname, 1, 4))) {
            if (this.replace_text != 0L) {
                KeyPair[] keys = new KeyPair[1];

                keys[0] = new KeyPair("PROFILENAME", this.pendedname);
                menues.SetFieldText(this.replace_text, MacroKit.Parse(this.replace_text_store, keys));
                menues.UpdateMenuField(menues.ConvertMenuFields(this.replace_text));
            }

            this.warning_replace.show();
        } else {
            exitWindowContext();
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onSave(long _menu, MENUsimplebutton_field button) {
        SaveGame();
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

    class InWarning implements IPoPUpMenuListener {
        private final int type;

        InWarning(int paramInt) {
            this.type = paramInt;
        }

        /**
         * Method description
         *
         */
        @Override
        public void onAgreeclose() {
            switch (this.type) {
             case 1 :
                 SaveLoadCommonManagement.getSaveLoadCommonManager().DeleteExistsMedia(EscapeSaveLoad.this.pendedname,
                         1, 4);

                 if (!(SaveLoadCommonManagement.getSaveLoadCommonManager().SetSaveGameFlag(
                         EscapeSaveLoad.this.pendedname, 1, 4))) {
                     Log.menu("ERRORR. Bad behaivoir on ne save game named " + EscapeSaveLoad.this.pendedname);
                 }

                 EscapeSaveLoad.this.hide();
             case 2 :
            }
        }

        /**
         * Method description
         *
         */
        @Override
        public void onCancel() {}

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
    }
}


//~ Formatted in DD Std on 13/08/26
