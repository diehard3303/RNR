/*
 * @(#)ModelPresetsSerialization.java   13/08/28
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
public class ModelPresetsSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "somodelpreset";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(ModelManager.ModelPresets value, PrintStream stream) {
        List<Pair<String, String>> attribute = Helper.createSingleAttribute("isman", value.isIs_man());

        Helper.addAttribute("name", value.getName(), attribute);
        Helper.addAttribute("placeflag", value.getPlaceFlags(), attribute);
        Helper.addAttribute("tag", value.getTag(), attribute);
        Helper.addAttribute("weight", value.getWeigh(), attribute);
        Helper.printClosedNodeWithAttributes(stream, getNodeName(), attribute);
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static ModelManager.ModelPresets deserializeXML(Node node) {
        String model_name = node.getAttribute("name");
        String placesString = node.getAttribute("placeflag");
        String tagStrign = node.getAttribute("tag");
        String ismanString = node.getAttribute("isman");
        String weightString = node.getAttribute("weight");
        int places = ConvertToIntegerAndWarn(placesString, "placeflag");
        int new_tag = ConvertToIntegerAndWarn(tagStrign, "tag");
        boolean is_man = ConvertToBooleanAndWarn(ismanString, "isman");
        int weight = ConvertToIntegerAndWarn(weightString, "weight");
        ModelManager.ModelPresets result = new ModelManager.ModelPresets(model_name, places, new_tag, is_man);

        result.setWeigh(weight);

        return result;
    }

    private static int ConvertToIntegerAndWarn(String stringValue, String attributeName) {
        if (null == stringValue) {
            Log.error("ModelPresetsSerialization in deserializeXML cannot find attribute " + attributeName);

            return 0;
        }

        try {
            int intValue = Integer.parseInt(stringValue);

            return intValue;
        } catch (NumberFormatException e) {
            Log.error("ModelPresetsSerialization in deserializeXML cannot convert attribute " + attributeName
                      + " with value " + stringValue + " to integer.");
        }

        return 0;
    }

    private static boolean ConvertToBooleanAndWarn(String stringValue, String attributeName) {
        if (null == stringValue) {
            Log.error("ModelPresetsSerialization in deserializeXML cannot find attribute " + attributeName);

            return false;
        }

        try {
            boolean boolValue = Boolean.parseBoolean(stringValue);

            return boolValue;
        } catch (NumberFormatException e) {
            Log.error("ModelPresetsSerialization in deserializeXML cannot convert attribute " + attributeName
                      + " with value " + stringValue + " to boolean.");
        }

        return false;
    }
}


//~ Formatted in DD Std on 13/08/28
