/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

import java.io.PrintStream;
import menu.MENUText_field;
import menu.MenuAfterInitNarrator;
import menu.menucreation;
import menu.menues;
import rnrorg.IStoreorgelement;
import rnrorg.organaiser;

public class MissionQuestItemIcon implements menucreation {
	private static final String XML = "..\\data\\config\\menu\\specmenu.xml";
	private static final String[] GROUPS_NORMAL = {
			"TIP - Type CLIENT to ADDRESS - AVAILABLE",
			"TIP - Type CLIENT to ADDRESS - NA",
			"TIP - Type BOX to CLIENT - AVAILABLE",
			"TIP - Type BOX to CLIENT - NA",
			"TIP - Type DELIVERY to CLIENT - AVAILABLE",
			"TIP - Type DELIVERY to CLIENT - NA",
			"TIP - Type TALK - AVAILABLE", "endmission" };

	private static final String[] GROUPS_SPEED = {
			"TIP - Type CLIENT to ADDRESS - AVAILABLE - SPEED LIMIT",
			"TIP - Type CLIENT to ADDRESS - NA - SPEED LIMIT",
			"TIP - Type BOX to CLIENT - AVAILABLE - SPEED LIMIT",
			"TIP - Type BOX to CLIENT - NA - SPEED LIMIT",
			"TIP - Type DELIVERY to CLIENT - AVAILABLE - SPEED LIMIT",
			"TIP - Type DELIVERY to CLIENT - NA - SPEED LIMIT",
			"TIP - Type TALK - AVAILABLE - SPEED LIMIT",
			"endmission - SPEED LIMIT" };
	private static final int PASS_A = 0;
	private static final int PASS_NA = 1;
	private static final int PACK_A = 2;
	private static final int PACK_NA = 3;
	private static final int TRAILER_A = 4;
	private static final int TRAILER_NA = 5;
	private static final int TALK_A = 6;
	private static final int FINISH_MISSION = 7;
	private static final String TEXTFIELD = "BlueSign - TEXT - HeightStep 31px";
	private static final String BLUE_SIGN = "BlueSign - BACK - HeightStep 31px";
	private static final String AWAIBLE = "ENTER - HeightStep 31px";
	private static final String NOTAWAIBLE = "STOP - HeightStep 31px";
	private static final String BOLT_LEFT_BOTTOM = "BlueSign - BACK - bolt LEFT BOTTOM";
	private static final String BOLT_RIGHT_BOTTOM = "BlueSign - BACK - bolt RIGHT BOTTOM";
	private long control_group_speed = 0L;
	private long text_field_speed = 0L;
	private long blue_sign_field_speed = 0L;
	private long awaible_field_speed = 0L;
	private long not_awaible_field_speed = 0L;
	private long bolt_left_bottom_speed = 0L;
	private long bolt_right_bottom_speed = 0L;
	private int native_poy_speed;
	private long control_group_normal = 0L;
	private long text_field_normal = 0L;
	private long blue_sign_field_normal = 0L;
	private long awaible_field_normal = 0L;
	private long not_awaible_field_normal = 0L;
	private long bolt_left_bottom_normal = 0L;
	private long bolt_right_bottom_normal = 0L;
	private int native_poy_normal;
	private int type;
	private String text;
	private String menuID;
	private long _menu = 0L;
	private int _slot = 0;
	private boolean bSpeeding = false;
	private boolean bKillMePlease = false;

	public void restartMenu(long _menu) {
	}

	private MissionQuestItemIcon(int type, String text, int slot,
			boolean speeding) {
		this.text = text;
		this.type = type;
		this._slot = slot;
		this.bSpeeding = speeding;
		switch (type) {
		case 0:
			this.menuID = "missioniconPassangerAMENU";
			break;
		case 1:
			this.menuID = "missioniconPassangerNAMENU";
			break;
		case 2:
			this.menuID = "missioniconPackageAMENU";
			break;
		case 3:
			this.menuID = "missioniconPackageNAMENU";
			break;
		case 4:
			this.menuID = "missioniconTrailerAMENU";
			break;
		case 5:
			this.menuID = "missioniconTrailerNAMENU";
			break;
		case 7:
			this.menuID = "missioniconEndMENU";
			break;
		case 6:
			this.menuID = "missioniconTalkAMENU";
		}
	}

	public void remove() {
		if (this._menu != 0L) {
			menues.CallMenuCallBack_ExitMenu(this._menu);
			this.bKillMePlease = false;
		} else {
			this.bKillMePlease = true;
		}
	}

	public static MissionQuestItemIcon create_trailer(String mission_name,
			boolean available, int slot) {
		String text = organaiser.getMissionDescription(mission_name);
		MissionQuestItemIcon ret = new MissionQuestItemIcon(
				(available) ? 4 : 5, text, slot, false);
		menues.createSimpleMenu(ret, 0);
		return ret;
	}

	public static MissionQuestItemIcon create_warehouse_trailer(
			boolean available, int slot) {
		IStoreorgelement warehouseOrderElement = organaiser.getInstance()
				.getCurrentWarehouseOrder();
		String text;
		String text;
		if (null == warehouseOrderElement)
			text = "NOT VALID";
		else {
			text = warehouseOrderElement.getDescription();
		}
		MissionQuestItemIcon ret = new MissionQuestItemIcon(
				(available) ? 4 : 5, text, slot, false);
		menues.createSimpleMenu(ret, 0);
		return ret;
	}

	public static MissionQuestItemIcon create_package(String mission_name,
			boolean available) {
		String text = organaiser.getMissionDescription(mission_name);
		MissionQuestItemIcon ret = new MissionQuestItemIcon(
				(available) ? 2 : 3, text, 0, false);
		menues.createSimpleMenu(ret, 0);
		return ret;
	}

	public static MissionQuestItemIcon create_passanger(String mission_name,
			boolean available) {
		String text = organaiser.getMissionDescription(mission_name);
		MissionQuestItemIcon ret = new MissionQuestItemIcon(
				(available) ? 0 : 1, text, 0, false);
		menues.createSimpleMenu(ret, 0);
		return ret;
	}

	public static MissionQuestItemIcon create_finish_mission_icon(
			String mission_name) {
		String text = organaiser.getMissionDescription(mission_name);
		MissionQuestItemIcon ret = new MissionQuestItemIcon(7, text, 0, false);
		menues.createSimpleMenu(ret, 0);
		return ret;
	}

	public static MissionQuestItemIcon create_talk_available_icon(
			String mission_name) {
		String text = organaiser.getMissionDescription(mission_name);
		MissionQuestItemIcon ret = new MissionQuestItemIcon(6, text, 0, false);
		menues.createSimpleMenu(ret, 0);
		return ret;
	}

	public void InitMenu(long _menu) {
		menues.InitXml(_menu, "..\\data\\config\\menu\\specmenu.xml",
				GROUPS_SPEED[this.type]);
		menues.InitXml(_menu, "..\\data\\config\\menu\\specmenu.xml",
				GROUPS_NORMAL[this.type]);

		this.control_group_normal = menues.FindFieldInMenu(_menu,
				GROUPS_NORMAL[this.type]);
		this.text_field_normal = menues.FindFieldInMenu_ByParent(_menu,
				GROUPS_NORMAL[this.type], "BlueSign - TEXT - HeightStep 31px");
		this.blue_sign_field_normal = menues.FindFieldInMenu_ByParent(_menu,
				GROUPS_NORMAL[this.type], "BlueSign - BACK - HeightStep 31px");
		this.awaible_field_normal = menues.FindFieldInMenu_ByParent(_menu,
				GROUPS_NORMAL[this.type], "ENTER - HeightStep 31px");
		this.not_awaible_field_normal = menues.FindFieldInMenu_ByParent(_menu,
				GROUPS_NORMAL[this.type], "STOP - HeightStep 31px");
		this.bolt_left_bottom_normal = menues.FindFieldInMenu_ByParent(_menu,
				GROUPS_NORMAL[this.type], "BlueSign - BACK - bolt LEFT BOTTOM");
		this.bolt_right_bottom_normal = menues
				.FindFieldInMenu_ByParent(_menu, GROUPS_NORMAL[this.type],
						"BlueSign - BACK - bolt RIGHT BOTTOM");

		this.control_group_speed = menues.FindFieldInMenu(_menu,
				GROUPS_SPEED[this.type]);
		this.text_field_speed = menues.FindFieldInMenu_ByParent(_menu,
				GROUPS_SPEED[this.type], "BlueSign - TEXT - HeightStep 31px");
		this.blue_sign_field_speed = menues.FindFieldInMenu_ByParent(_menu,
				GROUPS_SPEED[this.type], "BlueSign - BACK - HeightStep 31px");
		this.awaible_field_speed = menues.FindFieldInMenu_ByParent(_menu,
				GROUPS_SPEED[this.type], "ENTER - HeightStep 31px");
		this.not_awaible_field_speed = menues.FindFieldInMenu_ByParent(_menu,
				GROUPS_SPEED[this.type], "STOP - HeightStep 31px");
		this.bolt_left_bottom_speed = menues.FindFieldInMenu_ByParent(_menu,
				GROUPS_SPEED[this.type], "BlueSign - BACK - bolt LEFT BOTTOM");
		this.bolt_right_bottom_speed = menues.FindFieldInMenu_ByParent(_menu,
				GROUPS_SPEED[this.type], "BlueSign - BACK - bolt RIGHT BOTTOM");
	}

	private void ActualRearange() {
		if ((this._menu != 0L) && (((this.type == 4) || (this.type == 5)))) {
			if (this.text_field_normal != 0L) {
				MENUText_field field = menues
						.ConvertTextFields(this.control_group_normal);
				if (field != null) {
					if (this._slot == 1)
						field.poy = (this.native_poy_normal + 130);
					else {
						field.poy = this.native_poy_normal;
					}
					menues.UpdateField(field);
				}
			}

			if (this.text_field_speed != 0L) {
				MENUText_field field = menues
						.ConvertTextFields(this.control_group_speed);
				if (field != null) {
					if (this._slot == 1)
						field.poy = (this.native_poy_speed + 130);
					else {
						field.poy = this.native_poy_speed;
					}
					menues.UpdateField(field);
				}
			}

		}

		if (!(this.bSpeeding)) {
			menues.SetShowField(this.control_group_normal, true);
			menues.SetShowField(this.control_group_speed, false);
		} else {
			menues.SetShowField(this.control_group_normal, false);
			menues.SetShowField(this.control_group_speed, true);
		}
	}

	public void Rearange(int slot) {
		this._slot = slot;
		ActualRearange();
	}

	public void SetSpeeding(boolean bSpeed) {
		this.bSpeeding = bSpeed;
		ActualRearange();
	}

	public void AfterInitMenu(long _menu) {
		MenuAfterInitNarrator.justShow(_menu);

		if (this.text_field_normal != 0L) {
			MENUText_field field = menues
					.ConvertTextFields(this.text_field_normal);
			int text_height = menues.GetTextHeight(field.nativePointer,
					this.text);
			int text_baseline = menues.GetBaseLine(field.nativePointer);
			int text_lineheight = menues.GetTextLineHeight(field.nativePointer);
			int text_lines = Converts.HeightToLines(text_height, text_baseline,
					text_lineheight);

			field.text = this.text;

			this.native_poy_normal = field.poy;

			if (text_lines > 2) {
				field.leny += 25 * (text_lines - 2);
			}

			if ((this.blue_sign_field_normal != 0L) && (text_lines > 2)) {
				MENUText_field back = menues
						.ConvertTextFields(this.blue_sign_field_normal);
				back.leny += 25 * (text_lines - 2);
				menues.UpdateField(back);
			}

			if ((this.awaible_field_normal != 0L) && (text_lines > 2)) {
				MENUText_field awail = menues
						.ConvertTextFields(this.awaible_field_normal);
				awail.poy += 25 * (text_lines - 2);
				menues.UpdateField(awail);
			}

			if ((this.not_awaible_field_normal != 0L) && (text_lines > 2)) {
				MENUText_field notawail = menues
						.ConvertTextFields(this.not_awaible_field_normal);
				notawail.poy += 25 * (text_lines - 2);
				menues.UpdateField(notawail);
			}

			if ((this.bolt_left_bottom_normal != 0L) && (text_lines > 2)) {
				MENUText_field _field = menues
						.ConvertTextFields(this.bolt_left_bottom_normal);
				_field.poy += 25 * (text_lines - 2);
				menues.UpdateField(_field);
			}

			if ((this.bolt_right_bottom_normal != 0L) && (text_lines > 2)) {
				MENUText_field _field = menues
						.ConvertTextFields(this.bolt_right_bottom_normal);
				_field.poy += 25 * (text_lines - 2);
				menues.UpdateField(_field);
			}
			menues.UpdateField(field);
		}

		if (this.control_group_normal != 0L) {
			MENUText_field field = menues
					.ConvertTextFields(this.control_group_normal);
			this.native_poy_normal = field.poy;
		}

		if (this.text_field_speed != 0L) {
			MENUText_field field = menues
					.ConvertTextFields(this.text_field_speed);
			int text_height = menues.GetTextHeight(field.nativePointer,
					this.text);
			int text_baseline = menues.GetBaseLine(field.nativePointer);
			int text_lineheight = menues.GetTextLineHeight(field.nativePointer);
			int text_lines = Converts.HeightToLines(text_height, text_baseline,
					text_lineheight);

			field.text = this.text;

			this.native_poy_speed = field.poy;

			if (text_lines > 2) {
				field.leny += 25 * (text_lines - 2);
			}

			if ((this.blue_sign_field_speed != 0L) && (text_lines > 2)) {
				MENUText_field back = menues
						.ConvertTextFields(this.blue_sign_field_speed);
				back.leny += 25 * (text_lines - 2);
				menues.UpdateField(back);
			}

			if ((this.awaible_field_speed != 0L) && (text_lines > 2)) {
				MENUText_field awail = menues
						.ConvertTextFields(this.awaible_field_speed);
				awail.poy += 25 * (text_lines - 2);
				menues.UpdateField(awail);
			}

			if ((this.not_awaible_field_speed != 0L) && (text_lines > 2)) {
				MENUText_field notawail = menues
						.ConvertTextFields(this.not_awaible_field_speed);
				notawail.poy += 25 * (text_lines - 2);
				menues.UpdateField(notawail);
			}

			if ((this.bolt_left_bottom_speed != 0L) && (text_lines > 2)) {
				MENUText_field _field = menues
						.ConvertTextFields(this.bolt_left_bottom_speed);
				_field.poy += 25 * (text_lines - 2);
				menues.UpdateField(_field);
			}

			if ((this.bolt_right_bottom_speed != 0L) && (text_lines > 2)) {
				MENUText_field _field = menues
						.ConvertTextFields(this.bolt_right_bottom_speed);
				_field.poy += 25 * (text_lines - 2);
				menues.UpdateField(_field);
			}
			menues.UpdateField(field);
		}

		if (this.control_group_speed != 0L) {
			MENUText_field field = menues
					.ConvertTextFields(this.control_group_speed);
			this.native_poy_speed = field.poy;
		}
		this._menu = _menu;

		Rearange(this._slot);
		if (this.bKillMePlease)
			remove();
	}

	public void exitMenu(long _menu) {
		System.err.println("exitMenu " + this._menu + this.text);
		this._menu = 0L;
		this.bKillMePlease = false;
	}

	public String getMenuId() {
		return this.menuID;
	}
}