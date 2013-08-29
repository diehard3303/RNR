/*
 * @(#)ObjectProperties.java   13/08/28
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

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public final class ObjectProperties {
    private static final int DEFAULT_CAPACITY = 8;
    private Properties params = null;
    private String name = null;
    private ObjectProperties childObject = null;

    private ObjectProperties(String name) {
        if (null == name) {
            throw new IllegalArgumentException("name must be non-null reference");
        }

        this.name = name;
        this.params = new Properties();
    }

    ObjectProperties(String name, Properties params) {
        if ((null == name) || (null == params)) {
            throw new IllegalArgumentException("name and params must be non-null reference");
        }

        this.name = name;
        this.params = params;
    }

    /**
     * Method description
     *
     *
     * @param root
     * @param nodesToIgnore
     *
     * @return
     */
    public static LinkedList<ObjectProperties> extractList(Node root, String[] nodesToIgnore) {
        if (null == root) {
            throw new IllegalArgumentException("root must be non-null reference");
        }

        LinkedList<ObjectProperties> out = new LinkedList<ObjectProperties>();
        XmlFilter filter = new XmlFilter(root.getChildNodes());
        Node actionObjectNode = filter.nextElement();

        while (null != actionObjectNode) {
            for (String nameToIgnore : nodesToIgnore) {
                if (0 != nameToIgnore.compareToIgnoreCase(actionObjectNode.getNodeName())) {
                    continue;
                }

                actionObjectNode = filter.nextElement();

                break;
            }

            ObjectProperties actionObject = new ObjectProperties(actionObjectNode.getNodeName());
            NamedNodeMap attributesMap = actionObjectNode.getAttributes();

            for (int i = attributesMap.getLength() - 1; 0 <= i; --i) {
                if ((null == attributesMap.item(i).getTextContent())
                        || (0 == attributesMap.item(i).getTextContent().length())) {
                    actionObject.setParam("name", attributesMap.item(i).getNodeName());
                } else {
                    actionObject.setParam(attributesMap.item(i).getNodeName(), attributesMap.item(i).getTextContent());
                }
            }

            out.add(actionObject);
            actionObjectNode = filter.nextElement();
        }

        return out;
    }

    /**
     * Method description
     *
     *
     * @param filter
     * @param nodesNames
     *
     * @return
     */
    public static ArrayList<ObjectProperties> extractListEx(XmlFilter filter, String nodesNames) {
        if (null == filter) {
            return new ArrayList<ObjectProperties>();
        }

        ArrayList<ObjectProperties> out = new ArrayList<ObjectProperties>(8);
        Node currentActionNode = filter.nodeNameNext(nodesNames);

        while (null != currentActionNode) {
            NamedNodeMap actAttrs = currentActionNode.getAttributes();

            if (0 < actAttrs.getLength()) {
                Properties params = new Properties();
                String className = XmlDocument.extractMainAttribAndParams(actAttrs, params);
                ObjectProperties actionClassParams = new ObjectProperties(className, params);
                XmlFilter actionDelayNodeDetector = new XmlFilter(currentActionNode.getChildNodes());
                Node delayNode = actionDelayNodeDetector.nodeNameNext("wait");

                if (null != delayNode) {
                    String subClassName = "wait";
                    Properties subClassParams = new Properties();

                    subClassParams.put("name",
                                       XmlDocument.getAttributeValue(delayNode.getAttributes(), "name", "unkonwn"));
                    subClassParams.put("days", XmlDocument.getAttributeValue(delayNode.getAttributes(), "days", "1"));
                    subClassParams.put("hours", XmlDocument.getAttributeValue(delayNode.getAttributes(), "hours", "0"));
                    actionClassParams.createChild(subClassName, subClassParams);
                }

                out.add(actionClassParams);
            }

            currentActionNode = filter.nodeNameNext(nodesNames);
        }

        return out;
    }

    /**
     * Method description
     *
     *
     * @param filter
     *
     * @return
     */
    public static ArrayList<ObjectProperties> extractListEx(XmlFilter filter) {
        return extractListEx(filter, "action");
    }

    private void setParam(String paramName, String paramValue) {
        if ((null == paramName) || (null == paramValue)) {
            throw new IllegalArgumentException("paramName and paramValue must be non-null references");
        }

        if (null == this.params) {
            this.params = new Properties();
        }

        this.params.setProperty(paramName, paramValue);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Properties getParams() {
        return this.params;
    }

    ObjectProperties getChildObject() {
        return this.childObject;
    }

    void createChild(String name, Properties params) {
        if ((null == name) || (null == params)) {
            throw new IllegalArgumentException("name and params must be non-null references");
        }

        this.childObject = new ObjectProperties(name, params);
    }

    /**
     * @return the defaultCapacity
     */
    public static int getDefaultCapacity() {
        return DEFAULT_CAPACITY;
    }
}


//~ Formatted in DD Std on 13/08/28
