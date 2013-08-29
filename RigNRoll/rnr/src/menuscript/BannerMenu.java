/*
 * @(#)BannerMenu.java   13/08/26
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

import rnr.src.menu.Common;
import rnr.src.menu.DateData;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.TimeData;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.rnrconfig.IconMappings;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.loc;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class BannerMenu implements menucreation {
    private static final String LOGO_FIELD = "THE RACE LOGOTYPE";
    private static final String FIELD_RACE_NAME = "RaceName";
    MENUText_field m_cdownfield = null;
    int m_animid = -1;
    CoreTime s_start_date = null;
    String s_controlgroup = null;
    boolean need_race_name = false;
    boolean isCountDown = false;
    int s_raceid = -1;
    String s_race_logo_id = null;
    int s_winmoney = -1;
    int s_rating = -1;

    /** Field description */
    public Common common;
    private final String menuid;
    String race_name;
    String logo_name;
    String s_start;
    String s_finish;
    TimeData s_time;
    DateData s_date;
    String s_cityname;

    BannerMenu(String menuid) {
        this.menuid = menuid;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return this.menuid;
    }

    /**
     * Method description
     *
     *
     * @param menu
     *
     * @return
     */
    public static long createSameMenu(BannerMenu menu) {
        BannerMenu newmenu = new BannerMenu(menu.menuid);

        newmenu.race_name = menu.race_name;
        newmenu.need_race_name = menu.need_race_name;
        newmenu.s_controlgroup = menu.s_controlgroup;
        newmenu.s_raceid = menu.s_raceid;
        newmenu.s_start = menu.s_start;
        newmenu.s_finish = menu.s_finish;
        newmenu.s_rating = menu.s_rating;
        newmenu.s_time = menu.s_time;
        newmenu.s_date = menu.s_date;
        newmenu.isCountDown = menu.isCountDown;
        newmenu.s_cityname = menu.s_cityname;
        newmenu.s_race_logo_id = menu.s_race_logo_id;
        newmenu.s_start_date = menu.s_start_date;
        newmenu.m_animid = -1;

        return menues.createSimpleMenu(newmenu, 100000000.0D, "", 1600, 1200, 600, 300, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param raceid
     * @param date
     *
     * @return
     */
    public static long CreateStellaCheckIn(int raceid, CoreTime date) {
        String menuid = "";

        switch (raceid) {
         case 1 :
             menuid = "racecheckin04MENU";

             break;

         case 2 :
             menuid = "racecheckin03MENU";

             break;

         case 3 :
             menuid = "racecheckin02MENU";

             break;

         case 4 :
             menuid = "racecheckin01MENU";
        }

        BannerMenu bmenu = new BannerMenu(menuid);

        bmenu.s_raceid = raceid;
        bmenu.isCountDown = true;
        bmenu.s_start_date = date;
        bmenu.s_controlgroup = "STELLA Race" + Converts.bigRaceSuffixes(raceid) + " - Check-IN IN";

        return menues.createSimpleMenu(bmenu, 100000000.0D, "", 1600, 1200, 600, 150, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param raceid
     *
     * @return
     */
    public static long CreateStellaStartIn(int raceid) {
        String menuid = "";

        switch (raceid) {
         case 1 :
             menuid = "racestartin04MENU";

             break;

         case 2 :
             menuid = "racestartin03MENU";

             break;

         case 3 :
             menuid = "racestartin02MENU";

             break;

         case 4 :
             menuid = "racestartin01MENU";
        }

        BannerMenu bmenu = new BannerMenu(menuid);

        bmenu.s_raceid = raceid;
        bmenu.isCountDown = true;
        bmenu.s_controlgroup = "STELLA Race" + Converts.bigRaceSuffixes(raceid) + " - Start IN";

        return menues.createSimpleMenu(bmenu, 100000000.0D, "", 1600, 1200, 600, 150, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static long CreateStellaPrepareToOrders() {
        BannerMenu bmenu = new BannerMenu("stellaPreparingToOrdersMENU");

        bmenu.isCountDown = true;
        bmenu.s_controlgroup = "STELLA - PREPARING TO ORDERS";

        return menues.createSimpleMenu(bmenu, 100000000.0D, "", 1600, 1200, 600, 150, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static long CreateStellaPrepareToRace() {
        BannerMenu bmenu = new BannerMenu("stellaPreparingToRaceMENU");

        bmenu.isCountDown = true;
        bmenu.s_controlgroup = "STELLA - PREPARING TO RACE";

        return menues.createSimpleMenu(bmenu, 100000000.0D, "", 1600, 1200, 600, 150, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param cityname
     *
     * @return
     */
    public static long CreateStellaWelcome(String cityname) {
        BannerMenu bmenu = new BannerMenu("stellaWilcomMENU");

        bmenu.s_controlgroup = "STELLA - WELCOME";
        bmenu.s_cityname = cityname;

        return menues.createSimpleMenu(bmenu, 100000000.0D, "", 1600, 1200, 600, 150, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param raceid
     * @param start
     * @param finish
     * @param shortRacename
     * @param logoname
     * @param winmoney
     * @param rating
     * @param time
     * @param date
     *
     * @return
     */
    public static long CreateBannerMenu(int raceid, String start, String finish, String shortRacename, String logoname,
            int winmoney, int rating, TimeData time, DateData date) {
        String menuid = "";
        String grouop_suffix = "";

        switch (raceid) {
         case 1 :
             menuid = "banner04MENU";
             grouop_suffix = "04";

             break;

         case 2 :
             menuid = "banner03MENU";
             grouop_suffix = "03";

             break;

         case 3 :
             menuid = "banner02MENU";
             grouop_suffix = "02";

             break;

         case 4 :
             menuid = "banner01MENU";
             grouop_suffix = "01";
        }

        BannerMenu bmenu = new BannerMenu(menuid);

        bmenu.race_name = loc.getBigraceFullName(shortRacename);
        bmenu.need_race_name = true;
        bmenu.s_raceid = raceid;
        bmenu.s_start = start;
        bmenu.s_time = time;
        bmenu.s_date = date;
        bmenu.s_race_logo_id = logoname;
        bmenu.s_controlgroup = "BANNER Race" + grouop_suffix;

        return menues.createSimpleMenu(bmenu, 100000000.0D, "", 1600, 1200, 600, 300, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        long race_logo = menues.FindFieldInMenu(_menu, "THE RACE LOGOTYPE");

        if (race_logo != 0L) {
            if (this.s_race_logo_id != null) {
                menues.SetShowField(race_logo, true);
                IconMappings.remapSmallRaceLogos(this.s_race_logo_id, race_logo);
            } else {
                menues.SetShowField(race_logo, false);
            }
        }

        ClearStatic2();
        menues.WindowSet_ShowCursor(_menu, false);
        menues.SetStopWorld(_menu, false);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {
        if (this.m_animid == -1) {
            return;
        }

        menues.StopScriptAnimation(this.m_animid);
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

        long[] testcontrols = menues.InitXml(this.common.GetMenu(), Common.ConstructPath("menu_banner.xml"),
                                  this.s_controlgroup);

        if (testcontrols.length == 0) {
            if (this.s_controlgroup == "BANNER Race" + Converts.bigRaceSuffixes(this.s_raceid)) {
                this.s_raceid = 0;
                this.s_controlgroup = "BANNER Race" + Converts.bigRaceSuffixes(this.s_raceid);
                menues.InitXml(this.common.GetMenu(), Common.ConstructPath("menu_banner.xml"), this.s_controlgroup);
            } else {
                return;
            }
        }

        if ((this.s_start != null) && (this.s_date != null)) {
            KeyPair[] a1 = { new KeyPair("START", this.s_start), new KeyPair("DATE", ((this.s_date.month > 9)
                    ? "" + this.s_date.month
                    : new StringBuilder().append("0").append(this.s_date.month).toString()) + "/"
                        + ((this.s_date.day > 9)
                           ? "" + this.s_date.day
                           : new StringBuilder().append("0").append(this.s_date.day).toString())) };

            MacroKit.ApplyToTextfield(this.common.FindTextField("CitiesNames"), a1);
        }

        if (this.need_race_name) {
            long field = menues.FindFieldInMenu(_menu, "RaceName");

            menues.SetFieldText(field, this.race_name);
        }

        if (this.s_rating != -1) {
            KeyPair[] a2 = { new KeyPair("RATING", this.s_rating + "") };

            MacroKit.ApplyToTextfield(this.common.FindTextField("ParticipationRating"), a2);
        }

        if (this.s_cityname != null) {
            KeyPair[] a = { new KeyPair("CITY", this.s_cityname) };

            MacroKit.ApplyToTextfield(this.common.FindTextField("To The City"), a);
        }

        if (this.isCountDown) {
            this.m_cdownfield = this.common.FindTextField("Time");
        }

        if (this.s_start_date != null) {
            MENUText_field text = this.common.FindTextField("Text - Start Date and Time");

            if ((text != null) && (text.text != null)) {
                if (text.origtext == null) {
                    text.origtext = text.text;
                }

                text.text = Converts.ConvertDateAbsolute(text.origtext, this.s_start_date.gMonth(),
                        this.s_start_date.gDate(), this.s_start_date.gYear(), this.s_start_date.gHour(),
                        this.s_start_date.gMinute());
                menues.UpdateField(text);
            }
        }

        ClearStatic();
    }

    void setTime(double T) {
        if (null == this.m_cdownfield) {
            return;
        }

        int hours = (int) Math.floor(T / 3600.0D);
        int minutes = (int) Math.floor((T - (hours * 3600.0D)) / 60.0D);
        int seconds = (int) Math.floor(T - (hours * 3600.0D) - (minutes * 60.0D));

        Converts.ConverTime3Plus2(this.m_cdownfield, hours, minutes, seconds);
    }

    /**
     * Method description
     *
     */
    public void ClearStatic() {
        this.s_raceid = -1;
        this.s_start = null;
        this.s_finish = null;
        this.s_rating = -1;
        this.s_time = null;
        this.s_date = null;
        this.s_start_date = null;
    }

    /**
     * Method description
     *
     */
    public void ClearStatic2() {
        this.s_race_logo_id = null;
    }

    /**
     * @return the logoField
     */
    public static String getLogoField() {
        return LOGO_FIELD;
    }

    /**
     * @return the fieldRaceName
     */
    public static String getFieldRaceName() {
        return FIELD_RACE_NAME;
    }
}


//~ Formatted in DD Std on 13/08/26
