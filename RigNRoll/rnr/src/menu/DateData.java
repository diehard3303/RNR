/*
 * @(#)DateData.java   13/08/25
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
public class DateData {

    /** Field description */
    public static final int JAN = 1;

    /** Field description */
    public static final int FEB = 2;

    /** Field description */
    public static final int MAR = 3;

    /** Field description */
    public static final int APR = 4;

    /** Field description */
    public static final int MAY = 5;

    /** Field description */
    public static final int JUN = 6;

    /** Field description */
    public static final int JUL = 7;

    /** Field description */
    public static final int AUG = 8;

    /** Field description */
    public static final int SEP = 9;

    /** Field description */
    public static final int OCT = 10;

    /** Field description */
    public static final int NOV = 11;

    /** Field description */
    public static final int DEC = 12;

    /** Field description */
    public int month;

    /** Field description */
    public int day;

    /** Field description */
    public int year;

    /**
     * Constructs ...
     *
     */
    public DateData() {}

    /**
     * Constructs ...
     *
     *
     * @param month
     * @param day
     * @param year
     */
    public DateData(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }
}


//~ Formatted in DD Std on 13/08/25