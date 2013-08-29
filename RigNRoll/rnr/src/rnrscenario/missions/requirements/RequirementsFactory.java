/*
 * @(#)RequirementsFactory.java   13/08/28
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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrscenario.missions.PriorityTable;
import rnr.src.scenarioUtils.Pair;
import rnr.src.scenarioXml.XmlFilter;

//~--- JDK imports ------------------------------------------------------------

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.LinkedList;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class RequirementsFactory {
    private static final String MISSION_NAME_ATTRIBUTE = "mission";
    private static final String FLAG_NAME_ATTRIBUTE = "name";
    private static final String UPPER_BOUND_ATTRIBUTE = "upperBound";
    private static final String LOWER_BOUND_ATTRIBUTE = "lowerBound";
    private static final String REASON_ATTRIBUTE = "reason";
    private static final String ANY_ATTRIBUTE = "any";
    private static PriorityTable priorityTable;

    static {
        priorityTable = null;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        priorityTable = null;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setPriorityTable(PriorityTable value) {
        priorityTable = value;
    }

    private static Requirement constructFromOneString(Constructor<?> creator, Node argumentNode)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        assert(null != creator) : "creator must be non-null reference";

        if (XmlFilter.textContentExists(argumentNode)) {
            return ((Requirement) creator.newInstance(new Object[] { argumentNode.getTextContent() }));
        }

        throw new InstantiationException("no argument to constructor found in node");
    }

    private static Requirement constructFromtTwoStrings(Constructor<?> creator, Node missionNameNode, Node reasonNode)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        assert(null != creator) : "creator must be non-null reference";

        if ((XmlFilter.textContentExists(missionNameNode)) && (XmlFilter.textContentExists(reasonNode))) {
            String reason = reasonNode.getTextContent();

            if (0 == "any".compareToIgnoreCase(reason)) {
                return ((Requirement) creator.newInstance(new Object[] { missionNameNode.getTextContent(), null }));
            }

            for (MissionsLog.Event reasonType : MissionsLog.Event.values()) {
                if (0 == reasonType.name().compareTo(reason)) {
                    return ((Requirement) creator.newInstance(new Object[] { missionNameNode.getTextContent(),
                            reason }));
                }
            }

            throw new InstantiationException("invalid reason: " + reason);
        }

        throw new InstantiationException("no argument to constructor found in node");
    }

    private static Requirement constructFromtTwoDoubles(Constructor<?> creator, Node lowerBoundNode,
            Node upperBoundNode)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        assert(null != creator) : "creator must be non-null reference";

        try {
            if ((!(XmlFilter.textContentExists(upperBoundNode))) && (!(XmlFilter.textContentExists(lowerBoundNode)))) {
                MissionsLogger.getInstance().doLog("found requirement node withoud bounds: set upper and lower to 0",
                                                   Level.WARNING);

                return ((Requirement) creator.newInstance(new Object[] { Integer.valueOf(0), Integer.valueOf(0) }));
            }

            double firstArgument = (-1.0D / 0.0D);
            double secondArgument = (1.0D / 0.0D);

            if (XmlFilter.textContentExists(lowerBoundNode)) {
                firstArgument = Double.parseDouble(lowerBoundNode.getTextContent());
            }

            if (XmlFilter.textContentExists(upperBoundNode)) {
                secondArgument = Double.parseDouble(upperBoundNode.getTextContent());
            }

            return ((Requirement) creator.newInstance(new Object[] { Double.valueOf(firstArgument),
                    Double.valueOf(secondArgument) }));
        } catch (NumberFormatException e) {
            throw new InstantiationException("invalid value: " + e.getLocalizedMessage());
        }
    }

    private static Requirement makeWare(Node xmlNode)
            throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        assert(null != xmlNode) : "xmlNode must be non-null reference";

        Class<?> requirementClass = Class.forName("rnrscenario.missions.requirements." + xmlNode.getNodeName());

        for (Constructor<?> constructor : requirementClass.getDeclaredConstructors()) {
            switch (constructor.getParameterTypes().length) {
             case 0 :
                 return ((Requirement) constructor.newInstance(new Object[0]));

             case 1 :
                 if (constructor.getParameterTypes()[0] == String.class) {
                     Node nd = xmlNode.getAttributes().getNamedItem("mission");

                     if (nd == null) {
                         nd = xmlNode.getAttributes().getNamedItem("name");
                     }

                     return constructFromOneString(constructor, nd);
                 }

                 throw new InstantiationException("support 1-argument constructors only for String type");

             case 2 :
                 Class<?> firstArgumentClass = constructor.getParameterTypes()[0];
                 Class<?> secondArgumentClass = constructor.getParameterTypes()[1];

                 if ((firstArgumentClass == Double.TYPE) && (secondArgumentClass == Double.TYPE)) {
                     Node upperBoundNode = xmlNode.getAttributes().getNamedItem("upperBound");
                     Node lowerBoundNode = xmlNode.getAttributes().getNamedItem("lowerBound");

                     return constructFromtTwoDoubles(constructor, lowerBoundNode, upperBoundNode);
                 }

                 if ((firstArgumentClass != String.class) || (secondArgumentClass != String.class)) {
                     continue;
                 }

                 Node missionNameNode = xmlNode.getAttributes().getNamedItem("mission");
                 Node reasonNode = xmlNode.getAttributes().getNamedItem("reason");

                 return constructFromtTwoStrings(constructor, missionNameNode, reasonNode);
            }
        }

        throw new InstantiationException("no supported constructors found");
    }

    /**
     * Method description
     *
     *
     * @param root
     *
     * @return
     *
     * @throws RequirementsCreationException
     */
    public static Requirement makeOrRequirement(Node root) throws RequirementsCreationException {
        assert(null != root) : "root must be non-null reference";

        try {
            if (0 >= root.getChildNodes().getLength()) {
                throw new IllegalArgumentException("root must have at least 1 child nodes");
            }

            NodeList orList = root.getChildNodes();
            LinkedList<Pair<OrRequirement, NodeList>> requirementsStack = new LinkedList<Pair<OrRequirement,
                                                                              NodeList>>();
            OrRequirement result = new OrRequirement(priorityTable);

            requirementsStack.add(new Pair<OrRequirement, NodeList>(result, orList));

            while (!(requirementsStack.isEmpty())) {
                Pair<?, ?> alternation = requirementsStack.removeFirst();
                XmlFilter visitor = new XmlFilter((NodeList) alternation.getSecond());
                Node conditionNode = visitor.nextElement();

                while (null != conditionNode) {
                    Requirement ware = makeWare(conditionNode);

                    ware.setPriorityTable(priorityTable);

                    if (0 != conditionNode.getChildNodes().getLength()) {
                        AndRequirement and = new AndRequirement(priorityTable);
                        OrRequirement or = new OrRequirement(priorityTable);

                        and.addRequirement(ware);
                        and.addRequirement(or);
                        requirementsStack.add(new Pair<OrRequirement, NodeList>(or, conditionNode.getChildNodes()));
                        ((RequirementList) alternation.getFirst()).addRequirement(and);
                    } else {
                        ((RequirementList) alternation.getFirst()).addRequirement(ware);
                    }

                    conditionNode = visitor.nextElement();
                }
            }

            return result;
        } catch (IllegalAccessException e) {
            throw new RequirementsCreationException(e);
        } catch (InvocationTargetException e) {
            throw new RequirementsCreationException(e);
        } catch (InstantiationException e) {
            throw new RequirementsCreationException(e);
        } catch (ClassNotFoundException e) {
            throw new RequirementsCreationException(e);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
