/*
 * @(#)TextScroller.java   13/08/25
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

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class TextScroller extends IWheelEnabled {
    Vector<MENUText_field> m_TextFields = new Vector<MENUText_field>();
    MENU_ranger m_ranger;
    int m_numlines;
    int m_linewidth;
    int m_lineonscreen;
    int m_startbase;

    /**
     * Constructs ...
     *
     *
     * @param common
     * @param ranger
     * @param numlines
     * @param lineonscreen
     * @param linewidth
     * @param startbaseline
     * @param bHideRangerIfTextSmall
     * @param scroller_group
     */
    public TextScroller(Common common, MENU_ranger ranger, int numlines, int lineonscreen, int linewidth,
                        int startbaseline, boolean bHideRangerIfTextSmall, String scroller_group) {
        this.m_ranger = ranger;
        this.m_linewidth = linewidth;
        this.m_lineonscreen = lineonscreen;
        this.m_startbase = startbaseline;
        this.m_ranger.min_value = 0;
        this.m_ranger.current_value = 0;
        wheelInit(common.GetMenu(), scroller_group);

        if (numlines < lineonscreen) {
            this.m_ranger.max_value = 0;

            if (bHideRangerIfTextSmall) {
                menues.SetShowField(ranger.nativePointer, false);
                wheelHide(true);
            }
        } else {
            this.m_ranger.max_value = (numlines - lineonscreen);

            if (bHideRangerIfTextSmall) {
                menues.SetShowField(ranger.nativePointer, true);
                wheelHide(false);
            }
        }

        this.m_ranger.page = lineonscreen;
        menues.UpdateField(this.m_ranger);
        menues.SetScriptOnControl(common.GetMenu(), this.m_ranger, this, "OnRanger", 1L);
    }

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param ranger
     * @param numlines
     * @param lineonscreen
     * @param linewidth
     * @param startbaseline
     * @param group2hide
     * @param bHideRangerIfTextSmall
     * @param scroller_group
     */
    public TextScroller(long _menu, MENU_ranger ranger, int numlines, int lineonscreen, int linewidth,
                        int startbaseline, long group2hide, boolean bHideRangerIfTextSmall, String scroller_group) {
        this.m_ranger = ranger;
        this.m_linewidth = linewidth;
        this.m_lineonscreen = lineonscreen;
        this.m_startbase = startbaseline;
        wheelInit(_menu, scroller_group);
        this.m_ranger.min_value = 0;
        this.m_ranger.current_value = 0;

        if (numlines <= lineonscreen) {
            this.m_ranger.max_value = 0;

            if (bHideRangerIfTextSmall) {
                if (group2hide != 0L) {
                    menues.SetShowField(group2hide, false);
                }

                wheelHide(true);
            }
        } else {
            this.m_ranger.max_value = (numlines - lineonscreen);

            if (bHideRangerIfTextSmall) {
                if (group2hide != 0L) {
                    menues.SetShowField(group2hide, true);
                }

                wheelHide(false);
            }
        }

        this.m_ranger.page = lineonscreen;
        menues.UpdateField(this.m_ranger);
        menues.SetScriptOnControl(_menu, this.m_ranger, this, "OnRanger", 1L);
    }

    /**
     * Method description
     *
     */
    public void Deinit() {
        wheelDeinit();
    }

    /**
     * Method description
     *
     */
    @Override
    public void cbEnterFocus() {}

    /**
     * Method description
     *
     */
    @Override
    public void cbLeaveFocus() {}

    /**
     * Method description
     *
     */
    @Override
    public void ControlsCtrlAPressed() {}

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void ControlsMouseWheel(int value) {
        if (this.m_ranger == null) {
            return;
        }

        this.m_ranger.current_value -= value;

        if (this.m_ranger.current_value > this.m_ranger.max_value) {
            this.m_ranger.current_value = this.m_ranger.max_value;
        }

        if (this.m_ranger.current_value < this.m_ranger.min_value) {
            this.m_ranger.current_value = this.m_ranger.min_value;
        }

        menues.UpdateField(this.m_ranger);
    }

    /**
     * Method description
     *
     *
     * @param textfield
     */
    public void AddTextControl(MENUText_field textfield) {
        this.m_TextFields.add(textfield);
    }

    void OnRanger(long _menu, MENU_ranger ranger) {
        for (int i = 0; i < this.m_TextFields.size(); ++i) {
            MENUText_field t = this.m_TextFields.get(i);

            menues.SetBaseLine(t.nativePointer, this.m_startbase - (ranger.current_value * this.m_linewidth));
        }
    }
}


//~ Formatted in DD Std on 13/08/25
