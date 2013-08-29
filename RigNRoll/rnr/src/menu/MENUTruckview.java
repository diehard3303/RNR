/*
 * @(#)MENUTruckview.java   13/08/25
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.SCRuniperson;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class MENUTruckview {

    /** Field description */
    public long nativePointer;

    /** Field description */
    public String nameID;

    /** Field description */
    public String text;

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
    @SuppressWarnings("rawtypes")
    public Vector textures;

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector materials;

    /** Field description */
    @SuppressWarnings("rawtypes")
    public Vector callbacks;

    /** Field description */
    public String parentName;

    /** Field description */
    public String parentType;

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramInt1
     * @param paramInt2
     */
    public native void Bind3DModel(String paramString, int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramInt1
     * @param paramInt2
     */
    public native void BindVehicle(long paramLong, int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramSCRuniperson
     * @param paramString
     * @param paramInt1
     * @param paramInt2
     */
    public native void BindPerson(long paramLong, SCRuniperson paramSCRuniperson, String paramString, int paramInt1,
                                  int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     */
    public native void SetState(int paramInt1, int paramInt2, int paramInt3);

    /**
     * Method description
     *
     */
    public native void UnBind3DModel();

    /**
     * Method description
     *
     *
     * @param paramString
     * @param paramInt
     *
     * @return
     */
    public native int AddSwitch(String paramString, int paramInt);

    /**
     * Method description
     *
     *
     * @return
     */
    public native int SetupViewer();
}


//~ Formatted in DD Std on 13/08/25
