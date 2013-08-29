/*
 * @(#)ScenarioMachineLogger.java   13/08/27
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


package rnr.src.scenarioUtils;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.eng;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintStream;

import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public class ScenarioMachineLogger {
    private static ScenarioMachineLogger ourInstance = new ScenarioMachineLogger();
    private static final String LOGGER_NAME = "rnr.ScenarioLogger";
    private Logger log = null;

    private ScenarioMachineLogger() {
        this.log = Logger.getLogger("rnr.ScenarioLogger");
        this.log.setUseParentHandlers(false);

        try {
            TextFileHandler handler = new TextFileHandler(".\\warnings\\scenarioGraphMachineLog.log", false,
                                          new SimpleFormatter());

            handler.setFilter(new Filter() {
                @Override
                public boolean isLoggable(LogRecord record) {
                    return true;
                }
            });
            this.log.addHandler(handler);
            this.log.addHandler(new FileHandler(".\\warnings\\scenarioGraphMachineLog.xml"));
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
    public static ScenarioMachineLogger getInstance() {
        return ourInstance;
    }

    private void externalLog(String message) {
        try {
            eng.writeLog(message);
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
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

    /**
     * Method description
     *
     */
    public void switchOffLoging() {
        this.log.setLevel(Level.OFF);
    }

    /**
     * Method description
     *
     *
     * @param message
     */
    public void warning(String message) {
        this.log.warning(message);
        externalLog(message);
    }

    /**
     * Method description
     *
     *
     * @param message
     */
    public void error(String message) {
        this.log.severe(message);
        externalLog(message);
        System.err.println(message);
    }
}


//~ Formatted in DD Std on 13/08/27
