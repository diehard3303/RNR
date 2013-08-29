/*
 * @(#)TimeData.java   13/08/25
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
   Released under the BSD 3 clause license
Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this 
    list of conditions and the following disclaimer. Redistributions in binary 
    form must reproduce the above copyright notice, this list of conditions and 
    the following disclaimer in the documentation and/or other materials 
    provided with the distribution. Neither the name of the DieHard Development 
    nor the names of its contributors may be used to endorse or promote products 
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 *
 *
 *
 */


package rnr.src.menu;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class TimeData {

    /** Field description */
    public int hours;

    /** Field description */
    public int minutes;

    /** Field description */
    public int seconds;

    /**
     * Constructs ...
     *
     */
    public TimeData() {}

    /**
     * Constructs ...
     *
     *
     * @param _hours
     * @param _minutes
     */
    public TimeData(int _hours, int _minutes) {
        this.hours = _hours;
        this.minutes = _minutes;
        this.seconds = 0;
    }

    /**
     * Constructs ...
     *
     *
     * @param _hours
     * @param _minutes
     * @param _seconds
     */
    public TimeData(int _hours, int _minutes, int _seconds) {
        this.hours = _hours;
        this.minutes = _minutes;
        this.seconds = _seconds;
    }

    /**
     * Method description
     *
     *
     * @param secshift
     *
     * @return
     */
    public int AddSeconds(int secshift) {
        int outval = 0;

        this.seconds += secshift;

        if (this.seconds >= 60) {
            this.minutes += this.seconds / 60;
            this.seconds %= 60;
        }

        if (this.seconds < 0) {
            this.minutes -= this.seconds / 60 + 1;
            this.seconds = (this.seconds % 60 + 60);
        }

        if (this.minutes >= 60) {
            this.hours += this.minutes / 60;
            this.minutes %= 60;
        }

        if (this.minutes < 0) {
            this.hours -= this.minutes / 60 + 1;
            this.minutes = (this.minutes % 60 + 60);
        }

        if (this.hours >= 24) {
            outval += this.hours / 24;
            this.hours %= 24;
        }

        if (this.hours < 0) {
            outval -= this.hours / 24 + 1;
            this.hours = (this.hours % 24 + 24);
        }

        return outval;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public TimeData Clone() {
        return new TimeData(this.hours, this.minutes, this.seconds);
    }
}


//~ Formatted in DD Std on 13/08/25
