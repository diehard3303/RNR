/*
 * @(#)QuestPhase.java   13/08/27
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

final class QuestPhase implements Comparable<Object> {
    private int nom = 0;
    private LinkedList<ObjectProperties> actionList = null;
    private String missionPoint = null;

    QuestPhase(int nom, String missionPoint) {
        this.nom = nom;
        this.missionPoint = missionPoint;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getMissionPoint() {
        return this.missionPoint;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final int getNom() {
        return this.nom;
    }

    /**
     * Method description
     *
     *
     * @param o
     *
     * @return
     */
    @Override
    public int compareTo(Object o) {
        QuestPhase other = (QuestPhase) o;

        if (this.nom < other.nom) {
            return -1;
        }

        if (this.nom > other.nom) {
            return 1;
        }

        return 0;
    }

    void setActionList(LinkedList<ObjectProperties> list) {
        if (null == list) {
            return;
        }

        this.actionList = list;
    }

    final LinkedList<ObjectProperties> getActionList() {
        return this.actionList;
    }
}


//~ Formatted in DD Std on 13/08/27