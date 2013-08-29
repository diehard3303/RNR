/*
 * @(#)CoreTime.java   13/08/26
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


package rnr.src.rnrcore;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class CoreTime implements gameDate {

    /** Field description */
    public static final int LEAPYEAR_DAYS = 366;

    /** Field description */
    public static final int NON_LEAPYEAR_DAYS = 365;
    private static final int INDEX_YEAR = 0;
    private static final int INDEX_MONTH = 1;
    private static final int INDEX_DATE = 2;
    private static final int INDEX_HOUR = 3;
    private static final int INDEX_MINUTE = 4;
    private static final int INDEX_MAX = 4;
    int year;
    int month;
    int date;
    int hour;
    int minuten;

    /**
     * Constructs ...
     *
     */
    public CoreTime() {
        update();
    }

    /**
     * Constructs ...
     *
     *
     * @param copy
     */
    public CoreTime(CoreTime copy) {
        this.year = copy.year;
        this.month = copy.month;
        this.date = copy.date;
        this.hour = copy.hour;
        this.minuten = copy.minuten;
    }

    /**
     * Constructs ...
     *
     *
     * @param data
     */
    public CoreTime(String data) {
        if (null != data) {
            String[] coreTimePacked = data.split(" ");

            if (4 < coreTimePacked.length) {
                try {
                    this.year = Integer.parseInt(coreTimePacked[0]);
                    this.month = Integer.parseInt(coreTimePacked[1]);
                    this.date = Integer.parseInt(coreTimePacked[2]);
                    this.hour = Integer.parseInt(coreTimePacked[3]);
                    this.minuten = Integer.parseInt(coreTimePacked[4]);
                } catch (NumberFormatException e) {
                    this.year = 0;
                    this.month = 0;
                    this.date = 0;
                    this.hour = 0;
                    this.minuten = 0;
                    update();
                }
            } else {
                update();
            }
        } else {
            update();
        }
    }

    /**
     * Constructs ...
     *
     *
     * @param year
     * @param month
     * @param date
     * @param hour
     * @param minuten
     */
    public CoreTime(int year, int month, int date, int hour, int minuten) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minuten = minuten;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int gDate() {
        return this.date;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int gHour() {
        return this.hour;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int gMinute() {
        return this.minuten;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int gMonth() {
        return this.month;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int gYear() {
        return this.year;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void sDate(int value) {
        this.date = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void sHour(int value) {
        this.hour = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void sMinute(int value) {
        this.minuten = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void sMonth(int value) {
        this.month = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void sYear(int value) {
        this.year = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%d %d %d %d %d", new Object[] { Integer.valueOf(gYear()), Integer.valueOf(gMonth()),
                Integer.valueOf(gDate()), Integer.valueOf(gHour()), Integer.valueOf(gMinute()) });
    }

    /**
     * Method description
     *
     *
     * @param minus_days
     */
    public void minusDays(int minus_days) {
        if (minus_days <= 0) {
            return;
        }

        if (this.date > minus_days) {
            this.date -= minus_days;

            return;
        }

        minus_days -= this.date;

        if (this.month == 1) {
            this.year -= 1;
            this.month = 12;
        } else {
            this.month -= 1;
        }

        boolean is_leap = isLeapYear(this.year);

        this.date = monthDays(this.month, is_leap);
        minusDays(minus_days);
    }

    /**
     * Method description
     *
     *
     * @param days
     */
    public void plus_days(int days) {
        this.date += days;

        int datelimit = monthDays(this.month, isLeapYear(this.year));

        while (this.date > datelimit) {
            this.date -= datelimit;
            this.month += 1;

            while (this.month > 12) {
                this.month -= 12;
                this.year += 1;
            }

            datelimit = monthDays(this.month, isLeapYear(this.year));
        }
    }

    /**
     * Method description
     *
     *
     * @param copy
     */
    public void plus(CoreTime copy) {
        this.minuten += copy.minuten;

        while (this.minuten >= 60) {
            this.minuten -= 60;
            this.hour += 1;
        }

        this.hour += copy.hour;

        while (this.hour >= 24) {
            this.hour -= 24;
            this.date += 1;
        }

        this.date += copy.date;

        int datelimit = monthDays(this.month, isLeapYear(this.year));

        while (this.date > datelimit) {
            this.date -= datelimit;
            this.month += 1;

            while (this.month > 12) {
                this.month -= 12;
                this.year += 1;
            }

            datelimit = monthDays(this.month, isLeapYear(this.year));
        }

        this.month += copy.month;

        while (this.month > 12) {
            this.month -= 12;
            this.year += 1;
        }

        this.year += copy.year;

        int datelimit_result_year = monthDays(this.month, isLeapYear(this.year));

        if (datelimit_result_year < this.date) {
            this.month += 1;

            if (this.month >= 13) {
                this.month -= 12;
                this.year += 1;
            }

            this.date = (datelimit_result_year - this.date);
        }
    }

    void update() {
        if (eng.noNative) {
            return;
        }

        this.year = eng.gYear();
        this.month = eng.gMonth();
        this.date = eng.gDate();
        this.hour = eng.gHour();
        this.minuten = eng.gMinuten();
    }

    /**
     * Method description
     *
     *
     * @param tm
     *
     * @return
     */
    public int moreThan(CoreTime tm) {
        if (null == tm) {
            return 1;
        }

        if (this.year != tm.year) {
            return (this.year - tm.year);
        }

        if (this.month != tm.month) {
            return (this.month - tm.month);
        }

        if (this.date != tm.date) {
            return (this.date - tm.date);
        }

        if (this.hour != tm.hour) {
            return (this.hour - tm.hour);
        }

        if (this.minuten != tm.minuten) {
            return (this.minuten - tm.minuten);
        }

        return 0;
    }

    /**
     * Method description
     *
     *
     * @param tm
     * @param deltatime
     *
     * @return
     */
    public final int moreThanOnTime(CoreTime tm, CoreTime deltatime) {
        CoreTime ct = new CoreTime(tm);

        ct.plus(deltatime);

        return moreThan(ct);
    }

    /**
     * Method description
     *
     *
     * @param _days
     *
     * @return
     */
    public static CoreTime days(int _days) {
        return new CoreTime(0, 0, _days, 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param _days
     * @param _hours
     *
     * @return
     */
    public static CoreTime daysNhours(int _days, int _hours) {
        return new CoreTime(0, 0, _days, _hours, 0);
    }

    /**
     * Method description
     *
     *
     * @param _hours
     *
     * @return
     */
    public static CoreTime hours(int _hours) {
        return new CoreTime(0, 0, 0, _hours, 0);
    }

    /**
     * Method description
     *
     *
     * @param _mon
     *
     * @return
     */
    public static CoreTime monthes(int _mon) {
        return new CoreTime(0, _mon, 0, 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param _mon
     * @param _days
     *
     * @return
     */
    public static CoreTime monthesNDays(int _mon, int _days) {
        return new CoreTime(0, _mon, _days, 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param t1
     * @param t2
     *
     * @return
     */
    public static boolean isSameDate(CoreTime t1, CoreTime t2) {
        return ((t1.date == t2.date) && (t1.month == t2.month) && (t1.year == t2.year));
    }

    /**
     * Method description
     *
     *
     * @param year1
     * @param month1
     * @param date1
     * @param year2
     * @param month2
     * @param date2
     *
     * @return
     */
    public static int CompareByDays(int year1, int month1, int date1, int year2, int month2, int date2) {
        return CompareByDays(new CoreTime(year1, month1, date1, 0, 0), new CoreTime(year2, month2, date2, 0, 0));
    }

    /**
     * Method description
     *
     *
     * @param time1incoming
     * @param time2incoming
     *
     * @return
     */
    public static int CompareByDays(CoreTime time1incoming, CoreTime time2incoming) {
        boolean order = time1incoming.moreThan(time2incoming) != 0;
        CoreTime time2;
        CoreTime time1;

        if (order) {
            time1 = time1incoming;
            time2 = time2incoming;
        } else {
            time1 = time2incoming;
            time2 = time1incoming;
        }

        int res = 0;
        boolean year1leap = isLeapYear(time1.year);
        boolean year2leap = isLeapYear(time2.year);

        if (time1.year > time2.year) {
            int year = time2.year;

            while (++year != time1.year) {
                if (isLeapYear(year)) {
                    res += 366;
                }

                res += 365;
            }
        }

        if (time1.month > time2.month) {
            if (time1.year > time2.year) {
                if (((year2leap) && (time2.month < 3)) || ((year1leap) && (time2.month >= 3))) {
                    res += 366;
                } else {
                    res += 365;
                }
            }

            int month = time2.month;
            int day = time2.date;

            while (month != time1.month) {
                res += count_days_tillend(month, day, year1leap);
                day = 1;
                ++month;
            }

            res += time1.date;
        } else if (time1.month <= time2.month) {
            if ((time1.month == time2.month) && (time1.year == time2.year)) {
                res += time1.date - time2.date;
            } else {
                int month = time2.month;
                int day = time2.date;

                while (month != 13) {
                    res += count_days_tillend(month, day, year2leap);
                    day = 1;
                    ++month;
                }

                month = 1;
                day = 1;

                while (month != time1.month) {
                    res += count_days_tillend(month, day, year1leap);
                    ++month;
                }

                res += time1.date;
            }
        }

        return ((order)
                ? res
                : -res);
    }

    /**
     * Method description
     *
     *
     * @param year
     *
     * @return
     */
    public static boolean isLeapYear(int year) {
        double remain_centennial = year * 0.01D;
        int year_centennial = 100 * (int) remain_centennial;
        boolean is_centennial = year_centennial == year;

        if (is_centennial) {
            double remain = year / 400.0D;
            int year_400 = 400 * (int) remain;

            return (year_400 == year);
        }

        double remain = year / 4.0D;
        int year_4 = 4 * (int) remain;

        return (year_4 == year);
    }

    /**
     * Method description
     *
     *
     * @param month
     * @param leap_year
     *
     * @return
     */
    public static int monthDays(int month, boolean leap_year) {
        int datelimit = 30;

        switch (month) {
         case 1 :
         case 3 :
         case 5 :
         case 7 :
         case 8 :
         case 10 :
         case 12 :
             datelimit = 31;

             break;

         case 2 :
             datelimit = (leap_year)
                         ? 29
                         : 28;
         case 4 :
         case 6 :
         case 9 :
         case 11 :
        }

        return datelimit;
    }

    static int count_days_tillend(int frommonth, int fromday, boolean is_leap_year) {
        switch (frommonth) {
         case 1 :
         case 3 :
         case 5 :
         case 7 :
         case 8 :
         case 10 :
         case 12 :
             return (31 - fromday);

         case 4 :
         case 6 :
         case 9 :
         case 11 :
             return (30 - fromday);

         case 2 :
             return ((is_leap_year)
                     ? 29 - fromday
                     : 28 - fromday);
        }

        return 0;
    }
}


//~ Formatted in DD Std on 13/08/26
