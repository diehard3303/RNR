/*
 * @(#)ReservedCars.java   13/08/26
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


package rnr.src.rnrconfig;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrconfig.ReservedCars.ReservedVehicle;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class ReservedCars {
    private static Vector<ReservedVehicle> list = null;
    private static final String FILENAME = "..\\data\\config\\reservedvehicles.xml";
    private static final String ROOT = "vehicle";
    private static final String NAME = "name";
    private static final String COLORS = "color";
    private static final String NUM = "num";

    private static void parse(Node node) {
        String vehicle_name = node.getAttribute("name");
        NodeList childs = node.getNamedChildren("color");

        if ((childs == null) || (childs.size() == 0)) {
            ReservedVehicle item = new ReservedVehicle();

            item.name = vehicle_name;
            item.color = -1;
            list.add(item);
        } else {
            for (int i = 0; i < childs.size(); ++i) {
                if (childs.get(i) != null) {
                    String s_number = childs.get(i).getAttribute("num");
                    ReservedVehicle item = new ReservedVehicle();

                    item.name = vehicle_name;
                    item.color = new Integer(s_number).intValue();
                    list.add(item);
                }
            }
        }
    }

    private static void pasrse() {
        if ((list != null) && (list.size() != 0)) {
            return;
        }

        list = new Vector();

        Node top = XmlUtils.parse("..\\data\\config\\reservedvehicles.xml");

        if (top != null) {
            NodeList vehicles = top.getNamedChildren("vehicle");

            if (vehicles != null) {
                for (int i = 0; i < vehicles.size(); ++i) {
                    parse(vehicles.get(i));
                }
            }
        }
    }

    /**
     * Method description
     *
     */
    public static void reserve() {
        pasrse();

        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                ReservedVehicle item = list.get(i);

                reserveCar(item.name, item.color, true);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramInt
     * @param paramBoolean
     */
    public static native void reserveCar(String paramString, int paramInt, boolean paramBoolean);

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static class ReservedVehicle {
        String name;
        int color;
    }
}


//~ Formatted in DD Std on 13/08/26
