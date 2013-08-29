/*
 * @(#)RadioNews.java   13/08/28
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


package rnr.src.rnrscr.smi;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrorg.ActiveJournalListeners;
import rnr.src.rnrorg.JournalActiveListener;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrscr.IMissionInformation;
import rnr.src.rnrscr.MissionDialogs;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class RadioNews {
    private static HashMap<String, RadioNews> allnews = new HashMap<String, RadioNews>();
    private final String resource;

    /**
     * Constructs ...
     *
     *
     * @param mission_info
     * @param resource
     */
    public RadioNews(IMissionInformation mission_info, String resource) {
        this.resource = resource;
        MissionEventsMaker.makeRadioBreakNews(resource);
    }

    /**
     * Method description
     *
     */
    public void onAppear() {
        ActiveJournalListeners.startActiveJournals(new JournalActiveListener(this.resource));
        MissionDialogs.sayAppear(this.resource);
        ActiveJournalListeners.endActiveJournals();
        MissionDialogs.sayEnd(this.resource);
    }

    /**
     * Method description
     *
     *
     * @param resource
     */
    public static void appear(String resource) {
        if (!(allnews.containsKey(resource))) {
            return;
        }

        allnews.get(resource).onAppear();
        allnews.remove(resource);
    }

    /**
     * Method description
     *
     *
     * @param resource
     * @param news
     */
    public static void add(String resource, RadioNews news) {
        allnews.put(resource, news);
    }

    /**
     * Method description
     *
     *
     * @param resource
     */
    public static void remove(String resource) {
        if (!(allnews.containsKey(resource))) {
            return;
        }

        allnews.remove(resource);
    }
}


//~ Formatted in DD Std on 13/08/28
