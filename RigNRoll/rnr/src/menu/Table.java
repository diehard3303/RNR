/*
 * @(#)Table.java   13/08/25
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

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class Table extends IWheelEnabled {

    /** Field description */
    public static final int TABLE_GETLASTID = 1;

    /** Field description */
    public static final int TABLE_SINGLECHECK = 2;

    /** Field description */
    public static final int TABLE_NONPERMSINGLECHECK = 4;

    /** Field description */
    public static final int TABLE_AUTOHIDEEMPTYLINES = 8;

    /** Field description */
    public static final int TABLE_HORIZONTAL = 16;

    /** Field description */
    public static final int TABLE_NOKEYSCROLL = 32;

    /** Field description */
    public static final int TRAVERSE_SHOWCH_ENABLE = 1;

    /** Field description */
    public static final int TRAVERSE_FAKE_ENABLE = 2;

    /** Field description */
    public static final int CONTROL_TEXTFIELD = 0;

    /** Field description */
    public static final int CONTROL_SIMPLEBUTTON = 1;

    /** Field description */
    public static final int CONTROL_RADIOBUTTON = 2;

    /** Field description */
    public static final int CONTROL_TRUCKVIEW = 3;
    static int s_maxid = 0;
    static HashMap<?, ?> s_GlobalEntries;
    int m_iAnimationID = Common.GetID();
    int m_iFakeNum = 0;
    Vector<Long> m_FakeGroups = new Vector<Long>();
    private boolean buzzy_OnRangerScroll = false;
    int m_iLastUserID = -1;
    Vector<Integer> m_Events;
    MENU_ranger m_ranger;
    Vector<TableNode> m_AnimList;
    boolean m_isopening;
    int m_iCounter;
    double m_delay;
    TableAnimation m_animcb;
    TableNode m_animnode;
    TableCmp m_lastcomp;
    Vector<ItemInfo> m_items;
    TableTree m_rootnode;
    long[] m_groups;
    int m_iLinenum;
    int m_iVisSize;
    Cmenu_TTI m_root;
    Cmenu_TTI m_top;
    TableCallbacks m_callbacks;
    TableNode m_singlechecked;
    boolean m_bAutoHideEmpty;
    boolean m_bSingleChecked;
    boolean m_bPermSingleChecked;
    boolean m_bGetLastID;
    boolean m_bSelect;
    boolean m_bHorizontal;
    boolean m_bKeyscroll;
    long m_iLastControl;
    TableSelect m_SelectCbs;
    TableNode m_SelectNode;
    long m_iSelectedControl;
    int m_iSelectedID;
    int m_iCurrentSortType;
    int m_iCurrentTopLine;
    boolean m_bCurrentOrder;
    long m_menu;
    long m_table;

    /**
     * Constructs ...
     *
     *
     * @param menu
     * @param name
     */
    public Table(long menu, String name) {
        this.m_menu = menu;
        this.m_items = new Vector<ItemInfo>();
        this.m_Events = new Vector<Integer>();
        SetupRootNode();
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
     */
    public void DeInit() {
        wheelDeinit();
        this.m_rootnode = null;
        this.m_root = null;
        this.m_top = null;
    }

    void SetupRootNode() {
        this.m_rootnode = new TableTree();
        this.m_rootnode.showch = true;
        this.m_rootnode.item = new TableNode();
        this.m_rootnode.item.buildtime = this.m_rootnode;
        this.m_rootnode.item.depth = -1;
        this.m_rootnode.item.item = null;
    }

    /**
     * Method description
     *
     *
     * @param vertsize
     * @param linenum
     * @param xmlfile
     * @param xmlgroup
     * @param parentname
     * @param cbs
     * @param flags
     */
    public void Setup(long vertsize, int linenum, String xmlfile, String xmlgroup, String parentname,
                      TableCallbacks cbs, int flags) {
        this.m_iCurrentSortType = -1;
        this.m_bCurrentOrder = false;
        this.m_iCurrentTopLine = 0;
        this.m_bSingleChecked = ((flags & 0x2) != 0);
        this.m_bPermSingleChecked = ((flags & 0x4) == 0);
        this.m_bGetLastID = ((flags & 0x1) != 0);
        this.m_bAutoHideEmpty = ((flags & 0x8) != 0);
        this.m_bHorizontal = ((flags & 0x10) != 0);
        this.m_bKeyscroll = ((flags & 0x20) == 0);

        if (this.m_bKeyscroll) {
            long parent = menues.FindFieldInMenu(this.m_menu, parentname);
            Object parento = menues.ConvertMenuFields(parent);

            menues.SetScriptOnControl(this.m_menu, parento, this, "OnKeyOutUp", 15L);
            menues.SetScriptOnControl(this.m_menu, parento, this, "OnKeyOutDown", 14L);
        }

        wheelInit(this.m_menu, parentname);
        this.m_callbacks = cbs;
        this.m_iLinenum = linenum;
        this.m_groups = new long[linenum];
        this.m_table = menues.CreateTable(this.m_menu);

        for (int line = 0; line < linenum; ++line) {
            long group = menues.CreateGroup(this.m_menu);

            this.m_groups[line] = group;
            menues.AddGroupInTable(this.m_menu, this.m_table, line, group);
            menues.ScriptObjSyncGroup(this.m_menu, group, this, "SetupLineInTable");
            SetupCallbacks(group);
        }

        for (int i = 0; i < this.m_items.size(); ++i) {
            for (int line = 0; line < linenum; ++line) {
                long group = this.m_groups[line];
                ItemInfo item = this.m_items.get(i);
                long control = menues.InitXmlControl(this.m_menu, xmlfile, xmlgroup, item.name);

                if (item.uniquenaming) {
                    item.realname = item.name + item.userid;
                } else {
                    item.realname = item.name;
                }

                Object obj = null;

                switch (item.type) {
                 case 0 :
                     MENUText_field field = menues.ConvertTextFields(control);

                     if (!(this.m_bHorizontal)) {
                         MENUText_field tmp448_446 = field;

                         tmp448_446.poy = (int) (tmp448_446.poy + line * vertsize);
                         field.pox += item.xshift;
                     } else {
                         MENUText_field tmp483_481 = field;

                         tmp483_481.pox = (int) (tmp483_481.pox + line * vertsize);
                         field.poy += item.xshift;
                     }

                     field.userid = item.userid;
                     field.nameID = item.realname;
                     field.parentName = parentname;
                     menues.UpdateField(field);
                     obj = field;

                     break;

                 case 1 :
                     MENUsimplebutton_field button = menues.ConvertSimpleButton(control);

                     if (!(this.m_bHorizontal)) {
                         MENUsimplebutton_field tmp568_566 = button;

                         tmp568_566.poy = (int) (tmp568_566.poy + line * vertsize);
                         button.pox += item.xshift;
                     } else {
                         MENUsimplebutton_field tmp603_601 = button;

                         tmp603_601.pox = (int) (tmp603_601.pox + line * vertsize);
                         button.poy += item.xshift;
                     }

                     button.userid = item.userid;
                     button.nameID = item.realname;
                     button.parentName = parentname;
                     menues.UpdateField(button);
                     obj = button;

                     break;

                 case 2 :
                     MENUbutton_field radio = menues.ConvertButton(control);

                     if (!(this.m_bHorizontal)) {
                         MENUbutton_field tmp688_686 = radio;

                         tmp688_686.poy = (int) (tmp688_686.poy + line * vertsize);
                         radio.pox += item.xshift;
                     } else {
                         MENUbutton_field tmp723_721 = radio;

                         tmp723_721.pox = (int) (tmp723_721.pox + line * vertsize);
                         radio.poy += item.xshift;
                     }

                     radio.userid = item.userid;
                     radio.nameID = item.realname;
                     radio.parentName = parentname;
                     menues.UpdateField(radio);
                     obj = radio;

                     break;

                 case 3 :
                     MENUTruckview truckview = (MENUTruckview) menues.ConvertMenuFields(control);

                     if (!(this.m_bHorizontal)) {
                         MENUTruckview tmp811_809 = truckview;

                         tmp811_809.poy = (int) (tmp811_809.poy + line * vertsize);
                         truckview.pox += item.xshift;
                     } else {
                         MENUTruckview tmp846_844 = truckview;

                         tmp846_844.pox = (int) (tmp846_844.pox + line * vertsize);
                         truckview.poy += item.xshift;
                     }

                     truckview.userid = item.userid;
                     truckview.nameID = item.realname;
                     truckview.parentName = parentname;
                     menues.UpdateMenuField(truckview);
                     obj = truckview;
                }

                menues.AddControlInGroup(this.m_menu, group, obj);
                menues.LinkGroupAndControl(this.m_menu, obj);
                menues.ChangableFieldOnGroup(this.m_menu, obj);

                if (item.type != 0) {
                    menues.StoreControlState(this.m_menu, obj);
                }
            }
        }

        menues.FillMajorDataTable_ScriptObject(this.m_menu, this.m_table, this, "FillTable");
        RecalcGroups();
        SetupRootNode();

        if ((!(this.m_bSingleChecked)) || (this.m_root.children.size() <= 0) || (!(this.m_bPermSingleChecked))) {
            return;
        }

        Cmenu_TTI child = (Cmenu_TTI) this.m_root.children.get(0);

        if (child == null) {
            return;
        }

        this.m_singlechecked = ((TableNode) child.item);
        this.m_singlechecked.checked = true;
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    public void RedrawItemLine(TableNode node) {
        menues.RedrawGroup(this.m_menu, node.group);
    }

    /**
     * Method description
     *
     *
     * @param cbs
     */
    public void AttachSelectCbs(TableSelect cbs) {
        if (cbs == null) {
            this.m_bSelect = false;

            return;
        }

        this.m_bSelect = true;
        this.m_SelectCbs = cbs;
        this.m_iSelectedControl = 0L;
        this.m_SelectNode = null;
    }

    /**
     * Method description
     *
     */
    public void Deselect() {
        CheckDeselect();
        this.m_SelectNode = null;
    }

    /**
     * Method description
     *
     *
     * @param line
     *
     * @return
     */
    public TableNode GetNodeByLine(int line) {
        if (line >= this.m_root.children.size() - this.m_iLinenum) {
            return null;
        }

        return ((TableNode) ((Cmenu_TTI) this.m_root.children.get(line)).item);
    }

    /**
     * Method description
     *
     */
    public void SyncLineStates() {
        for (int line = 0; line < this.m_iLinenum; ++line) {
            int groupid = Common.GetID();

            for (int i = 0; i < this.m_items.size(); ++i) {
                ItemInfo item = this.m_items.get(i);

                if (item.type == 0) {
                    continue;
                }

                long control = menues.FindFieldInGroup(this.m_menu, GetGroupByLine(line), item.name);

                menues.SetSyncControlActive(this.m_menu, groupid, control);
                menues.SetSyncControlState(this.m_menu, groupid, control);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param start
     * @param finish
     */
    public void SyncLineStates(int start, int finish) {
        for (int line = 0; line < this.m_iLinenum; ++line) {
            int groupid = Common.GetID();

            for (int i = start; i < Math.min(finish, this.m_items.size()); ++i) {
                ItemInfo item = this.m_items.get(i);
                long control = menues.FindFieldInGroup(this.m_menu, GetGroupByLine(line), item.name);

                menues.SetSyncControlActive(this.m_menu, groupid, control);
                menues.SetSyncControlState(this.m_menu, groupid, control);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param group
     */
    public void SetupCallbacks(long group) {
        for (int i = 0; i < this.m_Events.size(); ++i) {
            int event = this.m_Events.get(i).intValue();

            switch (event) {
             case 4 :
                 menues.SetScriptOnGroup(this.m_menu, group, this, "OnButtonPress", 4L);

                 break;

             case 2 :
                 menues.SetScriptOnGroup(this.m_menu, group, this, "OnRadioPress", 2L);

                 break;

             case 3 :
                 menues.SetScriptOnGroup(this.m_menu, group, this, "OnMouseOver", 3L);

                 break;

             case 6 :
                 menues.SetScriptOnGroup(this.m_menu, group, this, "OnMouseInside", 6L);

                 break;

             case 5 :
                 menues.SetScriptOnGroup(this.m_menu, group, this, "OnMouseOutside", 5L);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public TableNode GetSingleChecked() {
        return this.m_singlechecked;
    }

    /**
     * Method description
     *
     */
    public void RedrawTable() {
        RecalcGroups();
        menues.RedrawTable(this.m_menu, this.m_table);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     */
    public void AddTextField(String name, int userid) {
        AddTextField(name, userid, 0, false);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     * @param xshift
     */
    public void AddTextField(String name, int userid, int xshift) {
        AddTextField(name, userid, xshift, true);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     */
    public void AddUniqueTextField(String name, int userid) {
        AddTextField(name, userid, 0, true);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     */
    public void AddTruckView(String name, int userid) {
        AddTruckView(name, userid, 0, false);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     * @param xshift
     */
    public void AddTruckView(String name, int userid, int xshift) {
        AddTruckView(name, userid, xshift, true);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     * @param xshift
     * @param uniquename
     */
    public void AddTruckView(String name, int userid, int xshift, boolean uniquename) {
        this.m_items.add(new ItemInfo(3, name, userid, xshift, uniquename));
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     * @param xshift
     * @param uniquename
     */
    public void AddTextField(String name, int userid, int xshift, boolean uniquename) {
        this.m_items.add(new ItemInfo(0, name, userid, xshift, uniquename));
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     */
    public void AddSimpleButton(String name, int userid) {
        AddSimpleButton(name, userid, 0, false);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     * @param xshift
     */
    public void AddSimpleButton(String name, int userid, int xshift) {
        AddSimpleButton(name, userid, xshift, true);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     */
    public void AddUniqueSimpleButton(String name, int userid) {
        AddSimpleButton(name, userid, 0, true);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     * @param xshift
     * @param uniquename
     */
    public void AddSimpleButton(String name, int userid, int xshift, boolean uniquename) {
        this.m_items.add(new ItemInfo(1, name, userid, xshift, uniquename));
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     */
    public void AddUniqueRadioButton(String name, int userid) {
        AddRadioButton(name, userid, 0, true);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     */
    public void AddRadioButton(String name, int userid) {
        AddRadioButton(name, userid, 0, false);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     * @param xshift
     */
    public void AddRadioButton(String name, int userid, int xshift) {
        AddRadioButton(name, userid, xshift, true);
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param userid
     * @param xshift
     * @param uniquename
     */
    public void AddRadioButton(String name, int userid, int xshift, boolean uniquename) {
        this.m_items.add(new ItemInfo(2, name, userid, xshift, uniquename));
    }

    /**
     * Method description
     *
     *
     * @param event
     */
    public void AddEvent(int event) {
        this.m_Events.add(new Integer(event));
    }

    void UpdateRanger() {
        this.m_ranger.max_value = ((this.m_iVisSize > this.m_iLinenum)
                                   ? this.m_iVisSize - this.m_iLinenum
                                   : 0);
        this.m_ranger.current_value = ((TableNode) this.m_top.item).line;

        if (this.m_ranger.current_value > this.m_ranger.max_value) {
            this.m_ranger.current_value = this.m_ranger.max_value;
        }

        menues.UpdateField(this.m_ranger);
    }

    /**
     * Method description
     *
     *
     * @param ranger
     */
    public void AttachRanger(MENU_ranger ranger) {
        if (null == ranger) {
            Log.menu("Table. AttachRanger. Trying to attach null ranger.");

            return;
        }

        this.m_ranger = ranger;
        this.m_ranger.userid = s_maxid;
        this.m_ranger.page = this.m_iLinenum;
        this.m_ranger.current_value = 0;
        menues.SetScriptOnControl(this.m_menu, ranger, this, "OnRangerScroll", 1L);
        UpdateRanger();
    }

    Cmenu_TTI ComputeFirst() {
        for (int i = 0; i < this.m_root.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI) this.m_root.children.get(i);

            if (child.toshow == true) {
                return child;
            }
        }

        return null;
    }

    int ComputeSize(Cmenu_TTI node) {
        if (!(node.toshow)) {
            return 0;
        }

        int n = (node == this.m_root)
                ? 0
                : 1;

        if (node.showCH) {
            for (int i = 0; i < node.children.size(); ++i) {
                Cmenu_TTI child = (Cmenu_TTI) node.children.get(i);

                n += ComputeSize(child);
            }
        }

        return n;
    }

    /**
     * Method description
     *
     *
     * @param visitor
     */
    public void Traverse(TableVisitor visitor) {
        Traverse(visitor, 0);
    }

    /**
     * Method description
     *
     *
     * @param visitor
     * @param flags
     */
    public void Traverse(TableVisitor visitor, int flags) {
        for (int i = 0; i < this.m_root.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI) this.m_root.children.get(i);

            CallTraverse(child, visitor, flags);
        }
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param visitor
     * @param flags
     */
    public void Traverse(TableNode node, TableVisitor visitor, int flags) {
        for (int i = 0; i < node.self.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI) node.self.children.get(i);

            CallTraverse(child, visitor, flags);
        }
    }

    boolean IsFlag(int value, int flag) {
        return ((value & flag) != 0);
    }

    void CallTraverse(Cmenu_TTI node, TableVisitor visitor, int flags) {
        if (node != this.m_root) {
            TableNode tablenode = (TableNode) node.item;

            if ((tablenode.item != null) || (IsFlag(flags, 2))) {
                visitor.VisitNode(tablenode);
            }
        }

        if ((!(IsFlag(flags, 1))) || (node.showCH)) {
            for (int i = 0; i < node.children.size(); ++i) {
                Cmenu_TTI child = (Cmenu_TTI) node.children.get(i);

                CallTraverse(child, visitor, flags);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param visitor
     */
    public void TraverseWithParent(TableParentVisitor visitor) {
        for (int i = 0; i < this.m_root.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI) this.m_root.children.get(i);
            TableNode node = (TableNode) child.item;

            if (node.item == null) {
                continue;
            }

            TraverseParent(visitor, node, null);
        }
    }

    /**
     * Method description
     *
     *
     * @param visitor
     * @param start
     */
    public void TraverseWithParent(TableParentVisitor visitor, TableNode start) {
        for (int i = 0; i < start.self.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI) this.m_root.children.get(i);
            TableNode node = (TableNode) child.item;

            if (node.item == null) {
                continue;
            }

            TraverseParent(visitor, node, (TableNode) start.parent.item);
        }
    }

    /**
     * Method description
     *
     *
     * @param visitor
     * @param start
     */
    public void TraverseUp(TableVisitor visitor, TableNode start) {
        Cmenu_TTI node = start.parent;

        while (node != this.m_root) {
            visitor.VisitNode((TableNode) node.item);
            node = ((TableNode) node.item).parent;
        }
    }

    private void TraverseParent(TableParentVisitor visitor, TableNode node, TableNode parent) {
        visitor.VisitPreChildren(node);

        for (int i = 0; i < node.self.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI) node.self.children.get(i);

            TraverseParent(visitor, (TableNode) child.item, node);
        }

        visitor.VisitNodeWithParent(node, parent);
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    public void Check(TableNode node) {
        if (this.m_bSingleChecked) {
            if (node == this.m_singlechecked) {
                if (!(this.m_bPermSingleChecked)) {
                    this.m_singlechecked.checked = false;
                    menues.RedrawGroup(this.m_menu, this.m_singlechecked.group);
                    this.m_singlechecked = null;
                } else {
                    menues.RedrawGroup(this.m_menu, this.m_singlechecked.group);
                }

                return;
            }

            if (this.m_singlechecked != null) {
                this.m_singlechecked.checked = false;

                if (this.m_singlechecked.group != -1L) {
                    menues.RedrawGroup(this.m_menu, this.m_singlechecked.group);
                }
            }

            this.m_singlechecked = node;

            if (node != null) {
                this.m_singlechecked.checked = true;

                if (this.m_singlechecked.group != -1L) {
                    menues.RedrawGroup(this.m_menu, this.m_singlechecked.group);
                } else {
                    int newline = 0;
                    int topline = ((TableNode) this.m_top.item).line;

                    if ((this.m_singlechecked.line - topline >= this.m_iLinenum)
                            || (topline > this.m_singlechecked.line)) {
                        newline = this.m_singlechecked.line;
                    }

                    ChangeTop(newline);

                    if (this.m_ranger != null) {
                        UpdateRanger();
                    }

                    RedrawTable();
                }
            }

            return;
        }

        node.checked = (!(node.checked));

        if (node.group != -1L) {
            menues.RedrawGroup(this.m_menu, node.group);
        }
    }

    /**
     * Method description
     *
     */
    public void ResetSingleChecked() {
        if (this.m_singlechecked != null) {
            this.m_singlechecked.checked = false;
        }

        this.m_singlechecked = ((TableNode) FindItemByLine(this.m_root, 0).item);
        this.m_singlechecked.checked = true;
        RedrawTable();
    }

    /**
     * Method description
     *
     */
    public void ClearSingleChecked() {
        if (this.m_singlechecked != null) {
            Check(this.m_singlechecked);
        }
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param toredraw
     */
    public void ShowHideNode(TableNode node, boolean toredraw) {
        node.self.toshow = (!(node.self.toshow));
        menues.UpdateData(node.self);

        if (!(toredraw)) {
            return;
        }

        RecalcGroups();
        RedrawTable();
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param delay
     * @param animation
     */
    public void ShowHideSubtree(TableNode node, double delay, TableAnimation animation) {
        if (delay == 0.0D) {
            node.self.showCH = (!(node.self.showCH));
            menues.UpdateData(node.self);

            return;
        }

        this.m_delay = (delay / 1000.0D);
        this.m_animcb = animation;

        if (this.m_AnimList == null) {
            this.m_AnimList = new Vector<TableNode>();
        } else {
            this.m_AnimList.clear();
        }

        this.m_animnode = node;
        this.m_iCounter = -1;
        this.m_isopening = (!(node.self.showCH));
        Traverse(node, new MakeAnimList(), 1);

        if (this.m_isopening) {
            node.self.showCH = true;
            menues.UpdateData(node.self);
        }

        menues.SetScriptObjectAnimation(this.m_menu, this.m_iAnimationID, this, "ShowHideAnimation");
    }

    void ShowHideAnimation(long stuff, double time) {
        int count = (int) (time / this.m_delay);

        if (count > this.m_AnimList.size() - 1) {
            count = this.m_AnimList.size() - 1;
        }

        if (count <= this.m_iCounter) {
            return;
        }

        for (int i = this.m_iCounter + 1; i <= count; ++i) {
            int index = (this.m_isopening)
                        ? i
                        : this.m_AnimList.size() - i - 1;
            TableNode node = this.m_AnimList.get(index);

            node.self.toshow = this.m_isopening;
            menues.UpdateData(node.self);
        }

        RedrawTable();

        if (this.m_animcb != null) {
            for (int i = this.m_iCounter + 1; i <= count; ++i) {
                int index = (this.m_isopening)
                            ? i
                            : this.m_AnimList.size() - i - 1;
                TableNode node = this.m_AnimList.get(index);

                this.m_animcb.OnNodeChange(node, this.m_isopening);
            }
        }

        this.m_iCounter = count;

        if (count != this.m_AnimList.size() - 1) {
            return;
        }

        StopAnimation();
        this.m_animcb.OnFinishAnimation();
    }

    private void StopAnimation() {
        menues.StopScriptAnimation(this.m_iAnimationID);
        this.m_AnimList.clear();

        if (!(this.m_isopening)) {
            this.m_animnode.self.showCH = false;
            menues.UpdateData(this.m_animnode.self);
        }

        this.m_animnode = null;
        RedrawTable();
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param comparator
     */
    @SuppressWarnings("unchecked")
    public void SortTable(int type, TableCmp comparator) {
        if (type == this.m_iCurrentSortType) {
            this.m_bCurrentOrder = (!(this.m_bCurrentOrder));
        } else {
            this.m_iCurrentSortType = type;
            this.m_bCurrentOrder = true;
        }

        comparator.SetOrder(this.m_bCurrentOrder);
        this.m_lastcomp = comparator;

        PrivateComparator comp = new PrivateComparator(comparator);

        SortNode(this.m_root, comp);
        ChangeTop(0);
        menues.UpdateDataWithChildren(this.m_root);

        if (this.m_bSingleChecked) {
            ResetSingleChecked();
        } else {
            RedrawTable();
        }
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings("unchecked")
    public void RestoreLastSort() {
        if (this.m_lastcomp == null) {
            return;
        }

        PrivateComparator comp = new PrivateComparator(this.m_lastcomp);

        SortNode(this.m_root, comp);
        ChangeTop(0);
        menues.UpdateDataWithChildren(this.m_root);
        RedrawTable();
    }

    void ChangeTop(int line) {
        RecalcGroups();

        if (((TableNode) this.m_top.item).line == line) {
            return;
        }

        if (this.m_bSelect) {
            CheckDeselect();
        }

        this.m_top.ontop = false;
        menues.UpdateData(this.m_top);
        this.m_top = FindItemByLine(this.m_root, line);
        this.m_top.ontop = true;
        menues.UpdateData(this.m_top);
        menues.ConnectTableAndData(this.m_menu, this.m_table);
        RecalcGroups();

        if ((!(this.m_bSelect)) || (this.m_SelectNode == null)) {
            return;
        }

        CheckSelect(this.m_SelectNode, this.m_iSelectedID,
                    FindControlInGroup(this.m_SelectNode.group, this.m_iSelectedID));
    }

    long FindControlInGroup(long group, int id) {
        if (group == -1L) {
            return 0L;
        }

        for (int i = 0; i < this.m_items.size(); ++i) {
            ItemInfo item = this.m_items.get(i);

            if (item.userid == id) {
                return menues.FindFieldInGroup(this.m_menu, group, item.name);
            }
        }

        return 0L;
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param item
     * @param showch
     *
     * @return
     */
    public TableNode AddItem(TableNode node, Object item, boolean showch) {
        TableTree parent = (node == null)
                           ? this.m_rootnode
                           : node.buildtime;
        TableTree newitem = new TableTree();

        newitem.item = new TableNode();
        newitem.showch = showch;
        newitem.item.buildtime = newitem;
        newitem.item.checked = false;
        newitem.item.depth = ((parent.item == null)
                              ? 0
                              : parent.item.depth + 1);
        newitem.item.item = item;
        parent.children.add(newitem);

        return newitem.item;
    }

    /**
     * Method description
     *
     *
     * @param v
     */
    public void EraseTraverse(EraseVisitor v) {
        if (this.m_bSingleChecked) {
            if (this.m_singlechecked != null) {
                this.m_singlechecked.checked = false;
            }

            this.m_singlechecked = null;
        }

        VisitEraseTraverse(this.m_root, v);
        ActualEreaseTraverse(this.m_root);
        menues.SetXMLDataOnTable(this.m_menu, this.m_table, this.m_root);

        if (this.m_bSingleChecked) {
            ResetSingleChecked();
        } else {
            RedrawTable();
        }
    }

    private void VisitEraseTraverse(Cmenu_TTI node, EraseVisitor v) {
        TableNode n = (TableNode) node.item;

        if ((n.item != null) && (v.VisitNode(n))) {
            n.tokill = true;

            return;
        }

        for (int i = 0; i < node.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI) node.children.get(i);

            VisitEraseTraverse(child, v);
        }
    }

    private void ActualEreaseTraverse(Cmenu_TTI node) {
        for (int i = 0; i < node.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI) node.children.get(i);
            TableNode n = (TableNode) child.item;

            if (n.tokill) {
                node.children.remove(i);
                --i;
            } else {
                ActualEreaseTraverse(child);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int GetLineNum() {
        return this.m_iLinenum;
    }

    /**
     * Method description
     *
     *
     * @param line
     *
     * @return
     */
    public long GetGroupByLine(int line) {
        return this.m_groups[line];
    }

    /**
     * Method description
     *
     *
     * @param line
     *
     * @return
     */
    public int GetScreenLineByLogical(int line) {
        if (this.m_top == null) {
            return line;
        }

        return (line - ((TableNode) this.m_top.item).line);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Vector<Long> GetFakeGroups() {
        return this.m_FakeGroups;
    }

    /**
     * Method description
     *
     *
     * @param table
     */
    @SuppressWarnings("unchecked")
    public void FillTable(Cmenu_TTI table) {
        this.m_root = table;
        this.m_iCurrentTopLine = 0;

        int nodenum = SyncTrees(table, this.m_rootnode, null);

        if (nodenum - this.m_iLinenum > this.m_iFakeNum) {
            for (int i = this.m_iFakeNum; i < nodenum - this.m_iLinenum; ++i) {
                long group = menues.CreateGroup(this.m_menu);

                menues.AddGroupInTable(this.m_menu, this.m_table, i + this.m_iLinenum, group);
                this.m_FakeGroups.add(new Long(group));
            }

            this.m_iFakeNum = (nodenum - this.m_iLinenum);
        }

        ClearSetupTree(this.m_rootnode);

        for (int i = 0; i < this.m_iLinenum; ++i) {
            Cmenu_TTI fakenode = new Cmenu_TTI();
            TableNode faketablenode = new TableNode();

            faketablenode.self = fakenode;
            faketablenode.parent = this.m_root;
            fakenode.item = faketablenode;
            fakenode.children = new Vector<Cmenu_TTI>();
            fakenode.ontop = false;
            fakenode.showCH = false;
            fakenode.toshow = true;
            this.m_root.children.add(fakenode);
        }

        this.m_top = ComputeFirst();

        if (this.m_top != null) {
            this.m_top.ontop = true;
        }

        menues.UpdateDataWithChildren(table);
    }

    /**
     * Method description
     *
     */
    public void RefillTree() {
        if (this.m_root == null) {
            return;
        }

        FillTable(this.m_root);
        menues.SetXMLDataOnTable(this.m_menu, this.m_table, this.m_root);
        RecalcGroups();
        SetupRootNode();

        if (this.m_bSingleChecked) {
            ResetSingleChecked();
        } else {
            RedrawTable();
        }
    }

    void ClearSetupTree(TableTree setupnode) {
        setupnode.item.buildtime = null;
        setupnode.item = null;

        for (int i = 0; i < setupnode.children.size(); ++i) {
            ClearSetupTree(setupnode.children.get(i));
        }

        setupnode.children.clear();
    }

    /**
     * Method description
     *
     */
    public void ResetTop() {
        RecalcGroups();
        ChangeTop(0);
        menues.RedrawTable(this.m_menu, this.m_table);
    }

    /**
     * Method description
     *
     *
     * @param newtop
     */
    public void SetTop(TableNode newtop) {
        if ((newtop == null) || (newtop.self == this.m_top)) {
            return;
        }

        this.m_top.ontop = false;
        menues.UpdateData(this.m_top);
        this.m_top = newtop.self;
        this.m_top.ontop = true;
        menues.UpdateData(this.m_top);
        RedrawTable();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public TableNode GetTop() {
        return ((TableNode) this.m_top.item);
    }

    void RecalcGroups() {
        CInteger h = new CInteger(-1);
        CInteger rh = new CInteger(0);

        CalcGroups(this.m_root, h, rh);

        if ((this.m_top != null) && (!(this.m_top.toshow))) {
            this.m_top.ontop = false;
            menues.UpdateData(this.m_top);
            this.m_top = FindItemByLine(this.m_root, 0);
            this.m_top.ontop = true;
            menues.UpdateData(this.m_top);
            RecalcGroups();
        }

        this.m_iVisSize = (h.value - this.m_iLinenum);

        if ((this.m_bSingleChecked) && (this.m_singlechecked != null) && (!(this.m_singlechecked.self.toshow))) {
            this.m_singlechecked.checked = false;
            this.m_singlechecked = ((TableNode) FindItemByLine(this.m_root, 0).item);
            this.m_singlechecked.checked = true;
        }

        menues.ConnectTableAndData(this.m_menu, this.m_table);

        if (this.m_ranger != null) {
            UpdateRanger();
        }
    }

    void CalcGroups(Cmenu_TTI tablenode, CInteger linenum, CInteger real_line_num) {
        TableNode localitem = (TableNode) tablenode.item;

        localitem.real_line = real_line_num.value;

        if ((linenum == null) || (!(tablenode.toshow))) {
            localitem.line = -1;
            localitem.group = -1L;
        } else {
            localitem.line = linenum.value;

            int screenline = GetScreenLineByLogical(linenum.value);

            if ((screenline < 0) || (screenline >= this.m_iLinenum)) {
                localitem.group = -1L;
            } else {
                localitem.group = this.m_groups[screenline];
            }

            linenum.value += 1;
        }

        real_line_num.value += 1;

        for (int i = 0; i < tablenode.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI) tablenode.children.get(i);

            CalcGroups(child, ((tablenode.showCH) && (tablenode.toshow))
                              ? linenum
                              : null, real_line_num);
        }
    }

    @SuppressWarnings("unchecked")
    int SyncTrees(Cmenu_TTI tablenode, TableTree setupnode, Cmenu_TTI parent) {
        int sum = 1;

        tablenode.item = setupnode.item;
        tablenode.showCH = setupnode.showch;
        tablenode.ontop = false;
        tablenode.toshow = true;
        tablenode.children = new Vector<Cmenu_TTI>();
        setupnode.item.self = tablenode;

        if (setupnode.item != null) {
            setupnode.item.parent = parent;
        }

        for (int i = 0; i < setupnode.children.size(); ++i) {
            tablenode.children.add(new Cmenu_TTI());
            sum += SyncTrees((Cmenu_TTI) tablenode.children.get(i), setupnode.children.get(i), tablenode);
        }

        return sum;
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
        TableNode node = (TableNode) table_node.item;

        if (this.m_bAutoHideEmpty) {
            menues.SetShowField(obj.nativePointer, node.item != null);

            if (node.item != null) {
                this.m_callbacks.SetupLineInTable(node, obj);
            }
        } else {
            this.m_callbacks.SetupLineInTable(node, obj);
        }
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        TableNode node = (TableNode) table_node.item;

        if (this.m_bAutoHideEmpty) {
            menues.SetShowField(obj.nativePointer, node.item != null);

            if (node.item != null) {
                this.m_callbacks.SetupLineInTable(node, obj);
            }
        } else {
            this.m_callbacks.SetupLineInTable(node, obj);
        }
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
        TableNode node = (TableNode) table_node.item;

        if (this.m_bAutoHideEmpty) {
            menues.SetShowField(obj.nativePointer, node.item != null);

            if (node.item != null) {
                this.m_callbacks.SetupLineInTable(node, obj);
            }
        } else {
            this.m_callbacks.SetupLineInTable(node, obj);
        }
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        TableNode node = (TableNode) table_node.item;

        if (this.m_bAutoHideEmpty) {
            menues.SetShowField(obj.nativePointer, node.item != null);

            if (node.item != null) {
                this.m_callbacks.SetupLineInTable(3, node, obj);
            }
        } else {
            this.m_callbacks.SetupLineInTable(3, node, obj);
        }
    }

    void OnRangerScroll(long _menu, MENU_ranger scroller) {
        if (this.buzzy_OnRangerScroll) {
            return;
        }

        if (scroller.current_value == ((TableNode) this.m_top.item).line) {
            return;
        }

        this.buzzy_OnRangerScroll = true;
        this.m_iCurrentTopLine = scroller.current_value;
        ChangeTop(scroller.current_value);
        menues.RedrawTable(this.m_menu, this.m_table);
        this.buzzy_OnRangerScroll = false;
    }

    Cmenu_TTI FindItemByLine(Cmenu_TTI node, int line) {
        TableNode localitem = (TableNode) node.item;

        if (localitem.line == line) {
            return node;
        }

        if (node.showCH) {
            for (int i = 0; i < node.children.size(); ++i) {
                Cmenu_TTI child = (Cmenu_TTI) node.children.get(i);
                Cmenu_TTI result = FindItemByLine(child, line);

                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int GetLastID() {
        return this.m_iLastUserID;
    }

    void CheckLastID(long _group) {
        if ((!(this.m_bGetLastID)) && (!(this.m_bSelect))) {
            return;
        }

        for (int i = 0; i < this.m_items.size(); ++i) {
            ItemInfo iteminfo = this.m_items.get(i);
            long control = menues.FindFieldInGroup(this.m_menu, _group, iteminfo.realname);

            if (menues.GetStoredState(this.m_menu, control) == 0L) {
                continue;
            }

            this.m_iLastUserID = iteminfo.userid;
            this.m_iLastControl = control;

            return;
        }
    }

    void CheckDeselect() {
        if (!(this.m_bSelect)) {
            return;
        }

        if ((this.m_SelectNode == null) || (this.m_iSelectedControl == 0L)) {
            return;
        }

        this.m_SelectCbs.Deselect(this.m_SelectNode, this.m_iSelectedID, this.m_iSelectedControl);
    }

    void CheckSelect(TableNode node, int id, long control) {
        wheelControlSelected();

        if (!(this.m_bSelect)) {
            return;
        }

        if ((node == null) || (node.line == -1)) {
            return;
        }

        this.m_SelectNode = node;
        this.m_iSelectedID = id;
        this.m_iSelectedControl = control;

        if (control != 0L) {
            this.m_SelectCbs.Select(this.m_SelectNode, this.m_iSelectedID, this.m_iSelectedControl);
        }
    }

    void OnKeyOutUp(long _menu, MENUText_field parent) {
        OnKeyOutUp();
    }

    void OnKeyOutUp(long _menu, MENUsimplebutton_field parent) {
        OnKeyOutUp();
    }

    void OnKeyOutUp(long _menu, MENUbutton_field parent) {
        OnKeyOutUp();
    }

    void OnKeyOutUp() {
        if (this.m_ranger == null) {
            return;
        }

        if (this.m_ranger.current_value <= this.m_ranger.min_value) {
            return;
        }

        this.m_ranger.current_value -= 1;
        this.m_iCurrentTopLine = this.m_ranger.current_value;
        ChangeTop(this.m_ranger.current_value);
        menues.UpdateField(this.m_ranger);
        RedrawTable();
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void ControlsMouseWheel(int value) {
        if (this.m_ranger == null) {
            return;
        }

        this.m_ranger.current_value -= value;

        if (this.m_ranger.current_value > this.m_ranger.max_value) {
            this.m_ranger.current_value = this.m_ranger.max_value;
        }

        if (this.m_ranger.current_value < this.m_ranger.min_value) {
            this.m_ranger.current_value = this.m_ranger.min_value;
        }

        menues.UpdateField(this.m_ranger);
        RedrawTable();
    }

    void OnKeyOutDown(long _menu, MENUText_field parent) {
        OnKeyOutDown();
    }

    void OnKeyOutDown(long _menu, MENUsimplebutton_field parent) {
        OnKeyOutDown();
    }

    void OnKeyOutDown(long _menu, MENUbutton_field parent) {
        OnKeyOutDown();
    }

    void OnKeyOutDown() {
        if (this.m_ranger == null) {
            return;
        }

        if (this.m_ranger.current_value >= this.m_ranger.max_value) {
            return;
        }

        this.m_ranger.current_value += 1;
        this.m_iCurrentTopLine = this.m_ranger.current_value;
        ChangeTop(this.m_ranger.current_value);
        menues.UpdateField(this.m_ranger);
        RedrawTable();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int sliderPosition() {
        return this.m_ranger.current_value;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param _group
     */
    public void OnRadioPress(long _menu, long _group) {
        Cmenu_TTI tablenode = menues.GetXMLDataOnGroup(_menu, _group);

        CheckDeselect();
        CheckLastID(_group);
        CheckSelect((TableNode) tablenode.item, this.m_iLastUserID, this.m_iLastControl);
        this.m_callbacks.OnEvent(2L, (TableNode) tablenode.item, _group, _menu);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param _group
     */
    public void OnButtonPress(long _menu, long _group) {
        Cmenu_TTI tablenode = menues.GetXMLDataOnGroup(_menu, _group);

        CheckDeselect();
        CheckLastID(_group);
        CheckSelect((TableNode) tablenode.item, this.m_iLastUserID, this.m_iLastControl);
        this.m_callbacks.OnEvent(4L, (TableNode) tablenode.item, _group, _menu);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param _group
     */
    public void OnMouseOver(long _menu, long _group) {
        Cmenu_TTI tablenode = menues.GetXMLDataOnGroup(_menu, _group);

        CheckLastID(_group);
        this.m_callbacks.OnEvent(3L, (TableNode) tablenode.item, _group, _menu);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param _group
     */
    public void OnMouseInside(long _menu, long _group) {
        Cmenu_TTI tablenode = menues.GetXMLDataOnGroup(_menu, _group);

        CheckLastID(_group);
        this.m_callbacks.OnEvent(6L, (TableNode) tablenode.item, _group, _menu);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param _group
     */
    public void OnMouseOutside(long _menu, long _group) {
        Cmenu_TTI tablenode = menues.GetXMLDataOnGroup(_menu, _group);

        CheckLastID(_group);
        this.m_callbacks.OnEvent(5L, (TableNode) tablenode.item, _group, _menu);
    }

    @SuppressWarnings("unchecked")
    void SortNode(Cmenu_TTI node, PrivateComparator comparator) {
        Collections.sort(node.children, comparator);

        for (int i = 0; i < node.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI) node.children.get(i);

            SortNode(child, comparator);
        }
    }

    /**
     * Interface description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ
     */
    public static abstract interface EraseVisitor {

        /**
         * Method description
         *
         *
         * @param paramTableNode
         *
         * @return
         */
        public abstract boolean VisitNode(TableNode paramTableNode);
    }


    /**
     * Interface description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ
     */
    public static abstract interface TableAnimation {

        /**
         * Method description
         *
         *
         * @param paramTableNode
         * @param paramBoolean
         */
        public abstract void OnNodeChange(TableNode paramTableNode, boolean paramBoolean);

        /**
         * Method description
         *
         */
        public abstract void OnFinishAnimation();
    }


    /**
     * Interface description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ
     */
    public static abstract interface TableParentVisitor {

        /**
         * Method description
         *
         *
         * @param paramTableNode
         */
        public abstract void VisitPreChildren(TableNode paramTableNode);

        /**
         * Method description
         *
         *
         * @param paramTableNode1
         * @param paramTableNode2
         */
        public abstract void VisitNodeWithParent(TableNode paramTableNode1, TableNode paramTableNode2);
    }


    /**
     * Interface description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ
     */
    public static abstract interface TableVisitor {

        /**
         * Method description
         *
         *
         * @param paramTableNode
         */
        public abstract void VisitNode(TableNode paramTableNode);
    }


    static class ItemInfo {
        int xshift;
        int type;
        int userid;
        boolean uniquenaming;
        String name;
        String realname;

        ItemInfo(int _type, String _name, int _userid, int _xshift, boolean _uniquenaming) {
            this.type = _type;
            this.name = _name;
            this.userid = _userid;
            this.xshift = _xshift;
            this.uniquenaming = _uniquenaming;
        }
    }


    class MakeAnimList implements Table.TableVisitor {

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitNode(TableNode node) {
            if ((node.line != -1 ^ Table.this.m_isopening)) {
                Table.this.m_AnimList.add(node);
            }

            if (node.self.toshow != Table.this.m_isopening) {
                return;
            }

            node.self.toshow = (!(node.self.toshow));
            menues.UpdateData(node.self);
        }
    }


    static class PrivateComparator implements Comparator<Object> {
        Comparator<Object> cmp;

        /**
         * Constructs ...
         *
         *
         * @param _cmp
         */
        public PrivateComparator(Comparator<Object> _cmp) {
            this.cmp = _cmp;
        }

        /**
         * Method description
         *
         *
         * @param o1
         * @param o2
         *
         * @return
         */
        @Override
        public int compare(Object o1, Object o2) {
            Cmenu_TTI m1 = (Cmenu_TTI) o1;
            Cmenu_TTI m2 = (Cmenu_TTI) o2;
            TableNode n1 = (TableNode) m1.item;
            TableNode n2 = (TableNode) m2.item;

            if ((n1.item == null) && (n2.item == null)) {
                return 0;
            }

            if (n1.item == null) {
                return 1;
            }

            if (n2.item == null) {
                return -1;
            }

            return this.cmp.compare(n1.item, n2.item);
        }
    }


    static class TableTree {
        TableNode item;
        Vector<TableTree> children;
        boolean showch;

        /**
         * Constructs ...
         *
         */
        public TableTree() {
            this.children = new Vector<TableTree>();
        }
    }
}


//~ Formatted in DD Std on 13/08/25
