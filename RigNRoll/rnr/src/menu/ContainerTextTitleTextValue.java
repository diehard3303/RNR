/*
 * @(#)ContainerTextTitleTextValue.java   13/08/25
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

import rnr.src.rnrcore.loc;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class ContainerTextTitleTextValue {
    private final String title;
    private String value;

    /** Field description */
    public String loc_title;

    /** Field description */
    public String loc_value;

    private ContainerTextTitleTextValue(ContainerTextTitleTextValue base) {
        this.title = base.title;
        this.value = base.value;
        this.loc_title = base.loc_title;
        this.loc_value = base.loc_value;
    }

    /**
     * Constructs ...
     *
     *
     * @param title
     * @param value
     */
    public ContainerTextTitleTextValue(String title, String value) {
        this.title = title;
        this.value = value;
        this.loc_title = loc.getMenuString(title);
        this.loc_value = loc.getMenuString(value);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public ContainerTextTitleTextValue clone() {
        return new ContainerTextTitleTextValue(this);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Method description
     *
     *
     * @param n_value
     */
    public void setValue(String n_value) {
        this.value = n_value;
        this.loc_value = loc.getMenuString(this.value);
    }

    /**
     * Method description
     *
     */
    public void cleanValue() {
        this.value = "";
        this.loc_value = "";
    }
}


//~ Formatted in DD Std on 13/08/25
