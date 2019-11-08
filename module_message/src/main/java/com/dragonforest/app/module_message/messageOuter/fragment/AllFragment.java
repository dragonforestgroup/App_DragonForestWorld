package com.dragonforest.app.module_message.messageOuter.fragment;

import com.dragonforest.app.module_message.database.MessageDBHelper;
import com.dragonforest.app.module_message.messageOuter.bean.MessageOuterModel;

import java.util.LinkedList;


/**
 * @author 韩龙林
 * @date 2019/10/8 19:56
 */
public class AllFragment extends BaseMessageOuterFragment {

    @Override
    public int initMessageType() {
        // 全部类型 此值为-1
        return -1;
    }

    @Override
    public LinkedList<MessageOuterModel> getData() {
        return MessageDBHelper.getAllMessageOuterModels();
    }
}
