/*
 * @(#)organaiser.java   13/08/26
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


package rnr.src.rnrorg;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menuscript.HeadUpDisplay;
import rnr.src.rnrcore.eng;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class organaiser {
    private static organaiser ORG = null;
    private IStoreorgelement current = null;
    private IStoreorgelement current_warehouse = null;
    private int has_unread = 0;
    private Vector<IStoreorgelement> allorgelements;

    private organaiser() {
        this.allorgelements = new Vector();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static organaiser getInstance() {
        if (null == ORG) {
            ORG = new organaiser();
        }

        return ORG;
    }

    private static boolean isMissionActual(IStoreorgelement element) {
        return ((element.getStatus() == IStoreorgelement.Status.urgentMission)
                || (element.getStatus() == IStoreorgelement.Status.pendingMission));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Iterator<IStoreorgelement> gOrganaiser() {
        return this.allorgelements.iterator();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public IStoreorgelement getCurrentWarehouseOrder() {
        return this.current_warehouse;
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void setCurrentWarehouseOrder(IStoreorgelement val) {
        this.current_warehouse = val;
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void add(IStoreorgelement val) {
        if ((this.allorgelements.isEmpty()) || (null == this.current)) {
            this.current = val;
        }

        this.allorgelements.add(val);

        if (!(val.isRead())) {
            if (0 == this.has_unread++) {
                HeadUpDisplay.updateUnread();
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void addOnRestore(IStoreorgelement val) {
        this.allorgelements.add(val);
    }

    /**
     * Method description
     *
     *
     * @param obj
     */
    public void remove(IStoreorgelement obj) {
        for (int i = 0; i < this.allorgelements.size(); ++i) {
            if (this.allorgelements.elementAt(i).equals(obj)) {
                this.allorgelements.removeElementAt(i);

                return;
            }
        }
    }

    /**
     * Method description
     *
     */
    public void updateMissionsOrgElements() {
        for (IStoreorgelement element : this.allorgelements) {
            String mission_name = MissionOrganiser.getInstance().getMissionForOrganiser(element.getName());

            if ((null != mission_name) && (element instanceof Scorgelement)) {
                MissionEventsMaker.updateOrganiser(mission_name, (Scorgelement) element);
            } else if ((element instanceof WarehouseOrder)
                       && (element.getStatus() == IStoreorgelement.Status.pendingMission)
                       && (element.getStatus() == IStoreorgelement.Status.urgentMission)) {
                MissionEventsMaker.updateOrganiser(mission_name, (WarehouseOrder) element);
            }
        }
    }

    private void findCurrent() {
        for (IStoreorgelement element : getInstance().allorgelements) {
            if (isMissionActual(element)) {
                this.current = element;

                return;
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param delta_money
     * @param delta_rating
     * @param delta_rank
     */
    public static void failWarehouseMission_TimeoutPickup(int delta_money, int delta_rating, double delta_rank) {
        failWarehouseMission(0, delta_money, delta_rating, delta_rank);
    }

    /**
     * Method description
     *
     *
     * @param delta_money
     * @param delta_rating
     * @param delta_rank
     */
    public static void failWarehouseMission_TimeoutComplete(int delta_money, int delta_rating, double delta_rank) {
        failWarehouseMission(1, delta_money, delta_rating, delta_rank);
    }

    /**
     * Method description
     *
     *
     * @param delta_money
     * @param delta_rating
     * @param delta_rank
     */
    public static void failWarehouseMission_Damaged(int delta_money, int delta_rating, double delta_rank) {
        failWarehouseMission(2, delta_money, delta_rating, delta_rank);
    }

    /**
     * Method description
     *
     *
     * @param delta_money
     * @param delta_rating
     * @param delta_rank
     */
    public static void failWarehouseMission_Declined(int delta_money, int delta_rating, double delta_rank) {
        failWarehouseMission(3, delta_money, delta_rating, delta_rank);
    }

    /**
     * Method description
     *
     *
     * @param delta_money
     * @param delta_rating
     * @param delta_rank
     */
    public static void finishWarehouseMission(int delta_money, int delta_rating, double delta_rank) {
        for (IStoreorgelement element : getInstance().allorgelements) {
            if ((element instanceof WarehouseOrder) && (isMissionActual(element))) {
                RewardForfeit summary = element.getReward();

                if (null != summary) {
                    summary.setRealMoney(delta_money);
                    summary.setRealRate(delta_rating);
                    summary.setRealRank((int) delta_rank);
                }

                element.finish();

                return;
            }
        }
    }

    private static void failWarehouseMission(int type_fail, int delta_money, int delta_rating, double delta_rank) {
        for (IStoreorgelement element : getInstance().allorgelements) {
            if ((element instanceof WarehouseOrder) && (isMissionActual(element))) {
                RewardForfeit summary = element.getForfeit();

                if (null != summary) {
                    summary.setRealMoney(delta_money);
                    summary.setRealRate(delta_rating);
                    summary.setRealRank((int) delta_rank);
                }

                element.fail(type_fail);

                return;
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param list
     */
    public static void declineActiveMissions(List<String> list) {
        for (IStoreorgelement element : getInstance().allorgelements) {
            if (isMissionActual(element)) {
                String nm = element.getName();
                String mission_name = MissionOrganiser.getInstance().getMissionForOrganiser(nm);

                if (!(list.contains(mission_name))) {
                    element.decline();
                }
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param missionToDecline
     */
    public static void declineMission(String missionToDecline) {
        if (null == missionToDecline) {
            return;
        }

        for (IStoreorgelement element : getInstance().allorgelements) {
            if (isMissionActual(element)) {
                String organizerName = element.getName();
                String missionName = MissionOrganiser.getInstance().getMissionForOrganiser(organizerName);

                if (0 == missionToDecline.compareTo(missionName)) {
                    element.decline();

                    return;
                }
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param fill_this
     */
    public static void getInterestingElements(ListOfMissions fill_this) {
        fill_this.currentmission = getInstance().current;

        for (IStoreorgelement element : getInstance().allorgelements) {
            if (isMissionActual(element)) {
                String mission_name = MissionOrganiser.getInstance().getMissionForOrganiser(element.getName());
                boolean is_warehouse_mission = element instanceof WarehouseOrder;
                MissionsListParams params = new MissionsListParams((null == mission_name)
                        ? element.getName()
                        : mission_name, is_warehouse_mission);

                fill_this.missions.add(element);
                fill_this.mission_names.add(params);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static void choose(IStoreorgelement value) {
        for (IStoreorgelement element : getInstance().allorgelements) {
            if ((value.equals(element)) && (isMissionActual(element))) {
                getInstance().current = value;
                getInstance().allorgelements.remove(element);
                getInstance().allorgelements.add(0, element);

                return;
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     *
     * @return
     */
    public static String getMissionDescription(String mission_name) {
        String org_name = MissionOrganiser.getInstance().getOrgForMission(mission_name);

        for (IStoreorgelement element : getInstance().allorgelements) {
            if (element.getName().compareTo(org_name) == 0) {
                return element.getDescription();
            }
        }

        IStoreorgelement elem = Organizers.getInstance().get(org_name);

        if (null != elem) {
            return elem.getDescription();
        }

        return org_name;
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     *
     * @return
     */
    public static String getMissionDescriptionRef(String mission_name) {
        String org_name = MissionOrganiser.getInstance().getOrgForMission(mission_name);

        for (IStoreorgelement element : getInstance().allorgelements) {
            if (element.getName().compareTo(org_name) == 0) {
                return element.getDescriptionRef();
            }
        }

        IStoreorgelement elem = Organizers.getInstance().get(org_name);

        if (null != elem) {
            return elem.getDescriptionRef();
        }

        return org_name;
    }

    /**
     * Method description
     *
     *
     * @param mission_name
     */
    public static void declineMissionByName(String mission_name) {
        String org_name = MissionOrganiser.getInstance().getOrgForMission(mission_name);

        if ((null == org_name) || (org_name.length() == 0)) {
            eng.err("declineMissionByName couldn't find organiser for mission " + mission_name);

            return;
        }

        IStoreorgelement elem = Organizers.getInstance().get(org_name);

        if (null != elem) {
            elem.decline();
        } else {
            eng.err("declineMissionByName couldn't find organiser element named " + org_name + " for mission "
                    + mission_name);
        }
    }

    /**
     * Method description
     *
     *
     * @param elem
     *
     * @return
     */
    public boolean isCurrent(IStoreorgelement elem) {
        return elem.equals(this.current);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static IStoreorgelement getCurrent() {
        return getInstance().current;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static CurrentMissionInfo getCurrentMissionInfo() {
        String mission_name = (getInstance().current != null)
                              ? MissionOrganiser.getInstance().getMissionForOrganiser(getInstance().current.getName())
                              : null;

        return new CurrentMissionInfo(mission_name, getInstance().current);
    }

    /**
     * Method description
     *
     *
     * @param elem
     */
    public static void finishMission(IStoreorgelement elem) {
        if (getInstance().isCurrent(elem)) {
            getInstance().current = null;
            getInstance().findCurrent();
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public boolean hasUnread() {
        return (this.has_unread != 0);
    }

    /**
     * Method description
     *
     */
    public void readOne() {
        if (this.has_unread == 0) {
            return;
        }

        this.has_unread -= 1;

        if (this.has_unread == 0) {
            HeadUpDisplay.updateUnread();
        }
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        ORG = null;
        MissionOrganiser.deinit();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public Vector<IStoreorgelement> getAllorgelements() {
        return this.allorgelements;
    }

    /**
     * Method description
     *
     *
     * @param allorgelements
     */
    public void setAllorgelements(Vector<IStoreorgelement> allorgelements) {
        this.allorgelements = allorgelements;
    }

    static class CurrentMissionInfo {
        String mission_name;
        IStoreorgelement currentmission;
        boolean is_warehouse_order;
        boolean not_null;

        CurrentMissionInfo(String mission_name, IStoreorgelement currentmission) {
            this.mission_name = mission_name;
            this.currentmission = currentmission;
            this.not_null = (null != currentmission);
            this.is_warehouse_order = ((this.not_null) && (currentmission instanceof WarehouseOrder));
        }
    }


    static class ListOfMissions {
        Vector missions;
        Vector mission_names;
        IStoreorgelement currentmission;

        ListOfMissions() {
            this.missions = new Vector();
            this.mission_names = new Vector();
            this.currentmission = null;
        }
    }


    static class MissionsListParams {
        String name;
        boolean is_warehouse_mission;

        MissionsListParams(String name, boolean is_warehouse_mission) {
            this.is_warehouse_mission = is_warehouse_mission;
            this.name = name;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
