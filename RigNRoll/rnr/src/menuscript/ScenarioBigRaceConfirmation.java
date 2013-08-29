/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

import menu.Common;
import menu.DateData;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.TextScroller;
import menu.menucreation;
import menu.menues;
import rnrcore.CoreTime;
import rnrcore.loc;
import scriptEvents.EventsControllerHelper;

public class ScenarioBigRaceConfirmation implements menucreation {
	private static final String ok_button = "ACCEPT MONSTER CUP - OK";
	private static final String cancel_button = "ACCEPT MONSTER CUP - CANCEL";
	private static final String method_ok = "onOk";
	private static final String method_cancel = "onCancel";
	private static final String msg_ok = "Scenario Answer Ok";
	private static final String msg_cancel = "Scenario Answer Cancel";
	public static final String msg_menu_closed = "Scenario Answer Recived";
	private static boolean result = false;
	private String start;
	private CoreTime start_date;
	private String finish;
	private DateData finish_date;
	private int winmoney;
	TextScroller scroller;
	private static ScenarioBigRaceConfirmation m_lastMenu = null;
	private boolean f_buttonPressed;

	public ScenarioBigRaceConfirmation() {
		this.scroller = null;

		this.f_buttonPressed = false;
	}

	public void restartMenu(long _menu) {
	}

	public static final ScenarioBigRaceConfirmation getLastMenu() {
		return m_lastMenu;
	}

	public static final boolean gResult() {
		return result;
	}

	public static final long createScenarioBigRaceConfirmationMenu(
			String start, CoreTime start_date, String finish,
			DateData finish_date, int winmoney) {
		m_lastMenu = new ScenarioBigRaceConfirmation();
		return m_lastMenu._createScenarioBigRaceConfirmationMenu(start,
				start_date, finish, finish_date, winmoney);
	}

	private final long _createScenarioBigRaceConfirmationMenu(String _start,
			CoreTime _start_date, String _finish, DateData _finish_date,
			int _winmoney) {
		this.start = _start;
		this.start_date = _start_date;
		this.finish = _finish;
		this.finish_date = _finish_date;
		this.winmoney = _winmoney;
		return menues.createSimpleMenu(this, 1000000000.0D, "", 1600, 1200,
				1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0,
				0);
	}

	public void InitMenu(long _menu) {
		menues.InitXml(_menu, "..\\data\\config\\menu\\menu_documents.xml",
				"DOCUMENT - WELCOME TO MONSTER CUP");
		long okButton = menues
				.FindFieldInMenu(_menu, "ACCEPT MONSTER CUP - OK");
		long cancelButton = menues.FindFieldInMenu(_menu,
				"ACCEPT MONSTER CUP - CANCEL");
		menues.SetScriptOnControl(_menu, menues.ConvertSimpleButton(okButton),
				this, "onOk", 4L);
		menues.SetScriptOnControl(_menu,
				menues.ConvertSimpleButton(cancelButton), this, "onCancel", 4L);
	}

	public void onOk(long _menu, MENUsimplebutton_field button) {
		this.f_buttonPressed = true;
		result = true;
		EventsControllerHelper.messageEventHappened("Scenario Answer Ok");
		EventsControllerHelper.messageEventHappened("Scenario Answer Recived");
		menues.CallMenuCallBack_ExitMenu(_menu);
	}

	public void onCancel(long _menu, MENUsimplebutton_field button) {
		this.f_buttonPressed = true;
		result = false;
		EventsControllerHelper.messageEventHappened("Scenario Answer Cancel");
		EventsControllerHelper.messageEventHappened("Scenario Answer Recived");
		menues.CallMenuCallBack_ExitMenu(_menu);
	}

	public void AfterInitMenu(long _menu) {
		Common common = new Common(_menu);

		MENU_ranger ranger = (MENU_ranger) menues.ConvertMenuFields(menues
				.FindFieldInMenu(_menu, "Tableranger - Newspaper"));
		MENUText_field text = (MENUText_field) menues.ConvertMenuFields(menues
				.FindFieldInMenu(_menu, "MONSTER CUP - TEXT"));

		KeyPair[] pair1 = { new KeyPair("FULL_DATE1",
				loc.getDateString("FULL_DATE")) };
		String source1 = MacroKit.Parse(text.text, pair1);
		KeyPair[] pairs1 = new KeyPair[8];
		pairs1[0] = new KeyPair("YEAR", "" + this.start_date.gYear());
		pairs1[1] = new KeyPair("MONTH", Converts.makeClock(this.start_date
				.gMonth()));
		pairs1[2] = new KeyPair("DATE", Converts.makeClock(this.start_date
				.gDate()));
		pairs1[3] = new KeyPair("HOURS", Converts.makeClock(this.start_date
				.gHour()));
		pairs1[4] = new KeyPair("MINUTES", Converts.makeClock(this.start_date
				.gMinute()));
		pairs1[5] = new KeyPair("SOURCE", this.start);
		pairs1[6] = new KeyPair("DESTINATION", this.finish);
		pairs1[7] = new KeyPair("MONEY", "" + this.winmoney);
		String source2 = MacroKit.Parse(source1, pairs1);
		KeyPair[] pair2 = { new KeyPair("FULL_DATE2",
				loc.getDateString("FULL_DATE")) };
		String source3 = MacroKit.Parse(source2, pair2);
		KeyPair[] pairs2 = new KeyPair[3];
		pairs2[0] = new KeyPair("YEAR", "" + this.finish_date.year);
		pairs2[1] = new KeyPair("MONTH",
				Converts.makeClock(this.finish_date.month));
		pairs2[2] = new KeyPair("DATE",
				Converts.makeClock(this.finish_date.day));
		String final_text = MacroKit.Parse(source3, pairs2);
		text.text = final_text;
		menues.UpdateField(text);
		int texh = menues.GetTextLineHeight(text.nativePointer);
		int startbase = menues.GetBaseLine(text.nativePointer);
		int linescreen = Converts.HeightToLines(text.leny, startbase, texh);
		int linecounter = Converts.HeightToLines(
				menues.GetTextHeight(text.nativePointer, final_text),
				startbase, texh);
		this.scroller = new TextScroller(common, ranger, linecounter,
				linescreen, texh, startbase, false,
				"DOCUMENT - WELCOME TO MONSTER CUP - ARTICLE");
		this.scroller.AddTextControl(text);

		menues.WindowSet_ShowCursor(_menu, true);
		menues.SetStopWorld(_menu, true);
		menues.SetMenagPOLOSY(_menu, false);
		menues.setShowMenu(_menu, true);
	}

	public void exitMenu(long _menu) {
		m_lastMenu = null;
		if (this.scroller != null) {
			this.scroller.Deinit();
		}

		if (!(this.f_buttonPressed)) {
			result = false;
			EventsControllerHelper
					.messageEventHappened("Scenario Answer Cancel");
			EventsControllerHelper
					.messageEventHappened("Scenario Answer Recived");
		}
	}

	public String getMenuId() {
		return "scenarioBigRaceConfirmationMENU";
	}
}