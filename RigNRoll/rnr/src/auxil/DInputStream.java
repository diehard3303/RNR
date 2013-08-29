/*
 * @(#)DInputStream.java   13/08/25
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

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class DInputStream extends InputStream {
    boolean e = false;
    ranb r = null;
    int[] buf = { -1, -1, -1, -1 };
    int ptr = 4;
    InputStream in;

    /**
     * Constructs ...
     *
     *
     * @param in
     *
     * @throws IOException
     */
    public DInputStream(InputStream in) throws IOException {
        this.in = in;

        for (int k = 0; k < 4; ++k) {
            if ((this.buf[k] = in.read()) == -1) {
                break;
            }
        }

        if (Arrays.equals(this.buf, new int[] { 83, 68, 84, 69 })) {
            this.e = true;
            this.r = new ranb(31235L);
        } else {
            this.ptr = 0;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     *
     * @throws IOException
     */
    @Override
    public int available() throws IOException {
        return super.available();
    }

    /**
     * Method description
     *
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        super.close();
    }

    /**
     * Method description
     *
     *
     * @param readlimit
     */
    @Override
    public synchronized void mark(int readlimit) {
        super.mark(readlimit);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean markSupported() {
        return super.markSupported();
    }

    /**
     * Method description
     *
     *
     * @throws IOException
     */
    @Override
    public synchronized void reset() throws IOException {
        super.reset();
    }

    /**
     * Method description
     *
     *
     * @param n
     *
     * @return
     *
     * @throws IOException
     */
    @Override
    public long skip(long n) throws IOException {
        return super.skip(n);
    }

    /**
     * Method description
     *
     *
     * @return
     *
     * @throws IOException
     */
    @Override
    public int read() throws IOException {
        if (this.e) {
            int src = this.in.read();

            if (src == -1) {
                return src;
            }

            return ((int) (src ^ this.r.next(256L)) & 0xFF);
        }

        if (this.ptr < 4) {
            if (this.buf[this.ptr] == -1) {
                return -1;
            }

            return this.buf[(this.ptr++)];
        }

        return this.in.read();
    }
}


//~ Formatted in DD Std on 13/08/25
