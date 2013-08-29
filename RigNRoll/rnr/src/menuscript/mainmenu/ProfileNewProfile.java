/*
 * @(#)ProfileNewProfile.java   13/08/26
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

import rnr.src.menu.IValueChanged;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUEditBox;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.RadioSmartSwitch;
import rnr.src.menu.TableOfElements;
import rnr.src.menu.menues;
import rnr.src.menuscript.EditFieldWrapper;
import rnr.src.menuscript.IEditFieldListener;
import rnr.src.menuscript.IPoPUpMenuListener;
import rnr.src.menuscript.PoPUpMenu;
import rnr.src.menuscript.parametrs.ParametrsBlock;
import rnr.src.rnrcore.loc;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class ProfileNewProfile extends PanelDialog implements IValueChanged, IPoPUpMenuListener, IEditFieldListener {
    private static final String FIELD_PROFILENAME = "PROFILE NAME - VALUE";
    private static final String FIELD_PROFILE_LICENCEPLATE_LIGHT = "LICENSE PLATE - TEXT LIGHT";
    private static final String FIELD_PROFILE_LICENCEPLATE_DARK = "LICENSE PLATE - TEXT DARK";
    private static final String TABLE_NAME = "TABLEGROUP - PROFILE - NEW PROFILE - 2 70";
    private static final String PANEL_XML = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String PANEL_CONTROLGROUP = "Tablegroup - ELEMENTS - Profile Panel";
    private static final String PANEL_TITLE = "LICENSE PLATE";
    private static final String WARNING_XML = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String WARNING_GROUP = "Tablegroup - PROFILE -  NEW PROFILE - CONFIRM REPLACE";
    private static final String WARNING_WINDOW = "PROFILE -  NEW PROFILE - CONFIRM REPLACE";
    private static final String WARNING_TEXT = "Profile - REPLACE";
    private static final String WARNING_TEXT_KEY = "PROFILENAME";
    private static final String CANNOTDELETE_GROUP = "Tablegroup - PROFILE -  NEW PROFILE - CANNOT EDIT";
    private static final String CANNOTDELETE_WINDOW = "PROFILE -  NEW PROFILE - CANNOT EDIT";
    private static final int NUM_STATES = 7;
    private long _menu = 0L;
    private long profilename = 0L;
    private TableOfElements table = null;
    RadioSmartSwitch panel = null;
    private int logo_state = 0;
    private PoPUpMenu warning = null;
    private PoPUpMenu info_cannotdelete = null;
    private long warning_text = 0L;
    private EditFieldWrapper licence_light = null;
    private EditFieldWrapper licence_dark = null;

    /** Field description */
    public String licenceText = "";
    private String warning_text_store;

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
    public ProfileNewProfile(long _menu, long[] controls, long window, long exitButton, long defaultButton,
                             long okButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, 0L, parent);
        this._menu = _menu;
        this.table = new TableOfElements(_menu, "TABLEGROUP - PROFILE - NEW PROFILE - 2 70");
        this.logo_state = ProfileManagement.getProfileManager().GetCurrentProfileLogo();
        this.panel = new RadioSmartSwitch("..\\data\\config\\menu\\menu_MAIN.xml",
                                          "Tablegroup - ELEMENTS - Profile Panel", loc.getMENUString("LICENSE PLATE"),
                                          this.logo_state, 7, false);
        this.panel.load(_menu);
        this.table.insert(this.panel);
        this.param_values.addParametr("LICENSE PLATE", 0, 0, this.panel);
        this.panel.addListener(this);
        this.warning = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_MAIN.xml",
                                     "Tablegroup - PROFILE -  NEW PROFILE - CONFIRM REPLACE",
                                     "PROFILE -  NEW PROFILE - CONFIRM REPLACE");
        this.warning_text = this.warning.getField("Profile - REPLACE");

        if (this.warning_text != 0L) {
            this.warning_text_store = menues.GetFieldText(this.warning_text);
        }

        this.warning.addListener(this);
        this.info_cannotdelete = new PoPUpMenu(_menu, "..\\data\\config\\menu\\menu_MAIN.xml",
                "Tablegroup - PROFILE -  NEW PROFILE - CANNOT EDIT", "PROFILE -  NEW PROFILE - CANNOT EDIT");
        this.info_cannotdelete.addListener(this);
        this.licence_light = new EditFieldWrapper(_menu, "LICENSE PLATE - TEXT LIGHT");
        this.licence_dark = new EditFieldWrapper(_menu, "LICENSE PLATE - TEXT DARK");
        this.licence_light.addListener(this);
        this.licence_dark.addListener(this);
        this.licenceText = getDefaultLicenceName_prefix();
        this.licence_light.setText(this.licenceText);
        this.licence_light.setSuffix(getDefaultLicenceName_suffix());
        this.licence_dark.setText(this.licenceText);
        this.licence_dark.setSuffix(getDefaultLicenceName_suffix());
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
        this.table.DeInit();
        super.exitMenu();
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        this.table.initTable();
        this.profilename = this.parent.menu.findField("PROFILE NAME - VALUE");
        menues.SetFieldText(this.profilename, ProfileManagement.getProfileManager().GetCurrentProfileName());
        menues.UpdateMenuField(menues.ConvertMenuFields(this.profilename));
        this.warning.afterInit();
        this.info_cannotdelete.afterInit();

        if (this.profilename != 0L) {
            new EditName(this.profilename);
        }

        boolean is_light = isLightPlate(this.logo_state);

        if (is_light) {
            this.licence_light.show();
            this.licence_dark.hide();
        } else {
            this.licence_dark.show();
            this.licence_light.hide();
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void OnOk(long _menu, MENUsimplebutton_field button) {
        menues.ConvertMenuFields(this.profilename);

        String text = menues.GetFieldText(this.profilename);

        if (ProfileManagement.getProfileManager().GetCurrentProfileName().compareToIgnoreCase(text) == 0) {
            if ((this.logo_state == ProfileManagement.getProfileManager().GetCurrentProfileLogo())
                    && (ProfileManagement.getProfileManager().GetCurrentProfileLicensePlateString().compareToIgnoreCase(
                        this.licenceText) == 0)) {
                exitDialog();
            } else {
                this.info_cannotdelete.show();
            }
        } else if (ProfileManagement.getProfileManager().IsProfileExists(text)) {
            if (this.warning_text != 0L) {
                KeyPair[] keys = new KeyPair[1];

                keys[0] = new KeyPair("PROFILENAME", text);
                menues.SetFieldText(this.warning_text, MacroKit.Parse(this.warning_text_store, keys));
                menues.UpdateMenuField(menues.ConvertMenuFields(this.warning_text));
            }

            this.warning.show();
        } else {
            ProfileManagement.getProfileManager().CreateProfile(text, this.logo_state, this.licenceText);
            super.OnOk(_menu, null);
            StartMenu.getCurrentObject().update_profile_name();
            StartMenu.getCurrentObject().restoreProfileValuesToMenu();
            exitDialog();
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void valueChanged() {
        this.logo_state = this.panel.changeValue();

        boolean is_light = isLightPlate(this.logo_state);

        if (is_light) {
            this.licence_light.show();
            this.licence_dark.hide();
        } else {
            this.licence_dark.show();
            this.licence_light.hide();
        }
    }

    @Override
    protected void onShow(boolean value) {
        if (value) {
            if (this.profilename != 0L) {
                menues.SetFieldText(this.profilename, ProfileManagement.getProfileManager().GetCurrentProfileName());
                menues.UpdateMenuField(menues.ConvertMenuFields(this.profilename));
            }

            this.licenceText = getDefaultLicenceName_prefix();

            if ((this.licence_light != null) && (this.licenceText != null)) {
                this.licence_light.setText(this.licenceText);
                this.licence_light.setSuffix(getDefaultLicenceName_suffix());
            }

            if ((this.licence_light != null) && (this.licenceText != null)) {
                this.licence_dark.setText(this.licenceText);
                this.licence_dark.setSuffix(getDefaultLicenceName_suffix());
            }

            if (this.panel != null) {
                this.logo_state = ProfileManagement.getProfileManager().GetCurrentProfileLogo();
                this.panel.reciveValue(this.logo_state);
            }
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void onAgreeclose() {
        menues.ConvertMenuFields(this.profilename);

        String text = menues.GetFieldText(this.profilename);

        if (ProfileManagement.getProfileManager().DeleteProfile(text)) {
            if (ProfileManagement.getProfileManager().CreateProfile(text, this.logo_state, this.licenceText)) {
                ;
            }

            super.OnOk(this._menu, null);
            StartMenu.getCurrentObject().update_profile_name();
            StartMenu.getCurrentObject().restoreProfileValuesToMenu();
            exitDialog();
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
    public void onClose() {}

    /**
     * Method description
     *
     */
    @Override
    public void onOpen() {}

    static boolean isLightPlate(int logo_state) {
        return ((logo_state & 0x4) != 0);
    }

    private String getDefaultLicenceName_prefix() {
        String res = ProfileManagement.getProfileManager().GetCurrentProfileLicensePlateString();

        if (null == res) {
            res = "none";
        }

        return res.toUpperCase();
    }

    private String getDefaultLicenceName_suffix() {
        String res = ProfileManagement.getProfileManager().GetDefaultLicensePlateSuffix();

        if (null == res) {
            res = "503";
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param text
     */
    @Override
    public void textDismissed(String text) {
        this.licenceText = text;
        this.licence_light.setText(this.licenceText);
        this.licence_dark.setText(this.licenceText);
    }

    /**
     * Method description
     *
     *
     * @param text
     */
    @Override
    public void textEntered(String text) {
        this.licenceText = text;
        this.licence_light.setText(this.licenceText);
        this.licence_dark.setText(this.licenceText);
    }

    class EditName {
        private final String METHOD_CHANGENAME = "changeProfileName";
        private final String METHOD_DISSMIS = "dissmisProfileName";
        String lastProfileName;
        String pendProfileName;

        EditName(long control) {
            Object obj = menues.ConvertMenuFields(control);

            menues.SetScriptOnControl(ProfileNewProfile.this._menu, obj, this, "dissmisProfileName", 19L);
            menues.SetScriptOnControl(ProfileNewProfile.this._menu, obj, this, "changeProfileName", 16L);
            this.lastProfileName = menues.GetFieldText(control);
        }

        void dissmisProfileName(long _menu, MENUEditBox obj) {
            this.pendProfileName = menues.GetFieldText(obj.nativePointer);

            if (this.pendProfileName.compareTo("") == 0) {
                obj.text = this.lastProfileName;
                menues.UpdateMenuField(obj);
            }
        }

        void changeProfileName(long _menu, MENUEditBox obj) {
            menues.ConvertMenuFields(obj.nativePointer);
            this.pendProfileName = menues.GetFieldText(obj.nativePointer);

            if (this.pendProfileName.compareTo("") == 0) {
                menues.SetFieldText(obj.nativePointer, this.lastProfileName);
                obj.text = this.lastProfileName;
                menues.UpdateMenuField(obj);
            } else {
                this.lastProfileName = this.pendProfileName;
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/26
