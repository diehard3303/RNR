/*
 * @(#)ScriptRef.java   13/08/26
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


package rnr.src.rnrcore;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class ScriptRef {
    private int uid = 0;
    private boolean reged = false;

    /**
     * Method description
     *
     *
     * @param id
     */
    public void setUid(int id) {
        this.uid = id;
    }

    /**
     * Method description
     *
     *
     * @param ref
     *
     * @return
     */
    public int getUid(IScriptRef ref) {
        if ((!(this.reged)) && (null != ref)) {
            this.reged = true;
            this.uid = UidStorage.getInstance().getUid();

            while (!(ScriptRefStorage.addRefference(ref))) {
                this.uid = UidStorage.getInstance().getUid();
            }
        }

        return this.uid;
    }

    /**
     * Method description
     *
     *
     * @param ref
     */
    public void removeRef(IScriptRef ref) {
        if ((!(this.reged)) || (null == ref)) {
            return;
        }

        ScriptRefStorage.removeRefference(ref);
        this.reged = false;
    }

    /**
     * Method description
     *
     *
     * @param uid
     * @param ref
     */
    public void register(int uid, IScriptRef ref) {
        this.uid = uid;
        this.reged = true;
        ScriptRefStorage.addRefference(ref);
    }
}


//~ Formatted in DD Std on 13/08/26
