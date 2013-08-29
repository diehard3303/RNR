/*
 * @(#)TableCallbacks.java   13/08/25
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
 * Interface description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public abstract interface TableCallbacks {

    /**
     * Method description
     *
     *
     * @param paramTableNode
     * @param paramMENUText_field
     */
    public abstract void SetupLineInTable(TableNode paramTableNode, MENUText_field paramMENUText_field);

    /**
     * Method description
     *
     *
     * @param paramTableNode
     * @param paramMENUsimplebutton_field
     */
    public abstract void SetupLineInTable(TableNode paramTableNode, MENUsimplebutton_field paramMENUsimplebutton_field);

    /**
     * Method description
     *
     *
     * @param paramTableNode
     * @param paramMENUbutton_field
     */
    public abstract void SetupLineInTable(TableNode paramTableNode, MENUbutton_field paramMENUbutton_field);

    /**
     * Method description
     *
     *
     * @param paramInt
     * @param paramTableNode
     * @param paramObject
     */
    public abstract void SetupLineInTable(int paramInt, TableNode paramTableNode, Object paramObject);

    /**
     * Method description
     *
     *
     * @param paramLong1
     * @param paramTableNode
     * @param paramLong2
     * @param paramLong3
     */
    public abstract void OnEvent(long paramLong1, TableNode paramTableNode, long paramLong2, long paramLong3);
}


//~ Formatted in DD Std on 13/08/25
