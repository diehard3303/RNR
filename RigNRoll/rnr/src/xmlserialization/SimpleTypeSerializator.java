/*
 * @(#)SimpleTypeSerializator.java   13/08/28
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

import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class SimpleTypeSerializator {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeNameInteger() {
        return "int";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeNameString() {
        return "string";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXMLInteger(int value, PrintStream stream) {
        Helper.printClosedNodeWithAttributes(stream, "int", Helper.createSingleAttribute("value", value));
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXMLInteger(long value, PrintStream stream) {
        Helper.printClosedNodeWithAttributes(stream, "int", Helper.createSingleAttribute("value", value));
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static int deserializeXMLInteger(Node node) {
        String value = node.getAttribute("value");

        if (null == value) {
            return 0;
        }

        return Integer.parseInt(value);
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static long deserializeXMLLong(Node node) {
        String value = node.getAttribute("value");

        if (null == value) {
            return 0L;
        }

        return Integer.parseInt(value);
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXMLString(String value, PrintStream stream) {
        Helper.printClosedNodeWithAttributes(stream, "string", Helper.createSingleAttribute("value", value));
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXMLStringList(Collection<String> value, PrintStream stream) {
        if ((null == value) || (null == stream)) {
            return;
        }

        Helper.openNode(stream, "string_list");

        for (String s : value) {
            serializeXMLString(s, stream);
        }

        Helper.closeNode(stream, "string_list");
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static List<String> deserializeXMLStringList(Node node) {
        if (null == node) {
            return Collections.emptyList();
        }

        List<String> result = new LinkedList<String>();
        NodeList strings = node.getNamedChildren("string");

        for (Node string : strings) {
            result.add(deserializeXMLString(string));
        }

        return result;
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static String deserializeXMLString(Node node) {
        return node.getAttribute("value");
    }
}


//~ Formatted in DD Std on 13/08/28
