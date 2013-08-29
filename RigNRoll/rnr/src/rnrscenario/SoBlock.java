/*
 * @(#)SoBlock.java   13/08/26
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

import rnr.src.rnrcore.BlockedSOObjectXmlSerializable;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.ObjectXmlSerializable;
import rnr.src.rnrcore.eng;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ
 */
public class SoBlock {
    private static SoBlock instance = null;
    private Vector<Blocked_SO> blocked = new Vector();
    private ObjectXmlSerializable serializable = null;

    private SoBlock() {
        initSerializator();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static SoBlock getInstance() {
        if (null == instance) {
            instance = new SoBlock();
        }

        return instance;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        if (instance != null) {
            instance.deinitSerializator();
        }

        instance = null;
    }

    private void initSerializator() {
        this.serializable = new BlockedSOObjectXmlSerializable(this);
        this.serializable.registerObjectXmlSerializable();
    }

    private void deinitSerializator() {
        if (this.serializable != null) {
            this.serializable.unRegisterObjectXmlSerializable();
        }
    }

    /**
     * Method description
     *
     */
    public void process() {
        Iterator iter = this.blocked.iterator();

        while (iter.hasNext()) {
            Blocked_SO block = (Blocked_SO) iter.next();
            boolean finish = block.process();

            if (finish) {
                iter.remove();
            }
        }
    }

    private void add(Blocked_SO sobl) {
        this.blocked.add(sobl);
    }

    /**
     * Method description
     *
     *
     * @param tip
     * @param pointname
     * @param days
     */
    public void addTimeBlocker_DAYS(int tip, String pointname, int days) {
        TimeOutCondition ti = new TimeOutCondition();

        ti.deltatime = CoreTime.days(days);

        Blocked_SO blocker = new Blocked_SO(tip, pointname, ti);

        add(blocker);
        blocker.block();
    }

    /**
     * Method description
     *
     *
     * @param tip
     * @param days
     */
    public void addTimeBlocker_DAYS(int tip, int days) {
        TimeOutCondition ti = new TimeOutCondition();

        ti.deltatime = CoreTime.days(days);

        Blocked_SO blocker = new Blocked_SO(tip, null, ti);

        add(blocker);
        blocker.block();
    }

    /**
     * Method description
     *
     *
     * @param tip
     * @param pointname
     */
    public void addBlocker(int tip, String pointname) {
        if (eng.useNative()) {
            eng.blockNamedSO(tip, pointname);
        }
    }

    /**
     * Method description
     *
     *
     * @param tip
     */
    public void addBlocker(int tip) {
        if (eng.useNative()) {
            eng.blockSO(tip);
        }
    }

    /**
     * Method description
     *
     *
     * @param tip
     * @param pointname
     */
    public void removeBlocker(int tip, String pointname) {
        Iterator iter = this.blocked.iterator();

        while (iter.hasNext()) {
            Blocked_SO blocker = (Blocked_SO) iter.next();

            if (blocker.canBeRemovedByUnblocker(tip, pointname) != false) {
                iter.remove();
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param tip
     */
    public void removeBlocker(int tip) {
        removeBlocker(tip, null);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Vector<Blocked_SO> getBlocked() {
        return this.blocked;
    }

    /**
     * Method description
     *
     *
     * @param blocked
     */
    public void setBlocked(Vector<Blocked_SO> blocked) {
        this.blocked = blocked;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ
     */
    public static class Blocked_SO {
        private boolean finished = false;
        int type = 0;
        String name = "no name";
        SoBlock.TimeOutCondition condition = null;

        /**
         * Constructs ...
         *
         *
         * @param type
         * @param name
         * @param condition
         */
        public Blocked_SO(int type, String name, SoBlock.TimeOutCondition condition) {
            assert(condition != null);
            this.type = type;
            this.name = name;
            this.condition = condition;
        }

        /**
         * Method description
         *
         */
        public void block() {
            if (eng.useNative()) {
                if (null != this.name) {
                    eng.blockNamedSO(this.type, this.name);
                } else {
                    eng.blockSO(this.type);
                }
            }
        }

        /**
         * Method description
         *
         */
        public void unblock() {
            if (eng.useNative()) {
                if (null != this.name) {
                    eng.unblockNamedSO(this.type, this.name);
                } else {
                    eng.unblockSO(this.type);
                }
            }
        }

        boolean process() {
            if (this.finished) {
                return true;
            }

            this.finished = this.condition.isFinished();

            if (this.finished) {
                unblock();

                return true;
            }

            return false;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int getType() {
            return this.type;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public String getName() {
            return this.name;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public SoBlock.TimeOutCondition getCondition() {
            return this.condition;
        }

        private boolean canBeRemovedByUnblocker(int tip, String pointname) {
            if (pointname == null) {
                return (this.type == tip);
            }

            return ((this.type == tip) && (pointname.compareTo(this.name) == 0));
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ
     */
    public static class TimeOutCondition {
        CoreTime deltatime = null;
        CoreTime timeStart;

        TimeOutCondition() {
            this.timeStart = new CoreTime();
        }

        /**
         * Constructs ...
         *
         *
         * @param timeStart
         * @param deltatime
         */
        public TimeOutCondition(CoreTime timeStart, CoreTime deltatime) {
            this.timeStart = timeStart;
            this.deltatime = deltatime;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isFinished() {
            return (new CoreTime().moreThanOnTime(this.timeStart, this.deltatime) >= 0);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public CoreTime getDeltatime() {
            return this.deltatime;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public CoreTime getTimeStart() {
            return this.timeStart;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
