/*
 * @(#)MultiMaterialControl.java   13/08/25
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

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class MultiMaterialControl {
    boolean vis = true;
    long currentcontrol = 0L;
    HashMap<String, Long> m_controls = new HashMap<String, Long>();
    String name;
    String parentname;
    String controlgroup;
    String filename;
    Common common;

    /**
     * Constructs ...
     *
     *
     * @param common
     * @param name
     * @param parentname
     * @param filename
     * @param controlgroup
     */
    public MultiMaterialControl(Common common, String name, String parentname, String filename, String controlgroup) {
        this.name = name;
        this.parentname = parentname;
        this.controlgroup = controlgroup;
        this.filename = filename;
        this.common = common;
    }

    /**
     * Method description
     *
     *
     * @param materialname
     */
    public void AddMaterial(String materialname) {
        if (this.m_controls.containsKey(materialname)) {
            return;
        }

        long control = menues.InitXmlControl(this.common.GetMenu(), this.filename, this.controlgroup, this.name);
        MENUText_field pic = menues.ConvertTextFields(control);

        pic.parentName = this.parentname;
        menues.UpdateField(pic);
        menues.ChangeAllMaterialsOnControl(this.common.GetMenu(), control, materialname);
        this.m_controls.put(materialname, new Long(control));
    }

    /**
     * Method description
     *
     */
    public void SetIgnoreEvents() {
        for (Iterator<?> it = this.m_controls.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
            Long c = (Long) entry.getValue();

            menues.SetIgnoreEvents(c.longValue(), true);
        }
    }

    /**
     * Method description
     *
     *
     * @param tohide
     */
    public void SetVis(boolean tohide) {
        if (this.vis == tohide) {
            return;
        }

        this.vis = tohide;

        if (this.currentcontrol != 0L) {
            menues.SetShowField(this.currentcontrol, this.vis);
        }
    }

    /**
     * Method description
     *
     *
     * @param materialname
     */
    public void SetMaterial(String materialname) {
        Long l = this.m_controls.get(materialname);

        this.currentcontrol = ((l == null)
                               ? 0L
                               : l.longValue());

        for (Iterator<?> it = this.m_controls.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
            Long c = (Long) entry.getValue();

            menues.SetShowField(c.longValue(), (c.longValue() == this.currentcontrol) && (this.vis));
        }
    }

    /**
     * Method description
     *
     */
    public void HideAll() {
        for (Iterator<?> it = this.m_controls.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
            Long c = (Long) entry.getValue();

            menues.SetShowField(c.longValue(), false);
        }

        this.vis = false;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public MENUText_field GetControl() {
        return menues.ConvertTextFields(this.currentcontrol);
    }
}


//~ Formatted in DD Std on 13/08/25
