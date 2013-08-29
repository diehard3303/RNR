/*
 * @(#)ChaseCochConfig.java   13/08/26
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
public final class ChaseCochConfig extends Config {
    private static final String MY_NODE = "chase_cochraine";
    private static final double DEFAULT_CATCH_TIME = 180.0D;
    private static final int DEFAULT_SHOOTS_COUNT = 10;
    private static final double DEFAULT_DISTANCE = 200.0D;
    private static final XPathExpression[] secondsToCatchCochQuery;
    private static final XPathExpression[] shootsToKillCochQuery;
    private static final XPathExpression[] distanceToHitCochQuery;

    static {
        secondsToCatchCochQuery = new XPathExpression[3];
        shootsToKillCochQuery = new XPathExpression[3];
        distanceToHitCochQuery = new XPathExpression[3];
        secondsToCatchCochQuery[0] = XmlUtils.makeXpath("./timings/time_to_catch/@game_level_easy");
        secondsToCatchCochQuery[1] = XmlUtils.makeXpath("./timings/time_to_catch/@game_level_medium");
        secondsToCatchCochQuery[2] = XmlUtils.makeXpath("./timings/time_to_catch/@game_level_hard");
        shootsToKillCochQuery[0] = XmlUtils.makeXpath("./counts/shoot_to_kill_coch/@game_level_easy");
        shootsToKillCochQuery[1] = XmlUtils.makeXpath("./counts/shoot_to_kill_coch/@game_level_medium");
        shootsToKillCochQuery[2] = XmlUtils.makeXpath("./counts/shoot_to_kill_coch/@game_level_hard");
        distanceToHitCochQuery[0] = XmlUtils.makeXpath("./distances/distance_to_hit_coch/@game_level_easy");
        distanceToHitCochQuery[1] = XmlUtils.makeXpath("./distances/distance_to_hit_coch/@game_level_medium");
        distanceToHitCochQuery[2] = XmlUtils.makeXpath("./distances/distance_to_hit_coch/@game_level_hard");
    }

    private final double[] secondsToCatchCoch = new double[3];
    private final int[] shootsToKillCoch = new int[3];
    private final double[] distanceToHitCoch = new double[3];
    private int gameDifficultyLevel;

    /**
     * Constructs ...
     *
     */
    public ChaseCochConfig() {
        super("chases", "chase_cochraine", 1);

        for (int i = 0; i < 3; ++i) {
            this.secondsToCatchCoch[i] = 180.0D;
            this.shootsToKillCoch[i] = 10;
            this.distanceToHitCoch[i] = 200.0D;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getTimeToCatchCoch() {
        reloadData();

        return this.secondsToCatchCoch[this.gameDifficultyLevel];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getShootsToKillCoch() {
        reloadData();

        return this.shootsToKillCoch[this.gameDifficultyLevel];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getDistanceToHitCoch() {
        reloadData();

        return this.distanceToHitCoch[this.gameDifficultyLevel];
    }

    @Override
    protected void loadFromNode(Node source) {
        if (null == source) {
            return;
        }

        try {
            for (int i = 0; i < 3; ++i) {
                this.secondsToCatchCoch[i] = parseDouble((String) secondsToCatchCochQuery[i].evaluate(source,
                        XPathConstants.STRING), 180.0D);
                this.distanceToHitCoch[i] = parseDouble((String) distanceToHitCochQuery[i].evaluate(source,
                        XPathConstants.STRING), 200.0D);
                this.shootsToKillCoch[i] = parseInteger((String) shootsToKillCochQuery[i].evaluate(source,
                        XPathConstants.STRING), 10);
            }
        } catch (XPathExpressionException e) {
            ScriptsLogger.getInstance().log(Level.WARNING, 4,
                                            "error loading coch chase timings config: " + e.getMessage());
        }
    }

    @Override
    protected void setGameLevel(int gameLevel) {
        assert((0 <= gameLevel) && (this.secondsToCatchCoch.length > gameLevel));
        this.gameDifficultyLevel = gameLevel;
    }
}


//~ Formatted in DD Std on 13/08/26
