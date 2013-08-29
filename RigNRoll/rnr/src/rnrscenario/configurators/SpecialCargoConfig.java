/*
 * @(#)SpecialCargoConfig.java   13/08/26
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
public final class SpecialCargoConfig extends Config {
    private static final String MY_NODE = "chase_special_cargo";
    private final double[] banditsDelay = new double[3];
    private double blowRadius = 8.0D;
    private int gameDifficultyLevel = 0;

    /**
     * Constructs ...
     *
     */
    public SpecialCargoConfig() {
        super("chases", "chase_special_cargo", 4);
        this.banditsDelay[0] = 241.0D;
        this.banditsDelay[1] = 181.0D;
        this.banditsDelay[2] = 121.0D;
    }

    @Override
    protected void loadFromNode(Node source) {
        if (null == source) {
            return;
        }

        XmlFilter rootChildrenFilter = new XmlFilter(source.getChildNodes());
        Node timingsNode = rootChildrenFilter.nodeNameNext("timings");

        if (null != timingsNode) {
            Config.fillTripleValues(new XmlFilter(timingsNode.getChildNodes()).nodeNameNext("call_from_bandits_delay"),
                                    this.banditsDelay);
        }

        rootChildrenFilter.goOnStart();

        Node distancesNode = rootChildrenFilter.nodeNameNext("distances");

        if (null == distancesNode) {
            return;
        }

        Node distanceNode = new XmlFilter(distancesNode.getChildNodes()).nodeNameNext("player_bounding_sphere_radius");

        if ((null == distanceNode) || (null == distanceNode.getTextContent())) {
            return;
        }

        try {
            this.blowRadius = Double.parseDouble(distanceNode.getTextContent());
        } catch (NumberFormatException e) {
            System.err.print("SpecialCargoConfig.loadFromNode: " + e.getMessage());
        }
    }

    @Override
    protected void setGameLevel(int gameLevel) {
        this.gameDifficultyLevel = gameLevel;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getBlowRadius() {
        return this.blowRadius;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getBanditsDelay() {
        return this.banditsDelay[this.gameDifficultyLevel];
    }
}


//~ Formatted in DD Std on 13/08/26
