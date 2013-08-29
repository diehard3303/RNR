/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package rnr.src.rnrscenario.consistency;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ScenarioClass {
	public static final int LOOK_FOR_STAGE_IN_OBJECT_FIELD = -1;
	public static final int SHOULD_NEVER_BE_INVOKED = -2;

	public abstract int scenarioStage();

	public abstract String fieldWithDesiredStage();
}