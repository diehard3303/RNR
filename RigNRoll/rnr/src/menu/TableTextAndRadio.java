/*
 * @(#)TableTextAndRadio.java   13/08/25
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
public class TableTextAndRadio implements TableCallbacks {
    private static final String TITLE = "TEXT - TITLE";
    private static final String VALUE = "RADIO - VALUE";
    private static final String TABLENAME = "TableTextAndRadio";
    private static int num_table = 0;
    private Table table = null;
    private int num_rows = 0;
    private int rows_shift = 0;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param FILENAME
     * @param CONTROLGROUP
     * @param PARENT
     */
    public TableTextAndRadio(long _menu, String FILENAME, String CONTROLGROUP, String PARENT) {
        readName(PARENT);
        this.table = new Table(_menu, "TableTextAndRadio" + (num_table++));
        this.table.AddEvent(2);
        this.table.AddTextField("TEXT - TITLE", 1);
        this.table.AddRadioButton("RADIO - VALUE", 2);
        this.table.Setup(this.rows_shift, this.num_rows, FILENAME, CONTROLGROUP, PARENT, this, 0);
    }

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param FILENAME
     * @param CONTROLGROUP
     * @param PARENT
     * @param RANGER
     */
    public TableTextAndRadio(long _menu, String FILENAME, String CONTROLGROUP, String PARENT, String RANGER) {
        readName(PARENT);
        this.table = new Table(_menu, "TableTextAndRadio" + (num_table++));
        this.table.AddEvent(2);
        this.table.AddTextField("TEXT - TITLE", 1);
        this.table.AddRadioButton("RADIO - VALUE", 2);
        this.table.Setup(this.rows_shift, this.num_rows, FILENAME, CONTROLGROUP, PARENT, this, 0);

        long field_ranger = menues.FindFieldInMenu(_menu, RANGER);

        if (field_ranger != 0L) {
            this.table.AttachRanger(menues.ConvertRanger(field_ranger));
        } else {
            Log.menu("TableTextAndRadio. Cannot find ranger named " + RANGER);
        }
    }

    /**
     * Method description
     *
     */
    public void DeInit() {
        if (this.table != null) {
            this.table.DeInit();
        }
    }

    /**
     * Method description
     *
     */
    public void afterInit() {}

    /**
     * Method description
     *
     *
     * @return
     */
    public int sliderPosition() {
        return this.table.sliderPosition();
    }

    private void readName(String table_name) {
        String[] astr = table_name.split(" ");

        if (astr.length < 2) {
            Log.menu("TableOfElements const. Bad name for root element - does not include table sizes. Name:\t"
                     + table_name);

            return;
        }

        this.num_rows = Integer.decode(astr[(astr.length - 2)]).intValue();
        this.rows_shift = Integer.decode(astr[(astr.length - 1)]).intValue();
    }

    /**
     * Method description
     *
     *
     * @param data
     */
    public void fillTable(ArrayList<ContainerTextTitleTextValue> data) {
        this.table.SetupRootNode();
        readData(data);
        this.table.RefillTree();
        this.table.RedrawTable();
    }

    /**
     * Method description
     *
     */
    public void redraw() {
        this.table.RedrawTable();
    }

    private void readData(ArrayList<ContainerTextTitleTextValue> data) {
        Iterator<ContainerTextTitleTextValue> iter = data.iterator();

        while (iter.hasNext()) {
            this.table.AddItem(null, iter.next(), true);
        }
    }

    /**
     * Method description
     *
     *
     * @param event
     * @param node
     * @param group
     * @param _menu
     */
    @Override
    public void OnEvent(long event, TableNode node, long group, long _menu) {}

    /**
     * Method description
     *
     *
     * @param type
     * @param node
     * @param control
     */
    @Override
    public void SetupLineInTable(int type, TableNode node, Object control) {}

    /**
     * Method description
     *
     *
     * @param node
     * @param button
     */
    @Override
    public void SetupLineInTable(TableNode node, MENUbutton_field button) {
        if (null == node.item) {
            button.text = "";
            menues.SetShowField(button.nativePointer, false);
            menues.UpdateField(button);

            return;
        }

        button.text = ((ContainerTextTitleTextValue) node.item).loc_value;
        menues.SetShowField(button.nativePointer, true);
        menues.UpdateField(button);
        menues.SetFieldState(button.nativePointer, 0);
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param button
     */
    @Override
    public void SetupLineInTable(TableNode node, MENUsimplebutton_field button) {}

    /**
     * Method description
     *
     *
     * @param node
     * @param text
     */
    @Override
    public void SetupLineInTable(TableNode node, MENUText_field text) {
        if (null == node.item) {
            text.text = "";
            menues.SetShowField(text.nativePointer, false);
            menues.UpdateField(text);

            return;
        }

        text.text = ((ContainerTextTitleTextValue) node.item).loc_title;
        menues.SetShowField(text.nativePointer, true);
        menues.UpdateField(text);
    }

    /**
     * @return the title
     */
    public static String getTitle() {
        return TITLE;
    }

    /**
     * @return the value
     */
    public static String getValue() {
        return VALUE;
    }

    /**
     * @return the tablename
     */
    public static String getTablename() {
        return TABLENAME;
    }
}


//~ Formatted in DD Std on 13/08/25
