/*
 * @(#)ScriptRefXmlSerialization.java   13/08/28
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

import rnr.src.rnrcore.IScriptRef;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrcore.ScriptRefStorage;
import rnr.src.rnrcore.XmlSerializable;
import rnr.src.rnrcore.XmlSerializationFabric;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class ScriptRefXmlSerialization implements XmlSerializable {
    private static ScriptRefXmlSerialization instance = new ScriptRefXmlSerialization();

    /**
     * Method description
     *
     *
     * @return
     */
    public static ScriptRefXmlSerialization getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        Map<Integer, IScriptRef> refTable = ScriptRefStorage.getRefferaceTable();

        if ((null == refTable) || (refTable.isEmpty())) {
            return;
        }

        Set<Entry<Integer, IScriptRef>> set = refTable.entrySet();
        ArrayList setSerialization = new ArrayList();

        for (Map.Entry entry : set) {
            IXMLSerializable xmlInterface = ((IScriptRef) entry.getValue()).getXmlSerializator();

            if (null != xmlInterface) {
                setSerialization.add(xmlInterface);
            }
        }

        Helper.openNode(stream, getRootNodeName());

        for (IXMLSerializable pair : setSerialization) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            Helper.openNode(stream, "interface");
            pair.serialize(stream);
            Helper.closeNode(stream, "interface");
            ListElementSerializator.serializeXMLListelementClose(stream);
        }

        Helper.closeNode(stream, getRootNodeName());
    }

    /**
     * Method description
     *
     *
     * @param nodeDom
     */
    @Override
    public void loadFromNode(org.w3c.dom.Node nodeDom) {
        ScriptRefStorage.clearRefferaceTable();

        rnr.src.xmlutils.Node node = new rnr.src.xmlutils.Node(nodeDom);
        NodeList list = node.getNamedChildren(ListElementSerializator.getNodeName());

        for (rnr.src.xmlutils.Node element : list) {
            rnr.src.xmlutils.Node interfaceNode = element.getNamedChild("interface");

            if (null != interfaceNode) {
                rnr.src.xmlutils.Node searchInterfaceNode = interfaceNode.getChild();
                IXMLSerializable serializationInterface =
                    XmlSerializationFabric.getDeSerializationInterface(searchInterfaceNode.getName());

                if (null != serializationInterface) {
                    serializationInterface.deSerialize(searchInterfaceNode);
                } else {
                    Log.error(
                        "ScriptRefXmlSerialization in loadFromNode couldnt find serialization interface for node "
                        + searchInterfaceNode.getName());
                }
            }
        }
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
     * @return
     */
    @Override
    public String getRootNodeName() {
        return "scriptref";
    }
}


//~ Formatted in DD Std on 13/08/28
