/*
 * @(#)demointro.java   13/08/28
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
import rnr.src.players.actorveh;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.rnrscr.specobjects;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class demointro extends sctask {

    /** Field description */
    public static final String Tutorial1Text = "TUTORIAL - WAREHOUSE";

    /** Field description */
    public static final String Tutorial2Text = "TUTORIAL - TRUCKSTOP";
    String Enter_for_tutorial = "Exit_to_OV_SB_TS";
    int count_runs = 0;
    int count_2 = 0;
    String Tutorial1TextOld =
        "To take your first order, go to the nearest base. Be guided by the road signs and the map. To invoke the map press <Tab>. To change the map scale press <Tab> once again. Choose the order and go to the base in the specified destination. To receive the maximal profit you have to overtake all competitors (they will have trailers of the same type as you). To be recovered on the move, press <Ctrl 9>, to refuel and be repaired - press <Backspace>.";
    String Tutorial2TextOld =
        "You can drive into the truck stop (the exit is to the right from you) to refuel, repair the truck, to learn the news or just to have a rest. - To refuel, drive inside of the gas station and press <F2> after the prompt appeared; - To repair the truck, approach the entrance of the repair station and press <F2> after the prompt appeared; - To learn the news approach the bar entrance and press <F2> after the prompt appeared; - To have a rest, approach the motel and press <F2> after the prompt appeared;";
    vectorJ vEnter_for_tutorial;

    demointro() {
        super(3, false);
        this.vEnter_for_tutorial = new vectorJ(1678.0D, -23737.0D, 21.0D);
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        this.count_runs += 1;

        if (this.count_runs == 2) {
            createinfoMenu();
        }

        this.count_2 += 1;

        if (this.count_2 != 2) {
            return;
        }

        this.count_2 = 0;

        specobjects so = specobjects.getInstance();
        cSpecObjects obj = so.GetNearestTruckStopEnter();

        if ((obj == null) || (obj.name.compareToIgnoreCase(this.Enter_for_tutorial) != 0)) {
            return;
        }

        vectorJ pPlayer = Crew.getIgrokCar().gPosition();

        if (pPlayer.len2(obj.position) >= 22500.0D) {
            return;
        }

        vectorJ dir = new vectorJ(0.949D, -0.31D, 0.054D);
        vectorJ pPlayerDir = Crew.getIgrokCar().gDir();

        if (dir.dot(pPlayerDir) <= 0.5D) {
            return;
        }

        createTruckStopinfoMenu();
        finish();
    }

    void createinfoMenu() {
        messgroup mess = new messgroup("TUTORIAL - WAREHOUSE", true, true, 30.0D, "ESC", true, false);

        mess.start();
    }

    void createTruckStopinfoMenu() {
        messgroup mess = new messgroup("TUTORIAL - TRUCKSTOP", true, true, 30.0D, "ESC", true, false);

        mess.start();
    }

    void createJournalnote1() {}

    void createJournalnote2() {}
}


//~ Formatted in DD Std on 13/08/28
