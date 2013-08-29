/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

import menu.BalanceUpdater;
import menu.BaseMenu;
import menu.Common;
import menu.JavaEvents;
import menu.KeyPair;
import menu.ListenerManager;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.NotifyInformation;
import menu.Table;
import menu.TableCallbacks;
import menu.TableNode;
import menu.TextScroller;
import menu.TimeData;
import menu.menucreation;
import menu.menues;
import rnrconfig.IconMappings;
import rnrcore.event;
import rnrcore.loc;
import rnrorg.JournalFinishWarehouse;

public class RacePanelMenu extends BaseMenu implements menucreation {
	public static final int STARTED = 0;
	public static final int READY = 1;
	public static final int WAITING = 2;
	private static final double FADE_DELAY = 2.0D;
	private static final double FADE_DUR = 1.0D;
	private static final int s_animid = Common.GetID();
	private static final int s_clockid = Common.GetID();
	private static final int NAME = 0;
	private static final int RATING = 1;
	private static final int POSITION = 2;
	private static final int SPEED = 3;
	private static final int TIME = 4;
	private static final int STATUS_STARTED = 5;
	private static final int STATUS_READY = 6;
	private static final int STATUS_WAITING = 7;
	private static final int THROUGH_TIME = 5;
	private static final int CHECKPOINT_TIME = 5;
	private static final String[] XMLS = { "..\\data\\config\\menu\\menu_race",
			"..\\data\\config\\menu\\menu_warehouse" };
	private static final boolean[] needRaceid = { true, false };
	private static final int MENU_RACE = 0;
	private static final int MENU_WH = 1;
	private static int menu_xml = 0;
	private String menuId;
	long balanc_control = 0L;

	static int s_response = 0;

	private static boolean changeinterior = false;

	private static boolean immediateshow = false;

	private static long pr_menu_win = 0L;
	OfficeTab.FadeAnimationTextField m_fadeText;
	OfficeTab.FadeAnimation_ControlColor m_fadeColor;
	Table m_partstable;
	static TextScroller s_scroller = null;
	static Common s_common;
	static String s_controlgroup;
	static int s_raceid = 0;
	static String s_race_name = null;
	static String s_race_id = null;
	static int s_money = -1;
	static String s_finish;
	static int s_ratingreq = -1;
	static int s_2ndratingreq = -1;
	static String s_nextcheckpoint;
	static TimeData s_timeallowed;
	static String s_message;
	static String s_drivername;
	static int s_rating = -1;
	static int s_startposition = -1;
	static int s_isfirst = -1;
	static int s_rating_prize = -1;
	static TimeData s_total;
	static double s_topspeed = -1.0D;
	static double s_avespeed = -1.0D;
	static int s_ratingboost = -1;
	static int s_forefit = -1;
	static boolean s_hasok;
	static boolean s_bigracewin = false;
	static int s_medal = -1;
	static int s_place = -1;
	static boolean s_flashingwait = false;
	static boolean s_yesno = false;
	static int s_towrate = -1;
	static int s_balance = -1;
	static boolean s_fadeevent = false;
	static int s_clockdown = -1;
	static String s_race_logo_id = null;
	static boolean s_hidesemi = false;

	static ParticipantInfo[] s_participants = null;
	static SummaryInfo[] s_summary = null;

	static RacePanelMenu s_instance = null;
	MENUText_field m_fadingcontrol;
	MENUText_field m_clockcontrol;

	public void restartMenu(long _menu) {
	}

	RacePanelMenu(String id) {
		this.menuId = id;
	}

	public static void ExitPanel() {
		if (!(s_fadeevent))
			menues.CallMenuCallBack_ExitMenu(s_common.GetMenu());
		else
			menues.SetScriptObjectAnimation(1L, s_animid, s_instance, "OnAnim");
	}

	public static void Refresh() {
		if ((s_participants != null) && (s_instance != null)
				&& (s_instance.m_partstable != null))
			s_instance.m_partstable.RedrawTable();
	}

	static int GetResponse() {
		return s_response;
	}

	public static long PanelPreparingToRaceIn() {
		changeinterior = true;
		s_controlgroup = "Panel - Common - PREPARING TO RACE - IN";
		s_fadeevent = true;
		menu_xml = 1;
		return menues.createSimpleMenu(
				new RacePanelMenu("preparetoRaceInMENU"), 1000000000.0D, "",
				1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelPreparingToRaceOut() {
		menu_xml = 1;
		s_controlgroup = "Panel - Common - PREPARING TO RACE - OUT";
		s_fadeevent = true;
		return menues.createSimpleMenu(
				new RacePanelMenu("preparetoRaceOutMENU"), 1000000000.0D, "",
				1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelPreparingToOrdersIn() {
		menu_xml = 1;
		changeinterior = true;
		s_controlgroup = "Panel - Common - PREPARING TO RACE - IN";
		s_fadeevent = true;
		return menues.createSimpleMenu(new RacePanelMenu(
				"preparetoOrdersInMENU"), 1000000000.0D, "", 1600, 1200, 1600,
				1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelPreparingToOrdersOut() {
		menu_xml = 1;
		s_controlgroup = "Panel - Common - PREPARING TO RACE - OUT";
		s_fadeevent = true;
		return menues.createSimpleMenu(new RacePanelMenu(
				"preparetoOrdersOutMENU"), 1000000000.0D, "", 1600, 1200, 1600,
				1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelListOfParticipantsIn(int raceid, String racename,
			String logo_name, ParticipantInfo[] info) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - LIST OF PARTICIPANTS - IN";
		s_flashingwait = true;
		s_participants = info;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		String id = "";
		switch (raceid) {
		case 1:
			id = "participantsRaceIn04MENU";
			break;
		case 2:
			id = "participantsRaceIn03MENU";
			break;
		case 3:
			id = "participantsRaceIn02MENU";
			break;
		case 4:
			id = "participantsRaceIn01MENU";
		}

		return menues.createSimpleMenu(new RacePanelMenu(id), 1000000000.0D,
				"", 1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelListOfParticipantsOut(int raceid, String racename,
			String logo_name, ParticipantInfo[] info) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - LIST OF PARTICIPANTS - OUT";
		s_flashingwait = true;
		s_participants = info;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		String id = "";
		switch (raceid) {
		case 1:
			id = "participantsRaceOut04MENU";
			break;
		case 2:
			id = "participantsRaceOut03MENU";
			break;
		case 3:
			id = "participantsRaceOut02MENU";
			break;
		case 4:
			id = "participantsRaceOut01MENU";
		}
		return menues.createSimpleMenu(new RacePanelMenu(id), 1000000000.0D,
				"", 1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelSummaryReport(int raceid, String racename,
			String logo_name, SummaryInfo[] info) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - SUMMARY REPORT";
		s_summary = info;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		String id = "";
		switch (raceid) {
		case 1:
			id = "raceSummary04MENU";
			break;
		case 2:
			id = "raceSummary03MENU";
			break;
		case 3:
			id = "raceSummary02MENU";
			break;
		case 4:
			id = "raceSummary01MENU";
		}
		return menues.createSimpleMenu(new RacePanelMenu(id), 10.0D, "", 1600,
				1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelQualified(int raceid, String racename,
			String logo_name, int money_prize, int rating_prize, int ratingreq,
			String finish, String nextcheckpoint, TimeData timeallowed,
			String drivername, int driverrating, int driverstartposition,
			int clockdown) {
		menu_xml = 0;
		s_raceid = raceid;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - QUALIFY";
		s_money = money_prize;
		s_finish = finish;
		s_ratingreq = ratingreq;
		s_rating_prize = rating_prize;
		s_nextcheckpoint = nextcheckpoint;
		s_timeallowed = timeallowed;
		s_drivername = drivername;
		s_rating = driverrating;
		s_startposition = driverstartposition;
		s_hasok = false;
		s_flashingwait = true;
		s_yesno = true;
		s_clockdown = clockdown;
		String id = "";
		switch (raceid) {
		case 1:
			id = "raceQualified04MENU";
			break;
		case 2:
			id = "raceQualified03MENU";
			break;
		case 3:
			id = "raceQualified02MENU";
			break;
		case 4:
			id = "raceQualified01MENU";
		}
		RacePanelMenu menuuu = new RacePanelMenu(id);
		return menues.createSimpleMenu(menuuu, 1000000000.0D, "ESC", 1600,
				1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelNotqualified(int raceid, String racename,
			String logo_name, int money_prize, int rating_prize, int ratingreq,
			String finish, String nextcheckpoint, TimeData timeallowed,
			String drivername, int driverrating) {
		menu_xml = 0;
		s_raceid = raceid;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - DO NOT QUALIFY";
		s_money = money_prize;
		s_finish = finish;
		s_ratingreq = ratingreq;
		s_rating_prize = rating_prize;
		s_nextcheckpoint = nextcheckpoint;
		s_timeallowed = timeallowed;
		s_drivername = drivername;
		s_rating = driverrating;
		s_hasok = false;
		s_2ndratingreq = ratingreq;

		String id = "";
		switch (raceid) {
		case 1:
			id = "raceNotQualified04MENU";
			break;
		case 2:
			id = "raceNotQualified03MENU";
			break;
		case 3:
			id = "raceNotQualified02MENU";
			break;
		case 4:
			id = "raceNotQualified01MENU";
		}
		return menues.createSimpleMenu(new RacePanelMenu(id), 5.0D, "ESC",
				1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelCheckpointFirst(int raceid, String source,
			String destination, String racename, String logo_name,
			boolean isfirst, String finish, String nextcheckpoint,
			TimeData timeallowed, int ratingboost, TimeData total,
			double topspeed, double avespeed) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - CHECKPOINT";
		s_finish = finish;
		s_nextcheckpoint = nextcheckpoint;
		s_timeallowed = timeallowed;
		s_total = total;
		s_topspeed = topspeed;
		s_avespeed = avespeed;
		s_isfirst = (isfirst) ? 1 : 0;
		s_ratingboost = ratingboost;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;

		s_hasok = false;

		String id = "";
		switch (raceid) {
		case 1:
			id = "raceCheckPointFirst04MENU";
			break;
		case 2:
			id = "raceCheckPointFirst03MENU";
			break;
		case 3:
			id = "raceCheckPointFirst02MENU";
			break;
		case 4:
			id = "raceCheckPointFirst01MENU";
		}
		int journal_message = 0;
		switch (raceid) {
		case 1:
			journal_message = 15;
			break;
		case 2:
			journal_message = 16;
			break;
		case 3:
			journal_message = 17;
			break;
		case 4:
			journal_message = 18;
		}
		JournalFinishWarehouse.createNote(journal_message,
				loc.getBigraceShortName(racename), logo_name, source,
				destination, 1, ratingboost, 0);
		return menues.createSimpleMenu(new RacePanelMenu(id), 5.0D, "ESC",
				1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelDropOrContinue(int raceid, String racename,
			String logo_name) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - DROP OR CONTINUE";
		s_yesno = true;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		String id = "";
		switch (raceid) {
		case 1:
			id = "raceDropOrContinue04MENU";
			break;
		case 2:
			id = "raceDropOrContinue03MENU";
			break;
		case 3:
			id = "raceDropOrContinue02MENU";
			break;
		case 4:
			id = "raceDropOrContinue01MENU";
		}
		return menues.createSimpleMenu(new RacePanelMenu(id), 1000000000.0D,
				"ESC", 1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelCheckpointMissed(int raceid, String racename,
			String logo_name, String nextcheckpoint, TimeData timeallowed,
			String finish) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - CHECKPOINT MISSED";
		s_nextcheckpoint = nextcheckpoint;
		s_timeallowed = timeallowed;
		s_finish = finish;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		String id = "";
		switch (raceid) {
		case 1:
			id = "raceCheckpointMissed04MENU";
			break;
		case 2:
			id = "raceCheckpointMissed03MENU";
			break;
		case 3:
			id = "raceCheckpointMissed02MENU";
			break;
		case 4:
			id = "raceCheckpointMissed01MENU";
		}
		return menues.createSimpleMenu(new RacePanelMenu(id), 1000000000.0D,
				"ESC", 1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static void MenuImmediateShow(boolean value) {
		immediateshow = value;
	}

	public static long RaceNotAParticipant(int raceid, String racename,
			String logo_name) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - NOT A PARTICIPANT";
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		String id = "";
		switch (raceid) {
		case 1:
			id = "raceNotAParticipant04MENU";
			break;
		case 2:
			id = "raceNotAParticipant03MENU";
			break;
		case 3:
			id = "raceNotAParticipant02MENU";
			break;
		case 4:
			id = "raceNotAParticipant01MENU";
		}
		return menues.createSimpleMenu(new RacePanelMenu(id), 5.0D, "", 1600,
				1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
	}

	public static long PanelRaceWin(int raceid, String source,
			String destination, String racename, String logo_name, int medal,
			int money, int ratingboost, TimeData total, double topspeed,
			double avespeed, int balance) {
		menu_xml = 0;
		s_raceid = raceid;
		s_bigracewin = true;
		s_medal = medal;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		String id = "";
		switch (s_medal) {
		case 0:
			s_controlgroup = "Panel - Race"
					+ Converts.bigRaceSuffixes(s_raceid) + " - GRAND PRIX";
			switch (raceid) {
			case 1:
				id = "raceWinGold04MENU";
				break;
			case 2:
				id = "raceWinGold03MENU";
				break;
			case 3:
				id = "raceWinGold02MENU";
				break;
			case 4:
				id = "raceWinGold01MENU";
			}
			break;
		case 1:
			s_controlgroup = "Panel - Race"
					+ Converts.bigRaceSuffixes(s_raceid) + " - SILVER CUP";
			switch (raceid) {
			case 1:
				id = "raceWinSilver04MENU";
				break;
			case 2:
				id = "raceWinSilver03MENU";
				break;
			case 3:
				id = "raceWinSilver02MENU";
				break;
			case 4:
				id = "raceWinSilver01MENU";
			}
			break;
		case 2:
			s_controlgroup = "Panel - Race"
					+ Converts.bigRaceSuffixes(s_raceid) + " - BRONZE CUP";
			switch (raceid) {
			case 1:
				id = "raceWinBronze04MENU";
				break;
			case 2:
				id = "raceWinBronze03MENU";
				break;
			case 3:
				id = "raceWinBronze02MENU";
				break;
			case 4:
				id = "raceWinBronze01MENU";
			}
		}

		s_total = total;
		s_topspeed = topspeed;
		s_avespeed = avespeed;
		s_ratingboost = ratingboost;
		s_medal = medal;
		s_hasok = true;
		s_balance = balance;
		s_money = money;

		pr_menu_win = menues.createSimpleMenu(new RacePanelMenu(id),
				1000000000.0D, "", 1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
		int journal_message = 0;
		switch (raceid) {
		case 1:
			switch (medal) {
			case 0:
				journal_message = 23;
				break;
			case 1:
				journal_message = 27;
				break;
			case 2:
				journal_message = 31;
			}
			break;
		case 2:
			switch (medal) {
			case 0:
				journal_message = 24;
				break;
			case 1:
				journal_message = 28;
				break;
			case 2:
				journal_message = 32;
			}
			break;
		case 3:
			switch (medal) {
			case 0:
				journal_message = 25;
				break;
			case 1:
				journal_message = 29;
				break;
			case 2:
				journal_message = 33;
			}
			break;
		case 4:
			switch (medal) {
			case 0:
				journal_message = 26;
				break;
			case 1:
				journal_message = 30;
				break;
			case 2:
				journal_message = 34;
			}
		}

		JournalFinishWarehouse.createNote(journal_message, racename, logo_name,
				source, destination, medal, ratingboost, money);
		return pr_menu_win;
	}

	public static void animate_closewin() {
		if (!(s_bigracewin))
			return;
		menues.SetScriptObjectAnimation(pr_menu_win, 10L,
				new RacePanelMenu(""), "closeWin");
	}

	public void closeWin(long cookie, double time) {
		if (pr_menu_win != cookie) {
			menues.StopScriptAnimation(10L);
			return;
		}
		if (time > 5.0D) {
			menues.StopScriptAnimation(10L);
			menues.CallMenuCallBack_ExitMenu(cookie);
		}
	}

	public static long PanelRaceFinish(int raceid, String source,
			String destination, String racename, String logo_name, int place,
			int ratingboost, TimeData total, double topspeed, double avespeed,
			int balance) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - FINISHED";
		s_place = place;
		s_total = total;
		s_topspeed = topspeed;
		s_avespeed = avespeed;
		s_ratingboost = ratingboost;
		s_balance = balance;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;

		String id = "";
		switch (raceid) {
		case 1:
			id = "raceFinish04MENU";
			break;
		case 2:
			id = "raceFinish03MENU";
			break;
		case 3:
			id = "raceFinish02MENU";
			break;
		case 4:
			id = "raceFinish01MENU";
		}
		long res = menues.createSimpleMenu(new RacePanelMenu(id), 5.0D, "ESC",
				1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
		int journal_message = 0;
		switch (raceid) {
		case 1:
			journal_message = 19;
			break;
		case 2:
			journal_message = 20;
			break;
		case 3:
			journal_message = 21;
			break;
		case 4:
			journal_message = 22;
		}
		JournalFinishWarehouse.createNote(journal_message, racename, logo_name,
				source, destination, place + 1, ratingboost, 0);
		return res;
	}

	public static long PanelRaceCancelled(int raceid, String racename,
			String logo_name, int ratingboost, boolean use_semi) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - CANCELLED";
		s_ratingboost = ratingboost;
		s_hasok = true;
		s_hidesemi = !(use_semi);
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		String id = "";
		switch (raceid) {
		case 1:
			id = "raceCanceled04MENU";
			break;
		case 2:
			id = "raceCanceled03MENU";
			break;
		case 3:
			id = "raceCanceled02MENU";
			break;
		case 4:
			id = "raceCanceled01MENU";
		}
		long res = menues.createSimpleMenu(new RacePanelMenu(id),
				1000000000.0D, "ESC", 1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
		int journal_message = 0;
		switch (raceid) {
		case 1:
			journal_message = 47;
			break;
		case 2:
			journal_message = 48;
			break;
		case 3:
			journal_message = 49;
			break;
		case 4:
			journal_message = 50;
		}
		JournalFinishWarehouse.createNote(journal_message, racename, logo_name,
				"", "", 0, ratingboost, 0);
		return res;
	}

	public static long PanelRaceDamaged(int raceid, String racename,
			String logo_name, int forfeit, int ratingboost, int balance,
			boolean use_semi) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - DAMAGED - OUT";
		s_ratingboost = ratingboost;
		s_hasok = true;
		s_hidesemi = !(use_semi);
		s_forefit = forfeit;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		String id = "";
		switch (raceid) {
		case 1:
			id = "raceDefaulted04MENU";
			break;
		case 2:
			id = "raceDefaulted03MENU";
			break;
		case 3:
			id = "raceDefaulted02MENU";
			break;
		case 4:
			id = "raceDefaulted01MENU";
		}
		long res = menues.createSimpleMenu(new RacePanelMenu(id),
				1000000000.0D, "ESC", 1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
		int journal_message = 0;
		switch (raceid) {
		case 1:
			journal_message = 35;
			break;
		case 2:
			journal_message = 36;
			break;
		case 3:
			journal_message = 37;
			break;
		case 4:
			journal_message = 38;
		}
		JournalFinishWarehouse.createNote(journal_message, racename, logo_name,
				"", "", 0, ratingboost, 0);
		return res;
	}

	public static long PanelRaceDefaulted(int raceid, String racename,
			String logo_name, int ratingboost, boolean use_semi) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - DEFAULTED";
		s_ratingboost = ratingboost;
		s_hasok = true;
		s_hidesemi = !(use_semi);
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		String id = "";
		switch (raceid) {
		case 1:
			id = "raceDefaulted04MENU";
			break;
		case 2:
			id = "raceDefaulted03MENU";
			break;
		case 3:
			id = "raceDefaulted02MENU";
			break;
		case 4:
			id = "raceDefaulted01MENU";
		}
		long res = menues.createSimpleMenu(new RacePanelMenu(id),
				1000000000.0D, "ESC", 1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
		int journal_message = 0;
		switch (raceid) {
		case 1:
			journal_message = 43;
			break;
		case 2:
			journal_message = 44;
			break;
		case 3:
			journal_message = 45;
			break;
		case 4:
			journal_message = 46;
		}
		JournalFinishWarehouse.createNote(journal_message, racename, logo_name,
				"", "", 0, ratingboost, 0);
		return res;
	}

	public static long PanelTowedAndDefaulted(int raceid, String racename,
			String logo_name, int towrate, int ratingboost, int balance) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - TOWED AND DEFAULTED";
		s_ratingboost = ratingboost;
		s_hasok = true;
		s_towrate = towrate;
		s_balance = balance;
		s_race_name = loc.getBigraceShortName(racename);
		s_race_id = racename;
		s_race_logo_id = logo_name;
		String id = "";
		switch (raceid) {
		case 1:
			id = "raceTowedDefaulted04MENU";
			break;
		case 2:
			id = "raceTowedDefaulted03MENU";
			break;
		case 3:
			id = "raceTowedDefaulted02MENU";
			break;
		case 4:
			id = "raceTowedDefaulted01MENU";
		}
		long res = menues.createSimpleMenu(new RacePanelMenu(id),
				1000000000.0D, "ESC", 1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
		int journal_message = 0;
		switch (raceid) {
		case 1:
			journal_message = 39;
			break;
		case 2:
			journal_message = 40;
			break;
		case 3:
			journal_message = 41;
			break;
		case 4:
			journal_message = 42;
		}
		JournalFinishWarehouse.createNote(journal_message, racename, logo_name,
				"", "", 0, ratingboost, 0);
		return res;
	}

	public static long PanelTowedDefaultedIn(int raceid, String racename,
			String logo_name, int towrate, int ratingboost, int balance) {
		menu_xml = 0;
		s_raceid = raceid;
		s_controlgroup = "Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
				+ " - TOWED AND DEFAULTED - IN";
		s_ratingboost = ratingboost;
		s_towrate = towrate;
		s_balance = balance;
		String id = "";
		switch (raceid) {
		case 1:
			id = "raceTowedDefaultedIn04MENU";
			break;
		case 2:
			id = "raceTowedDefaultedIn03MENU";
			break;
		case 3:
			id = "raceTowedDefaultedIn02MENU";
			break;
		case 4:
			id = "raceTowedDefaultedIn01MENU";
		}
		long res = menues.createSimpleMenu(new RacePanelMenu(id),
				1000000000.0D, "ESC", 1600, 1200, 1600, 1200, 0, 0,
				"..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
		int journal_message = 0;
		switch (raceid) {
		case 1:
			journal_message = 39;
			break;
		case 2:
			journal_message = 40;
			break;
		case 3:
			journal_message = 41;
			break;
		case 4:
			journal_message = 42;
		}
		JournalFinishWarehouse.createNote(journal_message, racename, logo_name,
				"", "", 0, ratingboost, 0);
		return res;
	}

	public void InitMenu(long _menu) {
		this.common = new Common(_menu);
		s_common = this.common;
		ListenerManager.TriggerEvent(103);
		String xmlname = XMLS[menu_xml] + ".xml";

		menues.InitXml(_menu, xmlname, s_controlgroup);
		s_instance = this;
		if (s_yesno) {
			this.common.SetScriptOnButton("BUTTON - YES", this, "OnYes");
			this.common.SetScriptOnButton("BUTTON - NO", this, "OnNo");
		}
		if (s_bigracewin) {
			this.common.SetScriptOnButton("BUTTON - OK", this, "OnLeave");
		}
		if ((s_hasok) && (!(s_bigracewin))) {
			menues.SetMenuCallBack_ExitMenu(_menu,
					menues.FindFieldInMenu(_menu, "BUTTON - OK"), 4L);
		}

		if (s_race_name != null) {
			this.common.SetTextValue("Race NAME - TITLE", s_race_name);
		}

		if (s_money != -1) {
			if (menues.FindFieldInMenu(_menu, "GRAN PRIX") != 0L) {
				KeyPair[] macro = { new KeyPair("MONEY_GRAN_PRIX",
						Converts.ConvertNumeric(s_money)) };
				MacroKit.ApplyToTextfield(
						this.common.FindTextField("GRAN PRIX"), macro);
			}

			if (menues.FindFieldInMenu(_menu, "Prize Money - VALUE") != 0L) {
				KeyPair[] macro = { new KeyPair("MONEY_GRAND_PRIX",
						Converts.ConvertNumeric(s_money)) };
				MacroKit.ApplyToTextfield(
						this.common.FindTextField("Prize Money - VALUE"), macro);
			}
		}

		if (s_finish != null) {
			KeyPair[] macro = { new KeyPair("FINISH_CITY", s_finish) };
			MacroKit.ApplyToTextfield(this.common.FindTextField("FINISH"),
					macro);
		}

		if (s_ratingreq != -1) {
			KeyPair[] macro = { new KeyPair("PARTICIPATION_RATING", ""
					+ s_ratingreq) };
			MacroKit.ApplyToTextfield(
					this.common.FindTextField("Required Rating"), macro);
		}

		if (s_nextcheckpoint != null) {
			KeyPair[] macro = { new KeyPair("NEXT_CHECK_POINT",
					s_nextcheckpoint) };
			MacroKit.ApplyToTextfield(
					this.common.FindTextField("Next Check Point"), macro);
		}

		if (s_2ndratingreq != -1) {
			KeyPair[] macro = { new KeyPair("PARTICIPATION_RATING", ""
					+ s_ratingreq) };
			MacroKit.ApplyToTextfield(this.common.FindTextField("Your rating"),
					macro);
		}

		if (s_timeallowed != null) {
			Converts.ConvertTimeAllowed(
					this.common.FindTextField("Time allowed"),
					s_timeallowed.hours, s_timeallowed.minutes,
					s_timeallowed.seconds);
		}

		if ((s_money != -1) && (s_rating_prize != -1) && (s_race_id != null)
				&& (loc.getBigraceDescription(s_race_id) != null)) {
			MENUText_field text = this.common.FindTextField("Message");
			if (text != null) {
				KeyPair[] macro = {
						new KeyPair("RATING_GRAND_PRIX", "" + s_rating_prize),
						new KeyPair("MONEY_GRAN_PRIX",
								Converts.ConvertNumeric(s_money)) };
				text.text = MacroKit.Parse(
						loc.getBigraceDescription(s_race_id), macro);
				menues.UpdateField(text);
			}
		}

		if (s_drivername != null) {
			KeyPair[] macro = { new KeyPair("NAME", s_drivername) };
			MacroKit.ApplyToTextfield(this.common.FindTextField("Name"), macro);
		}

		if (s_rating != -1) {
			KeyPair[] macro = { new KeyPair("RATING", "" + s_rating) };
			MacroKit.ApplyToTextfield(this.common.FindTextField("Rating"),
					macro);
		}

		if (s_startposition != -1) {
			KeyPair[] macro = { new KeyPair("START_POSITION", ""
					+ s_startposition) };
			MacroKit.ApplyToTextfield(
					this.common.FindTextField("Your position"), macro);
		}

		if (s_total != null) {
			Converts.ConvertTotalTime(
					this.common.FindTextField("Total time - VALUE"),
					s_total.hours, s_total.minutes, s_total.seconds);
		}

		if (s_topspeed != -1.0D) {
			KeyPair[] macro = { new KeyPair("TOP_SPEED",
					Converts.ConvertDouble(s_topspeed, 2)) };
			MacroKit.ApplyToTextfield(
					this.common.FindTextField("Top Speed - VALUE"), macro);
		}

		if (s_avespeed != -1.0D) {
			KeyPair[] macro = { new KeyPair("AVERAGE_SPEED",
					Converts.ConvertDouble(s_avespeed, 2)) };
			MacroKit.ApplyToTextfield(
					this.common.FindTextField("Average Speed - VALUE"), macro);
		}

		if (s_ratingboost != -1) {
			KeyPair[] macro = {
					new KeyPair("RATING", "" + Math.abs(s_ratingboost)),
					new KeyPair("SIGN", (s_ratingboost > 0) ? "+"
							: (s_ratingboost == 0) ? " " : "-") };
			MacroKit.ApplyToTextfield(
					this.common.FindTextField("Rating - VALUE"), macro);
		}

		if (s_place != -1) {
			this.common.SetTextValue("Place - VALUE", s_place + "");
		}
		if (s_towrate != -1) {
			KeyPair[] macro = {
					new KeyPair(
							"TOW_AWAY_FEE",
							""
									+ Converts
											.ConvertNumeric((s_towrate >= 0) ? s_towrate
													: -s_towrate)),
					new KeyPair("SIGN", (s_towrate >= 0) ? "" : "-") };
			MacroKit.ApplyToTextfield(
					this.common.FindTextField("Tow-away fee - VALUE"), macro);
		}

		if (s_forefit != -1) {
			KeyPair[] macro = {
					new KeyPair(
							"FORFEIT",
							""
									+ Converts
											.ConvertNumeric((s_forefit >= 0) ? s_forefit
													: -s_forefit)),
					new KeyPair("SIGN", (s_forefit >= 0) ? "" : "-") };
			MacroKit.ApplyToTextfield(
					this.common.FindTextField("Forfeit - VALUE"), macro);
		}

		this.balanc_control = menues.FindFieldInMenu(_menu,
				"Your balance - VALUE");
		BalanceUpdater.AddBalanceControl(this.balanc_control);

		if (s_flashingwait) {
			MENUText_field text = this.common
					.FindTextField("START MESSAGE - COUNTER");
			if (text != null) {
				this.m_fadeText = new OfficeTab.FadeAnimationTextField(text,
						1.0D);
				this.m_fadeText.Start();
			}

			text = this.common.FindTextField("START PICTURE - COUNTER");
			if (text != null) {
				this.m_fadeColor = new OfficeTab.FadeAnimation_ControlColor(0,
						1.0D, false);
				this.m_fadeColor.AddControl(text.nativePointer);
				this.m_fadeColor.Start();
			}
		}

		if (s_participants != null) {
			this.m_partstable = new Table(_menu, "Participants");
			this.m_partstable.AddTextField("Driver - VALUE", 0);
			this.m_partstable.AddTextField("Rating - VALUE", 1);
			this.m_partstable.AddTextField("Start Position - VALUE", 2);
			this.m_partstable.AddTextField("Status - READY", 6);
			this.m_partstable.AddTextField("Status - WAITING", 7);
			this.m_partstable.AddTextField("Status - STARTED", 5);

			for (int i = 0; i < s_participants.length; ++i) {
				this.m_partstable.AddItem(null, s_participants[i], false);
			}

			this.m_partstable.Setup(38L, 12, xmlname,
					"Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
							+ " - LIST OF PARTICIPANTS - TABLE",
					"TABLEGROUP - LIST OF PARTICIPANTS - 12 38",
					new PartsCbs(), 8);
		}

		if (s_summary == null)
			return;
		this.m_partstable = new Table(_menu, "Summary");
		this.m_partstable.AddTextField("Driver - VALUE", 0);
		this.m_partstable.AddTextField("Rating - VALUE", 1);
		this.m_partstable.AddTextField("Place - VALUE", 2);
		this.m_partstable.AddTextField("Total Time - VALUE", 4);
		this.m_partstable.AddTextField("Average Speed - VALUE", 3);

		for (int i = 0; i < s_summary.length; ++i) {
			this.m_partstable.AddItem(null, s_summary[i], false);
		}

		this.m_partstable.Setup(38L, 12, xmlname,
				"Panel - Race" + Converts.bigRaceSuffixes(s_raceid)
						+ " - SUMMARY REPORT - TABLE",
				"TABLEGROUP - SUMMARY REPORT - 12 38", new SummaryCbs(), 8);
	}

	public void AfterInitMenu(long _menu) {
		ListenerManager.TriggerEvent(104);
		if (s_hasok)
			menues.setfocuscontrolonmenu(_menu,
					menues.FindFieldInMenu(_menu, "BUTTON - OK"));
		if (s_isfirst != -1) {
			menues.SetShowField(
					this.common.FindTextField("You pass").nativePointer,
					s_isfirst == 0);
			menues.SetShowField(
					this.common.FindTextField("You pass first").nativePointer,
					s_isfirst != 0);
		}

		if (s_fadeevent) {
			this.m_fadingcontrol = this.common.FindTextField("BACK FADE");
			menues.SetControlColor(this.m_fadingcontrol.nativePointer, false,
					0, 16777215);
			menues.SetControlColor(this.m_fadingcontrol.nativePointer, true, 0,
					16777215);
			menues.SetScriptObjectAnimation(0L, s_animid, this, "OnAnim");
		}

		if (s_clockdown != -1) {
			this.m_clockcontrol = this.common
					.FindTextField("START MESSAGE - COUNTER");
			menues.SetScriptObjectAnimation(0L, s_clockid, this, "OnClockdown");
		}
		if (s_hidesemi) {
			menues.SetShowField(
					this.common.FindTextField("Deliver the semitrailer - 01").nativePointer,
					false);
		}
		if (changeinterior)
			NotifyInformation.changeInteriorAfterInit(_menu);
		else if (immediateshow)
			NotifyInformation.leaveWarehouseAfterInit(_menu);
		else {
			NotifyInformation.notifyAfterInit(_menu);
		}
		long race_logo = menues.FindFieldInMenu(_menu, "THE RACE LOGOTYPE");
		if (race_logo != 0L) {
			if (s_race_logo_id != null) {
				menues.SetShowField(race_logo, true);
				IconMappings.remapRaceLogos(s_race_logo_id, race_logo);
			} else {
				menues.SetShowField(race_logo, false);
			}
		}

		long scroller_id = menues.FindFieldInMenu(_menu,
				"WHrace - BIGRUN - Info - QUALIFY - tableranger");
		long scroller_group = menues.FindFieldInMenu(_menu,
				"WHrace - BIGRUN - Info - QUALIFY - tableranger GROUP");
		long text_id = menues.FindFieldInMenu(_menu, "Message");
		if ((text_id != 0L) && (scroller_id != 0L) && (scroller_group != 0L)) {
			MENU_ranger ranger = (MENU_ranger) menues
					.ConvertMenuFields(scroller_id);
			MENUText_field text = (MENUText_field) menues
					.ConvertMenuFields(text_id);
			if ((ranger != null) && (text != null)) {
				int texh = menues.GetTextLineHeight(text.nativePointer);
				int startbase = menues.GetBaseLine(text.nativePointer);
				int linescreen = Converts.HeightToLines(text.leny, startbase,
						texh);
				int linecounter = Converts.HeightToLines(
						menues.GetTextHeight(text.nativePointer, text.text),
						startbase, texh);
				s_scroller = new TextScroller(_menu, ranger, linecounter,
						linescreen, texh, startbase, scroller_group, true,
						"WHrace - BIGRUN - Info - QUALIFY - tableranger GROUP");
				s_scroller.AddTextControl(text);
			}
		}

		changeinterior = false;
		immediateshow = false;
	}

	public void OnAnim(long cookie, double time) {
		double frame = time / 2.0D;
		if (frame > 1.0D) {
			menues.StopScriptAnimation(s_animid);
			if (cookie == 0L)
				JavaEvents.DispatchEvent(11, 0, null);
			else
				menues.CallMenuCallBack_ExitMenu(this.common.GetMenu());
		}
		int alpha = (int) (frame * 255.0D);
		if (cookie == 1L) {
			alpha = 255 - alpha;
		}
		int outcolor = 0xFFFFFF | alpha << 24;
		menues.SetControlColor(this.m_fadingcontrol.nativePointer, false, 0,
				outcolor);
		menues.SetControlColor(this.m_fadingcontrol.nativePointer, true, 0,
				outcolor);
	}

	public void OnClockdown(long cookie, double time) {
		int sec = (int) time;
		if (sec > s_clockdown) {
			s_response = 0;
			menues.StopScriptAnimation(s_clockid);
			menues.CallMenuCallBack_ExitMenu(this.common.GetMenu());
			return;
		}

		this.m_clockcontrol.text = Converts.ConvertNumeric2(s_clockdown - sec);
		menues.UpdateField(this.m_clockcontrol);
	}

	public void exitMenu(long _menu) {
		if (s_clockdown != -1)
			menues.StopScriptAnimation(s_clockid);
		menues.StopScriptAnimation(s_animid);
		ListenerManager.TriggerEvent(105);
		if (this.m_fadeText != null)
			this.m_fadeText.Finish();
		this.m_fadeText = null;

		if (s_scroller != null) {
			s_scroller.Deinit();
		}

		if (this.m_fadeColor != null)
			this.m_fadeColor.Finish();
		this.m_fadeColor = null;

		if (this.m_partstable != null) {
			this.m_partstable.DeInit();
		}

		BalanceUpdater.RemoveBalanceControl(this.balanc_control);
		s_instance = null;
		s_common = null;
		ClearStatic1();
		event.Setevent(9001);
		ClearStatic2();
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void OnLeave(long _menu, MENUsimplebutton_field button) {
		s_response = 3;
		menues.CallMenuCallBack_ExitMenu(_menu);
	}

	public void OnYes(long _menu, MENUsimplebutton_field button) {
		s_response = 1;

		if (s_clockdown != -1) {
			menues.StopScriptAnimation(s_clockid);
		}
		menues.CallMenuCallBack_ExitMenu(_menu);
	}

	public void OnNo(long _menu, MENUsimplebutton_field button) {
		s_response = 2;
		if (s_clockdown != -1)
			menues.StopScriptAnimation(s_clockid);
		menues.CallMenuCallBack_ExitMenu(_menu);
	}

	static void ClearStatic1() {
		s_race_name = null;
		s_race_id = null;
		s_controlgroup = null;
		s_raceid = 0;
		s_money = -1;
		s_finish = null;
		s_rating_prize = -1;
		s_ratingreq = -1;
		s_nextcheckpoint = null;
		s_timeallowed = null;
		s_message = null;
		s_drivername = null;
		s_rating = -1;
		s_startposition = -1;
		s_isfirst = -1;
		s_total = null;
		s_topspeed = -1.0D;
		s_avespeed = -1.0D;
		s_ratingboost = -1;
		s_hasok = false;
		s_medal = -1;
		s_place = -1;
		s_flashingwait = false;
		s_2ndratingreq = -1;
		s_yesno = false;
		s_towrate = -1;
		s_balance = -1;
		s_forefit = -1;
		s_fadeevent = false;
		s_clockdown = -1;
		s_participants = null;
		s_summary = null;
		s_hidesemi = false;
		s_race_logo_id = null;
		s_scroller = null;
	}

	static void ClearStatic2() {
		s_response = 0;
		s_bigracewin = false;
		pr_menu_win = 0L;
		s_scroller = null;
	}

	class SummaryCbs implements TableCallbacks {
		String Total_Time_VALUE_MACROS;
		String Average_Speed_VALUE_MACROS;
		String Rating_VALUE_MACROS;

		SummaryCbs() {
			this.Total_Time_VALUE_MACROS = null;
			this.Average_Speed_VALUE_MACROS = null;
			this.Rating_VALUE_MACROS = null;
		}

		public void SetupLineInTable(TableNode node, MENUText_field text) {
			RacePanelMenu.SummaryInfo info = (RacePanelMenu.SummaryInfo) node.item;
			if (text.userid == 0) {
				text.text = info.name;
			} else if (text.userid == 2) {
				int pos = info.position + 1;
				text.text = "" + pos;
			} else if (text.userid == 1) {
				if (this.Rating_VALUE_MACROS == null) {
					this.Rating_VALUE_MACROS = text.text;
				}
				KeyPair[] macro = {
						new KeyPair("RATING", "" + Math.abs(info.rating)),
						new KeyPair("SIGN", (info.rating > 0) ? "+"
								: (info.rating == 0) ? " " : "-") };
				text.text = MacroKit.Parse(this.Rating_VALUE_MACROS, macro);
			} else if (text.userid == 3) {
				if (this.Average_Speed_VALUE_MACROS == null) {
					this.Average_Speed_VALUE_MACROS = text.text;
				}
				KeyPair[] macro = { new KeyPair("AVERAGE_SPEED",
						Converts.ConvertDouble(info.speed, 0)) };
				text.text = MacroKit.Parse(this.Average_Speed_VALUE_MACROS,
						macro);
			} else if (text.userid == 4) {
				if (this.Total_Time_VALUE_MACROS == null) {
					this.Total_Time_VALUE_MACROS = text.text;
				}

				text.text = Converts.ConverTime3Plus2Total(
						this.Total_Time_VALUE_MACROS, info.time.hours,
						info.time.minutes, info.time.seconds);
			}
		}

		public void SetupLineInTable(TableNode node,
				MENUsimplebutton_field button) {
		}

		public void SetupLineInTable(TableNode node, MENUbutton_field radio) {
		}

		public void SetupLineInTable(int type, TableNode node, Object control) {
		}

		public void OnEvent(long event, TableNode node, long group, long _menu) {
		}
	}

	class PartsCbs implements TableCallbacks {
		String Rating_VALUE_MACROS;

		PartsCbs() {
			this.Rating_VALUE_MACROS = null;
		}

		public void SetupLineInTable(TableNode node, MENUText_field text) {
			RacePanelMenu.ParticipantInfo info = (RacePanelMenu.ParticipantInfo) node.item;
			switch (text.userid) {
			case 0:
				text.text = info.name;
				break;
			case 2:
				text.text = (info.position + 1) + "";
				break;
			case 1:
				if (this.Rating_VALUE_MACROS == null) {
					this.Rating_VALUE_MACROS = text.text;
				}
				KeyPair[] macro = {
						new KeyPair("RATING", ""
								+ ((info.rating >= 0) ? info.rating
										: -info.rating)),
						new KeyPair("SIGN", (info.rating >= 0) ? "" : "-") };
				text.text = MacroKit.Parse(this.Rating_VALUE_MACROS, macro);

				break;
			case 6:
				menues.SetShowField(text.nativePointer, info.state == 1);
				break;
			case 7:
				menues.SetShowField(text.nativePointer, info.state == 2);
				break;
			case 5:
				menues.SetShowField(text.nativePointer, info.state == 0);
			case 3:
			case 4:
			}
		}

		public void SetupLineInTable(TableNode node,
				MENUsimplebutton_field button) {
		}

		public void SetupLineInTable(TableNode node, MENUbutton_field radio) {
		}

		public void SetupLineInTable(int type, TableNode node, Object control) {
		}

		public void OnEvent(long event, TableNode node, long group, long _menu) {
		}
	}

	public static class SummaryInfo {
		String name;
		TimeData time;
		double speed;
		int position;
		int rating;

		public SummaryInfo() {
		}

		public SummaryInfo(String name, int rating, int position,
				TimeData time, double speed) {
			this.name = name;
			this.rating = rating;
			this.position = position;
			this.time = time;
			this.speed = speed;
		}
	}

	public static class ParticipantInfo {
		String name;
		int rating;
		int position;
		int state;

		public ParticipantInfo() {
		}

		public ParticipantInfo(String name, int rating, int position, int state) {
			this.name = name;
			this.rating = rating;
			this.position = position;
			this.state = state;
		}
	}
}