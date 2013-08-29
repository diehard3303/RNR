/*
 * @(#)aiplayer.java   13/08/26
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

import rnr.src.rnrcore.IScriptRef;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.ScriptRef;
import rnr.src.rnrcore.ScriptRefStorage;
import rnr.src.xmlserialization.AIPlayerSerializator;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class aiplayer implements IScriptRef {
    private Passport passport = null;
    private boolean modelbasedmodel = false;
    private boolean poolbasedmodel = false;
    private ScriptRef uid = null;
    private ImodelCreate modelCreator;
    private String idForModelCreator;
    private String poolref_name;

    /**
     * Constructs ...
     *
     */
    public aiplayer() {
        this.passport = new Passport(null);
        this.uid = new ScriptRef();
    }

    /**
     * Constructs ...
     *
     *
     * @param identitie
     */
    public aiplayer(String identitie) {
        this.passport = new Passport(identitie);
        this.uid = new ScriptRef();
    }

    private aiplayer(String identitie, int uidValue) {
        this.passport = new Passport(identitie);
        this.uid = new ScriptRef();
        this.uid.register(uidValue, this);
    }

    /**
     * Method description
     *
     *
     * @param identitie
     * @param uId
     *
     * @return
     */
    public static aiplayer createScriptRef(String identitie, int uId) {
        IScriptRef scriptRef = ScriptRefStorage.getRefference(uId);

        if (scriptRef == null) {
            return new aiplayer(identitie, uId);
        }

        assert(scriptRef instanceof aiplayer);

        return ((aiplayer) scriptRef);
    }

    /**
     * Method description
     *
     *
     * @param nameRef
     *
     * @return
     */
    public static aiplayer getScenarioAiplayer(String nameRef) {
        return CrewNamesManager.getInstance().gPlayer(nameRef);
    }

    /**
     * Method description
     *
     *
     * @param identitie
     *
     * @return
     */
    public static aiplayer getRefferencedAiplayer(String identitie) {
        aiplayer res = new aiplayer(identitie);
        RefferensedCreator creator = new RefferensedCreator();

        res.setModelCreator(creator, null);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param identitie
     * @param name
     *
     * @return
     */
    public static aiplayer getNamedAiplayerHiPoly(String identitie, String name) {
        aiplayer res = new aiplayer(identitie);
        MissionCutScenePlayer creator = new MissionCutScenePlayer();

        res.setModelCreator(creator, null);
        res.sPoolBased(name);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param identitie
     *
     * @return
     */
    public static aiplayer getSimpleAiplayer(String identitie) {
        aiplayer res = new aiplayer(identitie);
        ScenarioPersonPassanger creator = new ScenarioPersonPassanger();

        res.setModelCreator(creator, identitie);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param identitie
     * @param poolrefName
     *
     * @return
     */
    public static aiplayer getAmbientAiplayer(String identitie, String poolrefName) {
        aiplayer res = new aiplayer(identitie);
        AmbientPlayer creator = new AmbientPlayer();

        res.setModelCreator(creator, identitie);
        res.sPoolBased(poolrefName);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param identitie
     * @param poolrefName
     *
     * @return
     */
    public static aiplayer getNamedAiplayerNormal(String identitie, String poolrefName) {
        aiplayer res = new aiplayer(identitie);
        NamedScenarioPersonPassanger creator = new NamedScenarioPersonPassanger();

        res.setModelCreator(creator, identitie);
        res.sPoolBased(poolrefName);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param identitie
     * @param poolrefName
     *
     * @return
     */
    public static aiplayer getCutSceneAmbientPerson(String identitie, String poolrefName) {
        aiplayer res = new aiplayer(identitie);
        CutSceneAmbientPersonCreator creator = new CutSceneAmbientPersonCreator();

        res.setModelCreator(creator, identitie);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param modelCreator
     * @param id
     */
    public void setModelCreator(ImodelCreate modelCreator, String id) {
        this.modelCreator = modelCreator;
        this.idForModelCreator = id;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getIdForModelCreator() {
        return this.idForModelCreator;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ImodelCreate getModelCreator() {
        return this.modelCreator;
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void sModelBased(boolean val) {
        this.modelbasedmodel = val;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean getModelBased() {
        return this.modelbasedmodel;
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void sPoolBased(String name) {
        this.poolbasedmodel = true;
        this.poolref_name = name;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean getPoolBased() {
        return this.poolbasedmodel;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getPoolRefName() {
        return this.poolref_name;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void setUid(int value) {
        this.uid.setUid(value);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getUid() {
        return this.uid.getUid(this);
    }

    /**
     * Method description
     *
     */
    public void removeRef() {
        this.uid.removeRef(this);
    }

    /**
     * Method description
     *
     *
     * @param p
     */
    @Override
    public void updateNative(int p) {}

    /**
     * Method description
     *
     *
     * @return
     */
    public String gModelname() {
        return this.passport.modelName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String gFirstName() {
        return this.passport.firstName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String gLastName() {
        return this.passport.lastName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String gNickName() {
        return this.passport.nickName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String gPoolRefName() {
        return ((this.poolbasedmodel)
                ? this.poolref_name
                : (this.modelbasedmodel)
                  ? this.passport.modelName
                  : gNickName());
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public SCRuniperson getModel() {
        return this.modelCreator.create(this.passport.modelName, gPoolRefName(), this.idForModelCreator);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getIdentitie() {
        return this.passport.getM_identitie();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public CBContact createCBContacter() {
        return new CBContact(this.passport.modelName, this.passport.firstName, this.passport.lastName,
                             this.passport.nickName);
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public void beDriverOfCar(actorveh car) {
        car.takeDriver(this);
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public void abondoneCar(actorveh car) {
        car.abandoneCar(this);
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public void bePassangerOfCar(actorveh car) {
        car.takePassanger(this);
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public void bePackOfCar(actorveh car) {
        car.takePack(this);
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public void bePassangerOfCar_simple(actorveh car) {
        car.takePassanger_simple(this);
    }

    /**
     * Method description
     *
     *
     * @param car
     * @param prefix
     */
    public void bePassangerOfCar_simple_like(actorveh car, String prefix) {
        car.takePassanger_simple_like(this, prefix);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public IXMLSerializable getXmlSerializator() {
        return new AIPlayerSerializator(this);
    }

    /**
     * Method description
     *
     */
    public void delete() {
        removeRef();
    }
}


//~ Formatted in DD Std on 13/08/26
