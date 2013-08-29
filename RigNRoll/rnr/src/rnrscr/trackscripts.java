/*
 * @(#)trackscripts.java   13/08/28
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

import rnr.src.rnrcore.Suspender;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrscenario.ThreadTask;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class trackscripts {
    private Suspender suspender = null;

    /**
     * Constructs ...
     *
     */
    public trackscripts() {
        this.suspender = null;
    }

    /**
     * Constructs ...
     *
     *
     * @param synchronizationMonitor
     */
    public trackscripts(Object synchronizationMonitor) {
        this.suspender = new Suspender(synchronizationMonitor);
    }

    /**
     * Method description
     *
     *
     * @param synchronizationObject
     */
    public void setMonitor(Object synchronizationObject) {
        this.suspender = new Suspender(synchronizationObject);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     *
     * @return
     */
    public long CreateSceneXML(String Scenename) {
        return scenetrack.CreateSceneXML(Scenename, 25, null);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param preset
     *
     * @return
     */
    public static long CreateSceneXML(String Scenename, Object preset) {
        return scenetrack.CreateSceneXML(Scenename, 25, preset);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param pool
     * @param preset
     *
     * @return
     */
    public static long CreateSceneXML(String Scenename, Vector<?> pool, Object preset) {
        return scenetrack.CreateSceneXMLPool(Scenename, 25, pool, preset);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param pool
     * @param preset
     *
     * @return
     */
    public static long CreateSceneXMLCycle(String Scenename, Vector<?> pool, Object preset) {
        return scenetrack.CreateSceneXMLPool(Scenename, 5, pool, preset);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param order_to_wait_other_scene_creation
     */
    public void PlaySceneXMLThreaded(String Scenename, boolean order_to_wait_other_scene_creation) {
        long sc = scenetrack.CreateSceneXML(Scenename, 1, null);

        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }

        this.suspender.suspend();
        scenetrack.DeleteScene(sc);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param order_to_wait_other_scene_creation
     * @param preset
     */
    public void PlaySceneXMLThreaded(String Scenename, boolean order_to_wait_other_scene_creation, Object preset) {
        if (eng.noNative) {
            return;
        }

        long sc = scenetrack.CreateSceneXML(Scenename, 1, preset);

        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }

        this.suspender.suspend();
        scenetrack.DeleteScene(sc);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param order_to_wait_other_scene_creation
     * @param pool
     * @param preset
     */
    public void PlaySceneXMLThreaded(String Scenename, boolean order_to_wait_other_scene_creation, Vector<?> pool,
                                     Object preset) {
        long sc = scenetrack.CreateSceneXMLPool(Scenename, 1, pool, preset);

        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }

        this.suspender.suspend();
        scenetrack.DeleteScene(sc);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param pool
     * @param preset
     *
     * @return
     */
    public long createSceneXML(String Scenename, Vector<?> pool, Object preset) {
        return scenetrack.CreateSceneXMLPool(Scenename, 8, pool, preset);
    }

    /**
     * Method description
     *
     *
     * @param sc
     * @param order_to_wait_other_scene_creation
     */
    public void PlaySceneXMLThreaded(long sc, boolean order_to_wait_other_scene_creation) {
        scenetrack.UpdateSceneFlags(sc, 1);

        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }

        this.suspender.suspend();
        scenetrack.DeleteScene(sc);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param order_to_wait_other_scene_creation
     * @param safe
     */
    public void PlaySceneXMLThreaded(String Scenename, boolean order_to_wait_other_scene_creation, ThreadTask safe) {
        eng.lock();

        long sc = scenetrack.CreateSceneXML(Scenename, 9, null);

        event.eventObject((int) sc, safe, "_resum");

        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }

        eng.unlock();
        safe._susp();
        scenetrack.DeleteScene(sc);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param order_to_wait_other_scene_creation
     * @param pool
     * @param preset
     * @param safe
     */
    public void PlaySceneXMLThreaded(String Scenename, boolean order_to_wait_other_scene_creation, Vector<?> pool,
                                     Object preset, ThreadTask safe) {
        eng.lock();

        long sc = scenetrack.CreateSceneXMLPool(Scenename, 9, pool, preset);

        event.eventObject((int) sc, safe, "_resum");

        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }

        eng.unlock();
        safe._susp();
        scenetrack.DeleteScene(sc);
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param order_to_wait_other_scene_creation
     *
     * @return
     */
    public long PlaySceneXMLThreaded_nGet(String Scenename, boolean order_to_wait_other_scene_creation) {
        long sc = scenetrack.CreateSceneXML(Scenename, 9, null);

        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }

        this.suspender.suspend();

        return sc;
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param order_to_wait_other_scene_creation
     *
     * @return
     */
    public long PlaySceneXML_nGet(String Scenename, boolean order_to_wait_other_scene_creation) {
        long sc = scenetrack.CreateSceneXML(Scenename, 9, null);

        if (order_to_wait_other_scene_creation) {
            scenetrack.Set_waittrackcreation(order_to_wait_other_scene_creation);
        }

        return sc;
    }

    /**
     * Method description
     *
     */
    public void playSceneThreaded() {
        this.suspender.suspend();
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param pool
     * @param preset
     *
     * @return
     */
    public static long initSceneXML(String Scenename, Vector<?> pool, Object preset) {
        eng.lock();

        long sc = scenetrack.CreateSceneXMLPool(Scenename, 256, pool, preset);

        eng.unlock();

        return sc;
    }

    /**
     * Method description
     *
     *
     * @param Scenename
     * @param pool
     * @param preset
     *
     * @return
     */
    public static long initSceneXMLsuspended(String Scenename, Vector<?> pool, Object preset) {
        eng.lock();

        long sc = scenetrack.CreateSceneXMLPool(Scenename, 258, pool, preset);

        eng.unlock();

        return sc;
    }

    /**
     * Method description
     *
     *
     * @param scene
     * @param safe
     */
    public static void playInitedSceneThreaded(long scene, ThreadTask safe) {
        scenetrack.UpdateSceneFlags(scene, 1);
        event.eventObject((int) scene, safe, "_resum");
        safe._susp();
    }
}


//~ Formatted in DD Std on 13/08/28
