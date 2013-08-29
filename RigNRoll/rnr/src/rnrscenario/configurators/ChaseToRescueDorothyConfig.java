/*
 * @(#)ChaseToRescueDorothyConfig.java   13/08/26
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

import rnr.src.rnrscenario.config.Config;
import rnr.src.scenarioXml.XmlFilter;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class ChaseToRescueDorothyConfig extends Config {
    private static final String MY_NODE = "chase_rescue_dorothy";
    private final int[] timeToRescue = new int[3];
    private final double[] distanceToShoot = { 100.0D, 100.0D, 100.0D };
    private final double[] distanceToRescue = { 300.0D, 300.0D, 300.0D };
    private final double[] distanceToKill = { 10.0D, 10.0D, 10.0D };
    private int gameDifficultyLevel = 0;

    /**
     * Constructs ...
     *
     */
    public ChaseToRescueDorothyConfig() {
        super("chases", "chase_rescue_dorothy", 2);
    }

    @Override
    protected void loadFromNode(Node source) {
        if (null == source) {
            return;
        }

        XmlFilter traversal = new XmlFilter(source.getChildNodes());
        Node timingsNode = traversal.nodeNameNext("timings");

        if (null != timingsNode) {
            String[] timings = Config.loadTripleValuesOrNull(
                                   new XmlFilter(timingsNode.getChildNodes()).nodeNameNext("time_to_rescue"));

            if (null != timings) {
                try {
                    assert(timings.length == this.timeToRescue.length);

                    int[] temporaryTimings = new int[timings.length];

                    for (int i = 0; i < timings.length; ++i) {
                        temporaryTimings[i] = Integer.parseInt(timings[i]);
                    }

                    System.arraycopy(temporaryTimings, 0, this.timeToRescue, 0, timings.length);
                } catch (NumberFormatException e) {
                    System.err.println("illigal data in scenario config file");
                }
            }
        }

        Node distancesNode = traversal.goOnStart().nodeNameNext("distances");

        if (null == distancesNode) {
            return;
        }

        XmlFilter distancesNodesTraversal = new XmlFilter(distancesNode.getChildNodes());

        Config.fillTripleValues(distancesNodesTraversal.goOnStart().nodeNameNext("to_shoot"), this.distanceToShoot);
        Config.fillTripleValues(distancesNodesTraversal.goOnStart().nodeNameNext("to_rescue"), this.distanceToRescue);
        Config.fillTripleValues(distancesNodesTraversal.goOnStart().nodeNameNext("to_kill"), this.distanceToKill);
    }

    @Override
    protected void setGameLevel(int gameLevel) {
        reloadData();
        this.gameDifficultyLevel = gameLevel;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getTimeToRescue() {
        reloadData();

        return this.timeToRescue[this.gameDifficultyLevel];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getDistanceToRescue() {
        reloadData();

        return this.distanceToRescue[this.gameDifficultyLevel];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getDistanceToKill() {
        reloadData();

        return this.distanceToKill[this.gameDifficultyLevel];
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getDistanceToShoot() {
        reloadData();

        return this.distanceToShoot[this.gameDifficultyLevel];
    }
}


//~ Formatted in DD Std on 13/08/26
