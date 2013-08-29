/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript.cbvideo;

import java.util.ArrayList;
import menu.IWheelEnabled;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MenuAfterInitNarrator;
import menu.MenuControls;
import menu.menucreation;
import menu.menues;
import players.IcontaktCB;

public class MenuCallFullDialog extends IWheelEnabled implements menucreation {
	private static final String FILE = "..\\data\\config\\menu\\menu_com.xml";
	private static final String GROUP = "CB RADIO - DIALOG PAUSED";
	private static final String CONTROL_CONTACTNAME = "Contact NAME";
	private static final String[] CONTROLS_TEXT_RESIZE = {
			"CB Dialog Tableranger BACK - RESIZE Step160px",
			"CB Dialog Tableranger BORDER - RESIZE Step160px",
			"CB Dialog BACK - RESIZE Step160px" };

	private static final String[] CONTROLS_RANGER_RESIZE = { "CB Dialog Tableranger - RESIZE Step160px" };
	private static final String CONTROL_RANGER = CONTROLS_RANGER_RESIZE[0];
	private static final String RANGER_METHOD = "onRanger";
	private long _menu = 0L;
	private long[] text_resize = new long[CONTROLS_TEXT_RESIZE.length];
	private long[] ranger_resize = new long[CONTROLS_RANGER_RESIZE.length];
	private int[] initial_text_sizes = new int[CONTROLS_TEXT_RESIZE.length];
	private int[] initial_ranger_sizes = new int[CONTROLS_RANGER_RESIZE.length];
	private int current_on_top = 0;
	private static final int maxBoxes = 7;
	private static final int shift = 160;
	private MenuControls controls = null;
	private boolean bAnswered = false;
	private int iAnswerNum = 0;

	ArrayList<Dialogitem> full_dialog = new ArrayList();
	ArrayList<CBDialogBox> dialogs = new ArrayList();
	MENU_ranger m_ranger = null;

	public void restartMenu(long _menu) {
	}

	public void InitMenu(long _menu) {
		this._menu = _menu;
		this.controls = new MenuControls(_menu,
				"..\\data\\config\\menu\\menu_com.xml",
				"CB RADIO - DIALOG PAUSED");

		if (!(this.full_dialog.isEmpty())) {
			long caller_name = this.controls.findControl("Contact NAME");
			IcontaktCB contact = ((Dialogitem) this.full_dialog.get(0)).contact;
			menues.SetFieldText(caller_name, contact.gFirstName() + " "
					+ contact.gLastName());
		}

		for (Dialogitem item : this.full_dialog) {
			CBDialogBox box = new CBDialogBox(_menu, this);
			box.setItem(item);
			this.dialogs.add(box);
		}
		int size = getBoxexSize();
		for (int i = 0; i < this.text_resize.length; ++i) {
			this.text_resize[i] = this.controls
					.findControl(CONTROLS_TEXT_RESIZE[i]);
			MENUText_field field = menues
					.ConvertTextFields(this.text_resize[i]);
			this.initial_text_sizes[i] = field.leny;
		}
		for (int i = 0; i < this.ranger_resize.length; ++i) {
			this.ranger_resize[i] = this.controls
					.findControl(CONTROLS_RANGER_RESIZE[i]);
			MENU_ranger field = menues.ConvertRanger(this.ranger_resize[i]);
			this.initial_ranger_sizes[i] = field.leny;
		}
		for (int i = 0; i < this.text_resize.length; ++i) {
			MENUText_field field = menues
					.ConvertTextFields(this.text_resize[i]);
			field.leny = (this.initial_text_sizes[i] + 160 * size);
			menues.UpdateField(field);
		}
		for (int i = 0; i < this.ranger_resize.length; ++i) {
			MENU_ranger field = menues.ConvertRanger(this.ranger_resize[i]);
			field.leny = (this.initial_ranger_sizes[i] + 160 * size);
			menues.UpdateField(field);
		}

		MENUText_field field = menues.ConvertTextFields(this.controls
				.getTopControl());
		field.poy -= 160 * size;
		menues.UpdateField(field);

		long ranger = this.controls.findControl(CONTROL_RANGER);
		this.m_ranger = menues.ConvertRanger(ranger);
		this.m_ranger.min_value = 0;
		this.m_ranger.max_value = ((this.dialogs.size() < 7) ? 0 : this.dialogs
				.size() - 7);
		menues.UpdateField(this.m_ranger);
		menues.SetScriptOnControl(_menu, menues.ConvertRanger(ranger), this,
				"onRanger", 1L);

		wheelInit(_menu, "CB Dialog BACK - RESIZE Step160px");
	}

	public void AfterInitMenu(long _menu) {
		for (CBDialogBox box : this.dialogs) {
			box.AfterInitMenu();
		}
		redistributeDialogBoxes();
		MenuAfterInitNarrator.justShowAndStop(_menu);
	}

	public void exitMenu(long _menu) {
		wheelDeinit();
		for (CBDialogBox box : this.dialogs)
			box.exitMenu();
	}

	public String getMenuId() {
		return "cbvfulldialogMENU";
	}

	public void onRanger(long _menu, MENU_ranger ranger) {
		if (ranger.current_value == this.current_on_top)
			return;
		this.current_on_top = ranger.current_value;
		redistributeDialogBoxes();
	}

	private void redistributeDialogBoxes() {
		int size = getBoxexSize();
		int iter = 0;
		for (CBDialogBox box : this.dialogs) {
			if (this.current_on_top > iter) {
				box.hide();
			} else {
				int position = size - iter + this.current_on_top;
				if (position < 0) {
					box.hide();
				} else {
					box.show();
					box.move_y(-160 * position);
				}
			}
			++iter;
		}
	}

	private int getBoxexSize() {
		int size = this.dialogs.size() - 1;
		if (size >= 7) {
			size = 6;
		}
		return size;
	}

	public void CloseDilaogAnswer1() {
		this.bAnswered = true;
		this.iAnswerNum = 0;
	}

	public void CloseDilaogAnswer2() {
		this.bAnswered = true;
		this.iAnswerNum = 1;
	}

	public boolean IsAnswered() {
		return this.bAnswered;
	}

	public int GetAnswerNum() {
		return this.iAnswerNum;
	}

	public static MenuCallFullDialog create(ArrayList<Dialogitem> full_dialog) {
		MenuCallFullDialog dlg = new MenuCallFullDialog();
		dlg.full_dialog = full_dialog;
		dlg._menu = menues.createSimpleMenu(dlg, 3);
		return dlg;
	}

	public void close() {
		menues.CallMenuCallBack_ExitMenu(this._menu);
	}

	public long getMenuDescriptor() {
		return this._menu;
	}

	public void ControlsMouseWheel(int value) {
		if (this.m_ranger != null) {
			this.m_ranger.current_value -= value;
			if (this.m_ranger.current_value > this.m_ranger.max_value) {
				this.m_ranger.current_value = this.m_ranger.max_value;
			}

			if (this.m_ranger.current_value < this.m_ranger.min_value) {
				this.m_ranger.current_value = this.m_ranger.min_value;
			}

			menues.UpdateField(this.m_ranger);
			if (this.m_ranger.current_value == this.current_on_top)
				return;
			this.current_on_top = this.m_ranger.current_value;
			redistributeDialogBoxes();
		}
	}

	public void cbEnterFocus() {
	}

	public void cbLeaveFocus() {
	}

	public void ControlsCtrlAPressed() {
	}
}