/*
 * @(#)CursedHiWay.java   13/08/28
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

import rnr.src.menu.JavaEvents;
import rnr.src.menu.menues;
import rnr.src.rnrcore.CursedHiWayObjectXmlSerializable;
import rnr.src.rnrcore.ObjectXmlSerializable;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscr.Helper;
import rnr.src.scriptEvents.EventsControllerHelper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class CursedHiWay extends TypicalAnm {
    private static final int[] EVENTS = { 21350, 21380, 21370 };
    private static final String[] EVENT_METHODS = { "on_enter_zone", "on_exit_zone", "on_downfall" };
    private static final String DWORD = "DWORD_CursedHiWay";
    private static final String[] MESSAGES = { "1370_enter", "1370_exit", "1370_downfall", "sc01300 failed",
            "sc01300 loaded" };
    private static final int MESSAGE_ENTER = 0;
    private static final int MESSAGE_EXIT = 1;
    private static final int MESSAGE_DOWNFALL = 2;
    private static final int MESSAGE_QUESTFAILED = 3;
    private static final int MESSAGE_QUESTLOADED = 4;
    private static final String METHOD_LOAD = "onLoad";
    private static final String METHOD_FAIL = "onFail";
    private static final double VELOCITY_DECREASE = 0.05D;
    private static CursedHiWay instance = null;
    private int[] event_ids = null;
    private double last_time = 0.0D;
    private boolean first_time = true;
    private InfluenceZone zone = null;
    private vectorJ positionInfluenceZone = null;
    private boolean on_animation = false;
    private boolean release_animation = false;
    private boolean stop_animation = false;
    private double last_coef = 0.0D;
    private boolean dead_from_mist = false;
    private ObjectXmlSerializable serializator = null;

    /**
     * Constructs ...
     *
     */
    public CursedHiWay() {
        instance = this;
        StaticScenarioStuff.makeReadyCursedHiWay(true);
        EventsControllerHelper.getInstance().addMessageListener(this, "onLoad", MESSAGES[4]);
        EventsControllerHelper.getInstance().addMessageListener(this, "onFail", MESSAGES[3]);
        this.serializator = new CursedHiWayObjectXmlSerializable(this);
        this.serializator.registerObjectXmlSerializable();
    }

    /**
     * Method description
     *
     */
    public static void finishCursedHiWay() {
        if (null == instance) {
            return;
        }

        instance.stop_animation = true;
        eng.setdword("DWORD_CursedHiWay", 0);

        if (null != instance.event_ids) {
            for (int i = 0; i < instance.event_ids.length; ++i) {
                event.removeEventObject(instance.event_ids[i]);
            }
        }

        instance = null;
        JavaEvents.SendEvent(72, 0, new Data(0.0D));
    }

    /**
     * Method description
     *
     */
    public void onLoad() {
        this.event_ids = new int[EVENTS.length];

        for (int i = 0; i < EVENTS.length; ++i) {
            this.event_ids[i] = event.eventObject(EVENTS[i], this, EVENT_METHODS[i]);
        }

        eng.setdword("DWORD_CursedHiWay", 1);
    }

    /**
     * Method description
     *
     */
    public void onFail() {
        if (this.on_animation) {
            return;
        }
    }

    /**
     * Method description
     *
     */
    public void on_enter_zone() {
        this.on_animation = true;
        EventsControllerHelper.messageEventHappened(MESSAGES[0]);
        this.positionInfluenceZone = Helper.getCurrentPosition();
        this.zone = new InfluenceZone(this.positionInfluenceZone);
        eng.CreateInfinitScriptAnimation(this);
    }

    /**
     * Method description
     *
     */
    public void on_exit_zone() {
        this.release_animation = true;
        EventsControllerHelper.messageEventHappened(MESSAGES[1]);
        eng.enableCabinView(true);
    }

    /**
     * Method description
     *
     */
    public void on_downfall() {
        this.stop_animation = true;
        EventsControllerHelper.messageEventHappened(MESSAGES[2]);
    }

    /**
     * Method description
     *
     *
     * @param dt
     *
     * @return
     */
    @Override
    public boolean animaterun(double dt) {
        if (this.stop_animation) {
            this.serializator.unRegisterObjectXmlSerializable();

            return true;
        }

        if (this.dead_from_mist) {
            JavaEvents.SendEvent(72, 0, new Data(1.0D));
            this.serializator.unRegisterObjectXmlSerializable();

            return true;
        }

        if (this.first_time) {
            this.last_time = dt;
            this.first_time = false;
        }

        double diff = 0.05D * (dt - this.last_time);

        if ((this.release_animation) || (!(this.zone.isInside()))) {
            diff *= -1.0D;
        }

        this.last_coef += diff;

        if (this.last_coef > 1.0D) {
            this.last_coef = 1.0D;
            this.dead_from_mist = true;
            menues.gameoverMenu();
            eng.disableControl();
        } else if (this.last_coef < 0.0D) {
            this.last_coef = 0.0D;
        }

        JavaEvents.SendEvent(72, 0, new Data(this.last_coef));

        if (this.last_coef > 0.001D) {
            eng.enableCabinView(false);
        } else {
            eng.enableCabinView(true);
        }

        this.last_time = dt;

        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isDead_from_mist() {
        return this.dead_from_mist;
    }

    /**
     * Method description
     *
     *
     * @param dead_from_mist
     */
    public void setDead_from_mist(boolean dead_from_mist) {
        this.dead_from_mist = dead_from_mist;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double getLast_coef() {
        return this.last_coef;
    }

    /**
     * Method description
     *
     *
     * @param last_coef
     */
    public void setLast_coef(double last_coef) {
        this.last_coef = last_coef;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isOn_animation() {
        return this.on_animation;
    }

    /**
     * Method description
     *
     *
     * @param on_animation
     */
    public void setOn_animation(boolean on_animation) {
        this.on_animation = on_animation;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public vectorJ getPositionInfluenceZone() {
        return this.positionInfluenceZone;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setPositionInfluenceZone(vectorJ value) {
        if (null == value) {
            return;
        }

        this.positionInfluenceZone = value;
        this.zone = new InfluenceZone(this.positionInfluenceZone);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isRelease_animation() {
        return this.release_animation;
    }

    /**
     * Method description
     *
     *
     * @param release_animation
     */
    public void setRelease_animation(boolean release_animation) {
        this.release_animation = release_animation;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean isStop_animation() {
        return this.stop_animation;
    }

    /**
     * Method description
     *
     *
     * @param stop_animation
     */
    public void setStop_animation(boolean stop_animation) {
        this.stop_animation = stop_animation;
    }

    static class Data {
        double value;

        Data() {
            this.value = 0.0D;
        }

        Data(double value) {
            this.value = value;
        }
    }


    static class InfluenceZone {
        vectorJ P0;
        vectorJ n;
        vectorJ m;
        double n_coef;
        double m_coef;

        InfluenceZone(vectorJ posP0) {
            this.P0 = posP0;
            this.P0.z = 0.0D;

            vectorJ pos = eng.getControlPointPosition("Cursed Highway Scene");

            pos.z = 0.0D;
            this.n = pos.oMinusN(this.P0);
            this.m = this.n.oCross(new vectorJ(0.0D, 0.0D, 1.0D));
            this.m.norm();
            this.m.mult(100.0D);

            vectorJ shift_p0 = new vectorJ(this.m);

            shift_p0.mult(0.5D);
            this.P0.oMinus(shift_p0);
            this.n_coef = this.n.length();
            this.n_coef *= this.n_coef;
            this.m_coef = this.m.length();
            this.m_coef *= this.m_coef;
            this.n_coef = (1.0D / this.n_coef);
            this.m_coef = (1.0D / this.m_coef);
        }

        boolean isInside() {
            vectorJ pos = Helper.getCurrentPosition();

            pos.oMinus(this.P0);

            double pos_n = pos.dot(this.n) * this.n_coef;

            if ((pos_n < 0.0D) || (pos_n > 1.0D)) {
                return false;
            }

            double pos_m = pos.dot(this.m) * this.m_coef;

            return ((pos_m >= 0.0D) && (pos_m <= 1.0D));
        }
    }
}


//~ Formatted in DD Std on 13/08/28
