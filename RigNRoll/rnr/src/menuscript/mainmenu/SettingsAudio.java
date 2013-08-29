/*
 * @(#)SettingsAudio.java   13/08/26
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
import rnr.src.menu.SliderGroupRadioButtons;
import rnr.src.menu.TableOfElements;
import rnr.src.menuscript.parametrs.IParametr;
import rnr.src.menuscript.parametrs.ParametrsBlock;
import rnr.src.rnrcore.Modifier;
import rnr.src.rnrcore.loc;

//~--- JDK imports ------------------------------------------------------------

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class SettingsAudio extends PanelDialog implements IValueChanged {
    static String TABLE_DEVICE = "TABLEGROUP - SETTINGS - AUDIO - 1 60";
    static String TABLE_SETTINGS = "TABLEGROUP - SETTINGS - AUDIO - 10 60";
    private static final String LISTOFALTERANTIVESGROUP = "Tablegroup - ELEMENTS - List Special";
    private static final String[] SETTINGSTITLES = { "SETTINGS SOUND QUALITY", "SETTINGS EFFECTS VOLUME",
            "SETTINGS VEHICLE VOLUME", "SETTINGS SPEECH VOLUME", "SETTINGS MUSIC VOLUME" };
    private static final TreeSet<String> orderedSettingsTitles = new TreeSet();
    private static final String DEVICETITLE = "CURRENT AUDIO DEVICE";
    private static final int NUM_SETTINGS = 5;
    private static final String TITLE;

    static {
        orderedSettingsTitles.addAll(Arrays.asList(SETTINGSTITLES));
        TITLE = loc.getMENUString("common\\SETTINGS FOR:");
    }

    private TableOfElements table_device = null;
    private TableOfElements[] table_settings = null;
    private ListOfAlternatives devices = null;
    private SliderGroupRadioButtons[][] settings = null;
    @SuppressWarnings({ "unchecked", "rawtypes" })
    Vector<String> devises_str_ascii = new Vector();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    Vector<String> devises_str_unicode = new Vector();
    private String currentDeviceName = null;
    private int current_device = 0;
    private boolean bSilent = false;

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
    public SettingsAudio(long _menu, long[] controls, long window, long exitButton, long defaultButton, long okButton,
                         long applyButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, applyButton, parent);
        JavaEvents.SendEvent(18, -1, this);

        String[] str_devises = _getDevicesASCII();

        this.table_settings = new TableOfElements[str_devises.length];
        this.settings = new SliderGroupRadioButtons[str_devises.length][5];

        for (int dev = 0; dev < str_devises.length; ++dev) {
            this.table_settings[dev] = new TableOfElements(_menu, TABLE_SETTINGS);

            for (int i = 0; i < 5; ++i) {
                this.settings[dev][i] = CA.createSliderRadioButtons(loc.getMENUString(SETTINGSTITLES[i]), 0, 100, 0,
                        true);
            }

            for (int i = 0; i < 5; ++i) {
                this.settings[dev][i].load(_menu);
            }

            for (int i = 0; i < 5; ++i) {
                this.table_settings[dev].insert(this.settings[dev][i]);
            }

            for (int i = 0; i < 5; ++i) {
                this.param_values.addParametr(SETTINGSTITLES[i] + str_devises[dev], 0, 0, this.settings[dev][i]);
            }
        }

        this.table_device = new TableOfElements(_menu, TABLE_DEVICE);
        this.devices = new ListOfAlternatives(parent.menu.XML_FILE, "Tablegroup - ELEMENTS - List Special", TITLE,
                _getDevicesUnicode(), false);
        this.devices.load(_menu);
        this.table_device.insert(this.devices);
        this.param_values.addParametr("CURRENT AUDIO DEVICE", this.current_device, this.current_device, this.devices);
        this.devices.addListener(this);
        JavaEvents.SendEvent(18, 0, this.param_values);
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
        for (TableOfElements table_setting : this.table_settings) {
            table_setting.DeInit();
        }

        this.table_device.DeInit();
        super.exitMenu();
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        for (TableOfElements table_setting : this.table_settings) {
            table_setting.initTable();
        }

        this.table_device.initTable();
        super.afterInit();
        valueChanged();
        JavaEvents.SendEvent(19, 2, this);
        this.param_values.onUpdate();

        for (int dev = 0; dev < this.devises_str_ascii.size(); ++dev) {
            for (int i = 0; i < 5; ++i) {
                this.settings[dev][i].addListener(new ListenerParamsChanger());
            }
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
    public void OnExit(long _menu, MENUsimplebutton_field button) {
        JavaEvents.SendEvent(19, 4, this);
        readParamValues();
        super.OnExit(_menu, button);
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
        super.OnOk(_menu, button);
        JavaEvents.SendEvent(19, 0, this.param_values);
        JavaEvents.SendEvent(19, 1, this);
        JavaEvents.SendEvent(19, 2, this);
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
        super.OnApply(_menu, button);
        JavaEvents.SendEvent(19, 0, this.param_values);
        JavaEvents.SendEvent(19, 1, this);
        JavaEvents.SendEvent(19, 2, this);
    }

    private String[] _getDevicesASCII() {
        String[] res = new String[this.devises_str_ascii.size()];
        Iterator iter = this.devises_str_ascii.iterator();
        int counter = 0;

        while (iter.hasNext()) {
            res[(counter++)] = ((String) iter.next());
        }

        return res;
    }

    private String[] _getDevicesUnicode() {
        String[] res = new String[this.devises_str_unicode.size()];
        Iterator iter = this.devises_str_unicode.iterator();
        int counter = 0;

        while (iter.hasNext()) {
            res[(counter++)] = ((String) iter.next());
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    @Override
    public void OnDefault(long _menu, MENUsimplebutton_field button) {
        ((Object) this.param_values).visitAllParameters(new Modifier() {
            public void modify(Map.Entry<String, IParametr> victim) {
                if ((null == victim) || ("CURRENT AUDIO DEVICE".equals(victim.getKey()))
                        || (null == victim.getValue())) {
                    return;
                }

                int firstEntrance = victim.getKey().indexOf(SettingsAudio.this.currentDeviceName);

                if ((0 >= firstEntrance)
                        || (!(SettingsAudio.orderedSettingsTitles.contains(victim.getKey().substring(0,
                            firstEntrance))))) {
                    return;
                }

                victim.getValue().makeDefault();
            }
        });
    }

    /**
     * Method description
     *
     */
    @Override
    public void valueChanged() {
        this.current_device = this.devices.getValue();
        this.currentDeviceName = (this.devises_str_ascii.get(this.current_device));

        for (int i = 0; i < this.table_settings.length; ++i) {
            if (i == this.current_device) {
                continue;
            }

            this.table_settings[i].hideTable();
        }

        this.table_settings[this.current_device].showTable();

        if (this.bSilent) {
            return;
        }

        JavaEvents.SendEvent(19, 1, this);
        JavaEvents.SendEvent(19, 5, this);
    }

    /**
     * Method description
     *
     */
    @Override
    public void readParamValues() {
        JavaEvents.SendEvent(18, 1, this);
        JavaEvents.SendEvent(18, 2, this.param_values);
        this.bSilent = true;
        this.devices.setValue(this.current_device);
        this.param_values.setIntegerValue("CURRENT AUDIO DEVICE", this.current_device);
        this.param_values.onUpdate();
        valueChanged();
        this.bSilent = false;
    }

    class ListenerParamsChanger implements IValueChanged {

        /**
         * Method description
         *
         */
        @Override
        public void valueChanged() {
            if (SettingsAudio.this.bSilent) {
                return;
            }

            SettingsAudio.this.param_values.onOk();
            JavaEvents.SendEvent(19, 0, SettingsAudio.this.param_values);
            JavaEvents.SendEvent(19, 3, this);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
