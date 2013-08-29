/*
 * @(#)JournalElementSerialization.java   13/08/28
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


package rnr.src.xmlserialization;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.MacroBody;
import rnr.src.rnrorg.JournalActiveListener;
import rnr.src.rnrorg.journable;
import rnr.src.rnrorg.journalelement;
import rnr.src.xmlutils.Node;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class JournalElementSerialization {
    private static int version = 1;

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "journalElement";
    }

    /**
     * Method description
     *
     *
     * @param journalElement
     * @param stream
     */
    public static void serializeXML(journable journalElement, PrintStream stream) {
        Helper.openNode(stream, getNodeName(), version);

        CoreTime time = journalElement.getTime();

        if (null != time) {
            Helper.openNode(stream, "date");
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "date");
        }

        CoreTime expirationTime = journalElement.getDeactivationTime();

        if (null != expirationTime) {
            Helper.openNode(stream, "journalElement");
            CoreTimeSerialization.serializeXML(expirationTime, stream);
            Helper.closeNode(stream, "journalElement");
        }

        MacroBody macro = journalElement.getMacroBody();

        if (null != macro) {
            MacroSerialization.serializeXML(macro, stream);
        }

        List<String> answerListeners = journalElement.getListenersResources();

        for (String resourceName : answerListeners) {
            Helper.printClosedNodeWithAttributes(stream, "answerlistener",
                    Helper.createSingleAttribute("name", resourceName));
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static journable deserializeXML(Node node) {
        String versionString = node.getAttribute("version");
        int savedVersion = (versionString == null)
                           ? 0
                           : Integer.parseInt(versionString);
        CoreTime time = null;
        CoreTime expirationTime = null;
        MacroBody macroBody = null;

        if (savedVersion < version) {
            Node timeNode = node.getNamedChild(CoreTimeSerialization.getNodeName());

            if (null != timeNode) {
                time = CoreTimeSerialization.deserializeXML(timeNode);
            }
        } else {
            Node dateNode = node.getNamedChild("date");

            if (dateNode != null) {
                Node timeNode = dateNode.getNamedChild(CoreTimeSerialization.getNodeName());

                if (null != timeNode) {
                    time = CoreTimeSerialization.deserializeXML(timeNode);
                }
            }

            Node expirationDateNode = node.getNamedChild("journalElement");

            if (expirationDateNode != null) {
                Node timeNode = expirationDateNode.getNamedChild(CoreTimeSerialization.getNodeName());

                if (null != timeNode) {
                    expirationTime = CoreTimeSerialization.deserializeXML(timeNode);
                }
            }
        }

        Node body = node.getNamedChild(MacroSerialization.getNodeName());

        if (null != body) {
            macroBody = MacroSerialization.deserializeXML(body);
        } else {
            Log.error("JournalElementSerialization in deserializeXML has no node named "
                      + MacroSerialization.getNodeName());
        }

        journable jou = new journalelement(macroBody);

        if (null != time) {
            jou.setTime(time);
        }

        if (null != expirationTime) {
            jou.setDeactivationTime(expirationTime);
        }

        NodeList listenersNodes = node.getNamedChildren("answerlistener");

        if (listenersNodes != null) {
            for (Node listenerNode : listenersNodes) {
                String attributeName = listenerNode.getAttribute("name");

                if (attributeName == null) {
                    Log.error("Node answerlistener has no attribute name");
                } else {
                    jou.makeQuestionFor(new JournalActiveListener(attributeName));
                }
            }
        }

        jou.addToList();

        return jou;
    }
}


//~ Formatted in DD Std on 13/08/28
