/*
 * @(#)gasstationmenu.java   13/08/26
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

import rnr.src.menu.BalanceUpdater;
import rnr.src.menu.BaseMenu;
import rnr.src.menu.Common;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.SMenu;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.rnrscr.gameinfo;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class gasstationmenu extends BaseMenu implements menucreation {
    private static final String[] BUTTONS = { "CANCEL button", "PAY button", "FULL button" };
    private static final String[] ACTIONS = { "OnCancel", "OnPay", "OnFull" };
    private static final int full_button = 2;
    private static final String RANGER = "RANGER button";
    private static final String RANGER_BACK = "FuelTankIndicator";
    static double s_fAnimDurationMin = 0.1D;
    static double s_fAnimDurationMax = 7.0D;
    static double s_fMaxLitres = 500.0D;
    static int s_animid = Common.GetID();
    static int s_fProgressBarLen;
    static MENUText_field s_fieldThisSale;
    static MENUText_field s_fieldGallons;
    static MENUText_field s_fieldProgressBar;
    static gameinfo s_info;
    static double s_fAnimationLen;
    private final long[] buttons;
    private boolean action_started;
    int m_iMaxPossiblePurchase;
    String thisSaleText;
    String pricePerGallonText;
    String totalPriceText;

    /**
     * Constructs ...
     *
     */
    public gasstationmenu() {
        this.buttons = new long[BUTTONS.length];
        this.action_started = false;
        this.thisSaleText = null;
        this.pricePerGallonText = null;
        this.totalPriceText = null;
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
        this.common = new Common(_menu);
        this.common.InitBalance();
        menues.InitXml(_menu, Common.ConstructPath("menu_gasstation.xml"), "Menu GAS");
    }

    /**
     * Method description
     *
     *
     * @param button
     */
    public void PrintSimpleButton(MENUsimplebutton_field button) {}

    MENUText_field FindTextField(long _menu, String name) {
        long control = menues.FindFieldInMenu(_menu, name);

        if (control == 0L) {
            ;
        }

        return menues.ConvertTextFields(control);
    }

    MENUsimplebutton_field FindSimpleButton(long _menu, String name) {
        long control = menues.FindFieldInMenu(_menu, name);

        if (control == 0L) {
            ;
        }

        return menues.ConvertSimpleButton(control);
    }

    MENU_ranger FindScroller(long _menu, String name) {
        long control = menues.FindFieldInMenu(_menu, name);

        if (control == 0L) {
            ;
        }

        return menues.ConvertRanger(control);
    }

    /**
     * Method description
     *
     *
     * @param ranger
     */
    public void PrintRanger(MENU_ranger ranger) {}

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        InitData(_menu);
        menues.setShowMenu(_menu, true);

        for (int i = 0; i < BUTTONS.length; ++i) {
            MENUsimplebutton_field field = FindSimpleButton(_menu, BUTTONS[i]);

            this.buttons[i] = field.nativePointer;
            menues.SetScriptOnControl(_menu, field, this, ACTIONS[i], 4L);

            if (i == 2) {
                menues.setfocuscontrolonmenu(_menu, field.nativePointer);
            }
        }

        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(menues.GetBackMenu(_menu)), this, "OnExit", 17L);
    }

    void SetFieldText(long menu, String name, String text) {
        MENUText_field field = FindTextField(menu, name);

        field.text = text;
        menues.UpdateField(field);
    }

    String GetFieldText(long menu, String name) {
        MENUText_field field = FindTextField(menu, name);

        return field.text;
    }

    private void RecalucateBought(boolean bDoMaxBought) {
        gameinfo info = gameinfo.script;

        info.m_iMaxBought = (info.m_iMaxLitres - info.m_iStartLitres);

        if (bDoMaxBought) {
            info.m_iCurBought = info.m_iMaxBought;
        }

        int balance = 100 * Math.max(BalanceUpdater.GetBalance(), 0);

        this.m_iMaxPossiblePurchase = Math.max(balance / info.m_iPricePerLitre, 0);
        this.m_iMaxPossiblePurchase = ((this.m_iMaxPossiblePurchase > info.m_iMaxBought)
                                       ? info.m_iMaxBought
                                       : this.m_iMaxPossiblePurchase);
        info.m_iCurBought = ((info.m_iCurBought > this.m_iMaxPossiblePurchase)
                             ? this.m_iMaxPossiblePurchase
                             : info.m_iCurBought);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    public void InitData(long _menu) {
        this.thisSaleText = GetFieldText(_menu, "ThisSale");
        this.pricePerGallonText = GetFieldText(_menu, "PricePerGallon");
        this.totalPriceText = GetFieldText(_menu, "TotalPrice");

        gameinfo info = gameinfo.script;

        RecalucateBought(true);
        SetFieldText(_menu, "PricePerGallon",
                     FormatPriceShort(info.m_iPricePerLitre, this.pricePerGallonText, "GAS_PRICE_PER_GALLON"));
        SetFieldText(_menu, "ThisSale", FormatPrice(0, this.thisSaleText, "GAS_THIS_SALE"));
        SetFieldText(_menu, "Gallons", FormatGallonsShort(0));
        SetFieldText(_menu, "TotalGallons", FormatGallonsShort(info.m_iCurBought));
        SetFieldText(_menu, "TotalPrice",
                     FormatPrice(info.m_iCurBought * info.m_iPricePerLitre, this.totalPriceText, "GAS_TOTAL_PRICE"));

        MENUText_field progressbar = FindTextField(_menu, "FuelTankIndicator");

        s_fProgressBarLen = progressbar.lenx;
        progressbar.lenx = info.m_iStartLitres / info.m_iMaxLitres * s_fProgressBarLen;
        menues.UpdateField(progressbar);

        MENU_ranger ranger = FindScroller(_menu, "RANGER button");
        int newx = progressbar.pox + progressbar.lenx - ranger.thumbsize;

        ranger.lenx -= newx - ranger.pox;
        ranger.lenx -= ranger.thumbsize;

        int deltamax = (info.m_iMaxBought - this.m_iMaxPossiblePurchase) / info.m_iMaxBought * ranger.lenx;

        ranger.lenx -= deltamax;

        if (ranger.lenx <= 0) {
            ranger.lenx = 1;
        }

        ranger.lenx += ranger.thumbsize;
        ranger.pox = newx;
        ranger.min_value = 0;
        ranger.max_value = this.m_iMaxPossiblePurchase;
        ranger.current_value = ranger.max_value;

        if (ranger.max_value == 0) {
            menues.SetIgnoreEvents(ranger.nativePointer, true);
            ranger.max_value = 1;
        }

        menues.UpdateField(ranger);
        menues.UpdateField(ranger);
        menues.SetScriptOnControl(_menu, ranger, this, "OnScroller", 1L);
    }

    private void RecalculateAnimation() {
        RecalucateBought(false);

        gameinfo info = gameinfo.script;
        double factor = Math.min(info.m_iCurBought / s_fMaxLitres, 1.0D);
        double length = s_fAnimDurationMin + (s_fAnimDurationMax - s_fAnimDurationMin) * factor;

        s_fAnimationLen = length;
        s_info = info;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param time
     */
    public void FinalAnimation(long _menu, double time) {
        RecalculateAnimation();

        double factor = time / s_fAnimationLen;

        if (factor > 1.0D) {
            menues.StopScriptAnimation(s_animid);
            s_fieldGallons = null;
            s_fieldThisSale = null;
            s_fieldProgressBar = null;
            s_info = null;
            menues.CallMenuCallBack_OKMenu(_menu);

            return;
        }

        int curvalue = (int) Math.ceil(factor * s_info.m_iCurBought);

        s_fieldGallons.text = FormatGallonsShort(curvalue);
        s_fieldThisSale.text = FormatPrice(curvalue * s_info.m_iPricePerLitre, this.thisSaleText, "GAS_THIS_SALE");

        double barlen = (curvalue + s_info.m_iStartLitres) / s_info.m_iMaxLitres;

        s_fieldProgressBar.lenx = (int) (barlen * s_fProgressBarLen);
        menues.UpdateField(s_fieldGallons);
        menues.UpdateField(s_fieldThisSale);
        menues.UpdateField(s_fieldProgressBar);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnPay(long _menu, MENUsimplebutton_field button) {
        if (this.action_started) {
            return;
        }

        this.action_started = true;
        RecalculateAnimation();
        s_fieldGallons = FindTextField(_menu, "Gallons");
        s_fieldThisSale = FindTextField(_menu, "ThisSale");
        s_fieldProgressBar = FindTextField(_menu, "FuelTankIndicator");

        MENU_ranger ranger = FindScroller(_menu, "RANGER button");

        menues.SetIgnoreEvents(ranger.nativePointer, true);

        for (int i = 0; i < this.buttons.length; ++i) {
            menues.SetIgnoreEvents(this.buttons[i], true);
        }

        menues.SetScriptObjectAnimation(_menu, s_animid, this, "FinalAnimation");
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnExit(long _menu, SMenu button) {
        OnCancel(this.common.s_menu, null);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnCancel(long _menu, MENUsimplebutton_field button) {
        if (this.action_started) {
            return;
        }

        this.action_started = true;

        if (s_fieldProgressBar != null) {
            menues.StopScriptAnimation(s_animid);
            s_fieldGallons = null;
            s_fieldThisSale = null;
            s_fieldProgressBar = null;
            s_info = null;
        }

        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    /**
     * Method description
     *
     *
     * @param _price
     * @param text
     * @param macro
     *
     * @return
     */
    public String FormatPrice(int _price, String text, String macro) {
        int i_price = (int) Math.floor(_price / 100.0D);
        int m_price = _price - (i_price * 100);
        String i_ret;

        if (i_price >= 1000) {
            i_ret = "" + i_price;
        } else {
            if (i_price >= 100) {
                i_ret = "0" + i_price;
            } else {
                if (i_price >= 10) {
                    i_ret = "00" + i_price;
                } else {
                    i_ret = "000" + i_price;
                }
            }
        }

        String m_ret;

        if (m_price >= 10) {
            m_ret = "." + m_price;
        } else {
            m_ret = ".0" + m_price;
        }

        if ((text == null) || (macro == null)) {
            return i_ret + m_ret;
        }

        KeyPair[] keys = new KeyPair[1];

        keys[0] = new KeyPair(macro, i_ret + m_ret);

        return MacroKit.Parse(text, keys);
    }

    /**
     * Method description
     *
     *
     * @param _price
     * @param text
     * @param macro
     *
     * @return
     */
    public String FormatPriceShort(int _price, String text, String macro) {
        int i_price = (int) Math.floor(_price / 100.0D);
        int m_price = _price - (i_price * 100);
        String i_ret;

        if (i_price >= 100) {
            i_ret = "" + i_price;
        } else {
            if (i_price >= 10) {
                i_ret = "0" + i_price;
            } else {
                i_ret = "00" + i_price;
            }
        }

        String m_ret;

        if (m_price >= 10) {
            m_ret = "." + m_price;
        } else {
            m_ret = ".0" + m_price;
        }

        if ((text == null) || (macro == null)) {
            return i_ret + m_ret;
        }

        KeyPair[] keys = new KeyPair[1];

        keys[0] = new KeyPair(macro, i_ret + m_ret);

        return MacroKit.Parse(text, keys);
    }

    /**
     * Method description
     *
     *
     * @param _gallon
     *
     * @return
     */
    public String FormatGallonsShort(int _gallon) {
        int gallon = _gallon * 100;
        int i_price = (int) Math.floor(gallon / 100.0D);
        int m_price = gallon - (i_price * 100);
        String i_ret;

        if (i_price >= 100) {
            i_ret = "" + i_price;
        } else {
            if (i_price >= 10) {
                i_ret = "0" + i_price;
            } else {
                i_ret = "00" + i_price;
            }
        }

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
     * @param _menu
     * @param ranger
     */
    public void OnScroller(long _menu, MENU_ranger ranger) {
        gameinfo info = gameinfo.script;

        info.m_iCurBought = ranger.current_value;
        SetFieldText(_menu, "TotalGallons", FormatGallonsShort(info.m_iCurBought));
        SetFieldText(_menu, "TotalPrice",
                     FormatPrice(info.m_iCurBought * info.m_iPricePerLitre, this.totalPriceText, "GAS_TOTAL_PRICE"));
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnFull(long _menu, MENUsimplebutton_field field) {
        MENU_ranger ranger = FindScroller(_menu, "RANGER button");

        ranger.current_value = this.m_iMaxPossiblePurchase;
        menues.UpdateField(ranger);
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
        return "gasMENU";
    }

    /**
     * @return the fullButton
     */
    public static int getFullButton() {
        return full_button;
    }

    /**
     * @return the ranger
     */
    public static String getRanger() {
        return RANGER;
    }

    /**
     * @return the rangerBack
     */
    public static String getRangerBack() {
        return RANGER_BACK;
    }
}


//~ Formatted in DD Std on 13/08/26
