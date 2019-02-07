package com.example.vyanktesh.amesh;

public class Adminmessangingmodel {
    String read,message;
    public Adminmessangingmodel(){}
    public Adminmessangingmodel(String read,String message) {
        this.read=read;
        this.message=message;
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
