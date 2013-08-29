/*
 * @(#)Panel.java   13/08/26
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

import rnr.src.menu.FabricControlColor;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.SMenu;
import rnr.src.menu.menues;
import rnr.src.rnrcore.Log;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class Panel implements IWindowContext {
    private static final String ACTIONMETHOD = "OnAction";
    private static final String EXITMETHOD = "OnExit";
    protected static final String BACK_SUFF = " - BACKGROUND";
    protected static final String EXIT_KEY_SUFF = " Exit";
    protected static final String DEFAULT_KEY_SUFF = " DEFAULT";
    protected static final String OK_KEY_SUFF = " OK";
    protected static final String DIALOG_KEY_PREF = "Button ";
    private HashMap<String, PanelDialog> dialogs = new HashMap();
    private long[] floating_background = null;
    private long[] buttons = null;
    protected HashMap<Integer, String> dials_on_buttons = new HashMap();
    private long exit_key = 0L;
    private long window = 0L;
    private final ArrayList<String> blind = new ArrayList();
    private LastPanelState lastPanelState = null;
    MainMenu menu;
    private final String panelname;

    Panel(MainMenu menu, String panelname, String[] dialogs) {
        this.menu = menu;
        this.panelname = panelname;
        this.floating_background = menu.loadGroup(panelname + " - BACKGROUND");
        this.buttons = new long[dialogs.length];

        for (int i = 0; i < dialogs.length; ++i) {
            this.buttons[i] = menu.findField("Button " + dialogs[i]);
            this.dials_on_buttons.put(new Integer(i), dialogs[i]);
        }

        this.exit_key = menu.findField(panelname + " Exit");
        this.window = menu.findField(panelname);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getName() {
        return this.panelname;
    }

    /**
     * Method description
     *
     */
    public void restoreBlindness() {
        Iterator iter = this.blind.iterator();

        while (iter.hasNext()) {
            String name = (String) iter.next();
            long field = this.menu.findField(name);

            if (0L == field) {
                Log.menu("restoreBlindness. Has no such field as " + name);
            }

            CA.freezControl(field);
        }
    }

    protected void addBlind(String name) {
        this.blind.add(name);
    }

    protected void add(String name, PanelDialog dialog) {
        this.dialogs.put(name, dialog);
    }

    @SuppressWarnings("rawtypes")
    void init() {
        Set dials = this.dialogs.entrySet();
        Iterator iter = dials.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            PanelDialog single_dial = (PanelDialog) entry.getValue();

            single_dial.setShow(false);
            single_dial.afterInit();
        }

        setShowBackground(false);

        for (int i = 0; i < this.buttons.length; ++i) {
            MENUsimplebutton_field field = menues.ConvertSimpleButton(this.buttons[i]);

            field.userid = i;
            menues.UpdateField(field);
            menues.SetScriptOnControl(this.menu._menu, field, this, "OnAction", 4L);
        }

        MENUsimplebutton_field exitfield = menues.ConvertSimpleButton(this.exit_key);

        menues.SetScriptOnControl(this.menu._menu, exitfield, this, "OnExit", 4L);

        SMenu _window = menues.ConvertWindow(this.window);

        menues.SetScriptOnControl(this.menu._menu, _window, this, "OnExit", 17L);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnAction(long _menu, MENUsimplebutton_field button) {
        this.menu.OnDialogOpen(new StartDialog(0));

        PanelDialog dialog = this.dialogs.get(this.dials_on_buttons.get(new Integer(button.userid)));

        dialog.update();
        dialog.setAnimating(true);
        dialog.setShow(false);
        dialog.setFreeze(true);
        setShowBackground(false);
        setFreezeBackground(true);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnExit(long _menu, MENUsimplebutton_field button) {
        exitWindow();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void OnExit(long _menu, SMenu button) {
        exitWindow();
    }

    private void exitWindow() {
        this.menu.resetToDefaulScreen();
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitWindowContext() {
        exitWindow();
    }

    void OnDialogExit(PanelDialog dialog) {
        dialog.setAnimating(true);
        dialog.setShow(true);
        dialog.setFreeze(true);
        setFreezeBackground(true);
        setShowBackground(true);
        new FadeOutDialog(new StartDialog(2));
    }

    private void setAlphaBackground(int value) {
        if (null == this.floating_background) {
            return;
        }

        for (int i = 0; i < this.floating_background.length; ++i) {
            FabricControlColor.setControlAlfa(this.floating_background[i], value);
        }
    }

    private void setShowBackground(boolean value) {
        if (null == this.floating_background) {
            return;
        }

        if (value) {
            for (int i = 0; i < this.floating_background.length; ++i) {
                CA.showControl(this.floating_background[i]);
            }
        } else {
            for (int i = 0; i < this.floating_background.length; ++i) {
                CA.hideControl(this.floating_background[i]);
            }
        }
    }

    private void setFreezeBackground(boolean value) {
        if (null == this.floating_background) {
            return;
        }

        if (value) {
            for (int i = 0; i < this.floating_background.length; ++i) {
                CA.freezControl(this.floating_background[i]);
            }
        } else {
            for (int i = 0; i < this.floating_background.length; ++i) {
                CA.unfreezControl(this.floating_background[i]);
            }
        }
    }

    /**
     * Method description
     *
     */
    public void exitMenu() {
        Set dials = this.dialogs.entrySet();
        Iterator iter = dials.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            PanelDialog single_dial = (PanelDialog) entry.getValue();

            single_dial.exitMenu();
        }

        this.dialogs.clear();
        this.dialogs = null;
    }

    /**
     * Method description
     *
     */
    public void restoreLastState() {
        if (null != this.lastPanelState) {
            this.lastPanelState.restoreState();
        }
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void makeLastState(String name) {
        this.lastPanelState = new LastPanelState(name);
    }

    /**
     * Method description
     *
     */
    @Override
    public void updateWindowContext() {}

    /**
     * Method description
     *
     */
    @SuppressWarnings("rawtypes")
    public void restoreProfileValuesToPanel() {
        Set set = this.dialogs.entrySet();

        for (Map.Entry entry : set) {
            ((PanelDialog) entry.getValue()).readParamValues();
        }
    }

    class FadeInDialog {
        private IFadeOutFadeIn cb = null;

        FadeInDialog(IFadeOutFadeIn paramIFadeOutFadeIn) {
            this.cb = paramIFadeOutFadeIn;
            Panel.this.menu.getClass();
            menues.StopScriptAnimation(9L);
            Panel.this.menu.getClass();
            menues.SetScriptObjectAnimation(0L, 9L, this, "animate");
        }

        @SuppressWarnings("rawtypes")
        void animate(long c, double time) {
            int res_alpha = Animation.alpha_fadein(time, Panel.this.menu.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();
            Set dials = Panel.this.dialogs.entrySet();
            Iterator iter = dials.iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                PanelDialog single_dial = (PanelDialog) entry.getValue();

                if (single_dial.isAnimating()) {
                    single_dial.setAlpha(res_alpha);
                }
            }

            Panel.this.setAlphaBackground(res_alpha);

            if (end_animation) {
                this.cb.fadeinEnded();
                Panel.this.menu.getClass();
                menues.StopScriptAnimation(9L);
            }
        }
    }


    class FadeOutDialog {
        private IFadeOutFadeIn cb = null;

        FadeOutDialog(IFadeOutFadeIn paramIFadeOutFadeIn) {
            this.cb = paramIFadeOutFadeIn;
            Panel.this.menu.getClass();
            menues.StopScriptAnimation(8L);
            Panel.this.menu.getClass();
            menues.SetScriptObjectAnimation(0L, 8L, this, "animate");
        }

        void animate(long c, double time) {
            int res_alpha = Animation.alpha_fadeout(time, Panel.this.menu.PERIODFADEIN);
            boolean end_animation = Animation.animtionEnded();
            Set dials = Panel.this.dialogs.entrySet();
            Iterator iter = dials.iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                PanelDialog single_dial = (PanelDialog) entry.getValue();

                if (single_dial.isAnimating()) {
                    single_dial.setAlpha(res_alpha);
                }
            }

            Panel.this.setAlphaBackground(res_alpha);

            if (end_animation) {
                this.cb.fadeoutEnded();
                Panel.this.menu.getClass();
                menues.StopScriptAnimation(8L);
            }
        }
    }


    class LastPanelState {
        private final String dialogOpened;

        LastPanelState(String paramString) {
            this.dialogOpened = paramString;
        }

        /**
         * Method description
         *
         */
        public void restoreState() {
            String name = this.dialogOpened;
            int userid = -1;

            for (int i = 0; i < Panel.this.buttons.length; ++i) {
                String fname = menues.GetFieldName(Panel.this.buttons[i]);

                if (fname.contains(name)) {
                    userid = i;

                    break;
                }
            }

            Panel.this.menu.OnDialogOpenImmediate();

            PanelDialog dialog = Panel.this.dialogs.get(Panel.this.dials_on_buttons.get(new Integer(userid)));

            dialog.update();
            dialog.setShow(true);
            dialog.setAlpha(255);
            Panel.this.setAlphaBackground(255);
            dialog.setFreeze(false);
            Panel.this.setFreezeBackground(false);
            Panel.this.setShowBackground(true);
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ
     */
    public class StartDialog implements IFadeOutFadeIn {
        final int STARTMENU_HEADFADED = 0;
        final int DIALOG_FADEDIN = 1;
        final int DIALOG_FADED = 2;
        final int STARTMENU_HEADFADEDIN = 3;
        private int order = 0;

        StartDialog(int paramInt) {
            this.order = paramInt;
        }

        /**
         * Method description
         *
         */
        @Override
        public void fadeinEnded() {
            switch (this.order) {
             case 1 :
                 Set dials = Panel.this.dialogs.entrySet();
                 Iterator iter = dials.iterator();

                 while (iter.hasNext()) {
                     Map.Entry entry = (Map.Entry) iter.next();
                     PanelDialog single_dial = (PanelDialog) entry.getValue();

                     if (single_dial.isAnimating()) {
                         single_dial.setFreeze(false);
                         single_dial.setAnimating(false);
                         Panel.this.menu.rememberPanelState(Panel.this.panelname, (String) entry.getKey());
                     }
                 }

                 Panel.this.setFreezeBackground(false);
             case 3 :
            }
        }

        /**
         * Method description
         *
         */
        @SuppressWarnings("rawtypes")
        @Override
        public void fadeoutEnded() {
            switch (this.order) {
             case 0 :
                 this.order = 1;

                 Set dials = Panel.this.dialogs.entrySet();
                 Iterator iter = dials.iterator();

                 while (iter.hasNext()) {
                     Map.Entry entry = (Map.Entry) iter.next();
                     PanelDialog single_dial = (PanelDialog) entry.getValue();

                     if (single_dial.isAnimating()) {
                         single_dial.setShow(true);
                     }
                 }

                 Panel.this.setShowBackground(true);
                 new Panel.FadeInDialog(this);

                 break;

             case 2 :
                 this.order = 3;
                 dials = Panel.this.dialogs.entrySet();

                 Iterator iter1 = dials.iterator();

                 while (iter1.hasNext()) {
                     Map.Entry entry = (Map.Entry) iter1.next();
                     PanelDialog single_dial = (PanelDialog) entry.getValue();

                     if (single_dial.isAnimating()) {
                         single_dial.setShow(false);
                         single_dial.setAnimating(false);
                         Panel.this.menu.forgetPanelState(Panel.this.panelname);
                     }
                 }

                 Panel.this.setShowBackground(false);
                 Panel.this.menu.OnDialogClose(this);
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/26
