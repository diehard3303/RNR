/*
 * @(#)IsoQuest.java   13/08/27
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

//~--- JDK imports ------------------------------------------------------------

import java.util.LinkedList;

final class IsoQuest {
    private final LinkedList<IsoQuestItemTask> tasks;
    private String name;
    private boolean finishOnLastPhase;

    IsoQuest() {
        this.tasks = new LinkedList<IsoQuestItemTask>();
        this.name = null;
        this.finishOnLastPhase = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isFinishOnLastPhase() {
        return this.finishOnLastPhase;
    }

    /**
     * Method description
     *
     *
     * @param finishOnLastPhase
     */
    public void setFinishOnLastPhase(boolean finishOnLastPhase) {
        this.finishOnLastPhase = finishOnLastPhase;
    }

    /**
     * Method description
     *
     *
     * @param task
     */
    public void addTask(IsoQuestItemTask task) {
        if (null == task) {
            return;
        }

        this.tasks.add(task);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public IsoQuestItemTask[] getTasks() {
        IsoQuestItemTask[] result = new IsoQuestItemTask[this.tasks.size()];

        return (this.tasks.toArray(result));
    }
}


//~ Formatted in DD Std on 13/08/27
