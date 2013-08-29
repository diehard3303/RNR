/*
 * @(#)ScenarioAnalysisLogger.java   13/08/26
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

import rnr.src.scenarioAnalysis.DynamicScenarioAnalyzer;
import rnr.src.scenarioAnalysis.StaticScenarioAnalyzer;

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
public final class ScenarioAnalysisLogger {
    private static final String LOGGER_NAME = "rnr.ScenarioAnalysisLogger";
    private static final String LOGS_DIRECTORY = PathHolder.getWritablePath() + "warnings\\";
    private static final ScenarioAnalysisLogger ourInstance = new ScenarioAnalysisLogger();
    private Logger log = null;

    private ScenarioAnalysisLogger() {
        this.log = Logger.getLogger("rnr.ScenarioAnalysisLogger");
        this.log.setUseParentHandlers(false);

        try {
            this.log.addHandler(new TextFileHandler(LOGS_DIRECTORY + "scenarioAnalisys.log", false,
                    new SimpleFormatter()));

            TextFileHandler staticAnalysisLogHandler = new TextFileHandler(LOGS_DIRECTORY
                                                           + "staticScenarioAnalisys.log", false,
                                                               new OnlyMessageOutFormatter());
            TextFileHandler dynamicAnalysisLogHandler = new TextFileHandler(LOGS_DIRECTORY
                                                            + "dynamicScenarioAnalisys.log", false,
                                                                new OnlyMessageOutFormatter());

            staticAnalysisLogHandler.setFilter(new Filter() {
                @Override
                public boolean isLoggable(LogRecord record) {
                    return ((0 == StaticScenarioAnalyzer.class.getName().compareToIgnoreCase(
                        record.getSourceClassName())) && (record.getLevel().intValue() >= Level.INFO.intValue()));
                }
            });
            dynamicAnalysisLogHandler.setFilter(new Filter() {
                @Override
                public boolean isLoggable(LogRecord record) {
                    return (0 == DynamicScenarioAnalyzer.class.getName().compareToIgnoreCase(
                        record.getSourceClassName()));
                }
            });
            this.log.addHandler(staticAnalysisLogHandler);
            this.log.addHandler(dynamicAnalysisLogHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static ScenarioAnalysisLogger getInstance() {
        return ourInstance;
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
