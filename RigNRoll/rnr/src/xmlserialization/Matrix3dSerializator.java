/*
 * @(#)Matrix3dSerializator.java   13/08/28
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

import rnr.src.rnrcore.matrixJ;
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
public class Matrix3dSerializator {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "matrix3d";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(matrixJ value, PrintStream stream) {
        Helper.openNode(stream, "matrix3d");

        if (null == value.v0) {
            Log.error("ERRORR. Matrix3dSerializator. serializeXML null==value.v0.");
        } else {
            Helper.openNode(stream, "v0");
            Vector3dSerializator.serializeXML(value.v0, stream);
            Helper.closeNode(stream, "v0");
        }

        if (null == value.v1) {
            Log.error("ERRORR. Matrix3dSerializator. serializeXML null==value.v1.");
        } else {
            Helper.openNode(stream, "v1");
            Vector3dSerializator.serializeXML(value.v1, stream);
            Helper.closeNode(stream, "v1");
        }

        if (null == value.v2) {
            Log.error("ERRORR. Matrix3dSerializator. serializeXML null==value.v2.");
        } else {
            Helper.openNode(stream, "v2");
            Vector3dSerializator.serializeXML(value.v2, stream);
            Helper.closeNode(stream, "v2");
        }

        Helper.closeNode(stream, "matrix3d");
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static matrixJ deserializeXML(Node node) {
        Node v0Node = node.getNamedChild("v0");
        Node v1Node = node.getNamedChild("v1");
        Node v2Node = node.getNamedChild("v2");
        matrixJ M = new matrixJ();

        if (null == v0Node) {
            Log.error("ERRORR. Matrix3dSerializator. deserializeXML. No node with name v0");
        } else {
            Node vectorNode = v0Node.getNamedChild(Vector3dSerializator.getNodeName());

            if (null != vectorNode) {
                M.v0 = Vector3dSerializator.deserializeXML(vectorNode);
            } else {
                Log.error(
                    "ERRORR. Matrix3dSerializator. deserializeXML. Node with name v0 has no child node with name "
                    + Vector3dSerializator.getNodeName());
            }
        }

        if (null == v1Node) {
            Log.error("ERRORR. Matrix3dSerializator. deserializeXML. No node with name v1");
        } else {
            Node vectorNode = v1Node.getNamedChild(Vector3dSerializator.getNodeName());

            if (null != vectorNode) {
                M.v1 = Vector3dSerializator.deserializeXML(vectorNode);
            } else {
                Log.error(
                    "ERRORR. Matrix3dSerializator. deserializeXML. Node with name v1 has no child node with name "
                    + Vector3dSerializator.getNodeName());
            }
        }

        if (null == v2Node) {
            Log.error("ERRORR. Matrix3dSerializator. deserializeXML. No node with name v2");
        } else {
            Node vectorNode = v2Node.getNamedChild(Vector3dSerializator.getNodeName());

            if (null != vectorNode) {
                M.v2 = Vector3dSerializator.deserializeXML(vectorNode);
            } else {
                Log.error(
                    "ERRORR. Matrix3dSerializator. deserializeXML. Node with name v2 has no child node with name "
                    + Vector3dSerializator.getNodeName());
            }
        }

        return M;
    }
}


//~ Formatted in DD Std on 13/08/28
