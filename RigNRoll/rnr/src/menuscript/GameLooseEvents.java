/*
 * @(#)GameLooseEvents.java   13/08/26
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
   Released under the BSD 3 clause license
Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this 
    list of conditions and the following disclaimer. Redistributions in binary 
    form must reproduce the above copyright notice, this list of conditions and 
    the following disclaimer in the documentation and/or other materials 
    provided with the distribution. Neither the name of the DieHard Development 
    nor the names of its contributors may be used to endorse or promote products 
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 *
 *
 *
 */


package rnr.src.menuscript;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.JavaEvents;
import rnr.src.rnrcore.Helper;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class GameLooseEvents {
    private static final boolean DEBUG_SHOW_TOTAL = false;

    /**
     * Method description
     *
     */
    public static void createEconomyLooseMenu() {
        VictoryMenu.createLooseEconomy(new VictoryMenuCloseListener());
    }

    /**
	 * @return the debugShowTotal
	 */
	public static boolean isDebugShowTotal() {
		return DEBUG_SHOW_TOTAL;
	}

	static class TotalMenuCloseListener implements VictoryMenuExitListener {

        /**
         * Method description
         *
         *
         * @param result
         */
        @Override
        public void OnMenuExit(int result) {
            switch (result) {
             case 1 :
                 JavaEvents.SendEvent(23, 1, null);
            }
        }
    }


    static class VictoryMenuCloseListener implements VictoryMenuExitListener {

        /**
         * Method description
         *
         *
         * @param result
         */
        @Override
        public void OnMenuExit(int result) {
            boolean hasAnyContest = Helper.hasContest();

            if (!(hasAnyContest)) {
                TotalVictoryMenu.createGameOverTotal(new GameLooseEvents.TotalMenuCloseListener());
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/26
