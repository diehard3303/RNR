/*
 * @(#)Article.java   13/08/28
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

import rnr.src.rnrcore.CoreTime;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public abstract class Article implements IArticle {
    private CoreTime dueToTime = null;
    private int paperindex = 1;
    private int uid = -1;

    /**
     * Constructs ...
     *
     */
    public Article() {}

    /**
     * Constructs ...
     *
     *
     * @param paperindex
     */
    public Article(int paperindex) {
        this.paperindex = paperindex;
    }

    final boolean sameNews(int uid) {
        return ((this.uid != -1) && (uid == this.uid));
    }

    final boolean sameNews(Article article) {
        return sameNews(article.uid);
    }

    final void setUid(int uid) {
        this.uid = uid;
    }

    protected final void setDueToTime(int year, int month, int day, int hour) {
        this.dueToTime = new CoreTime(year, month, day, hour, 0);
    }

    /**
     * Method description
     *
     *
     * @param currentTime
     *
     * @return
     */
    public final boolean isOldArticle(CoreTime currentTime) {
        return ((this.dueToTime != null) && (currentTime.moreThan(this.dueToTime) >= 0));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isNews() {
        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isRaceAnnouncement() {
        return false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isRaceSummary() {
        return false;
    }

    /**
     * Method description
     *
     */
    @Override
    public void readArticle() {}

    /**
     * Method description
     *
     *
     * @return
     */
    public NewspaperQuest isQuest() {
        return null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public final int paperIndex() {
        return this.paperindex;
    }
}


//~ Formatted in DD Std on 13/08/28
