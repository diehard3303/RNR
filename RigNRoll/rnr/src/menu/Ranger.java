/*
 * @(#)Ranger.java   13/08/25
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

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class Ranger {
    private static final String METH = "onChange";
    private boolean seilent_set_value = false;
    private final ArrayList<IRangerChanged> listeners = new ArrayList<IRangerChanged>();
    private final long ranger;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param _ranger
     * @param min
     * @param max
     * @param curent
     */
    public Ranger(long _menu, long _ranger, int min, int max, int curent) {
        this.ranger = _ranger;
        init(_menu, min, max, curent);
    }

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param name
     * @param min
     * @param max
     * @param curent
     */
    public Ranger(long _menu, String name, int min, int max, int curent) {
        this.ranger = menues.FindFieldInMenu(_menu, name);
        init(_menu, min, max, curent);
    }

    /**
     * Method description
     *
     *
     * @param lst
     */
    public void addListener(IRangerChanged lst) {
        this.listeners.add(lst);
    }

    /**
     * Method description
     *
     *
     * @param lst
     */
    public void removeListener(IRangerChanged lst) {
        this.listeners.remove(lst);
    }

    private void init(long _menu, int min, int max, int curent) {
        MENU_ranger rng = menues.ConvertRanger(this.ranger);

        rng.max_value = min;
        rng.max_value = max;
        rng.current_value = curent;
        menues.UpdateField(rng);
        menues.SetScriptOnControl(_menu, rng, this, "onChange", 1L);
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setState(int value) {
        menues.SetFieldState(this.ranger, value);
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setValue(int value) {
        MENU_ranger rng = menues.ConvertRanger(this.ranger);

        rng.current_value = value;
        menues.UpdateField(rng);
        menues.ConvertRanger(this.ranger);
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param seilent
     */
    public void setValue(int value, boolean seilent) {
        boolean old = this.seilent_set_value;

        this.seilent_set_value = seilent;
        setValue(value);
        this.seilent_set_value = old;
    }

    /**
     * Method description
     *
     *
     * @param min
     * @param max
     * @param value
     * @param page
     * @param seilent
     */
    public void setValue(int min, int max, int value, int page, boolean seilent) {
        boolean old = this.seilent_set_value;

        this.seilent_set_value = seilent;

        MENU_ranger rng = menues.ConvertRanger(this.ranger);

        rng.min_value = min;
        rng.max_value = max;
        rng.current_value = value;
        rng.page = page;
        menues.UpdateField(rng);
        menues.ConvertRanger(this.ranger);
        this.seilent_set_value = old;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getValue() {
        return menues.GetFieldState(this.ranger);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param rng
     */
    public void onChange(long _menu, MENU_ranger rng) {
        if (this.seilent_set_value) {
            return;
        }

        for (IRangerChanged iter : this.listeners) {
            iter.rangerChanged(rng.current_value);
        }
    }

    /**
     * Method description
     *
     */
    public void show() {
        menues.SetShowField(this.ranger, true);
    }

    /**
     * Method description
     *
     */
    public void hide() {
        menues.SetShowField(this.ranger, false);
    }

    /**
     * @return the meth
     */
    public static String getMeth() {
        return METH;
    }
}


//~ Formatted in DD Std on 13/08/25
