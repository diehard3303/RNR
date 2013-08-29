/*
 * @(#)CrewNamesManager.java   13/08/26
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


package rnr.src.players;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class CrewNamesManager {
    private static aiplayer mainCharacter;
    private static CrewNamesManager instance;

    static {
        instance = null;
    }

    private final HashMap<String, aiplayer> registeredNames = new HashMap();

    private CrewNamesManager() {
        String[] namesSCENARIO = {
            "SC_SECRETARY", "SC_DOROTHY", "SC_MATTHEW", "SC_BANDIT3", "SC_BANDITJOE", "SC_BANDITGUN",
            "SC_BILL_OF_LANDING", "SC_STRANGER", "SC_PITERPAN", "SC_ONTANIELO", "SC_KOH", "SC_POLICE", "SC_MONICA"
        };

        for (String name : namesSCENARIO) {
            add(name);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static CrewNamesManager getInstance() {
        if (null == instance) {
            instance = new CrewNamesManager();
        }

        return instance;
    }

    /**
     * Method description
     *
     *
     * @param carOfMainCharacter
     */
    public static void deinitMainCharacter(actorveh carOfMainCharacter) {
        assert(null != carOfMainCharacter);
        mainCharacter.abondoneCar(carOfMainCharacter);
        mainCharacter = null;
    }

    /**
     * Method description
     *
     *
     * @param character
     */
    public static void mainCharacterLoaded(aiplayer character) {
        mainCharacter = character;
    }

    /**
     * Method description
     *
     */
    public static void createMainCharacter() {
        if (null != mainCharacter) {
            return;
        }

        mainCharacter = new aiplayer("SC_NICK");
        mainCharacter.setModelCreator(new MC(), "MC");
    }

    private void add(String name) {
        aiplayer player = new aiplayer(name);

        player.setModelCreator(new ScenarioPlayer(), "sc");
        this.registeredNames.put(name, player);
    }

    aiplayer gPlayer(String nameRef) {
        if (!(this.registeredNames.containsKey(nameRef))) {
            return null;
        }

        return (this.registeredNames.get(nameRef));
    }

    static aiplayer getMainCharacterPlayer() {
        return mainCharacter;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        instance = null;
    }
}


//~ Formatted in DD Std on 13/08/26
