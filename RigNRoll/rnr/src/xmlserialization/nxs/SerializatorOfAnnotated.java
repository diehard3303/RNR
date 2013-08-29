/*
 * @(#)SerializatorOfAnnotated.java   13/08/28
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

import rnr.src.rnrcore.CoreTime;
import rnr.src.scenarioUtils.Pair;
import rnr.src.xmlserialization.Helper;
import rnr.src.xmlserialization.Log;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ
 */
public final class SerializatorOfAnnotated {
    private static final String CONSTRUCTOR_ARGUMENT_INDEX_ATTR = "constructor_argument_index";
    private static SerializatorOfAnnotated instance;

    static {
        instance = new SerializatorOfAnnotated();
    }

    private final List<Pair<String, String>> nodeAttributesBuffer;
    private final Map<String, StateLoadDescription> classNamesResolveTable;
    private boolean wereErrorsDuringLastSerialization;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public SerializatorOfAnnotated() {
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(Integer.TYPE) {
            @Override
            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.setInt(fieldHost, Integer.parseInt(data));
            }
        });
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(Double.TYPE) {
            @Override
            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.setDouble(fieldHost, Double.parseDouble(data));
            }
        });
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(Float.TYPE) {
            @Override
            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.setFloat(fieldHost, Float.parseFloat(data));
            }
        });
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(Boolean.TYPE) {
            @Override
            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.setBoolean(fieldHost, Boolean.parseBoolean(data));
            }
        });
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(String.class) {
            @Override
            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.set(fieldHost, data);
            }
        });
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(CoreTime.class) {
            @Override
            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.set(fieldHost, new CoreTime(data));
            }
        });
        this.nodeAttributesBuffer = new ArrayList();
        this.classNamesResolveTable = new HashMap();
        this.wereErrorsDuringLastSerialization = false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static SerializatorOfAnnotated getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static SerializatorOfAnnotated resetInstance() {
        instance = new SerializatorOfAnnotated();

        return instance;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static <T extends Annotation> void visitAllAnnotatedHierarchyFields(Class<T> desired, Class hierarchyLeaf,
            FieldVisitor<T> visitor) {
        assert((null != desired) && (null != hierarchyLeaf));

        List parents = new ArrayList();
        Class parent = hierarchyLeaf.getSuperclass();

        while (null != parent) {
            parents.add(parent);
            parent = parent.getSuperclass();
        }

        parents.add(hierarchyLeaf);

        for (Class classHierarchyElement : parents) {
            for (Field statePart : classHierarchyElement.getDeclaredFields()) {
                Annotation stateAnnotation = statePart.getAnnotation(desired);

                if (null == stateAnnotation) {
                    continue;
                }

                visitor.visit(statePart, (T) stateAnnotation);
            }
        }
    }

    Map<String, StateLoadDescription> getStateLoadingDescriptions() {
        return Collections.unmodifiableMap(this.classNamesResolveTable);
    }

    /**
     * Method description
     *
     *
     * @param id
     * @param clazz
     */
    public void register(String id, Class<? extends AnnotatedSerializable> clazz) {
        if ((null != id) && (null != clazz) && (null == this.classNamesResolveTable.get(id))) {
            this.classNamesResolveTable.put(id, new StateLoadDescription(clazz));
        } else {
            Log.error("NXS-init: trying to register invalid class");
        }
    }

    @SuppressWarnings("unused")
    private static String getTextContent(rnr.src.xmlutils.Node from) {
        return from.getNode().getTextContent();
    }

    private void loadStateFor(AnnotatedSerializable target, StateLoadDescription metaData, NodeList stateStore) {
        assert((null != target) && (null != metaData));

        if (null == stateStore) {
            return;
        }

        Map<String, StateRecordLoader> stateLoaders = metaData.getStateLoaders();

        for (rnr.src.xmlutils.Node stateRecord : stateStore) {
            StateRecordLoader loader = stateLoaders.get(stateRecord.getName());

            if (null != loader) {
                String content = getTextContent(stateRecord);

                loader.load(target, (0 < content.length())
                                    ? content
                                    : null);
            } else {
                Log.warning(String.format("NXS-load: no loader found for node '%s'",
                                          new Object[] { stateRecord.getName() }));
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param from
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public AnnotatedSerializable loadStateOrNull(rnr.src.xmlutils.Node from) {
        AnnotatedSerializable restored = null;

        try {
            if (null != from) {
                String classUid = from.getName();
                NodeList stateRecords = from.getChildren();

                assert((null != classUid) && (null != stateRecords));

                StateLoadDescription classInstanceRestoreDescription = this.classNamesResolveTable.get(classUid);

                if (null != classInstanceRestoreDescription) {
                    Constructor<AnnotatedSerializable> nonTrivialConstructor =
                        classInstanceRestoreDescription.getNonTrivialConstructor();

                    if (null != nonTrivialConstructor) {
                        assert(0 < nonTrivialConstructor.getParameterTypes().length);

                        RemapSerializedArguments remappingDescription =
                            nonTrivialConstructor.getAnnotation(RemapSerializedArguments.class);
                        Class[] argumentsTypes = nonTrivialConstructor.getParameterTypes();
                        Set argumentsToLoad = getRecordsForConstructorIds(nonTrivialConstructor, remappingDescription);
                        TreeMap constructorArgumentsData = new TreeMap();

                        for (Iterator stateRecordIterator = stateRecords.iterator(); stateRecordIterator.hasNext(); ) {
                            rnr.src.xmlutils.Node stateRecord = (rnr.src.xmlutils.Node) stateRecordIterator.next();
                            int constructorArgumentIndex =
                                Integer.parseInt(stateRecord.getAttribute("constructor_argument_index"));

                            if (argumentsToLoad.remove(Integer.valueOf(constructorArgumentIndex))) {
                                constructorArgumentsData.put(Integer.valueOf(constructorArgumentIndex),
                                                             getTextContent(stateRecord));
                                stateRecordIterator.remove();
                            }
                        }

                        Object[] constructorArguments = new Object[nonTrivialConstructor.getParameterTypes().length];
                        int argumentIndex;

                        if (null != remappingDescription) {
                            assert(remappingDescription.newArguments().length
                                   == nonTrivialConstructor.getParameterTypes().length);

                            for (int i = 0; i < remappingDescription.newArguments().length; ++i) {
                                constructorArguments[i] = unmarshal(
                                    argumentsTypes[i],
                                    (String) constructorArgumentsData.get(
                                        Integer.valueOf(remappingDescription.newArguments()[i])));
                            }
                        } else {
                            argumentIndex = 0;

                            for (String argumentData : constructorArgumentsData.values()) {
                                constructorArguments[argumentIndex] = unmarshal(argumentsTypes[argumentIndex],
                                        argumentData);
                                ++argumentIndex;
                            }
                        }

                        nonTrivialConstructor.setAccessible(true);
                        restored = nonTrivialConstructor.newInstance(constructorArguments);
                    } else {
                        restored = classInstanceRestoreDescription.getRestoredClass().getDeclaredConstructor(
                            new Class[0]).newInstance(new Object[0]);
                    }

                    loadStateFor(restored, classInstanceRestoreDescription, stateRecords);
                } else {
                    Log.error(String.format("NXS-load: class for id %s was not registered in %s",
                                            new Object[] { classUid,
                            super.getClass().getName() }));
                }
            }
        } catch (NumberFormatException e) {
            Log.error(String.format("NXS-load: number data format error: %s", new Object[] { e.getMessage() }));
        } catch (NullPointerException e) {
            Log.error(String.format("NXS-load: xml structure error: %s", new Object[] { e.getMessage() }));
        } catch (InstantiationException e) {
            Log.error(String.format("NXS-load: AnnotatedSerializable implementation error: %s",
                                    new Object[] { e.getMessage() }));
        } catch (IllegalAccessException e) {
            Log.error(String.format("NXS-load: AnnotatedSerializable implementation error: %s",
                                    new Object[] { e.getMessage() }));
        } catch (InvocationTargetException e) {
            Log.error(String.format("NXS-load: AnnotatedSerializable implementation error: %s",
                                    new Object[] { e.getMessage() }));
        } catch (NoSuchMethodException e) {
            Log.error(String.format("NXS-load: AnnotatedSerializable implementation error: %s",
                                    new Object[] { e.getMessage() }));
        }

        if (null != restored) {
            restored.finilizeDeserialization();
        }

        return restored;
    }

    private Object unmarshal(Class to, String from) {
        assert((null != to) && (null != from));

        if (Integer.TYPE.equals(to)) {
            return Integer.valueOf(Integer.parseInt(from));
        }

        if (Double.TYPE.equals(to)) {
            return Double.valueOf(Double.parseDouble(from));
        }

        if (Float.TYPE.equals(to)) {
            return Float.valueOf(Float.parseFloat(from));
        }

        if (Boolean.TYPE.equals(to)) {
            return Boolean.valueOf(Boolean.parseBoolean(from));
        }

        if (String.class.equals(to)) {
            return from;
        }

        if (CoreTime.class.equals(to)) {
            return new CoreTime(from);
        }

        Log.error(String.format("NXS-save: failed to unmarshal string '%s' into object of class '%s'",
                                new Object[] { from,
                to.getName() }));

        return null;
    }

    private static Set<Integer> getRecordsForConstructorIds(Constructor<AnnotatedSerializable> nonTrivialConstructor,
            RemapSerializedArguments remappingDescription) {
        assert(null != nonTrivialConstructor);

        Set argumentsToLoad = new HashSet();

        if (null != remappingDescription) {
            for (int i = 0; i < remappingDescription.newArguments().length; ++i) {
                argumentsToLoad.add(Integer.valueOf(remappingDescription.newArguments()[i]));
            }
        } else {
            for (int i = 0; i < nonTrivialConstructor.getParameterTypes().length; ++i) {
                argumentsToLoad.add(Integer.valueOf(i));
            }
        }

        return argumentsToLoad;
    }

    /**
     * Method description
     *
     *
     * @param where
     * @param what
     */
    public void saveState(PrintStream where, AnnotatedSerializable what) {
        assert((null != where) && (null != what));
        this.wereErrorsDuringLastSerialization = false;
        Helper.openNode(where, what.getId());

        try {
            saveMethods(where, what);
            saveFields(where, what);
        } finally {
            Helper.closeNode(where, what.getId());
        }
    }

    boolean wereErrorsDuringLastSerialization() {
        return this.wereErrorsDuringLastSerialization;
    }

    @SuppressWarnings("unchecked")
    private void saveFields(PrintStream where, AnnotatedSerializable what) {
        assert((null != where) && (null != what));
        visitAllAnnotatedHierarchyFields(SaveTo.class, what.getClass(), new FieldVisitor(where, what) {
            public void visit(Field target, SaveTo annotation) {
                String nodeName = annotation.destinationNodeName();
                int constructorArgumentIndex = annotation.constructorArgumentNumber();

                try {
                    SerializatorOfAnnotated.this.nodeAttributesBuffer.clear();
                    SerializatorOfAnnotated.this.nodeAttributesBuffer.add(new Pair("constructor_argument_index",
                            Integer.toString(constructorArgumentIndex)));
                    target.setAccessible(true);
                    this.val$where.print('<'
                                         + Helper.printNodeWithAttributes(nodeName,
                                             SerializatorOfAnnotated.this.nodeAttributesBuffer) + '>');

                    Object toSave = target.get(this.val$what);

                    if (null != toSave) {
                        this.val$where.print(toSave.toString());
                    }

                    Helper.closeNode(this.val$where, nodeName);
                } catch (IllegalAccessException e) {
                    SerializatorOfAnnotated.access$302(SerializatorOfAnnotated.this, true);
                    Log.error(String.format("NXS-save: failed to save field '%s' for instance of '%s': %s",
                                            new Object[] { this.val$what.getClass().getName(),
                            target.getName(), e.getMessage() }));
                }
            }
        });
    }

    private void saveMethods(PrintStream where, AnnotatedSerializable what) {
        assert((null != where) && (null != what));

        for (Method stateSaver : what.getClass().getMethods()) {
            SaveTo saveToAnnotation = stateSaver.getAnnotation(SaveTo.class);

            if (null == saveToAnnotation) {
                continue;
            }

            if (Void.TYPE.equals(stateSaver.getReturnType())) {
                this.wereErrorsDuringLastSerialization = true;
                Log.error(String.format("NXS-save: state getter method '%s' of class '%s' returns void!",
                                        new Object[] { stateSaver.getName(),
                        what.getClass().getName() }));
            } else if (0 < stateSaver.getParameterTypes().length) {
                this.wereErrorsDuringLastSerialization = true;
                Log.error(String.format("NXS-save: state getter method '%s' of class '%s' has arguments!",
                                        new Object[] { stateSaver.getName(),
                        what.getClass().getName() }));
            } else {
                String nodeName = saveToAnnotation.destinationNodeName();
                int constructorArgumentIndex = saveToAnnotation.constructorArgumentNumber();

                try {
                    Object toSave = stateSaver.invoke(what, new Object[0]);

                    this.nodeAttributesBuffer.clear();
                    this.nodeAttributesBuffer.add(new Pair("constructor_argument_index",
                            Integer.toString(constructorArgumentIndex)));
                    where.print('<' + Helper.printNodeWithAttributes(nodeName, this.nodeAttributesBuffer) + '>');

                    if (null != toSave) {
                        where.print(toSave.toString());
                    }

                    Helper.closeNode(where, nodeName);
                } catch (IllegalAccessException e) {
                    this.wereErrorsDuringLastSerialization = true;
                    Log.error(String.format("NXS-save: failed to save method '%s' return for instance of '%s': %s",
                                            new Object[] { what.getClass().getName(),
                            stateSaver.getName(), e.getMessage() }));
                } catch (InvocationTargetException e) {
                    this.wereErrorsDuringLastSerialization = true;
                    Log.error(String.format("NXS-save: failed to save method '%s' return for instance of '%s': %s",
                                            new Object[] { what.getClass().getName(),
                            stateSaver.getName(), e.getMessage() }));
                }
            }
        }
    }

    private static abstract interface FieldVisitor<T extends Annotation> {

        /**
         * Method description
         *
         *
         * @param paramField
         * @param paramT
         */
        public abstract void visit(Field paramField, T paramT);
    }


    static final class StateLoadDescription {
        private final Map<String, StateRecordLoader> stateLoaders = new HashMap();
        private Constructor<AnnotatedSerializable> nonTrivialConstructor = null;
        private final Class<? extends AnnotatedSerializable> restoredClass;

        StateLoadDescription(Class<? extends AnnotatedSerializable> restoredClass) {
            assert(null != restoredClass);
            this.restoredClass = restoredClass;
            findAnnotatedForLoadingFields();
            finadAnnotatedForLoadingMethods();
            findNonTrivialConstructor();
        }

        private void findAnnotatedForLoadingFields() {
            SerializatorOfAnnotated.access$100(LoadFrom.class, this.restoredClass,
                                               new SerializatorOfAnnotated.FieldVisitor() {
                public void visit(Field target, LoadFrom annotation) {
                    SerializatorOfAnnotated.StateLoadDescription.this.stateLoaders.put(annotation.sourceNodeName(),
                            new SetFieldStateLoader(target));
                }
            });
        }

        private void finadAnnotatedForLoadingMethods() {
            for (Method stateLoader : this.restoredClass.getMethods()) {
                LoadFrom stateAnnotation = stateLoader.getAnnotation(LoadFrom.class);

                if (null == stateAnnotation) {
                    continue;
                }

                if ((1 != stateLoader.getParameterTypes().length)
                        || (!(String.class.equals(stateLoader.getParameterTypes()[0])))) {
                    Log.warning(
                        String.format(
                            "NXS-init: found annotated method with bad signature: class=%s method=%s",
                            new Object[] { this.restoredClass.getName(),
                                           stateLoader.getName() }));
                }

                this.stateLoaders.put(stateAnnotation.sourceNodeName(), new InvokeMethodStateLoader(stateLoader));
            }
        }

        private void findNonTrivialConstructor() {
            for (Constructor stateInitiator : this.restoredClass.getDeclaredConstructors()) {
                if ((null == stateInitiator.getAnnotation(DeserializationConstructor.class))
                        || (0 == stateInitiator.getParameterTypes().length)) {
                    continue;
                }

                this.nonTrivialConstructor = stateInitiator;

                return;
            }
        }

        Constructor<AnnotatedSerializable> getNonTrivialConstructor() {
            return this.nonTrivialConstructor;
        }

        Class<? extends AnnotatedSerializable> getRestoredClass() {
            return this.restoredClass;
        }

        Map<String, StateRecordLoader> getStateLoaders() {
            return this.stateLoaders;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
