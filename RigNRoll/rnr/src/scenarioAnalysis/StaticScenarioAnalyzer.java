/*
 * @(#)StaticScenarioAnalyzer.java   13/08/27
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
import rnr.src.scenarioUtils.Pair;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public final class StaticScenarioAnalyzer {
    private static final String NODE_NAME_PRIFIX = "\tnode name == ";
    private static final int DEFAULT_CAPACITY = 8;
    private final Map<String, GraphVertex> scenarioGraph;

    /**
     * Constructs ...
     *
     *
     * @param scenarioGraphMatrix
     *
     * @throws IllegalArgumentException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public StaticScenarioAnalyzer(AdjacencyMatrix scenarioGraphMatrix) throws IllegalArgumentException {
        if (null == scenarioGraphMatrix) {
            throw new IllegalArgumentException("scenarioGraph must be non-null reference");
        }

        Collection vertexNames = scenarioGraphMatrix.getGraphVertexesNames();

        this.scenarioGraph = new HashMap(vertexNames.size());

        for (String vertexName : vertexNames) {
            this.scenarioGraph.put(vertexName, new GraphVertex(vertexName));
        }

        for (Iterator i$ = this.scenarioGraph.values().iterator(); i$.hasNext(); ) {
            currentVertex = (GraphVertex) i$.next();

            Collection neighbours = scenarioGraphMatrix.getOutputEdges(currentVertex.getName());

            for (String name : neighbours) {
                currentVertex.addNeighbour((GraphVertex) this.scenarioGraph.get(name));
            }
        }

        GraphVertex currentVertex;
    }

    private void setAllVertexesColotToWhite() {
        for (GraphVertex currentVertex : this.scenarioGraph.values()) {
            currentVertex.setColorWhite();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<String> analyzeAccessibility(String[] defaultStartNodes)
            throws IllegalArgumentException, NoSuchElementException {
        GraphVertex startVertex = this.scenarioGraph.get("ScenarioGraphRoot");

        ScenarioAnalysisLogger.getInstance().getLog().finest(
            "Breadth-first search on scenario graph started on ScenarioGraphRoot");

        for (String defaultStartNode : defaultStartNodes) {
            GraphVertex vertexToAddAsNeighbourToTtartVertex = this.scenarioGraph.get(defaultStartNode);

            if (null != vertexToAddAsNeighbourToTtartVertex) {
                startVertex.addNeighbour(vertexToAddAsNeighbourToTtartVertex);
            } else {
                ScenarioAnalysisLogger.getInstance().getLog().warning(defaultStartNode + " not found");
            }
        }

        LinkedList greyVertexQueue = new LinkedList();

        setAllVertexesColotToWhite();

        Map whiteVertexes = new HashMap(this.scenarioGraph);

        whiteVertexes.remove(startVertex.getName());
        startVertex.advanceColor();
        greyVertexQueue.add(startVertex);

        int step = 0;

        while (!(greyVertexQueue.isEmpty())) {
            GraphVertex vertex = (GraphVertex) greyVertexQueue.getFirst();

            ScenarioAnalysisLogger.getInstance().getLog().finest("step #" + step + " on vertex " + vertex.getName());

            for (GraphVertex neighbour : vertex.getAdjacentVertexes()) {
                if (GraphVertex.VertexColor.WHITE == neighbour.getColor()) {
                    ScenarioAnalysisLogger.getInstance().getLog().finest("breadth-first search found "
                            + neighbour.getName());
                    neighbour.advanceColor();
                    greyVertexQueue.addLast(neighbour);
                    whiteVertexes.remove(neighbour.getName());
                }
            }

            vertex.advanceColor();
            greyVertexQueue.removeFirst();
            ++step;
        }

        return new ArrayList(whiteVertexes.keySet());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void depthFirstSearchVisit(GraphVertex toProcess, Collection<List<String>> cyclesStorage,
                                       LinkedList<GraphVertex> searchStack)
            throws IllegalArgumentException {
        if ((null == toProcess) || (null == cyclesStorage) || (null == searchStack)) {
            throw new IllegalArgumentException("all arguments must be non-null references");
        }

        if (GraphVertex.VertexColor.WHITE != toProcess.getColor()) {
            throw new IllegalArgumentException("toProcess must have color white");
        }

        toProcess.advanceColor();
        searchStack.addLast(toProcess);
        ScenarioAnalysisLogger.getInstance().getLog().finest("depth-first search found " + toProcess.getName()
                + "; deep == " + searchStack.size());
        ScenarioAnalysisLogger.getInstance().getLog().finest("PROCESSING CHILDS " + toProcess.getName());

        for (GraphVertex neighbour : toProcess.getAdjacentVertexes()) {
            if (GraphVertex.VertexColor.GREY == neighbour.getColor()) {
                List detectedCycle = new LinkedList();

                for (ListIterator iter = searchStack.listIterator(searchStack.size()); iter.hasPrevious(); ) {
                    GraphVertex stackElement = (GraphVertex) iter.previous();

                    detectedCycle.add(stackElement.getName());

                    if (stackElement == neighbour) {
                        break;
                    }
                }

                if (0 < detectedCycle.size()) {
                    cyclesStorage.add(detectedCycle);
                } else {
                    ScenarioAnalysisLogger.getInstance().getLog().severe(
                        "depthFirstSearchVisit: detectedCycle.size() <= 0");
                }
            } else if (GraphVertex.VertexColor.WHITE == neighbour.getColor()) {
                depthFirstSearchVisit(neighbour, cyclesStorage, searchStack);
            }
        }

        ScenarioAnalysisLogger.getInstance().getLog().finest("PROCESSING CHILDS END: " + toProcess.getName());
        searchStack.removeLast();
        toProcess.advanceColor();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Collection<List<String>> findCycles() throws IllegalArgumentException, NoSuchElementException {
        ScenarioAnalysisLogger.getInstance().getLog().finest(
            "Depth-first search on scenario graph started on ScenarioGraphRoot");

        GraphVertex startVertex = this.scenarioGraph.get("ScenarioGraphRoot");

        setAllVertexesColotToWhite();

        Collection out = new LinkedList();
        LinkedList searchStack = new LinkedList();

        depthFirstSearchVisit(startVertex, out, searchStack);

        return out;
    }

    /**
     * Method description
     *
     *
     * @param defaultStartedQuest
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean validate(String[] defaultStartedQuest) {
        Collection<List<String>> cycles = findCycles();
        String[] nodesNames = new String[defaultStartedQuest.length];

        for (int i = 0; i < nodesNames.length; ++i) {
            nodesNames[i] = defaultStartedQuest[i] + "_phase_" + 1;
        }

        List<String> unreachableNodes = analyzeAccessibility(nodesNames);
        Pair out = new Pair(Boolean.valueOf(true), Boolean.valueOf(true));
        boolean scenarioGraphIsValid = true;

        for (Iterator unreachableNode = unreachableNodes.iterator(); unreachableNode.hasNext(); ) {
            if (!(((String) unreachableNode.next()).contains("_debug_phase_"))) {
                continue;
            }

            unreachableNode.remove();
        }

        if (!(unreachableNodes.isEmpty())) {
            Collections.sort(unreachableNodes);
            ScenarioAnalysisLogger.getInstance().getLog().info("STATIC ANLYSIS FOUND UNREACHABLE STATES");

            for (String stateName : unreachableNodes) {
                ScenarioAnalysisLogger.getInstance().getLog().info("\tnode name == " + stateName);
            }

            out.setFirst(Boolean.valueOf(false));
            scenarioGraphIsValid = false;
        }

        if (!(cycles.isEmpty())) {
            ScenarioAnalysisLogger.getInstance().getLog().info("STATIC ANLYSIS FOUND CYCLES IN SCENARIO");

            boolean wasOutput = false;

            for (List cycle : cycles) {
                if (cycle.isEmpty()) {
                    continue;
                }

                wasOutput = true;
                ScenarioAnalysisLogger.getInstance().getLog().info("\tCYCLE:");

                for (String cycleVertex : cycle) {
                    ScenarioAnalysisLogger.getInstance().getLog().info("\t\tnode name == " + cycleVertex);
                }

                ScenarioAnalysisLogger.getInstance().getLog().info("\t\tnode name == " + ((String) cycle.get(0)));
            }

            scenarioGraphIsValid = false;

            if (!(wasOutput)) {
                ScenarioAnalysisLogger.getInstance().getLog().warning(
                    "static analyser found cycles, but all empty - check java code for bugs");
            }
        }

        return scenarioGraphIsValid;
    }

    @SuppressWarnings("rawtypes")
    private static final class GraphVertex implements Comparable {
        private VertexColor color;
        private final String name;
        private final Set<GraphVertex> adjacentVertexes;

        @SuppressWarnings("unchecked")
        GraphVertex(String name) {
            if (null == name) {
                throw new IllegalArgumentException("name must be non-null reference");
            }

            this.adjacentVertexes = new HashSet(8);
            this.name = name;
            this.color = VertexColor.WHITE;
        }

        static enum VertexColor {
            WHITE, GREY, BLACK;

            /**
             * Method description
             *
             *
             * @return
             */
            public static final VertexColor[] values() {
                return ((VertexColor[]) $VALUES.clone());
            }
        }

        void advanceColor() {
            if (VertexColor.WHITE == this.color) {
                this.color = VertexColor.GREY;
            } else if (VertexColor.GREY == this.color) {
                this.color = VertexColor.BLACK;
            } else {
                throw new IllegalStateException("color could not be advanced");
            }
        }

        void setColorWhite() {
            this.color = VertexColor.WHITE;
        }

        String getName() {
            return this.name;
        }

        VertexColor getColor() {
            return this.color;
        }

        Set<GraphVertex> getAdjacentVertexes() {
            return Collections.unmodifiableSet(this.adjacentVertexes);
        }

        void addNeighbour(GraphVertex neighbour) {
            if (null == neighbour) {
                throw new IllegalArgumentException("neighbour must be non-null reference");
            }

            this.adjacentVertexes.add(neighbour);
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
            if (null == o) {
                throw new IllegalArgumentException("o must be non-null reference");
            }

            return this.name.compareTo(((GraphVertex) o).name);
        }
    }
}


//~ Formatted in DD Std on 13/08/27
