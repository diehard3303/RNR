/*
 * @(#)MacroKit.java   13/08/25
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class MacroKit {

    /**
     * Method description
     *
     *
     * @param source
     * @param keys
     *
     * @return
     */
    public static String Parse(String source, KeyPair[] keys) {
        StringBuffer buf = new StringBuffer(source);

        for (int i = 0; i < keys.length; ++i) {
            int lastIndex = 0;
            int index = buf.indexOf('%' + keys[i].GetKey().toUpperCase() + '%', lastIndex);

            while (index != -1) {
                lastIndex = index + keys[i].GetKey().length() + 2;
                buf.replace(index, lastIndex, keys[i].GetValue());
                lastIndex = 0;
                index = buf.indexOf('%' + keys[i].GetKey().toUpperCase() + '%', lastIndex);
            }
        }

        return buf.toString();
    }

    /**
     * Method description
     *
     *
     * @param source
     * @param macro
     *
     * @return
     */
    public static boolean HasMacro(String source, String macro) {
        StringBuffer buf = new StringBuffer(source);
        int lastIndex = 0;
        int index = buf.indexOf('%' + macro + '%', lastIndex);

        return (index != -1);
    }

    /**
     * Method description
     *
     *
     * @param text
     * @param keys
     */
    public static void ApplyToTextfield(MENUText_field text, KeyPair[] keys) {
        if (text != null) {
            if (text.origtext == null) {
                text.origtext = text.text;
            }

            text.text = Parse(text.origtext, keys);
            menues.UpdateField(text);
        }
    }
}


//~ Formatted in DD Std on 13/08/25
