/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package rnrscenario.controllers;

public abstract interface ScenarioHost {
	public abstract void registerController(
			ScenarioController paramScenarioController);

	public abstract void unregisterController(
			ScenarioController paramScenarioController);
}