/*
 * @(#)stage.java   13/08/26
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

import rnr.src.rnrcore.eng;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.MessageEvent;
import rnr.src.scriptEvents.ScriptEvent;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public abstract class stage {
    private static final Object interruptionLatch = new Object();
    private static boolean interrupted = false;
    private static boolean useDebugConditions = false;
    private final Object syncMonitor;
    private final String messageOnFinish;

    /**
     * Constructs ...
     *
     *
     * @param monitor
     * @param stageSymbolicName
     */
    public stage(Object monitor, String stageSymbolicName) {
        if ((null == monitor) || (null == stageSymbolicName)) {
            throw new IllegalArgumentException("monitor or stageSymbolicName is null");
        }

        this.syncMonitor = monitor;
        this.messageOnFinish = "finished scene " + stageSymbolicName;
    }

    protected static boolean isInterrupted() {
        return interrupted;
    }

    /**
     * Method description
     *
     */
    public static void interrupt() {
        synchronized (interruptionLatch) {
            interrupted = true;
        }
    }

    /**
     * Method description
     *
     */
    public static void resetInterruptionStatus() {
        synchronized (interruptionLatch) {
            interrupted = false;
        }
    }

    /**
     * Method description
     *
     */
    public static void performDebugPrePostConditions() {
        useDebugConditions = true;
    }

    /**
     * Method description
     *
     */
    public static void resetDebugPrePostConditions() {
        useDebugConditions = false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Object getSyncMonitor() {
        return this.syncMonitor;
    }

    protected abstract void playSceneBody(cScriptFuncs paramcScriptFuncs);

    protected void debugSetupPrecondition() {}

    protected void debugSetupPostcondition() {}

    /**
     * Method description
     *
     *
     * @param automat
     */
    public final void playScene(cScriptFuncs automat) {
        if (useDebugConditions) {
            debugSetupPrecondition();
        }

        try {
            eng.disableEscKeyScenesSkip();
            playSceneBody(automat);
        } finally {
            eng.enableEscKeyScenesSkip();
            eng.blockEscapeMenu();
        }

        if (useDebugConditions) {
            debugSetupPostcondition();
        }

        if (interrupted) {
            return;
        }

        eng.lock();

        synchronized (interruptionLatch) {
            if (!(interrupted)) {
                EventsController.getInstance().eventHappen(new ScriptEvent[] {
                    new MessageEvent(this.messageOnFinish) });
            }
        }

        eng.unlock();
    }
}


//~ Formatted in DD Std on 13/08/26
