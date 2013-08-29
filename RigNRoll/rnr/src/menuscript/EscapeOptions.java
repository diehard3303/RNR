/*
 * @(#)EscapeOptions.java   13/08/26
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
   Released under the BSD 3 clause license
Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this 
    list of conditions and the following disclaimer. Redistributions in binary 
    form must reproduce the above copyright notice, this list of conditions and 
    the following disclaimer in the documentation and/or other materials 
    provided with the distribution. Neither the name of the DieHard Development 
    nor the names of its contributors may be used to endorse or promote products 
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 *
 *
 *
 */


package rnr.src.menuscript;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.Common;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.menues;
import rnr.src.menu.xmlcontrols.MENUCustomStuff;
import rnr.src.menuscript.mainmenu.RaplayGameOptionsESC;
import rnr.src.menuscript.mainmenu.SettingsAudioESC;
import rnr.src.menuscript.mainmenu.SettingsControlsESC;
import rnr.src.menuscript.mainmenu.SettingsVideoESC;
import rnr.src.menuscript.mainmenu.SinglePlayerGameOptionsESC;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class EscapeOptions extends WindowParentMenu implements IresolutionChanged {
    private static final String FILE = "..\\data\\config\\menu\\menu_esc.xml";
    private static final String MENU = "OPTIONS";
    private static final int VIDEOSETTINGS = 2;
    private static final int GAME_OPTIONS = 3;
    private static final String[] SINGLE_TABNAMES = { "Tab0 - CONTROLS", "Tab0 - AUDIO", "Tab0 - VIDEO",
            "Tab0 - GAME PLAY" };
    private static final String[] REPLAY_TABNAMES = { "Tab0 - CONTROLS", "Tab0 - AUDIO", "Tab0 - VIDEO",
            "Tab0 - REPLAY OPTIONS" };
    private static boolean f_resolution_changed = false;
    private static int game_type = 0;
    private long tab_to_hide = 0L;
    private final ConfirmDialog m_cdialog;
    private String m_textemptywarn;

    /** Field description */
    public String m_inSoundCardName;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param common
     * @param dialog
     * @param _game_type
     */
    public EscapeOptions(long _menu, Common common, ConfirmDialog dialog, int _game_type) {
        super(_menu, "..\\data\\config\\menu\\menu_esc.xml", "OPTIONS");
        this.common = common;
        this.m_cdialog = dialog;
        game_type = _game_type;
        setSaveBetweenTabs();
    }

    /**
     * Method description
     *
     *
     * @param listener
     */
    public void addVideoSettingsListener(IresolutionChanged listener) {
        SettingsVideoESC video = (SettingsVideoESC) this.settings.get(2);

        video.addListener(listener);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String GetCustomText() {
        return this.m_textemptywarn;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        this.settings.add(SettingsControlsESC.create(_menu, menues.FindFieldInMenu(_menu, "BUTT - CONTROLS - CANCEL"),
                menues.FindFieldInMenu(_menu, "BUTT - CONTROLS - DEFAULT"),
                menues.FindFieldInMenu(_menu, "BUTT - CONTROLS - OK"),
                menues.FindFieldInMenu(_menu, "BUTT - CONTROLS - APPLY")));
        this.settings.add(SettingsAudioESC.create(_menu, menues.FindFieldInMenu(_menu, "BUTT - AUDIO - CANCEL"),
                menues.FindFieldInMenu(_menu, "BUTT - AUDIO - DEFAULT"),
                menues.FindFieldInMenu(_menu, "BUTT - AUDIO - OK"),
                menues.FindFieldInMenu(_menu, "BUTT - AUDIO - APPLY")));
        this.settings.add(SettingsVideoESC.create(_menu, menues.FindFieldInMenu(_menu, "BUTT - VIDEO - CANCEL"),
                menues.FindFieldInMenu(_menu, "BUTT - VIDEO - DEFAULT"),
                menues.FindFieldInMenu(_menu, "BUTT - VIDEO - OK"),
                menues.FindFieldInMenu(_menu, "BUTT - VIDEO - APPLY")));

        if (game_type != 3) {
            this.settings.add(SinglePlayerGameOptionsESC.create(_menu,
                    menues.FindFieldInMenu(_menu, "BUTT - GAMEPLAY - CANCEL"),
                    menues.FindFieldInMenu(_menu, "BUTT - GAMEPLAY - DEFAULT"),
                    menues.FindFieldInMenu(_menu, "BUTT - GAMEPLAY - OK"),
                    menues.FindFieldInMenu(_menu, "BUTT - GAMEPLAY - APPLY"), game_type));
        } else {
            this.settings.add(RaplayGameOptionsESC.create(_menu,
                    menues.FindFieldInMenu(_menu, "BUTT - REPLAY OPTIONS - CANCEL"),
                    menues.FindFieldInMenu(_menu, "BUTT - REPLAY OPTIONS - DEFAULT"),
                    menues.FindFieldInMenu(_menu, "BUTT - REPLAY OPTIONS - OK"),
                    menues.FindFieldInMenu(_menu, "BUTT - REPLAY OPTIONS - APPLY")));
        }

        if (game_type != 3) {
            for (int i = 0; i < SINGLE_TABNAMES.length; ++i) {
                if (i == 3) {
                    this.tab_to_hide = menues.FindFieldInMenu(_menu, REPLAY_TABNAMES[i]);
                    menues.SetShowField(this.tab_to_hide, false);
                }

                long single_tab = menues.FindFieldInMenu(_menu, SINGLE_TABNAMES[i]);
                xmlcontrols.MENUCustomStuff Tab = (xmlcontrols.MENUCustomStuff) menues.ConvertMenuFields(single_tab);

                AddTab(_menu, Tab);
            }
        } else {
            for (int i = 0; i < REPLAY_TABNAMES.length; ++i) {
                if (i == 3) {
                    this.tab_to_hide = menues.FindFieldInMenu(_menu, SINGLE_TABNAMES[i]);
                    menues.SetShowField(this.tab_to_hide, false);
                }

                long single_tab = menues.FindFieldInMenu(_menu, REPLAY_TABNAMES[i]);
                xmlcontrols.MENUCustomStuff Tab = (xmlcontrols.MENUCustomStuff) menues.ConvertMenuFields(single_tab);

                AddTab(_menu, Tab);
            }
        }
    }

    @Override
    void NeedToConfirm(String text) {
        this.m_cdialog.AskUser(this, text);
    }

    void OnOk(long _menu, MENUsimplebutton_field button) {
        super.OnOk(_menu, button);
    }

    /**
     * Method description
     *
     */
    @Override
    public void changed() {
        f_resolution_changed = true;
    }

    /**
     * Method description
     *
     */
    @Override
    public void Activate() {
        if (this.tab_to_hide != 0L) {
            menues.SetShowField(this.tab_to_hide, false);
        }

        if (f_resolution_changed) {
            activateTab(2);
        }

        super.Activate();
    }
}


//~ Formatted in DD Std on 13/08/26
