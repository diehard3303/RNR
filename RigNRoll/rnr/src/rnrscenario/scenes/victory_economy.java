/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package rnr.src.rnrscenario.scenes;

import rnr.src.menu.JavaEvents;
import rnr.src.menuscript.TotalVictoryMenu;
import rnr.src.menuscript.VictoryMenu;
import rnr.src.menuscript.VictoryMenuExitListener;
import rnr.src.players.Crew;
import rnr.src.players.actorveh;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.scenetrack;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscenario.PayOffManager;
import rnr.src.rnrscenario.cScriptFuncs;
import rnr.src.rnrscenario.stage;
import rnr.src.rnrscenario.tech.SleepOnTime;
import rnr.src.rnrscr.Bar;
import rnr.src.rnrscr.drvscripts;
import rnr.src.rnrscr.trackscripts;

public final class victory_economy extends stage {
	private final Object latch = new Object();
	private final boolean DEBUG_SHOWFINAL_MENU = 0;
	private static final String SCENE = "gold_driver";
	private static final String BAR_NAME = "Oxnard_Bar_01";
	private final boolean f_menu_closed = false;
	private boolean f_create_final_victory_menu = false;
	private final int menu_result = 0;

	public victory_economy(Object monitor) {
		super(monitor, "victory_economy");
	}

	private void enterBar() {
		Bar.setCutSceneAmbientBar(true);
		eng.console("createbarpeople");
	}

	private void exitBar() {
		Bar.setCutSceneAmbientBar(false);
		eng.console("createbarpeople exit");
	}

	@Override
	protected void playSceneBody(cScriptFuncs automat)
  {
    trackscripts trs = new trackscripts(getSyncMonitor());
    eng.lock();
    eng.startMangedFadeAnimation();
    eng.disableControl();
    vectorJ pos = eng.getControlPointPosition("Oxnard_Bar_01");
    actorveh car = Crew.getIgrokCar();
    car.teleport(pos);
    eng.unlock();
    rnr.src.rnrscenario.tech.Helper.waitTeleport();
    trs.PlaySceneXMLThreaded("justfade", false);
    eng.lock();
    long cam_scene = scenetrack.CreateSceneXML("gold_driver", 2, null);
    enterBar();
    eng.unlock();
    new cSceneSuspendEventWaiter(cam_scene).waitEvent();
    eng.lock();
    VictoryMenu.createWinEconomy(new WinMenuClosed());
    boolean hasContest = rnr.src.rnrcore.Helper.hasContest();
    this.f_create_final_victory_menu = (!(hasContest));
    eng.unlock();
    while (true)
    {
      new SleepOnTime(100);
      synchronized (this.latch)
      {
        if (this.f_menu_closed)
        {
          break label168:
        }
      }
    }
    label168: eng.lock();
    PayOffManager.getInstance().makePayOff(PayOffManager.PAYOFF_NAMES[10]);
    eng.startMangedFadeAnimation();
    scenetrack.DeleteScene(cam_scene);
    exitBar();
    eng.unlock();
    switch (this.menu_result)
    {
    case 0:
    	rnr.src.rnrscenario.tech.Helper.makeComeInAndLeaveParking();
      drvscripts.playInsideCarFast(Crew.getIgrok(), car);
      eng.lock();
      eng.enableControl();
      eng.unlock();
      break;
    case 1:
      eng.lock();
      eng.enableControl();
      eng.unlock();
      rnr.src.rnrscenario.tech.Helper.waitGameWorldLoad();
      eng.lock();
      JavaEvents.SendEvent(23, 1, null);
      eng.unlock();
    }

    rnr.src.rnrcore.Helper.debugSceneFinishedEvent();
  }

	class cSceneSuspendEventWaiter {
		long scene;
		private boolean toFinish = false;

		cSceneSuspendEventWaiter(long paramLong) {
			this.scene = paramLong;
			event.eventObject((int) paramLong + 1, this, "event");
			scenetrack.UpdateSceneFlags(paramLong, 1);
		}

		public void event() {
			synchronized (victory_economy.this.latch) {
				this.toFinish = true;
			}
		}

		public void waitEvent() {
			while (true) {
				new SleepOnTime(100);
				boolean isFinished;
				synchronized (victory_economy.this.getSyncMonitor()) {
					isFinished = this.toFinish;
				}
				if (isFinished)
					return;
			}
		}
	}

	class TotalVictoryMenuClosed implements VictoryMenuExitListener {
		@Override
		public void OnMenuExit(int result) {
			synchronized (victory_economy.this.latch) {
				victory_economy.access$202(victory_economy.this, result);
				victory_economy.access$302(victory_economy.this, true);
			}
		}
	}

	class WinMenuClosed implements VictoryMenuExitListener {
		@Override
		public void OnMenuExit(int result) {
			synchronized (victory_economy.this.latch) {
				if (victory_economy.this.f_create_final_victory_menu) {
					TotalVictoryMenu
							.createGameOverTotal(new victory_economy.TotalVictoryMenuClosed());
				} else {
					victory_economy.access$202(victory_economy.this, result);
					victory_economy.access$302(victory_economy.this, true);
				}
			}
		}
	}
}