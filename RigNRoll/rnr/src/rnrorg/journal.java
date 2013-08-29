/*
 * @(#)journal.java   13/08/26
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


package rnr.src.rnrorg;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menuscript.HeadUpDisplay;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class journal {
    private static journal JOURNAL = null;
    private ArrayList<journable> alljournalelements;
    private final ArrayList<journable> activeJournalElements;

    private journal() {
        this.alljournalelements = new ArrayList();
        this.activeJournalElements = new ArrayList();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static journal getInstance() {
        if (null == JOURNAL) {
            JOURNAL = new journal();
        }

        return JOURNAL;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        JOURNAL = null;
    }

    /**
     * Method description
     *
     */
    public void resortactiveNotes() {
        for (journable jou : this.alljournalelements) {
            if ((jou.isQuestion()) && (!(this.activeJournalElements.contains(jou)))) {
                this.activeJournalElements.add(jou);
            }
        }

        HeadUpDisplay.updateUnread();
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void add(journable val) {
        this.alljournalelements.add(val);

        if (val.isQuestion()) {
            this.activeJournalElements.add(val);
            HeadUpDisplay.updateUnread();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean hasUnread() {
        return (!(this.activeJournalElements.isEmpty()));
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public static boolean isUnreadMission(String name) {
        for (journable j : getInstance().activeJournalElements) {
            if (name.compareTo(j.getMissionName()) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method description
     *
     */
    public void updateActiveNotes() {
        boolean make_changes = false;
        ArrayList toclean = new ArrayList();

        for (journable elem : this.activeJournalElements) {
            if (!(elem.isQuestion())) {
                make_changes = true;
                toclean.add(elem);
            }
        }

        this.activeJournalElements.removeAll(toclean);

        if (make_changes) {
            HeadUpDisplay.updateUnread();
        }
    }

    /**
     * Method description
     *
     */
    public void declineAll() {
        for (journable elem : this.activeJournalElements) {
            if (elem.isQuestion()) {
                elem.decline();
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int journalSize() {
        return this.alljournalelements.size();
    }

    /**
     * Method description
     *
     *
     * @param index
     *
     * @return
     */
    public journable get(int index) {
        if (this.alljournalelements.isEmpty()) {
            return null;
        }

        if ((index < 0) || (index >= this.alljournalelements.size())) {
            return null;
        }

        return (this.alljournalelements.get(index));
    }

    /**
     * Method description
     *
     *
     * @param obj
     */
    public void remove(journable obj) {
        for (int i = 0; i < this.alljournalelements.size(); ++i) {
            if (this.alljournalelements.get(i).equals(obj)) {
                this.alljournalelements.remove(i);

                return;
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<journable> getAlljournalelements() {
        return this.alljournalelements;
    }

    /**
     * Method description
     *
     *
     * @param alljournalelements
     */
    public void setAlljournalelements(ArrayList<journable> alljournalelements) {
        this.alljournalelements = alljournalelements;
    }
}


//~ Formatted in DD Std on 13/08/26
