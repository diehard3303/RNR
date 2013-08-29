/*
 * @(#)ranb.java   13/08/25
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


package rnr.src.auxil;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class ranb {
    long[] table = new long[55];
    int index1;
    int index2;

    /**
     * Constructs ...
     *
     *
     * @param j
     */
    public ranb(long j) {
        seed(j);
    }

    /**
     * Method description
     *
     *
     * @param limit
     *
     * @return
     */
    public long next(long limit) {
        this.index1 = ((this.index1 + 1) % 55);
        this.index2 = ((this.index2 + 1) % 55);
        this.table[this.index1] = (this.table[this.index1] - this.table[this.index2] & 0xFFFFFFFF);

        return (this.table[this.index1] % limit);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public long next() {
        this.index1 = ((this.index1 + 1) % 55);
        this.index2 = ((this.index2 + 1) % 55);
        this.table[this.index1] = (this.table[this.index1] - this.table[this.index2] & 0xFFFFFFFF);

        return this.table[this.index1];
    }

    /**
     * Method description
     *
     *
     * @param j
     */
    public void seed(long j) {
        for (int q = 0; q < 55; ++q) {
            this.table[q] = 0L;
        }

        long k = 1L;

        this.table[54] = j;

        for (int i = 0; i < 54; ++i) {
            int ii = 21 * i % 55;

            this.table[ii] = k;
            k = j - k & 0xFFFFFFFF;
            j = this.table[ii];
        }

        for (int loop = 0; loop < 4; ++loop) {
            for (int i = 0; i < 55; ++i) {
                this.table[i] = (this.table[i] - this.table[((1 + i + 30) % 55)] & 0xFFFFFFFF);
            }
        }

        this.index1 = 0;
        this.index2 = 31;
    }
}


//~ Formatted in DD Std on 13/08/25
