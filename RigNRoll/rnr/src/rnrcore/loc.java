/*
 * @(#)loc.java   13/08/26
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
public class loc {

    /** Field description */
    public static final String JOURNAL = "journal";

    /** Field description */
    public static final String MPLONG = "MPLONG";

    /** Field description */
    public static final String MPSHORT = "MPSHORT";

    /** Field description */
    public static final String BIGRACE_SHORTNAME = "BIGRACE_SHORTNAME";

    /** Field description */
    public static final String MERCHNAME = "MERCHNAME";

    /** Field description */
    public static final String CITYNAME = "CITYNAME";

    /** Field description */
    public static final String CITYNAME_NATIVE = "CITYNAME_NATIVE";

    /** Field description */
    public static final String DATE_TIME = "DATE_TIME";

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getNewspaperString(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("newspaper", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getRepairTableString(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("carsystems", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getMenuString(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("menu", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getMENUString(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("MENU", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getMissionPointLongName(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("MPLONG", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getMissionPointShortName(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("MPSHORT", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getJournalString(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("journal", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getOrgString(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("organizer", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getScenarioNamesString(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("SCENARIONAMES", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getnickNamesString(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("NICKNAMES", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getCustomerName(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("customer", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getDialogName(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("DIALOG", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getMissionSuccesPictureText(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("MISSION_PICTURE", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getBigraceShortName(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("BIGRACE_SHORTNAME", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getBigraceFullName(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("BIGRACE_FULLNAME", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getBigraceDescription(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("BIGRACE_DESCRIPTION", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getDateString(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("DATE_TIME", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getAiString(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("AI", str);
        }

        return str;
    }

    /**
     * Method description
     *
     *
     * @param str
     *
     * @return
     */
    public static String getCityName(String str) {
        if (!(eng.noNative)) {
            return eng.getStringRef("CITYNAME", str);
        }

        return str;
    }
}


//~ Formatted in DD Std on 13/08/26
