/*
 * @(#)SettingsVideo.java   13/08/26
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
import rnr.src.menu.JavaEvents;
import rnr.src.menu.ListOfAlternatives;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.RadioGroupSmartSwitch;
import rnr.src.menu.SliderGroupRadioButtons;
import rnr.src.menu.TableOfElements;
import rnr.src.menu.menues;
import rnr.src.menuscript.IPoPUpMenuListener;
import rnr.src.menuscript.PoPUpMenu;
import rnr.src.menuscript.parametrs.BlockMemo;
import rnr.src.menuscript.parametrs.ParametrsBlock;
import rnr.src.rnrcore.loc;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class SettingsVideo extends PanelDialog implements IValueChanged, IPoPUpMenuListener {
    private static final String TITLE = loc.getMENUString("common\\SETTINGS FOR:");
    protected static String TABLE_DEVICE = "TABLEGROUP - SETTINGS - VIDEO - 1 60";
    protected static String TABLE_SETTINGS = "TABLEGROUP - SETTINGS - VIDEO - 10 60";
    protected static String RANGER = "Tableranger - SETTINGS - VIDEO";
    protected static String WARNING = "Tablegroup - SETTINGS - VIDEO - CONFIRM MESSAGE";
    protected static String WARNING_WND = "SETTINGS - VIDEO - CONFIRM MESSAGE";
    private static final String LISTOFALTERANTIVESGROUP = "Tablegroup - ELEMENTS - List Special";
    private static final String DEVICETITLE = "CURRENT VIDEO DEVICE";
    private static final int NUM_SETTINGS = 13;
    private static final int RESOLUTION_SETTING_NOM = 0;
    private static final int AA_SETTING_NOM = 1;
    private static final int tip_list_resolutions = 1;
    private static final int tip_slider = 2;
    private static final int tip_switch = 3;
    private static final int[] settings_tips = {
        1, 1, 2, 2, 3, 2, 2, 3, 2, 3, 2, 2, 3
    };
    private static final String[] settings_titles = {
        "SETTINGS OPTION RESOLUTION", "SETTINGS OPTION ANTIALIASING", "SETTINGS OPTION FILTERING",
        "SETTINGS OPTION SHADOWS", "SETTINGS OPTION 3DCLOUDS", "SETTINGS OPTION VISIBILITYRANGE",
        "SETTINGS OPTION VEHICLEDETAIL", "SETTINGS OPTION DROPS", "SETTINGS OPTION HDR", "SETTINGS OPTION MOTIONBLUR",
        "SETTINGS OPTION REFLECTIONDETAILS", "SETTINGS OPTION REFLECTIONUPDATERATE", "SETTINGS OPTION LIGHTMAPS"
    };
    protected static boolean res_changed_menu = false;
    protected static resolution lastResolution = null;
    protected static int lastAliasing = 0;
    protected static BlockMemo lastParams = null;
    protected static boolean applyLastParams = false;

    /** Field description */
    public int[] settings_canchage = {
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
    };
    private TableOfElements table_settings = null;
    private TableOfElements table_device = null;
    private ListOfAlternatives devices = null;
    private ListOfAlternatives resolutionsList = null;
    private ListOfAlternatives aliasingList = null;
    protected PoPUpMenu warning = null;
    private long _menu = 0L;
    private boolean inEvent = true;
    Vector<String> devises_str = new Vector();
    int current_device = 0;
    Vector good_supportedResolutions = new Vector();
    resolution currentResolution = new resolution();
    resolution in_currentResolution = new resolution();
    resolution confirmedResolution = new resolution();
    resolution defaultResolution = new resolution();
    Vector good_supportedAntialiasing = new Vector();
    int currentAliasing = 0;
    int in_currentAliasing = 0;
    int confirmedAliasing = 0;
    int defaultAliasing = 0;
    protected boolean res_changed = false;
    protected boolean aliasing_changed = false;

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
     * @param applyButton
     * @param parent
     */
    public SettingsVideo(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton,
                         long applyButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, applyButton, parent);
        this._menu = _menu;
        JavaEvents.SendEvent(20, -1, this);

        String[] str_devises = getDevices();

        this.res_changed = false;
        this.aliasing_changed = false;

        int dev = this.current_device;

        this.table_settings = new TableOfElements(_menu, TABLE_SETTINGS, RANGER);
        this.confirmedResolution = this.currentResolution;
        this.confirmedAliasing = this.currentAliasing;

        int curres = findResolution(this.currentResolution);
        int default_res = findResolution(this.defaultResolution);
        int cur_alias = this.currentAliasing;
        int default_alias = this.defaultAliasing;

        for (int i = 0; i < 13; ++i) {
            switch (settings_tips[i]) {
             case 1 :
                 if (i == 0) {
                     ListOfAlternatives lst = CA.createListOfAlternatives(loc.getMENUString(settings_titles[i]),
                                                  getResolutionsList(), this.settings_canchage[i] == 1);

                     this.resolutionsList = lst;
                     lst.load(_menu);
                     this.param_values.addParametr(settings_titles[i] + str_devises[dev], curres, default_res, lst);
                     this.table_settings.insert(lst);
                     lst.addListener(new ListenerResolutionChanger(this));
                 } else if (i == 1) {
                     ListOfAlternatives lst = CA.createListOfAlternatives(loc.getMENUString(settings_titles[i]),
                                                  getAntialiasingList(), this.settings_canchage[i] == 1);

                     this.aliasingList = lst;
                     lst.load(_menu);
                     this.param_values.addParametr(settings_titles[i] + str_devises[dev], cur_alias, default_alias,
                                                   lst);
                     this.table_settings.insert(lst);
                     lst.addListener(new ListenerAliasingChanger(this));
                 }

                 break;

             case 2 :
                 SliderGroupRadioButtons sldr = CA.createSliderRadioButtons(loc.getMENUString(settings_titles[i]), 0,
                                                    100, 0, this.settings_canchage[i] == 1);

                 sldr.load(_menu);
                 this.param_values.addParametr(settings_titles[i] + str_devises[dev], 0, 0, sldr);
                 this.table_settings.insert(sldr);

                 break;

             case 3 :
                 RadioGroupSmartSwitch rgr = CA.createRadioGroupSmartSwitch(loc.getMENUString(settings_titles[i]),
                                                 true, this.settings_canchage[i] == 1);

                 rgr.load(_menu);
                 this.param_values.addParametr(settings_titles[i] + str_devises[dev], false, false, rgr);
                 this.table_settings.insert(rgr);
            }
        }

        this.table_device = new TableOfElements(_menu, TABLE_DEVICE);
        this.devices = new ListOfAlternatives(parent.menu.XML_FILE, "Tablegroup - ELEMENTS - List Special", TITLE,
                str_devises, false);
        this.devices.load(_menu);
        this.table_device.insert(this.devices);
        this.param_values.addParametr("CURRENT VIDEO DEVICE", this.current_device, this.current_device, this.devices);
        this.devices.addListener(this);
        JavaEvents.SendEvent(20, 0, this.param_values);
        this.currentAliasing = this.param_values.getIntegerValue(settings_titles[1] + str_devises[dev]);
        this.warning = new PopUpSelfClose(_menu, parent.menu.XML_FILE, WARNING, WARNING_WND);
        this.warning.addListener(this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
        this.table_settings.DeInit();
        this.table_device.DeInit();
        super.exitMenu();
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        this.inEvent = true;
        this.table_settings.initTable();
        this.table_device.initTable();
        this.warning.afterInit();
        super.afterInit();

        if (res_changed_menu) {
            this.warning.show();
            res_changed_menu = false;
        }

        this.inEvent = false;
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
        if ((this.res_changed) || (this.aliasing_changed)) {
            lastResolution = this.confirmedResolution;
            this.confirmedResolution = this.currentResolution;
            lastAliasing = this.confirmedAliasing;
            this.confirmedAliasing = this.currentAliasing;
            this.param_values.onOk();
            lastParams.recordChanges(this.param_values);
            JavaEvents.SendEvent(21, -1, this);
            JavaEvents.SendEvent(21, 0, this.param_values);

            if (this.res_changed) {
                StartMenu.menuNeedRestoreLastState();
                menues.CallMenuCallBack_ExitMenu(_menu);
            }

            res_changed_menu = this.res_changed;
            this.res_changed = false;
            this.aliasing_changed = false;

            return;
        }

        this.param_values.onOk();
        lastParams = null;
        JavaEvents.SendEvent(21, -1, this);
        JavaEvents.SendEvent(21, 0, this.param_values);
        exitDialog();
        this.res_changed = false;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void OnApply(long _menu, MENUsimplebutton_field button) {
        if ((this.res_changed) || (this.aliasing_changed)) {
            lastResolution = this.confirmedResolution;
            this.confirmedResolution = this.currentResolution;
            lastAliasing = this.confirmedAliasing;
            this.confirmedAliasing = this.currentAliasing;
            this.param_values.onOk();
            lastParams.recordChanges(this.param_values);
            JavaEvents.SendEvent(21, -1, this);
            JavaEvents.SendEvent(21, 0, this.param_values);

            if (this.res_changed) {
                StartMenu.menuNeedRestoreLastState();
                menues.CallMenuCallBack_ExitMenu(_menu);
            }

            this.aliasing_changed = false;
            res_changed_menu = this.res_changed;
            this.res_changed = false;

            return;
        }

        this.param_values.onOk();
        lastParams = null;
        JavaEvents.SendEvent(21, -1, this);
        JavaEvents.SendEvent(21, 0, this.param_values);
        this.res_changed = false;
    }

    /**
     * Method description
     *
     */
    @Override
    public void update() {
        this.inEvent = true;

        if (applyLastParams) {
            lastParams.restoreChanges(this.param_values);
            lastParams = null;
            applyLastParams = false;

            int dev = this.current_device;
            String[] str_devises = getDevices();
            int curres = findResolution(this.confirmedResolution);

            this.param_values.setIntegerValue(settings_titles[0] + str_devises[dev], curres);
            this.resolutionsList.setValue(curres);
            this.param_values.setIntegerValue(settings_titles[1] + str_devises[dev], this.confirmedAliasing);
            this.aliasingList.setValue(this.confirmedAliasing);
        } else {
            super.update();
        }

        if (null == lastParams) {
            lastParams = new BlockMemo(this.param_values);
        }

        this.inEvent = false;
    }

    private String[] getDevices() {
        String[] res = new String[this.devises_str.size()];
        Iterator iter = this.devises_str.iterator();
        int counter = 0;

        while (iter.hasNext()) {
            res[(counter++)] = ((String) iter.next());
        }

        return res;
    }

    /**
     * Method description
     *
     */
    @Override
    public void valueChanged() {
        this.current_device = this.devices.getValue();
        JavaEvents.SendEvent(22, 0, this.param_values);
    }

    private int findResolution(resolution res_to_find) {
        if (this.good_supportedResolutions.isEmpty()) {
            return -1;
        }

        int res = 0;
        Iterator iter = this.good_supportedResolutions.iterator();

        while (iter.hasNext()) {
            if (((resolution) iter.next()).isSame(res_to_find)) {
                return res;
            }

            ++res;
        }

        return -1;
    }

    private String[] getResolutionsList() {
        String[] res = new String[this.good_supportedResolutions.size()];

        if (this.good_supportedResolutions.isEmpty()) {
            return res;
        }

        int count = 0;
        Iterator iter = this.good_supportedResolutions.iterator();

        while (iter.hasNext()) {
            res[(count++)] = ((resolution) iter.next()).to_str();
        }

        return res;
    }

    private String[] getAntialiasingList() {
        String[] res = new String[this.good_supportedAntialiasing.size()];

        if (this.good_supportedAntialiasing.isEmpty()) {
            return res;
        }

        int count = 0;
        Iterator iter = this.good_supportedAntialiasing.iterator();

        while (iter.hasNext()) {
            res[(count++)] = ((antialisaing) iter.next()).to_str();
        }

        return res;
    }

    /**
     * Method description
     *
     */
    @Override
    public void onAgreeclose() {
        lastResolution = null;
    }

    /**
     * Method description
     *
     */
    @Override
    public void onClose() {
        this.res_changed = true;
        this.aliasing_changed = true;
        this.confirmedResolution = lastResolution;
        this.currentResolution = lastResolution;
        this.confirmedAliasing = lastAliasing;
        this.currentAliasing = lastAliasing;
        applyLastParams = true;
        lastParams.restore(this.param_values);
        JavaEvents.SendEvent(21, -1, this);
        JavaEvents.SendEvent(21, 0, this.param_values);
        StartMenu.menuNeedRestoreLastState();
        menues.CallMenuCallBack_ExitMenu(this._menu);
        this.res_changed = false;
        this.aliasing_changed = false;
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

    class ListenerAliasingChanger implements IValueChanged {
        SettingsVideo parent = null;

        ListenerAliasingChanger(SettingsVideo paramSettingsVideo) {
            this.parent = paramSettingsVideo;
        }

        /**
         * Method description
         *
         */
        @Override
        public void valueChanged() {
            SettingsVideo.this.currentAliasing = SettingsVideo.this.aliasingList.getValue();
            SettingsVideo.this.aliasing_changed = (SettingsVideo.this.confirmedAliasing
                    != SettingsVideo.this.currentAliasing);

            if (!(SettingsVideo.this.inEvent)) {
                SettingsVideo.access$102(SettingsVideo.this, true);

                int dev = SettingsVideo.this.current_device;
                String[] str_devises = SettingsVideo.this.getDevices();

                SettingsVideo.this.in_currentAliasing = SettingsVideo.this.currentAliasing;
                SettingsVideo.this.in_currentResolution.x = SettingsVideo.this.currentResolution.x;
                SettingsVideo.this.in_currentResolution.y = SettingsVideo.this.currentResolution.y;
                SettingsVideo.this.in_currentResolution.bits = SettingsVideo.this.currentResolution.bits;
                JavaEvents.SendEvent(21, 1, this.parent);

                int curres = SettingsVideo.this.findResolution(SettingsVideo.this.in_currentResolution);

                SettingsVideo.this.res_changed =
                    (!(SettingsVideo.this.confirmedResolution.isSame(SettingsVideo.this.in_currentResolution)));
                SettingsVideo.this.currentResolution =
                    ((SettingsVideo.resolution) SettingsVideo.this.good_supportedResolutions.get(curres));
                SettingsVideo.this.param_values.setIntegerValueChange(SettingsVideo.settings_titles[0]
                        + str_devises[dev], curres);
                SettingsVideo.access$102(SettingsVideo.this, false);
            }
        }
    }


    class ListenerResolutionChanger implements IValueChanged {
        SettingsVideo parent = null;

        ListenerResolutionChanger(SettingsVideo paramSettingsVideo) {
            this.parent = paramSettingsVideo;
        }

        /**
         * Method description
         *
         */
        @Override
        public void valueChanged() {
            SettingsVideo.this.currentResolution =
                ((SettingsVideo.resolution) SettingsVideo.this.good_supportedResolutions.get(
                    SettingsVideo.this.resolutionsList.getValue()));
            SettingsVideo.this.res_changed =
                (!(SettingsVideo.this.confirmedResolution.isSame(SettingsVideo.this.currentResolution)));

            if (!(SettingsVideo.this.inEvent)) {
                SettingsVideo.access$102(SettingsVideo.this, true);

                int dev = SettingsVideo.this.current_device;
                String[] str_devises = SettingsVideo.this.getDevices();

                SettingsVideo.this.in_currentAliasing = SettingsVideo.this.currentAliasing;
                JavaEvents.SendEvent(21, 2, this.parent);
                SettingsVideo.this.currentAliasing = SettingsVideo.this.in_currentAliasing;
                SettingsVideo.this.aliasing_changed = (SettingsVideo.this.confirmedAliasing
                        != SettingsVideo.this.currentAliasing);
                SettingsVideo.this.param_values.setIntegerValueChange(SettingsVideo.settings_titles[1]
                        + str_devises[dev], SettingsVideo.this.currentAliasing);
                SettingsVideo.access$102(SettingsVideo.this, false);
            }
        }
    }


    static class antialisaing {
        int num_of_samples;

        antialisaing() {
            this.num_of_samples = 0;
        }

        String to_str() {
            return loc.getMENUString("AA_" + this.num_of_samples + "x");
        }
    }


    static class resolution {
        int x;
        int y;
        int bits;
        String format;

        String to_str() {
            return "" + this.x + "x" + this.y + " (" + this.format + ")";
        }

        boolean isSame(resolution value) {
            return ((value.x == this.x) && (value.y == this.y) && (value.bits == this.bits));
        }
    }
}


//~ Formatted in DD Std on 13/08/26
