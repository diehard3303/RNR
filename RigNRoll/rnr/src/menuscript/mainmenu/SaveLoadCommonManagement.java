/*
 * @(#)SaveLoadCommonManagement.java   13/08/26
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

import rnr.src.menu.JavaEvents;
import rnr.src.menuscript.mainmenu.SaveLoadCommonManagement.Media;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class SaveLoadCommonManagement {
    private static SaveLoadCommonManagement instance = null;

    /** Field description */
    public static final int type_Single = 1;

    /** Field description */
    public static final int type_Multi = 2;

    /** Field description */
    public static final int type_QRace = 4;

    /** Field description */
    public static final int type_QuickSave = 1;

    /** Field description */
    public static final int type_AutoSave = 2;

    /** Field description */
    public static final int type_Save = 4;

    /** Field description */
    public static final int type_Clip = 8;
    private final Vector<Media> ret_exits_files = new Vector<Media>();

    /** Field description */
    public MediaTime out_time = new MediaTime();

    /** Field description */
    public int in_game_type = 0;

    /** Field description */
    public int in_media_type = 0;

    /** Field description */
    public int in_sort_type = 0;

    /** Field description */
    public boolean in_sort_dir = true;

    /** Field description */
    public boolean bRet = false;

    /** Field description */
    public String in_name0;

    /** Field description */
    public String in_name1;

    /** Field description */
    public String out_string;

    /**
     * Method description
     *
     *
     * @return
     */
    public static SaveLoadCommonManagement getSaveLoadCommonManager() {
        if (null == instance) {
            instance = new SaveLoadCommonManagement();
        }

        return instance;
    }

    Vector<Media> GetExistsMedia(int GameType, int mediaType, int sort_type, boolean sortUp) {
        this.in_game_type = GameType;
        this.in_media_type = mediaType;
        this.in_sort_type = sort_type;
        this.in_sort_dir = sortUp;
        JavaEvents.SendEvent(25, 0, this);

        Vector<Media> res = (Vector<Media>) this.ret_exits_files.clone();

        this.ret_exits_files.clear();

        return res;
    }

    void DeleteExistsMedia(String name, int GameType, int mediaType) {
        this.in_game_type = GameType;
        this.in_media_type = mediaType;
        this.in_name0 = name;
        JavaEvents.SendEvent(25, 1, this);
    }

    boolean RenameExistsMedia(String name, int GameType, int mediaType, String new_name) {
        this.in_game_type = GameType;
        this.in_media_type = mediaType;
        this.in_name0 = name;
        this.in_name1 = new_name;
        JavaEvents.SendEvent(25, 2, this);

        return this.bRet;
    }

    void SetStartNewGameFlag(int GameType) {
        this.in_game_type = GameType;
        JavaEvents.SendEvent(25, 3, this);
    }

    void SetLoadGameFlagFromESCMenu(String exists_media, int GameType, int mediaType) {
        this.in_game_type = GameType;
        this.in_name0 = exists_media;
        this.in_media_type = mediaType;
        JavaEvents.SendEvent(25, 4, this);
    }

    void SetLoadGameFlagFromMainMenu(String exists_media, int GameType, int mediaType) {
        this.in_game_type = GameType;
        this.in_name0 = exists_media;
        this.in_media_type = mediaType;
        JavaEvents.SendEvent(25, 5, this);
    }

    void SetRestartGameFlag() {
        JavaEvents.SendEvent(25, 6, this);
    }

    boolean SetSaveGameFlag(String exists_media, int GameType, int mediaType) {
        this.in_game_type = GameType;
        this.in_name0 = exists_media;
        this.in_media_type = mediaType;
        JavaEvents.SendEvent(25, 7, this);

        return this.bRet;
    }

    void SetQuitToMainMenuFlag() {
        JavaEvents.SendEvent(25, 8, this);
    }

    void UpdateShot(String exists_media, int GameType, int mediaType) {
        this.in_game_type = GameType;
        this.in_name0 = exists_media;
        this.in_media_type = mediaType;
        JavaEvents.SendEvent(25, 9, this);
    }

    void UpdateShotByCurrent() {
        JavaEvents.SendEvent(25, 10, this);
    }

    MediaTime GetCurrentMediaTime() {
        JavaEvents.SendEvent(25, 11, this);

        return this.out_time;
    }

    /**
     * Method description
     *
     */
    public void OnEnterESCmenu() {
        JavaEvents.SendEvent(25, 13, this);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean IsTheCurrentGameSaved() {
        this.bRet = false;
        JavaEvents.SendEvent(25, 12, this);

        return this.bRet;
    }

    /**
     * Method description
     *
     *
     * @param exists_media
     * @param GameType
     * @param mediaType
     *
     * @return
     */
    public boolean IsCompatibleGame(String exists_media, int GameType, int mediaType) {
        this.bRet = true;
        this.in_game_type = GameType;
        this.in_name0 = exists_media;
        this.in_media_type = mediaType;
        JavaEvents.SendEvent(25, 14, this);

        return this.bRet;
    }

    static class Media {
        String media_name;
        int game_type;
        int media_type;
        int year;
        int month;
        SaveLoadCommonManagement.MediaTime media_time;
    }


    static class MediaTime {
        int year;
        int month;
        int day;
        int hour;
        int min;
        int sec;

        MediaTime() {
            this.year = 0;
            this.month = 0;
            this.day = 0;
            this.hour = 0;
            this.min = 0;
            this.sec = 0;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
