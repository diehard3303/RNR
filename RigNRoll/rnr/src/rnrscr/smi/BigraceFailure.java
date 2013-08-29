/*
 * @(#)BigraceFailure.java   13/08/28
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

import rnr.src.menu.KeyPair;
import rnr.src.menu.MacroKit;
import rnr.src.rnrcore.loc;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class BigraceFailure extends Article {

    /** Field description */
    public String raceName = "no name";

    /** Field description */
    public String shortRaceName = "short no name";

    /** Field description */
    public String startWarehouse = "start no name";

    /**
     * Constructs ...
     *
     *
     * @param raceName
     * @param shortRaceName
     * @param startWarehouse
     * @param yearArticleLife
     * @param monthArticleLife
     * @param dayArticleLife
     * @param hourArticleLife
     */
    public BigraceFailure(String raceName, String shortRaceName, String startWarehouse, int yearArticleLife,
                          int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        this.raceName = loc.getBigraceFullName(raceName);
        this.shortRaceName = loc.getBigraceShortName(shortRaceName);
        this.startWarehouse = startWarehouse;
        setDueToTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getBody() {
        KeyPair[] template = { new KeyPair("FULLRACENAME", this.raceName), new KeyPair("SOURCE", this.startWarehouse) };

        return MacroKit.Parse(loc.getNewspaperString("BIGRACEFAILURE BODY"), template);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getHeader() {
        KeyPair[] template = { new KeyPair("SHORTRACENAME", this.shortRaceName) };

        return MacroKit.Parse(loc.getNewspaperString("BIGRACEFAILURE HEADER"), template);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isRaceAnnouncement() {
        return true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getTexture() {
        return "tex_menu_News_Rally_failed";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getPriority() {
        return 10;
    }
}


//~ Formatted in DD Std on 13/08/28
