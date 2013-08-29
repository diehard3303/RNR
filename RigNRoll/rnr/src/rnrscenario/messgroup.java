/*
 * @(#)messgroup.java   13/08/28
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

import rnr.src.menu.menues;
import rnr.src.menuscript.MessageWindow;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class messgroup extends sctask {
    String message;
    boolean hasok;
    boolean istutorial;
    double lifetime;
    String exitkey;
    boolean stopworld;
    boolean polosy;

    messgroup(String message, boolean hasok, boolean istutorial, double lifetime, String exitkey, boolean stopworld,
              boolean polosy) {
        super(3, true);
        this.message = message;
        this.hasok = hasok;
        this.istutorial = istutorial;
        this.lifetime = lifetime;
        this.exitkey = exitkey;
        this.stopworld = stopworld;
        this.polosy = polosy;

        if (!(makecreation())) {
            return;
        }

        finish();
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        if (makecreation()) {
            finish();
        }
    }

    boolean makecreation() {
        if (!(menues.cancreate_somenu())) {
            return false;
        }

        create();

        return true;
    }

    void create() {
        MessageWindow.CreateMessageWindow(this.message, this.hasok, this.istutorial, this.lifetime, this.exitkey,
                                          this.stopworld, this.polosy);
    }
}


//~ Formatted in DD Std on 13/08/28
