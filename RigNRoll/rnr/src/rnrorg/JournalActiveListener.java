/*
 * @(#)JournalActiveListener.java   13/08/26
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


package rnr.src.rnrorg;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscr.IMissionInformation;
import rnr.src.rnrscr.MissionDialogs;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class JournalActiveListener {
    private final String m_resource;

    /**
     * Constructs ...
     *
     *
     * @param resourceId
     */
    public JournalActiveListener(String resourceId) {
        assert(resourceId != null);
        this.m_resource = resourceId;
    }

    /**
     * Method description
     *
     */
    public void onAnswerNo() {
        MissionDialogs.sayNo(this.m_resource);
    }

    /**
     * Method description
     *
     */
    public void onAnswerYes() {
        MissionDialogs.sayYes(this.m_resource);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public IMissionInformation getMissionInfo() {
        return MissionDialogs.getMissionInfoAndInCachedToo(this.m_resource);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getResource() {
        return this.m_resource;
    }
}


//~ Formatted in DD Std on 13/08/26
