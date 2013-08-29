/*
 * @(#)OrganaizerPhotoAlbum.java   13/08/28
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


package rnr.src.menuscript.org;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.Cmenu_TTI;
import rnr.src.menu.Common;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.MENUEditBox;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUTruckview;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.TextScroller;
import rnr.src.menu.menues;
import rnr.src.menuscript.Converts;
import rnr.src.menuscript.table.ISelectLineListener;
import rnr.src.menuscript.table.ISetupLine;
import rnr.src.menuscript.table.Table;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.Log;
import rnr.src.rnrorg.Album;
import rnr.src.rnrorg.Album.Item;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class OrganaizerPhotoAlbum implements IOrgTab {
    private static final String XML = "..\\data\\config\\menu\\menu_com.xml";
    private static final String TEXT_FIELD_MESSAGE = "PHOTO ALBUM - PHOTO - TEXT";
    private static final String TEXT_FIELD_DATE = "PHOTO ALBUM - PHOTO - DATE";
    private static final String TEXT_FIELD_MESSAGE_SCROLLER_GROUP = "GROUP Tableranger - PHOTO ALBUM - PHOTO - TEXT";
    private static final String TEXT_FIELD_MESSAGE_SCROLLER = "Tableranger - PHOTO ALBUM - PHOTO - TEXT";
    private static final String PIC_SIMPLE = "PHOTO ALBUM - PHOTO - PIC";
    private static final String PIC_BIGRACE = "PHOTO ALBUM - PHOTO - PIC - race summary";
    OrganiserMenu parent = null;

    /** Field description */
    public sort out_photos_sort_mode = null;
    PhotoInfo current_info = null;
    TextScroller scroller = null;
    long text_message_field = 0L;
    long text_message_field_scroller_group = 0L;
    long text_message_field_scroller = 0L;
    long date_field = 0L;
    long _menu = 0L;
    long pic_simple = 0L;
    long pic_bigrace = 0L;
    String date_initial_value = null;
    Photos photos;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param parent
     */
    public OrganaizerPhotoAlbum(long _menu, OrganiserMenu parent) {
        this._menu = _menu;
        this.parent = parent;
        this.photos = new Photos(_menu, this);

        long control = menues.FindFieldInMenu(_menu, "CALL COMMUNICATOR HELP - PHOTO ALBUM");

        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
        this.photos.deinit();

        if (this.scroller != null) {
            this.scroller.Deinit();
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        this.date_field = menues.FindFieldInMenu(this._menu, "PHOTO ALBUM - PHOTO - DATE");
        this.text_message_field = menues.FindFieldInMenu(this._menu, "PHOTO ALBUM - PHOTO - TEXT");
        this.text_message_field_scroller_group = menues.FindFieldInMenu(this._menu,
                "GROUP Tableranger - PHOTO ALBUM - PHOTO - TEXT");
        this.text_message_field_scroller = menues.FindFieldInMenu(this._menu,
                "Tableranger - PHOTO ALBUM - PHOTO - TEXT");
        this.pic_simple = menues.FindFieldInMenu(this._menu, "PHOTO ALBUM - PHOTO - PIC");
        this.pic_bigrace = menues.FindFieldInMenu(this._menu, "PHOTO ALBUM - PHOTO - PIC - race summary");

        if (this.date_field != 0L) {
            menues.SetShowField(this.date_field, false);
        }

        if (this.text_message_field != 0L) {
            menues.SetShowField(this.text_message_field, false);
        }

        if (this.text_message_field_scroller_group != 0L) {
            menues.SetShowField(this.text_message_field_scroller_group, false);
        }

        if (this.pic_bigrace != 0L) {
            menues.SetShowField(this.pic_bigrace, false);
        }

        if (this.pic_simple != 0L) {
            menues.SetShowField(this.pic_simple, true);
        }

        this.photos.afterInit();
    }

    void OnMissionSelect(String mission_text, CoreTime date, boolean bIsBigRace) {
        if ((this.date_field != 0L) && (menues.ConvertMenuFields(this.date_field) != null)) {
            menues.SetShowField(this.date_field, true);

            MENUText_field obj = (MENUText_field) menues.ConvertMenuFields(this.date_field);

            if (this.date_initial_value == null) {
                this.date_initial_value = obj.text;
            }

            obj.text = Converts.ConvertDateAbsolute(this.date_initial_value, date.gMonth(), date.gDate(), date.gYear(),
                    date.gHour(), date.gMinute());
            menues.UpdateMenuField(obj);
        }

        if ((this.text_message_field != 0L) && (menues.ConvertMenuFields(this.text_message_field) != null)
                && (this.text_message_field_scroller != 0L)
                && (menues.ConvertMenuFields(this.text_message_field_scroller) != null)) {
            MENU_ranger ranger = (MENU_ranger) menues.ConvertMenuFields(this.text_message_field_scroller);
            MENUText_field text = (MENUText_field) menues.ConvertMenuFields(this.text_message_field);

            text.text = mission_text;
            menues.UpdateField(text);
            menues.SetShowField(this.text_message_field, true);

            int texh = menues.GetTextLineHeight(text.nativePointer);
            int startbase = menues.GetBaseLine(text.nativePointer);
            int linescreen = Converts.HeightToLines(text.leny, startbase, texh);
            int linecounter = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, mission_text), startbase,
                                  texh);

            if (this.scroller != null) {
                this.scroller.Deinit();
            }

            this.scroller = new TextScroller(this._menu, ranger, linecounter, linescreen, texh, startbase,
                                             this.text_message_field_scroller_group, true,
                                             "GROUP Tableranger - PHOTO ALBUM - PHOTO - TEXT");
            this.scroller.AddTextControl(text);
        }

        if (!(bIsBigRace)) {
            if (this.pic_bigrace != 0L) {
                menues.SetShowField(this.pic_bigrace, false);
            }

            if (this.pic_simple != 0L) {
                menues.SetShowField(this.pic_simple, true);
            }
        } else {
            if (this.pic_bigrace != 0L) {
                menues.SetShowField(this.pic_bigrace, true);
            }

            if (this.pic_simple != 0L) {
                menues.SetShowField(this.pic_simple, false);
            }
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void enterFocus() {
        this.photos.updateTable();
    }

    /**
     * Method description
     *
     */
    @Override
    public void leaveFocus() {}

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(4);
        }
    }

    /**
     * Interface description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ
     */
    public static abstract interface PhotosComparator extends Comparator {

        /**
         * Method description
         *
         *
         * @param paramBoolean
         */
        public abstract void SetOrder(boolean paramBoolean);
    }


    static class PhotoInfo {
        String mission_name;
        String photo_text;
        CoreTime date;
        String texture_name;
        boolean wheather_show;
        boolean isBigRace;

        PhotoInfo() {
            this.wheather_show = true;
            this.isBigRace = false;
        }

        /**
         * Method description
         *
         *
         * @param object
         *
         * @return
         */
        public boolean equal(PhotoInfo object) {
            return ((this.mission_name == object.mission_name) && (this.photo_text == object.photo_text)
                    && (this.date.moreThan(object.date) == 0));
        }
    }


    class Photos implements ISetupLine, ISelectLineListener {
        private static final int PHOTO_DATE = 0;
        private static final int PHOTO_NAME = 1;
        private final String SORT_METHOD = "onSort";
        String initial_date_value = null;
        private final OrganaizerPhotoAlbum.photos_table_data TABLE_DATA = new OrganaizerPhotoAlbum.photos_table_data();
        private long _menu = 0L;
        OrganaizerPhotoAlbum _parent = null;
        private final String[] SORT = { "BUTTON - PHOTO ALBUM - LIST - DATE", "BUTTON - PHOTO ALBUM - LIST - TASK" };
        private final String[] TABLE_ELEMENTS = { "PHOTO ALBUM - LIST - DATE - VALUE",
                "PHOTO ALBUM - LIST - TASK - VALUE" };
        ArrayList<Album.Item> items = null;
        Table table;

        Photos(long _menu, OrganaizerPhotoAlbum _parent) {
            this.items = Album.getInstance().getAll();
            this._menu = _menu;
            this._parent = _parent;
            OrganaizerPhotoAlbum.this.out_photos_sort_mode = new OrganaizerPhotoAlbum.sort(0, true);
            this.table = new Table(_menu, "TABLEGROUP - PHOTO ALBUM - 18 38", "Tableranger - PHOTO ALBUM - list");
            this.table.setSelectionMode(1);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_com.xml", "Tablegroup - ELEMENTS - PHOTO ALBUM",
                                     this.TABLE_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.table.addListener(this);
            reciveTableData();
            build_tree_data();

            for (String name : this.TABLE_ELEMENTS) {
                this.table.initLinesSelection(name);
            }

            if (null != this.SORT) {
                for (int i = 0; i < this.SORT.length; ++i) {
                    long field = menues.FindFieldInMenu(_menu, this.SORT[i]);
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
         * @param obj
         * @param table_node
         */
        @Override
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        @Override
        public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        @Override
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        @Override
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        @Override
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {}

        /**
         * Method description
         *
         *
         * @param obj
         * @param table_node
         */
        @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
            OrganaizerPhotoAlbum.PhotoInfo line = (OrganaizerPhotoAlbum.PhotoInfo) table_node.item;

            if (!(line.wheather_show)) {
                menues.SetShowField(obj.nativePointer, false);

                return;
            }

            int control = this.table.getMarkedPosition(obj.nativePointer);

            switch (control) {
             case 0 :
                 if (this.initial_date_value == null) {
                     this.initial_date_value = obj.text;
                 }

                 obj.text = Converts.ConvertDateAbsolute(this.initial_date_value, line.date.gMonth(),
                         line.date.gDate(), line.date.gYear(), line.date.gHour(), line.date.gMinute());
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);

                 break;

             case 1 :
                 obj.text = "" + line.mission_name;
                 menues.UpdateMenuField(obj);
                 menues.SetBlindess(obj.nativePointer, false);
                 menues.SetIgnoreEvents(obj.nativePointer, false);
            }

            menues.SetShowField(obj.nativePointer, true);
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

        private void build_tree_data() {
            this.table.reciveTreeData(convertTableData());
        }

        @SuppressWarnings("unchecked")
        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();

            if (this.items != null) {
                for (int i = 0; i < this.items.size(); ++i) {
                    OrganaizerPhotoAlbum.PhotoInfo info = new OrganaizerPhotoAlbum.PhotoInfo();

                    info.date = this.items.get(i).date;
                    info.mission_name = this.items.get(i).locdesc;
                    info.photo_text = this.items.get(i).loctext;
                    info.texture_name = this.items.get(i).material;
                    info.isBigRace = this.items.get(i).is_bigrace_item;
                    this.TABLE_DATA.all_lines.add(info);
                }
            }

            OrganaizerPhotoAlbum.PhotosComparator comp = null;

            if (OrganaizerPhotoAlbum.this.out_photos_sort_mode.type == 0) {
                comp = new OrganaizerPhotoAlbum.SortByNameComparator();
                comp.SetOrder(OrganaizerPhotoAlbum.this.out_photos_sort_mode.up);
            } else if (OrganaizerPhotoAlbum.this.out_photos_sort_mode.type == 1) {
                comp = new OrganaizerPhotoAlbum.SortByDateComparator();
                comp.SetOrder(OrganaizerPhotoAlbum.this.out_photos_sort_mode.up);
            } else {
                comp = new OrganaizerPhotoAlbum.SortByNameComparator();
                comp.SetOrder(true);
            }

            Collections.sort(this.TABLE_DATA.all_lines, comp);
            buildvoidcells();
        }

        private void buildvoidcells() {
            if (this.TABLE_DATA.all_lines.size() < this.table.getNumRows()) {
                int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();

                for (int i = 0; i < dif; ++i) {
                    OrganaizerPhotoAlbum.PhotoInfo data = new OrganaizerPhotoAlbum.PhotoInfo();

                    data.wheather_show = false;
                    this.TABLE_DATA.all_lines.add(data);
                }
            } else {
                int count_good_data = 0;
                Iterator iter = this.TABLE_DATA.all_lines.iterator();

                while ((iter.hasNext()) && (((OrganaizerPhotoAlbum.PhotoInfo) iter.next()).wheather_show)) {
                    ++count_good_data;
                }

                if ((count_good_data < this.table.getNumRows())
                        || (count_good_data >= this.TABLE_DATA.all_lines.size())) {
                    return;
                }

                for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                    this.TABLE_DATA.all_lines.remove(i);
                }
            }
        }

        private void make_sync_group() {
            long[] contrls_name = null;

            if (0 < this.TABLE_ELEMENTS.length) {
                contrls_name = this.table.getLineStatistics_controls(this.TABLE_ELEMENTS[0]);
            }

            long[] contrls_power = null;

            if (1 < this.TABLE_ELEMENTS.length) {
                contrls_power = this.table.getLineStatistics_controls(this.TABLE_ELEMENTS[1]);
            }

            if ((null == contrls_name) || (null == contrls_power)) {
                return;
            }

            if (contrls_name.length != contrls_power.length) {
                Log.menu("ERRORR. make_sync_group has wrong behaivoir. contrls_name.length is " + contrls_name.length
                         + " contrls_time.length is " + contrls_power.length);

                return;
            }

            for (int i = 0; i < contrls_name.length; ++i) {
                menues.SetSyncControlActive(this._menu, i, contrls_name[i]);
                menues.SetSyncControlState(this._menu, i, contrls_name[i]);
                menues.SetSyncControlActive(this._menu, i, contrls_power[i]);
                menues.SetSyncControlState(this._menu, i, contrls_power[i]);
            }
        }

        /**
         * Method description
         *
         */
        public void updateTable() {
            reciveTableData();
            build_tree_data();
            this.table.refresh();
        }

        /**
         * Method description
         *
         */
        public void afterInit() {
            this.table.afterInit();
            make_sync_group();
        }

        /**
         * Method description
         *
         */
        public void deinit() {
            this.table.deinit();
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onSort(long _menu, MENUsimplebutton_field button) {
            switch (button.userid) {
             case 0 :
                 OrganaizerPhotoAlbum.this.out_photos_sort_mode = new OrganaizerPhotoAlbum.sort(0,
                         !(OrganaizerPhotoAlbum.this.out_photos_sort_mode.up));

                 break;

             case 1 :
                 OrganaizerPhotoAlbum.this.out_photos_sort_mode = new OrganaizerPhotoAlbum.sort(1,
                         !(OrganaizerPhotoAlbum.this.out_photos_sort_mode.up));
            }

            OrganaizerPhotoAlbum.PhotoInfo old_photo_info = OrganaizerPhotoAlbum.this.current_info;

            updateTable();

            if (old_photo_info != null) {
                for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                    if (this.TABLE_DATA.all_lines.elementAt(i).equal(old_photo_info)) {
                        this.table.select_line_by_data(this.TABLE_DATA.all_lines.elementAt(i));
                    }
                }
            }
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
            OrganaizerPhotoAlbum.PhotoInfo data = (OrganaizerPhotoAlbum.PhotoInfo) table.getItemOnLine(line).item;

            if ((data != null) && (data.wheather_show == true) && (data.date != null) && (data.mission_name != null)
                    && (data.photo_text != null) && (data.texture_name != null)) {
                OrganaizerPhotoAlbum.this.current_info = data;

                if (data.texture_name != null) {
                    JavaEvents.SendEvent(68, 1, data);
                }

                this._parent.OnMissionSelect(data.photo_text, data.date, data.isBigRace);
            }
        }

        /**
         * Method description
         *
         *
         * @param table
         * @param lines
         */
        @Override
        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {}
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ
     */
    public class SortByDateComparator implements OrganaizerPhotoAlbum.PhotosComparator {
        int order;

        /**
         * Method description
         *
         *
         * @param isascending
         */
        @Override
        public void SetOrder(boolean isascending) {
            this.order = ((isascending)
                          ? 1
                          : -1);
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
            OrganaizerPhotoAlbum.PhotoInfo item1 = (OrganaizerPhotoAlbum.PhotoInfo) o1;
            OrganaizerPhotoAlbum.PhotoInfo item2 = (OrganaizerPhotoAlbum.PhotoInfo) o2;

            if (this.order == 1) {
                return ((item1.date.moreThan(item2.date) > 0)
                        ? 1
                        : -1);
            }

            if (this.order == -1) {
                return ((item1.date.moreThan(item2.date) < 0)
                        ? 1
                        : -1);
            }

            return ((item1.date.moreThan(item2.date) > 0)
                    ? 1
                    : -1);
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ
     */
    public class SortByNameComparator implements OrganaizerPhotoAlbum.PhotosComparator {
        int order;

        /**
         * Method description
         *
         *
         * @param isascending
         */
        @Override
        public void SetOrder(boolean isascending) {
            this.order = ((isascending)
                          ? 1
                          : -1);
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
            OrganaizerPhotoAlbum.PhotoInfo item1 = (OrganaizerPhotoAlbum.PhotoInfo) o1;
            OrganaizerPhotoAlbum.PhotoInfo item2 = (OrganaizerPhotoAlbum.PhotoInfo) o2;

            return Common.Compare(item1.mission_name, item2.mission_name, this.order == 1);
        }
    }


    static class photos_table_data {
        Vector<OrganaizerPhotoAlbum.PhotoInfo> all_lines;

        photos_table_data() {
            this.all_lines = new Vector();
        }
    }


    static class sort {
        int type;
        boolean up;

        sort(int type, boolean up) {
            this.type = type;
            this.up = up;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
