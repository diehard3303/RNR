/*
 * @(#)MissionDialogs.java   13/08/28
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


package rnr.src.rnrscr;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.players.Crew;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrcore.eng;
import rnr.src.rnrcore.event;
import rnr.src.rnrcore.vectorJ;
import rnr.src.rnrorg.MissionEventsMaker;
import rnr.src.rnrscenario.missions.MissionEndUIController;
import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscenario.missions.MissionManager;
import rnr.src.rnrscenario.missions.MissionPlacement;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.infochannels.InformationChannelData;
import rnr.src.rnrscenario.missions.map.PointsController;
import rnr.src.rnrscenario.missions.requirements.MissionsLog;
import rnr.src.rnrscenario.missions.requirements.MissionsLog.Event;
import rnr.src.scriptEvents.EventsControllerHelper;
import rnr.src.scriptEvents.SuccessEventChannel;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class MissionDialogs {
    private static MissionDialogs m_instance = null;
    @SuppressWarnings("unchecked")
    private final ArrayList<IMissionInformation> m_mission_infos = new ArrayList<IMissionInformation>();
    @SuppressWarnings("unchecked")
    private final ArrayList<IMissionInformation> m_cached_mission_infos = new ArrayList<IMissionInformation>();
    private String m_rotatingMission = null;
    @SuppressWarnings("unchecked")
    private final HashMap<String, IPointActivated> m_activatePointListeners = new HashMap<String, IPointActivated>();

    /**
     * Method description
     *
     *
     * @return
     */
    public static MissionDialogs getReference() {
        if (null == m_instance) {
            m_instance = new MissionDialogs();
        }

        return m_instance;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        m_instance = null;
    }

    /**
     * Method description
     *
     *
     * @param tip
     * @param position
     * @param time
     *
     * @return
     */
    public static DialogsSet queueDialogsForSO(int tip, vectorJ position, CoreTime time) {
        DialogsSet res = new DialogsSet();
        Iterator iter = getReference().m_mission_infos.iterator();

        while (iter.hasNext()) {
            IMissionInformation dialog = (IMissionInformation) iter.next();

            if ((!(dialog.isDialog())) || (!(dialog.isInfoPosted()))) {
                continue;
            }

            if (!(dialog.isInformationActive())) {
                continue;
            }

            if (!(dialog.hasPoint())) {
                continue;
            }

            if (!(MissionHelper.isThatPlace(tip, position, dialog.getPointName()))) {
                continue;
            }

            if ((!(dialog.isFinishInformaton()))
                    && (!(MissionEventsMaker.freeSlotForMission(dialog.getMissionName())))) {
                continue;
            }

            if (dialog.checkTimePeriods(time)) {
                res.addQuest(new SODialogParams(dialog.getIdentitie(),
                                                MissionEventsMaker.queueNPCResourseForDialog(dialog),
                                                dialog.getDialogName(), 1, dialog.wasPlayed(),
                                                dialog.isFinishInformaton(), dialog.getPointName(),
                                                dialog.getMissionName()));
            }
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param channelUID
     *
     * @return
     */
    public static IMissionInformation queueDialog(String channelUID) {
        for (IMissionInformation d : getReference().m_mission_infos) {
            if (d.getChannelId().compareToIgnoreCase(channelUID) == 0) {
                return d;
            }
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @param dialog
     */
    public static void AddDialog(IMissionInformation dialog) {
        getReference().m_mission_infos.add(dialog);
    }

    /**
     * Method description
     *
     *
     * @param resource_name
     * @param identitie
     * @param listener
     */
    public static void addDialogLiveDialog(String resource_name, String identitie, IPointActivated listener) {
        addDialogCarDialog(resource_name, identitie, listener);
    }

    /**
     * Method description
     *
     *
     * @param resource_name
     * @param identitie
     * @param listener
     */
    public static void addDialogCarDialog(String resource_name, String identitie, IPointActivated listener) {
        IMissionInformation dialog = getMissionInfo(resource_name);

        getReference().m_activatePointListeners.put(dialog.getChannelId(), listener);

        if (!(MissionSystemInitializer.getMissionsManager().isLoading())) {
            MissionEventsMaker.RegisterActivationPoint(dialog.getMissionName(), dialog.getChannelId());
        }
    }

    /**
     * Method description
     *
     *
     * @param resource
     * @param identie
     */
    public static void startDialogCarDialog(String resource, String identie) {
        IMissionInformation dialog = getMissionInfo(resource);
        aiplayer npc = MissionEventsMaker.queueNPCPlayer_FreeFromVoter(dialog, identie);

        dialog.freeVoter();
        Dialog.getDialog(dialog.getDialogName()).start_car_leave_car_on_end(npc, Crew.getIgrok().getModel(),
                         dialog.getIdentitie());
    }

    /**
     * Method description
     *
     *
     * @param dialogname
     */
    public static void activateDialog(String dialogname) {
        Iterator<IMissionInformation> iter = getReference().m_mission_infos.iterator();

        while (iter.hasNext()) {
            IMissionInformation dialog = iter.next();

            if (dialog.getDialogName().compareTo(dialogname) == 0) {
                dialog.postInfo();

                return;
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param dialogname
     */
    public static void RemoveDialog(String dialogname) {
        if ((null != getReference().m_rotatingMission)
                && (dialogname.compareTo(getReference().m_rotatingMission) == 0)) {
            return;
        }

        Iterator<IMissionInformation> iter = getReference().m_mission_infos.iterator();

        while (iter.hasNext()) {
            IMissionInformation dialog = iter.next();

            if (dialog.getDialogName().compareTo(dialogname) == 0) {
                iter.remove();

                break;
            }
        }

        iter = getReference().m_cached_mission_infos.iterator();

        while (iter.hasNext()) {
            IMissionInformation dialog = iter.next();

            if (dialog.getDialogName().compareTo(dialogname) == 0) {
                iter.remove();

                return;
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param dialog_name
     */
    public static void sayYes(String dialog_name) {
        getReference().m_rotatingMission = dialog_name;

        for (IMissionInformation d : getReference().m_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) == 0) {
                d.receiveAnswer();

                ArrayList<String> mission_names_to_inform = d.getDependantMissions();

                mission_names_to_inform.add(d.getMissionName());

                for (String mission_name : mission_names_to_inform) {
                    MissionEventsMaker.channelSayAccept(d.getChannelId(), mission_name, d.getPointName(),
                            d.isChannelImmediate());
                    MissionsLog.getInstance().eventHappen(mission_name, MissionsLog.Event.MISSION_ACCEPTED);
                }

                getReference().m_rotatingMission = null;

                return;
            }
        }

        for (IMissionInformation d : getReference().m_cached_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) == 0) {
                d.receiveAnswer();

                ArrayList<String> mission_names_to_inform = d.getDependantMissions();

                mission_names_to_inform.add(d.getMissionName());

                for (String mission_name : mission_names_to_inform) {
                    MissionEventsMaker.channelSayAccept(d.getChannelId(), mission_name, d.getPointName(),
                            d.isChannelImmediate());
                    MissionsLog.getInstance().eventHappen(mission_name, MissionsLog.Event.MISSION_ACCEPTED);
                }

                getReference().m_cached_mission_infos.remove(d);
                getReference().m_rotatingMission = null;

                return;
            }
        }

        getReference().m_rotatingMission = null;
        eng.err("MissionDialogs. sayYes. Has no such dialog " + dialog_name);
    }

    /**
     * Method description
     *
     *
     * @param dialog_name
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void sayNo(String dialog_name) {
        getReference().m_rotatingMission = dialog_name;

        for (IMissionInformation d : getReference().m_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) == 0) {
                d.receiveAnswer();

                ArrayList<String> mission_names_to_inform = d.getDependantMissions();

                mission_names_to_inform.add(d.getMissionName());

                for (String mission_name : mission_names_to_inform) {
                    MissionEventsMaker.channelSayDeclein(d.getChannelId(), mission_name, d.getPointName(),
                            d.isChannelImmediate());

                    MissionInfo mi = MissionSystemInitializer.getMissionsManager().getMissionInfo(mission_name);

                    if (mi != null) {
                        for (InformationChannelData channelDescription : mi.getAllChannels()) {
                            List pointsToClear = new ArrayList(channelDescription.getPlacesNames());

                            PointsController.getInstance().freePoints(pointsToClear,
                                    MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission_name));
                        }

                        MissionPlacement mp =
                            MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission_name);
                        String s = mp.getInfo().getQuestItemPlacement();

                        if (null != s) {
                            ArrayList pNames = new ArrayList();

                            pNames.add(s);
                            PointsController.getInstance().freePoints(pNames, mp);
                        }
                    }

                    MissionsLog.getInstance().eventHappen(mission_name, MissionsLog.Event.PLAYER_DECLINED_MISSION);
                }

                getReference().m_rotatingMission = null;

                return;
            }
        }

        for (IMissionInformation d : getReference().m_cached_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) == 0) {
                d.receiveAnswer();

                ArrayList mission_names_to_inform = d.getDependantMissions();

                mission_names_to_inform.add(d.getMissionName());

                for (String mission_name : mission_names_to_inform) {
                    MissionEventsMaker.channelSayDeclein(d.getChannelId(), mission_name, d.getPointName(),
                            d.isChannelImmediate());

                    MissionInfo mi = MissionSystemInitializer.getMissionsManager().getMissionInfo(mission_name);

                    if (mi != null) {
                        for (InformationChannelData channelDescription : mi.getAllChannels()) {
                            List pointsToClear = new ArrayList(channelDescription.getPlacesNames());

                            PointsController.getInstance().freePoints(pointsToClear,
                                    MissionSystemInitializer.getMissionsManager().getMissionPlacement(mission_name));
                        }
                    }

                    MissionsLog.getInstance().eventHappen(mission_name, MissionsLog.Event.PLAYER_DECLINED_MISSION);
                }

                getReference().m_cached_mission_infos.remove(d);
                getReference().m_rotatingMission = null;

                return;
            }
        }

        getReference().m_rotatingMission = null;
        eng.err("MissionDialogs. sayNo. Has no such dialog " + dialog_name);
    }

    /**
     * Method description
     *
     *
     * @param dialog_name
     */
    public static void sayAppear(String dialog_name) {
        getReference().m_rotatingMission = dialog_name;

        for (IMissionInformation d : getReference().m_mission_infos) {
            if ((!(d.wasPlayed())) && (d.getDialogName().compareToIgnoreCase(dialog_name) == 0)) {
                d.playMissionInfo();

                if (!(d.isFinishInformaton())) {
                    MissionEventsMaker.channelSayAppear(d.getChannelId(), d.getMissionName(), d.getPointName(),
                            d.isChannelImmediate());
                }

                MissionsLog.getInstance().eventHappen(d.getMissionName(),
                        MissionsLog.Event.PLAYER_INFORMED_ABOUT_MISSION);

                ArrayList<String> mission_names_to_inform = d.getDependantMissions();

                for (String mission_name : mission_names_to_inform) {
                    if (d.isFinishInformaton()) {
                        MissionEventsMaker.channelSayAppear(d.getChannelId(), mission_name, d.getPointName(),
                                d.isChannelImmediate());
                    }

                    MissionsLog.getInstance().eventHappen(mission_name,
                            MissionsLog.Event.PLAYER_INFORMED_ABOUT_MISSION);
                }

                getReference().m_rotatingMission = null;

                return;
            }
        }

        getReference().m_rotatingMission = null;
        eng.err("MissionDialogs. sayAppear. Has no such dialog " + dialog_name);
    }

    /**
     * Method description
     *
     *
     * @param dialog_name
     */
    public static void sayEnd(String dialog_name) {
        if (hasFinish(dialog_name)) {
            EventsControllerHelper.eventHappened(new SuccessEventChannel());
            MissionEndUIController.nextMissionToEvent();
        } else {
            event.SetScriptevent(9850L);
        }

        for (IMissionInformation d : getReference().m_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) == 0) {
                ArrayList<String> mission_names_to_inform = d.getDependantMissions();

                mission_names_to_inform.add(d.getMissionName());

                for (String mission_name : mission_names_to_inform) {
                    MissionEventsMaker.channelSayEnd(d.getChannelId(), mission_name, d.getPointName(),
                                                     d.isChannelImmediate());
                }

                if (d.wasVoterFreed()) {
                    MissionEventsMaker.npcPlayer_ResumeVoter(d);
                }

                getReference().m_cached_mission_infos.add(d);
                getReference().m_mission_infos.remove(d);

                return;
            }
        }

        eng.err("MissionDialogs. sayEnd. Has no such dialog " + dialog_name);
    }

    /**
     * Method description
     *
     *
     * @param dialog_name
     *
     * @return
     */
    public static boolean hasFinish(String dialog_name) {
        for (IMissionInformation d : getReference().m_mission_infos) {
            if ((d.getDialogName().compareToIgnoreCase(dialog_name) == 0) && (d.isFinishInformaton())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param dialog_name
     *
     * @return
     */
    public static IMissionInformation getMissionInfo(String dialog_name) {
        for (IMissionInformation d : getReference().m_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) == 0) {
                return d;
            }
        }

        eng.err("MissionDialogs. getMissionInfo. Has no such dialog " + dialog_name);

        return null;
    }

    /**
     * Method description
     *
     *
     * @param dialog_name
     *
     * @return
     */
    public static IMissionInformation getMissionInfoAndInCachedToo(String dialog_name) {
        IMissionInformation missionInfo = getMissionInfo(dialog_name);

        if (missionInfo != null) {
            return missionInfo;
        }

        for (IMissionInformation d : getReference().m_cached_mission_infos) {
            if (d.getDialogName().compareToIgnoreCase(dialog_name) == 0) {
                return d;
            }
        }

        eng.err("MissionDialogs. getMissionInfo. Has no such dialog " + dialog_name);

        return null;
    }

    /**
     * Method description
     *
     *
     * @param resourceid
     */
    public static void pointActivated(String resourceid) {
        if (!(getReference().m_activatePointListeners.containsKey(resourceid))) {
            return;
        }

        getReference().m_activatePointListeners.get(resourceid).pointActivated();
        getReference().m_activatePointListeners.remove(resourceid);
    }

    /**
     * Method description
     *
     *
     * @param channel_uid
     * @param point_name
     */
    public static void moveChannelOnPoint(String channel_uid, String point_name) {
        for (IMissionInformation d : getReference().m_mission_infos) {
            if (d.getChannelId().compareToIgnoreCase(channel_uid) == 0) {
                d.setPointName(point_name);
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param channel_uid
     */
    public static void freeChannelFromPoint(String channel_uid) {
        for (IMissionInformation d : getReference().m_mission_infos) {
            if (d.getChannelId().compareToIgnoreCase(channel_uid) == 0) {
                d.freeFromPoint();
            }
        }
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public static class TimePeriod {
        CoreTime from;
        CoreTime to;

        boolean isInPeriod(CoreTime time) {
            return ((time.moreThan(this.from) >= 0) && (time.moreThan(this.to) < 0));
        }
    }


    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public static class dayTimePeriod {
        int hour_from;
        int hour_to;

        boolean isInPeriod(CoreTime time) {
            return ((time.gHour() >= this.hour_from) && (time.gHour() >= this.hour_to));
        }
    }
}


//~ Formatted in DD Std on 13/08/28
