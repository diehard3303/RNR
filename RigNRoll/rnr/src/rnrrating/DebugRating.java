/*
 * @(#)DebugRating.java   13/08/26
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


package rnr.src.rnrrating;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class DebugRating {
    private static final String desciption_winwill = "Воля к победе";
    private static final String desciption_succesibility = "Успешность";
    private static final String desciption_raceVSdelievery = "Доля гонок VS грузы";
    private static final String desciption_whdelieverydistance = "ср.раст. грузы";
    private static final String desciption_whdelieveryvelocity = "ср.ск. грузы";
    private static final String desciption_roadcontestdistance = "ср.раст. гонки";
    private static final String desciption_roadcontestvelocity = "ср.ск. гонки";
    private static final String desciption_realtimecoefficient = "Коэф. времени";
    private static final String desciption_maxlevelseries = "Макс. уровень";
    private static final String desciption_maxdaystoplay = "Кол-во дней";
    private static final String desciption_penaltycoef = "Коеф. штрафа";
    private static final String desciption_bonus_series = "Награда для ";
    private static final String desciption_limit_series = "Лимин на уровень ";
    private static final String desciption_plug = "-----------------------------";
    static double succesibility = 0.9D;
    static double winwill = 0.5D;
    static double raceVSdelievery = 0.5D;
    static int daysforgame = 365;
    static double whdelieverydistance = 20.0D;
    static double whdelieveryvelocity = 75.0D;
    static double roadcontestdistance = 10.0D;
    static double roadcontestvelocity = 75.0D;
    static double realtimecoefficient = 0.04166666666666666D;
    static TextControl log = null;
    static TextControl logcounter = null;
    private static DebugRating debug;
    static JFrame frame;

    static {
        debug = new DebugRating();
        frame = new JFrame("Rating system");
    }

    IRefreshScreenValues refresher;
    private GameResults formedResults;
    private int countdelieveries;
    private double currentdistance_delievery;
    private boolean buzy_delievery;
    private int countroadraces;
    private double currentdistance_roadcontest;
    private boolean buzy_roadcontest;
    private CoreTime time_win;
    private int time_win_daysdifference;
    private int count_series1;
    private int count_series2;
    private int count_series3;
    private int count_series4;
    private int count_series5;
    private int count_series1_participated;
    private int count_series2_participated;
    private int count_series3_participated;
    private int count_series4_participated;
    private int count_series5_participated;
    private int count_series1_participated_beformonstercup;
    private int count_series2_participated_beformonstercup;
    private int count_series3_participated_beformonstercup;
    private int count_series4_participated_beformonstercup;
    private int count_series5_participated_beformonstercup;
    private final Stack bigraces;
    private final String totalstr;
    private final String scorestr;

    /**
     * Constructs ...
     *
     */
    public DebugRating() {
        this.refresher = null;
        this.formedResults = null;
        this.countdelieveries = 0;
        this.currentdistance_delievery = 0.0D;
        this.buzy_delievery = false;
        this.countroadraces = 0;
        this.currentdistance_roadcontest = 0.0D;
        this.buzy_roadcontest = false;
        this.time_win = null;
        this.time_win_daysdifference = 0;
        this.count_series1 = 0;
        this.count_series2 = 0;
        this.count_series3 = 0;
        this.count_series4 = 0;
        this.count_series5 = 0;
        this.count_series1_participated = 0;
        this.count_series2_participated = 0;
        this.count_series3_participated = 0;
        this.count_series4_participated = 0;
        this.count_series5_participated = 0;
        this.count_series1_participated_beformonstercup = 0;
        this.count_series2_participated_beformonstercup = 0;
        this.count_series3_participated_beformonstercup = 0;
        this.count_series4_participated_beformonstercup = 0;
        this.count_series5_participated_beformonstercup = 0;
        this.bigraces = new Stack();
        this.totalstr = "\tTOTAL rating ";
        this.scorestr = "\tScores ";
    }

    @SuppressWarnings("rawtypes")
    private final void constructFrame() {
        frame.setVisible(false);
        frame.setDefaultCloseOperation(3);
        frame.setBounds(24, 100, 1000, 600);
        frame.setResizable(false);

        JPanel mainpanel = (JPanel) frame.getContentPane();

        mainpanel.setLayout(new GridLayout(2, 2));

        JPanel toppanel = new JPanel();

        toppanel.setLayout(new GridLayout(1, 4));

        JPanel bottompanel = new JPanel();

        bottompanel.setLayout(new GridLayout(1, 2));
        mainpanel.add(toppanel);
        mainpanel.add(bottompanel);

        JPanel input_limits = new JPanel();

        input_limits.setLayout(new GridLayout(7, 2));

        JPanel input_bonus = new JPanel();

        input_bonus.setLayout(new GridLayout(6, 2));

        JPanel input_minor2 = new JPanel();

        input_minor2.setLayout(new GridLayout(8, 2));

        JPanel input_minor = new JPanel();

        input_minor.setLayout(new GridLayout(8, 2));

        JPanel output1 = new JPanel();

        output1.setLayout(new GridLayout(1, 1));

        JPanel output2 = new JPanel();

        output2.setLayout(new GridLayout(1, 1));

        JTextField[] limit_series = new JTextField[6];

        for (int i = 0; i < 6; ++i) {
            JTextField text1 = new JTextField("" + BigRace.limits_series[i]);

            limit_series[i] = text1;
            text1.setBounds(0, 0, 10, 10);

            JTextField text_descr = new JTextField("Лимин на уровень " + i);

            text_descr.setEditable(false);
            input_limits.add(text_descr);
            input_limits.add(text1);
        }

        JTextField[] bonus_series = new JTextField[6];

        for (int i = 0; i < 6; ++i) {
            JTextField text1 = new JTextField("" + WHRating.series_GOLD[i]);

            bonus_series[i] = text1;
            text1.setBounds(0, 0, 10, 10);

            JTextField text_descr = new JTextField("Награда для " + i);

            text_descr.setEditable(false);
            text1.setEditable(false);
            input_bonus.add(text_descr);
            input_bonus.add(text1);
        }

        JTextField txt_succesibility = new JTextField("" + succesibility);
        JTextField txt_winwill = new JTextField("" + winwill);
        JTextField txt_raceVSdelievery = new JTextField("" + raceVSdelievery);
        JTextField txt_whdelieverydistance = new JTextField("" + whdelieverydistance);
        JTextField txt_whdelieveryvelocity = new JTextField("" + whdelieveryvelocity);
        JTextField txt_roadcontestdistance = new JTextField("" + roadcontestdistance);
        JTextField txt_roadcontestvelocity = new JTextField("" + roadcontestvelocity);
        JTextField txt_realtimecoefficient = new JTextField("" + realtimecoefficient);
        JTextField txt_maxlevel = new JTextField("" + BigRace.series_highest);
        JTextField txt_maxdays = new JTextField("" + daysforgame);
        JTextField txt_penaltycoef = new JTextField("" + WHRating.penaltyCoeffecient);
        JTextField txt_plug1 = new JTextField("-----------------------------");
        JTextField txt_plug2 = new JTextField("-----------------------------");
        JTextField txt_plug3 = new JTextField("-----------------------------");
        JTextField txt_plug4 = new JTextField("-----------------------------");
        JTextField txt_succesibility_descr = new JTextField("Успешность");
        JTextField txt_winwill_descr = new JTextField("Воля к победе");
        JTextField txt_raceVSdelievery_descr = new JTextField("Доля гонок VS грузы");
        JTextField txt_whdelieverydistance_descr = new JTextField("ср.раст. грузы");
        JTextField txt_whdelieveryvelocity_descr = new JTextField("ср.ск. грузы");
        JTextField txt_roadcontestdistance_descr = new JTextField("ср.раст. гонки");
        JTextField txt_roadcontestvelocity_descr = new JTextField("ср.ск. гонки");
        JTextField txt_realtimecoefficient_descr = new JTextField("Коэф. времени");
        JTextField txt_maxlevel_descr = new JTextField("Макс. уровень");
        JTextField txt_maxdays_descr = new JTextField("Кол-во дней");
        JTextField txt_penaltycoef_descr = new JTextField("Коеф. штрафа");
        JTextField txt_plug1_descr = new JTextField("-----------------------------");
        JTextField txt_plug2_descr = new JTextField("-----------------------------");
        JTextField txt_plug3_descr = new JTextField("-----------------------------");
        JTextField txt_plug4_descr = new JTextField("-----------------------------");

        txt_succesibility_descr.setEditable(false);
        txt_winwill_descr.setEditable(false);
        txt_raceVSdelievery_descr.setEditable(false);
        txt_whdelieverydistance_descr.setEditable(false);
        txt_whdelieveryvelocity_descr.setEditable(false);
        txt_roadcontestdistance_descr.setEditable(false);
        txt_roadcontestvelocity_descr.setEditable(false);
        txt_realtimecoefficient_descr.setEditable(false);
        txt_maxlevel_descr.setEditable(false);
        txt_maxdays_descr.setEditable(false);
        txt_penaltycoef_descr.setEditable(false);
        txt_plug1.setEditable(false);
        txt_plug2.setEditable(false);
        txt_plug3.setEditable(false);
        txt_plug4.setEditable(false);
        txt_plug1_descr.setEditable(false);
        txt_plug2_descr.setEditable(false);
        txt_plug3_descr.setEditable(false);
        txt_plug4_descr.setEditable(false);
        input_minor.add(txt_succesibility_descr);
        input_minor.add(txt_succesibility);
        input_minor.add(txt_winwill_descr);
        input_minor.add(txt_winwill);
        input_minor.add(txt_raceVSdelievery_descr);
        input_minor.add(txt_raceVSdelievery);
        input_minor.add(txt_whdelieverydistance_descr);
        input_minor.add(txt_whdelieverydistance);
        input_minor.add(txt_whdelieveryvelocity_descr);
        input_minor.add(txt_whdelieveryvelocity);
        input_minor.add(txt_roadcontestdistance_descr);
        input_minor.add(txt_roadcontestdistance);
        input_minor.add(txt_roadcontestvelocity_descr);
        input_minor.add(txt_roadcontestvelocity);
        input_minor2.add(txt_realtimecoefficient_descr);
        input_minor2.add(txt_realtimecoefficient);
        input_minor2.add(txt_maxlevel_descr);
        input_minor2.add(txt_maxlevel);
        input_minor2.add(txt_maxdays_descr);
        input_minor2.add(txt_maxdays);
        input_minor2.add(txt_penaltycoef_descr);
        input_minor2.add(txt_penaltycoef);
        input_minor2.add(txt_plug1);
        input_minor2.add(txt_plug1_descr);
        input_minor2.add(txt_plug2);
        input_minor2.add(txt_plug2_descr);
        input_minor2.add(txt_plug3);
        input_minor2.add(txt_plug3_descr);

        JTextArea textlog = new JTextArea("Result");
        JPanel single = new JPanel();

        single.setBorder(new LineBorder(Color.black, 5));
        single.add(textlog);

        JScrollPane scrolllog = new JScrollPane(single);

        output1.add(scrolllog);
        scrolllog.setVisible(true);

        JTextArea textlogwide = new JTextArea("All events");

        single = new JPanel();
        single.setBorder(new LineBorder(Color.black, 5));
        single.add(textlogwide);
        scrolllog = new JScrollPane(single);
        output2.add(scrolllog);
        scrolllog.setVisible(true);
        log = new TextControl(textlogwide);
        logcounter = new TextControl(textlog);

        JButton runbutton = new JButton("Run");
        run_listener mListener = new run_listener(limit_series, bonus_series, txt_succesibility, txt_winwill,
                                     txt_raceVSdelievery, txt_whdelieverydistance, txt_whdelieveryvelocity,
                                     txt_roadcontestdistance, txt_roadcontestvelocity, txt_realtimecoefficient,
                                     txt_maxlevel, txt_maxdays, txt_penaltycoef);

        this.refresher = mListener;
        runbutton.addMouseListener(mListener);
        input_minor.add(runbutton);

        JButton savebutton = new JButton("Save");

        savebutton.addMouseListener(mListener);
        input_minor.add(savebutton);

        JButton loadbutton = new JButton("Load");

        loadbutton.addMouseListener(mListener);
        input_limits.add(loadbutton);

        JButton probabilityGraphs = new JButton("FineTurning");

        probabilityGraphs.addMouseListener(mListener);
        input_minor2.add(probabilityGraphs);

        JButton statbutton = new JButton("Statistics");

        statbutton.addMouseListener(mListener);
        input_minor2.add(statbutton);

        JComboBox presetslist = run_listener.formJList();

        presetslist.addMouseListener(mListener);
        input_limits.add(presetslist);
        mListener.id_run = runbutton;
        mListener.id_save = savebutton;
        mListener.id_load = loadbutton;
        mListener.id_stat = statbutton;
        mListener.id_prob = probabilityGraphs;
        mListener.initComboBox();
        toppanel.add(input_minor);
        toppanel.add(input_minor2);
        toppanel.add(input_limits);
        toppanel.add(input_bonus);
        bottompanel.add(output1);
        bottompanel.add(output2);
        frame.doLayout();
        frame.setVisible(true);
    }

    /**
     * Method description
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        debug.constructFrame();
    }

    static synchronized void makeDebugStatistics() {
        TextControl.useLog = false;
        BigRace.gReference().startBigRaceSystem();
        debug.process();
        debug.count_series1 = 0;
        debug.count_series2 = 0;
        debug.count_series3 = 0;
        debug.count_series4 = 0;
        debug.count_series5 = 0;
        debug.count_series1_participated = 0;
        debug.count_series2_participated = 0;
        debug.count_series3_participated = 0;
        debug.count_series4_participated = 0;
        debug.count_series5_participated = 0;
        debug.count_series1_participated_beformonstercup = 0;
        debug.count_series2_participated_beformonstercup = 0;
        debug.count_series3_participated_beformonstercup = 0;
        debug.count_series4_participated_beformonstercup = 0;
        debug.count_series5_participated_beformonstercup = 0;
        debug.countdelieveries = 0;
        debug.countroadraces = 0;
        debug.bigraces.clear();
        CoreTime.current_year = 0;
        CoreTime.current_month = 0;
        CoreTime.current_date = 0;
        CoreTime.current_hour = 0;
        CoreTime.current_minuten = 0;
        RateSystem.deinit();
        TextControl.useLog = true;
    }

    static synchronized void makeDebug() {
        BigRace.gReference().startBigRaceSystem();
        debug.process();
        log.close();
        logcounter.close();
        debug.count_series1 = 0;
        debug.count_series2 = 0;
        debug.count_series3 = 0;
        debug.count_series4 = 0;
        debug.count_series5 = 0;
        debug.count_series1_participated = 0;
        debug.count_series2_participated = 0;
        debug.count_series3_participated = 0;
        debug.count_series4_participated = 0;
        debug.count_series5_participated = 0;
        debug.count_series1_participated_beformonstercup = 0;
        debug.count_series2_participated_beformonstercup = 0;
        debug.count_series3_participated_beformonstercup = 0;
        debug.count_series4_participated_beformonstercup = 0;
        debug.count_series5_participated_beformonstercup = 0;
        debug.countdelieveries = 0;
        debug.countroadraces = 0;
        debug.bigraces.clear();
        CoreTime.current_year = 0;
        CoreTime.current_month = 0;
        CoreTime.current_date = 0;
        CoreTime.current_hour = 0;
        CoreTime.current_minuten = 0;
        RateSystem.deinit();
    }

    /**
     * Method description
     *
     */
    public static final void renewScreen() {
        debug.refresher.refreshScreenValues();
    }

    void process() {
        CoreTime timstart = new CoreTime();

        message("Start.\n\t\tTOTAL rating " + RateSystem.gLiveRating() + ".");
        date();

        while (true) {
            int hourmove = 1;

            CoreTime.move_current_on_hour(hourmove);
            delieveryEarnings(hourmove);
            raceEarnings(hourmove);
            bigRaceEarnings();
            BigRace.process();

            int days = CoreTime.CompareByDays(new CoreTime(), timstart);

            if (days > daysforgame) {
                date();
                message("Debug finished. " + days + " days passed.");

                break;
            }
        }

        logcounter.print("\nGame finished.\n");
        logcounter.print("\tTOTAL deliever\t" + this.countdelieveries + ".\n");
        logcounter.print("\tTOTAL roadraces\t" + this.countroadraces + ".\n");
        logcounter.print("\tTOTAL series1\t" + this.count_series1 + ".\t Participated in "
                         + this.count_series1_participated + "(" + this.count_series1_participated_beformonstercup
                         + ")" + ".\n");
        logcounter.print("\tTOTAL series2\t" + this.count_series2 + ".\t Participated in "
                         + this.count_series2_participated + "(" + this.count_series2_participated_beformonstercup
                         + ")" + ".\n");
        logcounter.print("\tTOTAL series3\t" + this.count_series3 + ".\t Participated in "
                         + this.count_series3_participated + "(" + this.count_series3_participated_beformonstercup
                         + ")" + ".\n");
        logcounter.print("\tTOTAL series4\t" + this.count_series4 + ".\t Participated in "
                         + this.count_series4_participated + "(" + this.count_series4_participated_beformonstercup
                         + ")" + ".\n");
        logcounter.print("\tTOTAL series5\t" + this.count_series5 + ".\t Participated in "
                         + this.count_series5_participated + "(" + this.count_series5_participated_beformonstercup
                         + ")" + ".\n");

        if (this.time_win != null) {
            logcounter.print("\n Date is: y " + this.time_win.gYear() + " m " + this.time_win.gMonth() + " d "
                             + this.time_win.gDate() + " h " + this.time_win.gHour());
            this.time_win_daysdifference = CoreTime.CompareByDays(new CoreTime(), this.time_win);
            this.time_win = null;
        } else {
            this.time_win_daysdifference = -1;
        }

        this.formedResults = formGameResults();
    }

    static GameResults getGameResults() {
        return debug.formedResults;
    }

    private GameResults formGameResults() {
        GameResults res = new GameResults();

        res.daysleft = this.time_win_daysdifference;
        res.sp[1] = this.count_series1_participated;
        res.sp_bc[1] = this.count_series1_participated_beformonstercup;
        res.sp[2] = this.count_series2_participated;
        res.sp_bc[2] = this.count_series2_participated_beformonstercup;
        res.sp[3] = this.count_series3_participated;
        res.sp_bc[3] = this.count_series3_participated_beformonstercup;
        res.sp[4] = this.count_series4_participated;
        res.sp_bc[4] = this.count_series4_participated_beformonstercup;
        res.sp[5] = this.count_series5_participated;
        res.sp_bc[5] = this.count_series5_participated_beformonstercup;

        return res;
    }

    private void delieveryEarnings(int hourspassed) {
        if ((!(this.buzy_roadcontest)) && (!(this.buzy_delievery)) && (1.0D - raceVSdelievery > Math.random())) {
            this.buzy_delievery = true;
        }

        if (!(this.buzy_delievery)) {
            return;
        }

        this.currentdistance_delievery += whdelieveryvelocity * realtimecoefficient * hourspassed;

        if (this.currentdistance_delievery > whdelieverydistance) {
            this.buzy_delievery = false;
            this.currentdistance_delievery = 0.0D;
            this.countdelieveries += 1;
            date();
            message("Participate in delievery");
            RateSystem.rateDelievery("LIVE");

            if (succesibility > Math.random()) {
                if (winwill > Math.random()) {
                    int bonus = RateSystem.finish("LIVE", 0);

                    message("Win delievery.\t\t" + this.scorestr + bonus + this.totalstr + RateSystem.gLiveRating()
                            + ".");
                } else if (winwill > Math.random()) {
                    int bonus = RateSystem.finish("LIVE", 1);

                    message("Finished delievery\t" + this.scorestr + bonus + this.totalstr + RateSystem.gLiveRating()
                            + ".");
                } else if (winwill > Math.random()) {
                    int bonus = RateSystem.finish("LIVE", 2);

                    message("Finished delievery\t" + this.scorestr + bonus + this.totalstr + RateSystem.gLiveRating()
                            + ".");
                } else {
                    int bonus = RateSystem.finish("LIVE", 3);

                    message("Finished delievery.\t" + this.scorestr + bonus + this.totalstr + RateSystem.gLiveRating()
                            + ".");
                }
            } else {
                int bonus = RateSystem.fail("LIVE");

                message("Failed delievery.\t" + this.scorestr + bonus + this.totalstr + RateSystem.gLiveRating() + ".");
            }
        }
    }

    private void raceEarnings(int hourspassed) {
        if ((!(this.buzy_roadcontest)) && (!(this.buzy_delievery)) && (raceVSdelievery > Math.random())) {
            this.buzy_roadcontest = true;
        }

        if (!(this.buzy_roadcontest)) {
            return;
        }

        this.currentdistance_roadcontest += roadcontestvelocity * realtimecoefficient * hourspassed;

        if (this.currentdistance_roadcontest > roadcontestdistance) {
            this.buzy_roadcontest = false;
            this.countroadraces += 1;
            this.currentdistance_roadcontest = 0.0D;
            date();
            message("Participate in Road contest");
            RateSystem.rateRoadContest("LIVE");

            if (succesibility > Math.random()) {
                if (winwill > Math.random()) {
                    int bonus = RateSystem.finish("LIVE", 0);

                    message("Win Road race.\t\t" + this.scorestr + bonus + this.totalstr + RateSystem.gLiveRating()
                            + ".");
                } else if (winwill > Math.random()) {
                    int bonus = RateSystem.finish("LIVE", 1);

                    message("Finished Road race.\t" + this.scorestr + bonus + this.totalstr + RateSystem.gLiveRating()
                            + ".");
                } else if (winwill > Math.random()) {
                    int bonus = RateSystem.finish("LIVE", 2);

                    message("Finished Road race.\t" + this.scorestr + bonus + this.totalstr + RateSystem.gLiveRating()
                            + ".");
                } else {
                    int bonus = RateSystem.finish("LIVE", 3);

                    message("Finished Road race.\t" + this.scorestr + bonus + this.totalstr + RateSystem.gLiveRating()
                            + ".");
                }
            } else {
                int bonus = RateSystem.fail("LIVE");

                message("Failed Road race.\t" + this.scorestr + bonus + this.totalstr + RateSystem.gLiveRating() + ".");
            }
        }
    }

    static void sceduleBigRace(int id, int year, int month, int day, int hours, int minuten) {
        switch (id) {
         case 1 :
             debug.count_series1 += 1;

             break;

         case 2 :
             debug.count_series2 += 1;

             break;

         case 3 :
             debug.count_series3 += 1;

             break;

         case 4 :
             debug.count_series4 += 1;

             break;

         case 5 :
             debug.count_series5 += 1;
        }

        debug.bigraces.add(new Bigraceprepared(id, new CoreTime(year, month, day, hours, minuten)));
    }

    private void bigRaceEarnings() {
        if (this.bigraces.empty()) {
            return;
        }

        Bigraceprepared race = (Bigraceprepared) this.bigraces.peek();

        if (new CoreTime().moreThan(race.time) < 0) {
            return;
        }

        if (BigRace.limits_series[race.id] > RateSystem.gLiveRating()) {
            date();
            message("Missing BigRace series " + race.id);
            this.bigraces.pop();

            return;
        }

        switch (race.id) {
         case 1 :
             this.count_series1_participated += 1;

             break;

         case 2 :
             this.count_series2_participated += 1;

             break;

         case 3 :
             this.count_series3_participated += 1;

             break;

         case 4 :
             this.count_series4_participated += 1;

             break;

         case 5 :
             this.count_series5_participated += 1;
        }

        if (race.id == BigRace.series_highest) {
            this.time_win = new CoreTime();
            this.count_series1_participated_beformonstercup = this.count_series1_participated;
            this.count_series2_participated_beformonstercup = this.count_series2_participated;
            this.count_series3_participated_beformonstercup = this.count_series3_participated;
            this.count_series4_participated_beformonstercup = this.count_series4_participated;
            this.count_series5_participated_beformonstercup = this.count_series5_participated;
        }

        date();
        message("Participate in BigRace series " + race.id);
        RateSystem.rateBigRace("LIVE", race.id);

        if (succesibility > Math.random()) {
            if (winwill > Math.random()) {
                int bonus = RateSystem.finish("LIVE", 0);

                message("Win BigRace series " + race.id + "\t" + this.scorestr + bonus + this.totalstr
                        + RateSystem.gLiveRating() + ".");
            } else if (winwill > Math.random()) {
                int bonus = RateSystem.finish("LIVE", 1);

                message("Finished BigRace series " + race.id + this.scorestr + bonus + this.totalstr
                        + RateSystem.gLiveRating() + ".");
            } else if (winwill > Math.random()) {
                int bonus = RateSystem.finish("LIVE", 2);

                message("Finished BigRace series " + race.id + this.scorestr + bonus + this.totalstr
                        + RateSystem.gLiveRating() + ".");
            } else {
                int bonus = RateSystem.finish("LIVE", 3);

                message("Finished BigRace series " + race.id + this.scorestr + bonus + this.totalstr
                        + RateSystem.gLiveRating() + ".");
            }
        } else {
            int bonus = RateSystem.fail("LIVE");

            message("Failes BigRace series " + race.id + "." + this.scorestr + bonus + this.totalstr
                    + RateSystem.gLiveRating() + ".");
        }

        this.bigraces.pop();
    }

    void message(String messaga) {
        log.print("\n " + messaga);
    }

    void date() {
        CoreTime time = new CoreTime();

        log.print("\n Date is: y " + time.gYear() + " m " + time.gMonth() + " d " + time.gDate() + " h "
                  + time.gHour());
    }

    static abstract interface IRefreshScreenValues {

        /**
         * Method description
         *
         */
        public abstract void refreshScreenValues();
    }


    private static class Bigraceprepared {
        int id;
        DebugRating.CoreTime time;

        Bigraceprepared(int id, DebugRating.CoreTime time) {
            this.id = id;
            this.time = time;
        }
    }


    static class CoreTime {
        static int current_year;
        static int current_month;
        static int current_date;
        static int current_hour;
        static int current_minuten;
        int year;
        int month;
        int date;
        int hour;
        int minuten;

        /**
         * Constructs ...
         *
         */
        public CoreTime() {
            update();
        }

        /**
         * Constructs ...
         *
         *
         * @param copy
         */
        public CoreTime(CoreTime copy) {
            this.year = copy.year;
            this.month = copy.month;
            this.date = copy.date;
            this.hour = copy.hour;
            this.minuten = copy.minuten;
        }

        /**
         * Constructs ...
         *
         *
         * @param year
         * @param month
         * @param date
         * @param hour
         * @param minuten
         */
        public CoreTime(int year, int month, int date, int hour, int minuten) {
            this.year = year;
            this.month = month;
            this.date = date;
            this.hour = hour;
            this.minuten = minuten;
        }

        static void move_current_on_hour(int hours) {
            CoreTime time = new CoreTime();

            time.plus(hours(hours));
            current_year = time.gYear();
            current_month = time.gMonth();
            current_date = time.gDate();
            current_hour = time.gHour();
            current_minuten = time.gMinute();
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int gDate() {
            return this.date;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int gHour() {
            return this.hour;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int gMinute() {
            return this.minuten;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int gMonth() {
            return this.month;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int gYear() {
            return this.year;
        }

        void update() {
            this.year = current_year;
            this.month = current_month;
            this.date = current_date;
            this.hour = current_hour;
            this.minuten = current_minuten;
        }

        /**
         * Method description
         *
         *
         * @param copy
         */
        public void plus(CoreTime copy) {
            this.minuten += copy.minuten;

            if (this.minuten >= 60) {
                this.minuten -= 60;
                this.hour += 1;
            }

            this.hour += copy.hour;

            if (this.hour >= 24) {
                this.hour -= 24;
                this.date += 1;
            }

            this.date += copy.date;

            int datelimit = 30;

            switch (this.month) {
             case 1 :
             case 3 :
             case 5 :
             case 7 :
             case 8 :
             case 10 :
             case 12 :
                 datelimit = 31;

                 break;

             case 2 :
                 datelimit = 28;
             case 4 :
             case 6 :
             case 9 :
             case 11 :
            }

            if (this.date >= datelimit) {
                this.date -= datelimit;
                this.month += 1;
            }

            this.month += copy.month;

            if (this.month >= 13) {
                this.month -= 12;
                this.year += 1;
            }

            this.year += copy.year;
        }

        /**
         * Method description
         *
         *
         * @param tm
         *
         * @return
         */
        public int moreThan(CoreTime tm) {
            if (this.year != tm.year) {
                return (this.year - tm.year);
            }

            if (this.month != tm.month) {
                return (this.month - tm.month);
            }

            if (this.date != tm.date) {
                return (this.date - tm.date);
            }

            if (this.hour != tm.hour) {
                return (this.hour - tm.hour);
            }

            if (this.minuten != tm.minuten) {
                return (this.minuten - tm.minuten);
            }

            return 0;
        }

        /**
         * Method description
         *
         *
         * @param tm
         * @param deltatime
         *
         * @return
         */
        public final int moreThanOnTime(CoreTime tm, CoreTime deltatime) {
            CoreTime ct = new CoreTime(tm);

            ct.plus(deltatime);

            return moreThan(ct);
        }

        /**
         * Method description
         *
         *
         * @param _days
         *
         * @return
         */
        public static CoreTime days(int _days) {
            return new CoreTime(0, 0, _days, 0, 0);
        }

        /**
         * Method description
         *
         *
         * @param _days
         * @param _hours
         *
         * @return
         */
        public static CoreTime daysNhours(int _days, int _hours) {
            return new CoreTime(0, 0, _days, _hours, 0);
        }

        /**
         * Method description
         *
         *
         * @param _hours
         *
         * @return
         */
        public static CoreTime hours(int _hours) {
            return new CoreTime(0, 0, 0, _hours, 0);
        }

        /**
         * Method description
         *
         *
         * @param _mon
         *
         * @return
         */
        public static CoreTime monthes(int _mon) {
            return new CoreTime(0, _mon, 0, 0, 0);
        }

        /**
         * Method description
         *
         *
         * @param _mon
         * @param _days
         *
         * @return
         */
        public static CoreTime monthesNDays(int _mon, int _days) {
            return new CoreTime(0, _mon, _days, 0, 0);
        }

        /**
         * Method description
         *
         *
         * @param time1incoming
         * @param time2incoming
         *
         * @return
         */
        public static final int CompareByDays(CoreTime time1incoming, CoreTime time2incoming) {
            boolean order = time1incoming.moreThan(time2incoming) != 0;
            CoreTime time1 = null;
            CoreTime time2 = null;

            if (order) {
                time1 = time1incoming;
                time2 = time2incoming;
            } else {
                time1 = time2incoming;
                time2 = time1incoming;
            }

            int res = 0;

            if (time1.year > time2.year) {
                res += (time1.year - time2.year - 1) * 365;
            }

            if (time1.month > time2.month) {
                if (time1.year > time2.year) {
                    res += 365;
                }

                int month = time2.month;
                int day = time2.date;

                while (month != time1.month) {
                    res += count_days_tillend(month, day);
                    day = 1;
                    ++month;
                }

                res += time1.date;
            } else if (time1.month < time2.month) {
                int month = time1.month;
                int day = time1.date;

                while (month != 12) {
                    res += count_days_tillend(month, day);
                    day = 1;
                    ++month;
                }

                month = 0;
                day = 1;

                while (month != time2.month) {
                    res += count_days_tillend(month, day);
                    ++month;
                }

                res += time2.date;
            } else if (time1.date > time2.date) {
                res += time1.date - time2.date;
            } else if (time1.date < time2.date) {
                res += time1.date - time2.date + 365;
            } else {
                res = 0;
            }

            return ((order)
                    ? res
                    : -res);
        }

        static int count_days_tillend(int frommonth, int fromday) {
            switch (frommonth) {
             case 0 :
             case 2 :
             case 4 :
             case 6 :
             case 7 :
             case 9 :
             case 11 :
                 return (31 - fromday);

             case 3 :
             case 5 :
             case 8 :
             case 10 :
                 return (30 - fromday);

             case 1 :
                 return (28 - fromday);
            }

            return 0;
        }
    }


    static class File {
        private FileOutputStream file = null;
        private PrintStream stream = null;

        File(String filename) {
            try {
                this.file = new FileOutputStream(filename);
                this.stream = new PrintStream(this.file);
            } catch (Exception e) {
                System.out.print(filename);
            }
        }

        void close() {
            try {
                this.file.flush();
                this.file.close();
            } catch (Exception e) {
                System.out.print("Error occured. " + e.toString());
            }
        }

        void print(String text) {
            this.stream.print(text);
        }
    }


    static class GameResults {
        int[] sp;
        int[] sp_bc;
        int daysleft;

        GameResults() {
            this.sp = new int[6];
            this.sp_bc = new int[6];
        }
    }


    static class TextControl {
        static boolean useLog = true;
        private JTextArea file = null;
        private StringBuffer stream = null;

        TextControl(JTextArea file) {
            this.file = file;
            this.stream = new StringBuffer();
        }

        void close() {
            if (!(useLog)) {
                return;
            }

            this.file.setText(this.stream.toString());
            this.stream = new StringBuffer();
        }

        void print(String text) {
            if (!(useLog)) {
                return;
            }

            this.stream.append(text);
        }
    }


    static class run_listener extends MouseInputAdapter implements DebugRating.IRefreshScreenValues {
        static presetscontainer allpresets = new presetscontainer();
        private static JComboBox list;

        /** Field description */
        public Component id_run;

        /** Field description */
        public Component id_save;

        /** Field description */
        public Component id_load;

        /** Field description */
        public Component id_stat;

        /** Field description */
        public Component id_prob;
        JTextField[] limit_series;
        JTextField[] bonus_series;
        JTextField txt_penalty_coeff;
        JTextField txt_succesibility;
        JTextField txt_winwill;
        JTextField txt_raceVSdelievery;
        JTextField txt_whdelieverydistance;
        JTextField txt_whdelieveryvelocity;
        JTextField txt_roadcontestdistance;
        JTextField txt_roadcontestvelocity;
        JTextField txt_realtimecoefficient;
        JTextField txt_maxlevel;
        JTextField txt_maxdays;

        run_listener(JTextField[] limit_series, JTextField[] bonus_series, JTextField txt_succesibility,
                     JTextField txt_winwill, JTextField txt_raceVSdelievery, JTextField txt_whdelieverydistance,
                     JTextField txt_whdelieveryvelocity, JTextField txt_roadcontestdistance,
                     JTextField txt_roadcontestvelocity, JTextField txt_realtimecoefficient, JTextField txt_maxlevel,
                     JTextField txt_maxdays, JTextField txt_penalty_coeff) {
            this.limit_series = limit_series;
            this.bonus_series = bonus_series;
            this.txt_penalty_coeff = txt_penalty_coeff;
            this.txt_succesibility = txt_succesibility;
            this.txt_winwill = txt_winwill;
            this.txt_raceVSdelievery = txt_raceVSdelievery;
            this.txt_whdelieverydistance = txt_whdelieverydistance;
            this.txt_whdelieveryvelocity = txt_whdelieveryvelocity;
            this.txt_roadcontestdistance = txt_roadcontestdistance;
            this.txt_roadcontestvelocity = txt_roadcontestvelocity;
            this.txt_realtimecoefficient = txt_realtimecoefficient;
            this.txt_maxlevel = txt_maxlevel;
            this.txt_maxdays = txt_maxdays;
        }

        /**
         * Method description
         *
         */
        @Override
        public void refreshScreenValues() {
            renewvalues();
        }

        /**
         * Method description
         *
         *
         * @param arg0
         */
        @Override
        public void mouseClicked(MouseEvent arg0) {
            arg0.consume();
            super.mouseClicked(arg0);

            if (this.id_run.equals(arg0.getComponent())) {
                renewvalues();
                DebugRating.makeDebug();
            } else if (this.id_save.equals(arg0.getComponent())) {
                entername();
            } else if (this.id_load.equals(arg0.getComponent())) {
                String name = (String) list.getSelectedItem();
                preset cach = allpresets.get(name);

                fillWithPreset(cach);
                renewvaluesALL();
            } else if (this.id_stat.equals(arg0.getComponent())) {
                renewvaluesALL();
                DebugRatingStatistics.AnealManager.makeStatistics();
            } else if (this.id_prob.equals(arg0.getComponent())) {
                DebugRatingStatistics.newMeasure();
                DebugRatingStatistics.MakeStatisticsLayOut();
            }
        }

        private void entername() {
            JFrame frame = new JFrame("Enter preset name...");

            frame.setResizable(false);
            frame.setBounds(300, 300, 200, 100);
            frame.setVisible(false);

            JPanel panel = (JPanel) frame.getContentPane();

            panel.setLayout(new GridLayout(2, 1));

            JTextField nametext = new JTextField(c_entername.lastname);

            nametext.setEditable(true);
            panel.add(nametext);

            JButton ok = new JButton("OK");

            ok.addMouseListener(new c_entername(this, frame, nametext));
            panel.add(ok);
            frame.setVisible(true);
        }

        private void renewvaluesALL() {
            for (int i = 0; i < 6; ++i) {
                WHRating.series_GOLD[i] = Integer.decode(this.bonus_series[i].getText()).intValue();
            }

            WHRating.renew();
            renewvalues();
        }

        private void renewvalues() {
            for (int i = 0; i < 6; ++i) {
                BigRace.limits_series[i] = Integer.decode(this.limit_series[i].getText()).intValue();
                this.bonus_series[i].setText("" + WHRating.series_GOLD[i]);
            }

            WHRating.renew();
            DebugRating.succesibility = Double.parseDouble(this.txt_succesibility.getText());
            DebugRating.winwill = Double.parseDouble(this.txt_winwill.getText());
            DebugRating.raceVSdelievery = Double.parseDouble(this.txt_raceVSdelievery.getText());
            DebugRating.whdelieverydistance = Double.parseDouble(this.txt_whdelieverydistance.getText());
            DebugRating.whdelieveryvelocity = Double.parseDouble(this.txt_whdelieveryvelocity.getText());
            DebugRating.roadcontestdistance = Double.parseDouble(this.txt_roadcontestdistance.getText());
            DebugRating.roadcontestvelocity = Double.parseDouble(this.txt_roadcontestvelocity.getText());
            DebugRating.realtimecoefficient = Double.parseDouble(this.txt_realtimecoefficient.getText());
            BigRace.series_highest = Integer.decode(this.txt_maxlevel.getText()).intValue();
            DebugRating.daysforgame = Integer.decode(this.txt_maxdays.getText()).intValue();
        }

        private void fillWithPreset(preset cach) {
            for (int i = 0; i < 6; ++i) {
                this.limit_series[i].setText("" + cach.int_preserts[i]);
                this.bonus_series[i].setText("" + cach.int_preserts[(i + 6)]);
            }

            this.txt_maxlevel.setText("" + cach.int_preserts[12]);
            this.txt_maxdays.setText("" + cach.int_preserts[13]);
            this.txt_succesibility.setText("" + cach.double_presets[0]);
            this.txt_winwill.setText("" + cach.double_presets[1]);
            this.txt_raceVSdelievery.setText("" + cach.double_presets[2]);
            this.txt_whdelieverydistance.setText("" + cach.double_presets[3]);
            this.txt_whdelieveryvelocity.setText("" + cach.double_presets[4]);
            this.txt_roadcontestdistance.setText("" + cach.double_presets[5]);
            this.txt_roadcontestvelocity.setText("" + cach.double_presets[6]);
            this.txt_realtimecoefficient.setText("" + cach.double_presets[7]);
            this.txt_penalty_coeff.setText("" + cach.double_presets[8]);
        }

        protected preset formPreset() {
            int[] lim_ints = new int[6];
            int[] bonus_ints = new int[6];

            for (int i = 0; i < 6; ++i) {
                lim_ints[i] = Integer.decode(this.limit_series[i].getText()).intValue();
                bonus_ints[i] = Integer.decode(this.bonus_series[i].getText()).intValue();
            }

            return new preset(lim_ints, bonus_ints, Double.parseDouble(this.txt_succesibility.getText()),
                              Double.parseDouble(this.txt_winwill.getText()),
                              Double.parseDouble(this.txt_raceVSdelievery.getText()),
                              Double.parseDouble(this.txt_whdelieverydistance.getText()),
                              Double.parseDouble(this.txt_whdelieveryvelocity.getText()),
                              Double.parseDouble(this.txt_roadcontestdistance.getText()),
                              Double.parseDouble(this.txt_roadcontestvelocity.getText()),
                              Double.parseDouble(this.txt_realtimecoefficient.getText()),
                              Integer.decode(this.txt_maxlevel.getText()).intValue(),
                              Integer.decode(this.txt_maxdays.getText()).intValue(),
                              Double.parseDouble(this.txt_penalty_coeff.getText()));
        }

        private static void load_presets() {
            try {
                FileInputStream stream = new FileInputStream("presets.sav");
                ObjectInputStream obj = new ObjectInputStream(stream);

                allpresets = (presetscontainer) obj.readObject();
                obj.close();
            } catch (Exception e) {
                System.out.print("Cannot serialize presets load. Error " + e.toString());
            }
        }

        protected static void save_presets() {
            try {
                FileOutputStream stream = new FileOutputStream("presets.sav");
                ObjectOutputStream obj = new ObjectOutputStream(stream);

                obj.writeObject(allpresets);
                obj.close();
            } catch (Exception e) {
                System.out.print("Cannot serialize presets save. Error " + e.toString());
            }
        }

        void refillComboBox(String lastname) {
            Object[] lstobjects = allpresets.getEntries();

            list.removeAllItems();

            for (int i = 0; i < lstobjects.length; ++i) {
                list.addItem(lstobjects[i]);
            }

            list.setSelectedItem(lastname);
        }

        void initComboBox() {
            load_presets();

            preset default_preset = formPreset();

            allpresets.add("default", default_preset);
            refillComboBox("default");
        }

        static JComboBox formJList() {
            list = new JComboBox();

            return list;
        }

        static class c_entername extends MouseInputAdapter {
            static String lastname = "noname";
            DebugRating.run_listener papa;
            JFrame frame;
            JTextField text;

            c_entername(DebugRating.run_listener papa, JFrame frame, JTextField text) {
                this.papa = papa;
                this.frame = frame;
                this.text = text;
            }

            /**
             * Method description
             *
             *
             * @param arg0
             */
            @Override
            public void mouseClicked(MouseEvent arg0) {
                String name = this.text.getText();

                if (name.compareToIgnoreCase("") == 0) {
                    name = "noname";
                }

                lastname = name;
                DebugRating.run_listener.allpresets.add(lastname, this.papa.formPreset());
                DebugRating.run_listener.save_presets();
                this.papa.refillComboBox(lastname);
                this.frame.dispose();
                this.frame = null;
            }
        }


        static class preset implements Serializable {
            static final long serialVersionUID = 1235L;
            int[] int_preserts = new int[14];
            double[] double_presets = new double[9];

            preset() {}

            preset(int[] limits, int[] bonuses, double txt_succesibility, double txt_winwill,
                   double txt_raceVSdelievery, double txt_whdelieverydistance, double txt_whdelieveryvelocity,
                   double txt_roadcontestdistance, double txt_roadcontestvelocity, double txt_realtimecoefficient,
                   int max_level, int max_days, double penlty_coeff) {
                for (int i = 0; i < 6; ++i) {
                    this.int_preserts[i] = limits[i];
                    this.int_preserts[(i + 6)] = bonuses[i];
                }

                this.int_preserts[12] = max_level;
                this.int_preserts[13] = max_days;
                this.double_presets[0] = txt_succesibility;
                this.double_presets[1] = txt_winwill;
                this.double_presets[2] = txt_raceVSdelievery;
                this.double_presets[3] = txt_whdelieverydistance;
                this.double_presets[4] = txt_whdelieveryvelocity;
                this.double_presets[5] = txt_roadcontestdistance;
                this.double_presets[6] = txt_roadcontestvelocity;
                this.double_presets[7] = txt_realtimecoefficient;
                this.double_presets[8] = penlty_coeff;
            }
        }


        static class presetscontainer implements Serializable {
            static final long serialVersionUID = 12340L;
            static final String filename = "presets.sav";
            static final String DEFAULT = "default";
            private final HashMap all_presets;

            presetscontainer() {
                this.all_presets = new HashMap();
            }

            /**
             * Method description
             *
             *
             * @param name
             * @param cach
             */
            public void add(String name, DebugRating.run_listener.preset cach) {
                this.all_presets.put(name, cach);
            }

            /**
             * Method description
             *
             *
             * @param name
             *
             * @return
             */
            public DebugRating.run_listener.preset get(String name) {
                DebugRating.run_listener.preset res = null;

                res = (DebugRating.run_listener.preset) this.all_presets.get(name);

                return res;
            }

            /**
             * Method description
             *
             *
             * @return
             */
            public Object[] getEntries() {
                Set set = this.all_presets.keySet();

                return set.toArray();
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/26
