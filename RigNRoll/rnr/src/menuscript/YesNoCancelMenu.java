/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

import java.util.ArrayList;
import java.util.Iterator;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.menues;
import rnrcore.Log;

public class YesNoCancelMenu {
	protected static int NUMELEM = 0;
	protected static final String NAMEUNIQSUFF = "SmartGroupTop";
	private static final String[] BUTTONS = { "YES", "NO", "CANCEL" };
	private static final String[] METHODS = { "OnYes", "OnNo", "OnCancel" };
	private static final String EXITMETH = "OnExit";
	private long[] controls = null;
	ArrayList<IYesNoCancelMenuListener> listeners = new ArrayList();

	public YesNoCancelMenu() {
	}

	public YesNoCancelMenu(long _menu, String xmlFile, String controlGroup,
			String windowName) {
		this.controls = menues.InitXml(_menu, xmlFile, controlGroup);
		if (null == this.controls) {
			Log.menu("ERRORR. Cannot create PoPUpMenu. File " + xmlFile
					+ " has no group named " + controlGroup);
			return;
		}
		if (BUTTONS.length != METHODS.length) {
			Log.menu("Bab Class PoPUpMenu. BUTTONS.length!=METHODS.length");
			return;
		}
		for (int i = 0; i < BUTTONS.length; ++i) {
			long exitButton = findControlNamed(BUTTONS[i]);
			if (0L != exitButton) {
				Object field = menues.ConvertMenuFields(exitButton);
				menues.SetScriptOnControl(_menu, field, this, METHODS[i], 4L);
			}
		}
		if (windowName != null) {
			long wnd = findControlNamed(windowName);
			if (0L != wnd) {
				SMenu _wnd = menues.ConvertWindow(wnd);
				menues.SetScriptOnControl(_menu, _wnd, this, "OnExit", 17L);
			} else {
				Log.menu("PoPUpMenu constructor. There is no window named "
						+ windowName + " for controlgroup " + controlGroup);
			}
		}
		String parentName = controlGroup + "SmartGroupTop" + (NUMELEM++);
		menues.SetFieldName(_menu, this.controls[0], parentName);
		for (int i = 1; i < this.controls.length; ++i)
			menues.SetFieldParentName(this.controls[i], parentName);
	}

	public void afterInit() {
		setShow(false);
	}

	public void OnYes(long _menu, MENUsimplebutton_field field) {
		setShow(false);
		Iterator iter = this.listeners.iterator();
		while (iter.hasNext())
			((IYesNoCancelMenuListener) iter.next()).onYesClose();
	}

	public void OnNo(long _menu, MENUsimplebutton_field field) {
		setShow(false);
		Iterator iter = this.listeners.iterator();
		while (iter.hasNext())
			((IYesNoCancelMenuListener) iter.next()).onNoClose();
	}

	public void OnCancel(long _menu, MENUsimplebutton_field field) {
		setShow(false);
		Iterator iter = this.listeners.iterator();
		while (iter.hasNext())
			((IYesNoCancelMenuListener) iter.next()).onCancelClose();
	}

	private void setShow(boolean value) {
		if (null == this.controls)
			return;
		if (value)
			for (int i = 0; i < this.controls.length; ++i)
				menues.SetShowField(this.controls[i], true);
		else
			for (int i = 0; i < this.controls.length; ++i)
				menues.SetShowField(this.controls[i], false);
	}

	public void show() {
		setShow(true);
		Iterator iter = this.listeners.iterator();
		while (iter.hasNext())
			((IYesNoCancelMenuListener) iter.next()).onOpen();
	}

	public void hide() {
		setShow(false);
		Iterator iter = this.listeners.iterator();
		while (iter.hasNext())
			((IYesNoCancelMenuListener) iter.next()).onClose();
	}

	public void addListener(IYesNoCancelMenuListener lst) {
		this.listeners.add(lst);
	}

	public void callonYesClose() {
		Iterator iter = this.listeners.iterator();
		while (iter.hasNext())
			((IYesNoCancelMenuListener) iter.next()).onYesClose();
	}

	public long getField(String name) {
		return findControlNamed(name);
	}

	private long findControlNamed(String name) {
		if (null == this.controls)
			return 0L;
		long res = 0L;
		for (int i = 0; i < this.controls.length; ++i) {
			if (menues.GetFieldName(this.controls[i]).compareTo(name) == 0) {
				res = this.controls[i];
				break;
			}
		}
		return res;
	}

	boolean is_bad() {
		return (null == this.controls);
	}
}