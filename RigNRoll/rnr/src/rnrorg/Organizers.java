/*
 * @(#)Organizers.java   13/08/26
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

import rnr.src.rnrcore.eng;
import rnr.src.scenarioUtils.Pair;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class Organizers {
    private static Organizers ourInstance = new Organizers();
    private final ArrayList<Pair<Pattern, String>> organizerNamePatterns = new ArrayList<Pair<Pattern, String>>();
    private final HashMap<String, IStoreorgelement> organizerElements = new HashMap<String, IStoreorgelement>();
    private HashMap<String, IStoreorgelement> organizerElementsSpecial = new HashMap<String, IStoreorgelement>();

    /**
     * Method description
     *
     *
     * @return
     */
    public static Organizers getInstance() {
        return ourInstance;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        ourInstance = new Organizers();
    }

    /**
     * Method description
     *
     *
     * @param organizerName
     *
     * @return
     */
    public IStoreorgelement get(String organizerName) {
        IStoreorgelement result = this.organizerElements.get(organizerName);

        if (null == result) {
            return (this.organizerElementsSpecial.get(organizerName));
        }

        return result;
    }

    /**
     * Method description
     *
     *
     * @param organizerName
     * @param org
     */
    public void add(String organizerName, IStoreorgelement org) {
        this.organizerElements.put(organizerName, org);
    }

    /**
     * Method description
     *
     *
     * @param organizerName
     * @param org
     */
    public void addSpecial(String organizerName, IStoreorgelement org) {
        this.organizerElementsSpecial.put(organizerName, org);
    }

    /**
     * Method description
     *
     *
     * @param organizerName
     *
     * @return
     */
    public IStoreorgelement getPatterned(String organizerName) {
        if (null != organizerName) {
            for (Pair<?, ?> pair : this.organizerNamePatterns) {
                if (((Pattern) pair.getFirst()).matcher(organizerName).matches()) {
                    return get((String) pair.getSecond());
                }
            }
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param namePattern
     */
    public void add(String name, Pattern namePattern) {
        if ((null == name) || (null == namePattern)) {
            return;
        }

        this.organizerNamePatterns.add(new Pair<Pattern, String>(namePattern, name));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public HashMap<String, IStoreorgelement> getOrganizerElementsSpecial() {
        return this.organizerElementsSpecial;
    }

    /**
     * Method description
     *
     *
     * @param organizerElementsSpecial
     */
    public void setOrganizerElementsSpecial(HashMap<String, IStoreorgelement> organizerElementsSpecial) {
        this.organizerElementsSpecial = organizerElementsSpecial;
    }

    /**
     * Method description
     *
     *
     * @param element
     *
     * @return
     */
    public IStoreorgelement submitRestoredOrgElement(Scorgelement element) {
        IStoreorgelement result = null;

        if (element.getType().isSpecialType()) {
            result = this.organizerElementsSpecial.get(element.getName());
        } else {
            result = this.organizerElements.get(element.getName());
        }

        if (null == result) {
            eng.err("ERRORR. Organizers on submitRestoredOrgElement cannot find named element " + element.getName());
            eng.fatal("Save incompatibility.");

            return element;
        }

        if (!(result instanceof Scorgelement)) {
            eng.err("ERRORR. Organizers on submitRestoredOrgElement found named element " + element.getName()
                    + " that is not instance of Scorgelement.");
            eng.fatal("Save incompatibility.");

            return element;
        }

        Scorgelement orgElement = (Scorgelement) result;

        orgElement.submitLoadedOrgNode(element);

        return orgElement;
    }
}


//~ Formatted in DD Std on 13/08/26
