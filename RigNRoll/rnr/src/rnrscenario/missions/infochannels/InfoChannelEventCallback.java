/*
 * @(#)InfoChannelEventCallback.java   13/08/28
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


package rnr.src.rnrscenario.missions.infochannels;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.scriptActions.ScriptAction;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class InfoChannelEventCallback {
    private List<ScriptAction> onAcceptActions;
    private List<ScriptAction> onAppearActions;
    private List<ScriptAction> onDeclineActions;
    private List<ScriptAction> onEndActions;
    private boolean channelWasClosed;
    private ChannelClose closeInformation;
    private String acceptPalce;
    private String appearPalce;
    private String declinePalce;

    /**
     * Constructs ...
     *
     */
    public InfoChannelEventCallback() {
        this.onAcceptActions = null;
        this.onAppearActions = null;
        this.onDeclineActions = null;
        this.onEndActions = null;
        this.channelWasClosed = false;
        this.closeInformation = ChannelClose.DIALOG;
        this.acceptPalce = null;
        this.appearPalce = null;
        this.declinePalce = null;
    }

    /**
     * Enum description
     *
     */
    public static enum ChannelClose {
        DIALOG, DELAYED_ANSWERS;

        private final boolean closeOnAppear;
        private final boolean closeOnYes;
        private final boolean closeOnNo;
        private final boolean closeOnEnd;

        /**
         * Method description
         *
         *
         * @return
         */
        public static final ChannelClose[] values() {
            return ((ChannelClose[]) $VALUES.clone());
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isCloseOnAppear() {
            return this.closeOnAppear;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isCloseOnEnd() {
            return this.closeOnEnd;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isCloseOnNo() {
            return this.closeOnNo;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isCloseOnYes() {
            return this.closeOnYes;
        }
    }

    boolean channelClosed() {
        return this.channelWasClosed;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean hasAcceptAction() {
        return (!(this.onAcceptActions.isEmpty()));
    }

    /**
     * Method description
     *
     *
     * @param onAcceptActions
     * @param onAppearActions
     * @param onDeclineActions
     * @param onEndActions
     * @param closeInformation
     */
    public void useCallbacks(List<ScriptAction> onAcceptActions, List<ScriptAction> onAppearActions,
                             List<ScriptAction> onDeclineActions, List<ScriptAction> onEndActions,
                             ChannelClose closeInformation) {
        this.onAcceptActions = new ArrayList(onAcceptActions);
        this.onAppearActions = new ArrayList(onAppearActions);
        this.onDeclineActions = new ArrayList(onDeclineActions);
        this.onEndActions = new ArrayList(onEndActions);
        this.closeInformation = closeInformation;
    }

    private static void execute(List<ScriptAction> actionList) {
        assert(null != actionList) : "actionList must be non-null reference";

        for (ScriptAction action : actionList) {
            action.act();
        }

        actionList.clear();
    }

    void callBackAppear(String where) {
        this.appearPalce = where;
        execute(this.onAppearActions);
        this.channelWasClosed = this.closeInformation.isCloseOnAppear();
    }

    void callBackAccept(String where) {
        this.acceptPalce = where;
        execute(this.onAcceptActions);
        this.channelWasClosed = this.closeInformation.isCloseOnYes();
    }

    void callBackDecline(String where) {
        this.declinePalce = where;
        execute(this.onDeclineActions);
        this.channelWasClosed = this.closeInformation.isCloseOnNo();
    }

    void callBackEnd(String where) {
        execute(this.onEndActions);
        this.channelWasClosed = this.closeInformation.isCloseOnEnd();
    }

    String whereAccepted() {
        return this.acceptPalce;
    }

    String whereDeclined() {
        return this.declinePalce;
    }

    String whereAppeared() {
        return this.appearPalce;
    }
}


//~ Formatted in DD Std on 13/08/28
