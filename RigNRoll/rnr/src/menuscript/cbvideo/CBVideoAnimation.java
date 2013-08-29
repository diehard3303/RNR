/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript.cbvideo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import menu.Controls;
import menu.JavaEvents;
import menu.menues;
import menuscript.IMenuListener;
import players.Crew;
import players.IcontaktCB;
import players.aiplayer;
import rnrcore.EventsHolder;
import rnrcore.IEventListener;
import rnrcore.loc;
import rnrscr.CBVideocall;
import rnrscr.MissionDialogs;
import rnrscr.cbdialogmessage;
import rnrscr.cbdialogmessage.request;

public class CBVideoAnimation implements IMenuListener {
	private static final String[] POSTFIXES;
	private static final int YES_ANSWER = 0;
	private static final int NO_ANSWER = 1;
	private static final String METHOD_MESSAGE = "onMessage";
	private static final String METHOD_REQUEST = "onRequest";
	private static final String METHOD_ANIMATION = "onAnimation";
	private static final int animationId = 1;
	private static final int cookie = 1;
	private CBVideocall call = null;
	private MenuCall menu = null;
	private ArrayList<Dialogitem> full_dialog = new ArrayList();
	private OnPauseOn on = null;
	private OnPauseOff off = null;
	private MenuCallFullDialog history = null;
	private boolean wait_answer = false;
	private boolean bHistoryShown = false;

	public CBVideoAnimation(CBVideocall call) {
		this.call = call;
		this.menu = MenuCall.create();
		this.menu.SetTimeToAnswer(call.gTimeForAnswer());
		this.menu.setItem(new Dialogitem(call.who(), call
				.incommingCallMessage()));
	}

	private Dialogitem constructMessage(cbdialogmessage mess, IcontaktCB contact) {
		if (this.on == null) {
			this.on = new OnPauseOn();
		}
		if (this.off == null) {
			this.off = new OnPauseOff();
		}

		if (mess.requests.isEmpty()) {
			return new Dialogitem(contact, mess.message);
		}
		this.wait_answer = true;
		if (this.menu != null) {
			this.menu.startAnimation();
		}
		String[] answers = new String[mess.requests.size()];
		int count = 0;
		Iterator iter = mess.requests.iterator();
		while (iter.hasNext()) {
			cbdialogmessage.request req = (cbdialogmessage.request) iter.next();
			if ((req.id < POSTFIXES.length) && (req.id >= 0)) {
				answers[count] = loc.getDialogName(req.requestmessage)
						+ POSTFIXES[req.id];
				++count;
			}
		}

		String[] _answers = new String[count];
		for (int i = 0; i < count; ++i) {
			_answers[i] = answers[i];
		}
		return new Dialogitem(contact, _answers, 1, -1);
	}

	void onMessage(cbdialogmessage mess) {
		Dialogitem item = constructMessage(mess, (mess.igrokspeeks) ? Crew
				.getIgrok().createCBContacter() : this.call.who());
		this.menu.setItem(item);
		this.full_dialog.add(item);
	}

	void onRequest(int _state) {
	}

	void onAnimation(long cookie, double time) {
		if (this.call.gFinished()) {
			JavaEvents.SendEvent(26, 0, this);
			menues.StopScriptAnimation(1L);
			this.menu.close();
			if (this.on != null) {
				this.on.leave();
			}
			if (this.off != null) {
				this.off.leave();
			}

			sayEndDialog();
		}
		if (this.wait_answer)
			if ((Controls.isNoPressed())
					|| ((this.menu != null) && (!(this.menu.itAnimationRun())))
					|| ((this.history != null) && (this.history.IsAnswered()) && (this.history
							.GetAnswerNum() == 1))) {
				if ((this.history != null) && (this.history.IsAnswered())) {
					hideHistory();
					JavaEvents.SendEvent(26, 2, this);
				}
				if (!(this.full_dialog.isEmpty())) {
					this.full_dialog.remove(this.full_dialog.size() - 1);
				}
				this.wait_answer = false;
				this.menu.stopAnimation();
				this.call.answer(1);
				sayNo();
			} else if ((Controls.isYesPressed())
					|| (Controls.isEnterPressed())
					|| ((this.history != null) && (this.history.IsAnswered()) && (this.history
							.GetAnswerNum() == 0))) {
				if ((this.history != null) && (this.history.IsAnswered())) {
					hideHistory();
					JavaEvents.SendEvent(26, 2, this);
				}
				if (!(this.full_dialog.isEmpty())) {
					this.full_dialog.remove(this.full_dialog.size() - 1);
				}
				this.wait_answer = false;
				this.menu.stopAnimation();
				this.call.answer(0);
				sayYes();
			}
	}

	private void showHistory() {
		if (!(this.bHistoryShown)) {
			this.call.pause(true);
			this.menu.hide();
			this.history = MenuCallFullDialog.create(this.full_dialog);
			this.bHistoryShown = true;
		}
	}

	private void hideHistory() {
		if (this.bHistoryShown) {
			this.call.pause(false);
			this.history.close();
			this.history = null;
			this.menu.show();
			this.bHistoryShown = false;
		}
	}

	private void sayEndDialog() {
		MissionDialogs.sayEnd(this.call.getResourceName());
	}

	private void sayNo() {
		MissionDialogs.sayNo(this.call.getResourceName());
	}

	private void sayYes() {
		MissionDialogs.sayYes(this.call.getResourceName());
	}

	private void sayAppear() {
		MissionDialogs.sayAppear(this.call.getResourceName());
	}

	public void onClose() {
		assert (this.call != null);
		this.call.registerMesageCallBack(this, "onMessage");
		this.call.registerRequestCallBack(this, "onRequest");
		this.call.start();
		menues.SetScriptObjectAnimation(1L, 1L, this, "onAnimation");
		JavaEvents.SendEvent(26, 1, this);
		sayAppear();
	}

	public void onOpen() {
	}

	static {
		POSTFIXES = new String[] { "(Y)", "(N)" };
	}

	class OnPauseOff implements IEventListener {
		OnPauseOff() {
			EventsHolder.addEventListenet(28, this);
		}

		void leave() {
			EventsHolder.removeEventListenet(28, this);
		}

		public void on_event(int value) {
			CBVideoAnimation.this.hideHistory();
		}
	}

	class OnPauseOn implements IEventListener {
		OnPauseOn() {
			EventsHolder.addEventListenet(27, this);
		}

		void leave() {
			EventsHolder.removeEventListenet(27, this);
		}

		public void on_event(int value) {
			CBVideoAnimation.this.showHistory();
		}
	}
}