/*
 * @(#)PickUpEventManager.java   13/08/26
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

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class PickUpEventManager {
    private static PickUpEventManager instance = null;
    private final List<IPickUpEventListener> m_listeners;
    private final List<IPickUpEventListener> m_listenersToRemove;
    private final List<IPickUpEventListener> m_listenersToAdd;
    private boolean m_onEventProcess;

    /**
     * Constructs ...
     *
     */
    public PickUpEventManager() {
        this.m_listeners = new ArrayList();
        this.m_listenersToRemove = new ArrayList();
        this.m_listenersToAdd = new ArrayList();
        this.m_onEventProcess = false;
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public static void addListener(IPickUpEventListener listener) {
        if (instance == null) {
            return;
        }

        if (!(instance.m_onEventProcess)) {
            instance.m_listeners.add(listener);
        } else {
            instance.m_listenersToAdd.add(listener);
        }
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public static void removeListener(IPickUpEventListener listener) {
        if (instance == null) {
            return;
        }

        if (!(instance.m_onEventProcess)) {
            instance.m_listeners.remove(listener);
        } else {
            instance.m_listenersToRemove.add(listener);
        }
    }

    private void makePickUpEventOnMission(String missionName) {
        this.m_onEventProcess = true;

        for (IPickUpEventListener listener : this.m_listeners) {
            listener.onPickUpevent(missionName);
        }

        this.m_onEventProcess = false;
        this.m_listeners.addAll(this.m_listenersToAdd);
        this.m_listenersToAdd.clear();
        this.m_listeners.removeAll(this.m_listenersToRemove);
        this.m_listenersToRemove.clear();
    }

    /**
     * Method description
     *
     *
     * @param missionName
     */
    public static void pickUpEventOnMission(String missionName) {
        if (instance == null) {
            return;
        }

        instance.makePickUpEventOnMission(missionName);
    }

    /**
     * Method description
     *
     */
    public static void init() {
        instance = new PickUpEventManager();
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        instance = null;
    }
}


//~ Formatted in DD Std on 13/08/26
