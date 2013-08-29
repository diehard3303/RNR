/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package rnr.src.rnrscr;

import rnr.src.rnrcore.SCRanimparts;
import rnr.src.rnrcore.SCRperson;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.vectorJ;

public class SFpedestrians extends animation {
	private static final String MODEL_TO_CHOOSE = null;
	static SCRanimparts MANGO1;
	static SCRanimparts JUMP002;
	static SCRanimparts JUMP002Sim;
	static SCRanimparts OTHOD2;
	static SCRanimparts Falling;
	static SCRanimparts FallingSim;
	static SCRanimparts Laing;
	static SCRanimparts BLaing;
	static SCRanimparts StandUp;
	static SCRanimparts StandUpBack;
	static SCRanimparts FRONTFALL;
	static SCRanimparts BACKFALL;
	static SCRanimparts ToWallB1;
	static SCRanimparts ToWallB2;
	static SCRanimparts ToWallF1;
	static SCRanimparts ATAWALL;
	static SCRanimparts ATAWALLFACE;
	static SCRanimparts ASINCH1;
	static SCRanimparts OTHODB;
	static SCRanimparts STANDING;
	static SCRanimparts FORCAR1;
	static SCRanimparts FORCAR2;
	static SCRanimparts FORCAR3;
	static SCRanimparts FORCAR4;
	static SCRanimparts FORCAR5;
	static SCRanimparts FORCAR6;
	static SCRanimparts FORCAR7;
	static SCRanimparts FORCAR8;
	static SCRanimparts FORCAR9;
	static SCRanimparts FORCAR9SIM;
	static SCRanimparts FORCAR10;
	static SCRanimparts FORCAR10SIM;
	static SCRanimparts RUN;
	static SCRperson PERSONAGE;

	public void BlockedByCar(SCRperson PERSONAGEIn) {
		Chainanm FirstChain = CreateChain(PERSONAGEIn);
		FirstChain.Add(3, 5, 1, 2.0D);
		FirstChain.AddAsk(7, 1.0D);
		FirstChain.Add(3, 3, 1, 0.1D);
		PERSONAGEIn.StartChain(FirstChain);
	}

	public void RunFrom(SCRperson PERSONAGEIn) {
		Chainanm FirstChain = CreateChain(PERSONAGEIn);
		FirstChain.Add(3, 4, 1, RandomLength(1.0D, 2.0D));
		PERSONAGEIn.StartChain(FirstChain);
	}

	public void FalltoFront(SCRperson PERSONAGEIn) {
		Chainanm FirstChain = CreateChain(PERSONAGEIn);
		FirstChain.Add(2, 1, 7);
		FirstChain.Add(3, 5, 3, 2.0D);
		FirstChain.AddAsk(6, 1.0D);
		FirstChain.Add(2, 1, 10);
		PERSONAGEIn.StartChain(FirstChain);
	}

	public void FalltoBack(SCRperson PERSONAGEIn) {
		Chainanm FirstChain = CreateChain(PERSONAGEIn);
		FirstChain.Add(2, 1, 8);
		FirstChain.Add(3, 5, 2, 2.0D);
		FirstChain.AddAsk(6, 1.0D);
		FirstChain.Add(2, 1, 9);
		PERSONAGEIn.StartChain(FirstChain);
	}

	public void FlightOverChest(SCRperson PERSONAGEIn) {
		Chainanm FirstChain = CreateChain(PERSONAGEIn);
		FirstChain.Add(2, 1, 13);
		FirstChain.Add(3, 5, 3, 2.0D);
		FirstChain.AddAsk(6, 1.0D);
		FirstChain.Add(2, 1, 10);
		PERSONAGEIn.StartChain(FirstChain);
	}

	public void FlightOverBack(SCRperson PERSONAGEIn) {
		Chainanm FirstChain = CreateChain(PERSONAGEIn);
		FirstChain.Add(2, 1, 14);
		FirstChain.Add(3, 5, 2, 2.0D);
		FirstChain.AddAsk(6, 1.0D);
		FirstChain.Add(2, 1, 9);
		PERSONAGEIn.StartChain(FirstChain);
	}

	public void ReturnOnWay(SCRperson PERSONAGEIn, vectorJ toreturn) {
		Chainanm FirstChain = CreateChain(PERSONAGEIn);

		FirstChain.Add(1, toreturn, 3, 1);
		PERSONAGEIn.StartChain(FirstChain);
	}

	public void LeftOthod(SCRperson PERSONAGEIn) {
		Chainanm FirstChain = CreateChain(PERSONAGEIn);
		FirstChain.Add(2, 1, 11);
		PERSONAGEIn.StartChain(FirstChain);
	}

	public void RaunToWall(SCRperson PERSONAGEIn, vectorJ point1, vectorJ point2) {
		Chainanm FirstChain = CreateChain(PERSONAGEIn);

		FirstChain.Add(1, point1, 4, 1);
		FirstChain.Add(1, point2, 4, 1);

		FirstChain.Add(2, 1, 2);
		FirstChain.Add(3, 2, 2, 10.0D);
		PERSONAGEIn.StartChain(FirstChain);
	}

	public void FallRight(SCRperson PERSONAGEIn) {
		Chainanm FirstChain = CreateChain(PERSONAGEIn);
		FirstChain.Add(2, 1, 5);
		FirstChain.Add(3, 5, 3, 2.0D);
		FirstChain.AddAsk(6, 1.0D);
		FirstChain.Add(2, 1, 10);
		PERSONAGEIn.StartChain(FirstChain);
	}

	public void FallLeft(SCRperson PERSONAGEIn) {
		Chainanm FirstChain = CreateChain(PERSONAGEIn);
		FirstChain.Add(2, 1, 6);
		FirstChain.Add(3, 5, 3, 2.0D);
		FirstChain.AddAsk(6, 1.0D);
		FirstChain.Add(2, 1, 10);
		PERSONAGEIn.StartChain(FirstChain);
	}

	public void PlayPredefine() {
		Chainanm FirstChain = CreateChain(PERSONAGE);
		FirstChain.Add(2, 1, 3);
		FirstChain.Add(2, 1, 4);
		FirstChain.Add(2, 1, 8);
		FirstChain.Add(3, 5, 2, 1.0D);
		FirstChain.Add(2, 1, 9);
		FirstChain.Add(2, 1, 7);
		FirstChain.Add(3, 5, 3, 2.0D);
		FirstChain.Add(2, 1, 10);

		FirstChain.Add(3, 3, 1);
		PERSONAGE.StartChain(FirstChain);
	}

	public void Init(int VsegoPed) {
		if (VsegoPed == 0)
			return;
		CreateAnimation animcreation = new CreateAnimation();
		Eventsmain EVENTS = new Eventsmain();

		for (int i = 0; i < VsegoPed; ++i) {
			animcreation.dotisthin(animcreation.getModelName(i));
			EVENTS.dotisthin(PERSONAGE);
		}
	}

	public static SCRperson createPedestrian(String model_name) {
		SFpedestrians sf = new SFpedestrians();
		SFpedestrians tmp13_12 = sf;
		tmp13_12.getClass();
		CreateAnimation animcreation = new CreateAnimation();
		SFpedestrians tmp27_26 = sf;
		tmp27_26.getClass();
		Eventsmain EVENTS = new Eventsmain();
		animcreation.dotisthin(model_name);
		EVENTS.dotisthin(PERSONAGE);
		return PERSONAGE;
	}

	public class Eventsmain {
		public void dotisthin(SCRperson PERSONAGEIn) {
			PERSONAGEIn.AttachToEvent(7);
			PERSONAGEIn.AttachToEvent(5);
			PERSONAGEIn.AttachToEvent(4);
			PERSONAGEIn.AttachToEvent(6);
		}
	}

	public class CreateAnimation {
		private boolean isWomanModel(String model_name) {
			return model_name.startsWith("Woman");
		}

		private boolean isHeelWomanModel(String model_name) {
			return (model_name.compareTo("Woman007") == 0);
		}

		private String getModelName(int nom) {
			String model_name = "Woman001";
			nom += 1;
			if (nom > 41) {
				double deveance = nom / 41.0D;
				int periodF = (int) Math.floor(deveance);
				nom -= 41 * periodF;
			}
			switch (nom) {
			case 0:
				model_name = "Woman001";
				break;
			case 1:
				model_name = "Woman001";
				break;
			case 2:
				model_name = "Woman002";
				break;
			case 3:
				model_name = "Woman003";
				break;
			case 4:
				model_name = "Woman004";
				break;
			case 5:
				model_name = "Woman005";
				break;
			case 6:
				model_name = "Woman006";
				break;
			case 7:
				model_name = "Woman007";
				break;
			case 8:
				model_name = "Woman008";
				break;
			case 9:
				model_name = "Woman009";
				break;
			case 10:
				model_name = "Woman010";
				break;
			case 11:
				model_name = "Woman011";
				break;
			case 12:
				model_name = "Woman012";
				break;
			case 13:
				model_name = "Woman013";
				break;
			case 14:
				model_name = "Woman014";
				break;
			case 15:
				model_name = "Woman015";
				break;
			case 16:
				model_name = "Woman016";
				break;
			case 17:
				model_name = "Woman017";
				break;
			case 18:
				model_name = "Woman018";
				break;
			case 19:
				model_name = "Woman019";
				break;
			case 20:
				model_name = "Woman020";
				break;
			case 21:
				model_name = "Man_001";
				break;
			case 22:
				model_name = "Man_002";
				break;
			case 23:
				model_name = "Man_003";
				break;
			case 24:
				model_name = "Man_004";
				break;
			case 25:
				model_name = "Man_005";
				break;
			case 26:
				model_name = "Man_006";
				break;
			case 27:
				model_name = "Man_007";
				break;
			case 28:
				model_name = "Man_008";
				break;
			case 29:
				model_name = "Man_009";
				break;
			case 30:
				model_name = "Man_010";
				break;
			case 31:
				model_name = "Man_011";
				break;
			case 32:
				model_name = "Man_012";
				break;
			case 33:
				model_name = "Man_013";
				break;
			case 34:
				model_name = "Man_014";
				break;
			case 35:
				model_name = "Man_015";
				break;
			case 36:
				model_name = "Man_016";
				break;
			case 37:
				model_name = "Man_017";
				break;
			case 38:
				model_name = "Man_018";
				break;
			case 39:
				model_name = "Man_019";
				break;
			case 40:
				model_name = "Man_020";
				break;
			default:
				model_name = "Man_001";
			}
			if (null != SFpedestrians.MODEL_TO_CHOOSE)
				model_name = SFpedestrians.MODEL_TO_CHOOSE;
			return model_name;
		}

		public void dotisthin(String model_name) {
      vectorJ temppos = new vectorJ();

      vectorJ pos = new vectorJ(0.0D, 0.0D, 0.0D);
      vectorJ dir = new vectorJ(0.0D, 1.0D, 0.0D);

      boolean iswoman = isWomanModel(model_name);
      int WOMANHEEL = 1;
      int WOMANFOOT = 2;
      int womantype = (iswoman) ? WOMANFOOT : (isHeelWomanModel(model_name)) ? WOMANHEEL : 0;
      SFpedestrians.PERSONAGE = SCRperson.CreateAnm(model_name, pos, dir, false);

      double tuner = 1.0D;
      double tunerFall = 2.0D;

      int Choice = 0;
      double randCH = Math.random();
      if (randCH < 0.25D) Choice = 1;
      if ((randCH >= 0.25D) && (randCH < 0.5D)) Choice = 2;
      if ((randCH >= 0.5D) && (randCH < 0.75D)) Choice = 3;
      if ((randCH >= 0.75D) && (randCH < 1.0D)) Choice = 4;

      double velocityresult = 1.0D;

      if (iswoman);
      String NameOfWalk;
      String NameOfRun;
      String NameOfWalk;
      String NameOfRun;
      switch (womantype)
      {
      case 2:
        switch (Choice)
        {
        case 1:
          NameOfWalk = "WGOThin002"; velocityresult = 1.0D; break;
        case 2:
          NameOfWalk = "WGOThin004"; velocityresult = 0.8D; break;
        case 3:
          NameOfWalk = "WGOThin005"; velocityresult = 1.2D; break;
        case 4:
          NameOfWalk = "WGOThin006"; velocityresult = 1.0D; break;
        default:
          NameOfWalk = "WGOThin006"; velocityresult = 1.0D;
        }
        NameOfRun = "WomanRun001";
        break;
      case 1:
        switch (Choice)
        {
        case 1:
          NameOfWalk = "WGoHell001"; velocityresult = 0.8D; break;
        default:
          NameOfWalk = "WGoHell001"; velocityresult = 1.0D;
        }
        NameOfRun = "WomanRun001";
        break;
      default:
        switch (Choice)
        {
        case 1:
          NameOfWalk = "WGOThin002"; velocityresult = 1.0D; break;
        case 2:
          NameOfWalk = "WGOThin004"; velocityresult = 0.8D; break;
        case 3:
          NameOfWalk = "WGOThin005"; velocityresult = 1.2D; break;
        case 4:
          NameOfWalk = "WGOThin006"; velocityresult = 1.0D; break;
        default:
          NameOfWalk = "WGOThin006"; velocityresult = 1.0D;
        }
        NameOfRun = "WomanRun001";
        break label525:

        switch (Choice)
        {
        case 1:
          NameOfWalk = "ManGo001"; velocityresult = 1.2D; break;
        case 2:
          NameOfWalk = "ManGo002"; velocityresult = 1.0D; break;
        case 3:
          NameOfWalk = "ManGo003"; velocityresult = 1.0D; break;
        case 4:
          NameOfWalk = "ManGo004"; velocityresult = 1.0D; break;
        default:
          NameOfWalk = "ManGo004"; velocityresult = 1.0D;
        }
        NameOfRun = "ManRun001";
      }

      label525: SFpedestrians.MANGO1 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, NameOfWalk);

      SFpedestrians.MANGO1.Tune(tuner, true, true);
      SFpedestrians.MANGO1.SetVelocity(animation.RandomVelocity(velocityresult) * tuner);

      SFpedestrians.RUN = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, NameOfRun);
      SFpedestrians.RUN.Tune(tuner * 2.0D, true, true);
      SFpedestrians.RUN.SetVelocity(animation.RandomVelocity(1.5D) * tuner * 2.0D);

      SFpedestrians.STANDING = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "StayRandom");
      SFpedestrians.STANDING.Tune(tuner, true, true);
      SFpedestrians.STANDING.SetVelocity(0.0D);

      SFpedestrians.JUMP002 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "Jump002");
      SFpedestrians.JUMP002.Tune(tuner, false, false);
      temppos.Set(-2.07D, 0.0D, 0.0D);
      SFpedestrians.JUMP002.SetShift(temppos);
      temppos.Set(1.0D, 0.0D, 0.0D);
      SFpedestrians.JUMP002.SetDir(temppos);

      SFpedestrians.JUMP002Sim = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "Jump002Sim");
      SFpedestrians.JUMP002Sim.Tune(tuner, false, false);
      temppos.Set(2.14D, 0.0D, 0.0D);
      SFpedestrians.JUMP002Sim.SetShift(temppos);
      temppos.Set(-1.0D, 0.0D, 0.0D);
      SFpedestrians.JUMP002Sim.SetDir(temppos);

      SFpedestrians.OTHOD2 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "Othod002");
      SFpedestrians.OTHOD2.Tune(tuner, false, false);
      temppos.Set(-0.28849D, 0.30839D, 0.0D);
      SFpedestrians.OTHOD2.SetShift(temppos);
      temppos.Set(0.0D, 1.0D, 0.0D);
      SFpedestrians.OTHOD2.SetDir(temppos);

      SFpedestrians.OTHODB = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "OthodB001");
      SFpedestrians.OTHODB.Tune(tuner, false, false);
      temppos.Set(0.0D, -0.54048D, 0.0D);
      SFpedestrians.OTHODB.SetShift(temppos);
      temppos.Set(0.0D, 1.0D, 0.0D);
      SFpedestrians.OTHODB.SetDir(temppos);

      SFpedestrians.Falling = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "J_Falling001");
      SFpedestrians.Falling.Tune(tunerFall, false, false);
      temppos.Set(-2.64D, 0.0D, 0.0D);
      SFpedestrians.Falling.SetShift(temppos);
      temppos.Set(-1.0D, 0.0D, 0.0D);
      SFpedestrians.Falling.SetDir(temppos);

      SFpedestrians.FallingSim = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "J_Falling001Sim");
      SFpedestrians.FallingSim.Tune(tunerFall, false, false);
      temppos.Set(2.65622D, 0.0D, 0.0D);
      SFpedestrians.FallingSim.SetShift(temppos);
      temppos.Set(1.0D, 0.0D, 0.0D);
      SFpedestrians.FallingSim.SetDir(temppos);

      SFpedestrians.Laing = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "Laying001");
      SFpedestrians.Laing.Tune(tuner, true, false);
      SFpedestrians.Laing.SetVelocity(0.0D);

      SFpedestrians.BLaing = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "BLaying001");
      SFpedestrians.BLaing.Tune(tuner, true, false);
      SFpedestrians.BLaing.SetVelocity(0.0D);

      SFpedestrians.StandUp = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "StandUp001");
      SFpedestrians.StandUp.Tune(tunerFall, false, false);
      temppos.Set(0.0D, -0.7D, 0.0D);
      SFpedestrians.StandUp.SetShift(temppos);
      temppos.Set(0.0D, 1.0D, 0.0D);
      SFpedestrians.StandUp.SetDir(temppos);

      SFpedestrians.StandUpBack = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "StandUp002");
      SFpedestrians.StandUpBack.Tune(tunerFall, false, false);
      temppos.Set(0.62039D, 0.91575D, 0.0D);
      SFpedestrians.StandUpBack.SetShift(temppos);
      temppos.Set(0.0D, -1.0D, 0.0D);
      SFpedestrians.StandUpBack.SetDir(temppos);

      SFpedestrians.FRONTFALL = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "JFFalling001");
      SFpedestrians.FRONTFALL.Tune(tunerFall, false, false);
      temppos.Set(0.0D, 4.09D, 0.0D);
      SFpedestrians.FRONTFALL.SetShift(temppos);
      temppos.Set(0.0D, 1.0D, 0.0D);
      SFpedestrians.FRONTFALL.SetDir(temppos);

      SFpedestrians.FORCAR1 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar001");
      SFpedestrians.FORCAR1.Tune(tunerFall, false, false);
      temppos.Set(-2.6229D, -5.15998D, 0.0D);
      SFpedestrians.FORCAR1.SetShift(temppos);
      temppos.Set(-1.0D, 0.0D, 0.0D);
      SFpedestrians.FORCAR1.SetDir(temppos);

      SFpedestrians.FORCAR2 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar001");
      SFpedestrians.FORCAR2.Tune(tunerFall, false, false);
      temppos.Set(-2.6229D, -5.15998D, 0.0D);
      SFpedestrians.FORCAR2.SetShift(temppos);
      temppos.Set(-1.0D, 0.0D, 0.0D);
      SFpedestrians.FORCAR2.SetDir(temppos);

      SFpedestrians.FORCAR3 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar003");
      SFpedestrians.FORCAR3.Tune(2.0D, false, false);
      temppos.Set(-2.6229D, -5.15998D, 0.0D);
      SFpedestrians.FORCAR3.SetShift(temppos);
      temppos.Set(-1.0D, 0.0D, 0.0D);
      SFpedestrians.FORCAR3.SetDir(temppos);

      SFpedestrians.FORCAR4 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar004");
      SFpedestrians.FORCAR4.Tune(2.0D, false, false);
      temppos.Set(-3.43185D, -5.15998D, 0.0D);
      SFpedestrians.FORCAR4.SetShift(temppos);
      temppos.Set(-1.0D, 0.0D, 0.0D);
      SFpedestrians.FORCAR4.SetDir(temppos);

      SFpedestrians.FORCAR5 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar005");
      SFpedestrians.FORCAR5.Tune(2.0D, false, false);
      temppos.Set(-3.43185D, -5.15998D, 0.0D);
      SFpedestrians.FORCAR5.SetShift(temppos);
      temppos.Set(-1.0D, 0.0D, 0.0D);
      SFpedestrians.FORCAR5.SetDir(temppos);

      SFpedestrians.FORCAR6 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar006");
      SFpedestrians.FORCAR6.Tune(2.0D, false, false);
      temppos.Set(-3.87639D, 7.7759D, 0.0D);
      SFpedestrians.FORCAR6.SetShift(temppos);
      temppos.Set(0.0D, -1.0D, 0.0D);
      SFpedestrians.FORCAR6.SetDir(temppos);

      SFpedestrians.FORCAR7 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar007");
      SFpedestrians.FORCAR7.Tune(2.0D, false, false);
      temppos.Set(-3.87639D, 7.7759D, 0.0D);
      SFpedestrians.FORCAR7.SetShift(temppos);
      temppos.Set(0.0D, -1.0D, 0.0D);
      SFpedestrians.FORCAR7.SetDir(temppos);

      SFpedestrians.FORCAR8 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar008");
      SFpedestrians.FORCAR8.Tune(2.0D, false, false);
      temppos.Set(-2.9058D, 8.677199999999999D, 0.0D);
      SFpedestrians.FORCAR8.SetShift(temppos);
      temppos.Set(1.0D, 0.0D, 0.0D);
      SFpedestrians.FORCAR8.SetDir(temppos);

      SFpedestrians.FORCAR9 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar009");
      SFpedestrians.FORCAR9.Tune(2.0D, false, false);
      temppos.Set(-1.52852D, -2.54889D, 0.0D);
      SFpedestrians.FORCAR9.SetShift(temppos);
      temppos.Set(-1.0D, 0.0D, 0.0D);
      SFpedestrians.FORCAR9.SetDir(temppos);

      SFpedestrians.FORCAR9SIM = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar009Sim");
      SFpedestrians.FORCAR9SIM.Tune(2.0D, false, false);
      temppos.Set(1.52852D, -2.54889D, 0.0D);
      SFpedestrians.FORCAR9SIM.SetShift(temppos);
      temppos.Set(1.0D, 0.0D, 0.0D);
      SFpedestrians.FORCAR9SIM.SetDir(temppos);

      SFpedestrians.FORCAR10 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar010");
      SFpedestrians.FORCAR10.Tune(2.0D, false, false);
      temppos.Set(-2.09063D, 2.91445D, 0.0D);
      SFpedestrians.FORCAR10.SetShift(temppos);
      temppos.Set(1.0D, 0.0D, 0.0D);
      SFpedestrians.FORCAR10.SetDir(temppos);

      SFpedestrians.FORCAR10SIM = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "ForCar010Sim");
      SFpedestrians.FORCAR10SIM.Tune(2.0D, false, false);
      temppos.Set(2.09063D, 2.91445D, 0.0D);
      SFpedestrians.FORCAR10SIM.SetShift(temppos);
      temppos.Set(-1.0D, 0.0D, 0.0D);
      SFpedestrians.FORCAR10SIM.SetDir(temppos);

      SFpedestrians.BACKFALL = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "JBFalling001");
      SFpedestrians.BACKFALL.Tune(tunerFall, false, false);
      temppos.Set(0.0D, -4.85D, 0.0D);
      SFpedestrians.BACKFALL.SetShift(temppos);
      temppos.Set(0.0D, 1.0D, 0.0D);
      SFpedestrians.BACKFALL.SetDir(temppos);

      SFpedestrians.ToWallB1 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "To_Wall001");
      SFpedestrians.ToWallB1.Tune(tuner, false, false);
      temppos.Set(0.0D, -0.43108D, 0.0D);
      SFpedestrians.ToWallB1.SetShift(temppos);
      temppos.Set(0.0D, 1.0D, 0.0D);
      SFpedestrians.ToWallB1.SetDir(temppos);

      SFpedestrians.ToWallB2 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "To_Wall002");
      SFpedestrians.ToWallB2.Tune(tuner, false, false);
      temppos.Set(0.0D, -0.6009D, 0.0D);
      SFpedestrians.ToWallB2.SetShift(temppos);
      temppos.Set(0.0D, 1.0D, 0.0D);
      SFpedestrians.ToWallB2.SetDir(temppos);

      SFpedestrians.ToWallF1 = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "To_Wall003");
      SFpedestrians.ToWallF1.Tune(tuner, false, false);
      temppos.Set(0.0D, 0.52884D, 0.0D);
      SFpedestrians.ToWallF1.SetShift(temppos);
      temppos.Set(0.0D, 1.0D, 0.0D);
      SFpedestrians.ToWallF1.SetDir(temppos);

      SFpedestrians.ATAWALL = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "AtaWall001");
      SFpedestrians.ATAWALL.Tune(tuner, true, false);
      SFpedestrians.ATAWALL.SetVelocity(0.0D);

      SFpedestrians.ATAWALLFACE = SFpedestrians.this.CreateAnm(SFpedestrians.PERSONAGE, "AtaWall002");
      SFpedestrians.ATAWALLFACE.Tune(tuner, true, false);
      SFpedestrians.ATAWALLFACE.SetVelocity(0.0D);

      SFpedestrians.ASINCH1 = SFpedestrians.this.CreateAnmanmFive(SFpedestrians.PERSONAGE, null, null, null, "Head_Act001", "R_H_Act001", "L_H_Act001");

      SFpedestrians.ASINCH1.Tune(0.5D, true, true);

      SFpedestrians.ASINCH1.SetUpAsinchron(4.0D, 0.05D);

      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.MANGO1, 3, 1, 5);

      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.RUN, 4, 1, 5);

      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.JUMP002, 1, 3, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.JUMP002Sim, 1, 4, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.Falling, 1, 6, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FallingSim, 1, 5, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.StandUp, 1, 10, 5);

      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.StandUpBack, 1, 9, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FRONTFALL, 1, 7, 5);

      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.BACKFALL, 1, 8, 5);

      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.ToWallB1, 1, 1, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.ToWallB2, 1, 1, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.ToWallF1, 1, 2, 5);

      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.OTHOD2, 1, 11, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.OTHODB, 1, 12, 5);

      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR1, 1, 14, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR2, 1, 14, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR3, 1, 14, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR4, 1, 14, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR5, 1, 14, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR6, 1, 14, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR7, 1, 14, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR8, 1, 14, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR9, 1, 13, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR9SIM, 1, 13, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR10, 1, 14, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.FORCAR10SIM, 1, 14, 5);

      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.ATAWALL, 2, 1, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.ATAWALLFACE, 2, 2, 5);

      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.Laing, 5, 3, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.BLaing, 5, 2, 5);
      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.STANDING, 5, 1, 5);

      SFpedestrians.PERSONAGE.AddAnimGroup(SFpedestrians.ASINCH1, 6, 0, 5);
      SFpedestrians.PERSONAGE.SetFive(1, SFpedestrians.MANGO1);

      eng.play(SFpedestrians.PERSONAGE);
    }
	}
}