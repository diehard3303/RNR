/*
 * @(#)BlockedSOSerializator.java   13/08/28
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

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrscenario.SoBlock;
import rnr.src.rnrscenario.SoBlock.Blocked_SO;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class BlockedSOSerializator implements IXMLSerializable {
    private final SoBlock m_blocker;

    /**
     * Constructs ...
     *
     */
    public BlockedSOSerializator() {
        this.m_blocker = null;
    }

    /**
     * Constructs ...
     *
     *
     * @param object
     */
    public BlockedSOSerializator(SoBlock object) {
        this.m_blocker = object;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "blockso";
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void serialize(PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        Vector<Blocked_SO> blockedItems = this.m_blocker.getBlocked();

        for (SoBlock.Blocked_SO item : blockedItems) {
            ListElementSerializator.serializeXMLListelementOpen(stream);

            List attributes = new ArrayList();

            if (item.getName() != null) {
                Helper.addAttribute("name", item.getName(), attributes);
            }

            Helper.addAttribute("type", item.getType(), attributes);
            Helper.printOpenNodeWithAttributes(stream, "blocker", attributes);

            SoBlock.TimeOutCondition finishCondition = item.getCondition();

            if (finishCondition != null) {
                Helper.printOpenNodeWithAttributes(stream, "condition",
                                                   Helper.createSingleAttribute("timecondition", ""));

                SoBlock.TimeOutCondition timeOutCondition = finishCondition;
                CoreTime deltaTime = timeOutCondition.getDeltatime();

                if (deltaTime != null) {
                    Helper.openNode(stream, "deltatime");
                    CoreTimeSerialization.serializeXML(deltaTime, stream);
                    Helper.closeNode(stream, "deltatime");
                }

                CoreTime startTime = timeOutCondition.getTimeStart();

                if (startTime != null) {
                    Helper.openNode(stream, "starttime");
                    CoreTimeSerialization.serializeXML(startTime, stream);
                    Helper.closeNode(stream, "starttime");
                }

                Helper.closeNode(stream, "condition");
            }

            Helper.closeNode(stream, "blocker");
            ListElementSerializator.serializeXMLListelementClose(stream);
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void deSerialize(Node node) {
        NodeList listFilterNode = node.getNamedChildren(ListElementSerializator.getNodeName());

        if ((listFilterNode == null) || (listFilterNode.isEmpty())) {
            return;
        }

        Vector blocked = new Vector();

        for (Node listElementNode : listFilterNode) {
            Node blockSoElementNode = listElementNode.getNamedChild("blocker");
            String nameSoAttributeString = blockSoElementNode.getAttribute("name");
            String typeSoAttributeString = blockSoElementNode.getAttribute("type");
            String soName = nameSoAttributeString;
            int soType = Helper.ConvertToIntegerAndWarn(typeSoAttributeString, "type",
                             "Node blocker has no attribute type");
            SoBlock.TimeOutCondition finishCondition = null;
            Node conditionNode = blockSoElementNode.getNamedChild("condition");

            if (conditionNode != null) {
                String attrTimeCondition = conditionNode.getAttribute("timecondition");

                if (attrTimeCondition != null) {
                    CoreTime deltaTime = getCoreTimeSerializationFromNode(conditionNode, "deltatime");
                    CoreTime startTime = getCoreTimeSerializationFromNode(conditionNode, "starttime");

                    finishCondition = new SoBlock.TimeOutCondition(startTime, deltaTime);
                } else {
                    Log.error("Node condition has non of attributes nocondition or timecondition");
                }
            }

            blocked.add(new SoBlock.Blocked_SO(soType, soName, finishCondition));
        }

        SoBlock.getInstance().setBlocked(blocked);
    }

    private CoreTime getCoreTimeSerializationFromNode(Node node, String childName) {
        Node timeContainerNode = node.getNamedChild(childName);

        if (timeContainerNode == null) {
            Log.error("getCoreTimeSerializationFromNode. Node " + node.getName() + " has no child node with name "
                      + childName);

            return null;
        }

        Node timeNode = timeContainerNode.getNamedChild(CoreTimeSerialization.getNodeName());

        if (timeNode == null) {
            Log.error("getCoreTimeSerializationFromNode. Node " + childName + " has no child node with name "
                      + CoreTimeSerialization.getNodeName());

            return null;
        }

        CoreTime result = CoreTimeSerialization.deserializeXML(timeNode);

        if (result == null) {
            Log.error("getCoreTimeSerializationFromNode. result time is null.");

            return null;
        }

        return result;
    }
}


//~ Formatted in DD Std on 13/08/28
