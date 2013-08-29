/*
 * @(#)ExternalChannelSayAppear.java   13/08/28
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


package rnr.src.scriptActions;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrloggers.ScenarioLogger;
import rnr.src.rnrscr.IMissionInformation;
import rnr.src.rnrscr.MissionDialogs;

//~--- JDK imports ------------------------------------------------------------

import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class ExternalChannelSayAppear extends ScriptAction {

    /** Field description */
    public String point = "";

    /** Field description */
    public String name;

    /**
     * Constructs ...
     *
     */
    public ExternalChannelSayAppear() {
        super(9);
    }

    /**
     * Method description
     *
     */
    @Override
    public void act() {
        ScenarioLogger.getInstance().machineLog(Level.INFO,
                "action performed: " + super.getClass().getName() + "; channel name: " + this.name);

        IMissionInformation channelInformation = MissionDialogs.getMissionInfo(this.name);

        if ((null != channelInformation) && (null != this.point)) {
            channelInformation.setPointName(this.point);
        }

        MissionDialogs.sayAppear(this.name);
    }
}


//~ Formatted in DD Std on 13/08/28