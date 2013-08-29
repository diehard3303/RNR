/*
 * @(#)BridgeTall.java   13/08/28
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

import rnr.src.menu.menues;
import rnr.src.menuscript.menuBridgeToll;
import rnr.src.rnrcore.TypicalAnm;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class BridgeTall extends TypicalAnm {

    /** Field description */
    public static final int ENTER = 0;

    /** Field description */
    public static final int EXIT = 1;

    /** Field description */
    public static final int DEFAULT = -1;
    private static int last_event = -1;

    /** Field description */
    public static final double DISTANCE_EXIT_TALL_RULE = 160000.0D;
    private static BridgeTall animation = null;
    private static boolean m_menu_is_on = false;
    private boolean to_stop = false;
    private vectorJ pos_enter = null;

    /**
     * Constructs ...
     *
     */
    public BridgeTall() {
        this.pos_enter = Helper.getCurrentPosition();
        eng.CreateInfinitScriptAnimation(this);
    }

    /**
     * Method description
     *
     *
     * @param event
     */
    public static void enterBridgeTall(int event) {
        if (m_menu_is_on) {
            return;
        }

        if ((0 == last_event) && (1 == event)) {
            eng.log("Make tall.");
            last_event = -1;

            if (null != animation) {
                animation.stop_anim();
                animation = null;
            }

            if (menues.cancreate_messagewindow()) {
                m_menu_is_on = true;
                menuBridgeToll.CreateBridgeTollMenu(new ILeaveMenuListener() {
                    @Override
                    public void menuLeaved() {
                        BridgeTall.access$002(false);
                    }
                });
            }
        } else if ((-1 == last_event) && (0 == event)) {
            last_event = 0;

            if (null != animation) {
                animation.stop_anim();
            }

            animation = new BridgeTall();
        }
    }

    private void stop_anim() {
        this.to_stop = true;
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
        if (this.to_stop) {
            animation = null;

            return true;
        }

        vectorJ pos = Helper.getCurrentPosition();

        if (160000.0D < pos.len2(this.pos_enter)) {
            stop_anim();
            animation = null;
            last_event = -1;
        }

        return false;
    }
}


//~ Formatted in DD Std on 13/08/28
