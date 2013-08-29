/*
 * @(#)MacroSerialization.java   13/08/28
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

import rnr.src.rnrcore.MacroBody;
import rnr.src.rnrcore.MacroBodyLocString;
import rnr.src.rnrcore.MacroBodyString;
import rnr.src.rnrcore.MacroBuilder;
import rnr.src.rnrcore.Macros;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class MacroSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "macrosBody";
    }

    /**
     * Method description
     *
     *
     * @param macros
     * @param stream
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void serializeXML(MacroBody macros, PrintStream stream) {
        List attr = new ArrayList();

        if (macros instanceof MacroBodyString) {
            MacroBodyString macrosBodyString = (MacroBodyString) macros;

            Helper.addAttribute("macrosBodySimple", "", attr);
            Helper.addAttribute("value", macrosBodyString.getBody(), attr);
        } else if (macros instanceof MacroBodyLocString) {
            MacroBodyLocString macrosBodyLoc = (MacroBodyLocString) macros;

            Helper.addAttribute("macrosBodyLocref", "", attr);
            Helper.addAttribute("namespace", macrosBodyLoc.getNamespace(), attr);
            Helper.addAttribute("locref", macrosBodyLoc.getLocref(), attr);
        }

        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attr);

        List lstMacroses = macros.getMacrosList();

        if (null != lstMacroses) {
            for (Macros macro : lstMacroses) {
                Helper.printOpenNodeWithAttributes(stream, "macro",
                                                   Helper.createSingleAttribute("key", macro.getKey()));
                serializeXML(macro.getBody(), stream);
                Helper.closeNode(stream, "macro");
            }
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
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static MacroBody deserializeXML(Node node) {
        String isSimple = node.getAttribute("macrosBodySimple");
        String isLocRef = node.getAttribute("macrosBodyLocref");

        if ((isSimple == null) && (isLocRef == null)) {
            Log.error(
                "MacroSerialization on deserialize. Cannot find any of attributes macrosBodySimple, macrosBodyLocref");
        }

        List macroces = new ArrayList();
        NodeList macrosNodes = node.getNamedChildren("macro");

        if (null != macrosNodes) {
            for (Node macroNode : macrosNodes) {
                String key = macroNode.getAttribute("key");

                if (null == key) {
                    Log.error("MacroSerialization on deserialize. Cannot find attributes key in node macro");
                }

                Node macroBodyNode = macroNode.getNamedChild(getNodeName());

                if (null == macroBodyNode) {
                    Log.error("MacroSerialization on deserialize. Cannot find named node  " + getNodeName()
                              + " in node " + "macro");
                }

                MacroBody macroBody = deserializeXML(macroBodyNode);
                Macros newMacros = new Macros(key, macroBody);

                macroces.add(newMacros);
            }
        }

        MacroBody res = null;

        if (null != isSimple) {
            String valueString = node.getAttribute("value");

            res = MacroBuilder.makeMacroBody(valueString, macroces);
        }

        if (null != isLocRef) {
            String namespaceString = node.getAttribute("namespace");
            String locrefString = node.getAttribute("locref");

            res = MacroBuilder.makeMacroBody(namespaceString, locrefString, macroces);
        }

        return res;
    }
}


//~ Formatted in DD Std on 13/08/28
