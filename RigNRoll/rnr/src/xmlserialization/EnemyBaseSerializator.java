/*
 * @(#)EnemyBaseSerializator.java   13/08/28
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

import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrscenario.configurators.EnemyBaseConfig;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.controllers.EnemyBase;
import rnr.src.rnrscenario.controllers.EnemyBase.Timing_BadCall;
import rnr.src.rnrscenario.controllers.EnemyBase.Timing_DakotaCall;
import rnr.src.rnrscenario.controllers.EnemyBase.Timing_MonicaFindsOut;
import rnr.src.rnrscenario.controllers.EnemyBase.Timing_ThreatenCall;
import rnr.src.rnrscenario.controllers.ScenarioHost;
import rnr.src.scenarioUtils.Pair;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

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
    scenarioStage = 14,
    fieldWithDesiredStage = ""
)
public class EnemyBaseSerializator implements IXMLSerializable {
    private EnemyBase m_object = null;
    private EnemyBaseConfig m_objectConfig;
    private ScenarioHost host;

    /**
     * Constructs ...
     *
     *
     * @param value
     */
    public EnemyBaseSerializator(EnemyBase value) {
        this.m_object = value;
    }

    /**
     * Constructs ...
     *
     *
     * @param config
     * @param host
     */
    public EnemyBaseSerializator(EnemyBaseConfig config, ScenarioHost host) {
        this.m_objectConfig = config;
        this.host = host;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "enemybase";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(EnemyBase value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("isAssaultCycleSceneCreated",
                                                    value.isAssaultCycleSceneCreated());

        Helper.addAttribute("wasVehicleExchange", value.isWasVehicleExchange(), attributes);
        Helper.addAttribute("bad_conditions", value.isBad_conditions(), attributes);
        Helper.addAttribute("assault_started", value.isAssault_started(), attributes);
        Helper.addAttribute("assault_start_time", value.getAssault_start_time(), attributes);
        Helper.addAttribute("want_dakota_warning", value.isWant_dakota_warning(), attributes);
        Helper.addAttribute("dakota_warning_start_time", value.getDakota_warning_start_time(), attributes);
        Helper.addAttribute("want_dakota_warning_started", value.isWant_dakota_warning_started(), attributes);
        Helper.addAttribute("to_slow_down_gepard", value.isTo_slow_down_gepard(), attributes);
        Helper.addAttribute("slowdown_start", value.isSlowdown_start(), attributes);
        Helper.addAttribute("initialVelocity", value.getInitialVelocity(), attributes);
        Helper.addAttribute("SLOW_DOWN_ACCEL", value.getSlowDownAcceleration(), attributes);
        Helper.addAttribute("to_track_tunnel", value.isTo_track_tunnel(), attributes);
        Helper.addAttribute("was_near_base", value.isWas_near_base(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);
        Helper.openNode(stream, "dwords");
        SimpleTypeSerializator.serializeXMLStringList(value.getCurrentDwords(), stream);
        Helper.closeNode(stream, "dwords");

        actorveh[] cars = value.getCars();

        if ((null != cars) && (cars.length != 0)) {
            Helper.openNode(stream, "cars");

            for (actorveh car : cars) {
                ActorVehSerializator.serializeXML(car, stream);
            }

            Helper.closeNode(stream, "cars");
        }

        actorveh[] assaultCars = value.getCars_assault();

        if ((null != assaultCars) && (assaultCars.length != 0)) {
            Helper.openNode(stream, "cars_assault");

            for (actorveh car : assaultCars) {
                ActorVehSerializator.serializeXML(car, stream);
            }

            Helper.closeNode(stream, "cars_assault");
        }

        aiplayer dakota = value.getDakota();

        if (null != dakota) {
            Helper.openNode(stream, "dakota");
            AIPlayerSerializator.serializeXML(dakota, stream);
            Helper.closeNode(stream, "dakota");
        }

        aiplayer monica = value.getMonica();

        if (null != monica) {
            Helper.openNode(stream, "monica");
            AIPlayerSerializator.serializeXML(monica, stream);
            Helper.closeNode(stream, "monica");
        }

        EnemyBase.Timing_BadCall timing = value.getTo_make_2020_call();

        if (null != timing) {
            Helper.openNode(stream, "to_make_2020_call");

            CoreTime time = timing.getInterruptTime();

            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "to_make_2020_call");
        }

        EnemyBase.Timing_DakotaCall timing = value.getTo_make_dakota_call();

        if (null != timing) {
            Helper.openNode(stream, "to_make_dakota_call");

            CoreTime time = timing.getInterruptTime();

            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "to_make_dakota_call");
        }

        EnemyBase.Timing_MonicaFindsOut timing = value.getTo_make_discover_base();

        if (null != timing) {
            Helper.openNode(stream, "to_make_discover_base");

            CoreTime time = timing.getInterruptTime();

            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "to_make_discover_base");
        }

        EnemyBase.Timing_ThreatenCall timing = value.getTo_make_threat_call();

        if (null != timing) {
            Helper.openNode(stream, "to_make_threat_call");

            CoreTime time = timing.getFinishTime();

            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "to_make_threat_call");
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param config
     * @param host
     */
    @SuppressWarnings("rawtypes")
    public static void deserializeXML(Node node, EnemyBaseConfig config, ScenarioHost host) {
        if (null != EnemyBase.getInstance()) {
            Log.error("Illegal state: EnemyBase already exists, maybe save before fixed #19463?");

            return;
        }

        String errorMessage = "EnemyBaseSerializator in deserializeXML ";
        String assault_start_timeString = node.getAttribute("assault_start_time");
        String assault_startedString = node.getAttribute("assault_started");
        String bad_conditionsString = node.getAttribute("bad_conditions");
        String dakota_warning_start_timeString = node.getAttribute("dakota_warning_start_time");
        String initialVelocityString = node.getAttribute("initialVelocity");
        String isAssaultCycleSceneCreatedString = node.getAttribute("isAssaultCycleSceneCreated");
        String SLOW_DOWN_ACCELString = node.getAttribute("SLOW_DOWN_ACCEL");
        String slowdown_startString = node.getAttribute("slowdown_start");
        String to_slow_down_gepardString = node.getAttribute("to_slow_down_gepard");
        String to_track_tunnelString = node.getAttribute("to_track_tunnel");
        String want_dakota_warningString = node.getAttribute("want_dakota_warning");
        String want_dakota_warning_startedString = node.getAttribute("want_dakota_warning_started");
        String was_near_baseString = node.getAttribute("was_near_base");
        String wasVehicleExchangeString = node.getAttribute("wasVehicleExchange");
        double assault_start_timeValue = Helper.ConvertToDoubleAndWarn(assault_start_timeString, "assault_start_time",
                                             errorMessage);
        boolean assault_startedValue = Helper.ConvertToBooleanAndWarn(assault_startedString, "assault_started",
                                           errorMessage);
        boolean bad_conditionsValue = Helper.ConvertToBooleanAndWarn(bad_conditionsString, "bad_conditions",
                                          errorMessage);
        double dakota_warning_start_timeValue = Helper.ConvertToDoubleAndWarn(dakota_warning_start_timeString,
                                                    "dakota_warning_start_time", errorMessage);
        double initialVelocityValue = Helper.ConvertToDoubleAndWarn(initialVelocityString, "initialVelocity",
                                          errorMessage);
        boolean isAssaultCycleSceneCreatedValue = Helper.ConvertToBooleanAndWarn(isAssaultCycleSceneCreatedString,
                                                      "isAssaultCycleSceneCreated", errorMessage);
        double SLOW_DOWN_ACCELValue = Helper.ConvertToDoubleAndWarn(SLOW_DOWN_ACCELString, "SLOW_DOWN_ACCEL",
                                          errorMessage);
        boolean slowdown_startValue = Helper.ConvertToBooleanAndWarn(slowdown_startString, "slowdown_start",
                                          errorMessage);
        boolean to_slow_down_gepardValue = Helper.ConvertToBooleanAndWarn(to_slow_down_gepardString,
                                               "to_slow_down_gepard", errorMessage);
        boolean to_track_tunnelValue = Helper.ConvertToBooleanAndWarn(to_track_tunnelString, "to_track_tunnel",
                                           errorMessage);
        boolean want_dakota_warningValue = Helper.ConvertToBooleanAndWarn(want_dakota_warningString,
                                               "want_dakota_warning", errorMessage);
        boolean want_dakota_warning_startedValue = Helper.ConvertToBooleanAndWarn(want_dakota_warning_startedString,
                                                       "want_dakota_warning_started", errorMessage);
        boolean was_near_baseValue = Helper.ConvertToBooleanAndWarn(was_near_baseString, "was_near_base", errorMessage);
        boolean wasVehicleExchangeValue = Helper.ConvertToBooleanAndWarn(wasVehicleExchangeString,
                                              "wasVehicleExchange", errorMessage);
        actorveh[] cars = null;
        actorveh[] carsAssault = null;
        aiplayer dakota = null;
        aiplayer monica = null;
        CoreTime discoverBaseTime = null;
        CoreTime dakotaCallTime = null;
        CoreTime threatCallTime = null;
        CoreTime make2020CallTime = null;
        Node carsNode = node.getNamedChild("cars");
        Node carsAssaultNode = node.getNamedChild("cars_assault");
        Node dakotaNode = node.getNamedChild("dakota");
        Node monicaNode = node.getNamedChild("monica");
        Node discoverBaseTimeNode = node.getNamedChild("to_make_discover_base");
        Node dakotaCallTimeNode = node.getNamedChild("to_make_dakota_call");
        Node threatCallTimeNode = node.getNamedChild("to_make_threat_call");
        Node make2020CallTimeNode = node.getNamedChild("to_make_2020_call");
        Node dwords = node.getNamedChild("dwords");
        EnemyBase result = new EnemyBase(config, host);

        if (null != dwords) {
            Node stringList = dwords.getNamedChild("string_list");

            if (null != stringList) {
                List activeDwords = SimpleTypeSerializator.deserializeXMLStringList(stringList);

                for (String activeDword : activeDwords) {
                    result.activateDword(activeDword);
                }
            }
        }

        int size;

        if (null != carsNode) {
            NodeList listCars = carsNode.getNamedChildren(ActorVehSerializator.getNodeName());

            if ((null == listCars) || (listCars.isEmpty())) {
                Log.error(errorMessage + " node with name " + "cars" + " has no child nodes with name "
                          + ActorVehSerializator.getNodeName());
            } else {
                cars = new actorveh[listCars.size()];
                size = 0;

                for (Node carNode : listCars) {
                    actorveh car = ActorVehSerializator.deserializeXML(carNode);

                    cars[(size++)] = car;
                }
            }
        }

        if (null != carsAssaultNode) {
            NodeList listCars = carsAssaultNode.getNamedChildren(ActorVehSerializator.getNodeName());

            if ((listCars == null) || (listCars.isEmpty())) {
                Log.error(errorMessage + " node with name " + "cars_assault" + " has no child nodes with name "
                          + ActorVehSerializator.getNodeName());
            } else {
                carsAssault = new actorveh[listCars.size()];
                size = 0;

                for (Node carNode : listCars) {
                    actorveh car = ActorVehSerializator.deserializeXML(carNode);

                    carsAssault[(size++)] = car;
                }
            }
        }

        if (null != dakotaNode) {
            Node playerNode = dakotaNode.getNamedChild(AIPlayerSerializator.getNodeName());

            if (null == playerNode) {
                Log.error(errorMessage + " node with name " + "dakota" + " has no child nodes with name "
                          + AIPlayerSerializator.getNodeName());
            } else {
                dakota = AIPlayerSerializator.deserializeXML(playerNode);
            }
        }

        if (null != monicaNode) {
            Node playerNode = monicaNode.getNamedChild(AIPlayerSerializator.getNodeName());

            if (null == playerNode) {
                Log.error(errorMessage + " node with name " + "monica" + " has no child nodes with name "
                          + AIPlayerSerializator.getNodeName());
            } else {
                monica = AIPlayerSerializator.deserializeXML(playerNode);
            }
        }

        if (null != discoverBaseTimeNode) {
            Node timeNode = discoverBaseTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());

            if (null == timeNode) {
                Log.error(errorMessage + " node with name " + "to_make_discover_base"
                          + " has no child nodes with name " + CoreTimeSerialization.getNodeName());
            } else {
                discoverBaseTime = CoreTimeSerialization.deserializeXML(timeNode);
            }
        }

        if (null != dakotaCallTimeNode) {
            Node timeNode = dakotaCallTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());

            if (null == timeNode) {
                Log.error(errorMessage + " node with name " + "to_make_dakota_call" + " has no child nodes with name "
                          + CoreTimeSerialization.getNodeName());
            } else {
                dakotaCallTime = CoreTimeSerialization.deserializeXML(timeNode);
            }
        }

        if (null != threatCallTimeNode) {
            Node timeNode = threatCallTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());

            if (null == timeNode) {
                Log.error(errorMessage + " node with name " + "to_make_threat_call" + " has no child nodes with name "
                          + CoreTimeSerialization.getNodeName());
            } else {
                threatCallTime = CoreTimeSerialization.deserializeXML(timeNode);
            }
        }

        if (null != make2020CallTimeNode) {
            Node timeNode = make2020CallTimeNode.getNamedChild(CoreTimeSerialization.getNodeName());

            if (null == timeNode) {
                Log.error(errorMessage + " node with name " + "to_make_2020_call" + " has no child nodes with name "
                          + CoreTimeSerialization.getNodeName());
            } else {
                make2020CallTime = CoreTimeSerialization.deserializeXML(timeNode);
            }
        }

        result.setAssault_start_time(assault_start_timeValue);
        result.setAssault_started(assault_startedValue);
        result.setBad_conditions(bad_conditionsValue);
        result.setDakota_warning_start_time(dakota_warning_start_timeValue);
        result.setInitialVelocity(initialVelocityValue);
        result.setAssaultCycleSceneCreated(isAssaultCycleSceneCreatedValue);
        result.setSlowDownAcceleration(SLOW_DOWN_ACCELValue);
        result.setSlowdown_start(slowdown_startValue);
        result.setTo_slow_down_gepard(to_slow_down_gepardValue);
        result.setTo_track_tunnel(to_track_tunnelValue);
        result.setWant_dakota_warning(want_dakota_warningValue);
        result.setWant_dakota_warning_started(want_dakota_warning_startedValue);
        result.setWas_near_base(was_near_baseValue);
        result.setWasVehicleExchange(wasVehicleExchangeValue);
        result.setCars(cars);
        result.setCars_assault(carsAssault);
        result.setDakota(dakota);
        result.setMonica(monica);

        if (null != discoverBaseTime) {
            EnemyBase.Timing_MonicaFindsOut timing = new EnemyBase.Timing_MonicaFindsOut();

            timing.setInterruptTime(discoverBaseTime);
            result.setTo_make_discover_base(timing);
        }

        if (null != dakotaCallTime) {
            EnemyBase.Timing_DakotaCall timing = new EnemyBase.Timing_DakotaCall();

            timing.setInterruptTime(dakotaCallTime);
            result.setTo_make_dakota_call(timing);
        }

        if (null != threatCallTime) {
            EnemyBase.Timing_ThreatenCall timing = new EnemyBase.Timing_ThreatenCall();

            timing.setFinishTime(threatCallTime);
            result.setTo_make_threat_call(timing);
        }

        if (null != make2020CallTime) {
            EnemyBase.Timing_BadCall timing = new EnemyBase.Timing_BadCall();

            timing.setInterruptTime(make2020CallTime);
            result.setTo_make_2020_call(timing);
        }
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void deSerialize(Node node) {
        deserializeXML(node, this.m_objectConfig, this.host);
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
