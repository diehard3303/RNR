/*
 * @(#)Dumper.java   13/08/28
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

import rnr.src.config.ApplicationFolders;
import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrscenario.sctask;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

final class Dumper extends sctask {
    static final long serialVersionUID = 1L;
    private static final int RUN_FREQUENCY = 60;
    private static final int DUMP_SIZE = 2;
    private static final String FOLDER = ApplicationFolders.RCMDF() + ".\\warnings\\";
    private static final String FILE = "missionsDump.log";
    private boolean on = false;
    private final List<Dumpable> toDump = new ArrayList<Dumpable>(2);

    Dumper() {
        super(60, false);
        super.start();

        File folderToLog = new File(FOLDER);

        if (folderToLog.exists()) {
            return;
        }

        folderToLog.mkdir();
    }

    void addTask(Dumpable task) {
        if (null == task) {
            return;
        }

        this.toDump.add(task);
    }

    /**
     * Method description
     *
     */
    public void on() {
        this.on = true;
    }

    /**
     * Method description
     *
     */
    public void off() {
        this.on = false;
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        if (!(this.on)) {
            return;
        }

        FileOutputStream out;

        try {
            out = new FileOutputStream(FOLDER + "missionsDump.log");

            for (Dumpable dumpable : this.toDump) {
                dumpable.makeDump(out);
            }
        } catch (FileNotFoundException e) {
            MissionsLogger.getInstance().doLog("dumper failed to do dump: " + e.getMessage(), Level.WARNING);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
