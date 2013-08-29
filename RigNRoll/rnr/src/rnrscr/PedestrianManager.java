/*
 * @(#)PedestrianManager.java   13/08/28
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

import rnr.src.menu.JavaEvents;
import rnr.src.players.Crew;
import rnr.src.players.IdentiteNames;
import rnr.src.rnrcore.SCRperson;
import rnr.src.rnrscr.PedestrianManager.ModelUsage;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
public class PedestrianManager {
    private static final String MODEL_TO_CHOOSE = null;
    private static PedestrianManager instance = null;
    private final HashMap<String, ModelUsage> model_usages;
    private int population;
    private int nom_models;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings("unchecked")
    public PedestrianManager() {
        this.model_usages = new HashMap<String, ModelUsage>();
        this.population = 0;
        this.nom_models = 0;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static PedestrianManager getInstance() {
        if (instance == null) {
            instance = new PedestrianManager();
        }

        return instance;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        instance = null;
    }

    private void renewModelNames() {
        ArrayList<String> names = Crew.getInstance().getPoolNames();
        Iterator<String> iter = names.iterator();

        while (iter.hasNext()) {
            String name = iter.next();
            IdentiteNames info = new IdentiteNames(name);

            JavaEvents.SendEvent(57, 1, info);

            if ((null != MODEL_TO_CHOOSE) && (MODEL_TO_CHOOSE.compareTo(info.modelName) != 0)) {
                continue;
            }

            if (!(this.model_usages.containsKey(info.modelName))) {
                this.model_usages.put(info.modelName, new ModelUsage());
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private String getModelName() {
        Set<Entry<String, ModelUsage>> models = this.model_usages.entrySet();

        if (models.isEmpty()) {
            return null;
        }

        Iterator<Entry<String, ModelUsage>> iter = models.iterator();
        Map.Entry lowest = null;
        int num_lowest = 10000000;

        while (iter.hasNext()) {
            Map.Entry entry = iter.next();
            ModelUsage item = (ModelUsage) entry.getValue();

            if ((lowest == null) || (item.persons.size() < num_lowest)) {
                lowest = entry;
                num_lowest = item.persons.size();

                if (num_lowest == 0) {
                    break;
                }
            }
        }

        if (lowest == null) {
            return null;
        }

        return ((String) lowest.getKey());
    }

    private void addmodel(String model_name, SCRperson person) {
        ModelUsage usage = null;

        if (this.model_usages.containsKey(model_name)) {
            usage = this.model_usages.get(model_name);
        } else {
            usage = new ModelUsage();
            this.model_usages.put(model_name, usage);
        }

        usage.persons.add(person);
        this.nom_models += 1;
    }

    private boolean createPedestrian() {
        String model_name = getModelName();

        if (null == model_name) {
            return false;
        }

        SCRperson person = SFpedestrians.createPedestrian(model_name);

        addmodel(model_name, person);

        return true;
    }

    private void removePedestrian(int num_to_remove) {
        Collection<ModelUsage> models = this.model_usages.values();

        if (models.isEmpty()) {
            return;
        }

        int removed = 0;

        while (num_to_remove != removed) {
            Iterator<ModelUsage> iter = models.iterator();
            ModelUsage biggest = null;
            int num_biggest = 0;

            while (iter.hasNext()) {
                ModelUsage item = iter.next();

                if ((biggest == null) || (item.persons.size() > num_biggest)) {
                    biggest = item;
                    num_biggest = item.persons.size();
                }
            }

            if (biggest == null) {
                return;
            }

            if (biggest.persons.isEmpty()) {
                return;
            }

            SCRperson person_to_remove = biggest.persons.remove(0);

            person_to_remove.delete();
            ++removed;
            this.nom_models -= 1;
        }
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void removeNamedModel(String name) {
        ModelUsage models = this.model_usages.get(name);

        if (models.persons.isEmpty()) {
            return;
        }

        for (SCRperson pers : models.persons) {
            pers.delete();
            this.nom_models -= 1;
        }

        models.persons.clear();
    }

    /**
     * Method description
     *
     *
     * @param num
     */
    public void setPopulation(int num) {
        this.population = num;
    }

    /**
     * Method description
     *
     */
    public void process() {
        renewModelNames();

        if (this.population > this.nom_models) {
            while (true) {
                if ((this.population <= this.nom_models) || (!(createPedestrian()))) {
                    return;
                }
            }
        }

        if (this.population < this.nom_models) {
            removePedestrian(this.nom_models - this.population);
        }
    }

    static class ModelUsage {
        ArrayList<SCRperson> persons;

        ModelUsage() {
            this.persons = new ArrayList<SCRperson>();
        }
    }
}


//~ Formatted in DD Std on 13/08/28
