/*
 * @(#)ModelManager.java   13/08/28
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

import rnr.src.rnrscr.ModelManager.ModelPresets;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class ModelManager {
    ArrayList<ModelPresets> peopleModels;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings("unchecked")
    public ModelManager() {
        this.peopleModels = new ArrayList<ModelPresets>();
    }

    @SuppressWarnings("unchecked")
    private void sort() {
        Collections.sort(this.peopleModels, new Sortir());
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param usageFlags
     * @param tag
     * @param isMan
     */
    public void AddModel(String name, int usageFlags, int tag, boolean isMan) {
        this.peopleModels.add(new ModelPresets(name, usageFlags, tag, isMan));
    }

    /**
     * Method description
     *
     *
     * @param avalablecollection
     * @param sotip
     * @param tag
     * @param num
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ArrayList<ModelPresets> getModels(ArrayList<ModelPresets> avalablecollection, int sotip, int tag, int num) {
        sort();

        ArrayList res = new ArrayList();
        Iterator iter = this.peopleModels.iterator();

        while (iter.hasNext()) {
            ModelPresets model = (ModelPresets) iter.next();
            Iterator hasthat = avalablecollection.iterator();
            boolean has_that = false;

            do {
                if (!(hasthat.hasNext())) {
                    break;
                }
            } while (!(model.equals(hasthat.next())));

            has_that = true;

            if (has_that) {
                continue;
            }

            if ((((tag == model.getTag()) || ((model.getTag() & tag) != 0))) && (model.getPlaceFlags() == sotip)) {
                res.add(model);

                if (0 == --num) {
                    break;
                }
            }
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param avalablecollection
     * @param sotip
     * @param tag
     * @param isMan
     * @param num
     *
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ArrayList<ModelPresets> getModels(ArrayList<ModelPresets> avalablecollection, int sotip, int tag,
            boolean isMan, int num) {
        if (0 == num) {
            return new ArrayList<ModelPresets>();
        }

        sort();

        ArrayList res = new ArrayList();
        Iterator iter = this.peopleModels.iterator();

        while (iter.hasNext()) {
            ModelPresets model = (ModelPresets) iter.next();
            Iterator hasthat = avalablecollection.iterator();
            boolean has_that = false;

            do {
                if (!(hasthat.hasNext())) {
                    break;
                }
            } while (!(model.equals(hasthat.next())));

            has_that = true;

            if (has_that) {
                continue;
            }

            if ((((tag == model.getTag()) || ((model.getTag() & tag) != 0))) && ((model.getPlaceFlags() & sotip) != 0)
                    && (model.is_man == isMan)) {
                res.add(model);

                if (0 == --num) {
                    break;
                }
            }
        }

        return res;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public static class ModelPresets {
        String name;
        int placeFlags;
        private int tag;
        int weigh;
        boolean is_man;

        ModelPresets() {
            this.name = new String("unknown");
            this.placeFlags = 0;
            this.tag = 0;
            this.weigh = 0;
            this.is_man = true;
        }

        /**
         * Constructs ...
         *
         *
         * @param model_name
         * @param flags
         * @param new_tag
         * @param is_man
         */
        public ModelPresets(String model_name, int flags, int new_tag, boolean is_man) {
            this.name = new String(model_name);
            this.placeFlags = flags;
            this.tag = new_tag;
            this.weigh = 0;
            this.is_man = is_man;
        }

        /**
         * Method description
         *
         */
        public void useIt() {
            this.weigh += 1;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isIs_man() {
            return this.is_man;
        }

        /**
         * Method description
         *
         *
         * @param is_man
         */
        public void setIs_man(boolean is_man) {
            this.is_man = is_man;
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
        public int getPlaceFlags() {
            return this.placeFlags;
        }

        /**
         * Method description
         *
         *
         * @param placeFlags
         */
        public void setPlaceFlags(int placeFlags) {
            this.placeFlags = placeFlags;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int getTag() {
            return this.tag;
        }

        /**
         * Method description
         *
         *
         * @param tag
         */
        public void setTag(int tag) {
            this.tag = tag;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int getWeigh() {
            return this.weigh;
        }

        /**
         * Method description
         *
         *
         * @param weigh
         */
        public void setWeigh(int weigh) {
            this.weigh = weigh;
        }
    }


    @SuppressWarnings("rawtypes")
    static class Sortir implements Comparator {

        /**
         * Method description
         *
         *
         * @param arg0
         * @param arg1
         *
         * @return
         */
        @Override
        public int compare(Object arg0, Object arg1) {
            return (((ModelManager.ModelPresets) arg0).weigh - ((ModelManager.ModelPresets) arg1).weigh);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
