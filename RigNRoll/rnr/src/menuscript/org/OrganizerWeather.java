/*
 * @(#)OrganizerWeather.java   13/08/26
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


package rnr.src.rnr.src.menuscript.org;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.gameobj.WarehouseInfo;
import rnr.src.gameobj.WeatherPoint;
import rnr.src.menu.BaseMenu;
import rnr.src.menu.Common;
import rnr.src.menu.DateData;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.KeyPair;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.MacroKit;
import rnr.src.menu.RNRMap;
import rnr.src.menu.SelectCb;
import rnr.src.menu.Table;
import rnr.src.menu.TableCallbacks;
import rnr.src.menu.TableCmp;
import rnr.src.menu.TableNode;
import rnr.src.menu.menues;
import rnr.src.menuscript.Converts;

//~--- JDK imports ------------------------------------------------------------

import java.nio.ByteBuffer;

import java.util.HashSet;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class OrganizerWeather extends BaseMenu implements IOrgTab, TableCallbacks, SelectCb {
    OrganiserMenu parent = null;
    WeatherData m_wdata = new WeatherData();
    MENUText_field[] m_4dayTextFields = new MENUText_field[8];
    MENUsimplebutton_field[] m_4dayIcons = new MENUsimplebutton_field[24];
    MENUbutton_field[] m_daybuttons = new MENUbutton_field[4];
    String[] locFarenheitGraduses = new String[2];
    int m_curwh = 0;
    int m_4day = 0;
    boolean m_hideflag = true;
    Table m_WhTable;
    RNRMap m_map;
    int[] m_wiconids;
    ByteBuffer m_convertbuffer;
    HashSet m_hidenames;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param parent
     */
    public OrganizerWeather(long _menu, OrganiserMenu parent) {
        this.parent = parent;
        this.common = new Common(_menu);
        InitMenu();

        long control = menues.FindFieldInMenu(_menu, "CALL COMMUNICATOR HELP - WEATHER");

        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    void InitMenu() {
        long _menu = this.common.GetMenu();

        this.m_WhTable = new Table(_menu, "Weather whs");
        this.m_WhTable.AddRadioButton("Weather - City", 0);
        this.m_WhTable.AddEvent(2);
        this.m_WhTable.Setup(38L, 20, Common.ConstructPath("menu_com.xml"), "CB RADIO - WEATHER",
                             "TABLEGROUP Weather - City - 19 38", this, 10);
        this.m_WhTable.AttachRanger(this.common.FindScroller("Tableranger - Weather - City"));
        this.m_map = this.common.FindRNRMapByParent("MAP - zooming picture", "Weather - MAP");
        this.m_daybuttons[0] = this.common.FindRadioButton("BUTTON - TODAY");
        this.m_daybuttons[0].userid = 0;
        menues.UpdateField(this.m_daybuttons[0]);
        menues.SetScriptOnControl(_menu, this.m_daybuttons[0], this, "On4DaySwitch", 2L);
        this.m_daybuttons[1] = this.common.FindRadioButton("BUTTON - 2 DAY");
        this.m_daybuttons[1].userid = 1;
        menues.UpdateField(this.m_daybuttons[1]);
        menues.SetScriptOnControl(_menu, this.m_daybuttons[1], this, "On4DaySwitch", 2L);
        this.m_daybuttons[2] = this.common.FindRadioButton("BUTTON - 3 DAY");
        this.m_daybuttons[2].userid = 2;
        menues.UpdateField(this.m_daybuttons[2]);
        menues.SetScriptOnControl(_menu, this.m_daybuttons[2], this, "On4DaySwitch", 2L);
        this.m_daybuttons[3] = this.common.FindRadioButton("BUTTON - 4 DAY");
        this.m_daybuttons[3].userid = 3;
        menues.UpdateField(this.m_daybuttons[3]);
        menues.SetScriptOnControl(_menu, this.m_daybuttons[3], this, "On4DaySwitch", 2L);
        this.m_wdata.Init();
        this.m_map = this.common.FindRNRMap("MAP - zooming picture");
        this.common.SetScriptOnButton("BUTTON - CITY", this, "OnCitySort");
        InitIcons();
        InitHideMap();
        this.m_convertbuffer = ByteBuffer.allocate(4);
    }

    void InitIcons() {
        String[] iconnames = {
            "4DayForecast - BUTT - SUNNY", "4DayForecast - BUTT - MOSTLY CLEAR", "4DayForecast - BUTT - CLOUDY",
            "4DayForecast - BUTT - RAINY", "4DayForecast - BUTT - SNOWY", "4DayForecast - BUTT - STORMY"
        };
        String[] textnames = { "4DayForecast - Temperature - F VALUE", "4DayForecast - Temperature - C VALUE" };
        String[] parents = { "GROUP Today", "GROUP 2 DAY", "GROUP 3 DAY", "GROUP 4 DAY" };

        for (int i = 0; i < 4; ++i) {
            for (int ic = 0; ic < iconnames.length; ++ic) {
                long control = menues.InitXmlControl(this.common.GetMenu(), Common.ConstructPath("menu_com.xml"),
                                   "CB RADIO - WEATHER", iconnames[ic]);
                MENUsimplebutton_field b = menues.ConvertSimpleButton(control);

                b.parentName = parents[i];
                menues.UpdateField(b);
                this.m_4dayIcons[(i * iconnames.length + ic)] = b;
            }

            for (int ic = 0; ic < textnames.length; ++ic) {
                long control = menues.InitXmlControl(this.common.GetMenu(), Common.ConstructPath("menu_com.xml"),
                                   "CB RADIO - WEATHER", textnames[ic]);
                MENUText_field text = menues.ConvertTextFields(control);

                this.locFarenheitGraduses[ic] = text.text;
                text.parentName = parents[i];
                menues.UpdateField(text);
                this.m_4dayTextFields[(i * textnames.length + ic)] = text;
            }
        }
    }

    void UpdateIcons() {
        for (int i = 0; i < 4; ++i) {
            WeatherPoint wpoint = this.m_wdata.GetWPoint(i, false, this.m_curwh);
            KeyPair[] macro = new KeyPair[2];
            double farenheit = Converts.ConvertFahrenheit(wpoint.temp);

            macro[0] = new KeyPair("SIGN", (farenheit >= 0.0D)
                                           ? "+"
                                           : "-");
            macro[1] = new KeyPair("TEMPERATURE_F", Converts.ConvertDouble(Math.abs(farenheit), 0));
            this.m_4dayTextFields[(i * 2 + 0)].text = MacroKit.Parse(this.locFarenheitGraduses[0], macro);
            menues.UpdateField(this.m_4dayTextFields[(i * 2 + 0)]);
            macro[0] = new KeyPair("SIGN", (wpoint.temp >= 0.0D)
                                           ? "+"
                                           : "-");
            macro[1] = new KeyPair("TEMPERATURE_C", Converts.ConvertDouble(Math.abs(wpoint.temp), 0));
            this.m_4dayTextFields[(i * 2 + 1)].text = MacroKit.Parse(this.locFarenheitGraduses[1], macro);
            menues.UpdateField(this.m_4dayTextFields[(i * 2 + 1)]);

            int wstate = wpoint.GetState();

            for (int ic = 0; ic < 6; ++ic) {
                menues.SetShowField(this.m_4dayIcons[(i * 6 + ic)].nativePointer, ic == wstate);
            }
        }
    }

    void FullRefreshMap(WeatherData d) {
        this.m_map.ClearObjects();
        this.m_map.SetClickableGroup(25, true);
        this.m_map.SetClickableGroup(26, true);
        this.m_wiconids = new int[d.m_warehouses.size()];

        for (int i = 0; i < d.m_warehouses.size(); ++i) {
            WarehouseInfo w = (WarehouseInfo) d.m_warehouses.get(i);

            if (w.bIsMine) {
                w.ID = this.m_map.AddObject(26, this.m_WhTable.GetNodeByLine(i), w.mapx, w.mapy, w.name, "");
            } else {
                w.ID = this.m_map.AddObject(25, this.m_WhTable.GetNodeByLine(i), w.mapx, w.mapy, w.name, "");
            }

            WeatherPoint p = this.m_wdata.GetWPoint(0, false, i);
            int id = Common.GetID();
            KeyPair[] macro = new KeyPair[2];
            double farenheit = Converts.ConvertFahrenheit(p.temp);

            macro[0] = new KeyPair("SIGN", (farenheit >= 0.0D)
                                           ? "+"
                                           : "-");
            macro[1] = new KeyPair("TEMPERATURE_F", Converts.ConvertDouble(Math.abs(farenheit), 0));
            this.m_map.AddMapObject(16 + p.GetState(), w.mapx, w.mapy, false, false, id,
                                    MacroKit.Parse(this.locFarenheitGraduses[0], macro), "");
            this.m_wiconids[i] = id;
        }

        this.m_map.AttachCallback(this.common, this);
        ShowHideMap(true);
    }

    void UpdateMap() {
        for (int i = 0; i < this.m_wdata.m_warehouses.size(); ++i) {
            WeatherPoint wpoint = this.m_wdata.GetWPoint(this.m_4day, false, i);

            this.m_map.SetObjectType(this.m_wiconids[i], wpoint.GetState() + 16);

            KeyPair[] macro = new KeyPair[2];
            double farenheit = Converts.ConvertFahrenheit(wpoint.temp);

            macro[0] = new KeyPair("SIGN", (farenheit >= 0.0D)
                                           ? "+"
                                           : "-");
            macro[1] = new KeyPair("TEMPERATURE_F", Converts.ConvertDouble(Math.abs(farenheit), 0));
            this.m_map.SetObjectText(this.m_wiconids[i], MacroKit.Parse(this.locFarenheitGraduses[0], macro));
        }
    }

    void AfterInitMenu() {
        menues.SetIgnoreEvents(this.common.FindSimpleButton("CommWeatherMapBorder_01").nativePointer, true);
        menues.SetIgnoreEvents(this.common.FindSimpleButton("CommWeatherMapBorder_02").nativePointer, true);
        this.m_map.AttachStandardControls(this.common, "Weather - MAP - Shift Buttons", "Weather - MAP - Interface");
        menues.SetScriptOnControlDataPass(this.common.GetMenu(), this.m_map, this, "OnMapZoom", 327676L);
    }

    void OnMapZoom(long _menu, RNRMap map, long data) {
        this.m_convertbuffer.clear();
        this.m_convertbuffer.putInt((int) data);
        this.m_convertbuffer.rewind();

        float zoom = this.m_convertbuffer.getFloat();
        boolean newhideflag = zoom <= 1.0F * RNRMap.ALPHA + 0.01F;

        if (this.m_hideflag != newhideflag) {
            ShowHideMap(newhideflag);
        }
    }

    void InitHideMap() {
        this.m_hidenames = new HashSet();
        this.m_hidenames.add("WV");
        this.m_hidenames.add("MW");
        this.m_hidenames.add("KM");
        this.m_hidenames.add("MD");
        this.m_hidenames.add("PS");
        this.m_hidenames.add("PD");
        this.m_hidenames.add("KM");
        this.m_hidenames.add("TM");
        this.m_hidenames.add("SC");
        this.m_hidenames.add("ST");
        this.m_hidenames.add("MC");
        this.m_hidenames.add("LB");
    }

    void ShowHideMap(boolean hide) {
        for (int i = 0; i < this.m_wdata.m_warehouses.size(); ++i) {
            WarehouseInfo w = (WarehouseInfo) this.m_wdata.m_warehouses.get(i);

            if (this.m_hidenames.contains(w.idname)) {
                if (hide) {
                    this.m_map.HideMapObject(w.ID);
                    this.m_map.HideMapObject(this.m_wiconids[i]);
                } else {
                    this.m_map.ShowMapObject(w.ID);
                    this.m_map.ShowMapObject(this.m_wiconids[i]);
                }
            }
        }

        this.m_hideflag = hide;
    }

    void SetDayButtonsState() {
        for (int i = 0; i < this.m_daybuttons.length; ++i) {
            menues.SetFieldState(this.m_daybuttons[i].nativePointer, (i == this.m_4day)
                    ? 1
                    : 0);
        }
    }

    void On4DaySwitch(long _menu, MENUbutton_field radio) {
        if (radio.userid == this.m_4day) {
            menues.SetFieldState(this.m_daybuttons[this.m_4day].nativePointer, 1);

            return;
        }

        this.m_4day = radio.userid;
        SetDayButtonsState();
        UpdateMap();
    }

    void Refresh() {
        JavaEvents.SendEvent(7, 0, this);
        SetDayButtonsState();

        WeatherData d = this.m_wdata;

        for (int i = 0; i < d.m_warehouses.size(); ++i) {
            WarehouseInfo wh = (WarehouseInfo) d.m_warehouses.get(i);

            wh.handle = i;
            this.m_WhTable.AddItem(null, wh, false);
        }

        this.m_WhTable.RefillTree();
        FullRefreshMap(this.m_wdata);
        UpdateWHInfo();

        DateData dau2 = (DateData) this.m_wdata.m_dates.get(2);

        Converts.ConvertDate(this.common.FindRadioButton("BUTTON - 3 DAY"), dau2.month, dau2.day, dau2.year);

        DateData dau3 = (DateData) this.m_wdata.m_dates.get(3);

        Converts.ConvertDate(this.common.FindRadioButton("BUTTON - 4 DAY"), dau3.month, dau3.day, dau3.year);
    }

    void UpdateWHInfo() {
        TableNode n = this.m_WhTable.GetSingleChecked();
        WarehouseInfo whinfo = (WarehouseInfo) n.item;

        if (whinfo == null) {
            return;
        }

        for (int i = 0; i < this.m_wdata.m_warehouses.size(); ++i) {
            WarehouseInfo w = (WarehouseInfo) this.m_wdata.m_warehouses.get(i);

            this.m_map.SelectMapObject(w.ID, false);
        }

        int currentwh = (int) whinfo.handle;

        this.m_curwh = currentwh;
        this.m_map.SelectMapObject(whinfo.ID, true);
        UpdateIcons();
    }

    void OnCitySort(long _menu, MENUsimplebutton_field b) {
        this.m_WhTable.SortTable(0, new WarehouseSorter());
        UpdateWHInfo();
        UpdateMap();
    }

    /**
     * Method description
     *
     *
     * @param state
     * @param sender
     */
    @Override
    public void OnSelect(int state, Object sender) {
        TableNode node = (TableNode) sender;

        this.m_WhTable.Check(node);
        UpdateWHInfo();
        UpdateMap();
    }

    /**
     * Method description
     *
     *
     * @param node
     * @param text
     */
    @Override
    public void SetupLineInTable(TableNode node, MENUText_field text) {}

    /**
     * Method description
     *
     *
     * @param node
     * @param button
     */
    @Override
    public void SetupLineInTable(TableNode node, MENUsimplebutton_field button) {}

    /**
     * Method description
     *
     *
     * @param node
     * @param radio
     */
    @Override
    public void SetupLineInTable(TableNode node, MENUbutton_field radio) {
        WarehouseInfo wh = (WarehouseInfo) node.item;

        radio.text = wh.name;
        menues.SetFieldState(radio.nativePointer, (node.checked)
                ? 1
                : 0);
    }

    /**
     * Method description
     *
     *
     * @param type
     * @param node
     * @param control
     */
    @Override
    public void SetupLineInTable(int type, TableNode node, Object control) {}

    /**
     * Method description
     *
     *
     * @param event
     * @param node
     * @param group
     * @param _menu
     */
    @Override
    public void OnEvent(long event, TableNode node, long group, long _menu) {
        this.m_WhTable.Check(node);
        UpdateWHInfo();
        UpdateMap();
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        AfterInitMenu();
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
        if (this.m_WhTable != null) {
            this.m_WhTable.DeInit();
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void enterFocus() {
        Refresh();
    }

    /**
     * Method description
     *
     */
    @Override
    public void leaveFocus() {}

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(2);
        }
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static class WarehouseSorter extends TableCmp {

        /**
         * Method description
         *
         *
         * @param o1
         * @param o2
         *
         * @return
         */
        @Override
        public int compare(Object o1, Object o2) {
            WarehouseInfo w1 = (WarehouseInfo) o1;
            WarehouseInfo w2 = (WarehouseInfo) o2;

            return Common.Compare(w1.name, w2.name, this.order);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
