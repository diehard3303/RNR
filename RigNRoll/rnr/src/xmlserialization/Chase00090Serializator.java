/*
 * @(#)Chase00090Serializator.java   13/08/28
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

import rnr.src.players.aiplayer;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrcore.eng;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.ScenarioHost;
import rnr.src.rnrscenario.controllers.chase00090;
import rnr.src.xmlserialization.nxs.SerializatorOfAnnotated;
import rnr.src.xmlutils.Node;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = 1,
    fieldWithDesiredStage = ""
)
public class Chase00090Serializator implements IXMLSerializable {
    private static ScenarioHost host;
    private chase00090 serializationTarget = null;

    /**
     * Constructs ...
     *
     */
    public Chase00090Serializator() {
        this.serializationTarget = null;
    }

    /**
     * Constructs ...
     *
     *
     * @param value
     */
    public Chase00090Serializator(chase00090 value) {
        this.serializationTarget = value;
    }

    /**
     * Method description
     *
     *
     * @param host
     */
    public static void setHost(ScenarioHost host) {
        Chase00090Serializator.host = host;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "chase00090";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(chase00090 value, PrintStream stream) {
        Helper.openNode(stream, getNodeName());
        SerializatorOfAnnotated.getInstance().saveState(stream, value);

        aiplayer dorothy = chase00090.getDorothy();

        if (null != dorothy) {
            Helper.openNode(stream, "Dorothy");
            AIPlayerSerializator.serializeXML(dorothy, stream);
            Helper.closeNode(stream, "Dorothy");
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    public static void deserializeXML(Node node) {
        aiplayer dorothy = null;
        Node dorothyNode = node.getNamedChild("Dorothy");

        if (null != dorothyNode) {
            Node itemNode = dorothyNode.getNamedChild(AIPlayerSerializator.getNodeName());

            if (null != itemNode) {
                dorothy = AIPlayerSerializator.deserializeXML(itemNode);
            }
        }

        chase00090 ware =
            (chase00090) SerializatorOfAnnotated.getInstance().loadStateOrNull(node.getNamedChild("chase00090"));

        if (null == ware) {
            eng.err("Error while loading saved game: failed to load instance of chase00090");
        } else {
            ware.setHost(host);
        }

        chase00090.setDorothy(dorothy);
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void deSerialize(Node node) {
        deserializeXML(node);
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    public void serialize(PrintStream stream) {
        serializeXML(this.serializationTarget, stream);
    }
}


//~ Formatted in DD Std on 13/08/28
