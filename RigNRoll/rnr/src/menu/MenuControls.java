/*
 * @(#)MenuControls.java   13/08/28
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


package rnr.src.menu;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.Log;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class MenuControls {
    protected static int NUMELEM = 0;
    protected static final String NAMEUNIQSUFF = "MenuControls";
    private long[] controls;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param xmlname
     * @param group
     */
    public MenuControls(long _menu, String xmlname, String group) {
        this.controls = menues.InitXml(_menu, xmlname, group);

        if (this.controls == null) {
            Log.menu("ERRORR. MenuControls. Reading " + xmlname + " controlgroup " + group);
        }
    }

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param xmlname
     * @param group
     * @param make_unique
     */
    public MenuControls(long _menu, String xmlname, String group, boolean make_unique) {
        this.controls = menues.InitXml(_menu, xmlname, group);

        if (this.controls == null) {
            Log.menu("ERRORR. MenuControls. Reading " + xmlname + " controlgroup " + group);
        }

        if (!(make_unique)) {
            return;
        }

        if (this.controls.length == 0) {
            Log.menu("ERRORR. MenuControls. make_unique group has no controls. Reading " + xmlname + " controlgroup "
                     + group);
        }

        String parentName = menues.GetFieldName(this.controls[0]) + "MenuControls" + (NUMELEM++);

        menues.SetFieldName(_menu, this.controls[0], parentName);

        for (int i = 1; i < this.controls.length; ++i) {
            menues.SetFieldParentName(this.controls[i], parentName);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public long getTopControl() {
        if (this.controls == null) {
            Log.menu("ERRORR. MenuControls. getTopControl Bad Controls... controls==null");

            return 0L;
        }

        if (this.controls.length == 0) {
            Log.menu("ERRORR. MenuControls. getTopControl Bad Controls... controls.length==0");

            return 0L;
        }

        return this.controls[0];
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public long findControl(String name) {
        if (this.controls == null) {
            return 0L;
        }

        for (long crtl : this.controls) {
            if (menues.GetFieldName(crtl).compareTo(name) == 0) {
                return crtl;
            }
        }

        return 0L;
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public long[] findControls(String name) {
        if (this.controls == null) {
            return new long[0];
        }

        ArrayList res = new ArrayList();

        for (long crtl : this.controls) {
            if (menues.GetFieldName(crtl).compareTo(name) == 0) {
                res.add(crtl);
            }
        }

        long[] arr = new long[res.size()];
        int iter = 0;

        for (long ctrl : res) {
            arr[(iter++)] = ctrl;
        }

        return arr;
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public long[] findControls_Contains(String name) {
        if (this.controls == null) {
            return new long[0];
        }

        ArrayList res = new ArrayList();

        for (long crtl : this.controls) {
            if (menues.GetFieldName(crtl).matches("*" + name + "*")) {
                res.add(crtl);
            }
        }

        long[] arr = new long[res.size()];
        int iter = 0;

        for (long ctrl : res) {
            arr[(iter++)] = ctrl;
        }

        return arr;
    }

    /**
     * Method description
     *
     */
    public void show() {
        menues.SetShowField(this.controls[0], true);
    }

    /**
     * Method description
     *
     */
    public void hide() {
        menues.SetShowField(this.controls[0], false);
    }

    /**
     * Method description
     *
     */
    public void setNoFocusOnControls() {
        for (long item : this.controls) {
            menues.setFocusOnControl(item, false);
            menues.SetIgnoreEvents(item, true);
            menues.SetBlindess(item, true);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
