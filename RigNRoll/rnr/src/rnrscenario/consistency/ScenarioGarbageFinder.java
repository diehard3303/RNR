/*
 * @(#)ScenarioGarbageFinder.java   13/08/28
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

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public final class ScenarioGarbageFinder {
    private static final int ERROR_REPORT_CAPACITY = 1024;
    private static final StringBuilder STRING_BUFFER = new StringBuilder(1024);
    private static boolean fatalOnGarbage = true;

    /**
     * Method description
     *
     *
     * @param fatalOnGarbage
     */
    public static void setFatalOnGarbage(boolean fatalOnGarbage) {
        ScenarioGarbageFinder.fatalOnGarbage = fatalOnGarbage;
    }

    /**
     * Method description
     *
     *
     * @param context
     * @param targetToTest
     * @param scenarioStage
     *
     * @return
     */
    public static boolean isExpired(String context, Object targetToTest, ScenarioStage scenarioStage) {
        if ((null == targetToTest) || (null == scenarioStage)) {
            System.err.println("Invalid arguments came into ScenarioGarbageFinder.isExpired");

            return false;
        }

        try {
            if (isExpired(targetToTest, scenarioStage)) {
                if ((!(eng.noNative)) && (fatalOnGarbage)) {
                    eng.fatal(
                        String.format(
                            "Detected invalid object in scenario stage %d; context: %s, object class: %s",
                            new Object[] { Integer.valueOf(scenarioStage.getScenarioStage()),
                                           context, targetToTest.getClass() }));
                }

                return true;
            }
        } catch (IllegalAccessException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
        } catch (NoSuchFieldException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
        }

        return false;
    }

    private static boolean isExpired(Object targetToTest, ScenarioStage scenarioStage)
            throws IllegalAccessException, NoSuchFieldException {
        if ((null == targetToTest) || (null == scenarioStage)) {
            return false;
        }

        ScenarioClass scenarioMetaData = targetToTest.getClass().getAnnotation(ScenarioClass.class);

        if (null != scenarioMetaData) {
            if (-1 == scenarioMetaData.scenarioStage()) {
                Field fieldWithStage =
                    targetToTest.getClass().getDeclaredField(scenarioMetaData.fieldWithDesiredStage());

                fieldWithStage.setAccessible(true);

                if (Integer.TYPE == fieldWithStage.getType()) {
                    int desiredScanrioStage = fieldWithStage.getInt(targetToTest);

                    if (desiredScanrioStage != scenarioStage.getScenarioStage()) {
                        return true;
                    }
                } else {
                    throw new IllegalStateException("Illegal annotation on " + targetToTest.getClass()
                                                    + " class: field " + fieldWithStage + "is not 'int'");
                }
            } else if (scenarioMetaData.scenarioStage() != scenarioStage.getScenarioStage()) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("rawtypes")
    private static void findOutOfDateScenarioObjects(String containerName, Iterable container,
            ScenarioStage scenarioStage, GarbageProcessor processor) {
        STRING_BUFFER.setLength(0);

        int objectsRemoved = 0;
        Iterator iter;

        if ((null != container) && (null != scenarioStage)) {
            for (iter = container.iterator(); iter.hasNext(); ) {
                Object objectFromContainer = iter.next();

                try {
                    if (isExpired(objectFromContainer, scenarioStage)) {
                        ++objectsRemoved;
                        STRING_BUFFER.append("\tobject's class=").append(objectFromContainer.getClass()).append('\n');

                        if (null != processor) {
                            processor.process(iter, objectFromContainer);
                        }
                    }
                } catch (IllegalAccessException e) {
                    System.err.print(e.getMessage());
                    e.printStackTrace(System.err);
                } catch (NoSuchFieldException e) {
                    System.err.print(e.getMessage());
                    e.printStackTrace(System.err);
                }
            }
        }

        if (0 >= objectsRemoved) {
            return;
        }

        STRING_BUFFER.insert(0, " contains garbage:\n");
        STRING_BUFFER.insert(0, containerName);
        STRING_BUFFER.append("total ").append(objectsRemoved).append(" objects were removed");

        if (fatalOnGarbage) {
            if (eng.noNative) {
                return;
            }

            eng.fatal(STRING_BUFFER.toString());
        } else {
            eng.err(STRING_BUFFER.toString());
        }
    }

    /**
     * Method description
     *
     *
     * @param containerName
     * @param container
     * @param scenarioStage
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Object> getOutOfDateScenarioObjects(String containerName, Iterable container,
            ScenarioStage scenarioStage) {
        List garbageList = new ArrayList();

        findOutOfDateScenarioObjects(containerName, container, scenarioStage, new GarbageProcessor(garbageList) {
            @Override
            public void process(Iterator containerIterator, Object garbage) {
                this.garbageList.add(garbage);
            }
        });

        return garbageList;
    }

    /**
     * Method description
     *
     *
     * @param containerName
     * @param container
     * @param scenarioStage
     */
    public static void deleteOutOfDateScenarioObjects(String containerName, Iterable container,
            ScenarioStage scenarioStage) {
        findOutOfDateScenarioObjects(containerName, container, scenarioStage, new GarbageProcessor() {
            @Override
            public void process(Iterator containerIterator, Object garbage) {
                containerIterator.remove();
            }
        });
    }

    private static abstract interface GarbageProcessor {

        /**
         * Method description
         *
         *
         * @param paramIterator
         * @param paramObject
         */
        public abstract void process(Iterator paramIterator, Object paramObject);
    }
}


//~ Formatted in DD Std on 13/08/28
