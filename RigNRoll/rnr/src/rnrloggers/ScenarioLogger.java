/*
 * @(#)ScenarioLogger.java   13/08/26
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.eng;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class ScenarioLogger {
    private static final String LOGGER_NAME = "rnr.ScenarioLogger";
    private static final String LOGS_DIRECTORY = PathHolder.getWritablePath() + "warnings\\";
    private static final int XML_PARSER_MARK = 0;
    private static final int SCENARIO_MACHINE_MARK = 1;
    private static final ScenarioLogger ourInstance = new ScenarioLogger();
    private Logger log = null;

    private ScenarioLogger() {
        createLoggingDirectory();
        this.log = Logger.getLogger("rnr.ScenarioLogger");
        this.log.setUseParentHandlers(false);

        try {
            TextFileHandler machineHandler = new TextFileHandler(LOGS_DIRECTORY + "scenarioGraphMachine.log", false,
                                                 new SimpleFormatter());

            machineHandler.setFilter(new IntegerFilter(1));

            TextFileHandler parserHandler = new TextFileHandler(LOGS_DIRECTORY + "scenarioParsing.log", false,
                                                new SimpleFormatter());

            parserHandler.setFilter(new IntegerFilter(0));
            this.log.addHandler(machineHandler);
            this.log.addHandler(parserHandler);
            this.log.addHandler(new FileHandler(LOGS_DIRECTORY + "scenarioLog.xml"));
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
    public static ScenarioLogger getInstance() {
        return ourInstance;
    }

    private void createLoggingDirectory() {
        File logsDirectory = new File(LOGS_DIRECTORY);

        if ((logsDirectory.exists()) || (logsDirectory.mkdir())) {
            return;
        }

        System.err.println("failed to make directory for scenario system logs");
    }

    private static void externalLog(String message) {
        try {
            eng.writeLog(message);
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method description
     *
     */
    public void switchOffLoging() {
        this.log.setLevel(Level.OFF);
    }

    private void error(String message, Object param) {
        if (null == message) {
            return;
        }

        this.log.log(Level.SEVERE, message, param);
        externalLog(message);
        System.err.println(message);
    }

    private void warning(String message, Object param) {
        if (null == message) {
            return;
        }

        this.log.log(Level.WARNING, message, param);
        externalLog(message);
    }

    /**
     * Method description
     *
     *
     * @param message
     */
    public void machineWarning(String message) {
        if (null == message) {
            return;
        }

        warning(message, Integer.valueOf(1));
    }

    /**
     * Method description
     *
     *
     * @param message
     */
    public void parserWarning(String message) {
        if (null == message) {
            return;
        }

        warning(message, Integer.valueOf(0));
    }

    /**
     * Method description
     *
     *
     * @param message
     */
    public void machineError(String message) {
        if (null == message) {
            return;
        }

        error(message, Integer.valueOf(1));
    }

    /**
     * Method description
     *
     *
     * @param message
     */
    public void parserError(String message) {
        if (null == message) {
            return;
        }

        error(message, Integer.valueOf(0));
    }

    /**
     * Method description
     *
     *
     * @param level
     * @param message
     */
    public void parserLog(Level level, String message) {
        if ((null == level) || (null == message)) {
            return;
        }

        this.log.log(level, message, Integer.valueOf(0));
    }

    /**
     * Method description
     *
     *
     * @param level
     * @param message
     */
    public void machineLog(Level level, String message) {
        if ((null == level) || (null == message)) {
            return;
        }

        this.log.log(level, message, Integer.valueOf(1));
    }
}


//~ Formatted in DD Std on 13/08/26
