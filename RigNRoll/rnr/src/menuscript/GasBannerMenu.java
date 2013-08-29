/*
 * @(#)GasBannerMenu.java   13/08/26
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


package rnr.src.menuscript;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.menucreation;
import rnr.src.menu.menues;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class GasBannerMenu implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_gasstation.xml";
    private static final String GROUP = "GAS_PRICE_TABLO";
    private static final String PRICE_TEXT = "GAS_PRICE_TABLO - VALUE";
    private static long text_field = 0L;

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        menues.WindowSet_ShowCursor(_menu, false);
        text_field = menues.FindFieldInMenu(_menu, "GAS_PRICE_TABLO - VALUE");
        menues.setMenuID(_menu, 220);
        menues.setShowMenu(_menu, false);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        menues.InitXml(_menu, "..\\data\\config\\menu\\menu_gasstation.xml", "GAS_PRICE_TABLO");
    }

    private static String FormatPriceShort(int _price) {
        int i_price = (int) Math.floor(_price / 100.0D);
        int m_price = _price - (i_price * 100);
        String i_ret = "" + i_price;
        String m_ret;

        if (m_price >= 10) {
            m_ret = "." + m_price;
        } else {
            m_ret = ".0" + m_price;
        }

        return i_ret + m_ret;
    }

    /**
     * Method description
     *
     *
     * @param price
     */
    public static void SetValue(int price) {
        if (text_field == 0L) {
            return;
        }

        menues.SetFieldText(text_field, FormatPriceShort(price));
        menues.UpdateMenuField(menues.ConvertMenuFields(text_field));
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
     * @param _menu
     */
    @Override
    public void restartMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "gasBannerMENU";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static long CreateGasBannerMenu() {
        return menues.createSimpleMenu(new GasBannerMenu(), 100000000.0D, "", 1600, 1200, 270, 270, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
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

    /**
     * @return the priceText
     */
    public static String getPriceText() {
        return PRICE_TEXT;
    }
}


//~ Formatted in DD Std on 13/08/26
