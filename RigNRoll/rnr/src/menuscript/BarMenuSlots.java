/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package rnr.src.menuscript;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.menues;
import rnr.src.menuscript.BarMenuSlots.ActionLine;
import rnr.src.menuscript.BarMenuSlots.NewsLine;
import rnr.src.menuscript.table.Table;
import rnr.src.menuscript.tablewrapper.TableData;
import rnr.src.menuscript.tablewrapper.TableLine;
import rnr.src.menuscript.tablewrapper.TableWrapped;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrorg.organaiser;
import rnr.src.rnrscenario.missions.MissionManager;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.map.MissionsMap;
import rnr.src.rnrscenario.missions.map.Place;
import rnr.src.rnrscr.Dialog;
import rnr.src.rnrscr.DialogsSet;
import rnr.src.rnrscr.IMissionInformation;
import rnr.src.rnrscr.MissionDialogs;
import rnr.src.rnrscr.SODialogParams;
import rnr.src.rnrscr.smi.Newspapers;

public class BarMenuSlots {
	private static final boolean DEBUG = eng.noNative;
	private static final String XML = "..\\data\\config\\menu\\menu_bar.xml";
	private static final String TABLE_ACTIONS = "TABLEGROUP BarTALKS Buttons - 4 100";
	private static final String TABLE_NEWS = "TABLEGROUP BarNEWS Buttons - 4 100";
	private static final String GROUP_ACTIONS = "TABLEGROUP ELEMENTS - ACTIONS";
	private static final String GROUP_NEWS = "TABLEGROUP ELEMENTS - NEWS";
	private static final String[] TABLE_LINE_ACTIONS = {
			"BarTALKS - BUTTON - Type TALK",
			"BarTALKS - BUTTON - Type BOX to CLIENT - AVAILABLE",
			"BarTALKS - BUTTON - Type BOX to CLIENT - NA",
			"BarTALKS - BUTTON - Type CLIENT to ADDRESS - AVAILABLE",
			"BarTALKS - BUTTON - Type CLIENT to ADDRESS - NA",
			"BarTALKS - BACK", "STOP BOX", "STOP CLIENT" };
	private static final int ACTION_TALK = 0;
	private static final int ACTION_BOX_A = 1;
	private static final int ACTION_PASS_A = 3;
	private static final int ACTION_BACK = 5;
	private static final int ACTION_STOP_BOX = 6;
	private static final int ACTION_STOP_CLIENT = 7;
	private static final String[] TABLE_LINE_NEWS = { "BarNews Button" };
	private static final int NEWS_NEWS = 0;
	private static final int TYPE_DIALOG = 0;
	public static final int TYPE_PACKAGE = 1;
	private static final int TYPE_PACKAGE_NA = 2;
	public static final int TYPE_PASSANGER = 3;
	private static final int TYPE_PASSANGER_NA = 4;
	private static final String NEWS_ACTION = "onNewsSelect";
	private static final String PICKUP_ACTION = "onPackSelect";
	private BarMenu menu;
	private NewsTable table_news;
	private ActionTable table_actions;
	private vectorJ pos;
	@SuppressWarnings("unchecked")
	private ArrayList<NewsLine> news = new ArrayList<NewsLine>();
	@SuppressWarnings("unchecked")
	private ArrayList<ActionLine> actions = new ArrayList<ActionLine>();

	public BarMenuSlots(BarMenu menu, long _menu, vectorJ position) {
		this.menu = menu;
		this.pos = position;
		this.table_news = new NewsTable(_menu);
		this.table_actions = new ActionTable(_menu);
	}

	public void afterInit() {
		this.table_news.afterInit();
		this.table_actions.afterInit();
		update();
	}

	public boolean anyDialogStart() {
		if (this.actions.size() != 0) {
			Iterator i$ = this.actions.iterator();
			if (i$.hasNext()) {
				ActionLine line = (ActionLine) i$.next();
				if (line.type == 0) {
					DialogActionLine d = (DialogActionLine) line;
					this.menu.startDialog(d.actor);
				} else {
					this.menu.pickUpPack(line.mission_name);
				}
				return true;
			}
		}
		return false;
	}

	public void update() {
		this.news.clear();
		this.actions.clear();
		if (DEBUG) {
			return;
		}
		BarMenu.BarEntry[] entries = null;
		BarMenu.BarActor[] actors = null;
		BarMenu.BarPack[] packs = null;
		if (!(DEBUG)) {
			Place bar_place = MissionSystemInitializer.getMissionsMap()
					.getNearestPlaceByType(0);
			if (null != bar_place)
				entries = Newspapers.getTheOnlyNewsPaper_BarEntries(bar_place
						.getName());
			DialogsSet set = MissionDialogs.queueDialogsForSO(8, this.pos,
					new CoreTime());
			int approks_size = set.getQuestCount();
			int size = 0;
			if (MissionManager.getMSEnable()) {
				for (int i = 0; i < approks_size; ++i)
					size += ((set.getQuest(i).wasPlayed()) ? 0 : 1);
			}
			actors = new BarMenu.BarActor[size];
			packs = new BarMenu.BarPack[0];
			if (null != bar_place)
				packs = MissionEventsMaker.querryBarSlots(bar_place.getName());
			int countActors = 0;
			if (MissionManager.getMSEnable())
				for (int i = 0; i < approks_size; ++i) {
					SODialogParams questParams = set.getQuest(i);
					if (!(questParams.wasPlayed())) {
						String dialogname = questParams.getDescription();
						Dialog d = Dialog.getDialog(dialogname);
						if (null != d) {
							actors[countActors] = new BarMenu.BarActor();
							actors[countActors].params = questParams;
							actors[countActors].dialogname = dialogname;
							actors[countActors].model = questParams
									.getNpcModel();
						} else {
							eng.err("Dialog " + dialogname + " not found");
						}
						++countActors;
					}
				}
		} else {
			entries = new BarMenu.BarEntry[2];
			entries[0] = new BarMenu.BarEntry();
			entries[0].headline = "entrie 1";
			entries[0].papertext = "entrie 1 text";
			entries[0].paperindex = 0;
			entries[0].type = 0;
			entries[0].keys = null;
			entries[0].winner = null;
			entries[0].article = null;
			entries[1] = new BarMenu.BarEntry();
			entries[1].headline = "entrie 2";
			entries[1].papertext = "entrie 2 text";
			entries[1].paperindex = 0;
			entries[1].type = 0;
			entries[1].keys = null;
			entries[1].winner = null;
			entries[1].article = null;

			actors = new BarMenu.BarActor[1];
			actors[0] = new BarMenu.BarActor();
			packs = new BarMenu.BarPack[2];
			packs[0] = new BarMenu.BarPack();
			packs[0].mission_name = "mission 1 name";
			packs[0].type = 1;
			packs[1] = new BarMenu.BarPack();
			packs[1].mission_name = "mission 2 name";
			packs[1].type = 3;
		}
		if (null != entries) {
			for (BarMenu.BarEntry e : entries) {
				this.news.add(new NewsLine(e));
			}
		}
		for (BarMenu.BarActor a : actors) {
			this.actions.add(new DialogActionLine(a));
		}
		boolean hasno_packs_slots = MissionEventsMaker.isPackageSlotBuzzy();
		boolean hasno_pass_slots = MissionEventsMaker.isPassanerSlotBuzzy();
		if (DEBUG) {
			hasno_packs_slots = true;
			hasno_pass_slots = true;
		}
		for (BarMenu.BarPack p : packs) {
			ActionLine line = new ActionLine(p.mission_name, p.type);
			if (line.type == 1)
				line.type = ((hasno_packs_slots) ? 2 : 1);
			else if (line.type == 3)
				line.type = ((hasno_pass_slots) ? 4 : 3);
			this.actions.add(line);
		}
		this.table_actions.updateTable();
		this.table_news.updateTable();
	}

	/**
	 * @return the xml
	 */
	public static String getXml() {
		return XML;
	}

	/**
	 * @return the tableActions
	 */
	public static String getTableActions() {
		return TABLE_ACTIONS;
	}

	/**
	 * @return the groupActions
	 */
	public static String getGroupActions() {
		return GROUP_ACTIONS;
	}

	/**
	 * @return the tableNews
	 */
	public static String getTableNews() {
		return TABLE_NEWS;
	}

	/**
	 * @return the groupNews
	 */
	public static String getGroupNews() {
		return GROUP_NEWS;
	}

	/**
	 * @return the actionTalk
	 */
	public static int getActionTalk() {
		return ACTION_TALK;
	}

	/**
	 * @return the actionBoxA
	 */
	public static int getActionBoxA() {
		return ACTION_BOX_A;
	}

	/**
	 * @return the actionPassA
	 */
	public static int getActionPassA() {
		return ACTION_PASS_A;
	}

	/**
	 * @return the actionBack
	 */
	public static int getActionBack() {
		return ACTION_BACK;
	}

	/**
	 * @return the actionStopBox
	 */
	public static int getActionStopBox() {
		return ACTION_STOP_BOX;
	}

	/**
	 * @return the actionStopClient
	 */
	public static int getActionStopClient() {
		return ACTION_STOP_CLIENT;
	}

	/**
	 * @return the newsNews
	 */
	public static int getNewsNews() {
		return NEWS_NEWS;
	}

	/**
	 * @return the typeDialog
	 */
	public static int getTypeDialog() {
		return TYPE_DIALOG;
	}

	/**
	 * @return the typePackageNa
	 */
	public static int getTypePackageNa() {
		return TYPE_PACKAGE_NA;
	}

	/**
	 * @return the typePassangerNa
	 */
	public static int getTypePassangerNa() {
		return TYPE_PASSANGER_NA;
	}

	/**
	 * @return the newsAction
	 */
	public static String getNewsAction() {
		return NEWS_ACTION;
	}

	/**
	 * @return the pickupAction
	 */
	public static String getPickupAction() {
		return PICKUP_ACTION;
	}

	class ActionTable extends TableWrapped {
		protected void reciveTableData() {
			this.TABLE_DATA.all_lines.addAll(BarMenuSlots.this.actions);
		}

		protected void deinit() {
			this.table.deinit();
		}

		public void enterFocus(Table table) {
		}

		public void leaveFocus(Table table) {
		}

		ActionTable(long paramLong) {
			super(paramLong, 0, false, "..\\data\\config\\menu\\menu_bar.xml",
					"TABLEGROUP BarTALKS Buttons - 4 100", null,
					"TABLEGROUP ELEMENTS - ACTIONS",
					BarMenuSlots.TABLE_LINE_ACTIONS, null, null);
			initButtons(paramLong);
		}

		private final void initButtons(long _menu) {
			long[] controls = this.table
					.getLineStatistics_controls(BarMenuSlots.TABLE_LINE_ACTIONS[0]);
			int i = 0;
			for (long c : controls) {
				MENUsimplebutton_field button = menues.ConvertSimpleButton(c);
				button.userid = (i++);
				menues.UpdateField(button);
				menues.SetScriptOnControl(_menu, button, this, "onPackSelect",
						4L);
			}
			long[] controls = this.table
					.getLineStatistics_controls(BarMenuSlots.TABLE_LINE_ACTIONS[3]);
			int i = 0;
			for (long c : controls) {
				MENUsimplebutton_field button = menues.ConvertSimpleButton(c);
				button.userid = (i++);
				menues.UpdateField(button);
				menues.SetScriptOnControl(_menu, button, this, "onPackSelect",
						4L);
			}
			long[] controls = this.table
					.getLineStatistics_controls(BarMenuSlots.TABLE_LINE_ACTIONS[1]);
			int i = 0;
			for (long c : controls) {
				MENUsimplebutton_field button = menues.ConvertSimpleButton(c);
				button.userid = (i++);
				menues.UpdateField(button);
				menues.SetScriptOnControl(_menu, button, this, "onPackSelect",
						4L);
			}
		}

		public void onSort(long _menu, MENUsimplebutton_field button) {
		}

		public final void SetupLineInTable(long button, int position,
				TableLine table_node) {
			BarMenuSlots.ActionLine line = (BarMenuSlots.ActionLine) table_node;
			boolean to_show = (line.type == position) || (position == 5);
			if (!(to_show)) {
				if ((position == 6) && (line.type == 2))
					to_show = true;
				else if ((position == 7) && (line.type == 4))
					to_show = true;
			}
			menues.SetShowField(button, to_show);
			if (line.type == position) {
				if (position == 0) {
					BarMenuSlots.DialogActionLine dialogline = (BarMenuSlots.DialogActionLine) line;
					String text = organaiser
							.getMissionDescription(MissionDialogs
									.getMissionInfo(dialogline.actor.dialogname)
									.getMissionName());
					menues.SetFieldText(button, text);
				} else {
					String text = organaiser
							.getMissionDescription(line.mission_name);
					menues.SetFieldText(button, text);
				}
				menues.UpdateMenuField(menues.ConvertMenuFields(button));
			}
		}

		public final void updateSelectedInfo(TableLine linedata) {
			linedata = null;
		}

		public void onPackSelect(long _menu, MENUsimplebutton_field button) {
			int i = button.userid;
			BarMenuSlots.ActionLine line = (BarMenuSlots.ActionLine) BarMenuSlots.this.actions
					.get(i);
			if (line.type == 0) {
				BarMenuSlots.DialogActionLine d = (BarMenuSlots.DialogActionLine) line;
				BarMenuSlots.this.menu.startDialog(d.actor);
			} else {
				BarMenuSlots.this.menu.pickUpPack(line.mission_name);
			}
		}
	}

	class NewsTable extends TableWrapped {
		protected void reciveTableData() {
			this.TABLE_DATA.all_lines.addAll(BarMenuSlots.this.news);
		}

		protected void deinit() {
			this.table.deinit();
		}

		public void enterFocus(Table table) {
		}

		public void leaveFocus(Table table) {
		}

		NewsTable(long paramLong) {
			super(paramLong, 0, false, "..\\data\\config\\menu\\menu_bar.xml",
					"TABLEGROUP BarNEWS Buttons - 4 100", null,
					"TABLEGROUP ELEMENTS - NEWS", BarMenuSlots.TABLE_LINE_NEWS,
					null, null);
			initButtons(paramLong);
		}

		private final void initButtons(long _menu) {
			long[] controls = this.table
					.getLineStatistics_controls(BarMenuSlots.TABLE_LINE_NEWS[0]);
			int i = 0;
			for (long c : controls) {
				MENUsimplebutton_field button = menues.ConvertSimpleButton(c);
				button.userid = (i++);
				menues.UpdateField(button);
				menues.SetScriptOnControl(_menu, button, this, "onNewsSelect",
						4L);
			}
		}

		public void onSort(long _menu, MENUsimplebutton_field button) {
		}

		public final void SetupLineInTable(long button, int position,
				TableLine table_node) {
			if (position == 0) {
				BarMenuSlots.NewsLine line = (BarMenuSlots.NewsLine) table_node;
				menues.SetFieldText(button, line.entry.headline);
				menues.UpdateMenuField(menues.ConvertMenuFields(button));
			}
			menues.SetShowField(button, true);
		}

		public final void updateSelectedInfo(TableLine linedata) {
		}

		public void onNewsSelect(long _menu, MENUsimplebutton_field button) {
			BarMenuSlots.this.menu
					.OnPaperSelect(((BarMenuSlots.NewsLine) BarMenuSlots.this.news
							.get(button.userid)).entry);
		}
	}

	static class DialogActionLine extends BarMenuSlots.ActionLine {
		BarMenu.BarActor actor;

		DialogActionLine(BarMenu.BarActor actor) {
			super(0);

			this.actor = actor;
			this.mission_name = actor.params.getMissionName();
		}
	}

	static class ActionLine extends TableLine {
		String mission_name;
		int type;

		ActionLine(int type) {
			this.type = type;
			this.mission_name = "";
		}

		ActionLine(String mission_name, int type) {
			this.type = type;
			this.mission_name = mission_name;
		}
	}

	static class NewsLine extends TableLine {
		BarMenu.BarEntry entry;

		NewsLine(BarMenu.BarEntry entry) {
			this.entry = entry;
		}
	}
}