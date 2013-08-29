/*
 * @(#)AndEventChecker.java   13/08/27
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


package rnr.src.scriptEvents;

//~--- JDK imports ------------------------------------------------------------

import java.util.LinkedList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public final class AndEventChecker implements EventChecker {
    static final long serialVersionUID = 0L;
    private final LinkedList<EventChecker> andCheckersList;
    private ScriptEvent lastSuccessfullyChecked;

    /**
     * Constructs ...
     *
     */
    public AndEventChecker() {
        this.andCheckersList = new LinkedList<EventChecker>();
        this.lastSuccessfullyChecked = null;
    }

    /**
     * Method description
     *
     *
     * @param andChecker
     */
    public void addAndChecker(EventChecker andChecker) {
        if (null == andChecker) {
            return;
        }

        this.andCheckersList.add(andChecker);
    }

    /**
     * Method description
     *
     */
    @Override
    public void deactivateChecker() {
        for (EventChecker checker : this.andCheckersList) {
            checker.deactivateChecker();
        }
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     *
     * @return
     */
    @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        for (EventChecker checker : this.andCheckersList) {
            if (!(checker.checkEvent(eventTuple))) {
                return false;
            }

            this.lastSuccessfullyChecked = checker.lastPossetiveChecked();
        }

        return true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastSuccessfullyChecked;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public List<ScriptEvent> getExpectantEvent() {
        List out = new LinkedList();

        for (EventChecker checker : this.andCheckersList) {
            List expected = checker.getExpectantEvent();

            if ((null != expected) && (!(expected.isEmpty()))) {
                out.addAll(expected);
            }
        }

        return out;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String isValid() {
        return null;
    }
}


//~ Formatted in DD Std on 13/08/27
