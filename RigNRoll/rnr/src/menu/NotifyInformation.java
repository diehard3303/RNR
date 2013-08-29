/*
 * @(#)NotifyInformation.java   13/08/25
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


package rnr.src.menu;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class NotifyInformation {

    /** Field description */
    public static boolean notify_should_stop_world = true;

    /** Field description */
    public static boolean notify_should_show_cursor = true;

    /** Field description */
    public static boolean notify_should_be_showed = true;

    /** Field description */
    public static boolean fMainMenuRequested = false;

    /**
     * Method description
     *
     */
    public static void reset() {
        notify_should_stop_world = true;
        notify_should_show_cursor = true;
        notify_should_be_showed = true;
    }

    /**
     * Method description
     *
     */
    public static void onWHmessages() {
        notify_should_stop_world = false;
        notify_should_show_cursor = false;
        notify_should_be_showed = false;
    }

    /**
     * Method description
     *
     */
    public static void onWHmessagesRenew() {
        notify_should_stop_world = false;
        notify_should_show_cursor = true;
        notify_should_be_showed = true;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    public static void notifyAfterInit(long _menu) {
        menues.WindowSet_ShowCursor(_menu, notify_should_show_cursor);
        menues.SetStopWorld(_menu, notify_should_stop_world);

        if (notify_should_be_showed) {
            menues.setShowMenu(_menu, true);
        }

        reset();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    public static void changeInteriorAfterInit(long _menu) {
        menues.WindowSet_ShowCursor(_menu, false);
        menues.SetStopWorld(_menu, false);
        menues.setShowMenu(_menu, true);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     */
    public static void leaveWarehouseAfterInit(long _menu) {
        changeInteriorAfterInit(_menu);
    }

    /**
     * Method description
     *
     */
    public static void requestMainMenu() {
        fMainMenuRequested = true;
    }

    /**
     * Method description
     *
     */
    public static void hasDeliveryMessages() {
        rnr.src.menuscript.WHmenues.whmenu_immediateshow = true;
    }

    /**
     * Method description
     *
     */
    public static void startShowMessages() {
        onWHmessages();
        fMainMenuRequested = false;
    }

    /**
     * Method description
     *
     */
    public static void hasOneMessage() {
        if (!(fMainMenuRequested)) {
            onWHmessages();
        }

        fMainMenuRequested = false;
    }

    /**
     * Method description
     *
     */
    public static void panelFolded() {
        rnr.src.menuscript.WHmenues.whmenu_immediateshow = false;
        fMainMenuRequested = false;
    }

    /**
     * Method description
     *
     */
    public static void panelUnFolded() {
        rnr.src.menuscript.WHmenues.whmenu_immediateshow = true;
    }
}


//~ Formatted in DD Std on 13/08/25
