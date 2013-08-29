/*
 * @(#)DelayedResourceDisposer.java   13/08/28
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


package rnr.src.rnrscenario.missions;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscenario.sctask;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class DelayedResourceDisposer extends sctask {
    static final long serialVersionUID = 1L;
    private static final int UPDATE_FREQUENCY = 3;
    private static final int TASKS_CAPACITY = 128;
    private static DelayedResourceDisposer instance = null;
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final List<DisposeTask> tasks = new ArrayList(128);
    private boolean allToDispose = false;

    private DelayedResourceDisposer() {
        super(3, false);
        super.start();
    }

    /**
     * Method description
     *
     */
    public void setAllToDispose() {
        this.allToDispose = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static DelayedResourceDisposer getInstance() {
        if (null == instance) {
            instance = new DelayedResourceDisposer();
        }

        return instance;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        if (null != instance) {
            instance.finishImmediately();
        }

        instance = null;
    }

    /**
     * Method description
     *
     *
     * @param resource
     * @param missionName
     * @param channelUid
     * @param time
     */
    public void addResourceToDispose(Disposable resource, String missionName, String channelUid, int time) {
        if (null == resource) {
            return;
        }

        DisposeTask taskToInsert = new DisposeTask(time, resource, missionName, channelUid);
        int insertionIndex = Collections.binarySearch(this.tasks, taskToInsert);

        if (0 > insertionIndex) {
            insertionIndex = -(1 + insertionIndex);
        }

        this.tasks.add(insertionIndex, taskToInsert);
    }

    /**
     * Method description
     *
     *
     * @param resource
     *
     * @return
     */
    public boolean removeResource(Disposable resource) {
        if (null != resource) {
            for (int i = 0; i < this.tasks.size(); ++i) {
                if (this.tasks.get(i).resource != resource) {
                    continue;
                }

                this.tasks.remove(i);

                return true;
            }
        }

        return false;
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        if (this.tasks.isEmpty()) {
            return;
        }

        int taskIndex = 0;

        while ((this.tasks.size() > taskIndex) && (!(this.tasks.get(taskIndex).perform(3)))) {
            ++taskIndex;
        }

        int tasksToDelete = this.tasks.size() - taskIndex;

        while (0 < tasksToDelete) {
            this.tasks.remove(this.tasks.size() - 1).perform(3);
            --tasksToDelete;
        }

        this.allToDispose = false;
    }

    /**
     * Method description
     *
     *
     * @param missionName
     *
     * @return
     */
    public final boolean hasChannelsForMission(String missionName) {
        for (DisposeTask task : this.tasks) {
            if (missionName.compareTo(task.getMissionName()) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public List<DelayedDisposeChannelData> getDelayedDisposeChannelsData() {
        ArrayList result = new ArrayList();

        for (DisposeTask singleTask : this.tasks) {
            DelayedDisposeChannelData resultData = new DelayedDisposeChannelData(singleTask.missionName,
                                                       singleTask.channelUid, singleTask.secondsRemain);

            result.add(resultData);
        }

        return result;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public class DelayedDisposeChannelData {

        /** Field description */
        public String missionName;

        /** Field description */
        public String channelUid;

        /** Field description */
        public int secondsRemained;

        /**
         * Constructs ...
         *
         *
         * @param paramString1
         * @param paramString2
         * @param paramInt
         */
        public DelayedDisposeChannelData(String paramString1, String paramString2, int paramInt) {
            this.missionName = paramString1;
            this.channelUid = paramString2;
            this.secondsRemained = paramInt;
        }
    }


    private static final class DisposeTask implements Comparable<DisposeTask> {
        private int secondsRemain = 0;
        private boolean done = false;
        private Disposable resource = null;
        private String missionName = "";
        private String channelUid = "";

        DisposeTask(int secondsRemain, Disposable resource, String missionName, String channelUid) {
            assert(null != resource) : "resource must be non-null reference";
            this.secondsRemain = secondsRemain;
            this.resource = resource;
            this.missionName = missionName;
            this.channelUid = channelUid;
        }

        boolean perform(int timeLeft) {
            if (this.done) {
                return true;
            }

            this.secondsRemain -= timeLeft;

            if ((0 >= this.secondsRemain) || (DelayedResourceDisposer.getInstance().allToDispose)) {
                this.resource.dispose();
                this.done = true;

                return true;
            }

            return false;
        }

        /**
         * Method description
         *
         *
         * @param other
         *
         * @return
         */
        @Override
        public int compareTo(DisposeTask other) {
            if (this.secondsRemain < other.secondsRemain) {
                return 1;
            }

            if (this.secondsRemain > other.secondsRemain) {
                return -1;
            }

            return 0;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public String getMissionName() {
            return this.missionName;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int getTime() {
            return this.secondsRemain;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
