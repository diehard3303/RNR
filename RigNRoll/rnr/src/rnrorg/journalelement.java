/*
 * @(#)journalelement.java   13/08/26
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

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.MacroBody;
import rnr.src.rnrcore.MacroBuilder;
import rnr.src.rnrcore.Macros;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.gameDate;
import rnr.src.rnrloggers.ScriptsLogger;
import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscr.IMissionInformation;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class journalelement implements journable {
    gameDate time = null;
    CoreTime m_expirationTime = null;
    private MacroBody macroBody = null;
    private String descriptionCached = null;
    private final ArrayList<PickEventAdapter> listeners = new ArrayList();
    private boolean need_menu = true;

    /**
     * Constructs ...
     *
     */
    public journalelement() {
        this.macroBody = MacroBuilder.makeSimpleMacroBody("no description");
    }

    /**
     * Constructs ...
     *
     *
     * @param body
     */
    public journalelement(MacroBody body) {
        this.macroBody = body;
    }

    /**
     * Constructs ...
     *
     *
     * @param description
     * @param macroces
     */
    public journalelement(String description, List<Macros> macroces) {
        this.macroBody = MacroBuilder.makeMacroBody("journal", description, macroces);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean needMenu() {
        return this.need_menu;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMissionName() {
        if (this.listeners.isEmpty()) {
            return "";
        }

        return this.listeners.get(0).getMissionName();
    }

    /**
     * Method description
     *
     *
     * @param time
     */
    @Override
    public void setDeactivationTime(CoreTime time) {
        this.m_expirationTime = new CoreTime(time);
        new Deactivator();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public CoreTime getDeactivationTime() {
        return this.m_expirationTime;
    }

    /**
     * Method description
     *
     */
    @Override
    public void decline() {
        if (null != this.m_expirationTime) {
            this.m_expirationTime = new CoreTime();
        }
    }

    /**
     * Method description
     *
     *
     * @param time
     */
    @Override
    public void setTime(CoreTime time) {
        if (null != time) {
            this.time = new CoreTime(time);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int gHour() {
        return this.time.gHour();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int gMinute() {
        return this.time.gMinute();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int gMonth() {
        return this.time.gMonth();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int gYear() {
        return this.time.gYear();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int gDate() {
        return this.time.gDate();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public CoreTime getTime() {
        if (this.time instanceof CoreTime) {
            return ((CoreTime) this.time);
        }

        return null;
    }

    /**
     * Method description
     *
     */
    @Override
    public void answerNO() {
        for (PickEventAdapter lst : this.listeners) {
            lst.onSayNo();
        }

        this.listeners.clear();
    }

    /**
     * Method description
     *
     */
    @Override
    public void answerYES() {
        for (PickEventAdapter lst : this.listeners) {
            lst.onSayYes();
        }

        this.listeners.clear();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isQuestion() {
        if (this.listeners.isEmpty()) {
            return false;
        }

        for (PickEventAdapter lst : this.listeners) {
            if (lst.isInformationActive()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public final String description() {
        if (null == this.descriptionCached) {
            this.descriptionCached = this.macroBody.makeString();
        }

        return this.descriptionCached;
    }

    /**
     * Method description
     *
     */
    @Override
    public void start() {
        this.time = new CoreTime();
        addToList();
        ScriptsLogger.getInstance().log(Level.INFO, 0, "journal record: " + this.macroBody.makeString());
    }

    /**
     * Method description
     *
     */
    @Override
    public void deleteFromList() {}

    /**
     * Method description
     *
     */
    @Override
    public void addToList() {
        journal.getInstance().add(this);
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    @Override
    public void makeQuestionFor(JournalActiveListener listener) {
        this.listeners.add(new PickEventAdapter(listener));

        MissionInfo mission =
            MissionSystemInitializer.getMissionsManager().getMissionInfo(listener.getMissionInfo().getMissionName());

        this.need_menu = mission.hasAcceptAction();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public List<String> getListenersResources() {
        ArrayList result = new ArrayList();

        for (PickEventAdapter adapter : this.listeners) {
            result.add(adapter.m_listener.getResource());
        }

        return result;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public MacroBody getMacroBody() {
        return this.macroBody;
    }

    class Deactivator extends TypicalAnm {
        Deactivator() {
            eng.CreateInfinitScriptAnimation(this);
        }

        /**
         * Method description
         *
         *
         * @param dt
         *
         * @return
         */
        @Override
        public boolean animaterun(double dt) {
            if ((journalelement.this.listeners.isEmpty()) || (null == journalelement.this.m_expirationTime)) {
                return true;
            }

            CoreTime current = new CoreTime();

            if (current.moreThan(journalelement.this.m_expirationTime) >= 0) {
                journalelement.this.answerNO();
                journal.getInstance().updateActiveNotes();

                return true;
            }

            return false;
        }
    }


    static class PickEventAdapter implements IPickUpEventListener {
        private final JournalActiveListener m_listener;

        PickEventAdapter(JournalActiveListener listener) {
            assert(listener != null);
            this.m_listener = listener;
            PickUpEventManager.addListener(this);
        }

        /**
         * Method description
         *
         *
         * @param missionName
         */
        @Override
        public void onPickUpevent(String missionName) {
            assert(missionName != null);

            IMissionInformation missionInfo = this.m_listener.getMissionInfo();

            if ((missionInfo != null) && (missionInfo.getMissionName().compareTo(missionName) == 0)) {
                onSayYes();
            }
        }

        /**
         * Method description
         *
         */
        public void onSayYes() {
            this.m_listener.onAnswerYes();
            PickUpEventManager.removeListener(this);
            journal.getInstance().updateActiveNotes();
        }

        /**
         * Method description
         *
         */
        public void onSayNo() {
            this.m_listener.onAnswerNo();
            PickUpEventManager.removeListener(this);
            journal.getInstance().updateActiveNotes();
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isInformationActive() {
            IMissionInformation missionInfo = this.m_listener.getMissionInfo();

            if (missionInfo == null) {
                return false;
            }

            return missionInfo.hasQuestion();
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public String getMissionName() {
            IMissionInformation missionInfo = this.m_listener.getMissionInfo();

            if (missionInfo == null) {
                return "";
            }

            return missionInfo.getMissionName();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
