/*
 * @(#)Bar.java   13/08/28
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

import rnr.src.players.CutSceneAuxPersonCreator;
import rnr.src.players.ImodelCreate;
import rnr.src.players.SOPersonModelCreator;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.SCRanimparts;
import rnr.src.rnrcore.SCRcamera;
import rnr.src.rnrcore.SCRperson;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscr.smi.Newspapers;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Bar extends animation {

    /** Field description */
    public static final boolean TEST_BAR_HACK = 1;
    private static final int MAX_BARSTAND_MODELS = 3;
    private static Bar instance;
    private static boolean isCutSceneAmbientBar;
    static int NOMTABLEMAMANIMS;
    static int NOMTABLEWOMAMANIMS;
    static int NOMBARSTANDANIMS;
    static int NOMBARMEN_toShow;

    /** Field description */
    public static int barType;
    static int MAXPEOPLECOF;
    private static List<SCRperson> m_pedPersons;
    private static List<Long> m_scenes;
    @SuppressWarnings("rawtypes")
    static ArrayList AllTablePersons;
    @SuppressWarnings("rawtypes")
    static ArrayList AllBarStandPersons;
    @SuppressWarnings("rawtypes")
    static ArrayList AllBarmenPersons;

    static {
        instance = null;
        isCutSceneAmbientBar = false;
        NOMTABLEMAMANIMS = 4;
        NOMTABLEWOMAMANIMS = 4;
        NOMBARSTANDANIMS = 4;
        NOMBARMEN_toShow = 1;
        barType = 1;
        MAXPEOPLECOF = 128;
        m_pedPersons = new ArrayList();
        m_scenes = new ArrayList();
    }

    boolean useScene = false;
    int PeopleCof = 128;
    private DialogsSet questSet = null;
    private final HashMap<String, Long> currentdialogs = new HashMap();
    private int totalModels = 0;
    private int total_Man_Models = 0;
    private int total_Woman_Models = 0;
    private int total_TableWoman_Models = 0;
    private int total_TableMan_Models = 0;
    private int total_BarStand_Models = 0;
    private int total_BarStand_Woman_Models = 0;
    private int total_BarStand_Man_Models = 0;
    private final int total_BarMen_Models = 1;

    private Bar() {
        Newspapers.addBigraceFailure(1, "Simple Race", "race1", "Oxnard", 1, 1, 1, 1);

        cSpecObjects so = new cSpecObjects();

        so.sotip = 8;
        StartWorkWithSO(so);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static Bar getInstance() {
        if (instance == null) {
            instance = new Bar();
        }

        return instance;
    }

    /**
     * Method description
     *
     *
     * @param type
     */
    public static void setBarType(int type) {
        barType = type;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getQuestsCount() {
        return this.questSet.getQuestCount();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static final vectorJ getCurrentBarPosition() {
        return getInstance().currentOwner.position;
    }

    private void loadModels() {
        BarTimeZone zonner = new BarTimeZone();

        zonner.ProcessZone(eng.gHour(), this.PeopleCof / MAXPEOPLECOF, (eng.gMonth() == 7) && (eng.gDate() == 14));
        this.total_Man_Models = zonner.GetManCount();
        this.total_Woman_Models = zonner.GetWomanCount();

        if (this.total_Man_Models > 20) {
            this.total_Man_Models = 20;
        }

        if (this.total_Woman_Models > 20) {
            this.total_Woman_Models = 20;
        }

        this.totalModels = (this.total_Man_Models + this.total_Woman_Models);
        LoadModels(this.total_Man_Models, this.total_Woman_Models);
        this.total_TableWoman_Models = 0;
        this.total_TableMan_Models = 0;
        this.total_BarStand_Woman_Models = 0;
        this.total_BarStand_Man_Models = 0;

        if ((null != this.questSet) && (0 != this.questSet.getQuestCount())) {
            for (int i = 0; i < this.questSet.getQuestCount(); ++i) {
                SCRuniperson uni_person = this.questSet.getQuest(i).getNpcModel();
                String modelname = eng.GetManPrefix(uni_person.nativePointer);
                boolean man_was_delted = false;

                if (removeManModelWithName(modelname)) {
                    man_was_delted = true;
                    this.totalModels -= 1;
                    this.total_Man_Models -= 1;
                }

                if (removeWomanModelWithName(modelname)) {
                    if (man_was_delted) {
                        eng.err("Dumb error on deleting model name from models. Bar.java. loadModels");
                    }

                    this.totalModels -= 1;
                    this.total_Woman_Models -= 1;
                }
            }

            this.total_TableWoman_Models = this.total_Woman_Models;
            this.total_TableMan_Models = this.total_Man_Models;
            this.total_BarStand_Models = this.questSet.getQuestCount();
        } else {
            this.total_TableWoman_Models = this.total_Woman_Models;
            this.total_TableMan_Models = this.total_Man_Models;

            int num_on_bar_stand = AdvancedRandom.RandFromInreval(0, Math.min(3, this.totalModels));

            if (0 != num_on_bar_stand) {
                this.total_BarStand_Woman_Models = AdvancedRandom.RandFromInreval(0, num_on_bar_stand);
                this.total_BarStand_Man_Models = (num_on_bar_stand - this.total_BarStand_Woman_Models);
                this.total_TableWoman_Models -= this.total_BarStand_Woman_Models;
                this.total_TableMan_Models -= this.total_BarStand_Man_Models;
            }

            this.total_BarStand_Models = (this.total_BarStand_Woman_Models + this.total_BarStand_Man_Models);

            if (this.total_BarStand_Models != num_on_bar_stand) {
                eng.err("load Models math errorr!!!!");
            }
        }

        LoadModelByTag(1, 1);
    }

    /**
     * Method description
     *
     *
     * @param s
     */
    @Override
    public void StartWorkWithSO(cSpecObjects s) {
        super.StartWorkWithSO(s);
        leaveBar();
        Init(MissionDialogs.queueDialogsForSO(s.sotip, s.position, new CoreTime()));
        loadModels();
    }

    /**
     * Method description
     *
     *
     * @param Cof
     */
    public void SetPeopleCof(int Cof) {
        if (Cof >= MAXPEOPLECOF) {
            double deveance = Cof / MAXPEOPLECOF;
            int periodF = (int) Math.floor(deveance);

            this.PeopleCof = (Cof - (MAXPEOPLECOF * periodF));
        }
    }

    /**
     * Method description
     *
     *
     * @param PERSONAGE1
     * @param possit
     * @param dirrit
     */
    public void PlacePerson(SCRperson PERSONAGE1, vectorJ possit, vectorJ dirrit) {
        PERSONAGE1.SetPos(possit);
        PERSONAGE1.SetDir(dirrit);
    }

    /**
     * Method description
     *
     *
     * @param modelName
     * @param animation
     * @param animationShift
     * @param timecoef
     * @param animationScale
     * @param pos
     * @param dir
     * @param has_detail
     *
     * @return
     */
    public SCRperson createPersonForBar(String modelName, String animation, vectorJ animationShift, double timecoef,
            double animationScale, vectorJ pos, vectorJ dir, boolean has_detail) {
        SCRperson PERSONAGE = (has_detail)
                              ? SCRperson.CreateAnm(modelName, pos, dir, true)
                              : SCRperson.CreateAnmNoDetailes(modelName, pos, dir, true);
        SCRanimparts ANIM = CreateAnmSingle(PERSONAGE, animation);

        ANIM.PermanentShift(animationShift.x, animationShift.y, animationShift.z);
        ANIM.Tune(animationScale, false);
        ANIM.SetVelocity(0.0D);
        ANIM.setTimeNScalePreventRandom(timecoef, animationScale);
        PERSONAGE.AddAnimGroup(ANIM, 700);
        eng.play(PERSONAGE);

        Eventsmain EVENTS = new Eventsmain();

        EVENTS.dotisthin(PERSONAGE);
        PlacePerson(PERSONAGE, pos, dir);
        PERSONAGE.ShiftPerson(ANIM.PermanentShift());

        Chainanm ManChain = CreateChain(PERSONAGE);

        ManChain.Add(5, 700, ANIM);
        PERSONAGE.StartChain(ManChain);
        PERSONAGE.SetInBarWorld();
        addPedPerson(PERSONAGE);

        return PERSONAGE;
    }

    /**
     * Method description
     *
     *
     * @param modelName
     * @param animation
     * @param animationShift
     * @param timecoef
     * @param animationScale
     * @param pos
     * @param dir
     *
     * @return
     */
    public SCRperson createItemForBar(String modelName, String animation, vectorJ animationShift, double timecoef,
                                      double animationScale, vectorJ pos, vectorJ dir) {
        SCRperson PERSONAGE = SCRperson.CreateAnmNoDetailes(modelName, pos, dir, true);

        PERSONAGE.setDependent();

        SCRanimparts ANIM = CreateSimpleAnm(PERSONAGE, animation);

        ANIM.PermanentShift(animationShift.x, animationShift.y, animationShift.z);
        ANIM.Tune(animationScale, false);
        ANIM.SetVelocity(0.0D);
        ANIM.setTimeNScalePreventRandom(timecoef, animationScale);
        PERSONAGE.AddAnimGroup(ANIM, 700);
        eng.play(PERSONAGE);

        Eventsmain EVENTS = new Eventsmain();

        EVENTS.dotisthin(PERSONAGE);
        PlacePerson(PERSONAGE, pos, dir);
        PERSONAGE.ShiftPerson(ANIM.PermanentShift());

        Chainanm ManChain = CreateChain(PERSONAGE);

        ManChain.Add(5, 700, ANIM);
        PERSONAGE.StartChain(ManChain);
        PERSONAGE.SetInBarWorld();
        addPedPerson(PERSONAGE);

        return PERSONAGE;
    }

    /**
     * Method description
     *
     *
     * @param M
     * @param P
     */
    public void testTableScene(matrixJ M, vectorJ P) {
        Vector vec = new Vector();

        vec.add(new SceneActorsPool("model", SCRuniperson.createSOPerson("Man_001", "tableman")));
        vec.add(new SceneActorsPool("plate", SCRuniperson.createSOPerson("Plate1", "plate")));
        vec.add(new SceneActorsPool("fork", SCRuniperson.createSOPerson("Fork1", "fork")));
        scenetrack.CreateSceneXMLPool("table_m1_bar", 5, vec, new Data("", M, P));
    }

    /**
     * Method description
     *
     *
     * @param dir
     * @param P
     * @param ids
     * @param modelNames
     * @param scenename
     *
     * @return
     */
    public long createBarScene(vectorJ dir, vectorJ P, String[] ids, String[] modelNames, String scenename) {
        Vector vec = new Vector();

        if ((ids == null) || (modelNames == null) || (ids.length != modelNames.length)) {
            eng.err("ids==null || modelNames==null || ids.length!=modelNames.length");
        }

        for (int i = 0; i < ids.length; ++i) {
            vec.add(new SceneActorsPool(ids[i], SCRuniperson.createSOPerson(modelNames[i], "bart_so_model")));
        }

        return scenetrack.CreateSceneXMLPool(scenename, 5, vec,
                new Data("", new matrixJ(dir, new vectorJ(0.0D, 0.0D, 1.0D)), P));
    }

    /**
     * Method description
     *
     *
     * @param poss
     * @param dirs
     *
     * @return
     */
    public long[] Table(vectorJ[] poss, vectorJ[] dirs) {
        if (isCutSceneAmbientBar) {
            ArrayList goodDirections = new ArrayList();
            ArrayList goodPositions = new ArrayList();

            for (int i = 0; i < dirs.length; ++i) {
                vectorJ dir = dirs[i];

                if ((dir.x < 0.0D) || (dir.y > 0.0D)) {
                    goodDirections.add(dirs[i]);
                    goodPositions.add(poss[i]);
                }
            }

            int size = goodDirections.size();
            vectorJ[] goodDirectionsArray = (vectorJ[]) goodDirections.toArray(new vectorJ[goodDirections.size()]);
            vectorJ[] goodPositionsArray = (vectorJ[]) goodPositions.toArray(new vectorJ[goodDirections.size()]);
            ArrayList models = new ArrayList();

            for (int i = 0; i < size; ++i) {
                boolean maleModel = i % 2 == 0;

                models.add(new ModelForBarScene(getModelName(0, maleModel), maleModel));
            }

            long[] scenes = BarPeopleScene.createTableScenes(goodPositionsArray, goodDirectionsArray, models);

            addScenes(scenes);

            return new long[0];
        }

        ArrayList models = new ArrayList();

        for (int i = 0; i < this.total_TableWoman_Models; ++i) {
            models.add(new ModelForBarScene(getModelName(0, false), false));
        }

        for (int i = 0; i < this.total_TableMan_Models; ++i) {
            models.add(new ModelForBarScene(getModelName(0, true), true));
        }

        long[] scenes = BarPeopleScene.createTableScenes(poss, dirs, models);

        addScenes(scenes);

        return new long[0];
    }

    /**
     * Method description
     *
     *
     * @param poss
     * @param dirs
     *
     * @return
     */
    public long[] BarStand(vectorJ[] poss, vectorJ[] dirs) {
        if (isCutSceneAmbientBar) {
            return new long[0];
        }

        ArrayList models = new ArrayList();
        boolean putInDialogs = false;
        String[] dialogNames = null;
        boolean withDialogs = (null != this.questSet) && (0 != this.questSet.getQuestCount());

        if (!(withDialogs)) {
            for (int i = 0; i < this.total_BarStand_Woman_Models; ++i) {
                models.add(new ModelForBarScene(getModelName(0, false), false));
            }

            for (int i = 0; i < this.total_BarStand_Man_Models; ++i) {
                models.add(new ModelForBarScene(getModelName(0, true), true));
            }
        } else {
            dialogNames = new String[this.questSet.getQuestCount()];

            for (int pers = 0; pers < this.questSet.getQuestCount(); ++pers) {
                putInDialogs = true;

                SCRuniperson uni_person = this.questSet.getQuest(pers).getNpcModel();
                String modelname = eng.GetManPrefix(uni_person.nativePointer);

                models.add(new ModelForBarScene(modelname, !(modelname.contains("Woman"))));
                dialogNames[pers] = this.questSet.getQuest(pers).getDescription();
            }
        }

        long[] scenes = BarPeopleScene.createBarStandScenes(poss, dirs, models);

        addScenes(scenes);

        if (putInDialogs) {
            assert((dialogNames != null) && (scenes.length == dialogNames.length));

            for (int i = 0; i < scenes.length; ++i) {
                this.currentdialogs.put(dialogNames[i], Long.valueOf(scenes[i]));
            }
        }

        return new long[0];
    }

    /**
     * Method description
     *
     *
     * @param poss
     * @param dirs
     *
     * @return
     */
    public long[] BarmanStand(vectorJ[] poss, vectorJ[] dirs) {
        ArrayList models = new ArrayList();

        for (int i = 0; i < NOMBARMEN_toShow; ++i) {
            models.add(new ModelForBarScene(getModelName(1, true), true));
        }

        long[] scenes = BarPeopleScene.createBarmanScenes(poss, dirs, models);

        addScenes(scenes);

        return new long[0];
    }

    /**
     * Method description
     *
     *
     * @param sz
     * @param prohids
     *
     * @return
     */
    public int[] Shuffle(int sz, int prohids) {
        int[] intArr = new int[sz];

        for (int i0 = 0; i0 < sz; ++i0) {
            intArr[i0] = i0;
        }

        Random rnd_ch = new Random();

        for (int pr = 0; pr < prohids; ++pr) {
            for (int i = 0; i < sz; ++i) {
                int replace = rnd_ch.nextInt(sz - i) + i;
                int prev = intArr[i];

                intArr[i] = intArr[replace];
                intArr[replace] = prev;
            }
        }

        return intArr;
    }

    private static void addPedPerson(SCRperson person) {
        m_pedPersons.add(person);
    }

    private static void addScenes(long[] scene) {
        for (long value : scene) {
            m_scenes.add(Long.valueOf(value));
        }
    }

    /**
     * Method description
     *
     */
    public static void deleteBar() {
        for (SCRperson person : m_pedPersons) {
            person.delete();
        }

        m_pedPersons.clear();

        for (Iterator i$ = m_scenes.iterator(); i$.hasNext(); ) {
            long scene = ((Long) i$.next()).longValue();

            scenetrack.DeleteScene(scene);
            BarPeopleScene.forgetScenePersons(scene);
        }

        m_scenes.clear();
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void startDialog(String name) {
        if (!(this.currentdialogs.containsKey(name))) {
            String message = "startint dialog in bar - has no person for dialog " + name;

            eng.log(message);

            return;
        }

        long scene = this.currentdialogs.get(name).longValue();

        scenetrack.UpdateSceneFlags(scene, 256);

        List persons = BarPeopleScene.getScenePersons(scene);

        for (SCRuniperson person : persons) {
            person.stop();
        }
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void endDialog(String name) {
        if (!(this.currentdialogs.containsKey(name))) {
            String message = "ending dialog in bar - has no person for dialog " + name;

            eng.log(message);

            return;
        }

        long scene = this.currentdialogs.get(name).longValue();

        scenetrack.UpdateSceneFlags(scene, 1);
    }

    /**
     * Method description
     *
     */
    public void leaveBar() {
        this.currentdialogs.clear();
        this.questSet = null;
    }

    /**
     * Method description
     *
     *
     * @param quests
     */
    public void Init(DialogsSet quests) {
        if (null != quests) {
            this.questSet = quests;
        }
    }

    /**
     * Method description
     *
     *
     * @param camp
     */
    public void BarCreateCamera(long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);

        cam.BindToBar(eng.GetCurrentBar());
        cam.PlayCamera();
    }

    /**
     * Method description
     *
     *
     * @param camp
     */
    public void BarCreateCamera_Matrix(long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        matrixJ mrot = new matrixJ();

        mrot.v0.Set(-1.0D, 0.0D, 0.0D);
        mrot.v1.Set(0.0D, -1.0D, 0.0D);
        mrot.v2.Set(0.0D, 0.0D, 1.0D);

        vectorJ pos = new vectorJ(0.0D, 0.0D, 0.0D);

        cam.BindToMatrix(mrot, pos);
        cam.PlayCamera();
    }

    /**
     * Method description
     *
     *
     * @param camp
     */
    public void BarDeleteCamera(long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);

        cam.DeleteCamera();
    }

    /**
     * Method description
     *
     *
     * @param camp
     */
    public void BarStopCamera(long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);

        cam.StopCamera();
    }

    /**
     * Method description
     *
     *
     * @param tsk
     */
    public void deletetask_pers(long tsk) {
        eng.DeleteTASK(tsk);
    }

    /**
     * Method description
     *
     *
     * @param drv
     */
    public void bar_realesecar(long drv) {
        event.Setevent(1002 + (int) drv);
    }

    /**
     * Method description
     *
     *
     * @param man
     */
    public void createanimateddoor_fromperson(long man) {
        SCRuniperson person = new SCRuniperson();

        person.nativePointer = man;

        matrixJ mrot = new matrixJ();

        mrot.v0.Set(1.0D, 0.0D, 0.0D);
        mrot.v1.Set(0.0D, 0.0D, 1.0D);
        mrot.v2.Set(0.0D, -1.0D, 0.0D);
        person.CreateAnimatedSpace_timedependance("Space_DoorToBar_" + eng.GetBarInnerName(eng.GetCurrentBar()),
                "Bar_door_01", "space_MDL-Bar_door_01", "Ivan_In_Bar_001", 0.0D, 0L, 0L, mrot, true, false);
    }

    /**
     * Method description
     *
     *
     * @param man
     */
    public void createanimateddoor_fromperson_close(long man) {
        SCRuniperson person = new SCRuniperson();

        person.nativePointer = man;

        matrixJ mrot = new matrixJ();

        mrot.v0.Set(1.0D, 0.0D, 0.0D);
        mrot.v1.Set(0.0D, 0.0D, 1.0D);
        mrot.v2.Set(0.0D, -1.0D, 0.0D);
        person.CreateAnimatedSpace_timedependance("Space_DoorToBar_" + eng.GetBarInnerName(eng.GetCurrentBar()),
                "Bar_door_01", "space_MDL-Bar_door_01", "", 0.0D, 0L, 0L, mrot, true, false);
    }

    /**
     * Method description
     *
     *
     * @param man
     */
    public void deleteanimateddoor_fromperson(long man) {
        SCRuniperson person = new SCRuniperson();

        person.nativePointer = man;
        person.DeleteAnimatedSpace("Space_DoorToBar_" + eng.GetBarInnerName(eng.GetCurrentBar()), "Bar_door_01", 0L);
    }

    /**
     * Method description
     *
     *
     * @param man
     */
    public void createanimateddoorinside_fromperson(long man) {
        SCRuniperson person = new SCRuniperson();

        person.nativePointer = man;

        matrixJ mrot = new matrixJ();

        mrot.v0.Set(1.0D, 0.0D, 0.0D);
        mrot.v1.Set(0.0D, 0.0D, 1.0D);
        mrot.v2.Set(0.0D, -1.0D, 0.0D);

        matrixJ mrot_newspaper = new matrixJ();

        mrot_newspaper.v0.Set(1.0D, 0.0D, 0.0D);
        mrot_newspaper.v1.Set(0.0D, 1.0D, 0.0D);
        mrot_newspaper.v2.Set(0.0D, 0.0D, 1.0D);
        person.CreateAnimatedSpace_timedependance("Space_DoorBar01", "Bar_door_02", "space_MDL-Bar_door_01",
                "Ivan_In_Bar_002", 0.0D, 0L, 0L, mrot, true, false);
        person.CreateAnimatedSpace_timedependance("Space_newspaper", "newspaper", "space_MDL-newspaper",
                "Ivan_In_Bar_002", 0.0D, 0L, 0L, mrot_newspaper, true, true);
    }

    /**
     * Method description
     *
     *
     * @param man
     */
    public void deleteanimateddoorinside_fromperson(long man) {
        SCRuniperson person = new SCRuniperson();

        person.nativePointer = man;
        person.DeleteAnimatedSpace("Space_DoorBar01", "Bar_door_02", 0L);
        person.DeleteAnimatedSpace("Space_newspaper", "newspaper", 0L);
    }

    /**
     * Method description
     *
     *
     * @param man
     */
    public void PlacePersonInBar(long man) {
        SCRuniperson person = new SCRuniperson();

        person.nativePointer = man;
        person.SetInBarWorld();
    }

    /**
     * Method description
     *
     *
     * @param camp
     */
    public void PlaceCameraInBar(long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);

        cam.SetInBarWorld();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public long playMenuCamera() {
        return scenetrack.CreateSceneXML("barin menu camera " + barType, 5, null);
    }

    void barfreecam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");

        freeCam.PlayCamera();
        freeCam.SetInBarWorld();

        vectorJ possit = new vectorJ(2.0D, 2.0D, 1.0D);

        freeCam.SetCameraPosition(possit);
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void setCutSceneAmbientBar(boolean value) {
        isCutSceneAmbientBar = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static ImodelCreate getModelCreator() {
        if (isCutSceneAmbientBar) {
            return new CutSceneAuxPersonCreator();
        }

        return new SOPersonModelCreator();
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ
     */
    public class CreateAnimation {
        private String getBarmanModelName(int nom) {
            ArrayList names = Bar.this.getModelNames(1);

            if (nom >= names.size()) {
                double deveance = nom / names.size();
                int periodF = (int) Math.floor(deveance);

                nom -= names.size() * periodF;
            }

            String modelname = (String) names.get(nom);

            return modelname;
        }

        /**
         * Method description
         *
         *
         * @param nom
         * @param pos
         * @param dir
         */
        public void barmenScene(int nom, vectorJ pos, vectorJ dir) {
            String modelname = getBarmanModelName(nom);
            SCRuniperson _barmen = SCRuniperson.createNamedSOPerson(modelname, "barmen", "barmen");
            SCRuniperson barmenCloth = SCRuniperson.createNamedSOPerson("BarmenCloth", "barmenCloth", "barmenCloth");
            SCRuniperson barmenWine = SCRuniperson.createNamedSOPerson("Wine1", "Wine1", "Wine1");
            Vector v = new Vector(3);

            v.add(new SceneActorsPool("barmen", _barmen));
            v.add(new SceneActorsPool("barmenCloth", barmenCloth));
            v.add(new SceneActorsPool("barmenWine", barmenWine));

            matrixJ matr = new matrixJ(dir, new vectorJ(0.0D, 0.0D, 1.0D));
            long scene = scenetrack.CreateSceneXMLPool("barmen", 5, v, new preset(matr, pos));

            scenetrack.moveSceneTime(scene, AdvancedRandom.getRandomDouble() * 15.0D);
            Bar.access$000(new long[] { scene });
        }

        /**
         * Method description
         *
         *
         * @param nom
         * @param pos
         * @param dir
         */
        public void barmen(int nom, vectorJ pos, vectorJ dir) {
            String modelname = getBarmanModelName(nom);
            SCRperson PERSONAGE = null;
            vectorJ[] shifts = { new vectorJ(0.0D, 0.0D, 0.0D), new vectorJ(0.0D, 0.0D, 0.0D) };
            double[] scale = { 1.2D, 1.2D };
            String[] anmNames = { "barmen", "barmen_second_pers" };
            String[][] addItems = {
                { "BarmenCloth", "Wine1" }, { "BarmenCloth", "Wine1" }
            };
            String[][] addAnims = {
                { "BarmenCloth", "dishesBarman_M1" }, { "BarmenCloth", "dishesBarman_M1" }
            };
            double timecoef = AdvancedRandom.getRandomDouble();

            PERSONAGE = Bar.this.createPersonForBar(modelname, anmNames[nom], shifts[nom], timecoef, scale[nom], pos,
                    dir, false);

            for (int i = 0; i < addAnims[nom].length; ++i) {
                SCRperson addItem = Bar.this.createItemForBar(addItems[nom][i], addAnims[nom][i], shifts[nom],
                                        timecoef, scale[nom], pos, dir);

                PERSONAGE.addDependent(addItem);
            }
        }

        /**
         * Method description
         *
         *
         * @param nom
         * @param isMAN
         * @param modelname
         * @param pos
         * @param dir
         *
         * @return
         */
        public SCRperson barstand(int nom, boolean isMAN, String modelname, vectorJ pos, vectorJ dir) {
            SCRperson PERSONAGE = null;
            vectorJ[] shifts = { new vectorJ(0.0D, 0.12D, 0.0D), new vectorJ(0.0D, 0.12D, 0.0D),
                                 new vectorJ(0.0D, 0.14D, 0.0D), new vectorJ(-0.03D, 0.06D, 0.0D) };
            double[] scale = { 1.2D, 1.2D, 1.2D, 1.2D };
            String[] anmNames = { "Man_inBar005", "Man_inBar006", "W_inbar005", "W_inbar006" };
            String[][] addItems = {
                { "GlassSmall" }, { "GlassSmall" }, new String[0], new String[0]
            };
            String[][] addAnims = {
                { "dishesBar_M05" }, { "dishesBar_M06" }, new String[0], new String[0]
            };

            if (nom >= 2) {
                double deveance = nom / 2.0D;
                int periodF = (int) Math.floor(deveance);

                nom -= 2 * periodF;
            }

            if (!(isMAN)) {
                nom += 2;
            }

            double timecoef = AdvancedRandom.getRandomDouble();

            PERSONAGE = Bar.this.createPersonForBar(modelname, anmNames[nom], shifts[nom], timecoef, scale[nom], pos,
                    dir, true);

            for (int i = 0; i < addAnims[nom].length; ++i) {
                SCRperson addItem = Bar.this.createItemForBar(addItems[nom][i], addAnims[nom][i], shifts[nom],
                                        timecoef, scale[nom], pos, dir);

                PERSONAGE.addDependent(addItem);
            }

            return PERSONAGE;
        }

        class preset {

            /** Field description */
            public vectorJ P;

            /** Field description */
            public matrixJ M;

            preset(matrixJ parammatrixJ, vectorJ paramvectorJ) {
                this.M = parammatrixJ;
                this.P = P;
            }
        }
    }


    static class Data {
        String model;
        matrixJ M;
        vectorJ P;

        Data(String model, matrixJ M, vectorJ P) {
            this.model = model;
            this.M = M;
            this.P = P;
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ
     */
    public class Eventsmain {

        /**
         * Method description
         *
         *
         * @param PERSONAGE
         */
        public void dotisthin(SCRperson PERSONAGE) {
            PERSONAGE.AttachToEvent(8);
        }
    }


    static class Presets {
        String newspaper;
        String bardoor;
    }
}


//~ Formatted in DD Std on 13/08/28
