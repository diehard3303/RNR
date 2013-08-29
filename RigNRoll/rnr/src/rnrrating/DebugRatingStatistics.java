/*
 * @(#)DebugRatingStatistics.java   13/08/26
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.annealing.Anneal;
import rnr.src.annealing.IMeasure;
import rnr.src.annealing.IRandomSolution;
import rnr.src.annealing.MeasureResult;
import rnr.src.annealing.SingleMeasure;
import rnr.src.ui.GraficWrapper;
import rnr.src.ui.Message;
import rnr.src.ui.ProgressBar;
import rnr.src.ui.SliderBox;
import rnr.src.ui.ValueChanged;

//~--- JDK imports ------------------------------------------------------------

import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class DebugRatingStatistics {
    protected static DebugRatingStatistics statistica = null;
    private final double a_low = 1.0D;
    private final double b_low = 1.0D;
    private final double c_low = 1.0D;
    private final double L_low = 1.0D;
    private double a_current = 1.0D;
    private double b_current = 1.0D;
    private double c_current = 1.0D;
    private double L_current = 1.0D;
    private final JFrame frame = new JFrame("Statistic");
    Manager M = new Manager();

    /**
     * Method description
     *
     */
    public static final void newMeasure() {
        statistica = new DebugRatingStatistics();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static final boolean iteration() {
        return statistica._iteration();
    }

    /**
     * Method description
     *
     *
     * @param level
     *
     * @return
     */
    public static final int getRating(int level) {
        return (int) statistica.get_value(level);
    }

    /**
     * Method description
     *
     *
     * @param logger
     */
    public static final void printStatistics(DebugRating.TextControl logger) {}

    /**
     * Method description
     *
     */
    public static final void MakeStatisticsLayOut() {
        statistica.constructFrame();
        statistica.buildBonusGraphic();
        statistica.buildRatingGraphic();
    }

    private final double get_value(double level) {
        return (this.L_current * Math.exp(this.a_current * level * level + this.b_current * level + this.c_current));
    }

    private final boolean _iteration() {
        return true;
    }

    private void constructFrame() {
        this.frame.setVisible(false);
        this.frame.setDefaultCloseOperation(1);
        this.frame.setBounds(50, 150, 1000, 200);
        this.frame.setResizable(false);

        JPanel mainpanel = (JPanel) this.frame.getContentPane();

        mainpanel.setLayout(new GridLayout(1, 1));

        JPanel changables = new JPanel();

        changables.setLayout(new GridLayout(1, 5));

        JTextField formule = new JTextField("L*exp(a*l^2 + b*l + c)");

        formule.setEditable(false);

        SliderBox sla = new SliderBox(this.a_current - 10.0D, this.a_current + 10.0D, 100, this.a_current, "A");
        SliderBox slb = new SliderBox(this.b_current - 10.0D, this.b_current + 10.0D, 100, this.b_current, "B");
        SliderBox slc = new SliderBox(this.c_current - 10.0D, this.c_current + 10.0D, 100, this.c_current, "C");
        SliderBox slL = new SliderBox(this.L_current - 10.0D, this.L_current + 10.0D, 100, this.L_current, "L");

        this.M.formulaEdit = new Controls(sla, slb, slc, slL);
        changables.add(formule);
        changables.add(sla);
        changables.add(slb);
        changables.add(slc);
        changables.add(slL);
        mainpanel.add(changables);
        this.frame.doLayout();
        this.frame.setVisible(true);
    }

    protected void buildBonusGraphic() {
        this.M.graficBonus.clean();

        for (int i = 0; i < 6; ++i) {
            WHRating.series_GOLD[i] = getRating(i);
            this.M.graficBonus.addPoint(i, WHRating.series_GOLD[i]);
        }

        this.M.graficBonus.refresh();
    }

    protected void buildRatingGraphic() {
        this.M.graficRating.clean();

        int iteracii = DebugRating.daysforgame / 3;
        int r = BigRace.limits_series[1];
        int i = 1;
        int count = 0;

        while ((i < BigRace.series_highest) && (iteracii > count++)) {
            r += WHRating.series_GOLD[i];
            this.M.graficRating.addPoint(count, r);

            if (r > BigRace.limits_series[(i + 1)]) {
                ;
            }

            ++i;
        }

        for (int iter = 0; iter < 6; ++iter) {
            if (iter == BigRace.series_highest) {
                this.M.graficRating.addHorizontalLine(BigRace.limits_series[iter], "MONSTERCUP");
            } else {
                this.M.graficRating.addHorizontalLine(BigRace.limits_series[iter], "level " + iter);
            }
        }

        this.M.graficRating.refresh();
    }

    protected void renewValues() {
        this.a_current = this.M.formulaEdit._a_coef.slider.getSliderValue();
        this.b_current = this.M.formulaEdit._b_coef.slider.getSliderValue();
        this.c_current = this.M.formulaEdit._c_coef.slider.getSliderValue();
        this.L_current = this.M.formulaEdit._L_coef.slider.getSliderValue();
    }

    static class AnealManager implements IMeasure, IRandomSolution {
        static final double startTemperature = 200.0D;
        static final double fieldDisturbance = 2.0D;
        static final int numInternalLaunches = 3;
        static final int acceptableDayDifference = 90;
        static final int acceptableRacesDifference = 10;
        private final ArrayList history;

        AnealManager() {
            this.history = new ArrayList();
        }

        /**
         * Method description
         *
         *
         * @param number
         *
         * @return
         */
        @Override
        public final Object[] generateFieldOfSolutions(int number) {
            DebugRatingStatistics.Data[] field = new DebugRatingStatistics.Data[number];
            Random rnd = new Random();
            int lim = BigRace.series_highest;
            double[] disturbance = new double[6];

            for (int distur = 0; distur < 6; ++distur) {
                disturbance[distur] = (2.0D
                                       * Math.pow(10.0D,
                                                  Math.floor(0.43429D * Math.log(WHRating.series_GOLD[distur]))));
            }

            for (int i = 0; i < number; ++i) {
                int[] datavalues = new int[6];

                for (int vals = 0; vals < 6; ++vals) {
                    if (WHRating.series_GOLD[vals] <= 1) {
                        datavalues[vals] = ((lim > vals)
                                            ? (int) (WHRating.series_GOLD[vals]
                                                     + disturbance[vals] * (2.0D - (2.0D * rnd.nextDouble())))
                                            : WHRating.series_GOLD[vals]);
                    } else {
                        datavalues[vals] = ((lim > vals)
                                            ? (int) (WHRating.series_GOLD[vals]
                                                     + disturbance[vals] * (1.0D - (2.0D * rnd.nextDouble())))
                                            : WHRating.series_GOLD[vals]);
                    }

                    double count = 2.0D;

                    while ((vals > 0) && (datavalues[vals] <= datavalues[(vals - 1)])) {
                        datavalues[vals] = (int) (datavalues[vals]
                                                  + disturbance[vals] * count * (1.0D - rnd.nextDouble()));
                        count += 1.0D;
                    }
                }

                field[i] = new DebugRatingStatistics.Data(datavalues[0], datavalues[1], datavalues[2], datavalues[3],
                        datavalues[4], datavalues[5]);

                for (int itern = 0; itern < 6; ++itern) {
                    if (field[i].values[itern] < 0.1D * Math.pow(10.0D, itern)) {
                        int tmp354_352 = itern;
                        int[] tmp354_349 = field[i].values;

                        tmp354_349[tmp354_352] = (int) (tmp354_349[tmp354_352] + 2.0D * Math.pow(10.0D, itern));
                    }
                }
            }

            return field;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public final Object generateInitialSolution() {
            if (this.history.isEmpty()) {
                SingleMeasure m = new SingleMeasure();

                m.data = new DebugRatingStatistics.Data(WHRating.series_GOLD[0], WHRating.series_GOLD[1],
                        WHRating.series_GOLD[2], WHRating.series_GOLD[3], WHRating.series_GOLD[4],
                        WHRating.series_GOLD[5]);
                m.result = makeMeasure(m.data);
                this.history.add(m);
            }

            return ((SingleMeasure) this.history.get(this.history.size() - 1)).data;
        }

        /**
         * Method description
         *
         *
         * @param _data
         *
         * @return
         */
        @Override
        public final MeasureResult makeMeasure(Object _data) {
            DebugRatingStatistics.Data data = (DebugRatingStatistics.Data) _data;

            for (int i = 0; i < 6; ++i) {
                WHRating.series_GOLD[i] = data.values[i];
            }

            WHRating.renew();
            DebugRating.renewScreen();

            DebugRating.GameResults[] daysstat = new DebugRating.GameResults[3];

            for (int iter = 0; iter < 3; ++iter) {
                DebugRating.makeDebugStatistics();
                daysstat[iter] = DebugRating.getGameResults();
            }

            return new MeasureResult(computeWeight(daysstat, 3));
        }

        private final int computeWeight(DebugRating.GameResults[] res, int size) {
            int meandays = 0;
            int meanraces = 0;
            int countmeanraces = 0;

            for (int i = 0; i < size; ++i) {
                meandays += ((res[i].daysleft > 0)
                             ? res[i].daysleft
                             : DebugRating.daysforgame);

                if (res[i].daysleft < 0) {
                    continue;
                }

                int itermediate = 0;
                int count = 0;

                for (int j = 1; j < BigRace.series_highest; ++j) {
                    ++count;
                    itermediate += res[i].sp_bc[j];
                }

                ++countmeanraces;
                meanraces += itermediate / count;
            }

            meandays /= size;
            meanraces = (countmeanraces != 0)
                        ? meanraces / countmeanraces
                        : 0;

            int racesdispercion = 0;

            for (int i = 0; i < size; ++i) {
                for (int j = 1; j < BigRace.series_highest; ++j) {
                    if (res[i].daysleft < 0) {
                        continue;
                    }

                    int diffrence = Math.abs(res[i].sp_bc[j] - meanraces);

                    if (diffrence > racesdispercion) {
                        racesdispercion = diffrence;
                    }
                }
            }

            double W1 = (DebugRating.daysforgame - meandays) * Math.exp(-meandays / 90.0D);
            double W2 = Math.exp(-racesdispercion / 10.0D);

            return (int) (W1 * W2);
        }

        private final int maxComputedWeight() {
            return DebugRating.daysforgame;
        }

        static void makeStatistics() {
            Thread runner = new Thread() {
                @Override
                public void run() {
                    DebugRatingStatistics.AnealManager.prmakeStatistics();
                }
            };

            runner.start();
        }

        protected static void prmakeStatistics() {
            ProgressBar PROGRESS = new ProgressBar(0, 10);
            AnealManager man = new AnealManager();

            for (int count_measures = 0; count_measures < 10; ++count_measures) {
                PROGRESS.update(count_measures);

                Anneal stat = new Anneal(man, man);
                double t0 = man.maxComputedWeight() / 2.0D * (1.0D - (count_measures / 9.0D * 0.9D));
                double dt = t0 / 300.0D;

                stat.start(t0, dt);

                StringBuffer History = new StringBuffer("History Buff\n");
                int max_weight = 0;
                int index_max_weight = 0;

                for (int i = 0; i < stat.history.size(); ++i) {
                    SingleMeasure m = stat.history.get(i);

                    History.append("Measure N " + i + ".\tWeight = \t" + m.result.weight + ".\t");

                    DebugRatingStatistics.Data data = (DebugRatingStatistics.Data) m.data;

                    History.append("Data\t\tlvl0 : " + data.values[0] + "\tlvl1 : " + data.values[1] + "\tlvl2 : "
                                   + data.values[2] + "\tlvl3 : " + data.values[3] + "\tlvl4 : " + data.values[4]
                                   + "\tlvl5 : " + data.values[5]);
                    History.append("\n");

                    if (m.result.weight >= max_weight) {
                        max_weight = m.result.weight;
                        index_max_weight = i;
                    }
                }

                man.history.add(stat.history.get(index_max_weight));
            }

            int max_weight = 0;
            int index_max_weight = 0;
            StringBuffer totalHistory = new StringBuffer("TOTAL History Buff\n");

            for (int i = 0; i < man.history.size(); ++i) {
                SingleMeasure m = (SingleMeasure) man.history.get(i);

                totalHistory.append("Measure N " + i + ".\tWeight = \t" + m.result.weight + ".\t");

                DebugRatingStatistics.Data data = (DebugRatingStatistics.Data) m.data;

                totalHistory.append("Data\t\tlvl0 : " + data.values[0] + "\tlvl1 : " + data.values[1] + "\tlvl2 : "
                                    + data.values[2] + "\tlvl3 : " + data.values[3] + "\tlvl4 : " + data.values[4]
                                    + "\tlvl5 : " + data.values[5]);
                totalHistory.append("\n");

                if (m.result.weight >= max_weight) {
                    max_weight = m.result.weight;
                    index_max_weight = i;
                }
            }

            new Message(totalHistory.toString());

            for (int i = 0; i < 6; ++i) {
                WHRating.series_GOLD[i] =
                    ((DebugRatingStatistics.Data) ((SingleMeasure) man.history.get(index_max_weight)).data).values[i];
            }

            WHRating.renew();
            DebugRating.renewScreen();
            PROGRESS.close();
        }
    }


    static class Controls {
        DebugRatingStatistics.TrackSliders _a_coef;
        DebugRatingStatistics.TrackSliders _b_coef;
        DebugRatingStatistics.TrackSliders _c_coef;
        DebugRatingStatistics.TrackSliders _L_coef;

        Controls(SliderBox valuea, SliderBox valueb, SliderBox valuec, SliderBox valueL) {
            this._a_coef = new DebugRatingStatistics.TrackSliders(valuea);
            valuea.assignChangeListener(this._a_coef);
            this._b_coef = new DebugRatingStatistics.TrackSliders(valueb);
            valueb.assignChangeListener(this._b_coef);
            this._c_coef = new DebugRatingStatistics.TrackSliders(valuec);
            valuec.assignChangeListener(this._c_coef);
            this._L_coef = new DebugRatingStatistics.TrackSliders(valueL);
            valuea.assignChangeListener(this._L_coef);
        }
    }


    static class Data {

        /** Field description */
        public int[] values = new int[6];

        Data() {}

        Data(int i1, int i2, int i3, int i4, int i5, int i6) {
            this.values[0] = i1;
            this.values[1] = i2;
            this.values[2] = i3;
            this.values[3] = i4;
            this.values[4] = i5;
            this.values[5] = i6;
        }
    }


    static class Manager {
        DebugRatingStatistics.Controls formulaEdit;
        GraficWrapper graficBonus;
        GraficWrapper graficRating;

        Manager() {
            this.formulaEdit = null;
            this.graficBonus = GraficWrapper.newGraphec(24, 100, 1000, 600);
            this.graficRating = GraficWrapper.newGraphec(24, 100, 1000, 600);
        }
    }


    static class TrackSliders implements ValueChanged {
        SliderBox slider;

        TrackSliders(SliderBox slider) {
            this.slider = slider;
        }

        /**
         * Method description
         *
         */
        @Override
        public void recieveChange() {
            DebugRatingStatistics.statistica.renewValues();
            DebugRatingStatistics.statistica.buildBonusGraphic();
            DebugRatingStatistics.statistica.buildRatingGraphic();
            DebugRating.renewScreen();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
