/*
 * @(#)ScenarioPassing.java   13/08/28
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


package rnr.src.rnrscenario;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class ScenarioPassing extends sctask {

    /** Field description */
    public static final long serialVersionUID = 0L;
    private static ScenarioPassing instance = null;

    /** Field description */
    public static final String[] MESSAGES = new String[0];

    /** Field description */
    public static final int START_KOH_HELP = 0;

    /** Field description */
    public static final int KOH_HELP_PHASE1 = 1;

    /** Field description */
    public static final int KOH_HELP_PHASE2 = 2;

    /**
     * Constructs ...
     *
     */
    public ScenarioPassing() {
        super(3, false);
        start();
    }

    /**
     * Method description
     *
     *
     * @throws ScenarioPassing.InitializedException
     */
    public static void init() throws ScenarioPassing.InitializedException {
        if (null != instance) {
            throw new ScenarioPassing.InitializedException();
        }

        instance = new ScenarioPassing();
    }

    /**
     * Method description
     *
     *
     * @throws ScenarioPassing.NotInitializedException
     */
    public static void deinit() throws ScenarioPassing.NotInitializedException {
        if (null == instance) {
            throw new ScenarioPassing.NotInitializedException();
        }

        instance.finishImmediately();
        instance = null;
    }

    static class InitializedException extends Exception {

        /** Field description */
        public static final long serialVersionUID = 0L;

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String toString() {
            return ScenarioPassing.class.getName() + " initialized already";
        }
    }


    static class NotInitializedException extends Exception {

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String toString() {
            return ScenarioPassing.class.getName() + " not initialized";
        }
    }
}


//~ Formatted in DD Std on 13/08/28
