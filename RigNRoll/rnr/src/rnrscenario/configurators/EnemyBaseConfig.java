/*
 * @(#)EnemyBaseConfig.java   13/08/26
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


package rnr.src.rnrscenario.configurators;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Node;

import rnr.src.rnrloggers.ScriptsLogger;
import rnr.src.rnrscenario.config.Config;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.util.logging.Level;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class EnemyBaseConfig extends Config {
    private static final String MY_NODE_NAME = "enemy_base";

    /** Field description */
    public static final int TIMING_BREAK_ROPE = 0;
    private static final int TOTAL_TIMINGS = 1;
    private static final double[] DEFAULT_TIMINGS;
    private static final String[] GAME_LEVEL_ATTRIBUTES;
    private static final XPathExpression[][] TIMINGS_XPATH_QUERIES;

    static {
        DEFAULT_TIMINGS = new double[1];
        GAME_LEVEL_ATTRIBUTES = new String[3];
        TIMINGS_XPATH_QUERIES = new XPathExpression[3][1];
    }

    private final double[][] timingsForGameLevels = new double[3][1];
    private double[] timings = this.timingsForGameLevels[0];
    private final XPathExpression BREAKING_DISTANCE_XPATH_QUERY;
    private double breakingDistanceAfterRopeBreaking;

    /**
     * Constructs ...
     *
     */
    public EnemyBaseConfig() {
        super("locations", "enemy_base", 0);
        DEFAULT_TIMINGS[0] = 50.0D;
        GAME_LEVEL_ATTRIBUTES[0] = "game_level_easy";
        GAME_LEVEL_ATTRIBUTES[1] = "game_level_medium";
        GAME_LEVEL_ATTRIBUTES[2] = "game_level_hard";

        for (int i = 0; i < 3; ++i) {
            String xPath = String.format("./timings/time_to_break_rope/@%s", new Object[] { GAME_LEVEL_ATTRIBUTES[i] });

            TIMINGS_XPATH_QUERIES[i][0] = XmlUtils.makeXpath(xPath);

            if (null != TIMINGS_XPATH_QUERIES[i][0]) {
                continue;
            }

            throw new IllegalStateException("check xPath syntax in EnemyBaseConfig static constructor");
        }

        this.BREAKING_DISTANCE_XPATH_QUERY = XmlUtils.makeXpath("./distances/drive_after_broken_rope/@length");

        for (double[] timingsForGameLevel : this.timingsForGameLevels) {
            System.arraycopy(DEFAULT_TIMINGS, 0, timingsForGameLevel, 0, timingsForGameLevel.length);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getAfterRopeBreakingDistance() {
        reloadData();

        return this.breakingDistanceAfterRopeBreaking;
    }

    /**
     * Method description
     *
     *
     * @param timingId
     *
     * @return
     */
    public double getTiming(int timingId) {
        reloadData();

        if ((0 <= timingId) && (this.timings.length > timingId)) {
            return this.timings[timingId];
        }

        return 0.0D;
    }

    @Override
    protected void loadFromNode(Node source) {
        if (null == source) {
            return;
        }

        try {
            String breakingDistanceText = (String) this.BREAKING_DISTANCE_XPATH_QUERY.evaluate(source,
                                              XPathConstants.STRING);

            this.breakingDistanceAfterRopeBreaking = Double.parseDouble(breakingDistanceText);
        } catch (NumberFormatException e) {
            ScriptsLogger.getInstance().log(Level.WARNING, 4,
                                            "error loading enemy base distances config: " + e.getMessage());
        } catch (XPathExpressionException e) {
            ScriptsLogger.getInstance().log(Level.WARNING, 4,
                                            "error loading enemy base distances config: " + e.getMessage());
        }

        for (int gameLevel = 0; gameLevel < 3; ++gameLevel) {
            try {
                Node timingNode = (Node) TIMINGS_XPATH_QUERIES[gameLevel][0].evaluate(source, XPathConstants.NODE);

                if (null != timingNode) {
                    this.timingsForGameLevels[gameLevel][0] = Double.parseDouble(timingNode.getTextContent());
                }
            } catch (XPathExpressionException e) {
                ScriptsLogger.getInstance().log(Level.WARNING, 4,
                                                "error loading enemy base timings config: " + e.getMessage());
            } catch (NumberFormatException e) {
                ScriptsLogger.getInstance().log(Level.WARNING, 4,
                                                "error loading enemy base timings config: " + e.getMessage());
            }
        }
    }

    @Override
    protected void setGameLevel(int gameLevel) {
        assert((0 <= gameLevel) && (this.timingsForGameLevels.length > gameLevel));
        this.timings = this.timingsForGameLevels[gameLevel];
    }
}


//~ Formatted in DD Std on 13/08/26
