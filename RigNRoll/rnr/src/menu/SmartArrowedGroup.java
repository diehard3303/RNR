/*
 * @(#)SmartArrowedGroup.java   13/08/25
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

import rnr.src.rnrcore.Log;

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
public class SmartArrowedGroup implements IRadioChangeListener, ITableInsertable {
    protected static int NUMELEM = 0;
    protected static final String NAMEUNIQSUFF = "SmartGroupTop";
    private static final String HEAD = "TITLE";
    private static final String[] ARROWS = { "LEFT", "RIGHT" };
    private static final String[] ARROWSCB = { "OnLeft", "OnRight" };
    protected long[] controls = null;
    private final long[] arrows = new long[2];
    protected long head = 0L;
    private int toppic_x = 0;
    private int toppic_y = 0;
    private boolean bBlind = false;
    private final ArrayList<IValueChanged> cbs = new ArrayList<IValueChanged>();
    protected String xml_file;
    protected String xml_node_group;
    protected String title;

    /**
     * Constructs ...
     *
     *
     * @param xml_file
     * @param xml_node_group
     * @param title
     * @param bBlind
     */
    public SmartArrowedGroup(String xml_file, String xml_node_group, String title, boolean bBlind) {
        this.title = title;
        this.xml_file = xml_file;
        this.xml_node_group = xml_node_group;
        this.bBlind = bBlind;
    }

    /**
     * Method description
     *
     *
     * @param cb
     */
    public void addListener(IValueChanged cb) {
        this.cbs.add(cb);
    }

    protected void callValueChanged() {
        Iterator<IValueChanged> iter = this.cbs.iterator();

        while (iter.hasNext()) {
            iter.next().valueChanged();
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    public void load(long _menu) {
        this.controls = menues.InitXml(_menu, this.xml_file, this.xml_node_group);
        initTopPic(_menu);

        for (int i = 0; i < this.controls.length; ++i) {
            if ((readArrows(_menu, this.controls[i])) || (readHead(this.controls[i]))) {
                continue;
            }
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void init() {
        menues.SetFieldText(this.head, this.title);
        menues.UpdateMenuField(menues.ConvertMenuFields(this.head));
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
            Log.menu("Slider Group. Top contrtol is not a picture. Xml file " + this.xml_file + ". Control group "
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

    private boolean readArrows(long _menu, long field) {
        String name = menues.GetFieldName(field);

        for (int i = 0; i < ARROWS.length; ++i) {
            if (name.compareTo(ARROWS[i]) == 0) {
                this.arrows[i] = field;
                menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.arrows[i]), this, ARROWSCB[i], 4L);

                return true;
            }
        }

        return false;
    }

    private boolean readHead(long field) {
        String name = menues.GetFieldName(field);

        if (name.compareTo("TITLE") == 0) {
            this.head = field;

            return true;
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnLeft(long _menu, MENUsimplebutton_field button) {}

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnRight(long _menu, MENUsimplebutton_field button) {}

    /**
     * Method description
     *
     *
     * @param button
     * @param cs
     */
    @Override
    public void controlSelected(MENUbutton_field button, int cs) {}

    /**
     * Method description
     *
     */
    @Override
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
    @Override
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
    @Override
    public void show() {
        for (int i = 0; i < this.controls.length; ++i) {
            menues.SetBlindess(this.controls[i], this.bBlind);
            menues.SetIgnoreEvents(this.controls[i], this.bBlind);
            menues.SetShowField(this.controls[i], true);
        }
    }

    /**
     * Method description
     *
     *
     * @param shiftx
     * @param shifty
     */
    @Override
    public void updatePositon(int shiftx, int shifty) {
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

    /**
     * @return the head
     */
    public static String getHead() {
        return HEAD;
    }
}


//~ Formatted in DD Std on 13/08/25
