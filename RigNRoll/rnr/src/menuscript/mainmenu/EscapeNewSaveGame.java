/*
 * @(#)EscapeNewSaveGame.java   13/08/26
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

import rnr.src.rnrcore.loc;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class EscapeNewSaveGame implements INewSaveGameLine {
    private static final String TEXT = loc.getMENUString("common\\[EMPTY]");
    private String SAVE_GAME_NAME = null;
    private String SAVE_GAME_NAME_FIRST = null;
    EscapeSaveLoad menu = null;
    private boolean first_time = true;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param table
     * @param __menu
     */
    public EscapeNewSaveGame(long _menu, LoadGameTable table, EscapeSaveLoad __menu) {
        SaveLoadCommonManagement.Media game_media = new SaveLoadCommonManagement.Media();

        game_media.game_type = 1;
        game_media.media_name = TEXT;
        game_media.media_time = SaveLoadCommonManagement.getSaveLoadCommonManager().GetCurrentMediaTime();
        this.menu = __menu;
        table.addTABLEDATAline(this, game_media);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getSaveName() {
        return this.SAVE_GAME_NAME;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isGoodName() {
        return ((this.SAVE_GAME_NAME != null) && (this.SAVE_GAME_NAME.compareTo("") != 0)
                && (this.SAVE_GAME_NAME.compareTo(this.SAVE_GAME_NAME_FIRST) != 0));
    }

    /**
     * Method description
     *
     *
     * @param data
     *
     * @return
     */
    @Override
    public boolean canDelete(SaveLoadCommonManagement.Media data) {
        return false;
    }

    /**
     * Method description
     *
     *
     * @param data
     *
     * @return
     */
    @Override
    public boolean canLoad(SaveLoadCommonManagement.Media data) {
        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isPersistant() {
        return true;
    }

    /**
     * Method description
     *
     *
     * @param data
     * @param oldName
     * @param newName
     *
     * @return
     */
    @Override
    public boolean rename(SaveLoadCommonManagement.Media data, String oldName, String newName) {
        if (null == this.SAVE_GAME_NAME) {
            this.SAVE_GAME_NAME = oldName;
        }

        if (newName.compareToIgnoreCase("") == 0) {
            return false;
        }

        if (this.SAVE_GAME_NAME.compareToIgnoreCase(newName) != 0) {
            this.SAVE_GAME_NAME = newName;
            data.media_name = this.SAVE_GAME_NAME_FIRST;
            this.menu.SaveGame();

            return true;
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param data
     * @param newname
     *
     * @return
     */
    @Override
    public boolean dismiss(SaveLoadCommonManagement.Media data, String newname) {
        data.media_name = this.SAVE_GAME_NAME_FIRST;

        if (newname.compareTo("") == 0) {
            this.SAVE_GAME_NAME = this.SAVE_GAME_NAME_FIRST;
        } else {
            this.SAVE_GAME_NAME = newname;
        }

        return true;
    }

    /**
     * Method description
     *
     *
     * @param data
     *
     * @return
     */
    @Override
    public boolean clearOnEnterEdit(SaveLoadCommonManagement.Media data) {
        if ((this.first_time) || (this.SAVE_GAME_NAME_FIRST.compareToIgnoreCase(data.media_name) == 0)) {
            this.SAVE_GAME_NAME_FIRST = data.media_name;
            this.SAVE_GAME_NAME = "";
            this.first_time = false;

            return true;
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param data
     *
     * @return
     */
    @Override
    public boolean canBeDeleted(SaveLoadCommonManagement.Media data) {
        return false;
    }

    /**
     * Method description
     *
     *
     * @param data
     *
     * @return
     */
    @Override
    public boolean canBeLoaded(SaveLoadCommonManagement.Media data) {
        return false;
    }

    /**
     * Method description
     *
     *
     * @param data
     *
     * @return
     */
    @Override
    public boolean isMediaCurrent(SaveLoadCommonManagement.Media data) {
        return true;
    }
}


//~ Formatted in DD Std on 13/08/26
