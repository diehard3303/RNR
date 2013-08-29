/*
 * @(#)GameXmlSerializator.java   13/08/26
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


package rnr.src.rnrcore;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import rnr.src.rnrloggers.ScriptsLogger;
import rnr.src.rnrscenario.consistency.ScenarioGarbageFinder;
import rnr.src.rnrscenario.consistency.ScenarioStage;
import rnr.src.rnrscenario.consistency.StageChangedListener;
import rnr.src.scenarioXml.XmlDocument;
import rnr.src.scenarioXml.XmlFilter;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class GameXmlSerializator implements StageChangedListener {

    /** Field description */
    public static final int NO_VERSIONING = -1;
    private static final int BYTE_STREAM_INITIAL_CAPACITY = 1024;
    private static final String GAME_SAVE_ROOT_NODE_NAME = "RnRSave";
    private static final String GAME_SAVE_ROOT_VERSION_ATTR = "version";
    private List<XmlSerializable> serializableXmlObjects = null;
    private String path = null;
    private String file = null;
    private byte[] stream = null;
    private boolean loadFromArray = false;
    private int save_version = -1;
    private int load_version = -1;

    /**
     * Constructs ...
     *
     */
    public GameXmlSerializator() {
        this.loadFromArray = false;
    }

    /**
     * Constructs ...
     *
     *
     * @param toLoadFrom
     */
    public GameXmlSerializator(byte[] toLoadFrom) {
        if (null == toLoadFrom) {
            throw new IllegalArgumentException("toLoadFrom must be non-null reference");
        }

        this.stream = toLoadFrom;
        this.loadFromArray = true;
    }

    /**
     * Constructs ...
     *
     *
     * @param pathToFolder
     * @param fileName
     */
    public GameXmlSerializator(String pathToFolder, String fileName) {
        if (null == pathToFolder) {
            throw new IllegalArgumentException("pathToFolder must be non-null reference");
        }

        if (null == fileName) {
            throw new IllegalArgumentException("fileName must be non-null reference");
        }

        this.path = pathToFolder;

        if ('\\' != this.path.charAt(this.path.length() - 1)) {
            this.path += '\\';
        }

        this.file = fileName;
        this.serializableXmlObjects = new LinkedList();
    }

    /**
     * Method description
     *
     *
     * @param target
     */
    public void addSerializationTarget(XmlSerializable target) {
        if (null == target) {
            throw new IllegalArgumentException("GameXmlSerializator.addSerializationTarget: target must be non-null");
        }

        this.serializableXmlObjects.add(target);
    }

    /**
     * Method description
     *
     *
     * @param target
     */
    public void removeSerializationTarget(XmlSerializable target) {
        if (null == target) {
            throw new IllegalArgumentException(
                "GameXmlSerializator.removeSerializationTarget: target must be non-null");
        }

        this.serializableXmlObjects.remove(target);
    }

    /**
     * Method description
     *
     *
     * @param target
     */
    public void addSerializationTargetExclusively(XmlSerializable target) {
        if (null == target) {
            throw new IllegalArgumentException(
                "GameXmlSerializator.addSerializationTargetExclusively: target must be non-null");
        }

        this.serializableXmlObjects.add(0, target);
    }

    /**
     * Method description
     *
     *
     * @param destination
     */
    public void save(PrintStream destination) {
        if (null == destination) {
            throw new IllegalArgumentException("destination must be non-null reference");
        }

        destination.println("<?xml version=\"1.0\" encoding=\"Cp1252\" ?>");
        destination.println("<RnRSave version=\"" + getSave_version() + "\"" + '>');

        for (XmlSerializable serializationTarget : this.serializableXmlObjects) {
            serializationTarget.saveToStreamAsSetOfXmlNodes(destination);
        }

        destination.println("</RnRSave>");
        destination.flush();
    }

    /**
     * Method description
     *
     *
     * @param source
     */
    public void load(XmlDocument source) {
        if (null == source) {
            throw new IllegalArgumentException("source must be non-null reference");
        }

        Document document = source.getContent();
        NodeList list = document.getElementsByTagName("RnRSave");

        if (1 != list.getLength()) {
            String error = "failed to load game: RnRSave node wasn't found";

            ScriptsLogger.getInstance().log(Level.SEVERE, 4, error);
            eng.err(error);

            return;
        }

        org.w3c.dom.Node root = list.item(0);
        Node rootNodeUtil = new Node(root);
        String versionLoadString = rootNodeUtil.getAttribute("version");

        if (versionLoadString != null) {
            int version = Integer.decode(versionLoadString).intValue();

            setLoad_version(version);
        } else {
            setLoad_version(-1);
        }

        XmlFilter filter = new XmlFilter(root.getChildNodes());

        for (XmlSerializable serializationTarget : this.serializableXmlObjects) {
            org.w3c.dom.Node objectSourceNode = filter.nodeNameNext(serializationTarget.getRootNodeName());

            if (null != objectSourceNode) {
                serializationTarget.loadFromNode(objectSourceNode);
            } else {
                serializationTarget.yourNodeWasNotFound();
            }

            filter.goOnStart();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public byte[] saveToByteArray() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);
        PrintStream out = new PrintStream(byteStream);

        save(out);
        out.close();

        try {
            byteStream.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);

            String error = "failed to save game to byte array: " + e.getMessage();

            eng.err(error);
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, error);
        }

        byte[] saveByteArray = byteStream.toByteArray();

        try {
            new XmlDocument(saveByteArray);
        } catch (IOException e) {
            String errorMessage = "failed to save game to byte array: " + e.getMessage();

            ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessage);
            eng.err(errorMessage);
            eng.fatal(errorMessage);
        }

        return saveByteArray;
    }

    /**
     * Method description
     *
     */
    public void saveToFile() {
        File savingDirectory = new File(this.path);

        if ((!(savingDirectory.exists())) && (!(savingDirectory.mkdir()))) {
            System.err.println("failed to creade dir: " + savingDirectory.getPath());
        }

        PrintStream out = null;

        try {
            out = new PrintStream(this.path + this.file);
            save(out);
        } catch (FileNotFoundException e) {
            String errorMessgae = "failed to save game: " + e.getMessage();

            ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessgae);
            eng.err(errorMessgae);
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * Method description
     *
     */
    public void loadFromDefaultSource() {
        if (((!(this.loadFromArray)) && (null == this.file)) || ((this.loadFromArray) && (null == this.stream))) {
            throw new IllegalStateException("source was not specified");
        }

        try {
            XmlDocument xml;

            if (this.loadFromArray) {
                xml = new XmlDocument(this.stream);
            } else {
                xml = new XmlDocument(this.path + this.file);
            }

            load(xml);
        } catch (IOException e) {
            String errorMessage = "failed to load game from file: " + e.getMessage();

            ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessage);
            eng.err(errorMessage);
            eng.fatal(errorMessage);
        }
    }

    /**
     * Method description
     *
     *
     * @param array
     */
    public void loadFromByteArray(byte[] array) {
        if (null == array) {
            throw new IllegalArgumentException("stream must be non-null reference");
        }

        try {
            XmlDocument xml = new XmlDocument(array);

            load(xml);
        } catch (IOException e) {
            String errorMessage = "failed to load game from byte array: " + e.getMessage();

            ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessage);
            eng.err(errorMessage);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getLoad_version() {
        return this.load_version;
    }

    /**
     * Method description
     *
     *
     * @param load_version
     */
    public void setLoad_version(int load_version) {
        this.load_version = load_version;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getSave_version() {
        return this.save_version;
    }

    /**
     * Method description
     *
     *
     * @param save_version
     */
    public void setSave_version(int save_version) {
        this.save_version = save_version;
    }

    /**
     * Method description
     *
     *
     * @param scenarioStage
     */
    @Override
    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        ScenarioGarbageFinder.deleteOutOfDateScenarioObjects("GameXmlSerializetor", this.serializableXmlObjects,
                scenarioStage);
    }
}


//~ Formatted in DD Std on 13/08/26
