/*
 * @(#)DynamicScenarioAnalyzer.java   13/08/27
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


package rnr.src.scenarioAnalysis;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrloggers.ScenarioAnalysisLogger;
import rnr.src.scenarioMachine.FiniteStateMachine;
import rnr.src.scenarioUtils.Pair;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public final class DynamicScenarioAnalyzer {
    private static final int MAX_EMULATION_ITERATIONS_COUNT = 2048;
    private static final String INFO_RECORD_PREFIX = "\t++++ ";
    private static final String ARROW = " --> ";
    private FiniteStateMachine scenarioAutomat = null;
    private final boolean chooseMade = false;
    private final int choose = 0;
    private final Object mutex = new Object();
    private final boolean warningClosed = false;

    DynamicScenarioAnalyzer(FiniteStateMachine machine) {
        if (null == machine) {
            throw new IllegalArgumentException("machine must be non-null reference");
        }

        this.scenarioAutomat = machine;
    }

    private void printSimulationResults(ScriptEvent[] eventTuple) {
        try {
            Collection<String> activeStates = this.scenarioAutomat.getCurrentActiveStates();
            List<String> statedStates = this.scenarioAutomat.getRecentlyStarted();
            List<String> closedStates = this.scenarioAutomat.getRecentlyClosed();
            List<Pair<String, String>> stepsMade = this.scenarioAutomat.getRecentlySteps();

            if ((statedStates.isEmpty()) && (closedStates.isEmpty()) && (stepsMade.isEmpty())) {
                return;
            }

            ScenarioAnalysisLogger.getInstance().getLog().info("simulated tuple:");

            for (ScriptEvent event : eventTuple) {
                ScenarioAnalysisLogger.getInstance().getLog().finest('\t' + event.toString());
            }

            ScenarioAnalysisLogger.getInstance().getLog().info("simulation result:");
            ScenarioAnalysisLogger.getInstance().getLog().info("\tactive:");

            for (String infoStr : activeStates) {
                ScenarioAnalysisLogger.getInstance().getLog().info("\t++++ " + infoStr);
            }

            ScenarioAnalysisLogger.getInstance().getLog().info("\tstarted:");

            for (String infoStr : statedStates) {
                ScenarioAnalysisLogger.getInstance().getLog().info("\t++++ " + infoStr);
            }

            ScenarioAnalysisLogger.getInstance().getLog().info("\tclosed:");

            for (String infoStr : closedStates) {
                ScenarioAnalysisLogger.getInstance().getLog().info("\t++++ " + infoStr);
            }

            ScenarioAnalysisLogger.getInstance().getLog().info("\tsteps made:");

            for (Pair infoPair : stepsMade) {
                ScenarioAnalysisLogger.getInstance().getLog().info("\t++++ " + infoPair.getFirst() + " --> "
                        + infoPair.getSecond());
            }
        } catch (UnsupportedOperationException exception) {
            ScenarioAnalysisLogger.getInstance().getLog().severe("fsm was created without recordChanges flag!");
        }
    }

    private void emulateEventTuple(ScriptEvent[] eventTuple) {
        EventsController.getInstance().eventHappen(eventTuple);
        printSimulationResults(eventTuple);
        this.scenarioAutomat.clearLog();
    }

    /**
     * Method description
     *
     *
     * @param defaultStartedQuests
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void randomSimulation(String[] defaultStartedQuests) {
        ScenarioAnalysisLogger.getInstance().getLog().finest("RANDOM SIMULATION STARTED:\n");
        ScenarioAnalysisLogger.getInstance().getLog().finest("\nACTIVATING DEFAULT QUESTS:\n");

        for (String questToActivate : defaultStartedQuests) {
            this.scenarioAutomat.activateState(true, new String[] { questToActivate + "_phase_" + 1 });
        }

        printSimulationResults(new ScriptEvent[0]);
        this.scenarioAutomat.clearLog();
        ScenarioAnalysisLogger.getInstance().getLog().finest("\nGENERATING RANDOM EVENTS:\n");

        Random randomGenerator = new Random(System.nanoTime());
        int iterationNom = 0;
        ScriptEvent[] emptyArrayTuple = new ScriptEvent[0];

        while ((!(this.scenarioAutomat.getCurrentActiveStates().isEmpty())) && (2048 > iterationNom)) {
            List<List<ScriptEvent>> expectedEvents = this.scenarioAutomat.getExpectedEvents();

            if ((null == expectedEvents) || (expectedEvents.isEmpty())) {
                break;
            }

            List eventTuple = expectedEvents.get(randomGenerator.nextInt(expectedEvents.size()));

            emulateEventTuple((ScriptEvent[]) eventTuple.toArray(emptyArrayTuple));
            ++iterationNom;
        }

        ScenarioAnalysisLogger.getInstance().getLog().finest("\nACTIVE SATES:\n");

        Collection activeStates = this.scenarioAutomat.getCurrentActiveStates();

        for (String infoStr : activeStates) {
            ScenarioAnalysisLogger.getInstance().getLog().info("\t++++ " + infoStr);
        }

        ScenarioAnalysisLogger.getInstance().getLog().finest("\nRANDOM SIMULATION CLOSED");
    }

    /**
     * Method description
     *
     *
     * @param ui
     * @param defaultStartedQuests
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void uiSimulation(AnalysisUI ui, String[] defaultStartedQuests) {
        ui.show();
        ScenarioAnalysisLogger.getInstance().getLog().finest("RANDOM SIMULATION STARTED:\n");
        ScenarioAnalysisLogger.getInstance().getLog().finest("\nACTIVATING DEFAULT QUESTS:\n");

        for (String questToActivate : defaultStartedQuests) {
            this.scenarioAutomat.activateState(true, new String[] { questToActivate + "_phase_" + 1 });
        }

        printSimulationResults(new ScriptEvent[0]);
        this.scenarioAutomat.clearLog();
        ScenarioAnalysisLogger.getInstance().getLog().finest("\nGENERATING RANDOM EVENTS:\n");

        int iterationNom = 0;
        ScriptEvent[] emptyArrayTuple = new ScriptEvent[0];

        while ((!(this.scenarioAutomat.getCurrentActiveStates().isEmpty())) && (2048 > iterationNom)) {
            List<List<ScriptEvent>> expectedEvents = this.scenarioAutomat.getExpectedEvents();

            if ((null == expectedEvents) || (expectedEvents.isEmpty())) {
                break;
            }

            String[] stringTupleEvents = convertForUi(expectedEvents);
            boolean check_results = true;
            int choose_made = 0;

            try {
                ui.recieve(stringTupleEvents, new UiListener());

                while (check_results) {
                    synchronized (this.mutex) {
                        check_results = !(this.chooseMade);
                        choose_made = this.choose;
                    }
                }
            } catch (AnalysisUI.ExceptionBadData e) {
                boolean bool1;
                boolean bool1 = false;

                e.showEvent(new WarnListener());

                while (!(bool1)) {
                    synchronized (this.mutex) {
                        bool1 = this.warningClosed;
                    }
                }
            }

            List eventTuple = expectedEvents.get(choose_made);

            emulateEventTuple((ScriptEvent[]) eventTuple.toArray(emptyArrayTuple));
            ++iterationNom;
        }

        ScenarioAnalysisLogger.getInstance().getLog().finest("\nACTIVE SATES:\n");

        Collection activeStates = this.scenarioAutomat.getCurrentActiveStates();

        for (String infoStr : activeStates) {
            ScenarioAnalysisLogger.getInstance().getLog().info("\t++++ " + infoStr);
        }

        ScenarioAnalysisLogger.getInstance().getLog().finest("\nRANDOM SIMULATION CLOSED");
        ui.close();
    }

    private String[] convertForUi(List<List<ScriptEvent>> eventsTuple) {
        String[] res = new String[eventsTuple.size()];

        for (int i = 0; i < eventsTuple.size(); ++i) {
            String events_string = "";

            for (ScriptEvent event : (List) eventsTuple.get(i)) {
                events_string = event.toString() + "\n";
            }

            res[i] = events_string;
        }

        return res;
    }

    class UiListener implements AnalysisUI.IUpdateChoose {
        UiListener() {
            synchronized (DynamicScenarioAnalyzer.this.mutex) {
                DynamicScenarioAnalyzer.access$102(DynamicScenarioAnalyzer.this, false);
                DynamicScenarioAnalyzer.access$202(DynamicScenarioAnalyzer.this, 0);
            }
        }

        /**
         * Method description
         *
         *
         * @param value
         */
        @Override
        public void choose(int value) {
            synchronized (DynamicScenarioAnalyzer.this.mutex) {
                DynamicScenarioAnalyzer.access$102(DynamicScenarioAnalyzer.this, true);
                DynamicScenarioAnalyzer.access$202(DynamicScenarioAnalyzer.this, value);
            }
        }
    }


    class WarnListener implements AnalysisUI.WarnMessageClosed {
        WarnListener() {
            synchronized (DynamicScenarioAnalyzer.this.mutex) {
                DynamicScenarioAnalyzer.access$302(DynamicScenarioAnalyzer.this, false);
            }
        }

        /**
         * Method description
         *
         */
        @Override
        public void closed() {
            synchronized (DynamicScenarioAnalyzer.this.mutex) {
                DynamicScenarioAnalyzer.access$302(DynamicScenarioAnalyzer.this, true);
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/27
