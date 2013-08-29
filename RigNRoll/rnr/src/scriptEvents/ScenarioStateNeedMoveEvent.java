/*
 * @(#)ScenarioStateNeedMoveEvent.java   13/08/27
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


package rnr.src.scriptEvents;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public final class ScenarioStateNeedMoveEvent implements ScriptEvent {
    static final long serialVersionUID = 0L;
    private int nodeIntName = -1;
    private String nodeStringName = null;

    /**
     * Constructs ...
     *
     */
    public ScenarioStateNeedMoveEvent() {}

    /**
     * Constructs ...
     *
     *
     * @param destinationNodeName
     * @param destinationNodeID
     */
    public ScenarioStateNeedMoveEvent(String destinationNodeName, int destinationNodeID) {
        if (null == destinationNodeName) {
            throw new IllegalArgumentException("destinationNodeName must be non-null");
        }

        this.nodeIntName = destinationNodeID;
        this.nodeStringName = destinationNodeName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getNodeIntName() {
        return this.nodeIntName;
    }

    /**
     * Method description
     *
     *
     * @param nodeIntName
     */
    public void setNodeIntName(int nodeIntName) {
        this.nodeIntName = nodeIntName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getNodeStringName() {
        return this.nodeStringName;
    }

    /**
     * Method description
     *
     *
     * @param nodeStringName
     */
    public void setNodeStringName(String nodeStringName) {
        this.nodeStringName = nodeStringName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String toString() {
        if (null != this.nodeStringName) {
            return "ScenarioStateNeedMoveEvent with nodeStringName " + this.nodeStringName;
        }

        return new String();
    }
}


//~ Formatted in DD Std on 13/08/27
