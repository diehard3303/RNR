/*
 * @(#)loaddriver.java   13/08/26
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


package rnr.src.rnrconfig;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.Helper;
import rnr.src.rnrcore.SCRcardriver;
import rnr.src.rnrcore.SCRcarpassanger;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class loaddriver {
    @SuppressWarnings("unchecked")
    private static HashMap<String, String> _modelNameToPrefix = new HashMap();
    private static Boolean _firstConstructorCall = Boolean.valueOf(true);
    private static final boolean FAST_DRIVERS = true;

    /**
     * Constructs ...
     *
     */
    public loaddriver() {
        if (!(_firstConstructorCall.booleanValue())) {
            return;
        }

        InitHashMap();
        _firstConstructorCall = Boolean.valueOf(false);
    }

    private static void InitHashMap() {
        String fileName = "..\\data\\config\\PassangerAnimations.xml";
        Node top = XmlUtils.parse(fileName);

        if (top == null) {
            return;
        }

        String topName = top.getName();

        if (topName != "PassangerAnimations") {
            return;
        }

        NodeList pairs = top.getNamedChildren("Entry");

        for (int i = 0; i < pairs.size(); ++i) {
            Node node = pairs.get(i);
            String modelName = node.getAttribute("modelName");
            String prefix = node.getAttribute("prefix");

            AddPair(modelName, prefix);
        }
    }

    private static void AddPair(String modelName, String prefix) {
        _modelNameToPrefix.put(modelName, prefix);
    }

    /**
     * Method description
     *
     *
     * @param player
     *
     * @return
     */
    public SCRcardriver Init(aiplayer player) {
        SCRcardriver drv = SCRcardriver.CreateCarDriver(player.getModel());
        String prefix = gAnimationPrefix(player.gModelname());

        if (null != prefix) {
            boolean make_fast = (prefix.startsWith("Man")) || (prefix.startsWith("Woman"));

            if (!(make_fast)) {
                drv.AddSitPose(prefix + "_sit_pose");
                drv.AddPedalingAnimation(prefix);
                drv.AddSteeringAnimation(prefix + "_rul01_RF", 0, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_grasp_RF", 0, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_LB", 1, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_grasp_LB", 1, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_LF", 2, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_grasp_LF", 2, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_RB", 3, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_grasp_RB", 3, 0);
                drv.AddGearingAnimation(prefix + "_rul01", 1);
            } else {
                drv.AddSitPose(prefix + "_sit_pose");
                drv.AddSteeringAnimation(prefix + "_rul01_LF", 2, 0);
                drv.AddSteeringAnimation(prefix + "_rul01_RB", 3, 0);
            }
        }

        vectorJ pos = new vectorJ(0.0D, 0.0D, -0.839D);

        drv.SetShift(pos);
        drv.play();

        return drv;
    }

    /**
     * Method description
     *
     *
     * @param player
     * @param car
     * @param stateflag
     *
     * @return
     */
    public SCRcarpassanger InitPassanger(aiplayer player, actorveh car, int stateflag) {
        SCRcarpassanger drv = InitPassanger_NoShift(player, stateflag);
        String prefix = gAnimationPrefix(player.gModelname());

        if (prefix != null) {
            drv.SetShift(GetPassendgerShift(prefix, eng.GetVehiclePrefix(car.getCar())));
        } else if (hasPackageShift(player.gPoolRefName())) {
            drv.SetShift(Helper.getPackageShift());
        }

        return drv;
    }

    /**
     * Method description
     *
     *
     * @param player
     * @param stateflag
     *
     * @return
     */
    public SCRcarpassanger InitPassanger_NoShift(aiplayer player, int stateflag) {
        SCRcarpassanger drv = new SCRcarpassanger();

        drv.initPassanger(player.getModel(), stateflag);

        String prefix = gAnimationPrefixForPassanger(player.gModelname());

        if (prefix != null) {
            drv.AddSitPose(prefix + "_sit_pose");
            drv.AddRockingAnimation(prefix + "_pas01_RF", 0, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RF1", 0, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LB", 1, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LB1", 1, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LF", 2, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LF1", 2, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RB", 3, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RB1", 3, 0);
        }

        drv.play();

        return drv;
    }

    /**
     * Method description
     *
     *
     * @param player
     * @param perfix
     * @param stateflag
     *
     * @return
     */
    public SCRcarpassanger InitPassanger_NoShift(aiplayer player, String perfix, int stateflag) {
        SCRcarpassanger drv = new SCRcarpassanger();

        drv.initPassanger(player.getModel(), stateflag);

        String prefix = gAnimationPrefixForPassanger(player.gModelname());

        if (prefix != null) {
            drv.AddSitPose(prefix + "_" + perfix + "_sit_pose");
            drv.AddRockingAnimation(prefix + "_pas01_RF", 0, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RF1", 0, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LB", 1, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LB1", 1, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LF", 2, 0);
            drv.AddRockingAnimation(prefix + "_pas01_LF1", 2, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RB", 3, 0);
            drv.AddRockingAnimation(prefix + "_pas01_RB1", 3, 0);
        }

        drv.play();

        return drv;
    }

    private boolean hasNoPassengerShift(String personmodelname) {
        return ((personmodelname.compareTo("BANDIT_JOE") == 0) || (personmodelname.compareTo("IVAN_NEW") == 0));
    }

    private boolean hasPackageShift(String personmodelname) {
        return personmodelname.contains("package");
    }

    /**
     * Method description
     *
     *
     * @param personmodelname
     * @param prefix
     *
     * @return
     */
    public vectorJ GetPassendgerShift(String personmodelname, String prefix) {
        vectorJ shift = new vectorJ(0.0D, 0.0D, -0.839D);

        if ((null == personmodelname) || (null == prefix)) {
            return shift;
        }

        if ((!(hasNoPassengerShift(personmodelname))) && (!(hasPackageShift(personmodelname)))) {
            shift = PassangerShifts.getInstance().getShift(prefix);
        } else if (hasPackageShift(personmodelname)) {
            shift = Helper.getPackageShift();
        }

        return shift;
    }

    /**
     * Method description
     *
     *
     * @param modelname
     *
     * @return
     */
    public String gAnimationPrefix(String modelname) {
        if (null == modelname) {
            return null;
        }

        if (modelname.startsWith("Man")) {
            return "Man";
        }

        if (modelname.startsWith("Woman")) {
            return "Woman";
        }

        if (modelname.endsWith("_Slow")) {
            String[] res = modelname.split("_Slow");

            return res[0];
        }

        return modelname;
    }

    /**
     * Method description
     *
     *
     * @param modelname
     *
     * @return
     */
    public String gAnimationPrefixForPassanger(String modelname) {
        if (null == modelname) {
            return null;
        }

        if (_modelNameToPrefix.containsKey(modelname)) {
            return (_modelNameToPrefix.get(modelname));
        }

        return gAnimationPrefix(modelname);
    }
}


//~ Formatted in DD Std on 13/08/26
