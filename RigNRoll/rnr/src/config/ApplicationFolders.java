/*
 * @(#)ApplicationFolders.java   13/08/25
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


package rnr.src.config;

//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public final class ApplicationFolders {
    private static String _GCMDFW = ".\\";
    private static String _APPNAMEW = "RigNRoll";

    static {
        BufferedReader bufferedReaderFromFile = null;
        FileInputStream streamFromFile = null;
        InputStreamReader inputStreamReader = null;

        try {
            streamFromFile = new FileInputStream("APPNAMEW.txt");
            inputStreamReader = new InputStreamReader(streamFromFile, "UTF-16LE");
            bufferedReaderFromFile = new BufferedReader(inputStreamReader);
            _APPNAMEW = bufferedReaderFromFile.readLine();
            _APPNAMEW = _APPNAMEW.trim();
        } catch (FileNotFoundException e) {}
        catch (IOException e) {
            e.printStackTrace(System.err);
        } finally {
            close(bufferedReaderFromFile);
            close(streamFromFile);
            close(inputStreamReader);
        }

        _GCMDFW = System.getProperty("user.home") + "\\" + _APPNAMEW + "\\GameWorld\\";
    }

    private static void close(Closeable target) {
        if (null == target) {
            return;
        }

        try {
            target.close();
        } catch (IOException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String GCMDFW() {
        return _GCMDFW;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String GCCFW() {
        return "..\\Data\\config\\";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String GCBFW() {
        return "..\\Bin\\";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String RCMDFW() {
        return GCMDFW();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String RCCFW() {
        return GCCFW();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String RCBFW() {
        return GCBFW();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String GCMDF() {
        return GCMDFW();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String GCCF() {
        return GCCFW();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String GCBF() {
        return GCBFW();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String RCMDF() {
        return GCMDFW();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String RCCF() {
        return GCCFW();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String RCBF() {
        return GCBFW();
    }
}


//~ Formatted in DD Std on 13/08/25
