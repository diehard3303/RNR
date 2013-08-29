/*
 * @(#)JournalFinishWarehouse.java   13/08/26
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

import rnr.src.menu.Helper;
import rnr.src.rnrcore.MacroBuilder;
import rnr.src.rnrcore.Macros;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class JournalFinishWarehouse extends journalelement {
    private static final String[] NOTES = {
        "DELIVERY FINISH", "DELIVERY TIMEOUT", "DELIVERY CANCEL", "DELIVERY DAMAGED", "DELIVERY EVACUATION",
        "TENDER FINISH GOLD", "TENDER FINISH SILVER", "TENDER FINISH BRONZE", "TENDER FINISH SIMPLE", "TENDER TIMEOUT",
        "TENDER CANCEL", "CONTEST FINISH WON", "CONTEST FINISH SIMPLE", "CONTEST TIMEOUT", "CONTEST CANCEL",
        "BIGRACE FINISH CHECKPOINT 1", "BIGRACE FINISH CHECKPOINT 2", "BIGRACE FINISH CHECKPOINT 3",
        "BIGRACE FINISH CHECKPOINT 4", "BIGRACE FINISH 1", "BIGRACE FINISH 2", "BIGRACE FINISH 3", "BIGRACE FINISH 4",
        "BIGRACE GOLD 1", "BIGRACE GOLD 2", "BIGRACE GOLD 3", "BIGRACE GOLD 4", "BIGRACE SILVER 1", "BIGRACE SILVER 2",
        "BIGRACE SILVER 3", "BIGRACE SILVER 4", "BIGRACE BRONZE 1", "BIGRACE BRONZE 2", "BIGRACE BRONZE 3",
        "BIGRACE BRONZE 4", "BIGRACE DAMAGED 1", "BIGRACE DAMAGED 2", "BIGRACE DAMAGED 3", "BIGRACE DAMAGED 4",
        "BIGRACE EVACUATION 1", "BIGRACE EVACUATION 2", "BIGRACE EVACUATION 3", "BIGRACE EVACUATION 4",
        "BIGRACE TIMEOUT 1", "BIGRACE TIMEOUT 2", "BIGRACE TIMEOUT 3", "BIGRACE TIMEOUT 4", "BIGRACE DECLINE 1",
        "BIGRACE DECLINE 2", "BIGRACE DECLINE 3", "BIGRACE DECLINE 4", "DELIVERY EVACUATION ONLY"
    };

    /** Field description */
    public static final int DELIVERY_FINISH = 0;

    /** Field description */
    public static final int DELIVERY_TIMEOUT = 1;

    /** Field description */
    public static final int DELIVERY_CANCEL = 2;

    /** Field description */
    public static final int DELIVERY_DAMAGED = 3;

    /** Field description */
    public static final int DELIVERY_EVACUATION = 4;

    /** Field description */
    public static final int TENDER_FINISH_GOLD = 5;

    /** Field description */
    public static final int TENDER_FINISH_SILVER = 6;

    /** Field description */
    public static final int TENDER_FINISH_BRONZE = 7;

    /** Field description */
    public static final int TENDER_FINISH_SIMPLE = 8;

    /** Field description */
    public static final int TENDER_TIMEOUT = 9;

    /** Field description */
    public static final int TENDER_CANCEL = 10;

    /** Field description */
    public static final int CONTEST_FINISH_WON = 11;

    /** Field description */
    public static final int CONTEST_FINISH_SIMPLE = 12;

    /** Field description */
    public static final int CONTEST_TIMEOUT = 13;

    /** Field description */
    public static final int CONTEST_CANCEL = 14;

    /** Field description */
    public static final int BIGRACE_FINISH_CHECKPOINT_1 = 15;

    /** Field description */
    public static final int BIGRACE_FINISH_CHECKPOINT_2 = 16;

    /** Field description */
    public static final int BIGRACE_FINISH_CHECKPOINT_3 = 17;

    /** Field description */
    public static final int BIGRACE_FINISH_CHECKPOINT_4 = 18;

    /** Field description */
    public static final int BIGRACE_FINISH_1 = 19;

    /** Field description */
    public static final int BIGRACE_FINISH_2 = 20;

    /** Field description */
    public static final int BIGRACE_FINISH_3 = 21;

    /** Field description */
    public static final int BIGRACE_FINISH_4 = 22;

    /** Field description */
    public static final int BIGRACE_GOLD_1 = 23;

    /** Field description */
    public static final int BIGRACE_GOLD_2 = 24;

    /** Field description */
    public static final int BIGRACE_GOLD_3 = 25;

    /** Field description */
    public static final int BIGRACE_GOLD_4 = 26;

    /** Field description */
    public static final int BIGRACE_SILVER_1 = 27;

    /** Field description */
    public static final int BIGRACE_SILVER_2 = 28;

    /** Field description */
    public static final int BIGRACE_SILVER_3 = 29;

    /** Field description */
    public static final int BIGRACE_SILVER_4 = 30;

    /** Field description */
    public static final int BIGRACE_BRONZE_1 = 31;

    /** Field description */
    public static final int BIGRACE_BRONZE_2 = 32;

    /** Field description */
    public static final int BIGRACE_BRONZE_3 = 33;

    /** Field description */
    public static final int BIGRACE_BRONZE_4 = 34;

    /** Field description */
    public static final int BIGRACE_DAMAGED_1 = 35;

    /** Field description */
    public static final int BIGRACE_DAMAGED_2 = 36;

    /** Field description */
    public static final int BIGRACE_DAMAGED_3 = 37;

    /** Field description */
    public static final int BIGRACE_DAMAGED_4 = 38;

    /** Field description */
    public static final int BIGRACE_EVACUATION_1 = 39;

    /** Field description */
    public static final int BIGRACE_EVACUATION_2 = 40;

    /** Field description */
    public static final int BIGRACE_EVACUATION_3 = 41;

    /** Field description */
    public static final int BIGRACE_EVACUATION_4 = 42;

    /** Field description */
    public static final int BIGRACE_TIMEOUT_1 = 43;

    /** Field description */
    public static final int BIGRACE_TIMEOUT_2 = 44;

    /** Field description */
    public static final int BIGRACE_TIMEOUT_3 = 45;

    /** Field description */
    public static final int BIGRACE_TIMEOUT_4 = 46;

    /** Field description */
    public static final int BIGRACE_DECLINE_1 = 47;

    /** Field description */
    public static final int BIGRACE_DECLINE_2 = 48;

    /** Field description */
    public static final int BIGRACE_DECLINE_3 = 49;

    /** Field description */
    public static final int BIGRACE_DECLINE_4 = 50;

    /** Field description */
    public static final int DELIVERY_EVACUATION_ONLY = 51;

    private JournalFinishWarehouse() {}

    private JournalFinishWarehouse(String description, List<Macros> macrosPairs) {
        super(description, macrosPairs);
    }

    /**
     * Method description
     *
     *
     * @param type
     */
    public static final void createNote(int type) {
        missionEventType(type);
        new JournalFinishWarehouse(NOTES[type], null).start();
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param macroces
     */
    public static final void createNote(int type, List<Macros> macroces) {
        missionEventType(type);
        new JournalFinishWarehouse(NOTES[type], macroces).start();
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param shortracename
     * @param logoname
     * @param source
     * @param destination
     * @param place
     * @param rating
     * @param money
     */
    public static final void createNote(int type, String shortracename, String logoname, String source,
            String destination, int place, int rating, int money) {
        missionEventType(type);
        AlbumFinishWarehouse.postNote(type, shortracename, logoname);

        List macro = new ArrayList();

        macro.add(new Macros("SHORTRACENAME", MacroBuilder.makeSimpleMacroBody("BIGRACE_SHORTNAME", shortracename)));
        macro.add(new Macros("SOURCE", MacroBuilder.makeSimpleMacroBody("CITYNAME", source)));
        macro.add(new Macros("DESTINATION", MacroBuilder.makeSimpleMacroBody("CITYNAME", destination)));
        macro.add(new Macros("PLACE", MacroBuilder.makeSimpleMacroBody("" + place)));
        macro.add(new Macros("MONEY", MacroBuilder.makeSimpleMacroBody(Helper.convertMoney(money))));
        macro.add(new Macros("RATING", MacroBuilder.makeSimpleMacroBody("" + rating)));
        new JournalFinishWarehouse(NOTES[type], macro).start();
    }

    private static void missionEventType(int type) {
        IStoreorgelement elem = organaiser.getInstance().getCurrentWarehouseOrder();

        if (null == elem) {
            return;
        }

        switch (type) {
         case 0 :
         case 5 :
         case 6 :
         case 7 :
         case 8 :
         case 11 :
         case 12 :
         case 15 :
         case 16 :
         case 17 :
         case 18 :
         case 19 :
         case 20 :
         case 21 :
         case 22 :
         case 23 :
         case 24 :
         case 25 :
         case 26 :
         case 27 :
         case 28 :
         case 29 :
         case 30 :
         case 31 :
         case 32 :
         case 33 :
         case 34 :
             elem.finish();

             break;

         case 1 :
         case 2 :
         case 3 :
         case 4 :
         case 9 :
         case 10 :
         case 13 :
         case 14 :
         case 35 :
         case 36 :
         case 37 :
         case 38 :
         case 39 :
         case 40 :
         case 41 :
         case 42 :
         case 43 :
         case 44 :
         case 45 :
         case 46 :
         case 47 :
         case 48 :
         case 49 :
         case 50 :
             elem.fail(0);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
