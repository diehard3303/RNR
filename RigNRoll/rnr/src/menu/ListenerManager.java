/*
 * @(#)ListenerManager.java   13/08/25
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


package rnr.src.menu;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class ListenerManager {

    /** Field description */
    public static final int INIT_MENU = 103;

    /** Field description */
    public static final int AFTER_INIT_MENU = 104;

    /** Field description */
    public static final int EXIT_MENU = 105;
    private static HashMap<Integer, Vector<SelectCb>> m_map;

    /**
     * Method description
     *
     *
     * @param event
     * @param cb
     */
    public static void AddListener(int event, SelectCb cb) {
        if (m_map == null) {
            m_map = new HashMap<Integer, Vector<SelectCb>>();
        }

        Integer i = new Integer(event);
        Vector<SelectCb> vec = m_map.get(i);

        if (vec == null) {
            m_map.put(i, vec = new Vector<SelectCb>());
        }

        vec.add(cb);
    }

    /**
     * Method description
     *
     *
     * @param event
     */
    public static void TriggerEvent(int event) {
        if ((event == 103) && (m_map == null)) {
            m_map = new HashMap<Integer, Vector<SelectCb>>();
        }

        if (null != m_map) {
            Vector<?> vec = m_map.get(Integer.valueOf(event));

            if (vec != null) {
                for (int i = 0; i < vec.size(); ++i) {
                    SelectCb cb = (SelectCb) vec.get(i);

                    cb.OnSelect(event, null);
                }
            }
        }

        if (event == 105) {
            m_map = null;
        }
    }
}


//~ Formatted in DD Std on 13/08/25
