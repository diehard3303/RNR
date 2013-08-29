/*
 * @(#)Anneal.java   13/08/25
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


package rnr.src.annealing;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.ui.ProgressBar;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Random;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class Anneal {
    private static final int FIELDSIZE = 1000;
    private double dT = 1.0D;
    private IMeasure function = null;
    private IRandomSolution randomizer = null;
    private double T = 0.0D;
    private SingleMeasure currentSolution = null;
    private Object[] solutionsField = null;
    private int fieldSize = 0;
    private final Random rnd = new Random();

    /** Field description */
    public ArrayList<SingleMeasure> history = new ArrayList<SingleMeasure>();

    /**
     * Constructs ...
     *
     *
     * @param function
     * @param randomizer
     */
    public Anneal(IMeasure function, IRandomSolution randomizer) {
        this.function = function;
        this.randomizer = randomizer;
    }

    private void updateTemperature() {
        this.T -= this.dT;
    }

    private void initNewSolution() {
        this.currentSolution = new SingleMeasure();
        this.currentSolution.data = this.randomizer.generateInitialSolution();
        this.currentSolution.result = this.function.makeMeasure(this.currentSolution.data);
        this.solutionsField = this.randomizer.generateFieldOfSolutions(1000);
        this.fieldSize = 1000;
    }

    private void setNewSolution(Object S, MeasureResult result) {
        this.history.add(this.currentSolution);
        this.currentSolution = new SingleMeasure();
        this.currentSolution.data = S;
        this.currentSolution.result = result;
        this.solutionsField = this.randomizer.generateFieldOfSolutions(1000);
        this.fieldSize = 1000;
    }

    private Object pickRandomSolution() {
        int num = (int) Math.floor(this.rnd.nextDouble() * this.fieldSize);

        return this.solutionsField[num];
    }

    private boolean acceptNewSolution(double T, MeasureResult res_new, MeasureResult res_old) {
        double D = MeasureResult.difference(res_new, res_old);
        double probability = Math.exp(-D / T);

        return (this.rnd.nextDouble() < probability);
    }

    /**
     * Method description
     *
     *
     * @param T0
     * @param dt
     */
    public void start(double T0, double dt) {
        ProgressBar progress = new ProgressBar(0, (int) T0);

        this.T = T0;
        this.dT = dt;
        initNewSolution();

        while (this.T > 0.0D) {
            Object S1 = pickRandomSolution();
            MeasureResult res = this.function.makeMeasure(S1);

            if (MeasureResult.less(res, this.currentSolution.result)) {
                setNewSolution(S1, res);
            } else if (acceptNewSolution(this.T, res, this.currentSolution.result)) {
                setNewSolution(S1, res);
            }

            progress.update((int) (T0 - this.T));
            updateTemperature();
        }

        this.history.add(this.currentSolution);
        progress.close();
        progress = null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public SingleMeasure getResult() {
        return this.currentSolution;
    }
}


//~ Formatted in DD Std on 13/08/25
