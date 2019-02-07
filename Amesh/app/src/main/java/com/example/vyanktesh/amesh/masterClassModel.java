package com.example.vyanktesh.amesh;

public class masterClassModel {

    String partyName, address, mobilenumber,PanNo, Bankname, Bnakbranch, accountNumber, Accountname, IFSCcode,groupnamei, Entrydateedit,typespinner,groupadmin,groupid,Remarks;
    public masterClassModel(){}
    public masterClassModel(String partyName, String address, String mobilenumber, String panNo, String bankname, String bnakbranch, String accountNumber, String accountname, String IFSCcode, String groupnamei, String entrydateedit, String typespinner, String groupadmin,String groupid,String Remarks) {
        this.partyName = partyName;
        this.address = address;
        this.mobilenumber = mobilenumber;
        PanNo = panNo;
        Bankname = bankname;
        Bnakbranch = bnakbranch;
        this.Remarks=Remarks;
        this.accountNumber = accountNumber;
        Accountname = accountname;
        this.IFSCcode = IFSCcode;
        this.groupnamei = groupnamei;
        Entrydateedit = entrydateedit;
        this.typespinner = typespinner;
        this.groupadmin = groupadmin;
        this.groupid=groupid;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }



    public String getPanNo() {
        return PanNo;
    }

    public void setPanNo(String panNo) {
        PanNo = panNo;
    }

    public String getBankname() {
        return Bankname;
    }

    public void setBankname(String bankname) {
        Bankname = bankname;
    }

    public String getBnakbranch() {
        return Bnakbranch;
    }

    public void setBnakbranch(String bnakbranch) {
        Bnakbranch = bnakbranch;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountname() {
        return Accountname;
    }

    public void setAccountname(String accountname) {
        Accountname = accountname;
    }

    public String getIFSCcode() {
        return IFSCcode;
    }

    public void setIFSCcode(String IFSCcode) {
        this.IFSCcode = IFSCcode;
    }

    public String getGroupnamei() {
        return groupnamei;
    }

    public void setGroupnamei(String groupnamei) {
        this.groupnamei = groupnamei;
    }

    public String getEntrydateedit() {
        return Entrydateedit;
    }

    public void setEntrydateedit(String entrydateedit) {
        Entrydateedit = entrydateedit;
    }

    public String getTypespinner() {
        return typespinner;
    }

    public void setTypespinner(String typespinner) {
        this.typespinner = typespinner;
    }

    public String getGroupadmin() {
        return groupadmin;
    }

    public void setGroupadmin(String groupadmin) {
        this.groupadmin = groupadmin;
    }
}
