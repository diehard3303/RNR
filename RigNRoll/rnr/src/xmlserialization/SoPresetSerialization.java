/*
 * @(#)SoPresetSerialization.java   13/08/28
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

import rnr.src.rnrscr.ModelManager;
import rnr.src.rnrscr.ModelManager.ModelPresets;
import rnr.src.rnrscr.Presets;
import rnr.src.rnrscr.SOPresets;
import rnr.src.rnrscr.STOHistory;
import rnr.src.rnrscr.STOpresets;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class SoPresetSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "presets";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(SOPresets value, PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        ArrayList<ModelPresets> models = value.getModels();

        if (null != models) {
            Helper.openNode(stream, "presetsmodels");

            for (ModelManager.ModelPresets preset : models) {
                ModelPresetsSerialization.serializeXML(preset, stream);
            }

            Helper.closeNode(stream, "presetsmodels");
        }

        if (value instanceof STOpresets) {
            STOpresets stopresets = (STOpresets) value;

            Helper.printOpenNodeWithAttributes(stream, "stopresets",
                                               Helper.createSingleAttribute("money", stopresets.getMoney()));

            STOHistory history = stopresets.getHistorycomming();

            if (null != history) {
                StoHistorySerialization.serializeXML(history, stream);
            }

            Helper.closeNode(stream, "stopresets");
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
    public static SOPresets deserializeXML(Node node) {
        Presets result = null;
        ArrayList modelPresets = new ArrayList();
        Node models = node.getNamedChild("presetsmodels");

        if (null != models) {
            NodeList allModelPresets = models.getNamedChildren(ModelPresetsSerialization.getNodeName());

            for (Node singleModelPreset : allModelPresets) {
                ModelManager.ModelPresets model_preset = ModelPresetsSerialization.deserializeXML(singleModelPreset);

                modelPresets.add(model_preset);
            }
        }

        Node stoPresetNode = node.getNamedChild("stopresets");

        if (null != stoPresetNode) {
            STOpresets stopresets = new STOpresets();

            result = stopresets;

            String moneyString = stoPresetNode.getAttribute("money");
            int moneyValue = Helper.ConvertToIntegerAndWarn(moneyString, "money",
                                 "SoPresetSerialization in deserializeXML ");

            stopresets.setMoney(moneyValue);

            Node historyNode = stoPresetNode.getNamedChild(StoHistorySerialization.getNodeName());

            if (null != historyNode) {
                STOHistory history = StoHistorySerialization.deserializeXML(historyNode);

                stopresets.setHistorycomming(history);
            }
        } else {
            result = new Presets();
        }

        result.setModels(modelPresets);

        return result;
    }
}


//~ Formatted in DD Std on 13/08/28
