/*
 * @(#)UidStorageSerialization.java   13/08/28
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

import rnr.src.rnrcore.UidStorage;
import rnr.src.rnrcore.XmlSerializable;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.LinkedList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class UidStorageSerialization implements XmlSerializable {
    private static UidStorageSerialization instance = new UidStorageSerialization();

    /**
     * Method description
     *
     *
     * @return
     */
    public static UidStorageSerialization getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getRootNodeName() {
        return getNodeName();
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void loadFromNode(org.w3c.dom.Node node) {
        deserializeXML(new rnr.src.xmlutils.Node(node));
    }

    /**
     * Method description
     *
     */
    @Override
    public void yourNodeWasNotFound() {}

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        serializeXML(UidStorage.getInstance(), stream);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "uidstorage";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(UidStorage value, PrintStream stream) {
        Helper.printOpenNodeWithAttributes(stream, getNodeName(),
                                           Helper.createSingleAttribute("maxcreated", value.getMaxCreatedUid()));

        List<Integer> uids = value.getUids();

        for (Integer uidValue : uids) {
            SimpleTypeSerializator.serializeXMLInteger(uidValue.intValue(), stream);
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    public static void deserializeXML(rnr.src.xmlutils.Node node) {
        UidStorage value = UidStorage.getInstance();
        String maxCreatedString = node.getAttribute("maxcreated");
        int maxCreatedValue = Helper.ConvertToIntegerAndWarn(maxCreatedString, "maxcreated",
                                  "UidStorageSerialization on deserializeXML ");
        NodeList listUidsNode = node.getNamedChildren(SimpleTypeSerializator.getNodeNameInteger());
        LinkedList<Integer> listUids = new LinkedList<Integer>();

        if (null != listUidsNode) {
            for (rnr.src.xmlutils.Node uidValue : listUidsNode) {
                listUids.add(Integer.valueOf(SimpleTypeSerializator.deserializeXMLInteger(uidValue)));
            }
        }

        value.setUids(listUids);
        value.setMaxCreatedUid(maxCreatedValue);
    }
}


//~ Formatted in DD Std on 13/08/28
