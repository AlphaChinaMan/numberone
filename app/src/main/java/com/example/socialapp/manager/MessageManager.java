package com.example.socialapp.manager;

import com.example.socialapp.inface.MessageListener;

/**
 * Created by 陈梦轩 on 2017/4/13.
 */

public class MessageManager {
    private static MessageManager messageManager;

    private MessageListener messageListListener;

    public MessageListener getMessageListListener() {
        return messageListListener;
    }

    public void setMessageListListener(MessageListener messageListListener) {
        this.messageListListener = messageListListener;
    }

    public static synchronized MessageManager getInsatance() {
        if (messageManager == null) {
            messageManager = new MessageManager();
        }
        return messageManager;
    }

}
