/*
 * @(#)ApplicationTab.java   13/08/26
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


package rnr.src.menuscript.office;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.menues;
import rnr.src.menuscript.IUpdateListener;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public abstract class ApplicationTab {
    private static final String[] BUTTONS = { "BUTTON - APPLY - ", "BUTTON - DISCARD - " };
    private static final String[] METHODS = { "onApply", "onDiscard" };
    private final ArrayList<IDirtyListener> dirty_listeners = new ArrayList();
    private final ArrayList<IWarningListener> warning_listeners = new ArrayList();
    private final ArrayList<IUpdateListener> update_listeners = new ArrayList();
    protected boolean f_dirty = true;
    OfficeMenu parent = null;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param name
     * @param parent
     */
    public ApplicationTab(long _menu, String name, OfficeMenu parent) {
        for (int i = 0; i < BUTTONS.length; ++i) {
            long button = menues.FindFieldInMenu(_menu, BUTTONS[i] + name);

            if (0L != button) {
                menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button), this, METHODS[i], 4L);
            }
        }

        this.parent = parent;
    }

    protected void setDirty() {
        if (this.f_dirty) {
            return;
        }

        this.f_dirty = true;

        for (IDirtyListener listener : this.dirty_listeners) {
            listener.settedDirty(this);
        }
    }

    /**
     * Method description
     *
     *
     * @param lst
     */
    public void addListener(IWarningListener lst) {
        this.warning_listeners.add(lst);
    }

    /**
     * Method description
     *
     *
     * @param lst
     */
    public void addListenerUpdate(IUpdateListener lst) {
        this.update_listeners.add(lst);
    }

    /**
     * Method description
     *
     *
     * @param lst
     */
    public void addListenerDirty(IDirtyListener lst) {
        this.dirty_listeners.add(lst);
    }

    protected void makeNotEnoughMoney() {
        for (IWarningListener lst : this.warning_listeners) {
            lst.makeNotEnoughMoney();
        }
    }

    protected void makeNotEnoughTurns(int num_bases) {
        for (IWarningListener lst : this.warning_listeners) {
            lst.makeNotEnoughTurnOver(num_bases);
        }
    }

    protected void makeNotEnoughCars() {
        for (IWarningListener lst : this.warning_listeners) {
            lst.makeNotEnoughCars();
        }
    }

    protected void makeUpdate() {
        for (IUpdateListener lst : this.update_listeners) {
            lst.onUpdate();
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onApply(long _menu, MENUsimplebutton_field button) {
        apply();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onDiscard(long _menu, MENUsimplebutton_field button) {
        discard();
    }

    /**
     * Method description
     *
     */
    public abstract void afterInit();

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean update() {
        if (!(this.f_dirty)) {
            return false;
        }

        this.f_dirty = false;

        return true;
    }

    /**
     * Method description
     *
     */
    public abstract void deinit();

    /**
     * Method description
     *
     */
    public abstract void apply();

    /**
     * Method description
     *
     */
    public abstract void discard();
}


//~ Formatted in DD Std on 13/08/26
