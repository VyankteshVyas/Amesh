package com.example.vyanktesh.amesh;

public class NewgroupModel {
    String groupname;
    String groupadminmobileNumber;
    String PartyType;
    public NewgroupModel(){

    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupadminmobileNumber() {
        return groupadminmobileNumber;
    }

    public void setGroupadminmobileNumber(String groupadminmobileNumber) {
        this.groupadminmobileNumber = groupadminmobileNumber;
    }

    public String getPartyType() {
        return PartyType;
    }

    public void setPartyType(String partyType) {
        PartyType = partyType;
    }

    public NewgroupModel(String groupname, String groupadminmobileNumber, String PartyType){
        this.groupadminmobileNumber=groupadminmobileNumber;
        this.groupname=groupname;
        this.PartyType=PartyType;

    }
}
