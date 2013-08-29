/*
 * @(#)StartMissionListeners.java   13/08/28
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


package rnr.src.rnrscenario.missions;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class StartMissionListeners {
    private static StartMissionListeners instance = null;
    private final HashMap<String, IStartMissionListener> startlisteners = new HashMap<String, IStartMissionListener>();
    private final HashMap<String, IChannelEventListener> channellisteners = new HashMap<String,
                                                                                IChannelEventListener>();

    /**
     * Method description
     *
     *
     * @return
     */
    public static StartMissionListeners getInstance() {
        if (null == instance) {
            instance = new StartMissionListeners();
        }

        return instance;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        instance = null;
    }

    /**
     * Method description
     *
     *
     * @param missionName
     * @param listener
     */
    public void registerStartMissionListener(String missionName, IStartMissionListener listener) {
        this.startlisteners.put(missionName, listener);
    }

    /**
     * Method description
     *
     *
     * @param missionName
     * @param listener
     */
    public void registerChannelEventListener(String missionName, IChannelEventListener listener) {
        this.channellisteners.put(missionName, listener);
    }

    /**
     * Method description
     *
     *
     * @param missionName
     */
    public void unregisterStartMissionListener(String missionName) {
        this.startlisteners.remove(missionName);
    }

    /**
     * Method description
     *
     *
     * @param missionName
     */
    public void unregisterChannelEventListener(String missionName) {
        this.channellisteners.remove(missionName);
    }

    /**
     * Method description
     *
     *
     * @param missionName
     *
     * @return
     */
    public IStartMissionListener getStartMissionListener(String missionName) {
        if (this.startlisteners.containsKey(missionName)) {
            return (this.startlisteners.get(missionName));
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @param missionName
     *
     * @return
     */
    public IChannelEventListener getChannleEventListener(String missionName) {
        if (this.channellisteners.containsKey(missionName)) {
            return (this.channellisteners.get(missionName));
        }

        return null;
    }
}


//~ Formatted in DD Std on 13/08/28
