package com.example.vyanktesh.amesh;

public class RecieverModel {
    String Groupid;
    String userName;


    public RecieverModel(){}

    public RecieverModel(String groupid, String userName) {
        Groupid = groupid;
        this.userName = userName;
    }

    public String getGroupid() {
        return Groupid;
    }

    public void setGroupid(String groupid) {
        Groupid = groupid;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }
}
