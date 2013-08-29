/*
 * @(#)JournalStartWarehouse.java   13/08/26
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

import rnr.src.rnrconfig.MerchandizeInformation;
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
public class JournalStartWarehouse extends journalelement {
    private static final String[] START_JOURNALS = {
        "DELIVERY START JOURNAL", "TENDER START JOURNAL", "CONTEST START JOURNAL", "BIGRACE START JOURNAL NOSEMI 1",
        "BIGRACE START JOURNAL NOSEMI 2", "BIGRACE START JOURNAL NOSEMI 3", "BIGRACE START JOURNAL NOSEMI 4",
        "BIGRACE START JOURNAL SEMI 1", "BIGRACE START JOURNAL SEMI 2", "BIGRACE START JOURNAL SEMI 3",
        "BIGRACE START JOURNAL SEMI 4"
    };
    private static final int DELIVERY = 0;
    private static final int TENDER = 1;
    private static final int CONTEST = 2;
    private static final int BIGRACE_1_NOSEMI = 3;
    private static final int BIGRACE_2_NOSEMI = 4;
    private static final int BIGRACE_3_NOSEMI = 5;
    private static final int BIGRACE_4_NOSEMI = 6;
    private static final int BIGRACE_1_SEMI = 7;
    private static final int BIGRACE_2_SEMI = 8;
    private static final int BIGRACE_3_SEMI = 9;
    private static final int BIGRACE_4_SEMI = 10;

    /**
     * Constructs ...
     *
     */
    public JournalStartWarehouse() {}

    /**
     * Constructs ...
     *
     *
     * @param descriptionLocRef
     * @param macrosPairs
     */
    public JournalStartWarehouse(String descriptionLocRef, List<Macros> macrosPairs) {
        super(descriptionLocRef, macrosPairs);
    }

    /**
     * Method description
     *
     *
     * @param description
     * @param type
     * @param merchandise
     * @param load_point
     * @param complete_point
     * @param raceName
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static journable createStartJournalNote(String description, IStoreorgelement.Type type, String merchandise,
            String load_point, String complete_point, String raceName) {
        EventGetPointLocInfo info_load = MissionEventsMaker.getLocalisationMissionPointInfoLocRefs(load_point);
        EventGetPointLocInfo info_complete = MissionEventsMaker.getLocalisationMissionPointInfoLocRefs(complete_point);
        String note_loc_ref = START_JOURNALS[0];
        List macroces = new ArrayList();
        int num = 0;
        MerchandizeInformation merchandizeInfo = new MerchandizeInformation(merchandise);

        switch (type.ordinal()) {
         case 9 :
             note_loc_ref = START_JOURNALS[0];
             macroces.add(new Macros("CARGO",
                                     MacroBuilder.makeSimpleMacroBody("MERCHNAME",
                                         merchandizeInfo.getMerchandizeName())));
             macroces.add(new Macros("SOURCE", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_load.short_name)));
             macroces.add(new Macros("DESTINATION",
                                     MacroBuilder.makeSimpleMacroBody("MPSHORT", info_complete.short_name)));

             break;

         case 10 :
             note_loc_ref = START_JOURNALS[1];
             macroces.add(new Macros("SOURCE", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_load.short_name)));
             macroces.add(new Macros("DESTINATION",
                                     MacroBuilder.makeSimpleMacroBody("MPSHORT", info_complete.short_name)));

             break;

         case 11 :
             note_loc_ref = START_JOURNALS[2];
             macroces.add(new Macros("SOURCE", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_load.short_name)));
             macroces.add(new Macros("DESTINATION",
                                     MacroBuilder.makeSimpleMacroBody("MPSHORT", info_complete.short_name)));

             break;

         case 1 :
         case 2 :
         case 3 :
         case 4 :
         case 5 :
         case 6 :
         case 7 :
         case 8 :
             switch (type.ordinal()) {
              case 1 :
                  num = 3;

                  break;

              case 2 :
                  num = 4;

                  break;

              case 3 :
                  num = 5;

                  break;

              case 4 :
                  num = 6;

                  break;

              case 5 :
                  num = 7;

                  break;

              case 6 :
                  num = 8;

                  break;

              case 7 :
                  num = 9;

                  break;

              case 8 :
                  num = 10;
             }

             note_loc_ref = START_JOURNALS[num];
             macroces.add(new Macros("CARGO",
                                     MacroBuilder.makeSimpleMacroBody("MERCHNAME",
                                         merchandizeInfo.getMerchandizeName())));
             macroces.add(new Macros("SOURCE", MacroBuilder.makeSimpleMacroBody("MPSHORT", info_load.short_name)));
             macroces.add(new Macros("DESTINATION",
                                     MacroBuilder.makeSimpleMacroBody("MPSHORT", info_complete.short_name)));
             macroces.add(new Macros("SHORTRACENAME", MacroBuilder.makeSimpleMacroBody("BIGRACE_SHORTNAME", raceName)));
        }

        return new JournalStartWarehouse(note_loc_ref, macroces);
    }
}


//~ Formatted in DD Std on 13/08/26
