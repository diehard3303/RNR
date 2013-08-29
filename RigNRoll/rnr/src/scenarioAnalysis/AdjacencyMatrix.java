/*
 * @(#)AdjacencyMatrix.java   13/08/27
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
import rnr.src.scenarioMachine.FiniteState;
import rnr.src.scenarioMachine.FiniteStatesSet;
import rnr.src.scriptActions.ScenarioAnalysisMarkAction;
import rnr.src.scriptActions.SingleStepScenarioAdvanceAction;
import rnr.src.scriptActions.StartScenarioBranchAction;
import rnr.src.scriptActions.StopScenarioBranchAction;
import rnr.src.scriptActions.TimeAction;

//~--- JDK imports ------------------------------------------------------------

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

@SuppressWarnings({ "rawtypes", "unchecked" })
final class AdjacencyMatrix implements Cloneable {
    private static final int DEFAULT_STRING_BUILDER_SIZE = 256;
    private static final int DEFAULT_CAPACITY_SMALL = 8;
    private static final int DEFAULT_CAPACITY_BIG = 64;
    private static Map<String, TransitionAction> classesInterpretedAsTransitionAction = null;
    private static final int EDGE_EXISTS = 1;
    private static final int EDGE_ABSENCES = 0;
    private static final int GRAPH_ROOT_INDEX = 0;
    static final String GRAPH_ROOT_NAME = "ScenarioGraphRoot";

    static {
        try {
            classesInterpretedAsTransitionAction = new HashMap(8);

            String className = StartScenarioBranchAction.class.getName();

            classesInterpretedAsTransitionAction.put(className, new TransitionAction(className, "getBranchName"));
            className = SingleStepScenarioAdvanceAction.class.getName();
            classesInterpretedAsTransitionAction.put(className, new TransitionAction(className, "getDestination"));
            className = StopScenarioBranchAction.class.getName();
            classesInterpretedAsTransitionAction.put(className, new TransitionAction(className, "getDestination"));
            className = ScenarioAnalysisMarkAction.class.getName();
            classesInterpretedAsTransitionAction.put(className, new TransitionAction(className, "getDestination"));
            className = TimeAction.class.getName();
            classesInterpretedAsTransitionAction.put(className,
                    new TransitionActionOfAction(className, "getChildAction"));
        } catch (ClassNotFoundException e) {
            ScenarioAnalysisLogger.getInstance().getLog().severe(e.getMessage());
        } catch (NoSuchMethodException e) {
            ScenarioAnalysisLogger.getInstance().getLog().severe(e.getMessage());
        }
    }

    private int[][] edges = null;
    private List<String> vertexesNames = null;
    private Map<String, Integer> vertexIndexesResolve = null;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    AdjacencyMatrix(FiniteStatesSet scenarioStates, List externalActions) {
        Map statesMap = scenarioStates.getStates();
        Set states = statesMap.entrySet();

        this.edges = new int[states.size() + 1][states.size() + 1];

        for (int i = 0; i < states.size() + 1; ++i) {
            Arrays.fill(this.edges[i], 0);
        }

        this.vertexIndexesResolve = new HashMap(64);
        this.vertexesNames = new ArrayList(64);

        String nodeName = "ScenarioGraphRoot";

        this.vertexIndexesResolve.put(nodeName, Integer.valueOf(0));
        this.vertexesNames.add(nodeName);

        int vertexIndex = 1;

        for (Map.Entry entry : states) {
            nodeName = (String) entry.getKey();
            this.vertexIndexesResolve.put(nodeName, Integer.valueOf(vertexIndex));
            this.vertexesNames.add(nodeName);
            ++vertexIndex;
        }

        Iterator i$;

        if ((null != externalActions) && (!(externalActions.isEmpty()))) {
            for (i$ = externalActions.iterator(); i$.hasNext(); ) {
                Object externalAction = i$.next();

                if (null != externalAction) {
                    tryAddEdge(externalAction, 0, "unknown");
                }
            }
        }

        for (Map.Entry entry : states) {
            FiniteState currentNode = (FiniteState) entry.getValue();
            int index = this.vertexIndexesResolve.get(currentNode.getName()).intValue();
            List actions = currentNode.getAllAvalibleActions();

            if (null == actions) {
                continue;
            }

            for (i$ = actions.iterator(); i$.hasNext(); ) {
                Object internalAction = i$.next();

                if (null != internalAction) {
                    tryAddEdge(internalAction, index, currentNode.getName());
                }
            }
        }

        FiniteState currentNode;
        int index;
    }

    @SuppressWarnings({ "unused", "unchecked", "rawtypes" })
    private AdjacencyMatrix(List<String> vertexNamesResolveTable, Map<String, Integer> vertexIndexesResolveTable,
                            int[][] edgesMatrix) {
        if ((null == vertexIndexesResolveTable) || (null == edgesMatrix) || (0 >= edgesMatrix.length)) {
            throw new IllegalArgumentException(
                "vertexIndexesResolveTable and edgesMatrix must be non-null references and edgesMatrix must have at least one row");
        }

        this.edges = new int[edgesMatrix.length][edgesMatrix[0].length];

        for (int i = 0; i < edgesMatrix.length; ++i) {
            System.arraycopy(edgesMatrix[i], 0, this.edges[i], 0, edgesMatrix[i].length);
        }

        this.vertexIndexesResolve = new HashMap(vertexIndexesResolveTable);
        this.vertexesNames = new ArrayList(vertexNamesResolveTable);
    }

    private static TransitionAction findTransitionAction(Object action) {
        String name = action.getClass().getName();

        return (classesInterpretedAsTransitionAction.get(name));
    }

    private void tryAddEdge(Object actionObjectToInterpret, int sourceIndex, String souceName) {
        if (null == actionObjectToInterpret) {
            throw new IllegalArgumentException("actionObjectToInterpret must be non-null reference");
        }

        if ((0 > sourceIndex) || (sourceIndex >= this.edges.length)) {
            throw new IllegalArgumentException("sourceIndex must be non-negative and less than edges.length");
        }

        if (null == souceName) {
            throw new IllegalArgumentException("souceName must be non-null reference");
        }

        TransitionAction pointerContainerToFieldWithDestinationNodeName = findTransitionAction(actionObjectToInterpret);

        if (null == pointerContainerToFieldWithDestinationNodeName) {
            return;
        }

        String destinationNodeName =
            pointerContainerToFieldWithDestinationNodeName.getTransitionDestination(actionObjectToInterpret);

        if (pointerContainerToFieldWithDestinationNodeName.hasDestination()) {
            if ((null == destinationNodeName) || (0 >= destinationNodeName.length())) {
                ScenarioAnalysisLogger.getInstance().getLog().warning("can't find destination node: source == "
                        + souceName + "; action == " + actionObjectToInterpret.toString());
            }

            Integer destIndex = this.vertexIndexesResolve.get(destinationNodeName);

            if (null == destIndex) {
                destIndex = this.vertexIndexesResolve.get(destinationNodeName + "_phase_" + 1);
            }

            if (null == destIndex) {
                StringBuilder builder = new StringBuilder(256);

                builder.append("can't find index of destination node: source == ");
                builder.append(souceName);
                builder.append(" destination == ");
                builder.append(destinationNodeName);
                builder.append(" data source == ");
                builder.append(actionObjectToInterpret.toString());
                ScenarioAnalysisLogger.getInstance().getLog().warning(builder.toString());

                return;
            }

            if (0 == this.edges[sourceIndex][destIndex.intValue()]) {
                this.edges[sourceIndex][destIndex.intValue()] = 1;
            } else {
                ScenarioAnalysisLogger.getInstance().getLog().warning("multiedge found in scenario graph: source == "
                        + souceName + "; destination == " + destinationNodeName);
            }
        }
    }

    boolean isEdgeExist(int from, int to) {
        if ((0 > from) || (0 > from) || (from >= this.edges.length) || (to >= this.edges[from].length)) {
            throw new IllegalArgumentException("indexes are out of bounds");
        }

        return (0 != this.edges[from][to]);
    }

    boolean isEdgeExist(String from, String to) {
        Integer fromIndex = this.vertexIndexesResolve.get(from);

        if (null == fromIndex) {
            throw new IllegalArgumentException("vertex with name " + from + "doesn't exist");
        }

        Integer toIndex = this.vertexIndexesResolve.get(to);

        if (null == toIndex) {
            throw new IllegalArgumentException("vertex with name " + to + "doesn't exist");
        }

        return (0 != this.edges[fromIndex.intValue()][toIndex.intValue()]);
    }

    Collection<String> getGraphVertexesNames() {
        return Collections.unmodifiableSet(this.vertexIndexesResolve.keySet());
    }

    @SuppressWarnings("unchecked")
    Collection<String> getOutputEdges(String from) {
        Integer fromIndex = this.vertexIndexesResolve.get(from);

        if (null == fromIndex) {
            throw new IllegalArgumentException("vertex with name " + from + "doesn't exist");
        }

        @SuppressWarnings("rawtypes") List out = new LinkedList();

        for (int i = 0; i < this.edges[fromIndex.intValue()].length; ++i) {
            if (1 != this.edges[fromIndex.intValue()][i]) {
                continue;
            }

            out.add(this.vertexesNames.get(i));
        }

        return out;
    }

    private static class TransitionAction {
        protected Method nameOfNextNodeGetter = null;

        TransitionAction(String className, String getterName) throws ClassNotFoundException, NoSuchMethodException {
            Class instanceClass = Class.forName(className);

            while (Object.class != instanceClass) {
                try {
                    this.nameOfNextNodeGetter = instanceClass.getDeclaredMethod(getterName, new Class[0]);
                    this.nameOfNextNodeGetter.setAccessible(true);

                    return;
                } catch (NoSuchMethodException e) {
                    instanceClass = instanceClass.getSuperclass();
                }
            }

            throw new NoSuchMethodException("method " + getterName + " wasn't found in " + className
                                            + " and all it's superclasses");
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean hasDestination() {
            return true;
        }

        String getTransitionDestination(Object action) {
            try {
                return ((String) this.nameOfNextNodeGetter.invoke(action, new Object[0]));
            } catch (IllegalAccessException exception) {
                ScenarioAnalysisLogger.getInstance().getLog().warning(
                    "illegal access: can't extract transition action from " + action.toString());

                return null;
            } catch (ClassCastException exception) {
                ScenarioAnalysisLogger.getInstance().getLog().warning(
                    "illegal cast to String: can't extract transition action from " + action.toString());

                return null;
            } catch (InvocationTargetException e) {
                ScenarioAnalysisLogger.getInstance().getLog().warning(
                    "invocation exception: can't getter call failed on object " + action.toString());
            }

            return null;
        }
    }


    private static final class TransitionActionOfAction extends AdjacencyMatrix.TransitionAction {
        private boolean m_hasDestination = true;

        TransitionActionOfAction(String className, String getterName)
                throws ClassNotFoundException, NoSuchMethodException {
            super(className, getterName);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public boolean hasDestination() {
            return this.m_hasDestination;
        }

        @Override
        String getTransitionDestination(Object action) {
            try {
                Object nextaction = this.nameOfNextNodeGetter.invoke(action, new Object[0]);

                if (null != nextaction) {
                    AdjacencyMatrix.TransitionAction next_transition = AdjacencyMatrix.access$000(nextaction);

                    if (next_transition != null) {
                        this.m_hasDestination = next_transition.hasDestination();

                        return next_transition.getTransitionDestination(nextaction);
                    }
                }

                this.m_hasDestination = false;

                return null;
            } catch (IllegalAccessException exception) {
                ScenarioAnalysisLogger.getInstance().getLog().warning(
                    "illegal access: can't extract transition action from " + action.toString());

                return null;
            } catch (ClassCastException exception) {
                ScenarioAnalysisLogger.getInstance().getLog().warning(
                    "illegal cast to String: can't extract transition action from " + action.toString());

                return null;
            } catch (InvocationTargetException e) {
                ScenarioAnalysisLogger.getInstance().getLog().warning(
                    "invocation exception: can't getter call failed on object " + action.toString());
            }

            return null;
        }
    }
}


//~ Formatted in DD Std on 13/08/27
