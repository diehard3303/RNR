/*
 * @(#)InfoText.java   13/08/26
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


package rnr.src.rnrlauncher.data;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class InfoText {
    private final String name;
    private final String userSystemInfo;
    private final String requiredSystemInfo;
    private final String resulutionInfo;
    private final int status;

    /**
     * Constructs ...
     *
     *
     * @param userSystemInfo
     * @param requiredSystemInfo
     * @param resulutionInfo
     * @param status
     * @param name
     */
    public InfoText(String userSystemInfo, String requiredSystemInfo, String resulutionInfo, int status, String name) {
        this.userSystemInfo = userSystemInfo;
        this.requiredSystemInfo = requiredSystemInfo;
        this.resulutionInfo = resulutionInfo;
        this.status = status;
        this.name = name;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getUserSystemInfo() {
        return this.userSystemInfo;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getRequiredSystemInfo() {
        return this.requiredSystemInfo;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getResulutionInfo() {
        return this.resulutionInfo;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getStatus() {
        return this.status;
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
}


//~ Formatted in DD Std on 13/08/26
