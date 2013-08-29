/*
 * @(#)XmlDocument.java   13/08/27
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


package rnr.src.scenarioXml;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;

import rnr.src.auxil.DInputStream2;
import rnr.src.auxil.XInputStreamCreate;
import rnr.src.rnrloggers.ScenarioLogger;

//~--- JDK imports ------------------------------------------------------------

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public class XmlDocument {
    private Document document;
    private boolean schemaValidationPassed;

    /**
     * Constructs ...
     *
     *
     * @param dataArray
     *
     * @throws IOException
     */
    public XmlDocument(byte[] dataArray) throws IOException {
        this.schemaValidationPassed = true;

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            this.document = documentBuilder.parse(new DInputStream2(new ByteArrayInputStream(dataArray)));
        } catch (ParserConfigurationException exception) {
            processException("Failed to parse document from byte array", exception);
        } catch (IOException exception) {
            processException("IO error while loading dovument from byte array", exception);
        } catch (SAXException exception) {
            processException("SAXException while parsing document from byte array", exception);
        }
    }

    /**
     * Constructs ...
     *
     *
     * @param path
     *
     * @throws IOException
     */
    public XmlDocument(String path) throws IOException {
        this(path, null);
    }

    /**
     * Constructs ...
     *
     *
     * @param path
     * @param schemaPath
     *
     * @throws IOException
     */
    public XmlDocument(String path, String schemaPath) throws IOException {
        this.schemaValidationPassed = true;

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            this.document = documentBuilder.parse(new DInputStream2(XInputStreamCreate.open(path)));

            if ((null != schemaPath) && (0 < schemaPath.length())) {
                SchemaFactory schemaBuilder = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
                Source schemaSource = new StreamSource(new File(schemaPath));
                Schema xmlSchema = schemaBuilder.newSchema(schemaSource);
                Validator xmlChecker = xmlSchema.newValidator();

                try {
                    xmlChecker.validate(new DOMSource(this.document));
                } catch (SAXException e) {
                    ScenarioLogger.getInstance().parserLog(Level.SEVERE,
                            "Loaded XML file is invalid: " + e.getLocalizedMessage());
                    this.schemaValidationPassed = false;
                }
            }
        } catch (ParserConfigurationException exception) {
            processException("Failed to parse document " + path, exception);
        } catch (IOException exception) {
            processException("Failed to open document " + path, exception);
        } catch (SAXException exception) {
            processException("SAXException while parsing document " + path, exception);
        }
    }

    private void processException(String text, Exception exception) throws IOException {
        ScenarioLogger.getInstance().parserLog(Level.SEVERE, text);
        ScenarioLogger.getInstance().parserLog(Level.SEVERE, exception.getMessage());

        throw new IOException(exception.getMessage());
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Document getContent() {
        return this.document;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean validationPassed() {
        return this.schemaValidationPassed;
    }

    static Properties extractAttribs(NamedNodeMap map) {
        if ((null == map) || (0 >= map.getLength())) {
            return null;
        }

        Properties out = new Properties();

        for (int i = 0; i < map.getLength(); ++i) {
            out.put(map.item(i).getNodeName(), map.item(i).getTextContent());
        }

        return out;
    }

    static String extractMainAttribAndParams(NamedNodeMap map, Properties params) {
        if (null == params) {
            throw new IllegalArgumentException("params must be non-null reference");
        }

        if ((null == map) || (0 >= map.getLength())) {
            return "unknown";
        }

        int actMainAttrIndex = getIndexOfAttributeWithoutContent(map);
        String mainAttribute;

        if (-1 != actMainAttrIndex) {
            mainAttribute = map.item(actMainAttrIndex).getNodeName();
        } else {
            mainAttribute = "unknown";
        }

        Properties extraxted = extractAttribs(map);

        if ((null != extraxted) && (0 < extraxted.size())) {
            extraxted.remove(mainAttribute);
            params.putAll(extraxted);
        }

        return mainAttribute;
    }

    static String getAttributeValue(NamedNodeMap attributes, String name, String defaultValue) {
        Node value = attributes.getNamedItem(name);

        if (null != value) {
            return value.getTextContent();
        }

        return defaultValue;
    }

    static int getIndexOfAttributeWithoutContent(NamedNodeMap map) {
        for (int i = map.getLength() - 1; 0 <= i; --i) {
            if ((null == map.item(i).getTextContent()) || (0 >= map.item(i).getTextContent().length())) {
                return i;
            }
        }

        return -1;
    }

    static String getSingleChildNodeAttribute(Node rootNode, String childNodeName, String attributeName) {
        Node found = new XmlFilter(rootNode.getChildNodes()).nodeNameNext(childNodeName);

        if (null != found) {
            return getAttributeValue(found.getAttributes(), attributeName, null);
        }

        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Collection<Node> extractTags(String rootNode, String tag) throws IOException {
        if (null == rootNode) {
            throw new IllegalArgumentException("rootNode must be non-null reference");
        }

        if (null == tag) {
            throw new IllegalArgumentException("tag must be non-null reference");
        }

        Document document = getContent();

        if (null == document) {
            throw new IOException("document wasn't loaded");
        }

        Collection<Node> out = new LinkedList<Node>();
        XmlFilter filter = new XmlFilter(document.getElementsByTagName(rootNode));
        Node xmlNode = filter.nextElement();

        while (null != xmlNode) {
            XmlFilter messagesFilter = new XmlFilter(xmlNode.getChildNodes());
            Node messageTextNode = messagesFilter.nodeNameNext(tag);

            while (null != messageTextNode) {
                out.add(messageTextNode);
                messageTextNode = messagesFilter.nodeNameNext(tag);
            }

            xmlNode = filter.nextElement();
        }

        return out;
    }

    /**
     * Method description
     *
     *
     * @param rootNode
     * @param tagName
     *
     * @return
     *
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection<String> extractTagsTextContent(String rootNode, String tagName) throws IOException {
        if (null == rootNode) {
            throw new IllegalArgumentException("rootNode must be non-null reference");
        }

        if (null == tagName) {
            throw new IllegalArgumentException("tagName must be non-null reference");
        }

        Collection<Node> nodeList = extractTags(rootNode, tagName);
        Collection<String> out = new LinkedList<String>();

        for (Node tag : nodeList) {
            out.add(tag.getTextContent());
        }

        return out;
    }

    /**
     * Method description
     *
     *
     * @param rootNode
     * @param tagName
     *
     * @return
     *
     * @throws IOException
     */
    public Collection<Properties> extractPropertiesFromAttributes(String rootNode, String tagName) throws IOException {
        if (null == rootNode) {
            throw new IllegalArgumentException("rootNode must be non-null reference");
        }

        if (null == tagName) {
            throw new IllegalArgumentException("tagName must be non-null reference");
        }

        Collection<Node> nodeList = extractTags(rootNode, tagName);
        Collection<Properties> out = new LinkedList<Properties>();

        for (Node tag : nodeList) {
            Properties extracted = extractAttribs(tag.getAttributes());

            if ((null != extracted) && (0 < extracted.size())) {
                out.add(extracted);
            }
        }

        return out;
    }
}


//~ Formatted in DD Std on 13/08/27
