/*
 * @(#)STOreaction.java   13/08/28
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


package rnr.src.rnrscr;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.CoreTime;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public class STOreaction {
    private static final String sceneType1 = "sto1";
    private static final String sceneType2 = "sto2";
    private static final String sceneType3 = "sto3";
    private static final String sceneType4 = "sto4";
    private static final String sceneType5 = "sto5";
    private static final String XXX_BadRacer1 = "XXX_BadRacer1";
    private static final String XXX_BadRacer2 = "XXX_BadRacer2";
    private static final String XXX_Champion1 = "XXX_Champion1";
    private static final String XXX_Champion2 = "XXX_Champion2";
    private static final String XXX_Champion3 = "XXX_Champion3";
    private static final String XXX_Lostrace1 = "XXX_Lostrace1";
    private static final String XXX_Lostrace2 = "XXX_Lostrace2";
    private static final String XXX_Lostrace3 = "XXX_Lostrace3";
    private static final String XXX_NicA_Howmytruck1 = "XXX_NicA_Howmytruck1";
    private static final String XXX_NicA_Howmytruck2 = "XXX_NicA_Howmytruck2";
    private static final String XXX_NicA_Howmytruck3 = "XXX_NicA_Howmytruck3";
    private static final String XXX_NicA_Howmytruck4 = "XXX_NicA_Howmytruck4";
    private static final String XXX_NicA_Howmytruck5 = "XXX_NicA_Howmytruck5";
    private static final String XXX_Letscheck1 = "XXX_Letscheck1";
    private static final String XXX_Letscheck2 = "XXX_Letscheck2";
    private static final String XXX_Letscheck3 = "XXX_Letscheck3";
    private static final String XXX_Letscheck4 = "XXX_Letscheck4";
    private static final String XXX_NicA_Gday = "XXX_NicA_Gday";
    private static final String XXX_NicA_Gevng = "XXX_NicA_Gevng";
    private static final String XXX_NicA_Gmng = "XXX_NicA_Gmng";
    private static final String XXX_NicA_Hello = "XXX_NicA_Hello";
    private static final String XXX_NicA_Hi = "XXX_NicA_Hi";
    private static final String XXX_Gday = "XXX_Gday";
    private static final String XXX_Gevng = "XXX_Gevng";
    private static final String XXX_Gmng = "XXX_Gmng";
    private static final String XXX_Hello = "XXX_Hello";
    private static final String XXX_Hi = "XXX_Hi";
    private static final String XXX_Lookgood = "XXX_Lookgood";
    private static final String XXX_Nicetomeet = "XXX_Nicetomeet";
    private static final String XXX_Whoisthat = "XXX_Whoisthat";
    private static final String XXX_DamagedTruck1 = "XXX_DamagedTruck1";
    private static final String XXX_NewTruck1 = "XXX_NewTruck1";
    private static final String XXX_NewTruck2 = "XXX_NewTruck2";
    private static final String XXX_NewTruck3 = "XXX_NewTruck3";
    private static final String XXX_Wonrace1 = "XXX_Wnrc1";
    private static final String XXX_Wonrace2 = "XXX_Wnrc2";
    private static final String XXX_Wonrace3 = "XXX_Wnrc3";
    @SuppressWarnings({ "unchecked", "rawtypes" })
    Map<DayTime, RandomAccesString> vNickSalute = new HashMap();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    Map<DayTime, RandomAccesString> vRepSalute1st = new HashMap();
    RandomAccesString vRepSalute2nd = new RandomAccesString();
    RandomAccesString vRepSalute2ndNewTruck = new RandomAccesString();
    RandomAccesString vRepSalute2ndDamagedTruck = new RandomAccesString();
    RandomAccesString vRepWonrace = new RandomAccesString();
    RandomAccesString vRepLostRace = new RandomAccesString();
    RandomAccesString vRepChampion = new RandomAccesString();
    RandomAccesString vRepBadRacer = new RandomAccesString();
    RandomAccesString vNicReply = new RandomAccesString();
    RandomAccesString vRepReply = new RandomAccesString();

    STOreaction() {
        RandomAccesString straccess = new RandomAccesString();

        straccess.add("XXX_NicA_Gmng");
        straccess.add("XXX_NicA_Hello");
        straccess.add("XXX_NicA_Hi");
        this.vNickSalute.put(DayTime.morning, straccess);
        straccess = new RandomAccesString();
        straccess.add("XXX_NicA_Gday");
        straccess.add("XXX_NicA_Hello");
        straccess.add("XXX_NicA_Hi");
        this.vNickSalute.put(DayTime.day, straccess);
        straccess = new RandomAccesString();
        straccess.add("XXX_NicA_Gevng");
        straccess.add("XXX_NicA_Hello");
        straccess.add("XXX_NicA_Hi");
        this.vNickSalute.put(DayTime.evening, straccess);
        straccess = new RandomAccesString();
        straccess.add("XXX_NicA_Hello");
        straccess.add("XXX_NicA_Hi");
        this.vNickSalute.put(DayTime.night, straccess);
        straccess = new RandomAccesString();
        straccess.add("XXX_Gmng");
        straccess.add("XXX_Hello");
        straccess.add("XXX_Hi");
        this.vRepSalute1st.put(DayTime.morning, straccess);
        straccess = new RandomAccesString();
        straccess.add("XXX_Gday");
        straccess.add("XXX_Hello");
        straccess.add("XXX_Hi");
        this.vRepSalute1st.put(DayTime.day, straccess);
        straccess = new RandomAccesString();
        straccess.add("XXX_Gevng");
        straccess.add("XXX_Hello");
        straccess.add("XXX_Hi");
        this.vRepSalute1st.put(DayTime.evening, straccess);
        straccess = new RandomAccesString();
        straccess.add("XXX_Hello");
        straccess.add("XXX_Hi");
        this.vRepSalute1st.put(DayTime.night, straccess);
        this.vRepSalute2nd.add("XXX_Lookgood");
        this.vRepSalute2nd.add("XXX_Nicetomeet");
        this.vRepSalute2nd.add("XXX_Whoisthat");
        this.vRepSalute2ndNewTruck.add("XXX_NewTruck1");
        this.vRepSalute2ndNewTruck.add("XXX_NewTruck2");
        this.vRepSalute2ndNewTruck.add("XXX_NewTruck3");
        this.vRepSalute2ndDamagedTruck.add("XXX_DamagedTruck1");
        this.vRepWonrace.add("XXX_Wnrc1");
        this.vRepWonrace.add("XXX_Wnrc2");
        this.vRepWonrace.add("XXX_Wnrc3");
        this.vRepLostRace.add("XXX_Lostrace1");
        this.vRepLostRace.add("XXX_Lostrace2");
        this.vRepLostRace.add("XXX_Lostrace3");
        this.vRepChampion.add("XXX_Champion1");
        this.vRepChampion.add("XXX_Champion2");
        this.vRepChampion.add("XXX_Champion3");
        this.vRepBadRacer.add("XXX_BadRacer1");
        this.vRepBadRacer.add("XXX_BadRacer2");
        this.vNicReply.add("XXX_NicA_Howmytruck1");
        this.vNicReply.add("XXX_NicA_Howmytruck2");
        this.vNicReply.add("XXX_NicA_Howmytruck3");
        this.vNicReply.add("XXX_NicA_Howmytruck4");
        this.vNicReply.add("XXX_NicA_Howmytruck5");
        this.vRepReply.add("XXX_Letscheck1");
        this.vRepReply.add("XXX_Letscheck2");
        this.vRepReply.add("XXX_Letscheck3");
        this.vRepReply.add("XXX_Letscheck4");
    }

    private static enum DayTime {
        morning, day, evening, night;

        /**
         * Method description
         *
         *
         * @return
         */
        public static final DayTime[] values() {
            return ((DayTime[]) $VALUES.clone());
        }
    }

    private DayTime getDayTime() {
        CoreTime time = new CoreTime();
        int hour = time.gHour();

        return (((hour >= 17) && (hour < 21))
                ? DayTime.evening
                : ((hour >= 12) && (hour < 17))
                  ? DayTime.day
                  : ((hour >= 6) && (hour < 12))
                    ? DayTime.morning
                    : DayTime.night);
    }

    void createReaction(stoscene.STOPreset preset, STOHistory history) {
        DayTime timeOfDay = getDayTime();

        preset.Nic1 = this.vNickSalute.get(timeOfDay).get();
        preset.Nic2 = this.vNicReply.get();
        preset.Rep2 = this.vRepReply.get();

        if (history.isFirstTimeHere()) {
            preset.Rep1 = this.vRepSalute1st.get(timeOfDay).get();
            preset.sceneName = "sto1";
        } else if (RaceHistory.RH.wonBigRace(CoreTime.hours(2))) {
            preset.Rep1 = this.vRepChampion.get();
            preset.sceneName = "sto4";
        } else if (RaceHistory.RH.wonSimpleRace(CoreTime.hours(2))) {
            preset.Rep1 = this.vRepWonrace.get();
            preset.sceneName = "sto4";
        } else if (RaceHistory.RH.lostCountsOfBigRaces()) {
            preset.Rep1 = this.vRepBadRacer.get();
            preset.sceneName = "sto3";
        } else if (RaceHistory.RH.lostBigRace()) {
            preset.Rep1 = this.vRepLostRace.get();
            preset.sceneName = "sto5";
        } else if (history.carIsDamaged()) {
            preset.Rep1 = this.vRepSalute2ndDamagedTruck.get();
            preset.sceneName = "sto3";
        } else if (history.newTruck()) {
            preset.Rep1 = this.vRepSalute2ndNewTruck.get();
            preset.sceneName = "sto2";
        } else if (history.isSecondOrMoreTimeHere()) {
            preset.Rep1 = this.vRepSalute2nd.get();
            preset.sceneName = "sto2";
        }
    }
}


//~ Formatted in DD Std on 13/08/28
