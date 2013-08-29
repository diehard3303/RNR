/*
 * @(#)Line.java   13/08/26
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


package rnr.src.menuscript.table;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.MENUText_field;
import rnr.src.menu.menues;
import rnr.src.rnrcore.Log;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Line {
    protected static int NUMELEM = 0;
    protected static final String NAMEUNIQSUFF = "SmartGroupTop";
    private long[] controls = null;
    private int toppic_x = 0;
    private int toppic_y = 0;
    private boolean f_initialized = false;
    private long group = 0L;
    private long[] marked_controls = null;
    private String[] marked_names = null;
    protected String xml_file;
    protected String xml_node_group;

    Line(String xmlfilename, String xmlcontrolgroup, String[] marked_names) {
        this.xml_file = xmlfilename;
        this.xml_node_group = xmlcontrolgroup;
        this.marked_names = marked_names;

        if (null != marked_names) {
            this.marked_controls = new long[marked_names.length];
        }
    }

    void load(long _menu) {
        if (this.f_initialized) {
            Log.menu("Line initialized not ones. xml file " + this.xml_file + " controlgroup " + "xml_node_group");

            return;
        }

        this.f_initialized = true;
        this.controls = menues.InitXml(_menu, this.xml_file, this.xml_node_group);
        initTopPic(_menu);
        initGroup(_menu);
    }

    long getLine() {
        if (!(this.f_initialized)) {
            Log.menu("getLine. Line not initialized. xml file " + this.xml_file + " controlgroup " + "xml_node_group");

            return -1L;
        }

        return this.group;
    }

    long getNamedControl(String name) {
        for (int i = 0; i < this.controls.length; ++i) {
            if (menues.GetFieldName(this.controls[i]).compareTo(name) == 0) {
                return this.controls[i];
            }
        }

        return 0L;
    }

    private boolean mark_field(long field) {
        if (null == this.marked_names) {
            Log.menu("ERRORR. mark_field - wrong behaivoir.");

            return false;
        }

        for (int i = 0; i < this.marked_names.length; ++i) {
            if (menues.GetFieldName(field).compareTo(this.marked_names[i]) == 0) {
                this.marked_controls[i] = field;

                return true;
            }
        }

        return false;
    }

    private void initGroup(long _menu) {
        this.group = menues.CreateGroup(_menu);

        if (this.marked_names != null) {
            for (int i = 0; i < this.controls.length; ++i) {
                if (mark_field(this.controls[i])) {
                    Object obj = menues.ConvertMenuFields(this.controls[i]);

                    menues.AddControlInGroup(_menu, this.group, obj);
                    menues.LinkGroupAndControl(_menu, obj);
                    menues.ChangableFieldOnGroup(_menu, obj);
                }
            }
        } else {
            this.marked_controls = new long[this.controls.length];

            for (int i = 0; i < this.controls.length; ++i) {
                this.marked_controls[i] = this.controls[i];

                Object obj = menues.ConvertMenuFields(this.controls[i]);

                menues.AddControlInGroup(_menu, this.group, obj);
                menues.LinkGroupAndControl(_menu, obj);
                menues.ChangableFieldOnGroup(_menu, obj);
            }
        }
    }

    private void initTopPic(long _menu) {
        if (null == this.controls) {
            Log.menu("Slider Group. Trying to access not created controls field. bindToParent");

            return;
        }

        if (0 == this.controls.length) {
            Log.menu("Slider Group. Trying to bindToParent broken group");
        }

        MENUText_field toppic = menues.ConvertTextFields(this.controls[0]);

        if (null == toppic) {
            Log.menu("ControlGroup. Top contrtol is not a picture. Xml file " + this.xml_file + ". Control group "
                     + this.xml_node_group + ".");

            return;
        }

        this.toppic_x = toppic.pox;
        this.toppic_y = toppic.poy;

        String parentName = toppic.nameID + "SmartGroupTop" + (NUMELEM++);

        menues.SetFieldName(_menu, this.controls[0], parentName);

        for (int i = 1; i < this.controls.length; ++i) {
            menues.SetFieldParentName(this.controls[i], parentName);
        }
    }

    void shiftLine(int shiftx, int shifty) {
        if (!(this.f_initialized)) {
            Log.menu("shiftLine. Line not initialized. xml file " + this.xml_file + " controlgroup "
                     + "xml_node_group");

            return;
        }

        MENUText_field top = menues.ConvertTextFields(this.controls[0]);

        if (null == top) {
            Log.menu("Slider Group. Top contrtol is not a picture. Xml file " + this.xml_file + ". Control group "
                     + this.xml_node_group + ".");

            return;
        }

        top.pox = (this.toppic_x + shiftx);
        top.poy = (this.toppic_y + shifty);
        menues.UpdateField(top);
    }

    int getMarkedPosition(long control) {
        if ((null == this.marked_controls) || (0L == control)) {
            return -1;
        }

        for (int i = 0; i < this.marked_controls.length; ++i) {
            if (control == this.marked_controls[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Method description
     *
     */
    public void hide() {
        for (int i = 0; i < this.controls.length; ++i) {
            menues.SetBlindess(this.controls[i], true);
            menues.SetIgnoreEvents(this.controls[i], true);
            menues.SetShowField(this.controls[i], false);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param parent
     */
    public void insertInTable(long _menu, long parent) {
        String parentName = menues.GetFieldName(parent);
        MENUText_field top = menues.ConvertTextFields(this.controls[0]);

        top.parentName = parentName;
        menues.UpdateField(top);
    }

    /**
     * Method description
     *
     */
    public void show() {
        for (int i = 0; i < this.controls.length; ++i) {
            menues.SetBlindess(this.controls[i], false);
            menues.SetIgnoreEvents(this.controls[i], false);
            menues.SetShowField(this.controls[i], true);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
