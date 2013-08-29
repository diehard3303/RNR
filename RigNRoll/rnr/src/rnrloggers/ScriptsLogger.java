/*
 * @(#)ScriptsLogger.java   13/08/26
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


package rnr.src.rnrloggers;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class ScriptsLogger {
    private static final String LOGGER_NAME = "rnr.ScriptsLogger";
    private static final String FOLDER = PathHolder.getWritablePath() + "warnings\\";

    /** Field description */
    public static final int JOURNAL_LOG_MARK = 0;

    /** Field description */
    public static final int ORGANIZER_LOG_MARK = 1;

    /** Field description */
    public static final int MISSION_POINT_LOG_MARK = 2;

    /** Field description */
    public static final int SAVING_SYSTEM_LOG_MARK = 3;

    /** Field description */
    public static final int GENERAL_LOG_MARK = 4;
    private static final SimpleFormatter FORMATTER = new SimpleFormatter();
    private static final ScriptsLogger ourInstance = new ScriptsLogger();
    private Logger log = null;

    private ScriptsLogger() {
        this.log = Logger.getLogger("rnr.ScriptsLogger");
        this.log.setUseParentHandlers(false);

        try {
            TextFileHandler mainHandler = new TextFileHandler(FOLDER + "scriptsJavaLog.log", false, FORMATTER);

            mainHandler.setFilter(new Filter() {
                @Override
                public boolean isLoggable(LogRecord record) {
                    return true;
                }
            });

            TextFileHandler journalHandler = new TextFileHandler(FOLDER + "journalJava.log", false, FORMATTER);

            journalHandler.setFilter(new IntegerFilter(0));

            TextFileHandler organizerHandler = new TextFileHandler(FOLDER + "organizerJava.log", false, FORMATTER);

            organizerHandler.setFilter(new IntegerFilter(1));

            TextFileHandler mpHandler = new TextFileHandler(FOLDER + "mpJava.log", false, FORMATTER);

            mpHandler.setFilter(new IntegerFilter(2));

            TextFileHandler savingSystem = new TextFileHandler(FOLDER + "saving.log", false, FORMATTER);

            savingSystem.setFilter(new IntegerFilter(3));

            TextFileHandler generalHandler = new TextFileHandler(FOLDER + "general.log", false, FORMATTER);

            generalHandler.setFilter(new IntegerFilter(4));
            this.log.addHandler(mainHandler);
            this.log.addHandler(journalHandler);
            this.log.addHandler(organizerHandler);
            this.log.addHandler(mpHandler);
            this.log.addHandler(savingSystem);
            this.log.addHandler(generalHandler);
        } catch (IOException exception) {
            System.err.println(exception.getLocalizedMessage());
            exception.printStackTrace(System.err);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static ScriptsLogger getInstance() {
        return ourInstance;
    }

    /**
     * Method description
     *
     *
     * @param level
     * @param systemID
     * @param message
     */
    public void log(Level level, int systemID, String message) {
        if ((null == level) || (null == message)) {
            return;
        }

        this.log.log(level, message, Integer.valueOf(systemID));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Logger getLog() {
        return this.log;
    }
}


//~ Formatted in DD Std on 13/08/26
