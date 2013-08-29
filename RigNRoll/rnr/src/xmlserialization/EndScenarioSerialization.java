/*
 * @(#)EndScenarioSerialization.java   13/08/28
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
import rnr.src.rnrscenario.EndScenario;
import rnr.src.rnrscenario.EndScenario.DelayedAction;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class EndScenarioSerialization implements XmlSerializable {
    private static EndScenarioSerialization instance = new EndScenarioSerialization();

    /**
     * Method description
     *
     *
     * @return
     */
    public static EndScenarioSerialization getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "endscenario";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(EndScenario value, PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        List<DelayedAction> list = value.getDelayedActions();

        if (null != list) {
            Helper.openNode(stream, "endscenariotimeactions");

            for (EndScenario.DelayedAction item : list) {
                ActionTaskDaysSerialization.serializeXML(item, stream);
            }

            Helper.closeNode(stream, "endscenariotimeactions");
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void deserializeXML(rnr.src.xmlutils.Node node) {
        EndScenario value = EndScenario.getInstance();
        rnr.src.xmlutils.Node timeActionsNode = node.getNamedChild("endscenariotimeactions");

        if (null == timeActionsNode) {
            return;
        }

        List timeActionsList = new ArrayList();
        NodeList list = timeActionsNode.getNamedChildren(ActionTaskDaysSerialization.getNodeName());

        for (rnr.src.xmlutils.Node itemNode : list) {
            EndScenario.DelayedAction timeAction = ActionTaskDaysSerialization.deserializeXML(itemNode);

            timeActionsList.add(timeAction);
        }

        value.setDelayedActions(timeActionsList);
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        serializeXML(EndScenario.getInstance(), stream);
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
     * @return
     */
    @Override
    public String getRootNodeName() {
        return getNodeName();
    }
}


//~ Formatted in DD Std on 13/08/28
