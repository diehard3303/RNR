/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package players;

public enum CarName {
	CAR_COCH, CAR_BANDITS, CAR_DAKOTA, CAR_PITER_PAN, CAR_MATHEW, CAR_MATHEW_DEAD, CAR_MONICA, CAR_DOROTHY, CAR_POLICE, CAR_GEPARD, CAR_JOHN;

	private final String name;
	private final int color;

	public static final CarName[] values() {
		return ((CarName[]) $VALUES.clone());
	}

	public String getName() {
		return this.name;
	}

	public int getColor() {
		return this.color;
	}
}