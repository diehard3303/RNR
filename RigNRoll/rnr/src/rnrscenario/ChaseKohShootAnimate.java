/*
 * @(#)ChaseKohShootAnimate.java   13/08/26
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

import rnr.src.players.Crew;
import rnr.src.players.ScenarioPersonPassanger;
import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.SCRuniperson;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class ChaseKohShootAnimate extends ShootChasing {
    private static final String[] mACTOR_NAME = { "ONTANELIO_Slow", "HKMP7PDW", "Model_shoot" };
    private static final String[] mTRACKS = { "02070shootbegin", "02070shootfinish", "02070shootcycle" };
    private final aiplayer[] aux_player = new aiplayer[2];

    /**
     * Constructs ...
     *
     *
     * @param chase
     * @param car_running
     * @param isFromLoad
     */
    public ChaseKohShootAnimate(IShootChasing chase, actorveh car_running, boolean isFromLoad) {
        super(chase, Crew.getIgrokCar(), car_running, mACTOR_NAME, mTRACKS);
        init(isFromLoad);
    }

    @Override
    protected void prepareForScenes(boolean isFromLoad) {
        Crew.getIgrokCar().registerCar("gepard");

        aiplayer person_player = new aiplayer("SC_ONTANIELOLOW");

        person_player.setModelCreator(new ScenarioPersonPassanger(), "shooter");

        aiplayer gun_player = new aiplayer("SC_DAKOTAGUN");

        gun_player.setModelCreator(new ScenarioPersonPassanger(), "gun");

        if (!(isFromLoad)) {
            person_player.bePassangerOfCar(Crew.getIgrokCar());
            gun_player.bePassangerOfCar_simple(Crew.getIgrokCar());
        }

        this.aux_player[0] = person_player;
        this.aux_player[1] = gun_player;
    }

    @Override
    protected void prepareForFinish() {
        Crew.getIgrokCar().dropOffPassangersButDriver();

        for (int i = 0; i < this.aux_player.length; ++i) {
            this.aux_player[i].delete();
            this.aux_player[i] = null;
        }
    }

    @Override
    protected SCRuniperson getShoterModel() {
        return this.aux_player[0].getModel();
    }

    @Override
    protected SCRuniperson getGunModel() {
        return this.aux_player[1].getModel();
    }
}


//~ Formatted in DD Std on 13/08/26
