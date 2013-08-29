/*
 * @(#)BigRaceCrowd.java   13/08/28
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
import rnr.src.rnrcore.Collide;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrcore.SCRpersongroup;
import rnr.src.rnrcore.SCRtalkingperson;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.ScriptRef;
import rnr.src.rnrcore.anm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscr.Helper;
import rnr.src.rnrscr.trackscripts;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class BigRaceCrowd implements anm {

    /** Field description */
    public static String unicidName = "bigracePerson";
    private static int unicidNameNumber = 0;
    static int id = 0;
    @SuppressWarnings("rawtypes")
    Vector<SCRtalkingperson> groupLeaders;
    @SuppressWarnings("rawtypes")
    Vector<SCRtalkingperson> women_groupLeaders;
    @SuppressWarnings("rawtypes")
    Stack<String> models_for_groups;
    @SuppressWarnings("rawtypes")
    Stack<String> weemen_models_for_groups;
    private boolean scene_ended;
    @SuppressWarnings("rawtypes")
    private final Vector<SceneActorsPool> pool;
    private final ScriptRef uid;
    Random rnd;
    double row_wide;
    double line_wide;
    double row_hight;
    @SuppressWarnings("rawtypes")
    Vector<SCRpersongroup> allGroups;
    long winners_scene;
    String DWORDname;
    Vector<SCRuniperson> CrowdPersons;
    Vector<cPhotografer> _Photografers;

    /**
     * Constructs ...
     *
     */
    public BigRaceCrowd() {
        this.groupLeaders = new Vector<SCRtalkingperson>();
        this.women_groupLeaders = new Vector<SCRtalkingperson>();
        this.models_for_groups = new Stack<String>();
        this.weemen_models_for_groups = new Stack<String>();
        this.scene_ended = false;
        this.pool = new Vector<SceneActorsPool>();
        this.uid = new ScriptRef();
        this.rnd = new Random();
        this.row_wide = 1.1D;
        this.line_wide = 0.9D;
        this.row_hight = 0.2D;
        this.allGroups = new Vector<SCRpersongroup>();
        this.winners_scene = 0L;
        this.DWORDname = "DWORD_BigRace_WHFinish";
        this.CrowdPersons = new Vector<SCRuniperson>();
        this._Photografers = new Vector<cPhotografer>();
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
    @Override
    public void removeRef() {
        this.uid.removeRef(this);
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
     * @param p
     */
    @Override
    public void updateNative(int p) {}

    int getRnd(int nm) {
        return getPrefix(nm);
    }

    int getPrefix(int nm) {
        if (nm <= 1) {
            return nm;
        }

        int res = (int) (nm * this.rnd.nextDouble() + 1.0D);

        if (res > nm) {
            res = nm;
        }

        return res;
    }

    double getRandom(double nm) {
        if (nm <= 0.0D) {
            return nm;
        }

        return (nm * this.rnd.nextDouble());
    }

    double getRandom(double MIN, double MAX) {
        return (MIN + getRandom(MAX - MIN));
    }

    @SuppressWarnings("unchecked")
    void buildModelStack() {
        if (getRnd(2) == 1) {
            for (int i = 0; i < 4; ++i) {
                int pref = getPrefix(10);
                String res = "";

                if (pref >= 10) {
                    res = "Man_0" + pref;
                } else {
                    res = "Man_00" + pref;
                }

                this.models_for_groups.push(res);
            }
        } else {
            for (int i = 0; i < 4; ++i) {
                int pref = getPrefix(10);
                String res = "";

                if (pref >= 10) {
                    res = "Woman0" + pref;
                } else {
                    res = "Woman00" + pref;
                }

                this.weemen_models_for_groups.push(res);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String gunicidName() {
        String res = new String(unicidName);

        res = res + (++unicidNameNumber);

        return res;
    }

    /**
     * Method description
     *
     */
    public void delsome() {
        for (int i = 0; i < this.allGroups.size(); ++i) {
            SCRpersongroup gr = this.allGroups.elementAt(i);

            SCRpersongroup.removeGroup(gr);
        }

        for (int i = 0; i < this.groupLeaders.size(); ++i) {
            SCRtalkingperson gr = this.groupLeaders.elementAt(i);

            gr.stop();
        }

        for (int i = 0; i < this.women_groupLeaders.size(); ++i) {
            SCRtalkingperson gr = this.women_groupLeaders.elementAt(i);

            gr.stop();
        }
    }

    /**
     * Method description
     *
     *
     * @param base_pos
     * @param base_dir
     */
    @SuppressWarnings("unchecked")
    public void make_group(vectorJ base_pos, vectorJ base_dir) {
        vectorJ norm = new vectorJ(base_dir.y, -base_dir.x, 0.0D);
        vectorJ zlevel = Collide.collidePoint(base_pos.oPlusN(new vectorJ(0.0D, 0.0D, 100.0D)),
                             base_pos.oPlusN(new vectorJ(0.0D, 0.0D, -100.0D)));

        base_dir.z = zlevel.z;

        int sz = this.models_for_groups.size();
        int sz_2 = (int) Math.floor(sz * 0.5D);
        int sz_remains = sz - sz_2;
        SCRuniperson UP = SCRuniperson.createCutSceneAmbientPerson(this.models_for_groups.pop(), gunicidName(), null,
                              base_pos.oPlusN(norm).oPlusN(base_dir), base_dir);

        UP.SetInWarehouseEnvironment();

        SCRtalkingperson pers = new SCRtalkingperson(UP);

        UP.play();

        SCRpersongroup group = SCRpersongroup.create(UP);

        this.allGroups.add(group);
        this.groupLeaders.add(pers);

        if (sz_2 > 1) {
            for (int i = 1; i < sz_2; ++i) {
                vectorJ shift = new vectorJ(base_dir);

                shift.mult(i);

                SCRuniperson UP1 = SCRuniperson.createCutSceneAmbientPerson(this.models_for_groups.pop(),
                                       gunicidName(), null, base_pos.oPlusN(norm).oPlusN(shift), base_dir);

                UP1.SetInWarehouseEnvironment();
                UP1.play();
                group.addPerson(UP1);
            }
        }

        if (sz_remains >= 1) {
            for (int i = 0; i < sz_remains; ++i) {
                vectorJ shift = new vectorJ(base_dir);

                shift.mult(i);

                SCRuniperson UP1 = SCRuniperson.createCutSceneAmbientPerson(this.models_for_groups.pop(),
                                       gunicidName(), null, base_pos.oPlusN(shift), base_dir);

                UP1.SetInWarehouseEnvironment();
                UP1.play();
                group.addPerson(UP1);
            }
        }
    }

    @SuppressWarnings("unchecked")
    void crowPlaceSomeWhere() {
        vectorJ place = new vectorJ(getPrefix(30) - 15, getPrefix(30) - 15, 0.0D);

        for (int OUT = 0; OUT < 2; ++OUT) {
            for (int i = 0; i < 2; ++i) {
                vectorJ pos = new vectorJ(OUT * 2 + 2 * i, 2 * i - (OUT * 2), 1.0D);

                pos.oPlus(place);

                vectorJ dir = new vectorJ(Math.cos(i), Math.sin(i), 0.0D);

                for (int j = 0; j < 4; ++j) {
                    int pref = getPrefix(20);
                    String res = "";

                    if (pref >= 10) {
                        res = "Man_0" + pref;
                    } else {
                        res = "Man_00" + pref;
                    }

                    this.models_for_groups.push(res);
                }

                make_group(pos, dir);
            }

            eng.CreateInfinitCycleScriptAnimation(this, 5.0D + getRandom(5.0D));
        }
    }

    /**
     * Method description
     *
     *
     * @param dir
     * @param pos1
     * @param pos2
     */
    public void nativePlaces(vectorJ dir, vectorJ pos1, vectorJ pos2) {
        for (int i = 0; i < 10; ++i) {
            vectorJ shift = new vectorJ(dir);

            shift.mult(i * 4);
            buildModelStack();
            make_group(pos1.oPlusN(shift), dir);
        }

        for (int i = 0; i < 10; ++i) {
            buildModelStack();

            vectorJ shift = new vectorJ(dir);

            shift.mult(i * 4);
            make_group(pos2.oPlusN(shift), dir);
        }

        play1();
    }

    /**
     * Method description
     *
     */
    public void buildModelStackHuge() {
        for (int i = 0; i < 50; ++i) {
            buildModelStack();
        }
    }

    double gRowWidth() {
        return (this.row_wide * getRandom(0.6D, 1.4D));
    }

    double gLineWidth() {
        return (this.line_wide * getRandom(0.8D, 1.2D));
    }

    vectorJ make2dPosition(vectorJ base_pos, vectorJ base_dir, int row, int place) {
        vectorJ shiftline = new vectorJ(-base_dir.y, base_dir.x, 0.0D);
        vectorJ shiftrow = new vectorJ(-base_dir.x, -base_dir.y, -base_dir.z);

        shiftrow.mult(gRowWidth() + (row - 1) * this.row_wide);

        vectorJ zshift = new vectorJ(0.0D, 0.0D, 1.0D);

        zshift.mult(row * this.row_hight);
        shiftline.mult(gLineWidth() + (place - 1) * this.line_wide);

        vectorJ res = base_pos.oPlusN(shiftline).oPlusN(shiftrow).oPlusN(zshift);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param base_pos
     * @param base_dir
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void makeGroupHuge(vectorJ base_pos, vectorJ base_dir) {
        int sz = this.models_for_groups.size() + this.weemen_models_for_groups.size();
        int people_in_group = 4;
        int count_people_in_group = 0;
        int places = 25;
        int rows = (int) Math.ceil(sz / places);
        Vector<OnePlace> allPlaces = new Vector<OnePlace>();

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < places; ++j) {
                allPlaces.add(new OnePlace(i, j));
            }
        }

        for (int v = 0; v < allPlaces.size(); ++v) {
            OnePlace el = allPlaces.elementAt(v);
            int r = getRnd(allPlaces.size() - 1);
            OnePlace repl_el = allPlaces.elementAt(r);

            allPlaces.setElementAt(el, r);
            allPlaces.setElementAt(repl_el, v);
        }

        SCRpersongroup group = null;
        int man_lim = this.models_for_groups.size();

        for (int i = 0; i < sz; ++i) {
            if (count_people_in_group >= people_in_group) {
                count_people_in_group = 0;
            }

            OnePlace place = allPlaces.elementAt(i);
            Stack<String> chosen = this.models_for_groups;
            Vector<SCRtalkingperson> chosen_leaders = this.groupLeaders;
            boolean steelman = true;

            if (i >= man_lim) {
                steelman = false;

                if (i == man_lim) {
                    count_people_in_group = 0;
                }

                chosen = this.weemen_models_for_groups;
                chosen_leaders = this.women_groupLeaders;
            }

            if ((steelman) && (getRandom(1.0D) < 0.2D)) {
                SCRuniperson UP = SCRuniperson.createCutSceneAmbientPerson(chosen.pop(), gunicidName(), null,
                                      make2dPosition(base_pos, base_dir, place.row, place.place), base_dir);

                UP.SetInWarehouseEnvironment();
                platPhotografAnimation(UP);
            } else if (0 == count_people_in_group) {
                SCRuniperson UP = SCRuniperson.createCutSceneAmbientPerson(chosen.pop(), gunicidName(), null,
                                      make2dPosition(base_pos, base_dir, place.row, place.place), base_dir);

                UP.SetInWarehouseEnvironment();

                SCRtalkingperson pers = new SCRtalkingperson(UP);

                UP.play();
                group = SCRpersongroup.create(UP);
                chosen_leaders.add(pers);
                this.allGroups.add(group);
                ++count_people_in_group;
            } else {
                SCRuniperson UP1 = SCRuniperson.createCutSceneAmbientPerson(chosen.pop(), gunicidName(), null,
                                       make2dPosition(base_pos, base_dir, place.row, place.place), base_dir);

                UP1.SetInWarehouseEnvironment();
                UP1.play();
                group.addPerson(UP1);
                ++count_people_in_group;
            }
        }
    }

    void clearGroups() {
        for (int i = 0; i < this.allGroups.size(); ++i) {
            SCRpersongroup gr = this.allGroups.elementAt(i);

            SCRpersongroup.removeGroup(gr);
        }
    }

    /**
     * Method description
     *
     *
     * @param dir
     * @param pos
     */
    public void nativePlaces(vectorJ dir, vectorJ pos) {
        buildModelStackHuge();
        makeGroupHuge(pos, dir);
        play1();
    }

    /**
     * Method description
     *
     *
     * @param place
     * @param pos
     * @param matr
     */
    @SuppressWarnings("unchecked")
    public void winnersAnimation(int place, vectorJ pos, matrixJ matr) {
        eng.setdword(this.DWORDname, 1);

        PhotografPreset preset = new PhotografPreset();

        preset.P = pos;
        preset.M = matr;
        preset.Mcam = new matrixJ(preset.M);
        this.pool.add(new SceneActorsPool("MC", Crew.getIgrok().getModel()));
        Crew.getIgrok().getModel().SetInWarehouseEnvironment();

        SCRuniperson perec1 = SCRuniperson.createCutSceneAmbientPerson("Man_002", "Man_002_winer");

        perec1.SetInWarehouseEnvironment();
        perec1.lockPerson();

        SCRuniperson perec2 = SCRuniperson.createCutSceneAmbientPerson("Man_001", "Man_001_winer");

        perec2.SetInWarehouseEnvironment();
        perec2.lockPerson();
        this.pool.add(new SceneActorsPool("man1", perec1));
        this.pool.add(new SceneActorsPool("man2", perec2));

        long sc = 0L;

        switch (place) {
         case 1 :
             sc = scenetrack.CreateSceneXMLPool("bigrace_1place", 9, this.pool, preset);

             break;

         case 2 :
             sc = scenetrack.CreateSceneXMLPool("bigrace_2place", 9, this.pool, preset);

             break;

         case 3 :
             sc = scenetrack.CreateSceneXMLPool("bigrace_3place", 9, this.pool, preset);
        }

        SCRuniperson[] persons = scenetrack.GetSceneAll(sc);

        for (SCRuniperson singlePerson : persons) {
            singlePerson.SetInWarehouseEnvironment();
        }

        event.eventObject((int) sc, this, "endscene");
        this.winners_scene = sc;
    }

    /**
     * Method description
     *
     *
     * @param pos
     * @param matr
     */
    public void winnersAnimationBackGround(vectorJ pos, matrixJ matr) {
        eng.setdword(this.DWORDname, 1);

        PhotografPreset preset = new PhotografPreset();

        preset.P = pos;
        preset.M = matr;
        preset.Mcam = new matrixJ(preset.M);
        scenetrack.CreateSceneXML("bigrace_background", 25, preset);
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings("rawtypes")
    public void endscene() {
        Iterator<SceneActorsPool> iter = this.pool.iterator();
        int i = 0;

        while (iter.hasNext()) {
            SceneActorsPool pool_item = iter.next();

            if (i != 0) {
                pool_item.unlock();
            }

            ++i;
        }

        this.scene_ended = true;
        scenetrack.DeleteScene(this.winners_scene);
        clearPhoto();
        clearGroups();
        clearCrowd();
        delsome();
        Helper.restoreCameraToIgrokCar();
        event.Setevent(9001);
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings("rawtypes")
    public void play1() {
        Iterator<SCRtalkingperson> iter = this.groupLeaders.iterator();

        while (iter.hasNext()) {
            SCRtalkingperson p = iter.next();

            if (getRandom(1.0D) > 0.7D) {
                p.playAnimation("m_in_crowd_0" + getPrefix(3), 0.5D);
            }
        }

        iter = this.women_groupLeaders.iterator();

        while (iter.hasNext()) {
            SCRtalkingperson p = iter.next();

            if (getRandom(1.0D) > 0.7D) {
                p.playAnimation("w_in_crowd_0" + getPrefix(3), 0.5D);
            }
        }
    }

    /**
     * Method description
     *
     */
    public void play2() {
        Iterator<SCRtalkingperson> iter = this.groupLeaders.iterator();

        while (iter.hasNext()) {
            SCRtalkingperson p = iter.next();

            p.playAnimation("m_in_crowd_02", 0.5D);
        }
    }

    /**
     * Method description
     *
     */
    public void play3() {
        Iterator<SCRtalkingperson> iter = this.groupLeaders.iterator();

        while (iter.hasNext()) {
            SCRtalkingperson p = iter.next();

            p.playAnimation("m_in_crowd_03", 0.5D);
        }
    }

    /**
     * Method description
     *
     *
     * @param pers
     */
    public void platPhotografAnimation(SCRuniperson pers) {
        PhotografPreset preset = new PhotografPreset();

        preset.P = pers.GetPosition();
        preset.M = pers.GetMatrix();
        preset.Mcam = new matrixJ(preset.M);

        SCRuniperson camm = SCRuniperson.createCutSceneAmbientPerson("Model_Photocam_01", gunicidName(), null,
                                preset.P, preset.M.v1);

        camm.SetInWarehouseEnvironment();
        this.CrowdPersons.add(camm);
        this.CrowdPersons.add(pers);

        SceneActorsPool poolman = new SceneActorsPool("man", pers);
        SceneActorsPool poolcam = new SceneActorsPool("cam", camm);

        poolman.lock();
        poolcam.lock();

        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();

        pool.add(poolman);
        pool.add(poolcam);

        cPhotografer photo = new cPhotografer();

        this._Photografers.add(photo);
        photo.pool = pool;
        photo.preset = preset;
        ThreadTask.create(photo);
    }

    void clearPhoto() {
        for (int i = 0; i < this._Photografers.size(); ++i) {
            cPhotografer ph = this._Photografers.elementAt(i);

            scenetrack.StopScene(ph.scene);
        }

        this._Photografers.clear();
    }

    void clearCrowd() {}

    /**
     * Method description
     *
     *
     * @param dt
     *
     * @return
     */
    @Override
    public boolean animaterun(double dt) {
        if (this.scene_ended) {
            return true;
        }

        play1();

        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public IXMLSerializable getXmlSerializator() {
        return null;
    }

    class OnePlace {
        int row;
        int place;

        OnePlace(int paramInt1, int paramInt2) {
            this.row = paramInt1;
            this.place = place;
        }
    }


    class PhotografPreset {
        vectorJ P;
        matrixJ M;
        matrixJ Mcam;

        PhotografPreset() {
            this.P = null;
            this.M = null;
            this.Mcam = null;
        }
    }


    class cPhotografer implements Ithreadprocedure {
        ThreadTask safe;
        Vector<SceneActorsPool> pool;
        BigRaceCrowd.PhotografPreset preset;

        /** Field description */
        public volatile long scene;

        cPhotografer() {
            this.safe = null;
            this.pool = null;
            this.preset = null;
        }

        /**
         * Method description
         *
         */
        @Override
        public void call() {
            this.scene = trackscripts.initSceneXML("bigrace_photoman", this.pool, this.preset);

            Iterator<SceneActorsPool> iterpool = this.pool.iterator();

            while (iterpool.hasNext()) {
                SceneActorsPool item = iterpool.next();

                item.unlock();
            }

            scenetrack.moveSceneTime(this.scene, 10.0D * Math.random());
            scenetrack.ReplaceSceneFlags(this.scene, 5);
            event.eventObject((int) this.scene, this.safe, "_resum");
            this.safe._susp();
            scenetrack.DeleteScene(this.scene);
        }

        /**
         * Method description
         *
         *
         * @param safe
         */
        @Override
        public void take(ThreadTask safe) {
            this.safe = safe;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
