/*
 * @(#)NativeSerializationInterface.java   13/08/26
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


package rnr.src.rnrcore;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrloggers.ScriptsLogger;
import rnr.src.rnrscenario.Sc_serial;

//~--- JDK imports ------------------------------------------------------------

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class NativeSerializationInterface {
    private static final boolean USE_SERIALIZABLES = false;
    private static GameXmlSerializator xmlSerializator = null;
    private static List<ISelfSerializable> binarySerializableTargets = new LinkedList<ISelfSerializable>();

    /**
     * Method description
     *
     *
     * @param saver
     */
    public static void setGameSerializator(GameXmlSerializator saver) {
        if (null != saver) {
            xmlSerializator = saver;
        } else {
            throw new IllegalArgumentException("saver must be non-null reference");
        }
    }

    /**
     * Method description
     *
     *
     * @param target
     */
    public static void addSelfSerializable(ISelfSerializable target) {
        if (null != target) {
            binarySerializableTargets.add(target);
        } else {
            throw new IllegalArgumentException("target must be non-null reference");
        }
    }

    /**
     * Method description
     *
     *
     * @param saveVersion
     *
     * @return
     */
    public static byte[] serialize(int saveVersion) {
        Sc_serial.getInstance().recieve();
        xmlSerializator.setSave_version(saveVersion);

        try {
            byte[] xmlData = xmlSerializator.saveToByteArray();
            ByteArrayOutputStream binarySerialized = new ByteArrayOutputStream();
            ObjectOutputStream mainStream = new ObjectOutputStream(binarySerialized);

            mainStream.writeObject(xmlData);
            mainStream.close();
            binarySerialized.close();

            return binarySerialized.toByteArray();
        } catch (IOException exception) {
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, "scenario serialization failed");
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, exception.getMessage());
            exception.printStackTrace(System.err);
            System.err.flush();
        }

        return new byte[0];
    }

    private static void closeObject(Closeable target) {
        try {
            if (null != target) {
                target.close();
            }
        } catch (IOException exception) {
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, exception.getMessage());
            exception.printStackTrace(System.err);
            System.err.flush();
        }
    }

    /**
     * Method description
     *
     *
     * @param dataArray
     */
    public static void deserialize(byte[] dataArray) {
        if (null == dataArray) {
            String errorMessgae = "NativeSerializationInterface.deserialize erorr: dataArray is null";

            ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessgae);
            eng.err(errorMessgae);

            return;
        }

        if (0 == dataArray.length) {
            String errorMessgae = "NativeSerializationInterface.deserialize erorr: dataArray has 0 length";

            ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessgae);
            eng.err(errorMessgae);

            return;
        }

        ByteArrayInputStream byteStream = null;
        ObjectInputStream reader = null;

        try {
            byteStream = new ByteArrayInputStream(dataArray);
            reader = new ObjectInputStream(byteStream);

            byte[] xmlTextData = (byte[]) reader.readObject();

            xmlSerializator.loadFromByteArray(xmlTextData);
        } catch (ClassNotFoundException exception) {
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, "scenario deserialization failed");
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, exception.getMessage());
            exception.printStackTrace(System.err);
            System.err.flush();
        } catch (IOException exception) {
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, "scenario deserialization failed");
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, exception.getMessage());
            exception.printStackTrace(System.err);
            System.err.flush();
        } catch (Throwable e) {
            e.printStackTrace(System.err);

            if (!(eng.noNative)) {
                eng.exceptionDuringGameSerialization();
            }
        } finally {
            closeObject(reader);
            closeObject(byteStream);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
