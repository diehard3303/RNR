/*
 * @(#)Chase.java   13/08/26
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


package rnr.src.players;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscenario.config.Config;
import rnr.src.rnrscenario.config.ConfigManager;
import rnr.src.rnrscenario.configurators.AiChaseConfig;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Chase {

    /** Field description */
    public static final int relative = 1;

    /** Field description */
    public static final int absolute = 2;

    /** Field description */
    public int mode = 2;

    /** Field description */
    public double dist_ahead0;

    /** Field description */
    public double dist_ahead2;

    /** Field description */
    public double dist_behind0;

    /** Field description */
    public double dist_behind2;

    /** Field description */
    public double minvel;

    /** Field description */
    public double maxvel;

    /**
     * Constructs ...
     *
     */
    public Chase() {
        setParameters("default");
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setMode(int value) {
        this.mode = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setDistAhead0(double value) {
        this.dist_ahead0 = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setDistAhead2(double value) {
        this.dist_ahead2 = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setDistBehind0(double value) {
        this.dist_behind0 = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setDistBehind2(double value) {
        this.dist_behind2 = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setMinVel(double value) {
        this.minvel = value;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void setMaxVel(double value) {
        this.maxvel = value;
    }

    /**
     * Method description
     *
     *
     * @param name
     */
    public void setParameters(String name) {
        Config config = ConfigManager.getGlobal().getConfig(3);

        assert(config instanceof AiChaseConfig) : "illegal type of config";
        ((AiChaseConfig) config).FillParameters(name, this);
    }

    /**
     * Method description
     *
     */
    public void paramMadracing() {
        setParameters("madRacing");
    }

    /**
     * Method description
     *
     */
    public void paramMadChasing() {
        setParameters("madChasing");
    }

    /**
     * Method description
     *
     */
    public void paramModerateChasing() {
        setParameters("moderateChasing");
    }

    /**
     * Method description
     *
     */
    public void paramWeeekChasing() {
        setParameters("weekChasing");
    }

    /**
     * Method description
     *
     *
     * @param paramactorveh1
     * @param paramactorveh2
     */
    public native void makechase(actorveh paramactorveh1, actorveh paramactorveh2);

    /**
     * Method description
     *
     *
     * @param paramactorveh1
     * @param paramactorveh2
     */
    public native void be_ahead(actorveh paramactorveh1, actorveh paramactorveh2);

    /**
     * Method description
     *
     *
     * @param paramactorveh1
     * @param paramactorveh2
     */
    public native void be_behind(actorveh paramactorveh1, actorveh paramactorveh2);
}


//~ Formatted in DD Std on 13/08/26
