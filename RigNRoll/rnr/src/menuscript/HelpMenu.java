/*
 * @(#)HelpMenu.java   13/08/26
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


package rnr.src.menuscript;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.BaseMenu;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.menuscript.table.Table;
import rnr.src.menuscript.tablewrapper.TableData;
import rnr.src.menuscript.tablewrapper.TableLine;
import rnr.src.menuscript.tablewrapper.TableWrapped;
import rnr.src.rnrcore.loc;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class HelpMenu extends BaseMenu implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\specmenu.xml";
    private static final String GROUP = "HELP";
    private static final String HELP_TABLE = "TABLEGROUP HELP - 27 36";
    private static final String HELP_TABLE_RANGER = "Tableranger - HELP";
    private static final String HELP_LINE = "Tablegroup - HELP";
    private static final String[] HELP_LINE_ELEMENTS = { "Key Block TITLE", "Key - BACK PICTURE", "Key",
            "Key VALUE - BACK PICTURE", "Key VALUE" };
    private static final int HELP_TITLE = 0;
    private static final int HELP_KEY_PICTURE = 1;
    private static final int HELP_KEY = 2;
    private static final int HELP_KEY_VALUE_PICTURE = 3;
    private static final int HELP_KEY_VALUE = 4;
    private static final String FILENAME = "..\\data\\config\\menu\\helpcontent.xml";
    private static final String ROOT = "group";
    private static final String NAME = "loc_name";
    private static final String LINE = "line";
    private static final String KEY = "key";
    private static final String KEY_VALUE = "loc_key_value";
    static long _menu = 0L;
    ArrayList<HelpLine> helpLines;
    Vector<KeyPair> macro;
    helpTable table;

    /**
     * Constructs ...
     *
     */
    public HelpMenu() {
        this.helpLines = new ArrayList();
        this.macro = null;
        this.table = null;
    }

    void parseLine(Node node) {
        HelpLine line = new HelpLine();

        line.isBlockTitle = false;
        line.key = node.getAttribute("key");
        line.key_value = node.getAttribute("loc_key_value");

        if (line.key == null) {
            line.key = "UNKNOWN";
        }

        if (line.key_value == null) {
            line.key_value = "UNKNOWN";
        }

        this.helpLines.add(line);
    }

    void parseBlock(Node node) {
        HelpLine group = new HelpLine();

        group.isBlockTitle = true;
        group.block_title = node.getAttribute("loc_name");

        if (group.block_title == null) {
            group.block_title = "UNKNOWN";
        }

        this.helpLines.add(group);

        NodeList line = node.getNamedChildren("line");

        if (line != null) {
            for (int i = 0; i < line.size(); ++i) {
                parseLine(line.get(i));
            }
        }
    }

    void FillHelp() {
        this.helpLines = new ArrayList();

        Node top = XmlUtils.parse("..\\data\\config\\menu\\helpcontent.xml");

        if (top != null) {
            NodeList block = top.getNamedChildren("group");

            if (block != null) {
                for (int i = 0; i < block.size(); ++i) {
                    parseBlock(block.get(i));
                }
            }
        }

        for (HelpLine line : this.helpLines) {
            if (line.isBlockTitle) {
                line.block_title = loc.getMENUString(line.block_title);
            } else {
                KeyPair[] _macro = new KeyPair[this.macro.size()];
                int i = 0;

                for (KeyPair key : this.macro) {
                    _macro[i] = key;
                    ++i;
                }

                line.key = MacroKit.Parse(loc.getMENUString(line.key), _macro);
                line.key_value = loc.getMENUString(line.key_value);
            }
        }
    }

    ArrayList<HelpLine> reciveHelp() {
        return this.helpLines;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void restartMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @param __menu
     */
    @Override
    public void InitMenu(long __menu) {
        _menu = __menu;
        menues.InitXml(_menu, "..\\data\\config\\menu\\specmenu.xml", "HELP");
        JavaEvents.SendEvent(9, 0, this);
        FillHelp();
        this.table = new helpTable(_menu);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        menues.WindowSet_ShowCursor(_menu, true);
        menues.SetStopWorld(_menu, true);

        long buttonOK = menues.FindFieldInMenu(_menu, "BUTTON - OK");

        if (buttonOK != 0L) {
            Object field = menues.ConvertMenuFields(buttonOK);

            if (field != null) {
                menues.SetScriptOnControl(_menu, field, this, "OnOk", 4L);
                menues.setfocuscontrolonmenu(_menu, buttonOK);
            }
        }

        this.table.afterInit();
        menues.setShowMenu(_menu, true);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnOk(long _menu, MENUsimplebutton_field button) {
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    /**
     * Method description
     *
     *
     * @param __menu
     */
    @Override
    public void exitMenu(long __menu) {
        this.table.deinit();
        _menu = 0L;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static long CreateMenu() {
        if (_menu == 0L) {
            return menues.createSimpleMenu(new HelpMenu(), 240000.0D, "ESC", 1600, 1200, 1600, 1200, 0, 0,
                                           "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        }

        menues.CallMenuCallBack_ExitMenu(_menu);

        return 0L;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return "helpMENU";
    }

    static class HelpLine extends TableLine {
        String key;
        String key_value;
        String block_title;
        boolean isBlockTitle;
    }


    class helpTable extends TableWrapped {
        helpTable(long paramLong) {
            super(paramLong, 0, false, "..\\data\\config\\menu\\specmenu.xml", "TABLEGROUP HELP - 27 36",
                  "Tableranger - HELP", "Tablegroup - HELP", HelpMenu.HELP_LINE_ELEMENTS, null, null);
        }

        /**
         * Method description
         *
         */
        @Override
        public void updateTable() {
            super.updateTable();
        }

        /**
         * Method description
         *
         *
         * @param table
         */
        public void enterFocus(Table table) {}

        /**
         * Method description
         *
         *
         * @param table
         */
        public void leaveFocus(Table table) {}

        protected void deinit() {
            this.table.deinit();
        }

        @Override
        protected void reciveTableData() {
            this.TABLE_DATA.all_lines.addAll(HelpMenu.this.reciveHelp());
        }

        /**
         * Method description
         *
         *
         * @param button
         * @param position
         * @param table_node
         */
        @Override
        public void SetupLineInTable(long button, int position, TableLine table_node) {
            HelpMenu.HelpLine line = (HelpMenu.HelpLine) table_node;

            switch (position) {
             case 0 :
                 if (line.isBlockTitle) {
                     menues.SetShowField(button, true);
                     menues.SetFieldText(button, line.block_title);
                 } else {
                     menues.SetShowField(button, false);
                 }

                 break;

             case 1 :
                 if (line.isBlockTitle) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);
                 }

                 break;

             case 2 :
                 if (line.isBlockTitle) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);
                     menues.SetFieldText(button, line.key);
                 }

                 break;

             case 3 :
                 if (line.isBlockTitle) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);
                 }

                 break;

             case 4 :
                 if (line.isBlockTitle) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);
                     menues.SetFieldText(button, line.key_value);
                 }
            }

            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        /**
         * Method description
         *
         *
         * @param linedata
         */
        @Override
        public void updateSelectedInfo(TableLine linedata) {}

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onSort(long _menu, MENUsimplebutton_field button) {}

        /**
         * Method description
         *
         *
         * @param paramLong
         * @param paramMENUsimplebutton_field
         */
        @Override
        public void onSort(long paramLong,
                           rnr.src.menuscript.tablewrapper.MENUsimplebutton_field paramMENUsimplebutton_field) {

            // TODO Auto-generated method stub
        }

        /**
         * Method description
         *
         *
         * @param paramLong
         * @param paramMENUsimplebutton_field
         */
        @Override
        public void onSort(long paramLong,
                           rnr.src.menuscript.tablewrapper.MENUsimplebutton_field paramMENUsimplebutton_field) {

            // TODO Auto-generated method stub
        }

        /**
         * Method description
         *
         *
         * @param paramLong
         * @param paramMENUsimplebutton_field
         */
        @Override
        public void onSort(long paramLong,
                           rnr.src.menuscript.tablewrapper.MENUsimplebutton_field paramMENUsimplebutton_field) {

            // TODO Auto-generated method stub
        }

        /**
         * Method description
         *
         *
         * @param paramLong
         * @param paramMENUsimplebutton_field
         */
        @Override
        public void onSort(long paramLong,
                           rnr.src.menuscript.tablewrapper.MENUsimplebutton_field paramMENUsimplebutton_field) {

            // TODO Auto-generated method stub
        }

        /**
         * Method description
         *
         *
         * @param paramLong
         * @param paramMENUsimplebutton_field
         */
        @Override
        public void onSort(long paramLong,
                           rnr.src.menuscript.tablewrapper.MENUsimplebutton_field paramMENUsimplebutton_field) {

            // TODO Auto-generated method stub
        }
    }
}


//~ Formatted in DD Std on 13/08/26
