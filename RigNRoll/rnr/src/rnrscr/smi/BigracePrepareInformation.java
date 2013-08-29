/*
 * @(#)BigracePrepareInformation.java   13/08/28
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
import rnr.src.menuscript.Converts;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.loc;
import rnr.src.rnrscr.IMissionInformation;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class BigracePrepareInformation extends NewspaperQuest {
    private String raceName = "race no name";
    private String shortRaceName = "short no name";
    private String startWarehouse = "start no name";
    private String finishWarehouse = "finish no name";
    private CoreTime startTime = null;
    private CoreTime finishTime = null;
    private final int type;
    private final int moneyPrize;
    private final int ratingThreshold;

    /**
     * Constructs ...
     *
     *
     * @param news_name
     * @param mission_info
     * @param type
     * @param raceName
     * @param shortRaceName
     * @param startWarehouse
     * @param finishWarehouse
     * @param moneyPrize
     * @param ratingThreshold
     * @param yearstart
     * @param monthstart
     * @param daystart
     * @param hourstart
     * @param minstart
     * @param yearfinish
     * @param monthfinish
     * @param dayfinish
     * @param hourfinish
     * @param minfinish
     * @param yearArticleLife
     * @param monthArticleLife
     * @param dayArticleLife
     * @param hourArticleLife
     */
    public BigracePrepareInformation(String news_name, IMissionInformation mission_info, int type, String raceName,
                                     String shortRaceName, String startWarehouse, String finishWarehouse,
                                     int moneyPrize, int ratingThreshold, int yearstart, int monthstart, int daystart,
                                     int hourstart, int minstart, int yearfinish, int monthfinish, int dayfinish,
                                     int hourfinish, int minfinish, int yearArticleLife, int monthArticleLife,
                                     int dayArticleLife, int hourArticleLife) {
        super(type, news_name, mission_info);
        this.type = type;
        this.raceName = loc.getBigraceFullName(raceName);
        this.shortRaceName = loc.getBigraceShortName(shortRaceName);
        this.startWarehouse = loc.getCityName(startWarehouse);
        this.finishWarehouse = loc.getCityName(finishWarehouse);
        this.moneyPrize = moneyPrize;
        this.ratingThreshold = ratingThreshold;
        setStartTime(yearstart, monthstart, daystart, hourstart, minstart);
        setfinishTime(yearfinish, monthfinish, dayfinish, hourfinish, minfinish);
        setDueToTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
    }

    private void setStartTime(int year, int month, int day, int hour, int minut) {
        this.startTime = new CoreTime(year, month, day, hour, minut);
    }

    private void setfinishTime(int year, int month, int day, int hour, int minut) {
        this.finishTime = new CoreTime(year, month, day, hour, minut);
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
             body_node = "BIGRACEPREPARE BODY 1";

             break;

         case 2 :
             body_node = "BIGRACEPREPARE BODY 2";

             break;

         case 3 :
             body_node = "BIGRACEPREPARE BODY 3";

             break;

         case 4 :
             body_node = "BIGRACEPREPARE BODY 4";
        }

        KeyPair[] template = {
            new KeyPair("DESTINATION", this.finishWarehouse), new KeyPair("SOURCE", this.startWarehouse),
            new KeyPair("PRIZE", "" + this.moneyPrize), new KeyPair("FULLRACENAME", "" + this.raceName),
            new KeyPair("SHORTRACENAME", "" + this.shortRaceName), new KeyPair("RATING", "" + this.ratingThreshold)
        };
        String ret = MacroKit.Parse(loc.getNewspaperString(body_node), template);
        String ret1 = Converts.ConvertDateAbsolute(ret, this.startTime.gMonth(), this.startTime.gDate(),
                          this.startTime.gYear(), this.startTime.gHour(), this.startTime.gMinute());
        KeyPair[] pair = { new KeyPair("FULL_DATE1", "FULL_DATE") };
        String source1 = MacroKit.Parse(ret1, pair);
        String ret2 = Converts.ConvertDateAbsolute(source1, this.finishTime.gMonth(), this.finishTime.gDate(),
                          this.finishTime.gYear(), this.finishTime.gHour(), this.finishTime.gMinute());

        return ret2;
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
             header_node = "BIGRACEPREPARE HEADER 1";

             break;

         case 2 :
             header_node = "BIGRACEPREPARE HEADER 2";

             break;

         case 3 :
             header_node = "BIGRACEPREPARE HEADER 3";

             break;

         case 4 :
             header_node = "BIGRACEPREPARE HEADER 4";
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
        switch (this.type) {
         case 1 :
             return "tex_menu_News_Rally04";

         case 2 :
             return "tex_menu_News_Rally03";

         case 3 :
             return "tex_menu_News_Rally04";

         case 4 :
             return "tex_menu_News_Rally01";
        }

        return "error";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getPriority() {
        return 25;
    }
}


//~ Formatted in DD Std on 13/08/28
