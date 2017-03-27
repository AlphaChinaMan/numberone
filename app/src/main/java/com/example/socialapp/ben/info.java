package com.example.socialapp.ben;

/**
 * Created by 陈梦轩 on 2017/3/23.
 */

public class info {
    //接收
    String receiveAvatar;
    String receiveContent;
    //时间
    String chatTime;
    //发送
    String sendAvatar;
    String sendContent;

int type;
    public info(String receiveAvatar, String receiveContent, String chatTime, String sendAvatar, String sendContent) {
        this.receiveAvatar = receiveAvatar;
        this.receiveContent = receiveContent;
        this.chatTime = chatTime;
        this.sendAvatar = sendAvatar;
        this.sendContent = sendContent;
    }

    public String getReceiveAvatar() {
        return receiveAvatar;
    }

    public void setReceiveAvatar(String receiveAvatar) {
        this.receiveAvatar = receiveAvatar;
    }

    public String getReceiveContent() {
        return receiveContent;
    }

    public void setReceiveContent(String receiveContent) {
        this.receiveContent = receiveContent;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public String getSendAvatar() {
        return sendAvatar;
    }

    public void setSendAvatar(String sendAvatar) {
        this.sendAvatar = sendAvatar;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
