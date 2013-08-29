/*
 * @(#)PassangerShifts.java   13/08/26
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

import rnr.src.rnrcore.vectorJ;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class PassangerShifts {
    private static PassangerShifts instance = new PassangerShifts();
    private final HashMap<String, vectorJ> m_shifts = new HashMap();

    private PassangerShifts() {
        this.m_shifts.put("Freight_Argosy_", new vectorJ(1.3447D, 0.15D, -0.839D));
        this.m_shifts.put("Freight_Cor_", new vectorJ(1.0204D, 0.22D, -0.839D));
        this.m_shifts.put("Freight_ClassicXL_", new vectorJ(0.857D, 0.15D, -0.839D));
        this.m_shifts.put("Freight_Century_", new vectorJ(1.0191D, 0.15D, -0.839D));
        this.m_shifts.put("Kenworth_T600_", new vectorJ(0.8148D, 0.15D, -0.839D));
        this.m_shifts.put("Kenworth_T800_", new vectorJ(0.80955D, 0.15D, -0.839D));
        this.m_shifts.put("Kenworth_W900_", new vectorJ(0.81113D, 0.15D, -0.839D));
        this.m_shifts.put("KenworthT2000_", new vectorJ(1.34486D, 0.15D, -0.8D));
        this.m_shifts.put("Peterbilt_378_", new vectorJ(0.83D, 0.15D, -0.839D));
        this.m_shifts.put("Peterbilt_379_", new vectorJ(0.851D, 0.15D, -0.839D));
        this.m_shifts.put("Peterbilt_387_", new vectorJ(1.14788D, 0.15D, -0.839D));
        this.m_shifts.put("WesternStar_4900_", new vectorJ(1.04339D, 0.25D, -0.839D));
        this.m_shifts.put("Sterling_9500_", new vectorJ(0.917157D, 0.15D, -0.839D));
        this.m_shifts.put("Gepard_", new vectorJ(0.0D, 0.0D, -0.839D));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static PassangerShifts getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     *
     * @param prefix
     *
     * @return
     */
    public vectorJ getShift(String prefix) {
        if (!(this.m_shifts.containsKey(prefix))) {
            return new vectorJ(0.0D, 0.0D, -0.839D);
        }

        return (this.m_shifts.get(prefix));
    }
}


//~ Formatted in DD Std on 13/08/26
