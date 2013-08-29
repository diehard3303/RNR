/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package rnr.src.rnrscenario.scenes;

import rnr.src.menu.DateData;
import rnr.src.menuscript.ScenarioBigRaceConfirmation;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.consistency.ScenarioClass;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscenario.tech.SleepOnTime;
import rnr.src.rnrscr.trackscripts;
import rnr.src.scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage = -2, fieldWithDesiredStage = "")
public final class sc01030 extends stage {
	private static final String method_menu_closed = "menuClosed";
	private final Object latch = new Object();
	private boolean f_menu_closed = false;
	private static boolean m_debugScene = false;
	private static boolean m_debugAnswerYes = false;
	private long m_menuCreated = 0L;

	public static void setDebugSceneAnswer(boolean value) {
		m_debugScene = true;
		m_debugAnswerYes = value;
	}

	public sc01030(Object monitor) {
		super(monitor, "sc01030");
	}

	public final void menuClosed() {
		synchronized (this.latch) {
			this.f_menu_closed = true;
		}
	}

	@Override
	protected void playSceneBody(cScriptFuncs automat)
  {
    eng.disableControl();
    rnr.src.rnrscenario.tech.Helper.makeParkAndComeOut("OxnardParking04", 0);
    trackscripts trs = new trackscripts(getSyncMonitor());
    trs.PlaySceneXMLThreaded("01030_part1", true);
    trs.PlaySceneXMLThreaded("01030_part2", false);
    eng.lock();
    long sc = scenetrack.CreateSceneXML("01030_part3", 5, null);
    EventsControllerHelper.getInstance().addMessageListener(this, "menuClosed", "Scenario Answer Recived");
    this.m_menuCreated = ScenarioBigRaceConfirmation.createScenarioBigRaceConfirmationMenu("", new CoreTime(), "", new DateData(), 40);
    eng.unlock();
    int countSleepTimes = 0;
    do
    {
      ++countSleepTimes;
      new SleepOnTime(100);

      synchronized (this.latch)
      {
        if (this.f_menu_closed)
        {
          break label189:
        }
      }
    }
    while ((!(m_debugScene)) || (countSleepTimes <= 30));
    if (m_debugAnswerYes)
      ScenarioBigRaceConfirmation.getLastMenu().onOk(this.m_menuCreated, null);
    else {
      ScenarioBigRaceConfirmation.getLastMenu().onCancel(this.m_menuCreated, null);
    }
    m_debugScene = false;

    label189: eng.lock();
    scenetrack.DeleteScene(sc);
    eng.unlock();
    boolean acceptScene = ScenarioBigRaceConfirmation.gResult();
    if (acceptScene)
      trs.PlaySceneXMLThreaded("01030_part4", false);
    else
      trs.PlaySceneXMLThreaded("01030_part5", false);
    rnr.src.rnrscenario.tech.Helper.makeComeInAndLeaveParking();
    eng.enableControl();
    eng.lock();
    rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
    eng.unlock();
  }
}