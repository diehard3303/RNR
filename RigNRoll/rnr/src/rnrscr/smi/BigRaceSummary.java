/*
 * @(#)BigRaceSummary.java   13/08/28
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
public class BigRaceSummary extends Article {

    /** Field description */
    public String raceName = "race no name";

    /** Field description */
    public String notLocalizedraceName = "race no name";

    /** Field description */
    public String finishWarehouse = "finish no name";

    /** Field description */
    public String goldDriverName = "gold no name";

    /** Field description */
    public String silverDriverName = "silver no name";

    /** Field description */
    public String bronzeDriverName = "bronze no name";

    /** Field description */
    public int place = 1;

    /** Field description */
    public boolean bAnyPrize = true;
    private boolean hasNonGoldPlayersInSummary = true;

    /** Field description */
    public int type;

    /**
     * Constructs ...
     *
     *
     * @param type
     * @param raceName
     * @param finishWarehouse
     * @param goldDriverName
     * @param bIsIgrokPrizer
     * @param igroksPlace
     * @param yearArticleLife
     * @param monthArticleLife
     * @param dayArticleLife
     * @param hourArticleLife
     */
    public BigRaceSummary(int type, String raceName, String finishWarehouse, String goldDriverName,
                          boolean bIsIgrokPrizer, int igroksPlace, int yearArticleLife, int monthArticleLife,
                          int dayArticleLife, int hourArticleLife) {
        super(type);
        this.notLocalizedraceName = raceName;
        this.type = type;
        this.raceName = loc.getBigraceFullName(raceName);
        this.finishWarehouse = finishWarehouse;
        this.goldDriverName = goldDriverName;
        this.place = igroksPlace;
        this.bAnyPrize = bIsIgrokPrizer;
        this.hasNonGoldPlayersInSummary = false;
        setDueToTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
    }

    /**
     * Constructs ...
     *
     *
     * @param type
     * @param raceName
     * @param finishWarehouse
     * @param goldDriverName
     * @param silverDriverName
     * @param bronzeDriverName
     * @param bIsIgrokPrizer
     * @param igroksPlace
     * @param yearArticleLife
     * @param monthArticleLife
     * @param dayArticleLife
     * @param hourArticleLife
     */
    public BigRaceSummary(int type, String raceName, String finishWarehouse, String goldDriverName,
                          String silverDriverName, String bronzeDriverName, boolean bIsIgrokPrizer, int igroksPlace,
                          int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        super(type);
        this.notLocalizedraceName = raceName;
        this.type = type;
        this.raceName = loc.getBigraceFullName(raceName);
        this.finishWarehouse = finishWarehouse;
        this.goldDriverName = goldDriverName;
        this.silverDriverName = silverDriverName;
        this.bronzeDriverName = bronzeDriverName;
        this.place = igroksPlace;
        this.bAnyPrize = bIsIgrokPrizer;
        this.hasNonGoldPlayersInSummary = true;
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
        String body_node = "";

        switch (this.type) {
         case 1 :
             body_node = (this.hasNonGoldPlayersInSummary)
                         ? "BIGRACESUMMARY BODY 1"
                         : "BIGRACESUMMARY SHORT BODY 1";

             break;

         case 2 :
             body_node = (this.hasNonGoldPlayersInSummary)
                         ? "BIGRACESUMMARY BODY 2"
                         : "BIGRACESUMMARY SHORT BODY 2";

             break;

         case 3 :
             body_node = (this.hasNonGoldPlayersInSummary)
                         ? "BIGRACESUMMARY BODY 3"
                         : "BIGRACESUMMARY SHORT BODY 3";

             break;

         case 4 :
             body_node = (this.hasNonGoldPlayersInSummary)
                         ? "BIGRACESUMMARY BODY 4"
                         : "BIGRACESUMMARY SHORT BODY 4";
        }

        KeyPair[] template = { new KeyPair("DESTINATION", this.finishWarehouse),
                               new KeyPair("FULLRACENAME", this.raceName),
                               new KeyPair("DRIVERNAME", this.goldDriverName),
                               new KeyPair("DRIVERNAME1", this.silverDriverName),
                               new KeyPair("DRIVERNAME2", this.bronzeDriverName) };

        return MacroKit.Parse(loc.getNewspaperString(body_node), template);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getHeader() {
        String header_node = "";

        switch (this.type) {
         case 1 :
             header_node = "BIGRACESUMMARY HEADER 1";

             break;

         case 2 :
             header_node = "BIGRACESUMMARY HEADER 2";

             break;

         case 3 :
             header_node = "BIGRACESUMMARY HEADER 3";

             break;

         case 4 :
             header_node = "BIGRACESUMMARY HEADER 4";
        }

        return loc.getNewspaperString(header_node);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isRaceSummary() {
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
        String ret = "bigraceshot_";

        switch (this.type) {
         case 1 :
             ret = ret + "04_";

             break;

         case 2 :
             ret = ret + "03_";

             break;

         case 3 :
             ret = ret + "02_";

             break;

         case 4 :
             ret = ret + "01_";
        }

        ret = ret + this.notLocalizedraceName;

        if (this.bAnyPrize) {
            ;
        }

        switch (this.place) {
         case 0 :
             ret = ret + "_gold";

             break;

         case 1 :
             ret = ret + "_silver";

             break;

         case 2 :
             ret = ret + "_bronze";

             break;

         default :
             ret = ret + "noprize";

             break;
        }

        return ret;
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
