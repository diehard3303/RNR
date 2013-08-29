/*
 * @(#)XmlFilter.java   13/08/27
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


package rnr.src.scenarioXml;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public final class XmlFilter {
    private NodeList nodes = null;
    private int currentIndex = 0;

    /**
     * Constructs ...
     *
     *
     * @param nodes
     */
    public XmlFilter(NodeList nodes) {
        if (null == nodes) {
            throw new IllegalArgumentException("nodes must be non-null reference");
        }

        this.nodes = nodes;
    }

    private Node filterNoNodeTypeNext(int nodeTypeToFilter) {
        while ((this.currentIndex < this.nodes.getLength())
                && (nodeTypeToFilter == this.nodes.item(this.currentIndex).getNodeType())) {
            this.currentIndex += 1;
        }

        if (this.currentIndex < this.nodes.getLength()) {
            return this.nodes.item(this.currentIndex);
        }

        return null;
    }

    private Node nodeTypeNext(int nodeTypeToFind) {
        while (this.currentIndex < this.nodes.getLength()) {
            if (nodeTypeToFind == this.nodes.item(this.currentIndex).getNodeType()) {
                this.currentIndex += 1;

                return this.nodes.item(this.currentIndex - 1);
            }

            this.currentIndex += 1;
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @param nodes
     */
    public void reset(NodeList nodes) {
        if (null == nodes) {
            throw new IllegalArgumentException("nodes must be non-null reference");
        }

        this.nodes = nodes;
        this.currentIndex = 0;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public XmlFilter goOnStart() {
        this.currentIndex = 0;

        return this;
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public Node nodeNameNext(String name) {
        if (null == name) {
            throw new IllegalArgumentException("name must be non-null");
        }

        while (this.currentIndex < this.nodes.getLength()) {
            String name_item = this.nodes.item(this.currentIndex).getNodeName();

            if (0 == name_item.compareToIgnoreCase(name)) {
                this.currentIndex += 1;

                return this.nodes.item(this.currentIndex - 1);
            }

            this.currentIndex += 1;
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Node filterNoCommentsNext() {
        return filterNoNodeTypeNext(8);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Node filterNoTextNext() {
        return filterNoNodeTypeNext(3);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Node nextElement() {
        return nodeTypeNext(1);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param dataProcessor
     * @param param
     */
    public void visitAllNodes(String name, XmlNodeDataProcessor dataProcessor, Object param) {
        goOnStart();

        Node current = nodeNameNext(name);

        while (null != current) {
            dataProcessor.process(current, param);
            current = nodeNameNext(name);
        }
    }

    /**
     * Method description
     *
     *
     * @param where
     *
     * @return
     */
    public static boolean textContentExists(Node where) {
        return ((null != where) && (null != where.getTextContent()) && (0 < where.getTextContent().length()));
    }
}


//~ Formatted in DD Std on 13/08/27
