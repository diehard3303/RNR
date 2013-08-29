/*
 * @(#)QuestSemitrailer.java   13/08/28
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

import rnr.src.players.actorveh;
import rnr.src.players.semitrailer;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class QuestSemitrailer implements IQuestItem {
    private static final String NONAME = "no name";
    String model = "no name";
    String place = "no name";
    private semitrailer trailer = null;

    /**
     * Constructs ...
     *
     */
    public QuestSemitrailer() {}

    /**
     * Constructs ...
     *
     *
     * @param model
     * @param place
     */
    public QuestSemitrailer(String model, String place) {
        this.model = model;
        this.place = place;
    }

    /**
     * Method description
     *
     */
    @Override
    public void create() {
        vectorJ pos = eng.getControlPointPosition(this.place);

        if (pos.length() < 1.0D) {
            pos = new vectorJ(3225.0D, -24500.0D, 2.0D);
        }

        this.trailer = semitrailer.create(this.model, new matrixJ(), pos);
    }

    /**
     * Method description
     *
     */
    @Override
    public void destroy() {
        if (null != this.trailer) {
            this.trailer.delete();
            this.trailer = null;
        }
    }

    /**
     * Method description
     *
     *
     * @param player
     *
     * @return
     */
    public boolean have(actorveh player) {
        semitrailer palyer_semi = player.querryTrailer();

        if (palyer_semi == null) {
            return false;
        }

        return palyer_semi.equal(this.trailer);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getPlacement() {
        return this.place;
    }
}


//~ Formatted in DD Std on 13/08/28
