/*
 * @(#)InfoChannelsXmlExtractor.java   13/08/28
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


package rnr.src.rnrscenario.missions;

//~--- non-JDK imports --------------------------------------------------------

import org.w3c.dom.Node;

import rnr.src.rnrloggers.MissionsLogger;
import rnr.src.rnrscenario.missions.infochannels.InformationChannelData;
import rnr.src.rnrscenario.missions.infochannels.InformationChannelDataCreationException;
import rnr.src.scenarioXml.XmlNodeDataProcessor;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;
import java.util.logging.Level;

class InfoChannelsXmlExtractor implements XmlNodeDataProcessor {
    private List<InformationChannelData> store = null;
    private final MissionCreationContext context;

    InfoChannelsXmlExtractor(List<InformationChannelData> store, MissionCreationContext context) {
        assert(null != store) : "store must be non-null reference";
        this.store = store;
        this.context = context;
    }

    /**
     * Method description
     *
     *
     * @param target
     * @param param
     */
    @Override
    public void process(Node target, Object param) {
        assert(null != target) : "target must be non-null reference";

        try {
            this.store.add(new InformationChannelData(target, this.context));
        } catch (InformationChannelDataCreationException e) {
            MissionsLogger.getInstance().doLog(e.getLocalizedMessage(), Level.SEVERE);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
