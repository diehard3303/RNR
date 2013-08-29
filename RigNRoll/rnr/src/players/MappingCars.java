/*
 * @(#)MappingCars.java   13/08/26
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


package rnr.src.players;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class MappingCars {
    private HashMap<String, actorveh> mappedCars;

    /**
     * Constructs ...
     *
     */
    public MappingCars() {
        this.mappedCars = new HashMap();
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public actorveh getMappedCar(String name) {
        actorveh car = this.mappedCars.get(name);

        return car;
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param car
     */
    public void addMappedCar(String name, actorveh car) {
        this.mappedCars.put(name, car);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void removeMappedCar(String name) {
        this.mappedCars.remove(name);
    }

    /**
     * Method description
     *
     *
     * @param playerId
     */
    @SuppressWarnings("rawtypes")
    public void removeMappedCar(int playerId) {
        Set set = this.mappedCars.entrySet();
        Iterator iter = set.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            if (((actorveh) entry.getValue()).getAi_player() == playerId) {
                iter.remove();

                return;
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public HashMap<String, actorveh> getMappedCars() {
        return this.mappedCars;
    }

    /**
     * Method description
     *
     *
     * @param mappedCars
     */
    public void setMappedCars(HashMap<String, actorveh> mappedCars) {
        this.mappedCars = mappedCars;
    }
}


//~ Formatted in DD Std on 13/08/26
