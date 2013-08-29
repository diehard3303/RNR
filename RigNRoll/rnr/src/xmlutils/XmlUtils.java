/*
 * @(#)XmlUtils.java   13/08/26
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


package rnr.src.xmlutils;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import rnr.src.auxil.DInputStream2;
import rnr.src.auxil.XInputStreamCreate;
import rnr.src.rnrcore.eng;

//~--- JDK imports ------------------------------------------------------------

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class XmlUtils {
    private static final XPath xpath = XPathFactory.newInstance().newXPath();

    /**
     * Method description
     *
     *
     * @return
     */
    public static XPath getXPath() {
        return xpath;
    }

    /**
     * Method description
     *
     *
     * @param fileName
     *
     * @return
     */
    public static Node parse(String fileName) {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        Document doc;

        try {
            DocumentBuilder docbuild = fac.newDocumentBuilder();

            doc = docbuild.parse(new DInputStream2(XInputStreamCreate.open(fileName)));
        } catch (Exception e) {
            eng.err("parsing error: " + e.toString());

            return null;
        }

        NodeList topChildren = doc.getChildNodes();

        if (topChildren.getLength() != 1) {
            eng.err("parse XmlUtils Undefinite behaiboir.");

            return null;
        }

        return new Node(topChildren.item(0));
    }

    /**
     * Method description
     *
     *
     * @param expression
     *
     * @return
     */
    public static XPathExpression makeXpath(String expression) {
        try {
            return xpath.compile(expression);
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return null;
    }
}


//~ Formatted in DD Std on 13/08/26
