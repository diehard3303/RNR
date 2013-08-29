/*
 * @(#)ProfileManagement.java   13/08/26
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


package rnr.src.menuscript.mainmenu;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.JavaEvents;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class ProfileManagement {
    private static ProfileManagement instance = null;
    private final Vector<?> exits_profile_names = new Vector<Object>();
    private final boolean bRet = false;
    private String profile_name;
    private int profile_logo;
    private String new_profile_name;
    private String default_license_string;
    private String profile_license_string;

    /**
     * Method description
     *
     *
     * @return
     */
    public static ProfileManagement getProfileManager() {
        if (null == instance) {
            instance = new ProfileManagement();
        }

        return instance;
    }

    Vector<?> GetExistsProfiles() {
        JavaEvents.SendEvent(24, 0, this);

        Vector<?> res = (Vector<?>) this.exits_profile_names.clone();

        this.exits_profile_names.clear();

        return res;
    }

    void SetPrifile(String profile_name) {
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 1, this);
    }

    boolean DeleteProfile(String profile_name) {
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 2, this);

        return this.bRet;
    }

    boolean RenameProfile(String profile_name, String new_profile_name) {
        this.profile_name = profile_name;
        this.new_profile_name = new_profile_name;
        JavaEvents.SendEvent(24, 3, this);

        return this.bRet;
    }

    String GetCurrentProfileName() {
        JavaEvents.SendEvent(24, 4, this);

        return this.profile_name;
    }

    String getDefaultProfileName() {
        JavaEvents.SendEvent(24, 5, this);

        return this.profile_name;
    }

    void SetCurrentProfileLogo(int logo) {
        this.profile_logo = logo;
        JavaEvents.SendEvent(24, 6, this);
    }

    int GetCurrentProfileLogo() {
        JavaEvents.SendEvent(24, 7, this);

        return this.profile_logo;
    }

    void SetProfileLogo(String profile_name, int logo) {
        this.profile_logo = logo;
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 8, this);
    }

    int GetProfileLogo(String profile_name) {
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 9, this);

        return this.profile_logo;
    }

    boolean CreateProfile(String profile_name, int logo, String licese_string) {
        this.profile_name = profile_name;
        this.profile_logo = logo;
        this.profile_license_string = licese_string;
        JavaEvents.SendEvent(24, 11, this);

        return this.bRet;
    }

    boolean IsProfileExists(String profile_name) {
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 12, this);

        return this.bRet;
    }

    String GetCurrentProfileLicensePlateString() {
        JavaEvents.SendEvent(24, 13, this);

        return this.profile_license_string;
    }

    void SetCurrentProfileLicensePlateString(String name) {
        this.profile_license_string = name;
        JavaEvents.SendEvent(24, 14, this);
    }

    String GetProfileLicensePlateString(String profile_name) {
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 15, this);

        return this.profile_license_string;
    }

    void SetProfileLicensePlateString(String profile_name, String licese_string) {
        this.profile_name = profile_name;
        this.profile_license_string = licese_string;
        JavaEvents.SendEvent(24, 16, this);
    }

    String GetDefaultLicensePlateString() {
        JavaEvents.SendEvent(24, 17, this);

        return this.default_license_string;
    }

    String GetDefaultLicensePlateSuffix() {
        JavaEvents.SendEvent(24, 18, this);

        return this.default_license_string;
    }
}


//~ Formatted in DD Std on 13/08/26
