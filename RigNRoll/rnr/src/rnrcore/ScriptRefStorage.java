/*
 * @(#)ScriptRefStorage.java   13/08/26
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

import java.util.HashMap;
import java.util.Map;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class ScriptRefStorage {
    private static final int HASH_MAP_CAPACITY = 300;
    private static ScriptRefStorage ourInstance = new ScriptRefStorage();
    private static final Object latch = new Object();
    private final ScriptRefTable refferenceTable = new ScriptRefTable();

    /**
     * Method description
     *
     */
    public void deinit() {
        synchronized (latch) {
            this.refferenceTable.clear();
        }
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void compact() {
        synchronized (latch) {
            UidStorage.getInstance().reset();

            HashMap newTable = new HashMap();

            for (IScriptRef reference : this.refferenceTable.table.values()) {
                int newUid = UidStorage.getInstance().getUid();

                reference.setUid(newUid);
                newTable.put(Integer.valueOf(newUid), reference);
            }

            ScriptRefTable.access$202(this.refferenceTable, newTable);

            if (!(eng.noNative)) {
                eng.reloadScriptObjectsUids();
            }
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static ScriptRefStorage getInstance() {
        return ourInstance;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static Map<Integer, IScriptRef> getRefferaceTable() {
        return ourInstance.refferenceTable.table;
    }

    /**
     * Method description
     *
     */
    public static void clearRefferaceTable() {
        ourInstance.refferenceTable.clear();
    }

    /**
     * Method description
     *
     *
     * @param uid
     *
     * @return
     */
    public static IScriptRef getRefference(int uid) {
        return getInstance().refferenceTable.getReference(uid);
    }

    /**
     * Method description
     *
     *
     * @param target
     *
     * @return
     */
    public static boolean addRefference(IScriptRef target) {
        synchronized (latch) {
            if (getInstance().refferenceTable.referenceExists(target)) {
                return false;
            }

            getInstance().refferenceTable.addReference(target);

            return true;
        }
    }

    /**
     * Method description
     *
     *
     * @param target
     *
     * @return
     */
    public static boolean removeRefference(IScriptRef target) {
        return getInstance().refferenceTable.removeReference(target);
    }

    private static final class ScriptRefTable {
        private final Map<Integer, IScriptRef> table;

        private ScriptRefTable() {
            this.table = new HashMap(300);
        }

        void addReference(IScriptRef target) {
            synchronized (ScriptRefStorage.latch) {
                if (null != target) {
                    int id = target.getUid();

                    if (id == 0) {
                        Log.simpleMessage("addReference - id == 0");
                    }

                    this.table.put(Integer.valueOf(id), target);
                } else {
                    Log.simpleMessage("addReference - null");
                }
            }
        }

        boolean referenceExists(IScriptRef target) {
            synchronized (ScriptRefStorage.latch) {
                return this.table.containsKey(Integer.valueOf(target.getUid()));
            }
        }

        boolean removeReference(IScriptRef target) {
            synchronized (ScriptRefStorage.latch) {
                if (null != target) {
                    int id = target.getUid();

                    return (null != this.table.remove(Integer.valueOf(id)));
                }

                return false;
            }
        }

        IScriptRef getReference(int id) {
            synchronized (ScriptRefStorage.latch) {
                if (this.table.containsKey(Integer.valueOf(id))) {
                    return (this.table.get(Integer.valueOf(id)));
                }

                Log.simpleMessage("getReference(" + id + ") not found");

                return null;
            }
        }

        /**
         * Method description
         *
         */
        public void clear() {
            synchronized (ScriptRefStorage.latch) {
                this.table.clear();
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/26
