/*
 * @(#)MissionsRequirementsAutoTest.java   13/08/28
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


package rnr.src.rnrscenario.missions.requirements;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import rnr.src.scenarioUtils.Pair;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class MissionsRequirementsAutoTest {
    private static final int MAX_DEEP = 5;
    private static final int MAX_ALTERNATION_LENGTH = 5;
    private static final int MAX_ALTERNATION_COUNT = 20;

    /**
     * Method description
     *
     *
     * @param args
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {
        try {
            ScalarInIntervalRequirement.nativeLibraryExists = false;

            MissionsLog log = MissionsLog.getInstance();

            log.eventHappen("m1", MissionsLog.Event.PLAYER_DECLINED_MISSION);
            log.eventHappen("m2", MissionsLog.Event.PLAYER_INFORMED_ABOUT_MISSION);
            log.eventHappen("m2", MissionsLog.Event.MISSION_ACCEPTED);
            log.eventHappen("m2", MissionsLog.Event.MISSION_COMPLETE);
            log.eventHappen("m3", MissionsLog.Event.PLAYER_INFORMED_ABOUT_MISSION);
            log.eventHappen("m3", MissionsLog.Event.MISSION_ACCEPTED);
            log.eventHappen("m3", MissionsLog.Event.FREIGHT_LOADING_EXPIRED);

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document conditionTree = builder.newDocument();
            Node requirementRoot = conditionTree.createElement("req");
            List statements = new ArrayList();

            createRequirementsOnMissionLog(conditionTree, statements);
            createScalarRequirements(conditionTree, statements, "TimeRequirement");
            createScalarRequirements(conditionTree, statements, "RankRequirement");
            createScalarRequirements(conditionTree, statements, "RatingRequirement");
            createScalarRequirements(conditionTree, statements, "MoneyRequirement");

            Random random = new Random(System.nanoTime());

            for (int level = 0; level < 5; ++level) {
                List topAlternations = new LinkedList();
                int alternationsCount = random.nextInt(20) + 1;

                for (int i = 0; i < alternationsCount; ++i) {
                    boolean alternationValue = false;
                    List alternation = new LinkedList();
                    int statementsInAlternation = random.nextInt(5) + 1;

                    for (int j = 0; j < statementsInAlternation; ++j) {
                        int statementNomber = random.nextInt(statements.size());

                        alternationValue |=
                            ((Boolean) ((Pair) statements.get(statementNomber)).getSecond()).booleanValue();
                        alternation.add(((Node) ((Pair) statements.get(statementNomber)).getFirst()).cloneNode(false));
                    }

                    topAlternations.add(new Pair(alternation, Boolean.valueOf(alternationValue)));
                }

                for (int levelNomber = 0; levelNomber < level; ++levelNomber) {
                    List nextTopAlternations = new LinkedList();

                    while (!(topAlternations.isEmpty())) {
                        int alternationsToSplit = random.nextInt(topAlternations.size()) + 1;
                        List newAlternation = new LinkedList();
                        boolean alternationValue = false;

                        for (int i = 0; i < alternationsToSplit; ++i) {
                            Pair alternation = (Pair) topAlternations.remove(random.nextInt(topAlternations.size()));
                            Pair toConjugateWith = (Pair) statements.get(random.nextInt(statements.size()));
                            Node toAddInNewAlternation = ((Node) toConjugateWith.getFirst()).cloneNode(false);

                            for (Node statement : (List) alternation.getFirst()) {
                                toAddInNewAlternation.appendChild(statement);
                            }

                            alternationValue |= ((((Boolean) toConjugateWith.getSecond()).booleanValue())
                                                 && (((Boolean) alternation.getSecond()).booleanValue()));
                            newAlternation.add(toAddInNewAlternation);
                        }

                        nextTopAlternations.add(new Pair(newAlternation, Boolean.valueOf(alternationValue)));
                    }

                    topAlternations = nextTopAlternations;
                }

                boolean requirementValue = false;
                Node root = requirementRoot.cloneNode(false);

                for (Pair alternation : topAlternations) {
                    requirementValue |= ((Boolean) alternation.getSecond()).booleanValue();

                    for (Node statement : (List) alternation.getFirst()) {
                        root.appendChild(statement);
                    }
                }

                Requirement req = RequirementsFactory.makeOrRequirement(root);

                if (requirementValue == req.check()) {
                    continue;
                }

                throw new Exception("TEST FAILED!");
            }
        } catch (Exception e) {
            System.err.println("TEST FAILED: " + e.getLocalizedMessage());

            return;
        }

        System.out.println("\nTEST SUCCEDED");
    }

    @SuppressWarnings("unchecked")
    private static void createScalarRequirements(Document conditionTree, List<Pair<Node, Boolean>> statements,
            String className) {
        Element requirement = conditionTree.createElement(className);

        requirement.setAttribute("upperBound", "2.0");
        requirement.setAttribute("lowerBound", "3.0");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(false)));
        requirement = conditionTree.createElement(className);
        requirement.setAttribute("upperBound", "1");
        requirement.setAttribute("lowerBound", "0");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(true)));
        requirement = conditionTree.createElement(className);
        requirement.setAttribute("upperBound", "0.3");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(false)));
        requirement = conditionTree.createElement(className);
        requirement.setAttribute("lowerBound", "0.6");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(false)));
    }

    @SuppressWarnings("unchecked")
    private static void createRequirementsOnMissionLog(Document conditionTree, List<Pair<Node, Boolean>> statements) {
        assert(null != conditionTree) : "conditionTree must be non-null reference";
        assert(null != statements) : "statements must be non-null reference";

        Element requirement = conditionTree.createElement("MissionAcceptedRequirement");

        requirement.setAttribute("mission", "m1");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(false)));
        requirement = conditionTree.createElement("MissionAcceptedRequirement");
        requirement.setAttribute("mission", "m2");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(true)));
        requirement = conditionTree.createElement("MissionCompleteRequirement");
        requirement.setAttribute("mission", "m2");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(true)));
        requirement = conditionTree.createElement("MissionCompleteRequirement");
        requirement.setAttribute("mission", "m3");
        statements.add(new Pair(requirement, Boolean.valueOf(false)));
        requirement = conditionTree.createElement("MissionDeclinedRequirement");
        requirement.setAttribute("mission", "m1");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(true)));
        requirement = conditionTree.createElement("MissionDeclinedRequirement");
        requirement.setAttribute("mission", "m2");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(false)));
        requirement = conditionTree.createElement("MissionFailureRequirement");
        requirement.setAttribute("mission", "m1");
        requirement.setAttribute("reason", "any");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(false)));
        requirement = conditionTree.createElement("MissionFailureRequirement");
        requirement.setAttribute("mission", "m3");
        requirement.setAttribute("reason", "FREIGHT_LOADING_EXPIRED");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(true)));
        requirement = conditionTree.createElement("MissionFinishedRequirement");
        requirement.setAttribute("mission", "m1");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(false)));
        requirement = conditionTree.createElement("MissionFinishedRequirement");
        requirement.setAttribute("mission", "m2");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(true)));
        requirement = conditionTree.createElement("MissionFinishedRequirement");
        requirement.setAttribute("mission", "m3");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(true)));
        requirement = conditionTree.createElement("PlayerInformedAboutMissionRequirement");
        requirement.setAttribute("mission", "m1");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(false)));
        requirement = conditionTree.createElement("PlayerInformedAboutMissionRequirement");
        requirement.setAttribute("mission", "m2");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(true)));
        requirement = conditionTree.createElement("PlayerInformedAboutMissionRequirement");
        requirement.setAttribute("mission", "m3");
        statements.add(new Pair<Node, Boolean>(requirement, Boolean.valueOf(true)));
    }
}


//~ Formatted in DD Std on 13/08/28
