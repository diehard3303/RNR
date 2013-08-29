/*
 * @(#)PointListExtractor.java   13/08/28
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

import rnr.src.scenarioXml.XmlFilter;
import rnr.src.scenarioXml.XmlNodeDataProcessor;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

final class PointListExtractor implements XmlNodeDataProcessor {
    private final Set<String> commonPoints = new HashSet<String>();
    private final Set<String> finishPoints = new HashSet<String>();

    PointListExtractor(Node root) {
        assert(null != root) : "root must be non-null reference";
        new XmlFilter(root.getChildNodes()).visitAllNodes("point", this, null);
    }

    Collection<String> getFinishPoints() {
        return Collections.unmodifiableSet(this.finishPoints);
    }

    Collection<String> getCommonPoints() {
        return Collections.unmodifiableSet(this.commonPoints);
    }

    Collection<String> getPoints() {
        List<String> result = new LinkedList<String>(this.commonPoints);

        result.addAll(this.finishPoints);

        return result;
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
        Node pointName = target.getAttributes().getNamedItem("name");

        if (!(XmlFilter.textContentExists(pointName))) {
            return;
        }

        if (null != target.getAttributes().getNamedItem("finish")) {
            this.finishPoints.add(pointName.getTextContent());
        } else {
            this.commonPoints.add(pointName.getTextContent());
        }
    }
}


//~ Formatted in DD Std on 13/08/28
