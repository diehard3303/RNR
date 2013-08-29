/*
 * @(#)NotifyGameOver.java   13/08/28
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


package rnr.src.menuscript;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.BaseMenu;
import rnr.src.menu.Common;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MacroKit;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.players.Crew;
import rnr.src.rnrcore.event;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.rnrscr.ILeaveMenuListener;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public class NotifyGameOver extends BaseMenu implements menucreation {

    /** Field description */
    public static final int GAMEOVER = 0;

    /** Field description */
    public static final int GAMEOVER_JAIL = 1;

    /** Field description */
    public static final int GAMEOVER_MURDER = 2;

    /** Field description */
    public static final int GAMEOVER_BANKRUPT = 3;

    /** Field description */
    public static final int GAMEOVER_BLACK_SCREEN = 4;
    static int s_gameovertype = 0;
    static Common s_common;
    static NotifyGameOver s_this;
    private ILeaveMenuListener leaveMenuListener = null;

    NotifyGameOver() {}

    NotifyGameOver(ILeaveMenuListener listener) {
        this.leaveMenuListener = listener;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void restartMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @param exitkey
     * @param duration
     * @param gameovertype
     * @param listener
     *
     * @return
     */
    public static long CreateNotifyGameOver(String exitkey, double duration, int gameovertype,
            ILeaveMenuListener listener) {
        s_gameovertype = gameovertype;

        return menues.createSimpleMenu(new NotifyGameOver(listener), duration, exitkey, 1600, 1200, 1600, 1200, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    /**
     * Method description
     *
     *
     * @param exitkey
     * @param duration
     * @param gameovertype
     *
     * @return
     */
    public static long CreateNotifyGameOver(String exitkey, double duration, int gameovertype) {
        s_gameovertype = gameovertype;

        return menues.createSimpleMenu(new NotifyGameOver(), duration, exitkey, 1600, 1200, 1600, 1200, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    static void KillOrderMenu() {
        if (s_this != null) {
            menues.CallMenuCallBack_ExitMenu(s_common.GetMenu());
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        s_this = this;
        this.common = new Common(_menu);
        s_common = this.common;

        String cgroup = "Message GAMEOVER";

        switch (s_gameovertype) {
         case 0 :
             cgroup = "Message GAMEOVER";

             break;

         case 1 :
             cgroup = "Message GAMEOVER 01";

             break;

         case 2 :
             cgroup = "Message GAMEOVER 02";

             break;

         case 3 :
             cgroup = "Message GAMEOVER 03";

             break;

         case 4 :
             cgroup = "Message GAMEOVER with Black Background";
        }

        menues.InitXml(_menu, Common.ConstructPath("menu_msg_gameover.xml"), cgroup);
        menues.SetMenuCallBack_ExitMenu(_menu, menues.FindFieldInMenu(_menu, "BUTTON - OK"), 4L);
        menues.WindowSet_ShowCursor(_menu, true);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        menues.setShowMenu(_menu, true);
        menues.setfocuscontrolonmenu(_menu, menues.FindFieldInMenu(_menu, "BUTTON - OK"));
        menues.SetStopWorld(_menu, true);

        if ((((s_gameovertype == 4) || (s_gameovertype == 0) || (s_gameovertype == 1))) && (Crew.getIgrok() != null)
                && (Crew.getIgrok().gFirstName() != null) && (Crew.getIgrok().gLastName() != null)) {
            long _textId = menues.FindFieldInMenu(_menu, "BackPaper - text");

            if (_textId != 0L) {
                String _textV = menues.GetFieldText(_textId);

                if (_textV != null) {
                    KeyPair[] pairs = new KeyPair[1];

                    pairs[0] = new KeyPair("NICOLAS_ARMSTRONG",
                                           Crew.getIgrok().gFirstName() + " " + Crew.getIgrok().gLastName());
                    menues.SetFieldText(_textId, MacroKit.Parse(_textV, pairs));
                    menues.UpdateMenuField(menues.ConvertMenuFields(_textId));
                }
            }
        }

        switch (s_gameovertype) {
         case 0 :
             JavaEvents.SendEvent(71, 9, null);

             break;

         case 1 :
             JavaEvents.SendEvent(71, 10, null);

             break;

         case 2 :
             JavaEvents.SendEvent(71, 10, null);

             break;

         case 3 :
             JavaEvents.SendEvent(71, 8, null);

             break;

         case 4 :
             JavaEvents.SendEvent(71, 9, null);
        }
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {
        if (this.leaveMenuListener != null) {
            this.leaveMenuListener.menuLeaved();
        }

        s_this = null;
        event.Setevent(9001);

        if (scenarioscript.script.isInstantOrder()) {
            JavaEvents.SendEvent(23, 3, this);
        } else if ((s_gameovertype != 3) && (s_gameovertype != 2)) {
            JavaEvents.SendEvent(23, 1, this);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        switch (s_gameovertype) {
         case 0 :
             return "gameoverEndMENU";

         case 1 :
             return "gameoverJailMENU";

         case 2 :
             return "gameoverMurderMENU";

         case 3 :
             return "gameoverBankruptMENU";

         case 4 :
             return "gameoverEndWthBackgroundMENU";
        }

        return null;
    }
}


//~ Formatted in DD Std on 13/08/28
