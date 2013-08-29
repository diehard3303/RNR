/*
 * @(#)SinglePlayerNewGame.java   13/08/26
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
public class SinglePlayerNewGame extends PanelDialog {
    private static final String STARTGAME = "SINGLE PLAYER - NEW GAME START";
    private static final String STARTGAMEMETH = "OnStart";
    private static final String[] LODs = { loc.getMENUString("LOD_EASY"), loc.getMENUString("LOD_NORMAL"),
            loc.getMENUString("LOD_HARD") };
    private static final String TITLE = loc.getMENUString("LOD TITLE");
    private static final String TABLE = "TABLEGROUP - SINGLE PLAYER - NEW GAME - 11 60";
    private TableOfElements table = null;
    private ListOfAlternatives levelOfDifaculty = null;

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
    public SinglePlayerNewGame(long _menu, long[] controls, long window, long exitButton, long defaultButton,
                               long okButton, long applyButton, Panel parent) {
        super(_menu, window, controls, exitButton, defaultButton, okButton, applyButton, parent);

        MENUsimplebutton_field startButton =
            menues.ConvertSimpleButton(parent.menu.findField("SINGLE PLAYER - NEW GAME START"));

        menues.SetScriptOnControl(parent.menu._menu, startButton, this, "OnStart", 4L);
        this.table = new TableOfElements(_menu, "TABLEGROUP - SINGLE PLAYER - NEW GAME - 11 60");
        this.levelOfDifaculty = CA.createListOfAlternatives(TITLE, LODs, true);
        this.levelOfDifaculty.load(_menu);
        this.table.insert(this.levelOfDifaculty);
        this.param_values.addParametr("SINGLE PLAYER LOD", 0, 0, this.levelOfDifaculty);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnStart(long _menu, MENUsimplebutton_field button) {
        this.param_values.onOk();
        JavaEvents.SendEvent(12, 4, this.param_values);
        SaveLoadCommonManagement.getSaveLoadCommonManager().SetStartNewGameFlag(1);
        TopWindow.quitTopMenu();
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        super.afterInit();
        JavaEvents.SendEvent(12, 3, this.param_values);
        this.table.initTable();
        this.param_values.onUpdate();
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
}


//~ Formatted in DD Std on 13/08/26
