/*
 * @(#)PhasedQuest.java   13/08/27
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

final class PhasedQuest {
    private LinkedList<ObjectProperties> actionsOnStart;
    private LinkedList<ObjectProperties> actionsOnEnd;
    private String name;
    private String missionPoint;
    private String organizer;
    private final LinkedList<QuestPhase> phases;

    PhasedQuest() {
        this.actionsOnStart = new LinkedList<ObjectProperties>();
        this.actionsOnEnd = new LinkedList<ObjectProperties>();
        this.name = null;
        this.missionPoint = null;
        this.organizer = null;
        this.phases = new LinkedList<QuestPhase>();
    }

    String getOrganizerRef() {
        return this.organizer;
    }

    LinkedList<QuestPhase> getPhases() {
        return this.phases;
    }

    LinkedList<ObjectProperties> getActionsOnStart() {
        return this.actionsOnStart;
    }

    LinkedList<ObjectProperties> getActionsOnEnd() {
        return this.actionsOnEnd;
    }

    String getMissionPoint() {
        return this.missionPoint;
    }

    String getName() {
        return this.name;
    }

    void setOrganizerRef(String organizer) {
        this.organizer = organizer;
    }

    void setName(String name) {
        this.name = name;
    }

    void setMissionPoint(String missionPoint) {
        this.missionPoint = missionPoint;
    }

    void addPhase(QuestPhase toAdd) {
        if (null == toAdd) {
            return;
        }

        this.phases.add(toAdd);
    }

    void setActionOnEnd(LinkedList<ObjectProperties> actionList) {
        if (null == actionList) {
            throw new IllegalArgumentException("actionList must be non-null references");
        }

        this.actionsOnEnd = actionList;
    }

    void setActionOnStart(LinkedList<ObjectProperties> actionList) {
        if (null == actionList) {
            throw new IllegalArgumentException("actionList must be non-null reference");
        }

        this.actionsOnStart = actionList;
    }
}


//~ Formatted in DD Std on 13/08/27
