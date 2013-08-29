/*
 * @(#)InvokeMethodStateLoader.java   13/08/28
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


package rnr.src.xmlserialization.nxs;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.xmlserialization.Log;

//~--- JDK imports ------------------------------------------------------------

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class InvokeMethodStateLoader implements StateRecordLoader {
    private static final String ERROR_FORMAT_STRING =
        "NXS-load: InvokeMethodStateLoader failed to invoke '%s' on object of type '%s': %s";
    private final Method delegate;

    /**
     * Constructs ...
     *
     *
     * @param method
     */
    public InvokeMethodStateLoader(Method method) {
        assert(null != method);

        if ((1 != method.getParameterTypes().length) || (!(String.class.equals(method.getParameterTypes()[0])))) {
            throw new IllegalArgumentException("method must take String as argument");
        }

        this.delegate = method;
    }

    /**
     * Method description
     *
     *
     * @param host
     * @param recordData
     */
    @Override
    public void load(Object host, String recordData) {
        assert(null != host);

        try {
            this.delegate.invoke(host, new Object[] { recordData });
        } catch (IllegalAccessException e) {
            Log.warning(
                String.format(
                    "NXS-load: InvokeMethodStateLoader failed to invoke '%s' on object of type '%s': %s",
                    new Object[] { this.delegate.getName(),
                                   host.getClass().getName(), e.getMessage() }));
        } catch (InvocationTargetException e) {
            Log.warning(
                String.format(
                    "NXS-load: InvokeMethodStateLoader failed to invoke '%s' on object of type '%s': %s",
                    new Object[] { this.delegate.getName(),
                                   host.getClass().getName(), e.getMessage() }));
        }
    }
}


//~ Formatted in DD Std on 13/08/28
