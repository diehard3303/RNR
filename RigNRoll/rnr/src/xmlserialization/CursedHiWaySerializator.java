/*
 * @(#)CursedHiWaySerializator.java   13/08/28
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
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.CursedHiWay;
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
public class CursedHiWaySerializator implements IXMLSerializable {
    CursedHiWay m_object = null;

    /**
     * Constructs ...
     *
     */
    public CursedHiWaySerializator() {
        this.m_object = null;
    }

    /**
     * Constructs ...
     *
     *
     * @param value
     */
    public CursedHiWaySerializator(CursedHiWay value) {
        this.m_object = value;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "cursedhiway";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(CursedHiWay value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("dead_from_mist",
                                                    value.isDead_from_mist());

        Helper.addAttribute("last_coef", value.getLast_coef(), attributes);
        Helper.addAttribute("on_animation", value.isOn_animation(), attributes);
        Helper.addAttribute("release_animation", value.isRelease_animation(), attributes);
        Helper.addAttribute("stop_animation", value.isStop_animation(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);

        vectorJ posInfluence = value.getPositionInfluenceZone();

        if (null != posInfluence) {
            Helper.openNode(stream, "positionInfluenceZone");
            Vector3dSerializator.serializeXML(posInfluence, stream);
            Helper.closeNode(stream, "positionInfluenceZone");
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    public static void deserializeXML(Node node) {
        String errorMessage = "CursedHiWaySerializator in deserializeXML ";
        String deadFromMistString = node.getAttribute("dead_from_mist");
        String lastCoefString = node.getAttribute("last_coef");
        String onAnimationString = node.getAttribute("on_animation");
        String releaseAnimationString = node.getAttribute("release_animation");
        String stopAnimationString = node.getAttribute("stop_animation");
        boolean deadFromMistValue = Helper.ConvertToBooleanAndWarn(deadFromMistString, "dead_from_mist", errorMessage);
        double lastCoefValue = Helper.ConvertToDoubleAndWarn(lastCoefString, "last_coef", errorMessage);
        boolean onAnimationValue = Helper.ConvertToBooleanAndWarn(onAnimationString, "on_animation", errorMessage);
        boolean releaseAnimationValue = Helper.ConvertToBooleanAndWarn(releaseAnimationString, "release_animation",
                                            errorMessage);
        boolean startAnimationValue = Helper.ConvertToBooleanAndWarn(stopAnimationString, "stop_animation",
                                          errorMessage);
        Node positionInfluenceNode = node.getNamedChild("positionInfluenceZone");
        vectorJ posInfluence = null;

        if (null != positionInfluenceNode) {
            Node vectorNode = positionInfluenceNode.getNamedChild(Vector3dSerializator.getNodeName());

            if (null == vectorNode) {
                Log.error(errorMessage + " has no child node named " + Vector3dSerializator.getNodeName()
                          + " in node named " + "positionInfluenceZone");
            } else {
                posInfluence = Vector3dSerializator.deserializeXML(vectorNode);
            }
        }

        CursedHiWay result = new CursedHiWay();

        result.setDead_from_mist(deadFromMistValue);
        result.setLast_coef(lastCoefValue);
        result.setOn_animation(onAnimationValue);
        result.setPositionInfluenceZone(posInfluence);
        result.setRelease_animation(releaseAnimationValue);
        result.setStop_animation(startAnimationValue);
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void deSerialize(Node node) {
        deserializeXML(node);
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
