/*
 * @(#)ScenarioStage.java   13/08/26
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


package rnr.src.rnrscenario.consistency;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrloggers.ScriptsLogger;
import rnr.src.xmlserialization.nxs.AnnotatedSerializable;
import rnr.src.xmlserialization.nxs.LoadFrom;
import rnr.src.xmlserialization.nxs.SaveTo;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class ScenarioStage implements AnnotatedSerializable {

    /** Field description */
    public static final int SCENARIO_STAGE_UNDEFINED_MARKER = -2147483648;

    /** Field description */
    public static final int SCENARIO_STAGE_FINISHED_SUCCESSFULLY_MARKER = 2147483646;

    /** Field description */
    public static final int SCENARIO_STAGE_FINISHED_UNSUCCESSFULLY_MARKER = 2147483645;

    /** Field description */
    public static final int SCENARIO_STAGE_UNLOADED_MARKER = 2147483647;
    private static final String MY_SERIALIZATION_UID = "ScenarioStage";
    private static final int LISTENERS_INITIAL_CAPACITY = 7;
    private final List<StageChangedListener> listeners;
    private final Object latch;
    @SaveTo(
        destinationNodeName = "stage",
        constructorArgumentNumber = 0,
        saveVersion = 0
    )
    @LoadFrom(
        sourceNodeName = "stage",
        fromVersion = 0,
        untilVersion = 0
    )
    private int stageNumber;

    /**
     * Constructs ...
     *
     */
    public ScenarioStage() {
        this.listeners = new ArrayList<StageChangedListener>(7);
        this.latch = new Object();
        this.stageNumber = -2147483648;
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void addListener(StageChangedListener listener) {
        synchronized (this.latch) {
            if (null != listener) {
                this.listeners.add(listener);
            }
        }
    }

    /**
     * Method description
     *
     */
    public void scenarioStageUndefined() {
        synchronized (this.latch) {
            this.stageNumber = -2147483648;
        }
    }

    /**
     * Method description
     *
     *
     * @param successful
     */
    public void scenarioFinished(boolean successful) {
        ScriptsLogger.getInstance().log(Level.INFO, 4, "scenario stopped at stage #" + this.stageNumber);
        setStageNumber((successful)
                       ? 2147483646
                       : 2147483645);
    }

    /**
     * Method description
     *
     */
    public void scenarioUnloaded() {
        ScriptsLogger.getInstance().log(Level.INFO, 4, "scenario stopped at stage #" + this.stageNumber);
        setStageNumber(2147483647);
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setStageNumber(int value) {
        synchronized (this.latch) {
            if (value < this.stageNumber) {
                ScriptsLogger.getInstance().log(Level.SEVERE, 4, "attempt to setup lower scenario stage value!");
                ConsistancyCorruptedReporter.scenarioBackwardMoving();

                return;
            }

            this.stageNumber = value;

            for (StageChangedListener listener : this.listeners) {
                listener.scenarioCheckPointReached(this);
            }

            ScriptsLogger.getInstance().log(Level.INFO, 4, "entered scenario stage #" + value);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getScenarioStage() {
        synchronized (this.latch) {
            return this.stageNumber;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isScenarioFinished() {
        synchronized (this.latch) {
            boolean finished = 2147483646 == this.stageNumber;

            finished |= 2147483645 == this.stageNumber;
            finished |= 2147483647 == this.stageNumber;

            return finished;
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void finilizeDeserialization() {
        ScriptsLogger.getInstance().log(Level.INFO, 4, "scenario loaded at stage #" + this.stageNumber);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getUid() {
        return "ScenarioStage";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getId() {
        return getUid();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Object getLatch() {
        return this.latch;
    }
}


//~ Formatted in DD Std on 13/08/26
