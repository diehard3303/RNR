/*
 * @(#)InformationChannelsFactory.java   13/08/28
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


package rnr.src.rnrscenario.missions.infochannels;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrscenario.missions.infochannels.InformationChannelsFactory.ChannelParameters;
import rnr.src.rnrscr.smi.IArticleCreator;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class InformationChannelsFactory {
    private static final int DEFAULT_CHANNELS_CAPACITY = 16;
    private static InformationChannelsFactory ourInstance;

    static {
        ourInstance = new InformationChannelsFactory();
    }

    private final Map<String, InformationChannel> channelsPrototypes = new HashMap<String, InformationChannel>(16);
    private final Map<String, ChannelParameters> channelsTypeImmediate = new HashMap<String, ChannelParameters>(16);

    /**
     * Method description
     *
     *
     * @return
     */
    public static InformationChannelsFactory getInstance() {
        return ourInstance;
    }

    /**
     * Method description
     *
     */
    public static void deinit() {
        if (ourInstance != null) {
            ourInstance.channelsPrototypes.clear();
            ourInstance.channelsTypeImmediate.clear();
        }

        ourInstance = new InformationChannelsFactory();
    }

    /**
     * Method description
     *
     *
     * @param uid
     * @param prototype
     * @param is_immediate
     * @param is_bounded
     * @param is_dialog
     * @param closeChannelInfo
     */
    public void addChannelPrototype(String uid, InformationChannel prototype, boolean is_immediate, boolean is_bounded,
                                    boolean is_dialog, InfoChannelEventCallback.ChannelClose closeChannelInfo) {
        assert(null != prototype) : "target to add must be non-null reference";
        this.channelsPrototypes.put(uid, prototype);
        this.channelsTypeImmediate.put(uid,
                                       new ChannelParameters(is_immediate, is_bounded, is_dialog, closeChannelInfo));
    }

    /**
     * Method description
     *
     *
     * @param channelName
     *
     * @return
     */
    public boolean isImmediateChannel(String channelName) {
        if (!(this.channelsTypeImmediate.containsKey(channelName))) {
            return false;
        }

        return this.channelsTypeImmediate.get(channelName).isImmidiate();
    }

    /**
     * Method description
     *
     *
     * @param channelName
     *
     * @return
     */
    public boolean isBoundedChannel(String channelName) {
        if (!(this.channelsTypeImmediate.containsKey(channelName))) {
            return false;
        }

        return this.channelsTypeImmediate.get(channelName).isBounded();
    }

    /**
     * Method description
     *
     *
     * @param channelName
     *
     * @return
     */
    public boolean isDialogChannel(String channelName) {
        if (!(this.channelsTypeImmediate.containsKey(channelName))) {
            return false;
        }

        return this.channelsTypeImmediate.get(channelName).isDialog();
    }

    /**
     * Method description
     *
     *
     * @param channelName
     *
     * @return
     */
    public InfoChannelEventCallback.ChannelClose getCloseChannelInfo(String channelName) {
        if (!(this.channelsTypeImmediate.containsKey(channelName))) {
            return InfoChannelEventCallback.ChannelClose.DIALOG;
        }

        return this.channelsTypeImmediate.get(channelName).getCloseChannelInfo();
    }

    /**
     * Method description
     *
     *
     * @param name
     * @param data_for_creation
     *
     * @return
     *
     * @throws NoSuchChannelException
     */
    public InformationChannel construct(String name, Object data_for_creation) throws NoSuchChannelException {
        assert(null != name) : "name must be non-null reference";

        InformationChannel prototype = this.channelsPrototypes.get(name);

        if (null != prototype) {
            InformationChannel res = prototype.clone();

            if ((res instanceof ArticleChannel) && (data_for_creation instanceof IArticleCreator)) {
                ((ArticleChannel) res).setArticle((IArticleCreator) data_for_creation);
            }

            return res;
        }

        throw new NoSuchChannelException(name);
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/28
     * @author         TJ    
     */
    public static final class ChannelParameters {
        private boolean immidiate = false;
        private boolean bounded = false;
        private boolean dialog = false;
        private InfoChannelEventCallback.ChannelClose closeChannelInfo = InfoChannelEventCallback.ChannelClose.DIALOG;

        /**
         * Constructs ...
         *
         *
         * @param immidiate
         * @param bounded
         * @param dialog
         * @param closeChannelInfo
         */
        public ChannelParameters(boolean immidiate, boolean bounded, boolean dialog,
                                 InfoChannelEventCallback.ChannelClose closeChannelInfo) {
            this.immidiate = immidiate;
            this.bounded = bounded;
            this.dialog = dialog;
            this.closeChannelInfo = closeChannelInfo;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isImmidiate() {
            return this.immidiate;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isBounded() {
            return this.bounded;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public boolean isDialog() {
            return this.dialog;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public InfoChannelEventCallback.ChannelClose getCloseChannelInfo() {
            return this.closeChannelInfo;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
