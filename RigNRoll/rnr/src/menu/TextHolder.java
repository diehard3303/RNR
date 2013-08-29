/*
 * @(#)TextHolder.java   13/08/25
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.gameobj.WHOrderInfo;
import rnr.src.menuscript.Converts;
import rnr.src.menuscript.OfficeTab;
import rnr.src.rnrcore.loc;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class TextHolder extends BaseMenu {
    static final int TEXTFIELD = 0;
    static final int SIMPLEBUTTON = 1;
    static final int RADIOBUTTON = 2;
    @SuppressWarnings("rawtypes")
    Vector m_radios = new Vector();
    SelectCb m_cbs;
    RatingData m_ratingdata;
    OfficeTab.FadeAnimation m_FadeAnim;
    long m_SelectedControl;
    int[] m_LineID;
    @SuppressWarnings("rawtypes")
    HashMap m_staticobjs;

    /**
     * Constructs ...
     *
     *
     * @param common
     */
    @SuppressWarnings("rawtypes")
    public TextHolder(Common common) {
        this.common = common;
        this.m_staticobjs = new HashMap();
    }

    /**
     * Method description
     *
     *
     * @param ID
     * @param text
     */
    @SuppressWarnings("unchecked")
    public void AttachControl(int ID, MENUText_field text) {
        text.userid = ID;
        menues.UpdateField(text);
        this.m_staticobjs.put(new Integer(ID), new TextField(text));
    }

    /**
     * Method description
     *
     *
     * @param ID
     * @param field
     */
    @SuppressWarnings("unchecked")
    public void AttachControl(int ID, MENUsimplebutton_field field) {
        field.userid = ID;
        menues.UpdateField(field);
        this.m_staticobjs.put(new Integer(ID), new SimpleButton(field));
    }

    void OnRadioPress(long _menu, MENUbutton_field field) {
        for (int i = 0; i < this.m_radios.size(); ++i) {
            MENUbutton_field radio = (MENUbutton_field) this.m_radios.get(i);

            if (radio != field) {
                menues.SetFieldState(radio.nativePointer, 0);
            } else {
                menues.SetFieldState(radio.nativePointer, 1);
            }
        }

        if (this.m_cbs != null) {
            this.m_cbs.OnSelect(field.userid, this);
        }

        Deselect();
        Select(field.nativePointer);
    }

    private void Deselect() {
        if (this.m_SelectedControl == 0L) {
            return;
        }

        this.m_FadeAnim.Finish(this.m_SelectedControl);
        this.m_SelectedControl = 0L;
    }

    private void Select(long control) {
        this.m_SelectedControl = control;
        this.m_FadeAnim.Start(this.m_SelectedControl);
    }

    /**
     * Method description
     *
     *
     * @param ID
     * @param field
     */
    @SuppressWarnings("unchecked")
    public void AttachControl(int ID, MENUbutton_field field) {
        field.userid = ID;
        menues.UpdateField(field);
        this.m_staticobjs.put(new Integer(ID), new RadioButton(field));
        menues.SetScriptOnControl(this.common.GetMenu(), field, this, "OnRadioPress", 2L);
        this.m_radios.add(field);
    }

    @SuppressWarnings("rawtypes")
    private void FillInfo(WHOrderInfo order, HashMap map) {
        for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            int id = ((Integer) entry.getKey()).intValue();
            Control c = (Control) entry.getValue();

            if (order != null) {
                KeyPair[] keys = new KeyPair[1];

                switch (id) {
                 case 3 :
                     if (order.order_type == 0) {
                         c.SetText(loc.getAiString("DELIVERY"));
                     } else if (order.order_type == 1) {
                         c.SetText(loc.getAiString("QUALIFYING_RACE"));
                     } else if (order.order_type == 2) {
                         c.SetText(loc.getAiString("ANOTHER_ORDER"));
                     }

                     break;

                 case 4 :
                     c.SetText(order.shipto);

                     break;

                 case 5 :
                     c.SetText(order.freight);

                     break;

                 case 6 :
                     keys[0] = new KeyPair("MONEY", "" + Converts.ConvertNumeric(order.charges));
                     c.SetText(MacroKit.Parse(c.GetNativeString(), keys));

                     break;

                 case 7 :
                     c.SetText((order.competition)
                               ? loc.getMENUString("common\\YES")
                               : loc.getMENUString("common\\NO"));

                     break;

                 case 8 :
                     keys[0] = new KeyPair("MONEY", "" + order.forfeit);
                     c.SetText(MacroKit.Parse(c.GetNativeString(), keys));

                     break;

                 case 9 :
                     c.SetText(Converts.ConverTime3Plus2(c.GetNativeString(), order.time_limit_hour,
                             order.time_limit_min));

                     break;

                 case 10 :
                     keys[0] = new KeyPair("VALUE", "" + Converts.ConvertDouble(order.weight, 3));
                     c.SetText(MacroKit.Parse(c.GetNativeString(), keys));

                     break;

                 case 11 :
                     keys[0] = new KeyPair("VALUE", "" + Converts.ConvertDouble(order.fragil * 100.0D, 0));
                     c.SetText(MacroKit.Parse(c.GetNativeString(), keys));

                     break;

                 case 12 :
                     keys[0] = new KeyPair("VALUE", "" + Converts.ConvertDouble(order.distance + 0.5D, 0));
                     c.SetText(MacroKit.Parse(c.GetNativeString(), keys));
                }
            } else {
                c.SetText("");
            }

            c.Update();
        }
    }

    /**
     * Method description
     *
     *
     * @param order
     */
    public void FillWHOrderInfo(WHOrderInfo order) {
        FillInfo(order, this.m_staticobjs);
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param node
     * @param control
     */
    public void SetupLineInTable(int type, TableNode node, Object control) {}

    /**
     * Interface description
     *
     *
     * @version        1.0, 13/08/25
     * @author         TJ    
     */
    public static abstract interface Control {

        /**
         * Method description
         *
         *
         * @param paramString
         */
        public abstract void SetText(String paramString);

        /**
         * Method description
         *
         *
         * @return
         */
        public abstract String GetNativeString();

        /**
         * Method description
         *
         */
        public abstract void Update();

        /**
         * Method description
         *
         *
         * @return
         */
        public abstract int GetX();

        /**
         * Method description
         *
         *
         * @return
         */
        public abstract int GetY();

        /**
         * Method description
         *
         *
         * @param paramInt
         */
        public abstract void SetX(int paramInt);

        /**
         * Method description
         *
         *
         * @param paramInt
         */
        public abstract void SetY(int paramInt);

        /**
         * Method description
         *
         *
         * @param paramString
         */
        public abstract void SetParent(String paramString);

        /**
         * Method description
         *
         *
         * @return
         */
        public abstract int GetType();

        /**
         * Method description
         *
         *
         * @return
         */
        public abstract long GetNativeP();
    }


    class RadioButton implements TextHolder.Control {
        MENUbutton_field field;
        String native_text;

        /**
         * Constructs ...
         *
         *
         * @param paramMENUbutton_field
         */
        public RadioButton(MENUbutton_field paramMENUbutton_field) {
            this.field = paramMENUbutton_field;
            this.native_text = this.field.text;
        }

        /**
         * Method description
         *
         *
         * @param text
         */
        @Override
        public void SetText(String text) {
            this.field.text = text;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String GetNativeString() {
            return this.native_text;
        }

        /**
         * Method description
         *
         */
        @Override
        public void Update() {
            menues.UpdateField(this.field);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public int GetType() {
            return 2;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public int GetX() {
            return this.field.pox;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public int GetY() {
            return this.field.poy;
        }

        /**
         * Method description
         *
         *
         * @param x
         */
        @Override
        public void SetX(int x) {
            this.field.pox = x;
        }

        /**
         * Method description
         *
         *
         * @param y
         */
        @Override
        public void SetY(int y) {
            this.field.poy = y;
        }

        /**
         * Method description
         *
         *
         * @param parent
         */
        @Override
        public void SetParent(String parent) {
            this.field.parentName = parent;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public long GetNativeP() {
            return this.field.nativePointer;
        }
    }


    class SimpleButton implements TextHolder.Control {
        MENUsimplebutton_field field;
        String native_text;

        /**
         * Constructs ...
         *
         *
         * @param paramMENUsimplebutton_field
         */
        public SimpleButton(MENUsimplebutton_field paramMENUsimplebutton_field) {
            this.field = paramMENUsimplebutton_field;
            this.native_text = this.field.text;
        }

        /**
         * Method description
         *
         *
         * @param text
         */
        @Override
        public void SetText(String text) {
            this.field.text = text;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String GetNativeString() {
            return this.native_text;
        }

        /**
         * Method description
         *
         */
        @Override
        public void Update() {
            menues.UpdateField(this.field);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public int GetType() {
            return 1;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public int GetX() {
            return this.field.pox;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public int GetY() {
            return this.field.poy;
        }

        /**
         * Method description
         *
         *
         * @param x
         */
        @Override
        public void SetX(int x) {
            this.field.pox = x;
        }

        /**
         * Method description
         *
         *
         * @param y
         */
        @Override
        public void SetY(int y) {
            this.field.poy = y;
        }

        /**
         * Method description
         *
         *
         * @param parent
         */
        @Override
        public void SetParent(String parent) {
            this.field.parentName = parent;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public long GetNativeP() {
            return this.field.nativePointer;
        }
    }


    class TextField implements TextHolder.Control {
        MENUText_field field;
        String native_text;

        /**
         * Constructs ...
         *
         *
         * @param paramMENUText_field
         */
        public TextField(MENUText_field paramMENUText_field) {
            this.field = paramMENUText_field;
            this.native_text = this.field.text;
        }

        /**
         * Method description
         *
         *
         * @param text
         */
        @Override
        public void SetText(String text) {
            this.field.text = text;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public String GetNativeString() {
            return this.native_text;
        }

        /**
         * Method description
         *
         */
        @Override
        public void Update() {
            menues.UpdateField(this.field);
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public int GetType() {
            return 0;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public int GetX() {
            return this.field.pox;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public int GetY() {
            return this.field.poy;
        }

        /**
         * Method description
         *
         *
         * @param x
         */
        @Override
        public void SetX(int x) {
            this.field.pox = x;
        }

        /**
         * Method description
         *
         *
         * @param y
         */
        @Override
        public void SetY(int y) {
            this.field.poy = y;
        }

        /**
         * Method description
         *
         *
         * @param parent
         */
        @Override
        public void SetParent(String parent) {
            this.field.parentName = parent;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public long GetNativeP() {
            return this.field.nativePointer;
        }
    }
}


//~ Formatted in DD Std on 13/08/25
