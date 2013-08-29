/*
 * @(#)STOHistory.java   13/08/28
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
import rnr.src.rnrcore.eng;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class STOHistory {
    private int count_meetings;
    private String lastcarmodel;
    private boolean cameWithNewCar;
    private double damagelevel;

    /**
     * Constructs ...
     *
     */
    public STOHistory() {
        this.count_meetings = 0;
        this.lastcarmodel = "";
        this.cameWithNewCar = false;
        this.damagelevel = 0.0D;
    }

    /**
     * Method description
     *
     */
    public void visit() {
        this.count_meetings += 1;

        actorveh ourcar = Crew.getIgrokCar();

        if ((ourcar == null) || (ourcar.getCar() == 0)) {
            eng.err("Visiting STO with realy bad car.");
        }

        String model = eng.GetVehiclePrefix(ourcar.getCar());

        this.cameWithNewCar = (model.compareToIgnoreCase(this.lastcarmodel) != 0);
        this.lastcarmodel = model;
        this.damagelevel = ourcar.querryCarDamaged();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isFirstTimeHere() {
        return (this.count_meetings == 1);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isSecondOrMoreTimeHere() {
        return (this.count_meetings >= 2);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean newTruck() {
        return this.cameWithNewCar;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean carIsDamaged() {
        return (this.damagelevel > 0.5D);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isCameWithNewCar() {
        return this.cameWithNewCar;
    }

    /**
     * Method description
     *
     *
     * @param cameWithNewCar
     */
    public void setCameWithNewCar(boolean cameWithNewCar) {
        this.cameWithNewCar = cameWithNewCar;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getCount_meetings() {
        return this.count_meetings;
    }

    /**
     * Method description
     *
     *
     * @param count_meetings
     */
    public void setCount_meetings(int count_meetings) {
        this.count_meetings = count_meetings;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getDamagelevel() {
        return this.damagelevel;
    }

    /**
     * Method description
     *
     *
     * @param damagelevel
     */
    public void setDamagelevel(double damagelevel) {
        this.damagelevel = damagelevel;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getLastcarmodel() {
        return this.lastcarmodel;
    }

    /**
     * Method description
     *
     *
     * @param lastcarmodel
     */
    public void setLastcarmodel(String lastcarmodel) {
        this.lastcarmodel = lastcarmodel;
    }
}


//~ Formatted in DD Std on 13/08/28
