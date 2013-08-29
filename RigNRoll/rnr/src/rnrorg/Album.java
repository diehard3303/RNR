/*
 * @(#)Album.java   13/08/26
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


package rnr.src.rnrorg;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.loc;
import rnr.src.xmlserialization.CoreTimeSerialization;
import rnr.src.xmlserialization.Helper;
import rnr.src.xmlutils.Node;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Album {
    private static Album instance = null;
    @SuppressWarnings("unchecked")
    private ArrayList<Item> items = new ArrayList();

    /**
     * Method description
     *
     *
     * @return
     */
    public static Album getInstance() {
        if (null == instance) {
            instance = new Album();
        }

        return instance;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "albumElement";
    }

    /**
     * Method description
     *
     *
     * @param description
     * @param text
     * @param date
     * @param material
     *
     * @return
     */
    public Item add(String description, String text, CoreTime date, String material) {
        Item ret = new Item(description, text, date, material, false);

        this.items.add(ret);

        return ret;
    }

    /**
     * Method description
     *
     *
     * @param description
     * @param text
     * @param date
     * @param material
     */
    public void addBigRaceShot(String description, String text, CoreTime date, String material) {
        this.items.add(new Item(description, text, date, material, true));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public ArrayList<Item> getAll() {
        return this.items;
    }

    /**
     * Method description
     *
     *
     * @param i
     */
    public void setAll(ArrayList<Item> i) {
        this.items = i;
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static class Item {

        /** Field description */
        public String description;

        /** Field description */
        public String locdesc;

        /** Field description */
        public String text;

        /** Field description */
        public String loctext;

        /** Field description */
        public CoreTime date;

        /** Field description */
        public String material;

        /** Field description */
        public boolean is_bigrace_item;

        Item(String description, String text, CoreTime date, String material, boolean is_bigrace_item) {
            this.date = date;
            this.material = material;
            this.is_bigrace_item = is_bigrace_item;
            this.description = description;
            this.text = text;

            if (is_bigrace_item) {
                this.locdesc = loc.getBigraceShortName(description);
                this.loctext = AlbumFinishWarehouse.locText(description, text);
            } else {
                this.locdesc = loc.getOrgString(description);
                this.loctext = loc.getMissionSuccesPictureText(text);
            }
        }

        /**
         * Method description
         *
         *
         * @param stream
         */
        public void serializeXML(PrintStream stream) {
            Helper.openNode(stream, Album.getNodeName());
            CoreTimeSerialization.serializeXML(this.date, stream);

            List attributes = Helper.createSingleAttribute("albumElementDescr", this.description);

            Helper.addAttribute("albumElementText", this.text, attributes);
            Helper.addAttribute("albumElementMaterial", this.material, attributes);
            Helper.addAttribute("albumElement", this.is_bigrace_item, attributes);
            Helper.printClosedNodeWithAttributes(stream, "albumElementValue", attributes);
            Helper.closeNode(stream, Album.getNodeName());
        }

        /**
         * Method description
         *
         *
         * @param node
         *
         * @return
         */
        public static Item deserializeXML(Node node) {
            Node timeNode = node.getNamedChild(CoreTimeSerialization.getNodeName());
            CoreTime d = new CoreTime();

            if (null != timeNode) {
                d = CoreTimeSerialization.deserializeXML(timeNode);
            }

            String des = null;
            String t = null;
            String mat = null;
            boolean isbgr = false;
            Node body = node.getNamedChild("albumElementValue");

            if (null != body) {
                des = body.getAttribute("albumElementDescr");
                t = body.getAttribute("albumElementText");
                mat = body.getAttribute("albumElementMaterial");

                String str = body.getAttribute("albumElement");

                isbgr = Helper.ConvertToBooleanAndWarn(str, "albumElement", "Album on deserializeXML");
            }

            Item item = new Item(des, t, d, mat, isbgr);

            return item;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
