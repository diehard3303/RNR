/*
 * @(#)sctaskmanager.java   13/08/28
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


package rnr.src.rnrscenario;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscenario.consistency.ScenarioGarbageFinder;
import rnr.src.rnrscenario.consistency.ScenarioStage;
import rnr.src.rnrscenario.consistency.StageChangedListener;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class sctaskmanager implements StageChangedListener {
    private final ArrayList<scrun> tasks;
    private final ArrayList<scrun> new_tasks;
    private ScenarioStage scenarioStageChange;
    private boolean m_isRunning;
    private boolean _toprint;

    /**
     * Constructs ...
     *
     */
    public sctaskmanager() {
        this.tasks = new ArrayList();
        this.new_tasks = new ArrayList();
        this.scenarioStageChange = null;
        this.m_isRunning = false;
        this._toprint = true;
    }

    /**
     * Method description
     *
     */
    public void gameDeinitLaunched() {
        clearTasks(this.tasks);
        clearTasks(this.new_tasks);
    }

    private static void clearTasks(List<scrun> tasks) {
        for (Iterator taskIterator = tasks.iterator(); taskIterator.hasNext(); ) {
            scrun task = (scrun) taskIterator.next();

            if ((null != task) && (!(task.couldSurviveDuringGameDeinit()))) {
                taskIterator.remove();
            }
        }
    }

    /**
     * Method description
     *
     */
    public void run() {
        this.m_isRunning = true;

        boolean rem = this._toprint;

        this._toprint = (!(this.tasks.isEmpty()));

        for (scrun task : this.tasks) {
            if (task.marked()) {
                continue;
            }

            task.mark(true);

            if ((task.started()) && (!(task.finished()))) {
                task.run();
            }
        }

        if (!(this.new_tasks.isEmpty())) {
            this.tasks.addAll(this.new_tasks);
            this.new_tasks.clear();
            this.m_isRunning = false;
            run();

            return;
        }

        Iterator iter = this.tasks.iterator();

        while (iter.hasNext()) {
            scrun ob = (scrun) iter.next();

            ob.mark(false);

            if (ob.finished()) {
                iter.remove();
            }
        }

        if (null != this.scenarioStageChange) {
            ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(super.getClass().getName(), this.tasks,
                    this.scenarioStageChange);
            this.scenarioStageChange = null;
        }

        this.m_isRunning = false;
        this._toprint = rem;
    }

    /**
     * Method description
     *
     *
     * @param ob
     */
    public void add(scrun ob) {
        this.new_tasks.add(ob);
    }

    /**
     * Method description
     *
     *
     * @param ob
     */
    public void remove(scrun ob) {
        if (!(this.m_isRunning)) {
            this.tasks.remove(ob);
            this.new_tasks.remove(ob);
        } else {
            ob.finish();
        }
    }

    /**
     * Method description
     *
     */
    public void finish() {}

    /**
     * Method description
     *
     */
    public void start() {}

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean finished() {
        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean started() {
        return false;
    }

    /**
     * Method description
     *
     *
     * @param scenarioStage
     */
    @Override
    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        if (this.m_isRunning) {
            this.scenarioStageChange = scenarioStage;
        } else {
            ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(super.getClass().getName(), this.tasks, scenarioStage);
            ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(super.getClass().getName(), this.new_tasks,
                    scenarioStage);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
