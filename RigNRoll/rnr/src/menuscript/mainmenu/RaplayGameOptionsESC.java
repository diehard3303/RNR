/*
 * @(#)RaplayGameOptionsESC.java   13/08/26
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
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.RadioGroupSmartSwitch;
import rnr.src.menu.SMenu;
import rnr.src.menu.TableOfElements;
import rnr.src.menu.menues;
import rnr.src.menuscript.parametrs.ParametrsBlock;
import rnr.src.rnrcore.loc;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class RaplayGameOptionsESC extends PanelDialog {
    private static final String OK = "BUTT - REPLAY OPTIONS - OK";
    private static final String DEFAULT = "BUTT - REPLAY OPTIONS - DEFAULT";
    private static final String OKMETHOD = "OnOk";
    private static final String DEFAULTMETHOD = "OnDefault";
    protected static String TABLE = "TABLEGROUP REPLAY OPTIONS 02 - 2 65";
    protected static String RANGER = null;
    private static final String TITLE_REPEAT_FOREVER = "OPTION_REPLAY_REPEAT_FOREVER";
    TableOfElements table = null;

    private RaplayGameOptionsESC(long _menu, long exitButton, long defaultButton, long okButton, long applyButton) {
        super(_menu, 0L, new long[0], exitButton, defaultButton, okButton, applyButton,
              new Panel(new MainMenu("menu_esc.xml"), "ESC PANEL", new String[0]));
        this.table = new TableOfElements(_menu, TABLE, RANGER);

        String filename = CA.FILENAME;

        CA.FILENAME = "..\\data\\config\\menu\\menu_esc.xml";

        RadioGroupSmartSwitch repeat_replay =
            CA.createRadioGroupSmartSwitch(loc.getMENUString("OPTION_REPLAY_REPEAT_FOREVER"), true, true);

        repeat_replay.load(_menu);
        this.table.insert(repeat_replay);
        this.param_values.addParametr("OPTION REPLAY REPEAT FOREVER", false, false, repeat_replay);
        CA.FILENAME = filename;
        JavaEvents.SendEvent(74, 0, this.param_values);

        MENUsimplebutton_field ok_button =
            menues.ConvertSimpleButton(this.parent.menu.findField("BUTT - REPLAY OPTIONS - OK"));
        MENUsimplebutton_field default_button =
            menues.ConvertSimpleButton(this.parent.menu.findField("BUTT - REPLAY OPTIONS - DEFAULT"));

        menues.SetScriptOnControl(_menu, ok_button, this, "OnOk", 4L);
        menues.SetScriptOnControl(_menu, default_button, this, "OnDefault", 4L);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param exitButton
     * @param defaultButton
     * @param okButton
     * @param applyButton
     *
     * @return
     */
    public static RaplayGameOptionsESC create(long _menu, long exitButton, long defaultButton, long okButton,
            long applyButton) {
        RaplayGameOptionsESC result = new RaplayGameOptionsESC(_menu, exitButton, defaultButton, okButton, applyButton);

        return result;
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
        JavaEvents.SendEvent(75, 0, this.param_values);
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
        JavaEvents.SendEvent(75, 0, this.param_values);
    }

    /**
     * Method description
     *
     */
    @Override
    public void update() {
        JavaEvents.SendEvent(74, 0, this.param_values);
        super.update();
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
        update();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param window
     */
    @Override
    public void OnExit(long _menu, SMenu window) {}

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
    public void readParamValues() {
        JavaEvents.SendEvent(75, 0, this.param_values);
        this.param_values.onUpdate();
        JavaEvents.SendEvent(75, 0, this.param_values);
    }
}


//~ Formatted in DD Std on 13/08/26
