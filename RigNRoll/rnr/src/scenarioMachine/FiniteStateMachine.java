/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package rnr.src.scenarioMachine;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import rnr.src.rnrcore.XmlSerializable;
import rnr.src.rnrloggers.ScenarioLogger;
import rnr.src.scenarioUtils.Pair;
import rnr.src.scenarioXml.XmlFilter;
import rnr.src.scenarioXml.XmlNodeDataProcessor;
import rnr.src.scriptEvents.EventListener;
import rnr.src.scriptEvents.ScenarioBranchEndEvent;
import rnr.src.scriptEvents.ScriptEvent;
import rnr.src.xmlserialization.Helper;

public final class FiniteStateMachine implements EventListener,
		XmlNodeDataProcessor, XmlSerializable {
	private static final String STATE_NODE_NAME = "state";
	private static final String FSM_STATE_NODE_NAME = "fsmState";
	private static final String REACTED_NODE_NAME = "reacted";
	private static final String UID_ATTRIBUTE = "uid";
	private static final String NAME_ATTRIBUTE = "name";
	private static final int MAX_WALK_THROUGH_DEEP = 128;
	private static final int DEFAULT_CURRENT_STATES_STORAGE_CAPACITY = 8;
	private final LinkedHashMap<String, Pair<Boolean, FiniteState>> currentActiveStates;
	private FiniteStatesSet statesPool;
	private static final int ARRAY_CAPASITY = 32;
	private final List<FiniteState> recentlyEntered;
	private final List<FiniteState> recentlyLeaved;
	private final Set<String> toWalkThrough;
	private final List<String[]> toActivate;
	private final Set<String> toDeactivate;
	private ChangesContainer changesLog;
	private int eventsDeep;

	public void clear() {
		this.currentActiveStates.clear();

		this.recentlyEntered.clear();
		this.recentlyLeaved.clear();

		this.toWalkThrough.clear();
		this.toActivate.clear();
		this.toDeactivate.clear();

		this.eventsDeep = 0;
		this.statesPool = null;
	}

	public FiniteStateMachine() {
		this(false);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FiniteStateMachine(boolean recordChanges) {
		this.currentActiveStates = new LinkedHashMap<String, Pair<Boolean, FiniteState>>(8);
		this.statesPool = null;

		this.recentlyEntered = new ArrayList(32);
		this.recentlyLeaved = new ArrayList(32);

		this.toWalkThrough = new HashSet();
		this.toActivate = new LinkedList();
		this.toDeactivate = new HashSet();

		this.changesLog = null;

		this.eventsDeep = 0;

		this.statesPool = new FiniteStatesSet();
		if (!(recordChanges))
			return;
		this.changesLog = new ChangesContainer();
	}

	private static void printErrorMessage(String error) {
		ScenarioLogger.getInstance().machineWarning(error);
		ScenarioLogger.getInstance().machineLog(Level.FINEST, "STACK TRACE:");
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (StackTraceElement callNode : stackTrace) {
			ScenarioLogger.getInstance().machineLog(Level.FINEST,
					callNode.toString());
		}
	}

	@SuppressWarnings("unchecked")
	private boolean activateState(FiniteState state) {
		if (null != state) {
			if (null != this.changesLog) {
				this.changesLog.addScenarioBranchStart(state.getName());
			}
			this.currentActiveStates.put(state.getName(),
					new Pair<Boolean, FiniteState>(Boolean.valueOf(false), state));
			state.entered();
			ScenarioLogger.getInstance().machineLog(Level.INFO,
					"activated state " + state.getName());
			return true;
		}
		return false;
	}

	private void walkThroughToEnd(String startOfWayName) {
		Pair pair = this.currentActiveStates.remove(startOfWayName);

		if (null != pair) {
			int iterationCount = 0;
			FiniteState startOfWay = (FiniteState) pair.getSecond();
			startOfWay.leaved();
			do {
				if ((null == startOfWay.getNextStates())
						|| (startOfWay.getNextStates().isEmpty()))
					break;
				startOfWay = startOfWay.getNextStates()
						.getFirst();
				++iterationCount;
			} while (128 >= iterationCount);

			ScenarioLogger
					.getInstance()
					.machineLog(
							Level.SEVERE,
							"failed to find end of way == "
									+ startOfWayName
									+ ": way is too long, check FSM or increase max way lenght");

			this.currentActiveStates.put(
					((FiniteState) pair.getSecond()).getName(), pair);
			 return;
		} else {
			ScenarioLogger.getInstance().machineLog(
					Level.SEVERE,
					"failed to find start of way == " + startOfWayName
							+ ": walkThroghToEnd call failed");
			return;
		}
	}

	private void deactivateAllStatesInQueue() {
		for (String stateName : this.toDeactivate) {
			@SuppressWarnings("rawtypes")
			Pair removed = this.currentActiveStates.remove(stateName);
			if (null != removed) {
				ScenarioLogger.getInstance().machineLog(Level.INFO,
						stateName + " deactivated");
				if (null != this.changesLog) {
					this.changesLog
							.addScenarioStateClosed(((FiniteState) removed
									.getSecond()).getName());
				}
				((FiniteState) removed.getSecond()).leaved();
			} else {
				printErrorMessage("ScenarioMachine internal error: trying to deactivate unexsisting state == "
						+ stateName);
			}
		}
		this.toDeactivate.clear();
	}

	private void activateAllStatesInQueue() {
		for (Iterator<String[]> posibleNamesIterator = this.toActivate.iterator(); posibleNamesIterator
				.hasNext();) {
			String[] posibleNames = posibleNamesIterator.next();
			posibleNamesIterator.remove();
			for (String stateName : posibleNames) {
				FiniteState found = this.statesPool.findState(stateName);
				if (null == found)
					continue;
				activateState(found);
				break;
			}
		}
	}

	public final void setStatesPool(FiniteStatesSet statesPool) {
		if (null == statesPool) {
			throw new IllegalArgumentException(
					"stateToPool must be non-null reference");
		}
		this.statesPool = statesPool;
	}

	public final void activateState(boolean immediately, String[] stateNames) {
		if (immediately) {
			for (String stateName : stateNames) {
				if (activateState(this.statesPool.findState(stateName))) {
					return;
				}
			}

		} else
			this.toActivate.add(stateNames);
	}

	public final void activateState(String[] stateNames) {
		activateState(0 == this.eventsDeep, stateNames);
	}

	public final String findEndNode(String startOfWayName) {
		if (null == startOfWayName) {
			return null;
		}
		FiniteState startOfWay = this.statesPool.getStates().get(
				startOfWayName);
		if (null != startOfWay) {
			while (true) {
				if ((null == startOfWay.getNextStates())
						|| (startOfWay.getNextStates().isEmpty()))
					break;
				startOfWay = startOfWay.getNextStates()
						.getFirst();
			}

		}

		ScenarioLogger.getInstance().machineLog(
				Level.SEVERE,
				"failed to find start of way == " + startOfWayName
						+ ": findEndNode call failed");
		return null;
	}

	public final void addNodeToWalkFromToEnd(String startOfWayName) {
		if (null == startOfWayName)
			return;
		this.toWalkThrough.add(startOfWayName);
	}

	public final void deactivateState(String stateName) {
		if (null == stateName)
			return;
		this.toDeactivate.add(stateName);
	}

	public final int getCurrentActiveStatesCount() {
		return this.currentActiveStates.size();
	}

	public final Collection<String> getCurrentActiveStates() {
		return Collections.unmodifiableSet(this.currentActiveStates.keySet());
	}

	public final Set<String> getStatesNames() {
		return Collections
				.unmodifiableSet(this.statesPool.getStates().keySet());
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final void eventHappened(List<ScriptEvent> event) {
		ScenarioLogger.getInstance().machineLog(Level.FINEST,
				"BEGINING PROCESSING OF EVENT TUPLE");

		this.eventsDeep += 1;

		for (ListIterator<ScriptEvent> iter = event.listIterator(); iter.hasNext();) {
			ScriptEvent ev = iter.next();

			if (ev instanceof ScenarioBranchEndEvent) {
				Pattern nodeToDeactivateNamePattern = ((ScenarioBranchEndEvent) ev)
						.getNodeNamePattern();
				for (@SuppressWarnings("rawtypes") Map.Entry entry : this.currentActiveStates.entrySet()) {
					if (nodeToDeactivateNamePattern.matcher(
							(CharSequence) entry.getKey()).matches()) {
						this.toWalkThrough.add((String) entry.getKey());
					}
				}
			}
		}
		Pattern nodeToDeactivateNamePattern;
		for (@SuppressWarnings("rawtypes") Map.Entry next : this.currentActiveStates.entrySet()) {
			if (((Boolean) ((Pair) next.getValue()).getFirst()).booleanValue()) {
				continue;
			}

			FiniteState currentState = (FiniteState) ((Pair) next.getValue())
					.getSecond();
			ScenarioLogger.getInstance().machineLog(Level.FINEST,
					"processing event tuple on " + currentState.getName());
			FiniteState newState = currentState.process(event);

			if ((currentState != newState)
					&& (!(this.toWalkThrough.contains(currentState.getName())))
					&& (!(this.toDeactivate.contains(currentState.getName())))) {
				if (null != this.changesLog) {
					this.changesLog.addStep(currentState.getName(),
							newState.getName());
				}
				ScenarioLogger.getInstance().machineLog(
						Level.FINEST,
						"deleted node from active states; name == "
								+ currentState.getName());

				((Pair) next.getValue()).setFirst(Boolean.valueOf(true));
				this.recentlyEntered.add(newState);
				this.recentlyLeaved.add(currentState);
			}
		}

		this.eventsDeep -= 1;

		if (0 == this.eventsDeep) {
			for (Iterator iter = this.currentActiveStates.entrySet().iterator(); iter
					.hasNext();) {
				Map.Entry next = (Map.Entry) iter.next();

				if (((Boolean) ((Pair) next.getValue()).getFirst())
						.booleanValue()) {
					iter.remove();
				}

			}

			for (FiniteState entered : this.recentlyEntered) {
				this.currentActiveStates.put(entered.getName(), new Pair(
						Boolean.valueOf(false), entered));
				ScenarioLogger.getInstance().machineLog(Level.FINEST,
						entered.getName() + " added to active states");
			}

			for (ListIterator iter = this.recentlyEntered.listIterator(); iter
					.hasNext();) {
				FiniteState entered = (FiniteState) iter.next();
				iter.remove();
				entered.entered();
			}

			for (ListIterator iter = this.recentlyLeaved.listIterator(); iter
					.hasNext();) {
				FiniteState leaved = (FiniteState) iter.next();
				iter.remove();
				leaved.leaved();
			}

			while (!(this.toWalkThrough.isEmpty())) {
				String toRemoveThroughMoving = this.toWalkThrough
						.iterator().next();
				this.toWalkThrough.remove(toRemoveThroughMoving);
				walkThroughToEnd(toRemoveThroughMoving);
			}

			this.toWalkThrough.clear();
			deactivateAllStatesInQueue();
			activateAllStatesInQueue();
		}

		ScenarioLogger.getInstance().machineLog(Level.FINEST,
				"END OF PROCESSING OF EVENT TUPLE");
	}

	public final void clearActiveStates() {
		this.currentActiveStates.clear();
	}

	@SuppressWarnings("unchecked")
	private void loadCurrentFSMStateFromStringArray(
			Collection<Pair<String, List<Integer>>> array) {
		if (null == array) {
			ScenarioLogger.getInstance().machineLog(Level.SEVERE,
					"array is null");
			throw new IllegalArgumentException("'array' is null");
		}

		this.currentActiveStates.clear();
		for (@SuppressWarnings("rawtypes") Pair nodeToActivate : array) {
			FiniteState node = this.statesPool.getStates().get(
					nodeToActivate.getFirst());
			if (null != node) {
				this.currentActiveStates.put((String) nodeToActivate.getFirst(),
						new Pair<Boolean, FiniteState>(Boolean.valueOf(false), node));

				node.restoreState((List<Integer>) nodeToActivate.getSecond());
			} else {
				ScenarioLogger
						.getInstance()
						.machineLog(
								Level.SEVERE,
								"node \""
										+ nodeToActivate
										+ "\" wasn't found in pool: restored FSM can be uncorrect");
			}
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void process(Node target, Object param) {
		if ((null == target) || (null == param)) {
			return;
		}

		try {
			@SuppressWarnings("rawtypes")
			Collection statesToRestore = (Collection) param;

			String recordFromXML = target.getAttributes().getNamedItem("name")
					.getTextContent();
			@SuppressWarnings("rawtypes")
			List doneReactions = new LinkedList();
			XmlFilter searcher = new XmlFilter(target.getChildNodes());
			Node reacted = searcher.nodeNameNext("reacted");
			while (null != reacted) {
				try {
					doneReactions.add(new Integer(reacted.getAttributes()
							.getNamedItem("uid").getTextContent()));
				} catch (NumberFormatException e) {
					ScenarioLogger.getInstance().machineLog(
							Level.SEVERE,
							"invalid value came from save file: bad reaction uid; error message == "
									+ e.getMessage());
				}
				reacted = searcher.nodeNameNext("reacted");
			}
			statesToRestore.add(new Pair(recordFromXML, doneReactions));
		} catch (ClassCastException e) {
			ScenarioLogger.getInstance().machineLog(Level.SEVERE,
					"invalid param to \"process\" method");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void loadFromNode(Node source) {
		if (null == source) {
			throw new IllegalArgumentException(
					"source must be non-null reference");
		}

		@SuppressWarnings("rawtypes")
		Collection statesToRestore = new LinkedList();
		XmlFilter filter = new XmlFilter(source.getChildNodes());
		filter.visitAllNodes("state", this, statesToRestore);

		loadCurrentFSMStateFromStringArray(statesToRestore);
	}

	@Override
	public void yourNodeWasNotFound() {
	}

	@Override
	public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
		if (null == stream) {
			throw new IllegalArgumentException("stream must be non-null");
		}
		Helper.openNode(stream, "fsmState");
		for (@SuppressWarnings("rawtypes") Pair activeState : this.currentActiveStates.values()) {
			FiniteState state = (FiniteState) activeState.getSecond();
			List<Integer> dataToSave = state.getDataToSave();
			if (!(dataToSave.isEmpty())) {
				Helper.printOpenNodeWithAttributes(stream, "state", Helper
						.createSingleAttribute("name",
								((FiniteState) activeState.getSecond())
										.getName()));
				for (Integer valueToSave : dataToSave) {
					Helper.printClosedNodeWithAttributes(
							stream,
							"reacted",
							Helper.createSingleAttribute("uid",
									valueToSave.toString()));
				}
				Helper.closeNode(stream, "state");
			} else {
				Helper.printClosedNodeWithAttributes(stream, "state", Helper
						.createSingleAttribute("name",
								((FiniteState) activeState.getSecond())
										.getName()));
			}
		}
		Helper.closeNode(stream, "fsmState");
	}

	@Override
	public String getRootNodeName() {
		return "fsmState";
	}

	@SuppressWarnings("unchecked")
	public List<List<ScriptEvent>> getExpectedEvents() {
		@SuppressWarnings("rawtypes")
		List out = new LinkedList();
		for (@SuppressWarnings("rawtypes") Map.Entry entry : this.currentActiveStates.entrySet()) {
			@SuppressWarnings("rawtypes")
			List<List<ScriptEvent>> listOfExpected = ((FiniteState) ((Pair) entry.getValue())
					.getSecond()).getExpectedEvents();
			if ((null != listOfExpected) && (!(listOfExpected.isEmpty()))) {
				out.addAll(listOfExpected);
			}
		}
		return out;
	}

	public List<String> getRecentlyStarted() {
		if (null == this.changesLog) {
			throw new UnsupportedOperationException(
					"machine was started without enabling of log");
		}
		return Collections.unmodifiableList(this.changesLog.getStarted());
	}

	public List<String> getRecentlyClosed() {
		if (null == this.changesLog) {
			throw new UnsupportedOperationException(
					"machine was started without enabling of log");
		}
		return Collections.unmodifiableList(this.changesLog.getClosed());
	}

	public List<Pair<String, String>> getRecentlySteps() {
		if (null == this.changesLog) {
			throw new UnsupportedOperationException(
					"machine was started without enabling of log");
		}
		return Collections.unmodifiableList(this.changesLog.getSteps());
	}

	public void clearLog() {
		if (null == this.changesLog) {
			throw new UnsupportedOperationException(
					"machine was started without enabling of log");
		}
		this.changesLog.clearAll();
	}

	private static final class ChangesContainer {
		private final List<String> branchesStarted;
		private final List<String> branchesClosed;
		private final List<Pair<String, String>> steps;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private ChangesContainer() {
			this.branchesStarted = new LinkedList();
			this.branchesClosed = new LinkedList();
			this.steps = new LinkedList();
		}

		@SuppressWarnings("unchecked")
		void addStep(String from, String to) {
			if ((null == from) || (null == to)) {
				throw new IllegalArgumentException(
						"all parameters must be non-null");
			}
			this.steps.add(new Pair<String, String>(from, to));
		}

		void addScenarioBranchStart(String started) {
			if (null == started) {
				throw new IllegalArgumentException(
						"started must be non-null reference");
			}
			this.branchesStarted.add(started);
		}

		void addScenarioStateClosed(String closed) {
			if (null == closed) {
				throw new IllegalArgumentException(
						"started must be non-null reference");
			}
			this.branchesClosed.add(closed);
		}

		void clearAll() {
			this.branchesStarted.clear();
			this.branchesClosed.clear();
			this.steps.clear();
		}

		List<String> getStarted() {
			return Collections.unmodifiableList(this.branchesStarted);
		}

		List<String> getClosed() {
			return Collections.unmodifiableList(this.branchesClosed);
		}

		List<Pair<String, String>> getSteps() {
			return Collections.unmodifiableList(this.steps);
		}
	}
}