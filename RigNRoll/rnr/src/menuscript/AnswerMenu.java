/*
 * @(#)AnswerMenu.java   13/08/26
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

import rnr.src.menu.Controls;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.MenuAfterInitNarrator;
import rnr.src.menu.Titres;
import rnr.src.menu.menucreation;
import rnr.src.menu.menues;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrcore.ScriptRef;
import rnr.src.rnrcore.anm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrscr.IDialogListener;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class AnswerMenu implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_bar.xml";
    private static final String GROUP = "Dialog - Two Answers";
    private static final String PROGRESS_BAR = "Dialog - Two Answers - ProgressBar";
    private static final String PROGRESS_BORDER = "Dialog - Two Answers - ProgressBar Border";
    private static final String PROGRESS_FIELD = "Dialog - Two Answers - WARNING";
    private static final String[] BUTTONS = { "Answer01", "Answer02" };
    private static final String[] ACTIONS = { "onYes", "onNo" };
    private static final String[] POSTFIXES = { " (Y)", " (N)" };
    private long progress_bar = 0L;
    private long progress_border = 0L;
    private long progress_field = 0L;
    private boolean finished = false;
    private final String[] texts;
    private long _menu;
    private long[] buttons;
    private final IDialogListener listener;

    private AnswerMenu(String[] texts, IDialogListener listener) {
        this.texts = texts;
        this.listener = listener;
    }

    /**
     * Method description
     *
     *
     * @param texts
     * @param listener
     *
     * @return
     */
    public static long createAnswerMenu(String[] texts, IDialogListener listener) {
        Titres.clearTitres();

        return menues.createSimpleMenu(new AnswerMenu(texts, listener));
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void InitMenu(long _menu) {
        this._menu = _menu;
        menues.InitXml(_menu, "..\\data\\config\\menu\\menu_bar.xml", "Dialog - Two Answers");
        this.buttons = new long[BUTTONS.length];

        for (int i = 0; i < BUTTONS.length; ++i) {
            this.buttons[i] = menues.FindFieldInMenu(_menu, BUTTONS[i]);
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.buttons[i]), this, ACTIONS[i], 4L);
            menues.SetFieldText(this.buttons[i], this.texts[i] + POSTFIXES[i]);
        }

        this.progress_bar = menues.FindFieldInMenu(_menu, "Dialog - Two Answers - ProgressBar");
        this.progress_border = menues.FindFieldInMenu(_menu, "Dialog - Two Answers - ProgressBar Border");
        this.progress_field = menues.FindFieldInMenu(_menu, "Dialog - Two Answers - WARNING");
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void AfterInitMenu(long _menu) {
        if (this.progress_bar != 0L) {
            menues.SetShowField(this.progress_bar, false);
        }

        if (this.progress_border != 0L) {
            menues.SetShowField(this.progress_border, false);
        }

        MenuAfterInitNarrator.justShowWithCursor(_menu);
        eng.CreateInfinitScriptAnimation(new WaitQnswer());
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    @Override
    public void exitMenu(long _menu) {
        this.finished = true;
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
     * @return
     */
    @Override
    public String getMenuId() {
        return "answerMENU";
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onYes(long _menu, MENUsimplebutton_field button) {
        sayYes();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onNo(long _menu, MENUsimplebutton_field button) {
        sayNo();
    }

    private void sayNo() {
        this.finished = true;

        if (null != this.listener) {
            this.listener.onNo("");
        }

        menues.CallMenuCallBack_ExitMenu(this._menu);
    }

    private void sayYes() {
        this.finished = true;

        if (null != this.listener) {
            this.listener.onYes("");
        }

        menues.CallMenuCallBack_ExitMenu(this._menu);
    }

    /**
     * @return the xml
     */
    public static String getXml() {
        return XML;
    }

    /**
     * @return the group
     */
    public static String getGroup() {
        return GROUP;
    }

    /**
     * @return the progressBar
     */
    public static String getProgressBar() {
        return PROGRESS_BAR;
    }

    /**
     * @return the progressBorder
     */
    public static String getProgressBorder() {
        return PROGRESS_BORDER;
    }

    /**
     * @return the progressField
     */
    public static String getProgressField() {
        return PROGRESS_FIELD;
    }

    class WaitQnswer implements anm {
        @SuppressWarnings("unused")
        private double time_count_down = 10.0D;
        private final ScriptRef uid;

        WaitQnswer() {
            this.uid = new ScriptRef();
            this.time_count_down = 10.0D;
        }

        /**
         * Method description
         *
         *
         * @param nativePointer
         */
        @Override
        public void updateNative(int nativePointer) {}

        /**
         * Method description
         *
         *
         * @param dt
         *
         * @return
         */
        @Override
        public boolean animaterun(double dt) {
            if (AnswerMenu.this.finished) {
                return true;
            }

            if (Controls.isNoPressed()) {
                AnswerMenu.this.sayNo();

                return true;
            }

            if (Controls.isYesPressed()) {
                AnswerMenu.this.sayYes();

                return true;
            }

            if (dt > 10.0D) {
                AnswerMenu.this.sayNo();

                return true;
            }

            if (AnswerMenu.this.progress_bar != 0L) {
                menues.SetShowField(AnswerMenu.this.progress_bar, false);
            }

            if (AnswerMenu.this.progress_border != 0L) {
                menues.SetShowField(AnswerMenu.this.progress_border, false);
            }

            if (AnswerMenu.this.progress_field != 0L) {
                MENUText_field tf = menues.ConvertTextFields(AnswerMenu.this.progress_field);

                if (null != tf) {
                    int show_time = Math.max(0, (int) Math.ceil(10.0D - dt));
                    KeyPair[] keys = { new KeyPair("SEC", "" + show_time) };

                    MacroKit.ApplyToTextfield(tf, keys);
                }
            }

            return false;
        }

        /**
         * Method description
         *
         *
         * @param value
         */
        @Override
        public void setUid(int value) {
            this.uid.setUid(value);
        }

        /**
         * Method description
         *
         */
        @Override
        public void removeRef() {
            this.uid.removeRef(this);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public int getUid() {
            return this.uid.getUid(this);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public IXMLSerializable getXmlSerializator() {
            return null;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
