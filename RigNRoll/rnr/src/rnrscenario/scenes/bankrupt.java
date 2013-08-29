/*
 * @(#)bankrupt.java   13/08/28
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

import rnr.src.menu.JavaEvents;
import rnr.src.players.Crew;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscenario.tech.SleepOnTime;
import rnr.src.rnrscr.ILeaveMenuListener;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public final class bankrupt extends stage {
    private static final String[] SCENENAME = { "bankrupt", "01190" };
    private static final int INDEX_SIMPLE_BANCRUPT = 0;
    private static final int INDEX_SCENARIO_BANCRUPT = 1;
    private static int s_chosenIndex = 0;
    private static final int SCENEFLAGS = 2;
    private final boolean w8WorldLoaded = false;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     */
    public bankrupt(Object monitor) {
        super(monitor, "bankrupt");
    }

    /**
     * Method description
     *
     */
    public static void setSimpleBankrupt() {
        s_chosenIndex = 0;
    }

    /**
     * Method description
     *
     */
    public static void setScenarioBankrupt() {
        s_chosenIndex = 1;
    }

    @Override
    protected void playSceneBody(cScriptFuncs automat) {
        eng.lock();

        String sceneName = SCENENAME[s_chosenIndex];

        eng.unlock();

        long scene = scenetrack.CreateSceneXML(sceneName, 2, null);

        eng.lock();
        new cSceneSuspendEventWaiter(scene);
        eng.unlock();

        while (!(this.w8WorldLoaded)) {
            new SleepOnTime(10);
        }

        rnr.src.rnrscenario.tech.Helper.waitGameWorldLoad();
        eng.lock();

        if (scenarioscript.script.isInstantOrder()) {
            JavaEvents.SendEvent(23, 3, this);
        } else {
            JavaEvents.SendEvent(23, 1, this);
        }

        eng.unlock();
    }

    class cSceneSuspendEventWaiter extends TypicalAnm implements ILeaveMenuListener {
        private boolean check = true;
        private boolean toFinish = false;
        private final long scene;

        cSceneSuspendEventWaiter(long paramLong) {
            this.scene = paramLong;
            event.eventObject((int) paramLong + 1, this, "event");
            eng.CreateInfinitScriptAnimation(this);
            scenetrack.UpdateSceneFlags(paramLong, 1);
        }

        /**
         * Method description
         *
         */
        public void event() {
            assert(this.check);
            this.check = false;
            scenarioscript.script.menu_bankrupt(this);
        }

        /**
         * Method description
         *
         */
        @Override
        public void menuLeaved() {
            assert(!(this.check));
            this.toFinish = true;
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
            if (this.toFinish) {
                eng.startMangedFadeAnimation();
                scenetrack.DeleteScene(this.scene);
                eng.enableControl();
                Crew.getIgrok().getModel().SetInGameWorld();
                rnr.src.rnrscr.Helper.restoreCameraToIgrokCar();
                eng.blockEscapeMenu();

                return true;
            }

            return false;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
