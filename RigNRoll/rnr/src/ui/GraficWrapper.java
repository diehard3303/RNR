/*
 * @(#)GraficWrapper.java   13/08/26
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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class GraficWrapper {
    private static final String str_xmin = "Xmin";
    private static final String str_xmax = "Xmax";
    private static final String str_ymin = "Ymin";
    private static final String str_ymax = "Ymax";
    private static final String str_descrfoot = "Descr.";
    private static final String str_valuefoot = "Val.";
    private static final String str_clickedfoot = "clicked";
    private static final String str_scalefoot = "Scale axes";
    private static final String str_autoscale = "Auto";
    private static final String str_nonautoscale = "Edit";
    private static final String str_logfoot = "Log.scales";
    private static final String str_logx = "Log.X";
    private static final String str_nonlogx = "ord.X";
    private static final String str_logy = "Log.Y";
    private static final String str_nonlogy = "ord.Y";
    private final JFrame frame = new JFrame("Graphic");
    private final Graffec grafic = new Graffec();
    private double lastsetXmin = 0.0D;
    private double lastsetYmin = 0.0D;
    private double lastsetXmax = 0.0D;
    private double lastsetYmax = 0.0D;
    private boolean automatic = true;

    private GraficWrapper(int pox0, int poy0, int width, int heigh) {
        constructFrame(pox0, poy0, width, heigh);
    }

    /**
     * Method description
     *
     *
     * @param pox0
     * @param poy0
     * @param width
     * @param heigh
     *
     * @return
     */
    public static final GraficWrapper newGraphec(int pox0, int poy0, int width, int heigh) {
        return new GraficWrapper(pox0, poy0, width, heigh);
    }

    /**
     * Method description
     *
     *
     * @param x
     * @param y
     */
    public void addPoint(double x, double y) {
        this.grafic.add(x, y);
    }

    /**
     * Method description
     *
     *
     * @param y
     * @param name
     */
    public void addHorizontalLine(double y, String name) {
        this.grafic.addHorizontal(y, name);
    }

    /**
     * Method description
     *
     *
     * @param x
     * @param name
     */
    public void addVerticalLine(double x, String name) {
        this.grafic.addVertical(x, name);
    }

    /**
     * Method description
     *
     */
    public void clean() {
        this.grafic.clear();
    }

    /**
     * Method description
     *
     */
    public void refresh() {
        if (this.automatic) {
            this.grafic.makeautomatic();
        }

        updateGrafec();
    }

    void constructFrame(int pox0, int poy0, int width, int heigh) {
        this.grafic.x_min = -1.0D;
        this.grafic.x_max = 20.0D;
        this.grafic.y_min = -1.0D;
        this.grafic.y_max = 20.0D;
        this.frame.setVisible(false);
        this.frame.setDefaultCloseOperation(1);
        this.frame.setBounds(pox0, poy0, width, heigh);
        this.frame.setResizable(false);

        JPanel leftpanel = new JPanel();

        leftpanel.setLayout(new GridLayout(20, 2));
        leftpanel.setMaximumSize(new Dimension(this.frame.getWidth() / 7, this.frame.getHeight()));
        leftpanel.setPreferredSize(new Dimension(this.frame.getWidth() / 7, this.frame.getHeight()));
        leftpanel.setAlignmentY(0.0F);

        JPanel rightpanel = new JPanel();

        rightpanel.setLayout(new GridLayout(1, 1));

        JPanel mainpanel = (JPanel) this.frame.getContentPane();

        mainpanel.setLayout(new BoxLayout(mainpanel, 0));

        JTextField descrfoot = new JTextField("Descr.");
        JTextField valuefoot = new JTextField("Val.");
        JTextField xmin = new JTextField("" + this.grafic.x_min);
        JTextField xmax = new JTextField("" + this.grafic.x_max);
        JTextField ymin = new JTextField("" + this.grafic.y_min);
        JTextField ymax = new JTextField("" + this.grafic.y_max);
        JTextField xmin_descr = new JTextField("Xmin");
        JTextField xmax_descr = new JTextField("Xmax");
        JTextField ymin_descr = new JTextField("Ymin");
        JTextField ymax_descr = new JTextField("Ymax");
        ValuesChangedTracker trakcer = new ValuesChangedTracker(this);

        xmin.addFocusListener(trakcer);
        xmax.addFocusListener(trakcer);
        ymin.addFocusListener(trakcer);
        ymax.addFocusListener(trakcer);
        descrfoot.setEditable(false);
        valuefoot.setEditable(false);
        xmin_descr.setEditable(false);
        xmax_descr.setEditable(false);
        ymin_descr.setEditable(false);
        ymax_descr.setEditable(false);
        leftpanel.add(descrfoot);
        leftpanel.add(valuefoot);
        leftpanel.add(xmin_descr);
        leftpanel.add(xmin);
        leftpanel.add(xmax_descr);
        leftpanel.add(xmax);
        leftpanel.add(ymin_descr);
        leftpanel.add(ymin);
        leftpanel.add(ymax_descr);
        leftpanel.add(ymax);
        leftpanel.add(new JPanel());
        leftpanel.add(new JPanel());

        JTextField clikedfoot = new JTextField("clicked");

        clikedfoot.setEditable(false);
        clikedfoot.setBorder(BorderFactory.createEmptyBorder());
        leftpanel.add(clikedfoot);
        leftpanel.add(new JPanel());

        JTextField x_cliked = new JTextField("" + this.grafic.clicker.click_x_res);

        x_cliked.setEditable(false);

        JTextField y_cliked = new JTextField("" + this.grafic.clicker.click_y_res);

        y_cliked.setEditable(false);
        leftpanel.add(x_cliked);
        leftpanel.add(y_cliked);

        for (int i = 0; i < 10; ++i) {
            leftpanel.add(new JPanel());
        }

        rightpanel.add(this.grafic);
        this.grafic.scalecontrols = new Controls(xmin, xmax, ymin, ymax, x_cliked, y_cliked);

        ButtonGroup automatic_group = new ButtonGroup();
        JRadioButton is_active = new JRadioButton("Auto");
        JRadioButton is_not_active = new JRadioButton("Edit");

        is_not_active.setSelected(!(this.automatic));
        is_active.setSelected(this.automatic);
        automatic_group.add(is_active);
        automatic_group.add(is_not_active);
        is_active.addChangeListener(new ChangeScale(this, is_active));

        ButtonGroup logariphX_group = new ButtonGroup();
        JRadioButton logx_is_active = new JRadioButton("Log.X");
        JRadioButton logx_is_not_active = new JRadioButton("ord.X");

        logx_is_not_active.setSelected(true);
        logariphX_group.add(logx_is_active);
        logariphX_group.add(logx_is_not_active);
        logx_is_active.addChangeListener(new LogariphmicScaleX(this, logx_is_active));

        ButtonGroup logariphY_group = new ButtonGroup();
        JRadioButton logy_is_active = new JRadioButton("Log.Y");
        JRadioButton logy_is_not_active = new JRadioButton("ord.Y");

        logy_is_not_active.setSelected(true);
        logariphY_group.add(logy_is_active);
        logariphY_group.add(logy_is_not_active);
        logy_is_active.addChangeListener(new LogariphmicScaleY(this, logy_is_active));

        JTextField scalefoot = new JTextField("Scale axes");

        scalefoot.setBorder(BorderFactory.createEmptyBorder());
        scalefoot.setEditable(false);
        leftpanel.add(scalefoot);
        leftpanel.add(new JPanel());
        leftpanel.add(is_active);
        leftpanel.add(new JPanel());
        leftpanel.add(is_not_active);
        leftpanel.add(new JPanel());

        JTextField logfoot = new JTextField("Log.scales");

        logfoot.setBorder(BorderFactory.createEmptyBorder());
        logfoot.setEditable(false);
        leftpanel.add(logfoot);
        leftpanel.add(new JPanel());
        leftpanel.add(logx_is_active);
        leftpanel.add(logx_is_not_active);
        leftpanel.add(logy_is_active);
        leftpanel.add(logy_is_not_active);
        mainpanel.add(leftpanel);
        mainpanel.add(rightpanel);
        this.frame.doLayout();
        this.frame.setVisible(true);
    }

    protected void setAutomatic() {
        this.automatic = (!(this.automatic));

        if (this.automatic) {
            this.lastsetXmax = this.grafic.scalecontrols.getXmax();
            this.lastsetYmax = this.grafic.scalecontrols.getYmax();
            this.lastsetXmin = this.grafic.scalecontrols.getXmin();
            this.lastsetYmin = this.grafic.scalecontrols.getYmin();
            this.grafic.makeautomatic();
        } else {
            this.grafic.makeuserdefined(this.lastsetXmin, this.lastsetXmax, this.lastsetYmin, this.lastsetYmax);
        }

        updateGrafec();
    }

    protected void setLogariphmicX() {
        this.grafic.x_logarifmic = (!(this.grafic.x_logarifmic));
        updateGrafec();
    }

    protected void setLogariphmicY() {
        this.grafic.y_logarifmic = (!(this.grafic.y_logarifmic));
        updateGrafec();
    }

    protected void updateGrafec() {
        this.grafic.repaint();
    }

    protected void updateGrafecValues() {
        this.grafic.updateWith(this.grafic.scalecontrols.getXmin(), this.grafic.scalecontrols.getXmax(),
                               this.grafic.scalecontrols.getYmin(), this.grafic.scalecontrols.getYmax());
        updateGrafec();
    }

    static class ChangeScale extends RadioChanged {
        GraficWrapper p;

        ChangeScale(GraficWrapper p, JRadioButton button) {
            super(button);
            this.p = p;
        }

        /**
         * Method description
         *
         *
         * @param e
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            super.stateChanged(e);

            if (this.changed) {
                this.p.setAutomatic();
            }
        }
    }


    static class Controls {
        JTextField xmin = null;
        JTextField xmax = null;
        JTextField ymin = null;
        JTextField ymax = null;
        JTextField x_click = null;
        JTextField y_click = null;

        Controls(JTextField xmin, JTextField xmax, JTextField ymin, JTextField ymax, JTextField x_click,
                 JTextField y_click) {
            this.xmin = xmin;
            this.xmax = xmax;
            this.ymin = ymin;
            this.ymax = ymax;
            this.x_click = x_click;
            this.y_click = y_click;
        }

        void set(double xmin_value, double xmax_value, double ymin_value, double ymax_value) {
            this.xmin.setText("" + xmin_value);
            this.xmax.setText("" + xmax_value);
            this.ymin.setText("" + ymin_value);
            this.ymax.setText("" + ymax_value);
        }

        double getXmin() {
            return Double.parseDouble(this.xmin.getText());
        }

        double getYmin() {
            return Double.parseDouble(this.ymin.getText());
        }

        double getXmax() {
            return Double.parseDouble(this.xmax.getText());
        }

        double getYmax() {
            return Double.parseDouble(this.ymax.getText());
        }

        void setEditable(boolean value) {
            this.xmin.setEditable(value);
            this.xmax.setEditable(value);
            this.ymin.setEditable(value);
            this.ymax.setEditable(value);
        }

        void updateClick(double x, double y) {
            NumberFormat n = NumberFormat.getInstance();

            n.setMaximumFractionDigits(2);

            DecimalFormat d = new DecimalFormat("0.###E0");

            d.setMultiplier(100);

            if (x > 100000.0D) {
                this.x_click.setText(d.format(x));
            } else {
                this.x_click.setText(n.format(x));
            }

            if (x > 100000.0D) {
                this.y_click.setText(d.format(y));
            } else {
                this.y_click.setText(n.format(y));
            }
        }
    }


    static class Graffec extends JComponent {
        static final long serialVersionUID = 1L;
        boolean x_logarifmic = false;
        boolean y_logarifmic = false;
        GraficWrapper.Controls scalecontrols = null;
        ClickShow clicker = null;
        ArrayList points = new ArrayList();
        ArrayList horizontalLines = new ArrayList();
        ArrayList verticalLines = new ArrayList();
        double x_min;
        double x_max;
        double y_min;
        double y_max;

        Graffec() {
            this.clicker = new ClickShow(this);
            addMouseListener(this.clicker);
        }

        int gXlog(double x) {
            double xln = Math.log((x <= 0.0D)
                                  ? 1.0E-007D
                                  : x);
            double xminln = Math.log((this.x_min <= 0.0D)
                                     ? 1.0E-007D
                                     : this.x_min);
            double xmaxln = Math.log((this.x_max <= 0.0D)
                                     ? 1.0E-007D
                                     : this.x_max);

            return (int) (getWidth() * (xln - xminln) / (xmaxln - xminln));
        }

        int gYlog(double y) {
            double yln = Math.log((y <= 0.0D)
                                  ? 1.0E-007D
                                  : y);
            double yminln = Math.log((this.y_min <= 0.0D)
                                     ? 1.0E-007D
                                     : this.y_min);
            double ymaxln = Math.log((this.y_max <= 0.0D)
                                     ? 1.0E-007D
                                     : this.y_max);

            return (int) (getHeight() * (ymaxln - yln) / (ymaxln - yminln));
        }

        int gX(double x) {
            if (this.x_logarifmic) {
                return gXlog(x);
            }

            return (int) (getWidth() * (x - this.x_min) / (this.x_max - this.x_min));
        }

        int gY(double y) {
            if (this.y_logarifmic) {
                return gYlog(y);
            }

            return (int) (getHeight() * (this.y_max - y) / (this.y_max - this.y_min));
        }

        double gXreverse(int x) {
            if (this.x_logarifmic) {
                return gXlogReverse(x);
            }

            return (this.x_min + x * (this.x_max - this.x_min) / getWidth());
        }

        double gYreverse(int y) {
            y = getHeight() - y;

            if (this.y_logarifmic) {
                return gYlogReverse(y);
            }

            return (this.y_min + y * (this.y_max - this.y_min) / getHeight());
        }

        double gXlogReverse(int x) {
            double xminln = Math.log((this.x_min <= 0.0D)
                                     ? 1.0E-007D
                                     : this.x_min);
            double xmaxln = Math.log((this.x_max <= 0.0D)
                                     ? 1.0E-007D
                                     : this.x_max);

            return Math.exp(xminln + x * (xmaxln - xminln) / getWidth());
        }

        double gYlogReverse(int y) {
            double yminln = Math.log((this.y_min <= 0.0D)
                                     ? 1.0E-007D
                                     : this.y_min);
            double ymayln = Math.log((this.y_max <= 0.0D)
                                     ? 1.0E-007D
                                     : this.y_max);

            return Math.exp(yminln + y * (ymayln - yminln) / getHeight());
        }

        void clear() {
            this.points.clear();
            this.horizontalLines.clear();
        }

        void add(double x, double y) {
            this.points.add(new Point(x, y));
        }

        void addHorizontal(double y, String name) {
            this.horizontalLines.add(new HorizontalLine(y, name));
        }

        void addVertical(double x, String name) {
            this.verticalLines.add(new HorizontalLine(x, name));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (this.points.size() < 2) {
                return;
            }

            g.setColor(Color.GREEN);
            g.drawString("Xmin " + ((this.x_min <= 0.0D)
                                    ? 1.0E-007D
                                    : this.x_min), 50, getHeight() - 20);
            g.drawString("Ymin " + ((this.y_min <= 0.0D)
                                    ? 1.0E-007D
                                    : this.y_min), 10, getHeight() - 70);
            g.drawString("Xmax " + ((this.x_max <= 0.0D)
                                    ? 1.0E-007D
                                    : this.x_max), getWidth() - 150, getHeight() - 20);
            g.drawString("Ymax " + ((this.y_max <= 0.0D)
                                    ? 1.0E-007D
                                    : this.y_max), 10, 20);
            g.setColor(Color.BLACK);
            g.drawLine(gX(this.x_min), gY(this.y_min), gX(this.x_min), gY(this.y_max));
            g.drawLine(gX(this.x_min), gY(this.y_min), gX(this.x_max), gY(this.y_min));
            g.setColor(Color.GRAY);

            int num_devide = 10;

            if (num_devide < 2) {
                num_devide = 2;
            }

            for (int i = 1; i < num_devide; ++i) {
                g.drawLine(gX(this.x_min), gY(this.y_min + i * (this.y_max - this.y_min) / num_devide), gX(this.x_max),
                           gY(this.y_min + i * (this.y_max - this.y_min) / num_devide));
                g.drawLine(gX(this.x_min + i * (this.x_max - this.x_min) / num_devide), gY(this.y_min),
                           gX(this.x_min + i * (this.x_max - this.x_min) / num_devide), gY(this.y_max));
            }

            g.setColor(Color.BLUE);

            for (int i = 0; i < this.points.size() - 1; ++i) {
                Point p1 = (Point) this.points.get(i);
                Point p2 = (Point) this.points.get(i + 1);

                g.drawLine(gX(p1.x[0]), gY(p1.x[1]), gX(p2.x[0]), gY(p2.x[1]));
            }

            g.setColor(Color.ORANGE);

            for (int i = 0; i < this.horizontalLines.size(); ++i) {
                HorizontalLine l = (HorizontalLine) this.horizontalLines.get(i);

                g.drawLine(0, gY(l.level), getWidth(), gY(l.level));
                g.drawString(l.name, 10, gY(l.level) + 10);
            }

            g.setColor(Color.RED);

            for (int i = 0; i < this.verticalLines.size(); ++i) {
                HorizontalLine l = (HorizontalLine) this.verticalLines.get(i);

                g.drawLine(gX(l.level), 0, gX(l.level), getHeight());
                g.drawString(l.name, gX(l.level) + 10, 50);
            }

            g.setColor(Color.MAGENTA);

            int radius = 10;

            this.clicker.click_x_res = gXreverse(this.clicker.click_x);
            this.clicker.click_y_res = gYreverse(this.clicker.click_y);
            g.drawOval(this.clicker.click_x - (radius / 2), this.clicker.click_y - (radius / 2), radius, radius);
            this.scalecontrols.updateClick(this.clicker.click_x_res, this.clicker.click_y_res);
        }

        void makeautomatic() {
            if (this.points.size() < 2) {
                return;
            }

            double s_x_min = (1.0D / 0.0D);
            double s_x_max = (-1.0D / 0.0D);
            double s_y_min = (1.0D / 0.0D);
            double s_y_max = (-1.0D / 0.0D);

            for (int i = 0; i < this.points.size(); ++i) {
                Point p1 = (Point) this.points.get(i);

                if (p1.x[0] < s_x_min) {
                    s_x_min = p1.x[0];
                }

                if (p1.x[1] < s_y_min) {
                    s_y_min = p1.x[1];
                }

                if (p1.x[0] > s_x_max) {
                    s_x_max = p1.x[0];
                }

                if (p1.x[1] > s_y_max) {
                    s_y_max = p1.x[1];
                }
            }

            if (s_x_min == s_x_max) {
                s_x_min += -1.0D;
                s_x_max += 1.0D;
            }

            if (s_y_min == s_y_max) {
                s_y_min += -1.0D;
                s_y_max += 1.0D;
            }

            this.scalecontrols.set(s_x_min, s_x_max, s_y_min, s_y_max);
            updateWith(s_x_min, s_x_max, s_y_min, s_y_max);
            this.scalecontrols.setEditable(false);
        }

        void makeuserdefined(double xmin_value, double xmax_value, double ymin_value, double ymax_value) {
            this.scalecontrols.set(xmin_value, xmax_value, ymin_value, ymax_value);
            updateWith(xmin_value, xmax_value, ymin_value, ymax_value);
            this.scalecontrols.setEditable(true);
        }

        void updateWith(double xmin_value, double xmax_value, double ymin_value, double ymax_value) {
            this.x_min = xmin_value;
            this.x_max = xmax_value;
            this.y_min = ymin_value;
            this.y_max = ymax_value;
        }

        static class ClickShow extends MouseAdapter {
            int click_x = 0;
            int click_y = 0;
            double click_x_res = 0.0D;
            double click_y_res = 0.0D;
            GraficWrapper.Graffec p_compenent;

            ClickShow(GraficWrapper.Graffec p_compenent) {
                this.p_compenent = p_compenent;
            }

            /**
             * Method description
             *
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                java.awt.Point p = e.getPoint();

                this.click_x = p.x;
                this.click_y = p.y;
                this.p_compenent.repaint();
            }
        }


        static class HorizontalLine {
            double level;
            String name;

            HorizontalLine(double level, String name) {
                this.level = level;
                this.name = name;
            }
        }


        static class Point {
            double[] x = new double[2];

            Point(double x, double y) {
                this.x[0] = x;
                this.x[1] = y;
            }
        }
    }


    static class LogariphmicScaleX extends RadioChanged {
        GraficWrapper p;

        LogariphmicScaleX(GraficWrapper p, JRadioButton button) {
            super(button);
            this.p = p;
        }

        /**
         * Method description
         *
         *
         * @param e
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            super.stateChanged(e);

            if (this.changed) {
                this.p.setLogariphmicX();
            }
        }
    }


    static class LogariphmicScaleY extends RadioChanged {
        GraficWrapper p;

        LogariphmicScaleY(GraficWrapper p, JRadioButton button) {
            super(button);
            this.p = p;
        }

        /**
         * Method description
         *
         *
         * @param e
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            super.stateChanged(e);

            if (this.changed) {
                this.p.setLogariphmicY();
            }
        }
    }


    static class ValuesChangedTracker extends FocusAdapter {
        GraficWrapper p;

        ValuesChangedTracker(GraficWrapper p) {
            this.p = p;
        }

        /**
         * Method description
         *
         *
         * @param e
         */
        @Override
        public void focusLost(FocusEvent e) {
            this.p.updateGrafecValues();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
