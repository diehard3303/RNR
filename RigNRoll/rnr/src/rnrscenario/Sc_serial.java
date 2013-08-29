/*
 * @(#)Sc_serial.java   13/08/28
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


package rnr.src.rnrscenario;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.ISelfSerializable;
import rnr.src.rnrcore.SelfSerializable;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class Sc_serial extends SelfSerializable {
    protected static ISelfSerializable mInterface = null;
    private static Sc_serial instance = null;
    static final long serialVersionUID = 0L;

    /**
     * Method description
     *
     *
     * @return
     */
    public static ISelfSerializable getSerializationInterface() {
        if (null == mInterface) {
            mInterface = new SrializableStub();
        }

        return mInterface;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static Sc_serial getInstance() {
        if (null == instance) {
            instance = new Sc_serial();
        }

        return instance;
    }

    /**
     * Method description
     *
     */
    public void recieve() {}

    private void restore() {}

    /**
     * Method description
     *
     *
     * @param stream
     *
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @Override
    public void deserialize(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        instance = (Sc_serial) stream.readObject();
        instance.restore();
    }

    static class SrializableStub implements ISelfSerializable {

        /**
         * Method description
         *
         *
         * @return
         */
        @Override
        public Serializable getSerializationTarget() {
            return Sc_serial.getInstance();
        }

        /**
         * Method description
         *
         *
         * @param stream
         *
         * @throws ClassNotFoundException
         * @throws IOException
         */
        @Override
        public void deserialize(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            Sc_serial.getInstance().deserialize(stream);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
