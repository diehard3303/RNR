/*
 * @(#)ModelPoolSerializator.java   13/08/28
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

import rnr.src.players.DriversModelsPool;
import rnr.src.players.DriversModelsPool.NickName;
import rnr.src.players.NickNamesUniqueName;
import rnr.src.rnrconfig.GetTruckersIdentities;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;

//~--- JDK imports ------------------------------------------------------------

import java.io.PrintStream;

import java.util.ArrayList;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class ModelPoolSerializator {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "driversmodelspool";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    public static void serializeXML(DriversModelsPool value, PrintStream stream) {
        Helper.openNode(stream, getNodeName());

        ArrayList<String> cycleModels = value.getCycleListModelNames();

        Helper.openNode(stream, "CycleListModelNames");

        for (String item : cycleModels) {
            SimpleTypeSerializator.serializeXMLString(item, stream);
        }

        Helper.closeNode(stream, "CycleListModelNames");

        ArrayList<String> poolModels = value.getPool();

        Helper.openNode(stream, "pool");

        for (String item : poolModels) {
            SimpleTypeSerializator.serializeXMLString(item, stream);
        }

        Helper.closeNode(stream, "pool");

        ArrayList<String> exposingModels = value.getExposing();

        Helper.openNode(stream, "exposing");

        for (String item : exposingModels) {
            SimpleTypeSerializator.serializeXMLString(item, stream);
        }

        Helper.closeNode(stream, "exposing");

        ArrayList<NickName> freeModels = value.getFreeNickNames();

        Helper.openNode(stream, "freenicknames");

        for (DriversModelsPool.NickName item : freeModels) {
            NickNameSerializator.serializeXML(item, stream);
        }

        Helper.closeNode(stream, "freenicknames");

        ArrayList<NickName> bussyModels = value.getBussyNickNames();

        Helper.openNode(stream, "bussynicknames");

        for (DriversModelsPool.NickName item : bussyModels) {
            NickNameSerializator.serializeXML(item, stream);
        }

        Helper.closeNode(stream, "bussynicknames");

        NickNamesUniqueName uniqueNamer = value.getUniqueName();

        UniqueNickNameSerializator.serializeXML(uniqueNamer, stream);
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
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static DriversModelsPool deserializeXML(Node node) {
        GetTruckersIdentities identities = new GetTruckersIdentities();
        DriversModelsPool result = DriversModelsPool.create(identities.get());
        Node cycleList = node.getNamedChild("CycleListModelNames");
        NodeList strings = cycleList.getNamedChildren("string");
        ArrayList cycleModels = new ArrayList();

        for (Node stringnode : strings) {
            cycleModels.add(SimpleTypeSerializator.deserializeXMLString(stringnode));
        }

        result.setCycleListModelNames(cycleModels);

        Node poolList = node.getNamedChild("pool");

        strings = poolList.getNamedChildren("string");

        ArrayList poolModels = new ArrayList();

        for (Node stringnode : strings) {
            poolModels.add(SimpleTypeSerializator.deserializeXMLString(stringnode));
        }

        result.setPool(poolModels);

        Node exposingList = node.getNamedChild("exposing");

        strings = exposingList.getNamedChildren("string");

        ArrayList exposingModels = new ArrayList();

        for (Node stringnode : strings) {
            exposingModels.add(SimpleTypeSerializator.deserializeXMLString(stringnode));
        }

        result.setExposing(exposingModels);

        Node freeList = node.getNamedChild("freenicknames");
        NodeList nicknamesList = freeList.getNamedChildren(NickNameSerializator.getNodeName());
        ArrayList freeModels = new ArrayList();

        for (Node nickNameNode : nicknamesList) {
            freeModels.add(NickNameSerializator.deserializeXML(nickNameNode));
        }

        result.setFreeNickNames(freeModels);

        Node bussyList = node.getNamedChild("bussynicknames");

        nicknamesList = bussyList.getNamedChildren(NickNameSerializator.getNodeName());

        ArrayList bussyModels = new ArrayList();

        for (Node nickNameNode : nicknamesList) {
            bussyModels.add(NickNameSerializator.deserializeXML(nickNameNode));
        }

        result.setBussyNickNames(bussyModels);

        Node uniqueNameNode = node.getNamedChild(UniqueNickNameSerializator.getNodeName());

        result.setUniqueName(UniqueNickNameSerializator.deserializeXML(uniqueNameNode));

        return result;
    }
}


//~ Formatted in DD Std on 13/08/28
