/*
 * @(#)Analysis.java   13/08/27
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


package rnr.src.scenarioAnalysis;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.NativeEventController;
import rnr.src.rnrcore.ScenarioSync;
import rnr.src.rnrloggers.ScenarioAnalysisLogger;
import rnr.src.rnrscenario.config.ConfigManager;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.scenarioXml.CbvEventListenerBuilder;
import rnr.src.scenarioXml.InternalScenarioRepresentation;
import rnr.src.scenarioXml.XmlScenarioMachineBuilder;
import rnr.src.scriptEvents.CbVideoEventsListener;
import rnr.src.scriptEvents.EventsController;
import rnr.src.scriptEvents.EventsControllerHelper;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public final class Analysis {
    private static final int BUFFER_SIZE = 1048576;
    private static final String CONFIG_FILE_PATH = ".\\Analysis.cfg";
    private static final String QUESTS_XML = "quests";
    private static final String CBV_XML = "cbv";
    private static final String MSG_XML = "msg";
    private static final String Q_XML = "q";
    private static final String SV_FOLDER_XML = "save_folder";
    private static final String SV_FILE_XML = "save_file";
    private static final byte[] BUFFER;
    private static int folderIncrement;

    static {
        BUFFER = new byte[1048576];
        folderIncrement = 0;
    }

    private static void moveResults() {
        File logDirectory = new File(".\\warnings\\");

        if (logDirectory.exists()) {
            File dir = new File("d:\\temp\\scenariologs\\res" + (folderIncrement++));

            if (!(dir.mkdirs())) {
                ;
            }

            try {
                copyFiles(logDirectory, dir);
            } catch (IOException c) {
                System.err.print(c.toString());
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param src
     * @param dest
     *
     * @throws IOException
     */
    public static void copyFiles(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!(dest.mkdirs())) {
                System.err.println("Failed to make destination directories");
            }

            String[] list = src.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith("log");
                }
            });

            for (String aList : list) {
                String dest1 = dest.getPath() + "\\" + aList;
                String src1 = src.getPath() + "\\" + aList;

                copyFiles(new File(src1), new File(dest1));
            }
        } else {
            FileInputStream in = null;
            FileOutputStream out = null;

            try {
                in = new FileInputStream(src);
                out = new FileOutputStream(dest);

                int bytesRead = in.read(BUFFER);

                while (0 < bytesRead) {
                    out.write(BUFFER, 0, bytesRead);
                    bytesRead = in.read(BUFFER);
                }
            } finally {
                if (null != in) {
                    in.close();
                }

                if (null != out) {
                    out.close();
                }
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param configFileName
     *
     * @return
     */
    public static boolean performStaticValidation(String configFileName) {
        assert(null != configFileName);

        AnalysisContext analysisContext = new AnalysisContext().invoke(configFileName);
        InternalScenarioRepresentation scenario = analysisContext.getScenario();
        CbvEventListenerBuilder cbvBuilder = analysisContext.getCbvBuilder();
        String[] defaultStartedQuests = analysisContext.getDefaultStartedQuest();

        return runStaticAnalysis(scenario, cbvBuilder, defaultStartedQuests);
    }

    /**
     * Method description
     *
     *
     * @param configFileName
     *
     * @return
     */
    public static boolean performDynamicValidation(String configFileName) {
        return (null != configFileName);
    }

    /**
     * Method description
     *
     *
     * @param arguments
     */
    public static void main(String[] arguments) {
        AnalysisContext analysisContext = new AnalysisContext().invoke(".\\Analysis.cfg");
        InternalScenarioRepresentation scenario = analysisContext.getScenario();
        CbvEventListenerBuilder cbvBuilder = analysisContext.getCbvBuilder();
        String[] defaultStartedQuests = analysisContext.getDefaultStartedQuest();

        runStaticAnalysis(scenario, cbvBuilder, defaultStartedQuests);
        runDynamicAnalysis(scenario, cbvBuilder, defaultStartedQuests);
        runGuiControllerAnalysis(scenario, defaultStartedQuests);
    }

    private static void runGuiControllerAnalysis(InternalScenarioRepresentation scenario,
            String[] defaultStartedQuests) {
        try {
            DynamicScenarioAnalyzer analyzerDynamic = new DynamicScenarioAnalyzer(scenario.getStatesMachine());
            AnalysisUI ui = new AnalysisUI();

            analyzerDynamic.uiSimulation(ui, defaultStartedQuests);
        } catch (Throwable e) {
            e.printStackTrace(System.err);
        } finally {
            ScenarioSync.interruptScriptRunningThread();
            moveResults();
        }
    }

    private static void runDynamicAnalysis(InternalScenarioRepresentation scenario, CbvEventListenerBuilder cbvBuilder,
            String[] defaultStartedQuest) {
        EventsController.getInstance().addListener(cbvBuilder.getWare());
        EventsController.getInstance().addListener(scenario.getStatesMachine());

        DynamicScenarioAnalyzer analyzerDynamic = new DynamicScenarioAnalyzer(scenario.getStatesMachine());

        analyzerDynamic.randomSimulation(defaultStartedQuest);
    }

    private static boolean runStaticAnalysis(InternalScenarioRepresentation scenario,
            CbvEventListenerBuilder cbvBuilder, String[] defaultStartedQuest) {
        AdjacencyMatrix scenarioGraph = new AdjacencyMatrix(scenario.getStatesSet(),
                                            cbvBuilder.getWare().getActionList());
        StaticScenarioAnalyzer analyzerStatic = new StaticScenarioAnalyzer(scenarioGraph);

        return analyzerStatic.validate(defaultStartedQuest);
    }

    private static class AnalysisContext {
        private String[] defaultStartedQuests;
        private InternalScenarioRepresentation scenario;
        private CbvEventListenerBuilder cbvBuilder;

        /**
         * Method description
         *
         *
         * @return
         */
        public String[] getDefaultStartedQuest() {
            return this.defaultStartedQuests;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public InternalScenarioRepresentation getScenario() {
            return this.scenario;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public CbvEventListenerBuilder getCbvBuilder() {
            return this.cbvBuilder;
        }

        /**
         * Method description
         *
         *
         * @param configFileName
         *
         * @return
         */
        public AnalysisContext invoke(String configFileName) {
            assert(null != configFileName);

            File logDirectory = new File(".\\warnings\\");

            if ((!(logDirectory.exists())) && (!(logDirectory.mkdir()))) {
                System.err.println("Failed to create directory for log files");
            }

            Properties defaultSettings = new Properties();

            defaultSettings.setProperty("quests", ".\\quests.xml");
            defaultSettings.setProperty("cbv", ".\\cbvideocalls.xml");
            defaultSettings.setProperty("msg", ".\\messageevents.xml");
            defaultSettings.setProperty("q", "gamebegining");
            defaultSettings.setProperty("save_folder", ".\\saves");
            defaultSettings.setProperty("save_file", "gamesave.xml");

            Properties settings = new Properties(defaultSettings);

            try {
                settings.load(new FileInputStream(configFileName));
            } catch (FileNotFoundException e) {
                ScenarioAnalysisLogger.getInstance().getLog().warning(e.getMessage());
            } catch (IOException e) {
                ScenarioAnalysisLogger.getInstance().getLog().warning(e.getMessage());
            }

            String questXmlFileName = settings.getProperty("quests");
            String cbvCallsXmlFileName = settings.getProperty("cbv");
            String msgEventsXmlFileName = settings.getProperty("msg");

            this.defaultStartedQuests = settings.getProperty("q").split(",");
            rnr.src.rnrcore.eng.noNative = true;
            ConfigManager.setConfigFilePath("");

            if (null == scenarioscript.script) {
                new scenarioscript();
            }

            NativeEventController.init();
            EventsControllerHelper.init();

            try {
                EventsControllerHelper.getInstance().uploadMessageEventsToRegister(msgEventsXmlFileName);
                this.scenario = XmlScenarioMachineBuilder.getScenarioMachine(questXmlFileName, true);
                this.cbvBuilder = new CbvEventListenerBuilder(cbvCallsXmlFileName, this.scenario.getStatesMachine());
            } catch (IOException exception) {
                System.err.println(exception.getMessage());
                exception.printStackTrace(System.err);

                return this;
            }

            return this;
        }
    }
}


//~ Formatted in DD Std on 13/08/27
