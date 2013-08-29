/*
 * @(#)MappedCarsSerialization.java   13/08/28
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

import rnr.src.players.MappingCars;
import rnr.src.players.actorveh;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.HashMap;
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
public class MappedCarsSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "mappedcars";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings("rawtypes")
    public static void serializeXML(MappingCars value, PrintStream stream) {
        Helper.openNode(stream, getNodeName());
        Helper.openNode(stream, "list");

        HashMap<String, actorveh> mappedCars = value.getMappedCars();
        Set<Entry<String, actorveh>> set = mappedCars.entrySet();

        for (Map.Entry entry : set) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            SimpleTypeSerializator.serializeXMLString((String) entry.getKey(), stream);
            ActorVehSerializator.serializeXML((actorveh) entry.getValue(), stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
        }

        Helper.closeNode(stream, "list");
        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static MappingCars deserializeXML(Node node) {
        MappingCars result = new MappingCars();
        NodeList listNodes = node.getNamedChildren("list");

        if (!(listNodes.isEmpty())) {
            HashMap mappedCars = new HashMap();
            NodeList elements = listNodes.get(0).getNamedChildren(ListElementSerializator.getNodeName());

            for (Node element : elements) {
                String value = null;
                actorveh car = null;
                NodeList carsList = element.getNamedChildren(ActorVehSerializator.getNodeName());
                NodeList valueList = element.getNamedChildren(SimpleTypeSerializator.getNodeNameString());

                if (!(carsList.isEmpty())) {
                    car = ActorVehSerializator.deserializeXML(carsList.get(0));
                }

                if (!(valueList.isEmpty())) {
                    value = SimpleTypeSerializator.deserializeXMLString((Node) valueList.get(0));
                }

                mappedCars.put(value, car);
            }

            result.setMappedCars(mappedCars);
        }

        return result;
    }
}


//~ Formatted in DD Std on 13/08/28
