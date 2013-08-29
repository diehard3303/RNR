/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

import java.util.ArrayList;
import java.util.Iterator;
import menu.BaseMenu;
import menu.Common;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.SelectCb;
import menu.menues;
import menu.xmlcontrols.MENUCustomStuff;
import menuscript.mainmenu.PanelDialog;

public abstract class ParentMenu extends BaseMenu implements SelectCb, ISubMenu {
	private static final String BUTTACTION = "OnButton";
	private static final String PREPAREBUTTSWITCH = "OnButtonSwitch";
	private static final String TABACTION = "OnTab";
	private static final String PREPARETABSWITCH = "OnTabSwitch";
	protected ArrayList<Long> tabs;
	protected ArrayList<PanelDialog> settings;
	protected ArrayList<Long> buttons;
	protected ArrayList<ISubMenu> m_menus_buttons;
	private int currentTab;
	private int currentButton;
	public boolean buttonPending;
	private boolean tabPending;
	private int pendingTab;
	private int pendingButton;
	private boolean f_menu_exited;
	private static int LASTSETTINGS = 0;
	private boolean f_saveBetweenTabs;
	private boolean f_afterInitPassed;
	int LASTSETTINGS_CONFIRMING;

	public ParentMenu() {
		this.tabs = new ArrayList();
		this.settings = new ArrayList();

		this.buttons = new ArrayList();
		this.m_menus_buttons = new ArrayList();

		this.currentTab = 0;
		this.currentButton = -1;
		this.buttonPending = false;
		this.tabPending = false;
		this.pendingTab = 0;
		this.pendingButton = 0;
		this.f_menu_exited = false;

		this.f_saveBetweenTabs = false;

		this.f_afterInitPassed = false;

		this.LASTSETTINGS_CONFIRMING = 0;
	}

	public void setSaveBetweenTabs() {
		this.f_saveBetweenTabs = true;
	}

	abstract void NeedToConfirm(String paramString);

	public void freezeTab(int ID) {
		if (ID >= this.tabs.size())
			return;
		menues.SetBlindess(((Long) this.tabs.get(ID)).longValue(), true);
		menues.SetIgnoreEvents(((Long) this.tabs.get(ID)).longValue(), true);
	}

	public void unFreezeTab(int ID) {
		if (ID >= this.tabs.size())
			return;
		menues.SetBlindess(((Long) this.tabs.get(ID)).longValue(), false);
		menues.SetIgnoreEvents(((Long) this.tabs.get(ID)).longValue(), false);
	}

	public void freezeButton(int ID) {
		if (ID >= this.buttons.size())
			return;
		menues.SetBlindess(((Long) this.buttons.get(ID)).longValue(), true);
		menues.SetIgnoreEvents(((Long) this.buttons.get(ID)).longValue(), true);
	}

	public void unFreezeButton(int ID) {
		if (ID >= this.buttons.size())
			return;
		menues.SetBlindess(((Long) this.buttons.get(ID)).longValue(), false);
		menues.SetIgnoreEvents(((Long) this.buttons.get(ID)).longValue(), false);
	}

	public int addButton(MENUbutton_field button, ISubMenu menu) {
		int size = this.buttons.size();
		this.buttons.add(Long.valueOf(button.nativePointer));
		this.m_menus_buttons.add(menu);
		if (menu != null)
			menu.SetParent(this);
		menues.SetScriptOnControl(this.common.GetMenu(), button, this,
				"OnButton", 10L);
		menues.SetScriptOnControl(this.common.GetMenu(), button, this,
				"OnButtonSwitch", 18L);
		button.userid = size;
		menues.UpdateMenuField(button);
		return size;
	}

	public int AddTab(long _menu, xmlcontrols.MENUCustomStuff tab) {
		int ID = this.tabs.size();
		this.tabs.add(Long.valueOf(tab.nativePointer));
		tab.userid = ID;
		menues.UpdateMenuField(tab);
		menues.SetScriptOnControl(_menu, tab, this, "OnTab", 10L);
		menues.SetScriptOnControl(_menu, tab, this, "OnTabSwitch", 18L);
		return ID;
	}

	protected void deselectCurrentButton() {
		for (int i = 0; i < this.buttons.size(); ++i) {
			long control = ((Long) this.buttons.get(i)).longValue();
			menues.setControlCanOperate(control, true);
			menues.SetFieldState(control, 0);
		}
		menues.setfocuscontrolonmenu(this.common.GetMenu(),
				((Long) this.buttons.get(this.currentButton)).longValue());
	}

	protected void selectButton(int nom) {
		for (int i = 0; i < this.buttons.size(); ++i) {
			long control = ((Long) this.buttons.get(i)).longValue();
			menues.setControlCanOperate(control, true);
			menues.SetFieldState(control, (i == nom) ? 1 : 0);
		}
	}

	protected void onSelectButton(int nom) {
		this.currentButton = nom;
		for (int i = 0; i < this.buttons.size(); ++i) {
			long control = ((Long) this.buttons.get(i)).longValue();
			menues.setControlCanOperate(control, true);
			if (i != nom)
				menues.SetFieldState(control, 0);
		}
	}

	public void initSubMenues() {
		for (int i = 0; i < this.m_menus_buttons.size(); ++i) {
			ISubMenu menu = (ISubMenu) this.m_menus_buttons.get(i);
			if (menu != null)
				menu.InitMenu(this.common.GetMenu());
		}
	}

	public void afterInitSubMenues() {
		this.f_afterInitPassed = true;
		for (int i = 0; i < this.m_menus_buttons.size(); ++i) {
			ISubMenu menu = (ISubMenu) this.m_menus_buttons.get(i);
			if (menu != null)
				menu.afterInit();
		}
	}

	public void RefreshTabs() {
		for (int i = 0; i < this.m_menus_buttons.size(); ++i) {
			ISubMenu menu = (ISubMenu) this.m_menus_buttons.get(i);
			if (menu != null)
				menu.Refresh();
		}
	}

	public void OnButtonSwitch(long _menu, MENUbutton_field butt) {
		if (!(this.f_afterInitPassed))
			return;
		if (butt.userid == this.currentButton) {
			menues.setControlCanOperate(butt.nativePointer, false);
			return;
		}

		if (this.currentButton != -1) {
			ISubMenu submenu = (ISubMenu) this.m_menus_buttons
					.get(this.currentButton);
			if (submenu != null) {
				int result = submenu.LeaveSubMenu();
				this.buttonPending = (result != 0);
				if (this.buttonPending)
					this.pendingButton = butt.userid;
				switch (result) {
				case 1:
					NeedToConfirm(null);
					break;
				case 2:
					NeedToConfirm(submenu.GetCustomText());
				}
				if (result == 0)
					onSelectButton(butt.userid);
				else
					menues.setControlCanOperate(butt.nativePointer, false);
			} else {
				onSelectButton(butt.userid);
			}
		} else {
			onSelectButton(butt.userid);
		}
	}

	public void OnButton(long _menu, MENUbutton_field butt) {
		if (!(this.f_afterInitPassed))
			return;
		if (this.currentButton == -1)
			this.currentButton = butt.userid;
		if (!(this.buttonPending)) {
			ISubMenu menu = (ISubMenu) this.m_menus_buttons
					.get(this.currentButton);
			if (menu != null)
				menu.Activate();
		} else {
			selectButton(this.currentButton);
		}
	}

	public void OnTabSwitch(long _menu, xmlcontrols.MENUCustomStuff tab) {
		if (!(this.f_afterInitPassed))
			return;
		if (LASTSETTINGS == tab.userid)
			return;
		if (this.currentTab != -1)
			if (this.f_saveBetweenTabs) {
				int result = LeaveSubMenuSettingsTab();
				this.tabPending = (result != 0);
				if (this.tabPending) {
					this.pendingTab = tab.userid;
				}
				switch (result) {
				case 1:
					NeedToConfirm(null);
					break;
				case 2:
					NeedToConfirm(GetCustomText());
				}
				menues.setControlCanOperate(tab.nativePointer, result == 0);
			} else
				menues.setControlCanOperate(tab.nativePointer, true);
	}

	public void OnTab(long _menu, xmlcontrols.MENUCustomStuff tab) {
		if (!(this.f_afterInitPassed)) {
			return;
		}

		if (!(this.tabPending)) {
			this.currentTab = tab.userid;
			LASTSETTINGS = tab.userid;
			if (this.currentTab < this.settings.size())
				((PanelDialog) this.settings.get(this.currentTab)).update();
		}
	}

	void PassUserDecision(int type) {
		if (this.buttonPending) {
			ISubMenu menu = (ISubMenu) this.m_menus_buttons
					.get(this.currentButton);
			menu.UserDecisionMenuSwitching(type);
			if (this.f_menu_exited)
				return;
			if (type == 2) {
				this.pendingButton = -1;
				this.buttonPending = false;
			} else {
				activatePendingButton();
			}
		} else if (this.tabPending) {
			UserDecisionTabSwitching(type);
			if (this.f_menu_exited)
				return;
			if (type == 2) {
				this.pendingTab = -1;
				this.tabPending = false;
			} else {
				activatePendingTab();
			}
		}
	}

	protected void activateTab(int tab) {
		menues.SetFieldState(((Long) this.tabs.get(tab)).longValue(), 1);
	}

	private void activatePendingButton() {
		this.buttonPending = false;
		this.currentButton = this.pendingButton;
		this.pendingButton = -1;
		selectButton(this.currentButton);
		ISubMenu menu = (ISubMenu) this.m_menus_buttons.get(this.currentButton);
		if (menu != null)
			menu.Activate();
	}

	private void activatePendingTab() {
		this.currentTab = this.pendingTab;
		LASTSETTINGS = this.currentTab;
		menues.setControlCanOperate(
				((Long) this.tabs.get(this.currentTab)).longValue(), true);
		menues.SetFieldState(
				((Long) this.tabs.get(this.currentTab)).longValue(), 1);
		if (this.currentTab < this.settings.size())
			((PanelDialog) this.settings.get(this.currentTab)).update();
		this.pendingTab = -1;
		this.tabPending = false;
	}

	public void OnSelect(int state, Object sender) {
	}

	public void exitMenu() {
		this.f_menu_exited = true;
		Iterator iter = this.m_menus_buttons.iterator();
		while (iter.hasNext()) {
			ISubMenu menu = (ISubMenu) iter.next();
			if (menu != null)
				menu.exitMenu();
		}
		Iterator iter = this.settings.iterator();
		while (iter.hasNext()) {
			((PanelDialog) iter.next()).exitMenu();
		}

		this.m_menus_buttons = null;
		this.settings = null;
	}

	void show() {
	}

	void hide() {
	}

	public void Activate() {
		show();
		Refresh();
	}

	public int LeaveSubMenu() {
		if ((!(this.settings.isEmpty()))
				&& (((PanelDialog) this.settings.get(LASTSETTINGS))
						.areValuesChanged())) {
			this.LASTSETTINGS_CONFIRMING = LASTSETTINGS;
			return 1;
		}
		hide();
		return 0;
	}

	public int LeaveSubMenuSettingsTab() {
		if (((PanelDialog) this.settings.get(LASTSETTINGS)).areValuesChanged()) {
			this.LASTSETTINGS_CONFIRMING = LASTSETTINGS;
			return 1;
		}
		return 0;
	}

	public void UserDecisionMenuSwitching(int type) {
		if (LASTSETTINGS < this.settings.size())
			switch (type) {
			case 0:
				((PanelDialog) this.settings.get(this.LASTSETTINGS_CONFIRMING))
						.OnOk(this.common.GetMenu(), null);
				hide();
				break;
			case 1:
				((PanelDialog) this.settings.get(this.LASTSETTINGS_CONFIRMING))
						.OnExit(this.common.GetMenu(),
								(MENUsimplebutton_field) null);
				hide();
			}
	}

	public void UserDecisionTabSwitching(int type) {
		if (LASTSETTINGS < this.settings.size())
			switch (type) {
			case 0:
				((PanelDialog) this.settings.get(this.LASTSETTINGS_CONFIRMING))
						.OnOk(this.common.GetMenu(), null);
				break;
			case 1:
				((PanelDialog) this.settings.get(this.LASTSETTINGS_CONFIRMING))
						.OnExit(this.common.GetMenu(),
								(MENUsimplebutton_field) null);
			}
	}

	public void afterInit() {
		hide();
		afterInitSubMenues();
		Iterator iter = this.settings.iterator();
		while (iter.hasNext())
			((PanelDialog) iter.next()).afterInit();
	}

	public void Refresh() {
		Iterator iter = this.settings.iterator();
		while (iter.hasNext())
			((PanelDialog) iter.next()).update();
	}

	public String GetCustomText() {
		return null;
	}

	public void SetParent(ParentMenu parent) {
	}

	protected void makeNonActiveState() {
		if (-1 != this.currentButton)
			deselectCurrentButton();
		this.currentButton = -1;
	}
}