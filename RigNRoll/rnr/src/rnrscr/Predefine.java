/*
 * @(#)Predefine.java   13/08/28
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

import rnr.src.rnrcore.SCRperson;
import rnr.src.rnrcore.vectorJ;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class Predefine {

    /**
     * Method description
     *
     *
     * @param PERSONAGE
     * @param possit
     * @param dirrit
     */
    public void PlacePerson(SCRperson PERSONAGE, vectorJ possit, vectorJ dirrit) {
        PERSONAGE.SetPos(possit);
        PERSONAGE.SetDir(dirrit);
    }

    /**
     * Method description
     *
     *
     * @param positions
     * @param directions
     *
     * @return
     */
    public long[] Table(vectorJ[] positions, vectorJ[] directions) {
        return Bar.getInstance().Table(positions, directions);
    }

    /**
     * Method description
     *
     *
     * @param positions
     * @param directions
     *
     * @return
     */
    public long[] BarStand(vectorJ[] positions, vectorJ[] directions) {
        return Bar.getInstance().BarStand(positions, directions);
    }

    /**
     * Method description
     *
     *
     * @param positions
     * @param directions
     *
     * @return
     */
    public long[] BarmanStand(vectorJ[] positions, vectorJ[] directions) {
        return Bar.getInstance().BarmanStand(positions, directions);
    }

    /**
     * Method description
     *
     */
    public void InitiateBar() {}

    /**
     * Method description
     *
     *
     * @param positions
     * @param directions
     * @param positions_crane
     * @param directions_crane
     * @param positions_crane1
     * @param directions_crane1
     *
     * @return
     */
    public long[] WHoperator(vectorJ[] positions, vectorJ[] directions, vectorJ[] positions_crane,
                             vectorJ[] directions_crane, vectorJ[] positions_crane1, vectorJ[] directions_crane1) {
        return warehouse.getInstance().WHoperator(positions, directions, positions_crane, directions_crane,
                positions_crane1, directions_crane);
    }

    void startworkwithBar(cSpecObjects so) {
        Bar.getInstance().StartWorkWithSO(so);
    }

    void startworkwithWarehouse(cSpecObjects so) {
        warehouse.getInstance().StartWorkWithSO(so);
    }
}


//~ Formatted in DD Std on 13/08/28
