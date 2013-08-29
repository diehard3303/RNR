/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

import gameobj.CarParts;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import menu.BalanceUpdater;
import menu.BaseMenu;
import menu.CInteger;
import menu.Cmenu_TTI;
import menu.ComboBox;
import menu.Common;
import menu.CondTable;
import menu.FabricControlColor;
import menu.Item;
import menu.JavaEvents;
import menu.KeyPair;
import menu.ListenerManager;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.SMenu;
import menu.SelectCb;
import menu.Table.TableVisitor;
import menu.TableNode;
import menu.TruckView;
import menu.UpgradeTable;
import menu.menucreation;
import menu.menues;
import players.Crew;
import players.aiplayer;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrscr.gameinfo;
import xmlutils.Node;
import xmlutils.NodeList;
import xmlutils.XmlUtils;

public class STOmenues extends BaseMenu implements SelectCb, menucreation {
	private static final String METALLICBUTTON = "METALLIC Check BOX";
	private static final String CHAMELIONBUTTON = "CHAMELEON Check BOX";
	private long control_metallic;
	private long control_chamelion;
	private static final String CHAMELION_METALLIC_METHOD = "onColorChange";
	private static final String CLOSE_WINDOW_METHOD = "OnExitMenu";
	static final boolean DEBUG = eng.noNative;
	String MenuRepairTexture;
	static long s_table;
	static int s_activetab;
	static int s_activepopup;
	static final int MAINCOLOR_ID = 0;
	static final int LEATHER_ID = 1;
	static final int DASH_ID = 2;
	static final int CLOTH_ID = 3;
	static final int GAUGES_ID = 4;
	static final int GLASSES_ID = 5;
	static final int MAX_ID = 6;
	static final int METALLIC_ID = 10;
	static String[] s_xmltypenames = { "MainColor", "Leather", "Dash", "Cloth",
			"Gauges", "Glasses" };
	static String[] s_xmltypetitle = { "MAIN", "LEATHER", "DASH", "CLOTH",
			"GAUGES", "GLASSES" };
	static String[] s_mattypenames = { "color", "leather", "dash", "cloth",
			"gauges", "glass" };
	static int[] s_matnum = { 6, 8, 6, 16, 8, 16 };
	static int[] s_mattexturesize = { 256, 256, 512, 512, 512, 512 };
	static int[] s_numpos = { 6, 8, 6, 16, 9, 16 };
	int m_iMainColor;
	int m_iLeather;
	int m_iCloth;
	int m_iDash;
	int m_iDashGauges;
	int m_iGlass;
	long balanc_control;
	boolean isMetallic;
	boolean isHamelion;
	Vector m_RestylePrices;
	CondTable m_Table;
	UpgradeTable m_UpgradeTable;
	CarParts m_CarParts;
	TableNode m_vehicle;
	ComboBox[] m_testcombobox;
	int m_iRestyleAuth;
	int m_iRepairAuth;
	private static boolean isMenuCreated = false;
	private static boolean isMenuAskedToCreate = false;
	private static boolean isShowMenuImmediately = false;
	TruckView m_truckview;
	private PoPUpMenu info_cannotapply;
	private PoPUpMenu info_cannotupgrade;
	private PoPUpMenu info_cannotupgrade_guepard;
	SMenu m_ConfirmWindow;
	ApplyResults m_vApplyResults;
	DiscardResults m_vDiscardResults;
	LogosAnim anims;
	private HashMap<String, ControlHolder> control_names;

	public STOmenues() {
		this.balanc_control = 0L;

		this.m_RestylePrices = new Vector();

		this.m_testcombobox = new ComboBox[6];

		this.info_cannotapply = null;
		this.info_cannotupgrade = null;
		this.info_cannotupgrade_guepard = null;

		this.m_vApplyResults = new ApplyResults();

		this.m_vDiscardResults = new DiscardResults();

		this.anims = null;

		this.control_names = new HashMap();
	}

	public void restartMenu(long _menu) {
	}

	public static void showMenu() {
		if (isMenuCreated) {
			menues.showMenu(6000);
		} else if (isMenuAskedToCreate)
			isShowMenuImmediately = true;
	}

	public static void createMenu() {
		isMenuAskedToCreate = true;
		menues.CreateSTOMenu(1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public void InitMenu(long _menu) {
		this.common = new Common(_menu);
		ListenerManager.TriggerEvent(103);
		this.common.InitBalance();
		s_activepopup = -1;
		s_activetab = 0;
		this.m_CarParts = gameinfo.script.m_CarParts;

		menues.InitXml(_menu, Common.ConstructPath("menu_repair.xml"),
				"MenuREPAIR COMMON 1");

		menues.InitXml(_menu, Common.ConstructPath("menu_repair.xml"),
				"MenuRepair RepairVEHICLE");

		menues.InitXml(_menu, Common.ConstructPath("menu_repair.xml"),
				"MenuRepair RestyleVEHICLE");

		menues.InitXml(_menu, Common.ConstructPath("menu_repair.xml"),
				"MenuRepair UpgradeVEHICLE");

		menues.InitXml(_menu, Common.ConstructPath("menu_repair.xml"),
				"MenuREPAIR COMMON 2");

		menues.InitXml(_menu, Common.ConstructPath("menu_repair.xml"),
				"MenuREPAIR CONFIRM");

		this.m_ConfirmWindow = this.common.FindWindow("YES_NO_CANCEL");
		menues.SetShowField(this.m_ConfirmWindow.nativePointer, false);

		this.common.SetScriptOnButton("BUTTON - YES", this, "OnConfirmYes");
		this.common.SetScriptOnButton("BUTTON - NO", this, "OnConfirmNo");
		this.common.SetScriptOnButton("BUTTON - CANCEL", this,
				"OnConfirmCancel");

		this.control_chamelion = menues.FindFieldInMenu(_menu,
				"CHAMELEON Check BOX");
		this.control_metallic = menues.FindFieldInMenu(_menu,
				"METALLIC Check BOX");
		this.balanc_control = menues.FindFieldInMenu(_menu,
				"YOUR BALANCE - VALUE");
		menues.SetScriptOnControl(_menu,
				menues.ConvertMenuFields(this.control_chamelion), this,
				"onColorChange", 2L);
		menues.SetScriptOnControl(_menu,
				menues.ConvertMenuFields(this.control_metallic), this,
				"onColorChange", 2L);
		menues.SetScriptOnControl(_menu,
				menues.ConvertMenuFields(menues.GetBackMenu(_menu)), this,
				"OnExitMenu", 17L);

		for (int i = 0; i < this.m_testcombobox.length; ++i) {
			int numitems = s_matnum[i];
			String intext = ((i == 0) || (i == 5)) ? "Exterior" : "Interior";
			this.m_testcombobox[i] = new ComboBox(this.common,
					Common.ConstructPath("menu_repair.xml"),
					"MenuRepair RestyleVEHICLE - " + intext + " - "
							+ s_xmltypenames[i], s_xmltypenames[i] + "00",
					"Border - " + s_xmltypenames[i]
							+ " - JustForTest 8 positions",
					this.common.FindRadioButton("Restyle - "
							+ s_xmltypenames[i]),
					this.common.FindSimpleButton(s_xmltypetitle[i]
							+ " - PopUP button"), numitems, 38, 2, 3);

			this.m_testcombobox[i].AddListener(this);
			if (DEBUG)
				this.m_testcombobox[i].SetMaterial("mat_KENWORTH_T2000_"
						+ s_mattypenames[i] + "_110");
			else {
				this.m_testcombobox[i].SetMaterial("mat_"
						+ gameinfo.script.m_sTruckName + "_"
						+ s_mattypenames[i] + "_110");
			}
		}

		gameinfo info = gameinfo.script;

		this.common.SetTextValue("Make - VALUE", info.m_sVehManufactLocalized);
		this.common.SetTextValue("Model - VALUE", info.m_sVehModelLocalized);
		this.common.SetTextValue("Vehicle type - VALUE", info.m_sVehType);

		if ((Crew.getIgrok() != null) && (Crew.getIgrok().gFirstName() != null)
				&& (Crew.getIgrok().gLastName() != null)) {
			KeyPair[] pairs = new KeyPair[1];
			pairs[0] = new KeyPair("NICOLAS_ARMSTRONG", Crew.getIgrok()
					.gFirstName() + " " + Crew.getIgrok().gLastName());
			menues.SetFieldText(
					menues.FindFieldInMenu(_menu, "Driver - VALUE"), MacroKit
							.Parse(menues.GetFieldText(menues.FindFieldInMenu(
									_menu, "Driver - VALUE")), pairs));
			menues.UpdateMenuField(menues.ConvertMenuFields(menues
					.FindFieldInMenu(_menu, "Driver - VALUE")));
		}

		InitTable(_menu);
		InitCommonButtons(_menu);

		this.info_cannotapply = new PoPUpMenu(_menu,
				Common.ConstructPath("menu_repair.xml"),
				"MESSAGE - Not enough money", "MESSAGE - Not enough money");
		this.info_cannotupgrade = new PoPUpMenu(_menu,
				Common.ConstructPath("menu_repair.xml"),
				"MESSAGE - Full repair first", "MESSAGE - Full repair first");
		this.info_cannotupgrade_guepard = new PoPUpMenu(_menu,
				Common.ConstructPath("menu_repair.xml"),
				"MESSAGE - Guepard unupgradable");
		InitLogos(_menu);
	}

	public void AfterInitMenu(long _menu) {
		isMenuCreated = true;
		ListenerManager.TriggerEvent(104);
		InitRestyle(_menu);
		BalanceUpdater.AddBalanceControl(this.balanc_control);

		reciveMetallicChamelionFlags();

		long fild = menues.FindFieldInMenu(_menu, "BUTTON - EXIT");
		menues.setfocuscontrolonmenu(_menu, fild);
		menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(fild),
				this, "OnExit", 4L);

		this.m_Table.AfterInitMenu();
		this.m_UpgradeTable.AfteInitMenu();

		InitTabControls(_menu);

		AfterInitRepair(_menu, s_table);
		MENUTruckview view_ext = this.common.FindTruckView("Exterior");
		view_ext.BindVehicle(gameinfo.script.m_pVehiclePointer, 1, 8);

		MENUTruckview view_int = this.common.FindTruckView("Interior");
		view_int.BindVehicle(gameinfo.script.m_pVehiclePointer, 0, 0);

		RedrawRestyle();

		MENUsimplebutton_field discard = this.common
				.FindSimpleButton("BUTTON - DISCARD ALL");
		menues.SetScriptOnControl(_menu, discard, this, "OnDiscard", 4L);

		MENUsimplebutton_field apply = this.common
				.FindSimpleButton("BUTTON - APPLY ALL");
		menues.SetScriptOnControl(_menu, apply, this, "OnApply", 4L);

		UpdateRepairTotal();
		Init3DVehicle(_menu);

		this.info_cannotapply.afterInit();
		this.info_cannotupgrade.afterInit();
		this.info_cannotupgrade_guepard.afterInit();
		AfterInitLogos(_menu);

		this.m_truckview.SetCameraSwitch(96);
		if (isShowMenuImmediately) {
			isShowMenuImmediately = false;
			menues.showMenu(6000);
		}
	}

	public void exitMenu(long _menu) {
		isMenuAskedToCreate = false;
		isMenuCreated = false;
		BalanceUpdater.RemoveBalanceControl(this.balanc_control);

		this.m_UpgradeTable.DeInit();
		this.m_Table.DeInit();
		ListenerManager.TriggerEvent(105);
	}

	public String getMenuId() {
		return "stoMENU";
	}

	private void RedrawRestyle() {
		SelectColor(0, this.m_iMainColor);
		SelectColor(1, this.m_iLeather);
		SelectColor(3, this.m_iCloth);
		SelectColor(2, this.m_iDash);
		SelectColor(4, this.m_iDashGauges);
		SelectColor(5, this.m_iGlass);
		SelectColor(10, (this.isMetallic) ? 1 : 0);
		if (this.control_metallic != 0L) {
			menues.SetFieldState(this.control_metallic, (this.isMetallic) ? 1
					: 0);
		}
		if (this.control_chamelion != 0L) {
			menues.SetFieldState(this.control_chamelion, (this.isHamelion) ? 1
					: 0);
		}

		this.m_testcombobox[0].Select(s_matnum[0] - 1 - this.m_iMainColor);
		this.m_testcombobox[1].Select(this.m_iLeather);
		this.m_testcombobox[2].Select(this.m_iDash);
		this.m_testcombobox[3].Select(this.m_iCloth);
		this.m_testcombobox[4].Select(this.m_iDashGauges);
		this.m_testcombobox[5].Select(this.m_iGlass);
	}

	public void TestRedraw(long _menu, MENUsimplebutton_field button) {
		menues.RedrawTable(_menu, s_table);
	}

	public void AfterInitRepair(long _menu, long _table) {
		gameinfo.script.m_iRepairTableVisSize = 109;
	}

	public void InitCommonButtons(long _menu) {
		KeyPair[] macro = { new KeyPair("SIGN", " "),
				new KeyPair("MONEY", Converts.ConvertNumeric(0)) };

		MacroKit.ApplyToTextfield(
				this.common.FindTextField("TOTAL AUTHORIZED - VALUE"), macro);
	}

	void Init3DVehicle(long _menu) {
		this.m_truckview.BindRepairVehicle();
		this.m_truckview.InitMaterialSwitches();
		this.m_truckview.InitCameraSwitches();
		this.m_truckview.AttachCarInfo(gameinfo.script.m_CarParts);
	}

	String ToStringLeadingZeros(int num) {
		if (num < 10) {
			return "0" + num;
		}
		return "" + num;
	}

	private void reciveMetallicChamelionFlags() {
		menues.SetFieldState(this.control_metallic, (this.isMetallic) ? 1 : 0);
		menues.SetFieldState(this.control_chamelion, (this.isHamelion) ? 1 : 0);
	}

	private int getTrueColor(int externelcolor) {
		int minus = externelcolor & 0x1;
		return ((externelcolor - minus) / 2);
	}

	private boolean isHamelionColor(int externelcolor) {
		int res = externelcolor & 0x1;
		return (res != 0);
	}

	private int getValueColor(int truecolor, boolean is_hamelion) {
		return ((is_hamelion) ? truecolor * 2 + 1 : truecolor * 2);
	}

	public void InitRestyle(long _menu) {
		for (int i = 0; i < 6; ++i) {
			this.m_RestylePrices.add(new CInteger(0));
		}
		menues.SetFieldState(
				this.common.FindRadioButton("Restyle - MainColor").nativePointer,
				s_matnum[0] - 1);

		updateValuesFromInfo();

		for (int type = 0; type < 6; ++type) {
			int state = 0;
			switch (type) {
			case 0:
				state = s_matnum[0] - 1 - this.m_iMainColor;
				break;
			case 1:
				state = this.m_iLeather;
				break;
			case 3:
				state = this.m_iCloth;
				break;
			case 2:
				state = this.m_iDash;
				break;
			case 4:
				state = this.m_iDashGauges;
				break;
			case 5:
				state = this.m_iGlass;
			}

			menues.SetFieldState(
					this.common.FindRadioButton("Restyle - "
							+ s_xmltypenames[type]).nativePointer, state);

			int num = s_matnum[type];
			int[] temp = new int[num];
			for (int i = 0; i < num; ++i)
				temp[i] = i;
			this.m_testcombobox[type].FillMappingData(1.0F / num, temp);
		}
	}

	public void InitTabControls(long _menu) {
		MENUbutton_field tab0 = this.common
				.FindRadioButton("TAB - REPAIR VEHICLE");
		MENUbutton_field tab2 = this.common
				.FindRadioButton("TAB - UPGRADES & ACCESSORIES");
		MENUbutton_field tab1 = this.common
				.FindRadioButton("TAB - RESTYLE VEHICLE");

		long group = menues.FindFieldInMenu(_menu, "BACK ALL - RepairVEHICLE");
		menues.SetShowField(group, s_activetab == 0);
		menues.SetFieldState(tab0.nativePointer, (s_activetab == 0) ? 1 : 0);

		group = menues.FindFieldInMenu(_menu, "BACK ALL - RestyleVEHICLE");
		menues.SetShowField(group, s_activetab == 1);
		menues.SetFieldState(tab1.nativePointer, (s_activetab == 1) ? 1 : 0);

		group = menues.FindFieldInMenu(_menu, "BACK ALL - UpgradeVEHICLE");
		menues.SetShowField(group, s_activetab == 2);
		menues.SetFieldState(tab2.nativePointer, (s_activetab == 2) ? 1 : 0);

		menues.SetScriptOnControl(_menu, tab0, this, "OnTab", 2L);
		menues.SetScriptOnControl(_menu, tab1, this, "OnTab", 2L);
		menues.SetScriptOnControl(_menu, tab2, this, "OnTab", 2L);

		menues.setfocuscontrolonmenu(_menu, tab0.nativePointer);
	}

	public void InitPopupControls(long _menu) {
		MENUsimplebutton_field popup2 = this.common
				.FindSimpleButton("DASH - PopUP button");
		MENUsimplebutton_field popup3 = this.common
				.FindSimpleButton("CLOTH - PopUP button");
		MENUsimplebutton_field popup4 = this.common
				.FindSimpleButton("GAUGES - PopUP button");

		MENUsimplebutton_field popup5 = this.common
				.FindSimpleButton("MAIN - PopUP button");

		menues.SetScriptOnControl(_menu, popup2, this, "OnPopup", 4L);
		menues.SetScriptOnControl(_menu, popup3, this, "OnPopup", 4L);
		menues.SetScriptOnControl(_menu, popup4, this, "OnPopup", 4L);
		menues.SetScriptOnControl(_menu, popup5, this, "OnPopup", 4L);

		for (int type = 0; type < 5; ++type) {
			if (type == 1)
				continue;
			MENUText_field popupgroup = FindPopupGroupByIndex(_menu, type);
			menues.SetShowField(popupgroup.nativePointer, false);

			String radioname = "Restyle - " + s_xmltypenames[type];
			MENUbutton_field button = this.common.FindRadioButton(radioname);
			menues.SetScriptOnControl(_menu, button, this, "OnRadioSelect", 2L);

			if (type == 0) {
				menues.SetFieldState(button.nativePointer, s_numpos[type] - 1);
			}

		}

		for (int type = 0; type < 5; ++type) {
			if (type == 1)
				continue;
			for (int i = 0; i < s_numpos[type]; ++i) {
				String controlname = s_xmltypenames[type]
						+ ToStringLeadingZeros(i);

				MENUsimplebutton_field button = this.common
						.FindSimpleButton(controlname);
				menues.SetScriptOnControl(_menu, button, this, "OnSelect", 4L);
			}
		}
	}

	public void InitTable(long _menu) {
		this.m_truckview = new TruckView(
				this.common.FindTruckView("VehCondMonitor - 3D Vehicle"));
		this.m_Table = new CondTable(this.common, "Repair Table");
		for (int i = 0; i < 3; ++i) {
			this.m_Table.AddControl(0, i, "VALUES - " + (i + 1)
					+ "level - BUTTONbrowse");

			this.m_Table.AddControl(2, i, "VALUES - " + (i + 1)
					+ " level - Text Glow");

			this.m_Table.AddControl(1, i, "VALUES - " + (i + 1)
					+ " level - Text");

			this.m_Table.AddControl(5, i, "VALUES - " + (i + 1)
					+ "level - BUTTONconditionRED");

			this.m_Table.AddControl(4, i, "VALUES - " + (i + 1)
					+ "level - BUTTONconditionYELLOW");

			this.m_Table.AddControl(3, i, "VALUES - " + (i + 1)
					+ "level - BUTTONconditionGREEN");
		}

		this.m_Table.AddControl(7, 0, "Repair Price - VALUES");
		this.m_Table.AddControl(8, 0, "Authorize - VALUES");
		this.m_Table.AddControl(9, 0, "Check BOX");

		this.m_UpgradeTable = new UpgradeTable(this.common, "Upgrade Table",
				this);

		for (int i = 0; i < 4; ++i) {
			this.m_UpgradeTable.AddControl(0, i, "VALUES - " + (i + 1)
					+ " level - BUTTONbrowse");

			this.m_UpgradeTable.AddControl(2, i, "VALUES - " + (i + 1)
					+ " level - Text Glow");

			this.m_UpgradeTable.AddControl(1, i, "VALUES - " + (i + 1)
					+ " level - Text");
		}

		this.m_UpgradeTable.AddControl(10, 0, "UpDown - Check BOX");
		this.m_UpgradeTable.AddControl(11, 0, "UpDown - Check BOX GRAY");
		this.m_UpgradeTable.AddControl(4, 0, "VALUES - Upgrade Price");
		this.m_UpgradeTable.AddControl(5, 0, "VALUES - Button UPGRADE");
		this.m_UpgradeTable.AddControl(6, 0, "VALUES - Button UPGRADE GRAY");
		this.m_UpgradeTable.AddControl(7, 0, "VALUES - Button DOWNGRADE");
		this.m_UpgradeTable.AddControl(8, 0, "VALUES - Button DOWNGRADE GRAY");
		this.m_UpgradeTable.AddControl(9, 0, "VALUES - Downgrade Return");
		this.m_UpgradeTable.AddControl(13, 0,
				"VALUES - 1 level - INDICATOR GREEN");
		this.m_UpgradeTable.AddControl(12, 0,
				"VALUES - 1 level - INDICATOR RED");
		this.m_UpgradeTable.AddControl(14, 0, "VALUES - 1 level - INDICATOR");
		this.m_UpgradeTable.AddControl(15, 0,
				"VALUES - 1 level - INDICATOR Border");

		this.m_CarParts.FixupHierarchy();
		this.m_vehicle = this.m_CarParts.FillCondTable(this.m_Table);
		this.m_Table.Setup(38, 16, Common.ConstructPath("menu_repair.xml"),
				"MenuRepair RepairVEHICLE - TABLE",
				"TABLEGROUP 01 - RepairVEHICLE - TABLE",
				new PartsCameraTrigger(this, this.m_truckview));

		this.m_Table.AttachRanger(this.common
				.FindScroller("ShipTo - tableranger"));

		this.m_UpgradeTable.Setup(38, 16,
				Common.ConstructPath("menu_repair.xml"),
				"MenuRepair UpgradeVEHICLE - TABLE", "UA - TABLEGROUP - 16 38");

		this.m_UpgradeTable.AttachRanger(this.common
				.FindScroller("UA - TABLEGROUP - tableranger"));
	}

	MENUText_field FindPopupGroupByIndex(long _menu, int index) {
		switch (index) {
		case 0:
			return this.common
					.FindTextField("Border - MainColor - JustForTest 8 positions");
		case 1:
			return this.common
					.FindTextField("Border - Leather - JustForTest 8 positions");
		case 2:
			return this.common
					.FindTextField("Border - Dash - JustForTest 6 positions");
		case 3:
			return this.common
					.FindTextField("Border - Cloth - JustForTest 8 positions");
		case 4:
			return this.common
					.FindTextField("Border - Gauges - JustForTest 8 positions");
		}
		return null;
	}

	public void ClearPopup(long _menu) {
		if (s_activepopup != -1) {
			MENUText_field popupgroup = FindPopupGroupByIndex(_menu,
					s_activepopup);

			menues.SetShowField(popupgroup.nativePointer, false);
			s_activepopup = -1;
		}
	}

	public void OnDiscard(long _menu, MENUsimplebutton_field button) {
		this.m_Table.Traverse(this.m_vDiscardResults);
		this.m_iRepairAuth = 0;
		this.m_iRestyleAuth = 0;
		this.m_UpgradeTable.OnDiscard();

		updateValuesFromInfo();

		for (int i = 0; i < this.m_RestylePrices.size(); ++i) {
			CInteger value = (CInteger) this.m_RestylePrices.get(i);
			value.value = 0;
		}

		RedrawRestyle();
		UpdateRepairTotal();
		menues.RedrawTable(_menu, s_table);
	}

	public void OnExit(long _menu, MENUsimplebutton_field button) {
		if ((this.m_iRepairAuth != 0) || (this.m_iRestyleAuth != 0)
				|| (this.m_UpgradeTable.total_upgrade_price != 0)) {
			menues.SetShowField(this.m_ConfirmWindow.nativePointer, true);
			return;
		}
		if (this.anims != null) {
			this.anims.Exit();
		}
		exitMenu(_menu);
		menues.CallMenuCallBack_ExitMenu(_menu);
	}

	public void OnExitMenu(long _menu, SMenu button) {
		OnExit(_menu, null);
	}

	void OnConfirmYes(long _menu, MENUsimplebutton_field button) {
		if (CanApply(gameinfo.script)) {
			OnApply(this.common.GetMenu(), null);
			if (this.anims != null) {
				this.anims.Exit();
			}
			exitMenu(_menu);
			menues.CallMenuCallBack_ExitMenu(_menu);
		} else {
			menues.SetShowField(this.m_ConfirmWindow.nativePointer, false);
			this.info_cannotapply.show();
		}
	}

	void OnConfirmNo(long _menu, MENUsimplebutton_field button) {
		OnDiscard(this.common.GetMenu(), null);
		if (this.anims != null) {
			this.anims.Exit();
		}
		exitMenu(_menu);
		menues.CallMenuCallBack_ExitMenu(_menu);
	}

	void OnConfirmCancel(long _menu, MENUsimplebutton_field button) {
		menues.SetShowField(this.m_ConfirmWindow.nativePointer, false);
	}

	boolean CanApply(gameinfo info) {
		return (this.m_iRepairAuth + this.m_iRestyleAuth
				+ this.m_UpgradeTable.total_upgrade_price <= BalanceUpdater
					.GetBalance());
	}

	public void OnApply(long _menu, MENUsimplebutton_field button) {
		long fild = menues.FindFieldInMenu(_menu, "BUTTON - EXIT");
		menues.setfocuscontrolonmenu(_menu, fild);
		gameinfo info = gameinfo.script;
		if (!(CanApply(info))) {
			this.info_cannotapply.show();
			return;
		}

		info.m_iTotalAuth += this.m_iRepairAuth + this.m_iRestyleAuth
				+ this.m_UpgradeTable.total_upgrade_price;
		this.m_iRepairAuth = (this.m_iRestyleAuth = 0);

		this.m_Table.Traverse(this.m_vApplyResults);
		this.m_CarParts.FixupHierarchy();
		this.m_UpgradeTable.OnApply();

		this.m_Table.RedrawTable(false);

		for (int i = 0; i < this.m_RestylePrices.size(); ++i) {
			CInteger value = (CInteger) this.m_RestylePrices.get(i);
			value.value = 0;
		}

		provideValuesToInfo();

		UpdateRepairTotal();
		UpdateRestyleTotal();

		JavaEvents.SendEvent(10, 0, this);

		this.m_truckview.AttachCarInfo(info.m_CarParts);
	}

	public void OnRadioSelect(long _menu, MENUbutton_field field) {
		int type = GetTypeByName(field.nameID, s_xmltypenames);
		int index;
		int index;
		if (type == 0)
			index = s_numpos[type] - menues.GetFieldState(field.nativePointer)
					- 1;
		else {
			index = menues.GetFieldState(field.nativePointer);
		}
		SelectColor(type, index);
	}

	public void onColorChange(long _menu, MENUbutton_field field) {
		boolean oldmetal = this.isMetallic;
		boolean oldcham = this.isHamelion;
		this.isMetallic = (menues.GetFieldState(this.control_metallic) != 0);
		this.isHamelion = (menues.GetFieldState(this.control_chamelion) != 0);
		if (oldcham != this.isHamelion)
			SelectColor(0, this.m_iMainColor);
		if (oldmetal != this.isMetallic)
			SelectColor(10, (this.isMetallic) ? 1 : 0);
	}

	public void OnPopup(long _menu, MENUsimplebutton_field field) {
		int index = -1;
		int count = 0;
		for (String str : s_xmltypetitle) {
			if (field.nameID.indexOf(str) != -1) {
				index = count;
				break;
			}
			++count;
		}

		MENUText_field popupgroup = FindPopupGroupByIndex(_menu, index);
		if (index == s_activepopup) {
			ClearPopup(_menu);
		} else {
			ClearPopup(_menu);
			menues.SetShowField(popupgroup.nativePointer, true);
			s_activepopup = index;
		}
	}

	public void SelectColor(int type, int color) {
		gameinfo info = gameinfo.script;
		CInteger value = (CInteger) this.m_RestylePrices
				.get((type != 10) ? type : 0);

		if ((type == 0) || (type == 10)) {
			this.m_iMainColor = ((type != 10) ? color : this.m_iMainColor);
			if ((getValueColor(this.m_iMainColor, this.isHamelion) == info.m_iVehicleMainColor)
					&& (this.isMetallic == info.m_bVehicleMettalic)) {
				value.value = 0;
			} else if (this.isHamelion) {
				if (this.isMetallic)
					value.value = ((CInteger) (CInteger) info.m_RestyleBodyColorMetallicChameleonPricelist
							.get(color)).value;
				else {
					value.value = ((CInteger) (CInteger) info.m_RestyleBodyColorChameleonPricelist
							.get(color)).value;
				}
			} else if (this.isMetallic)
				value.value = ((CInteger) (CInteger) info.m_RestyleBodyColorMetallicPricelist
						.get(color)).value;
			else {
				value.value = ((CInteger) (CInteger) info.m_RestyleBodyColorPricelist
						.get(color)).value;
			}

		} else if (type == 1) {
			this.m_iLeather = color;
			if (this.m_iLeather == info.m_iVehicleLeather)
				value.value = 0;
			else
				value.value = ((CInteger) (CInteger) info.m_RestyleLeatherPricelist
						.get(color)).value;
		} else if (type == 3) {
			this.m_iCloth = color;
			if (this.m_iCloth == info.m_iVehicleCloth)
				value.value = 0;
			else
				value.value = ((CInteger) (CInteger) info.m_RestyleClothPricelist
						.get(color)).value;
		} else if (type == 2) {
			this.m_iDash = color;
			if (this.m_iDash == info.m_iVehicleDash)
				value.value = 0;
			else
				value.value = ((CInteger) (CInteger) info.m_RestyleDashPricelist
						.get(color)).value;
		} else if (type == 4) {
			this.m_iDashGauges = color;
			if (this.m_iDashGauges == info.m_iVehicleDashGauges)
				value.value = 0;
			else
				value.value = ((CInteger) (CInteger) info.m_RestyleDashGaugesPricelist
						.get(color)).value;
		} else if (type == 5) {
			this.m_iGlass = color;
			if (this.m_iGlass == info.m_iVehicleGlasses)
				value.value = 0;
			else {
				value.value = ((CInteger) (CInteger) info.m_RestyleGlassesPricelist
						.get(color)).value;
			}

		}

		UpdateRestyleTotal();
		MENUTruckview truckview = null;
		if ((type == 0) || (type == 5) || (type == 10))
			truckview = this.common.FindTruckView("Exterior");
		else {
			truckview = this.common.FindTruckView("Interior");
		}
		if (type == 0)
			truckview.SetState(0, type,
					getValueColor(this.m_iMainColor, this.isHamelion));
		else if (type == 10)
			truckview.SetState(0, type, (this.isMetallic) ? 1 : 0);
		else
			truckview.SetState(0, type, color);
	}

	int GetTypeByName(String name, String[] names) {
		for (int i = 0; i < names.length; ++i) {
			if (name.indexOf(names[i]) != -1)
				return i;
		}
		return -1;
	}

	public void OnSelect(long _menu, MENUsimplebutton_field field) {
		int index = field.nameID.charAt(field.nameID.length() - 1) - '0' + 10
				* (field.nameID.charAt(field.nameID.length() - 2) - '0');

		int type = GetTypeByName(field.nameID, s_xmltypenames);

		if (type == -1) {
			return;
		}

		SelectColor(type, index);

		ClearPopup(_menu);
	}

	boolean IsCarGuepard() {
		gameinfo info = gameinfo.script;

		return ((info != null) && (info.m_sVehModelInternal != null) && (info.m_sVehModelInternal
				.compareToIgnoreCase("GEPARD") == 0));
	}

	boolean IsCarBroken() {
		Item item = this.m_CarParts.GetItem(96);
		return (item.condition != 0);
	}

	public void OnTab(long _menu, MENUbutton_field field) {
		int index;
		int index;
		if (field.nameID.indexOf("RESTYLE") != -1) {
			index = 1;
		} else {
			int index;
			if (field.nameID.indexOf("REPAIR") != -1)
				index = 0;
			else
				index = 2;
		}
		if (index == s_activetab) {
			menues.SetFieldState(field.nativePointer, 1);
			return;
		}

		if ((index != 0) && (((IsCarBroken()) || (IsCarGuepard())))) {
			if (IsCarGuepard())
				this.info_cannotupgrade_guepard.show();
			else if (IsCarBroken()) {
				this.info_cannotupgrade.show();
			}

			long tab0 = menues.FindFieldInMenu(_menu, "TAB - REPAIR VEHICLE");
			menues.SetFieldState(tab0, 1);
			long tab1 = menues.FindFieldInMenu(_menu, "TAB - RESTYLE VEHICLE");
			menues.SetFieldState(tab1, 0);
			long tab2 = menues.FindFieldInMenu(_menu,
					"TAB - UPGRADES & ACCESSORIES");
			menues.SetFieldState(tab2, 0);
			return;
		}

		long group0 = menues.FindFieldInMenu(_menu, "BACK ALL - RepairVEHICLE");
		menues.SetShowField(group0, index == 0);
		long group1 = menues
				.FindFieldInMenu(_menu, "BACK ALL - RestyleVEHICLE");
		menues.SetShowField(group1, index == 1);
		long group2 = menues
				.FindFieldInMenu(_menu, "BACK ALL - UpgradeVEHICLE");
		menues.SetShowField(group2, index == 2);

		long tab0 = menues.FindFieldInMenu(_menu, "TAB - REPAIR VEHICLE");
		menues.SetFieldState(tab0, (index == 0) ? 1 : 0);

		long tab1 = menues.FindFieldInMenu(_menu, "TAB - RESTYLE VEHICLE");
		menues.SetFieldState(tab1, (index == 1) ? 1 : 0);

		long tab2 = menues.FindFieldInMenu(_menu,
				"TAB - UPGRADES & ACCESSORIES");
		menues.SetFieldState(tab2, (index == 2) ? 1 : 0);

		s_activetab = index;
	}

	void SelectItem3D(TableItem item) {
		if (item.depth == 0)
			return;
		this.m_truckview.SetCameraSwitch(item.item.id);
	}

	public void SelectGroup(long _menu, boolean isselect, Cmenu_TTI node) {
		DownPass(isselect, node);
		TableItem local_item = (TableItem) node.item;
		SelectItem3D(local_item);
		UpPass(isselect, local_item.parent);
	}

	void UpdateRepairTotal() {
		this.m_Table.FixUp();
		Item item = this.m_CarParts.GetItem(96);
		this.m_iRepairAuth = item.auth;

		KeyPair[] macro = { new KeyPair("VALUE",
				Converts.ConvertNumeric(this.m_iRepairAuth)) };
		MacroKit.ApplyToTextfield(this.common.FindTextFieldByParent("1 VALUE",
				"BACK ALL - RepairVEHICLE"), macro);

		UpdateAuth();
	}

	void UpdateRestyleTotal() {
		int bodyrepaint = ((CInteger) this.m_RestylePrices.get(0)).value;
		bodyrepaint += ((CInteger) this.m_RestylePrices.get(5)).value;

		KeyPair[] macro = { new KeyPair("VALUE",
				Converts.ConvertNumeric(bodyrepaint)) };
		MacroKit.ApplyToTextfield(
				this.common.FindTextField("BodyRepaint - VALUE"), macro);

		int iteriorprice = 0;
		for (int i = 1; i < 6; ++i) {
			if (i != 5) {
				iteriorprice += ((CInteger) this.m_RestylePrices.get(i)).value;
			}

		}

		KeyPair[] macro = { new KeyPair("VALUE",
				Converts.ConvertNumeric(iteriorprice)) };
		MacroKit.ApplyToTextfield(
				this.common.FindTextField("InteriorRestyle - VALUE"), macro);

		KeyPair[] macro = { new KeyPair("VALUE",
				Converts.ConvertNumeric(iteriorprice + bodyrepaint)) };
		MacroKit.ApplyToTextfield(
				this.common.FindTextField("RestyleTOTAL - VALUE"), macro);

		this.m_iRestyleAuth = (iteriorprice + bodyrepaint);
		UpdateAuth();
	}

	public void UpdateAuth() {
		KeyPair[] macro = {
				new KeyPair("SIGN", (this.m_iRepairAuth + this.m_iRestyleAuth
						+ this.m_UpgradeTable.total_upgrade_price >= 0) ? " "
						: "-"),
				new KeyPair("MONEY", Converts.ConvertNumeric(Math
						.abs(this.m_iRepairAuth + this.m_iRestyleAuth
								+ this.m_UpgradeTable.total_upgrade_price))) };

		MacroKit.ApplyToTextfield(
				this.common.FindTextField("TOTAL AUTHORIZED - VALUE"), macro);
	}

	void DownPass(boolean isselect, Cmenu_TTI node) {
		TableItem local_item = (TableItem) node.item;

		if (local_item.item.condition == 0) {
			return;
		}
		if (node.children.size() == 0) {
			if (local_item.checked != isselect) {
				local_item.checked = isselect;
			}
			local_item.item.auth = ((local_item.checked) ? local_item.item.price
					: 0);

			return;
		}

		int sum = 0;

		for (int i = 0; i < node.children.size(); ++i) {
			Cmenu_TTI child_node = (Cmenu_TTI) node.children.get(i);
			TableItem child_item = (TableItem) child_node.item;
			DownPass(isselect, child_node);
			sum += child_item.item.auth;
		}

		local_item.item.auth = sum;
		local_item.checked = isselect;
	}

	void UpPass(boolean isselect, Cmenu_TTI node) {
		if (node == null) {
			return;
		}
		TableItem local_item = (TableItem) node.item;

		if (local_item.item.condition == 0) {
			return;
		}
		int sum = 0;
		int count = 0;

		for (int i = 0; i < node.children.size(); ++i) {
			Cmenu_TTI child_node = (Cmenu_TTI) node.children.get(i);
			TableItem child_item = (TableItem) child_node.item;
			sum += child_item.item.auth;
			if (child_item.checked) {
				++count;
			}
		}
		local_item.item.auth = sum;
		local_item.checked = (count > 0);

		UpPass(isselect, local_item.parent);
	}

	void SetGlobalState(long _menu, Cmenu_TTI node, boolean state, int condition) {
		if (node.children.size() == 0) {
			TableItem item = (TableItem) node.item;
			if ((item.item.condition == condition) && (item.checked != state)) {
				SelectGroup(_menu, state, node);
			}
			return;
		}

		for (int i = 0; i < node.children.size(); ++i) {
			Cmenu_TTI child = (Cmenu_TTI) node.children.get(i);
			SetGlobalState(_menu, child, state, condition);
		}
	}

	public void OnSelect(int state, Object sender) {
		if ((state >= 100) && (state <= 101)) {
			UpdateRepairTotal();
			return;
		}
		for (int i = 0; i < this.m_testcombobox.length; ++i)
			if (sender == this.m_testcombobox[i])
				SelectColor(i, (i == 0) ? s_matnum[i] - 1 - state : state);
	}

	private void updateValuesFromInfo() {
		gameinfo info = gameinfo.script;
		this.m_iMainColor = getTrueColor(info.m_iVehicleMainColor);
		this.m_iLeather = info.m_iVehicleLeather;
		this.m_iCloth = info.m_iVehicleCloth;
		this.m_iDash = info.m_iVehicleDash;
		this.m_iDashGauges = info.m_iVehicleDashGauges;
		this.m_iGlass = info.m_iVehicleGlasses;
		this.isMetallic = info.m_bVehicleMettalic;
		this.isHamelion = isHamelionColor(info.m_iVehicleMainColor);
	}

	private void provideValuesToInfo() {
		gameinfo info = gameinfo.script;
		info.m_iVehicleMainColor = getValueColor(this.m_iMainColor,
				this.isHamelion);
		info.m_iVehicleLeather = this.m_iLeather;
		info.m_iVehicleCloth = this.m_iCloth;
		info.m_iVehicleDash = this.m_iDash;
		info.m_iVehicleDashGauges = this.m_iDashGauges;
		info.m_iVehicleGlasses = this.m_iGlass;
		info.m_bVehicleMettalic = this.isMetallic;
	}

	void InitLogos(long _menu) {
		Node top = XmlUtils.parse("..\\data\\config\\manufacturerslogos.xml");
		NodeList root = top.getNamedChildren("manufacturer");
		for (int i = 0; i < root.size(); ++i) {
			Node node = (Node) root.get(i);
			String control_name = node.getAttribute("control");
			String manufacturer_name = node.getAttribute("name");
			this.control_names.put(
					manufacturer_name,
					new ControlHolder(menues.FindFieldInMenu(_menu,
							control_name)));
		}
	}

	public void SetShowLogo(String manufacturer) {
		ControlHolder holder = (ControlHolder) this.control_names
				.get(manufacturer);
		if (holder != null)
			holder.bShow = true;
	}

	void AfterInitLogos(long _menu) {
		if (!(eng.noNative)) {
			JavaEvents.SendEvent(67, 7, this);
			this.anims = new LogosAnim(this);
		}
	}

	class LogosAnim extends TypicalAnm {
		STOmenues father = null;
		boolean menu_exited = false;
		Vector<Long> logos = null;
		public static final double logo_time0 = 1.0D;
		public static final double logo_time1 = 3.0D;
		public static final double logo_time2 = 1.0D;
		public static final double logo_rise_time = 1.0D;
		public static final double logo_stable_time = 4.0D;
		public static final double one_logo_period = 5.0D;

		LogosAnim(STOmenues _father) {
			this.father = _father;
			this.logos = new Vector();
			Collection collect = this.father.control_names.values();
			if (collect != null) {
				Object[] controls = collect.toArray();
				for (int i = 0; i < collect.size(); ++i) {
					if ((controls[i] != null)
							&& (((STOmenues.ControlHolder) (STOmenues.ControlHolder) controls[i]).bShow)
							&& (((STOmenues.ControlHolder) (STOmenues.ControlHolder) controls[i]).id_logo != 0L))
						this.logos
								.add(Long
										.valueOf(((STOmenues.ControlHolder) (STOmenues.ControlHolder) controls[i]).id_logo));
					else {
						menues.SetShowField(
								((STOmenues.ControlHolder) (STOmenues.ControlHolder) controls[i]).id_logo,
								false);
					}
				}
			}
			eng.CreateInfinitScriptAnimation(this);
		}

		public void Exit() {
			this.menu_exited = true;
		}

		public boolean animaterun(double dt) {
			if (this.menu_exited) {
				return true;
			}

			if (this.logos.size() > 1) {
				double anim_time = 5.0D * (dt / 5.0D - Math.floor(dt / 5.0D));
				int current_logo = (int) Math.floor(dt / 5.0D)
						% this.logos.size();

				int logo_alpha = 255;
				if (anim_time < 1.0D)
					logo_alpha = (int) (255.0D * anim_time / 1.0D);
				else if (anim_time < 4.0D)
					logo_alpha = 255;
				else {
					logo_alpha = (int) (255.0D * (5.0D - anim_time) / 1.0D);
				}

				for (int i = 0; i < this.logos.size(); ++i)
					if (current_logo != i) {
						FabricControlColor.setControlAlfa(
								((Long) this.logos.elementAt(i)).longValue(),
								logo_alpha);
						menues.SetShowField(
								((Long) this.logos.elementAt(i)).longValue(),
								false);
					} else {
						FabricControlColor.setControlAlfa(
								((Long) this.logos.elementAt(i)).longValue(),
								logo_alpha);
						menues.SetShowField(
								((Long) this.logos.elementAt(i)).longValue(),
								true);
					}
			} else if (this.logos.size() == 1) {
				menues.SetShowField(
						((Long) this.logos.elementAt(0)).longValue(), true);
			}

			return false;
		}
	}

	class ControlHolder {
		long id_logo = 0L;
		boolean bShow = false;

		ControlHolder(long paramLong) {
			this.id_logo = paramLong;
		}
	}

	class DiscardResults implements Table.TableVisitor {
		public void VisitNode(TableNode node) {
			if (node.checked)
				node.checked = false;
		}
	}

	class ApplyResults implements Table.TableVisitor {
		public void VisitNode(TableNode node) {
			if ((node.self.children.size() == 0) && (node.checked))
				((Item) node.item).condition = 0;
		}
	}

	public class TableHelper {
		long menu;
		long table;
		String xmlfilename;
		String controlgroup;
		String parentname;
		int x;
		int y;
		int stepy;
		long curgroup;
		int curline;

		TableHelper(long paramLong1, long paramLong2, String paramString1,
				String paramString2, String paramString3) {
			this.menu = paramLong1;
			this.table = paramLong2;
			this.xmlfilename = paramString1;
			this.controlgroup = paramString2;
			this.curline = -1;
			this.parentname = paramString3;

			this.x = -1;
			this.y = -1;
			this.stepy = -1;
		}

		long AddGroup() {
			this.curgroup = menues.CreateGroup(this.menu);
			this.curline += 1;
			return this.curgroup;
		}

		MENUbutton_field AddRadioButton(String name, String prefix) {
			long control = menues.InitXmlControl(this.menu, this.xmlfilename,
					this.controlgroup, name);
			if (control == 0L) {
				return null;
			}

			MENUbutton_field wrapped = menues.ConvertButton(control);
			if (this.parentname.length() != 0)
				wrapped.parentName = this.parentname;
			if (this.stepy == -1) {
				this.x = wrapped.pox;
				this.y = wrapped.poy;
				this.stepy = (wrapped.leny + 2);
			}

			wrapped.poy = (this.y + this.curline * this.stepy);
			wrapped.nameID = prefix + wrapped.nameID;
			menues.UpdateField(wrapped);

			menues.StoreControlState(this.menu, wrapped);
			menues.AddControlInGroup(this.menu, this.curgroup, wrapped);
			menues.ChangableFieldOnGroup(this.menu, wrapped);
			menues.LinkGroupAndControl(this.menu, wrapped);
			return wrapped;
		}

		MENUText_field AddTextfield(String name, String prefix, boolean islinked) {
			long control = menues.InitXmlControl(this.menu, this.xmlfilename,
					this.controlgroup, name);
			if (control == 0L) {
				return null;
			}

			MENUText_field wrapped = menues.ConvertTextFields(control);
			if (this.parentname.length() != 0)
				wrapped.parentName = this.parentname;
			if (this.stepy == -1) {
				this.x = wrapped.pox;
				this.y = wrapped.poy;
				this.stepy = (wrapped.leny + 2);
			}

			wrapped.poy = (this.y + this.curline * this.stepy);
			wrapped.nameID = prefix + wrapped.nameID;
			menues.UpdateField(wrapped);

			menues.StoreControlState(this.menu, wrapped);
			menues.AddControlInGroup(this.menu, this.curgroup, wrapped);
			menues.ChangableFieldOnGroup(this.menu, wrapped);
			if (islinked)
				menues.LinkGroupAndControl(this.menu, wrapped);
			return wrapped;
		}

		MENUsimplebutton_field AddSimpleButton(String name, String prefix,
				boolean islinked) {
			long control = menues.InitXmlControl(this.menu, this.xmlfilename,
					this.controlgroup, name);
			if (control == 0L) {
				return null;
			}

			MENUsimplebutton_field wrapped = menues
					.ConvertSimpleButton(control);

			if (this.parentname.length() != 0)
				wrapped.parentName = this.parentname;
			if (this.stepy == -1) {
				this.x = wrapped.pox;
				this.y = wrapped.poy;
				this.stepy = (wrapped.leny + 2);
			}

			wrapped.poy = (this.y + this.curline * this.stepy);
			wrapped.nameID = prefix + wrapped.nameID;
			menues.UpdateField(wrapped);

			menues.StoreControlState(this.menu, wrapped);
			menues.AddControlInGroup(this.menu, this.curgroup, wrapped);
			menues.ChangableFieldOnGroup(this.menu, wrapped);
			if (islinked)
				menues.LinkGroupAndControl(this.menu, wrapped);
			return wrapped;
		}
	}

	private class TableItem {
		Cmenu_TTI parent;
		boolean checked;
		int depth;
		Item item;

		TableItem(int paramInt, Item paramItem, Cmenu_TTI paramCmenu_TTI) {
			this.item = paramItem;
			this.depth = paramInt;
			this.checked = false;
			this.parent = paramCmenu_TTI;
		}
	}
}