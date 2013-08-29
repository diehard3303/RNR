/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

import menu.Helper;
import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.MenuAfterInitNarrator;
import menu.menucreation;
import menu.menues;
import rnrcore.eng;

public class RoadService implements menucreation {
	private static final int RESULT_PAY = 1;
	private static final int RESULT_CANCEL = 2;
	private static final String XML = "..\\data\\config\\menu\\menu_roadservice.xml";
	private static final String GROUP = "Road Service BILL";
	private static final String[] ACTION_BUTTON = { "BUTTON - PAY",
			"BUTTON - CANCEL" };
	private static final String[] ACTION_METHOD = { "onPay", "onCancel" };
	private static final int TOWING = 0;
	private static final int REPAIRING = 1;
	private static final int REFUELING = 2;
	private static final int TOTAL = 3;
	private static final int BALANCE = 4;
	private static final int SIZE = 5;
	private int[] values = new int[5];
	private String[] CONTROLS = { "Road Service BILL - Towing - VALUE",
			"Road Service BILL - Repairing - VALUE",
			"Road Service BILL - Refueling - VALUE",
			"Road Service BILL - Total - VALUE",
			"Road Service BILL - Balance VALUE" };

	private int result = 2;

	public void restartMenu(long _menu) {
	}

	public static long create(int tow_value, int repair_value,
			int refuel_value, int total_value, int balance_value) {
		RoadService menu = new RoadService();
		for (int i = 0; i < menu.values.length; ++i) {
			switch (i) {
			case 0:
				menu.values[i] = tow_value;
				break;
			case 1:
				menu.values[i] = repair_value;
				break;
			case 2:
				menu.values[i] = refuel_value;
				break;
			case 3:
				menu.values[i] = total_value;
				break;
			case 4:
				menu.values[i] = balance_value;
			}
		}
		return menues.createSimpleMenu(menu);
	}

	public void InitMenu(long _menu) {
		menues.InitXml(_menu, "..\\data\\config\\menu\\menu_roadservice.xml",
				"Road Service BILL");
		for (int i = 0; i < this.CONTROLS.length; ++i) {
			long control = menues.FindFieldInMenu(_menu, this.CONTROLS[i]);
			if (0L != control) {
				String text = menues.GetFieldText(control);
				KeyPair[] pair = {
						new KeyPair("SIGN", (this.values[i] >= 0) ? "" : "-"),
						new KeyPair("MONEY",
								Helper.convertMoney(this.values[i])) };
				menues.SetFieldText(control, MacroKit.Parse(text, pair));
			}
		}
		for (int i = 0; i < ACTION_BUTTON.length; ++i) {
			long button = menues.FindFieldInMenu(_menu, ACTION_BUTTON[i]);
			menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button),
					this, ACTION_METHOD[i], 4L);
		}
	}

	public void AfterInitMenu(long _menu) {
		MenuAfterInitNarrator.justShowAndStop(_menu);
	}

	public void exitMenu(long _menu) {
		if (!(eng.noNative))
			JavaEvents.SendEvent(6, this.result, this);
	}

	public String getMenuId() {
		return "roadserviceMENU";
	}

	public void onPay(long _menu, MENUsimplebutton_field button) {
		this.result = 1;
		menues.CallMenuCallBack_ExitMenu(_menu);
	}

	public void onCancel(long _menu, MENUsimplebutton_field button) {
		this.result = 2;
		menues.CallMenuCallBack_ExitMenu(_menu);
	}
}