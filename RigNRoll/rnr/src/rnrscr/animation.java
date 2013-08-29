/*
 * @(#)animation.java   13/08/28
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

import rnr.src.rnrcore.SCRanimparts;
import rnr.src.rnrcore.SCRperson;
import rnr.src.rnrcore.eng;
import rnr.src.rnrscr.ModelManager.ModelPresets;

//~--- JDK imports ------------------------------------------------------------

import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class animation implements animationConst {
    static final int MAX_MAN_MODELS = 20;
    static final int MAX_WOMAN_MODELS = 20;
    static final int BARMAN_TAG = 1;
    static final int DEFAULT_TAG = 0;
    static String DefaultModelName = "Woman016";
    private ArrayList<ModelManager.ModelPresets> peopleModels;
    protected cSpecObjects currentOwner;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings("unchecked")
    public animation() {
        this.peopleModels = new ArrayList<ModelPresets>();
        this.currentOwner = null;
    }

    /**
     * Method description
     *
     *
     * @param s
     */
    public void StartWorkWithSO(cSpecObjects s) {
        this.currentOwner = s;

        if (null == s.Presets()) {
            Presets ourPresets = new Presets();

            s.SetPresets(ourPresets);
        }

        this.peopleModels = this.currentOwner.Presets().getModels();
    }

    /**
     * Method description
     *
     *
     * @param out
     *
     * @throws Exception
     */
    public void PrindDbgInfo(FileWriter out) throws Exception {}

    /**
     * Method description
     *
     *
     * @param mcReq
     * @param wcReq
     * @param mcResp
     * @param wcResp
     */
    public void ModelReqestReport(int mcReq, int wcReq, int mcResp, int wcResp) {
        if ((0 == mcResp) && (0 != mcReq)) {
            if (null != this.currentOwner) {
                eng.writeLog("!!!ERROR!!! animation wiht owner " + this.currentOwner.name + "reqested" + mcResp
                             + " man models. Zero models getted!!!");
            } else {
                eng.writeLog("!!!ERROR!!! animation reqested " + mcResp + " man models. " + "Zero models getted!!!");
            }
        }

        if ((0 == wcResp) && (0 != wcReq)) {
            if (null != this.currentOwner) {
                eng.writeLog("!!!ERROR!!! animation wiht owner " + this.currentOwner.name + "reqested" + wcReq
                             + " woman models. Zero models getted!!!");
            } else {
                eng.writeLog("!!!ERROR!!! animation reqested " + wcReq + " woman models. " + "Zero models getted!!!");
            }
        }

        if (mcReq > mcResp) {
            eng.writeLog("!!!Warning!!! animation reqested " + mcReq + " man models. " + mcResp + " models getted!!!");
        }

        if (wcReq <= wcResp) {
            return;
        }

        eng.writeLog("!!!Warning!!! animation reqested " + wcReq + " woman models. " + wcResp + " models getted!!!");
    }

    /**
     * Method description
     *
     *
     * @param tag
     * @param nommodels
     */
    public void LoadModelByTag(int tag, int nommodels) {
        if (null == this.currentOwner) {
            return;
        }

        specobjects obj = specobjects.getInstance();
        ModelManager modelSource = (ModelManager) obj.GetModelSource();

        if (!(this.peopleModels.isEmpty())) {
            Iterator iter = this.peopleModels.iterator();

            while (iter.hasNext()) {
                ModelManager.ModelPresets model = (ModelManager.ModelPresets) iter.next();

                if ((((model.getTag() == tag) || ((model.getTag() & tag) != 0))) && (--nommodels == 0)) {
                    return;
                }
            }
        }

        ArrayList availablemodels = modelSource.getModels(this.peopleModels, this.currentOwner.sotip, tag, nommodels);

        if (!(availablemodels.isEmpty())) {
            this.currentOwner.Presets().AddModel(availablemodels);
        }

        if (availablemodels.size() != nommodels) {
            eng.err("LoadModelByTag reports. Models with tag " + tag + " cannot load " + nommodels);
        }

        this.peopleModels = this.currentOwner.Presets().getModels();
    }

    /**
     * Method description
     *
     *
     * @param manCount
     * @param womanCount
     */
    public void LoadModels(int manCount, int womanCount) {
        if (null == this.currentOwner) {
            StartWorkWithSO(new cSpecObjects());
        }

        int requestedMCount = manCount;
        int requestedWCount = womanCount;
        specobjects obj = specobjects.getInstance();
        ModelManager modelSource = (ModelManager) obj.GetModelSource();

        if (!(this.peopleModels.isEmpty())) {
            Iterator iter = this.peopleModels.iterator();

            while (iter.hasNext()) {
                ModelManager.ModelPresets model = (ModelManager.ModelPresets) iter.next();

                if (model.is_man) {
                    --manCount;
                } else if (!(model.is_man)) {
                    --womanCount;
                }
            }
        }

        int var = manCount;

        if (0 < var) {
            ArrayList models = modelSource.getModels(this.peopleModels, this.currentOwner.sotip, 0, true, var);

            this.currentOwner.Presets().AddModel(models);

            if (models.size() != var) {
                eng.err("LoadModels acnnot load " + var + " rested man models");
            }
        } else if (0 > var) {
            this.currentOwner.Presets().DelModel(true, -var);
        }

        var = womanCount;

        if (0 < var) {
            ArrayList models = modelSource.getModels(this.peopleModels, this.currentOwner.sotip, 0, false, var);

            this.currentOwner.Presets().AddModel(models);

            if (models.size() != var) {
                eng.err("LoadModels cannot load " + var + "  rested woman models");
            }
        } else if (0 > var) {
            this.currentOwner.Presets().DelModel(false, -var);
        }

        this.peopleModels = this.currentOwner.Presets().getModels();
        ModelReqestReport(requestedMCount, requestedWCount, requestedMCount - manCount, requestedWCount - womanCount);
    }

    protected String getModelName(int tag, boolean isMan) {
        if (this.peopleModels.isEmpty()) {
            return DefaultModelName;
        }

        Iterator iter = this.peopleModels.iterator();

        while (iter.hasNext()) {
            ModelManager.ModelPresets model = (ModelManager.ModelPresets) iter.next();

            if ((model.is_man == isMan) && (((model.getTag() == tag) || ((model.getTag() & tag) != 0)))) {
                iter.remove();

                return model.getName();
            }
        }

        return DefaultModelName;
    }

    protected ArrayList<String> getModelNames(int tag) {
        ArrayList names = new ArrayList();
        Iterator iter = this.peopleModels.iterator();

        while (iter.hasNext()) {
            ModelManager.ModelPresets model = (ModelManager.ModelPresets) iter.next();

            if ((tag == model.getTag()) || ((model.getTag() & tag) != 0)) {
                iter.remove();
                names.add(model.getName());
            }
        }

        return names;
    }

    protected boolean removeManModelWithName(String modelName) {
        if (this.peopleModels.isEmpty()) {
            return false;
        }

        Iterator iter = this.peopleModels.iterator();

        while (iter.hasNext()) {
            ModelManager.ModelPresets model = (ModelManager.ModelPresets) iter.next();

            if ((model.is_man == true) && (model.getName().compareTo(modelName) == 0)) {
                iter.remove();

                return true;
            }
        }

        return false;
    }

    protected boolean removeWomanModelWithName(String modelName) {
        if (this.peopleModels.isEmpty()) {
            return false;
        }

        Iterator iter = this.peopleModels.iterator();

        while (iter.hasNext()) {
            ModelManager.ModelPresets model = (ModelManager.ModelPresets) iter.next();

            if ((!(model.is_man)) && (model.getName().compareTo(modelName) == 0)) {
                iter.remove();

                return true;
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param person
     * @param name_mim
     * @param name_leg
     * @param name_body
     * @param name_head
     * @param name_rhand
     * @param name_lhand
     *
     * @return
     */
    public SCRanimparts CreateAnmanmFive(SCRperson person, String name_mim, String name_leg, String name_body,
            String name_head, String name_rhand, String name_lhand) {
        SCRanimparts poit_tr = new SCRanimparts();

        poit_tr.nAnimParts(person, name_mim, name_leg, name_body, name_head, name_rhand, name_lhand);

        return poit_tr;
    }

    /**
     * Method description
     *
     *
     * @param pers
     * @param animName
     * @param _type
     *
     * @return
     */
    public SCRanimparts CreateAnm(SCRperson pers, String animName, int _type) {
        String commonname = pers.GetName();

        commonname = commonname + '_';

        String name_mim = "_face";
        String name_leg = "_legs";
        String name_body = "_body";
        String name_head = "_head";
        String name_rhand = "_hand_r";
        String name_lhand = "_hand_l";

        name_mim = commonname + animName + name_mim;
        name_leg = commonname + animName + name_leg;
        name_body = commonname + animName + name_body;
        name_head = commonname + animName + name_head;
        name_rhand = commonname + animName + name_rhand;
        name_lhand = commonname + animName + name_lhand;

        SCRanimparts poit_tr = new SCRanimparts();

        poit_tr.AnimPartsIgnorNull(pers, name_mim, name_leg, name_body, name_head, name_rhand, name_lhand);
        pers.AddAnimGroup(poit_tr, _type);

        if (_type == 700) {
            poit_tr.TuneLoop(true);
        }

        if (_type == 800) {
            poit_tr.TuneLoop(false);
        }

        return poit_tr;
    }

    /**
     * Method description
     *
     *
     * @param person
     * @param animName
     *
     * @return
     */
    public SCRanimparts CreateAnmSingle(SCRperson person, String animName) {
        SCRanimparts poit_tr = new SCRanimparts();

        poit_tr.AnimPartsIgnorNull(person, null, animName, null, null, null, null);

        return poit_tr;
    }

    /**
     * Method description
     *
     *
     * @param person
     * @param animName
     *
     * @return
     */
    public SCRanimparts CreateAnm(SCRperson person, String animName) {
        String name_mim = "_face";
        String name_leg = "_legs";
        String name_body = "_body";
        String name_head = "_head";
        String name_rhand = "_hand_r";
        String name_lhand = "_hand_l";

        name_mim = animName + name_mim;
        name_leg = animName + name_leg;
        name_body = animName + name_body;
        name_head = animName + name_head;
        name_rhand = animName + name_rhand;
        name_lhand = animName + name_lhand;

        SCRanimparts poit_tr = new SCRanimparts();

        poit_tr.AnimPartsIgnorNull(person, name_mim, name_leg, name_body, name_head, name_rhand, name_lhand);

        return poit_tr;
    }

    /**
     * Method description
     *
     *
     * @param person
     * @param animName
     *
     * @return
     */
    public SCRanimparts CreateSimpleAnm(SCRperson person, String animName) {
        String name_mim = "_face";
        String name_leg = "";
        String name_body = "_body";
        String name_head = "_head";
        String name_rhand = "_hand_r";
        String name_lhand = "_hand_l";

        name_mim = animName + name_mim;
        name_leg = animName + name_leg;
        name_body = animName + name_body;
        name_head = animName + name_head;
        name_rhand = animName + name_rhand;
        name_lhand = animName + name_lhand;

        SCRanimparts poit_tr = new SCRanimparts();

        poit_tr.AnimPartsIgnorNull(person, name_mim, name_leg, name_body, name_head, name_rhand, name_lhand);

        return poit_tr;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Eventanm CreateEvent() {
        Eventanm posss = new Eventanm();

        posss.nevent();

        return posss;
    }

    /**
     * Method description
     *
     *
     * @param nm
     *
     * @return
     */
    public static int RandomFromNom(int nm) {
        if (nm <= 1) {
            return nm;
        }

        int res = (int) (nm * Math.random() + 1.0D);

        if (res > nm) {
            res = nm;
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param velin
     *
     * @return
     */
    public static double RandomVelocity(double velin) {
        double velR = 0.9D;

        velR += 0.2D * Math.random();
        velin *= velR;

        return velin;
    }

    /**
     * Method description
     *
     *
     * @param fromtime
     * @param totime
     *
     * @return
     */
    public static double RandomLength(double fromtime, double totime) {
        double velR = fromtime;

        velR += (totime - fromtime) * Math.random();

        return velR;
    }

    /**
     * Method description
     *
     *
     * @param inanim
     *
     * @return
     */
    public Chainanm CreateChain(SCRperson inanim) {
        Chainanm posss = new Chainanm();

        posss.nchain(inanim);

        return posss;
    }
}


//~ Formatted in DD Std on 13/08/28
