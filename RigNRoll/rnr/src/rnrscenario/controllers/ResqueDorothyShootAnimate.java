/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package rnrscenario.controllers;

import players.Crew;
import players.ScenarioPersonPassanger;
import players.actorveh;
import players.aiplayer;
import rnrcore.SCRuniperson;
import rnrscenario.IShootChasing;
import rnrscenario.ShootChasing;
import rnrscenario.consistency.ScenarioClass;

@ScenarioClass(scenarioStage = 1)
public class ResqueDorothyShootAnimate extends ShootChasing {
	private static final String[] mACTOR_NAME = { "BANDIT_JOE_Slow",
			"Model_gun", "Model_shoot" };
	private static final String[] mTRACKS = { "00060shootbegin",
			"00060shootfinish", "00060shootcycle" };
	private aiplayer[] aux_player = new aiplayer[2];

	public ResqueDorothyShootAnimate(IShootChasing chase, actorveh car_chaser,
			boolean isFromLoad) {
		super(chase, car_chaser, Crew.getIgrokCar(), mACTOR_NAME, mTRACKS);
		init(isFromLoad);
	}

	protected void prepareForScenes(boolean isFromLoad) {
		getCar_chaser().registerCar("banditcar");

		aiplayer person_player = new aiplayer("SC_BANDITJOELOW");
		person_player.setModelCreator(new ScenarioPersonPassanger(), "shooter");
		aiplayer gun_player = aiplayer.getNamedAiplayerNormal("SC_BANDITGUN",
				"dorothycagsegun");

		if (!(isFromLoad)) {
			person_player.bePassangerOfCar(getCar_chaser());
			gun_player.bePassangerOfCar_simple(getCar_chaser());
		}

		this.aux_player[0] = person_player;
		this.aux_player[1] = gun_player;
	}

	protected void prepareForFinish() {
		actorveh car = getCar_chaser();
		if (null == car)
			return;
		for (aiplayer anAux_player : this.aux_player) {
			if (null == anAux_player)
				continue;
			anAux_player.abondoneCar(car);
		}
	}

	protected SCRuniperson getShoterModel() {
		return this.aux_player[0].getModel();
	}

	protected SCRuniperson getGunModel() {
		return this.aux_player[1].getModel();
	}
}