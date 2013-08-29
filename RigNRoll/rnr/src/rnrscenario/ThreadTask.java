/*
 * @(#)ThreadTask.java   13/08/26
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


package rnr.src.rnrscenario;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.eng;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class ThreadTask implements Runnable {
    private static final Executor threadPool = Executors.newSingleThreadExecutor();
    private final Semaphore semaphore = new Semaphore(0);
    private final Ithreadprocedure proc;

    /**
     * Constructs ...
     *
     *
     * @param proc
     */
    public ThreadTask(Ithreadprocedure proc) {
        this.proc = proc;
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {
        this.proc.take(this);
        this.proc.call();
    }

    /**
     * Method description
     *
     *
     * @param proc
     */
    public static void create(Ithreadprocedure proc) {
        threadPool.execute(new ThreadTask(proc));
    }

    /**
     * Method description
     *
     */
    public void _resum() {
        if (0 >= this.semaphore.availablePermits()) {
            this.semaphore.release();
        } else {
            eng.fatal("ThreadTask._resum called twice!");
        }
    }

    /**
     * Method description
     *
     */
    public void _susp() {
        try {
            this.semaphore.acquire();
        } catch (InterruptedException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /**
     * Method description
     *
     *
     * @param milleseconds
     */
    public void sleep(long milleseconds) {
        try {
            Thread.sleep(milleseconds);
        } catch (InterruptedException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
