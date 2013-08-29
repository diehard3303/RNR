/*
 * @(#)CoreTimeSerialization.java   13/08/28
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


package rnr.src.xmlserialization;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.CoreTime;
import rnr.src.scenarioUtils.Pair;
import rnr.src.xmlutils.Node;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class CoreTimeSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "coretime";
    }

    /**
     * Method description
     *
     *
     * @param time
     * @param stream
     */
    public static void serializeXML(CoreTime time, PrintStream stream) {
        List<Pair<String, String>> attrinutes = Helper.createSingleAttribute("year", time.gYear());

        Helper.addAttribute("month", time.gMonth(), attrinutes);
        Helper.addAttribute("date", time.gDate(), attrinutes);
        Helper.addAttribute("hour", time.gHour(), attrinutes);
        Helper.addAttribute("minuten", time.gMinute(), attrinutes);
        Helper.printClosedNodeWithAttributes(stream, getNodeName(), attrinutes);
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static CoreTime deserializeXML(Node node) {
        String yearString = node.getAttribute("year");
        String monthString = node.getAttribute("month");
        String dateString = node.getAttribute("date");
        String hourString = node.getAttribute("hour");
        String minutsString = node.getAttribute("minuten");
        int yearValue = ConvertToIntegerAndWarn(yearString, "year");
        int monthValue = ConvertToIntegerAndWarn(monthString, "month");
        int dateValue = ConvertToIntegerAndWarn(dateString, "date");
        int hourValue = ConvertToIntegerAndWarn(hourString, "hour");
        int minutValue = ConvertToIntegerAndWarn(minutsString, "minuten");
        CoreTime result = new CoreTime(yearValue, monthValue, dateValue, hourValue, minutValue);

        return result;
    }

    private static int ConvertToIntegerAndWarn(String stringValue, String attributeName) {
        if (null == stringValue) {
            Log.error("CoreTimeSerialization in deserializeXML cannot find attribute " + attributeName);

            return 0;
        }

        try {
            int intValue = Integer.parseInt(stringValue);

            return intValue;
        } catch (NumberFormatException e) {
            Log.error("CoreTimeSerialization in deserializeXML cannot convert attribute " + attributeName
                      + " with value " + stringValue + " to integer.");
        }

        return 0;
    }
}


//~ Formatted in DD Std on 13/08/28
