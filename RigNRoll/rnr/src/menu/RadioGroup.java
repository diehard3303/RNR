/*
 * @(#)RadioGroup.java   13/08/25
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
   Released under the BSD 3 clause license
Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this 
    list of conditions and the following disclaimer. Redistributions in binary 
    form must reproduce the above copyright notice, this list of conditions and 
    the following disclaimer in the documentation and/or other materials 
    provided with the distribution. Neither the name of the DieHard Development 
    nor the names of its contributors may be used to endorse or promote products 
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 *
 *
 *
 */


package rnr.src.menu;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.eng;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class RadioGroup {
    private static final String[] METHODS_ACTIVEPRESS_TRACK = { "onPressEvent", "onReleaseEvent", "onReleaseEvent",
            "onActive", "onPassive" };
    private static final int[] EVENTS_ACTIVEPRESS = { 8, 4, 22, 3, 5 };
    private static final int SINGLE_SELECTION = 1;
    private static final int MULTY_SELECTION = 2;
    protected ArrayList<IRadioChangeListener> listeners = new ArrayList<IRadioChangeListener>();
    protected ArrayList<IRadioAccess> access_listeners = new ArrayList<IRadioAccess>();
    private final ArrayList<IActivePressedTracker> activepressed_listeners = new ArrayList<IActivePressedTracker>();
    MENUbutton_field[] buttons = null;
    protected boolean undersession = false;
    protected boolean silent_selecting = false;
    protected int focus_member = 0;
    boolean f_deselecting = false;
    private int mode = 1;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param buttons
     */
    public RadioGroup(long _menu, MENUbutton_field[] buttons) {
        this.buttons = buttons;

        for (int i = 0; i < buttons.length; ++i) {
            menues.SetScriptOnControl(_menu, buttons[i], this, "onSelect", 10L);
            menues.SetScriptOnControl(_menu, buttons[i], this, "onDeselectSelect", 11L);
        }
    }

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param buttons
     * @param track_active_pressed
     */
    public RadioGroup(long _menu, MENUbutton_field[] buttons, boolean track_active_pressed) {
        this.buttons = buttons;

        for (int i = 0; i < buttons.length; ++i) {
            menues.SetScriptOnControl(_menu, buttons[i], this, "onSelect", 10L);
            menues.SetScriptOnControl(_menu, buttons[i], this, "onDeselectSelect", 11L);

            if (track_active_pressed) {
                for (int j = 0; j < METHODS_ACTIVEPRESS_TRACK.length; ++j) {
                    menues.SetScriptOnControl(_menu, buttons[i], this, METHODS_ACTIVEPRESS_TRACK[j],
                                              EVENTS_ACTIVEPRESS[j]);
                }
            }
        }
    }

    protected void beginsession() {
        this.undersession = true;
    }

    protected void endsession() {
        this.undersession = false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getSelected() {
        return this.focus_member;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int[] getMultySelected() {
        int[] all = getSelectedLines();
        int count = 0;

        for (int i : all) {
            count += i;
        }

        int[] res = new int[count];

        count = 0;

        int count_all = 0;

        for (int i : all) {
            if (i != 0) {
                res[(count++)] = count_all;
            }

            ++count_all;
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param nom
     */
    public void deselect(int nom) {
        if ((nom >= 0) && (nom <= this.buttons.length - 1)) {
            menues.SetFieldState(this.buttons[nom].nativePointer, 0);
        } else {
            eng.err("bad select action on RadioGroup. nom is " + nom);
        }
    }

    /**
     * Method description
     *
     *
     * @param nom
     */
    public void silentDeselect(int nom) {
        this.silent_selecting = true;

        if ((nom >= 0) && (nom <= this.buttons.length - 1)) {
            menues.SetFieldState(this.buttons[nom].nativePointer, 0);
        } else {
            eng.err("bad select action on RadioGroup. nom is " + nom);
        }

        this.silent_selecting = false;
    }

    /**
     * Method description
     *
     *
     * @param nom
     */
    public void select(int nom) {
        if ((nom >= 0) && (nom <= this.buttons.length - 1)) {
            menues.SetFieldState(this.buttons[nom].nativePointer, 1);
        } else {
            eng.err("bad select action on RadioGroup. nom is " + nom);
        }
    }

    /**
     * Method description
     *
     *
     * @param nom
     * @param value
     */
    public void makeactive(int nom, boolean value) {
        if ((nom >= 0) && (nom <= this.buttons.length - 1)) {
            menues.setActiveControl(this.buttons[nom].nativePointer, value);
        } else {
            eng.err("bad makeactive action on RadioGroup. nom is " + nom);
        }
    }

    /**
     * Method description
     *
     *
     * @param nom
     * @param value
     */
    public void makepressed(int nom, boolean value) {
        if ((nom >= 0) && (nom <= this.buttons.length - 1)) {
            menues.setPressedControl(this.buttons[nom].nativePointer, value);
        } else {
            eng.err("bad makeactive action on RadioGroup. nom is " + nom);
        }
    }

    /**
     * Method description
     *
     *
     * @param nom
     */
    public void silentSelect(int nom) {
        this.silent_selecting = true;

        if ((nom >= 0) && (nom <= this.buttons.length - 1)) {
            menues.SetFieldState(this.buttons[nom].nativePointer, 1);
        } else {
            eng.err("bad select action on RadioGroup. nom is " + nom);
        }

        this.silent_selecting = false;
    }

    /**
     * Method description
     *
     */
    public void deselectall() {
        this.f_deselecting = true;

        for (int i = 0; i < this.buttons.length; ++i) {
            menues.SetFieldState(this.buttons[i].nativePointer, 0);
        }

        this.focus_member = 0;
        this.f_deselecting = false;
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void addListener(IRadioChangeListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void removeListener(IRadioChangeListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void addAccessListener(IRadioAccess listener) {
        this.access_listeners.add(listener);
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void addActiveListener(IActivePressedTracker listener) {
        this.activepressed_listeners.add(listener);
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void removeAccessListener(IRadioAccess listener) {
        this.access_listeners.remove(listener);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onSelect(long _menu, MENUbutton_field button) {
        if ((this.undersession) || (this.f_deselecting) || ((this.silent_selecting) && (this.mode == 2))) {
            return;
        }

        int[] states = new int[this.buttons.length];
        boolean can_be_selected = true;
        Iterator<IRadioAccess> iter = this.access_listeners.iterator();

        do {
            if (!(iter.hasNext())) {
                break;
            }
        } while (iter.next().controlAccessed(button, 1));

        can_be_selected = false;

        int selected = getButtonNom(button);

        if (can_be_selected) {
            this.focus_member = selected;
        }

        switch (this.mode) {
         case 1 :
             if (can_be_selected) {
                 for (int i = 0; i < states.length; ++i) {
                     states[i] = ((i == selected)
                                  ? 1
                                  : 0);
                 }
             }
         case 2 :
        }

        beginsession();

        switch (this.mode) {
         case 1 :
             if (!(can_be_selected)) {
                 menues.SetFieldState(button.nativePointer, 0);
             } else {
                 for (int i = 0; i < this.buttons.length; ++i) {
                     menues.SetFieldState(this.buttons[i].nativePointer, states[i]);
                 }
             }

             break;

         case 2 :
             menues.SetFieldState(button.nativePointer, (can_be_selected)
                     ? 1
                     : 0);
        }

        endsession();

        if ((!(can_be_selected)) || (this.silent_selecting)) {
            return;
        }

        Iterator<IRadioChangeListener> iter1 = this.listeners.iterator();

        while (iter1.hasNext()) {
            iter1.next().controlSelected(button, 1);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onDeselectSelect(long _menu, MENUbutton_field button) {
        if ((this.undersession) || (this.f_deselecting)) {
            return;
        }

        boolean can_be_deselected = true;
        Iterator<IRadioAccess> iter = this.access_listeners.iterator();

        do {
            if (!(iter.hasNext())) {
                break;
            }
        } while (iter.next().controlAccessed(button, 0));

        can_be_deselected = false;
        beginsession();

        switch (this.mode) {
         case 1 :
             if (!(can_be_deselected)) {
                 for (int i = 0; i < this.buttons.length; ++i) {
                     menues.SetFieldState(this.buttons[i].nativePointer,
                                          (this.buttons[i].nativePointer == button.nativePointer)
                                          ? 1
                                          : 0);
                 }
             }

             break;

         case 2 :
             if (!(can_be_deselected)) {
                 menues.SetFieldState(button.nativePointer, 1);
             }
        }

        endsession();

        switch (this.mode) {
         case 1 :
             if (this.silent_selecting) {
                 return;
             }

             Iterator<IRadioChangeListener> iter1 = this.listeners.iterator();

             while (iter1.hasNext()) {
                 iter1.next().controlSelected(button, 1);
             }

             break;

         case 2 :
             if ((!(can_be_deselected)) || (this.silent_selecting)) {
                 return;
             }

             Iterator<IRadioChangeListener> iter11 = this.listeners.iterator();

             while (iter11.hasNext()) {
                 iter11.next().controlSelected(button, 0);
             }
        }
    }

    /**
     * Method description
     *
     */
    public void setSingleSelectionMode() {
        this.mode = 1;
    }

    /**
     * Method description
     *
     */
    public void setMultySelectionMode() {
        this.mode = 2;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isSingleSelectionMode() {
        return (this.mode == 1);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onPressEvent(long _menu, MENUbutton_field button) {
        for (IActivePressedTracker lst : this.activepressed_listeners) {
            lst.onPress(button.userid);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onReleaseEvent(long _menu, MENUbutton_field button) {
        for (IActivePressedTracker lst : this.activepressed_listeners) {
            lst.onRelease(button.userid);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onActive(long _menu, MENUbutton_field button) {
        for (IActivePressedTracker lst : this.activepressed_listeners) {
            lst.onActive(button.userid);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onPassive(long _menu, MENUbutton_field button) {
        for (IActivePressedTracker lst : this.activepressed_listeners) {
            lst.onPassive(button.userid);
        }
    }

    private int getButtonNom(MENUbutton_field button) {
        for (int i = 0; i < this.buttons.length; ++i) {
            if (this.buttons[i].nativePointer == button.nativePointer) {
                return i;
            }
        }

        return -1;
    }

    private int[] getSelectedLines() {
        int[] res = new int[this.buttons.length];

        for (int i = 0; i < res.length; ++i) {
            res[i] = menues.GetFieldState(this.buttons[i].nativePointer);
        }

        return res;
    }

    /**
     * @return the multySelection
     */
    public static int getMultySelection() {
        return MULTY_SELECTION;
    }

    /**
     * @return the singleSelection
     */
    public static int getSingleSelection() {
        return SINGLE_SELECTION;
    }
}


//~ Formatted in DD Std on 13/08/25
