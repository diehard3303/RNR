/*
 * @(#)MissionPassanger.java   13/08/28
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

import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class MissionPassanger {
    private static MissionPassanger instance = null;
    private actorveh car;
    private aiplayer npc;
    private aiplayer pack;

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
     * @return
     */
    public static MissionPassanger getInstance() {
        if (null == instance) {
            instance = new MissionPassanger();
        }

        return instance;
    }

    static void start(String identitie, String person_ref_name) {
        getInstance();

        if ((instance.car != null) && (instance.npc != null)) {
            instance.npc.abondoneCar(instance.car);
        }

        instance.car = Crew.getIgrokCar();
        instance.npc = aiplayer.getRefferencedAiplayer(identitie);
        instance.npc.sPoolBased(person_ref_name);
        instance.npc.bePassangerOfCar(instance.car);
    }

    static void start(String person_ref_name) {
        getInstance();

        if ((instance.car != null) && (instance.pack != null)) {
            instance.pack.abondoneCar(instance.car);
        }

        instance.car = Crew.getIgrokCar();
        instance.pack = aiplayer.getRefferencedAiplayer(person_ref_name);
        instance.pack.sPoolBased(person_ref_name);
        instance.pack.bePackOfCar(instance.car);
    }

    static void finish(boolean pass) {
        if (pass) {
            getInstance();

            if ((instance.car != null) && (instance.npc != null)) {
                instance.npc.abondoneCar(instance.car);
            }

            instance.npc = null;

            if (instance.pack != null) {
                return;
            }

            instance.car = null;
        } else {
            getInstance();

            if ((instance.car != null) && (instance.pack != null)) {
                instance.pack.abondoneCar(instance.car);
            }

            instance.pack = null;

            if (instance.npc != null) {
                return;
            }

            instance.car = null;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public actorveh getCar() {
        return this.car;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public aiplayer getNpc() {
        return this.npc;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public aiplayer getPack() {
        return this.pack;
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public void setCar(actorveh car) {
        this.car = car;
    }

    /**
     * Method description
     *
     *
     * @param npc
     */
    public void setNpc(aiplayer npc) {
        this.npc = npc;
    }

    /**
     * Method description
     *
     *
     * @param pack
     */
    public void setPack(aiplayer pack) {
        this.pack = pack;
    }
}


//~ Formatted in DD Std on 13/08/28
