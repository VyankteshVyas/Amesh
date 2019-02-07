package com.example.vyanktesh.amesh;

public class Groupadminmessangingmodel {
    String groupname,read,message;
    public Groupadminmessangingmodel() {

    }
    public Groupadminmessangingmodel(String groupname,String read,String message) {
        this.groupname=groupname;
        this.read=read;
        this.message=message;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
