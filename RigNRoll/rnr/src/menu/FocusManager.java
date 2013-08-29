/*
 * @(#)FocusManager.java   13/08/25
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

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class FocusManager {
    private static FocusManager manager = null;
    private final ArrayList<IFocusHolder> data = new ArrayList<IFocusHolder>();
    private final ArrayList<IFocusHolder> lastFocuses = new ArrayList<IFocusHolder>();

    private static FocusManager getManager() {
        if (null == manager) {
            manager = new FocusManager();
        }

        return manager;
    }

    /**
     * Method description
     *
     *
     * @param item
     */
    public static void register(IFocusHolder item) {
        FocusManager man = getManager();

        if (man.data.contains(item)) {
            return;
        }

        man.data.add(item);
        man.lastFocuses.add(item);
    }

    /**
     * Method description
     *
     *
     * @param item
     */
    public static void unRegister(IFocusHolder item) {
        FocusManager man = getManager();

        man.data.remove(item);
        man.lastFocuses.remove(item);
    }

    /**
     * Method description
     *
     *
     * @param item
     */
    public static void enterFocus(IFocusHolder item) {
        FocusManager man = getManager();

        if (!(man.lastFocuses.isEmpty())) {
            man.lastFocuses.remove(item);

            if (!(man.lastFocuses.isEmpty())) {
                IFocusHolder last = man.lastFocuses.get(man.lastFocuses.size() - 1);

                last.cbLeaveFocus();
                man.lastFocuses.remove(last);
            }
        }

        man.lastFocuses.add(item);
        item.cbEnterFocus();
    }

    /**
     * Method description
     *
     *
     * @param item
     */
    public static void leaveFocus(IFocusHolder item) {
        FocusManager man = getManager();
        IFocusHolder last = man.lastFocuses.get(man.lastFocuses.size() - 1);

        if (last.equals(item)) {
            last.cbLeaveFocus();
            man.lastFocuses.remove(item);

            if (!(man.lastFocuses.isEmpty())) {
                last = man.lastFocuses.get(man.lastFocuses.size() - 1);
                last.cbEnterFocus();
            }
        } else {
            man.lastFocuses.remove(item);
        }
    }

    /**
     * Method description
     *
     *
     * @param item
     *
     * @return
     */
    public static boolean isFocused(IFocusHolder item) {
        FocusManager man = getManager();

        if (man.lastFocuses.isEmpty()) {
            return false;
        }

        return man.lastFocuses.get(man.lastFocuses.size() - 1).equals(item);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static IFocusHolder getFocused() {
        FocusManager man = getManager();

        if (man.lastFocuses.isEmpty()) {
            return null;
        }

        return (man.lastFocuses.get(man.lastFocuses.size() - 1));
    }
}


//~ Formatted in DD Std on 13/08/25
