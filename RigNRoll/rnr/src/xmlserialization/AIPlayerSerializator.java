/*
 * @(#)AIPlayerSerializator.java   13/08/28
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

import rnr.src.players.CrewNamesManager;
import rnr.src.players.ImodelCreate;
import rnr.src.players.aiplayer;
import rnr.src.rnrcore.IXMLSerializable;
import rnr.src.scenarioUtils.Pair;
import rnr.src.xmlutils.Node;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.List;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class AIPlayerSerializator implements IXMLSerializable {
    private final aiplayer m_player;

    /**
     * Constructs ...
     *
     */
    public AIPlayerSerializator() {
        this.m_player = null;
    }

    /**
     * Constructs ...
     *
     *
     * @param player
     */
    public AIPlayerSerializator(aiplayer player) {
        this.m_player = player;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "aiplayer";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(aiplayer value, PrintStream stream) {
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("identitie", value.getIdentitie());

        Helper.addAttribute("uid", value.getUid(), attributes);
        Helper.addAttribute("modelbasedmodel", value.getModelBased(), attributes);
        Helper.addAttribute("poolbasedmodel", value.getPoolBased(), attributes);
        Helper.addAttribute("poolref_name", value.getPoolRefName(), attributes);
        Helper.addAttribute("idForModelCreator", value.getIdForModelCreator(), attributes);
        Helper.printOpenNodeWithAttributes(stream, getNodeName(), attributes);

        ImodelCreate creator = value.getModelCreator();

        if (creator != null) {
            Class classCreator = creator.getClass();
            String className = classCreator.getName();

            Helper.printClosedNodeWithAttributes(stream, "modelcreator",
                    Helper.createSingleAttribute("classname", className));
        }

        Helper.closeNode(stream, getNodeName());
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static aiplayer deserializeXML(Node node) {
        String uid = node.getAttribute("uid");
        String identitie = node.getAttribute("identitie");
        int integerUid = Integer.parseInt(uid);
        aiplayer result = aiplayer.createScriptRef(identitie, integerUid);
        String modelBased = node.getAttribute("modelbasedmodel");
        String poolBased = node.getAttribute("poolbasedmodel");
        String poolRefName = node.getAttribute("poolref_name");
        String idForModelCreation = node.getAttribute("idForModelCreator");

        if ((poolBased != null) && (Boolean.parseBoolean(poolBased))) {
            result.sPoolBased(poolRefName);
        }

        if (modelBased != null) {
            result.sModelBased(Boolean.parseBoolean(modelBased));
        }

        Node modelCreatorNode = node.getNamedChild("modelcreator");

        if (null != modelCreatorNode) {
            String className = modelCreatorNode.getAttribute("classname");

            if (null != className) {
                try {
                    Class modelCreatorClass = Class.forName(className);

                    if (null != modelCreatorClass) {
                        Object modelCreator = modelCreatorClass.newInstance();

                        result.setModelCreator((ImodelCreate) modelCreator, idForModelCreation);
                    }
                } catch (Exception e) {
                    Log.error("Exception occured of deserializeXML of AIPlayerSerializator. Message: " + e.toString());
                }
            }
        }

        if ("SC_NICK".equals(identitie)) {
            CrewNamesManager.mainCharacterLoaded(result);
        }

        return result;
    }

    /**
     * Method description
     *
     *
     * @param node
     */
    @Override
    public void deSerialize(Node node) {
        deserializeXML(node);
    }

    /**
     * Method description
     *
     *
     * @param stream
     */
    @Override
    public void serialize(PrintStream stream) {
        serializeXML(this.m_player, stream);
    }
}


//~ Formatted in DD Std on 13/08/28
