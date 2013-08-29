/*
 * @(#)RatedItem.java   13/08/26
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


package rnr.src.rnrrating;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.Log;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class RatedItem {
    private String id = "Non identified";
    private int rating = 20;

    /**
     * Constructs ...
     *
     *
     * @param id
     */
    public RatedItem(String id) {
        this.id = id;
    }

    /**
     * Method description
     *
     *
     * @param id
     *
     * @return
     */
    public boolean compare(String id) {
        return (this.id.compareToIgnoreCase(id) == 0);
    }

    /**
     * Method description
     *
     *
     * @param rate
     */
    public void addRating(int rate) {
        this.rating += rate;
        Log.rating(this.id + "\taddRating\t" + rate + "\tTotal\t" + this.rating);
    }

    /**
     * Method description
     *
     *
     * @param rate
     */
    public void subRating(int rate) {
        this.rating -= rate;
        this.rating = ((this.rating < 20)
                       ? 20
                       : this.rating);
        Log.rating(this.id + "\tsubRating\t" + rate + "\tTotal\t" + this.rating);
    }

    /**
     * Method description
     *
     *
     * @param rate
     */
    public void setRating(int rate) {
        this.rating = rate;
        this.rating = ((this.rating < 20)
                       ? 20
                       : this.rating);
        Log.rating(this.id + "\tsetRating\t" + rate + "\tTotal\t" + this.rating);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     * Method description
     *
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
}


//~ Formatted in DD Std on 13/08/26
