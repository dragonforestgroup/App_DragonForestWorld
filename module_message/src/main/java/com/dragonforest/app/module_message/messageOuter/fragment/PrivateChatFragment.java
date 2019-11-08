package com.dragonforest.app.module_message.messageOuter.fragment;

import com.dragonforest.app.module_message.database.MessageType;

/**
 * @author 韩龙林
 * @date 2019/10/8 20:09
 */
public class PrivateChatFragment extends BaseMessageOuterFragment {

    @Override
    public int initMessageType() {
        return MessageType.TYPE_PRIVATE_CHAT;
    }
}
