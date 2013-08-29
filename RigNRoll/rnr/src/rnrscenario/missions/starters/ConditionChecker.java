/*
 * @(#)ConditionChecker.java   13/08/28
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


package rnr.src.rnrscenario.missions.starters;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.scriptEvents.EventsControllerHelper;
import rnr.src.xmlserialization.nxs.AnnotatedSerializable;
import rnr.src.xmlserialization.nxs.SaveTo;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public abstract class ConditionChecker implements AnnotatedSerializable {
    private static List<ConditionChecker> allCheckers;

    static {
        allCheckers = Collections.synchronizedList(new ArrayList());
    }

    private final String conditionId;
    @SaveTo(
        destinationNodeName = "mission_name",
        constructorArgumentNumber = 0,
        saveVersion = 0
    )
    String missionName;

    /**
     * Constructs ...
     *
     *
     * @param missionName
     * @param conditionId
     */
    public ConditionChecker(String missionName, String conditionId) {
        assert(null != missionName);
        assert(null != conditionId);
        this.missionName = missionName;
        this.conditionId = conditionId;
        allCheckers.add(this);
        EventsControllerHelper.getInstance().addMessageListener(this, "missionFinished", missionName + " failed");
    }

    /**
     * Method description
     *
     */
    public static void clearAllCheckers() {
        for (ConditionChecker checker : allCheckers) {
            checker.unsubscribeSelfFromMessages();
        }

        allCheckers.clear();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static List<ConditionChecker> getAllCheckers() {
        return allCheckers;
    }

    /**
     * Method description
     *
     */
    public void missionFinished() {
        unsubscribeSelfFromMessages();
        allCheckers.remove(this);
    }

    private void unsubscribeSelfFromMessages() {
        EventsControllerHelper.getInstance().removeMessageListener(this, "missionFinished",
                this.missionName + " failed");
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final String getMissionName() {
        return this.missionName;
    }

    final boolean checkCondition() {
        if (check()) {
            missionFinished();

            return true;
        }

        return false;
    }

    /**
     * Method description
     *
     */
    @Override
    public final void finilizeDeserialization() {
        CheckersRestorer.sendCheckerToNativeOnAfterLoad(this);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public final String getId() {
        return this.conditionId;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public abstract boolean check();
}


//~ Formatted in DD Std on 13/08/28
