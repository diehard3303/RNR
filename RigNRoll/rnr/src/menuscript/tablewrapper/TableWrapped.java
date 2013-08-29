/*
 * @(#)TableWrapped.java   13/08/26
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


package rnr.src.menuscript.tablewrapper;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.Cmenu_TTI;
import rnr.src.menu.FocusManager;
import rnr.src.menu.MENUEditBox;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUTruckview;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.menues;
import rnr.src.menuscript.table.IFocusListener;
import rnr.src.menuscript.table.ISelectLineListener;
import rnr.src.menuscript.table.ISetupLine;
import rnr.src.menuscript.table.Table;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public abstract class TableWrapped implements ISetupLine, ISelectLineListener, IFocusListener {
    private final String SORT_METHOD = "onSort";
    protected TableLine selected = null;
    protected TableData TABLE_DATA = new TableData();
    protected Table table;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param selectionType
     * @param track_activity
     * @param XML
     * @param TABLE
     * @param TABLE_RANGER
     * @param LINE
     * @param LINE_ELEMENTS
     * @param isRadioButton
     * @param SORT
     */
    public TableWrapped(long _menu, int selectionType, boolean track_activity, String XML, String TABLE,
                        String TABLE_RANGER, String LINE, String[] LINE_ELEMENTS, boolean[] isRadioButton,
                        String[] SORT) {
        this.table = new Table(_menu, TABLE, TABLE_RANGER);
        this.table.setSelectionMode(selectionType);
        this.table.setShiftMode(1);
        this.table.fillWithLines(XML, LINE, LINE_ELEMENTS);
        this.table.takeSetuperForAllLines(this);
        this.table.addListener(this);
        this.table.addFocusListener(this);
        reciveTableDataWrapped();
        build_tree_data();

        if (track_activity) {
            this.table.makeTrackActivity();
        }

        if (selectionType != 0) {
            if (isRadioButton != null) {
                for (int i = 0; i < Math.min(isRadioButton.length, LINE_ELEMENTS.length); ++i) {
                    if (isRadioButton[i] != false) {
                        this.table.initLinesSelection(LINE_ELEMENTS[i]);
                    }
                }
            } else {
                for (int i = 0; i < LINE_ELEMENTS.length; ++i) {
                    this.table.initLinesSelection(LINE_ELEMENTS[i]);
                }
            }
        }

        if (null != SORT) {
            for (int i = 0; i < SORT.length; ++i) {
                long field = menues.FindFieldInMenu(_menu, SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

                buts.userid = i;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }
        }
    }

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param selectionType
     * @param shiftType
     * @param track_activity
     * @param XML
     * @param TABLE
     * @param TABLE_RANGER
     * @param LINE
     * @param LINE_ELEMENTS
     * @param SORT
     */
    public TableWrapped(long _menu, int selectionType, int shiftType, boolean track_activity, String XML, String TABLE,
                        String TABLE_RANGER, String LINE, String[] LINE_ELEMENTS, String[] SORT) {
        this.table = new Table(_menu, TABLE, TABLE_RANGER);
        this.table.setSelectionMode(selectionType);
        this.table.setShiftMode(shiftType);
        this.table.fillWithLines(XML, LINE, LINE_ELEMENTS);
        this.table.takeSetuperForAllLines(this);
        this.table.addListener(this);
        this.table.addFocusListener(this);
        reciveTableDataWrapped();
        build_tree_data();

        if (track_activity) {
            this.table.makeTrackActivity();
        }

        if (selectionType != 0) {
            for (String name : LINE_ELEMENTS) {
                this.table.initLinesSelection(name);
            }
        }

        if (null != SORT) {
            for (int i = 0; i < SORT.length; ++i) {
                long field = menues.FindFieldInMenu(_menu, SORT[i]);
                MENUsimplebutton_field buts = menues.ConvertSimpleButton(field);

                buts.userid = i;
                menues.SetScriptOnControl(_menu, buts, this, "onSort", 4L);
                menues.UpdateField(buts);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramMENUsimplebutton_field
     */
    public abstract void onSort(long paramLong, MENUsimplebutton_field paramMENUsimplebutton_field);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt
     * @param paramTableLine
     */
    public abstract void SetupLineInTable(long paramLong, int paramInt, TableLine paramTableLine);

    /**
     * Method description
     *
     *
     * @param paramTableLine
     */
    public abstract void updateSelectedInfo(TableLine paramTableLine);

    protected abstract void reciveTableData();

    /**
     * Method description
     *
     */
    public void EnterFocus() {
        FocusManager.enterFocus(this.table);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isInFocus() {
        return FocusManager.isFocused(this.table);
    }

    /**
     * Method description
     *
     */
    public void afterInit() {
        this.table.afterInit();
    }

    private void reciveTableDataWrapped() {
        this.TABLE_DATA.all_lines.clear();
        reciveTableData();
        buildvoidcells();
    }

    private Cmenu_TTI convertTableData() {
        Cmenu_TTI root = new Cmenu_TTI();

        for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
            Cmenu_TTI ch = new Cmenu_TTI();

            ch.toshow = true;
            ch.ontop = (i == 0);
            ch.item = this.TABLE_DATA.all_lines.get(i);
            root.children.add(ch);
        }

        return root;
    }

    protected void buildvoidcells() {
        if (this.TABLE_DATA.all_lines.size() < this.table.getNumRows()) {
            int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();

            for (int i = 0; i < dif; ++i) {
                TableLine data = new TableLine();

                data.wheather_show = false;
                this.TABLE_DATA.all_lines.add(data);
            }
        } else {
            int count_good_data = 0;
            Iterator iter = this.TABLE_DATA.all_lines.iterator();

            while ((iter.hasNext()) && (((TableLine) iter.next()).wheather_show)) {
                ++count_good_data;
            }

            if ((count_good_data >= this.table.getNumRows()) && (count_good_data < this.TABLE_DATA.all_lines.size())) {
                for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                    this.TABLE_DATA.all_lines.remove(i);
                }
            }
        }
    }

    private void build_tree_data() {
        this.table.reciveTreeData(convertTableData());
    }

    /**
     * Method description
     *
     */
    public void updateTable() {
        this.selected = null;
        reciveTableDataWrapped();
        build_tree_data();
        this.table.select_line(0);
        this.table.refresh();
    }

    /**
     * Method description
     *
     */
    public void redrawTable() {
        this.table.redrawTable();
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    @Override
    public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine) table_node.item;

        if (!(line.wheather_show)) {
            menues.SetShowField(obj.nativePointer, false);

            return;
        }

        int position = this.table.getMarkedPosition(obj.nativePointer);

        SetupLineInTable(obj.nativePointer, position, line);
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    @Override
    public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine) table_node.item;

        if (!(line.wheather_show)) {
            menues.SetShowField(obj.nativePointer, false);

            return;
        }

        int position = this.table.getMarkedPosition(obj.nativePointer);

        SetupLineInTable(obj.nativePointer, position, line);
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    @Override
    public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine) table_node.item;

        if (!(line.wheather_show)) {
            menues.SetShowField(obj.nativePointer, false);

            return;
        }

        int position = this.table.getMarkedPosition(obj.nativePointer);

        SetupLineInTable(obj.nativePointer, position, line);
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    @Override
    public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine) table_node.item;

        if (!(line.wheather_show)) {
            menues.SetShowField(obj.nativePointer, false);

            return;
        }

        int position = this.table.getMarkedPosition(obj.nativePointer);

        SetupLineInTable(obj.nativePointer, position, line);
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    @Override
    public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine) table_node.item;

        if (!(line.wheather_show)) {
            menues.SetShowField(obj.nativePointer, false);

            return;
        }

        int position = this.table.getMarkedPosition(obj.nativePointer);

        SetupLineInTable(obj.nativePointer, position, line);
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    @Override
    public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
        TableLine line = (TableLine) table_node.item;

        if (!(line.wheather_show)) {
            menues.SetShowField(obj.nativePointer, false);

            return;
        }

        int position = this.table.getMarkedPosition(obj.nativePointer);

        SetupLineInTable(obj.nativePointer, position, line);
    }

    /**
     * Method description
     *
     *
     * @param table
     * @param line
     */
    @Override
    public void selectLineEvent(Table table, int line) {
        for (TableLine item : this.TABLE_DATA.all_lines) {
            item.selected = false;
        }

        TableLine data = (TableLine) table.getItemOnLine(line).item;

        data.selected = true;
        this.selected = data;
        updateSelectedInfo(this.selected);
    }

    /**
     * Method description
     *
     *
     * @param table
     * @param lines
     */
    @Override
    public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
        for (TableLine item : this.TABLE_DATA.all_lines) {
            item.selected = false;
        }

        for (Cmenu_TTI item : lines) {
            if (item.item != null) {
                TableLine data = (TableLine) item.item;

                data.selected = true;
            }
        }

        this.selected = ((TableLine) table.getSelectedData().item);
        updateSelectedInfo(this.selected);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public TableLine getSelected() {
        return this.selected;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public TableLine getTop() {
        return ((TableLine) this.table.getItemOnLine(0).item);
    }

    /**
     * Method description
     *
     *
     * @param line
     *
     * @return
     */
    public TableLine getLineItem(int line) {
        return ((TableLine) this.table.getItemOnLine(line).item);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<TableLine> getSelectedMultiple() {
        ArrayList res = new ArrayList();

        for (TableLine line : this.TABLE_DATA.all_lines) {
            if (line.selected) {
                res.add(line);
            }
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param data
     */
    public void selectLineByData(TableLine data) {
        this.table.select_line_by_data(data);
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void deselectOnLooseFocus(boolean value) {
        this.table.setLooseSelectionLooseFocus(value);
    }

    /**
     * Method description
     *
     *
     * @param data
     * @param value
     */
    public void activeLineByData(TableLine data, boolean value) {
        this.table.active_line_by_data(data, value);
    }

    /**
     * Method description
     *
     *
     * @param data
     * @param value
     */
    public void pressedLineByData(TableLine data, boolean value) {
        this.table.pressed_line_by_data(data, value);
    }
}


//~ Formatted in DD Std on 13/08/26
