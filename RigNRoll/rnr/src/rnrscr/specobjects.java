/*
 * @(#)specobjects.java   13/08/28
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

import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class specobjects implements sotypes {
    private static specobjects instance = null;

    /** Field description */
    public int last_SO_zone;
    ModelManager modelSource;
    private final ArrayList<cSpecObjects> allLoadedObjCash;
    private final HashMap<String, cSpecObjects> allLoadedObjects;

    @SuppressWarnings("unchecked")
    specobjects() {
        this.modelSource = new ModelManager();
        this.modelSource.AddModel("BARMEN", 8, 1, true);
        this.modelSource.AddModel("Man_005", 16, 0, true);
        this.modelSource.AddModel("Man_003", 24, 0, true);
        this.modelSource.AddModel("Man_004", 26, 0, true);
        this.modelSource.AddModel("Man_002", 26, 0, true);
        this.modelSource.AddModel("Man_001", 26, 0, true);
        this.modelSource.AddModel("Man_006", 26, 0, true);
        this.modelSource.AddModel("Man_007", 26, 0, true);
        this.modelSource.AddModel("Man_008", 26, 0, true);
        this.modelSource.AddModel("Man_009", 10, 1, true);
        this.modelSource.AddModel("Man_010", 10, 1, true);
        this.modelSource.AddModel("Man_011", 26, 0, true);
        this.modelSource.AddModel("Man_012", 26, 0, true);
        this.modelSource.AddModel("Man_013", 26, 0, true);
        this.modelSource.AddModel("Man_014", 26, 0, true);
        this.modelSource.AddModel("Man_015", 26, 0, true);
        this.modelSource.AddModel("Man_016", 26, 0, true);
        this.modelSource.AddModel("Man_017", 26, 0, true);
        this.modelSource.AddModel("Man_018", 26, 0, true);
        this.modelSource.AddModel("Man_019", 26, 0, true);
        this.modelSource.AddModel("Man_020", 26, 0, true);
        this.modelSource.AddModel("Man_021", 26, 0, true);
        this.modelSource.AddModel("Man_022", 26, 0, true);
        this.modelSource.AddModel("Man_023", 26, 0, true);
        this.modelSource.AddModel("Man_024", 26, 0, true);
        this.modelSource.AddModel("Man_025", 26, 0, true);
        this.modelSource.AddModel("Man_026", 26, 0, true);
        this.modelSource.AddModel("Man_027", 26, 0, true);
        this.modelSource.AddModel("Man_028", 26, 0, true);
        this.modelSource.AddModel("Man_029", 26, 0, true);
        this.modelSource.AddModel("Woman001", 8, 0, false);
        this.modelSource.AddModel("Woman002", 8, 0, false);
        this.modelSource.AddModel("Woman003", 8, 0, false);
        this.modelSource.AddModel("Woman004", 8, 0, false);
        this.modelSource.AddModel("Woman005", 8, 0, false);
        this.modelSource.AddModel("Woman006", 8, 0, false);
        this.modelSource.AddModel("Woman007", 8, 0, false);
        this.modelSource.AddModel("Woman008", 8, 0, false);
        this.modelSource.AddModel("Woman009", 8, 0, false);
        this.modelSource.AddModel("Woman010", 8, 0, false);
        this.modelSource.AddModel("Woman011", 8, 0, false);
        this.modelSource.AddModel("Woman012", 8, 0, false);
        this.modelSource.AddModel("Woman013", 8, 0, false);
        this.modelSource.AddModel("Woman014", 8, 0, false);
        this.modelSource.AddModel("Woman015", 8, 0, false);
        this.modelSource.AddModel("Woman016", 8, 0, false);
        this.modelSource.AddModel("Woman017", 8, 0, false);
        this.modelSource.AddModel("Woman018", 8, 0, false);
        this.modelSource.AddModel("Woman019", 8, 0, false);
        this.allLoadedObjects = new HashMap<String, cSpecObjects>();
        this.allLoadedObjCash = new ArrayList<cSpecObjects>();
        this.last_SO_zone = 0;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static specobjects getInstance() {
        if (null == instance) {
            instance = new specobjects();
        }

        return instance;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static BarPresets getBarPresets() {
        BarPresets res = new BarPresets();
        cSpecObjects bar_info = getInstance().GetNearestLoadedBar();

        if (bar_info == null) {
            return null;
        }

        res.bardoor = "Space_DoorToBar_" + bar_info.name;
        res.P = bar_info.position;
        res.M = bar_info.matrix;

        return res;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static PolicePresets getPolicePresets() {
        PolicePresets res = new PolicePresets();
        cSpecObjects police_info = getInstance().GetNearestLoadedPolice();

        if (police_info == null) {
            return null;
        }

        res.P = police_info.position;
        res.M = police_info.matrix;

        return res;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static OfficePresets getOfficePresets() {
        OfficePresets res = new OfficePresets();
        cSpecObjects police_info = getInstance().GetNearestLoadedOffice();

        if (null == police_info) {
            return null;
        }

        res.P = police_info.position;
        res.M = police_info.matrix;

        return res;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Object GetModelSource() {
        return this.modelSource;
    }

    private cSpecObjects addLoadingObject(cSpecObjects object) {
        this.allLoadedObjects.put(object.name, object);

        for (cSpecObjects cached : this.allLoadedObjCash) {
            if ((cached.sotip == object.sotip) && (0 == cached.name.compareTo(object.name))) {
                cached.matrix = object.matrix;
                cached.position = object.position;

                return cached;
            }
        }

        this.allLoadedObjCash.add(object);

        return object;
    }

    private void removeObjectFromLoaded(String name) {
        this.allLoadedObjects.remove(name);
    }

    @SuppressWarnings("rawtypes")
    private cSpecObjects GetNearestObject(int sotip) {
        if (this.allLoadedObjects.isEmpty()) {
            return null;
        }

        double nearest = 2500000000.0D;
        vectorJ curpos = Helper.getCurrentPosition();

        if (curpos == null) {
            curpos = new vectorJ();
        }

        cSpecObjects lastgood = null;
        Set<Entry<String, cSpecObjects>> setLoaded = this.allLoadedObjects.entrySet();

        for (Map.Entry entry : setLoaded) {
            cSpecObjects current = (cSpecObjects) entry.getValue();

            if (current.sotip == sotip) {
                double diff = current.position.len2(curpos);

                if (diff < nearest) {
                    nearest = diff;
                    lastgood = current;
                }
            }
        }

        return lastgood;
    }

    /**
     * Method description
     *
     *
     * @param br
     *
     * @return
     */
    public cSpecObjects AddLoadedWH(cSpecObjects br) {
        br.sotip = 2;

        return addLoadingObject(br);
    }

    /**
     * Method description
     *
     *
     * @param br
     *
     * @return
     */
    public cSpecObjects AddLoadedBar(cSpecObjects br) {
        br.sotip = 8;

        return addLoadingObject(br);
    }

    /**
     * Method description
     *
     *
     * @param br
     *
     * @return
     */
    public cSpecObjects AddLoadedOffice(cSpecObjects br) {
        br.sotip = 1;

        return addLoadingObject(br);
    }

    /**
     * Method description
     *
     *
     * @param br
     *
     * @return
     */
    public cSpecObjects AddLoadedSTO(cSpecObjects br) {
        br.sotip = 16;

        return addLoadingObject(br);
    }

    /**
     * Method description
     *
     *
     * @param br
     *
     * @return
     */
    public cSpecObjects AddLoadedMotel(cSpecObjects br) {
        br.sotip = 4;

        return addLoadingObject(br);
    }

    /**
     * Method description
     *
     *
     * @param br
     *
     * @return
     */
    public cSpecObjects AddLoadedPolice(cSpecObjects br) {
        br.sotip = 64;

        return addLoadingObject(br);
    }

    /**
     * Method description
     *
     *
     * @param br
     *
     * @return
     */
    public cSpecObjects AddLoadedParking(cSpecObjects br) {
        br.sotip = 128;

        return addLoadingObject(br);
    }

    /**
     * Method description
     *
     *
     * @param br
     *
     * @return
     */
    public cSpecObjects AddLoadedTruckStopExit(cSpecObjects br) {
        br.sotip = 256;

        return addLoadingObject(br);
    }

    /**
     * Method description
     *
     *
     * @param br
     *
     * @return
     */
    public cSpecObjects AddLoadedTruckStopEnter(cSpecObjects br) {
        br.sotip = 512;

        return addLoadingObject(br);
    }

    /**
     * Method description
     *
     *
     * @param br
     *
     * @return
     */
    public cSpecObjects AddLoadedScenarioObject(cSpecObjects br) {
        br.sotip = 1024;

        return addLoadingObject(br);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public cSpecObjects getCurrentObject() {
        switch (this.last_SO_zone) {
         case 2 :
             return GetNearestLoadedWH();

         case 8 :
             return GetNearestLoadedBar();

         case 16 :
             return GetNearestLoadedSTO();

         case 4 :
             return GetNearestLoadedMotel();

         case 1 :
             return GetNearestLoadedOffice();

         case 64 :
             return GetNearestLoadedPolice();
        }

        return GetNearestObject(this.last_SO_zone);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public cSpecObjects GetNearestTruckStopExit() {
        return GetNearestObject(256);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public cSpecObjects GetNearestTruckStopEnter() {
        return GetNearestObject(512);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public cSpecObjects GetNearestLoadedBar() {
        return GetNearestObject(8);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public cSpecObjects GetNearestLoadedWH() {
        return GetNearestObject(2);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public cSpecObjects GetNearestLoadedSTO() {
        return GetNearestObject(16);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public cSpecObjects GetNearestLoadedMotel() {
        return GetNearestObject(4);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public cSpecObjects GetNearestLoadedOffice() {
        return GetNearestObject(1);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public cSpecObjects GetNearestLoadedPolice() {
        return GetNearestObject(64);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public cSpecObjects GetNearestLoadedScenarioObject() {
        return GetNearestObject(1024);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public cSpecObjects GetLoadedNamedScenarioObject(String name) {
        return (this.allLoadedObjects.get(name));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public cSpecObjects GetNearestLoadedParkingPlace() {
        return GetNearestObject(128);
    }

    /**
     * Method description
     *
     *
     * @param lengthlimit
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public cSpecObjects GetNearestLoadedParkingPlace_nearBar(double lengthlimit) {
        cSpecObjects so = GetNearestLoadedBar();

        if (null == so) {
            return null;
        }

        cSpecObjects nearest = null;
        double len = lengthlimit * lengthlimit;
        Set<Entry<String, cSpecObjects>> setLoadedObjects = this.allLoadedObjects.entrySet();

        for (Map.Entry entry : setLoadedObjects) {
            cSpecObjects ob = (cSpecObjects) entry.getValue();

            if (128 == ob.sotip) {
                ;
            }

            double px = so.position.x - ob.position.x;
            double py = so.position.y - ob.position.y;
            double pz = so.position.z - ob.position.z;
            double testlen = px * px + py * py + pz * pz;

            if (testlen < len) {
                len = testlen;
                nearest = ob;
            }
        }

        return nearest;
    }

    /**
     * Method description
     *
     *
     * @param barfind
     * @param lengthlimit
     *
     * @return
     */
    public cSpecObjects GetNearestLoadedParkingPlace_nearBarNamed(String barfind, double lengthlimit) {
        cSpecObjects so = GetNearestLoadedBar();

        if ((null == so) || (0 != so.name.compareTo(barfind))) {
            return null;
        }

        return GetNearestLoadedParkingPlace_nearBar(lengthlimit);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void DeleteLoadedBar(String name) {
        removeObjectFromLoaded(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void DeleteLoadedWH(String name) {
        removeObjectFromLoaded(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void DeleteLoadedScenarioObject(String name) {
        removeObjectFromLoaded(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public boolean ifLoadedBar(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public boolean ifLoadedOffice(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public boolean ifLoadedMotel(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public boolean ifLoadedRepair(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public boolean ifLoadedPolice(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public boolean ifLoadedParkingPlaces(String name) {
        return this.allLoadedObjects.containsKey(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void DeleteLoadedOffice(String name) {
        removeObjectFromLoaded(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void DeleteLoadedSTO(String name) {
        removeObjectFromLoaded(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void DeleteLoadedMotel(String name) {
        removeObjectFromLoaded(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void DeleteLoadedPolice(String name) {
        removeObjectFromLoaded(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void DeleteLoadedParking(String name) {
        removeObjectFromLoaded(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void DeleteLoadedTruckStopExit(String name) {
        removeObjectFromLoaded(name);
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void DeleteLoadedTruckStopEnter(String name) {
        removeObjectFromLoaded(name);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<cSpecObjects> getAllLoadedObjCash() {
        return this.allLoadedObjCash;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setAllLoadedObjCash(ArrayList<cSpecObjects> value) {
        ArrayList<cSpecObjects> toAdd = new ArrayList<cSpecObjects>();

        for (cSpecObjects newObject : value) {
            boolean wasInList = false;

            for (cSpecObjects currentObject : this.allLoadedObjCash) {
                if ((newObject.sotip == currentObject.sotip) && (newObject.name.compareTo(currentObject.name) == 0)) {
                    wasInList = true;
                    currentObject.SetPresets(newObject.Presets());

                    break;
                }
            }

            if (!(wasInList)) {
                toAdd.add(newObject);
            }
        }

        this.allLoadedObjCash.addAll(toAdd);
    }

    static class BarPresets {
        String bardoor;
        matrixJ M;
        vectorJ P;
    }


    static class KeyObjects {
        private String name;
        private int type;

        KeyObjects(String name, int type) {
            this.name = name;
            this.type = type;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public String getName() {
            return this.name;
        }

        /**
         * Method description
         *
         *
         * @param name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int getType() {
            return this.type;
        }

        /**
         * Method description
         *
         *
         * @param type
         */
        public void setType(int type) {
            this.type = type;
        }
    }


    static class OfficePresets {
        matrixJ M;
        vectorJ P;
    }


    static class PolicePresets {
        matrixJ M;
        vectorJ P;
    }
}


//~ Formatted in DD Std on 13/08/28
