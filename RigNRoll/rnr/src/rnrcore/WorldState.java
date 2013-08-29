/*
 * @(#)WorldState.java   13/08/26
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


package rnr.src.rnrcore;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.JavaEvents;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class WorldState {

    /** Field description */
    public String world;

    /** Field description */
    public String state;

    /** Field description */
    public boolean result;

    WorldState(String world) {
        this.world = world;
        this.state = "";
        this.result = false;
    }

    WorldState(String world, String state) {
        this.world = world;
        this.state = state;
        this.result = false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean checkStateWorld_GameWorld() {
        return checkStateWorld("game");
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean checkStateWorld_VehicleChanged() {
        return checkStateState("changevehicle");
    }

    /**
     * Method description
     *
     *
     * @param world
     *
     * @return
     */
    public static boolean checkStateWorld(String world) {
        WorldState res = new WorldState(world);

        JavaEvents.SendEvent(42, 0, res);

        return res.result;
    }

    /**
     * Method description
     *
     *
     * @param world
     * @param state
     *
     * @return
     */
    public static boolean checkStateWorld(String world, String state) {
        WorldState res = new WorldState(world, state);

        JavaEvents.SendEvent(42, 0, res);

        return res.result;
    }

    /**
     * Method description
     *
     *
     * @param state
     *
     * @return
     */
    public static boolean checkStateState(String state) {
        WorldState res = new WorldState("", state);

        JavaEvents.SendEvent(42, 0, res);

        return res.result;
    }
}


//~ Formatted in DD Std on 13/08/26
