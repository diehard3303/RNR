/*
 * @(#)IconMappings.java   13/08/26
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


package rnr.src.rnrconfig;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.JavaEvents;
import rnr.src.menu.menues;
import rnr.src.menu.menues.CMaterial_whithmapping;
import rnr.src.menu.menues.ctexcoord_multylayer;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class IconMappings {
    private static final String PERSON_ICONS_FILE = "people_icons.xml";
    private static final String RACE_SMALL_LOGOS_FILE = "race_logos_small.xml";
    private static final String VEHICLE_ICONS_FILE = "vehicle_icons.xml";
    private static final String RACE_LOGOS_FILE = "race_logos.xml";
    private static final String REMAP_METH = "remap";
    private static final int MAPPING_LAYER = 0;
    private static IconMappings instance = null;
    private texcoords data;
    private picturematerialParams cmparam;

    /**
     * Constructs ...
     *
     */
    public IconMappings() {
        this.data = null;
        this.cmparam = null;
    }

    private static IconMappings gInstance() {
        if (null == instance) {
            instance = new IconMappings();
        }

        return instance;
    }

    /**
     * Method description
     *
     *
     * @param id
     * @param control
     */
    public static void remapPersonIcon(String id, long control) {
        texcoords data = new texcoords("people_icons.xml", id);
        picturematerialParams cmparam = new picturematerialParams("people_icons.xml");

        JavaEvents.SendEvent(34, 0, cmparam);
        JavaEvents.SendEvent(34, 1, data);
        gInstance().data = data;
        gInstance().cmparam = cmparam;
        menues.CallMappingModifications(control, gInstance(), "remap");
        gInstance().data = null;
        gInstance().cmparam = null;
    }

    /**
     * Method description
     *
     *
     * @param id
     * @param control
     */
    public static void remapRaceLogos(String id, long control) {
        texcoords data = new texcoords("race_logos.xml", id);
        picturematerialParams cmparam = new picturematerialParams("race_logos.xml");

        JavaEvents.SendEvent(34, 0, cmparam);
        JavaEvents.SendEvent(34, 1, data);
        gInstance().data = data;
        gInstance().cmparam = cmparam;
        menues.CallMappingModifications(control, gInstance(), "remap");
        gInstance().data = null;
        gInstance().cmparam = null;
    }

    /**
     * Method description
     *
     *
     * @param id
     * @param control
     */
    public static void remapSmallRaceLogos(String id, long control) {
        texcoords data = new texcoords("race_logos_small.xml", id);
        picturematerialParams cmparam = new picturematerialParams("race_logos_small.xml");

        JavaEvents.SendEvent(34, 0, cmparam);
        JavaEvents.SendEvent(34, 1, data);
        gInstance().data = data;
        gInstance().cmparam = cmparam;
        menues.CallMappingModifications(control, gInstance(), "remap");
        gInstance().data = null;
        gInstance().cmparam = null;
    }

    /**
     * Method description
     *
     *
     * @param id
     * @param control
     */
    public static void remapVehicleIcon(String id, long control) {
        texcoords data = new texcoords("vehicle_icons.xml", id);
        picturematerialParams cmparam = new picturematerialParams("vehicle_icons.xml");

        JavaEvents.SendEvent(34, 0, cmparam);
        JavaEvents.SendEvent(34, 1, data);
        gInstance().data = data;
        gInstance().cmparam = cmparam;
        menues.CallMappingModifications(control, gInstance(), "remap");
        gInstance().data = null;
        gInstance().cmparam = null;
    }

    /**
     * Method description
     *
     *
     * @param sizex
     * @param sizey
     * @param stuff
     */
    public void remap(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        for (menues.CMaterial_whithmapping tex : stuff) {
            if (tex.tex.size() == 0) {
                continue;
            }

            if (tex.usepatch) {
                continue;
            }

            for (menues.ctexcoord_multylayer layer : tex.tex) {
                if (layer.index == 0) {
                    layer.t0x = (this.data.x / this.cmparam.resx);
                    layer.t0y = (this.data.y / this.cmparam.resy);
                    layer.t1x = ((this.data.x + this.data.sx) / this.cmparam.resx);
                    layer.t1y = (this.data.y / this.cmparam.resy);
                    layer.t2x = ((this.data.x + this.data.sx) / this.cmparam.resx);
                    layer.t2y = ((this.data.y + this.data.sy) / this.cmparam.resy);
                    layer.t3x = (this.data.x / this.cmparam.resx);
                    layer.t3y = ((this.data.y + this.data.sy) / this.cmparam.resy);
                }
            }
        }
    }

    static class picturematerialParams {
        String file;
        float resx;
        float resy;

        picturematerialParams(String file) {
            this.file = file;
        }
    }


    static class texcoords {
        String file;
        String id;
        float x;
        float y;
        float sx;
        float sy;

        texcoords(String file, String id) {
            this.file = file;
            this.id = id;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
