package com.example.socialapp.ben;

/**
 * Created by 陈梦轩 on 2017/3/23.
 */

public class info {
    private String name;

    private  String content;
    private  String time;
private  String wei;

    public info(String name, String content, String time, String wei) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.wei = wei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWei() {
        return wei;
    }

    public void setWei(String wei) {
        this.wei = wei;
    }

    @Override
    public String toString() {
        return "info{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", wei='" + wei + '\'' +
                '}';
    }
}
