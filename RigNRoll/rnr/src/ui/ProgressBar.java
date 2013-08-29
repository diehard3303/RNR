/*
 * @(#)ProgressBar.java   13/08/26
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


package rnr.src.ui;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class ProgressBar extends JFrame {
    static final long serialVersionUID = 1L;
    protected JProgressBar bar = null;
    protected Object monitor = new Object();
    private Thread runner = null;
    protected int value = 0;

    /**
     * Constructs ...
     *
     *
     * @param min
     * @param max
     *
     * @throws HeadlessException
     */
    public ProgressBar(int min, int max) throws HeadlessException {
        constructFrame(min, max);
    }

    /**
     * Constructs ...
     *
     *
     * @param min
     * @param max
     * @param arg0
     */
    public ProgressBar(int min, int max, GraphicsConfiguration arg0) {
        super(arg0);
        constructFrame(min, max);
    }

    /**
     * Constructs ...
     *
     *
     * @param min
     * @param max
     * @param arg0
     *
     * @throws HeadlessException
     */
    public ProgressBar(int min, int max, String arg0) throws HeadlessException {
        super(arg0);
        constructFrame(min, max);
    }

    /**
     * Constructs ...
     *
     *
     * @param min
     * @param max
     * @param arg0
     * @param arg1
     */
    public ProgressBar(int min, int max, String arg0, GraphicsConfiguration arg1) {
        super(arg0, arg1);
        constructFrame(min, max);
    }

    private void constructFrame(int min, int max) {
        UIManager.put("ProgressBar.foreground", new Color(8, 32, 128));
        setVisible(false);
        setDefaultCloseOperation(3);
        setBounds(400, 400, 600, 50);
        setResizable(false);

        JPanel mainpanel = (JPanel) getContentPane();

        this.bar = new JProgressBar(min, max);
        this.bar.setMinimum(min);
        this.bar.setMaximum(max);
        this.bar.setValue(min);
        this.bar.setStringPainted(true);
        mainpanel.add(this.bar);
        doLayout();
        setVisible(true);
        constructThreadListener();
    }

    private void constructThreadListener() {
        this.runner = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Runnable runme = new Runnable() {
                        @Override
                        public void run() {
                            synchronized (ProgressBar.this.monitor) {
                                ProgressBar.this.bar.setValue(ProgressBar.this.value);
                            }
                        }
                    };

                    SwingUtilities.invokeLater(runme);

                    try {
                        Thread.sleep(100L);
                    } catch (Exception ex) {}
                }
            }
        };
        this.runner.start();
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void update(int value) {
        synchronized (this.monitor) {
            this.value = value;
        }
    }

    /**
     * Method description
     *
     */
    public void close() {
        this.runner = null;
        dispose();
    }
}


//~ Formatted in DD Std on 13/08/26
