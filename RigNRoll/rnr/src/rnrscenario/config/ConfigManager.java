/*
 * @(#)ConfigManager.java   13/08/26
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

import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.HashMap;
import java.util.Map;

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
public final class ConfigManager implements DataReloader {
    private static String CONFIG_FILE = "..\\Data\\config\\scenario_values.xml";
    private static ConfigManager global;
    private final Map<Integer, Config> slaves;
    private final Map<Integer, XPathExpression> xPathCache;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ConfigManager() {
        this.slaves = new HashMap();
        this.xPathCache = new HashMap();
    }

    static void dropGlobal() {
        global = null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static ConfigManager getGlobal() {
        return global;
    }

    /**
     * Method description
     *
     *
     * @param global
     */
    public static void setGlobal(ConfigManager global) {
        if (null == global) {
            global = global;
        } else {
            throw new IllegalStateException("Could not set global twice");
        }
    }

    /**
     * Method description
     *
     *
     * @param path
     */
    public static void setConfigFilePath(String path) {
        if (null == path) {
            return;
        }

        CONFIG_FILE = path;
    }

    /**
     * Method description
     *
     *
     * @param newSlave
     */
    public void addConfig(Config newSlave) {
        if (null == newSlave) {
            return;
        }

        newSlave.setDataReloader(this);
        this.slaves.put(Integer.valueOf(newSlave.getUid()), newSlave);
    }

    /**
     * Method description
     *
     *
     * @param configUid
     *
     * @return
     */
    public Config getConfig(int configUid) {
        return (this.slaves.get(Integer.valueOf(configUid)));
    }

    /**
     * Method description
     *
     *
     * @param gameLevel
     */
    public void setGameLevel(int gameLevel) {
        if (Config.isGameLevelDifficultyValid(gameLevel)) {
            for (Config config : this.slaves.values()) {
                config.setGameLevel(gameLevel);
            }
        } else {
            System.err.print("AHTUNG!!! illegal game level!!!");
        }
    }

    /**
     * Method description
     *
     *
     * @param doReload
     */
    public void reloadConfigsOnQuery(boolean doReload) {
        for (Config config : this.slaves.values()) {
            config.setNeedReloadOnQuery(doReload);
        }
    }

    private void reloadConfig(Config toReload, Node rootNode) {
        if ((null == rootNode) || (null == toReload)) {
            return;
        }

        XPathExpression xPath = this.xPathCache.get(Integer.valueOf(toReload.getUid()));

        if (null == xPath) {
            StringBuilder xPathQuery = new StringBuilder();

            xPathQuery.append("/scenario");

            if (null != toReload.getNodesGroup()) {
                xPathQuery.append('/');
                xPathQuery.append(toReload.getNodesGroup());
            }

            xPathQuery.append('/');
            xPathQuery.append(toReload.getNodeNameToLoadFrom());
            xPath = XmlUtils.makeXpath(xPathQuery.toString());
            this.xPathCache.put(Integer.valueOf(toReload.getUid()), xPath);
        }

        try {
            org.w3c.dom.Node configNode = (org.w3c.dom.Node) xPath.evaluate(rootNode.getNode(), XPathConstants.NODE);

            toReload.loadFromNode(configNode);
        } catch (XPathExpressionException e) {
            System.err.print(e.getMessage());
        }
    }

    /**
     * Method description
     *
     */
    public void load() {
        Node root = XmlUtils.parse(CONFIG_FILE);

        for (Config config : this.slaves.values()) {
            reloadConfig(config, root);
        }
    }

    /**
     * Method description
     *
     *
     * @param sourceUid
     */
    @Override
    public void perform(int sourceUid) {
        reloadConfig(this.slaves.get(Integer.valueOf(sourceUid)), XmlUtils.parse(CONFIG_FILE));
    }
}


//~ Formatted in DD Std on 13/08/26
