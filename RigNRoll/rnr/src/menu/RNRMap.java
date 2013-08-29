/*
 * @(#)RNRMap.java   13/08/25
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
   Released under the BSD 3 clause license
Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this 
    list of conditions and the following disclaimer. Redistributions in binary 
    form must reproduce the above copyright notice, this list of conditions and 
    the following disclaimer in the documentation and/or other materials 
    provided with the distribution. Neither the name of the DieHard Development 
    nor the names of its contributors may be used to endorse or promote products 
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 *
 *
 *
 */


package rnr.src.menu;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class RNRMap {

    /** Field description */
    public static final int CB_MAPZOOM = 327676;

    /** Field description */
    public static final int ROAD_ICON0 = 0;

    /** Field description */
    public static final int ROAD_ICON1 = 1;

    /** Field description */
    public static final int ROAD_ICON2 = 2;

    /** Field description */
    public static final int WAREHOUSE = 3;

    /** Field description */
    public static final int GAMER_WAREHOUSE = 4;

    /** Field description */
    public static final int OFFICE_WAREHOUSE = 5;

    /** Field description */
    public static final int WAREHOUSE_ORDER = 6;

    /** Field description */
    public static final int EMPLOYEE_ORDER = 7;

    /** Field description */
    public static final int EMPLOYEE_TASK = 8;

    /** Field description */
    public static final int EMPLOYEE_MIGHTY = 9;

    /** Field description */
    public static final int EMPLOYEE = 10;

    /** Field description */
    public static final int OFFICE = 11;

    /** Field description */
    public static final int OFFICE_FOR_SALE = 12;

    /** Field description */
    public static final int REPAIR = 13;

    /** Field description */
    public static final int DEALER = 14;

    /** Field description */
    public static final int DESTINATION = 15;

    /** Field description */
    public static final int WEATHER_SUNNY = 16;

    /** Field description */
    public static final int WEATHER_MOSTLY_CLEAR = 17;

    /** Field description */
    public static final int WEATHER_CLOUDY = 18;

    /** Field description */
    public static final int WEATHER_RAIN = 19;

    /** Field description */
    public static final int WEATHER_THUNDER = 20;

    /** Field description */
    public static final int WEATHER_SNOW = 21;

    /** Field description */
    public static final int WEATHER_TEMPERARURE = 22;

    /** Field description */
    public static final int WEATHER_MOONCLEAR = 23;

    /** Field description */
    public static final int WEATHER_MOONCLOUDY = 24;

    /** Field description */
    public static final int WEATHER_WAREHOUSE = 25;

    /** Field description */
    public static final int WEATHER_WAREHOUSE_MY = 26;

    /** Field description */
    public static final int MAP_ORG_PLAYER_CAR = 27;

    /** Field description */
    public static final int MAP_ORG_TASK_CURRENT = 28;

    /** Field description */
    public static final int MAP_ORG_TASK_CURRENT_ICON = 29;

    /** Field description */
    public static final int MAP_ORG_TASK = 30;

    /** Field description */
    public static final int MAP_ORG_TASK_ICON = 31;

    /** Field description */
    public static final int MAP_ORG_DESTINATION_CURRENT = 32;

    /** Field description */
    public static final int MAP_ORG_DESTINATION = 33;

    /** Field description */
    public static final int MAP_ORG_SEMITRAILER = 34;

    /** Field description */
    public static final int MAP_ORG_PASSANGER = 35;

    /** Field description */
    public static final int MAP_ORG_SENDING = 36;

    /** Field description */
    public static final int OBJECT_MAX = 37;

    /** Field description */
    public static final int STATE_NONE = 0;

    /** Field description */
    public static final int STATE_DRAG = 1;

    /** Field description */
    public static final int STATE_ZOOM = 2;

    /** Field description */
    public static final int STATE_RAMKA = 3;

    /** Field description */
    public static final float MIN_ZOOM = 1.0F;

    /** Field description */
    public static final float MAX_ZOOM = 12.0F;

    /** Field description */
    public static final float START_X = 1.5F;

    /** Field description */
    public static final float START_Y = 1.5F;

    /** Field description */
    public static final int SCALE_NUM = 10;

    /** Field description */
    public static final float ALPHA = (float) Math.pow(12.0D, 0.111111111938953D);

    /** Field description */
    public static final int MAP_START = 0;

    /** Field description */
    public static final int MAP_LEFT = 0;

    /** Field description */
    public static final int MAP_RIGHT = 1;

    /** Field description */
    public static final int MAP_UP = 2;

    /** Field description */
    public static final int MAP_DOWN = 3;

    /** Field description */
    public static final int MAP_ZOOMOUT = 4;

    /** Field description */
    public static final int MAP_ZOOMIN = 5;

    /** Field description */
    public static final int MAP_MAX = 6;
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
    @SuppressWarnings("rawtypes")
    Vector textures;
    @SuppressWarnings("rawtypes")
    Vector materials;
    @SuppressWarnings("rawtypes")
    Vector callbacks;
    String parentName;
    String parentType;
    SelectCb cb;
    @SuppressWarnings("rawtypes")
    HashMap objs;
    MENUbutton_field hand;
    MENUbutton_field select;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings("rawtypes")
    public RNRMap() {
        this.objs = new HashMap();
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings("rawtypes")
    public void ClearObjects() {
        for (Iterator it = this.objs.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            Integer ID = (Integer) entry.getKey();

            RemoveMapObject(ID.intValue());
        }

        this.objs.clear();
        RemoveAllObjects();
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param obj
     * @param x
     * @param y
     * @param text
     * @param tips
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public int AddObject(int type, Object obj, float x, float y, String text, String tips) {
        int id = Common.GetID();

        AddMapObject(type, x, y, false, false, id, text, tips);
        this.objs.put(new Integer(id), obj);

        return id;
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param obj
     * @param startID
     * @param endID
     * @param tips
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public int AddOrder(int type, Object obj, int startID, int endID, String tips) {
        int id = Common.GetID();

        AddOrder(type, startID, endID, false, false, id, tips);
        this.objs.put(new Integer(id), obj);

        return id;
    }

    /**
     * Method description
     *
     *
     * @param common
     * @param cb
     */
    public void AttachCallback(Common common, SelectCb cb) {
        this.cb = cb;
        menues.UpdateMenuField(this);
        menues.SetScriptOnControlDataPass(common.GetMenu(), this, this, "OnMapClick", 7L);
    }

    /**
     * Method description
     *
     */
    public void SetStartPosition() {
        SetMaxZoom(12.0F);
        SetMinZoom(1.0F);
        Move(1.5F, 1.5F);
    }

    /**
     * Method description
     *
     *
     * @param common
     * @param parentname
     */
    public void AttachStandardControls(Common common, String parentname) {
        AttachStandardControls(common, parentname, parentname);
    }

    /**
     * Method description
     *
     *
     * @param common
     * @param parentname
     * @param parent1name
     */
    public void AttachStandardControls(Common common, String parentname, String parent1name) {
        SetStartPosition();

        String[] names = {
            "MAP shiftLEFT", "MAP shiftRIGHT", "MAP shiftUP", "MAP shiftDOWN", "MAP zoomMINUS", "MAP zoomPLUS"
        };
        String[] cbnames = {
            "OnLeft", "OnRight", "OnUp", "OnDown", "OnZoomOut", "OnZoomIn"
        };
        int parentswitch = 4;

        for (int i = 0; i < 6; ++i) {
            MENUsimplebutton_field button = common.FindSimpleButtonByParent(names[i], (i >= parentswitch)
                    ? parent1name
                    : parentname);

            button.userid = this.userid;
            menues.UpdateField(button);
            menues.SetScriptOnControl(common.GetMenu(), button, this, cbnames[i], 4L);
        }

        for (int i = 0; i < 10; ++i) {
            MENUsimplebutton_field button = common.FindSimpleButtonByParent("MAP zoomSCALE 0" + i, parent1name);

            button.userid = this.userid;
            menues.UpdateField(button);
            menues.SetScriptOnControl(common.GetMenu(), button, this, "OnZoomScale", 4L);
        }

        MENUbutton_field hand = common.FindRadioButtonByParent("MAP hand", parent1name);

        SetState(1);
        menues.SetFieldState(hand.nativePointer, 1);
        hand.userid = this.userid;
        menues.UpdateField(hand);

        MENUbutton_field select = common.FindRadioButtonByParent("MAP select", parent1name);

        menues.SetFieldState(select.nativePointer, 0);
        select.userid = this.userid;
        menues.UpdateField(select);
        this.select = select;
        this.hand = hand;
        menues.SetScriptOnControl(common.GetMenu(), hand, this, "OnHand", 2L);
        menues.SetScriptOnControl(common.GetMenu(), select, this, "OnSelect", 2L);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param map
     * @param data
     */
    public void OnMapClick(long _menu, RNRMap map, long data) {
        this.cb.OnSelect(0, this.objs.get(new Integer((int) data)));
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnZoomIn(long _menu, MENUsimplebutton_field field) {
        ZoomIn();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnZoomOut(long _menu, MENUsimplebutton_field field) {
        ZoomOut();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnLeft(long _menu, MENUsimplebutton_field field) {
        MoveLeft();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnRight(long _menu, MENUsimplebutton_field field) {
        MoveRight();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnUp(long _menu, MENUsimplebutton_field field) {
        MoveUp();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnDown(long _menu, MENUsimplebutton_field field) {
        MoveDown();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnZoomScale(long _menu, MENUsimplebutton_field field) {
        int index = field.nameID.charAt(field.nameID.length() - 1) - '0';
        float minzoom = 1.0F;
        float zoom = minzoom * (float) Math.pow(ALPHA, index);

        Zoom(zoom);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnSelect(long _menu, MENUbutton_field field) {
        if (GetState() != 3) {
            SetState(3);
        }

        menues.SetFieldState(this.select.nativePointer, 1);
        menues.SetFieldState(this.hand.nativePointer, 0);
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param field
     */
    public void OnHand(long _menu, MENUbutton_field field) {
        if (GetState() != 1) {
            SetState(1);
        }

        menues.SetFieldState(this.select.nativePointer, 0);
        menues.SetFieldState(this.hand.nativePointer, 1);
    }

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramInt4
     * @param paramLong
     *
     * @return
     */
    public native long Init(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramFloat1
     * @param paramFloat2
     * @param paramBoolean1
     * @param paramBoolean2
     * @param paramInt2
     * @param paramString1
     * @param paramString2
     */
    public native void AddMapObject(int paramInt1, float paramFloat1, float paramFloat2, boolean paramBoolean1,
                                    boolean paramBoolean2, int paramInt2, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramBoolean1
     * @param paramBoolean2
     * @param paramInt4
     * @param paramString
     */
    public native void AddOrder(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1,
                                boolean paramBoolean2, int paramInt4, String paramString);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public native void RemoveMapObject(int paramInt);

    /**
     * Method description
     *
     */
    public native void RemoveAllObjects();

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public native void ShowMapObject(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public native void HideMapObject(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramFloat
     */
    public native void Zoom(float paramFloat);

    /**
     * Method description
     *
     */
    public native void ZoomIn();

    /**
     * Method description
     *
     */
    public native void ZoomOut();

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public native void SetState(int paramInt);

    /**
     * Method description
     *
     *
     * @return
     */
    public native int GetState();

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramBoolean
     *
     * @return
     */
    public native boolean SelectMapObject(int paramInt, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramBoolean
     *
     * @return
     */
    public native boolean HighlightMapObject(int paramInt, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @return
     */
    public native float GetMinZoom();

    /**
     * Method description
     *
     *
     * @return
     */
    public native float GetMaxZoom();

    /**
     * Method description
     *
     *
     * @return
     */
    public native float GetZoomStep();

    /**
     * Method description
     *
     *
     * @return
     */
    public native float GetXMoveStep();

    /**
     * Method description
     *
     *
     * @return
     */
    public native float GetYMoveStep();

    /**
     * Method description
     *
     *
     * @param paramFloat
     */
    public native void SetMaxZoom(float paramFloat);

    /**
     * Method description
     *
     *
     * @param paramFloat
     */
    public native void SetMinZoom(float paramFloat);

    /**
     * Method description
     *
     *
     * @param paramFloat
     */
    public native void SetZoomStep(float paramFloat);

    /**
     * Method description
     *
     *
     * @param paramFloat
     */
    public native void SetXMoveStep(float paramFloat);

    /**
     * Method description
     *
     *
     * @param paramFloat
     */
    public native void SetYMoveStep(float paramFloat);

    /**
     * Method description
     *
     */
    public native void MoveRight();

    /**
     * Method description
     *
     */
    public native void MoveLeft();

    /**
     * Method description
     *
     */
    public native void MoveUp();

    /**
     * Method description
     *
     */
    public native void MoveDown();

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     */
    public native void SetObjectType(int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramString
     */
    public native void SetObjectText(int paramInt, String paramString);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramPriority
     */
    public native void SetDefaultIconPicturePriority(int paramInt, Priority paramPriority);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramPriority
     */
    public native void SetDefaultIconTextPriority(int paramInt, Priority paramPriority);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramPriority
     *
     * @return
     */
    public native boolean SetMapObjectTextPriority(int paramInt, Priority paramPriority);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramPriority
     *
     * @return
     */
    public native boolean SetMapObjectPicturePriority(int paramInt, Priority paramPriority);

    /**
     * Method description
     *
     *
     * @param paramFloat1
     * @param paramFloat2
     */
    public native void Move(float paramFloat1, float paramFloat2);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramString1
     * @param paramString2
     */
    public native void SetCallback(int paramInt, String paramString1, String paramString2);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramBoolean
     */
    public native void SetClickableGroup(int paramInt, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramInt
     *
     * @return
     */
    public native boolean GetClickableGroup(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     */
    public native void makeDirectionsAsOne(int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramBoolean
     */
    public native void setActiveMapObject(int paramInt, boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramBoolean
     */
    public native void setPressedMapObject(int paramInt, boolean paramBoolean);

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ    
     */
    public class Priority {
        int[][] priority;

        /**
         * Constructs ...
         *
         */
        public Priority() {
            this.priority = new int[3][4];
        }

        /**
         * Method description
         *
         *
         * @param activestate
         * @param highlighted
         * @param selected
         * @param value
         */
        public void SetPriority(int activestate, boolean highlighted, boolean selected, int value) {
            this.priority[activestate][(((highlighted)
                                         ? 2
                                         : 0) + ((selected)
                    ? 1
                    : 0))] = value;
        }

        /**
         * Method description
         *
         *
         * @param activestate
         * @param highlighted
         * @param selected
         * @param value
         *
         * @return
         */
        public int GetPriority(int activestate, boolean highlighted, boolean selected, int value) {
            return this.priority[activestate][(0 + 0)];
        }
    }
}


//~ Formatted in DD Std on 13/08/25
