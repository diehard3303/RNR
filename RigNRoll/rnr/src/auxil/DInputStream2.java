/*
 * @(#)DInputStream2.java   13/08/25
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.eng;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.InputStream;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class DInputStream2 extends InputStream {
    private int markedPtr = 0;
    byte[] outBuf = null;
    int ptr = 0;

    /**
     * Constructs ...
     *
     *
     * @param in
     *
     * @throws IOException
     */
    public DInputStream2(InputStream in) throws IOException {
        byte[] inBuf = new byte[in.available()];
        int r = in.read(inBuf);

        assert(r == inBuf.length);

        while (in.available() > 0) {
            byte[] buf = new byte[in.available() + inBuf.length];

            System.arraycopy(inBuf, inBuf.length, buf, 0, inBuf.length);

            int r1 = in.read(buf, inBuf.length, in.available());

            assert(r1 == buf.length - inBuf.length);
            inBuf = buf;
        }

        if (eng.noNative) {
            this.outBuf = inBuf;
        } else {
            this.outBuf = DCProcess.process(inBuf, 0);
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
        return (this.outBuf.length - this.ptr);
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
        this.markedPtr = this.ptr;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public boolean markSupported() {
        return true;
    }

    /**
     * Method description
     *
     *
     * @throws IOException
     */
    @Override
    public synchronized void reset() throws IOException {
        this.ptr = this.markedPtr;
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
        if (this.ptr < this.outBuf.length) {
            return this.outBuf[(this.ptr++)];
        }

        return -1;
    }
}


//~ Formatted in DD Std on 13/08/25
