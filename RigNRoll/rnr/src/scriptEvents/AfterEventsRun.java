/*
 * @(#)AfterEventsRun.java   13/08/28
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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public class AfterEventsRun {
    private static AfterEventsRun instance = null;
    private final HashMap<Integer, List<IAfterEventsRun>> runs = new HashMap();
    private int session = 0;

    /**
     * Method description
     *
     *
     * @return
     */
    public static AfterEventsRun getInstance() {
        if (null == instance) {
            instance = new AfterEventsRun();
        }

        return instance;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        instance = null;
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addListener(IAfterEventsRun listener) {
        if (EventsController.getInstance().isBussy()) {
            List currentSessionList = null;

            if (this.runs.containsKey(Integer.valueOf(this.session))) {
                currentSessionList = this.runs.get(Integer.valueOf(this.session));
            } else {
                currentSessionList = new LinkedList();
                this.runs.put(Integer.valueOf(this.session), currentSessionList);
            }

            currentSessionList.add(listener);
        } else {
            listener.run();
        }
    }

    /**
     * Method description
     *
     */
    public void run() {
        this.session += 1;
        runSession(this.session - 1);
        this.session -= 1;
    }

    private void runSession(int current_session) {
        if (this.runs.containsKey(Integer.valueOf(current_session))) {
            List<IAfterEventsRun> items = this.runs.get(Integer.valueOf(current_session));

            for (IAfterEventsRun listener : items) {
                listener.run();
            }

            items.clear();
        }
    }
}


//~ Formatted in DD Std on 13/08/28
