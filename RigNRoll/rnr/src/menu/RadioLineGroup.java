/*
 * @(#)RadioLineGroup.java   13/08/25
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
   Released under the BSD 3 clause license
Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this 
    list of conditions and the following disclaimer. Redistributions in binary 
    form must reproduce the above copyright notice, this list of conditions and 
    the following disclaimer in the documentation and/or other materials 
    provided with the distribution. Neither the name of the DieHard Development 
    nor the names of its contributors may be used to endorse or promote products 
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 *
 *
 *
 */


package rnr.src.menu;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class RadioLineGroup extends RadioGroup {

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param buttons
     */
    public RadioLineGroup(long _menu, MENUbutton_field[] buttons) {
        super(_menu, buttons);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void onSelect(long _menu, MENUbutton_field button) {
        if ((this.undersession) || (this.f_deselecting)) {
            return;
        }

        beginsession();

        boolean hit = false;

        for (int i = 0; i < this.buttons.length; ++i) {
            menues.SetFieldState(this.buttons[i].nativePointer, (hit)
                    ? 0
                    : 1);

            if (this.buttons[i].nativePointer == button.nativePointer) {
                this.focus_member = i;
                hit = true;
            }
        }

        endsession();

        Iterator<IRadioChangeListener> iter = this.listeners.iterator();

        while (iter.hasNext()) {
            iter.next().controlSelected(button, this.focus_member);
        }
    }
}


//~ Formatted in DD Std on 13/08/25
