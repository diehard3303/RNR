/*
 * @(#)ProfileSelectProfile.java   13/08/26
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
public class ProfileSelectProfile extends PanelDialog implements ISetupLine, ISelectLineListener {
    private static final String[] BUTTONS = { "PROFILE - SELECT PROFILE SELECT", "PROFILE - SELECT PROFILE DELETE" };
    private static final String[] METHODS = { "onSelect", "onDelete" };
    private static final String TABLET = "PROFILE - SELECT PROFILE - THE PLATE";
    private static final String TABLET_LICENCE = "PROFILE - SELECT PROFILE - THE DRIVER";
    private static final String[] TABLET_LICENCETEXT = { "PROFILE - SELECT PROFILE - LICENSE PLATE - TEXT LIGHT",
            "PROFILE - SELECT PROFILE - LICENSE PLATE - TEXT DARK" };
    private static final int TEXT_LIGHT = 0;
    private static final int TEXT_DARK = 1;
    private static final String TABLE = "TABLEGROUP - PROFILE - SELECT PROFILE - 8 70";
    private static final String RANGER = "Tableranger - PROFILE - SELECT PROFILE";
    private static final String XML_NAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String LINES = "Tablegroup - ELEMENTS - Profile Lines";
    private static final String[] ELEMENTS = { "PROFILE - SELECT PROFILE - DriverName",
            "PROFILE - SELECT PROFILE - DriverName Edit" };
    private static final String WARNING_XML = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String DELETE_GROUP = "Tablegroup - PROFILE -  SELECT PROFILE - CONFIRM DELETE";
    private static final String DELETE_WINDOW = "PROFILE -  SELECT PROFILE - CONFIRM DELETE";
    private static final String REPLACE_GROUP = "Tablegroup - PROFILE -  NEW PROFILE - CONFIRM REPLACE";
    private static final String REPLACE_WINDOW = "PROFILE -  NEW PROFILE - CONFIRM REPLACE";
    private static final String CANNOTDELETE_GROUP = "Tablegroup - PROFILE -  INFO";
    private static final String CANNOTDELETE_WINDOW = "PROFILE -  INFO";
    private static final String REPLACE_TEXT = "Profile - REPLACE";
    private static final String REPLACE_TEXT_KEY = "PROFILENAME";
    private long _menu = 0L;
    private Table table = null;
    private long tablet = 0L;
    private long tablet_licence = 0L;
    private PoPUpMenu warning_delete = null;
    private PoPUpMenu warning_replace = null;
    private PoPUpMenu info_cannotdelete = null;
    private long replace_text = 0L;
    private String profile_under_warning = null;
    private final line_data line_data_under_warning = null;
    private final String newProfilename_under_warning = null;
    private final table_data TABLE_DATA = new table_data();
    EditName editName = new EditName();
    private final long[] tablet_licence_text;
    private String replace_text_store;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param controls
     * @param window
     * @param exitButton
     * @param defaultButton
     * @param okButton
     * @param parent
     */
    public ProfileSelectProfile(long _menu, long[] controls, long window, long exitButton, long defaultButton,
                                long okButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, 0L, parent);
        this._menu = _menu;
        this.tablet = menues.FindFieldInMenu(_menu, "PROFILE - SELECT PROFILE - THE PLATE");
        this.tablet_licence = menues.FindFieldInMenu(_menu, "PROFILE - SELECT PROFILE - THE DRIVER");
        this.tablet_licence_text = new long[TABLET_LICENCETEXT.length];

        for (int i = 0; i < TABLET_LICENCETEXT.length; ++i) {
            this.tablet_licence_text[i] = menues.FindFieldInMenu(_menu, TABLET_LICENCETEXT[i]);
        }

        for (int i = 0; i < BUTTONS.length; ++i) {
            Object field = menues.ConvertMenuFields(menues.FindFieldInMenu(_menu, BUTTONS[i]));

            menues.SetScriptOnControl(_menu, field, this, METHODS[i], 4L);
        }

        this.table = new Table(_menu, "TABLEGROUP - PROFILE - SELECT PROFILE - 8 70",
                               "Tableranger - PROFILE - SELECT PROFILE");
        this.table.fillWithLines("..\\data\\config\\menu\\menu_MAIN.xml", "Tablegroup - ELEMENTS - Profile Lines",
                                 ELEMENTS);
        this.table.takeSetuperForAllLines(this);
        reciveTableData();
        build_tree_data();
        this.table.initLinesSelection(ELEMENTS[0]);
        this.table.addListener(this);
        this.warning_delete = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_MAIN.xml",
                "Tablegroup - PROFILE -  SELECT PROFILE - CONFIRM DELETE",
                "PROFILE -  SELECT PROFILE - CONFIRM DELETE");
        this.warning_replace = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_MAIN.xml",
                "Tablegroup - PROFILE -  NEW PROFILE - CONFIRM REPLACE", "PROFILE -  NEW PROFILE - CONFIRM REPLACE");
        this.info_cannotdelete = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_MAIN.xml",
                "Tablegroup - PROFILE -  INFO", "PROFILE -  INFO");
        this.warning_delete.addListener(new InWarning(2));
        this.warning_replace.addListener(new InWarning(4));
        this.info_cannotdelete.addListener(new InWarning(1));
        this.replace_text = this.warning_replace.getField("Profile - REPLACE");

        if (this.replace_text != 0L) {
            this.replace_text_store = menues.GetFieldText(this.replace_text);
        }
    }

    private Cmenu_TTI convertTableData() {
        Cmenu_TTI root = new Cmenu_TTI();

        for (int i = 0; i < this.TABLE_DATA.all_profiles.size(); ++i) {
            Cmenu_TTI ch = new Cmenu_TTI();

            ch.toshow = true;
            ch.ontop = (i == 0);
            ch.item = this.TABLE_DATA.all_profiles.get(i);
            root.children.add(ch);
        }

        return root;
    }

    private void buildvoidcells() {
        if (this.TABLE_DATA.all_profiles.size() < this.table.getNumRows()) {
            int dif = this.table.getNumRows() - this.TABLE_DATA.all_profiles.size();

            for (int i = 0; i < dif; ++i) {
                line_data data = new line_data();

                data.wheather_show = false;
                this.TABLE_DATA.all_profiles.add(data);
            }
        } else {
            int count_good_data = 0;
            Iterator iter = this.TABLE_DATA.all_profiles.iterator();

            while ((iter.hasNext()) && (((line_data) iter.next()).wheather_show)) {
                ++count_good_data;
            }

            if ((count_good_data >= this.table.getNumRows())
                    && (count_good_data < this.TABLE_DATA.all_profiles.size())) {
                for (int i = this.TABLE_DATA.all_profiles.size() - 1; i >= count_good_data; --i) {
                    this.TABLE_DATA.all_profiles.remove(i);
                }
            }
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        super.afterInit();
        this.table.afterInit();
        this.editName.init();

        if (this.tablet != 0L) {
            menues.SetBlindess(this.tablet, true);
            menues.SetIgnoreEvents(this.tablet, true);
        }

        this.table.select_line(this.TABLE_DATA.current_profile);
        this.warning_delete.afterInit();
        this.warning_replace.afterInit();
        this.info_cannotdelete.afterInit();
    }

    private void build_tree_data() {
        this.table.reciveTreeData(convertTableData());
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onSelect(long _menu, MENUsimplebutton_field button) {
        this.TABLE_DATA.current_profile = this.table.getSelected();
        ProfileManagement.getProfileManager().SetPrifile(
            this.TABLE_DATA.all_profiles.get(this.TABLE_DATA.current_profile).profile_name);
        StartMenu.getCurrentObject().update_profile_name();
        StartMenu.getCurrentObject().restoreProfileValuesToMenu();
        exitDialog();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onDelete(long _menu, MENUsimplebutton_field button) {
        if (this.table.getSelected() >= this.TABLE_DATA.all_profiles.size()) {
            Log.menu("ERRORR onDelete has wrong behaovoir.");

            return;
        }

        int selected = this.table.getSelected();

        this.profile_under_warning = this.TABLE_DATA.all_profiles.get(selected).profile_name;

        if (ProfileManagement.getProfileManager().GetCurrentProfileName().compareToIgnoreCase(
                this.profile_under_warning) == 0) {
            this.info_cannotdelete.show();
        } else {
            this.warning_delete.show();
        }
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
             obj.text = line.profile_name;
             menues.UpdateMenuField(obj);
             menues.SetBlindess(obj.nativePointer, false);
             menues.SetIgnoreEvents(obj.nativePointer, false);
        }

        menues.SetShowField(obj.nativePointer, true);
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
    public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        line_data line = (line_data) table_node.item;

        menues.SetFieldText(obj.nativePointer, line.profile_name);
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
        line_data line = (line_data) table_node.item;

        menues.SetFieldText(obj.nativePointer, line.profile_name);
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        line_data line = (line_data) table_node.item;

        menues.SetFieldText(obj.nativePointer, line.profile_name);
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param table_node
     */
    public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
        line_data line = (line_data) table_node.item;

        menues.SetFieldText(obj.nativePointer, line.profile_name);
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
        this.editName.selectLine(line);

        line_data data = (line_data) table.getItemOnLine(line).item;

        if (this.tablet != 0L) {
            menues.SetBlindess(this.tablet, true);
            menues.SetIgnoreEvents(this.tablet, true);
            menues.SetFieldState(this.tablet, data.logo_state);
            menues.SetFieldText(this.tablet_licence, data.profile_name);
            menues.UpdateMenuField(menues.ConvertMenuFields(this.tablet_licence));

            boolean is_light = ProfileNewProfile.isLightPlate(data.logo_state);

            for (int i = 0; i < this.tablet_licence_text.length; ++i) {
                menues.SetShowField(this.tablet_licence_text[i],
                                    ((is_light) && (i == 0)) || ((!(is_light)) && (i == 1)));
                menues.SetFieldText(this.tablet_licence_text[i], data.logo_name);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.tablet_licence_text[i]));
            }
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void update() {
        this.editName.from_update = true;
        super.update();
        reciveTableData();
        this.table.updateTreeData(convertTableData());
        this.table.refresh();
        this.table.select_line(this.TABLE_DATA.current_profile);
        this.editName.from_update = false;

        if (this.tablet != 0L) {
            menues.SetBlindess(this.tablet, true);
            menues.SetIgnoreEvents(this.tablet, true);
        }
    }

    private void reciveTableData() {
        ProfileManagement pro = ProfileManagement.getProfileManager();
        Vector profiles = pro.GetExistsProfiles();
        Iterator iter = profiles.iterator();
        int count_iteration = 0;

        this.TABLE_DATA.current_profile = 0;
        this.TABLE_DATA.all_profiles.clear();

        while (iter.hasNext()) {
            line_data data = new line_data();

            data.profile_name = ((String) iter.next());
            data.logo_state = pro.GetProfileLogo(data.profile_name);
            data.logo_name = pro.GetProfileLicensePlateString(data.profile_name).toUpperCase()
                             + pro.GetDefaultLicensePlateSuffix();
            this.TABLE_DATA.all_profiles.add(data);
            ++count_iteration;
        }

        if (-1 == this.TABLE_DATA.current_profile) {
            Log.menu("ERRORR. Current profile is absent above all profiles.");
        }

        buildvoidcells();
    }

    /**
     * Method description
     *
     *
     * @param table
     * @param lines
     */
    public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {}

    @Override
    protected void onShow(boolean value) {
        if (this.tablet != 0L) {
            menues.SetBlindess(this.tablet, true);
            menues.SetIgnoreEvents(this.tablet, true);

            if ((value) && (this.table != null)) {
                line_data data = (line_data) this.table.getSelectedData().item;

                if (data != null) {
                    menues.SetFieldState(this.tablet, data.logo_state);
                    menues.SetFieldText(this.tablet_licence, data.profile_name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(this.tablet_licence));

                    boolean is_light = ProfileNewProfile.isLightPlate(data.logo_state);

                    for (int i = 0; i < this.tablet_licence_text.length; ++i) {
                        menues.SetShowField(this.tablet_licence_text[i],
                                            ((is_light) && (i == 0)) || ((!(is_light)) && (i == 1)));
                        menues.SetFieldText(this.tablet_licence_text[i], data.logo_name);
                        menues.UpdateMenuField(menues.ConvertMenuFields(this.tablet_licence_text[i]));
                    }
                }
            }
        }
    }

    @Override
    protected void onFreeze(boolean value) {
        if (this.tablet != 0L) {
            menues.SetBlindess(this.tablet, true);
            menues.SetIgnoreEvents(this.tablet, true);
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
        super.exitMenu();
        this.table.deinit();
    }

    class EditName {
        private final String METHOD_CHANGENAME = "changeProfileName";
        private final String METHOD_DISSMIS = "dissmisProfileName";
        private final int BACKFIELD = 0;
        private final int EDITFIELD = 1;
        private int lastline;
        private long[] controls;
        private long[] controls_back;
        boolean from_update;

        EditName() {
            this.METHOD_CHANGENAME = "changeProfileName";
            this.METHOD_DISSMIS = "dissmisProfileName";
            this.BACKFIELD = 0;
            this.EDITFIELD = 1;
            this.lastline = -1;
            this.from_update = false;
        }

        void selectLine(int line) {
            if (this.from_update) {
                return;
            }

            if (this.lastline == line) {
                if ((line < 0) || (line >= this.controls.length)) {
                    Log.menu("ERRORR.ProfileSelectProfile EditName selectLine - bad value " + line
                             + " with controls.length " + this.controls.length);

                    return;
                }

                ProfileSelectProfile.line_data data =
                    (ProfileSelectProfile.line_data) ProfileSelectProfile.this.table.getItemOnLine(line).item;

                menues.setfocuscontrolonmenu(ProfileSelectProfile.this._menu, this.controls[line]);
                menues.SetShowField(this.controls[line], true);
                menues.SetBlindess(this.controls[line], false);
                menues.SetIgnoreEvents(this.controls[line], false);
                menues.SetFieldText(this.controls[line], data.profile_name);
                menues.SetFieldText(this.controls_back[line], "");
                menues.SetShowField(this.controls_back[line], false);
                menues.UpdateMenuField(menues.ConvertMenuFields(this.controls[line]));
                menues.UpdateMenuField(menues.ConvertMenuFields(this.controls_back[line]));
            }

            this.lastline = line;
        }

        void init() {
            this.controls_back =
                ProfileSelectProfile.this.table.getLineStatistics_controls(ProfileSelectProfile.ELEMENTS[0]);
            this.controls =
                ProfileSelectProfile.this.table.getLineStatistics_controls(ProfileSelectProfile.ELEMENTS[1]);

            for (int i = 0; i < this.controls.length; ++i) {
                menues.SetScriptOnControl(ProfileSelectProfile.this._menu, menues.ConvertMenuFields(this.controls[i]),
                                          this, "changeProfileName", 16L);
                menues.SetScriptOnControl(ProfileSelectProfile.this._menu, menues.ConvertMenuFields(this.controls[i]),
                                          this, "dissmisProfileName", 19L);
            }
        }

        void dissmisProfileName(long _menu, MENUEditBox obj) {
            this.from_update = true;
            ProfileSelectProfile.this.table.refreshLine(this.lastline);
            this.from_update = false;
        }

        void changeProfileName(long _menu, MENUEditBox obj) {
            menues.ConvertMenuFields(obj.nativePointer);

            String text = menues.GetFieldText(obj.nativePointer);

            if (text.compareToIgnoreCase("") == 0) {
                ProfileSelectProfile.this.table.refresh();

                return;
            }

            ProfileSelectProfile.line_data data =
                (ProfileSelectProfile.line_data) ProfileSelectProfile.this.table.getItemOnLine(this.lastline).item;
            String oldprofile = data.profile_name;

            if (oldprofile.compareToIgnoreCase(text) != 0) {
                if (ProfileManagement.getProfileManager().IsProfileExists(text)) {
                    ProfileSelectProfile.access$302(ProfileSelectProfile.this, text);
                    ProfileSelectProfile.access$402(ProfileSelectProfile.this, data);

                    if (ProfileSelectProfile.this.replace_text != 0L) {
                        KeyPair[] keys = new KeyPair[1];

                        keys[0] = new KeyPair("PROFILENAME", text);
                        menues.SetFieldText(ProfileSelectProfile.this.replace_text,
                                            MacroKit.Parse(ProfileSelectProfile.this.replace_text_store, keys));
                        menues.UpdateMenuField(menues.ConvertMenuFields(ProfileSelectProfile.this.replace_text));
                    }

                    ProfileSelectProfile.this.warning_replace.show();
                } else {
                    data.profile_name = text;
                    ProfileManagement.getProfileManager().RenameProfile(oldprofile, text);
                    ProfileSelectProfile.this.table.refresh();
                    StartMenu.getCurrentObject().update_profile_name();
                }
            }
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
                 ProfileManagement.getProfileManager().DeleteProfile(ProfileSelectProfile.this.profile_under_warning);
                 ProfileSelectProfile.this.update();
                 ProfileSelectProfile.this.table.select_line(0);
                 StartMenu.getCurrentObject().update_profile_name();

                 break;

             case 4 :
                 if (ProfileManagement.getProfileManager().DeleteProfile(
                         ProfileSelectProfile.this.newProfilename_under_warning)) {
                     String oldprofile = ProfileSelectProfile.this.line_data_under_warning.profile_name;

                     if (ProfileManagement.getProfileManager().RenameProfile(oldprofile,
                             ProfileSelectProfile.this.newProfilename_under_warning)) {
                         ProfileSelectProfile.this.line_data_under_warning.profile_name =
                             ProfileSelectProfile.this.newProfilename_under_warning;
                     }
                 }

                 ProfileSelectProfile.this.update();
                 ProfileSelectProfile.access$302(ProfileSelectProfile.this, null);
                 ProfileSelectProfile.access$402(ProfileSelectProfile.this, null);
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
                 ProfileSelectProfile.this.table.refresh();
                 ProfileSelectProfile.access$302(ProfileSelectProfile.this, null);
                 ProfileSelectProfile.access$402(ProfileSelectProfile.this, null);
            }
        }

        /**
         * Method description
         *
         */
        @Override
        public void onOpen() {}

        /**
         * Method description
         *
         */
        @Override
        public void onCancel() {}
    }


    static class line_data {
        boolean wheather_show;
        int logo_state;
        String logo_name;
        String profile_name;

        line_data() {
            this.wheather_show = true;
            this.logo_name = "RIPP";
        }
    }


    static class table_data {
        Vector<ProfileSelectProfile.line_data> all_profiles;
        int current_profile;

        table_data() {
            this.all_profiles = new Vector();
            this.current_profile = 1;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
