/*
 * @(#)ParametrsBlock.java   13/08/26
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


package rnr.src.menuscript.parametrs;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.Log;
import rnr.src.rnrcore.Modifier;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class ParametrsBlock {
    private final HashMap<String, IParametr> params;

    /**
     * Constructs ...
     *
     */
    @SuppressWarnings("unchecked")
    public ParametrsBlock() {
        this.params = new HashMap<String, IParametr>();
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param value
     * @param defaul_value
     * @param changer
     */
    public void addParametr(String name, boolean value, boolean defaul_value, IBooleanValueChanger changer) {
        this.params.put(name, new BooleanParametr(value, defaul_value, changer));
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param value
     * @param defaul_value
     * @param changer
     */
    public void addParametr(String name, int value, int defaul_value, IIntegerValueChanger changer) {
        this.params.put(name, new IntegerParametr(value, defaul_value, changer));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public ParametrsBlock makeMemo() {
        ParametrsBlock res = new ParametrsBlock();
        Set<?> _set = this.params.entrySet();
        Iterator<?> iter = _set.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            IParametr p = (IParametr) entry.getValue();

            if (p.isBoolean()) {
                res.addParametr((String) entry.getKey(), p.getBoolean(), false, null);
            } else if (p.isInteger()) {
                res.addParametr((String) entry.getKey(), p.getInteger(), 0, null);
            }
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param block
     */
    public void recordMemoChanges(ParametrsBlock block) {
        Set<?> _set = this.params.entrySet();
        Iterator<?> iter = _set.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            IParametr p = (IParametr) entry.getValue();

            if (p.isBoolean()) {
                block.setBooleanValueChange((String) entry.getKey(), p.getBoolean());
            } else if (p.isInteger()) {
                block.setIntegerValueChange((String) entry.getKey(), p.getInteger());
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param memo
     */
    public void restoreMemo(ParametrsBlock memo) {
        Set<?> _set = memo.params.entrySet();
        Iterator<?> iter = _set.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            IParametr p = (IParametr) entry.getValue();

            if (p.isBoolean()) {
                setBooleanValue((String) entry.getKey(), p.getBoolean());
            } else if (p.isInteger()) {
                setIntegerValue((String) entry.getKey(), p.getInteger());
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param block
     */
    public void restoreMemoChanges(ParametrsBlock block) {
        Set<?> _set = block.params.entrySet();
        Iterator<?> iter = _set.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            IParametr p = (IParametr) entry.getValue();

            if (p.isBoolean()) {
                setBooleanValueChange((String) entry.getKey(), p.getBooleanChange());
            } else if (p.isInteger()) {
                setIntegerValueChange((String) entry.getKey(), p.getIntegerChange());
            }
        }
    }

    /**
     * Method description
     *
     */
    public void onInit() {
        Set<?> _set = this.params.entrySet();
        Iterator<?> iter = _set.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            ((IParametr) entry.getValue()).updateDefault();
        }
    }

    /**
     * Method description
     *
     */
    public void onUpdate() {
        Set<?> _set = this.params.entrySet();
        Iterator<?> iter = _set.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            ((IParametr) entry.getValue()).update();
        }
    }

    /**
     * Method description
     *
     */
    public void onDefault() {
        Set<?> _set = this.params.entrySet();
        Iterator<?> iter = _set.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            ((IParametr) entry.getValue()).makeDefault();
        }
    }

    /**
     * Method description
     *
     *
     * @param visitor
     */
    public void visitAllParameters(Modifier<Map.Entry<String, IParametr>> visitor) {
        for (Map.Entry a_set : this.params.entrySet()) {
            visitor.modify(a_set);
        }
    }

    /**
     * Method description
     *
     */
    public void onOk() {
        Set<?> _set = this.params.entrySet();
        Iterator<?> iter = _set.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            ((IParametr) entry.getValue()).readFromChanger(true);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean areValuesChanged() {
        Set<?> _set = this.params.entrySet();
        Iterator<?> iter = _set.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            if (((IParametr) entry.getValue()).changed()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param name_param
     *
     * @return
     */
    public int getIntegerValue(String name_param) {
        if (!(this.params.containsKey(name_param))) {
            Log.menu("ParametrsBlock. getInegerValue. Cannot find value named " + name_param);

            return -1;
        }

        IParametr param = this.params.get(name_param);

        if (!(param.isInteger())) {
            Log.menu("ParametrsBlock. getInegerValue. Parametr " + name_param + " is not integer");

            return -1;
        }

        return param.getInteger();
    }

    /**
     * Method description
     *
     *
     * @param name_param
     * @param value
     */
    public void setIntegerValue(String name_param, int value) {
        if (!(this.params.containsKey(name_param))) {
            Log.menu("ParametrsBlock. setInegerValue. cannot find value named " + name_param);

            return;
        }

        IParametr param = this.params.get(name_param);

        if (!(param.isInteger())) {
            Log.menu("ParametrsBlock. setInegerValue. Parametr " + name_param + " is not integer");

            return;
        }

        param.setInteger(value);
    }

    /**
     * Method description
     *
     *
     * @param name_param
     * @param value
     */
    public void setIntegerValueDefault(String name_param, int value) {
        if (!(this.params.containsKey(name_param))) {
            Log.menu("ParametrsBlock. setIntegerValueDefault. cannot find value named " + name_param);

            return;
        }

        IParametr param = this.params.get(name_param);

        if (!(param.isInteger())) {
            Log.menu("ParametrsBlock. setIntegerValueDefault. Parametr " + name_param + " is not integer");

            return;
        }

        param.setIntegerDefault(value);
    }

    /**
     * Method description
     *
     *
     * @param name_param
     * @param value
     */
    public void setIntegerValueChange(String name_param, int value) {
        if (!(this.params.containsKey(name_param))) {
            Log.menu("ParametrsBlock. setInegerValue. cannot find value named " + name_param);

            return;
        }

        IParametr param = this.params.get(name_param);

        if (!(param.isInteger())) {
            Log.menu("ParametrsBlock. setInegerValue. Parametr " + name_param + " is not integer");

            return;
        }

        param.setIntegerChange(value);
    }

    /**
     * Method description
     *
     *
     * @param name_param
     *
     * @return
     */
    public int getIntegerValueChange(String name_param) {
        if (!(this.params.containsKey(name_param))) {
            Log.menu("ParametrsBlock. setInegerValue. cannot find value named " + name_param);

            return 0;
        }

        IParametr param = this.params.get(name_param);

        if (!(param.isInteger())) {
            Log.menu("ParametrsBlock. setInegerValue. Parametr " + name_param + " is not integer");

            return 0;
        }

        param.readFromChanger(false);

        return param.getIntegerChange();
    }

    /**
     * Method description
     *
     *
     * @param name_param
     *
     * @return
     */
    public boolean getBooleanValue(String name_param) {
        if (!(this.params.containsKey(name_param))) {
            Log.menu("ParametrsBlock. getBooleanValue. Cannot find value named " + name_param);

            return false;
        }

        IParametr param = this.params.get(name_param);

        if (!(param.isBoolean())) {
            Log.menu("ParametrsBlock. getBooleanValue. Parametr " + name_param + " is not boolean");

            return false;
        }

        return param.getBoolean();
    }

    /**
     * Method description
     *
     *
     * @param name_param
     * @param value
     */
    public void setBooleanValue(String name_param, boolean value) {
        if (!(this.params.containsKey(name_param))) {
            Log.menu("ParametrsBlock. setBooleanValue. cannot find value named " + name_param);

            return;
        }

        IParametr param = this.params.get(name_param);

        if (!(param.isBoolean())) {
            Log.menu("ParametrsBlock. setBooleanValue. Parametr " + name_param + " is not boolean");

            return;
        }

        param.setBoolean(value);
    }

    /**
     * Method description
     *
     *
     * @param name_param
     * @param value
     */
    public void setBooleanValueDefault(String name_param, boolean value) {
        if (!(this.params.containsKey(name_param))) {
            Log.menu("ParametrsBlock. setBooleanValueDefault. cannot find value named " + name_param);

            return;
        }

        IParametr param = this.params.get(name_param);

        if (!(param.isBoolean())) {
            Log.menu("ParametrsBlock. setBooleanValueDefault. Parametr " + name_param + " is not boolean");

            return;
        }

        param.setBooleanDefault(value);
    }

    private void setBooleanValueChange(String name_param, boolean value) {
        if (!(this.params.containsKey(name_param))) {
            Log.menu("ParametrsBlock. setBooleanValue. cannot find value named " + name_param);

            return;
        }

        IParametr param = this.params.get(name_param);

        if (!(param.isBoolean())) {
            Log.menu("ParametrsBlock. setBooleanValue. Parametr " + name_param + " is not boolean");

            return;
        }

        param.setBooleanChange(value);
    }
}


//~ Formatted in DD Std on 13/08/26
