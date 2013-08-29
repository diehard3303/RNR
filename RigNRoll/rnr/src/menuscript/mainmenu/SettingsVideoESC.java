/*
 * @(#)SettingsVideoESC.java   13/08/26
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
import rnr.src.menuscript.IresolutionChanged;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class SettingsVideoESC extends SettingsVideo {
    private static final String _TABLE_DEVICE = "TABLEGROUP - SETTINGS - VIDEO - 1 60";
    private static final String _TABLE_SETTINGS = "TABLEGROUP VIDEO 02 - 5 65";
    private static final String _RANGER = "STRING - VIDEO - Tableranger";
    private static final String _FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String _WARNING = "Tablegroup - SETTINGS - VIDEO - CONFIRM MESSAGE";
    private static final String _WARNING_WND = "PopUpWarning - RESOLUTIONCHANGED";
    private static String filename;
    private static String prev_table_device;
    private static String prev_table_settings;
    private static String prev_ranger;
    private static String prev_warning;
    private static String prev_warning_wnd;
    private ArrayList<IresolutionChanged> listeners = new ArrayList();

    private SettingsVideoESC(long _menu, long exitButton, long defaultButton, long okButton, long applyButton) {
        super(_menu, new long[0], 0L, exitButton, defaultButton, okButton, applyButton,
              new Panel(new MainMenu("menu_esc.xml"), "ESC PANEL", new String[0]));
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void addListener(IresolutionChanged listener) {
        this.listeners.add(listener);
    }

    private static void changeTableStrings() {
        prev_table_device = TABLE_DEVICE;
        prev_table_settings = TABLE_SETTINGS;
        prev_ranger = RANGER;
        prev_warning = WARNING;
        prev_warning_wnd = WARNING_WND;
        filename = CA.FILENAME;
        TABLE_DEVICE = "TABLEGROUP - SETTINGS - VIDEO - 1 60";
        TABLE_SETTINGS = "TABLEGROUP VIDEO 02 - 5 65";
        RANGER = "STRING - VIDEO - Tableranger";
        WARNING = "Tablegroup - SETTINGS - VIDEO - CONFIRM MESSAGE";
        WARNING_WND = "PopUpWarning - RESOLUTIONCHANGED";
        CA.FILENAME = "..\\data\\config\\menu\\menu_esc.xml";
    }

    private static void changeBACK() {
        TABLE_DEVICE = prev_table_device;
        TABLE_SETTINGS = prev_table_settings;
        RANGER = prev_ranger;
        WARNING = prev_warning;
        WARNING_WND = prev_warning_wnd;
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
    public static SettingsVideoESC create(long _menu, long exitButton, long defaultButton, long okButton,
            long applyButton) {
        changeTableStrings();

        SettingsVideoESC result = new SettingsVideoESC(_menu, exitButton, defaultButton, okButton, applyButton);

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
        this.listeners = null;
        super.exitMenu();
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
        if (this.res_changed) {
            callResolutionChanged();
        }

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
        if (this.res_changed) {
            callResolutionChanged();
        }

        super.OnApply(_menu, button);
    }

    /**
     * Method description
     *
     */
    @Override
    public void onClose() {
        callResolutionChanged();
        super.onClose();
    }

    /**
     * Method description
     *
     */
    @Override
    public void onAgreeclose() {
        super.onAgreeclose();
    }

    private void callResolutionChanged() {
        Iterator iter = this.listeners.iterator();

        while (iter.hasNext()) {
            ((IresolutionChanged) iter.next()).changed();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
