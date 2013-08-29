/*
 * @(#)MENUText_field.java   13/08/25
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

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class MENUText_field {

    /** Field description */
    public String nameID = new String();

    /** Field description */
    public long nativePointer;

    /** Field description */
    public boolean inXML;

    /** Field description */
    public int ID;

    /** Field description */
    public int userid;

    /** Field description */
    public int Xres;

    /** Field description */
    public int Yres;

    /** Field description */
    public int poy;

    /** Field description */
    public int pox;

    /** Field description */
    public int leny;

    /** Field description */
    public int lenx;

    /** Field description */
    public int fonssz;

    /** Field description */
    public int baseline;

    /** Field description */
    public int horizbaseline;

    /** Field description */
    public boolean bold;

    /** Field description */
    public boolean italic;

    /** Field description */
    public String font;

    /** Field description */
    public String text;

    /** Field description */
    public String origtext;

    /** Field description */
    public String TextFlags;

    /** Field description */
    public int textColor;

    /** Field description */
    public String parentName;

    /** Field description */
    public String parentType;

    /** Field description */
    public Vector<?> callbacks;

    /**
     * Constructs ...
     *
     */
    public MENUText_field() {
        this.userid = -1;
    }
}


//~ Formatted in DD Std on 13/08/25
