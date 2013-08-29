/*
 * @(#)CBVideocallelemnt.java   13/08/28
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

import rnr.src.players.IcontaktCB;
import rnr.src.players.aiplayer;
import rnr.src.scriptEvents.CbVideoEvent;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.ScriptEvent;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class CBVideocallelemnt implements CBVideocall {

    /** Field description */
    public float timeforanswer = 10.0F;

    /** Field description */
    public boolean talkanyway = true;
    private String dilaog_name = null;

    /** Field description */
    public long nativePointer;

    /** Field description */
    public aiplayer whocalls;
    String dialogname;
    private String store_name;
    boolean finished;

    CBVideocallelemnt(aiplayer pl, String dial) {
        this.whocalls = pl;
        this.dialogname = dial;
        this.finished = false;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void pause(boolean value) {
        dialogpause(value);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean talkAnyWay() {
        return this.talkanyway;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public float gTimeForAnswer() {
        return this.timeforanswer;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String incommingCallMessage() {
        return incommingMessage();
    }

    /**
     * Method description
     *
     *
     * @param store_name
     */
    public void setStoreName(String store_name) {
        this.store_name = store_name;
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void setDialogName(String name) {
        this.dilaog_name = name;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getDialogName() {
        return this.dilaog_name;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getResourceName() {
        return this.store_name;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public IcontaktCB who() {
        return this.whocalls.createCBContacter();
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param methodname
     */
    @Override
    public void registerMesageCallBack(Object obj, String methodname) {
        registerMesageCB(obj, methodname);
    }

    /**
     * Method description
     *
     *
     * @param obj
     * @param methodname
     */
    @Override
    public void registerRequestCallBack(Object obj, String methodname) {
        registerRequestCB(obj, methodname);
    }

    /**
     * Method description
     *
     */
    @Override
    public void start() {
        dialogstart();
        EventsController.getInstance().eventHappen(new ScriptEvent[] {
            new CbVideoEvent(this.store_name, CbVideoEvent.EventType.START, 0) });
    }

    /**
     * Method description
     *
     *
     * @param res
     */
    @Override
    public void answer(int res) {
        requestanswer(res);
        EventsController.getInstance().eventHappen(new ScriptEvent[] {
            new CbVideoEvent(this.store_name, CbVideoEvent.EventType.ANSWER, res) });
    }

    /**
     * Method description
     *
     */
    public void endfromnative() {
        EventsController.getInstance().eventHappen(new ScriptEvent[] {
            new CbVideoEvent(this.store_name, CbVideoEvent.EventType.FINISH, 0) });
        this.finished = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean gFinished() {
        return this.finished;
    }

    void examplecallback(cbdialogmessage mess) {}

    void examplerequestcallback(int _state) {}

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isCallIncoming() {
        return isCallIncomingNative();
    }

    /**
     * Method description
     *
     *
     * @param paramObject
     * @param paramString
     */
    public native void registerMesageCB(Object paramObject, String paramString);

    /**
     * Method description
     *
     *
     * @param paramObject
     * @param paramString
     */
    public native void registerRequestCB(Object paramObject, String paramString);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public native void requestanswer(int paramInt);

    /**
     * Method description
     *
     */
    public native void dialogstart();

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public native void dialogpause(boolean paramBoolean);

    /**
     * Method description
     *
     */
    public native void initiate();

    /**
     * Method description
     *
     *
     * @return
     */
    public native String incommingMessage();

    /**
     * Method description
     *
     *
     * @return
     */
    public native boolean isCallIncomingNative();
}


//~ Formatted in DD Std on 13/08/28
