/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package menuscript;

public abstract interface VictoryMenuExitListener {
	public static final int RESULT_CONTINUE = 0;
	public static final int RESULT_EXIT = 1;

	public abstract void OnMenuExit(int paramInt);
}