/*
 * @(#)pager.java   13/08/25
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menuscript.Converts;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class pager implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\specmenu.xml";
    private static final String[] CONTROLS = { "background", "PAGER - BACK", "Field GRADIENT dark", "PAGER - BACK2",
            "pager - text" };
    private static final String[] CONTROLS_FM = { "background_FM", "PAGER - BACK - FM", "Field GRADIENT dark - FM",
            "PAGER - BACK2 - FM", "pager - text - FM" };
    private static int[] start_values = null;
    private static int[] start_values_FM = null;
    private long[] controls;
    private long[] controls_fm;
    private long[] group;
    private long[] group_fm;

    /**
     * Constructs ...
     *
     */
    public pager() {
        this.controls = null;
        this.controls_fm = null;
        this.group = null;
        this.group_fm = null;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void restartMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        this.group = menues.InitXml(_menu, "..\\data\\config\\menu\\specmenu.xml", "pager");
        this.group_fm = menues.InitXml(_menu, "..\\data\\config\\menu\\specmenu.xml", "pager_FM");
        this.controls = new long[CONTROLS.length];

        for (int i = 0; i < CONTROLS.length; ++i) {
            this.controls[i] = menues.FindFieldInMenu(_menu, CONTROLS[i]);
        }

        this.controls_fm = new long[CONTROLS_FM.length];

        for (int i = 0; i < CONTROLS_FM.length; ++i) {
            this.controls_fm[i] = menues.FindFieldInMenu(_menu, CONTROLS_FM[i]);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        start_values = new int[CONTROLS.length];

        for (int i = 0; i < CONTROLS.length; ++i) {
            if (this.controls[i] != 0L) {
                Object field = menues.ConvertMenuFields(this.controls[i]);

                if (field != null) {
                    switch (i) {
                     case 0 :
                         start_values[i] = ((MENUText_field) field).poy;

                         break;

                     case 1 :
                         start_values[i] = ((MENUsimplebutton_field) field).leny;

                         break;

                     case 2 :
                     case 3 :
                     case 4 :
                         start_values[i] = ((MENUText_field) field).leny;
                    }
                }
            }
        }

        start_values_FM = new int[CONTROLS_FM.length];

        for (int i = 0; i < CONTROLS_FM.length; ++i) {
            if (this.controls_fm[i] != 0L) {
                Object field = menues.ConvertMenuFields(this.controls_fm[i]);

                if (field != null) {
                    switch (i) {
                     case 0 :
                         start_values_FM[i] = ((MENUText_field) field).poy;

                         break;

                     case 1 :
                         start_values_FM[i] = ((MENUText_field) field).leny;

                         break;

                     case 2 :
                     case 3 :
                     case 4 :
                         start_values_FM[i] = ((MENUText_field) field).leny;
                    }
                }
            }
        }

        for (int i = 0; i < this.group.length; ++i) {
            menues.SetBlindess(this.group[i], true);
            menues.setFocusOnControl(this.group[i], false);
            menues.SetIgnoreEvents(this.group[i], true);
        }

        for (int i = 0; i < this.group_fm.length; ++i) {
            menues.SetBlindess(this.group_fm[i], true);
            menues.setFocusOnControl(this.group_fm[i], false);
            menues.SetIgnoreEvents(this.group_fm[i], true);
        }

        long window = menues.GetBackMenu(_menu);

        if (window != 0L) {
            menues.SetBlindess(window, true);
            menues.setFocusOnControl(window, false);
            menues.SetIgnoreEvents(window, true);
        }

        MenuAfterInitNarrator.justShow(_menu);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "pagerMENU";
    }

    void setShow(boolean value) {}

    void setText(String text, boolean isFMPager) {
        long text_control = (isFMPager)
                            ? this.controls_fm[4]
                            : this.controls_fm[4];

        if ((text_control == 0L) || (text == null)) {
            return;
        }

        MENUText_field text_field = menues.ConvertTextFields(text_control);

        if (text_field == null) {
            return;
        }

        int texth = menues.GetTextHeight(text_field.nativePointer, text);
        int bl = menues.GetBaseLine(text_field.nativePointer);
        int tlh = menues.GetTextLineHeight(text_field.nativePointer);
        int lines = Converts.HeightToLines(texth, bl, tlh);

        if (!(isFMPager)) {
            int add_lines = (lines > 2)
                            ? lines - 2
                            : 0;

            for (int i = 0; i < CONTROLS.length; ++i) {
                if (this.controls[i] != 0L) {
                    Object field = menues.ConvertMenuFields(this.controls[i]);

                    if (field != null) {
                        switch (i) {
                         case 0 :
                             ((MENUText_field) field).poy = (start_values[i] - (25 * add_lines));

                             break;

                         case 1 :
                             ((MENUsimplebutton_field) field).leny = (start_values[i] + 25 * add_lines);

                             break;

                         case 2 :
                         case 3 :
                         case 4 :
                             ((MENUText_field) field).leny = (start_values[i] + 25 * add_lines);
                        }

                        menues.UpdateMenuField(field);
                    }
                }
            }
        }

        if (isFMPager) {
            int add_lines = (lines > 1)
                            ? lines - 1
                            : 0;

            for (int i = 0; i < CONTROLS_FM.length; ++i) {
                if (this.controls_fm[i] != 0L) {
                    Object field = menues.ConvertMenuFields(this.controls_fm[i]);

                    if (field != null) {
                        switch (i) {
                         case 0 :
                             ((MENUText_field) field).poy = (start_values_FM[i] - (25 * add_lines));

                             break;

                         case 1 :
                             ((MENUText_field) field).leny = (start_values_FM[i] + 25 * add_lines);

                             break;

                         case 2 :
                         case 3 :
                         case 4 :
                             ((MENUText_field) field).leny = (start_values_FM[i] + 25 * add_lines);
                        }

                        menues.UpdateMenuField(field);
                    }
                }
            }
        }

        if (!(isFMPager)) {
            menues.SetFieldText(this.controls[4], text);
            menues.SetShowField(this.controls_fm[0], false);
            menues.SetShowField(this.controls[0], true);
        } else {
            menues.SetFieldText(this.controls_fm[4], text);
            menues.SetShowField(this.controls_fm[0], true);
            menues.SetShowField(this.controls[0], false);
        }
    }

    /**
     * @return the xml
     */
    public static String getXml() {
        return XML;
    }
}


//~ Formatted in DD Std on 13/08/25
