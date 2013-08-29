/*
 * @(#)sctask.java   13/08/28
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


package rnr.src.rnrscenario;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class sctask implements scrun {
    private boolean f_mark = false;
    private boolean surviveDuringGameDeinit = true;
    private int m_tip = 3;
    protected boolean f_started;
    protected boolean f_finished;

    /**
     * Constructs ...
     *
     *
     * @param tip
     * @param stopOnGameDeinit
     */
    public sctask(int tip, boolean stopOnGameDeinit) {
        this.surviveDuringGameDeinit = (!(stopOnGameDeinit));
        this.m_tip = tip;

        switch (tip) {
         case 0 :
             ScenarioSave.getInstance().addTaskOnEveryFrame(this);

             break;

         case 3 :
             ScenarioSave.getInstance().addTaskOn3Seconds(this);

             break;

         case 60 :
             ScenarioSave.getInstance().addTaskOn60Seconds(this);

             break;

         case 600 :
             ScenarioSave.getInstance().addTaskOn600Seconds(this);
        }

        this.f_started = false;
        this.f_finished = false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean couldSurviveDuringGameDeinit() {
        return this.surviveDuringGameDeinit;
    }

    /**
     * Method description
     *
     *
     * @param ob
     */
    @Override
    public void add(scrun ob) {}

    /**
     * Method description
     *
     */
    @Override
    public void run() {}

    /**
     * Method description
     *
     */
    @Override
    public void finish() {
        this.f_finished = true;
    }

    /**
     * Method description
     *
     */
    @Override
    public void finishImmediately() {
        switch (this.m_tip) {
         case 0 :
             ScenarioSave.getInstance().removeTaskOnEveryFrame(this);

             break;

         case 3 :
             ScenarioSave.getInstance().removeTaskOn3Seconds(this);

             break;

         case 60 :
             ScenarioSave.getInstance().removeTaskOn60Seconds(this);

             break;

         case 600 :
             ScenarioSave.getInstance().removeTaskOn600Seconds(this);
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void start() {
        this.f_started = true;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean finished() {
        return this.f_finished;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean started() {
        return this.f_started;
    }

    /**
     * Method description
     *
     *
     * @param f_mark
     */
    @Override
    public void mark(boolean f_mark) {
        this.f_mark = f_mark;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean marked() {
        return this.f_mark;
    }
}


//~ Formatted in DD Std on 13/08/28
