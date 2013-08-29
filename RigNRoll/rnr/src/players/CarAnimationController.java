/*
 * @(#)CarAnimationController.java   13/08/26
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrconfig.GetTruckersIdentities;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
public class CarAnimationController implements ICarCreationController {
    private ArrayList<TypeCarPair> nonloadedCars;
    private ArrayList<TypeCarPair> loadedCars;
    private HashMap<actorveh, String> assignedAnimations;
    private DriversModelsPool pool;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public CarAnimationController() {
        this.nonloadedCars = new ArrayList();
        this.loadedCars = new ArrayList();
        this.assignedAnimations = new HashMap();
        this.pool = null;
    }

    private TypeCarPair removeFromList(int playerId, List<TypeCarPair> list) {
        Iterator iter = list.iterator();

        while (iter.hasNext()) {
            TypeCarPair item = (TypeCarPair) iter.next();

            if (item.car.getAi_player() == playerId) {
                iter.remove();

                return item;
            }
        }

        if (!($assertionsDisabled)) {
            throw new AssertionError();
        }

        return null;
    }

    private actorveh findPlayerIdForNickName(String nickName) {
        Set set = this.assignedAnimations.entrySet();

        for (Map.Entry entry : set) {
            if (nickName.compareTo((String) entry.getValue()) == 0) {
                return ((actorveh) entry.getKey());
            }
        }

        return null;
    }

    private boolean createplayerForCar(actorveh car) {
        DriversModelsPool.NickName nick_name = getPoolInitialize().getNickName();

        if (nick_name == null) {
            return false;
        }

        aiplayer pl = aiplayer.getAmbientAiplayer(nick_name.getIdentitie(), nick_name.getNickName());

        pl.beDriverOfCar(car);
        this.assignedAnimations.put(car, nick_name.getNickName());

        return true;
    }

    private DriversModelsPool getPoolInitialize() {
        if (null == this.pool) {
            GetTruckersIdentities identities = new GetTruckersIdentities();

            this.pool = DriversModelsPool.createAndFill(identities.get());
        }

        return this.pool;
    }

    /**
     * Method description
     *
     *
     * @param playerType
     * @param playerId
     *
     * @return
     */
    @Override
    public actorveh onCarCreate(int playerType, int playerId) {
        actorveh car = new actorveh();

        car.setAi_player(playerId);

        if (!(createplayerForCar(car))) {
            this.nonloadedCars.add(new TypeCarPair(playerType, car));
        } else {
            this.loadedCars.add(new TypeCarPair(playerType, car));
        }

        return car;
    }

    /**
     * Method description
     *
     *
     * @param playerType
     * @param playerId
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void onCarDelete(int playerType, int playerId) {
        Set set = this.assignedAnimations.entrySet();

        for (Map.Entry entry : set) {
            if (((actorveh) entry.getKey()).getAi_player() == playerId) {
                getPoolInitialize().removeNickName((String) entry.getValue());
                removeFromList(playerId, this.loadedCars);
                this.assignedAnimations.remove(entry.getKey());

                return;
            }
        }

        removeFromList(playerId, this.nonloadedCars);
    }

    /**
     * Method description
     *
     *
     * @param nickName
     */
    public void modelUnloaded(String nickName) {
        getPoolInitialize().nativecallModelUnloaded(nickName);

        actorveh car = findPlayerIdForNickName(nickName);

        if (null != car) {
            TypeCarPair friedPair = removeFromList(car.getAi_player(), this.loadedCars);

            this.nonloadedCars.add(friedPair);
        }
    }

    /**
     * Method description
     *
     */
    public void renewState() {
        for (int i = 0; i < this.nonloadedCars.size(); ) {
            TypeCarPair item = this.nonloadedCars.get(i);

            if (createplayerForCar(item.car)) {
                this.nonloadedCars.remove(i);
                this.loadedCars.add(item);
            } else {
                ++i;
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<String> getPoolNames() {
        return getPoolInitialize().getPoolNames();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public HashMap<actorveh, String> getAssignedAnimations() {
        return this.assignedAnimations;
    }

    /**
     * Method description
     *
     *
     * @param assignedAnimations
     */
    public void setAssignedAnimations(HashMap<actorveh, String> assignedAnimations) {
        this.assignedAnimations = assignedAnimations;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<TypeCarPair> getLoadedCars() {
        return this.loadedCars;
    }

    /**
     * Method description
     *
     *
     * @param loadedCars
     */
    public void setLoadedCars(ArrayList<TypeCarPair> loadedCars) {
        this.loadedCars = loadedCars;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<TypeCarPair> getNonloadedCars() {
        return this.nonloadedCars;
    }

    /**
     * Method description
     *
     *
     * @param nonloadedCars
     */
    public void setNonloadedCars(ArrayList<TypeCarPair> nonloadedCars) {
        this.nonloadedCars = nonloadedCars;
    }

    /**
     * Method description
     *
     *
     * @param pool
     */
    public void setPool(DriversModelsPool pool) {
        this.pool = pool;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public DriversModelsPool getPool() {
        return getPoolInitialize();
    }

    /**
     * Method description
     *
     */
    public void deinit() {
        if (this.pool != null) {
            this.pool.deinit();
        }
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static class TypeCarPair {
        actorveh car;
        int typePlayer;

        /**
         * Constructs ...
         *
         *
         * @param typePlayer
         * @param car
         */
        public TypeCarPair(int typePlayer, actorveh car) {
            this.car = car;
            this.typePlayer = typePlayer;
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
        public int getTypePlayer() {
            return this.typePlayer;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
