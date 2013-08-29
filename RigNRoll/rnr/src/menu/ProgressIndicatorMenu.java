/*
 * @(#)ProgressIndicatorMenu.java   13/08/25
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
public class ProgressIndicatorMenu implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\specmenu.xml";
    private static final String GROUP = "PROGRESS_INDICATOR";
    static String[] lamps_mames = {
        "Progress Ind - LAMP 00", "Progress Ind - LAMP 01", "Progress Ind - LAMP 02", "Progress Ind - LAMP 03",
        "Progress Ind - LAMP 04", "Progress Ind - LAMP 05", "Progress Ind - LAMP 06", "Progress Ind - LAMP 07",
        "Progress Ind - LAMP 08", "Progress Ind - LAMP 09", "Progress Ind - LAMP 10", "Progress Ind - LAMP 11",
        "Progress Ind - LAMP 12", "Progress Ind - LAMP 13", "Progress Ind - LAMP 14", "Progress Ind - LAMP 15",
        "Progress Ind - LAMP 16", "Progress Ind - LAMP 17", "Progress Ind - LAMP 18", "Progress Ind - LAMP 19",
        "Progress Ind - LAMP 20", "Progress Ind - LAMP 21", "Progress Ind - LAMP 22", "Progress Ind - LAMP 23",
        "Progress Ind - LAMP 24", "Progress Ind - LAMP 25", "Progress Ind - LAMP 26", "Progress Ind - LAMP 27",
        "Progress Ind - LAMP 28", "Progress Ind - LAMP 29", "Progress Ind - LAMP 30", "Progress Ind - LAMP 31"
    };
    static long[] lamps = null;
    static TextureCoordinatesHolder holder = null;
    static long sky = 0L;

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
        menues.InitXml(_menu, "..\\data\\config\\menu\\specmenu.xml", "PROGRESS_INDICATOR");
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        lamps = new long[lamps_mames.length];

        for (int i = 0; i < lamps_mames.length; ++i) {
            lamps[i] = menues.FindFieldInMenu(_menu, lamps_mames[i]);

            if (lamps[i] != 0L) {
                menues.SetBlindess(lamps[i], true);
                menues.SetIgnoreEvents(lamps[i], true);
                menues.SetFieldState(lamps[i], 0);
            }
        }

        holder = new TextureCoordinatesHolder();
        sky = menues.FindFieldInMenu(_menu, "Progress Ind - SKY");

        if ((sky != 0L) && (holder != null)) {
            menues.CallMappingModifications(sky, holder, "StoreTextureCoordimates");
            menues.SetBlindess(sky, true);
            menues.SetIgnoreEvents(sky, true);
        }

        menues.WindowSet_ShowCursor(_menu, false);
        menues.setShowMenu(_menu, true);
    }

    /**
     * Method description
     *
     *
     * @param fraction
     */
    public static void SetValue(double fraction) {
        int lmas_to_show = (int) (lamps_mames.length * fraction + 0.5D);

        lmas_to_show = Math.min(lmas_to_show, lamps_mames.length);

        for (int i = 0; i < lmas_to_show; ++i) {
            if (lamps[i] != 0L) {
                menues.SetFieldState(lamps[i], 1);
            }
        }

        if ((sky != 0L) && (holder != null)) {
            holder.SetValue((float) fraction);
            menues.CallMappingModifications(sky, holder, "AnimateTextureCoordiantes");
        }
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
        return "ProgressIndicatorMENU";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static long CreateProgressIndicatorMenu() {
        return menues.createSimpleMenu(new ProgressIndicatorMenu(), 1);
    }

    /**
     * @return the xml
     */
    public static String getXml() {
        return XML;
    }

    /**
     * @return the group
     */
    public static String getGroup() {
        return GROUP;
    }

    class TextureCoordinatesHolder {
        float[] uleft__23;
        float[] uright_01;
        float value;

        TextureCoordinatesHolder() {
            this.uleft__23 = null;
            this.uright_01 = null;
            this.value = 0.0F;
        }

        /**
         * Method description
         *
         *
         * @param sizex
         * @param sizey
         * @param stuff
         */
        public void StoreTextureCoordimates(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
            this.uright_01 = new float[stuff.length];
            this.uleft__23 = new float[stuff.length];

            for (int i = 0; i < stuff.length; ++i) {
                if ((stuff[i]._state == 0) && (!(stuff[i]._active)) && (!(stuff[i].pressed))
                        && (stuff[i].tex.size() > 0)) {
                    this.uleft__23[i] = ((menues.ctexcoord_multylayer) stuff[i].tex.elementAt(0)).t2x;
                    this.uright_01[i] = ((menues.ctexcoord_multylayer) stuff[i].tex.elementAt(0)).t0x;
                }
            }
        }

        /**
         * Method description
         *
         *
         * @param sizex
         * @param sizey
         * @param stuff
         */
        public void AnimateTextureCoordiantes(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
            for (int i = 0; i < stuff.length; ++i) {
                if (stuff[i].tex.size() > 0) {
                    ((menues.ctexcoord_multylayer) stuff[i].tex.elementAt(0)).t2x = (this.uleft__23[i]
                            * (56.0F + this.value * 455.0F) / 56.0F);
                    ((menues.ctexcoord_multylayer) stuff[i].tex.elementAt(0)).t3x = (this.uleft__23[i]
                            * (56.0F + this.value * 455.0F) / 56.0F);
                    ((menues.ctexcoord_multylayer) stuff[i].tex.elementAt(0)).t0x = (this.uright_01[i]
                            * (57.0F + this.value * 454.0F) / 57.0F);
                    ((menues.ctexcoord_multylayer) stuff[i].tex.elementAt(0)).t1x = (this.uright_01[i]
                            * (57.0F + this.value * 454.0F) / 57.0F);
                }
            }
        }

        /**
         * Method description
         *
         *
         * @param _value
         */
        public void SetValue(float _value) {
            this.value = _value;
        }
    }
}


//~ Formatted in DD Std on 13/08/25
