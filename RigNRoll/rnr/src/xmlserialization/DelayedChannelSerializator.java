/*
 * @(#)DelayedChannelSerializator.java   13/08/28
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


package rnr.src.xmlserialization;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.XmlSerializable;
import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrscenario.missions.DelayedResourceDisposer;
import rnr.src.rnrscenario.missions.DelayedResourceDisposer.DelayedDisposeChannelData;
import rnr.src.rnrscenario.missions.Disposable;
import rnr.src.rnrscenario.missions.MissionEndUIController;
import rnr.src.rnrscenario.missions.MissionInfo;
import rnr.src.rnrscenario.missions.MissionManager;
import rnr.src.rnrscenario.missions.MissionPhase;
import rnr.src.rnrscenario.missions.MissionSystemInitializer;
import rnr.src.rnrscenario.missions.infochannels.InformationChannel;
import rnr.src.rnrscenario.missions.infochannels.InformationChannelData;
import rnr.src.rnrscenario.missions.infochannels.NoSuchChannelException;
import rnr.src.rnrscenario.missions.map.PointsController;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class DelayedChannelSerializator implements XmlSerializable {
    private static DelayedChannelSerializator instance = new DelayedChannelSerializator();

    /**
     * Method description
     *
     *
     * @return
     */
    public static DelayedChannelSerializator getInstance() {
        return instance;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public String getRootNodeName() {
        return getNodeName();
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void loadFromNode(org.w3c.dom.Node node) {
        deserializeXML(new rnr.src.xmlutils.Node(node));
    }

    /**
     * Method description
     *
     */
    @Override
    public void yourNodeWasNotFound() {}

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        serializeXML(stream);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "delayed_channel";
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void serializeXML(PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        List tasks = DelayedResourceDisposer.getInstance().getDelayedDisposeChannelsData();
        List mission_printed = new LinkedList();

        for (DelayedResourceDisposer.DelayedDisposeChannelData task : tasks) {
            String mission = task.missionName;

            if (!(mission_printed.contains(mission))) {
                int time = task.secondsRemained;
                List attributes = Helper.createSingleAttribute("name", mission);

                Helper.addAttribute("time", time, attributes);
                Helper.printClosedNodeWithAttributes(stream, "mission", attributes);
                mission_printed.add(mission);
            }
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void deserializeXML(xmlutils.Node node) {
        NodeList elements = node.getNamedChildren("mission");

        if ((null == elements) || (elements.isEmpty())) {
            return;
        }

        for (rnr.src.xmlutils.Node element : elements) {
            String mission = element.getAttribute("name");
            String timestr = element.getAttribute("time");
            int time = Helper.ConvertToIntegerAndWarn(timestr, "time", "Remined time of delayed channel");
            MissionInfo mi = MissionSystemInitializer.getMissionsManager().getMissionInfo(mission);

            if (mi != null) {
                List<MissionPhase> waysToEnd = mi.getEndPhase();

                for (Iterator<MissionPhase> i$ = waysToEnd.iterator(); i$.hasNext(); ) {
                    MissionPhase possibleEnd = i$.next();

                    for (InformationChannelData channelDescription : possibleEnd.getInfoChannels()) {
                        try {
                            InformationChannel channel = channelDescription.makeWare();
                            List pointsToClear = new ArrayList(channelDescription.getPlacesNames());

                            if (channel.isNoMainFinishSucces()) {
                                Disposable resourceCleaner = new Disposable(channel, pointsToClear, mission) {
                                    @Override
                                    public void dispose() {
                                        this.channel.dispose();
                                        PointsController.getInstance().freePoints(
                                            this.pointsToClear,
                                            MissionSystemInitializer.getMissionsManager().getMissionPlacement(
                                                this.mission));
                                    }
                                };

                                DelayedResourceDisposer.getInstance().addResourceToDispose(resourceCleaner, mission,
                                        channel.getUid(), time);
                                possibleEnd.getUIController().placeRecourcesThroghChannel(channel, mission,
                                        channelDescription.getResource());
                                possibleEnd.getUIController().postInfo();
                            }
                        } catch (NoSuchChannelException e) {
                            MissionsLogger.getInstance().doLog("wrong channel name data: " + e.getMessage(),
                                                               Level.SEVERE);
                        }
                    }
                }
            }
        }

        String mission;
        int time;
        Iterator i$;
        MissionPhase possibleEnd;
    }
}


//~ Formatted in DD Std on 13/08/28
