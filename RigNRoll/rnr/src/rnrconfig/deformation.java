/*
 * @(#)deformation.java   13/08/26
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


package rnr.src.rnrconfig;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.eng;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class deformation {

    /**
     * Method description
     *
     *
     * @param deform_obj
     */
    public void Barrel_Crash1(long deform_obj) {
        eng.SetCrashBind_DO(deform_obj, 1, 1, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 2, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 3, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 4, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 5, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 6, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 7, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 8, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 9, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 10, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 11, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 12, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 13, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 14, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 15, 1);
        eng.SetCrashBind_DO(deform_obj, 1, 16, 1);
    }

    /**
     * Method description
     *
     *
     * @param deform_obj
     */
    public void Barrel_Crash2(long deform_obj) {
        eng.SetCrashBind_DO(deform_obj, 2, 1, 1);
        eng.SetCrashBind_DO(deform_obj, 2, 2, 1);
        eng.SetCrashBind_DO(deform_obj, 2, 3, 1);
        eng.SetCrashBind_DO(deform_obj, 2, 4, 1);
        eng.SetCrashBind_DO(deform_obj, 2, 5, 1);
        eng.SetCrashBind_DO(deform_obj, 2, 6, 0);
        eng.SetCrashBind_DO(deform_obj, 2, 7, 0);
        eng.SetCrashBind_DO(deform_obj, 2, 8, 0);
        eng.SetCrashBind_DO(deform_obj, 2, 9, 0);
        eng.SetCrashBind_DO(deform_obj, 2, 10, 0);
        eng.SetCrashBind_DO(deform_obj, 2, 11, 0);
    }
}


//~ Formatted in DD Std on 13/08/26
