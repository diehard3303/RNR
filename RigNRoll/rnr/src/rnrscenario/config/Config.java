/*
 * @(#)Config.java   13/08/26
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


package rnr.src.rnrscenario.config;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Node;

import rnr.src.rnrloggers.ScriptsLogger;

//~--- JDK imports ------------------------------------------------------------

import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public abstract class Config {

    /** Field description */
    public static final int GAME_LEVEL_HARD = 2;

    /** Field description */
    public static final int GAME_LEVEL_MEDIUM = 1;

    /** Field description */
    public static final int GAME_LEVEL_EASY = 0;

    /** Field description */
    public static final int GAME_LEVELS_COUNT = 3;

    /** Field description */
    public static final String GROUP_LOCATIONS = "locations";

    /** Field description */
    public static final String GROUP_CHASES = "chases";
    private boolean needReloadOnQuery = true;
    private final String nodeNameToLoadFrom;
    private final String nodesGroup;
    private final int uid;
    private DataReloader dataReloader;

    protected Config(String groupName, String nodeToLoadFrom, int uid) {
        assert(null != nodeToLoadFrom);
        this.nodesGroup = groupName;
        this.nodeNameToLoadFrom = nodeToLoadFrom;
        this.uid = uid;
    }

    /**
     * Method description
     *
     *
     * @param value
     *
     * @return
     */
    public static boolean isGameLevelDifficultyValid(int value) {
        return ((0 == value) || (2 == value) || (1 == value));
    }

    void setDataReloader(DataReloader reloader) {
        this.dataReloader = reloader;
    }

    protected final void reloadData() {
        if ((!(this.needReloadOnQuery)) || (null == this.dataReloader)) {
            return;
        }

        this.dataReloader.perform(this.uid);
    }

    protected abstract void loadFromNode(Node paramNode);

    protected abstract void setGameLevel(int paramInt);

    /**
     * Method description
     *
     *
     * @return
     */
    public final int getUid() {
        return this.uid;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final String getNodesGroup() {
        return this.nodesGroup;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final String getNodeNameToLoadFrom() {
        return this.nodeNameToLoadFrom;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isNeedReloadOnQuery() {
        return this.needReloadOnQuery;
    }

    /**
     * Method description
     *
     *
     * @param needReloadOnQuery
     */
    public void setNeedReloadOnQuery(boolean needReloadOnQuery) {
        this.needReloadOnQuery = needReloadOnQuery;
    }

    protected static String[] loadTripleValuesOrNull(Node source) {
        if (null == source) {
            return null;
        }

        Node easyLevelValueNode = source.getAttributes().getNamedItem("game_level_easy");
        Node mediumLevelValueNode = source.getAttributes().getNamedItem("game_level_medium");
        Node hardLevelValueNode = source.getAttributes().getNamedItem("game_level_hard");

        if ((null == easyLevelValueNode) || (null == mediumLevelValueNode) || (null == hardLevelValueNode)) {
            return null;
        }

        String[] result = new String[3];

        result[0] = easyLevelValueNode.getTextContent();
        result[1] = mediumLevelValueNode.getTextContent();
        result[2] = hardLevelValueNode.getTextContent();

        return result;
    }

    protected static void fillTripleValues(Node source, double[] values) {
        if (null == source) {
            return;
        }

        assert(null != values);
        assert(3 == values.length);

        String[] rawValues = loadTripleValuesOrNull(source);

        if (null == rawValues) {
            return;
        }

        double[] result = new double[rawValues.length];

        try {
            for (int i = 0; i < rawValues.length; ++i) {
                result[i] = Double.parseDouble(rawValues[i]);
            }
        } catch (NumberFormatException e) {
            System.err.println("bad data in scenario config file");

            return;
        }

        System.arraycopy(result, 0, values, 0, values.length);
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param defaultValue
     *
     * @return
     */
    public static double parseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            ScriptsLogger.getInstance().log(Level.WARNING, 4, "error loading config: " + e.getMessage());
        }

        return defaultValue;
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param defaultValue
     *
     * @return
     */
    public static int parseInteger(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            ScriptsLogger.getInstance().log(Level.WARNING, 4, "error loading config: " + e.getMessage());
        }

        return defaultValue;
    }
}


//~ Formatted in DD Std on 13/08/26
