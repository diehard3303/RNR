/*
 * @(#)AlbumFinishWarehouse.java   13/08/26
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


package rnr.src.rnrorg;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.KeyPair;
import rnr.src.menu.MacroKit;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.loc;

//~--- JDK imports ------------------------------------------------------------

import java.util.Arrays;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class AlbumFinishWarehouse {

    /** Field description */
    public static final String[] NOTES = {
        "BIGRACE GOLD 1", "BIGRACE GOLD 2", "BIGRACE GOLD 3", "BIGRACE GOLD 4", "BIGRACE SILVER 1", "BIGRACE SILVER 2",
        "BIGRACE SILVER 3", "BIGRACE SILVER 4", "BIGRACE BRONZE 1", "BIGRACE BRONZE 2", "BIGRACE BRONZE 3",
        "BIGRACE BRONZE 4"
    };

    /** Field description */
    public static final int[] TYPES = {
        23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34
    };

    /** Field description */
    public static final String TEXTUREPREFIX = "bigraceshot";

    /** Field description */
    public static final String[] PLACES = { "gold", "silver", "bronze", "noprize" };

    /** Field description */
    public static final int PLACE_GOLD = 0;

    /** Field description */
    public static final int PLACE_SILVER = 1;

    /** Field description */
    public static final int PLACE_BRONZE = 2;

    /** Field description */
    public static final int PLACE_NOPRIZE = 3;

    /** Field description */
    public static final String[] PREFIXES = {
        "bigraceshot_04_", "bigraceshot_03_", "bigraceshot_02_", "bigraceshot_01_", "bigraceshot_04_",
        "bigraceshot_03_", "bigraceshot_02_", "bigraceshot_01_", "bigraceshot_04_", "bigraceshot_03_",
        "bigraceshot_02_", "bigraceshot_01_"
    };

    /** Field description */
    public static final String[] SUFFIXES = {
        "_" + PLACES[0], "_" + PLACES[0], "_" + PLACES[0], "_" + PLACES[0], "_" + PLACES[1], "_" + PLACES[1],
        "_" + PLACES[1], "_" + PLACES[1], "_" + PLACES[2], "_" + PLACES[2], "_" + PLACES[2], "_" + PLACES[2]
    };

    private static String textureName(int index, String logoname) {
        return PREFIXES[index] + logoname + SUFFIXES[index];
    }

    static void postNote(int type, String shortracename, String logoname) {
        int index = Arrays.binarySearch(TYPES, type);

        if ((index < 0) || (index >= TYPES.length)) {
            return;
        }

        CoreTime time = new CoreTime();

        Album.getInstance().addBigRaceShot(shortracename, Integer.toString(index), time, textureName(index, logoname));
    }

    /**
     * Method description
     *
     *
     * @param shortracename
     * @param index
     *
     * @return
     */
    public static String locText(String shortracename, String index) {
        KeyPair[] keys = { new KeyPair("SHORTRACENAME", loc.getBigraceShortName(shortracename)) };

        return MacroKit.Parse(loc.getMissionSuccesPictureText(NOTES[Integer.parseInt(index)]), keys);
    }
}


//~ Formatted in DD Std on 13/08/26
