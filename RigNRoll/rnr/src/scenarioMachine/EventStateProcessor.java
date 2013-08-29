/*
 * @(#)EventStateProcessor.java   13/08/27
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


package rnr.src.scenarioMachine;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.scriptActions.ScriptAction;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.LinkedList;
import java.util.List;

/**
 * Interface description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public abstract interface EventStateProcessor {

    /**
     * Method description
     *
     *
     * @param paramList
     * @param paramLinkedList
     * @param paramFiniteState
     *
     * @return
     */
    public abstract FiniteState processEvent(List<ScriptEvent> paramList, LinkedList<FiniteState> paramLinkedList,
            FiniteState paramFiniteState);

    /**
     * Method description
     *
     *
     * @param paramList
     *
     * @return
     */
    public abstract List<ScriptAction> getAllAvalibleActions(List<?> paramList);

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract List<List<ScriptEvent>> getAllAvalibleEvents();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract List<Integer> getDataToSave();

    /**
     * Method description
     *
     *
     * @param paramList
     */
    public abstract void restoreFromData(List<Integer> paramList);
}


//~ Formatted in DD Std on 13/08/27
