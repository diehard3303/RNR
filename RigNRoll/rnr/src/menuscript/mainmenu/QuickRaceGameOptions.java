/*
 * @(#)QuickRaceGameOptions.java   13/08/26
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

import rnr.src.menu.JavaEvents;
import rnr.src.menu.ListOfAlternatives;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.RadioGroupSmartSwitch;
import rnr.src.menu.SliderGroupRadioButtons;
import rnr.src.menu.TableOfElements;
import rnr.src.menu.menues;
import rnr.src.rnrcore.loc;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class QuickRaceGameOptions extends PanelDialog {
    private static final String OK = "QUICK RACE - GAME OPTIONS OK";
    private static final String DEFAULT = "QUICK RACE - GAME OPTIONS DEFAULT";
    private static final String OKMETHOD = "OnOk";
    private static final String DEFAULTMETHOD = "OnDefault";
    protected static String TABLE = "TABLEGROUP - QUICK RACE - GAME OPTIONS - 11 60";
    protected static String RANGER = "Tableranger - QUICK RACE - GAME OPTIONS";
    private static final String TITLE_TRAFFIC = "TRAFFIC LEVEL";
    private static final String TITLE_CUTSCENES = "OPTION CUTSCENES";
    private static final String TITLE_MAPORIENTATION = "OPTION MAPORIENTATION";
    private static final String[] MAPORIENTATIONS = { loc.getMENUString("ORIENTATION FORWARD"),
            loc.getMENUString("ORIENTATION NORTH") };
    TableOfElements table = null;
    SliderGroupRadioButtons trafficLevel = null;
    RadioGroupSmartSwitch cutscenes = null;

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
    public QuickRaceGameOptions(long _menu, long[] controls, long window, long exitButton, long defaultButton,
                                long okButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, 0L, parent);
        this.table = new TableOfElements(_menu, TABLE, RANGER);

        SliderGroupRadioButtons trafficLevel = CA.createSliderRadioButtons(loc.getMENUString("TRAFFIC LEVEL"), 0, 100,
                                                   50, true);
        RadioGroupSmartSwitch cutscenes = CA.createRadioGroupSmartSwitch(loc.getMENUString("OPTION CUTSCENES"), true,
                                              true);
        ListOfAlternatives map_orientations = CA.createListOfAlternatives(loc.getMENUString("OPTION MAPORIENTATION"),
                                                  MAPORIENTATIONS, true);

        trafficLevel.load(_menu);
        cutscenes.load(_menu);
        map_orientations.load(_menu);
        this.table.insert(trafficLevel);
        this.table.insert(cutscenes);
        this.table.insert(map_orientations);
        this.param_values.addParametr("QUICK RACE TRAFFIC", 50, 50, trafficLevel);
        this.param_values.addParametr("QUICK RACE CUTSCENES", true, true, cutscenes);
        this.param_values.addParametr("QUICK RACE MAPORIENTATION", 0, 0, map_orientations);
        JavaEvents.SendEvent(65, 5, this.param_values);

        MENUsimplebutton_field ok_button =
            menues.ConvertSimpleButton(parent.menu.findField("QUICK RACE - GAME OPTIONS OK"));
        MENUsimplebutton_field default_button =
            menues.ConvertSimpleButton(parent.menu.findField("QUICK RACE - GAME OPTIONS DEFAULT"));

        menues.SetScriptOnControl(_menu, ok_button, this, "OnOk", 4L);
        menues.SetScriptOnControl(_menu, default_button, this, "OnDefault", 4L);
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
        this.param_values.onUpdate();
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
        this.param_values.onOk();
        JavaEvents.SendEvent(65, 6, this.param_values);
        exitDialog();
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
        this.param_values.onOk();
    }

    /**
     * Method description
     *
     */
    @Override
    public void update() {
        super.update();
    }

    /**
     * Method description
     *
     */
    @Override
    public void readParamValues() {
        JavaEvents.SendEvent(65, 5, this.param_values);
        this.param_values.onUpdate();
    }
}


//~ Formatted in DD Std on 13/08/26
