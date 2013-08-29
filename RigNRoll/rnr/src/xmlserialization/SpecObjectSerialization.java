/*
 * @(#)SpecObjectSerialization.java   13/08/28
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

import rnr.src.rnrscr.SOPresets;
import rnr.src.rnrscr.cSpecObjects;
import rnr.src.scenarioUtils.Pair;
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
public class SpecObjectSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "specobject";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(cSpecObjects value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("name", value.name);

        Helper.addAttribute("type", value.sotip, attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);

        if (null != value.Presets()) {
            SoPresetSerialization.serializeXML(value.Presets(), stream);
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
    public static cSpecObjects deserializeXML(Node node) {
        cSpecObjects so = new cSpecObjects();
        String name = node.getAttribute("name");
        String typeString = node.getAttribute("type");

        if (null == name) {
            Log.error("SpecObjectSerialization in deserializeXML has no attribute name");
        }

        int typeValue = Helper.ConvertToIntegerAndWarn(typeString, "type",
                            "SpecObjectSerialization in deserializeXML ");

        so.name = name;
        so.sotip = typeValue;

        Node presetsNode = node.getNamedChild(SoPresetSerialization.getNodeName());

        if (null != presetsNode) {
            SOPresets presets = SoPresetSerialization.deserializeXML(presetsNode);

            so.SetPresets(presets);
        }

        return so;
    }
}


//~ Formatted in DD Std on 13/08/28
