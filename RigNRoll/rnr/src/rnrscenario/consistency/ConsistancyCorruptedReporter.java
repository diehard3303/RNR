/*
 * @(#)ConsistancyCorruptedReporter.java   13/08/26
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


package rnr.src.rnrscenario.consistency;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.eng;
import rnr.src.rnrloggers.ScriptsLogger;
import rnr.src.rnrscenario.scenarioscript;

//~--- JDK imports ------------------------------------------------------------

import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class ConsistancyCorruptedReporter {
    private static final int ERROR_MESSAGE_CAPACITY = 1024;
    private static final int ENCLOSING_METHOD_INDEX = 3;
    private static final StringBuilder STRING_DUFFER;
    private static final Object latch;

    static {
        STRING_DUFFER = new StringBuilder(1024);
        latch = new Object();
    }

    private static void writeStackTraceInfoIntoBuffer() {
        synchronized (latch) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

            for (StackTraceElement stackTraceElement : stackTrace) {
                STRING_DUFFER.append('\t').append(stackTraceElement).append('\n');
            }
        }
    }

    /**
     * Method description
     *
     */
    public static void scenarioBackwardMoving() {
        synchronized (latch) {
            if (eng.noNative) {
                throw new ScenarioConsistencyCorruptedException("backward scenario moving");
            }

            STRING_DUFFER.setLength(0);
            STRING_DUFFER.append("scenario corrupted (backward moving)\n");
            STRING_DUFFER.append("stack trace: \n");
            writeStackTraceInfoIntoBuffer();
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, STRING_DUFFER.toString());
            eng.fatal("Scenario state corruption detected: attempt to move state backward!");
        }
    }

    /**
     * Method description
     *
     *
     * @param containerClass
     * @param storedElementsType
     */
    public static void undeletedTrashFound(Class containerClass, Class<?> storedElementsType) {
        synchronized (latch) {
            if (eng.noNative) {
                throw new ScenarioConsistencyCorruptedException("trash found in container");
            }

            STRING_DUFFER.setLength(0);

            ScenarioClass scenarioMetaData = storedElementsType.getAnnotation(ScenarioClass.class);

            assert(null != scenarioMetaData);
            STRING_DUFFER.append("scenario corrupted (undeleted reference found in container)\n");
            STRING_DUFFER.append("container class: ").append(containerClass.getName()).append('\n');
            STRING_DUFFER.append("reference class: ").append(storedElementsType.getName()).append('\n');
            STRING_DUFFER.append("current scenario stage: ").append(
                scenarioscript.script.getStage().getScenarioStage()).append('\n');
            STRING_DUFFER.append("desired scenario stage: ").append(scenarioMetaData.scenarioStage()).append('\n');
            STRING_DUFFER.append("stack trace: \n");
            writeStackTraceInfoIntoBuffer();
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, STRING_DUFFER.toString());
            eng.fatal("Scenario state corruption detected: garbage listeners found!");
        }
    }

    /**
     * Method description
     *
     *
     * @param message
     */
    public static void methodCalledNotInTime(String message) {
        synchronized (latch) {
            if (eng.noNative) {
                throw new ScenarioConsistencyCorruptedException("method called not in time");
            }

            STRING_DUFFER.setLength(0);

            String badMethodName = Thread.currentThread().getStackTrace()[3].getMethodName();

            STRING_DUFFER.append("scenario corrupted (method called not in time)\n");
            STRING_DUFFER.append("message: ").append(message).append('\n');
            STRING_DUFFER.append("method: ").append(badMethodName).append('\n');
            STRING_DUFFER.append("current scenario stage: ").append(
                scenarioscript.script.getStage().getScenarioStage()).append('\n');
            STRING_DUFFER.append("stack trace: \n");
            writeStackTraceInfoIntoBuffer();
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, STRING_DUFFER.toString());
            eng.fatal(
                "Scenario state corruption detected: method called not in time! Futher game working is not guaranteed.");
        }
    }

    /**
     * Method description
     *
     *
     * @param clazz
     */
    public static void methodCalledNotInTime(Class<?> clazz) {
        synchronized (latch) {
            if (eng.noNative) {
                throw new ScenarioConsistencyCorruptedException("method called not in time");
            }

            STRING_DUFFER.setLength(0);

            String badMethodName = Thread.currentThread().getStackTrace()[3].getMethodName();
            ScenarioClass scenarioMetaData = clazz.getAnnotation(ScenarioClass.class);

            assert(null != scenarioMetaData);
            STRING_DUFFER.append("scenario corrupted (method called not in time)\n");
            STRING_DUFFER.append("class: ").append(clazz.getName()).append('\n');
            STRING_DUFFER.append("method: ").append(badMethodName).append('\n');
            STRING_DUFFER.append("current scenario stage: ").append(
                scenarioscript.script.getStage().getScenarioStage()).append('\n');
            STRING_DUFFER.append("desired scenario stage: ").append(scenarioMetaData.scenarioStage()).append('\n');
            STRING_DUFFER.append("stack trace: \n");
            writeStackTraceInfoIntoBuffer();
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, STRING_DUFFER.toString());
            eng.fatal(
                "Scenario state corruption detected: method called not in time! Futher game working is not guaranteed.");
        }
    }
}


//~ Formatted in DD Std on 13/08/26
