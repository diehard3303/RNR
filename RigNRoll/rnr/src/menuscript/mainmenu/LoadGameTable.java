/*
 * @(#)LoadGameTable.java   13/08/26
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


package rnr.src.menuscript.mainmenu;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.Cmenu_TTI;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUEditBox;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUTruckview;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.menues;
import rnr.src.menuscript.Converts;
import rnr.src.menuscript.IPoPUpMenuListener;
import rnr.src.menuscript.PoPUpMenu;
import rnr.src.menuscript.table.ISelectLineListener;
import rnr.src.menuscript.table.ISetupLine;
import rnr.src.menuscript.table.Table;
import rnr.src.rnrcore.Log;

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
public class LoadGameTable implements ISetupLine, ISelectLineListener {
    private static final String[] METHODS = { "onLoad", "onDelete" };
    private static final int ACTION_LOAD = 0;
    private static final int ACTION_DELETE = 1;
    private static final String GRAY = " GRAY";
    private static final String DELETE_GROUP = "Tablegroup - CONFIRM DELETE";
    private static final String DELETE_WINDOW = "CONFIRM DELETE";
    private static final String REPLACE_GROUP = "Tablegroup - CONFIRM REPLACE";
    private static final String REPLACE_WINDOW = "CONFIRM REPLACE";
    private static final String REPLACE_TEXT = "REPLACE";
    private static final String REPLACE_TEXT_KEY = "PROFILENAME";
    private static final int SAVENAME_ORDER = 0;
    private static final int SAVETIME_ORDER = 2;
    private static final int SAVENAME_QUICKRACE_ORDER = 3;
    private static final int SAVETIME_QUICKRACE_ORDER = 5;

    /** Field description */
    public static final int LOAD_FROM_MAIN_MENU = 0;

    /** Field description */
    public static final int LOAD_FROM_ESC_MENU = 1;
    private String[] BUTTONS = null;
    private String TABLE = null;
    private String RANGER = null;
    private String XML_NAME = null;
    private String LINES = null;
    private String[] ELEMENTS = null;
    private String WARNING_XML = null;
    protected int menu_load_type = 0;
    protected int type_game = 1;
    protected boolean bIsClips = false;
    private IWindowContext context = null;
    private long _menu = 0L;
    private Table table = null;
    private PoPUpMenu warning_delete = null;
    private PoPUpMenu warning_replace = null;
    private PoPUpMenu warning_not_compatible = null;
    SaveLoadCommonManagement.Media under_warning = null;
    private final String newGameName_under_warning = null;
    private long replace_text = 0L;
    private long[] gray_buttons = null;
    private final table_data TABLE_DATA = new table_data();
    boolean sort_by_name = false;
    boolean date_sort_up = false;
    boolean names_sort_up = true;
    long current_game_name = 0L;
    EditName editName = new EditName();
    private String SAVETIME_ORDER_text = null;
    private String SAVETIME_ORDER_QR_text = null;
    private String replace_text_store;

    /**
     * Constructs ...
     *
     *
     * @param context
     * @param _menu
     * @param action_buttons
     * @param table_name
     * @param ranger_name
     * @param xml_name
     * @param lines_name
     * @param lineselements
     * @param type_game
     * @param type_menu_load
     * @param bIsClips
     * @param sortByNameButtonName
     * @param sortByDateButtonName
     * @param gameNameFieldName
     */
    public LoadGameTable(IWindowContext context, long _menu, String[] action_buttons, String table_name,
                         String ranger_name, String xml_name, String lines_name, String[] lineselements, int type_game,
                         int type_menu_load, boolean bIsClips, String sortByNameButtonName,
                         String sortByDateButtonName, String gameNameFieldName) {
        this.menu_load_type = type_menu_load;
        this.context = context;
        this.BUTTONS = action_buttons;
        this.TABLE = table_name;
        this.RANGER = ranger_name;
        this.XML_NAME = xml_name;
        this.WARNING_XML = this.XML_NAME;
        this.LINES = lines_name;
        this.ELEMENTS = lineselements;
        this.type_game = type_game;
        this.bIsClips = bIsClips;
        this._menu = _menu;
        this.gray_buttons = new long[this.BUTTONS.length];

        for (int i = 0; i < this.BUTTONS.length; ++i) {
            this.gray_buttons[i] = menues.FindFieldInMenu(_menu, this.BUTTONS[i] + " GRAY");

            Object field = menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, this.BUTTONS[i]));

            menues.SetScriptOnControl(_menu, field, this, METHODS[i], 4L);
        }

        this.table = new Table(_menu, this.TABLE, this.RANGER);
        this.table.fillWithLines(this.XML_NAME, this.LINES, this.ELEMENTS);
        this.table.takeSetuperForAllLines(this);
        reciveTableData();
        build_tree_data();

        if (0 < this.ELEMENTS.length) {
            this.table.initLinesSelection(this.ELEMENTS[0]);
        }

        if (2 < this.ELEMENTS.length) {
            this.table.initLinesSelection(this.ELEMENTS[2]);
        }

        if (bIsClips) {
            if (3 < this.ELEMENTS.length) {
                this.table.initLinesSelection(this.ELEMENTS[3]);
            }

            if (5 < this.ELEMENTS.length) {
                this.table.initLinesSelection(this.ELEMENTS[5]);
            }
        }

        this.table.addListener(this);
        this.warning_delete = new PoPUpMenu(_menu, this.WARNING_XML, "Tablegroup - CONFIRM DELETE", "CONFIRM DELETE");
        this.warning_replace = new PoPUpMenu(_menu, this.WARNING_XML, "Tablegroup - CONFIRM REPLACE",
                "CONFIRM REPLACE");

        switch (this.menu_load_type) {
         case 0 :
             this.warning_not_compatible = new PoPUpMenu(_menu, this.WARNING_XML,
                     "Tablegroup - SINGLE PLAYER - LOAD GAME - DIFFERENT VERSION");

             break;

         case 1 :
             this.warning_not_compatible = new PoPUpMenu(_menu, this.WARNING_XML,
                     "Tablegroup - SAVELOAD - DIFFERENT VERSION");

             break;

         default :
             this.warning_not_compatible = null;
        }

        this.warning_delete.addListener(new InWarning(2));
        this.warning_replace.addListener(new InWarning(4));
        this.replace_text = this.warning_replace.getField("REPLACE");

        if (this.replace_text != 0L) {
            this.replace_text_store = menues.GetFieldText(this.replace_text);
        }

        if (sortByNameButtonName != null) {
            long sort_by_name = menues.FindFieldInMenu(_menu, sortByNameButtonName);
            Object sort_by_name_field = menues.ConvertMenuFields(sort_by_name);

            menues.SetScriptOnControl(_menu, sort_by_name_field, this, "SortByName", 4L);
        }

        if (sortByDateButtonName != null) {
            long sort_by_date = menues.FindFieldInMenu(_menu, sortByDateButtonName);
            Object sort_by_date_field = menues.ConvertMenuFields(sort_by_date);

            menues.SetScriptOnControl(_menu, sort_by_date_field, this, "SortByDate", 4L);
        }

        if (gameNameFieldName != null) {
            this.current_game_name = menues.FindFieldInMenu(_menu, gameNameFieldName);
        }
    }

    private void make_sync_group() {
        long[] contrls_name = null;

        if (0 < this.ELEMENTS.length) {
            contrls_name = this.table.getLineStatistics_controls(this.ELEMENTS[0]);
        }

        long[] contrls_time = null;

        if (2 < this.ELEMENTS.length) {
            contrls_time = this.table.getLineStatistics_controls(this.ELEMENTS[2]);
        }

        if ((null == contrls_name) || (null == contrls_time)) {
            return;
        }

        if (contrls_name.length != contrls_time.length) {
            Log.menu("ERRORR. make_sync_group has wrong behaivoir. contrls_name.length is " + contrls_name.length
                     + " contrls_time.length is " + contrls_time.length);

            return;
        }

        long[] contrls_time_qr = null;
        long[] contrls_name_qr = null;

        if (this.bIsClips) {
            if (3 < this.ELEMENTS.length) {
                contrls_name_qr = this.table.getLineStatistics_controls(this.ELEMENTS[3]);
            }

            if (5 < this.ELEMENTS.length) {
                contrls_time_qr = this.table.getLineStatistics_controls(this.ELEMENTS[5]);
            }
        }

        for (int i = 0; i < contrls_name.length; ++i) {
            menues.SetSyncControlActive(this._menu, i, contrls_name[i]);
            menues.SetSyncControlState(this._menu, i, contrls_name[i]);
            menues.SetSyncControlActive(this._menu, i, contrls_time[i]);
            menues.SetSyncControlState(this._menu, i, contrls_time[i]);

            if ((contrls_time_qr != null) && (i < contrls_time_qr.length) && (contrls_time_qr[i] != 0L)) {
                menues.SetSyncControlActive(this._menu, i, contrls_time_qr[i]);
                menues.SetSyncControlState(this._menu, i, contrls_time_qr[i]);
            }

            if ((contrls_name_qr != null) && (i < contrls_name_qr.length) && (contrls_name_qr[i] != 0L)) {
                menues.SetSyncControlActive(this._menu, i, contrls_name_qr[i]);
                menues.SetSyncControlState(this._menu, i, contrls_name_qr[i]);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param context
     */
    public void afterInit(IWindowContext context) {
        this.editName.init();
        this.table.afterInit();
        make_sync_group();

        if (!(isEmpty())) {
            this.table.select_line(0);
        }

        this.warning_delete.afterInit();
        this.warning_replace.afterInit();

        if (this.warning_not_compatible != null) {
            this.warning_not_compatible.afterInit();
        }

        for (long gray : this.gray_buttons) {
            if (0L != gray) {
                menues.SetShowField(gray, false);
            }
        }
    }

    /**
     * Method description
     *
     */
    public void readParamValues() {
        this.editName.from_update = true;
        reciveTableData();
        this.table.updateTreeData(convertTableData());
        this.table.refresh();
        this.editName.from_update = false;
    }

    /**
     * Method description
     *
     *
     * @param context
     */
    public void update(IWindowContext context) {
        this.editName.from_update = true;
        reciveTableData();
        this.table.updateTreeData(convertTableData());
        this.table.refresh();
        this.editName.from_update = false;

        if (isEmpty()) {
            context.exitWindowContext();
        }
    }

    /**
     * Method description
     *
     *
     * @param cb
     * @param game_media
     */
    public void addTABLEDATAline(INewSaveGameLine cb, SaveLoadCommonManagement.Media game_media) {
        line_data data = new line_data();

        data.game_media = game_media;
        data.cb = cb;
        this.TABLE_DATA.all_lines.add(0, data);
    }

    private void reciveTableData() {
        Vector data = SaveLoadCommonManagement.getSaveLoadCommonManager().GetExistsMedia(this.type_game,
                          (!(this.bIsClips))
                          ? 7
                          : 8, (this.sort_by_name)
                               ? 0
                               : 1, (this.sort_by_name)
                                    ? this.names_sort_up
                                    : this.date_sort_up);
        Iterator iter = data.iterator();

        for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ) {
            line_data check = this.TABLE_DATA.all_lines.get(i);

            if ((null == check.cb) || (!(check.cb.isPersistant()))) {
                this.TABLE_DATA.all_lines.remove(i);
            } else {
                ++i;
            }
        }

        while (iter.hasNext()) {
            line_data item = new line_data();

            item.game_media = ((SaveLoadCommonManagement.Media) iter.next());
            this.TABLE_DATA.all_lines.add(item);
        }

        buildvoidcells();
    }

    private void buildvoidcells() {
        if (this.TABLE_DATA.all_lines.size() < this.table.getNumRows()) {
            int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();

            for (int i = 0; i < dif; ++i) {
                line_data data = new line_data();

                data.wheather_show = false;
                this.TABLE_DATA.all_lines.add(data);
            }
        } else {
            int count_good_data = 0;
            Iterator iter = this.TABLE_DATA.all_lines.iterator();

            while ((iter.hasNext()) && (((line_data) iter.next()).wheather_show)) {
                ++count_good_data;
            }

            if ((count_good_data >= this.table.getNumRows()) && (count_good_data < this.TABLE_DATA.all_lines.size())) {
                for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                    this.TABLE_DATA.all_lines.remove(i);
                }
            }
        }
    }

    private boolean isEmpty() {
        Iterator iter = this.TABLE_DATA.all_lines.iterator();

        while (iter.hasNext()) {
            if (((line_data) iter.next()).wheather_show) {
                return false;
            }
        }

        return true;
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

    /**
     * Method description
     *
     *
     * @return
     */
    public String GetSelectedMediaName() {
        if (this.table.getSelected() >= this.TABLE_DATA.all_lines.size()) {
            Log.menu("ERRORR onDelete has wrong behaovoir.");

            return null;
        }

        int selected = this.table.getSelected();
        line_data data = this.TABLE_DATA.all_lines.get(selected);
        SaveLoadCommonManagement.Media media = data.game_media;

        if (null != data.cb) {
            return null;
        }

        return media.media_name;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onLoad(long _menu, MENUsimplebutton_field button) {
        if (this.table.getSelected() >= this.TABLE_DATA.all_lines.size()) {
            Log.menu("ERRORR onDelete has wrong behaovoir.");

            return;
        }

        int selected = this.table.getSelected();
        line_data data = this.TABLE_DATA.all_lines.get(selected);
        SaveLoadCommonManagement.Media media = data.game_media;

        if ((null != data.cb) && (!(data.cb.canLoad(media)))) {
            return;
        }

        if (null != media) {
            if ((this.warning_not_compatible != null)
                    && (!(SaveLoadCommonManagement.getSaveLoadCommonManager().IsCompatibleGame(media.media_name,
                        media.game_type, media.media_type)))) {
                this.warning_not_compatible.show();

                return;
            }

            switch (this.menu_load_type) {
             case 0 :
                 SaveLoadCommonManagement.getSaveLoadCommonManager().SetLoadGameFlagFromMainMenu(media.media_name,
                         media.game_type, media.media_type);

                 break;

             case 1 :
                 SaveLoadCommonManagement.getSaveLoadCommonManager().SetLoadGameFlagFromESCMenu(media.media_name,
                         media.game_type, media.media_type);
            }

            TopWindow.quitTopMenu();
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onDelete(long _menu, MENUsimplebutton_field button) {
        if (this.table.getSelected() >= this.TABLE_DATA.all_lines.size()) {
            Log.menu("ERRORR onDelete has wrong behaovoir.");

            return;
        }

        int selected = this.table.getSelected();
        line_data data = this.TABLE_DATA.all_lines.get(selected);
        SaveLoadCommonManagement.Media media = data.game_media;

        if ((null != data.cb) && (!(data.cb.canDelete(media)))) {
            return;
        }

        this.under_warning = media;
        this.warning_delete.show();
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
        line_data line = (line_data) table_node.item;

        if (!(line.wheather_show)) {
            menues.SetShowField(obj.nativePointer, false);

            return;
        }

        int control = this.table.getMarkedPosition(obj.nativePointer);

        switch (control) {
         case 0 :
             obj.text = line.game_media.media_name;
             menues.UpdateMenuField(obj);
             menues.SetBlindess(obj.nativePointer, false);
             menues.SetIgnoreEvents(obj.nativePointer, false);

             if ((this.bIsClips) && (line.game_media.game_type != 1)) {
                 menues.SetShowField(obj.nativePointer, false);

                 return;
             }

             menues.SetShowField(obj.nativePointer, true);

             break;

         case 2 :
             if (this.SAVETIME_ORDER_text == null) {
                 this.SAVETIME_ORDER_text = obj.text;
             }

             obj.text = Converts.ConvertDateAbsolute(this.SAVETIME_ORDER_text, line.game_media.media_time.month,
                     line.game_media.media_time.day, line.game_media.media_time.year, line.game_media.media_time.hour,
                     line.game_media.media_time.min, line.game_media.media_time.sec);
             menues.UpdateMenuField(obj);
             menues.SetBlindess(obj.nativePointer, false);
             menues.SetIgnoreEvents(obj.nativePointer, false);

             if ((this.bIsClips) && (line.game_media.game_type != 1)) {
                 menues.SetShowField(obj.nativePointer, false);

                 return;
             }

             menues.SetShowField(obj.nativePointer, true);

             break;

         case 3 :
             obj.text = line.game_media.media_name;
             menues.UpdateMenuField(obj);
             menues.SetBlindess(obj.nativePointer, false);
             menues.SetIgnoreEvents(obj.nativePointer, false);

             if ((this.bIsClips) && (line.game_media.game_type != 1)) {
                 menues.SetShowField(obj.nativePointer, true);

                 return;
             }

             menues.SetShowField(obj.nativePointer, false);

             break;

         case 5 :
             if (this.SAVETIME_ORDER_QR_text == null) {
                 this.SAVETIME_ORDER_QR_text = obj.text;
             }

             obj.text = Converts.ConvertDateAbsolute(this.SAVETIME_ORDER_QR_text, line.game_media.media_time.month,
                     line.game_media.media_time.day, line.game_media.media_time.year, line.game_media.media_time.hour,
                     line.game_media.media_time.min, line.game_media.media_time.sec);
             menues.UpdateMenuField(obj);
             menues.SetBlindess(obj.nativePointer, false);
             menues.SetIgnoreEvents(obj.nativePointer, false);

             if ((this.bIsClips) && (line.game_media.game_type != 1)) {
                 menues.SetShowField(obj.nativePointer, true);

                 return;
             }

             menues.SetShowField(obj.nativePointer, false);
         case 1 :
         case 4 :
        }
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        menues.SetBlindess(obj.nativePointer, true);
        menues.SetIgnoreEvents(obj.nativePointer, true);
        menues.SetShowField(obj.nativePointer, false);
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {}

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {}

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {}

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {}

    /**
     * Method description
     *
     *
     * @param table
     * @param line
     */
    @Override
    public void selectLineEvent(Table table, int line) {
        this.editName.selectLine(line);

        line_data data = (line_data) table.getItemOnLine(line).item;

        if (null != data.cb) {
            setDeleteActionGray(!(data.cb.canBeDeleted(data.game_media)));
            setLoadActionGray(!(data.cb.canBeLoaded(data.game_media)));
        } else {
            setDeleteActionGray(false);
            setLoadActionGray(false);
        }

        if (data.game_media != null) {
            if ((null != data.cb) && (data.cb.isMediaCurrent(data.game_media))) {
                SaveLoadCommonManagement.getSaveLoadCommonManager().UpdateShotByCurrent();
            } else {
                SaveLoadCommonManagement.getSaveLoadCommonManager().UpdateShot(data.game_media.media_name,
                        data.game_media.game_type, data.game_media.media_type);
            }

            if (this.current_game_name != 0L) {
                menues.SetFieldText(this.current_game_name, data.game_media.media_name);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.current_game_name));
            }
        }
    }

    static boolean isLoadgameListEmpty(int type, boolean isClips) {
        if (!(isClips)) {
            return SaveLoadCommonManagement.getSaveLoadCommonManager().GetExistsMedia(type, 7, 0, false).isEmpty();
        }

        return SaveLoadCommonManagement.getSaveLoadCommonManager().GetExistsMedia(type, 8, 0, false).isEmpty();
    }

    /**
     * Method description
     *
     *
     * @param table
     * @param lines
     */
    public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {}

    private void setLoadActionGray(boolean value) {
        if (0L != this.gray_buttons[0]) {
            menues.SetShowField(this.gray_buttons[0], value);
        }
    }

    private void setDeleteActionGray(boolean value) {
        if (0L != this.gray_buttons[1]) {
            menues.SetShowField(this.gray_buttons[1], value);
        }
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
    public void SortByName(long _menu, MENUsimplebutton_field button) {
        this.sort_by_name = true;
        this.names_sort_up = (!(this.names_sort_up));
        this.editName.from_update = true;
        reciveTableData();
        this.table.updateTreeData(convertTableData());
        this.table.refresh();
        this.editName.from_update = false;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void SortByDate(long _menu, MENUsimplebutton_field button) {
        this.sort_by_name = false;
        this.date_sort_up = (!(this.date_sort_up));
        this.editName.from_update = true;
        reciveTableData();
        this.table.updateTreeData(convertTableData());
        this.table.refresh();
        this.editName.from_update = false;
    }

    class EditName {
        private final String METHOD_CHANGENAME = "changeSaveGameName";
        private final String METHOD_DISSMIS = "dissmissSaveGameName";
        private final int BACKFIELD = 0;
        private final int BACKFIELD_QR = 3;
        private final int EDITFIELD = 1;
        private final int EDITFIELD_QR = 4;
        private int lastline;
        private long[] controls_sp;
        private long[] controls_back_sp;
        private long[] controls_qr;
        private long[] controls_back_qr;
        boolean from_update;

        EditName() {
            this.METHOD_CHANGENAME = "changeSaveGameName";
            this.METHOD_DISSMIS = "dissmissSaveGameName";
            this.BACKFIELD = 0;
            this.BACKFIELD_QR = 3;
            this.EDITFIELD = 1;
            this.EDITFIELD_QR = 4;
            this.lastline = -1;
            this.from_update = false;
        }

        void selectLine(int line) {
            if (this.from_update) {
                return;
            }

            if (this.lastline == line) {
                if ((line < 0) || (line >= this.controls_sp.length)) {
                    Log.menu("ERRORR.ProfileSelectProfile EditName selectLine - bad value " + line
                             + " with controls.length " + this.controls_sp.length);

                    return;
                }

                LoadGameTable.line_data data =
                    (LoadGameTable.line_data) LoadGameTable.this.table.getItemOnLine(line).item;

                if ((null != data.cb) && (data.cb.clearOnEnterEdit(data.game_media))) {
                    data.game_media.media_name = "";
                }

                if (data.game_media != null) {
                    if ((LoadGameTable.this.bIsClips) && (data.game_media.game_type != 1)) {
                        menues.setfocuscontrolonmenu(LoadGameTable.this._menu, this.controls_qr[line]);
                        menues.SetShowField(this.controls_qr[line], true);
                        menues.SetBlindess(this.controls_qr[line], false);
                        menues.SetIgnoreEvents(this.controls_qr[line], false);
                        menues.SetFieldText(this.controls_qr[line], data.game_media.media_name);
                        menues.SetFieldText(this.controls_back_qr[line], "");
                        menues.SetShowField(this.controls_back_qr[line], false);
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.controls_qr[line]));
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.controls_back_qr[line]));
                    } else {
                        menues.setfocuscontrolonmenu(LoadGameTable.this._menu, this.controls_sp[line]);
                        menues.SetShowField(this.controls_sp[line], true);
                        menues.SetBlindess(this.controls_sp[line], false);
                        menues.SetIgnoreEvents(this.controls_sp[line], false);
                        menues.SetFieldText(this.controls_sp[line], data.game_media.media_name);
                        menues.SetFieldText(this.controls_back_sp[line], "");
                        menues.SetShowField(this.controls_back_sp[line], false);
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.controls_sp[line]));
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.controls_back_sp[line]));
                    }
                }
            }

            this.lastline = line;
        }

        void init() {
            this.controls_back_sp = LoadGameTable.this.table.getLineStatistics_controls(LoadGameTable.this.ELEMENTS[0]);

            if (null == this.controls_back_sp) {
                Log.menu("ERRORR. LoadGameTable EditName init(). Table contains no controls_back fields, named "
                         + LoadGameTable.this.ELEMENTS[0]);
            }

            this.controls_sp = LoadGameTable.this.table.getLineStatistics_controls(LoadGameTable.this.ELEMENTS[1]);

            if (null == this.controls_sp) {
                Log.menu("ERRORR. LoadGameTable EditName init(). Table contains no controls fields, named "
                         + LoadGameTable.this.ELEMENTS[1]);
            }

            if (LoadGameTable.this.bIsClips) {
                this.controls_back_qr =
                    LoadGameTable.this.table.getLineStatistics_controls(LoadGameTable.this.ELEMENTS[3]);

                if (null == this.controls_back_sp) {
                    Log.menu("ERRORR. LoadGameTable EditName init(). Table contains no controls_back fields, named "
                             + LoadGameTable.this.ELEMENTS[3]);
                }

                this.controls_qr = LoadGameTable.this.table.getLineStatistics_controls(LoadGameTable.this.ELEMENTS[4]);

                if (null == this.controls_qr) {
                    Log.menu("ERRORR. LoadGameTable EditName init(). Table contains no controls fields, named "
                             + LoadGameTable.this.ELEMENTS[4]);
                }
            }

            for (int i = 0; i < this.controls_sp.length; ++i) {
                menues.SetScriptOnControl(LoadGameTable.this._menu, menues.ConvertMenuFields(this.controls_sp[i]),
                                          this, "changeSaveGameName", 16L);
                menues.SetScriptOnControl(LoadGameTable.this._menu, menues.ConvertMenuFields(this.controls_sp[i]),
                                          this, "dissmissSaveGameName", 19L);

                if ((this.controls_qr != null) && (i < this.controls_qr.length) && (this.controls_qr[i] != 0L)) {
                    menues.SetScriptOnControl(LoadGameTable.this._menu, menues.ConvertMenuFields(this.controls_qr[i]),
                                              this, "changeSaveGameName", 16L);
                    menues.SetScriptOnControl(LoadGameTable.this._menu, menues.ConvertMenuFields(this.controls_qr[i]),
                                              this, "dissmissSaveGameName", 19L);
                }
            }
        }

        void dissmissSaveGameName(long _menu, MENUEditBox obj) {
            LoadGameTable.line_data data =
                (LoadGameTable.line_data) LoadGameTable.this.table.getItemOnLine(this.lastline).item;

            if (null != data.cb) {
                menues.ConvertMenuFields(obj.nativePointer);

                String text = menues.GetFieldText(obj.nativePointer);

                if (data.cb.dismiss(data.game_media, text)) {
                    dismiss_data();
                }
            } else {
                dismiss_data();
            }
        }

        void changeSaveGameName(long _menu, MENUEditBox obj) {
            menues.ConvertMenuFields(obj.nativePointer);

            String text = menues.GetFieldText(obj.nativePointer);
            LoadGameTable.line_data data =
                (LoadGameTable.line_data) LoadGameTable.this.table.getItemOnLine(this.lastline).item;

            if (text.compareToIgnoreCase("") == 0) {
                if (null != data.cb) {
                    data.cb.dismiss(data.game_media, text);
                }

                dismiss_data();

                return;
            }

            String oldprofile = data.game_media.media_name;

            if (oldprofile.compareToIgnoreCase(text) != 0) {
                if (null != data.cb) {
                    if (data.cb.rename(data.game_media, oldprofile, text)) {
                        return;
                    }

                    dismiss_data();
                } else if (!(SaveLoadCommonManagement.getSaveLoadCommonManager().RenameExistsMedia(
                        data.game_media.media_name, data.game_media.game_type, data.game_media.media_type, text))) {
                    LoadGameTable.access$302(LoadGameTable.this, text);
                    LoadGameTable.this.under_warning = data.game_media;

                    if (LoadGameTable.this.replace_text != 0L) {
                        KeyPair[] keys = new KeyPair[1];

                        keys[0] = new KeyPair("PROFILENAME", text);
                        menues.SetFieldText(LoadGameTable.this.replace_text,
                                            MacroKit.Parse(LoadGameTable.this.replace_text_store, keys));
                        menues.UpdateMenuField(menues.ConvertMenuFields(LoadGameTable.this.replace_text));
                    }

                    LoadGameTable.this.warning_replace.show();
                } else {
                    data.game_media.media_name = text;

                    if (LoadGameTable.this.current_game_name != 0L) {
                        menues.SetFieldText(LoadGameTable.this.current_game_name, data.game_media.media_name);
                        menues.UpdateMenuField(menues.ConvertMenuFields(LoadGameTable.this.current_game_name));
                    }

                    dismiss_data();
                }
            } else {
                dismiss_data();
            }
        }

        private void dismiss_data() {
            this.from_update = true;
            LoadGameTable.this.table.refreshLine(this.lastline);
            this.from_update = false;
        }
    }


    class InWarning implements IPoPUpMenuListener {
        static final int CANNOTDELETE = 1;
        static final int DELETE = 2;
        static final int REPLACE = 4;
        private final int state;

        InWarning(int paramInt) {
            this.state = paramInt;
        }

        /**
         * Method description
         *
         */
        @Override
        public void onAgreeclose() {
            switch (this.state) {
             case 2 :
                 SaveLoadCommonManagement.getSaveLoadCommonManager().DeleteExistsMedia(
                     LoadGameTable.this.under_warning.media_name, LoadGameTable.this.under_warning.game_type,
                     LoadGameTable.this.under_warning.media_type);
                 LoadGameTable.this.context.updateWindowContext();

                 if (LoadGameTable.this.isEmpty() != 0) {
                     return;
                 }

                 LoadGameTable.this.table.select_line(0);

                 break;

             case 4 :
                 SaveLoadCommonManagement.getSaveLoadCommonManager().DeleteExistsMedia(
                     LoadGameTable.this.newGameName_under_warning, LoadGameTable.this.under_warning.game_type,
                     LoadGameTable.this.under_warning.media_type);
                 SaveLoadCommonManagement.getSaveLoadCommonManager().RenameExistsMedia(
                     LoadGameTable.this.under_warning.media_name, LoadGameTable.this.under_warning.game_type,
                     LoadGameTable.this.under_warning.media_type, LoadGameTable.this.newGameName_under_warning);

                 if (LoadGameTable.this.current_game_name != 0L) {
                     menues.SetFieldText(LoadGameTable.this.current_game_name,
                                         LoadGameTable.this.newGameName_under_warning);
                     menues.UpdateMenuField(menues.ConvertMenuFields(LoadGameTable.this.current_game_name));
                 }

                 LoadGameTable.this.context.updateWindowContext();
                 LoadGameTable.access$302(LoadGameTable.this, null);
                 LoadGameTable.this.under_warning = null;
            }
        }

        /**
         * Method description
         *
         */
        @Override
        public void onClose() {
            switch (this.state) {
             case 4 :
                 LoadGameTable.this.table.refresh();
                 LoadGameTable.access$302(LoadGameTable.this, null);
                 LoadGameTable.this.under_warning = null;
            }
        }

        /**
         * Method description
         *
         */
        @Override
        public void onCancel() {}

        /**
         * Method description
         *
         */
        @Override
        public void onOpen() {}
    }


    static class line_data {
        boolean wheather_show;
        INewSaveGameLine cb;
        SaveLoadCommonManagement.Media game_media;

        line_data() {
            this.wheather_show = true;
            this.cb = null;
        }
    }


    static class table_data {
        Vector<LoadGameTable.line_data> all_lines;

        table_data() {
            this.all_lines = new Vector();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
