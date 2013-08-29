/*
 * @(#)XmlSerializableController.java   13/08/26
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


package rnr.src.rnrcore;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscenario.consistency.ScenarioGarbageFinder;
import rnr.src.rnrscenario.consistency.ScenarioStage;
import rnr.src.rnrscenario.consistency.StageChangedListener;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class XmlSerializableController implements StageChangedListener {
    private final HashMap<ObjectXmlSerailizatorId, ObjectXmlSerializable> objectToSerialize;

    /**
     * Constructs ...
     *
     */
    public XmlSerializableController() {
        this.objectToSerialize = new HashMap();
    }

    /**
     * Method description
     *
     */
    public void clearSerializators() {
        this.objectToSerialize.clear();
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        Set setObjectSerializators = this.objectToSerialize.entrySet();

        for (Map.Entry entry : setObjectSerializators) {
            ObjectXmlSerializable object = (ObjectXmlSerializable) entry.getValue();
            IXMLSerializable serializator = object.getXmlSerializator();

            serializator.serialize(stream);
        }
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    public void loadFromNode(org.w3c.dom.Node node) {
        if (null == node) {
            return;
        }

        xmlutils.Node utilNode = new xmlutils.Node(node);
        NodeList children = utilNode.getChildren();
        HashMap nodesWereDeserialized = new HashMap();

        for (xmlutils.Node serializationNode : children) {
            String nodeName = serializationNode.getName();

            if (!(nodesWereDeserialized.containsKey(nodeName))) {
                nodesWereDeserialized.put(nodeName, Boolean.valueOf(true));

                IXMLSerializable serializationInterface =
                    XmlSerializationFabric.getDeSerializationInterface(serializationNode.getName());

                if (null != serializationInterface) {
                    serializationInterface.deSerialize(serializationNode);
                }
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param object
     */
    public void registerObject(ObjectXmlSerializable object) {
        if (this.objectToSerialize.containsKey(object.getSerializationId())) {
            eng.log("Execution ERRORR. ObjectXmlSerializator recieves registered object with class name "
                    + object.getClass().getName() + " .");

            return;
        }

        this.objectToSerialize.put(object.getSerializationId(), object);
    }

    /**
     * Method description
     *
     *
     * @param object
     */
    public void unRegisterObject(ObjectXmlSerializable object) {
        this.objectToSerialize.remove(object.getSerializationId());
    }

    /**
     * Method description
     *
     *
     * @param scenarioStage
     */
    @Override
    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(super.getClass().getName(),
                this.objectToSerialize.values(), scenarioStage);
    }
}


//~ Formatted in DD Std on 13/08/26
