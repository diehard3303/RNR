/*
 * @(#)Newspapers.java   13/08/28
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
import rnr.src.menuscript.BarMenu;
import rnr.src.menuscript.BarMenu.BarEntry;
import rnr.src.players.actorveh;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrscenario.missions.BigRaceAnnounceMission;
import rnr.src.rnrscr.IMissionInformation;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class Newspapers {
    private static Newspapers theonlynespaper = null;
    private final ArrayList<Article> articles;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings("unchecked")
    public Newspapers() {
        this.articles = new ArrayList<Article>();
    }

    private static Newspapers getTheonly() {
        if (null == theonlynespaper) {
            theonlynespaper = new Newspapers();
        }

        return theonlynespaper;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        theonlynespaper = null;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static Newspapers getInstance() {
        return getTheonly();
    }

    private void addElement(Article acticle) {
        this.articles.add(acticle);
        Collections.sort(this.articles, new ArticleSorter());
    }

    protected void clearOldArticles() {
        CoreTime time = new CoreTime();
        Iterator<Article> iter = this.articles.iterator();

        while (iter.hasNext()) {
            Article s = iter.next();

            if (s.isOldArticle(time)) {
                iter.remove();
            }
        }
    }

    protected void clearSameNewsArticles(int uid) {
        Iterator<Article> iter = this.articles.iterator();

        while (iter.hasNext()) {
            Article s = iter.next();

            if (s.sameNews(uid)) {
                iter.remove();
            }
        }
    }

    protected void clearSameNewsArticles(Article article) {
        Iterator<Article> iter = this.articles.iterator();

        while (iter.hasNext()) {
            Article s = iter.next();

            if (s.sameNews(article)) {
                iter.remove();
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int numNews() {
        return getTheonly().articles.size();
    }

    /**
     * Method description
     *
     *
     * @param point_name
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static BarMenu.BarEntry[] getTheOnlyNewsPaper_BarEntries(String point_name) {
        getTheonly().clearOldArticles();

        if (null == point_name) {
            return new BarMenu.BarEntry[0];
        }

        ArrayList list_entries = new ArrayList();
        Iterator iter = getTheonly().articles.iterator();

        while (iter.hasNext()) {
            Article s = (Article) iter.next();
            NewspaperQuest quest = s.isQuest();

            if (null == quest) {
                BarMenu.BarEntry bar_article = new BarMenu.BarEntry();

                bar_article.article = s;
                bar_article.papertext = s.getBody();
                bar_article.headline = s.getHeader();

                if (s.isNews()) {
                    bar_article.type = 0;
                } else if (s.isRaceAnnouncement()) {
                    bar_article.type = 1;
                } else if (s.isRaceSummary()) {
                    bar_article.type = 2;
                }

                bar_article.paperindex = s.paperIndex();
                bar_article.keys = new KeyPair[0];
                bar_article.texture_name = s.getTexture();
                list_entries.add(bar_article);
            } else if ((s.isRaceAnnouncement())
                       || ((quest.getMissionInfo().hasPoint())
                           && (quest.getMissionInfo().getPointName().compareTo(point_name) == 0))) {
                if (s.isRaceAnnouncement()) {
                    quest.getMissionInfo().setPointName(point_name);
                }

                BarMenu.BarEntry bar_article = new BarMenu.BarEntry();

                bar_article.article = s;
                bar_article.papertext = s.getBody();
                bar_article.headline = s.getHeader();

                if (s.isNews()) {
                    bar_article.type = 0;
                } else if (s.isRaceAnnouncement()) {
                    bar_article.type = 1;
                } else if (s.isRaceSummary()) {
                    bar_article.type = 2;
                }

                bar_article.texture_name = s.getTexture();
                bar_article.paperindex = s.paperIndex();
                bar_article.keys = new KeyPair[0];
                list_entries.add(bar_article);
            }
        }

        BarMenu.BarEntry[] entries = new BarMenu.BarEntry[list_entries.size()];
        int count = 0;

        for (BarMenu.BarEntry entr : list_entries) {
            entries[(count++)] = entr;
        }

        return entries;
    }

    /**
     * Method description
     *
     *
     * @param race_uid
     * @param raceName
     * @param shortRaceName
     * @param startWarehouse
     * @param yearArticleLife
     * @param monthArticleLife
     * @param dayArticleLife
     * @param hourArticleLife
     */
    public static void addBigraceFailure(int race_uid, String raceName, String shortRaceName, String startWarehouse,
            int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        BigRaceAnnounceMission.clearAnnounced(race_uid);
        getTheonly().clearSameNewsArticles(race_uid);

        Article article = new BigraceFailure(raceName, shortRaceName, startWarehouse, yearArticleLife,
                              monthArticleLife, dayArticleLife, hourArticleLife);

        article.setUid(race_uid);
        getTheonly().addElement(article);
    }

    /**
     * Method description
     *
     *
     * @param race_uid
     * @param raceName
     * @param startWarehouse
     * @param numParticipants
     * @param firstPositionDriverName
     * @param yearArticleLife
     * @param monthArticleLife
     * @param dayArticleLife
     * @param hourArticleLife
     */
    public static void addBigraceStartInformation(int race_uid, String raceName, String startWarehouse,
            int numParticipants, String firstPositionDriverName, int yearArticleLife, int monthArticleLife,
            int dayArticleLife, int hourArticleLife) {
        BigRaceAnnounceMission.clearAnnounced(race_uid);
        getTheonly().clearSameNewsArticles(race_uid);

        Article article = new BigraceStartInformation(raceName, startWarehouse, numParticipants,
                              firstPositionDriverName, yearArticleLife, monthArticleLife, dayArticleLife,
                              hourArticleLife);

        article.setUid(race_uid);
        getTheonly().addElement(article);
    }

    /**
     * Method description
     *
     *
     * @param race_uid
     * @param type
     * @param raceName
     * @param goldPrizer
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
    public static void addBigRaceSummary(int race_uid, int type, String raceName, actorveh goldPrizer,
            String finishWarehouse, String goldDriverName, String silverDriverName, String bronzeDriverName,
            boolean bIsIgrokPrizer, int igroksPlace, int yearArticleLife, int monthArticleLife, int dayArticleLife,
            int hourArticleLife) {
        BigRaceAnnounceMission.clearAnnounced(race_uid);
        getTheonly().clearSameNewsArticles(race_uid);

        Article article = new BigRaceSummary(type, raceName, finishWarehouse, goldDriverName, silverDriverName,
                              bronzeDriverName, bIsIgrokPrizer, igroksPlace, yearArticleLife, monthArticleLife,
                              dayArticleLife, hourArticleLife);

        article.setUid(race_uid);
        getTheonly().addElement(article);
    }

    /**
     * Method description
     *
     *
     * @param race_uid
     * @param type
     * @param raceName
     * @param goldPrizer
     * @param finishWarehouse
     * @param goldDriverName
     * @param bIsIgrokPrizer
     * @param igroksPlace
     * @param yearArticleLife
     * @param monthArticleLife
     * @param dayArticleLife
     * @param hourArticleLife
     */
    public static void addBigRaceSummary(int race_uid, int type, String raceName, actorveh goldPrizer,
            String finishWarehouse, String goldDriverName, boolean bIsIgrokPrizer, int igroksPlace,
            int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        BigRaceAnnounceMission.clearAnnounced(race_uid);
        getTheonly().clearSameNewsArticles(race_uid);

        Article article = new BigRaceSummary(type, raceName, finishWarehouse, goldDriverName, bIsIgrokPrizer,
                              igroksPlace, yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);

        article.setUid(race_uid);
        getTheonly().addElement(article);
    }

    /**
     * Method description
     *
     *
     * @param destinationWarehouse
     * @param baseNames
     * @param multiplier
     * @param yearArticleLife
     * @param monthArticleLife
     * @param dayArticleLife
     * @param hourArticleLife
     */
    @SuppressWarnings("rawtypes")
    public static void addTenderInformation(String destinationWarehouse, Vector baseNames, int multiplier,
            int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        getTheonly().addElement(new TenderInformation(destinationWarehouse, baseNames, multiplier, yearArticleLife,
                monthArticleLife, dayArticleLife, hourArticleLife));
    }

    /**
     * Method description
     *
     *
     * @param article
     */
    public static void addMissionNewsReadyArticle(Article article) {
        getTheonly().clearSameNewsArticles(article);
        getTheonly().addElement(article);
    }

    /**
     * Method description
     *
     *
     * @param news_name
     * @param mission_info
     */
    public static void addMissionNews(String news_name, IMissionInformation mission_info) {
        getTheonly().addElement(new NewspaperQuest(news_name, mission_info));
    }

    /**
     * Method description
     *
     *
     * @param news_name
     */
    public static void removeMissionNews(String news_name) {
        for (Article article : getTheonly().articles) {
            NewspaperQuest quest = article.isQuest();

            if ((null != quest) && (quest.getMissionInfo().getDialogName().compareTo(news_name) == 0)) {
                getTheonly().articles.remove(article);

                return;
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<Article> getArticles() {
        return this.articles;
    }
}


//~ Formatted in DD Std on 13/08/28
