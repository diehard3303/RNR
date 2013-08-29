/*
 * @(#)gameinfo.java   13/08/28
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


package rnr.src.rnrscr;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.gameobj.CarParts;
import rnr.src.menu.CInteger;
import rnr.src.menu.DateData;
import rnr.src.menuscript.OfficeGameData;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.eng;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.text.MessageFormat;

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class gameinfo {

    /** Field description */
    public static gameinfo script = null;

    /** Field description */
    public int m_StartMonth = 5;

    /** Field description */
    public int m_StartYear = 2012;

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector<CInteger> m_RestyleBodyColorPricelist = new Vector<CInteger>();

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector<CInteger> m_RestyleBodyColorMetallicPricelist = new Vector<CInteger>();

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector<CInteger> m_RestyleBodyColorChameleonPricelist = new Vector<CInteger>();

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector<CInteger> m_RestyleBodyColorMetallicChameleonPricelist = new Vector<CInteger>();

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector<CInteger> m_RestyleLeatherPricelist = new Vector<CInteger>();

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector<CInteger> m_RestyleClothPricelist = new Vector<CInteger>();

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector<CInteger> m_RestyleDashPricelist = new Vector<CInteger>();

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector<CInteger> m_RestyleDashGaugesPricelist = new Vector<CInteger>();

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector<CInteger> m_RestyleGlassesPricelist = new Vector<CInteger>();

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector<?> CurrenWareHouseOrders;

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector<?> aWarehouses;

    /** Field description */
    public int m_CurrentWarehouse;

    /** Field description */
    public int m_CurrentOrder;

    /** Field description */
    public int m_HighlightedOrder;

    /** Field description */
    public int m_iStartLitres;

    /** Field description */
    public int m_iMaxLitres;

    /** Field description */
    public int m_iPricePerLitre;

    /** Field description */
    public int m_iCurBought;

    /** Field description */
    public int m_iMaxBought;

    /** Field description */
    public CarParts m_CarParts;

    /** Field description */
    public int m_iRepairTableVisSize;

    /** Field description */
    public int m_iCurScroll;

    /** Field description */
    public String m_sVehType;

    /** Field description */
    public String m_sVehModelInternal;

    /** Field description */
    public String m_sVehManufactLocalized;

    /** Field description */
    public String m_sVehModelLocalized;

    /** Field description */
    public String m_sTruckName;

    /** Field description */
    public int m_iTruckInstance;

    /** Field description */
    public long m_pVehiclePointer;

    /** Field description */
    public long m_pVehicleActorPointer;

    /** Field description */
    public int m_iVehicleMainColor;

    /** Field description */
    public int m_iVehicleLeather;

    /** Field description */
    public int m_iVehicleCloth;

    /** Field description */
    public int m_iVehicleDash;

    /** Field description */
    public int m_iVehicleDashGauges;

    /** Field description */
    public int m_iVehicleGlasses;

    /** Field description */
    public boolean m_bVehicleMettalic;

    /** Field description */
    public OfficeGameData m_GameData;

    /** Field description */
    public MotelData m_moteldata;

    /** Field description */
    public long m_pPlayer;

    /** Field description */
    public int m_iTotalAuth;

    /** Field description */
    public int m_iTestMenu;

    /** Field description */
    public cCreateWindowDispatch CW_info;

    /**
     * Constructs ...
     *
     */
    public gameinfo() {
        script = this;
        FirstFrame();
    }

    /**
     * Method description
     *
     *
     * @param value
     *
     * @return
     */
    public static String ConvertMoney(int value) {
        if (value < 0) {
            return "-$ " + ConvertNumeric(-value);
        }

        return "$ " + ConvertNumeric(value);
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param precision
     *
     * @return
     */
    public static String ConvertDouble(double value, int precision) {
        if (precision == 0) {
            return (int) value + "";
        }

        StringBuffer buf = new StringBuffer("");

        for (int i = 0; i < precision; ++i) {
            buf.append("#");
        }

        MessageFormat mf = new MessageFormat("{0,number,#." + buf.toString() + "}");
        Object[] objs = { new Double(value) };

        return mf.format(objs);
    }

    /**
     * Method description
     *
     *
     * @param hour
     * @param min
     *
     * @return
     */
    public static String ConvertTime(int hour, int min) {
        return ((hour >= 10)
                ? hour + ""
                : new StringBuilder().append("0").append(hour).toString()) + ":" + ((min >= 10)
                ? min + ""
                : new StringBuilder().append("0").append(min).toString());
    }

    /**
     * Method description
     *
     *
     * @param mph
     *
     * @return
     */
    public static String ConvertSpeed(double mph) {
        return ConvertDouble(mph, 2) + " mph";
    }

    /**
     * Method description
     *
     *
     * @param hour
     * @param min
     * @param sec
     *
     * @return
     */
    public static String ConvertTime(int hour, int min, int sec) {
        return ((hour >= 10)
                ? hour + ""
                : new StringBuilder().append("0").append(hour).toString()) + ":" + ((min >= 10)
                ? min + ""
                : new StringBuilder().append("0").append(min).toString()) + ":" + ((sec >= 10)
                ? sec + ""
                : new StringBuilder().append("0").append(sec).toString());
    }

    /**
     * Method description
     *
     *
     * @param value
     *
     * @return
     */
    public static String ConvertNumeric(int value) {
        StringBuffer start = new StringBuffer("" + value);
        int len = start.length();
        int i = 1;

        while (value / 1000 > 0) {
            start.insert(len - (i * 3), ' ');
            value /= 1000;
            ++i;
        }

        return start.toString();
    }

    /**
     * Method description
     *
     *
     * @param value
     *
     * @return
     */
    public static String ConvertCelcius(double value) {
        return ((value >= 0.0D)
                ? "+"
                : "-") + ConvertDouble(value, 0) + "@C";
    }

    /**
     * Method description
     *
     *
     * @param value
     *
     * @return
     */
    public static String ConvertFahrenheit(double value) {
        return ((value >= 0.0D)
                ? "+"
                : "-") + ConvertDouble(value * 9.0D / 5.0D + 32.0D, 0) + "@F";
    }

    /**
     * Method description
     *
     *
     * @param month
     *
     * @return
     */
    public static String ConvertMonth(int month) {
        switch (month - 1) {
         case 0 :
             return "January";

         case 1 :
             return "February";

         case 2 :
             return "March";

         case 3 :
             return "April";

         case 4 :
             return "May";

         case 5 :
             return "June";

         case 6 :
             return "July";

         case 7 :
             return "August";

         case 8 :
             return "September";

         case 9 :
             return "October";

         case 10 :
             return "November";

         case 11 :
             return "December";
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @param d
     *
     * @return
     */
    public static String ConvertFullDate(DateData d) {
        return ConvertMonth(d.month) + " " + d.day + ", " + d.year;
    }

    /**
     * Method description
     *
     *
     * @param d
     *
     * @return
     */
    public static String ConvertBriefDate(DateData d) {
        return ConvertMonth(d.month) + " " + d.day;
    }

    /**
     * Method description
     *
     *
     * @param weight
     *
     * @return
     */
    public static String ConvertWeight(double weight) {
        return ConvertDouble(weight, 3) + " ton";
    }

    /**
     * Method description
     *
     *
     * @param distance
     *
     * @return
     */
    public static String ConvertDistance(double distance) {
        return ConvertDouble(distance + 0.5D, 0) + (((int) (distance + 0.5D) == 1)
                ? " mile"
                : " miles");
    }

    /**
     * Method description
     *
     */
    public void FirstFrame() {
        this.CurrenWareHouseOrders = new Vector<Object>();
        this.aWarehouses = new Vector<Object>();
        this.m_CarParts = new CarParts();
        this.m_moteldata = new MotelData();
        this.m_CurrentWarehouse = -1;
        this.m_CurrentOrder = -1;
        this.m_iStartLitres = 10;
        this.m_iMaxLitres = 100;
        this.m_iPricePerLitre = 234;
        this.CW_info = new cCreateWindowDispatch();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int GetSelectedOrder() {
        return this.m_CurrentOrder;
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings("unchecked")
    public void LoadRepairPrices() {
        Node top = XmlUtils.parse("..\\data\\config\\restyle_prices.xml");
        NodeList root = (top != null)
                        ? top.getNamedChildren("types")
                        : null;
        NodeList part = ((root != null) && (!(root.isEmpty())))
                        ? root.get(0).getNamedChildren("body")
                        : null;
        Node node = ((part != null) && (!(part.isEmpty())))
                    ? (Node) part.get(0)
                    : null;
        String str_price = (node != null)
                           ? node.getAttribute("price")
                           : "0";
        CInteger price = new CInteger(0);

        try {
            price.value = Integer.parseInt(str_price);
        } catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }

        for (int i = 0; i < 30; ++i) {
            this.m_RestyleBodyColorPricelist.add(price);
        }

        part = ((root != null) && (!(root.isEmpty())))
               ? root.get(0).getNamedChildren("body_metallic")
               : null;
        node = ((part != null) && (!(part.isEmpty())))
               ? (Node) part.get(0)
               : null;
        str_price = (node != null)
                    ? node.getAttribute("price")
                    : "0";
        price = new CInteger(0);

        try {
            price.value = Integer.parseInt(str_price);
        } catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }

        for (int i = 0; i < 30; ++i) {
            this.m_RestyleBodyColorMetallicPricelist.add(price);
        }

        part = ((root != null) && (!(root.isEmpty())))
               ? root.get(0).getNamedChildren("body_chameleon")
               : null;
        node = ((part != null) && (!(part.isEmpty())))
               ? (Node) part.get(0)
               : null;
        str_price = (node != null)
                    ? node.getAttribute("price")
                    : "0";
        price = new CInteger(0);

        try {
            price.value = Integer.parseInt(str_price);
        } catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }

        for (int i = 0; i < 30; ++i) {
            this.m_RestyleBodyColorChameleonPricelist.add(price);
        }

        part = ((root != null) && (!(root.isEmpty())))
               ? root.get(0).getNamedChildren("body_metallic_chameleon")
               : null;
        node = ((part != null) && (!(part.isEmpty())))
               ? (Node) part.get(0)
               : null;
        str_price = (node != null)
                    ? node.getAttribute("price")
                    : "0";
        price = new CInteger(0);

        try {
            price.value = Integer.parseInt(str_price);
        } catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }

        for (int i = 0; i < 30; ++i) {
            this.m_RestyleBodyColorMetallicChameleonPricelist.add(price);
        }

        part = ((root != null) && (!(root.isEmpty())))
               ? root.get(0).getNamedChildren("leather")
               : null;
        node = ((part != null) && (!(part.isEmpty())))
               ? (Node) part.get(0)
               : null;
        str_price = (node != null)
                    ? node.getAttribute("price")
                    : "0";
        price = new CInteger(0);

        try {
            price.value = Integer.parseInt(str_price);
        } catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }

        for (int i = 0; i < 30; ++i) {
            this.m_RestyleLeatherPricelist.add(price);
        }

        part = ((root != null) && (!(root.isEmpty())))
               ? root.get(0).getNamedChildren("cloth")
               : null;
        node = ((part != null) && (!(part.isEmpty())))
               ? (Node) part.get(0)
               : null;
        str_price = (node != null)
                    ? node.getAttribute("price")
                    : "0";
        price = new CInteger(0);

        try {
            price.value = Integer.parseInt(str_price);
        } catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }

        for (int i = 0; i < 30; ++i) {
            this.m_RestyleClothPricelist.add(price);
        }

        part = ((root != null) && (!(root.isEmpty())))
               ? root.get(0).getNamedChildren("dash")
               : null;
        node = ((part != null) && (!(part.isEmpty())))
               ? (Node) part.get(0)
               : null;
        str_price = (node != null)
                    ? node.getAttribute("price")
                    : "0";
        price = new CInteger(0);

        try {
            price.value = Integer.parseInt(str_price);
        } catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }

        for (int i = 0; i < 30; ++i) {
            this.m_RestyleDashPricelist.add(price);
        }

        part = ((root != null) && (!(root.isEmpty())))
               ? root.get(0).getNamedChildren("dash_gauges")
               : null;
        node = ((part != null) && (!(part.isEmpty())))
               ? (Node) part.get(0)
               : null;
        str_price = (node != null)
                    ? node.getAttribute("price")
                    : "0";
        price = new CInteger(0);

        try {
            price.value = Integer.parseInt(str_price);
        } catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }

        for (int i = 0; i < 30; ++i) {
            this.m_RestyleDashGaugesPricelist.add(price);
        }

        part = ((root != null) && (!(root.isEmpty())))
               ? root.get(0).getNamedChildren("glasses")
               : null;
        node = ((part != null) && (!(part.isEmpty())))
               ? (Node) part.get(0)
               : null;
        str_price = (node != null)
                    ? node.getAttribute("price")
                    : "0";
        price = new CInteger(0);

        try {
            price.value = Integer.parseInt(str_price);
        } catch (NumberFormatException e) {
            eng.log("ERRORR restyle_prices.xml. Attribute price is not int.");
        }

        for (int i = 0; i < 30; ++i) {
            this.m_RestyleGlassesPricelist.add(price);
        }
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public class MotelData {

        /** Field description */
        public int iCheckInHour;

        /** Field description */
        public int iCheckInMin;

        /** Field description */
        public int iCheckOutHour;

        /** Field description */
        public int iCheckOutMin;

        /** Field description */
        public double dCostPerHour;

        /** Field description */
        public CoreTime current_date;

        /** Field description */
        public boolean bEnoughtMoney;

        /** Field description */
        public String city_name;

        /**
         * Constructs ...
         *
         */
        public MotelData() {
            this.iCheckOutHour = (this.iCheckInHour = 10);
            this.iCheckOutMin = (this.iCheckInMin = 12);
            this.dCostPerHour = 20.0D;
            this.bEnoughtMoney = true;
            this.current_date = new CoreTime(1, 1, 1, 1, 1);
            this.city_name = "Unknow";
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public class OneTitrInfo {

        /** Field description */
        public float time_to_show;

        /** Field description */
        public float time_elapsed;

        /** Field description */
        public int nom_shifts;

        /** Field description */
        public long p_menu;

        /**
         * Constructs ...
         *
         */
        public OneTitrInfo() {
            this.p_menu = 0L;
            this.time_to_show = 0.0F;
            this.time_elapsed = 0.0F;
            this.nom_shifts = 1;
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public class Warehouse {

        /** Field description */
        public String name;

        /** Field description */
        public double x;

        /** Field description */
        public double y;

        /** Field description */
        public int ID;

        /** Field description */
        public int arrowindex;

        /** Field description */
        public boolean bIsMine;
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public class cCreateWindowDispatch {

        /** Field description */
        public boolean OnSpecObject;

        /** Field description */
        public boolean InsideBar;

        /** Field description */
        public boolean Titre;

        /** Field description */
        public Vector<String> strings;

        /** Field description */
        public String menuId;

        /**
         * Constructs ...
         *
         */
        public cCreateWindowDispatch() {
            this.OnSpecObject = false;
            this.InsideBar = false;
            this.Titre = false;
            this.strings = new Vector<String>();
        }

        /**
         * Method description
         *
         */
        public void ClearAll() {
            this.strings.clear();
            this.OnSpecObject = false;
            this.InsideBar = false;
            this.Titre = false;
        }

        /**
         * Method description
         *
         *
         * @param str
         */
        public void AddString(String str) {
            this.strings.add(str);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
