package com.kaku.colorfulnews.mvp.ui.adapter;


import java.io.Serializable;

public class PersonAdapter implements Serializable{
    private String userId = "";
    private String name = "";
    private String avatarSrc = "";
    private String desc ;

    public PersonAdapter()
    {

    }

    public PersonAdapter(String userId,String name,String avatarSrc,String desc)
    {
        this.userId = userId;
        this.name = name;
        this.avatarSrc = avatarSrc;
        this.desc  = desc;
    }

    public void setUserId(String Id) {
        this.userId = Id;
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public void setAvatarSrc(String avatarSrc) {
        this.avatarSrc = avatarSrc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.name;
    }

    public String getAvatarSrc() {
        return this.avatarSrc;
    }

    public String getDesc() {
        return this.desc;
    }
}
