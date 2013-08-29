/*
 * @(#)RedRockCanyonSerializator.java   13/08/28
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

import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.RedRockCanyon;
import rnr.src.rnrscenario.controllers.ScenarioHost;
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
@ScenarioClass(
    scenarioStage = 13,
    fieldWithDesiredStage = ""
)
public class RedRockCanyonSerializator implements IXMLSerializable {
    private static final String COCH_CALLED_ATTRIBUTE = "cochCalled";
    private static final String MATHEW_CALLED_ATTRIBUTE = "mathewCalled";
    private RedRockCanyon m_object = null;
    private ScenarioHost host;

    /**
     * Constructs ...
     *
     *
     * @param value
     */
    public RedRockCanyonSerializator(RedRockCanyon value) {
        this.m_object = value;
    }

    /**
     * Constructs ...
     *
     *
     * @param scenarioHost
     */
    public RedRockCanyonSerializator(ScenarioHost scenarioHost) {
        this.m_object = null;
        this.host = scenarioHost;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "redRockCanyon";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(RedRockCanyon value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("cochCalled", value.isCochCalled());

        Helper.addAttribute("mathewCalled", value.isMathewCalled(), attributes);
        Helper.printClosedNodeWithAttributes(stream, getNodeName(), attributes);
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param host
     */
    public static void deserializeXML(Node node, ScenarioHost host) {
        String cochCalledString = node.getAttribute("cochCalled");
        String mathewCalledString = node.getAttribute("mathewCalled");
        boolean cochCalled = Helper.ConvertToBooleanAndWarn(cochCalledString, "cochCalled",
                                 "RedRockCanyonSerializator in deserializeXML");
        boolean mathewCalled = Helper.ConvertToBooleanAndWarn(mathewCalledString, "mathewCalled",
                                   "RedRockCanyonSerializator in deserializeXML");
        RedRockCanyon result = new RedRockCanyon(host, mathewCalled);

        result.setCochCalled(cochCalled);
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void deSerialize(Node node) {
        deserializeXML(node, this.host);
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    public void serialize(PrintStream stream) {
        serializeXML(this.m_object, stream);
    }
}


//~ Formatted in DD Std on 13/08/28
