/*
 * @(#)Node.java   13/08/26
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


package rnr.src.xmlutils;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.NamedNodeMap;

import rnr.src.scenarioUtils.Pair;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class Node {
    private org.w3c.dom.Node node = null;

    /**
     * Constructs ...
     *
     *
     * @param node
     */
    public Node(org.w3c.dom.Node node) {
        this.node = node;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public org.w3c.dom.Node getNode() {
        return this.node;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final String getName() {
        return this.node.getNodeName();
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public final boolean isName(String name) {
        return (0 == this.node.getNodeName().compareTo(name));
    }

    private Node getFirstElement(NodeList res) {
        if (res.isEmpty()) {
            return null;
        }

        return (res.get(0));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final String getValue() {
        org.w3c.dom.NodeList list = this.node.getChildNodes();

        for (int i = 0; i < list.getLength(); ++i) {
            if (list.item(i).getNodeName().compareTo("#text") == 0) {
                return list.item(i).getNodeValue();
            }
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @param attrinuteName
     *
     * @return
     */
    public final String getAttribute(String attrinuteName) {
        NamedNodeMap map = this.node.getAttributes();
        org.w3c.dom.Node nd = map.getNamedItem(attrinuteName);

        if (null == nd) {
            return null;
        }

        return nd.getNodeValue();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final List<Pair<String, String>> getAllAttributes() {
        NamedNodeMap map = this.node.getAttributes();
        List result = new ArrayList();

        for (int i = 0; i < map.getLength(); ++i) {
            org.w3c.dom.Node nd = map.item(i);

            result.add(new Pair(nd.getNodeName(), nd.getNodeValue()));
        }

        return result;
    }

    /**
     * Method description
     *
     *
     * @param attrinuteName
     *
     * @return
     */
    public final boolean hasAttribute(String attrinuteName) {
        NamedNodeMap map = this.node.getAttributes();

        return (null != map.getNamedItem(attrinuteName));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final NodeList getChildren() {
        NodeList res = new NodeList();
        org.w3c.dom.NodeList list = this.node.getChildNodes();

        for (int i = 0; i < list.getLength(); ++i) {
            String check = list.item(i).getNodeName();

            if (check.compareTo("#text") != 0) {
                res.add(new Node(list.item(i)));
            }
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final Node getChild() {
        NodeList res = getChildren();

        return getFirstElement(res);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public final NodeList getNamedChildren(String name) {
        NodeList res = new NodeList();
        org.w3c.dom.NodeList list = this.node.getChildNodes();

        for (int i = 0; i < list.getLength(); ++i) {
            Node nd = new Node(list.item(i));

            if (nd.isName(name)) {
                res.add(nd);
            }
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public final Node getNamedChild(String name) {
        NodeList res = getNamedChildren(name);

        return getFirstElement(res);
    }
}


//~ Formatted in DD Std on 13/08/26
