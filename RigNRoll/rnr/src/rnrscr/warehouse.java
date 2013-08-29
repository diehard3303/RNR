/*
 * @(#)warehouse.java   13/08/28
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


package rnr.src.rnrscr;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SCRwarehousecrane;
import rnr.src.rnrcore.SCRwarehousoperator;
import rnr.src.rnrcore.vectorJ;

//~--- JDK imports ------------------------------------------------------------

import java.util.Random;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class warehouse extends animation {
    private static warehouse instance = new warehouse();
    static int NOMOPERATORMODELS = 5;
    static SCRwarehousoperator WHOPER;
    static SCRwarehousecrane whCrane;
    private long[] m_operators = null;
    private long[] m_cranes = null;
    protected boolean wereModelsLoaded = false;

    /**
     * Method description
     *
     *
     * @return
     */
    public static warehouse getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public long[] getOperators() {
        return this.m_operators;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public long[] getCranes() {
        return this.m_cranes;
    }

    /**
     * Method description
     *
     *
     * @param PERSONAGE1
     * @param possit
     * @param dirrit
     */
    public void PlacePerson(SCRuniperson PERSONAGE1, vectorJ possit, vectorJ dirrit) {
        PERSONAGE1.SetPosition(possit);
        PERSONAGE1.SetDirection(dirrit);
    }

    /**
     * Method description
     *
     *
     * @param PERSONAGE1
     * @param possit
     * @param dirrit
     */
    public void PlaceOperator(SCRwarehousoperator PERSONAGE1, vectorJ possit, vectorJ dirrit) {
        PERSONAGE1.SetPosition(possit);
        PERSONAGE1.SetDirection(dirrit);
    }

    /**
     * Method description
     *
     *
     * @param carne
     * @param possit
     * @param dirrit
     */
    public void PlaceCrane(SCRwarehousecrane carne, vectorJ possit, vectorJ dirrit) {
        carne.SetPosition(possit);
        carne.SetDirection(dirrit);
    }

    /**
     * Method description
     *
     *
     * @param poss
     * @param dirs
     * @param poss_crane
     * @param dirs_crane
     * @param poss_crane1
     * @param dirs_crane1
     *
     * @return
     */
    public long[] WHoperator(vectorJ[] poss, vectorJ[] dirs, vectorJ[] poss_crane, vectorJ[] dirs_crane,
                             vectorJ[] poss_crane1, vectorJ[] dirs_crane1) {
        CreateAnimation animcreation = new CreateAnimation();
        int pos_sz = poss.length;

        this.m_operators = new long[2 * pos_sz];
        this.m_cranes = new long[2 * pos_sz];

        int[] dg = Shuffle(pos_sz, 1);
        long[] rs = new long[pos_sz];
        int iter = 0;

        for (int pers = 0; pers < pos_sz; ++pers) {
            animcreation.operator(pers);
            WHOPER.play();
            animcreation.crane();
            rs[pers] = WHOPER.nativePointer;
            PlaceCrane(whCrane, poss_crane[dg[pers]], dirs_crane[dg[pers]]);
            PlaceOperator(WHOPER, poss[dg[pers]], dirs[dg[pers]]);
            whCrane.MoveMain(100.0D, 25.0D, "forward");
            this.m_operators[iter] = WHOPER.nativePointer;
            this.m_cranes[iter] = whCrane.nativePointer;
            ++iter;
            animcreation.operator(pers);
            animcreation.crane();
            PlaceCrane(whCrane, poss_crane1[dg[pers]], dirs_crane1[dg[pers]]);
            PlaceOperator(WHOPER, poss[dg[pers]], dirs[dg[pers]]);
            whCrane.MoveMain(100.0D, 25.0D, "forward");
            this.m_operators[iter] = WHOPER.nativePointer;
            this.m_cranes[iter] = whCrane.nativePointer;
            ++iter;
        }

        return rs;
    }

    /**
     * Method description
     *
     *
     * @param sz
     * @param prohids
     *
     * @return
     */
    public int[] Shuffle(int sz, int prohids) {
        int[] intArr = new int[sz];

        for (int i0 = 0; i0 < sz; ++i0) {
            intArr[i0] = i0;
        }

        Random rnd_ch = new Random();

        for (int pr = 0; pr < prohids; ++pr) {
            for (int i = 0; i < sz; ++i) {
                int replace = rnd_ch.nextInt(sz - i) + i;
                int prev = intArr[i];

                intArr[i] = intArr[replace];
                intArr[replace] = prev;
            }
        }

        return intArr;
    }

    /**
     * Method description
     *
     *
     * @param vehpos
     * @param vehdir
     *
     * @return
     */
    public long testcrane(vectorJ vehpos, vectorJ vehdir) {
        SCRwarehousoperator ope = new SCRwarehousoperator();

        ope.nWarehousoperator("Man_001");

        SCRwarehousecrane cran = new SCRwarehousecrane();

        cran.nWarehousecrane("model_WarehouseCrane", "Space_Crane_Main");
        cran.FindCraneParts("Space_Crane");

        vectorJ dir0 = vehdir;
        vectorJ dir = new vectorJ(dir0);

        dir.x = (-dir0.y);
        dir.y = dir0.x;

        vectorJ pos = dir0.normN();

        pos.x *= -20.0D;
        pos.y *= -20.0D;
        pos.z += 10.0D;
        pos.oPlus(vehpos);
        PlaceCrane(cran, pos, dir);
        ope.TakeCrane(cran);

        return ope.nativePointer;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public class CreateAnimation {

        /**
         * Method description
         *
         *
         * @param nomoper
         */
        public void operator(int nomoper) {
            if (!(warehouse.this.wereModelsLoaded)) {
                warehouse.this.LoadModels(warehouse.NOMOPERATORMODELS, 0);
            }

            warehouse.WHOPER = new SCRwarehousoperator();
            warehouse.WHOPER.nWarehousoperator(warehouse.this.getModelName(0, true));
            warehouse.WHOPER.AddFreeAnimation("BasePult002", "BasePult004", 1);
            warehouse.WHOPER.AddAnimationRandom("BasePult003", 1);
            warehouse.WHOPER.AddPultPodhodAnimation("BasePult005Podhod", "BasePult006Othod");
            warehouse.WHOPER.AddRuleAnimations("CraneOperator");
        }

        /**
         * Method description
         *
         */
        public void crane() {
            warehouse.whCrane = SCRwarehousecrane.CreateCrane("model_WarehouseCrane", "Space_Crane_Main");
            warehouse.whCrane.FindCraneParts("Space_Crane");
        }
    }
}


//~ Formatted in DD Std on 13/08/28
