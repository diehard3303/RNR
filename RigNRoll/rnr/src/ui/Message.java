/*
 * @(#)Message.java   13/08/26
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

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Message extends JFrame {
    static final long serialVersionUID = 1L;
    private static int lastX = 10;
    private static int lastY = 10;
    private static final int maxX = 1000;
    private static final int maxY = 600;

    /**
     * Constructs ...
     *
     *
     * @param name
     *
     * @throws HeadlessException
     */
    public Message(String name) throws HeadlessException {
        constructFrame(name);
    }

    /**
     * Constructs ...
     *
     *
     * @param name
     * @param arg0
     */
    public Message(String name, GraphicsConfiguration arg0) {
        super(arg0);
        constructFrame(name);
    }

    /**
     * Constructs ...
     *
     *
     * @param name
     * @param arg0
     *
     * @throws HeadlessException
     */
    public Message(String name, String arg0) throws HeadlessException {
        super(arg0);
        constructFrame(name);
    }

    /**
     * Constructs ...
     *
     *
     * @param name
     * @param arg0
     * @param arg1
     */
    public Message(String name, String arg0, GraphicsConfiguration arg1) {
        super(arg0, arg1);
        constructFrame(name);
    }

    static void Display(String message) {
        new Message(message, "message");
    }

    private int gX() {
        lastX += 150;

        if (lastX > 1000) {
            lastX = 10;
        }

        return lastX;
    }

    private int gY() {
        lastY += 150;

        if (lastY > 600) {
            lastY = 10;
        }

        return lastY;
    }

    private void constructFrame(String name) {
        setVisible(false);
        setDefaultCloseOperation(3);
        setBounds(gX(), gY(), 400, 200);
        setResizable(false);

        JPanel mainpanel = (JPanel) getContentPane();

        mainpanel.setLayout(new BoxLayout(mainpanel, 1));
        mainpanel.setAlignmentX(0.5F);

        JTextArea text = new JTextArea(name);

        text.setBorder(BorderFactory.createLoweredBevelBorder());
        text.setAlignmentX(0.5F);

        JPanel pn1 = new JPanel();

        pn1.add(text);

        JScrollPane scroll = new JScrollPane(pn1);
        JButton OK = new JButton("LADNO");
        MouseListener list = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Message.this.dispose();
            }
        };

        OK.addMouseListener(list);
        OK.setAlignmentX(0.5F);
        mainpanel.add(scroll);
        mainpanel.add(OK);
        doLayout();
        setVisible(true);
    }
}


//~ Formatted in DD Std on 13/08/26
