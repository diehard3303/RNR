/*
 * @(#)SoSerialization.java   13/08/28
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
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.rnrscr.specobjects;
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
public class SoSerialization implements XmlSerializable {
    private static SoSerialization instance = new SoSerialization();

    /**
     * Method description
     *
     *
     * @return
     */
    public static SoSerialization getInstance() {
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
     * @param stream
     */
    @Override
    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        serializeXML(specobjects.getInstance(), stream);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "so";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(specobjects value, PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        ArrayList<cSpecObjects> cachedObjects = value.getAllLoadedObjCash();

        if (null != cachedObjects) {
            Helper.openNode(stream, "socach");

            for (cSpecObjects object : cachedObjects) {
                SpecObjectSerialization.serializeXML(object, stream);
            }

            Helper.closeNode(stream, "socach");
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
        rnr.src.xmlutils.Node cachSpecobjectsNode = node.getNamedChild("socach");

        if (null == cachSpecobjectsNode) {
            return;
        }

        NodeList specObjectsList = cachSpecobjectsNode.getNamedChildren(SpecObjectSerialization.getNodeName());
        ArrayList cachedObjects = new ArrayList();

        for (rnr.src.xmlutils.Node specobject : specObjectsList) {
            cSpecObjects newObject = SpecObjectSerialization.deserializeXML(specobject);

            cachedObjects.add(newObject);
        }

        specobjects.getInstance().setAllLoadedObjCash(cachedObjects);
    }
}


//~ Formatted in DD Std on 13/08/28
