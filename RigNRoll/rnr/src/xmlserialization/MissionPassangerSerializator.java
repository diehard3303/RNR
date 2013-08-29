/*
 * @(#)MissionPassangerSerializator.java   13/08/28
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

import rnr.src.players.actorveh;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.XmlSerializable;
import rnr.src.rnrscr.MissionPassanger;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class MissionPassangerSerializator implements XmlSerializable {
    private static MissionPassangerSerializator instance = new MissionPassangerSerializator();

    /**
     * Method description
     *
     *
     * @return
     */
    public static MissionPassangerSerializator getInstance() {
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
        return "mission_passanger";
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeNamePack() {
        return "aipack";
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
        serializeXML(MissionPassanger.getInstance(), stream);
    }

    private void serializeXML(MissionPassanger missionPassanger, PrintStream stream) {
        Helper.openNode(stream, getRootNodeName());

        actorveh car = missionPassanger.getCar();

        if (car != null) {
            ActorVehSerializator.serializeXML(car, stream);
        }

        aiplayer player = missionPassanger.getNpc();

        if (player != null) {
            AIPlayerSerializator.serializeXML(player, stream);
        }

        aiplayer pack = missionPassanger.getPack();

        if (pack != null) {
            Helper.openNode(stream, getNodeNamePack());
            AIPlayerSerializator.serializeXML(pack, stream);
            Helper.closeNode(stream, getNodeNamePack());
        }

        Helper.closeNode(stream, getRootNodeName());
    }

    private void deserializeXML(rnr.src.xmlutils.Node node) {
        rnr.src.xmlutils.Node carNode = node.getNamedChild(ActorVehSerializator.getNodeName());

        if (carNode != null) {
            actorveh car = ActorVehSerializator.deserializeXML(carNode);

            MissionPassanger.getInstance().setCar(car);
        }

        rnr.src.xmlutils.Node playerNode = node.getNamedChild(AIPlayerSerializator.getNodeName());
        aiplayer player = null;

        if (playerNode != null) {
            player = AIPlayerSerializator.deserializeXML(playerNode);
        }

        rnr.src.xmlutils.Node pNode = node.getNamedChild(getNodeNamePack());

        if (pNode != null) {
            if (player != null) {
                MissionPassanger.getInstance().setNpc(player);
            }

            rnr.src.xmlutils.Node packNode = pNode.getNamedChild(AIPlayerSerializator.getNodeName());

            if (packNode != null) {
                aiplayer pack = AIPlayerSerializator.deserializeXML(packNode);

                if (pack != null) {
                    MissionPassanger.getInstance().setPack(pack);
                }
            }
        } else {
            if (player == null) {
                return;
            }

            if (player.gModelname().contains("package")) {
                MissionPassanger.getInstance().setPack(player);
            } else {
                MissionPassanger.getInstance().setNpc(player);
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/28
