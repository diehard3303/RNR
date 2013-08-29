/*
 * @(#)Converts.java   13/08/26
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

import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.menues;
import rnr.src.rnrcore.MacroBody;
import rnr.src.rnrcore.MacroBuilder;
import rnr.src.rnrcore.Macros;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.loc;

//~--- JDK imports ------------------------------------------------------------

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Converts {

    /**
     * Method description
     *
     *
     * @param height
     * @param baseline
     * @param texth
     *
     * @return
     */
    public static int HeightToLines(int height, int baseline, int texth) {
        return ((height - baseline) / texth);
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

    private static String ConvertMonthLoc(int month) {
        switch (month - 1) {
         case 0 :
             return loc.getDateString("JANUARY");

         case 1 :
             return loc.getDateString("FEBRUARY");

         case 2 :
             return loc.getDateString("MARCH");

         case 3 :
             return loc.getDateString("APRIL");

         case 4 :
             return loc.getDateString("MAY");

         case 5 :
             return loc.getDateString("JUNE");

         case 6 :
             return loc.getDateString("JULY");

         case 7 :
             return loc.getDateString("AUGUST");

         case 8 :
             return loc.getDateString("SEPTEMBER");

         case 9 :
             return loc.getDateString("OCTOBER");

         case 10 :
             return loc.getDateString("NOVEMBER");

         case 11 :
             return loc.getDateString("DECEMBER");
        }

        return loc.getDateString("JANUARY");
    }

    /**
     * Method description
     *
     *
     * @param hours
     *
     * @return
     */
    public static String makeClock(int hours) {
        String str_hours = "00";

        if (hours >= 10) {
            str_hours = "" + hours;
        } else if ((hours >= 1) && (hours < 10)) {
            str_hours = "0" + hours;
        }

        return str_hours;
    }

    private static String makeClock00(int hours) {
        String str_hours = "000";

        if (hours >= 100) {
            str_hours = "" + hours;
        } else if ((hours >= 10) && (hours < 100)) {
            str_hours = "0" + hours;
        } else if ((hours >= 1) && (hours < 10)) {
            str_hours = "00" + hours;
        }

        return str_hours;
    }

    private static String GetPostfixForMotel(int _hour, int _min, int _sec) {
        if (eng.use_12HourTimeFormat()) {
            if (_hour < 12) {
                return "A";
            }

            return "P";
        }

        return "";
    }

    private static String GetPostfix(int _hour, int _min, int _sec) {
        if (eng.use_12HourTimeFormat()) {
            if (_hour < 12) {
                return " AM";
            }

            return " PM";
        }

        return "";
    }

    private static String makeClockHour(int _hour, int _min, int _sec) {
        if (eng.use_12HourTimeFormat()) {
            return makeClock((_hour % 12 == 0)
                             ? 12
                             : _hour % 12);
        }

        return makeClock(_hour);
    }

    /**
     * Method description
     *
     *
     * @param text
     * @param _hour
     * @param _min
     * @param _sec
     */
    public static void ConverTimeAbsolute(MENUText_field text, int _hour, int _min, int _sec) {
        if ((text != null) && (text.text != null)) {
            String hour = makeClockHour(_hour, _min, _sec);
            String min = makeClock(_min) + ((MacroKit.HasMacro(text.text, "SECONDS"))
                                            ? ""
                                            : GetPostfix(_hour, _min, _sec));
            String sec = makeClock(_sec) + ((!(MacroKit.HasMacro(text.text, "SECONDS")))
                                            ? ""
                                            : GetPostfix(_hour, _min, _sec));
            KeyPair[] key = { new KeyPair("HOURS", hour), new KeyPair("MINUTES", min), new KeyPair("SECONDS", sec) };

            MacroKit.ApplyToTextfield(text, key);
        }
    }

    /**
     * Method description
     *
     *
     * @param macro_string
     * @param _hour
     * @param _min
     * @param _sec
     *
     * @return
     */
    public static String ConverTimeAbsolute(String macro_string, int _hour, int _min, int _sec) {
        String hour = makeClockHour(_hour, _min, _sec);
        String min = makeClock(_min) + ((MacroKit.HasMacro(macro_string, "SECONDS"))
                                        ? ""
                                        : GetPostfix(_hour, _min, _sec));
        String sec = makeClock(_sec) + ((!(MacroKit.HasMacro(macro_string, "SECONDS")))
                                        ? ""
                                        : GetPostfix(_hour, _min, _sec));
        KeyPair[] key = { new KeyPair("HOURS", hour), new KeyPair("MINUTES", min), new KeyPair("SECONDS", sec) };
        String ret = MacroKit.Parse(macro_string, key);

        return ret;
    }

    /**
     * Method description
     *
     *
     * @param text
     * @param macro_string
     * @param _hour
     * @param _min
     * @param _sec
     */
    public static void ConverTimeAbsolute(MENUText_field text, String macro_string, int _hour, int _min, int _sec) {
        String hour = makeClockHour(_hour, _min, _sec);
        String min = makeClock(_min) + ((MacroKit.HasMacro(macro_string, "SECONDS"))
                                        ? ""
                                        : GetPostfix(_hour, _min, _sec));
        String sec = makeClock(_sec) + ((!(MacroKit.HasMacro(macro_string, "SECONDS")))
                                        ? ""
                                        : GetPostfix(_hour, _min, _sec));
        KeyPair[] key = { new KeyPair("HOURS", hour), new KeyPair("MINUTES", min), new KeyPair("SECONDS", sec) };

        text.text = MacroKit.Parse(macro_string, key);
        menues.UpdateField(text);
    }

    /**
     * Method description
     *
     *
     * @param text
     * @param _hour
     * @param _min
     * @param _sec
     */
    public static void ConvertTimeAllowed(MENUText_field text, int _hour, int _min, int _sec) {
        String min = makeClock00(_min + _hour * 60);
        String sec = makeClock(_sec);
        String string = min + ":" + sec;
        KeyPair[] macro = { new KeyPair("TIME_ALLOWED", string) };

        MacroKit.ApplyToTextfield(text, macro);
    }

    /**
     * Method description
     *
     *
     * @param text
     * @param _hour
     * @param _min
     * @param _sec
     */
    public static void ConvertTotalTime(MENUText_field text, int _hour, int _min, int _sec) {
        String min = makeClock00(_min + _hour * 60);
        String sec = makeClock(_sec);
        String string = min + ":" + sec;
        KeyPair[] macro = { new KeyPair("TOTAL_TIME", string) };

        MacroKit.ApplyToTextfield(text, macro);
    }

    /**
     * Method description
     *
     *
     * @param text
     * @param hour
     * @param min
     * @param sec
     */
    public static void ConvertTimeLeft(MENUText_field text, int hour, int min, int sec) {
        String string = ((hour >= 10)
                         ? hour + ""
                         : new StringBuilder().append("0").append(hour).toString()) + ":" + ((min >= 10)
                ? min + ""
                : new StringBuilder().append("0").append(min).toString()) + ":" + ((min >= 10)
                ? min + ""
                : new StringBuilder().append("0").append(sec).toString());
        KeyPair[] macro = { new KeyPair("TIME_LEFT", string) };

        MacroKit.ApplyToTextfield(text, macro);
    }

    /**
     * Method description
     *
     *
     * @param macro_string
     * @param _hour
     * @param _min
     *
     * @return
     */
    public static String ConverTimeForMotel(String macro_string, int _hour, int _min) {
        String min = makeClockHour(_hour, _min, 0);
        String sec = makeClock(_min) + GetPostfixForMotel(_hour, _min, 0);
        KeyPair[] key = { new KeyPair("HOURS", min), new KeyPair("MINUTES", sec) };
        String ret = MacroKit.Parse(macro_string, key);

        return ret;
    }

    /**
     * Method description
     *
     *
     * @param macro_string
     * @param _hour
     * @param _min
     *
     * @return
     */
    public static String ConverTimeAbsolute(String macro_string, int _hour, int _min) {
        String min = makeClockHour(_hour, _min, 0);
        String sec = makeClock(_min) + GetPostfix(_hour, _min, 0);
        KeyPair[] key = { new KeyPair("HOURS", min), new KeyPair("MINUTES", sec) };
        String ret = MacroKit.Parse(macro_string, key);

        return ret;
    }

    /**
     * Method description
     *
     *
     * @param macro_string
     * @param month
     * @param year
     *
     * @return
     */
    public static String ConvertDate(String macro_string, int month, int year) {
        KeyPair[] key = { new KeyPair("MONTH", ConvertMonthLoc(month)), new KeyPair("YEAR", "" + year) };

        return MacroKit.Parse(macro_string, key);
    }

    /**
     * Method description
     *
     *
     * @param text
     * @param month
     * @param date
     * @param year
     */
    public static void ConvertDate(MENUbutton_field text, int month, int date, int year) {
        KeyPair[] key = { new KeyPair("MONTH", ConvertMonthLoc(month)), new KeyPair("DATE", makeClock(date)),
                          new KeyPair("YEAR", "" + year) };

        if (text != null) {
            if (text.origtext == null) {
                text.origtext = text.text;
            }

            text.text = MacroKit.Parse(text.origtext, key);
            menues.UpdateField(text);
        }
    }

    /**
     * Method description
     *
     *
     * @param _source
     * @param month
     * @param date
     * @param year
     * @param _hour
     * @param _min
     *
     * @return
     */
    public static String ConvertDateLongAbsolute(String _source, int month, int date, int year, int _hour, int _min) {
        KeyPair[] key = { new KeyPair("MONTH", ConvertMonthLoc(month)), new KeyPair("DATE", makeClock(date)),
                          new KeyPair("YEAR", "" + year) };

        return ConverTimeAbsolute(MacroKit.Parse(_source, key), _hour, _min);
    }

    /**
     * Method description
     *
     *
     * @param _source
     * @param month
     * @param date
     * @param year
     * @param _hour
     * @param _min
     *
     * @return
     */
    public static String ConvertDateAbsolute(String _source, int month, int date, int year, int _hour, int _min) {
        KeyPair[] pair = { new KeyPair("FULL_DATE", loc.getDateString("FULL_DATE")) };
        String source = MacroKit.Parse(_source, pair);
        KeyPair[] pairs = new KeyPair[3];

        pairs[0] = new KeyPair("YEAR", "" + year);
        pairs[1] = new KeyPair("MONTH", makeClock(month));
        pairs[2] = new KeyPair("DATE", makeClock(date));

        return ConverTimeAbsolute(MacroKit.Parse(source, pairs), _hour, _min);
    }

    /**
     * Method description
     *
     *
     * @param month
     * @param date
     * @param year
     * @param _hour
     * @param _min
     *
     * @return
     */
    public static List<Macros> ConvertDateMacroAbsolute(int month, int date, int year, int _hour, int _min) {
        List<Macros> mocro_lst = new ArrayList<Macros>();

        mocro_lst.add(new Macros("YEAR", MacroBuilder.makeSimpleMacroBody("" + year)));
        mocro_lst.add(new Macros("MONTH", MacroBuilder.makeSimpleMacroBody(makeClock(month))));
        mocro_lst.add(new Macros("DATE", MacroBuilder.makeSimpleMacroBody(makeClock(date))));

        MacroBody body = MacroBuilder.makeMacroBody("DATE_TIME", "FULL_DATE", mocro_lst);
        Macros macros_full_date = new Macros("FULL_DATE", body);
        List<Macros> result = new ArrayList<Macros>();

        result.add(macros_full_date);
        result.add(new Macros("HOURS", MacroBuilder.makeSimpleMacroBody(makeClockHour(_hour, _min, 0))));
        result.add(new Macros("MINUTES",
                              MacroBuilder.makeSimpleMacroBody(makeClock(_min) + GetPostfix(_hour, _min, 0))));

        return result;
    }

    /**
     * Method description
     *
     *
     * @param _source
     * @param month
     * @param date
     * @param year
     * @param _hour
     * @param _min
     * @param _sec
     *
     * @return
     */
    public static String ConvertDateAbsolute(String _source, int month, int date, int year, int _hour, int _min,
            int _sec) {
        KeyPair[] pair = { new KeyPair("FULL_DATE", loc.getDateString("FULL_DATE")) };
        String source = MacroKit.Parse(_source, pair);
        KeyPair[] pairs = new KeyPair[3];

        pairs[0] = new KeyPair("YEAR", "" + year);
        pairs[1] = new KeyPair("MONTH", makeClock(month));
        pairs[2] = new KeyPair("DATE", makeClock(date));

        return ConverTimeAbsolute(MacroKit.Parse(source, pairs), _hour, _min, _sec);
    }

    /**
     * Method description
     *
     *
     * @param _source
     * @param month
     * @param date
     * @param year
     *
     * @return
     */
    public static String ConvertDate(String _source, int month, int date, int year) {
        KeyPair[] pair = { new KeyPair("FULL_DATE", loc.getDateString("FULL_DATE")) };
        String source = MacroKit.Parse(_source, pair);
        KeyPair[] pairs = new KeyPair[3];

        pairs[0] = new KeyPair("YEAR", "" + year);
        pairs[1] = new KeyPair("MONTH", makeClock(month));
        pairs[2] = new KeyPair("DATE", makeClock(date));

        return MacroKit.Parse(source, pairs);
    }

    /**
     * Method description
     *
     *
     * @param macro_string
     * @param _min
     * @param _sec
     *
     * @return
     */
    public static String ConverTime3Plus2(String macro_string, int _min, int _sec) {
        String min = makeClock00(_min);
        String sec = makeClock(_sec);
        KeyPair[] key = { new KeyPair("MINUTES", min), new KeyPair("SECONDS", sec) };

        return MacroKit.Parse(macro_string, key);
    }

    /**
     * Method description
     *
     *
     * @param macro_string
     * @param _hour
     * @param _min
     * @param _sec
     *
     * @return
     */
    public static String ConverTime3Plus2Total(String macro_string, int _hour, int _min, int _sec) {
        String min = makeClock00(_min + _hour * 60);
        String sec = makeClock(_sec);
        KeyPair[] key = { new KeyPair("TOTAL_TIME", min + ":" + sec) };

        return MacroKit.Parse(macro_string, key);
    }

    /**
     * Method description
     *
     *
     * @param text
     * @param _hour
     * @param _min
     * @param _sec
     */
    public static void ConverTime3Plus2(MENUText_field text, int _hour, int _min, int _sec) {
        String min = makeClock00(_min + _hour * 60);
        String sec = makeClock(_sec);
        KeyPair[] key = { new KeyPair("MINUTES", min), new KeyPair("SECONDS", sec) };

        MacroKit.ApplyToTextfield(text, key);
    }

    /**
     * Method description
     *
     *
     * @param value
     *
     * @return
     */
    public static String ConvertNumeric2(int value) {
        return "0" + value;
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
    public static String ConvertSignedInit(int value) {
        return ((value >= 0)
                ? "+"
                : "") + value;
    }

    /**
     * Method description
     *
     *
     * @param value
     *
     * @return
     */
    public static double ConvertFahrenheit(double value) {
        double f = value * 9.0D / 5.0D + 32.0D;

        return f;
    }

    /**
     * Method description
     *
     *
     * @param raceid
     *
     * @return
     */
    public static String bigRaceSuffixes(int raceid) {
        String grouop_suffix = "";

        switch (raceid) {
         case 1 :
             grouop_suffix = "04";

             break;

         case 2 :
             grouop_suffix = "03";

             break;

         case 3 :
             grouop_suffix = "02";

             break;

         case 4 :
             grouop_suffix = "01";
        }

        return grouop_suffix;
    }
}


//~ Formatted in DD Std on 13/08/26
