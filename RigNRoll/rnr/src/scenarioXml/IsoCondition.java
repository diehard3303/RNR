/*
 * @(#)IsoCondition.java   13/08/27
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

import rnr.src.scriptEvents.EventChecker;

//~--- JDK imports ------------------------------------------------------------

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public final class IsoCondition {
    private ObjectProperties properties = null;
    private EventChecker readyCondition = null;
    private IsoCondition andCondition = null;
    private LinkedList<IsoCondition> orConditions = null;

    /**
     * Constructs ...
     *
     */
    public IsoCondition() {}

    /**
     * Constructs ...
     *
     *
     * @param readyCondition
     */
    public IsoCondition(EventChecker readyCondition) {
        this.readyCondition = readyCondition;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public EventChecker getReadyCondition() {
        return this.readyCondition;
    }

    /**
     * Method description
     *
     *
     * @param root
     *
     * @return
     */
    public boolean extractFromNode(Node root) {
        if (null == root) {
            return false;
        }

        if (0 != root.getNodeName().compareTo("cond")) {
            throw new IllegalArgumentException("root is not valid condition node");
        }

        Properties thisConditionParams = new Properties();
        String thisCondidionName = XmlDocument.extractMainAttribAndParams(root.getAttributes(), thisConditionParams);

        this.properties = new ObjectProperties(thisCondidionName, thisConditionParams);
        this.andCondition = new IsoCondition();

        if (!(this.andCondition.extractFromNode(new XmlFilter(root.getChildNodes()).nodeNameNext("cond")))) {
            this.andCondition = null;
        }

        Node current = root.getNextSibling();

        while (null != current) {
            if (0 == current.getNodeName().compareTo("cond")) {
                IsoCondition condition = new IsoCondition();
                Properties params = new Properties();
                String name = XmlDocument.extractMainAttribAndParams(current.getAttributes(), params);

                condition.properties = new ObjectProperties(name, params);

                IsoCondition childCondition = new IsoCondition();

                if (!(childCondition.extractFromNode(new XmlFilter(current.getChildNodes()).nodeNameNext("cond")))) {
                    childCondition = null;
                }

                condition.andCondition = childCondition;

                if (null == this.orConditions) {
                    this.orConditions = new LinkedList<IsoCondition>();
                }

                this.orConditions.add(condition);
            }

            current = current.getNextSibling();
        }

        return true;
    }

    ObjectProperties getProperties() {
        return this.properties;
    }

    IsoCondition getAndCondition() {
        return this.andCondition;
    }

    List<IsoCondition> getOrList() {
        return this.orConditions;
    }
}


//~ Formatted in DD Std on 13/08/27
