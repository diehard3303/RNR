/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

import gameobj.WHOrderInfo;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import menu.BalanceUpdater;
import menu.BaseMenu;
import menu.Cmenu_TTI;
import menu.Common;
import menu.JavaEventCb;
import menu.JavaEvents;
import menu.KeyPair;
import menu.ListenerManager;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.RNRMap;
import menu.RNRMap.Priority;
import menu.SMenu;
import menu.Table;
import menu.Table.TableVisitor;
import menu.TableCallbacks;
import menu.TableCmp;
import menu.TableNode;
import menu.TextHolder;
import menu.TextScroller;
import menu.TextureFrame;
import menu.menucreation;
import menu.menues;
import rnrcore.Helper;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.loc;
import rnrscenario.ScenarioFlagsManager;
import rnrscr.gameinfo;
import rnrscr.gameinfo.Warehouse;

public class WHmenues extends BaseMenu implements TableCallbacks, JavaEventCb,
		menucreation {
	public static final int TYPE_DELIVERY = 0;
	public static final int TYPE_TENDER = 1;
	public static final int TYPE_CONTEST = 2;
	public static final int ORDER_TYPE = 3;
	public static final int SHIPTO = 4;
	public static final int FREIGHT = 5;
	public static final int CHARGES = 6;
	public static final int COMPETITION = 7;
	public static final int FORFEIT = 8;
	public static final int TIME_ALLOWED = 9;
	public static final int WEIGHT = 10;
	public static final int FRAQ = 11;
	public static final int DISTANCE = 12;
	public static final int SHIPTO_GOLD = 13;
	public static final int FREIGHT_GOLD = 14;
	public static final int CHARGES_GOLD = 15;
	public static final int PIC_GOLD = 16;
	static long s_table;
	int s_CurrentSort;
	static int s_OrderTypeFlag = 1;
	static int s_ShipToSortFlag = 2;
	static int s_ChargesSortFlag = 3;
	static int s_ForfeitSortFlag = 4;
	static int s_TimeSortFlag = 5;
	static int s_WeightSortFlag = 6;
	static int s_FraqSortFlag = 7;
	static int s_CompetitionSortFlag = 8;
	static int s_FreightSortFlag = 9;
	static int s_DistanceSortFlag = 10;
	static int s_CargoTypeSortFlag = 11;
	boolean s_bOrder;
	int ID;
	static long s_menu;
	long s_prevhightlightgroup;
	long s_highlightgroup;
	long balanc_control;
	long ok_button;
	long cancel_button;
	TableNode selected_node;
	int highLightedLine;
	boolean bMouseMove;
	boolean bWarehouseEnabled;
	private final Object latch;
	static Vector s_Arrows;
	TextureFrame m_raceframe;
	TextureFrame m_defaultframe;
	TextureFrame m_trailerframe;
	Table m_Table;
	TextHolder m_OrderDetails;
	TextHolder m_StatusDetails;
	PopUpLongWarehouseInfo pop_up;
	long picture_control;
	private static final String POPUP_XML_FILE = "..\\data\\config\\menu\\menu_warehouse.xml";
	private static final String POPUP_CONTROLGROUP = "Panel - Common - DESTINATION NOT IN AREA";
	private static final String POPUP_WINDOW = "ALL - Common - DESTINATION NOT IN AREA";
	private static final String[] POPUP_BUTTONS = { "BUTTON - CANCEL",
			"BUTTON - OK" };
	private static final String[] POPUP_METHODS = { "OnCancel", "OnOk" };
	private PoPUpMenu not_in_area;
	NotInAreaWarning not_in_area_listener;
	static int s_iFragPicX;
	static int s_iFragPicY;
	static int s_iFragTextX;
	static int s_iFragTextY;
	MENUText_field m_fraqpic;
	MENUText_field m_fraqtext;
	FindWhTraverse m_FindWh;
	MaterialUpdateName material_name;
	public boolean inOurArea;
	public static boolean whmenu_immediateshow = false;
	int m_filterstate;
	RNRMap m_map;
	String charges_text;
	CheckWhFlags races;
	private PoPUpMenu show_help;
	private long help_text;
	private long help_text_scroller;
	TextScroller scroller;
	Common common;

	public WHmenues() {
		this.s_CurrentSort = 0;

		this.s_bOrder = false;
		this.ID = 0;

		this.s_prevhightlightgroup = -1L;
		this.s_highlightgroup = -1L;
		this.balanc_control = 0L;
		this.ok_button = 0L;
		this.cancel_button = 0L;
		this.selected_node = null;
		this.highLightedLine = -1;
		this.bMouseMove = false;
		this.bWarehouseEnabled = true;
		this.latch = new Object();

		this.m_raceframe = new TextureFrame();
		this.m_defaultframe = new TextureFrame();
		this.m_trailerframe = new TextureFrame();

		this.pop_up = null;
		this.picture_control = 0L;

		this.not_in_area = null;

		this.not_in_area_listener = null;

		this.m_FindWh = new FindWhTraverse();

		this.material_name = new MaterialUpdateName();

		this.inOurArea = true;

		this.charges_text = null;

		this.races = null;

		this.show_help = null;
		this.help_text = 0L;
		this.help_text_scroller = 0L;
		this.scroller = null;
	}

	public void restartMenu(long _menu) {
	}

	public void InitMenu(long _menu) {
		this.common = new Common(_menu);
		ListenerManager.TriggerEvent(103);
		menues.InitXml(_menu, Common.ConstructPath("menu_warehouse.xml"),
				"COMMON");

		menues.InitXml(_menu, Common.ConstructPath("menu_warehouse.xml"),
				"MenuWarehouse");

		menues.InitXml(_menu, Common.ConstructPath("menu_warehouse.xml"),
				"MenuOffice VEHICLE Sale - BILL");

		menues.InitXml(_menu, Common.ConstructPath("menu_warehouse.xml"),
				"MenuWarehouse - TABLE Order details");

		InitTable(_menu);
		InitHolder();
		InitSorters();
		this.m_trailerframe.Init(4, 5, 512, 512);
		this.m_map = this.common.FindRNRMap("MAP - zooming picture");
		this.m_raceframe.Init(4, 1, 512, 128);
		this.m_defaultframe.Init(1, 1, 256, 256);
		this.picture_control = menues.InitXmlControl(_menu,
				Common.ConstructPath("menu_warehouse.xml"),
				"MenuWarehouse - TABLE ShiptoFreightCharges",
				"Freights type - PIC");

		menues.SetFieldParentName(this.picture_control,
				"GROUP 04 - Order details SORT");

		gameinfo info = gameinfo.script;

		JavaEvents.RegisterEvent(8, this);
		if ((info.m_CurrentWarehouse >= 0)
				&& (info.m_CurrentWarehouse < info.aWarehouses.size())) {
			gameinfo.Warehouse currentw = (gameinfo.Warehouse) info.aWarehouses
					.get(info.m_CurrentWarehouse);

			MENUText_field title = this.common
					.FindTextField("WarehouseFRAME - TITLE");

			KeyPair[] keys = null;
			keys = new KeyPair[1];
			keys[0] = new KeyPair("CITY_NAME", "" + currentw.name.toUpperCase());
			title.text = MacroKit.Parse(title.text, keys);
			menues.UpdateField(title);
		}

		this.balanc_control = menues.FindFieldInMenu(_menu,
				"Your balance - VALUE");

		this.ok_button = menues.FindFieldInMenu(_menu, "BUTTON - OK");
		this.cancel_button = menues.FindFieldInMenu(_menu, "BUTTON - CANCEL");

		this.pop_up = new PopUpLongWarehouseInfo(_menu);
		this.not_in_area = new PoPUpMenu(_menu,
				"..\\data\\config\\menu\\menu_warehouse.xml",
				"Panel - Common - DESTINATION NOT IN AREA",
				"ALL - Common - DESTINATION NOT IN AREA", POPUP_BUTTONS,
				POPUP_METHODS, false);
		this.not_in_area_listener = new NotInAreaWarning(_menu);
		this.not_in_area.addListener(this.not_in_area_listener);

		this.show_help = new PoPUpMenu(_menu,
				Common.ConstructPath("menu_warehouse.xml"), "TOOLTIP - CWA");
		this.help_text = this.show_help.getField("CALL WAREHOUSE HELP - TEXT");
		this.help_text_scroller = this.show_help
				.getField("CALL WAREHOUSE HELP - Tableranger");
	}

	public void DeInit() {
		BalanceUpdater.RemoveBalanceControl(this.balanc_control);
		if (this.m_Table != null) {
			this.m_Table.DeInit();
		}
		if (this.scroller != null)
			this.scroller.Deinit();
	}

	boolean CompareWhItems(WHOrderInfo o1, WHOrderInfo o2) {
		return (o1.nativep == o2.nativep);
	}

	void RefreshOrders() {
		CheckIsWarehouseEnabled();
		synchronized (this.latch) {
			gameinfo info = gameinfo.script;
			WHOrderInfo lastitem = (WHOrderInfo) this.m_Table
					.GetSingleChecked().item;
			int topline = this.m_Table.GetTop().line;
			TableNode t = null;
			for (int i = 0; i < info.CurrenWareHouseOrders.size(); ++i) {
				TableNode temp = this.m_Table.AddItem(null,
						info.CurrenWareHouseOrders.get(i), false);

				if ((lastitem != null)
						&& (CompareWhItems(
								(WHOrderInfo) info.CurrenWareHouseOrders.get(i),
								lastitem))) {
					t = temp;
				}
			}

			this.m_Table.RefillTree();
			this.m_Table.RestoreLastSort();
			if (t != null) {
				this.m_Table.Check(t);
			} else {
				this.pop_up.hide();
				this.not_in_area.hide();
			}
			int lowesttop = Math.max(0, info.CurrenWareHouseOrders.size()
					- this.m_Table.GetLineNum());

			topline = Math.min(topline, lowesttop);
			this.m_Table.SetTop(this.m_Table.GetNodeByLine(topline));

			InitMap(this.common.GetMenu(), false);
		}
	}

	String ConstructCargoTexName(String cargoid) {
		return "tex_CargoLabel_GAME_" + cargoid;
	}

	void InitHolder() {
		this.m_OrderDetails = new TextHolder(this.common);
		this.m_OrderDetails.AttachControl(3,
				this.common.FindTextField("Order Type - VALUE"));

		this.m_OrderDetails.AttachControl(6,
				this.common.FindTextField("Fee - VALUE"));

		this.m_OrderDetails.AttachControl(7,
				this.common.FindTextField("Competition - VALUE"));

		this.m_OrderDetails.AttachControl(8,
				this.common.FindTextField("Forfeit - VALUE"));

		this.m_OrderDetails.AttachControl(4,
				this.common.FindTextField("Ship to - VALUE"));

		this.m_OrderDetails.AttachControl(9,
				this.common.FindTextField("Time allowed - VALUE"));

		this.m_OrderDetails.AttachControl(5,
				this.common.FindTextField("Freight - VALUE"));

		this.m_OrderDetails.AttachControl(11,
				this.common.FindTextField("Fragility - VALUE"));

		this.m_OrderDetails.AttachControl(12,
				this.common.FindTextField("Distance - VALUE"));

		this.m_OrderDetails.AttachControl(10,
				this.common.FindTextField("Weight - VALUE"));

		this.m_fraqpic = this.common.FindTextField("Fragility - Progress Ind");
		this.m_fraqtext = this.common.FindTextField("Fragility - VALUE");

		s_iFragPicX = this.m_fraqpic.lenx;
		s_iFragTextX = this.m_fraqtext.lenx;

		this.m_StatusDetails = new TextHolder(this.common);
		this.m_StatusDetails.AttachControl(4,
				this.common.FindTextField("1 VALUE"));
		this.m_StatusDetails.AttachControl(3,
				this.common.FindTextField("2 VALUE"));

		this.m_StatusDetails.AttachControl(5,
				this.common.FindTextField("3 VALUE"));
		this.m_StatusDetails.AttachControl(10,
				this.common.FindTextField("4 VALUE"));
		this.m_StatusDetails.AttachControl(11,
				this.common.FindTextField("5 VALUE"));
		this.m_StatusDetails.AttachControl(9,
				this.common.FindTextField("6 VALUE"));

		this.m_StatusDetails.AttachControl(6,
				this.common.FindTextField("7 VALUE"));
	}

	void InitSorters() {
		this.common.SetScriptOnButtonUserid("Ship to", this, "OnSort", 4);
		this.common.SetScriptOnButtonUserid("Fee", this, "OnSort", 6);
		this.common.SetScriptOnButtonUserid("Order Type", this, "OnSort", 3);

		this.common.SetScriptOnButtonUserid("Competition", this, "OnSort", 7);

		this.common.SetScriptOnButtonUserid("Forfeit", this, "OnSort", 8);
		this.common.SetScriptOnButtonUserid("Time allowed", this, "OnSort", 9);

		this.common.SetScriptOnButtonUserid("Freight", this, "OnSort", 5);
		this.common.SetScriptOnButtonUserid("Weight", this, "OnSort", 10);
		this.common.SetScriptOnButtonUserid("Fragility", this, "OnSort", 11);
		this.common.SetScriptOnButtonUserid("Distance", this, "OnSort", 12);
		this.common.SetScriptOnButtonUserid("BUTTON - Type", this, "OnSort", 3);

		this.common.SetScriptOnButtonUserid("BUTTON - Ship to", this, "OnSort",
				4);

		this.common.SetScriptOnButtonUserid("BUTTON - Freight", this, "OnSort",
				5);

		this.common.SetScriptOnButtonUserid("BUTTON - Fee", this, "OnSort", 6);
		this.common.SetScriptOnButton("Button DETAILS", this, "ShowDetails");
	}

	void OnSort(long _menu, MENUsimplebutton_field button) {
		this.m_Table.SortTable(button.userid, new SortWhOrder(button.userid));
		UpdateCurrentOrder();
	}

	void ShowDetails(long _menu, MENUsimplebutton_field button) {
		WHOrderInfo order = (WHOrderInfo) this.m_Table.GetSingleChecked().item;

		if (null == order) {
			return;
		}

		if (this.pop_up != null)
			this.pop_up.show(order.shipto_full);
	}

	void InitTable(long _menu) {
		menues.CreateTable(_menu);
		CheckIsWarehouseEnabled();

		gameinfo info = gameinfo.script;

		this.m_Table = new Table(_menu, "Warehouse table");
		this.m_Table.AddRadioButton("ShiptoVALUES - TABLE", 4);
		this.m_Table.AddRadioButton("FreightVALUES - TABLE", 5);
		this.m_Table.AddRadioButton("ChargesVALUES - TABLE", 6);

		this.m_Table.AddRadioButton("ShiptoVALUES GoldOrder - TABLE", 13);
		this.m_Table.AddRadioButton("FreightVALUES GoldOrder - TABLE", 14);
		this.m_Table.AddRadioButton("ChargesVALUES GoldOrder - TABLE", 15);

		this.m_Table.AddRadioButton("TypeDELIVERY - TABLE", 0);
		this.m_Table.AddRadioButton("TypeCONTEST - TABLE", 2);
		this.m_Table.AddRadioButton("TypeTENDER - TABLE", 1);
		this.m_Table.AddTextField("GoldOrder - PIC", 16);

		this.m_Table.AddEvent(2);
		this.m_Table.AddEvent(6);
		this.m_Table.AddEvent(5);

		synchronized (this.latch) {
			for (int i = 0; i < info.CurrenWareHouseOrders.size(); ++i) {
				this.m_Table.AddItem(null, info.CurrenWareHouseOrders.get(i),
						false);
			}
		}

		this.m_Table.Setup(38L, 8, Common.ConstructPath("menu_warehouse.xml"),
				"MenuWarehouse - TABLE ShiptoFreightCharges",
				"TABLEGROUP 02 - ShiptoFreightCharges TABLE", this, 11);

		this.m_Table.AttachRanger(this.common
				.FindScroller("ShipTo - tableranger"));
	}

	public void OnCargo(long _menu, MENUbutton_field field) {
		gameinfo info = gameinfo.script;
		int index = info.m_CurrentOrder;
		if (index == -1)
			index = 0;
	}

	public void OnScroll(long _menu, MENU_ranger field) {
		Cmenu_TTI tti_global = menues.GetXMLDataOnTable(_menu, s_table);
		for (int i = 0; i < tti_global.children.size(); ++i) {
			Cmenu_TTI node_local = (Cmenu_TTI) tti_global.children.get(i);
			node_local.ontop = (i == field.current_value);
		}

		menues.UpdateDataWithChildren(tti_global);

		menues.ConnectTableAndData(_menu, s_table);

		menues.RedrawTable(_menu, s_table);
	}

	void AddButtonCallbackInGroup(long _menu, long _group, String name) {
		long pControl = menues.FindFieldInMenu(_menu, name);
		MENUsimplebutton_field control = menues.ConvertSimpleButton(pControl);
		menues.AddControlInGroup(_menu, _group, control);
		menues.LinkGroupAndControl(_menu, control);
		menues.StoreControlState(_menu, control);
	}

	public void InitCallbacks(long _menu, long table) {
		long group = menues.CreateGroup(_menu);

		AddButtonCallbackInGroup(_menu, group, "Ship to");
		AddButtonCallbackInGroup(_menu, group, "Fee");
		AddButtonCallbackInGroup(_menu, group, "Order Type");
		AddButtonCallbackInGroup(_menu, group, "Competition");

		AddButtonCallbackInGroup(_menu, group, "Forfeit");
		AddButtonCallbackInGroup(_menu, group, "Time allowed");
		AddButtonCallbackInGroup(_menu, group, "Freight");
		AddButtonCallbackInGroup(_menu, group, "Weight");
		AddButtonCallbackInGroup(_menu, group, "Fragility");
		AddButtonCallbackInGroup(_menu, group, "Distance");

		long pControl = menues.FindFieldInMenu(_menu, "BUTTON - Fee");
		MENUsimplebutton_field control = menues.ConvertSimpleButton(pControl);
		menues.AddControlInGroup(_menu, group, control);
		menues.LinkGroupAndControl(_menu, control);
		menues.StoreControlState(_menu, control);

		pControl = menues.FindFieldInMenu(_menu, "BUTTON - Ship to");
		control = menues.ConvertSimpleButton(pControl);
		menues.AddControlInGroup(_menu, group, control);
		menues.LinkGroupAndControl(_menu, control);
		menues.StoreControlState(_menu, control);

		pControl = menues.FindFieldInMenu(_menu, "BUTTON - Freight");
		control = menues.ConvertSimpleButton(pControl);
		menues.AddControlInGroup(_menu, group, control);
		menues.LinkGroupAndControl(_menu, control);
		menues.StoreControlState(_menu, control);

		menues.LingGroupAndTable(_menu, group, table);
		menues.SetScriptOnTable(_menu, table, this, "SortCallback", 4L);
	}

	public void SortItems(long _menu, long table, MenuComparator comp,
			int sortflag) {
		if (this.s_CurrentSort == sortflag) {
			this.s_bOrder = (!(this.s_bOrder));
		} else {
			this.s_CurrentSort = sortflag;
			this.s_bOrder = true;
		}

		comp.SetOrder(this.s_bOrder);
		Cmenu_TTI root = menues.GetXMLDataOnTable(_menu, table);
		Collections.sort(root.children, comp);
		for (int i = 0; i < root.children.size(); ++i)
			((Cmenu_TTI) root.children.get(i)).ontop = (i == 0);
		menues.UpdateDataWithChildren(root);
		menues.ConnectTableAndData(_menu, table);
		menues.RedrawTable(_menu, table);
	}

	public void OnTrailerSort(long _menu, MENUsimplebutton_field button) {
		SortItems(_menu, s_table, new CargoTypeComparator(),
				s_CargoTypeSortFlag);
	}

	public void OnTypeSort(long _menu, MENUsimplebutton_field button) {
		SortItems(_menu, s_table, new OrderValueComparator(), s_OrderTypeFlag);
	}

	public void SortCallback(long _menu, long table) {
		long pSortType = menues.FindFieldInMenu(_menu, "Order Type");
		long pSortCharges = menues.FindFieldInMenu(_menu, "Fee");
		long pSortCharges1 = menues.FindFieldInMenu(_menu, "BUTTON - Fee");
		long pSortCompetition = menues.FindFieldInMenu(_menu, "Competition");
		long pSortForfeit = menues.FindFieldInMenu(_menu, "Forfeit");
		long pSortShipto = menues.FindFieldInMenu(_menu, "Ship to");
		long pSortShipto1 = menues.FindFieldInMenu(_menu, "BUTTON - Ship to");
		long pSortTime = menues.FindFieldInMenu(_menu, "Time allowed");
		long pSortFreight = menues.FindFieldInMenu(_menu, "Freight");
		long pSortFreight1 = menues.FindFieldInMenu(_menu, "BUTTON - Freight");
		long pSortWeight = menues.FindFieldInMenu(_menu, "Weight");
		long pSortFraq = menues.FindFieldInMenu(_menu, "Fragility");
		long pSortDistance = menues.FindFieldInMenu(_menu, "Distance");

		if (menues.GetStoredState(_menu, pSortType) == 4L) {
			SortItems(_menu, table, new OrderValueComparator(), s_OrderTypeFlag);
		}

		if ((menues.GetStoredState(_menu, pSortCharges) == 4L)
				|| (menues.GetStoredState(_menu, pSortCharges1) == 4L)) {
			SortItems(_menu, table, new ChargesComparator(), s_ChargesSortFlag);
		}

		if (menues.GetStoredState(_menu, pSortCompetition) == 4L) {
			SortItems(_menu, table, new CompetitionComparator(),
					s_CompetitionSortFlag);
		}

		if (menues.GetStoredState(_menu, pSortForfeit) == 4L) {
			SortItems(_menu, table, new ForfeitComparator(), s_ForfeitSortFlag);
		}

		if ((menues.GetStoredState(_menu, pSortShipto) == 4L)
				|| (menues.GetStoredState(_menu, pSortShipto1) == 4L)) {
			SortItems(_menu, table, new ShipToComparator(), s_ShipToSortFlag);
		}

		if (menues.GetStoredState(_menu, pSortTime) == 4L) {
			SortItems(_menu, table, new TimeComparator(), s_TimeSortFlag);
		}
		if ((menues.GetStoredState(_menu, pSortFreight) == 4L)
				|| (menues.GetStoredState(_menu, pSortFreight1) == 4L)) {
			SortItems(_menu, table, new FreightComparator(), s_FreightSortFlag);
		}
		if (menues.GetStoredState(_menu, pSortWeight) == 4L) {
			SortItems(_menu, table, new WeightComparator(), s_WeightSortFlag);
		}
		if (menues.GetStoredState(_menu, pSortDistance) == 4L) {
			SortItems(_menu, table, new DistanceComparator(),
					s_DistanceSortFlag);
		}

		if (menues.GetStoredState(_menu, pSortFraq) == 4L)
			SortItems(_menu, table, new FraqComparator(), s_FraqSortFlag);
	}

	public void FillOrderTable(Cmenu_TTI table) {
		CheckIsWarehouseEnabled();
		gameinfo info = gameinfo.script;
		synchronized (this.latch) {
			table.children = new Vector();
			for (int i = 0; i < info.CurrenWareHouseOrders.size(); ++i) {
				Cmenu_TTI child = new Cmenu_TTI();
				table.children.add(child);
				child.item = info.CurrenWareHouseOrders.get(i);
				WHOrderInfo localitem = (WHOrderInfo) child.item;
				localitem.accept = false;

				child.toshow = true;
				child.ontop = (i == 0);
				child.children = new Vector();
			}
		}

		menues.UpdateDataWithChildren(table);
	}

	public RNRMap FindMap(long _menu) {
		long pMap = menues.FindFieldInMenu(_menu, "MAP - zooming picture");
		return menues.ConvertRNRMAPFields(pMap);
	}

	public void ClearAccept(Cmenu_TTI table) {
		for (int i = 0; i < table.children.size(); ++i) {
			Cmenu_TTI localmenu = (Cmenu_TTI) table.children.get(i);
			WHOrderInfo localitem = (WHOrderInfo) localmenu.item;
			localitem.accept = false;
		}
	}

	public void SelectMapObject(int state, int ID) {
		boolean found = false;
		int result = 0;
		for (int i = 0; i < s_Arrows.size(); ++i) {
			Arrow ar = (Arrow) s_Arrows.get(i);
			if (ID == ar.ID) {
				result = i;
				found = true;
				break;
			}
		}

		if (!(found)) {
			return;
		}
		Arrow ar = (Arrow) s_Arrows.get(result);
		this.m_FindWh.SetUp((WHOrderInfo) ar.orders.get(0));
		this.m_Table.Traverse(this.m_FindWh);
		if (this.m_FindWh.GetNode() != null) {
			this.m_Table.Check(this.m_FindWh.GetNode());
		}

		menues.RedrawTable(s_menu, s_table);
	}

	public void MouseOutside(long _menu, long _group) {
		Cmenu_TTI node_item = menues.GetXMLDataOnGroup(_menu, _group);
		WHOrderInfo localitem = (WHOrderInfo) node_item.item;

		if (localitem.arrowindex >= s_Arrows.size())
			return;
		Arrow ar = (Arrow) s_Arrows.get(localitem.arrowindex);
		RNRMap map = FindMap(_menu);
		map.HighlightMapObject(ar.ID, false);
	}

	public void MouseInside(long _menu, long _group) {
		Cmenu_TTI node_item = menues.GetXMLDataOnGroup(_menu, _group);
		WHOrderInfo localitem = (WHOrderInfo) node_item.item;
		if ((s_Arrows == null) || (localitem.arrowindex >= s_Arrows.size()))
			return;
		Arrow ar = (Arrow) s_Arrows.get(localitem.arrowindex);
		RNRMap map = FindMap(_menu);
		map.HighlightMapObject(ar.ID, true);
	}

	public void SelectOrder(long _menu, int orderindex) {
		Cmenu_TTI tti_global = menues.GetXMLDataOnTable(_menu, s_table);

		int index = -1;
		int topindex = -1;
		for (int i = 0; i < tti_global.children.size(); ++i) {
			Cmenu_TTI node_order = (Cmenu_TTI) tti_global.children.get(i);
			if (node_order.ontop == true)
				topindex = i;
			WHOrderInfo order = (WHOrderInfo) node_order.item;
			if (orderindex == order.WH_slot_ID) {
				index = i;
			}
		}
		Cmenu_TTI node_order = (Cmenu_TTI) tti_global.children.get(index);
		WHOrderInfo localitem = (WHOrderInfo) node_order.item;

		if ((topindex != -1)
				&& (((index - topindex >= 8) || (index < topindex)))) {
			Cmenu_TTI topnode = (Cmenu_TTI) tti_global.children.get(topindex);
			topnode.ontop = false;
			menues.UpdateData(topnode);
			int newtopindex;
			int newtopindex;
			if (index - topindex >= 8) {
				newtopindex = (tti_global.children.size() - index < 8) ? Math
						.max(tti_global.children.size() - 8, 0) : index;
			} else {
				newtopindex = index;
			}
			topnode = (Cmenu_TTI) tti_global.children.get(newtopindex);
			topnode.ontop = true;
			menues.UpdateData(topnode);
			menues.ConnectTableAndData(_menu, s_table);
		}

		if (!(localitem.accept)) {
			ClearAccept(tti_global);
			localitem.accept = true;
			UpdateControlData(_menu, localitem);
		}
		menues.RedrawAll(_menu);
	}

	public void ButtonPress(long _menu, long _group) {
		CheckIsWarehouseEnabled();
		long table = menues.GetTable_byGroup(_menu, _group);
		Cmenu_TTI tti_group = menues.GetXMLDataOnGroup(_menu, _group);
		WHOrderInfo localitem = (WHOrderInfo) tti_group.item;
		if (!(localitem.accept)) {
			Cmenu_TTI tti_global = menues.GetXMLDataOnTable(_menu, table);
			ClearAccept(tti_global);
			localitem.accept = true;
			UpdateControlData(_menu, localitem);
		}

		menues.RedrawAll(_menu);

		gameinfo info = gameinfo.script;

		long pMap = menues.FindFieldInMenu(_menu, "MAP - zooming picture");
		RNRMap map = menues.ConvertRNRMAPFields(pMap);

		synchronized (this.latch) {
			info.m_CurrentOrder = localitem.WH_slot_ID;
			if (info.m_CurrentOrder >= info.CurrenWareHouseOrders.size()) {
				return;
			}
			WHOrderInfo curorder = (WHOrderInfo) info.CurrenWareHouseOrders
					.get(info.m_CurrentOrder);

			if (curorder.arrowindex >= s_Arrows.size())
				return;
			Arrow ar = (Arrow) s_Arrows.get(curorder.arrowindex);
			map.SelectMapObject(ar.ID, true);
		}
	}

	public void UpdateControlData(long _menu, WHOrderInfo item) {
		gameinfo info = gameinfo.script;
		info.m_CurrentOrder = item.WH_slot_ID;

		this.m_OrderDetails.FillWHOrderInfo(item);
		this.m_StatusDetails.FillWHOrderInfo(item);

		String ship_to = null;
		String fee = null;

		if (item.order_type == 0) {
			ship_to = loc.getMENUString("common\\Ship To");
			fee = loc.getMENUString("common\\Max Fee");
		} else if (item.order_type == 1) {
			ship_to = loc.getMENUString("common\\Finish");
			fee = loc.getMENUString("common\\Gran Prix");
		} else if (item.order_type == 2) {
			ship_to = loc.getMENUString("common\\Pick Up Site");
			fee = loc.getMENUString("common\\Max Fee");
		} else {
			ship_to = loc.getMENUString("common\\Ship To");
			fee = loc.getMENUString("common\\Max Fee");
		}

		MENUsimplebutton_field b = null;
		this.common.SetTextValue("7 TITLE", fee);
		b = this.common.FindSimpleButton("Fee");
		b.text = fee;
		menues.UpdateField(b);

		this.common.SetTextValue("1 TITLE", ship_to);
		b = this.common.FindSimpleButton("Ship to");
		b.text = ship_to;
		menues.UpdateField(b);

		MENUsimplebutton_field trailers = this.common
				.FindSimpleButton("Trailer type");

		MENUsimplebutton_field races = this.common
				.FindSimpleButton("Race type");
		MENUsimplebutton_field racefreight = this.common
				.FindSimpleButton("RaceFreights type");

		MENUsimplebutton_field freights = this.common
				.FindSimpleButton("Freights type");

		menues.SetShowField(trailers.nativePointer, item.order_type == 0);

		menues.SetShowField(races.nativePointer, item.order_type != 0);

		menues.SetShowField(freights.nativePointer, item.order_type == 0);

		menues.SetShowField(racefreight.nativePointer, item.order_type != 0);

		menues.SetShowField(this.picture_control, item.order_type == 0);

		switch (item.order_type) {
		case 0:
			this.m_trailerframe.ApplyToPatch(trailers.nativePointer,
					item.cargox, item.cargoy, "photo");

			this.material_name.name = ConstructCargoTexName(item.cargotypeid);
			JavaEvents.SendEvent(66, 0, this.material_name);

			break;
		case 2:
			this.m_raceframe.ApplyToPatch(races.nativePointer, 0, 0, "photo");
			this.m_raceframe.ApplyToPatch(racefreight.nativePointer, 3, 0,
					"photo");
			break;
		case 1:
			this.m_raceframe.ApplyToPatch(races.nativePointer, 0, 0, "photo");
			this.m_raceframe.ApplyToPatch(racefreight.nativePointer, 1, 0,
					"photo");
		}
	}

	public void SetupLineInTable(MENUbutton_field button, Cmenu_TTI table) {
		WHOrderInfo item = (WHOrderInfo) table.item;
		if (button.nameID.indexOf("ShiptoVALUES - TABLE") != -1) {
			button.text = " " + item.shipto;
			int state = 0;
			if (item.accept)
				state = 1;
			menues.SetFieldState(button.nativePointer, state);
		}

		if (button.nameID.indexOf("FreightVALUES - TABLE") != -1) {
			button.text = " " + item.freight;
			int state = 0;
			if (item.accept)
				state = 1;
			menues.SetFieldState(button.nativePointer, state);
		}

		if (button.nameID.indexOf("ChargesVALUES - TABLE") != -1) {
			button.text = " $" + item.charges;
			int state = 0;
			if (item.accept)
				state = 1;
			menues.SetFieldState(button.nativePointer, state);
		}

		if (button.nameID.indexOf("TypeDELIVERY - TABLE") != -1) {
			int state = 0;
			if (item.accept)
				state = 1;
			boolean vis = item.order_type == 0;
			menues.SetFieldState(button.nativePointer, state);
			menues.SetShowField(button.nativePointer, vis);
		}

		if (button.nameID.indexOf("TypeCONTEST - TABLE") != -1) {
			int state = 0;
			if (item.accept)
				state = 1;
			boolean vis = item.order_type == 1;
			menues.SetFieldState(button.nativePointer, state);
			menues.SetShowField(button.nativePointer, vis);
		}

		if (button.nameID.indexOf("TypeTENDER - TABLE") != -1) {
			int state = 0;
			if (item.accept)
				state = 1;
			boolean vis = item.order_type == 2;
			menues.SetFieldState(button.nativePointer, state);
			menues.SetShowField(button.nativePointer, vis);
		}
	}

	public int FindWarehouseByName(String name, Vector data) {
		for (int i = 0; i < data.size(); ++i) {
			gameinfo.Warehouse wh = (gameinfo.Warehouse) data.get(i);
			if (wh.name.equals(name))
				return i;
		}
		return -1;
	}

	public void SetupZoom(long _menu) {
		for (int i = 0; i < 10; ++i) {
			long control = menues.FindFieldInMenu(_menu, "MAP zoomSCALE 0" + i);
			MENUsimplebutton_field button = menues.ConvertSimpleButton(control);
			menues.SetScriptOnControl(_menu, button, this, "OnZoomScale", 4L);
		}
	}

	public void OnZoomScale(long _menu, MENUsimplebutton_field field) {
		int number = field.nameID.charAt(field.nameID.length() - 1) - '0';
		float minzoom = 1.0F;
		float maxzoom = 12.0F;
		float num = 10.0F;
		float zoom = (maxzoom - minzoom) / (num - 1.0F) * number + minzoom;

		RNRMap map = FindMap(_menu);
		map.Zoom(zoom);
	}

	public void InitDebugMap(long _menu) {
		RNRMap map = FindMap(_menu);
		RNRMap tmp11_10 = map;
		tmp11_10.getClass();
		RNRMap.Priority pr = new RNRMap.Priority(tmp11_10);
		pr.SetPriority(0, false, false, 1);
		pr.SetPriority(1, false, false, 1);
		pr.SetPriority(2, false, false, 1);

		pr.SetPriority(0, true, false, 2);
		pr.SetPriority(1, true, false, 2);
		pr.SetPriority(2, true, false, 2);

		pr.SetPriority(0, true, true, 3);
		pr.SetPriority(1, true, true, 3);
		pr.SetPriority(2, true, true, 3);

		pr.SetPriority(0, false, true, 3);
		pr.SetPriority(1, false, true, 3);
		pr.SetPriority(2, false, true, 3);
		map.SetDefaultIconPicturePriority(6, pr);

		int ID = 0;
		int posy = 0;
		int posx = 0;
		for (int i = 0; i < 37; ++i) {
			map.SetClickableGroup(i, true);
			if ((i == 6) || (i == 7))
				continue;
			if (i == 8) {
				continue;
			}

			map.AddMapObject(i, (float) (-0.8D + posx),
					(float) (-0.8D + posy * 0.15D), false, false, ID++, "880",
					"");

			map.AddMapObject(i, (float) (-0.6D + posx),
					(float) (-0.8D + posy * 0.15D), true, false, ID++, "880",
					"");

			map.AddMapObject(i, (float) (-0.4D + posx),
					(float) (-0.8D + posy * 0.15D), false, true, ID++, "880",
					"");

			map.AddMapObject(i, (float) (-0.2D + posx),
					(float) (-0.8D + posy * 0.15D), true, true, ID++, "880", "");

			++posy;

			if (posy > 10) {
				posy = 0;
				++posx;
			}

		}

		map.AddMapObject(0, 0.1F, 0.5F, false, false, ID++, "880", "");
		map.AddMapObject(0, 0.1F, 0.9F, false, false, ID++, "880", "");
		map.AddOrder(6, ID - 2, ID - 1, false, false, ID++, "");

		map.AddMapObject(0, 0.3F, 0.5F, false, false, ID++, "880", "");
		map.AddMapObject(0, 0.3F, 0.9F, false, false, ID++, "880", "");
		map.AddOrder(6, ID - 2, ID - 1, true, false, ID++, "");

		map.AddMapObject(0, 0.5F, 0.5F, false, false, ID++, "880", "");

		map.AddMapObject(0, 0.5F, 0.9F, false, false, ID++, "880", "");

		map.AddOrder(6, ID - 2, ID - 1, false, true, ID++, "");

		map.AddMapObject(0, 0.7000001F, 0.5F, false, false, ID++, "880", "");

		map.AddMapObject(0, 0.7000001F, 0.9F, false, false, ID++, "880", "");

		map.AddOrder(6, ID - 2, ID - 1, true, true, ID++, "");
	}

	public void InitMap(long _menu, boolean isrezoom) {
		CheckIsWarehouseEnabled();

		gameinfo info = gameinfo.script;

		long pMap = menues.FindFieldInMenu(_menu, "MAP - zooming picture");
		if (pMap == 0L) {
			return;
		}
		RNRMap map = menues.ConvertRNRMAPFields(pMap);
		menues.SetScriptOnControlDataPass(_menu, map, this, "OnMapclick", 7L);

		if (isrezoom) {
			map.Init(0, 0, 0, 0, 0L);
			map.SetStartPosition();
			map.SetState(1);
		}
		RNRMap tmp78_76 = map;
		tmp78_76.getClass();
		RNRMap.Priority arrowspr = new RNRMap.Priority(tmp78_76);
		arrowspr.SetPriority(0, false, false, 1);
		arrowspr.SetPriority(1, false, false, 1);
		arrowspr.SetPriority(2, false, false, 1);

		arrowspr.SetPriority(0, true, false, 2);
		arrowspr.SetPriority(1, true, false, 2);
		arrowspr.SetPriority(2, true, false, 2);

		arrowspr.SetPriority(0, true, true, 3);
		arrowspr.SetPriority(1, true, true, 3);
		arrowspr.SetPriority(2, true, true, 3);

		arrowspr.SetPriority(0, false, true, 3);
		arrowspr.SetPriority(1, false, true, 3);
		arrowspr.SetPriority(2, false, true, 3);

		map.ClearObjects();

		map.SetDefaultIconPicturePriority(6, arrowspr);
		RNRMap tmp216_214 = map;
		tmp216_214.getClass();
		RNRMap.Priority whpr = new RNRMap.Priority(tmp216_214);
		whpr.SetPriority(0, false, false, 0);
		whpr.SetPriority(1, false, false, 0);
		whpr.SetPriority(2, false, false, 0);

		whpr.SetPriority(0, true, false, 0);
		whpr.SetPriority(1, true, false, 0);
		whpr.SetPriority(2, true, false, 0);

		whpr.SetPriority(0, true, true, 0);
		whpr.SetPriority(1, true, true, 0);
		whpr.SetPriority(2, true, true, 0);

		whpr.SetPriority(0, false, true, 0);
		whpr.SetPriority(1, false, true, 0);
		whpr.SetPriority(2, false, true, 0);

		map.SetDefaultIconPicturePriority(3, whpr);
		map.SetDefaultIconPicturePriority(4, whpr);

		for (int i = 0; i < info.aWarehouses.size(); ++i) {
			gameinfo.Warehouse w = (gameinfo.Warehouse) info.aWarehouses.get(i);
			w.ID = this.ID;
			w.arrowindex = -1;
			if (w.bIsMine)
				map.AddMapObject(4, (float) w.x, (float) w.y, false, false,
						this.ID++, w.name, "");
			else {
				map.AddMapObject(3, (float) w.x, (float) w.y, false, false,
						this.ID++, w.name, "");
			}
		}

		map.SetClickableGroup(6, true);

		s_Arrows = new Vector();
		if (info.m_CurrentWarehouse == -1) {
			return;
		}
		gameinfo.Warehouse currentw = (gameinfo.Warehouse) info.aWarehouses
				.get(info.m_CurrentWarehouse);
		RNRMap tmp541_539 = map;
		tmp541_539.getClass();
		RNRMap.Priority curwhpr = new RNRMap.Priority(tmp541_539);
		curwhpr.SetPriority(0, false, false, 10);
		curwhpr.SetPriority(1, false, false, 10);
		curwhpr.SetPriority(2, false, false, 10);
		map.SetMapObjectPicturePriority(currentw.ID, curwhpr);

		double minx = currentw.x;
		double miny = currentw.y;
		double maxx = currentw.x;
		double maxy = currentw.y;
		synchronized (this.latch) {
			for (int i = 0; i < info.CurrenWareHouseOrders.size(); ++i) {
				WHOrderInfo order = (WHOrderInfo) info.CurrenWareHouseOrders
						.get(i);

				int index = FindWarehouseByName(order.shipto, info.aWarehouses);
				gameinfo.Warehouse targetw = (gameinfo.Warehouse) info.aWarehouses
						.get(index);

				if (targetw.x < minx)
					minx = targetw.x;
				if (targetw.y < miny) {
					miny = targetw.y;
				}
				if (targetw.x > maxx)
					maxx = targetw.x;
				if (targetw.y > maxy)
					maxy = targetw.y;
				Arrow ar;
				if (targetw.arrowindex == -1) {
					Arrow ar = new Arrow();
					ar.target = targetw;
					ar.ID = (this.ID++);
					ar.orders = new Vector();
					s_Arrows.add(ar);
					targetw.arrowindex = (s_Arrows.size() - 1);
				} else {
					ar = (Arrow) s_Arrows.get(targetw.arrowindex);
				}
				order.arrowindex = targetw.arrowindex;
				ar.orders.add(order);
			}

		}

		Vector v = (Vector) s_Arrows.clone();
		Collections.sort(v, new Comparator(currentw.x, currentw.y) {
			double x;
			double y;

			public int compare(Object o1, Object o2) {
				WHmenues.Arrow a1 = (WHmenues.Arrow) o1;
				WHmenues.Arrow a2 = (WHmenues.Arrow) o2;
				double len1 = (a1.target.x - this.x) * (a1.target.x - this.x)
						+ (a1.target.y - this.y) * (a1.target.y - this.y);

				double len2 = (a2.target.x - this.x) * (a2.target.x - this.x)
						+ (a2.target.y - this.y) * (a2.target.y - this.y);

				return Common.Sign(len2 - len1);
			}
		});
		for (int i = 0; i < v.size(); ++i) {
			Arrow ar = (Arrow) v.get(i);
			map.AddOrder(6, currentw.ID, ar.target.ID, false, false, ar.ID, "");
		}

		WHOrderInfo order0 = (this.m_Table.GetSingleChecked() != null) ? (WHOrderInfo) this.m_Table
				.GetSingleChecked().item : null;
		if (order0 != null) {
			Arrow arrow0 = (Arrow) s_Arrows.get(order0.arrowindex);
			map.SelectMapObject(arrow0.ID, true);
		}

		if (isrezoom) {
			double shiftx = Math.min((maxx - minx) * 0.25D,
					(2.0D - (maxx - minx)) / 2.0D);

			double shifty = Math.min((maxy - miny) * 0.1D,
					(2.0D - (maxy - miny)) / 2.0D);

			minx -= shiftx;
			maxx += shiftx;

			miny -= shifty;
			maxy += shifty;

			miny = Math.max(Math.min(miny, 1.0D), -1.0D);
			maxy = Math.max(Math.min(maxy, 1.0D), -1.0D);
			minx = Math.max(Math.min(minx, 1.0D), -1.0D);
			maxx = Math.max(Math.min(maxx, 1.0D), -1.0D);

			if (maxy - miny > maxx - minx) {
				double len = maxy - miny;
				double halfdelta = (maxy - miny - (maxx - minx)) / 2.0D;

				if (minx - halfdelta < -1.0D) {
					minx = -1.0D;
					maxx = -1.0D + len;
				} else if (maxx + halfdelta > 1.0D) {
					maxx = 1.0D;
					minx = 1.0D - len;
				} else {
					minx -= halfdelta;
					maxx += halfdelta;
				}
			}

			if (maxx - minx > maxy - miny) {
				double len = maxx - minx;
				double halfdelta = (maxx - minx - (maxy - miny)) / 2.0D;

				if (miny - halfdelta < -1.0D) {
					miny = -1.0D;
					maxy = -1.0D + len;
				} else if (maxy + halfdelta > 1.0D) {
					maxy = 1.0D;
					miny = 1.0D - len;
				} else {
					miny -= halfdelta;
					maxy += halfdelta;
				}
			}

			float centerx = (float) (minx + maxx) / 2.0F;
			float centery = (float) (miny + maxy) / 2.0F;

			float zoom = (float) (2.0D / (maxx - minx));
			map.Zoom(zoom);
			map.Move(centerx, centery);
		}
	}

	public void OnOk(long _menu, MENUsimplebutton_field b) {
		CheckIsWarehouseEnabled();
		boolean bCanDoOk = true;
		synchronized (this.latch) {
			if (!(this.bWarehouseEnabled)) {
				bCanDoOk = false;
			}
		}
		if (bCanDoOk) {
			this.inOurArea = true;
			JavaEvents.SendEvent(71, 6, this);
			if ((this.inOurArea) || (this.not_in_area_listener == null))
				menues.CallMenuCallBack_OKMenu(_menu);
			else if (!(this.not_in_area_listener.NeedToShow()))
				menues.CallMenuCallBack_OKMenu(_menu);
			else
				this.not_in_area.show();
		} else {
			OnCancel(this.common.s_menu, null);
		}
	}

	public void OnExit(long _menu, SMenu button) {
		if (!(this.not_in_area_listener.IsOnScreen()))
			OnCancel(this.common.s_menu, null);
	}

	public void OnCancel(long _menu, MENUsimplebutton_field b) {
		menues.CallMenuCallBack_ExitMenu(_menu);
	}

	public void OnMapclick(long _menu, RNRMap map, long data) {
		for (int i = 0; i < s_Arrows.size(); ++i) {
			Arrow ar = (Arrow) s_Arrows.get(i);
			if (ar.ID == data) {
				this.m_FindWh.SetUp((WHOrderInfo) ar.orders.get(0));
				this.m_Table.Traverse(this.m_FindWh);
				if (this.m_FindWh.GetNode() != null) {
					this.m_Table.Check(this.m_FindWh.GetNode());
					UpdateCurrentOrder();
				}
			}
		}
	}

	public void exitMenu(long _menu) {
		if (this.races != null) {
			this.races.Exit();
		}
		DeInit();
		ListenerManager.TriggerEvent(105);
		JavaEvents.UnregisterEvent(8);
		event.Setevent(9001);
	}

	public void AfterInitMenu(long _menu) {
		ListenerManager.TriggerEvent(104);
		BalanceUpdater.AddBalanceControl(this.balanc_control);

		if (whmenu_immediateshow) {
			whmenu_immediateshow = false;
			menues.setShowMenu(_menu, true);
		}

		menues.SetIgnoreEvents(this.picture_control, true);
		menues.SetShowField(this.picture_control, false);

		long c = menues.FindFieldInMenu(_menu, "ShiptoVALUES - TABLE_0");
		menues.setfocuscontrolonmenu(_menu, c);

		this.common.SetScriptOnButton("BUTTON - OK", this, "OnOk");
		this.common.SetScriptOnButton("BUTTON - CANCEL", this, "OnCancel");

		gameinfo info = gameinfo.script;
		info.m_CurrentOrder = 0;
		info.m_HighlightedOrder = -1;

		this.m_Table.SyncLineStates();

		this.m_map.AttachStandardControls(this.common, "GROUP 03 - MAP");

		InitMap(_menu, true);

		menues.SetShowField(
				this.common.FindSimpleButton("Freights type").nativePointer,
				false);

		if (!(eng.noNative)) {
			this.races = new CheckWhFlags(_menu);
		}

		this.pop_up.afterInit();
		this.not_in_area.afterInit();
		this.show_help.afterInit();

		menues.SetScriptOnControl(_menu,
				menues.ConvertMenuFields(menues.GetBackMenu(_menu)), this,
				"OnExit", 17L);

		long control = menues.FindFieldInMenu(_menu, "CALL WAREHOUSE HELP");
		menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control),
				this, "ShowHelp", 4L);
	}

	public void SetupHandSelectMode(long _menu) {
		long pMAPhand = menues.FindFieldInMenu(_menu, "MAP hand");
		MENUbutton_field maphand = menues.ConvertButton(pMAPhand);

		long pMAPselect = menues.FindFieldInMenu(_menu, "MAP select");
		MENUbutton_field mapselect = menues.ConvertButton(pMAPselect);

		menues.SetFieldState(pMAPhand, 1);
		menues.SetFieldState(pMAPselect, 0);

		menues.SetScriptOnControl(_menu, maphand, this, "OnHandSelect", 2L);

		menues.SetScriptOnControl(_menu, mapselect, this, "OnHandSelect", 2L);
	}

	public void OnHandSelect(long _menu, MENUbutton_field field) {
		int state = menues.GetFieldState(field.nativePointer);
		if (state == 0) {
			menues.SetFieldState(field.nativePointer, 1);
			return;
		}

		if (field.nameID.equals("MAP hand")) {
			RNRMap m = FindMap(_menu);
			m.SetState(1);

			long pSelect = menues.FindFieldInMenu(_menu, "MAP select");
			menues.SetFieldState(pSelect, 0);
		}

		if (field.nameID.equals("MAP select")) {
			RNRMap m = FindMap(_menu);
			m.SetState(3);

			long pHand = menues.FindFieldInMenu(_menu, "MAP hand");
			menues.SetFieldState(pHand, 0);
		}
	}

	public void OnMapUp(long _menu, MENUsimplebutton_field field) {
		long pMap = menues.FindFieldInMenu(_menu, "MAP - zooming picture");
		RNRMap map = menues.ConvertRNRMAPFields(pMap);
		map.MoveUp();
	}

	public void OnMapDown(long _menu, MENUsimplebutton_field field) {
		long pMap = menues.FindFieldInMenu(_menu, "MAP - zooming picture");
		RNRMap map = menues.ConvertRNRMAPFields(pMap);
		map.MoveDown();
	}

	public void OnMapLeft(long _menu, MENUsimplebutton_field field) {
		long pMap = menues.FindFieldInMenu(_menu, "MAP - zooming picture");
		RNRMap map = menues.ConvertRNRMAPFields(pMap);
		map.MoveLeft();
	}

	public void OnMapRight(long _menu, MENUsimplebutton_field field) {
		long pMap = menues.FindFieldInMenu(_menu, "MAP - zooming picture");
		RNRMap map = menues.ConvertRNRMAPFields(pMap);
		map.MoveRight();
	}

	public void Zoomin(long _menu, MENUsimplebutton_field field) {
		long pMap = menues.FindFieldInMenu(_menu, "MAP - zooming picture");
		if (pMap == 0L)
			return;
		RNRMap map = menues.ConvertRNRMAPFields(pMap);

		map.ZoomIn();
	}

	public void Zoomout(long _menu, MENUsimplebutton_field field) {
		long pMap = menues.FindFieldInMenu(_menu, "MAP - zooming picture");
		if (pMap == 0L)
			return;
		RNRMap map = menues.ConvertRNRMAPFields(pMap);

		map.ZoomOut();
	}

	public void SetupLineInTable(TableNode node, MENUText_field text) {
		WHOrderInfo item = (WHOrderInfo) node.item;
		menues.SetShowField(text.nativePointer, item.order_coolness != 0);
	}

	public void SetupLineInTable(TableNode node, MENUsimplebutton_field button) {
	}

	boolean In(int a, int a1, int a2) {
		return ((a >= a1) && (a <= a2));
	}

	public void SetupLineInTable(TableNode node, MENUbutton_field radio) {
		WHOrderInfo item = (WHOrderInfo) node.item;
		menues.SetFieldState(radio.nativePointer, (node.checked) ? 1 : 0);

		if ((In(radio.userid, 4, 6)) || (In(radio.userid, 13, 15))) {
			menues.SetShowField(radio.nativePointer,
					((!(In(radio.userid, 4, 6))) ? 1 : 0)
							^ ((item.order_coolness == 0) ? 1 : 0));
		}

		switch (radio.userid) {
		case 4:
		case 13:
			radio.text = item.shipto;
			break;
		case 5:
		case 14:
			radio.text = item.freight;
			break;
		case 6:
		case 15:
			radio.text = "$ " + Converts.ConvertNumeric(item.charges);

			break;
		case 0:
			menues.SetShowField(radio.nativePointer, item.order_type == 0);

			return;
		case 2:
			menues.SetShowField(radio.nativePointer, item.order_type == 1);

			return;
		case 1:
			menues.SetShowField(radio.nativePointer, item.order_type == 2);

			return;
		case 3:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		}
	}

	void UpdateCurrentOrder() {
		WHOrderInfo order = (this.m_Table.GetSingleChecked() != null) ? (WHOrderInfo) this.m_Table
				.GetSingleChecked().item : null;
		this.m_OrderDetails.FillWHOrderInfo(order);
		this.m_StatusDetails.FillWHOrderInfo(order);
		if (null == order) {
			return;
		}
		gameinfo.script.m_CurrentOrder = order.WH_slot_ID;

		this.m_fraqpic.lenx = (int) (order.fragil * s_iFragPicX);
		this.m_fraqtext.lenx = Math.max(this.m_fraqpic.lenx, s_iFragTextX);
		menues.UpdateField(this.m_fraqpic);
		menues.UpdateField(this.m_fraqtext);

		String ship_to = null;
		String fee = null;

		if (order.order_type == 0) {
			ship_to = loc.getMENUString("common\\Ship To");
			fee = loc.getMENUString("common\\Max Fee");
		} else if (order.order_type == 1) {
			ship_to = loc.getMENUString("common\\Finish");
			fee = loc.getMENUString("common\\Gran Prix");
		} else if (order.order_type == 2) {
			ship_to = loc.getMENUString("common\\Pick Up Site");
			fee = loc.getMENUString("common\\Max Fee");
		} else {
			ship_to = loc.getMENUString("common\\Ship To");
			fee = loc.getMENUString("common\\Max Fee");
		}

		MENUsimplebutton_field b = null;
		this.common.SetTextValue("7 TITLE", fee);
		b = this.common.FindSimpleButton("Fee");
		b.text = fee;
		menues.UpdateField(b);

		this.common.SetTextValue("1 TITLE", ship_to);
		b = this.common.FindSimpleButton("Ship to");
		b.text = ship_to;
		menues.UpdateField(b);

		MENUsimplebutton_field trailers = this.common
				.FindSimpleButton("Trailer type");

		MENUsimplebutton_field races = this.common
				.FindSimpleButton("Race type");
		MENUsimplebutton_field racefreight = this.common
				.FindSimpleButton("RaceFreights type");

		MENUsimplebutton_field freights = this.common
				.FindSimpleButton("Freights type");

		menues.SetShowField(trailers.nativePointer, order.order_type == 0);

		menues.SetShowField(races.nativePointer, order.order_type != 0);

		menues.SetShowField(freights.nativePointer, order.order_type == 0);

		menues.SetShowField(racefreight.nativePointer, order.order_type != 0);

		menues.SetShowField(this.picture_control, order.order_type == 0);

		switch (order.order_type) {
		case 0:
			this.m_trailerframe.ApplyToPatch(trailers.nativePointer,
					order.cargox, order.cargoy, "photo");

			this.material_name.name = ConstructCargoTexName(order.cargotypeid);
			JavaEvents.SendEvent(66, 0, this.material_name);

			break;
		case 2:
			this.m_raceframe.ApplyToPatch(races.nativePointer, 0, 0, "photo");
			this.m_raceframe.ApplyToPatch(racefreight.nativePointer, 3, 0,
					"photo");
			break;
		case 1:
			this.m_raceframe.ApplyToPatch(races.nativePointer, 0, 0, "photo");
			this.m_raceframe.ApplyToPatch(racefreight.nativePointer, 1, 0,
					"photo");
		}

		if (order.arrowindex >= s_Arrows.size())
			return;
		Arrow ar = (Arrow) s_Arrows.get(order.arrowindex);
		this.m_map.SelectMapObject(ar.ID, true);
	}

	public void SetupLineInTable(int type, TableNode node, Object control) {
	}

	void OnRadioPress(TableNode node) {
		this.m_Table.Check(node);
		UpdateCurrentOrder();
	}

	void OnMouseInside(TableNode node) {
		this.bMouseMove = true;
		this.highLightedLine = node.line;
	}

	void OnMouseOutside(TableNode node) {
		this.bMouseMove = true;
	}

	public void OnEvent(long event, TableNode node, long group, long _menu) {
		if (event == 2L)
			OnRadioPress(node);
		else if (event == 6L)
			OnMouseInside(node);
		else if (event == 5L)
			OnMouseOutside(node);
	}

	public void OnEvent(int ID, int value, Object obj) {
		if (value == 0) {
			RefreshOrders();
		} else if (value == 1) {
			if (this.bMouseMove) {
				for (int i = 0; i < s_Arrows.size(); ++i) {
					Arrow ar = (Arrow) s_Arrows.get(i);
					this.m_map.HighlightMapObject(ar.ID, false);
				}
			}

			if (this.highLightedLine != -1) {
				this.m_FindWh.SetUp(this.highLightedLine);
				this.m_Table.Traverse(this.m_FindWh);
				if (this.m_FindWh.GetNode() != null) {
					WHOrderInfo localitem = (WHOrderInfo) this.m_FindWh
							.GetNode().item;
					if ((localitem == null) || (s_Arrows == null)
							|| (localitem.arrowindex >= s_Arrows.size()))
						return;
					Arrow ar = (Arrow) s_Arrows.get(localitem.arrowindex);
					this.m_map.HighlightMapObject(ar.ID, true);
				}
			}

			this.bMouseMove = false;
			this.highLightedLine = -1;
		}
	}

	public String getMenuId() {
		return "warehouseMENU";
	}

	public void ShowHelp(long _menu, MENUsimplebutton_field button) {
		ShowTabHelp();
	}

	public void ShowTabHelp() {
		if ((this.help_text != 0L) && (this.help_text_scroller != 0L)) {
			MENU_ranger ranger = (MENU_ranger) menues
					.ConvertMenuFields(this.help_text_scroller);
			MENUText_field text = (MENUText_field) menues
					.ConvertMenuFields(this.help_text);
			if ((ranger != null) && (text != null)) {
				int texh = menues.GetTextLineHeight(text.nativePointer);
				int startbase = menues.GetBaseLine(text.nativePointer);
				int linescreen = Converts.HeightToLines(text.leny, startbase,
						texh);
				int linecounter = Converts.HeightToLines(
						menues.GetTextHeight(text.nativePointer, text.text),
						startbase, texh) + 3;
				if (this.scroller != null) {
					this.scroller.Deinit();
				}

				this.scroller = new TextScroller(this.common, ranger,
						linecounter, linescreen, texh, startbase, true,
						"CALL WAREHOUSE HELP - TEXT");
				this.scroller.AddTextControl(text);
			}
		}

		this.show_help.show();
	}

	private boolean CheckIsWarehouseEnabled() {
		boolean ret = false;
		if ((ScenarioFlagsManager.getInstance() != null)
				&& (!(ScenarioFlagsManager.getInstance()
						.getFlagValue("WarehousesEnabledByScenario"))))
			synchronized (this.latch) {
				if ((gameinfo.script.CurrenWareHouseOrders != null)
						&& (((!(gameinfo.script.CurrenWareHouseOrders.isEmpty())) || (this.bWarehouseEnabled)))) {
					gameinfo.script.CurrenWareHouseOrders.clear();
					ret = true;
				}

				this.bWarehouseEnabled = false;
			}
		else {
			synchronized (this.latch) {
				if (!(this.bWarehouseEnabled)) {
					ret = true;
				}
				this.bWarehouseEnabled = true;
			}
		}
		return ret;
	}

	class CheckWhFlags extends TypicalAnm {
		boolean menu_exited = false;

		long button_race_01 = 0L;

		long button_race_02 = 0L;

		long button_race_03 = 0L;

		long button_race_04 = 0L;

		CheckWhFlags(long paramLong) {
			eng.CreateInfinitScriptAnimation(this);

			this.button_race_01 = menues.FindFieldInMenu(paramLong,
					"BUTTON - AWAIT - Race04");

			this.button_race_02 = menues.FindFieldInMenu(paramLong,
					"BUTTON - AWAIT - Race03");

			this.button_race_03 = menues.FindFieldInMenu(paramLong,
					"BUTTON - AWAIT - Race02");

			this.button_race_04 = menues.FindFieldInMenu(paramLong,
					"BUTTON - AWAIT - Race01");

			if ((this.button_race_01 != 0L)
					&& (menues.ConvertSimpleButton(this.button_race_01) != null)) {
				menues.SetScriptOnControl(paramLong,
						menues.ConvertSimpleButton(this.button_race_01), this,
						"WaitForRace", 4L);
			}

			if ((this.button_race_02 != 0L)
					&& (menues.ConvertSimpleButton(this.button_race_02) != null)) {
				menues.SetScriptOnControl(paramLong,
						menues.ConvertSimpleButton(this.button_race_02), this,
						"WaitForRace", 4L);
			}

			if ((this.button_race_03 != 0L)
					&& (menues.ConvertSimpleButton(this.button_race_03) != null)) {
				menues.SetScriptOnControl(paramLong,
						menues.ConvertSimpleButton(this.button_race_03), this,
						"WaitForRace", 4L);
			}

			if ((this.button_race_04 == 0L)
					|| (menues.ConvertSimpleButton(this.button_race_04) == null))
				return;
			menues.SetScriptOnControl(paramLong,
					menues.ConvertSimpleButton(this.button_race_04), this,
					"WaitForRace", 4L);
		}

		public void Exit() {
			this.menu_exited = true;
		}

		void WaitForRace(long _menu, MENUsimplebutton_field button) {
			if (WHmenues.this.ok_button != 0L) {
				menues.SetIgnoreEvents(WHmenues.this.ok_button, true);
			}

			if (WHmenues.this.cancel_button != 0L) {
				menues.SetIgnoreEvents(WHmenues.this.cancel_button, true);
			}

			Helper.waitCheckinOnWarehouse();
		}

		public boolean animaterun(double dt) {
			if (this.menu_exited) {
				return true;
			}

			if (WHmenues.this.CheckIsWarehouseEnabled() != 0) {
				synchronized (WHmenues.this.latch) {
					WHmenues.this.RefreshOrders();
				}
			}
			if (!(WHmenues.this.bWarehouseEnabled)) {
				if (this.button_race_01 != 0L) {
					menues.SetShowField(this.button_race_01, false);
				}
				if (this.button_race_02 != 0L) {
					menues.SetShowField(this.button_race_02, false);
				}
				if (this.button_race_03 != 0L) {
					menues.SetShowField(this.button_race_03, false);
				}
				if (this.button_race_04 != 0L)
					menues.SetShowField(this.button_race_04, false);
			} else {
				int res = Helper.isWarehouseWaitCheckin();
				if (res < 0) {
					if (this.button_race_01 != 0L) {
						menues.SetShowField(this.button_race_01, false);
					}
					if (this.button_race_02 != 0L) {
						menues.SetShowField(this.button_race_02, false);
					}
					if (this.button_race_03 != 0L) {
						menues.SetShowField(this.button_race_03, false);
					}
					if (this.button_race_04 != 0L)
						menues.SetShowField(this.button_race_04, false);
				} else if (res == 1) {
					if (this.button_race_01 != 0L) {
						menues.SetShowField(this.button_race_01, true);
					}
					if (this.button_race_02 != 0L) {
						menues.SetShowField(this.button_race_02, false);
					}
					if (this.button_race_03 != 0L) {
						menues.SetShowField(this.button_race_03, false);
					}
					if (this.button_race_04 != 0L)
						menues.SetShowField(this.button_race_04, false);
				} else if (res == 2) {
					if (this.button_race_01 != 0L) {
						menues.SetShowField(this.button_race_01, false);
					}
					if (this.button_race_02 != 0L) {
						menues.SetShowField(this.button_race_02, true);
					}
					if (this.button_race_03 != 0L) {
						menues.SetShowField(this.button_race_03, false);
					}
					if (this.button_race_04 != 0L)
						menues.SetShowField(this.button_race_04, false);
				} else if (res == 3) {
					if (this.button_race_01 != 0L) {
						menues.SetShowField(this.button_race_01, false);
					}
					if (this.button_race_02 != 0L) {
						menues.SetShowField(this.button_race_02, false);
					}
					if (this.button_race_03 != 0L) {
						menues.SetShowField(this.button_race_03, true);
					}
					if (this.button_race_04 != 0L)
						menues.SetShowField(this.button_race_04, false);
				} else if (res == 4) {
					if (this.button_race_01 != 0L) {
						menues.SetShowField(this.button_race_01, false);
					}
					if (this.button_race_02 != 0L) {
						menues.SetShowField(this.button_race_02, false);
					}
					if (this.button_race_03 != 0L) {
						menues.SetShowField(this.button_race_03, false);
					}
					if (this.button_race_04 != 0L)
						menues.SetShowField(this.button_race_04, true);
				} else {
					if (this.button_race_01 != 0L) {
						menues.SetShowField(this.button_race_01, false);
					}
					if (this.button_race_02 != 0L) {
						menues.SetShowField(this.button_race_02, false);
					}
					if (this.button_race_03 != 0L) {
						menues.SetShowField(this.button_race_03, false);
					}
					if (this.button_race_04 != 0L) {
						menues.SetShowField(this.button_race_04, false);
					}
				}
			}

			return false;
		}
	}

	static class MaterialUpdateName {
		String name;

		MaterialUpdateName() {
			this.name = null;
		}
	}

	class FindWhTraverse implements Table.TableVisitor {
		TableNode node;
		WHOrderInfo order;
		int line;

		TableNode GetNode() {
			return this.node;
		}

		void SetUp(WHOrderInfo order) {
			this.node = null;
			this.order = order;
			this.line = -1;
		}

		void SetUp(int line) {
			this.node = null;
			this.order = null;
			this.line = line;
		}

		public void VisitNode(TableNode node) {
			WHOrderInfo info = (WHOrderInfo) node.item;
			if (this.order != null) {
				if ((info != null)
						&& (WHmenues.this.CompareWhItems(this.order, info))) {
					this.node = node;
				}
			} else if ((info != null) && (node.line == this.line))
				this.node = node;
		}
	}

	class PopUpLongWarehouseInfo {
		private PoPUpMenu menu = null;

		private long text = 0L;
		private static final String MENU_GROUP = "TOOLTIP - MenuWarehouse - Destination DETAILS";
		private static final String TOOLTIP_TEXT = "MW - Destination DETAILS - VALUE";
		private static final String XML = "..\\data\\config\\menu\\menu_warehouse.xml";

		PopUpLongWarehouseInfo(long paramLong) {
			this.menu = new PoPUpMenu(paramLong,
					"..\\data\\config\\menu\\menu_warehouse.xml",
					"TOOLTIP - MenuWarehouse - Destination DETAILS",
					"TOOLTIP - MenuWarehouse - Destination DETAILS");
			this.text = this.menu.getField("MW - Destination DETAILS - VALUE");
		}

		public void afterInit() {
			this.menu.afterInit();
		}

		void show(String name) {
			menues.SetFieldText(this.text, name);
			menues.UpdateMenuField(menues.ConvertMenuFields(this.text));
			this.menu.show();
		}

		void hide() {
			this.menu.hide();
		}
	}

	static class SortWhOrder extends TableCmp {
		int type;

		public SortWhOrder(int _type) {
			this.type = _type;
		}

		public int compare(Object o1, Object o2) {
			WHOrderInfo c1 = (WHOrderInfo) o1;
			WHOrderInfo c2 = (WHOrderInfo) o2;
			switch (this.type) {
			case 4:
				return Common.Compare(c1.shipto, c2.shipto, this.order);
			case 6:
				return Common.Compare(c1.charges, c2.charges, this.order);
			case 3:
				return Common.Compare(c1.order_type, c2.order_type, this.order);
			case 7:
				return Common.Compare(c1.competition, c2.competition,
						this.order);
			case 8:
				return Common.Compare(c1.forfeit, c2.forfeit, this.order);
			case 9:
				return Common.Compare(c1.time_limit_hour * 60
						+ c1.time_limit_min, c2.time_limit_hour * 60
						+ c2.time_limit_min, this.order);
			case 5:
				return Common.Compare(c1.freight, c2.freight, this.order);
			case 10:
				return Common.Compare(c1.weight, c2.weight, this.order);
			case 11:
				return Common.Compare(c1.fragil, c2.fragil, this.order);
			case 12:
				return Common.Compare(c1.distance, c2.distance, this.order);
			}
			return 0;
		}
	}

	public class WeightComparator implements WHmenues.MenuComparator {
		int order;

		public void SetOrder(boolean isascending) {
			this.order = ((isascending) ? 1 : -1);
		}

		public int compare(Object o1, Object o2) {
			Cmenu_TTI node1 = (Cmenu_TTI) o1;
			Cmenu_TTI node2 = (Cmenu_TTI) o2;
			WHOrderInfo item1 = (WHOrderInfo) node1.item;
			WHOrderInfo item2 = (WHOrderInfo) node2.item;
			double d = item1.weight - item2.weight;
			int diff = 0;
			if (d > 0.0D)
				diff = 1;
			if (d < 0.0D)
				diff = -1;
			return (diff * this.order);
		}
	}

	public class FraqComparator implements WHmenues.MenuComparator {
		int order;

		public void SetOrder(boolean isascending) {
			this.order = ((isascending) ? 1 : -1);
		}

		public int compare(Object o1, Object o2) {
			Cmenu_TTI node1 = (Cmenu_TTI) o1;
			Cmenu_TTI node2 = (Cmenu_TTI) o2;
			WHOrderInfo item1 = (WHOrderInfo) node1.item;
			WHOrderInfo item2 = (WHOrderInfo) node2.item;
			double d = item1.fragil - item2.fragil;
			int diff = 0;
			if (d > 0.0D)
				diff = 1;
			if (d < 0.0D)
				diff = -1;
			return (diff * this.order);
		}
	}

	public class ForfeitComparator implements WHmenues.MenuComparator {
		int order;

		public void SetOrder(boolean isascending) {
			this.order = ((isascending) ? 1 : -1);
		}

		public int compare(Object o1, Object o2) {
			Cmenu_TTI node1 = (Cmenu_TTI) o1;
			Cmenu_TTI node2 = (Cmenu_TTI) o2;
			WHOrderInfo item1 = (WHOrderInfo) node1.item;
			WHOrderInfo item2 = (WHOrderInfo) node2.item;
			int diff = item1.forfeit - item2.forfeit;
			return (diff * this.order);
		}
	}

	public class CompetitionComparator implements WHmenues.MenuComparator {
		int order;

		public void SetOrder(boolean isascending) {
			this.order = ((isascending) ? -1 : 1);
		}

		public int compare(Object o1, Object o2) {
			Cmenu_TTI node1 = (Cmenu_TTI) o1;
			Cmenu_TTI node2 = (Cmenu_TTI) o2;
			WHOrderInfo item1 = (WHOrderInfo) node1.item;
			WHOrderInfo item2 = (WHOrderInfo) node2.item;
			int a = (item1.competition) ? 1 : 0;
			int b = (item2.competition) ? 1 : 0;
			return ((a - b) * this.order);
		}
	}

	public class DistanceComparator implements WHmenues.MenuComparator {
		int order;

		public void SetOrder(boolean isascending) {
			this.order = ((isascending) ? -1 : 1);
		}

		public int compare(Object o1, Object o2) {
			Cmenu_TTI node1 = (Cmenu_TTI) o1;
			Cmenu_TTI node2 = (Cmenu_TTI) o2;
			WHOrderInfo item1 = (WHOrderInfo) node1.item;
			WHOrderInfo item2 = (WHOrderInfo) node2.item;
			double d = item1.distance - item2.distance;
			int diff = 0;
			if (d > 0.0D)
				diff = 1;
			if (d < 0.0D)
				diff = -1;
			return (diff * this.order);
		}
	}

	public class ChargesComparator implements WHmenues.MenuComparator {
		int order;

		public void SetOrder(boolean isascending) {
			this.order = ((isascending) ? -1 : 1);
		}

		public int compare(Object o1, Object o2) {
			Cmenu_TTI node1 = (Cmenu_TTI) o1;
			Cmenu_TTI node2 = (Cmenu_TTI) o2;
			WHOrderInfo item1 = (WHOrderInfo) node1.item;
			WHOrderInfo item2 = (WHOrderInfo) node2.item;
			int diff = item1.charges - item2.charges;
			return (diff * this.order);
		}
	}

	public class TimeComparator implements WHmenues.MenuComparator {
		int order;

		public void SetOrder(boolean isascending) {
			this.order = ((isascending) ? 1 : -1);
		}

		public int compare(Object o1, Object o2) {
			Cmenu_TTI node1 = (Cmenu_TTI) o1;
			Cmenu_TTI node2 = (Cmenu_TTI) o2;
			WHOrderInfo item1 = (WHOrderInfo) node1.item;
			WHOrderInfo item2 = (WHOrderInfo) node2.item;
			int diff = item1.time_limit_hour * 60 + item1.time_limit_min
					- (item2.time_limit_hour * 60 + item2.time_limit_min);

			return (diff * this.order);
		}
	}

	public class FreightComparator implements WHmenues.MenuComparator {
		int order;

		public void SetOrder(boolean isascending) {
			this.order = ((isascending) ? 1 : -1);
		}

		public int compare(Object o1, Object o2) {
			Cmenu_TTI node1 = (Cmenu_TTI) o1;
			Cmenu_TTI node2 = (Cmenu_TTI) o2;
			WHOrderInfo item1 = (WHOrderInfo) node1.item;
			WHOrderInfo item2 = (WHOrderInfo) node2.item;
			return (item1.freight.compareTo(item2.freight) * this.order);
		}
	}

	public class ShipToComparator implements WHmenues.MenuComparator {
		int order;

		public void SetOrder(boolean isascending) {
			this.order = ((isascending) ? 1 : -1);
		}

		public int compare(Object o1, Object o2) {
			Cmenu_TTI node1 = (Cmenu_TTI) o1;
			Cmenu_TTI node2 = (Cmenu_TTI) o2;
			WHOrderInfo item1 = (WHOrderInfo) node1.item;
			WHOrderInfo item2 = (WHOrderInfo) node2.item;
			return (item1.shipto.compareTo(item2.shipto) * this.order);
		}
	}

	public class CargoTypeComparator implements WHmenues.MenuComparator {
		int order;

		public void SetOrder(boolean isascending) {
			this.order = ((isascending) ? 1 : -1);
		}

		public int compare(Object o1, Object o2) {
			Cmenu_TTI node1 = (Cmenu_TTI) o1;
			Cmenu_TTI node2 = (Cmenu_TTI) o2;
			WHOrderInfo item1 = (WHOrderInfo) node1.item;
			WHOrderInfo item2 = (WHOrderInfo) node2.item;
			return Common.Compare(item1.cargotype, item2.cargotype,
					this.order == 1);
		}
	}

	public class OrderValueComparator implements WHmenues.MenuComparator {
		int order;

		public void SetOrder(boolean isascending) {
			this.order = ((isascending) ? 1 : -1);
		}

		public int compare(Object o1, Object o2) {
			Cmenu_TTI node1 = (Cmenu_TTI) o1;
			Cmenu_TTI node2 = (Cmenu_TTI) o2;
			WHOrderInfo item1 = (WHOrderInfo) node1.item;
			WHOrderInfo item2 = (WHOrderInfo) node2.item;
			return Common.Compare(item1.order_type, item2.order_type,
					this.order == 1);
		}
	}

	public static abstract interface MenuComparator extends Comparator {
		public abstract void SetOrder(boolean paramBoolean);
	}

	class NotInAreaWarning implements IPoPUpMenuListener {
		long check_box = 0L;
		int in_value = 0;
		long _menu = 0L;
		boolean onScreen = false;

		public NotInAreaWarning(long paramLong) {
			JavaEvents.SendEvent(71, 4, this);
			this._menu = paramLong;
			this.onScreen = false;
		}

		public boolean NeedToShow() {
			return (this.in_value == 0);
		}

		public void onAgreeclose() {
			this.onScreen = false;
			if ((WHmenues.this.not_in_area != null) && (this.check_box != 0L)) {
				int check_value = menues.GetFieldState(this.check_box);
				if (this.in_value != check_value) {
					this.in_value = check_value;
					JavaEvents.SendEvent(71, 5, this);
				}
			}

			menues.CallMenuCallBack_OKMenu(this._menu);
		}

		public void onCancel() {
			if ((WHmenues.this.not_in_area == null) || (this.check_box == 0L))
				return;
			int check_value = menues.GetFieldState(this.check_box);
			if (this.in_value != check_value) {
				this.in_value = check_value;
				JavaEvents.SendEvent(71, 5, this);
			}
		}

		public void onClose() {
			this.onScreen = false;
			JavaEvents.SendEvent(71, 4, this);
		}

		public void onOpen() {
			this.onScreen = true;
			if (WHmenues.this.not_in_area != null) {
				this.check_box = WHmenues.this.not_in_area
						.getField("DESTINATION NOT IN AREA - CHECKBOX");
				if (this.check_box != 0L)
					menues.SetFieldState(this.check_box, this.in_value);
			}
		}

		boolean IsOnScreen() {
			return this.onScreen;
		}
	}

	public class Arrow {
		Vector orders;
		int ID;
		gameinfo.Warehouse target;
	}
}