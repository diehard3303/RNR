/*
 * @(#)ActionTaskDaysSerialization.java   13/08/27
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

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrscenario.EndScenario;
import rnr.src.rnrscenario.EndScenario.DelayedAction;
import rnr.src.scriptEvents.ScriptEvent;
import rnr.src.scriptEvents.VoidEvent;
import rnr.src.xmlutils.Node;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public class ActionTaskDaysSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "timeaction";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void serializeXML(EndScenario.DelayedAction value, PrintStream stream) {
        List attributes = Helper.createSingleAttribute("days", value.getTimeDelta().gDate());

        Helper.addAttribute("hours", value.getTimeDelta().gHour(), attributes);
        Helper.addAttribute("name", value.getName(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);

        ScriptEvent event = value.getEvent_on_activate();

        if (event != null) {
            if (event instanceof VoidEvent) {
                VoidEvent voidEvent = (VoidEvent) event;

                Helper.printClosedNodeWithAttributes(stream, "enterevent",
                        Helper.createSingleAttribute("value", voidEvent.getInfo()));
            } else {
                Log.error("ActionTaskDaysSerialization on serializeXML has getEvent_on_activate unexpected event type "
                          + event.getClass().getName());
            }
        }

        ScriptEvent event1 = value.getEvent_on_remove();

        if (event1 != null) {
            if (event1 instanceof VoidEvent) {
                VoidEvent voidEvent = (VoidEvent) event1;

                Helper.printClosedNodeWithAttributes(stream, "exitevent",
                        Helper.createSingleAttribute("value", voidEvent.getInfo()));
            } else {
                Log.error("ActionTaskDaysSerialization on serializeXML has getEvent_on_remove unexpected event type "
                          + event1.getClass().getName());
            }
        }

        CoreTimeSerialization.serializeXML(value.getTimeStart(), stream);
        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static EndScenario.DelayedAction deserializeXML(Node node) {
        String daysString = node.getAttribute("days");
        String hoursString = node.getAttribute("hours");
        int daysValue = Helper.ConvertToIntegerAndWarn(daysString, "days",
                            "ActionTaskDaysSerialization on deserializeXML");
        int hoursValue = (null == hoursString)
                         ? 0
                         : Helper.ConvertToIntegerAndWarn(hoursString, "hours",
                             "ActionTaskDaysSerialization on deserializeXML");
        String name = node.getAttribute("name");
        ScriptEvent onEnter = null;
        Node onEnterNode = node.getNamedChild("enterevent");

        if (null != onEnterNode) {
            String eventValue = onEnterNode.getAttribute("value");

            onEnter = new VoidEvent(eventValue);
        } else {
            Log.warning("ActionTaskDaysSerialization on deserializeXML has no node named enterevent");
        }

        ScriptEvent onLeave = null;
        Node onLeaveNode = node.getNamedChild("exitevent");

        if (null != onLeaveNode) {
            String eventValue = onLeaveNode.getAttribute("value");

            onLeave = new VoidEvent(eventValue);
        } else {
            Log.warning("ActionTaskDaysSerialization on deserializeXML has no node named exitevent");
        }

        CoreTime startTime = null;
        Node startTimeNode = node.getNamedChild(CoreTimeSerialization.getNodeName());

        if (null != startTimeNode) {
            startTime = CoreTimeSerialization.deserializeXML(startTimeNode);
        } else {
            Log.warning("ActionTaskDaysSerialization on deserializeXML has no node named "
                        + CoreTimeSerialization.getNodeName());
        }

        EndScenario.DelayedAction result = new EndScenario.DelayedAction(name, daysValue, hoursValue, onEnter, onLeave);

        result.setTimeStart(startTime);

        return result;
    }
}


//~ Formatted in DD Std on 13/08/27
