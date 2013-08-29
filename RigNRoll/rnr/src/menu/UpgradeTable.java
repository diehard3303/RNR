/*
 * @(#)UpgradeTable.java   13/08/26
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
import rnr.src.menuscript.STOmenues;
import rnr.src.rnrcore.loc;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class UpgradeTable implements TableCallbacks, Table.TableAnimation {

    /** Field description */
    public static final int OPENGROUP = 0;

    /** Field description */
    public static final int TEXT = 1;

    /** Field description */
    public static final int TEXTGLOW = 2;

    /** Field description */
    public static final int UPGRADE_PRICE = 4;

    /** Field description */
    public static final int UPGRADE_ALL = 5;

    /** Field description */
    public static final int UPGRADE_ALL_GRAY = 6;

    /** Field description */
    public static final int DOWNGRADE_ALL = 7;

    /** Field description */
    public static final int DOWNGRADE_ALL_GRAY = 8;

    /** Field description */
    public static final int DOWNGRADE_PRICE = 9;

    /** Field description */
    public static final int CHECK = 10;

    /** Field description */
    public static final int CHECK_GRAY = 11;

    /** Field description */
    public static final int INDICATOR_RED = 12;

    /** Field description */
    public static final int INDICATOR_GREEN = 13;

    /** Field description */
    public static final int INDICATOR_GRAY = 14;

    /** Field description */
    public static final int BORDER = 15;

    /** Field description */
    public static final int SIZE = 19;

    /** Field description */
    public static double OPEN_DUR = 0.5D;
    static final boolean OPEN = true;
    static final boolean CLOSED = false;
    UpgradeNode root = null;
    boolean m_bAnimating = false;
    int m_iAnimLine = -1;
    boolean m_bGlowEnable = false;
    STOmenues father = null;
    int m_iSelectedLine = 0;
    int m_iWasSelectedLine = 0;
    boolean bIsGreenIndicatorSizeStored = false;
    boolean bIsGrayIndicatorSizeStored = false;
    boolean bIsRedIndicatorSizeStored = false;
    float indicator_red_original_size = 0.0F;
    float indicator_green_original_size = 0.0F;
    float indicator_gray_original_size = 0.0F;
    CountingTraverse m_Counting = new CountingTraverse();
    int out_id = 0;

    /** Field description */
    public String detailed_name = null;

    /** Field description */
    public long id_details_picture = 0L;
    TextScroller scroller = null;

    /** Field description */
    public long id_details_text = 0L;

    /** Field description */
    public long id_details_text_scroller = 0L;
    String store_marked_for_purchase = null;
    String store_marked_for_return = null;
    String store_total = null;
    long id_marked_for_purchase = 0L;
    long id_marked_for_return = 0L;
    long id_total = 0L;
    long id_total_title = 0L;

    /** Field description */
    public int total_upgrade_price = 0;
    Table m_Table;
    MENU_ranger m_Ranger;
    @SuppressWarnings("rawtypes")
    Vector[] m_aNames;
    Common common;
    boolean[][] m_aArrowStates;

    /**
     * Constructs ...
     *
     *
     * @param common
     * @param name
     * @param _father
     */
    public UpgradeTable(Common common, String name, STOmenues _father) {
        this.m_Table = new Table(common.GetMenu(), name);
        this.m_aNames = new Vector[19];

        for (int i = 0; i < 19; ++i) {
            this.m_aNames[i] = new Vector<Object>(4);
        }

        this.common = common;
        this.father = _father;
    }

    int Encode(int type, int level) {
        return (level * 1000 + type);
    }

    int DecodeLevel(int id) {
        return (id / 1000);
    }

    int DecodeType(int id) {
        return (id % 1000);
    }

    @SuppressWarnings("unchecked")
    void AddName(int type, int level, String name) {
        Vector<String> v = this.m_aNames[type];

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

         case 4 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 5 :
             this.m_Table.AddSimpleButton(name, Encode(type, level));

             break;

         case 6 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 7 :
             this.m_Table.AddSimpleButton(name, Encode(type, level));

             break;

         case 8 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 9 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 10 :
             this.m_Table.AddRadioButton(name, Encode(type, level));

             break;

         case 11 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 13 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 12 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 14 :
             this.m_Table.AddTextField(name, Encode(type, level));

             break;

         case 15 :
             this.m_Table.AddTextField(name, Encode(type, level));
         case 3 :
        }
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

    boolean HasLevels(int level, int type) {
        switch (type) {
         case 0 :
             return true;

         case 1 :
             return true;

         case 2 :
             return true;

         case 4 :
             return false;

         case 5 :
             return false;

         case 6 :
             return false;

         case 7 :
             return false;

         case 8 :
             return false;

         case 9 :
             return false;

         case 10 :
             return false;

         case 11 :
             return false;

         case 13 :
             return true;

         case 12 :
             return true;

         case 14 :
             return true;

         case 15 :
             return true;

         case 3 :
        }

        return false;
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

        if (((level != node.depth) && (HasLevels(level, type))) || (node.item == null)) {
            menues.SetShowField(text.nativePointer, false);

            return;
        }

        menues.SetShowField(text.nativePointer, true);

        UpgradeItem item = (UpgradeItem) node.item;

        switch (type) {
         case 2 :
             text.text = "" + item.name;

             break;

         case 4 :
             text.text = "$" + item.upgrade_price;

             break;

         case 9 :
             text.text = "$" + item.downgrade_price;

             break;

         case 8 :
             if ((item.can_downgrade_all != true) && (node.self.children.size() != 0)) {
                 return;
             }

             menues.SetShowField(text.nativePointer, false);

             break;

         case 6 :
             if ((item.can_upgrade_all != true) && (node.self.children.size() != 0)) {
                 return;
             }

             menues.SetShowField(text.nativePointer, false);

             break;

         case 11 :
             if ((item.checked) && (item.need_one) && (node.self.children.size() == 0)) {
                 menues.SetShowField(text.nativePointer, true);
             } else {
                 menues.SetShowField(text.nativePointer, false);
             }

             menues.SetIgnoreEvents(text.nativePointer, true);
             menues.SetBlindess(text.nativePointer, true);

             break;

         case 13 :
             if (!(this.bIsGreenIndicatorSizeStored)) {
                 this.indicator_green_original_size = text.lenx;
                 this.bIsGreenIndicatorSizeStored = true;
             }

             if (item.farshness > item.original_farshness) {
                 menues.SetShowField(text.nativePointer, true);
             } else {
                 menues.SetShowField(text.nativePointer, false);
             }

             text.lenx = (int) (this.indicator_green_original_size * item.farshness);
             text.lenx = ((text.lenx <= 0)
                          ? 1
                          : text.lenx);
             menues.UpdateField(text);

             break;

         case 12 :
             if (!(this.bIsRedIndicatorSizeStored)) {
                 this.indicator_red_original_size = text.lenx;
                 this.bIsRedIndicatorSizeStored = true;
             }

             if (item.farshness < item.original_farshness) {
                 menues.SetShowField(text.nativePointer, true);
             } else {
                 menues.SetShowField(text.nativePointer, false);
             }

             text.lenx = (int) (this.indicator_red_original_size * item.original_farshness);
             text.lenx = ((text.lenx <= 0)
                          ? 1
                          : text.lenx);
             menues.UpdateField(text);

             break;

         case 14 :
             if (!(this.bIsGrayIndicatorSizeStored)) {
                 this.indicator_gray_original_size = text.lenx;
                 this.bIsGrayIndicatorSizeStored = true;
             }

             if (item.farshness < item.original_farshness) {
                 text.lenx = (int) (this.indicator_gray_original_size * item.farshness);
             } else {
                 text.lenx = (int) (this.indicator_gray_original_size * item.original_farshness);
             }

             text.lenx = ((text.lenx <= 0)
                          ? 1
                          : text.lenx);
             menues.UpdateField(text);
         case 3 :
         case 5 :
         case 7 :
         case 10 :
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

        if (((level != node.depth) && (HasLevels(level, type))) || (node.item == null)) {
            menues.SetShowField(button.nativePointer, false);

            return;
        }

        menues.SetShowField(button.nativePointer, true);

        UpgradeItem item = (UpgradeItem) node.item;

        switch (type) {
         case 0 :
             if (node.self.children.size() != 0) {
                 return;
             }

             menues.SetShowField(button.nativePointer, false);

             break;

         case 7 :
             if ((item.can_downgrade_all) && (node.self.children.size() != 0)) {
                 return;
             }

             menues.SetShowField(button.nativePointer, false);

             break;

         case 5 :
             if ((item.can_upgrade_all) && (node.self.children.size() != 0)) {
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

        if (((level != node.depth) && (HasLevels(level, type))) || (node.item == null)) {
            menues.SetShowField(radio.nativePointer, false);

            return;
        }

        menues.SetShowField(radio.nativePointer, true);

        UpgradeItem item = (UpgradeItem) node.item;

        switch (type) {
         case 10 :
             if (((item.checked) && (item.need_one) && (node.self.children.size() == 0))
                     || (node.self.children.size() != 0)) {
                 menues.SetShowField(radio.nativePointer, false);

                 return;
             }

             menues.SetFieldState(radio.nativePointer, (item.checked)
                     ? 1
                     : 0);

             break;

         case 1 :
             radio.text = "" + item.name;
             menues.SetFieldState(radio.nativePointer, (this.m_iSelectedLine == node.line)
                     ? 1
                     : 0);
        }
    }

    /**
     * Method description
     *
     */
    public void StartGlow() {
        this.m_Table.Traverse(new StartGlowTraverse(), 0);
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

    void OnButtonPress(TableNode node, long group) {
        int type = DecodeType(this.m_Table.GetLastID());

        if (type == 0) {
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

                if (this.m_iWasSelectedLine != this.m_iSelectedLine) {
                    UpgradeItem item = (UpgradeItem) node.item;

                    this.out_id = item.id;
                    JavaEvents.SendEvent(67, 4, this);
                }
            } else {
                if (this.m_iWasSelectedLine != this.m_iSelectedLine) {
                    UpgradeItem item = (UpgradeItem) node.item;

                    this.out_id = item.id;
                    JavaEvents.SendEvent(67, 4, this);
                }

                this.m_Table.RedrawTable();
            }
        }

        if (type == 5) {
            UpgradeItem item = (UpgradeItem) node.item;

            this.out_id = item.id;
            JavaEvents.SendEvent(67, 2, this);
            this.m_Table.RedrawTable();
        }

        if (type == 7) {
            UpgradeItem item = (UpgradeItem) node.item;

            this.out_id = item.id;
            JavaEvents.SendEvent(67, 3, this);
            this.m_Table.RedrawTable();
        }
    }

    void OnRadioPress(TableNode node, long group) {
        int type = DecodeType(this.m_Table.GetLastID());

        if (type == 10) {
            UpgradeItem item = (UpgradeItem) node.item;

            this.out_id = item.id;
            JavaEvents.SendEvent(67, 1, this);
            this.m_Table.RedrawTable();
        }

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

                UpgradeItem item = (UpgradeItem) node.item;

                this.out_id = item.id;
                JavaEvents.SendEvent(67, 4, this);
            } else {
                this.m_Table.RedrawTable();
            }
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

    /**
     * Method description
     *
     *
     * @param node
     * @param direction
     */
    @Override
    public void OnNodeChange(TableNode node, boolean direction) {
        if (direction) {
            int level = node.depth;

            menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), node.group, (String) this.m_aNames[1].get(level),
                                               0.5D, 1.0D, "type1");
            menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), node.group, (String) this.m_aNames[2].get(level),
                                               0.5D, 1.0D, "type2");
        }
    }

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
     *
     * @param vertsize
     * @param linenum
     * @param xmlfile
     * @param controlgroup
     * @param parent
     */
    public void Setup(int vertsize, int linenum, String xmlfile, String controlgroup, String parent) {
        this.root = new UpgradeNode();
        JavaEvents.SendEvent(67, 0, this);
        FillUpgradeTable();
        this.out_id = this.root.item.id;
        JavaEvents.SendEvent(67, 4, this);
        this.m_Table.AddEvent(4);
        this.m_Table.AddEvent(2);
        this.m_Table.AddEvent(3);
        this.m_Table.Setup(vertsize, linenum, xmlfile, controlgroup, parent, this, 1);
        this.m_aArrowStates = new boolean[linenum][];

        for (int i = 0; i < this.m_aArrowStates.length; ++i) {
            this.m_aArrowStates[i] = new boolean[4];

            for (int i1 = 0; i1 < 4; ++i1) {
                this.m_aArrowStates[i][i1] = true;
            }
        }
    }

    /**
     * Method description
     *
     */
    public void DeInit() {
        if (this.m_Table != null) {
            this.m_Table.DeInit();
        }

        if (this.scroller != null) {
            this.scroller.Deinit();
        }
    }

    /**
     * Method description
     *
     */
    public void AfteInitMenu() {
        for (int depth = 0; depth < 4; ++depth) {
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

        this.m_Table.RedrawTable();
        PrepareSummary(this.common.GetMenu());
        PrepareDetailedText(this.common.GetMenu());

        for (int depth = 0; depth < 4; ++depth) {
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

    private void fillUpgradeTable(TableNode pap, UpgradeNode node) {
        TableNode res = this.m_Table.AddItem(pap, node.item, true);

        for (int i = 0; i < node.childs.size(); ++i) {
            fillUpgradeTable(res, node.childs.get(i));
        }
    }

    /**
     * Method description
     *
     */
    public void FillUpgradeTable() {
        if (this.root != null) {
            fillUpgradeTable(null, this.root);
        }
    }

    void PrepareDetailedText(long _menu) {
        this.id_details_text = menues.FindFieldInMenu(_menu, "UA - VehicleUpgradeMONITOR - DETAILS TEXT");
        this.id_details_picture = menues.FindFieldInMenu(_menu, "UA - VehicleUpgradeMONITOR - UpgradePIC");
        this.id_details_text_scroller = menues.FindFieldInMenu(_menu, "UA - VehicleUpgradeMONITOR - tableranger");
        SetDetailedString();
        ShowDetailedPicture(false);
    }

    /**
     * Method description
     *
     *
     * @param bShow
     */
    public void ShowDetailedPicture(boolean bShow) {
        menues.SetShowField(this.id_details_picture, bShow);
    }

    /**
     * Method description
     *
     */
    public void SetDetailedString() {
        if (this.detailed_name != null) {
            MENU_ranger ranger = (MENU_ranger) menues.ConvertMenuFields(this.id_details_text_scroller);
            MENUText_field text = (MENUText_field) menues.ConvertMenuFields(this.id_details_text);

            if ((ranger != null) && (text != null)) {
                if (this.id_details_text != 0L) {
                    menues.SetShowField(this.id_details_text, true);
                }

                text.text = this.detailed_name;
                menues.UpdateField(text);

                int texh = menues.GetTextLineHeight(text.nativePointer);
                int startbase = menues.GetBaseLine(text.nativePointer);
                int linescreen = Converts.HeightToLines(text.leny, startbase, texh);
                int linecounter = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, this.detailed_name),
                                      startbase, texh);

                if (this.scroller != null) {
                    this.scroller.Deinit();
                }

                this.scroller = new TextScroller(this.common, ranger, linecounter, linescreen, texh, startbase, false,
                                                 "UA - VehicleUpgradeMONITOR - DETAILS TEXT");
                this.scroller.AddTextControl(text);
            }
        } else {
            if (this.id_details_text != 0L) {
                menues.SetShowField(this.id_details_text, false);
            }

            if (this.scroller != null) {
                this.scroller.Deinit();
            }

            this.scroller = null;
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    public void PrepareSummary(long _menu) {
        this.id_marked_for_purchase = menues.FindFieldInMenu(_menu, "Marked for purchase - VALUE");
        this.id_marked_for_return = menues.FindFieldInMenu(_menu, "Marked for return - VALUE");
        this.id_total = menues.FindFieldInMenu(_menu, "Total/Return - VALUE");
        this.id_total_title = menues.FindFieldInMenu(_menu, "Total/Return - TITLE");

        if (this.id_marked_for_purchase != 0L) {
            this.store_marked_for_purchase = menues.GetFieldText(this.id_marked_for_purchase);
        }

        if (this.id_marked_for_return != 0L) {
            this.store_marked_for_return = menues.GetFieldText(this.id_marked_for_return);
        }

        if (this.id_total != 0L) {
            this.store_total = menues.GetFieldText(this.id_total);
        }

        SetSummary(0, 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param to_install
     * @param to_uninstall
     * @param summary
     */
    public void SetSummary(int to_install, int to_uninstall, int summary) {
        if (this.id_marked_for_purchase != 0L) {
            KeyPair[] pairs = new KeyPair[1];

            pairs[0] = new KeyPair("VALUE", "" + to_install);
            menues.SetFieldText(this.id_marked_for_purchase, MacroKit.Parse(this.store_marked_for_purchase, pairs));

            if (menues.ConvertMenuFields(this.id_marked_for_purchase) != null) {
                menues.UpdateMenuField(menues.ConvertMenuFields(this.id_marked_for_purchase));
            }
        }

        if (this.id_marked_for_return != 0L) {
            KeyPair[] pairs = new KeyPair[1];

            pairs[0] = new KeyPair("VALUE", "" + to_uninstall);
            menues.SetFieldText(this.id_marked_for_return, MacroKit.Parse(this.store_marked_for_return, pairs));

            if (menues.ConvertMenuFields(this.id_marked_for_return) != null) {
                menues.UpdateMenuField(menues.ConvertMenuFields(this.id_marked_for_return));
            }
        }

        if (this.id_total != 0L) {
            KeyPair[] pairs = new KeyPair[1];

            pairs[0] = new KeyPair("MONEY", Converts.ConvertNumeric(Math.abs(summary)));
            menues.SetFieldText(this.id_total, MacroKit.Parse(this.store_total, pairs));

            if (menues.ConvertMenuFields(this.id_total) != null) {
                menues.UpdateMenuField(menues.ConvertMenuFields(this.id_total));
            }
        }

        if (this.id_total_title != 0L) {
            menues.SetFieldText(this.id_total_title, (summary >= 0)
                    ? loc.getMENUString("common\\Total")
                    : loc.getMENUString(
                        "menu_repair.xml\\MenuRepair UpgradeVEHICLE\\BACK ALL - UpgradeVEHICLE\\Total/Return - TITLE"));

            if (menues.ConvertMenuFields(this.id_total_title) != null) {
                menues.UpdateMenuField(menues.ConvertMenuFields(this.id_total_title));
            }
        }

        this.total_upgrade_price = summary;

        if (this.father != null) {
            this.father.UpdateAuth();
        }
    }

    /**
     * Method description
     *
     */
    public void OnApply() {
        JavaEvents.SendEvent(67, 5, this);
        this.m_Table.RedrawTable();
    }

    /**
     * Method description
     *
     */
    public void OnDiscard() {
        JavaEvents.SendEvent(67, 6, this);
        this.m_Table.RedrawTable();
    }

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


    class InitAnimTraverse implements Table.TableVisitor {

        /**
         * Method description
         *
         *
         * @param node
         */
        @Override
        public void VisitNode(TableNode node) {
            if (node.real_line == 0) {
                return;
            }

            long gr;

            if (node.real_line >= UpgradeTable.this.m_Table.GetLineNum()) {
                Long group = UpgradeTable.this.m_Table.GetFakeGroups().get(node.real_line
                                 - UpgradeTable.this.m_Table.GetLineNum());

                gr = group.longValue();
            } else {
                gr = UpgradeTable.this.m_Table.GetGroupByLine(node.real_line);
            }

            for (int depth = 0; depth < 4; ++depth) {
                String name = (String) UpgradeTable.this.m_aNames[0].get(depth);

                menues.CopyUVRotationOnGroupTree(UpgradeTable.this.common.GetMenu(),
                                                 UpgradeTable.this.m_Table.GetGroupByLine(0), name, false, 0, gr,
                                                 false, 0);
                menues.CopyUVRotationOnGroupTree(UpgradeTable.this.common.GetMenu(),
                                                 UpgradeTable.this.m_Table.GetGroupByLine(0), name, true, 0, gr, true,
                                                 0);
                menues.CopyUVRotationOnGroupTree(UpgradeTable.this.common.GetMenu(),
                                                 UpgradeTable.this.m_Table.GetGroupByLine(0), name, true, 1, gr, true,
                                                 1);
            }

            if (node.depth >= 1) {
                node.self.showCH = false;
            }

            menues.UpdateData(node.self);
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

            String textname = (String) UpgradeTable.this.m_aNames[1].get(node.depth);
            String glowname = (String) UpgradeTable.this.m_aNames[2].get(node.depth);

            menues.SetAlfaAnimationOnGroupTree(UpgradeTable.this.common.GetMenu(), node.group, textname, 0.5D, 1.0D,
                                               "type1");
            menues.SetAlfaAnimationOnGroupTree(UpgradeTable.this.common.GetMenu(), node.group, glowname, 0.5D, 1.0D,
                                               "type2");
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static class UpgradeNode {
        UpgradeItem item;
        Vector<UpgradeNode> childs;

        /**
         * Constructs ...
         *
         */
        public UpgradeNode() {
            this.childs = new Vector<UpgradeNode>();
            this.item = new UpgradeItem();
        }

        /**
         * Constructs ...
         *
         *
         * @param _item
         */
        public UpgradeNode(UpgradeItem _item) {
            this.childs = new Vector<UpgradeNode>();
            this.item = _item;
        }

        /**
         * Method description
         *
         *
         * @param _item
         *
         * @return
         */
        public UpgradeNode AddChild(UpgradeItem _item) {
            UpgradeNode child = new UpgradeNode(_item);

            this.childs.add(child);

            return child;
        }

        /**
         * Method description
         *
         *
         * @param i
         *
         * @return
         */
        public UpgradeNode GetChild(int i) {
            return (this.childs.elementAt(i));
        }

        /**
         * Method description
         *
         *
         * @param ch
         */
        public void AddChild(UpgradeNode ch) {
            this.childs.add(ch);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
