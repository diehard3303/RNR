/*
 * @(#)CarParts.java   13/08/25
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
   Released under the BSD 3 clause license
Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this 
    list of conditions and the following disclaimer. Redistributions in binary 
    form must reproduce the above copyright notice, this list of conditions and 
    the following disclaimer in the documentation and/or other materials 
    provided with the distribution. Neither the name of the DieHard Development 
    nor the names of its contributors may be used to endorse or promote products 
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 *
 *
 *
 */


package rnr.src.gameobj;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.CondTable;
import rnr.src.menu.Item;
import rnr.src.menu.TableNode;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.loc;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.lang.reflect.Field;

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class CarParts {

    /** Field description */
    public static final int COND_GREEN = 0;

    /** Field description */
    public static final int COND_YELLOW = 1;

    /** Field description */
    public static final int COND_RED = 2;

    /** Field description */
    public static final int MAX_W = 8;

    /** Field description */
    public static final int VP_ENGINE_BEGIN = 0;

    /** Field description */
    public static final int VP_ENGINE_MOUNT = 0;

    /** Field description */
    public static final int VP_ENGINE_CRANKSHAFT = 1;

    /** Field description */
    public static final int VP_ENGINE_BLOCK = 2;

    /** Field description */
    public static final int VP_ENGINE_BRAKE = 3;

    /** Field description */
    public static final int VP_ENGINE_OIL = 4;

    /** Field description */
    public static final int VP_ENGINE_OILPUMP = 5;

    /** Field description */
    public static final int VP_ENGINE_OILFILTER = 6;

    /** Field description */
    public static final int VP_ENGINE_OILPIPELINE = 7;

    /** Field description */
    public static final int VP_ENGINE_OILCOOLER = 8;

    /** Field description */
    public static final int VP_ENGINE_OILPAN = 9;

    /** Field description */
    public static final int VP_ENGINE_END = 9;

    /** Field description */
    public static final int VP_ENGINE_MAX = 10;

    /** Field description */
    public static final int VP_COOLING_BEGIN = 10;

    /** Field description */
    public static final int VP_COOLING_COOLANT = 10;

    /** Field description */
    public static final int VP_COOLING_HOSES = 11;

    /** Field description */
    public static final int VP_COOLING_RADIATOR = 12;

    /** Field description */
    public static final int VP_COOLING_WATERPUMP = 13;

    /** Field description */
    public static final int VP_COOLING_THERMOSTAT = 14;

    /** Field description */
    public static final int VP_COOLING_FAN = 15;

    /** Field description */
    public static final int VP_COOLING_END = 15;

    /** Field description */
    public static final int VP_COOLING_MAX = 6;

    /** Field description */
    public static final int VP_STEERING_BEGIN = 16;

    /** Field description */
    public static final int VP_STEERING_SHAFT = 16;

    /** Field description */
    public static final int VP_STEERING_FLUID = 17;

    /** Field description */
    public static final int VP_STEERING_BELT = 18;

    /** Field description */
    public static final int VP_STEERING_GEAR = 19;

    /** Field description */
    public static final int VP_STEERING_PUMP = 20;

    /** Field description */
    public static final int VP_STEERING_RODS = 21;

    /** Field description */
    public static final int VP_STEERING_END = 21;

    /** Field description */
    public static final int VP_STEERING_MAX = 6;

    /** Field description */
    public static final int VP_BRAKING_BEGIN = 22;

    /** Field description */
    public static final int VP_BRAKING_DRUMS = 22;

    /** Field description */
    public static final int VP_BRAKING_SHOES = 23;

    /** Field description */
    public static final int VP_BRAKING_COMPRESSOR = 24;

    /** Field description */
    public static final int VP_BRAKING_GOUVERNOR = 25;

    /** Field description */
    public static final int VP_BRAKING_RESERVOIRS = 26;

    /** Field description */
    public static final int VP_BRAKING_CHAMBERS = 27;

    /** Field description */
    public static final int VP_BRAKING_VALVE = 28;

    /** Field description */
    public static final int VP_BRAKING_PARKING = 29;

    /** Field description */
    public static final int VP_BRAKING_NOSES = 30;

    /** Field description */
    public static final int VP_BRAKING_ABS = 31;

    /** Field description */
    public static final int VP_BRAKING_END = 31;

    /** Field description */
    public static final int VP_BRAKING_MAX = 10;

    /** Field description */
    public static final int VP_TRANSMISSION_BEGIN = 32;

    /** Field description */
    public static final int VP_TRANSMISSION_CLUTCH = 32;

    /** Field description */
    public static final int VP_TRANSMISSION_OIL = 33;

    /** Field description */
    public static final int VP_TRANSMISSION_GEAR = 34;

    /** Field description */
    public static final int VP_TRANSMISSION_AXLES = 35;

    /** Field description */
    public static final int VP_TRANSMISSION_DRIVESHAFT = 36;

    /** Field description */
    public static final int VP_TRANSMISSION_INTERSHAFT = 37;

    /** Field description */
    public static final int VP_TRANSMISSION_HUBS = 38;

    /** Field description */
    public static final int VP_TRANSMISSION_END = 38;

    /** Field description */
    public static final int VP_TRANSMISSION_MAX = 7;

    /** Field description */
    public static final int VP_ELECTRICAL_BEGIN = 39;

    /** Field description */
    public static final int VP_ELECTRICAL_BATTERIES = 39;

    /** Field description */
    public static final int VP_ELECTRICAL_ALTERNATOR = 40;

    /** Field description */
    public static final int VP_ELECTRICAL_STARTER = 41;

    /** Field description */
    public static final int VP_ELECTRICAL_IGNITION = 42;

    /** Field description */
    public static final int VP_ELECTRICAL_LIGHTSSOUND = 43;

    /** Field description */
    public static final int VP_ELECTRICAL_WIPERS = 44;

    /** Field description */
    public static final int VP_ELECTRICAL_WARES = 45;

    /** Field description */
    public static final int VP_ELECTRICAL_END = 45;

    /** Field description */
    public static final int VP_ELECTRICAL_MAX = 7;

    /** Field description */
    public static final int VP_SUSPENSION_BEGIN = 46;

    /** Field description */
    public static final int VP_SUSPENSION_LEAFSPRINGS = 46;

    /** Field description */
    public static final int VP_SUSPENSION_ABSORBERS = 47;

    /** Field description */
    public static final int VP_SUSPENSION_AIRBAGS = 48;

    /** Field description */
    public static final int VP_SUSPENSION_COMPRESSOR = 49;

    /** Field description */
    public static final int VP_SUSPENSION_AIRPIPELINE = 50;

    /** Field description */
    public static final int VP_SUSPENSION_GOVERNORS = 51;

    /** Field description */
    public static final int VP_SUSPENSION_ARMSSTABILIZERS = 52;

    /** Field description */
    public static final int VP_SUSPENSION_END = 52;

    /** Field description */
    public static final int VP_SUSPENSION_MAX = 7;

    /** Field description */
    public static final int VP_WHEELS_BEGIN = 53;

    /** Field description */
    public static final int VP_WHEELS_RIM_0 = 53;

    /** Field description */
    public static final int VP_WHEELS_TIRE_0 = 61;

    /** Field description */
    public static final int VP_WHEELS_END = 68;

    /** Field description */
    public static final int VP_WHEELS_MAX = 16;

    /** Field description */
    public static final int VP_FUEL_BEGIN = 69;

    /** Field description */
    public static final int VP_FUEL_INJECTORS = 69;

    /** Field description */
    public static final int VP_FUEL_PUMP = 70;

    /** Field description */
    public static final int VP_FUEL_TANK = 71;

    /** Field description */
    public static final int VP_FUEL_FILTER = 72;

    /** Field description */
    public static final int VP_FUEL_AIRFILTER = 73;

    /** Field description */
    public static final int VP_FUEL_TURBOCHARGER = 74;

    /** Field description */
    public static final int VP_FUEL_INTAKEMANIFOLD = 75;

    /** Field description */
    public static final int VP_FUEL_INTAKEVALVES = 76;

    /** Field description */
    public static final int VP_FUEL_END = 76;

    /** Field description */
    public static final int VP_FUEL_MAX = 8;

    /** Field description */
    public static final int VP_EXHAUST_BEGIN = 77;

    /** Field description */
    public static final int VP_EXHAUST_VALVES = 77;

    /** Field description */
    public static final int VP_EXHAUST_MANIFOLD = 78;

    /** Field description */
    public static final int VP_EXHAUST_PIPES = 79;

    /** Field description */
    public static final int VP_EXHAUST_MUFFLERS = 80;

    /** Field description */
    public static final int VP_EXHAUST_END = 80;

    /** Field description */
    public static final int VP_EXHAUST_MAX = 4;

    /** Field description */
    public static final int VP_COUPLING_BEGIN = 81;

    /** Field description */
    public static final int VP_COUPLING_LOCKMECHANISM = 81;

    /** Field description */
    public static final int VP_COUPLING_5THWHEEL = 82;

    /** Field description */
    public static final int VP_COUPLING_KINGPIN = 83;

    /** Field description */
    public static final int VP_COUPLING_END = 83;

    /** Field description */
    public static final int VP_COUPLING_MAX = 3;

    /** Field description */
    public static final int MAX_CARV_DAMAGE = 84;

    /** Field description */
    public static final int VP_BODY_AND_FRAME_BEGIN = 84;

    /** Field description */
    public static final int VP_BODY = 84;

    /** Field description */
    public static final int VP_FRAME = 85;

    /** Field description */
    public static final int VP_GLASES = 86;

    /** Field description */
    public static final int VP_BODY_AND_FRAME_END = 86;

    /** Field description */
    public static final int VP_BODY_AND_FRAME_MAX = 3;

    /** Field description */
    public static final int VP_CARVSTATE_0 = 87;

    /** Field description */
    public static final int VP_CARVSTATE_odometer = 87;

    /** Field description */
    public static final int VP_CARVSTATE_engine_power = 88;

    /** Field description */
    public static final int VP_CARVSTATE_fuel = 89;

    /** Field description */
    public static final int VP_CARVSTATE_tire = 90;

    /** Field description */
    public static final int VP_CARVSTATE_brake = 91;

    /** Field description */
    public static final int VP_CARVSTATE_brake_burn = 92;

    /** Field description */
    public static final int VP_CARVSTATE_oil_additive = 93;

    /** Field description */
    public static final int VP_CARVSTATE_engine_brake = 94;

    /** Field description */
    public static final int MAX_VP_STATE = 95;

    /** Field description */
    public static final int VP_VEHICLE = 96;

    /** Field description */
    public static final int VP_ENGINE = 97;

    /** Field description */
    public static final int VP_COOLINGSYS = 98;

    /** Field description */
    public static final int VP_STEERINGSYS = 99;

    /** Field description */
    public static final int VP_BRAKINGSYS = 100;

    /** Field description */
    public static final int VP_TRANSMISSIONSYS = 101;

    /** Field description */
    public static final int VP_ELECTRICALSYS = 102;

    /** Field description */
    public static final int VP_SUSPENSIONSYS = 103;

    /** Field description */
    public static final int VP_WHEELSYS = 104;

    /** Field description */
    public static final int VP_FUELSYS = 105;

    /** Field description */
    public static final int VP_EXHAUSTSYS = 106;

    /** Field description */
    public static final int VP_COUPLINGSYS = 107;

    /** Field description */
    public static final int VP_BODYNFRAME = 108;

    /** Field description */
    public static final int VP_REALMAX = 109;
    private static CarPartsNode m_root = null;
    Item[] m_Parts = null;

    /**
     * Constructs ...
     *
     */
    public CarParts() {
        this.m_Parts = new Item[109];
        FillData();
        SetupHierarchy();
    }

    private void FillData() {
        for (int i = 0; i < 109; ++i) {
            this.m_Parts[i] = new Item(0, 0, 0, "no name");
            this.m_Parts[i].id = i;
        }
    }

    /**
     * Method description
     *
     *
     * @param ID
     *
     * @return
     */
    public Item GetItem(int ID) {
        return this.m_Parts[ID];
    }

    /**
     * Method description
     *
     *
     * @param ID
     * @param price
     * @param condition
     */
    public void SetItemInfo(int ID, int price, int condition) {
        if (this.m_Parts[ID] == null) {
            return;
        }

        this.m_Parts[ID].price = price;
        this.m_Parts[ID].condition = condition;
    }

    private TableNode fillCondTable(CondTable table, TableNode pap, CarPartsNode node) {
        if ((node.item.id != 86) && (node.item.id != 43)) {
            TableNode res = table.AddItem(pap, node.item);

            for (int i = 0; i < node.childs.size(); ++i) {
                fillCondTable(table, res, (CarPartsNode) node.childs.get(i));
            }

            return res;
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @param table
     *
     * @return
     */
    public TableNode FillCondTable(CondTable table) {
        return fillCondTable(table, null, m_root);
    }

    /**
     * Method description
     *
     *
     * @param parts
     */
    public void CloneData(CarParts parts) {
        for (int i = 0; i < 109; ++i) {
            if (this.m_Parts[i] == null) {
                continue;
            }

            this.m_Parts[i].auth = parts.m_Parts[i].auth;
            this.m_Parts[i].price = parts.m_Parts[i].price;
            this.m_Parts[i].condition = parts.m_Parts[i].condition;
            this.m_Parts[i].name = parts.m_Parts[i].name;
        }
    }

    private int resolveConstant(String name) {
        int result = -1;

        try {
            Class cl = super.getClass();
            Field field = cl.getField(name);

            result = field.getInt(this);
        } catch (Exception e) {
            eng.err("Resolve constant error. " + e.toString());
        }

        return result;
    }

    private void SetupHierarchy() {
        if (m_root == null) {
            m_root = new XmlTable().parse();
        }
    }

    /**
     * Method description
     *
     */
    public void FixupHierarchy() {
        Fixup(m_root);
    }

    void Fixup(CarPartsNode node) {
        if (node.childs.size() == 0) {
            if (node.item.condition == 0) {
                node.item.price = 0;
                node.item.auth = 0;
            }

            return;
        }

        for (int i = 0; i < node.childs.size(); ++i) {
            Fixup((CarPartsNode) node.childs.get(i));
        }

        node.item.condition = 0;
        node.item.price = 0;
        node.item.auth = 0;

        for (int i = 0; i < node.childs.size(); ++i) {
            CarPartsNode child = (CarPartsNode) node.childs.get(i);

            if (child.item.condition > node.item.condition) {
                node.item.condition = child.item.condition;
            }

            node.item.price += child.item.price;
            node.item.auth += child.item.auth;
        }

        node.item.price += 0;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ    
     */
    public static class CarPartsNode {
        Vector childs;
        Item item;

        CarPartsNode(Item _item) {
            this.childs = new Vector();
            this.item = _item;
        }

        /**
         * Method description
         *
         *
         * @param _item
         *
         * @return
         */
        public CarPartsNode AddChild(Item _item) {
            CarPartsNode child = new CarPartsNode(_item);

            this.childs.add(child);

            return child;
        }

        /**
         * Method description
         *
         *
         * @param ch
         */
        public void AddChild(CarPartsNode ch) {
            this.childs.add(ch);
        }
    }


    class XmlTable {
        private static final String FILENAME = "..\\data\\config\\repairsystem.xml";
        private static final String ROOT = "carsystems";
        private static final String ALIASE = "alias";
        private static final String NAME = "name";

        /**
         * Method description
         *
         *
         * @return
         */
        public CarParts.CarPartsNode parse() {
            Node top = XmlUtils.parse("..\\data\\config\\repairsystem.xml");
            NodeList root = top.getNamedChildren("carsystems");

            if (root.size() != 1) {
                eng.err("suspicious ..\\data\\config\\repairsystem.xml in XmlTable CarParts");

                return null;
            }

            NodeList vehicles = root.get(0).getChildren();

            if (vehicles.size() != 1) {
                eng.err("suspicious ..\\data\\config\\repairsystem.xml in XmlTable CarParts 2");

                return null;
            }

            return parse(vehicles.get(0));
        }

        private CarParts.CarPartsNode parse(Node node) {
            CarParts.CarPartsNode res =
                new CarParts.CarPartsNode(
                    CarParts.this.m_Parts[CarParts.this.resolveConstant(node.getAttribute("alias"))]);

            if ((res.item.id != 86) && (res.item.id != 43)) {
                res.item.name = resolveName(node.getAttribute("name"));
            }

            NodeList childs = node.getChildren();

            for (int i = 0; i < childs.size(); ++i) {
                res.AddChild(parse(childs.get(i)));
            }

            return res;
        }

        private String resolveName(String name) {
            return loc.getRepairTableString(name);
        }
    }
}


//~ Formatted in DD Std on 13/08/25
