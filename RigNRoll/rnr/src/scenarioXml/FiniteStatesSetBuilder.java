/*
 * @(#)FiniteStatesSetBuilder.java   13/08/28
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

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrloggers.ScenarioLogger;
import rnr.src.rnrorg.Organizers;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.scenarioMachine.FiniteState;
import rnr.src.scenarioMachine.FiniteStateMachine;
import rnr.src.scenarioMachine.FiniteStatesSet;
import rnr.src.scenarioMachine.FsmActionAdapter;
import rnr.src.scenarioUtils.AdvancedClass;
import rnr.src.scriptActions.MissionPointMarkAction;
import rnr.src.scriptActions.MissionPointUnmarkAction;
import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptActions.SingleStepScenarioAdvanceAction;
import rnr.src.scriptActions.StartOrgAction;
import rnr.src.scriptActions.StartScenarioBranchAction;
import rnr.src.scriptActions.StopScenarioBranchAction;
import rnr.src.scriptEvents.AndEventChecker;
import rnr.src.scriptEvents.ComplexEventReaction;
import rnr.src.scriptEvents.EventChecker;
import rnr.src.scriptEvents.EventReaction;
import rnr.src.scriptEvents.ExactEventChecker;
import rnr.src.scriptEvents.IsoQuestEmulationStateProcessor;
import rnr.src.scriptEvents.NativeMessageListener;
import rnr.src.scriptEvents.OrEventChecker;
import rnr.src.scriptEvents.PhasedQuestEmulationStateProcessor;
import rnr.src.scriptEvents.ScenarioStateMoveEventChecker;
import rnr.src.scriptEvents.ScriptEvent;
import rnr.src.scriptEvents.SimpleEventReaction;
import rnr.src.scriptEvents.SpecialObjectEvent;
import rnr.src.scriptEvents.SpecialObjectEvent.EventType;
import rnr.src.scriptEvents.SpecialObjectEventTypeChecker;
import rnr.src.scriptEvents.UniversalStateProcessor;
import rnr.src.scriptEvents.VoidEvent;
import rnr.src.scriptEvents.VoidEventChecker;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Pattern;

//TODO: Auto-generated Javadoc

/**
 * The Class FiniteStatesSetBuilder.
 */
public final class FiniteStatesSetBuilder {

    /** The Constant SCENARIO_STATE_NAME_SUFFIX. */
    public static final String SCENARIO_STATE_NAME_SUFFIX = "_phase_";

    /** The Constant SCENARIO_VIRTUAL_STATE_SUFFIX. */
    public static final String SCENARIO_VIRTUAL_STATE_SUFFIX = "_virtual_";

    /** The states. */
    private final LinkedList<FiniteState> states;

    /**
     *     Instantiates a new finite states set builder.
     */
    @SuppressWarnings("unchecked")
    public FiniteStatesSetBuilder() {
        this.states = new LinkedList<FiniteState>();
    }

    /**
     *     Creates the simple advance scheme.
     *    
     *     @param nextStateNames
     *                the next state names
     *     @param stateProcessor
     *                the state processor
     */
    private static void createSimpleAdvanceScheme(List<String> nextStateNames, UniversalStateProcessor stateProcessor) {
        if ((null == nextStateNames) || (null == stateProcessor)) {
            throw new IllegalArgumentException("nextStateNames and stateProcessor must be non-null references");
        }

        for (String avalibleState : nextStateNames) {
            SimpleEventReaction reactor = new SimpleEventReaction(null,
                                              new ScenarioStateMoveEventChecker(avalibleState), 0);

            stateProcessor.addReaction(reactor);
        }
    }

    /**
     *     Action from string params.
     *    
     *     @param name
     *                the name
     *     @param params
     *                the params
     *     @param substituteInsteadThis
     *                the substitute instead this
     *     @param paramToConstructors
     *                the param to constructors
     *     @return the script action
     *     @throws ActionConstructionException
     *                 the action construction exception
     */
    @SuppressWarnings("rawtypes")
    static ScriptAction actionFromStringParams(String name, Properties params, String substituteInsteadThis,
            FiniteStateMachine paramToConstructors)
            throws FiniteStatesSetBuilder.ActionConstructionException {
        if ((null == name) || (null == params) || (null == substituteInsteadThis)) {
            throw new IllegalArgumentException("actionFromStringParams: all parameters must be non-null references");
        }

        try {
            AdvancedClass scriptActionClass = new AdvancedClass(name, new String[] { "scriptActions" });
            Constructor[] constructorsArray = scriptActionClass.getAllConstructors();
            Object constructed = null;

            for (Constructor creator : constructorsArray) {
                creator.setAccessible(true);

                switch (creator.getParameterTypes().length) {
                 case 0 :
                     constructed = creator.newInstance(new Object[0]);

                     break;

                 case 1 :
                     Class constructorParameterType = creator.getParameterTypes()[0];

                     if (scenarioscript.class == constructorParameterType) {
                         constructed = creator.newInstance(new Object[] { scenarioscript.script });

                         break;
                     }

                     if (FiniteStateMachine.class != constructorParameterType) {
                         continue;
                     }

                     constructed = creator.newInstance(new Object[] { paramToConstructors });

                     break;
                }
            }

            if (null == constructed) {
                throw new FiniteStatesSetBuilder.ActionConstructionException(
                    "failed to create action. couldn't find valid constructor with no or one String parameter");
            }

            Set entries = params.entrySet();

            for (Iterator i$ = entries.iterator(); i$.hasNext(); ) {
                Object newVar = i$.next();
                Map.Entry entry = (Map.Entry) newVar;
                String fieldName = (String) entry.getKey();
                String fieldValue = (String) entry.getValue();
                Field field = scriptActionClass.findFieldInHierarchy(fieldName);

                try {
                    field.setAccessible(true);

                    if (Integer.TYPE == field.getType()) {
                        try {
                            field.setInt(constructed, Integer.parseInt(fieldValue));
                        } catch (NumberFormatException exception) {
                            ScenarioLogger.getInstance().parserWarning("invalid int param " + fieldName + " == "
                                    + fieldValue);
                        }
                    } else if (Boolean.TYPE == field.getType()) {
                        field.setBoolean(constructed, Boolean.parseBoolean(fieldValue));
                    } else if (0 == "this".compareTo(fieldValue)) {
                        field.set(constructed, substituteInsteadThis);
                    } else {
                        field.set(constructed, fieldValue);
                    }
                } catch (IllegalArgumentException exception) {
                    ScenarioLogger.getInstance().parserWarning("invalid param " + fieldName + " == "
                            + params.getProperty(fieldName) + "; object field is not a String");
                } catch (IllegalAccessException exception) {
                    ScenarioLogger.getInstance().parserWarning("access denied to " + fieldName + " == "
                            + params.getProperty(fieldName));
                }
            }

            if (!(((ScriptAction) constructed).validate())) {
                ScenarioLogger.getInstance().parserLog(Level.WARNING, "created instance of " + name + " is invalid");
            }

            return ((ScriptAction) constructed);
        } catch (InstantiationException exception) {
            processException("failed to create action: trying to create abstract class", exception);

            throw new FiniteStatesSetBuilder.ActionConstructionException("failed to construct " + name);
        } catch (IllegalAccessException exception) {
            processException("failed to create action " + name + ": illegal access error", exception);

            throw new FiniteStatesSetBuilder.ActionConstructionException("failed to construct " + name);
        } catch (InvocationTargetException exception) {
            processException("failed to create action " + name + ": construction error",
                             exception.getTargetException());

            throw new FiniteStatesSetBuilder.ActionConstructionException("failed to construct " + name);
        } catch (ClassNotFoundException exception) {
            processException("failed to create action " + name + ":  specified class wasn't found", exception);

            throw new FiniteStatesSetBuilder.ActionConstructionException("failed to construct " + name);
        } catch (NoSuchFieldException exception) {
            processException("data came from xml for action " + name + ": invalid property name", exception);

            throw new FiniteStatesSetBuilder.ActionConstructionException("failed to construct " + name);
        } catch (NullPointerException exception) {
            processException("something big and bad happened while creatring action " + name, exception);

            throw new FiniteStatesSetBuilder.ActionConstructionException("failed to construct " + name);
        } catch (ClassCastException exception) {
            processException("illegal class of action " + name + "; not child of ScriptAction", exception);

            throw new FiniteStatesSetBuilder.ActionConstructionException("failed to construct " + name);
        }
    }

    /**
     *     Actions from action list.
     *    
     *     @param actionList
     *                the action list
     *     @param substituteInsteadThis
     *                the substitute instead this
     *     @param paramToConstructors
     *                the param to constructors
     *     @return the array list
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static ArrayList<ScriptAction> actionsFromActionList(List<ObjectProperties> actionList,
            String substituteInsteadThis, FiniteStateMachine paramToConstructors) {
        if ((null == actionList) || (actionList.isEmpty())) {
            return new ArrayList<ScriptAction>(0);
        }

        ArrayList actionSet = new ArrayList(actionList.size());

        for (ObjectProperties action : actionList) {
            ScenarioLogger.getInstance().parserLog(Level.INFO,
                    "creating ScriptAction for action xml action \"" + action.getName() + '"');

            try {
                if (null != action.getChildObject()) {
                    String masterActionName = XmlStrings.resolveName(action.getName());
                    ScriptAction master = actionFromStringParams(masterActionName, action.getParams(),
                                              substituteInsteadThis, paramToConstructors);
                    String slaveActionName = XmlStrings.resolveName(action.getChildObject().getName());
                    ScriptAction slave = actionFromStringParams(slaveActionName, action.getChildObject().getParams(),
                                             substituteInsteadThis, paramToConstructors);
                    Field[] slaveFields = slave.getClass().getFields();

                    for (Field field : slaveFields) {
                        if (!(field.getType().isInstance(master))) {
                            continue;
                        }

                        field.setAccessible(true);
                        field.set(slave, master);
                        actionSet.add(slave);

                        break;
                    }
                } else {
                    String resolved = XmlStrings.resolveName(action.getName());

                    if (null != resolved) {
                        actionSet.add(actionFromStringParams(resolved, action.getParams(), substituteInsteadThis,
                                paramToConstructors));
                    } else {
                        ScenarioLogger.getInstance().parserLog(Level.SEVERE,
                                "failed to create realization of ScripAction class: can't find class for "
                                + action.getName() + " action");
                    }
                }
            } catch (ActionConstructionException exception) {
                ScenarioLogger.getInstance().parserLog(Level.SEVERE, exception.getMessage());
            } catch (IllegalAccessException exception) {
                System.err.println(exception.getLocalizedMessage());
                exception.printStackTrace(System.err);
                ScenarioLogger.getInstance().parserError(
                    "failed to instanciate hierarhical action: access to field denided");
            } catch (IllegalArgumentException exception) {
                ScenarioLogger.getInstance().parserError("probably unresolved action name came from XML");
                System.err.println(exception.getLocalizedMessage());
                exception.printStackTrace(System.err);
            }
        }

        return actionSet;
    }

    /**
     *     Process exception.
     *    
     *     @param message
     *                the message
     *     @param exception
     *                the exception
     */
    private static void processException(String message, Throwable exception) {
        if (null != message) {
            ScenarioLogger.getInstance().parserWarning(message);
        }

        if ((null == exception) || (null == exception.getMessage())) {
            return;
        }

        ScenarioLogger.getInstance().parserWarning(exception.getMessage());
    }

    /**
     *     Adds the transition.
     *    
     *     @param transitionSource
     *                the transition source
     *     @param transitionDestination
     *                the transition destination
     */
    private static void addTransition(FiniteState transitionSource, List<FiniteState> transitionDestination) {
        if ((null == transitionSource) || (null == transitionDestination)) {
            throw new IllegalArgumentException(
                "transitionSource and transitionDestination must be non-null references");
        }

        for (FiniteState destinationNode : transitionDestination) {
            transitionSource.addNextState(destinationNode);
        }
    }

    /**
     *     Builds the event checker.
     *    
     *     @param condition
     *                the condition
     *     @param soEventTypeExternalParamToConstructor
     *                the so event type external param to constructor
     *     @return the event checker
     */
    @SuppressWarnings("rawtypes")
    public static EventChecker buildEventChecker(IsoCondition condition,
            SpecialObjectEvent.EventType soEventTypeExternalParamToConstructor) {
        if (null == condition) {
            return null;
        }

        if (null != condition.getReadyCondition()) {
            return condition.getReadyCondition();
        }

        String checkerClassName = condition.getProperties().getName();

        ScenarioLogger.getInstance().parserLog(Level.INFO,
                "creating EventChecker for condition \"" + checkerClassName + '"');

        try {
            String resolvedClassName = XmlStrings.resolveName(checkerClassName);

            if (null == resolvedClassName) {
                ScenarioLogger.getInstance().parserWarning("unresolvet action class name " + checkerClassName);

                return null;
            }

            AdvancedClass checkerClass = new AdvancedClass(resolvedClassName, new String[] { "scriptEvents" });
            Constructor[] avalibleConstructors = checkerClass.getAllConstructors();
            EventChecker ware = null;

            for (Constructor creator : avalibleConstructors) {
                switch (creator.getParameterTypes().length) {
                 case 0 :
                     creator.setAccessible(true);
                     ware = (EventChecker) creator.newInstance(new Object[0]);

                     break;

                 case 1 :
                     if (SpecialObjectEvent.EventType.class != creator.getParameterTypes()[0]) {
                         continue;
                     }

                     creator.setAccessible(true);
                     ware = (EventChecker) creator.newInstance(new Object[] { soEventTypeExternalParamToConstructor });

                     break;
                }
            }

            if (null == ware) {
                ScenarioLogger.getInstance().parserWarning("couldn't find appropriate constructor for"
                        + resolvedClassName);

                return null;
            }

            Properties fields = condition.getProperties().getParams();

            for (Map.Entry entry : fields.entrySet()) {
                String fieldName = (String) entry.getKey();
                String fieldValue = (String) entry.getValue();

                try {
                    Field field = checkerClass.findFieldInHierarchy(fieldName);

                    field.setAccessible(true);

                    if (Integer.TYPE == field.getType()) {
                        field.setInt(ware, Integer.parseInt(fieldValue));
                    }

                    if (CoreTime.class == field.getType()) {
                        field.set(ware, new CoreTime(0, 0, 0, Integer.parseInt(fieldValue), 0));
                    }

                    if (String.class == field.getType()) {
                        field.set(ware, fieldValue);
                    }

                    if (SpecialObjectEvent.EventType.class == field.getType()) {
                        try {
                            field.set(ware, soEventTypeExternalParamToConstructor);
                        } catch (IllegalArgumentException exception) {
                            field.set(ware, SpecialObjectEvent.EventType.any);
                            ScenarioLogger.getInstance().parserWarning("failed to set field " + field.getName()
                                    + " of class instance " + checkerClass);
                            ScenarioLogger.getInstance().parserWarning(
                                "invalid enum value came, set field to default value == any");
                        }
                    }

                    ScenarioLogger.getInstance().parserWarning("field " + fieldName + " in class " + resolvedClassName
                            + " has unsupported type");
                } catch (NoSuchFieldException exception) {
                    processException("couldn't find field named " + fieldName + " in class " + resolvedClassName,
                                     exception);
                } catch (NumberFormatException exception) {
                    processException("invalid value to field named " + fieldName + " while creating instance of class "
                                     + resolvedClassName, exception);
                } catch (NullPointerException exception) {
                    processException("NullPointerExcption has occured while creating instance of class "
                                     + resolvedClassName, exception);
                } catch (IllegalArgumentException exception) {
                    processException("IllegalArgumentException has occured while creating instance of class "
                                     + resolvedClassName + ": error in internal data structures", exception);
                }
            }

            if (ware instanceof NativeMessageListener) {
                ((NativeMessageListener) ware).prepare();
            }

            String errorString = ware.isValid();

            if (null != errorString) {
                ScenarioLogger.getInstance().parserWarning("created instance of " + ware.getClass().getName()
                        + " is invalid! error: " + errorString);
            }

            if (null != condition.getAndCondition()) {
                AndEventChecker andChecker = new AndEventChecker();
                EventChecker additionalChacker = buildEventChecker(condition.getAndCondition(),
                                                     soEventTypeExternalParamToConstructor);

                if (null != additionalChacker) {
                    andChecker.addAndChecker(additionalChacker);
                    andChecker.addAndChecker(ware);
                    ware = andChecker;
                } else {
                    ScenarioLogger.getInstance().parserWarning(
                        "failed to create AND event checker: additional checker wasn't created");
                }
            }

            if ((null != condition.getOrList()) && (0 < condition.getOrList().size())) {
                OrEventChecker orChecker = new OrEventChecker();

                for (IsoCondition orCondition : condition.getOrList()) {
                    orChecker.addOrChecker(buildEventChecker(orCondition, soEventTypeExternalParamToConstructor));
                }

                orChecker.addOrChecker(ware);

                return orChecker;
            }

            return ware;
        } catch (ClassNotFoundException exception) {
            processException("couldn't find class with name " + checkerClassName + " and resolved name "
                             + XmlStrings.resolveName(checkerClassName), exception);
        } catch (InstantiationException exception) {
            processException("couldn't instaciate copy of class " + checkerClassName, exception);
        } catch (IllegalAccessException exception) {
            processException("access denied to constructor of class" + checkerClassName, exception);
        } catch (InvocationTargetException exception) {
            processException("access denied to constructor of class" + checkerClassName, exception);
        } catch (NullPointerException exception) {
            processException("something big and bad happened", exception);
        }

        return null;
    }

    /**
     *     Check action for virtual nodes.
     *    
     *     @param actions
     *                the actions
     *     @param virtualNodeNamer
     *                the virtual node namer
     *     @param machine
     *                the machine
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void checkActionForVirtualNodes(List<ScriptAction> actions, VirtualNodeNumeration virtualNodeNamer,
            FiniteStateMachine machine) {
        ArrayList added_actions = new ArrayList();

        for (ScriptAction action : actions) {
            if (action.actActionAsScenarioNode()) {
                IsoQuest quest = buildVirualSelfFinishConditionalState(action, virtualNodeNamer, machine);

                buildStateSet(quest, machine);

                String virtualNodeName = quest.getName() + "_phase_" + 0;

                added_actions.add(new StartScenarioBranchAction(virtualNodeName, machine));
            }
        }

        actions.addAll(added_actions);
    }

    /**
     *     Builds the virual self finish conditional state.
     *    
     *     @param action
     *                the action
     *     @param virtualNodeNamer
     *                the virtual node namer
     *     @param machine
     *                the machine
     *     @return the iso quest
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
    public IsoQuest buildVirualSelfFinishConditionalState(ScriptAction action, VirtualNodeNumeration virtualNodeNamer,
            FiniteStateMachine machine) {
        IsoQuest quest = new IsoQuest();

        quest.setName(virtualNodeNamer.buildName(action.getClass().getName()));
        quest.setFinishOnLastPhase(true);

        IsoCondition condition = null;

        if (null != action.getExactEventForConditionOnActivate()) {
            ScriptEvent eventForChecker = action.getExactEventForConditionOnActivate();
            EventChecker checker;

            if (eventForChecker instanceof VoidEvent) {
                checker = new VoidEventChecker((VoidEvent) eventForChecker, true);
            } else {
                checker = new ExactEventChecker(eventForChecker, true);
            }

            condition = new IsoCondition(checker);
        }

        ArrayList actions = new ArrayList();

        actions.add(action.getChildAction());
        actions.add(new StopScenarioBranchAction(quest.getName(), machine));

        IsoQuestItemTask task = new IsoQuestItemTask(actions, condition);

        quest.addTask(task);
        condition = null;

        if (null != action.getExactEventForConditionOnDeactivate()) {
            ScriptEvent eventForChecker = action.getExactEventForConditionOnDeactivate();
            EventChecker checker;

            if (eventForChecker instanceof VoidEvent) {
                checker = new VoidEventChecker((VoidEvent) eventForChecker, false);
            } else {
                checker = new ExactEventChecker(eventForChecker, false);
            }

            condition = new IsoCondition();
        }

        actions = new ArrayList();
        actions.add(new StopScenarioBranchAction(quest.getName(), machine));
        task = new IsoQuestItemTask(actions, condition);
        quest.addTask(task);

        return quest;
    }

    /**
     *     Builds the state set.
     *    
     *     @param isoQuest
     *                the iso quest
     *     @param machine
     *                the machine
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void buildStateSet(IsoQuest isoQuest, FiniteStateMachine machine) {
        if ((null == isoQuest) || (null == machine)) {
            throw new IllegalArgumentException("all parameters must be non-null references");
        }

        if (0 >= isoQuest.getTasks().length) {
            throw new IllegalArgumentException("isoQuest must have at least one phase");
        }

        VirtualNodeNumeration virtualNodeNamer = new VirtualNodeNumeration(isoQuest.getName());

        ScenarioLogger.getInstance().parserLog(Level.INFO,
                "creating scenario subgraph for isoQuest \"" + isoQuest.getName() + '"');

        IsoQuestItemTask[] questPhases = isoQuest.getTasks();

        Arrays.sort(questPhases);

        ArrayList recentlyCreatedStates = new ArrayList(isoQuest.getTasks().length);
        LinkedList namesAccumulator = new LinkedList();
        String finalStateName = isoQuest.getName() + "_phase_" + (questPhases[(questPhases.length - 1)].getPhase() + 1);
        FiniteState finalState = new FiniteState(null, finalStateName);

        if (isoQuest.isFinishOnLastPhase()) {
            finalState.addStateChangedListener(new FsmActionAdapter(null) {
                @Override
                public void stateEntered(FiniteState state) {
                    this.machine.deactivateState(state.getName());
                }
            });
        }

        namesAccumulator.add(finalStateName);
        recentlyCreatedStates.add(finalState);

        int currentPositionInPhaseArray = questPhases.length - 1;
        int currentPhase = questPhases[currentPositionInPhaseArray].getPhase();
        String currentNodeName = isoQuest.getName() + "_phase_" + currentPhase;
        IsoQuestEmulationStateProcessor isoQuestEmulation = new IsoQuestEmulationStateProcessor(finalStateName);
        LinkedList toActOnStast = new LinkedList();
        boolean isNodeWalkThrough = true;
        boolean finishExists = false;

        while (0 <= currentPositionInPhaseArray) {
            ScenarioLogger.getInstance().parserLog(Level.INFO, "processing phase #" + currentPhase + " task");

            ArrayList toAct = new ArrayList();
            ArrayList actionsInProperties = new ArrayList();

            questPhases[currentPositionInPhaseArray].getActionList(actionsInProperties, toAct);
            toAct.addAll(actionsFromActionList(actionsInProperties, isoQuest.getName(), machine));
            checkActionForVirtualNodes(toAct, virtualNodeNamer, machine);

            if (questPhases[currentPositionInPhaseArray].isLastPhase()) {
                toAct.add(new StopScenarioBranchAction(currentNodeName, machine));
                finishExists = true;
            }

            EventChecker conditionChecker = null;

            if (null == questPhases[currentPositionInPhaseArray].getCondition()) {
                if (SpecialObjectEvent.EventType.none == questPhases[currentPositionInPhaseArray].getEventType()) {
                    toActOnStast.addAll(toAct);
                } else {
                    conditionChecker =
                        new SpecialObjectEventTypeChecker(questPhases[currentPositionInPhaseArray].getEventType());
                    isNodeWalkThrough = false;
                }
            } else {
                conditionChecker = buildEventChecker(questPhases[currentPositionInPhaseArray].getCondition(),
                        questPhases[currentPositionInPhaseArray].getEventType());

                if (null != conditionChecker) {
                    isNodeWalkThrough = false;
                } else {
                    ScenarioLogger.getInstance().parserWarning(
                        "EventChecker wasn't created from condition list, check XML for mistakes; scenario machine can work uncorrectly");
                }
            }

            if (null != conditionChecker) {
                EventReaction reaction;

                switch (toAct.size()) {
                 case 0 :
                     reaction = new SimpleEventReaction(null, conditionChecker,
                                                        questPhases[currentPositionInPhaseArray].getUid());

                     break;

                 case 1 :
                     reaction = new SimpleEventReaction((ScriptAction) toAct.get(0), conditionChecker,
                                                        questPhases[currentPositionInPhaseArray].getUid());

                     break;

                 default :
                     Collections.sort(toAct);
                     reaction = new ComplexEventReaction(conditionChecker,
                             questPhases[currentPositionInPhaseArray].getUid());
                     ((ComplexEventReaction) reaction).addAction(toAct);
                }

                isoQuestEmulation.addReaction(reaction);
            }

            --currentPositionInPhaseArray;

            if ((0 > currentPositionInPhaseArray)
                    || (0 < currentPhase - questPhases[currentPositionInPhaseArray].getPhase())) {
                if ((0 <= currentPositionInPhaseArray)
                        && (1 < questPhases[currentPositionInPhaseArray].getPhase() - currentPhase)) {
                    ScenarioLogger.getInstance().parserWarning(
                        "Scenario structure error: found illegal scenario step from phase " + currentPhase + " to "
                        + questPhases[currentPositionInPhaseArray].getPhase());
                    ScenarioLogger.getInstance().parserWarning("Quset name: " + isoQuest.getName());
                    ScenarioLogger.getInstance().parserWarning("Scenario will work uncorrectly");

                    return;
                }

                createSimpleAdvanceScheme(namesAccumulator, isoQuestEmulation);

                FiniteState state = new FiniteState(isoQuestEmulation, currentNodeName);

                if (isNodeWalkThrough) {
                    toActOnStast.add(new SingleStepScenarioAdvanceAction(isoQuestEmulation.getImplicitNodeName(),
                            machine));
                }

                if (finishExists) {
                    isoQuestEmulation.setImplicitStepNextNodeName(null);
                }

                if (!(toActOnStast.isEmpty())) {
                    if (1 < toActOnStast.size()) {
                        Collections.sort(toActOnStast);
                    }

                    state.addStateChangedListener(new FsmActionAdapter(toActOnStast) {
                        @Override
                        public void stateEntered(FiniteState state) {
                            actAllActions();
                        }
                    });
                }

                addTransition(state, recentlyCreatedStates);
                recentlyCreatedStates.add(state);
                namesAccumulator.add(currentNodeName);

                if (0 <= currentPositionInPhaseArray) {
                    finishExists = false;
                    currentPhase = questPhases[currentPositionInPhaseArray].getPhase();
                    isoQuestEmulation = new IsoQuestEmulationStateProcessor(currentNodeName);
                    currentNodeName = isoQuest.getName() + "_phase_" + currentPhase;
                    isNodeWalkThrough = true;
                    toActOnStast.clear();
                }
            }
        }

        this.states.addAll(recentlyCreatedStates);
    }

    /**
     *     Builds the state set.
     *    
     *     @param phasedQuest
     *                the phased quest
     *     @param machine
     *                the machine
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
    public void buildStateSet(PhasedQuest phasedQuest, FiniteStateMachine machine) {
        if ((null == phasedQuest) || (null == machine)) {
            throw new IllegalArgumentException("phasedQuest and machine must be non-null references");
        }

        ScenarioLogger.getInstance().parserLog(Level.INFO,
                "creating scenario subgraph for phasedQuest \"" + phasedQuest.getName() + '"');

        if (null != phasedQuest.getOrganizerRef()) {
            Organizers.getInstance().add(phasedQuest.getOrganizerRef(),
                                         Pattern.compile(phasedQuest.getName() + "(" + "_phase_" + "\\d+)?"));
        }

        ArrayList recentlyCreatedStates = new ArrayList(phasedQuest.getPhases().size() + 2);
        ArrayList nodeChainNames = new ArrayList(phasedQuest.getPhases().size() + 1);
        String previousNodeName;

        if (!(phasedQuest.getPhases().isEmpty())) {
            previousNodeName = phasedQuest.getName() + "_phase_" + (phasedQuest.getPhases().getLast().getNom() + 1);
        } else {
            previousNodeName = phasedQuest.getName() + "_phase_" + 1;
        }

        FiniteState finalState = new FiniteState(null, previousNodeName);
        List actionsOnExit = actionsFromActionList(phasedQuest.getActionsOnEnd(), previousNodeName, machine);

        if ((null != phasedQuest.getMissionPoint()) && (0 < phasedQuest.getMissionPoint().length())) {
            actionsOnExit.add(new MissionPointUnmarkAction(phasedQuest.getMissionPoint()));
        }

        Collections.sort(actionsOnExit);
        finalState.addStateChangedListener(new FsmActionAdapter(actionsOnExit) {
            private Object machine;
            @Override
            public void stateEntered(FiniteState state) {
                actAllActions();
                ((FiniteStateMachine) this.machine).deactivateState(state.getName());
            }
        });
        nodeChainNames.add(previousNodeName);
        recentlyCreatedStates.add(finalState);

        for (ListIterator iter = phasedQuest.getPhases().listIterator(phasedQuest.getPhases().size());
                iter.hasPrevious(); ) {
            QuestPhase phase = (QuestPhase) iter.previous();

            ScenarioLogger.getInstance().parserLog(Level.INFO,
                    "creating subgraph node for \"" + phasedQuest.getName() + "\" quest phase #" + phase.getNom());

            String phaseName = phasedQuest.getName() + "_phase_" + phase.getNom();
            PhasedQuestEmulationStateProcessor stateProcessor = new PhasedQuestEmulationStateProcessor();

            createSimpleAdvanceScheme(nodeChainNames, stateProcessor);

            FiniteState extractedState = new FiniteState(stateProcessor, phaseName);
            List onStart = actionsFromActionList(phase.getActionList(), phaseName, machine);
            String pointToUnmark;

            if (null != phase.getMissionPoint()) {
                onStart.add(new MissionPointMarkAction(phase.getMissionPoint()));
                pointToUnmark = phase.getMissionPoint();
            } else {
                pointToUnmark = null;
            }

            Collections.sort(onStart);
            extractedState.addStateChangedListener(new FsmActionAdapter(onStart) {
                private Object pointToUnmark;
                @Override
                public void stateEntered(FiniteState state) {
                    actAllActions();
                }
                @Override
                public void stateLeaved(FiniteState state) {
                    if (null == this.pointToUnmark) {
                        return;
                    }

                    ScriptAction toAct = new MissionPointUnmarkAction();

                    toAct.act();
                }
            });
            nodeChainNames.add(phaseName);
            addTransition(extractedState, recentlyCreatedStates);
            recentlyCreatedStates.add(extractedState);
        }

        PhasedQuestEmulationStateProcessor stateProcessor = new PhasedQuestEmulationStateProcessor();

        createSimpleAdvanceScheme(nodeChainNames, stateProcessor);

        FiniteState firstState = new FiniteState(stateProcessor, phasedQuest.getName());
        List onEnterActionList = actionsFromActionList(phasedQuest.getActionsOnStart(), phasedQuest.getName(), machine);

        if ((null != phasedQuest.getMissionPoint()) && (0 < phasedQuest.getMissionPoint().length())) {
            onEnterActionList.add(new MissionPointMarkAction(phasedQuest.getMissionPoint()));
        }

        onEnterActionList.add(new StartOrgAction(phasedQuest.getName() + "_phase_" + 1));
        Collections.sort(onEnterActionList);
        firstState.addStateChangedListener(new FsmActionAdapter(onEnterActionList) {
            @Override
            public void stateEntered(FiniteState state) {
                actAllActions();
            }
        });
        addTransition(firstState, recentlyCreatedStates);
        recentlyCreatedStates.add(firstState);
        this.states.addAll(recentlyCreatedStates);
    }

    /**
     *     Adds the states to set.
     *    
     *     @param set
     *                the set
     */
    void addStatesToSet(FiniteStatesSet set) {
        for (FiniteState scenarioNode : this.states) {
            set.addState(scenarioNode);
        }
    }

    /**
     *     Pour out.
     *    
     *     @param set
     *                the set
     */
    void pourOut(FiniteStatesSet set) {
        addStatesToSet(set);
        this.states.clear();
    }

    /**
     *     The Class ActionConstructionException.
     */
    private static final class ActionConstructionException extends Exception {

        /** The Constant serialVersionUID. */
        static final long serialVersionUID = 1L;

        /**
         *         Instantiates a new action construction exception.
         */
        @SuppressWarnings("unused")
        ActionConstructionException() {}

        /**
         *         Instantiates a new action construction exception.
         *        
         *         @param message
         *                    the message
         */
        ActionConstructionException(String message) {
            super(message);
        }
    }


    /**
     *     The Class VirtualNodeNumeration.
     */
    private static class VirtualNodeNumeration {

        /** The iter. */
        private int iter = 0;

        /** The parent node name. */
        private final String parentNodeName;

        /**
         *         Instantiates a new virtual node numeration.
         *        
         *         @param parentNodeName
         *                    the parent node name
         */
        VirtualNodeNumeration(String parentNodeName) {
            this.parentNodeName = parentNodeName;
        }

        /**
         *         Builds the name.
         *        
         *         @param prefix
         *                    the prefix
         *         @return the string
         */
        String buildName(String prefix) {
            return prefix + "_virtual_" + this.parentNodeName + "_" + (this.iter++);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
