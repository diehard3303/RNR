/*
 * @(#)CBVideoAnimation.java   13/08/28
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
Released under the FreeBSD  license 
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met: 

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies, 
either expressed or implied, of the FreeBSD Project.
 *
 *
 *
 */


package rnr.src.rnrscr;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.MENUTruckview;
import rnr.src.rnrcore.SCRtalkingperson;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.SceneActorsPool;
import rnr.src.rnrcore.scenetrack;

//~--- JDK imports ------------------------------------------------------------

import java.util.Stack;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class CBVideoAnimation {
    private static Stack<String> contacterID = null;
    private long animation = 0L;
    private SCRtalkingperson person = null;
    private MENUTruckview view = null;
    private String m_suffics = "";

    /** Field description */
    public boolean finished = false;

    private CBVideoAnimation(MENUTruckview view) {
        this.view = view;
        this.m_suffics = suffics();
    }

    private static String suffics() {
        if (null == contacterID) {
            contacterID = new Stack<String>();

            for (int i = 0; i < 100; ++i) {
                suffics("_cbv_contacter" + i);
            }
        }

        return (contacterID.pop());
    }

    private static void suffics(String res) {
        contacterID.push(res);
    }

    /**
     * Method description
     *
     */
    public void finish() {
        scenetrack.DeleteScene(this.animation);
        this.animation = 0L;
        this.view.UnBind3DModel();
        this.view = null;
        this.person.stop();
        this.person = null;
        suffics(this.m_suffics);
        this.finished = true;
    }

    private static final String formeSceneName(String modelname) {
        if (modelname.contains("Woman")) {
            return "Woman";
        }

        if (modelname.contains("Man_")) {
            return "Man";
        }

        return modelname;
    }

    /**
     * Method description
     *
     *
     * @param view
     * @param modelName
     *
     * @return
     */
    public static final CBVideoAnimation makeAnimation(MENUTruckview view, String modelName) {
        CBVideoAnimation res = new CBVideoAnimation(view);

        res.person = new SCRtalkingperson(SCRuniperson.createNamedCBVideoPerson(modelName, modelName + res.m_suffics,
                null));

        SCRuniperson unipers = res.person.getPerson();

        unipers.play();

        Vector<SceneActorsPool> v = new Vector<SceneActorsPool>(1);

        v.add(new SceneActorsPool("man", unipers));
        res.animation = scenetrack.CreateSceneXMLPool(formeSceneName(modelName), 5, v, new preset(modelName));
        res.view.BindPerson(res.animation, unipers, modelName, 2, 0);

        return res;
    }

    static class preset {

        /** Field description */
        public String modelname;

        preset(String modelname) {
            this.modelname = modelname;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
