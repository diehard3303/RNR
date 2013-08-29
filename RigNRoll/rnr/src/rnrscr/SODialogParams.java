/*
 * @(#)SODialogParams.java   13/08/28
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

import rnr.src.rnrcore.SCRuniperson;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class SODialogParams implements Comparable<Object> {
    private final SCRuniperson npcModel;
    private final String description;
    private final int id;
    private boolean played;
    private final boolean isFinishmIssionDialog;
    private final String identitie;
    private final String pointName;
    private final String missionName;

    /**
     * Constructs ...
     *
     *
     * @param identitie
     * @param npcModel
     * @param description
     * @param id
     * @param played
     * @param isFinishmIssionDialog
     * @param pointName
     * @param missionName
     */
    public SODialogParams(String identitie, SCRuniperson npcModel, String description, int id, boolean played,
                          boolean isFinishmIssionDialog, String pointName, String missionName) {
        this.npcModel = npcModel;
        this.description = description;
        this.id = id;
        this.played = played;
        this.isFinishmIssionDialog = isFinishmIssionDialog;
        this.identitie = identitie;
        this.pointName = pointName;
        this.missionName = missionName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public SCRuniperson getNpcModel() {
        return this.npcModel;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Method description
     *
     *
     * @param other
     *
     * @return
     */
    @Override
    public int compareTo(Object other) {
        int otherId = ((SODialogParams) other).id;

        if (this.id < otherId) {
            return -1;
        }

        if (this.id > otherId) {
            return 1;
        }

        return 0;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean wasPlayed() {
        return this.played;
    }

    /**
     * Method description
     *
     */
    public void play() {
        this.played = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isfinishDialog() {
        return this.isFinishmIssionDialog;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getIdentitie() {
        return this.identitie;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getMissionName() {
        return this.missionName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getPointName() {
        return this.pointName;
    }
}


//~ Formatted in DD Std on 13/08/28
