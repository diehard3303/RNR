/*
 * @(#)NewspaperQuest.java   13/08/28
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

import rnr.src.rnrcore.loc;
import rnr.src.rnrorg.ActiveJournalListeners;
import rnr.src.rnrorg.JournalActiveListener;
import rnr.src.rnrscr.IMissionInformation;
import rnr.src.rnrscr.MissionDialogs;

//~--- JDK imports ------------------------------------------------------------

import java.util.Random;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class NewspaperQuest extends Article {
    static Random rnd = new Random();
    private final IMissionInformation mission_info;
    private final String name;
    private final String loc_name;
    private final String loc_header_name;
    private String textureName;

    /**
     * Constructs ...
     *
     *
     * @param name
     * @param mission_info
     */
    public NewspaperQuest(String name, IMissionInformation mission_info) {
        this.name = name;
        this.mission_info = mission_info;
        this.loc_name = name;
        this.loc_header_name = this.loc_name + " header";

        int num = (int) (rnd.nextDouble() * 7.0D);

        switch (num) {
         case 0 :
             this.textureName = "tex_menu_News01";

             break;

         case 1 :
             this.textureName = "tex_menu_News02";

             break;

         case 2 :
             this.textureName = "tex_menu_News03";

             break;

         case 3 :
             this.textureName = "tex_menu_News04";

             break;

         case 4 :
             this.textureName = "tex_menu_News05";

             break;

         case 5 :
             this.textureName = "tex_menu_News06";

             break;

         case 6 :
             this.textureName = "tex_menu_News07";
        }
    }

    /**
     * Constructs ...
     *
     *
     * @param type
     * @param name
     * @param mission_info
     */
    public NewspaperQuest(int type, String name, IMissionInformation mission_info) {
        super(type);
        this.name = name;
        this.mission_info = mission_info;
        this.loc_name = name;
        this.loc_header_name = this.loc_name + " header";

        int num = (int) (rnd.nextDouble() * 7.0D);

        switch (num) {
         case 0 :
             this.textureName = "tex_menu_News01";

             break;

         case 1 :
             this.textureName = "tex_menu_News02";

             break;

         case 2 :
             this.textureName = "tex_menu_News03";

             break;

         case 3 :
             this.textureName = "tex_menu_News04";

             break;

         case 4 :
             this.textureName = "tex_menu_News05";

             break;

         case 5 :
             this.textureName = "tex_menu_News06";

             break;

         case 6 :
             this.textureName = "tex_menu_News07";
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void readArticle() {
        ActiveJournalListeners.startActiveJournals(new JournalActiveListener(this.name));
        MissionDialogs.sayAppear(this.name);
        ActiveJournalListeners.endActiveJournals();
        MissionDialogs.sayEnd(this.name);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getHeader() {
        return loc.getNewspaperString(this.loc_header_name);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getBody() {
        return loc.getNewspaperString(this.loc_name);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public IMissionInformation getMissionInfo() {
        return this.mission_info;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public NewspaperQuest isQuest() {
        return this;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getTexture() {
        return this.textureName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getPriority() {
        return 20;
    }
}


//~ Formatted in DD Std on 13/08/28
