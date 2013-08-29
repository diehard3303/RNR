/*
 * @(#)Condition.java   13/08/26
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


package rnr.src.menuscript;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.gameobj.CarInfo;
import rnr.src.gameobj.CarParts;
import rnr.src.menu.Common;
import rnr.src.menu.CondTable;
import rnr.src.menu.TruckView;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Condition extends VehiclePopup {
    CondTable m_CondTable;
    PartsCameraTrigger m_PartsTrigger;

    /**
     * Constructs ...
     *
     *
     * @param common
     * @param windowname
     * @param xmlfilename
     * @param controlgroup
     */
    public Condition(Common common, String windowname, String xmlfilename, String controlgroup) {
        super(common, windowname, xmlfilename, controlgroup);
    }

    /**
     * Method description
     *
     *
     * @param controlgroup
     */
    public void AttachConditionTable(String controlgroup) {
        this.m_CondTable = new CondTable(this.common, "Condition Table");

        for (int i = 1; i <= 3; ++i) {
            this.m_CondTable.AddControl(0, i - 1, "VALUES - " + i + "level - BUTTONbrowse");
            this.m_CondTable.AddControl(2, i - 1, "VALUES - " + i + " level - Text Glow");
            this.m_CondTable.AddControl(1, i - 1, "VALUES - " + i + " level - Text");
            this.m_CondTable.AddControl(5, i - 1, "VALUES - " + i + "level - BUTTONconditionRED");
            this.m_CondTable.AddControl(4, i - 1, "VALUES - " + i + "level - BUTTONconditionYELLOW");
            this.m_CondTable.AddControl(3, i - 1, "VALUES - " + i + "level - BUTTONconditionGREEN");
        }

        this.m_PartsTrigger = new PartsCameraTrigger(null, this.m_truckview);
        this.m_CondTable.Setup(38, 7, this.m_xmlfilename, controlgroup, this.m_windowname, this.m_PartsTrigger);
        this.m_CondTable.AttachRanger(this.common.FindScrollerByParent("Tableranger - Condition", this.m_windowname));
    }

    @Override
    protected void AfterInitMenu() {
        super.AfterInitMenu();
        this.m_truckview.BindRepairVehicle();
        this.m_truckview.InitMaterialSwitches();
        this.m_truckview.InitCameraSwitches();
    }

    void AttachCarInfo(CarInfo car) {
        this.m_bWorking = (car != null);

        if (!(this.m_bWorking)) {
            return;
        }

        if (this.m_CondTable != null) {
            car.parts.FillCondTable(this.m_CondTable);
            this.m_CondTable.RefillTree();
        }

        this.m_truckview.AttachCarInfo(car.parts);
    }
}


//~ Formatted in DD Std on 13/08/26
