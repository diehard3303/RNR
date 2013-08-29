/*
 * @(#)SettingsControls.java   13/08/26
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

import rnr.src.menu.ContainerTextTitleTextValue;
import rnr.src.menu.IValueChanged;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.ListOfAlternatives;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.RadioGroupSmartSwitch;
import rnr.src.menu.SliderGroupRadioButtons;
import rnr.src.menu.TableOfElements;
import rnr.src.menuscript.PoPUpMenu;
import rnr.src.menuscript.keybind.Action;
import rnr.src.menuscript.keybind.Axe;
import rnr.src.menuscript.keybind.Bind;
import rnr.src.menuscript.keybind.Key;
import rnr.src.menuscript.keybind.KeyBindingsData;
import rnr.src.menuscript.mainmenu.RemapControlsTable.WrapTextsAndKeycode;
import rnr.src.menuscript.parametrs.ParametrsBlock;
import rnr.src.rnrcore.loc;

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
public class SettingsControls extends PanelDialog implements IValueChanged {
    private static final String SENCITIVITY_TITLE = "SETTINGS SENCITIVITY TITLE";
    private static final String FORCEFEEDBACK_TITLE = "SETTINGS FORCEFEEDBACK TITLE";
    private static final String CENTERINGFORCE_TITLE = "SETTINGS CENTERINGFORCE TITLE";
    private static final String TRANSMISSION_TITLE = "OPTION TRANSSMITION";
    private static final String INVERTMOUSE_TITLE = "OPTION INVERT MOUSE";
    private static final String[] TRANSMISSION_VALUES = { loc.getMENUString("OPTION TRANSSMITION MANUAL"),
            loc.getMENUString("OPTION TRANSSMITION AUTO") };
    private static final String TITLE = loc.getMENUString("common\\SETTINGS FOR:");
    private static final String LISTOFALTERANTIVESGROUP = "Tablegroup - ELEMENTS - List Special";
    protected static String POPUP = "Tablegroup - SETTINGS - CONTROLS - CONFIRM MESSAGE";
    protected static String POPUPWND = null;
    protected static String TABLE = "TABLEGROUP - SETTINGS - CONTROLS - 1 60";
    protected static String TABLE_SETTINGS = "TABLEGROUP - SETTINGS - CONTROLS - 4 60";
    private static final int tip_list_list = 1;
    private static final int tip_slider = 2;
    private static final int tip_switch = 3;
    private static final String[][] keyboard_alternatives = {
        null, null
    };
    private static final String[][] joystic_alternatives = {
        null, null
    };
    private static final String[][] common_alternatives = {
        TRANSMISSION_VALUES, null
    };
    private static final int[] settings_keyboard_tips = { 2, 2 };
    private static final int[] settings_joystick_tips = { 2, 2 };
    private static final int[] settings_comon_tips = { 1, 3 };
    private static final String[] settings_titles_keyboard = { "SETTINGS SENCITIVITY TITLE",
            "SETTINGS CENTERINGFORCE TITLE" };
    private static final String[] settings_titles_joystick = { "SETTINGS SENCITIVITY TITLE",
            "SETTINGS FORCEFEEDBACK TITLE" };
    private static final String[] settings_titles_common = { "OPTION TRANSSMITION", "OPTION INVERT MOUSE" };
    private TableOfElements table = null;
    private ListOfAlternatives currentDevice = null;
    private TableOfElements[] table_settings = null;
    protected RemapControlsTable remap_table = null;
    Vector<?> m_inputdevices = new Vector<Object>();
    Vector<?> m_default_inputdevices = new Vector<Object>();
    PoPUpMenu warning = null;
    ArrayList[] defaulted_bindings = null;
    ArrayList[] current_bindings = null;

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
    public SettingsControls(long _menu, long[] controls, long window, long exitButton, long defaultButton,
                            long okButton, long applyButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, applyButton, parent);
        JavaEvents.SendEvent(0, 0, this);

        String[] str_devises = getDevices();
        int num_devices = str_devises.length;

        this.table = new TableOfElements(_menu, TABLE);
        this.currentDevice = new ListOfAlternatives(parent.menu.XML_FILE, "Tablegroup - ELEMENTS - List Special",
                TITLE, str_devises, false);
        this.currentDevice.load(_menu);
        this.table.insert(this.currentDevice);
        this.table_settings = new TableOfElements[num_devices];

        for (int i = 0; i < num_devices; ++i) {
            this.table_settings[i] = new TableOfElements(_menu, TABLE_SETTINGS);

            switch (i) {
             case 0 :
                 for (int t = 0; t < settings_keyboard_tips.length; ++t) {
                     switch (settings_keyboard_tips[t]) {
                      case 1 :
                          ListOfAlternatives lst =
                              CA.createListOfAlternatives(loc.getMENUString(settings_titles_keyboard[t]),
                                                          keyboard_alternatives[t], true);

                          lst.load(_menu);
                          this.table_settings[i].insert(lst);

                          if (!(needToDevideBetweenDevices(settings_titles_keyboard[t]))) {
                              this.param_values.addParametr(settings_titles_keyboard[t], 50, 50, lst);
                          } else {
                              this.param_values.addParametr(settings_titles_keyboard[t] + str_devises[i], 50, 50, lst);
                          }

                          break;

                      case 2 :
                          SliderGroupRadioButtons sl =
                              CA.createSliderRadioButtons(loc.getMENUString(settings_titles_keyboard[t]), 0, 100, 50,
                                                          true);

                          sl.load(_menu);
                          this.table_settings[i].insert(sl);

                          if (!(needToDevideBetweenDevices(settings_titles_keyboard[t]))) {
                              this.param_values.addParametr(settings_titles_keyboard[t], 50, 50, sl);
                          } else {
                              this.param_values.addParametr(settings_titles_keyboard[t] + str_devises[i], 50, 50, sl);
                          }

                          break;

                      case 3 :
                          RadioGroupSmartSwitch sw =
                              CA.createRadioGroupSmartSwitch(loc.getMENUString(settings_titles_keyboard[t]), true,
                                                             true);

                          sw.load(_menu);
                          this.table_settings[i].insert(sw);

                          if (!(needToDevideBetweenDevices(settings_titles_keyboard[t]))) {
                              this.param_values.addParametr(settings_titles_keyboard[t], false, false, sw);
                          } else {
                              this.param_values.addParametr(settings_titles_keyboard[t] + str_devises[i], false, false,
                                                            sw);
                          }
                     }
                 }

                 break;

             default :
                 for (int t = 0; t < settings_joystick_tips.length; ++t) {
                     switch (settings_joystick_tips[t]) {
                      case 1 :
                          ListOfAlternatives lst =
                              CA.createListOfAlternatives(loc.getMENUString(settings_titles_joystick[t]),
                                                          joystic_alternatives[t], true);

                          lst.load(_menu);
                          this.table_settings[i].insert(lst);

                          if (!(needToDevideBetweenDevices(settings_titles_joystick[t]))) {
                              this.param_values.addParametr(settings_titles_joystick[t], 50, 50, lst);
                          } else {
                              this.param_values.addParametr(settings_titles_joystick[t] + str_devises[i], 50, 50, lst);
                          }

                          break;

                      case 2 :
                          SliderGroupRadioButtons sl =
                              CA.createSliderRadioButtons(loc.getMENUString(settings_titles_joystick[t]), 0, 100, 50,
                                                          true);

                          sl.load(_menu);
                          this.table_settings[i].insert(sl);

                          if (!(needToDevideBetweenDevices(settings_titles_joystick[t]))) {
                              this.param_values.addParametr(settings_titles_joystick[t], 50, 50, sl);
                          } else {
                              this.param_values.addParametr(settings_titles_joystick[t] + str_devises[i], 50, 50, sl);
                          }

                          break;

                      case 3 :
                          RadioGroupSmartSwitch sw =
                              CA.createRadioGroupSmartSwitch(loc.getMENUString(settings_titles_joystick[t]), true,
                                                             true);

                          sw.load(_menu);
                          this.table_settings[i].insert(sw);

                          if (!(needToDevideBetweenDevices(settings_titles_joystick[t]))) {
                              this.param_values.addParametr(settings_titles_joystick[t], false, false, sw);
                          } else {
                              this.param_values.addParametr(settings_titles_joystick[t] + str_devises[i], false, false,
                                                            sw);
                          }
                     }
                 }
            }
        }

        for (int t = 0; t < settings_comon_tips.length; ++t) {
            switch (settings_comon_tips[t]) {
             case 1 :
                 ListOfAlternatives lst = CA.createListOfAlternatives(loc.getMENUString(settings_titles_common[t]),
                                              common_alternatives[t], true);

                 lst.load(_menu);

                 for (int i = 0; i < num_devices; ++i) {
                     this.table_settings[i].insert(lst);
                 }

                 this.param_values.addParametr(settings_titles_common[t], 50, 50, lst);

                 break;

             case 2 :
                 SliderGroupRadioButtons sl = CA.createSliderRadioButtons(loc.getMENUString(settings_titles_common[t]),
                                                  0, 100, 50, true);

                 sl.load(_menu);

                 for (int i = 0; i < num_devices; ++i) {
                     this.table_settings[i].insert(sl);
                 }

                 this.param_values.addParametr(settings_titles_common[t], 50, 50, sl);

                 break;

             case 3 :
                 RadioGroupSmartSwitch sw =
                     CA.createRadioGroupSmartSwitch(loc.getMENUString(settings_titles_common[t]), true, true);

                 sw.load(_menu);

                 for (int i = 0; i < num_devices; ++i) {
                     this.table_settings[i].insert(sw);
                 }

                 this.param_values.addParametr(settings_titles_common[t], false, false, sw);
            }
        }

        JavaEvents.SendEvent(14, 0, this.param_values);
        this.currentDevice.addListener(this);
        this.remap_table = new RemapControlsTable(_menu, parent);
        this.warning = new PoPUpMenu(_menu, parent.menu.XML_FILE, POPUP, POPUPWND);
        this.warning.addListener(this.remap_table);
        this.remap_table.warning = this.warning;
    }

    private boolean needToDevideBetweenDevices(String name) {
        return (("SETTINGS SENCITIVITY TITLE".compareTo(name) == 0)
                || ("SETTINGS FORCEFEEDBACK TITLE".compareTo(name) == 0));
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
        for (int dev = 0; dev < this.table_settings.length; ++dev) {
            this.table_settings[dev].DeInit();
        }

        this.table.DeInit();
        this.remap_table.deinit();
        super.exitMenu();
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        super.afterInit();
        this.warning.afterInit();
        this.table.initTable();

        for (int i = 0; i < this.table_settings.length; ++i) {
            this.table_settings[i].initTable();
        }

        this.remap_table.afterInit();
    }

    /**
     * Method description
     *
     */
    @Override
    public void update() {
        if (null != this.current_bindings) {
            for (int i = 0; i < this.current_bindings.length; ++i) {
                this.current_bindings[i] = null;
            }
        }

        JavaEvents.SendEvent(14, 0, this.param_values);
        this.param_values.onUpdate();

        int cur_device = this.currentDevice.getValue();

        this.remap_table.fillTable(formDeviceBindingsData(cur_device), cur_device);

        for (int i = 0; i < this.table_settings.length; ++i) {
            if (i == cur_device) {
                continue;
            }

            this.table_settings[i].hideTable();
        }

        this.table_settings[cur_device].showTable();
    }

    /**
     * Method description
     *
     */
    @Override
    public void readParamValues() {
        JavaEvents.SendEvent(0, 0, this);
        update();
    }

    private String[] getDevices() {
        String[] res = new String[this.m_inputdevices.size()];
        Iterator<?> iter = this.m_inputdevices.iterator();
        int i = 0;

        while (iter.hasNext()) {
            res[(i++)] = ((KeyBindingsData) iter.next()).getDeviceName();
        }

        return res;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    ArrayList<RemapControlsTable.WrapTextsAndKeycode> formDeviceBindingsData(int num_device) {
        if (null == this.current_bindings) {
            this.current_bindings = new ArrayList[this.m_inputdevices.size()];
        }

        if (null != this.current_bindings[num_device]) {
            return this.current_bindings[num_device];
        }

        ArrayList<WrapTextsAndKeycode> ret = new ArrayList<WrapTextsAndKeycode>();
        Object device = this.m_inputdevices.get(num_device);

        if (device == null) {
            return ret;
        }

        KeyBindingsData data = (KeyBindingsData) device;
        Iterator<?> iter = data.getIterator();

        while (iter.hasNext()) {
            Bind bind = (Bind) iter.next();
            String actionName = bind.action.getActionname();
            String codeName = (bind.axe != null)
                              ? bind.axe.getAxename()
                              : (bind.key != null)
                                ? bind.key.getKeyname()
                                : "";
            RemapControlsTable.WrapTextsAndKeycode item = new RemapControlsTable.WrapTextsAndKeycode();

            item.texts = new ContainerTextTitleTextValue(actionName, codeName);
            item.bind = bind;
            ret.add(item);
        }

        data.freeIterator();
        this.current_bindings[num_device] = ret;

        if (null == this.defaulted_bindings) {
            this.defaulted_bindings = new ArrayList[this.m_default_inputdevices.size()];
        }

        if (null != this.defaulted_bindings[num_device]) {
            return this.current_bindings[num_device];
        }

        ArrayList<WrapTextsAndKeycode> res = new ArrayList<WrapTextsAndKeycode>();

        device = this.m_default_inputdevices.get(num_device);

        if (device == null) {
            return res;
        }

        data = (KeyBindingsData) device;
        iter = data.getIterator();

        while (iter.hasNext()) {
            Bind bind = (Bind) iter.next();
            String actionName = bind.action.getActionname();
            String codeName = (bind.axe != null)
                              ? bind.axe.getAxename()
                              : (bind.key != null)
                                ? bind.key.getKeyname()
                                : "";
            RemapControlsTable.WrapTextsAndKeycode item = new RemapControlsTable.WrapTextsAndKeycode();

            item.texts = new ContainerTextTitleTextValue(actionName, codeName);
            item.bind = bind;
            res.add(item);
        }

        data.freeIterator();
        this.defaulted_bindings[num_device] = res;

        return ret;
    }

    private ArrayList<RemapControlsTable.WrapTextsAndKeycode> clone(
            ArrayList<RemapControlsTable.WrapTextsAndKeycode> data) {
        ArrayList<WrapTextsAndKeycode> ndata = new ArrayList<WrapTextsAndKeycode>();
        Iterator<WrapTextsAndKeycode> iter = data.iterator();

        while (iter.hasNext()) {
            ndata.add(iter.next().clone());
        }

        return ndata;
    }

    void updateNewBindings(int num_device) {
        if ((null == this.current_bindings) || (null == this.current_bindings[num_device])) {
            return;
        }

        Object device = this.m_inputdevices.get(num_device);
        KeyBindingsData data = (KeyBindingsData) device;
        ArrayList<?> current_binds = this.current_bindings[num_device];
        Iterator<?> iter = current_binds.iterator();

        while (iter.hasNext()) {
            RemapControlsTable.WrapTextsAndKeycode wrap = (RemapControlsTable.WrapTextsAndKeycode) iter.next();

            data.updateBind(wrap.bind);
        }
    }

    /**
     * Method description
     *
     *
     * @param menu
     * @param button
     */
    @Override
    public void OnOk(long menu, MENUsimplebutton_field button) {
        int sz = this.m_inputdevices.size();

        for (int i = 0; i < sz; ++i) {
            updateNewBindings(i);
        }

        this.param_values.onOk();
        JavaEvents.SendEvent(15, 0, this.param_values);
        JavaEvents.SendEvent(1, 0, this);
        exitDialog();
    }

    /**
     * Method description
     *
     *
     * @param menu
     * @param button
     */
    @Override
    public void OnApply(long menu, MENUsimplebutton_field button) {
        int sz = this.m_inputdevices.size();

        for (int i = 0; i < sz; ++i) {
            updateNewBindings(i);
        }

        this.param_values.onOk();
        JavaEvents.SendEvent(15, 0, this.param_values);
        JavaEvents.SendEvent(1, 0, this);
    }

    /**
     * Method description
     *
     *
     * @param menu
     * @param button
     */
    @Override
    public void OnDefault(long menu, MENUsimplebutton_field button) {
        for (int i = 0; i < this.defaulted_bindings.length; ++i) {
            if (null != this.defaulted_bindings[i]) {
                this.current_bindings[i] = clone(this.defaulted_bindings[i]);
            }
        }

        this.param_values.onDefault();

        int cur_device = this.currentDevice.getValue();

        this.remap_table.fillTable(formDeviceBindingsData(cur_device), cur_device);
    }

    /**
     * Method description
     *
     */
    @Override
    public void valueChanged() {
        this.remap_table.leaveRemaping();

        int cur_device = this.currentDevice.getValue();

        this.remap_table.fillTable(formDeviceBindingsData(cur_device), cur_device);

        for (int i = 0; i < this.table_settings.length; ++i) {
            if (i == cur_device) {
                continue;
            }

            this.table_settings[i].hideTable();
        }

        this.table_settings[cur_device].showTable();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean areValuesChanged() {
        if (super.areValuesChanged()) {
            return true;
        }

        for (int i = 0; i < this.m_inputdevices.size(); ++i) {
            if (keybindingsChanged(i)) {
                return true;
            }
        }

        return false;
    }

    private boolean keybindingsChanged(int num_device) {
        return false;
    }
}


//~ Formatted in DD Std on 13/08/26
