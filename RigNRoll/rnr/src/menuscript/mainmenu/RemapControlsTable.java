/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package rnr.src.menuscript.mainmenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import rnr.src.menu.ContainerTextTitleTextValue;
import rnr.src.menu.JavaEvents;
import rnr.src.menu.TableNode;
import rnr.src.menu.TableTextAndRadio;
import rnr.src.menu.menues;
import rnr.src.menuscript.IPoPUpMenuListener;
import rnr.src.menuscript.PoPUpMenu;
import rnr.src.menuscript.keybind.Action;
import rnr.src.menuscript.keybind.Axe;
import rnr.src.menuscript.keybind.Bind;
import rnr.src.menuscript.keybind.Key;

public class RemapControlsTable
  implements IPoPUpMenuListener
{
  static int st_NUM_TABLES = 2;
  private int NUM_TABLES = st_NUM_TABLES;
  static String[] TABLES = { "TABLEGROUP - SETTINGS - CONTROLS 01 - 7 40", "TABLEGROUP - SETTINGS - CONTROLS 02 - 7 40" };
  private static final String FIELDSGROUP = "Tablegroup - ELEMENTS - 2 Texts";
  private static final String RANGER = "Tableranger - SETTINGS - CONTROLS";
  PoPUpMenu warning = null;

  private RemapTable[] ctable = new RemapTable[???.NUM_TABLES];

  private ArrayList<WrapTextsAndKeycode> map_controls = new ArrayList();

  private WrapTextsAndKeycode editing = null;

  private final long _remap = 0L;
  int m_resultkey;
  int m_resultaxe;
  int m_half_axe;
  int m_close = 0;
  boolean m_full_axe;
  String m_resultstring;
  int m_currentdevice = 0;

  private static final int[] num_axepair_actions = { 1, 2 };

  boolean f_inremaping = false;

  public RemapControlsTable(long _menu, Panel parent)
  {
    this.NUM_TABLES = st_NUM_TABLES;
    JavaEvents.SendEvent(2, 0, this);
    for (int i = 0; i < this.NUM_TABLES; ++i)
      this.ctable[i] = new RemapTable(i, _menu, parent.menu.XML_FILE, "Tablegroup - ELEMENTS - 2 Texts", TABLES[i], "Tableranger - SETTINGS - CONTROLS");
  }

  public void afterInit() {
    for (int i = 0; i < this.NUM_TABLES; ++i)
      this.ctable[i].afterInit(); 
  }

  void fillTable(ArrayList<WrapTextsAndKeycode> controls, int device) {
    this.m_currentdevice = device;
    this.map_controls = controls;
    ArrayList[] list = new ArrayList[this.NUM_TABLES];
    for (int i = 0; i < list.length; ++i) {
      list[i] = new ArrayList();
    }
    int cur_lst = 0;
    for (int i = 0; i < this.map_controls.size(); ++i) {
      list[(cur_lst++)].add(this.map_controls.get(i).texts);
      if (cur_lst >= this.NUM_TABLES) {
        cur_lst = 0;
      }
    }
    for (int i = 1; i < this.NUM_TABLES; ++i) {
      if (list[i].size() < list[0].size()) {
        list[i].add(null);
      }
    }

    for (int i = 0; i < this.NUM_TABLES; ++i)
      this.ctable[i].fillTable(list[i]);
  }

  private void removeBindingNotOne(WrapTextsAndKeycode excluded) {
    int keycode = (excluded.bind.key != null) ? excluded.bind.key.getKeycode() : -1;
    Iterator iter = this.map_controls.iterator();
    while (iter.hasNext()) {
      WrapTextsAndKeycode item = (WrapTextsAndKeycode)iter.next();
      if ((item.bind.key != null) && (!(item.equals(excluded))) && (keycode == item.bind.key.getKeycode())) {
        item.bind.key = null;
        item.bind.axe = null;
        item.texts.cleanValue();
      }
    }
    for (int i = 0; i < this.NUM_TABLES; ++i)
      this.ctable[i].redraw();
  }

  private Vector removeOneAction(WrapTextsAndKeycode excluded, boolean full_axe, boolean bIsInverse)
  {
    Vector ret = new Vector();
    Axe e_axe = excluded.bind.axe;
    Iterator iter = this.map_controls.iterator();
    while (iter.hasNext()) {
      WrapTextsAndKeycode item = (WrapTextsAndKeycode)iter.next();
      if ((item.bind.axe != null) && (!(item.equals(excluded))) && (e_axe.getAxecode() == item.bind.axe.getAxecode()) && ((
        (full_axe) || (item.bind.axe.isFullAxe()) || (item.bind.axe.isInverse() == bIsInverse)))) {
        item.bind.axe = null;
        item.bind.key = null;
        item.texts.cleanValue();
        if (item.bind.action != null) {
          ret.addElement(item.bind.action);
        }
      }
    }

    return ret;
  }

  private void setNewAction(WrapTextsAndKeycode excluded, boolean full_axe, boolean bIsInverse) {
    Axe e_axe = excluded.bind.axe;
    e_axe.setFullAxe(full_axe);
    e_axe.setInverse(bIsInverse);
    JavaEvents.SendEvent(17, 0, e_axe);
    excluded.texts.setValue(e_axe.getAxename());
  }

  private void clearByActionNum(int action_num) {
    Iterator iter = this.map_controls.iterator();
    while (iter.hasNext()) {
      WrapTextsAndKeycode wrap = (WrapTextsAndKeycode)iter.next();
      int nom_action = wrap.bind.action.getActionnom();
      if (nom_action == action_num) {
        wrap.bind.axe = null;
        wrap.bind.key = null;
        wrap.texts.cleanValue();
      }
    }
  }

  private void removeAxeBindingsFixOne(WrapTextsAndKeycode excluded0, int half_axe, boolean full_axe) {
    boolean bIsInverse = half_axe == -1;
    Vector oldActio0 = null;
    Vector oldActio1 = null;

    int excludede_action_nom = excluded0.bind.action.getActionnom();
    if ((!(full_axe)) && (((excludede_action_nom == num_axepair_actions[0]) || (excludede_action_nom == num_axepair_actions[1])))) {
      WrapTextsAndKeycode excluded1 = null;
      int paired_action = -1;
      if (excludede_action_nom == num_axepair_actions[0])
        paired_action = num_axepair_actions[1];
      else if (excludede_action_nom == num_axepair_actions[1]) {
        paired_action = num_axepair_actions[1];
      }

      Iterator iter = this.map_controls.iterator();
      while (iter.hasNext()) {
        WrapTextsAndKeycode wrap = (WrapTextsAndKeycode)iter.next();
        int nom_action = wrap.bind.action.getActionnom();
        if (nom_action == paired_action) {
          excluded1 = wrap;
        }
      }

      setNewAction(excluded0, full_axe, bIsInverse);

      if (excluded1 != null) {
        excluded1.bind.axe = excluded0.bind.axe.clone();
        setNewAction(excluded1, full_axe, !(bIsInverse));
        oldActio1 = removeOneAction(excluded1, full_axe, !(bIsInverse));
      }
      oldActio0 = removeOneAction(excluded0, full_axe, bIsInverse);
    } else {
      setNewAction(excluded0, full_axe, bIsInverse);
      oldActio0 = removeOneAction(excluded0, full_axe, bIsInverse);
    }

    if (oldActio0 != null) {
      for (int i = 0; i < oldActio0.size(); ++i) {
        Action action = (Action)oldActio0.elementAt(i);
        if (action != null) {
          if (action.getActionnom() == num_axepair_actions[0])
            clearByActionNum(num_axepair_actions[1]);
          else if (action.getActionnom() == num_axepair_actions[1]) {
            clearByActionNum(num_axepair_actions[0]);
          }
        }
      }
    }

    if (oldActio1 != null) {
      for (int i = 0; i < oldActio1.size(); ++i) {
        Action action = (Action)oldActio1.elementAt(i);
        if (action != null) {
          if (action.getActionnom() == num_axepair_actions[0])
            clearByActionNum(num_axepair_actions[1]);
          else if (action.getActionnom() == num_axepair_actions[1]) {
            clearByActionNum(num_axepair_actions[0]);
          }

        }

      }

    }

    for (int i = 0; i < this.NUM_TABLES; ++i)
      this.ctable[i].redraw();
  }

  public boolean OnRemap(long _menu, int numtable, long _line)
  {
    int line = (int)_line + this.ctable[numtable].sliderPosition();
    if (this.NUM_TABLES * line + numtable < this.map_controls.size()) {
      this.warning.show();
      this.editing = (this.map_controls.get(this.NUM_TABLES * line + numtable));
      JavaEvents.SendEvent(4, 0, this);
      if (0 != this.m_currentdevice)
        JavaEvents.SendEvent(16, 0, this);
      menues.SetScriptObjectAnimation(0L, 223L, this, "CheckKeyAnim");
      this.m_resultkey = -1;
      this.m_resultaxe = -1;
      this.m_close = 0;
      return true;
    }
    return false;
  }

  void CheckKeyAnim(long _menu, double time)
  {
    JavaEvents.SendEvent(4, 1, this);
    if (this.m_close == 1) {
      leaveRemaping();
      return;
    }

    if (0 != this.m_currentdevice)
      JavaEvents.SendEvent(16, 1, this);
    if (this.m_resultkey != -1) {
      leaveRemaping();
      this.editing.texts.setValue(this.m_resultstring);
      menues.SetFieldText(this._remap, this.editing.texts.loc_value);
      menues.UpdateMenuField(menues.ConvertMenuFields(this._remap));
      this.editing.bind.key = new Key();
      this.editing.bind.key.setKeycode(this.m_resultkey);
      this.editing.bind.key.setKeyname(this.m_resultstring);
      this.editing.bind.axe = null;
      removeBindingNotOne(this.editing);
      this.editing = null;
    } else if (-1 != this.m_resultaxe) {
      leaveRemaping();
      this.editing.bind.axe = new Axe();
      this.editing.bind.axe.setAxecode(this.m_resultaxe);
      this.editing.bind.key = null;
      removeAxeBindingsFixOne(this.editing, this.m_half_axe, this.m_full_axe);
    }
  }

  public void leaveRemaping()
  {
    if (this.f_inremaping)
      return;
    this.f_inremaping = true;
    menues.StopScriptAnimation(223L);
    this.warning.hide();
    this.f_inremaping = false;
  }

  @Override
public void onClose() {
    leaveRemaping();
    if (this._remap == 0L) return; menues.SetFieldState(this._remap, 0); }

  @Override
public void onCancel() { }

  @Override
public void onOpen() { }

  @Override
public void onAgreeclose() { }

  public void deinit() { this.m_close = 0;
    JavaEvents.SendEvent(16, 2, this);
    JavaEvents.SendEvent(4, 2, this);
    for (int i = 0; i < this.NUM_TABLES; ++i)
      this.ctable[i].DeInit();
    menues.StopScriptAnimation(223L);
  }

  static class WrapTextsAndKeycode
  {
    ContainerTextTitleTextValue texts;
    Bind bind;

    @Override
	public WrapTextsAndKeycode clone()
    {
      WrapTextsAndKeycode res = new WrapTextsAndKeycode();
      res.texts = this.texts.clone();
      res.bind = this.bind.clone();
      return res;
    }
  }

  class RemapTable extends TableTextAndRadio
  {
    private int number = 0;

    @Override
	public void OnEvent(long event, TableNode node, long group, long _menu)
    {
      if (event != 2L)
        return;
      long n_remap = menues.FindFieldInGroup(_menu, group, "RADIO - VALUE");
      if ((0L != RemapControlsTable.this._remap) && (n_remap != RemapControlsTable.this._remap))
        menues.SetFieldState(RemapControlsTable.this._remap, 0);
      else if (n_remap == RemapControlsTable.this._remap) {
        menues.SetFieldState(RemapControlsTable.this._remap, 1);
      }
      RemapControlsTable(RemapControlsTable.this, n_remap);
      if ((!(RemapControlsTable.this.OnRemap(_menu, this.number, menues.GetLineInTable(_menu, group)))) && 
        (RemapControlsTable.this._remap != 0L)) menues.SetFieldState(RemapControlsTable.this._remap, 0);
    }

    public RemapTable(int paramInt, long paramLong, String paramString1, String paramString2, String paramString3, String paramString4)
    {
      super(paramLong, paramString1, CONTROLGROUP, paramString3, paramString4);
      this.number = paramInt;
    }
  }
}