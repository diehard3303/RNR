/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

import java.util.ArrayList;
import java.util.Iterator;
import menu.MENUsimplebutton_field;
import menu.SMenu;
import menu.menues;
import rnrcore.Log;

public class PoPUpMenu {
	protected static int NUMELEM = 0;
	protected static final String NAMEUNIQSUFF = "SmartGroupTop";
	private static final String[] _BUTTONS = { "CANCEL", "OK" };
	private static final String[] _METHODS = { "OnCancel", "OnOk" };
	private static final String EXITMETH = "OnExit";
	private boolean unique = true;
	private boolean bFindWindow = true;
	private long[] controls = null;
	ArrayList<IPoPUpMenuListener> listeners = new ArrayList();

	private int origin_x = 0;
	private int origin_y = 0;

	public PoPUpMenu(long _menu, String xmlFile, String controlGroup,
			String windowName, boolean unique) {
		this.unique = unique;
		init(_menu, xmlFile, controlGroup, windowName, _BUTTONS, _METHODS);
	}

	public PoPUpMenu(long _menu, String xmlFile, String controlGroup) {
		this.unique = false;
		this.bFindWindow = false;
		init(_menu, xmlFile, controlGroup, null, _BUTTONS, _METHODS);
	}

	public PoPUpMenu(long _menu, String xmlFile, String controlGroup,
			String windowName) {
		init(_menu, xmlFile, controlGroup, windowName, _BUTTONS, _METHODS);
	}

	public PoPUpMenu(long _menu, String xmlFile, String controlGroup,
			String windowName, String[] BUTTONS, String[] METHODS) {
		init(_menu, xmlFile, controlGroup, windowName, BUTTONS, METHODS);
	}

	public PoPUpMenu(long _menu, String xmlFile, String controlGroup,
			String windowName, String[] BUTTONS, String[] METHODS,
			boolean unique) {
		this.unique = unique;
		init(_menu, xmlFile, controlGroup, windowName, BUTTONS, METHODS);
	}

	private void init(long _menu, String xmlFile, String controlGroup,
			String windowName, String[] BUTTONS, String[] METHODS) {
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
		if (this.unique) {
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
			for (int i = 1; i < this.controls.length; ++i) {
				menues.SetFieldParentName(this.controls[i], parentName);
			}
		}

		if ((this.bFindWindow) && (this.controls != null)
				&& (this.controls.length > 0) && (this.controls[0] != 0L)) {
			SMenu __menu = menues.ConvertWindow(this.controls[0]);
			this.origin_y = __menu.poy;
			this.origin_x = __menu.pox;
		}
	}

	public void afterInit() {
		setShow(false);
	}

	public void OnCancel(long _menu, MENUsimplebutton_field field) {
		setShow(false);
		Iterator iter = this.listeners.iterator();
		while (iter.hasNext()) {
			IPoPUpMenuListener obj = (IPoPUpMenuListener) iter.next();
			if (obj != null) {
				obj.onCancel();
				obj.onClose();
			}
		}
	}

	public void OnExit(long _menu, SMenu field) {
		setShow(false);
		Iterator iter = this.listeners.iterator();
		while (iter.hasNext())
			((IPoPUpMenuListener) iter.next()).onClose();
	}

	public void OnOk(long _menu, MENUsimplebutton_field field) {
		setShow(false);
		Iterator iter = this.listeners.iterator();
		while (iter.hasNext())
			((IPoPUpMenuListener) iter.next()).onAgreeclose();
	}

	private void setShow(boolean value) {
		if (null == this.controls)
			return;
		if (value) {
			for (int i = 0; i < this.controls.length; ++i)
				menues.SetShowField(this.controls[i], true);
		} else
			for (int i = 0; i < this.controls.length; ++i)
				menues.SetShowField(this.controls[i], false);
	}

	public void show() {
		setShow(true);
		Iterator iter = this.listeners.iterator();
		while (iter.hasNext())
			((IPoPUpMenuListener) iter.next()).onOpen();
	}

	public void hide() {
		setShow(false);
		Iterator iter = this.listeners.iterator();
		while (iter.hasNext())
			((IPoPUpMenuListener) iter.next()).onClose();
	}

	public void addListener(IPoPUpMenuListener lst) {
		this.listeners.add(lst);
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

	public boolean is_bad() {
		return (null == this.controls);
	}

	public void MoveByFromOrigin(int shiftX, int shiftY) {
		SMenu _menu = menues.ConvertWindow(this.controls[0]);
		_menu.pox = (this.origin_x + shiftX);
		_menu.poy = (this.origin_y + shiftY);
		menues.UpdateField(_menu);
	}
}