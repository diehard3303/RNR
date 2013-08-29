/*
 * @(#)actorveh.java   13/08/26
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


package rnr.src.players;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrconfig.loaddriver;
import rnr.src.rnrcore.IScriptRef;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.rnrcore.SCRcardriver;
import rnr.src.rnrcore.SCRcarpassanger;
import rnr.src.rnrcore.SCRuniperson;
import rnr.src.rnrcore.ScriptRef;
import rnr.src.rnrcore.ScriptRefStorage;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.matrixJ;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrscr.parkingplace;
import rnr.src.xmlserialization.ActorVehSerializator;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class actorveh implements IScriptRef {

    /** Field description */
    public static final int NOVEH = 0;

    /** Field description */
    public static final int ISDRIVER = 1;

    /** Field description */
    public static final int ISPASSANGER = 2;

    /** Field description */
    public static final int NOTUSESHIFT = 4;

    /** Field description */
    public static final int USEPREFIX = 8;

    /** Field description */
    public static final int ISPACK = 16;
    private ScriptRef uid = null;
    private int ai_player;
    private int car;

    /**
     * Constructs ...
     *
     */
    public actorveh() {
        this.ai_player = 0;
        this.car = 0;
        this.uid = new ScriptRef();
    }

    private actorveh(int uId) {
        this.ai_player = 0;
        this.car = 0;
        this.uid = new ScriptRef();
        this.uid.register(uId, this);
    }

    /**
     * Method description
     *
     *
     * @param uId
     *
     * @return
     */
    public static actorveh createScriptRef(int uId) {
        IScriptRef scriptRef = ScriptRefStorage.getRefference(uId);

        if (scriptRef == null) {
            return new actorveh(uId);
        }

        if (scriptRef instanceof actorveh) {
            return ((actorveh) scriptRef);
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void setUid(int value) {
        this.uid.setUid(value);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getUid() {
        return this.uid.getUid(this);
    }

    /**
     * Method description
     *
     */
    public void removeRef() {
        this.uid.removeRef(this);
    }

    /**
     * Method description
     *
     *
     * @param p
     */
    @Override
    public void updateNative(int p) {
        setAi_player(p);
    }

    /**
     * Method description
     *
     */
    public void UpdateCar() {
        this.car = ((0 != this.ai_player)
                    ? GetPlayerCar(this.ai_player)
                    : 0);
    }

    /**
     * Method description
     *
     */
    public void deinitcar() {
        this.car = 0;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean Exists() {
        return (this.ai_player != 0);
    }

    /**
     * Method description
     *
     */
    public void deactivate() {
        if (0 == this.ai_player) {
            return;
        }

        delete_ai_player();
        this.ai_player = 0;
        this.car = 0;
    }

    /**
     * Method description
     *
     *
     * @param player
     *
     * @return
     */
    public SCRcardriver takeDrivernative(aiplayer player) {
        SCRcardriver res_driver = new loaddriver().Init(player);

        UpdateCar();
        res_driver.attachToCar(this.car);
        setModelInCar(player.getModel());

        return res_driver;
    }

    /**
     * Method description
     *
     *
     * @param player
     * @param stateflag
     *
     * @return
     */
    public SCRcarpassanger takePassengernative(aiplayer player, int stateflag) {
        if (((stateflag & 0x2) != 0) && ((stateflag & 0x4) == 0)) {
            SCRcarpassanger res_passanger = new loaddriver().InitPassanger(player, this, stateflag);

            res_passanger.attachToCar(this.car);
            setModelInCar(player.getModel());

            return res_passanger;
        }

        if (((stateflag & 0x2) != 0) && ((stateflag & 0x4) != 0) && ((stateflag & 0x8) == 0)) {
            SCRcarpassanger res_passanger = new loaddriver().InitPassanger_NoShift(player, stateflag);

            res_passanger.attachToCar(this.car);
            setModelInCar(player.getModel());

            return res_passanger;
        }

        eng.err("Errorious takePassenger");

        return null;
    }

    /**
     * Method description
     *
     *
     * @param player
     * @param stateflag
     * @param noshiftPrefix
     *
     * @return
     */
    public SCRcarpassanger takePassengernative(aiplayer player, int stateflag, String noshiftPrefix) {
        if (((stateflag & 0x2) != 0) && ((stateflag & 0x4) != 0) && ((stateflag & 0x8) != 0)) {
            SCRcarpassanger res_passanger = new loaddriver().InitPassanger_NoShift(player, noshiftPrefix, stateflag);

            res_passanger.attachToCar(this.car);
            setModelInCar(player.getModel());

            return res_passanger;
        }

        eng.err("Errorious takePassenger with prefix");

        return null;
    }

    /**
     * Method description
     *
     *
     * @param player
     */
    public void takeDriver(aiplayer player) {
        callTakePassanger(player, 1);
    }

    /**
     * Method description
     *
     *
     * @param player
     */
    public void takePassanger(aiplayer player) {
        callTakePassanger(player, 2);
    }

    /**
     * Method description
     *
     *
     * @param player
     */
    public void takePack(aiplayer player) {
        callTakePassanger(player, 18);
    }

    /**
     * Method description
     *
     *
     * @param player
     */
    public void takePassanger_simple(aiplayer player) {
        callTakePassanger(player, 6);
    }

    /**
     * Method description
     *
     *
     * @param player
     * @param prefix
     */
    public void takePassanger_simple_like(aiplayer player, String prefix) {
        callTakePassanger(player, 14, prefix);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public vectorJ gDir() {
        if (this.car != 0) {
            return eng.GetVehicle_dir(this.car);
        }

        return defaultDirection();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public vectorJ defaultPosition() {
        return new vectorJ();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public vectorJ defaultDirection() {
        return new vectorJ(0.0D, 1.0D, 0.0D);
    }

    void setModelInCar(SCRuniperson charmodel) {
        long drv = eng.GetVehicleDriver(this.car);

        if ((charmodel == null) || (this.car == 0) || (0L == drv)) {
            return;
        }

        vectorJ pos = eng.GetVehicle_steeringwheel_pos(drv);

        if (pos != null) {
            charmodel.SetPosition(pos);
        }

        vectorJ dir = eng.GetVehicle_steeringwheel_dir(drv);

        if (dir != null) {
            charmodel.SetDirection(dir);
        }
    }

    /**
     * Method description
     *
     *
     * @param distance
     */
    public void traceforward(double distance) {
        vectorJ dir = gDir();

        dir.mult(distance);
        autopilotTo(gPosition().oPlusN(dir));
    }

    /**
     * Method description
     *
     *
     * @param paramaiplayer
     * @param paramInt
     */
    public native void callTakePassanger(aiplayer paramaiplayer, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramaiplayer
     * @param paramInt
     * @param paramString
     */
    public native void callTakePassanger(aiplayer paramaiplayer, int paramInt, String paramString);

    /**
     * Method description
     *
     *
     * @param paramaiplayer
     */
    public native void abandoneCar(aiplayer paramaiplayer);

    /**
     * Method description
     *
     *
     * @param paramInt
     *
     * @return
     */
    public native int GetPlayerCar(int paramInt);

    private native void delete_ai_player();

    /**
     * Method description
     *
     */
    public native void setNeverUnloadFlag();

    /**
     * Method description
     *
     */
    public native void leave_target();

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public native void registerCar(String paramString);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public native void SetHidden(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void autopilotTo(vectorJ paramvectorJ);

    /**
     * Method description
     *
     */
    public native void stop_autopilot();

    /**
     * Method description
     *
     *
     * @return
     */
    public native boolean autopilotFinished();

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void teleport(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramparkingplace
     * @param paramInt
     */
    public native void makeParking(parkingplace paramparkingplace, int paramInt);

    /**
     * Method description
     *
     *
     * @param paramparkingplace
     * @param paramDouble
     * @param paramInt
     */
    public native void makeParkingAnimated(parkingplace paramparkingplace, double paramDouble, int paramInt);

    /**
     * Method description
     *
     */
    public native void leaveParking();

    /**
     * Method description
     *
     *
     * @return
     */
    public native parkingplace parked();

    /**
     * Method description
     *
     *
     * @return
     */
    public native boolean isparked();

    /**
     * Method description
     *
     *
     * @param paramparkingplace
     *
     * @return
     */
    public native int lockParkingForMission(parkingplace paramparkingplace);

    /**
     * Method description
     *
     *
     * @param paramparkingplace
     *
     * @return
     */
    public native int hasParkingForMission(parkingplace paramparkingplace);

    /**
     * Method description
     *
     *
     * @param paramparkingplace
     * @param paramInt
     */
    public native void freeParkingForMission(parkingplace paramparkingplace, int paramInt);

    /**
     * Method description
     *
     *
     * @return
     */
    public native matrixJ gMatrix();

    /**
     * Method description
     *
     *
     * @return
     */
    public native matrixJ gMatrixOnRoad();

    /**
     * Method description
     *
     *
     * @return
     */
    public native vectorJ gPosition();

    /**
     * Method description
     *
     *
     * @return
     */
    public native vectorJ gPositionSteerWheel();

    /**
     * Method description
     *
     *
     * @return
     */
    public native vectorJ gPositionSaddle();

    /**
     * Method description
     *
     *
     * @return
     */
    public native vectorJ gVelocity();

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     * @param parammatrixJ
     */
    public native void sPosition(vectorJ paramvectorJ, matrixJ parammatrixJ);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void sPosition(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @param paramactorveh
     *
     * @return
     */
    public native boolean gOvertaken(actorveh paramactorveh);

    /**
     * Method description
     *
     *
     * @param paramInt
     */
    public native void sOnTheRoadLaneAndStop(int paramInt);

    /**
     * Method description
     *
     *
     * @param paramVector
     * @param paramString
     */
    public static native void makerace(Vector paramVector, String paramString);

    /**
     * Method description
     *
     */
    public native void stoprace();

    /**
     * Method description
     *
     *
     * @param paramVector
     * @param paramvectorJ
     * @param paramDouble1
     * @param paramDouble2
     * @param paramInt1
     * @param paramInt2
     */
    public static native void aligncars(Vector paramVector, vectorJ paramvectorJ, double paramDouble1,
            double paramDouble2, int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramVector
     * @param paramString
     * @param paramDouble1
     * @param paramDouble2
     * @param paramInt1
     * @param paramInt2
     */
    public static native void aligncars_inTrajectoryStart(Vector paramVector, String paramString, double paramDouble1,
            double paramDouble2, int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramVector
     * @param paramString
     * @param paramDouble1
     * @param paramDouble2
     * @param paramInt1
     * @param paramInt2
     */
    public static native void aligncars_inTrajectoryFinish(Vector paramVector, String paramString, double paramDouble1,
            double paramDouble2, int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramVector
     * @param paramString
     * @param paramDouble1
     * @param paramDouble2
     * @param paramInt1
     * @param paramInt2
     */
    public static native void aligncars_inRaceStart(Vector paramVector, String paramString, double paramDouble1,
            double paramDouble2, int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramVector
     * @param paramString
     * @param paramDouble1
     * @param paramDouble2
     * @param paramInt1
     * @param paramInt2
     */
    public static native void aligncars_inRaceFinish(Vector paramVector, String paramString, double paramDouble1,
            double paramDouble2, int paramInt1, int paramInt2);

    /**
     * Method description
     *
     *
     * @param paramVector
     * @param paramString
     */
    public static native void autopilotOnTrajectory(Vector paramVector, String paramString);

    /**
     * Method description
     *
     *
     * @param paramsemitrailer
     */
    public native void attach(semitrailer paramsemitrailer);

    /**
     * Method description
     *
     *
     * @param paramsemitrailer
     */
    public native void deattach(semitrailer paramsemitrailer);

    /**
     * Method description
     *
     *
     * @return
     */
    public native semitrailer querryTrailer();

    /**
     * Method description
     *
     *
     * @param paramsemitrailer
     *
     * @return
     */
    public native boolean isTrailerAttachedBySaddle(semitrailer paramsemitrailer);

    /**
     * Method description
     *
     *
     * @param paramDouble
     */
    public native void sVeclocity(double paramDouble);

    /**
     * Method description
     *
     *
     * @param paramvectorJ
     */
    public native void sVeclocity(vectorJ paramvectorJ);

    /**
     * Method description
     *
     *
     * @return
     */
    public native vehicle takeoff_currentcar();

    /**
     * Method description
     *
     *
     * @return
     */
    public native vehicle querryCurrentCar();

    /**
     * Method description
     *
     *
     * @return
     */
    public native double querryCarDamaged();

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public native void setLockPlayer(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public native void setCollideMode(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public native void makeUnloadable(boolean paramBoolean);

    /**
     * Method description
     *
     *
     * @param paramString
     */
    public native void changeEngine(String paramString);

    /**
     * Method description
     *
     */
    public native void loadImmediateForPredefineAnimation();

    /**
     * Method description
     *
     */
    public native void noNeedForPredefineAnimation();

    /**
     * Method description
     *
     */
    public native void switchOffEngine();

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public native void setHandBreak(boolean paramBoolean);

    /**
     * Method description
     *
     */
    public native void releasePedalBrake();

    /**
     * Method description
     *
     */
    public native void detachSemitrailer();

    /**
     * Method description
     *
     */
    public void deleteSemitrailerIfExists() {
        semitrailer semitrailer = querryTrailer();

        if (null == semitrailer) {
            return;
        }

        detachSemitrailer();
        semitrailer.delete();
    }

    /**
     * Method description
     *
     *
     * @param paramsemitrailer
     */
    public native void attachSemitrailer(semitrailer paramsemitrailer);

    /**
     * Method description
     *
     *
     * @param paramBoolean
     */
    public native void showOnMap(boolean paramBoolean);

    /**
     * Method description
     *
     */
    public native void setVisible();

    private native void droppOffAllButOnePassanger(int paramInt);

    /**
     * Method description
     *
     */
    public void dropOffPassangersButDriver() {
        droppOffAllButOnePassanger(Crew.getIgrok().getUid());
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getAi_player() {
        return this.ai_player;
    }

    /**
     * Method description
     *
     *
     * @param ai_player
     */
    public void setAi_player(int ai_player) {
        this.ai_player = ai_player;
        UpdateCar();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public int getCar() {
        return this.car;
    }

    /**
     * Method description
     *
     *
     * @param car
     */
    public void setCar(int car) {
        this.car = car;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public IXMLSerializable getXmlSerializator() {
        return new ActorVehSerializator(this);
    }
}


//~ Formatted in DD Std on 13/08/26
