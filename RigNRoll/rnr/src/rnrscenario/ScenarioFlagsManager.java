/*
 * @(#)ScenarioFlagsManager.java   13/08/28
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


package rnr.src.rnrscenario;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrscenario.ScenarioFlagsManager.IAnimation;
import rnr.src.scenarioUtils.Pair;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class ScenarioFlagsManager extends sctask {

    /** Field description */
    public static final String MONSTER_CUP_PREPARED = "Start_M_CUP_news";

    /** Field description */
    public static final String HIGHWAY_108_NEWS_AVALIBLE = "Start_SR108_news";

    /** Field description */
    public static final String MISSIONS_ENABLED = "MissionsEnebledByScenario";

    /** Field description */
    public static final String DOROTHY_IS_AVALABLE = "Dorothy_is_available";

    /** Field description */
    public static final String SAVES_ARE_AVALIBLE = "SavesEnabledByScenario";

    /** Field description */
    public static final String WAREHOUSES_ARE_AVALIBLE = "WarehousesEnabledByScenario";
    private static ScenarioFlagsManager instance = null;
    @SuppressWarnings("unchecked")
    private final HashMap<String, Pair<Boolean, IAnimation>> m_flagAnimations = new HashMap<String,
                                                                                    Pair<Boolean, IAnimation>>();

    private ScenarioFlagsManager() {
        super(3, false);
        start();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static ScenarioFlagsManager getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        instance = null;
    }

    /**
     * Method description
     *
     */
    public static void init() {
        instance = new ScenarioFlagsManager();
        instance.setFlagValue("MissionsEnebledByScenario", true);
        instance.setFlagValue("Dorothy_is_available", false);
        instance.setFlagValue("SavesEnabledByScenario", true);
        instance.setFlagValue("WarehousesEnabledByScenario", true);
        instance.setFlagValue("Start_SR108_news", false);
    }

    /**
     * Method description
     *
     *
     * @param flagName
     *
     * @return
     */
    public static boolean getValue(String flagName) {
        return getInstance().getFlagValue(flagName);
    }

    /**
     * Method description
     *
     *
     * @param flagName
     * @param value
     */
    public static void setValue(String flagName, boolean value) {
        getInstance().setFlagValue(flagName, value);
    }

    /**
     * Method description
     *
     *
     * @param flagName
     *
     * @return
     */
    public final boolean getFlagValue(String flagName) {
        if (!(this.m_flagAnimations.containsKey(flagName))) {
            return false;
        }

        Pair flagValue = this.m_flagAnimations.get(flagName);

        return ((Boolean) flagValue.getFirst()).booleanValue();
    }

    /**
     * Method description
     *
     *
     * @param flagName
     * @param value
     */
    public final void setFlagValue(String flagName, boolean value) {
        if (this.m_flagAnimations.containsKey(flagName)) {
            Pair flagValue = this.m_flagAnimations.get(flagName);

            flagValue.setFirst(Boolean.valueOf(value));
            flagValue.setSecond(new StaticFlag());
        } else {
            Pair flagValue = new Pair(Boolean.valueOf(value), new StaticFlag());

            this.m_flagAnimations.put(flagName, flagValue);
        }
    }

    /**
     * Method description
     *
     *
     * @param flagName
     * @param numDays
     */
    public final void setFlagValueTimed(String flagName, int numDays) {
        boolean value = true;
        IAnimation anim = new TimeFlag(numDays);

        value = anim.animateFlag(value);

        if (this.m_flagAnimations.containsKey(flagName)) {
            Pair flagValue = this.m_flagAnimations.get(flagName);

            flagValue.setFirst(Boolean.valueOf(value));
            flagValue.setSecond(anim);
        } else {
            Pair flagValue = new Pair(Boolean.valueOf(value), anim);

            this.m_flagAnimations.put(flagName, flagValue);
        }
    }

    /**
     * Method description
     *
     *
     * @param flagName
     * @param finishTime
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final void setFlagValueTimed(String flagName, CoreTime finishTime) {
        boolean value = true;
        IAnimation anim = new TimeFlag(finishTime);

        value = anim.animateFlag(value);

        if (this.m_flagAnimations.containsKey(flagName)) {
            Pair flagValue = this.m_flagAnimations.get(flagName);

            flagValue.setFirst(Boolean.valueOf(value));
            flagValue.setSecond(anim);
        } else {
            Pair flagValue = new Pair(Boolean.valueOf(value), anim);

            this.m_flagAnimations.put(flagName, flagValue);
        }
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public final void run() {
        Set values = this.m_flagAnimations.entrySet();

        for (Map.Entry entry : values) {
            Pair pairFlaganimation = (Pair) entry.getValue();
            IAnimation animation = (IAnimation) pairFlaganimation.getSecond();
            boolean previousValue = ((Boolean) pairFlaganimation.getFirst()).booleanValue();
            boolean newValue = animation.animateFlag(previousValue);

            pairFlaganimation.setFirst(Boolean.valueOf(newValue));
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final HashMap<String, Boolean> getStaticFlags() {
        HashMap result = new HashMap();
        Set values = this.m_flagAnimations.entrySet();

        for (Map.Entry entry : values) {
            Pair pairFlaganimation = (Pair) entry.getValue();
            IAnimation animation = (IAnimation) pairFlaganimation.getSecond();

            if (animation instanceof StaticFlag) {
                result.put(entry.getKey(), pairFlaganimation.getFirst());
            }
        }

        return result;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final HashMap<String, CoreTime> getTimedFlags() {
        HashMap result = new HashMap();
        Set values = this.m_flagAnimations.entrySet();

        for (Map.Entry entry : values) {
            Pair pairFlaganimation = (Pair) entry.getValue();
            IAnimation animation = (IAnimation) pairFlaganimation.getSecond();

            if (animation instanceof TimeFlag) {
                TimeFlag timeAnimation = (TimeFlag) animation;

                result.put(entry.getKey(), timeAnimation.getFinishTime());
            }
        }

        return result;
    }

    static abstract interface IAnimation {

        /**
         * Method description
         *
         *
         * @param paramBoolean
         *
         * @return
         */
        public abstract boolean animateFlag(boolean paramBoolean);
    }


    static class StaticFlag implements ScenarioFlagsManager.IAnimation {

        /**
         * Method description
         *
         *
         * @param currentFlagValue
         *
         * @return
         */
        @Override
        public boolean animateFlag(boolean currentFlagValue) {
            return currentFlagValue;
        }
    }


    static class TimeFlag implements ScenarioFlagsManager.IAnimation {
        private final CoreTime m_finishTime;

        TimeFlag(CoreTime finishTime) {
            this.m_finishTime = new CoreTime(finishTime);
        }

        TimeFlag(int numDays) {
            this.m_finishTime = new CoreTime();
            this.m_finishTime.plus_days(numDays);
        }

        CoreTime getFinishTime() {
            return this.m_finishTime;
        }

        /**
         * Method description
         *
         *
         * @param currentFlagValue
         *
         * @return
         */
        @Override
        public boolean animateFlag(boolean currentFlagValue) {
            if (currentFlagValue) {
                CoreTime currentTime = new CoreTime();

                return (currentTime.moreThan(this.m_finishTime) < 0);
            }

            return false;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
