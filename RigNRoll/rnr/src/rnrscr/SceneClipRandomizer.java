/*
 * @(#)SceneClipRandomizer.java   13/08/28
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


package rnr.src.rnrscr;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrloggers.MissionsLogger;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class SceneClipRandomizer {
    private static final String[] METHODS = { "setClipActive", "setClipPassive" };
    private static final int ACTIVE = 0;
    private static final int PASSIVE = 1;
    private final String m_sceneName;
    private final Clips[] m_clips;

    SceneClipRandomizer(String sceneName, Clips[] clips) {
        this.m_sceneName = sceneName;
        this.m_clips = clips;
    }

    /**
     * Method description
     *
     *
     * @param flags
     * @param pool
     * @param preset
     *
     * @return
     */
    public long createScene(int flags, Vector<?> pool, Object preset) {
        long scene = scenetrack.CreateSceneXMLPool(this.m_sceneName, flags, pool, preset);

        if (scene == 0L) {
            MissionsLogger.getInstance().doLog("SceneClipRandomizer, createScene " + this.m_sceneName, Level.INFO);
        }

        for (Clips clip : this.m_clips) {
            if (clip.m_clipNames == null) {
                continue;
            }

            if (clip.m_clipNames.length == 0) {
                continue;
            }

            int indexActive = AdvancedRandom.RandFromInreval(0, clip.m_clipNames.length - 1);

            for (int i = 0; i < clip.m_clipNames.length; ++i) {
                scenetrack.ChangeClipParam(scene, clip.m_actorName, clip.m_clipNames[i], this, (indexActive == i)
                        ? METHODS[0]
                        : METHODS[1]);
            }
        }

        return scene;
    }

    /**
     * Method description
     *
     *
     * @param pars
     */
    public void setClipActive(camscripts.trackclipparams pars) {
        pars.track_mute = false;
    }

    /**
     * Method description
     *
     *
     * @param pars
     */
    public void setClipPassive(camscripts.trackclipparams pars) {
        pars.track_mute = true;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public static class Clips {
        String m_actorName;
        String[] m_clipNames;

        Clips(String actorName, String[] clipNames) {
            this.m_actorName = actorName;
            this.m_clipNames = clipNames;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
