/*
 * @(#)OrganizerSerialization.java   13/08/28
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

import rnr.src.rnrcore.XmlSerializable;
import rnr.src.rnrorg.IStoreorgelement;
import rnr.src.rnrorg.MissionOrganiser;
import rnr.src.rnrorg.Organizers;
import rnr.src.rnrorg.Scorgelement;
import rnr.src.rnrorg.WarehouseOrder;
import rnr.src.rnrorg.organaiser;
import rnr.src.scenarioUtils.Pair;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class OrganizerSerialization implements XmlSerializable {
    private static OrganizerSerialization instance = new OrganizerSerialization();

    /**
     * Method description
     *
     *
     * @return
     */
    public static OrganizerSerialization getInstance() {
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
        deserializeXML(new Node(node));
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
        serializeXML(stream);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "organiser";
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void serializeXML(PrintStream stream) {
        List<Pair<String, String>> attributesMain = Helper.createSingleAttribute("numwarehouseorders",
                                                        WarehouseOrder.getCountNumOrder());

        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributesMain);
        Helper.openNode(stream, "orgelements");

        HashMap<String, IStoreorgelement> specialElements = Organizers.getInstance().getOrganizerElementsSpecial();

        if (!(specialElements.isEmpty())) {
            Set<Entry<String, IStoreorgelement>> set = specialElements.entrySet();

            for (Map.Entry entry : set) {
                if (entry.getValue() instanceof Scorgelement) {
                    Helper.printOpenNodeWithAttributes(stream, "element",
                                                       Helper.createSingleAttribute("name", (String) entry.getKey()));
                    OrganizerNodeSerialization.serializeXML((Scorgelement) entry.getValue(), stream);
                    Helper.closeNode(stream, "element");
                }
            }
        }

        Helper.closeNode(stream, "orgelements");
        Helper.openNode(stream, "items");

        Vector orgelements = organaiser.getInstance().getAllorgelements();
        IStoreorgelement currentElement = organaiser.getCurrent();
        IStoreorgelement currentWarehouseElement = organaiser.getInstance().getCurrentWarehouseOrder();

        if ((null != orgelements) && (!(orgelements.isEmpty()))) {
            for (IStoreorgelement element : orgelements) {
                boolean isCurrent = (currentElement != null) && (currentElement.equals(element));

                ListElementSerializator.serializeXMLListelementOpen(stream);

                if (element instanceof Scorgelement) {
                    OrganizerNodeSerialization.serializeXML((Scorgelement) element, stream);
                } else if (element instanceof WarehouseOrder) {
                    WarehouseOrganizerNodeSerialization.serializeXML((WarehouseOrder) element, stream);

                    boolean isCurrentWarehouseOrder = (currentWarehouseElement != null)
                                                      && (currentWarehouseElement.equals(element));

                    if (isCurrentWarehouseOrder) {
                        Helper.printClosedNodeWithAttributes(stream, "current_warehouse_order", null);
                    }
                }

                if (isCurrent) {
                    Helper.printClosedNodeWithAttributes(stream, "current", null);
                }

                ListElementSerializator.serializeXMLListelementClose(stream);
            }
        }

        Helper.closeNode(stream, "items");
        Helper.openNode(stream, "missionitems");

        HashMap missions = MissionOrganiser.getInstance().getMissions();

        if ((null != missions) && (!(missions.isEmpty()))) {
            Set set = missions.entrySet();

            for (Map.Entry entry : set) {
                String missionName = (String) entry.getKey();
                String organizerName = (String) entry.getValue();
                List attributes = Helper.createSingleAttribute("missionname", missionName);

                Helper.addAttribute("orgname", organizerName, attributes);
                Helper.printClosedNodeWithAttributes(stream, "element", attributes);
            }
        }

        Helper.closeNode(stream, "missionitems");
        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void deserializeXML(Node node) {
        String numWarehousesOrdersString = node.getAttribute("numwarehouseorders");
        int numWarehousesOrders = Helper.ConvertToIntegerAndWarn(numWarehousesOrdersString, "numwarehouseorders",
                                      "OrganizerSerialization in deserializeXML ");

        WarehouseOrder.setCountNumOrder(numWarehousesOrders);

        Node organizersSpecialElements = node.getNamedChild("orgelements");

        if (null != organizersSpecialElements) {
            HashMap specialElements = new HashMap();
            NodeList listSpecialElements = organizersSpecialElements.getNamedChildren("element");

            if ((null != listSpecialElements) && (!(listSpecialElements.isEmpty()))) {
                for (Node element : listSpecialElements) {
                    String name = element.getAttribute("name");

                    if (null == name) {
                        Log.error(
                            "OrganizerSerialization in deserializeXML has no attribute named name in node named element");
                    }

                    Node orgElementNode = element.getNamedChild(OrganizerNodeSerialization.getNodeName());

                    if (null == orgElementNode) {
                        Log.error("OrganizerSerialization in deserializeXML has no node named "
                                  + OrganizerNodeSerialization.getNodeName() + " in node named " + "element");
                    }

                    Scorgelement _scorgelement = OrganizerNodeSerialization.deserializeXML(orgElementNode);

                    specialElements.put(name, _scorgelement);
                }
            }

            Organizers.getInstance().setOrganizerElementsSpecial(specialElements);
        }

        xmlutils.Node organizerNode = node.getNamedChild("items");
        xmlutils.Node missionOrganizerNode = node.getNamedChild("missionitems");

        if (null == organizerNode) {
            Log.error("OrganizerSerialization in deserializeXML has no named node items");
        } else {
            NodeList listElements = organizerNode.getNamedChildren(ListElementSerializator.getNodeName());

            if ((listElements != null) && (!(listElements.isEmpty()))) {
                IStoreorgelement currentElem = null;
                IStoreorgelement currentWarehouseOrder = null;

                for (xmlutils.Node element : listElements) {
                    boolean isCurrent = element.getNamedChild("current") != null;
                    xmlutils.Node orgelement = element.getNamedChild(OrganizerNodeSerialization.getNodeName());

                    if (null != orgelement) {
                        Scorgelement scOrgElement = OrganizerNodeSerialization.deserializeXML(orgelement);
                        IStoreorgelement elementToAddToOrganizer =
                            Organizers.getInstance().submitRestoredOrgElement(scOrgElement);

                        organaiser.getInstance().addOnRestore(elementToAddToOrganizer);

                        if (isCurrent) {
                            currentElem = elementToAddToOrganizer;
                        }
                    }

                    xmlutils.Node warehouseElement =
                        element.getNamedChild(WarehouseOrganizerNodeSerialization.getNodeName());

                    if (null != warehouseElement) {
                        boolean isCurrentWarehouseOrder = element.getNamedChild("current_warehouse_order") != null;
                        WarehouseOrder order = WarehouseOrganizerNodeSerialization.deserializeXML(warehouseElement);

                        organaiser.getInstance().addOnRestore(order);

                        if (isCurrent) {
                            currentElem = order;
                        }

                        if (isCurrentWarehouseOrder) {
                            currentWarehouseOrder = order;
                        }
                    }
                }

                if (null != currentWarehouseOrder) {
                    organaiser.getInstance().setCurrentWarehouseOrder(currentWarehouseOrder);
                }

                if (null != currentElem) {
                    organaiser.choose(currentElem);
                }
            }
        }

        if (null == missionOrganizerNode) {
            Log.error("OrganizerSerialization in deserializeXML has no named node missionitems");
        } else {
            NodeList listElements = missionOrganizerNode.getNamedChildren("element");

            if ((listElements == null) || (listElements.isEmpty())) {
                return;
            }

            for (xmlutils.Node element : listElements) {
                String missionName = element.getAttribute("missionname");
                String orgName = element.getAttribute("orgname");

                if (null == missionName) {
                    Log.error(
                        "OrganizerSerialization in deserializeXML has attribute named missionname in node named element");
                }

                if (null == orgName) {
                    Log.error(
                        "OrganizerSerialization in deserializeXML has attribute named orgname in node named element");
                }

                MissionOrganiser.getInstance().addMission(missionName, orgName);
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/28
