/*
 * @(#)InfoChannelEventsListener.java   13/08/28
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

import rnr.src.scriptEvents.EventListener;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.ScriptEvent;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class InfoChannelEventsListener extends InfoChannelEventCallback
        implements EventListener, WhereWasChannelAccepted {
    private String sourceUid = null;

    /**
     * Constructs ...
     *
     *
     * @param desiredSourceUid
     */
    public InfoChannelEventsListener(String desiredSourceUid) {
        assert(null != desiredSourceUid) : "desiredSourceUid must be non-null reference";
        this.sourceUid = desiredSourceUid;
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     */
    @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event : eventTuple) {
            if (event instanceof InfoChannelEvent) {
                InfoChannelEvent channelEvent = (InfoChannelEvent) event;

                if (0 == this.sourceUid.compareTo(channelEvent.getSourceUid())) {
                    InfoChannelEvent channelProcessor = (InfoChannelEvent) event;

                    channelProcessor.executeCallBack(this);
                }
            }
        }

        if (!(channelClosed())) {
            return;
        }

        EventsController.getInstance().removeListener(this);
    }

    /**
     * Method description
     *
     *
     * @param eventTuple
     *
     * @return
     */
    public boolean hasAcceptAction(List<ScriptEvent> eventTuple) {
        for (ScriptEvent event : eventTuple) {
            if (event instanceof InfoChannelEvent) {
                InfoChannelEvent channelEvent = (InfoChannelEvent) event;

                if ((0 == this.sourceUid.compareTo(channelEvent.getSourceUid())) && (hasAcceptAction())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String where() {
        return super.whereAccepted();
    }
}


//~ Formatted in DD Std on 13/08/28
