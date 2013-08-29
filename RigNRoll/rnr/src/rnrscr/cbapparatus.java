/*
 * @(#)cbapparatus.java   13/08/28
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

import rnr.src.players.aiplayer;
import rnr.src.rnrcore.eng;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class cbapparatus {
    private static cbapparatus instance = null;
    private final ArrayList<CBVideocallelemnt> allcalls = new ArrayList();

    /**
     * Method description
     *
     *
     * @return
     */
    public static cbapparatus getInstance() {
        if (instance == null) {
            instance = new cbapparatus();
        }

        return instance;
    }

    private void add(CBVideocallelemnt el) {
        this.allcalls.add(el);
    }

    /**
     * Method description
     *
     */
    public void clearFinishedCalls() {
        for (ListIterator<CBVideocallelemnt> i = this.allcalls.listIterator(); i.hasNext(); ) {
            CBVideocallelemnt ell = i.next();

            if (ell.gFinished()) {
                i.remove();
            }
        }
    }

    /**
     * Method description
     *
     */
    public void clearAllCalls() {
        this.allcalls.clear();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ArrayList<CBVideocallelemnt> gCallers() {
        ArrayList res = new ArrayList(this.allcalls.size());

        for (CBVideocallelemnt ell : this.allcalls) {
            if (!(ell.gFinished())) {
                res.add(ell);
            }
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param pl
     * @param dialogname
     *
     * @return
     */
    public CBVideocallelemnt makecall(aiplayer pl, String dialogname) {
        CBVideocallelemnt el = new CBVideocallelemnt(pl, dialogname);

        el.setDialogName(dialogname);

        if (null == el.whocalls) {
            eng.err("ERROR. cbvideocall has null player. Dialog name " + dialogname);
        }

        el.initiate();

        if (0L == el.nativePointer) {
            eng.err("ERROR. cbvideocall has no reflection in native. Dialog name " + dialogname);
        }

        add(el);

        return el;
    }
}


//~ Formatted in DD Std on 13/08/28
