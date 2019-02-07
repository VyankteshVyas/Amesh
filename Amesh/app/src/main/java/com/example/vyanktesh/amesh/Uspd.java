package com.example.vyanktesh.amesh;

public class Uspd {
    String Password,lastlogin,Groupid;
    public Uspd(){}
    public Uspd(String Password,String lastlogin,String Groupid){
        this.Password=Password;
        this.lastlogin=lastlogin;
        this.Groupid=Groupid;
    }

    public String getGroupid() {
        return Groupid;
    }

    public void setGroupid(String groupid) {
        Groupid = groupid;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }
}
