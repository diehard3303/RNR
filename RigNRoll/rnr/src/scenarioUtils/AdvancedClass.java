/*
 * @(#)AdvancedClass.java   13/08/27
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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/27
 * @author         TJ    
 */
public final class AdvancedClass {
    private Class<?> internal = null;

    /**
     * Constructs ...
     *
     *
     * @param className
     * @param packegesToSearch
     *
     * @throws ClassNotFoundException
     */
    public AdvancedClass(String className, String[] packegesToSearch) throws ClassNotFoundException {
        if (null == className) {
            throw new IllegalArgumentException("className must be non-null reference");
        }

        for (String packageName : packegesToSearch) {
            try {
                this.internal = Class.forName(packageName + '.' + className);
            } catch (ClassNotFoundException ex) {}
        }

        if (null != this.internal) {
            return;
        }

        throw new ClassNotFoundException(className + "wasn't found");
    }

    /**
     * Method description
     *
     *
     * @param fieldName
     *
     * @return
     *
     * @throws NoSuchFieldException
     */
    public Field findFieldInHierarchy(String fieldName) throws NoSuchFieldException {
        Class<?> hierarchyElement = this.internal;

        do {
            try {
                return hierarchyElement.getDeclaredField(fieldName);
            } catch (NoSuchFieldException exception) {
                hierarchyElement = hierarchyElement.getSuperclass();
            }
        } while (Object.class != hierarchyElement);

        throw new NoSuchFieldException("Field " + fieldName + " wasn't found in class " + this.internal.getName()
                                       + " and in all its ancestors");
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Constructor[] getAllConstructors() {
        return this.internal.getDeclaredConstructors();
    }

    /**
     * Method description
     *
     *
     * @param methodName
     *
     * @return
     */
    public Method findMethodWithoutParameters(String methodName) {
        try {
            return this.internal.getMethod(methodName, new Class[0]);
        } catch (NoSuchMethodException exception) {}

        return null;
    }

    /**
     * Method description
     *
     *
     * @param methodName
     * @param params
     *
     * @return
     *
     * @throws NoSuchMethodException
     */
    public Method findMethodInHierarchy(String methodName, Class[] params) throws NoSuchMethodException {
        Class<?> hierarchyElement = this.internal;

        do {
            try {
                return hierarchyElement.getDeclaredMethod(methodName, params);
            } catch (NoSuchMethodException exception) {
                hierarchyElement = hierarchyElement.getSuperclass();
            }
        } while (Object.class != hierarchyElement);

        throw new NoSuchMethodException("method " + methodName + " wasn't found in class " + this.internal.getName()
                                        + " and in all its ancestors");
    }

    /**
     * Method description
     *
     *
     * @param params
     *
     * @return
     *
     * @throws InstantiationException
     */
    public Object callConstructor(Object[] params) throws InstantiationException {
        try {
            Constructor<?> creator = getConstructor(params);

            creator.setAccessible(true);

            return creator.newInstance(params);
        } catch (SecurityException e) {
            throw new InstantiationException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new InstantiationException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new InstantiationException(e.getMessage());
        } catch (InstantiationException e) {
            throw new InstantiationException(e.getMessage());
        }
    }

    /**
     * Method description
     *
     *
     * @param paramsTypes
     *
     * @return
     *
     * @throws NoSuchMethodException
     */
    public Constructor<?> getConstructor(Class[] paramsTypes) throws NoSuchMethodException {
        return this.internal.getConstructor(paramsTypes);
    }

    /**
     * Method description
     *
     *
     * @param params
     *
     * @return
     *
     * @throws InstantiationException
     */
    @SuppressWarnings("rawtypes")
    public Constructor<?> getConstructor(Object[] params) throws InstantiationException {
        try {
            Class[] paramsClasses = new Class[params.length];

            for (int i = 0; i < params.length; ++i) {
                if (null != params[i]) {
                    Object parametrObject = params[i];

                    if (parametrObject instanceof Integer) {
                        paramsClasses[i] = Integer.TYPE;
                    } else if (parametrObject instanceof Character) {
                        paramsClasses[i] = Character.TYPE;
                    } else if (parametrObject instanceof Byte) {
                        paramsClasses[i] = Byte.TYPE;
                    } else if (parametrObject instanceof Short) {
                        paramsClasses[i] = Short.TYPE;
                    } else if (parametrObject instanceof Long) {
                        paramsClasses[i] = Long.TYPE;
                    } else if (parametrObject instanceof Float) {
                        paramsClasses[i] = Float.TYPE;
                    } else if (parametrObject instanceof Double) {
                        paramsClasses[i] = Double.TYPE;
                    } else if (parametrObject instanceof Void) {
                        paramsClasses[i] = Void.TYPE;
                    } else {
                        paramsClasses[i] = parametrObject.getClass();
                    }
                } else {
                    throw new IllegalArgumentException("all parameters must be non-null");
                }
            }

            return this.internal.getDeclaredConstructor(paramsClasses);
        } catch (SecurityException e) {
            throw new InstantiationException(e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new InstantiationException(e.getMessage());
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Class<?> getInternal() {
        return this.internal;
    }
}


//~ Formatted in DD Std on 13/08/27
