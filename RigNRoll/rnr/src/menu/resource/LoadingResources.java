/*
 * @(#)LoadingResources.java   13/08/26
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

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class LoadingResources {
    private final HashMap<String, MenuLoadPack> loadingPacks;

    /**
     * Constructs ...
     *
     */
    public LoadingResources() {
        this.loadingPacks = new HashMap<String, MenuLoadPack>();
    }

    /**
     * Method description
     *
     *
     * @param menuID
     */
    public void startMenuLoading(String menuID) {
        MenuLoadPack pack = ResourcesDescription.getInstance().getPack(menuID);

        if (null != pack) {
            this.loadingPacks.put(menuID, pack);
        }
    }

    /**
     * Method description
     *
     *
     * @param menuID
     */
    public void leaveMenu(String menuID) {
        if (!(this.loadingPacks.containsKey(menuID))) {
            return;
        }

        this.loadingPacks.remove(menuID);
    }

    /**
     * Method description
     *
     *
     * @param menuID
     * @param resourceID
     *
     * @return
     */
    public IMenuResources getMenuResource(String menuID, String resourceID) {
        if (!(this.loadingPacks.containsKey(menuID))) {
            return null;
        }

        return this.loadingPacks.get(menuID).getResource(resourceID);
    }
}


//~ Formatted in DD Std on 13/08/26
