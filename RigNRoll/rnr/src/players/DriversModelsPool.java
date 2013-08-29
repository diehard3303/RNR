/*
 * @(#)DriversModelsPool.java   13/08/26
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

import rnr.src.menu.JavaEvents;
import rnr.src.players.DriversModelsPool.NickName;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrscr.PedestrianManager;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class DriversModelsPool {
    private static final int POOLSIZE = 5;
    @SuppressWarnings("unchecked")
    private ArrayList<String> cycleListModelNames = new ArrayList<String>();
    @SuppressWarnings("unchecked")
    private ArrayList<String> pool = new ArrayList<String>();
    @SuppressWarnings("unchecked")
    private ArrayList<String> exposing = new ArrayList<String>();
    @SuppressWarnings("unchecked")
    private ArrayList<NickName> freeNickNames = new ArrayList<NickName>();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private ArrayList<NickName> bussyNickNames = new ArrayList();
    private NickNamesUniqueName uniqueName = new NickNamesUniqueName();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private DriversModelsPool(ArrayList<String> model_names) {
        this.cycleListModelNames = ((ArrayList) model_names.clone());
    }

    private void fillPool() {
        for (int i = 0; i < 5; ++i) {
            addNewModel();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<String> getPoolNames() {
        return this.pool;
    }

    /**
     * Method description
     *
     */
    public void makePoolCycle() {
        int count = 0;
        int min_count = 1000000000;
        int min_index = -1;

        for (String model_name : this.pool) {
            int usaed = 0;

            for (NickName nn : this.bussyNickNames) {
                if (nn.isIdentitie(model_name) != false) {
                    ++usaed;
                }
            }

            if (min_count > usaed) {
                min_count = usaed;
                min_index = count;
            }

            ++count;
        }

        if (min_index >= 0) {
            expose(this.pool.get(min_index));
        }
    }

    private void leaveNickName(String nickName) {
        this.uniqueName.leaveNickNameString(nickName);
    }

    private void checkExposed() {
        for (int i = 0; i < this.exposing.size(); ) {
            String exposeModel = this.exposing.get(i);

            for (int freei = 0; freei < this.freeNickNames.size(); ) {
                if (this.freeNickNames.get(freei).exposeByModel(exposeModel) != false) {
                    NickName leavedNickName = this.freeNickNames.remove(freei);

                    leaveNickName(leavedNickName.getNickName());
                }

                ++freei;
            }

            boolean has_exposed = false;

            for (NickName nick_name : this.bussyNickNames) {
                if (nick_name.isIdentitie(exposeModel) != false) {
                    has_exposed = true;

                    break;
                }
            }

            if (!(has_exposed)) {
                returnOldModel(exposeModel);
                addNewModel();
                this.exposing.remove(i);
            } else {
                ++i;
            }
        }
    }

    private void expose(String model_name) {
        for (int i = 0; i < this.pool.size(); ++i) {
            if (model_name.compareTo(this.pool.get(i)) == 0) {
                this.pool.remove(i);

                break;
            }
        }

        this.exposing.add(model_name);
        checkExposed();
    }

    private NickName getNickName(String model_name) {
        if (null == model_name) {
            return null;
        }

        int count = 0;

        for (NickName nick_name : this.freeNickNames) {
            if (nick_name.isIdentitie(model_name) != false) {
                return (this.freeNickNames.remove(count));
            }

            ++count;
        }

        return new NickName(this.uniqueName.getNickNameString(), model_name);
    }

    NickName getNickName() {
        NickName res = getNickName(getModelName());

        if (res != null) {
            this.bussyNickNames.add(res);
        }

        return res;
    }

    void removeNickName(String nick_name) {
        int count = 0;

        for (NickName nn : this.bussyNickNames) {
            if (nn.isNickName(nick_name) != false) {
                this.freeNickNames.add(this.bussyNickNames.remove(count));

                break;
            }

            ++count;
        }

        checkExposed();
    }

    private void addNewModel() {
        if (this.cycleListModelNames.isEmpty()) {
            return;
        }

        String new_model_name = this.cycleListModelNames.get(0);
        NickName try_nick_name = new NickName(this.uniqueName.getNickNameString(), new_model_name);

        if (try_nick_name.isBad()) {
            return;
        }

        this.cycleListModelNames.remove(0);
        this.pool.add(0, new_model_name);
        this.freeNickNames.add(try_nick_name);
    }

    /**
     * Method description
     *
     *
     * @param model_name
     */
    public void nativecallModelUnloaded(String model_name) {
        Iterator nick = this.bussyNickNames.iterator();

        while (nick.hasNext()) {
            NickName nick_name = (NickName) nick.next();

            if (nick_name.exposeByModel(model_name) != false) {
                nick.remove();
            }
        }

        expose(model_name);
    }

    private void returnOldModel(String model_name) {
        this.cycleListModelNames.add(model_name);
        PedestrianManager.getInstance().removeNamedModel(model_name);
    }

    private String getModelName() {
        if (this.pool.isEmpty()) {
            return null;
        }

        String res = this.pool.remove(0);

        this.pool.add(res);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param models
     *
     * @return
     */
    public static DriversModelsPool createAndFill(ArrayList<String> models) {
        DriversModelsPool pool = new DriversModelsPool(models);

        pool.fillPool();

        return pool;
    }

    /**
     * Method description
     *
     *
     * @param models
     *
     * @return
     */
    public static DriversModelsPool create(ArrayList<String> models) {
        return new DriversModelsPool(models);
    }

    private static boolean lock(String model_name, String nick_name) {
        IdentiteNames info = new IdentiteNames(model_name);

        JavaEvents.SendEvent(57, 1, info);

        SCRuniperson person = SCRuniperson.createNamedAmbientPerson(info.modelName, nick_name, info.modelName);

        if (person != null) {
            person.lockPerson();
            person.setAnimationsLoadConsequantly();

            return true;
        }

        return false;
    }

    private static void unlock(String nick_name) {
        SCRuniperson person = SCRuniperson.findModel(nick_name);

        if (person != null) {
            person.unlockPerson();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<NickName> getBussyNickNames() {
        return this.bussyNickNames;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setBussyNickNames(ArrayList<NickName> value) {
        for (NickName nn : this.bussyNickNames) {
            nn.exposeByModel(nn.getIdentitie());
        }

        this.bussyNickNames = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<String> getCycleListModelNames() {
        return this.cycleListModelNames;
    }

    /**
     * Method description
     *
     *
     * @param cycleListModelNames
     */
    public void setCycleListModelNames(ArrayList<String> cycleListModelNames) {
        this.cycleListModelNames = cycleListModelNames;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<String> getExposing() {
        return this.exposing;
    }

    /**
     * Method description
     *
     *
     * @param exposing
     */
    public void setExposing(ArrayList<String> exposing) {
        this.exposing = exposing;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<NickName> getFreeNickNames() {
        return this.freeNickNames;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setFreeNickNames(ArrayList<NickName> value) {
        for (NickName nn : this.freeNickNames) {
            nn.exposeByModel(nn.getIdentitie());
        }

        this.freeNickNames = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<String> getPool() {
        return this.pool;
    }

    /**
     * Method description
     *
     *
     * @param pool
     */
    public void setPool(ArrayList<String> pool) {
        this.pool = pool;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public NickNamesUniqueName getUniqueName() {
        return this.uniqueName;
    }

    /**
     * Method description
     *
     *
     * @param uniqueName
     */
    public void setUniqueName(NickNamesUniqueName uniqueName) {
        this.uniqueName = uniqueName;
    }

    /**
     * Method description
     *
     */
    public void deinit() {
        for (NickName nn : this.freeNickNames) {
            nn.exposeByModel(nn.getIdentitie());
        }

        this.freeNickNames.clear();

        for (NickName nn : this.bussyNickNames) {
            nn.exposeByModel(nn.getIdentitie());
        }

        this.bussyNickNames.clear();
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static class NickName {
        private String identitie;
        private final String name;
        private final boolean badModel;

        /**
         * Constructs ...
         *
         *
         * @param name
         * @param identitie
         */
        public NickName(String name, String identitie) {
            this.name = name;
            this.badModel = (!(initwithModel(identitie)));
        }

        private boolean initwithModel(String identitie) {
            exposeByModel(this.identitie);
            this.identitie = identitie;

            return DriversModelsPool.access$000(identitie, this.name);
        }

        private boolean exposeByModel(String identitie) {
            if (isIdentitie(identitie)) {
                DriversModelsPool.access$100(this.name);
                this.identitie = null;

                return true;
            }

            return false;
        }

        private boolean isIdentitie(String model_name) {
            if (this.identitie == null) {
                return false;
            }

            return (this.identitie.compareTo(model_name) == 0);
        }

        private boolean isNickName(String nick_name) {
            if (this.name == null) {
                return false;
            }

            return (this.name.compareTo(nick_name) == 0);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isBad() {
            return this.badModel;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public String getIdentitie() {
            return this.identitie;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public String getNickName() {
            return this.name;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
