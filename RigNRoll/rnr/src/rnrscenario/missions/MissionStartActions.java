/*
 * @(#)MissionStartActions.java   13/08/28
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


package rnr.src.rnrscenario.missions;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rnr.src.rnrscenario.scenarioscript;
import rnr.src.scenarioMachine.FiniteStateMachine;
import rnr.src.scenarioXml.FiniteStatesSetBuilder;
import rnr.src.scenarioXml.ObjectProperties;
import rnr.src.scenarioXml.XmlFilter;
import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptEvents.EventListener;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class MissionStartActions implements EventListener {
    private ArrayList<ScriptAction> actions = new ArrayList<ScriptAction>();
    private String mission_name = null;
    private String org_name = null;

    /**
     * Constructs ...
     *
     *
     * @param mission_name
     * @param org_name
     */
    public MissionStartActions(String mission_name, String org_name) {
        this.mission_name = mission_name;
        this.org_name = org_name;
    }

    /**
     * Constructs ...
     *
     *
     * @param mission_name
     * @param org_name
     * @param load_actions
     */
    public MissionStartActions(String mission_name, String org_name, ArrayList<ScriptAction> load_actions) {
        this.mission_name = mission_name;
        this.org_name = org_name;
        this.actions.addAll(load_actions);
    }

    /**
     * Method description
     *
     *
     * @param source
     * @param node_name
     */
    public void extractActions(Node source, String node_name) {
        NodeList list = source.getChildNodes();
        Node strta_node = null;

        for (int i = 0; i < list.getLength(); ++i) {
            Node node = list.item(i);

            if (node.getNodeName().compareToIgnoreCase(node_name) == 0) {
                strta_node = node;

                break;
            }
        }

        if (null == strta_node) {
            return;
        }

        FiniteStateMachine scenarioMachine = scenarioscript.script.getScenarioMachine();
        XmlFilter actionFilter = new XmlFilter(strta_node.getChildNodes());
        List<ObjectProperties> actionsOnStartRawInfo = ObjectProperties.extractListEx(actionFilter);

        this.actions = FiniteStatesSetBuilder.actionsFromActionList(actionsOnStartRawInfo, this.org_name,
                scenarioMachine);
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     */
    @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event : eventTuple) {
            if ((event instanceof CreateMissionEvent)
                    && (this.mission_name.compareToIgnoreCase(((CreateMissionEvent) event).getMissionName()) == 0)) {
                for (ScriptAction action : this.actions) {
                    action.act();
                }
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/28
