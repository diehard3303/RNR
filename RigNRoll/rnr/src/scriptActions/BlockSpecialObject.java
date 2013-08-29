/*
 * @(#)BlockSpecialObject.java   13/08/27
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


package rnr.src.scriptActions;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrloggers.ScenarioLogger;
import rnr.src.rnrscenario.SoBlock;
import rnr.src.rnrscenario.scenarioscript;
import rnr.src.scenarioUtils.StringToSOTypeConverter;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public final class BlockSpecialObject extends ScenarioAction {
    static final int years = 65536;
    static final int monthes = 4096;
    static final int days = 256;
    static final int hours = 16;
    static final int minuts = 1;
    boolean use_name = false;

    /** Field description */
    public String name = "no name";
    private String type = null;
    int tip = 0;
    boolean timed = false;
    int timeflag = 256;
    int num_years = 0;
    int num_monthes = 0;
    int num_days = 0;
    int num_hours = 0;
    int num_minuts = 0;

    /**
     * Constructs ...
     *
     *
     * @param scenario
     */
    public BlockSpecialObject(scenarioscript scenario) {
        super(scenario);
    }

    /**
     * Method description
     *
     */
    @Override
    public void act() {
        if (!(validate())) {
            System.err.println("BlockSpecialObject wasn't correctly initialized");

            return;
        }

        this.tip = StringToSOTypeConverter.convert(this.type);

        if (this.timed) {
            if (0 >= (this.timeflag & 0x100) - 256) {
                if (this.use_name) {
                    getScript().getSoBlocker().addTimeBlocker_DAYS(this.tip, this.name, this.num_days);
                } else {
                    getScript().getSoBlocker().addTimeBlocker_DAYS(this.tip, this.num_days);
                }
            }
        } else if (this.use_name) {
            getScript().getSoBlocker().addBlocker(this.tip, this.name);
        } else {
            getScript().getSoBlocker().addBlocker(this.tip);
        }

        ScenarioLogger.getInstance().machineLog(Level.INFO,
                "action performed " + super.getClass().getName() + " name == " + this.name);
    }

    /**
     * Method description
     *
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean validate() {
        return ((null != this.type) && (null != this.name));
    }
}


//~ Formatted in DD Std on 13/08/27
