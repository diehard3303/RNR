/*
 * @(#)SettingsControlsESC.java   13/08/26
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

import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.SMenu;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class SettingsControlsESC extends SettingsControls {
    private static String prev_table;
    private static String prev_table_settings;
    private static int num_tables;
    private static String[] remap_tables;
    private static String filename;
    private static String prev_popup;
    private static String prev_popupwnd;

    private SettingsControlsESC(long _menu, long exitButton, long defaultButton, long okButton, long applyButton) {
        super(_menu, new long[0], 0L, exitButton, defaultButton, okButton, applyButton,
              new Panel(new MainMenu("menu_esc.xml"), "ESC PANEL", new String[0]));
    }

    private static void changeTableStrings() {
        prev_table = TABLE;
        prev_table_settings = TABLE_SETTINGS;
        num_tables = RemapControlsTable.st_NUM_TABLES;
        remap_tables = RemapControlsTable.TABLES;
        prev_popup = POPUP;
        prev_popupwnd = POPUPWND;
        filename = CA.FILENAME;
        TABLE = "TABLEGROUP - SETTINGS - CONTROLS - 1 60";
        TABLE_SETTINGS = "TABLEGROUP - SETTINGS - CONTROLS - 4 65";
        RemapControlsTable.TABLES = new String[] { "TABLEGROUP - SETTINGS - CONTROLS 01 - 8 38" };
        RemapControlsTable.st_NUM_TABLES = 1;
        POPUP = "Tablegroup - SETTINGS - CONTROLS - CONFIRM MESSAGE";
        POPUPWND = null;
        CA.FILENAME = "..\\data\\config\\menu\\menu_esc.xml";
    }

    private static void changeBACK() {
        TABLE = prev_table;
        TABLE_SETTINGS = prev_table_settings;
        RemapControlsTable.st_NUM_TABLES = num_tables;
        RemapControlsTable.TABLES = remap_tables;
        POPUP = prev_popup;
        POPUPWND = prev_popupwnd;
        CA.FILENAME = filename;
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
    public static SettingsControlsESC create(long _menu, long exitButton, long defaultButton, long okButton,
            long applyButton) {
        changeTableStrings();

        SettingsControlsESC result = new SettingsControlsESC(_menu, exitButton, defaultButton, okButton, applyButton);

        changeBACK();

        return result;
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
     * @param button
     */
    @Override
    public void OnOk(long _menu, MENUsimplebutton_field button) {
        super.OnOk(_menu, button);
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
        this.remap_table.deinit();
        super.exitMenu();
    }
}


//~ Formatted in DD Std on 13/08/26
