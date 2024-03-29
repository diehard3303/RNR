/*
 * @(#)CbvEventListenerBuilder.java   13/08/28
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

import rnr.src.rnrloggers.ScenarioLogger;
import rnr.src.scenarioMachine.FiniteStateMachine;
import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptEvents.CbVideoCallback;
import rnr.src.scriptEvents.CbVideoEventsListener;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public class CbvEventListenerBuilder {
    private CbVideoEventsListener ware = null;

    /**
     * Constructs ...
     *
     *
     * @param pathToXmlFile
     * @param scenarioMachine
     *
     * @throws IllegalArgumentException
     */
    public CbvEventListenerBuilder(String pathToXmlFile, FiniteStateMachine scenarioMachine)
            throws IllegalArgumentException {
        if ((null == pathToXmlFile) || (null == scenarioMachine)) {
            throw new IllegalArgumentException("pathToXmlFile and scenarioMachine must be non-null references");
        }

        LinkedList<CbVideoCallRawData> external = XmlScenarioParser.parseCbVideoXml(pathToXmlFile);

        if (null == external) {
            throw new IllegalArgumentException("invalid data stored in " + pathToXmlFile);
        }

        ScenarioLogger.getInstance().parserLog(Level.FINEST, "BUILDING CBV EVENTS LISTENER");
        this.ware = new CbVideoEventsListener();

        for (CbVideoCallRawData rawCall : external) {
            ScenarioLogger.getInstance().parserLog(Level.FINEST, "parsing \"" + rawCall.getName() + "\" call");

            CbVideoCallback callBack = new CbVideoCallback(rawCall.getIndexesOfAnswerActions().size());

            if (null != rawCall.getStartActions()) {
                ScenarioLogger.getInstance().parserLog(Level.FINEST, "parsing actions on start");

                ArrayList<ScriptAction> onStartActions =
                    FiniteStatesSetBuilder.actionsFromActionList(rawCall.getStartActions(), "unknown", scenarioMachine);

                if ((null != onStartActions) && (0 < onStartActions.size())) {
                    Collections.sort(onStartActions);
                    callBack.addOnStartActions(onStartActions);
                }
            }

            if (null != rawCall.getFinishActions()) {
                ScenarioLogger.getInstance().parserLog(Level.FINEST, "parsing actions on finish");

                ArrayList<ScriptAction> onFinishActions =
                    FiniteStatesSetBuilder.actionsFromActionList(rawCall.getFinishActions(), "unknown",
                        scenarioMachine);

                if ((null != onFinishActions) && (0 < onFinishActions.size())) {
                    Collections.sort(onFinishActions);
                    callBack.addOnFinishActions(onFinishActions);
                }
            }

            Set<Integer> answersActions = rawCall.getIndexesOfAnswerActions();

            for (Integer answerIndex : answersActions) {
                ScenarioLogger.getInstance().parserLog(Level.FINEST, "parsing actions on answer #" + answerIndex);

                List<ScriptAction> actions =
                    FiniteStatesSetBuilder.actionsFromActionList(rawCall.getAnswerActions(answerIndex), "unknown",
                        scenarioMachine);

                Collections.sort(actions);
                callBack.addOnAnswerActions(answerIndex.intValue(), actions);
            }

            this.ware.addEventReaction(rawCall.getName(), callBack);
        }

        ScenarioLogger.getInstance().parserLog(Level.FINEST, "CBV EVENTS LISTENER WAS BUILT");
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public CbVideoEventsListener getWare() {
        return this.ware;
    }
}


//~ Formatted in DD Std on 13/08/28
