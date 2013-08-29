/*
 * @(#)CrewSerialization.java   13/08/28
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

import rnr.src.players.CarAnimationController;
import rnr.src.players.Crew;
import rnr.src.players.LiveCarCreationController;
import rnr.src.players.MappingCars;
import rnr.src.players.ScenarioCarCreationController;
import rnr.src.xmlutils.Node;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class CrewSerialization {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "crew";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(Crew value, PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        ScenarioCarCreationController controller = value.getCarCreationController();

        CarAnimationControllerSerialization.serializeXML(controller, stream);

        MappingCars mappedCars = value.getMappingCars();

        MappedCarsSerialization.serializeXML(mappedCars, stream);
        controller = value.getScenarioCarCreationController();
        ScenarioCarCreationControllerSerialization.serializeXML(controller, stream);

        LiveCarCreationController controller1 = value.getLiveCarCreationController();

        LiveCarCreationControllerSerialization.serializeXML(controller1, stream);
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
    public static Crew deserializeXML(Node node) {
        Crew result = new Crew();
        Node carAnimationController = node.getNamedChild(CarAnimationControllerSerialization.getNodeName());

        if (null != carAnimationController) {
            result.setCarCreationController(CarAnimationControllerSerialization.deserializeXML(carAnimationController));
        }

        Node scenarioCarCreationController =
            node.getNamedChild(ScenarioCarCreationControllerSerialization.getNodeName());

        if (null != scenarioCarCreationController) {
            result.setScenarioCarCreationController(
                ScenarioCarCreationControllerSerialization.deserializeXML(scenarioCarCreationController));
        }

        Node liveCarCreationController = node.getNamedChild(LiveCarCreationControllerSerialization.getNodeName());

        if (null != liveCarCreationController) {
            result.setLiveCarCreationController(
                LiveCarCreationControllerSerialization.deserializeXML(liveCarCreationController));
        }

        Node mappedCars = node.getNamedChild(MappedCarsSerialization.getNodeName());

        if (null != mappedCars) {
            result.setMappingCars(MappedCarsSerialization.deserializeXML(mappedCars));
        }

        return result;
    }
}


//~ Formatted in DD Std on 13/08/28
