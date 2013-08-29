/*
 * @(#)IsoQuestItemTask.java   13/08/28
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
import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptEvents.SpecialObjectEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

final class IsoQuestItemTask implements Comparable<Object> {
    private static final int ACTION_LIST_DEFAULT_CAPACITY = 32;
    private static final int DEFAULT_PHASE = 1;
    private static final int TOKENS_IN_PHASE_STRING = 2;

    /** Field description */
    public static final int DEFAULT_UID = -1;
    private SpecialObjectEvent.EventType eventType = SpecialObjectEvent.EventType.any;
    private int phase = 0;
    private int uid = -1;
    private boolean finish = false;
    private ArrayList<ObjectProperties> actionList = new ArrayList<ObjectProperties>(32);
    private final ArrayList<ScriptAction> readyActionList = new ArrayList<ScriptAction>();
    private IsoCondition condition = null;

    IsoQuestItemTask(Node target) {
        if (null == target) {
            throw new IllegalArgumentException("target must be non-null reference");
        }

        NamedNodeMap attributes = target.getAttributes();

        try {
            String phaseAndUid = XmlDocument.getAttributeValue(attributes, "phase",
                                     Integer.toString(1) + '.' + Integer.toString(-1));
            StringTokenizer tokenizer = new StringTokenizer(phaseAndUid, ".");

            if (2 != tokenizer.countTokens()) {
                ScenarioLogger.getInstance().parserWarning(
                    "Invalid isoquest.item.task.phase, set default phase nom == 1 default uid == -1");
                ScenarioLogger.getInstance().parserWarning(
                    "Phase must match to pattern '\num.num' where num is possetive integer");
            }

            if (tokenizer.hasMoreTokens()) {
                this.phase = Integer.parseInt(tokenizer.nextToken());
            } else {
                this.phase = 1;
            }

            if (tokenizer.hasMoreTokens()) {
                this.uid = Integer.parseInt(tokenizer.nextToken());
            } else {
                this.uid = -1;
            }
        } catch (NumberFormatException exception) {
            this.phase = 1;
            this.uid = -1;
            ScenarioLogger.getInstance().parserWarning(
                "Invalid isoquest.item.task.phase, set default phase nom == 1 default uid == -1");
            ScenarioLogger.getInstance().parserWarning(exception.getLocalizedMessage());
        }

        this.finish = Boolean.parseBoolean(XmlDocument.getAttributeValue(attributes, "finish", "false"));

        int actMainAttrIndex = XmlDocument.getIndexOfAttributeWithoutContent(attributes);

        if (-1 != actMainAttrIndex) {
            try {
                Node eventTypeNode = attributes.item(actMainAttrIndex);

                if (null != eventTypeNode) {
                    this.eventType = SpecialObjectEvent.EventType.f2;
                } else {
                    this.eventType = SpecialObjectEvent.EventType.none;
                }
            } catch (IllegalArgumentException exception) {
                this.eventType = SpecialObjectEvent.EventType.none;
                System.err.println("illegal eventType came from XML, must be one of: any, f2, exit, enter");
                System.err.println(exception.getLocalizedMessage());
                exception.printStackTrace(System.err);
            }
        } else {
            this.eventType = SpecialObjectEvent.EventType.none;
        }

        XmlFilter filter = new XmlFilter(target.getChildNodes());

        this.condition = new IsoCondition();

        if (!(this.condition.extractFromNode(filter.nodeNameNext("cond")))) {
            this.condition = null;
        }

        filter.goOnStart();
        this.actionList = ObjectProperties.extractListEx(filter);
    }

    IsoQuestItemTask(List<ScriptAction> actions, IsoCondition condition) {
        this.readyActionList.addAll(actions);
        this.condition = condition;
    }

    IsoCondition getCondition() {
        return this.condition;
    }

    SpecialObjectEvent.EventType getEventType() {
        return this.eventType;
    }

    int getPhase() {
        return this.phase;
    }

    int getUid() {
        return this.uid;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isLastPhase() {
        return this.finish;
    }

    void getActionList(List<ObjectProperties> properties, List<ScriptAction> actions) {
        properties.addAll(this.actionList);
        actions.addAll(this.readyActionList);
    }

    /**
     * Method description
     *
     *
     * @param o
     *
     * @return
     */
    @Override
    public int compareTo(Object o) {
        IsoQuestItemTask compareTarget = (IsoQuestItemTask) o;

        if (this.phase < compareTarget.phase) {
            return -1;
        }

        if (this.phase > compareTarget.phase) {
            return 1;
        }

        return 0;
    }
}


//~ Formatted in DD Std on 13/08/28
