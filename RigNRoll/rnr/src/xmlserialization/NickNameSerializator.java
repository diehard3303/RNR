/*
 * @(#)NickNameSerializator.java   13/08/28
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
public class NickNameSerializator {

    /**
     * Method description
     *
     *
     * @return
     */
    public static String getNodeName() {
        return "nickname";
    }

    /**
     * Method description
     *
     *
     * @param value
     * @param stream
     */
    @SuppressWarnings("rawtypes")
    public static void serializeXML(DriversModelsPool.NickName value, PrintStream stream) {
        List attributes = Helper.createSingleAttribute("identitie", value.getIdentitie());

        Helper.addAttribute("nickname", value.getNickName(), attributes);
        Helper.printClosedNodeWithAttributes(stream, getNodeName(), attributes);
    }

    /**
     * Method description
     *
     *
     * @param node
     *
     * @return
     */
    public static DriversModelsPool.NickName deserializeXML(Node node) {
        String nickName = node.getAttribute("nickname");
        String identitie = node.getAttribute("identitie");

        if (null == nickName) {
            Log.error("NickNameSerializator in deserializeXML has no attribute nickname");
        }

        if (null == identitie) {
            Log.error("NickNameSerializator in deserializeXML has no attribute identitie");
        }

        return new DriversModelsPool.NickName(nickName, identitie);
    }
}


//~ Formatted in DD Std on 13/08/28
