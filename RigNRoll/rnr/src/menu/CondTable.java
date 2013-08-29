/*
 * @(#)CondTable.java   13/08/25
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

import rnr.src.menuscript.Converts;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class CondTable implements TableCallbacks, Table.TableAnimation {

    /** Field description */
    public static final int OPENGROUP = 0;

    /** Field description */
    public static final int TEXT = 1;

    /** Field description */
    public static final int TEXTGLOW = 2;

    /** Field description */
    public static final int CONDITION_GREEN = 3;

    /** Field description */
    public static final int CONDITION_YELLOW = 4;

    /** Field description */
    public static final int CONDITION_RED = 5;

    /** Field description */
    public static final int STUB = 6;

    /** Field description */
    public static final int PRICE = 7;

    /** Field description */
    public static final int AUTH = 8;

    /** Field description */
    public static final int CHECK = 9;

    /** Field description */
    public static final int SIZE = 10;

    /** Field description */
    public static double OPEN_DUR = 0.5D;

    /** Field description */
    public static final int EVENT_CHECK = 100;

    /** Field description */
    public static final int EVENT_UNCHECK = 101;

    /** Field description */
    public static final int EVENT_SELECT = 102;
    static final boolean OPEN = true;
    static final boolean CLOSED = false;
    boolean m_bAnimating = false;
    int m_iSelectedLine = 0;
    int m_iWasSelectedLine = 0;
    FixupTraverse m_FixupTraverse = new FixupTraverse();
    FixupChecked m_FixupChecked = new FixupChecked();
    IsTypeFullCheck m_istypefullcheck = new IsTypeFullCheck();
    TypeCheck m_typecheck = new TypeCheck();
    String auth_text = null;
    String price_text = null;
    CountingTraverse m_Counting = new CountingTraverse();
    IsSubTreeFullCheck m_fullcheck = new IsSubTreeFullCheck();
    SubTreeCheck m_SubTreeChecker = new SubTreeCheck();
    UpPass m_UpPass = new UpPass();
    boolean m_bGlowEnable = false;
    Common common;
    boolean[][] m_aArrowStates;
    int m_iAnimLine;
    MENU_ranger m_Ranger;
    SelectCb m_cbs;
    boolean m_bHasChecked;
    Table m_Table;
    menues m_this;
    Vector[] m_aNames;

    /**
     * Constructs ...
     *
     *
     * @param common
     * @param name
     */
    public CondTable(Common common, String name) {
        this.m_Table = new Table(common.GetMenu(), name);
        this.m_aNames = new Vector[10];

        for (int i = 0; i < 10; ++i) {
            this.m_aNames[i] = new Vector(3);
        }

        this.common = common;
    }

    /**
     * Method description
     *
     *
     * @param visitor
     */
    public void Traverse(Table.TableVisitor visitor) {
        this.m_Table.Traverse(visitor);
    }

    static boolean HasLevel(int ID) {
        return (ID <= 6);
    }

    /**
     * Method description
     *
     */
    public void DeInit() {
        if (this.m_Table != null) {
            this.m_Table.DeInit();
        }
    }

    int Encode(int type, int level) {
        return (level * 100 + type);
    }

    int DecodeLevel(int id) {
        return (id / 100);
    }

    int DecodeType(int id) {
        return (id % 100);
    }

    void AddName(int type, int level, String name) {
        Vector v = this.m_aNames[type];

        if (v.size() <= level) {
            v.setSize(level + 1);
        }

        v.set(level, name);
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param level
     * @param name
     */
    public void AddControl(int type, int level, String name) {
        AddName(type, level, name);

        switch (type) {
         case 0 :
             this.m_Table.AddSimpleButton(name, Encode(type, level));

             break;

         case 1 :
             this.m_Table.AddRadioButton(name, Encode(type, level));

             break;

         case 2 :
             this.m_Table.AddTextField(name, Encode(type, level));
             this.m_bGlowEnable = true;

             break;

         case 7 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 8 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 9 :
             this.m_Table.AddRadioButton(name, Encode(type, level));
             this.m_bHasChecked = true;

             break;

         case 3 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 4 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 5 :
             this.m_Table.AddTextField(name, Encode(type, level));
         case 6 :
        }
    }

    /**
     * Method description
     *
     */
    public void Fixup() {
        this.m_Table.TraverseWithParent(new FixupTraverse());
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param item
     *
     * @return
     */
    public TableNode AddItem(TableNode node, Item item) {
        return this.m_Table.AddItem(node, item, node == null);
    }

    /**
     * Method description
     *
     *
     * @param vertsize
     * @param linenum
     * @param xmlfile
     * @param controlgroup
     * @param parent
     * @param cb
     */
    public void Setup(int vertsize, int linenum, String xmlfile, String controlgroup, String parent, SelectCb cb) {
        this.m_cbs = cb;
        this.m_Table.AddEvent(4);
        this.m_Table.AddEvent(2);
        this.m_Table.AddEvent(3);
        this.m_Table.Setup(vertsize, linenum, xmlfile, controlgroup, parent, this, 1);
        Fixup();
        this.m_aArrowStates = new boolean[linenum][];

        for (int i = 0; i < this.m_aArrowStates.length; ++i) {
            this.m_aArrowStates[i] = new boolean[3];

            for (int i1 = 0; i1 < 3; ++i1) {
                this.m_aArrowStates[i][i1] = true;
            }
        }
    }

    /**
     * Method description
     *
     */
    public void RefillTree() {
        this.m_Table.RefillTree();
        Fixup();
    }

    /**
     * Method description
     *
     */
    public void StartGlow() {
        this.m_Table.Traverse(new StartGlowTraverse(), 0);
    }

    /**
     * Method description
     *
     *
     * @param ranger
     */
    public void AttachRanger(MENU_ranger ranger) {
        this.m_Table.AttachRanger(ranger);
        this.m_Ranger = ranger;
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param text
     */
    @Override
    public void SetupLineInTable(TableNode node, MENUText_field text) {
        int level = DecodeLevel(text.userid);
        int type = DecodeType(text.userid);

        if (((HasLevel(type)) && (level != node.depth)) || (node.item == null)) {
            menues.SetShowField(text.nativePointer, false);

            return;
        }

        menues.SetShowField(text.nativePointer, true);

        Item item = (Item) node.item;

        switch (type) {
         case 2 :
             text.text = "" + item.name;

             break;

         case 7 :
             if (this.price_text == null) {
                 this.price_text = text.text;
             }

             if (item.price > 0) {
                 KeyPair[] macro = { new KeyPair("VALUE", Converts.ConvertNumeric(item.price)) };

                 text.text = MacroKit.Parse(this.price_text, macro);

                 return;
             }

             text.text = "-";

             break;

         case 8 :
             if (this.auth_text == null) {
                 this.auth_text = text.text;
             }

             if (item.auth == 0) {
                 text.text = "";

                 return;
             }

             KeyPair[] macro = { new KeyPair("VALUE", Converts.ConvertNumeric(item.auth)) };

             text.text = MacroKit.Parse(this.auth_text, macro);

             break;

         case 3 :
             if (item.condition == 0) {
                 return;
             }

             menues.SetShowField(text.nativePointer, false);

             break;

         case 4 :
             if (item.condition == 1) {
                 return;
             }

             menues.SetShowField(text.nativePointer, false);

             break;

         case 5 :
             if (item.condition == 2) {
                 return;
             }

             menues.SetShowField(text.nativePointer, false);
         case 6 :
        }
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param button
     */
    @Override
    public void SetupLineInTable(TableNode node, MENUsimplebutton_field button) {
        int level = DecodeLevel(button.userid);
        int type = DecodeType(button.userid);

        if (((HasLevel(type)) && (level != node.depth)) || (node.item == null)) {
            menues.SetShowField(button.nativePointer, false);

            return;
        }

        menues.SetShowField(button.nativePointer, true);

        switch (type) {
         case 0 :
             if (node.self.children.size() != 0) {
                 return;
             }

             menues.SetShowField(button.nativePointer, false);
        }
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param radio
     */
    @Override
    public void SetupLineInTable(TableNode node, MENUbutton_field radio) {
        int level = DecodeLevel(radio.userid);
        int type = DecodeType(radio.userid);

        if (((HasLevel(type)) && (level != node.depth)) || (node.item == null)) {
            menues.SetShowField(radio.nativePointer, false);

            return;
        }

        menues.SetShowField(radio.nativePointer, true);

        Item item = (Item) node.item;

        if (type == 1) {
            radio.text = "" + item.name;
            menues.SetFieldState(radio.nativePointer, (this.m_iSelectedLine == node.line)
                    ? 1
                    : 0);
        }

        if (type == 9) {
            menues.SetShowField(radio.nativePointer, item.condition != 0);
            menues.SetFieldState(radio.nativePointer, (node.checked)
                    ? 1
                    : 0);
        }
    }

    void RotateButtonTree(TableNode node, boolean direction, double velocity) {
        menues.SetUVRotationOnGroupTree(this.common.GetMenu(), node.group, (String) this.m_aNames[0].get(node.depth),
                                        false, 0, velocity, ((direction)
                ? 1
                : -1) * 90);
        menues.SetUVRotationOnGroupTree(this.common.GetMenu(), node.group, (String) this.m_aNames[0].get(node.depth),
                                        true, 0, velocity, ((direction)
                ? 1
                : -1) * 90);
        menues.SetUVRotationOnGroupTree(this.common.GetMenu(), node.group, (String) this.m_aNames[0].get(node.depth),
                                        true, 1, velocity, ((direction)
                ? 1
                : -1) * 90);
    }

    void RotateButtonGroup(TableNode node, boolean direction, double velocity) {}

    void OnButtonPress(TableNode node, long group) {
        int type = DecodeType(this.m_Table.GetLastID());

        if (type != 0) {
            return;
        }

        this.m_iWasSelectedLine = this.m_iSelectedLine;
        this.m_iSelectedLine = node.line;

        if (!(this.m_bAnimating)) {
            this.m_bAnimating = true;
            this.m_iAnimLine = node.line;
            menues.SetIgnoreEvents(this.m_Ranger.nativePointer, true);
            RotateButtonTree(node, !(node.self.showCH), 1.0D / OPEN_DUR);
            this.m_aArrowStates[this.m_Table.GetScreenLineByLogical(node.line)][node.depth] = ((!(node.self.showCH))
                    ? true
                    : false);
            this.m_Counting.Clear();
            this.m_Table.Traverse(node, this.m_Counting, 0);

            int numch = this.m_Counting.GetCount() - 1;

            this.m_Table.ShowHideSubtree(node, 1000.0D * OPEN_DUR / numch, (this.m_bGlowEnable)
                    ? this
                    : null);

            if ((this.m_cbs != null) && (this.m_iWasSelectedLine != this.m_iSelectedLine)) {
                this.m_cbs.OnSelect(102, node);
            }
        } else {
            if ((this.m_cbs != null) && (this.m_iWasSelectedLine != this.m_iSelectedLine)) {
                this.m_cbs.OnSelect(102, node);
            }

            this.m_Table.RedrawTable();
        }
    }

    void OnRadioPress(TableNode node, long group) {
        int type = DecodeType(this.m_Table.GetLastID());

        if (type == 1) {
            this.m_iWasSelectedLine = this.m_iSelectedLine;
            this.m_iSelectedLine = node.line;

            if (this.m_iWasSelectedLine != this.m_iSelectedLine) {
                if ((!(this.m_bAnimating)) && (node.self.children.size() != 0) && (!(node.self.showCH))) {
                    this.m_bAnimating = true;
                    this.m_iAnimLine = node.line;
                    menues.SetIgnoreEvents(this.m_Ranger.nativePointer, true);
                    RotateButtonTree(node, !(node.self.showCH), 1.0D / OPEN_DUR);
                    this.m_aArrowStates[this.m_Table.GetScreenLineByLogical(node.line)][node.depth] =
                        ((!(node.self.showCH))
                         ? true
                         : false);
                    this.m_Counting.Clear();
                    this.m_Table.Traverse(node, this.m_Counting, 0);

                    int numch = this.m_Counting.GetCount() - 1;

                    this.m_Table.ShowHideSubtree(node, 1000.0D * OPEN_DUR / numch, (this.m_bGlowEnable)
                            ? this
                            : null);
                } else {
                    this.m_Table.RedrawTable();
                }

                if (this.m_cbs != null) {
                    this.m_cbs.OnSelect(102, node);
                }
            } else {
                this.m_Table.RedrawTable();
            }
        }

        if (type == 9) {
            FullCheck(node);

            if (this.m_cbs != null) {
                this.m_cbs.OnSelect((node.checked)
                                    ? 100
                                    : 101, node);
            }
        }
    }

    private void FullCheck(TableNode node) {
        if (node.checked == true) {
            this.m_fullcheck.Setup();
            this.m_Table.Traverse(node, this.m_fullcheck, 0);

            if (!(this.m_fullcheck.GetResult())) {
                node.checked = false;
            }
        }

        this.m_Table.Check(node);
        this.m_SubTreeChecker.Setup(node.checked);
        this.m_Table.Traverse(node, this.m_SubTreeChecker, 0);
        this.m_Table.TraverseUp(this.m_UpPass, node);
        this.m_Table.TraverseWithParent(this.m_FixupTraverse);
        this.m_Table.RedrawTable();
    }

    /**
     * Method description
     *
     */
    public void FixUp() {
        this.m_Table.TraverseWithParent(this.m_FixupTraverse);
    }

    void OnMouseOver(TableNode node, long group) {
        if (!(this.m_bGlowEnable)) {
            return;
        }

        int type = DecodeType(this.m_Table.GetLastID());
        int level = DecodeLevel(this.m_Table.GetLastID());

        if ((level != node.depth) || (type != 1)) {
            return;
        }

        menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), group, (String) this.m_aNames[1].get(level), 0.5D,
                                           1.0D, "type1");
        menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), group, (String) this.m_aNames[2].get(level), 0.5D,
                                           1.0D, "type2");
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
    public void OnEvent(long event, TableNode node, long group, long _menu) {
        switch ((int) event) {
         case 4 :
             OnButtonPress(node, group);

             break;

         case 2 :
             OnRadioPress(node, group);

             break;

         case 3 :
             OnMouseOver(node, group);
        }
    }

    /**
     * Method description
     *
     *
     * @param fixup
     */
    public void RedrawTable(boolean fixup) {
        if (fixup) {
            Fixup();
        }

        this.m_Table.RedrawTable();
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param direction
     */
    @Override
    public void OnNodeChange(TableNode node, boolean direction) {
        if (!(direction)) {
            return;
        }

        int level = node.depth;

        menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), node.group, (String) this.m_aNames[1].get(level),
                                           0.5D, 1.0D, "type1");
        menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), node.group, (String) this.m_aNames[2].get(level),
                                           0.5D, 1.0D, "type2");
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    public void VisitNode(TableNode node) {}

    /**
     * Method description
     *
     */
    @Override
    public void OnFinishAnimation() {
        this.m_bAnimating = false;
        this.m_iAnimLine = -1;
        menues.SetIgnoreEvents(this.m_Ranger.nativePointer, false);
    }

    /**
     * Method description
     *
     */
    public void AfterInitMenu() {
        for (int depth = 0; depth < 3; ++depth) {
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0),
                                            (String) this.m_aNames[0].get(depth), false, 0, 100.0D, 0.0D);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0),
                                            (String) this.m_aNames[0].get(depth), true, 0, 100.0D, 0.0D);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0),
                                            (String) this.m_aNames[0].get(depth), true, 1, 100.0D, 0.0D);
        }

        this.m_Table.Traverse(new InitAnimTraverse());

        if (this.m_bGlowEnable) {
            StartGlow();
        }

        for (int depth = 0; depth < 3; ++depth) {
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0),
                                            (String) this.m_aNames[0].get(depth), false, 0, 1000.0D, 90.0D);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0),
                                            (String) this.m_aNames[0].get(depth), true, 0, 1000.0D, 90.0D);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0),
                                            (String) this.m_aNames[0].get(depth), true, 1, 1000.0D, 90.0D);
        }
    }

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

    class CountingTraverse implements Table.TableVisitor {
        int count;

        CountingTraverse() {
            this.count = 0;
        }

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitNode(TableNode node) {
            this.count += 1;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int GetCount() {
            return this.count;
        }

        /**
         * Method description
         *
         */
        public void Clear() {
            this.count = 0;
        }
    }


    class FixupChecked implements Table.TableParentVisitor {

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitPreChildren(TableNode node) {
            if (node.self.children.size() == 0) {
                return;
            }

            node.checked = false;
        }

        /**
         * Method description
         *
         *
         * @param node
         * @param parent
         */
        @Override
        public void VisitNodeWithParent(TableNode node, TableNode parent) {
            if (parent == null) {
                return;
            }

            if (node.checked) {
                parent.checked = true;
            }
        }
    }


    class FixupTraverse implements Table.TableParentVisitor {

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitPreChildren(TableNode node) {
            Item item = (Item) node.item;

            if (node.self.children.size() == 0) {
                item.auth = ((node.checked)
                             ? item.price
                             : 0);

                return;
            }

            item.condition = 0;
            item.price = 0;
            item.auth = 0;
        }

        /**
         * Method description
         *
         *
         * @param node
         * @param parent
         */
        @Override
        public void VisitNodeWithParent(TableNode node, TableNode parent) {
            if (parent == null) {
                return;
            }

            Item parentitem = (Item) parent.item;
            Item item = (Item) node.item;

            if (parentitem.condition < item.condition) {
                parentitem.condition = item.condition;
            }

            parentitem.price += item.price;
            parentitem.auth += item.auth;
        }
    }


    class InitAnimTraverse implements Table.TableVisitor {
        int start;

        InitAnimTraverse() {
            this.start = 0;
        }

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitNode(TableNode node) {
            if ((node.line == 0) || (node.line == -1)) {
                return;
            }

            long gr;

            if (node.line >= CondTable.this.m_Table.GetLineNum()) {
                Long group = (Long) CondTable.this.m_Table.GetFakeGroups().get(node.line
                                 - CondTable.this.m_Table.GetLineNum());

                gr = group.longValue();
            } else {
                gr = CondTable.this.m_Table.GetGroupByLine(node.line);
            }

            for (int depth = 0; depth < 3; ++depth) {
                String name = (String) CondTable.this.m_aNames[0].get(depth);

                menues.CopyUVRotationOnGroupTree(CondTable.this.common.GetMenu(),
                                                 CondTable.this.m_Table.GetGroupByLine(0), name, false, 0, gr, false,
                                                 0);
                menues.CopyUVRotationOnGroupTree(CondTable.this.common.GetMenu(),
                                                 CondTable.this.m_Table.GetGroupByLine(0), name, true, 0, gr, true, 0);
                menues.CopyUVRotationOnGroupTree(CondTable.this.common.GetMenu(),
                                                 CondTable.this.m_Table.GetGroupByLine(0), name, true, 1, gr, true, 1);
            }
        }
    }


    private class IsSubTreeFullCheck implements Table.TableVisitor {
        boolean isfull;

        void Setup() {
            this.isfull = true;
        }

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitNode(TableNode node) {
            if (node.checked != this.isfull) {
                this.isfull = false;
            }
        }

        boolean GetResult() {
            return this.isfull;
        }
    }


    private class IsTypeFullCheck implements Table.TableVisitor {
        boolean isfull;
        int cond;

        void Setup(int condition) {
            this.isfull = true;
            this.cond = condition;
        }

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitNode(TableNode node) {
            if ((node.self.children.size() != 0) || (!(this.isfull))) {
                return;
            }

            Item item = (Item) node.item;

            if ((item.condition == this.cond) && (!(node.checked))) {
                this.isfull = false;
            }
        }

        boolean GetResult() {
            return this.isfull;
        }
    }


    class StartGlowTraverse implements Table.TableVisitor {

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitNode(TableNode node) {
            if (node.line == -1) {
                return;
            }

            String textname = (String) CondTable.this.m_aNames[1].get(node.depth);
            String glowname = (String) CondTable.this.m_aNames[2].get(node.depth);

            menues.SetAlfaAnimationOnGroupTree(CondTable.this.common.GetMenu(), node.group, textname, 0.5D, 1.0D,
                                               "type1");
            menues.SetAlfaAnimationOnGroupTree(CondTable.this.common.GetMenu(), node.group, glowname, 0.5D, 1.0D,
                                               "type2");
        }
    }


    private class SubTreeCheck implements Table.TableVisitor {
        boolean check;

        void Setup(boolean _check) {
            this.check = _check;
        }

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitNode(TableNode node) {
            if (node.checked != this.check) {
                CondTable.this.m_Table.Check(node);
            }
        }
    }


    private class TypeCheck implements Table.TableVisitor {
        boolean tocheck;
        int cond;

        void Setup(int condition, boolean _tocheck) {
            this.tocheck = _tocheck;
            this.cond = condition;
        }

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitNode(TableNode node) {
            if (node.self.children.size() != 0) {
                return;
            }

            Item item = (Item) node.item;

            if (item.condition == this.cond) {
                node.checked = this.tocheck;
            }
        }
    }


    private class UpPass implements Table.TableVisitor {

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitNode(TableNode node) {
            boolean checkIt = true;

            for (int i = 0; i < node.self.children.size(); ++i) {
                TableNode child = (TableNode) ((Cmenu_TTI) node.self.children.get(i)).item;
                Item item = (Item) child.item;

                if ((item.condition == 0) || (child.checked)) {
                    continue;
                }

                checkIt = false;

                break;
            }

            if (checkIt) {
                if (!(node.checked)) {
                    CondTable.this.m_Table.Check(node);
                }
            } else if (node.checked) {
                CondTable.this.m_Table.Check(node);
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/25
