/*
 * @(#)SpecialObjectEvent.java   13/08/28
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscr.cSpecObjects;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ
 */
public final class SpecialObjectEvent implements ScriptEvent {
    static final long serialVersionUID = 0L;
    private static final int DEFAULT_STRING_BUILDER_CAPACITY = 100;
    private EventType eventType = EventType.any;
    private int soType = 0;
    private String soName = "unknown";

    /**
     * Enum description
     *
     */
    public static enum EventType { enter, exit, f2, any, none; }

    SpecialObjectEvent() {}

    /**
     * Constructs ...
     *
     *
     * @param object
     * @param eventType
     */
    public SpecialObjectEvent(cSpecObjects object, EventType eventType) {
        if (null == object) {
            System.err.println("SpecialObjectEvent.<init>: failed to construct instance, object must be non-null");
        }

        this.soName = object.name;
        this.soType = object.sotip;
        this.eventType = eventType;
    }

    /**
     * Constructs ...
     *
     *
     * @param objectName
     * @param objectType
     * @param eventType
     */
    public SpecialObjectEvent(String objectName, int objectType, EventType eventType) {
        if (null == objectName) {
            System.err.println("SpecialObjectEvent.<init>: failed to construct instance, object must be non-null");
        }

        this.soName = objectName;
        this.soType = objectType;
        this.eventType = eventType;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getObjectName() {
        return this.soName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getSoType() {
        return this.soType;
    }

    /**
     * Method description
     *
     *
     * @param entered
     */
    public void setObject(cSpecObjects entered) {
        this.soName = entered.name;
        this.soType = entered.sotip;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public EventType getEventType() {
        return this.eventType;
    }

    void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(100);

        builder.append("SpecialObjectEvent");

        if (null != this.soName) {
            builder.append(" name == " + this.soName + ';');
        }

        builder.append(" event type == " + this.eventType.name());

        return builder.toString();
    }
}


//~ Formatted in DD Std on 13/08/28
