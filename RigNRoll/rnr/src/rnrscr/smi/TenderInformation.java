/*
 * @(#)TenderInformation.java   13/08/28
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
import rnr.src.rnrconfig.WarehouseInformation;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.loc;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class TenderInformation extends Article {
    static Random rnd = new Random();
    private String destinationWarehouse = "destination no name";
    @SuppressWarnings("unchecked")
    private final ArrayList<String> warehouses = new ArrayList<String>();
    private CoreTime timeEnd = null;
    private FeeMultiplier mulpiplier;
    private String textureName;

    /**
     * Constructs ...
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
    public TenderInformation(String destinationWarehouse, Vector baseNames, int multiplier, int yearArticleLife,
                             int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        this.destinationWarehouse = destinationWarehouse;

        Iterator iter = baseNames.iterator();

        while (iter.hasNext()) {
            addWarehouse((String) iter.next());
        }

        this.timeEnd = new CoreTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife, 0);
        setDueToTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);

        switch (multiplier) {
         case 2 :
             this.mulpiplier = FeeMultiplier.DOUBLE;

             break;

         case 3 :
             this.mulpiplier = FeeMultiplier.TRIPLE;

             break;

         case 4 :
             this.mulpiplier = FeeMultiplier.QUADRUPLE;

             break;

         default :
             eng.err("TenderInformation multiplier has wrong multiplier parametr.");
        }

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
     * Enum description
     *
     */
    public static enum FeeMultiplier {
        DOUBLE, TRIPLE, QUADRUPLE;

        /**
         * Method description
         *
         *
         * @return
         */
        public static final FeeMultiplier[] values() {
            return ((FeeMultiplier[]) $VALUES.clone());
        }
    }

    private void addWarehouse(String wh_name) {
        this.warehouses.add(wh_name);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getBody() {
        String warehouseRealName = new WarehouseInformation(this.destinationWarehouse).getRealName();
        String warehouses_str = "";

        for (int i = 0; i < this.warehouses.size(); ++i) {
            warehouses_str = warehouses_str + new WarehouseInformation(this.warehouses.get(i)).getRealName();

            if (i != this.warehouses.size() - 1) {
                warehouses_str = warehouses_str + ", ";
            }
        }

        String mult_str = "";

        switch (rnr.src.rnrscr.smi.TenderInformation.FeeMultiplier[this.mulpiplier.ordinal()]) {
         case 1 :
             mult_str = loc.getNewspaperString("MULTIPLIER DOUBLE");

             break;

         case 2 :
             mult_str = loc.getNewspaperString("MULTIPLIER TRIPLE");

             break;

         case 3 :
             mult_str = loc.getNewspaperString("MULTIPLIER QUADRUPLE");
        }

        KeyPair[] template = { new KeyPair("DESTINATION", warehouseRealName), new KeyPair("WAREHOUSES", warehouses_str),
                               new KeyPair("MULTIPLIER", mult_str) };
        String ret = MacroKit.Parse(loc.getNewspaperString("TENDER BODY"), template);

        return Converts.ConverTimeAbsolute(ret, this.timeEnd.gHour(), this.timeEnd.gMinute());
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getHeader() {
        return loc.getNewspaperString("TENDER HEADER");
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean isNews() {
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
        return this.textureName;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getDestinationWarehouse() {
        return this.destinationWarehouse;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public FeeMultiplier getMulpiplier() {
        return this.mulpiplier;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public CoreTime getTimeEnd() {
        return this.timeEnd;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<String> getWarehouses() {
        return this.warehouses;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getPriority() {
        return 15;
    }
}


//~ Formatted in DD Std on 13/08/28
