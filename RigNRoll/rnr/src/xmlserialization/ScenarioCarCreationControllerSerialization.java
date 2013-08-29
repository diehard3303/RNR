/*
 * @(#)ScenarioCarCreationControllerSerialization.java   13/08/28
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

import rnr.src.players.ScenarioCarCreationController;
import rnr.src.players.actorveh;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class ScenarioCarCreationControllerSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "scenariocarcreationcontroller";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(ScenarioCarCreationController value, PrintStream stream) {
        Helper.openNode(stream, "scenariocarcreationcontroller");
        Helper.openNode(stream, "loadedcars");

        ArrayList<actorveh> cars = value.getCars();

        for (actorveh item : cars) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            ActorVehSerializator.serializeXML(item, stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
        }

        Helper.closeNode(stream, "loadedcars");
        Helper.closeNode(stream, "scenariocarcreationcontroller");
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ScenarioCarCreationController deserializeXML(Node node) {
        ScenarioCarCreationController controller = new ScenarioCarCreationController();
        NodeList listCars = node.getNamedChildren("loadedcars");

        if (!(listCars.isEmpty())) {
            ArrayList<actorveh> cars = new ArrayList<actorveh>();
            NodeList listNodes = listCars.get(0).getNamedChildren(ListElementSerializator.getNodeName());

            for (Node listItem : listNodes) {
                actorveh car = null;
                NodeList actorvehNode = listItem.getNamedChildren(ActorVehSerializator.getNodeName());

                if (!(actorvehNode.isEmpty())) {
                    car = ActorVehSerializator.deserializeXML(actorvehNode.get(0));
                }

                if (null != car) {
                    cars.add(car);
                }
            }

            controller.setCars(cars);
        }

        return controller;
    }
}


//~ Formatted in DD Std on 13/08/28
