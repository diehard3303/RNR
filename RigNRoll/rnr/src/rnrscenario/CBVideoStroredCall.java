/*
 * @(#)CBVideoStroredCall.java   13/08/28
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

import rnr.src.players.aiplayer;
import rnr.src.rnrscr.CBVideocallelemnt;
import rnr.src.rnrscr.cbapparatus;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class CBVideoStroredCall {

    /** Field description */
    public String name = "unnamed";

    /** Field description */
    public String dialogname = "no dialog";

    /** Field description */
    public float timecall = 10.0F;

    /** Field description */
    public boolean talkanyway = true;
    private aiplayer who;

    CBVideoStroredCall(String name, String dialogname, float timecall, boolean talkanyway) {
        this.name = name;
        this.dialogname = dialogname;
        this.timecall = timecall;
        this.talkanyway = talkanyway;
    }

    /**
     * Method description
     *
     *
     * @param identitie
     */
    public void setIdentitie(String identitie) {
        this.who = new aiplayer(identitie);
    }

    /**
     * Method description
     *
     *
     * @param identitie
     * @param mission_name
     */
    public void makecall(String identitie, String mission_name) {
        this.who = new aiplayer(identitie);

        CBVideocallelemnt call = cbapparatus.getInstance().makecall(this.who, this.dialogname);

        call.setStoreName(this.name);
        call.timeforanswer = this.timecall;
        call.talkanyway = this.talkanyway;
    }

    /**
     * Method description
     *
     */
    public void makecall() {
        CBVideocallelemnt call = cbapparatus.getInstance().makecall(this.who, this.dialogname);

        call.setStoreName(this.name);
        call.timeforanswer = this.timecall;
        call.talkanyway = this.talkanyway;
    }

    CBVideocallelemnt makeImmediateCall() {
        CBVideocallelemnt call = cbapparatus.getInstance().makecall(this.who, this.dialogname);

        call.setStoreName(this.name);
        call.timeforanswer = this.timecall;
        call.talkanyway = this.talkanyway;
        cCBVideoCall_Caller.makeImmediateCall(call);

        return call;
    }
}


//~ Formatted in DD Std on 13/08/28
