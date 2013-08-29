/*
 * @(#)BigRaceSerialization.java   13/08/28
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
import rnr.src.rnrcore.XmlSerializable;
import rnr.src.rnrrating.BigRace;
import rnr.src.scenarioUtils.Pair;

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
public class BigRaceSerialization implements XmlSerializable {
    private static BigRaceSerialization instance = new BigRaceSerialization();

    /**
     * Method description
     *
     *
     * @return
     */
    public static BigRaceSerialization getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getRootNodeName() {
        return getNodeName();
    }

    /**
     * Method description
     *
     *
     * @param nodeDom
     */
    @Override
    public void loadFromNode(org.w3c.dom.Node nodeDom) {
        deserializeXML(new rnr.src.xmlutils.Node(nodeDom));
    }

    /**
     * Method description
     *
     */
    @Override
    public void yourNodeWasNotFound() {}

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        serializeXML(BigRace.gReference(), stream);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "bigrace";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(BigRace value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("firsttime",
                                                    value.isFirst_time_scheduled_race());

        Helper.addAttribute("monstercuppassed", value.isMostercup_processed(), attributes);
        Helper.addAttribute("seriehigh", value.getSeries_high(), attributes);
        Helper.addAttribute("serieslow", value.getSeries_low(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);

        CoreTime lastTimeScheduled = value.getLasttimesceduled();

        if (null != lastTimeScheduled) {
            Helper.openNode(stream, "lasttimescheduled");
            CoreTimeSerialization.serializeXML(lastTimeScheduled, stream);
            Helper.closeNode(stream, "lasttimescheduled");
        }

        CoreTime nextRace = value.getNext_race();

        if (null != nextRace) {
            Helper.openNode(stream, "nextrace");
            CoreTimeSerialization.serializeXML(nextRace, stream);
            Helper.closeNode(stream, "nextrace");
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    public static void deserializeXML(rnr.src.xmlutils.Node node) {
        String firstTimeString = node.getAttribute("firsttime");
        String mostercuppassedString = node.getAttribute("monstercuppassed");
        String serieshighString = node.getAttribute("seriehigh");
        String serieslowString = node.getAttribute("serieslow");
        int seriesHigh = ConvertToIntegerAndWarn(serieshighString, "seriehigh");
        int seriesLow = ConvertToIntegerAndWarn(serieslowString, "serieslow");
        boolean firsttimeValue = ConvertToBooleanAndWarn(firstTimeString, "firsttime");
        boolean monstercuppassedValue = ConvertToBooleanAndWarn(mostercuppassedString, "monstercuppassed");

        BigRace.gReference().setFirst_time_scheduled_race(firsttimeValue);
        BigRace.gReference().setMostercup_processed(monstercuppassedValue);
        BigRace.gReference().setSeries_high(seriesHigh);
        BigRace.gReference().setSeries_low(seriesLow);

        rnr.src.xmlutils.Node lastTimeNode = node.getNamedChild("lasttimescheduled");
        rnr.src.xmlutils.Node nextRaceNode = node.getNamedChild("nextrace");

        if (null == lastTimeNode) {
            return;
        }

        rnr.src.xmlutils.Node nodeTime = lastTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());

        if (null == nodeTime) {
            return;
        }

        CoreTime lastTimeValue = CoreTimeSerialization.deserializeXML(nodeTime);

        BigRace.gReference().setLasttimesceduled(lastTimeValue);

        if (null == nextRaceNode) {
            return;
        }

        CoreTime nextRaceValue = CoreTimeSerialization.deserializeXML(nodeTime);

        BigRace.gReference().setNext_race(nextRaceValue);
    }

    private static int ConvertToIntegerAndWarn(String stringValue, String attributeName) {
        if (null == stringValue) {
            Log.error("BigRaceSerialization in deserializeXML cannot find attribute " + attributeName);

            return 0;
        }

        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            Log.error("BigRaceSerialization in deserializeXML cannot convert attribute " + attributeName
                      + " with value " + stringValue + " to integer.");
        }

        return 0;
    }

    private static boolean ConvertToBooleanAndWarn(String stringValue, String attributeName) {
        if (null == stringValue) {
            Log.error("BigRaceSerialization in deserializeXML cannot find attribute " + attributeName);

            return false;
        }

        try {
            return Boolean.parseBoolean(stringValue);
        } catch (NumberFormatException e) {
            Log.error("BigRaceSerialization in deserializeXML cannot convert attribute " + attributeName
                      + " with value " + stringValue + " to boolean.");
        }

        return false;
    }
}


//~ Formatted in DD Std on 13/08/28
