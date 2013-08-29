/*
 * @(#)KeyBindingsData.java   13/08/26
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


package rnr.src.menuscript.keybind;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.Log;

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
public class KeyBindingsData {
    private String deviceName;
    private final ArrayList<Bind> actions;
    private boolean f_check_access;

    /**
     * Constructs ...
     *
     */
    public KeyBindingsData() {
        this.actions = new ArrayList<Bind>();
        this.f_check_access = false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getDeviceName() {
        return this.deviceName;
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void setDeviceName(String name) {
        this.deviceName = name;
    }

    /**
     * Method description
     *
     *
     * @param bind
     */
    public void addBind(Bind bind) {
        if (this.f_check_access) {
            Log.menu("KeyBindingsData. Wrong add BindData bahaivouir.");

            return;
        }

        this.actions.add(bind);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Iterator<Bind> getIterator() {
        if (this.f_check_access) {
            Log.menu("KeyBindingsData. Wrong add getIterator bahaivouir.");

            return null;
        }

        this.f_check_access = true;

        return this.actions.iterator();
    }

    /**
     * Method description
     *
     */
    public void freeIterator() {
        if (!(this.f_check_access)) {
            Log.menu("KeyBindingsData. Wrong add freeIterator bahaivouir.");

            return;
        }

        this.f_check_access = false;
    }

    /**
     * Method description
     *
     *
     * @param bind
     */
    public void updateBind(Bind bind) {
        Iterator<?> iter = getIterator();

        while (iter.hasNext()) {
            Bind original = (Bind) iter.next();

            if (original.action.getActionnom() == bind.action.getActionnom()) {
                if (null != bind.axe) {
                    original.axe = bind.axe.clone();
                } else {
                    original.axe = null;
                }

                if (null != bind.key) {
                    original.key = bind.key.clone();
                } else {
                    original.key = null;
                }
            }
        }

        freeIterator();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int size() {
        return this.actions.size();
    }

    /**
     * Method description
     *
     *
     * @param i
     *
     * @return
     */
    public Bind getBind(int i) {
        if (i >= size()) {
            return null;
        }

        return (this.actions.get(i));
    }
}


//~ Formatted in DD Std on 13/08/26
