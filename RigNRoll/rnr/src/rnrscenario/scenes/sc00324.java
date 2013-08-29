/*
 * @(#)sc00324.java   13/08/28
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


package rnr.src.rnrscenario.scenes;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menuscript.AnswerMenu;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.loc;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscr.IDialogListener;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
@ScenarioClass(
    scenarioStage = 2,
    fieldWithDesiredStage = ""
)
public final class sc00324 extends stage implements IDialogListener {
    private static final String[] SCENES = { "00324", "00324_menu", "00324_accept", "00324_reject" };
    private static final int SCENE_START = 0;
    private static final int SCENE_MENU = 1;
    private static final int SCENE_ACCEPT = 2;
    private static final int SCENE_REJECT = 3;
    private static final String METHOD_TO_CALL_WHEN_CHOOSE_MENU_CLOSED = "menuClosed";
    private static final String msg_ok = "Scenario Answer Ok";
    private static final String msg_cancel = "Scenario Answer Cancel";
    private static final String[] ANSWERS = { loc.getDialogName("NicA_SC00324_05"),
            loc.getDialogName("NicA_SC00324_06") };
    private static final int YES = 0;
    private static final int NO = 1;
    private static boolean m_isDebugOn = false;
    private static boolean m_debugAnswer = false;
    private final Object latch = new Object();
    private boolean f_menu_closed = false;
    private int i_resultmenu = 1;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public sc00324(Object monitor) {
        super(monitor, "sc00324");
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setDebugUnswer(boolean value) {
        m_isDebugOn = true;
        m_debugAnswer = value;
    }

    /**
     * Method description
     *
     */
    public final void menuClosed() {
        synchronized (this.latch) {
            this.f_menu_closed = true;
            this.latch.notify();
        }
    }

    /*
     *  (non-Javadoc)
     * @see rnr.src.rnrscenario.stage#playSceneBody(rnr.src.rnrscenario.cScriptFuncs)
     */
    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.doWide(true);
        eng.disableControl();
        eng.startMangedFadeAnimation();
        rnr.src.rnrscenario.tech.Helper.makeComeOut();

        trackscripts trs = new trackscripts(getSyncMonitor());

        trs.PlaySceneXMLThreaded(SCENES[0], true);

        long sc = scenetrack.CreateSceneXML(SCENES[1], 5, null);

        EventsControllerHelper.getInstance().addMessageListener(this, "menuClosed", "Scenario Answer Recived");
        eng.lock();

        if (m_isDebugOn) {
            this.f_menu_closed = true;
            m_isDebugOn = false;
            this.i_resultmenu = ((m_debugAnswer)
                                 ? 0
                                 : 1);
        } else {
            AnswerMenu.createAnswerMenu(ANSWERS, this);
        }

        eng.unlock();

        if (!(this.f_menu_closed)) {
            try {
                this.latch.wait();
            } catch (InterruptedException e) {
                eng.writeLog("Error in sc00324.playSceneBody interrupted while waiting for user input.");
            } finally {}
        }

        scenetrack.DeleteScene(sc);

        if (this.i_resultmenu == 0) {
            eng.lock();
            EventsControllerHelper.messageEventHappened("Scenario Answer Ok");
            eng.unlock();
            trs.PlaySceneXMLThreaded(SCENES[2], false);
        } else {
            eng.lock();
            EventsControllerHelper.messageEventHappened("Scenario Answer Cancel");
            eng.unlock();
            trs.PlaySceneXMLThreaded(SCENES[3], false);
        }

        eng.startMangedFadeAnimation();
        EventsControllerHelper.getInstance().removeMessageListener(this, "menuClosed", "Scenario Answer Recived");
        rnr.src.rnrscenario.tech.Helper.makeComeInAndLeaveParking();
        eng.doWide(false);
        eng.enableControl();
        eng.lock();
        rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
        eng.unlock();
    }

    void waitFor(int timemillesec) throws InterruptedException {
        Thread.sleep(timemillesec);
    }

    /**
     * Method description
     *
     *
     * @param dialog_name
     */
    @Override
    public void onAppear(String dialog_name) {}

    /**
     * Method description
     *
     *
     * @param dialog_name
     */
    @Override
    public void onNo(String dialog_name) {
        menuClosed();
        this.i_resultmenu = 1;
    }

    /**
     * Method description
     *
     *
     * @param dialog_name
     */
    @Override
    public void onYes(String dialog_name) {
        menuClosed();
        this.i_resultmenu = 0;
    }
}


//~ Formatted in DD Std on 13/08/28
