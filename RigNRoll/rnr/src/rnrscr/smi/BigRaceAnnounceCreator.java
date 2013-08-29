/*
 * @(#)BigRaceAnnounceCreator.java   13/08/28
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

import rnr.src.rnrscr.IMissionInformation;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class BigRaceAnnounceCreator implements IArticleCreator {
    static final long serialVersionUID = 0L;
    private final int race_uid;
    private final int type;
    private final String raceName;
    private final String shortRaceName;
    private final String startWarehouse;
    private final String finishWarehouse;
    private final int moneyPrize;
    private final int ratingThreshold;
    private final int yearstart;
    private final int monthstart;
    private final int daystart;
    private final int hourstart;
    private final int minstart;
    private final int yearfinish;
    private final int monthfinish;
    private final int dayfinish;
    private final int hourfinish;
    private final int minfinish;
    private final int yearArticleLife;
    private final int monthArticleLife;
    private final int dayArticleLife;
    private final int hourArticleLife;

    /**
     * Constructs ...
     *
     *
     * @param race_uid
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
    public BigRaceAnnounceCreator(int race_uid, int type, String raceName, String shortRaceName, String startWarehouse,
                                  String finishWarehouse, int moneyPrize, int ratingThreshold, int yearstart,
                                  int monthstart, int daystart, int hourstart, int minstart, int yearfinish,
                                  int monthfinish, int dayfinish, int hourfinish, int minfinish, int yearArticleLife,
                                  int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        this.race_uid = race_uid;
        this.type = type;
        this.raceName = raceName;
        this.shortRaceName = shortRaceName;
        this.startWarehouse = startWarehouse;
        this.finishWarehouse = finishWarehouse;
        this.moneyPrize = moneyPrize;
        this.ratingThreshold = ratingThreshold;
        this.yearstart = yearstart;
        this.monthstart = monthstart;
        this.daystart = daystart;
        this.hourstart = hourstart;
        this.minstart = minstart;
        this.yearfinish = yearfinish;
        this.monthfinish = monthfinish;
        this.dayfinish = dayfinish;
        this.hourfinish = hourfinish;
        this.minfinish = minfinish;
        this.yearArticleLife = yearArticleLife;
        this.monthArticleLife = monthArticleLife;
        this.dayArticleLife = dayArticleLife;
        this.hourArticleLife = hourArticleLife;
    }

    /**
     * Method description
     *
     *
     * @param news_name
     * @param mission_info
     *
     * @return
     */
    public Article create(String news_name, IMissionInformation mission_info) {
        Article article = new BigracePrepareInformation(news_name, mission_info, this.type, this.raceName,
                              this.shortRaceName, this.startWarehouse, this.finishWarehouse, this.moneyPrize,
                              this.ratingThreshold, this.yearstart, this.monthstart, this.daystart, this.hourstart,
                              this.minstart, this.yearfinish, this.monthfinish, this.dayfinish, this.hourfinish,
                              this.minfinish, this.yearArticleLife, this.monthArticleLife, this.dayArticleLife,
                              this.hourArticleLife);

        article.setUid(this.race_uid);

        return article;
    }
}


//~ Formatted in DD Std on 13/08/28
