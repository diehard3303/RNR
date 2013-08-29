/*
 * @(#)XInputStreamCreate.java   13/08/25
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class XInputStreamCreate {
    static Pattern pattern = Pattern.compile("\\.[Xx][Mm][Ll]");

    /**
     * Method description
     *
     *
     * @param path
     *
     * @return
     *
     * @throws FileNotFoundException
     */
    public static InputStream open(String path) throws FileNotFoundException {
        FileInputStream f = null;

        try {
            f = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            f = null;
        }

        Matcher m = pattern.matcher(path);
        String rpath = m.replaceAll(".pkqt");

        if (!(rpath.equals(path))) {
            FileInputStream fs = null;

            try {
                fs = new FileInputStream(rpath);
            } catch (FileNotFoundException e) {
                fs = null;
            }

            if ((fs == null) && (f == null)) {
                throw new FileNotFoundException();
            }

            if ((fs != null) && (f != null)) {
                throw new FileNotFoundException();
            }

            if (fs != null) {
                return fs;
            }
        }

        if (f != null) {
            return f;
        }

        throw new FileNotFoundException();
    }
}


//~ Formatted in DD Std on 13/08/25
