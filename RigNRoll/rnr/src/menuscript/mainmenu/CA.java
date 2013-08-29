/*
 * @(#)CA.java   13/08/26
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

import rnr.src.menu.ListOfAlternatives;
import rnr.src.menu.RadioGroupSmartSwitch;
import rnr.src.menu.SliderGroupRadioButtons;
import rnr.src.menu.menues;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class CA {
    static String FILENAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String LISTOFALTERANTIVESGROUP = "Tablegroup - ELEMENTS - List";
    private static final String RADIOGROUPSMARTSWICTHGROUP = "Tablegroup - ELEMENTS - Switch";
    private static final String SLIDERRADIOBUTTONSGROUP = "Tablegroup - ELEMENTS - Slider";

    static final void freezControl(long field) {
        menues.SetBlindess(field, true);
        menues.SetIgnoreEvents(field, true);
    }

    static final void unfreezControl(long field) {
        menues.SetBlindess(field, false);
        menues.SetIgnoreEvents(field, false);
    }

    static final void hideControl(long field) {
        menues.SetShowField(field, false);
    }

    static final void showControl(long field) {
        menues.SetShowField(field, true);
    }

    static final ListOfAlternatives createListOfAlternatives(String title, String[] alternarives, boolean bCanChange) {
        return new ListOfAlternatives(FILENAME, "Tablegroup - ELEMENTS - List", title, alternarives, !(bCanChange));
    }

    static final RadioGroupSmartSwitch createRadioGroupSmartSwitch(String title, boolean value, boolean bCanChange) {
        return new RadioGroupSmartSwitch(FILENAME, "Tablegroup - ELEMENTS - Switch", title, value, !(bCanChange));
    }

    static final SliderGroupRadioButtons createSliderRadioButtons(String title, int min_value, int max_value,
            int cur_value, boolean bCanChange) {
        return new SliderGroupRadioButtons(FILENAME, "Tablegroup - ELEMENTS - Slider", title, min_value, max_value,
                                           cur_value, !(bCanChange));
    }
}


//~ Formatted in DD Std on 13/08/26
