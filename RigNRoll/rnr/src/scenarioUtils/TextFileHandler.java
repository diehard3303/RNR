/*
 * @(#)TextFileHandler.java   13/08/27
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


package rnr.src.scenarioUtils;

//~--- JDK imports ------------------------------------------------------------

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public final class TextFileHandler extends Handler {
    private static final String SEPARATOR = "======================================================";
    private FileOutputStream output = null;
    private PrintWriter writer = null;

    /**
     * Constructs ...
     *
     *
     * @param path
     * @param append
     * @param formatter
     *
     * @throws IOException
     */
    public TextFileHandler(String path, boolean append, Formatter formatter) throws IOException {
        this.output = new FileOutputStream(path, append);
        this.writer = new PrintWriter(this.output);
        this.writer.println("======================================================");
        this.writer.println("======================================================");
        this.writer.flush();
        this.output.flush();
        setFormatter(formatter);
    }

    /**
     * Method description
     *
     *
     * @param record
     */
    @Override
    public void publish(LogRecord record) {
        if (null == record) {
            return;
        }

        Formatter formatter = getFormatter();

        if ((null == formatter) || ((null != getFilter()) && (!(getFilter().isLoggable(record))))) {
            return;
        }

        try {
            this.writer.println(formatter.format(record));
            this.writer.flush();
            this.output.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /**
     * Method description
     *
     */
    @Override
    public void flush() {
        try {
            this.writer.flush();
            this.output.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /**
     * Method description
     *
     *
     * @throws SecurityException
     */
    @Override
    public void close() throws SecurityException {
        try {
            this.writer.close();
            this.output.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
    }

	/**
	 * @return the separator
	 */
	public static String getSeparator() {
		return SEPARATOR;
	}
}


//~ Formatted in DD Std on 13/08/27
