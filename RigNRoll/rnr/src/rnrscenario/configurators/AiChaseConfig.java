/*
 * @(#)AiChaseConfig.java   13/08/26
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

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import rnr.src.players.Chase;
import rnr.src.rnrscenario.config.Config;
import rnr.src.scenarioXml.XmlFilter;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class AiChaseConfig extends Config {

    /** Field description */
    public static final int relative = 1;

    /** Field description */
    public static final int absolute = 2;
    private static final String MY_NODE_NAME1 = "ai_chase_parameters";
    private static final String MY_NODE_NAME2 = "all_parameters";
    private final HashMap<String, ChaseParameters> chase_parameters = new HashMap();
    private final ChaseParameters default_parameters = new ChaseParameters("default");
    private int gameDifficultyLevel = 0;

    /**
     * Constructs ...
     *
     */
    public AiChaseConfig() {
        super("ai_chase_parameters", "all_parameters", 3);
    }

    @Override
    protected void loadFromNode(Node source) {
        if (null == source) {
            return;
        }

        this.chase_parameters.clear();
        this.chase_parameters.put("default", this.default_parameters);

        XmlFilter traversal = new XmlFilter(source.getChildNodes());
        Node node = traversal.nextElement();

        while (null != node) {
            String name = null;

            if (node.getAttributes() != null) {
                Node node_name = node.getAttributes().getNamedItem("name");

                if (node_name != null) {
                    name = node_name.getTextContent();
                }
            }

            if ((name != null) && (!(this.chase_parameters.containsKey(name)))) {
                ChaseParameters new_parameters = new ChaseParameters(name);

                if (node.getAttributes() != null) {
                    Node mode_name = node.getAttributes().getNamedItem("mode");

                    if ((mode_name != null) && (mode_name.getTextContent() != null)
                            && ("relative".compareToIgnoreCase(mode_name.getTextContent()) == 0)) {
                        new_parameters.mode = 1;
                    }
                }

                XmlFilter localtraverse = new XmlFilter(node.getChildNodes());

                if (localtraverse != null) {
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("dist_ahead0"),
                                            new_parameters.dist_ahead0);
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("dist_ahead2"),
                                            new_parameters.dist_ahead2);
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("dist_behind0"),
                                            new_parameters.dist_behind0);
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("dist_behind2"),
                                            new_parameters.dist_behind2);
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("minvel"), new_parameters.minvel);
                    Config.fillTripleValues(localtraverse.goOnStart().nodeNameNext("maxvel"), new_parameters.maxvel);
                    this.chase_parameters.put(name, new_parameters);
                }
            }

            node = traversal.nextElement();
        }
    }

    @Override
    protected void setGameLevel(int gameLevel) {
        reloadData();
        assert(gameLevel < 3);
        this.gameDifficultyLevel = gameLevel;
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param chase
     */
    public void FillParameters(String name, Chase chase) {
        assert(chase != null);
        reloadData();
        assert(name != null);

        ChaseParameters new_parameters = this.chase_parameters.get(name);

        if (new_parameters == null) {
            new_parameters = this.default_parameters;
        }

        assert(new_parameters != null);
        chase.setMode(new_parameters.mode);
        chase.setDistAhead2(new_parameters.dist_ahead2[this.gameDifficultyLevel]);
        chase.setDistAhead0(new_parameters.dist_ahead0[this.gameDifficultyLevel]);
        chase.setDistBehind0(new_parameters.dist_behind0[this.gameDifficultyLevel]);
        chase.setDistBehind2(new_parameters.dist_behind2[this.gameDifficultyLevel]);
        chase.setMinVel(new_parameters.minvel[this.gameDifficultyLevel]);
        chase.setMaxVel(new_parameters.maxvel[this.gameDifficultyLevel]);
    }

    private static final class ChaseParameters {
        String name = null;

        /** Field description */
        public int mode = 2;

        /** Field description */
        public double[] dist_ahead0 = { 10.0D, 10.0D, 10.0D };

        /** Field description */
        public double[] dist_ahead2 = { 11.0D, 11.0D, 11.0D };

        /** Field description */
        public double[] dist_behind0 = { -10.0D, -10.0D, -10.0D };

        /** Field description */
        public double[] dist_behind2 = { -11.0D, -11.0D, -11.0D };

        /** Field description */
        public double[] minvel = { 20.0D, 30.0D, 40.0D };

        /** Field description */
        public double[] maxvel = { 40.0D, 50.0D, 70.0D };

        ChaseParameters(String name) {
            this.name = name;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
