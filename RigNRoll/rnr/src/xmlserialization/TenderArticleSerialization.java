/*
 * @(#)TenderArticleSerialization.java   13/08/28
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
import rnr.src.rnrscr.smi.Newspapers;
import rnr.src.rnrscr.smi.TenderInformation;
import rnr.src.rnrscr.smi.TenderInformation.FeeMultiplier;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.List;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class TenderArticleSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "newspaper_tender";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void serializeXML(TenderInformation value, PrintStream stream) {
        TenderInformation.FeeMultiplier multiplier = value.getMulpiplier();
        int multiplierValue = 1;

        if (multiplier == TenderInformation.FeeMultiplier.DOUBLE) {
            multiplierValue = 2;
        } else if (multiplier == TenderInformation.FeeMultiplier.QUADRUPLE) {
            multiplierValue = 4;
        } else if (multiplier == TenderInformation.FeeMultiplier.TRIPLE) {
            multiplierValue = 3;
        }

        List attributes = Helper.createSingleAttribute("day", value.getTimeEnd().gDate());

        Helper.addAttribute("destination", value.getDestinationWarehouse(), attributes);
        Helper.addAttribute("hour", value.getTimeEnd().gHour(), attributes);
        Helper.addAttribute("month", value.getTimeEnd().gMonth(), attributes);
        Helper.addAttribute("multiplier", multiplierValue, attributes);
        Helper.addAttribute("year", value.getTimeEnd().gYear(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);

        List baseNames = value.getWarehouses();

        if ((null == baseNames) || (baseNames.isEmpty())) {
            Log.error("TenderArticleSerialization in serializeXML has empty Warehouses.");
        } else {
            Helper.openNode(stream, "bases");

            for (String baseName : baseNames) {
                SimpleTypeSerializator.serializeXMLString(baseName, stream);
            }

            Helper.closeNode(stream, "bases");
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void deserializeXML(Node node) {
        String errorrMessage = "TenderArticleSerialization on deserializeXML ";
        String dayString = node.getAttribute("day");
        String destinationString = node.getAttribute("destination");
        String hourString = node.getAttribute("hour");
        String monthString = node.getAttribute("month");
        String multiplierString = node.getAttribute("multiplier");
        String yearString = node.getAttribute("year");
        int year = Helper.ConvertToIntegerAndWarn(yearString, "year", errorrMessage);
        int month = Helper.ConvertToIntegerAndWarn(monthString, "month", errorrMessage);
        int day = Helper.ConvertToIntegerAndWarn(dayString, "day", errorrMessage);
        int hour = Helper.ConvertToIntegerAndWarn(hourString, "hour", errorrMessage);
        int multiplier = Helper.ConvertToIntegerAndWarn(multiplierString, "multiplier", errorrMessage);
        Vector bases = new Vector();
        Node baseNamesNode = node.getNamedChild("bases");

        if (null == baseNamesNode) {
            Log.error(errorrMessage + " has no node named " + "bases");
        } else {
            NodeList listBaseNames = baseNamesNode.getNamedChildren(SimpleTypeSerializator.getNodeNameString());

            if ((null == listBaseNames) || (listBaseNames.isEmpty())) {
                Log.error(errorrMessage + " node named " + "bases" + " has no children named "
                          + SimpleTypeSerializator.getNodeNameString());
            } else {
                for (Node stringNode : listBaseNames) {
                    String baseName = SimpleTypeSerializator.deserializeXMLString(stringNode);

                    bases.add(baseName);
                }
            }
        }

        Newspapers.addTenderInformation(destinationString, bases, multiplier, year, month, day, hour);
    }
}


//~ Formatted in DD Std on 13/08/28
