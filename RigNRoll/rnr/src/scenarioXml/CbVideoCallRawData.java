/*
 * @(#)CbVideoCallRawData.java   13/08/27
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

import rnr.src.rnrloggers.ScenarioLogger;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

final class CbVideoCallRawData {
    private String name = null;
    private ArrayList<ObjectProperties> onStartActionList = null;
    private ArrayList<ObjectProperties> onFinishActionList = null;
    private final HashMap<Integer, ArrayList<ObjectProperties>> onAnswerActionList = new HashMap<Integer,
                                                                                         ArrayList<ObjectProperties>>();

    @SuppressWarnings("unused")
    CbVideoCallRawData(Node xmlDataRoot) {
        if ((null == xmlDataRoot) || (0 != xmlDataRoot.getNodeName().compareToIgnoreCase("element"))) {
            throw new IllegalArgumentException("xmlDataRoot must be non-null and must have name element");
        }

        NamedNodeMap attributes = xmlDataRoot.getAttributes();

        this.name = XmlDocument.getAttributeValue(attributes, "name", "unkownName");

        XmlFilter filter = new XmlFilter(xmlDataRoot.getChildNodes());
        Node startNode = filter.nodeNameNext("start");

        if (null != startNode) {
            this.onStartActionList = ObjectProperties.extractListEx(new XmlFilter(startNode.getChildNodes()));
        }

        filter.goOnStart();

        Node finishNode = filter.nodeNameNext("finish");

        if (null != finishNode) {
            this.onFinishActionList = ObjectProperties.extractListEx(new XmlFilter(finishNode.getChildNodes()));
        }

        filter.goOnStart();

        Node answerNode = filter.nodeNameNext("answer");

        while (null != answerNode) {
            Integer answerNom;

            try {
                answerNom = Integer.valueOf(Integer.parseInt(XmlDocument.getAttributeValue(answerNode.getAttributes(),
                        "value", "noninteger")));
            } catch (NumberFormatException exception) {
                ScenarioLogger.getInstance().parserWarning(
                    "CbVideoCallRawData.<init>: invalid value came from XML to answer nom: answer ignored;");
                ScenarioLogger.getInstance().parserWarning(exception.getLocalizedMessage());
                answerNode = filter.nodeNameNext("answer");
            }

            continue;
        }
    }

    String getName() {
        return this.name;
    }

    Set<Integer> getIndexesOfAnswerActions() {
        return this.onAnswerActionList.keySet();
    }

    ArrayList<ObjectProperties> getAnswerActions(Integer index) {
        return (this.onAnswerActionList.get(index));
    }

    ArrayList<ObjectProperties> getStartActions() {
        return this.onStartActionList;
    }

    ArrayList<ObjectProperties> getFinishActions() {
        return this.onFinishActionList;
    }

    @SuppressWarnings("unused")
    private static final class TagStrings {
        static final String NODE_NAME = "element";
        static final String ANSWER_NODE = "answer";
        static final String FINISH_NODE = "finish";
        static final String START_NODE = "start";
        static final String NAME_ATTRIBUTE_STRING = "name";
        static final String DIALOG_ATTRIBUTE_STRING = "dialog";
        static final String WHO_ATTRIBUTE_STRING = "who";
        static final String TIMECALL_ATTRIBUTE_STRING = "timecall";
        static final String TALK_ATTRIBUTE_STRING = "talkanyway";
        static final String VALUE_ATTRIBUTE_STRING = "value";
    }
}


//~ Formatted in DD Std on 13/08/27
