/*
 * @(#)MainTestClass.java   13/08/26
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


package rnr.src.rnrcore;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class MainTestClass {
    private static final double[] DISTANCES = { 10.0D, 100.0D, 1000.0D, 3000.0D };
    private static final double[] DISTANCES_q = new double[0];

    static enum Eum {
        TOO, FOO;

        /**
         * Method description
         *
         *
         * @return
         */
        public static final Eum[] values() {
            return ((Eum[]) $VALUES.clone());
        }
    }

    /**
     * Method description
     *
     *
     * @param arguments
     *
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static void main(String[] arguments) throws NoSuchFieldException, IllegalAccessException {
        A goo = new A();

        A.class.getField("a").set(goo, "1");

        int insertion = Arrays.binarySearch(DISTANCES, 40000.0D);
        int ins = Arrays.binarySearch(DISTANCES_q, 1.0D);

        ins = -(1 + ins);
        insertion = -(1 + insertion);

        int aa = 11;

        aa %= 0;
        System.out.print(aa);

        String qqq = "qqq";

        if (qqq.getClass() == String.class) {
            System.out.print("qqq");
        }

        List a = new LinkedList();

        a.add(Eum.FOO);

        if (a.contains(Eum.FOO)) {
            System.out.print("fff");
        }

        if (!(a.contains(Eum.TOO))) {
            return;
        }

        System.out.print("too");
    }

    static class A {

        /** Field description */
        public int a;

        A() {
            this.a = 0;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
