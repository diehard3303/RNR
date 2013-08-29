/*
 * @(#)Helper.java   13/08/28
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

import rnr.src.scenarioUtils.Pair;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class Helper {
    private static List<Pair<String, String>> s_xmlSpecialSymbols = new ArrayList<Pair<String, String>>();

    static {
        s_xmlSpecialSymbols.add(new Pair<String, String>("&", "&amp;"));
        s_xmlSpecialSymbols.add(new Pair<String, String>("<", "&lt;"));
        s_xmlSpecialSymbols.add(new Pair<String, String>(">", "&gt;"));
        s_xmlSpecialSymbols.add(new Pair<String, String>("\"", "&quot;"));
        s_xmlSpecialSymbols.add(new Pair<String, String>("'", "&#39;"));
    }

    private static String replaceSpecialSymbols(String dirtyString) {
        if (null == dirtyString) {
            return null;
        }

        StringBuffer buffer = new StringBuffer(dirtyString);

        for (Pair<?, ?> pair : s_xmlSpecialSymbols) {
            int lastSymbol = 0;
            int firstIndex = buffer.indexOf((String) pair.getFirst(), lastSymbol);
            int szWas = ((String) pair.getFirst()).length();
            int szReplace = ((String) pair.getSecond()).length();

            while (firstIndex != -1) {
                buffer.replace(firstIndex, firstIndex + szWas, (String) pair.getSecond());
                lastSymbol = firstIndex + szReplace;
                firstIndex = buffer.indexOf((String) pair.getFirst(), lastSymbol);
            }
        }

        String cleanString = buffer.toString();

        return cleanString;
    }

    /**
     * Method description
     *
     *
     * @param nodeName
     * @param attributeValueSet
     *
     * @return
     */
    public static String printNodeWithAttributes(String nodeName, List<Pair<String, String>> attributeValueSet) {
        if (null == attributeValueSet) {
            return nodeName;
        }

        String result = nodeName;

        for (Pair<?, ?> pair : attributeValueSet) {
            result = result + ' ' + ((String) pair.getFirst()) + "=\""
                     + replaceSpecialSymbols((String) pair.getSecond()) + '"';
        }

        return result;
    }

    /**
     * Method description
     *
     *
     * @param stream
     * @param nodename
     * @param attributeValueSet
     */
    public static void printClosedNodeWithAttributes(PrintStream stream, String nodename,
            List<Pair<String, String>> attributeValueSet) {
        stream.println('<' + printNodeWithAttributes(nodename, attributeValueSet) + "/>");
    }

    /**
     * Method description
     *
     *
     * @param stream
     * @param nodename
     * @param version
     * @param attributeValueSet
     */
    public static void printClosedNodeWithAttributes(PrintStream stream, String nodename, int version,
            List<Pair<String, String>> attributeValueSet) {
        Pair<String, String> addVersionattribute = new Pair<String, String>("version", Integer.toString(version));

        attributeValueSet.add(addVersionattribute);
        stream.println('<' + printNodeWithAttributes(nodename, attributeValueSet) + "/>");
    }

    /**
     * Method description
     *
     *
     * @param stream
     * @param nodeName
     * @param attributeValueSet
     */
    public static void printOpenNodeWithAttributes(PrintStream stream, String nodeName,
            List<Pair<String, String>> attributeValueSet) {
        stream.println('<' + printNodeWithAttributes(nodeName, attributeValueSet) + '>');
    }

    /**
     * Method description
     *
     *
     * @param stream
     * @param nodeName
     * @param version
     * @param attributeValueSet
     */
    public static void printOpenNodeWithAttributes(PrintStream stream, String nodeName, int version,
            List<Pair<String, String>> attributeValueSet) {
        Pair<String, String> addVersionattribute = new Pair<String, String>("version", Integer.toString(version));

        attributeValueSet.add(addVersionattribute);
        stream.println('<' + printNodeWithAttributes(nodeName, attributeValueSet) + '>');
    }

    /**
     * Method description
     *
     *
     * @param stream
     * @param nodeName
     */
    public static void openNode(PrintStream stream, String nodeName) {
        stream.println('<' + nodeName + '>');
    }

    /**
     * Method description
     *
     *
     * @param stream
     * @param nodeName
     * @param version
     */
    public static void openNode(PrintStream stream, String nodeName, int version) {
        stream.println('<' + nodeName + ' ' + "version" + "=\"" + version + "\">");
    }

    /**
     * Method description
     *
     *
     * @param stream
     * @param nodeName
     */
    public static void closeNode(PrintStream stream, String nodeName) {
        stream.println("</" + nodeName + '>');
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param value
     *
     * @return
     */
    public static List<Pair<String, String>> createSingleAttribute(String name, String value) {
        List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();

        result.add(new Pair<String, String>(name, value));

        return result;
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param value
     *
     * @return
     */
    public static List<Pair<String, String>> createSingleAttribute(String name, int value) {
        List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();

        result.add(new Pair<String, String>(name, Integer.toString(value)));

        return result;
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param value
     *
     * @return
     */
    public static List<Pair<String, String>> createSingleAttribute(String name, boolean value) {
        List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();

        result.add(new Pair<String, String>(name, Boolean.toString(value)));

        return result;
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param value
     *
     * @return
     */
    public static List<Pair<String, String>> createSingleAttribute(String name, double value) {
        List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();

        result.add(new Pair<String, String>(name, Double.toString(value)));

        return result;
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param value
     * @param attributes
     */
    public static void addAttribute(String name, int value, List<Pair<String, String>> attributes) {
        attributes.add(new Pair<String, String>(name, Integer.toString(value)));
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param value
     * @param attributes
     */
    public static void addAttribute(String name, String value, List<Pair<String, String>> attributes) {
        attributes.add(new Pair<String, String>(name, value));
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param value
     * @param attributes
     */
    public static void addAttribute(String name, boolean value, List<Pair<String, String>> attributes) {
        attributes.add(new Pair<String, String>(name, Boolean.toString(value)));
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param value
     * @param attributes
     */
    public static void addAttribute(String name, double value, List<Pair<String, String>> attributes) {
        attributes.add(new Pair<String, String>(name, Double.toString(value)));
    }

    /**
     * Method description
     *
     *
     * @param stringValue
     * @param attributeName
     * @param message
     *
     * @return
     */
    public static int ConvertToIntegerAndWarn(String stringValue, String attributeName, String message) {
        if (null == stringValue) {
            Log.error(message + " cannot find attribute " + attributeName);

            return 0;
        }

        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            Log.error(message + " cannot convert attribute " + attributeName + " with value " + stringValue
                      + " to integer.");
        }

        return 0;
    }

    /**
     * Method description
     *
     *
     * @param stringValue
     * @param attributeName
     * @param message
     *
     * @return
     */
    public static boolean ConvertToBooleanAndWarn(String stringValue, String attributeName, String message) {
        if (null == stringValue) {
            Log.error(message + " cannot find attribute " + attributeName);

            return false;
        }

        if ((0 != "false".compareToIgnoreCase(stringValue)) && (0 != "true".compareToIgnoreCase(stringValue))) {
            Log.error(message + " cannot convert attribute " + attributeName + " with value " + stringValue
                      + " to boolean.");

            return false;
        }

        return Boolean.parseBoolean(stringValue);
    }

    /**
     * Method description
     *
     *
     * @param stringValue
     * @param attributeName
     * @param message
     *
     * @return
     */
    public static double ConvertToDoubleAndWarn(String stringValue, String attributeName, String message) {
        if (null == stringValue) {
            Log.error(message + " cannot find attribute " + attributeName);

            return 0.0D;
        }

        try {
            return Double.parseDouble(stringValue);
        } catch (NumberFormatException e) {
            Log.error(message + " cannot convert attribute " + attributeName + " with value " + stringValue
                      + " to boolean.");
        }

        return 0.0D;
    }
}


//~ Formatted in DD Std on 13/08/28
