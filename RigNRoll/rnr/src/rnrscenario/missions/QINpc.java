/*
 * @(#)QINpc.java   13/08/28
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


package rnr.src.rnrscenario.missions;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.scriptEvents.CreateNpcFromQuestItem;
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class QINpc extends QuestItem {
    static final long serialVersionUID = 0L;

    /** Field description */
    public String model;

    /** Field description */
    public String place;

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getPlacement() {
        return this.place;
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     */
    @Override
    public void doPlace(String mission_name) {
        EventsControllerHelper.eventHappened(new CreateNpcFromQuestItem(this, mission_name));
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     * @param make_place
     */
    public void doDealyedPlace(String mission_name, boolean make_place) {
        if (make_place) {
            MissionEventsMaker.createQuestItemPassanger(mission_name, this.model, this.place);
        } else {
            MissionEventsMaker.createQuestItemPassangerNoAnimation(mission_name, this.model, this.place);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isSemitrailer() {
        return false;
    }
}


//~ Formatted in DD Std on 13/08/28
