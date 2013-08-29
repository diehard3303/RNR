/*
 * @(#)MissionPhase.java   13/08/28
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

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrscenario.missions.infochannels.InformationChannelData;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.scenarioMachine.FiniteStateMachine;
import rnr.src.scenarioXml.FiniteStatesSetBuilder;
import rnr.src.scenarioXml.IsoCondition;
import rnr.src.scenarioXml.ObjectProperties;
import rnr.src.scenarioXml.XmlFilter;
import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptEvents.ComplexEventReaction;
import rnr.src.scriptEvents.EventChecker;
import rnr.src.scriptEvents.EventCheckersBuilders;
import rnr.src.scriptEvents.SpecialObjectEvent.EventType;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public final class MissionPhase {
    private EventChecker condition = null;
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<ScriptAction> actions = new LinkedList();
    private ComplexEventReaction reaction = null;
    private MissionEndUIController uiController = null;
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final List<InformationChannelData> infoChannels = new LinkedList();

    MissionPhase(List<InformationChannelData> channels, List<ScriptAction> load_actions, EventChecker conditions) {
        this.actions.addAll(load_actions);
        this.infoChannels.addAll(channels);

        if (null != conditions) {
            this.reaction = new ComplexEventReaction(conditions, 0);
            this.reaction.addAction(this.actions);
        }

        EventCheckersBuilders.do_construct();

        if (null != this.uiController) {
            return;
        }

        this.uiController = new NullMissionEndUIController();
    }

    MissionPhase(Node dataSource, String defaultArgument, MissionCreationContext context) {
        assert(null != dataSource) : "dataSource must be non-null reference";
        loadActions(dataSource, defaultArgument);

        XmlFilter dataMiner = new XmlFilter(dataSource.getChildNodes());

        loadInformationChannels(dataMiner.nodeNameNext("channels"), context);
        dataMiner.goOnStart();
        loadCondition(dataMiner.nodeNameNext("cond"));

        if (null != this.condition) {
            this.reaction = new ComplexEventReaction(this.condition, 0);
            this.reaction.addAction(this.actions);
        }

        dataMiner.goOnStart();

        Node menuNode = dataMiner.nodeNameNext("menu");

        if (null != menuNode) {
            Node textNode = menuNode.getAttributes().getNamedItem("text");
            Node materialNode = menuNode.getAttributes().getNamedItem("material");

            if ((XmlFilter.textContentExists(textNode)) && (XmlFilter.textContentExists(materialNode))) {
                this.uiController = new PostChannelsAfterMenu(textNode.getTextContent(), materialNode.getTextContent());
            }
        }

        if (null == this.uiController) {
            this.uiController = new NullMissionEndUIController();
        }

        EventCheckersBuilders.do_construct();
    }

    private void loadActions(Node source, String defaultArgument) {
        assert(null != source) : "source must be valid non-null reference";

        FiniteStateMachine scenarioMachine = scenarioscript.script.getScenarioMachine();
        XmlFilter actionFilter = new XmlFilter(source.getChildNodes());
        ArrayList<ObjectProperties> actionsOnStartRawInfo = ObjectProperties.extractListEx(actionFilter);

        this.actions = FiniteStatesSetBuilder.actionsFromActionList(actionsOnStartRawInfo, defaultArgument,
                scenarioMachine);
    }

    private void loadInformationChannels(Node source, MissionCreationContext context) {
        if (null == source) {
            return;
        }

        XmlFilter childVisitor = new XmlFilter(source.getChildNodes());

        childVisitor.visitAllNodes("infochannel", new InfoChannelsXmlExtractor(this.infoChannels, context), null);

        int mainChannelsCount = 0;

        for (InformationChannelData loaded : this.infoChannels) {
            if (loaded.isMarkedAsMain()) {
                ++mainChannelsCount;
            }
        }

        if (1 >= mainChannelsCount) {
            return;
        }

        MissionsLogger.getInstance().doLog("found multiple main channel", Level.SEVERE);
    }

    private void loadCondition(Node source) {
        if (null == source) {
            return;
        }

        IsoCondition endConditionRawData = new IsoCondition();

        endConditionRawData.extractFromNode(source);
        this.condition = FiniteStatesSetBuilder.buildEventChecker(endConditionRawData, SpecialObjectEvent.EventType.f2);
    }

    ComplexEventReaction getPhaseReaction() {
        return this.reaction;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<InformationChannelData> getInfoChannels() {
        return Collections.unmodifiableList(this.infoChannels);
    }

    void addOnAcceptAction(ScriptAction action) {
        if (null == action) {
            return;
        }

        for (InformationChannelData infoChannelData : this.infoChannels) {
            infoChannelData.addOnAcceptAction(action);
        }
    }

    void execute() {
        for (ScriptAction action : this.actions) {
            action.act();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public MissionEndUIController getUIController() {
        return this.uiController;
    }
}


//~ Formatted in DD Std on 13/08/28
