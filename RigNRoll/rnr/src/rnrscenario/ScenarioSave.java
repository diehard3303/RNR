/*
 * @(#)ScenarioSave.java   13/08/28
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
import rnr.src.rnrscenario.controllers.chaseTopo;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class ScenarioSave implements StageChangedListener {
    private static ScenarioSave instance = null;
    private final sctaskmanager tasks_on_0sec = new sctaskmanager();
    private final sctaskmanager tasks_on_3sec = new sctaskmanager();
    private final sctaskmanager tasks_on_60sec = new sctaskmanager();
    private final sctaskmanager tasks_on_600sec = new sctaskmanager();
    chaseTopo CHASETOPO = null;
    Tutorial tutor = new Tutorial();

    /**
     * Method description
     *
     *
     * @return
     */
    public static ScenarioSave getInstance() {
        if (null == instance) {
            instance = new ScenarioSave();
        }

        return instance;
    }

    /**
     * Method description
     *
     */
    public void gameDeinitLaunched() {
        this.tasks_on_0sec.gameDeinitLaunched();
        this.tasks_on_3sec.gameDeinitLaunched();
        this.tasks_on_60sec.gameDeinitLaunched();
        this.tasks_on_600sec.gameDeinitLaunched();
    }

    void run_0() {
        this.tasks_on_0sec.run();
    }

    void run_3() {
        this.tasks_on_3sec.run();
    }

    void run_60() {
        this.tasks_on_60sec.run();
    }

    void run_600() {
        this.tasks_on_600sec.run();
    }

    void addTaskOnEveryFrame(scrun task) {
        this.tasks_on_0sec.add(task);
    }

    void addTaskOn3Seconds(scrun task) {
        this.tasks_on_3sec.add(task);
    }

    void addTaskOn60Seconds(scrun task) {
        this.tasks_on_60sec.add(task);
    }

    void addTaskOn600Seconds(scrun task) {
        this.tasks_on_600sec.add(task);
    }

    void removeTaskOnEveryFrame(scrun task) {
        this.tasks_on_0sec.remove(task);
    }

    void removeTaskOn3Seconds(scrun task) {
        this.tasks_on_3sec.remove(task);
    }

    void removeTaskOn60Seconds(scrun task) {
        this.tasks_on_60sec.remove(task);
    }

    void removeTaskOn600Seconds(scrun task) {
        this.tasks_on_600sec.remove(task);
    }

    /**
     * Method description
     *
     *
     * @param object
     */
    public void setChaseTopo(chaseTopo object) {
        this.CHASETOPO = object;
    }

    /**
     * Method description
     *
     *
     * @param scenarioStage
     */
    @Override
    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        this.tasks_on_0sec.scenarioCheckPointReached(scenarioStage);
        this.tasks_on_3sec.scenarioCheckPointReached(scenarioStage);
        this.tasks_on_60sec.scenarioCheckPointReached(scenarioStage);
        this.tasks_on_600sec.scenarioCheckPointReached(scenarioStage);

        if ((null == this.CHASETOPO)
                || (!(ScenarioGarbageFinder.isExpired("ScenarioSave", this.CHASETOPO, scenarioStage)))) {
            return;
        }

        this.CHASETOPO.finishImmediately();
        this.CHASETOPO = null;
    }
}


//~ Formatted in DD Std on 13/08/28
