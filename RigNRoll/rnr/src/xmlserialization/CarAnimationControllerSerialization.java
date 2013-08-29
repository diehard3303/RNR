/*
 * @(#)CarAnimationControllerSerialization.java   13/08/28
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

import rnr.src.players.CarAnimationController;
import rnr.src.players.CarAnimationController.TypeCarPair;
import rnr.src.players.DriversModelsPool;
import rnr.src.players.actorveh;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
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
public class CarAnimationControllerSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "caranimationscontroller";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings("rawtypes")
    public static void serializeXML(CarAnimationController value, PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        DriversModelsPool pool = value.getPool();

        ModelPoolSerializator.serializeXML(pool, stream);

        HashMap<actorveh, String> assignedAnimations = value.getAssignedAnimations();
        Set<Entry<actorveh, String>> set = assignedAnimations.entrySet();

        Helper.openNode(stream, "assignedanimations");

        for (Map.Entry entry : set) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            ActorVehSerializator.serializeXML((actorveh) entry.getKey(), stream);
            SimpleTypeSerializator.serializeXMLString((String) entry.getValue(), stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
        }

        Helper.closeNode(stream, "assignedanimations");

        ArrayList<TypeCarPair> loadedCars = value.getLoadedCars();

        Helper.openNode(stream, "loadedcars");

        for (CarAnimationController.TypeCarPair entry : loadedCars) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            ActorVehSerializator.serializeXML(entry.getCar(), stream);
            SimpleTypeSerializator.serializeXMLInteger(entry.getTypePlayer(), stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
        }

        Helper.closeNode(stream, "loadedcars");

        ArrayList<TypeCarPair> nonloadedCars = value.getNonloadedCars();

        Helper.openNode(stream, "nonloadedcars");

        for (CarAnimationController.TypeCarPair entry : nonloadedCars) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            ActorVehSerializator.serializeXML(entry.getCar(), stream);
            SimpleTypeSerializator.serializeXMLInteger(entry.getTypePlayer(), stream);
            ListElementSerializator.serializeXMLListelementClose(stream);
        }

        Helper.closeNode(stream, "nonloadedcars");
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
    public static CarAnimationController deserializeXML(Node node) {
        CarAnimationController result = new CarAnimationController();
        Node poolNode = node.getNamedChild(ModelPoolSerializator.getNodeName());

        if (null != poolNode) {
            DriversModelsPool pool = ModelPoolSerializator.deserializeXML(poolNode);

            result.setPool(pool);
        }

        Node assignedAnimations = node.getNamedChild("assignedanimations");

        if (null != assignedAnimations) {
            HashMap assignedAnimationsResult = new HashMap();
            NodeList list = assignedAnimations.getNamedChildren(ListElementSerializator.getNodeName());

            for (Node nodeList : list) {
                actorveh car = null;
                String string = null;
                NodeList actorvehNode = nodeList.getNamedChildren(ActorVehSerializator.getNodeName());

                if (!(actorvehNode.isEmpty())) {
                    car = ActorVehSerializator.deserializeXML(actorvehNode.get(0));
                }

                NodeList stringNode = nodeList.getNamedChildren(SimpleTypeSerializator.getNodeNameString());

                if (!(stringNode.isEmpty())) {
                    string = SimpleTypeSerializator.deserializeXMLString(stringNode.get(0));
                }

                assignedAnimationsResult.put(car, string);
            }

            result.setAssignedAnimations(assignedAnimationsResult);
        }

        Node loadedCars = node.getNamedChild("loadedcars");

        if (null != loadedCars) {
            ArrayList loadedCarsResult = new ArrayList();
            NodeList list = loadedCars.getNamedChildren(ListElementSerializator.getNodeName());

            for (Node nodeList : list) {
                actorveh car = null;
                int value = 0;
                Node actorvehNode = nodeList.getNamedChild(ActorVehSerializator.getNodeName());

                if (null != actorvehNode) {
                    car = ActorVehSerializator.deserializeXML(actorvehNode);
                }

                Node intNode = nodeList.getNamedChild(SimpleTypeSerializator.getNodeNameInteger());

                if (null != intNode) {
                    value = SimpleTypeSerializator.deserializeXMLInteger(intNode);
                }

                loadedCarsResult.add(new CarAnimationController.TypeCarPair(value, car));
            }

            result.setLoadedCars(loadedCarsResult);
        }

        NodeList nonLoadedCars = node.getNamedChildren("nonloadedcars");

        if (!(nonLoadedCars.isEmpty())) {
            ArrayList nonLoadedCarsResult = new ArrayList();
            Node nodenonLoadedCars = nonLoadedCars.get(0);
            NodeList list = nodenonLoadedCars.getNamedChildren(ListElementSerializator.getNodeName());

            for (Node nodeList : list) {
                actorveh car = null;
                int value = 0;
                NodeList actorvehNode = nodeList.getNamedChildren(ActorVehSerializator.getNodeName());

                if (!(actorvehNode.isEmpty())) {
                    car = ActorVehSerializator.deserializeXML(actorvehNode.get(0));
                }

                NodeList intNode = nodeList.getNamedChildren(SimpleTypeSerializator.getNodeNameInteger());

                if (!(intNode.isEmpty())) {
                    value = SimpleTypeSerializator.deserializeXMLInteger(intNode.get(0));
                }

                nonLoadedCarsResult.add(new CarAnimationController.TypeCarPair(value, car));
            }

            result.setNonloadedCars(nonLoadedCarsResult);
        }

        return result;
    }
}


//~ Formatted in DD Std on 13/08/28
