/*
 * @(#)Variables.java   13/08/25
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


package rnr.src.adjusting;

//~--- JDK imports ------------------------------------------------------------

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class Variables {
    private static final String FILENAME = "adjust.xml";
    private static final String VARIABLE = "variable";
    private static final String CLASS = "class";
    private static final String FIELD = "field";
    private static final String VALUE = "value";
    private static final String GETCURRENTOBJECT = "getCurrentObject";

    /**
     * Method description
     *
     */
    public static void adjust() {
        rnr.src.xmlutils.Node top = rnr.src.xmlutils.XmlUtils.parse("adjust.xml");

        if (null == top) {
            log("ADJUSTING. There is no adjust.xml for adjusting.", true);

            return;
        }

        rnr.src.xmlutils.NodeList vars = top.getNamedChildren("variable");

        for (int i = 0; i < vars.size(); ++i) {
            setupNode(vars.get(i));
        }
    }

    private static void setupNode(rnr.src.xmlutils.Node node) {
        String classname = node.getAttribute("class");
        String fieldname = node.getAttribute("field");
        String value = node.getAttribute("value");

        if (null == classname) {
            log("ADJUSTING. No attribute class", true);

            return;
        }

        if (null == fieldname) {
            log("ADJUSTING. No attribute field", true);

            return;
        }

        if (null == value) {
            log("ADJUSTING. No attribute value", true);

            return;
        }

        try {
            Class<?> cls = Class.forName(classname);
            Method getobj = cls.getMethod("getCurrentObject", new Class[0]);
            Object result = getobj.invoke(null, new Object[0]);
            Field field = cls.getField(fieldname);
            Class<?> fieldtype = field.getType();

            if (!(fieldtype.isPrimitive())) {
                log("ADJUSTING. Field " + fieldname + " in class " + classname + " is not primitive type. Type is "
                    + fieldtype.getName(), true);

                return;
            }

            if (null == result) {
                log("ADJUSTING. Null object returned in getCurrentObject for class " + classname, true);
            }

            String typename = fieldtype.getName();

            if (typename.compareTo(Double.TYPE.getName()) == 0) {
                double val = new Double(value).doubleValue();

                if (field.getDouble(result) != val) {
                    log("ADJUSTING. Field " + fieldname + " has different value.", false);
                }

                field.setDouble(result, val);
            } else if (typename.compareTo(Float.TYPE.getName()) == 0) {
                float val = new Float(value).floatValue();

                if (field.getFloat(result) != val) {
                    log("ADJUSTING. Field " + fieldname + " has different value.", false);
                }

                field.setFloat(result, val);
            } else if (typename.compareTo(Integer.TYPE.getName()) == 0) {
                int val = new Integer(value).intValue();

                if (field.getInt(result) != val) {
                    log("ADJUSTING. Field " + fieldname + " has different value.", false);
                }

                field.setInt(result, val);
            } else if (typename.compareTo(Long.TYPE.getName()) == 0) {
                int val = new Long(value).intValue();

                if (field.getLong(result) != val) {
                    log("ADJUSTING. Field " + fieldname + " has different value.", false);
                }

                field.setLong(result, val);
            } else if (typename.compareTo(String.class.getName()) == 0) {
                if (((String) field.get(result)).compareTo(value) != 0) {
                    log("ADJUSTING. Field " + fieldname + " has different value.", false);
                }

                field.set(result, value);
            }
        } catch (Exception c) {
            log("ADJUSTING. " + c.toString(), true);
        }
    }

    private static void log(String str, boolean is_err) {
        if (is_err) {
            rnr.src.rnrcore.eng.err(str);
        }

        rnr.src.rnrcore.eng.console(str);
    }
}


//~ Formatted in DD Std on 13/08/25
