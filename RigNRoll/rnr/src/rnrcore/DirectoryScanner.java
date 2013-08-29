/*
 * @(#)DirectoryScanner.java   13/08/26
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


package rnr.src.rnrcore;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.FileFilter;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class DirectoryScanner {
    private final String path;

    /**
     * Constructs ...
     *
     *
     * @param path
     */
    public DirectoryScanner(String path) {
        this.path = path;
    }

    /**
     * Enum description
     *
     */
    public static enum Type {
        subdirs, nosubdirs;

        /**
         * Method description
         *
         *
         * @return
         */
        public static final Type[] values() {
            return ((Type[]) $VALUES.clone());
        }
    }

    private void scanSubdir(String extention, File path, IScanFileListener listener) {
        if (path.isDirectory()) {
            File[] files = path.listFiles(new Filter(extention));

            for (File file : files) {
                if (file.isFile()) {
                    listener.scan(file.getPath());
                } else {
                    scanSubdir(extention, file, listener);
                }
            }
        } else {
            listener.scan(path.getPath());
        }
    }

    /**
     * Method description
     *
     *
     * @param extention
     * @param listener
     * @param type
     */
    public void scanFiles(String extention, IScanFileListener listener, Type type) {
        File dir = new File(this.path);

        if (dir.isFile()) {
            return;
        }

        File[] files = dir.listFiles(new Filter(extention));

        if (null == files) {
            return;
        }

        switch (type.ordinal()) {
         case 1 :
             for (File file : files) {
                 scanSubdir(extention, file, listener);
             }

             break;

         case 2 :
             for (File file : files) {
                 if (file.isFile()) {
                     listener.scan(file.getPath());
                 }
             }
        }
    }

    static class Filter implements FileFilter {
        private final String extention;

        Filter(String extention) {
            this.extention = extention;
        }

        /**
         * Method description
         *
         *
         * @param pathname
         *
         * @return
         */
        @Override
        public boolean accept(File pathname) {
            if (pathname.isDirectory()) {
                return true;
            }

            return pathname.getName().endsWith(this.extention);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
