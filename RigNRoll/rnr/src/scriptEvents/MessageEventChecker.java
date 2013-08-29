/*
 * @(#)MessageEventChecker.java   13/08/27
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


package rnr.src.scriptEvents;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public class MessageEventChecker implements EventChecker {
    static final long serialVersionUID = 0L;
    private String text = null;
    private ScriptEvent lastPossetiveChecked = null;

    /**
     * Constructs ...
     *
     */
    public MessageEventChecker() {}

    /**
     * Constructs ...
     *
     *
     * @param messageToWait
     */
    public MessageEventChecker(String messageToWait) {
        this.text = messageToWait;
    }

    /**
     * Method description
     *
     */
    @Override
    public void deactivateChecker() {}

    /**
     * Method description
     *
     *
     * @param eventTuple
     *
     * @return
     */
    @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        if (null == this.text) {
            System.err.println("MessageEventChecker wasn't correctly inited: text field is null");

            return false;
        }

        for (ScriptEvent event : eventTuple) {
            if ((event instanceof MessageEvent)
                    && (0 == ((MessageEvent) event).getMessage().compareToIgnoreCase(this.text))) {
                this.lastPossetiveChecked = event;

                return true;
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
    public String isValid() {
        if (null == this.text) {
            return "\"text\" field is null";
        }

        if (!(EventsControllerHelper.getInstance().isRegisteredMessageEvent(this.text))) {
            return this.text + " is not a valid message";
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastPossetiveChecked;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public List<ScriptEvent> getExpectantEvent() {
        List<ScriptEvent> out = new ArrayList<ScriptEvent>(1);

        out.add(new MessageEvent(this.text));

        return out;
    }
}


//~ Formatted in DD Std on 13/08/27
