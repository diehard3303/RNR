/*
 * @(#)CBCallsStorage.java   13/08/28
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

import rnr.src.players.Crew;
import rnr.src.rnrcore.eng;
import rnr.src.rnrscr.CBVideocallelemnt;
import rnr.src.xmlutils.Node;
import rnr.src.xmlutils.NodeList;
import rnr.src.xmlutils.XmlUtils;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public final class CBCallsStorage {
    private static final String TOP = "cbcall";
    private static final String ELEMENT = "element";
    private static final String NAME = "name";
    private static final String DIALOG = "dialog";
    private static final String WHO = "who";
    private static final String TIMECALL = "timecall";
    private static final String TALKANYWAY = "talkanyway";
    private static CBCallsStorage instance = null;
    private final ArrayList<CBVideoStroredCall> storedCBVideoCalls = new ArrayList();
    private boolean inited = false;

    /**
     * Method description
     *
     *
     * @return
     */
    public static CBCallsStorage getInstance() {
        if (null == instance) {
            instance = new CBCallsStorage();
        }

        return instance;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        if (instance != null) {
            instance.storedCBVideoCalls.clear();
        }

        instance = null;
    }

    /**
     * Method description
     *
     *
     * @param name
     *
     * @return
     */
    public CBVideoStroredCall getStoredCall(String name) {
        for (CBVideoStroredCall storedCBVideoCall : this.storedCBVideoCalls) {
            if (storedCBVideoCall.name.compareToIgnoreCase(name) == 0) {
                return storedCBVideoCall;
            }
        }

        return null;
    }

    private void addStoredCBVideoCall(CBVideoStroredCall call) {
        this.storedCBVideoCalls.add(call);
    }

    /**
     * Method description
     *
     */
    @SuppressWarnings("rawtypes")
    public void init() {
        if (this.inited) {
            return;
        }

        this.inited = true;

        Vector filenames = new Vector();

        eng.getFilesAllyed("cbcall", filenames);

        for (String name : filenames) {
            initwithfile(name);
        }
    }

    private void initwithfile(String filename) {
        Node node = XmlUtils.parse(filename);

        if (null == node) {
            return;
        }

        NodeList children = node.getChildren().findNamedNodes("element");

        for (Node nextchild : children) {
            String name = getAttr(nextchild, "name", "noname");
            String dialog = getAttr(nextchild, "dialog", "noname");
            String who = getAttr(nextchild, "who", null);
            String timecall = getAttr(nextchild, "timecall", "10.");
            String talkanyway = getAttr(nextchild, "talkanyway", "true");
            double time = 10.0D;

            try {
                time = Double.parseDouble(timecall);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

            boolean f_talkanyway = true;

            try {
                f_talkanyway = Boolean.parseBoolean(talkanyway);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

            CBVideoStroredCall element = new CBVideoStroredCall(name, dialog, (float) time, f_talkanyway);

            if (who != null) {
                element.setIdentitie(who);
            }

            addStoredCBVideoCall(element);
        }
    }

    private String getAttr(Node node, String attr, String defaultvalue) {
        if (node.hasAttribute(attr)) {
            return node.getAttribute(attr);
        }

        return defaultvalue;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Vector printDialogsInfo() {
        Vector res = new Vector();

        for (CBVideoStroredCall element : getInstance().storedCBVideoCalls) {
            CBVideocallelemnt call = element.makeImmediateCall();
            long pointer = call.nativePointer;

            res.add(new Data(pointer, call.getDialogName(), call.whocalls.getIdentitie(),
                             Crew.getIgrok().getIdentitie()));
        }

        return res;
    }

    static class Data {
        String name;
        String caller;
        String callee;
        long pointer;

        Data() {}

        Data(long pointer, String name, String caler, String calee) {
            this.pointer = pointer;
            this.name = name;
            this.caller = caler;
            this.callee = calee;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
