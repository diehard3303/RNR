/*
 * @(#)IMissionInformation.java   13/08/28
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

/**
 * Interface description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public abstract interface IMissionInformation {

    /**
     * Method description
     *
     *
     * @param paramCoreTime
     *
     * @return
     */
    public abstract boolean checkTimePeriods(CoreTime paramCoreTime);

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String getMissionName();

    /**
     * Method description
     *
     *
     * @param paramIChannelPointChanges
     */
    public abstract void addChannelPlaceChangedListener(IChannelPointChanges paramIChannelPointChanges);

    /**
     * Method description
     *
     */
    public abstract void freeFromPoint();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract boolean hasPoint();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String getPointName();

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public abstract void setPointName(String paramString);

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract ArrayList<String> getDependantMissions();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String getDialogName();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String getChannelId();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract boolean isDialog();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract boolean wasVoterFreed();

    /**
     * Method description
     *
     */
    public abstract void freeVoter();

    /**
     * Method description
     *
     */
    public abstract void playMissionInfo();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract boolean wasPlayed();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract boolean isFinishInformaton();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract String getIdentitie();

    /**
     * Method description
     *
     */
    public abstract void makeInformationNotActive();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract boolean isInformationActive();

    /**
     * Method description
     *
     */
    public abstract void postInfo();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract boolean isInfoPosted();

    /**
     * Method description
     *
     */
    public abstract void receiveAnswer();

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract boolean hasQuestion();

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public abstract void setChannelImmediate(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract boolean isChannelImmediate();
}


//~ Formatted in DD Std on 13/08/28
