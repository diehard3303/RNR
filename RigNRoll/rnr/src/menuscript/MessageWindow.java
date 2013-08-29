/*
 * @(#)MessageWindow.java   13/08/26
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
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class MessageWindow extends BaseMenu implements menucreation {
    static String s_Text;
    static boolean s_hasok;
    static boolean s_istutorial;
    static boolean s_stopworld;
    static boolean s_polosy;
    private String menuId = "";
    String m_Text;
    boolean m_hasok;
    boolean m_istutorial;
    boolean m_stopworld;
    boolean m_polosy;

    private MessageWindow(String type) {
        this.menuId = type;
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
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        this.common = new Common(_menu);
        this.m_Text = s_Text;
        s_Text = null;
        this.m_hasok = s_hasok;
        this.m_istutorial = s_istutorial;
        this.m_polosy = s_polosy;
        this.m_stopworld = s_stopworld;
        menues.SetStopWorld(_menu, this.m_stopworld);
        menues.SetMenagPOLOSY(_menu, this.m_polosy);

        if (this.m_istutorial) {
            menues.InitXml(_menu, Common.ConstructPath("menu_tutorial.xml"), this.m_Text);
        } else {
            menues.InitXmlControl(_menu, Common.ConstructPath("menu_com.xml"), "MESSAGE", "Message Tutorial");

            MENUText_field text = this.common.FindTextField("Just Text");

            text.text = this.m_Text;
            menues.UpdateField(text);
        }

        MENUsimplebutton_field okbutton = this.common.FindSimpleButton("BUTTON - OK");

        if (this.m_hasok) {
            menues.SetMenuCallBack_ExitMenu(_menu, okbutton.nativePointer, 4L);
        } else {
            menues.SetShowField(okbutton.nativePointer, false);
        }

        menues.WindowSet_ShowCursor(_menu, this.m_hasok);
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

        MENUsimplebutton_field okbutton = this.common.FindSimpleButton("BUTTON - OK");

        menues.setfocuscontrolonmenu(_menu, okbutton.nativePointer);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {}

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getMenuId() {
        return this.menuId;
    }

    /**
     * Method description
     *
     *
     * @param message
     * @param hasok
     * @param istutorial
     * @param lifetime
     * @param exitkey
     * @param stopworld
     * @param polosy
     *
     * @return
     */
    public static long CreateMessageWindow(String message, boolean hasok, boolean istutorial, double lifetime,
            String exitkey, boolean stopworld, boolean polosy) {
        s_Text = message;
        s_hasok = hasok;
        s_istutorial = istutorial;
        s_stopworld = stopworld;
        s_polosy = polosy;

        String id = "";

        if (message.compareTo("TUTORIAL - WAREHOUSE") == 0) {
            id = "tutorialTruckStopMENU";
        } else if (message.compareTo("TUTORIAL - TRUCKSTOP") == 0) {
            id = "tutorialWarehouseMENU";
        }

        return menues.createSimpleMenu(new MessageWindow(id), lifetime, exitkey, 1600, 1200, 1600, 1200, 0, 0,
                                       "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }
}


//~ Formatted in DD Std on 13/08/26
