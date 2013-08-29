/*
 * @(#)cCBVideoCall_Caller.java   13/08/28
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
import rnr.src.menuscript.cbvideo.CBVideoAnimation;
import rnr.src.menuscript.cbvideo.MenuNotify;
import rnr.src.rnrscr.CBVideocall;
import rnr.src.rnrscr.CBVideocallelemnt;
import rnr.src.rnrscr.cbapparatus;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class cCBVideoCall_Caller extends sctask {
    private static cCBVideoCall_Caller instance = null;

    private cCBVideoCall_Caller() {
        super(3, false);
    }

    /**
     * Method description
     *
     */
    public static void initialize() {
        deinitialize();
        instance = new cCBVideoCall_Caller();
        instance.start();
    }

    /**
     * Method description
     *
     */
    public static void deinitialize() {
        if (null == instance) {
            return;
        }

        instance.finishImmediately();
        instance = null;
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        ArrayList<CBVideocallelemnt> v = cbapparatus.getInstance().gCallers();

        for (CBVideocallelemnt aV : v) {
            if (menues.cancreate_somenu()) {
                makeCall(aV);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param call
     *
     * @return
     */
    public static boolean makeImmediateCall(CBVideocall call) {
        if (menues.cancreate_somenu()) {
            instance.makeCall(call);

            return true;
        }

        return false;
    }

    private void makeCall(CBVideocall call) {
        if (call.gFinished()) {
            return;
        }

        if (call.isCallIncoming()) {
            MenuNotify menuNotify = MenuNotify.create(call.gTimeForAnswer());

            menuNotify.addListener(new CBVideoAnimation(call));
        } else {
            new CBVideoAnimation(call).onClose();
        }

        call.start();
    }
}


//~ Formatted in DD Std on 13/08/28
