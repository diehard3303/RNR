/*
 * @(#)xmlcontrols.java   13/08/28
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


package rnr.src.menu;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscr.animation;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class xmlcontrols extends animation {

    /** Field description */
    public static final int STATE_USER = 0;

    /** Field description */
    public static final int STATE_COLOR = 1;

    /** Field description */
    public static final int STATE_CAMERA = 2;

    /** Field description */
    public static final int VIEW_INTERIOR = 0;

    /** Field description */
    public static final int VIEW_EXTERIOR = 1;

    /** Field description */
    public static final int VIEW_MODEL = 2;

    /** Field description */
    public static final int VIEWFLAG_LOAD = 2;

    /** Field description */
    public static final int VIEWFLAG_CAMERALOAD = 4;

    /**
     * Method description
     *
     *
     * @param active
     * @param pressed
     *
     * @return
     */
    public int GetIndex(boolean active, boolean pressed) {
        if (pressed) {
            return 2;
        }

        return ((active)
                ? 1
                : 0);
    }

    /**
     * Method description
     *
     *
     * @param pox
     * @param poy
     * @param sizex
     * @param sizey
     * @param stuff
     */
    public void Complex3(int pox, int poy, int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        int max = 0;

        for (int i = 0; i < stuff.length; ++i) {
            if (!(stuff[i].usepatch)) {
                continue;
            }

            if (stuff[i]._state <= max) {
                continue;
            }

            max = stuff[i]._state;
        }

        ++max;

        int[][] leftwidths = new int[max][];
        int[][] rightwidths = new int[max][];

        for (int i = 0; i < max; ++i) {
            leftwidths[i] = new int[3];
            rightwidths[i] = new int[3];
        }

        for (int i = 0; i < stuff.length; ++i) {
            if (!(stuff[i].usepatch)) {
                continue;
            }

            int activeindex = GetIndex(stuff[i]._active, stuff[i].pressed);

            if (stuff[i]._patch.tip.equals("left")) {
                leftwidths[stuff[i]._state][activeindex] = stuff[i]._patch.sx;
            }

            if (!(stuff[i]._patch.tip.equals("right"))) {
                continue;
            }

            rightwidths[stuff[i]._state][activeindex] = stuff[i]._patch.sx;
        }

        for (int i = 0; i < stuff.length; ++i) {
            if (!(stuff[i].usepatch)) {
                continue;
            }

            int activeindex = GetIndex(stuff[i]._active, stuff[i].pressed);
            int leftwidth = leftwidths[stuff[i]._state][activeindex];
            int rightwidth = rightwidths[stuff[i]._state][activeindex];

            if (stuff[i]._patch.tip.equals("left")) {
                stuff[i]._patch.x = 0;
                stuff[i]._patch.y = 0;
                stuff[i]._patch.sx = leftwidth;
                stuff[i]._patch.sy = sizey;
            }

            if (stuff[i]._patch.tip.equals("center")) {
                stuff[i]._patch.x = leftwidth;
                stuff[i]._patch.y = 0;
                stuff[i]._patch.sx = (sizex - leftwidth - rightwidth);
                stuff[i]._patch.sy = sizey;
            }

            if (!(stuff[i]._patch.tip.equals("right"))) {
                continue;
            }

            stuff[i]._patch.x = (sizex - rightwidth);
            stuff[i]._patch.y = 0;
            stuff[i]._patch.sx = rightwidth;
            stuff[i]._patch.sy = sizey;
        }
    }

    /**
     * Method description
     *
     *
     * @param pox
     * @param poy
     * @param sizex
     * @param sizey
     * @param stuff
     */
    public void Complex3Vert(int pox, int poy, int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        int max = 0;

        for (int i = 0; i < stuff.length; ++i) {
            if (!(stuff[i].usepatch)) {
                continue;
            }

            if (stuff[i]._state <= max) {
                continue;
            }

            max = stuff[i]._state;
        }

        ++max;

        int[][] upsize = new int[max][];
        int[][] downsize = new int[max][];

        for (int i = 0; i < max; ++i) {
            upsize[i] = new int[2];
            downsize[i] = new int[2];
        }

        for (int i = 0; i < stuff.length; ++i) {
            if (!(stuff[i].usepatch)) {
                continue;
            }

            int activeindex;

            if (stuff[i]._active) {
                activeindex = 1;
            } else {
                activeindex = 0;
            }

            if (stuff[i]._patch.tip.equals("up")) {
                upsize[stuff[i]._state][activeindex] = stuff[i]._patch.sy;
            }

            if (!(stuff[i]._patch.tip.equals("down"))) {
                continue;
            }

            downsize[stuff[i]._state][activeindex] = stuff[i]._patch.sy;
        }

        for (int i = 0; i < stuff.length; ++i) {
            if (!(stuff[i].usepatch)) {
                continue;
            }

            int activeindex;

            if (stuff[i]._active) {
                activeindex = 1;
            } else {
                activeindex = 0;
            }

            int up = upsize[stuff[i]._state][activeindex];
            int down = downsize[stuff[i]._state][activeindex];

            if (stuff[i]._patch.tip.equals("up")) {
                stuff[i]._patch.x = 0;
                stuff[i]._patch.y = 0;
                stuff[i]._patch.sx = sizex;
                stuff[i]._patch.sy = up;
            }

            if (stuff[i]._patch.tip.equals("center")) {
                stuff[i]._patch.x = 0;
                stuff[i]._patch.y = up;
                stuff[i]._patch.sx = sizex;
                stuff[i]._patch.sy = (sizey - up - down);
            }

            if (!(stuff[i]._patch.tip.equals("down"))) {
                continue;
            }

            stuff[i]._patch.x = 0;
            stuff[i]._patch.y = (sizey - down);
            stuff[i]._patch.sx = sizex;
            stuff[i]._patch.sy = down;
        }
    }

    /**
     * Method description
     *
     *
     * @param pox
     * @param poy
     * @param sizex
     * @param sizey
     * @param stuff
     */
    public void Complex9(int pox, int poy, int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        int maxstate = -1;

        for (int i = 0; i < stuff.length; ++i) {
            if (!(stuff[i].usepatch)) {
                continue;
            }

            if (stuff[i]._state > maxstate) {
                maxstate = stuff[i]._state;
            }
        }

        if (maxstate == -1) {
            return;
        }

        ++maxstate;

        PatchInfo[][] data = new PatchInfo[maxstate][];

        for (int i = 0; i < maxstate; ++i) {
            data[i] = new PatchInfo[3];

            for (int index = 0; index < 3; ++index) {
                data[i][index] = new Object() {
                    int up;
                    int down;
                    int left;
                    int right;
                };
            }
        }

        for (int i = 0; i < stuff.length; ++i) {
            menues.CMaterial_whithmapping patch = stuff[i];

            if (!(patch.usepatch)) {
                continue;
            }

            int index = GetIndex(patch._active, patch.pressed);

            if (patch._patch.tip.equals("leftUP")) {
                data[patch._state][index].left = patch._patch.sx;
                data[patch._state][index].up = patch._patch.sy;
            }

            if (!(patch._patch.tip.equals("rightDOWN"))) {
                continue;
            }

            data[patch._state][index].right = patch._patch.sx;
            data[patch._state][index].down = patch._patch.sy;
        }

        for (int i = 0; i < stuff.length; ++i) {
            menues.CMaterial_whithmapping patch = stuff[i];

            if (!(patch.usepatch)) {
                continue;
            }

            int index = GetIndex(patch._active, patch.pressed);
            PatchInfo info = data[patch._state][index];

            if (patch._patch.tip.equals("leftUP")) {
                patch._patch.x = 0;
                patch._patch.y = 0;
                patch._patch.sx = info.left;
                patch._patch.sy = info.up;
            }

            if (patch._patch.tip.equals("centerUP")) {
                patch._patch.x = info.left;
                patch._patch.y = 0;
                patch._patch.sx = (sizex - info.left - info.right);
                patch._patch.sy = info.up;
            }

            if (patch._patch.tip.equals("rightUP")) {
                patch._patch.x = (sizex - info.right);
                patch._patch.y = 0;
                patch._patch.sx = info.right;
                patch._patch.sy = info.up;
            }

            if (patch._patch.tip.equals("leftCENTER")) {
                patch._patch.x = 0;
                patch._patch.y = info.up;
                patch._patch.sx = info.left;
                patch._patch.sy = (sizey - info.up - info.down);
            }

            if (patch._patch.tip.equals("centerCENTER")) {
                patch._patch.x = info.left;
                patch._patch.y = info.up;
                patch._patch.sx = (sizex - info.left - info.right);
                patch._patch.sy = (sizey - info.up - info.down);
            }

            if (patch._patch.tip.equals("rightCENTER")) {
                patch._patch.x = (sizex - info.right);
                patch._patch.y = info.up;
                patch._patch.sx = info.right;
                patch._patch.sy = (sizey - info.up - info.down);
            }

            if (patch._patch.tip.equals("leftDOWN")) {
                patch._patch.x = 0;
                patch._patch.y = (sizey - info.down);
                patch._patch.sx = info.left;
                patch._patch.sy = info.down;
            }

            if (patch._patch.tip.equals("centerDOWN")) {
                patch._patch.x = info.left;
                patch._patch.y = (sizey - info.down);
                patch._patch.sx = (sizex - info.left - info.right);
                patch._patch.sy = info.down;
            }

            if (!(patch._patch.tip.equals("rightDOWN"))) {
                continue;
            }

            patch._patch.x = (sizex - info.right);
            patch._patch.y = (sizey - info.down);
            patch._patch.sx = info.right;
            patch._patch.sy = info.down;
        }
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public class MENUCombobox {
        long nativePointer;
        String nameID;
        String text;
        int ID;
        int userid;
        int Xres;
        int Yres;
        int poy;
        int pox;
        int leny;
        int lenx;
        Vector textures;
        Vector materials;
        Vector callbacks;
        String parentName;
        String parentType;
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public class MENUComboboxStuff {
        long nativePointer;
        String nameID;
        String text;
        int ID;
        int userid;
        int Xres;
        int Yres;
        int poy;
        int pox;
        int leny;
        int lenx;
        Vector textures;
        Vector materials;
        Vector callbacks;
        String parentName;
        String parentType;
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public class MENUCustomStuff {

        /** Field description */
        public long nativePointer;

        /** Field description */
        public String nameID;

        /** Field description */
        public String text;

        /** Field description */
        public int ID;

        /** Field description */
        public int userid;

        /** Field description */
        public int Xres;

        /** Field description */
        public int Yres;

        /** Field description */
        public int poy;

        /** Field description */
        public int pox;

        /** Field description */
        public int leny;

        /** Field description */
        public int lenx;

        /** Field description */
        public Vector textures;

        /** Field description */
        public Vector materials;

        /** Field description */
        public Vector callbacks;

        /** Field description */
        public String parentName;

        /** Field description */
        public String parentType;
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public class MENUTabGroup {
        long nativePointer;
        String nameID;
        String text;
        int ID;
        int userid;
        int Xres;
        int Yres;
        int poy;
        int pox;
        int leny;
        int lenx;
        Vector textures;
        Vector materials;
        Vector callbacks;
        String parentName;
        String parentType;
    }
}


//~ Formatted in DD Std on 13/08/28
