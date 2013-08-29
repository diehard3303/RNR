/*
 * @(#)MenuResourcesManager.java   13/08/26
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


package rnr.src.menu.resource;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class MenuResourcesManager {
    private static String[] activatorIconsMenuIds = {
        "f2barMENU", "f2defaultMENU", "f2gasMENU", "f2motelMENU", "f2officeMENU", "f2policeMENU", "f2repairMENU",
        "missioniconTrailerAMENU", "missioniconTrailerNAMENU", "missioniconPackageAMENU", "missioniconPackageNAMENU",
        "missioniconPassangerAMENU", "missioniconPassangerNAMENU", "missioniconEndMENU", "missioniconTalkAMENU"
    };
    private static String[] bannerStallasMenuIds = {
        "banner01MENU", "banner02MENU", "banner03MENU", "banner04MENU", "racecheckin01MENU", "racecheckin02MENU",
        "racecheckin03MENU", "racecheckin04MENU", "racestartin01MENU", "racestartin02MENU", "racestartin03MENU",
        "racestartin04MENU", "stellaPreparingToOrdersMENU", "stellaPreparingToRaceMENU", "stellaWilcomMENU"
    };

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public static native void holdMenuResource(String paramString);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public static native void leaveMenuResource(String paramString);

    /**
     * Method description
     *
     */
    public static void holdResources() {
        for (String menuid : activatorIconsMenuIds) {
            holdMenuResource(menuid);
        }

        for (String menuid : bannerStallasMenuIds) {
            holdMenuResource(menuid);
        }
    }

    /**
     * Method description
     *
     */
    public static void leaveResources() {
        for (String menuid : activatorIconsMenuIds) {
            leaveMenuResource(menuid);
        }

        for (String menuid : bannerStallasMenuIds) {
            leaveMenuResource(menuid);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
