/*
 * @(#)Table.java   13/08/26
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

import rnr.src.menu.Cmenu_TTI;
import rnr.src.menu.Controls;
import rnr.src.menu.Helper;
import rnr.src.menu.IActivePressedTracker;
import rnr.src.menu.IRadioAccess;
import rnr.src.menu.IRadioChangeListener;
import rnr.src.menu.ITableNodeVisitor;
import rnr.src.menu.IWheelEnabled;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.RadioGroup;
import rnr.src.menu.menues;
import rnr.src.rnrcore.Log;
import rnr.src.rnrcore.eng;

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
public class Table extends IWheelEnabled implements IRadioChangeListener, IRadioAccess {
    private static int ACTIVE_SYNC = 0;

    /** Field description */
    public static final int SELECTION_NON = 0;

    /** Field description */
    public static final int SELECTION_SIGLE = 1;

    /** Field description */
    public static final int SELECTION_MULIPE = 2;

    /** Field description */
    public static final int SHIFT_X_POSITIVE = 0;

    /** Field description */
    public static final int SHIFT_Y_POSITIVE = 1;

    /** Field description */
    public static final int SHIFT_X_NEGATIVE = 2;

    /** Field description */
    public static final int SHIFT_Y_NEGATIVE = 3;
    private static final String MAKETREEDATA_METHOD = "reciveTreeData_native";
    private int thisACTIVE_SYNC = 0;
    protected int num_rows = 0;
    private int rows_shift = 0;
    private long table_root = 0L;
    private long table_ranger = 0L;
    @SuppressWarnings("unchecked")
    private final ArrayList<Line> lines = new ArrayList<Line>();
    protected Cmenu_TTI root = null;
    private int nom_line = 0;
    private boolean init_session = true;
    private int mode_selection = 1;
    private int mode_shift = 1;
    private boolean state_raging = false;
    private boolean state_crtlA = false;
    private boolean looseSelectionOnLeaveFocus = false;
    protected ArrayList<RadioGroup> selectedLine = new ArrayList<RadioGroup>();
    protected TrackActivity trackActivity = null;
    protected boolean make_track_activity = false;
    protected ArrayList<ISelectLineListener> select_listeners = new ArrayList<ISelectLineListener>();
    protected ArrayList<IFocusListener> focus_listeners = new ArrayList<IFocusListener>();
    protected ArrayList<IRangerListener> ranger_listeners = new ArrayList<IRangerListener>();
    protected Cmenu_TTI selected_one = null;
    private ArrayList<Cmenu_TTI> selectedLines = new ArrayList<Cmenu_TTI>();
    private long _menu;
    private long table;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param table_name
     * @param rangerName
     */
    public Table(long _menu, String table_name, String rangerName) {
        ACTIVE_SYNC += 100;
        this.thisACTIVE_SYNC = ACTIVE_SYNC;
        this.init_session = true;
        this._menu = _menu;
        this.table = menues.CreateTable(_menu);
        this.table_root = menues.FindFieldInMenu(_menu, table_name);

        if (0L == this.table_root) {
            eng.err("Table Name " + table_name + " not found.");
        }

        if (rangerName != null) {
            initRanger(rangerName);
        }

        String[] astr = table_name.split(" ");

        if (astr.length < 2) {
            Log.menu("Table. Bad name for root element - does not include table sizes. Name:\t" + table_name);

            return;
        }

        wheelInit(_menu, table_name);
        this.num_rows = Integer.decode(astr[(astr.length - 2)]).intValue();
        this.rows_shift = Integer.decode(astr[(astr.length - 1)]).intValue();
    }

    /**
     * Method description
     *
     */
    public void makeTrackActivity() {
        this.make_track_activity = true;
        this.trackActivity = new TrackActivity();
    }

    /**
     * Method description
     *
     *
     * @param line
     *
     * @return
     */
    public boolean isLineActiveNow(int line) {
        if ((!(this.make_track_activity)) || (this.trackActivity == null)) {
            return false;
        }

        return (this.trackActivity.count_active[line] > 0);
    }

    /**
     * Method description
     *
     *
     * @param line
     *
     * @return
     */
    public boolean isLinePressedNow(int line) {
        if ((!(this.make_track_activity)) || (this.trackActivity == null)) {
            return false;
        }

        return (this.trackActivity.count_pressed[line] > 0);
    }

    /**
     * Method description
     *
     *
     * @param mode
     */
    public void setSelectionMode(int mode) {
        this.mode_selection = mode;
    }

    /**
     * Method description
     *
     *
     * @param mode
     */
    public void setShiftMode(int mode) {
        this.mode_shift = mode;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setLooseSelectionLooseFocus(boolean value) {
        this.looseSelectionOnLeaveFocus = value;
    }

    private int get_line_nom() {
        return (this.nom_line++);
    }

    /**
     * Method description
     *
     *
     * @param control
     *
     * @return
     */
    public int getMarkedPosition(long control) {
        Iterator<Line> iter = this.lines.iterator();

        while (iter.hasNext()) {
            int mark = iter.next().getMarkedPosition(control);

            if (mark != -1) {
                return mark;
            }
        }

        return -1;
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

    protected void OnRangerScroll(long _menu, MENU_ranger scroller) {
        if (this.init_session) {
            return;
        }

        this.state_raging = true;
        refresf_table(scroller.current_value);
        renewSelection();

        for (IRangerListener lst : this.ranger_listeners) {
            lst.rangerMoved();
        }

        this.state_raging = false;
    }

    protected void renewSelection() {
        int selected_line_num;

        switch (this.mode_selection) {
         case 1 :
             selected_line_num = Helper.tellLine(this.root, this.selected_one);

             if ((selected_line_num < 0) || (selected_line_num >= this.num_rows)) {
                 for (RadioGroup group : this.selectedLine) {
                     group.deselectall();
                 }

                 return;
             }

             for (RadioGroup group : this.selectedLine) {
                 group.silentSelect(selected_line_num);
             }

             break;

         case 2 :
             for (RadioGroup group : this.selectedLine) {
                 group.deselectall();
             }

             for (Cmenu_TTI item : this.selectedLines) {
                 nom_line = Helper.tellLine(this.root, item);

                 if ((nom_line >= 0) && (nom_line < this.num_rows)) {
                     for (RadioGroup group : this.selectedLine) {
                         group.silentSelect(nom_line);
                     }
                 }
             }
        }

        int nom_line;
    }

    /**
     * Method description
     *
     */
    public void redrawTable() {
        refresf_table(Helper.tell0Line(this.root));
    }

    private void refresf_table(int position_on_top) {
        Helper.setNumVisibleNodeOnTop(this.root, position_on_top + 1);
        menues.UpdateDataWithChildren(this.root);
        menues.ConnectTableAndData(this._menu, this.table);
        menues.RedrawTable(this._menu, this.table);
    }

    private void addLine(Line line) {
        line.insertInTable(this._menu, this.table_root);
        this.lines.add(line);

        if (0L != this.table_ranger) {
            MENU_ranger rang = menues.ConvertRanger(this.table_ranger);

            rang.max_value = (this.lines.size() - this.num_rows);

            if (rang.max_value < rang.min_value) {
                rang.max_value = rang.min_value;
            }

            menues.UpdateField(rang);
        }

        menues.AddGroupInTable(this._menu, this.table, get_line_nom(), line.getLine());
    }

    /**
     * Method description
     *
     *
     * @param xmlfilename
     * @param xmlcontrolgroup
     * @param marked_names
     */
    public void fillWithLines(String xmlfilename, String xmlcontrolgroup, String[] marked_names) {
        for (int i = 0; i < this.num_rows; ++i) {
            Line line = new Line(xmlfilename, xmlcontrolgroup, marked_names);

            line.load(this._menu);
            addLine(line);

            switch (this.mode_shift) {
             case 2 :
                 line.shiftLine(-this.rows_shift * i, 0);

                 break;

             case 3 :
                 line.shiftLine(0, -this.rows_shift * i);

                 break;

             case 0 :
                 line.shiftLine(this.rows_shift * i, 0);

                 break;

             case 1 :
                 line.shiftLine(0, this.rows_shift * i);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param data
     */
    public void reciveTreeData(Cmenu_TTI data) {
        this.root = data;
        menues.FillMajorDataTable_ScriptObject(this._menu, this.table, this, "reciveTreeData_native");
        menues.ConnectTableAndData(this._menu, this.table);
        this.selected_one = null;
        this.selectedLines.clear();
        renewSelection();
    }

    private Cmenu_TTI findEqualObject(ICompareLines comparator, Cmenu_TTI root, Cmenu_TTI compareto) {
        if ((null == compareto) || (null == root)) {
            return null;
        }

        if (comparator.equal(root.item, compareto.item)) {
            return root;
        }

        for (Iterator<?> i$ = root.children.iterator(); i$.hasNext(); ) {
            Object item = i$.next();
            Cmenu_TTI menu_data = (Cmenu_TTI) item;
            Cmenu_TTI res = findEqualObject(comparator, menu_data, compareto);

            if (null != res) {
                return res;
            }
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @param data
     * @param comparator
     */
    public void reciveTreeData(Cmenu_TTI data, ICompareLines comparator) {
        this.root = data;
        menues.FillMajorDataTable_ScriptObject(this._menu, this.table, this, "reciveTreeData_native");
        menues.ConnectTableAndData(this._menu, this.table);
        this.selected_one = findEqualObject(comparator, this.root, this.selected_one);

        ArrayList<Cmenu_TTI> old_selectedLines = (ArrayList<Cmenu_TTI>) this.selectedLines.clone();

        this.selectedLines.clear();

        for (int i = 0; i < old_selectedLines.size(); ++i) {
            Cmenu_TTI selected = findEqualObject(comparator, this.root, old_selectedLines.get(i));

            if (selected != null) {
                this.selectedLines.add(selected);
            }
        }

        renewSelection();
    }

    /**
     * Method description
     *
     *
     * @param data
     */
    public void updateTreeData(Cmenu_TTI data) {
        if (null == this.root) {
            return;
        }

        this.root.children = data.children;
        menues.UpdateDataWithChildren(this.root);
        menues.SetXMLDataOnTable(this._menu, this.table, this.root);
        menues.ConnectTableAndData(this._menu, this.table);
        this.selectedLines.clear();
        renewSelection();
    }

    protected void reciveTreeData_native(Cmenu_TTI data) {
        if (null == this.root) {
            return;
        }

        data.children = this.root.children;
        this.root = data;
        menues.UpdateDataWithChildren(this.root);
    }

    /**
     * Method description
     *
     *
     * @param setup
     */
    public void takeSetuperForAllLines(ISetupLine setup) {
        Iterator<Line> iter = this.lines.iterator();

        while (iter.hasNext()) {
            menues.ScriptObjSyncGroup(this._menu, iter.next().getLine(), setup, "SetupLineInTable");
        }
    }

    /**
     * Method description
     *
     *
     * @param controlsname
     */
    public void initLinesSelection(String controlsname) {
        initRadioGroup(this._menu, controlsname);
    }

    private void initRadioGroup(long _menu, String name) {
        long[] res = getLineStatistics_controls(name);

        if (res.length == 0) {
            return;
        }

        MENUbutton_field[] buttons = new MENUbutton_field[res.length];

        for (int i = 0; i < res.length; ++i) {
            buttons[i] = menues.ConvertButton(res[i]);
            buttons[i].userid = i;
            menues.UpdateField(buttons[i]);
            menues.SetSyncControlState(_menu, this.thisACTIVE_SYNC + i, buttons[i].nativePointer);
            menues.SetSyncControlActive(_menu, this.thisACTIVE_SYNC + i, buttons[i].nativePointer);
        }

        RadioGroup group = new RadioGroup(_menu, buttons, this.make_track_activity);

        group.addListener(this);
        group.addAccessListener(this);

        if (this.make_track_activity) {
            group.addActiveListener(this.trackActivity);
        }

        this.selectedLine.add(group);
    }

    private void makeSingleSelection(MENUbutton_field button, int cs) {
        if (cs == 1) {
            Iterator<ISelectLineListener> iter = this.select_listeners.iterator();

            while (iter.hasNext()) {
                iter.next().selectLineEvent(this, button.userid);
            }

            this.selected_one = getItemOnLine(button.userid);

            for (RadioGroup group : this.selectedLine) {
                group.silentSelect(button.userid);
            }
        } else {
            for (RadioGroup group : this.selectedLine) {
                group.silentDeselect(button.userid);
            }
        }

        switch (this.mode_selection) {
         case 1 :
             break;

         case 2 :
             if (1 == cs) {
                 this.selectedLines.clear();
                 this.selectedLines.add(this.selected_one);

                 return;
             }

             this.selectedLines.remove(getItemOnLine(button.userid));
        }
    }

    @SuppressWarnings("unused")
    private void makeShiftSelection(MENUbutton_field button, int cs) {
        Cmenu_TTI clicked_one = getItemOnLine(button.userid);

        for (RadioGroup group : this.selectedLine) {
            group.deselectall();
        }

        if (this.selected_one.equals(clicked_one)) {
            this.selectedLines.clear();
            this.selectedLines.add(this.selected_one);

            for (RadioGroup group : this.selectedLine) {
                group.silentSelect(button.userid);
            }

            tellMulipleSelection();

            return;
        }

        this.selectedLines.clear();

        int firstline = Helper.tellLine(this.root, this.selected_one);
        int secondline = Helper.tellLine(this.root, clicked_one);

        if (firstline < secondline) {
            Helper.traverseTree(this.root, this.selected_one, clicked_one, new Visitor());
        } else {
            Helper.traverseTree(this.root, clicked_one, this.selected_one, new Visitor());
        }

        for (Iterator<Cmenu_TTI> i$ = this.selectedLines.iterator(); i$.hasNext(); ) {
            Cmenu_TTI line = i$.next();

            for (RadioGroup group : this.selectedLine) {
                int line_num = Helper.tellLine(this.root, line);

                if ((line_num >= 0) && (line_num < this.num_rows)) {
                    group.silentSelect(line_num);
                }
            }
        }

        Cmenu_TTI line;

        tellMulipleSelection();
    }

    private void makeCrtlSelection(MENUbutton_field button, int cs) {
        Cmenu_TTI selected_item = getItemOnLine(button.userid);

        if ((cs == 1) && (selected_item != this.selected_one) && (!(this.selectedLines.contains(selected_item)))) {
            Iterator<ISelectLineListener> iter = this.select_listeners.iterator();

            while (iter.hasNext()) {
                iter.next().selectLineEvent(this, button.userid);
            }

            this.selected_one = selected_item;

            if (!(this.selectedLines.contains(this.selected_one))) {
                this.selectedLines.add(this.selected_one);
            }

            for (RadioGroup group : this.selectedLine) {
                group.silentSelect(button.userid);
            }
        } else {
            this.selectedLines.remove(selected_item);

            for (RadioGroup group : this.selectedLine) {
                group.silentDeselect(button.userid);
            }
        }

        tellMulipleSelection();
    }

    private void tellMulipleSelection() {
        Iterator<ISelectLineListener> iter = this.select_listeners.iterator();

        while (iter.hasNext()) {
            iter.next().selectMultipleLinesEvent(this, new ArrayList<Cmenu_TTI>(this.selectedLines));
        }
    }

    /**
     * Method description
     *
     *
     * @param button
     * @param cs
     */
    @Override
    public void controlSelected(MENUbutton_field button, int cs) {
        if ((this.selectedLine == null) || (this.selectedLine.isEmpty())) {
            return;
        }

        wheelControlSelected();

        switch (this.mode_selection) {
         case 1 :
             makeSingleSelection(button, cs);

             break;

         case 2 :
             boolean is_multyselect = false;

             if (!(this.state_raging)) {
                 boolean shift = Controls.isShiftPressed();
                 boolean ctrl = Controls.isControlPressed();

                 is_multyselect = (shift) || (ctrl) || (this.state_crtlA);

                 if (!(is_multyselect)) {
                     makeSingleSelection(button, cs);
                 } else if ((!(this.state_crtlA)) && (shift)) {
                     makeShiftSelection(button, cs);
                 } else if ((this.state_crtlA) || (ctrl)) {
                     makeCrtlSelection(button, cs);
                 }

                 return;
             }

             is_multyselect = this.selectedLines.size() > 1;

             if (!(is_multyselect)) {
                 makeSingleSelection(button, cs);

                 return;
             }

             makeCrtlSelection(button, cs);
        }
    }

    /**
     * Method description
     *
     *
     * @param add
     */
    public void addListener(ISelectLineListener add) {
        this.select_listeners.add(add);
    }

    /**
     * Method description
     *
     *
     * @param add
     */
    public void addFocusListener(IFocusListener add) {
        this.focus_listeners.add(add);
    }

    /**
     * Method description
     *
     *
     * @param add
     */
    public void addRangerListener(IRangerListener add) {
        this.ranger_listeners.add(add);
    }

    /**
     * Method description
     *
     */
    public void afterInit() {
        this.init_session = false;

        if ((this.selectedLine != null) && (this.root != null) && (!(this.root.children.isEmpty()))) {
            this.selected_one = ((Cmenu_TTI) this.root.children.get(0));
        }

        refresh();
    }

    /**
     * Method description
     *
     */
    public void deinit() {
        wheelDeinit();
        this.selected_one = null;
        this.select_listeners = null;
        this.focus_listeners = null;

        if (this.selectedLines != null) {
            this.selectedLines.clear();
        }

        this.selectedLines = null;
        this.root = null;
    }

    /**
     * Method description
     *
     *
     * @param line
     */
    public void refreshLine(int line) {
        menues.RedrawGroup(this._menu, menues.GetGroupOnLine(this._menu, this.table, line));
    }

    /**
     * Method description
     *
     */
    public void refresh_no_select() {
        if (0L != this.table_ranger) {
            int rows = Helper.numVisibleNodes(this.root);

            if (rows <= this.num_rows) {
                MENU_ranger ranger = menues.ConvertRanger(this.table_ranger);

                ranger.min_value = 0;
                ranger.max_value = 0;
                ranger.current_value = 0;
                menues.UpdateField(ranger);
            } else {
                MENU_ranger ranger = menues.ConvertRanger(this.table_ranger);

                ranger.min_value = 0;
                ranger.max_value = (rows - this.num_rows);
                ranger.current_value = 0;
                menues.UpdateField(ranger);
            }
        }

        refresf_table(0);

        if (this.selected_one != null) {
            int pos = Helper.tellLine(this.root, this.selected_one);

            if (pos < 0) {
                MENU_ranger rang = menues.ConvertRanger(this.table_ranger);

                rang.current_value += pos;
                menues.UpdateField(rang);
            } else if (pos >= this.num_rows) {
                MENU_ranger rang = menues.ConvertRanger(this.table_ranger);

                rang.current_value += pos - this.num_rows + 1;
                menues.UpdateField(rang);
            }
        } else if (this.selectedLines.isEmpty()) {
            select_line(0);
        } else {
            for (Cmenu_TTI item : this.selectedLines) {
                int pos = Helper.tellLine(this.root, item);

                if ((pos >= 0) && (pos < this.num_rows)) {
                    this.selected_one = item;

                    return;
                }
            }

            int pos = Helper.tellLine(this.root, this.selectedLines.get(0));

            if (pos < 0) {
                MENU_ranger rang = menues.ConvertRanger(this.table_ranger);

                rang.current_value += pos;
                menues.UpdateField(rang);
            } else if (pos >= this.num_rows) {
                MENU_ranger rang = menues.ConvertRanger(this.table_ranger);

                rang.current_value += pos - this.num_rows + 1;
                menues.UpdateField(rang);
            }

            this.selected_one = (this.selectedLines.get(0));
        }

        ArrayList<Cmenu_TTI> lines = new ArrayList<Cmenu_TTI>();

        for (Cmenu_TTI item : this.selectedLines) {
            lines.add(item);
        }

        Iterator<ISelectLineListener> iter = this.select_listeners.iterator();

        while (iter.hasNext()) {
            iter.next().selectMultipleLinesEvent(this, lines);
        }
    }

    /**
     * Method description
     *
     */
    public void refresh() {
        if (0L != this.table_ranger) {
            int rows = Helper.numVisibleNodes(this.root);

            if (rows <= this.num_rows) {
                MENU_ranger ranger = menues.ConvertRanger(this.table_ranger);

                ranger.min_value = 0;
                ranger.max_value = 0;
                ranger.current_value = 0;
                menues.UpdateField(ranger);
            } else {
                MENU_ranger ranger = menues.ConvertRanger(this.table_ranger);

                ranger.min_value = 0;
                ranger.max_value = (rows - this.num_rows);
                ranger.current_value = 0;
                menues.UpdateField(ranger);
            }
        }

        if ((this.selectedLine != null) && (this.root != null) && (!(this.root.children.isEmpty()))) {
            this.selected_one = ((Cmenu_TTI) this.root.children.get(0));
        }

        refresf_table(0);
        select_line(0);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getNumRows() {
        return this.num_rows;
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public long[] getLineStatistics_controls(String name) {
        long[] res = new long[this.lines.size()];
        int i = 0;
        Iterator<Line> iter = this.lines.iterator();

        while (iter.hasNext()) {
            res[(i++)] = iter.next().getNamedControl(name);
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param line
     *
     * @return
     */
    public Cmenu_TTI getItemOnLine(int line) {
        if (line >= this.lines.size()) {
            return null;
        }

        return menues.GetXMLDataOnGroup(this._menu, this.lines.get(line).getLine());
    }

    /**
     * Method description
     *
     *
     * @param line
     * @param value
     */
    public void active_line(int line, boolean value) {
        if ((this.selectedLine != null) && (line < this.lines.size())) {
            for (RadioGroup group : this.selectedLine) {
                group.makeactive(line, value);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param line
     * @param value
     */
    public void pressed_line(int line, boolean value) {
        if ((this.selectedLine != null) && (line < this.lines.size())) {
            for (RadioGroup group : this.selectedLine) {
                group.makepressed(line, value);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param line
     */
    public void select_line(int line) {
        if ((this.selectedLine != null) && (line < this.lines.size())) {
            for (RadioGroup group : this.selectedLine) {
                group.silentSelect(line);
            }
        }

        MENUbutton_field butt = new MENUbutton_field();

        butt.userid = line;
        makeSingleSelection(butt, 1);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getSelected() {
        if (this.selectedLine != null) {
            return Helper.tellItemLine(this.root, this.selected_one);
        }

        return 0;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Cmenu_TTI getSelectedData() {
        return this.selected_one;
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param value
     *
     * @return
     */
    public boolean active_line_by_data(Object obj, boolean value) {
        if (this.selectedLine != null) {
            Cmenu_TTI item = Helper.findInTree(this.root, obj);

            if (item == null) {
                return false;
            }

            int pos = Helper.tellLine(this.root, item);

            if (pos >= 0) {
                if (pos < this.num_rows) {
                    active_line(pos, value);
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param value
     *
     * @return
     */
    public boolean pressed_line_by_data(Object obj, boolean value) {
        if (this.selectedLine != null) {
            Cmenu_TTI item = Helper.findInTree(this.root, obj);

            if (item == null) {
                return false;
            }

            int pos = Helper.tellLine(this.root, item);

            if (pos >= 0) {
                if (pos < this.num_rows) {
                    pressed_line(pos, value);
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param obj
     *
     * @return
     */
    public boolean select_line_by_data(Object obj) {
        if (this.selectedLine != null) {
            Cmenu_TTI item = Helper.findInTree(this.root, obj);

            if (item == null) {
                return false;
            }

            int pos = Helper.tellLine(this.root, item);

            if (pos < 0) {
                MENU_ranger rang = menues.ConvertRanger(this.table_ranger);

                rang.current_value += pos;
                menues.UpdateField(rang);
                refresf_table(rang.current_value);
                select_line(0);
            } else if (pos >= this.num_rows) {
                MENU_ranger rang = menues.ConvertRanger(this.table_ranger);

                rang.current_value += pos - this.num_rows + 1;
                menues.UpdateField(rang);
                refresf_table(rang.current_value);
                select_line(this.num_rows - 1);
            } else {
                select_line(pos);
            }

            return true;
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param button
     * @param state
     *
     * @return
     */
    @Override
    public boolean controlAccessed(MENUbutton_field button, int state) {
        if ((this.selectedLine == null) || (this.selectedLine.isEmpty())) {
            return false;
        }

        switch (this.mode_selection) {
         case 1 :
             return (state == 1);

         case 2 :
             boolean is_multyselect = false;

             if (!(this.state_raging)) {
                 boolean shift = Controls.isShiftPressed();
                 boolean ctrl = Controls.isControlPressed();

                 if ((!(shift)) && (!(ctrl)) && (!(this.state_crtlA))) {
                     for (RadioGroup item : this.selectedLine) {
                         item.setSingleSelectionMode();
                     }
                 } else {
                     is_multyselect = true;

                     for (RadioGroup item : this.selectedLine) {
                         item.setMultySelectionMode();
                     }
                 }

                 if (!(is_multyselect)) {
                     return (state == 1);
                 }

                 if ((!(this.state_crtlA)) && (shift)) {
                     return true;
                 }

                 if ((this.state_crtlA) || (ctrl)) {
                     if (state == 0) {
                         return ((this.selectedLines.size() != 1)
                                 || (!(this.selectedLines.contains(getItemOnLine(button.userid)))));
                     }

                     return true;
                 }
             } else {
                 is_multyselect = this.selectedLines.size() > 1;

                 if (!(is_multyselect)) {
                     return (state == 1);
                 }

                 if (state == 0) {
                     return ((this.selectedLines.size() != 1)
                             || (!(this.selectedLines.contains(getItemOnLine(button.userid)))));
                 }

                 return true;
             }

             return false;
        }

        return false;
    }

    /**
     * Method description
     *
     */
    @Override
    public void ControlsCtrlAPressed() {
        this.state_crtlA = true;
        this.selectedLines.clear();

        for (RadioGroup item : this.selectedLine) {
            item.setMultySelectionMode();
        }

        Helper.traverseTree(this.root, new Visitor());

        for (Iterator<Cmenu_TTI> i$ = this.selectedLines.iterator(); i$.hasNext(); ) {
            Cmenu_TTI line = i$.next();

            for (RadioGroup group : this.selectedLine) {
                int line_num = Helper.tellLine(this.root, line);

                if ((line_num >= 0) && (line_num < this.num_rows)) {
                    group.silentSelect(line_num);
                }
            }
        }

        Cmenu_TTI line;

        tellMulipleSelection();
        this.state_crtlA = false;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void ControlsMouseWheel(int value) {
        if (this.table_ranger != 0L) {
            MENU_ranger rang = menues.ConvertRanger(this.table_ranger);

            rang.current_value -= value;

            if (rang.current_value > rang.max_value) {
                rang.current_value = rang.max_value;
            }

            if (rang.current_value < rang.min_value) {
                rang.current_value = rang.min_value;
            }

            menues.UpdateField(rang);
            refresf_table(rang.current_value);
        }
    }

    /**
     * Method description
     *
     */
    public void deselectAll() {
        for (RadioGroup group : this.selectedLine) {
            group.deselectall();
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void cbEnterFocus() {
        for (IFocusListener item : this.focus_listeners) {
            item.enterFocus(this);
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void cbLeaveFocus() {
        for (IFocusListener item : this.focus_listeners) {
            item.leaveFocus(this);
        }

        if (this.looseSelectionOnLeaveFocus) {
            for (RadioGroup item : this.selectedLine) {
                item.deselectall();
            }
        }
    }

    class TrackActivity implements IActivePressedTracker {
        int[] count_pressed;
        int[] count_active;

        TrackActivity() {
            this.count_active = new int[Table.this.num_rows];
            this.count_pressed = new int[Table.this.num_rows];

            for (int i = 0; i < Table.this.num_rows; ++i) {
                this.count_active[i] = 0;
                this.count_pressed[i] = 0;
            }
        }

        /**
         * Method description
         *
         *
         * @param id
         */
        @Override
        public void onActive(int id) {
            this.count_active[id] += 1;
        }

        /**
         * Method description
         *
         *
         * @param id
         */
        @Override
        public void onPassive(int id) {
            this.count_active[id] -= 1;
        }

        /**
         * Method description
         *
         *
         * @param id
         */
        @Override
        public void onPress(int id) {
            if (this.count_pressed[id] == 0) {
                this.count_pressed[id] += 1;
            }
        }

        /**
         * Method description
         *
         *
         * @param id
         */
        @Override
        public void onRelease(int id) {
            if (this.count_pressed[id] > 0) {
                this.count_pressed[id] -= 1;
            }
        }
    }


    class Visitor implements ITableNodeVisitor {

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void visitNode(Cmenu_TTI node) {
            Table.this.selectedLines.add(node);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
