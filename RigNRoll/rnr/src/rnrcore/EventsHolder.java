/*
 * @(#)EventsHolder.java   13/08/26
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


package rnr.src.rnrcore;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.JavaEventCb;
import rnr.src.menu.JavaEvents;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
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
public class EventsHolder implements JavaEventCb {
    private static EventsHolder singleton = null;
    private final HashMap<Integer, ArrayList<IEventListener>> events;
    private final HashMap<Integer, ArrayList<IEventListener>> eventsToRemove;
    private final HashMap<Integer, ArrayList<IEventListener>> eventsToAdd;
    private boolean m_onQueue;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings("unchecked")
    public EventsHolder() {
        this.events = new HashMap<Integer, ArrayList<IEventListener>>();
        this.eventsToRemove = new HashMap<Integer, ArrayList<IEventListener>>();
        this.eventsToAdd = new HashMap<Integer, ArrayList<IEventListener>>();
        this.m_onQueue = false;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void add(int id, IEventListener listener) {
        if (!(this.m_onQueue)) {
            if (!(this.events.containsKey(Integer.valueOf(id)))) {
                @SuppressWarnings("rawtypes") ArrayList obj = new ArrayList();

                obj.add(listener);
                this.events.put(Integer.valueOf(id), obj);
                JavaEvents.RegisterEvent(id, this);
            } else {
                ((ArrayList) this.events.get(Integer.valueOf(id))).add(listener);
            }
        } else {
            addQueedAdd(this.eventsToAdd, id, listener);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void addQueedAdd(HashMap<Integer, ArrayList<IEventListener>> mapToAddTo, int id,
                                    IEventListener listener) {
        if (!(mapToAddTo.containsKey(Integer.valueOf(id)))) {
            ArrayList addedListener = new ArrayList();

            addedListener.add(listener);
            mapToAddTo.put(Integer.valueOf(id), addedListener);
        } else {
            ((ArrayList) mapToAddTo.get(Integer.valueOf(id))).add(listener);
        }
    }

    private void remove(int id, IEventListener listener) {
        if (!(this.m_onQueue)) {
            if (!(this.events.containsKey(Integer.valueOf(id)))) {
                return;
            }

            ArrayList<IEventListener> obj = this.events.get(Integer.valueOf(id));

            obj.remove(listener);
        } else {
            addQueedAdd(this.eventsToRemove, id, listener);
        }
    }

    private static EventsHolder gHolder() {
        if (singleton == null) {
            singleton = new EventsHolder();
        }

        return singleton;
    }

    /**
     * Method description
     *
     *
     * @param ID
     * @param value
     * @param obj
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void OnEvent(int ID, int value, Object obj) {
        if (!(this.events.containsKey(Integer.valueOf(ID)))) {
            return;
        }

        ArrayList<IEventListener> lsts = this.events.get(Integer.valueOf(ID));

        this.m_onQueue = true;

        for (IEventListener listener : lsts) {
            listener.on_event(value);
        }

        this.m_onQueue = false;

        Set set = this.eventsToRemove.entrySet();

        for (Iterator i$ = set.iterator(); i$.hasNext(); ) {
            Entry entry = (Map.Entry) i$.next();

            for (IEventListener listener : entry.getValue()) {
                remove(((Integer) entry.getKey()).intValue(), listener);
            }
        }

        Map.Entry entry;
        Set set = this.eventsToAdd.entrySet();

        for (Iterator i$ = set.iterator(); i$.hasNext(); ) {
            entry = (Map.Entry) i$.next();

            for (IEventListener listener : (ArrayList) entry.getValue()) {
                add(((Integer) entry.getKey()).intValue(), listener);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param id
     * @param listener
     */
    public static void addEventListenet(int id, IEventListener listener) {
        gHolder().add(id, listener);
    }

    /**
     * Method description
     *
     *
     * @param id
     * @param listener
     */
    public static void removeEventListenet(int id, IEventListener listener) {
        gHolder().remove(id, listener);
    }
}


//~ Formatted in DD Std on 13/08/26
