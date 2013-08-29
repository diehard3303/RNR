/*
 * @(#)MENUBase_Line.java   13/08/25
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class MENUBase_Line {
    int[] base_line_active = null;
    int[] base_line_passive = null;
    int[] base_line_pressed = null;
    int num_states = 0;

    /**
     * Constructs ...
     *
     *
     * @param field
     */
    public MENUBase_Line(long field) {
        int num_states = (field != 0L)
                         ? menues.GetNumStates(field)
                         : 0;

        if (num_states != 0) {
            this.base_line_active = new int[num_states];
            this.base_line_passive = new int[num_states];
            this.base_line_pressed = new int[num_states];

            for (int i = 0; i < num_states; ++i) {
                this.base_line_active[i] = menues.GetBaseLine(field, true, i);
                this.base_line_passive[i] = menues.GetBaseLine(field, false, i);
                this.base_line_pressed[i] = menues.GetBaseLinePressed(field, i);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param field
     * @param moveTo
     */
    public void MoveBaseLine(long field, int moveTo) {
        int num_states = (field != 0L)
                         ? menues.GetNumStates(field)
                         : 0;

        if ((this.base_line_active != null) && (this.base_line_passive != null) && (this.base_line_pressed != null)) {
            for (int i = 0; i < Math.min(num_states, this.base_line_active.length); ++i) {
                menues.SetBaseLine(field, this.base_line_active[i] + moveTo, true, i);
                menues.SetBaseLine(field, this.base_line_passive[i] + moveTo, false, i);
                menues.SetBaseLinePressed(field, this.base_line_pressed[i] + moveTo, i);
            }

            menues.UpdateMenuField(menues.ConvertMenuFields(field));
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int GetMinBaseLine() {
        if ((this.base_line_active != null) && (this.base_line_passive != null) && (this.base_line_pressed != null)) {
            int min = 1000000000;

            for (int i = 0; i < this.base_line_active.length; ++i) {
                min = Math.min(this.base_line_active[i], min);
                min = Math.min(this.base_line_passive[i], min);
                min = Math.min(this.base_line_pressed[i], min);
            }

            return min;
        }

        return 0;
    }
}


//~ Formatted in DD Std on 13/08/25
