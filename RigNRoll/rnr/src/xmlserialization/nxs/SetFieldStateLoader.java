/*
 * @(#)SetFieldStateLoader.java   13/08/28
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

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;

final class SetFieldStateLoader implements StateRecordLoader {
    private static final Map<Class<?>, FieldSetter> fieldSetters;

    static {
        fieldSetters = new HashMap<Class<?>, FieldSetter>();
    }

    private final Field field;
    private final FieldSetter setter;

    SetFieldStateLoader(Field field) {
        assert(null != field);
        this.field = field;
        this.setter = (fieldSetters.get(field.getType()));

        if (null != this.setter) {
            return;
        }

        throw new IllegalArgumentException(String.format("Type '%s' is unsupported",
                new Object[] { field.getClass().getName() }));
    }

    static void addFieldSetter(FieldSetter setter) {
        if (null == setter) {
            return;
        }

        fieldSetters.put(setter.getFieldType(), setter);
    }

    /**
     * Method description
     *
     *
     * @param fieldHost
     * @param recordData
     */
    @Override
    public void load(Object fieldHost, String recordData) {
        assert(null != fieldHost);
        this.field.setAccessible(true);
        this.setter.setField(fieldHost, this.field, recordData);
    }

    static abstract class FieldSetter {
        private final Class<?> fieldType;

        FieldSetter(Class<?> fieldType) {
            this.fieldType = fieldType;
        }

        abstract void set(Object paramObject, Field paramField, String paramString) throws IllegalAccessException;

        final Class<?> getFieldType() {
            return this.fieldType;
        }

        final void setField(Object fieldHost, Field field, String data) {
            assert(null != field);

            if (null == data) {
                return;
            }

            try {
                set(fieldHost, field, data);
            } catch (NumberFormatException e) {
                Log.warning(
                    String.format(
                        "NXS-load: FieldSetter failed to setup field '%s' of class '%s' instance: %s",
                        new Object[] { field.getName(),
                                       fieldHost.getClass().getName(), e.getMessage() }));
            } catch (IllegalAccessException e) {
                Log.warning(
                    String.format(
                        "NXS-load: FieldSetter failed to setup field '%s' of class '%s' instance: %s",
                        new Object[] { field.getName(),
                                       fieldHost.getClass().getName(), e.getMessage() }));
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/28
