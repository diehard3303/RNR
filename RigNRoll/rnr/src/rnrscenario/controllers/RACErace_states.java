/*
 * @(#)RACErace_states.java   13/08/28
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


package rnr.src.rnrscenario.controllers;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.actorveh;
import rnr.src.rnrscenario.consistency.ScenarioClass;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
@ScenarioClass(
    scenarioStage = -1,
    fieldWithDesiredStage = "scenarioStage"
)
public class RACErace_states {
    private double timeElapsed = 0.0D;
    private boolean anyonfinish = false;
    private int lastplace = 1;
    private int statesucceded = 0;
    private HashMap<actorveh, RACErace_state_single> participants = new HashMap();
    private final int scenarioStage;

    /**
     * Constructs ...
     *
     *
     * @param scenarioStage
     */
    public RACErace_states(int scenarioStage) {
        this.scenarioStage = scenarioStage;
    }

    /**
     * Method description
     *
     *
     * @param player
     */
    public void addParticipant(actorveh player) {
        if (this.participants.containsKey(player)) {
            return;
        }

        this.participants.put(player, new RACErace_state_single(this.scenarioStage));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Set<actorveh> getParticipants() {
        return this.participants.keySet();
    }

    /**
     * Method description
     *
     *
     * @param pl
     *
     * @return
     */
    public RACErace_state_single getState(actorveh pl) {
        if (pl == null) {
            return null;
        }

        return (this.participants.get(pl));
    }

    /**
     * Method description
     *
     */
    public void updatePlaces() {
        Collection coll = this.participants.values();
        ArrayList lst = new ArrayList(coll);

        Collections.sort(lst);

        Iterator iter = lst.listIterator();
        int place = this.lastplace;

        while (iter.hasNext()) {
            RACErace_state_single data = (RACErace_state_single) iter.next();

            if (data.isFinished()) {
                continue;
            }

            data.setPlace(place++);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getTimeElapsed() {
        return this.timeElapsed;
    }

    /**
     * Method description
     *
     *
     * @param timeElapsed
     */
    public void setTimeElapsed(double timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isAnyonfinish() {
        return this.anyonfinish;
    }

    /**
     * Method description
     *
     *
     * @param anyonfinish
     */
    public void setAnyonfinish(boolean anyonfinish) {
        this.anyonfinish = anyonfinish;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getLastplace() {
        return this.lastplace;
    }

    /**
     * Method description
     *
     *
     * @param lastplace
     */
    public void setLastplace(int lastplace) {
        this.lastplace = lastplace;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getStatesucceded() {
        return this.statesucceded;
    }

    /**
     * Method description
     *
     *
     * @param statesucceded
     */
    public void setStatesucceded(int statesucceded) {
        this.statesucceded = statesucceded;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public HashMap<actorveh, RACErace_state_single> getParticipantsAllData() {
        return this.participants;
    }

    /**
     * Method description
     *
     *
     * @param data
     */
    public void setParticipantsAllData(HashMap<actorveh, RACErace_state_single> data) {
        this.participants = data;
    }
}


//~ Formatted in DD Std on 13/08/28
