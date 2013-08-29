/*
 * @(#)TestCreateAllMenues.java   13/08/26
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.menues;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.lang.reflect.Method;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class TestCreateAllMenues extends TypicalAnm {
    private static final double COOLDOWN = 2.0D;
    CreateAllMenues men = null;
    Method[] meths = null;
    int position = 0;
    private double lastT = 0.0D;

    TestCreateAllMenues() {
        eng.noNative = true;
        eng.CreateInfinitScriptAnimation(this);

        try {
            Class cl = Class.forName("rnrscenario.CreateAllMenues");

            this.meths = cl.getDeclaredMethods();
            this.men = new CreateAllMenues();
        } catch (Exception e) {
            e = null;
        }
    }

    /**
     * Method description
     *
     *
     * @param dt
     *
     * @return
     */
    @Override
    public boolean animaterun(double dt) {
        if ((this.meths == null) || (this.position >= this.meths.length)) {
            log("TestCreateAllMenues finished");
            event.Setevent(8001);

            return true;
        }

        if (dt - this.lastT > 2.0D) {
            this.lastT = dt;

            if ((this.position > 0) && (this.men.lastMenuCreated != 0L)) {
                menues.CallMenuCallBack_ExitMenu(this.men.lastMenuCreated);
                this.men.lastMenuCreated = 0L;
            }

            Method m = this.meths[(this.position++)];

            log(m);

            try {
                m.invoke(this.men, new Object[0]);
            } catch (Exception e) {
                eng.fatal(e.getMessage());
            }
        }

        return false;
    }

    private static void log(Method meth) {
        System.err.print("\ntry " + meth.getName());
        System.err.flush();
    }

    private static void log(String text) {
        System.err.print("\n" + text + "\n");
        System.err.flush();
    }

    static void runAll() {
        new TestCreateAllMenues();
    }

    /**
     * Method description
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        runAll();
    }
}


//~ Formatted in DD Std on 13/08/26
