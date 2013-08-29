/*
 * @(#)AnalysisUI.java   13/08/27
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


package rnr.src.scenarioAnalysis;

//~--- JDK imports ------------------------------------------------------------

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public class AnalysisUI {
    private static final int NUM_BUTTONS = 10;
    IUpdateChoose listener = null;
    JFrame frame = new JFrame("Analysis UI");
    JButton[] buttons = null;

    /**
     * Constructs ...
     *
     */
    public AnalysisUI() {
        createFrame();
    }

    private void createFrame() {
        this.frame.setVisible(false);
        this.frame.setDefaultCloseOperation(1);
        this.frame.setBounds(50, 150, 1500, 1000);
        this.frame.setResizable(false);

        JPanel mainpanel = (JPanel) this.frame.getContentPane();

        mainpanel.setLayout(new GridLayout(10, 1));
        this.buttons = new JButton[10];

        for (int i = 0; i < 10; ++i) {
            JButton button = new JButton("not initialised");

            button.addActionListener(new ButtonPressEvent(i));
            button.setVisible(false);
            mainpanel.add(button);
            this.buttons[i] = button;
        }

        this.frame.doLayout();
        this.frame.setVisible(false);
    }

    void recieve(String[] options, IUpdateChoose listener) throws AnalysisUI.ExceptionBadData {
        this.listener = listener;

        if (options.length > 10) {
            throw new AnalysisUI.ExceptionBadData(options.length);
        }

        for (int i = 0; i < 10; ++i) {
            this.buttons[i].setVisible(false);
        }

        for (int i = 0; i < options.length; ++i) {
            this.buttons[i].setVisible(true);
            this.buttons[i].setText(options[i]);
        }
    }

    private void choose(int value) {
        if (null != this.listener) {
            this.listener.choose(value);
        }
    }

    void show() {
        this.frame.setVisible(true);
    }

    void close() {
        this.frame.dispose();
    }

    static abstract interface IUpdateChoose {

        /**
         * Method description
         *
         *
         * @param paramInt
         */
        public abstract void choose(int paramInt);
    }


    /**
     * Interface description
     *
     *
     * @version        1.0, 13/08/27
     * @author         TJ    
     */
    public static abstract interface WarnMessageClosed {

        /**
         * Method description
         *
         */
        public abstract void closed();
    }


    class ButtonPressEvent implements ActionListener {
        int m_nom_event = 0;

        ButtonPressEvent(int paramInt) {
            this.m_nom_event = paramInt;
        }

        /**
         * Method description
         *
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            AnalysisUI.this.choose(this.m_nom_event);
        }
    }


    static class ExceptionBadData extends Exception {
        static final long serialVersionUID = 0L;
        String message;
        JFrame frame;

        ExceptionBadData(int value) {
            super("Number of passed ways to go is more than 10 and equals " + value);
            this.message = "Number of passed ways to go is more than 10 and equals " + value;
        }

        void showEvent(AnalysisUI.WarnMessageClosed listener) {
            this.frame = new JFrame("WARNING");
            this.frame.setDefaultCloseOperation(1);
            this.frame.setBounds(500, 300, 300, 150);
            this.frame.setResizable(false);

            JPanel mainpanel = (JPanel) this.frame.getContentPane();

            mainpanel.setLayout(new GridLayout(1, 1));

            JButton button = new JButton(this.message);

            button.addActionListener(new ActionListener(listener) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AnalysisUI.ExceptionBadData.this.frame.dispose();
                    this.listener.closed();
                }
            });
            mainpanel.add(button);
            this.frame.doLayout();
            this.frame.setVisible(true);
        }
    }
}


//~ Formatted in DD Std on 13/08/27
