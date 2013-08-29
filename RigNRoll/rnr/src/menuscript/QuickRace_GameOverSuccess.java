/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.menucreation;
import menu.menues;
import rnrcore.loc;

public class QuickRace_GameOverSuccess implements menucreation {
	private static final String XML = "..\\data\\config\\menu\\menu_msg_gameover.xml";
	private static final String MENU_GROUP = "Message GAMEOVER INSTANTORDER - EXECUTED";
	private static final String restart_button = "BUTTON - GAMEOVER INSTANTORDER - EXECUTED - RESTART";
	private static final String main_menu_button = "BUTTON - GAMEOVER INSTANTORDER - EXECUTED - MAIN MENU";
	private static final String methodRestart = "onRestart";
	private static final String methodExit2MainMenu = "onExit";
	private static final String[] results_values = {
			"GameoverINSTANTORDER - EXECUTED - PROFIT - VALUE",
			"GameoverINSTANTORDER - EXECUTED - AVERAGE SPEED - VALUE",
			"GameoverINSTANTORDER - EXECUTED - CAREFULNESS - VALUE" };

	private static final String[] loc_titles = {
			"menu_msg_gameover.xml\\Message GAMEOVER INSTANTORDER - EXECUTED\\Message GAMEOVER INSTANTORDER - EXECUTED - BACK\\GameoverINSTANTORDER - EXECUTED - BACK 02\\GameoverINSTANTORDER - TEXT - EXECUTED",
			"menu_msg_gameover.xml\\Message GAMEOVER INSTANTORDER - EXECUTED\\Message GAMEOVER INSTANTORDER - EXECUTED - BACK\\GameoverINSTANTORDER - EXECUTED - RESULTS\\GameoverINSTANTORDER - EXECUTED - RESULTS - TITLE",
			"menu_msg_gameover.xml\\Message GAMEOVER INSTANTORDER - EXECUTED\\Message GAMEOVER INSTANTORDER - EXECUTED - BACK\\GameoverINSTANTORDER - EXECUTED - RESULTS\\GameoverINSTANTORDER - EXECUTED - PROFIT - TITLE",
			"common\\Average Speed :",
			"menu_msg_gameover.xml\\Message GAMEOVER INSTANTORDER - EXECUTED\\Message GAMEOVER INSTANTORDER - EXECUTED - BACK\\GameoverINSTANTORDER - EXECUTED - RESULTS\\GameoverINSTANTORDER - EXECUTED - CAREFULNESS - TITLE" };

	Results in_results = new Results();
	public static final int ACTION_RESTART = 0;
	public static final int ACTION_QUIT_TO_MAIN_MENU = 1;
	private static int out_action = 0;

	public void restartMenu(long _menu) {
	}

	public void InitMenu(long _menu) {
		menues.InitXml(_menu, "..\\data\\config\\menu\\menu_msg_gameover.xml",
				"Message GAMEOVER INSTANTORDER - EXECUTED");

		long _restart_button = menues.FindFieldInMenu(_menu,
				"BUTTON - GAMEOVER INSTANTORDER - EXECUTED - RESTART");
		long _main_menu_button = menues.FindFieldInMenu(_menu,
				"BUTTON - GAMEOVER INSTANTORDER - EXECUTED - MAIN MENU");

		menues.SetScriptOnControl(_menu,
				menues.ConvertSimpleButton(_restart_button), this, "onRestart",
				4L);
		menues.SetScriptOnControl(_menu,
				menues.ConvertSimpleButton(_main_menu_button), this, "onExit",
				4L);
	}

	public void AfterInitMenu(long _menu) {
		menues.WindowSet_ShowCursor(_menu, true);
		menues.SetStopWorld(_menu, true);
		menues.setShowMenu(_menu, true);

		for (String name : loc_titles) {
			menues.SetFieldText(menues.FindFieldInMenu(_menu, name),
					loc.getMENUString(name));
		}

		JavaEvents.SendEvent(65, 12, this.in_results);

		KeyPair[] pairs = new KeyPair[2];
		pairs[0] = new KeyPair("SIGN", ""
				+ ((this.in_results.money >= 0) ? "" : "-"));
		pairs[1] = new KeyPair("MONEY", "" + Math.abs(this.in_results.money));
		menues.SetFieldText(menues.FindFieldInMenu(_menu, results_values[0]),
				MacroKit.Parse(menues.GetFieldText(menues.FindFieldInMenu(
						_menu, results_values[0])), pairs));
		menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(
				_menu, results_values[0])));

		KeyPair[] pairs = new KeyPair[1];
		pairs[0] = new KeyPair("VALUE", "" + this.in_results.average_speed);
		menues.SetFieldText(menues.FindFieldInMenu(_menu, results_values[1]),
				MacroKit.Parse(menues.GetFieldText(menues.FindFieldInMenu(
						_menu, results_values[1])), pairs));
		menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(
				_menu, results_values[1])));

		menues.SetFieldText(menues.FindFieldInMenu(_menu, results_values[2]),
				"" + this.in_results.carefulness);
		menues.UpdateMenuField(menues.ConvertMenuFields(menues.FindFieldInMenu(
				_menu, results_values[2])));
	}

	public void exitMenu(long _menu) {
	}

	public static final int GetAction() {
		return out_action;
	}

	public void onRestart(long _menu, MENUsimplebutton_field button) {
		out_action = 0;
		menues.CallMenuCallBack_ExitMenu(_menu);
		JavaEvents.SendEvent(65, 18, null);
	}

	public void onExit(long _menu, MENUsimplebutton_field button) {
		out_action = 1;
		menues.CallMenuCallBack_ExitMenu(_menu);
		JavaEvents.SendEvent(65, 18, null);
	}

	public String getMenuId() {
		return "gameoverInstantOrderExecutedMENU";
	}

	public static long Create() {
		return menues.createSimpleMenu(new QuickRace_GameOverSuccess());
	}

	static class Results {
		int money;
		int average_speed;
		int carefulness;

		Results() {
			this.money = 0;
			this.average_speed = 0;
			this.carefulness = 0;
		}
	}
}