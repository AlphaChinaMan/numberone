package com.example.socialapp.ben;

import com.hyphenate.chat.EMGroupManager;

/**
 * Created by 陈梦轩 on 2017/4/8.
 */

public class CreatGroupBen {
    private String groupname;//群组名
    private String desc;//简介
    private String[] allMembers;//初始化群成员,只有自己可以为空数组
    private String reason; //邀请成员加入的reason
    private EMGroupManager.EMGroupOptions option;	//群组类型选项，；可以设置群组最大用户数默认（200）及群组类型

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String[] getAllMembers() {
        return allMembers;
    }

    public void setAllMembers(String[] allMembers) {
        this.allMembers = allMembers;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public EMGroupManager.EMGroupOptions getOption() {
        return option;
    }

    public void setOption(EMGroupManager.EMGroupOptions option) {
        this.option = option;
    }
}
