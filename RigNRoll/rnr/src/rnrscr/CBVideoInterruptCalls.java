/*
 * @(#)CBVideoInterruptCalls.java   13/08/28
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

import rnr.src.rnrcore.CoreTime;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class CBVideoInterruptCalls {
    private static CBVideoInterruptCalls instance;
    private final ArrayList<IInterruptCall> calls = new ArrayList();

    private static CBVideoInterruptCalls getInstance() {
        if (instance == null) {
            instance = new CBVideoInterruptCalls();
        }

        return instance;
    }

    /**
     * Method description
     *
     *
     * @param call
     */
    public static void add(IInterruptCall call) {
        getInstance().calls.add(call);
    }

    /**
     * Method description
     *
     *
     * @param call
     */
    public static void remove(IInterruptCall call) {
        getInstance().calls.remove(call);
    }

    /**
     * Method description
     *
     *
     * @param from
     * @param to
     *
     * @return
     */
    public static CoreTime interruptCall(CoreTime from, CoreTime to) {
        return getInstance()._interruptCall(from, to);
    }

    private CoreTime _interruptCall(CoreTime from, CoreTime to) {
        Iterator<IInterruptCall> iter = this.calls.iterator();

        while (iter.hasNext()) {
            IInterruptCall call = iter.next();
            CoreTime calltime = call.getInterruptTime();

            if ((calltime.moreThan(from) > 0) && (calltime.moreThan(to) < 0)) {
                return calltime;
            }
        }

        return to;
    }
}


//~ Formatted in DD Std on 13/08/28
