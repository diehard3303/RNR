/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package rnr.src.rnrcore;

import rnr.src.menu.JavaEvents;

public final class Helper {
	private static String SCENE_FINISHED_EVENT = "debug_scene_finished";

	public static void addNativeEventListener(INativeMessageEvent listener) {
		NativeEventController.addNativeEventListener(listener);
	}

	public static void removeNativeEventListener(INativeMessageEvent listener) {
		NativeEventController.removeNativeListener(listener);
	}

	public static void peekNativeMessage(String message) {
		if (!(eng.noNative))
			JavaEvents.SendEvent(46, 0, new NativeMessage(message));
	}

	public static void debugSceneFinishedEvent() {
		if (!(eng.noNative))
			JavaEvents
					.SendEvent(46, 0, new NativeMessage(SCENE_FINISHED_EVENT));
	}

	public static vectorJ getPackageShift() {
		vectorJ pos = new vectorJ();
		JavaEvents.SendEvent(55, 0, pos);
		return pos;
	}

	public static int isWarehouseWaitCheckin()
  {
    la test = new Object()
    {
      boolean flag;
      int value;
    };
    JavaEvents.SendEvent(69, 0, test);
    if (test.flag)
      return test.value;
    return -1;
  }

	public static void waitCheckinOnWarehouse()
  {
    2a test = new Object()
    {
      boolean flag;
      int value;
    };
    JavaEvents.SendEvent(69, 0, test);
    if (!(test.flag)) {
      eng.err("attempring waitCheckinOnWarehouse on base that is not pending for checking, or our rating is to low to paticipate in race");
      return;
    }
    JavaEvents.SendEvent(69, 1, new Object());
  }

	public static double getTimeToReachPointInSeconds(vectorJ point) {
		Data data = new Data();
		data.pos1 = rnr.src.rnrscr.Helper.getCurrentPosition();
		data.pos2 = point;
		JavaEvents.SendEvent(41, 0, data);
		return data.time;
	}

	public static double getTimeToReachFinishPointIsSeconds(String mission_name) {
		Data data = new Data();
		data.pos1 = rnr.src.rnrscr.Helper.getCurrentPosition();
		data.pos2 = data.pos1;
		data.mission_name = mission_name;
		JavaEvents.SendEvent(41, 2, data);
		return data.time;
	}

	public static double getTimeToReachFinishPointIsSecondsWarehouseOrder() {
		Data data = new Data();
		data.pos1 = rnr.src.rnrscr.Helper.getCurrentPosition();
		data.pos2 = data.pos1;
		JavaEvents.SendEvent(41, 3, data);
		return data.time;
	}

	public static boolean hasContest() {
		GameState game_state = new GameState();
		JavaEvents.SendEvent(40, 0, game_state);
		return ((game_state.has_economy_contest) || (game_state.has_scenario_contest));
	}

	public static boolean winEconomy() {
		GameState game_state = new GameState();
		JavaEvents.SendEvent(40, 0, game_state);
		return ((!(game_state.has_economy_contest)) && (game_state.succeeded_economy_win));
	}

	static class Data {
		vectorJ pos1;
		vectorJ pos2;
		String mission_name;
		double time;
	}

	static class NativeMessage {
		String message;

		NativeMessage() {
		}

		NativeMessage(String message) {
			this.message = message;
		}
	}

	static class GameState {
		boolean has_economy_contest;
		boolean succeeded_economy_win;
		boolean has_scenario_contest;
		boolean succeeded_scenario_win;
		boolean last_loose;
	}
}