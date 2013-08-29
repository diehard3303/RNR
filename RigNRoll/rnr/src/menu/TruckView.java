/*
 * @(#)TruckView.java   13/08/26
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

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.gameobj.CarInfo;
import rnr.src.gameobj.CarParts;
import rnr.src.rnrcore.SCRuniperson;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class TruckView {

    /** Field description */
    public static final int VIEW_CONDITION = 10;

    /** Field description */
    public static final int VIEW_UNLOADED = -1;
    MENUTruckview m_truckview;
    int state;
    @SuppressWarnings("rawtypes")
    private final Vector m_Switches;
    @SuppressWarnings("rawtypes")
    private final Vector m_Cameras;

    /**
     * Constructs ...
     *
     *
     * @param control
     */
    @SuppressWarnings("rawtypes")
    public TruckView(MENUTruckview control) {
        this.state = -1;
        this.m_truckview = control;
        this.m_Switches = new Vector();
        this.m_Cameras = new Vector();
    }

    /**
     * Method description
     *
     *
     * @param parts
     */
    public void AttachCarInfo(CarParts parts) {
        for (int i = 0; i < this.m_Switches.size(); ++i) {
            SwitchInfo info = (SwitchInfo) this.m_Switches.get(i);
            int state = parts.GetItem(info.partsid).condition;

            this.m_truckview.SetState(1, info.switchid, state);
        }
    }

    /**
     * Method description
     *
     */
    public void BindRepairVehicle() {
        if (this.state == 10) {
            return;
        }

        if (this.state != -1) {
            this.m_truckview.UnBind3DModel();
        }

        this.m_truckview.Bind3DModel("model_Damage_Truck", 1, 2);
        this.state = 10;
    }

    /**
     * Method description
     *
     *
     * @param person
     * @param name
     * @param type
     * @param flags
     */
    public void BindNodePerson(SCRuniperson person, String name, int type, int flags) {
        if (this.state != -1) {
            this.m_truckview.UnBind3DModel();
        }

        this.m_truckview.BindPerson(0L, person, name, type, flags);
        this.state = type;
    }

    /**
     * Method description
     *
     */
    public void UnbindVehicle() {
        if (this.state != -1) {
            this.m_truckview.UnBind3DModel();
        }

        this.state = -1;
    }

    /**
     * Method description
     *
     *
     * @param car
     * @param type
     * @param flags
     */
    public void BindVehicle(CarInfo car, int type, int flags) {
        if (this.state != -1) {
            this.m_truckview.UnBind3DModel();
        }

        this.m_truckview.BindVehicle(car.GetVehiclePointer(), type, flags);
        this.state = type;
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void InitMaterialSwitches() {
        HashMap switches = new HashMap();

        switches.put(Integer.valueOf(97), Integer.valueOf(this.m_truckview.AddSwitch("mat_Engine_110", 1)));
        switches.put(Integer.valueOf(98), Integer.valueOf(this.m_truckview.AddSwitch("mat_CoolingSystem_110", 1)));
        switches.put(Integer.valueOf(99), Integer.valueOf(this.m_truckview.AddSwitch("mat_SteeringSystem_110", 1)));
        switches.put(Integer.valueOf(100), Integer.valueOf(this.m_truckview.AddSwitch("mat_BrakingSystem_110", 1)));
        switches.put(Integer.valueOf(105), Integer.valueOf(this.m_truckview.AddSwitch("mat_FuelSystem_110", 1)));
        switches.put(Integer.valueOf(104), Integer.valueOf(this.m_truckview.AddSwitch("mat_TiresRims_110", 1)));
        switches.put(Integer.valueOf(101), Integer.valueOf(this.m_truckview.AddSwitch("mat_DriveTrain_110", 1)));
        switches.put(Integer.valueOf(108), Integer.valueOf(this.m_truckview.AddSwitch("mat_BodyFrame_110", 1)));
        switches.put(Integer.valueOf(106), Integer.valueOf(this.m_truckview.AddSwitch("mat_ExhaustSystem_110", 1)));
        switches.put(Integer.valueOf(103), Integer.valueOf(this.m_truckview.AddSwitch("mat_Suspension_110", 1)));
        switches.put(Integer.valueOf(102), Integer.valueOf(this.m_truckview.AddSwitch("mat_ElectricalSystem_110", 1)));
        switches.put(Integer.valueOf(107), Integer.valueOf(this.m_truckview.AddSwitch("mat_CouplingSystem_110", 1)));
        switches.put(Integer.valueOf(107), Integer.valueOf(this.m_truckview.AddSwitch("mat_CouplingSystem_110", 1)));

        if (switches.containsKey(new Integer(97))) {
            for (int i = 0; i <= 9; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(97))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(97, ((Integer) switches.get(Integer.valueOf(97))).intValue()));
        }

        if (switches.containsKey(new Integer(98))) {
            for (int i = 10; i <= 15; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(98))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(98, ((Integer) switches.get(Integer.valueOf(98))).intValue()));
        }

        if (switches.containsKey(new Integer(99))) {
            for (int i = 16; i <= 21; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(99))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(99, ((Integer) switches.get(Integer.valueOf(99))).intValue()));
        }

        if (switches.containsKey(new Integer(100))) {
            for (int i = 22; i <= 31; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(100))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(100, ((Integer) switches.get(Integer.valueOf(100))).intValue()));
        }

        if (switches.containsKey(new Integer(101))) {
            for (int i = 32; i <= 38; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(101))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(101, ((Integer) switches.get(Integer.valueOf(101))).intValue()));
        }

        if (switches.containsKey(new Integer(102))) {
            for (int i = 39; i <= 45; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(102))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(102, ((Integer) switches.get(Integer.valueOf(102))).intValue()));
        }

        if (switches.containsKey(new Integer(103))) {
            for (int i = 46; i <= 52; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(103))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(103, ((Integer) switches.get(Integer.valueOf(103))).intValue()));
        }

        if (switches.containsKey(new Integer(104))) {
            for (int i = 53; i <= 68; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(104))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(104, ((Integer) switches.get(Integer.valueOf(104))).intValue()));
        }

        if (switches.containsKey(new Integer(105))) {
            for (int i = 69; i <= 76; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(105))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(105, ((Integer) switches.get(Integer.valueOf(105))).intValue()));
        }

        if (switches.containsKey(new Integer(106))) {
            for (int i = 77; i <= 80; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(106))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(106, ((Integer) switches.get(Integer.valueOf(106))).intValue()));
        }

        if (switches.containsKey(new Integer(107))) {
            for (int i = 81; i <= 83; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(107))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(107, ((Integer) switches.get(Integer.valueOf(107))).intValue()));
        }

        if (switches.containsKey(new Integer(107))) {
            for (int i = 81; i <= 83; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(107))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(107, ((Integer) switches.get(Integer.valueOf(107))).intValue()));
        }

        if (switches.containsKey(new Integer(108))) {
            for (int i = 84; i <= 86; ++i) {
                this.m_Switches.add(new SwitchInfo(i, ((Integer) switches.get(Integer.valueOf(108))).intValue()));
            }

            this.m_Switches.add(new SwitchInfo(108, ((Integer) switches.get(Integer.valueOf(108))).intValue()));
        }
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void InitCameraSwitches() {
        HashMap cameras = new HashMap();

        cameras.put(Integer.valueOf(96), Integer.valueOf(this.m_truckview.AddSwitch("Camera_Vehicle", 2)));
        cameras.put(Integer.valueOf(97), Integer.valueOf(this.m_truckview.AddSwitch("CameraEngine", 2)));
        cameras.put(Integer.valueOf(98), Integer.valueOf(this.m_truckview.AddSwitch("CameraCoolingSystem", 2)));
        cameras.put(Integer.valueOf(107), Integer.valueOf(this.m_truckview.AddSwitch("CameraCouplingSystem", 2)));
        cameras.put(Integer.valueOf(102), Integer.valueOf(this.m_truckview.AddSwitch("CameraElectricalSystem", 2)));
        cameras.put(Integer.valueOf(106), Integer.valueOf(this.m_truckview.AddSwitch("CameraExhaustSystem", 2)));
        cameras.put(Integer.valueOf(103), Integer.valueOf(this.m_truckview.AddSwitch("CameraSuspension", 2)));
        cameras.put(Integer.valueOf(104), Integer.valueOf(this.m_truckview.AddSwitch("CameraTiresRims", 2)));
        cameras.put(Integer.valueOf(101), Integer.valueOf(this.m_truckview.AddSwitch("CameraDriveTrain", 2)));
        cameras.put(Integer.valueOf(108), Integer.valueOf(this.m_truckview.AddSwitch("CameraBodyFrame", 2)));
        cameras.put(Integer.valueOf(99), Integer.valueOf(this.m_truckview.AddSwitch("CameraSteeringSystem", 2)));
        cameras.put(Integer.valueOf(100), Integer.valueOf(this.m_truckview.AddSwitch("CameraBrakingSystem", 2)));
        cameras.put(Integer.valueOf(105), Integer.valueOf(this.m_truckview.AddSwitch("CameraFuelSystem", 2)));
        this.m_Cameras.add(new SwitchInfo(96, ((Integer) cameras.get(Integer.valueOf(96))).intValue()));

        if (cameras.containsKey(new Integer(97))) {
            this.m_Cameras.add(new SwitchInfo(97, ((Integer) cameras.get(Integer.valueOf(97))).intValue()));

            for (int i = 0; i <= 9; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(97))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(98))) {
            this.m_Cameras.add(new SwitchInfo(98, ((Integer) cameras.get(Integer.valueOf(98))).intValue()));

            for (int i = 10; i <= 15; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(98))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(99))) {
            this.m_Cameras.add(new SwitchInfo(99, ((Integer) cameras.get(Integer.valueOf(99))).intValue()));

            for (int i = 16; i <= 21; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(99))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(100))) {
            this.m_Cameras.add(new SwitchInfo(100, ((Integer) cameras.get(Integer.valueOf(100))).intValue()));

            for (int i = 22; i <= 31; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(100))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(101))) {
            this.m_Cameras.add(new SwitchInfo(101, ((Integer) cameras.get(Integer.valueOf(101))).intValue()));

            for (int i = 32; i <= 38; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(101))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(102))) {
            this.m_Cameras.add(new SwitchInfo(102, ((Integer) cameras.get(Integer.valueOf(102))).intValue()));

            for (int i = 39; i <= 45; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(102))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(103))) {
            this.m_Cameras.add(new SwitchInfo(103, ((Integer) cameras.get(Integer.valueOf(103))).intValue()));

            for (int i = 46; i <= 52; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(103))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(104))) {
            this.m_Cameras.add(new SwitchInfo(104, ((Integer) cameras.get(Integer.valueOf(104))).intValue()));

            for (int i = 53; i <= 68; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(104))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(105))) {
            this.m_Cameras.add(new SwitchInfo(105, ((Integer) cameras.get(Integer.valueOf(105))).intValue()));

            for (int i = 69; i <= 76; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(105))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(106))) {
            this.m_Cameras.add(new SwitchInfo(106, ((Integer) cameras.get(Integer.valueOf(106))).intValue()));

            for (int i = 77; i <= 80; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(106))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(107))) {
            this.m_Cameras.add(new SwitchInfo(107, ((Integer) cameras.get(Integer.valueOf(107))).intValue()));

            for (int i = 81; i <= 83; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(107))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(107))) {
            this.m_Cameras.add(new SwitchInfo(107, ((Integer) cameras.get(Integer.valueOf(107))).intValue()));

            for (int i = 81; i <= 83; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(107))).intValue()));
            }
        }

        if (cameras.containsKey(new Integer(108))) {
            this.m_Cameras.add(new SwitchInfo(108, ((Integer) cameras.get(Integer.valueOf(108))).intValue()));

            for (int i = 84; i <= 86; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, ((Integer) cameras.get(Integer.valueOf(108))).intValue()));
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param partsid
     */
    public void SetCameraSwitch(int partsid) {
        for (int i = 0; i < this.m_Cameras.size(); ++i) {
            SwitchInfo info = (SwitchInfo) this.m_Cameras.get(i);

            if (info.partsid == partsid) {
                this.m_truckview.SetState(2, info.switchid, 0);

                break;
            }
        }

        boolean bFoundMaterial = false;

        for (int i = 0; i < this.m_Switches.size(); ++i) {
            SwitchInfo info = (SwitchInfo) this.m_Switches.get(i);

            if (info.partsid == partsid) {
                this.m_truckview.SetState(1, info.switchid, 3);
                bFoundMaterial = true;

                break;
            }
        }

        if (!(bFoundMaterial)) {
            this.m_truckview.SetState(1, -1, 3);
        }
    }

    private class SwitchInfo {
        int partsid;
        int switchid;

        SwitchInfo(int paramInt1, int paramInt2) {
            this.partsid = paramInt1;
            this.switchid = paramInt2;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
