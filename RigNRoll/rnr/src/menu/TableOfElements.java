/*
 * @(#)TableOfElements.java   13/08/25
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
public class TableOfElements extends IWheelEnabled {
    private long _menu = 0L;
    private long table_root = 0L;
    private long table_ranger = 0L;
    private int num_rows = 0;
    private int rows_shift = 0;
    private final ArrayList<ITableInsertable> elements = new ArrayList<ITableInsertable>();
    private int currentOnTop = 0;
    private boolean _is_hidden = false;
    private boolean f_tableUpdated = false;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param table_name
     */
    public TableOfElements(long _menu, String table_name) {
        this._menu = _menu;
        this.table_root = menues.FindFieldInMenu(_menu, table_name);

        String[] astr = table_name.split(" ");

        if (astr.length < 2) {
            Log.menu("TableOfElements const. Bad name for root element - does not include table sizes. Name:\t"
                     + table_name);

            return;
        }

        this.num_rows = Integer.decode(astr[(astr.length - 2)]).intValue();
        this.rows_shift = Integer.decode(astr[(astr.length - 1)]).intValue();
        wheelInit(_menu, table_name);
    }

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param table_name
     * @param rangerName
     */
    public TableOfElements(long _menu, String table_name, String rangerName) {
        this._menu = _menu;
        this.table_root = menues.FindFieldInMenu(_menu, table_name);

        if (rangerName != null) {
            initRanger(rangerName);
        }

        String[] astr = table_name.split(" ");

        if (astr.length < 2) {
            Log.menu("TableOfElements const. Bad name for root element - does not include table sizes. Name:\t"
                     + table_name);

            return;
        }

        this.num_rows = Integer.decode(astr[(astr.length - 2)]).intValue();
        this.rows_shift = Integer.decode(astr[(astr.length - 1)]).intValue();
        wheelInit(_menu, table_name);
    }

    private void initRanger(String rangerName) {
        this.table_ranger = menues.FindFieldInMenu(this._menu, rangerName);

        if (0L == this.table_ranger) {
            Log.menu("TableOfElements const. initRanger - no sych ranger. Name:\t" + rangerName);

            return;
        }

        MENU_ranger rang = menues.ConvertRanger(this.table_ranger);

        rang.min_value = 0;
        rang.max_value = 0;
        menues.SetScriptOnControl(this._menu, rang, this, "OnRangerScroll", 1L);
        menues.UpdateField(rang);
    }

    void OnRangerScroll(long _menu, MENU_ranger scroller) {
        if (!(this.f_tableUpdated)) {
            return;
        }

        this.currentOnTop = scroller.current_value;
        updateTable();
    }

    /**
     * Method description
     *
     */
    @Override
    public void cbEnterFocus() {}

    /**
     * Method description
     *
     */
    @Override
    public void cbLeaveFocus() {}

    /**
     * Method description
     *
     */
    @Override
    public void ControlsCtrlAPressed() {}

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void ControlsMouseWheel(int value) {
        MENU_ranger scroller = menues.ConvertRanger(this.table_ranger);

        if (scroller == null) {
            return;
        }

        scroller.current_value -= value;

        if (scroller.current_value > scroller.max_value) {
            scroller.current_value = scroller.max_value;
        }

        if (scroller.current_value < scroller.min_value) {
            scroller.current_value = scroller.min_value;
        }

        menues.UpdateField(scroller);
        updateTable();
    }

    /**
     * Method description
     *
     *
     * @param item
     */
    public void insert(ITableInsertable item) {
        item.insertInTable(this._menu, this.table_root);
        this.elements.add(item);

        if (0L != this.table_ranger) {
            MENU_ranger rang = menues.ConvertRanger(this.table_ranger);

            rang.max_value = (this.elements.size() - this.num_rows);

            if (rang.max_value < rang.min_value) {
                rang.max_value = rang.min_value;
            }

            menues.UpdateField(rang);
        }
    }

    /**
     * Method description
     *
     */
    public void hideTable() {
        this._is_hidden = true;

        Iterator<ITableInsertable> iter = this.elements.iterator();

        while (iter.hasNext()) {
            ITableInsertable elem = iter.next();

            elem.hide();
        }
    }

    /**
     * Method description
     *
     */
    public void showTable() {
        this._is_hidden = false;
        updateTable();
    }

    /**
     * Method description
     *
     */
    public void initTable() {
        Iterator<ITableInsertable> iter = this.elements.iterator();

        while (iter.hasNext()) {
            ITableInsertable elem = iter.next();

            elem.init();
        }

        updateTable();
    }

    private void updateTable() {
        if (this._is_hidden) {
            return;
        }

        this.f_tableUpdated = true;

        Iterator<ITableInsertable> iter = this.elements.iterator();
        int num = 0;

        while (iter.hasNext()) {
            ITableInsertable elem = iter.next();
            int line = num - this.currentOnTop;

            if ((num < this.currentOnTop) || (line >= this.num_rows)) {
                ++num;
                elem.hide();
            }

            elem.show();
            elem.updatePositon(0, this.rows_shift * line);
            ++num;
        }
    }

    /**
     * Method description
     *
     */
    public void DeInit() {
        wheelDeinit();
    }
}


//~ Formatted in DD Std on 13/08/25
