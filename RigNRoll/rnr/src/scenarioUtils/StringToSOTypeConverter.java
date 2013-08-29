/*
 * @(#)StringToSOTypeConverter.java   13/08/27
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


package rnr.src.scenarioUtils;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrloggers.ScenarioLogger;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public class StringToSOTypeConverter {
    private static HashMap<String, Integer> conversionTable = new HashMap();
    private static final int DEFAULT_CONVERTED_VALUE = 1024;

    static {
        conversionTable.put("bar", Integer.valueOf(8));
        conversionTable.put("police", Integer.valueOf(64));
        conversionTable.put("johnhouse", Integer.valueOf(1024));
        conversionTable.put("scobject", Integer.valueOf(1024));
        conversionTable.put("wharehouse", Integer.valueOf(2));
        conversionTable.put("motel", Integer.valueOf(4));
        conversionTable.put("office", Integer.valueOf(1));
    }

    /**
     * Method description
     *
     *
     * @param type
     *
     * @return
     */
    public static int convert(String type) {
        if (null != type) {
            Integer converted = conversionTable.get(type);

            if (null != converted) {
                return converted.intValue();
            }

            ScenarioLogger.getInstance().machineLog(Level.WARNING,
                    "StrintToSOTypeConverter: failed to convert " + type + " to sotip; returned default value");
        } else {
            ScenarioLogger.getInstance().machineLog(Level.WARNING,
                    "StrintToSOTypeConverter: can't convert null reference; returned default value");
        }

        return 1024;
    }
}


//~ Formatted in DD Std on 13/08/27
